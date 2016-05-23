package ir.parsdeveloper.commons.tags.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hadi tayebi
 */
public class Row {
    List<Cell> cells;
    Object rowObject;

    public Row() {
        cells = new ArrayList<Cell>(10);
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Object getRowObject() {
        return rowObject;
    }

    public void setRowObject(Object rowObject) {
        this.rowObject = rowObject;
    }
}
