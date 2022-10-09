/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.model;

import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.util.BdcQlcSignUtil;

/**
 * 描述   不动产全流程签章流程文档实体类
 *
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:19:11
 */
public class SignDoc {

    /**
     * 签署文档fileKey
     */
    private String docFilekey;
    /**
     * 文档名称
     */
    private String docName;
    /**
     * 排序
     */
    private int docOrder;

    public SignDoc(){
        docName="A-"+ BdcQlcSignUtil.getRandomNumOfSign();
        docOrder=1;
    }

    public String getDocFilekey() {
        return docFilekey;
    }

    public void setDocFilekey(String docFilekey) {
        this.docFilekey = docFilekey;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public int getDocOrder() {
        return docOrder;
    }

    public void setDocOrder(int docOrder) {
        this.docOrder = docOrder;
    }
}
