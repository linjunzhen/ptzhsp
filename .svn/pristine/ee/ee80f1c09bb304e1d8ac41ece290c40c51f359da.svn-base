<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,autocomplete"></eve:resources>

<script type="text/javascript">
	
	
	function loadAutoCompleteDatas() {
		$.post("sysRoleController.do?load", {

		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			$("#AutoCompleteInput").autocomplete(
					resultJson,
					{
						matchContains : true,
						formatItem : function(row, i, max) {
							//下拉框显示
							return "<div>" + row.ROLE_NAME+"</div>";
						},
						formatMatch : function(row) {
							//查询条件
							return row.ROLE_NAME+row.PINYIN;
						},
						formatResult : function(row, i, max) {
							//显示在文本框中
							return row.ROLE_NAME;
						}
					}).result(
					function(event, data, formatted) {

						if ($("#selectedRecords #" + data.ROLE_ID).text() == ""){
							var allowCount = $("input[name='allowCount']").val();
							var selectedLength= $("#selectedRecords")[0].length+1;
							if((selectedLength>allowCount)&&allowCount!=0){
								alert("最多只能选择"+allowCount+"条记录!");
								return;
							}else{
								$("#selectedRecords").append(
										"<option id='"+data.ROLE_ID+"' value='"+data.ROLE_ID+"'>"
												+ data.ROLE_NAME + "</option>");
								$("#selectRecords").html("");
								loadDatas();
							}
						}
					}

			);
		});
	}
	

	function loadDatas(params) {
		$("#selectRecords").html("");
		$.post("sysRoleController.do?load", params, function(responseText,
				status, xhr) {
			var resultJson = $.parseJSON(responseText);
			for ( var index in resultJson) {
				var record = resultJson[index];
				if ($("#" + record.ROLE_ID).text() == "") {
					$("#selectRecords").append(
							"<option id='"+record.ROLE_ID+"' value='"+record.ROLE_ID+"' >"
									+ record.ROLE_NAME + "</option>");
				}

			}
		});
	}
	
	function choiceSelectRecords(){
		var selectedRecords= $("#selectedRecords")[0];
		if(selectedRecords.length>=1){
			var roleIds = "";
			var roleNames = "";
			for(var i = 0;i<selectedRecords.length;i++){
				if(i>0){
					roleIds+=",";
					roleNames+=",";
				}
				roleIds+=selectedRecords[i].value;
				roleNames+=selectedRecords[i].text;
			}
			art.dialog.data("selectRoleInfo", {
				roleIds:roleIds,
				roleNames:roleNames
			});// 存储
			AppUtil.closeLayer();
		}else{
			alert("请至少选择一条记录!")
		}
	}
	
	function loadSelectedRecords(){
		var roleIds = $("input[name='roleIds']").val().split(",");
		var roleNames = $("input[name='roleNames']").val().split(",");
		for(var i =0;i<roleIds.length;i++){
			$("#selectedRecords").append(
					"<option id='"+roleIds[i]+"' value='"+roleIds[i]+"' >"
							+ roleNames[i] + "</option>");
		}
	}

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		var roleIds = $("input[name='roleIds']").val();
		if(roleIds){
			loadSelectedRecords();
		}
		loadDatas();
		//加载自动补全数据
		loadAutoCompleteDatas();
		//移到右边
		$("#addSysRole").click(
			function() {
				var selectedLength= $("#selectedRecords")[0].length+$("#selectRecords option:selected").length;
				if((selectedLength>allowCount)&&allowCount!=0){
					alert("最多只能选择"+allowCount+"条记录!");
					return;
				}else{
					$("#selectRecords option:selected").appendTo(
					"#selectedRecords");
				}
		});
		$("#addAllSysRole").click(function() {
			var selectedLength= $("#selectedRecords")[0].length+$("#selectRecords")[0].length;
			if((selectedLength>allowCount)&&allowCount!=0){
				alert("最多只能选择"+allowCount+"条记录!");
				return;
			}else{
				$("#selectRecords option").appendTo("#selectedRecords");
			}
		});

		$("#removeSysRole").click(function() {
			$("#selectedRecords option:selected").appendTo("#selectRecords");
		});

		$("#removeAllSysRole").click(function() {
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
	<form id="SelectorForm" method="post"
		action="excelConfigController.do?saveOrUpdate">
		<input type="hidden" name="roleIds" value="${roleIds}"> 
		<input type="hidden" name="roleNames" value="${roleNames}"> 
		<input type="hidden" name="allowCount" value="${allowCount}"> 
		<input type="hidden" name="callbackFn" value="${callbackFn}">
		<div id="SelectorFormDiv">
        <div  class="newContTab">
        	<div class="eve-search">
                <input  class="eve-autoinputbg" id="AutoCompleteInput" type="text" 
                 onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" 
                 style="width: 218px;">
                <input type="button" name="" style="cursor: default;" class="eve-autoqueryicon">
            </div>
            <br class="clearFloat"/>
            <div class="eve-select">
            	<!-- 左边供选择块 -->
            	<div class="eve-selectleft">
            	    <select multiple="multiple" id="selectRecords"
                        style="width: 100%;height: 100%">
                    </select>
                </div>
                <!-- 左边供选择块结束 -->
                <!-- 中间部分按钮块 -->
                <div style="float:left; width: 50px;margin: 17% 0 0 10px;">
                    <span id="addSysRole"> 
                    <input type="button" class="selectorBtn" value=">" >
                    </span><br/>
                    <c:if test="${allowCount==0}">
                     <span id="addAllSysRole">
                    <input type="button" class="selectorBtn" value=">>" >
                    </span> <br />
                    </c:if>
                    <span id="removeSysRole"> 
                    <input type="button" class="selectorBtn" value="<" >
                    </span><br />
                    <c:if test="${allowCount==0}">
                    <span id="removeAllSysRole">
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

