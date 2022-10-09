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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/ui20211220/css/applyform.css">
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
                    FlowUtil.initFormFieldValue(eflowObj.busRecord, "T_BSFW_GCJSXFYS_FORM");
                                                /*特殊项目信息判定*/
                            if (eflowObj.busRecord.IFSPECIALBUILDING == "1") {
                                $("input:radio[name='IFSPECIALBUILDING']")[0].checked = true;
                                //$("input:radio[name='DECLARATIONTYPE'][value='001']").attr('checked',true);
                                //$("input:radio[name='EXE_SUBBUSCLASS']")[1].checked = true;
                                $("#specialProject").attr("style", "");
                                $("#normalProject").attr("style", "display:none");
                                checkHandleType('特殊项目');
                            } else {
                                $("input:radio[name='IFSPECIALBUILDING']")[1].checked = true;
                                //$("input:radio[name='DECLARATIONTYPE'][value='002']").attr('checked',true);
                                //console.log($("input:radio[name='EXE_SUBBUSCLASS']"))
                                //$("input:radio[name='EXE_SUBBUSCLASS']")[0].checked = true;
                                checkHandleType('非特殊 项目');
                                $("#specialProject").attr("style", "display:none");
                                $("#normalProject").attr("style", "");
                            }
                            if(eflowObj.busRecord.DECLARATIONTYPE == "001"){
                                $("input:radio[name='DECLARATIONTYPE'][value='001']").attr('checked',true);
                            }else{
                                $("input:radio[name='DECLARATIONTYPE'][value='002']").attr('checked',true);
                            }
                            
                }else{
            /*获取消防设计阶段数据*/
            var projectCode = '${projectCode}';
            if (projectCode) {
                $.post("projectItemController/getProjectDetailInfoByXfsj.do", {projectCode: projectCode}, function (data) {
                    if (data) {
                        var json = JSON.parse(data);
                        if (json.returnStatus) {
                            /*项目基本信息*/
                            $("input[name='PRJ_NUM']").val(json.PRJ_NUM);
                            $("input[name='PRJ_CODE']").val(json.PRJ_CODE);
                            $("input[name='PRJ_NAME']").val(json.PRJ_NAME);
                            $("input[name='ADDRESS']").val(json.ADDRESS);
                            $("input[name='LEGAL_REPRESENT']").val(json.LEGAL_REPRESENT);
                            $("input[name='LEGAL_INFORMATION']").val(json.LEGAL_INFORMATION);
                            $("input[name='CONTACTOR']").val(json.CONTACTOR);
                            $("input[name='CONTACT_INFORMATION']").val(json.CONTACT_INFORMATION);
                            $("input[name='BUILD_CORPNAME']").val(json.BUILD_CORPNAME);
                            /*隐藏域表单回填*/
                            $("input[name='PROJECT_CODE']").val(json.PRJ_CODE);
                            $("input[name='PROJECT_NAME']").val(json.PRJ_NAME);
                            $("input[name='FC_PRJ_TYPE']").val(json.FC_PRJ_TYPE);
                            $("input[name='FC_CHARACTER']").val(json.FC_CHARACTER);
                            $("input[name='JSDWSJSQR']").val(json.JSDWSJSQR);
                            $("input[name='ZFCXJSZGBM']").val(json.ZFCXJSZGBM);
                            $("input[name='XMXFBM']").val(json.XMXFBM);
                            $("input[name='SGTSCHGZHBH']").val(json.SGTSCHGZHBH);
                            $("input[name='ZYGCXFSCHGSBH']").val(json.ZYGCXFSCHGSBH);
                            $("input[name='SET_LOCATION']").val(json.SET_LOCATION);
                            $("input[name='CAPACITY']").val(json.CAPACITY);
                            $("input[name='SET_TYPE']").val(json.SET_TYPE);
                            $("input[name='STORAGE_TYPE']").val(json.STORAGE_TYPE);
                            $("input[name='CG_STORAGE_NAME']").val(json.CG_STORAGE_NAME);
                            $("input[name='STORAGE_CAPACITY']").val(json.STORAGE_CAPACITY);
                            $("input[name='DC_STORAGE_NAME']").val(json.DC_STORAGE_NAME);
                            $("input[name='MATERIAL_CATEGORY']").val(json.MATERIAL_CATEGORY);
                            $("input[name='INSULATION_LAYER']").val(json.INSULATION_LAYER);
                            $("input[name='BW_USE_CHARACTER']").val(json.BW_USE_CHARACTER);
                            $("input[name='BW_ORIGINAL_USE']").val(json.BW_ORIGINAL_USE);
                            $("input[name='BEGIN_DETE']").val(json.BEGIN_DETE);
                            $("input[name='END_DATE']").val(json.END_DATE);
                            $("input[name='FC_FACILITIES']").val(json.FC_FACILITIES);
                            $("input[name='OTHER_INSTRUCTIONS']").val(json.OTHER_INSTRUCTIONS);
                            $("input[name='DECORATION_PART']").val(json.DECORATION_PART);
                            $("input[name='DECORATION_AREA']").val(json.DECORATION_AREA);
                            $("input[name='DECORATION_LAYER']").val(json.DECORATION_LAYER);
                            $("input[name='ZX_USE_CHARACTER']").val(json.ZX_USE_CHARACTER);
                            $("input[name='ZX_ORIGINAL_USE']").val(json.ZX_ORIGINAL_USE);
                            $("input[name='DTXX_JSON']").val(json.DTXX_JSON);
                            $("input[name='ZRZTXX_JSON']").val(json.ZRZTXX_JSON);
                            $("input[name='ADMIN_DIVISION']").val(json.ADMIN_DIVISION);
                            $("input[name='APPLY_DATE']").val(json.APPLY_DATE);
                            $("input[name='IFSPECIALBUILDING']").val(json.IFSPECIALBUILDING);

                            /*特殊项目信息判定*/
                            if ($("input[name='IFSPECIALBUILDING']").val() == "1") {
                                $("input:radio[name='IFSPECIALBUILDING']")[0].checked = true;
                                $("input:radio[name='DECLARATIONTYPE'][value='001']").attr('checked',true);
                                $("input:radio[name='EXE_SUBBUSCLASS']")[1].checked = true;
                                $("#specialProject").attr("style", "");
                                $("#normalProject").attr("style", "display:none");
                                checkHandleType('特殊项目');
                            } else {
                                $("input:radio[name='IFSPECIALBUILDING']")[1].checked = true;
                                $("input:radio[name='DECLARATIONTYPE'][value='002']").attr('checked',true);
                                console.log($("input:radio[name='EXE_SUBBUSCLASS']"))
                                $("input:radio[name='EXE_SUBBUSCLASS']")[0].checked = true;
                                checkHandleType('非特殊 项目');
                                $("#specialProject").attr("style", "display:none");
                                $("#normalProject").attr("style", "");
                            }
                            $("input:radio[name='EXE_SUBBUSCLASS']").attr("disabled", "true");
                            /*竣工验收日期，申请日期*/
                            var date = new Date();
                            var year = date.getFullYear();
                            var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1);
                            var date = date.getDate() > 9 ? date.getDate() : '0' + date.getDate();
                            $("input[name='APPLYDATE']").val(year + "-" + month + "-" + date);
                        } else {
                            $("input[name='PROJECT_CODE']").val(projectCode);
                            $("input[name='PRJ_CODE']").val(projectCode);
                            art.dialog({
                                content: "请先申报消防设计",
                                icon:"error",
                                ok: true
                            });
                        }
                    }
                });
            }                 
                
                }
            }



            
        });
    
    	/*判断是否特殊工程*/
    	function isSpecialBuilding(val) {
    		/*特殊项目信息判定*/
    		if (val == 1) {
    			$("#specialProject").attr("style", "");
    			$("#normalProject").attr("style", "display:none");
    			$("#specialProject").show();
    			$("#normalProject").hide();
    			checkHandleType('特殊项目');
    		} else {
    			checkHandleType('非特殊 项目');
    			$("#specialProject").attr("style", "display:none");
    			$("#normalProject").attr("style", "");
    			$("#specialProject").hide();
    			$("#normalProject").show();
    		}
    	}
    
    	/*提交事件*/
    	function submitFlow(){
            valiSpecialProject();
            var yanshouDate = $("input[name='YANSHOUCHECKDATE']").val();
            var applyDate = $("input[name='APPLYDATE']").val();
            if (new Date(yanshouDate) < new Date(applyDate)) {
                var valifileResult=AppUtil.getSubmitMaterFileJson("T_BSFW_GCJSXFYS_FORM",1);
                var validateResult =$("#T_BSFW_GCJSXFYS_FORM").validationEngine("validate");
                if (valifileResult) {
                    if (validateResult) {
                        setHiddenValue();
                        /*回填特殊工程竣工验收信息*/
                        valiSpecialProject();
                        $.dialog.open("projectItemController/randomCheckByXfysView.do" , {
                            title : "随机抽查",
                            width : "300px",
                            height : "150px",
                            lock : true,
                            resize : false,
                            close:function () {
                                var checkNum = art.dialog.data("checkNum");
                                if (checkNum) {
                                    $("input[name='CHECKNUM']").val(checkNum);
                                    AppUtil.submitWebSiteFlowForm('T_BSFW_GCJSXFYS_FORM');
                                } else {
                                    art.dialog({
                                        content: "未填写随机数",
                                        icon:"error",
                                        ok: true
                                    });
                                }
                            }
                        },false)
                    }
                }
            } else {
                art.dialog({
                    content: "竣工验收日期需早于申请日期",
                    icon:"error",
                    ok: true
                });
            }

        }

        function saveFlow() {
            
            valiSpecialProject();
             //var valifileResult=AppUtil.getSubmitMaterFileJson("T_BSFW_GCJSXFYS_FORM",1);
             var validateResult =$("#T_BSFW_GCJSXFYS_FORM").validationEngine("validate");
             if(validateResult){
             setHiddenValue();
             valiSpecialProject();
             AppUtil.tempSaveWebSiteFlowForm('T_BSFW_GCJSXFYS_FORM');
             }
    
    	}
    
    function setHiddenValue(){
       var PRJ_NAME = $("input[name='PRJ_NAME']").val();
       $("input[name='PROJECT_NAME']").val(PRJ_NAME);
       if($("input:radio[name='IFSPECIALBUILDING']")[0].checked){
       $("input[name='IFSPECIALBUILDING']").val("1");
       }else{
       $("input[name='IFSPECIALBUILDING']").val("0");
       }
       
       if($("input:radio[name='DECLARATIONTYPE']")[0].checked){
       $("input[name='DECLARATIONTYPE']").val("001");
       }else{
       $("input[name='DECLARATIONTYPE']").val("002");
       }
       
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
    <form id="T_BSFW_GCJSXFYS_FORM" method="post">
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
        <div class="bsbox clearfix">
            <div class="bsboxT">
                <ul>
                    <li class="on" style="background:none"><span>项目信息</span></li>
                </ul>
            </div>
            <div class="bsboxC">
                <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: fixed;">
                    <tr>
                        <th> <span class="bscolor1">*</span>报建编号</th>
                        <td>
                            <input type="text"  name="PRJ_NUM" class="tab_text validate[required]" value="${serviceItem.PRJ_NUM}" />
                        </td>
                        <th> <span class="bscolor1">*</span>项目代码</th>
                        <td>
                            <input type="text"  name="PRJ_CODE" class="tab_text validate[required]" value="${serviceItem.PRJ_CODE}" />
                        </td>
                    </tr>
                    <tr>
                        <th> <span class="bscolor1">*</span>项目名称</th>
                        <td colspan="3"><input type="text"  name="PRJ_NAME" class="tab_text validate[required] w838" value="${serviceItem.PRJ_NAME}" /></td>
                    </tr>
                    <tr>
                        <th> <span class="bscolor1">*</span>工程地址</th>
                        <td colspan="3"><input type="text"  name="ADDRESS" class="tab_text validate[required] w838" value="${serviceItem.ADDRESS}" /></td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 建设单位</th>
                        <td colspan="3"><input type="text"  name="BUILD_CORPNAME" class="tab_text validate[required] w838" value="${serviceItem.BUILD_CORPNAME}" /></td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 项目负责人</th>
                        <td>
                            <input type="text" maxlength="60"  name="CONTACTOR" class="tab_text validate[required]" value="${serviceItem.CONTACTOR}" />
                        </td>
                        <th><span class="bscolor1">*</span> 联系电话</th>
                        <td>
                            <input type="text"  name="CONTACT_INFORMATION" class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${serviceItem.CONTACT_INFORMATION}" />
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 法定代表人</th>
                        <td>
                            <input type="text" maxlength="60"  name="LEGAL_REPRESENT" class="tab_text validate[required]" value="${serviceItem.LEGAL_REPRESENT}" />
                        </td>
                        <th><span class="bscolor1">*</span> 联系电话</th>
                        <td>
                            <input type="text"  name="LEGAL_INFORMATION" class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${serviceItem.LEGAL_INFORMATION}" />
                        </td>
                    </tr>
                    <tr>
                        <th> 消防检测机构</th>
                        <td colspan="3"><input type="text"  name="XF_CHECKJG" class="tab_text  w838" maxlength="128" value="${serviceItem.XF_CHECKJG}"/></td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 建设主管部门</th>
                        <td colspan="3"><input type="text" maxlength="60"  name="BUILDMANAGERJG" class="tab_text validate[required] w838" maxlength="128" value="${serviceItem.BUILDMANAGERJG}"/></td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 消防验收申请（备案）编号</th>
                        <td colspan="3"><input type="text" maxlength="60"  name="FCACCEPTANCENUM" class="tab_text w838 validate[required]" maxlength="64" value="${serviceItem.FCACCEPTANCENUM}"/></td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 竣工验收日期</th>
                        <td>
                            <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text validate[required] Wdate" name="YANSHOUCHECKDATE" value="${serviceItem.YANSHOUCHECKDATE}"/>
                        </td>
                        <th><span class="bscolor1">*</span> 申请日期</th>
                        <td>
                            <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" class="tab_text validate[required] Wdate" name="APPLYDATE" value="${serviceItem.APPLYDATE}" />
                        </td>
                    </tr>
                    <tr>
                        <th> 是否特殊项目</th>
                        <td>
                            <input type="radio" value="1" <c:if test="${serviceItem.IFSPECIALBUILDING =='1'}">checked="checked"</c:if>   onclick="isSpecialBuilding(1)" name="IFSPECIALBUILDING" > 是
                            <input type="radio" value="0" <c:if test="${serviceItem.IFSPECIALBUILDING =='0'}">checked="checked"</c:if>   onclick="isSpecialBuilding(0)" name="IFSPECIALBUILDING" > 否
                        </td>
                        <th><span class="bscolor1">*</span> 申报类别</th>
                        <td>
                            <input type="radio" value="001"  <c:if test="${serviceItem.DECLARATIONTYPE =='001'}">checked="checked"</c:if>  name="DECLARATIONTYPE" > 消防验收
                            <input type="radio" value="002"  <c:if test="${serviceItem.DECLARATIONTYPE =='002'}">checked="checked"</c:if>  name="DECLARATIONTYPE" > 消防备案
                        </td>
                    </tr>
                    <tr>
                        <th><span class="bscolor1">*</span> 公开方式</th>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="OPENWAY"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="请选择公开方式" name="OPEN_WAY" id="openWay">
                            </eve:eveselect>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="specialProject">
                <div class="bsboxT">
                    <ul>
                        <li class="on" style="background:none"><span>特殊工程信息</span></li>
                    </ul>
                </div>
                <div class="bsboxC">
                    <table cellpadding="0" cellspacing="0" class="bstable1">
<%--                        <tr>--%>
<%--                            <th><span class="bscolor1">*</span> 特殊工程类别</th>--%>
<%--                            <td colspan="3">--%>
<%--                                <eve:eveselect clazz="eve-input w280 sel validate[required]" dataParams=""--%>
<%--                                               dataInterface="dictionaryService.findDatasForSelect" value=""--%>
<%--                                               defaultEmptyText="请选择申报类别" name="" >--%>
<%--                                </eve:eveselect>--%>
<%--                            </td>--%>
<%--                        </tr>--%>
                        <tr>
                            <th><span class="bscolor1">*</span> 《建设工程消防设计审查意见书》文号</th>
                            <td>
                                <input type="text" maxlength="60"  name="DESIGNREVIEWNUM" class="tab_text validate[required]" value="${serviceItem.DESIGNREVIEWNUM}"/>
                            </td>
                            <th><span class="bscolor1">*</span> 审核日期</th>
                            <td>
                                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text validate[required] Wdate" name="AUDITDATE" value="${serviceItem.AUDITDATE}"/>
                            </td>
                        </tr>
                        <tr><th colspan="4" style="background-color: #FFD39B; text-align: center;">竣工验收情况</th></tr>
                        <tr>
                            <th> 建筑类别</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION1" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION1}"/>
                            </td>
                            <th> 总平面布局</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION2" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION2}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 平面布置</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION3" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION3}"/>
                            </td>
                            <th> 消防水源</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION4" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION4}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 消防电源</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION5" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION5}"/>
                            </td>
                            <th> 装修防火</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION6" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION6}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 建筑保温</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION7" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION7}"/>
                            </td>
                            <th> 防火分区</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION8" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION8}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 室外消火栓系统</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION9" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION9}"/>
                            </td>
                            <th> 火灾自动报警系统</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION10" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION10}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 室内消火栓系统</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION11" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION11}"/>
                            </td>
                            <th> 自动喷水灭火系统</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION12" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION12}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 其他灭火设施</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION13" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION13}"/>
                            </td>
                            <th> 防烟排烟系统</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION14" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION14}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 安全疏散</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION15" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION15}"/>
                            </td>
                            <th> 防烟分区</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION16" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION16}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 消防电梯</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION17" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION17}"/>
                            </td>
                            <th> 防爆</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION18" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION18}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 灭火器</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION19" maxlength="100" class="tab_text" value="${serviceItem.ACCEPTANCESITUATION19}"/>
                            </td>
                            <th> 其他事项</th>
                            <td>
                                <input type="text"  name="OTHERMATTER" maxlength="100" class="tab_text" value="${serviceItem.OTHERMATTER}"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 其他验收情况</th>
                            <td>
                                <input type="text"  name="ACCEPTANCESITUATION20" maxlength="100" class="tab_text w838" value="${serviceItem.ACCEPTANCESITUATION20}"/>
                            </td>
                            <th></th>
                            <td></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="normalProject">
                <div class="bsboxT">
                    <ul>
                        <li class="on" style="background:none"><span>非特殊工程信息</span></li>
                    </ul>
                </div>
                <div class="bsboxC">
                    <table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: auto;">
                        <tr>
                            <th><span class="bscolor1">*</span> 建设工程质量监督机构</th>
                            <td colspan="3"><input type="text"  name="QUALITYDEPART" maxlength="128" class="tab_text validate[required]" value="${serviceItem.QUALITYDEPART}"/></td>
                        </tr>
                        <tr>
                            <th><span class="bscolor1">*</span> 消防设计备案凭证文号</th>
                            <td>
                                <input type="text"  name="DESIGNRECORDNUM" class="tab_text validate[required]" maxlength="128" value="${serviceItem.DESIGNRECORDNUM}"/>
                            </td>
                            <th><span class="bscolor1">*</span> 竣工验收完成日期</th>
                            <td>
                                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text validate[required] Wdate" name="FINISHCHECKDATE" value="${serviceItem.FINISHCHECKDATE}"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <%--隐藏表单--%>
            <input type="hidden" id="ACCEPTANCESITUATION1" name="BUILDINGCATEGORY" value="${serviceItem.BUILDINGCATEGORY}">
            <input type="hidden" id="ACCEPTANCESITUATION2" name="GENERALLAYOUT" value="${serviceItem.GENERALLAYOUT}">
            <input type="hidden" id="ACCEPTANCESITUATION3" name="PLANELAYOUT" value="${serviceItem.PLANELAYOUT}">
            <input type="hidden" id="ACCEPTANCESITUATION4" name="FIRESOURCE" value="${serviceItem.FIRESOURCE}">
            <input type="hidden" id="ACCEPTANCESITUATION5" name="FIREPOWER" value="${serviceItem.FIREPOWER}">
            <input type="hidden" id="ACCEPTANCESITUATION6" name="DECORATIONFC" value="${serviceItem.DECORATIONFC}">
            <input type="hidden" id="ACCEPTANCESITUATION7" name="BUILDINGINSULATION" value="${serviceItem.BUILDINGINSULATION}">
            <input type="hidden" id="ACCEPTANCESITUATION8" name="FIRECOMPARTMENT" value="${serviceItem.FIRECOMPARTMENT}">
            <input type="hidden" id="ACCEPTANCESITUATION9" name="OFHYDRANTSYSTEM" value="${serviceItem.OFHYDRANTSYSTEM}">
            <input type="hidden" id="ACCEPTANCESITUATION10" name="AUTOFIRESYSTEM" value="${serviceItem.AUTOFIRESYSTEM}">
            <input type="hidden" id="ACCEPTANCESITUATION11" name="IFHYDRANTSYSTEM" value="${serviceItem.IFHYDRANTSYSTEM}">
            <input type="hidden" id="ACCEPTANCESITUATION12" name="AUTOMATICSPRINKLER" value="${serviceItem.AUTOMATICSPRINKLER}">
            <input type="hidden" id="ACCEPTANCESITUATION13" name="OTHERFACILITIES" value="${serviceItem.OTHERFACILITIES}">
            <input type="hidden" id="ACCEPTANCESITUATION14" name="SMOKECONTROLSYS" value="${serviceItem.SMOKECONTROLSYS}">
            <input type="hidden" id="ACCEPTANCESITUATION15" name="SAFEEVACUATION" value="${serviceItem.SAFEEVACUATION}">
            <input type="hidden" id="ACCEPTANCESITUATION16" name="SMOKEPREZONE" value="${serviceItem.SMOKEPREZONE}">
            <input type="hidden" id="ACCEPTANCESITUATION17" name="FIREELEVATOR" value="${serviceItem.FIREELEVATOR}">
            <input type="hidden" id="ACCEPTANCESITUATION18" name="EXPLOSIONPROOF" value="${serviceItem.EXPLOSIONPROOF}">
            <input type="hidden" id="ACCEPTANCESITUATION19" name="FIREEXTINGUISHER" value="${serviceItem.FIREEXTINGUISHER}">
            <input type="hidden" id="ACCEPTANCESITUATION110" name="OTHERS" value="${serviceItem.OTHERS}">

            <input type="hidden" name="PROJECT_CODE" value="${serviceItem.PROJECT_CODE}">
            <input type="hidden" name="PROJECT_NAME" value="${serviceItem.PROJECT_NAME}">
            <input type="hidden" name="FC_PRJ_TYPE" value="${serviceItem.FC_PRJ_TYPE}">
            <input type="hidden" name="FC_CHARACTER" value="${serviceItem.FC_CHARACTER}">
            <input type="hidden" name="JSDWSJSQR" value="${serviceItem.JSDWSJSQR}">
            <input type="hidden" name="ZFCXJSZGBM" value="${serviceItem.ZFCXJSZGBM}">
            <input type="hidden" name="XMXFBM" value="${serviceItem.XMXFBM}">
            <input type="hidden" name="SGTSCHGZHBH" value="${serviceItem.SGTSCHGZHBH}">
            <input type="hidden" name="ZYGCXFSCHGSBH" value="${serviceItem.ZYGCXFSCHGSBH}">
            <input type="hidden" name="SET_LOCATION" value="${serviceItem.SET_LOCATION}">
            <input type="hidden" name="CAPACITY" value="${serviceItem.CAPACITY}">
            <input type="hidden" name="SET_TYPE" value="${serviceItem.SET_TYPE}">
            <input type="hidden" name="STORAGE_TYPE" value="${serviceItem.STORAGE_TYPE}">
            <input type="hidden" name="CG_STORAGE_NAME" value="${serviceItem.CG_STORAGE_NAME}">
            <input type="hidden" name="STORAGE_CAPACITY" value="${serviceItem.STORAGE_CAPACITY}">
            <input type="hidden" name="DC_STORAGE_NAME" value="${serviceItem.DC_STORAGE_NAME}">
            <input type="hidden" name="MATERIAL_CATEGORY" value="${serviceItem.MATERIAL_CATEGORY}">
            <input type="hidden" name="INSULATION_LAYER" value="${serviceItem.INSULATION_LAYER}">
            <input type="hidden" name="BW_USE_CHARACTER" value="${serviceItem.BW_USE_CHARACTER}">
            <input type="hidden" name="BW_ORIGINAL_USE" value="${serviceItem.BW_ORIGINAL_USE}">
            <input type="hidden" name="BEGIN_DETE" value="${serviceItem.BEGIN_DETE}">
            <input type="hidden" name="END_DATE" value="${serviceItem.END_DATE}">
            <input type="hidden" name="FC_FACILITIES" value="${serviceItem.FC_FACILITIES}">
            <input type="hidden" name="OTHER_INSTRUCTIONS" value="${serviceItem.OTHER_INSTRUCTIONS}">
            <input type="hidden" name="DECORATION_PART" value="${serviceItem.DECORATION_PART}">
            <input type="hidden" name="DECORATION_AREA" value="${serviceItem.DECORATION_AREA}">
            <input type="hidden" name="DECORATION_LAYER" value="${serviceItem.DECORATION_LAYER}">
            <input type="hidden" name="ZX_USE_CHARACTER" value="${serviceItem.ZX_USE_CHARACTER}">
            <input type="hidden" name="ZX_ORIGINAL_USE" value="${serviceItem.ZX_ORIGINAL_USE}">
            <input type="hidden" name="DTXX_JSON" value="${serviceItem.DTXX_JSON}">
            <input type="hidden" name="ZRZTXX_JSON" value="${serviceItem.ZRZTXX_JSON}">
            <input type="hidden" name="ADMIN_DIVISION" value="${serviceItem.ADMIN_DIVISION}">
            <input type="hidden" name="APPLY_DATE" value="${serviceItem.APPLY_DATE}">
            <input type="hidden" name="IFSPECIALBUILDING" value="${serviceItem.IFSPECIALBUILDING}">
            <input type="hidden" name="DECLARATIONTYPE" value="${serviceItem.DECLARATIONTYPE}">
            <input type="hidden" name="CHECKNUM" value="${serviceItem.CHECKNUM}">


        <%--结束编写基本信息 --%>

        <jsp:include page="./applyuserinfo.jsp" />

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
            <%--开始引入申请表信息部分 --%>
            <jsp:include page="/webpage/website/applyforms/xfsj/applyForm.jsp" >
                <jsp:param value="T_BSFW_GCJSXFYS_FORM" name="formId"/>
                <jsp:param value="41" name="materCode"/>
            </jsp:include>
            <%--结束引入申请表信息部分 --%>
        </div>


    </form>

    <%--开始引入提交DIV界面 --%>
    <jsp:include page="./submitdiv.jsp" >
        <jsp:param value="submitFlow();" name="submitFn"/>
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
</html>