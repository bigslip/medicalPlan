package ir.parsdeveloper.commons.utils;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.exception.ServiceException;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.internal.util.SerializationHelper;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author hadi tayebi
 */
public class CommonUtils {

    public static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    private static final char[] toRemoveCharacters = {'\u0020',
            '.',
            '-',
            '_',
            ','};
    private static final char[] toReplaceCharacters = {'\u06CC',
            '\u0626',
            '\u0621',
            '\u0624',
            '\u0622',
            // '\u067E',
            // '\u0698',
            //'\u06AF',
            //'\u0686',
            '\u06A9'};
    private static final char[] replacementCharacters = {'\u064A',
            '\u064A',
            '\u064A',
            '\u0648',
            '\u0627',
            // '\u0628',
            // '\u0631',
            //'\u0643',
            //'\u062C',
            '\u0643'};
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public static Object clone(Serializable source) {
        return SerializationHelper.clone(source);
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public static SimpleDateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    public static String getTime(Date date) {
        return timeFormat.format(date);
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static boolean isValidEmailAddress(String mail) {
        return emailPattern.matcher(mail).matches();
    }

    public static Byte[] byteArrayToObject(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        int i = 0;
        for (byte b : bytesPrim) bytes[i++] = b; //Autoboxing
        return bytes;
    }

    public static byte[] encodeBase64(byte[] binaryData) {
        return Base64.encodeBase64(binaryData);
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        return Base64.decodeBase64(base64Data);
    }

    public static byte[] decodeBase64(InputStream base64Data) {
        try {
            byte[] data = new byte[base64Data.available()];
            base64Data.read(data);
            return Base64.decodeBase64(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static InputStream decodeBase642(InputStream base64Data) {
        try {
            byte[] data = new byte[base64Data.available()];
            base64Data.read(data);
            return new ByteArrayInputStream(Base64.decodeBase64(data));
        } catch (Exception e) {
            return null;
        }
    }

    public static void validateMultipartImage(MultipartFile multipartFile) throws ServiceException {
        if (!Constants.ACCEPT_IMAGE_MIME_TYPES.contains(multipartFile.getContentType())) {
            throw new ServiceException(MessageBundleUtil.getMessage("errors.imageUpload.type", MessageBundleUtil.getMessage("uploadImage.supportFormats")));
        }
//        else if (multipartFile.getSize() > Constants.MAX_UPLOAD_SIZE) {
//            throw new ServiceException(MessageBundleUtil.getMessage("errors.imageUpload.size", String.valueOf(Constants.MAX_UPLOAD_SIZE / 1025) + " KB"));
//        }
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static Date addMinus(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days * -1); //minus number would decrement the days
        return cal.getTime();
    }

    public static String fixString(String str) {
        if (!StringUtils.hasText(str)) {
            return "";
        }
        char[] chars = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            boolean appended = false;
            for (int j = toReplaceCharacters.length - 1; j >= 0; j--) {
                if (chars[i] == toReplaceCharacters[j]) {
                    stringBuilder.append(replacementCharacters[j]);
                    appended = true;
                    break;
                }
            }
            if (!appended) {
                for (int j = toRemoveCharacters.length - 1; j >= 0; j--) {
                    if (chars[i] == toRemoveCharacters[j]) {
                        break;
                    }
                    if (j == 0) {
                        stringBuilder.append(chars[i]);
                    }
                }
            }
        }
        return stringBuilder.toString();

    }
}
