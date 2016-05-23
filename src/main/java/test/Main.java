package test;


import com.sun.xml.wss.impl.misc.Base64;
import ir.parsdeveloper.commons.utils.CommonUtils;
import org.slf4j.Logger;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author hadi tayebi
 */

public class Main {
    private final static SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    protected static Logger logger;
    static int tax_percent = 3;

/*    public static void main(String s[]) throws Exception {
        Calendar calendar0 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        System.out.println(calendar0.getTime());
        System.out.println(calendar0.get(Calendar.ZONE_OFFSET));
        System.out.println(calendar1.get(Calendar.ZONE_OFFSET));
        System.out.println(calendar2.get(Calendar.ZONE_OFFSET));
    }*/

    public static void main(String s[]) throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String builtTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        System.out.println(builtTime);


    }

    public static void main2(String s[]) throws Exception {

        calendarFormatter1.setTimeZone(TimeZone.getTimeZone("GMT"));
        String createdTime = calendarFormatter1.format(new Date());
        System.out.println("createdTime :" + createdTime);
        byte[] username = "admin".getBytes();
        System.out.println(Base64.encode(username));

        String usernameBase64 = new String(CommonUtils.encodeBase64(username));
        System.out.println("usernameBase64 :" + usernameBase64);
        byte[] rowPassword = ("admin" + createdTime + "e10adc3949ba59abbe56e057f20f883e").getBytes("utf-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] hash = sha.digest(rowPassword);
        byte[] pass = CommonUtils.encodeBase64(hash);
        System.out.println("pass : " + new String(pass, "utf-8"));
        calc(5000, 3000);
        /*for(int j=0;j<1000;j++) {
            for (int i = 0; i < 1000; i++) {
                calc( j,i);
            }
        }*/
/*
        PersistenceProvider provider=new HibernatePersistenceProvider();

        URL url=new URL("http","localhost",8161,"");
        URLConnection urlConnection = url.openConnection();
        System.out.println(urlConnection.getReadTimeout());

        java.util.jar.Manifest manifest = new java.util.jar.Manifest();
        manifest.read(Main.class.getResourceAsStream("/META-INF/MANIFEST.MF"));
        java.util.jar.Attributes attributes = manifest.getMainAttributes();
        String builtTime= attributes.getValue("Built-Time");
        builtTime="2016-01-17T08:28:52Z";
        System.out.println("builtTime : "+builtTime);
        DateFormatter dateFormatter=new DateFormatter("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            System.out.println(dateFormatter.parse(builtTime, Locale.ENGLISH).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    static void calc(long confirmedAmount, long supplementAmount) throws Exception {
        long totalConfirmedAmount = confirmedAmount + supplementAmount;

        float totalTax = (float) totalConfirmedAmount * tax_percent / 100;
        totalTax = Math.round(totalTax);

        long totalPayableAmount = (long) (totalConfirmedAmount - totalTax);


        float payableAmount = (float) confirmedAmount * totalPayableAmount / totalConfirmedAmount;

        float supplementPayableAmount = (float) supplementAmount * totalPayableAmount / totalConfirmedAmount;


        if (supplementPayableAmount > 0) {
            if (payableAmount > 0) {
                payableAmount = (float) Math.floor(payableAmount);
                supplementPayableAmount = totalPayableAmount - payableAmount;
            }
        }
        payableAmount = (long) payableAmount;
        supplementPayableAmount = (long) supplementPayableAmount;

        if (totalPayableAmount != (payableAmount + supplementPayableAmount)
                || (confirmedAmount + supplementAmount) != totalPayableAmount + totalTax) {
            throw new Exception("hhhhhhhhhhhhhhhhhhhhhhhhhooooooooooooooooooooooooooo!!!!!!!!!!!");
        }


        System.out.println("totalTax " + totalTax);
        System.out.println("-----------------------------------------");
        System.out.println("payableAmount " + payableAmount);
        System.out.println("supplementPayableAmount " + supplementPayableAmount);
        System.out.println("-----------------------------------------");
        System.out.println("totalPay " + totalPayableAmount);

    }


    static void calc2(float confirmedAmount, float supplementAmount) {
        float tax1 = confirmedAmount * tax_percent / 100;
        float tax2 = supplementAmount * tax_percent / 100;
        float totalTax = tax1 + tax2;

        float payableAmount = confirmedAmount - tax1;

        float supplementPayableAmount = supplementAmount - tax2;

        if (supplementPayableAmount > 0) {
            supplementPayableAmount = (float) (supplementPayableAmount + (payableAmount % Math.floor(payableAmount)));
            supplementPayableAmount = Math.round(supplementPayableAmount);
            payableAmount = (float) Math.floor(payableAmount);
        } else {
            payableAmount = Math.round(payableAmount);
        }

        long totalPay = Math.round(confirmedAmount + supplementAmount - totalTax);
        totalTax = Math.round(totalTax);
        if (totalPay != (payableAmount + supplementPayableAmount)) {
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhooooooooooooooooooooooooooo!!!!!!!!!!!");
        }

        System.out.println("tax1 " + tax1);
        System.out.println("tax2 " + tax2);
        System.out.println("totalTax " + totalTax);
        System.out.println("-----------------------------------------");
        System.out.println("payableAmount " + payableAmount);
        System.out.println("supplementPayableAmount " + supplementPayableAmount);
        System.out.println("-----------------------------------------");
        System.out.println("totalPay " + totalPay);

    }


}
