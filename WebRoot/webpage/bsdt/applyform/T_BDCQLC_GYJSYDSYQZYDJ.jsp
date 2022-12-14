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
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/gyjsydsyqzydj/js/gyjsydsyqzydj.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript">
	$(function() {
	
		<%-- ???????????????????????????????????????????????? --%>
	    $("#djjfxx_gyjsydsyqzydj").attr("style","display:none;");
    	$("#djfzxx_gyjsydsyqzydj").attr("style","display:none;");
    	$("#djgdxx_gyjsydsyqzydj").attr("style","display:none;");
    	  	
		//??????????????????????????????
		$.validationEngine.defaults.autoHidePrompt = true;
		$.validationEngine.defaults.promptPosition = "topRight";
		$.validationEngine.defaults.autoHideDelay = "2000";
		$.validationEngine.defaults.fadeDuration = "0.5";
		$.validationEngine.defaults.autoPositionUpdate = true;
		var flowSubmitObj = FlowUtil.getFlowObj();
		var dealItems = '${dealItem.DEALITEM_NODE}'; //???DB????????????????????????????????????,JBPM6_CHECKDEALITEM
		dealItems = "," + dealItems + ",";
		if (flowSubmitObj) {
			//????????????????????????????????????
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//?????????????????????????????????
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_GYJSYDSYQZYDJ_FORM");			
			if (flowSubmitObj.busRecord) {
				//????????????????????????
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_GYJSYDSYQZYDJ_FORM");
				//??????JSON????????????
				initAutoTable(flowSubmitObj);
				if( flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '??????') {
					//????????????????????????????????????????????????
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '??????'){
						$("#printBtn").show();
					}
					//????????????????????????????????????????????????
					$("#dfspb").attr("onclick","goPrintTemplate('GYJSYDSYQZYDJJSPB','3')");
					$("#sfspb").attr("onclick","goPrintTemplate('GYJSYDSYQZYDJJSPB','3')");
					//????????????????????????????????????????????????
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="??????" || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){
						$("#T_BDCQLC_GYJSYDSYQZYDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");
						//a??????????????????
				 		//$("#dfspb").find(".easyui-linkbutton").attr("disabled",true).css("pointer-events","none");		 	
					}
					if($("input[name='SBMC']").val()){
					}else{						
					  $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
					}
				 }
            
	             if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '??????') {
		            $("#bdcqzh_tr").attr("style","");
		            $("#BDC_DBBTN").removeAttr("disabled");	
		            $("#bdcczr_tr").attr("style","");                		                         
		         }	
		                     
	             //????????????????????????????????????
	             var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
	             if(BDC_OPTYPE != null && BDC_OPTYPE !=""){	 	             	
	                //?????????????????????
	                if(BDC_OPTYPE == "1"){//????????????????????????                    
	                    $("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");
	                    $("#bdcczr_tr").attr("style","");
	                   
	                    //??????????????????
	                    $("#BDC_QZVIEW").attr("style","");
	                    $("#BDC_QZPRINT").attr("style","");
	                    $("#BDC_QZVIEW").removeAttr("disabled");
	                    $("#BDC_QZPRINT").removeAttr("disabled");
	                   
	                    //???????????????
	                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
	                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");	                  
	                    $("#qzbsmsavebtn").attr("style","");
	                    $("#qzbsmsavebtn").removeAttr("disabled");

	                }else if(BDC_OPTYPE == "2"){//????????????????????????	                     
	                   	$("#bdcqzh_tr").attr("style","");
	                   	$("#BDC_DBBTN").remove();
	                   	$("#bdcqzbsm_tr").attr("style","");	                  
	                  	$("#bdcczr_tr").attr("style","");
	                   	$("#qzbsmsavebtn").remove();
	                   	//????????????
	                  	$("#BDC_QZVIEW").attr("style","");
	                   	$("#BDC_QZVIEW").removeAttr("disabled");
	                    //????????????????????????????????????
	                    $("#djjfxx_gyjsydsyqzydj").attr("style","");
    					$("#djfzxx_gyjsydsyqzydj").attr("style","");
    					$("#djgdxx_gyjsydsyqzydj").attr("style","");	                   	    					
    					$("#djjfxx_gyjsydsyqzydj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djfzxx_gyjsydsyqzydj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djgdxx_gyjsydsyqzydj").find("input,select,.Wdate").removeAttr("disabled");	                  
	                   
	                    var currentUser = $("input[name='uploadUserName']").val();
	                    var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	                    $("input[name='DJFZXX_FZRY']").val(currentUser);
	                    $("input[name='DJFZXX_FZSJ']").val(currentTime);
	                    $("input[name='DJJFMX_SFRQ']").val(currentTime);
	                }else if(BDC_OPTYPE == "flag1"){//???????????? -????????????                 	                   
	                    $("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");
	                    $("#bdcczr_tr").attr("style","");	                   
	                    //??????????????????
	                    $("#BDC_QZVIEW").attr("style","");	                   
	                    $("#BDC_QZVIEW").removeAttr("disabled");	                    
	                    //???????????????
	                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
	                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");	                    
	                    //$("#qzbsmsavebtn").attr("style","");
	                    //$("#qzbsmsavebtn").removeAttr("disabled");	                
	                }else if(BDC_OPTYPE == "flag2"){//???????????? -????????????                   	                      					
    					$("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");	                   
	                    $("#bdcczr_tr").attr("style","");
	                    $("#qzbsmsavebtn").remove();
	                    //????????????
	                    $("#BDC_QZVIEW").attr("style","");
	                    $("#BDC_QZVIEW").removeAttr("disabled");	                   
	                    //????????????????????????????????????
	                    $("#djjfxx_gyjsydsyqzydj").attr("style","");
    					$("#djfzxx_gyjsydsyqzydj").attr("style","");
    					$("#djgdxx_gyjsydsyqzydj").attr("style","");
		             }
	            }
	            
	            //?????????????????????????????????
	            var isEndFlow = false;
	            if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {
	                isEndFlow = true;
	            }
	            if(isEndFlow){
	                $("#bdcqzh_tr").attr("style","");
	                $("#BDC_DBBTN").remove();
	                $("#bdcqzbsm_tr").attr("style","");	                
	                $("#bdcczr_tr").attr("style","");
	                $("#qzbsmsavebtn").remove();
	                //????????????
	                $("#BDC_QZVIEW").attr("style","");
	                $("#BDC_QZVIEW").removeAttr("disabled");
	               
	                //????????????????????????????????????
                    $("#djjfxx_gyjsydsyqzydj").attr("style","");
   					$("#djfzxx_gyjsydsyqzydj").attr("style","");
   					$("#djgdxx_gyjsydsyqzydj").attr("style","");
	                $("#T_BDCQLC_GYJSYDSYQZYDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");	   
	            }	 					
			}else{
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');				
			}
		}
	});

