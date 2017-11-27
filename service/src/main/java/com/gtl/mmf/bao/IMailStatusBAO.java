/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.bao;

/**
 *
 * @author trainee7
 */
public interface IMailStatusBAO {
   public  Integer updateMailStatus(String tableClassName ,String email ,boolean status);
    
}
