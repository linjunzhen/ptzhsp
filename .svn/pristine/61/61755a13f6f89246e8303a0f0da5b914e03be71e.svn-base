/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.website.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
/**
 * 描述 消防设计服务层
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月9日 上午11:23:10
 */
@SuppressWarnings("rawtypes")
public interface XFDesignService extends BaseService {
    /**
     * 根据项目编号，查询责任主体列表信息
     * @param prj_code
     * @param prj_num
     * @return
     */
    List<Map<String, Object>> findZrztxxList(String prj_code, String prj_num);
    /**
     * 根据项目编号，查询单体建筑物列表信息
     * @param prj_code
     * @param prj_num
     * @return
     */
    List<Map<String, Object>> findDtjzwxxList(String prj_code, String prj_num);
    /**
     * 根据项目代码查找报建编号
     * @param prjCode
     * @return
     */
    String getPrjNum(String prjCode);
    /**
     * 根据projectCode 和 prjNum 查询项目建设消防设计信息
     * @param projectCode
     * @param prjNum
     * @return
     */
    Map<String, Object> findXfProjectInfo(String projectCode, String prjNum);
}
