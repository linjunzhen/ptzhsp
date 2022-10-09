/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * PcipDataInputWsImplServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.evecom.platform.pcipws.model;

import java.net.MalformedURLException;

import net.evecom.platform.ecipws.model.EcipDataInputWsImplServiceSoapBindingStub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-24 下午4:06:07
 */
public class PcipDataInputWsImplServiceSoapBindingStub extends org.apache.axis.client.Stub 
implements net.evecom.platform.pcipws.model.PcipDataInputWs {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(PcipDataInputWsImplServiceSoapBindingStub.class);
    /**
     * 
     */
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    /**
     * 
     */
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    /**
     * 
     */
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    /**
     * 
     */
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    /**
     * 
     */
    static org.apache.axis.description.OperationDesc [] operations;

    static {
        operations = new org.apache.axis.description.OperationDesc[5];
        initOperationDesc1();
    }

    private static void initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("collectReportBlack");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "userId"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "password"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "operType"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "report"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "Result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("collectReportCase");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "userId"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "password"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "operType"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "report"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "Result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("collectReportSmSupport");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "userId"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "password"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "operType"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "report"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "Result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("collectReportPermit");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "userId"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "password"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "operType"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "report"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "Result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("collectReportSpotcheck");
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "userId"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "password"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "operType"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(
                new javax.xml.namespace.QName("", "report"), 
                org.apache.axis.description.ParameterDesc.IN, 
                new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "Result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        operations[4] = oper;

    }

    public PcipDataInputWsImplServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public PcipDataInputWsImplServiceSoapBindingStub(java.net.URL endpointURL, 
            javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public PcipDataInputWsImplServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public java.lang.String collectReportBlack(java.lang.String userId, 
            java.lang.String password, java.lang.String operType, java.lang.String report) 
                    throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(
                new javax.xml.namespace.QName("http://webservice.pcipws.evecom.net/", 
                        "collectReportBlack"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userId, password, operType, report});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String collectReportCase(java.lang.String userId, 
            java.lang.String password, java.lang.String operType, java.lang.String report) 
                    throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(
                new javax.xml.namespace.QName("http://webservice.pcipws.evecom.net/", 
                        "collectReportCase"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userId, password, operType, report});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String collectReportSmSupport(java.lang.String userId, 
            java.lang.String password, java.lang.String operType, java.lang.String report) 
                    throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(
                new javax.xml.namespace.QName("http://webservice.pcipws.evecom.net/", 
                        "collectReportSmSupport"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userId, password, operType, report});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String collectReportPermit(java.lang.String userId, 
            java.lang.String password, java.lang.String operType, java.lang.String report) 
                    throws java.rmi.RemoteException {
//        if (super.cachedEndpoint == null) {
//            throw new org.apache.axis.NoEndPointException();
//        }
        if (super.cachedEndpoint == null) {
//          throw new org.apache.axis.NoEndPointException();
          try {
              String url = "http://172.16.51.21:7777/pcipws/webservice/dataInput?wsdl";
              super.cachedEndpoint = new java.net.URL(url);
          } catch (MalformedURLException e) {
              // TODO Auto-generated catch block
              log.error(e.getMessage());
          }
      }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(
                new javax.xml.namespace.QName("http://webservice.pcipws.evecom.net/", 
                        "collectReportPermit"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userId, password, operType, report});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String collectReportSpotcheck(java.lang.String userId, 
            java.lang.String password, java.lang.String operType, java.lang.String report) 
                    throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(
                new javax.xml.namespace.QName("http://webservice.pcipws.evecom.net/", 
                        "collectReportSpotcheck"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userId, password, operType, report});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
