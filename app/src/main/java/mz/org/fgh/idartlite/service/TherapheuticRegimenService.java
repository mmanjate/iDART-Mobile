package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;

public class TherapheuticRegimenService extends BaseService {

    public TherapheuticRegimenService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createTherapheuticRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException {
        getDataBaseHelper().getTherapeuticRegimenDao().create(therapeuticRegimen);
    }
}
