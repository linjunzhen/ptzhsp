<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript">

      /**
      * 标题附件上传对话框
      */
     function openResultFileUploadDialog4(){
         //定义上传的人员的ID
         var uploadUserId = $("input[name='uploadUserId']").val();
         //定义上传的人员的NAME
         var uploadUserName = $("input[name='uploadUserName']").val();
         //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
         art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_INTERNAL_STOCK&uploadUserId='
             +uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
             title: "上传(附件)",
             width: "620px",
             height: "300px",
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
                         var fileid=$("input[name='FILE_ID4']").val();
                         if(fileid!=""&&fileid!=null){
                             $("input[name='FILE_ID4']").val(fileid+";"+uploadedFileInfo.fileId);
                         }else{
                             $("input[name='FILE_ID4']").val(uploadedFileInfo.fileId);
                         }
                         var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                         spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
                         spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                         spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile4('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
                         $("#fileListDiv4").append(spanHtml);
                     }
                 }
                 art.dialog.removeData("uploadedFileInfo");
             }
         });
      }
      
      function delUploadFile4(fileId,attacheKey){
         //AppUtil.delUploadMater(fileId,attacheKey);
         art.dialog.confirm("您确定要删除该文件吗?", function() {
             //移除目标span
             $("#"+fileId).remove();
             var FILE_ID4=$("input[name='FILE_ID4']").val();
             var arrayIds=FILE_ID4.split(";");
             for(var i=0;i<arrayIds.length;i++){
                 if(arrayIds[i]==fileId){
                     arrayIds.splice(i,1);
                     break;
                 }
             }
             $("input[name='FILE_ID4']").val(arrayIds.join(";"));
         });
     }

	$(function(){		
           var ybfileids="${busRecord.FILE_ID4 }";
           $.post("executionController.do?getResultFiles&fileIds="+ybfileids,{fileIds:ybfileids}, function(resultJson) {
               if(resultJson!="websessiontimeout"){
                   $("#fileListDiv4").html("");
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
                   $("#fileListDiv4").html(newhtml);
               }
           });
	});
</script>
<c:if test="${busRecord.ISMEDICALREGISTER == '1'}">
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
        <tr>
            <th colspan="4" >医疗保险登记信息</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>医保经办人：</td>
            <td><input type="text" class="tab_text validate[required]" maxlength="16"
                       name="MEDICAL_OPERRATOR"  value="${busRecord.MEDICAL_OPERRATOR }"/></td>
            <td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
            <td><input type="text" class="tab_text validate[required]" maxlength="32"
                       name="MEDICAL_PHONE"  value="${busRecord.MEDICAL_PHONE }"/></td>
        </tr>
        <tr>
			<td class="tab_width"><font class="tab_color">*</font>是否自动生成医疗保障企业开户信息：</td>
			<td colspan="3">
				<input type="radio" class="validate[required]" name="IS_OPEN_YBACCOUNT" value="1" <c:if test="${busRecord.IS_OPEN_YBACCOUNT==1}"> checked="checked"</c:if> />是
				<input type="radio" class="validate[required]" name="IS_OPEN_YBACCOUNT" value="0" <c:if test="${busRecord.IS_OPEN_YBACCOUNT==0}"> checked="checked"</c:if>/>否
			</td>
		</tr>
		<tr>
			<td class="tab_width">是否同时进行员工参保：</td>
			<td colspan="3">
				<input type="radio" name="IS_EMPLOY_INMEDICAL" value="1" onclick="initYbCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INMEDICAL==1}"> checked="checked"</c:if> />是
				<input type="radio" name="IS_EMPLOY_INMEDICAL" value="0" onclick="initYbCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INMEDICAL!=1}"> checked="checked"</c:if>/>否
			</td>
		</tr>
		<tbody id="ybcb" style="display: none;">
			<tr>
				<input  name="FILE_ID4"  type="hidden" value="${busRecord.FILE_ID4 }">
				<td class="tab_width">附件：</td>
				<td colspan="3">
					<div style="width:100%;display: none;" id="UPDATE_FILE_DIV4"></div>
						<a href="javascript:void(0);" onclick="openResultFileUploadDialog4()">
							<img id="UPDATE_FILE_BTN4" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
						</a>
					<div style="width:100%;" id="fileListDiv4"></div>
				</td>
			</tr>
			<tr>
				<td class="tab_width">样表下载：</td>
				<td colspan="3">
					<a href='webpage/commercial/mater/职工基本医疗保险参保登记表（空表）.xlsx' style="color: blue;">职工基本医疗保险参保登记表（空表）</a>&nbsp;&nbsp;
					<a href='webpage/commercial/mater/职工基本医疗保险参保登记表（样表）.xlsx' style="color: blue;">职工基本医疗保险参保登记表（样表）</a>
				</td>
			</tr>
			<tr>
				<td class="tab_width">办事指南：</td>
				<td colspan="3">
					<a href="https://zwfw.fujian.gov.cn/person-todo/todo-fingerpost?infoType=1&unid=77B49B51A8875C539AF4E25D8515848A&type=1&siteUnid=EC4BF9378FEEB8761D62BEDFF8EDE105" target='_blank' style="color: blue;">职工基本医疗保险参保登记</a>
				</td>
			</tr>
		</tbody>
    </table>
</c:if>
