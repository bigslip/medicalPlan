package ir.parsdeveloper.commons.tags.table;

/**
 * @author hadi tayebi
 *         Date: 2/23/14
 */
public class EditColumnTag extends ColumnTag {
    protected final static String DEFAULT_EVENT_ID = "edit";
    protected final static String DEFAULT_EVENT_ONCLICK = "setSelectedRow";
    private final static String COLUMN_TITLE_KEY = "lbl.core.btn.edit";
    protected String eventId;
    protected String onclick;

    protected String getCellValue(DataTableTag dataTableTag) {
        String _eventId = DEFAULT_EVENT_ID;
        String _onclick = DEFAULT_EVENT_ONCLICK;

        if (getEventId() != null) {
            _eventId = getEventId();
        }
        if (getOnclick() != null) {
            _onclick = getOnclick();
        }

        StringBuilder tag = new StringBuilder("<button ");
        if (getCssStyle() != null) {
            tag.append(" style='").append(getCssStyle()).append("'");
        }
        tag.append(" class='btn editCell ");
        if (getCssClass() != null) {
            tag.append(getCssClass());
        }
        tag.append("' name='_eventId' value='");
        tag.append(_eventId);
        tag.append("' onclick='return ").append(_onclick).append("(").append(dataTableTag.getRowNumber()).append(")'");
        tag.append("></button>");
        return tag.toString();
    }

    public String getTitleKey() {
        return COLUMN_TITLE_KEY;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }
}
