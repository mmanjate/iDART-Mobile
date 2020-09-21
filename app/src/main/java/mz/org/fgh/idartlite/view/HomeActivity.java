package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityHomeBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.viewmodel.HomeVM;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        activityHomeBinding.setViewModel(getRelatedViewModel());

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                currentUser = (User) bundle.getSerializable("user");
                currentClinic = (Clinic) bundle.getSerializable("clinic");
            }
        }

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