/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IClientInvestmentPerformanceBAO;
import com.gtl.mmf.bao.IInvestorDashBoardBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;

import static com.gtl.mmf.service.util.IConstants.DAY_VALUE;
import static com.gtl.mmf.service.util.IConstants.FIVE;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_ID;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.THREESIXTYFIVE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.LineChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 07958
 */
@Component("clientInvestmentPerformanceController")
@Scope("view")
public class ClientInvestmentPerformanceController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.ClientInvestmentPerformanceController");
    private static final String ADV_DASH_BOARD = "/pages/advisordashboard.xhtml?faces-redirect=true";
    private static final String ASSIGN_PORTFOLIO = "/pages/assign_portfolio.xhtml?faces-redirect=true";

    @Autowired
    private IClientInvestmentPerformanceBAO clientInvestmentPerformanceBAO;
    @Autowired
    private IInvestorDashBoardBAO investorDashBoardBAO;
    private List<PortfolioDetailsVO> portfolioDetailsVOs;
    private InvestorDetailsVO investorDetailsVO;
    private List<LineChartDataVO> benchMarkLineChartDataVOList;
    private List<LineChartDataVO> recommendedLineChartDataVOList;
    private List<LineChartDataVO> selfLineChartDataVOList;
    private String benchMarkLineChartData;
    private String recommendedLineChartData;
    private String selfLineChartData;
    private String portfolioAllocationJSON;
    private PortfolioVO portfolioVO;
    private String reDirectionUrl;
    private String jsonToolTipFormat;
    private Integer nodays;
    private String jsonCategory;
    private String jsonDateFormat;
    private Long fivedays;
    private Long oneMonth;
    private Long threeMonth;
    private Long sixMonth;
    private Long oneYear;
    private Long fiveYear;
    private Long tenYear;
    private InvestorDetailsVO investorProfile;

    @PostConstruct
    public void afterInit() {
        benchMarkLineChartDataVOList = new ArrayList<LineChartDataVO>();
        recommendedLineChartDataVOList = new ArrayList<LineChartDataVO>();
        selfLineChartDataVOList = new ArrayList<LineChartDataVO>();
        portfolioDetailsVOs = new ArrayList<PortfolioDetailsVO>();
        investorDetailsVO = new InvestorDetailsVO();
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        investorProfile = (InvestorDetailsVO) storedValues.get(SELECTED_INVESTOR);
        Integer customerPorfolioId = (Integer) storedValues.get(PORTFOLIO_ID);
        Integer relationid = investorProfile.getRelationId();
        StringBuilder portfolioAllocationJSONObj = new StringBuilder();
        portfolioVO = clientInvestmentPerformanceBAO.getClientInvestmentPerformanceDetails(
                benchMarkLineChartDataVOList,
                recommendedLineChartDataVOList,
                selfLineChartDataVOList,
                investorDetailsVO,
                portfolioAllocationJSONObj,
                customerPorfolioId,
                relationid);
        buildLineChartdata(benchMarkLineChartDataVOList, recommendedLineChartDataVOList, selfLineChartDataVOList);
        portfolioAllocationJSON = portfolioAllocationJSONObj.toString();
        setTolltipdateFormat();
    }

    public void onClickReturnDays() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        Integer noOfdaysToBeBacked = params.get(DAY_VALUE) == null ? ZERO : Integer.parseInt(params.get(DAY_VALUE));
        clearLinchartList();
        investorDashBoardBAO.getLineChartData(investorDetailsVO.getCustomerId(), noOfdaysToBeBacked, benchMarkLineChartDataVOList,
                recommendedLineChartDataVOList, selfLineChartDataVOList);
        buildLineChartdata(benchMarkLineChartDataVOList, recommendedLineChartDataVOList, selfLineChartDataVOList);
        //setTolltipdateFormat();
    }

    private void clearLinchartList() {
        benchMarkLineChartDataVOList.clear();
        recommendedLineChartDataVOList.clear();
        selfLineChartDataVOList.clear();
    }

    private void buildLineChartdata(List<LineChartDataVO> benchMarkLineChartDataVOList,
            List<LineChartDataVO> recommendedLineChartDataVOList, List<LineChartDataVO> selfLineChartDataVOList) {
        benchMarkLineChartData = PortfolioAllocationChartUtil.generateLineChartData(benchMarkLineChartDataVOList);
        recommendedLineChartData = PortfolioAllocationChartUtil.generateLineChartData(recommendedLineChartDataVOList);
        selfLineChartData = PortfolioAllocationChartUtil.generateLineChartData(selfLineChartDataVOList);
    }

    public boolean isChgAssignPFVisisble() {
        if (investorDetailsVO.getStatus() == EnumAdvisorMappingStatus.IPS_CREATED.getValue()
                || investorDetailsVO.getStatus() == EnumAdvisorMappingStatus.IPS_REVIEWED.getValue()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public void onClickBack() {
        reDirectionUrl = ADV_DASH_BOARD;
    }

    public void onClickChangeAssignPF() {
        reDirectionUrl = ASSIGN_PORTFOLIO;
    }

    public String reDirect() {
        return reDirectionUrl;
    }

    public String getBenchMarkLineChartData() {
        return benchMarkLineChartData;
    }

    public void setBenchMarkLineChartData(String benchMarkLineChartData) {
        this.benchMarkLineChartData = benchMarkLineChartData;
    }

    public String getRecommendedLineChartData() {
        return recommendedLineChartData;
    }

    public void setRecommendedLineChartData(String recommendedLineChartData) {
        this.recommendedLineChartData = recommendedLineChartData;
    }

    public String getSelfLineChartData() {
        return selfLineChartData;
    }

    public void setSelfLineChartData(String selfLineChartData) {
        this.selfLineChartData = selfLineChartData;
    }

    public List<PortfolioDetailsVO> getPortfolioDetailsVOs() {
        return portfolioDetailsVOs;
    }

    public void setPortfolioDetailsVOs(List<PortfolioDetailsVO> portfolioDetailsVOs) {
        this.portfolioDetailsVOs = portfolioDetailsVOs;
    }

    public InvestorDetailsVO getInvestorDetailsVO() {
        return investorDetailsVO;
    }

    public void setInvestorDetailsVO(InvestorDetailsVO investorDetailsVO) {
        this.investorDetailsVO = investorDetailsVO;
    }

    public String getPortfolioAllocationJSON() {
        return portfolioAllocationJSON;
    }

    public void setPortfolioAllocationJSON(String portfolioAllocationJSON) {
        this.portfolioAllocationJSON = portfolioAllocationJSON;
    }

    public PortfolioVO getPortfolioVO() {
        return portfolioVO;
    }

    public void setPortfolioVO(PortfolioVO portfolioVO) {
        this.portfolioVO = portfolioVO;
    }

    private void setTolltipdateFormat() {
        String toolTipFormat;
        int max = Math.max(benchMarkLineChartDataVOList.size(), Math.max(recommendedLineChartDataVOList.size(), selfLineChartDataVOList.size()));
        String category = null;
        String dateFormat;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Long portfolioAssigned;
        if (max <= THREESIXTYFIVE) {
            category = "month";
            nodays = ONE;
            dateFormat = "%Y/%b";
        } else {
            int year = max / THREESIXTYFIVE;
            if (year > FIVE) {
                category = "year";
                dateFormat = "%Y";
                nodays = ONE;
            } else {
                category = "month";
                nodays = FIVE;
                dateFormat = "%Y/%b";
            }

            //nodays = max>ONE?(max>THREESIXTYFIVE?year:(max > THIRTY && max <= THREESIXTYFIVE? 12:(max >= FIFTEEN? FIFTEEN:max))):ONE;
        }
        
        portfolioAssigned = new Date().getTime();
        if (investorDashBoardBAO.portfolioAssignedDate() != null) {
            portfolioAssigned = Long.valueOf(investorDashBoardBAO.portfolioAssignedDate());
        }

        long currentTime = new Date().getTime();
        cal.add(Calendar.DATE, -5);
  
// Calculating portfolio assigned date for performance graph. By default it is portfolio assigned date      
        fivedays = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.MONTH, -1);
        oneMonth = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.MONTH, -3);
        threeMonth = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.MONTH, -6);
        sixMonth = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.YEAR, -1);
        oneYear = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.YEAR, -5);
        fiveYear = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.YEAR, -10);
        tenYear = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonCategory = objectMapper.writeValueAsString(category);
            jsonDateFormat = objectMapper.writeValueAsString(dateFormat);
        } catch (IOException e) {
            LOGGER.severe(StackTraceWriter.getStackTrace(e));

        }
    }

    public String getJsonToolTipFormat() {
        return jsonToolTipFormat;
    }

    public void setJsonToolTipFormat(String jsonToolTipFormat) {
        this.jsonToolTipFormat = jsonToolTipFormat;
    }

    public Integer getNodays() {
        return nodays;
    }

    public void setNodays(Integer nodays) {
        this.nodays = nodays;
    }

    public String getJsonCategory() {
        return jsonCategory;
    }

    public void setJsonCategory(String jsonCategory) {
        this.jsonCategory = jsonCategory;
    }

    public String getJsonDateFormat() {
        return jsonDateFormat;
    }

    public void setJsonDateFormat(String jsonDateFormat) {
        this.jsonDateFormat = jsonDateFormat;
    }

    public Double getMaxYavalue() {
        return investorDashBoardBAO.getMaxYavalue() == null
                ? 0.0 : investorDashBoardBAO.getMaxYavalue();
    }

    public String getPortfolioAssignedDate() {
        return investorDashBoardBAO.portfolioAssignedDate() == null
                ? String.valueOf(new Date().getTime())
                : investorDashBoardBAO.portfolioAssignedDate();
    }

    public String getPortfolioDate() {
        if (investorDashBoardBAO.portfolioAssignedDate() != null) {
            long time = Long.parseLong(investorDashBoardBAO.portfolioAssignedDate());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            cal.add(Calendar.MONTH, 1);
            Long start = cal.getTimeInMillis() - 19820000;
            return start.toString();
        }
        return String.valueOf(new Date().getTime());
    }

    public Long getFivedays() {
        return fivedays;
    }

    public Long getOneMonth() {
        return oneMonth;
    }

    public Long getThreeMonth() {
        return threeMonth;
    }

    public Long getSixMonth() {
        return sixMonth;
    }

    public Long getOneYear() {
        return oneYear;
    }

    public Long getFiveYear() {
        return fiveYear;
    }

    public Long getTenYear() {
        return tenYear;
    }

}
