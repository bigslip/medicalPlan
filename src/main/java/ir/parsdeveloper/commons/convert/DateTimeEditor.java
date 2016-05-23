package ir.parsdeveloper.commons.convert;

import ir.parsdeveloper.commons.utils.DateUtil;
import ir.parsdeveloper.commons.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author hadi tayebi
 */
public class DateTimeEditor extends PropertyEditorSupport {
    private static Log logger = LogFactory.getLog(DateTimeEditor.class);


    private boolean allowEmpty = true;

    private int exactDateLength = -1;


    public DateTimeEditor() {
    }

    public DateTimeEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }


    public DateTimeEditor(boolean allowEmpty, int exactDateLength) {

        this.allowEmpty = allowEmpty;
        this.exactDateLength = exactDateLength;
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    public String getAsText() {
        Date value = (Date) getValue();
        if (value == null) {
            return "";
        }

        return DateUtil.gregorianToJalali(value);
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
            throw new IllegalArgumentException(
                    "Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
        } else {
            if (text != null) {
                String[] texts = text.split(" ");
                Date date;
                if (text.startsWith("12") || text.startsWith("13") || text.startsWith("14")) {
                    date = DateUtil.jalaliToGregorian(texts[0]);
                } else {
                    try {
                        date = DateUtil.convertStringToDate(texts[0]);
                    } catch (ParseException e) {
                        setValue(null);
                        return;
                    }
                }

                if (texts.length == 2) {
                    String[] time = texts[1].split(":");
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR, Integer.valueOf(time[0]));
                    calendar.set(Calendar.MINUTE, Integer.valueOf(time[1]));
                    date = calendar.getTime();
                }
                setValue(date);

            }
        }
    }
}
