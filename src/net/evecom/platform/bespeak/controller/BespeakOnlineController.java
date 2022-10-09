/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.model.UserInfo;
import net.evecom.platform.bespeak.service.BespeakOnlineService;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 在线预约管理
 * 
 * @author Panda Chen
 * @created 2016-11-30 下午03:47:04
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/bespeakOnlineController")
public class BespeakOnlineController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BespeakOnlineController.class);
    /**
     * path
     */
    private String path = "bespeak/";
    /**
     * service
     */
    @Resource
    BespeakOnlineService service;
    /**
     * dictionaryService
     */
    @Resource
    DictionaryService dictionaryService;

    /**
     * 
     * 描述 后台管理主界面
     * 
     * @author Panda Chen
     * @created 2016-11-30 下午04:41:47
     * @param request
     * @return
     */
    @RequestMapping(params = "goIndex")
    public ModelAndView goIndex(HttpServletRequest request) {
        return new ModelAndView(path + "index");
    }

    /**
     * 
     * 描述获取树状结构
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:20:46
     * @param request
     * @param response
     * @see net.evecom.platform.base.controller.BaseController#tree(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(params = "tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = getParent();
        String json = JSON.toJSONString(root);
        log.info(json);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 获取父节点信息
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:20:59
     * @return
     */
    private Map<String, Object> getParent() {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "预约部门树");
        root.put("open", true);
        root.put("ID", "-1");
        root.put("TREE_LEVEL", 1);
        List<Map<String, Object>> toplist = service.findByParentId("0");
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("ID"));
            top.put("departId", top.get("DEPART_ID"));
            top.put("parentId", top.get("PARENT_ID"));
            top.put("name", top.get("DEPART_NAME"));
            this.getChildren(top, (String) top.get("ID"));
        }
        root.put("children", toplist);
        return root;
    }

    /**
     * 
     * 描述 获取孩子信息
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:21:11
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String parentId) {
        List<Map<String, Object>> children = service.findByParentId(parentId);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("ID"));
                child.put("name", child.get("DEPART_NAME"));
                child.put("departId", child.get("DEPART_ID"));
                child.put("parentId", child.get("PARENT_ID"));
                this.getChildren(child, (String) child.get("ID"));
            }
        }
    }

    /**
     * 
     * 描述 跳转到编辑页面
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:37:21
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String, Object> department = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(entityId) && !entityId.equals("undefined") && !entityId.equals("0")) {
            department = service.getByJdbc("T_BESPEAK_ONLINE_CONFIG", new String[] { "ID" }, new Object[] { entityId });
        }
        department.put("PARENT_ID", parentId);
        department.put("PARENT_NAME", parentName);
        request.setAttribute("department", department);
        return new ModelAndView(path + "info");
    }

    /**
     * 
     * 描述保存
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:40:47
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("STATUS", DepartmentService.NORMAL_STATUS);
        }
        try {
            service.saveOrUpdateTreeData(parentId, variables);
            j.setMsg("保存成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
            j.setMsg("保存失败");
            j.setSuccess(false);
        }

        return j;
    }

    /**
     * 
     * 描述 删除
     * 
     * @author Panda Chen
     * @created 2016-12-2 上午11:13:13
     * @param request
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        service.passAppointmentToDeptId(selectColNames);
        service.remove("T_BESPEAK_ONLINE_CONFIG", "ID", new String[] { selectColNames });
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 描述获取列表数据
     * 
     * @author Panda Chen
     * @created 2016-12-2 上午11:26:28
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.SN", "desc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = service.findBySqlFilter(filter, "T_BESPEAK_TIME_CONFIG");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 打开时段配置页面
     * 
     * @author Panda Chen
     * @created 2016-11-30 下午04:41:47
     * @param request
     * @return
     */
    @RequestMapping(params = "goInfoForTime")
    public ModelAndView goInfoForTime(HttpServletRequest request) {
        String id = request.getParameter("ID");
        String onLineId = request.getParameter("onLineId");
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("ONLINE_ID", onLineId);
        if (StringUtils.isNotBlank(id)) {
            info = service.getByJdbc("T_BESPEAK_TIME_CONFIG", new String[] { "ID" }, new Object[] { id });
        }
        request.setAttribute("info", info);
        return new ModelAndView(path + "timeInfo");
    }

    /**
     * 
     * 描述修改时段配置
     * 
     * @author Panda Chen
     * @created 2016-12-2 上午11:40:03
     * @param request
     * @return
     */
    @RequestMapping(params = "doEditForTime")
    @ResponseBody
    public AjaxJson doEditForTime(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            int sn = service.getMaxSn("T_BESPEAK_TIME_CONFIG", "SN");
            variables.put("SN", sn + 1);
        }
        try {
            service.saveOrUpdate(variables, "T_BESPEAK_TIME_CONFIG", entityId);
            j.setMsg("保存成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
            j.setMsg("保存失败");
            j.setSuccess(false);
        }

        return j;
    }

    /**
     * 
     * 描述 删除时段配置
     * 
     * @author Panda Chen
     * @created 2016-12-2 上午11:40:56
     * @param request
     * @return
     */
    @RequestMapping(params = "doDelForTime")
    @ResponseBody
    public AjaxJson doDelForTime(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        service.remove("T_BESPEAK_TIME_CONFIG", "ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 描述更新表格拖拽排序结果
     * 
     * @author Panda Chen
     * @created 2016年3月24日 上午10:37:47
     * @param request
     * @return
     */
    @RequestMapping(params = "updateSn")
    @ResponseBody
    public AjaxJson updateSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] configIds = request.getParameterValues("configIds[]");
        service.updateSn("SN", "T_BESPEAK_TIME_CONFIG", configIds);
        j.setMsg("排序成功");
        return j;
    }

    /**
     * 
     * 描述移动端首页(2018-07-24修改业务部门选择)
     * 
     * @author Panda Chen
     * @created 2016-12-5 上午10:37:23
     * @param request
     * @return
     */
    @RequestMapping("/goPortalIndex")
    public ModelAndView goPortalIndex(HttpServletRequest request) {
        /*String parentId = request.getParameter("parentId");
        if (StringUtils.isEmpty(parentId)) {
            parentId = "0";
        }
        String sql = "select t.*,(select count(t1.id) " +
                "from t_bespeak_online_config t1 where t1.parent_id = t.id) " +
                "as count from t_bespeak_online_config t where t.parent_id=? " +
                "order by t.tree_sn asc,t.create_time asc";
        List list = service.getListBySql(sql, new String[] { parentId });*/

        StringBuffer sql = new StringBuffer();
        sql.append("select b.depart_id,d.depart_name from ");
        sql.append("(select t.depart_id from T_CKBS_BUSINESSDATA t where t.service_status=1 group by t.depart_id) b ");
        sql.append("left join t_msjw_system_department d on d.depart_id = b.depart_id ");
        sql.append("order by d.tree_sn ");
        List list = service.getListBySql(sql.toString(), null);
        request.setAttribute("list", list);
        return new ModelAndView(path + "portalIndex");
    }
    
    /**
     * 
     * 描述    业务选择
     * @author Danto Huang
     * @created 2018年7月24日 上午10:12:55
     * @param request
     * @return
     */
    @RequestMapping("/goBusinessChoose")
    public ModelAndView goBusinessChoose(HttpServletRequest request) {
        String departId = request.getParameter("departId");
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name from T_CKBS_BUSINESSDATA t ");
        sql.append("left join t_msjw_system_department d on d.depart_id=t.depart_id ");
        sql.append("where t.depart_id=? and t.service_status=1 and t.is_appoint=1 ");
        List list = service.getListBySql(sql.toString(), new String[] { departId });
        request.setAttribute("list", list);
        return new ModelAndView(path + "businessChoose");
    }

    /**
     * 
     * 描述 获取时段预约信息列表
     * 
     * @author Panda Chen
     * @created 2016-12-6 上午09:06:41
     * @param request
     * @return
     */
    @RequestMapping("/goPortalBespeakTime")
    public ModelAndView goPortalBespeakTime(HttpServletRequest request) {
        // 获取登陆用户信息
        String userCode = request.getParameter("userCode");
        UserInfo userInfo = service.getUser(userCode);
        // 获取可提前预约的天数
        String tq = dictionaryService.getDicCode("wsyyktqyygzr", "可提前预约工作日");
        // 获取可预约日期（提前5个工作日）
        String wdsql = "select * from (select t.* from t_msjw_system_workday t " +
                "where t.w_date>to_char(sysdate, 'yyyy-MM-dd' ) and t.w_setid=2 " +
                "order by t.w_date asc ) wd where rownum<=?";
        List<Map<String, Object>> wdlist = service.getListBySql(wdsql, new String[] { tq });
        log.info("当天不可预约");
        if(wdlist!=null&&wdlist.size()>0) {
            log.info(wdlist.get(0).get("W_DATE"));
        }
        // 根据id获取可预约时段配置信息
        /*String id = request.getParameter("id");*/
        String departId = request.getParameter("departId");
        /*String kyysql = "select bt.* from t_bespeak_time_config bt " +
                "where bt.ONLINE_ID = (select b.id from " +
                "(select s.* from  t_bespeak_online_config s start " +
                "with  s.id=? connect by prior  s.parent_id=s.id) b where b.parent_id='0') " +
                "order by bt.sn desc";*/
        String kyysql = "select * from T_CKBS_APPOINTMENT_SET t where t.depart_id=? order by t.begin_time";
        List<Map<String, Object>> kyylist = service.getListBySql(kyysql, new String[] { departId });
        // 获取已预约数据
        /*String departId = request.getParameter("departId");*/
        List list = new ArrayList();
        for (Map wdmap : wdlist) {
            Map<String, Object> allmap = new HashMap<String, Object>();
            allmap.putAll(wdmap);
            List<Map<String, Object>> bespeaklist = new ArrayList<Map<String, Object>>();
            for (Map kyymap : kyylist) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.putAll(kyymap);
                /*String yyysql = "select t.* from t_bespeak_apply t " +
                        "where t.date_time = ? and t.begin_time=? " +
                        "and t.end_time=? and t.depart_id=? and t.status='1'";*/
                StringBuffer yyysql = new StringBuffer();
                yyysql.append("select t.* from T_CKBS_APPOINTMENT_APPLY t ");
                yyysql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
                yyysql.append("where t.date_time = ? and t.begin_time=? and t.end_time=? and b.depart_id=? ");
                yyysql.append(" and t.status<>0 ");
                List<Map<String, Object>> yyylist = service.getListBySql(yyysql.toString(), new String[] {
                        wdmap.get("W_DATE").toString(), kyymap.get("BEGIN_TIME").toString(),
                        kyymap.get("END_TIME").toString(), departId });
                map.put("bespeakNumber", yyylist.size());
                Date nowTime = new Date();
                long time = DateTimeUtil.getIntervalTime(DateTimeUtil.getStrOfDate(nowTime, "yyyy-MM-dd HH:mm"), wdmap
                        .get("W_DATE").toString() + " " + kyymap.get("BEGIN_TIME").toString(), "yyyy-MM-dd HH:mm", 3);
                // 小于十分钟不可预约，大于十分钟可预约，也可取消预约0：不可预约，1：可预约，2：取消预约
                if (time < 10) {
                    map.put("isOverTime", "0");
                } else {
                    map.put("isOverTime", "1");
                    for (Map yyymap : yyylist) {
                        Map<String, Object> tempmap = new HashMap<String, Object>();
                        tempmap.putAll(yyymap);
                        if (!StringUtils.isEmpty(userInfo) && yyymap.get("CARD").equals(userInfo.getIdentityNo())
                                && yyymap.get("STATUS").equals("1")) {
                            map.put("isOverTime", 2);
                            map.put("applyId", tempmap.get("RECORD_ID"));
                            break;
                        }
                    }
                }
                log.info(map);
                bespeaklist.add(map);
            }
            allmap.put("bespeaklist", bespeaklist);
            list.add(allmap);
        }
        request.setAttribute("list", list);
        return new ModelAndView(path + "portalBespeakTime");
    }

    /**
     * 
     * 描述 跳转到预约表单页面
     * 
     * @author Panda Chen
     * @created 2016-12-6 上午09:12:42
     * @param request
     * @return
     */
    @RequestMapping("/goPortalBespeakApply")
    public ModelAndView goPortalBespeakApply(HttpServletRequest request) {
        return new ModelAndView(path + "portalBespeakApply");
    }

    /**
     * 
     * 描述 预约
     * 
     * @author Panda Chen
     * @created 2016-12-6 上午09:06:30
     * @param request
     * @return
     */
    @RequestMapping("/doPortalBespeakApply")
    @ResponseBody
    public AjaxJson doPortalBespeakApply(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userCode = request.getParameter("userCode");
        UserInfo userInfo = service.getUser(userCode);
        if (StringUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo.getIdentityNo())
                || StringUtils.isEmpty(userInfo.getUserName())) {
            j.setMsg("预约失败，请重新登陆后再试");
            j.setSuccess(false);
        } else {
            try {
                Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                variables.put("NAME", userInfo.getUserName());
                variables.put("CARD", userInfo.getIdentityNo());
                variables.put("PHONE", userInfo.getMobile());
                Integer allowBespeakNumber = Integer.parseInt(variables.get("allowBespeakNumber").toString());
                // 该时段已预约数
                /*String yyysql = "select t.* from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.date_time = ? and t.begin_time=? and t.end_time=? " +
                        "and t.depart_id=? and t.status='1'";*/
                String yyysql = "select t.* from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.date_time = ? and t.begin_time=? and t.end_time=? " +
                        "and t.business_code=? and t.status='1'";
                List<Map<String, Object>> yyylist = service.getListBySql(yyysql,
                        new String[] { variables.get("DATE_TIME").toString(), variables.get("BEGIN_TIME").toString(),
                                variables.get("END_TIME").toString(), variables.get("BUSINESS_CODE").toString() });
                // 一个身份证，同一个部门一天只能预约一次
                /*String cardsql = "select * from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.card=? and t.date_time = ? and t.depart_id=? and t.status='1'";*/
                String cardsql = "select * from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.card=? and t.date_time = ? and t.business_code=? and t.status='1'";
                List<Map<String, Object>> cardlist = service.getListBySql(cardsql,
                        new String[] { variables.get("CARD").toString(), variables.get("DATE_TIME").toString(),
                                variables.get("BUSINESS_CODE").toString() });
                if (cardlist.size() > 0) {
                    j.setMsg("本次预约失败<br/>您已预约了" + cardlist.get(0).get("DATE_TIME") + " "
                            + cardlist.get(0).get("BEGIN_TIME") + "-" + cardlist.get(0).get("END_TIME") + "时段");
                    j.setSuccess(false);
                } else if (allowBespeakNumber <= yyylist.size()) {
                    j.setMsg("该时段已预约满");
                    j.setSuccess(false);
                } else {
                    variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    service.saveOrUpdate(variables, "T_CKBS_APPOINTMENT_APPLY", null);
                    service.sendMsg(variables);
                    j.setMsg("预约成功");
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
                j.setMsg("预约失败，请稍后再试");
                j.setSuccess(false);
            }
        }
        return j;
    }

    /**
     * 
     * 描述 获取用户信息
     * 
     * @author Panda Chen
     * @created 2016-11-1 下午03:11:20
     * @param request
     * @return
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public AjaxJson getUserInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userCode = request.getParameter("userCode");
        UserInfo userInfo = service.getUser(userCode);
        if (StringUtils.isEmpty(userInfo)) {
            j.setMsg("获取用户信息失败，请稍后再试");
            j.setSuccess(false);
        } else {
            j.setJsonString(userInfo.toString());
            j.setSuccess(true);
        }
        return j;
    }

    /**
     * 
     * 描述 取消预约
     * 
     * @author Panda Chen
     * @created 2016-12-6 上午09:06:30
     * @param request
     * @return
     */
    @RequestMapping("/doPortalBespeakApplyDel")
    @ResponseBody
    public AjaxJson doPortalBespeakApplyDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userCode = request.getParameter("userCode");
        UserInfo userInfo = service.getUser(userCode);
        if (StringUtils.isEmpty(userInfo)) {
            j.setMsg("取消预约失败<br/>失败原因：获取登陆用户信息失败，请稍后再试");
            j.setSuccess(false);
        } else {
            try {
                String id = request.getParameter("id");
                Map<String, Object> info = service.getByJdbc("T_CKBS_APPOINTMENT_APPLY", new String[] { "RECORD_ID" },
                        new String[] { id });
                if (!StringUtils.isEmpty(info) && info.get("IS_TAKE").equals("0")) {
                    info.put("STATUS", 0);
                    service.saveOrUpdate(info, "T_CKBS_APPOINTMENT_APPLY", id);
                    j.setMsg("取消预约成功");
                } else {
                    j.setMsg("取消预约失败<br/>失败原因：该预约号已进行人工取号，不得取消");
                    j.setSuccess(false);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
                j.setMsg("取消预约失败");
                j.setSuccess(false);
            }
        }

        return j;
    }

    /**
     * 
     * 描述 跳转到预约表单页面
     * 
     * @author Panda Chen
     * @created 2016-12-6 上午09:12:42
     * @param request
     * @return
     */
    @RequestMapping("/goPortalBespeakList")
    public ModelAndView goPortalBespeakList(HttpServletRequest request) {
        return new ModelAndView(path + "portalBespeakList");
    }

    /**
     * 
     * 描述 获取预约列表
     * 
     * @author Panda Chen
     * @created 2016-12-7 下午04:10:05
     * @param request
     * @param response
     */
    @RequestMapping("/getPortalBespeakList")
    @ResponseBody
    public AjaxJson getPortalBespeakList(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String userCode = request.getParameter("userCode");
        UserInfo userInfo = service.getUser(userCode);
        List<Map<String, Object>> list;
        Integer start = Integer.parseInt(request.getParameter("start") == null ? "0" : request.getParameter("start"));
        Integer limit = Integer.parseInt(request.getParameter("limit") == null ? "10" : request.getParameter("limit"));
        Integer end = limit + start;
        if (!StringUtils.isEmpty(userInfo)) {
            /*String sql = "select *  from " +
                    "(select a.*, rownum rownum_ from " +
                    "(select t.*,d.depart_name from T_CKBS_APPOINTMENT_APPLY t,t_msjw_system_department d " +
                    "where t.card = ? and t.name = ? and t.depart_id=d.depart_id " +
                    "order by t.date_time desc,begin_time desc,t.create_time desc) " +
                    "a where rownum < ?) b where b.rownum_ >= ?";*/
            String sql = "select *  from " +
                    "(select a.*, rownum rownum_ from " +
                    "(select t.*,d.business_name from T_CKBS_APPOINTMENT_APPLY t,T_CKBS_BUSINESSDATA d " +
                    "where t.card = ? and t.name = ? and t.business_code=d.business_code " +
                    "order by t.date_time desc,begin_time desc,t.create_time desc) " +
                    "a where rownum < ?) b where b.rownum_ >= ?";
            list = service.getListBySql(sql, new Object[] { userInfo.getIdentityNo(), userInfo.getUserName(), end,
                    start });
            j.setJsonString(JSON.toJSONString(list));
        } else {
            j.setSuccess(false);
            j.setMsg("获取列表数据失败，请稍后再试");
        }
        return j;
    }
}
