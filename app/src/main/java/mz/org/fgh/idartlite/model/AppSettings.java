package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.settings.AppSettingsDaoImpl;

@DatabaseTable(tableName = AppSettings.TABLE_NAME, daoClass = AppSettingsDaoImpl.class)
public class AppSettings extends BaseModel {

    public static final String TABLE_NAME = "settings";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SETTING_CODE = "code";
    public static final String COLUMN_SETTING_VALUE = "values";
    public static final String COLUMN_SETTING_DESCRIPTION = "description";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_SETTING_CODE)
    private String code;

    @DatabaseField(columnName = COLUMN_SETTING_VALUE)
    private String value;

    @DatabaseField(columnName = COLUMN_SETTING_DESCRIPTION)
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String isValid(Context context) {
        if (this.code == null) return "O código do parametro deve ser indicado";
        if (this.value == null) return "O valor do parametro deve ser indicado";
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
