package mz.org.fgh.idartlite.service.clinicInfo;

import android.app.Application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;

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
    public List<ClinicInformation> getPatientTratmentFollowUpByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException {
        List<ClinicInformation> fectedList = getDataBaseHelper().getClinicInfoDao().getPatientTratmentFollowUpByPeriod(start, end, offset, limit, reportType);

        if (!Utilities.listHasElements(fectedList)) return null;

        List<ClinicInformation> toReturn = new ArrayList<>();

        for (ClinicInformation info : fectedList){
            if (!Utilities.listHasElements(toReturn)) toReturn.add(info);

            if (!patientHasRecord(toReturn, info)) {
                toReturn.add(info);
            }else {
                if (isToBeCounted(toReturn, info)) toReturn.add(info);
            }
        }
        return toReturn;
    }

    @Override
    public List<ClinicInformation> getRAMsByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException {
        List<ClinicInformation> fectedList = getDataBaseHelper().getClinicInfoDao().getRAMsByPeriod(start, end, offset, limit, reportType);

        if (!Utilities.listHasElements(fectedList)) return null;

        List<ClinicInformation> toReturn = new ArrayList<>();

        for (ClinicInformation info : fectedList){
            if (!Utilities.listHasElements(toReturn)) toReturn.add(info);

            if (!patientHasRecord(toReturn, info)) {
                toReturn.add(info);
            }else {
                if (isToBeCounted(toReturn, info)) toReturn.add(info);
            }
        }
        return toReturn;
    }

    private boolean patientHasRecord(List<ClinicInformation> toReturn, ClinicInformation info){
        for (ClinicInformation addedInfo : toReturn){
            if (addedInfo.getPatient().equals(info.getPatient())) return true;
        }
        return false;
    }

    private boolean isToBeCounted(List<ClinicInformation> toReturn, ClinicInformation info) {
        for (ClinicInformation addedInfo : toReturn){
            if (addedInfo.getPatient().equals(info.getPatient()) && (DateUtilities.dateDiff(addedInfo.getRegisterDate(), info.getRegisterDate(), DateUtilities.HOUR_FORMAT) > 0)) {
                toReturn.remove(addedInfo);
                return true;
            }
        }
        return false;
    }

    @Override
    public long countOfPeriod(Date start, Date end) throws SQLException {
        return getDataBaseHelper().getClinicInfoDao().countOfPeriod(start, end);
    }

    @Override
    public List<ClinicInformation> getPregnantPatientWithStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit,String reportType) throws SQLException {
        return getDataBaseHelper().getClinicInfoDao().getPregnantPatientWithStartDateAndEndDateWithLimit(application,startDate,endDate,offset,limit,reportType);
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
