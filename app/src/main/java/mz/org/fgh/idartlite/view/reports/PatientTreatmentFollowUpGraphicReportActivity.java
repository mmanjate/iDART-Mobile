package mz.org.fgh.idartlite.view.reports;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

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
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.dispense.AwatingPatientsGraphReportVM;
import mz.org.fgh.idartlite.viewmodel.report.PatientTreatmentFollowUpGraphicReportVM;

public class PatientTreatmentFollowUpGraphicReportActivity extends BaseActivity {

    private String start;
    private String end;
    private HIChartView chartView;
    private EditText edtStart;
    private EditText edtEnd;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                String reportType = Utilities.stringHasValue((String) bundle.getSerializable(ClinicInformation.PARAM_FOLLOW_STATUS)) ? (String) bundle.getSerializable(ClinicInformation.PARAM_FOLLOW_STATUS) : (String) bundle.getSerializable(ClinicInformation.PARAM_RAM_STATUS);
                getRelatedViewModel().setReportType(reportType);
            }
        }

        setContentView(R.layout.activity_patient_treatment_follow_up_graph_report);

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

            DatePickerDialog datePickerDialog = new DatePickerDialog(PatientTreatmentFollowUpGraphicReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(PatientTreatmentFollowUpGraphicReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
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

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PatientTreatmentFollowUpGraphicReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
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

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PatientTreatmentFollowUpGraphicReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
                        edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getRelatedViewModel().getSearchParams().setEndDate(DateUtilities.createDateWithTime(end, DateUtilities.END_DAY_TIME, DateUtilities.DATE_TIME_FORMAT));
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        search.setOnClickListener(v -> {

            if (!Utilities.stringHasValue(start) || !Utilities.stringHasValue(end) ){
                Utilities.displayAlertDialog(PatientTreatmentFollowUpGraphicReportActivity.this, "Por favor indicar o período por analisar!").show();
            }else
            if (DateUtilities.dateDiff(DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(PatientTreatmentFollowUpGraphicReportActivity.this, "A data inicio deve ser menor que a data fim.").show();
            }else
            if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT)) < 0){
                Utilities.displayAlertDialog(PatientTreatmentFollowUpGraphicReportActivity.this, "A data inicio deve ser menor que a data corrente.").show();
            }
            else
            if ((int) DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(PatientTreatmentFollowUpGraphicReportActivity.this, "A data fim deve ser menor que a data corrente.").show();
            }else {
                getRelatedViewModel().initSearch();
            }
        });
    }

    @Override
    public PatientTreatmentFollowUpGraphicReportVM getRelatedViewModel() {
        return (PatientTreatmentFollowUpGraphicReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PatientTreatmentFollowUpGraphicReportVM.class);
    }

    public void generateGraph(List<ClinicInformation> clinicInformationList) {
        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText(getRelatedViewModel().getReportTitle());
        options.setTitle(title);

        List<String> categories = new ArrayList<>();

        for (ClinicInformation information : clinicInformationList){
            if (!categories.contains(information.getPatient().getReferenceEpisode().getSanitaryUnit())) {
                categories.add(information.getPatient().getReferenceEpisode().getSanitaryUnit());
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
        cdSuspeito.setName(getRelatedViewModel().getReportPosetiveBarTitle());
        Number[] suspectData = new Number[categories.size()];


        int countTotal;
        int countSuspeito;

        int i = 0;
        for (String us : categories){

            countTotal = 0;
            countSuspeito = 0;

            for (ClinicInformation clinicInformation : clinicInformationList){
                if (clinicInformation.getPatient().getReferenceEpisode().getSanitaryUnit().equals(us)){
                    countTotal ++;

                    if (getRelatedViewModel().isRAMReport() && clinicInformation.isAdverseReactionOfMedicine()) countSuspeito ++;

                    else if (getRelatedViewModel().isFollowReport() && clinicInformation.hasSevenOrMoreLateDays()) countSuspeito ++;

                }
            }
            totalData[i] = countTotal;
            suspectData[i] = countSuspeito;

            i++;
        }

        cdTotal.setData(new ArrayList<>(Arrays.asList(totalData)));
        cdSuspeito.setData(new ArrayList<>(Arrays.asList(suspectData)));

        options.setSeries(new ArrayList<>(Arrays.asList(cdTotal, cdSuspeito)));

        chartView.setOptions(options);

        chartView.setVisibility(View.VISIBLE);
    }

    public void displaySearchResult() {
        generateGraph(getRelatedViewModel().getAllDisplyedRecords());
    }
}
