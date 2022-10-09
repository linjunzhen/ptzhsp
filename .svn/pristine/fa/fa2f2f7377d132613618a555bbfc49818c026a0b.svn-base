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
                var exeid = flowSubmitObj.EFLOW_EXEID;
                $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
                //初始化表单字段权限控制
                FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_MSBASQ_FORM");
                //初始化表单字段值
                if(flowSubmitObj.busRecord){
                    initMsbasqFileList(flowSubmitObj.busRecord);
                    FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_MSBASQ_FORM");
                    if(flowSubmitObj.busRecord.RUN_STATUS!=0){
                        $("#userinfo_div input").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                        $("#userinfo_div select").each(function(index){
                            $(this).attr("disabled","disabled");
                        });
                    }

                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME =='四大片区') {
                        $("#opinion").show();
                        $("#sdpqOpinion").show();
                    }
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME =='执法局') {
                        $("#opinion").show();
                        $("#zfjOpinion").show();
                    }
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME =='自然资源与生态环境局') {
                        $("#opinion").show();
                        $("#sthjOpinion").show();
                    }
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME =='自然资源利用处') {
                        $("#opinion").show();
                        $("#zrzyOpinion").show();
                    }
                    /* if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME =='规划局') {
                        $("#opinion").show();
                        $("#ghjOpinion").show();
                    } */
                    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '审批' || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '办结') {
                        $("#opinion").show();
                        $("#sdpqOpinion").show();
                        $("#zfjOpinion").show();
                        $("#sthjOpinion").show();
                        $("#zrzyOpinion").show();
                        //$("#ghjOpinion").show();
                        $("#opinion textarea").each(function () {
                            $(this).attr("disabled", true);
                        });
                        $(".msbasqUploadBtn").hide();
                    }

                    var isEndFlow = false;
                    if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
                        isEndFlow = true;
                    }
                    if(isEndFlow){
                        $("#opinion").show();
                        $("#sdpqOpinion").show();
                        $("#zfjOpinion").show();
                        $("#sthjOpinion").show();
                        $("#zrzyOpinion").show();
                        //$("#ghjOpinion").show();
                    }

                    if (flowSubmitObj.busRecord.ITEM_CODE == '345071904XK01004') {
                        $("#zrzyOpinion").hide();
                        //$("#sthjOpinion").hide();
                    }
                    /* if (flowSubmitObj.busRecord.ITEM_CODE != '345071904XK01004') {
                        $("#ghjOpinion").hide();
                    } */
                }else{
                    $("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
                }

                var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
                if($("input[name='FINISH_GETTYPE']:checked").val()=='02'){
                    $("#addr").attr("style","");
                    $("#addressee").attr("style","");
                    $("#addrmobile").attr("style","");
                    $("#addrprov").attr("style","");
                    $("#addrcity").attr("style","");
                    $("#addrpostcode").attr("style","");
                    $("#addrremarks").attr("style","");
                }
                $("input[name='FINISH_GETTYPE']").click(function(){
                    var radio = document.getElementsByName("FINISH_GETTYPE");
                    for (i=0; i<radio.length; i++) {
                        if (radio[i].checked) {
                            var str=radio[i].value;
                            if("02"==str){
                                $("#addr").attr("style","");
                                $("#addressee").attr("style","");
                                $("#addrmobile").attr("style","");
                                $("#addrprov").attr("style","");
                                $("#addrcity").attr("style","");
                                $("#addrpostcode").attr("style","");
                                $("#addrremarks").attr("style","");
                            }else{
                                $("#addr").attr("style","display:none;");
                                $("#addressee").attr("style","display:none;");
                                $("#addrmobile").attr("style","display:none;");
                                $("#addrprov").attr("style","display:none;");
                                $("#addrcity").attr("style","display:none;");
                                $("#addrpostcode").attr("style","display:none;");
                                $("#addrremarks").attr("style","display:none;");
                            }
                        }
                    }
                });

            }
            //初始化材料列表
            //AppUtil.initNetUploadMaters({
            //	busTableName:"T_BSFW_PTJINFO"
            //});
        });


        function FLOW_SubmitFun(flowSubmitObj){
            //先判断表单是否验证通过
            var validateResult =$("#T_BSFW_MSBASQ_FORM").validationEngine("validate");
            if(validateResult){
                setGrBsjbr();//个人不显示经办人设置经办人的值
                var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
                if(submitMaterFileJson||submitMaterFileJson==""){
                    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
                    
                    //先获取表单上的所有值
                    var formData = FlowUtil.getFormEleData("T_BSFW_MSBASQ_FORM");
                    for(var index in formData){
                        flowSubmitObj[eval("index")] = formData[index];
                    }
                  	//并联审查-民宿申请流程（旧）
                    if (flowSubmitObj.ITEM_CODE && flowSubmitObj.ITEM_CODE == '345071904XK01004') {
                    	var checkNodeName = ['四大片区','执法局','自然资源与生态环境局'];
    	       			if(checkNodeName.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
    	       				flowSubmitObj.EFLOW_JOINPRENODENAMES="四大片区,执法局,自然资源与生态环境局";
    	       			}
                    }else{//民宿备案流程（新）
                    	var checkNodeName = ['四大片区','自然资源与生态环境局','自然资源利用处','执法局'];
    	       			if(checkNodeName.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME)>=0){
    	       				flowSubmitObj.EFLOW_JOINPRENODENAMES="四大片区,自然资源与生态环境局,自然资源利用处,执法局";
    	       			}
                    }
                    return flowSubmitObj;
                }
                return null;
            }
            return null;
        }
        function setGrBsjbr(){
            var val=$('input:checkbox[name="SFXSJBRXX"]:checked').val();
            var lxval=$('input:radio[name="BSYHLX"]:checked').val();
            if(val==null&&lxval=="1"){
                $('input[name="JBR_NAME"]').val($('input[name="SQRMC"]').val());
                var zjlx = $('#SQRZJLX option:selected').val();
                $("#JBR_ZJLX").find("option[value='"+zjlx+"']").attr("selected",true);
                $('input[name="JBR_ZJHM"]').val($('input[name="SQRSFZ"]').val());
                $('input[name="JBR_MOBILE"]').val($('input[name="SQRSJH"]').val());
                $('input[name="JBR_LXDH"]').val($('input[name="SQRDHH"]').val());
                $('input[name="JBR_ADDRESS"]').val($('input[name="SQRLXDZ"]').val());
                $('input[name="JBR_EMAIL"]').val($('input[name="SQRYJ"]').val());
            }
        }
        function FLOW_TempSaveFun(flowSubmitObj){
            //先判断表单是否验证通过
            var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData("T_BSFW_MSBASQ_FORM");
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
<form id="T_BSFW_MSBASQ_FORM" method="post">
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}" /> 
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}" />
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" /> 
    <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" /> 
    <input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" /> 
    <input type="hidden" name="SXLX" value="${serviceItem.SXLX}" /> 
    <input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
    <input type="hidden" value="${busRecord.SDPQ_FILE_ID}" name="SDPQ_FILE_ID" id="SDPQ_FILE_ID">
    <input type="hidden" value="${busRecord.ZFJ_FILE_ID}" name="ZFJ_FILE_ID" id="ZFJ_FILE_ID">
    <input type="hidden" value="${busRecord.GHJ_FILE_ID}" name="GHJ_FILE_ID" id="GHJ_FILE_ID">
    <input type="hidden" value="${busRecord.ZRZY_FILE_ID}" name="ZRZY_FILE_ID" id="ZRZY_FILE_ID">
    <input type="hidden" value="${busRecord.STHJ_FILE_ID}" name="STHJ_FILE_ID" id="STHJ_FILE_ID">
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
            <td colspan="3">
            	<input type="text" class="tab_text validate[required]" style="width: 70%"
                                   maxlength="220" name="SBMC" />
                <span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span>
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

    <%--并联审查材料页面--%>
    <jsp:include page="./msbasq/fillOpinion.jsp" />

    <%--开始引入公用上传材料界面 --%>
    <jsp:include page="./applyMaterList.jsp">
        <jsp:param value="2" name="isWebsite" />
    </jsp:include>
    <%--结束引入公用上传材料界面 --%>

    <div class="tab_height"></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
        <tr>
            <th colspan="2">办理结果领取方式</th>
        </tr>
        <tr>
            <td>
            	<input type="radio" name="FINISH_GETTYPE" value="02"
                <c:if test="${execution.FINISH_GETTYPE=='02' }">checked="checked"</c:if> />快递送达
            </td>
            <td>
            	<input type="radio" name="FINISH_GETTYPE" value="01"
                <c:if test="${execution.FINISH_GETTYPE=='01'||execution.FINISH_GETTYPE==null }">checked="checked"</c:if> />窗口领取
            </td>
        </tr>
        <tr id="addressee" style="display: none;">
            <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>收件人姓名：</span>
                <input type="text" maxlength="16" class="tab_text validate[required]"
                       class="tab_text" value="${execution.RESULT_SEND_ADDRESSEE}"
                       name="RESULT_SEND_ADDRESSEE" />
            </td>
            <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>电话号码：</span>
                <input type="text" maxlength="11" class="tab_text validate[required,custom[mobilePhoneLoose]]"
                       class="tab_text" value="${execution.RESULT_SEND_MOBILE}"
                       name="RESULT_SEND_MOBILE" />
            </td>
        </tr>
        <tr id="addrprov" style="display: none;">
            <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>所属省市：</span>
                <input name="RESULT_SEND_PROV" id="province" class="easyui-combobox"  style="width:120px; height:26px;"
                       data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#city').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#city').combobox('reload',url);
							   }
							}
	                " value="${execution.RESULT_SEND_PROV}" />
                <input name="RESULT_SEND_CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;"
                       data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#county').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#county').combobox('reload',url);
							   }
							}
	                " value="${execution.RESULT_SEND_CITY}" />
                <input name="RESULT_SEND_COUNTY" id="county" class="easyui-combobox" style="width:120px; height:26px;"
                       data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                editable:false,
			                onSelect:function(rec){
							   $('#sllx').combobox('clear');
							   if(rec.TYPE_CODE){
				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);
							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
							       $('#sllx').combobox('reload',url);
							   }
							}
	                " value="${execution.RESULT_SEND_COUNTY}" />
            </td>
            <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>邮政编码：</span>
                <input type="text" maxlength="6" class="tab_text validate[[required],custom[chinaZip]]"
                       class="tab_text" value="${execution.RESULT_SEND_POSTCODE}"
                       name="RESULT_SEND_POSTCODE" />
            </td>
        </tr>
        <tr id="addr" style="display: none;">
            <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>详细地址：</span>
                <textarea rows="5" cols="80" name="RESULT_SEND_ADDR" value="${execution.RESULT_SEND_ADDR}"
                          maxlength="1998" class="validate[required]"></textarea></td>
            </td>
            <td/>
        </tr>
        <tr id="addrremarks" style="display: none;">
            <td><span style="width: 90px;float:left;text-align:right;">邮递备注：</span>
                <textarea rows="5" cols="80" name="RESULT_SEND_REMARKS" value="${execution.RESULT_SEND_REMARKS}"
                          maxlength="1998" class="validate[]"></textarea></td>
            </td>
            <td/>
        </tr>
    </table>
    <div class="tab_height"></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
        <tr>
            <th colspan="2">其他信息</th>
        </tr>
        <tr>
            <td class="tab_width">备注：</td>
            <td><textarea name="REMARK" cols="140" rows="10"
                          class="validate[[],maxSize[500]]"></textarea>
            </td>
            <td/>
        </tr>
    </table>
</form>
</body>
</html>
