package ir.parsdeveloper.web.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author hadi tayebi
 */
@Configuration
//@Profile(Constants.PRODUCT_PROFILE)
public class ReportViews {

    DataSource dataSource;
  /*  @Bean()
    public JasperReportsPdfView testReport() {
        JasperReportsPdfView report = new JasperReportsPdfView();
        report.setUrl("/reports/test-report.jrxml");
        report.setJdbcDataSource(dataSource);
        return report;
    }*/

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
