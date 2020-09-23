package mz.org.fgh.idartlite.view.patient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.common.ListbleAdapter;
import mz.org.fgh.idartlite.common.RecyclerTouchListener;
import mz.org.fgh.idartlite.databinding.ActivityPrescriptionBinding;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.PrescriptionAdapter;
import mz.org.fgh.idartlite.viewmodel.PrescriptionVM;

public class PrescriptionActivity extends BaseActivity {

    private ActivityPrescriptionBinding prescriptionBinding;

    private List<Listble> selectedDrugs;

    private RecyclerView rcvSelectedDrugs;

    private ListbleAdapter listbleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prescriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_prescription);

        rcvSelectedDrugs = prescriptionBinding.rcvSelectedDrugs;

        prescriptionBinding.drugsDataLyt.setVisibility(View.GONE);
        getRelatedViewModel().setInitialDataVisible(true);
        getRelatedViewModel().setDrugDataVisible(false);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().getPrescription().setPatient((Patient) bundle.getSerializable("patient"));
                prescriptionBinding.setViewModel(getRelatedViewModel());

                if (getRelatedViewModel().getPrescription().getPatient() == null){
                    throw new RuntimeException("Não foi seleccionado um paciente para detalhar.");
                }
            }
        }

        populateForm();

        List<Drug> d = new ArrayList<>();
        Drug drug = new Drug();
        drug.setId(1);
        drug.setDescription("Paracetamol");
        drug.setPackSize(3);

        Drug dru = new Drug();
        dru.setId(2);
        dru.setDescription("Efferflu");
        dru.setPackSize(4);

        Drug dr = new Drug();
        dr.setId(3);
        dr.setDescription("Quinino");
        dr.setPackSize(7);

        d.add(drug);
        d.add(dru);
        d.add(dr);

        ArrayAdapter<Drug> adapter = new ArrayAdapter<Drug>(getApplicationContext(), android.R.layout.simple_spinner_item, d);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        prescriptionBinding.spnDrugs.setAdapter(adapter);


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

        prescriptionBinding.imvAddSelectedDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (selectedDrugs == null) selectedDrugs = new ArrayList<>();

               if ( prescriptionBinding.spnDrugs.getSelectedItem() != null){
                   Listble listble = (Listble) prescriptionBinding.spnDrugs.getSelectedItem();

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
    }

    private void populateForm() {

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
        if (listbleAdapter != null) {
            listbleAdapter.notifyDataSetChanged();
        }else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

            listbleAdapter = new ListbleAdapter(rcvSelectedDrugs, this.selectedDrugs, this);
            rcvSelectedDrugs.setAdapter(listbleAdapter);
        }

    }
}