package mz.org.fgh.idartlite.service.splash;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.PharmacyType;

public interface ISplashService extends IBaseService {

    AppSettings getCentralServerSettings() throws SQLException;

    void saveAppSettings(List<AppSettings> settings) throws SQLException;

    boolean appHasNoUsersOnDB();

    List<PharmacyType> getAllPharmacyTypes();

    List<DiseaseType> getAllDiseaseTypes() throws SQLException;

    List<Drug> getAllDrugs() throws SQLException;

    void syncApp(RestResponseListener listener);

    List<AppSettings> getAllSettings() throws SQLException;
}
