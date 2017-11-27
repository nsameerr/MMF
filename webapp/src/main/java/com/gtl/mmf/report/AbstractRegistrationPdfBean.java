/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.report;

import com.gtl.mmf.report.util.ReportConfigUtil;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.COMPILE_DIR;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.FILE_PATH;
import static com.gtl.mmf.service.util.IConstants.JASPER;
import static com.gtl.mmf.service.util.IConstants.MANDATEFILE;
import static com.gtl.mmf.service.util.IConstants.MMF_MANDATE_PAGE;
import static com.gtl.mmf.service.util.IConstants.MMF_REGISTRATION_PAGE;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author trainee12
 */
public abstract class AbstractRegistrationPdfBean {

    private ReportExportOption exportOption;
    private static final String DIRECTORY = "/registration";
    private HttpServletRequest httpRequest;

    public AbstractRegistrationPdfBean() {
        super();
        setExportOption(ReportExportOption.PDF);
    }

    protected void prepareReport() throws JRException, IOException, SQLException, ClassNotFoundException {
        ExternalContext externalContext = null;
    	ServletContext context = null;
    	try{
    	   context = httpRequest.getSession().getServletContext();   
       }catch(Exception e){
    	   e.printStackTrace();
    	   externalContext = FacesContext.getCurrentInstance().getExternalContext();
    	   context = (ServletContext) externalContext.getContext();
       }
    	
       // ServletContext context = (ServletContext) externalContext.getContext();
      //  HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
       // HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        File reportFile;
        File mandateFile;
        JasperPrint mandatePrint = null;
        JasperPrint jasperPrint = null;
        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        boolean first = true;
        boolean mandatefirst = true;
        String[] fieldLength = PropertiesLoader.getPropertiesValue(MMF_REGISTRATION_PAGE).split(COMA);
//        String mandatePage = PropertiesLoader.getPropertiesValue(MMF_MANDATE_PAGE);
        String[] mandatePage = PropertiesLoader.getPropertiesValue(MMF_MANDATE_PAGE).split(COMA);
        File directory = new File(LookupDataLoader.getResourcePath() + DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRPdfExporterParameter.IS_COMPRESSED, Boolean.TRUE);
        if (isRegistration()) {
            if (fieldLength.length != 0 && isInvestor()) {
                for (String pageName : fieldLength) {
                    if (first) {
                        reportFile = new File(ReportConfigUtil.getJasperFilePath(context,
                                getCompileDir(), pageName + JASPER));
                        jasperPrint = ReportConfigUtil.fillReport(reportFile,
                                null, new JRBeanCollectionDataSource(getRegistrationReportList()));
                        jasperPrints.add(jasperPrint);
                    }
                    if (!first) {
                        reportFile = new File(ReportConfigUtil.getJasperFilePath(context,
                                getCompileDir(), pageName + JASPER));
                        JasperPrint jasperPrintPages = ReportConfigUtil.fillReport(reportFile,
                                null, new JRBeanCollectionDataSource(getRegistrationReportList()));

                        jasperPrints.add(jasperPrintPages);
                    }
                    first = false;
                }
                for (String mndtpageName : mandatePage) {
                    if (mandatefirst) {
                        mandateFile = new File(ReportConfigUtil.getJasperFilePath(context,
                                getCompileDir(), mndtpageName + JASPER));
                        mandatePrint = ReportConfigUtil.fillReport(mandateFile,
                                null, new JRBeanCollectionDataSource(getMandateReportList()));
//                        mandatePrint.setPageHeight(269);
                        jasperPrints.add(mandatePrint);
                    }
                    if (!mandatefirst) {
                        reportFile = new File(ReportConfigUtil.getJasperFilePath(context,
                                getCompileDir(), mndtpageName + JASPER));
                        JasperPrint mandatePrintPages = ReportConfigUtil.fillReport(reportFile,
                                null, new JRBeanCollectionDataSource(getMandateReportList()));
                        mandatePrintPages.setPageHeight(269);
                        jasperPrints.add(mandatePrintPages);
                    }
                    first = false;
                }
//                mandateFile = new File(ReportConfigUtil.getJasperFilePath(context,
//                        getCompileDir(), mandatePage + JASPER));
//                mandatePrint = ReportConfigUtil.fillReport(mandateFile,
//                        null, new JRBeanCollectionDataSource(getMandateReportList()));
//                mandatePrint.setPageHeight(269);
//                jasperPrints.add(mandatePrint);
                if (new File(LookupDataLoader.getResourcePath() + FILE_PATH + getCompileFileName() + DOT + getExportOption().toString().toLowerCase()).exists()) {
                    File reg_pdf = new File(LookupDataLoader.getResourcePath() + FILE_PATH + getCompileFileName() + DOT + getExportOption().toString().toLowerCase());
                    reg_pdf.delete();
                }

                // Registration pdf
                File pdfFile = new File(LookupDataLoader.getResourcePath() + FILE_PATH + getCompileFileName() + DOT + getExportOption().toString().toLowerCase());
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfFile.getAbsolutePath()); // your output goes here
                exporter.exportReport();
            }
        } else {
            ///Mandate file
            mandateFile = new File(ReportConfigUtil.getJasperFilePath(context,
                    getCompileDir(), mandatePage + JASPER));
            mandatePrint = ReportConfigUtil.fillReport(mandateFile,
                    null, new JRBeanCollectionDataSource(getMandateReportList()));

            if (new File(LookupDataLoader.getResourcePath() + FILE_PATH + getCompileFileName() + MANDATEFILE + DOT + getExportOption().toString().toLowerCase()).exists()) {
                File mndt_pdf = new File(LookupDataLoader.getResourcePath() + FILE_PATH + getCompileFileName() + MANDATEFILE + DOT + getExportOption().toString().toLowerCase());
                mndt_pdf.delete();
            }
            //mandate pdf
            File mandatePdfFile = new File(LookupDataLoader.getResourcePath() + FILE_PATH + getCompileFileName() + MANDATEFILE + DOT + getExportOption().toString().toLowerCase());
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, mandatePrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, mandatePdfFile.getAbsolutePath()); // your output goes here
            exporter.exportReport();
        }

    }

    public ReportExportOption getExportOption() {
        return exportOption;
    }

    public void setExportOption(ReportExportOption exportOption) {
        this.exportOption = exportOption;
    }

    protected Map<String, Object> getReportParameters() {
        return new HashMap<String, Object>();
    }

    protected ArrayList<RegistrationVo> getRegistrationReportList() {
        return new ArrayList<RegistrationVo>();
    }

    protected ArrayList<MandateVo> getMandateReportList() {
        return new ArrayList<MandateVo>();
    }

    protected String getCompileDir() {
        return COMPILE_DIR;
    }

    //protected abstract JRDataSource getJRDataSource();
    protected abstract String getCompileFileName();

    protected abstract boolean isRegistration();

    protected abstract boolean isInvestor();

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
}
