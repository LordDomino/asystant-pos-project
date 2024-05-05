package main.java.sql;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static String generateSalesNo(Date date) {
        // 00000000-000000
        // DDMMYYYY-ORDER#
        // 29042024-000001

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearVal  = localDate.getYear();
        int monthVal = localDate.getMonthValue();
        int dayValue   = localDate.getDayOfMonth();

        String y = System.out.println(String.valueOf(yearVal));
        System.out.println(new DecimalFormat("00").format(monthVal));
        System.out.println(new DecimalFormat("00").format(dayValue));
        return date.toString();
    }

    public static void main(String[] args) {
        try {
            Utils.generateSalesNo(new Date(new SimpleDateFormat("ddMMyyyy").parse("29042024").getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}