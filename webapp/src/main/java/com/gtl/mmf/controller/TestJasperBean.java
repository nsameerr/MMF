/**
 *
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.REPORT_JRXML_FILE_NAME;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gtl.mmf.report.AbstractReportBean;
import com.gtl.mmf.report.ReportsDataSource;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;

/**
 * @author 08237
 *
 */
@Component("testJasperBean")
@Scope("view")
public class TestJasperBean extends AbstractReportBean {
    private static final Logger LOGGER = Logger
			.getLogger("com.gtl.mmf.controller.TestJasperBean");
    private static final String COMPILE_FILE_NAME = REPORT_JRXML_FILE_NAME;

    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }

    @Override
    protected Map<String, Object> getReportParameters() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();

        //The report title
        //reportParameters.put("ReportTitle", "JasperReports Test");		
        return reportParameters;
    }

    protected JRDataSource getJRDataSource() {
        // return your custom datasource implementation
        ReportsDataSource dataSource = new ReportsDataSource();
        return dataSource;

    }

    public String execute() {
        try {
            super.prepareReport();
        } catch (IOException e) {
           LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        } catch (JRException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        }
        return null;
    }
}
