package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.user.UserDaoImpl;
import mz.org.fgh.idartlite.util.Utilities;

@DatabaseTable(tableName = "user", daoClass = UserDaoImpl.class)
public class User extends BaseModel {

    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CLINIC_ID = "clinic_id";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @SerializedName("cl_username")
    @DatabaseField(columnName = COLUMN_USER_NAME)
    private String userName;

    @SerializedName("cl_password")
    @DatabaseField(columnName = COLUMN_PASSWORD)
    private String password;

    @DatabaseField(columnName = COLUMN_CLINIC_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Clinic clinic;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    private String validadeToLogin(Context context) {
        if (!Utilities.stringHasValue(this.userName)) return context.getString(R.string.user_is_mandatory);
        if (!Utilities.stringHasValue(this.password)) return context.getString(R.string.pass_is_mandatory);

        return "";
    }

    @Override
    public String isValid(Context context) {
        return validadeToLogin(context);
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
