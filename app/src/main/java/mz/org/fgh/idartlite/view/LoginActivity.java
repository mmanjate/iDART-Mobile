package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.databinding.ActivityLoginBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.LoginVM;
public class LoginActivity extends BaseActivity implements RestResponseListener {
    private ActivityLoginBinding activityLoginBinding;
    private List<Clinic> list;
    private UserService userService;
    private ArrayAdapter<Clinic> adapterSpinnerClinic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(getRelatedViewModel());
//        activityLoginBinding.executePendingBindings();
        userService = new UserService(getApplication(), getCurrentUser());
        list = new ArrayList<Clinic>();
        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                list.add(0, new Clinic());
                list.addAll((List<Clinic>) bundle.getSerializable("clinicList"));
            }
        }
        try {
            if(userService.checkIfUsertableIsEmpty()){
                populateSpinner();
                activityLoginBinding.spinnerFarmacia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentClinic = (Clinic) parent.getSelectedItem();
                        getRelatedViewModel().setCurrentClinic(currentClinic);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } else{
                //activityLoginBinding.textIcon.setVisibility(View.GONE);
                activityLoginBinding.textFarmacia.setVisibility(View.GONE);
                activityLoginBinding.spinnerFarmacia.setVisibility(View.GONE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @BindingAdapter({"toastMessage"})
    public static void runTostMessage(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
    public void populateSpinner() {
        adapterSpinnerClinic = new ArrayAdapter<Clinic>(this, android.R.layout.simple_spinner_item, list);
        adapterSpinnerClinic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLoginBinding.spinnerFarmacia.setAdapter(adapterSpinnerClinic);
        activityLoginBinding.spinnerFarmacia.setVisibility(View.VISIBLE);
    }


    public void runRestUserAccess(){
        final String[] auth_access = {null};
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
             //   showLoading(LoginActivity.this ,getString(R.string.authenticate),getString(R.string.loading));

            }
        });
        new Thread(new Runnable() {
            public void run() {
                // do here some long operation

                auth_access[0] = userService.getUserAuthentication(currentClinic.getUuid(), currentUser.getUserName(), currentUser.getPassword(), LoginActivity.this);

                    /*while (!Utilities.stringHasValue(auth_access[0])){
                        Thread.sleep(2000);
                    }

                    if (auth_access[0].equals(userService.auth)) {
                        getRelatedViewModel().setUserAuthentic(true);
                    }else
                        getRelatedViewModel().setUserAuthentic(false);*/

//                   hideLoading();

            }
        }).start();

    }

    @Override
    public LoginVM getRelatedViewModel() {
        return (LoginVM) super.getRelatedViewModel();
    }
    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(LoginVM.class);
    }


    @Override
    public void doOnRestSucessResponse(String flag) {
        if (Utilities.stringHasValue(flag)){
            if (flag.equals(userService.auth)) {
                getRelatedViewModel().setUserAuthentic(true);

                try {
                    getRelatedViewModel().saveUserSettingsAndProcced();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Utilities.displayAlertDialog(LoginActivity.this, "Ocorreu um erro ao guardar as configuracoes do utilizados.").show();
                }
            }else {
                getRelatedViewModel().setUserAuthentic(false);
                Utilities.displayAlertDialog(LoginActivity.this, "Os dados do utilizador sao invalidos").show();
            }
        }

    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {

    }
}