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
    <eve:resources loadres="apputil,easyui,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
    <link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css" />
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">

        $(function () {
            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate = true;

            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
            if (EFLOW_FLOWOBJ) {
                //将其转换成JSON对象
                var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
                //初始化表单字段值
                if (eflowObj.busRecord) {
                    FlowUtil.initFormFieldValue(eflowObj.busRecord, "T_BSFW_LYYDSP_FORM");
                }
            }

            var projectCode = '${projectCode}';
            if (projectCode) {
                $("input[name='PROJECT_CODE']").val(projectCode);
            }

            /*经办人地址必填*/
            $("input[name='JBR_ADDRESS']").attr("class","tab_text validate[required]");
            $("input[name='JBR_ADDRESS']").parent().prev().html("<span class='bscolor1'>*</span>联系地址");

        });

        /*提交事件*/
        function submitFlow(formId){
            //先判断表单是否验证通过
            var validateResult =$("#"+formId).validationEngine("validate");
            if(validateResult){

                /*判断红线图是否校验*/
                var isRedChecked = $("input[name='IS_REDCHECK']").val();
                if (!(isRedChecked && isRedChecked == '1')) {
                    art.dialog({
                        content : "红线图未校验成功，请校验成功后再提交流程",
                        icon : "error",
                        ok : true
                    });
                    return null;
                }

                var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
                if(submitMaterFileJson||submitMaterFileJson==""){
                    //获取流程信息对象JSON
                    var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
                    //将其转换成JSON对象
                    var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);

                    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
                    //先获取表单上的所有值
                    var formData = FlowUtil.getFormEleData(formId);
                    for(var index in formData){
                        flowSubmitObj[eval("index")] = formData[index];
                    }
                    flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
                    var postParam = $.param(flowSubmitObj);
                    AppUtil.ajaxProgress({
                        url : "webSiteController.do?submitApply",
                        params : postParam,
                        callback : function(resultJson) {
                            if(resultJson.OPER_SUCCESS){
                                AppUtil.ajaxProgress({
                                    url : "projectItemController/lyydspSendMsgToSwb.do",
                                    params : resultJson,
                                    callback : function(resultJson){
                                        art.dialog({
                                            content : resultJson.OPER_MSG,
                                            icon : "succeed",
                                            lock : true,
                                            ok:function(){
                                                window.top.location.href=__newUserCenter;
                                            },
                                            close: function(){
                                                window.top.location.href=__newUserCenter;
                                            }
                                        });
                                    }
                                })
                            }else{
                                art.dialog({
                                    content : resultJson.OPER_MSG,
                                    icon : "error",
                                    ok : true
                                });
                            }
                        }
                    });
                }else{
                    return null;
                }
            }
        }

        function saveFlow() {
            AppUtil.tempSaveWebSiteFlowForm('T_BSFW_LYYDSP_FORM');
        }


    </script>
</head>

