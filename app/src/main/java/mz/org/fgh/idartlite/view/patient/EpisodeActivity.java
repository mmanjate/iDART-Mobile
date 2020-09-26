package mz.org.fgh.idartlite.view.patient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.DialogListener;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.common.ValorSimples;
import mz.org.fgh.idartlite.databinding.CreateActivityEpisodeBindingImpl;
import mz.org.fgh.idartlite.databinding.EpisodeActivityBinding;
import mz.org.fgh.idartlite.databinding.EpisodeActivityBindingImpl;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.EpisodeVM;

import static android.R.*;

public class EpisodeActivity extends BaseActivity implements DialogListener {



    private EpisodeActivityBinding createEpisodeBinding;

    ArrayAdapter<String> stopReasonSpinnerAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        createEpisodeBinding = DataBindingUtil.setContentView(this, R.layout.episode_activity);
        createEpisodeBinding.setViewModel(getRelatedViewModel());

        this.loadSpinnerValues();
        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {

                getRelatedViewModel().setPatient((Patient) bundle.getSerializable("patient"));
               createEpisodeBinding.setPatient(getRelatedViewModel().getPatient());

                if (getRelatedViewModel().getPatient() == null){
                    throw new RuntimeException("Não foi seleccionado um paciente para detalhar.");
                }
                if((Episode) bundle.getSerializable("episode") != null) {
                    getRelatedViewModel().setEpisode((Episode) bundle.getSerializable("episode"));
                    populateEpisodeForEdit();
                    createEpisodeBinding.spnStopReason.setSelection(stopReasonSpinnerAdapter.getPosition(getRelatedViewModel().getEpisode().getStopReason()));

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


    }

    private void loadSpinnerValues() {
        List<ValorSimples> spinnerReasonToStartArray =  new ArrayList<ValorSimples>();
        spinnerReasonToStartArray.add(ValorSimples.fastCreate("Referido De"));
        spinnerReasonToStartArray.add(ValorSimples.fastCreate("Re-Referido De"));

        List<String> spinnerReasonToStopArray =  new ArrayList<String>();
        spinnerReasonToStopArray.add("");
        spinnerReasonToStopArray.add("Referido para mesma US");
        spinnerReasonToStopArray.add("Abandono");
        spinnerReasonToStopArray.add("Óbito");

        ArrayAdapter<ValorSimples> adapter = new ArrayAdapter<ValorSimples>(getApplicationContext(), layout.simple_spinner_item, spinnerReasonToStartArray);

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

        getRelatedViewModel().getEpisode().setEpisodeDate(DateUtilitis.createDate(createEpisodeBinding.editEpisodeDate.getText().toString(),"dd-MM-YYYY"));
       getRelatedViewModel().getEpisode().setNotes(createEpisodeBinding.editTextEpisodeObservation.getText().toString());
       getRelatedViewModel().getEpisode().setPatient(getRelatedViewModel().getPatient());
       getRelatedViewModel().getEpisode().setStartReason(createEpisodeBinding.spnStartReason.getSelectedItem().toString());
       if(!createEpisodeBinding.spnStopReason.getSelectedItem().toString().isEmpty()){
           getRelatedViewModel().getEpisode().setStopReason(createEpisodeBinding.spnStopReason.getSelectedItem().toString());
       }
    }

    public void populateEpisodeForEdit(){


      //  createEpisodeBinding.editEpisodeDate.setText(DateUtilitis.formatToDDMMYYYY(getRelatedViewModel().getEpisode().getEpisodeDate()));
     //   createEpisodeBinding.editTextEpisodeObservation.setText(getRelatedViewModel().getEpisode().getNotes());
      //  getRelatedViewModel().getEpisode().setEpisodeDate(DateUtilitis.createDate(createEpisodeBinding.editEpisodeDate.getText().toString(),"dd-MM-YYYY"));
     //   getRelatedViewModel().getEpisode().setNotes(createEpisodeBinding.editTextEpisodeObservation.getText().toString());
    //    getRelatedViewModel().getEpisode().setPatient(getRelatedViewModel().getPatient());
    //    getRelatedViewModel().getEpisode().setStartReason(createEpisodeBinding.spnStartReason.getSelectedItem().toString());
     //   if(!createEpisodeBinding.spnStopReason.getSelectedItem().toString().isEmpty()){
     //       getRelatedViewModel().getEpisode().setStopReason(createEpisodeBinding.spnStopReason.getSelectedItem().toString());
     //   }
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
        Intent intent = new Intent(EpisodeActivity.this, EpisodeFragment.class);
        startActivity(intent);
    }
}