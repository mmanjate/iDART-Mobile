package mz.org.fgh.idartlite.service.patient;

import android.app.Application;
import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.patient.IPatientDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.prescription.PrescriptionService;

import static mz.org.fgh.idartlite.model.Patient.COLUMN_UUID;

public class PatientService extends BaseService implements IPatientService {

    private IPatientDao patientDao;
    protected ClinicService clinicService;
    protected PrescriptionService prescriptionService;
    protected EpisodeService episodeService ;

    public PatientService(Application application, User currentUser) {
        super(application, currentUser);
        try{
            patientDao = getDataBaseHelper().getPatientDao();
            this.prescriptionService = new PrescriptionService(application, currentUser);
            this.episodeService = new EpisodeService(application, currentUser);
            this.clinicService = new ClinicService(application, currentUser);


        }catch (SQLException sql){
            Log.i("erro ", sql.getMessage());
        }
    }

    public PatientService(Application application) {
        super(application);

        try {
            patientDao = getDataBaseHelper().getPatientDao();
        } catch (SQLException sql) {
            Log.i("erro ", sql.getMessage());
        }
    }

    public List<Patient> searchPatientByParamAndClinic(String param, Clinic clinic, long offset, long limit) throws SQLException {
            return patientDao.searchPatientByParamAndClinic(param, clinic, offset , limit);

    }

    public List<Patient> getALLPatient() throws  SQLException {
        return patientDao.queryForAll();
    }

    public void  savePatient(Patient patient) throws SQLException {
        patientDao.create(patient);
    }

    public int countNewPatientsByPeriod(Date start, Date end) throws SQLException {
        return patientDao.countNewPatientsByPeriod(start, end, getApplication());
    }

    public Patient getPatientByUuid(String uuid) throws SQLException {

        Patient typeList = patientDao.getPatientByUuid(uuid);

        if (typeList != null) return typeList;

        return null;
    }

    public boolean checkPatient(LinkedTreeMap<String, Object> patient) {

        boolean result = false;

        try {
            Patient localPatient = getPatientByUuid((Objects.requireNonNull(patient.get("uuidopenmrs")).toString()));

            if (localPatient != null)
                result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void saveOnPatient(LinkedTreeMap<String, Object> patient) {

        Patient localPatient = new Patient();
        try {

            Clinic clinic = clinicService.getClinicByUuid(Objects.requireNonNull(patient.get("clinicuuid")).toString());

            String concatAdrees = getFullAdreess(Objects.requireNonNull(patient.get("address1")).toString(),
                    Objects.requireNonNull(patient.get("address2")).toString(),
                    Objects.requireNonNull(patient.get("address3")).toString());

            localPatient.setAddress(concatAdrees);
            localPatient.setBirthDate(getSqlDateFromString(Objects.requireNonNull(patient.get("dateofbirth")).toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            localPatient.setClinic(clinic);
            localPatient.setFirstName(Objects.requireNonNull(patient.get("firstnames")).toString());
            localPatient.setLastName(Objects.requireNonNull(patient.get("lastname")).toString());
            localPatient.setGender(Objects.requireNonNull(patient.get("sex")).toString());
            localPatient.setNid(Objects.requireNonNull(patient.get("patientid")).toString());
            localPatient.setPhone(Objects.requireNonNull(patient.get("workphone")).toString());
            localPatient.setUuid(Objects.requireNonNull(patient.get("uuidopenmrs")).toString());
            localPatient.setStartARVDate(getSqlDateFromString(Objects.requireNonNull(patient.get("datainiciotarv")).toString(), "dd MMM yyyy"));
            savePatient(localPatient);
            episodeService.saveEpisodeFromRest(patient, localPatient);
            prescriptionService.saveLastPrescriptionFromRest(patient, localPatient);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
      this.getDataBaseHelper().getPatientDao().update(patient);
    }

    @Override
    public Patient checkExistsPatientWithNID(String nid) throws SQLException {
       return getDataBaseHelper().getPatientDao().checkExistsPatientWithNID(nid);
    }

    private String getFullAdreess(String address1, String address2, String address3) {
        return address1 + " " + address2 + " " + address3;
    }

}
