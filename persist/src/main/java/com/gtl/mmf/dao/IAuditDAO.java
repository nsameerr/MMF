package com.gtl.mmf.dao;

import java.util.List;
import java.util.Set;

import com.gtl.mmf.entity.PortfolioAuditTb;
import com.gtl.mmf.entity.PortfolioDetailsAuditTb;
import com.gtl.mmf.entity.PortfolioSecuritiesAuditTb;
import com.gtl.mmf.entity.PortfolioTb;

public interface IAuditDAO {

    PortfolioTb loadPortfolio(Integer portfolioId);

    List<Object[]> loadPortfolioDet(Integer portfolioId);

    Integer insertPortfolioTbAudit(PortfolioAuditTb portfolioAuditTb);

    Integer insertPortfolioDetailTbAudit(PortfolioDetailsAuditTb portfolioDetailsAuditTb);

    void insertPortfolioSecuritiesTbAudit(Set<PortfolioSecuritiesAuditTb> portfolioSecuritiesAuditTbs);

    public List<Object[]> loadPortfolioSec(Integer portfolioDetId);

}
