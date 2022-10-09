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
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<script type="text/javascript" src="webpage/bsdt/applyform/bdcqlc/common/js/BdcQzPrintUtil.js"></script> 
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/zjfwsyzydj/js/zjfwsyzydj.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>
<script type="text/javascript">
	$(function() {
		<%-- 登记缴费信息、发证、归档信息隐藏 --%>
	    $("#djjfxx_zjfwsyzydj").attr("style","display:none;");
    	$("#djfzxx_zjfwsyzydj").attr("style","display:none;");
    	$("#djgdxx_zjfwsyzydj").attr("style","display:none;");

		$("#ZD_TDYT").combobox({
			url:'bdcGyjsjfwzydjController/loadTdytData.do',
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			prompt:'请选择土地用途',
			panelHeight:'200',
			multiple:true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
			},
			onLoadSuccess: function (row) {  //下拉框数据加载成功调用
				var opts = $(this).combobox('options');
				var target = this;
				var values = $(target).combobox('getValues');//获取选中的值的values
				$.map(values, function (value) {
					var el = opts.finder.getEl(target, value);
					el.find('input.combobox-checkbox')._propAttr('checked', true);
				})
			},
			onSelect: function (row) { //选中一个选项时调用
				var opts = $(this).combobox('options');
				//设置选中值所对应的复选框为选中状态
				var el = opts.finder.getEl(this, row[opts.valueField]);
				el.find('input.combobox-checkbox')._propAttr('checked', true);
				//获取选中的值的values
				// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
				$("#ZD_YTSM").val($(this).combobox('getText'))
			},
			onUnselect: function (row) {
				//不选中一个选项时调用
				var opts = $(this).combobox('options');
				var el = opts.finder.getEl(this, row[opts.valueField]);
				el.find('input.combobox-checkbox')._propAttr('checked', false);
				//获取选中的值的values
				// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
				$("#ZD_YTSM").val($(this).combobox('getText'))
			}
		});

		$("#ZDZL_XIAN").combobox({
			url:'bdcGyjsjfwzydjController/loadZdzlqxData.do',
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight: '200',
			editable: false,
			required:true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onLoadSuccess:function(row) {
				$('#ZDZL_XIAN').combobox('setValue','351001');
			},
			onSelect:function (row) {
				$('#ZDZL_ZHENG').combobox('clear');
				$('#ZDZL_CUN').combobox('clear');
				if (row.VALUE) {
					var url = 'bdcGyjsjfwzydjController/loadZdzlzData.do?value='+row.VALUE;
					$('#ZDZL_ZHENG').combobox('reload',url);
				}
			}
		});

		$("#ZDZL_ZHENG").combobox({
			url:'bdcGyjsjfwzydjController/loadZdzlzData.do',
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight: '200',
			editable: false,
			required:true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onSelect:function (row) {
				$('#ZDZL_CUN').combobox('clear');
				if (row.VALUE) {
					var url = 'bdcGyjsjfwzydjController/loadZdzlxcData.do?value='+row.VALUE;
					$('#ZDZL_CUN').combobox('reload',url);
				}
			}
		});

		$("#ZDZL_CUN").combobox({
			url:'bdcGyjsjfwzydjController/loadZdzlxcData.do',
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight: '200',
			editable: false,
			required:true,
			formatter: function (row) {
				var opts = $(this).combobox('options');
				return row[opts.textField]
			},
			onSelect:function (row) {

			}
		});

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
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_ZJFWSYZYDJ_FORM");			
			if (flowSubmitObj.busRecord) {
				//初始化表单字段值
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_ZJFWSYZYDJ_FORM");
				//初始JSON格式信息
				initAutoTable(flowSubmitObj);
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

				if( flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') {
					//除开始环节外，审批表均可进行打印
					$("#dfspb").attr("onclick","goPrintTemplate('ZJFWSYZYDJJSPB','3')");
					$("#sfspb").attr("onclick","goPrintTemplate('ZJFWSYZYDJJSPB','3')");
					//开始、受理环节之后数据均不可编辑
					if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME!="受理" || flowSubmitObj.EFLOW_EXERUNSTATUS == '2'){
						$("#T_BDCQLC_ZJFWSYZYDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");
						//a标签按钮禁用
				 		//$("#dfspb").find(".easyui-linkbutton").attr("disabled",true).css("pointer-events","none");		 	
					}
					if($("input[name='SBMC']").val()){
					}else{						
					  $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
					}
				 }
            
	             if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
		            $("#bdcqzh_tr").attr("style","");
		            $("#BDC_DBBTN").removeAttr("disabled");	
		            $("#bdcczr_tr").attr("style","");                		                         
		         }	
		                     
	             //若从后台收费发证功能进入
	             var BDC_OPTYPE = $("input[name='BDC_OPTYPE']").val();
	             if(BDC_OPTYPE != null && BDC_OPTYPE !=""){	 	             	
	                //填写权证标识码
	                if(BDC_OPTYPE == "1"){//缮证（权证打印）                    
	                    $("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");
	                    $("#bdcczr_tr").attr("style","");
	                   
	                    //权证打印按钮
	                    $("#BDC_QZVIEW").attr("style","");
	                    $("#BDC_QZPRINT").attr("style","");
	                    $("#BDC_QZVIEW").removeAttr("disabled");
	                    $("#BDC_QZPRINT").removeAttr("disabled");
	                   
	                    //权证标识码
	                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
	                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");	                  
	                    $("#qzbsmsavebtn").attr("style","");
	                    $("#qzbsmsavebtn").removeAttr("disabled");

	                }else if(BDC_OPTYPE == "2"){//打证（缴费发证）	                     
	                   	$("#bdcqzh_tr").attr("style","");
	                   	$("#BDC_DBBTN").remove();
	                   	$("#bdcqzbsm_tr").attr("style","");	                  
	                  	$("#bdcczr_tr").attr("style","");
	                   	$("#qzbsmsavebtn").remove();
	                   	//证书预览
	                  	$("#BDC_QZVIEW").attr("style","");
	                   	$("#BDC_QZVIEW").removeAttr("disabled");
	                    //登记缴费、发证、归档信息
	                    $("#djjfxx_zjfwsyzydj").attr("style","");
    					$("#djfzxx_zjfwsyzydj").attr("style","");
    					$("#djgdxx_zjfwsyzydj").attr("style","");	                   	    					
    					$("#djjfxx_zjfwsyzydj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djfzxx_zjfwsyzydj").find("input,select,.Wdate").removeAttr("disabled");
    					$("#djgdxx_zjfwsyzydj").find("input,select,.Wdate").removeAttr("disabled");	                  
	                   
	                    var currentUser = $("input[name='uploadUserName']").val();
	                    var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	                    $("input[name='DJFZXX_FZRY']").val(currentUser);
	                    $("input[name='DJFZXX_FZSJ']").val(currentTime);
	                    $("input[name='DJJFMX_SFRQ']").val(currentTime);
	                }else if(BDC_OPTYPE == "flag1"){//权证打印 -我的打证                 	                   
	                    $("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");
	                    $("#bdcczr_tr").attr("style","");	                   
	                    //权证预览按钮
	                    $("#BDC_QZVIEW").attr("style","");	                   
	                    $("#BDC_QZVIEW").removeAttr("disabled");	                    
	                    //权证标识码
	                    $("input[name='BDC_QZBSM']").removeAttr("disabled");
	                    $("input[name='BDC_QZBSM']").addClass(" validate[required]");	                    
	                    //$("#qzbsmsavebtn").attr("style","");
	                    //$("#qzbsmsavebtn").removeAttr("disabled");	                
	                }else if(BDC_OPTYPE == "flag2"){//缴费发证 -我的发证                   	                      					
    					$("#bdcqzh_tr").attr("style","");
	                    $("#BDC_DBBTN").remove();
	                    $("#bdcqzbsm_tr").attr("style","");	                   
	                    $("#bdcczr_tr").attr("style","");
	                    $("#qzbsmsavebtn").remove();
	                    //证书预览
	                    $("#BDC_QZVIEW").attr("style","");
	                    $("#BDC_QZVIEW").removeAttr("disabled");	                   
	                    //登记缴费、发证、归档信息
	                    $("#djjfxx_zjfwsyzydj").attr("style","");
    					$("#djfzxx_zjfwsyzydj").attr("style","");
    					$("#djgdxx_zjfwsyzydj").attr("style","");
		             }
	            }
	            
	            //流程结束，控制页面只读
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
	                //证书预览
	                $("#BDC_QZVIEW").attr("style","");
	                $("#BDC_QZVIEW").removeAttr("disabled");
	               
	                //登记缴费、发证、归档信息
                    $("#djjfxx_zjfwsyzydj").attr("style","");
   					$("#djfzxx_zjfwsyzydj").attr("style","");
   					$("#djgdxx_zjfwsyzydj").attr("style","");
	                $("#T_BDCQLC_ZJFWSYZYDJ_FORM").find("input,select,.Wdate").attr("disabled","disabled");	   
	            }	 					
			}else{
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');				
			}
		}
	});

	

	//------------------------------------身份身份证读取开始---------------------------------------------------
	function MyGetData() //OCX读卡成功后的回调函数
	{
		//          POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>     
		//          POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>    
	}

	function MyClearData() //OCX读卡失败后的回调函数
	{
		alert("未能有效识别身份证，请重新读卡！");
		$("input[name='POWERPEOPLENAME']").val("");
		$("input[name='POWERPEOPLEIDNUM']").val("");
	}

	function MyGetErrMsg() //OCX读卡消息回调函数
	{
		//          Status.value = GT2ICROCX.ErrMsg;       
	}

	function PowerPeopleRead(o) //开始读卡(权利人信息)
	{
		var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0) {
			GT2ICROCX.ReadCard();
			$("#" + powerPeopleInfoID + " [name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
			$("#" + powerPeopleInfoID + " [name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo);
			checkLimitPerson();
		}
	}
	function PowLegalRead(o) //开始读卡（权利人-法人信息）
	{
		var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0) {
			GT2ICROCX.ReadCard();
			$("#" + powerPeopleInfoID + " [name='POWLEGALNAME']").val(GT2ICROCX.Name);
			$("#" + powerPeopleInfoID + " [name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo);
			checkLimitPerson();
		}
	}
	function PowAgentRead(o) //开始读卡（权利人-代理人信息）
	{
		var powerPeopleInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0) {
			GT2ICROCX.ReadCard();
			$("#" + powerPeopleInfoID + " [name='POWAGENTNAME']").val(GT2ICROCX.Name);
			$("#" + powerPeopleInfoID + " [name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo);
			checkLimitPerson();
		}
	}
	
	function PowSrcRead(o)//开始读卡（权属来源-权利人信息）
	{
		var powerSourceInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0){
			GT2ICROCX.ReadCard();
			$("#" + powerSourceInfoID + " [name='POWERSOURCE_QLRMC']").val(GT2ICROCX.Name);
			$("#" + powerSourceInfoID + " [name='POWERSOURCE_ZJHM']").val(GT2ICROCX.CardNo);
			checkLimitPerson();
		}
	} 
		
	function PowFRDHRead(o)//开始读卡（权属来源-法人信息）
	{
		var powerSourceInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0){
			GT2ICROCX.ReadCard();
			$("#" + powerSourceInfoID + " [name='POWERSOURCE_FDDBRXM']").val(GT2ICROCX.Name);
			$("#" + powerSourceInfoID + " [name='POWERSOURCE_FDDBRZJHM']").val(GT2ICROCX.CardNo);
			checkLimitPerson();
		}
	} 
	
	function PowDLRRead(o)//开始读卡（权属来源-代理人信息）
	{
		var powerSourceInfoID = $(o).parent().parent().parent().parent().parent().parent().attr('id');
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0){
			GT2ICROCX.ReadCard();
			$("#" + powerSourceInfoID + " [name='POWERSOURCE_DLRXM']").val(GT2ICROCX.Name);
			$("#" + powerSourceInfoID + " [name='POWERSOURCE_DLRZJHM']").val(GT2ICROCX.CardNo);
			checkLimitPerson();
		}
	} 
		

	function print() //打印
	{
		GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
	}
	//------------------------------------身份身份证读取结束---------------------------------------------------
