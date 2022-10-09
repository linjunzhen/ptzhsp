/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.ecipws.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import net.evecom.core.util.StringUtil;
import net.evecom.platform.entinfo.model.EcipEntInfoManagementServiceSoapBindingStub;
import net.evecom.platform.entinfo.model.EcipResponse;
import net.evecom.platform.entinfo.model.EntCompositeInfo;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * 描述
 * @author Rider Chen
 * @created 2016年12月20日 上午8:08:00
 */
public class EntInfoWebServiceUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EntInfoWebServiceUtil.class);
    
    public static void main(String[] args) {
        EcipEntInfoManagementServiceSoapBindingStub stub;
        try {
            stub = new EcipEntInfoManagementServiceSoapBindingStub();
            stub.setUsername("evecom");
            stub.setPassword("Evecom@ecippss2015");
            EntCompositeInfo item = new EntCompositeInfo();
            List<EntCompositeInfo> itemsList = new ArrayList<EntCompositeInfo>();
            itemsList.add(item);
            try {
                EntCompositeInfo[]  items =  new  EntCompositeInfo[1];
                items[0] = item;
                EcipResponse ecr = stub.updateEntInfo(items);
                log.info(ecr.getErrorMsg());
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        } catch (AxisFault e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
    }
}
