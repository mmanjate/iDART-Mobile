package mz.org.fgh.idartlite.service.dispense;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.ReturnedDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.stock.StockService;

public class ReturnedDrugService extends BaseService implements IReturnedDrugService {


    private StockService stockService;
    private DispenseService dispenseService;
    public ReturnedDrugService(Application application, User currUser) {

        super(application, currUser);
        stockService=new StockService(application,currUser);
        dispenseService=new DispenseService(application,currUser);
    }




    @Override
    public void createReturnedDrug(ReturnedDrug returnedDrug) throws SQLException {



            Stock stock= returnedDrug.getDispensedDrug().getStock();
            Dispense dispense=returnedDrug.getDispensedDrug().getDispense();

           stock.setStockMoviment(stock.getStockMoviment()+returnedDrug.getQtyToModify());

            stockService.updateStock(stock);

            dispense.setReturned(true);
            dispenseService.udpateDispense(dispense);

            getDataBaseHelper().getReturnedDrugDao().create(returnedDrug);


    }
}
