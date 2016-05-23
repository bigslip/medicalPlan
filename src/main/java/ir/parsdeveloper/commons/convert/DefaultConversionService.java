package ir.parsdeveloper.commons.convert;

/**
 * Created by h.tayebi on 11/30/2015.
 */
public class DefaultConversionService extends org.springframework.binding.convert.service.DefaultConversionService {
    public DefaultConversionService() {
        this.addConverter(new StringToDateConverter());
        this.addConverter(new MultiPartFileToObject());
        this.addConverter(new IntegerToString());
        this.addConverter(new LongToString());
    }
}
