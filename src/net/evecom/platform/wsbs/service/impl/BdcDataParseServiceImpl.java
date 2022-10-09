/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.DbUtil;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.call.service.EstateWebService;
import net.evecom.platform.system.dao.SysRoleDao;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.service.BdcDataParseService;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 不动产接口服务业务接口 不动产数据解析服务接口
 * @author Water Guo
 * @created 2018-3-9 下午4:24:22
 */
@SuppressWarnings("rawtypes")
@Service("bdcDataParseService")
public class BdcDataParseServiceImpl extends BaseServiceImpl implements BdcDataParseService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcDataParseServiceImpl.class);
    /**
     * 引入获取省网办报送数据的处理业务的dao
     */
    @Resource
    private SwbInterfaceDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private SysRoleDao sysRoleDao;
    /**
     * 引入接口日志启记录服务
     */
    @Resource
    private DataAbutLogService dataAbutLogService;
    /**
     * estateWebService
     */
    @Resource
    private EstateWebService estateWebService;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 属性bdcDbUrl
     */
    private String bdcDbUrl;
    /**
     * 属性bdcDbUsername
     */
    private String bdcDbUsername;
    /**
     * 属性bdcDbPassword
     */
    private String bdcDbPassword;
    
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 
     * 描述  从不动产同步办件数据
     * @author Kester Chen
     * @created 2018-3-9 下午4:33:31
     * @param log
     */
    @SuppressWarnings({ "unchecked" })
    @Override
    public void parseBdcDataFromProa(Log log) {
        // 获取数据
        Connection bdcconn = null;
        try {
            bdcconn = DbUtil.getConnect(this.getBdcDbUrl(), this.getBdcDbUsername(),
                    this.getBdcDbPassword());
            parsProvinceDatas(log, bdcconn);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (bdcconn != null)
                DbUtils.closeQuietly(bdcconn);
        }
    }
    /**
     * 
     * 描述 解析不动产的办件数据
     * @author Kester Chen
     * @created 2018-3-10 下午4:06:45
     * @param log
     * @param bdcconn
     */
    private void parsProvinceDatas(Log log, Connection bdcconn) {
        String sql = "select * from " +
                "(select * from T_CWSYNCH t where t.issuccess = 0 " +
                "and (t.type<>10 or t.prosh not in " +
                "(select t2.prosh from T_CWSYNCH t2 where t2.issuccess = 1)) " +
                "order by t.createdate,t.type asc) ";
        List<Map<String, Object>> bdcinfoList = DbUtil.findBySql(bdcconn, sql, new Object[] {}, false);
        if (bdcinfoList != null && !bdcinfoList.isEmpty() && bdcinfoList.size() > 0) {
            log.info("开始调用【解析不动产办件数据】定时任务，解析处理办件数据....");
            for (Map<String, Object> bdcinfo : bdcinfoList) {
                parseProvinceBj(bdcinfo,bdcconn);
            }
            log.info("调用【解析不动产的办件数据】定时任务，解析处理办件数据结束....");
        }
    }
    /**
     *
     * 描述 解析不动产的办件数据
     * @author Water Guo
     * @created 2018-3-10 下午4:06:45
     * @param bdcconn
     */
    private void parseProvinceBj(Map<String, Object> bdcinfo, Connection bdcconn) {
        // TODO Auto-generated method stub
        String type = bdcinfo.get("TYPE")==null?
                "":bdcinfo.get("TYPE").toString();
        String flowInfoJson = bdcinfo.get("JSON")==null?
                "":bdcinfo.get("JSON").toString();
        String result = "";
//        String id = bdcinfo.get("cwsynch_id")==null?
//                "":bdcinfo.get("cwsynch_id").toString();
//        System.out.println("=================================="+id);
        if (StringUtils.isNotEmpty(type)) {
            if ("10".equals(type)) {
                result = estateWebService.flowStart(flowInfoJson);
            }else if ("20".equals(type)) {
                result = estateWebService.flowExecute(flowInfoJson);
            }
//            System.out.println("=============="+result);
            Map<String, Object> resultInfo = JSON.parseObject(result, Map.class);
            String resultCode = resultInfo.get("resultCode").toString();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String opdate = sdf.format(date);
            if ("001".equals(resultCode)) {
                String updateSql = "update T_CWSYNCH t set t.issuccess = 1 where t.cwsynch_id = ?";
                DbUtil.update(bdcconn, updateSql, new Object[] { bdcinfo.get("CWSYNCH_ID") }, false);
                String insertSql = "insert into T_CWSYNCHDETAIL  " +
                        "(CWSYNCHDETAIL_ID,CWSYNCH_ID,ISSUCCESS,RESULT,CREATEDATE,CREATEPERSON ) " +
                        "values (?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?) ";
                DbUtil.update(bdcconn, insertSql, new Object[] { 
                        opdate+bdcinfo.get("CWSYNCH_ID"),bdcinfo.get("CWSYNCH_ID"),
                        "1",result,DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        bdcinfo.get("CREATEPERSON")}, false);
            }else {
                if (result.indexOf("环节的流程任务需在审批平台处理")>0) {
                    log.info("不动产流程执行请求处理结果：【办结】环节的流程任务需在审批平台处理,不正确的请求接口重新报送" );
                }else {
                    String updateSql = "update T_CWSYNCH t set t.issuccess = 2 where t.cwsynch_id = ?";
                    DbUtil.update(bdcconn, updateSql, new Object[] { bdcinfo.get("CWSYNCH_ID") }, false);
                    String insertSql = "insert into T_CWSYNCHDETAIL  " +
                            "(CWSYNCHDETAIL_ID,CWSYNCH_ID,ISSUCCESS,RESULT,CREATEDATE,CREATEPERSON ) " +
                            "values (?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?) ";
                    DbUtil.update(bdcconn, insertSql, new Object[] { 
                            opdate+bdcinfo.get("CWSYNCH_ID"),bdcinfo.get("CWSYNCH_ID"),
                            "2",result,DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                            bdcinfo.get("CREATEPERSON")}, false);
                }
            } 
        }
        
    }
    /**
     * 
     * 描述 不动产对接情况每日定时器
     * @author Water Guo
     * @created 2018-5-22 上午10:40:39
     * @param log
     */
    public void bdcProvDataSituation(Log log) {
        // TODO Auto-generated method stub
        Connection bdcconn = null;
        try {
            bdcconn = DbUtil.getConnect(this.getBdcDbUrl(), this.getBdcDbUsername(),
                    this.getBdcDbPassword());
            bdcconnSituation(log, bdcconn);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (bdcconn != null)
                DbUtils.closeQuietly(bdcconn);
        }
    }
    /**
     *
     * 描述 解析不动产的办件数据
     * @author Water Guo
     * @created 2018-3-10 下午4:06:45
     * @param bdcconn
     */
    private void bdcconnSituation(Log log2, Connection bdcconn) {
        // TODO Auto-generated method stub
        String currentTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String beginTime = currentTime + " 00:00:00";
        String endTime = currentTime + " 23:59:59";
        String e = makeE(bdcconn, beginTime, endTime);
        String f = makeF(bdcconn, beginTime, endTime);
        String g = makeG(bdcconn, beginTime, endTime);
        String h = makeH(bdcconn, beginTime, endTime);
        String j = makeJ(bdcconn, beginTime, endTime);
        String k = makeK(bdcconn, beginTime, endTime);
        String l = makeL(bdcconn, beginTime, endTime);
        String a = makeA(beginTime, endTime);
        String b = makeB(beginTime, endTime);
        String c = makeC(beginTime, endTime);
        String d = makeD(beginTime, endTime);
        String i = makeI(beginTime, endTime);
        String ch = (Integer.parseInt(c)-Integer.parseInt(h))+"";
        String content = "A:"+a+" B:"+b+" C:"+c+" D:"+d+" E:"+e+
                " F:"+f+" G:"+g+" H:"+h+" C-H:"+ch+" J:"+j+" K:"+k+"I:"+i
                +"剩余数量："+l;
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("CONTENT", content);
        msg.put("RECEIVER_MOB", "13799978657");
        msg.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        msg.put("SEND_STATUS", "0");
        this.dao.saveOrUpdate(msg, "T_MSJW_SYSTEM_MSGSEND", null);
        
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeI(String beginTime, String endTime) {
        StringBuffer isqlBuffer= new StringBuffer("");
        isqlBuffer.append("select count(t.record_id) as I from T_CKBS_NUMRECORD t ");
        isqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_id = t.depart_id  ");
        isqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d2 on d2.depart_id = d.parent_id  ");
        isqlBuffer.append("where d2.depart_name like '%不动产%'   ");
        isqlBuffer.append("and (t.create_time between ? and ? ) ");
        isqlBuffer.append("and t.call_status = 1 ");
        isqlBuffer.append("and t.exe_id is null ");
        Map<String, Object> iMap = dao.getByJdbc(isqlBuffer.toString(),
                new Object[] { beginTime,endTime });
        String i = iMap.get("I").toString();
        return i;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeD(String beginTime, String endTime) {
        StringBuffer dsqlBuffer= new StringBuffer("");
        dsqlBuffer.append("select count(t.record_id) as D from T_CKBS_NUMRECORD t ");
        dsqlBuffer.append("left join jbpm6_execution e on e.exe_id = t.exe_id ");
        dsqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_id = t.depart_id ");
        dsqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d2 on d2.depart_id = d.parent_id ");
        dsqlBuffer.append("where d2.depart_name like '%不动产%' ");
        dsqlBuffer.append("and (t.create_time between ? and ?) ");
        dsqlBuffer.append("and t.call_status = 1 ");
        dsqlBuffer.append("and d.depart_name not like '%查询%' ");
        dsqlBuffer.append("and t.exe_id is not null ");
        dsqlBuffer.append("and e.source is null ");
        Map<String, Object> dMap = dao.getByJdbc(dsqlBuffer.toString(),
                new Object[] { beginTime,endTime });
        String d = dMap.get("D").toString();
        return d;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeC(String beginTime, String endTime) {
        StringBuffer csqlBuffer= new StringBuffer("");
        csqlBuffer.append("select count(t.record_id) as C from T_CKBS_NUMRECORD t ");
        csqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_id = t.depart_id  ");
        csqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d2 on d2.depart_id = d.parent_id  ");
        csqlBuffer.append("where d2.depart_name like '%不动产%'   ");
        csqlBuffer.append("and (t.create_time between ? and ?)   ");
        csqlBuffer.append("and t.call_status = 1   ");
        csqlBuffer.append("and d.depart_name not like '%查询%'  ");
        Map<String, Object> cMap = dao.getByJdbc(csqlBuffer.toString(),
                new Object[] { beginTime,endTime });
        String c = cMap.get("C").toString();
        return c;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeB(String beginTime, String endTime) {
        StringBuffer bsqlBuffer= new StringBuffer("");
        bsqlBuffer.append("select count(t.record_id) as B from T_CKBS_NUMRECORD t ");
        bsqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_id = t.depart_id  ");
        bsqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d2 on d2.depart_id = d.parent_id  ");
        bsqlBuffer.append("where d2.depart_name like '%不动产%'   ");
        bsqlBuffer.append("and (t.create_time between ? and ?)   ");
        bsqlBuffer.append("and t.call_status = 1   ");
        bsqlBuffer.append("and d.depart_name like '%查询%'  ");
        Map<String, Object> bMap = dao.getByJdbc(bsqlBuffer.toString(),
                new Object[] { beginTime,endTime });
        String b = bMap.get("B").toString();
        return b;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeA(String beginTime, String endTime) {
        StringBuffer asqlBuffer= new StringBuffer("");
        asqlBuffer.append("select count(t.record_id) as A from T_CKBS_NUMRECORD t ");
        asqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_id = t.depart_id  ");
        asqlBuffer.append("left join T_MSJW_SYSTEM_DEPARTMENT d2 on d2.depart_id = d.parent_id  ");
        asqlBuffer.append("where d2.depart_name like '%不动产%'   ");
        asqlBuffer.append("and (t.create_time between ? and ?)   ");
        asqlBuffer.append("and t.call_status = 1   ");
        Map<String, Object> aMap = dao.getByJdbc(asqlBuffer.toString(),
                new Object[] { beginTime,endTime });
        String a = aMap.get("A").toString();
        return a;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeK(Connection bdcconn, String beginTime, String endTime) {
        StringBuffer ksqlBuffer= new StringBuffer("");
        ksqlBuffer.append("select count(*) as K from T_CWSYNCHDETAIL cw ");
        ksqlBuffer.append("left join T_CWSYNCH cc on cc.cwsynch_id = cw.cwsynch_id ");
        ksqlBuffer.append("where cw.cwsynch_id in ( ");
        ksqlBuffer.append("select c.cwsynch_id from T_CWSYNCH c where c.prosh in( ");
        ksqlBuffer.append("select t.prosh from T_CWSYNCH t where t.type = 10 )  ");
        ksqlBuffer.append("and c.type = 10)  ");
        ksqlBuffer.append("and cw.issuccess = 2 ");
        ksqlBuffer.append("and cc.createdate between to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        ksqlBuffer.append("and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        List<Map<String, Object>> kList = DbUtil.findBySql(
                bdcconn, ksqlBuffer.toString(), new Object[] {beginTime,endTime}, false);
        String k = kList.get(0).get("K").toString();
        return k;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeJ(Connection bdcconn, String beginTime, String endTime) {
        StringBuffer jsqlBuffer= new StringBuffer("");
        jsqlBuffer.append("select count(*) as J from T_CWSYNCHDETAIL cw ");
        jsqlBuffer.append("left join T_CWSYNCH cc on cc.cwsynch_id = cw.cwsynch_id ");
        jsqlBuffer.append("where cw.cwsynch_id in ( ");
        jsqlBuffer.append("select c.cwsynch_id from T_CWSYNCH c where c.prosh in( ");
        jsqlBuffer.append("select t.prosh from T_CWSYNCH t where t.type = 10 )  ");
        jsqlBuffer.append("and c.type = 10) ");
        jsqlBuffer.append("and cw.issuccess = 1 ");
        jsqlBuffer.append("and cc.createdate between to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        jsqlBuffer.append("and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        List<Map<String, Object>> jList = DbUtil.findBySql(
                bdcconn, jsqlBuffer.toString(), new Object[] {beginTime,endTime}, false);
        String j = jList.get(0).get("J").toString();
        return j;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeH(Connection bdcconn, String beginTime, String endTime) {
        StringBuffer hsqlBuffer= new StringBuffer("");
        hsqlBuffer.append("select count(*) as H from T_CWSYNCHDETAIL cw  ");
        hsqlBuffer.append("left join T_CWSYNCH cc on cc.cwsynch_id = cw.cwsynch_id  ");
        hsqlBuffer.append("where cw.cwsynch_id in ( ");
        hsqlBuffer.append("select c.cwsynch_id from T_CWSYNCH c where c.prosh in( ");
        hsqlBuffer.append("select t.prosh from T_CWSYNCH t where t.type = 10 )  ");
        hsqlBuffer.append("and c.type = 10)  ");
        hsqlBuffer.append("and cc.createdate between to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        hsqlBuffer.append("and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        List<Map<String, Object>> hList = DbUtil.findBySql(
                bdcconn, hsqlBuffer.toString(), new Object[] {beginTime,endTime}, false);
        String h = hList.get(0).get("H").toString();
        return h;
    }
    /**
     *
     * 描述
     * @author Water Guo
     * @created 2018-3-10 下午6:06:45
     * @param beginTime
     * @param endTime
     */
    private String makeG(Connection bdcconn, String beginTime, String endTime) {
        StringBuffer gsqlBuffer= new StringBuffer("");
        gsqlBuffer.append("select count(*) as G from T_CWSYNCHDETAIL cw  ");
        gsqlBuffer.append("left join T_CWSYNCH cc on cc.cwsynch_id = cw.cwsynch_id  ");
        gsqlBuffer.append("where cw.cwsynch_id in (  ");
        gsqlBuffer.append("select c.cwsynch_id from T_CWSYNCH c where c.prosh in(  ");
        gsqlBuffer.append("select t.prosh from T_CWSYNCH t where t.type = 10 ) )  ");
        gsqlBuffer.append("and cw.issuccess = 2  ");
        gsqlBuffer.append("and cc.createdate between to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        gsqlBuffer.append("and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        List<Map<String, Object>> gList = DbUtil.findBySql(
                bdcconn, gsqlBuffer.toString(), new Object[] {beginTime,endTime}, false);
        String g = gList.get(0).get("G").toString();
        return g;
    }
    private String makeF(Connection bdcconn, String beginTime, String endTime) {
        StringBuffer fsqlBuffer= new StringBuffer("");
        fsqlBuffer.append("select count(*) as F from T_CWSYNCHDETAIL cw ");
        fsqlBuffer.append("left join T_CWSYNCH cc on cc.cwsynch_id = cw.cwsynch_id ");
        fsqlBuffer.append("where cw.cwsynch_id in ( ");
        fsqlBuffer.append("select c.cwsynch_id from T_CWSYNCH c where c.prosh in( ");
        fsqlBuffer.append("select t.prosh from T_CWSYNCH t where t.type = 10 ) ) ");
        fsqlBuffer.append("and cw.issuccess = 1 ");
        fsqlBuffer.append("and cc.createdate between to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        fsqlBuffer.append("and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        List<Map<String, Object>> fList = DbUtil.findBySql(
                bdcconn, fsqlBuffer.toString(), new Object[] {beginTime,endTime}, false);
        String f = fList.get(0).get("F").toString();
        return f;
    }
    private String makeL(Connection bdcconn, String beginTime, String endTime) {
        StringBuffer lsqlBuffer= new StringBuffer("");
        lsqlBuffer.append("select count(*) as L from T_CWSYNCH t where t.issuccess = 0  ");
        lsqlBuffer.append("and t.createdate between to_date(?,'yyyy-mm-dd hh24:mi:ss')  ");
        lsqlBuffer.append("and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        List<Map<String, Object>> lList = DbUtil.findBySql(
                bdcconn, lsqlBuffer.toString(), new Object[] {beginTime,endTime}, false);
        String l = lList.get(0).get("L").toString();
        return l;
    }
    private String makeE(Connection bdcconn, String beginTime, String endTime) {
        StringBuffer esqlBuffer= new StringBuffer("");
        esqlBuffer.append("select count(*) as E from T_CWSYNCHDETAIL cw ");
        esqlBuffer.append("left join T_CWSYNCH cc on cc.cwsynch_id = cw.cwsynch_id ");
        esqlBuffer.append("where cw.cwsynch_id in ( ");
        esqlBuffer.append("select c.cwsynch_id from T_CWSYNCH c where c.prosh in( ");
        esqlBuffer.append("select t.prosh from T_CWSYNCH t where t.type = 10 ) ) ");
        esqlBuffer.append("and cc.createdate between to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        esqlBuffer.append("and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
        List<Map<String, Object>> eList = DbUtil.findBySql(
                bdcconn, esqlBuffer.toString(), new Object[] {beginTime,endTime}, false);
        String e = eList.get(0).get("E").toString();
        return e;
    }
    /**
     * 
     * 描述 属性bdcDbUrl get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getBdcDbUrl() {
        if (bdcDbUrl == null || StringUtils.isEmpty(this.bdcDbUrl))
            this.bdcDbUrl = this.dictionaryService.getDicNames("BDCSJTBXGPZ", "BDC_DBURL");
        return bdcDbUrl;
    }

    /**
     * 
     * 描述 属性bdcDbUrl set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setBdcDbUrl(String bdcDbUrl) {
        this.bdcDbUrl = bdcDbUrl;
    }

    /**
     * 
     * 描述 属性bdcDbUsername get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getBdcDbUsername() {
        if (bdcDbUsername == null || StringUtils.isEmpty(this.bdcDbUsername))
            this.bdcDbUsername = this.dictionaryService.getDicNames("BDCSJTBXGPZ", "BDC_DBUSERNAME");
        return this.bdcDbUsername;
    }

    /**
     * 
     * 描述 属性bdcDbUsername set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setBdcDbUsername(String bdcDbUsername) {
        this.bdcDbUsername = bdcDbUsername;
    }

    /**
     * 
     * 描述 属性bdcDbPassword get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getBdcDbPassword() {
        if (bdcDbPassword == null || StringUtils.isEmpty(this.bdcDbPassword))
            this.bdcDbPassword = this.dictionaryService.getDicNames("BDCSJTBXGPZ", "BDC_DBPASSWORD");
        return bdcDbPassword;
    }

    /**
     * 
     * 描述 属性bdcDbPassword set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setBdcDbPassword(String bdcDbPassword) {
        this.bdcDbPassword = bdcDbPassword;
    }
}
