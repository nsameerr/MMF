package com.gtl.mmf;

import com.gtl.mmf.bao.IAuditBAO;
import com.gtl.mmf.entity.PortfolioAuditTb;
import com.gtl.mmf.entity.PortfolioDetailsAuditTb;
import com.gtl.mmf.entity.PortfolioSecuritiesAuditTb;
import com.gtl.mmf.entity.PortfolioTb;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.vo.PortfolioVO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author 08237
 *
 */
public class AuditAspect {
	private static final String EDIT = "Edit";
//	private static final String CREATE = "Create";
//	private static final String CREATE_PORTFOLIO= "createPortfolio";
	@Autowired
    private IAuditBAO iAuditBAO;
	/**
	 * Method execute only after return of the methods mentioned in the pointcut
	 * @param joinPoint
	 * @param retVal
	 * @throws Throwable
	 */
    public void afterReturning(JoinPoint joinPoint,Object retVal) throws Throwable {
        PortfolioAuditTb  portfolioAuditTb = savePfAudit((PortfolioTb)retVal);
/*        if(joinPoint.getSignature().getName().equalsIgnoreCase(CREATE_PORTFOLIO)) {
            portfolioAuditTb.setActvityType(CREATE);
        } else {*/
            portfolioAuditTb.setActvityType(EDIT);
/*        }*/
        iAuditBAO.insertPortfolioTbAudit(portfolioAuditTb);
    }
    
    /**
     * Method to execute before of action
     * @param joinPoint
     */
    public void beforeEdit(JoinPoint joinPoint) {        
        Object []  args = joinPoint.getArgs();        
        PortfolioTb portfolioTb = ((PortfolioVO)args[ZERO]).toPortfolioTb();        
        PortfolioAuditTb  portfolioAuditTb = savePfAudit(portfolioTb);
        portfolioAuditTb.setActvityType(EDIT);
        iAuditBAO.insertPortfolioTbAudit(portfolioAuditTb);
        savePFDetailsAudit(portfolioTb,portfolioAuditTb);
    }
    
    /**
     * Method to save portfolio details and security details in to portfolio_details_audit_tb and
     * portfolio_securities_audit_rd respectively. 
     * @param pfTb
     * @param portfolioAuditTb
     */
    private void savePFDetailsAudit(PortfolioTb pfTb,PortfolioAuditTb portfolioAuditTb){
    	List<Object[]> portfolioDetailsTb = iAuditBAO.loadPortfolioDet(pfTb.getPortfolioId());
    	Set<PortfolioSecuritiesAuditTb> portfolioSecuritiesTbs = new HashSet<PortfolioSecuritiesAuditTb>();
    	for (Object[] objects : portfolioDetailsTb) {
            PortfolioDetailsAuditTb pfDetailsAuditTb = new PortfolioDetailsAuditTb();
            pfDetailsAuditTb.setPortfolioDetailsId((Integer)objects[0]);
            pfDetailsAuditTb.setPortfolioId((Integer)objects[1]);
            pfDetailsAuditTb.setAssetClassId((Short)objects[2]);           
            pfDetailsAuditTb.setRangeFrom((BigDecimal)objects[3]);
            pfDetailsAuditTb.setRangeTo((BigDecimal)objects[4]);
            pfDetailsAuditTb.setNewWeight((BigDecimal)objects[5]);
            pfDetailsAuditTb.setCurrentWeight((BigDecimal)objects[6]);
            pfDetailsAuditTb.setInitialWeight((BigDecimal)objects[7]);
            pfDetailsAuditTb.setRebalanceRequired((Boolean)objects[8]);
            pfDetailsAuditTb.setRebalanceReqdDate((Timestamp)objects[9]);            
            pfDetailsAuditTb.setCurrentValue((BigDecimal)objects[10]);
            pfDetailsAuditTb.setNewAllocation((BigDecimal)objects[11]);
            pfDetailsAuditTb.setLastUpdateon(new Date());
            pfDetailsAuditTb.setActivityType(EDIT);
            pfDetailsAuditTb.setPortfolioAuditTb(portfolioAuditTb);
            iAuditBAO.insertPortfolioDetailTbAudit(pfDetailsAuditTb);
            List<Object[]> pfSecDetails = iAuditBAO.loadPortfolioSec(pfDetailsAuditTb.getPortfolioDetailsId());
           
            for (Object[] objects2 : pfSecDetails) {
            	PortfolioSecuritiesAuditTb psat = new  PortfolioSecuritiesAuditTb();
                psat.setPortfolioDetailsAuditTb(pfDetailsAuditTb);                
                psat.setPortfolioSecuritiesId((Integer)objects2[0]);
                psat.setPortfolioId((Integer)objects2[1]);
                psat.setPortfolioDetailsId((Integer)objects2[2]);
                psat.setAssetClassId((Short)objects2[3]);
                psat.setSecurityId((String)objects2[4]);
                psat.setExpReturns((BigDecimal)objects2[5]);
       		 	psat.setStdDev((BigDecimal)objects2[6]);
       		 	psat.setNewWeight((BigDecimal)objects2[7]);
       		 	psat.setCurrentPrice((BigDecimal)objects2[8]);
	       		psat.setNewToleranceNegativeRange((Integer)objects2[9]); 	
       		 	psat.setNewTolerancePositiveRange((Integer)objects2[10]);
       		 	psat.setNewUnits((Integer)objects2[11]);       		 
       		 	psat.setCurrentValue((BigDecimal)objects2[12]);
       		 	psat.setCurrentWeight((BigDecimal)objects2[13]);
       		 	psat.setInitialWeight((BigDecimal)objects2[14]);
       		 	psat.setInitialPrice((BigDecimal)objects2[15]);
       		 	psat.setInitialToleranceNegativeRange((Integer)objects2[16]);
       		 	psat.setInitialTolerancePositiveRange((Integer)objects2[17]);
       		 	psat.setInitialUnits((BigDecimal)objects2[18]);
       		 	psat.setInitialValue((BigDecimal)objects2[19]);
       		 	psat.setRebalanceRequired((Boolean)objects2[20]);
       		 	psat.setRebalanceReqdDate((Timestamp)objects2[21]);
       		 	psat.setNewAllocation((BigDecimal)objects2[22]);
       		 	psat.setSecurityDescription((String)objects2[23]);
       		 	psat.setActivityType(EDIT);
                psat.setLastUpdateon(new Date());   
                portfolioSecuritiesTbs.add(psat);                
			}
            iAuditBAO.insertPortfolioSecuritiesTbAudit(portfolioSecuritiesTbs);
		}
    	
    }
    
