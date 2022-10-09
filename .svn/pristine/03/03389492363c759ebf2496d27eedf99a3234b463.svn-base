/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.sign;

import net.evecom.platform.zzhy.model.TableNameEnum;

import java.util.Objects;

/**
 * 描述   根据业务表名获取短信发送类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class SignMsgFactory {
    /**
     * 根据业务表名获取短信发送类
     * @param tableName
     * @return
     */
    public static SignContent createSignContent(String tableName){
        SignContent signContent=null;
        if(Objects.equals(tableName, TableNameEnum.T_COMMERCIAL_DOMESTIC.getVal())){
            signContent=new CompanySignContent();
        }else if(Objects.equals(tableName, TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal())){
            signContent=new NzJointventureSignContent();
        }
        return signContent;
    }
}
