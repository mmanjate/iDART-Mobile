package mz.org.fgh.idartlite.dao;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;


import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;

import java.sql.SQLException;
import java.util.List;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

public class EpisodeDaoImpl extends GenericDaoImpl<Episode, Integer> implements EpisodeDao {



    public EpisodeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public EpisodeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public EpisodeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Episode> getAllByPatient(Patient patient) throws SQLException {
        return queryBuilder().orderBy(Episode.COLUMN_EPISODE_DATE,true).where().eq(Episode.COLUMN_PATIENT_ID, patient.getId()).query();
    }

    @Override
    public Episode getLatestByPatient(Patient patient) throws SQLException {
        return queryBuilder().orderBy(Episode.COLUMN_EPISODE_DATE,false).where().eq(Episode.COLUMN_PATIENT_ID, patient.getId()).queryForFirst();
    }

    @Override
    public Episode findEpisodeWithStopReasonByPatient(Patient patient) throws SQLException {
        return queryBuilder().orderBy(Episode.COLUMN_EPISODE_DATE,true).where().eq(Episode.COLUMN_PATIENT_ID, patient.getId()).and().isNotNull(Episode.COLUMN_STOP_REASON).queryForFirst();
    }

    public List<Episode> getAllEpisodeByStatus(String status) throws SQLException {

        List<Episode> typeList = this.queryForEq(COLUMN_SYNC_STATUS, status);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList;

        return null;
    }
}
