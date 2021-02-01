package mz.org.fgh.idartlite.dao.settings;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.AppSettings;

public interface IAppSettingsDao extends IGenericDao<AppSettings, Integer> {
    AppSettings getCentralServerUrl() throws SQLException;
}
