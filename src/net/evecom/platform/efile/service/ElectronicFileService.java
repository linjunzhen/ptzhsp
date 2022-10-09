/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.efile.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述可信电子文件业务相关service
 * 
 * @author Luffy Cai
 *
 */
public interface ElectronicFileService extends BaseService {

    /**
     * 
     * @Description 电子文件信息提交(附件材料)
     * @author Luffy Cai
     * @date 2021年7月22日 void
     */
    public void submitEfileInfo();

    /**
     * 
     * @Description 电子文件信息提交(办理结果附件信息)
     * @author Luffy Cai
     * @date 2021年8月13日 void
     */
    public void submitResultEfileInfo();

    /**
     * 
     * @Description 电子文件办件信息提交
     * @author Luffy Cai
     * @date 2021年7月22日 void
     */
    public void saveEfileProjectInfo();

    /**
     * 
     * @Description 电子文件流程信息提交
     * @author Luffy Cai
     * @date 2021年7月22日 void
     */
    public void saveEfileProcessInfo();

    /**
     * 
     * @Description 不动产事项电子文件信息提交(附件材料)
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    public void submitBdcEfileInfo();

    /**
     * 
     * @Description 不动产事项电子文件信息提交(办理结果附件信息)
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    public void submitBdcResultEfileInfo();

    /**
     * 
     * @Description 不动产事项电子文件办件信息提交
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    public void saveBdcEfileProjectInfo();

    /**
     * 
     * @Description 不动产事项电子文件流程信息提交
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    public void saveBdcEfileProcessInfo();

    /**
     * 
     * @Description 事项与材料目录获取接口
     * @author Luffy Cai
     * @date 2021年8月10日
     * @param item_id
     * @param projectName
     * @param projectCode
     * @return Map<String,Object>
     */
    public Map<String, Object> getEfileProjectCatalog(String item_id, String projectName, String projectCode);
}
