package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.settings.AppSettingsDaoImpl;

@DatabaseTable(tableName = AppSettings.TABLE_NAME, daoClass = AppSettingsDaoImpl.class)
public class AppSettings extends BaseModel implements Listble {

    public static final String TABLE_NAME = "settings";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SETTING_CODE = "code";
    public static final String COLUMN_SETTING_VALUE = "values";
    public static final String COLUMN_SETTING_DESCRIPTION = "description";

    public static final String SERVER_URL_SETTING = "server_url";
    public static final String DATA_SYNC_PERIOD_SETTING = "data_sync_period";
    public static final String METADATA_SYNC_PERIOD_SETTING = "metadata_sync_period";
    public static final String DATA_REMOTION_PERIOD = "data_remotion_period";

    public static final String DEFAULT_DATA_SYNC_PERIOD_SETTING = "8";
    public static final String DEFAULT_METADATA_SYNC_PERIOD_SETTING = "1";
    public static final String DEFAULT_DATA_REMOTION_PERIOD = "2";

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

    public AppSettings() {
    }

    public AppSettings(String code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    @Override
    public int getDrawable() {
        return 0;
    }

    public boolean isUrlSetting(){
        return this.code.equals(SERVER_URL_SETTING);
    }

    public boolean isDataSyncPeriodSetting(){
        return this.code.equals(DATA_SYNC_PERIOD_SETTING);
    }

    public boolean isMetadataSyncPediorSetting(){
        return this.code.equals(METADATA_SYNC_PERIOD_SETTING);
    }

    public boolean isDataRemotionPeriodSetting(){
        return this.code.equals(DATA_REMOTION_PERIOD);
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

    public static AppSettings generateUrlSetting(String centralServerUrl){
        return new AppSettings(SERVER_URL_SETTING, centralServerUrl, "Endereço do servidor central");
    }

    public static AppSettings generateDataSyncSetting(String value){
        return new AppSettings(DATA_SYNC_PERIOD_SETTING, value, "Periodo de sincronização de dados");
    }

    public static AppSettings generateMetadataSyncSetting(String value){
        return new AppSettings(METADATA_SYNC_PERIOD_SETTING, value, "Periodo de sincronização de metadados");
    }

    public static AppSettings generateDataRemotionSetting(String value){
        return new AppSettings(DATA_REMOTION_PERIOD, value, "Periodo de remoção de dados da base de dados");
    }

    @Override
    public String canBeEdited(Context context) {
        return null;
    }

    @Override
    public String canBeRemoved(Context context) {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
