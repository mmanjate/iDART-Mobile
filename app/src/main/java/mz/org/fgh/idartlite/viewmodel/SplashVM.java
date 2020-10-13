package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.service.DiseaseTypeService;
import mz.org.fgh.idartlite.service.PharmacyTypeService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.util.Utilities;

public class SplashVM extends BaseViewModel {
    private UserService userService;
    private PharmacyTypeService pharmacyTypeService;
    private DiseaseTypeService diseaseTypeService;

    public SplashVM(@NonNull Application application) {
        super(application);
        this.userService = new UserService(application);
        this.pharmacyTypeService = new PharmacyTypeService(application, null);
        diseaseTypeService = new DiseaseTypeService(application, null);
    }
    public boolean appHasNoUsersOnDB(){
        boolean usersOnDB = false;
        try {
            usersOnDB = userService.checkIfUsertableIsEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(getRelatedActivity(), "Ocorreu um erro ao verificar as configuração de utilizadores.").show();
        }
        return usersOnDB;
    }
    public List<PharmacyType> getAllPharmacyTypes(){
        List<PharmacyType> pharmacyTypes = null;
        try {
            pharmacyTypes = pharmacyTypeService.getAllPharmacyType();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pharmacyTypes;
    }

    public List<DiseaseType> getAllDiseaseTypes() throws SQLException {
        return diseaseTypeService.getAllDiseaseTypes();
    }
}
