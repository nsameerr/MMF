/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author 07960
 */
public final class TemplateUtil {

    private static VelocityEngine velocityEngine = null;
    private static VelocityContext velocityContext = null;
    private static TemplateUtil templateUtil = null;

    private TemplateUtil() {
    }

    public static TemplateUtil getInstance() {
        if (templateUtil == null) {
            templateUtil = new TemplateUtil();
        }
        return templateUtil;
    }

    public VelocityContext getVelocityContext() {
        if (velocityContext == null) {
            velocityContext = new VelocityContext();
        }
        return velocityContext;
    }

    public VelocityEngine getVelocityEngine() {
        if (velocityEngine == null) {
            velocityEngine = new VelocityEngine();
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.init();
        }
        return velocityEngine;
    }
}
