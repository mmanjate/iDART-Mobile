package mz.org.fgh.idartlite.dao.clinicInfo;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.databasehelper.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.util.Utilities;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

public class ClinicInfoDaoImpl extends GenericDaoImpl<ClinicInformation, Integer> implements IClinicInfoDao {



    public ClinicInfoDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public ClinicInfoDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ClinicInfoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<ClinicInformation> getRAMsByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException {
        Where<ClinicInformation, Integer> queryBuilder = queryBuilder().limit(limit)
                .offset(offset).where().ge(ClinicInformation.COLUMN_REGISTER_DATE, start)
                .and()
                .le(ClinicInformation.COLUMN_REGISTER_DATE, end);

        if (Utilities.stringHasValue(reportType) && (reportType.equals(ClinicInformation.PARAM_RAM_STATUS_POSETIVE))){
            queryBuilder.and().eq(ClinicInformation.COLUMN_ADVERSE_REACTION_MEDICINE, true);
        }else if (Utilities.stringHasValue(reportType) && (reportType.equals(ClinicInformation.PARAM_RAM_STATUS_NEGATIVE))){
            queryBuilder.and().eq(ClinicInformation.COLUMN_ADVERSE_REACTION_MEDICINE, false);
        }

        return queryBuilder.query();

    }

    @Override
    public long countOfPeriod(Date start, Date end) throws SQLException {
        return queryBuilder().distinct().where().ge(ClinicInformation.COLUMN_REGISTER_DATE, start)
                .and()
                .le(ClinicInformation.COLUMN_REGISTER_DATE, end).countOf();

    }

    @Override
    public List<ClinicInformation> getAllByPatient(Patient patient) throws SQLException {
        return queryBuilder().orderBy(ClinicInformation.COLUMN_REGISTER_DATE,true).where().eq(ClinicInformation.COLUMN_PATIENT_ID, patient.getId()).query();
    }

    @Override
    public List<ClinicInformation> getAllClinicInfoByStatus(String status) throws SQLException {
        return queryBuilder().where().eq(ClinicInformation.COLUMN_SYNC_STATUS, status).query();
    }

    @Override
    public List<ClinicInformation> getPregnantPatientWithStartDateAndEndDateWithLimit(Application application, Date startDate, Date endDate, long offset, long limit) throws SQLException {

        QueryBuilder<Episode, Integer> episodeQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getEpisodeDao().queryBuilder();

        episodeQb.where().isNotNull(Episode.COLUMN_STOP_REASON);
        QueryBuilder<Patient, Integer> patientQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientDao().queryBuilder();
        patientQb.where().not().in(Patient.COLUMN_ID,episodeQb.selectRaw("patient_id"));
        QueryBuilder<ClinicInformation, Integer> clinicInformationQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getClinicInfoDao().queryBuilder();
        QueryBuilder<ClinicInformation, Integer> clinicInformationQb1 =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getClinicInfoDao().queryBuilder();
        QueryBuilder<Patient, Integer> patientInnerQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientDao().queryBuilder();


        clinicInformationQb1.groupBy(ClinicInformation.COLUMN_PATIENT_ID).join(patientInnerQb);

     //   dispenseQb1.join(prescriptionInnerQb);
        clinicInformationQb.orderBy(ClinicInformation.COLUMN_REGISTER_DATE,true).limit(limit)
                .offset(offset).where().eq(ClinicInformation.COLUMN_IS_PREGNANT,true).and().ge(ClinicInformation.COLUMN_REGISTER_DATE, startDate)
                .and()
                .le(ClinicInformation.COLUMN_REGISTER_DATE, endDate).and().in(ClinicInformation.COLUMN_REGISTER_DATE,clinicInformationQb1.selectRaw("max(register_date)"));
        System.out.println(clinicInformationQb.prepareStatementString());

        return clinicInformationQb.query();
    }

