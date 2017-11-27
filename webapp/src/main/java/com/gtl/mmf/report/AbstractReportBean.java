/**
 * 
 */
package com.gtl.mmf.report;

import com.gtl.mmf.report.util.ReportConfigUtil;
import static com.gtl.mmf.service.util.IConstants.COMPILE_DIR;
import static com.gtl.mmf.service.util.IConstants.CONTENT_DISPOSITION;
import static com.gtl.mmf.service.util.IConstants.CONTENT_TYPE_APPLICATION;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.JASPER;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.util.DbConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
/**
 * @author 08237
 *
 */
public abstract class AbstractReportBean {
	private ReportExportOption exportOption;
	private static final String TEMP = ".tmp";
	private static final String CURRENT_DIRECTORY = DOT;
	
	public AbstractReportBean() {
		super();
		setExportOption(ReportExportOption.PDF);
	}
	
	protected void prepareReport() throws JRException, IOException, SQLException, ClassNotFoundException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
		ServletContext context = (ServletContext) externalContext.getContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();		
		ReportConfigUtil.compileReport(context, getCompileDir(), getCompileFileName());		
		File reportFile = new File(ReportConfigUtil.getJasperFilePath(context, 
				getCompileDir(), getCompileFileName()+JASPER));		
		
                JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, 
				getReportParameters(),DbConnection.getInstance().getConnection());
                
		if(getExportOption().equals(ReportExportOption.HTML)) {
			ReportConfigUtil.exportReportAsHtml(jasperPrint, response.getWriter());
		}else {		
			//Commented code to handle pdf,xls,rtf format
			//request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);		
			//response.sendRedirect(request.getContextPath()+DOUBLE_FORWARED_SLAH+getExportOption());

			response.setContentType(CONTENT_TYPE_APPLICATION+getExportOption()
					.toString().toLowerCase());
			response.setHeader(CONTENT_DISPOSITION, 
					"attachment;filename="+getCompileFileName()+
					DOT+getExportOption().toString().toLowerCase());
			ServletOutputStream sos = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, sos);
			File tempFile = File.createTempFile(getCompileFileName()+DOT
					+getExportOption().toString().toLowerCase(),
					TEMP,new File(CURRENT_DIRECTORY));
			InputStream isStream = null;
			isStream = new FileInputStream(tempFile);
			int ibit = 256;
			while ((ibit) >= ZERO)
			 {
	            ibit = isStream.read();
	            sos.write(ibit);
			 }
			sos.flush();
			sos.close();
			isStream.close();
		}		
		FacesContext.getCurrentInstance().responseComplete();
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

	protected String getCompileDir() {
		return COMPILE_DIR;
	}
	
	//protected abstract JRDataSource getJRDataSource();

	protected abstract String getCompileFileName();
}
