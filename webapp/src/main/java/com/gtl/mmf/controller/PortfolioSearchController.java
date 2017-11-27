package com.gtl.mmf.controller;




import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gtl.mmf.bao.IPortfolioSearchBAO;

/**@author Sumeet Pol 07-10-16
 * Portfolio Search Controller for new portfolioSearch.jsp
 * */

/*@Autowired
private IFpMainCalculationBAO fpmaincalculations;*/

@RestController
public class PortfolioSearchController {
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.rpController");
	
	@Autowired
	private IPortfolioSearchBAO searchBao;
	 // method to give list of portfolio on load
	@RequestMapping(value="/portfolioSearchV2", method=RequestMethod.POST)
	 public String portfolioSearchResultOnLoad(){
		 LOGGER.log(Level.DEBUG,"enter portfolioSearchResultOnLoad" );
		 String result;
		 result = getDummyPortfolioSearchResultOnLoad();
		 LOGGER.log(Level.DEBUG,"exit portfolioSearchResultOnLoad :" + result );
		 result = searchBao.getDefaultPortfolioSearchResult();
		 LOGGER.log(Level.DEBUG,"exit portfolioSearchResultOnLoad :" + result );
		 return result;
	 }
	
	 //Dummy method to get result
	 private String getDummyPortfolioSearchResultOnLoad(){
		 String text = "{\r\n  \"portfolios\": [\r\n    {\r\n      \"portfolioName\": \"portfolio name 1\",\r\n      \"advisorName\": \"advisor name 1\",\r\n      \"advisorCustRating\": \"5\",\r\n      \"portfolioType\": \"mutual funds\",\r\n      \"riskType\": \"moderate high\",\r\n      \"fee\": \"0.5%\",\r\n      \"returns\": {\r\n        \"oneMonth\": \"1%\",\r\n        \"threeMonth\": \"2%\",\r\n        \"sixMonth\": \"3%\",\r\n        \"sinceInception\": \"4%\"\r\n      },\r\n      \"advisorDesc\": \"some desc of advisor\"\r\n    },\r\n    {\r\n      \"portfolioName\": \"portfolio name 2\",\r\n      \"advisorName\": \"advisor name 2\",\r\n      \"advisorCustRating\": \"5\",\r\n      \"portfolioType\": \"mutual funds\",\r\n      \"riskType\": \"moderate high\",\r\n      \"fee\": \"0.5%\",\r\n      \"returns\": {\r\n        \"oneMonth\": \"1%\",\r\n        \"threeMonth\": \"2%\",\r\n        \"sixMonth\": \"3%\",\r\n        \"sinceInception\": \"4%\"\r\n      },\r\n      \"advisorDesc\": \"some desc of advisor\"\r\n    },\r\n    {\r\n      \"portfolioName\": \"portfolio name 3\",\r\n      \"advisorName\": \"advisor name 3\",\r\n      \"advisorCustRating\": \"5\",\r\n      \"portfolioType\": \"mutual funds\",\r\n      \"riskType\": \"moderate high\",\r\n      \"fee\": \"0.5%\",\r\n      \"returns\": {\r\n        \"oneMonth\": \"1%\",\r\n        \"threeMonth\": \"2%\",\r\n        \"sixMonth\": \"3%\",\r\n        \"sinceInception\": \"4%\"\r\n      },\r\n      \"advisorDesc\": \"some desc of advisor\"\r\n    }\r\n  ]\r\n}";
		 return text;
	 }
}
