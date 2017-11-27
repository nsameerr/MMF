/**
 *
 */
package com.gtl.mmf.service.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 08237
 *
 */
public class BeanLoader implements ApplicationContextAware {

    protected static ApplicationContext applicationContext = null;

    private BeanLoader() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanLoader.applicationContext = applicationContext;
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }
}
