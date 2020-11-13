package mz.org.fgh.idartlite.service.prescription;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.dispense.DispenseTypeService;
import mz.org.fgh.idartlite.service.drug.TherapeuthicLineService;
import mz.org.fgh.idartlite.service.drug.TherapheuticRegimenService;

import static java.util.Objects.*;

public class PrescriptionService extends BaseService implements IPrescriptionService {

    protected TherapheuticRegimenService therapeuticRegimenService;
    protected TherapeuthicLineService therapeuticLineService;
    protected DispenseTypeService dispenseTypeService;
    protected PrescribedDrugService prescribedDrugService;

    public PrescriptionService(Application application, User currUser) {
        super(application, currUser);
        this.therapeuticRegimenService = new TherapheuticRegimenService(getApp(), currUser);
        this.therapeuticLineService = new TherapeuthicLineService(getApp(), currUser);
        this.dispenseTypeService = new DispenseTypeService(getApp(), currUser);
        this.prescribedDrugService = new PrescribedDrugService(getApp(),currUser);

    }

    public List<Prescription> getAllPrescriptionsByPatient(Patient patient) throws SQLException {
        return getDataBaseHelper().getPrescriptionDao().getAllByPatient(patient);
    }


    public void createPrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().create(prescription);
        if (prescription.getPrescribedDrugs() != null)
            savePriscribedDrus(prescription.getPrescribedDrugs());
    }


    public void savePriscribedDrus(List<PrescribedDrug> prescribedDrugs) throws SQLException {
        for (PrescribedDrug prescribedDrug : prescribedDrugs) {
            getDataBaseHelper().getPrescribedDrugDao().create(prescribedDrug);
        }
    }

    public void updatePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().update(prescription);

        DeleteBuilder<PrescribedDrug, Integer> deleteBuilder = getDataBaseHelper().getPrescribedDrugDao().deleteBuilder();
        deleteBuilder.where().eq(PrescribedDrug.COLUMN_PRESCRIPTION, prescription.getId());
        deleteBuilder.delete();

        getDataBaseHelper().getPrescribedDrugDao().create(prescription.getPrescribedDrugs());
    }

    public void updatePrescriptionEntity(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().update(prescription);
    }

    public void closePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().closePrescription(prescription);
    }

    public void deletePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().delete(prescription);
    }

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        return getDataBaseHelper().getPrescriptionDao().getLastPatientPrescription(patient);
    }

    public void saveLastPrescriptionFromRest(LinkedTreeMap<String, Object> patient, Patient localPatient) {

        try {
            Prescription prescription = new Prescription();

            TherapeuticRegimen therapeuticRegimen = therapeuticRegimenService.getTherapeuticRegimenByDescription(requireNonNull(patient.get("regimenome")).toString());
            TherapeuticLine therapeuticLine = therapeuticLineService.getTherapeuticLineByCode(requireNonNull(patient.get("linhanome")).toString());

            prescription.setPrescriptionDate(getSqlDateFromString(requireNonNull(patient.get("prescriptiondate")).toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            if (patient.get("prescriptionenddate") != null)
                prescription.setExpiryDate(getSqlDateFromString(requireNonNull(patient.get("prescriptionenddate")).toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            prescription.setPatient(localPatient);
            prescription.setPrescriptionSeq("0001");
            prescription.setSupply(Math.round(Float.parseFloat(requireNonNull(patient.get("duration")).toString())));
            prescription.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
            prescription.setTherapeuticLine(therapeuticLine);
            prescription.setTherapeuticRegimen(therapeuticRegimen);
            prescription.setUrgentNotes(requireNonNull(patient.get("motivocriacaoespecial")).toString());
            prescription.setUrgentPrescription(requireNonNull(patient.get("prescricaoespecial")).toString());
            prescription.setUuid(UUID.randomUUID().toString());

            if ((int) Float.parseFloat(requireNonNull(patient.get("dispensasemestral")).toString()) > 0)
                prescription.setDispenseType(dispenseTypeService.getDispenseTypeByCode("Dispensa Semestral (DS)"));
            else if ((int) Float.parseFloat(requireNonNull(patient.get("dispensatrimestral")).toString()) > 0)
                prescription.setDispenseType(dispenseTypeService.getDispenseTypeByCode("Dispensa Trimestral (DT)"));
            else
                prescription.setDispenseType(dispenseTypeService.getDispenseTypeByCode("Dispensa Mensal (DM)"));
            createPrescription(prescription);

                prescribedDrugService.savePrescribedDrug(prescription,requireNonNull(patient.get("jsonprescribeddrugs")).toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
