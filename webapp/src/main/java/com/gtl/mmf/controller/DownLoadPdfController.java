/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.FILE_PATH;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.PDF;
import static com.gtl.mmf.service.util.IConstants.RIGHTS_AND_OBLIGATIONS_DOCUMENT;
import static com.gtl.mmf.service.util.IConstants.SINGLE_SLASH;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.webapp.util.FileUploadUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 09607
 */
@Named("downLoadPdfController")
@Scope("view")
public class DownLoadPdfController {

    boolean investor;
    String regId;

    @PostConstruct

    public void afterInit() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        if (storedValues.get(IS_ADVISOR) != null && !(Boolean) storedValues.get(IS_ADVISOR)) {
            regId = (String) storedValues.get("RegID");
            investor = true;
        }

    }

    public void downloadPdf() {
        List<String> fileList = new ArrayList<String>();
        String file1 = LookupDataLoader.getResourcePath() + FILE_PATH + this.getRegId() + DOT + PDF;
        String file2 = LookupDataLoader.getResourcePath() + SINGLE_SLASH + RIGHTS_AND_OBLIGATIONS_DOCUMENT + DOT + PDF;
        fileList.add(file1);
        fileList.add(file2);
//        boolean dwnld_status = FileUploadUtil.downloadFile(file1);
        boolean dwnld_status = FileUploadUtil.downloadFile(fileList);
    }

    public boolean isInvestor() {
        return investor;
    }

    public void setInvestor(boolean investor) {
        this.investor = investor;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

}
