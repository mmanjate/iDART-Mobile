package mz.org.fgh.idartlite.view.reports;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.adapter.ReportListAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityReportsListingBinding;
import mz.org.fgh.idartlite.model.Report;

public class ReportsListingActivity extends BaseActivity {

    private RecyclerView reyclerReports;
    private ActivityReportsListingBinding reportListingBinding;
    private ReportListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportListingBinding=   DataBindingUtil.setContentView(this, R.layout.activity_reports_listing);

      //  reportListingBinding.setViewModel(getRelatedViewModel());
        reyclerReports = reportListingBinding.reyclerReports;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reyclerReports.setLayoutManager(layoutManager);
        reyclerReports.setHasFixedSize(true);
        reyclerReports.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        if (adapter == null) {
            adapter = new ReportListAdapter(reyclerReports, fillReports(), this);
            reyclerReports.setAdapter(adapter);
        }




        reyclerReports.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), reyclerReports, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        nextActivity(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                )
        );
    }

    private void nextActivity(int position) {
        Map<String, Object> params = new HashMap<>();
        //params.put("patient", reyclerReports.getAdapter().getI);
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        nextActivity(DispenseReportActivity.class,params);
    }

    private List<Report> fillReports(){
        List<Report> reports= new ArrayList<Report>();

        Report report =new Report();

        report.setCode("001");
        report.setDescription("Relatorio de Dispensas Da Farmacia");

        Report report2= new Report();

        report.setCode("002");
        report.setDescription("Alerta de Stock");


        reports.add(report);

        return reports;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }
}