package mz.org.fgh.idartlite.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityHomeBinding;
import mz.org.fgh.idartlite.databinding.ActivityPatientHomeBinding;
import mz.org.fgh.idartlite.viewmodel.home.HomeVM;
import mz.org.fgh.idartlite.viewmodel.home.PatientHomeVM;

public class PatientHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPatientHomeBinding activityPatientHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_patient_home);
        activityPatientHomeBinding.setViewModel(getRelatedViewModel());

        activityPatientHomeBinding.executePendingBindings();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PatientHomeVM.class);
    }

    @Override
    public PatientHomeVM getRelatedViewModel() {
        return (PatientHomeVM) super.getRelatedViewModel();
    }
}