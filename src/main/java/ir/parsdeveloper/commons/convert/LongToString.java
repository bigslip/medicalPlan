package ir.parsdeveloper.commons.convert;


import org.springframework.binding.convert.converters.StringToObject;

public class LongToString extends StringToObject {

    public LongToString() {
        super(Long.class);
    }

    @Override
    protected Object toObject(String string, Class<?> targetClass) throws Exception {
        return Long.valueOf(string.replaceAll("[,\\s]", ""));
    }

    @Override
    protected String toString(Object object) throws Exception {
        return object.toString();
    }
}
