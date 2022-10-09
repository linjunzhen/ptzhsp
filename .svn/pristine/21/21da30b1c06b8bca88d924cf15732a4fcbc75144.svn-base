/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * PcipDataInputWsImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.evecom.platform.pcipws.model;
/**
 * 
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-24 下午4:05:24
 */
public class PcipDataInputWsImplServiceLocator extends org.apache.axis.client.Service 
implements net.evecom.platform.pcipws.model.PcipDataInputWsImplService {

    public PcipDataInputWsImplServiceLocator() {
    }


    public PcipDataInputWsImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PcipDataInputWsImplServiceLocator(java.lang.String wsdlLoc, 
            javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PcipDataInputWsImplPort
    /**
     * 
     */
    private java.lang.String PcipDataInputWsImplPort_address = 
            "http://www.pingtancredit.gov.cn/pcipws/webservice/dataInput";

    public java.lang.String getPcipDataInputWsImplPortAddress() {
        return PcipDataInputWsImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    /**
     * 
     */
    private java.lang.String PcipDataInputWsImplPortWSDDServiceName = "PcipDataInputWsImplPort";

    public java.lang.String getPcipDataInputWsImplPortWSDDServiceName() {
        return PcipDataInputWsImplPortWSDDServiceName;
    }

    public void setPcipDataInputWsImplPortWSDDServiceName(java.lang.String name) {
        PcipDataInputWsImplPortWSDDServiceName = name;
    }

    public net.evecom.platform.pcipws.model.PcipDataInputWs getPcipDataInputWsImplPort() 
            throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PcipDataInputWsImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPcipDataInputWsImplPort(endpoint);
    }

    public net.evecom.platform.pcipws.model.PcipDataInputWs getPcipDataInputWsImplPort(
            java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            net.evecom.platform.pcipws.model.PcipDataInputWsImplServiceSoapBindingStub _stub = 
                    new net.evecom.platform.pcipws.model.PcipDataInputWsImplServiceSoapBindingStub(
                            portAddress, this);
            _stub.setPortName(getPcipDataInputWsImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPcipDataInputWsImplPortEndpointAddress(java.lang.String address) {
        PcipDataInputWsImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (net.evecom.platform.pcipws.model.PcipDataInputWs.class.isAssignableFrom(serviceEndpointInterface)) {
                net.evecom.platform.pcipws.model.PcipDataInputWsImplServiceSoapBindingStub _stub = 
                        new net.evecom.platform.pcipws.model.PcipDataInputWsImplServiceSoapBindingStub(
                                new java.net.URL(PcipDataInputWsImplPort_address), this);
                _stub.setPortName(getPcipDataInputWsImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " 
        + (serviceEndpointInterface == null ? 
                "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, 
            Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PcipDataInputWsImplPort".equals(inputPortName)) {
            return getPcipDataInputWsImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.pcipws.evecom.net/", "PcipDataInputWsImplService");
    }
    /**
     * 
     */
    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.pcipws.evecom.net/", "PcipDataInputWsImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) 
            throws javax.xml.rpc.ServiceException {
        
if ("PcipDataInputWsImplPort".equals(portName)) {
            setPcipDataInputWsImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
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
