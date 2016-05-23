package ir.parsdeveloper.commons.tags.form;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import ir.parsdeveloper.commons.utils.StringUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * @author hadi tayebi
 */
public class ButtonTag extends org.springframework.web.servlet.tags.form.ButtonTag {

    public final static String DEFAULT_BUTTON_TYPE = "submit";
    public final static String DEFAULT_BUTTON_NAME = "_eventId";
    public final static String DEFAULT_BUTTON_CSS_CLASS = " btn ";
    public final static String PRIMARY_BUTTON_CSS_CLASS = " btn btn-primary ";
    public final static String NEW_WINDOW_SCRIPT = " newWindow(this);";
    public final static String SELF_WINDOW_SCRIPT = " thisWindow(this);";

    private String event;
    private String valueKey;
    private String type;
    private Boolean primary = Boolean.FALSE;
    private Boolean newWindow = Boolean.FALSE;

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write(MessageBundleUtil.getMessage(getValueKey()));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    protected void writeValue(TagWriter tagWriter) throws JspException {
        tagWriter.writeAttribute("value", getEvent());
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getValue() {
        return getEvent();
    }

    @Override
    protected String getCssClass() {
        String cssClass = super.getCssClass();
        if (StringUtils.isEmpty(cssClass)) {
            cssClass = Constants.EMPTY_STRING;
        }
        if (getPrimary()) {
            return cssClass + PRIMARY_BUTTON_CSS_CLASS;
        } else {
            return cssClass + DEFAULT_BUTTON_CSS_CLASS;
        }
    }

    @Override
    public String getName() {
        return DEFAULT_BUTTON_NAME;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public String getType() {
        return (type == null) ? DEFAULT_BUTTON_TYPE : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Boolean getNewWindow() {
        return newWindow;
    }

    public void setNewWindow(Boolean newWindow) {
        this.newWindow = newWindow;
    }

    protected String getOnclick() {
        String onclick = super.getOnclick();
        if (StringUtils.isEmpty(onclick)) {
            onclick = Constants.EMPTY_STRING;
        }
        if (getNewWindow()) {
            return onclick + NEW_WINDOW_SCRIPT;
        } else {
            return onclick + SELF_WINDOW_SCRIPT;
        }
    }

    @Override
    public String getId() {
        return super.getId() != null ? super.getId() : DEFAULT_BUTTON_NAME + "_" + getEvent();
    }
}