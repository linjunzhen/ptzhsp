<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<script type="text/javascript" src="plug-in/jqueryUpload/AppUtilNew.js"></script>
<script type="text/javascript">
    $(function(){
        var fileids="${busRecord.FILE_ID1 }";
        $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                $("#fileListDiv1").html("");
                var newhtml = "";
                var list=resultJson.rows;
                for(var i=0; i<list.length; i++){
                    newhtml+="<p id='"+list[i].FILE_ID+"'>";
                    newhtml+='<a style="cursor: pointer;color: blue;margin-right: 5px;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+=list[i].FILE_NAME+'</a>';
                    newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                    newhtml +="</p><br/>"
                }
                $("#fileListDiv1").html(newhtml);
            }
        });
    });

    function delUploadFile1(fileId,attacheKey){
        AppUtil.delUploadMater(fileId,attacheKey);
        var FILE_ID1=$("input[name='FILE_ID1']").val();
        var arrayIds=FILE_ID1.split(";");
        for(var i=0;i<arrayIds.length;i++){
            if(arrayIds[i]==fileId){
                arrayIds.splice(i,1);
                break;
            }
        }
        $("input[name='FILE_ID1']").val(arrayIds.join(";"));
    }
</script>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">	
	<tr>
		<th colspan="4">前置审批及其他文件</th>
	</tr>
	<tr>
		<td class="tab_width">附件：</td>
		<td colspan="3"> <div style="width:100%;display: none;" id="UPDATE_FILE_DIV"></div><a id="UPDATE_FILE_BTN"></a>
		<div style="width:100%;" id="fileListDiv1"></div></td>			
	</tr>
</table>