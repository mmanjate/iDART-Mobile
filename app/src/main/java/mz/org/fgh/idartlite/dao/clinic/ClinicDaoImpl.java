package mz.org.fgh.idartlite.dao.clinic;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Episode;

import java.sql.SQLException;
import java.util.List;

import static mz.org.fgh.idartlite.model.Clinic.COLUMN_UUID;

public class ClinicDaoImpl extends GenericDaoImpl<Clinic, Integer> implements IClinicDao {

    public ClinicDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public ClinicDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ClinicDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Clinic> getAllClinics() throws SQLException {
       return queryForAll();
    }

    @Override
    public Clinic getClinicByUuid(String uuid) throws SQLException {
        return queryBuilder().where().eq(COLUMN_UUID, uuid).queryForFirst();
    }
}
