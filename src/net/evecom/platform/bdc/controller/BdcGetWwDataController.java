/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcGetWwDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 描述： 不动产获取省外网数据(预购商品房预告登记，预购商品房抵押权预告登记)
 *
 * @author Madison You
 * @created 2020年2月10日 上午12:14:56
 */
@Controller
@RequestMapping("/bdcGetWwDataController")
public class BdcGetWwDataController extends BaseController {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 9:23:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcGetWwDataController.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 14:03:00
     * @param
     * @return
     */
    @Resource
    private BdcGetWwDataService bdcGetWwDataService;

    /**
     * 描述:测试
     *
     * @author Madison You
     * @created 2020/4/15 9:23:00
     * @param
     * @return
     */
    @RequestMapping("/testData")
    @ResponseBody
    public Map<String,Object> testData(HttpServletRequest request, HttpServletResponse response) {
        bdcGetWwDataService.bdcGetWwData(log);
        return null;
    }

    /**
     * 描述:不动产获取外网数据
     *
     * @author Madison You
     * @created 2020/4/16 14:55:00
     * @param
     * @return
     */
    @RequestMapping("/getBdcWwData")
    @ResponseBody
    public Map<String, Object> getBdcWwData(HttpServletRequest request , HttpServletResponse response) {
        String bdcWwsqbh = request.getParameter("bdcWwsqbh");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        /*获取申请表数据*/
        try{
            Map<String, Object> sqbMap = bdcGetWwDataService.getByJdbc("T_BDCQLC_WWSJ_SQB",
                    new String[]{"SLBH"}, new Object[]{bdcWwsqbh});
            if (sqbMap != null) {
                returnMap.put("sqbData", sqbMap);
                /*获取申请人子表数据*/
                List<Map<String,Object>> sqrzbList = bdcGetWwDataService.getSqrzbData(bdcWwsqbh);
                returnMap.put("sqrzbData", sqrzbList);
                returnMap.put("success", true);
                returnMap.put("msg", "获取不动产外网数据成功");
            } else {
                returnMap.put("msg", "查询不到数据");
            }
        } catch (Exception e) {
            log.info("获取不动产外网数据失败");
            returnMap.put("msg", "获取不动产外网数据失败，请联系管理员");
        }
        return returnMap;
    }

    /**
     * 描述:不动产获取外网材料
     *
     * @author Madison You
     * @created 2020/4/16 17:07:00
     * @param
     * @return
     */
    @RequestMapping("/getBdcWwCl")
    @ResponseBody
    public List<Map<String, Object>> getBdcWwCl(HttpServletRequest request, HttpServletResponse response) {
        String bdcWwsqbh = request.getParameter("bdcWwsqbh");
        List<Map<String,Object>> clList = bdcGetWwDataService.findBdcWwClList(bdcWwsqbh);
        return clList;
    }

}
