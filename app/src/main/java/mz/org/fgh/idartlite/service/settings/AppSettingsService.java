package mz.org.fgh.idartlite.service.settings;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.User;

public class AppSettingsService extends BaseService<AppSettings> implements IAppSettingsService {

    public AppSettingsService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public AppSettingsService(Application application) {
        super(application);
    }

    @Override
    public void save(AppSettings record) throws SQLException {
        super.save(record);
    }

    @Override
    public void update(AppSettings record) throws SQLException {
        super.update(record);
    }
}
