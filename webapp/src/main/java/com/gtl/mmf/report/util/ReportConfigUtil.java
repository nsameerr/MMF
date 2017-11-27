/**
 * 
 */
package com.gtl.mmf.report.util;

import static com.gtl.mmf.service.util.IConstants.JASPER;
import static com.gtl.mmf.service.util.IConstants.JRXML;
import com.gtl.mmf.util.StackTraceWriter;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

/**
 * @author 08237
 *
 */
public class ReportConfigUtil {
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.report.util.ReportConfigUtil");
	private static void setCompileTempDir(ServletContext context, String uri) {
		System.setProperty("jasper.reports.compile.temp", context.getRealPath(uri));
	}
	
	/**
	 * To compile the jrxml
	 * @param context
	 * @param compileDir
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public static boolean compileReport(ServletContext context, String compileDir, 
			String filename) throws JRException {
		String jasperFileName = context.getRealPath(compileDir + filename +JASPER);
		File jasperFile = new File(jasperFileName);
		
		if(jasperFile.exists()) { 			
			// jasper file already exists, do not compile again
			return true; 
		}
		try {
			
			// jasper file has not been constructed yet, so compile the xml file
			setCompileTempDir(context, compileDir);			
			String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(JASPER))+JRXML;			
			JasperCompileManager.compileReportToFile(xmlFileName);			
			return true;
		}catch(JRException e) {
			 LOGGER.log(Level.SEVERE, "Benchmark details updation failed. Exception : {0}", StackTraceWriter.getStackTrace(e));
			 return false;
		}
	}
	
	/**
	 * To fill data in report
	 * @param reportFile
	 * @param parameters
         * @param jRDataSource
	 * @return
	 * @throws JRException
	 */
	public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, 
			         JRDataSource jRDataSource) throws JRException {
		//parameters.put("BaseDir", reportFile.getParentFile());		
                JasperPrint jasperPrint =  JasperFillManager.fillReport(reportFile.getPath(), 
				parameters, jRDataSource);
		return jasperPrint;
	}
        
        public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, 
			         Connection connection) throws JRException {
		//parameters.put("BaseDir", reportFile.getParentFile());		
		JasperPrint jasperPrint =  JasperFillManager.fillReport(reportFile.getPath(), 
				parameters, connection);
		return jasperPrint;
	}
	
	public static String getJasperFilePath(ServletContext context, String compileDir, 
			String jasperFile) {
		return context.getRealPath(compileDir + jasperFile);
	}
	
	/**
	 * To export PDF, XLS and RTF
	 * @param exporter
	 * @param jasperPrint
	 * @param out
	 * @throws JRException
	 */
	private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, 
			PrintWriter out) throws JRException {
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		
		exporter.exportReport();
	}

	/**
	 * To export as HTML
	 * @param jasperPrint
	 * @param out
	 * @throws JRException
	 */
	public static void exportReportAsHtml(JasperPrint jasperPrint, 
			PrintWriter out) throws JRException {
		JRHtmlExporter exporter = new JRHtmlExporter();	
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
		
		exportReport(exporter, jasperPrint, out);
	}
}
