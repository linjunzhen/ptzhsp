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
<eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/cxjmcbxb/js/cxjmcbxb.js"></script> 
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/common/js/ybCommon.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/ybqlc/common/js/ybEmsSend.js"></script>
<script type="text/javascript">
	var ywId = "";//业务ID
	$(function() {			
		//初始化验证引擎的配置
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate = true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		var dealItems = '${dealItem.DEALITEM_NODE}'; //从DB中获取需要特殊处理的环节,JBPM6_CHECKDEALITEM
		dealItems = "," + dealItems + ",";
		if (flowSubmitObj) {
			//获取表单字段权限控制信息
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);//申报号
			//初始化表单字段权限控制
			//FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_YBQLC_CXJMDJ_FORM");				
		 	if (flowSubmitObj.busRecord) {
				//初始化表单字段值
			 	FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_YBQLC_CXJMDJ_FORM");	 		
			 	ywId = 	flowSubmitObj.busRecord.YW_ID;//业务ID
			 	if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES != '开始' || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){//除退回至开始环节外开放受理信息
					$("#slxx").attr("style","");      	  
		      	 }		 			 	
			    if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '受理' &&flowSubmitObj.busRecord.CXJM_TYPE==null ){
					//类型默认选中-居民新参保登记
					$("input[name='CXJM_TYPE']:first").attr('checked', 'checked');
				}	
				if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES=='审查'  
					|| flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){					
					$('#T_YBQLC_CXJMDJ_FORM').find(":radio,:checkbox,input,select").attr('disabled',"disabled");
										
					var cxjmValue = $("input[name='CXJM_TYPE']:checked").val();						 
					//隐藏表格的复选框
					$("#xzConfGrid").datagrid("hideColumn", "ck");	
					$("#xbConfGrid").datagrid("hideColumn", "ck");	
					$("#selectPersonInfosGrid").datagrid("hideColumn", "ck");
									
					//设置datagrid不可编辑
					var xzConfGrid1 = $('#xzConfGrid').datagrid('getColumnOption', 'aae140');
					xzConfGrid1.editor={};
					var xzConfGrid2 = $('#xzConfGrid').datagrid('getColumnOption', 'aae030');
					xzConfGrid2.editor={};
					var xzConfGrid3 = $('#xzConfGrid').datagrid('getColumnOption', 'aae031');
					xzConfGrid3.editor={};
					var xzConfGrid4 = $('#xzConfGrid').datagrid('getColumnOption', 'aac066');
					xzConfGrid4.editor={};

					var xbConfGrid1 = $('#xbConfGrid').datagrid('getColumnOption', 'aac066');
					xbConfGrid1.editor={};
					var xbConfGrid2 = $('#xbConfGrid').datagrid('getColumnOption', 'aae030');
					xbConfGrid2.editor={};	
					var xbConfGrid2 = $('#xbConfGrid').datagrid('getColumnOption', 'bac524');
					xbConfGrid2.editor={};
					
					/* if(cxjmValue != '3' && flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){//除停保环节外办结需显示查询建账信息
					  	$("#page_4").attr("style","");
					  	$("#page_4").find("input,:checkBox,select").attr('disabled',"disabled");
						$("#cxjzGrid").datagrid("resize");
					} */
					
					if(cxjmValue=='2'&&flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES=='审查'){					
						$('#page_2').find(".eflowbutton").attr('style',"background:#94C4FF");
						$("#pushXbBtn").attr("style","");
	    				$("#pushXbBtn").removeAttr("disabled", "disabled");
					}
					if(cxjmValue=='3'&&flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES=='审查'){
						$('#page_3').find(".eflowbutton").attr('style',"background:#94C4FF");
						$("#pushTbBtn").attr("style","");
	    				$("#pushTbBtn").removeAttr("disabled", "disabled");
					}
				}
				if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES!='开始'){
					if($("input[name='SBMC']").val()){
					}else{
						$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+'${serviceItem.ITEM_NAME}');					
					}
				}						
			}else{
				$("input[name='SBMC']").val("-"+'${serviceItem.ITEM_NAME}');
				
			}
						
			//类型选择操作
			cxjmtypeclick();
			
			
		/* 	if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '查询建账'){
				$("#page_4").attr("style","");
				$("#cxjzGrid").datagrid("resize");
				$("#slxx input").each(function(index){
					$(this).attr("disabled","disabled");
				});
				$("#page_1").attr("style","display:none;");
				$("#page_2").attr("style","display:none");
				$("#page_3").attr("style","display:none");	
			} */

			/* 
			//发送地税页面初始化	 
		    var date = new Date();
		    $('input[name="MONTH"]').val(date.getFullYear());//年度默认当前年度
		    //空数据，横向滚动
			$('#fsdsGrid').datagrid({
				onLoadSuccess: function(data){
					if(data.total==0){
						var dc = $(this).data('datagrid').dc;
				        var header2Row = dc.header2.find('tr.datagrid-header-row');
				        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
			        }
				}
			}); */
				
			/* var url = "YbCxjmcbxbController.do?paramjson&exeId="+exeid;
			$('#paramConfGrid').datagrid('options').url = url;
			$('#paramConfGrid').datagrid('reload'); */
		}
		
		//初始化险种信息选中值
		$('#xzConfGrid').datagrid({
			onLoadSuccess : function(data){
				for(var i=0;i<data.rows.length;i++){
					if(data.rows[i].ck==true){
						$('#xzConfGrid').datagrid('selectRow',i);
					}
				}
		}});
	});
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_YBQLC_CXJMDJ_FORM" method="post">
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
		
		<%-- 参保：险种信息--%>
		<input type="hidden" name="XZXX_JSON" />
		<%-- 参保：建账信息--%>
		<input type="hidden" name="CBJZXX_JSON" />
		<%-- 续保：参保人信息--%>
		<input type="hidden" name="CBRXX_JSON" />
		<%-- 续保：建账信息--%>
		<input type="hidden" name="XBJZXX_JSON" />
		<%-- 停保：建账信息--%>
		<input type="hidden" name="TBXX_JSON" />
		
		<%--===================重要的隐藏域内容=========== --%>
		
		<%--===================事项基本信息开始=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="baseinfo">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td > 审批事项：</td>
				<td >${serviceItem.ITEM_NAME}</td>
	            <td style="width: 170px;background: #fbfbfb;"> 承诺时间(工作日):</td>
	            <td >${serviceItem.CNQXGZR}</td>
	        </tr>
			<tr>
				<td >所属部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td style="width: 170px;background: #fbfbfb;"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					style="width: 70%" maxlength="60"
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
		</table>
		<%--===================事项基本信息结束=========== --%>
		
		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>
		
		<%--开始受理信息--%>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="slxx" style="display:none">
			<tr>
				<th colspan="4">受理信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>业务办理子项：</td>
				<td colspan="3">
				   <input type="radio" name="CXJM_TYPE" onclick="cxjmtypeclick();" value="1" <c:if test="${busRecord.CXJM_TYPE=='1'}">checked="checked"</c:if> >居民新参保登记
			       <input type="radio" name="CXJM_TYPE" onclick="cxjmtypeclick();" value="2" <c:if test="${busRecord.CXJM_TYPE=='2'}">checked="checked"</c:if> >居民续保登记
				   <input type="radio" name="CXJM_TYPE" onclick="cxjmtypeclick();" value="3" <c:if test="${busRecord.CXJM_TYPE=='3'}">checked="checked"</c:if> >居民停保减员登记		       
				</td>
			</tr>
		</table>
		<%--结束受理信息--%>
		
		<%--医保通用查询页面--%>
		<div id="ybCommonSearch" style="">
			<jsp:include page="./ybqlc/common/ybCommonSearch.jsp" />			
		</div>
		<%--医保通用查询页面 --%>
		
		<!-- 开始引入居民新参保登记页面 -->
		<div id="page_1" style="display:none;">
			<jsp:include page="./ybqlc/cxjmcbxb/cxjmcb.jsp">
				<jsp:param value="${busRecord.YW_ID}" name="YB_YWID" />
				<jsp:param value="${exeId}" name="exeId" />
			</jsp:include>
		</div>
		<!-- 结束引入居民新参保登记页面 -->
		
		<!-- 开始引入居民续保登记页面 -->
		<div id="page_2" style="display:none;">
			<jsp:include page="./ybqlc/cxjmcbxb/cxjmxb.jsp">
				<jsp:param value="${busRecord.YW_ID}" name="YB_YWID" />
				<jsp:param value="${exeId}" name="exeId" />
			</jsp:include>
		</div>
		<!-- 结束引入居民续保登记页面 -->
		
		<!-- 开始引入居民停保减员登记页面 -->
		<div id="page_3" style="display:none;">	
			<jsp:include page="./ybqlc/cxjmcbxb/cxjmtb.jsp">
				<jsp:param value="${busRecord.YW_ID}" name="YB_YWID" />
				<jsp:param value="${exeId}" name="exeId" />
			</jsp:include>	
		</div>
		<!-- 结束引入居民停保减员登记页面 -->
		
		<%--医保办结结果领取方式&其他信息页面--%>
		<jsp:include page="./ybqlc/common/T_YBQLC_BJJGLQ.jsp" />			
		<%--医保办结结果领取方式&其他信息页面 --%>
		
		<%-- <!-- 开始查询建账页面 -->
		<div id="page_4" style="display:none;">	
			<jsp:include page="./ybqlc/cxjmcbxb/cxjz.jsp">
				<jsp:param value="${busRecord.YW_ID}" name="YB_YWID" />
				<jsp:param value="${exeId}" name="exeId" />
			</jsp:include>	
		</div>
		<!-- 结束查询建账页面-->
		
		<!-- 开始缴费账目信息发送地税页面 -->
		<div id="page_5" style="display:none;">	
			<jsp:include page="./ybqlc/cxjmcbxb/fsds.jsp">
				<jsp:param value="${busRecord.YW_ID}" name="YB_SUB_YWID" />
				<jsp:param value="${busRecord.cxjmtype}" name="cxjmtype"/>
			</jsp:include>	
		</div>
		<!-- 结束缴费账目信息发送地税页面-->  --%>
	</form>
</body>
</html>
