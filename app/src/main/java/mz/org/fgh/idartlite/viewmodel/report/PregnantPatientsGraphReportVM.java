package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.PregnantPatientGraphReportActivity;
import mz.org.fgh.idartlite.viewmodel.clinicInfo.PregnantPatientReportVM;

public class PregnantPatientsGraphReportVM extends PregnantPatientReportVM {

    public PregnantPatientsGraphReportVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());
        ((PregnantPatientGraphReportActivity) getRelatedActivity()).displaySearchResult();
    }



  /*  public PregnantPatientGraphReportActivity getRelatedActivity() {
        return (PregnantPatientGraphReportActivity) super.getRelatedActivity();
    }*/
}
