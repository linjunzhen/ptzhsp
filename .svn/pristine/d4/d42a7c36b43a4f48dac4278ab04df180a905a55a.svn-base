<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,artdialog,validationegine,ztree,autocomplete"></eve:resources>

<script type="text/javascript">
	function showSelectDEPART_IDTree() {
		var treeObj = $("input[name='DEPART_NAME']");
		var treeOffset = $("input[name='DEPART_NAME']").offset();
		$("#DEPART_IDTreeContent").css({
			left : (treeOffset.left) + "px",
			top : (treeOffset.top + treeObj.outerHeight()) + "px"
		}).slideDown("fast");
		$("body").bind("mousedown", onDEPART_IDTreeBodyDown);
	}
	
	function onSelectDEPART_IDTreeClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("DEPART_IDTree");
		var selectNode = zTree.getSelectedNodes()[0];
		$("input[name='DEPART_PATH']").attr("value", selectNode.PATH);
		$("input[name='DEPART_NAME']").attr("value", selectNode.name);
		loadDatas({
			"Q_D.PATH_LIKE":selectNode.PATH
		});
		hideDEPART_IDSelectTree();
	}
	
	function onDEPART_IDTreeBodyDown(event) {
		if (!(event.target.id == "DEPART_ID"
				|| event.target.id == "DEPART_IDTreeContent" || $(event.target)
				.parents("#DEPART_IDTreeContent").length > 0)) {
			hideDEPART_IDSelectTree();
		}
	}
	
	function hideDEPART_IDSelectTree() {
		$("#DEPART_IDTreeContent").fadeOut("fast");
		$("body").unbind("mousedown", onDEPART_IDTreeBodyDown);
	}
	
	function onSelectRole(rec){
		loadDatas({
			"roleId":rec.ROLE_ID
		});
	}
	
	var DEPART_IDSetting = {
		view : {
			dblClickExpand : false,
			selectedMulti : false
		},
		async : {
			enable : true,
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
				"idAndNameCol" : "DEPART_ID,DEPART_NAME",
				"targetCols" : "PARENT_ID,PATH",
				"rootParentId" : "0",
				"Q_STATUS_!=":0,
				"dicTypeCode" : "",
				"isShowRoot" : "true",
				"rootName" : "全部组织机构"
			}
		},
		callback : {
			onClick : onSelectDEPART_IDTreeClick
		}
	};
	
	
	function loadAutoCompleteDatas() {
		$.post("sysUserController.do?load", {

		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			$("#AutoCompleteInput").autocomplete(
					resultJson,
					{
						matchContains : true,
						formatItem : function(row, i, max) {
							//下拉框显示
							return "<div>" + row.FULLNAME+"&nbsp;"+row.DEPART_NAME+"</div>";
						},
						formatMatch : function(row) {
							//查询条件
							return row.FULLNAME+row.PINYIN;
						},
						formatResult : function(row, i, max) {
							//显示在文本框中
							return row.FULLNAME;
						}
					}).result(
					function(event, data, formatted) {

						if ($("#selectedRecords #" + data.USER_ID).text() == ""){
							var allowCount = $("input[name='allowCount']").val();
							var selectedLength= $("#selectedRecords")[0].length+1;
							if((selectedLength>allowCount)&&allowCount!=0){
								alert("最多只能选择"+allowCount+"条记录!");
								return;
							}else{
								$("#selectedRecords").append(
										"<option id='"+data.USER_ID+"' value='"+data.USER_ID+"'>"
												+ data.FULLNAME + "</option>");
								var height = 350 - 83;
								var selectContent = "<select multiple=\"multiple\" id=\"selectRecords\" style=\"width: 100%;height:"
									+ height + "px\"></select>";
								var currentSelectIndex = $("input[name='CurrentSelectIndex']").val();
								if(currentSelectIndex=="0"){
									$("#selectUserByDepDiv").html("");
									var path = $("input[name='DEPART_PATH']").attr("value");
									$("#selectUserByDepDiv").html(selectContent);
									loadDatas({
										"Q_D.PATH_LIKE" : path
									});
								}else{
									$("#selectUserByRoleDiv").html("");
									var roleId = $("input[name='SelectRole']").attr("value");
									$("#selectUserByRoleDiv").html(selectContent);
									loadDatas({
										"roleId" : roleId
									});
								}
								$("#selectRecords").dblclick(function(){ //绑定双击事件
							    	var selectedLength= $("#selectedRecords")[0].length+1;
									if((selectedLength>allowCount)&&allowCount!=0){
										alert("最多只能选择"+allowCount+"条记录!");
										return;
									}else{
										$("option:selected",this).appendTo("#selectedRecords");//追加给对方
									}
							    });
								//双击选项
							    $("#selectedRecords").dblclick(function(){
							        $("option:selected",this).appendTo("#selectRecords");
							    });
							}
						}
					}

			);
		});
	}
	

	function loadDatas(params) {
		$("#selectRecords").html("");
		$.post("sysUserController.do?load", params, function(responseText,
				status, xhr) {
			var resultJson = $.parseJSON(responseText);
			for ( var index in resultJson) {
				if(index!="indexOf"){
					var user = resultJson[index];
					if ($("#" + user.USER_ID).text() == "") {
						$("#selectRecords").append(
								"<option id='"+user.USER_ID+"' value='"+user.USER_ID+"' >"
										+ user.FULLNAME + "</option>");
					}
				}
				
			}
		});
	}
	
	function choiceSelectRecords(){
		var selectedRecords= $("#selectedRecords")[0];
		if(selectedRecords.length>=1){
			var userIds = "";
			var userNames = "";
			for(var i = 0;i<selectedRecords.length;i++){
				if(i>0){
					userIds+=",";
					userNames+=",";
				}
				userIds+=selectedRecords[i].value;
				userNames+=selectedRecords[i].text;
			}
			art.dialog.data("selectUserInfo", {
				userIds:userIds,
				userNames:userNames
			});// 存储
			AppUtil.closeLayer();
		}else{
			alert("请至少选择一条记录!")
		}
	}
	
	function loadSelectedRecords(){
		var userIds = $("input[name='userIds']").val().split(",");
		var userNames = $("input[name='userNames']").val().split(",");
		for(var i =0;i<userIds.length;i++){
			$("#selectedRecords").append(
					"<option id='"+userIds[i]+"' value='"+userIds[i]+"' >"
							+ userNames[i] + "</option>");
		}
	}

	$(function() {
		var height = 350 - 83;
		var selectContent = "<select multiple=\"multiple\" id=\"selectRecords\" style=\"width: 100%;height:"
				+ height + "px\"></select>";
		$("#selectUserByDepDiv").append(selectContent);
		var allowCount = $("input[name='allowCount']").val();
		$("#selectAccordion").accordion({
			//被选中时加载数据
			onSelect : function(title, index) {
				$("#selectUserByDepDiv").html("");
				$("#selectUserByRoleDiv").html("");
				$("input[name='CurrentSelectIndex']").val(index);
				if (index == 0) {
					var path = $("input[name='DEPART_PATH']").attr("value");
					$("#selectUserByDepDiv").html(selectContent);
					loadDatas({
						"Q_D.PATH_LIKE" : path
					});
				} else if (index == 1) {
					var roleId = $("input[name='SelectRole']").attr("value");
					$("#selectUserByRoleDiv").html(selectContent);
					loadDatas({
						"roleId" : roleId
					});
				}
				$("#selectRecords").dblclick(function(){ //绑定双击事件
			    	var selectedLength= $("#selectedRecords")[0].length+1;
					if((selectedLength>allowCount)&&allowCount!=0){
						alert("最多只能选择"+allowCount+"条记录!");
						return;
					}else{
						$("option:selected",this).appendTo("#selectedRecords");//追加给对方
					}
			    });
			    $("#selectedRecords").dblclick(function(){
			        $("option:selected",this).appendTo("#selectRecords");
			    });
			}
		});
		$.fn.zTree.init($("#DEPART_IDTree"), DEPART_IDSetting);
		var userIds = $("input[name='userIds']").val();
		if(userIds){
			loadSelectedRecords();
		}
		loadDatas();
		//加载自动补全数据
		loadAutoCompleteDatas();
		//移到右边
		$("#addSysUser").click(
			function() {
				var selectedLength= $("#selectedRecords")[0].length+$("#selectRecords option:selected").length;
				if((selectedLength>allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}else{
					$("#selectRecords option:selected").appendTo(
					"#selectedRecords");
				}
				/* if ($("#selectRecords option:selected").length < 2) {
					var selectedLength= $("#selectedRecords")[0].length+$("#selectRecords option:selected").length;
					if((selectedLength>allowCount)&&allowCount!=0){
						alert("最多只能选择"+allowCount+"条记录!");
						return;
					}else{
						$("#selectRecords option:selected").appendTo(
						"#selectedRecords");
					}
				} else {
					alert("每次只能选择一条记录!");
				} */
		});
		$("#addAllSysUser").click(function() {
			var selectedLength= $("#selectedRecords")[0].length+$("#selectRecords")[0].length;
			if((selectedLength>allowCount)&&allowCount!=0){
				alert("最多只能选择"+allowCount+"条记录!");
				return;
			}else{
				$("#selectRecords option").appendTo("#selectedRecords");
			}
		});

		$("#removeSysUser").click(function() {
			$("#selectedRecords option:selected").appendTo("#selectRecords");
		});

		$("#removeAllSysUser").click(function() {
			$("#selectedRecords option").appendTo("#selectRecords");
		});
		
	    $("#selectRecords").dblclick(function(){ //绑定双击事件
	    	var selectedLength= $("#selectedRecords")[0].length+1;
			if((selectedLength>allowCount)&&allowCount!=0){
				alert("最多只能选择"+allowCount+"条记录!");
				return;
			}else{
				$("option:selected",this).appendTo("#selectedRecords");//追加给对方
			}
	    });

	    //双击选项
	    $("#selectedRecords").dblclick(function(){
	        $("option:selected",this).appendTo("#selectRecords");
	    });
		
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ExcelConfigForm" method="post"
		action="excelConfigController.do?saveOrUpdate">
		<input type="hidden" name="CurrentSelectIndex" value="0"> 
		<input type="hidden" name="userIds" value="${userIds}"> 
		<input type="hidden" name="userNames" value="${userNames}"> 
		<input type="hidden" name="allowCount" value="${allowCount}"> 
		<input type="hidden" name="callbackFn" value="${callbackFn}">
		<input type="hidden" name="isForWin" value="${isForWin}">
		<div id="ExcelConfigFormDiv">
        <div  class="newContTab">
        	<div class="eve-search">
                <input  class="eve-autoinputbg" id="AutoCompleteInput"
                  onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" 
                 type="text" maxlength="10" style="width: 218px;">
                <input type="button" style="cursor: default;" name="" class="eve-autoqueryicon">
            </div>
            <br class="clearFloat"/>
            <div class="eve-select">
            	<!-- 左边供选择块 -->
            	<div class="eve-selectleft">
                    <div id="selectAccordion" class="easyui-accordion"
                        style="width:100%;height:100%;">
                        <div title="按部门选择人员">
                            <div align="left">
                            <input type="text" style="width:98%;" readonly="readonly"
                            onclick="showSelectDEPART_IDTree();"
                             class="eve-input" name="DEPART_NAME" id="DDEPART_NAME" />
                            </div>
                            <div id="selectUserByDepDiv"></div>
                        </div>
                        <div title="按角色选择人员">
                            <input style="width:100%;height:25px;float:left;"
                               class="easyui-combobox" name="SelectRole"
	                          data-options="url:'sysRoleController.do?load&defaultEmpty=true',
	                          editable:false,method: 'post',valueField:'ROLE_ID',textField:'ROLE_NAME',
	                          onSelect:onSelectRole,
	                          panelHeight: '200'" 
                           /> 
                            <div id="selectUserByRoleDiv"></div>
                        </div>
                    </div>
                </div>
                <!-- 左边供选择块结束 -->
                <!-- 中间部分按钮块 -->
                <div style="float:left; width: 50px;margin: 17% 0 0 10px;">
                    <span id="addSysUser"> 
                    <input type="button" class="selectorBtn" value=">" >
                    </span><br/>
                    <c:if test="${allowCount==0}">
                     <span id="addAllSysUser">
                    <input type="button" class="selectorBtn" value=">>" >
                    </span> <br />
                    </c:if>
                    <span id="removeSysUser"> 
                    <input type="button" class="selectorBtn" value="<" >
                    </span><br />
                    <c:if test="${allowCount==0}">
                    <span id="removeAllSysUser">
                     <input type="button" class="selectorBtn" value="<<" >
                    </span><br/> 
                    </c:if>
                </div>
				<!-- 中间部分按钮块 -->
                <!-- 右侧已经选择块 -->

                <div class="eve-selectright">
                    <select multiple="multiple" id="selectedRecords"
                        style="width: 100%;height: 100%">
                    </select>
                </div>
				<!-- 右侧已经选择块 -->
            </div>
        </div>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="button" onclick="choiceSelectRecords();" class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				<input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
	
	<div class="treeContent eve-combotree"
		style="display:none; position: absolute;" id="DEPART_IDTreeContent">
		<ul class="ztree" style="margin-top:0; width:228px;height: 200px"
			id="DEPART_IDTree"></ul>
	</div>
</body>

