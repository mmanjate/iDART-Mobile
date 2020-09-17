package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;

public class StockService extends BaseService {

    public StockService(Application application, User currUser) {
        super(application, currUser);
    }
}
