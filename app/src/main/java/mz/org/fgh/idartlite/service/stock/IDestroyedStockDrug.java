package mz.org.fgh.idartlite.service.stock;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.Drug;

public interface IDestroyedStockDrug extends IBaseService<DestroyedDrug> {
    List<Drug> getAllDrugsWithExistingLote() throws SQLException;

    void saveAll(List<DestroyedDrug> stocksToDestroy) throws SQLException;

    List<Drug> getDestroyedDrugs() throws SQLException;

    List<DestroyedDrug> getAllRelatedDestroyedStocks(DestroyedDrug relatedRecord) throws SQLException;


}
