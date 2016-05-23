package ir.parsdeveloper.commons.tags.table;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.tags.utils.HtmlAttributeMap;
import ir.parsdeveloper.commons.tags.utils.TagConstants;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import ir.parsdeveloper.commons.utils.StringUtils;
import ir.parsdeveloper.model.entity.core.BaseModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author hadi tayebi
 */
public class DataTableTag extends BodyTagSupport {

    private static final String DEFAULT_DIR = "rtl";
    private static final String DEFAULT_CSS_CLASS = " table table-bordered datatable dataTable ";
    private static final String DEFAULT_CSS_STYLE = " direction: rtl;";

    private static Log logger = LogFactory.getLog(DataTableTag.class);
    protected Collection list;
    List<Row> rows;
    JspWriter jspWriter;
    private int rowNumber = 0;
    private String name;
    private Row currentRow;
    private List<HeaderCell> headerCells;
    private BaseModel currentRowObject;
    private String var;
    private Iterator<BaseModel> tableIterator;
    private String footer;
    private Boolean lastIteration;
    private Boolean bypassSelectCell = false;
    private Boolean bypassRowNumber = false;
    private String uid;
    private String caption;
    private String cssStyle;

    public String getCssStyle() {
        return (cssStyle != null && cssStyle.length() > 0) ? cssStyle : DEFAULT_CSS_STYLE;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String string) {
        this.caption = string;
    }

    public String getFooter() {
        return this.footer;
    }

