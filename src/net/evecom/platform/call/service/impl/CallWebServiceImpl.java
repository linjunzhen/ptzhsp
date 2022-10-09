/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.call.service.CallService;
import net.evecom.platform.call.service.CallWebService;

/**
 * 描述
 * @author Danto Huang
 * @created 2016-1-25 下午5:43:12
 */
public class CallWebServiceImpl implements CallWebService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CallWebServiceImpl.class);
    
    @Override
    public String getWaitCall(String roomNo) {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            Properties properties = FileUtil.readProperties("conf/jdbc.properties");
            Class.forName(properties.getProperty("jdbc.driverClassName"));
            Sm4Utils sm4 = new Sm4Utils();
            String url = sm4.decryptDataCBC(properties.getProperty("jdbc.url"));
            String user = sm4.decryptDataCBC(properties.getProperty("jdbc.username"));
            String password = sm4.decryptDataCBC(properties.getProperty("jdbc.password"));
            con = DriverManager.getConnection(url, user, password);
            String sql = "select t.*,s.screen_no from T_CKBS_CALLWAIT t "
                    + "left join (select m.win_no,m.screen_no from T_CKBS_WIN_USER m group by m.win_no,m.screen_no) s"
                    + " on s.win_no=t.win_no where t.take_status=0 and t.room_no='"+roomNo+"' order by t.create_time";
            pre = con.prepareStatement(sql);
            result = pre.executeQuery(sql);
            while (result.next()){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("ID", result.getString("ID"));
                map.put("RECORD_ID", result.getString("RECORD_ID"));
                map.put("LINE_NO", result.getString("LINE_NO"));
                map.put("WIN_NO", result.getString("WIN_NO"));
                map.put("SCREEN_NO", result.getString("SCREEN_NO"));
                map.put("VOICE", result.getString("VOICE"));
                map.put("LEDINFO", result.getString("LEDINFO"));
                map.put("TYPE", result.getString("TYPE"));
                list.add(map);
            }
            pre.close();
            String updSql = "update T_CKBS_CALLWAIT t set t.take_status=1 where t.id=?";
            pre = con.prepareStatement(updSql);
            for(int i=0;i<list.size();i++){
                String id = list.get(i).get("ID").toString();
                pre.setString(1, id);
                pre.executeUpdate();
            }
            pre.close();
            con.close();
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }catch (SQLException e) {
            log.error(e.getMessage());
        }finally{
            if (con!=null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (pre!=null) {
                try {
                    pre.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return JSON.toJSONString(list);
    }

}
