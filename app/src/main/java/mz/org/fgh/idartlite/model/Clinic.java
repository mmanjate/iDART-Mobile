package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.ClinicDaoImpl;

import java.util.Objects;

@DatabaseTable(tableName = "clinic", daoClass = ClinicDaoImpl.class)
public class Clinic extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CODE = "password";
	public static final String COLUMN_CLINIC_NAME = "clinic_name";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_UUID = "uuid";
	public static final String COLUMN_PHARMACY_TYPE_ID = "pharmacy_type_id";

	@DatabaseField(columnName = "id", generatedId = true)
	private int id;

	@DatabaseField(columnName = "code")
	private String code;

	@DatabaseField(columnName = "clinic_name")
	private String clinicName;

	@DatabaseField(columnName = "phone")
	private String phone;

	@DatabaseField(columnName = "address")
	private String address;

	@DatabaseField(columnName = "uuid")
	private String uuid;

	@DatabaseField(columnName = "pharmacy_type_id", canBeNull = false, foreign = true)
	private PharmacyType pharmacyType;

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

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public PharmacyType getPharmacyType() {
		return pharmacyType;
	}

	public void setPharmacyType(PharmacyType pharmacyType) {
		this.pharmacyType = pharmacyType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Clinic clinic = (Clinic) o;
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
		return "Clinic{" +
				"code='" + code + '\'' +
				", clinicName='" + clinicName + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", uuid='" + uuid + '\'' +
				'}';
	}
}
