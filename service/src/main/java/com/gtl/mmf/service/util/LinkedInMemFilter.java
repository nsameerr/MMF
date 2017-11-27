/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.linkedin.util.LinkedInConnectionsVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trainee3
 */
public class LinkedInMemFilter {

    public static List<String> getFilteredMembers(List<String> memList,
            List<LinkedInConnectionsVO> linkedinLIst) {
        List<String> tempList = new ArrayList<String>();
        List<String> tempMemList = new ArrayList<String>();
        tempMemList.addAll(memList);
        for (LinkedInConnectionsVO licvo : linkedinLIst) {
            tempList.add(licvo.getId());
        }
        tempMemList.retainAll(tempList);
        return tempMemList;
    }
}
