package mz.org.fgh.idartlite.dao.drug;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.TherapeuticLine;

import java.sql.SQLException;
import java.util.List;

public class TherapeuticLineDaoImpl extends GenericDaoImpl<TherapeuticLine, Integer> implements ITherapeuticLineDao {

    public TherapeuticLineDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public TherapeuticLineDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public TherapeuticLineDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<TherapeuticLine> getAll() throws SQLException {
        return queryForAll();
    }

    @Override
    public TherapeuticLine getTherapeuticLineByCode(String code) throws SQLException {
        return queryBuilder().where().eq(TherapeuticLine.COLUMN_CODE, code).queryForFirst();
    }
}
