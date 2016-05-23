package ir.parsdeveloper.commons.tags.table;

import ir.parsdeveloper.commons.tags.utils.HtmlAttributeMap;
import ir.parsdeveloper.commons.tags.utils.TagConstants;
import ir.parsdeveloper.commons.tags.utils.TagUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hadi tayebi
 */

public class Column {

    HtmlAttributeMap headerAttributes = new HtmlAttributeMap();
    HtmlAttributeMap columnAttributes = new HtmlAttributeMap();
    private String titleKey;
    //private String beanPropertyName;
    /**
     * copy of the attribute map from the header cell. Needed to change attributes (title) in this cell only
     */
    private HtmlAttributeMap htmlAttributes;

    /**
     * contains the evaluated body value. Filled in getOpenTag.
     */
    private String stringValue;

    /**
     * Cell.
     */
    private List<Cell> cells;


    public Column(String titleKey, HtmlAttributeMap headerAttributes) {
        this.titleKey = titleKey;
        this.headerAttributes = headerAttributes;
        cells = new ArrayList<Cell>(10);
        //this.beanPropertyName = beanPropertyName;

    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public String getColumnOpenTag() {
        return TagUtils.createOpenTagString(TagConstants.TAGNAME_ROW, columnAttributes);
    }

    public String getHeaderOpenTag() {
        return TagUtils.createOpenTagString(TagConstants.TAGNAME_COLUMN_HEADER, this.headerAttributes);
    }

    public String getHeaderCloseTag() {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN_HEADER + TagConstants.TAG_CLOSE;
    }

    /**
     * Generates the cell open tags.
     *
     * @return String td open tags
     */
   /* public String getOpenTag() {
        HtmlAttributeMap rowAttributes = cell.getPerRowAttributes();

        HtmlAttributeMap atts = htmlAttributes;
        if (rowAttributes != null) {
            atts = (HtmlAttributeMap) atts.clone();
            atts.putAll(rowAttributes);
        }
        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN, atts);
    }*/

    /**
     * Initialize the cell value.
     *
     * @throws ObjectLookupException for errors in bean property lookup
     * @throws ObjectLookupException
     */
/*    public void initialize() throws ObjectLookupException {
        if (this.stringValue == null) {
            this.stringValue = createChoppedAndLinkedValue();
        }
    }*/

    /**
     * Generates the cell close tags (&lt;/td>).
     *
     * @return String td closing tags
     */
   /* public String getCloseTag() {
        this.stringValue = null;
        return this.header.getCloseTag();
    }*/

 /*   *//**
     * Calculates the cell content, cropping or linking the value as needed.
     *
     * @return String
     * @throws ObjectLookupException for errors in bean property lookup
     *//*
    public String createChoppedAndLinkedValue() throws ObjectLookupException {

        String fullValue = ObjectUtils.toString(getValue(true));
        String choppedValue;

        // trim the string if a maxLength or maxWords is defined
        if (this.header.getMaxLength() > 0) {
            choppedValue = HtmlTagUtil.abbreviateHtmlString(fullValue, this.header.getMaxLength(), false);
        } else if (this.header.getMaxWords() > 0) {
            choppedValue = HtmlTagUtil.abbreviateHtmlString(fullValue, this.header.getMaxWords(), true);
        } else {
            choppedValue = fullValue;
        }

        // chopped content? add the full content to the column "title" attribute
        // note, simply checking that length is less than before can't be enough due to the "..." added if the string is
        // cropped
        if (!ObjectUtils.equals(fullValue, choppedValue)) {
            // clone the attribute map, don't want to add title to all the columns
            this.htmlAttributes = (HtmlAttributeMap) this.htmlAttributes.clone();
            // add title
            this.htmlAttributes.put(TagConstants.ATTRIBUTE_TITLE, HtmlTagUtil.stripHTMLTags(fullValue));
        }


        return choppedValue;
    }

*/

    /**
     * get the final value to be displayed in the table. This method can only be called after initialize(), where the
     * content is evaluated
     *
     * @return String final value to be displayed in the table
     */
    public String getChoppedAndLinkedValue() {
        return this.stringValue;
    }


}
