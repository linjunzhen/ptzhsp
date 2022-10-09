/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 已立项项目计划管理操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ProjectService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);

    /**
     * 
     * 描述 根据区划代码和代码类型获取项目编号
     * 
     * @author Kester Chen
     * @created 2019年12月7日 下午3:58:18
     * @param XZQHCode 区划行政代码 示例：平潭,350128000000
     * @param codetype 代码类型1：省级 2：部级
     * @return
     */
    public String getPrjCode(String XZQHCode, String codetype);

    /**
     * 获取投资项目基本信息
     * 
     * @param projectCode
     * @return
     */
    public Map<String, Object> getXMJBXXBData(String projectCode);

    /**
     * 
     * 描述 根据项目编号获取单体编码序列号
     * 
     * @author Kester Chen
     * @created 2019年12月10日 上午9:19:38
     * @param prjCode
     * @return
     */
    public String getUnitCode(String prjCode);

    /**
     * 
     * 描述 根据区划代码和代码类型获取工程项目有关环节（文书）编号
     * 
     * @author Luffy Cai
     * @created 2020年5月7日 下午3:58:18
     * @param XZQHCode 区划行政代码 示例：平潭,350128000000
     * @param codetype 工程项目有关环节代码类型
     * @return
     */
    public String getGcxmCode(String XZQHCode, String codetype);

    /**
     * 
     * @Description 根据报建编号获取部四库一平台施工许可证编号
     * @author Luffy Cai
     * @date 2020年11月20日
     * @param proNum
     * @return String
     */
    public String getConstrnum(String proNum);

    /**
     * 
     * @Description 根据工程分类代码获取电子证照施工许可证编号
     * @author Luffy Cai
     * @date 2020年11月20日
     * @param proType
     * @return String
     */
    public String getCertNum(String proType);

    /**
     * 
     * @Description 根据报建号和代码类型获取工程项目编号
     * @author Luffy Cai
     * @date 2021年4月21日
     * @param proNum
     * @param type
     * @return String
     */
    public String getGcxmCodeByProNum(String proNum, String type);

}
