/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.wsbs.dao.ProjectDao;
import net.evecom.platform.wsbs.service.ProjectService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 已立项项目计划管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("projectService")
public class ProjectServiceImpl extends BaseServiceImpl implements ProjectService {
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }@Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_WSBS_PROJECT T ");
        sql.append(whereSql);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    public String getPrjCode(String XZQHCode,String codetype) {
        //350128000000   1是省级的   2 是部级的
        String PrjCode = XZQHCode;
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyMMdd");
        String dirSdfString = dirSdf.format(new Date());
        PrjCode = PrjCode + dirSdfString;
        
        List<Object> params = new ArrayList<Object>();
        StringBuffer getMaxSJCodesql = new StringBuffer(" select T.* from T_BSFW_GCJSGETCODE T ");
        getMaxSJCodesql.append(" where 1=1 ");
        getMaxSJCodesql.append(" and t.create_date = '");
        getMaxSJCodesql.append(dirSdfString);
        getMaxSJCodesql.append("' and t.codetype = '");
        getMaxSJCodesql.append(codetype);
        getMaxSJCodesql.append("' ");

        Map<String,Object> maxSJCodeMap = dao.getMapBySql(getMaxSJCodesql.toString(), params.toArray());
        String maxCode = "";
        String id = "";
        if ("1".equals(codetype)) {
            if (maxSJCodeMap!=null) {
                maxCode = maxSJCodeMap.get("MAXCODE")==null?"":maxSJCodeMap.get("MAXCODE").toString();
                id = maxSJCodeMap.get("ID")==null?"":maxSJCodeMap.get("ID").toString();
                if (StringUtils.isEmpty(maxCode)) {
                    maxCode="0001";
                }else {
                    int a = Integer.parseInt(maxCode)+1;
                    maxCode = StringUtils.leftPad(String.valueOf(a), 4, '0');
                }
            }else {
                maxCode="0001";
            }
        }else if ("2".equals(codetype)) {
            if (maxSJCodeMap!=null) {
                maxCode = maxSJCodeMap.get("MAXCODE")==null?"":maxSJCodeMap.get("MAXCODE").toString();
                id = maxSJCodeMap.get("ID")==null?"":maxSJCodeMap.get("ID").toString();
                if (StringUtils.isEmpty(maxCode)) {
                    maxCode="0001";
                }else {
                    int a = Integer.parseInt(maxCode)+1;
                    maxCode = StringUtils.leftPad(String.valueOf(a), 4, '0');
                }
            }else {
                maxCode="0001";
            }
        }
        
        PrjCode = PrjCode + maxCode ;
        
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CREATE_DATE", dirSdfString);
        variables.put("MAXCODE", maxCode);
        variables.put("CODETYPE", codetype);
        dao.saveOrUpdate(variables, "T_BSFW_GCJSGETCODE", id);
        
                
//        System.out.println(PrjCode);
        return PrjCode;
    }
    

    public String getUnitCode(String prjCode) {
//        String  serialNumber = "";
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select max(t.serial_number) as maxcode from "
                + " TB_FC_UNIT_PROJECT_INFO t where t.prj_code = '");
        sql.append(prjCode);
        sql.append("' ");
        Map<String,Object> maxSJCodeMap = dao.getMapBySql(sql.toString(), params.toArray());
        String maxCode = "";
        if (maxSJCodeMap!=null) {
            maxCode = maxSJCodeMap.get("MAXCODE")==null?"":maxSJCodeMap.get("MAXCODE").toString();
            if (StringUtils.isEmpty(maxCode)) {
                maxCode="001";
            }else {
                int a = Integer.parseInt(maxCode)+1;
                maxCode = StringUtils.leftPad(String.valueOf(a), 3, '0');
            }
        }else {
            maxCode="001";
        }
//        serialNumber = prjCode+"-"+maxCode;
                
