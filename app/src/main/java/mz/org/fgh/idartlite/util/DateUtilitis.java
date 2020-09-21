package mz.org.fgh.idartlite.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilitis {

    public static final String YEAR_FORMAT="YYYY";
    public static final String DAY_FORMAT="DD";
    public static final String MONTH_FORMAT="MM";
    public static final String HOUR_FORMAT="HH";
    public static final String SECOND_FORMAT="ss";
    public static final String MINUTE_FORMAT="mm";
    public static final String MILLISECOND_FORMAT="SSS";
    public static final String DATE_FORMAT="dd-MM-yyyy";

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


        if (dateFormat.equals(DateUtilitis.MILLISECOND_FORMAT)) return differenceMilliSeconds;

        double diferenceSeconds = differenceMilliSeconds/1000;

        if (dateFormat.equals(DateUtilitis.SECOND_FORMAT)) return diferenceSeconds;

        double diferenceMinutes = diferenceSeconds/60;

        if (dateFormat.equals(DateUtilitis.MINUTE_FORMAT)) return diferenceMinutes;

        double diferenceHours = diferenceMinutes/60;

        if (dateFormat.equals(DateUtilitis.HOUR_FORMAT)) return diferenceHours;

        double diferenceDays = diferenceHours/24;

        if (dateFormat.equals(DateUtilitis.DAY_FORMAT)) return diferenceDays;

        double diferenceMonts = diferenceDays/30;

        if (dateFormat.equals(DateUtilitis.MONTH_FORMAT)) return diferenceMonts;

        double diferenceYears = diferenceMonts/12;

        if (dateFormat.equals(DateUtilitis.YEAR_FORMAT)) return diferenceYears;

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

}
