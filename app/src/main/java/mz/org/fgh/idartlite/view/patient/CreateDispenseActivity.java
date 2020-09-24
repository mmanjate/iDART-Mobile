package mz.org.fgh.idartlite.view.patient;

import androidx.databinding.Bindable;
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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.common.ListbleAdapter;
import mz.org.fgh.idartlite.databinding.ActivityCreateDispenseBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.service.PrescriptionService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.DispenseVM;
import mz.org.fgh.idartlite.viewmodel.EpisodeVM;

public class CreateDispenseActivity extends BaseActivity {

    private ActivityCreateDispenseBinding activityCreateDispenseBinding;

    private List<Listble> selectedDrugs;

    private RecyclerView rcvSelectedDrugs;

    private ListbleAdapter listbleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityCreateDispenseBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_dispense);

        rcvSelectedDrugs = activityCreateDispenseBinding.rcvSelectedDrugs;

        activityCreateDispenseBinding.drugsDataLyt.setVisibility(View.GONE);
        getRelatedViewModel().setInitialDataVisible(true);
        getRelatedViewModel().setDrugDataVisible(false);

        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                getRelatedViewModel().getDispense().setPrescription((Prescription) bundle.getSerializable("prescription"));
                activityCreateDispenseBinding.setViewModel(getRelatedViewModel());

                if (getRelatedViewModel().getDispense().getPrescription() == null) {
                    // Carregar a ultima prescricao
                    throw new RuntimeException("Carregar ultima prescricao");
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

        activityCreateDispenseBinding.spnDrugs.setAdapter(adapter);

        activityCreateDispenseBinding.dispenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateDispenseActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        activityCreateDispenseBinding.dispenseDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        activityCreateDispenseBinding.nextPickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateDispenseActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        activityCreateDispenseBinding.nextPickupDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        activityCreateDispenseBinding.initialData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);

            }
        });
        activityCreateDispenseBinding.txvDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);

            }
        });

        activityCreateDispenseBinding.imvAddSelectedDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDrugs == null) selectedDrugs = new ArrayList<>();

                if (activityCreateDispenseBinding.spnDrugs.getSelectedItem() != null) {
                    Listble listble = (Listble) activityCreateDispenseBinding.spnDrugs.getSelectedItem();

                    if (!selectedDrugs.contains(listble)) {
                        listble.setListPosition(selectedDrugs.size() + 1);
                        selectedDrugs.add(listble);
                        Collections.sort(selectedDrugs);

                        displaySelectedDrugs();
                    } else {

                        Utilities.displayAlertDialog(CreateDispenseActivity.this, getString(R.string.drug_data_duplication_msg)).show();
                    }

                }
            }
        });

        activityCreateDispenseBinding.saveAndContinue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            createDispense();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }

    private void createDispense() throws ParseException, SQLException {

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date dispenseDate = formatter.parse(this.activityCreateDispenseBinding.dispenseDate.getText().toString());
        String dispenseMonths = this.activityCreateDispenseBinding.dispenseMonths.getText().toString();
        Date nextPickUpDate = formatter.parse(this.activityCreateDispenseBinding.nextPickupDate.getText().toString());

        Dispense dispense = new Dispense();

        Prescription prescription = getRelatedViewModel().getLastPatientPrescription(getRelatedViewModel().getDispense().getPrescription().getPatient());

        //Fazer as buscas corretas
        Clinic clinic = new Clinic();
        clinic.setId(1);
        Stock stock = new Stock();
        stock.setId(1);

        dispense.setNextPickupDate(nextPickUpDate);
        dispense.setPickupDate(dispenseDate);
        dispense.setSupply(4);
        dispense.setSyncStatus("R");
        dispense.setPrescription(prescription);
        dispense.setUuid("1");

        getRelatedViewModel().create(dispense);

        int quantitySupplied;


        for (Listble dd : this.selectedDrugs
        ) {

            Drug drugSelected = (Drug) dd;

            quantitySupplied = Integer.valueOf(dispenseMonths) * drugSelected.getPackSize();

            DispensedDrug dispensedDrug = new DispensedDrug();
            dispensedDrug.setQuantitySupplied(quantitySupplied);
            dispensedDrug.setStock(stock);
            dispensedDrug.setSyncStatus("R");
            dispensedDrug.setDispense(dispense);

            getRelatedViewModel().createDispensedDrug(dispensedDrug);

        }

        Utilities.displayAlertDialog(getApplicationContext(), "" + " Dispensa Criada Com Sucesso");

    }

    private void populateForm() {

    }

    private void changeVisibilityToInitialData(View view) {
        if (view.equals(activityCreateDispenseBinding.initialData)) {
            if (activityCreateDispenseBinding.initialDataLyt.getVisibility() == View.VISIBLE) {
                activityCreateDispenseBinding.initialDataLyt.setVisibility(View.GONE);
                switchLayout();
            } else {
                activityCreateDispenseBinding.initialDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        } else if (view.equals(activityCreateDispenseBinding.txvDrugs)) {
            if (activityCreateDispenseBinding.drugsDataLyt.getVisibility() == View.VISIBLE) {
                activityCreateDispenseBinding.drugsDataLyt.setVisibility(View.GONE);
                switchLayout();
            } else {
                activityCreateDispenseBinding.drugsDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
    }

    private void switchLayout() {
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setDrugDataVisible(!getRelatedViewModel().isDrugDataVisible());
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DispenseVM.class);
    }

    @Override
    public DispenseVM getRelatedViewModel() {
        return (DispenseVM) super.getRelatedViewModel();
    }

    private void displaySelectedDrugs() {
        if (listbleAdapter != null) {
            listbleAdapter.notifyDataSetChanged();
        } else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

            listbleAdapter = new ListbleAdapter(rcvSelectedDrugs, this.selectedDrugs, this);
            rcvSelectedDrugs.setAdapter(listbleAdapter);
        }

    }
}