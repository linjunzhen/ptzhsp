/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.platform.bdc.util.WordRedrawUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcDyqscdjDao;
import net.evecom.platform.bdc.service.BdcDyqscdjService;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bdc.service.BdcQlcCreateSignFlowService;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.service.BdcYgspfygdjService;
import net.evecom.platform.bdc.util.ExcelRedrawUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述 不动产抵押权登记操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("bdcDyqscdjService")
public class BdcDyqscdjServiceImpl extends BaseServiceImpl implements BdcDyqscdjService {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcDyqscdjServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private BdcDyqscdjDao dao;
    /**
     * 所引入的serivice
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 所引入的service
     */
    @Resource
    private BdcGyjsjfwzydjService gyjsjfwzydjService;

    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * 描述:审批表打印配置类
     *
     * @author Madison You
     * @created 2020/4/27 11:29:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService bdcSqbConfig;

    /**
     * 引入service
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;

    /**
     * 引入service
     */
    @Resource
    private BdcYgspfygdjService bdcYgspfygdjService;

    /**
     * 引入service
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;

    /**
     * 引入service
     */
    @Resource
    private BdcQlcCreateSignFlowService bdcQlcCreateSignFlowService;

    /**
     * 覆盖获取实体dao方法 描述
     *
     * @return
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 一审自定义数据回填方法
     *
     * @param args
     * @author Roger Li
     * @created 2019年12月25日 下午4:24:34
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setRedrawData(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");
        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }
        // 为标签设初值以防页面出现标签
        this.setDefaultValue(returnMap, StringUtil.getValue(execution, "EXE_ID"));
        // 设值流程主表相关信息 如收件人
        this.setSubmitUsrsInfo(returnMap, execution);
        // 设值流程相关信息 如环节审核人、审核意见等
        this.setFlowAllObj(returnMap, flowAllObj);
        // 设值服务事项和流程表单相关信息 如服务事项名称
        // this.setItemAndDefInfo(returnMap, serviceItem);
        // 设值业务表相关信息
        this.setBusRecordInfo(returnMap, busInfo);
        // 模板类型 templateType未设值则前端默认使用DataRegion方式进行数据填充
        // DataRegion(通过添加书签做的模板)则前端以DataRegion方式填充数据
        // DataTag(通过添加【ITEM_NAME】类似标识的方式做的模板)则前端以DataRegion方式填充数据
        returnMap.put("templateType", "DataTag");
        /* 模板字符串替换 */
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述 为标签设初值以防页面出现标签
     *
     * @param returnMap
     * @return
     * @author Roger Li
     * @created 2019年12月17日 下午2:35:52
     */
    private void setDefaultValue(Map<String, Object> returnMap, String exeId) {
        returnMap.put("ewm", "");
        returnMap.put("dlrxm", "");
        returnMap.put("lxdh2", "");
        returnMap.put("dljgmc", "");
        returnMap.put("dlrxm2", "");
        returnMap.put("lxdh4", "");
        returnMap.put("dljgmc2", "");
        returnMap.put("yb", "");
        returnMap.put("yb1", "");
        returnMap.put("lz", "");
        returnMap.put("bz", "");
        returnMap.put("xydzl", "");
        returnMap.put("xydbdcdyh", "");

        // 审核意见
        returnMap.put("hd", "");
        returnMap.put("fzr", "");
        returnMap.put("hdsj", "");
        returnMap.put("sh", "");
        returnMap.put("scr1", "");
        returnMap.put("fhrq", "");
        returnMap.put("cs", "");
        returnMap.put("scr", "");
        returnMap.put("fsrq", "");

        returnMap.put("djyy", "");
        returnMap.put("djyyzmwj", "");
    }

    /**
     * 描述 获取流程主表数据
     *
     * @param execution
     * @return
     * @author Roger Li
     * @created 2019年12月17日 上午10:52:45
     */
    private void setSubmitUsrsInfo(Map<String, Object> returnMap, Map<String, Object> execution) {
        // 申请人通信地址
        returnMap.put("txdz1", StringUtil.getValue(execution, "SQRLXDZ"));
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
    }

    /**
     * 描述 设值服务事项和流程表单相关信息
     *
     * @param returnMap,serviceItem
     * @return
     * @author Roger Li
     * @created 2019年12月17日 下午2:26:46
     */
    private void setItemAndDefInfo(Map<String, Object> returnMap, Map<String, Object> serviceItem) {
        // 权力类型在setBusInfo中 取的是抵押明细json中的fw_qllx
        // 登记类型
        returnMap.put("djlx", StringUtil.getValue(serviceItem, "CATALOG_NAME"));
    }

    /**
     * 描述 设值流程相关信息
     *
     * @param returnMap
     * @param flowAllObj
     * @author Roger Li
     * @created 2019年12月27日 下午3:34:33
     */
    @SuppressWarnings("unchecked")
    private void setFlowAllObj(Map<String, Object> returnMap, Map<String, Object> flowAllObj) {
        // TODO 审核意见
        String exeId = StringUtil.getValue((Map<String, Object>) flowAllObj.get("EFLOWOBJ"), "EFLOW_EXEID");
        List<Map<String, Object>> tasks = gyjsjfwzydjService.findAuditTaskListByExeId(exeId);
        for (Map<String, Object> task : tasks) {
            String nodeName = StringUtil.getValue(task, "TASK_NODENAME");
            String assigner = StringUtil.getValue(task, "ASSIGNER_NAME");
            String opinion = StringUtil.getValue(task, "HANDLE_OPINION");
            String date = StringUtil.getValue(task, "END_TIME");
            if (StringUtils.isNotBlank(date)) {
                date = DateTimeUtil.getChinaDate(date);
            } else {
                date = "";
            }
            if (StringUtils.isBlank(assigner)) {
                assigner = StringUtil.getValue(task, "TEAM_NAMES");
            }
            if (nodeName.equals("登簿")) {
                returnMap.put("hd", opinion);
                returnMap.put("fzr", assigner);
                returnMap.put("hdsj", date);
            }
        }
    }

