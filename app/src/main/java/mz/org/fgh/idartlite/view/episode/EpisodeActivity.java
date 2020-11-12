package mz.org.fgh.idartlite.view.episode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.EpisodeActivityBinding;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.SimpleValue;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.viewmodel.episode.EpisodeVM;

import static android.R.layout;

public class EpisodeActivity extends BaseActivity implements IDialogListener {

    private EpisodeActivityBinding createEpisodeBinding;

    ArrayAdapter<String> stopReasonSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        createEpisodeBinding = DataBindingUtil.setContentView(this, R.layout.episode_activity);
        createEpisodeBinding.setViewModel(getRelatedViewModel());

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

        this.loadSpinnerValues();
        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {

                getRelatedViewModel().setPatient((Patient) bundle.getSerializable("patient"));
               createEpisodeBinding.setPatient(getRelatedViewModel().getPatient());
             this.changeApplicationStepToCreate();
                if (getRelatedViewModel().getPatient() == null){
                    throw new RuntimeException("Não foi seleccionado um paciente para detalhar.");
                }
                if((Episode) bundle.getSerializable("episode") != null) {
                    getRelatedViewModel().setEpisode((Episode) bundle.getSerializable("episode"));
                    createEpisodeBinding.spnStopReason.setSelection(stopReasonSpinnerAdapter.getPosition(getRelatedViewModel().getEpisode().getStopReason()));

                }

                if(bundle.getSerializable("viewDetails")!= null && (boolean) bundle.getSerializable("viewDetails") == true) {
                    this.changeApplicationStepToDisplay();
                    disableFieldsToView();
                }
            }
        }

        createEpisodeBinding.setEpisode(getRelatedViewModel().getEpisode());

        createEpisodeBinding.editEpisodeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EpisodeActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        createEpisodeBinding.editEpisodeDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        createEpisodeBinding.editEpisodeDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(EpisodeActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            createEpisodeBinding.editEpisodeDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });
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


    private void disableFieldsToView(){
        createEpisodeBinding.spnStopReason.setEnabled(false);
        createEpisodeBinding.spnStopReason.setClickable(false);
        createEpisodeBinding.editTextEpisodeObservation.setEnabled(false);
        createEpisodeBinding.editTextEpisodeObservation.setClickable(false);
        createEpisodeBinding.editEpisodeDate.setEnabled(false);
        createEpisodeBinding.editEpisodeDate.setClickable(false);
        createEpisodeBinding.saveAndContinue.setVisibility(View.GONE);
    }

    private void loadSpinnerValues() {
        List<SimpleValue> spinnerReasonToStartArray =  new ArrayList<SimpleValue>();
        spinnerReasonToStartArray.add(SimpleValue.fastCreate("Referido De"));
        spinnerReasonToStartArray.add(SimpleValue.fastCreate("Re-Referido De"));

        List<String> spinnerReasonToStopArray =  new ArrayList<String>();
        spinnerReasonToStopArray.add("");
        spinnerReasonToStopArray.add("Referido para mesma US");
     /*   spinnerReasonToStopArray.add("Abandono");
        spinnerReasonToStopArray.add("Parametros Laboratoriais");
        spinnerReasonToStopArray.add("Gravidez");
        spinnerReasonToStopArray.add("Tuberculose");
        spinnerReasonToStopArray.add("Falha de adesão");
        spinnerReasonToStopArray.add("A pedido do paciente");
        spinnerReasonToStopArray.add("Óbito");*/

        ArrayAdapter<SimpleValue> adapter = new ArrayAdapter<SimpleValue>(getApplicationContext(), layout.simple_spinner_item, spinnerReasonToStartArray);

        stopReasonSpinnerAdapter = new ArrayAdapter<String>(
                this, layout.simple_spinner_item, spinnerReasonToStopArray);

        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);

        stopReasonSpinnerAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        createEpisodeBinding.spnStartReason.setAdapter(adapter);
        createEpisodeBinding.spnStartReason.setEnabled(false);
        createEpisodeBinding.spnStartReason.setClickable(false);
        createEpisodeBinding.spnStopReason.setAdapter(stopReasonSpinnerAdapter);

    }

    public void populateEpisode(){

        if(createEpisodeBinding.editEpisodeDate.getText().length() != 0) {
         getRelatedViewModel().getEpisode().setEpisodeDate(DateUtilities.createDate(createEpisodeBinding.editEpisodeDate.getText().toString(), DateUtilities.DATE_FORMAT));
        }
       getRelatedViewModel().getEpisode().setNotes(createEpisodeBinding.editTextEpisodeObservation.getText().toString());
       getRelatedViewModel().getEpisode().setPatient(getRelatedViewModel().getPatient());
       getRelatedViewModel().getEpisode().setStartReason(createEpisodeBinding.spnStartReason.getSelectedItem().toString());
       if(!createEpisodeBinding.spnStopReason.getSelectedItem().toString().isEmpty()){
           getRelatedViewModel().getEpisode().setStopReason(createEpisodeBinding.spnStopReason.getSelectedItem().toString());
       }
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(EpisodeVM.class);
    }

    @Override
    public EpisodeVM getRelatedViewModel() {
        return (EpisodeVM) super.getRelatedViewModel();
    }

    public Patient getPatient(){
        return getRelatedViewModel().getPatient();
    }

    @Override
    public void doOnConfirmed() {
/*        Map<String, Object> params = new HashMap<>();
        params.put("patient", this.getPatient());
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        params.put("requestedFragment", EpisodeFragment.FRAGMENT_CODE_EPISODE);
        nextActivity(PatientActivity.class,params);*/
        finish();
    }

    @Override
    public void doOnDeny() {
    }
}