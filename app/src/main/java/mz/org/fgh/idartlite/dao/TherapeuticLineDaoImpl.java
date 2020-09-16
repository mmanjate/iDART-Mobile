package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.TherapeuticLine;

import java.sql.SQLException;

public class TherapeuticLineDaoImpl extends GenericDaoImpl<TherapeuticLine, Integer> implements TherapeuticLineDao{

    public TherapeuticLineDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public TherapeuticLineDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public TherapeuticLineDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
