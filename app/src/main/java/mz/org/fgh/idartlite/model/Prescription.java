package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import mz.org.fgh.idartlite.base.BaseModel;

import java.util.Date;
import java.util.Objects;

public class Prescription extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PRESCRIPTION_DATE = "prescription_date";
	public static final String COLUMN_SUPPLY = "supply";
	public static final String COLUMN_EXPIRY_DATE = "expiry_date";
	public static final String COLUMN_URGENT_PRESCRIPTION = "urgent_prescription";
	public static final String COLUMN_URGENT_NOTES = "urgent_notes";
	public static final String COLUMN_REGIMEN_ID = "regimen_id";
	public static final String COLUMN_LINE_ID = "line_id";
	public static final String COLUMN_DISPENSE_TYPE_ID = "dispense_type_id";
	public static final String COLUMN_PRESCRIPTION_SEQ = "prescription_seq";
	public static final String COLUMN_UUID = "uuid";
	public static final String COLUMN_PATIENT_ID = "patient_id";

	@DatabaseField(columnName = "id", id = true)
	private int id;

	@DatabaseField(columnName = "prescription_date")
	private Date prescriptionDate;

	@DatabaseField(columnName = "supply")
	private int supply;

	@DatabaseField(columnName = "expiry_date")
	private Date expiryDate;

	@DatabaseField(columnName = "urgent_prescription")
	private boolean urgentPrescription;

	@DatabaseField(columnName = "urgent_notes")
	private String urgentNotes;

	@DatabaseField(columnName = "regimen_id")
	private TherapeuticRegimen regimenId;

	@DatabaseField(columnName = "line_id")
	private TherapeuticLine lineId;

	//private DispenseType dispenseTypeId;

	@DatabaseField(columnName = "prescription_seq")
	private String prescriptionSeq;

	@DatabaseField(columnName = "uuid")
	private String uuid;

	@DatabaseField(columnName = "patient_id")
	private Patient patientId;

	//public Drug drugList() {
	//	return null;
	//}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public int getSupply() {
		return supply;
	}

	public void setSupply(int supply) {
		this.supply = supply;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isUrgentPrescription() {
		return urgentPrescription;
	}

	public void setUrgentPrescription(boolean urgentPrescription) {
		this.urgentPrescription = urgentPrescription;
	}

	public String getUrgentNotes() {
		return urgentNotes;
	}

	public void setUrgentNotes(String urgentNotes) {
		this.urgentNotes = urgentNotes;
	}

	public TherapeuticRegimen getRegimenId() {
		return regimenId;
	}

	public void setRegimenId(TherapeuticRegimen regimenId) {
		this.regimenId = regimenId;
	}

	public TherapeuticLine getLineId() {
		return lineId;
	}

	public void setLineId(TherapeuticLine lineId) {
		this.lineId = lineId;
	}

	public String getPrescriptionSeq() {
		return prescriptionSeq;
	}

	public void setPrescriptionSeq(String prescriptionSeq) {
		this.prescriptionSeq = prescriptionSeq;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Patient getPatientId() {
		return patientId;
	}

	public void setPatientId(Patient patientId) {
		this.patientId = patientId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Prescription that = (Prescription) o;
		return prescriptionDate.equals(that.prescriptionDate) &&
				expiryDate.equals(that.expiryDate) &&
				prescriptionSeq.equals(that.prescriptionSeq) &&
				uuid.equals(that.uuid);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(prescriptionDate, expiryDate, prescriptionSeq, uuid);
	}

	@Override
	public String toString() {
		return "Prescription{" +
				"prescriptionDate=" + prescriptionDate +
				", supply=" + supply +
				", expiryDate=" + expiryDate +
				", urgentPrescription=" + urgentPrescription +
				", urgentNotes='" + urgentNotes + '\'' +
				", prescriptionSeq='" + prescriptionSeq + '\'' +
				", uuid='" + uuid + '\'' +
				'}';
	}
}
