package mz.org.fgh.idartlite.dao.clinicInfo;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Patient;

public interface IClinicInfoDao extends IGenericDao<ClinicInformation, Integer> {


    List<ClinicInformation> getRAMsByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException;

    long countOfPeriod(Date start, Date end) throws SQLException;

    public List<ClinicInformation> getAllByPatient(Patient patient) throws SQLException;

    public List<ClinicInformation> getAllClinicInfoByStatus(String status) throws SQLException;

    List<ClinicInformation> getPatientTratmentFollowUpByPeriod(Date start, Date end, long offset, long limit, String reportType) throws SQLException;
}
