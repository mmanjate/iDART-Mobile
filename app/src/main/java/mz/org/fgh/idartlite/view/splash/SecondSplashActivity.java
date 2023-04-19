package mz.org.fgh.idartlite.view.splash;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivitySecondSplashBinding;
import mz.org.fgh.idartlite.viewmodel.splash.SecondSplashVM;

public class SecondSplashActivity extends BaseActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySecondSplashBinding secondSplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_second_splash);
        secondSplashBinding.setViewModel(getRelatedViewModel());
    }

    @Override
    public SecondSplashVM getRelatedViewModel() {
        return (SecondSplashVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(SecondSplashVM.class);
    }
}