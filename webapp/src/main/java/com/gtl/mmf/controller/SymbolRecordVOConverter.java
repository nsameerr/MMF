/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import com.gtl.mmf.service.util.SymbolListUtil;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author 07958
 */
@FacesConverter(value = "SymbolRecordVOConverter")
public class SymbolRecordVOConverter implements Converter {
	private static final Logger LOGGER = Logger
			.getLogger("com.gtl.mmf.controller.SymbolRecordVOConverter");
    private String asset;

    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.equals(EMPTY_STRING)) {
            return null;
        }
        try {
            asset = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("asset");
            SymbolRecordVO objectSymbolRecordVO = null;
            if (asset != null) {
                List<SymbolRecordVO> symbolList = SymbolListUtil.getSymbolList(asset);
                for (SymbolRecordVO symbolRecordVO : symbolList) {
                    if (symbolRecordVO.getTicker().equalsIgnoreCase(string)) {
                        objectSymbolRecordVO = symbolRecordVO;
                        break;
                    }
                }
            }
            if (objectSymbolRecordVO == null) {
                objectSymbolRecordVO = new SymbolRecordVO();
            }
            return objectSymbolRecordVO;
        } catch (Exception e) {
        	LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "No valid data.", ""));
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (String) o;
//        SymbolRecordVO symbolRecordVO = (SymbolRecordVO) o;
//        return symbolRecordVO.getTicker();

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
