/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.service.util;

import com.sun.jersey.api.client.Client;

/**
 *
 * @author 09860
 */
public class RestClient {
    private static Client client = null;
    private RestClient(){};
    public static Client getClient(){
        if(client == null){
            client = Client.create();       
        }
        return client;
    }
}
