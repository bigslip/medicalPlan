package ir.parsdeveloper.commons.tags.form;

import ir.parsdeveloper.commons.convert.DateTimeEditor;
import ir.parsdeveloper.commons.tags.utils.TagConstants;
import ir.parsdeveloper.commons.tags.utils.TagUtils;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import java.beans.PropertyEditor;
import java.io.IOException;

/**
 * @author hadi tayebi
 */
public class InputTag extends org.springframework.web.servlet.tags.form.InputTag {

    public final static String DEFAULT_INPUT_CSS_CLASS = " form-control ";
    public final static String DEFAULT_LANG_ATTRIBUTE = "en";
    public final static String DATA_NUMBER_ATTRIBUTE = "data-number";

    private static DateTimeEditor dateTimeEditor = new DateTimeEditor();


    private String labelKey;
    private String suffixKey;
    private String suffix;
    private String placeholder;
    private boolean required = true;
    private boolean numerical;
    private Integer colSize;
    private String type = "text";

    protected void writeLabel(TagWriter tagWriter) throws IOException, JspException {
        LabelTag labelTag = new LabelTag(getPath(), resolveId(), getLabelKey(), getRequired());
        labelTag.renderTag(pageContext, tagWriter);
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        try {
            if (getLabelKey() != null) {
                writeLabel(tagWriter);
            }
            Integer rowColumnCount = getRowColumnCount();

            tagWriter.startTag(TagConstants.DIV_TAG_NAME);
            tagWriter.writeAttribute(CLASS_ATTRIBUTE, TagUtils.divCssClass(rowColumnCount, false));

            int result = super.writeTagContent(tagWriter);
            tagWriter.endTag();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return EVAL_PAGE;
        }

    }


    protected void writeDefaultAttributes(TagWriter tagWriter) throws JspException {
        super.writeDefaultAttributes(tagWriter);
        writeOptionalAttribute(tagWriter, TagConstants.PLACEHOLDER_ATTRIBUTE, getPlaceholder() == null ? null : MessageBundleUtil.getMessage(getPlaceholder()));
        if (getRequired()) {
            writeOptionalAttribute(tagWriter, TagConstants.DATA_REQUIRED_ATTRIBUTE, "true");
            writeOptionalAttribute(tagWriter, TagConstants.REQUIRED_ATTRIBUTE, "true");
        }
        if (numerical) {
            writeOptionalAttribute(tagWriter, DATA_NUMBER_ATTRIBUTE, "true");
        }
    }

    protected int getRowColumnCount() {
        if (colSize != null) {
            return colSize;
        } else {
            Integer rowColumnCount = (Integer) pageContext.findAttribute("_rowColumnCount");
            if (rowColumnCount == null) {
                rowColumnCount = TagConstants.DEFAULT_ROW_COLUMN_COUNT;
            }
            return rowColumnCount;
        }
    }

    protected String getDivCssClass() {
        return (super.getCssClass() == null) ? DEFAULT_INPUT_CSS_CLASS : super.getCssClass() + DEFAULT_INPUT_CSS_CLASS;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    protected String getCssClass() {
        return (super.getCssClass() == null) ? DEFAULT_INPUT_CSS_CLASS : super.getCssClass() + DEFAULT_INPUT_CSS_CLASS;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getLang() {
        return super.getLang() == null ? DEFAULT_LANG_ATTRIBUTE : super.getLang();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getSuffixKey() {
        return suffixKey;
    }

    public void setSuffixKey(String suffixKey) {
        this.suffixKey = suffixKey;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean getNumerical() {
        return numerical;
    }

    public void setNumerical(boolean numerical) {
        this.numerical = numerical;
    }

    public Integer getColSize() {
        return colSize;
    }

    public void setColSize(Integer colSize) {
        this.colSize = colSize;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


//    protected PropertyEditor getPropertyEditor() throws JspException {
//        return getType().equals("date") ? dateTimeEditor : super.getPropertyEditor();
//    }
}