<body  style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<c:if test="${projectCode == null }">
	<jsp:include page="/webpage/website/newui/head.jsp" />
	</c:if>
	<c:if test="${projectCode != null }">
	<jsp:include page="/webpage/website/newproject/head.jsp" />
	</c:if>
	<%--结束编写头部文件 --%>
	<div class="eui-main">
    <jsp:include page="./formtitle.jsp" />
    <form id="T_BSFW_LYYDSP_FORM" method="post">
        <%--===================重要的隐藏域内容=========== --%>
        <%--开始引入公共隐藏域部分 --%>
        <jsp:include page="commonhidden.jsp" />
        <%--结束引入公共隐藏域部分 --%>
        <input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}" />
        <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
        <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
        <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
        <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
        <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
        <%--===================重要的隐藏域内容=========== --%>
        <%--红线图是否验证成功--%>
        <input type="hidden" name="IS_REDCHECK" value="${serviceItem.IS_REDCHECK}">
        <div class="bsbox clearfix">

            <%--=========================项目信息===========================--%>
            <div class="bsboxT">
                <ul>
                    <li class="on" style="background:none"><span>项目信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
                <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: auto;">
                    <tr>
                        <th><span class="bscolor1">*</span> 全省统一项目编码：</th>
                        <td colspan="3"><input type="text"  name="PROJECT_CODE" class="tab_text" value="${serviceItem.projectCode}" disabled/></td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 项目地址：</th>
                        <td colspan="3"><input type="text"  name="PROJECT_ADDRESS" maxlength="128" class="tab_text" value="${serviceItem.PROJECT_ADDRESS}"/></td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 用地申请单位：</th>
                        <td>
                            <input type="text" maxlength="128"  name="LAND_APPLICATION_UNIT" class="tab_text validate[required]" value="${serviceItem.LAND_APPLICATION_UNIT}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 用地类型：</th>
                        <td>
                            <input type="text" maxlength="32" class="tab_text"  name="LAND_TYPE"  value="永久用地" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 用地总面积：</th>
                        <td>
                            <input type="text" maxlength="32"  name="USE_LAND_AREA" class="tab_text validate[required]" value="${serviceItem.USE_LAND_AREA}"/>
                            <span>公顷</span>
                        </td>
                        <th><span class="bscolor1">*</span> 林地总面积：</th>
                        <td>
                            <input type="text" maxlength="32"  name="FOREST_LAND_AREA" class="tab_text validate[required]" value="${serviceItem.FOREST_LAND_AREA}"/>
                            <span>公顷</span>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 城镇或乡村规划：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="FHBFH"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择城镇或乡村规划" name="TOWN_VILLAGE_PLANNING">
                            </eve:eveselect>
                        </td>
                        <th><span class="bscolor1">*</span> 涉密项目：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="FHBFH"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择涉密项目" name="SECRET_PROJECT">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 投资项目：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="FHBFH"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择投资项目" name="INVEST_PROJECT">
                            </eve:eveselect>
                        </td>
                        <th><span class="bscolor1">*</span> 项目类型类别：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="XMLXLB"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择项目类型类别" name="PROJECT_TYPE">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 公开方式：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="GKFS"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择公开方式" name="OPEN_WAY">
                            </eve:eveselect>
                        </td>
                        <th><span class="bscolor1">*</span> 申请来源：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SQLY1"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择申请来源" name="APPLY_FROM">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 重点项目级别：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="ZDXMJB"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择重点项目级别" name="PROJECT_RANK">
                            </eve:eveselect>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="bsboxT">
                <ul>
                    <li class="on" style="background:none"><span>法人信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
                <table cellpadding="0" cellspacing="0" class="bstable1">
                    <tr>
                        <th><span class="bscolor1">*</span> 法人姓名：</th>
                        <td>
                            <input type="text" maxlength="16"  name="UNIT_LEAD_NAME" class="tab_text validate[required]" value="${serviceItem.UNIT_LEAD_NAME}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 法人证件类型：</th>
                        <td>
                            <eve:eveselect clazz=" tab_text w280 sel validate[required]" onchange="setValidate(this.value);"
                                           dataParams="DocumentType" value="${serviceItem.UNIT_LEAD_TYPE}"
                                           dataInterface="dictionaryService.findDatasForSelect" id="sqrzjlx"
                                           defaultEmptyText="====选择证件类型====" name="UNIT_LEAD_TYPE"></eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 法人证件号码：</th>
                        <td>
                            <input type="text" maxlength="18"  name="UNIT_LEAD_CARDNO" class="tab_text validate[required]" value="${serviceItem.UNIT_LEAD_NAME}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 法人手机号码：</th>
                        <td>
                            <input type="text"  name="UNIT_LEAD_PHONE" class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${serviceItem.UNIT_LEAD_TYPE}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 法人性别：</th>
                        <td>
                            <eve:eveselect clazz=" tab_text w280 sel validate[required]"
                                           dataParams="sex" value="${serviceItem.UNIT_LEAD_SEX}"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="====选择性别====" name="UNIT_LEAD_SEX"></eve:eveselect>
                        </td>
                        <th> 法人出生日期：</th>
                        <td>
                            <input type="text"  name="UNIT_LEAD_BIRTHDAY" class="tab_text" value="${serviceItem.UNIT_LEAD_TYPE}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 家庭住址或机构地址：</th>
                        <td>
                            <input type="text" maxlength="128"  name="UNIT_LEAD_ADDR" class="tab_text validate[required]" value="${serviceItem.UNIT_LEAD_NAME}"/>
                        </td>
                        <th> 邮政编码：</th>
                        <td>
                            <input type="text"  name="UNIT_LEAD_POSTCODE" maxlength="6" class="tab_text" value="${serviceItem.UNIT_LEAD_TYPE}"/>
                        </td>
                    </tr>
                    <tr>
                        <th> 邮箱：</th>
                        <td>
                            <input type="text" maxlength="32"  name="UNIT_LEAD_EMAIL" class="tab_text" value="${serviceItem.UNIT_LEAD_NAME}"/>
                        </td>
                        <th> 办公电话：</th>
                        <td>
                            <input type="text"  name="UNIT_LEAD_TEL" maxlength="16" class="tab_text" value="${serviceItem.UNIT_LEAD_TYPE}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 统一社会信用代码：</th>
                        <td>
                            <input type="text" maxlength="18" name="UNIT_JG_CODE" class="tab_text validate[required]" value="${serviceItem.UNIT_JG_CODE}"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="bsboxT">
                <ul>
                    <li class="on" style="background:none"><span>项目批准文件</span></li>
                </ul>
            </div>
            <div class="bsboxC">
                <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
                    <tr>
                        <th><span class="bscolor1">*</span> 项目名称：</th>
                        <td>
                            <input type="text" maxlength="128"  name="PROJECT_NAME" class="tab_text validate[required]" value="${serviceItem.PROJECT_NAME}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 批准机关：</th>
                        <td>
                            <input type="text" maxlength="128"  name="APPROVAL_AUTHORITY" class="tab_text validate[required]" value="${serviceItem.APPROVAL_AUTHORITY}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 批准文件名称：</th>
                        <td>
                            <input type="text" maxlength="128"  name="APPROVAL_FILE_NAME" class="tab_text validate[required]" value="${serviceItem.APPROVAL_FILE_NAME}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 批准文号：</th>
                        <td>
                            <input type="text" maxlength="64"  name="APPROVAL_FILE_NUM" class="tab_text validate[required]" value="${serviceItem.APPROVAL_FILE_NUM}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 批准文件类型：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="PZWJLX"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择批准文件类型" name="APPROVAL_FILE_TYPE">
                            </eve:eveselect>
                        </td>
                        <th><span class="bscolor1">*</span> 批准等级：</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="PZDJ"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择批准等级" name="APPROVAL_FILE_RANK">
                            </eve:eveselect>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="bsboxT">
                <ul>
                    <li class="on" style="background:none"><span>资质单位</span></li>
                </ul>
            </div>
            <div class="bsboxC">
                <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
                    <tr>
                        <th><span class="bscolor1">*</span> 单位名称：</th>
                        <td>
                            <input type="text" maxlength="128"  name="INTELLIGENCE_NAME" class="tab_text validate[required]" value="${serviceItem.INTELLIGENCE_NAME}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 资质等级：</th>
                        <td>
                            <input type="text" maxlength="32"  name="INTELLIGENCE_RANK" class="tab_text validate[required]" value="${serviceItem.INTELLIGENCE_RANK}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 单位法人：</th>
                        <td>
                            <input type="text" maxlength="16"  name="INTELLIGENCE_LEGAL_NAME" class="tab_text validate[required]" value="${serviceItem.LEGAL_REPRESENT}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 法人身份证：</th>
                        <td>
                            <input type="text" maxlength="18"  name="INTELLIGENCE_LEGAL_CARDNO" class="tab_text validate[required]" value="${serviceItem.LEGAL_INFORMATION}"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">林地可研报告中是否有对项目的建设背景、拟占林地的情况调查、影响程度、综合评价及制定的保障措施等因素进行综合分析，并认为该项目使用林地方案可行。</td>
                        <th><span class="bscolor1">*</span> 页码：</th>
                        <td>
                            <input type="text" maxlength="8"  name="INTELLIGENCE_PAGENUM" class="tab_text validate[required]" value="${serviceItem.INTELLIGENCE_PAGENUM}"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 批准等级：</th>
                        <td>
                            <input type="text" maxlength="32"  name="INTELLIGENCE_APPROVAL_RANK" class="tab_text validate[required]" value="${serviceItem.INTELLIGENCE_APPROVAL_RANK}"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="bsboxT">
                <ul>
                    <li class="on" style="background:none"><span>缴款单位信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
                <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
                    <tr>
                        <th><span class="bscolor1">*</span> 开户行：</th>
                        <td colspan="3">
                            <input  type="text"  name="PAY_UNIT_BANK" maxlength="64" class="tab_text validate[required]" value="${serviceItem.PAY_UNIT_BANK}"/>
                            <span style="color: red; font-weight: 700;">（请认真核对，务必填写正确，否则影响交款和审核审批）</span>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 户名：</th>
                        <td colspan="3">
                            <input type="text"  name="PAY_UNIT_BANKNAME" maxlength="64" class="tab_text validate[required]" value="${serviceItem.PAY_UNIT_BANKNAME}"/>
                            <span style="color: red; font-weight: 700;">（请认真核对，务必填写正确，否则影响交款和审核审批）</span>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 账号：</th>
                        <td colspan="3">
                            <input type="text"  name="PAY_UNIT_BANKCOUNT" maxlength="32" class="tab_text validate[required]" value="${serviceItem.PAY_UNIT_BANKCOUNT}"/>
                            <span style="color: red; font-weight: 700;">（请认真核对，务必填写正确，否则影响交款和审核审批）</span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4"><span style="color: red; font-weight: 700;">为规范免征植被费，如需免征植被费，请联系省局审批处</span></td>
                    </tr>
                </table>
            </div>
                <div class="bsboxT">
                    <ul>
                        <li class="on" style="background:none"><span>红线图验证</span></li>
                    </ul>
                </div>
                <div style="margin-top: 30px;">
                    <span style="padding: 0px 30px;color:red;font-weight: 700;font-size: 14px;">红线图校验成功后才可提交流程，仅支持shape类型红线图，在材料列表请上传shape格式的压缩包红线图（zip格式）</span>
                </div>
                <div class="bsboxC">
                    <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
                        <input name="upimage" id="upload_file" type="file">
                        <textarea id="base64_output" style="display: none;" ></textarea>
                    </table>
                </div>
        </div>

        <%--结束编写基本信息 --%>

        <jsp:include page="./applyuserinfo.jsp" />

        <div class="bsbox clearfix">
            <div class="bsboxT">
                <ul>
                    <li class="on" style="background:none"><span>所需材料</span></li>
                </ul>
            </div>
            <jsp:include page="./matterListZF.jsp" >
                <jsp:param value="1" name="applyType"/>
                <jsp:param value="T_BSFW_ZFTZSGTSJHZTB_FORM" name="formName"/>
            </jsp:include>
        </div>
    </form>

    <%--开始引入提交DIV界面 --%>
    <jsp:include page="./submitdiv.jsp" >
        <jsp:param value="submitFlow('T_BSFW_LYYDSP_FORM');" name="submitFn"/>
        <jsp:param value="saveFlow();" name="tempSaveFn"/>
    </jsp:include>
    <%--结束引入提交DIV界面 --%>

    <%-- 引入阶段流程图 并且当前节点不在开始或结束节点--%>
    <c:if test="${EFLOWOBJ.HJMC!=null}">
        <jsp:include page="xmlct.jsp" >
            <jsp:param value="${EFLOW_FLOWDEF.DEF_ID}" name="defId"/>
            <jsp:param value="${EFLOWOBJ.HJMC}" name="nodeName"/>
        </jsp:include>
    </c:if>
    <%-- 结束引入阶段流程图 并且当前节点不在开始或结束节点--%>

    <%--开始引入回执界面 --%>
    <jsp:include page="./hzxx.jsp">
        <jsp:param value="${EFLOWOBJ.EFLOW_EXEID}" name="exeId" />
    </jsp:include>
    <%--结束引入回执界面 --%>
</div>



	<%--开始编写尾部文件 --%>
	<jsp:include page="/webpage/website/newui/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>

<script type="text/javascript">
    document.getElementById("upload_file").onchange = function () {
        var file = $_('upload_file').files[0];
        r = new FileReader();  //本地预览
        r.onload = function(){
            var result = r.result;
            if (result.indexOf("data:application/zip;base64,") != -1) {
                result = result.replace("data:application/zip;base64,","");
            } else if (result.indexOf("data:;base64,") != -1) {
                result = result.replace("data:;base64,", "");
            }else if(result.indexOf("data:application/x-zip-compressed;base64,") != -1){
                result = result.replace("data:application/x-zip-compressed;base64,", "");
            }
            $_('base64_output').value = result;
            $.post("projectItemController/checkRedLinePicture.do",{base64Code:result},function (data) {
                if (data) {
                    var json = JSON.parse(data)
                    if (json.success) {
                        $("input[name='IS_REDCHECK']").val("1");
                    }
                    art.dialog({
                        content: json.msg,
                        icon:"warning",
                        time:3,
                        ok: true
                    });
                }
            })
        }
         r.readAsDataURL(file);    //Base64
    };
    function $_(id) {
        return document.getElementById(id);
    }
</script>
</html>