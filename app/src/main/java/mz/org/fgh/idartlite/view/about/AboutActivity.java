package mz.org.fgh.idartlite.view.about;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityAboutBinding;
import mz.org.fgh.idartlite.viewmodel.about.AboutVM;

public class AboutActivity extends BaseActivity {

    private ActivityAboutBinding activityAboutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutBinding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        activityAboutBinding.setView((AboutVM) getRelatedViewModel());
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(AboutVM.class);
    }


}