package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;

public class TherapeuthicLineService extends BaseService {

    public TherapeuthicLineService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createTherapheuticLine(TherapeuticLine therapeuticLine) throws SQLException {
        getDataBaseHelper().getTherapeuticLineDao().create(therapeuticLine);
    }
}
