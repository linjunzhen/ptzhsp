/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcAppointService;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 描述： 不动产权证预约
 *
 * @author Madison You
 * @created 2020年2月10日 上午12:14:56
 */
@Controller
@RequestMapping("/bdcAppointController")
public class BdcAppointController extends BaseController {
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/2/10 12:30:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcExecutionController.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/2/10 12:30:00
     * @param
     * @return
     */
    @Resource
    private BdcAppointService bdcAppointService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/4/19 14:54:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:不动产权证预约取证后台页面
     *
     * @author Madison You
     * @created 2020/2/10 12:13:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcqzAppointListView")
    public ModelAndView bdcqzAppointListView(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/statist/bdcqzAppointListView");
    }

    /**
     * 描述:不动产权证预约取证后台页面数据
     *
     * @author Madison You
     * @created 2020/2/10 12:29:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcqzAppointListData")
    public void bdcqzAppointListData(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = bdcAppointService.bdcqzAppointListData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:编辑页面
     *
     * @author Madison You
     * @created 2020/2/12 17:08:00
     * @param
     * @return
     */
    @RequestMapping(params = "editBdcqzAppointView")
    public ModelAndView editBdcqzAppointView(HttpServletRequest request) {
        String id = request.getParameter("ID");
        Map<String,Object> bdcMap = bdcAppointService.getByJdbc("T_CKBS_BDCQZ_APPOINTMENT",
                new String[]{"ID"}, new Object[]{id});
        if (bdcMap.get("LZ_TYPE").equals("0")) {
            request.setAttribute("LZ_TYPE", "窗口领证");
        } else {
            request.setAttribute("LZ_TYPE","送证上门");
        }
        request.setAttribute("bdcMap", bdcMap);
        return new ModelAndView("hflow/bdc/statist/editBdcqzAppointView");
    }

    /**
     * 描述:保存修改
     *
     * @author Madison You
     * @created 2020/2/12 17:24:00
     * @param
     * @return
     */
    @RequestMapping(params = "editBdcAppointUpdateAndSave")
    @ResponseBody
    public AjaxJson editBdcAppointUpdateAndSave(HttpServletRequest request , HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("ID");
        Map<String, Object> flowForm = BeanUtil.getMapFromRequest(request);
        bdcAppointService.saveOrUpdate(flowForm, "T_CKBS_BDCQZ_APPOINTMENT", id);
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 描述:导出表格
     *
     * @author Madison You
     * @created 2020/2/10 12:55:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcqzAppointExport")
    public void bdcqzAppointExport(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = bdcAppointService.bdcqzAppointListData(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/hflow/bdc/statist/bdcqzAppointListView.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }

        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("ID");
        excludeFields.add("REASON");
        excludeFields.add("CURRENTROW");
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心不动产权证预约领证表.xls",
                excludeFields, response, startRow, startCol, datas, "", false,
                new int[] { 1, 2 });
    }

    /**
     * 描述:预约不动产权证页面
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/2/10 13:54:00
     */
    @RequestMapping("/bdcqzAppointView")
    public ModelAndView bdcqzAppointView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hflow/bdc/bdcqzAppointView");
    }

    /**
     * 描述:预约不动产权证
     *
     * @author Madison You
     * @created 2020/2/10 16:34:00
     * @param
     * @return
     */
    @RequestMapping("/appointBdcqz")
    @ResponseBody
    public Map<String, Object> appointBdcqz(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        HashMap<String, Object> returnMap = new HashMap<>();
        String id = bdcAppointService.saveOrUpdate(variables, "T_CKBS_BDCQZ_APPOINTMENT", null);
        if (id != null && !id.isEmpty()) {
            ExecutorService service = Executors.newFixedThreadPool(1);
            String cqrName = StringUtil.getValue(variables, "CQR_NAME");
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        String mobile = dictionaryService.getDicCode("bdcyylz", "mobile");
                        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                        SmsSendUtil.sendSms(date + "," + cqrName, mobile, "198253");
                    } catch (UnsupportedEncodingException e) {
                        log.error("预约不动产权证，发送短信失败");
                    }
                }
            });
            service.shutdown();
            returnMap.put("success", true);
        }
        return returnMap;
    }

    /**
     * 描述:不动产领证按钮
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/2/10 13:54:00
     */
    @RequestMapping("/bdclzTab")
    public ModelAndView bdclzTab(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hflow/bdc/bdclzTab");
    }

    /**
     * 描述:不动产缴交登记费手机页面
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/2/10 13:54:00
     */
    @RequestMapping("/bdcRegisterPayView")
    public ModelAndView bdcRegisterPayView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hflow/bdc/bdcRegisterPayView");
    }

    /**
     * 描述:社保卡缴交登记费手机页面
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/2/10 13:54:00
     */
    @RequestMapping("/sbkRegisterPayView")
    public ModelAndView sbkRegisterPayView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hflow/bdc/sbkRegisterPayView");
    }




}
