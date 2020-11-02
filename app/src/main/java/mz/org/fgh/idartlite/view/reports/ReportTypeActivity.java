package mz.org.fgh.idartlite.view.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityHomeBinding;
import mz.org.fgh.idartlite.databinding.ActivityReportTypeBinding;
import mz.org.fgh.idartlite.viewmodel.HomeVM;
import mz.org.fgh.idartlite.viewmodel.ReportTypeVM;

public class ReportTypeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_report_type);

        ActivityReportTypeBinding activityReportTypeBinding = DataBindingUtil.setContentView(this, R.layout.activity_report_type);
        activityReportTypeBinding.setViewModel(getRelatedViewModel());

        activityReportTypeBinding.executePendingBindings();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ReportTypeVM.class);
    }

    @Override
    public ReportTypeVM getRelatedViewModel() {
        return (ReportTypeVM) super.getRelatedViewModel();
    }
}