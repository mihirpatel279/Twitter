/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author LinJian
 */
public class DateAndTime {
    
    //define the format of the date
    public static final String DATE_FORMAT_NOW = "MM/DD/YYYY";
    //public static String dt;
    //return a String of the current system date and time
    public static String DateTime()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    
}
