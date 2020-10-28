package mz.org.fgh.idartlite.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;



public class SyncEpisode {


    @SerializedName("startdate")
    private Date startDate;

    @SerializedName("stopdate")
    private Date stopDate;

    @SerializedName("startreason")
    private String startReason;

    @SerializedName("stopreason")
    private String stopReason;

    @SerializedName("startnotes")
    private String startNotes;

    @SerializedName("stopnotes")
    private String stopNotes;

    @SerializedName("patientuuid")
    private String patientuuid;

    @SerializedName("syncstatus")
    private char syncStatus;

    @SerializedName("usuuid")
    private String usuuid;

    @SerializedName("clinicuuid")
    private String clinicuuid;



    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getStopDate() {
        return stopDate;
    }
    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
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
    public String getStartNotes() {
        return startNotes;
    }
    public void setStartNotes(String startNotes) {
        this.startNotes = startNotes;
    }
    public String getStopNotes() {
        return stopNotes;
    }
    public void setStopNotes(String stopNotes) {
        this.stopNotes = stopNotes;
    }

    public String getPatientuuid() {
        return patientuuid;
    }

    public void setPatientuuid(String patientuuid) {
        this.patientuuid = patientuuid;
    }

    public char getSyncStatus() {
        return syncStatus;
    }
    public void setSyncStatus(char syncStatus) {
        this.syncStatus = syncStatus;
    }
    public String getUsuuid() {
        return usuuid;
    }
    public void setUsuuid(String usuuid) {
        this.usuuid = usuuid;
    }
    public String getClinicuuid() {
        return clinicuuid;
    }
    public void setClinicuuid(String clinicuuid) {
        this.clinicuuid = clinicuuid;
    }
}