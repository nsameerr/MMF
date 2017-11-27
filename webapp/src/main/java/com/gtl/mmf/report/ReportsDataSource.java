/**
 * 
 */
package com.gtl.mmf.report;

import java.util.List;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.service.util.BeanLoader;

/**
 * @author 08237
 *
 */
public class ReportsDataSource implements JRDataSource {
	
	private List<UserDetailsVO> list;	
	private int index = -1;
	
	public ReportsDataSource() {
		super();
		IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
		list = userProfileBAO.getAllUsers();
	}
	
	public boolean next() throws JRException {
		index++;
		return index < list.size();
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;

		String fieldName = field.getName();

//		if (fieldName.equals("City")) {
//			value = list.get(index).getCin();
//		} else if (fieldName.equals("Id")) {
//			value = list.get(index).getPanNo();
//		} else if (fieldName.equals("Name")) {
//			value = list.get(index).getHomeAddress();
//		} else if (fieldName.equals("Street")) {
//			value = "";
//		}
                if (fieldName.equals("master_customer_tb_home_address1")) {
			value = list.get(index).getHomeAddress();
                }

		return value;
	}
}
