/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import org.ietf.jgss.Oid;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述  台湾地区职业资格采信证书相关业务操作Service
 * @author Allin Lin
 * @created 2019年5月24日 下午3:03:41
 */
public interface TwQualificationService extends BaseService{
    
    /**
     * 描述    修改或保存台湾地区职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月27日 上午10:25:56
     * @param data
     * @param entityId
     * @return
     */
    public String saveOrUpdateTwQualify(Map<String, Object> data,String entityId);
    
    /**
     * 描述    删除台湾地区职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月27日 上午10:49:05
     * @param qualificationIds
     */
    public void removeCascade(String[] qualificationIds);
    
    /**
     * 描述    根据sqlFilter获取列表
     * @author Allin Lin
     * @created 2019年5月27日 上午11:18:05
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 描述   验证台胞证件号是否已经存在
     * @author Allin Lin
     * @created 2019年5月28日 下午3:14:32
     * @param zjhm
     * @return
     */
    public boolean isExist(String zsNumber);
    
    /**
     * 描述    根据姓名、证书编号获取职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月30日 上午10:43:53
     * @param username
     * @param zsNum
     * @return 
     */
    public List<Map<String, Object>> getTwQualification(String username,String zsNum);

}
