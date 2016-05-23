package ir.parsdeveloper.commons.tags.table;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.tags.utils.HtmlAttributeMap;
import ir.parsdeveloper.commons.tags.utils.MultipleHtmlAttribute;
import ir.parsdeveloper.commons.tags.utils.TagConstants;
import ir.parsdeveloper.commons.tags.utils.TagUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author hadi tayebi
 */
public class HeaderCell {

    /**
     * Map containing the html tags attributes for cells (td).
     */
    private HtmlAttributeMap htmlAttributes;

    /**
     * Map containing the html tags attributes for header cells (td).
     */
    private HtmlAttributeMap headerAttributes;


    /**
     * param name used in adding a link.
     */
    private String paramName;

    /**
     * property of the object where to get the param value from.
     */
    private String paramProperty;

    private String title;
    private String titleKey;


    /**
     * column number.
     */
    private int columnNumber;


    /**
     * property name to look up in the bean.
     */
    private String beanPropertyName;

    /**
     * show null values?
     */
    private boolean showNulls;

    /**
     * max length of cell content.
     */
    private int maxLength;

    /**
     * max number of words for cell content.
     */
    private int maxWords;

    /**
     * group the column?
     */
    private int group = -1;


    /**
     * getter for the grouping index.
     *
     * @return 0 if the column is not grouped or the grouping order
     */
    public int getGroup() {
        return this.group;
    }

    /**
     * setter for the grouping index.
     *
     * @param groupingOrder int grouping order (>0)
     */
    public void setGroup(int groupingOrder) {
        this.group = groupingOrder;
    }

    /**
     * getter for the max number of characters to display in the column.
     *
     * @return int number of characters to display in the column
     */
    public int getMaxLength() {
        return this.maxLength;
    }

    /**
     * setter for the max number of characters to display in the column.
     *
     * @param numOfChars number of characters to display in the column
     */
    public void setMaxLength(int numOfChars) {
        this.maxLength = numOfChars;
    }

    /**
     * getter for the max number of words to display in the column.
     *
     * @return int number of words to display in the column
     */
    public int getMaxWords() {
        return this.maxWords;
    }

    /**
     * setter for the max number of words to display in the column.
     *
     * @param numOfWords number of words to display in the column
     */
    public void setMaxWords(int numOfWords) {
        this.maxWords = numOfWords;
    }

    /**
     * Should null be displayed?
     *
     * @return true null will be displayed in cell content
     */
    public boolean getShowNulls() {
        return this.showNulls;
    }

    /**
     * Enable or disable displaying of null values.
     *
     * @param outputNulls boolean true if null should be displayed
     */
    public void setShowNulls(boolean outputNulls) {
        this.showNulls = outputNulls;
    }

    /**
     * Getter for the name of the property to look up in the bean.
     *
     * @return String name of the property to look up in the bean
     */
    public String getBeanPropertyName() {
        return this.beanPropertyName;
    }

    /**
     * Setter for the name of the property to look up in the bean.
     *
     * @param propertyName - name of the property to look up in the bean
     */
    public void setBeanPropertyName(String propertyName) {
        this.beanPropertyName = propertyName;
    }


    /**
     * Getter for the column number.
     *
     * @return int column number
     */
    public int getColumnNumber() {
        return this.columnNumber;
    }

    /**
     * Setter for the column number.
     *
     * @param number - int column number
     */
    public void setColumnNumber(int number) {
        this.columnNumber = number;
    }


    /**
     * Gets the column title.
     *
     * @return the column title. If no title is specified the capitalized bean property name is returned
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        } else if (this.beanPropertyName != null) {
            return StringUtils.capitalize(this.beanPropertyName);
        }

        return Constants.EMPTY_STRING;
    }

    /**
     * Setter for the column title.
     *
     * @param value - the column title
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Returns the HtmlAttributeMap containg all the html attributes for the <strong>td </strong> tags.
     *
     * @return HtmlAttributeMap with td attributes
     */
    public HtmlAttributeMap getHtmlAttributes() {
        return this.htmlAttributes;
    }

    /**
     * Sets the HtmlAttributeMap containg all the html attributes for the <strong>td </strong> tags.
     *
     * @param attributes HtmlAttributeMap
     */
    public void setHtmlAttributes(HtmlAttributeMap attributes) {
        this.htmlAttributes = attributes;
    }

    /**
     * returns the HtmlAttributeMap containg all the html attributes for the <strong>th </strong> tags.
     *
     * @return HtmlAttributeMap with th attributes
     */
    public HtmlAttributeMap getHeaderAttributes() {
        return this.headerAttributes;
    }

    /**
     * Sets the HtmlAttributeMap containg all the html attributes for the <strong>th </strong> tags.
     *
     * @param attributes HtmlAttributeMap
     */
    public void setHeaderAttributes(HtmlAttributeMap attributes) {
        this.headerAttributes = attributes;
    }

    /**
     * Adds a css class to the html "class" attribute.
     *
     * @param cssClass String
     */
    public void addHeaderClass(String cssClass) {
        // null safe
        if (StringUtils.isBlank(cssClass)) {
            return;
        }

        // if headerAttributes has not been set, instantiates a new map
        if (headerAttributes == null) {
            headerAttributes = new HtmlAttributeMap();
        }

        Object classAttributes = this.headerAttributes.get(TagConstants.ATTRIBUTE_CLASS);

        // handle multiple values
        if (classAttributes == null) {
            this.headerAttributes.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(cssClass));
        } else {
            ((MultipleHtmlAttribute) classAttributes).addAttributeValue(cssClass);
        }
    }

    /**
     * return the open tags for a column header (th).
     *
     * @return String &lt;th&gt; tags with attributes
     */
    public String getHeaderOpenTag() {
        return TagUtils.createOpenTagString(TagConstants.TAGNAME_COLUMN_HEADER, this.headerAttributes);
    }

    /**
     * return the closing tags for a cell (td).
     *
     * @return String &lt;/td&gt;
     */
    public String getCloseTag() {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE;
    }

    /**
     * return the closing tags for a column header (th).
     *
     * @return String &lt;/th&gt;
     */
    public String getHeaderCloseTag() {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN_HEADER + TagConstants.TAG_CLOSE;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String name) {
        this.paramName = name;
    }

    public String getParamProperty() {
        return this.paramProperty;
    }

    public void setParamProperty(String property) {
        this.paramProperty = property;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public String render() {
        StringBuilder tag = new StringBuilder();
        tag.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_COLUMN_HEADER);
        tag.append(this.getHeaderAttributes().toString());
        tag.append(TagConstants.TAG_CLOSE);
        tag.append(this.getTitleKey());
        tag.append(TagConstants.TAG_TH_CLOSE);
        return tag.toString();
    }
}
