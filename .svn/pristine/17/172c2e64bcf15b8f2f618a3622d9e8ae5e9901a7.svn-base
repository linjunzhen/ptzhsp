<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript">
	
	 /**
      * 标题附件上传对话框
      */
     function openResultFileUploadDialog3(){
         //定义上传的人员的ID
         var uploadUserId = $("input[name='uploadUserId']").val();
         //定义上传的人员的NAME
         var uploadUserName = $("input[name='uploadUserName']").val();
         //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
         art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_NZ_JOINTVENTURE&uploadUserId='
             +uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
             title: "上传(附件)",
             width: "620px",
             height: "240px",
             lock: true,
             resize: false,
             close: function(){
                 var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                 if(uploadedFileInfo){
                     if(uploadedFileInfo.fileId){
                         var fileType = 'gif,jpg,jpeg,bmp,png';
                         var attachType = 'attach';
                         if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
                             attachType = "image";
                         }
                         var fileid=$("input[name='FILE_ID3']").val();
                         if(fileid!=""&&fileid!=null){
                             $("input[name='FILE_ID3']").val(fileid+";"+uploadedFileInfo.fileId);
                         }else{
                             $("input[name='FILE_ID3']").val(uploadedFileInfo.fileId);
                         }
                         var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                         spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
                         spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                         spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile3('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
                         $("#fileListDiv3").append(spanHtml);
                     }
                 }
                 art.dialog.removeData("uploadedFileInfo");
             }
         });
      }
      
      function delUploadFile3(fileId,attacheKey){
         //AppUtil.delUploadMater(fileId,attacheKey);
         art.dialog.confirm("您确定要删除该文件吗?", function() {
             //移除目标span
             $("#"+fileId).remove();
             var FILE_ID3=$("input[name='FILE_ID3']").val();
             var arrayIds=FILE_ID3.split(";");
             for(var i=0;i<arrayIds.length;i++){
                 if(arrayIds[i]==fileId){
                     arrayIds.splice(i,1);
                     break;
                 }
             }
             $("input[name='FILE_ID3']").val(arrayIds.join(";"));
         });
     }
     
      $(function(){
           var fileids="${busRecord.FILE_ID3 }";
           $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
               if(resultJson!="websessiontimeout"){
                   $("#fileListDiv3").html("");
                   var newhtml = "";
                   var list=resultJson.rows;
                   for(var i=0; i<list.length; i++){
                       newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                       newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                       newhtml+=list[i].FILE_NAME+'</a>';
                       newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                       newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                       newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a></p>"
                       newhtml+='</p>';
                   }
                   $("#fileListDiv3").html(newhtml);
               }
           });
	});
</script>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="shbxdjxx">
	<tr>
		<th colspan="4" >社会保险登记信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>社保经办人：</td>
		<td><input type="text" class="tab_text validate[required]" maxlength="16"
			name="INSURANCE_OPERRATOR"  value="${busRecord.INSURANCE_OPERRATOR }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td><input type="text" class="tab_text validate[required]" maxlength="32"
			name="INSURANCE_PHONE"  value="${busRecord.INSURANCE_PHONE }"/></td>
	</tr>
	<tr>		
		<td class="tab_width"><font class="tab_color">*</font>身份证号码：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required,validate[vidcard]]"
			name="INSURANCE_IDNO"  value="${busRecord.INSURANCE_IDNO }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>参加险种：</td>
		<td colspan="3">
			<eve:excheckbox
				dataInterface="dictionaryService.findDatasForSelect"
				name="INSURANCE_TYPE" width="650px;" clazz="checkbox"
				dataParams="insuranceType" value="${busRecord.INSURANCE_TYPE}">
			</eve:excheckbox>
			
		</td>
	</tr>
	
	<tr>
		<td class="tab_width">是否同时进行员工参保：</td>
		<td colspan="3">
			<input type="radio" name="IS_EMPLOY_INSU" value="1" onclick="initSfCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INSU==1}"> checked="checked"</c:if> />是
			<input type="radio" name="IS_EMPLOY_INSU" value="0" onclick="initSfCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INSU!=1}"> checked="checked"</c:if>/>否
		</td>
	</tr>
	<tr class='ygupload' style="display:none">
		<input  name="FILE_ID3"  type="hidden"
		value="${busRecord.FILE_ID3 }">
		<td class="tab_width">附件：</td>
		<td colspan="3">
			<div style="width:100%;display: none;" id="UPDATE_FILE_DIV3"></div>
				<a href="javascript:void(0);" onclick="openResultFileUploadDialog3()">
					<img id="UPDATE_FILE_BTN3" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				</a>
			<div style="width:100%;" id="fileListDiv3"></div>
		</td>
	</tr>
	<tr class='ygupload'  style="display:none">
		<td class="tab_width">样表下载：</td>
		<td colspan="3">
			<a href='webpage/commercial/mater/qyzg.xlsx' style="color: blue;">福建省企业职工基本养老保险登记表</a>&nbsp;&nbsp;
			<a href='webpage/commercial/mater/shry.xlsx' style="color: blue;">参加社会保险人员增员申报表</a>
		</td>
	</tr>
	<tr class='ygupload'  style="display:none">
		<td class="tab_width">办事指南：</td>
		<td colspan="3">
			<a href="https://zwfw.fujian.gov.cn/person-todo/todo-fingerpost?infoType=1&unid=AF770E43BA61E0F67D5C88FFDB580B07&type=1&siteUnid=EC4BF9378FEEB8761D62BEDFF8EDE105&noNotice=true" target='_blank' style="color: blue;">工伤保险及企业职工基本养老保险职工参保登记</a>
			&nbsp;&nbsp;<a href="https://zwfw.fujian.gov.cn/person-todo/todo-fingerpost?infoType=1&unid=AB84E16FB662A639391555739F4A32B7&type=1&siteUnid=EC4BF9378FEEB8761D62BEDFF8EDE105&noNotice=true" target='_blank' style="color: blue;">工伤保险及企业职工基本养老保险人员增减</a>
		</td>
	</tr>
			
</table>
