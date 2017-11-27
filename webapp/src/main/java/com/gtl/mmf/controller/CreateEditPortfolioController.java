/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.ICreateEditPortfolioBAO;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.ALLOCATEDFUND_TEMP;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.CREATE;
import static com.gtl.mmf.service.util.IConstants.DDMMMYY;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import static com.gtl.mmf.service.util.IConstants.FROM_NOTIFICATION;
import static com.gtl.mmf.service.util.IConstants.HUNDRED;
import static com.gtl.mmf.service.util.IConstants.HUNDRED_DOUBLE;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_ID;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_LINK_C_OR_E;
import static com.gtl.mmf.service.util.IConstants.SECURITY_ALLOCATION_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.SECURITY_DELETE_STATUS;
import static com.gtl.mmf.service.util.IConstants.SECURITY_NOT_TRADABLE;
import static com.gtl.mmf.service.util.IConstants.SIX;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
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
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZEROD;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.SymbolListUtil;
import com.gtl.mmf.service.vo.AssetClassVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * This class is used for creating and editing portfolio
 *
 * @author 07958
 */
@Named("createEditPortfolioController")
@Scope("view")
public class CreateEditPortfolioController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.CreateEditPortfolioController");
    private static final String ALLOCATION_FAILED = "Please allocate your assets within the range.";
    private static final String INVALID_PORTFOLIO_NAME = "Please enter portfolio name.";
    private static final String INVALID_SECURITY_DETAILS = "You have not added securities for all asset classes.";
    private static final String PORTFOLIO_CREATED_SUCCESS = "Your portfolio %s created successfully.";
    private static final String PORTFOLIO_EDITED_SUCCESS = "Your portfolio %s modified successfully.";
    private static final String PORTFOLIO_NAME_ALREADY_EXISTS = "Portfolio name already exists";
    private static final String PORTFOLIOS_TO_EDIT = "PORTFOLIOS_TO_EDIT";
    private static final String SECURITY = "security";
    private static final String ASSET = "asset";
    private static final String ASSET_TYPE = "assetType";
    private static final Double MAXIMUM_ALLOCATION = 100.0;
    private static final String PORTFOLIO_SELECTED = "PORTFOLIO_SELECTED";
    private static final String SHOW_MESSAGE = "SHOW_EDIT_MESSAGE";
    private static final String SHOW_EDIT_MESSAGE_PORTFOLIO = "SHOW_EDIT_MESSAGE_PORTFOLIO";
    private static final String PORTFOLIO_NAME_INPUT_ID = "portfolio-name";
    private Map<Integer, String> benchmarkIndexList;
    private Map<String, Integer> portfolioStyleList;
    private PortfolioVO portfolioVO;
    @Autowired
    private ICreateEditPortfolioBAO createEditPortfolioBAO;
    private String portfolioAllocationJSON;
    private Integer modifiedSecurityIndex;
    private String modifiedAsset;
    private boolean createPortfolio;
    private boolean noPortfolioToShow;
    private int advisorId;
    private Integer portfolioSelected;
    private int securityIndex;
    private String assetClass;
    private int defaultPortfolioSelected;
    private Map<Integer, String> portfolioNames;
    private Integer benchmarkIndexSelected;
    private String linkStatus;
    private String marketData;
    private String newSecurityId;
    private boolean onSaveorUpdate;

    /**
     * This method load initial data for showing in create/edit portfolio page
     */
    @PostConstruct
    public void afterInit() {

        //selecting a portfolio as a default one for showing when page is loading
        defaultPortfolioSelected = LookupDataLoader.getPortfolioTypes().keySet().iterator().next();

        //Loading Benchmark name list 
        this.benchmarkIndexList = LookupDataLoader.getBenchMark();
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        advisorId = userSessionBean != null ? userSessionBean.getUserId() : ZERO;

        //selecting a Benchamark as a default one for showing when page is loading
        benchmarkIndexSelected = LookupDataLoader.getBenchMark().keySet().iterator().next();
        linkStatus = (String) getStoredValues().get(PORTFOLIO_LINK_C_OR_E);

        /*
         Checking for create portfolio or Edit portfolio
         */
        if ((Boolean) getStoredValues().get(CREATE)) {

            //This method will load a new portfolio for creating
            onSaveorUpdate = true;
            loadNewPortfolio();
        } else {
            onSaveorUpdate = false;
            /*
             Edit portfolio
             Option 1
             When user select a portfolio from the performance grid.
             We need to set the the portfolio  style, name, benchmark name 
             based on the select portfolio.
             Option 2
             User select Edit option
             */
            if (getStoredValues().get(FROM_NOTIFICATION) != null) {

                // Loading portfolio for editing when user select a portfolio from the performance grid.
                if (getStoredValues().get(FROM_NOTIFICATION) == Boolean.TRUE) {
                    Integer portfolioId = (Integer) getStoredValues().get(PORTFOLIO_ID);

                    //Loading Seleted portfolio from the DB
                    portfolioVO = createEditPortfolioBAO.getPortfolioById(advisorId, portfolioId);
                    Integer portfolioTypeId = portfolioVO.getPortfolioTypeSelectedKey();
                    Integer benchmarkIndexId = portfolioVO.getBenchmarkIndexSelected();

                    //this line is added here to assign selected portfolioid before loading portfolio
                    portfolioSelected = portfolioId;

                    // Loading all the portfolios created under the portfolio style selected by the advisor
                    portfolioNames = createEditPortfolioBAO.getPorfolioNames(advisorId, portfolioTypeId, LookupDataLoader.getAssetClasses(), benchmarkIndexId);

                    // Loading portfolio for Editing selected portfolioid and benchamek is passing
                    loadEditPortfolio(portfolioTypeId, benchmarkIndexId);
                    benchmarkIndexSelected = portfolioVO.getBenchmarkIndexSelected();
                    noPortfolioToShow = false;
                    this.updateMarketData();

                    //Checking portfolio for rebalancing
                    this.checkPortfolioStatusForReblance();

                    //To allow editing portfolio while accessing portfolio from portfolio performance grid
                    getStoredValues().remove(FROM_NOTIFICATION);
                }
            } else {

                // Loading portfolio for Editing default portfolio id and benchamek is passing
                loadEditPortfolio(defaultPortfolioSelected, benchmarkIndexSelected);

                //Checking portfolio for rebalancing
                this.checkPortfolioStatusForReblance();
            }
        }
        this.updateMarketData();
    }

    public String actionCancel() {
        return "/pages/advisordashboard?faces-redirect=true";
    }

    public String actionAddRemoveSecurity() {
        return "/pages/create_edit_portfolio?faces-redirect=true";
    }

    /**
     * This method is called when advisor adds ane security to the portfolio.
     *
     * @param event event for adding a new security to portfolio
     */
    public void doActionAddSecurity(ActionEvent event) {
        if (getRequestParameterMap().get(ASSET_TYPE) != null) {

            //Getting the asset type under which the security needs to added.
            String assetType = getRequestParameterMap().get(ASSET_TYPE);

            //Checking For Cash to avoid
            if (!assetType.equalsIgnoreCase(TEXT_CASH)) {
                PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();

                portfolioSecurityVO.setAssetClassId(LookupDataLoader.getAssetClasses().get(assetType));
                portfolioSecurityVO.setAssetClass(assetType);

                //Used to return asset class for selected asset ID
                PortfolioDetailsVO assetclassVO = PortfolioUtil.getAssetclassVO(assetType, portfolioVO);

                // Adding securuity to the particular asset class
                assetclassVO.getSecurities().add(portfolioSecurityVO);
                getStoredValues().put(PORTFOLIO, portfolioVO);

                /*
                 Storing portfolio names and selected portfolio for showing in page
                 after relaoding in case of Edit portfolio
                 */
                if (!createPortfolio) {
                    getStoredValues().put(PORTFOLIOS_TO_EDIT, portfolioNames);
                    getStoredValues().put(PORTFOLIO_SELECTED, portfolioSelected);
                }
            }
        }
    }

    /**
     * This method is used to remove a security selected by the advisor
     *
     * @param event event for removing a security from the portfolio
     */
    public void doActionRemoveSecurity(ActionEvent event) {

        //Getting ID indicating the security
        int securityInd = Integer.valueOf(getRequestParameterMap().get("securityIndex"));

        //Getting Asset class under which Security selected for removing.
        String assetType = getRequestParameterMap().get("assetClass");

        //Used to get asset class for selected asset ID
        PortfolioDetailsVO assetclassVO = PortfolioUtil.getAssetclassVO(assetType, portfolioVO);

        //Storing the portfolio
        getStoredValues().put(PORTFOLIO, portfolioVO);

        /*
         On create porfolio on remove portfolio always remove the security from
         corresponding asset class list.
         */
        if (createPortfolio) {
            assetclassVO.getSecurities().remove(securityInd);
        } else {

            PortfolioSecurityVO securityToRemove = assetclassVO.getSecurities().get(securityInd);

            // Remove security if security added is not persisted one.
            if (securityToRemove.getAllocatedPreviously()) {
                securityToRemove.setNewAllocation(ZEROD);
                securityToRemove.setStatus(SECURITY_DELETE_STATUS);
                // getStoredValues().remove(FROM_NOTIFICATION);
            } else {
                assetclassVO.getSecurities().remove(securityToRemove);
            }

            /*
             Storing portfolio names and selected portfolio for showing in page
             after relaoding  portfolio page
             */
            getStoredValues().put(PORTFOLIOS_TO_EDIT, portfolioNames);
            getStoredValues().put(PORTFOLIO_SELECTED, portfolioSelected);
        }
        /*
         This method is used to calculate the totalallcotion specified for each asset
         class afeter adding a new security
         */
        getSumNewAssetAllocation(assetclassVO);
        updateMarketData();
        try {
            this.plotPortfolioAllocationChart();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Plotting allication chart failed on new allcation change. Exception : {0}", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method is used to load portfolio for editing when user select Edit
     * portfolio from side bar
     *
     * @param portfolioStyleSelected Portfolio style of portfolio selected
     * @param benchmarkSelected benchmark
     */
    private void loadEditPortfolio(Integer portfolioTypeSelected, Integer benchmarkSelected) {
        try {

            boolean flag = false;
            /*
             When user add/remove securities or making changes in portfolio which 
             require reloading the portfolio for that reson the portfolio is stored  into a 
             MAP called StoredValues and loaded from that when reloading is required
             OPTION 1 
             When user clicks on Edit link then we need to open first portfolio in default portfolio style.
             Option 2
             When user makes changes in portfolio we need to reload the same portfolio.
             */
            //Checking portfolio is relaoding in the page
            if (getStoredValues().get(PORTFOLIO) == null) {

                //Advisor clicks edit portfolio link
                PortfolioTypeVO portfolioTypeDefault = LookupDataLoader.getPortfolioTypes().get(portfolioTypeSelected);

                //getting portfolio names under default portfolio style 
//                 portfolioNames = createEditPortfolioBAO.getPorfolioNames(
//                      advisorId, portfolioTypeDefault.getId(), LookupDataLoader.getAssetClasses(), benchmarkSelected);

                /*
                 Checking portfolio's are availabel in default portfolio style.
                 if no portfolios in default style then a message No portfolio created under this style 
                 is displayed.
                 */
                if (portfolioNames.isEmpty()) {
                    onSaveorUpdate = true;
                    //Loading default portfolio
                    portfolioVO = new PortfolioVO(LookupDataLoader.getAssetClasses(), portfolioTypeDefault, benchmarkSelected);
                    portfolioVO.setPortfolioTypeSelectedKey(portfolioTypeSelected);
                    portfolioVO.setPortfolioSizeSelectedKey(LookupDataLoader.getPortfolioTypes().get(portfolioTypeSelected).getMasterPortfolioSizeTb().getPortfolioSizeId());
                    portfolioVO.setPortfolioStyleSelectedKey(LookupDataLoader.getPortfolioTypes().get(portfolioTypeSelected).getMasterPortfolioStyleTb().getPortfolioStyleId());
                    portfolioVO.setAdvisorId(advisorId);
                    noPortfolioToShow = true;
                } else {

                    /*
                     It is checking that whether portfolio is selectd from performance grid in the dashboard
                     then that portfolio is setting as selected otherwise default one is selected
                     */
                    if (getStoredValues().get(FROM_NOTIFICATION) == null) {
                        portfolioSelected = (Integer) portfolioNames.keySet()
                                .iterator().next();
                    }

                    //Loading portfolio selected
                    createEditPortfolioBAO.updatePortfolioTypeOfPortfolio(portfolioVO.getPortfolioId(), portfolioVO.getPortfolioTypeSelectedKey());
                    portfolioVO = createEditPortfolioBAO.getPortfolioById(advisorId, portfolioVO.getPortfolioId());
                    noPortfolioToShow = false;
                }
            } else {

                /*
                 When Create/Edit portfolio page is reloading portfolio and portfolio names
                 stored in th Storedvalue is loaded
                 */
                portfolioVO = (PortfolioVO) getStoredValues().get(PORTFOLIO);
                portfolioNames = (Map<Integer, String>) getStoredValues().get(PORTFOLIOS_TO_EDIT);
                portfolioSelected = (Integer) getStoredValues().get(PORTFOLIO_SELECTED);
                benchmarkIndexSelected = portfolioVO.getBenchmarkIndexSelected();
                noPortfolioToShow = false;
                getStoredValues().remove(PORTFOLIO);
            }

            /*
             This method is called to create JSON string representaion for creating pie chart for the
             new portfolio with default alloction specified.
             */
            this.plotPortfolioAllocationChart();
            createPortfolio = false;

            //Assigning labels to portfolio securities only if portfolio under the selected style is available
            if (!noPortfolioToShow) {
                assignSecurityLabel();
            }
            this.updateMarketData();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * Assigning labels to securities in the portfolio
     */
    private void assignSecurityLabel() {

        //Getting securities assigned in the portfolio.
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getPortfolioSecurityVOs(portfolioVO);
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
            String securityId = portfolioSecurityVO.getSecurityId();
            if (securityId != null) {

                //Used to get asset class type for a particular asset ID
                String assetClassType = PortfolioUtil.getAssetClassType(portfolioSecurityVO.getAssetClassId());

                /*
                 Getting the record for the particular security from Synbool file list.
                 All  the securities are categorized into different asset classes and maintain it as a list.
                 */
                SymbolRecordVO symbolRecordVO = SymbolListUtil.getSymbolRecordVO(assetClassType, securityId);

                //Label for identifying the security is added
                portfolioSecurityVO.setSecurityLabel(symbolRecordVO.getLabel());
                portfolioSecurityVO.setAssetClass(assetClassType);
            }
        }
    }

    /**
     * This method is used to load new portfolio when user select create
     * portfolio from side bar
     */
    private void loadNewPortfolio() {
        try {
            Map<String, Object> storedValues = getStoredValues();
            createPortfolio = true;

            /*
             When user add/remove securities or making changes in portfolio which 
             require reloading the portfolio for that reson the portfolio is stored a into a 
             MAP called StoredValues and loaded from that when reloading is required
             OPTION 1 
             When user clicks on create link then we need to create a new portfolio
             in default portfolio style.
             Option 2
             When user makes changes in portfolio we need to reload the same portfolio.
             */
            if (storedValues.get(PORTFOLIO) != null) {

                //Loading the new portfolio created from MAP after Page reloading 
                portfolioVO = (PortfolioVO) storedValues.get(PORTFOLIO);
                benchmarkIndexSelected = portfolioVO.getBenchmarkIndexSelected();

            } else {
                /*Loading a new portfolio for advisor.
                 # When user selects create portfolio link  default portfolio is selected for creating.
                 # Parameters
                 1. All the asset classes used which is used to set the asset cllases for new portfolio
                 2. Portfolio type selected which is in this case the default one. 
                 Which is used to check whether a asset need to add into this portfolio
                 and assign the range of allocation for the asset.
                 3. Benchamark selected
                 */
                portfolioVO = new PortfolioVO(LookupDataLoader.getAssetClasses(),
                        LookupDataLoader.getPortfolioTypes().get(defaultPortfolioSelected), benchmarkIndexSelected);
                portfolioVO.setPortfolioTypeSelectedKey(LookupDataLoader.getPortfolioTypes().keySet().iterator().next());
                portfolioVO.setPortfolioStyleSelectedKey(LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey())
                        .getMasterPortfolioStyleTb().getPortfolioStyleId());
                portfolioVO.setPortfolioSizeSelectedKey(LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey())
                        .getMasterPortfolioSizeTb().getPortfolioSizeId());
                /*
                 This method is called to create JSON string representaion for creating pie chart for the
                 new portfolio with default alloction specified.
                 */
                this.plotPortfolioAllocationChart();
                portfolioVO.setAdvisorId(advisorId);
                benchmarkIndexSelected = LookupDataLoader.getBenchMark().keySet().iterator().next();

                /*
                 This method set the default securities with alloction assigned for each asset class
                 when loading the portfolio first time
                 */
                this.setDefaultAllocationForAssets();
            }

            //Showing message after successfull creation of the portfolio
            if (getStoredValues().get(SHOW_MESSAGE) != null) {
                if ((Boolean) getStoredValues().get(SHOW_MESSAGE)) {
                    String message = String.format(PORTFOLIO_CREATED_SUCCESS, getStoredValues().get(SHOW_EDIT_MESSAGE_PORTFOLIO));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
                }
                getStoredValues().remove(SHOW_MESSAGE);
            }

            /*
             This method is called to create JSON string representaion for creating pie chart for the
             new portfolio with new alloctions specified by the advisor.
             */
            this.plotPortfolioAllocationChart();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    private Map<String, Object> getStoredValues() {
        return (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
    }

    /**
     * This method is called when advisor changes portfolio selected in the
     * Select box
     *
     * @param event called when portfolio is changed
     */
    public void changePortfoiliotoEditListner(ValueChangeEvent event) {
        try {

            //Getting newly selected portfolio ID
            portfolioSelected = (Integer) event.getNewValue();
            if (portfolioSelected != -1) {

                //This method is used to retrieve a portfolio by its ID.
                portfolioVO = createEditPortfolioBAO.getPortfolioById(advisorId, portfolioSelected);
            }

            //Assigning labels to securities in the portfolio
            assignSecurityLabel();

            /*
             Used to get JSON string for the pie chart color used to represent asset,
             value allocated and asset name is the combination used for creating JSON
             string for each asset
             */
            plotPortfolioAllocationChart();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "changePortfoiliotoEditListner() - Changing portfolio on edit "
                    + "portfolio cause error - Exception : {0}", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method is called when advisor changes benchmark name
     *
     * @param event Benchmark name is changed
     */
    public void benchMarkChangeListner(AjaxBehaviorEvent event) {
        try {
            Integer benchmarkSelected = benchmarkIndexSelected;

            /*
             Based on Benchmark loading portfolio
             Create portfolio newly selected benchmark is used to create portfolio
             Edit Only portfolios listed under selected benchmark is displayed for editing
             */
            if (createPortfolio) {
                //Create portfolio
                portfolioVO.setBenchmarkIndexSelected(benchmarkSelected);
            } else {
                //Edit portfolio
                loadEditPortfolio(portfolioVO.getPortfolioTypeSelectedKey(), benchmarkSelected);
            }
            plotPortfolioAllocationChart();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "benchMarkChangeListner() - Changing benchmark on "
                    + "edit portfolio cause error - Exception : {0}", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method is called when the allocation for a asset changes. Calculate
     * new total allocation for asset classes and Cash.
     */
    public void newAllocationChange() {

        //Selecting modified security.
        modifiedSecurityIndex = Integer.valueOf(getRequestParameterMap().get(SECURITY)).intValue();

        //Selecting modified asset class.
        modifiedAsset = getRequestParameterMap().get(ASSET);

        //Getting Security modified.
        PortfolioSecurityVO modifiedSecurity = getModifiedSecurity();

        //Setting new value.
        modifiedSecurity.setNewAllocation(Double.valueOf(getRequestParameterMap().get("newAllocationChange")));
        PortfolioDetailsVO modifiedAssetVO = PortfolioUtil.getAssetclassVO(modifiedAsset, portfolioVO);

        /*
         This method is used to calculate the totalallcotion specified for each asset
         class afeter adding a new security
         */
        getSumNewAssetAllocation(modifiedAssetVO);
        updateMarketData();
        try {
            this.plotPortfolioAllocationChart();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Plotting allication chart failed on new allcation change. Exception :"
                    + " {0}", StackTraceWriter.getStackTrace(ex));
        }
    }

    /*
     This method is used to calculate the totalallcotion specified for each asset
     class afeter adding a new security
     #For asset class 
     New allocation = old allocation for asset + new alloction for the security added
     # For cash 
     Allcotion = 100 - Total alloction for all security
     */
    private void getSumNewAssetAllocation(PortfolioDetailsVO modifiedAssetVO) {
        modifiedAssetVO.setNewAllocation(SECURITY_ALLOCATION_DEFAULT);
        for (PortfolioSecurityVO portfolioSecurityVO : PortfolioUtil.getPortfolioSecurityVOs(modifiedAssetVO)) {
            modifiedAssetVO.setNewAllocation(modifiedAssetVO.getNewAllocation() + portfolioSecurityVO.getNewAllocation());
        }

        // Calculating alloction for cash
        portfolioVO.getCash().setNewAllocation(MAXIMUM_ALLOCATION - getTotalAllocation());
    }

    /**
     * This Method is used to calculate the total allocation done for all the
     * securities added to the portfolio
     *
     * @return Double which is the sum allocation for all securities
     */
    private Double getTotalAllocation() {
        return portfolioVO.getEquity().getNewAllocation() + portfolioVO.getFno().getNewAllocation()
                + portfolioVO.getDebt().getNewAllocation() + portfolioVO.getGold().getNewAllocation()
                + portfolioVO.getInternational().getNewAllocation() + portfolioVO.getMutualfund().getNewAllocation()
                + portfolioVO.getIndex().getNewAllocation() + portfolioVO.getMidcap().getNewAllocation()
                + portfolioVO.getEquityDiversified().getNewAllocation() + portfolioVO.getBalanced().getNewAllocation()
                + portfolioVO.getDebtLiquid().getNewAllocation() + portfolioVO.getTax_planning().getNewAllocation();
    }

    /**
     * When portfolio style changed by advisor
     *
     * @param event
     */
    public void portflioStyleChangeListner(AjaxBehaviorEvent event) {
        try {

            //Getting new portfolio style.
            Integer portfolioStyleSelected = portfolioVO.getPortfolioStyleSelectedKey();
            Integer portfolioSizeSelected = portfolioVO.getPortfolioSizeSelectedKey();
            Integer portfolioTypeId = createEditPortfolioBAO.getPortfolioByStyleAndSize(portfolioStyleSelected, portfolioSizeSelected);
            /*
             Checking for create portfolio and edit portfolio
             */
            if (createPortfolio) {

                /*
                 When portfolio style changes we need to make changes to,
                 1 Allcotion pie chart
                 2 Load new portfolio with selected style
                 3 Set assets for the ne selected portfolio style
                 */
                this.plotPortfolioAllocationChart();
                portfolioVO = new PortfolioVO(
                        LookupDataLoader.getAssetClasses(), LookupDataLoader.getPortfolioTypes().get(portfolioTypeId), benchmarkIndexSelected);
                portfolioVO.setPortfolioStyleSelectedKey(portfolioStyleSelected);
                portfolioVO.setPortfolioTypeSelectedKey(portfolioTypeId);
                portfolioVO.setPortfolioSizeSelectedKey(portfolioSizeSelected);
                portfolioVO.setBenchmarkIndexSelected(benchmarkIndexSelected);
                this.setDefaultAllocationForAssets();

            } else {
                loadEditPortfolio(portfolioTypeId, benchmarkIndexSelected);
                this.plotPortfolioAllocationChart();
            }
            portfolioVO.setPortfolioStyleSelectedKey(portfolioStyleSelected);
            portfolioVO.setPortfolioTypeSelectedKey(portfolioTypeId);
            portfolioVO.setPortfolioSizeSelectedKey(portfolioSizeSelected);
            portfolioVO.setAdvisorId(advisorId);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * Used to get JSON string for the pie chart color used to represent asset,
     * value allocated and asset name is the combination used for creating JSON
     * string for each asset
     *
     * @throws IOException
     */
    private void plotPortfolioAllocationChart() throws IOException {
        portfolioAllocationJSON = PortfolioAllocationChartUtil.generatePiechartJSONString(
                PortfolioUtil.getPortfolioDetails(portfolioVO));
    }

    /**
     * Saving portfolio into DB
     *
     * @param event to save portfolio
     */
    public void doActionSavePortfolio(ActionEvent event) {

        if (!isValidationFailed()) {
            updateMarketData();

            //Condition to prevent save of PF with security not tradable
            if (!isSecuritytradable(portfolioVO)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, SECURITY_NOT_TRADABLE, SECURITY_NOT_TRADABLE));
            } else {

                PortfolioTypeVO portfolioType = LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey());
                if (createPortfolio) {
                    portfolioVO.setBenchmarkIndexSelected(benchmarkIndexSelected);
                    calculateBeforeSaveOrUpdate();
                    PortfolioTb portfolioTb = createEditPortfolioBAO.createPortfolio(portfolioVO, portfolioType);
                    portfolioVO = createEditPortfolioBAO.getPortfolioById(advisorId, portfolioTb.getPortfolioId());
                    createPortfolio = false;
                    portfolioSelected = portfolioTb.getPortfolioId();
                    portfolioNames = new HashMap<Integer, String>();
                    portfolioNames.put(portfolioSelected, portfolioTb.getPortfolioName());
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, String.format(PORTFOLIO_CREATED_SUCCESS, portfolioVO.getPortfolioName()),
                                    String.format(PORTFOLIO_CREATED_SUCCESS, portfolioVO.getPortfolioName())));
                } else {
                    calculateBeforeSaveOrUpdate();
                    createEditPortfolioBAO.transformPortfolioWithNewAssetClass(portfolioVO, portfolioType, LookupDataLoader.getAssetClasses());
                    createEditPortfolioBAO.updatePortfolio(portfolioVO, portfolioType, LookupDataLoader.getAssetClasses());
                    portfolioVO = createEditPortfolioBAO.getPortfolioById(advisorId, portfolioVO.getPortfolioId());
                    this.setSecurityTickerReadOnly();
                    String message = String.format(PORTFOLIO_EDITED_SUCCESS, portfolioVO.getPortfolioName());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
                }
            }
        }
    }

    /**
     * # Assign current market price in securities. #Calculate total number of
     * units that can be brought using current allocation.#Used to set current
     * value for each asset class and current weight of total allocated amount
     * used.
     *
     */
    public void updateMarketData() {
        PortfolioUtil.updatePortfolioMarketPrice(portfolioVO);
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getPortfolioSecurityVOs(portfolioVO);
        if (createPortfolio) {
            for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {

                /*
                 This method is used to calculate the number of units that can be brought
                 for the specified allocation Transaction Count calculation Amount
                 allocated for security = (allocation percentage / 100) * total allocation
                 amount transaction count = Amount allocated for security / market price
                 */
//            portfolioSecurityVO.setExecutedUnits(PortfolioUtil.getTransactionCount(
//                    portfolioSecurityVO.getNewAllocation(), portfolioSecurityVO.getCurrentPrice(), ALLOCATEDFUND_TEMP));
                // calculating existing weight based on previous/initial allocation
//                portfolioSecurityVO.setExecutedUnits(PortfolioUtil.getTransactionCount(
//                        portfolioSecurityVO.getInitialAllocation(), portfolioSecurityVO.getCurrentPrice(), ALLOCATEDFUND_TEMP));
                portfolioSecurityVO.setExecutedUnits(ZERO_POINT_ZERO);
            }
        }


        /*
         Calculate values (price * units).
         Calculate Weight
         1. Security Weight (security_current_value/portfolio value)*100
         2. Existing Weight (value/total)*100
        
         */
//        PortfolioUtil.updateValueAndWeight(portfolioVO);
        PortfolioUtil.updateValueAndWeightForAdvisor(portfolioVO, onSaveorUpdate);
        /*
         This method is used to check the transactional eligibility of the security
         */
        PortfolioUtil.checkTransactionEligibility(portfolioVO);
    }

    /**
     *
     * @return boolean true if validation is success
     */
    private boolean isValidationFailed() {

        // validate portfolio name
        boolean invalidName = portfolioVO.getPortfolioName() == null || portfolioVO.getPortfolioName().isEmpty();
        if (invalidName) {
            FacesContext.getCurrentInstance().addMessage(PORTFOLIO_NAME_INPUT_ID,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_PORTFOLIO_NAME, INVALID_PORTFOLIO_NAME));
        }

        /*
         While saving on create portfolio the name is compared with all
         the names under  the selected style
         */
        if (createPortfolio) {
            Map<Integer, String> portfolioNamesExists = createEditPortfolioBAO.getPorfolioNames(
                    advisorId, portfolioVO.getPortfolioTypeSelectedKey(), LookupDataLoader.getAssetClasses(), benchmarkIndexSelected);
            Iterator<String> itr = portfolioNamesExists.values().iterator();
            while (itr.hasNext()) {
                if (portfolioVO.getPortfolioName().equalsIgnoreCase(itr.next())) {
                    invalidName = true;
                    FacesContext.getCurrentInstance().addMessage(PORTFOLIO_NAME_INPUT_ID,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, PORTFOLIO_NAME_ALREADY_EXISTS, PORTFOLIO_NAME_ALREADY_EXISTS));
                    break;
                }
            }
        }
        /*
         validate allocation for all the asset classes
         First  check whether the asset class in the select portfolio style need to allocate security 
         Then cheks the value allocated is in range.
         Allocation should be in,
         Minimum Range > New Allocation 
         New Allocation < MaximumRange
         */
        boolean invalidAllcation
                = //isEquityForAllocation() && isAssetAllocationInvalid(portfolioVO.getEquity())? true : 
                isDebtForAllocation() && isAssetAllocationInvalid(portfolioVO.getDebt()) ? true
                : isGoldForAllocation() && isAssetAllocationInvalid(portfolioVO.getGold()) ? true
                : isInternationalForAllocation() && isAssetAllocationInvalid(portfolioVO.getInternational()) ? true
                : isFnoForAllocation() && isAssetAllocationInvalid(portfolioVO.getFno()) ? true
                : isEquityDiversifiedForAllocation() && isAssetAllocationInvalid(portfolioVO.getEquityDiversified()) ? true
                : isIndexForAllocation() && isAssetAllocationInvalid(portfolioVO.getIndex()) ? true
                : isMidcapForAllocation() && isAssetAllocationInvalid(portfolioVO.getMidcap()) ? true
                //                : isMFForAllocation() && isAssetAllocationInvalid(portfolioVO.getMutualfund()) ? true 
                : isBalancedForAllocation() && isAssetAllocationInvalid(portfolioVO.getBalanced()) ? true
                : isDebtLiquidForAllocation() && isAssetAllocationInvalid(portfolioVO.getDebtLiquid()) ? true
                : isTaxPlanningForAllocation() && isSecurityAllcationInvalid(portfolioVO.getTax_planning()) ? true
                : isAssetAllocationInvalid(portfolioVO.getCash()) ? true : false;
        if (invalidAllcation) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ALLOCATION_FAILED, ALLOCATION_FAILED));
        }
        /*
         validate Ticker for all the Securities
         First  check whether the asset class in the select portfolio style need to allocate security 
         
         */
        boolean invalidSecurityDetails = isEquityForAllocation() && isSecurityAllcationInvalid(portfolioVO.getEquity())
                ? true : isDebtForAllocation() && isSecurityAllcationInvalid(portfolioVO.getDebt()) ? true
                : isGoldForAllocation() && isSecurityAllcationInvalid(portfolioVO.getGold()) ? true
                : isInternationalForAllocation() && isSecurityAllcationInvalid(portfolioVO.getInternational()) ? true
                : isFnoForAllocation() && isSecurityAllcationInvalid(portfolioVO.getFno()) ? true
                : isEquityDiversifiedForAllocation() && isSecurityAllcationInvalid(portfolioVO.getEquityDiversified()) ? true
                : isMidcapForAllocation() && isSecurityAllcationInvalid(portfolioVO.getMidcap()) ? true
                : isIndexForAllocation() && isSecurityAllcationInvalid(portfolioVO.getIndex()) ? true
                : isMFForAllocation() && isSecurityAllcationInvalid(portfolioVO.getMutualfund()) ? true
                : isBalancedForAllocation() && isSecurityAllcationInvalid(portfolioVO.getBalanced()) ? true
                : isDebtLiquidForAllocation() && isSecurityAllcationInvalid(portfolioVO.getDebtLiquid()) ? true
                : isTaxPlanningForAllocation() && isSecurityAllcationInvalid(portfolioVO.getTax_planning()) ? true
                : isSecurityAllcationInvalid(portfolioVO.getCash()) ? true : false;
        if (invalidSecurityDetails) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_SECURITY_DETAILS, INVALID_SECURITY_DETAILS));
        }

        return invalidAllcation || invalidName || invalidSecurityDetails;
    }

    private void setSecurityTickerReadOnly() {
        for (PortfolioSecurityVO securityVO : PortfolioUtil.getPortfolioSecurityVOs(portfolioVO)) {
            securityVO.setAllocatedPreviously(true);
        }
    }

    /**
     * Checks the security allcated is valid
     *
     * @param assetAllocatioToValidate Asset class to validate
     * @return boolean
     */
    private boolean isSecurityAllcationInvalid(PortfolioDetailsVO assetAllocatioToValidate) {
        boolean invalidSecurityAllocation = false;
        if ((assetAllocatioToValidate.getMaximumRange() == null ? ZEROD : assetAllocatioToValidate.getMaximumRange()) > 0) {
            for (PortfolioSecurityVO portfolioSecurityVO : assetAllocatioToValidate.getSecurities()) {
                if (portfolioSecurityVO.getStatus() != null && portfolioSecurityVO.getStatus()) {
                    if (portfolioSecurityVO.getSecurityId() == null || portfolioSecurityVO.getSecurityId().isEmpty()) {
                        invalidSecurityAllocation = true;
                        break;
                    }
                }
            }
        }

        return invalidSecurityAllocation;
    }

    /**
     * Checks for allocation is in specified range Allocation should be in,
     * Minimum Range > New Allocation New Allocation < MaximumRange
     *
     * @param assetAllocatioToValidate asset class to validate
     * @return boolean true if validation failed
     */
    private boolean isAssetAllocationInvalid(PortfolioDetailsVO assetAllocatioToValidate) {
        return ((assetAllocatioToValidate.getMinimumRange() == null ? ZEROD : assetAllocatioToValidate.getMinimumRange()) > assetAllocatioToValidate.getNewAllocation())
                || (assetAllocatioToValidate.getNewAllocation() > (assetAllocatioToValidate.getMaximumRange() == null ? HUNDRED_DOUBLE : assetAllocatioToValidate.getMaximumRange()))
                        ? true : false;
    }

    public void recieveModifiedTickerIndex() {
        modifiedSecurityIndex = Integer.valueOf(getRequestParameterMap().get(SECURITY)).intValue();
        modifiedAsset = getRequestParameterMap().get(ASSET);
    }

    /**
     * This method is used to get new Ticker
     *
     * @param event
     */
    public void reciveNewTicker(SelectEvent event) {

        // Check new scrip already in selected portfolio.
        try {
            String newScrip = event.getObject().toString();

            // If security with securityId(ticker) already exists remove new one added and return that. Else return newly created.
            PortfolioSecurityVO currentSecurity = getModifiedSecurity();
            PortfolioSecurityVO securityExists = currentSecurity;
            PortfolioDetailsVO portfolioDetailsVO = PortfolioUtil.getAssetclassVO(modifiedAsset, portfolioVO);
            boolean securityAlreadyExists = false;

            //Checking the security is already exist in the portfolio
            for (PortfolioSecurityVO securityVO : portfolioDetailsVO.getSecurities()) {
                if (currentSecurity != securityVO && securityVO.getSecurityId() != null && securityVO.getSecurityId().equalsIgnoreCase(newScrip)) {
                    securityAlreadyExists = true;
                    securityExists = securityVO;
                    break;
                }
            }

            if (securityAlreadyExists) {
                portfolioDetailsVO.getSecurities().remove(currentSecurity);
                portfolioDetailsVO.getSecurities().remove(securityExists);
                portfolioDetailsVO.getSecurities().add(securityExists);
                securityExists.setStatus(true);
                getSumNewAssetAllocation(portfolioDetailsVO);
            } else {

                //Adding security ticker for new security
                securityExists.setSecurityId(newScrip);
                SymbolRecordVO selectedSymbol = SymbolListUtil.getSymbolRecordVO(modifiedAsset, newScrip);
                securityExists.setScripDecription(selectedSymbol.getScripDescription());
                securityExists.setSecurityLabel(selectedSymbol.getLabel());
                securityExists.setVenueCode(selectedSymbol.getVenue());
                securityExists.setVenueScriptCode(selectedSymbol.getVenueScripCode());
                securityExists.setSecurityCode(selectedSymbol.getScripName());
                securityExists.setInstrumentType(selectedSymbol.getInstrumentType());
                securityExists.setExpirationdate(selectedSymbol.getExpirationDate() == null
                        ? null : DateUtil.stringToDate(selectedSymbol.getExpirationDate(), DDMMMYY));
                securityExists.setStrikePrice(selectedSymbol.getStrikePrice());
                securityExists.setAssetClass(modifiedAsset);
            }
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method C
     *
     * @return PortfolioSecurityVO security modified
     */
    private PortfolioSecurityVO getModifiedSecurity() {
        PortfolioDetailsVO portfolioDetailsVO = PortfolioUtil.getAssetclassVO(modifiedAsset, portfolioVO);
        return portfolioDetailsVO.getSecurities().get(modifiedSecurityIndex);
    }

    private boolean isTickerExists(String assetClass, String newTicker) {
        boolean exists = false;
        PortfolioDetailsVO portfolioDetailsVO = PortfolioUtil.getAssetclassVO(assetClass, portfolioVO);
        for (PortfolioSecurityVO security : portfolioDetailsVO.getSecurities()) {
            if (security.getStatus() && security.getSecurityId() != null && security.getSecurityId().equalsIgnoreCase(newTicker)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public List<String> completeTicker(String query) {
        FacesContext context = FacesContext.getCurrentInstance();
        String tickerAsset = (String) UIComponent.getCurrentComponent(context).getAttributes().get(ASSET);
        List<String> symbolFiltered = new ArrayList<String>();
        List<SymbolRecordVO> symbolList = SymbolListUtil.getSymbolList(tickerAsset);
        for (SymbolRecordVO symbolRecordVO : symbolList) {
            if (SymbolListUtil.isSymbolRecordValid(symbolRecordVO, query) && !isTickerExists(tickerAsset, symbolRecordVO.getTicker())) {
                symbolFiltered.add(symbolRecordVO.getTicker());
            }
        }
        return symbolFiltered;
    }

    /**
     * This method is used to check whether the portfolio need re-balancing.
     * Asset class CASH is not taken for re-balancing. Checks current asset
     * class weight is in the specified range Check price for security is in
     * specified range.
     */
    private void checkPortfolioStatusForReblance() {
        for (PortfolioDetailsVO portfolioDetailsVO : PortfolioUtil
                .getPortfolioDetails(portfolioVO)) {

            // Checking for Asset CASH
            if (!PortfolioUtil.getAssetClassType(
                    portfolioDetailsVO.getAssetId())
                    .equalsIgnoreCase(TEXT_CASH)) {
                portfolioDetailsVO.setWeightNotInRange(!PortfolioUtil
                        .isPortfolioDetailWeightInRange(portfolioDetailsVO));
            }

        }
        portfolioVO.getCash().setWeightNotInRange(false);
        /*check price for security is in specified range.
         checking price is in range
         calculate the maximum Range and minimum Range for price and check price is in this range
         maximum Range = current price + (current price * Tolerance Positive Range/ 100.00)
         minimum Range = current price- (current price * Tolerance Negative Range/ 100.00);
         */
        for (PortfolioSecurityVO portfolioSecurityVO : PortfolioUtil.getPortfolioSecurityVOs(portfolioVO)) {
            portfolioSecurityVO.setPriceNotInRange(!PortfolioUtil.isPortfolioSecurityPriceInRange(portfolioSecurityVO));
        }
    }

    private Map<String, String> getRequestParameterMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

    public List<Integer> getPriceToleranceList() {
        return LookupDataLoader.getPriceToleranceList();
    }

    public Map<Integer, PortfolioTypeVO> getPortfolioTypes() {
        return LookupDataLoader.getPortfolioTypes();
    }

    public Map<Integer, PortfolioTypeVO> getPortfolioStyle() {
        return LookupDataLoader.getPortfolioStyle();
    }

    public Map<Integer, PortfolioTypeVO> getPortfolioSize() {
        Map<Integer, PortfolioTypeVO> list = new LinkedHashMap<Integer, PortfolioTypeVO>();
        list.putAll(LookupDataLoader.getPortfolioSize());
        return list;
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isEquityForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_EQUITY);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isEquityDiversifiedForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), EQUITY_DIVERSIFIED);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isDebtForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_DEBT);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isFnoForAllocation() {
        boolean flag = false;
        if (!portfolioVO.getPortfolioStyleSelectedKey().equals(SIX)) {
            flag = PortfolioAllocationChartUtil.isAssetForAllocation(
                    LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_FNO);
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isInternationalForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_INTERNATIONAL);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isGoldForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_GOLD);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isMFForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_MUTUAL_FUND);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isIndexForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_INDEX);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isMidcapForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), MIDCAP);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isBalancedForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_BALANCED);
    }

    /**
     * This method is used to check whether the asset class in the select
     * portfolio style need to allocate security . it is done by checking the
     * allocation of each Asset. if it is !=0 return true
     *
     * @return boolean true if asset class is set for allocation in the selected
     * portfolio style
     */
    public boolean isTaxPlanningForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TAX_PLANNING);
    }

    public String getTextEquity() {
        return TEXT_EQUITY;
    }

    public String getTextDebt() {
        return TEXT_DEBT;
    }

    public String getTextFO() {
        return TEXT_FNO;
    }

    public String getTextMF() {
        return TEXT_MUTUAL_FUND;
    }

    public Map<Integer, String> getBenchmarkIndexList() {
        return benchmarkIndexList;
    }

    public void setBenchmarkIndexList(Map<Integer, String> benchmarkIndexList) {
        this.benchmarkIndexList = benchmarkIndexList;
    }

    public Map<String, Integer> getPortfolioStyleList() {
        return portfolioStyleList;
    }

    public void setPortfolioStyleList(Map<String, Integer> portfolioStyleList) {
        this.portfolioStyleList = portfolioStyleList;
    }

    public PortfolioVO getPortfolioVO() {
        return portfolioVO;
    }

    public void setPortfolioVO(PortfolioVO portfolioVO) {
        this.portfolioVO = portfolioVO;
    }

    public String getPortfolioAllocationJSON() {
        return portfolioAllocationJSON;
    }

    public void setPortfolioAllocationJSON(String portfolioAllocationJSON) {
        this.portfolioAllocationJSON = portfolioAllocationJSON;
    }

    public Integer getNewTickerSecurity() {
        return modifiedSecurityIndex;
    }

    public void setNewTickerSecurity(Integer newTickerSecurity) {
        this.modifiedSecurityIndex = newTickerSecurity;
    }

    public boolean isCreatePortfolio() {
        return createPortfolio;
    }

    public boolean isNoPortfolioToShow() {
        return noPortfolioToShow;
    }

    public Integer getPortfolioSelected() {
        return portfolioSelected;
    }

    public void setPortfolioSelected(Integer portfolioSelected) {
        this.portfolioSelected = portfolioSelected;
    }

    public int getSecurityIndex() {
        return securityIndex;
    }

    public void setSecurityIndex(int securityIndex) {
        this.securityIndex = securityIndex;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public Integer getModifiedSecurityIndex() {
        return modifiedSecurityIndex;
    }

    public void setModifiedSecurityIndex(Integer modifiedSecurityIndex) {
        this.modifiedSecurityIndex = modifiedSecurityIndex;
    }

    public String getModifiedAsset() {
        return modifiedAsset;
    }

    public void setModifiedAsset(String modifiedAsset) {
        this.modifiedAsset = modifiedAsset;
    }

    public Map<Integer, String> getPortfolioNames() {
        return portfolioNames;
    }

    public void setPortfolioNames(Map<Integer, String> portfolioNames) {
        this.portfolioNames = portfolioNames;
    }

    public Integer getBenchmarkIndexSelected() {
        return benchmarkIndexSelected;
    }

    public void setBenchmarkIndexSelected(Integer benchmarkIndexSelected) {
        this.benchmarkIndexSelected = benchmarkIndexSelected;
    }

    public String getLinkStatus() {
        return linkStatus;
    }

    public String getMarketData() {
        return marketData;
    }

    public void setMarketData(String marketData) {
        this.marketData = marketData;
    }

    public String getNewSecurityId() {
        return newSecurityId;
    }

    public void setNewSecurityId(String newSecurityId) {
        this.newSecurityId = newSecurityId;
    }

    public String getTextGold() {
        return TEXT_GOLD;
    }

    public String getTextEquityDiverified() {
        return EQUITY_DIVERSIFIED;
    }

    public String getTextInternational() {
        return TEXT_INTERNATIONAL;
    }

    public String getTextIndex() {
        return TEXT_INDEX;
    }

    public String getTextMidcap() {
        return MIDCAP;
    }

    public String getTextBalanced() {
        return TEXT_BALANCED;
    }

    /**
     * This method is used to load the default securities for each asset class
     * from the property file when new portfolio is created.
     *
     */
    private void setDefaultAllocationForAssets() {

        /*
         Reading default securities specified in the property file,which is a comma sepreated list.
         #  Format For Specifying Default Securities #
         # NSE & BSE File Entries     :  AssetType:ScripDescription - ScripName | Series | Venue
         # NSE DEBT  File Entries     :  AssetType:ScripDescription - ScripName | ExpirationDate | Venue
         # NSEFO & BSEFO  File Entries:  AssetType:ScripName - ExpirationDate | StrikePrice | OptionType | Venue
         # NSEMF File Entries         :  AssetType:ScripDescription - ScripName | Series |Venue
         # AssetTypes                 :  Index,Equity Diversified,MidCap,Gold,International,Debt-Medium/Long,Debt-Liquid/Short
         */
        if (!portfolioVO.getPortfolioStyleSelectedKey().equals(SIX)) {
            String[] defaultSecurities = getDefaultSecurityForPortfolio(portfolioVO.getPortfolioSizeSelectedKey());

            //Loading all the asset classes
            Map<String, Short> assetClasses = LookupDataLoader.getAssetClasses();
            try {

                // Each default security is adding to coressponding asset class specified
                for (String securityName : defaultSecurities) {
                    String[] securityInfo = securityName.split(":");

                    //Comparing the asset type in the default security and assets loaded
                    if (assetClasses.containsKey(securityInfo[ZERO])) {
                        Short value = assetClasses.get(securityInfo[ZERO]);

                        //Adding security into asset class
                        addSecurityInformation(securityInfo[ZERO], value,
                                securityInfo[ONE]);
                    }
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
            }
        }

    }

    /**
     *
     * @param assetType asset type for default security
     * @param assetId - id of that asset type
     * @param newScrip - Description of the security
     * @throws ParseException
     */
    private void addSecurityInformation(String assetType, Short assetId, String newScrip) throws ParseException {
        PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
        portfolioSecurityVO.setAssetClassId(assetId);
        portfolioSecurityVO.setAssetClass(assetType);
        //Getting default alloction for the specified  asset type
        Double defaultAllocation = (double) getDefaultAllocationAmount(assetType);

        //Getting asset class information for the specified asset
        PortfolioDetailsVO assetclassVO = PortfolioUtil.getAssetclassVO(assetType, portfolioVO);
        if (newScrip != null) {
            portfolioSecurityVO.setSecurityId(newScrip);

            /*
             Selecting the specified secuirity data from the list which is Loaded.
             */
            SymbolRecordVO selectedSymbol = SymbolListUtil
                    .getSymbolRecordVO(assetType, newScrip);
            portfolioSecurityVO.setScripDecription(selectedSymbol
                    .getScripDescription());
            portfolioSecurityVO.setSecurityLabel(selectedSymbol
                    .getLabel());
            portfolioSecurityVO.setVenueCode(selectedSymbol
                    .getVenue());
            portfolioSecurityVO.setVenueScriptCode(selectedSymbol
                    .getVenueScripCode());
            portfolioSecurityVO.setSecurityCode(selectedSymbol
                    .getScripName());
            portfolioSecurityVO.setInstrumentType(selectedSymbol
                    .getInstrumentType());
            portfolioSecurityVO.setExpirationdate(selectedSymbol
                    .getExpirationDate() == null ? null : DateUtil
                            .stringToDate(
                                    selectedSymbol.getExpirationDate(),
                                    DDMMMYY));
            portfolioSecurityVO.setStrikePrice(selectedSymbol
                    .getStrikePrice());
            portfolioSecurityVO.setNewAllocation(defaultAllocation);
            // line 1374 added to set initial allocation to zero only at time of new portfolio creation
            portfolioSecurityVO.setInitialAllocation(ZEROD);
            assetclassVO.getSecurities().add(portfolioSecurityVO);
        }
        /*
         Calculating the total allcotion for each aset class
         */
        getSumNewAssetAllocation(assetclassVO);

        //Updating portfolio values with current market data.
        updateMarketData();

        // Ploting Pie chart using ne allocated data
        try {
            this.plotPortfolioAllocationChart();
        } catch (IOException ex) {
            LOGGER.log(
                    Level.SEVERE,
                    "Plotting allication chart failed on new allcation change. Exception : {0}",
                    StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method is used to get the default allocation assigned for each asset
     * class.
     *
     * @param assetType asset type selected from portfolio
     * @return int allocation for the asset class
     */
    private int getDefaultAllocationAmount(String assetType) {
        int amount = 0;

        /*
         Seleting the portfolio style for the portfolio selected for creating.
         Portfolio style contain details that specify the defalut alloction, range for each asset etc
         */
        PortfolioTypeVO portfolioStyle = LookupDataLoader.getPortfolioTypes()
                .get(portfolioVO.getPortfolioTypeSelectedKey());
        if (assetType.equalsIgnoreCase(TEXT_INDEX)) {
            amount = portfolioStyle.getEquitiesIndex();
        } else if (assetType.equalsIgnoreCase(EQUITY_DIVERSIFIED)) {
            amount = portfolioStyle.getDiversified_equity();
        } else if (assetType.equalsIgnoreCase(MIDCAP)) {
            amount = portfolioStyle.getMidCap();
        } else if (assetType.equalsIgnoreCase(TEXT_GOLD)) {
            amount = portfolioStyle.getGold();
        } else if (assetType.equalsIgnoreCase(TEXT_INTERNATIONAL)) {
            amount = portfolioStyle.getInternational();
        } else if (assetType.equalsIgnoreCase(TEXT_DEBT)) {
            amount = portfolioStyle.getDebt();
        } else if (assetType.equalsIgnoreCase(TEXT_CASH)) {
            amount = portfolioStyle.getCash();
//        } else if (assetType.equalsIgnoreCase(TEXT_MUTUAL_FUND)) {
//            amount = portfolioStyle.getMutualFund() / 2;
        } else if (assetType.equalsIgnoreCase(TEXT_BALANCED)) {
            amount = portfolioStyle.getBalanced();
        } else if (assetType.equalsIgnoreCase(TEXT_DEBT_LIQUID)) {
            amount = portfolioStyle.getDebt_liquid();
        } else if (assetType.equalsIgnoreCase(TAX_PLANNING)) {
            amount = portfolioStyle.getTax_planning();
        }
        return amount;
    }

    /**
     * When portfolio size is changed by advisor
     *
     * @param event
     */
    public void portflioSizeChangeListner(AjaxBehaviorEvent event) {
        try {

            //Getting new portfolio style.           
            Integer portfolioSizeSelected = portfolioVO.getPortfolioSizeSelectedKey();
            Integer portfolioStyleId = portfolioVO.getPortfolioStyleSelectedKey();
            Integer portfolioTypeId = createEditPortfolioBAO.getPortfolioByStyleAndSize(portfolioStyleId, portfolioSizeSelected);

            /*
             Checking for create portfolio and edit portfolio
             */
            if (createPortfolio) {

                /*
                 When portfolio style changes we need to make changes to,
                 1 Allcotion pie chart
                 2 Load new portfolio with selected style
                 3 Set assets for the ne selected portfolio style
                 */
                this.plotPortfolioAllocationChart();
                portfolioVO = new PortfolioVO(
                        LookupDataLoader.getAssetClasses(), LookupDataLoader.getPortfolioTypes().get(portfolioTypeId), benchmarkIndexSelected);
                portfolioVO.setPortfolioSizeSelectedKey(portfolioSizeSelected);
                portfolioVO.setPortfolioTypeSelectedKey(portfolioTypeId);
                portfolioVO.setPortfolioStyleSelectedKey(portfolioStyleId);
                portfolioVO.setBenchmarkIndexSelected(benchmarkIndexSelected);
                this.setDefaultAllocationForAssets();
            } else {
                loadEditPortfolio(portfolioTypeId, benchmarkIndexSelected);
                this.plotPortfolioAllocationChart();
            }
            portfolioVO.setPortfolioSizeSelectedKey(portfolioSizeSelected);
            portfolioVO.setPortfolioTypeSelectedKey(portfolioTypeId);
            portfolioVO.setPortfolioStyleSelectedKey(portfolioStyleId);
            portfolioVO.setAdvisorId(advisorId);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public boolean isDebtLiquidForAllocation() {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_DEBT_LIQUID);
    }

    public String getTextDebtLiquid() {
        return TEXT_DEBT_LIQUID;
    }

    /**
     * Method to load default securities under each portfolio size
     *
     * @param portfolioType
     * @return
     */
    private String[] getDefaultSecurityForPortfolio(int portfolioType) {
        String[] defaultSec = null;
        switch (portfolioVO.getPortfolioSizeSelectedKey()) {
            case 1:
                defaultSec = PropertiesLoader.getPropertiesValue("defaultsecurities1").split(COMA);
                break;
            case 2:
                defaultSec = PropertiesLoader.getPropertiesValue("defaultsecurities2").split(COMA);
                break;
            case 3:
                defaultSec = PropertiesLoader.getPropertiesValue("defaultsecurities3").split(COMA);
                break;
            case 4:
                defaultSec = PropertiesLoader.getPropertiesValue("defaultsecurities4").split(COMA);
                break;
            case 5:
                defaultSec = PropertiesLoader.getPropertiesValue("defaultsecurities5").split(COMA);
                break;
            case 6:
                defaultSec = PropertiesLoader.getPropertiesValue("defaultsecurities6").split(COMA);
                break;
            default:
                defaultSec = PropertiesLoader.getPropertiesValue("defaultsecurities1").split(COMA);
                break;
        }
        return defaultSec;
    }

    /**
     * Method written to calculate weight and required units before save /update
     */
    public void calculateBeforeSaveOrUpdate() {
        onSaveorUpdate = true;
        PortfolioUtil.updatePortfolioMarketPrice(portfolioVO);
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getPortfolioSecurityVOs(portfolioVO);
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
            // calculating existing weight based on new allocation
            portfolioSecurityVO.setExecutedUnits(PortfolioUtil.getTransactionCount(
                    portfolioSecurityVO.getNewAllocation(), portfolioSecurityVO.getCurrentPrice(), ALLOCATEDFUND_TEMP));
        }
        PortfolioUtil.updateValueAndWeightForAdvisor(portfolioVO, onSaveorUpdate);
    }

    private boolean isSecuritytradable(PortfolioVO portfolioDetailsVO) {
        boolean isTradable = true;
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getPortfolioSecurityVOs(portfolioVO);
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
            if (portfolioSecurityVO.getStatus()) {
                if (portfolioSecurityVO.getTradeSignal() != null) {
                    if (portfolioSecurityVO.getTradeSignal().equalsIgnoreCase("red")) {
                        isTradable = false;
                        break;
                    }
                }
            }
        }
        return isTradable;
    }

    public String getTaxPlanning() {
        return TAX_PLANNING;
    }
}
