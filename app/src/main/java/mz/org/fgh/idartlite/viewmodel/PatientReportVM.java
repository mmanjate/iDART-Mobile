package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.service.PatientService;
import mz.org.fgh.idartlite.util.DateUtilitis;

public class PatientReportVM extends BaseViewModel {

    private PatientService patientService;



    public PatientReportVM(@NonNull Application application) {
        super(application);

        patientService = new PatientService(application);
    }

    public int countNewPatientByPeriod(Date start, Date end){

        int p = 0;
        try {
            p = patientService.countnewPatientsByPeriod(start, end);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<DateTime> processPeriods(String startDate, String endDate){
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DateUtilitis.DATE_FORMAT);

        String prerioType;

        if (DateUtilitis.dateDiff(DateUtilitis.createDate(endDate, DateUtilitis.DATE_FORMAT), DateUtilitis.createDate(startDate, DateUtilitis.DATE_FORMAT), DateUtilitis.MONTH_FORMAT) <= 6){
            prerioType = "WEEK";
        }else if ((DateUtilitis.dateDiff(DateUtilitis.createDate(endDate, DateUtilitis.DATE_FORMAT), DateUtilitis.createDate(startDate, DateUtilitis.DATE_FORMAT), DateUtilitis.MONTH_FORMAT) > 6) &&
                  (DateUtilitis.dateDiff(DateUtilitis.createDate(endDate, DateUtilitis.DATE_FORMAT), DateUtilitis.createDate(startDate, DateUtilitis.DATE_FORMAT), DateUtilitis.MONTH_FORMAT) <= 12)){
            prerioType = "MONTH";
        }else if ((DateUtilitis.dateDiff(DateUtilitis.createDate(endDate, DateUtilitis.DATE_FORMAT), DateUtilitis.createDate(startDate, DateUtilitis.DATE_FORMAT), DateUtilitis.MONTH_FORMAT) > 12) &&
                  (DateUtilitis.dateDiff(DateUtilitis.createDate(endDate, DateUtilitis.DATE_FORMAT), DateUtilitis.createDate(startDate, DateUtilitis.DATE_FORMAT), DateUtilitis.YEAR_FORMAT) < 8)){
            prerioType = "YEAR";
        }else if (DateUtilitis.dateDiff(DateUtilitis.createDate(endDate, DateUtilitis.DATE_FORMAT), DateUtilitis.createDate(startDate, DateUtilitis.DATE_FORMAT), DateUtilitis.MONTH_FORMAT) > 12){
            prerioType = "YEARS_INTERVAL";
        }else throw new RuntimeException("O periodo informado é inválido!");

        DateTime start = formatter.parseDateTime(startDate);
        DateTime end = formatter.parseDateTime(endDate);

        return generatePeriods(start, end, prerioType);
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

            if ((int) DateUtilitis.dateDiff(DateUtilitis.getCurrentDate(), dateBetween.toDate(), DateUtilitis.DAY_FORMAT) < 0){
                dateBetween = new DateTime();
            }
            start = dateBetween;

            datesBetween.add(dateBetween);
        }

        return datesBetween;
    }
}
