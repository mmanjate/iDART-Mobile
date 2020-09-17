package mz.org.fgh.idartlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Disease_type")
public class DiseaseType {

    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_DESCRIPTION = "description";


    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "unit")
    private String unit;

    @DatabaseField(columnName = "description")
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
