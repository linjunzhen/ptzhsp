<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String nowDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
    request.setAttribute("nowDate", nowDate);
    String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    request.setAttribute("nowTime", nowTime);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/common/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css">
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/dyqscdjzb/js/bdcdyqscdjzb.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>

    <script type="text/javascript">
        $(function () {
            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate = true;

            var userType = '${sessionScope.curLoginMember.USER_TYPE}';
            initUserForm(userType);
            $("input[name='YH_XWJL3']").attr("class", "");
            $("input[name='YH_XWJL4']").attr("class", "");

            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
            if (EFLOW_FLOWOBJ) {
                var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
                if (flowSubmitObj.busRecord) {
                    initAutoTable(flowSubmitObj); //初始化权利信息
                    //初始化权利人性质
                    setTakecardType(flowSubmitObj.busRecord.TAKECARD_TYPE);
                    dyqrChangeFun(flowSubmitObj.busRecord.DYQRXX_NATURE, false);
                    setSfzgedy(flowSubmitObj.busRecord.DYQDJ_SFZGEDY);
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_DYQSCDJZB_FORM");
                    if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='开始'){
					      //抵押权人信息-证件类型开放可编辑       
					      $("select[name='DYQRXX_IDTYPE']").removeAttr("disabled");
					}
                } else {
                    $("input[name='SBMC']").val($("input[name='SQRMC']").val() + "-" + '${serviceItem.ITEM_NAME}');
                    //抵押权登记模块中，是否最高额抵押值为否（默认选项），抵押方式值为一般抵押，被担保主债权数额为可编辑状态，最高债权额为不可编辑状态，抵押宗数默认值为1
                    $("select[name='DYQDJ_SFZGEDY']").val("0");
                    $("select[name='DYQDJ_DYFS']").val("1");
                    $("input[name='DYQDJ_DYZS']").val("1");
                    $("input[name='DYQDJ_ZGZQE']").attr("disabled", true);
                    //5、登记原因默认“首次登记”。
                    $("input[name='DYQDJ_DJYY']").val("首次登记");
                    //6、法定代表人、代理人、抵押人的证件类型默认为“身份证”可修改。
                    $("select[name='DYQRXX_LEGAL_IDTYPE']").val("身份证");
                    //$("input[name='DYQRXX_LEGAL_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQRXX_AGENT_IDTYPE']").val("身份证");
                    //$("input[name='DYQRXX_AGENT_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQDJ_IDTYPE']").val("身份证");
                    //$("input[name='DYQDJ_IDNO']").addClass(",custom[vidcard]");
                    $("select[name='DYQDJ_AGENT_IDTYPE']").val("身份证");
                    //$("input[name='DYQDJ_AGENT_IDNO']").addClass(",custom[vidcard]");

                    //抵押不动产类型默认为'土地和房屋'
                    $("select[name='DYBDCLX']").val("2");
                    $("input[name='DYSW']").val(1);
                    openSelector();
                }
            }

            /*初始化材料*/
            var fileids=$('input[name="RESULT_FILE_ID"]').val();
            $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
                if(resultJson!="websessiontimeout"){
                    $("#fileListDiv").html("");
                    var newhtml = "";
                    var list=resultJson.rows;
                    for(var i=0; i<list.length; i++){
                        newhtml+="<p style='margin-left: 5px; margin-right: 5px;line-height: 20px;' id='"+list[i].FILE_ID+"' >";
                        newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                        newhtml+=list[i].FILE_NAME+'</a>';
                        newhtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a>";
                        newhtml+='</p>';

                    }
                    $("#fileListDiv").html(newhtml);
                }
            });
        })
    </script>
    <style>
        /* .tab_text{
            width: 270px;
            height: 24px;
            line-height: 24px;
            padding: 2px 3px 2px 1px;
            border: 1px solid #bbb;
        } */
    </style>
</head>

