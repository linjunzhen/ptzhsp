/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 
 * 描述：工程建设项目消防事项数据共享定时器
 * @author Scolder Lin
 * @created 2020年1月8日 下午5:30:39
 */
@SuppressWarnings("rawtypes")
public interface ProjectXFDao extends BaseDao {
    /**
     * 查询消防项目基本信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findXFBaseList(int status);
    /**
     * 查询消防项目单体信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findXFUnitList(int status);
    /**
     * 查询消防项目责任主体信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findXFCorpList(int status);
    /**
     * 查询消防项目储罐信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findXFStorageList(int status);
    /**
     * 查询消防项目堆场信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findXFYardList(int status);
    /**
     * 查询消防项目建筑保温信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findXFInsuList(int status);
    /**
     * 查询消防项目装修工程信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findXFDecorateList(int status);
    /**
     * 查询竣工验收备案信息列表
     * @param status
     * @return
     */
    List<Map<String, Object>> findFinishManageList(int status);
    /**
     * 描述:查询消防验收（备案）申请列表
     *
     * @author Madison You
     * @created 2020/1/16 10:42:00
     * @param
     * @return
     */
    List<Map<String, Object>> findXFYsbaList(int status);

    /**
     * 描述:查询消防验收情况列表
     *
     * @author Madison You
     * @created 2020/1/16 11:19:00
     * @param
     * @return
     */
    List<Map<String, Object>> findXFYsqkList(int status);
}
