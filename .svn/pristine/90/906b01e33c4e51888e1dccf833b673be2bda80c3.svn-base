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
    <script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
    
        var t= 0;
        $(function () {
            //初始化验证引擎的配置
            $.validationEngine.defaults.autoHidePrompt = true;
            $.validationEngine.defaults.promptPosition = "topRight";
            $.validationEngine.defaults.autoHideDelay = "2000";
            $.validationEngine.defaults.fadeDuration = "0.5";
            $.validationEngine.defaults.autoPositionUpdate =true;

            var flowSubmitObj = FlowUtil.getFlowObj();
            
            //当流程为"审批"环节且事项为"消防备案"时，调用随机抽中事件
            function showAuto(){
               //清除定时器
               clearInterval(t);
               $.ajax({
                   url:"projectItemController/randomCheckByXfys.do",
                   data:{busRecordId:flowSubmitObj.EFLOW_BUSRECORDID},
                   type:"post",
                   async:false,
                   success:function (data) {
                       if (data) {
                           var jsonData = JSON.parse(data);
                           if (jsonData.isSelect) {
                               art.dialog({
                                   content: "系统随机生成的数字为：" + jsonData.randomNumList +"，\n此业主填写的数字为：" + jsonData.checkNum + "，\n此办件会被抽查",
                                   icon:"succeed",
                                   ok: true
                               });
                           } else {
                            $("input[name='ISRANDOMCHECK']").val(0);
                               art.dialog({
                                   content: "系统随机生成的数字为：" + jsonData.randomNumList +"，\n此业主填写的数字为：" + jsonData.checkNum + "，\n此办件不会被抽查",
                                   icon:"succeed",
                                   ok: true
                               });
                           }
                       }
                   }
               });         
            }           
            
            
            if (flowSubmitObj) {
                var editable = "";
                //console.log(flowSubmitObj.EFLOW_CURUSEROPERNODENAME);
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="受理"   && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="预审"){
                    $("#zjkinfo_div").attr("style","");                    
                    //初始化专家库信息
                    if(flowSubmitObj.busRecord["ZJKINFO_JSON"]){
                        var items = JSON.parse(flowSubmitObj.busRecord["ZJKINFO_JSON"]);
                        if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="专家抽取"){
                            editable = "edit";
                        }else{
                            $("#zjkSelector_btn").remove();
                        }
                        initZjkInfo(items,editable);
                    }                                     
                }
                
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="受理"){
                    $("#SLSFTG_TR").attr("style","");
                }   
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="专家抽取"){
                    $("#ZJSJSFQR_TR").attr("style",""); 
                    $("#zjkSelector_btn").removeAttr("disabled");
                }  
                 if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="签发"){
                    $("#QFSFTG_TR").attr("style","");
                }
                /*if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="结论审批"){
                    $("#JLSPSFTG_TR").attr("style","");
                }   
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="结论复查"){
                    $("#FCSFTG_TR").attr("style","");
                }  
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="监督报告初审"){
                    $("#JDBGCSSFTG_TR").attr("style","");
                }
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="监督报告审批"){
                    $("#JDBGSPSFTG_TR").attr("style","");
                }             */
                /*开始随机抽查判定*/
                /* if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == "审批" && flowSubmitObj.busRecord.IFSPECIALBUILDING != "1") {
                    //t = setInterval(showAuto, 1000);
                } */
                if (flowSubmitObj.busRecord) {                
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_GCJSTSXFYS_FORM");
                    //获取表单字段权限控制信息
                    var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
                    //初始化表单字段权限控制
                    FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_GCJSTSXFYS_FORM");
                    /*特殊项目信息判定*/
                    if (flowSubmitObj.busRecord.IFSPECIALBUILDING == '1') {
                        $("input:radio[name='IFSPECIALBUILDING']")[0].checked = true;
                        checkHandleType('特殊项目');
                        $("#specialProject").attr("style", "");
                        $("#normalProject").attr("style", "display:none");
                    } else {
                        $("input:radio[name='IFSPECIALBUILDING']")[1].checked = true;
                        checkHandleType('非特殊 项目');
                        $("#specialProject").attr("style", "display:none");
                        $("#normalProject").attr("style", "");
                    }
                    $("input:radio[name='IFSPECIALBUILDING']").attr("disabled", "true");
                }
                

    
            }
        });

        /*提交*/
        function FLOW_SubmitFun(flowSubmitObj) {
            //先判断表单是否验证通过
            var validateResult = $("#T_BSFW_GCJSTSXFYS_FORM").validationEngine(
                "validate");
            if (validateResult) {
                //获取已选专家的信息
                var zjkinfos = getZjkInfoJson();
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="专家抽取"){
                    if(zjkinfos != null && zjkinfos.length == 0){
                       art.dialog({
                           content : "请从专家库中抽取2至5个专家，并与专家确认时间。",
                           icon : "warning",
                           ok : true
                       });
                       return ;
                    }
                }
                var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
                if (submitMaterFileJson || submitMaterFileJson == "") {
                    valiSpecialProject();
                    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(
                        submitMaterFileJson);
                    //先获取表单上的所有值
                    var formData = FlowUtil.getFormEleData("T_BSFW_GCJSTSXFYS_FORM");
                    for ( var index in formData) {
                        flowSubmitObj[eval("index")] = formData[index];
                    }
                    
                    return flowSubmitObj;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        /*暂存*/
        function FLOW_TempSaveFun(flowSubmitObj) {
            //先判断表单是否验证通过
            var validateResult = $("#T_BSFW_GCJSTSXFYS_FORM").validationEngine(
                "validate");
            if (validateResult) {
                //获取已选专家的信息
                getZjkInfoJson();
                var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
                if (submitMaterFileJson || submitMaterFileJson == "") {
                    valiSpecialProject();
                    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(
                        submitMaterFileJson);
                    //先获取表单上的所有值
                    var formData = FlowUtil
                        .getFormEleData("T_BSFW_GCJSTSXFYS_FORM");
                    for ( var index in formData) {
                        flowSubmitObj[eval("index")] = formData[index];
                    }
                    return flowSubmitObj;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        /*退回*/
        function FLOW_BackFun(flowSubmitObj) {
            return flowSubmitObj;
        }


        function FLOW_ViewSumOpinionFun(flowSubmitObj){
            return flowSubmitObj;
        }

        /*批量填写特殊工程信息*/
        function valiSpecialProject() {
            var ACCEPTANCESITUATIONARR = $("input[name^='ACCEPTANCESITUATION']");
            for (let i = 0; i < ACCEPTANCESITUATIONARR.length; i++) {
                var name = ACCEPTANCESITUATIONARR[i].name;
                if (ACCEPTANCESITUATIONARR[i].value) {
                    $("#" + name).val(1);
                } else {
                    $("#" + name).val(0);
                }
            }
        }     

    </script>
</head>

<body>
<div class="detail_title">
    <h1>
        ${serviceItem.ITEM_NAME}
    </h1>
</div>
<form id="T_BSFW_GCJSTSXFYS_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
    <input type="hidden" name="ISRANDOMCHECK" value="1" />
    <%--===================重要的隐藏域内容=========== --%>

    <%--申报信息--%>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
        <tr>
            <th colspan="4">基本信息</th>
        </tr>
        <tr>
            <td class="tab_width"> 投资项目编号：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="PRJ_CODE" disabled/>
            </td>
            <td class="tab_width"> 申报号：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="EXE_ID" disabled>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 报建编号：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="PRJ_NUM" disabled/>
            </td>
            <td class="tab_width"> 项目名称：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="PRJ_NAME" disabled>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 工程地址：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="ADDRESS" disabled/>
            </td>
            <td class="tab_width"> 建设单位：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="BUILD_CORPNAME" disabled>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 法定代表人：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="LEGAL_REPRESENT" disabled/>
            </td>
            <td class="tab_width"> 联系电话：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="LEGAL_INFORMATION" disabled>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 项目负责人：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="CONTACTOR" disabled/>
            </td>
            <td class="tab_width"> 联系电话：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="CONTACT_INFORMATION" disabled>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 建设主管部门：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="BUILDMANAGERJG" maxlength="128"/>
            </td>
            <td class="tab_width"> 竣工验收日期：</td>
            <td colspan="1">
                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text Wdate" name="YANSHOUCHECKDATE"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 申请日期：</td>
            <td colspan="1">
                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text Wdate" name="APPLYDATE"/>
            </td>
            <td class="tab_width"> 是否特殊项目：</td>
            <td colspan="1">
                <input type="radio" value="1" name="IFSPECIALBUILDING" disabled> 是
                <input type="radio" value="0" name="IFSPECIALBUILDING" disabled> 否
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 申报类别：</td>
            <td colspan="1">
                <input type="radio" value="001" name="DECLARATIONTYPE" disabled> 消防验收
                <input type="radio" value="002" name="DECLARATIONTYPE" disabled> 消防备案
            </td>
            <td class="tab_width"> 消防检测机构：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="XF_CHECKJG" maxlength="128">
            </td>
        </tr>
        <tr>
            <td>公开方式</td>
            <td>
                <eve:eveselect clazz="tab_text validate[required]" dataParams="OPENWAY"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="请选择公开方式" name="OPEN_WAY" id="openWay">
                </eve:eveselect>
            </td>
            <td class="tab_width"> 消防验收申请（备案）编号</td>
            <td><input type="text" class="tab_text" name="FCACCEPTANCENUM" maxlength="64"></td>
        </tr>
         <tr>
            <td>使用单元性质</td>
            <td colspan="1">
                <input type="radio" value="1" name="SYDYXZ" > 人员密集场所
                <input type="radio" value="2" name="SYDYXZ" > 火灾危险性为丙、丁、戊类的工业建筑物或者构筑物
                <input type="radio" value="3" name="SYDYXZ" > 其他项目
            </td>
            <td>是否抽查</td>
            <td colspan="1">
                <input type="radio" value="1" name="ISCHECK" disabled> 是
                <input type="radio" value="0" name="ISCHECK" disabled> 否
            </td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="normalProject">
        <tr>
            <th colspan="4">非特殊工程信息</th>
        </tr>
        <tr>
            <td class="tab_width"> 建设工程质量监督机构：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="QUALITYDEPART" maxlength="128"/>
            </td>
            <td class="tab_width"> 消防设计备案凭证文号：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="DESIGNRECORDNUM" maxlength="60">
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 竣工验收完成日期：</td>
            <td colspan="1">
                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text Wdate" name="FINISHCHECKDATE"/>
            </td>
        </tr>
    </table>

    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="specialProject">
        <tr>
            <th colspan="4">特殊工程信息</th>
        </tr>
        <tr>
<%--            <td class="tab_width"> 特殊工程类别：</td>--%>
<%--            <td colspan="1">--%>
<%--                <eve:eveselect clazz="eve-input sel validate[required]" dataParams=""--%>
<%--                               dataInterface="dictionaryService.findDatasForSelect" value=""--%>
<%--                               defaultEmptyText="请选择申报类别" name="" >--%>
<%--                </eve:eveselect>--%>
<%--            </td>--%>
            <td class="tab_width"> 《建设工程消防设计审查意见书》文号：</td>
            <td colspan="1">
                <input type="text" class="tab_text" name="DESIGNREVIEWNUM" maxlength="60">
            </td>
            <td class="tab_width"> 审核日期：</td>
            <td colspan="1">
                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text Wdate" name="AUDITDATE"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 建筑类别：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION1"/>
            </td>
            <td class="tab_width"> 总平面布局：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION2"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 平面布置：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION3"/>
            </td>
            <td class="tab_width"> 消防水源：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION4"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 消防电源：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION5"/>
            </td>
            <td class="tab_width"> 装修防火：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION6"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 建筑保温：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION7"/>
            </td>
            <td class="tab_width"> 防火分区：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION8"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 室外消火栓系统：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION9"/>
            </td>
            <td class="tab_width"> 火灾自动报警系统：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION10"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 室内消火栓系统：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION11"/>
            </td>
            <td class="tab_width"> 自动喷水灭火系统：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION12"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 其他灭火设施：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION13"/>
            </td>
            <td class="tab_width"> 防烟排烟系统：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION14"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 安全疏散：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION15"/>
            </td>
            <td class="tab_width"> 防烟分区：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION16"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 消防电梯：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION17"/>
            </td>
            <td class="tab_width"> 防爆：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION18"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width"> 灭火器：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION19"/>
            </td>
            <td class="tab_width"> 其他事项：</td>
            <td colspan="1">
                <input type="text" class="tab_text" maxlength="100" name="OTHERMATTER"/>
            </td>
        </tr>
        <tr>
            <td class="tab_width">其他验收情况</td>
            <td>
                <input type="text" class="tab_text" maxlength="100" name="ACCEPTANCESITUATION20"/>
            </td>
        </tr>
    </table>
    <%--隐藏域--%>
    <input type="hidden" id="CHECKNUM" name="CHECKNUM">
    <input type="hidden" id="ACCEPTANCESITUATION1" name="BUILDINGCATEGORY">
    <input type="hidden" id="ACCEPTANCESITUATION2" name="GENERALLAYOUT">
    <input type="hidden" id="ACCEPTANCESITUATION3" name="PLANELAYOUT">
    <input type="hidden" id="ACCEPTANCESITUATION4" name="FIRESOURCE">
    <input type="hidden" id="ACCEPTANCESITUATION5" name="FIREPOWER">
    <input type="hidden" id="ACCEPTANCESITUATION6" name="DECORATIONFC">
    <input type="hidden" id="ACCEPTANCESITUATION7" name="BUILDINGINSULATION">
    <input type="hidden" id="ACCEPTANCESITUATION8" name="FIRECOMPARTMENT">
    <input type="hidden" id="ACCEPTANCESITUATION9" name="OFHYDRANTSYSTEM">
    <input type="hidden" id="ACCEPTANCESITUATION10" name="AUTOFIRESYSTEM">
    <input type="hidden" id="ACCEPTANCESITUATION11" name="IFHYDRANTSYSTEM">
    <input type="hidden" id="ACCEPTANCESITUATION12" name="AUTOMATICSPRINKLER">
    <input type="hidden" id="ACCEPTANCESITUATION13" name="OTHERFACILITIES">
    <input type="hidden" id="ACCEPTANCESITUATION14" name="SMOKECONTROLSYS">
    <input type="hidden" id="ACCEPTANCESITUATION15" name="SAFEEVACUATION">
    <input type="hidden" id="ACCEPTANCESITUATION16" name="SMOKEPREZONE">
    <input type="hidden" id="ACCEPTANCESITUATION17" name="FIREELEVATOR">
    <input type="hidden" id="ACCEPTANCESITUATION18" name="EXPLOSIONPROOF">
    <input type="hidden" id="ACCEPTANCESITUATION19" name="FIREEXTINGUISHER">
    <input type="hidden" id="ACCEPTANCESITUATION110" name="OTHERS">
    <input type="hidden" id="ZJKINFO_JSON" name="ZJKINFO_JSON">

    <%--开始引入公用上传材料界面 --%>
    <jsp:include page="./applyMaterList.jsp">
        <jsp:param value="2" name="isWebsite" />
    </jsp:include>


    <%--开始引入公用申报对象--%>
    <jsp:include page="./applyuserinfo.jsp" />
    <%--结束引入公用申报对象 --%>
    
    <%--开始引入专家库信息--%>
    <jsp:include page="./tsxf/xfzjk_info.jsp" />
    <%--结束引入专家库信息--%>
    
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="YWCZ_TABLE" >
        <tr>
            <th colspan="4">业务操作</th>
        </tr>
        <tr id="SLSFTG_TR" style="display: none;">
            <td class="tab_width"><font class="tab_color">*</font>受理是否通过：</td>
            <td><eve:radiogroup typecode="YesOrNo" width="130px"
                    fieldname="SLSFTG" defaultvalue="1" ></eve:radiogroup></td>
        </tr>
        <tr id="ZJSJSFQR_TR" style="display: none;">
            <td class="tab_width"><font class="tab_color">*</font>专家是否确认时间：</td>
            <td><eve:radiogroup typecode="YesOrNo" width="130px"
                    fieldname="ZJSFQR" defaultvalue="1" ></eve:radiogroup></td>
        </tr>        
        <tr id="QFSFTG_TR" style="display: none;">
            <td class="tab_width"><font class="tab_color">*</font>验收是否合格：</td>
            <td><eve:radiogroup typecode="YesOrNo" width="130px"
                    fieldname="YSSFHG" defaultvalue="1" ></eve:radiogroup></td>
        </tr>
    </table>
<%--         开始引入申请表信息部分
        <jsp:include page="/webpage/website/applyforms/xfsj/applyForm.jsp" >
            <jsp:param value="T_BSFW_GCJSTSXFYS_FORM" name="formId"/>
            <jsp:param value="materCode" name="41"/>
        </jsp:include>
        结束引入申请表信息部分 --%>
</form>
</body>
</html>
