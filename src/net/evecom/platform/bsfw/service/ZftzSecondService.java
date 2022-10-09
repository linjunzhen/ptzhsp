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
 * 描述
 * @author Danto Huang
 * @created 2015-12-7 下午2:47:51
 */
public interface ZftzSecondService extends BaseService {
    /**
     * 
     * 描述 公示是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsPublicityPass(Map<String,Object> flowVars);

    /**
     * 描述:公示是否通过（适用于政府投资副流程）
     *
     * @author Madison You
     * @created 2019/8/12 10:27:00
     * @param
     * @return
     */
    public Set<String> getIsPublicityPassF(Map<String,Object> flowVars);

    /**
     * 描述:适用于政府投资第四流程
     *
     * @author Madison You
     * @created 2019/8/21 17:16:00
     * @param
     * @return
     */
    public Set<String> getIsPublicityPassS(Map<String,Object> flowVars);

    /**
     * 描述:公示是否通过（适用于政府投资第一流程）
     *
     * @author Madison You
     * @created 2019/8/15 10:01:00
     * @param
     * @return
     */
    public Set<String> getIsPublicityPassC(Map<String, Object> flowVars);

    /**
     * 描述:公示是否通过（适用于政府投资第二阶段流程）
     *
     * @author Madison You
     * @created 2019/8/12 11:18:00
     * @param
     * @return
     */
    public Set<String> getIsPublicityPassE(Map<String,Object> flowVars);
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
     * 描述:适用于政府投资副流程
     *
     * @author Madison You
     * @created 2019/8/12 10:35:00
     * @param
     * @return
     */
    public Set<String> getIsConcertPassF(Map<String,Object> flowVars);

    /**
     * 描述:适用于政府投资第四流程
     *
     * @author Madison You
     * @created 2019/8/21 17:18:00
     * @param
     * @return
     */
    public Set<String> getIsConcertPassS(Map<String,Object> flowVars);

    /**
     * 描述:适用于政府投资第二流程
     *
     * @author Madison You
     * @created 2019/8/12 11:23:00
     * @param
     * @return
     */
    public Set<String> getIsConcertPassE(Map<String,Object> flowVars);
    /**
     * 
     * 描述：适用于政府投资新流程第四流程
     * @author Rider Chen
     * @created 2020年1月7日 上午9:31:09
     * @param flowVars
     * @return
     */
    public Set<String> getIsConcertPassNewS(Map<String,Object> flowVars);
    /**
     * 
     * 描述：适用于政府投资新流程第二流程
     * @author Rider Chen
     * @created 2020年1月7日 上午9:31:09
     * @param flowVars
     * @return
     */
    public Set<String> getIsConcertPassNewE(Map<String,Object> flowVars);
    /**
     * 
     * 描述：适用于政府投资新流程副流程
     * @author Rider Chen
     * @created 2020年1月7日 上午9:31:09
     * @param flowVars
     * @return
     */
    public Set<String> getIsConcertPassNewF(Map<String,Object> flowVars);
    /**
     * 
     * 描述：适用于政府投资新流程一
     * @author Rider Chen
     * @created 2020年1月7日 上午9:31:09
     * @param flowVars
     * @return
     */
    public Set<String> getIsConcertPassNew(Map<String,Object> flowVars);

    /**
     * 描述:适用于政府投资第二流程（签发是否通过）
     *
     * @author Madison You
     * @created 2020/3/13 10:42:00
     * @param
     * @return
     */
    public Set<String> getIsQfPassSecond(Map<String, Object> flowVars);
}
