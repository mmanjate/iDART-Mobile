package mz.org.fgh.idartlite.view.clinicInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityClinicInfoBinding;
import mz.org.fgh.idartlite.databinding.EpisodeActivityBinding;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.clinicInfo.ClinicInfoVM;
import mz.org.fgh.idartlite.viewmodel.episode.EpisodeVM;

public class ClinicInfoActivity extends BaseActivity  {

    private ActivityClinicInfoBinding createClinicInfoBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createClinicInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_clinic_info);
        createClinicInfoBinding.setViewModel(getRelatedViewModel());

    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ClinicInfoVM.class);
    }

    @Override
    public ClinicInfoVM getRelatedViewModel() {
        return (ClinicInfoVM) super.getRelatedViewModel();
    }


    public void changeFormSectionVisibility(View view) {
        if (view.equals(createClinicInfoBinding.demograficData)){
            if (createClinicInfoBinding.demograficDataLyt.getVisibility() == View.VISIBLE){
                switchLayout();
                createClinicInfoBinding.ibtnInitialData.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.demograficDataLyt);
            }else {
                switchLayout();
                createClinicInfoBinding.ibtnInitialData.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.demograficDataLyt);
            }
        }else if (view.equals(createClinicInfoBinding.addressData)){
            if (createClinicInfoBinding.addressDataLyt.getVisibility() == View.VISIBLE){
                switchLayout();
                createClinicInfoBinding.ibtnAddress.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.addressDataLyt);
            }else {
                createClinicInfoBinding.ibtnAddress.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.addressDataLyt);
                switchLayout();
            }
        }else if (view.equals(createClinicInfoBinding.clinicInfo)){
            if (createClinicInfoBinding.clinicDataLyt.getVisibility() == View.VISIBLE){
                createClinicInfoBinding.ibtnClinic.animate().setDuration(200).rotation(180);
                Utilities.collapse(createClinicInfoBinding.clinicDataLyt);
                switchLayout();
            }else {
                createClinicInfoBinding.ibtnClinic.animate().setDuration(200).rotation(0);
                Utilities.expand(createClinicInfoBinding.clinicDataLyt);
                switchLayout();
            }
        }
    }


    private void switchLayout(){
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setAddressDataVisible(!getRelatedViewModel().isAddressDataVisible());
    }


}