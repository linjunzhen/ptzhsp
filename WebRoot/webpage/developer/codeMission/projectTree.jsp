
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.FileUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
	<eve:resources
		loadres="jquery,easyui,apputil,layer,validationegine,icheck,artdialog,ztree"></eve:resources>

	<script type="text/javascript">
 var setting = {
     check: {
	 enable: true,
	 chkDisabledInherit: true
	 },
		 view : {
		 showLine :false,
		 selectedMulti : true,
		 dblClickExpand : false
		 },
		 data : {
		 simpleData : {
		 enable : true
		 }
		 }
		 };
 var treeNodes; 
 $(function(){
	 $.ajax({
	 async : false,
	 cache:false,
	 type: 'POST',
	 dataType : "json",
	 url: "codeMissionController.do?projectTreeJson",//请求的action路径
	 error: function () {//请求失败处理函数
	 alert('请求失败');
	 },
	 success:function(data){ //请求成功后处理函数。
	 treeNodes = data; //把后台封装好的简单Json格式赋给treeNodes
	 }
	 });
	 $.fn.zTree.init($("#tree"), setting, treeNodes);
 });
 function onCheck(){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		nodes=treeObj.getCheckedNodes(true);
		var s= nodes[0].path;
		var filePath = ""
			for(var i=0;i<nodes.length;i++){
				path=nodes[i].path.replace(s,"");
				filePath+=path+","
			}
		//alert(filePath);
	
		if(filePath!=null&&filePath!=""){
			$("#wait").show();
		 $.ajax({
			 type: 'POST',
			 url: "codeMissionController.do?copyFile",//请求的action路径
			 data:{'filePath':filePath},
			 error: function () {//请求失败处理函数
			 alert('请求失败');
			 },
			 success:function(data){ //请求成功后处理函数。
			$("#wait").hide();
			 var obj = eval("("+data+")");
			alert("提取更新包成功 !"+obj.info);
			 AppUtil.closeLayer();
			 }
			 });
		}else{
			alert("请选择跟新包文件")
		}
}
</script>
</head>

<body style="margin: 0px; background-color: #f7f7f7;">
	<div class="zTreeDemoBackground left" style="height: 80%;">
		<ul id="tree" class="ztree" style="height: 100%;"></ul>
	</div>
	<div class="eve_buttons" style="width: 30%; right: 0;">
		<input value="确定" type="button"
			class="z-dlg-button z-dialog-okbutton aui_state_highlight"
			onclick="onCheck()" />
		<input value="取消" type="button"
			class="z-dlg-button z-dialog-cancelbutton"
			onclick="AppUtil.closeLayer();" />
	</div>
	<div id="wait" style="display: none;position: absolute;top: 200px;left: 200px;"><img src="plug-in/artdialog-4.1.7/skins/icons/loading.gif" />正在压缩...</div>
</body>




