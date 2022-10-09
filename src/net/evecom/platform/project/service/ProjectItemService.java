/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述 工程建设项目流程事项表单Service
 *
 * @author Madison You
 * @version 1.0
 * @created 2019年12月16日 上午9:15:15
 */
public interface ProjectItemService extends BaseService {

    /**
     * 描述:获取当前项目消防设计申报号
     *
     * @author Madison You
     * @created 2019/12/7 15:51:00
     * @param
     * @return
     */
    Map<String, Object> findExecutionByProjectAndItem(String projectCode, String itemCode);

    /**
     * 描述:获取unid
     *
     * @author Madison You
     * @created 2019/12/16 14:08:00
     * @param
     * @return
     */
    String getUNID();

    /**
     * 描述:环节流转
     *
     * @author Madison You
     * @created 2019/12/17 9:18:00
     * @param
     * @return
     */
    Map<String, Object> doFlowJob(String infoXml);

    /**
     * 描述:将url文件转为base64字符串
     *
     * @author Madison You
     * @created 2019/12/17 17:00:00
     * @param
     * @return
     */
    String getBase64FromUrl(String url);

    /**
     * 描述:根据exeid获取文件信息
     *
     * @author Madison You
     * @created 2019/12/17 17:01:00
     * @param
     * @return
     */
    List<Map<String, Object>> getFileInfoByExeId(String exeId);

    /**
     * 描述:获取办件结果信息
     *
     * @author Madison You
     * @created 2019/12/18 10:39:00
     * @param
     * @return
     */
    Map<String, Object> doFlowResultJob(String infoXml);

    /**
     * 描述:计时暂停
     *
     * @author Madison You
     * @created 2019/12/18 15:59:00
     * @param
     * @return
     */
    Map<String, Object> doFlowHandleUpJob(String infoXml);

    /**
     * 描述:重启流程
     *
     * @author Madison You
     * @created 2019/12/18 16:17:00
     * @param
     * @return
     */
    Map<String, Object> doFlowRestartJob(String infoXml);

    /**
     * 描述:省林业局材料
     *
     * @author Madison You
     * @created 2019/12/19 19:37:00
     * @param
     * @return
     */
    List<Map<String,Object>> getSlyjMaterList(String exeId);

    /**
     * 描述:获取补件信息列表
     *
     * @author Madison You
     * @created 2019/12/19 20:09:00
     * @param
     * @return
     */
    List<Map<String, Object>> getBjInfoList(String exeId);

    /**
     * 描述:获取电子证照信息
     *
     * @author Madison You
     * @created 2019/12/19 20:37:00
     * @param
     * @return
     */
    Map<String, Object> doFlowDzzzJob(String infoXml);

    /**
     * 描述:根据typeCode和dicCode获取dicName
     *
     * @author Madison You
     * @created 2019/12/23 11:05:00
     * @param
     * @return
     */
    String getDicByTypeCodeAndDicCode(String typeCode, String dicCode);

    /**
     * 描述:上传base64材料
     *
     * @author Madison You
     * @created 2019/12/23 12:44:00
     * @param
     * @return
     */
    String uploadFile(String busRecordid, String busTablename, String base64Code, String fileName);

    /**
     * 描述:上传补正材料到文件服务器
     *
     * @author Madison You
     * @created 2019/12/23 12:49:00
     * @param
     * @return
     */
    void uploadBzFile(String id, String base64Code);

    /**
     * 描述:工程建设项目当前在办环节人员信息表数据
     *
     * @author Madison You
     * @created 2020/3/5 14:24:00
     * @param
     * @return
     */
    List<Map<String, Object>> getProjectNodeData(SqlFilter filter);

    /**
     * 描述:查询林业用地审批办件发送返回信息
     *
     * @author Madison You
     * @created 2020/5/24 11:27:00
     * @param
     * @return
     */
    List<Map<String, Object>> findLyReturnData(String exeId);
}
