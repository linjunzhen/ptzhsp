/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.esb;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 描述 选择参数，方便管理
 * @author Allin Lin
 * @created 2021年1月25日 下午3:30:44
 */
public class ESBParams {
    
    /**
     * 参数
     */
    private List<String> params;
    
    /**
     * 参数值
     */
    private List<String> values;

    public ESBParams() {
        params = new ArrayList<String>();
        values = new ArrayList<String>();
    }

    public ESBParams(String param, String value) {
        this();
        add(param, value);
    }

    public void add(String param, String value) {
        params.add(param);
        values.add(value);
    }

    public String[] getParams() {
        return params.toArray(new String[]{});
    }

    public String[] getValues() {
        return values.toArray(new String[]{});
    }
}
