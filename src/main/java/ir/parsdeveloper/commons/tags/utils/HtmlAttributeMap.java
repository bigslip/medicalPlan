package ir.parsdeveloper.commons.tags.utils;


import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author hadi tayebi
 */

public class HtmlAttributeMap extends HashMap<String, Object> {


    private static final char DELIMITER = '"';


    private static final char EQUALS = '=';


    private static final char SPACE = ' ';

    /**
     * toString method: returns attributes in the format: attributename="attributevalue" attr2="attrValue2" ...
     *
     * @return String representation of the HtmlAttributeMap
     */
    public String toString() {

        if (size() == 0) {
            return Constants.EMPTY_STRING;
        }

        // buffer extimated in number of attributes * 30
        StringBuilder buffer = new StringBuilder(size() * 30);


        Set<Map.Entry<String, Object>> entrySet = entrySet();


        for (Map.Entry entry : entrySet) {


            buffer
                    .append(SPACE)
                    .append(entry.getKey())
                    .append(EQUALS)
                    .append(DELIMITER)
                    .append(entry.getValue())
                    .append(DELIMITER);
        }


        return buffer.toString();
    }


    public String put(String key, MultipleHtmlAttribute multipleHtmlAttribute) {
        return "";
    }

    public String put(String key, String value) {
        if (StringUtils.hasText(value)) {
            return (String) super.put(key, value.replaceAll("\"", "\\\""));
        }
        return null;
    }

    public Object put(String key, String value, String defaultValue) {
        if (StringUtils.hasText(value)) {
            return super.put(key, value);
        }
        return null;
    }
}
