package mz.org.fgh.idartlite.service;

import android.app.Application;
import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.dao.PatientDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;

import static mz.org.fgh.idartlite.model.Patient.COLUMN_UUID;

public class PatientService extends BaseService {

    private PatientDao patientDao;
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

    public List<Patient> searchPatientByParamAndClinic(String param, Clinic clinic, long offset, long limit) throws SQLException {
            return getDataBaseHelper().getPatientDao().searchPatientByParamAndClinic(param, clinic, offset , limit);

    }

    public List<Patient> getALLPatient() throws  SQLException {
        return patientDao.queryForAll();
    }

    public void  savePatient(Patient patient) throws SQLException {
        getDataBaseHelper().getPatientDao().create(patient);
    }

    public Patient getPatient(String uuid) throws SQLException {

        List<Patient> typeList = getDataBaseHelper().getPatientDao().queryForEq(COLUMN_UUID, uuid);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public boolean checkPatient(LinkedTreeMap<String, Object> patient) {

        boolean result = false;

        try {
            Patient localPatient = getPatient((Objects.requireNonNull(patient.get("uuidopenmrs")).toString()));

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

            Clinic clinic = clinicService.getClinic(Objects.requireNonNull(patient.get("clinicuuid")).toString());

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

    private String getFullAdreess(String address1, String address2, String address3) {
        return address1 + " " + address2 + " " + address3;
    }

}
