package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.util.Utilities;

public class SplashVM extends BaseViewModel {
    UserService userService;

    public SplashVM(@NonNull Application application) {
        super(application);
        this.userService = new UserService(application);
    }

    public boolean appHasUsersOnDB(){
        boolean usersOnDB = false;
        try {
            usersOnDB = userService.checkIfUsertableIsEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(getRelatedActivity(), "Ocorreu um erro ao verificar as configuração de utilizadores.").show();
        }
        return usersOnDB;
    }

}
