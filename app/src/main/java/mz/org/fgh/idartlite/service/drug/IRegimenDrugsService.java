package mz.org.fgh.idartlite.service.drug;

import java.sql.SQLException;
import java.util.ArrayList;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;


public interface IRegimenDrugsService extends IBaseService<RegimenDrug> {

    public void createRegimenDrug(RegimenDrug regimenDrug) throws SQLException ;

    public void saveRegimenDrug(TherapeuticRegimen regimen, ArrayList drugs);


}
