package com.example.emmanuel.lisheapp.tools;

public class Tool {


    /*Formats Time stamp into Custom date like October 20, 2017*/
    public static String formatDate (String date){
        String year = date.substring(0,4);
        int month = Integer.parseInt(date.substring(5,7));
        String day = date.substring(8,10);
        String text_month = null;
        switch (month){
            case 1:
                text_month = "January";
                break;
            case 2:
                text_month = "February";
                break;
            case 3:
                text_month = "March";
                break;
            case 4:
                text_month = "April";
                break;
            case 5:
                text_month = "May";
                break;
            case 6:
                text_month = "June";
                break;
            case 7:
                text_month = "July";
                break;
            case 8:
                text_month = "August";
                break;
            case 9:
                text_month = "September";
                break;
            case 10:
                text_month = "October";
                break;
            case 11:
                text_month = "November";
                break;
            case 12:
                text_month = "December";
                break;
        }
        String full_date = text_month+", "+day+" "+year;
        return full_date;
    }


}
