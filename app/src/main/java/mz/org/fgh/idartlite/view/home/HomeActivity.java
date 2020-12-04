package mz.org.fgh.idartlite.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityHomeBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.StockAlertReportActivity;
import mz.org.fgh.idartlite.viewmodel.home.HomeVM;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {

                this.getRelatedViewModel().setCurrentClinicSector((ClinicSector) bundle.getSerializable("clinicSector"));
            }
        }

        activityHomeBinding.setViewModel(getRelatedViewModel());

        activityHomeBinding.executePendingBindings();

     StockAlertReportActivity alert = new StockAlertReportActivity();
       alert.showDialog(this,savedInstanceState);


        Utilities.checkPermissionsToViewPdf(this);
    }


    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(HomeVM.class);
    }

    @Override
    public HomeVM getRelatedViewModel() {
        return (HomeVM) super.getRelatedViewModel();
    }
}