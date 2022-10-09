/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.rmi.RemoteException;
import java.util.*;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hflow.controller.MaterConfigController;
import net.evecom.platform.wsbs.dao.BspjDao;
import net.evecom.platform.wsbs.service.BspjService;

import net.evecom.platform.wsbs.service.EvaluationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

/**
 * 描述 办事评价操作service
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("bspjService")
public class BspjServiceImpl extends BaseServiceImpl implements BspjService {
    /**
     * 所引入的dao
     */
    @Resource
    private BspjDao dao;

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);

    /**
     * 所引入的webservice
     */

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * webservice账号
     */
    private final String SERVICE_USERNAME = "350128";

    /**
     * webservice密码
     */
    private final String SERVICE_PASSWORD  = "pt_eva_2019";

    /**
     * 
     * 描述 获取前台用户中心我的评价列表
     * 
     * @author Faker Li
     * @created 2015年12月4日 上午10:50:37
     * @param page
     * @param rows
     * @return
     * @see net.evecom.platform.wsbs.service.BspjService#findfrontList(java.lang.String,
     *      java.lang.String)
     */
    public Map<String, Object> findfrontList(String page, String rows) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        params.add(AppUtil.getLoginMember().get("YHZH"));
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("select E.ITEM_NAME,T.EXE_ID,T.PJNR,T.SFMY,");
        sql.append(" T.CREATE_TIME from T_WSBS_BSPJ T  JOIN JBPM6_EXECUTION E ON T.EXE_ID = E.EXE_ID ");
        sql.append(" WHERE T.YHZH = ? ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    @Override
    public Map<String, Object> findfrontList(String page, String rows, String itemCode) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        params.add(itemCode);
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.PJNR,T.SFMY,T.CREATE_TIME,T1.YHMC ");
        sql.append(" FROM T_WSBS_BSPJ T  JOIN T_BSFW_USERINFO T1 ON T.YHZH = T1.YHZH ");
        sql.append(" WHERE T.FWSXBM = ? ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer  sql=new StringBuffer("select T.*,S.ITEM_NAME　from  T_WSBS_BSPJ T LEFT ");
        sql.append(" JOIN T_WSBS_SERVICEITEM S ON T.FWSXBM=S.ITEM_CODE WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 描述:获取省网办件评价信息
     *
     * @author Madison You
     * @created 2019/10/21 12:47:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> findSwpjData
    (String businessCode, String projectNo, String nodeName, String visitFrom)
            throws ServiceException, RemoteException {
        EvaluationSubServiceImpl service = new EvaluationSubServiceImpl();
        EvaluationService asmx = service.getEvaluationServiceAsmx();
        StringBuffer xmlStr = new StringBuffer();
        xmlStr.append("<?xml version=\"1.0\" encoding=\"GB2312\"?><Case><businessCode>");
        xmlStr.append(businessCode);
        xmlStr.append("</businessCode><projectNo>");
        xmlStr.append(projectNo);
        xmlStr.append("</projectNo><nodeName>");
        xmlStr.append(nodeName);
        xmlStr.append("</nodeName><visitFrom>");
        xmlStr.append(visitFrom);
        xmlStr.append("</visitFrom></Case>");
        String result = asmx.getEvaluationDetail(SERVICE_USERNAME, SERVICE_PASSWORD, xmlStr.toString());
        return parseXmlStr(result);
    }

    /**
     * 描述:获取省网办件评价链接
     *
     * @author Madison You
     * @created 2019/10/21 12:48:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object>
    findSwpjLink(String businessCode, String projectNo,
                 String nodeName, String pf, String type) throws RemoteException, ServiceException {
        EvaluationSubServiceImpl service = new EvaluationSubServiceImpl();
        EvaluationService asmx = service.getEvaluationServiceAsmx();
        StringBuffer xmlStr = new StringBuffer();
        xmlStr.append("<?xml version=\"1.0\" encoding=\"GB2312\"?><Case><businessCode>");
        xmlStr.append(businessCode);
        xmlStr.append("</businessCode><projectNo>");
        xmlStr.append(projectNo);
        xmlStr.append("</projectNo><nodeName>");
        xmlStr.append(nodeName);
        xmlStr.append("</nodeName><pf>");
        xmlStr.append(pf);
        xmlStr.append("</pf><type>");
        xmlStr.append(type);
        xmlStr.append("</type></Case>");
        /*webService生成的方法*/
        String result = asmx.getSaveEvaluationUrl(SERVICE_USERNAME, SERVICE_PASSWORD, xmlStr.toString());
        return parseXmlStr(result);
    }

    /**
     * 描述:解析省网传来的xml信息
     *
     * @author Madison You
     * @created 2019/10/22 8:34:00
     * @param
     * @return
     */
    private Map<String,Object> parseXmlStr(String result){
        Map<String, Object> resultMap = new HashMap<>();
        Document document = null;
        try {
            document = DocumentHelper.parseText(result);
            Element rootElement = document.getRootElement();
            Iterator code = rootElement.elementIterator("code");
            while (code.hasNext()) {
                Element codeNext =(Element) code.next();
                resultMap.put("code", codeNext.getStringValue());
            }
            Iterator message = rootElement.elementIterator("message");
            while (message.hasNext()) {
                Element nextMessage = (Element) message.next();
                resultMap.put("message", nextMessage.getStringValue());
            }
            Iterator nextData = rootElement.elementIterator("data");
            while (nextData.hasNext()) {
                Element next = (Element) nextData.next();
                Iterator nextUrl = next.elementIterator("url");
                while (nextUrl.hasNext()) {
                    Element link =(Element) nextUrl.next();
                    resultMap.put("url", link.getStringValue());
                }
            }
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
        return resultMap;
    }

}
