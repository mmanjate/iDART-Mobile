package mz.org.fgh.idartlite.model;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.FormDaoImpl;

import java.util.Objects;

@DatabaseTable(tableName = "form", daoClass = FormDaoImpl.class)
public class Form extends BaseModel {

    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_DESCRIPTION = "description";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_UNIT)
    private String unit;

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
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
        return id == form.id &&
                unit.equals(form.unit) &&
                description.equals(form.description);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, unit, description);
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
