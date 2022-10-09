/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.model;


import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.project.dao.ProjectApplyDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * 描述   工程建设推送状态
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public abstract  class GcjsPushStatusTemplate {
    /**
     * 2021年省工改推送流程120的key
     */
    protected static final String GCJS_120="gcjs120";
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(GcjsPushStatusTemplate.class);
    /**
     * projectApplyDao
     */
    protected  ProjectApplyDao dao = (ProjectApplyDao)AppUtil.getBean("projectApplyDao");;
    /**
     *获取状态
     * @param flowDatas
     */
    protected abstract int getStatus(Map<String, Object> flowDatas);

    /**
     * 基础保存推送方法
     * @param flowDatas
     */
    @SuppressWarnings("unchecked")
    public void saveAfterToXmspsxblxxxxb(Map<String, Object> flowDatas) {
        int blzt=getStatus(flowDatas);
        if(blzt==0){
            return ;
        }
        // TODO Auto-generated method stub
        Map<String, Object> info = new HashMap<String, Object>();
        // 先获取流程实例申报号
        String exeId = (String) flowDatas.get("EFLOW_EXEID");
        // 是否暂存
        String EFLOW_ISTEMPSAVE = flowDatas.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowDatas.get("EFLOW_ISTEMPSAVE").toString();
        // 下一步环节名称
        //String EFLOW_NEWTASK_NODENAMES = flowDatas.get("EFLOW_NEWTASK_NODENAMES") == null ? ""
         //       : flowDatas.get("EFLOW_NEWTASK_NODENAMES").toString();
        // 定义流程实例对象
        Map<String, Object> flowExe = null;
        if (StringUtils.isNotEmpty(exeId) && !EFLOW_ISTEMPSAVE.equals("1")) {
            flowExe = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
            // 投资项目编号
            String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
            if (StringUtils.isNotEmpty(projectCode)) {
                // 获取办理意见内容
                String handleOpinion = flowDatas.get("EFLOW_HANDLE_OPINION") == null ? ""
                        : flowDatas.get("EFLOW_HANDLE_OPINION").toString();

                String blcs = flowDatas.get("BELONG_DEPTNAME") == null ? ""
                        : flowDatas.get("BELONG_DEPTNAME").toString();
                if (StringUtils.isEmpty(blcs)) {
                    // 获取当前用户信息
                    SysUser curUser = AppUtil.getLoginUser();
                    if (null != curUser) {
                        blcs = curUser.getDepName();
                    }
                }
                String blr = flowDatas.get("uploadUserName") == null ? "" : flowDatas.get("uploadUserName").toString();
                if (StringUtils.isEmpty(blr)) {
                    // 获取当前用户信息
                    SysUser curUser = AppUtil.getLoginUser();
                    if (null != curUser) {
                        blr = curUser.getFullname();
                    }
                }
                // 定义任务的办理时间
                String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                info.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
                info.put("XZQHDM", "350128");// 行政区划代码;
                info.put("GCDM", projectCode);// 工程代码;
                info.put("SPSXSLBM", exeId);// 审批事项实例编码
                info.put("BLCS", StringUtils.isEmpty(blcs) ? "项目投资处" : blcs);// 办理处（科）室
                info.put("BLR", StringUtils.isEmpty(blr) ? "项目投资处" : blr);// 办理人
                info.put("BLZT", blzt);// 办理状态
                info.put("BLYJ", handleOpinion);// 办理意见
                info.put("BLSJ", endTime);// 办理时间
                info.put("SJYXBS", "1");// 数据有效标识
                info.put("SJSCZT", "0");// 数据上传状态
                String dfsjzj = "";
                //保存基本信息表
                dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                
                //推送3的时候，马上再推送一条状态8的
                if (blzt == 3) {
                    saveAfterToXmspsxblxxxxb(8, exeId, blr, handleOpinion);
                }
                //特殊环节保存
                if(blzt==9||blzt==10){
                    saveAfterToXmspsxblxxxxb(blzt, exeId, flowExe, blr, handleOpinion);
                }
            }
        }
    }
    /**
     *
     * 描述：对特殊的环节增加特别程序
     *
     * @author Rider Chen
     * @created 2019年12月30日 上午11:05:39
     * @param blzt
     * @param exeId
     * @param flowExe
     * @param blr
     * @param blyj
     */
    @SuppressWarnings("unchecked")
    public void saveAfterToXmspsxblxxxxb(int blzt, String exeId, Map<String, Object> flowExe, String blr, String blyj) {
        Map<String, Object> info = new HashMap<String, Object>();
        // 投资项目编号
        String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
        if (StringUtils.isNotEmpty(projectCode)) {
            Map<String, Object> blxx3 = getSpsxblxxxxb(exeId, 3);
            if (null == blxx3 && blzt == 13) {
                blzt = 5;
            }
            // 定义任务的办理时间
            //String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            // 定义任务的办理时间(办理时间推迟二秒)
            String endTime = DateTimeUtil.getStrOfDate(new Date(new Date().getTime() + 2000), "yyyy-MM-dd HH:mm:ss");
            try {
                info.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
                info.put("XZQHDM", "350128");// 行政区划代码;
                info.put("GCDM", projectCode);// 工程代码;
                info.put("SPSXSLBM", exeId);// 审批事项实例编码
                // 获取当前用户信息
                SysUser curUser = AppUtil.getLoginUser();
                String blcs = "";
                if (null != curUser) {
                    blcs = curUser.getDepName();
                    if (StringUtils.isEmpty(blr)) {
                        blr = curUser.getFullname();
                    }
                }
                info.put("BLCS", StringUtils.isEmpty(blcs) ? "项目投资处" : blcs);// 办理处（科）室
                info.put("BLR", StringUtils.isEmpty(blr) ? "项目投资处" : blr);// 办理人
                info.put("BLZT", blzt);// 办理状态
                info.put("BLYJ", blyj);// 办理意见
                info.put("BLSJ", endTime);// 办理时间
                info.put("SJYXBS", "1");// 数据有效标识
                info.put("SJSCZT", "0");// 数据上传状态
                Map<String, Object> blxx = getSpsxblxxxxb(exeId, blzt);
                String dfsjzj = "";
                if (null == blxx || blzt == 9 || blzt == 10) {
                    if (blzt == 10) {
                        int newblzt = getSpsxblxxxxb(exeId);
                        if (newblzt == 9) {
                            dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                        }
                    } else {
                        int newblzt = getSpsxblxxxxb(exeId);
                        if (newblzt != 9) {
                            dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                        }
                    }
                }
                // 挂起（特别程序）
                if (blzt == 9) {
                    saveAfterXmspsxbltbcxxxb(exeId, flowExe);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }


    @SuppressWarnings("unchecked")
    public void saveAfterToXmspsxblxxxxb(int blzt, String exeId, String blr, String blyj) {
        // TODO Auto-generated method stub
        Map<String, Object> info = new HashMap<String, Object>();// 先获取流程实例申报号
        // 定义流程实例对象
        Map<String, Object> flowExe = null;
        if (StringUtils.isNotEmpty(exeId)) {
            flowExe = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
            // 投资项目编号
            String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
            if (StringUtils.isNotEmpty(projectCode)) {
                Map<String, Object> blxx3 = getSpsxblxxxxb(exeId, 3);
                if (null == blxx3 && blzt == 13) {
                    blzt = 5;
                }
                // 定义任务的办理时间(办理时间推迟一秒)
                String endTime = DateTimeUtil.getStrOfDate(new Date(new Date().getTime() + 1000), "yyyy-MM-dd HH:mm:ss");
                try {
                    info.put("LSH", getLsh("09", "S_SPGL_XMSPSXBLXXXXB"));// 流水号;
                    info.put("XZQHDM", "350128");// 行政区划代码;
                    info.put("GCDM", projectCode);// 工程代码;
                    info.put("SPSXSLBM", exeId);// 审批事项实例编码
                    // 获取当前用户信息
                    SysUser curUser = AppUtil.getLoginUser();
                    String blcs = "";
                    if (null != curUser) {
                        blcs = curUser.getDepName();
                        if (StringUtils.isEmpty(blr)) {
                            blr = curUser.getFullname();
                        }
                    }
                    info.put("BLCS", StringUtils.isEmpty(blcs) ? "项目投资处" : blcs);// 办理处（科）室
                    info.put("BLR", StringUtils.isEmpty(blr) ? "项目投资处" : blr);// 办理人
                    info.put("BLZT", blzt);// 办理状态
                    info.put("BLYJ", blyj);// 办理意见
                    info.put("BLSJ", endTime);// 办理时间
                    info.put("SJYXBS", "1");// 数据有效标识
                    info.put("SJSCZT", "0");// 数据上传状态
                    Map<String, Object> blxx = getSpsxblxxxxb(exeId, blzt);
                    String dfsjzj = "";
                    if (null == blxx || blzt == 9 || blzt == 10) {
                        if (blzt == 10) {
                            int newblzt = getSpsxblxxxxb(exeId);
                            if (newblzt == 9) {
                                dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                            }
                        } else {
                            int newblzt = getSpsxblxxxxb(exeId);
                            if (newblzt != 9) {
                                dao.saveOrUpdate(info, "SPGL_XMSPSXBLXXXXB", dfsjzj);
                            }
                        }
                    }
                    // 挂起（特别程序）
                    if (blzt == 9) {
                        saveAfterXmspsxbltbcxxxb(exeId);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    log.error("", e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void saveAfterXmspsxbltbcxxxb(String exeId) {
        String sql = "select h.hang_id as DFSJZJ,350128 as XZQHDM,e.project_code as GCDM,"
                + "E.EXE_ID as SPSXSLBM,l.link_name as TBCXMC,h.begin_time as TBCXKSSJ,"
                + "1 as TBCXSXLX,l.link_limittime as TBCXSX from  JBPM6_HANGINFO h,"
                + "JBPM6_TASK t ,JBPM6_EXECUTION e,T_WSBS_SERVICEITEM_LINK l "
                + "where t.task_id = h.task_id and t.exe_id = e.exe_id and "
                + "h.link_id = l.record_id and t.exe_id = ? "
                + "and t.step_seq>0 and e.project_code is not null order by  h.begin_time desc ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId }, null);
        DictionaryService  dictionaryService=(DictionaryService)AppUtil.getBean("dictionaryService");
        for (Map<String, Object> map : list) {
            Map<String, Object> dic = dictionaryService.get("SpecialLinkName", map.get("TBCXMC").toString());
            int tbcx = 8;
            if (null != dic) {
                tbcx = dic.get("DIC_DESC") == null ? 8 : Integer.parseInt(dic.get("DIC_DESC").toString());
            }
            map.put("LSH", getLsh("11", "S_SPGL_XMSPSXBLTBCXXXB"));// 流水号;
            map.put("TBCX", tbcx);
            map.put("SJYXBS", "1");// 数据有效标识
            map.put("SJSCZT", "0");// 数据上传状态
            Map<String, Object> tbcxMap = getSpsxbltbcxxxb(exeId, tbcx, map.get("TBCXMC").toString(),
                    map.get("TBCXKSSJ").toString());
            if (null == tbcxMap) {
                dao.saveOrUpdate(map, "SPGL_XMSPSXBLTBCXXXB", map.get("DFSJZJ").toString());
            }
        }
    }
    /**
     *
     * 描述： 获取特别程序
     *
     * @author Rider Chen
     * @created 2019年12月31日 上午10:17:06
     * @param exeId
     * @param tbcx
     * @param tbcxmc
     * @param tbcxkssj
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getSpsxbltbcxxxb(String exeId, int tbcx, String tbcxmc, String tbcxkssj) {
        String sql = " SELECT T.* FROM SPGL_XMSPSXBLTBCXXXB T WHERE  T.SPSXSLBM= ? AND T.TBCX=? "
                + " AND T.TBCXMC=? AND T.TBCXKSSJ=? ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId, tbcx, tbcxmc, tbcxkssj }, null);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    /**
     *
     * 描述 根据流水号获取最新的办理状态
     *
     * @author Rider Chen
     * @created 2019年7月5日 下午5:27:44
     * @param exeId
     * @return
     */
    @SuppressWarnings("unchecked")
    public int getSpsxblxxxxb(String exeId) {
        String sql = " SELECT T.* FROM SPGL_XMSPSXBLXXXXB T WHERE  T.SPSXSLBM= ? ORDER BY T.BLSJ DESC ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId, }, null);
        if (null != list && list.size() > 0) {
            return Integer.parseInt(list.get(0).get("BLZT").toString());
        }
        return 0;
    }
    /**
     *
     * @param exeId
     * @param flowExe
     */
    @SuppressWarnings("unchecked")
    public void saveAfterXmspsxbltbcxxxb(String exeId, Map<String, Object> flowExe) {

        // 投资项目编号
        String projectCode = flowExe.get("PROJECT_CODE") == null ? "" : flowExe.get("PROJECT_CODE").toString();
        Map<String, Object> info = new HashMap<String, Object>();
        int tbcx = 8;
        info.put("LSH", getLsh("11", "S_SPGL_XMSPSXBLTBCXXXB"));// 流水号;
        info.put("XZQHDM", 350128);// 行政区划代码
        info.put("GCDM", projectCode);// 工程代码
        info.put("SPSXSLBM", exeId);// 审批事项实例编码
        info.put("TBCX", tbcx);
        info.put("TBCXMC", "其他");
        // 特别程序开始时间
        String sTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        info.put("TBCXKSSJ", sTime);
        info.put("TBCXSXLX", 1);
        info.put("TBCXSX", 0);
        info.put("SJYXBS", "1");// 数据有效标识
        info.put("SJSCZT", "0");// 数据上传状态
        dao.saveOrUpdate(info, "SPGL_XMSPSXBLTBCXXXB", null);
    }

    /**
     *
     * 描述 根据流水号和办理状态获取详情
     *
     * @author Rider Chen
     * @created 2019年7月5日 下午5:27:44
     * @param exeId
     * @param blzt
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getSpsxblxxxxb(String exeId, int blzt) {
        String sql = " SELECT T.* FROM SPGL_XMSPSXBLXXXXB T WHERE  T.SPSXSLBM= ? AND T.BLZT=? ";
        List<Map<String, Object>> list = dao.findBySql(sql, new Object[] { exeId, blzt }, null);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    /**
     *
     * 描述：
     *
     * @author Rider Chen
     * @created 2019年6月17日 下午4:48:53
     * @param code
     *            数据对应表编码
     * @return
     */
    private String getLsh(String code, String seqName) {
        StringBuffer lsh = new StringBuffer("350128");
        lsh.append(DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss")).append(code)
                .append(StringUtil.getFormatNumber(10, getSeqValue(seqName)));
        return lsh.toString();
    }

    public String getSeqValue(String seqName) {
        // TODO Auto-generated method stub
        return dao.getSeqValue(seqName);
    }
}
