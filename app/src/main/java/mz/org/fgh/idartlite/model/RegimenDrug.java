package mz.org.fgh.idartlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Regimen_Drug")
public class RegimenDrug {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true)
    private Drug drug;

    public RegimenDrug() {
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
}
