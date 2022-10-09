<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	/**
	* 查看服务事项详细信息
	*/
	function ckServiceItemXzywGridRecord() {
	 var entityId = AppUtil.getEditDataGridRecord("ServiceItemXzywGrid");
	 if (entityId) {
	     showServiceItemXzywWindow(entityId);
	 }
	}
	
	/**
	* 显示服务事项信息对话框
	*/
	function showServiceItemXzywWindow(entityId) {
	 $.dialog.open("serviceItemController.do?detailedInfo&entityId=" + entityId, {
	     title : "详细信息",
	     width: "100%",
	     height: "100%",
	     left: "0%",
	     top: "0%",
	     fixed: true,
	     lock : true,
	     resize : false,
	     close: function () {
	         //AppUtil.gridDoSearch("ServiceItemXzywToolbar","ServiceItemXzywGrid");
	     }
	 }, false);
	};
	/**
	 * 点击树形节点触发的事件
	 */
	function onCatalogTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN"&&treeNode.id!=0) {
			$("#ServiceItemXzywToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(treeNode.id);
			AppUtil.gridDoSearch("ServiceItemXzywToolbar", "ServiceItemXzywGrid");
		}else if(event.target.tagName == "SPAN"&&treeNode.id==0){
			$("#ServiceItemXzywToolbar input[name='Q_T.CATALOG_CODE_EQ']").val("");
            AppUtil.gridDoSearch("ServiceItemXzywToolbar", "ServiceItemXzywGrid");
		}
	}
	function onDepartTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN"&&treeNode.id!=0) {
			$("#ServiceItemXzywToolbar input[name='Q_T.DEPART_ID_EQ']").val(treeNode.id);
			AppUtil.gridDoSearch("ServiceItemXzywToolbar", "ServiceItemXzywGrid");
		}else if(event.target.tagName == "SPAN"&&treeNode.id==0){
			$("#ServiceItemXzywToolbar input[name='Q_T.DEPART_ID_EQ']").val("");
            AppUtil.gridDoSearch("ServiceItemXzywToolbar", "ServiceItemXzywGrid");
		}
	}
	
	function addNewBusiness() {
		var itemId = AppUtil.getEditDataGridRecord("ServiceItemXzywGrid");
		if (itemId) {
			var rowData = $("#ServiceItemXzywGrid").datagrid("getChecked")[0];
			var defKey = rowData.DEF_KEY;
			var itemCode = rowData.ITEM_CODE;
			toUrl("executionController.do?goStart&defKey="+defKey+"&itemCode="+itemCode);
		}
	}
	function toUrl(url){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		$("#ServiceItemXzywToolbar").append(ssoForm);
		ssoForm.submit();		
	}
	$(document).ready(function() {
		/* var zxywcatalogTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onCatalogTreeClick
			},
			async : {
				enable : true,
				url : "catalogController.do?tree"
			}
		};
		$.fn.zTree.init($("#xzywcatalogTree"), zxywcatalogTreeSetting); */
		var xzywDepartTreeSetting = {
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : onDepartTreeClick
			},
			async : {
				enable : true,
				url : "departmentController.do?treeAuth"
			}
		};
		$.fn.zTree.init($("#xzywDepartTree"), xzywDepartTreeSetting);
		
		$("#ServiceItemXzywGrid").datagrid({
	        onDblClickRow: function(index, row){
	        	showServiceItemXzywWindow(row.ITEM_ID);
	        }
	    });
	});
	
	function gbxzywUrl(){
		var  cname = $("#xzywmlmc").val();
		var zTree = $.fn.zTree.getZTreeObj("xzywcatalogTree");
		zTree.setting.async.url = "catalogController.do?tree";
        zTree.setting.async.otherParam = {
            "Q_SC.CATALOG_NAME_LIKE" : cname,
        }
		zTree.reAsyncChildNodes(null, "refresh");
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<!-- <div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">			     
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;服务目录树
						</div>
					</div>
				</div>
			</div>
		</div>
		目录名称：<input class="eve-input" style="width:90px;" name="mlmc" id="xzywmlmc"/>
		<input type="button" value="查询" class="eve-button" onclick="gbxzywUrl();" />
		<ul id="xzywcatalogTree" class="ztree"></ul>
	</div> -->
	<div data-options="region:'west',split:false"
		style="width:250px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;组织机构树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="xzywDepartTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">
		<div id="ServiceItemXzywToolbar">
			<!--====================开始编写隐藏域============== -->
			<!-- <input type="hidden" name="Q_T.CATALOG_CODE_EQ" /> -->
			<input type="hidden" name="Q_T.DEPART_ID_EQ" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							 <a href="#" class="easyui-linkbutton" resKey="ADD_YW" iconCls="eicon-plus"
                                plain="true" onclick="addNewBusiness();">新建业务</a>
                                <a href="#" class="easyui-linkbutton" resKey="CK_YW" iconCls="eicon-eye"
                                plain="true" onclick="ckServiceItemXzywGridRecord();">详细信息</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ServiceItemForm">
                <table class="dddl-contentBorder dddl_table"
                    style="background-repeat:repeat;width:100%;border-collapse:collapse;">
                    <tbody>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">事项编码</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_T.ITEM_CODE_LIKE" /></td>
                            <td style="width:68px;text-align:right;">事项名称</td>
                            <td style="width:135px;"><input class="eve-input"
                                type="text" maxlength="20" style="width:128px;"
                                name="Q_T.ITEM_NAME_LIKE" /></td>
                            <c:if test="${cyjbmlcx != null && cyjbmlcx == 'true'}">
                                <td style="width:68px;text-align:right;">目录名称</td>
                                <td style="width:135px;"><input class="eve-input"
                                                                type="text" maxlength="20" style="width:128px;"
                                                                name="Q_C.CATALOG_NAME_LIKE" /></td>
                            </c:if>
                            <td style="width:68px;text-align:right;">事项性质</td>
                            <td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
                                style="width:128px;" name="Q_T.SXXZ_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
                            <td colspan="2"></td>
                        </tr>
                        <tr style="height:28px;">
                            <td style="width:68px;text-align:right;">办件类型</td>
                            <td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
                                style="width:128px;" name="Q_T.SXLX_="
                                data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
                            </td>
                            <td colspan="4"><input type="button" value="查询"
                                class="eve-button"
                                onclick="AppUtil.gridDoSearch('ServiceItemXzywToolbar','ServiceItemXzywGrid')" />
                                <input type="button" value="重置" class="eve-button"
                                onclick="AppUtil.gridSearchReset('ServiceItemForm')" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
            id="ServiceItemXzywGrid" fitcolumns="true" toolbar="#ServiceItemXzywToolbar"
            method="post" idfield="ITEM_ID" checkonselect="false"
            selectoncheck="false" fit="true" border="false" nowrap="false"
            url="serviceItemController.do?publishdatagrid&Q_T.FWSXMXLB_NEQ=2">
            <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th data-options="field:'DEF_KEY',hidden:true">DEF_KEY</th>
                    <th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
                    <th data-options="field:'ITEM_CODE',align:'left'" width="20%">事项编码</th>
                    <th data-options="field:'ITEM_NAME',align:'left'" width="35%">事项名称</th>
                    <c:if test="${cyjbmlcx != null && cyjbmlcx == 'true'}"><th data-options="field:'CATALOG_NAME',align:'left'" width="20%" >目录名称</th></c:if>
                    <th data-options="field:'SXXZ',align:'left'" width="10%" >事项性质</th>
                    <th data-options="field:'SXLX',align:'left'" width="10%" >办件类型</th>
                    <th data-options="field:'DEPART_NAME',align:'left'" width="20%" >所属部门</th>
                </tr>
            </thead>
        </table>

	</div>
</div>



