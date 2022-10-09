<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	SysUser sysUser = AppUtil.getLoginUser();
	//获取登录用户的角色CODE
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否商事登记受理员
    boolean isSsdjsly = roleCodes.contains("ssdjsly");
    boolean isBdcdjsly = roleCodes.contains("BDCDJZX_SLY");
    boolean isXmtzcsly = roleCodes.contains("QXZSPJ_TZXMSLY");
    boolean isCyjbcsly = roleCodes.contains("CYCJ_SLY");
	//判断是否商事登记受理员
	if("__ALL".equals(resKey)||isSsdjsly){
	    request.setAttribute("isSsdjsly",true);
	}else{
	    request.setAttribute("isSsdjsly",false);
	}
	if("__ALL".equals(resKey)||isBdcdjsly){
	    request.setAttribute("isBdcdjsly",true);
	}else{
	    request.setAttribute("isBdcdjsly",false);
	}
	if("__ALL".equals(resKey)||isXmtzcsly){
	    request.setAttribute("isXmtzcsly",true);
	}else{
	    request.setAttribute("isXmtzcsly",false);
	}
	if("__ALL".equals(resKey)||isCyjbcsly){
	    request.setAttribute("isCyjbcsly",true);
	}else{
	    request.setAttribute("isCyjbcsly",false);
	}
