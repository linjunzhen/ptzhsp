/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.ptwg.dao.SearchDao;
import net.evecom.platform.ptwg.service.SearchService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-2-28 下午4:40:06
 */
@Service("searchService")
public class SearchServiceImpl extends BaseServiceImpl implements SearchService {

    /**
     * 所引入的dao
     */
    @Resource
    private SearchDao dao;

    /**
     * 
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 
     */
    @Resource
    private DepartServiceItemService departServiceItemService;
    
    /**
     * 
     * 根据父编码获取服务类别列表
     * @author Danto Huang
     * @created 2017-6-13 下午6:59:14
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> getItemTypeList(String typeCode){
        StringBuffer sql =  new StringBuffer();
        sql.append("select t.type_id,t.type_name,t.app_icon_name from T_WSBS_BUSTYPE t where t.parent_id=");
        sql.append("(select type_id from T_WSBS_BUSTYPE where type_code=?) order by t.tree_sn ");
        return dao.findBySql(sql.toString(), new Object[]{typeCode}, null);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-2-28 下午4:53:21
     * @return
     */
    public List<Map<String,Object>> getNameSearchList(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.item_code,t.item_name,t.bldd,t.rzbsdtfs from T_WSBS_SERVICEITEM t ");
        sql.append("where t.mxyhdx like '%01%' and t.fwsxzt=1 ");
        if(filter.getQueryParams().get("Q_t.ITEM_NAME_LIKE")!=null){
            String searchName = filter.getQueryParams().get("Q_t.ITEM_NAME_LIKE").toString();
            sql.append("and (t.item_name like '%").append(searchName).append("%'");
            sql.append("or t.keyword like '%").append(searchName).append("%')");
            filter.removeFilter("Q_t.ITEM_NAME_LIKE");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        Map<String,Object> total = new HashMap<String, Object>();
        total.put("total", filter.getPagingBean().getTotalItems());
        list.add(0, total);
        return list;
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-2-28 下午4:53:21
     * @return
     */
    public List<Map<String,Object>> getTypeSearchList(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.item_code,t.item_name,t.bldd,t.rzbsdtfs from T_WSBS_SERVICEITEM t ");
        sql.append("left join T_WSBS_SERVICEITEM_TYPE p on p.item_id=t.item_id ");
        sql.append("where t.mxyhdx like '%01%' and t.fwsxzt=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), filter.getPagingBean());
        Map<String,Object> total = new HashMap<String, Object>();
        total.put("total", filter.getPagingBean().getTotalItems());
        list.add(0, total);
        return list;
    }


    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-2-28 下午4:53:21
     * @return
     */
    public List<Map<String,Object>> getTopItemList(String page, String rows){
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        
        StringBuffer sql = new StringBuffer("SELECT B.ITEM_ID,B.ITEM_CODE, B.ITEM_NAME,D.DEPART_NAME,B.RZBSDTFS,");
        sql.append(" COUNT(*) AS itemCount");
        sql.append(" FROM T_WSBS_SERVICEITEM B ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=B.SSBMBM ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION T on  T.ITEM_CODE = B.ITEM_CODE ");
        sql.append(" WHERE B.FWSXZT = '1' ");
        sql.append(" GROUP BY  B.ITEM_ID, B.ITEM_CODE,B.ITEM_NAME, D.DEPART_NAME, B.RZBSDTFS");
        sql.append(" ORDER BY itemCount DESC");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        
        return list;
    }
    
    /**
     * 
     * 个人喜好数据列表
     * @author Danto Huang
     * @created 2017-5-17 上午10:24:28
     * @param page
     * @param rows
     * @param userId
     * @return
     */
    public List<Map<String,Object>> getPersonalPrefer(String page, String rows, String userId){
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));

        StringBuffer sql = new StringBuffer();
        sql.append("select s.item_code,s.item_name,v.visnum from t_wsbs_serviceitem s ");
        sql.append("join (select t.leafid,count(t.leafid) visnum from T_STAT_VISITLOG t ");
        sql.append("where t.userid=? group by t.leafid) v on v.leafid = s.item_code ");
        sql.append("order by v.visnum desc ");
        
        params.add(userId);
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        
        return list;
    }

    /**
     * 
     * 办过类似服务
     * @author Danto Huang
     * @created 2017-5-24 上午11:14:08
     * @param filter
     * @return
     */
    public List<Map<String,Object>> getAlsoDoneItme(SqlFilter filter){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.item_code,t.item_name,decode(vis.total,null,0,vis.total) visnum ");
        sql.append("from t_wsbs_serviceitem t left join ");
        sql.append("(select t.item,sum(count1)+sum(count2)+sum(count3)+sum(count4)+sum(count5)");
        sql.append("+sum(count6)+sum(count7)+sum(count8)+sum(count9)+sum(count10)+sum(count11)");
        sql.append("+sum(count12)+sum(count13)+sum(count14)+sum(count15)+sum(count16)+sum(count17)");
        sql.append("+sum(count18)+sum(count19)+sum(count20)+sum(count21)+sum(count22)+sum(count23)");
        sql.append("+sum(count24)+sum(count25)+sum(count26)+sum(count27)+sum(count28)+sum(count29)");
        sql.append("+sum(count30)+sum(count31) as total from T_STAT_SERVICE_ITEM t group by t.item) vis ");
        sql.append("on vis.item = t.item_code where t.mxyhdx like '%01%' and t.fwsxzt=1 ");
        if(filter.getQueryParams().get("Q_t.TYPE_ID_EQ")!=null){
            sql.append("and t.grztfl='").append(filter.getQueryParams().get("Q_t.TYPE_ID_EQ")).append("' ");
        }
        sql.append("order by visnum desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, filter.getPagingBean());
        
        return list;
    }
    /**
     * 
     * 我的足迹
     * @author Danto Huang
     * @created 2017年7月3日 下午4:31:06
     * @param page
     * @param rows
     * @param yhzh
     * @return
     */
    public List<Map<String,Object>> getMyTrack(String page, String rows,String yhzh){
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer();
        sql.append("select t.leafid,s.item_name,t.visitdate,s.bldd from T_STAT_VISITLOG t ");
        sql.append("left join t_wsbs_serviceitem s on s.item_code=t.leafid ");
        sql.append("where t.userid='").append(yhzh).append("' ");
        sql.append("order by t.visitdate desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, pb);

        Map<String,Object> total = new HashMap<String, Object>();
        total.put("total", pb.getTotalItems());
        list.add(0, total);
        return list;
    }
    
    /**
     * 
     * 描述    根据事项编码获取办事指南信息
     * @author Danto Huang
     * @created 2018年9月7日 上午9:56:26
     * @param itemCode
     * @return
     */
    public Map<String,Object> findGuideByItemCode(String itemCode){
        Map<String,Object> guideInfo = new HashMap<String, Object>();
        Map<String, Object> itemInfo = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new Object[] { itemCode });
        guideInfo.put("ITEM_CODE", itemInfo.get("ITEM_CODE"));
        guideInfo.put("ITEM_NAME", itemInfo.get("ITEM_NAME"));
        String sxlb = dictionaryService.findByDicCodeAndTypeCode((String) itemInfo.get("SXLX"), "ServiceItemType");
        guideInfo.put("SXLB", sxlb);
        String sbdx = dictionaryService.findByDicCodeAndTypeCode((String) itemInfo.get("MXYHDX"), "faceUserType");
        guideInfo.put("SBDX", sbdx);
        String sxxz = dictionaryService.findByDicCodeAndTypeCode((String) itemInfo.get("SXXZ"), "ServiceItemXz");
        guideInfo.put("SXXZ", sxxz);
        guideInfo.put("IMPL_DEPART", itemInfo.get("IMPL_DEPART"));
        guideInfo.put("ZBCS", itemInfo.get("ZBCS"));
        guideInfo.put("LXR", itemInfo.get("LXR"));
        guideInfo.put("LXDH", itemInfo.get("LXDH"));
        guideInfo.put("JDDH", itemInfo.get("JDDH"));
        String fdsxlx = dictionaryService.findByDicCodeAndTypeCode((String) itemInfo.get("FDSXLX"), "FDSXLX");
        guideInfo.put("FDSX", itemInfo.get("FDSXGZR")+fdsxlx);
        guideInfo.put("CNSX", itemInfo.get("CNQXGZR")+"工作日");
        guideInfo.put("CNQXSM", itemInfo.get("CNQXSM"));
        String sfsf = dictionaryService.findByDicCodeAndTypeCode((String) itemInfo.get("SFSF"), "YesOrNo");
        guideInfo.put("SFSF", sfsf);
        guideInfo.put("REMARK", itemInfo.get("BZSM"));
        
        List<Map<String, Object>> specialLink = departServiceItemService
                .getSpecialLink((String) itemInfo.get("ITEM_ID"));
        guideInfo.put("specialLink", specialLink);
        
        guideInfo.put("SQTJ", itemInfo.get("SQTJ"));
        guideInfo.put("BGSJ", itemInfo.get("BGSJ"));
        guideInfo.put("BLDD", itemInfo.get("BLDD"));
        guideInfo.put("SQFS", itemInfo.get("SQFS"));
        guideInfo.put("SQZHYQ_2", itemInfo.get("SQZHYQ_2"));
        guideInfo.put("SQZHYQ_3", itemInfo.get("SQZHYQ_3"));
        guideInfo.put("SQZHYQ_4", itemInfo.get("SQZHYQ_4"));
        guideInfo.put("CKCS_2", itemInfo.get("CKCS_2"));
        guideInfo.put("CKCS_3", itemInfo.get("CKCS_3"));
        guideInfo.put("CKCS_4", itemInfo.get("CKCS_4"));
        guideInfo.put("FINISH_GETTYPE", itemInfo.get("FINISH_GETTYPE"));

        // 获取材料信息列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId((String) itemInfo.get("ITEM_ID"),null);
        guideInfo.put("applyMaters", applyMaters);
        
        List<Map<String, Object>> BLLC = this.findBllc((String) itemInfo.get("ITEM_ID"),
                (String) itemInfo.get("BDLCDYID"));
        guideInfo.put("BLLC", BLLC);
        
        guideInfo.put("XSYJ", itemInfo.get("XSYJ"));
        
        return guideInfo;
    }
    
    /**
     * 
     * 描述    获取流程设置数据
     * @author Danto Huang
     * @created 2018年9月7日 下午2:47:28
     * @param itemId
     * @param defId
     * @return
     */
    public List<Map<String,Object>> findBllc(String itemId,String defId){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.node_name,t.user_name,t.time_limit,d.dic_name workday from T_WSBS_SERVICEITEM_AUDITER t ");
        sql.append("left join (select dic_code, dic_name from t_msjw_system_dictionary ");
        sql.append("where type_id = (select type_id from t_msjw_system_dictype where type_code = 'FDSXLX')) d ");
        sql.append("on d.dic_code = t.time_type ");
        sql.append("where t.item_id = ? and t.def_id = ? and t.node_type = 'task' and t.user_account is not null ");
        sql.append("order by t.node_key");
        return dao.findBySql(sql.toString(), new Object[]{itemId,defId}, null);
    }
}
