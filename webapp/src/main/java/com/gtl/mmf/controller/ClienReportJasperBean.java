/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.report.AbstractReportBean;
import com.gtl.mmf.report.util.ClientPortfolioReportSummaryVO;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.CLIENT_SUMMARY_REPORT_JRXML_FILE_NAME;
import static com.gtl.mmf.service.util.IConstants.D_SPACE_MMM_COMMA_YYYY;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import org.springframework.context.annotation.Scope;

@Named("clienreportjasperbean")
@Scope("view")
public class ClienReportJasperBean extends AbstractReportBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.ClienReportJasperBean");
    private static final String COMPILE_FILE_NAME = CLIENT_SUMMARY_REPORT_JRXML_FILE_NAME;

    private ClientPortfolioReportSummaryVO clientPortfolioReportSummaryVO;

    @PostConstruct
    public void afterInit() {
        clientPortfolioReportSummaryVO = new ClientPortfolioReportSummaryVO();
    }

    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }

    @Override
    protected Map<String, Object> getReportParameters() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
        try {
            Date startDate = DateUtil.stringToDate(clientPortfolioReportSummaryVO.getStartDate(), D_SPACE_MMM_COMMA_YYYY);
            Date endDate = DateUtil.stringToDate(clientPortfolioReportSummaryVO.getEndDate(), D_SPACE_MMM_COMMA_YYYY);
            reportParameters.put("clientId", clientPortfolioReportSummaryVO.getClientId());
            reportParameters.put("clientName", clientPortfolioReportSummaryVO.getClientName());
            reportParameters.put("startDate", startDate);
            reportParameters.put("endDate", endDate);
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE,StackTraceWriter.getStackTrace(ex));
        }
        return reportParameters;
    }

    public void generteReport() throws SQLException, ClassNotFoundException {
        try {
            super.prepareReport();
        } catch (JRException ex) {
            LOGGER.log(Level.SEVERE,StackTraceWriter.getStackTrace(ex));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public ClientPortfolioReportSummaryVO getClientPortfolioReportSummaryVO() {
        return clientPortfolioReportSummaryVO;
    }

    public void setClientPortfolioReportSummaryVO(ClientPortfolioReportSummaryVO clientPortfolioReportSummaryVO) {
        this.clientPortfolioReportSummaryVO = clientPortfolioReportSummaryVO;
    }
}
