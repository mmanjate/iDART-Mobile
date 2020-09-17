package mz.org.fgh.idartlite.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "form")
public class Form {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Form form = (Form) o;
        return getId() == form.getId() &&
                Objects.equals(getUnit(), form.getUnit()) &&
                Objects.equals(getDescription(), form.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUnit(), getDescription());
    }
}