    /**
     * 描述 设值业务表数据
     *
     * @param busInfo
     * @return
     * @author Roger Li
     * @created 2019年12月17日 上午10:56:30
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setBusRecordInfo(Map<String, Object> returnMap, Map<String, Object> busInfo) {
        // 生成二维码
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            String path = ExcelRedrawUtil.getSavePath("jpg");
            ExcelRedrawUtil.encodeVersion6(returnMap.get("xmsqbh").toString(), path);
            returnMap.put("ewm", "[image]" + path + "[/image]");
        }
        returnMap.put("qllx", "抵押权");
        // 权利人信息 抵押权人
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "DYQRXX_NAME"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "DYQRXX_IDTYPE"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
        returnMap.put("txdz", StringUtil.getValue(busInfo, "DYQRXX_ADDR"));
        returnMap.put("yb", "");
        returnMap.put("fddbr", StringUtil.getValue(busInfo, "DYQRXX_LEGAL_NAME"));
        returnMap.put("lxdh1", "");
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "DYQRXX_AGENT_NAME"));
        returnMap.put("lxdh2", "");
        returnMap.put("dljgmc", "");

        // 义务人信息 抵押人
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "DYQDJ_NAME"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "DYQDJ_IDTYPE"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "DYQDJ_IDNO"));
        returnMap.put("txdz1", "");
        returnMap.put("yb1", "");
        returnMap.put("fddbr1", "");
        returnMap.put("lxdh3", "");
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DYQDJ_AGENT_NAME"));
        returnMap.put("lxdh4", "");
        returnMap.put("dljgmc1", "");

        List<Map> dymx = JSON.parseArray(StringUtil.getValue(busInfo, "DYMX_JSON"), Map.class);
        Map<String, Object> dymxMap = null;
        if (dymx.size() > 0) {
            dymxMap = dymx.get(0);
        }
        returnMap.put("zl", StringUtil.getValue(dymxMap, "FDZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(dymxMap, "BDCDYH"));
        StringBuilder qlxz = new StringBuilder();
        String zdxz = StringUtil.getValue(dymxMap, "QLXZ");
        String fwxz = StringUtil.getValue(dymxMap, "FWXZ");
        if (StringUtils.isNotBlank(zdxz)) {
            qlxz.append(zdxz).append("/");
            if (StringUtils.isNotBlank(fwxz)) {
                qlxz.append(fwxz);
            }
        }
        returnMap.put("qlxz", qlxz.toString());
        // TODO 写死
        returnMap.put("bdclx", "土地和房屋");
        StringBuilder sb = new StringBuilder();
        String zdmj = StringUtil.getValue(dymxMap, "ZDMJ");
        String fwjzmj = StringUtil.getValue(dymxMap, "JZMJ");
        if (StringUtils.isNotBlank(zdmj)) {
            sb.append("宗地面积:").append(zdmj).append("平方米/");
            if (StringUtils.isNotBlank(fwjzmj)) {
                sb.append("房屋建筑面积:").append(fwjzmj).append("平方米");
            }
        }
        returnMap.put("mj", sb.toString());
        returnMap.put("yt", StringUtil.getValue(dymxMap, "FW_GHYT"));
        returnMap.put("ybdcqzsh", StringUtil.getValue(dymxMap, "BDCQZH"));
        returnMap.put("gzwlx", "");

        // 抵押情况
        if (StringUtil.getValue(busInfo, "DYQDJ_SFZGEDY").equals("0")) {
            returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DYQDJ_BDBZZQSE"));
        } else {
            returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DYQDJ_ZGZQE"));
        }
        if (StringUtils.isNotBlank((String) busInfo.get("DYQDJ_ZWQSSJ"))
                && StringUtils.isNotBlank((String) busInfo.get("DYQDJ_ZWJSSJ"))) {
            StringBuilder zwlxqx = new StringBuilder();
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DYQDJ_ZWQSSJ"))).append("起 ");
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DYQDJ_ZWJSSJ"))).append("止");
            returnMap.put("zwlxqx", zwlxqx.toString());
        } else {
            returnMap.put("zwlxqx", "");
        }
        // 地役权情况
        String TAKECARD_TYPE = StringUtil.getValue(busInfo, "TAKECARD_TYPE");
        if (StringUtils.isNotEmpty(TAKECARD_TYPE) && TAKECARD_TYPE.equals("2")) {
            returnMap.put("djlx", "首次登记（转本）");
        } else {
            returnMap.put("djlx", "首次登记");
        }

        returnMap.put("djyy", StringUtil.getValue(busInfo, "DYQDJ_DJYY"));
        returnMap.put("djyyzmwj", "");

        returnMap.put("hd", StringUtil.getValue(busInfo, "DJSHXX_HZYJ"));
        returnMap.put("fzr", StringUtil.getValue(busInfo, "DJSHXX_SHR"));
        returnMap.put("hdsj", StringUtil.getValue(busInfo, "DJSHXX_SHJSRQ"));
    }

    /**
     * 
     * 描述 抵押权首次登记（以及转本登记）业务数据回填
     * 
     * @author Allin Lin
     * @created 2020年12月8日 上午10:47:55
     * @param args
     */
    public void setDyqscdjData(Map<String, Map<String, Object>> args) {
        /* 获取args数据 */
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /* 获取业务数据 */
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /* 初始化审批表字段 */
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /* 设置抵押权首次登记业务数据 */
        this.setDyqscdjBusRecordInfo(returnMap, busInfo, execution);

        /* 模板字符串替换 */
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述 设置抵押权首次登记业务数据
     * 
     * @author Allin Lin
     * @created 2020年12月8日 上午11:13:14
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void setDyqscdjBusRecordInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        String sqfs = execution.get("SQFS") == null ? "" : execution.get("SQFS").toString();
        String sjr = "平潭综合实验区行政服务中心";
        if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("2")) {
            sjr = StringUtil.getValue(execution, "CREATOR_NAME");
        }
        returnMap.put("sjr", sjr);

        returnMap.put("qllx", "抵押权");
        // 登记类型
        String TAKECARD_TYPE = StringUtil.getValue(busInfo, "TAKECARD_TYPE");
        if (StringUtils.isNotEmpty(TAKECARD_TYPE) && TAKECARD_TYPE.equals("2")) {
            returnMap.put("djlx", "抵押权首次登记（转本）");
        } else {
            returnMap.put("djlx", "抵押权首次登记");
        }

