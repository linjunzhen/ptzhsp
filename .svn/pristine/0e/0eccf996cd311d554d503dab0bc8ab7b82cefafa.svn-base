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
	src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/dyqzxdj/js/bdcdyqzxdj.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/dyqzxdj/js/dateTimeUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/bsdt/applyform/bdcqlc/js/bdcUtil.js"></script>

<script type="text/javascript">
	$(function() {
	
	    /*审批表在开始环节无法打印*/
		$("#SPBDF").attr("onclick","errorAction();");
		$("#SPBSF").attr("onclick","errorAction();");
	
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
			
			
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始' || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '受理') {
				 $("#djshxx").hide();
				 $("#doBooking").hide();
			}
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
				// 登簿环节初始化公告开始结束时间
				setDjshxx();
				$("#doBooking").show();
			}
			
			//获取表单字段权限控制信息
			var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
			var exeid = flowSubmitObj.EFLOW_EXEID;
			$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
			//初始化表单字段权限控制
			FlowUtil.initFormFieldRightControl(currentNodeFieldRights, "T_BDCQLC_DYQZXDJ_FORM");
			//初始化表单字段值
			if (flowSubmitObj.busRecord) {
				initAutoTable(flowSubmitObj); //初始化注销明细（json格式数据）
				FlowUtil.initFormFieldValue(flowSubmitObj.busRecord, "T_BDCQLC_DYQZXDJ_FORM");
				disabledDbBtn("doBooking");
				/*其它环节审批表可以进行打印*/
				if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始' && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '受理' 
					&& flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '待受理'){
					$("#printBtn").show();
				}
				$("#SPBDF").attr("onclick","goPrintTemplate('DYQZXDJSPBYS','3');");
				$("#SPBSF").attr("onclick","goPrintTemplate('DYQZXDJSPBYS',3);");
				
				// 结束环节处理
				if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.busRecord.RUN_STATUS != 1) {					
					$("#T_BDCQLC_DYQZXDJ_FORM input").each(function(index) {
						$(this).attr("disabled", "disabled");
					});
					$("#T_BDCQLC_DYQZXDJ_FORM select").each(function(index) {
						$(this).attr("disabled", "disabled");
					});
					$("#T_BDCQLC_DYQZXDJ_FORM textarea").each(function(index) {
						$(this).attr("disabled", "disabled");
					});
				}
				// 受理环节不展示登簿表格数据								
				if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME != '开始') { //非草稿状态设置为不可编辑
					$("#T_BDCQLC_DYQZXDJ_FORM input , #T_BDCQLC_DYQZXDJ_FORM select").each(function (index) {
						$(this).attr("disabled", "disabled");
					});
					if ($("input[name='SBMC']").val()) {
					} else {
						$("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME + "-" + '${serviceItem.ITEM_NAME}');
					};
					$("#zxmxAdd").hide();
				} else if (flowSubmitObj.busRecord.RUN_STATUS != 0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
					// $("#userinfo_div input").each(function(index) {
					// 	$(this).attr("disabled", "disabled");
					// });
					// $("#userinfo_div select").each(function(index) {
					// 	$(this).attr("disabled", "disabled");
					// });
					// $("#baseinfo input").each(function(index) {
					// 	$(this).attr("disabled", "disabled");
					// });
				}
				var isAuditPass = flowSubmitObj.busRecord.ISAUDITPASS;
				//设置文件是否合规
				if (isAuditPass == "-1") {
					$("input:radio[name='isAuditPass'][value='-1']").attr("checked", true);
				}
			} else {
				$("input[name='SBMC']").val("-" + '${serviceItem.ITEM_NAME}');
				//持证类型默认分别持证
				$("select[name='TAKECARD_TYPE']").val("0");
				//申请人证件类型默认为'身份证'
				$("select[name='SQRZJLX']").val("SF");
				$("input[name='SQRSFZ']").addClass(",custom[vidcard]");
			}

			var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME && dealItems && dealItems != "") {
				$("#ptcyjb").attr("style", "");
				if (flowSubmitObj.busRecord && flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "开始") {
					$("input[name='JBR_NAME']").removeAttr('readonly');
				}
			}
			
			if($("#zxmxTable").find("tr").length>1){				
				$("input[name='BDC_ZXZS']").val($("#zxmxTable").find("tr").length-1);
			} else{				
				$("input[name='BDC_ZXZS']").val(1);
			}
		}
	});

	// -------------------受理信息上传及高拍仪 开始-------------------------------------------------------------
	$(function() {
		var fileids = $("input[name='SLXX_FILE_ID']").val();
		$.post("executionController.do?getResultFiles&fileIds=" + fileids, {
			fileIds : fileids
		}, function(resultJson) {
			if (resultJson != "websessiontimeout") {
				$("#SLXX_fileListDiv").html("");
				var newhtml = "";
				var list = resultJson.rows;
				for (var i = 0; i < list.length; i++) {
					newhtml += '<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
					newhtml += '<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\'' + list[i].FILE_ID + '\');">';
					newhtml += list[i].FILE_NAME + '</a>';
					newhtml += '</p>';
				}
				$("#SLXX_fileListDiv").html(newhtml);
			}
		});
	});

	// 确认登簿后自动回填 注销人、注销时间、注销宗数
	function doBooking() {
		$("#ZXR").val('${sessionScope.curLoginUser.fullname}');
		$("#ZXSJ").val('${nowTime}');
	//$("#ZXZS").val('默认1，为抵押明细中的不动产单元数');
	}

	// 流程提交自动计算公告结束时间
	function setDjshxx() {
		$("#DJSH_GGQSSJ").val(moment().format('YYYY-MM-DD'));
		$("#DJSH_GGJSSJ").val(moment().format('YYYY-MM-DD'));
		var delay = $("#DJSH_GGSC").val();
		if (delay != null && delay != undefined) {
			$("#DJSH_GGJSSJ").val(moment().add('days', delay).format('YYYY-MM-DD'));
		}
	}

	function openSlxxFileUploadDialog() {
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDCQLC_DYQZXDJ', {
			title : "上传(附件)",
			width : "620px",
			height : "240px",
			lock : true,
			resize : false,
			close : function() {
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if (uploadedFileInfo) {
					if (uploadedFileInfo.fileId) {
						var fileType = 'gif,jpg,jpeg,bmp,png';
						var attachType = 'attach';
						if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
							attachType = "image";
						}
						var fileurl = $("input[name='SLXX_FILE_URL']").val();
						var fileid = $("input[name='SLXX_FILE_ID']").val();
						if (fileurl != "" && fileurl != null) {
							$("input[name='SLXX_FILE_URL']").val(fileurl + ';download/fileDownload?attachId=' + uploadedFileInfo.fileId + '&attachType=' + attachType);
							$("input[name='SLXX_FILE_ID']").val(fileid + ";" + uploadedFileInfo.fileId);
						} else {
							$("input[name='SLXX_FILE_URL']").val('download/fileDownload?attachId=' + uploadedFileInfo.fileId + '&attachType=' + attachType);
							$("input[name='SLXX_FILE_ID']").val(uploadedFileInfo.fileId);
						}
						var spanHtml = "<p id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
							+ "download/fileDownload?attachId=" + uploadedFileInfo.fileId
							+ "&attachType=" + attachType + "\" ";
						spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
						spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>"
						spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"SLXXdelUploadFile('" + uploadedFileInfo.fileId + "','');\" ><font color=\"red\">删除</font></a></p>"
						$("#SLXX_fileListDiv").append(spanHtml);
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	function SLXXchooseCtrl() {
		var gpytype = $('input[name="GPYTYPE"]:checked').val();
		if (gpytype == "" || gpytype == undefined) {
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
		} else if (gpytype == "1") {
			SLXXbindScanCtrl();
		} else if (gpytype == "2") {
			SLXXbindScanCtrlLT();
		} else if (gpytype == "3") {
			SLXXbindScanCtrlZT();
		}
	}
	function SLXXbindScanCtrl() {
		var onlineBusTableName = "T_BDCQLC_DYQZXDJ";
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
			"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
				title : "高拍仪",
				width : "800px",
				lock : true,
				resize : false,
				height : "620px",
				close : function() {
					var resultJsonInfo = art.dialog.data("resultJsonInfo");
					if (resultJsonInfo) {
						SLXXinitScanUploadMaters(resultJsonInfo);
						art.dialog.removeData("resultJsonInfo");
					}
				}
			}, false);
	}
	function SLXXbindScanCtrlLT() {
		var onlineBusTableName = "T_BDCQLC_DYQZXDJ";
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlLT.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
			"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
				title : "高拍仪",
				width : "800px",
				lock : true,
				resize : false,
				height : "620px",
				close : function() {
					var resultJsonInfo = art.dialog.data("resultJsonInfo");
					if (resultJsonInfo) {
						SLXXinitScanUploadMaters(resultJsonInfo);
						art.dialog.removeData("resultJsonInfo");
					}
				}
			}, false);
	}
	function SLXXbindScanCtrlZT() {
		var onlineBusTableName = "T_BDCQLC_DYQZXDJ";
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName=" + onlineBusTableName +
			"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
				title : "高拍仪",
				width : "800px",
				lock : true,
				resize : false,
				height : "620px",
				close : function() {
					var resultJsonInfo = art.dialog.data("resultJsonInfo");
					if (resultJsonInfo) {
						SLXXinitScanUploadMaters(resultJsonInfo);
						art.dialog.removeData("resultJsonInfo");
					}
				}
			}, false);
	}

	function SLXXinitScanUploadMaters(resultJson) {
		for (var i = 0; i < resultJson.length; i++) {

			var fileurl = $("input[name='SLXX_FILE_URL']").val();
			var fileid = $("input[name='SLXX_FILE_ID']").val();
			if (fileurl != "" && fileurl != null) {
				$("input[name='SLXX_FILE_URL']").val(fileurl + ";" + resultJson[i].data.filePath);
				$("input[name='SLXX_FILE_ID']").val(fileid + ";" + resultJson[i].data.fileId);
			} else {
				$("input[name='SLXX_FILE_URL']").val(resultJson[i].data.filePath);
				$("input[name='SLXX_FILE_ID']").val(resultJson[i].data.fileId);
			}
			//获取文件ID
			var fileId = resultJson[i].data.fileId;
			//获取文件名称
			var fileName = resultJson[i].data.fileName;
			//获取文件路径
			var filePath = resultJson[i].data.filePath;
			//获取文件的类型
			var fileType = resultJson[i].data.fileType;
			var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
			spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
			spanHtml += "<font color=\"blue\">" + fileName + "</font></a>"
			spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"SLXXdelUploadFile('" + fileId + "','');\" ><font color=\"red\">删除</font></a></p>"
			$("#SLXX_fileListDiv").append(spanHtml);
		}
	}
	/* function SLXXdelUploadFile(fileId,attacheKey){
		AppUtil.delUploadMater(fileId,attacheKey);
		var fileurl=$("input[name='SLXX_FILE_URL']").val();
		var fileid=$("input[name='SLXX_FILE_ID']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='SLXX_FILE_URL']").val(arrayurls.join(";"));
		$("input[name='SLXX_FILE_ID']").val(arrayIds.join(";"));
	} */
	function SLXXdelUploadFile(fileId, attacheKey) {
		//AppUtil.delUploadMater(fileId,attacheKey);
		art.dialog.confirm("您确定要删除该文件吗?", function() {
			//移除目标span
			$("#" + fileId).remove();
			var fileurl = $("input[name='SLXX_FILE_URL']").val();
			var fileid = $("input[name='SLXX_FILE_ID']").val();
			var arrayIds = fileid.split(";");
			var arrayurls = fileurl.split(";");
			for (var i = 0; i < arrayIds.length; i++) {
				if (arrayIds[i] == fileId) {
					arrayIds.splice(i, 1);
					arrayurls.splice(i, 1);
					break;
				}
			}
			$("input[name='SLXX_FILE_URL']").val(arrayurls.join(";"));
			$("input[name='SLXX_FILE_ID']").val(arrayIds.join(";"));
		});
	}
	// -------------------受理信息上传及高拍仪 结束-------------------------------------------------------------


	//------------------------------------身份身份证读取开始---------------------------------------------------  

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
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_BDCQLC_DYQZXDJ_FORM" method="post">
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
		
		
		<%--注销明细 --%>
		<input type="hidden" name="ZXMX_JSON" />
        <input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}" />
        <input type="hidden" name="BDC_DBJG" value="${busRecord.BDC_DBJG}" />

		<%--开始引入不动产基本信息--%>
		<jsp:include page="./bdcqlc/bdcJbxx.jsp" />
		<%--开始引入不动产基本信息 --%>

		<%--开始引入公用申报对象--%>
		<jsp:include page="./applyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>

		<%--开始受理信息--%>
		<jsp:include page="./bdcqlc/dyqzxdj/T_BDCDYQZXDJ_ACCEPTINFO.jsp" />
		<%--结束受理信息--%>

		<%--开始引入公用上传材料界面 --%>
		<jsp:include page="./applyMaterList.jsp">
			<jsp:param value="2" name="isWebsite" />
		</jsp:include>
		<%--结束引入公用上传材料界面 --%>

		<%--开始注销信息 --%>
		<jsp:include page="./bdcqlc/dyqzxdj/T_BDCDYQZXDJ_ZXXX.jsp" />
		<%--结束注销信息 --%>

		<%--开始注销明细 --%>
		<jsp:include page="./bdcqlc/dyqzxdj/T_BDCDYQZXDJ_ZXMX.jsp" />
		<%--结束注销明细 --%>
		
		<%--开始审批表打印按钮--%>
		<div id="printBtn" name="printBtn" style="display:none;">
			<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
				<tr>
					<th>
						<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="SPBDF"
							>审批表（单方）</a>
						<a href="javascript:void(0);" style="float:right; margin: 2px 0;" class="eflowbutton" id="SPBSF"
							>审批表（双方）</a>
					</th>
				</tr>
		    </table>
	    </div>
	    <%--结束审批表打印按钮--%>

		<!-- 登簿环节数据开始 -->
		<%-- <jsp:include page="./bdcqlc/dyqzxdj/bdcDjshxx.jsp" /> --%>
		<!-- 登簿环节数据结束 -->
		
		<%--开始登记审核意见信息（不动产通用） 只有核准意见--%>
	    <jsp:include page="./bdcqlc/bdcqlcDjshHzOpinion.jsp" /> 
		<%--结束登记审核意见信息（不动产通用）--%>

		<jsp:include page="./bdcqlc/bdcRemark.jsp" />

	</form>
</body>
<OBJECT Name="GT2ICROCX" width="0" height="0" type="hidden"
	CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA" CODEBASE="IdrOcx.cab#version=1,0,1,2">
</OBJECT>
</html>
