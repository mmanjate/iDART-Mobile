package mz.org.fgh.idartlite.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.DiseaseTypeDaoImpl;


public class Report extends BaseModel {

    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DESCRIPTION = "description";

    @DatabaseField(columnName = COLUMN_CODE)
    private String code;

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
    private String description;




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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report that = (Report) o;
        return code.equals(that.code);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Report{" +

                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
