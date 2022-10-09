/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.sync.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.sync.service.SwbDataService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-8-24 下午2:28:23
 */
@Controller
@RequestMapping("/swbDataController")
public class SwbDataController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SwbDataController.class);
    /**
     * swbDataService
     */
    @Resource
    private SwbDataService swbDataService;
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-8-24 下午2:34:44
     * @param request
     * @return
     */
    @RequestMapping(params="goSwbSync")
    public ModelAndView goSwbSync(HttpServletRequest request){
        return new ModelAndView("sync/swb/swbsynctab");
    }
    /**
     * 
     * 描述 转跳到省网办返回信息页面
     * @author Kester Chen
     * @created 2017-11-6 下午3:26:53
     * @param request
     * @return
     */ 
    @RequestMapping(params="goSwbReturnInfo")
    public ModelAndView goSwbReturnInfo(HttpServletRequest request){
        return new ModelAndView("sync/swb/swbReturnInfo");
    }
    /**
     * 
     * 描述 转跳到省网办返回信息统计页面
     * @author Kester Chen
     * @created 2020年6月8日 下午3:43:20
     * @param request
     * @return
     */
    @RequestMapping(params="goSwbReturnInfoStatis")
    public ModelAndView goSwbReturnInfoStatis(HttpServletRequest request){
        return new ModelAndView("sync/swb/swbReturnInfoStatis");
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-8-24 下午2:34:44
     * @param request
     * @return
     */
    @RequestMapping(params="goBjxxSync")
    public ModelAndView goBjxxSync(HttpServletRequest request){
        return new ModelAndView("sync/swb/bjxxSync");
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-8-25 上午8:51:25
     * @param request
     * @param response
     */
    @RequestMapping(params="bjxxUnSyncData")
    public void bjxxUnSyncData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "asc");
        List<Map<String, Object>> list = swbDataService.getUnSyncBjxx(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2017-11-6 下午3:34:53
     * @param request
     * @param response
     */
    @RequestMapping(params="swbReturnInfoData")
    public void swbReturnInfoData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.send_time", "desc");
        List<Map<String, Object>> list = swbDataService.getSwbReturnInfoData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2020年6月8日 下午3:44:03
     * @param request
     * @param response
     */
    @RequestMapping(params="swbReturnInfoStatisData")
    public void swbReturnInfoStatisData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = swbDataService.getSwbReturnInfoStatisData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 同步返回信息表
     * @author Kester Chen
     * @created 2017-11-6 下午4:47:27
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSwbReturnInfoExcel")
    public void exportSwbReturnInfoExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = swbDataService
                .findBySwbReturnInfoSqlFilter(filter);
        
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/sync/swb/swbReturnInfo.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "同步返回信息表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }
    /**
     * 
     * 描述  同步返回信息统计表
     * @author Kester Chen
     * @created 2020年6月8日 下午5:12:21
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSwbReturnInfoStatisExcel")
    public void exportSwbReturnInfoStatisExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = swbDataService
                .findBySwbReturnInfoStatisSqlFilter(filter);
        
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/sync/swb/swbReturnInfoStatis.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "同步返回信息统计表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }
    /**
     * 
     * 描述   办件信息同步
     * @author Danto Huang
     * @created 2016-8-25 下午2:20:08
     * @param request
     * @return
     */
    @RequestMapping(params="bjxxSync")
    @ResponseBody
    public AjaxJson bjxxSync(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String Ids = request.getParameter("exeIds");
        String[] exeIds = Ids.split(",");
        for(int i=0;i<exeIds.length;i++){
            String exeId = exeIds[i];
            swbDataService.saveBjxxData(exeId);
        }
        j.setMsg("同步成功");
        return j;
    }
    
    /**
     * 
     * 描述   跳转过程信息同步
     * @author Danto Huang
     * @created 2016-8-24 下午2:34:44
     * @param request
     * @return
     */
    @RequestMapping(params="goGcxxSync")
    public ModelAndView goGcxxSync(HttpServletRequest request){
        return new ModelAndView("sync/swb/gcxxSync");
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-8-25 上午8:51:25
     * @param request
     * @param response
     */
    @RequestMapping(params="gcxxUnSyncData")
    public void gcxxUnSyncData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "desc");
        List<Map<String, Object>> list = swbDataService.getUnSyncGcxx(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述   过程信息同步
     * @author Danto Huang
     * @created 2016-8-25 下午2:20:08
     * @param request
     * @return
     */
    @RequestMapping(params="gcxxSync")
    @ResponseBody
    public AjaxJson gcxxSync(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String Ids = request.getParameter("exeIds");
        String[] exeIds = Ids.split(",");
        for(int i=0;i<exeIds.length;i++){
            String exeId = exeIds[i];
            swbDataService.saveGcxxData(exeId);
        }
        j.setMsg("同步成功");
        return j;
    }
    
    /**
     * 
     * 描述   跳转过程信息同步
     * @author Danto Huang
     * @created 2016-8-24 下午2:34:44
     * @param request
     * @return
     */
    @RequestMapping(params="goJgxxSync")
    public ModelAndView goJgxxSync(HttpServletRequest request){
        return new ModelAndView("sync/swb/jgxxSync");
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-8-25 上午8:51:25
     * @param request
     * @param response
     */
    @RequestMapping(params="jgxxUnSyncData")
    public void jgxxUnSyncData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "desc");
        List<Map<String, Object>> list = swbDataService.getUnSyncJgxx(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述   结果信息同步
     * @author Danto Huang
     * @created 2016-8-25 下午2:20:08
     * @param request
     * @return
     */
    @RequestMapping(params="jgxxSync")
    @ResponseBody
    public AjaxJson jgxxSync(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String Ids = request.getParameter("exeIds");
        String[] exeIds = Ids.split(",");
        for(int i=0;i<exeIds.length;i++){
            String exeId = exeIds[i];
            swbDataService.saveJgxxData(exeId);
        }
        j.setMsg("同步成功");
        return j;
    }
}
