package ir.parsdeveloper.commons.utils;

//import com.tss.utils.calendar.JalaliCalendar;

import ir.parsdeveloper.commons.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Vahid Mavaji <br>
 * @version $Revision: 1.2 $ $Date: 2010/12/18 12:52:55 $ $Author: v_mavaji $
 */
public class DateUtil {
    private static final String TIME_PATTERN = "HH:mm";
    private static Log log = LogFactory.getLog(DateUtil.class);

    private DateUtil() {
    }

    public static String getDatePattern() {
        return "yyyy/MM/dd";
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date
     * in the form dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask   the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws java.text.ParseException when String doesn't match the expected format
     * @see java.text.SimpleDateFormat
     */
    public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
//            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
            return null;
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * MM/dd/yyyy HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws java.text.ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws java.text.ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }

            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return aDate;
    }


    public static Date jalaliToGregorian(String jDate) {
        if (jDate == null) {
            return null;
        }

        String[] jDateSections = jDate.split("/");


        int jYear = new Integer(jDateSections[0]);
        int jMonth = new Integer(jDateSections[1]);
        int jDay = new Integer(jDateSections[2]);


        int[] gDaysInMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] jDaysInMonth = new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};

        double gYear, gMonth, gDay;
        int jY, jM, jD;
        double gDayNo, jDayNo;
        int leap;

        int i;

        jY = jYear - 979;
        jM = jMonth - 1;
        jD = jDay - 1;

        jDayNo = 365 * jY + Math.floor(jY / 33) * 8 + Math.floor((jY % 33 + 3) / 4);
        for (i = 0; i < jM; ++i) {
            jDayNo += jDaysInMonth[i];
        }

        jDayNo += jD;

        gDayNo = jDayNo + 79;

        gYear = 1600 + 400 * Math.floor((gDayNo) / (146097));
        /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
        gDayNo = gDayNo % 146097;

        leap = 1;
        if (gDayNo >= 36525) /* 36525 = 365*100 + 100/4 */ {
            gDayNo--;
            gYear += 100 * Math.floor((gDayNo) / (36524));
            /* 36524 = 365*100 + 100/4 - 100/100 */
            gDayNo = gDayNo % 36524;

            if (gDayNo >= 365) {
                gDayNo++;
            } else {
                leap = 0;
            }
        }

        gYear += 4 * Math.floor((gDayNo) / (1461));
        /* 1461 = 365*4 + 4/4 */
        gDayNo %= 1461;

        if (gDayNo >= 366) {
            leap = 0;

            gDayNo--;
            gYear += Math.floor((gDayNo) / (365));
            gDayNo = gDayNo % 365;
        }

        int val = 0;
        if (i == 1) {
            val = 1;
        }

        for (i = 0; gDayNo >= gDaysInMonth[i] + val + ((i == 1 && leap == 1) ? 1 : 0); i++) {
            gDayNo -= gDaysInMonth[i] + ((i == 1 && leap == 1) ? 1 : 0);
        }
        gMonth = i + 1;
        gDay = gDayNo + 1;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, (int) gYear);
        calendar.set(Calendar.MONTH, (int) (gMonth - 1));
        calendar.set(Calendar.DAY_OF_MONTH, (int) gDay);

        return calendar.getTime();
    }


    public static String gregorianToJalali(Date date, boolean includeTime) {
        String dateStr = gregorianToJalali(date);
        if (includeTime) {
            return dateStr;
        }
        return dateStr + Constants.BLANK_SPACE + CommonUtils.getTime(date);
    }

    public static String gregorianToJalali(Date date) {
        String ret = "";
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            int gy1 = c.get(Calendar.YEAR);
            int gm1 = c.get(Calendar.MONTH);
            gm1++;
            int gd1 = c.get(Calendar.DAY_OF_MONTH);
            ret = gregorianToJalali(gy1, gm1, gd1);
        }
        return ret;
    }


    public static String gregorianToJalali(int gy1, int gm1, int gd1) {
        int gDaysInMonth[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int jDaysInMonth[] = new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
        int gy = gy1 - 1600;
        int gm = gm1 - 1;
        int gd = gd1 - 1;
        int gDayNo = 365 * gy + doubleToInt((gy + 3) / 4) - doubleToInt((gy + 99) / 100) + doubleToInt((gy + 399) / 400);
        int i;
        for (i = 0; i < gm; ++i) {
            gDayNo += gDaysInMonth[i];
        }
        if (gm > 1 && ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))) {
            gDayNo++;
        }
        gDayNo += gd;
        int jDayNo = gDayNo - 79;
        int j_np = doubleToInt(jDayNo / 12053); /* 12053 = 365*33 + 32/4 */
        jDayNo = jDayNo % 12053;
        int jy = 979 + 33 * j_np + 4 * doubleToInt(jDayNo / 1461); /* 1461 = 365*4 + 4/4 */
        jDayNo %= 1461;
        if (jDayNo >= 366) {
            jy += doubleToInt((jDayNo - 1) / 365);
            jDayNo = (jDayNo - 1) % 365;
        }
        for (i = 0; i < 11 && jDayNo >= jDaysInMonth[i]; ++i) {
            jDayNo -= jDaysInMonth[i];
        }
        int jm = i + 1;

        String month = String.valueOf(jm);
        if (month.length() < 2) {
            month = "0" + month;
        }

        String day = String.valueOf(jDayNo + 1);
        if (day.length() < 2) {
            day = "0" + day;
        }

        return jy + "/" + month + "/" + day;
    }

    private static int doubleToInt(double f) {
        Double fint = new Double(f);
        return fint.intValue();
    }

    public static Date addDay(Date date, int dayAmount) {
        if (date == null) {
            return null;
        }
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, dayAmount);
        return cal.getTime();
    }

    public static Date jalaliToGregorianWithCurrentTime(String jDate) {
        Date date = jalaliToGregorian(jDate);
        Calendar sourceCalendar = GregorianCalendar.getInstance();
        sourceCalendar.setTime(date);

        Calendar destCal = GregorianCalendar.getInstance();
        destCal.set(Calendar.YEAR, sourceCalendar.get(Calendar.YEAR));
        destCal.set(Calendar.MONTH, sourceCalendar.get(Calendar.MONTH));
        destCal.set(Calendar.DAY_OF_MONTH, sourceCalendar.get(Calendar.DAY_OF_MONTH));
        return destCal.getTime();
    }


    public static Date setCurrentHMSms(Date date) {
        Calendar sourceCalendar = GregorianCalendar.getInstance();

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, sourceCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, sourceCalendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, sourceCalendar.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, sourceCalendar.get(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    public static String gregorianToJalaliWithTime(int gy1, int gm1, int gd1) {
        int gDaysInMonth[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int jDaysInMonth[] = new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
        int gy = gy1 - 1600;
        int gm = gm1 - 1;
        int gd = gd1 - 1;
        int gDayNo = 365 * gy + doubleToInt((gy + 3) / 4) - doubleToInt((gy + 99) / 100) + doubleToInt((gy + 399) / 400);
        int i;
        for (i = 0; i < gm; ++i) {
            gDayNo += gDaysInMonth[i];
        }
        if (gm > 1 && ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))) {
            gDayNo++;
        }
        gDayNo += gd;
        int jDayNo = gDayNo - 79;
        int j_np = doubleToInt(jDayNo / 12053); /* 12053 = 365*33 + 32/4 */
        jDayNo = jDayNo % 12053;
        int jy = 979 + 33 * j_np + 4 * doubleToInt(jDayNo / 1461); /* 1461 = 365*4 + 4/4 */
        jDayNo %= 1461;
        if (jDayNo >= 366) {
            jy += doubleToInt((jDayNo - 1) / 365);
            jDayNo = (jDayNo - 1) % 365;
        }
        for (i = 0; i < 11 && jDayNo >= jDaysInMonth[i]; ++i) {
            jDayNo -= jDaysInMonth[i];
        }
        int jm = i + 1;

        Date currentDate = new Date(System.currentTimeMillis());

        SimpleDateFormat smp = new SimpleDateFormat("HH:mm:ss");
        smp.format(currentDate);


        return jy + "/" + jm + "/" + (jDayNo + 1) + "    " + smp.format(currentDate);
    }

    public static String gregorianToJalaliWithTime(Date date) {
        String ret = null;
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            int gy1 = c.get(Calendar.YEAR);
            int gm1 = c.get(Calendar.MONTH);
            gm1++;
            int gd1 = c.get(Calendar.DAY_OF_MONTH);
            ret = gregorianToJalaliWithTime(gy1, gm1, gd1);

        }
        return ret;
    }

    public static String getTime(Date date) {
        if (date == null) return null;
        String timeFormat = "HH:mm";
        java.text.SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
        return dateFormat.format(date);
    }


    /*  public static int getDiffMonth(String jDate1, String jDate2) {
        Date gDate1 = DateUtil.jalaliToGregorian(jDate1);
        Date gDate2 = DateUtil.jalaliToGregorian(jDate2);

        JalaliCalendar jc = new JalaliCalendar();

        jc.setTime(gDate1);

        int diff = 0;

        do {
            diff++;

            jc.add(Calendar.MONTH, 1);

            gDate1 = jc.getTime();
            jDate1 = DateUtil.gregorianToJalali(gDate1);
            System.out.println(jDate1);
        } while (gDate1.before(gDate2));

        return diff;
    }*/

}
