/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;
/**
 * 描述 业务表名枚举类
 *
 * @author Water Guo
 * @version 1.0
 * @created 2020年12月9日 上午11:23:10
 */
public enum TableNameEnum {
    /**
     * 内资有限
     */
    T_COMMERCIAL_DOMESTIC("T_COMMERCIAL_DOMESTIC"),
    /**
     * 外资企业
     */
    T_COMMERCIAL_FOREIGN("T_COMMERCIAL_FOREIGN"),
    /**
     * 内资合伙
     */
    T_COMMERCIAL_NZ_JOINTVENTURE("T_COMMERCIAL_NZ_JOINTVENTURE"),
    /**
     * 个体
     */
    T_COMMERCIAL_INDIVIDUAL("T_COMMERCIAL_INDIVIDUAL"),
    /**
     * 分公司
     */
    T_COMMERCIAL_BRANCH("T_COMMERCIAL_BRANCH"),
    /**
     * 股份公司
     */
    T_COMMERCIAL_INTERNAL_STOCK("T_COMMERCIAL_INTERNAL_STOCK"),
    /**
     * 个独
     */
    T_COMMERCIAL_SOLELYINVEST("T_COMMERCIAL_SOLELYINVEST");
    /**
     * 常量
     */
    private  String val="";
    /**
     * 构造方法
     * @param value
     */
    private TableNameEnum(String value) {
       this.val=value;
    }

    /**
     * 获取val
     * @return
     */
    public String getVal(){
        return val;
    }
}
