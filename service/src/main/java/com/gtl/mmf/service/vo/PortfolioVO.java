/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.MasterBenchmarkIndexTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.DECIMALPLACES_VALUE;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import static com.gtl.mmf.service.util.IConstants.SIX;
import static com.gtl.mmf.service.util.IConstants.TAX_PLANNING;
import static com.gtl.mmf.service.util.IConstants.TEXT_BALANCED;
import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT_LIQUID;
import static com.gtl.mmf.service.util.IConstants.TEXT_EQUITY;
import static com.gtl.mmf.service.util.IConstants.TEXT_FNO;
import static com.gtl.mmf.service.util.IConstants.TEXT_GOLD;
import static com.gtl.mmf.service.util.IConstants.TEXT_INDEX;
import static com.gtl.mmf.service.util.IConstants.TEXT_INTERNATIONAL;
import static com.gtl.mmf.service.util.IConstants.TEXT_MUTUAL_FUND;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 07958
 */
public class PortfolioVO {

    private Integer portfolioId;
    private int advisorId;

    //Equity Diversified
    private PortfolioDetailsVO equityDiversified;
    private PortfolioDetailsVO equity;
    private PortfolioDetailsVO debt;
    private PortfolioDetailsVO fno;
    private PortfolioDetailsVO international;
    private PortfolioDetailsVO gold;
    private PortfolioDetailsVO cash;
    private PortfolioDetailsVO mutualfund;
    private PortfolioDetailsVO index;
    private PortfolioDetailsVO midcap;
    private PortfolioDetailsVO tax_planning;
    private String portfolioName;
    private Integer benchmarkIndexSelected;
    private Integer portfolioStyleSelectedKey;
    private Double currentValue;
    private Boolean rebalanceRequired;
    private Date rebalanceRequiredDate;
    private String benchmarkName;
    private BigDecimal portfolioValue;
    private Date dateCreated;
    private Integer noOfCustomersAssigned;
    private PortfolioDetailsVO balanced;
    private Integer portfolioSizeSelectedKey;
    private Integer portfolioTypeSelectedKey;
    private PortfolioDetailsVO debtLiquid;
    private Double balanceCashWhnLastUpdated;

    public PortfolioVO() {
        setAssetClasses();
    }

    public PortfolioVO(Map<String, Short> assets, PortfolioTypeVO portfolioTypeVO, Integer benchmarkIndexSelected) {
        setAssetClasses();
        equity.setAssetId(assets.get(TEXT_EQUITY));
        equityDiversified.setAssetId(assets.get(EQUITY_DIVERSIFIED));
        debt.setAssetId(assets.get(TEXT_DEBT));
        international.setAssetId(assets.get(TEXT_INTERNATIONAL));
        gold.setAssetId(assets.get(TEXT_GOLD));
        cash.setAssetId(assets.get(TEXT_CASH));
        mutualfund.setAssetId(assets.get(TEXT_MUTUAL_FUND));
        index.setAssetId(assets.get(TEXT_INDEX));
        midcap.setAssetId(assets.get(MIDCAP));
        fno.setAssetId(assets.get(TEXT_FNO));
        balanced.setAssetId(assets.get(TEXT_BALANCED));
        debtLiquid.setAssetId(assets.get(TEXT_DEBT_LIQUID));
        tax_planning.setAssetId(assets.get(TAX_PLANNING));
        this.assignRangesInPortfolioDetails(portfolioTypeVO);
        this.benchmarkIndexSelected = benchmarkIndexSelected;
    }

    public void changePortfolioStyle(PortfolioTypeVO portfolioStyleSelected) {
        this.assignRangesInPortfolioDetails(portfolioStyleSelected);
    }

