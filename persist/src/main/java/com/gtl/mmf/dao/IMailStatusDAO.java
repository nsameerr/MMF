/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.dao;

/**
 *
 * @author trainee7
 */
public interface IMailStatusDAO {
    public Integer updateMailSendStatus(String tablename,String email,boolean mailStatus) throws Exception;
}
