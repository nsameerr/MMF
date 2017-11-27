package com.gtl.mmf.bao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.gtl.mmf.bao.IAuditBAO;
import com.gtl.mmf.dao.IAuditDAO;
import com.gtl.mmf.entity.PortfolioAuditTb;
import com.gtl.mmf.entity.PortfolioDetailsAuditTb;
import com.gtl.mmf.entity.PortfolioSecuritiesAuditTb;
import com.gtl.mmf.entity.PortfolioTb;

public class AuditBAOImpl implements IAuditBAO {

    @Autowired
    private IAuditDAO iAuditDAO;

    public PortfolioTb loadPortfolio(Integer portfolioId) {
        return iAuditDAO.loadPortfolio(portfolioId);
    }

    public List<Object[]> loadPortfolioDet(Integer portfolioId) {
        return iAuditDAO.loadPortfolioDet(portfolioId);
    }

    public Integer insertPortfolioTbAudit(PortfolioAuditTb portfolioAuditTb) {
        return iAuditDAO.insertPortfolioTbAudit(portfolioAuditTb);
    }

    public Integer insertPortfolioDetailTbAudit(PortfolioDetailsAuditTb portfolioDetailsAuditTb) {
        return iAuditDAO.insertPortfolioDetailTbAudit(portfolioDetailsAuditTb);
    }

    public void insertPortfolioSecuritiesTbAudit(Set<PortfolioSecuritiesAuditTb> portfolioSecuritiesAuditTbs) {
        iAuditDAO.insertPortfolioSecuritiesTbAudit(portfolioSecuritiesAuditTbs);
    }

    public List<Object[]> loadPortfolioSec(Integer portfolioDetId) {
        return iAuditDAO.loadPortfolioSec(portfolioDetId);
    }
}
