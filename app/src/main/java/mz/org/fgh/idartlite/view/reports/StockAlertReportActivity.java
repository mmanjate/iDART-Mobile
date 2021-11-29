package mz.org.fgh.idartlite.view.reports;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.ListbleReportRecycleViewAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityStockAlertReportBinding;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.view.about.AboutActivity;

public class StockAlertReportActivity extends BaseActivity {

    private RecyclerView reyclerStock;
    private ActivityStockAlertReportBinding stockAlertReportBinding;
    private IDispenseService dispenseService;
    private ListbleReportRecycleViewAdapter listbleReportRecycleViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockAlertReportBinding=   DataBindingUtil.setContentView(this, R.layout.activity_stock_alert_report);
        dispenseService= new DispenseService(getApplication(), getCurrentUser());

        reyclerStock = stockAlertReportBinding.reyclerStock;

        //stockAlertReportBinding.setViewModel(getRelatedViewModel());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reyclerStock.setLayoutManager(layoutManager);
        reyclerStock.setHasFixedSize(true);
        reyclerStock.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        List<StockReportData> stockReport=new ArrayList<>();
        try {
         stockReport= dispenseService.getStockAlertReportMonthPeriod();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listbleReportRecycleViewAdapter = new ListbleReportRecycleViewAdapter(reyclerStock, stockReport, this);
        reyclerStock.setAdapter(listbleReportRecycleViewAdapter);

        stockAlertReportBinding.executePendingBindings();

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.about:
                //If this activity started from other activity
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    public void showDialog(Activity activity, Bundle savedInstanceState){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_stock_alert_report_dialog);
        dispenseService= new DispenseService(activity.getApplication(), getCurrentUser());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity.getApplication());


        RecyclerView reyclerStock = (RecyclerView) dialog.findViewById(R.id.reyclerStock);

        reyclerStock.setLayoutManager(layoutManager);
        reyclerStock.setHasFixedSize(true);
        reyclerStock.addItemDecoration(new DividerItemDecoration(activity.getApplication(), LinearLayout.VERTICAL));
        List<StockReportData> stockReport=new ArrayList<>();
        try {
            stockReport= dispenseService.getStockAlertReportMonthPeriod();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listbleReportRecycleViewAdapter = new ListbleReportRecycleViewAdapter(reyclerStock, stockReport, (BaseActivity) activity);
        reyclerStock.setAdapter(listbleReportRecycleViewAdapter);



        LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.layoutId);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button ok = (Button) dialog.findViewById(R.id.buttonId);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(!stockReport.isEmpty()) {
            dialog.show();
        }

    }
}