<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String materCode = request.getParameter("materCode");
	request.setAttribute("materCode", materCode);
	String formId= request.getParameter("formId");
	request.setAttribute("formId", formId);
%>
<script type="text/javascript">

	function downloadDoc(){
		var exeId = '${EFLOWOBJ.EFLOW_EXEID}';
		var materCode='${materCode}';
		if(exeId!=""){
			var formId="${formId}";
			var validateResult =$("#"+formId).validationEngine("validate");
				if (validateResult) {
                    if(materCode==1){
                        window.location.href=__ctxPath+"/scclController/downLoadSGXKMater.do?exeId="+exeId;
                    }else if(materCode==21){
                        window.location.href=__ctxPath+"/scclController/downLoadJGYS1Mater.do?exeId="+exeId;
                    }else if(materCode==22){
                        window.location.href=__ctxPath+"/scclController/downLoadJGYS2Mater.do?exeId="+exeId;
                    }else if(materCode==3){
                        window.location.href=__ctxPath+"/scclController/downLoadXFSJMater.do?exeId="+exeId;
                    }else if(materCode==41){
                        window.location.href=__ctxPath+"/scclController/downLoadXFYS1Mater.do?exeId="+exeId;
                    }else if(materCode==42){
                        window.location.href=__ctxPath+"/scclController/downLoadXFYS2Mater.do?exeId="+exeId;
                    }
				} else {
					art.dialog({
						content: "请先完善信息,并暂存后，在打印！",
						icon: "warning",
						ok: true
					});
				}
		}else{
			art.dialog({
				content : "请完善信息并暂存后，再进行打印！",
				icon : "warning",
				ok : true
			});
		}
	}
	function applyFormSealFileUpload(materType,attachKey) {
        var itemCode="${serviceItem.ITEM_CODE}";
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		var EFLOW_BUSTABLENAME = $("input[name='EFLOW_BUSTABLENAME']").val();
		art.dialog.open('ktStampController/ktStampUploadView.do?busTableName='+EFLOW_BUSTABLENAME + "&uploadUserId="+
				uploadUserId + "&uploadUserName="+encodeURI(uploadUserName) + "&attachKey="+attachKey + "&itemCode="+itemCode,{
			title : "盖章",
			width : "1000px",
			height : "800px",
			lock : true,
			resize : false,
			close:function () {
				var resultJsonInfo = art.dialog.data("resultJsonInfo");
				if (resultJsonInfo) {
					//获取文件ID
					var fileId = resultJsonInfo.data.fileId;
					//获取文件名称
					var fileName = resultJsonInfo.data.fileName;
					//获取文件路径
					var filePath = resultJsonInfo.data.filePath;
					//获取文件的类型
					var fileType = resultJsonInfo.data.fileType;
					var attachType = 'attach';
					var spanHtml = "<p id=\""+fileId+"\"><a style=\"cursor: pointer;color: blue;margin-right: 5px;\"";
					spanHtml+=(" href=\"" + __file_server
							+ "download/fileDownload?attachId=" +fileId
							+ "&attachType="+attachType+"\" target=\"_blank\" ");
					spanHtml +="<font color=\"blue\">"+fileName+"</font></a>";
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delNewUploadMater('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>";
					$("input[name='APPLYFORMFILEFONT']").val(__file_server+ "download/fileDownload?attachId=" +fileId+ "&attachType="+attachType);
					//$("#applyFormFileId"+attachKey).append(spanHtml);
				}
			}
		})
	}

</script>
<style type="text/css">
	.btn:hover{
		color: #fff;
		background: #428bca;
	}
	.btn:focus{
		color: #fff;
		background: #428bca;
		outline: none;
	}
	.btn{
		width: 113px;
		padding:1px;
		background: #0a90f5;
		background-color: #4c8bf5;
		border-color: #357ebd;
		color: #fff;
		-moz-border-radius: 10px;
		-webkit-border-radius: 10px;
		border-radius: 10px; /* future proofing */
		-khtml-border-radius: 10px; /* for old Konqueror browsers */
		text-align: center;
		vertical-align: middle;
		border: 1px solid transparent;
		font-weight: 900;
		font-size:125%
	}
</style>

<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>申请表</span></li>
		</ul>
	</div>
	<form action="" id="APPLYFORM_FORM"  >
	<div class="bsboxC">
		<table cellpadding="0" cellspacing="0" class="bstable1" style="border: 0 dotted #a0a0a0;">
			<c:if test="${isQueryDetail==null}">
			<tr>
				<th> 申请表</th>
				<td>
					<input type="button"  class="btn" value="下载申请表" onclick="downloadDoc()"  />
				</td>
				<th> 盖章(申请表)</th>
				<td>
					<input type="text"   name="APPLYFORMFILEFONT"  disabled  class="tab_text " value="${busRecord.APPLYFORMFILEFONT}" />
					<a href="javascript:void(0);"  onclick="applyFormSealFileUpload('*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.rar;','')">
						<img  src="webpage/bsdt/applyform/images/tab_btn_gz.png">
					</a>
					<p id="applyFormFileId"></p>

				</td>
			</tr>
			</c:if>
				<c:if test="${isQueryDetail!=null}">
					<tr>
						<th> 申请表(办结后下载)</th>
						<td>
							<c:if test="${!empty busRecord.APPLYFORMFILEBACKEND}">
							<a href="${busRecord.APPLYFORMFILEBACKEND}"><input class="btn" type="button"
								   value="下载申请表"   /></a>
							</c:if>
						</td>
					</tr>
				</c:if>
		</table><br/>
	</div>
	</form>
</div>
