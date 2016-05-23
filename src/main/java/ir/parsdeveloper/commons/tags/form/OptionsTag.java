package ir.parsdeveloper.commons.tags.form;

import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;

/**
 * @author hadi tayebi
 */
public class OptionsTag extends org.springframework.web.servlet.tags.form.OptionsTag {
    boolean bypassOptional;

    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        if (!bypassOptional) {
            tagWriter.startTag("option");
            tagWriter.writeAttribute("value", "-1");
            tagWriter.appendValue(MessageBundleUtil.getMessage("info.please_select_item"));
            tagWriter.endTag();
        }
        return super.writeTagContent(tagWriter);
    }

    public boolean isBypassOptional() {
        return bypassOptional;
    }

    public void setBypassOptional(boolean bypassOptional) {
        this.bypassOptional = bypassOptional;
    }
}
