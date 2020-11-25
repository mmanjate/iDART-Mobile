package mz.org.fgh.idartlite.viewmodel.dispense;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.util.Utilities;

public class DispensedDrugsReportVM extends BaseViewModel {
    private IDispenseService dispenseService;

    private double totalAviamentos;

    public DispensedDrugsReportVM(@NonNull Application application) {
        super(application);
        dispenseService = new DispenseService(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return null;
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public void preInit() {

    }

    public Map<String, Double> search(Date start, Date end) throws SQLException {
        Map<String, Integer> map = new HashMap<>();

        Map<String, Double> percentageMap = new HashMap<>();

        List<Dispense> dispenseLis = dispenseService.getDispensesBetweenStartDateAndEndDate(start, end);
        if (Utilities.listHasElements(dispenseLis)){
            totalAviamentos = dispenseLis.size();

            for (Dispense dispense : dispenseLis){
                if (map.containsKey(dispense.getPrescription().getTherapeuticRegimen().getDescription())){
                    int qty = map.get(dispense.getPrescription().getTherapeuticRegimen().getDescription());
                    map.remove(dispense.getPrescription().getTherapeuticRegimen().getDescription());
                    map.put(dispense.getPrescription().getTherapeuticRegimen().getDescription(), qty+1);
                }else {
                    map.put(dispense.getPrescription().getTherapeuticRegimen().getDescription(), 1);
                }
            }

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                percentageMap.put(entry.getKey()+" ["+entry.getValue()+"]-["+((100*entry.getValue())/totalAviamentos)+"%]", ((100*entry.getValue())/totalAviamentos));
            }
        }
        return percentageMap;
    }

    public double getTotalAviamentos(){
        return this.totalAviamentos;
    }
}
