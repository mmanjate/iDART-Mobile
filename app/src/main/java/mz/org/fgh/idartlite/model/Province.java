package mz.org.fgh.idartlite.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.territory.ProvinceDaoImpl;

@DatabaseTable(tableName = "province", daoClass = ProvinceDaoImpl.class)
public class Province extends BaseModel implements Listble {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_COUNTRY = "country_id";
    public static final String COLUMN_REST_ID = "rest_id";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_CODE, canBeNull = false)
    private String code;

    @DatabaseField(columnName = COLUMN_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = COLUMN_UUID, canBeNull = false)
    private String uuid;

    @DatabaseField(columnName = COLUMN_COUNTRY ,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Country country;

    @DatabaseField(columnName = COLUMN_REST_ID, canBeNull = false)
    private int restId;

    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return getName();
    }

    @Override
    public int getDrawable() {
        return 0;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Province that = (Province) o;
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
        return "Province{" +
                "name=" + name +
                ", code=" + code +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
