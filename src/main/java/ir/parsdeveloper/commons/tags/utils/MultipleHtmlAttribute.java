package ir.parsdeveloper.commons.tags.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author hadi tayebi
 */

public class MultipleHtmlAttribute implements Cloneable {

    /**
     * Sets containing splitted attribute values.
     */
    private Set<String> attributeSet;

    /**
     * Constructor for MultipleHtmlAttribute.
     *
     * @param attributeValue String
     */
    public MultipleHtmlAttribute(String attributeValue) {
        this.attributeSet = new LinkedHashSet<String>();
        addAllAttributesFromArray(StringUtils.split(attributeValue));
    }

    /**
     * Adds attributes from an array.
     *
     * @param attributes Object[] Array containing attributes
     */
    private void addAllAttributesFromArray(String[] attributes) {
        if (attributes == null) {
            return;
        }

        // number of attributes to add
        int length = attributes.length;

        // add all the splitted attributes
        for (String attribute : attributes) {

            // don't add if empty
            if (!StringUtils.isEmpty(attribute)) {
                this.attributeSet.add(attribute);
            }

        }
    }

    /**
     * Returns the list of attributes separated by a space.
     *
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        Iterator iterator = this.attributeSet.iterator();

        while (iterator.hasNext()) {
            // apend next value
            buffer.append(iterator.next());
            if (iterator.hasNext()) {
                // append a space if there are more
                buffer.append(' ');
            }
        }

        return buffer.toString();
    }

    /**
     * Adds a value to the attribute.
     *
     * @param attributeValue value to add to the attribute
     */
    public void addAttributeValue(String attributeValue) {
        // don't add if empty
        if (!StringUtils.isEmpty(attributeValue)) {
            this.attributeSet.add(attributeValue);
        }

    }

    /**
     * Return true if this MultipleHtmlValue doesn't store any value.
     *
     * @return <code>true</code> if this MultipleHtmlValue doesn't store any value
     */
    public boolean isEmpty() {
        return attributeSet.isEmpty();
    }

    /**
     * @see java.lang.Object#clone()
     */
    protected Object clone() throws CloneNotSupportedException {
        MultipleHtmlAttribute clone;

        try {
            clone = (MultipleHtmlAttribute) super.clone();
        } catch (CloneNotSupportedException e) {
            // should never happen
            throw new UnhandledException(e);
        }

        // copy attributes
        clone.addAllAttributesFromArray(this.attributeSet.toArray(new String[this.attributeSet.size()]));

        return clone;
    }

}
