/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.star.util.DateTime;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.hd.service.ConsultService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.weixin.dao.WHomeDao;
import net.evecom.platform.weixin.resp.Article;
import net.evecom.platform.weixin.resp.Image;
import net.evecom.platform.weixin.resp.ImageMessageResp;
import net.evecom.platform.weixin.resp.NewsMessageResp;
import net.evecom.platform.weixin.resp.TextMessageResp;
import net.evecom.platform.weixin.resp.Video;
import net.evecom.platform.weixin.resp.VideoMessageResp;
import net.evecom.platform.weixin.resp.Voice;
import net.evecom.platform.weixin.resp.VoiceMessageResp;
import net.evecom.platform.weixin.service.UserBindService;
import net.evecom.platform.weixin.service.WHomeService;
import net.evecom.platform.weixin.util.MessageUtil;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Service("whomeService")
public class WHomeServiceImpl extends BaseServiceImpl implements WHomeService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WHomeServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private WHomeDao dao;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private UserInfoService userInfoService;
    /**
     * 引入Service
     */
    @Resource
    private UserBindService userBindService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 获取相应
     */
    @Override
    public String getWechatPost(HttpServletRequest request,Map<String, String> requestMap) {
        String respMessage = null;
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String serviceUrl = properties.getProperty("serviceUrl");
        try {
            String respContent = "请求处理异常，请稍候尝试！";
            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");// 公众帐号
            String msgType = requestMap.get("MsgType");// 消息类型
            //默认返回的文本消息
            TextMessageResp textMessage = new TextMessageResp();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            textMessage.setContent("欢迎访问/微笑 /::)!");
            respMessage = MessageUtil.textMessageToXml(textMessage);// 将文本消息对象转换成xml字符串
            /******************* 1.文本消息 *********************/
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content"); // 接收用户发送的文本消息内容
                // 创建图文消息
                NewsMessageResp newsMessage = new NewsMessageResp();
                newsMessage.setToUserName(fromUserName);
                newsMessage.setFromUserName(toUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                newsMessage.setFuncFlag(0);
                List<Article> articleList = new ArrayList<Article>();
                if (MessageUtil.isQqFace(content)) {// 判断用户发送的是否是单个QQ表情
                    textMessage.setContent(content);// 用户发什么QQ表情，就返回什么QQ表情
                    respMessage = MessageUtil.textMessageToXml(textMessage);// 将文本消息对象转换成xml字符串
                }
                List<Map<String, Object>> list = serviceItemService.findZNListByName(content, "0");
                if (list.size() > 0) {
                    articleList=setBSGuide(list,serviceUrl,fromUserName,articleList);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                } else {
                    respContent = "对不起，无对应办事指南信息!";
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                return respMessage;
            }
            /*************** 3.事件推送 ***************/
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 首次关注，订阅
                    String url = serviceUrl+"/userBindController.do?toBind&openID="+ fromUserName;
                    respContent = "欢迎使用“微平潭”办事服务平台，您还未绑定平潭行政服务中心官网账号"
                            + "，请<a href=\"" + url + "\">点这里</a>进行绑定！";
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String url= serviceUrl+"/userBindController.do?toBind&openID="+ fromUserName;
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    List<Article> articleList = new ArrayList<Article>();
                    String eventKey = requestMap.get("EventKey");
                    if (eventKey.equals("01_SERCENTER")) { respContent = "用户注册被点击！";
                    }else if (eventKey.equals("02_WORKQUERY")) {
                        respContent = "我的办件被点击！";
                        // 创建图文消息
                        NewsMessageResp newsMessage = new NewsMessageResp();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setFuncFlag(0);
                        Map<String, Object> bindInfo = userBindService
                                .getByJdbc("T_WX_USERBIND",new String[] { "OPEN_ID" },new Object[] { fromUserName });
                        if (bindInfo != null) {
                            Map<String, Object> user = userInfoService.getByJdbc("T_BSFW_USERINFO",
                                    new String[] { "YHZH" }, new Object[] { bindInfo.get("USER_NAME") });
                            String state=(String) user.get("YHZT");//获取用户状态
                            if(state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                                respContent = "抱歉该账号绑定用户已被禁用!";
                            }else{
                           List<Map<String, Object>> list =executionService.findWXWdbjList((String)user.get("YHZH"));
                                if (list.size() > 0) {
                                    articleList=setMyBanJian(list,serviceUrl,fromUserName,articleList);
                                    newsMessage.setArticleCount(articleList.size());
                                    newsMessage.setArticles(articleList);
                                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                                    return respMessage;
                                } else {
                                    respContent = "对不起，您尚未提交办理任何事项";
                                }
                            }
                        } else {
                            respContent = "欢迎使用“微平潭”办事服务平台，您还未绑定平潭行政服务中心官网账号"
                                    + "，请<a href=\"" + url + "\">点这里</a>进行绑定！";
                        }
                    } 
                }
            }
            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return respMessage;
    }

    /**
     * 处理办事指南
     * @param list
     * @return
     */
    public List setBSGuide(List<Map<String, Object>> list,String serviceUrl,
            String fromUserName,List<Article> articleList){
        int size = list.size() > 9 ? 9 : list.size();// 图文消息的个数限制为10
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = list.get(i);
            Article article = new Article();
            article.setDescription("点击查看更多");
            if (i == 0) {
                article.setTitle("点击查看更多");
                article.setPicUrl(serviceUrl+ "/webpage/weixin/images/viewmore.png");
                article.setUrl(serviceUrl+ "/busInteractController.do?bsGuide");
                articleList.add(article);
                Article article1 = new Article();
                article1.setDescription("点击查看更多");
                article1.setTitle((String) map.get("ITEM_NAME"));
                article1.setPicUrl(serviceUrl+ "/webpage/weixin/images/bsguide.png");
                article1.setUrl(serviceUrl + "/busInteractController.do?bsguideInfo&itemCode="
                        + map.get("ITEM_CODE"));
                articleList.add(article1);
            } else {
                article.setTitle((String) map.get("ITEM_NAME"));
                article.setPicUrl(serviceUrl
                        + "/webpage/weixin/images/bsguide.png");
                article.setUrl(serviceUrl
                        + "/busInteractController.do?bsguideInfo&itemCode="+ map.get("ITEM_CODE"));
                articleList.add(article);
            }
        }
        return articleList;
    }
    /**
     * 处理我的办件
     * @param list
     * @return
     */
    public List setMyBanJian(List<Map<String, Object>> list,String serviceUrl,
            String fromUserName,List<Article> articleList){
        int size = list.size() > 8 ? 8 : list.size();// 图文消息的个数限制为8
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = list.get(i);
            Article article = new Article();
            article.setTitle((String) map.get("ITEM_NAME"));
            if (i == 0) {
                article.setPicUrl(serviceUrl+ "/webpage/weixin/images/bsquery-more.png");
            } else {
                article.setPicUrl(serviceUrl+ "/webpage/weixin/images/query.png");
            }
            article.setUrl(serviceUrl
                    + "/busInteractController.do?toRateQuery&openID="+ fromUserName
                    +"&exeId="+map.get("EXE_ID"));
            articleList.add(article);
        }
        return articleList;
    }
    @Override
    public String getNoBindTextResp(String toUser) {
        Properties properties = FileUtil
                .readProperties("conf/config.properties");
        String appId = properties.getProperty("APP_ID");
        String serviceUrl = properties.getProperty("serviceUrl");
        String url = serviceUrl + "/userBindController.do?toBind&openID="
                + toUser;
        String content = "对不起，您还未绑定平潭行政服务中心官网账号，请<a href=\"" + url
                + "\">点这里</a>进行绑定！";
        String respMessage = null;
        // 回复文本消息
        TextMessageResp textMessage = new TextMessageResp();
        textMessage.setToUserName(toUser);
        textMessage.setFromUserName(appId);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);
        // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
        textMessage.setContent(content);
        // 将文本消息对象转换成xml字符串
        respMessage = MessageUtil.textMessageToXml(textMessage);
        return respMessage;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 17:31:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getWxBookDepartList() {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("select b.depart_id,d.depart_name from ");
        sql.append("(select t.depart_id from T_CKBS_BUSINESSDATA t where t.service_status=1 group by t.depart_id) b ");
        sql.append("left join t_msjw_system_department d on d.depart_id = b.depart_id ");
        sql.append("order by d.tree_sn ");
        List list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 17:53:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getWxBookBusinessChooseList(HttpServletRequest request) {
        String departId = request.getParameter("departId");
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,d.depart_name from T_CKBS_BUSINESSDATA t ");
        sql.append("left join t_msjw_system_department d on d.depart_id=t.depart_id ");
        sql.append("where t.depart_id=? and t.service_status=1 and t.is_appoint=1 ");
        List list = dao.findBySql(sql.toString(), new String[]{departId}, null);
        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/7/30 18:25:00
     * @param 
     * @return 
     */
    @Override
    public List<Map<String, Object>> getWxBookTimeList(HttpServletRequest request) {
        String cardNo = request.getParameter("cardNo");
        //String userName = request.getParameter("userName");
        // 获取可提前预约的天数
        String tq = dictionaryService.getDicCode("wsyyktqyygzr", "可提前预约工作日");
        // 获取可预约日期（提前5个工作日）
        String wdsql = "select * from (select t.* from t_msjw_system_workday t " +
                "where t.w_date>to_char(sysdate, 'yyyy-MM-dd' ) and t.w_setid=2 " +
                "order by t.w_date asc ) wd where rownum<=?";
        List<Map<String, Object>> wdlist = dao.findBySql(wdsql, new String[]{tq}, null);
        // 根据id获取可预约时段配置信息
        String departId = request.getParameter("departId");
        String kyysql = "select * from T_CKBS_APPOINTMENT_SET t where t.depart_id=? order by t.begin_time";
        List<Map<String, Object>> kyylist = dao.findBySql(kyysql, new String[]{departId}, null);
        // 获取已预约数据
        List list = new ArrayList();
        for (Map wdmap : wdlist) {
            Map<String, Object> allmap = new HashMap<String, Object>();
            allmap.putAll(wdmap);
            List<Map<String, Object>> bespeaklist = new ArrayList<Map<String, Object>>();
            for (Map kyymap : kyylist) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.putAll(kyymap);
                StringBuffer yyysql = new StringBuffer();
                yyysql.append("select t.* from T_CKBS_APPOINTMENT_APPLY t ");
                yyysql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
                yyysql.append("where t.date_time = ? and t.begin_time=? and t.end_time=? and b.depart_id=? ");
                List<Map<String, Object>> yyylist = dao.findBySql(yyysql.toString(),
                        new String[]{wdmap.get("W_DATE").toString(), kyymap.get("BEGIN_TIME").toString(),
                                kyymap.get("END_TIME").toString(), departId}, null);
                map.put("bespeakNumber", yyylist.size());
                Date nowTime = new Date();
                long time = DateTimeUtil.getIntervalTime(DateTimeUtil.getStrOfDate(nowTime, "yyyy-MM-dd HH:mm"), wdmap
                        .get("W_DATE").toString() + " " + kyymap.get("BEGIN_TIME").toString(), "yyyy-MM-dd HH:mm", 3);
                // 小于十分钟不可预约，大于十分钟可预约，也可取消预约0：不可预约，1：可预约，2：取消预约
                if (time < 10) {
                    map.put("isOverTime", "0");
                } else {
                    map.put("isOverTime", "1");
                    for (Map yyymap : yyylist) {
                        Map<String, Object> tempmap = new HashMap<String, Object>();
                        tempmap.putAll(yyymap);
                        if (!StringUtils.isEmpty(cardNo) && yyymap.get("CARD").equals(cardNo)
                                && yyymap.get("STATUS").equals("1")) {
                            map.put("isOverTime", 2);
                            map.put("applyId", tempmap.get("RECORD_ID"));
                            break;
                        }
                    }
                }
                log.info(map);
                bespeaklist.add(map);
            }
            allmap.put("bespeaklist", bespeaklist);
            list.add(allmap);
        }
        return list;
    }

    /**
     * 描述:获取已经预约的列表
     *
     * @author Madison You
     * @created 2020/7/31 14:18:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getWxBookAppointList(Map<String,Object> loginMember) {
        String zjhm = (String) loginMember.get("ZJHM");
        Sm4Utils sm4Utils = new Sm4Utils();
        List<Map<String, Object>> list = null;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(zjhm)) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select t.record_id,t.date_time,t.begin_time,t.end_time,x.business_name,t.is_take,t.status ");
            sql.append(" from t_ckbs_appointment_apply t left join t_ckbs_businessdata x ");
            sql.append(" on t.business_code = x.business_code where t.card = ? ");
            sql.append(" order by t.create_time desc ");
            list = dao.findBySql(sql.toString(), new Object[]{sm4Utils.encryptDataCBC(zjhm)}, null);
            if (list != null && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    String dateTime = (String) map.get("DATE_TIME");
                    Date dateTimeD = DateTimeUtil.getDateOfStr(dateTime, "yyyy-MM-dd");
                    Date now = DateTimeUtil.getDateOfStr(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd");
                    boolean before = dateTimeD.before(now);
                    if (before) {
                        map.put("IS_OVERTIME", "1");
                    } else {
                        map.put("IS_OVERTIME", "0");
                    }
                }
            }
        }
        return list;
    }

}