/********************************?????????????????????**********************************/
		function MyGetData()//OCX??????????????????????????????
		{	
// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- ??????--!>		
// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- ??????--!>    
		}

		function MyClearData()//OCX??????????????????????????????
		{
	        alert("????????????????????????????????????????????????");
			$("input[name='POWERPEOPLENAME']").val("");   
			$("input[name='POWERPEOPLEIDNUM']").val("");  
		}

		function MyGetErrMsg()//OCX????????????????????????
		{
// 			Status.value = GT2ICROCX.ErrMsg;	   
		}
		
		function PowerPeopleRead(o) //????????????(???????????????)
		{
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //????????????
			//????????????(?????????????????????)
			if (GT2ICROCX.GetState() == 0) {
				GT2ICROCX.ReadCard();
				$("#" + powerPeopleInfoID + " [name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
				$("#" + powerPeopleInfoID + " [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		}
		function PowLegalRead(o) //????????????????????????-???????????????
		{
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //????????????
			//????????????(?????????????????????)
			if (GT2ICROCX.GetState() == 0) {
				GT2ICROCX.ReadCard();
				$("#" + powerPeopleInfoID + " [name='POWLEGALNAME']").val(GT2ICROCX.Name);
				$("#" + powerPeopleInfoID + " [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		}
		function PowAgentRead(o) //????????????????????????-??????????????????
		{
			var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //????????????
			//????????????(?????????????????????)
			if (GT2ICROCX.GetState() == 0) {
				GT2ICROCX.ReadCard();
				$("#" + powerPeopleInfoID + " [name='POWAGENTNAME']").val(GT2ICROCX.Name);
				$("#" + powerPeopleInfoID + " [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		}
	
		function PowSrcRead(o)//????????????(????????????-???????????????)
		{
			var powerSourceInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //????????????
			//????????????(?????????????????????)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#" + powerSourceInfoID + " [name='POWERSOURCE_QLRMC']").val(GT2ICROCX.Name);
				$("#" + powerSourceInfoID + " [name='POWERSOURCE_ZJHM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
			
		function PowFRDHRead(o)//????????????(????????????-????????????)
		{
			var powerSourceInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //????????????
			//????????????(?????????????????????)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#" + powerSourceInfoID + " [name='POWERSOURCE_FDDBRXM']").val(GT2ICROCX.Name);
				$("#" + powerSourceInfoID + " [name='POWERSOURCE_FDDBRZJHM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		
		function PowDLRRead(o)//????????????(????????????-???????????????)
		{
			var powerSourceInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
			GT2ICROCX.PhotoPath = "c:"
			//GT2ICROCX.Start() //????????????
			//????????????(?????????????????????)
			if (GT2ICROCX.GetState() == 0){
				GT2ICROCX.ReadCard();
				$("#" + powerSourceInfoID + " [name='POWERSOURCE_DLRXM']").val(GT2ICROCX.Name);
				$("#" + powerSourceInfoID + " [name='POWERSOURCE_DLRZJHM']").val(GT2ICROCX.CardNo);
				checkLimitPerson();
			}
		} 
		

		function print()//??????
		{  		
			
			GT2ICROCX.PrintFaceImage(0,30,10)//0 ?????????1 ?????????2 ??????
		} 
/********************************?????????????????????**********************************/
</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>//??????????????????
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>//??????????????????
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>//??????????????????
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
	<form id="T_BDCQLC_GYJSYDSYQZYDJ_FORM" method="post">
		<%--===================????????????????????????=========== --%>
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
		
		<%--??????????????????--%>
		<input type="hidden" id="CUR_STEPNAMES" value="${execution.CUR_STEPNAMES}" />	
		<%--????????????--%>	
		<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
		<%--??????????????????--%>
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />
        <!-- ???????????????????????????????????????????????????????????????????????????????????? -->  
        <input type="hidden" name="BDC_OPTYPE" value="${param.bdc_optype}" />
        
        <%-- ????????????????????? --%>
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<%-- ???????????????????????? --%>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>
        
		<%--===================????????????????????????=========== --%>
		
		<%--?????????????????????????????????--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--????????????????????????????????? --%>

		<%--??????????????????????????????--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--?????????????????????????????? --%>

		<%--????????????????????????--%>
		<jsp:include page="./bdcqlc/gyjsydsyqzydj/T_ESTATE_ZYDJ_ACCEPTINFO.jsp" />
		<%--????????????????????????--%>

		<%--???????????????????????????????????? --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--???????????????????????????????????? --%>

		<%--???????????????????????????--%>
		<jsp:include page="./bdcqlc/gyjsydsyqzydj/bdcQlrxx.jsp" />
		<%--???????????????????????????--%>
		
		<%--??????????????????????????????--%>
		<jsp:include page="./bdcqlc/gyjsydsyqzydj/bdcQsly.jsp" />
		<%--??????????????????????????????--%>
		
		<%--????????????????????????-?????????????????????--%>
		<jsp:include page="./bdcqlc/bdcZdqlxx.jsp" />
		<%--????????????????????????-?????????????????????--%>
	
		  <%--???????????????????????????--%>
		<div id="printBtn" name="printBtn" style="display:none;">
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="sfspb" onclick="errorAction();">?????????????????????</a>
						<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="dfspb" onclick="errorAction();">?????????????????????</a>
					</th>
				</tr>
		    </table>
	    </div>
	    <%--???????????????????????????--%>
	    
	    <%--???????????????????????????????????????????????????--%>
	    <jsp:include page="./bdcqlc/bdcqlcDjshOpinion.jsp" /> 
		<%--???????????????????????????????????????????????????--%>
		
		<%-- ????????????????????????????????????????????????????????? --%>
	    <!-- djshxx:??????????????????,djjfxx:??????????????????,djfzxx:??????????????????,djdaxx:?????????????????? -->
	    <!-- optype:??????0??????????????????1????????????????????????????????? -->   
	    <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
	        <jsp:param value="gyjsydsyqzydj" name="domId" />
	        <jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
	    </jsp:include>
	    <%-- ????????????????????????????????????????????????????????? --%>

		
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
