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
    	if(flowSubmitObj){
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BSFW_XMSQBGLHSC_FORM");
    			
    			//获取表单字段权限控制信息
        		var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        		 //初始化表单字段权限控制
        		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BSFW_XMSQBGLHSC_FORM");
    		}
    		if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
    			if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="汇总意见"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXYXT_TR").attr("style","");
    			}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="协调结果"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXYXT_TR").attr("style","");
    				$("#SFXTYZ_TR").attr("style","");
				}else if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="填写意见"){
    				$("#YWCZ_TABLE").attr("style","");
    				$("#SFXYXT_TR").attr("style","");
    				$("#SFXTYZ_TR").attr("style","");
				}  else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区行政审批局领导签发") {
					$("#YWCZ_TABLE").attr("style", "");
					$("#SFTG_TR").attr("style", "");
				} else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "填写公示结果") {
					$("#YWCZ_TABLE").attr("style", "");
					$("#SFTG_TR").attr("style", "");
					$("#SHSFTG_TR").attr("style", "");
					$("#GSJG_TR").attr("style", "");
					$("#gsjgid").attr("class","validate[required]");
				} else if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "拟核准《项目申请报告+》"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区行政审批局项目投资处领导审批"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区审批局领导审签"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区管委会领导签发"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "核准《项目申请报告+》"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "结束") {
					$("#YWCZ_TABLE").attr("style", "");
					$("#SHSFTG_TR").attr("style", "");
					$("#GSJG_TR").attr("style", "");
					if('${busRecord.GSSFTG}'==-1){
						$("#GSXT_TR").attr("style", "");						
					}
					/* $("#NHZ_TR").attr("style", ""); */
				}
    			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "填写公示材料"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区行政审批局项目投资处领导审核"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区行政审批局领导签发"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "填写公示结果"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "集中公示"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "拟核准《项目申请报告+》"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区行政审批局项目投资处领导审批"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区审批局领导审签"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "区管委会领导签发"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "核准《项目申请报告+》"
						|| flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "结束") {
					$("#GS_TABLE").attr("style", "");
					$("#GS_TABLE :input[type='text']")
							.each(
									function(i) {
										if (this.name == "ZTZ") {
											$("input[name='" + this.name + "']")
													.attr("class",
															"tab_text validate[required,custom[number]]");
										} else if (this.name == "LXDH") {
											$("input[name='" + this.name + "']")
													.attr("class",
															"tab_text validate[required,custom[fixOrMobilePhone]]");
										} else if (this.name == "EMAIL") {
											$("input[name='" + this.name + "']")
													.attr("class",
															"tab_text validate[required,custom[email]]");
										} else if (this.name == "POSTCODE") {
											$("input[name='" + this.name + "']")
													.attr("class",
															"tab_text validate[required,custom[chinaZip]]");
										} else if (this.name == "GSKSSJ"
												|| this.name == "GSJSSJ") {
											$("input[name='" + this.name + "']")
													.attr("class",
															"easyui-datebox validate[required]");
										} else {
											$("input[name='" + this.name + "']")
													.attr("class",
															"tab_text validate[required]");
										}
									});
					$("#jsgm").attr("class","validate[required]");
				}
				
				/*公示阶段自动回填内容*/
                if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "集中公示") {
                    if(flowSubmitObj.busRecord) {
                        /*根据事项编码获取工程建设项目所对应的阶段*/
                        $.post("projectApplyController/getProjectJDByItemCode.do",{ITEM_CODE:flowSubmitObj.busRecord.ITEM_CODE},function (data) {
                            if (data) {
                                var dataObj = JSON.parse(data)
                                $("input[name='GSSXMM']").val(flowSubmitObj.busRecord.PROJECT_NAME + dataObj.NAME + '审批前公示');
                            } else {
                                $("input[name='GSSXMM']").val(flowSubmitObj.busRecord.PROJECT_NAME + '审批前公示');
                            }
                        })
                        $("input[name='SBDW']").val(flowSubmitObj.busRecord.ENTERPRISE_NAME);
                        $("input[name='JSDW']").val(flowSubmitObj.busRecord.ENTERPRISE_NAME);
                        $("input[name='JSDD']").val('平潭综合实验区');
                        $("input[name='GSKSSJ']").val(getDateStr(0));
                        $("input[name='GSJSSJ']").val(getDateStr(5));
                        $("input[name='SPCS']").val('平潭综合实验区项目投资处');
                        $("input[name='LXDH']").val('12345转');
                        $("input[name='EMAIL']").val('pt23155007@163.com');
                        $("input[name='POSTCODE']").val('350400');
                        $("input[name='ADDRESS']").val('平潭综合实验区行政审批局（区政务中心大楼）');

                    }
                }
			}
		}
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName : "T_BSFW_XMSQBGLHSC"
		//});

		var start = {
			    elem: "#start",
			    format: "YYYY-MM-DD",
			    istime: false,
			    choose: function(datas){
			        var beginTime = $("input[id='start']").val();
			    	var endTime = $("input[id='end']").val();
			    	if(beginTime!=""&&endTime!=""){
			    		var start = beginTime;
			    		var end = endTime;
			    		if(start>end){
			    			alert("开始时间必须小于等于结束时间,请重新输入!");
			    			$("input[id='start']").val("");
			    		}
			    	}
			    }
			};
			var end = {
			    elem: "#end",
			    format: "YYYY-MM-DD",
			    istime: false,
			    choose: function(datas){
			        var beginTime = $("input[id='start']").val();
			    	var endTime = $("input[id='end']").val();
			    	if(beginTime!=""&&endTime!=""){
			    		var start = beginTime;
			    		var end = endTime;
			    		if(start>end){
			    			alert("结束时间必须大于开始时间,请重新输入!");
			    			$("input[id='end']").val("");
			    		}
			    	}
			    }
			};
			laydate(start);
			laydate(end);
	});

	function FLOW_SubmitFun(flowSubmitObj) {
		//先判断表单是否验证通过
		var validateResult = $("#T_BSFW_XMSQBGLHSC_FORM").validationEngine(
				"validate");
		if (eWebEditor.getHTML() == null || eWebEditor.getHTML() == "") {
			layer.alert("请填写项目投资概况");
			return null;
		}
		$("#NTZXMGK").val(eWebEditor.getHTML());
		if (validateResult) {
			setGrBsjbr();//个人不显示经办人设置经办人的值
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			if (submitMaterFileJson || submitMaterFileJson == "") {
				$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(
						submitMaterFileJson);
				//先获取表单上的所有值
				var formData = FlowUtil
						.getFormEleData("T_BSFW_XMSQBGLHSC_FORM");
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

	function FLOW_TempSaveFun(flowSubmitObj) {
		//先判断表单是否验证通过
		var validateResult = $("#T_BSFW_XMSQBGLHSC_FORM").validationEngine(
				"validate");
		if (eWebEditor.getHTML() == null || eWebEditor.getHTML() == "") {
			parent.layer.alert("请填写项目投资概况");
			return null;
		}
		$("#NTZXMGK").val(eWebEditor.getHTML());
		if (validateResult) {
			var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			if (submitMaterFileJson || submitMaterFileJson == "") {
				$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(
						submitMaterFileJson);
				//先获取表单上的所有值
				var formData = FlowUtil
						.getFormEleData("T_BSFW_XMSQBGLHSC_FORM");
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

	function FLOW_BackFun(flowSubmitObj) {
		return flowSubmitObj;
	}
	
	function FLOW_ViewSumOpinionFun(flowSubmitObj){
		return flowSubmitObj;
	}
</script>
</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
   <form id="T_BSFW_XMSQBGLHSC_FORM" method="post" >
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
    <%--===================重要的隐藏域内容=========== --%>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">基本信息</th>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 拟投资项目编号：</td>
			<td colspan="3">
			<input type="text" class="tab_text validate[required]" value="${busRecord.TZXMBH}" name="TZXMBH" />
			</td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 拟投资项目名称：</td>
			<td><input type="text" maxlength="62"
				class="tab_text validate[required]" name="NTZXMC" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 投资人：</td>
			<td><input type="text" class="tab_text validate[required]"
				name="TZR"  maxlength="14"/></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 项目负责人姓名：</td>
			<td><input type="text" maxlength="14"
				class="tab_text validate[required]" name="XMFZRXM" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 项目负责人电话：</td>
			<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]"
				name="XMFZRDH"  maxlength="14"/></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 项目联系人姓名：</td>
			<td><input type="text" maxlength="14"
				class="tab_text validate[required]" name="XMLXRXM" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 项目联系人电话：</td>
			<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]"
				name="XMLXRDH"  maxlength="14"/></td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">拟投资项目概况</th>
		</tr>
		<tr>			
             <td colspan="4" align="center">
                 <IFRAME ID="eWebEditor" NAME="eWebEditor" 
                 		SRC="plug-in/ewebeditor/ewebeditor.htm?id=NTZXMGK&style=mini500" 
				FRAMEBORDER="0" SCROLLING="no" WIDTH="95%" HEIGHT="400"></IFRAME>
                 <input type="hidden" id="NTZXMGK" name="NTZXMGK" >
             </td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">专家综合审查情况</th>
		</tr>
		<tr>			
             <td colspan="4"><font size="3">　　《项目申请报告+》（送审版）已经于</font> <input type="text" name="ZJZHSCSJ" class="laydate-icon validate[required]"
				onclick="laydate({istime: false, format: 'YYYY-MM-DD'})" style="width:130px; height:26px;" /><font class="tab_color">*</font> <font size="3">提交项目投资窗口进行综合审查,并已按照专家审查意见进一步优化。</font></td>
		</tr>
	</table>
	
	<%--开始引入公用上传材料界面 --%>
	<jsp:include page="./applyMaterList.jsp" />
	<%--结束引入公用上传材料界面 --%>
	
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="GS_TABLE" style="display: none;">
		<tr>
			<th colspan="4">项目审批前公示</th>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 项目名称：</td>
			<td colspan="3"><input type="text" maxlength="62" style="width: 40%"
				name="GSSXMM" /></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 申报单位：</td>
			<td><input type="text" maxlength="62" 
				name="SBDW" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 建设单位：</td>
			<td><input type="text" maxlength="62"  
				name="JSDW" /></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 建设地点：</td>
			<td><input type="text" maxlength="62"
				name="JSDD" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 建设期限：</td>
			<td><input type="text" maxlength="17"
				name="JSQX" /></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 总投资（万元）：</td>
			<td><input type="text"  maxlength="14"
				name="ZTZ" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 资金来源：</td>
			<td><input type="text" maxlength="16"
				name="ZJLY" /></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 建设规模及主要内容：</td>
			<td colspan="3"><textarea rows="10" cols="140" name="JSGMJZYNR"  maxlength="1998" id="jsgm"></textarea> </td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 公示起始时间：</td>
			<td><input type="text" name="GSKSSJ" class="laydate-icon validate[required]"
				id="start" readonly="readonly"
				style="width:182px; height:26px;" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 公示结束时间：</td>
			<td><input type="text" name="GSJSSJ" class="laydate-icon validate[required]"
				id="end" readonly="readonly" style="width:182px; height:26px;" /></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 审批处室：</td>
			<td><input type="text" name="SPCS" maxlength="62"
				class="tab_text validate[]" /></td>
			<td class="tab_width"><font class="tab_color">*</font> 联系电话：</td>
			<td><input type="text" name="LXDH"  maxlength="14"
				/></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 电子邮箱：</td>
			<td><input type="text" name="EMAIL" maxlength="26"
				/></td>
			<td class="tab_width"><font class="tab_color">*</font> 邮政编码：</td>
			<td><input type="text" name="POSTCODE"  maxlength="14"
				/></td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 邮政地址：</td>
			<td colspan="3"><input type="text" maxlength="62" style="width: 40%"
				name="ADDRESS" /></td>
		</tr>
	</table>	
	<div class="tab_height" ></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="YWCZ_TABLE" style="display: none;">
		<tr>
			<th colspan="4">业务操作</th>
		</tr>
		<tr id="SFXYXT_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>是否需要协调：</td>
			<td><eve:radiogroup typecode="SHSFXYXT" width="130px"
					fieldname="SFXYXT" defaultvalue="-1"></eve:radiogroup></td>
		</tr>
		<tr id="SFXTYZ_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>协调是否一致：</td>
			<td><eve:radiogroup typecode="SHSFXTYZ" width="130px"
					fieldname="SFXTYZ" defaultvalue="1"></eve:radiogroup></td>
		</tr>
		<tr id="SFTG_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>签发意见：</td>
			<td><eve:radiogroup typecode="SHSFTG" width="130px"
					fieldname="QFJG" defaultvalue="1"></eve:radiogroup></td>
		</tr>
		<tr id="SHSFTG_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>公示结果：</td>
			<td><eve:radiogroup typecode="GSJG" width="130px"
					fieldname="GSSFTG" defaultvalue="1" onclick="if(this.value=='-1'){$('#GSXT_TR').show();var x=document.getElementsByName('XTSFTG');for(var i=0;i<x.length;i++){if(x[i].value==1)x[i].checked=true;}}else {$('#GSXT_TR').hide();var x=document.getElementsByName('XTSFTG');for(var i=0;i<x.length;i++){if(x[i].checked==true)x[i].checked=false;}}"></eve:radiogroup></td>
		</tr>
		<tr id="GSXT_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>协调结果：</td>
			<td><eve:radiogroup typecode="SHSFTG" width="130px"
					fieldname="XTSFTG" defaultvalue="1" ></eve:radiogroup></td>
		</tr>
		<tr id="GSJG_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>公示情况：</td>
			<td><textarea rows="10" cols="140" name="GSJG" id="gsjgid"></textarea> </td>
		</tr>
		<!-- <tr id="NHZ_TR" style="display: none;">
			<td class="tab_width"><font class="tab_color">*</font>拟核准意见：</td>
			<td><textarea rows="10" cols="140" name="NHZYJ" ></textarea> </td>
		</tr> -->
	</table>
	
	
	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>
	</form>
</body>
</html>
