package mz.org.fgh.idartlite.view.patientPanel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.common.ListableSpinnerAdapter;
import mz.org.fgh.idartlite.databinding.ActivityAddNewPatientBinding;
import mz.org.fgh.idartlite.databinding.ActivityPatientBinding;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Province;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.SimpleValue;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.episode.EpisodeActivity;
import mz.org.fgh.idartlite.view.prescription.PrescriptionActivity;
import mz.org.fgh.idartlite.viewmodel.patient.AddPatientVM;
import mz.org.fgh.idartlite.viewmodel.prescription.PrescriptionVM;

public class AddNewPatientActivity extends BaseActivity implements IDialogListener {

    private ActivityAddNewPatientBinding addNewPatientBinding;

    private ListableSpinnerAdapter provinceAdapter;
    private ListableSpinnerAdapter districtAdapter;
    private ListableSpinnerAdapter identifierTypeAdapter;
    private ListableSpinnerAdapter genderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNewPatientBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_patient);

        addNewPatientBinding.addressDataLyt.setVisibility(View.GONE);
        addNewPatientBinding.clinicDataLyt.setVisibility(View.GONE);
        addNewPatientBinding.ibtnClinic.animate().setDuration(200).rotation(180);
        addNewPatientBinding.ibtnAddress.animate().setDuration(200).rotation(180);

      // getApplicationStep().setCode(ApplicationStep.STEP_CREATE);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
               // getRelatedViewModel().getPrescription().setPatient((Patient) bundle.getSerializable("patient"));

                if(getApplicationStep().isApplicationStepEdit()){
                    getRelatedViewModel().setPatient((Patient) bundle.getSerializable("patient"));
                    getRelatedViewModel().setEpisode(getRelatedViewModel().getPatient().getReferenceEpisode());
                    this.getRelatedViewModel().setAge(String.valueOf(getRelatedViewModel().getPatient().getAge()));
                }

            }else {
                throw new RuntimeException(getString(R.string.no_patient_or_prescription));
            }
        }


        addNewPatientBinding.birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPatientActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        getRelatedViewModel().setBirthDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                        getRelatedViewModel().setAge(String.valueOf(getRelatedViewModel().getPatient().getAge()));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        addNewPatientBinding.birthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPatientActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            getRelatedViewModel().setBirthDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                            getRelatedViewModel().setAge(String.valueOf(getRelatedViewModel().getPatient().getAge()));

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }

            }

        });



        addNewPatientBinding.tarvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPatientActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        getRelatedViewModel().setInitalDateTarv(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));


                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        addNewPatientBinding.tarvDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPatientActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            getRelatedViewModel().setInitalDateTarv(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));


                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        addNewPatientBinding.admissionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPatientActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        getRelatedViewModel().setAdmissionDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        addNewPatientBinding.admissionDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPatientActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            getRelatedViewModel().setAdmissionDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
    });

        addNewPatientBinding.numberAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {

                   int age= Integer.parseInt(addNewPatientBinding.numberAge.getText().toString());

                   int ageDays=age*365;
                    getRelatedViewModel().setBirthDate(DateUtilities.getDateOfPreviousDays(DateUtilities.getCurrentDate(),ageDays));

                }
            }
        });


        addNewPatientBinding.setViewModel(getRelatedViewModel());
        getRelatedViewModel().setInitialDataVisible(true);
        getRelatedViewModel().setAddressDataVisible(false);
        populateForm();

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

    public void loadDistrcitAdapter(){

        districtAdapter = new ListableSpinnerAdapter(AddNewPatientActivity.this, R.layout.simple_auto_complete_item, getRelatedViewModel().getDistricts());
        addNewPatientBinding.spnDistrict.setAdapter(districtAdapter);
        addNewPatientBinding.setDistrictAdapter(districtAdapter);
    }


    public void changeFormSectionVisibility(View view) {
        if (view.equals(addNewPatientBinding.demograficData)){
            if (addNewPatientBinding.demograficDataLyt.getVisibility() == View.VISIBLE){
                switchLayout();
                addNewPatientBinding.ibtnInitialData.animate().setDuration(200).rotation(180);
                Utilities.collapse(addNewPatientBinding.demograficDataLyt);
            }else {
                switchLayout();
                addNewPatientBinding.ibtnInitialData.animate().setDuration(200).rotation(0);
                Utilities.expand(addNewPatientBinding.demograficDataLyt);
            }
        }else if (view.equals(addNewPatientBinding.addressData)){
            if (addNewPatientBinding.addressDataLyt.getVisibility() == View.VISIBLE){
                switchLayout();
                addNewPatientBinding.ibtnAddress.animate().setDuration(200).rotation(180);
                Utilities.collapse(addNewPatientBinding.addressDataLyt);
            }else {
                addNewPatientBinding.ibtnAddress.animate().setDuration(200).rotation(0);
                Utilities.expand(addNewPatientBinding.addressDataLyt);
                switchLayout();
            }
        }else if (view.equals(addNewPatientBinding.clinicInfo)){
            if (addNewPatientBinding.clinicDataLyt.getVisibility() == View.VISIBLE){
                addNewPatientBinding.ibtnClinic.animate().setDuration(200).rotation(180);
                Utilities.collapse(addNewPatientBinding.clinicDataLyt);
                switchLayout();
            }else {
                addNewPatientBinding.ibtnClinic.animate().setDuration(200).rotation(0);
                Utilities.expand(addNewPatientBinding.clinicDataLyt);
                switchLayout();
            }
        }
    }

    private void populateForm() {
        try {
            List<Province> provinces = new ArrayList<>();
            provinces.add(new Province());
            provinces.addAll(getRelatedViewModel().getAllProvinces());

            provinceAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, provinces);
            addNewPatientBinding.spnProvince.setAdapter(provinceAdapter);
            addNewPatientBinding.setProvinceAdapter(provinceAdapter);

            identifierTypeAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, getRelatedViewModel().getIdentifierTypes());
            addNewPatientBinding.spnIdentifierType.setAdapter(identifierTypeAdapter);
            addNewPatientBinding.setIdentifierTypeAdapter(identifierTypeAdapter);

            addNewPatientBinding.spnIdentifierType.setEnabled(false);
            addNewPatientBinding.spnIdentifierType.setClickable(false);

            genderAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, getRelatedViewModel().getGenders());
            addNewPatientBinding.spnGender.setAdapter(genderAdapter);
            addNewPatientBinding.setGenderAdapter(genderAdapter);

        } catch (Exception e) {
            Utilities.displayAlertDialog(AddNewPatientActivity.this, getString(R.string.error_loading_form_data)+e.getMessage());
            e.printStackTrace();
        }
    }

    private void switchLayout(){
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setAddressDataVisible(!getRelatedViewModel().isAddressDataVisible());
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(AddPatientVM.class);
    }

    @Override
    public AddPatientVM getRelatedViewModel() {
        return (AddPatientVM) super.getRelatedViewModel();
    }

    @Override
    public void doOnConfirmed() {

        if(getApplicationStep().isApplicationStepEdit()){
           /* Map<String, Object> params = new HashMap<>();
            params.put("patient",  getRelatedViewModel().getPatient());
            params.put("user", getCurrentUser());
            params.put("clinic", getCurrentClinic());
            params.put("requestedFragment", PatientPanelActivity.FRAGMENT_CODE_PRESCRIPTION);
            nextActivityFinishingCurrent(PatientPanelActivity.class,params);*/
            finish();
        }
        else {

            Map<String, Object> params = new HashMap<>();
            params.put("patient", getRelatedViewModel().getPatient());
            params.put("user", getCurrentUser());
            params.put("clinic", getCurrentClinic());

            nextActivity(PatientPanelActivity.class,params);
            finish();
        }

    }

    @Override
    public void doOnDeny() {

    }
}