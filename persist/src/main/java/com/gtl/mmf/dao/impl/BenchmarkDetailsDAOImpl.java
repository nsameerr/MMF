/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IBenchmarkDetailsDAO;
import com.gtl.mmf.entity.BenchmarkPerformanceTb;
import java.util.Date;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class BenchmarkDetailsDAOImpl implements IBenchmarkDetailsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int insertNewBenchmarkDetails(BenchmarkPerformanceTb benchmarkPerformanceTb) {
        if (benchmarkPerformanceTb != null && benchmarkPerformanceTb.getClose() != null
                & benchmarkPerformanceTb.getClose().doubleValue() > 0) {
            return (Integer) sessionFactory.getCurrentSession().save(benchmarkPerformanceTb);
        } else {
            //Code to insert previous day value for benchmark when no data received from MDS 
            String sql = "SELECT MAX(`datetime`) FROM benchmark_performance_tb"
                    + " WHERE `benchmark`=:benchmark";
            SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setInteger("benchmark", benchmarkPerformanceTb.getBenchmark());
            Date lastday = (Date) sqlQuery.uniqueResult();

            sql = "INSERT INTO benchmark_performance_tb(benchmark,datetime,open,high,low,close)"
                    + " SELECT `benchmark`,:eodDate,`open`,`high`,`low`,`close`"
                    + " FROM benchmark_performance_tb"
                    + " WHERE DATE_FORMAT(`datetime`,'%Y-%m-%d')= DATE_FORMAT(:dateYesterday,'%Y-%m-%d')AND `benchmark`=:benchmark";
            sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            return sqlQuery.setDate("eodDate", benchmarkPerformanceTb.getDatetime())
                    .setDate("dateYesterday", lastday)
                    .setInteger("benchmark", benchmarkPerformanceTb.getBenchmark())
                    .executeUpdate();
        }

    }
}
