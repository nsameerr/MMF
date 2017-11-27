package com.gtl.mmf.bao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IPortfolioSearchBAO;
import com.gtl.mmf.dao.IPortfolioSearchDAO;
import com.gtl.mmf.entity.PortfolioTb;

public class PortfolioSearchBAOImpl implements IPortfolioSearchBAO {

	private static final Logger logger = Logger.getLogger("com.gtl.mmf.bao.impl.portfoliosearchBAOImpl");
	private static String SPACE = " ";
	@Autowired
	private IPortfolioSearchDAO searchDAO;

	private static HashMap<Integer,String> riskTypeMap = new HashMap<Integer, String>();
	@Transactional
	public String getDefaultPortfolioSearchResult() {
		
		try {
			riskTypeMap.put(1, "Aggressive");
			riskTypeMap.put(2, "Moderately Aggressive");
			riskTypeMap.put(3, "Moderate");
			riskTypeMap.put(4, "Moderately Conservative");
			riskTypeMap.put(5, "Conservative");
			riskTypeMap.put(6, "Custom");
			
			
			JSONObject jsonresult = new JSONObject();
			JSONArray portfolios = new JSONArray();
			List<PortfolioTb> result = searchDAO.getDefaultSearchResult();
			logger.info("portfolio search default result size = " + result.size());
			for (PortfolioTb p : result) {
				logger.info("portfolioName: "+ p.getPortfolioName());
				logger.info("advisorId :"+ p.getMasterAdvisorTb().getAdvisorId());
				logger.info("advisorEmail :"+ p.getMasterAdvisorTb().getEmail());
				
				JSONObject obj =  new JSONObject();
				JSONObject returnObj = new JSONObject();
				obj.put("portfolioName", p.getPortfolioName());
				String firstname = p.getMasterAdvisorTb().getFirstName();
				String middlename = p.getMasterAdvisorTb().getMiddleName();
				String lastname = p.getMasterAdvisorTb().getLastName();
				String completename = "";
				if(middlename == null || middlename.equals("")){
					completename = firstname + lastname;
				} else {
					completename = firstname + SPACE +middlename +SPACE+ lastname;
				}
				obj.put("advisorName", completename);
				int rating = searchDAO.getAdvisorAverageRating(p.getMasterAdvisorTb().getAdvisorId());
				obj.put("advisorCustRating", rating); 
				obj.put("portfolioType", "mutual funds"); //TODO get portfolio type
/*				logger.info("portfolio risktyppe id = " + p.getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyleId());
				logger.info("portfolio risktyppe vale = " + p.getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyle());*/
				String riskTypeValue = riskTypeMap.get(p.getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyleId());
				obj.put("riskType", riskTypeValue); 
				obj.put("fee", "0.5%"); //TODO get fee
				String desc =  p.getMasterAdvisorTb().getMasterAdvisorQualificationTb().getOneLineDesc() == null ? " no content" : p.getMasterAdvisorTb().getMasterAdvisorQualificationTb().getOneLineDesc();  
				obj.put("advisorDesc", desc); 
				obj.put("indvOrCprt", p.getMasterAdvisorTb().getMasterApplicantTb().getIndvOrCprt());
				obj.put("city", p.getMasterAdvisorTb().getMasterApplicantTb().getAddress2City());
				obj.put("minInvest", p.getMasterPortfolioTypeTb().getMasterPortfolioSizeTb().getMinAum());
				returnObj.put("oneMonth", p.getPortfolio1MonthReturn() == null ? 0 : p.getPortfolio1MonthReturn());
				returnObj.put("threeMonth", p.getPortfolio90DayReturns() == null ? 0 : p.getPortfolio90DayReturns());
				returnObj.put("sixMonth", p.getPortfolio6MonthReturn() == null ? 0 : p.getPortfolio6MonthReturn());
				returnObj.put("sinceInception", p.getPortflioYtdReturn() == null ? 0 : p.getPortflioYtdReturn());
				obj.put("returns",returnObj);
				portfolios.put(obj);
				  /* {
			        "portfolioName": "portfolio name 1",
			        "advisorName": "advisor name 1",
			        "advisorCustRating": "5",
			        "portfolioType": "mutual funds",
			        "riskType": "moderate high",
			        "fee": "0.5%",
			        "returns": {
			          "oneMonth": "1%",
			          "threeMonth": "2%",
			          "sixMonth": "3%",
			          "sinceInception": "4%"
			        },
			        "advisorDesc": "some desc of advisor",
			        "indvOrCprt":0,
			        "city":"mumbai"
			      },*/
			}
			jsonresult.put("portfolios", portfolios);
			return jsonresult.toString();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"error in getting default portfolio result ",e);
		}
		return null;


	}

}
