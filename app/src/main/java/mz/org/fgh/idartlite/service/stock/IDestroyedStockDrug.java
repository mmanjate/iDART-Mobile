package mz.org.fgh.idartlite.service.stock;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DestroyedDrug;

public interface IDestroyedStockDrug extends IBaseService<DestroyedDrug> {
    void saveAll(List<DestroyedDrug> stocksToDestroy) throws SQLException;
}
