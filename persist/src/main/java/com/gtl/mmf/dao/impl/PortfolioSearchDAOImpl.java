package com.gtl.mmf.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gtl.mmf.dao.IPortfolioSearchDAO;
import com.gtl.mmf.entity.PortfolioTb;

public class PortfolioSearchDAOImpl implements IPortfolioSearchDAO{
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.PortfolioSearchDAOImpl");
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<PortfolioTb> getDefaultSearchResult() {
		String hql = "Select advisorId from MasterAdvisorTb";
		//LOGGER.info(hql);
		List<PortfolioTb> defaultList = new ArrayList<PortfolioTb>();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(query.list() != null ){
		
			List<Integer> advisorIdList = query.list();
	        Iterator<Integer> itr = advisorIdList.iterator();
	        while(itr.hasNext()){
	        	int advisorId = (Integer) itr.next();
	        	//LOGGER.info("advisorId = "+ advisorId);
	        	List<PortfolioTb> ports = getAdvisorsPortfolios(advisorId);
	        	Iterator<PortfolioTb> pitr = ports.iterator();
	        	while(pitr.hasNext()){
	        		PortfolioTb p  =  (PortfolioTb) pitr.next();
	        		defaultList.add(p);
	        		//LOGGER.info("poetfolioNmae "+ p.getPortfolioName());
	        	}
	        }
	        return defaultList;
		} else {
			return defaultList;
		}
        
		
	}
	
	private List<PortfolioTb> getAdvisorsPortfolios(int advisorId) {
        String hql = "FROM PortfolioTb WHERE masterAdvisorTb =:advisorId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.setInteger("advisorId", advisorId).list();
    }

	public int getAdvisorAverageRating(Integer advisorId) {
		String hql = "select avg(obj.ratingOverall) from CustomerAdvisorMappingTb as obj where masterAdvisorTb =:advisorId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("advisorId", advisorId);
        List listResult = query.list();
        Number number = (Number) listResult.get(0);
        number = (number ==  null) ? 0 : number;
        if(query.list() != null || query.list().size() != 0 ) {
        	
            LOGGER.log(Level.INFO,"avg cust rating for advisor "+ advisorId+ " rating:"+number.intValue());
            return (Integer) number;
        } else {
        	return 0;
        }
	}

}
