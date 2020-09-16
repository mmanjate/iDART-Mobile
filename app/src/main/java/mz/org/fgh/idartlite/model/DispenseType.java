package mz.org.fgh.idartlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Dispense_type")
public class DispenseType {

    public static final String COLUMN_CODE = "unit";
    public static final String COLUMN_DESCRIPTION = "description";

    @DatabaseField(columnName = "id", id = true)
    private int id;

    @DatabaseField(columnName = "code")
    private String code;

    @DatabaseField(columnName = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
