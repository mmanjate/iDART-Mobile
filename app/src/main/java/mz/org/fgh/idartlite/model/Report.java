package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;

import java.util.Objects;

import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.model.BaseModel;


public class Report<T extends BaseActivity> extends BaseModel {

    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DESCRIPTION = "description";

    @DatabaseField(columnName = COLUMN_CODE)
    private String code;

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
    private String description;

    private Class<T> displayActivity;

    private int icon;

    public Report(String code, String description, int icon) {
        this.code = code;
        this.description = description;
        this.icon = icon;
    }

    public Report(String code, String description, Class displayActivity, int icon) {
        this.code = code;
        this.description = description;
        this.displayActivity = displayActivity;
        this.icon = icon;
    }

    public Report() {
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    public static Report fastCreate(String code, String description, int icon){
        return new Report(code, description, icon);
    }

    public static Report fastCreate(String code, String description, int icon, Class displayActivity){
        return new Report(code, description, displayActivity, icon);
    }

    public Class<T> getDisplayActivity() {
        return displayActivity;
    }

    public void setDisplayActivity(Class<T> displayActivity) {
        this.displayActivity = displayActivity;
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
