/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import com.google.common.collect.Lists;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.project.dao.ScclDao;
import net.evecom.platform.project.service.ProjectWebsiteService;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.ParamAware;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 生成材料
 * 描述
 * @author Adrian Bian
 * @created 2020年1月6日 下午12:15:43
 */
@Service("projectWebsiteService")
public class ProjectWebsiteServiceImpl extends BaseServiceImpl implements ProjectWebsiteService {

    /**
     * 所引入的dao
     */
    @Resource
    private ScclDao dao;
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     *
     */
    @Resource
    private DepartServiceItemService departServiceItemService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * dictionaryService
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 根据项目类型id获取第一个阶段id
     * @param typeId
     * @return
     */
    public String getFirstStageIdByTypeId(String typeId){
        StringBuffer sql=new StringBuffer(" SELECT * ");
        sql.append("  FROM T_FLOW_CONFIG_STAGE T ");
        sql.append(" WHERE 1=1 AND TYPE_ID=? ORDER BY T.TREE_SN ");
        Map<String,Object> stage=exeDataService.getFirstByJdbc(sql.toString(),new Object[]{typeId});
        String stageId=StringUtil.getString(stage.get("STAGE_ID"));
        return stageId;
    }
    /**
     * 根据阶段id获取事项信息
     * @param stageId
     * @return
     */
    public Map<String,Object> getServiceItemByStageId(String stageId){
        StringBuffer sql=new StringBuffer(" SELECT * ");
        sql.append("  FROM T_FLOW_CONFIG_STAGE T LEFT JOIN T_WSBS_SERVICEITEM F ON F.ITEM_ID=T.ITEM_ID  ");
        sql.append(" WHERE 1=1 AND STAGE_ID=? and  f.fwsxzt='1' ORDER BY T.TREE_SN ");
        Map<String,Object> serviceItem=dao.getByJdbc(sql.toString(),new Object[]{stageId});
        String itemCode=StringUtil.getString(serviceItem.get("ITEM_CODE"));
        serviceItem.putAll(serviceItemService.getItemInfoByItemCode(itemCode));
        serviceItem.putAll(setFlowNodeName(StringUtil.getString(serviceItem.get("ITEM_ID"))));
        return serviceItem;
    }
    /**
     * 根据项目projectCode获取项目信息
     * @param projectCode
     * @return
     */
    public Map<String,Object> loadLocalXMJBXXBByProjectCode(String projectCode){
        StringBuffer sql=new StringBuffer();
        sql.append(" SELECT * FROM ");
        sql.append(" SPGL_XMJBXXB X");
        sql.append(" WHERE X.PROJECT_CODE=?  ");
        sql.append(" ORDER BY X.CREATE_TIME DESC ");
        Map<String,Object> projectInfo=exeDataService.getFirstByJdbc(sql.toString(),new Object[]{projectCode});
        return projectInfo;
    }
    /**
     * 项目工程办事指南设置流程节点名称
     * @param itemId
     * @return
     */
    private Map<String,Object> setFlowNodeName(String itemId){
        PagingBean pagingBean=new PagingBean(1,15);
        SqlFilter filter=new SqlFilter(pagingBean);
        Map<String,Object> serviceItem=new HashMap<>();
        if(StringUtils.isNotEmpty(itemId)){
            filter.addFilter("Q_T.ITEM_ID_=",itemId);
            filter.addSorted("T.RECORD_ID","asc");
            List<Map<String, Object>> list = departServiceItemService.getDefNode(filter);
            List<Map<String, Object>> resultList =  new ArrayList<Map<String,Object>>();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                String nodeName = map.get("NODE_NAME")==null?
                        "":map.get("NODE_NAME").toString();
                if ("开始".equals(nodeName)
                        ||"结束".equals(nodeName)
                        ||"待受理".equals(nodeName)
                        ||"网上预审".equals(nodeName)
                        ||"受理自动跳转".equals(nodeName)) {

                }else{
                    resultList.add(map);
                }
            }
            if (resultList.size() == 2) {
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查与决定");
            }else if (resultList.size() == 3) {
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
            }else if (resultList.size() == 4) {
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
                resultList.get(3).put("NODE_NAME", "制证与送达");
            }else if (resultList.size() == 5) {
                resultList.get(0).put("NODE_NAME", "受理");
                resultList.get(1).put("NODE_NAME", "审查");
                resultList.get(2).put("NODE_NAME", "决定");
                resultList.get(3).put("NODE_NAME", "制证");
                resultList.get(4).put("NODE_NAME", "送达");
            }
            serviceItem.put("HJMXS", resultList);
        }
        return serviceItem;
    }
    /**
     * 查找文章列表分页
     * @param variable
     * @return
     */
    public List<Map<String,Object>> findContentForPage(Map<String,Object> variable){
        String begin= StringUtil.getString(variable.get("start"));
        String limit= StringUtil.getString(variable.get("limit"));
        int limitNum=Integer.parseInt(limit);
        PagingBean pagingBean=new PagingBean(Integer.parseInt(begin),limitNum);
        String paras= StringUtil.getString(variable.get("paras"));
        String dsid= StringUtil.getString(variable.get("dsid"));
        Map<String,Object> modelDs=dao.getByJdbc("T_CMS_TPL_MODELDS",
                new String[]{"DSID"},new Object[]{dsid});
        String sql = (String)modelDs.get("DSCODE");
        Object[] paraR=new Object[]{};
        if (paras != null && !"".equals(paras)) {
            paraR = paras.split(":");
        }
        List<Map<String,Object>> contents=dao.findBySql(sql,paraR,pagingBean);
        variable.put("total",pagingBean.getTotalItems());
        return contents;
    }
    /**
     * 查找当前登录账号的所有项目
     * @param variable
     * @return
     */
    public List<Map<String,Object>> findMyProjectList(Map<String,Object> variable){
        String begin= StringUtil.getString(variable.get("start"));
        String limit= StringUtil.getString(variable.get("limit"));
        String projectCode=StringUtil.getString(variable.get("projectCode"));
        String projectName=StringUtil.getString(variable.get("projectName"));
        String projectType=StringUtil.getString(variable.get("projectType"));

        String yhzh=StringUtil.getString(AppUtil.getLoginMember().get("YHZH"));
        int limitNum=Integer.parseInt(limit);
        PagingBean pagingBean=new PagingBean(Integer.parseInt(begin),limitNum);
        StringBuffer sql = new StringBuffer("select T.ID, T.PROJECT_CODE, T.PROJECT_NAME, T.FLOW_CATE_ID, ");
        sql.append(" T.FLOW_CATE_NAME, T.CREATE_TIME ");
        sql.append(" FROM SPGL_XMJBXXB t ");
        sql.append(" WHERE T.CREATOR_ID ='");
        sql.append(yhzh).append("'");
        if(StringUtils.isNotEmpty(projectCode)){
            sql.append(" AND T.PROJECT_CODE like '%").append(projectCode).append("%'");
        }
        if(StringUtils.isNotEmpty(projectName)){
            sql.append(" AND T.PROJECT_NAME like '%").append(projectName).append("%'");
        }
        if(StringUtils.isNotEmpty(projectType)){
            sql.append(" AND T.PROJECT_TYPE='").append(projectType).append("'");
        }

        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        List<Map<String,Object>> contents=dao.findBySql(sql.toString(),null,pagingBean);
        variable.put("total",pagingBean.getTotalItems());
        return contents;
    }
    /**
     * 查找登录账号的项目信息
     * @param variable
     * @return
     */
    public List<Map<String,Object>> findMyProjectInfo(Map<String,Object> variable){
        List<Map<String,Object>> myProjectInfos=Lists.newArrayList();
        if(AppUtil.getLoginMember()==null){
            return myProjectInfos;
        }
        String yhzh = StringUtil.getString(AppUtil.getLoginMember().get("YHZH"));
        String myProjectName=StringUtil.getString(variable.get("myProjectName"));
        String myProjectCode=StringUtil.getString(variable.get("myProjectCode"));
        StringBuffer sql = new StringBuffer("SELECT * FROM ");
        sql.append("  SPGL_XMJBXXB x where   x.creator_id=? ");
        if(StringUtils.isNotEmpty(myProjectCode)){
            sql.append(" and x.project_code like '%").append(myProjectCode).append("%'");
        }
        if(StringUtils.isNotEmpty(myProjectName)){
            sql.append(" and x.project_name like '%").append(myProjectName).append("%'");
        }
        return dao.findBySql(sql.toString(),new Object[]{yhzh},null);
    }
    /**
     * 根据项目主键id获取项目信息
     * @param id
     * @return
     */
    public Map<String,Object> getProjectInfosById(String id){
        StringBuffer sql=new StringBuffer(" select X.*,D.*,X.PROJECT_TYPE,dIC.DIC_NAME,dic1.dic_name as sxlx ");
        sql.append(" FROM SPGL_XMJBXXB X ");
        sql.append("  left join  SPGL_XMDWXXB d ON X.ID=D.jbxx_id ");
        sql.append("  LEFT JOIN t_msjw_system_dictionary dIC ON X.PROJECT_TYPE=dIC.dic_code ");
        sql.append("  LEFT JOIN JBPM6_EXECUTION e ON E.project_code=X.project_code ");
        sql.append("  LEFT JOIN t_wsbs_serviceitem S ON E.ITEM_CODE=S.ITEM_CODE ");
        sql.append("  LEFT JOIN t_msjw_system_dictionary DIC1 ON DIC1.dic_code=S.SXLX  ");
        sql.append(" WHERE  dIC.type_code='PROJECTTYPE' AND DIC1.type_code='ServiceItemType' ");
        sql.append(" AND X.ID=? AND S.FWSXZT='1' ");

        Map<String,Object> project=exeDataService.getFirstByJdbc(sql.toString(),new Object[]{id});
        project=setProjectFileDicName(project);
        return project;
    }
    /**
     * 根据阶段id和主题编号获取需要办理的事项
     * @return
     */
    public List<Map<String,Object>> findServiceItemByStageIdAndTopicCode(Map<String,Object> variable){
        String stageId=StringUtil.getString(variable.get("stageId"));
        String topicCode=StringUtil.getString(variable.get("topicCode"));
        String itemName=StringUtil.getString(variable.get("itemName"));
        StringBuffer sql=new StringBuffer("SELECT T.STAGE_ID,");
        sql.append("T.TYPE_ID,T.NAME, S.ITEM_CODE, S.ITEM_NAME,I.IS_KEY_ITEM,s.IMPL_DEPART");
        sql.append(" FROM T_FLOW_CONFIG_STAGE T  ");
        sql.append("   left   JOIN T_FLOW_CONFIG_ITEM I  ON T.STAGE_ID = I.STAGE_ID");
        sql.append("    left   JOIN T_WSBS_SERVICEITEM S     ON I.ITEM_ID = S.ITEM_ID ");
        sql.append("  WHERE I.IS_SITE_OPTIONAL = 1   AND T.STAGE_ID =?   and i.topic_name=?  ");
        sql.append(" and s.fwsxzt='1' ");
        if(StringUtils.isNotEmpty(itemName)){
            sql.append("and s.item_name like '%").append(itemName).append("%' ");
        }
        sql.append(" ORDER BY I.SORT ASC, I.CREATE_TIME DESC ");
        List<Map<String,Object>> serviceItems=dao.findBySql(sql.toString(),new Object[]{stageId,topicCode},null);
        return serviceItems;
    }

    /**
     * 设置项目属性字典名称
     * @param project
     * @return
     */
    public Map<String,Object> setProjectFileDicName(Map<String,Object> project){
        String industry=StringUtil.getString(project.get("INDUSTRY"));
        project.put("INDUSTRY",getDicTypeName(industry));//国标行业
        String  theIndustry=StringUtil.getString(project.get("THE_INDUSTRY"));
        project.put("THE_INDUSTRY",dictionaryService.getDicNames("PERMITINDUSTRY",theIndustry));//所属行业
        String industryStructure=StringUtil.getString(project.get("INDUSTRY_STRUCTURE"));
        project.put("INDUSTRY_STRUCTURE",getDicTypeName(industryStructure));//所属产业结构
        String  permitIndustry=StringUtil.getString(project.get("PERMIT_INDUSTRY"));
        project.put("PERMIT_INDUSTRY",dictionaryService.getDicNames("PERMITINDUSTRY",permitIndustry));//投资项目行业分类
        String  placeCode=StringUtil.getString(project.get("PLACE_CODE"));
        project.put("PLACE_CODE",getDicTypeName(placeCode));//建设地点
        String  projectNature=StringUtil.getString(project.get("PROJECT_NATURE"));
        project.put("PROJECT_NATURE",dictionaryService.getDicNames("PROJECTNATURE",projectNature));//建设性质




        return project;
    }

    /**
     * 根据字典类别获取字典名称
     * @param dicTypeCode
     * @return
     */
    public String getDicTypeName(String dicTypeCode){
        String dicTypeName="";
        if(StringUtils.isNotEmpty(dicTypeCode)){
            StringBuffer sql=new StringBuffer("SELECT *  FROM ");
            sql.append("  T_MSJW_SYSTEM_DICTYPE d ");
            sql.append(" where d.type_code=? ");
            Map<String,Object> dicType=dao.getByJdbc(sql.toString(),new Object[]{dicTypeCode});
            if(dicType!=null){
                dicTypeName=StringUtil.getString(dicType.get("TYPE_NAME"));
            }
        }
        return dicTypeName;
    }
    /**
     *
     * 描述 转换国标行业
     *
     * @author Rider Chen
     * @created 2019年7月5日 下午1:40:37
     * @param code
     * @return
     */
    public String getIndustry(String code) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT D.* FROM T_MSJW_SYSTEM_DICTYPE D ");
        sql.append("  START WITH D.type_code = ? ");
        sql.append("  CONNECT BY PRIOR D.PARENT_ID = D.TYPE_ID");
        sql.append("   ORDER BY D.TREE_LEVEL ASC) A WHERE A.TREE_LEVEL = 5");
        sql.append(" AND A.PATH LIKE '%4028819d51cc6f280151cde6a3f00027%' ");
        List<Object> params = new ArrayList<Object>();
        params.add(code);
        Map<String, Object> map = dao.getByJdbc(sql.toString(), params.toArray());
        if (null != map && map.size() > 0) {
            String typeCode = map.get("TYPE_CODE").toString();
            if (typeCode.equals(code)) {
                return code;
            } else {
                return typeCode + code;
            }
        }
        return code;
    }

}
