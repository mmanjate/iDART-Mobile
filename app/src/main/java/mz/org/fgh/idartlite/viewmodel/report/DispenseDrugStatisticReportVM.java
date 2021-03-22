package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.service.dispense.DispenseDrugService;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseDrugService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.drug.ITherapheuticRegimenService;
import mz.org.fgh.idartlite.service.drug.TherapheuticRegimenService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.DispenseDrugStatisticReportActivity;
import mz.org.fgh.idartlite.view.reports.PatientsAwaitingStatisticsActivity;

public class DispenseDrugStatisticReportVM extends SearchVM<Dispense> {


    private IDispenseService dispenseService;

    private IDispenseDrugService dispenseDrugService;

    private ITherapheuticRegimenService therapheuticRegimenService;

    private String startDate;

    private String endDate;


    public DispenseDrugStatisticReportVM(@NonNull Application application) {
        super(application);
        dispenseService = new DispenseService(application, getCurrentUser());
        therapheuticRegimenService = new TherapheuticRegimenService(application, getCurrentUser());
        dispenseDrugService = new DispenseDrugService(application, getCurrentUser());
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

    public List getDispensesByDates(Date startDate, Date endDate, long offset, long limit) throws SQLException {

        ArrayList list = new ArrayList();

        List<Dispense> dispenseList = dispenseService.getDispensesBetweenStartDateAndEndDateWithLimit(startDate, endDate,offset,limit);


        List<DispensedDrug> dispensedDrugs = dispenseDrugService.getDispensedDrugsByDispenses(dispenseList);


        Map<Drug,List<Dispense>> drugsMap=new HashMap<>();


        for (int i=0;i<dispensedDrugs.size();i++){

            Drug    value = dispensedDrugs.get(i).getStock().getDrug();

            List<Dispense> al = drugsMap.get(value);

            if (al == null)
            {
                drugsMap.put(value, (List<Dispense>) (al = new ArrayList<Dispense>()));
            }

            al.add(dispensedDrugs.get(i).getDispense());
        }


        for (Drug drug: drugsMap.keySet()) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nomeMedicamento", drug.getDescription());
            map.put("totalGeral", drugsMap.get(drug).size());

            list.add(map);
        }


        return list;
    }


    @Override
    public void initSearch() {
        if (!Utilities.stringHasValue(startDate) || !Utilities.stringHasValue(endDate)) {
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.start_end_date_is_mandatory)).show();
        } else if (DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0) {
            Utilities.displayAlertDialog(getRelatedActivity(), "A data inicio deve ser menor que a data fim.").show();
        } else if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT)) < 0) {
            Utilities.displayAlertDialog(getRelatedActivity(), "A data inicio deve ser menor ou igual que a data corrente.").show();
        } else {

            try {
                super.initSearch();
                if (getAllDisplyedRecords().size() > 0) {
                    getRelatedActivity().generatePdfButton(true);
                } else {
                    Utilities.displayAlertDialog(getRelatedActivity(), "Não foram encontrados resultados para a sua pesquisa").show();
                    getRelatedActivity().generatePdfButton(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void generatePDF() {
        try {
            this.getRelatedActivity().createPdfDocument();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnNoRecordFound() {

    }


    public List doSearch(long offset, long limit) throws SQLException {

        return getDispensesByDates(DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), offset, limit);
    }


    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    @Override
    public AbstractSearchParams<Dispense> initSearchParams() {
        return null;
    }

    public DispenseDrugStatisticReportActivity getRelatedActivity() {
        return (DispenseDrugStatisticReportActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

    }

    @Bindable
    public Clinic getClinic() {
        return getCurrentClinic();
    }

    @Bindable
    public String getSearchParam() {
        return startDate;
    }

    public void setSearchParam(String searchParam) {
        this.startDate = searchParam;
        notifyPropertyChanged(BR.searchParam);
    }

    @Bindable
    public String getSearchParam2() {
        return endDate;
    }

    public void setSearchParam2(String searchParam) {
        this.endDate = searchParam;
        notifyPropertyChanged(BR.searchParam);
    }

}
