/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.BenchmarkPerformanceTb;
import static com.gtl.mmf.service.util.IConstants.MINUS_ONE;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author 07958
 */
public class BenchmarkDetailsVO {

    private Integer bmPerformanceId;
    private String benchmarkName;
    private int benchmarkId;
    private Double closedIndex;
    private Double openIndex;
    private Double highIndex;
    private Double lowIndex;
    private Integer sharesTraded;
    private Double turnover;
    private Date dateOfIndex;

    public BenchmarkDetailsVO() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, MINUS_ONE);
        dateOfIndex = cal.getTime();
    }

    public BenchmarkPerformanceTb toBenchmarkPerformanceTb() {
        BenchmarkPerformanceTb benchmarkPerformanceTb = new BenchmarkPerformanceTb();
        benchmarkPerformanceTb.setBenchmark(benchmarkId);
        benchmarkPerformanceTb.setOpen(BigDecimal.valueOf(openIndex));
        benchmarkPerformanceTb.setClose(BigDecimal.valueOf(closedIndex));
        benchmarkPerformanceTb.setHigh(BigDecimal.valueOf(highIndex));
        benchmarkPerformanceTb.setLow(BigDecimal.valueOf(lowIndex));
        benchmarkPerformanceTb.setDatetime(dateOfIndex);
        return benchmarkPerformanceTb;
    }

    public Integer getBmPerformanceId() {
        return bmPerformanceId;
    }

    public void setBmPerformanceId(Integer bmPerformanceId) {
        this.bmPerformanceId = bmPerformanceId;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    public int getBenchmarkId() {
        return benchmarkId;
    }

    public void setBenchmarkId(int benchmarkId) {
        this.benchmarkId = benchmarkId;
    }

    public Double getClosedIndex() {
        return closedIndex;
    }

    public void setClosedIndex(Double closedIndex) {
        this.closedIndex = closedIndex;
    }

    public Double getOpenIndex() {
        return openIndex;
    }

    public void setOpenIndex(Double openIndex) {
        this.openIndex = openIndex;
    }

    public Double getHighIndex() {
        return highIndex;
    }

    public void setHighIndex(Double highIndex) {
        this.highIndex = highIndex;
    }

    public Double getLowIndex() {
        return lowIndex;
    }

    public void setLowIndex(Double lowIndex) {
        this.lowIndex = lowIndex;
    }

    public Integer getSharesTraded() {
        return sharesTraded;
    }

    public void setSharesTraded(Integer sharesTraded) {
        this.sharesTraded = sharesTraded;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Date getDateOfIndex() {
        return dateOfIndex;
    }

    public void setDateOfIndex(Date dateOfIndex) {
        this.dateOfIndex = dateOfIndex;
    }
}
