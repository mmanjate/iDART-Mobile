package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.common.ValorSimples;
import mz.org.fgh.idartlite.dao.GenericDao;

public class GenericReportService extends BaseService {
    private GenericDao genericDao;

    public GenericReportService(Application application) {
        super(application);

        try {
            genericDao = getDataBaseHelper().getGenericDao(ValorSimples.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ValorSimples> countDispensesRegimenByPeriod(Date start, Date end) throws SQLException {
        return genericDao.countDispensesRegimenByPeriod(start,end);
    }


}
