/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * EcipDataInputWsImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.evecom.platform.ecipws.model;
/**
 * 
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-24 下午4:05:32
 */
public class EcipDataInputWsImplServiceLocator 
extends org.apache.axis.client.Service implements EcipDataInputWsImplService {

    public EcipDataInputWsImplServiceLocator() {
    }


    public EcipDataInputWsImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EcipDataInputWsImplServiceLocator(java.lang.String wsdlLoc, 
            javax.xml.namespace.QName sName) 
                    throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EcipDataInputWsImplPort
    /**
     * 
     */
    private java.lang.String EcipDataInputWsImplPort_address = "http://59.61.182.42/ecipws_pt/webservice/dataInput";

    public java.lang.String getEcipDataInputWsImplPortAddress() {
        return EcipDataInputWsImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    /**
     * 
     */
    private java.lang.String EcipDataInputWsImplPortWSDDServiceName = "EcipDataInputWsImplPort";

    public java.lang.String getEcipDataInputWsImplPortWSDDServiceName() {
        return EcipDataInputWsImplPortWSDDServiceName;
    }

    public void setEcipDataInputWsImplPortWSDDServiceName(java.lang.String name) {
        EcipDataInputWsImplPortWSDDServiceName = name;
    }

    public EcipDataInputWs getEcipDataInputWsImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EcipDataInputWsImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEcipDataInputWsImplPort(endpoint);
    }

    public EcipDataInputWs getEcipDataInputWsImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            EcipDataInputWsImplServiceSoapBindingStub _stub = 
                    new EcipDataInputWsImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getEcipDataInputWsImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEcipDataInputWsImplPortEndpointAddress(java.lang.String address) {
        EcipDataInputWsImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (EcipDataInputWs.class.isAssignableFrom(serviceEndpointInterface)) {
                EcipDataInputWsImplServiceSoapBindingStub _stub = 
                        new EcipDataInputWsImplServiceSoapBindingStub(
                                new java.net.URL(EcipDataInputWsImplPort_address), this);
                _stub.setPortName(getEcipDataInputWsImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException(
                "There is no stub implementation for the interface:  " 
        + (serviceEndpointInterface == null ? 
                "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, 
            Class serviceEndpointInterface) 
                    throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("EcipDataInputWsImplPort".equals(inputPortName)) {
            return getEcipDataInputWsImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.ecipws.evecom.net/", "EcipDataInputWsImplService");
    }

    /**
     * 
     */
    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.ecipws.evecom.net/", "EcipDataInputWsImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, 
            java.lang.String address) 
                    throws javax.xml.rpc.ServiceException {
        
if ("EcipDataInputWsImplPort".equals(portName)) {
            setEcipDataInputWsImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, 
            java.lang.String address) 
                    throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
