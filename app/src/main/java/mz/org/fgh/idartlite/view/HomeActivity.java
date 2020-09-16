package mz.org.fgh.idartlite.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.databinding.ActivityHomeBinding;
import mz.org.fgh.idartlite.viewmodel.HomeVM;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        HomeVM homeVM = new ViewModelProvider(this).get(HomeVM.class);
        homeVM.setBaseActivity(this);
        activityHomeBinding.setViewModel(homeVM);
        activityHomeBinding.executePendingBindings();
    }
}