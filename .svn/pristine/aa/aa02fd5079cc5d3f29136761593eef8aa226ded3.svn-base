/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.model;


import net.evecom.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 描述  工程建设120流程
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class GcjsPush120 extends  GcjsPushStatusTemplate {

    /**
     * 根据环节名称获取推送状态标志位
     * @param flowDatas
     * @return
     */
    @Override
    protected int getStatus(Map<String, Object> flowDatas) {
        //如果前面已经赋值办理状态了，直接采用前面赋值的状态就好了；
        String blzt= StringUtil.getString(flowDatas.get("BLZT"));
        if(StringUtils.isNotEmpty(blzt)){
            return Integer.parseInt(blzt);
        }
        //退回补件，退回补件对应状态为6
        String eflowIsbackPatch = StringUtil.getString(flowDatas.get("EFLOW_ISBACK_PATCH"));
        if (Objects.equals(eflowIsbackPatch,"true")){
            return 6;
        }
        int status=0;
        // 当前提交环节
        String curNodeName = StringUtil.getString(flowDatas.get("EFLOW_CURUSEROPERNODENAME"));
        String exeId =  StringUtil.getString(flowDatas.get("EFLOW_EXEID"));

        if(Objects.equals(curNodeName,"开始")){
            //补件再提交对应状态为7
            int lastBlzt=getSpsxblxxxxb(exeId);
            if(lastBlzt==6){
                status=7;
            }else{
                status=1;
            }
        }else if(Objects.equals(curNodeName,"受理")){
            status=3;
        }else if(Objects.equals(curNodeName,"审查")){

        }else if(Objects.equals(curNodeName,"决定")) {
            String eflowIsPass = StringUtil.getString(flowDatas.get("EFLOW_IS_PASS"));
            if(Objects.equals("0",eflowIsPass)){
                status = 13;
            }else{
                status = 11;
            }
        }
        return status;
    }
}
