/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.ptwg.dao.OnlineApplyDao;
import net.evecom.platform.ptwg.service.OnlineApplyService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-22 上午8:50:55
 */
@Service("onlineApplyService")
public class OnlineApplyServiceImpl extends BaseServiceImpl implements
        OnlineApplyService {

    /**
     * 所引入的dao
     */
    @Resource
    private OnlineApplyDao dao;

    /**
     * 
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 验证网格用户
     * @author Danto Huang
     * @created 2017-5-22 上午11:07:15
     * @param userCode
     * @param userName
     * @param userCard
     * @param userMobile
     * @return
     */
    public String checkGridUser(HttpServletRequest request){
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userCard = request.getParameter("userCard");
        String userMobile = request.getParameter("userMobile");
        Map<String,Object> user = this.getByJdbc("T_BSFW_USERINFO", new String[]{"YHZH"}, new Object[]{userCode});
        if(user==null){
            user = new HashMap<String, Object>();
            user.put("YHZH", userCode);
            user.put("DLMM", StringUtil.getMd5Encode(SysUser.DEFAULT_PASSWORD));
            user.put("USER_TYPE", "1");
            user.put("YHMC", userName);
            user.put("ZJLX", "SF");
            user.put("ZJHM", userCard);
            user.put("SJHM", userMobile);
            user.put("YHZT", "1");
            return dao.saveOrUpdate(user, "T_BSFW_USERINFO", null);
        }else{
            return user.get("USER_ID").toString();
        }
    }

    /**
     * 
     *  获取前台我的办件列表
     * @author Danto Huang
     * @created 2017-5-26 上午8:48:25
     * @param page
     * @param rows
     * @param yhzh
     * @param type
     * @return
     */
    public List<Map<String, Object>> findfrontWdbjList(String page, String rows,String yhzh,String type) {
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer( "SELECT E.EXE_ID,E.ITEM_NAME,E.RUN_STATUS,E.ITEM_CODE,");
        sql.append("(SELECT COUNT(*) FROM T_WSBS_BSPJ P WHERE P.EXE_ID=E.EXE_ID ) AS ISPJ,");
        sql.append("E.FINAL_HANDLEOPINION,E.CREATE_TIME,E.APPLY_STATUS,B.BJXX_ID FROM JBPM6_EXECUTION E ");
        sql.append(" LEFT JOIN T_WSBS_BJXX B ON E.EXE_ID=B.EXE_ID ");
        sql.append(" WHERE E.CREATOR_ACCOUNT = '"+yhzh+"' ");
        sql.append(" AND E.SQFS = '1' ");
        if("dealing".equals(type)){
            sql.append(" AND E.RUN_STATUS in ('0','1') ");
        }else if("overdone".equals(type)){
            sql.append(" AND E.RUN_STATUS in ('2','3','4','5','6','7') ");
        }else if("eveluate".equals(type)){
            sql.append(" AND E.RUN_STATUS in ('2','3','4','5','6','7') ");
            sql.append(" AND (SELECT COUNT(*) FROM T_WSBS_BSPJ P WHERE P.EXE_ID=E.EXE_ID )<1 ");
        }
        sql.append(" ORDER BY E.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        for (int i = 0; i < list.size(); i++) {
            String exeId=list.get(i).get("EXE_ID").toString();
            
            //设置状态标签
            String runstatus=list.get(i).get("RUN_STATUS").toString();
            if("0".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color: #008000;\">草稿</b>");
            }else if("1".equals(runstatus)){
                String taskStatus=null;
                boolean preAuditFlag=false;
                StringBuffer sql2 = new StringBuffer("SELECT TASK_ID,FROMTASK_IDS,TASK_NODENAME," +
                        "ASSIGNER_CODE,TASK_STATUS from JBPM6_TASK T ");
                sql2.append(" WHERE (T.TASK_STATUS='1' OR T.TASK_STATUS='-1') AND T.EXE_ID=? ");
                sql2.append("AND T.IS_AUDITED=1  ORDER BY T.CREATE_TIME DESC");
                //查询当前待办的任务,获取前一个任务的审批意见
                List<Map<String,Object>> tasks = dao.findBySql(sql2.toString(),new Object[]{exeId}, null);
                if(tasks!=null&&tasks.size()>0){
                    String fromTaskIds = (String) tasks.get(0).get("FROMTASK_IDS");
                    if (StringUtils.isNotEmpty(fromTaskIds)) {
                        Map<String, Object> fromTask = dao.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                                new Object[] { fromTaskIds.split(",")[0] });
                        // 获取退回意见
                        String BACKOPINION = (String) fromTask.get("HANDLE_OPINION");
                        list.get(i).put("BACKOPINION", BACKOPINION);
                        if(tasks.get(0).get("TASK_NODENAME").toString().contains("预审")||
                                "预审".equals(tasks.get(0).get("TASK_NODENAME").toString())||
                                "网上预审".equals(tasks.get(0).get("TASK_NODENAME").toString())){
                            list.get(i).put("preAuditState", "1");
                            preAuditFlag=true;
                        }
                    }
                    if(yhzh.equals(tasks.get(0).get("ASSIGNER_CODE"))){
                        list.get(i).put("TASK_STATUS", tasks.get(0).get("TASK_STATUS"));//待办；
                        list.get(i).put("TASK_ID", tasks.get(0).get("TASK_ID"));
                        taskStatus=tasks.get(0).get("TASK_STATUS").toString();
                    }
                }
                if("1".equals(taskStatus)){
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">待办</b>");
                }else if("-1".equals(taskStatus)){
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">退回</b>");
                }else if(preAuditFlag){
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">预审中</b>");
                }else{
                    list.get(i).put("runStatusStr","<b style=\"color: #008000;\">正在办理</b>");
                }
            }else if("2".equals(runstatus)||"6".equals(runstatus)||"3".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color:blue\">已办结</b>");
            }else if("4".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color:blue\">已办结（不通过）</b>");
            }else if("5".equals(runstatus)){
                list.get(i).put("runStatusStr", "<b style=\"color:blue;\">已办结（退件）</b>");
            }else if("7".equals(runstatus)){
                list.get(i).put("runStatusStr","<b style=\"color:blue;\">已办结（预审不通过）</b>");
            }
        }
        Map<String,Object> total = new HashMap<String, Object>();
        total.put("total", pb.getTotalItems());
        list.add(0, total);
        return list;
    }
}
