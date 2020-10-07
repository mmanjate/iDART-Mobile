package mz.org.fgh.idartlite.view.patient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.common.DialogListener;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.common.ListbleRecycleViewAdapter;
import mz.org.fgh.idartlite.common.ValorSimples;
import mz.org.fgh.idartlite.databinding.ActivityPrescriptionBinding;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.PrescriptionVM;

public class PrescriptionActivity extends BaseActivity implements DialogListener {

    private ActivityPrescriptionBinding prescriptionBinding;

    private List<Listble> selectedDrugs;

    private RecyclerView rcvSelectedDrugs;

    private ListbleRecycleViewAdapter listbleRecycleViewAdapter;

    private ArrayAdapter<DispenseType> dispenseTypeArrayAdapter;

    private ArrayAdapter<TherapeuticLine> lineArrayAdapter;

    private ArrayAdapter<TherapeuticRegimen> regimenArrayAdapter;

    private ArrayAdapter<Drug> drugArrayAdapter;

    private ArrayAdapter<ValorSimples> valorSimplesArrayAdapter;

    private ArrayAdapter<ValorSimples> motiveArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prescriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_prescription);

        rcvSelectedDrugs = prescriptionBinding.rcvSelectedDrugs;

        prescriptionBinding.drugsDataLyt.setVisibility(View.GONE);
        prescriptionBinding.urgentLyt.setVisibility(View.GONE);
        getRelatedViewModel().setInitialDataVisible(true);
        getRelatedViewModel().setDrugDataVisible(false);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().getPrescription().setPatient((Patient) bundle.getSerializable("patient"));
                if (getRelatedViewModel().getPrescription().getPatient() == null){
                    getRelatedViewModel().setPrescription((Prescription) bundle.getSerializable("prescription"));
                    if (getRelatedViewModel().getPrescription() == null) {
                        throw new RuntimeException(getString(R.string.no_patient_or_prescription));
                    }else {
                        try {
                            getRelatedViewModel().loadPrescribedDrugsOfPrescription();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Utilities.displayAlertDialog(PrescriptionActivity.this, getString(R.string.error_on_loading_data)+e.getLocalizedMessage()).show();
                        }
                    }
                }

            }else {
                throw new RuntimeException(getString(R.string.no_patient_or_prescription));
            }
        }

        if (getApplicationStep().isApplicationstepCreate()){
            try {
                getRelatedViewModel().loadLastPatientPrescription();
                getRelatedViewModel().getPrescription().setPrescriptionDate(DateUtilitis.getCurrentDate());
                getRelatedViewModel().getPrescription().setExpiryDate(null);
            } catch (SQLException e) {
                e.printStackTrace();
                Utilities.displayAlertDialog(PrescriptionActivity.this, getString(R.string.error_on_loading_data)+e.getLocalizedMessage()).show();
            }
        }

        prescriptionBinding.setViewModel(getRelatedViewModel());


        populateForm();

        loadSelectedPrescriptionToForm();

        prescriptionBinding.prescriptionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PrescriptionActivity.this, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                prescriptionBinding.prescriptionDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        prescriptionBinding.initialData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);

            }
        });
        prescriptionBinding.txvDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);

            }
        });
        prescriptionBinding.txvUrgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);

            }
        });

        prescriptionBinding.spnRegime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TherapeuticRegimen regimen = (TherapeuticRegimen) adapterView.getItemAtPosition(i);

                getRelatedViewModel().getPrescription().setTherapeuticRegimen(regimen);

                if (regimen.getId() > 0 && getRelatedViewModel().isSeeOnlyOfRegime()) {
                    reloadDrugsSpinnerByRegime(regimen);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prescriptionBinding.imvAddSelectedDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (selectedDrugs == null) selectedDrugs = new ArrayList<>();

               if ( prescriptionBinding.spnDrugs.getSelectedItem() != null){
                   Listble<Drug> listble = (Listble<Drug>) prescriptionBinding.spnDrugs.getSelectedItem();

                   if (!selectedDrugs.contains(listble)) {
                       listble.setListPosition(selectedDrugs.size()+1);
                       selectedDrugs.add(listble);
                       Collections.sort(selectedDrugs);

                       displaySelectedDrugs();
                   }else {

                       Utilities.displayAlertDialog(PrescriptionActivity.this, getString(R.string.drug_data_duplication_msg)).show();
                   }

               }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getApplicationStep().isApplicationStepDisplay()){
            disableAllSpinners();
        }
    }

    public void reloadDrugsSpinnerByRegime(TherapeuticRegimen regimen) {
        List<Drug> drugs = new ArrayList<>();
        drugs.add(new Drug());
        try {
            if (regimen != null && getRelatedViewModel().isSeeOnlyOfRegime()) {
                drugs.addAll(getRelatedViewModel().getAllDrugsOfTheraputicRegimen(regimen));
            }
            else {
                drugs.addAll(getRelatedViewModel().getAllDrugs());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        drugArrayAdapter.notifyDataSetChanged();
        /*drugArrayAdapter = new ArrayAdapter<Drug>(getApplicationContext(), android.R.layout.simple_spinner_item, drugs);
        drugArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prescriptionBinding.spnDrugs.setAdapter(drugArrayAdapter);*/

    }


    private void loadSelectedPrescriptionToForm() {

        prescriptionBinding.spnDispenseType.setSelection(dispenseTypeArrayAdapter.getPosition(getRelatedViewModel().getPrescription().getDispenseType()));

        prescriptionBinding.spnLine.setSelection(lineArrayAdapter.getPosition(getRelatedViewModel().getPrescription().getTherapeuticLine()));

        prescriptionBinding.spnRegime.setSelection(regimenArrayAdapter.getPosition(getRelatedViewModel().getPrescription().getTherapeuticRegimen()));

        prescriptionBinding.spnDuration.setSelection(valorSimplesArrayAdapter.getPosition(ValorSimples.fastCreate(getRelatedViewModel().getPrescription().getSupply())));

        prescriptionBinding.spnReson.setSelection(motiveArrayAdapter.getPosition(ValorSimples.fastCreate(getRelatedViewModel().getPrescription().getUrgentNotes())));


        if (this.selectedDrugs == null) selectedDrugs = new ArrayList<>();

        int i = 1;
        for (PrescribedDrug prescribedDrug : getRelatedViewModel().getPrescription().getPrescribedDrugs()){
            Drug d = prescribedDrug.getDrug();
            d.setListPosition(i);
            i++;
            selectedDrugs.add(d);
        }

        displaySelectedDrugs();
    }

    private void populateForm() {
        try {
            List<TherapeuticRegimen> therapeuticRegimenList = new ArrayList<>();
            therapeuticRegimenList.add(new TherapeuticRegimen());
            therapeuticRegimenList.addAll(getRelatedViewModel().getAllTherapeuticRegimen());

            List<TherapeuticLine> therapeuticLines = new ArrayList<>();
            therapeuticLines.add(new TherapeuticLine());
            therapeuticLines.addAll(getRelatedViewModel().getAllTherapeuticLines());

            List<DispenseType> dispenseTypes = new ArrayList<>();
            dispenseTypes.add(new DispenseType());
            dispenseTypes.addAll(getRelatedViewModel().getAllDispenseTypes());

            List<Drug> drugs = new ArrayList<>();
            drugs.add(new Drug());
            drugs.addAll(getRelatedViewModel().getAllDrugs());

            dispenseTypeArrayAdapter = new ArrayAdapter<DispenseType>(getApplicationContext(), android.R.layout.simple_spinner_item, dispenseTypes);
            dispenseTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prescriptionBinding.spnDispenseType.setAdapter(dispenseTypeArrayAdapter);

            lineArrayAdapter = new ArrayAdapter<TherapeuticLine>(getApplicationContext(), android.R.layout.simple_spinner_item, therapeuticLines);
            lineArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prescriptionBinding.spnLine.setAdapter(lineArrayAdapter);

            regimenArrayAdapter = new ArrayAdapter<TherapeuticRegimen>(getApplicationContext(), android.R.layout.simple_spinner_item, therapeuticRegimenList);
            regimenArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prescriptionBinding.spnRegime.setAdapter(regimenArrayAdapter);

            drugArrayAdapter = new ArrayAdapter<Drug>(getApplicationContext(), android.R.layout.simple_spinner_item, drugs);
            drugArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prescriptionBinding.spnDrugs.setAdapter(drugArrayAdapter);


            List<ValorSimples> durations = new ArrayList<>();
            durations.add(new ValorSimples());
            durations.add(ValorSimples.fastCreate(2, Prescription.DURATION_TWO_WEEKS));
            durations.add(ValorSimples.fastCreate(4, Prescription.DURATION_ONE_MONTH));
            durations.add(ValorSimples.fastCreate(8, Prescription.DURATION_TWO_MONTHS));
            durations.add(ValorSimples.fastCreate(12, Prescription.DURATION_THREE_MONTHS));
            durations.add(ValorSimples.fastCreate(16, Prescription.DURATION_FOUR_MONTHS));
            durations.add(ValorSimples.fastCreate(20, Prescription.DURATION_FIVE_MONTHS));
            durations.add(ValorSimples.fastCreate(24, Prescription.DURATION_SIX_MONTHS));

            valorSimplesArrayAdapter = new ArrayAdapter<ValorSimples>(getApplicationContext(), android.R.layout.simple_spinner_item, durations);
            valorSimplesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prescriptionBinding.spnDuration.setAdapter(valorSimplesArrayAdapter);

            List<ValorSimples> motives = new ArrayList<>();
            motives.add(new ValorSimples());
            motives.add(ValorSimples.fastCreate("Perda de Medicamento"));
            motives.add(ValorSimples.fastCreate("Ausencia do Clinico"));
            motives.add(ValorSimples.fastCreate("Laboratorio"));
            motives.add(ValorSimples.fastCreate("Rotura de Stock"));
            motives.add(ValorSimples.fastCreate("Outro"));

            motiveArrayAdapter = new ArrayAdapter<ValorSimples>(getApplicationContext(), android.R.layout.simple_spinner_item, motives);
            motiveArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            prescriptionBinding.spnReson.setAdapter(motiveArrayAdapter);

        } catch (SQLException e) {
            Utilities.displayAlertDialog(PrescriptionActivity.this, getString(R.string.error_loading_form_data)+e.getMessage());
            e.printStackTrace();
        }

    }

    private void changeVisibilityToInitialData(View view) {
        if (view.equals(prescriptionBinding.initialData)){
            if (prescriptionBinding.initialDataLyt.getVisibility() == View.VISIBLE){
                prescriptionBinding.initialDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                prescriptionBinding.initialDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }else if (view.equals(prescriptionBinding.txvDrugs)){
            if (prescriptionBinding.drugsDataLyt.getVisibility() == View.VISIBLE){
                prescriptionBinding.drugsDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                prescriptionBinding.drugsDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }else if (view.equals(prescriptionBinding.txvUrgent)){
            if (prescriptionBinding.urgentLyt.getVisibility() == View.VISIBLE){
                prescriptionBinding.urgentLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                prescriptionBinding.urgentLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
    }

    private void switchLayout(){
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setDrugDataVisible(!getRelatedViewModel().isDrugDataVisible());
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PrescriptionVM.class);
    }

    @Override
    public PrescriptionVM getRelatedViewModel() {
        return (PrescriptionVM) super.getRelatedViewModel();
    }

    private void displaySelectedDrugs(){
        if (listbleRecycleViewAdapter != null) {
            listbleRecycleViewAdapter.notifyDataSetChanged();
        }else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvSelectedDrugs, this.selectedDrugs, this);
            rcvSelectedDrugs.setAdapter(listbleRecycleViewAdapter);
        }
    }

    public void loadFormData() {
        getRelatedViewModel().getPrescription().setSupply(((ValorSimples) prescriptionBinding.spnDuration.getSelectedItem()).getId());
        getRelatedViewModel().getPrescription().setPrescriptionDate(DateUtilitis.createDate(prescriptionBinding.prescriptionDate.getText().toString(), "dd-MM-YYYY"));
        getRelatedViewModel().getPrescription().setDispenseType((DispenseType) prescriptionBinding.spnDispenseType.getSelectedItem());
        getRelatedViewModel().getPrescription().setTherapeuticRegimen((TherapeuticRegimen) prescriptionBinding.spnRegime.getSelectedItem());
        getRelatedViewModel().getPrescription().setTherapeuticLine((TherapeuticLine) prescriptionBinding.spnLine.getSelectedItem());

        if (getRelatedViewModel().getPrescription().isUrgent()) {

            getRelatedViewModel().getPrescription().setUrgentNotes(((ValorSimples) prescriptionBinding.spnReson.getSelectedItem()).getDescription());
        }
        List<PrescribedDrug> prescribedDrugs = new ArrayList<>();

        for (Listble drug : selectedDrugs){
            prescribedDrugs.add(initNewPrescribedDrug((Drug) drug));
        }

        getRelatedViewModel().getPrescription().setPrescribedDrugs(prescribedDrugs);

        if (getApplicationStep().isApplicationstepCreate()) getRelatedViewModel().getPrescription().setUuid(Utilities.getNewUUID().toString());

        getRelatedViewModel().getPrescription().setSyncStatus(BaseModel.SYNC_SATUS_READY);
    }

    private PrescribedDrug initNewPrescribedDrug(Drug drug) {
        return  new PrescribedDrug(drug, getRelatedViewModel().getPrescription());
    }

    public void doAfterPrescriptionSave(){
        Map<String, Object> params = new HashMap<>();
        params.put("prescription", getRelatedViewModel().getPrescription());
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        nextActivity(CreateDispenseActivity.class,params);
    }

    @Override
    public void doOnConfirmed() {
        if (getApplicationStep().isApplicationstepCreate()) {
            doAfterPrescriptionSave();
        }else {
            doOnDeny();
        }
    }

    @Override
    public void doOnDeny() {
        Map<String, Object> params = new HashMap<>();
        params.put("patient", getRelatedViewModel().getPrescription().getPatient());
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        params.put("requestedFragment", PrescriptionFragment.FRAGMENT_CODE_PRESCRIPTION);
        nextActivity(PatientActivity.class,params);
    }

    public void changeMotiveSpinnerStatus(boolean b) {
        prescriptionBinding.spnReson.setEnabled(b);
    }

    private void changeAllSpinnersStatus(boolean status){
        prescriptionBinding.spnDispenseType.setEnabled(status);
        prescriptionBinding.spnLine.setEnabled(status);
        prescriptionBinding.spnRegime.setEnabled(status);
        prescriptionBinding.spnDrugs.setEnabled(status);
        prescriptionBinding.spnDuration.setEnabled(status);
        prescriptionBinding.spnReson.setEnabled(status);

    }

    public void disableAllSpinners(){
        changeAllSpinnersStatus(false);
    }

    public void enableAllSpinners(){
        changeAllSpinnersStatus(true);
    }
}