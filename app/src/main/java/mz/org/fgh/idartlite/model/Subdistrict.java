package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.territory.SubdistrictDaoImpl;

@DatabaseTable(tableName = "subdistrict", daoClass = SubdistrictDaoImpl.class)
public class Subdistrict extends BaseModel {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_DISTRICT = "district_id";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_CODE, canBeNull = false)
    private String code;

    @DatabaseField(columnName = COLUMN_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = COLUMN_UUID, canBeNull = false)
    private String uuid;

    @DatabaseField(columnName = COLUMN_DISTRICT ,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private District district;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subdistrict that = (Subdistrict) o;
        return id == that.id &&
                uuid.equals(that.uuid) &&
                code.equals(that.code);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, code);
    }

    @Override
    public String toString() {
        return "Subdistrict{" +
                "name=" + name +
                ", code=" + code +
                '}';
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
