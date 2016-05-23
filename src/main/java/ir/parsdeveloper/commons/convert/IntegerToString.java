package ir.parsdeveloper.commons.convert;


import org.springframework.binding.convert.converters.StringToObject;

public class IntegerToString extends StringToObject {

    public IntegerToString() {
        super(Integer.class);
    }

    @Override
    protected Object toObject(String string, Class<?> targetClass) throws Exception {
        return Integer.valueOf(string.replaceAll("[,\\s]", ""));
    }

    @Override
    protected String toString(Object object) throws Exception {
        return object.toString();
    }
}
