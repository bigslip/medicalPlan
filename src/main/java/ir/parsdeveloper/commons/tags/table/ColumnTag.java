package ir.parsdeveloper.commons.tags.table;

import ir.parsdeveloper.commons.convert.DateTimeEditor;
import ir.parsdeveloper.commons.tags.utils.HtmlAttributeMap;
import ir.parsdeveloper.commons.tags.utils.MultipleHtmlAttribute;
import ir.parsdeveloper.commons.tags.utils.TagConstants;
import ir.parsdeveloper.commons.tags.utils.TagUtils;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import ir.parsdeveloper.commons.utils.ReflectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author hadi tayebi
 */
public class ColumnTag extends BodyTagSupport {

    public final static String COLUMN_INDEX = "ir.parsdeveloper.commons.tags.table.ColumnTag.COLUMN_INDEX";
    static DecimalFormat numberFormat = new DecimalFormat("#,##0");
    static DateTimeEditor dateTimeEditor = new DateTimeEditor();
    private static Log logger = LogFactory.getLog(ColumnTag.class);
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();
    private HtmlAttributeMap headerAttributeMap = new HtmlAttributeMap();
    private String property;
    private String type;
    private Boolean currency = Boolean.FALSE;
    private String title;
    private boolean autolink;
    /**
     * Automatically escape column content for html and xml media.
     */
    private boolean escapeXml;
    /**
     * A MessageFormat patter that will be used to decorate objects in the column. Can be used as a "shortcut" for
     * simple column decorations.
     */
    private String format;
    private String DateFormat;
    private String headerCssStyle;
    private String headerCssClass;
    private String cssStyle;
    private String cssClass;
    /**
     * If this attribute is provided, then the column's displayed is limited to this number of characters. An elipse
     * (...) is appended to the end if this column is linked, and the user can mouseover the elipse to get the full
     * text. (optional)
     */
    private int maxLength;
    /**
     * If this attribute is provided, then the column's displayed is limited to this number of words. An elipse (...) is
     * appended to the end if this column is linked, and the user can mouseover the elipse to get the full text.
     * (optional)
     */
    private int maxWords;
    /**
     * Property in a resource bundle to be used as the title for the column.
     */
    private String titleKey;
    /**
     * Static value for this cell, equivalent to column body.
     */
    private Object value;

    protected static void resetColumnIndex(PageContext pageContext) {
        pageContext.setAttribute(ColumnTag.COLUMN_INDEX, 0, PageContext.PAGE_SCOPE);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrency(Boolean currency) {
        this.currency = currency;
    }

    public String getDateFormat() {
        return DateFormat;
    }

    public void setDateFormat(String dateFormat) {
        DateFormat = dateFormat;
    }

    public void setProperty(String value) {
        this.property = value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getHeaderCssClass() {
        return headerCssClass;
    }

    public void setHeaderCssClass(String headerCssClass) {
        this.headerCssClass = headerCssClass;
    }

    public String getHeaderCssStyle() {
        return headerCssStyle;
    }

    public void setHeaderCssStyle(String headerCssStyle) {
        this.headerCssStyle = headerCssStyle;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     * setter for the "title" tags attribute.
     *
     * @param value attribute value
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * setter for the "format" tags attribute.
     *
     * @param value attribute value
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * setter for the "autolink" tags attribute.
     *
     * @param value attribute value
     */
    public void setAutolink(boolean value) {
        this.autolink = value;
    }

    /**
     * setter for the "escapeXml" tags attribute.
     *
     * @param value attribute value
     */
    public void setEscapeXml(boolean value) {
        this.escapeXml = value;
    }

    /**
     * setter for the "maxLength" tags attribute.
     *
     * @param value attribute value
     */
    public void setMaxLength(int value) {
        this.maxLength = value;
    }

    /**
     * setter for the "maxWords" tags attribute.
     *
     * @param value attribute value
     */
    public void setMaxWords(int value) {
        this.maxWords = value;
    }

    /**
     * setter for the "style" tags attribute.
     *
     * @param value attribute value
     */
    public void setStyle(String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
    }

    /**
     * setter for the "class" tags attribute.
     *
     * @param value attribute value
     */
    public void setClass(String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        try {
            DataTableTag dataTableTag = TagUtils.getParent(this, DataTableTag.class);
            if (dataTableTag == null) {
                throw new JspException("columnTag parent is null");
            }
            // If the list is empty, do not execute the body; may result in NPE
            if (dataTableTag.isEmpty()) {
                return SKIP_BODY;
            }
            return super.doStartTag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int doEndTag() throws JspException {
        try {
            DataTableTag dataTableTag = (DataTableTag) getParent();

            if (dataTableTag.isFirstIteration()) {
                HtmlAttributeMap headerAttribute = new HtmlAttributeMap();
                headerAttribute.put(TagConstants.CLASS_ATTRIBUTE, getHeaderCssClass());
                headerAttribute.put(TagConstants.ATTRIBUTE_STYLE, getHeaderCssStyle());
                HeaderCell headerCell = new HeaderCell();
                headerCell.setHeaderAttributes(headerAttribute);
                headerCell.setTitleKey(MessageBundleUtil.getMessage(getTitleKey()));
                dataTableTag.addColumn(headerCell);
                return super.doEndTag();
            }

            if (dataTableTag.isEmpty()) {
                return super.doEndTag();
            }
            Integer columnIndex = (Integer) pageContext.getAttribute(COLUMN_INDEX, PageContext.PAGE_SCOPE);
            Cell cell = new Cell(getCellValue(dataTableTag));
            dataTableTag.addCell(cell);
            pageContext.setAttribute(COLUMN_INDEX, ++columnIndex, PageContext.PAGE_SCOPE);
            return super.doEndTag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void release() {
        super.release();
        this.attributeMap.clear();
        this.autolink = false;
        this.headerAttributeMap.clear();
        this.maxLength = 0;
        this.maxWords = 0;
        this.property = null;
        this.title = null;
        this.titleKey = null;
        this.escapeXml = false;
        this.format = null;
        this.value = null;

    }

    protected String getCellValue(DataTableTag dataTableTag) throws Exception {
        String cellValue = "";
        if (this.property != null) {
            Object currentRowObject = dataTableTag.getCurrentRowObject();
            Object beanProperty = ReflectionUtils.getProperty(currentRowObject, this.property);

            if (beanProperty instanceof Date) {
                cellValue = TagUtils.getDisplayString(beanProperty, dateTimeEditor);
            } else if (beanProperty instanceof Number && currency) {
                cellValue = numberFormat.format(beanProperty); //TagUtils.getDisplayString(beanProperty);
            } else {
                cellValue = TagUtils.getDisplayString(beanProperty);
            }
        } else if (this.value != null) {
            if (this.value instanceof Date) {
                cellValue = TagUtils.getDisplayString(this.value, dateTimeEditor);
            } else {
                cellValue = TagUtils.getDisplayString(this.value);
            }
        } else if (this.bodyContent != null) {
            cellValue = this.bodyContent.getString();
        }
        return cellValue;
    }

    public String getTitleKey() {
        return titleKey;
    }

    /**
     * setter for the "titleKey" tags attribute.
     *
     * @param value property name
     */
    public void setTitleKey(String value) {
        this.titleKey = value;
    }
}
