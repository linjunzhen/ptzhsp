<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.pdl{
padding-left: 20px;
}
.extract-button {
    background: #0c83d3 none repeat scroll 0 0;
    border: 1px solid #0c83d3;
    border-radius: 3px;
    color: #fff;
    cursor: pointer;
    height: 26px;
    padding: 0 20px;
}
.extract-button2 {
    background: #0c83d3 none repeat scroll 0 0;
    border: 1px solid #0c83d3;
    border-radius: 3px;
    color: #fff;
    cursor: pointer;
    height: 26px;
    padding: 0 5px;
}
</style>
<script type="text/javascript">
/**初始化绑定select事件*/
$(function(){
	  //初始化网站上传图片
	  initUploadPic();
	  $("input[name='SHXYDMSFZHM']").blur(function(){
		  isExistsShxydm();
	  });
});
function isExistsShxydm(successCallback){
	FdaAppUtil.ajaxProgress({
	  	url : "foodProductionController/isExistsShxydm.do",
		params : {
			SHXYDMSFZHM:$("input[name='SHXYDMSFZHM']").val(),
			IS_HEALTH:"2"
		},
		callback : function(resultJson) {
			if (resultJson.success) {
				if(successCallback){successCallback.call();}
			}else {
				art.dialog({
            		content: resultJson.msg,
            		icon:"warning",
            		ok: true
        		});
			}
		}
  });
}
var swfUploadPic;
function initUploadPic(){
	var ztFilepath = $("input[name='WZJTLJ']").val();
	var successfulUploads = 0;
	if(null!=ztFilepath&&ztFilepath.length>0){
		successfulUploads = 1;
	}
	swfUploadPic = FdaAppUtil.bindSwfUpload({
        file_types : "*.jpg;*.jpeg;*.png;*.png;",
        file_size_limit :"10 MB",
        post_params : {
            "uploadPath":"WebsiteScreenshot"
        },
        file_upload_limit :1,
        button_placeholder_id:"WZJTLJ_UPLOAD",
        successful_uploads:successfulUploads,
        upload_success_handler:function(resultJson){
            if(resultJson.success==true){
                //获取文件ID
                var fileId = resultJson.fileId;
                //获取文件名称
                var fileName = resultJson.fileName;
                //获取文件路径
                var filePath = resultJson.filePath;
                
                var spanHtml = "<a href=\"javascript:void(0);\" title=\""+fileName+"\" ";
                spanHtml+=(" onclick=\"FdaAppUtil.downLoadFile('"+fileId+"');\">");
                spanHtml +="<font color=\"blue\">下载</font></a>&nbsp;"
                spanHtml +="<a href=\"javascript:void(0);\"   onclick=\"delPicFile('"+fileId+"');\" ><font color=\"red\">删除</font></a>";
                $("#WZJTLJ_DIV").append(spanHtml);
                $("input[name='WZJTLJ']").val(fileId);
                
            }
        }
    });
}

function delPicFile(fileId){
	art.dialog.confirm("您确定要删除该文件吗?", function() {
        var layload = layer.load("正在提交请求中…");
        $.post("fileAttachController.do?multiDel",{
                selectColNames :fileId
            }, function(responseText, status, xhr) {
                layer.close(layload);
                var resultJson = $.parseJSON(responseText);
                if(resultJson.success == true){
                	$("#WZJTLJ_DIV").html("");
                    $("input[name='WZJTLJ']").val("");
                    swfUploadPic.setStats({successful_uploads:0});
                }else{
                    art.dialog({
                        content: resultJson.msg,
                        icon:"error",
                        ok: true
                    });
                }
        });
    });
}

function txsm(obj){
	var con = $(obj).attr('title');
 	art.dialog({
 		lock : true,
 		title:'填写说明',
		content: con
	});
}

