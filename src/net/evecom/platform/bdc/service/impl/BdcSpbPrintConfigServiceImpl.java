/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcSpbPrintConfigDao;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述:不动产全流程审批表打印基本配置
 *
 * @author Madison You
 * @created 2020/04/26 15:27
 */
@Service("bdcSpbPrintConfigService")
public class BdcSpbPrintConfigServiceImpl extends BaseServiceImpl implements BdcSpbPrintConfigService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/27 8:38:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcSpbPrintConfigServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/26 17:10:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigDao bdcSpbPrintConfigDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/8/17 14:48:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/26 17:09:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return bdcSpbPrintConfigDao;
    }


    /**
     * 描述:不动产全流程格式化字符串
     *
     * @author Madison You
     * @created 2020/4/26 17:14:00
     * @param
     * @return
     */
    @Override
    public String bdcStringOutFormate(StringBuffer str) {
        if (str != null && str.length() > 0) {
            if (StringUtils.isNotEmpty(str.toString().replaceAll("\\,", ""))) {
                return str.substring(0, str.length() - 1);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 描述:不动产全流程日期格式转换
     *
     * @author Madison You
     * @created 2020/4/26 17:15:00
     * @param
     * @return
     */
    @Override
    public String bdcDateFormat(String dateStr, String oldFormat, String newFormat) {
        if (StringUtils.isNotEmpty(dateStr)) {
            try{
                Date parseDate = new SimpleDateFormat(oldFormat).parse(dateStr);
                return new SimpleDateFormat(newFormat).format(parseDate);
            } catch (ParseException e) {
                log.error("审批表日期转换错误" + e.getMessage());
            }
        }
        return "";
    }

    /**
     * 描述:不动产全流程获取子表业务数据
     *
     * @author Madison You
     * @created 2020/4/26 17:15:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> bdcGetChildTableBusInfo(String exeId, String childTableName) {
        Map<String, Object> exeMap = bdcSpbPrintConfigDao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"}, new Object[]{exeId});
        if (exeMap != null) {
            String busRecordId = exeMap.get("BUS_RECORDID").toString();
            Map<String, Object> busMap = bdcSpbPrintConfigDao.getByJdbc(childTableName,
                    new String[]{"BDC_SUB_YWID"}, new Object[]{busRecordId});
            return busMap;
        }
        return null;
    }

    /**
     * 描述:不动产全流程获取登簿环节受理信息
     *
     * @author Madison You
     * @created 2020/4/26 17:15:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> bdcGetDbNodeInfo(String exeId) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> map = null;
        sql.append(" select * from jbpm6_task ");
        sql.append(" where exe_id = ? and task_nodename = '登簿' and is_real_handled = 1 ");
        sql.append(" order by create_time desc ");
        List<Map<String, Object>> list = bdcSpbPrintConfigDao.findBySql(sql.toString(),
                new Object[]{exeId}, null);
        if (list != null && list.size() > 0) {
            map = list.get(0);
        }
        return map;
    }

    /**
     * 描述:不动产全流程根据环节名称获取某一环节受理信息
     *
     * @author Madison You
     * @created 2020/4/26 17:15:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> bdcGetNodeInfo(String exeId, String nodeName) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> map = null;
        sql.append(" select * from jbpm6_task ");
        sql.append(" where exe_id = ? and task_nodename = ? and is_real_handled = 1 ");
        sql.append(" order by create_time desc ");
        List<Map<String, Object>> list = bdcSpbPrintConfigDao.findBySql(sql.toString(),
                new Object[]{exeId,nodeName}, null);
        if (list != null && list.size() > 0) {
            map = list.get(0);
        }
        return map;
    }

    /**
     * 描述:初始化审批表字段
     *
     * @author Madison You
     * @created 2020/4/27 8:43:00
     * @param
     * @return
     */
    @Override
    public void bdcInitSpbVariables(Map<String, Object> returnMap) {
        returnMap.put("qllx", "");
        returnMap.put("djlx", "");
        returnMap.put("sjr", "");
        /*权利人信息*/
        returnMap.put("qlrxm", "");
        returnMap.put("sfzjzl", "");
        returnMap.put("zjhm", "");
        returnMap.put("txdz", "");
        returnMap.put("yb", "");
        returnMap.put("fddbr", "");
        returnMap.put("lxdh1", "");
        returnMap.put("dlrxm", "");
        returnMap.put("lxdh2", "");
        returnMap.put("dljgmc", "");
        /*义务人信息*/
        returnMap.put("ywrxm", "");
        returnMap.put("sfzjzl1", "");
        returnMap.put("zjhm1", "");
        returnMap.put("txdz1", "");
        returnMap.put("yb1", "");
        returnMap.put("fddbr1", "");
        returnMap.put("lxdh3", "");
        returnMap.put("dlrxm1", "");
        returnMap.put("lxdh4", "");
        returnMap.put("dljgmc1", "");
        /*抵押人信息*/
        returnMap.put("dyqrxm", "");
        returnMap.put("sfzjzl2", "");
        returnMap.put("zjhm2", "");
        returnMap.put("txdz2", "");
        returnMap.put("yb2", "");
        returnMap.put("dyrxm", "");
        returnMap.put("sfzjzl3", "");
        returnMap.put("zjhm3", "");
        returnMap.put("txdz3", "");
        returnMap.put("yb3", "");
        returnMap.put("zzqhtdkqx", "");
        returnMap.put("dybdcdjzmh", "");
        returnMap.put("yhlxr", "");
        returnMap.put("yhfr", "");
        returnMap.put("yhlxdz", "");
        returnMap.put("yhlxdh", "");
        /*不动产情况*/
        returnMap.put("zl", "");
        returnMap.put("bdcdyh", "");
        returnMap.put("qlxz", "");
        returnMap.put("bdclx", "");
        returnMap.put("mj", "");
        returnMap.put("yt", "");
        returnMap.put("ybdcqzsh", "");
        returnMap.put("gzwlx", "");
        returnMap.put("lz", "");
        returnMap.put("bdbzqse", "");
        returnMap.put("zwlxqx", "");
        returnMap.put("zwqssj", "");
        returnMap.put("zwjssj", "");
        /*地役权情况*/
        returnMap.put("xydzl", "");
        returnMap.put("xydbdcdyh", "");
        /*登记原因及证明*/
        returnMap.put("djyy", "");
        returnMap.put("djyyzmwj", "");
        /*不动产登记审批情况*/
        returnMap.put("cs", "");
        returnMap.put("scr", "");
        returnMap.put("csrq", "");
        returnMap.put("sh", "");
        returnMap.put("scr1", "");
        returnMap.put("fhrq", "");
        returnMap.put("hd", "");
        returnMap.put("fzr", "");
        returnMap.put("hdsj", "");
        returnMap.put("sp", "");
        returnMap.put("spr", "");
        returnMap.put("sprq", "");
        /*备注情况*/
        returnMap.put("bz", "");
        /*材料生成时间*/
        returnMap.put("clscsj", "");
        /*银行登记申请人*/
        returnMap.put("sqrgyqkxm1", "");
        returnMap.put("sqrgyqkzjzl1", "");
        returnMap.put("sqrgyqkzjhm1", "");
        returnMap.put("sqrgyqkgj1", "");
        returnMap.put("sqrgyqkxm2", "");
        returnMap.put("sqrgyqkzjzl2", "");
        returnMap.put("sqrgyqkzjhm2", "");
        returnMap.put("sqrgyqkgj2", "");
        returnMap.put("sqrgyqk1_1", "□");
        returnMap.put("sqrgyqk1_2", "□");
        returnMap.put("sqrgyqk1_3", "□");
        returnMap.put("sqrgyqkfe1", "____");
        returnMap.put("sqrgyqk2_1", "□");
        returnMap.put("sqrgyqk2_2", "□");
        returnMap.put("sqrgyqkfe2", "____");
        returnMap.put("sqrgyqklxr", "");
        returnMap.put("sqrgyqklxr", "");
        returnMap.put("sqrgyqklxdz", "");
        returnMap.put("sqrgyqklxdh", "");
        
        returnMap.put("BDC_TCCNS", "");
        
        returnMap.put("FBCZ1", "□");
        returnMap.put("FBCZ2", "□");
        
        
        returnMap.put("dyfs1", "□");
        returnMap.put("dyfs2", "□");
        returnMap.put("jzdy1", "□");
        returnMap.put("jzdy2", "□");
        returnMap.put("zgzqe", "");
        returnMap.put("zglxqx", "");
        returnMap.put("dbfw", "");
        returnMap.put("dyfw", "");
        //存量房税费联办在线签章需要补充的信息
        //纳税人承诺信息
        /*returnMap.put("nsrxm", "");
        returnMap.put("nsrzjlx", "");
        returnMap.put("nsrsfh", "");
        returnMap.put("nsrpoxm", "");
        returnMap.put("nsrposfh", "");
        returnMap.put("nsrChildList","");
        returnMap.put("nsrfcdz", "");
        returnMap.put("nsrjtqk1", "□");
        returnMap.put("nsrjtqk2", "□");
        returnMap.put("nsrjtqk3", "□");
        returnMap.put("nsrjtqk4", "□");
        returnMap.put("sqsj", "");*/
        
        returnMap.put("ISPOWTRANSFER_1", "□");
        returnMap.put("ISPOWTRANSFER_2", "□");
        returnMap.put("POW_NUM", "");
        returnMap.put("ISWATERTRANSFER_1", "□");
        returnMap.put("ISWATERTRANSFER_2", "□");
        returnMap.put("WATER_NUM", "");
        returnMap.put("ISGASTRANSFER_1", "□");
        returnMap.put("ISGASTRANSFER_2", "□");
        returnMap.put("GAS_NUM", "");
        returnMap.put("ISSVATRANSFER_1", "□");
        returnMap.put("ISSVATRANSFER_2", "□");
        returnMap.put("SVA_NUM", "");
        
        returnMap.put("BUYLIST", "");
        returnMap.put("SELLLIST", "");
        
        //不动产权属转移涉税补充信息填写确认单
        returnMap.put("isagree1", "□");
        returnMap.put("isagree2", "□");
        returnMap.put("isknow", "□");
        returnMap.put("gfzm1", "□");
        returnMap.put("gfzm2", "□");
        returnMap.put("gfzm3", "□");
        returnMap.put("esfjy1", "□");
        returnMap.put("esfjy2", "□");
        returnMap.put("grsdszsfs1", "□");
        returnMap.put("grsdszsfs2", "□");
        returnMap.put("grsdsmz1", "□");
        returnMap.put("grsdsmz2", "□");
        returnMap.put("isswfs", "□");
        //存量房评估补充信息
        returnMap.put("clffcdz", "");
        returnMap.put("clfzxqk1", "□");
        returnMap.put("clfzxqk2", "□");
        returnMap.put("clfzxqk3", "□");
        returnMap.put("clfzxqk4", "□");
        returnMap.put("clfcgqk1", "□");
        returnMap.put("clfcgqk2", "□");
        returnMap.put("clfcgqk3", "□");
        returnMap.put("clfcgqk4", "□");
        returnMap.put("clfcgqk5", "□");
        returnMap.put("clfdtqk1", "□");
        returnMap.put("clfdtqk2", "□");
        returnMap.put("clfyjqk1", "□");
        returnMap.put("clfyjqk2", "□");
        returnMap.put("clfljzt1", "□");
        returnMap.put("clfljzt2", "□");
        returnMap.put("clfljzt3", "□");
        returnMap.put("clfljzt4", "□");
        //家庭生活唯一用房承诺书
        returnMap.put("wyyffwdz", "");
        returnMap.put("wyyfqlrxm", "");
        returnMap.put("wyyfhyzk", "");
        returnMap.put("wyyfqlrsfh", "");
        returnMap.put("wyyfpoxm", "");
        returnMap.put("wyyfposfh", "");
        returnMap.put("wyyfzzcn", "");
        returnMap.put("wyyflxdh", "");
        //个人无偿赠与不动产登记表
       /* returnMap.put("zyrxm", "");
        returnMap.put("zyrsfh", "");
        returnMap.put("zyrdz", "");
        returnMap.put("zyrlxdh", "");
        returnMap.put("zyrbdcdz", "");
        returnMap.put("zyrmj", "");
        returnMap.put("szrxm", "");
        returnMap.put("szrsfh", "");
        returnMap.put("szrzz", "");
        returnMap.put("szrlxdh", "");
        returnMap.put("szryzrgx", "");*/
        //实名办税授权委托书
        /*returnMap.put("swdjindex", "");
        returnMap.put("swdjxm", "");
        returnMap.put("swdjzjlx", "");
        returnMap.put("swdjzjhm", "");
        returnMap.put("swdjsjhm", "");
        returnMap.put("swdjgzbm", "");
        returnMap.put("swdjzw", "");
        returnMap.put("swdjsfwt", "");
        returnMap.put("swdjfxsq", "");*/

    }

    /**
     * 描述:生成审批表
     *
     * @author Madison You
     * @created 2020/4/27 11:00:00
     * @param
     * @return
     */
    @Override
    public void bdcCreateSpbConfig(Map<String, Object> returnMap) {
        String tableType = StringUtil.getValue(returnMap, "tableType");
        if (StringUtils.isNotEmpty(tableType) && tableType.equals("1")) {
            /*打印申请表*/
            /*二维码*/
            String bdcdyh = StringUtil.getValue(returnMap, "bdcdyh");
            createQrCode(returnMap, bdcdyh);
            if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
                WordRedrawUtil.processNormalTable07(
                        "attachFiles//readtemplate//files//BDCQLCSQB.docx", returnMap);
            }
        } else {
            /*打印审批表*/
            /*二维码*/
            String sbh = StringUtil.getValue(returnMap, "xmsqbh");
            createQrCode(returnMap, sbh);
            if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
                String docPath = returnMap.get("TemplatePath").toString().split("\\.")[0];
                WordRedrawUtil.processNormalTable07(
                        "attachFiles//readtemplate//files//" + docPath + ".docx", returnMap);
            }
        }
    }



    /**
     * 描述:获取业务数据
     *
     * @author Madison You
     * @created 2020/4/27 11:02:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> bdcGetBusInfo(Map<String, Object> flowAllObj) {
        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }
        return busInfo;
    }

    /**
     * 描述:获取某一环节的的审核信息(审核人员，审核时间，审核意见)
     *
     * @author Madison You
     * @created 2020/4/28 8:42:00
     * @param
     * @return
     */
    @Override
    public void getTaskInfo(Map<String, Object> returnMap , String nodeName) {
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            Map<String, Object> dbNodeInfo = bdcGetNodeInfo(sbh,nodeName);
            if (dbNodeInfo != null) {
                returnMap.put("hd", StringUtil.getValue(dbNodeInfo, "HANDLE_OPINION"));
                returnMap.put("fzr", StringUtil.getValue(dbNodeInfo, "ASSIGNER_NAME"));
                returnMap.put("hdsj", bdcDateFormat(StringUtil.getValue(dbNodeInfo, "END_TIME"),
                        "yyyy-MM-dd hh:mm:ss","yyyy年MM月dd日"));
            }
        }
    }

    /**
     * 描述:获取所有收取的材料名称填入登记原因证明文件字段
     *
     * @author Madison You
     * @created 2020/6/2 9:28:00
     * @param
     * @return
     */
    @Override
    public String getMaterNameList(Map<String, Object> returnMap) {
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT DISTINCT C.MATER_NAME FROM JBPM6_EXECUTION A ");
            sql.append(" LEFT JOIN T_MSJW_SYSTEM_FILEATTACH B ON A.BUS_RECORDID = B.BUS_TABLERECORDID ");
            sql.append(" LEFT JOIN T_WSBS_APPLYMATER C ON B.ATTACH_KEY = C.MATER_CODE ");
            sql.append(" WHERE A.EXE_ID = ? AND B.FILE_ID IS NOT NULL ");
            List<Map<String, Object>> materNameList = bdcSpbPrintConfigDao.findBySql(sql.toString(),
                    new Object[]{sbh}, null);
            if (materNameList != null && !materNameList.isEmpty()) {
                int i = 0;
                StringBuffer materNameStr = new StringBuffer();
                for (Map<String, Object> materNameMap : materNameList) {
                    String materName = (String) materNameMap.get("MATER_NAME");
                    materNameStr.append(i+1).append("：").append(materName).append(";");
                    i++;
                }
                returnMap.put("djyyzmwj", materNameStr);
            }
        }
        return null;
    }

    /**
     * 描述:自动回填不动产登记审核意见
     *
     * @author Madison You
     * @created 2020/6/12 20:01:00
     * @param
     * @return
     */
    @Override
    public void getbdcDjshOpinion(Map<String, Object> busInfo , Map<String, Object> returnMap) {
        returnMap.put("sh", StringUtil.getValue(busInfo, "CS_OPINION_CONTENT"));
        returnMap.put("scr1", StringUtil.getValue(busInfo, "CS_OPINION_NAME"));
        returnMap.put("fhrq", bdcDateFormat(StringUtil.getValue(busInfo, "CS_OPINION_TIME"),
                "yyyy-MM-dd hh:mm:ss","yyyy年MM月dd日"));
        returnMap.put("hd", StringUtil.getValue(busInfo, "HZ_OPINION_CONTENT"));
        returnMap.put("fzr", StringUtil.getValue(busInfo, "HZ_OPINION_NAME"));
        returnMap.put("hdsj", bdcDateFormat(StringUtil.getValue(busInfo, "HZ_OPINION_TIME"),
                "yyyy-MM-dd hh:mm:ss","yyyy年MM月dd日"));
        /*签名替换*/
        fillInOpinionSign(busInfo,returnMap,"CS_OPINION_ID","scr1");
        fillInOpinionSign(busInfo,returnMap,"HZ_OPINION_ID","fzr");
    }

    /**
     * 描述:创建二维码
     *
     * @author Madison You
     * @created 2020/5/30 11:27:00
     * @param
     * @return
     */
    public void createQrCode(Map<String,Object> returnMap,String qrCodeValue) {
        if (StringUtils.isNotBlank(qrCodeValue)) {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            if (WordRedrawUtil.encode(byteArrayOut, qrCodeValue)) {
                Map<String, Object> img = new HashMap<String, Object>();
                img.put("width", WordRedrawUtil.bdcQRCODEWIDTH);
                img.put("height", WordRedrawUtil.bdcQRCODEHEIGHT);
                img.put("type", WordRedrawUtil.pICEXT);
                img.put("content", byteArrayOut.toByteArray());
                returnMap.put("p_ewm", img);
            }
            try {
                byteArrayOut.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 描述:签名替换
     *
     * @author Madison You
     * @created 2020/7/3 10:49:00
     * @param
     * @return
     */
    public void fillInOpinionSign(Map<String, Object> busInfo,Map<String,Object> returnMap,
                                  String signColumn, String fillColumn) {
        String HZ_OPINION_ID = StringUtil.getValue(busInfo, signColumn);
        if (StringUtils.isNotEmpty(HZ_OPINION_ID)) {
            Map<String, Object> userMap = bdcSpbPrintConfigDao.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                    new String[]{"USER_ID"}, new Object[]{HZ_OPINION_ID});
            if (userMap != null) {
                String signFileId = StringUtil.getValue(userMap, "SIGN_FILE_ID");
                if (StringUtils.isNotEmpty(signFileId)) {
                    Map<String, Object> fileMap = bdcSpbPrintConfigDao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                            new String[]{"FILE_ID"}, new Object[]{signFileId});
                    if (fileMap != null) {
                        Map<String, Object> signImg = new HashMap<>();
                        String filePath = (String) fileMap.get("FILE_PATH");
                        byte[] byteSign = getByteFileFromUrl(filePath);
                        signImg.put("width", WordRedrawUtil.bdcSIGNWIDTH);
                        signImg.put("height", WordRedrawUtil.bdcSIGNHEIGHT);
                        signImg.put("type", WordRedrawUtil.pICEXT);
                        signImg.put("content", byteSign);
                        returnMap.put(fillColumn, signImg);
                    }
                }
            }
        }
    }



    /**
     * 描述:获取url文件字节码
     *
     * @author Madison You
     * @created 2020/7/3 9:31:00
     * @param
     * @return
     */
    public byte[] getByteFileFromUrl(String filePath) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try{
            URL url = new URL(uploadFileUrl + filePath);
            in = new BufferedInputStream(url.openStream());
            out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (Exception e) {
            log.error("审批表打印获取签名文件失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
            if (out != null) {
                try{
                    out.close();
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }
        return out.toByteArray();
    }

    /**
     * 描述:问询记录字段替换
     *
     * @author Madison You
     * @created 2020/8/17 14:16:00
     * @param
     * @return
     */
    @Override
    public void fillWxjlForm(Map<String, Object> returnMap, Map<String, Object> busInfo) {
        /*不动产申请表询问记录*/
        selectYesOrNo("BDC_XWJL_YWR1", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_YWR2", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_YWR3", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_YWR4", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_YWR5", "1", busInfo, returnMap);
        if(!"undefined".equals(StringUtil.getValue(busInfo,"BDC_XWJL_YWRSZFE"))){
            returnMap.put("BDC_XWJL_YWRSZFE", StringUtil.getValue(busInfo, "BDC_XWJL_YWRSZFE"));
        }else{
            returnMap.put("BDC_XWJL_YWRSZFE", "");
        }
        returnMap.put("BDC_XWJL_YWR6", StringUtil.getValue(busInfo, "BDC_XWJL_YWR6"));
        selectYesOrNo("BDC_XWJL_QLR1", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_QLR2", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_QLR3", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_QLR4", "1", busInfo, returnMap);
        selectYesOrNo("BDC_XWJL_QLR5", "1", busInfo, returnMap);
        if(!"undefined".equals(StringUtil.getValue(busInfo,"BDC_XWJL_QLRSZFE"))){
            returnMap.put("BDC_XWJL_QLRSZFE", StringUtil.getValue(busInfo, "BDC_XWJL_QLRSZFE"));
        }else{
            returnMap.put("BDC_XWJL_QLRSZFE", "");
        }
        returnMap.put("BDC_XWJL_QLR6", StringUtil.getValue(busInfo, "BDC_XWJL_QLR6"));
        returnMap.put("BDC_XWJL_BZ", StringUtil.getValue(busInfo, "BDC_XWJL_BZ"));
        /*银行询问记录*/
        selectYesOrNo("YH_XWJL1", "1", busInfo, returnMap);
        selectYesOrNo("YH_XWJL2", "1", busInfo, returnMap);
        selectYesOrNo("YH_XWJL3", "1", busInfo, returnMap);
        selectYesOrNo("YH_XWJL4", "1", busInfo, returnMap);
        /*通用询问记录*/
        returnMap.put("tyxwjl1", dictionaryService.getDicNames("YesOrNo", StringUtil.getValue(busInfo, "TY_XWJL1")));
        returnMap.put("tyxwjl2", dictionaryService.getDicNames("SYFS", StringUtil.getValue(busInfo, "TY_XWJL2")));
        String tyXwjl2 = StringUtil.getValue(busInfo, "TY_XWJL2");
        if (tyXwjl2.equals("ddsy")) {
            returnMap.put("tyxwjl3", "");
            returnMap.put("tyxwjl4", "");
        } else {
            returnMap.put("tyxwjl3", dictionaryService.getDicNames("GYFS1", StringUtil.getValue(busInfo, "TY_XWJL3")));
            returnMap.put("tyxwjl4", StringUtil.getValue(busInfo, "TY_XWJL4"));
        }
        returnMap.put("tyxwjl5", dictionaryService.getDicNames("YesOrNo", StringUtil.getValue(busInfo, "TY_XWJL5")));
        returnMap.put("tyxwjl6", dictionaryService.getDicNames("YesOrNo", StringUtil.getValue(busInfo, "TY_XWJL6")));
        returnMap.put("tyxwjl7", dictionaryService.getDicNames("YesOrNo", StringUtil.getValue(busInfo, "TY_XWJL7")));
        returnMap.put("tyxwjl8", StringUtil.getValue(busInfo, "TY_XWJL8"));
    }

    /**
     * 描述:询问记录是否选择框
     *
     * @author Madison You
     * @created 2020/8/17 14:25:00
     * @param
     * @return
     */
    public void selectYesOrNo(String column, String columnValue , Map<String,Object> busInfo , Map<String,Object> returnMap) {
        String value = StringUtil.getValue(busInfo, column);
        if (StringUtils.isNotEmpty(value)) {
            if (value.equals(columnValue)) {
                returnMap.put(column + "_1", "■");
                returnMap.put(column + "_2", "□");
            } else {
                returnMap.put(column + "_1", "□");
                returnMap.put(column + "_2", "■");
            }
        } else {
            returnMap.put(column + "_1", "□");
            returnMap.put(column + "_2", "□");
        }
    }

}
