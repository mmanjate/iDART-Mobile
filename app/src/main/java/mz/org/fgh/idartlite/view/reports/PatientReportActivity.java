package mz.org.fgh.idartlite.view.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;

import com.highsoft.highcharts.Common.HIChartsClasses.HIChart;
import com.highsoft.highcharts.Common.HIChartsClasses.HIColumn;
import com.highsoft.highcharts.Common.HIChartsClasses.HIOptions;
import com.highsoft.highcharts.Common.HIChartsClasses.HIPlotOptions;
import com.highsoft.highcharts.Common.HIChartsClasses.HISubtitle;
import com.highsoft.highcharts.Common.HIChartsClasses.HITitle;
import com.highsoft.highcharts.Common.HIChartsClasses.HITooltip;
import com.highsoft.highcharts.Common.HIChartsClasses.HIXAxis;
import com.highsoft.highcharts.Common.HIChartsClasses.HIYAxis;
import com.highsoft.highcharts.Core.HIChartView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.PrescriptionActivity;
import mz.org.fgh.idartlite.viewmodel.PatientReportVM;

public class PatientReportActivity extends BaseActivity {

    private String start;
    private String end;
    private HIChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);
        chartView = (HIChartView) findViewById(R.id.hc_view);

        chartView.setVisibility(View.GONE);

        EditText edtStart = findViewById(R.id.start);
        EditText edtEnd = findViewById(R.id.end);
        ImageView search = findViewById(R.id.buttonSearch);

        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        start =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideSoftKeyboard(PatientReportActivity.this);
                generateGraph(start, end);
            }
        });
    }

    private void generateGraph(String start, String end) {
        List<DateTime> datesBetween = getRelatedViewModel().processPeriods(start, end);

        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("column");
        options.setChart(chart);

        HITitle title = new HITitle();

        title.setText("Número de pacientes por período");
        HISubtitle subtitle = new HISubtitle();
        subtitle.setText(start+" - "+end);
        options.setTitle(title);
        options.setSubtitle(subtitle);

        final HIYAxis hiyAxis = new HIYAxis();
        hiyAxis.setMin(0);
        hiyAxis.setTitle(new HITitle());
        hiyAxis.getTitle().setText("Quantidade");
        options.setYAxis(new ArrayList(){{add(hiyAxis);}});

        HIColumn patientData = new HIColumn();
        patientData.setName("Pacientes");
        ArrayList serieData = new ArrayList<>();

        final HIXAxis hixAxis = new HIXAxis();
        ArrayList categories = new ArrayList<>();
        for (int i = 0; i <= datesBetween.size() - 2; i++){
            if ( i < datesBetween.size()-2) {
                int qty = getRelatedViewModel().countNewPatientByPeriod(datesBetween.get(i).toDate(), datesBetween.get(i + 1).minusDays(1).toDate());
                if (qty > 0) {
                    categories.add(DateUtilitis.formatToDDMMYYYY(datesBetween.get(i).toDate()) + " TO " + DateUtilitis.formatToDDMMYYYY(datesBetween.get(i + 1).minusDays(1).toDate()));
                    serieData.add(qty);
                }
            }else {
                int qty = getRelatedViewModel().countNewPatientByPeriod(datesBetween.get(i).toDate(), datesBetween.get(i + 1).toDate());
                if (qty > 0) {
                    categories.add(DateUtilitis.formatToDDMMYYYY(datesBetween.get(i).toDate()) + " TO " + DateUtilitis.formatToDDMMYYYY(datesBetween.get(i + 1).toDate()));
                    serieData.add(qty);
                }
            }
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

    @Override
    public PatientReportVM getRelatedViewModel() {
        return (PatientReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PatientReportVM.class);
    }
}