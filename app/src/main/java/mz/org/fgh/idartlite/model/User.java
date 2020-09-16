package mz.org.fgh.idartlite.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.UserDaoImpl;

@DatabaseTable(tableName = "user", daoClass = UserDaoImpl.class)
public class User extends BaseModel {

    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";

    @DatabaseField(columnName = "id", id = true)
    private int id;

    @SerializedName("cl_username")
    @DatabaseField(columnName = "user_name")
    private String userName;

    @SerializedName("cl_password")
    @DatabaseField(columnName = "password")
    private String password;

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
}
