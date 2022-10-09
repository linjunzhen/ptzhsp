/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 服务事项操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ServiceItemDao extends BaseDao {
    /**
     * 
     * 描述 根据项目编码判断是否存在记录
     * @author Flex Hu
     * @created 2015年9月22日 上午11:28:45
     * @param itemCode
     * @return
     */
    public boolean isExists(String itemCode);
    /**
     * 
     * 描述 保存办事项目用户中间表
     * @author Flex Hu
     * @created 2015年9月24日 下午5:48:57
     * @param itemId
     * @param userIds
     */
    public void saveItemUsers(String itemId,String userIds);
    /**
     * 
     * 描述 根据项目ID获取绑定的用户IDSNAMES
     * @author Flex Hu
     * @created 2015年9月24日 下午5:57:47
     * @param itemId
     * @return
     */
    public Map<String,Object> getBindUserIdANdNames(String itemId);
    /**
     * 
     * 描述 保存办事事项票单模板配置
     * @author Faker Li
     * @created 2015年10月16日 下午5:28:31
     * @param itemId
     * @param ticketsIds
     */
    public void saveItemTickets(String itemId, String ticketsIds);
    /**
     * 
     * 描述 根据项目ID获取绑定的用户IDSNAMES
     * @author Faker Li
     * @created 2015年10月19日 上午9:10:55
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindTicketsIdANdNames(String itemId);
    /**
     * 
     * 描述 根据项目ID获取绑定的公文IDSNAMES
     * @author Faker Li
     * @created 2015年10月19日 下午3:03:09
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindDocumentIdANdNames(String itemId);
    /**
     * 
     * 描述 保存办事事项公文模板配置
     * @author Faker Li
     * @created 2015年10月19日 下午3:10:43
     * @param itemId
     * @param documentIds
     */
    public void saveItemDocument(String itemId, String documentIds);
    /**
     * 
     * 描述 根据项目ID获取绑定的阅办IDSNAMES
     * @author Faker Li
     * @created 2015年10月19日 下午3:03:09
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindReadIdANdNames(String itemId);
    /**
     * 
     * 描述 保存办事事项阅办模板配置
     * @author Faker Li
     * @created 2015年10月19日 下午3:10:43
     * @param itemId
     * @param readIds
     */
    public void saveItemRead(String itemId, String readIds);
    /**
     * 
     * 描述 更新办事事项状态
     * @author Faker Li
     * @created 2015年10月21日 上午10:25:36
     * @param selectColNames
     * @param state
     */
    public void updateFwsxzt(String selectColNames,String state);
    /**
     * 
     * 描述 根据目录编码获取事项编码
     * @author Faker Li
     * @created 2015年10月28日 下午5:10:27
     * @param catalogCode
     * @return
     */
    public int getMaxNumCode(String catalogCode);
    /**
     * 
     * 描述 保存绑定表单
     * @author Faker Li
     * @created 2015年11月16日 下午5:32:52
     * @param itemId
     * @param bindFormIds
     */
    public void saveItemFormIds(String itemId, String bindFormIds);
    /**
     * 
     * 描述 根据itemid获取绑定表单
     * @author Faker Li
     * @created 2015年11月17日 上午9:12:55
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindFormIdAndName(String itemId);
    /**
     * 
     * 描述 保存排序
     * @author Faker Li
     * @created 2015年11月24日 下午3:41:39
     * @param itemIds
     */
    public void updatSn(String[] itemIds);
    /**
     * 
     * 描述 获取最大排序值
     * @author Faker Li
     * @created 2015年11月24日 下午3:48:45
     * @return
     */
    public int getMaxSn();
    /**
     * 
     * 描述 根据catalogCode更新事项所属部门编码
     * @author Faker Li
     * @created 2015年12月16日 下午5:05:40
     * @param catalogCode
     * @param departCode
     */
    public void updateSSBMBM(String catalogCode, String departCode);
    /**
     * 
     * 描述 根据事项所属部门，事项性质，星级获取统计数
     * @author Faker Li
     * @created 2016年1月26日 上午9:57:23
     * @param ssbmbm
     * @param sxxz
     * @param itemstar
     * @return
     */
    public int getXnjcNum(String ssbmbm,String sxxz,String itemstar,String isKT);
    /**
     * 
     * 描述 是否支持挂起
     * @author Flex Hu
     * @created 2016年3月19日 上午8:55:45
     * @param itemCode
     * @return
     */
    public boolean isForHandUp(String itemCode);
    /**
     * 描述 获取省网办对应事项id
     * @author Water Guo
     * @created 2016年8月28日 上午11:15:00
     * @param itemCode
     * @return
     */
    public String getSwbItemCode(String itemCode);
    /**
     * 
     * 描述    获取自查条件最大排序值
     * @author Danto Huang
     * @created 2018年9月4日 上午10:45:38
     * @return
     */
    public int getMaxExamSn();
}
