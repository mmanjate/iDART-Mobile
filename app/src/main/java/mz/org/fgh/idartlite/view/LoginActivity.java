package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
    private List<Clinic> clinicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(getRelatedViewModel());

        changeViewToNormalMode();

        clinicList = new ArrayList<Clinic>();
        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                clinicList.add(0, new Clinic());
                clinicList.addAll((List<Clinic>) bundle.getSerializable("clinicList"));
            }
        }

        try {
            if(getRelatedViewModel().appHasUsersOnDB()){
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
                activityLoginBinding.textFarmacia.setVisibility(View.GONE);
                activityLoginBinding.spinnerFarmacia.setVisibility(View.GONE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(LoginActivity.this, "Ocorreu um erro ao verificar as configuração de utilizadores.").show();
        }
    }

    public void changeViewToAuthenticatingMode(){
        activityLoginBinding.button.setVisibility(View.GONE);
        activityLoginBinding.loginProgressBox.setVisibility(View.VISIBLE);
    }

    public void changeViewToNormalMode(){
        activityLoginBinding.button.setVisibility(View.VISIBLE);
        activityLoginBinding.loginProgressBox.setVisibility(View.GONE);
    }

    public void populateSpinner() {
        ArrayAdapter<Clinic> adapterSpinnerClinic = new ArrayAdapter<Clinic>(this, android.R.layout.simple_spinner_item, clinicList);
        adapterSpinnerClinic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLoginBinding.spinnerFarmacia.setAdapter(adapterSpinnerClinic);
        activityLoginBinding.spinnerFarmacia.setVisibility(View.VISIBLE);
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

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        changeViewToNormalMode();

        if (Utilities.stringHasValue(flag)){
            if (flag.equals(UserService.auth)) {
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