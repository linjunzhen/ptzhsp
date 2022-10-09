/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import ewebeditor.admin.main_jsp;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.service.BespeakOnlineService;
import net.evecom.platform.bespeak.util.Sm4Utils;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.system.service.DictionaryService;
import net.sf.json.JSONObject;

/**
 * 描述
 * 
 * @author Panda Chen
 * @created 2017年10月27日 上午9:15:28
 */
@Controller
@RequestMapping("/bespeakForMztController")
public class BespeakForMztController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BespeakForMztController.class);
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
     * 描述获取预约列表
     * 
     * @author Panda Chen
     * @created 2017年10月27日 上午9:17:52
     * @param request
     * @return
     */
    @RequestMapping("/getBespeakList")
    @ResponseBody
    public AjaxJson getBespeakList(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        // String sql = "select t.ID,t.DEPART_ID,t.DEPART_NAME,level from
        // t_bespeak_online_config t start with t.parent_id = '0' connect by
        // t.parent_id=prior t.id order siblings by t.tree_sn";
        // List<Map<String, Object>> list = service.getListBySql(sql, null);
        JSONObject jsonObject = getParam(request);
        if (jsonObject == null || jsonObject.isNullObject()) {
            j.setSuccess(false);
            j.setMsg("解密失败，请确认参数格式");
            return j;
        }
        String userName = jsonObject.getString("userName");
        String card = jsonObject.getString("card");
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(card) || StringUtils.isEmpty(phone)) {
            j.setMsg("预约请求参数不全");
            j.setSuccess(false);
            return j;
        }
        // String parentId = jsonObject.getString("parentId");
        // String sql = "select t.*,(select count(t1.id) " + "from
        // t_bespeak_online_config t1 where t1.parent_id = t.id) "
        // + "as count from t_bespeak_online_config t where t.parent_id=? "
        // + "order by t.tree_sn asc,t.create_time asc";
        // List<Map<String, Object>> list = service.getListBySql(sql, new
        // String[] { parentId });
        StringBuffer sql = new StringBuffer();
        sql.append("select b.depart_id,d.depart_name from ");
        sql.append("(select t.depart_id from T_CKBS_BUSINESSDATA t where t.service_status=1 group by t.depart_id) b ");
        sql.append("left join t_msjw_system_department d on d.depart_id = b.depart_id ");
        sql.append("order by d.tree_sn ");
        List<Map<String, Object>> list = service.getListBySql(sql.toString(), null);
        j.setJsonString(JSON.toJSONString(list));
        return j;
    }

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2018年9月30日 上午11:35:36
     * @param request
     * @return
     */
    @RequestMapping("/getBusinessList")
    @ResponseBody
    public AjaxJson getBusinessList(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        JSONObject jsonObject = getParam(request);
        if (jsonObject == null || jsonObject.isNullObject()) {
            j.setSuccess(false);
            j.setMsg("解密失败，请确认参数格式");
            return j;
        }
        String userName = jsonObject.getString("userName");
        String card = jsonObject.getString("card");
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(card) || StringUtils.isEmpty(phone)) {
            j.setMsg("预约请求参数不全");
            j.setSuccess(false);
            return j;
        }
        String departId = jsonObject.getString("departId");
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name from T_CKBS_BUSINESSDATA t ");
        sql.append("left join t_msjw_system_department d on d.depart_id=t.depart_id ");
        sql.append("where t.depart_id=? and t.service_status=1 and t.is_appoint=1 ");
        List<Map<String, Object>> list = service.getListBySql(sql.toString(), new String[] { departId });
        j.setJsonString(JSON.toJSONString(list));
        return j;
    }

    /**
     * 
     * 描述根据部门id获取预约时间列表
     * 
     * @author Panda Chen
     * @created 2017年10月27日 上午9:17:52
     * @param request
     * @return
     */
    @RequestMapping("/getTimeListById")
    @ResponseBody
    public AjaxJson getTimeListById(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        JSONObject jsonObject = getParam(request);
        if (jsonObject == null || jsonObject.isNullObject()) {
            j.setSuccess(false);
            j.setMsg("解密失败，请确认参数格式");
            return j;
        }
        String userName = jsonObject.getString("userName");
        String card = jsonObject.getString("card");
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(card) || StringUtils.isEmpty(phone)) {
            j.setMsg("预约请求参数不全");
            j.setSuccess(false);
            return j;
        }
        // 获取可提前预约的天数
        String tq = dictionaryService.getDicCode("wsyyktqyygzr", "可提前预约工作日");
        // 获取可预约日期（提前5个工作日）
        String wdsql = "select * from (select t.* from t_msjw_system_workday t "
                + "where t.w_date>to_char(sysdate, 'yyyy-MM-dd' ) and t.w_setid=2 "
                + "order by t.w_date asc ) wd where rownum<=?";
        List<Map<String, Object>> wdlist = service.getListBySql(wdsql, new String[] { tq });
        // 根据id获取可预约时段配置信息
        // String id = jsonObject.getString("id");
        // String kyysql = "select bt.* from t_bespeak_time_config bt " + "where
        // bt.ONLINE_ID = (select b.id from "
        // + "(select s.* from t_bespeak_online_config s start "
        // + "with s.id=? connect by prior s.parent_id=s.id) b where
        // b.parent_id='0') " + "order by bt.sn desc";
        // List<Map<String, Object>> kyylist = service.getListBySql(kyysql, new
        // String[] { id });
        String departId = jsonObject.getString("departId");
        String kyysql = "select * from T_CKBS_APPOINTMENT_SET t where t.depart_id=? order by t.begin_time";
        List<Map<String, Object>> kyylist = service.getListBySql(kyysql, new String[] { departId });

        // 获取已预约数据
        // String departId = jsonObject.getString("departId");
        List list = new ArrayList();
        for (Map wdmap : wdlist) {
            Map<String, Object> allmap = new HashMap<String, Object>();
            allmap.putAll(wdmap);
            List<Map<String, Object>> bespeaklist = new ArrayList<Map<String, Object>>();
            for (Map kyymap : kyylist) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.putAll(kyymap);
                // String yyysql = "select t.* from t_bespeak_apply t " + "where
                // t.date_time = ? and t.begin_time=? "
                // + "and t.end_time=? and t.depart_id=? and t.status='1'";
                StringBuffer yyysql = new StringBuffer();
                yyysql.append("select t.* from T_CKBS_APPOINTMENT_APPLY t ");
                yyysql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
                yyysql.append("where t.date_time = ? and t.begin_time=? and t.end_time=? and b.depart_id=? ");
                yyysql.append(" and t.status<>0 ");
                List<Map<String, Object>> yyylist = service.getListBySql(yyysql.toString(),
                        new String[] { wdmap.get("W_DATE").toString(), kyymap.get("BEGIN_TIME").toString(),
                                kyymap.get("END_TIME").toString(), departId });
                map.put("bespeakNumber", yyylist.size());
                Date nowTime = new Date();
                long time = DateTimeUtil.getIntervalTime(DateTimeUtil.getStrOfDate(nowTime, "yyyy-MM-dd HH:mm"),
                        wdmap.get("W_DATE").toString() + " " + kyymap.get("BEGIN_TIME").toString(), "yyyy-MM-dd HH:mm",
                        3);
                // 小于十分钟不可预约，大于十分钟可预约，也可取消预约0：不可预约，1：可预约，2：取消预约
                if (time < 10) {
                    map.put("isOverTime", "0");
                } else {
                    map.put("isOverTime", "1");
                    for (Map yyymap : yyylist) {
                        Map<String, Object> tempmap = new HashMap<String, Object>();
                        tempmap.putAll(yyymap);
                        if (!StringUtils.isEmpty(card) && yyymap.get("CARD").equals(card)
                                && yyymap.get("STATUS").equals("1")) {
                            map.put("isOverTime", 2);
                            map.put("applyId", tempmap.get("ID"));
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
        j.setJsonString(JSON.toJSONString(list));
        return j;
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
    @RequestMapping("/doBespeakApply")
    @ResponseBody
    public AjaxJson doBespeakApply(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        JSONObject jsonObject = getParam(request);
        if (jsonObject == null || jsonObject.isNullObject()) {
            j.setSuccess(false);
            j.setMsg("解密失败，请确认参数格式");
            return j;
        }
        String userName = jsonObject.getString("userName");
        String card = jsonObject.getString("card");
        String phone = jsonObject.getString("phone");

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(card) || StringUtils.isEmpty(phone)) {
            j.setMsg("预约请求参数不全");
            j.setSuccess(false);
        } else {
            try {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("NAME", userName);
                variables.put("CARD", card);
                variables.put("PHONE", phone);
                variables.put("DATE_TIME", jsonObject.getString("dateTime"));
                variables.put("BEGIN_TIME", jsonObject.getString("beginTime"));
                variables.put("END_TIME", jsonObject.getString("endTime"));
                variables.put("DEPART_ID", jsonObject.getString("departId"));
                variables.put("allowBespeakNumber", jsonObject.getString("allowBespeakNumber"));
                variables.put("BUSINESS_CODE", jsonObject.getString("BUSINESS_CODE"));
                Integer allowBespeakNumber = Integer.parseInt(variables.get("allowBespeakNumber").toString());
                // 该时段已预约数
                // String yyysql = "select t.* from t_bespeak_apply t "
                // + "where t.date_time = ? and t.begin_time=? and t.end_time=?
                // "
                // + "and t.depart_id=? and t.status='1'";

                String yyysql = "select t.* from T_CKBS_APPOINTMENT_APPLY t "
                        + "where t.date_time = ? and t.begin_time=? and t.end_time=? "
                        + "and t.business_code=? and t.status='1'";
                List<Map<String, Object>> yyylist = service
                        .getListBySql(yyysql,
                                new String[] { variables.get("DATE_TIME").toString(),
                                        variables.get("BEGIN_TIME").toString(), variables.get("END_TIME").toString(),
                                        variables.get("BUSINESS_CODE").toString() });
                // 一个身份证，同一个部门一天只能预约一次
                // String cardsql = "select * from t_bespeak_apply t "
                // + "where t.card=? and t.date_time = ? and t.depart_id=? and
                // t.status='1'";
                String cardsql = "select * from T_CKBS_APPOINTMENT_APPLY t "
                        + "where t.card=? and t.date_time = ? and t.business_code=? and t.status='1'";
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
                    String id = service.saveOrUpdate(variables, "T_CKBS_APPOINTMENT_APPLY", null);
                    service.sendMsg(variables);
                    j.setMsg(id);
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
     * 描述 取消预约
     * 
     * @author Panda Chen
     * @created 2016-12-6 上午09:06:30
     * @param request
     * @return
     */
    @RequestMapping("/doBespeakApplyDel")
    @ResponseBody
    public AjaxJson doBespeakApplyDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        JSONObject jsonObject = getParam(request);
        if (jsonObject == null || jsonObject.isNullObject()) {
            j.setSuccess(false);
            j.setMsg("解密失败，请确认参数格式");
            return j;
        }
        String userName = jsonObject.getString("userName");
        String card = jsonObject.getString("card");
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(card) || StringUtils.isEmpty(phone)) {
            j.setMsg("预约请求参数不全");
            j.setSuccess(false);
        } else {
            try {
                String id = jsonObject.getString("id");
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
                log.error(e.getMessage());
                j.setMsg("取消预约失败");
                j.setSuccess(false);
            }
        }

        return j;
    }

    /**
     * 
     * 描述 获取我的预约列表
     * 
     * @author Panda Chen
     * @created 2016-12-7 下午04:10:05
     * @param request
     * @param response
     */
    @RequestMapping("/getBespeakInfoById")
    @ResponseBody
    public AjaxJson getBespeakInfoById(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        List<Map<String, Object>> list;
        JSONObject jsonObject = getParam(request);
        if (jsonObject == null || jsonObject.isNullObject()) {
            j.setSuccess(false);
            j.setMsg("解密失败，请确认参数格式");
            return j;
        }
        String id = jsonObject.getString("id");
        String sql = "select t.* from t_bespeak_apply t where t.id = ?";
        list = service.getListBySql(sql, new Object[] { id });
        j.setJsonString(JSON.toJSONString(list));
        return j;
    }

    /**
     * 
     * 描述 获取我的预约列表
     * 
     * @author Panda Chen
     * @created 2016-12-7 下午04:10:05
     * @param request
     * @param response
     */
    @RequestMapping("/getMyBespeakList")
    @ResponseBody
    public AjaxJson getMyBespeakList(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        List<Map<String, Object>> list;
        JSONObject jsonObject = getParam(request);
        if (jsonObject == null || jsonObject.isNullObject()) {
            j.setSuccess(false);
            j.setMsg("解密失败，请确认参数格式");
            return j;
        }
        Integer start = Integer.parseInt(jsonObject.getString("start") == null ? "0" : jsonObject.getString("start"));
        Integer limit = Integer.parseInt(jsonObject.getString("limit") == null ? "10" : jsonObject.getString("limit"));
        Integer end = limit + start;
        String userName = jsonObject.getString("userName");
        String card = jsonObject.getString("card");
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(card) || StringUtils.isEmpty(phone)) {
            j.setMsg("预约请求参数不全");
            j.setSuccess(false);
        } else {
            // String sql = "select * from " + "(select a.*, rownum rownum_ from
            // "
            // + "(select t.*,d.depart_name from t_bespeak_apply
            // t,t_msjw_system_department d "
            // + "where t.card = ? and t.name = ? and t.depart_id=d.depart_id "
            // + "order by t.date_time desc,begin_time desc,t.create_time desc)
            // "
            // + "a where rownum < ?) b where b.rownum_ >= ?";

            String sql = "select *  from " + "(select a.*, rownum rownum_ from "
                    + "(select t.*,d.business_name from T_CKBS_APPOINTMENT_APPLY t,T_CKBS_BUSINESSDATA d "
                    + "where t.card = ? and t.name = ? and t.business_code=d.business_code "
                    + "order by t.date_time desc,begin_time desc,t.create_time desc) "
                    + "a where rownum < ?) b where b.rownum_ >= ?";
            list = service.getListBySql(sql, new Object[] { card, userName, end, start });
            j.setJsonString(JSON.toJSONString(list));
        }
        return j;
    }

    /**
     * 
     * 描述 解密
     * 
     * @author Panda Chen
     * @created 2017年10月27日 上午10:55:33
     * @param request
     * @return
     */
    public JSONObject getParam(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            String json = request.getParameter("json");
            Sm4Utils sm4 = new Sm4Utils("evecom@123456789", "evecom@987654321", false);
            json = sm4.decryptDataCBC(json);
            jsonObject = JSONObject.fromObject(json);
        } catch (Exception e) {
            log.error("解密失败，请确认格式是否正确");
        }
        return jsonObject;
    }

}
