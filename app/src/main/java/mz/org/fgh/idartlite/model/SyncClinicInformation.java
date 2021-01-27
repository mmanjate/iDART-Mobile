package mz.org.fgh.idartlite.model;


import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.clinicInfo.ClinicInfoDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;



public class SyncClinicInformation {






    private int id;


    private Date registerdate;


    private double weight;

    private double height;

    private String imc;

    private int distort;

    private int systole;

    private String patientuuid;

    private boolean istreatmenttpi;

    private boolean istreatmenttb;

    private boolean iscough;

    private boolean isfever;
    private boolean islostweight;
    private boolean issweating;
    private boolean hasparenttbtreatment;
    private boolean isreferedtoustb;
    private boolean haspatientcamecorrectdate;
    private int latedays;
    private boolean patientforgotmedicine;
    private int dayswithoutmedicine;
    private String latemotives;
    private boolean adversereactionofmedicine;
    private String adversereaction;
    private boolean isreferedtousram;
    private char syncstatus;
    private String uuid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public int getDistort() {
        return distort;
    }

    public void setDistort(int distort) {
        this.distort = distort;
    }

    public int getSystole() {
        return systole;
    }

    public void setSystole(int systole) {
        this.systole = systole;
    }

    public String getPatientuuid() {
        return patientuuid;
    }

    public void setPatientuuid(String patientuuid) {
        this.patientuuid = patientuuid;
    }

    public boolean isIstreatmenttpi() {
        return istreatmenttpi;
    }

    public void setIstreatmenttpi(boolean istreatmenttpi) {
        this.istreatmenttpi = istreatmenttpi;
    }

    public boolean isIstreatmenttb() {
        return istreatmenttb;
    }

    public void setIstreatmenttb(boolean istreatmenttb) {
        this.istreatmenttb = istreatmenttb;
    }

    public boolean isIscough() {
        return iscough;
    }

    public void setIscough(boolean iscough) {
        this.iscough = iscough;
    }

    public boolean isIsfever() {
        return isfever;
    }

    public void setIsfever(boolean isfever) {
        this.isfever = isfever;
    }

    public boolean isIslostweight() {
        return islostweight;
    }

    public void setIslostweight(boolean islostweight) {
        this.islostweight = islostweight;
    }

    public boolean isIssweating() {
        return issweating;
    }

    public void setIssweating(boolean issweating) {
        this.issweating = issweating;
    }

    public boolean isHasparenttbtreatment() {
        return hasparenttbtreatment;
    }

    public void setHasparenttbtreatment(boolean hasparenttbtreatment) {
        this.hasparenttbtreatment = hasparenttbtreatment;
    }

    public boolean isIsreferedtoustb() {
        return isreferedtoustb;
    }

    public void setIsreferedtoustb(boolean isreferedtoustb) {
        this.isreferedtoustb = isreferedtoustb;
    }

    public boolean isHaspatientcamecorrectdate() {
        return haspatientcamecorrectdate;
    }

    public void setHaspatientcamecorrectdate(boolean haspatientcamecorrectdate) {
        this.haspatientcamecorrectdate = haspatientcamecorrectdate;
    }

    public int getLatedays() {
        return latedays;
    }

    public void setLatedays(int latedays) {
        this.latedays = latedays;
    }

    public boolean isPatientforgotmedicine() {
        return patientforgotmedicine;
    }

    public void setPatientforgotmedicine(boolean patientforgotmedicine) {
        this.patientforgotmedicine = patientforgotmedicine;
    }

    public int getDayswithoutmedicine() {
        return dayswithoutmedicine;
    }

    public void setDayswithoutmedicine(int dayswithoutmedicine) {
        this.dayswithoutmedicine = dayswithoutmedicine;
    }

    public String getLatemotives() {
        return latemotives;
    }

    public void setLatemotives(String latemotives) {
        this.latemotives = latemotives;
    }

    public boolean isAdversereactionofmedicine() {
        return adversereactionofmedicine;
    }

    public void setAdversereactionofmedicine(boolean adversereactionofmedicine) {
        this.adversereactionofmedicine = adversereactionofmedicine;
    }

    public String getAdversereaction() {
        return adversereaction;
    }

    public void setAdversereaction(String adversereaction) {
        this.adversereaction = adversereaction;
    }

    public boolean isIsreferedtousram() {
        return isreferedtousram;
    }

    public void setIsreferedtousram(boolean isreferedtousram) {
        this.isreferedtousram = isreferedtousram;
    }

    public char getSyncstatus() {
        return syncstatus;
    }

    public void setSyncstatus(char syncstatus) {
        this.syncstatus = syncstatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
