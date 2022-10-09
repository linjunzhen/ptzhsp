/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import net.evecom.platform.wsbs.service.EvaluationService;
import net.evecom.platform.wsbs.service.EvaluationSubService;
import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 描述  省网评价系统生成代码
 * @author Madison You
 * @version v1.0
 */
public class EvaluationSubServiceImpl extends Service
        implements EvaluationSubService {
    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:48:00
     * @param
     * @return
     */
    public EvaluationSubServiceImpl() {
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:48:00
     * @param
     * @return
     */
    public EvaluationSubServiceImpl(EngineConfiguration config) {
        super(config);
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:48:00
     * @param
     * @return
     */
    public EvaluationSubServiceImpl(String wsdlLoc, QName sName)
            throws ServiceException {
        super(wsdlLoc, sName);
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private String EvaluationServiceAsmx_address =
            "http://www.fjbs.gov.cn:82/WaiLianService/services/EvaluationService.asmx";

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public String getEvaluationServiceAsmxAddress() {
        return EvaluationServiceAsmx_address;
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private String EvaluationServiceAsmxWSDDServiceName = "EvaluationService.asmx";

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public String getEvaluationServiceAsmxWSDDServiceName() {
        return EvaluationServiceAsmxWSDDServiceName;
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public void setEvaluationServiceAsmxWSDDServiceName(String name) {
        EvaluationServiceAsmxWSDDServiceName = name;
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public EvaluationService getEvaluationServiceAsmx() throws ServiceException {
       URL endpoint;
        try {
            endpoint = new URL(EvaluationServiceAsmx_address);
        }
        catch (MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getEvaluationServiceAsmx(endpoint);
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public EvaluationService getEvaluationServiceAsmx(URL portAddress) {
        try {
            EvaluationServiceImpl stub =
                    new EvaluationServiceImpl(portAddress, this);
            stub.setPortName(getEvaluationServiceAsmxWSDDServiceName());
            return stub;
        }
        catch (AxisFault e) {
            return null;
        }
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public void setEvaluationServiceAsmxEndpointAddress(String address) {
        EvaluationServiceAsmx_address = address;
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (EvaluationService.class.isAssignableFrom(serviceEndpointInterface)) {
                EvaluationServiceImpl stub = new EvaluationServiceImpl(
                        new URL(EvaluationServiceAsmx_address), this);
                stub.setPortName(getEvaluationServiceAsmxWSDDServiceName());
                return stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " +
                "" + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public Remote getPort(QName portName, Class serviceEndpointInterface)
            throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("EvaluationService.asmx".equals(inputPortName)) {
            return getEvaluationServiceAsmx();
        }
        else  {
            Remote stub = getPort(serviceEndpointInterface);
            ((Stub) stub).setPortName(portName);
            return stub;
        }
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public QName getServiceName() {
        return new QName("http://www.fjbs.gov.cn:82/WaiLianService/services/EvaluationService.asmx"
                , "EvaluationSubService");
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private HashSet ports = null;

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:50:00
     * @param
     * @return
     */
    public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName(
                    "http://www.fjbs.gov.cn:82/WaiLianService/services/EvaluationService.asmx",
                    "EvaluationService.asmx"));
        }
        return ports.iterator();
    }


    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:50:00
     * @param
     * @return
     */
    public void setEndpointAddress(String portName, String address) throws ServiceException {
        if ("EvaluationServiceAsmx".equals(portName)) {
                    setEvaluationServiceAsmxEndpointAddress(address);
        } else {
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:50:00
     * @param
     * @return
     */
    public void setEndpointAddress(QName portName, String address)
            throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