    public void setFooter(String string) {
        this.footer = string;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    protected boolean isEmpty() {
        return this.currentRowObject == null;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Collection getList() {
        return list;
    }

    public void setList(Collection list) {
        this.list = list;
    }

    protected boolean isFirstIteration() {
        return this.rowNumber == 0;
    }


    private Collection getListOfValues() {
        if (getList() != null) {
            return getList();
        }
        String formObjectName = (String) pageContext.getAttribute(Constants.FORM_OBJECT_NAME, PageContext.REQUEST_SCOPE);
        List entityList = (List) pageContext.getAttribute(formObjectName + "List", PageContext.REQUEST_SCOPE);

        if (entityList == null) {
            entityList = new ArrayList(0);
        }
        setList(entityList);
        return entityList;
    }

    public int doStartTag() throws JspException {
        jspWriter = pageContext.getOut();
        String formObjectName = (String) pageContext.getAttribute(Constants.FORM_OBJECT_NAME, PageContext.REQUEST_SCOPE);
        //initParameters();
        if (getUid() == null) {
            setUid(formObjectName);
        }
        if (getVar() == null) {
            setVar(formObjectName);
        }

        this.headerCells = new ArrayList<HeaderCell>(5);
        this.tableIterator = getListOfValues().iterator();
        this.rows = new ArrayList<Row>(getListOfValues().size());
        this.rowNumber = 0;
        this.currentRowObject = null;
        return EVAL_BODY_BUFFERED;
    }

    public int doAfterBody() {
        try {
            ColumnTag.resetColumnIndex(pageContext);
          /*  String rowContent = bodyContent.getString();
            bodyContent.clearBody();*/
            if (currentRow != null) {
                this.rows.add(currentRow);
            }

            if (this.tableIterator.hasNext()) {
                this.currentRowObject = this.tableIterator.next();
                currentRow = new Row();
                this.rowNumber++;
                //todo
                if (getVar() != null) {
                    if ((currentRowObject != null)) {
                        currentRow.setRowObject(currentRowObject);
                        this.pageContext.setAttribute(getVar(), currentRowObject);
                    } else {
                        // if row is null remove previous object
                        this.pageContext.removeAttribute(getVar());
                    }
                    // set the current row number into this.pageContext
                }
                this.pageContext.setAttribute(getUid() + TagConstants.DEFAULT_TABLE_ROWNUM_SUFFIX, this.rowNumber);
                this.lastIteration = !this.tableIterator.hasNext();
                return EVAL_BODY_AGAIN;
            }
            return SKIP_BODY;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Object getCurrentRowObject() {
        return this.currentRowObject;
    }

    public void addCell(Cell cell) {
        currentRow.addCell(cell);
    }

    public void addColumn(HeaderCell headerCell) {
        headerCells.add(headerCell);
    }

    public int doEndTag() throws JspException {
        try {
            writeTable();
            cleanUp();
            return EVAL_PAGE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * clean up instance variables, but not the ones representing tags attributes.
     */
    private void cleanUp() {
        // reset instance variables (non attributes)
        this.caption = null;
        this.footer = null;
        this.rowNumber = 0;
        this.tableIterator = null;
        this.currentRowObject = null;
        this.list = null;
        this.headerCells = null;
        this.rows = null;
        this.currentRow = null;
        this.pageContext.setAttribute(getUid() + TagConstants.DEFAULT_TABLE_ROWNUM_SUFFIX, null);

    }

    public void release() {
        super.release();
        this.name = null;
    }

    protected String getName() {
        return this.name;
    }

    void writeTable() throws Exception {

        openTableTag();
        writeCaption();
        writeTableHeader();
        writeTableBody();
          /* writePreBodyFooter();*/
        closeTableTag();

        writePagination();


    }

    private void writePagination() {
        // Integer totalPage = (Integer) pageContext.findAttribute("totalPage");
        Integer currentPage = (Integer) pageContext.findAttribute("currentPage");
        StringBuilder paginationTag = new StringBuilder("\n<input type='hidden' id='currentPage' name='currentPage' value='");
        paginationTag.append(currentPage).append("' />\n<div id='pagination' class='pagination-left col-md-6' >\n</div>\n");
        write(paginationTag.toString());
    }

    public void openTableTag() {
        HtmlAttributeMap tableAttributeMap = new HtmlAttributeMap();
        tableAttributeMap.put(TagConstants.CLASS_ATTRIBUTE, DEFAULT_CSS_CLASS);
        tableAttributeMap.put(TagConstants.ATTRIBUTE_STYLE, cssStyle, getCssStyle());
        tableAttributeMap.put(TagConstants.CELLPADDING_ATTRIBUTE, 0);
        tableAttributeMap.put(TagConstants.CELLSPACING_ATTRIBUTE, 0);
        tableAttributeMap.put(TagConstants.DIR_ATTRIBUTE, DEFAULT_DIR);

        if (this.getUid() != null && tableAttributeMap.get(TagConstants.ID_ATTRIBUTE) == null) {
            tableAttributeMap.put(TagConstants.ID_ATTRIBUTE, this.getUid());
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("<input type='hidden' id='").append(getUid()).append("_selected_rows' name='").append(getUid()).append("_selected_rows' value='-1'/>");
        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);

        buffer.append(tableAttributeMap.toString());
        buffer.append(TagConstants.TAG_CLOSE);
        write(buffer.toString());


    }

    void closeTableTag() {
        this.write(TagConstants.CLOSE_TABLE_TAG);
    }

    protected void writeCaption() {
        if (StringUtils.hasText(getCaption())) {
            this.write("<caption>" + getCaption() + "</caption>");
        }
    }

    protected void writeTableHeader() {
        StringBuilder header = new StringBuilder(TagConstants.TAG_THEAD_OPEN);
        header.append(TagConstants.TAG_TR_OPEN);
        if (this.rows != null && this.rows.size() > 0) {
            if (!getBypassSelectCell()) {
                header.append("\n<th><input id='").append(getUid());
                header.append("_selectAll' style='width:10px;' type='checkbox' onclick='selectAllItems(this,\"");
                header.append(getUid()).append("_checkbox\" )' /></th>");
            }
            if (!getBypassRowNumber()) {
                header.append("\n<th><span style='width:10px;'>").
                        append(MessageBundleUtil.getMessage("lbl.core.table.row")).append("</span></th>");
            }
        }

        for (HeaderCell cell : headerCells) {
            header.append(cell.render());
        }

        header.append(TagConstants.TAG_TR_CLOSE);
        header.append(TagConstants.TAG_THEAD_CLOSE);
        write(header.toString());
    }

    private void writeTableBody() throws Exception {
        StringBuilder tag = new StringBuilder();
        tag.append(TagConstants.TAG_TBODY_OPEN);
        int index = 0;
        if (this.rows != null && this.rows.size() > 0) {
            for (Row row : this.rows) {
                index++;
                BaseModel baseModel = (BaseModel) row.getRowObject();

                tag.append("\n<tr id='").append(baseModel.getId()).append("' ");
                tag.append(TagConstants.TAG_CLOSE);
                if (!getBypassSelectCell()) {
                    tag.append("\n<td><input type='checkbox'  name='").append(this.getUid())
                            .append("_checkbox' data-value='").append(index).append("' /></td>\n");
                }

                if (!getBypassRowNumber()) {
                    tag.append("\n<td><span>").append(index).append("</span></td>\n");
                }
                for (Cell cell : row.getCells()) {
                    tag.append(cell.toString());
                }


                tag.append(TagConstants.TAG_TR_CLOSE);
            }
        } else {
            tag.append(TagConstants.TAG_TR_OPEN);
            tag.append("<td colspan='20' class='emptyList'>").append(MessageBundleUtil.getMessage("errors.empty_list")).append("</td>");
            tag.append(TagConstants.TAG_TR_CLOSE);
        }
        tag.append(TagConstants.TAG_TBODY_CLOSE);
        this.write(tag.toString());
    }


    protected void writePreBodyFooter() {
        if (StringUtils.hasText(getFooter())) {
            this.write(TagConstants.TAG_TFOOTER_OPEN);
            this.write(getFooter());
            this.write(TagConstants.TAG_TFOOTER_CLOSE);
        }
    }

    public void write(String string) {
        if (string != null) {
            try {
                jspWriter.write(string);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Boolean getBypassSelectCell() {
        return bypassSelectCell;
    }

    public void setBypassSelectCell(Boolean bypassSelectCell) {
        this.bypassSelectCell = bypassSelectCell;
    }


    public Boolean getBypassRowNumber() {
        return bypassRowNumber;
    }

    public void setBypassRowNumber(Boolean bypassRowNumber) {
        this.bypassRowNumber = bypassRowNumber;
    }
}



