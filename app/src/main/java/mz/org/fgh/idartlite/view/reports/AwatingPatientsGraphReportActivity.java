package mz.org.fgh.idartlite.view.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.dispense.AwatingPatientsGraphReportVM;

public class AwatingPatientsGraphReportActivity extends BaseActivity {

    private String start;
    private String end;
    private HIChartView chartView;
    private EditText edtStart;
    private EditText edtEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_per_regimen_report);

        setContentView(R.layout.activity_report);
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(AwatingPatientsGraphReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(AwatingPatientsGraphReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
                edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                getRelatedViewModel().getSearchParams().setEndDate(DateUtilities.createDateWithTime(end,DateUtilities.END_DAY_TIME, DateUtilities.DATE_TIME_FORMAT));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        edtStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AwatingPatientsGraphReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
                        edtStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        start = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getRelatedViewModel().getSearchParams().setStartdate(DateUtilities.createDate(start, DateUtilities.DATE_FORMAT));
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        edtEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AwatingPatientsGraphReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
                        edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getRelatedViewModel().getSearchParams().setEndDate(DateUtilities.createDateWithTime(end,DateUtilities.END_DAY_TIME, DateUtilities.DATE_TIME_FORMAT));
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        search.setOnClickListener(v -> {

            Utilities.hideSoftKeyboard(AwatingPatientsGraphReportActivity.this);
            getRelatedViewModel().initSearch();
        });
    }


    public void generateGraph(List<Dispense> dispenseList) {
        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText("Gráfico de pacientes esperados");
        options.setTitle(title);

        List<String> categories = new ArrayList<>();
        for (Dispense dispense : dispenseList){
            if (!categories.contains(dispense.getPrescription().getTherapeuticRegimen().getDescription())) {
                categories.add(dispense.getPrescription().getTherapeuticRegimen().getDescription());
            }
        }

        HIXAxis xaxis = new HIXAxis();
        xaxis.setCategories(new ArrayList<>(categories));
        options.setXAxis(new ArrayList<>(Collections.singletonList(xaxis)));

        final HIYAxis hiyAxis = new HIYAxis();
        hiyAxis.setMin(0);
        hiyAxis.setTitle(new HITitle());
        hiyAxis.getTitle().setText("Quantidade");
        options.setYAxis(new ArrayList(){{add(hiyAxis);}});

        HIItems item = new HIItems();
        item.setHtml("Total por Tipo de Dispensa");
        item.setStyle(new HICSSObject());
        item.getStyle().setTop("18px");
        item.getStyle().setColor("black");

        HILabels labels = new HILabels();
        labels.setItems(new ArrayList<>(Collections.singletonList(item)));
        options.setLabels(labels);


        HIColumn cdm = new HIColumn();
        cdm.setName("Mensal");
        Number[] dmData = new Number[categories.size()];

        HIColumn cdt = new HIColumn();
        cdt.setName("Trimestral");
        Number[] dtData = new Number[categories.size()];

        HIColumn cds = new HIColumn();
        cds.setName("Semestral");
        Number[] dsData = new Number[categories.size()];

        HIColumn cdOther = new HIColumn();
        cdOther.setName("Outro");
        Number[] otherData = new Number[categories.size()];

        Number[] splineData = new Number[categories.size()];

        int dm;
        int dt;
        int ds;
        int other;

        int sumdm = 0;
        int sumdt = 0;
        int sumds = 0;
        int sumother = 0;

        int i = 0;
        for (String regimen : categories){

            dm = 0;
            dt = 0;
            ds = 0;
            other = 0;

            for (Dispense dispense : dispenseList){
                if (dispense.getPrescription().getTherapeuticRegimen().getDescription().equals(regimen)){
                    if (dispense.getPrescription().getDispenseType().isMonthlyDispense()) dm ++;
                    if (dispense.getPrescription().getDispenseType().isThreeMonthsDispense()) dt ++;
                    if (dispense.getPrescription().getDispenseType().isSixMonthsDispense()) ds ++;
                    if (dispense.getPrescription().getDispenseType().isOtherDispense()) other ++;

                }
            }
            dmData[i] = dm;
            dtData[i] = dt;
            dsData[i] = ds;
            otherData[i] = other;

            sumdm = sumdm+dm;
            sumdt = sumdt+dt;
            sumds = sumds+ds;
            sumother = sumother+other;

            splineData[i] = dm+dt+ds+other;

            i++;
        }

        cdm.setData(new ArrayList<>(Arrays.asList(dmData)));
        cdt.setData(new ArrayList<>(Arrays.asList(dtData)));
        cds.setData(new ArrayList<>(Arrays.asList(dsData)));
        cdOther.setData(new ArrayList<>(Arrays.asList(otherData)));


        HISpline spline = new HISpline();
        spline.setName("Total");
        spline.setData(new ArrayList<>(Arrays.asList(splineData)));
        spline.setMarker(new HIMarker());
        spline.getMarker().setLineWidth(2);
        spline.getMarker().setFillColor(HIColor.initWithName("white"));
        spline.getMarker().setLineColor(HIColor.initWithHexValue("f7a35c"));

        HIPie pie = new HIPie();
        pie.setName("Total");
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name", "Mensal");
        map1.put("y", sumdm);
        map1.put("color", "#7cb5ec");
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("name", "Trimestral");
        map2.put("y", sumdt);
        map2.put("color", "#434348");
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("name", "Semestral");
        map3.put("y", sumds);
        map3.put("color", "#90ed7d");
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("name", "Outro");
        map4.put("y", sumother);
        map4.put("color", "#ffcc7d");

        List<HashMap<String, Object>> totalmapList = new ArrayList<>();
        if (sumdm > 0) totalmapList.add(map1);
        if (sumdt > 0) totalmapList.add(map2);
        if (sumds > 0) totalmapList.add(map3);
        if (sumother > 0) totalmapList.add(map4);

        pie.setData(new ArrayList<>(totalmapList));
        pie.setCenter(new ArrayList<>(Arrays.asList(100, 80)));
        pie.setSize("100");
        pie.setShowInLegend(false);
        pie.setDataLabels(new HIDataLabels());
        pie.getDataLabels().setEnabled(true);

        //options.setSeries(new ArrayList<>(Arrays.asList(cdm, cdt, cds, cdOther, spline, pie)));

        options.setSeries(new ArrayList<>(Arrays.asList(cdm, cdt, cds, cdOther)));

        chartView.setOptions(options);

        chartView.setVisibility(View.VISIBLE);


    }

    @Override
    public AwatingPatientsGraphReportVM getRelatedViewModel() {
        return (AwatingPatientsGraphReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(AwatingPatientsGraphReportVM.class);
    }

    public void displaySearchResult() {

        generateGraph(getRelatedViewModel().getAllDisplyedRecords());
    }
}