/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import net.evecom.core.util.DbUtil;
import net.evecom.core.util.StringUtil;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:16:25
 */
public class DbUtilsTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(DbUtilsTestCase.class);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年10月9日 下午4:47:42
     * @param args
     */
    public static void main(String[] args){
        String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
        String username = "wsbsdt";
        String password = "wsbsdt";
        try {
            Connection conn = DbUtil.getConnect(dbUrl, username, password);
            String sql = "select count(*) from PROVINCEINFO T ";
            java.math.BigDecimal count = (java.math.BigDecimal)DbUtil.getObjectBySql(conn, sql, null);
            log.info("值:"+count);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        
    }
    
    /**
     * 数据库连接
     */
    static class ConnectDb {
        /**
         * driveClassName
         */
        private static String driveClassName = "oracle.jdbc.driver.OracleDriver";
        /**
         * url
         */
        private static String url = "jdbc:oracle:thin:@192.168.2.170:1521:ORCL";
        /**
         * user
         */
        private static String user = "fjrd";
        /**
         * password
         */
        private static String password = "fjrd";

        public static Connection connect() {
            Connection conn = null;

            // load driver
            try {
                Class.forName(driveClassName);
            } catch (ClassNotFoundException e) {
                log.info("load driver failed!");
                log.error(e.getMessage());
            }

            // connect db
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                log.info("connect failed!");
                log.error(e.getMessage());
            }

            return conn;
        }
    }

    /**
     * 使用DbUtils插入数据,非spring集成
     */
    @Test
    public void addDataTest() {
        Connection conn = ConnectDb.connect();
        try {
            QueryRunner qRunner = new QueryRunner();
            String sql = "insert into T_MSJW_DEMO_PRODUCT(PRODUCT_ID,PRODUCT_NAME,"
                    + "Create_Time,PRODUCT_CODE) values(?,?,?,?)";
            Object param[] = { 4, "产品4", "时间", "123" };
            // 注意param的位置
            int n = qRunner.update(conn, sql, param);
            log.info("成功插入" + n + "条数据！");
        } catch (SQLException e) {
            log.info("添加数据出错");
            log.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }

    /**
     * 使用DbUtils批量插入数据,非spring集成
     */
    @Test
    public void addBatchDataTest() {
        Connection conn = ConnectDb.connect();
        try {
            QueryRunner qRunner = new QueryRunner();
            String sql = "insert into T_MSJW_DEMO_PRODUCT(PRODUCT_ID,PRODUCT_NAME,"
                    + "Create_Time,PRODUCT_CODE) values(?,?,?,?)";
//            QueryRunner query = new QueryRunner();
            Object[][] params = {{5,"产品5","创建时间","555"},{6,"产品6", "创建时间", "666" },{ 7,"产品7", "创建时间","777"} };
            int[] n = qRunner.batch(conn, sql, params);
            log.info("插入了" + n.length + "数据");
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }

    /**
     * 使用DbUtils删除数据,非spring集成
     */
    @Test
    public void deleteDataTest() {
        Connection conn = ConnectDb.connect();
        try {
            QueryRunner qRunner = new QueryRunner();
            String sql = "DELETE FROM T_MSJW_DEMO_PRODUCT WHERE PRODUCT_ID='3'";
            int n = qRunner.update(conn, sql);
            log.info("成功删除" + n + "条数据！");
        } catch (SQLException e) {
            log.info("删除数据出错");
            log.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 使用Dbutils修改数据,非spring集成
     */
    @Test
    public void updateDataTest() {
        Connection conn = ConnectDb.connect();
        try {
            conn.setAutoCommit(false);
            QueryRunner qRunner = new QueryRunner();
            String sql = "UPDATE T_MSJW_DEMO_PRODUCT SET PRODUCT_NAME='111' WHERE PRODUCT_ID='1'";
            int n = qRunner.update(conn, sql);
            if (1 / 0 == 0) {
            }
            log.info("成功更新" + n + "条数据！");
        } catch (Exception e) {
            try {
                DbUtils.rollback(conn);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                log.error(e1.getMessage());
            }
            log.info("更新数据出错");
            log.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 使用Dbutils查询数据,非spring集成
     */
    @Test
    public void queryDataTest() {
        Connection conn = ConnectDb.connect();
//        ResultSetHandler rsh = new MapListHandler();
        try {
            QueryRunner qRunner = new QueryRunner();
           /* String sql = "SELECT PRODUCT_ID,PRODUCT_NAME,Create_Time,PRODUCT_CODE FROM T_MSJW_DEMO_PRODUCT ";
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> list = (List<Map<String, Object>>) qRunner.query(conn, sql, rsh);
            for (Map<String, Object> p : list) {
                log.info(p.get("PRODUCT_ID") + "," + p.get("PRODUCT_NAME"));
            }
            log.info("=========");
            String sql2 = "SELECT * FROM T_MSJW_DEMO_PRODUCT WHERE PRODUCT_ID=? OR PRODUCT_NAME=?";
            Object param[] = { 4, "产品名2" };
            @SuppressWarnings("unchecked")
            // 注意param的位置
            List<Map<String, Object>> list2 = (List<Map<String, Object>>) qRunner.query(conn, sql2, param, rsh);

            for (Map<String, Object> p : list2) {
                log.info(p.get("PRODUCT_ID") + "," + p.get("PRODUCT_NAME"));
            }*/
            /*String sql = "select PRODUCT_ID,PRODUCT_NAME FROM T_MSJW_DEMO_PRODUCT WHERE PRODUCT_ID=? ";
            Map<String,Object> map =  (Map<String, Object>) 
                    qRunner.query(conn, sql, new Object[]{"5"}, new MapHandler());
            log.info(map.get("PRODUCT_NAME"));*/
            
            String sql = "select count(*) FROM T_MSJW_DEMO_PRODUCT ";
            Object count = qRunner.query(conn, sql, new ScalarHandler());
            log.info("count is:"+count);

        } catch (SQLException e) {
            log.info("查询数据出错");
            log.error(e.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 判断是否有连接数据库
     */
    @Test
    public void isConnectionTest() {
        Connection conn = ConnectDb.connect();
        try {
            log.info(conn.isClosed());
        } catch (Exception e) {
            log.error("没连通");
            log.error(e.getMessage());
        }
    }

}
