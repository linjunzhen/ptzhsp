function formatFileDel(val,row){
	var FILE_ID = row.FILE_ID;
	if(FILE_ID&&FILE_ID!="0"){
		var uploadTableId = "${uploadTableId}";
		var uploadButtonId = "${uploadTableBtnId}";
		return "<a href='#' onclick='AppUtil.removeUploadFile(\""+FILE_ID+"\",null,null,\""+uploadTableId+"\",\""+uploadButtonId+"\");' >"+"删除</a>";
	}
}

function formatFileName(val,row){
	var FILE_ID = row.FILE_ID;
	if(FILE_ID&&FILE_ID!="0"){
		var uploadTableId = "${uploadTableId}";
		var uploadButtonId = "${uploadTableBtnId}";
		return "<a href='#' onclick='AppUtil.downLoadFile(\""+FILE_ID+"\");' ><font color='blue'>"+row.FILE_NAME+"</font></a>";
	}
}

function formatFileProgress(val,row){
	var FILE_ID = row.FILE_ID;
	if(FILE_ID&&FILE_ID!="0"){
		return "上传完毕";
	}
}