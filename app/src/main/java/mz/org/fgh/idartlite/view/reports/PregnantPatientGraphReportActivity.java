package mz.org.fgh.idartlite.view.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HICSSObject;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIItems;
import com.highsoft.highcharts.common.hichartsclasses.HILabels;
import com.highsoft.highcharts.common.hichartsclasses.HIMarker;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPie;
import com.highsoft.highcharts.common.hichartsclasses.HISpline;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.clinicInfo.PregnantPatientReportVM;
import mz.org.fgh.idartlite.viewmodel.report.DispenseDrugGraphStatisticReportVM;
import mz.org.fgh.idartlite.viewmodel.report.PregnantPatientsGraphReportVM;

public class PregnantPatientGraphReportActivity extends BaseActivity {

    private String start;
    private String end;
    private HIChartView chartView;
    private EditText edtStart;
    private EditText edtEnd;
    //private String reportType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnant_patient_graph_report);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().setReportType((String) bundle.getSerializable(ClinicInformation.CLINIC_INFO_STATUS));
            }
        }

        chartView = (HIChartView) findViewById(R.id.hc_view);

        chartView.setVisibility(View.GONE);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        edtStart = findViewById(R.id.start);
        edtEnd = findViewById(R.id.end);
        ImageView search = findViewById(R.id.buttonSearch);

        edtStart.setOnClickListener(view -> {
            int mYear, mMonth, mDay;

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(PregnantPatientGraphReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
                edtStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                start =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                getRelatedViewModel().getSearchParams().setStartdate(DateUtilities.createDate(start, DateUtilities.DATE_FORMAT));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        edtEnd.setOnClickListener(view -> {
            int mYear, mMonth, mDay;

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(PregnantPatientGraphReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
                edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                getRelatedViewModel().getSearchParams().setEndDate(DateUtilities.createDateWithTime(end, DateUtilities.END_DAY_TIME, DateUtilities.DATE_TIME_FORMAT));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        edtStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PregnantPatientGraphReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
                        edtStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        start =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getRelatedViewModel().getSearchParams().setStartdate(DateUtilities.createDate(start, DateUtilities.DATE_FORMAT));
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        edtEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PregnantPatientGraphReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
                        edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getRelatedViewModel().getSearchParams().setEndDate(DateUtilities.createDateWithTime(end, DateUtilities.END_DAY_TIME, DateUtilities.DATE_TIME_FORMAT));
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        search.setOnClickListener(v -> {
        /*    if (getRelatedViewModel().isReportGenerationOnProgress()){
                Utilities.displayAlertDialog(PregnantPatientGraphReportActivity.this, "Por favor aguarde o resultado do relatório já solicitado.").show();
                return;
            }*/
            if (!Utilities.stringHasValue(start) || !Utilities.stringHasValue(end) ){
                Utilities.displayAlertDialog(PregnantPatientGraphReportActivity.this, "Por favor indicar o período por analisar!").show();
            }else
            if (DateUtilities.dateDiff(DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(PregnantPatientGraphReportActivity.this, "A data inicio deve ser menor que a data fim.").show();
            }else
            if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT)) < 0){
                Utilities.displayAlertDialog(PregnantPatientGraphReportActivity.this, "A data inicio deve ser menor que a data corrente.").show();
            }
            else
            if ((int) DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(PregnantPatientGraphReportActivity.this, "A data fim deve ser menor que a data corrente.").show();
            }else {
              //  getRelatedViewModel().setReportGenerationStarted();
                getRelatedViewModel().initSearch();
            }
        });
    }


    public void generateGraph(List<ClinicInformation> clinicInformations) {
        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText(getRelatedViewModel().getReportTitle());
        options.setTitle(title);

        List<String> categories = new ArrayList<>();

        for (ClinicInformation information : clinicInformations){
            if (!categories.contains(information.getPatient().getEpisodes1().iterator().next().getSanitaryUnit())) {
                categories.add(information.getPatient().getEpisodes1().iterator().next().getSanitaryUnit());
            }
        }

        HIXAxis xaxis = new HIXAxis();
        xaxis.setCategories(new ArrayList<>(categories));
        options.setXAxis(new ArrayList<>(Collections.singletonList(xaxis)));

        final HIYAxis hiyAxis = new HIYAxis();
        hiyAxis.setMin(0);
        hiyAxis.setTitle(new HITitle());
        hiyAxis.getTitle().setText("Número");
        options.setYAxis(new ArrayList(){{add(hiyAxis);}});

        HIColumn cdTotal = new HIColumn();
        cdTotal.setName("Total Rastreado");
        Number[] totalData = new Number[categories.size()];

        HIColumn cdSuspeito = new HIColumn();
        cdSuspeito.setName("Pacientes Gravidas");
        Number[] suspectData = new Number[categories.size()];


        int countTotal;
        int countGravidas;


        int i = 0;
        for (String us : categories){

            countTotal = 0;
            countGravidas = 0;

            for (ClinicInformation clinicInformation : clinicInformations){
                if (clinicInformation.getPatient().getEpisodes1().iterator().next().getSanitaryUnit().equals(us)){
                    countTotal ++;

                    if ( clinicInformation.isPregnant()) countGravidas ++;



                }
            }
            totalData[i] = countTotal;
            suspectData[i] = countGravidas;

            i++;
        }

        cdTotal.setData(new ArrayList<>(Arrays.asList(totalData)));
        cdSuspeito.setData(new ArrayList<>(Arrays.asList(suspectData)));

        options.setSeries(new ArrayList<>(Arrays.asList(cdTotal, cdSuspeito)));

        chartView.setOptions(options);

        chartView.setVisibility(View.VISIBLE);


    }

    @Override
    public PregnantPatientsGraphReportVM getRelatedViewModel() {
        return (PregnantPatientsGraphReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PregnantPatientsGraphReportVM.class);
    }







    public void displaySearchResult() {

       generateGraph(getRelatedViewModel().getAllDisplyedRecords());

      //  getRelatedViewModel().setReportGenerationFinished();
    }

}