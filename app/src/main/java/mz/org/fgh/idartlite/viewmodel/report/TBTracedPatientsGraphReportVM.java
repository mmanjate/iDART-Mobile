package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.TBTracedPatientGraphReportActivity;
import mz.org.fgh.idartlite.viewmodel.clinicInfo.TBTracedPatientReportVM;

public class TBTracedPatientsGraphReportVM extends TBTracedPatientReportVM {

    public TBTracedPatientsGraphReportVM(@NonNull Application application) {
        super(application);
        deActivatePaginatedSearch();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());
        ((TBTracedPatientGraphReportActivity) getRelatedActivity()).displaySearchResult();
    }

}
