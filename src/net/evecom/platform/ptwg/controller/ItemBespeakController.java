/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.service.BespeakOnlineService;

import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.ptwg.service.ItemBespeakService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-3-3 上午9:13:38
 */
@Controller
@RequestMapping("/itemBespeakController")
public class ItemBespeakController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ItemBespeakController.class);
    /**
     * 引入itemBespeakService
     */
    @Resource
    private ItemBespeakService itemBespeakService;
    /**
     * 引入bespeakOnlineService
     */
    @Resource
    private BespeakOnlineService bespeakOnlineService;
    /**
     * 引入dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    
    /**
     * 
     * 描述   预约部门列表
     * @author Danto Huang
     * @created 2017-3-3 上午9:25:19
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/goPortalIndex")
    public void goPortalIndex(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        StringBuffer sql = new StringBuffer();
        sql.append("select b.depart_id,d.depart_name from ");
        sql.append("(select t.depart_id from T_CKBS_BUSINESSDATA t where t.service_status=1 group by t.depart_id) b ");
        sql.append("left join t_msjw_system_department d on d.depart_id = b.depart_id ");
        sql.append("order by d.tree_sn ");
        List list = bespeakOnlineService.getListBySql(sql.toString(), null);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述    预约业务列表
     * @author Danto Huang
     * @created 2018年9月11日 下午2:46:54
     * @param request
     * @param response
     */
    @RequestMapping("/goBusinessChoose")
    public void goBusinessChoose(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String departId = request.getParameter("departId");
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name from T_CKBS_BUSINESSDATA t ");
        sql.append("left join t_msjw_system_department d on d.depart_id=t.depart_id ");
        sql.append("where t.depart_id=? and t.service_status=1 and t.is_appoint=1 ");
        List list = bespeakOnlineService.getListBySql(sql.toString(), new String[] { departId });
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-3-6 上午9:06:10
     * @param request
     * @param response
     */
    @RequestMapping("/bespeakTimeList")
    public void bespeakTimeList(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // 获取登陆用户信息
        String cardNo = request.getParameter("cardNo");
        //String userName = request.getParameter("userName");
        // 获取可提前预约的天数
        String tq = dictionaryService.getDicCode("wsyyktqyygzr", "可提前预约工作日");
        // 获取可预约日期（提前5个工作日）
        String wdsql = "select * from (select t.* from t_msjw_system_workday t " +
                "where t.w_date>to_char(sysdate, 'yyyy-MM-dd' ) and t.w_setid=2 " +
                "order by t.w_date asc ) wd where rownum<=?";
        List<Map<String, Object>> wdlist = bespeakOnlineService.getListBySql(wdsql, new String[] { tq });
        // 根据id获取可预约时段配置信息
        String departId = request.getParameter("departId");
        String kyysql = "select * from T_CKBS_APPOINTMENT_SET t where t.depart_id=? order by t.begin_time";
        List<Map<String, Object>> kyylist = bespeakOnlineService.getListBySql(kyysql, new String[] { departId });
        // 获取已预约数据
        List list = new ArrayList();
        for (Map wdmap : wdlist) {
            Map<String, Object> allmap = new HashMap<String, Object>();
            allmap.putAll(wdmap);
            List<Map<String, Object>> bespeaklist = new ArrayList<Map<String, Object>>();
            for (Map kyymap : kyylist) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.putAll(kyymap);
                StringBuffer yyysql = new StringBuffer();
                yyysql.append("select t.* from T_CKBS_APPOINTMENT_APPLY t ");
                yyysql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
                yyysql.append("where t.date_time = ? and t.begin_time=? and t.end_time=? and b.depart_id=? ");
                List<Map<String, Object>> yyylist = bespeakOnlineService.getListBySql(yyysql.toString(),
                        new String[] { wdmap.get("W_DATE").toString(), kyymap.get("BEGIN_TIME").toString(),
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
                        if (!StringUtils.isEmpty(cardNo) && yyymap.get("CARD").equals(cardNo)
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
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    预约
     * @author Danto Huang
     * @created 2018年9月11日 下午2:59:07
     * @param request
     * @return
     */
    @RequestMapping("/doPortalBespeakApply")
    @ResponseBody
    public AjaxJson doPortalBespeakApply(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        AjaxJson j = new AjaxJson();
        Sm4Utils sm4Utils = new Sm4Utils();
        String userName = request.getParameter("userName");
        String cardNo = request.getParameter("cardNo");
        String mobile = request.getParameter("mobile");
        if (StringUtils.isEmpty(userName)|| StringUtils.isEmpty(cardNo)) {
            j.setMsg("预约失败，请重新登陆后再试");
            j.setSuccess(false);
        } else {
            try {
                Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                variables.put("NAME", userName);
                variables.put("CARD", cardNo);
                variables.put("PHONE", mobile);
                Integer allowBespeakNumber = Integer.parseInt(variables.get("allowBespeakNumber").toString());
                // 该时段已预约数
                String yyysql = "select t.* from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.date_time = ? and t.begin_time=? and t.end_time=? " +
                        "and t.business_code=? and t.status='1'";
                List<Map<String, Object>> yyylist = bespeakOnlineService.getListBySql(yyysql,
                        new String[] { variables.get("DATE_TIME").toString(), variables.get("BEGIN_TIME").toString(),
                                variables.get("END_TIME").toString(), variables.get("BUSINESS_CODE").toString() });
                // 一个身份证，同一个部门一天只能预约一次
                String cardsql = "select * from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.card=? and t.date_time = ? and t.business_code=? and t.status='1'";
//                System.out.println(sm4Utils.encryptDataCBC(variables.get("CARD").toString()));
                List<Map<String, Object>> cardlist = bespeakOnlineService.getListBySql(cardsql,
                        new String[] { sm4Utils.encryptDataCBC(variables.get("CARD").toString()), variables.get("DATE_TIME").toString(),
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
                    bespeakOnlineService.saveOrUpdate(variables, "T_CKBS_APPOINTMENT_APPLY", null);
                    bespeakOnlineService.sendMsg(variables);
                    j.setMsg("预约成功");
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
                j.setMsg("预约失败，请稍后再试");
                j.setSuccess(false);
            }
        }
        return j;
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-3-6 上午9:06:10
     * @param request
     * @param response
     */
    @RequestMapping("/applyMaterList")
    public void applyMaterList(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String itemCode = request.getParameter("itemCode");
        List<Map<String,Object>> list = applyMaterService.findMaterByItemCode(itemCode);

        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    获取预约列表
     * @author Danto Huang
     * @created 2018年9月11日 下午2:59:19
     * @param request
     * @param response
     */
    @RequestMapping("/getPortalBespeakList")
    public void getPortalBespeakList(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String userName = request.getParameter("userName");
        String cardNo = request.getParameter("cardNo");
        List<Map<String, Object>> list;
        Integer start = Integer.parseInt(request.getParameter("start") == null ? "0" : request.getParameter("start"));
        Integer limit = Integer.parseInt(request.getParameter("limit") == null ? "10" : request.getParameter("limit"));
        Integer end = limit + start;
        String sql = "select *  from " +
                "(select a.*, rownum rownum_ from " +
                "(select t.*,d.business_name from T_CKBS_APPOINTMENT_APPLY t,T_CKBS_BUSINESSDATA d " +
                "where t.card = ? and t.name = ? and t.business_code=d.business_code " +
                "order by t.date_time desc,begin_time desc,t.create_time desc) " +
                "a where rownum < ?) b where b.rownum_ >= ?";
        list = bespeakOnlineService.getListBySql(sql,new Object[] { cardNo, userName, end, start });
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    取消预约
     * @author Danto Huang
     * @created 2018年9月11日 下午3:10:35
     * @param request
     * @return
     */
    @RequestMapping("/doPortalBespeakApplyDel")
    @ResponseBody
    public AjaxJson doPortalBespeakApplyDel(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        AjaxJson j = new AjaxJson();
        String userName = request.getParameter("userName");
        String cardNo = request.getParameter("cardNo");
        if (StringUtils.isEmpty(userName)||StringUtils.isEmpty(cardNo)) {
            j.setMsg("取消预约失败<br/>失败原因：获取登陆用户信息失败，请稍后再试");
            j.setSuccess(false);
        } else {
            try {
                String recordId = request.getParameter("recordId");
                Map<String, Object> info = bespeakOnlineService.getByJdbc("T_CKBS_APPOINTMENT_APPLY",
                        new String[] { "RECORD_ID" }, new String[] { recordId });
                if (!StringUtils.isEmpty(info) && info.get("IS_TAKE").equals("0")) {
                    info.put("STATUS", 0);
                    bespeakOnlineService.saveOrUpdate(info, "T_CKBS_APPOINTMENT_APPLY", recordId);
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
}
