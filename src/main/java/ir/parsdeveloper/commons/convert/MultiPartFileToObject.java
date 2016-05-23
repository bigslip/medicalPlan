package ir.parsdeveloper.commons.convert;


import ir.parsdeveloper.commons.utils.CommonUtils;
import org.springframework.binding.convert.converters.Converter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hadi tayebi on 7/1/2015
 */
public class MultiPartFileToObject implements Converter {

    public Class<?> getSourceClass() {
        return MultipartFile.class;
    }


    public Class<?> getTargetClass() {
        return byte[].class;
    }


    public Object convertSourceToTargetClass(Object source, Class<?> targetClass) throws Exception {
        byte[] bytes = ((MultipartFile) source).getBytes();
        return CommonUtils.encodeBase64(bytes);
    }
}
