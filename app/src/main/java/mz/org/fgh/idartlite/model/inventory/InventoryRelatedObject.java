package mz.org.fgh.idartlite.model.inventory;

import java.util.List;

import mz.org.fgh.idartlite.model.StockAjustment;

public interface InventoryRelatedObject {

    List<StockAjustment> getAjustmentInfo();

    void setAjustmentInfo(List<StockAjustment> ajustmentInfo);

    void addAjustmentInfo(StockAjustment ajustment);

}
