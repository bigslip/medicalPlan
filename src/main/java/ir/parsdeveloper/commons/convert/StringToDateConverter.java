package ir.parsdeveloper.commons.convert;

import ir.parsdeveloper.commons.utils.DateUtil;
import org.springframework.binding.convert.converters.StringToObject;

import java.util.Date;

/**
 * @author hadi tayebi on 6/24/2015
 */
public class StringToDateConverter extends StringToObject {

    public StringToDateConverter() {
        super(Date.class);
    }

    @Override
    protected Object toObject(String date, Class<?> targetClass) throws Exception {
        return DateUtil.jalaliToGregorian(date);
    }

    @Override
    protected String toString(Object date) throws Exception {
        return DateUtil.gregorianToJalali((Date) date);
    }
}
