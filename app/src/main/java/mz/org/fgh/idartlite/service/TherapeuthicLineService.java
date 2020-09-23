package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;

public class TherapeuthicLineService extends BaseService {

    public TherapeuthicLineService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createTherapheuticLine(TherapeuticLine therapeuticLine) throws SQLException {
        getDataBaseHelper().getTherapeuticLineDao().create(therapeuticLine);
    }

    public List<TherapeuticLine> getAll() throws SQLException {
        return getDataBaseHelper().getTherapeuticLineDao().queryForAll();
    }
}
