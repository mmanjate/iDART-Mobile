package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;

public class SecondSplashActivity extends BaseActivity {

    private ClinicService clinicService;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_splash);
        try {
            authenticateUser(getCurrentUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void authenticateUser(User user) throws SQLException {
        final boolean[] resultado = {false};
        userService = new UserService(getApplication(), user);
        clinicService = new ClinicService(getApplication(), user);
        new Thread(new Runnable() {
            public void run() {
                // do here some long operation
                try {
                    Thread.sleep(2000);
                    resultado[0] = userService.getUserAuthentication(currentClinic.getUuid(), user.getUserName(), user.getPassword());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        if (true) {
            System.out.println(currentClinic);
            System.out.println(user.getUserName());
            clinicService.saveClinic(currentClinic);
            user.setClinic(clinicService.getCLinic().get(0));
            userService.saveUser(user);
            // so para teste
            Intent intent = new Intent(getApplication(), HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            bundle.putSerializable("clinic", clinicService.getCLinic().get(0));
            intent.putExtras(bundle);
            startActivity(intent);
            //setToastMessage(successUserCreation);
        } else {
            //setToastMessage(restErrorMessage);
        }
    }
    @Override
    public BaseViewModel initViewModel() {
        return null;
    }
}