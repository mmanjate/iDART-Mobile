package mz.org.fgh.idartlite.view.dispense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.DatePicker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.ListbleRecycleViewAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.databinding.ActivityCreateDispenseBinding;
import mz.org.fgh.idartlite.databinding.ActivityReturnDispenseBinding;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.ReturnedDrug;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.view.patientPanel.AddNewPatientActivity;
import mz.org.fgh.idartlite.view.patientPanel.DispenseFragment;
import mz.org.fgh.idartlite.view.patientPanel.PatientPanelActivity;
import mz.org.fgh.idartlite.view.patientPanel.PrescriptionFragment;
import mz.org.fgh.idartlite.view.prescription.PrescriptionActivity;
import mz.org.fgh.idartlite.viewmodel.dispense.ReturnDispenseVM;
import mz.org.fgh.idartlite.viewmodel.patient.AddPatientVM;

public class ReturnDispenseActivity extends BaseActivity implements IDialogListener {

    private ActivityReturnDispenseBinding activityReturnDispenseBinding;

    private RecyclerView rcvDispensedDrugs;

    private ListbleRecycleViewAdapter listbleRecycleViewAdapter;

    private Integer positionRemoved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReturnDispenseBinding = DataBindingUtil.setContentView(this, R.layout.activity_return_dispense);

        rcvDispensedDrugs = activityReturnDispenseBinding.rcvSelectedDrugs;

        if(!getApplicationStep().isApplicationStepRemove()) {
            getApplicationStep().setCode(ApplicationStep.STEP_CREATE);
        }
        Intent intent = this.getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                getRelatedViewModel().setDispense((Dispense) bundle.getSerializable("dispense"));
            }

            positionRemoved= (Integer) bundle.getSerializable("positionRemoved");
        }




        activityReturnDispenseBinding.returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReturnDispenseActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        getRelatedViewModel().setReturnDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        activityReturnDispenseBinding.returnDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(ReturnDispenseActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            getRelatedViewModel().setReturnDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));


                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }

            }

        });


        getRelatedViewModel().populateFormData();


        populateRecyclerView();


        activityReturnDispenseBinding.setViewModel(getRelatedViewModel());

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
    }

    private void populateRecyclerView(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcvDispensedDrugs.setLayoutManager(mLayoutManager);
        rcvDispensedDrugs.setItemAnimator(new DefaultItemAnimator());
        rcvDispensedDrugs.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

        listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvDispensedDrugs,getRelatedViewModel().getReturnedDrugs(), this);
        rcvDispensedDrugs.setAdapter(listbleRecycleViewAdapter);
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ReturnDispenseVM.class);
    }

    @Override
    public ReturnDispenseVM getRelatedViewModel() {
        return (ReturnDispenseVM) super.getRelatedViewModel();
    }


    public void doAfterReturnSave(){

        if (getApplicationStep().isApplicationStepSave()) {
            Map<String, Object> params = new HashMap<>();
            params.put("patient", getRelatedViewModel().getDispense().getPrescription().getPatient());
            params.put("user", getCurrentUser());
            params.put("clinic", getCurrentClinic());
            params.put("step", ApplicationStep.STEP_CREATE);
            nextActivityFinishingCurrent(PrescriptionActivity.class, params);
        }
        else if(getApplicationStep().isApplicationStepRemove()){
            Map<String, Object> params = new HashMap<>();
            params.put("patient",getRelatedViewModel().getDispense().getPrescription().getPatient());
            params.put("user", getCurrentUser());
            params.put("clinic", getCurrentClinic());
            params.put("requestedFragment", DispenseFragment.FRAGMENT_CODE_DISPENSE);
            params.put("positionRemoved", positionRemoved);
            nextActivityFinishingCurrent(PatientPanelActivity.class,params);
        }
    }

    @Override
    public void doOnConfirmed() {
        if (getApplicationStep().isApplicationStepSave() || getApplicationStep().isApplicationStepRemove()) {
            doAfterReturnSave();
        }else {
            doOnDeny();
        }
    }

    @Override
    public void doOnDeny() {
        Map<String, Object> params = new HashMap<>();
        params.put("patient",getRelatedViewModel().getDispense().getPrescription().getPatient());
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        params.put("requestedFragment", DispenseFragment.FRAGMENT_CODE_DISPENSE);
        nextActivityFinishingCurrent(PatientPanelActivity.class,params);
    }
}