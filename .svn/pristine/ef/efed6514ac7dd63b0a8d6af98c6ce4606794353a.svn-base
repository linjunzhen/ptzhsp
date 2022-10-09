<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<script type="text/javascript">
//增加为常用意见
function addCommonOpinion(){
var opinionContent = $("textarea[name='EFLOW_HANDLE_OPINION']").val();
if(opinionContent.replace(/\s/g, "")==""){
	parent.art.dialog({
		content: "请填写您的意见!",
		icon:"error",
		ok: true
	});
}else{
	url="commonOpinionController.do?saveCommonOpinion";
	formData="&BUSINESS_NAME=flowOpinion&BUSINESS_TYPE=${nextTrans[0].flowNodeName}&OPINION_CONTENT="+opinionContent;
	AppUtil.ajaxProgress({
		url : url,
		params : formData,
		callback : function(resultJson) {
			if(resultJson.success){
					$("select[name='OPINION_CONTENT']").find("option:first").remove(); 
					$("select[name='OPINION_CONTENT']").prepend("<option value='"+resultJson.jsonString+"'>"+opinionContent+"</option>"); 
					$("select[name='OPINION_CONTENT']").prepend("<option value=''>==选择常用意见==</option>"); 
					$("select[name='OPINION_CONTENT']").val(resultJson.jsonString); 
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
			}else{
				parent.art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
		}
	});
}
}
//删除选中的常用意见
function  removeCommonOpinion(){
if($("select[name='OPINION_CONTENT']").children('option:selected').val()==""){
	parent.art.dialog({
		content: "请选择要删除的常用意见!",
		icon:"error",
		ok: true
	});
}else{
	art.dialog.confirm("您确定要删除选择的常用意见吗?", function() {
		var opinionContent =$("select[name='OPINION_CONTENT']").children('option:selected').text();
		url="commonOpinionController.do?removeCommonOpinion";
		formData="&BUSINESS_NAME=flowOpinion&BUSINESS_TYPE=${nextTrans[0].flowNodeName}&OPINION_CONTENT="+opinionContent;
		AppUtil.ajaxProgress({
			url : url,
			params : formData,
			callback : function(resultJson) {
				if(resultJson.success){
					$("select[name='OPINION_CONTENT']").children("option:selected").remove(); 
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
				}else{
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			}
		});
	});
}
}

//选中常用意见时更换意见内容
function selectCommonOpinion(){
var opinionContent = $("select[name='OPINION_CONTENT']").children('option:selected').text();
if( $("select[name='OPINION_CONTENT']").children('option:selected').val()=="")opinionContent="";
$("textarea[name='EFLOW_HANDLE_OPINION']").val(opinionContent);
}
</script>
<style>
.eve-select-width{
	width:400px !important;
}
</style>
<c:if test="${IS_STARTFLOW=='false'}">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="2">审核意见</th>
		</tr>
		<c:if test="${nodeConfig.IS_ADDPASS==1}">
			<tr>
				<td colspan="2" ><span style="width: 100px;float:left;text-align:right;">审核结果：</span>
					<eve:radiogroup typecode="PASSORNOPASS" width="130px"
						fieldname="EFLOW_ISPASS" defaultvalue="1"></eve:radiogroup></td>
			</tr>
		</c:if>
		<tr> 
		    <c:if test="${EFLOW_DESTTOEND=='false'}">
			<td ><span style="width: 90px;float:left;text-align:right;"><font
					class="dddl_platform_html_requiredFlag">*</font>意见内容：

			</span>
			</td>
			<td>
				<div style="margin-top:5px;margin-bottom:5px;">
				<eve:eveselect clazz="eve-input eve-select-width" dataParams="flowOpinion,${nextTrans[0].flowNodeName}" 
					 dataInterface="commonOpinionService.findCommonOpinionList" value=""
					  defaultEmptyText="==选择常用意见==" onchange="selectCommonOpinion();" name="OPINION_CONTENT">
				</eve:eveselect>
				<span style="width:30px;display:inline-block;text-align:right;">
				<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="addCommonOpinion();"
					style="cursor:pointer;vertical-align:middle;" title="添加到常用意见">
				</span>
				<span style="width:30px;display:inline-block;text-align:right;">
				<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeCommonOpinion();" 
					style="cursor:pointer;vertical-align:middle;" title="删除选中的常用意见">
				</span>
				</div>
			 <textarea rows="6" cols="6"

					class="eve-textarea validate[required,maxSize[1500]]" style="width: 500px" name="EFLOW_HANDLE_OPINION"></textarea>
			</td>
			</c:if>
			
			<c:if test="${EFLOW_DESTTOEND=='true'}">
			<td><span style="width: 100px;float:left;text-align:right;">意见内容：
			</span>
			 <textarea rows="4" cols="5"
					class="eve-textarea validate[maxSize[1500]]" style="width: 400px" name="EFLOW_HANDLE_OPINION"></textarea>
			</td>
			</c:if>
		</tr>
	</table>
</c:if>