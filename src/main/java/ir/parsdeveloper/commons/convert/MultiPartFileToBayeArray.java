package ir.parsdeveloper.commons.convert;


import org.springframework.binding.convert.converters.Converter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hadi tayebi on 7/1/2015
 */
public class MultiPartFileToBayeArray implements Converter {
    @Override
    public Class<?> getSourceClass() {
        return MultipartFile.class;
    }

    @Override
    public Class<?> getTargetClass() {
        return byte[].class;
    }

    @Override
    public Object convertSourceToTargetClass(Object source, Class<?> targetClass) throws Exception {
        return ((MultipartFile) source).getBytes();
    }
}
