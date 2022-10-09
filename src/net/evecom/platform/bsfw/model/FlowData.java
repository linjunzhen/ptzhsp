/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.model;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.zzhy.model.TableNameEnum;

import java.util.*;

import com.alibaba.fastjson.JSON;

/**
 * 描述:业务数据类
 *
 * @author Madison You
 * @created 2020/11/3 9:56:00
 * @param
 * @return
 */
public class FlowData {

    /**
     * 描述:业务数据标准位
     *
     * @author Madison You
     * @created 2020/11/3 10:57:00
     * @param
     * @return
     */
    public static final Integer ALL_MAP = 0;

    /**
     * 描述:业务数据标准位
     *
     * @author Madison You
     * @created 2020/11/3 10:57:00
     * @param
     * @return
     */
    public static final Integer BUS_MAP = 1;

    /**
     * 描述:附近数据标记位
     *
     * @author Madison You
     * @created 2020/11/3 10:57:00
     * @param
     * @return
     */
    public static final Integer FILE_LIST = 2;

    /**
     * 描述:事项数据标记位
     *
     * @author Madison You
     * @created 2020/11/3 10:57:00
     * @param
     * @return
     */
    public static final Integer ITEM_MAP = 3;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/11 9:32:00
     * @param
     * @return
     */
    public static final Integer RESULT_MAP = 4;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:57:00
     * @param
     * @return
     */
    private Map<String, Object> exeMap = null;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:57:00
     * @param
     * @return
     */
    private Map<String, Object> busMap = null;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    private List<Map<String, Object>> fileList = null;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    private Map<String, Object> itemMap = null;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/11 9:29:00
     * @param
     * @return
     */
    private Map<String, Object> resultMap = null;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    private ExecutionService executionService = (ExecutionService) AppUtil.getBean("executionService");
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    private ExeDataService exeDataService = (ExeDataService) AppUtil.getBean("exeDataService");

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public Map<String, Object> getExeMap() {
        return exeMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public void setExeMap(Map<String, Object> exeMap) {
        this.exeMap = exeMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public Map<String, Object> getBusMap() {
        return busMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public void setBusMap(Map<String, Object> busMap) {
        this.busMap = busMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public List<Map<String, Object>> getFileList() {
        return fileList;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public void setFileList(List<Map<String, Object>> fileList) {
        this.fileList = fileList;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public Map<String, Object> getItemMap() {
        return itemMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public void setItemMap(Map<String, Object> itemMap) {
        this.itemMap = itemMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public FlowData(String exeId) {
        initExeMap(exeId);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/11 9:29:00
     * @param
     * @return
     */
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/11 9:29:00
     * @param
     * @return
     */
    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    public FlowData(String exeId, Integer dataType) {
        this.initExeMap(exeId);
        switch (dataType) {
            case 0:
                this.initAllMap();
                break;
            case 1:
                this.initBusMap();
                break;
            case 2:
                this.initFileList();
                break;
            case 3:
                this.initItemMap();
                break;
            case 4:
                this.initResultMap();
            default:
                this.initBusMap();
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/11 9:33:00
     * @param
     * @return
     */
    public FlowData(String exeId , Integer[] arr){
        this.initExeMap(exeId);
        for (Integer n : arr) {
            if (n == BUS_MAP) {
                this.initBusMap();
            } else if (n == FILE_LIST) {
                this.initFileList();
            } else if (n == ITEM_MAP) {
                this.initItemMap();
            } else if (n == RESULT_MAP) {
                this.initResultMap();
            }
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 15:06:00
     * @param
     * @return
     */
    private void initAllMap() {
        this.initBusMap();
        this.initItemMap();
        this.initFileList();
        this.initResultMap();
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    private void initExeMap(String exeId){
        this.exeMap = executionService.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{exeId});
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    private void initBusMap() {
        if (this.exeMap != null) {
            String busRecordid = this.exeMap.get("BUS_RECORDID").toString();
            String busTablename = this.exeMap.get("BUS_TABLENAME").toString();
            if (Objects.equals(busTablename, TableNameEnum.T_COMMERCIAL_DOMESTIC.getVal())||
            Objects.equals(busTablename,TableNameEnum.T_COMMERCIAL_FOREIGN.getVal())) {
                busTablename = "T_COMMERCIAL_COMPANY";
            }
            //国有转移7个事项虚拟主表替换真实表名称
            if(busTablename.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                busTablename = "T_BDCQLC_GYJSJFWZYDJ";
            }
            String primaryKeyName = executionService.getPrimaryKeyName(busTablename).get(0).toString();
            this.busMap = executionService.getByJdbc(busTablename, new String[] { primaryKeyName },
                    new Object[] { busRecordid });
            this.busMap.putAll(exeDataService.getHhLegal(StringUtil.getString(this.exeMap.get("EXE_ID"))));
            if(busTablename.equals("T_COMMERCIAL_SOLELYINVEST")){
                this.busMap.put("REGISTER_ADDR", busMap.get("COMPANY_ADDR"));
                this.busMap.put("LEGAL_NAME", "");
                this.busMap.put("LEGAL_MOBILE", "");
                this.busMap.put("LEGAL_IDNO", "");
            }else if(busTablename.equals("T_COMMERCIAL_BRANCH")){
                this.busMap.put("COMPANY_NAME", busMap.get("BRANCH_NAME"));
                this.busMap.put("POST_CODE", busMap.get("POSTCODE"));
            }else if(busTablename.equals("T_COMMERCIAL_INTERNAL_STOCK")){
                this.busMap.put("POST_CODE", busMap.get("POSTAL_CODE"));
            }else if(busTablename.equals("T_COMMERCIAL_NZ_JOINTVENTURE")){
                boolean legalFlag = false;
                if(busMap.get("HOLDER_JSON")!=null){
                    List<Map<String,Object>> holders = JSON.parseObject((String)busMap.get("HOLDER_JSON"), List.class);
                    for(Map<String,Object> holder : holders){
                        if("1".equals(holder.get("IS_PARTNERSHIP"))){
                            this.busMap.put("LEGAL_NAME", holder.get("LICENCE_APPOINT_NAME"));
                            this.busMap.put("LEGAL_IDNO", holder.get("LICENCE_APPOINT_IDNO"));
                            this.busMap.put("LEGAL_MOBILE", holder.get("CONTACT_WAY"));
                            legalFlag = true;
                            break;
                        }
                    }
                }
                if(!legalFlag){
                    this.busMap.put("LEGAL_NAME", "");
                    this.busMap.put("LEGAL_IDNO", "");
                    this.busMap.put("LEGAL_MOBILE", "");
                }
            }
        }
    }
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:58:00
     * @param
     * @return
     */
    private void initFileList() {
        if (this.exeMap != null) {
            String busRecordid = this.exeMap.get("BUS_RECORDID").toString();
            this.fileList = executionService.getAllByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"BUS_TABLERECORDID"}, new Object[]{busRecordid});

        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:59:00
     * @param
     * @return
     */
    private void initItemMap() {
        if (this.exeMap != null) {
            this.itemMap = executionService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_CODE"}, new Object[]{exeMap.get("ITEM_CODE")});
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/11 9:28:00
     * @param
     * @return
     */
    private void initResultMap(){
        if (this.exeMap != null) {
            this.resultMap = executionService.getByJdbc("JBPM6_FLOW_RESULT",
                    new String[]{"EXE_ID"}, new Object[]{exeMap.get("EXE_ID")});
        }
    }

}
