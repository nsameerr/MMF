/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.\
 * created by 07662
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.MasterPortfolioSizeTb;
import com.gtl.mmf.entity.MasterPortfolioStyleTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;

public class PortfolioTypeVO {

    private int id;
    private String investorProfile;
    private String profileType;
    private int minScore;
    private int maxScore;
    private int cash;
    private int gold;
    private int debt;
    private int equitiesIndex;
    private int equitiesBlueChip;
    private int equitiesMidCap;
    private int equitiesSmallCap;
    private int fando;
    private int international;
    private int commodities;
    private int mutualFund;
    private int range_min_equity;
    private int range_min_cash;
    private int range_min_gold;
    private int range_min_debt;
    private int range_min_fo;
    private int range_min_international;
    private int range_max_equity;
    private int range_max_cash;
    private int range_max_gold;
    private int range_max_debt;
    private int range_max_fo;
    private int range_max_international;
    private int range_min_mutual_fund;
    private int range_max_mutual_fund;
    private int range_max_diversifiedEquity;
    private int range_min_diversifiedEquity;
    private int diversified_equity;
    private int midCap;
    private int range_min_midcap;
    private int range_max_midcap;
    private int balanced;
    private int range_min_balanced;
    private int range_max_balanced;
    private Integer debt_liquid;
    private Integer range_min_debt_liquid;
    private Integer range_max_debt_liquid;
    private Integer tax_planning;
    private Integer min_tax_planning;
    private Integer max_tax_planning;
    private MasterPortfolioSizeTb masterPortfolioSizeTb;
    private MasterPortfolioStyleTb masterPortfolioStyleTb;
    private String portfolioSize;
    private Integer minAum;
    private Integer maxAum;

    public PortfolioTypeVO() {
    }

