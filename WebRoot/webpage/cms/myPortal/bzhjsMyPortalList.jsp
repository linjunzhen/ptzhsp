<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
/**
 * 移除树形hover工具按钮
 */
function removeBzhjsTreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
 * 点击树形节点触发的事件
 */
function onBzhjsMoudleTreeClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		if(treeNode.isParent){
			if(treeNode.id=="663"){
				$("#bzhjsContentToolbar input[name='MODULE_ID']").val("");
				$("#bzhjsContentToolbar input[name='Q_T.MODULE_ID_=']").val("");
				$("#bzhjsContentToolbar input[name='PATH']").val("");
				$("#bzhjsContentToolbar input[name='Q_M.PATH_LIKE']").val("");
			}else{//点击第二层级
				$("#bzhjsContentToolbar input[name='PATH']").val("0.663."+treeNode.id);
				$("#bzhjsContentToolbar input[name='Q_M.PATH_LIKE']").val("0.663."+treeNode.id);
				$("#bzhjsContentToolbar input[name='MODULE_ID']").val("");
				$("#bzhjsContentToolbar input[name='Q_T.MODULE_ID_=']").val("");
			}
		}else{
			$("#bzhjsContentToolbar input[name='MODULE_ID']").val(treeNode.id);
			$("#bzhjsContentToolbar input[name='Q_T.MODULE_ID_=']").val(treeNode.id);
			$("#bzhjsContentToolbar input[name='PATH']").val("");
			$("#bzhjsContentToolbar input[name='Q_M.PATH_LIKE']").val("");
		}
		/* if(treeNode.id=="663"){
			$("#bzhjsContentToolbar input[name='MODULE_ID']").val("");
			$("#bzhjsContentToolbar input[name='Q_T.MODULE_ID_=']").val("");
		}else{
			$("#bzhjsContentToolbar input[name='MODULE_ID']").val(treeNode.id);
			$("#bzhjsContentToolbar input[name='Q_T.MODULE_ID_=']").val(treeNode.id);
		} */
		AppUtil.gridDoSearch("bzhjsContentToolbar","bzhjsContentGrid");
	}else if(treeNode.id=="663"){
		$("#bzhjsContentToolbar input[name='MODULE_ID']").val("");
		$("#bzhjsContentToolbar input[name='Q_T.MODULE_ID_=']").val("");
		$("#bzhjsContentToolbar input[name='PATH']").val("");
		$("#bzhjsContentToolbar input[name='Q_M.PATH_LIKE']").val("");
		AppUtil.gridDoSearch("bzhjsContentToolbar","bzhjsContentGrid");
	}
}

/**
 * 重置查询条件
 */
function bzhjsResetSearch(){
	AppUtil.gridSearchReset("moduleForm");
}

/**
 * 预览文章
 */
function previewBzhjsContent(){
	var CONTENT_ID = AppUtil.getEditDataGridRecord("bzhjsContentGrid");
	window.open("contentController/view.do?contentId="+CONTENT_ID, "_blank", 
			"menubar=yes, status=yes, location=yes, scrollbars=yes, resizable=yes, alwaysRaised=yes, fullscreen=yes");
};
/*
 * 文章编辑回调父类方法
 */
function infoListSearch(resultJson){
    if(resultJson.success){
    	art.dialog({
    		content:resultJson.msg,
    		icon:"succeed",
    		time:3,
			ok: true
    	});
    }
	AppUtil.gridDoSearch('bzhjsContentToolbar','bzhjsContentGrid');
}

$(document).ready(function(){
var BzhjsTreeSetting = {
	edit: {
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	view : {
		selectedMulti: false,
		removeHoverDom : removeBzhjsTreeHoverDom
	},
	callback : {
		onClick : onBzhjsMoudleTreeClick
	},
	async : {
		enable : true,
		url : "moduleController.do?bzhjsTree",
		otherParam : {
			"tableName" : "T_CMS_ARTICLE_MODULE",
			"idAndNameCol" : "MODULE_ID,MODULE_NAME",
			"targetCols" : "PARENT_ID,PATH",
			"rootParentId" : "663",
			"isShowRoot" : "true",
			"rootName" : "标准化建设"
		}
	}
};
$.fn.zTree.init($("#bzhjsModuleTree"), BzhjsTreeSetting);
	AppUtil.initAuthorityRes("bzhjsContentToolbar");
});
//点标题进入到详情页
function formatTitle(val,row){
	//获取文章ID
	var contentId = row.CONTENT_ID;
	var href = "<a href='";
	href += " contentController/view.do?contentId="+contentId;
	href += "' target='_blank'><span style='text-decoration:none;color:#0368ff;'>"+val+"</span></a>";
    return href;
}
function formatIsTop(val,row){
	if(val=="1"){
		return "<font color='red'>是</font>";
	}else if(val=="0"){
		return "<font color='green'>否</font>";
	}
};
function bzhjsListSearch(){
	AppUtil.gridDoSearch('bzhjsContentToolbar','bzhjsContentGrid');
};
</script>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:false" 
		style="width:250px;" >
		<ul id="bzhjsModuleTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="bzhjsContentToolbar">
		    <%--====================开始编写隐藏域============== --%>
		    <input type="hidden" name="Q_T.MODULE_ID_=" value=""/>
		    <input type="hidden" name="MODULE_ID"/>
		    
		    <input type="hidden" name="Q_M.PATH_LIKE" value=""/>
		    <input type="hidden" name="PATH"/>
		    
			<input type="hidden" name="MODULEIDS"  />
		    <%--====================结束编写隐藏域============== --%> 
			<form action="#" name="moduleForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tr>
						<td style="width:68px;text-align:right;">文章标题：</td>
						<td style="width:135px;" ><input class="eve-input" type="text"
							 name="Q_T.CONTENT_TITLE_LIKE" style="width:128px;"></td>
						<td><input type="button" value="查询" class="eve-button" onclick="bzhjsListSearch();">
							<input type="button" value="重置" class="eve-button" onclick="bzhjsResetSearch();"></td>
					</tr>
				</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="bzhjsContentGrid" fitColumns="true" toolbar="#bzhjsContentToolbar"
			method="post" idField="CONTENT_ID" checkOnSelect="true" nowrap="false"
			selectOnCheck="true"  fit="true" border="false"
			data-options="onLoadSuccess:function(){
			     var CONTENT_ID = $('#bzhjsContentToolbar input[name=CONTENT_ID]').val();
			     if(CONTENT_ID&&CONTENT_ID!='0'){
			          $(this).datagrid('enableDnd');
			     }
			}"
			url="moduleController.do?bzhjsDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CONTENT_ID',hidden:true">文章编号</th>
					<th data-options="field:'CONTENT_TITLE',align:'left'" width="60%" formatter="formatTitle">文章标题</th>
					<th data-options="field:'RELEASE_TIME',align:'left'" width="15%">发布时间</th>
					<th data-options="field:'CHECKER',align:'left'" width="15%">审核人</th>
					<th data-options="field:'ISTOP',align:'left'" width="10%" formatter="formatIsTop">置顶</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>