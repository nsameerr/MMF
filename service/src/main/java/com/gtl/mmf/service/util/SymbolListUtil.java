/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.BSE;
import static com.gtl.mmf.service.util.IConstants.BSEFO;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import static com.gtl.mmf.service.util.IConstants.NSE;
import static com.gtl.mmf.service.util.IConstants.NSEDEBT;
import static com.gtl.mmf.service.util.IConstants.NSEFO;
import static com.gtl.mmf.service.util.IConstants.NSEMF;
import static com.gtl.mmf.service.util.IConstants.TAX_PLANNING;
import static com.gtl.mmf.service.util.IConstants.TEXT_BALANCED;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT_LIQUID;
import static com.gtl.mmf.service.util.IConstants.TEXT_FNO;
import static com.gtl.mmf.service.util.IConstants.TEXT_GOLD;
import static com.gtl.mmf.service.util.IConstants.TEXT_INDEX;
import static com.gtl.mmf.service.util.IConstants.TEXT_INTERNATIONAL;
import static com.gtl.mmf.service.util.IConstants.TEXT_MUTUAL_FUND;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author 07958
 */
public class SymbolListUtil {

    public static SymbolRecordVO getSymbolRecordVO(final String assetType, final String ticker) {
        List<SymbolRecordVO> symbolList = SymbolListUtil.getSymbolList(assetType);
        Collections.sort(symbolList, new Comparator<SymbolRecordVO>() {
            public int compare(SymbolRecordVO o1, SymbolRecordVO o2) {
                return o1.getTicker().compareTo(o2.getTicker());
            }
        });
        SymbolRecordVO symbolToSearch = new SymbolRecordVO();
        symbolToSearch.setTicker(ticker);
        int symbolIndex = Collections.binarySearch(symbolList, symbolToSearch, new Comparator<SymbolRecordVO>() {
            public int compare(SymbolRecordVO o1, SymbolRecordVO o2) {
                return o1.getTicker().compareTo(o2.getTicker());
            }
        });
        return (symbolIndex > ZERO ? symbolList.get(symbolIndex) : symbolToSearch);
    }

    public static SymbolRecordVO getSymbolRecordVO(final String assetType, final SymbolRecordVO symbolToSearch) {
        List<SymbolRecordVO> symbolList = SymbolListUtil.getSymbolList(assetType);
        Collections.sort(symbolList, new Comparator<SymbolRecordVO>() {
            public int compare(SymbolRecordVO o1, SymbolRecordVO o2) {
                return o1.getTicker().compareTo(o2.getTicker());
            }
        });
//        SymbolRecordVO symbolToSearch = new SymbolRecordVO();
//        symbolToSearch.setTicker(symbolToSearch);
        int symbolIndex = Collections.binarySearch(symbolList, symbolToSearch, new Comparator<SymbolRecordVO>() {
            public int compare(SymbolRecordVO o1, SymbolRecordVO o2) {
                return o1.getTicker().compareTo(o2.getTicker());
//                return o1.getTicker().compareTo(o2.getTicker());
            }
        });
        return symbolList.get(symbolIndex);
    }

    public static List<SymbolRecordVO> getSymbolList(final String assetType) {
        return assetType.equalsIgnoreCase(EQUITY_DIVERSIFIED) ? LookupDataLoader.getDiversifiedEquityList()
                : assetType.equalsIgnoreCase(TEXT_DEBT) ? LookupDataLoader.getDebtDataList()
                : assetType.equalsIgnoreCase(TEXT_FNO) ? LookupDataLoader.getDerivativesList()
//                : assetType.equalsIgnoreCase(TEXT_MUTUAL_FUND) ? LookupDataLoader.getActualMFSymbolDataList()
                : assetType.equalsIgnoreCase(TEXT_GOLD) ? LookupDataLoader.getGoldDataList()
                : assetType.equalsIgnoreCase(TEXT_INTERNATIONAL) ? LookupDataLoader.getInternationalDataList()
                : assetType.equalsIgnoreCase(TEXT_INDEX) ? LookupDataLoader.getIndexList()
                : assetType.equalsIgnoreCase(MIDCAP) ? LookupDataLoader.getMidcapDataList()
                : assetType.equalsIgnoreCase(TEXT_BALANCED) ? LookupDataLoader.getDiversifiedEquityList()
                : assetType.equalsIgnoreCase(TEXT_DEBT_LIQUID) ? LookupDataLoader.getDebtLiquidDataList()
                : assetType.equalsIgnoreCase(TAX_PLANNING) ? LookupDataLoader.getTaxPlanningDataList()
                : null;
    }

    public static Set<SymbolRecordVO> getWatchesList(List<PortfolioSecurityVO> securities, short assetType) {
        String assetClassType = PortfolioUtil.getAssetClassType(assetType);
        return SymbolListUtil.getWatchesList(securities, SymbolListUtil.getSymbolList(assetClassType));
    }

    public static Set<SymbolRecordVO> getWatchesList(List<PortfolioSecurityVO> securities, List<SymbolRecordVO> actualSymbols) {
        Set<SymbolRecordVO> watches = new HashSet<SymbolRecordVO>();
        for (PortfolioSecurityVO portfolioSecurityVO : securities) {
            SymbolRecordVO symbolRecordVO = new SymbolRecordVO();
            if (portfolioSecurityVO.getSecurityId() != null) {
                symbolRecordVO.setTicker(portfolioSecurityVO.getSecurityId());
                int symbolIndex = Collections.binarySearch(actualSymbols, symbolRecordVO, new Comparator<SymbolRecordVO>() {
                    public int compare(SymbolRecordVO o1, SymbolRecordVO o2) {
                        return o1.getTicker().compareTo(o2.getTicker());
                    }
                });
                if (symbolIndex >= ZERO) {
                    watches.add(actualSymbols.get(symbolIndex));
                }
            }
        }
        return watches;
    }

    public static boolean isSymbolRecordValid(SymbolRecordVO symbolRecordVO, String query) {
        boolean valid = false;
        if (symbolRecordVO.getVenue().equalsIgnoreCase(NSE) || symbolRecordVO.getVenue().equalsIgnoreCase(BSE)) {
            valid = StringUtils.containsIgnoreCase(symbolRecordVO.getScripDescription(), query)
                    || StringUtils.containsIgnoreCase(symbolRecordVO.getScripName(), query);
        } else if (symbolRecordVO.getVenue().equalsIgnoreCase(NSEDEBT)) {
            valid = StringUtils.containsIgnoreCase(symbolRecordVO.getScripName(), query);
        } else if (symbolRecordVO.getVenue().equalsIgnoreCase(NSEFO) || symbolRecordVO.getVenue().equalsIgnoreCase(BSEFO)) {
            valid = StringUtils.containsIgnoreCase(symbolRecordVO.getScripName(), query);
        } else if (symbolRecordVO.getVenue().equalsIgnoreCase(NSEMF)) {
            valid = StringUtils.containsIgnoreCase(symbolRecordVO.getScripDescription(), query)
                    || StringUtils.containsIgnoreCase(symbolRecordVO.getScripName(), query);
        }
        return valid;
    }
}
