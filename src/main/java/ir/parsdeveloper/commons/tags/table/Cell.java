package ir.parsdeveloper.commons.tags.table;

import ir.parsdeveloper.commons.tags.utils.HtmlAttributeMap;
import ir.parsdeveloper.commons.tags.utils.TagConstants;

/**
 * @author hadi tayebi
 */

public class Cell implements Cloneable {

    /**
     * empty cell object. Use as placeholder for empty cell to avoid useless object creation.
     */
    public static final Cell EMPTY_CELL = new Cell();

    /**
     * content of the cell.
     */
    private String value;

    /**
     * Per row html attributes (style, class).
     */
    private HtmlAttributeMap attributes;

    /**
     * Creates a new empty cell. This should never be done, use EMPTY_CELL instead.
     */
    private Cell() {
        super();
    }


    public Cell(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public HtmlAttributeMap getPerRowAttributes() {
        return this.attributes;
    }

    public void setPerRowAttributes(HtmlAttributeMap perRowValues) {
        this.attributes = perRowValues;
    }

    public String toString() {
        StringBuilder tag = new StringBuilder(TagConstants.TAG_TD_OPEN);
        tag.append(getValue());
        tag.append(TagConstants.TAG_TD_CLOSE);
        return tag.toString();
    }

}
