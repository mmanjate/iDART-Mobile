package mz.org.fgh.idartlite.dao.stock;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.util.Utilities;

public class IventoryDaoImpl extends GenericDaoImpl<Iventory, Integer> implements IIventoryDao {
    public IventoryDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public IventoryDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public IventoryDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public Iventory getLastInventory() throws SQLException {
        QueryBuilder<Iventory, Integer> queryBuilder = queryBuilder();

        List<Iventory> inventories = queryBuilder.orderBy("id", false).limit(1L).query();

        if (Utilities.listHasElements(inventories)) return inventories.get(0);

        return null;
    }

    @Override
    public List<Iventory> searchRecords(long offset, long limit) throws SQLException {
        return queryBuilder().limit(limit).offset(offset).orderBy(Iventory.COLUMN_ID, false).query();
    }

    @Override
    public Iventory getInventoryAfterDate(Date date) throws SQLException {
        QueryBuilder<Iventory, Integer> queryBuilder = queryBuilder();

        List<Iventory> inventories = queryBuilder.where().ge(Iventory.COLUMN_END_DATE, date).query();

        if (Utilities.listHasElements(inventories)) return inventories.get(0);

        return null;
    }
}
