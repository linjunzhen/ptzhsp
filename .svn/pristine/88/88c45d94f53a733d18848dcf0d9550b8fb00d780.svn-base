/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.system.dao.FormValidateDao;
import net.evecom.platform.system.service.FormValidateService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2015-3-23 上午10:43:49
 */
@Service("formValidateService")
public class FormValidateServiceImpl extends BaseServiceImpl implements FormValidateService {

    /**
     * 所引入的dao
     */
    @Resource
    private FormValidateDao dao;
    /**
     * 
     * 描述 覆盖获取实体dao方法
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 根据验证配置ID删除数据
     * @author Danto Huang
     * @created 2015-3-23 上午11:17:20
     * @param ruleId
     */
    public void removeByFormId(String fromId){
        dao.removeByFormId(fromId);
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select D.*,T.FORM_NAME from T_MSJW_SYSTEM_VALIDATE_RULE D ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_VALIDATE_FORM T ON D.FORM_ID=T.FORM_ID ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据验证规则Id获取规则信息
     * @author Danto Huang
     * @created 2015-3-23 下午4:22:16
     * @param formId
     * @return
     */
    public List findRuleInfos(String ruleId){
        Map<String,Object> pro = this.getByJdbc("T_MSJW_SYSTEM_VALIDATE_RULE",
                new String[]{"RULE_ID"},new Object[]{ruleId});
        String ruleInfo = (String) pro.get("RULES");
        return JSON.parseArray(ruleInfo, Map.class);
    }
    
    /**
     * 
     * 描述  根据表单编码获取所有表单字段验证规则
     * @author Danto Huang
     * @created 2015-3-24 下午2:17:58
     * @param formCode
     * @return
     */
    public List<Map<String,Object>>  findColumnRule(String formCode){
        String sql = "select * from T_MSJW_SYSTEM_VALIDATE_RULE t where "
                + "t.form_id=(select s.form_id from t_msjw_system_validate_form s where s.form_code=? )"
                + " order by t.rule_sn asc";
        return dao.findBySql(sql, new Object[] { formCode }, null);
    }
    /**
     * 
     * 描述 数据格式验证
     * @author Danto Huang
     * @created 2015-3-25 上午9:26:12
     * @param checkMap
     * @return
     */
    public Map<String,Object> checkFormData(Map checkMap){
        Map<String,Object>  result = new HashMap<String,Object>();
        String formCode = checkMap.get("BUS_TABLENAME").toString();
        //获取平台类型
        String applyPlat = (String) checkMap.get("APPLY_PLAT");
        if(applyPlat.equals("2")||applyPlat.equals("3")){
            result.put("SUCCESS", true);
            return result;
        }
        String applyUserId = (String) checkMap.get("APPLY_USERID");
        if(StringUtils.isEmpty(applyUserId)){
            result.put("MSG", "请登录后，再提交申请！");
            result.put("COLUMN_NAME", "");
            result.put("SUCCESS", false);
            return result;
        }
        List<Map<String, Object>> columnList = this.findColumnRule(formCode);
        boolean isSuccess = true;
        for(Map map : columnList){
            String notNull = map.get("NOTNULL").toString();
            List list = new ArrayList();
            if(map.get("RULES")!=null){
                list = JSON.parseArray(map.get("RULES").toString(), Map.class);
            }
            if(checkMap.get(map.get("COLUMN_CODE"))!=null&&checkMap.get(map.get("COLUMN_CODE"))!=""){
                String columnValue = checkMap.get(map.get("COLUMN_CODE")).toString();
                for(int i=0;i<list.size();i++){
                    Map rule = (Map) list.get(i);
                    if(rule.get("rule")!=null){
                        String regex = rule.get("rule").toString();
                        boolean match = Pattern.matches(regex, columnValue);
                        if(!match){
                            result.put("COLUMN_CODE", map.get("COLUMN_CODE"));
                            result.put("COLUMN_NAME", map.get("COLUMN_NAME"));
                            result.put("MSG", rule.get("msg"));
                            isSuccess = false;
                            break;
                        }
                    }
                }
            }else if(notNull.equals("1")){
                result.put("COLUMN_CODE", map.get("COLUMN_CODE"));
                result.put("COLUMN_NAME", map.get("COLUMN_NAME"));
                result.put("MSG", "不能为空！");
                isSuccess = false;
            }else if(map.get("DEPENDENCE")!=null){
                String dependence = map.get("DEPENDENCE").toString();
                String dependenceValue = map.get("DEPENDENCE_VALUE").toString();
                if(checkMap.get(dependence)!=null&&checkMap.get(dependence).equals(dependenceValue)){
                    result.put("COLUMN_CODE", map.get("COLUMN_CODE"));
                    result.put("COLUMN_NAME", map.get("COLUMN_NAME"));
                    result.put("MSG", "不能为空！");
                    isSuccess = false;
                }
            }
            if(!isSuccess)break;
        }
        result.put("SUCCESS", isSuccess);
        return result;
    }

    /**
     * 
     * 描述 获取某个表单验证规则的最大排序
     * @author Danto Huang
     * @created 2015-7-22 下午4:16:43
     * @param fromId
     * @return
     */
    public int getMaxSn(String fromId){
        return dao.getMaxSn(fromId);
    }
    
    /**
     * 
     * 描述  更新排序字段
     * @author Danto Huang
     * @created 2015-7-22 下午4:19:47
     * @param ruleIds
     */
    public void updateSn(String[] ruleIds){
        dao.updateSn(ruleIds);
    }
}