        // 权利人信息 抵押权人
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "DYQRXX_NAME"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "DYQRXX_IDTYPE"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
        returnMap.put("txdz", StringUtil.getValue(busInfo, "DYQRXX_ADDR"));
        returnMap.put("yb", "");
        returnMap.put("fddbr", StringUtil.getValue(busInfo, "DYQRXX_LEGAL_NAME"));
        returnMap.put("lxdh1", "");
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "DYQRXX_AGENT_NAME"));
        returnMap.put("lxdh2", "");
        returnMap.put("dljgmc", "");

        // 义务人信息 抵押人
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "DYQDJ_NAME"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "DYQDJ_IDTYPE"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "DYQDJ_IDNO"));
        returnMap.put("txdz1", "");
        returnMap.put("yb1", "");
        returnMap.put("fddbr1", "");
        returnMap.put("lxdh3", "");
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DYQDJ_AGENT_NAME"));
        returnMap.put("lxdh4", "");
        returnMap.put("dljgmc1", "");

        List<Map> dymx = JSON.parseArray(StringUtil.getValue(busInfo, "DYMX_JSON"), Map.class);
        Map<String, Object> dymxMap = null;
        if (dymx.size() > 0) {
            dymxMap = dymx.get(0);
        }
        returnMap.put("zl", StringUtil.getValue(dymxMap, "FDZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(dymxMap, "BDCDYH"));
        StringBuilder qlxz = new StringBuilder();
        String zdxz = StringUtil.getValue(dymxMap, "QLXZ");
        String fwxz = StringUtil.getValue(dymxMap, "FWXZ");
        if (StringUtils.isNotBlank(zdxz)) {
            qlxz.append(zdxz).append("/");
            if (StringUtils.isNotBlank(fwxz)) {
                qlxz.append(fwxz);
            }
        }
        returnMap.put("qlxz", qlxz.toString());
        returnMap.put("bdclx", "土地和房屋");
        StringBuilder sb = new StringBuilder();
        String zdmj = StringUtil.getValue(dymxMap, "ZDMJ");
        String fwjzmj = StringUtil.getValue(dymxMap, "JZMJ");
        if (StringUtils.isNotBlank(zdmj)) {
            sb.append("宗地面积:").append(zdmj).append("平方米/");
            if (StringUtils.isNotBlank(fwjzmj)) {
                sb.append("房屋建筑面积:").append(fwjzmj).append("平方米");
            }
        }
        returnMap.put("mj", sb.toString());
        returnMap.put("yt", StringUtil.getValue(dymxMap, "FW_GHYT"));
        returnMap.put("ybdcqzsh", StringUtil.getValue(dymxMap, "BDCQZH"));
        returnMap.put("gzwlx", "");

        // 抵押情况
        if (StringUtil.getValue(busInfo, "DYQDJ_SFZGEDY").equals("0")) {
            returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DYQDJ_BDBZZQSE"));
        } else {
            returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DYQDJ_ZGZQE"));
        }
        if (StringUtils.isNotBlank((String) busInfo.get("DYQDJ_ZWQSSJ"))
                && StringUtils.isNotBlank((String) busInfo.get("DYQDJ_ZWJSSJ"))) {
            StringBuilder zwlxqx = new StringBuilder();
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DYQDJ_ZWQSSJ"))).append("起 ");
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DYQDJ_ZWJSSJ"))).append("止");
            returnMap.put("zwlxqx", zwlxqx.toString());
        } else {
            returnMap.put("zwlxqx", "");
        }

        returnMap.put("djyy", StringUtil.getValue(busInfo, "DYQDJ_DJYY"));
        returnMap.put("djyyzmwj", "");

        returnMap.put("hd", StringUtil.getValue(busInfo, "DJSHXX_HZYJ"));
        returnMap.put("fzr", StringUtil.getValue(busInfo, "DJSHXX_SHR"));
        returnMap.put("hdsj", StringUtil.getValue(busInfo, "DJSHXX_SHJSRQ"));

    }

    /**
     * 
     * @Description 初始化业务数据
     * @author Luffy Cai
     * @date 2020年8月25日
     * @param returnMap
     * @param flowVars
     *            void
     */
    public void initGenValue(Map<String, Object> returnMap, Map<String, Object> flowVars) {
        // setDyqscdjtyBusInfo(returnMap, flowVars, flowVars);
        setDyqscdjBusInfo(returnMap, flowVars, flowVars);
        returnMap.put("xmsqbh", StringUtil.getValue(flowVars, "EFLOW_EXEID"));
        Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { flowVars.get("EFLOW_EXEID") });
        if (flowExe == null) {
            flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                    new Object[] { flowVars.get("EFLOW_EXEID") });
        }
        returnMap.put("xmsqsj", DateTimeUtil.getChinaDate(StringUtil.getValue(flowExe, "CREATE_TIME")));
    }

    /**
     * 描述:查找分户办件
     *
     * @author Madison You
     * @created 2020/8/26 14:40:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBdcfhData(String bdcdyh) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT x.* FROM JBPM6_EXECUTION T LEFT JOIN T_BDCQLC_GYJSJFWZYDJ X ON T.BUS_RECORDID = X.YW_ID ");
        sql.append(" WHERE X.ESTATE_NUM = ? AND T.RUN_STATUS = '2' AND X.ZYDJ_TYPE = '1' ORDER BY T.CREATE_TIME DESC");
        List list = dao.findBySql(sql.toString(), new Object[] { bdcdyh }, null);
        return list;
    }

    /**
     * @Description:设置国有建设及房屋转移登记通用业务数据
     * @author Madison You
     * @created 2020/4/30 9:18:00
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void setDyqscdjtyBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        returnMap.put("clscsj", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));
        if (StringUtil.getValue(execution, "SQFS").equals("1")) {
            returnMap.put("sjr", "");
        } else {
            returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        }
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isEmpty(sbh)) {
            returnMap.put("xmsqbh", StringUtil.getValue(busInfo, "EFLOW_EXEID"));
        }
        /* 根据单元号获取分户信息 */
        String BDCDYH = StringUtil.getValue(busInfo, "BDCDYH");
        List<Map<String, Object>> list = findBdcfhData(BDCDYH);
        Map<String, Object> map = list.get(0);
        /* 权利人信息JSON */
        String piJson = StringUtil.getValue(map, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            StringBuffer qlrxm = new StringBuffer();
            StringBuffer sfzjzl = new StringBuffer();
            StringBuffer zjhm = new StringBuffer();
            StringBuffer txdz = new StringBuffer();
            StringBuffer yb = new StringBuffer();
            StringBuffer fddbr = new StringBuffer();
            StringBuffer dlrxm = new StringBuffer();
            StringBuffer lxdh1 = new StringBuffer();
            StringBuffer lxdh2 = new StringBuffer();
            List<Map<String, Object>> sqrInfoList = new ArrayList<>();

            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /* 证件类型代码转换 */
                String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                sfzjzl.append(documentType).append(",");
                zjhm.append(StringUtil.getValue(piMap, "POWERPEOPLEIDNUM")).append(",");
                txdz.append(StringUtil.getValue(piMap, "POWERPEOPLEADDR")).append(",");
                yb.append(StringUtil.getValue(piMap, "POWERPEOPLEPOSTCODE")).append(",");
                fddbr.append(StringUtil.getValue(piMap, "POWLEGALNAME")).append(",");
                dlrxm.append(StringUtil.getValue(piMap, "POWAGENTNAME")).append(",");
                lxdh1.append(StringUtil.getValue(piMap, "POWERPEOPLEMOBILE")).append(",");
                lxdh2.append(StringUtil.getValue(piMap, "POWAGENTTEL")).append(",");

                Map<String, Object> sqlInfoMap = new HashMap<>();
                sqlInfoMap.put("sqrxm", StringUtil.getValue(piMap, "POWERPEOPLENAME"));
                if (powerpeopleidtype.equals("SF")) {
                    sqlInfoMap.put("sqrzjlx", "IDCard");
                } else if (powerpeopleidtype.equals("HZ")) {
                    sqlInfoMap.put("sqrzjlx", "Passport");
                } else if (powerpeopleidtype.equals("GATX")) {
                    sqlInfoMap.put("sqrzjlx", "HMPass");
                } else {
                    sqlInfoMap.put("sqrzjlx", "Other");
                }
                sqlInfoMap.put("sqrzjhm", StringUtil.getValue(piMap, "POWERPEOPLEIDNUM"));
                sqlInfoMap.put("sqrsjhm", StringUtil.getValue(piMap, "POWERPEOPLEMOBILE"));
                sqrInfoList.add(sqlInfoMap);
            }
            returnMap.put("sqrInfoList", sqrInfoList);
            returnMap.put("qlrxm", bdcSpbConfig.bdcStringOutFormate(qlrxm));
            returnMap.put("sfzjzl", bdcSpbConfig.bdcStringOutFormate(sfzjzl));
            returnMap.put("zjhm", bdcSpbConfig.bdcStringOutFormate(zjhm));
            returnMap.put("txdz", bdcSpbConfig.bdcStringOutFormate(txdz));
            returnMap.put("yb", bdcSpbConfig.bdcStringOutFormate(yb));
            returnMap.put("fddbr", bdcSpbConfig.bdcStringOutFormate(fddbr));
            returnMap.put("dlrxm", bdcSpbConfig.bdcStringOutFormate(dlrxm));
            returnMap.put("lxdh1", bdcSpbConfig.bdcStringOutFormate(lxdh1));
            returnMap.put("lxdh2", bdcSpbConfig.bdcStringOutFormate(lxdh2));
        }

        initYhdjsqrFieldForm(returnMap, busInfo, execution);

        /* 义务人信息JSON */
        String qsJson = StringUtil.getValue(map, "POWERSOURCEINFO_JSON");
        if (StringUtils.isNotEmpty(qsJson)) {
            StringBuffer ywrxm = new StringBuffer();
            StringBuffer sfzjzl1 = new StringBuffer();
            StringBuffer zjhm1 = new StringBuffer();
            StringBuffer fddbr1 = new StringBuffer();
            StringBuffer lxdh3 = new StringBuffer();
            StringBuffer dlrxm1 = new StringBuffer();
            StringBuffer lxdh4 = new StringBuffer();
            /* 原不动产权证书号 */
            StringBuffer ybdcqzsh = new StringBuffer();
            StringBuffer dljgmc1 = new StringBuffer();
            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "POWERSOURCE_QLRMC")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJHM")).append(",");
                fddbr1.append(StringUtil.getValue(qsMap, "POWERSOURCE_FDDBR")).append(",");
                lxdh3.append(StringUtil.getValue(qsMap, "POWERSOURCE_FDDBR_TEL")).append(",");
                dlrxm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLR")).append(",");
                lxdh4.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLR_TEL")).append(",");
                ybdcqzsh.append(StringUtil.getValue(qsMap, "POWERSOURCE_QSWH")).append(",");
                dljgmc1.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLJGMC")).append(",");
            }
            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
            returnMap.put("fddbr1", bdcSpbConfig.bdcStringOutFormate(fddbr1));
            returnMap.put("lxdh3", bdcSpbConfig.bdcStringOutFormate(lxdh3));
            returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("lxdh4", bdcSpbConfig.bdcStringOutFormate(lxdh4));
            returnMap.put("ybdcqzsh", bdcSpbConfig.bdcStringOutFormate(ybdcqzsh));
            returnMap.put("dljgmc1", bdcSpbConfig.bdcStringOutFormate(dljgmc1));
        }
        /* 抵押权人信息 */
        String DYQRXX_NAME = StringUtil.getValue(busInfo, "DYQRXX_NAME");
        returnMap.put("dyqrxm", StringUtil.getValue(busInfo, "DYQRXX_NAME"));
        if (StringUtils.isNotEmpty(DYQRXX_NAME)) {
            Map<String, Object> bankMap = dao.getByJdbc("T_BDCQLC_BANK", new String[] { "BANK_NAME" },
                    new Object[] { DYQRXX_NAME });
            if (bankMap != null) {
                returnMap.put("yhmc", StringUtil.getValue(bankMap, "BANK_NAME"));
                returnMap.put("yhdm", StringUtil.getValue(bankMap, "BANK_CODE"));
                returnMap.put("yhzzlx", "SOCNO");
                returnMap.put("yhlxr", StringUtil.getValue(bankMap, "BANK_CONTECT_NAME"));
                returnMap.put("yhlxdh", StringUtil.getValue(bankMap, "BANK_CONTECT_PHONE"));
                returnMap.put("yhlxzjhm", StringUtil.getValue(bankMap, "BANK_CONTECT_CARD"));
                returnMap.put("yhlxzjlx", "IDCard");
                returnMap.put("yhfr", StringUtil.getValue(bankMap, "BANK_LEGAL_NAME"));
                returnMap.put("yhfrsjh", StringUtil.getValue(bankMap, "BANK_LEGAL_PHONE"));
                returnMap.put("yhfrzjhm", StringUtil.getValue(bankMap, "BANK_LEGAL_CARD"));
                returnMap.put("yhfrzjlx", "IDCard");
                returnMap.put("yhlxdz", StringUtil.getValue(bankMap, "BANK_ADDRESS"));
            }
        }
        returnMap.put("sfzjzl2", StringUtil.getValue(busInfo, "DYQRXX_IDTYPE"));
        returnMap.put("zjhm2", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
        returnMap.put("dyrxm", StringUtil.getValue(map, "DY_DYR"));
        returnMap.put("sfzjzl3", StringUtil.getValue(map, "DY_DYRZJLX1"));
        returnMap.put("zjhm3", StringUtil.getValue(map, "DY_DYRZJHM1"));

        /* 债务起止时间 */
        /* 债务履行期限 */
        String DY_QLQSSJ = StringUtil.getValue(map, "DY_QLQSSJ");
        String DY_QLJSSJ = StringUtil.getValue(map, "DY_QLJSSJ");
        if (!DY_QLQSSJ.equals("") && !DY_QLJSSJ.equals("")) {
            String zwBegin = bdcSpbConfig.bdcDateFormat(DY_QLQSSJ, "yyyy-MM-dd", "yyyy年MM月dd日");
            String zwEnd = bdcSpbConfig.bdcDateFormat(DY_QLJSSJ, "yyyy-MM-dd", "yyyy年MM月dd日");
            returnMap.put("zwqssj", zwBegin);
            returnMap.put("zwjssj", zwEnd);
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        returnMap.put("zzqhtdkqx", StringUtil.getValue(map, "DY_ZZQHTDKQX"));
        /* 被担保债券数额 */
        returnMap.put("bdbzqse", StringUtil.getValue(map, "DY_DBSE"));

        /* 在建建筑物抵押范围 */
        returnMap.put("zjjzwdyfw", "");
        returnMap.put("bdcdyh", StringUtil.getValue(map, "ESTATE_NUM"));
        // 字典qlxz
        returnMap.put("qlxz", dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(map, "ZD_QLXZ"), "100"));
        returnMap.put("bdclx", "土地、房产");
        /* 问询记录 */
        bdcSpbConfig.fillWxjlForm(returnMap, busInfo);
    }

    /**
     * 描述 设置抵押权首次登记转本业务数据
     * 
     * @author Allin Lin
     * @created 2020年9月24日 下午5:28:41
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    public void setDyqscdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        returnMap.put("clscsj", DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日"));

        //回填业务字段
        String DYQDJ_DYFS =  StringUtil.getValue(busInfo, "DYQDJ_DYFS");
        if("1".equals(DYQDJ_DYFS)){//一般抵押
            returnMap.put("dyfs1", "☑");
            returnMap.put("zgzqe", StringUtil.getValue(busInfo, "DYQDJ_BDBZZQSE"));
        }
        if("2".equals(DYQDJ_DYFS)){//最高额抵押
            returnMap.put("dyfs2", "☑");
            returnMap.put("zgzqe", StringUtil.getValue(busInfo, "DYQDJ_ZGZQE"));
        }
        String DYQDJ_JZDY =  StringUtil.getValue(busInfo, "DYQDJ_JZDY");
        if("1".equals(DYQDJ_JZDY)){//是
            returnMap.put("jzdy1", "☑");
        }
        if("0".equals(DYQDJ_JZDY)){//否
            returnMap.put("jzdy2", "☑");
        }
        returnMap.put("zglxqx", StringUtil.getValue(busInfo, "DYQDJ_ZWQSSJ")
                +"至"+StringUtil.getValue(busInfo, "DYQDJ_ZWJSSJ"));
        returnMap.put("dbfw", StringUtil.getValue(busInfo, "DYQDJ_DBFW"));
        returnMap.put("dyfw", StringUtil.getValue(busInfo, "DYQDJ_DYFW"));
        // 回填银行申请表-抵押人信息
        initYhDjdyrFieldForm(returnMap, busInfo, execution);
        /* 抵押权人信息 */
        String DYQRXX_NAME = StringUtil.getValue(busInfo, "DYQRXX_NAME");
        returnMap.put("dyqrxm", StringUtil.getValue(busInfo, "DYQRXX_NAME"));
        if (StringUtils.isNotEmpty(DYQRXX_NAME)) {
            /*
             * Map<String, Object> bankMap = dao.getByJdbc("T_BDCQLC_BANK", new
             * String[] { "BANK_NAME" }, new Object[] { DYQRXX_NAME }); if
             * (bankMap != null) { returnMap.put("yhmc",
             * StringUtil.getValue(bankMap, "BANK_NAME")); returnMap.put("yhdm",
             * StringUtil.getValue(bankMap, "BANK_CODE"));
             * returnMap.put("yhzzlx", "SOCNO"); returnMap.put("yhlxr",
             * StringUtil.getValue(bankMap, "BANK_CONTECT_NAME"));
             * returnMap.put("yhlxdh", StringUtil.getValue(bankMap,
             * "BANK_CONTECT_PHONE")); returnMap.put("yhlxzjhm",
             * StringUtil.getValue(bankMap, "BANK_CONTECT_CARD"));
             * returnMap.put("yhlxzjlx", "IDCard"); returnMap.put("yhfr",
             * StringUtil.getValue(bankMap, "BANK_LEGAL_NAME"));
             * returnMap.put("yhfrsjh", StringUtil.getValue(bankMap,
             * "BANK_LEGAL_PHONE")); returnMap.put("yhfrzjhm",
             * StringUtil.getValue(bankMap, "BANK_LEGAL_CARD"));
             * returnMap.put("yhfrzjlx", "IDCard"); returnMap.put("yhlxdz",
             * StringUtil.getValue(bankMap, "BANK_ADDRESS")); }
             */
            returnMap.put("yhmc", DYQRXX_NAME);
            returnMap.put("yhdm", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
            returnMap.put("yhzzlx", zjlxFormmat(StringUtil.getValue(busInfo, "DYQRXX_IDTYPE")));
            returnMap.put("yhlxr", StringUtil.getValue(busInfo, "DYQRXX_AGENT_NAME"));
            returnMap.put("yhlxdh", StringUtil.getValue(busInfo, "DYQRXX_AGENT_PHONE"));
            returnMap.put("yhlxzjhm", StringUtil.getValue(busInfo, "DYQRXX_AGENT_IDNO"));
            returnMap.put("yhlxzjlx", zjlxFormmat(StringUtil.getValue(busInfo, "DYQRXX_AGENT_IDTYPE")));
            returnMap.put("yhfr", StringUtil.getValue(busInfo, "DYQRXX_LEGAL_NAME"));
            // returnMap.put("yhfrsjh", StringUtil.getValue(busInfo,
            // "BANK_LEGAL_PHONE"));
            returnMap.put("yhfrzjhm", StringUtil.getValue(busInfo, "DYQRXX_LEGAL_IDNO"));
            returnMap.put("yhfrzjlx", zjlxFormmat(StringUtil.getValue(busInfo, "DYQRXX_LEGAL_IDTYPE")));
            returnMap.put("yhlxdz", StringUtil.getValue(busInfo, "DYQRXX_ADDR"));

        }
        returnMap.put("sfzjzl2", StringUtil.getValue(busInfo, "DYQRXX_IDTYPE"));
        returnMap.put("zjhm2", StringUtil.getValue(busInfo, "DYQRXX_IDNO"));
        /* 问询记录 */
        bdcSpbConfig.fillWxjlForm(returnMap, busInfo);
    }

    /**
     * 描述 证件种类格式化
     * 
     * @author Allin Lin
     * @created 2020年12月1日 下午2:14:33
     * @param zjlx
     * @return
     */
    private String zjlxFormmat(String zjlx) {
        String fmtzjlx = "";
        if (zjlx.equals("身份证")) {
            fmtzjlx = "IDCard";
        } else if (zjlx.equals("港澳居民来往内地通行证")) {
            fmtzjlx = "HMPass";
        } else if (zjlx.equals("护照")) {
            fmtzjlx = "Passport";
        } else if (zjlx.equals("台湾居民来往内地通行证")) {
            fmtzjlx = "MTP";
        } else if (zjlx.equals("营业执照") || zjlx.equals("统一社会信用代码")) {
            fmtzjlx = "SOCNO";
        } else if (zjlx.equals("组织机构代码")) {
            fmtzjlx = "ORANO";
        } else {
            fmtzjlx = "Other";
        }
        return fmtzjlx;
    }

    /**
     * 描述 回填银行申请表-抵押人字段信息
     * 
     * @author Allin Lin
     * @created 2020年9月24日 下午5:49:38
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    public void initYhDjdyrFieldForm(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        /* 根据单元号调取不动产预告档案接口获取抵押人信息 */
        String bdcdyh = StringUtil.getValue(busInfo, "BDCDYH");
        String takecardType = StringUtil.getValue(busInfo, "TAKECARD_TYPE");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("bdcdyh", bdcdyh);
        List<Map<String, Object>> ygList = new ArrayList<Map<String, Object>>();
        AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(variables, "announceUrl");
        String jsonString = ajaxJson.getJsonString();
        if (StringUtils.isNotEmpty(jsonString)) {
            ygList = JSON.parseObject(jsonString, List.class);
        }
        /* 根据单元号调取不动产档案接口获取坐落、共有方式、权利比例字段 */
        List<Map<String, Object>> daList = new ArrayList<Map<String, Object>>();
        AjaxJson ajaxJson1 = bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdaxxcxUrl");
        String jsonString1 = ajaxJson1.getJsonString();
        if (StringUtils.isNotEmpty(jsonString1)) {
            daList = JSON.parseObject(jsonString1, List.class);
        }
        // 取预告登记种类 -预售商品房抵押权预告登记的预告数据
        Map<String, Object> dyrMap = new HashMap<String, Object>();// 抵押人信息
        if (ygList != null) {
            for (Map<String, Object> map : ygList) {
                String ygdjzl = StringUtil.getString(map.get("YGDJZL")).trim();// 预告登记种类
                if (ygdjzl.indexOf("抵押权预告登记") > -1 || ygdjzl.indexOf("预售商品房抵押权预告登记") > -1) {
                    dyrMap = map;
                    break;
                }
            }
        }
        // 取不动产单元号-权属状态为现势的档案信息
        Map<String, Object> daMap = new HashMap<String, Object>();
        for (Map<String, Object> map : daList) {
            String qszt = StringUtil.getString(map.get("QSZT")).trim();// 权属状态
            if (qszt.indexOf("现势") > -1) {
                daMap = map;
                break;
            }
        }
        if (StringUtils.isNotEmpty(takecardType) && takecardType.equals("1")) {// 抵押权首次登记
            if (daMap != null) {
                String qlrmc = StringUtil.getValue(daMap, "QLRMC");// 名称
                String[] qlrmcList = qlrmc.split(",");
                String zjlx = StringUtil.getValue(daMap, "ZJLX");// 名称
                String[] zjlxList = zjlx.split(",");
                String zjhm = StringUtil.getValue(daMap, "ZJHM");// 名称
                String[] zjhmList = zjhm.split(",");
                if (qlrmcList != null && qlrmcList.length > 0) {
                    returnMap.put("sqrgyqklxr", qlrmcList[0]);
                    returnMap.put("sqrgyqklxdz", "");// 联系地址
                    returnMap.put("sqrgyqklxdh", "");// 联系电话
                    String GYFS = StringUtil.getString(daMap.get("GYFS"));
                    String qlbl = StringUtil.getValue(daMap, "QLBL");// 权利比例
                    String[] qlblList = qlbl.split(",");
                    if (GYFS.equals("单独所有") || (qlrmcList.length == 1)) {
                        returnMap.put("sqrgyqkxm1", qlrmcList[0]);
                        returnMap.put("sqrgyqkzjzl1", zjlxList[0]);
                        returnMap.put("sqrgyqkzjhm1", zjhmList[0]);
                        returnMap.put("sqrgyqkgj1", "中国");
                        returnMap.put("sqrgyqk1_1", "■");
                        returnMap.put("sqrgyqk1_2", "□");
                        returnMap.put("sqrgyqk1_3", "□");
                        returnMap.put("sqrgyqkfe1", "______");

                        returnMap.put("sqrgyqk2_1", "□");
                        returnMap.put("sqrgyqk2_2", "□");
                        returnMap.put("sqrgyqkfe2", "______");
                    } else if (qlrmcList.length > 1) {
                        returnMap.put("sqrgyqkxm1", qlrmcList[0]);
                        returnMap.put("sqrgyqkzjzl1", zjlxList[0]);
                        returnMap.put("sqrgyqkzjhm1", zjhmList[0]);
                        returnMap.put("sqrgyqkgj1", "中国");
                        returnMap.put("sqrgyqkxm2", qlrmcList[1]);
                        returnMap.put("sqrgyqkzjzl2", zjlxList[0]);
                        returnMap.put("sqrgyqkzjhm2", zjhmList[1]);
                        returnMap.put("sqrgyqkgj2", "中国");
                        if (GYFS.equals("共同共有")) {
                            returnMap.put("sqrgyqk1_1", "□");
                            returnMap.put("sqrgyqk1_2", "■");
                            returnMap.put("sqrgyqk1_3", "□");
                            returnMap.put("sqrgyqkfe1", "______");
                            returnMap.put("sqrgyqk2_1", "■");
                            returnMap.put("sqrgyqk2_2", "□");
                            returnMap.put("sqrgyqkfe2", "______");
                        } else if (GYFS.equals("按份共有")) {
                            returnMap.put("sqrgyqk1_1", "□");
                            returnMap.put("sqrgyqk1_2", "□");
                            returnMap.put("sqrgyqk1_3", "■");
                            returnMap.put("sqrgyqkfe1", qlblList[0]);
                            returnMap.put("sqrgyqk2_1", "□");
                            returnMap.put("sqrgyqk2_2", "■");
                            returnMap.put("sqrgyqkfe2", qlblList[1]);
                        }
                    }
                    /* 不动产情况 */
                    returnMap.put("zl", daMap.get("FDZL"));
                    returnMap.put("dybdcdjzmh", daMap.get("BDCQZH"));
                }
            }
        } else {// 抵押权首次登记转本
            // 取义务人信息
            if (dyrMap != null) {
                String ywr = StringUtil.getValue(dyrMap, "YWR");// 名称
                String[] ywrList = ywr.split(",");
                String zjlx = StringUtil.getValue(dyrMap, "YWRZJZL");// 证件种类
                String[] ywrzjzlList = zjlx.split(",");
                String zjh = StringUtil.getValue(dyrMap, "YWRZJH");// 证件号
                String[] ywrzjhList = zjh.split(",");
                if (ywrList != null && ywrList.length > 0) {
                    returnMap.put("sqrgyqklxr", ywrList[0]);
                    returnMap.put("sqrgyqklxdz", "");// 联系地址
                    returnMap.put("sqrgyqklxdh", "");// 联系电话
                    // 共有情况
                    if (daMap != null) {
                        String GYFS = StringUtil.getString(daMap.get("GYFS"));
                        String qlbl = StringUtil.getValue(daMap, "QLBL");// 权利比例
                        String[] qlblList = qlbl.split(",");
                        if (GYFS.equals("单独所有") || (ywrList.length == 1)) {
                            returnMap.put("sqrgyqkxm1", ywrList[0]);
                            returnMap.put("sqrgyqkzjzl1", ywrzjzlList[0]);
                            returnMap.put("sqrgyqkzjhm1", ywrzjhList[0]);
                            returnMap.put("sqrgyqkgj1", "中国");
                            returnMap.put("sqrgyqk1_1", "■");
                            returnMap.put("sqrgyqk1_2", "□");
                            returnMap.put("sqrgyqk1_3", "□");
                            returnMap.put("sqrgyqkfe1", "______");

                            returnMap.put("sqrgyqk2_1", "□");
                            returnMap.put("sqrgyqk2_2", "□");
                            returnMap.put("sqrgyqkfe2", "______");
                        } else if (ywrList.length > 1) {
                            returnMap.put("sqrgyqkxm1", ywrList[0]);
                            returnMap.put("sqrgyqkzjzl1", ywrzjzlList[0]);
                            returnMap.put("sqrgyqkzjhm1", ywrzjhList[0]);
                            returnMap.put("sqrgyqkgj1", "中国");
                            returnMap.put("sqrgyqkxm2", ywrList[1]);
                            returnMap.put("sqrgyqkzjzl2", ywrzjzlList[0]);
                            returnMap.put("sqrgyqkzjhm2", ywrzjhList[1]);
                            returnMap.put("sqrgyqkgj2", "中国");
                            if (GYFS.equals("共同共有")) {
                                returnMap.put("sqrgyqk1_1", "□");
                                returnMap.put("sqrgyqk1_2", "■");
                                returnMap.put("sqrgyqk1_3", "□");
                                returnMap.put("sqrgyqkfe1", "______");
                                returnMap.put("sqrgyqk2_1", "■");
                                returnMap.put("sqrgyqk2_2", "□");
                                returnMap.put("sqrgyqkfe2", "______");
                            } else if (GYFS.equals("按份共有")) {
                                returnMap.put("sqrgyqk1_1", "□");
                                returnMap.put("sqrgyqk1_2", "□");
                                returnMap.put("sqrgyqk1_3", "■");
                                returnMap.put("sqrgyqkfe1", qlblList[0]);
                                returnMap.put("sqrgyqk2_1", "□");
                                returnMap.put("sqrgyqk2_2", "■");
                                returnMap.put("sqrgyqkfe2", qlblList[1]);
                            }
                        }
                        /* 不动产情况 */
                        returnMap.put("zl", daMap.get("FDZL"));
                        returnMap.put("dybdcdjzmh", dyrMap.get("BDCDJZMH"));
                    }
                }
            }
        }
    }

    /**
     * 描述:回填银行登记申请人字段（抵押人）
     *
     * @author Madison You
     * @created 2020/8/18 17:20:00
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void initYhdjsqrFieldForm(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        /* 根据单元号获取分户信息 */
        String BDCDYH = StringUtil.getValue(busInfo, "BDCDYH");
        List<Map<String, Object>> list = findBdcfhData(BDCDYH);
        Map<String, Object> map = list.get(0);
        /* 权利人信息JSON */
        String piJson = StringUtil.getValue(map, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            String GYTD_GYFS = StringUtil.getValue(map, "GYTD_GYFS");
            if (piList != null && piList.size() > 0) {
                Map<String, Object> piMapLxr = piList.get(0);
                returnMap.put("sqrgyqklxr", StringUtil.getValue(piMapLxr, "POWERPEOPLENAME"));
                returnMap.put("sqrgyqklxdz", StringUtil.getValue(piMapLxr, "POWERPEOPLEADDR"));
                returnMap.put("sqrgyqklxdh", StringUtil.getValue(piMapLxr, "POWERPEOPLEMOBILE"));

                if (GYTD_GYFS.equals("0")) {
                    Map<String, Object> piMap = piList.get(0);
                    returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap, "POWERPEOPLENAME"));
                    /* 证件类型代码转换 */
                    String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                    String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                    returnMap.put("sqrgyqkzjzl1", documentType);
                    returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap, "POWERPEOPLEIDNUM"));
                    returnMap.put("sqrgyqkgj1", "中国");
                    returnMap.put("sqrgyqk1_1", "■");
                    returnMap.put("sqrgyqk1_2", "□");
                    returnMap.put("sqrgyqk1_3", "□");
                    returnMap.put("sqrgyqkfe1", "______");

                    returnMap.put("sqrgyqk2_1", "□");
                    returnMap.put("sqrgyqk2_2", "□");
                    returnMap.put("sqrgyqkfe2", "______");
                } else if (GYTD_GYFS.equals("1")) {
                    Map<String, Object> piMap1 = piList.get(0);
                    returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap1, "POWERPEOPLENAME"));
                    /* 证件类型代码转换 */
                    String powerpeopleidtype1 = StringUtil.getValue(piMap1, "POWERPEOPLEIDTYPE");
                    String documentType1 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype1);
                    returnMap.put("sqrgyqkzjzl1", documentType1);
                    returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap1, "POWERPEOPLEIDNUM"));
                    returnMap.put("sqrgyqkgj1", "中国");
                    returnMap.put("sqrgyqk1_1", "□");
                    returnMap.put("sqrgyqk1_2", "■");
                    returnMap.put("sqrgyqk1_3", "□");
                    returnMap.put("sqrgyqkfe1", "______");
                    if (piList.size() > 1) {
                        Map<String, Object> piMap2 = piList.get(1);
                        returnMap.put("sqrgyqkxm2", StringUtil.getValue(piMap2, "POWERPEOPLENAME"));
                        /* 证件类型代码转换 */
                        String powerpeopleidtype2 = StringUtil.getValue(piMap2, "POWERPEOPLEIDTYPE");
                        String documentType2 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype2);
                        returnMap.put("sqrgyqkzjzl2", documentType2);
                        returnMap.put("sqrgyqkzjhm2", StringUtil.getValue(piMap2, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj2", "中国");
                        returnMap.put("sqrgyqk2_1", "■");
                        returnMap.put("sqrgyqk2_2", "□");
                        returnMap.put("sqrgyqkfe2", "______");
                    }
                } else if (GYTD_GYFS.equals("2")) {
                    Map<String, Object> piMap1 = piList.get(0);
                    returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap1, "POWERPEOPLENAME"));
                    /* 证件类型代码转换 */
                    String powerpeopleidtype1 = StringUtil.getValue(piMap1, "POWERPEOPLEIDTYPE");
                    String documentType1 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype1);
                    returnMap.put("sqrgyqkzjzl1", documentType1);
                    returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap1, "POWERPEOPLEIDNUM"));
                    returnMap.put("sqrgyqkgj1", "中国");
                    returnMap.put("sqrgyqk1_1", "□");
                    returnMap.put("sqrgyqk1_2", "□");
                    returnMap.put("sqrgyqk1_3", "■");
                    returnMap.put("sqrgyqkfe1", StringUtil.getValue(piMap1, "POWERPEOPLEPRO"));
                    if (piList.size() > 1) {
                        Map<String, Object> piMap2 = piList.get(1);
                        returnMap.put("sqrgyqkxm2", StringUtil.getValue(piMap2, "POWERPEOPLENAME"));
                        /* 证件类型代码转换 */
                        String powerpeopleidtype2 = StringUtil.getValue(piMap2, "POWERPEOPLEIDTYPE");
                        String documentType2 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype2);
                        returnMap.put("sqrgyqkzjzl2", documentType2);
                        returnMap.put("sqrgyqkzjhm2", StringUtil.getValue(piMap2, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj2", "中国");
                        returnMap.put("sqrgyqk2_1", "□");
                        returnMap.put("sqrgyqk2_2", "■");
                        returnMap.put("sqrgyqkfe2", StringUtil.getValue(piMap2, "POWERPEOPLEPRO"));
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述 （抵押权首次登记）银行申请表签章
     * 
     * @author Allin Lin
     * @created 2020年8月20日 上午12:25:43
     * @param flowVars
     * @param returnMap
     * @throws Exception
     */
    public void bankSign(Map<String, Object> flowVars, Map<String, Object> returnMap) throws Exception {
        // 1.取上传PDF文件路径
        Map<String, Object> materSignInfo = this.getByJdbc("T_BDCQLC_MATERSIGNINFO",
                new String[] { "EXE_ID", "MATER_NAME" },
                new Object[] { flowVars.get("EFLOW_EXEID"), BdcQlcCreateSignFlowService.BANKSQS_MATERNAME });
        flowVars.put("SIGN_FLAG", true);// 设置签章标志位
        // 2.创建外部用户（经办人）
        String jbrName = StringUtil.getString(flowVars.get("JBR_NAME"));
        String jbrZjhm = StringUtil.getString(flowVars.get("JBR_ZJHM"));
        String jbrZjlx = "IDCard";
        String jbrMobile = StringUtil.getString(flowVars.get("JBR_MOBILE"));
        String jbrEmail = StringUtil.getString(flowVars.get("JBR_EMAIL"));
        Map<String, Object> user = new HashMap<>();
        user.put("sqrxm", jbrName);
        user.put("sqrzjhm", jbrZjhm);
        user.put("sqrzjlx", jbrZjlx);
        user.put("contactsEmail", jbrEmail);
        user.put("sqrsjhm", jbrMobile);
        Map<String, Object> exUser = bdcQlcMaterGenAndSignService.creExUser(user);
        if (!(boolean) exUser.get("USER_CREATEFLAG")) {
            flowVars.put("SIGN_FLAG", false);
            flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
        } else {
            // 3.创建机构（银行&经办人信息）
            Map<String, Object> bank = bdcQlcMaterGenAndSignService.creBank(returnMap);
            if (!(boolean) bank.get(("INS_CREATEFLAG"))) {
                flowVars.put("SIGN_FLAG", false);
                flowVars.put("SIGN_MSG", bank.get("SIGN_MSG"));
            } else {
                // 5.文件直传
                Map<String, Object> fileResult = bdcQlcMaterGenAndSignService.getFileKey(
                        materSignInfo.get("MATER_PDFPATH").toString(), flowVars.get("EFLOW_EXEID").toString(),
                        BdcQlcCreateSignFlowService.BANKSQS_MATERNAME);
                if (!(boolean) fileResult.get("SIGN_FLAG")) {
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", fileResult.get("SIGN_MSG"));
                } else {
                    // 6.签署流程
                    Map<String, Object> result = bdcQlcCreateSignFlowService
                            .createSignFlowOfDyqscdjBankSqb(flowVars, returnMap);
                    if (!(boolean) result.get("SIGN_FLAG")) {
                        flowVars.put("SIGN_FLAG", false);
                        flowVars.put("SIGN_MSG", result.get("SIGN_MSG"));
                    }
                }
            }
        }
    }
}