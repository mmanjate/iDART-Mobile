package mz.org.fgh.idartlite.service.drug;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.util.Utilities;


public interface IRegimenDrugsService extends IBaseService {

    public void createRegimenDrug(RegimenDrug regimenDrug) throws SQLException ;

    public void saveRegimenDrug(TherapeuticRegimen regimen, ArrayList drugs);


}
