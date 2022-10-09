/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;

/**
 * 描述 政府投资项目第一阶段服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月16日 上午9:39:36
 */
@SuppressWarnings("rawtypes")
public interface ZftzService extends BaseService {
    /**
     * typeArr
     */
    public String[][] ZBTB_TYPEARRS = { { "kcXM", "勘察" }, { "sjXM", "设计" }, { "jlXM", "监理" }, { "gcXM", "工程施工" },
                                        { "zysbXM", "重要设备" }, { "zyclXM", "重要材料" } };
    /**
     * 描述 是否需要协调
     * 
     * @author Derek Zhang
     * @created 2015年11月16日 上午9:39:36
     * @param flowVars
     * @return
     */
    public Set<String> getIsNeedCoordination(Map<String, Object> flowVars);

    /**
     * 描述 协调结果--协调是否一致
     * 
     * @author Derek Zhang
     * @created 2015年11月16日 上午9:39:36
     * @param flowVars
     * @return
     */
    public Set<String> getCoordinationResult(Map<String, Object> flowVars);

    /**
     * 描述 领导审签意见
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveGWHLDQFYJ(Map<String, Object> flowVars);
    /**
     * 
     * 描述 获取需要从网站公式自动跳转的数据
     * @author Flex Hu
     * @created 2016年2月17日 下午4:19:11
     * @return
     */
    public List<Map<String,Object>> findNeedAutoJump();
    /**
     * 
     * 描述 面向网站公示进行跳转
     * @author Flex Hu
     * @created 2016年2月17日 下午4:34:57
     * @param data
     */
    public void jumpTaskForWzgs(Map<String,Object> data) throws Exception;
    /**
     * 
     * 描述 更新流程阶段字段
     * @author Flex Hu
     * @created 2016年3月7日 下午3:52:44
     * @param flowVars
     * @return
     */
    public Map<String,Object> updateFlowStage(Map<String,Object> flowVars);
    
    
    
    /**
     * 描述   是否有异议
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getHasDissent(Map<String, Object> flowVars) ;
    /**
     * 描述   异议复核结果是否驳回异议
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsRejectDissent(Map<String, Object> flowVars) ;
    /**
     * 描述   招标、投标流程是否送分管领导
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsSendFGLD(Map<String, Object> flowVars) ;
    /**
     * 描述   招标、投标流程是否送主任办公会审议
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsSendZRBGHSY(Map<String, Object> flowVars) ;

    /**
     * 描述  保存招标、投标流程业务子表数据
     * @author Derek Zhang
     * @created 2016年8月9日 上午8:44:49
     * @param flowDatas
     * @param busRecordId
     */
    public void saveOrUpdateZTBDateItems(Map<String, Object> flowDatas, String busRecordId);

    /**
     * 描述:适用于政府投资副流程（签发是否通过）
     *
     * @author Madison You
     * @created 2020/3/13 10:42:00
     * @param
     * @return
     */
    public Set<String> getIsQfPassElse(Map<String, Object> flowVars);
}
