/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述 流程图数据工具类
 * 
 * @author Sundy Sun
 * @version 2.0
 */
public class FlowDataUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowDataUtil.class);
    /**
     * 正常的流程节点状态颜色
     */
    private static final String STATE_COLOR = "green";
    /** * 颜色 */
    private static final String DEFALT_PASS_COLOR = "blue";

    public static void main(String[] args) {
        String jsonStr = "{ 'class': 'go.GraphLinksModel','linkFromPortIdProperty': 'fromPort',"
                + "'linkToPortIdProperty': 'toPort',"
                + "'nodeDataArray': [ {'category':'Comment', 'loc':'412 0', 'text':"
                + "'设区市公共租赁住房权力运行流程图（全省通用）', 'key':-13,'id':3},"
                + "{'key':-1, 'category':'Start', 'loc':'173 0', 'text':'开始','id':1},"
                + "{'key':1, 'loc':'175 269','id':2, " 
                + "'text':'街道(镇)办事人员打印申请人的相关申请信息和审查通过信息，在街道张榜公示(7天)'}],"
                + "'linkDataArray': [ {'from':-1, 'to':-3, 'fromPort':'B', 'toPort':'T', "
                + "'points':[175,30,175,40,175,42.35000000000002,174.390625,42.35000000000002,"
                + "174.390625,44.700000000000045,174.390625,54.700000000000045]}]}";
        changeNodeColor(jsonStr, "1", "red");
    }

    /**
     * 设置节点是否可以编辑
     * 
     * @param jsonStr
     * @param list
     * @return
     */
    public static String setEditState(String jsonStr, List<Map<String, Object>> list) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        List<FlowNodeBean> noSetId=new ArrayList<FlowNodeBean>();
        List<String> ids=new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            ids.add((String)list.get(i).get("NODE_CODE"));
        }
        Object[] array = new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            bean.setEditable(true);
            bean.setDeletable(true);
            //map.put(bean.getKey(), bean);
            if("start".equals(bean.getCategory())||"end".equals(bean.getCategory())
                    ||"Comment".equals(bean.getCategory())){
                noSetId.add(bean);
            }else{
                map.put(bean.getKey(), bean);
            }
            if(ids.contains(bean.getId())){
                bean.setEditable(false);
                bean.setDeletable(false);
                map.put(bean.getKey(), bean);
            }
        }
        
