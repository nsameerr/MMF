/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */

package com.gtl.mmf.common;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;


public class CaptchaService {
    
    private static ImageCaptchaService instance = null;
 
    public static ImageCaptchaService getInstance(){
        if(instance == null){
            CustomImageEngine customImageEngine = new CustomImageEngine();
            instance = new DefaultManageableImageCaptchaService 
                        (new FastHashMapCaptchaStore(),  customImageEngine, 180, 0x186a0, 0x124f8);
        }
        return instance;
    }
}
