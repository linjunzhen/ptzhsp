<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("WinUserForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#WinUserForm").serialize();
				var url = $("#WinUserForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							parent.$("#WinUserGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		},"T_CKBS_WIN_USER");
		
		$("#winDepartNo").combobox('setValues','${winUserInfo.WIN_BUSINESSES }'.split(','));
		
		if('${winUserInfo.IS_YCTB }'==1){
			$("#warnbus").attr("style","");
			$("#group").attr("style","");
		}
		
		$("input[name='IS_YCTB']").click(function(){			
			var Value = $(this).val();
			if(Value=="1"){
				$("#warnbus").attr("style","");
				$("#group").attr("style","");
			}else{
				$("#warnbus").attr("style","display:none;");
				$("#group").attr("style","display:none;");
				$("input[name='WARN_BUSINESS_CODES']").val('');
				$("input[name='WARN_BUSINESS_NAMES']").val('');
				$('#WIN_GROUP').combobox('select', [ '' ]);
			}
		});
	});
    
    function selectUser(){
    	var userIds = $("input[name='OPER_USERIDS']").val();
    	parent.$.dialog.open("sysUserController.do?selector&allowCount=100&userIds="
				+ userIds, {
			title : "人员选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
    			var selectUserInfo = art.dialog.data("selectUserInfo");
    			if(selectUserInfo&&selectUserInfo.userIds){
    				$("input[name='OPER_USERIDS']").val(selectUserInfo.userIds);
    				$("input[name='OPER_USERNAMES']").val(selectUserInfo.userNames);
    				art.dialog.removeData("selectUserInfo");
    			}
    		}
		}, false);
    }
    
    function setScreen(winNo){
    	AppUtil.ajaxProgress({
			url : "callSetController.do?getScreenInfo",
			params : {winNo:winNo},
			callback : function(resultJson) {
				var result = resultJson.msg;
				var arr = result.split(",");
				$("#screenNo").val(arr[0]);
				$("#roomNo").val(arr[1]);
			}
		});
    }
    
    function selectBusinessName(){
    	var businessCodes = $("input[name='WIN_BUSINESS_CODES']").val();
    	parent.$.dialog.open("callSetController.do?selector&allowCount=40&businessCodes="
				+ businessCodes, {
			title : "业务选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
    			var selectBueinessInfo = art.dialog.data("selectBueinessInfo");
    			if(selectBueinessInfo&&selectBueinessInfo.businessCodes){
    				$("input[name='WIN_BUSINESS_CODES']").val(selectBueinessInfo.businessCodes);
    				$("input[name='WIN_BUSINESS_NAMES']").val(selectBueinessInfo.businessNames);
    				art.dialog.removeData("selectBueinessInfo");
    			}
    		}
		}, false);
    }
    
    function selectWarnBusinessName(){
		var businessCodes = $("input[name='WARN_BUSINESS_CODES']").val();
		parent.$.dialog.open("callSetController.do?selector&allowCount=10&businessCodes="
				+ businessCodes, {
			title : "业务选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
				var selectBueinessInfo = art.dialog.data("selectBueinessInfo");
				if(selectBueinessInfo&&selectBueinessInfo.businessCodes){
					$("input[name='WARN_BUSINESS_CODES']").val(selectBueinessInfo.businessCodes);
					$("input[name='WARN_BUSINESS_NAMES']").val(selectBueinessInfo.businessNames);
					art.dialog.removeData("selectBueinessInfo");
				}
			}
		}, false);
	}

	function selectPriorBusinessName(){
		var businessCodes = $("input[name='PRIOR_BUSINESS_CODES']").val();
		parent.$.dialog.open("callSetController.do?selector&allowCount=10&businessCodes="
				+ businessCodes, {
			title : "业务选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
				var selectBueinessInfo = art.dialog.data("selectBueinessInfo");
				if(selectBueinessInfo&&selectBueinessInfo.businessCodes){
					$("input[name='PRIOR_BUSINESS_CODES']").val(selectBueinessInfo.businessCodes);
					$("input[name='PRIOR_BUSINESS_NAMES']").val(selectBueinessInfo.businessNames);
					art.dialog.removeData("selectBueinessInfo");
				} else if (selectBueinessInfo && selectBueinessInfo == 'none') {
					$("input[name='PRIOR_BUSINESS_CODES']").val("");
					$("input[name='PRIOR_BUSINESS_NAMES']").val("");
					art.dialog.removeData("selectBueinessInfo");
				}
			}
		}, false);
	}
    
    function newWinGroup(){
    	$.dialog.open("callSetController.do?newWinGroup",
		{
			title : "窗口分组",
			width : "390px",
			height : "220px",
			lock : true,
			resize : false
		}, false);
    }
    
    function addWinGroup(val, text) {
		$('#WIN_GROUP').combobox('reload');
		$('#WIN_GROUP').combobox('select', [ val ]);
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="WinUserForm" method="post" action="callSetController.do?saveOrUpdateWinUser">
	    <div  id="WinUserFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="RECORD_ID" value="${winUserInfo.RECORD_ID}">
		    <input type="hidden" name="OPER_USERIDS" value="${winUserInfo.OPER_USERIDS}">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
				</tr>
				
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;padding-left:4px;">窗口编号：</span> 
						<c:if test="${winUserInfo.RECORD_ID!=null}">
						${winUserInfo.WIN_NO }
						</c:if>
						<c:if test="${winUserInfo.RECORD_ID==null}">
						<eve:eveselect clazz="tab_text validate[required],ajax[ajaxVerifyValueExist]"
							dataInterface="callService.findWinsForSelect" dataParams="" id="WIN_NO"
							defaultEmptyText="选择窗口" name="WIN_NO" value="${winUserInfo.WIN_NO }"
							onchange="setScreen(this.value);">
						</eve:eveselect>
						</c:if>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 120px;float:left;text-align:right;">所属大厅：</span>
						<input type="text" style="width:180px;float:left;"
						value="${winUserInfo.BELONG_ROOM}" maxlength="4" id="roomNo"
						class="eve-input " readonly="readonly"
						name="BELONG_ROOM" />
					</td>
					<td><span style="width:120px;float:left;text-align:right;">屏编号：</span>
						<input type="text" style="width:180px;float:left;"
						value="${winUserInfo.SCREEN_NO}" maxlength="4" id="screenNo"
						class="eve-input " readonly="readonly"
						name="SCREEN_NO" />
					</td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">窗口所属业务类：</span>
						<input type="hidden" name="WIN_BUSINESS_CODES" value="${winUserInfo.WIN_BUSINESS_CODES }">
						<input type="text"
						style="width:570px;float:left;"
						value="${winUserInfo.WIN_BUSINESS_NAMES}" class="eve-input validate[required]"
						name="WIN_BUSINESS_NAMES" readonly="readonly"
						onclick="selectBusinessName();" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 120px;float:left;text-align:right;">用户名称：</span> <input
						type="text" style="width:570px;float:left;" readonly="readonly"
						value="${winUserInfo.OPER_USERNAMES}" class="eve-input validate[required]" name="OPER_USERNAMES" onclick="selectUser();"/> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>					
					<td colspan="2"><span
						style="width: 120px;float:left;text-align:right;">一窗通办窗口：</span>
						<eve:radiogroup typecode="YesOrNo" width="130px"
							defaultvalue="0" value="${winUserInfo.IS_YCTB}"
							fieldname="IS_YCTB">
						</eve:radiogroup>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr id="warnbus" style="display: none;">
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">预警情况业务类：</span>
						<input type="hidden" name="WARN_BUSINESS_CODES" value="${winUserInfo.WARN_BUSINESS_CODES }">
						<input type="text"
						style="width:570px;float:left;"
						value="${winUserInfo.WARN_BUSINESS_NAMES}" class="eve-input validate[required]"
						name="WARN_BUSINESS_NAMES" readonly="readonly"
						onclick="selectWarnBusinessName();" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr id="group" style="display: none;">
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">窗口分组：</span>
						<input class="easyui-combobox" style="width:180px;height:30px;" id="WIN_GROUP" name="WIN_GROUP" value="${winUserInfo.WIN_GROUP }"
							data-options="url:'callSetController.do?findGroupForWin&amp;defaultEmpty=false',editable:false,
								method: 'post',valueField:'VALUE',textField:'TEXT',panelWidth: 180,panelHeight: '100',multiple:false" />
								<span style="width:25px;display:inline-block;text-align:right;">
									<img src="plug-in/easyui-1.4/themes/icons/disable-btn.png"
									onclick="newWinGroup();"
									style="cursor:pointer;vertical-align:middle;" title="新建分组">
								</span>
					</td>
				</tr>
				<tr id="priorbus">
					<td colspan="2"><span style="width: 120px;float:left;text-align:right;">优先业务：</span>
						<input type="hidden" name="PRIOR_BUSINESS_CODES" value="${winUserInfo.PRIOR_BUSINESS_CODES }">
						<input type="text"
							   style="width:480px;float:left;"
							   value="${winUserInfo.PRIOR_BUSINESS_NAMES}" class="eve-input"
							   name="PRIOR_BUSINESS_NAMES" readonly="readonly"
							   onclick="selectPriorBusinessName();" />
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
