package mz.org.fgh.idartlite.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtilities {

    public static final String YEAR_FORMAT="YYYY";
    public static final String DAY_FORMAT="DD";
    public static final String MONTH_FORMAT="MM";
    public static final String HOUR_FORMAT="HH";
    public static final String SECOND_FORMAT="ss";
    public static final String MINUTE_FORMAT="mm";
    public static final String MILLISECOND_FORMAT="SSS";
    public static final String DATE_FORMAT="dd-MM-yyyy";
    public static final String MONTH_DATE_FORMAT="MM-dd-yyyy";
    public static final String DATE_TIME_FORMAT="dd-MM-yyyy HH:mm:ss";
    public static final String END_DAY_TIME="23:59:59";

    public static int getDayOfMonth(Date date){
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return d.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfWeek(Date date){
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return d.get(Calendar.DAY_OF_WEEK);
    }

    public static int getMonth(Date date){
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return d.get(Calendar.MONTH) + 1;
    }

    public static int getYear(Date date){
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return d.get(Calendar.YEAR);
    }

    public static int getHours(Date date){
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return d.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutes(Date date){
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return d.get(Calendar.MINUTE);
    }

    public static int getSeconds(Date date){
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        return d.get(Calendar.SECOND);
    }

    public static Date getCurrentDate(){
        return Calendar.getInstance().getTime();
    }

    public static double dateDiff(Date dataMaior, Date dataMenor, String dateFormat){

        double differenceMilliSeconds =  dataMaior.getTime() - dataMenor.getTime();


        if (dateFormat.equals(DateUtilities.MILLISECOND_FORMAT)) return differenceMilliSeconds;

        double diferenceSeconds = differenceMilliSeconds/1000;

        if (dateFormat.equals(DateUtilities.SECOND_FORMAT)) return diferenceSeconds;

        double diferenceMinutes = diferenceSeconds/60;

        if (dateFormat.equals(DateUtilities.MINUTE_FORMAT)) return diferenceMinutes;

        double diferenceHours = diferenceMinutes/60;

        if (dateFormat.equals(DateUtilities.HOUR_FORMAT)) return diferenceHours;

        double diferenceDays = diferenceHours/24;

        if (dateFormat.equals(DateUtilities.DAY_FORMAT)) return diferenceDays;

        double diferenceMonts = diferenceDays/30;

        if (dateFormat.equals(DateUtilities.MONTH_FORMAT)) return diferenceMonts;

        double diferenceYears = diferenceMonts/12;

        if (dateFormat.equals(DateUtilities.YEAR_FORMAT)) return diferenceYears;

        throw new IllegalArgumentException("UNKOWN DATE FORMAT [" + dateFormat + "]");
    }

    public static String parseDateToYYYYMMDDString(Date toParse){
        SimpleDateFormat datetemp = new SimpleDateFormat("yyyy-MM-dd");
        String data = datetemp.format(toParse);
        return data;
    }


    public static Date createDate(String stringDate, String dateFormat) {
        try {
            SimpleDateFormat sDate = new SimpleDateFormat(dateFormat);
            Date date = sDate.parse(stringDate);

            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date createDateWithTime(String stringDate, String time, String dateFormat) {
        try {
            SimpleDateFormat sDate = new SimpleDateFormat(dateFormat);
            Date date = sDate.parse(stringDate+" "+time);

            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatToDDMMYYYY(Date date){
        if (date == null) return null;

        return formatToDDMMYYYY(date, "-");
    }

    public static String formatToDDMMYYYY(Date date, String separator){
        if (date == null){
            return "";
        }
        return garantirXCaracterOnNumber(getDayOfMonth(date), 2) + separator + garantirXCaracterOnNumber(getMonth(date), 2) + separator + getYear(date);
    }

    public static String formatToMMDDYYYY(Date date, String separator){
        if (date == null){
            return "";
        }
        return  garantirXCaracterOnNumber(getMonth(date), 2) + separator + garantirXCaracterOnNumber(getDayOfMonth(date), 2) + separator + getYear(date);
    }

    public static String formatToMMDDYYYY(Date date){
        return  formatToMMDDYYYY(date, "/");
    }

    public static String garantirXCaracterOnNumber(long number, int x){
        String formatedNumber = "";
        int numberOfCharacterToIncrise = 0;

        formatedNumber = number + "";

        numberOfCharacterToIncrise = x - formatedNumber.length();

        for(int i = 0; i < numberOfCharacterToIncrise; i++) formatedNumber = "0" + formatedNumber;

        return formatedNumber;
    }

    public static double calculaIdade(String dataMenor, String format) throws ParseException {

        String dataMaior = formatToDDMMYYYY(getCurrentDate());

        try {
            Date dataMenorAux = createDate(dataMenor, "dd-MM-yyyy");
            Date dataMaiorAux = createDate(dataMaior, "dd-MM-yyyy");

            return dateDiff(dataMaiorAux, dataMenorAux, format);
        } catch (Exception e) {
            return 0;
        }
    }


    public static String formatToDDMMYYYY(String date) throws IllegalArgumentException {
        if (date == null) return null;

        String[] partes = null;

        String dia = "",
                mes = "",
                ano = "";

        partes = date.split("/");
        if (partes.length > 1) {
            dia = partes[0];
            mes = partes[1];
            ano = partes[2];
        } else {
            partes = date.split("-");
            if (partes.length > 1) {
                dia = partes[0];
                mes = partes[1];
                ano = partes[2];
            } else {
                throw new IllegalArgumentException("O argumento indicado para o parametro Data E invalido!");
            }

        }



        /**
         * Se a data estiver no formato yyyy/mm/dd
         */
        if (Integer.parseInt(dia) > 31) {
            String anoAux = ano;
            ano = dia;
            dia = anoAux;
        }

        /**
         * Se por ventura a data estiver no formato dd/mm, isto E, se o dia vier antes do mes
         * Nota: esta situacao será detectada no caso em que o dia for maior que 12, caso contrario passará tudo despercebido.
         * 		 Isso E PERIGOSO, mas por enquanto nAo há alternativa
         */

        if (Integer.parseInt(mes) > 12){
            String mesAux = mes;

            mes = dia;
            dia = mesAux;
        }

        return dia + "-" + mes + "-" + ano;
    }

    public static String parseDateToDDMMYYYYString(Date toParse){
        SimpleDateFormat datetemp = new SimpleDateFormat("dd-MM-yyyy");
        String data = datetemp.format(toParse);
        return data;
    }

    public static String formatToDDMMYYYY_HHMISS(Date date){

        //DD-Mon-YY HH:MI:SS
        if (date == null){
            return "";
        }

        return Utilities.garantirXCaracterOnNumber(getDayOfMonth(date), 2) + "-" + Utilities.garantirXCaracterOnNumber(getMonth(date), 2) + "-" + getYear(date) + " " + formatToHHMISS(date);
    }

    /**
     * @author Jorge Boane
     * @return Transforma uma data para uma sitring no formato yyyy-mm-dd hh:mi:ss
     * @param  date
     *
     */

    public static String formatToYYYYMMDD_HHMISS(Date date){

        //DD-Mon-YY HH:MI:SS
        if (date == null){
            return "";
        }

        return formatToYYYYMMDD(date) + " " + formatToHHMISS(date);
    }

    public static String formatToYYYYMMDD(Date date){
        if (date == null) return null;

        return getYear(date) + "-" +  Utilities.garantirXCaracterOnNumber(getMonth(date), 2) + "-" + Utilities.garantirXCaracterOnNumber(getDayOfMonth(date), 2);
    }

    public static String formatToHHMISS(Date date){
        if (date == null){
            return "";
        }

        return Utilities.garantirXCaracterOnNumber(getHours(date), 2) + ":" + Utilities.garantirXCaracterOnNumber(getMinutes(date), 2) + ":" + Utilities.garantirXCaracterOnNumber(getSeconds(date), 2);
    }


    /**
     * Retorna o numero de dias entre as datas.
     *
     * @param date1 data inicial
     * @param date2 data final
     * @return numero de dias
     */
    public static long getDaysBetween(final Date date1, final Date date2)
    {
        return (date2.getTime()
                - date1.getTime())
                / (1000 * 60 * 60 * 24);
    }

    public static Date getDateFromDayAndMonthAndYear(int day,
                                                     int month,
                                                     int year)
    {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);

        return cal.getTime();
    }

    public static String getDateAfterAddingDaysToGivenDate(final String oldDate, int daysToAdd){

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtilities.DATE_FORMAT);

        Calendar c = Calendar.getInstance();
        try{
            c.setTime(sdf.parse(oldDate));
        }catch(ParseException e){
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH,daysToAdd);

        return sdf.format(c.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int isWeekend(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtilities.DATE_FORMAT);

        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        DayOfWeek dayOfWeek = DayOfWeek.of(localDate.get(ChronoField.DAY_OF_WEEK));
        switch (dayOfWeek) {
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 7;
            default:
                return 0;
        }
    }

    public static int isSaturdayOrSunday(String stringDate) {

        Date date = DateUtilities.createDate(stringDate, DateUtilities.DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case 7:
                return 6;
            case 1:
                return 7;
            default:
                return 0;
        }
    }

    public static Date getDateOfPreviousDays(Date date, int days)
    {
        if (days < 0)
        {
            throw new IllegalArgumentException(
                    "The days must be a positive value");
        }

        return DateUtils.addDays(date, (-1) * days);
    }

    public static Date getSqlDateFromString(String stringDate, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date date = (Date) format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getUtilDateFromString(String stringDate, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date date = (Date) format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getStringDateFromDate(Date date, String pattern) {
        SimpleDateFormat datetemp = new SimpleDateFormat(pattern, Locale.getDefault());
        String data = datetemp.format(date);
        return data;

    }

    public static Date getDateOfForwardDays(Date date, int days)
    {
        if (days < 0)
        {
            throw new IllegalArgumentException(
                    "The days must be a positive value");
        }

        return DateUtils.addDays(date, days);
    }
}
