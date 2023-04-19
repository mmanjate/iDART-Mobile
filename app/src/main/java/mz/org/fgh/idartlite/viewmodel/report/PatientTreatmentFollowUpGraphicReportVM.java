package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.PatientTreatmentFollowUpGraphicReportActivity;
import mz.org.fgh.idartlite.view.reports.PatientTreatmentFollowUpReportActivity;

public class PatientTreatmentFollowUpGraphicReportVM extends PatientTreatmentFollowUpReportVM {


    public PatientTreatmentFollowUpGraphicReportVM(@NonNull Application application) {
        super(application);
        deActivatePaginatedSearch();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        ((PatientTreatmentFollowUpGraphicReportActivity) getRelatedActivity()).displaySearchResult();
    }

    public String getReportTitle(){
        if (this.reportType.equals(ClinicInformation.PARAM_RAM_STATUS_ALL)) return "Número dos Pacientes Rastreados e Identificados para RAM por Unidade Sanitária";
        else if (this.reportType.equals(ClinicInformation.PARAM_FOLLOW_STATUS_ALL)) return "Número dos Pacientes Identificados e Monitorados para Adesão por Unidade Sanitária";

        return null;
    }

    public String getReportPosetiveBarTitle(){
        if (this.reportType.equals(ClinicInformation.PARAM_RAM_STATUS_ALL)) return "Pacientes com RAM";
        else if (this.reportType.equals(ClinicInformation.PARAM_FOLLOW_STATUS_ALL)) return "Pacientes com mais de 7 dias de atraso";

        return null;
    }

    public boolean isRAMReport(){
        return this.reportType.equals(ClinicInformation.PARAM_RAM_STATUS_ALL);
    }


    public boolean isFollowReport(){
        return this.reportType.equals(ClinicInformation.PARAM_FOLLOW_STATUS_ALL);
    }

}
