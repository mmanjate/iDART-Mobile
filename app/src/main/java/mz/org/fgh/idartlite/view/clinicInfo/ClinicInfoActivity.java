package mz.org.fgh.idartlite.view.clinicInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityClinicInfoBinding;
import mz.org.fgh.idartlite.databinding.EpisodeActivityBinding;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.view.episode.EpisodeActivity;
import mz.org.fgh.idartlite.viewmodel.clinicInfo.ClinicInfoVM;
import mz.org.fgh.idartlite.viewmodel.episode.EpisodeVM;

public class ClinicInfoActivity extends BaseActivity  implements IDialogListener {

    private ActivityClinicInfoBinding createClinicInfoBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createClinicInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_clinic_info);
        createClinicInfoBinding.setViewModel(getRelatedViewModel());


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

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {

                getRelatedViewModel().setPatient((Patient) bundle.getSerializable("patient"));

                if(getApplicationStep().isApplicationStepEdit() || getApplicationStep().isApplicationStepDisplay()){

                    getRelatedViewModel().setClinicInformation((ClinicInformation) bundle.getSerializable("clinicInformation"));
                    if(getApplicationStep().isApplicationStepDisplay()){

                      disableFieldsToView();

                        //   this.getRelatedViewModel().setAge(String.valueOf(getRelatedViewModel().getPatient().getAge()));
                    }
                 //   this.getRelatedViewModel().setAge(String.valueOf(getRelatedViewModel().getPatient().getAge()));
                }
                else {

                    //   createClinicInfoBinding.setPatient(getRelatedViewModel().getPatient());
                    this.changeApplicationStepToCreate();
                    if (getRelatedViewModel().getPatient() == null) {
                        throw new RuntimeException("Não foi seleccionado um paciente para detalhar.");
                    }
                }
            }
        }


        createClinicInfoBinding.registerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ClinicInfoActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        getRelatedViewModel().setRegisterDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        createClinicInfoBinding.registerDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(ClinicInfoActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            getRelatedViewModel().setRegisterDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        createClinicInfoBinding.editStartTreatmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ClinicInfoActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        getRelatedViewModel().setTreatmentStartDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        createClinicInfoBinding.editStartTreatmentDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(ClinicInfoActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            getRelatedViewModel().setTreatmentStartDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        createClinicInfoBinding.weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {



                    double weight= Utilities.stringHasValue(createClinicInfoBinding.weight.getText().toString()) ? Double.parseDouble(createClinicInfoBinding.weight.getText().toString()) : 0;

                    double height=getRelatedViewModel().getClinicInformation().getHeight();

                    if(height !=0){

                        double imc= weight/(height*height);

                        getRelatedViewModel().setImc(String.valueOf(imc));
                    }
                }
           }
        });

        createClinicInfoBinding.height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {

                    double height= Utilities.stringHasValue(createClinicInfoBinding.height.getText().toString()) ? Double.parseDouble(createClinicInfoBinding.height.getText().toString()) : 0;

                    double weight=getRelatedViewModel().getClinicInformation().getWeight();

                    if(weight !=0){

                        double imc= weight/(height*height);

                        getRelatedViewModel().setImc(String.valueOf(imc));
                    }
                }
            }
        });

    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ClinicInfoVM.class);
    }

    @Override
    public ClinicInfoVM getRelatedViewModel() {
        return (ClinicInfoVM) super.getRelatedViewModel();
    }

    private void disableFieldsToView(){
        createClinicInfoBinding.registerDate.setEnabled(false);
        createClinicInfoBinding.registerDate.setClickable(false);
        createClinicInfoBinding.weight.setEnabled(false);
        createClinicInfoBinding.height.setEnabled(false);
        createClinicInfoBinding.imc.setEnabled(false);
        createClinicInfoBinding.distole.setEnabled(false);
        createClinicInfoBinding.systole.setEnabled(false);
        createClinicInfoBinding.radioTpiYes1.setEnabled(false);
        createClinicInfoBinding.radioTpiNo1.setEnabled(false);
        createClinicInfoBinding.radioTbYes1.setEnabled(false);
        createClinicInfoBinding.radioTbNo1.setEnabled(false);
        createClinicInfoBinding.radioCoughYes.setEnabled(false);
        createClinicInfoBinding.radioCoughNo.setEnabled(false);
        createClinicInfoBinding.radioFeverNo.setEnabled(false);
        createClinicInfoBinding.radioFeverYes.setEnabled(false);
        createClinicInfoBinding.radioWeightYes.setEnabled(false);
        createClinicInfoBinding.radioWeightNo.setEnabled(false);
        createClinicInfoBinding.radioSweatingYes.setEnabled(false);
        createClinicInfoBinding.radioSweatingNo.setEnabled(false);
        createClinicInfoBinding.radioParentYes.setEnabled(false);
        createClinicInfoBinding.radioParentNo.setEnabled(false);
        createClinicInfoBinding.radioTbReferedYes.setEnabled(false);
        createClinicInfoBinding.radioTbReferedNo.setEnabled(false);
        createClinicInfoBinding.radioPregnancyYes.setEnabled(false);
        createClinicInfoBinding.radioPregnancyNo.setEnabled(false);
        createClinicInfoBinding.radioMenstruationYes.setEnabled(false);
        createClinicInfoBinding.radioMenstruationNo.setEnabled(false);
        createClinicInfoBinding.radioPatientCameYes.setEnabled(false);
        createClinicInfoBinding.radioPatientCameNo.setEnabled(false);
        createClinicInfoBinding.radioWeightNo.setEnabled(false);
        createClinicInfoBinding.wrongDateNumber.setEnabled(false);
        createClinicInfoBinding.patientHoursWrongYes.setEnabled(false);
        createClinicInfoBinding.patientHoursWrongNo.setEnabled(false);
        createClinicInfoBinding.daysWithoutMedicine.setEnabled(false);
        createClinicInfoBinding.editTextMotives.setEnabled(false);
        createClinicInfoBinding.adversityReactionYes.setEnabled(false);
        createClinicInfoBinding.adversityReactionNo.setEnabled(false);
        createClinicInfoBinding.editTextAdverseReaction.setEnabled(false);
        createClinicInfoBinding.patientReferedYes.setEnabled(false);
        createClinicInfoBinding.patientReferedNo.setEnabled(false);
        createClinicInfoBinding.saveAndContinue.setVisibility(View.GONE);

    }


    public void changeFormSectionVisibility(View view) {
        if (view.equals(createClinicInfoBinding.vitalSigns)){
            if (createClinicInfoBinding.vitalSignsList.getVisibility() == View.VISIBLE){
                switchLayout();
                createClinicInfoBinding.ibtnVitalSigns.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.vitalSignsList);
            }else {
                switchLayout();
                createClinicInfoBinding.ibtnVitalSigns.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.vitalSignsList);
            }
        }
        else if (view.equals(createClinicInfoBinding.tbScreeningDetails)){
            if (createClinicInfoBinding.tbScreeningList.getVisibility() == View.VISIBLE){
                switchLayout();
                createClinicInfoBinding.ibtnTbScreeningDetails1.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.tbScreeningList);
                createClinicInfoBinding.txtTbScreening.setVisibility(View.GONE);
                createClinicInfoBinding.tbScreeningQuestion.setVisibility(View.GONE);
            }else {
                createClinicInfoBinding.ibtnTbScreeningDetails1.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.tbScreeningList);
                createClinicInfoBinding.txtTbScreening.setVisibility(View.VISIBLE);
                createClinicInfoBinding.tbScreeningQuestion.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
        else if (view.equals(createClinicInfoBinding.pregnancyScreening)){
            if (createClinicInfoBinding.pregnancyScreeningList.getVisibility() == View.VISIBLE){
                switchLayout();
                createClinicInfoBinding.ibtnPregnancyScreeningDetails1.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.pregnancyScreeningList);
                createClinicInfoBinding.pregnancyScreeningQuestion.setVisibility(View.GONE);
            }else {
                createClinicInfoBinding.ibtnPregnancyScreeningDetails1.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.pregnancyScreeningList);
                createClinicInfoBinding.pregnancyScreeningQuestion.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }

        else if (view.equals(createClinicInfoBinding.monitoringReinforcementAdesaoDetails)){
            if (createClinicInfoBinding.monitoringList.getVisibility() == View.VISIBLE){
                createClinicInfoBinding.ibtnMonitoringReinforcmentDetails.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.monitoringList);
                createClinicInfoBinding.monitoringReinforcmentDetailsList.setVisibility(View.GONE);
                switchLayout();
            }else {
                createClinicInfoBinding.ibtnMonitoringReinforcmentDetails.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.monitoringList);
                createClinicInfoBinding.monitoringReinforcmentDetailsList.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
        else if(view.equals(createClinicInfoBinding.adversityReaction)){
            if (createClinicInfoBinding.linearMonitoringAdversity.getVisibility() == View.VISIBLE){
                createClinicInfoBinding.ibtnAdverstiryReaction.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.linearMonitoringAdversity);
                createClinicInfoBinding.adverseReactionQuestion.setVisibility(View.GONE);
                switchLayout();
            }else {
                createClinicInfoBinding.ibtnAdverstiryReaction.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.linearMonitoringAdversity);
                createClinicInfoBinding.adverseReactionQuestion.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
    }


    //Handling Action Bar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.about:
                //If this activity started from other activity
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchLayout(){
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setAddressDataVisible(!getRelatedViewModel().isAddressDataVisible());
    }


   /* public String getAnswerChecked(){


        String error="Por Favor Verifique se Respondeu a todas as Perguntas do Questionario";
        int emptyRadio=-1;

        if(createClinicInfoBinding.tpiAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.tbAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.coughAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.feverAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.weightAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.sweatingAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.parentAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.tbReferedAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.pregnancyAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.menstruationAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.patientCameAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.patientHoursWrongAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.adversityReactionAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.patientReferedAnswers.getCheckedRadioButtonId()==emptyRadio) return error;

        return "";

    }*/


    public String getAnswerChecked(){


        String error="Por Favor Verifique se Respondeu a todas as Perguntas do Questionario";
        int emptyRadio=-1;

        if(createClinicInfoBinding.tpiAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.tbAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.coughAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.feverAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.weightAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.sweatingAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.parentAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.tbReferedAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.pregnancyAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.menstruationAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.patientCameAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.patientHoursWrongAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.adversityReactionAnswers.getCheckedRadioButtonId()==emptyRadio) return error;
        if(createClinicInfoBinding.patientReferedAnswers.getCheckedRadioButtonId()==emptyRadio) return error;

        return "";

    }


    @Override
    public void doOnConfirmed() {
        finish();
    }

    @Override
    public void doOnDeny() {

    }
}