    private void assignRangesInPortfolioDetails(PortfolioTypeVO portfolioStyleSelected) {

        //Equity diversified
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, EQUITY_DIVERSIFIED)) {
            equityDiversified.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_diversifiedEquity()));
            equityDiversified.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_diversifiedEquity()));
        }/*
         if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_EQUITY)) {
         equity.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_diversifiedEquity()));
         equity.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_diversifiedEquity()));
         }*/

        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_DEBT)) {
            debt.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_debt()));
            debt.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_debt()));
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_GOLD)) {
            gold.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_gold()));
            gold.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_gold()));
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_INTERNATIONAL)) {
            international.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_international()));
            international.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_international()));
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_FNO)) {
            fno.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_fo()));
            fno.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_fo()));
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_CASH)) {
            cash.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_cash()));
            cash.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_cash()));
        }
//        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_MUTUAL_FUND)) {
//            mutualfund.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_mutual_fund()));
//            mutualfund.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_mutual_fund()));
//        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_INDEX)) {
            index.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_equity()));
            index.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_equity()));
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, MIDCAP)) {
            midcap.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_midcap()));
            midcap.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_midcap()));
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_BALANCED)) {
            balanced.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_balanced()));
            balanced.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_balanced()));
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_DEBT_LIQUID)) {
            debtLiquid.setMinimumRange(Double.valueOf(portfolioStyleSelected.getRange_min_debt_liquid()));
            debtLiquid.setMaximumRange(Double.valueOf(portfolioStyleSelected.getRange_max_debt_liquid()));
        }

        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TAX_PLANNING)) {
            tax_planning.setMinimumRange(Double.valueOf(portfolioStyleSelected.getMin_tax_planning()));
            tax_planning.setMaximumRange(Double.valueOf(portfolioStyleSelected.getMax_tax_planning()));
        }
    }

    /**
     * Used to convert portfolioVo to portfolioTb
     *
     * @return portfolioTb with information from portfolioVo
     */
    public PortfolioTb toPortfolioTb() {
        PortfolioTb portfolioTb = new PortfolioTb();
        portfolioTb.setPortfolioId(portfolioId);

        // Setting portfolio type
        MasterPortfolioTypeTb masterPortfolioTypeTb = new MasterPortfolioTypeTb();
        masterPortfolioTypeTb.setId(portfolioTypeSelectedKey);
        portfolioTb.setMasterPortfolioTypeTb(masterPortfolioTypeTb);

        // Setting advisor
        MasterAdvisorTb masterAdvisorTb = new MasterAdvisorTb();
        masterAdvisorTb.setAdvisorId(advisorId);
        portfolioTb.setMasterAdvisorTb(masterAdvisorTb);

        // Setting benchmark
        MasterBenchmarkIndexTb benchmarkIndexTb = new MasterBenchmarkIndexTb();
        benchmarkIndexTb.setId(benchmarkIndexSelected);
        portfolioTb.setMasterBenchmarkIndexTb(benchmarkIndexTb);

        // Setting name, date created
        portfolioTb.setPortfolioName(portfolioName);
        portfolioTb.setPortfolioValue(BigDecimal.valueOf(currentValue).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioTb.setStatus(true);
        portfolioTb.setDateCreated(dateCreated);
        portfolioTb.setNoOfCustomersAssigned(noOfCustomersAssigned);
        portfolioTb.setBalanceCash(balanceCashWhnLastUpdated);
        return portfolioTb;
    }

    /**
     *
     * @param portfolioStyleSelected - Type of portfolio created like
     * aggressive,moderate,conservative etc.
     * @param portfolioIndex - ID of portfolio created
     * @param portfolioTb - portfolio created by advisor
     * @return List of asset class with securities assigned to it.
     */
    public List<PortfolioDetailsTb> toPortfolioDetailsTb(PortfolioTypeVO portfolioStyleSelected, Integer portfolioIndex, PortfolioTb portfolioTb) {
        List<PortfolioDetailsTb> portfolioDetailsTbs = new ArrayList<PortfolioDetailsTb>();
        portfolioTb.setPortfolioId(portfolioIndex);

        //Cheking whether the given asset class needed allocation when creating porytfolio
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, EQUITY_DIVERSIFIED)) {
            //converting each PortfolioDetailsVO into portfolioDetailsTb
            PortfolioDetailsTb portfolioDetailsTb = equityDiversified.toPortfolioDetailsTb();
            //Setting asset class id and portfolioTB into portfolioDetailsTb.
            getPortfolioDetailsTb(portfolioDetailsTb, equityDiversified.getAssetId(), portfolioTb);
            // selecting securities assigned in each asset class into PortfolioSecuritiesTb for saving
            List<PortfolioSecuritiesTb> securitiesTbs = equityDiversified.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_INDEX)) {
            PortfolioDetailsTb portfolioDetailsTb = index.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, index.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = index.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, MIDCAP)) {
            PortfolioDetailsTb portfolioDetailsTb = midcap.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, midcap.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = midcap.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_INTERNATIONAL)) {
            PortfolioDetailsTb portfolioDetailsTb = international.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, international.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = international.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_GOLD)) {
            PortfolioDetailsTb portfolioDetailsTb = gold.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, gold.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = gold.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
//        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_MUTUAL_FUND)) {
//            PortfolioDetailsTb portfolioDetailsTb = mutualfund.toPortfolioDetailsTb();
//            getPortfolioDetailsTb(portfolioDetailsTb, mutualfund.getAssetId(), portfolioTb);
//            List<PortfolioSecuritiesTb> securitiesTbs = mutualfund.toPortfolioSecuritiesTbs(portfolioIndex);
//            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
//            portfolioDetailsTbs.add(portfolioDetailsTb);
//        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_BALANCED)) {
            PortfolioDetailsTb portfolioDetailsTb = balanced.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, balanced.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = balanced.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_DEBT)) {
            PortfolioDetailsTb portfolioDetailsTb = debt.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, debt.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = debt.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (!portfolioStyleSelected.getMasterPortfolioStyleTb().getPortfolioStyleId().equals(SIX)) {
            if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_FNO)) {
                PortfolioDetailsTb portfolioDetailsTb = fno.toPortfolioDetailsTb();
                getPortfolioDetailsTb(portfolioDetailsTb, fno.getAssetId(), portfolioTb);
                List<PortfolioSecuritiesTb> securitiesTbs = fno.toPortfolioSecuritiesTbs(portfolioIndex);
                portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
                portfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_DEBT_LIQUID)) {
            PortfolioDetailsTb portfolioDetailsTb = debtLiquid.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, debtLiquid.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = debtLiquid.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_CASH)) {
            PortfolioDetailsTb portfolioDetailsTb = cash.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, cash.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = cash.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TAX_PLANNING)) {
            PortfolioDetailsTb portfolioDetailsTb = tax_planning.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, tax_planning.getAssetId(), portfolioTb);
            List<PortfolioSecuritiesTb> securitiesTbs = tax_planning.toPortfolioSecuritiesTbs(portfolioIndex);
            portfolioDetailsTb.setPortfolioSecuritiesTbs(new HashSet<PortfolioSecuritiesTb>(securitiesTbs));
            portfolioDetailsTbs.add(portfolioDetailsTb);
        }
        return portfolioDetailsTbs;
    }

    private PortfolioDetailsTb getPortfolioDetailsTb(PortfolioDetailsTb portfolioDetailsTb, Short assetId, PortfolioTb portfolioTb) {
        MasterAssetTb masterAssetTb = new MasterAssetTb();
        masterAssetTb.setId(assetId);
        portfolioDetailsTb.setMasterAssetTb(masterAssetTb);
        portfolioDetailsTb.setPortfolioTb(portfolioTb);
        return portfolioDetailsTb;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public Integer getBenchmarkIndexSelected() {
        return benchmarkIndexSelected;
    }

    public void setBenchmarkIndexSelected(Integer benchmarkIndexSelected) {
        this.benchmarkIndexSelected = benchmarkIndexSelected;
    }

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public PortfolioDetailsVO getEquity() {
        return equity;
    }

    public void setEquity(PortfolioDetailsVO equity) {
        this.equity = equity;
    }

    public PortfolioDetailsVO getDebt() {
        return debt;
    }

    public void setDebt(PortfolioDetailsVO debt) {
        this.debt = debt;
    }

    public PortfolioDetailsVO getFno() {
        return fno;
    }

    public void setFno(PortfolioDetailsVO fno) {
        this.fno = fno;
    }

    public PortfolioDetailsVO getInternational() {
        return international;
    }

    public void setInternational(PortfolioDetailsVO international) {
        this.international = international;
    }

    public PortfolioDetailsVO getGold() {
        return gold;
    }

    public void setGold(PortfolioDetailsVO gold) {
        this.gold = gold;
    }

    public PortfolioDetailsVO getCash() {
        return cash;
    }

    public void setCash(PortfolioDetailsVO cash) {
        this.cash = cash;
    }

    public Integer getPortfolioStyleSelectedKey() {
        return portfolioStyleSelectedKey;
    }

    public void setPortfolioStyleSelectedKey(Integer portfolioStyleSelectedKey) {
        this.portfolioStyleSelectedKey = portfolioStyleSelectedKey;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Boolean getRebalanceRequired() {
        return rebalanceRequired;
    }

    public void setRebalanceRequired(Boolean rebalanceRequired) {
        this.rebalanceRequired = rebalanceRequired;
    }

    public Date getRebalanceRequiredDate() {
        return rebalanceRequiredDate;
    }

    public void setRebalanceRequiredDate(Date rebalanceRequiredDate) {
        this.rebalanceRequiredDate = rebalanceRequiredDate;
    }

    public PortfolioDetailsVO getMutualfund() {
        return mutualfund;
    }

    public void setMutualfund(PortfolioDetailsVO mutualfund) {
        this.mutualfund = mutualfund;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getNoOfCustomersAssigned() {
        return noOfCustomersAssigned;
    }

    public void setNoOfCustomersAssigned(Integer noOfCustomersAssigned) {
        this.noOfCustomersAssigned = noOfCustomersAssigned;
    }

    public PortfolioDetailsVO getIndex() {
        return index;
    }

    public void setIndex(PortfolioDetailsVO index) {
        this.index = index;
    }

    public PortfolioDetailsVO getMidcap() {
        return midcap;
    }

    public void setMidcap(PortfolioDetailsVO midcap) {
        this.midcap = midcap;
    }

    public PortfolioDetailsVO getEquityDiversified() {
        return equityDiversified;
    }

    public void setEquityDiversified(PortfolioDetailsVO equityDiversified) {
        this.equityDiversified = equityDiversified;
    }

    public PortfolioDetailsVO getBalanced() {
        return balanced;
    }

    public void setBalanced(PortfolioDetailsVO balanced) {
        this.balanced = balanced;
    }

    public Integer getPortfolioSizeSelectedKey() {
        return portfolioSizeSelectedKey;
    }

    public void setPortfolioSizeSelectedKey(Integer portfolioSizeSelectedKey) {
        this.portfolioSizeSelectedKey = portfolioSizeSelectedKey;
    }

    public Integer getPortfolioTypeSelectedKey() {
        return portfolioTypeSelectedKey;
    }

    public void setPortfolioTypeSelectedKey(Integer portfolioTypeSelectedKey) {
        this.portfolioTypeSelectedKey = portfolioTypeSelectedKey;
    }

    public PortfolioDetailsVO getDebtLiquid() {
        return debtLiquid;
    }

    public void setDebtLiquid(PortfolioDetailsVO debtLiquid) {
        this.debtLiquid = debtLiquid;
    }

    public Double getBalanceCashWhnLastUpdated() {
        return balanceCashWhnLastUpdated;
    }

    public void setBalanceCashWhnLastUpdated(Double balanceCashWhnLastUpdated) {
        this.balanceCashWhnLastUpdated = balanceCashWhnLastUpdated;
    }

    private void setAssetClasses() {
        equity = new PortfolioDetailsVO();
        debt = new PortfolioDetailsVO();
        fno = new PortfolioDetailsVO();
        international = new PortfolioDetailsVO();
        gold = new PortfolioDetailsVO();
        mutualfund = new PortfolioDetailsVO();
        cash = new PortfolioDetailsVO();
        cash.setNewAllocation(100.0);
        index = new PortfolioDetailsVO();
        midcap = new PortfolioDetailsVO();
        equityDiversified = new PortfolioDetailsVO();
        balanced = new PortfolioDetailsVO();
        debtLiquid = new PortfolioDetailsVO();
        tax_planning = new PortfolioDetailsVO();
    }

    public PortfolioDetailsVO getTax_planning() {
        return tax_planning;
    }

    public void setTax_planning(PortfolioDetailsVO tax_planning) {
        this.tax_planning = tax_planning;
    }

    public List<PortfolioDetailsTb> toNewPortfolioDetailsTb(PortfolioTypeVO portfolioStyleSelected, Integer portfolioIndex, PortfolioTb portfolioTb) {
        List<PortfolioDetailsTb> newPortfolioDetailsTbs = new ArrayList<PortfolioDetailsTb>();

        //Cheking whether the given asset class needed allocation when creating porytfolio
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, EQUITY_DIVERSIFIED)) {
            //converting each PortfolioDetailsVO into portfolioDetailsTb
            PortfolioDetailsTb portfolioDetailsTb = equityDiversified.toPortfolioDetailsTb();
            //Setting asset class id and portfolioTB into portfolioDetailsTb.
            getPortfolioDetailsTb(portfolioDetailsTb, equityDiversified.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_INDEX)) {
            PortfolioDetailsTb portfolioDetailsTb = index.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, index.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, MIDCAP)) {
            PortfolioDetailsTb portfolioDetailsTb = midcap.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, midcap.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_INTERNATIONAL)) {
            PortfolioDetailsTb portfolioDetailsTb = international.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, international.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_GOLD)) {
            PortfolioDetailsTb portfolioDetailsTb = gold.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, gold.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_BALANCED)) {
            PortfolioDetailsTb portfolioDetailsTb = balanced.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, balanced.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_DEBT)) {
            PortfolioDetailsTb portfolioDetailsTb = debt.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, debt.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (!portfolioStyleSelected.getMasterPortfolioStyleTb().getPortfolioStyleId().equals(SIX)) {
            if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_FNO)) {
                PortfolioDetailsTb portfolioDetailsTb = fno.toPortfolioDetailsTb();
                getPortfolioDetailsTb(portfolioDetailsTb, fno.getAssetId(), portfolioTb);
                if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                    newPortfolioDetailsTbs.add(portfolioDetailsTb);
                }
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_DEBT_LIQUID)) {
            PortfolioDetailsTb portfolioDetailsTb = debtLiquid.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, debtLiquid.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TEXT_CASH)) {
            PortfolioDetailsTb portfolioDetailsTb = cash.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, cash.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        if (PortfolioAllocationChartUtil.isAssetForAllocation(portfolioStyleSelected, TAX_PLANNING)) {
            PortfolioDetailsTb portfolioDetailsTb = tax_planning.toPortfolioDetailsTb();
            getPortfolioDetailsTb(portfolioDetailsTb, tax_planning.getAssetId(), portfolioTb);
            if (portfolioDetailsTb.getPortfolioDetailsId() == null) {
                newPortfolioDetailsTbs.add(portfolioDetailsTb);
            }
        }
        return newPortfolioDetailsTbs;
    }

}
