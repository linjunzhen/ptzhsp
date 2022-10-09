<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<div class="eui-card eui-declaration wysb1 on" >

    <!-- 类型 -->
    <div class="eui-tit round">
        <b>类型</b>
    </div>
    <div class="eui-filter i1 eui-flex wrap">
        <e:for var="efor" dsid="237" filterid="1" end="16"  >
                        <span data-title="${efor.TYPE_ID}"  onclick="typeClickOfSelectServiceItem('${efor.TYPE_ID}')" <c:if test="${efor.TYPE_ID==typeId}"> class="on"</c:if>>   ${efor.TYPE_NAME}
                        </span>
        </e:for>
    </div>
    <!-- 阶段 -->
    <div class="eui-tit round">
        <b>阶段</b>
    </div>
    <div class="eui-filter i2 eui-flex wrap">
        <e:for var="efor" dsid="1004" filterid="${typeId}" end="6" >
                        <span  data-title="${efor.STAGE_ID}" onclick="stateClickOfSelectServiceItem('${efor.STAGE_ID}')" <c:if test="${efor.STAGE_ID==stageId}"> class="on" </c:if> >
                          ${efor.NAME}阶段
                        </span>
        </e:for>
    </div>

    <!-- 主题 -->
    <div class="eui-tit round">
        <b>主题</b>
    </div>
    <div class="eui-filter i2 eui-flex wrap">
        <e:for var="efor" dsid="40" filterid="TOPICNAME" end="10" >
                        <span  onclick="topicClickOfSelectServiceItem('${efor.DIC_CODE}')"  <c:if test="${efor.DIC_CODE==topicCode}"> class="on" </c:if> >
                          ${efor.DIC_NAME}
                        </span>
        </e:for>
    </div>

    <!-- 事项列表 -->
    <div class="eui-tit round">
        <b>事项列表</b>
    </div>
    <div class="eui-flex tc eui-search" style="margin-top: 0;">
        <input class="ipt" type="text"  name="ITEM_NAME_SELECTSERVICEITEM" placeholder="请输入事项名称">
        <a class="eui-btn" href="javascript:searchItemOfSelectServiceItem()">搜 索</a>
    </div>
    <div class="eui-sx-list">
        <table>
            <thead>
            <tr>
                <th>
                    <%--                                    <input class="eui-checkbox " name="itemNames" type="checkbox"  value="1" onchange="checkBoxItemNames(this.value)"/>--%>
                </th>
                <td>事项名称</td>
                <th>服务</th>
            </tr>
            </thead>
            <tbody id="listItemtbody">
<%--            <e:for var="efor" dsid="1000" filterid="${stageId}" end="100">--%>
<%--                <tr class="${efor.ITEM_CODE}tr">--%>

<%--                    <th><input class="eui-checkbox " name="itemName"  onclick="setItemCodesSelected()" id="checkBox${efor.ITEM_CODE}"--%>
<%--                            <c:if test="${efor.IS_KEY_ITEM=='1'}"> checked="checked"  data-title="1"   disabled="disabled"</c:if>--%>
<%--                               value="${efor.ITEM_CODE}" type="checkbox" title="${efor.ITEM_NAME}" /></th>--%>

<%--                    <td>${efor.ITEM_NAME}--%>
<%--                        <c:if test="${efor.IS_KEY_ITEM=='1'}">--%>
<%--                            <span>必经事项</span>--%>
<%--                        </c:if>--%>
<%--                    </td>--%>
<%--                    <th><a href="serviceItemController/bsznDetail.do?itemCode=${efor.ITEM_CODE}&projectType=1">办事指南</a></th>--%>
<%--                </tr>--%>
<%--            </e:for>--%>


            </tbody>
        </table>
    </div>
    <div class="eui-flex bt vc">
        <div class="eui-flex vc"><strong>已选事项：</strong>
            <div class="eui-sx-selected">
                <b id="toolBarNum">0</b>
                <div class="eui-sx-popup">
                    <div class="tit eui-flex bt">
                        <span>已选事项</span>
                        <span class="close">×</span>
                    </div>
                    <div class="con eui-table selectedItemName" id="selectedItemName">

                    </div>
                </div>
            </div>
        </div>
        <div class="eui-flex tc eui-sx-btn">
            <c:if test="${isQueryDetail!='true'}">
            <a class="eui-btn o"  onclick="tempSaveWebSiteFlowForm('T_GCXM_XMJBXX_FORM')">保存草稿</a>
            </c:if>
            <a id="goto2" class="eui-btn" href="javascript: goto2()">下一步</a>
        </div>
    </div>


</div>