//        for (int i = 0; i < list.size(); i++) {
//            String id = (String) list.get(i).get("NODE_CODE");
//            FlowNodeBean bean = map.get(id);
//            if(bean!=null){
//                bean.setEditable(false);
//                bean.setDeletable(false);
//                map.put(bean.getId(), bean);
//            }
//        }
        int index = 0;
        for (Map.Entry<String, FlowNodeBean> entry : map.entrySet()) {
            FlowNodeBean bean = map.get(entry.getKey());
            array[index] = bean;
            index = index + 1;
        }
        for (int i = 0; i < noSetId.size(); i++) {
            array[index]=noSetId.get(i);
            index=index+1;
        }
        jsonObj.put("nodeDataArray", array);
        return jsonObj.toString();
    }
    
    /**
     * 设置节点是否监察点颜色
     * 
     * @param jsonStr
     * @param list
     * @return
     */
    public static String setFlowNodeColor(String jsonStr, List<Map<String, Object>> list) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        List<FlowNodeBean> noSetId=new ArrayList<FlowNodeBean>();
        List<String> ids=new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            ids.add((String)list.get(i).get("NODE_CODE"));
        }
        Object[] array = new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            if(ids.contains(bean.getId())){
                bean.setColor("blue");
                map.put(bean.getKey(), bean);
            }else{
                map.put(bean.getKey(), bean);
            }
        }
 
        int index = 0;
        for (Map.Entry<String, FlowNodeBean> entry : map.entrySet()) {
            FlowNodeBean bean = map.get(entry.getKey());
            array[index] = bean;
            index = index + 1;
        }
        for (int i = 0; i < noSetId.size(); i++) {
            array[index]=noSetId.get(i);
            index=index+1;
        }
        jsonObj.put("nodeDataArray", array);
        return jsonObj.toString();
    }

    /**
     * 设置节点编码
     * 
     * @param jsonStr
     * @param tacheCode
     * @return
     */
    public static String setNodeIdForChange(String jsonStr, String tacheCode) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        List<FlowNodeBean> noSetId=new ArrayList<FlowNodeBean>();
        List<FlowNodeBean> list = new ArrayList<FlowNodeBean>();
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            if("start".equals(bean.getCategory())||"end".equals(bean.getCategory())
                    ||"Comment".equals(bean.getCategory())){
                noSetId.add(bean);
            }else{
                list.add(bean);
            }
        }
        Object[] array = list.toArray();
        Arrays.sort(array, new FlowBeanComparator());
        list = new ArrayList<FlowNodeBean>();
        boolean isExist = false;
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            FlowNodeBean bean = (FlowNodeBean) array[i];
            if (bean.getId() != null && !"".equals(bean.getId())) {
                isExist = true;
                int loc = bean.getId().lastIndexOf(".");
                String str = bean.getId().substring(loc + 1, bean.getId().length());
                int num = Integer.parseInt(str);
                if (num > max) {
                    max = num;
                }
            }
        }
        if (isExist) {
            for (int i = 0; i < array.length; i++) {
                FlowNodeBean bean = (FlowNodeBean) array[i];
                if (bean.getId() != null && !"".equals(bean.getId())) {
                    list.add(bean);
                } else {
                    max = max + 1;
                    bean.setId(tacheCode + "." + max);
                    list.add(bean);
                }
            }
        } else {
            for (int i = 0; i < array.length; i++) {
                FlowNodeBean bean = (FlowNodeBean) array[i];
                int ind=i+1;
                String pid=tacheCode + "." +ind;
                bean.setId(pid);
                list.add(bean);
            }
        }
        // jsonObj.remove("nodeDataArray");
        for (int i = 0; i < noSetId.size(); i++) {
            list.add(noSetId.get(i));
        }
        jsonObj.put("nodeDataArray", list.toArray());
        return jsonObj.toString();
    }

    /**
     * 设置节点编码
     * 
     * @param jsonStr
     * @param tacheCode
     * @return
     */
    public static String setNodeIdKey(String jsonStr, String tacheCode) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        //Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        List<FlowNodeBean> list = new ArrayList<FlowNodeBean>();
        String index = "0";
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            list.add(bean);
        }
        Object[] array = list.toArray();
        for (int i = 0; i < array.length; i++) {
            FlowNodeBean bb = (FlowNodeBean) array[i];
            log.info(bb.getText() + ";id:" + bb.getId());
        }
        Arrays.sort(array, new FlowBeanComparator());
        for (int i = 0; i < array.length; i++) {
            FlowNodeBean bb = (FlowNodeBean) array[i];
            log.info(bb.getText() + ";id:" + bb.getId());
        }
        list = new ArrayList<FlowNodeBean>();
        for (int i = 0; i < array.length; i++) {
            FlowNodeBean bean = (FlowNodeBean) array[i];
            // if(bean.getId()!=null&&!"".equals(bean.getId())){
            bean.setId(tacheCode + "." + i);
            list.add(bean);
            // }
        }
        // jsonObj.remove("nodeDataArray");
        jsonObj.put("nodeDataArray", list.toArray());
        // log.info(jsonObj.toString());
        return jsonObj.toString();
    }

    /**
     * 设置当前节点状态
     * 
     * @param jsonStr
     * @param ids
     */
    public static void setNodesState(String jsonStr, String[] ids) {
        log.info(jsonStr);
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            map.put(bean.getId(), bean);
        }
        for (int i = 0; i < ids.length; i++) {
            FlowNodeBean bean = map.get(ids[i]);
            bean.setColor(STATE_COLOR);
            map.put(bean.getId(), bean);
        }
        jsonObj.put("nodeDataArray", map.toString());
        log.info(jsonObj.toString());
    }

    /**
     * 更新上级节点
     * 
     * @param jsonStr
     * @param id
     */
    public static void updateUpperNodes(String jsonStr, String id) {
        log.info(jsonStr);
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        String index = "0";
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            if (!"".equals(id) && id != null && id.equals(bean.getKey())) {
                bean.setColor(STATE_COLOR);
                index = bean.getId();
            }
            map.put(bean.getId(), bean);
        }
        int num = Integer.parseInt(index);
        for (int i = 0; i < num; i++) {
            FlowNodeBean bean = map.get(i);
            bean.setColor(STATE_COLOR);
            map.put(bean.getId(), bean);
        }
        // jsonObj.put("nodeDataArray", list.toArray());
        jsonObj.put("nodeDataArray", map.toString());
        log.info(jsonObj.toString());
    }

    // 根据规则生成制定key
    public static String formatFlowJson(String jsonStr, String segmentId) {
        String newStr = "";
        return newStr;
    }

    /**
     * 统一更新流程图节点颜色
     * 
     * @param jsonStr
     *            流程图数据
     * @param color
     *            颜色值
     */
    public static String changeFlowColor(String jsonStr, String color) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        List<FlowNodeBean> list = new ArrayList<FlowNodeBean>();
        if (!"".equals(color) && color != null) {
            for (int i = 0; i < data.size(); i++) {
                String nodestr = data.getString(i);
                FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
                bean.setColor(color);
                list.add(bean);
            }
            jsonObj.put("nodeDataArray", list.toArray());
        }

        log.info(jsonObj.toString());
        return jsonObj.toString();
    }

    /**
     * 设置节点颜色
     * 
     * @param jsonStr
     *            流程数据
     * @param id
     *            节点key
     * @param color颜色
     */
    public static String changeNodeColor(String jsonStr, String id, String color) {
        log.info(jsonStr);
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        /**
         * JSONArray link=jsonObj.getJSONArray("linkDataArray"); JSONArray
         * fromport=jsonObj.getJSONArray("linkFromPortIdProperty"); JSONArray
         * toport=jsonObj.getJSONArray("linkToPortIdProperty"); JSONArray
         * mclass=jsonObj.getJSONArray("modelClass"); FlowModelBean model=new
         * FlowModelBean();
         * model.setLinkFromPortIdProperty(fromport.getString(0));
         * model.setLinkToPortIdProperty(toport.getString(0));
         * model.setModelClass(mclass.getString(0));
         **/
        List<FlowNodeBean> list = new ArrayList<FlowNodeBean>();
        Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            if (!"".equals(id) && id != null && id.equals(bean.getKey())) {
                bean.setColor(color);
            }
            list.add(bean);
            map.put(bean.getId(), bean);
        }
        // jsonObj.put("nodeDataArray", list.toArray());
        // log.info(jsonObj.toString());
        jsonObj.put("nodeDtaArray", map.toString());
        log.info(jsonObj.toString());
        return jsonObj.toString();
    }

    /**
     * 转化流程节点数据为对象列表信息
     * 
     * @param jsonStr
     *            流程数据
     * @return
     */
    public static List transformNodeToList(String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        List<FlowNodeBean> list = new ArrayList<FlowNodeBean>();
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            list.add(bean);
        }
        return list;
    }

    /**
     * 转化流程节点数据为map
     * 
     * @param jsonStr
     *            流程数据
     * @return
     */
    public static Map transformNodeToMap(String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        Map map = new HashMap<String, FlowNodeBean>();
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            map.put(bean.getKey(), bean);
        }
        return map;
    }
}
