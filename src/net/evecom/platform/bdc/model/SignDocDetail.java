/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.model;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import net.evecom.platform.bdc.util.BdcQlcSignUtil;

import java.util.List;

/**
 * 描述   不动产全流程签章文档详细实体类
 *
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:19:11
 */
public class SignDocDetail {
    /**
     * 文档filekey和docId必填其一
     */
    private String docFilekey;
    /**
     * 位置信息
     */
    private List<SignPo> signPos;

    public String getDocFilekey() {
        return docFilekey;
    }

    public void setDocFilekey(String docFilekey) {
        this.docFilekey = docFilekey;
    }

    public List<SignPo> getSignPos() {
        return signPos;
    }

    public void setSignPos(List<SignPo> signPos) {
        this.signPos = signPos;
    }

//    public static void main(String[] args) {
//        SignPo signPo=new SignPo();
//        SignPo signPo1=new SignPo();
//        SignDocDetail signDocDetail=new SignDocDetail();
//        List<SignPo> signPos= Lists.newArrayList();
//        signPos.add(signPo);
//        signPos.add(signPo1);
//        signDocDetail.setSignPos(signPos);
//        signDocDetail.setDocFilekey("1");
//        String json= JSON.toJSONString(signDocDetail);
//        System.out.println(json);
//
//    }

}
