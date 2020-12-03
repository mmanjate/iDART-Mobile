package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.param.operationtype.OperationTypeDaoImpl;

@DatabaseTable(tableName = OperationType.TABLE_NAME, daoClass = OperationTypeDaoImpl.class)
public class OperationType extends BaseModel {

    public static final String TABLE_NAME = "operation_type";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRIPTION = "description";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
    private String description;

    public OperationType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
