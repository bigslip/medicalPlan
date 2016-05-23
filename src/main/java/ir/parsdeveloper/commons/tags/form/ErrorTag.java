package ir.parsdeveloper.commons.tags.form;

import org.springframework.web.servlet.tags.form.ErrorsTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author hadi tayebi
 */
public class ErrorTag extends ErrorsTag {
    public void renderTag(PageContext pageContext, TagWriter tagWriter) throws JspException {
        setPageContext(pageContext);
        if (getPath() == null) {
            this.setPath("*");
        }
        doStartTag();
        renderDefaultContent(tagWriter);
    }
}
