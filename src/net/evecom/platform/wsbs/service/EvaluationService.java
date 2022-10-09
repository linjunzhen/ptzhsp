/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 描述
 * @author Madison You
 * @version v1.0
 */
public interface EvaluationService extends Remote {
    /**
     * 描述:省网评价系统接口代码
     *
     * @author Madison You
     * @created 2019/10/22 11:42:00
     * @param
     * @return
     */
    public String getEvaluationDetail(String userName, String passWord, String xmlStr)
            throws RemoteException;
    /**
     * 描述:省网评价系统接口代码
     *
     * @author Madison You
     * @created 2019/10/22 11:42:00
     * @param
     * @return
     */
    public String getSaveEvaluation(String userName, String passWord, String xmlStr)
            throws RemoteException;
    /**
     * 描述:省网评价系统接口代码
     *
     * @author Madison You
     * @created 2019/10/22 11:42:00
     * @param
     * @return
     */
    public String getEvaluationList(String userName, String passWord, String xmlStr)
            throws RemoteException;
    /**
     * 描述:省网评价系统接口代码
     *
     * @author Madison You
     * @created 2019/10/22 11:42:00
     * @param
     * @return
     */
    public String getSaveEvaluationUrl(String userName, String passWord, String xmlStr)
            throws RemoteException;
}
