package py.com.pol.politool.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class StringUtil {

    public static String extractExtension(String fileName){
        String[] splitted = fileName.split("\\.");
        return splitted[splitted.length-1];
    }

    public static String extractDayOfWeek(String date){
        String[] splitted = date.split(" ");
        date = splitted[splitted.length-1];
        if(date == null || date.isBlank()){
            return null;
        }
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(patternRecognizer(date)));
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public static String extractDate(String date){
        String[] splitted = date.split(" ");
        date = splitted[splitted.length-1];
        if(date == null || date.isBlank()){
            return null;
        }
        return date;
    }

    private static String patternRecognizer(String date){
        String splitted[] = date.split("\\/");
        String separador = "/";
        if(splitted.length == 1){
            separador = "-";
            splitted = date.split("\\-");
            if(splitted.length == 1){
                throw new RuntimeException("No se pudo reconocer el formato de la fecha: " + date);
            }
        }
        if(splitted.length != 3){
            throw new RuntimeException("No se pudo reconocer el formato de la fecha: " + date);
        }

        int daysDigits = splitted[0].length();
        int monthsDigits = splitted[1].length();
        int yearsDigits = splitted[2].length();

        return new StringBuilder()
        .append(String.format("%"+daysDigits+"s","d").replaceAll(" ","d"))
        .append(separador)
        .append(String.format("%"+monthsDigits+"s","M").replaceAll(" ","M"))
        .append(separador)
        .append(String.format("%"+yearsDigits+"s","y").replaceAll(" ","y"))
        .toString();

    }

}
