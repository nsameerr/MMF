/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import static com.gtl.mmf.service.util.IConstants.COMA;

import com.gtl.mmf.bao.ILookupDataLoaderBAO;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.LookupDataLoader;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class used to download symbol files.
 *
 * @author trainee3
 */
public class SymbolDownloadingTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.SymbolDownloadingTask");
    @Autowired
    private ILookupDataLoaderBAO lookupDataLoaderBAO;

    public void symbolfileDownload() throws Exception {
        int counter = 0;

        //LOGGER.log(Level.INFO, "Downloading symbol files..."); 
        String[] symbolFiles = PropertiesLoader.getPropertiesValue("symbolfiles").split(COMA);
        String fileLocation = PropertiesLoader.getPropertiesValue("symbolfilepath");
        LOGGER.log(Level.INFO, "Symbol file download location:{0}", fileLocation);
        String resourcePath = LookupDataLoader.getResourcePath();

        /**
         * Loading securities from different classification from database for
         * creating Asset class mapping.
         */
        List<Map<String, String>> loadMFClassification = lookupDataLoaderBAO.loadMFMap();
        LookupDataLoader.loadMFClassification(loadMFClassification);
        LOGGER.log(Level.INFO, "MF classification Map loaded.");

        LookupDataLoader.clearClassificationList();
        LOGGER.log(Level.INFO, "Classification List Cleared.");
        LOGGER.log(Level.INFO, "----------------------------Downloading and Symbol list preparation started--------------------------");
        //Downloading Each symbol file
        for (String string : symbolFiles) {
            counter++;

            boolean downloadStatus = LookupDataLoader.downloadNSESymbol(fileLocation,
                    string, string);
            LOGGER.log(Level.INFO, "NSE symbol downloaded status {0}", downloadStatus);
            if (downloadStatus) {
                LOGGER.log(Level.INFO, "Preparing symbol list {0} ", counter + " , " + string);

                //Adding Securities from the downloaded file into classification map
                boolean prepareStatus = LookupDataLoader.prepareNewSymbolMap(fileLocation, string, counter);
                LOGGER.log(Level.INFO, "NSE symbol preparation status {0}", prepareStatus + " , " + string);
            }
        }
        LOGGER.log(Level.INFO, "Symbol files downloaded");

    }

}
