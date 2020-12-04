package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.param.operationtype.OperationTypeDaoImpl;

@DatabaseTable(tableName = OperationType.TABLE_NAME, daoClass = OperationTypeDaoImpl.class)
public class OperationType extends BaseModel implements Listble<OperationType> {

    public static final String TABLE_NAME = "operation_type";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRIPTION = "description";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
    private String description;

    public OperationType() {
    }

    public OperationType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public OperationType(String description) {
        this.description = description;
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

    @Override
    public int getDrawable() {
        return 0;
    }

    @Override
    public String getCode() {
        return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationType)) return false;
        OperationType that = (OperationType) o;
        return id == that.id &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
