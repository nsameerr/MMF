/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao.impl;

import com.git.mds.common.InvalidData;
import com.git.mds.common.MDSMsg;
import com.gtl.mmf.bao.IInvestmentPolicyStmtBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.dao.IInvestmentPolicyStmtDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterBenchmarkIndexTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MasterPortfolioSizeTb;
import com.gtl.mmf.entity.MasterPortfolioStyleTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.entity.QuestionmasterTb;
import com.gtl.mmf.entity.QuestionoptionsmasterTb;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.InvestorNotificationMsgService;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.MarketDataUtil;
import com.gtl.mmf.service.util.NumberRoundUtil;
import com.gtl.mmf.service.util.StringToListConverter;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.CustomerRiskProfileVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.service.vo.QuestionResponseVO;
import com.gtl.mmf.util.MMFException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class InvestmentPolicyStmtBAOImpl implements IInvestmentPolicyStmtBAO, IConstants {

    private static final String CLOSE_INDEX = "CLOSE_INDEX";
    private Set customerPortfolioSecurities = new HashSet();
    List<CustomerPortfolioSecuritiesTb> oldCustomerPortfolioSecuritiesTbList = new ArrayList<CustomerPortfolioSecuritiesTb>();
    Set<CustomerPortfolioSecuritiesTb> oldCustomerPortfolioSecuritiesTbset = new HashSet<CustomerPortfolioSecuritiesTb>();
    List<CustomerPortfolioSecuritiesTb> newCustomerPortfolioSecuritiesTbList = new ArrayList<CustomerPortfolioSecuritiesTb>();

    @Autowired
    private IInvestmentPolicyStmtDAO investmentPolicyStmtDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getPageDetails(int customerId, List<QuestionResponseVO> questionResponseVOList,
            CustomerRiskProfileVO customerRiskProfileVO) {
        List<Object> investmentPolicyStmt = investmentPolicyStmtDAO.getPagedetails(customerId);
        List<Object[]> responseQuestionList = (List<Object[]>) investmentPolicyStmt.get(ZERO);
        List<CustomerRiskProfileTb> customerRiskProfileTblist = (List<CustomerRiskProfileTb>) investmentPolicyStmt.get(ONE);
        System.out.println("no of customerRiskProfileTb object " + customerRiskProfileTblist.size());
        Integer n = customerRiskProfileTblist.size();
        CustomerRiskProfileTb customerRiskProfileTb = (CustomerRiskProfileTb) customerRiskProfileTblist.get(n-1);
        for (Object[] item : responseQuestionList) {
            QuestionmasterTb questionmasterTb = (QuestionmasterTb) item[ZERO];
            QuestionoptionsmasterTb questionoptionsmasterTb = (QuestionoptionsmasterTb) item[ONE];
            questionResponseVOList.add(new QuestionResponseVO(questionmasterTb.getId(),
                    questionmasterTb.getQuestion(),
                    questionoptionsmasterTb.getId(),
                    questionoptionsmasterTb.getQOption(), questionmasterTb.getImgPath()));
        }
        customerRiskProfileVO.setRiskProfileId(customerRiskProfileTb.getRiskProfileId());
        customerRiskProfileVO.setRelationId(customerRiskProfileTb.getCustomerAdvisorMappingTb()
                .getRelationId());
        customerRiskProfileVO.setRiskScore(customerRiskProfileTb.getRiskScore() == null ? ZERO
                : customerRiskProfileTb.getRiskScore());
        customerRiskProfileVO.setExpReturn(customerRiskProfileTb.getExpReturn() == null ? ZERO
                : customerRiskProfileTb.getExpReturn());
        customerRiskProfileVO.setBenchMark(customerRiskProfileTb.getMasterBenchmarkIndexTb() == null
                ? ZERO : customerRiskProfileTb.getMasterBenchmarkIndexTb().getId());
        customerRiskProfileVO.setInvestHorizon(customerRiskProfileTb.getInvestHorizon() == null
                ? ZERO : customerRiskProfileTb.getInvestHorizon());
        customerRiskProfileVO.setLiquidityRetd(customerRiskProfileTb.getLiquidityReqd() == null
                ? ZERO : customerRiskProfileTb.getLiquidityReqd());
        customerRiskProfileVO.setIncomeRetd(customerRiskProfileTb.getIncomeReqd() == null ? ZERO
                : customerRiskProfileTb.getIncomeReqd());
        customerRiskProfileVO.setPortfolioStyle(customerRiskProfileTb.getMasterPortfolioStyleTbByPortfolioStyle().getPortfolioStyleId());
        // To display initial portfoliostyle
//        customerRiskProfileVO.setPortfolioStyle(customerRiskProfileTb.getMasterPortfolioStyleTbByInitialPortfolioStyle().getPortfolioStyleId());
        customerRiskProfileVO.setPortfolioSize(customerRiskProfileTb.getMasterPortfolioSizeTbByPortfolioSize()
                .getPortfolioSizeId());
        customerRiskProfileVO.setPortfolioType(customerRiskProfileTb.getMasterPortfolioTypeTb()
                .getId());
    }

    /**
     * Saving investment policy created by advisor based on customer risk
     * profile score.
     *
     * @param customerRiskProfileVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveInvestmentPolicy(CustomerRiskProfileVO customerRiskProfileVO) {

        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationId(customerRiskProfileVO.getRelationId());
        customerAdvisorMappingTb.setRelationStatus((short) EnumAdvisorMappingStatus.IPS_CREATED.getValue());
        customerAdvisorMappingTb.setReviewFreq(customerRiskProfileVO.getReviewFreequency());

        //Updateing status date while saving ploicy statement
        customerAdvisorMappingTb.setStatusDate(new Date());

        MasterBenchmarkIndexTb masterBenchmarkIndexTb = new MasterBenchmarkIndexTb();
        masterBenchmarkIndexTb.setId(customerRiskProfileVO.getBenchMark());

        MasterPortfolioTypeTb masterPortfolioTypeTb = new MasterPortfolioTypeTb();
        masterPortfolioTypeTb.setId(customerRiskProfileVO.getPortfolioType());
        MasterPortfolioSizeTb masterPortfolioSizeTb = new MasterPortfolioSizeTb();
        masterPortfolioSizeTb.setPortfolioSizeId(customerRiskProfileVO.getPortfolioSize());
        MasterPortfolioStyleTb masterPortfolioStyleTb = new MasterPortfolioStyleTb();
        masterPortfolioStyleTb.setPortfolioStyleId(customerRiskProfileVO.getPortfolioStyle());
        CustomerRiskProfileTb customerRiskProfileTb = new CustomerRiskProfileTb();
        customerRiskProfileTb.setRiskProfileId(customerRiskProfileVO.getRiskProfileId());
        customerRiskProfileTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
        customerRiskProfileTb.setExpReturn(customerRiskProfileVO.getExpReturn());
        customerRiskProfileTb.setMasterBenchmarkIndexTb(masterBenchmarkIndexTb);
        customerRiskProfileTb.setInvestHorizon(customerRiskProfileVO.getInvestHorizon());
        customerRiskProfileTb.setLiquidityReqd(customerRiskProfileVO.getLiquidityRetd());
        customerRiskProfileTb.setIncomeReqd(customerRiskProfileVO.getIncomeRetd());
        customerRiskProfileTb.setMasterPortfolioTypeTb(masterPortfolioTypeTb);
        customerRiskProfileTb.setMasterPortfolioStyleTbByPortfolioStyle(masterPortfolioStyleTb);
        customerRiskProfileTb.setMasterPortfolioSizeTbByPortfolioSize(masterPortfolioSizeTb);
        investmentPolicyStmtDAO.saveInvestmentPolicy(customerRiskProfileTb, customerAdvisorMappingTb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getPortfolioAssignPageDetails(int relationId, CustomerRiskProfileVO customerRiskProfileVO,
            Map<Integer, PortfolioVO> portfoliosMap, List<PieChartDataVO> pieChartDataVOList,
            CustomerPortfolioVO customerPortfolioVO, List<PortfolioSecurityVO> securityVo) {

        List<Object> responseitems = investmentPolicyStmtDAO.getPortfolioAssignPageDetails(relationId);
        generateResponseIPS(responseitems, customerRiskProfileVO, portfoliosMap, pieChartDataVOList,
                customerPortfolioVO, securityVo);
    }

    /**
     * Changing pie chart based on portfolio selected by advisor
     *
     * @param portfolioId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<PieChartDataVO> refreshPieChartData(int portfolioId) {
        List<PieChartDataVO> pieChartDataVOList = new ArrayList<PieChartDataVO>();
        PortfolioTb portfolioTb = new PortfolioTb();
        portfolioTb.setPortfolioId(portfolioId);
        List<PortfolioDetailsTb> portfolioDetailsTbList = investmentPolicyStmtDAO
                .getPortfolioPieCharDetails(portfolioTb);
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbList) {
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setLabel(portfolioDetailsTb.getMasterAssetTb().getAssetName());
            pieChartDataVO.setData(NumberRoundUtil.roundTwoDecimalValue(portfolioDetailsTb
                    .getNewAllocation().doubleValue()));
            pieChartDataVO.setColor(portfolioDetailsTb.getMasterAssetTb().getAssetColor());
            pieChartDataVOList.add(pieChartDataVO);
        }
        return pieChartDataVOList;
    }

    /**
     * Assigning IPS created by advisor to investor
     *
     * @param customerPortfolioVO
     * @param investorDetails
     * @param advisorName
     * @param isPortfolioCahnged
     * @throws MMFException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveAssignPortFolio(CustomerPortfolioVO customerPortfolioVO, InvestorDetailsVO investorDetails,
            String advisorName, Boolean isPortfolioCahnged) throws MMFException {
        /*
         * AOP method will call before call this method (CustomerPortfolioAuditBAOImpl)
         */
        try {
            boolean is_customer_portfolio = false;
            is_customer_portfolio = investmentPolicyStmtDAO.getCustomerDetails(customerPortfolioVO.getCustomerId());
            CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
            CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
            MasterAdvisorTb masterAdvisorTb = new MasterAdvisorTb();
            MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
            PortfolioTb portfolioTb = new PortfolioTb();
            customerAdvisorMappingTb.setRelationId(customerPortfolioVO.getRelationId());
            customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.IPS_SHARED.getValue());

            //Updating Status date
            customerAdvisorMappingTb.setStatusDate(new Date());
            masterAdvisorTb.setAdvisorId(customerPortfolioVO.getAdvisorId());
            masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());
            portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
            customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
            if (customerPortfolioVO.getCustomerPortfolioId() == ZERO) {
                customerPortfolioTb.setNoRebalanceStatus(Boolean.TRUE);
            }
            customerPortfolioTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
            customerPortfolioTb.setMasterAdvisorTb(masterAdvisorTb);
            customerPortfolioTb.setMasterCustomerTb(masterCustomerTb);
            customerPortfolioTb.setPortfolioTb(portfolioTb);
            if (customerPortfolioVO.getAdvisorComment().equalsIgnoreCase(EMPTY_STRING)) {
                customerPortfolioTb.setAdviserComment(customerPortfolioVO.getAdvisorPrevoisComments());
            } else {
                customerPortfolioTb.setAdviserComment(customerPortfolioVO.getAdvisorPrevoisComments()
                        + customerPortfolioVO.getUserName() + COLON
                        + customerPortfolioVO.getAdvisorComment() + NUM_SEQUENCE
                        + (customerPortfolioVO.getCommentFreq() + ONE)
                        + REVIEW_SEPERATOR);
            }
            customerPortfolioTb.setCommentFreq(customerPortfolioVO.getCommentFreq() + ONE);
            customerPortfolioTb.setLastUpdated(new Date());

            //Rebalancing is not setting when assigning portfolio
            customerPortfolioTb.setStatus(Boolean.TRUE);
            customerPortfolioTb.setCashAmount(customerPortfolioVO.getAlocatedFunds().floatValue());
            String venuName = customerPortfolioVO.getBenchmarkName().equalsIgnoreCase("NIFTY 50") ? NSE : BSE;
            MDSMsg mdsResponse = MarketDataUtil.getBenchmarkDetails(venuName);
            Double current_index = ZEROD;
            if (mdsResponse != null) {
                current_index = mdsResponse.getField(CLOSE_INDEX) == null ? ZERO
                        : mdsResponse.getField(CLOSE_INDEX).doubleValue();
                if (current_index > 0) {
                    double benchmarkUnit = customerPortfolioVO.getAlocatedFunds() / current_index;
                    customerPortfolioTb.setBenchmarkUnit(new BigDecimal(benchmarkUnit));
                } else {
                    customerPortfolioTb.setBenchmarkUnit(new BigDecimal(ZEROD));
                }
            }
            customerPortfolioTb.setBenchmarkStartValue(BigDecimal.valueOf(current_index));
            customerPortfolioTb.setAllocatedFund(customerPortfolioVO.getAlocatedFunds());
            customerPortfolioTb.setAdvisorPortfolioStartValue(customerPortfolioVO
                    .getAdvisorPortfolioStartValue());
            customerPortfolioTb.setOmsLoginId(customerPortfolioVO.getOmsLoginId() == null ? EMPTY_STRING
                    : customerPortfolioVO.getOmsLoginId());
            customerPortfolioTb.setPortfolioValue(customerPortfolioVO.getAlocatedFunds().floatValue());
            customerPortfolioTb.setVenueName(venuName);
            customerPortfolioTb.setCashFlowDflag(false);
            customerPortfolioTb.setPortfolioStatus(ACTIVE);
            //For contract terminated users
            if(is_customer_portfolio){
               CustomerPortfolioTb existing_portfolio = investmentPolicyStmtDAO.getOldPortfolioDetails(customerPortfolioVO.getCustomerId());
               if(existing_portfolio != null){
                   customerPortfolioTb = transferDetailsToNewPortfolio(existing_portfolio,customerPortfolioTb);
               }
            }
            investmentPolicyStmtDAO.saveAssignPortfolio(customerPortfolioTb, isPortfolioCahnged,customerPortfolioVO.getCustomerId());
            String notificationMsg = InvestorNotificationMsgService.getMessage(true,
                    EnumAdvisorMappingStatus.IPS_SHARED.getValue(), advisorName);
            sendMail(investorDetails.getFirstName(), investorDetails.getEmail(), notificationMsg);
        } catch (InvalidData ex) {
            throw new MMFException("Invalid data", ex);
        } catch (ClassNotFoundException ex) {
            throw new MMFException("No class found", ex);
        } catch (NullPointerException ex) {
            throw new MMFException("Null pointer exception", ex);
        }
    }

    /**
     * Loading details to show IPS to investor
     *
     * @param relationId
     * @param customerRiskProfileVO
     * @param portfoliosMap
     * @param pieChartDataVOList
     * @param customerPortfolioVO
     * @param securityVo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void customerViewIPS(int relationId, CustomerRiskProfileVO customerRiskProfileVO, Map<Integer, PortfolioVO> portfoliosMap,
            List<PieChartDataVO> pieChartDataVOList, CustomerPortfolioVO customerPortfolioVO, List<PortfolioSecurityVO> securityVo) {

        List<Object> responseitems = investmentPolicyStmtDAO.customerIPSView(relationId);
        generateResponseIPS(responseitems, customerRiskProfileVO, portfoliosMap, pieChartDataVOList, customerPortfolioVO, securityVo);
    }

    /**
     * Generating IPS details
     *
     * @param responseitems
     * @param customerRiskProfileVO
     * @param portfoliosMap
     * @param pieChartDataVOList
     * @param customerPortfolioVO
     */
    private void generateResponseIPS(List<Object> responseitems, CustomerRiskProfileVO customerRiskProfileVO,
            Map<Integer, PortfolioVO> portfoliosMap, List<PieChartDataVO> pieChartDataVOList,
            CustomerPortfolioVO customerPortfolioVO, List<PortfolioSecurityVO> securityVoList) {

        Object[] riskProfile = (Object[]) responseitems.get(ZERO);
        customerRiskProfileVO.setRiskProfileId(Integer.parseInt(riskProfile[ZERO].toString()));
        customerRiskProfileVO.setRelationId(Integer.parseInt(riskProfile[ONE].toString()));
        customerRiskProfileVO.setRiskScore(riskProfile[2] == null ? ZERO
                : Integer.parseInt(riskProfile[2].toString()));
        customerRiskProfileVO.setExpReturn(riskProfile[3] == null ? ZERO
                : Float.parseFloat(riskProfile[3].toString()));
        customerRiskProfileVO.setBenchMark(riskProfile[4] == null
                ? ZERO : Integer.parseInt(riskProfile[4].toString()));
        customerRiskProfileVO.setInvestHorizon(riskProfile[5] == null ? ZERO
                : Integer.parseInt(riskProfile[5].toString()));
        customerRiskProfileVO.setLiquidityRetd(riskProfile[6] == null ? ZERO
                : Float.parseFloat(riskProfile[6].toString()));
        customerRiskProfileVO.setIncomeRetd(riskProfile[7] == null ? ZERO
                : Float.parseFloat(riskProfile[7].toString()));
        customerRiskProfileVO.setPortfolioType(Integer.parseInt(riskProfile[8].toString()));
        customerRiskProfileVO.setBenchMark_text((String) (riskProfile[9] == null ? ZERO : riskProfile[9]));
        customerRiskProfileVO.setPortfolioType_text((String) (riskProfile[10] == null ? ZERO : riskProfile[10]));
        customerRiskProfileVO.setPortfolioStyle_text((String) (riskProfile[11] == null ? ZERO
                : riskProfile[11]));
        customerRiskProfileVO.setReviewFreequency_text((String) (riskProfile[12] == null ? ZERO
                : riskProfile[12]));
        customerRiskProfileVO.setPortfolioSize(Integer.parseInt(riskProfile[23].toString()));
        PortfolioTypeVO portfolioTypeVO = LookupDataLoader.getPortfolioTypes().get(Integer.parseInt(riskProfile[8].toString()));
        customerRiskProfileVO.setPortfolioStyleOpted(portfolioTypeVO.getInvestorProfile());
        customerRiskProfileVO.setPortfolioStyle(Integer.parseInt(riskProfile[24].toString()));
        List<Object> portfolio = (List<Object>) responseitems.get(ONE);
        for (Iterator<Object> it = portfolio.iterator(); it.hasNext();) {
            Object[] object = (Object[]) it.next();
            PortfolioVO portfolioVO = new PortfolioVO();
            if (customerRiskProfileVO.getPortfolioType() == Integer
                    .parseInt(object[2].toString())) {
                portfolioVO.setPortfolioId(Integer.parseInt(object[ZERO]
                        .toString()));
                portfolioVO.setPortfolioName(object[ONE].toString());
                portfolioVO.setPortfolioTypeSelectedKey(Integer
                        .parseInt(object[2].toString()));
                portfolioVO.setPortfolioValue(BigDecimal.valueOf(Double
                        .parseDouble(object[3].toString())));
                portfoliosMap.put(Integer.parseInt(object[ZERO].toString()),
                        portfolioVO);
            }
        }

        customerPortfolioVO.setBenchmarkName((String) (riskProfile[9] == null ? ZERO : riskProfile[9]));
        customerPortfolioVO.setRelationId(riskProfile[13] == null ? ZERO : Integer.parseInt(riskProfile[13]
                .toString()));
        customerPortfolioVO.setCustomerId(riskProfile[14] == null ? ZERO : Integer.parseInt(riskProfile[14].toString()));
        customerPortfolioVO.setAdvisorId(riskProfile[15] == null ? ZERO : Integer.parseInt(riskProfile[15]
                .toString()));
        customerPortfolioVO.setAdvisorPrevoisComments(riskProfile[16] == null ? EMPTY_STRING : riskProfile[16]
                .toString());
        customerPortfolioVO.setCustomerpreviouComments(riskProfile[17] == null ? EMPTY_STRING : riskProfile[17]
                .toString());
        String prevComment = customerPortfolioVO.getAdvisorPrevoisComments() + customerPortfolioVO
                .getCustomerpreviouComments();
        customerPortfolioVO.setCustomerPortfolioId(riskProfile[18] == null ? ZERO
                : Integer.parseInt(riskProfile[18].toString()));
        customerPortfolioVO.setCommentFreq(riskProfile[19] == null ? ZERO
                : Integer.parseInt(riskProfile[19].toString()));
        customerPortfolioVO.setPortfolioId(riskProfile[20] == null ? 0
                : Integer.parseInt(riskProfile[20].toString()));
        customerPortfolioVO.setPortfolioIdBeforeChange(riskProfile[20] == null
                ? ZERO : Integer.parseInt(riskProfile[20].toString()));
        customerPortfolioVO.setPreviousReviews(StringToListConverter.
                toStringList(prevComment, REVIEW_SEPERATOR, NUM_SEQUENCE));
        customerPortfolioVO.setAlocatedFunds(Double.parseDouble(riskProfile[21].toString()));
        customerPortfolioVO.setOmsLoginId(riskProfile[22] == null
                ? EMPTY_STRING : riskProfile[22].toString());

        PortfolioTb portfolioTb = new PortfolioTb();
        portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
        List<PortfolioDetailsTb> portfolioDetailsTbList = investmentPolicyStmtDAO.getPortfolioPieCharDetails(portfolioTb);
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbList) {
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setLabel(portfolioDetailsTb.getMasterAssetTb().getAssetName());
            pieChartDataVO.setData(NumberRoundUtil.roundTwoDecimalValue(portfolioDetailsTb
                    .getNewAllocation().doubleValue()));
            pieChartDataVO.setColor(portfolioDetailsTb.getMasterAssetTb().getAssetColor());
            pieChartDataVOList.add(pieChartDataVO);
            Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();
            for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
                PortfolioSecurityVO securityVO = new PortfolioSecurityVO();
                securityVO.setSecurityCode(portfolioSecuritiesTb.getSecurityCode());
                securityVO.setAssetClass(portfolioDetailsTb.getMasterAssetTb().getAssetName());
                securityVO.setNewTolerancePositiveRange(portfolioSecuritiesTb.getNewTolerancePositiveRange());
                securityVoList.add(securityVO);
            }
        }
    }

    /**
     * Investor is asking for a review for the IPS relation status is setting to
     * IPS_REVIEWED
     *
     * @param customerPortfolioVO - portfolio allocated ti investor
     * @param advisorDetailsVO - advisor details
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void customerReviewIPS(CustomerPortfolioVO customerPortfolioVO, AdvisorDetailsVO advisorDetailsVO,
            String investorName) throws ClassNotFoundException {
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
        customerPortfolioTb.setCustomerComment(customerPortfolioVO.getCustomerpreviouComments()
                + customerPortfolioVO.getUserName() + COLON
                + customerPortfolioVO.getCustomerComment() + NUM_SEQUENCE + (customerPortfolioVO
                .getCommentFreq() + ONE)
                + REVIEW_SEPERATOR);
        customerPortfolioTb.setCommentFreq(customerPortfolioVO.getCommentFreq() + ONE);
        customerPortfolioTb.setLastUpdated(new Date());
        customerAdvisorMappingTb.setRelationId(customerPortfolioVO.getRelationId());
        customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.IPS_REVIEWED.getValue());

        //updating status date when reviewing investment policy
        customerAdvisorMappingTb.setStatusDate(new Date());
        customerPortfolioTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);

        //Customer IPS review request is saving
        investmentPolicyStmtDAO.customerReviewIPS(customerPortfolioTb);
        String notificationMsg = InvestorNotificationMsgService.getAdvNotificationMessage(true,
                EnumAdvisorMappingStatus.IPS_REVIEWED.getValue(), investorName);
        sendMail(advisorDetailsVO.getFirstName(), advisorDetailsVO.getEmail(), notificationMsg);
    }

    /**
     * Investor is accepting IPS and relation status is changing IPS ACCEPTED
     *
     * @param customerPortfolioVO - portfolio allocated ti investor
     * @param advisorDetailsVO - advisor details
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void customerAcceptIPS(CustomerPortfolioVO customerPortfolioVO, AdvisorDetailsVO advisorDetailsVO,
            String investorName) throws ClassNotFoundException {
        boolean is_customerID = false;
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        PortfolioTb portfolioTb = new PortfolioTb();
        portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
        customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
        customerAdvisorMappingTb.setRelationId(customerPortfolioVO.getRelationId());
        customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.IPS_ACCEPTED.getValue());
        customerAdvisorMappingTb.setStatusDate(new Date());
        customerPortfolioTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
        customerPortfolioTb.setLastUpdated(new Date());
        customerPortfolioTb.setPortfolioTb(portfolioTb);
        CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb = new CustomerPortfolioSecuritiesTb();
        //portfolio assigned date is setting
        customerPortfolioTb.setPortfolioAssigned(new Date());

        //Rebalancing is not setting when assigning portfolio
        customerPortfolioTb.setRebalanceRequired(Boolean.TRUE);
        customerPortfolioTb.setRebalanceReqdDate(new Date());
        customerPortfolioTb.setNoRebalanceStatus(Boolean.TRUE);
        //investor acepting investment policy statement
        is_customerID = investmentPolicyStmtDAO.getCustomerDetails(customerPortfolioVO.getCustomerId());
        if (is_customerID) {
            investmentPolicyStmtDAO.customerAcceptIPS(customerPortfolioTb);
            List<CustomerPortfolioSecuritiesTb> seuritylist = getSecurityTableDetailsOfCustomer(customerPortfolioVO, customerPortfolioTb);
           // Set<CustomerPortfolioSecuritiesTb> security = new HashSet<CustomerPortfolioSecuritiesTb>(seuritylist);
           // customerPortfolioTb.setCustomerPortfolioSecuritiesTbs(security);
            //investmentPolicyStmtDAO.customerAcceptIPS(customerPortfolioTb);
            System.out.println("START UPDATE SECURITIES FOR TERMINATED CUSTOMERS!!!!!!!!!!!!!!!!");
            investmentPolicyStmtDAO.updateSecuritiesForCustomer(seuritylist);
            System.out.println("UPDATE SECURITIES FOR TERMINATED CUSTOMERS COMPLETED!!!!!!!!!!!!!!!!");
        } else {
            investmentPolicyStmtDAO.customerAcceptIPS(customerPortfolioTb);
        }

        String notificationMsg = InvestorNotificationMsgService.getAdvNotificationMessage(true,
                EnumAdvisorMappingStatus.IPS_ACCEPTED.getValue(), investorName);
        sendMail(advisorDetailsVO.getFirstName(), advisorDetailsVO.getEmail(), notificationMsg);
        //For copy holdings to new portfolio the investor who create after terminate contract 

    }

    private void sendMail(String firstName, String email, String message) throws ClassNotFoundException {
        MailUtil.getInstance().sendNotificationMail(firstName, email, message);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Integer getPortfolioByStyleAndSize(Integer portfolioStyleId, Integer portfolioSizeId) {
        MasterPortfolioTypeTb masterPortfolioTypeTb = investmentPolicyStmtDAO.getPortfolioByStyleAndSize(portfolioStyleId, portfolioSizeId);
        return masterPortfolioTypeTb.getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<CustomerPortfolioSecuritiesTb> getSecurityTableDetailsOfCustomer(CustomerPortfolioVO customerPortfolioVO, CustomerPortfolioTb customerPortfolioTb) {
        oldCustomerPortfolioSecuritiesTbList = new ArrayList<CustomerPortfolioSecuritiesTb>();
        newCustomerPortfolioSecuritiesTbList = new ArrayList<CustomerPortfolioSecuritiesTb>();
        newCustomerPortfolioSecuritiesTbList = investmentPolicyStmtDAO.getPortfolioSecuritiesForCustomer(customerPortfolioTb.getCustomerPortfolioId());
        Integer customer_portfolio_id = investmentPolicyStmtDAO.getCustomerPortfolioIdOfUser(customerPortfolioVO.getCustomerId(),customerPortfolioVO.getAdvisorId());
        if (customer_portfolio_id != null) {
            oldCustomerPortfolioSecuritiesTbList = investmentPolicyStmtDAO.getPortfolioSecuritiesForCustomer(customer_portfolio_id);
        }
        if (oldCustomerPortfolioSecuritiesTbList != null || !oldCustomerPortfolioSecuritiesTbList.isEmpty()) {
            if (newCustomerPortfolioSecuritiesTbList != null || !newCustomerPortfolioSecuritiesTbList.isEmpty()) {
                for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb1 : oldCustomerPortfolioSecuritiesTbList){
                    boolean securityExist = false;
                    for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb2 : newCustomerPortfolioSecuritiesTbList) {
                        if (customerPortfolioSecuritiesTb2.getSecurityCode().equalsIgnoreCase(customerPortfolioSecuritiesTb1.getSecurityCode())
                                && customerPortfolioSecuritiesTb2.getVenueScriptCode().equalsIgnoreCase(customerPortfolioSecuritiesTb1.getVenueScriptCode())
                                && customerPortfolioSecuritiesTb2.getVenueCode().equalsIgnoreCase(customerPortfolioSecuritiesTb1.getVenueCode())) {
                            securityExist = securityExist || true;
                            customerPortfolioSecuritiesTb2.setExeUnits((BigDecimal) (customerPortfolioSecuritiesTb1.getExeUnits()
                                    == null ? ZERO : customerPortfolioSecuritiesTb1.getExeUnits()));
                            customerPortfolioSecuritiesTb2.setAverageRate(customerPortfolioSecuritiesTb1.getAverageRate() == null
                                    ? ZERO : customerPortfolioSecuritiesTb1.getAverageRate());
                            customerPortfolioSecuritiesTb2.setAvgAcquisitionPrice(customerPortfolioSecuritiesTb1.getAvgAcquisitionPrice() == null
                                    ? ZERO : customerPortfolioSecuritiesTb1.getAvgAcquisitionPrice());
                            customerPortfolioSecuritiesTb2.setCurrentPrice(customerPortfolioSecuritiesTb1.getCurrentPrice() == null
                                    ? ZERO : customerPortfolioSecuritiesTb1.getCurrentPrice());
                            customerPortfolioSecuritiesTb2.setLastBoughtPrice(customerPortfolioSecuritiesTb1.getLastBoughtPrice() == null
                                    ? ZERO : customerPortfolioSecuritiesTb1.getLastBoughtPrice());
                            customerPortfolioSecuritiesTb2.setYesterDayUnitCount((BigDecimal) (customerPortfolioSecuritiesTb1.getYesterDayUnitCount() == null
                                    ? ZERO : customerPortfolioSecuritiesTb1.getYesterDayUnitCount()));
                            customerPortfolioSecuritiesTb2.setBlockCount((BigDecimal) (customerPortfolioSecuritiesTb1.getBlockCount() == null
                                    ? ZERO : customerPortfolioSecuritiesTb1.getBlockCount()));
                            
                        } 
                    }
                    if(!securityExist) {
                            customerPortfolioSecuritiesTb1.setCustomerPortfolioTb(customerPortfolioTb);
                            newCustomerPortfolioSecuritiesTbList.add(customerPortfolioSecuritiesTb1);
                        }
                }
            }
        }
        return newCustomerPortfolioSecuritiesTbList;
    }
    
    public CustomerPortfolioTb transferDetailsToNewPortfolio(CustomerPortfolioTb existing_portfolio,CustomerPortfolioTb customerPortfolioTb){
        customerPortfolioTb.setCurrentValue(existing_portfolio.getCurrentValue() == null ? ZERO : existing_portfolio.getCurrentValue());
        customerPortfolioTb.setPortfolio1YearReturns(existing_portfolio.getPortfolio1YearReturns() == null ? 
                ZERO : existing_portfolio.getPortfolio1YearReturns());
        customerPortfolioTb.setPortfolio90DayReturns(existing_portfolio.getPortfolio90DayReturns() == null ? 
                ZERO : existing_portfolio.getPortfolio90DayReturns());
        customerPortfolioTb.setPortfolioValue(existing_portfolio.getPortfolioValue() == null ? 
                ZERO : existing_portfolio.getPortfolioValue());
        customerPortfolioTb.setCashAmount(existing_portfolio.getCashAmount() == null ? ZERO : existing_portfolio.getCashAmount());
        customerPortfolioTb.setBuyingPower(existing_portfolio.getBuyingPower() == null ? ZERO : existing_portfolio.getBuyingPower());
        customerPortfolioTb.setBlockedCash(existing_portfolio.getBlockedCash() == null ? ZERO : existing_portfolio.getBlockedCash());
        customerPortfolioTb.setBlockedCount(existing_portfolio.getBlockedCount() == null ? ZERO : existing_portfolio.getBlockedCount());
        customerPortfolioTb.setRecomendedCashBal(existing_portfolio.getRecomendedCashBal() == null ? 
                ZERO : existing_portfolio.getRecomendedCashBal());
        customerPortfolioTb.setAllocatedFund(existing_portfolio.getAllocatedFund() == null ? ZERO : existing_portfolio.getAllocatedFund());
        customerPortfolioTb.setTempBlockedCash(existing_portfolio.getTempBlockedCash() == null ? ZERO : existing_portfolio.getTempBlockedCash());
        customerPortfolioTb.setTempBlockedCount(existing_portfolio.getTempBlockedCount() == null ? ZERO : existing_portfolio.getTempBlockedCount());
        customerPortfolioTb.setAdvisorPortfolioStartValue((BigDecimal) (existing_portfolio.getAdvisorPortfolioStartValue() == null ? 
                ZERO : existing_portfolio.getAdvisorPortfolioStartValue()));
        customerPortfolioTb.setCashFlow((BigDecimal) (existing_portfolio.getCashFlow() == null ? ZERO : existing_portfolio.getCashFlow()));
        customerPortfolioTb.setBenchmarkReturnsFromStart((BigDecimal) (existing_portfolio.getBenchmarkReturnsFromStart() == null ? 
                ZERO : existing_portfolio.getBenchmarkReturnsFromStart()));
        customerPortfolioTb.setIsFirstDayPerfmanceCalc(false);
        customerPortfolioTb.setPortfolioChangeViewed(false);
        customerPortfolioTb.setRebalanceViewed(false);
        customerPortfolioTb.setCommentFreq(ONE);
        customerPortfolioTb.setLastExecutionUpdate(existing_portfolio.getLastExecutionUpdate());
        return customerPortfolioTb; 
    }
}