    @Override
    public List<ClinicInformation> getTBSuspectPatientWithStartDateAndEndDateWithLimit(Application application, Date startDate, Date endDate, long offset, long limit) throws SQLException {
        QueryBuilder<Episode, Integer> episodeQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getEpisodeDao().queryBuilder();

        episodeQb.where().isNotNull(Episode.COLUMN_STOP_REASON);
        QueryBuilder<Patient, Integer> patientQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientDao().queryBuilder();
        patientQb.where().not().in(Patient.COLUMN_ID,episodeQb.selectRaw("patient_id"));
        QueryBuilder<ClinicInformation, Integer> clinicInformationQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getClinicInfoDao().queryBuilder();
        QueryBuilder<ClinicInformation, Integer> clinicInformationQb1 =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getClinicInfoDao().queryBuilder();
        QueryBuilder<Patient, Integer> patientInnerQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientDao().queryBuilder();


        clinicInformationQb1.groupBy(ClinicInformation.COLUMN_PATIENT_ID).join(patientInnerQb);

        clinicInformationQb.orderBy(ClinicInformation.COLUMN_REGISTER_DATE,true).limit(limit)
                .offset(offset).where().eq(ClinicInformation.COLUMN_IS_FEVER,true).or().eq(ClinicInformation.COLUMN_IS_COUGH,true).
                or().eq(ClinicInformation.COLUMN_IS_LOST_WEIGHT,true).or().eq(ClinicInformation.COLUMN_IS_SWEATING,true).
                or().eq(ClinicInformation.COLUMN_HAS_FATIGUE_OR_TIREDNESS_LAST_TWO_WEEKS,true).and().ge(ClinicInformation.COLUMN_REGISTER_DATE, startDate)
                .and()
                .le(ClinicInformation.COLUMN_REGISTER_DATE, endDate).and().in(ClinicInformation.COLUMN_REGISTER_DATE,clinicInformationQb1.selectRaw("max(register_date)"));
        System.out.println(clinicInformationQb.prepareStatementString());

        return clinicInformationQb.query();
    }

    @Override
    public List<ClinicInformation> getTracedPatientsWithStartDateAndEndDateWithLimit(Application application, Date startDate, Date endDate, long offset, long limit) throws SQLException {

        QueryBuilder<Episode, Integer> episodeQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getEpisodeDao().queryBuilder();

        episodeQb.where().isNotNull(Episode.COLUMN_STOP_REASON);
        QueryBuilder<Patient, Integer> patientQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientDao().queryBuilder();
        patientQb.where().not().in(Patient.COLUMN_ID,episodeQb.selectRaw("patient_id"));
        QueryBuilder<ClinicInformation, Integer> clinicInformationQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getClinicInfoDao().queryBuilder();
        QueryBuilder<ClinicInformation, Integer> clinicInformationQb1 =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getClinicInfoDao().queryBuilder();
        QueryBuilder<Patient, Integer> patientInnerQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPatientDao().queryBuilder();


        clinicInformationQb1.groupBy(ClinicInformation.COLUMN_PATIENT_ID).join(patientInnerQb);

        clinicInformationQb.orderBy(ClinicInformation.COLUMN_REGISTER_DATE,true).limit(limit)
                .offset(offset).where().ge(ClinicInformation.COLUMN_REGISTER_DATE, startDate)
                .and()
                .le(ClinicInformation.COLUMN_REGISTER_DATE, endDate).and().in(ClinicInformation.COLUMN_REGISTER_DATE,clinicInformationQb1.selectRaw("max(register_date)"));
        System.out.println(clinicInformationQb.prepareStatementString());

        return clinicInformationQb.query();
    }

    @Override
    public List<ClinicInformation> getPatientTratmentFollowUpByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException {
        Where<ClinicInformation, Integer> queryBuilder = queryBuilder().limit(limit)
                .offset(offset).where().ge(ClinicInformation.COLUMN_REGISTER_DATE, start)
                .and()
                .le(ClinicInformation.COLUMN_REGISTER_DATE, end);

        if (Utilities.stringHasValue(reportType) && (reportType.equals(ClinicInformation.PARAM_FOLLOW_STATUS_WITH_LATE_DAYS))){
            queryBuilder.and().eq(ClinicInformation.COLUMN_HAS_PATIENT_CAME_CORRECT_DATE, false)
                    .and()
                    .ge(ClinicInformation.COLUMN_LATE_DAYS, 7);
        }

        return queryBuilder.query();
    }


}
