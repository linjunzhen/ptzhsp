/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import net.evecom.core.model.FileInfoMsg;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AjaxJsonCode;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述 流程实例数据操作service
 * @author Water Guo
 * @version 1.0
 * @created 2019年3月29日 上午9:13:08
 */
public interface ExeDataService extends BaseService {
    /**
     * 商事身份认证环节名称
     */
    public  static final  String ID_IDENTIFY_TASKNAME="身份认证";
    /**
     * 商事秒批身份认证环节名称
     */
    public  static final  String ID_IDENTIFY_TASKNAME_MP="秒批身份认证";
    /**
     * 商事秒批身份认证环节名称
     */
    public  static   String ID_AUTO_TASKNAME_MP="智能审批";
    /**
     * 成功
     */
    public  static   String EXE_SUCCESS="办结";
    /**
     * 失败
     */
    public  static   String EXE_BACK="退回";
    /**
     *
     * 描述 获取公司名列表
     * @author Flex Hu
     * @created 2015年8月18日 下午2:26:14
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findCompanyName(SqlFilter sqlFilter);
    /**
     * 根据sql语句获取list
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String,Object>> findListBySql(String sql,Object[] params,PagingBean pb);
    /**
     *
     * @param beginTime
     * @return
     */
    public List<Map<String, Object>> findExeIdOfCommercial(String beginTime);
    /**
     * 根据exeId获取面签人员信息
     * @param exeId
     * @return
     */
    public AjaxJsonCode getResultOfSign(String exeId,String name,String idNo);
    /**
     * 上传并解码成图片保存
     * @param token
     * @param baseCode
     * @param type
     * @return
     */
    public AjaxJsonCode uploadAndSaveImg(String token,String baseCode,String type);
    /**
     * 合伙执行事务合伙人
     * @param exeId
     */
    public Map<String,Object> getHhLegal(String exeId );
    /**
     * 根据办件id获取股东法定公司
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findLegalCompanyByexeId(String exeId);
    /**
     *回填面签信息，身份正反面，手写签名
     * @param exeId
     * @return
     */
    public Map<String,Object> setSignInfo(String exeId);
    /**
     * 给出类型和后缀名就可以获取文件保存的文件信息
     * @param type
     * @param suffix
     * @return
     */
    public FileInfoMsg getUploadFilePath(String type, String suffix);
    /**
     * 保存上传的面签视频
     * @param file
     * @param token
     * @return
     */
    public AjaxJsonCode handleSignVideo(CommonsMultipartFile file, String token);
    /**
     *回填面签信息到模板
     * @param buscord
     * @return
     */
    public Map<String,Object> setSignInfo(Map<String,Object> buscord);
    /**
     * 根据办件id判断是否全部面签，并把面签状态置为办件已面签状态
     * @param exeId
     */
    public void judgeIsAllSignToChangeStatus(String exeId) ;
    /**
     * 根据token获取公司名称
     * @param token
     * @return
     */
    public AjaxJsonCode getCompanyNameByToken(String token);
    /**
     * 商事意见栏jsonlist
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findCommercialOpinionList(SqlFilter sqlFilter);
    /**
     * 根据exeId获取面签列表信息
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findSignListById(String exeId);
    /**
     * 改变面签状态
     * @param exeId
     * @param flag
     */
    public void changeSignStatus(String exeId,String flag);
    /**
     * 根据事项
     * @param itemCode
     * @param materFilter
     * @return
     */
    public List<Map<String,Object>>  findMaterFileByItemCode(String itemCode,String materFilter);
    /**
     * 根据面签参数获得图片
     * @param exeId 办件id
     * @param idNo  身份证号
     * @param colName  字段名
     * @return
     */
    public Map<String,Object> setSignImgByParams(String exeId,String name,String idNo,String colName);
    /**
     * 根据exeId获取业务表信息
     * @param exeId
     * @return
     */
    public Map<String, Object> getBuscordMap(String exeId);
    /**
     * 退回，所有人重新面签
     * @param exeId
     * @return
     */
    public boolean  setAllSignBack(String exeId);
    /**
     * 根据SQL获取到list中的第一条结果MAP
     *
     * @param sql
     * @param objs
     * @return
     */
    public Map<String, Object> getFirstByJdbc(String sql, Object[] objs) ;
    /**
     * 获取信用平台的公司信息
     * @param sfzhm
     * @param entName
     * @return
     */
    public List<Map<String,Object>> findCreditCompanyInfos(String sfzhm,String entName);
    /**
     *
     * 描述：是否秒批
     * @author Rider Chen
     * @created 2020年12月1日 下午4:44:25
     * @param flowVars
     * @return
     */
    public Set<String> getIsAutoApproval(Map<String, Object> flowVars);
    /**
     * 根据表名获取需要面签的people手机信息
     * @param exeId
     * @return
     */
    public Set<Map<String,Object>> findReceiverMobileByTableName(String exeId);
    /**
     * 内资公司发送面签短信
     * @param flowVars
     * @return
     */
    public  Map<String,Object> sendMsgOfCompany(Map<String,Object> flowVars);
    /**
     * 个体商户发送面签短信
     * @param flowVars
     * @return
     */
    public  Map<String,Object> sendMsgOfIndividual(Map<String,Object> flowVars);

    /**
     * 推送公司名数据到开普云进行核验
     * @param map
     * @return
     */
    public AjaxJson pushCompanyName(Map<String,Object> map);

    /**
     * 推送公司名数据到开普云进行核验(个体)
     * @param map
     * @return
     */
    public AjaxJson pushCompanyNameToGt(Map<String,Object> map);
    /**
     * 公司名查重
     * @param map
     * @return
     */
    public AjaxJsonCode getCompanyNameResult(Map<String,Object> map);;
    /**
     * 公司名查重(个体)
     * @param map
     * @return
     */
    public AjaxJsonCode getCompanyNameResultToGt(Map<String,Object> map);
    /**
     * 短信定时发送
     */
    public void sendMsgByTime();
    /**
     *商事秒批退回短信
     * @param exeId
     */
    public void sendExeBackMsg(String exeId);
    /**
     * 根据exeId获取办件和业务表信息
     * @param exeId
     * @return
     */
    public Map<String, Object> getExeAndBuscordMap(String exeId) ;
    /**
     * 根据exeId获取办件和业务表信息
     * @param exeId
     * @return
     */
    public Map<String, Object> getBuscordAndExeMap(String exeId) ;
    
    /**
     * 根据办件id获取企业信息
     * @param exeId
     * @return
     */
    public Map<String,Object> findCompanyByExeId(String exeId);
    
    
}
