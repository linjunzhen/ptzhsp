/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.hflow.model.FlowNextHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月7日 上午8:19:59
 */
public class JsonUtilTest  {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(JsonUtilTest.class);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月1日 上午11:28:30
     * @param args
     */
    public static void main(String[] args){
        String json = FileUtil.getContentOfFile("d:/nextstep.json");
        Map<String,Object> flowSubmitInfo = JSON.parseObject(json,Map.class);
        //获取传递过来的环节名称
        //String flowNodeName = (String) flowSubmitInfo.get("EFLOW_FLOWNODENAME");
        //获取已选择的审核人
        Map<String,Object> nextSteps = (Map<String, Object>) flowSubmitInfo.get("EFLOW_NEXTSTEPS");
        List<FlowNextHandler> handlers = JSON.parseArray(nextSteps.get("人力审批").toString(), FlowNextHandler.class);
        log.info(handlers.size());
        //List<Map> list = JSON.parseArray(applyListJson, Map.class);
        
        //log.info(nodeDataArray.size());
    }
    
    /**
     * 
     * 描述 遍历MAP
     * @author Flex Hu
     * @created 2015年8月20日 下午2:38:11
     */
    @Test
    public void iterMap(){
        Map<String,Object> colValues = new HashMap<String,Object>();
        Iterator it = colValues.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String fieldName = (String) entry.getKey();
            Object val = entry.getValue();
        }
    }

    /**
     * objectToJson
     * object转json
     */
    @Test
    public void objectToJson(){
        UserModel user = new UserModel("张三",1,20.33,false);  
        String text = JSON.toJSONString(user);
        log.info("字符串:"+text);
    }
    
    /**
     * jsonToObject
     * json转object
     */
    @Test
    public void jsonToObject(){
        String  str = "{\"marray\":false,\"sex\":1,\"username\":\"张三\",\"weight\":20.33}";
        UserModel user = JSON.parseObject(str,UserModel.class);
        log.info(user.getUsername()+","+user.isMarray());
    }
    
    /**
     * listToJson
     * list转json
     */ 
    @Test
    public void listToJson(){
        UserModel m1 = new UserModel("张三",1,20.33,false);  
        UserModel m2 = new UserModel("李四",1,20.33,true);  
        List<UserModel> list = new ArrayList<UserModel>();
        list.add(m1);
        list.add(m2);
        log.info(JSON.toJSONString(list));
    }
    
    /**
     * jsonToList
     * json转list
     */ 
    @Test
    public void jsonToList(){
        UserModel m1 = new UserModel("张三",1,20.33,false);  
        UserModel m2 = new UserModel("李四",1,20.33,true);  
        List<UserModel> list = new ArrayList<UserModel>();
        list.add(m1);
        list.add(m2);
        String str = JSON.toJSONString(list);
        List<UserModel> modelList = JSON.parseArray(str, UserModel.class);
        Iterator<UserModel> it = list.iterator();
        while(it.hasNext()){
            UserModel user = it.next();
            log.info(user.getUsername());
        }
    }
    
    /**
     * mapToJson
     * map 转 json
     */
    @Test
    public void mapToJson(){
        Map<String,UserModel> map = new HashMap<String,UserModel>();
        UserModel m1 = new UserModel("张三",1,20.33,false);  
        UserModel m2 = new UserModel("李四",1,20.33,true);  
        map.put("user1",m1);
        map.put("user2",m2);
        String json = JSON.toJSONString(map);
        log.info(json);
    }
    
    /**
     * multiTypeToJson
     * 组合类型集合的反序列化
     */
    @Test
    public void multiTypeToJson(){
        String str = "[{\"height\":180,\"sex\":1,\"userId\":22,\"username\":\"小三\",\"weight\":55}," +
                "{\"name\":\"福建省\",\"code\":\"362800\"}]";
        /*Type[] types = new Type[] {UserModel.class,
                 Province.class};
        List<Object>
         list = JSON.parseArray(str, types);
        UserModel user = (UserModel) list.get(0);
        Province pro = (Province) list.get(1);
        log.info(user.getUsername()+","+ pro.getName());*/
    }
    
    /**
     * jsonToMap
     * json转map
     */
    @Test
    public void jsonToMap(){
        Map<String,UserModel> map = new HashMap<String,UserModel>();
        UserModel m1 = new UserModel("张三",1,20.33,false);  
        UserModel m2 = new UserModel("李四",1,20.33,true);  
        map.put("user1",m1);
        map.put("user2",m2);
        String str = JSON.toJSONString(map);
        Map<String, UserModel> map2 = JSON.parseObject(str, new TypeReference<Map<String, UserModel>>() {});
        UserModel user = map2.get("user1");
        log.info(user.getUsername());
    }
    
   
    /**
     * 测试过滤字段功能
     * 描述
     * @author Flex Hu
     * @created 2014年9月7日 上午8:57:23
     */
    @Test
    public void testFilter(){
        Clazz clzz = new Clazz("一年三班","003");
        UserModel m1 = new UserModel("张三",1,20.33,false);  
        m1.setClazz(clzz);
        String json = JsonUtil.jsonStringFilter(m1,new String[]{"username"},JsonUtil.EXCLUDE);
        log.info(json);
    } 
    /**
     * 测试列表过滤字段
     * 描述
     * @author Flex Hu
     * @created 2014年9月7日 上午8:59:35
     */
    @Test
    public void testFilterForList(){
        UserModel m1 = new UserModel("张三",1,20.33,false);  
        Clazz clzz = new Clazz("一年三班","003");
        m1.setClazz(clzz);
        UserModel m2 = new UserModel("李四",1,20.33,true);  
        List<UserModel> list = new ArrayList<UserModel>();
        list.add(m1);
        list.add(m2);
        String json = JsonUtil.jsonStringFilter(list,new String[]{"username"},JsonUtil.EXCLUDE);
        log.info(json);
    } 
    
    @Test
    public void testAttachFileJson(){
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("ATTACH_ID", "-1");
        map1.put("FILE_IDS", "33,44");
        Map<String,String> map2 = new HashMap<String,String>();
        map2.put("ATTACH_ID","2");
        map2.put("FILE_IDS", "55,66");
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list.add(map1);
        list.add(map2);
        String json = JSON.toJSONString(list);
        log.info(JSON.toJSONString(list));
        List<Map> modelList = JSON.parseArray(json, Map.class);
        log.info(modelList.size()+","+modelList.get(0).get("ATTACH_ID"));
    }
}
