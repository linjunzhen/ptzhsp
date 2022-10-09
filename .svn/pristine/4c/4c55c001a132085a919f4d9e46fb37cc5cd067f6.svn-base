/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tempuri;

/**
 * 描述
 * @author Watson Zheng
 * @created 2016年12月14日 下午10:25:39
 */
/**
 * 描述
 * @author Watson Zheng
 * @created 2016年12月14日 下午10:26:23
 */
/**
 * 描述
 * 
 * @author Watson Zheng
 * @created 2016年12月14日 下午10:26:24
 */
public class ExchangeLocator extends org.apache.axis.client.Service implements org.tempuri.Exchange {

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:25:42
     */
    public ExchangeLocator() {
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:25:45
     * @param config
     */
    public ExchangeLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:25:51
     * @param wsdlLoc
     * @param sName
     * @throws javax.xml.rpc.ServiceException
     */
    public ExchangeLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
            throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ExchangeSoap
    // TODO
    /**
     * 描述
     */
    private java.lang.String ExchangeSoap_address = "http://10.23.251.199:11121/Services/Exchange.asmx";
    // private java.lang.String ExchangeSoap_address =
    // "http://218.66.59.166:11124/FZLicense/Services/Exchange.asmx";

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:25:56
     * @return
     * @see org.tempuri.Exchange#getExchangeSoapAddress()
     */
    public java.lang.String getExchangeSoapAddress() {
        return ExchangeSoap_address;
    }

    // The WSDD service name defaults to the port name.
    /**
     * 描述
     */
    private java.lang.String ExchangeSoapWSDDServiceName = "ExchangeSoap";

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:01
     * @return
     */
    public java.lang.String getExchangeSoapWSDDServiceName() {
        return ExchangeSoapWSDDServiceName;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:04
     * @param name
     */
    public void setExchangeSoapWSDDServiceName(java.lang.String name) {
        ExchangeSoapWSDDServiceName = name;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:06
     * @return
     * @throws javax.xml.rpc.ServiceException
     * @see org.tempuri.Exchange#getExchangeSoap()
     */
    public org.tempuri.ExchangeSoap getExchangeSoap() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ExchangeSoap_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getExchangeSoap(endpoint);
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:10
     * @param portAddress
     * @return
     * @throws javax.xml.rpc.ServiceException
     * @see org.tempuri.Exchange#getExchangeSoap(java.net.URL)
     */
    public org.tempuri.ExchangeSoap getExchangeSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.ExchangeSoapStub _stub = new org.tempuri.ExchangeSoapStub(portAddress, this);
            _stub.setPortName(getExchangeSoapWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:13
     * @param address
     */
    public void setExchangeSoapEndpointAddress(java.lang.String address) {
        ExchangeSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.ExchangeSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.ExchangeSoapStub _stub = new org.tempuri.ExchangeSoapStub(
                        new java.net.URL(ExchangeSoap_address), this);
                _stub.setPortName(getExchangeSoapWSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
                + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
            throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ExchangeSoap".equals(inputPortName)) {
            return getExchangeSoap();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:19
     * @return
     * @see org.apache.axis.client.Service#getServiceName()
     */
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "Exchange");
    }

    /**
     * 描述
     */
    private java.util.HashSet ports = null;

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:30
     * @return
     * @see org.apache.axis.client.Service#getPorts()
     */
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ExchangeSoap"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午10:26:44
     * @param portName
     * @param address
     * @throws javax.xml.rpc.ServiceException
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address)
            throws javax.xml.rpc.ServiceException {

        if ("ExchangeSoap".equals(portName)) {
            setExchangeSoapEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
            throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
