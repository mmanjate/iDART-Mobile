package mz.org.fgh.idartlite.viewmodel.dispense;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.AwatingPatientsGraphReportActivity;

public class AwatingPatientsGraphReportVM extends AwatingPatientsReportVM {

    public AwatingPatientsGraphReportVM(@NonNull Application application) {
        super(application);
        deActivatePaginatedSearch();
    }

    @Override
    protected void doOnNoRecordFound() {
        Utilities.displayAlertDialog(getRelatedActivity(), "Não foram encontrados resultados para a sua pesquisa").show();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());
        ((AwatingPatientsGraphReportActivity)getRelatedActivity()).displaySearchResult();
    }
}
