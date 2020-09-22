package mz.org.fgh.idartlite.view.patient;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityPatientBinding;
import mz.org.fgh.idartlite.databinding.CreateActivityEpisodeBindingImpl;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.EpisodeVM;
import mz.org.fgh.idartlite.viewmodel.PatientVM;

public class CreateEpisodeActivity extends BaseActivity {


    private Spinner spinnerStartReason;

    private Spinner spinnerStopReason;
    private CreateActivityEpisodeBindingImpl createEpisodeBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        createEpisodeBinding = DataBindingUtil.setContentView(this, R.layout.create_activity_episode);
        //setContentView(R.layout.create_activity_episode);
        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {

                getRelatedViewModel().setPatient((Patient) bundle.getSerializable("patient"));
                createEpisodeBinding.setPatient(getRelatedViewModel().getPatient());
                if (getRelatedViewModel().getPatient() == null){
                    throw new RuntimeException("Não foi seleccionado um paciente para detalhar.");
                }
            }
        }

       // Button button = (Button)findViewById(R.id.imageButton);
       // button.setOnClickListener(this);


        List<String> spinnerReasonToStartArray =  new ArrayList<String>();
        spinnerReasonToStartArray.add("Referido De");
        spinnerReasonToStartArray.add("Re-Referido De");

        List<String> spinnerReasonToStopArray =  new ArrayList<String>();
        spinnerReasonToStopArray.add("Referido para mesma US");
        spinnerReasonToStopArray.add("Abandono");
        spinnerReasonToStopArray.add("Óbito");




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerReasonToStartArray);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerReasonToStopArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinnerStartReason);
        sItems.setAdapter(adapter);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinnerStopReason);
        sItems2.setAdapter(adapter2);
    }

    public void createEpisode(View v) throws SQLException, ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");



        EditText dataVisita=findViewById(R.id.editEpisodeDate);

        String dob_var=(dataVisita.getText().toString());

       Date dateObject = formatter.parse(dob_var);

        //Date date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
        Spinner motivoParaIniciar=findViewById(R.id.spinnerStartReason);
        Spinner motivoParaFim=findViewById(R.id.spinnerStopReason);

        EditText notes=findViewById(R.id.editTextEpisodeObservation);

        Episode episode = new Episode();
        episode.setEpisodeDate(dateObject);

        episode.setPatient(getRelatedViewModel().getPatient());
        episode.setStartReason(motivoParaIniciar.getSelectedItem().toString());
        episode.setStopReason(motivoParaFim.getSelectedItem().toString());
        episode.setNotes(notes.getText().toString());

        getRelatedViewModel().createEpisode(episode);
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(EpisodeVM.class);
    }

    @Override
    public EpisodeVM getRelatedViewModel() {
        return (EpisodeVM) super.getRelatedViewModel();
    }
}