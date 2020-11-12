package mz.org.fgh.idartlite.service.prescription;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;


public interface IPrescribedDrugService extends IBaseService {

    public void createPrescribedDrug(PrescribedDrug prescribedDrug) throws SQLException ;

    public void savePrescribedDrug(Prescription prescription, String drugs) ;



}
