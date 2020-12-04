package mz.org.fgh.idartlite.service.stock;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.OperationType;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;

public interface IReferedStockService extends IBaseService<ReferedStockMoviment> {
    List<ReferedStockMoviment> getAllWithPagination(long offset, long limit) throws SQLException;

    List<Drug> getallDrugs() throws SQLException;

    List<OperationType> getAllOperationTypes() throws SQLException;

    void saveMany(List<ReferedStockMoviment> referedStockMovimentList) throws SQLException;

    void tempCreateOperationTypes() throws SQLException;

    List<ReferedStockMoviment> getAllRelatedReferedStockMoviments(ReferedStockMoviment relatedRecord) throws SQLException;
}
