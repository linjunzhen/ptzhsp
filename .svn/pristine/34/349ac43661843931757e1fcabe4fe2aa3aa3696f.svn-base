/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import net.evecom.core.sm.Sm4Utils;
/**
 * 
 * 描述：重写spring中的PropertyPlaceholderConfigurer类对加密的属性值进行处理
 * @author Rider Chen
 * @created 2019年8月1日 下午4:06:41
 */
public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    /**
     * 重写父类方法，解密指定属性名对应的属性值
     */
    @Override
    protected String convertProperty(String propertyName,String propertyValue){
        if(isEncryptPropertyVal(propertyName)){
            Sm4Utils sm4 = new Sm4Utils();
            return sm4.decryptDataCBC(propertyValue);//调用解密方法
        }else{
            return propertyValue;
        }
    }
    /**
     * 判断属性值是否需要解密
     * @param propertyName
     * @return
     */
    private boolean isEncryptPropertyVal(String propertyName) {
        if (propertyName.startsWith("jdbc.url")) {
            return true;
        } else if (propertyName.startsWith("jdbc.username")) {
            return true;
        } else if (propertyName.startsWith("jdbc.password")) {
            return true;
        } else {
            return false;
        }
    }
}
