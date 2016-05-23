package ir.parsdeveloper.commons.tags.form;

import ir.parsdeveloper.commons.tags.utils.TagConstants;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * @author hadi tayebi
 */
public class CheckboxTag extends org.springframework.web.servlet.tags.form.CheckboxTag {
    private String labelKey;
    private Boolean required = false;

    protected void writeLabel(TagWriter tagWriter) throws IOException, JspException {

        if (getLabelKey() != null) {
            pageContext.getOut().println(TagConstants.TAG_TD_OPEN);

            LabelTag labelTag = new LabelTag(getPath(), resolveId(), getLabelKey(), getRequired());
            labelTag.renderTag(pageContext, tagWriter);

            pageContext.getOut().println(TagConstants.TAG_TD_CLOSE);
        }
    }

    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writeLabel(tagWriter);
            writer.println(TagConstants.TAG_TD_OPEN);
            int result = super.writeTagContent(tagWriter);
            writer.println(TagConstants.TAG_TD_CLOSE);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return EVAL_PAGE;
        }

    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
