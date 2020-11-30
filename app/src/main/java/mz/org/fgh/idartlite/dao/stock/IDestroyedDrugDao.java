package mz.org.fgh.idartlite.dao.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.Drug;

public interface IDestroyedDrugDao extends IGenericDao<DestroyedDrug, Integer> {

    public List<DestroyedDrug> searchDestructions(Application application, long offset, long limit) throws SQLException;


    List<DestroyedDrug> getByDateAndDrug(Application application, Date date, Drug drug) throws SQLException;
}
