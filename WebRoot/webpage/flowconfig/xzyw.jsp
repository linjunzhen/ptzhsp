
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,icheck,ztree"></eve:resources>

<head>

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
        $.dialog.open("serviceItemController.do?detailedInfo&entityId="
                + entityId, {
            title : "详细信息",
            width : "100%",
            height : "100%",
            left : "0%",
            top : "0%",
            fixed : true,
            lock : true,
            resize : false,
            close : function() {
                //AppUtil.gridDoSearch("ServiceItemXzywToolbar","ServiceItemXzywGrid");
            }
        }, false);
    };
    /**
     * 点击树形节点触发的事件
     */
    function onCatalogTreeClick(event, treeId, treeNode, clickFlag) {
        if (event.target.tagName == "SPAN" && treeNode.id != 0) {
            $("#ServiceItemXzywToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(
                    treeNode.id);
            AppUtil.gridDoSearch("ServiceItemXzywToolbar",
                    "ServiceItemXzywGrid");
        } else if (event.target.tagName == "SPAN" && treeNode.id == 0) {
            $("#ServiceItemXzywToolbar input[name='Q_T.CATALOG_CODE_EQ']").val(
                    "");
            AppUtil.gridDoSearch("ServiceItemXzywToolbar",
                    "ServiceItemXzywGrid");
        }
    }

    function addNewBusiness() {
        var itemId = AppUtil.getEditDataGridRecord("ServiceItemXzywGrid");
        if (itemId) {
            var rowData = $("#ServiceItemXzywGrid").datagrid("getChecked")[0];
            var defKey = rowData.DEF_KEY;
            var itemCode = rowData.ITEM_CODE;
            toUrl("executionController.do?goStart&defKey=" + defKey
                    + "&itemCode=" + itemCode);
        }
    }
    function toUrl(url) {
        var ssoForm = $("<form action='"+url+"' method='post' target='_blank'></form>");
        $("#ServiceItemXzywToolbar").append(ssoForm);
        ssoForm.submit();
    }
    $(document).ready(function() {
        var zxywcatalogTreeSetting = {
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
        $.fn.zTree.init($("#xzywcatalogTree"), zxywcatalogTreeSetting);

        $("#ServiceItemXzywGrid").datagrid({
            onDblClickRow : function(index, row) {
                showServiceItemXzywWindow(row.ITEM_ID);
            }
        });
    });

    function gbxzywUrl() {
        var cname = $("#xzywmlmc").val();
        var zTree = $.fn.zTree.getZTreeObj("xzywcatalogTree");
        zTree.setting.async.url = "catalogController.do?tree";
        zTree.setting.async.otherParam = {
            "Q_SC.CATALOG_NAME_LIKE" : cname,
        }
        zTree.reAsyncChildNodes(null, "refresh");
    }
    
    function doChoose(){
        var cate_id='${cate_id}';
		var $dataGrid = $("#ServiceItemXzywGrid");

        var rowsData = $dataGrid.datagrid('getChecked');
		var selectColNames = "";
		var selectColIds = "";
		for ( var i = 0; i < rowsData.length; i++) {
			if(i>0){
				selectColNames+=",";
				selectColIds+=",";
			}
			selectColNames+=eval('rowsData[i].ITEM_NAME');
			selectColIds+=eval('rowsData[i].ITEM_ID');
		}
		
		$.post("flowConfigLinkController.do?checkFlow",{
			    "Q_T.CATE_ID_EQ":cate_id,
			    "Q_T.ITEM_ID_EQ":selectColIds
		    }, function(responseText, status, xhr) {
		    	var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
				    var iframes=parent.document.getElementsByTagName("iframe");
					var iframe = iframes[1];
					iframe.contentWindow.document.getElementById("ITEM_NAME").value=selectColNames;
					iframe.contentWindow.document.getElementById("ITEM_ID").value=selectColIds;
					AppUtil.closeLayer();
				}else{
// 					$dataGrid.datagrid('reload');
					art.dialog({
						content: "该流程已被选择，请选择其他流程",
						icon:"error",
					    ok: true
					});
				}
		});
		
		
    }
</script>
</head>
<body style="margin: 0px; background-color: #f7f7f7;">
	<div class="easyui-layout" fit="true">
		<div data-options="region:'west',split:false" style="width: 255px;">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111; top: 0px; left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color: #005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top: 7px; float: left;" />&nbsp;服务目录树
							</div>
						</div>
					</div>
				</div>
			</div>
			目录名称：<input class="eve-input" style="width: 90px;" name="mlmc"
				id="xzywmlmc" /> <input type="button" value="查询" class="eve-button"
				onclick="gbxzywUrl();" />
			<ul id="xzywcatalogTree" class="ztree"></ul>
		</div>
		<div data-options="region:'center',split:false">
			<div id="ServiceItemXzywToolbar">
				<!--====================开始编写隐藏域============== -->
				<input type="hidden" name="Q_T.CATALOG_CODE_EQ" />
				<!--====================结束编写隐藏域============== -->
				<form action="#" name="ServiceItemForm">
					<table class="dddl-contentBorder dddl_table"
						style="background-repeat: repeat; width: 100%; border-collapse: collapse;">
						<tbody>
							<tr style="height: 28px;">
								<td style="width: 68px; text-align: right;">事项编码</td>
								<td style="width: 135px;"><input class="eve-input"
									type="text" maxlength="20" style="width: 128px;"
									name="Q_T.ITEM_CODE_LIKE" /></td>
								<td style="width: 68px; text-align: right;">事项名称</td>
								<td style="width: 135px;"><input class="eve-input"
									type="text" maxlength="20" style="width: 128px;"
									name="Q_T.ITEM_NAME_LIKE" /></td>
								<td style="width: 68px; text-align: right;">事项性质</td>
								<td style="width: 135px;"><input class="easyui-combobox"
									style="width: 128px;" name="Q_T.SXXZ_="
									data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
								</td>
								<td colspan="2"></td>
							</tr>
							<tr style="height: 28px;">
								<td style="width: 68px; text-align: right;">办件类型</td>
								<td style="width: 135px;"><input class="easyui-combobox"
									style="width: 128px;" name="Q_T.SXLX_="
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
			<table class="easyui-datagrid" rownumbers="true" pagination="true"
				id="ServiceItemXzywGrid" fitcolumns="true"
				toolbar="#ServiceItemXzywToolbar" fitcolumns="true" method="post"
				idfield="YW_ID" fit="true" singleSelect="true"
				border="false"
				url="serviceItemController/allServiceItemdatagrid.do?Q_T.FWSXMXLB_NEQ=2">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'DEF_KEY',hidden:true" width="80">DEF_KEY</th>
						<th data-options="field:'ITEM_ID',hidden:true" width="80">ITEM_ID</th>
						<th data-options="field:'ITEM_CODE',align:'left'" width="100">事项编码</th>
						<th data-options="field:'ITEM_NAME',align:'left'" width="250">事项名称</th>
						<th data-options="field:'SXXZ',align:'left'" width="100">事项性质</th>
						<th data-options="field:'SXLX',align:'left'" width="100">办件类型</th>
						<th data-options="field:'DEPART_NAME',align:'left'" width="150">所属部门</th>
					</tr>
				</thead>
			</table>

		</div>
		<div data-options="region:'south',split:false" style="height: 46px;">
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="doChoose();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>
	
	
</body>


