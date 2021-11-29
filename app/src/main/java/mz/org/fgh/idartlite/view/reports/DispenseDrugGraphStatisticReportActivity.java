package mz.org.fgh.idartlite.view.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.report.DispenseDrugGraphStatisticReportVM;

public class DispenseDrugGraphStatisticReportActivity extends BaseActivity {

    private String start;
    private String end;
    private HIChartView chartView;
    private EditText edtStart;
    private EditText edtEnd;
    private RadioButton rdOnline;
    private RadioButton rdLocal;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispense_drug_graph_statistic_report);

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
        rdOnline = findViewById(R.id.rdOnline);
        rdLocal = findViewById(R.id.rdLocal);
        rdLocal.setChecked(true);

        rdOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRelatedViewModel().changeReportSearchMode(getString(R.string.online));
            }
        });

        rdLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRelatedViewModel().changeReportSearchMode(getString(R.string.local));
            }
        });


        edtStart.setOnClickListener(view -> {
            int mYear, mMonth, mDay;

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseDrugGraphStatisticReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseDrugGraphStatisticReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
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

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseDrugGraphStatisticReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
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

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseDrugGraphStatisticReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
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
                Utilities.displayAlertDialog(DispenseDrugGraphStatisticReportActivity.this, "Por favor indicar o período por analisar!").show();
            }else
            if (DateUtilities.dateDiff(DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(DispenseDrugGraphStatisticReportActivity.this, "A data inicio deve ser menor que a data fim.").show();
            }else
            if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT)) < 0){
                Utilities.displayAlertDialog(DispenseDrugGraphStatisticReportActivity.this, "A data inicio deve ser menor que a data corrente.").show();
            }
            else
            if ((int) DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(DispenseDrugGraphStatisticReportActivity.this, "A data fim deve ser menor que a data corrente.").show();
            }else {
                getRelatedViewModel().initSearch();
            }
        });
    }

    @Override
    public DispenseDrugGraphStatisticReportVM getRelatedViewModel() {
        return (DispenseDrugGraphStatisticReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DispenseDrugGraphStatisticReportVM.class);
    }

    private void generateGraph() {


        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("column");
        options.setChart(chart);

        HITitle title = new HITitle();

        title.setText("Dispensas por Frascos");
        HISubtitle subtitle = new HISubtitle();
        subtitle.setText(DateUtilities.formatToDDMMYYYY(start)+" - "+DateUtilities.formatToDDMMYYYY(end));
        options.setTitle(title);
        options.setSubtitle(subtitle);

        final HIYAxis hiyAxis = new HIYAxis();
        hiyAxis.setMin(0);
        hiyAxis.setTitle(new HITitle());
        hiyAxis.getTitle().setText("Nr. Dispensas");
        options.setYAxis(new ArrayList(){{add(hiyAxis);}});

        HIColumn patientData = new HIColumn();
        patientData.setName("Medicamento");
        ArrayList serieData = new ArrayList<>();

        final HIXAxis hixAxis = new HIXAxis();
        ArrayList categories = new ArrayList<>();

        for (Object dispense : getRelatedViewModel().getAllDisplyedRecords()){

            HashMap<String, Object> itemresult = (HashMap<String, Object>) (Object) dispense;

            categories.add(String.valueOf(itemresult.get("nomeMedicamento")));
            serieData.add(Integer.parseInt(itemresult.get("totalFrascosDispensados").toString()));

        }


        patientData.setData(serieData);

        hixAxis.setCategories(categories);
        options.setXAxis(new ArrayList(){{add(hixAxis);}});

        HITooltip tooltip = new HITooltip();
        tooltip.setHeaderFormat("<span style=\"font-size:15px\">{point.key}</span><table>");
        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>" + "<td style=\"padding:0\"><b>{point.y}</b></td></tr>");
        tooltip.setFooterFormat("</table>");
        tooltip.setShared(true);
        tooltip.setUseHTML(true);
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setPointPadding(0.2);
        plotOptions.getColumn().setBorderWidth(0);
        options.setPlotOptions(plotOptions);

        ArrayList series = new ArrayList<>();
        series.add(patientData);

        options.setSeries(series);

        chartView.setOptions(options);

        chartView.reload();

        chartView.setVisibility(View.VISIBLE);

    }

    public void displaySearchResult() {

        generateGraph();

    }
}