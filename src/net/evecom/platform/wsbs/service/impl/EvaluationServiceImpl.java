/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import net.evecom.platform.wsbs.service.EvaluationService;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

import javax.xml.namespace.QName;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 描述  省网评价系统生成代码
 * @author Madison You
 * @version v1.0
 */
public class EvaluationServiceImpl extends Stub implements EvaluationService {
    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private Vector cachedSerClasses = new Vector();
    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private Vector cachedSerQNames = new Vector();
    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private Vector cachedSerFactories = new Vector();
    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private Vector cachedDeserFactories = new Vector();

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    static OperationDesc [] operations;

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    static {
        operations = new OperationDesc[4];
        initOperationDesc1();
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    private static void initOperationDesc1(){
        OperationDesc oper;
        ParameterDesc param;
        oper = new OperationDesc();
        oper.setName("getEvaluationDetail");
        param = new ParameterDesc(new QName("", "userName"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "passWord"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "xmlStr"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new QName("", "getEvaluationDetailReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        operations[0] = oper;

        oper = new OperationDesc();
        oper.setName("getSaveEvaluation");
        param = new ParameterDesc(new QName("", "userName"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "passWord"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "xmlStr"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new QName("", "getSaveEvaluationReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        operations[1] = oper;

        oper = new OperationDesc();
        oper.setName("getEvaluationList");
        param = new ParameterDesc(new QName("", "userName"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "passWord"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "xmlStr"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new QName("", "getEvaluationListReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        operations[2] = oper;

        oper = new OperationDesc();
        oper.setName("getSaveEvaluationUrl");
        param = new ParameterDesc(new QName("", "userName"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "passWord"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "xmlStr"), ParameterDesc.IN,
                new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new QName("", "getSaveEvaluationUrlReturn"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        operations[3] = oper;

    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public EvaluationServiceImpl() throws AxisFault {
         this(null);
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public EvaluationServiceImpl(URL endpointURL, Service service) throws AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    public EvaluationServiceImpl(Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new Service();
        } else {
            super.service = service;
        }
        ((Service)super.service).setTypeMappingVersion("1.1");
    }

    /**
     * 描述:省网评价系统生成代码
     *
     * @author Madison You
     * @created 2019/10/22 11:49:00
     * @param
     * @return
     */
    protected Call createCall() throws RemoteException {
        try {
            Call _call = super._createCall();
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
            Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            return _call;
        }
        catch (Throwable _t) {
            throw new AxisFault("Failure trying to get the Call object", _t);
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
    public String getEvaluationDetail(String userName, String passWord, String xmlStr) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://impl.service.evaluation.linewell.com", "getEvaluationDetail"));
        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[] {userName, passWord, xmlStr});
            if (_resp instanceof RemoteException) {
                throw (RemoteException)_resp;
            } else {
                extractAttachments(_call);
                try {
                    return (String) _resp;
                } catch (Exception _exception) {
                    return (String) JavaUtils.convert(_resp, String.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
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
    public String getSaveEvaluation(String userName, String passWord, String xmlStr) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://impl.service.evaluation.linewell.com", "getSaveEvaluation"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[] {userName, passWord, xmlStr});
            if (_resp instanceof RemoteException) {
                throw (RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (String) _resp;
                } catch (Exception _exception) {
                    return (String) JavaUtils.convert(_resp, String.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
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
    public String getEvaluationList(String userName, String passWord, String xmlStr) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://impl.service.evaluation.linewell.com", "getEvaluationList"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[] {userName, passWord, xmlStr});
            if (_resp instanceof RemoteException) {
                throw (RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (String) _resp;
                } catch (Exception _exception) {
                    return (String) JavaUtils.convert(_resp, String.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
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
    public String getSaveEvaluationUrl(String userName, String passWord, String xmlStr) throws RemoteException {
        Call _call = createCall();
        _call.setOperation(operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://impl.service.evaluation.linewell.com", "getSaveEvaluationUrl"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[] {userName, passWord, xmlStr});
            if (_resp instanceof RemoteException) {
                throw (RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (String) _resp;
                } catch (Exception _exception) {
                    return (String) JavaUtils.convert(_resp, String.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

}
