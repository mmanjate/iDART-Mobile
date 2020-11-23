package mz.org.fgh.idartlite.viewmodel.splash;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.service.clinic.IPharmacyTypeService;
import mz.org.fgh.idartlite.service.clinic.PharmacyTypeService;
import mz.org.fgh.idartlite.service.drug.DiseaseTypeService;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDiseaseTypeService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.service.user.IUserService;
import mz.org.fgh.idartlite.service.user.UserService;
import mz.org.fgh.idartlite.util.Utilities;

public class SplashVM extends BaseViewModel {
    private IUserService userService;
    private IPharmacyTypeService pharmacyTypeService;
    private IDiseaseTypeService diseaseTypeService;
    private IDrugService drugService;

    public SplashVM(@NonNull Application application) {
        super(application);
        this.userService = new UserService(application);
        this.pharmacyTypeService = new PharmacyTypeService(application, null);
        diseaseTypeService = new DiseaseTypeService(application, null);
        drugService = new DrugService(application, null);
    }

    @Override
    protected IBaseService initRelatedService() {
        return null;
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

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

    public List<Drug> getAllDrugs() throws SQLException {
        return drugService.getAll();
    }
}
