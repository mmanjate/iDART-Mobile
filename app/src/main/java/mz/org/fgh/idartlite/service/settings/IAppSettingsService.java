package mz.org.fgh.idartlite.service.settings;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.AppSettings;

public interface IAppSettingsService extends IBaseService<AppSettings> {
    List<AppSettings> getAll() throws SQLException;

    void saveSetting(AppSettings record) throws SQLException;

    void runDataSync();

    void runMetadataSync();

    void runDataRemotion();
}
