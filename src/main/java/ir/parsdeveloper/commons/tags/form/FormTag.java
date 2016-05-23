package ir.parsdeveloper.commons.tags.form;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.tags.utils.TagConstants;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

/**
 * @author hadi tayebi
 */
public class FormTag extends org.springframework.web.servlet.tags.form.FormTag {
    protected final static String DEFAULT_FORM_CSS_CLASS = " form-horizontal form-bordered ";
    private String caption;
    private TagWriter tagWriter;

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        try {
            this.tagWriter = tagWriter;
            int superResult = super.writeTagContent(tagWriter);
            tagWriter.startTag(TagConstants.DIV_TAG_NAME);//open div tag , write <div

            ErrorTag errorsTag = new ErrorTag();
            errorsTag.renderTag(pageContext, tagWriter);

            tagWriter.forceBlock();//close div, write >
            return superResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new JspTagException(ex.toString());
        }
    }

    @Override
    public int doEndTag() throws JspException {
        try {

            tagWriter.endTag();
            return super.doEndTag();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return SKIP_BODY;
        }
    }

    protected String resolveModelAttribute() {
        return (String) pageContext.findAttribute(Constants.FORM_OBJECT_NAME);
    }

    protected String getAction() {
        final String FLOW_EXECUTION_URL = "flowExecutionUrl";
        String formAction = (String) pageContext.getAttribute(FLOW_EXECUTION_URL, PageContext.REQUEST_SCOPE);
        return (formAction != null && !formAction.isEmpty()) ? formAction : super.getAction();
    }

    @Override
    protected String getCssClass() {
        return (super.getCssClass() == null) ? DEFAULT_FORM_CSS_CLASS : super.getCssClass() + DEFAULT_FORM_CSS_CLASS;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
