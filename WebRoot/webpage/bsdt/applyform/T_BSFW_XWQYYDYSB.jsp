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
    <eve:resources
            loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
    <script type="text/javascript"
            src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <script type="text/javascript"
            src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
    <script type="text/javascript">
        $(function(){

            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate =true;
            var flowSubmitObj = FlowUtil.getFlowObj();
            var dealItems = '${dealItem.DEALITEM_NODE}';//从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
            dealItems = "," + dealItems + ",";
            if(flowSubmitObj){
                //获取表单字段权限控制信息
                var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
                console.log(currentNodeFieldRights)
                $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
                //初始化表单字段权限控制
                FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_XWQYYDYSB_FORM");
                //初始化表单字段值
                if(flowSubmitObj.busRecord){
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_XWQYYDYSB_FORM");
                    if(flowSubmitObj.busRecord.RUN_STATUS!=0){
                        if($("input[name='SBMC']").val()){
                        }else{
                            $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');
                        }
                    }
                    var  isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
                    //设置文件是否合规
                    if(isAuditPass == "-1"){
                        $("input:radio[name='isAuditPass'][value='-1']").attr("checked",true);
                    }
                }else{
                    $("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
                }
                checkCommercialStatus(flowSubmitObj);
            }

        });

        /*ajax获取当前件状态*/
        function findCommercialStatus(callback) {
            var XW_SSSBH = $("input[name='XW_SSSBH']").val();
            $.post("statisticsNewController/findCommercialStatus.do",{XW_SSSBH:XW_SSSBH},function (data){
                var dataObj = JSON.parse(data);
                if (dataObj.status == 'success') {
                    $("#downloadXwMater").html("<span style='cursor: pointer;' id='"+dataObj.RESULT_FILE_ID+"' onclick='downloadXwMater(this)'>下载<span>")
                } else {
                    $("#downloadXwMater").html("<span>暂无营业执照<span>")
                }
                callback(dataObj)
            })
        }

        function checkCommercialStatus(flowSubmitObj) {
            findCommercialStatus(function (dataObj) {
                if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '一证受理') {
                    if (dataObj.status == 'none') {
                        parent.art.dialog({
                            content : "商事申报号填写有误,请退回",
                            icon : "warning",
                            ok : true
                        });
                    } else if(dataObj.status == 'fail') {
                        parent.art.dialog({
                            content : "商事登记暂未办结,请等待办结后受理",
                            icon : "warning",
                            ok : true
                        });
                    }
                }
            });
        }

        function downloadXwMater(e) {
            var fileId = e.id;
            window.location.href=__ctxPath+"/DownLoadServlet?fileId="+fileId;
        }

        function FLOW_SubmitFun(flowSubmitObj){
            //先判断表单是否验证通过
            var validateResult =$("#T_BSFW_XWQYYDYSB_FORM").validationEngine("validate");
            if(validateResult){
                var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
                if(submitMaterFileJson||submitMaterFileJson==""){
                    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
                    //先获取表单上的所有值
                    var formData = FlowUtil.getFormEleData("T_BSFW_XWQYYDYSB_FORM");
                    for(var index in formData){
                        flowSubmitObj[eval("index")] = formData[index];
                    }
                    //console.dir(flowSubmitObj);
                    return flowSubmitObj;
                }else{
                    return null;
                }
            }else{
                return null;
            }

        }

        function FLOW_TempSaveFun(flowSubmitObj){
            //先判断表单是否验证通过
            var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData("T_BSFW_XWQYYDYSB_FORM");
            for(var index in formData){
                flowSubmitObj[eval("index")] = formData[index];
            }
            return flowSubmitObj;

        }

        function FLOW_BackFun(flowSubmitObj){
            return flowSubmitObj;
        }

        function setFileNumber(serialNum){
            /* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
            var enterprise = '${execution.SQJG_CODE}';
            var idCard = '${execution.SQRSFZ}';
            alert(idCard + "," + enterprise);
            var fileNum;
            if (enterprise != ""){
                fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
            } else {
                fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
            }
            $("#fileNumber").val(fileNum);
        }

    </script>
</head>

<body>
<div class="detail_title">
    <h1>${serviceItem.ITEM_NAME}</h1>
</div>
<form id="T_BSFW_XWQYYDYSB_FORM" method="post">
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId"
           value="${sessionScope.curLoginUser.userId}" /> <input type="hidden"
                                                                 name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> <input
        type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> <input
        type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> <input
        type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> <input
        type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="BELONG_DEPTNAME"
           value="${serviceItem.SSBMMC}" /> <input type="hidden" name="SXLX"
                                                   value="${serviceItem.SXLX}" /> <input type="hidden"
                                                                                         name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
    <%--===================重要的隐藏域内容=========== --%>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
        <tr>
            <th colspan="4">基本信息</th>
        </tr>
        <tr>
            <td>审批事项：</td>
            <td>${serviceItem.ITEM_NAME}</td>
            <td class="tab_width">承诺时间(工作日)：</td>
            <td>${serviceItem.CNQXGZR}</td>
        </tr>
        <tr>
            <td>所属部门：</td>
            <td>${serviceItem.SSBMMC}</td>
            <td class="tab_width">审批类型：</td>
            <td>${serviceItem.SXLX}</td>
        </tr>

        <tr>
            <td class="tab_width"><font class="tab_color">*</font> 申报名称：</td>
            <td colspan="3"><input type="text"
                                   class="tab_text validate[required]" style="width: 70%"
                                   maxlength="220" name="SBMC" value="${busRecord.SBMC}"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong>
				</span>
            </td>
        </tr>
        <tr>
            <td class="tab_width">申报号：</td>
            <td id="EXEID"></td>
            <td></td>
            <td></td>
        </tr>
    </table>



    <%--开始引入公用申报对象--%>
    <jsp:include page="./applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="JBRXX">
        <tr>
            <th colspan="4">预开户信息</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
            <td style="width:430px">
                <input type="text"
                       class="tab_text validate[required]"
                       maxlength="100" name="XW_QYMC" value="${serviceItem.XW_QYMC}"/></td>
            <td class="tab_width"><font class="tab_color">*</font>企业地址: </td>
            <td>
                <input type="text"
                       class="tab_text validate[required]"
                       maxlength="100" name="XW_QYDZ" value="${serviceItem.XW_QYDZ}"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>是否开具增值税专用发票：</td>
            <td>
                <eve:eveselect clazz="eve-input sel validate[required]" dataParams="YesOrNo"
                               dataInterface="dictionaryService.findDatasForSelect" value="${serviceItem.XW_FP}"
                               defaultEmptyText="是否开具增值税专用发票" name="XW_FP" id="openWay">
                </eve:eveselect>
            </td>
            <td class="tab_width"><font class="tab_color">*</font>申请容量：</td>
            <td>
                <input type="text"
                       class="tab_text validate[required]"
                       maxlength="100" name="XW_SQRL" value="${serviceItem.XW_SQRL}"/> KW</td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>商事申报号：</td>
            <td>
                <input type="text"
                       class="tab_text validate[required]"
                       maxlength="100" name="XW_SSSBH" id="XW_SSSBH" value="${serviceItem.XW_SSSBH}"/>
            </td>
            <td class="tab_width"><font class="tab_color">*</font>法人代表姓名：</td>
            <td>
                <input type="text"
                       class="tab_text validate[required]"
                       maxlength="100" name="XW_FRXM" value="${serviceItem.XW_FRXM}"/>
            </td>
        </tr>
        <tr>
            <<td class="tab_width"><font class="tab_color">*</font>身份证件号码：</td>
            <td>
                <input type="text"
                       class="tab_text validate[required]"
                       maxlength="100" name="XW_FRJJHM" value="${serviceItem.XW_FRJJHM}"/>
            </td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="JBRXX">
        <tr>
            <th colspan="4">材料下载</th>
        </tr>
        <tr>
            <td style="color: #053892;text-align: center">营业执照</td>
            <td style="color: #053892;text-align: center" id="downloadXwMater"></td>
        </tr>
    </table>


    <%--开始引入公用上传材料界面 --%>
    <jsp:include page="./applyMaterList.jsp">
        <jsp:param value="1" name="isWebsite" />
    </jsp:include>
    <%--结束引入公用上传材料界面 --%>

</form>
</body>
</html>