/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.website.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.system.service.SysUserService;
import net.evecom.platform.website.dao.WebSiteDao;
import net.evecom.platform.website.service.WebSiteService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月1日 上午11:04:11
 */
@Service("webSiteService")
public class WebSiteServiceImpl extends BaseServiceImpl implements WebSiteService {
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    
    /**
     * 所引入的dao
     */
    @Resource
    private WebSiteDao dao;
    

    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 根据流程实例设置当前环节的名称和审核人数据
     * @author Flex Hu
     * @created 2015年12月1日 上午11:07:07
     * @param flowExe
     * @param eFlowObj
     * @return
     */
    public Map<String,Object> getFlowCurNodeAndOper(Map<String,Object> flowExe,Map<String,Object> eFlowObj){
        //获取当前环节名称
        String CUR_STEPNAMES = (String) flowExe.get("CUR_STEPNAMES");
        String CUR_STEPAUDITACCOUNTS = (String) flowExe.get("CUR_STEPAUDITACCOUNTS");
        if(StringUtils.isNotEmpty(CUR_STEPNAMES)){
           //获取第一个环节的名称
            String hjmc = CUR_STEPNAMES.split(",")[0];
            //获取第一个人的帐号
            String firstAccount = CUR_STEPAUDITACCOUNTS.split(",")[0];
            Map<String,Object> userInfo = sysUserService.getUserInfo(firstAccount);
            eFlowObj.put("HJMC", hjmc);
            if(userInfo!=null){
                eFlowObj.put("SHRMC", userInfo.get("DEPART_NAME"));
            }
            return eFlowObj;
        }else{
            return eFlowObj;
        }
    }

    /**
     * 描述:获取环土局行业列表
     *
     * @author Madison You
     * @created 2019/8/19 11:10:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getHTIndustry() {
        StringBuffer industry = new StringBuffer("");
        industry.append(" select DISTINCT INDUSTRY_NAME from T_MSJW_SYSTEM_HTINDUSTRY ");
        List bySql = dao.findBySql(industry.toString(), null, null);
        return bySql;
    }

    /**
     * 描述: 获取环土局项目列表
     *
     * @author Madison You
     * @created 2019/8/19 11:10:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getHTProject(HttpServletRequest request) {
        String industry = request.getParameter("industry");
        StringBuffer sql = new StringBuffer("");
        sql.append(" select project_name from t_msjw_system_htindustry where industry_name = ? ");
        return dao.findBySql(sql.toString(), new Object[]{industry}, null);
    }

    /**
     * 描述:获取环土局项目列表详情
     *
     * @author Madison You
     * @created 2019/8/19 12:41:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getHTProjectDetail(HttpServletRequest request) {
        String project = request.getParameter("project");
        StringBuffer sql = new StringBuffer();
        sql.append(" select REPORT , REPORT_FORM , REGISTRAT_FORM from t_msjw_system_htindustry ");
        sql.append(" where project_name = ? ");
        return dao.getByJdbc(sql.toString(), new Object[]{project});
    }

    /**
     * 描述:模块内容查询数据
     *
     * @author Madison You
     * @created 2019/10/17 17:29:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> findModuleSearchData(SqlFilter filter) {
        String moduleIdS = filter.getRequest().getParameter("moduleId");
        int moduleId = 0;
        String SHARE_MODULEID = null;
        Map<String, Object> module = null;
        if (moduleIdS != null) {
            moduleId = Integer.parseInt(moduleIdS);
            module = dao.getByJdbc("T_CMS_ARTICLE_MODULE", new String[]{"MODULE_ID"}, new Object[]{moduleId});
            if (module != null) {
                SHARE_MODULEID = (String) module.get("SHARE_MODULEID");
            }
        }

        String page = filter.getRequest().getParameter("page");
        String rows = filter.getRequest().getParameter("rows");
        String key = filter.getRequest().getParameter("key");
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        params.add(moduleId);
        params.add("%"+key+"%");
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("select tid,content_id as itemId,content_title as itemTitle");
        sql.append(" ,to_char(to_date(release_time, 'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') as itemReldate ");
        sql.append(" ,titleimg,linkurl,keyword,hits  from view_article_content  ");
        sql.append("  where module_id in(select m1.module_id from t_cms_article_module m1 ");
        /*如果是共享信息*/
        if (SHARE_MODULEID != null) {
            params.remove(0);
            sql.append("  where m1.module_status = 1 and m1.module_delete = 0  ");
            sql.append(" start with m1.module_id in ("+SHARE_MODULEID+","+moduleId+") ");
        } else {
            sql.append(" where m1.module_status = 1 and m1.module_delete = 0 start with m1.module_id = ? ");
        }
        sql.append(" connect by prior m1.module_id = m1.parent_id) ");
        sql.append(" and content_status=1 and content_delete=0 ");
        sql.append(" and content_title like ? ");
        sql.append(" order by istop desc,release_time desc");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

}
