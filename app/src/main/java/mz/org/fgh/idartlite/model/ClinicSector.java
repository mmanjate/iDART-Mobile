package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.clinic.ClinicSectorDaoImpl;
import mz.org.fgh.idartlite.util.Utilities;

@DatabaseTable(tableName = "clinicSector", daoClass = ClinicSectorDaoImpl.class)
public class ClinicSector extends BaseModel implements Listble {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_SECTOR_NAME = "sector_name";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_UUID = "uuid";
	public static final String COLUMN_CLINIC_ID = "clinic_id";
	public static final String COLUMN_CLINIC_SECTOR_TYPE_ID = "clinic_sector_type_id";

	private static final long serialVersionUID = -5768764982413774128L;

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = COLUMN_CODE)
	private String code;

	@DatabaseField(columnName = COLUMN_SECTOR_NAME)
	private String sectorName;

	@DatabaseField(columnName = COLUMN_PHONE)
	private String phone;

	@DatabaseField(columnName = COLUMN_UUID)
	private String uuid;

	@DatabaseField(columnName = COLUMN_CLINIC_ID,canBeNull = false, foreign = true, foreignAutoRefresh = true )
	private Clinic clinic;

	@DatabaseField(columnName = COLUMN_CLINIC_SECTOR_TYPE_ID,canBeNull = false, foreign = true, foreignAutoRefresh = true )
	private ClinicSectorType clinicSectorType;

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public ClinicSector() {
	}

	public ClinicSectorType getClinicSectorType() {
		return clinicSectorType;
	}

	public void setClinicSectorType(ClinicSectorType clinicSectorType) {
		this.clinicSectorType = clinicSectorType;
	}

	public ClinicSector(int id, String sectorName) {
		this.id = id;
		this.sectorName = sectorName;
	}

	public ClinicSector(int id, String code, String sectorName) {
		this.id = id;
		this.code = code;
		this.sectorName = sectorName;
	}

	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return getSectorName();
	}

	@Override
	public int getDrawable() {
		return 0;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ClinicSector clinic = (ClinicSector) o;
		if (!Utilities.stringHasValue(code) || !Utilities.stringHasValue(clinic.code)) return false;
		return code.equals(clinic.code) &&
				uuid.equals(clinic.uuid);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(code, uuid);
	}

	@Override
	public String toString() {
		if(sectorName == null){
			return " ";
		}else{
			return  sectorName;
		}
	}


	@Override
	public String isValid(Context context) {
		return null;
	}

	@Override
	public String canBeEdited(Context context) {
		return null;
	}

	@Override
	public String canBeRemoved(Context context) {
		return null;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	// Visibility Validation
	public boolean mustValidateStock() {
		return this.clinicSectorType.isBrigadaMovel() || this.clinicSectorType.isClinicaMovel() || this.clinicSectorType.isParagemUnica() || this.clinicSectorType.isAPE();
	}
}
