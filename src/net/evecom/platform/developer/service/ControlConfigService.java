/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 配置控件操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ControlConfigService extends BaseService {
    /**
     * layout控件
     */
    public static final String CONTROL_TYPE_LAYOUT = "2";
    /**
     * 普通表格控件
     */
    public static final String CONTROL_TYPE_DATAGRID = "4";
    /**
     * 文本框控件
     */
    public static final String CONTROL_TYPE_TEXT = "6";
    /**
     * 数值框
     */
    public static final String CONTROL_TYPE_NUMBER = "7";
    /**
     * 日期框
     */
    public static final String CONTROL_TYPE_DATE = "8";
    /**
     * 下拉框
     */
    public static final String CONTROL_TYPE_COMBOBOX = "9";
    /**
     * 大文本框
     */
    public static final String CONTROL_TYPE_TEXTAREA= "10";
    /**
     * 表单布局
     */
    public static final String CONTROL_TYPE_FORMLAYOUT= "11";
    /**
     * 隐藏域控件
     */
    public static final String CONTROL_TYPE_HIDDEN = "12";
    /**
     * 下拉树控件
     */
    public static final String CONTROL_TYPE_CMOBOTREE = "13";
    /**
     * 上传控件
     */
    public static final String CONTROL_TYPE_UPLOAD = "14";
    /**
     * EWEB
     */
    public static final String CONTROL_TYPE_EWEB = "15";
    /**
     * 单选框控件
     */
    public static final String CONTROL_TYPE_RADIO = "16";
    /**
     * 复选框控件
     */
    public static final String CONTROL_TYPE_CHECKBOX = "17";
    /**
     * 树形列表操作模版代码
     */
    public static final String CONTROL_TYPE_TREELIST = "19";
    /**
     * 树形选择器模版代码
     */
    public static final String CONTROL_TYPE_TREESELECTOR = "20";
    /**
     * TAB标签值
     */
    public static final String CONTROL_TYPE_TAB = "21";
    /**
     * 
     * 描述 保存布局控件
     * @author Flex Hu
     * @created 2014年9月18日 下午2:48:00
     * @param config
     */
    public void saveLayoutControl(Map<String,Object> config);
    /**
     * 
     * 描述 保存datagrid控件
     * @author Flex Hu
     * @created 2014年9月18日 下午2:48:00
     * @param config
     */
    public void saveDataGridControl(Map<String,Object> config);
    /**
     * 
     * 描述 根据任务ID判断是否存在控件
     * @author Flex Hu
     * @created 2014年9月18日 上午11:48:36
     * @param missionId
     * @return
     */
    public boolean isExists(String missionId);
    /**
     * 
     * 描述 根据父亲id获取列表数据
     * @author Flex Hu
     * @created 2014年9月18日 下午2:42:39
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findList(String parentId,String missionId);
    /**
     * 
     * 描述 根据missionId获取生成代码
     * @author Flex Hu
     * @created 2014年9月18日 下午2:50:33
     * @param missionId
     * @return
     */
    public String getGenCodeByMissionId(String missionId);
    /**
     * 
     * 描述 生成权限条件的HTML代码
     * @author Flex Hu
     * @created 2014年9月23日 下午4:56:07
     * @param queryConfigs
     * @return
     */
    public String genQueryConfigHtml(List<Map<String,Object>> queryConfigs,String mainClassName);
    /**
     * 
     * 描述 根据任务ID、控件类型获取配置的信息记录
     * @author Flex Hu
     * @created 2014年9月24日 下午3:03:21
     * @param missionId
     * @param controlType
     * @return
     */
    public List<Map<String,Object>> findControl(String missionId,String controlType);
}
