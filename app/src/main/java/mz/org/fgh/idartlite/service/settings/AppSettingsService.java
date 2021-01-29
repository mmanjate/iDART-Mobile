package mz.org.fgh.idartlite.service.settings;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.service.RestRunDataForTestService;

public class AppSettingsService extends BaseService<AppSettings> implements IAppSettingsService {

    private RestRunDataForTestService restService;

    public AppSettingsService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public AppSettingsService(Application application) {
        super(application);

        //restService = new RestRunDataForTestService(application);
    }

    @Override
    public void save(AppSettings record) throws SQLException {
        super.save(record);
        getDataBaseHelper().getAppSettingsDao().create(record);
    }

    @Override
    public void update(AppSettings record) throws SQLException {
        super.update(record);
        getDataBaseHelper().getAppSettingsDao().update(record);
    }

    @Override
    public List<AppSettings> getAll() throws SQLException {
        return getDataBaseHelper().getAppSettingsDao().queryForAll();
    }

    @Override
    public AppSettings getCentralServerSettings() throws SQLException {
        return getDataBaseHelper().getAppSettingsDao().getCentralServerUrl();
    }

    @Override
    public void saveSetting(AppSettings record) throws SQLException {

        if (record.getId() > 0) update(record);
        else if (record.getId() == 0) save(record);
    }

    @Override
    public void runDataSync() {

        restService.runDataSync();
    }

    @Override
    public void runMetadataSync() {

        restService.runMetaDataSync();
    }

    @Override
    public void runDataRemotion() {

    }
}
