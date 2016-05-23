package ir.parsdeveloper.commons.tags.form;

import ir.parsdeveloper.commons.tags.utils.TagConstants;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * @author hadi tayebi
 */
public class TextareaTag extends org.springframework.web.servlet.tags.form.TextareaTag {
    public final static String DEFAULT_TEXT_AREA_CSS_CLASS = " form-control ";
    private final static String DEFAULT_ROWS = "3";
    private final static String DEFAULT_COLS = "30";
    private String labelKey;
    private Boolean required = false;
    private Integer colspan;


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
        JspWriter writer = pageContext.getOut();
        try {
            writeLabel(tagWriter);
            writer.println(TagConstants.TAG_TD_OPEN);
            int result = super.writeTagContent(tagWriter);
            writer.println(TagConstants.TAG_TD_CLOSE);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SKIP_BODY;
        }
    }

    protected void writeDefaultAttributes(TagWriter tagWriter) throws JspException {
        super.writeDefaultAttributes(tagWriter);
        writeOptionalAttribute(tagWriter, TagConstants.DATA_REQUIRED_ATTRIBUTE, String.valueOf(getRequired()));
        writeOptionalAttribute(tagWriter, TagConstants.REQUIRED_ATTRIBUTE, String.valueOf(getRequired()));
    }

    @Override
    protected String getCssClass() {
        return (super.getCssClass() == null) ? DEFAULT_TEXT_AREA_CSS_CLASS : super.getCssClass() + DEFAULT_TEXT_AREA_CSS_CLASS;
    }

    protected String getRows() {
        return (super.getRows() == null) ? DEFAULT_ROWS : super.getRows();
    }

    protected String getCols() {
        return (super.getCols() == null) ? DEFAULT_COLS : super.getCols();

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

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }
}
