package ir.parsdeveloper.commons.tags.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.jsp.tagext.Tag;
import java.beans.PropertyEditor;

/**
 * @author hadi tayebi
 */

public class TagUtils {

    @SuppressWarnings("unchecked")
    public static <E extends Tag> E getParent(Tag tag, Class<E> parentTagClass) {
        Tag parent = tag.getParent();
        if (parent == null) {
            return null;
        }
        if (parentTagClass.isAssignableFrom(parent.getClass())) {
            return (E) parent;
        }
        return getParent(parent, parentTagClass);
    }

    public static String getDisplayString(Object value) {
        return getDisplayString(value, TagConstants.DEFAULT_HTML_ESCAPE);
    }

    public static String getDisplayString(Object value, boolean htmlEscape) {
        if (value == null) {
            return "";
        }
        String displayValue = ObjectUtils.getDisplayString(value);
        return (htmlEscape ? HtmlUtils.htmlEscape(displayValue) : displayValue);
    }

    /**
     * Build the display value of the supplied <code>Object</code>, HTML escaped
     * as required. If the supplied value is not a {@link String} and the supplied
     * {@link PropertyEditor} is not null then the {@link PropertyEditor} is used
     * to obtain the display value.
     *
     * @see #getDisplayString(Object, boolean)
     */
    public static String getDisplayString(Object value, PropertyEditor propertyEditor) {
        return getDisplayString(value, propertyEditor, TagConstants.DEFAULT_HTML_ESCAPE);
    }

    public static String getDisplayString(Object value, PropertyEditor propertyEditor, boolean htmlEscape) {
        if (value == null) {
            return "";
        }
        if (propertyEditor != null && !(value instanceof String)) {
            try {
                propertyEditor.setValue(value);
                String text = propertyEditor.getAsText();
                if (text != null) {
                    return getDisplayString(text, htmlEscape);
                }
            } catch (Throwable ex) {
                // The PropertyEditor might not support this value... pass through.
            }
        }
        return getDisplayString(value, htmlEscape);
    }

    public static String createOpenTagString(String tagName, HtmlAttributeMap attributes) {
        StringBuilder buffer = new StringBuilder();

        buffer.append(TagConstants.TAG_OPEN).append(tagName);

        if (attributes != null) {
            buffer.append(attributes.toString());
        }
        buffer.append(TagConstants.TAG_CLOSE);
        return buffer.toString();
    }

    public static String divCssClass(int columnCount, boolean label) {
        String cssClass = "";
        if (label) {
            if (columnCount == 3) {
                cssClass = "col-lg-1 col-md-2 col-sm-2 col-xs-4";
            } else if (columnCount == 2) {
                cssClass = "col-lg-2 col-md-2 col-sm-2 col-xs-4";
            } else if (columnCount == 1) {
                cssClass = "col-lg-3 col-md-3 col-sm-4 col-xs-4";
            }
        } else {
            if (columnCount == 3) {
                cssClass = "col-lg-3 col-md-4 col-sm-4 col-xs-8";
            } else if (columnCount == 2) {
                cssClass = "col-lg-4 col-md-4 col-sm-4 col-xs-8";
            } else if (columnCount == 1) {
                cssClass = "col-lg-6 col-md-6 col-sm-8 col-xs-8";
            }
        }
        return cssClass;
    }


}
