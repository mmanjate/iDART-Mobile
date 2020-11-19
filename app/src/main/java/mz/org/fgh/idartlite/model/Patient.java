package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.patient.PatientDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.patient.PatientDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;

import static mz.org.fgh.idartlite.model.Subdistrict.COLUMN_DISTRICT;

@DatabaseTable(tableName = "patient", daoClass = PatientDaoImpl.class)
public class Patient extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NID = "nid";
	public static final String COLUMN_FIRST_NAME = "first_name";
	public static final String COLUMN_LAST_NAME = "last_name";
	public static final String COLUMN_BIRTH_DATE = "birth_date";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_START_ARV_DATE = "start_ARV_Date";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_UUID = "uuid";
	public static final String COLUMN_CLINIC_ID = "clinic_id";
	public static final String COLUMN_IDENTIFIER_TYPE = "identifier_type";
	public static final String COLUMN_DISTRICT = "district_id";
	public static final String COLUMN_PROVINCE = "province_id";
	public static final String COLUMN_BIRTH_DATE_ESTIMATED = "birth_date_estimated";

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = COLUMN_NID)
	private String nid;

	@DatabaseField(columnName = COLUMN_FIRST_NAME)
	private String firstName;

	@DatabaseField(columnName = COLUMN_LAST_NAME)
	private String lastName;

	@DatabaseField(columnName = COLUMN_BIRTH_DATE)
	private Date birthDate;

	@DatabaseField(columnName = COLUMN_GENDER)
	private String gender;

	@DatabaseField(columnName = COLUMN_START_ARV_DATE)
	private Date startARVDate;

	@DatabaseField(columnName = COLUMN_PHONE)
	private String phone;

	@DatabaseField(columnName = COLUMN_ADDRESS)
	private String address;

	@DatabaseField(columnName = COLUMN_UUID)
	private String uuid;

	@DatabaseField(columnName = COLUMN_CLINIC_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Clinic clinic;

	@DatabaseField(columnName = COLUMN_IDENTIFIER_TYPE)
	private String identifierType;

	@DatabaseField(columnName = COLUMN_BIRTH_DATE_ESTIMATED)
	private boolean birthDateEstimated;

	@DatabaseField(columnName = COLUMN_DISTRICT , foreign = true, foreignAutoRefresh = true)
	private District district;

	@DatabaseField(columnName = COLUMN_PROVINCE , foreign = true, foreignAutoRefresh = true)
	private Province province;

	private List<Episode> episodes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getStartARVDate() {
		return startARVDate;
	}

	public void setStartARVDate(Date startARVDate) {
		this.startARVDate = startARVDate;
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

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public String getDateString(){
		return DateUtilities.parseDateToDDMMYYYYString(this.birthDate);
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}


	public String getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}

	public boolean isBirthDateEstimated() {
		return birthDateEstimated;
	}

	public void setBirthDateEstimated(boolean birthDateEstimated) {
		this.birthDateEstimated = birthDateEstimated;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Episode getReferenceEpisode(){
		if (!Utilities.listHasElements(this.episodes)) return null;
		return this.episodes.get(0);
	}

	public boolean hasEndEpisode(){
		if (!Utilities.listHasElements(this.episodes)) return  false;

		for (Episode episode : this.episodes){
			if (Utilities.stringHasValue(episode.getStopReason())) return true;
		}
		return false;
	}

	public int getAge() {
		int age = 0;
		try {
			age = (int) DateUtilities.calculaIdade(DateUtilities.formatToDDMMYYYY(this.birthDate, "-"), DateUtilities.YEAR_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return age;
	}

	public String getAgeToString(){
		return String.valueOf(this.getAge());
	}

	public String getDateStartTarv(){
		if(this.startARVDate == null){
			return DateUtilities.parseDateToDDMMYYYYString(Calendar.getInstance().getTime());
		}else {
			return DateUtilities.parseDateToDDMMYYYYString(this.startARVDate);
		}
	}

	public String getFullName(){
		return this.firstName + " " + this.lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Patient patient = (Patient) o;
		return nid.equals(patient.nid) &&
				firstName.equals(patient.firstName) &&
				lastName.equals(patient.lastName) &&
				gender.equals(patient.gender) &&
				uuid.equals(patient.uuid);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(nid, firstName, lastName, gender, uuid);
	}

	@Override
	public String toString() {
		return "Patient{" +
				"nid='" + nid + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", birthDate=" + birthDate +
				", gender='" + gender + '\'' +
				", startARVDate=" + startARVDate +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", uuid='" + uuid + '\'' +
				'}';
	}

	public String validate(Context context) {
		if (this.nid.isEmpty()) return context.getString(R.string.nid_mandatory);
		if (this.firstName.isEmpty()) return context.getString(R.string.nome_mandatory);
		if (this.lastName.isEmpty()) return context.getString(R.string.apelido_mandatory);
		if(DateUtilities.dateDiff(this.birthDate, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT) > 0) {
			return context.getString(R.string.birth_date_not_correct );
		}

		if(this.gender.isEmpty()) return context.getString(R.string.sexo_mandatory);
		if(this.address.isEmpty()) return context.getString(R.string.regimen_mandatory);
		if(this.startARVDate == null) return context.getString(R.string.tarv_inital_mandatory);
		if(DateUtilities.dateDiff(this.startARVDate, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT) > 0) {
			return context.getString(R.string.tarv_inital_date_not_correct );
		}
		return "";
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
}
