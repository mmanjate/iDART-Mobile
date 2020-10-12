package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.EpisodeDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@DatabaseTable(tableName = "episode", daoClass = EpisodeDaoImpl.class)
public class Episode extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_EPISODE_DATE = "episode_date";
	public static final String COLUMN_START_REASON = "start_reason";
	public static final String COLUMN_STOP_REASON = "stop_reason";
	public static final String COLUMN_NOTES = "notes";
	public static final String COLUMN_UUID = "uuid";
	public static final String COLUMN_PATIENT_ID = "patient_id";
	public static final String COLUMN_SYNC_STATUS = "sync_status";
	public static final String COLUMN_SANITARY_UNIT = "sanitary_unit";
	public static final String COLUMN_SANITARY_UNIT_UUID = "us_uuid";


	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = COLUMN_EPISODE_DATE)
	private Date episodeDate;

	@DatabaseField(columnName = COLUMN_START_REASON)
	private String startReason;

	@DatabaseField(columnName = COLUMN_STOP_REASON)
	private String stopReason;

	@DatabaseField(columnName = COLUMN_NOTES)
	private String notes;

	@DatabaseField(columnName = COLUMN_UUID)
	private String uuid;

	@DatabaseField(columnName = COLUMN_PATIENT_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Patient patient;

	@DatabaseField(columnName = COLUMN_SYNC_STATUS)
	private String syncStatus;

	@DatabaseField(columnName = COLUMN_SANITARY_UNIT)
	private String sanitaryUnit;

	@DatabaseField(columnName = COLUMN_SANITARY_UNIT_UUID)
	private String usUuid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEpisodeDate() {
		return episodeDate;
	}

	public void setEpisodeDate(Date episodeDate) {
		this.episodeDate = episodeDate;
	}

	public String getStartReason() {
		return startReason;
	}

	public void setStartReason(String startReason) {
		this.startReason = startReason;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getSanitaryUnit() {
		return sanitaryUnit;
	}

	public void setSanitaryUnit(String sanitaryUnit) {
		this.sanitaryUnit = sanitaryUnit;
	}

	public String getUsUuid() {
		return usUuid;
	}

	public void setUsUuid(String usUuid) {
		this.usUuid = usUuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Episode episode = (Episode) o;
		return episodeDate == episode.episodeDate &&
				uuid.equals(episode.uuid) &&
				patient.equals(episode.patient);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(episodeDate, uuid, patient);
	}

	@Override
	public String toString() {
		return "Episode{" +
				"episodeDate=" + episodeDate +
				", startReason='" + startReason + '\'' +
				", stopReason='" + stopReason + '\'' +
				", notes='" + notes + '\'' +
				", uuid='" + uuid + '\'' +
				", syncStatus='" + syncStatus + '\'' +
				'}';
	}

	public String getStringEpisodeDate(){
		return DateUtilitis.parseDateToDDMMYYYYString(this.episodeDate);
	}

	public String validateEpisodeData(){
		if(episodeDate==null){
			return "A data do Episódio e um dado obrigatorio.";
		}
		if(DateUtilitis.dateDiff(episodeDate, Calendar.getInstance().getTime(),DateUtilitis.DAY_FORMAT) >=0){
			return "A data do Episódio não pode ser uma data futura.";
		}
		if(stopReason.isEmpty()){
			return "O Motivo de Fim é um dado obrigatorio.";
		}
		return "";
	}

	//Filling the Next PickUpDate Label
	public String getNoInfoString(){
		if(stopReason!=null){
			return "Sem Info.";
		}
		return "";
	}

	public String getBeginOrEnding(){
		if(stopReason!=null){
			return "Fim";
		}
		return "Inicio";
	}

	public boolean isSyncStatusReady(){
		return isSyncStatusReady(this.syncStatus);
	}

}
