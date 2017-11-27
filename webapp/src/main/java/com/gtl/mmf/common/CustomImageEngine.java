/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * creted by 07662
 */

package com.gtl.mmf.common;

import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import java.awt.Color;
import java.awt.Font;


public class CustomImageEngine extends ListImageCaptchaEngine {

    @Override
    protected void buildInitialFactories() {
        // Range of characters to generate
        WordGenerator wgen =new RandomWordGenerator ("abdefghijmnqrt123456789ABDEFGHLMNQRTY");
        // Range of text colors
        RandomRangeColorGenerator cgen =  new RandomRangeColorGenerator(
                                              new int[]{20, 25}, 
                                              new int[]{50, 120}, new int[]{50, 255});
        // Number of challenge characters   
        TextPaster textPaster = new RandomTextPaster(new Integer(6), new Integer(6), cgen, Boolean.valueOf(true));
        // Background and size of the image.
        GradientBackgroundGenerator background = new GradientBackgroundGenerator(187,40,Color.gray,Color.lightGray);
        // Alternate background options.
        /*FunkyBackgroundGenerator background = new FunkyBackgroundGenerator (new Integer(250), new Integer(50));
        UniColorBackgroundGeneratorx background = new UniColorBackgroundGenerator (250,50);*/
        // Text fonts   
        Font fontsList[] = {new Font("Open Sans", 0, 10), new Font("sans-serif", 0, 10)};
        FontGenerator fontGenerator = new RandomFontGenerator( new Integer(20), new Integer(25), fontsList);
        WordToImage wordToImage = new ComposedWordToImage(fontGenerator, background, textPaster);
        addFactory(new GimpyFactory(wgen, wordToImage));
    }
    
}