    /**
     * Method to insert table portfolio_audit_tb
     * @param pt
     * @return
     */
    private PortfolioAuditTb savePfAudit(PortfolioTb pt){
        PortfolioAuditTb pfauditTb = new PortfolioAuditTb();
        pfauditTb.setAdvisorId(pt.getMasterAdvisorTb().getAdvisorId());
        pfauditTb.setAlpha(pt.getAlpha());
        pfauditTb.setBenchmark(pt.getMasterBenchmarkIndexTb().getId());
        pfauditTb.setBenchmark1YearReturns(pt.getBenchmark1YearReturns());
        pfauditTb.setBenchmark90DaysReturns(pt.getBenchmark90DaysReturns());
        pfauditTb.setBeta(pt.getBeta());
        pfauditTb.setDateCreated(pt.getDateCreated());
        pfauditTb.setExpReturns(pt.getExpReturns());
        pfauditTb.setInfoRatio(pt.getInfoRatio());
        pfauditTb.setLastUpdated(pt.getLastUpdated());
        pfauditTb.setNoOfCustomersAssigned(pt.getNoOfCustomersAssigned());
        pfauditTb.setPortfolio1YearReturns(pt.getPortfolio1YearReturns());
        pfauditTb.setPortfolio90DayReturns(pt.getPortfolio90DayReturns());
        pfauditTb.setPortfolioId(pt.getPortfolioId());
        pfauditTb.setPortfolioName(pt.getPortfolioName());
        pfauditTb.setPortfolioStyle(pt.getMasterPortfolioTypeTb().getId());
        pfauditTb.setPortfolioValue(pt.getPortfolioValue());
        pfauditTb.setRSquared(pt.getRSquared());
        pfauditTb.setRebalanceReqdDate(pt.getRebalanceReqdDate());
        pfauditTb.setSharpeRatio(pt.getSharpeRatio());
        pfauditTb.setStartCurrentPeriod(pt.getStartCurrentPeriod());
        pfauditTb.setStatus(pt.getStatus());
        pfauditTb.setStdDev(pt.getStdDev());
        pfauditTb.setVaR(pt.getVaR());
        pfauditTb.setLastUpdateon(new Date());
        return pfauditTb;
    }
}
