package mz.org.fgh.idartlite.viewmodel.dispense;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.AwatingPatientsGraphReportActivity;

public class AwatingPatientsGraphReportVM extends AwatingPatientsReportVM {

    public AwatingPatientsGraphReportVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void doOnNoRecordFound() {

    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());
        ((AwatingPatientsGraphReportActivity)getRelatedActivity()).displaySearchResult();
    }
}
