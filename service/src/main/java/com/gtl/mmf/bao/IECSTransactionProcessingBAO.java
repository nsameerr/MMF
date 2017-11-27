/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

/**
 *
 * @author trainee8
 */
public interface IECSTransactionProcessingBAO {

    public void createFileForECSDebtFile();

    public void deleteDataFromEcsRegistrationDataTable();

    public void readExcelFileForDebtFromECS();

    public void readRegistrationTextFileFromECS();

    public void createECSTransmittalSheet();

    public void processApprovedMandates();
    
    public void readCsvFileForDebitPaymentResponseFromECS();
    
}
