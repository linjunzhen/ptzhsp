/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.cms.dao.ContentDao;
import net.evecom.platform.cms.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 描述 文章操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("contentService")
public class ContentServiceImpl extends BaseServiceImpl implements ContentService {
    /**
     * 所引入的dao
     */
    @Resource
    private ContentDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public void multicopy(String[] ids, String[] moduleIds) {
        // TODO Auto-generated method stub
        dao.multicopy(ids, moduleIds);
    }

    @Override
    public void paste(String[] ids, String moduleId) {
        // TODO Auto-generated method stub
        dao.paste(ids, moduleId);
    }

    @Override
    public Map<String, Object> findfrontList(String page, String rows, String moduleId) {
        Map<String,Object> module=dao.getByJdbc("t_cms_article_module",new String[]{"MODULE_ID"},
                new Object[]{moduleId});
        String shareModuleId=StringUtil.getString(module.get("SHARE_MODULEID"));
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("select tid,content_id as itemId,content_title as itemTitle"
                + ",to_char(to_date(release_time, 'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') as itemReldate"
                + ",titleimg,linkurl,keyword,hits  from view_article_content ");
        sql.append("  where module_id in(select m1.module_id from t_cms_article_module m1 ");
        //分享栏目是否为空
        if(StringUtils.isEmpty(shareModuleId)){
            sql.append(" where m1.module_status = 1 and m1.module_delete = 0 start with m1.module_id = ");
            sql.append(moduleId);
        }else{
            moduleId=String.format("%s,%s",moduleId,shareModuleId);
            sql.append(" where m1.module_status = 1 and m1.module_delete = 0 start with m1.module_id in (");
            sql.append(moduleId).append(")");
        }
        sql.append(" connect by prior m1.module_id = m1.parent_id) ");
        sql.append(" and content_status=1 and content_delete=0 ");
        sql.append(" order by istop desc,release_time desc");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    /**
     * 
     * 描述 接收公示公告信息
     * 
     * @author Rider Chen
     * @created 2015-12-15 上午10:31:16
     * @param map
     * @return
     * @throws IOException
     */
    public boolean dataSink(Map<String, String> map) {
        String GSSXMM = map.get("GSSXMM"); // 公示事项名称
        String SBDW = map.get("SBDW"); // 申报单位
        String JSDW = map.get("JSDW"); // 建设单位
        String JSDD = map.get("JSDD"); // 建设地点
        String JSQX = map.get("JSQX"); // 建设期限
        String ZTZ = map.get("ZTZ"); // 总投资
        String ZJLY = map.get("ZJLY"); // 资金来源
        String JSGMJZYNR = map.get("JSGMJZYNR"); // 建设规模及主要内容
        String GSKSSJ = map.get("GSKSSJ"); // 公示开始时间
        String GSJSSJ = map.get("GSJSSJ"); // 公示结束时间
        String SPCS = map.get("SPCS"); // 审批处室
        String LXDH = map.get("LXDH"); // 联系电话
        String EMAIL = map.get("EMAIL"); // 电子邮箱
        String POSTCODE = map.get("POSTCODE"); // 邮政编码
        String ADDRESS = map.get("ADDRESS"); // 邮政地址
        String MODULEID = map.get("MODULEID"); // 栏目ID
        String BUSID = map.get("BUSID"); // 源数据ID
        // 获取模版信息
        String contentText = FileUtil.getContentOfFile(FileUtil.getPrjRoot() 
                + "webpage/cms/article/template.txt");
        // 替换模版数据
        contentText = replaceText(contentText, "@TITLE", GSSXMM);
        contentText = replaceText(contentText, "@GSSXMM", GSSXMM);
        contentText = replaceText(contentText, "@SBDW", SBDW);
        contentText = replaceText(contentText, "@JSDW", JSDW);
        contentText = replaceText(contentText, "@JSDD", JSDD);
        contentText = replaceText(contentText, "@JSQX", JSQX);
        contentText = replaceText(contentText, "@ZTZ", ZTZ);
        contentText = replaceText(contentText, "@ZJLY", ZJLY);
        contentText = replaceText(contentText, "@JSGMJZYNR", JSGMJZYNR);
        contentText = replaceText(contentText, "@GSKSSJ", GSKSSJ);
        contentText = replaceText(contentText, "@GSJSSJ", GSJSSJ);
        contentText = replaceText(contentText, "@SPCS", SPCS);
        contentText = replaceText(contentText, "@LXDH", LXDH);
        contentText = replaceText(contentText, "@EMAIL", EMAIL);
        contentText = replaceText(contentText, "@POSTCODE", POSTCODE);
        contentText = replaceText(contentText, "@ADDRESS", ADDRESS);
        if (StringUtils.isNotEmpty(BUSID)) {
            if (StringUtils.isNotEmpty(MODULEID)) {
                Map<String, Object> content = new HashMap<String, Object>();
                content.put("CONTENT_TITLE", GSSXMM+"项目核准审批前公示");// 标题
                content.put("CONTENT_TEXT", contentText);// 文章内容
                content.put("COMEFROM", "平潭综合实验区行政服务中心");// 来源
                content.put("MODULE_ID", MODULEID);// 栏目ID
                content.put("AUTHOR", "管理员");// 作者
                content.put("CHECKER", "管理员");// 审核人
                content.put("CREATE_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));// 创建时间
                content.put("RELEASE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));// 发布时间
                content.put("CREATEUSERNAME", "超级管理员");// 撰稿人姓名
                content.put("CREATEUSERID", "402883524863ab1f014863ab1f340000");// 撰稿人ID
                content.put("HITS", "0");// 点击数
                content.put("CONTENT_MODAL", "1");// 文档变动
                content.put("ISTOP", "0");// 是否置顶
                content.put("CONTENTTYPE", "0");// 信息类型
                content.put("REMARK", BUSID);// 信息类型
                content.put("ISPUBLIC", "1");// 公开属性
                content.put("IPTYPE", "0");// IP限制类型
                content.put("CONTENT_DELETE", "0");
                content.put("CONTENT_STATUS", "1");
                this.saveOrUpdate(content, "T_CMS_ARTICLE_CONTENT", "", "S_CMS_ARTICLE_CONTENT");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 
     * 描述 替换信息
     * 
     * @author Rider Chen
     * @created 2015-12-15 上午10:33:55
     * @param text
     * @param target
     * @param replacement
     * @return
     */
    public static String replaceText(String text, String target, String replacement) {
        if (StringUtils.isNotEmpty(replacement)) {
            text = text.replace(target, replacement);
        } else {
            text = text.replace(target, "");
        }
        return text;
    }

    @Override
    public void removeContent(String tableName, String colName, Object[] colValues) {
        // TODO Auto-generated method stub
        this.remove(tableName, colName, colValues);
    }

    @Override
    public String saveOrUpdateContent(Map<String, Object> colValues, String tableName, String entityId) {
        // TODO Auto-generated method stub
        return this.saveOrUpdate(colValues, tableName, entityId);
    }

    @Override
    public String saveOrUpdateContent(Map<String, Object> colValues,String tableName, String entityId, String seqName) {
        // TODO Auto-generated method stub
        return this.saveOrUpdate(colValues, tableName, entityId, seqName);
    }

    @Override
    public Map<String, Object> getByJdbcContent(String tableName, String[] colNames, Object[] colValues) {
        // TODO Auto-generated method stub
        return this.getByJdbc(tableName, colNames, colValues);
    }

    @Override
    public String saveOrUpdateContentHits(Map<String, Object> colValues, String tableName, String entityId) {
        // TODO Auto-generated method stub
        return this.saveOrUpdate(colValues, tableName, entityId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findContentAppBySql(String page, String rows,String moduleId) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        params.add(moduleId);
        StringBuffer sql = new StringBuffer("SELECT T.CONTENT_ID,T.MODULE_ID,T.CONTENT_TITLE" +
                ",to_date(T.RELEASE_TIME, 'yyyy-mm-dd hh24:mi:ss') as ITEMRELDATE FROM T_CMS_ARTICLE_CONTENT T ");
        sql.append(" WHERE T.content_status = 1 AND T.content_delete = 0 AND T.module_id = ? ");
        sql.append(" ORDER BY T.istop desc,T.RELEASE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
        
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findContentFileAppBySql(String ctid) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        params.add(ctid);
        StringBuffer sql = new StringBuffer("select t.filename, t.filepath, t.filetype" +
                ", t.fileid from view_article_fileattach t where t.tid =? ");
        list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
        
    }
    

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findContentFileBySql(String attachFileIds) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = null;
        if (StringUtils.isNotEmpty(attachFileIds)) {
            StringBuffer sql = new StringBuffer("select t.* from T_CMS_ARTICLE_FILE t where t.file_id in  "
                    + StringUtil.getValueArray(attachFileIds));
            list = dao.findBySql(sql.toString(), null, null);
        } else {
            list = new ArrayList<Map<String, Object>>();
        }
        return list;
    }

    @Override
    public void updateFileToContentId(String fileIds, String contentId) {
        // TODO Auto-generated method stub
        dao.updateFileToContentId(fileIds, contentId);
    }

    /**
     * 描述:如果是常见问题模块，那么需要记录到便民服务下的常见问题中
     *
     * @author Madison You
     * @created 2019/10/18 9:57:00
     * @param
     * @return
     */
    @Override
    public void saveCommonProblem(Map<String,Object> variables) {
        HashMap<String, Object> map = new HashMap<>();
        if (variables != null) {
            int moduleId = Integer.parseInt(variables.get("MODULE_ID").toString());
            if (moduleId == 432) {
                map.put("PROBLEM_TITLE", variables.get("CONTENT_TITLE"));
                map.put("ANSWER_CONTENT", variables.get("CONTENT_TEXT"));
                map.put("LASTER_UPDATETIME", variables.get("RELEASE_TIME"));
                map.put("ITEM_ID", "bdcwt");
                dao.saveOrUpdate(map, "T_WSBS_COMMONPROBLEM", null);
            }
        }
    }
}
