package mz.org.fgh.idartlite.dao.dispense;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.dao.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;

public class DispenseDaoImpl extends GenericDaoImpl<Dispense, Integer> implements IDispenseDao {

    public DispenseDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DispenseDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DispenseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Dispense> getAllByPrescription(Prescription prescription) throws SQLException {
        return queryBuilder().where().eq(Dispense.COLUMN_PRESCRIPTION, prescription.getId()).query();
    }

    @Override
    public long countAllOfPrescription(Prescription prescription) throws SQLException {
        return queryBuilder().where().eq(Dispense.COLUMN_PRESCRIPTION, prescription.getId()).countOf();
    }

    public List<Dispense> getAllOfPatient(Application application, Patient patient) throws SQLException {

        QueryBuilder<Prescription, Integer> prescriptionQb = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getPrescriptionDao().queryBuilder();
        prescriptionQb.where().eq(Prescription.COLUMN_PATIENT_ID, patient.getId());

        QueryBuilder<TherapeuticLine, Integer> therapeuticLineQb = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getTherapeuticLineDao().queryBuilder();
        prescriptionQb.join(therapeuticLineQb);

        QueryBuilder<Dispense, Integer> dispenseQb =   IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDispenseDao().queryBuilder();
        dispenseQb.join(prescriptionQb);

        List<Dispense> dispenses = dispenseQb.orderBy(Dispense.COLUMN_NEXT_PICKUP_DATE,false).query();

        System.out.println(dispenseQb.orderBy(Dispense.COLUMN_NEXT_PICKUP_DATE,false).prepareStatementString());

        return dispenses;
    }

    @Override
    public Dispense getLastDispensePrescription(Prescription prescription) throws SQLException {

        List<Dispense> dispenseList = null;
        QueryBuilder<Dispense, Integer> dispenseQb = queryBuilder();

        dispenseQb.where().eq(Dispense.COLUMN_PRESCRIPTION, prescription.getId());

        dispenseList = dispenseQb.orderBy(Dispense.COLUMN_PICKUP_DATE, false).limit(1L).query();

        if(dispenseList.size()!= 0)
            return dispenseList.get(0);

        return null;

    }

    @Override
    public List<Dispense> getDispensesBetweenStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException {
        return queryBuilder().limit(limit)
                .offset(offset).where().ge(Dispense.COLUMN_PICKUP_DATE, startDate)
                .and()
                .le(Dispense.COLUMN_PICKUP_DATE, endDate).query();
    }



    @Override
    public List<Dispense> getDispensesBetweenStartDateAndEndDate(Date startDate, Date endDate) throws SQLException {
        return queryBuilder().where().ge(Dispense.COLUMN_PICKUP_DATE, startDate)
                .and()
                .le(Dispense.COLUMN_PICKUP_DATE, endDate).query();
    }

    @Override
    public List<Dispense> getAllDispensesByStatus(String status) throws SQLException {
        return queryBuilder().where().eq(Dispense.COLUMN_SYNC_STATUS, status).query();
    }


}
