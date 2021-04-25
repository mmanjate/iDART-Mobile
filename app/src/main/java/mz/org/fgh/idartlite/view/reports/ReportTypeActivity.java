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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.report.ReportListAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityReportTypeBinding;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Report;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.clinicInfo.PregnantPatientReportVM;
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
                            if (reportListAdapter.getItemAtPosition(position).getParams() != null){
                                reportListAdapter.getItemAtPosition(position).addParam("user", getCurrentUser());
                                reportListAdapter.getItemAtPosition(position).addParam("clinic", getCurrentClinic());
                                nextActivity(reportListAdapter.getItemAtPosition(position).getDisplayActivity(), reportListAdapter.getItemAtPosition(position).getParams());
                            }else
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
        reports.add(Report.fastCreate("003", "Relatorio de Entradas de pacientes", R.drawable.ic_list, PatientRegisterReportActivity.class));
        reports.add(Report.fastCreate("004", "FILA", R.drawable.ic_list, FILAReportActivity.class));
        reports.add(Report.fastCreate("005", "Relatorio de Pacientes Esperados", R.drawable.ic_list, PatientsAwaitingReportActivity.class));
        reports.add(Report.fastCreate("006", "Relatorio Resumo de Pacientes Esperados por Regime", R.drawable.ic_list, PatientsAwaitingStatisticsActivity.class));
        reports.add(Report.fastCreate("007", "Relatorio de Pacientes Faltosos", R.drawable.ic_list, AbsentPatientsReportActivity.class));
        reports.add(Report.fastCreate("008", "Relatorio de Dispensas Não Sincronizadas", R.drawable.ic_list, DispensesNonSyncReportActivity.class));
        reports.add(Report.fastCreate("009", "Relatorio de Estatistico de Dispensas por Frascos", R.drawable.ic_list, DispenseDrugStatisticReportActivity.class));
        reports.add(Report.fastCreate("010", "Lista de Pacientes Rastreados pelos RAMs", R.drawable.ic_list, AdverseReactionReportActivity.class, createParams(ClinicInformation.PARAM_RAM_STATUS, ClinicInformation.PARAM_RAM_STATUS_ALL)));
        reports.add(Report.fastCreate("011", "Lista de Pacientes Com RAM", R.drawable.ic_list, AdverseReactionReportActivity.class, createParams(ClinicInformation.PARAM_RAM_STATUS, ClinicInformation.PARAM_RAM_STATUS_POSETIVE)));
        reports.add(Report.fastCreate("010", "Lista/número dos Pacientes monitorados pela adesão", R.drawable.ic_list, PatientTreatmentFollowUpReportActivity.class, createParams(ClinicInformation.PARAM_FOLLOW_STATUS, ClinicInformation.PARAM_FOLLOW_STATUS_ALL)));
        reports.add(Report.fastCreate("011", "Pacientes que atrasaram nos levantamentos mais de 7 dias", R.drawable.ic_list, PatientTreatmentFollowUpReportActivity.class, createParams(ClinicInformation.PARAM_FOLLOW_STATUS, ClinicInformation.PARAM_FOLLOW_STATUS_WITH_LATE_DAYS)));
        reports.add(Report.fastCreate("012", "Relatorio de Pacientes com TB suspeito ", R.drawable.ic_list, TBSuspectPatientReportActivity.class));
        reports.add(Report.fastCreate("013", "Relatorio de Pacientes que rastrearam para TB ", R.drawable.ic_list, TBTracedPatientReportActivity.class));
        reports.add(Report.fastCreate("014", "Relatorio de pacientes rastreado pela gravidez", R.drawable.ic_list, PregnantPatientReportActivity.class,createParams(ClinicInformation.CLINIC_INFO_STATUS, ClinicInformation.PREGNANT_STATUS_ALL)));
        reports.add(Report.fastCreate("014", "Relatorio de de Pacientes Gravidas", R.drawable.ic_list, PregnantPatientReportActivity.class,createParams(ClinicInformation.CLINIC_INFO_STATUS, ClinicInformation.PREGNANT_STATUS_POSITIVE)));

        return reports;
    }

    private List<Report> loadGraphReports(){
        List<Report> reports= new ArrayList<Report>();
        reports.add(Report.fastCreate("001", "Entrada de Pacientes Referidos", R.drawable.ic_graphic, PatientReportActivity.class));
        reports.add(Report.fastCreate("002", "Dispensas por Regime Terapêutico", R.drawable.ic_graphic, DispensedDrugsReportActivity.class));
        reports.add(Report.fastCreate("003", "Pacientes Esperados por Regime e Tipo de Dispensa", R.drawable.ic_graphic, AwatingPatientsGraphReportActivity.class));
        reports.add(Report.fastCreate("004", "Estatistica de Dispensas por Frascos", R.drawable.ic_graphic, DispenseDrugGraphStatisticReportActivity.class));
        return reports;
    }


    private Map<String, Object> createParams(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        return map;
    }


}