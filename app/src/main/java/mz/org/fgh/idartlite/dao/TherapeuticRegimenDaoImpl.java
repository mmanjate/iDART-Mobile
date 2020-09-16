package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;

import java.sql.SQLException;

public class TherapeuticRegimenDaoImpl extends GenericDaoImpl<TherapeuticRegimen, Integer> implements TherapeuticRegimenDao{

    public TherapeuticRegimenDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public TherapeuticRegimenDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public TherapeuticRegimenDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