</script>
<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>	//设置回调函数
	MyGetData()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>	//设置回调函数
	MyGetErrMsg()
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>	//设置回调函数
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
	<form id="T_BDCQLC_ZJFWSYZYDJ_FORM" method="post">
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
        
        <%-- 权利人信息明细 --%>
		<input type="hidden" name="POWERPEOPLEINFO_JSON" id="POWERPEOPLEINFO_JSON"/>
		<%-- 权属来源信息明细 --%>
		<input type="hidden" name="POWERSOURCEINFO_JSON" id="POWERSOURCEINFO_JSON"/>

		<%--===================重要的隐藏域内容=========== --%>
		<%--开始引入不动产基本信息--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--开始引入不动产基本信息 --%>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
		
		<%--开始引入受理信息--%>
		<jsp:include page="./bdcqlc/zjfwsyzydj/T_ESTATE_ZYDJ_ACCEPTINFO.jsp" /> 
		<%--结束引入受理信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始引入权利人信息--%>
		<jsp:include page="./bdcqlc/zjfwsyzydj/bdcQlrxx.jsp" />
		<%--结束引入权利人信息--%>

		<%--开始引入权属来源信息--%>
		<jsp:include page="./bdcqlc/zjfwsyzydj/bdcQsly.jsp" />
		<%--结束引入权属来源信息--%>
		
		<%--开始引入宗地信息-国有权力人信息--%>
  		<jsp:include page="./bdcqlc/zjfwsyzydj/bdcZdqlxx.jsp" />
 	    <%--开始引入宗地信息-国有权力人信息--%>

		<%-- 引入登记审核、缴费信息、发证、归档信息 --%>
	    <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
	    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->   
	    <jsp:include page="./bdcqlc/common/djauditinfo.jsp">
	        <jsp:param value="zjfwsyzydj" name="domId" />
	        <jsp:param value="djjfxx,djfzxx,djdaxx" name="initDomShow" />
	    </jsp:include>
	    <%-- 引入登记审核、缴费信息、发证、归档信息 --%>
	    
	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
