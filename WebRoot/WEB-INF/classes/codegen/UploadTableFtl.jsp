<div id="uploadTableToolId">
	<span style="float:left;"><a id="${uploadTableBtnId}"></a><font
		color="red">*最多只能上传${limitFileSize}个文件</font></span>
</div>

<table id="${uploadTableId}" class="easyui-datagrid" style="width:97%;height:130px;"
	checkOnSelect="false" selectOnCheck="false" idField="SWF_FILEID"
	url="fileAttachController.do?datagrid&BUS_TABLERECORDID=${recordIdName}&BUS_TABLENAME=${bindTableName}"
	data-options="rownumbers:true,singleSelect:true">
	<thead>
		<tr>
			<th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th>
			<th data-options="field:'SWF_FILEID',hidden:true" width="80">SWF_FILEID</th>
			<th field="FILE_NAME" width="37%" formatter="formatFileName">文件名</th>
			<th field="FILE_SIZE" width="10%">大小</th>
			<th field="FILE_TYPE" width="10%">类型</th>
			<th field="fileprogress" width="25%" formatter="formatFileProgress">状态</th>
			<th field="FILE_DELETE" width="10%" formatter="formatFileDel">删除</th>
		</tr>
	</thead>
</table>
