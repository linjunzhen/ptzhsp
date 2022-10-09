<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,icheck,ztree"></eve:resources>
<script type="text/javascript">
    var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		async : {
			enable : true,
			url : "webpage/developer/codeMission/js/controllTree.json"
		}
	};
	
	$(document).ready(function(){
		$.fn.zTree.init($("#controlSelectTree"), setting);	
		$("#ControlConfigTab").tabs({
		  onSelect: function(title,index){
			  if(index==1){
				  var treeObj = $.fn.zTree.getZTreeObj("controlSelectTree");
				  var nodes = treeObj.getCheckedNodes(true);
				  if(nodes&&nodes.length==1){
					  var control = nodes[0];
					  var controlType = control.id;
					  var targetTab = $("#ControlConfigTab").tabs("getTab",1); 
					  var missionId = $("#missionId").val();
					  var parentId = $("#parentId").val();
					  $("#ControlConfigTab").tabs("update", {
							tab: targetTab,
							options: {
								title: "基本配置",
								href: "controlConfigController.do?controlInfo&controlType="+controlType+"&missionId="+missionId+"&parentId="+parentId
							}
					  });
				  }else{
					  alert("请先选择控件!");
					  $("#ControlConfigTab").tabs("select",0);
				  }
			  }
		  }
		});

	});
	
	function submitInfo(){
		var array = $("#ControlConfigTabFormDiv [name]");
		var submitParams = {};
		for(var i =0;i<array.length;i++){
			var value = array.eq(i).val();
			if(value!=null&&value!=""){
				value = AppUtil.trim(value);
				var name = array.eq(i).attr("name");
				submitParams[name] = value;
			}
		}
		AppUtil.ajaxProgress({
			url : "controlConfigController.do?saveOrUpdate",
			params : submitParams,
			callback : function(resultJson) {
				if(resultJson.success){
					parent.window.location.reload();
				}else{
					//layer.msg(resultJson.msg, 2, 1);
					art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			}
		});
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
		
		<div id="ControlConfigTabFormDiv" class="easyui-layout" fit="true">
		    <input type="hidden" name="MISSION_ID" id="missionId" value="${missionId}" />
	   		<input type="hidden" name="PARENT_ID" id="parentId" value="${parentId}" />
			<div data-options="region:'center'" fit="true">
				<div class="easyui-tabs eve-tabs" fit="true" id="ControlConfigTab">
					<div title="控件选择" style="padding:10px">
					    <ul id="controlSelectTree" class="ztree"></ul>
					</div>
					<div title="基本配置" style="padding:10px">
					
					</div>
				</div>
			</div>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="button" onclick="submitInfo();"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
</body>
