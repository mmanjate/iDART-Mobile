package mz.org.fgh.idartlite.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.dao.clinic.ClinicDaoImpl;
import mz.org.fgh.idartlite.dao.clinic.ClinicSectorDaoImpl;
import mz.org.fgh.idartlite.util.Utilities;

@DatabaseTable(tableName = "clinicSector", daoClass = ClinicSectorDaoImpl.class)
public class ClinicSector extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_SECTOR_NAME = "sector_name";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_UUID = "uuid";
	public static final String COLUMN_CLINIC_ID = "clinic_id";

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

	@DatabaseField(columnName = COLUMN_CLINIC_ID)
	private int clinicId;

	public ClinicSector() {
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

	public int getClinicId() {
		return clinicId;
	}

	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
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


}