    public PortfolioTypeVO(MasterPortfolioTypeTb masterPortfolioTypeTb) {
        this.id = masterPortfolioTypeTb.getId();
        this.investorProfile = masterPortfolioTypeTb.getMasterPortfolioStyleTb().getPortfolioStyle();
        this.profileType = masterPortfolioTypeTb.getMasterPortfolioStyleTb().getPortfolioStyleType();
        this.minScore = masterPortfolioTypeTb.getMasterPortfolioStyleTb().getMinScore();
        this.maxScore = masterPortfolioTypeTb.getMasterPortfolioStyleTb().getMaxScore();
        this.diversified_equity = masterPortfolioTypeTb.getEquityDiverisied();
        this.midCap = masterPortfolioTypeTb.getMidcap();
        this.equitiesIndex = masterPortfolioTypeTb.getEquitiesIndex();
        this.cash = masterPortfolioTypeTb.getCash();
        this.gold = masterPortfolioTypeTb.getGold();
        this.debt = masterPortfolioTypeTb.getDebt();
        this.equitiesBlueChip = masterPortfolioTypeTb.getEquitiesBluechip();
        this.equitiesMidCap = masterPortfolioTypeTb.getEquitiesMidcap();
        this.equitiesSmallCap = masterPortfolioTypeTb.getEquitiesSmallCap();
        this.fando = masterPortfolioTypeTb.getFando();
        this.international = masterPortfolioTypeTb.getInternational();
        this.commodities = masterPortfolioTypeTb.getCommodities();
        this.range_min_equity = masterPortfolioTypeTb.getRangeMinEquity();
        this.range_min_cash = masterPortfolioTypeTb.getRangeMinCash();
        this.range_min_gold = masterPortfolioTypeTb.getRangeMinGold();
        this.range_min_debt = masterPortfolioTypeTb.getRangeMinDebt();
        this.range_min_fo = masterPortfolioTypeTb.getRangeMinFo();
        this.range_min_international = masterPortfolioTypeTb.getRangeMinInternational();
        this.range_max_equity = masterPortfolioTypeTb.getRangeMaxEquity();
        this.range_max_cash = masterPortfolioTypeTb.getRangeMaxCash();
        this.range_max_gold = masterPortfolioTypeTb.getRangeMaxGold();
        this.range_max_debt = masterPortfolioTypeTb.getRangeMaxDebt();
        this.range_max_fo = masterPortfolioTypeTb.getRangeMaxFo();
        this.range_max_international = masterPortfolioTypeTb.getRangeMaxInternational();
        this.range_max_diversifiedEquity = masterPortfolioTypeTb.getRangeMaxEquityDiverisied();
        this.range_min_diversifiedEquity = masterPortfolioTypeTb.getRangeMinEquityDiverisied();
        this.range_min_midcap = masterPortfolioTypeTb.getRangeMinMicap();
        this.range_max_midcap = masterPortfolioTypeTb.getRangeMaxMidcap();
        this.mutualFund = masterPortfolioTypeTb.getMutualFunds();
        this.range_min_mutual_fund = masterPortfolioTypeTb.getRangeMinMutualFunds();
        this.range_max_mutual_fund = masterPortfolioTypeTb.getRangeMaxMutualFunds();
        this.balanced = masterPortfolioTypeTb.getBalanced();
        this.range_min_balanced = masterPortfolioTypeTb.getRangeMinBalanced();
        this.range_max_balanced = masterPortfolioTypeTb.getRangeMaxBalanced();
        this.debt_liquid = masterPortfolioTypeTb.getDebtLiquid();
        this.range_min_debt_liquid = masterPortfolioTypeTb.getRangeMinDebtLiquid();
        this.range_max_debt_liquid = masterPortfolioTypeTb.getRangeMaxDebtLiquid();
        this.masterPortfolioSizeTb = masterPortfolioTypeTb.getMasterPortfolioSizeTb();
        this.masterPortfolioStyleTb = masterPortfolioTypeTb.getMasterPortfolioStyleTb();
        this.portfolioSize = masterPortfolioTypeTb.getMasterPortfolioSizeTb().getPortfolioSize();
        this.minAum = masterPortfolioTypeTb.getMasterPortfolioSizeTb().getMinAum();
        this.maxAum = masterPortfolioTypeTb.getMasterPortfolioSizeTb().getMaxAum();
        this.tax_planning = masterPortfolioTypeTb.getTax_planning();
        this.min_tax_planning = masterPortfolioTypeTb.getMin_tax_planning();
        this.max_tax_planning = masterPortfolioTypeTb.getMax_tax_planning();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvestorProfile() {
        return investorProfile;
    }

    public void setInvestorProfile(String investorProfile) {
        this.investorProfile = investorProfile;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getEquitiesIndex() {
        return equitiesIndex;
    }

    public void setEquitiesIndex(int equitiesIndex) {
        this.equitiesIndex = equitiesIndex;
    }

    public int getEquitiesBlueChip() {
        return equitiesBlueChip;
    }

    public void setEquitiesBlueChip(int equitiesBlueChip) {
        this.equitiesBlueChip = equitiesBlueChip;
    }

    public int getEquitiesMidCap() {
        return equitiesMidCap;
    }

    public void setEquitiesMidCap(int equitiesMidCap) {
        this.equitiesMidCap = equitiesMidCap;
    }

    public int getEquitiesSmallCap() {
        return equitiesSmallCap;
    }

    public void setEquitiesSmallCap(int equitiesSmallCap) {
        this.equitiesSmallCap = equitiesSmallCap;
    }

    public int getFando() {
        return fando;
    }

    public void setFando(int fando) {
        this.fando = fando;
    }

    public int getInternational() {
        return international;
    }

    public void setInternational(int international) {
        this.international = international;
    }

    public int getCommodities() {
        return commodities;
    }

    public void setCommodities(int commodities) {
        this.commodities = commodities;
    }

    public int getRange_min_equity() {
        return range_min_equity;
    }

    public void setRange_min_equity(int range_min_equity) {
        this.range_min_equity = range_min_equity;
    }

    public int getRange_min_cash() {
        return range_min_cash;
    }

    public void setRange_min_cash(int range_min_cash) {
        this.range_min_cash = range_min_cash;
    }

    public int getRange_min_gold() {
        return range_min_gold;
    }

    public void setRange_min_gold(int range_min_gold) {
        this.range_min_gold = range_min_gold;
    }

    public int getRange_min_debt() {
        return range_min_debt;
    }

    public void setRange_min_debt(int range_min_debt) {
        this.range_min_debt = range_min_debt;
    }

    public int getRange_min_fo() {
        return range_min_fo;
    }

    public void setRange_min_fo(int range_min_fo) {
        this.range_min_fo = range_min_fo;
    }

    public int getRange_min_international() {
        return range_min_international;
    }

    public void setRange_min_international(int range_min_international) {
        this.range_min_international = range_min_international;
    }

    public int getRange_max_equity() {
        return range_max_equity;
    }

    public void setRange_max_equity(int range_max_equity) {
        this.range_max_equity = range_max_equity;
    }

    public int getRange_max_cash() {
        return range_max_cash;
    }

    public void setRange_max_cash(int range_max_cash) {
        this.range_max_cash = range_max_cash;
    }

    public int getRange_max_gold() {
        return range_max_gold;
    }

    public void setRange_max_gold(int range_max_gold) {
        this.range_max_gold = range_max_gold;
    }

    public int getRange_max_debt() {
        return range_max_debt;
    }

    public void setRange_max_debt(int range_max_debt) {
        this.range_max_debt = range_max_debt;
    }

    public int getRange_max_fo() {
        return range_max_fo;
    }

    public void setRange_max_fo(int range_max_fo) {
        this.range_max_fo = range_max_fo;
    }

    public int getRange_max_international() {
        return range_max_international;
    }

    public void setRange_max_international(int range_max_international) {
        this.range_max_international = range_max_international;
    }

    public int getMutualFund() {
        return mutualFund;
    }

    public void setMutualFund(int mutualFund) {
        this.mutualFund = mutualFund;
    }

    public int getRange_min_mutual_fund() {
        return range_min_mutual_fund;
    }

    public void setRange_min_mutual_fund(int range_min_mutual_fund) {
        this.range_min_mutual_fund = range_min_mutual_fund;
    }

    public int getRange_max_mutual_fund() {
        return range_max_mutual_fund;
    }

    public void setRange_max_mutual_fund(int range_max_mutual_fund) {
        this.range_max_mutual_fund = range_max_mutual_fund;
    }

    public int getRange_max_diversifiedEquity() {
        return range_max_diversifiedEquity;
    }

    public void setRange_max_diversifiedEquity(int range_max_diversifiedEquity) {
        this.range_max_diversifiedEquity = range_max_diversifiedEquity;
    }

    public int getRange_min_diversifiedEquity() {
        return range_min_diversifiedEquity;
    }

    public void setRange_min_diversifiedEquity(int range_min_diversifiedEquity) {
        this.range_min_diversifiedEquity = range_min_diversifiedEquity;
    }

    public int getDiversified_equity() {
        return diversified_equity;
    }

    public void setDiversified_equity(int diversified_equity) {
        this.diversified_equity = diversified_equity;
    }

    public int getMidCap() {
        return midCap;
    }

    public void setMidCap(int midCap) {
        this.midCap = midCap;
    }

    public int getRange_min_midcap() {
        return range_min_midcap;
    }

    public void setRange_min_midcap(int range_min_midcap) {
        this.range_min_midcap = range_min_midcap;
    }

    public int getRange_max_midcap() {
        return range_max_midcap;
    }

    public void setRange_max_midcap(int range_max_midcap) {
        this.range_max_midcap = range_max_midcap;
    }

    public int getBalanced() {
        return balanced;
    }

    public void setBalanced(int balanced) {
        this.balanced = balanced;
    }

    public int getRange_min_balanced() {
        return range_min_balanced;
    }

    public void setRange_min_balanced(int range_min_balanced) {
        this.range_min_balanced = range_min_balanced;
    }

    public int getRange_max_balanced() {
        return range_max_balanced;
    }

    public void setRange_max_balanced(int range_max_balanced) {
        this.range_max_balanced = range_max_balanced;
    }

    public Integer getDebt_liquid() {
        return debt_liquid;
    }

    public void setDebt_liquid(Integer debt_liquid) {
        this.debt_liquid = debt_liquid;
    }

    public Integer getRange_min_debt_liquid() {
        return range_min_debt_liquid;
    }

    public void setRange_min_debt_liquid(Integer range_min_debt_liquid) {
        this.range_min_debt_liquid = range_min_debt_liquid;
    }

    public Integer getRange_max_debt_liquid() {
        return range_max_debt_liquid;
    }

    public void setRange_max_debt_liquid(Integer range_max_debt_liquid) {
        this.range_max_debt_liquid = range_max_debt_liquid;
    }
    
    public MasterPortfolioSizeTb getMasterPortfolioSizeTb() {
        return masterPortfolioSizeTb;
    }

    public void setMasterPortfolioSizeTb(MasterPortfolioSizeTb masterPortfolioSizeTb) {
        this.masterPortfolioSizeTb = masterPortfolioSizeTb;
    }

    public MasterPortfolioStyleTb getMasterPortfolioStyleTb() {
        return masterPortfolioStyleTb;
    }

    public void setMasterPortfolioStyleTb(MasterPortfolioStyleTb masterPortfolioStyleTb) {
        this.masterPortfolioStyleTb = masterPortfolioStyleTb;
    }

    public String getPortfolioSize() {
        return portfolioSize;
    }

    public void setPortfolioSize(String portfolioSize) {
        this.portfolioSize = portfolioSize;
    }

    public Integer getMinAum() {
        return minAum;
    }

    public void setMinAum(Integer minAum) {
        this.minAum = minAum;
    }

    public Integer getMaxAum() {
        return maxAum;
    }

    public void setMaxAum(Integer maxAum) {
        this.maxAum = maxAum;
    }

    public Integer getTax_planning() {
        return tax_planning;
    }

    public void setTax_planning(Integer tax_planning) {
        this.tax_planning = tax_planning;
    }

    public Integer getMin_tax_planning() {
        return min_tax_planning;
    }

    public void setMin_tax_planning(Integer min_tax_planning) {
        this.min_tax_planning = min_tax_planning;
    }

    public Integer getMax_tax_planning() {
        return max_tax_planning;
    }

    public void setMax_tax_planning(Integer max_tax_planning) {
        this.max_tax_planning = max_tax_planning;
    }

    
}
