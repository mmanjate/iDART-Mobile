package mz.org.fgh.idartlite.service.clinicInfo;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Patient;


public interface IClinicInfoService extends IBaseService<ClinicInformation> {




    public void createClinicInfo(ClinicInformation clinicInformation) throws SQLException ;
    public void updateClinicInfo(ClinicInformation clinicInformation) throws SQLException ;
    public void deleteClinicInfo(ClinicInformation clinicInformation) throws SQLException ;

    public List<ClinicInformation> getAllClinicInfosByPatient(Patient patient) throws SQLException;

    public List<ClinicInformation> getAllClinicInfoByStatus(String status) throws SQLException;

    public List<ClinicInformation> getPregnantPatientWithStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit, String reportType) throws SQLException;

    public List<ClinicInformation> getTBSuspectPatientWithStartDateAndEndDateWithLimit( Date startDate, Date endDate, long offset, long limit) throws SQLException;

    public List<ClinicInformation> getTracedPatientsWithStartDateAndEndDateWithLimit( Date startDate, Date endDate, long offset, long limit) throws SQLException;

    List<ClinicInformation> getPatientTratmentFollowUpByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException;

    List<ClinicInformation> getRAMsByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException;

    long countOfPeriod(Date start, Date end) throws SQLException;


}
