<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.datagrid-btable tr{height: 40px;}
.datagrid-btable td{word-break: break-all;}
</style>
<script type="text/javascript">

	String.prototype.endWith=function(str){     
		  var reg=new RegExp(str+"$");     
		  return reg.test(this);        
	 }
  function formatFileName(val,row){
	  var fileId = row.FILE_ID;
	  var href = "<a href=\"javascript:void(0)\" ";
	  var fileName = row.FILE_NAME;
	  if(fileName.endWith(".doc")||fileName.endWith(".docx"))	  {
		href+=("onclick=\"ylDoc('"+row.FILE_PATH+"');\" >");
	  }else{
	    href+=("onclick=\"AppUtil.downLoadFile('"+fileId+"');\" >");
	  }
	  href+=(val+"</a>");
	  return href;
  }

  function ylDoc(filePath){
	  var params= "";
	  if(filePath&&filePath!=""){
			params+="&templatePath="+filePath;
		}
		$.dialog.open("variableController.do?ylTemplate"+params, {
	        title : "预览",
	        width: "100%",
	        height: "100%",
	        left: "0%",
	        top: "0%",
	        fixed: true,
	        lock : true,
	        resize : false
	    }, false);
  }
</script>

<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			fitcolumns="true" nowrap="false" 
			method="post" idfield="FILE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="fileAttachController.do?flowList&exeId=${exeId}&projectId=${projectId}">
			<thead>
				<tr>
				    <th data-options="field:'FULLNAME',align:'left'"  width="50">接收人员</th>
					<th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th> 
					<th data-options="field:'FILE_NAME',align:'left'"  width="250" formatter="formatFileName">材料名称</th>
					<th data-options="field:'CREATE_TIME',align:'left'"  width="100" >上传时间</th>
					<th data-options="field:'UPLOADER_NAME',align:'left'"  width="50">上传人员</th>
					<th data-options="field:'FLOW_TASKNAME',align:'left'"  width="100">环节名称</th>
					<th data-options="field:'UPLOADER_DEPNAME',align:'left'"  width="100">人员所处部门</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
