/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述 政府投资项目第三阶段流程服务接口
 * 
 * @author Derek Zhang
 * @created 2015年11月17日 上午10:23:52
 * @version v1.0
 */
@SuppressWarnings("rawtypes")
public interface ZftzThreeService extends BaseService {

    /**
     * 
     * 描述 计算协调出来的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResult(Map<String, Object> flowVars);
    /**
     * 
     * 描述 计算协调出来的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResultNew(Map<String, Object> flowVars);
    /**
     * 描述 计算是否协调一致的结果
     *
     * @param flowVars
     * @return
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     */
    public Set<String> getCoodResultA(Map<String, Object> flowVars);

    /**
     * 
     * 描述 计算是否协调一致的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSame(Map<String, Object> flowVars);
    /**
     * 
     * 描述 计算是否协调一致的结果
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSameNew(Map<String, Object> flowVars);
    /**
     * 
     * 描述 签发是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getQFIsPass(Map<String, Object> flowVars);

    /**
     * 
     * 描述 公示是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Set<String> getGSIsPass(Map<String, Object> flowVars);

    /**
     * 描述:公示是否通过（用于政府审批第三阶段流程）
     *
     * @author Madison You
     * @created 2019/8/9 16:27:00
     * @param
     * @return
     */
    public Set<String> getHhreeGSIsPass(Map<String, Object> flowVars);
    /**
     * 描述:是否需要协调（赋码信息决策判断）
     *
     * @author Madison You
     * @created 2019/8/21 10:52:00
     * @param
     * @return
     */
    public Set<String> getCoodResultForCodeChange(Map<String, Object> flowVars) ;

    /**
     * 描述:用于政府审批第三阶段流程
     *
     * @author Madison You
     * @created 2019/8/9 16:34:00
     * @param
     * @return
     */
    public Set<String> getTreeIsConcertPass(Map<String, Object> flowVars); 
    /**
     * 描述:用于政府审批第三阶段流程(新)
    *
    * @author Madison You
    * @created 2019/8/9 16:34:00
    * @param
    * @return
    */
   public Set<String> getTreeIsConcertPassNew(Map<String, Object> flowVars);

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-5-6 上午11:38:05
     * @param flowVars
     * @return
     */
    public Set<String> getIsConcertPass(Map<String,Object> flowVars);

    /**
     * 描述 保存批复意见
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveNPFYJ(Map<String, Object> flowVars);

    /**
     * 描述 保存管委会签发意见
     * 
     * @author Derek Zhang
     * @created 2015年11月17日 上午10:23:52
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveGWHQFYJ(Map<String, Object> flowVars);

    /**
     * 描述:适用于政府投资第三流程（签发是否通过）
     *
     * @author Madison You
     * @created 2020/3/13 10:42:00
     * @param
     * @return
     */
    public Set<String> getIsQfPassThree(Map<String, Object> flowVars);
}
