/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.cms.dao.ContentDao;

/**
 * 描述 文章操作dao
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("contentDao")
public class ContentDaoImpl extends BaseDaoImpl implements ContentDao {

    @SuppressWarnings("unchecked")
    @Override
    public void multicopy(String[] ids, String[] moduleIds) {
        // TODO Auto-generated method stub
        for (String id : ids) {
            Map<String, Object> content = this.getByJdbc("T_CMS_ARTICLE_CONTENT", new String[] { "CONTENT_ID" },
                    new Object[] { id });
            String entityId = "";
            for (String moduleId : moduleIds) {
                content.put("MODULE_ID", moduleId);
                content.put("CONTENT_STATUS", 0);
                content.remove("CONTENT_ID");
                entityId = this.saveOrUpdate(content, "T_CMS_ARTICLE_CONTENT", entityId, "S_CMS_ARTICLE_CONTENT");
                List<Object> params = new ArrayList<Object>();
                params.add(id);
                List<Map<String, Object>> list = this.findBySql("select t.* from T_MSJW_SYSTEM_FILEATTACH t "
                        + "where t.bus_tablename = 't_article_content' and t.bus_tablerecordid = ?", params.toArray(),
                        null);
                for (Map<String, Object> map : list) {
                    map.put("BUS_TABLERECORDID", entityId);
                    map.remove("FILE_ID");
                    this.saveOrUpdate(map, "T_MSJW_SYSTEM_FILEATTACH", "");
                }
                
                List<Map<String, Object>> list1 = this.findBySql("select t.* from T_CMS_ARTICLE_FILE t "
                        + "where  t.content_id = ?", params.toArray(),
                        null);
                for (Map<String, Object> map : list1) {
                    map.put("CONTENT_ID", entityId);
                    map.remove("FILE_ID");
                    this.saveOrUpdate(map, "T_CMS_ARTICLE_FILE", "");
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void paste(String[] ids, String moduleId) {
        // TODO Auto-generated method stub
        for (String id : ids) {
            Map<String, Object> content = this.getByJdbc("T_CMS_ARTICLE_CONTENT", new String[] { "CONTENT_ID" },
                    new Object[] { id });
            content.put("MODULE_ID", moduleId);
            this.saveOrUpdate(content, "T_CMS_ARTICLE_CONTENT", id);
        }
    }
    
    @Override
    public void updateFileToContentId(String fileIds, String contentId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("update T_CMS_ARTICLE_FILE T SET T.")
                .append("CONTENT_ID=? WHERE T.FILE_ID IN ");
            if(StringUtils.isNotEmpty(fileIds)){           
                sql.append(StringUtil.getValueArray(fileIds));
                this.jdbcTemplate.update(sql.toString(), new Object[]{contentId});
            }
    }


}
