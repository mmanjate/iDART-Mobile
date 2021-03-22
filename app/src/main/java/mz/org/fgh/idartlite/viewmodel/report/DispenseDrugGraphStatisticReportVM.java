package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.DispenseDrugGraphStatisticReportActivity;

public class DispenseDrugGraphStatisticReportVM extends DispenseDrugStatisticReportVM {

    public DispenseDrugGraphStatisticReportVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());
        ((DispenseDrugGraphStatisticReportActivity) getRelatedActivity()).displaySearchResult();
    }
}
