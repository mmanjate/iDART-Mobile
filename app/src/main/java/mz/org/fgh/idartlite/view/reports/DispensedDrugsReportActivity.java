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
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPie;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.core.HIChartView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.dispense.DispensedDrugsReportVM;

public class DispensedDrugsReportActivity extends BaseActivity {

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
        setContentView(R.layout.activity_dispensed_drugs_report);

        chartView = findViewById(R.id.hc_view);

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

            DatePickerDialog datePickerDialog = new DatePickerDialog(DispensedDrugsReportActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
                edtStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                start =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DispensedDrugsReportActivity.this, (view12, year, monthOfYear, dayOfMonth) -> {
                    edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
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

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DispensedDrugsReportActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                        edtStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        start = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        edtEnd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DispensedDrugsReportActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                    edtEnd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    end = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        search.setOnClickListener(v -> {

            if (!Utilities.stringHasValue(start) || !Utilities.stringHasValue(end) ){
                Utilities.displayAlertDialog(DispensedDrugsReportActivity.this, "Por favor indicar o período por analisar!").show();
            }else
            if (DateUtilities.dateDiff(DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(DispensedDrugsReportActivity.this, "A data inicio deve ser menor que a data fim.").show();
            }else
            if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT)) < 0){
                Utilities.displayAlertDialog(DispensedDrugsReportActivity.this, "A data inicio deve ser menor que a data corrente.").show();
            }
            else
            if ((int) DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(end, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0){
                Utilities.displayAlertDialog(DispensedDrugsReportActivity.this, "A data fim deve ser menor que a data corrente.").show();
            }else {
                Utilities.hideSoftKeyboard(DispensedDrugsReportActivity.this);
                try {
                    getRelatedViewModel().doSearch(DateUtilities.createDate(start, DateUtilities.DATE_FORMAT), DateUtilities.createDateWithTime(end, DateUtilities.END_DAY_TIME, DateUtilities.DATE_TIME_FORMAT));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void generateGraph() {

        start = edtStart.getText().toString();
        end = edtEnd.getText().toString();
        Map<String, Double> map;

        map = getRelatedViewModel().getSearchResults();

        if (map == null || map.isEmpty()){
            Utilities.displayAlertDialog(DispensedDrugsReportActivity.this, "Não foram encontrados resultados para a sua pesquisa!").show();
            return;
        } else {
            map.size();
        }

        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("pie");
        chart.setBackgroundColor(null);
        chart.setPlotBorderWidth(null);
        chart.setPlotShadow(false);
        options.setChart(chart);

        HITitle title = new HITitle();
        title.setText("Percentagem de Dispensas por Regime, "+start+" à "+end);
        options.setTitle(title);

        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: <b>{point.percentage:.1f}%</b>");
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setPie(new HIPie());
        plotOptions.getPie().setAllowPointSelect(true);
        plotOptions.getPie().setCursor("pointer");
        plotOptions.getPie().setShowInLegend(true);
        options.setPlotOptions(plotOptions);

        HIPie series1 = new HIPie();
        series1.setName("Regime");

        List<Map<String, Object>> dataMapList = new ArrayList<>();

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            HashMap<String, Object> m = new HashMap<>();
            m.put("name", entry.getKey());
            m.put("y", entry.getValue());
            if (dataMapList.size() == 1){
                m.put("sliced", true);
                m.put("selected", true);
            }
            dataMapList.add(m);
        }

        series1.setData(new ArrayList<>(dataMapList));

        options.setSeries(new ArrayList<>(Arrays.asList(series1)));

        chartView.setOptions(options);

        chartView.reload();

        chartView.setVisibility(View.VISIBLE);

    }

    @Override
    public DispensedDrugsReportVM getRelatedViewModel() {
        return (DispensedDrugsReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DispensedDrugsReportVM.class);
    }
}