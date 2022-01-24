package mz.org.fgh.idartlite.dao.episode;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.databasehelper.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.model.patient.PatientAttribute;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

import android.app.Application;

public class EpisodeDaoImpl extends GenericDaoImpl<Episode, Integer> implements IEpisodeDao {



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
        return queryBuilder().orderBy(Episode.COLUMN_EPISODE_DATE,false).where().eq(Episode.COLUMN_PATIENT_ID, patient.getId()).and().isNotNull(Episode.COLUMN_STOP_REASON).queryForFirst();
    }

    public List<Episode> getAllByStatus(String status) throws SQLException {

        List<Episode> typeList = this.queryForEq(COLUMN_SYNC_STATUS, status);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList;

        return null;
    }

    public List<Episode> getAllEpisodeByStatusAndPatientDispenseStatus(Application application, String episodeStatus, String dispenseStatus) throws SQLException {

        QueryBuilder<Patient, Integer> patientQb = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientDao().queryBuilder();

        QueryBuilder<PatientAttribute, Integer> attributeQb = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientAttributeDao().queryBuilder();
        if (dispenseStatus.equals(PatientAttribute.PATIENT_DISPENSATION_STATUS_FALTOSO) || dispenseStatus.equals(PatientAttribute.PATIENT_DISPENSATION_ABANDONO)) {
            attributeQb.where().eq(PatientAttribute.COLUMN_ATTRIBUTE, PatientAttribute.PATIENT_DISPENSATION_STATUS).and()
                    .eq(PatientAttribute.COLUMN_VALUE, PatientAttribute.PATIENT_DISPENSATION_STATUS_FALTOSO).or()
                    .eq(PatientAttribute.COLUMN_VALUE, PatientAttribute.PATIENT_DISPENSATION_ABANDONO);
        } else {
            attributeQb.where().eq(PatientAttribute.COLUMN_ATTRIBUTE, PatientAttribute.PATIENT_DISPENSATION_STATUS).and()
                    .eq(PatientAttribute.COLUMN_VALUE, PatientAttribute.PATIENT_DISPENSATION_NORMAL);
        }
        patientQb.join(attributeQb);

        QueryBuilder<Episode, Integer> episodeQb =   IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getEpisodeDao().queryBuilder();
        episodeQb.join(patientQb).where().eq(COLUMN_SYNC_STATUS,episodeStatus);

        return episodeQb.orderBy(Episode.COLUMN_ID, false).query();
    }

    @Override
    public List<Episode> getAllStartEpisodesBetweenStartDateAndEndDate(Date start, Date end, long offset, long limit) throws SQLException {
        QueryBuilder<Episode, Integer> qb = queryBuilder();
        if (offset > 0 && limit > 0) qb.limit(limit).offset(offset);
        qb.where().isNotNull(Episode.COLUMN_START_REASON).and().isNull(Episode.COLUMN_STOP_REASON).and().ge(Episode.COLUMN_EPISODE_DATE, start)
                .and()
                .le(Episode.COLUMN_EPISODE_DATE, end);

        return qb.query();
    }

    @Override
    public List<Episode> getAllStartEpisodesBetweenStartDateAndEndDate(Date start, Date end) throws SQLException {
        return queryBuilder()
                .where().isNotNull(Episode.COLUMN_START_REASON).and().isNull(Episode.COLUMN_STOP_REASON).and().ge(Episode.COLUMN_EPISODE_DATE, start)
                .and()
                .le(Episode.COLUMN_EPISODE_DATE, end).query();
    }



}
