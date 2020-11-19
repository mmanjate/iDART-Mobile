package mz.org.fgh.idartlite.service.stock;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Iventory;

public interface IIventoryService extends IBaseService<Iventory> {
    List<Iventory> getAllWithPagination(long offset, long limit) throws SQLException;
}
