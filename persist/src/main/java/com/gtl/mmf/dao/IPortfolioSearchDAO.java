package com.gtl.mmf.dao;

import java.util.List;


import com.gtl.mmf.entity.PortfolioTb;


public interface IPortfolioSearchDAO {

	public List<PortfolioTb> getDefaultSearchResult();

	public int getAdvisorAverageRating(Integer advisorId);

}
