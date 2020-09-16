package mz.org.fgh.idartlite.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityHomeBinding;
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