/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import java.math.BigDecimal;

/**
 *
 * @author trainee7
 */
public final class NumberToWord {
    private static final String SPACE = " ";
    private static final String AND_SPACE = " and";
    private static final String ONLY = " Only";
    private static final String RUPEES = " Rupees";
    private static final String PAISE = " Paise";
    private static final int TEN =  10;
    private static final int HUNDRED = 100;
    private static final int NINETEEN = 19;
    private static final int CRORE = 10000000;
    private static final int LAKH = 100000;
    private static final int THOUSAND = 1000;
    private static final int THREE = 3;
    private static final int ZERO = 0;
    private static final int FOUR = 4;
    private static final int TWO = 2;
    private static final int ONE = 1;
   
    private static final String[] unitdo ={"dummyval", " One", " Two", " Three", 
        " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", 
        " Twelve", " Thirteen", " Fourteen", " Fifteen",  " Sixteen", " Seventeen", 
         " Eighteen", " Nineteen"};
    private static final String[] tens =  {"dummyval", "Ten", " Twenty", 
        " Thirty", " Forty", " Fifty"," Sixty", " Seventy", " Eighty"," Ninety"};
    private static final String[] digit = {"dummyval", " Hundred", " Thousand", 
        " Lakh", " Crore"};
    
    /**
     * Count the number of digits in the input number
     * @param num input number
     * @return length of the input number
     */
    private static int numberCount(long num)
    {  
        double r;
        int cnt = ZERO;
       
        while (num > ZERO){
          r = num % TEN;
          cnt++;
          num = num / TEN;
        }
        return cnt;
    }
    /**
     * Function for Conversion of two digit
     * @param numq input number
     * @return 
     */
     private static String twonum(int numq){
        int numr, nq;
        StringBuilder twoDigitStrg = new StringBuilder();
        nq = numq / TEN;
        numr = numq % TEN;
        if (numq > NINETEEN){
            twoDigitStrg.append(tens[nq]).append(unitdo[numr]);
        }else{
            twoDigitStrg.append(unitdo[numq]);
        }
        return twoDigitStrg.toString();
    }

    /**
     * Function for Conversion of three digit
     * @param numq
     * @return 
     */
    private static String threenum(int numq){
        int numr, nq;
        StringBuilder threeDigitStrg = new StringBuilder();
        nq = numq / HUNDRED;
        numr = numq % HUNDRED;
        if (numr == 0){
            threeDigitStrg.append(unitdo[nq]).append(digit[ONE]);
        }else{
            threeDigitStrg.append(unitdo[nq]).append(digit[ONE]).append(AND_SPACE)
                    .append(twonum(numr));
        }
        return threeDigitStrg.toString();
    } 
    /**
     * 
     * @param number double variable used as input number 
     * @return number equivalent word as String 
     */
    public static String convertToWord(double number){
        int len;
        
        //Defining variables q is quotient, r is remainder
        long q = ZERO, r = ZERO;
        BigDecimal bd = new BigDecimal(String.valueOf(number));
        long num = bd.longValue();

        //Defining variables fractionalPart for finding fractionalPortion of the 
        //number rounded as two places
        double fractionalPart = Math.round((number - num) * HUNDRED) ; 
        
        StringBuilder numInWords = new StringBuilder();
        if (num <= ZERO) System.out.println("Zero or Negative number not suported for conversion");
        while (num > ZERO){
            len = numberCount(num);

            //Take the length of the number and do letter conversion
            switch (len){
                case 9:
                case 8:
                    q = num / CRORE;
                    r = num % CRORE;
                    numInWords.append(SPACE).append(twonum((int) q)).append(digit[FOUR]);
                    num = r;
                    break;
                case 7:
                case 6:
                    q=num / LAKH;
                    r=num % LAKH;
                    numInWords.append(SPACE).append(twonum((int) q)).append(digit[THREE]);
                    num = r;
                    break;
                case 5:
                case 4:
                    q=num / THOUSAND;
                    r=num % THOUSAND;
                    numInWords.append(SPACE).append(twonum((int) q)).append(digit[TWO]);
                    num = r;
                    break;
                case 3:  
                    if (len == THREE){
                        r = num;
                    }
                    numInWords.append(threenum((int) r));
                    num = ZERO;
                    break;
                case 2:
                    numInWords.append(twonum((int) num));
                    num = ZERO;
                    break;
                case 1:
                    numInWords.append(unitdo[(int)num]);
                    num = ZERO;
                    break;
                default:
                    numInWords.append("Conversion not supported");
                    break;
            }
            if (num == ZERO){
                numInWords.append(RUPEES);
                if(fractionalPart > 0){
                    numInWords.append(AND_SPACE).append(twonum((int) fractionalPart)).append(PAISE);
                }
               numInWords.append(ONLY);
            }                
        }
        // returning string with removed dummy values
        return numInWords.toString().replace("dummyval", "");
    }
}
