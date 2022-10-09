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
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer,autocomplete"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<script type="text/javascript" src="webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script> 
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/yydj/js/bdcqlcyydj.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>

<script type="text/javascript">
	$(function() {
	
		<%-- 登记缴费信息、发证、归档信息隐藏 --%>
	    $("#djjfxx_yydj").attr("style","display:none;");
    	$("#djfzxx_yydj").attr("style","display:none;");
    	$("#djgdxx_yydj").attr("style","display:none;");
    	  	
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
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_YYDJ_FORM");			
			if (flowSubmitObj.busRecord) {
				//初始化表单字段值
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_YYDJ_FORM");
				//初始JSON格式信息
				initAutoTable(flowSubmitObj);
				if( flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
					//除开始环节外，审批表均可进行打印
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '受理'){
							$("#printBtn").show();
					}
					$("#dfspb").attr("onclick","goPrintTemplate('YYDJJSPB','3')");
					$("#sfspb").attr("onclick","goPrintTemplate('YYDJJSPB','3')");
					//开始、受理环节之后数据均不可编辑
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="受理" || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){
						$("#T_BDCQLC_YYDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");
						//a标签按钮禁用
				 		//$("#dfspb").find(".easyui-linkbutton").attr("disabled",true).css("pointer-events","none");		 	
					}
					if($("input[name='SBMC']").val()){
					}else{						
					  $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
					}
				 }
				
				 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
	                $("#dbxx").attr("style","");	             
	                $("#BDC_DBBTN").removeAttr("disabled");	               
	                $("input[name='YYDJRB_DJJG']").val("平潭综合实验区不动产登记中心");         
	             }
	            
	             //若从后台收费发证功能进入
	             var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
	             if(BDC_OPTYPE != null && BDC_OPTYPE !=""){	 
	             	 $("#dbxx").attr("style","");//登簿信息开放  
	                //填写权证标识码
	                if(BDC_OPTYPE == "1"){//缮证（权证打印）                    
	                    $("#BDC_DBBTN").remove();
	                    //不动产登记证明打印按钮	                   
	                    $("#BDC_QZPRINT").attr("style","");	               
	                    $("#BDC_QZPRINT").removeAttr("disabled");  
	                }else if(BDC_OPTYPE == "2"){//打证（缴费发证）	                   
	                    $("#BDC_DBBTN").remove();
	                    //登记缴费、发证、归档信息
	                    $("#djjfxx_yydj").attr("style","");
    					$("#djfzxx_yydj").attr("style","");
    					$("#djgdxx_yydj").attr("style","");
    					    					
    					$("#djjfxx_yydj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djfzxx_yydj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djgdxx_yydj").find("input,select,.Wdate").removeAttr("disabled");
    					
	                    var currentUser = $("input[name='uploadUserName']").val();
	                    var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	                    $("input[name='DJFZXX_FZRY']").val(currentUser);//发证人员
	                    $("input[name='DJFZXX_FZSJ']").val(currentTime);//发证日期
	                    $("input[name='DJJFMX_SFRQ']").val(currentTime);//缴费日期
	                }else if(BDC_OPTYPE == "flag1"){//权证打印 -我的打证                 
	                    $("#BDC_DBBTN").remove(); 
	                }else if(BDC_OPTYPE == "flag2"){//缴费发证 -我的发证                   
	                    $("#BDC_DBBTN").remove();
	                    //登记缴费、发证、归档信息
	                    $("#djjfxx_yydj").attr("style","");
    					$("#djfzxx_yydj").attr("style","");
    					$("#djgdxx_yydj").attr("style","");
	                }
	            }
	            //流程结束，控制页面只读
	            var isEndFlow = false;
	            if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
	                isEndFlow = true;
	            }
	            if(isEndFlow){
	            	$("#dbxx").attr("style","");//登簿信息开放  	                	           
	                $("#BDC_DBBTN").remove();
              	    //登记缴费、发证、归档信息
                    $("#djjfxx_yydj").attr("style","");
   					$("#djfzxx_yydj").attr("style","");
   					$("#djgdxx_yydj").attr("style","");
	                $("#T_BDCQLC_YYDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");	               
	            }	 					
			}else{
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');				
			}
		}
	});

/********************************身份证读卡开始**********************************/
	function SQRRead(){//申请人信息读卡
	    GT2ICROCX.PhotoPath = "c:"
	    //GT2ICROCX.Start() //循环读卡
	    //单次读卡(点击一次读一次)
	    if (GT2ICROCX.GetState() == 0) {
	        GT2ICROCX.ReadCard();
	        $("input[name='JBXX_NAME']").val(GT2ICROCX.Name);
	        $("input[name='JBXX_IDNO']").val(GT2ICROCX.CardNo);
	    }
	}
	
	function print() //打印
	{
		GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
	}
/********************************身份证读卡结束**********************************/
</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//设置回调函数
	MyClearData()
</SCRIPT>
<style>
	.sel {
		border: solid 1px red;
	}
</style>
</head>

<body>
	<input type="hidden" id="sxtsx" name="sxtsx" value="0" />
	<input id="AutoExposure" type="hidden" onclick="autoexposure()" />
	<input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
	<input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
	<input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_YYDJ_FORM" method="post">
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
		
		<%--当前环节名称--%>
		<input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />	
		<%--登簿状态--%>	
		<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
		<%--登簿异常原因--%>
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
        <!-- 后台控制证书收费发证状态的标识位仅涉及不动产收费发证需要 -->   
        <input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
		<%--===================重要的隐藏域内容=========== --%>
		
		<%--开始引入不动产基本信息--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--开始引入不动产基本信息 --%>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始引入受理信息--%>
		<jsp:include page="./bdcqlc/yydj/T_BDCYYDJ_ACCEPTINFO.jsp" />
		<%--结束引入受理信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入基本信息--%>
			<jsp:include page="./bdcqlc/yydj/yydjJbxx.jsp" />
		<%--结束引入基本信息--%>

		<%--开始引入异议对象不动产--%>
			<jsp:include page="./bdcqlc/yydj/yydxbdc.jsp" />
		<%--结束引入异议对象不动产--%>
		
		<%--开始引入异议登记入簿--%>
		<div id="dbxx" style="display:none;">
			<jsp:include page="./bdcqlc/yydj/yydjrb.jsp" />
		</div>
		<%--结束引入异议登记入簿--%>
		
	
		
		<%--开始审批表打印按钮--%>
		<div id="printBtn" name="printBtn" style="display:none;">
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="sfspb" onclick="errorAction();">审批表（双方）</a>
						<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="dfspb" onclick="errorAction();">审批表（单方）</a>
					</th>
				</tr>
		    </table>
	    </div>
	    <%--结束审批表打印按钮--%>
		
		<%--开始登记审核意见信息（不动产通用）--%>
	    <jsp:include page="./bdcqlc/bdcqlcDjshOpinion.jsp" /> 
		<%--结束登记审核意见信息（不动产通用）--%>
		
		
		<%-- 引入登记审核、缴费信息、发证、归档信息 --%>
	    <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
	    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->   
	    <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
	        <jsp:param value="yydj" name="domId" />
	        <jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
	    </jsp:include>
	    <%-- 引入登记审核、缴费信息、发证、归档信息 --%>

		
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
