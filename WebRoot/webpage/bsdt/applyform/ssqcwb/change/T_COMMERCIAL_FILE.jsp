<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
                    newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                    newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+=list[i].FILE_NAME+'</a>';
                    newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                    newhtml +="</p>"
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



    $(function(){
        var fileids="${busRecord.FILE_ID2 }";
        $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                $("#fileListDiv2").html("");
                var newhtml = "";
                var list=resultJson.rows;
                for(var i=0; i<list.length; i++){
                    newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                    newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+=list[i].FILE_NAME+'</a>';
                    newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                    newhtml+='</p>';
                }
                $("#fileListDiv2").html(newhtml);
            }
        });
    });



    function delUploadFile2(fileId,attacheKey){
        //AppUtil.delUploadMater(fileId,attacheKey);
        art.dialog.confirm("您确定要删除该文件吗?", function() {
            //移除目标span
            $("#"+fileId).remove();
            var FILE_ID2=$("input[name='FILE_ID2']").val();
            var arrayIds=FILE_ID2.split(";");
            for(var i=0;i<arrayIds.length;i++){
                if(arrayIds[i]==fileId){
                    arrayIds.splice(i,1);
                    break;
                }
            }
            $("input[name='FILE_ID2']").val(arrayIds.join(";"));
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
                    newhtml+='</p>';
                }
                $("#fileListDiv3").html(newhtml);
            }
        });
    });



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
    

</script>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
<tr>
	<th colspan="4" >前台用户上传附件</th>
</tr>
<tr>
	<td class="tab_width">前置审批及其他文件：</td>
	<td colspan="3"> <div style="width:100%;display: none;" id="UPDATE_FILE_DIV"></div><a id="UPDATE_FILE_BTN"></a>
		<div style="width:100%;" id="fileListDiv1"></div></td>
</tr>
<tr>
	<td class="tab_width">冠福建省名或无行政区划名称材料：</td>
	<td colspan="3"> <div style="width:100%;display: none;" id="UPDATE_FILE_DIV2"></div><a id="UPDATE_FILE_BTN2"></a>
		<div style="width:100%;" id="fileListDiv2"></div></td>
</tr> 
<tr>
	<td class="tab_width">外省企业法人股东：</td>
	<td colspan="3"> <div style="width:100%;display: none;" id="UPDATE_FILE_DIV3"></div><a id="UPDATE_FILE_BTN3"></a>
		<div style="width:100%;" id="fileListDiv3"></div></td>
</tr> 
</table>