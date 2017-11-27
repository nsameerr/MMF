/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.report.util;

import com.gtl.mmf.report.AbstractRegistrationPdfBean;
import static com.gtl.mmf.report.ReportExportOption.PDF;
import static com.gtl.mmf.service.util.IConstants.CROSS;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationDataProcessingVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;

import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author trainee8
 */
public class RegistrationPdfDataPreparation extends AbstractRegistrationPdfBean {

    ArrayList<RegistrationVo> dataList;
    ArrayList<MandateVo> dataListMandate;
    String RegistrationPdfName;
    boolean investor;
    boolean registrationFlag;
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.report.util.RegistrationPdfDataPreparationBAO");
    HttpServletRequest httpRequest;

    public void createRegistrationPdfData(RegistrationDataProcessingVo regDataVo, boolean registration) {
        investor = true;
        registrationFlag = registration;

        if (registrationFlag) {
            setRegistrationPdfName(regDataVo.getRegistrationVo().getRegId());
            dataList = new ArrayList<RegistrationVo>();
            RegistrationVo registrationVo = regDataVo.getRegistrationVo();
            if ((registrationVo.getNationality() != null) && registrationVo.getNationality().
                    equalsIgnoreCase("IN")) {
                registrationVo.setNationality(EMPTY_STRING);
            }
            dataList.add(registrationVo);
        } else {
            setRegistrationPdfName(regDataVo.getMandateVo().getReg_id());
        }

        dataListMandate = new ArrayList<MandateVo>();
        dataListMandate.add(regDataVo.getMandateVo());
        setExportOption(PDF);
        try {
            generteReport();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationPdfDataPreparation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationPdfDataPreparation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generteReport() throws SQLException, ClassNotFoundException {
        try {
            super.prepareReport();
        } catch (JRException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    protected String getCompileFileName() {
        return getRegistrationPdfName();
    }

    @Override
    protected ArrayList<RegistrationVo> getRegistrationReportList() {
        return getDataList();
    }

    @Override
    protected ArrayList<MandateVo> getMandateReportList() {
        return getDataListMandate();
    }

    public ArrayList<RegistrationVo> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<RegistrationVo> dataList) {
        this.dataList = dataList;
    }

    @Override
    protected boolean isRegistration() {
        return registrationFlag;
    }

    public String getRegistrationPdfName() {
        return RegistrationPdfName;
    }

    public void setRegistrationPdfName(String RegistrationPdfName) {
        this.RegistrationPdfName = RegistrationPdfName;
    }

    public ArrayList<MandateVo> getDataListMandate() {
        return dataListMandate;
    }

    public void setDataListMandate(ArrayList<MandateVo> dataListMandate) {
        this.dataListMandate = dataListMandate;
    }

    public void createAdvisorMandateData(String id, MandateVo mandateVo) {
        investor = false;
        setRegistrationPdfName(id);
        dataListMandate = new ArrayList<MandateVo>();
        dataListMandate.add(mandateVo);
        setExportOption(PDF);
        try {
            generteReport();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationPdfDataPreparation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationPdfDataPreparation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected boolean isInvestor() {
        return investor;
    }

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
		super.setHttpRequest(httpRequest);
	}

}
