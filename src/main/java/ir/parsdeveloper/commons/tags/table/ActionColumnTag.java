package ir.parsdeveloper.commons.tags.table;

/**
 * @author hadi tayebi
 */
public class ActionColumnTag extends ColumnTag {


    protected Boolean newWindow = Boolean.FALSE;
    protected String eventId;
    protected String onclick;


    protected String getCellValue(DataTableTag dataTableTag) {
        StringBuilder cellValue = new StringBuilder("<button class='deleteCell' name='_eventId' value='delete' onclick='setSelectedRow(");
        cellValue.append(dataTableTag.getRowNumber()).append(")'");
        cellValue.append("></button>");
        return cellValue.toString();
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

    public Boolean getNewWindow() {
        return newWindow;
    }

    public void setNewWindow(Boolean newWindow) {
        this.newWindow = newWindow;
    }


}