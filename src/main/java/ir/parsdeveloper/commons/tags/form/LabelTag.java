package ir.parsdeveloper.commons.tags.form;


import ir.parsdeveloper.commons.tags.utils.TagConstants;
import ir.parsdeveloper.commons.tags.utils.TagUtils;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author hadi tayebi
 */

public class LabelTag extends org.springframework.web.servlet.tags.form.LabelTag {

    public final static String DEFAULT_LABEL_CSS_CLASS = " control-label ";
    private String valueKey;
    private Boolean required = Boolean.FALSE;
    private TagWriter tagWriter;

    public LabelTag() {
    }

    public LabelTag(String path, String forId, String labelKey, Boolean required) {
        setPath(path);
        setFor(forId);
        setValueKey(labelKey);
        setRequired(required);
    }

    public void renderTag(PageContext pageContext, TagWriter tagWriter) throws JspException {
        setPageContext(pageContext);
        Integer current_rowCol = (Integer) pageContext.findAttribute("current_rowCol");
        if (current_rowCol == null) {
            current_rowCol = 2;
        }
        tagWriter.startTag(TagConstants.DIV_TAG_NAME);// open div tag , write <div
        tagWriter.writeAttribute(CLASS_ATTRIBUTE, TagUtils.divCssClass(current_rowCol, true));

        tagWriter.forceBlock(); //block div tag , write >

        doStartTag();
        writeTagContent(tagWriter);
        doEndTag();
        tagWriter.endTag();// close div tag , write </div>
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        super.writeTagContent(tagWriter);
        this.tagWriter = tagWriter;
      /*  if (getRequired()) {
            tagWriter.appendValue(TagConstants.REQUIRED_STAR);
        }*/
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        tagWriter.appendValue(MessageBundleUtil.getMessage(getValueKey()));
        return super.doEndTag();
    }

    protected String getDivCssClass() {
        return "";
    }

    @Override
    protected String getCssClass() {
        return (super.getCssClass() == null) ? DEFAULT_LABEL_CSS_CLASS : super.getCssClass() + DEFAULT_LABEL_CSS_CLASS;
    }


    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