<body style="background: #f0f0f0;">
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if>

	<div class="eui-main">
        <jsp:include page="./formtitle.jsp" />
        <form id="T_BDCQLC_DYQSCDJZB_FORM" method="post">
            <%--开始引入公共隐藏域部分 --%>
            <jsp:include page="commonhidden.jsp" />
            <%--结束引入公共隐藏域部分 --%>

            <input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}" />
            <input type="hidden" name="USER_TYPE" value="${sessionScope.curLoginMember.USER_TYPE}" />
            <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
            <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
            <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
            <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
            <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
            
            <input type="hidden" name="ISQCWB" />
                <input type="hidden" name="SQFS" value="1" />
            <%--权属来源--%>
            <input type="hidden" name="QSLY_JSON" />
            <%--抵押明细--%>
            <input type="hidden" name="DYMX_JSON" />
            <input type="hidden" name="RESULT_FILE_URL">
            <input type="hidden" name="RESULT_FILE_ID">

            <%--===================重要的隐藏域内容=========== --%>

            <%--开始编写基本信息 --%>
            <div class="bsbox clearfix">
                <div class="bsboxT">
                    <ul>
                        <li class="on" style="background:none"><span>基本信息</span></li>
                    </ul>
                </div>
                <div class="bsboxC">
                    <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
                        <tr>
                            <th> 审批事项</th>
                            <td colspan="3">${serviceItem.ITEM_NAME}</td>
                        </tr>
                    </table>
                    <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;margin-top:-1px;">

                        <tr>
                            <th> 所属部门</th>
                            <td style="border-right-style: none;">${serviceItem.SSBMMC}</td>
                            <th colspan="1" style="background-color: white;border-left-style: none;border-right-style: none;"></th>
                            <td style="border-left-style: none;"></td>
                        </tr>
                        <tr>
                            <th> 审批类型</th>
                            <td>${serviceItem.SXLX}</td>
                            <th> 承诺时间</th>
                            <td>${serviceItem.CNQXGZR}</td>
                        </tr>
                        <tr>
                            <th><span class="bscolor1">*</span> 申报名称</th>
                            <td colspan="3"><input type="text" class="tab_text validate[required]"
                                                   style="width: 50%" value="${sessionScope.curLoginMember.YHMC}-${serviceItem.ITEM_NAME}"
                                                   name="SBMC" maxlength="120"/>
                                <span class="bscolor1"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
                        </tr>
                    </table>
                </div>
            </div>
            <%--结束编写基本信息 --%>

            <%--开始引入受理信息--%>
            <jsp:include page="./bdcqlc/dyqscdjzb/T_BDCDYSSCDJ_ACCEPTINFO.jsp" />
            <%--结束引入受理信息--%>

            <%--开始引入权属来源信息--%>
            <jsp:include page="./bdcqlc/dyqscdjzb/applyQsly.jsp" />
            <%--结束引入权属来源信息--%>

            <%--开始引入抵押权信息--%>
            <jsp:include page="./bdcqlc/dyqscdjzb/T_BDCDYSSCDJ_PLEDGEEINFO.jsp" />
            <%--结束引入抵押权信息--%>

            <%--开始引入抵押明细信息--%>
            <jsp:include page="./bdcqlc/dyqscdjzb/applyDymx.jsp" />
            <%--结束引入抵押明细信息--%>

            <%--开始银行询问记录基本信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/yhXwjl.jsp" />
            <%--开始银行询问记录基本信息--%>

            <div class="bsbox clearfix">
                <div class="bsboxT">
                    <ul>
                        <li class="on" style="background:none"><span>所需材料</span></li>
                    </ul>
                </div>
                <jsp:include page="../../bsdt/applyform/applyMaterList.jsp">
                    <jsp:param value="1" name="applyType" />
                    <jsp:param value="1" name="isWebsite" />
                </jsp:include>
            </div>

            <%--引入申报对象信息--%>
            <jsp:include page="./applyuserinfo.jsp" />
            <%--引入申报对象信息--%>

            <%--开始引入提交DIV界面 --%>
            <jsp:include page="./submitdiv.jsp" >
                <jsp:param value="submitFlow('T_BDCQLC_DYQSCDJZB_FORM');" name="submitFn"/>
                <jsp:param value="saveFlow('T_BDCQLC_DYQSCDJZB_FORM');" name="tempSaveFn"/>
            </jsp:include>
            <%--结束引入提交DIV界面 --%>

        </form>
    </div>

	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
