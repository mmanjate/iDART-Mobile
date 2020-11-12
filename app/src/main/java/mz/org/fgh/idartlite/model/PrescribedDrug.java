package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.PrescribedDrugDaoImpl;

import java.util.Objects;

@DatabaseTable(tableName = "prescribed_drug", daoClass = PrescribedDrugDaoImpl.class)
public class PrescribedDrug extends BaseModel {

    public static final String COLUMN_STOCK = "drug_id";
    public static final String COLUMN_PRESCRIPTION = "prescription_id";



    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_STOCK , canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Drug drug;

    @DatabaseField(columnName = COLUMN_PRESCRIPTION ,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Prescription prescription;

    public PrescribedDrug() {
    }

    public PrescribedDrug(Drug drug, Prescription prescription) {
        this.drug = drug;
        this.prescription = prescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescribedDrug that = (PrescribedDrug) o;
        return id == that.id &&
                drug.equals(that.drug) &&
                prescription.equals(that.prescription);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, drug, prescription);
    }

    @Override
    public String toString() {
        return "PrescribedDrug{" +
                "id=" + id +
                ", drug=" + drug +
                ", prescription=" + prescription +
                '}';
    }
}
