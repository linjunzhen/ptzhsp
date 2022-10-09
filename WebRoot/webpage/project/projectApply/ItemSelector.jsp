<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

SysUser sysUser = AppUtil.getLoginUser();
//获取登录用户的角色CODE
Set<String> roleCodes = sysUser.getRoleCodes();
//获取菜单KEY
String resKey = sysUser.getResKeys();
//判断是否经营范围备注描述管理员
boolean isJyfw = roleCodes.contains("TZXMCHAN");
//判断是否超级管理员或经营范围备注描述管理员
if("__ALL".equals(resKey)||isJyfw){
    request.setAttribute("isJyfwAll",true);
}else{
    request.setAttribute("isJyfwAll",false);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
	<eve:resources loadres="jquery,apputil,easyui,validationegine,artdialog,ztree,swfupload,layer,json2"></eve:resources>
	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">
	$(document).ready(function() {
	});
    function doSelectRows(){
    	var rows = $("#ProjectItemGrid").datagrid("getChecked");
    	if(rows.length==0){
			art.dialog({
				content: "请至少选择一条记录!",
				icon:"warning",
			    ok: true
			});			
			return null;
    	}else if('${account}'<=1&&rows.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
    		var defKeys = "";
			var itemCodes = "";
			var itemNames = "";
			for(var i = 0;i<rows.length;i++){
				if(i>0){
					defKeys+=",";
					itemCodes+=",";
					itemNames+=",";
				}
				defKeys+=rows[i].DEF_KEY;
				itemCodes+=rows[i].ITEM_CODE;
				itemNames+=rows[i].ITEM_NAME;
			}
			if(defKeys.indexOf('6')>-1){
				parent.art.dialog({
					content: "该事项审批流程未规范，请梳理后联系技术处（059123122315）协助重新配置。 ",
					icon:"error",
					ok: true
				});
				return null;
			}
    		art.dialog.data("selectProjectItemInfo", {
    			defKeys:defKeys,
    			itemCodes:itemCodes,
    			itemNames:itemNames
			}); 
    		AppUtil.closeLayer();
    	}
    	
    }


	$(function() {
		$('#ProjectItemGrid').datagrid({ pagination: false,
			onLoadSuccess: function (data) {
				if (data.rows.length > 0) {
					//调用mergeCellsByField()合并单元格
					mergeCellsByField("ProjectItemGrid", "NAME");
					mergeCellsByField("ProjectItemGrid", "uploadName");
				}
			}
		});
	});
	
	function dosearchProjectItem(){
		$("#ProjectItemGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('ProjectItemToolbar','ProjectItemGrid');
	}
	/**
	* EasyUI DataGrid根据字段动态合并单元格
	* 参数 tableID 要合并table的id
	* 参数 colList 要合并的列,用逗号分隔(例如："name,department,office");
	*/
	function mergeCellsByField(tableID, colList) {
		var ColArray = colList.split(",");
		var tTable = $("#" + tableID);
		var TableRowCnts = tTable.datagrid("getRows").length;
		var tmpA;
		var tmpB;
		var PerTxt = "";
		var CurTxt = "";
		var alertStr = "";
		for (j = ColArray.length - 1; j >= 0; j--) {
			PerTxt = "";
			tmpA = 1;
			tmpB = 0;

			for (i = 0; i <= TableRowCnts; i++) {
				if (i == TableRowCnts) {
					CurTxt = "";
				}
				else {
					CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
				}
				if (PerTxt == CurTxt) {
					tmpA += 1;
				}
				else {
					tmpB += tmpA;
					
					tTable.datagrid("mergeCells", {
						index: i - tmpA,
						field: ColArray[j],　　//合并字段
						rowspan: tmpA,
						colspan: null
					});
					tTable.datagrid("mergeCells", { //根据ColArray[j]进行合并
						index: i - tmpA,
						field: "Ideparture",
						rowspan: tmpA,
						colspan: null
					});
				   
					tmpA = 1;
				}
				PerTxt = CurTxt;
			}
		}
	}
/**
 * 格式化
 */
function formatState(val,row){
	//var html = '<a target="_blank" style="margin-left: 5px;" href="executionController.do?goDetail&exeId='+row.EXE_ID+'&categoryId='+row.CATEGORY_ID+'">查看</a>';	
	var html = '<a target="_blank" style="margin-left: 5px;" href="executionController.do?goDetail&isFiled='+row.ISFILED+'&exeId='+row.EXE_ID+'&categoryId='+row.CATEGORY_ID+'">查看</a>';
	/* html += '<a target="_blank" style="margin-left: 5px;" href="projectWebsiteApplyController.do?getApproveItem&item_instance_code='
			+row.EXE_ID+'&project_code=${PROJECT_CODE}&access_token=access_2c90b38a6842b69701684a640f933682">详情</a>'; */
	if(val=="-1"){
		return "<font color='red'>未受理</font>";
	} else if(val=="0"){
		return "<font color='red'>草稿</font>"+html;
	} else if(val=="1"){
		return "<font color='green'>正在办理</font>"+html;
	} else if(val=="2"){
		return "<font color='blue'>已办结(正常结束)</font>"+html;
	} else if(val=="3"){
		return "<font >已办结(审核通过)</font>"+html;
	}else if(val=="4"){
		return "<font >已办结(审核不通过)</font>"+html;
	}else if(val=="5"){
		return "<font color='red'>已办结(退回)</font>"+html;
	}else if(val=="6"){
		return "<font color='black'>强制结束</font>"+html;
	}else if(val=="7"){
		return "<font color='red'>预审不通过</font>"+html;
	}
};	
function formatReCall(val,row){
	var htmlrc = "";
	if(row.STATE=="-1"||row.STATE=="0"){
		
	}else if(row.CHECK_STATUS=="0"&&'${isJyfwAll}'=="true"){
		htmlrc += "<font color='black'>已撤回待审核</font>";
	}else if('${isJyfwAll}'=="true"){
// 		htmlrc += "<a href='#' class='easyui-linkbutton' reskey='FLOW_RECALL_KEY111' iconcls='eicon-list' plain='true' onclick=\"flowRecall('"+row.PROJECT_CODE+"','"+row.ITEM_CODE+"','"+row.EXE_ID+"');\">撤回</a>";
		htmlrc += "<input type='button' value='撤回' class='eve-button' onclick=\"flowRecall('"+row.PROJECT_CODE+"','"+row.ITEM_CODE+"','"+row.EXE_ID+"');\"/>"
	}else{
		
	}
	return htmlrc
};	

function flowRecall(projectcode,itemCode,exeId){
	$.dialog.open("executionController.do?recall&projectCode="+projectcode
			+"&exeid="+exeId+"&itemCode="+itemCode, {
		title : "流程撤回",
		width : "700px",
		height : "350px",
		lock : true,
		resize : false,
	    close: function () {
	        AppUtil.gridDoSearch("ProjectItemToolbar","ProjectItemGrid");
	    }
	}, false);
};
function formatSubject(val,row){
	//获取事项编码
	var STATE = row.STATE;
	//获取事项编码
	var itemCode = row.ITEM_CODE;
	//获取流程KEY
	var defkey= row.DEF_KEY;
	if(STATE==-1 || STATE==4 || STATE==5 || STATE==6 || STATE==7){
		var href = "";
		href = '<a href="javascript:toUrl(\''+defkey+'\',\''+itemCode+'\',\'${PROJECT_CODE}\')"';
		href += '><span style="text-decoration:underline;color:green;" title="'+val+'">'+val+'</span></a>';
		return href;
	} else {
		return val;
	}
}
function formatIsKey(val,row){
	if(val=="0"){
		return "<font>否</font>";
	} else if(val=="1"){
		return "<font color='green'>是</font>";
	}
}
function toUrl(defkey,itemCode,code){
	var url = "executionController.do?goStart&defKey="+defkey+"&itemCode="+itemCode+"&PROJECT_CODE="+code;
	var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
	//var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
	$("#ProjectItemToolbar").append(ssoForm);
	//ssoForm.append(codeInput);
	ssoForm.submit();		
}

function formatUpload(val,row){
	var html = '<a href="javascript:void(0);" onclick="openXxcjFileUploadDialog(\''+row.CATEGORY_ID+'\',\''+row.NAME+'\')">';
	html += '<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>';
	html += '</a>';
	return html;
}

function openXxcjFileUploadDialog(categoryId, categoryName){
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=JBPM6_EXECUTION', {
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
					var projectCode = document.getElementById("projectCode").value;
		        	//获取文件ID
	            	var fileId = uploadedFileInfo.fileId;
	        		//获取文件名称
	        		var fileName = uploadedFileInfo.fileName;
	        		var updateUrl = "userProjectController/uploadTZXMFile.do?fileName="+encodeURI(encodeURI(fileName))+"&projectCode="
							+projectCode+"&categoryId="+categoryId+"&categoryName="+encodeURI(encodeURI(categoryName))+"&fileId="+fileId
							+"&attachType="+attachType+"&fileType=2";
					$.ajax({
						url: updateUrl,
						async: false
					});
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false">
			<div id="ProjectItemToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="Q_T.CATALOG_CODE_EQ" />
				<input type="hidden" id="projectCode" value="${PROJECT_CODE}"/>
				<!--====================结束编写隐藏域============== -->
				<form name="ProjectItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr style="height:28px;">
                           <td style="width:68px;text-align:right;">事项编码</td>
                           <td style="width:135px;"><input class="eve-input"
                               type="text" maxlength="20" style="width:128px;"
                               name="Q_T.ITEM_CODE_LIKE" /></td>
                           <td style="width:68px;text-align:right;">事项名称</td>
                           <td style="width:135px;"><input class="eve-input"
                               type="text" maxlength="20" style="width:128px;"
                               name="Q_T.ITEM_NAME_LIKE" /></td>
                           <td style="width:68px;text-align:right;">事项性质</td>
                           <td style="width:135px;"><input class="easyui-combobox"
                               style="width:128px;" name="Q_T.SXXZ_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
                        </tr>
                        <tr style="height:28px;">
                           <td style="width:68px;text-align:right;">办件类型</td>
                           <td style="width:135px;"><input class="easyui-combobox"
                               style="width:128px;" name="Q_T.SXLX_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
						<td colspan="2"><input type="button" value="查询"
							class="eve-button"
							onclick="dosearchProjectItem();" />
							<input type="button" value="重置" class="eve-button"
							onclick="AppUtil.gridSearchReset('ProjectItemForm')" /></td>
					</tr>
				</table>
			    </form>
			</div>
			<!-- =========================查询面板结束========================= -->
	
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="ProjectItemGrid" fitColumns="true" toolbar="#ProjectItemToolbar"
				method="post" idField="ITEM_ID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="true"
				url="projectApplyController.do?publishdatagrid&Q_T.FWSXMXLB_NEQ=2&Q_C.ID_EQ=${FLOW_CATE_ID}&PROJECT_CODE=${PROJECT_CODE}&Q_L.IS_OFF_SHELVES_EQ=0">
				<thead>
					<tr>
	                    <th data-options="field:'DEF_KEY',hidden:true" width="80">DEF_KEY</th>
	                    <th data-options="field:'ITEM_ID',hidden:true" width="80">ITEM_ID</th>
	                    <th data-options="field:'CHECK_STATUS',hidden:true" width="80">CHECK_STATUS</th>
	                    <th data-options="field:'NAME',align:'left'" width="11%">集成流程名</th>
	                    <th data-options="field:'ITEM_CODE',align:'left'" width="11%">事项编码</th>
	                    <th data-options="field:'ITEM_NAME',align:'left'" width="29%" formatter="formatSubject">事项名称</th>
	                    <th data-options="field:'SXXZ',align:'left'" width="7%" >事项性质</th>
<!-- 	                    <th data-options="field:'SXLX',align:'left'" width="5%" >办件类型</th> -->
	                    <th data-options="field:'DEPART_NAME',align:'left'" width="10%" >所属部门</th>
	                    <th data-options="field:'IS_KEY_ITEM',align:'center'" width="5%" formatter="formatIsKey">是否必办</th>
	                    <th data-options="field:'STATE',align:'left'" width="8%" formatter="formatState">状态</th>
	                    <th data-options="field:'PROJECT_CODE',align:'left'" width="7%" formatter="formatReCall">操作</th>
	                    <th data-options="field:'uploadName',align:'center'" width="7%" formatter="formatUpload">阶段批复上传</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: right;">
				 <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>
</html>
