/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author 07958
 */
public class PortfolioPerformanceUtil {

    private static final int ROUND_SCALE = 2;

    public static Double getPortfolioPerformance(List<BigDecimal> subPeriodReturns) {
        Double sumSubPeriodValue = 1.0;
        for (BigDecimal subPeriodReturn : subPeriodReturns) {
            Double subReturn = subPeriodReturn == null ? BigDecimal.ZERO.doubleValue() : subPeriodReturn.doubleValue();
            sumSubPeriodValue *= (ONE+ subReturn);
        }
        Double totalReturn = sumSubPeriodValue - ONE;
        return BigDecimal.valueOf(totalReturn * 100.0).setScale(ROUND_SCALE, RoundingMode.HALF_UP).doubleValue();
    }

    public static Double getPerformanceWithValues(Double closeValue, Double startValue) {
        Double performance;
        if (closeValue == null || closeValue == ZERO_POINT_ZERO
                || startValue == null || startValue == ZERO_POINT_ZERO) {
            performance = ZERO_POINT_ZERO;
        } else {
            performance = ((closeValue / startValue) - ONE) * 100.0;
        }
        return performance;
    }
}
