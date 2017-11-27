/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.MASTER_PORTFOLIOS_LIST_SIZE;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 07958
 */
public class ServiceThreadUtil {

    /**
     * Finding out Partition list size
     *
     * @param totalPortfoliosCount total size portfolios
     * @return
     */
    public static int getPartitionedListSize(Long totalPortfoliosCount) {
        int partitionedListSize;
        if (totalPortfoliosCount <= MASTER_PORTFOLIOS_LIST_SIZE) {
            partitionedListSize = ONE;
        } else {
//            partitionedListSize = (int) Math.ceil(totalPortfoliosCount / MASTER_PORTFOLIOS_LIST_SIZE);
            partitionedListSize = ONE;
        }
        return partitionedListSize;
    }

    /**
     * Creating Partitioned list of portfolios based on new Partitioned list
     * size.
     *
     * @param partitionedListSize
     * @return
     */
    public static List<List<Object>> getPartitionedList(int partitionedListSize) {
        List<List<Object>> partitionedList = new ArrayList<List<Object>>();
        for (int i = ZERO; i < partitionedListSize; i++) {
            List<Object> objects = new ArrayList<Object>();
            partitionedList.add(objects);
        }
        return partitionedList;
    }
}
