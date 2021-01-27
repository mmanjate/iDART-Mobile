package mz.org.fgh.idartlite.service.clinicInfo;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;


public interface IClinicInfoService extends IBaseService<ClinicInformation> {




    public void createClinicInfo(ClinicInformation clinicInformation) throws SQLException ;
    public void updateClinicInfo(ClinicInformation clinicInformation) throws SQLException ;
    public void deleteClinicInfo(ClinicInformation clinicInformation) throws SQLException ;

    public List<ClinicInformation> getAllClinicInfosByPatient(Patient patient) throws SQLException;

    public List<ClinicInformation> getAllClinicInfoByStatus(String status) throws SQLException;




}
