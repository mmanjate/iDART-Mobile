package mz.org.fgh.idartlite.model;

import mz.org.fgh.idartlite.base.model.BaseModel;


public class StockReportData extends BaseModel {

    private String drugDescription;

    private String maximumConsumption;

    private String actualStock;

    private String validStock;

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

}
