package mz.org.fgh.idartlite.view.reports;

import android.os.Bundle;

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

import java.util.ArrayList;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.viewmodel.ReportVM;

public class ReportActivity extends BaseActivity {

    //private ActivityReportBinding reportBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //reportBinding = DataBindingUtil.setContentView(this, R.layout.activity_report);

        setContentView(R.layout.activity_report);
        HIChartView chartView = (HIChartView) findViewById(R.id.hc_view);

        //HIChartView chartView = reportBinding.hcView;
        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("column");
        options.setChart(chart);

        HITitle title = new HITitle();

        title.setText("UEFA Champions League 2016/17");
        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Team statistics");
        options.setTitle(title);
        options.setSubtitle(subtitle);

        final HIYAxis hiyAxis = new HIYAxis();
        hiyAxis.setMin(0);
        hiyAxis.setTitle(new HITitle());
        hiyAxis.getTitle().setText("Number");
        options.setYAxis(new ArrayList(){{add(hiyAxis);}});

        final HIXAxis hixAxis = new HIXAxis();
        ArrayList categories = new ArrayList<>();
        categories.add("Goals");
        categories.add("Assists");
        categories.add("Shots On Goal");
        categories.add("Shots");

        hixAxis.setCategories(categories);
        options.setXAxis(new ArrayList(){{add(hixAxis);}});

        HITooltip tooltip = new HITooltip();
        tooltip.setHeaderFormat("<span style=\"font-size:15px\">{point.key}</span><table>");
        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>" + "<td style=\"padding:0\"><b>{point.y}</b></td></tr>");
        tooltip.setFooterFormat("</talble>");
        tooltip.setShared(true);
        tooltip.setUseHTML(true);
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setPointPadding(0.2);
        plotOptions.getColumn().setBorderWidth(0);
        options.setPlotOptions(plotOptions);

        HIColumn realMadrid = new HIColumn();
        realMadrid.setName("Real Madrid");
        ArrayList realMadridData = new ArrayList<>();
        realMadridData.add(36);
        realMadridData.add(31);
        realMadridData.add(93);
        realMadridData.add(236);
        realMadrid.setData(realMadridData);

        HIColumn juventus = new HIColumn();
        juventus.setName("Juventus");
        ArrayList juventusData = new ArrayList<>();
        juventusData.add(22);
        juventusData.add(10);
        juventusData.add(66);
        juventusData.add(178);
        juventus.setData(juventusData);

        HIColumn monaco = new HIColumn();
        monaco.setName("Monaco");
        ArrayList monacoData = new ArrayList<>();
        monacoData.add(22);
        monacoData.add(17);
        monacoData.add(56);
        monacoData.add(147);
        monaco.setData(monacoData);

        HIColumn atleticoMadrid = new HIColumn();
        atleticoMadrid.setName("Atlético Madrid");
        ArrayList atleticoMadridData = new ArrayList<>();
        atleticoMadridData.add(15);
        atleticoMadridData.add(9);
        atleticoMadridData.add(55);
        atleticoMadridData.add(160);
        atleticoMadrid.setData(atleticoMadridData);

        ArrayList series = new ArrayList<>();
        series.add(realMadrid);
        series.add(juventus);
        series.add(monaco);
        series.add(atleticoMadrid);

        options.setSeries(series);

        chartView.setOptions(options);

    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ReportVM.class);
    }
}