package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.patient.PatientDaoImpl;
import mz.org.fgh.idartlite.dao.stock.StockAlertDaoImpl;

@DatabaseTable(tableName = "stock_alert", daoClass = StockAlertDaoImpl.class)
public class StockReportData extends BaseModel {

    private static final long serialVersionUID = 3794862790392873389L;

    public static final String TABLE_NAME = "stock_alert";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DRUG_DESCRIPTION = "drugDescription";
    public static final String COLUMN_MAX_CONSUPTION = "maximumConsumption";
    public static final String COLUMN_CUR_STOCK = "actualStock";
    public static final String COLUMN_VALID_STOCK = "validStock";
    public static final String COLUMN_STOCK_DESCRIPTION = "stockDescription";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_DRUG_DESCRIPTION)
    private String drugDescription;
    @DatabaseField(columnName = COLUMN_MAX_CONSUPTION)
    private String maximumConsumption;
    @DatabaseField(columnName = COLUMN_CUR_STOCK)
    private String actualStock;
    @DatabaseField(columnName = COLUMN_VALID_STOCK)
    private String validStock;
    @DatabaseField(columnName = COLUMN_STOCK_DESCRIPTION)
    private String stockDescription;


    public String getDrugDescription() {
        return drugDescription;
    }

    public String getMaximumConsumption() {
        return maximumConsumption;
    }

    public String getActualStock() {
        return actualStock;
    }

    public String getValidStock() {
        return validStock;
    }

    public void setDrugDescription(String drugDescription) {
        this.drugDescription = drugDescription;
    }

    public void setMaximumConsumption(String maximumConsumption) {
        this.maximumConsumption = maximumConsumption;
    }

    public void setActualStock(String actualStock) {
        this.actualStock = actualStock;
    }

    public void setValidStock(String validStock) {
        this.validStock = validStock;
    }

    public String getStockDescription() {
        return stockDescription;
    }

    public void setStockDescription(String stockDescription) {
        this.stockDescription = stockDescription;
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
