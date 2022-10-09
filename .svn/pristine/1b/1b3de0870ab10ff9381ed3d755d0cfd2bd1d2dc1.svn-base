<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,autocomplete"></eve:resources>

<script type="text/javascript">
	
	
	function loadAutoCompleteDatas() {
		$.post("flowFormController.do?load&start=0&limit=1000", {

		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			$("#AutoCompleteInput").autocomplete(
					resultJson,
					{
						matchContains : true,
						formatItem : function(row, i, max) {
							//下拉框显示
							return "<div>" + row.FORM_NAME+"</div>";
						},
						formatMatch : function(row) {
							//查询条件
							return row.FORM_NAME+row.PINYIN;
						},
						formatResult : function(row, i, max) {
							//显示在文本框中
							return row.FORM_NAME;
						}
					}).result(
					function(event, data, formatted) {

						if ($("#selectedRecords #" + data.FORM_ID).text() == ""){
							var allowCount = $("input[name='allowCount']").val();
							var selectedLength= $("#selectedRecords")[0].length+1;
							if((selectedLength>allowCount)&&allowCount!=0){
								alert("最多只能选择"+allowCount+"条记录!");
								return;
							}else{
								$("#selectedRecords").append(
										"<option id='"+data.FORM_ID+"' value='"+data.FORM_ID+"'>"
												+ data.FORM_NAME + "</option>");
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
		$.post("flowFormController.do?load&start=0&limit=1000", params, function(responseText,
				status, xhr) {
			var resultJson = $.parseJSON(responseText);
			for ( var index in resultJson) {
				var record = resultJson[index];
				if ($("#" + record.FORM_ID).text() == "") {
					$("#selectRecords").append(
							"<option id='"+record.FORM_ID+"' value='"+record.FORM_ID+"' >"
									+ record.FORM_NAME + "</option>");
				}

			}
		});
	}
	
	function choiceSelectRecords(){
		var selectedRecords= $("#selectedRecords")[0];
		if(selectedRecords.length>=1){
			var formIds = "";
			var formNames = "";
			for(var i = 0;i<selectedRecords.length;i++){
				if(i>0){
					formIds+=",";
					formNames+=",";
				}
				formIds+=selectedRecords[i].value;
				formNames+=selectedRecords[i].text;
			}
			art.dialog.data("selectFormInfo", {
				formIds:formIds,
				formNames:formNames
			});// 存储
			AppUtil.closeLayer();
		}else{
			alert("请至少选择一条记录!")
		}
	}
	
	function loadSelectedRecords(){
		var formIds = $("input[name='formIds']").val().split(",");
		var formNames = $("input[name='formNames']").val().split(",");
		for(var i =0;i<formIds.length;i++){
			$("#selectedRecords").append(
					"<option id='"+formIds[i]+"' value='"+formIds[i]+"' >"
							+ formNames[i] + "</option>");
		}
	}

	$(function() {
		var allowCount = $("input[name='allowCount']").val();
		var formIds = $("input[name='formIds']").val();
		if(formIds){
			loadSelectedRecords();
		}
		loadDatas();
		//加载自动补全数据
		loadAutoCompleteDatas();
		//移到右边
		$("#addRecord").click(
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
		$("#addAllRecord").click(function() {
			var selectedLength= $("#selectedRecords")[0].length+$("#selectRecords")[0].length;
			if((selectedLength>allowCount)&&allowCount!=0){
				alert("最多只能选择"+allowCount+"条记录!");
				return;
			}else{
				$("#selectRecords option").appendTo("#selectedRecords");
			}
		});

		$("#removeRecord").click(function() {
			$("#selectedRecords option:selected").appendTo("#selectRecords");
		});

		$("#removeAllRecord").click(function() {
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
		<input type="hidden" name="formIds" value="${formIds}"> 
		<input type="hidden" name="formNames" value="${formNames}"> 
		<input type="hidden" name="allowCount" value="${allowCount}"> 
		<input type="hidden" name="callbackFn" value="${callbackFn}">
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
                    <span id="addRecord"> 
                    <input type="button" class="selectorBtn" value=">" >
                    </span><br/>
                    <c:if test="${allowCount==0}">
                     <span id="addAllRecord">
                    <input type="button" class="selectorBtn" value=">>" >
                    </span> <br />
                    </c:if>
                    <span id="removeRecord"> 
                    <input type="button" class="selectorBtn" value="<" >
                    </span><br />
                    <c:if test="${allowCount==0}">
                    <span id="removeAllRecord">
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

