package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;

import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.DispenseDrugGraphStatisticReportActivity;

public class DispenseDrugGraphStatisticReportVM extends DispenseDrugStatisticReportVM {

    public DispenseDrugGraphStatisticReportVM(@NonNull Application application) {
        super(application);
    }

    public void dismisDialog() {
        getLoadingDialog().dismisDialog();
    }
}
