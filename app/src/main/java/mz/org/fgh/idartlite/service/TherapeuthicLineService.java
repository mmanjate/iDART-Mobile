package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static mz.org.fgh.idartlite.model.DiseaseType.COLUMN_DESCRIPTION;

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

    public TherapeuticLine getTherapeuticLine(String code) throws SQLException {

        List<TherapeuticLine> typeList = getDataBaseHelper().getTherapeuticLineDao().queryForEq(COLUMN_DESCRIPTION,code);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public boolean checkLine(Object line) {

        boolean result = false;
        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) line;

        try {
            TherapeuticLine localLine = getTherapeuticLine((Objects.requireNonNull(itemresult.get("linhanome")).toString()));

            if(localLine != null)
                result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


    public void saveLine(Object line){

        TherapeuticLine localLine = new TherapeuticLine();

        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) line;

            localLine.setId((int) Float.parseFloat(Objects.requireNonNull(itemresult.get("linhaid")).toString()));
            localLine.setCode((Objects.requireNonNull(itemresult.get("linhanome")).toString()));
            localLine.setDescription((Objects.requireNonNull(itemresult.get("linhanome")).toString()));
            createTherapheuticLine(localLine);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
