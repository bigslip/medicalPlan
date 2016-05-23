package ir.parsdeveloper.commons.tags.table;

/**
 * @author hadi tayebi
 */
public class SelectRowTag extends ActionColumnTag {

    protected final static String DEFAULT_EVENT_ONCLICK = "setSelectedRow";
    protected final static String NEW_WINDOW_SCRIPT = "newWindow(this);";


    protected String getCellValue(DataTableTag dataTableTag) {
        String _eventId = getEventId();
        String _onclick = DEFAULT_EVENT_ONCLICK;

        if (getEventId() != null) {
            _eventId = getEventId();
        }
        if (getOnclick() != null) {
            _onclick = getOnclick();
        }
        if (getCssClass() == null) {
            setCssClass("selectRow");
        }
        StringBuilder tag = new StringBuilder();
        tag.append("<button class='");
        tag.append(getCssClass()).append("' name='_eventId' value='");
        tag.append(_eventId);
        tag.append("' onclick='");
        if (getNewWindow()) {
            tag.append(NEW_WINDOW_SCRIPT);
        }
        tag.append("return ").append(_onclick).append("(").append(dataTableTag.getRowNumber()).append(");'");
        tag.append("></button>");
        return tag.toString();
    }

}