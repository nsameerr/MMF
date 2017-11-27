package com.gtl.linkedin.util;

import java.util.ResourceBundle;

public final class PropertiesLoader {
    private static final String PROPERTIES_FILE = "resources";
    private static ResourceBundle reBundle;

    private PropertiesLoader(){}
    static{
        reBundle = ResourceBundle.getBundle(PROPERTIES_FILE);
    }

    public static String getPropertiesValue(String key)  {
        return reBundle.getString(key);
    }
    /*public static PropertiesLoader proLoader = null;
    public static Properties properties = null;

    private PropertiesLoader(){};

    public static PropertiesLoader getInstance(){
            if(proLoader == null){
                    proLoader = new PropertiesLoader();
                    properties = new Properties();
            }
            return proLoader;
    }

    public Properties getProperties(){
            InputStream in = getClass().getResourceAsStream("resources.properties");
            try {
                    properties.load(in);
            } catch (IOException e) {
                    e.printStackTrace();
            }finally{
                    try {
                            in.close();
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
            }

            return properties;
    }*/	
}
