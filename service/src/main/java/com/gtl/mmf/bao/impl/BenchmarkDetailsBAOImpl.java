/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IBenchmarkDetailsBAO;
import com.gtl.mmf.dao.IBenchmarkDetailsDAO;
import com.gtl.mmf.entity.BenchmarkPerformanceTb;
import com.gtl.mmf.service.vo.BenchmarkDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class BenchmarkDetailsBAOImpl implements IBenchmarkDetailsBAO {

    @Autowired
    private IBenchmarkDetailsDAO benchmarkDetailsDAO;
/**
 * Inserting Benchmark information on each day
 * @param benchmarkDetailsVO - Current day Benchmark details
 * @return 
 */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int insertNewBenchmarkDetails(BenchmarkDetailsVO benchmarkDetailsVO) {
        BenchmarkPerformanceTb benchmarkPerformanceTb = benchmarkDetailsVO.toBenchmarkPerformanceTb();
        return benchmarkDetailsDAO.insertNewBenchmarkDetails(benchmarkPerformanceTb);
    }
}
