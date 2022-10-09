/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2015-3-23 上午10:42:39
 */
public interface FormValidateService extends BaseService {

    /**
     * 
     * 描述 根据验证配置ID删除数据
     * @author Danto Huang
     * @created 2015-3-23 上午11:17:20
     * @param ruleId
     */
    public void removeByFormId(String fromId);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据验证规则Id获取规则信息
     * @author Danto Huang
     * @created 2015-3-23 下午4:22:16
     * @param ruleId
     * @return
     */
    public List<Map<String,Object>> findRuleInfos(String ruleId);
    /**
     * 
     * 描述  根据表单编码获取所有表单字段验证规则
     * @author Danto Huang
     * @created 2015-3-24 下午2:17:58
     * @param formCode
     * @return
     */
    public List<Map<String,Object>>  findColumnRule(String formCode);
    
    /**
     * 
     * 描述  数据格式验证
     * @author Danto Huang
     * @created 2015-3-24 下午5:09:15
     * @param request
     * @return
     */
    public Map<String,Object> checkFormData(Map checkMap);
    /**
     * 
     * 描述 获取某个表单验证规则的最大排序
     * @author Danto Huang
     * @created 2015-7-22 下午4:16:43
     * @param fromId
     * @return
     */
    public int getMaxSn(String fromId);
    /**
     * 
     * 描述  更新排序字段
     * @author Danto Huang
     * @created 2015-7-22 下午4:19:47
     * @param ruleIds
     */
    public void updateSn(String[] ruleIds);
}
