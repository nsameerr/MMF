package com.gtl.mmf.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.gtl.mmf.bao.ILookupDataLoaderBAO;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.IndexBseTb;
import com.gtl.mmf.entity.IndexNseTb;
import com.gtl.mmf.entity.MasterSecurityClassificationTb;
import com.gtl.mmf.entity.MidcapBseTb;
import com.gtl.mmf.entity.MidcapNseTb;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to read CSV file and convert data into corresponding
 * objects
 *
 * @author trainee8
 */
public class CSVParser implements IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.CSVParser");
    private static final String INPUT_STREAM_IS_NULL = "INPUT_STREAM_IS_NULL";
    private static final int TO_GET_EMPTY_DATA = -1;

    /**
     *
     * @param dataInStream
     * @param benchmark
     * @return
     * @throws IOException
     */
    public static boolean parseAndPersistCSV(InputStream dataInStream, int benchmark) throws IOException {
        boolean isFirstRow = false;
        BufferedReader dataBuffReader = null;
        String row = null;
        List<MasterSecurityClassificationTb> dataList = null;
        if (dataInStream == null) {
            throw new IllegalArgumentException(INPUT_STREAM_IS_NULL);
        }
        dataBuffReader = new BufferedReader(new InputStreamReader(dataInStream));
        dataList = new ArrayList<MasterSecurityClassificationTb>();
        while ((row = dataBuffReader.readLine()) != null) {

            // To skip first row from the csv.
            if (!isFirstRow) {
                isFirstRow = true;
                continue;
            }
            fillDataList(row, dataList, benchmark);
        }
        dataBuffReader.close();
        dataInStream.close();
        int count = saveObject(dataList);
        return count != ZERO;
    }

    private static List<MasterSecurityClassificationTb> fillDataList(String rawData, List<MasterSecurityClassificationTb> dataList, int benchmark) {
        String dataArray[] = null;
        dataArray = rawData.split(COMA, TO_GET_EMPTY_DATA);
        return dataList;
    }

    /**
     * *
     * Converting CSV data into MasterSecurityClassificationTb
     *
     * @param dataArray
     * @return
     */
    private static MasterSecurityClassificationTb convertToObject(
            String[] dataArray) {
        MasterSecurityClassificationTb securityClassificationTb = new MasterSecurityClassificationTb();
        securityClassificationTb.setAmc(dataArray[ZERO].replaceAll(DOUBLE_QUOTE,
                EMPTY_STRING).trim());
        securityClassificationTb.setCodeNo(dataArray[ONE].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setSchemeName(dataArray[2].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setSchemeType(dataArray[3].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setSchemeCategory(dataArray[4].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setSchemeMinimumAmount(dataArray[6]
                .replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());

        try {
            if (!dataArray[7].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim()
                    .isEmpty()) {
                securityClassificationTb.setLaunchDate(DateUtil.stringToDate(
                        dataArray[7].replaceAll(DOUBLE_QUOTE, EMPTY_STRING)
                        .trim(), DD_MMM_YYYY));
            }
            if (!dataArray[8].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim()
                    .isEmpty()) {
                securityClassificationTb.setClosureDDate(DateUtil.stringToDate(
                        dataArray[8].replaceAll(DOUBLE_QUOTE, EMPTY_STRING)
                        .trim(), DD_MMM_YYYY));
            }
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        }
        securityClassificationTb.setClosedFlag(dataArray[9].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setIsinDivReinvestment(dataArray[11]
                .replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setIsinDivPayout(dataArray[12].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setIsinGrowth(dataArray[13].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        securityClassificationTb.setMmfClassification(dataArray[14].replaceAll(
                DOUBLE_QUOTE, EMPTY_STRING).trim());
        return securityClassificationTb;
    }

    /**
     * Saving Master security objects created from CSV file
     *
     * @param dataList
     * @return
     */
    private static int saveObject(List<MasterSecurityClassificationTb> dataList) {
        ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        int count = lookupDataLoaderBAO.saveMasterSecurityList(dataList);
        return count;
    }

    /**
     * Saving Midcap BSE objects created from CSV file
     *
     * @param dataList
     * @return
     */
    private static int saveMidcapBseTb(List<MidcapBseTb> dataList) {
        ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        int count = lookupDataLoaderBAO.saveMasterMidCapBseList(dataList);
        return count;
    }

    /**
     * Saving Midcap NSE objects created from CSV file
     *
     * @param dataList
     * @return
     */
    private static int saveMidcapNseTb(List<MidcapNseTb> dataList) {
        ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        int count = lookupDataLoaderBAO.saveMasterMidCapNseList(dataList);
        return count;
    }

    /**
     * Saving Index NSE objects created from CSV file
     *
     * @param dataList
     * @return
     */
    private static int saveIndexNseTb(List<IndexNseTb> dataList) {
        ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        int count = lookupDataLoaderBAO.saveMasterIndexNseList(dataList);
        return count;
    }

    /**
     * Saving Index BSE objects created from CSV file
     *
     * @param dataList
     * @return
     */
    private static int saveIndexBseTb(List<IndexBseTb> dataList) {
        ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        int count = lookupDataLoaderBAO.saveMasterIndexBseList(dataList);
        return count;
    }

    private static int saveIfcMicrMappingTb(List<IfcMicrMappingTb> dataList) {
        ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        int count = lookupDataLoaderBAO.saveIfcMicrMappingTbList(dataList);
        return count;
    }

    /**
     * Parsing MasterSecurity Classification,midcap Bse,midcap Nse,index Bse,
     * index NSE CSV files into corresponding objects
     *
     * @param dataInStream
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean parseAndPersistCSVData(InputStream dataInStream, String fileName) throws IOException {
        boolean isFirstRow = false;
        int count = ZERO;
        BufferedReader dataBuffReader = null;
        String row = null;
        List<MasterSecurityClassificationTb> dataList = null;
        List<MidcapBseTb> midcapBseList = new ArrayList<MidcapBseTb>();
        List<MidcapNseTb> midcapNseList = new ArrayList<MidcapNseTb>();
        List<IndexBseTb> indexBseTbList = new ArrayList<IndexBseTb>();
        List<IndexNseTb> indexNseTbList = new ArrayList<IndexNseTb>();
        List<IfcMicrMappingTb> micrMappingTbList = new ArrayList<IfcMicrMappingTb>();

        if (dataInStream == null) {
            throw new IllegalArgumentException(INPUT_STREAM_IS_NULL);
        }
        dataBuffReader = new BufferedReader(new InputStreamReader(dataInStream));
        dataList = new ArrayList<MasterSecurityClassificationTb>();
        while ((row = dataBuffReader.readLine()) != null) {

            // To skip first row from the csv.
            if (!isFirstRow) {
                isFirstRow = true;
                continue;
            }

            String dataArray[] = null;
            if (fileName.equalsIgnoreCase(IFSC_MICR_MAPPING)) {
                dataArray = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            } else {
                dataArray = row.split(COMA, TO_GET_EMPTY_DATA);
            }

            //Converting CSV data into Mapping objects
            if (fileName.equalsIgnoreCase(INDEX_NSE)) {
                indexNseTbList.add(convertToIndexNseTb(dataArray));
            } else if (fileName.equalsIgnoreCase(INDEX_BSE)) {
                indexBseTbList.add(convertToIndexBseTb(dataArray));
            } else if (fileName.equalsIgnoreCase(MIDCAP_BSE)) {
                midcapBseList.add(convertToMidcapBseTb(dataArray));
            } else if (fileName.equalsIgnoreCase(MIDCAP_NSE)) {
                midcapNseList.add(convertToMidcapNseTb(dataArray));
            } else if (fileName.equalsIgnoreCase(AMFI_FILE)) {
                dataList.add(convertToObject(dataArray));
            } else if (fileName.equalsIgnoreCase(IFSC_MICR_MAPPING)) {
                micrMappingTbList.add(convertToIfcMicrMappingTb(dataArray));
            }
        }

        dataBuffReader.close();
        dataInStream.close();

        //Saving Mapping objects
        if (fileName.equalsIgnoreCase(INDEX_NSE)) {
            count = saveIndexNseTb(indexNseTbList);
        } else if (fileName.equalsIgnoreCase(INDEX_BSE)) {
            count = saveIndexBseTb(indexBseTbList);
        } else if (fileName.equalsIgnoreCase(MIDCAP_BSE)) {
            count = saveMidcapBseTb(midcapBseList);
        } else if (fileName.equalsIgnoreCase(MIDCAP_NSE)) {
            count = saveMidcapNseTb(midcapNseList);
        } else if (fileName.equalsIgnoreCase(AMFI_FILE)) {
            count = saveObject(dataList);
        } else if (fileName.equalsIgnoreCase(IFSC_MICR_MAPPING)) {
            count = saveIfcMicrMappingTb(micrMappingTbList);
        }
        return count != ZERO;
    }

    private static MidcapBseTb convertToMidcapBseTb(String[] dataArray) {
        MidcapBseTb midCapBse = new MidcapBseTb();
        midCapBse.setScripCode(dataArray[ZERO].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        midCapBse.setCompanyName(dataArray[ONE].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        midCapBse.setIsin(dataArray[3].trim());
        return midCapBse;
    }

    private static MidcapNseTb convertToMidcapNseTb(String[] dataArray) {
        MidcapNseTb midCapNse = new MidcapNseTb();
        midCapNse.setIsinCode(dataArray[INDEX_POS_FOUR].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        midCapNse.setCompanyName(dataArray[ZERO].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        return midCapNse;
    }

    private static IndexBseTb convertToIndexBseTb(String[] dataArray) {
        IndexBseTb indexBseTb = new IndexBseTb();
        indexBseTb.setScripCode(dataArray[ZERO].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        indexBseTb.setScripName(dataArray[ONE].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        return indexBseTb;
    }

    private static IndexNseTb convertToIndexNseTb(String[] dataArray) {
        IndexNseTb indexNseTb = new IndexNseTb();
        indexNseTb.setSymbol(dataArray[ZERO].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        return indexNseTb;
    }

    private static IfcMicrMappingTb convertToIfcMicrMappingTb(String[] dataArray) {
        IfcMicrMappingTb ifcMicrMappingTb = new IfcMicrMappingTb();
        if (dataArray.length == 11) {
            ifcMicrMappingTb.setBank(dataArray[ZERO] == null ? EMPTY_STRING : dataArray[ZERO].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setIfsc(dataArray[ONE] == null ? EMPTY_STRING : dataArray[1].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setMicr(dataArray[TWO] == null ? EMPTY_STRING : dataArray[2].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setBranch(dataArray[3] == null ? EMPTY_STRING : dataArray[3].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setAddress(dataArray[4] == null ? EMPTY_STRING : dataArray[4].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setContact(dataArray[5] == null ? EMPTY_STRING : dataArray[5].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setCity(dataArray[6] == null ? EMPTY_STRING : dataArray[6].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setDistrict(dataArray[7] == null ? EMPTY_STRING : dataArray[7].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
            ifcMicrMappingTb.setState(dataArray[8] == null ? EMPTY_STRING : dataArray[8].replaceAll(DOUBLE_QUOTE, EMPTY_STRING).trim());
        }

        return ifcMicrMappingTb;
    }

}