%>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript">
	function rowformater(value,row,index){
		var isSsdjsly = '${isSsdjsly}';
		var isBdcdjsly = '${isBdcdjsly}';
		//alert(resKey);
		var html = "";
		if(isSsdjsly=='true'){			
			 html += "<a href='#' onclick='setCompany("+index+")'>企业设立登记</a>&nbsp;&nbsp;|&nbsp;&nbsp;";
		}
<%--		if(isBdcdjsly=='true'){			--%>
<%--			 html += "<a href='#' onclick='bdcAccept("+index+")'>不动产登记</a>&nbsp;&nbsp;|&nbsp;&nbsp;";--%>
<%--		} --%>
		html = html+"<a href='#' onclick='accept("+index+")'>受理</a>&nbsp;&nbsp;|&nbsp;&nbsp;"+
// 		"<a href='#' onclick='pass("+index+")'>过号</a>&nbsp;&nbsp;|&nbsp;&nbsp;"+
<%--		"<a href='#' onclick='deal("+index+")'>处理</a>&nbsp;&nbsp;|&nbsp;&nbsp;"+--%>
		"<a href='#' onclick='dealzx("+index+")'>咨询</a>&nbsp;&nbsp;|&nbsp;&nbsp;"+
		"<a href='#' onclick='deallz("+index+")'>领照</a>&nbsp;&nbsp;|&nbsp;&nbsp;"+
		"<a href='#' onclick='forward("+index+")'>转发</a>";
		return html;
	}
	
	function setCompany(index) {
		//$('#QueuingUndoGrid').datagrid('selectRow',index);  
		var row = $('#callingGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("callController.do?setCompany&recordId="
					+ row.RECORD_ID, {
				title : "企业设立登记",
				width : "450px",
				height : "260px",
				lock : true,
				resize : false
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}

	var secs = 20;//chenbin  叫号按钮时间控制
	var wait = secs * 1000;
	//叫号
	function callCurrent(){		
		$("#callingGrid").datagrid("reload");
		$("#QueuingUndoGrid").datagrid("reload");
		//显示第一页数据  
		$("#QueuingUndoGrid").datagrid("options").pageNumber = 1;  
		//分页栏上跳转到第一页  
		$("#QueuingUndoGrid").datagrid('getPager').pagination({pageNumber: 1});  
		$("#QueuingUndoGrid").datagrid("reload");
		setTimeout("calling()",2000);
		setBtnDisable();
	    
	}
	
	function calling(){
		var row = $('#callingGrid').datagrid('getData').rows[0];
		if(!row){
			row = $('#QueuingUndoGrid').datagrid('getData').rows[0];
			if(row.APPOINTCALL==0){
				parent.art.dialog({
					content : "当前等待记录未到可叫号时段",
					icon : "warning",
					time : 3,
					ok : true
				});
				return null;
			}
		}
 		var winNo = '${winNo}';
 		if (row) {
			AppUtil.ajaxProgress({
			//	url : "http://localhost:8080/call/callController.do?callNum",
				url : "callController.do?callQueuing",
				params : {
					recordId : row.RECORD_ID,
					lineNo : row.LINE_NO,
					winNo : winNo,
					type : "callNum"
				},
				callback : function(resultJson) {
					$("#callingGrid").datagrid("reload");
					$("#QueuingUndoGrid").datagrid("reload");
					//$("#currentCallInfo").html('（当前叫号：'+row.LINE_NO+'）');
					parent.art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						time : 3,
						ok : true
					});
				}
			}); 
	 	}else{
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}
	
	function setBtnDisable() {
		var disabSeconds = 20;//设置变灰延时时间，单位：秒
		document.getElementById("callNum").value = "    叫号(" + disabSeconds + ")";
		document.getElementById("callNum").disabled = true;
		for (i = 1; i <= disabSeconds; i++) {
			window.setTimeout("update(" + i + ")", i * 1000);
		}
		window.setTimeout("timer()", disabSeconds * 1000);
	}
	function update(num, value) {
		if (num == (wait / 1000)) {
			document.getElementById("callNum").value = "    叫 号";
		} else {
			printnr = (wait / 1000) - num;
			document.getElementById("callNum").value = "    叫号(" + printnr + ")";
		}
	}

	function timer() {
		document.getElementById("callNum").disabled = false;
		document.getElementById("callNum").value = "    叫 号";
	}
	//过号
	function passCurrent() {
		pass(0);
	}
	//请稍等
	function waiting() {
		//return;
 		var winNo = '${winNo}';
		AppUtil.ajaxProgress({
			url : "callController.do?callQueuing",
			params : {
				winNo : winNo,
				type : "CallingWait"
			},
			callback : function(resultJson) {
				parent.art.dialog({
					content : resultJson.msg,
					icon : "succeed",
					time : 3,
					ok : true
				});
			}
		});
		setWaitingBtnDisable();
	}

	function setWaitingBtnDisable() {
		var disabSeconds = 50;//设置变灰延时时间，单位：秒
		document.getElementById("callNumWaiting").value = "    请稍候(" + disabSeconds + ")";
		document.getElementById("callNumWaiting").disabled = true;
		for (i = 1; i <= disabSeconds; i++) {
			window.setTimeout("updateWaiting(" + i + ")", i * 1000);
		}
		window.setTimeout("timerWaiting()", disabSeconds * 1000);
	}
	var secs2 = 50;//chenbin  叫号按钮时间控制
	var wait2 = secs2 * 1000;
	function updateWaiting(num, value) {
		if (num == (wait2 / 1000)) {
			document.getElementById("callNumWaiting").value = "    请稍候";
		} else {
			printnr = (wait2 / 1000) - num;
			document.getElementById("callNumWaiting").value = "    请稍候(" + printnr + ")";
		}
	}

	function timerWaiting() {
		document.getElementById("callNumWaiting").disabled = false;
		document.getElementById("callNumWaiting").value = "    请稍候";
	}
	//服务
	function servicing() {
		//return;
 		var winNo = '${winNo}';
		AppUtil.ajaxProgress({
			url : "callController.do?callQueuing",
			params : {
				winNo : winNo,
				type : "CallingService"
			},
			callback : function(resultJson) {
				parent.art.dialog({
					content : resultJson.msg,
					icon : "succeed",
					time : 3,
					ok : true
				});
			}
		});
		setServicingBtnDisable();
	}
	function setServicingBtnDisable() {
		var disabSeconds = 50;//设置变灰延时时间，单位：秒
		document.getElementById("callNumServicing").value = "    服 务(" + disabSeconds + ")";
		document.getElementById("callNumServicing").disabled = true;
		for (i = 1; i <= disabSeconds; i++) {
			window.setTimeout("updateServicing(" + i + ")", i * 1000);
		}
		window.setTimeout("timerServicing()", disabSeconds * 1000);
	}
// 	var secs2 = 60;//chenbin  叫号按钮时间控制
// 	var wait2 = secs2 * 1000;
	function updateServicing(num, value) {
		if (num == (wait2 / 1000)) {
			document.getElementById("callNumServicing").value = "    服 务";
		} else {
			printnr = (wait2 / 1000) - num;
			document.getElementById("callNumServicing").value = "    服 务(" + printnr + ")";
		}
	}

	function timerServicing() {
		document.getElementById("callNumServicing").disabled = false;
		document.getElementById("callNumServicing").value = "    服 务";
	}
	//其他处理
	function dealCurrent() {
		deal(0);
	}
	//受理
	function accept(index) {
		var row = $('#callingGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("serviceItemController/selector.do", {
				title : "事项选择器",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						var defKey = selectItemInfo.defKeys;
						var itemCode = selectItemInfo.itemCodes;													
						art.dialog.removeData("selectItemInfo");	
						toUrl("executionController.do?goStart&defKey="
								+ defKey + "&itemCode=" + itemCode
								+ "&takenoway=" + row.TAKENOWAY
								+ "&lineCard="
								+ row.LINE_CARDNO + "&lineId=" + row.RECORD_ID
								+ "&zjlx="+row.LINE_ZJLX,row.LINE_NAME);
					}
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}
	function toUrl(url,lineName){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var lineNameInput="<input name='lineName' type='hidden' value='"+lineName+"' />";
		$("#callingToolbar").append(ssoForm);
		ssoForm.append(lineNameInput);
		ssoForm.submit();		
	}
	function pass(index) {
		var row = $('#callingGrid').datagrid('getData').rows[index];
		if (row) {
			
			if(window.confirm("您确定要过号吗？")){
				AppUtil.ajaxProgress({
					url : "callController.do?queuingPass",
					params : {
						recordId : row.RECORD_ID
					},
					callback : function(resultJson) {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							time : 3,
							ok : true
						});
						$("#callingGrid").datagrid("reload");
						parent.reloadTabGrid("已受理记录","pass");
					}
				});
			}
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}

	function deal(index) {
		//$('#QueuingUndoGrid').datagrid('selectRow',index);  
		var row = $('#callingGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("callController.do?goQueuingDeal&recordId="
					+ row.RECORD_ID, {
				title : "办件处理",
				width : "450px",
				height : "260px",
				lock : true,
				resize : false
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}

	function dealzx(index) {
		//$('#QueuingUndoGrid').datagrid('selectRow',index);  
		var row = $('#callingGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("callController.do?goQueuingDealzx&recordId="
					+ row.RECORD_ID, {
				title : "办件处理",
				width : "450px",
				height : "260px",
				lock : true,
				resize : false
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}

	function deallz(index) {
		//$('#QueuingUndoGrid').datagrid('selectRow',index);  
		var row = $('#callingGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("callController.do?goQueuingDeallz&recordId="
					+ row.RECORD_ID, {
				title : "办件处理",
				width : "450px",
				height : "260px",
				lock : true,
				resize : false
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}
	
	function bdcAccept(index) {
		var row = $('#callingGrid').datagrid('getData').rows[index];
		if (row) {
			if(window.confirm("此操作将直接完成叫号受理，请确定是否已在业务系统完成业务受理？")){
				AppUtil.ajaxProgress({
					url : "callController.do?bdcAccept",
					params : {
						recordId : row.RECORD_ID
					},
					callback : function(resultJson) {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							time : 3,
							ok : true
						});
						$("#callingGrid").datagrid("reload");
						parent.reloadTabGrid("已受理记录","deal");
					}
				});
			}
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}

	function afterDeal() {
		$("#callingGrid").datagrid("reload");
		parent.reloadTabGrid("已受理记录","deal");
	}

	function forward(index) {
		//$('#QueuingUndoGrid').datagrid('selectRow',index);  
		var row = $('#callingGrid').datagrid('getData').rows[index];
		var winNo = '${winNo }';
		var url = "";
		if(winNo==""){
			url = "callController.do?goQueuingForward&recordId="+row.RECORD_ID;
		}else{
			url = "callController.do?goQueuingForward&recordId="+row.RECORD_ID+"&winNo="+winNo;
		}
		if (row) {
			$.dialog.open(url, {
				title : "办件转发",
				width : "450px",
				height : "200px",
				lock : true,
				resize : false
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "succeed",
				time : 3,
				ok : true
			});
		}
	}

	function bindScanCtrl(){
		$.dialog.open("webpage/call/queuing/VideoInputTakeNoCtl.jsp?", {
			title : "窗口取号",
			width:"810px",
			lock: true,
			resize:false,
			height:"100%",
			close: function () {
			}
		}, false);
	}
	
	function bindScanCtrlContinue(takeNoWay){
		var winNo = '${winNo}';
		$.dialog.open("webpage/call/queuing/VideoInputTakeNoContinueCtl.jsp?winNo="+winNo+"&takeNoWay="+takeNoWay, {
			title : "窗口取号",
			width:"810px",
			lock: true,
			resize:false,
			height:"100%",
			close: function () {
			}
		}, false);
	}
	function creditInquiry2(){
		var isXmtzcsly = '${isXmtzcsly}';
		var isCyjbcsly = '${isCyjbcsly}';
		var account = '';
		var password = '';
		if(isCyjbcsly=='true'){
			account = 'cycjc';
			password = '123456';
		}
		if(isXmtzcsly=='true'){
			account = 'msreq';
			password = 'msreq';
		}
		$.dialog.open("http://172.16.51.98:8080/auth/loging?account="+account+"&password="+password, {
			title : "信用报告查询 详询:15980100998",
			width:"910px",
			lock: true,
			resize:false,
			height:"90%",
			close: function () {
			}
		}, false);
	}

	function creditInquiry(){
		$.dialog.open("callController.do?showCreditInquiry", {
			title : "信用报告查询 详询:15980100998",
			width : "450px",
			height : "200px",
			lock: true,
			resize:false,
		}, false);
	}
	function changeWin(){
		var winNo = '${winNo }';
		$.dialog.open("callController.do?goChangeWin&winNo="+winNo, {
			title : "窗口切换",
			width : "450px",
			height : "200px",
			lock : true,
			resize : false
		}, false);
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("weqweqwe");
		AppUtil.initAuthorityRes("weqweqweq");
	});
	function refresh(){
		var currTab =  self.parent.$('#tabRegion').tabs('getSelected'); //获得当前tab
	    var url = $(currTab.panel('options').content).attr('src');
	    self.parent.$('#tabRegion').tabs('update', {
	      tab : currTab,
	      options : {
	       content : '<iframe. scrolling="auto" frameborder="0"  src="callController.do?goQueuingUndo" style="width:100%;height:100%;"></iframe>'
	      }
	     });
	}
	
	setInterval(function(){
		//显示第一页数据  
		$("#QueuingUndoGrid").datagrid("options").pageNumber = 1;  
		//分页栏上跳转到第一页  
		$("#QueuingUndoGrid").datagrid('getPager').pagination({pageNumber: 1}); $("#QueuingUndoGrid").datagrid('reload');},60000);
	/* $(document).ready(function() {
		AppUtil.initAuthorityRes("QueuingUndoToolbar");

	}); */
</script>
	<div class="call_buttons" id = "weqweqwe">
		<span style="font-size: 21px;">当前为</span><span style="font-size: 24px;color: blue;"> ${winNo }</span>	<span style="font-size: 21px;">号窗口&nbsp;&nbsp;&nbsp;&nbsp;</span>
		
		<input type="button" value="切换窗口" class="input_out1" onmousemove="this.className='input_in1'" onmouseout="this.className='input_out1'"  onclick="changeWin()" >&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<input type="button" id="callNum" value="    叫 号" class="input_out2" onmousemove="this.className='input_in2'" onmouseout="this.className='input_out2'" onclick="callCurrent()" >&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="    过 号" class="input_out3" onmousemove="this.className='input_in3'" onmouseout="this.className='input_out3'" onclick="passCurrent()" >&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" id="callNumWaiting" value="    请稍候" class="input_out4" onmousemove="this.className='input_in4'" onmouseout="this.className='input_out4'" onclick="waiting()" >&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" id="callNumServicing" value="    服 务" class="input_out5" onmousemove="this.className='input_in5'" onmouseout="this.className='input_out5'" onclick="servicing()" >&nbsp;&nbsp;&nbsp;&nbsp;
<%--		<input type="button" value="    处 理" class="input_out6" onmousemove="this.className='input_in6'" onmouseout="this.className='input_out6'" onclick="dealCurrent()">&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--		<input type="button"  value="    取 号" class="input_out6"   onmousemove="this.className='input_in6'" onmouseout="this.className='input_out6'" onclick="bindScanCtrl()">--%>
<%--		<a href="#" class="input_out6"  iconcls="icon-note-edit" plain="true" onclick="bindScanCtrl();">取    号</a>--%>
<%--		<a href="#" class="input_out6"  iconcls="icon-note-edit" plain="true" onclick="bindScanCtrlContinue();">继续取号</a>--%>
	</div>
<div class="easyui-layout" fit="true">
	<div region="center"  id = "weqweqweq">
		<!-- =========================查询面板开始========================= -->
		<div id="callingToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;叫号中事项
									<a href="#" class="easyui-linkbutton" reskey="Video_takeNo_Continue" iconcls="icon-note-edit" plain="true" onclick="bindScanCtrlContinue(3);">继续叫号</a>
									<c:if test="${winNo=='19'||winNo=='20'||winNo=='64'||winNo=='69'||winNo=='70'||winNo=='71'}">
										<a href="#" class="easyui-linkbutton" reskey="Video_takeNo_Batch" iconcls="icon-note-edit" plain="true" onclick="bindScanCtrlContinue(4);">批量业务</a>
									</c:if>
									<a href="#" class="easyui-linkbutton" reskey="CREDITINQUIRY" iconcls="icon-note-edit" plain="true" onclick="creditInquiry();">信用报告查询</a>
							</div>
						</div>
					</div>
				</div>
			</div>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"  
			id="callingGrid" nowrap="false" style="height: 40%;"
			toolbar="#callingToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="false" border="false"
			url="callController.do?callingGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'TAKENOWAY',hidden:true" width="150">取号方式</th>
					<th data-options="field:'LINE_NO',align:'left'" width="150">排队号</th>
					<th data-options="field:'LINE_NAME',align:'left'" width="100">经办人姓名</th>
					<th data-options="field:'LINE_ZJLX',hidden:true" width="100" >证件类型</th>
					<th data-options="field:'ZJLX',align:'left'" width="100" >证件类型</th>
					<th data-options="field:'LINE_CARDNO',align:'left'" width="200" >证件号码</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="200" >取号时间</th>
					<th data-options="field:'id',formatter:rowformater" width="300">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		<!-- =========================查询面板开始========================= -->
		<div id="QueuingUndoToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;待处理事项
									<a href="#" class="easyui-linkbutton" reskey="Video_takeNo" iconcls="icon-note-edit" plain="true" onclick="bindScanCtrl();">取    号</a>
									<span id="currentCallInfo" style="color: red;"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="QueuingUndoForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:80px;text-align:right;">身份证号码：</td>
							<td style="width:128px;">
							<input class="eve-input" type="text" maxlength="20" style="width:115px;" name="Q_t.LINE_CARDNO_LIKE" />
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('QueuingUndoToolbar','QueuingUndoGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('QueuingUndoForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="QueuingUndoGrid"  nowrap="false" style="height: 53%;"
			toolbar="#QueuingUndoToolbar" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="false" border="false"
			url="callController.do?queuingUndoGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'RECORD_ID',hidden:true" width="80">RECORD_ID</th>
					<th data-options="field:'APPOINTCALL',hidden:true" width="80">APPOINTCALL</th>
					<th data-options="field:'LINE_NO',align:'left'" width="150">排队号</th>
					<th data-options="field:'LINE_NAME',align:'left'" width="100">经办人姓名</th>
					<th data-options="field:'LINE_ZJLX',hidden:true" width="100" >证件类型</th>
					<th data-options="field:'ZJLX',align:'left'" width="100" >证件类型</th>
					<th data-options="field:'LINE_CARDNO',align:'left'" width="200" >证件号码</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="200" >取号时间</th>
					<!-- <th data-options="field:'id',formatter:rowformater" width="180">操作</th> -->
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>