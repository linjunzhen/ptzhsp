/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年1月31日 下午8:11:39
 */
public class Fileinfo extends File {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(Fileinfo.class);
    /**
     * 
     */
    private int id;
    /**
     * 
     */
    private int fid;
    /**
     * 
     */
    public static List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
    /**
     * 
     */
    public static String json = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public Fileinfo(String pathname) {
        super(pathname);
    }

    /**
     * 
     * 描述 非递归遍历文件夹
     * 
     * @author Faker Li
     * @created 2015-1-21 上午10:29:35
     * @param path 文件路径
     */
    public static void readfiles(String path) {

        LinkedList<Fileinfo> list = new LinkedList<Fileinfo>();
        Fileinfo dir = new Fileinfo(path);
        dir.setFid(0);
        dir.setId(1);
        int index = 1;
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            Fileinfo tem = new Fileinfo(file[i].getAbsolutePath());
            tem.setId(++index);
            tem.setFid(dir.getId());
            Map<String, Object> fileMap = null;
            fileMap = new HashMap<String, Object>();
            fileMap.put("name", tem.getName());
            fileMap.put("id", tem.getId());
            fileMap.put("pId", tem.getFid());
            fileMap.put("path", tem.getPath());
            if (tem.isFile()) {
                fileMap.put("icon", "plug-in/easyui-1.4/themes/icons/page.png");
            } else {
                fileMap.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            }
            fileMap.put("isParent", false);
            fileList.add(fileMap);
            if (file[i].isDirectory()) {
                list.add(tem);
            } else {

            }
        }
        Fileinfo tmp;
        while (!list.isEmpty()) {
            tmp = (Fileinfo) list.removeFirst();
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    Fileinfo tem = new Fileinfo(file[i].getAbsolutePath());
                    tem.setFid(tmp.getId());
                    tem.setId(++index);
                    if (file[i].isDirectory())
                        list.add(tem);
                    else {

                    }
                    Map<String, Object> fileMap = null;
                    fileMap = new HashMap<String, Object>();
                    fileMap.put("name", tem.getName());
                    fileMap.put("id", tem.getId());
                    fileMap.put("pId", tem.getFid());
                    fileMap.put("path", tem.getPath());
                    if (tem.isFile()) {
                        fileMap.put("icon", "plug-in/easyui-1.4/themes/icons/page.png");
                    } else {
                        fileMap.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                    }
                    fileMap.put("isParent", false);
                    fileList.add(fileMap);
                }

            } else {
                log.info(tmp.getAbsolutePath());
            }
        }

        // log.info(System.currentTimeMillis() - a);
        // log.info(fileString);

    }
    /**
     * 
     * 排序栏目树文件夹在上，文件在下
     * @author Faker Li
     * @created 2015年2月3日 下午4:37:04
     * @param fileList
     * @return
     */
    public static List<Map<String, Object>> sortList(List<Map<String, Object>> fileList){
        List<Map<String, Object>> newfileList = new ArrayList<Map<String, Object>>();
        newfileList.add(fileList.get(0));
        for (Map<String, Object> map : Fileinfo.fileList) {
            if(map.get("icon").equals("plug-in/easyui-1.4/themes/icons/folder_table.png")){
                newfileList.add(map);
            }  
        }
        for (Map<String, Object> map : Fileinfo.fileList) {
            if(map.get("icon").equals("plug-in/easyui-1.4/themes/icons/page.png")){
                newfileList.add(map);
            } 
        }
        Fileinfo.fileList.clear();
        return newfileList;
    } 
}
