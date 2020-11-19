package mz.org.fgh.idartlite.viewmodel.patient;

import android.app.Application;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.DateUtilities;

public class PatientReportVM extends BaseViewModel {

    private IPatientService patientService;

    private String prerioType;


    public PatientReportVM(@NonNull Application application) {
        super(application);

        patientService = new PatientService(application);
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected <T extends BaseService> Class<T> getRecordServiceClass() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    public int countNewPatientByPeriod(Date start, Date end){

        int p = 0;
        try {
            p = patientService.countNewPatientsByPeriod(start, end);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<DateTime> processPeriods(String startDate, String endDate){
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateUtilities.DATE_FORMAT);

        if (DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.MONTH_FORMAT) <= 6){
            prerioType = "WEEK";
        }else if ((DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.MONTH_FORMAT) > 6) &&
                  (DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.MONTH_FORMAT) <= 12)){
            prerioType = "MONTH";
        }else if ((DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.MONTH_FORMAT) > 12) &&
                  (DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.YEAR_FORMAT) < 8)){
            prerioType = "YEAR";
        }else if (DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.MONTH_FORMAT) > 12){
            prerioType = "YEARS_INTERVAL";
        }else throw new RuntimeException("O periodo informado é inválido!");

        DateTime start = formatter.parseDateTime(startDate);
        DateTime end = formatter.parseDateTime(endDate);

        return generatePeriods(start, end, prerioType);
    }

    public String getPrerioType(){
        if (this.prerioType.equals("WEEK")){
            return "Semana";
        }else if (this.prerioType.equals("MONTH")){
            return "Mês";
        }else {
            return "Ano";
        }
    }

    private List<DateTime> generatePeriods(DateTime start, DateTime end, String prerioType) {
        List<DateTime> datesBetween = new ArrayList<>();

        while (start.isBefore(end)) {
            datesBetween.add(start);
            DateTime dateBetween;

            if (prerioType.equals("WEEK")){
                dateBetween = start.plusWeeks(1);
            }else if (prerioType.equals("MONTH")){
                dateBetween = start.plusMonths(1);
            }else if (prerioType.equals("YEAR")){
                dateBetween = start.plusMonths(1);
            }else if (prerioType.equals("YEARS_INTERVAL")){
                dateBetween = start.plusMonths(2);
            }else {
                dateBetween = start.plusYears(4);
            }

            if ((int) DateUtilities.dateDiff(DateUtilities.getCurrentDate(), dateBetween.toDate(), DateUtilities.DAY_FORMAT) < 0){
                dateBetween = new DateTime();
            }
            start = dateBetween;

            datesBetween.add(dateBetween);
        }

        return datesBetween;
    }
}
