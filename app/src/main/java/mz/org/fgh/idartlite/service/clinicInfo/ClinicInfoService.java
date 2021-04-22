package mz.org.fgh.idartlite.service.clinicInfo;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.patient.PatientService;

import static mz.org.fgh.idartlite.util.DateUtilities.getSqlDateFromString;

public class ClinicInfoService extends BaseService<ClinicInformation> implements IClinicInfoService {

    public ClinicInfoService(Application application, User currUser) {
        super(application, currUser);
       // this.patientService = new PatientService(getApp(), currUser);
    }

    public ClinicInfoService(Application application) {
        super(application);
    }

    @Override
    public void createClinicInfo(ClinicInformation clinicInformation) throws SQLException {
         getDataBaseHelper().getClinicInfoDao().create(clinicInformation);
    }

    @Override
    public void updateClinicInfo(ClinicInformation clinicInformation) throws SQLException {
        getDataBaseHelper().getClinicInfoDao().update(clinicInformation);
    }

    @Override
    public void deleteClinicInfo(ClinicInformation clinicInformation) throws SQLException {
        getDataBaseHelper().getClinicInfoDao().delete(clinicInformation);
    }

    @Override
    public List<ClinicInformation> getAllClinicInfosByPatient(Patient patient) throws SQLException {
        return getDataBaseHelper().getClinicInfoDao().getAllByPatient(patient);
    }

    @Override
    public List<ClinicInformation> getAllClinicInfoByStatus(String status) throws SQLException {
        return getDataBaseHelper().getClinicInfoDao().getAllClinicInfoByStatus(status);
    }

    @Override
    public List<ClinicInformation> getPregnantPatientWithStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException {
        return getDataBaseHelper().getClinicInfoDao().getPregnantPatientWithStartDateAndEndDateWithLimit(application,startDate,endDate,offset,limit);
    }

    @Override
    public List<ClinicInformation> getTBSuspectPatientWithStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException {
        return getDataBaseHelper().getClinicInfoDao().getTBSuspectPatientWithStartDateAndEndDateWithLimit(application,startDate,endDate,offset,limit);
    }

    @Override
    public List<ClinicInformation> getTracedPatientsWithStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException {
        return getDataBaseHelper().getClinicInfoDao().getTracedPatientsWithStartDateAndEndDateWithLimit(application,startDate,endDate,offset,limit);
    }
}
