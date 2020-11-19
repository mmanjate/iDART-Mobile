package mz.org.fgh.idartlite.service.prescription;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.drug.DrugService;

public class PrescribedDrugService extends BaseService<PrescribedDrug> implements IPrescribedDrugService {

    private static final String TAG = "PrescriptionDrugService";

    protected DrugService drugService = new DrugService(getApplication(), null);

    public PrescribedDrugService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public PrescribedDrugService(Application application) {
        super(application);
    }

    @Override
    public void save(PrescribedDrug record) throws SQLException {

    }

    @Override
    public void update(PrescribedDrug relatedRecord) throws SQLException {

    }


    public void createPrescribedDrug(PrescribedDrug prescribedDrug) throws SQLException {
        getDataBaseHelper().getPrescribedDrugDao().create(prescribedDrug);
    }


    public void savePrescribedDrug(Prescription prescription, String drugs) {

    List drugList = getListofObjectFromString(drugs);

        if (drugList.size() > 0) {
            for (Object drug : drugList) {
                try {
                    LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) drug;

                    Drug localDrug = drugService.getDrugByFNMCode(Objects.requireNonNull(itemresult.get("drugcode")).toString());

                    if (localDrug != null) {
                        PrescribedDrug prescribedDrug = new PrescribedDrug();
                        prescribedDrug.setPrescription(prescription);
                        prescribedDrug.setDrug(localDrug);
                        createPrescribedDrug(prescribedDrug);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    continue;
                }
            }
        }
    }

    @Override
    public List<PrescribedDrug> getAllByPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getPrescribedDrugDao().getAllByPrescription(prescription);
    }

    private List getListofObjectFromString(String stringJsonObject){
        List masterList = new ArrayList();
        String[] lines = stringJsonObject.replace("},{","}\n{").split("\\n");
        for (String line : lines) {
            if (line.startsWith("[{"))
                line = line.replace("[{", "{");
            if (line.endsWith("}]"))
                line = line.replace("}]", "}");

            Gson gson = new Gson();
            masterList.add(gson.fromJson(line, Object.class));
        }
        return masterList;
    }

}
