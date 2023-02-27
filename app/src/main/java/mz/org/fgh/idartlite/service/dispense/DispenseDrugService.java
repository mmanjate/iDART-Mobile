package mz.org.fgh.idartlite.service.dispense;

import android.app.Application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;

public class DispenseDrugService extends BaseService<DispensedDrug> implements IDispenseDrugService {

    public DispenseDrugService(Application application, User currUser) {
        super(application, currUser);
    }

    public DispenseDrugService(Application application) {
        super(application);
    }

    @Override
    public void save(DispensedDrug record) throws SQLException {

    }

    @Override
    public void update(DispensedDrug record) throws SQLException {

    }

    public void createDispensedDrug(DispensedDrug dispenseDrug) throws SQLException {
        getDataBaseHelper().getDispensedDrugDao().create(dispenseDrug);
    }

    public List<DispensedDrug> findDispensedDrugByDispenseId(int id) throws SQLException {

        return getDataBaseHelper().getDispensedDrugDao().findDispensedDrugByDispenseId(id);
    }

    @Override
    public List<Drug> findDrugsOnDispensedDrugsByDispense(Dispense dispense) throws SQLException {
        List<DispensedDrug> dispenseDrugs= findDispensedDrugByDispenseId(dispense.getId());

        List<Drug> drugs= new ArrayList<>();
        for (DispensedDrug dispenseDrug:
                dispenseDrugs)
        {

            drugs.add(dispenseDrug.getStock().getDrug());
        }
        return drugs;
    }




    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException {
        return getDataBaseHelper().getDispensedDrugDao().checkStockIsDispensedDrug(stock);
    }

    @Override
    public List<DispensedDrug> getDispensedDrugsByDispenses(List<Dispense> dispenses) throws SQLException {
        return getDataBaseHelper().getDispensedDrugDao().getDispensedDrugsByDispenses(dispenses);
    }

    @Override
    public void deleteDispensedDrugs(List<DispensedDrug> dispenseDrugs) throws SQLException {
        getDataBaseHelper().getDispensedDrugDao().delete(dispenseDrugs);
    }

    @Override
    public Long getDispensedDrugsByStockAndStock(Drug drug, Stock stock) throws SQLException {
        List<DispensedDrug> listDispenses = getDataBaseHelper().getDispensedDrugDao().getDispensedDrugsByStockAndStock(drug, stock);
        long total = 0;
        for(DispensedDrug d : listDispenses) {
            total +=d.getQuantitySupplied();
        }
        return total;
    }
}
