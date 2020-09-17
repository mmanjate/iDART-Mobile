package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.PrescriptionDaoImpl;

import java.util.Date;
import java.util.Objects;

@DatabaseTable(tableName = "prescription", daoClass = PrescriptionDaoImpl.class)
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
	public static final String COLUMN_SYNC_STATUS = "sync_status";

	@DatabaseField(columnName = COLUMN_ID, id = true)
	private int id;

	@DatabaseField(columnName = COLUMN_PRESCRIPTION_DATE)
	private Date prescriptionDate;

	@DatabaseField(columnName = COLUMN_SUPPLY)
	private int supply;

	@DatabaseField(columnName = COLUMN_EXPIRY_DATE)
	private Date expiryDate;

	@DatabaseField(columnName = COLUMN_URGENT_PRESCRIPTION)
	private boolean urgentPrescription;

	@DatabaseField(columnName = COLUMN_URGENT_NOTES)
	private String urgentNotes;

	@DatabaseField(columnName = COLUMN_REGIMEN_ID)
	private TherapeuticRegimen regimenId;

	@DatabaseField(columnName = COLUMN_LINE_ID)
	private TherapeuticLine lineId;

	@DatabaseField(columnName = COLUMN_DISPENSE_TYPE_ID, canBeNull = false, foreign = true)
	private DispenseType dispenseType;

	@DatabaseField(columnName = COLUMN_PRESCRIPTION_SEQ)
	private String prescriptionSeq;

	@DatabaseField(columnName = COLUMN_UUID)
	private String uuid;

	@DatabaseField(columnName = COLUMN_PATIENT_ID, canBeNull = false, foreign = true)
	private Patient patient;

	@DatabaseField(columnName = COLUMN_SYNC_STATUS)
	private String syncStatus;

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

	public DispenseType getDispenseType() {
		return dispenseType;
	}

	public void setDispenseType(DispenseType dispenseType) {
		this.dispenseType = dispenseType;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
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
				", dispenseType=" + dispenseType +
				", prescriptionSeq='" + prescriptionSeq + '\'' +
				", uuid='" + uuid + '\'' +
				", syncStatus='" + syncStatus + '\'' +
				'}';
	}
}
