package mz.org.fgh.idartlite.service.report;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.SimpleValue;


public interface IGenericReportService extends IBaseService {

    public List<SimpleValue> countDispensesRegimenByPeriod(Date start, Date end) throws SQLException;

}
