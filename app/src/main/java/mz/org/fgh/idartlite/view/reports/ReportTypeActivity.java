package mz.org.fgh.idartlite.view.reports;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.adapter.ReportListAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityReportTypeBinding;
import mz.org.fgh.idartlite.model.Report;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.report.ReportTypeVM;

public class ReportTypeActivity extends BaseActivity {

    private ActivityReportTypeBinding activityReportTypeBinding;

    private RecyclerView reyclerReports;
    private RecyclerView reyclerGraphs;
    private ReportListAdapter reportListAdapter;
    private ReportListAdapter reportGraphAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityReportTypeBinding = DataBindingUtil.setContentView(this, R.layout.activity_report_type);
        activityReportTypeBinding.setViewModel(getRelatedViewModel());

        activityReportTypeBinding.executePendingBindings();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initReportsRcv();
    }

    private void initReportsRcv() {
        reyclerReports = activityReportTypeBinding.reyclerReports;
        reyclerGraphs = activityReportTypeBinding.reyclerGraphs;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reyclerReports.setLayoutManager(layoutManager);
        reyclerReports.setHasFixedSize(true);
        reyclerReports.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        if (reportListAdapter == null) {
            reportListAdapter = new ReportListAdapter(reyclerReports, loadListReports(), this);
            reyclerReports.setAdapter(reportListAdapter);


        reyclerReports.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), reyclerReports, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        nextActivityWithGenericParams(reportListAdapter.getItemAtPosition(position).getDisplayActivity());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
                }
                ));
        }

        reyclerGraphs.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reyclerGraphs.setHasFixedSize(true);
        reyclerGraphs.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        if (reportGraphAdapter == null) {
            reportGraphAdapter = new ReportListAdapter(reyclerGraphs, loadGraphReports(), this);
            reyclerGraphs.setAdapter(reportGraphAdapter);


        reyclerGraphs.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), reyclerReports, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        nextActivityWithGenericParams(reportGraphAdapter.getItemAtPosition(position).getDisplayActivity());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
                }
                ));
        }
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ReportTypeVM.class);
    }

    @Override
    public ReportTypeVM getRelatedViewModel() {
        return (ReportTypeVM) super.getRelatedViewModel();
    }

    public void changeFormSectionVisibility(View view) {
        if (view.equals(activityReportTypeBinding.listingData)){
            if (activityReportTypeBinding.listsLyt.getVisibility() == View.VISIBLE){

                activityReportTypeBinding.ibtnListing.animate().setDuration(200).rotation(180);
                Utilities.collapse(activityReportTypeBinding.listsLyt);
            }else {
                activityReportTypeBinding.ibtnListing.animate().setDuration(200).rotation(0);
                Utilities.expand(activityReportTypeBinding.listsLyt);
            }
        }else if (view.equals(activityReportTypeBinding.graphData)){
            if (activityReportTypeBinding.graphsLyt.getVisibility() == View.VISIBLE){

                activityReportTypeBinding.graphs.animate().setDuration(200).rotation(180);
                Utilities.collapse(activityReportTypeBinding.graphsLyt);
            }else {
                activityReportTypeBinding.graphs.animate().setDuration(200).rotation(0);
                Utilities.expand(activityReportTypeBinding.graphsLyt);

            }
        }
    }

    private List<Report> loadListReports(){
        List<Report> reports= new ArrayList<Report>();
        reports.add(Report.fastCreate("001", "Relatorio de Dispensas Da Farmacia", R.drawable.ic_list, DispenseReportActivity.class));
        reports.add(Report.fastCreate("002", "Alerta de Stock", R.drawable.ic_list, StockAlertReportActivity.class));

        return reports;
    }

    private List<Report> loadGraphReports(){
        List<Report> reports= new ArrayList<Report>();
        reports.add(Report.fastCreate("001", "Entrada de Pacientes Referidos", R.drawable.ic_graphic, PatientReportActivity.class));
        reports.add(Report.fastCreate("002", "Dispensas por Regime Terapêutico", R.drawable.ic_graphic, DispensedDrugsReportActivity.class));

        return reports;
    }

}