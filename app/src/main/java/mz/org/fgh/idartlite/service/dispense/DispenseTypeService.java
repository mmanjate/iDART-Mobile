package mz.org.fgh.idartlite.service.dispense;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.User;

public class DispenseTypeService extends BaseService<DispenseType>  implements  IDispenseTypeService{

    public DispenseTypeService(Application application, User currUser) {
        super(application, currUser);
    }

    public DispenseTypeService(Application application) {
        super(application);
    }

    @Override
    public void save(DispenseType record) throws SQLException {

    }

    @Override
    public void update(DispenseType relatedRecord) throws SQLException {

    }

    public void createDispenseType(DispenseType dispenseType) throws SQLException {
        getDataBaseHelper().getDispenseTypeDao().create(dispenseType);
    }

    public List<DispenseType> getAll() throws SQLException {
        return getDataBaseHelper().getDispenseTypeDao().getAll();
    }

    public DispenseType getDispenseTypeByDescription(String description) throws SQLException {

        return getDataBaseHelper().getDispenseTypeDao().getDispenseTypeByDescription(description);
    }

    public boolean checkDipsenseType(Object dispenseType) {

        boolean result = false;

        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) dispenseType;

        try {

            DispenseType localDispenseType = getDispenseTypeByDescription(itemresult.get("value").toString());

            if(localDispenseType != null)
                result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


    public void saveDispenseType(Object dispenseType){

        DispenseType loalDispensetype = new DispenseType();
        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) dispenseType;

            loalDispensetype.setCode(String.valueOf(itemresult.get("name")));
            loalDispensetype.setDescription(String.valueOf(itemresult.get("value")));
            createDispenseType(loalDispensetype);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
