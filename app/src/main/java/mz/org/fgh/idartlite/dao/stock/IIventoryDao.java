package mz.org.fgh.idartlite.dao.stock;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.inventory.Iventory;

public interface IIventoryDao extends IGenericDao<Iventory, Integer> {
    Iventory getLastInventory() throws SQLException;

    Iventory getInventoryAfterDate(Date date) throws SQLException;

    List<Iventory> getPastInventoryToRemove(Date date) throws SQLException;
}
