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
import com.highsoft.highcharts.common.hichartsclasses.HIItem;
import com.highsoft.highcharts.common.hichartsclasses.HILabels;
import com.highsoft.highcharts.common.hichartsclasses.HIMarker;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPie;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISpline;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
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

        /*HIItem item = new HIItem();
        item.setHtml("Total por Tipo de Dispensa");
        item.setStyle(new HICSSObject());
        item.getStyle().setTop("18px");
        item.getStyle().setColor("black");

        HILabels labels = new HILabels();
        labels.setItems(new ArrayList<>(Collections.singletonList(item)));
        options.setLabels(labels);*/

        HITooltip tooltip = new HITooltip();
        tooltip.setHeaderFormat("<span style=\"font-size:10px\">{point.key}</span><table>");
        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td><td style=\"padding:0\"><b>{point.y:.1f} mm</b></td></tr>");
        tooltip.setFooterFormat("</table>");
        tooltip.setShared(true);
        tooltip.setUseHTML(true);
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setPointPadding(0.2);
        plotOptions.getColumn().setBorderWidth(0);
        options.setPlotOptions(plotOptions);


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