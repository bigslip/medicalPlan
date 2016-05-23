package ir.parsdeveloper.commons.tags.form;

import ir.parsdeveloper.commons.tags.utils.TagConstants;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * @author hadi tayebi
 */
public class SelectTag extends org.springframework.web.servlet.tags.form.SelectTag {
    public final static String DEFAULT_SELECT_CSS_CLASS = " form-control ";
    private String labelKey;
    private boolean required = false;
    private boolean noTdTag;

    protected void writeLabel(TagWriter tagWriter) throws IOException, JspException {

        if (getLabelKey() != null) {
            pageContext.getOut().println(TagConstants.TAG_TD_OPEN);

            LabelTag labelTag = new LabelTag(getPath(), resolveId(), getLabelKey(), getRequired());
            labelTag.renderTag(pageContext, tagWriter);

            pageContext.getOut().println(TagConstants.TAG_TD_CLOSE);
        }
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        if (getItems() instanceof String) {
            setItems(pageContext.findAttribute((String) getItems()));
        }

        JspWriter writer = pageContext.getOut();
        try {
            writeLabel(tagWriter);
            if (!noTdTag) {
                writer.println(TagConstants.TAG_TD_OPEN);
            }
            return super.writeTagContent(tagWriter);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SKIP_BODY;
        }
    }

    protected void writeDefaultAttributes(TagWriter tagWriter) throws JspException {
        super.writeDefaultAttributes(tagWriter);
        if (getRequired()) {
            writeOptionalAttribute(tagWriter, TagConstants.DATA_REQUIRED_ATTRIBUTE, String.valueOf(getRequired()));
            writeOptionalAttribute(tagWriter, TagConstants.REQUIRED_ATTRIBUTE, String.valueOf(getRequired()));
        }
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            int result = super.doEndTag();
            if (!noTdTag) {
                pageContext.getOut().println(TagConstants.TAG_TD_CLOSE);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SKIP_BODY;
        }
    }

    @Override
    protected String getCssClass() {
        return (super.getCssClass() == null) ? DEFAULT_SELECT_CSS_CLASS : super.getCssClass() + DEFAULT_SELECT_CSS_CLASS;
    }


    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getNoTdTag() {
        return noTdTag;
    }

    public void setNoTdTag(boolean noTdTag) {
        this.noTdTag = noTdTag;
    }


}
