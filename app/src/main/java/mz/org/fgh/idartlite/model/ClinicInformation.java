package mz.org.fgh.idartlite.model;


import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.clinicInfo.ClinicInfoDaoImpl;


@DatabaseTable(tableName = "clinic_inforamtion", daoClass = ClinicInfoDaoImpl.class)
public class ClinicInformation extends BaseModel {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_IMC = "imc";
    public static final String COLUMN_DISTORT = "distort";
    public static final String COLUMN_SYSTOLE = "systole";
    public static final String COLUMN_PATIENT_ID = "patient_id";


    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_WEIGHT)
    private double weight;

    @DatabaseField(columnName = COLUMN_HEIGHT)
    private double height;

    @DatabaseField(columnName = COLUMN_IMC)
    private String imc;

    @DatabaseField(columnName = COLUMN_DISTORT)
    private double distort;

    @DatabaseField(columnName = COLUMN_SYSTOLE)
    private double systole;

    @DatabaseField(columnName = COLUMN_PATIENT_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Patient patient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getDistort() {
        return distort;
    }

    public void setDistort(double distort) {
        this.distort = distort;
    }

    public double getSystole() {
        return systole;
    }

    public void setSystole(double systole) {
        this.systole = systole;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
