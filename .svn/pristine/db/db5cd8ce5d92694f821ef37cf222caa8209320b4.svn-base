/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.esb;

/**
 * 
 * 描述 ESB选择的XPath
 * @author Allin Lin
 * @created 2021年1月25日 下午3:43:57
 */
public class ESBXPath {
    
    /**
     * 
     */
    private static final String XPATH_TO_ROW = "/soap:Envelope/soap:Body"
            + "/out:business/resultset[@name=\"retrieve\"]/row";
    
    /**
     * 
     */
    private static final String XPATH_TO_RESULTSET = "/soap:Envelope/soap:Body"
            + "/out:business/resultset[@name=\"retrieve\"]";
    
    /**
     * 
     */
    private static final String XPATH_TO_INFORMATION = "/soap:Envelope/soap:Body/out:business/result[@information]";
   
    /**
     * 
     */
    private static final String XPATH_TO_ERROR = "/soap:Envelope/soap:Body/soap:Fault/faultstring/error";
    
    
    /**
     * 
     */
    private static final String XPATH_TO_ROWCOUNT = "/soap:Envelope/soap:Body/out:business/result[@rowCount]";
   
    /**
     * 
     */
    private static final String XPATH_TO_PAGES = "/soap:Envelope/soap:Body/out:business/result[@pages]";

    /**
     * YLZ ESB里选择到Row的XPATH
     *
     * @return /soap:Envelope/soap:Body/out:business/resultset[@name="retrieve"]/row
     */
    public static String xPathToRow() {
        return XPATH_TO_ROW;
    }

    /**
     * YLZ ESB里选择到Resultset的XPATH
     *
     * @return /soap:Envelope/soap:Body/out:business/resultset[@name="retrieve"]
     */
    public static String xPathToResultSet(){
        return XPATH_TO_RESULTSET;
    }

    /**
     * YLZ ESB里按条件exp选择到Row的XPATH
     *
     * @param exp 选择条件
     * @return /soap:Envelope/soap:Body/out:business/resultset[@name="retrieve"]/row[exp]
     */
    public static String xPathToRowWith(String exp) {
        return XPATH_TO_ROW + "[" + exp + "]";
    }

    /**
     * YLZ ESB里选择到Information的XPATH
     *
     * @return /soap:Envelope/soap:Body/out:business/result[@information]
     */
    public static String xPathToInformation(){
        return XPATH_TO_INFORMATION;
    }

    /**
     * YLZ ESB里选择到Error的XPATH
     *
     * @return /soap:Envelope/soap:Body/soap:Fault/faultstring/error
     */
    public static String xPathToError(){
        return XPATH_TO_ERROR;
    }

    /**
     * YLZ ESB里选择到RowCount的XPATH
     *
     * @return /soap:Envelope/soap:Body/out:business/result[@rowCount]
     */
    public static String xPathToRowcount(){
        return XPATH_TO_ROWCOUNT;
    }

    public static String xPathToPages() {
        return XPATH_TO_PAGES;
    }
}
