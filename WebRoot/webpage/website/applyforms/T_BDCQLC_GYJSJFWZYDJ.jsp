<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
	<!--新ui-->
	<link href="<%=path%>/webpage/website/newui/css/public.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
    <eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
    <script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css">
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/js/bdcUtil.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/gyjsjfwzydj/js/gyjsjfwzydj.js"></script>
    <script type="text/javascript" src="<%=basePath%>/webpage/website/applyforms/bdcqlc/js/bdcEmsSend.js"></script>

</head>

<style>
    /* .tab_text{
        width: 270px;
        height: 24px;
        line-height: 24px;
        padding: 2px 3px 2px 1px;
        border: 1px solid #bbb;
    } */
</style>

<script>
    $(function () {
        //初始化验证引擎的配置
        $.validationEngine.defaults.autoHidePrompt = true;
        $.validationEngine.defaults.promptPosition = "topRight";
        $.validationEngine.defaults.autoHideDelay = "2000";
        $.validationEngine.defaults.fadeDuration = "0.5";
        $.validationEngine.defaults.autoPositionUpdate = true;

        initEasyUiForm();
        disabledQslyForm();
        var userType = '${sessionScope.curLoginMember.USER_TYPE}';
        initUserForm(userType);
        initNormalForm();
        delChildItem();
        //获取流程信息对象JSON
        var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
        if (EFLOW_FLOWOBJ) {
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
            if (flowSubmitObj.busRecord) {
                FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_GYJSJFWZYDJ_FORM");
                initAutoTable(flowSubmitObj);//初始化权利信息
                queryTypeFn();
                
              	//初始化电力水力燃气过户信息
                checkIsPowTransfer(flowSubmitObj.busRecord.IS_POWTRANSFER);
    			checkIsWaterTransfer(flowSubmitObj.busRecord.IS_WATERTRANSFER);
    			checkIsGasTransfer(flowSubmitObj.busRecord.IS_GASTRANSFER);
                /*规划用途和用途说明须要联动，用途说明的内容和规划用途保持一致*/
                if (flowSubmitObj.busRecord.ZD_TDYT) {
                    $("#ZD_TDYT").combobox("setValues", flowSubmitObj.busRecord.ZD_TDYT.split(","));
                }
                if (flowSubmitObj.busRecord.ZDZL_XIAN) {
                    $("#ZDZL_XIAN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_XIAN);
                }
                if (flowSubmitObj.busRecord.ZDZL_ZHENG) {
                    $("#ZDZL_ZHENG").combobox("setValue", flowSubmitObj.busRecord.ZDZL_ZHENG);
                }
                if (flowSubmitObj.busRecord.ZDZL_CUN) {
                    $("#ZDZL_CUN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_CUN);
                }

                if (flowSubmitObj.busRecord.RUN_STATUS) {
                    if (flowSubmitObj.busRecord.RUN_STATUS != 0) {

                    }

                }
            } else {
                $("select[name='POWERSOURCE_DYQRZJLX']").val("统一社会信用代码");
                $("input[name='FW_DJYY']").val("转移登记");
                $("input[name='DY_DJJG']").val("平潭综合实验区不动产登记与交易");
                $("input[name='DY_DYDJYY']").val("预购商品房抵押权预告登记转抵押权首次登记");
                setSfzgedy("0");
                openSelector();
            }
        }
        
      	//默认房源信息-当期应收款所属月份值
    	var SSDJ_FYXX_DQYSKSSYF = $("input[name='SSDJ_FYXX_DQYSKSSYF']").val();
    	if(SSDJ_FYXX_DQYSKSSYF==null || SSDJ_FYXX_DQYSKSSYF=="null" || SSDJ_FYXX_DQYSKSSYF==""){
    		var date = new Date();
    		var year=date.getFullYear();
    		var month=date.getMonth()+1;
    		month =(month<10 ? "0"+month:month);
    		var mydate = (year.toString()+"-"+month.toString());
    		$("input[name='SSDJ_FYXX_DQYSKSSYF']").val(mydate);
    	}
    	
    	//计算房源信息-单价值
    	var SSDJ_FYXX_DJ = $("input[name='SSDJ_FYXX_DJ']").val();
    	if(SSDJ_FYXX_DJ==null || SSDJ_FYXX_DJ=="null" || SSDJ_FYXX_DJ=="" || SSDJ_FYXX_DJ=="NaN"){
    		var SSDJ_FYXX_JYJG = $("input[name='SSDJ_FYXX_JYJG']").val();
    		var SSDJ_FYXX_JZMJ = $("input[name='SSDJ_FYXX_JZMJ']").val();
    		var SSDJ_FYXX_DJ = (SSDJ_FYXX_JYJG/SSDJ_FYXX_JZMJ).toFixed(2);
    		$("input[name='SSDJ_FYXX_DJ']").val(SSDJ_FYXX_DJ);
    	}
    });

</script>

<body style="background: #f0f0f0;">
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if>

	<div class="eui-main">
        <jsp:include page="./formtitle.jsp" />
        <form id="T_BDCQLC_GYJSJFWZYDJ_FORM" method="post">
            <%--开始引入公共隐藏域部分 --%>
            <jsp:include page="commonhidden.jsp" />
            <%--结束引入公共隐藏域部分 --%>
            <input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}" />
            <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
            <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
            <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
            <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
            <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />

            <%-- 权利人信息明细 --%>
            <input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
            <%-- 权属来源信息明细 --%>
            <input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
            <%-- 权属来源限制明细 --%>
            <input type="hidden" name="POWERLIMITINFO_JSON" id="POWERLIMITINFO_JSON"/>
            <%--是否全程网办--%>
            <input type="hidden" id="ISQCWB" name="ISQCWB" value="1" />

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
            <jsp:include page="./bdcqlc/gyjsjfwzydj/T_ESTATE_ZYDJ_ACCEPTINFO.jsp" />
            <%--结束引入受理信息--%>

            <%--开始引入权利人信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQlrxx.jsp" />
            <%--结束引入权利人信息--%>

            <%--开始引入权属来源信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/bdcQsly.jsp" />
            <%--结束引入权属来源信息--%>

            <%--开始引入抵押情况信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/T_ESTATE_ZYDJ_OBLIGEEINFO.jsp" />
            <%--结束引入抵押情况信息--%>

            <%--开始引入宗地信息-国有权力人信息--%>
            <jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
            <%--开始引入宗地信息-国有权力人信息--%>

            <%--开始引入房屋基本信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/bdcFwjbxx.jsp" />
            <%--开始引入房屋基本信息--%>

            <%--开始不动产询问记录基本信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/bdcXwjl.jsp" />
            <%--开始不动产询问记录基本信息--%>

            <%--开始银行询问记录基本信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/yhXwjl.jsp" />
            <%--开始银行询问记录基本信息--%>

<%--            &lt;%&ndash;开始银行询问记录基本信息&ndash;%&gt;--%>
<%--            <jsp:include page="./bdcqlc/gyjsjfwzydj/tyXwjl.jsp" />--%>
<%--            &lt;%&ndash;开始银行询问记录基本信息&ndash;%&gt;--%>

            <%--涉税登记信息--%>
            <jsp:include page="./bdcqlc/gyjsjfwzydj/taxRelatedInfo.jsp"/>
            <%--涉税登记信息--%>
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

            <%--引入申报对象信息--%>
            <jsp:include page="bdcqlc/T_BDCQLC_EMS.jsp" />
            <%--引入申报对象信息--%>

            <%--开始引入提交DIV界面 --%>
            <jsp:include page="./submitdiv.jsp" >
                <jsp:param value="submitFlow('T_BDCQLC_GYJSJFWZYDJ_FORM');" name="submitFn"/>
                <jsp:param value="saveFlow('T_BDCQLC_GYJSJFWZYDJ_FORM');" name="tempSaveFn"/>
            </jsp:include>
            <%--结束引入提交DIV界面 --%>

        </form>
    </div>

	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>