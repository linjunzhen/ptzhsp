/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.FlowNodeBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
public class FlowJsonHandle {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowJsonHandle.class);
    /**
     * 根目录
     */
    private static Item root = new Item();
    /**
     * 路径
     */
    private static List<String> paths = new ArrayList<String>();
    /**
     * json字符串
     */
    private static String jsonStr ="";
    public static void main(String[] args) {
        findOutOf(jsonStr);
    }

    public static String findIdBykey(String key, String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        //Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            if (key.equals(bean.getKey())) {
                return bean.getId();
            }
        }
        return null;
    }

    public static List<String> findOutOf(String json) {
        jsonStr = json;
        paths = new ArrayList<String>();
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray data = jsonObj.getJSONArray("nodeDataArray");
        Map<String, FlowNodeBean> map = new HashMap<String, FlowNodeBean>();
        for (int i = 0; i < data.size(); i++) {
            String nodestr = data.getString(i);
            FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
            if ("start".equals(bean.getCategory())) {
                root.setValue(bean.getId());
                root.setKey(bean.getKey());
            }
            map.put(bean.getKey(), bean);
        }
        // 搜索根节点的孩子
        JSONArray links = jsonObj.getJSONArray("linkDataArray");
        List<Item> next = new ArrayList<Item>();
        // FlowNodeBean rootNode=map.get(root.getKey());
        for (int i = 0; i < links.size(); i++) {
            String link = links.getString(i);
            FlowLinkBean bean = JSON.parseObject(link, FlowLinkBean.class);
            if (root.getKey().equals(bean.getFrom())) {
                Item child = new Item();
                FlowNodeBean node = map.get(bean.getTo());
                child.setValue(node.getId());
                child.setKey(node.getKey());
                next.add(child);
            }
        }
        root.setNextItem(next);
        // 搜索孩子的孩子
        setItem(next, map);
        List<String> stack = new ArrayList<String>();
        List<String> pathList = new ArrayList<String>();
        generatePath(stack, root, pathList);
        for (int i = 0; i < paths.size(); i++) {
            log.info(paths.get(i));
        }
        return paths;
    }

    /**
     * 构建路径
     * 
     * @param stack
     * @param root
     * @param pathList
     */
    public static void generatePath(List<String> stack, Item root,
            List<String> pathList) {
        if (root != null) {
            stack.add(root.getValue());
            if (root.getNextItem().size() == 0) {
                changeToPath(stack, pathList); // 把值栈中的值转化为路径
            } else {
                List<Item> items = root.getNextItem();
                for (int i = 0; i < items.size(); i++) {
                    generatePath(stack, items.get(i), pathList);
                }
            }
            stack.remove(stack.size() - 1);
        }
    }

    public static void setItem(List<Item> items, Map<String, FlowNodeBean> map) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            FlowNodeBean node = map.get(item.getKey());
            if (!"end".equals(node.getCategory())) {
                List<Item> childs = findChilds(map, item.getValue());
                if (childs.size() > 0) {
                    item.setNextItem(childs);
                    setItem(childs, map);
                }
            }
        }
    }

    /**
     * 根据父ID查询子节点
     * 
     * @param pid父节点ID
     * @return
     */
    public static List<Item> findChilds(Map<String, FlowNodeBean> map,
            String pid) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        JSONArray links = jsonObj.getJSONArray("linkDataArray");
        List<Item> next = new ArrayList<Item>();
        for (int i = 0; i < links.size(); i++) {
            String link = links.getString(i);
            FlowLinkBean bean = JSON.parseObject(link, FlowLinkBean.class);
            FlowNodeBean from = map.get(bean.getFrom());
            FlowNodeBean to = map.get(bean.getTo());
            if (!"end".equals(to.getCategory()) && pid.equals(from.getId())) {
                Item child = new Item();
                child.setValue(to.getId());
                child.setKey(to.getKey());
                next.add(child);
            }
        }
        return next;
    }

    /**
     * 该算法使用递归方式实现，采用深度优先遍历树的节点，同时记录下已经遍历的节点保存在栈中。
     * 当遇到叶子节点时，输出此时栈中的所有元素，即为当前的一条路径；然后pop出当前叶子节点
     * 
     * @param stack
     *            为深度优先遍历过程中存储节点的栈
     * @param root
     *            为树的要节点或子树的根节点
     * @param pathList
     *            为树中所有从根到叶子节点的路径的列表
     */
    public static void buildPath(List<String> stack, Item root,
            List<String> pathList) {
        if (root != null) {
            stack.add(root.getValue());
            if (root.getNextItem().size() == 0) {
                changeToPath(stack, pathList); // 把值栈中的值转化为路径
            } else {
                List<Item> items = root.getNextItem();
                for (int i = 0; i < items.size(); i++) {
                    buildPath(stack, items.get(i), pathList);
                }
            }
            stack.remove(stack.size() - 1);
        }
    }

    /**
     * @param path
     * @param pathList
     */
    public static void changeToPath(List<String> path, List<String> pathList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i) != null) {
                sb.append(path.get(i) + "  ");
            }
        }
        pathList.add(sb.toString().trim());
        paths.add(sb.toString().trim());
        // log.info(pathList);
    }

}