//        System.out.println(serialNumber);
        return maxCode;
    }

    /**
     * 获取投资项目基本信息
     * @param projectCode
     * @return
     */
    public Map<String,Object> getXMJBXXBData(String projectCode){
        StringBuffer sql=new StringBuffer();
        sql.append(" select PERMIT_ITEM_CODE,GET_LAND_MODE,XMTZLY,GCFL,SFWCQYPG,TDSFDSJFA");
        sql.append(" From SPGL_XMJBXXB p where p.project_code=? ");
        return exeDataService.getFirstByJdbc(sql.toString(),new Object[]{projectCode});
    }
    
    /**
     * 
     * 描述 根据区划代码和代码类型获取工程项目有关环节（文书）编号
     * @author Luffy Cai
     * @created 2020年5月7日 下午3:58:18
     * @param PrjCode 区划行政代码
     * @param codetype 工程项目有关环节代码类型
     * @return
     */    
    public String getGcxmCode(String PrjCode,String codetype){
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyMMdd");
        String dirSdfString = dirSdf.format(new Date());
        PrjCode = PrjCode + dirSdfString;
        List<Object> params = new ArrayList<Object>();
        StringBuffer getMaxSJCodesql = new StringBuffer(" select T.* from T_BSFW_GCJSGETCODE T ");
        getMaxSJCodesql.append(" where 1=1 ");
        getMaxSJCodesql.append(" and t.create_date = '");
        getMaxSJCodesql.append(dirSdfString);
        getMaxSJCodesql.append("' and t.codetype = '");
        getMaxSJCodesql.append(codetype);
        getMaxSJCodesql.append("' ");
        Map<String,Object> maxSJCodeMap = dao.getMapBySql(getMaxSJCodesql.toString(), params.toArray());
        String maxCode = "";
        String maxHjCode = "";
        String id = "";
            if (maxSJCodeMap!=null) {
                maxCode = maxSJCodeMap.get("MAXCODE")==null?"":maxSJCodeMap.get("MAXCODE").toString();
                maxHjCode = maxSJCodeMap.get("MAXHJCODE")==null?"":maxSJCodeMap.get("MAXHJCODE").toString();
                id = maxSJCodeMap.get("ID")==null?"":maxSJCodeMap.get("ID").toString();
                if (StringUtils.isEmpty(maxCode)&&StringUtils.isEmpty(maxHjCode)) {
                    maxCode="0001";
                    maxHjCode="001";
                }else {
                    int a = Integer.parseInt(maxCode)+1;
                    maxCode = StringUtils.leftPad(String.valueOf(a), 4, '0');
                    if(StringUtils.isEmpty(maxHjCode)) {
                        maxHjCode="001";
                    }else {
                        int b = Integer.parseInt(maxHjCode)+1;
                        maxHjCode = StringUtils.leftPad(String.valueOf(b), 3, '0');
                    }
                }
            }else {
                maxCode="0001";
                maxHjCode="001";
            }
        PrjCode = PrjCode + maxCode + "-" + codetype + "-" + maxHjCode;
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CREATE_DATE", dirSdfString);
        variables.put("MAXCODE", maxCode);
        variables.put("MAXHJCODE", maxHjCode);
        variables.put("CODETYPE", codetype);
        dao.saveOrUpdate(variables, "T_BSFW_GCJSGETCODE", id);
        return PrjCode;
    }
    
    /**
     * 
     * @Description 根据报建编号获取部四库一平台施工许可证编号
     * @author Luffy Cai
     * @date 2020年11月20日
     * @param proNum
     * @return String
     */
    public String getConstrnum(String proNum) {
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String dirSdfString = dirSdf.format(new Date());
        StringBuffer getMaxSJCodesql = new StringBuffer(" select T.* from T_BSFW_GCJSGETCODE T ");
        getMaxSJCodesql.append(" where 1=1 ");
        getMaxSJCodesql.append(" and t.PRJNUM = '");
        getMaxSJCodesql.append(proNum);
        getMaxSJCodesql.append("' and t.codetype ='SX' and t.MAXHJCODE is not null");
        Map<String,Object> maxSJCodeMap = dao.getMapBySql(getMaxSJCodesql.toString(), null);
        String maxCode = "";
        String id = "";
        if(maxSJCodeMap!=null) {
            maxCode = maxSJCodeMap.get("MAXHJCODE")==null?"":maxSJCodeMap.get("MAXHJCODE").toString();
            id = maxSJCodeMap.get("ID")==null?"":maxSJCodeMap.get("ID").toString();
            if (StringUtils.isEmpty(maxCode)) {
                maxCode="001";
            }else {
                int a = Integer.parseInt(maxCode)+1;
                maxCode = StringUtils.leftPad(String.valueOf(a), 3, '0');
            }
        }else {
            maxCode="001";
        }
        String constrnum = proNum+"-SX-"+maxCode;
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CREATE_DATE", dirSdfString);
        variables.put("MAXHJCODE", maxCode);
        variables.put("CODETYPE", "SX");
        variables.put("PRJNUM", proNum);
        dao.saveOrUpdate(variables, "T_BSFW_GCJSGETCODE", id);
        return constrnum;
    }    
    
    /**
     * 
     * @Description 获取电子证照施工许可证编号
     * @author Luffy Cai
     * @date 2020年11月20日
     * @return String
     */
    public String getCertNum(String proType) {
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String dirSdfString = dirSdf.format(new Date());
        StringBuffer getMaxSJCodesql = new StringBuffer(" select T.* from T_BSFW_GCJSGETCODE T ");
        getMaxSJCodesql.append(" where 1=1 ");
        getMaxSJCodesql.append(" and t.create_date = '");
        getMaxSJCodesql.append(dirSdfString);
        getMaxSJCodesql.append("' and t.codetype ='SX' and t.MAXLISTCODE is not null");
        Map<String,Object> maxSJCodeMap = dao.getMapBySql(getMaxSJCodesql.toString(), null);
        String maxCode = "";
        String id = "";
        if(maxSJCodeMap!=null) {
            maxCode = maxSJCodeMap.get("MAXLISTCODE")==null?"":maxSJCodeMap.get("MAXLISTCODE").toString();
            id = maxSJCodeMap.get("ID")==null?"":maxSJCodeMap.get("ID").toString();
            if (StringUtils.isEmpty(maxCode)) {
                maxCode="01";
            }else {
                int a = Integer.parseInt(maxCode)+1;
                maxCode = StringUtils.leftPad(String.valueOf(a), 2, '0');
            }
        }else {
            maxCode="01";
        }
        //电子证照施工许可证编号规则：区划编码+日期+序列码+工程分类码
        String certNum = "350128" + dirSdfString + maxCode + proType;
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CREATE_DATE", dirSdfString);
        variables.put("MAXLISTCODE", maxCode);
        variables.put("CODETYPE", "SX");
        dao.saveOrUpdate(variables, "T_BSFW_GCJSGETCODE", id);
        return certNum;
    }    
    
    /**
     * 
     * @Description 根据报建号和代码类型获取工程项目编号
     * @author Luffy Cai
     * @date 2021年4月21日
     * @param proNum
     * @param type
     * @return String
     */
    public String getGcxmCodeByProNum(String proNum, String type) {
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String dirSdfString = dirSdf.format(new Date());
        StringBuffer getMaxSJCodesql = new StringBuffer(" select T.* from T_BSFW_GCJSGETCODE T ");
        getMaxSJCodesql.append(" where 1=1 ");
        getMaxSJCodesql.append(" and t.PRJNUM = '");
        getMaxSJCodesql.append(proNum);
        getMaxSJCodesql.append("' and t.codetype ='");
        getMaxSJCodesql.append(type);
        getMaxSJCodesql.append("' and t.MAXHJCODE is not null");
        Map<String,Object> maxSJCodeMap = dao.getMapBySql(getMaxSJCodesql.toString(), null);
        String maxCode = "";
        String id = "";
        if(maxSJCodeMap!=null) {
            maxCode = maxSJCodeMap.get("MAXHJCODE")==null?"":maxSJCodeMap.get("MAXHJCODE").toString();
            id = maxSJCodeMap.get("ID")==null?"":maxSJCodeMap.get("ID").toString();
            if (StringUtils.isEmpty(maxCode)) {
                maxCode="001";
            }else {
                int a = Integer.parseInt(maxCode)+1;
                maxCode = StringUtils.leftPad(String.valueOf(a), 3, '0');
            }
        }else {
            maxCode="001";
        }
        String constrnum = proNum+"-"+type+"-"+maxCode;
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CREATE_DATE", dirSdfString);
        variables.put("MAXHJCODE", maxCode);
        variables.put("CODETYPE", type);
        variables.put("PRJNUM", proNum);
        dao.saveOrUpdate(variables, "T_BSFW_GCJSGETCODE", id);
        return constrnum;
    }    
    
    
}