</script>
<!--开始信用记录查询页面 -->
<jsp:include page="../common/queryFailureRecord.jsp"></jsp:include>
<!--结束信用记录查询页面 -->
<form action="" id="BASEINFO_FORM"  >
	<div class="syj-title1"><span>基本信息</span></div>
	<input type="hidden" name="ZZXX_STATE" value="${ZZXX_STATE}" />
	<input name="ISPROVINCE" type="hidden" value="0"/>
	<input name="SQLX" type="hidden" value="1"/>
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<%-- <th><font color="#ff0101">*</font>申请类型</th>
			<td>					
					<eve:excheckbox name="SQLX" width="80%" datainterface="dictionaryService.findList"
					queryparamjson="{TYPE_CODE:'spscsqlx',ORDER_TYPE:'ASC'}" puremode="true" isstartp="false"></eve:excheckbox>
			</td>
			 --%>
			<th><font color="#ff0101">*</font>申请人名称</th>
			<td colspan="3"><input type="text"
				 maxlength="32" class="syj-input1 validate[required]" placeholder="请输入名称"
				name="SQRMC" /></td>
		</tr>
		<tr>
			<th>营业执照注册号</th>
			<td><input type="text"
				 maxlength="32" class="syj-input1" placeholder="请输入营业执照注册号"
				name="YYZZZCH" /></td>
					
			<th><font color="#ff0101">*</font>统一社会信用代码<br/>(身份证号码)</th>
			<td><input type="text" placeholder="请参照右侧填写说明" style="width: 70%;"
				maxlength="32" class="syj-input1 validate[required]" name="SHXYDMSFZHM" />
			<a href="javascript:void(0);" onclick="txsm(this);"
			title="无社会信用代码可暂时填写组织机构代码"
			style="width: 20%;color: #0C83D3;">填写说明</a>	
			<%-- <input style="border-color:red;background-color: red;float:right;" type="button" onclick="queryFailureRecord('SHXYDMSFZHM','SQRMC','1','${ITEM.ITEM_NAME}');" class="extract-button" value="查询失信黑名单记录"> --%>
			</td>
		</tr>
		<tr>
			<th ><font color="#ff0101">*</font>法定代表人（负责人）姓名</th>
			<td colspan="3"><input type="text" style="width: 70%;"
				 maxlength="32" class="syj-input1 validate[required]" placeholder="请输入法定代表人（负责人）姓名"
				name="FDDBRXM" /></td>
			<!-- <th>固定电话</th>
			<td>
				<input type="text"
				 maxlength="20" class="syj-input1 validate[custom[fixPhoneWithAreaCode]]" placeholder="请输入固定电话"
				name="FDDBRGDDH" />
			</td> -->
			<!-- <th><font color="#ff0101">*</font>有 效 期（年）</th>
			<td>
				<input type="text" placeholder="请输入有 效 期"
				 maxlength="4" readonly="readonly" class="syj-input1 validate[required,custom[integer],min[0],max[5]]"
				 name="YXQ" value="5" />
			</td> -->
		</tr>
		<!-- <tr>
		<th><font color="#ff0101">*</font>移动电话</th>
			<td>
			<input type="text" maxlength="20" class="syj-input1 validate[required],custom[mobilePhoneLoose]"
			  placeholder="请输入移动电话" name="FDDBRYDDH" />
			</td>
		</tr> -->
		<tr>
			<th rowspan="2"><font color="#ff0101">*</font>联系人</th>
			<td rowspan="2"><input type="text" placeholder="请输入联系人"
				 maxlength="32" class="syj-input1 validate[required]"
				name="LXR" /></td>
			<th>固定电话</th>
			<td>
				<input type="text" placeholder="请输入固定电话"
				class="syj-input1 validate[custom[fixPhoneWithAreaCode]]" name="LXRGDDD" />
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>移动电话</th>
			<td>
				<input type="text" placeholder="请输入移动电话"
				 class="syj-input1 validate[required],custom[mobilePhoneLoose]" name="LXRYDDD" />
			</td>
			
		</tr>
		<tr>
			<th>传真</th>
			<td>
			<input type="text" placeholder="请输入传真"
				maxlength="16" class="syj-input1 validate[custom[fixPhoneWithAreaCode]]" name="CZ" />
			</td>
			<th>电子邮箱</th>
			<td><input type="text" placeholder="请输入电子邮箱"
				maxlength="64" class="syj-input1 validate[custom[email]]" name="DZYJ" /></td>
		</tr> 
		
		<!-- <tr>
			<th><font color="#ff0101">*</font>申请副本数（份）</th>
			<td><input type="text" placeholder="请输入申请副本数"
				 maxlength="4" class="syj-input1 validate[required,custom[integer],min[0]]"
				name="SQFBS" /></td>
			<th><font color="#ff0101">*</font>有 效 期（年）</th>
			<td>
				<input type="text" placeholder="请输入有 效 期"
				 maxlength="4" readonly="readonly" class="syj-input1 validate[required,custom[integer],min[0],max[5]]"
				 name="YXQ" value="5" />
			</td>
		</tr> -->
    	<jsp:include page="./address.jsp">
    		<jsp:param value="${EFLOW_VARS_JSON}" name="EFLOW_VARS_JSON"/>
    	</jsp:include>
		<tr>
			<th>备注</th>
			<td colspan="3">
				<textarea name="BZ" cols="80" rows="5" maxlength="500" class="validate[maxSize[500]]"></textarea>
			</td>
		</tr>
	</table>
</form>
