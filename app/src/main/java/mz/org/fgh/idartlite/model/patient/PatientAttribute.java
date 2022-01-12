package mz.org.fgh.idartlite.model.patient;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.patient.PatientAttributeDaoImpl;

@DatabaseTable(tableName = "patient_attribute", daoClass = PatientAttributeDaoImpl.class)
public class PatientAttribute extends BaseModel {

    public static final String TABLE_NAME = "patient_attribute";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ATTRIBUTE = "attribute";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String PATIENT_DISPENSATION_STATUS = "PATIENT_DISPENSATION_STATUS";
    public static final String PATIENT_DISPENSATION_STATUS_FALTOSO = "faltoso";
    public static final String PATIENT_DISPENSATION_ABANDONO = "abandono";
    public static final String PATIENT_DISPENSATION_NORMAL = "normal";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_ATTRIBUTE, unique = true)
    private String attribute;

    @DatabaseField(columnName = COLUMN_VALUE)
    private String value;

    @DatabaseField(columnName = COLUMN_PATIENT_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Patient patient;

    public PatientAttribute(String attribute, String value, Patient patient) {
        this.attribute = attribute;
        this.value = value;
        this.patient = patient;
    }

    public PatientAttribute() {
    }

    public static PatientAttribute fastCreateFaltoso(Patient patient) {
        return new PatientAttribute(PATIENT_DISPENSATION_STATUS, PATIENT_DISPENSATION_STATUS_FALTOSO, patient);
    }

    public static PatientAttribute fastCreateAbandono(Patient patient) {
        return new PatientAttribute(PATIENT_DISPENSATION_STATUS, PATIENT_DISPENSATION_ABANDONO, patient);
    }

    public static PatientAttribute fastCreateNormal(Patient patient) {
        return new PatientAttribute(PATIENT_DISPENSATION_STATUS, PATIENT_DISPENSATION_NORMAL, patient);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "PatientAttribute{" +
                "id=" + id +
                ", attribute='" + attribute + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientAttribute)) return false;
        PatientAttribute that = (PatientAttribute) o;
        return id == that.id && Objects.equals(attribute, that.attribute) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attribute, value);
    }

    public boolean isFaltosoOrAbandonoAttr() {
        if (!this.attribute.equals(PATIENT_DISPENSATION_STATUS)) return false;

        return this.value.equals(PATIENT_DISPENSATION_STATUS_FALTOSO) || this.value.equals(PATIENT_DISPENSATION_ABANDONO);
    }
}
