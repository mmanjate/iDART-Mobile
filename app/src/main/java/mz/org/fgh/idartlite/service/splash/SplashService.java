package mz.org.fgh.idartlite.service.splash;

import android.app.Application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.service.Disease.RestDiseaseTypeService;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseTypeService;
import mz.org.fgh.idartlite.rest.service.Drug.RestDrugService;
import mz.org.fgh.idartlite.rest.service.Form.RestFormService;
import mz.org.fgh.idartlite.rest.service.Regimen.RestTherapeuticRegimenService;
import mz.org.fgh.idartlite.rest.service.TherapeuticLine.RestTherapeuticLineService;
import mz.org.fgh.idartlite.rest.service.clinic.RestClinicService;
import mz.org.fgh.idartlite.rest.service.clinic.RestPharmacyTypeService;
import mz.org.fgh.idartlite.service.clinic.IPharmacyTypeService;
import mz.org.fgh.idartlite.service.clinic.PharmacyTypeService;
import mz.org.fgh.idartlite.service.drug.DiseaseTypeService;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDiseaseTypeService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.service.settings.AppSettingsService;
import mz.org.fgh.idartlite.service.settings.IAppSettingsService;
import mz.org.fgh.idartlite.service.user.IUserService;
import mz.org.fgh.idartlite.service.user.UserService;
import mz.org.fgh.idartlite.util.Utilities;

public class SplashService extends BaseService implements ISplashService {

    private IUserService userService;
    private IPharmacyTypeService pharmacyTypeService;
    private IDiseaseTypeService diseaseTypeService;
    private IDrugService drugService;
    private IAppSettingsService appSettingsService;

    private List<Clinic> clinicList;

    private RestClinicService restClinicService;

    public SplashService(Application application) {
        super(application);

    }

    @Override
    public void init(Application application, User currentUser) {
        super.init(application, currentUser);

        this.userService = new UserService(application);
        this.pharmacyTypeService = new PharmacyTypeService(application, null);
        diseaseTypeService = new DiseaseTypeService(application, null);
        drugService = new DrugService(application, null);
        appSettingsService = new AppSettingsService(getApplication());
        restClinicService = new RestClinicService(getApplication(), null);

        clinicList = new ArrayList<Clinic>();
    }

    @Override
    public AppSettings getCentralServerSettings() throws SQLException {
        return appSettingsService.getCentralServerSettings();
    }

    @Override
    public void saveAppSettings(List<AppSettings> settings) throws SQLException {
        for (AppSettings appSetting : settings){
            appSettingsService.saveSetting(appSetting);
        }
    }

    @Override
    public boolean appHasNoUsersOnDB(){
        boolean usersOnDB = false;
        try {
            usersOnDB = userService.checkIfUsertableIsEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersOnDB;
    }

    @Override
    public List<PharmacyType> getAllPharmacyTypes(){
        List<PharmacyType> pharmacyTypes = null;
        try {
            pharmacyTypes = pharmacyTypeService.getAllPharmacyType();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pharmacyTypes;
    }

    @Override
    public List<DiseaseType> getAllDiseaseTypes() throws SQLException {
        return diseaseTypeService.getAllDiseaseTypes();
    }

    @Override
    public List<Drug> getAllDrugs() throws SQLException {
        return drugService.getAll();
    }

    @Override
    public void syncApp(RestResponseListener listener){
        new Thread(() -> {
            RestPharmacyTypeService.restGetAllPharmacyType();

            RestDiseaseTypeService.restGetAllDiseaseType();

            RestFormService.restGetAllForms();

            try {
                long timeOut = 60000;
                long requestTime = 0;
                while (!Utilities.listHasElements(getAllDiseaseTypes())) {
                    Thread.sleep(2000);
                    requestTime += 2000;
                    if (requestTime >= timeOut) {
                        break;
                    }
                }

                RestDrugService.restGetAllDrugs();


                requestTime = 0;
                while (!Utilities.listHasElements(getAllDrugs())) {
                    Thread.sleep(4000);
                    requestTime += 4000;
                    if (requestTime >= timeOut) {
                        break;
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            RestDispenseTypeService.restGetAllDispenseType();
            RestTherapeuticRegimenService.restGetAllTherapeuticRegimen();
            RestTherapeuticLineService.restGetAllTherapeuticLine();

            try {
                clinicList = restClinicService.restGetAllClinic(listener);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            long timeOut = 60000;
            long requestTime = 0;
            while (!Utilities.listHasElements(clinicList)) {
                try {
                    Thread.sleep(2000);
                    requestTime += 2000;
                    if (requestTime >= timeOut) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (Utilities.listHasElements(clinicList)) {
                listener.doOnRestSucessResponseObjects("success", clinicList);
            }else listener.doOnRestErrorResponse("Não foi possível buscar os dados das clínicas");

        }).start();
    }

    @Override
    public List<AppSettings> getAllSettings() throws SQLException {
        return appSettingsService.getAll();
    }
}
