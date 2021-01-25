package mz.org.fgh.idartlite.dao.settings;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.AppSettings;

public class AppSettingsDaoImpl extends GenericDaoImpl<AppSettings, Integer> implements IAppSettingsDao {

    public AppSettingsDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public AppSettingsDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public AppSettingsDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
