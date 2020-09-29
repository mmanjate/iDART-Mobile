package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

public class PrescriptionService extends BaseService {

    protected TherapheuticRegimenService therapeuticRegimenService;
    protected TherapeuthicLineService therapeuticLineService;
    protected DispenseTypeService dispenseTypeService;

    public PrescriptionService(Application application, User currUser) {
        super(application, currUser);
        this.therapeuticRegimenService = new TherapheuticRegimenService(getApp(), currUser);
        this.therapeuticLineService = new TherapeuthicLineService(getApp(), currUser);
        this.dispenseTypeService = new DispenseTypeService(getApp(), currUser);

    }

    public List<Prescription> getAllPrescriptionsByPatient(Patient patient) throws SQLException{
        return getDataBaseHelper().getPrescriptionDao().getAllByPatient(patient);
    }


    public void createPrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().create(prescription);
        if(prescription.getPrescribedDrugs() != null)
        savePriscribedDrus(prescription.getPrescribedDrugs());
    }



    public void savePriscribedDrus(List<PrescribedDrug> prescribedDrugs) throws SQLException {
        for (PrescribedDrug prescribedDrug: prescribedDrugs) {
            getDataBaseHelper().getPrescribedDrugDao().create(prescribedDrug);
        }
    }

    public List<PrescribedDrug> getAllOfPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getPrescribedDrugDao().queryForEq(PrescribedDrug.COLUMN_PRESCRIPTION, prescription.getId());
    }


    public void updatePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().update(prescription);
        getDataBaseHelper().getPrescribedDrugDao().deleteBuilder().where().eq(PrescribedDrug.COLUMN_PRESCRIPTION, prescription.getId());
        getDataBaseHelper().getPrescribedDrugDao().create(prescription.getPrescribedDrugs());
    }


    public void deletePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().delete(prescription);
    }

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        return getDataBaseHelper().getPrescriptionDao().getLastPatientPrescription(patient);
    }

    public void saveLastPrescriptionFromRest(LinkedTreeMap<String, Object> patient,Patient localPatient) {

        try {
            Prescription prescription = new Prescription();
//            Patient localPatient = getPatient(Objects.requireNonNull(patient.get("uuidopenmrs")).toString());

            TherapeuticRegimen therapeuticRegimen = therapeuticRegimenService.getTherapeuticRegimenFromDescription(Objects.requireNonNull(patient.get("regimenome")).toString());
            TherapeuticLine therapeuticLine = therapeuticLineService.getTherapeuticLine(Objects.requireNonNull(patient.get("linhanome")).toString());

            prescription.setPrescriptionDate(getSqlDateFromString(Objects.requireNonNull(patient.get("prescriptiondate")).toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            prescription.setExpiryDate(getSqlDateFromString(Objects.requireNonNull(patient.get("prescriptionenddate")).toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            prescription.setPatient(localPatient);
            prescription.setPrescriptionSeq("0001");
            prescription.setSupply(Math.round(Float.parseFloat(Objects.requireNonNull(patient.get("duration")).toString())));
            prescription.setSyncStatus("U");
            prescription.setTherapeuticLine(therapeuticLine);
            prescription.setTherapeuticRegimen(therapeuticRegimen);
            prescription.setUrgentNotes(Objects.requireNonNull(patient.get("motivocriacaoespecial")).toString());
            prescription.setUrgentPrescription(Objects.requireNonNull(patient.get("prescricaoespecial")).toString());
            prescription.setUuid(UUID.randomUUID().toString());

            if (Float.parseFloat(Objects.requireNonNull(patient.get("dispensasemestral")).toString()) > 0)
                prescription.setDispenseType(dispenseTypeService.getDispenseType("Dispensa MensSemestral (DS)"));
            else if (Float.parseFloat(Objects.requireNonNull(patient.get("dispensatrimestral")).toString()) > 0)
                prescription.setDispenseType(dispenseTypeService.getDispenseType("Dispensa Trimestral (DT)"));
            else
                prescription.setDispenseType(dispenseTypeService.getDispenseType("Dispensa Mensal (DM)"));
            createPrescription(prescription);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
