package mz.org.fgh.idartlite.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecuteWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityHomeBinding;
import mz.org.fgh.idartlite.service.restService.RestStockService;
import mz.org.fgh.idartlite.viewmodel.HomeVM;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        activityHomeBinding.setViewModel(getRelatedViewModel());

        activityHomeBinding.executePendingBindings();

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