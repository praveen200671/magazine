package com.anm.bslndmag.Utils;

import android.content.Context;

import com.anm.bslndmag.DBInstance;
import com.anm.bslndmag.HomeActivity;
import com.anm.bslndmag.Model.Vaccinations;
import com.anm.bslndmag.Session.CartSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

import es.dmoral.toasty.Toasty;

public class Utilsclass {
    public String getTodaysData() {
        return "";
    }

    public static String[] GetStringArray(ArrayList<Vaccinations> arr) {
        // declaration and initialise String Array
        String str[] = new String[arr.size()];
        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {
            // Assign each value to String array
            str[j] = arr.get(j).getVcc_name();
        }
        return str;
    }
    public static boolean CheckDates(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }
    public static String GetStringArrayArrayliststring(ArrayList<Vaccinations> a) {
//        // declaration and initialise String Array
////        String str[] = new String[arr.size()];
//        // ArrayList to Array Conversion
//        for (int j = 0; j < arr.size(); j++) {
//            // Assign each value to String array
//            str[j] = arr.get(j).getVcc_name();
//        }
//        return str;



        if (a == null)
            return "";

        int iMax = a.size() - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        b.append("");
        for (int i = 0; ; i++) {
            b.append(a.get(i).getVcc_name());
            if (i == iMax)
                return b.append("").toString();
            b.append(",");
        }
    }

    public static String GetStringArrayArrayliststringcode(ArrayList<Vaccinations> a) {
//        // declaration and initialise String Array
////        String str[] = new String[arr.size()];
//        // ArrayList to Array Conversion
//        for (int j = 0; j < arr.size(); j++) {
//            // Assign each value to String array
//            str[j] = arr.get(j).getVcc_name();
//        }
//        return str;



        if (a == null)
            return "";

        int iMax = a.size() - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        b.append("");
        for (int i = 0; ; i++) {
            b.append(a.get(i).getVcc_code());
            if (i == iMax)
                return b.append("").toString();
            b.append(",");
        }
    }


    public static String GetStringlisttostring(ArrayList<String> a) {
//        // declaration and initialise String Array
////        String str[] = new String[arr.size()];
//        // ArrayList to Array Conversion
//        for (int j = 0; j < arr.size(); j++) {
//            // Assign each value to String array
//            str[j] = arr.get(j).getVcc_name();
//        }
//        return str;



        if (a == null)
            return "";

        int iMax = a.size() - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        b.append("");
        for (int i = 0; ; i++) {
            b.append(a.get(i));
            if (i == iMax)
                return b.append("").toString();
            b.append(",");
        }
    }

    public static boolean validateDate()
    {
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);


            String dateStr = "30/08/2020";
            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = curFormater.parse(dateStr);
//            SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMMM-yyyy");
//            String newDateStr = postFormater.format(dateObj);
            //if(formattedDate.equalsIgnoreCase())

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date strDate = sdf.parse(dateStr);
            if (System.currentTimeMillis() > strDate.getTime()) {
                return false;
            }

        }catch (Exception e)
        {
            e.getMessage();
            return true;
        }
return true;
    }

    public static String  generateuniqid(Context context)
    {
        CartSession cartSession=new CartSession(context);

        return cartSession.getCount();
    }
    public static String getTodayDateYYYY_MM_DD()
    {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }



    public static void recivedSms(String message, Context context) {
        try
        {
            String edit = message;
            int myNum = 0;
            try {
                myNum = Integer.parseInt(edit.replaceAll("[\\D]", ""));
            } catch(NumberFormatException nfe) {

            }
            String str1 = String.valueOf(myNum);
            DBInstance dbInstance = DBInstance.getInstance(context);
                Toasty.success(context,str1).show();
                dbInstance.ChangeAddmotherOnlineSubmitStatus(str1);
                dbInstance.ChangeUpdatechildOnlineSubmitStatus(str1);

        }
        catch (Exception e)
        {
        }
    }

}
