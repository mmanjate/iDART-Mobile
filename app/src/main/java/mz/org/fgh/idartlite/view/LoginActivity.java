package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import mz.org.fgh.idartlite.databinding.ActivityLoginBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.viewmodel.LoginVM;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding activityLoginBinding;
    private Spinner spinnerFarmacia;
    private TextView textFarmacia, textIcon;
    private List<Clinic> list;
    private ClinicService clinicService;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        activityLoginBinding.setViewModel(getRelatedViewModel());
        activityLoginBinding.executePendingBindings();

        userService = new UserService(getApplication(), getCurrentUser());
        spinnerFarmacia = findViewById(R.id.spinnerFarmacia);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                list = (List<Clinic>) bundle.getSerializable("clinicList");
            }
        }

        try {
            if(userService.checkIfUsertableIsEmpty()){
                populateSpinner();
                spinnerFarmacia.setVisibility(View.VISIBLE);
                ArrayAdapter<Clinic> adapter = new ArrayAdapter<Clinic>(this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFarmacia.setAdapter(adapter);
                spinnerFarmacia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Clinic clinic = (Clinic) parent.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } else{
                textFarmacia = findViewById(R.id.textFarmacia);
                textIcon = findViewById(R.id.textIcon);
                textIcon.setVisibility(View.GONE);
                textFarmacia.setVisibility(View.GONE);
                spinnerFarmacia.setVisibility(View.GONE);
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

    public void populateSpinner() throws SQLException {
        try {
            list = new ArrayList<Clinic>();
            clinicService = new ClinicService(getApplication(),getCurrentUser());
            list = clinicService.getCLinic();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }


    /*private void doWebSearch() {
        Thread searchThread = new Thread(this);
        searchThread.start();
    }*/

    @Override
    public LoginVM getRelatedViewModel() {
        return (LoginVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(LoginVM.class);
    }
}