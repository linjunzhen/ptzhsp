<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除关联事项
	 */
	function deleteRelatedItemGridRecord() {
		AppUtil.deleteDataGridRecord("commercialSetController.do?multiDelRelatedItem",
				"RelatedItemGrid");
	};
	
	function showRelatedItemGridRecord(){
    	parent.$.dialog.open("serviceItemController/selector.do?&allowCount=10&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "事项选择器",
            width:"80%",
            height:"100%",
            lock: true,
            resize:false,
            close: function () {
    			var selectItemInfo = art.dialog.data("selectItemInfo");
    			if(selectItemInfo){
    				var itemCodes = selectItemInfo.itemCodes;
    				saveSelectItems(itemCodes);
                    art.dialog.removeData("selectItemInfo");
    			}
    		}
    	}, false);
	}
	
	function saveSelectItems(itemCodes){
		AppUtil.ajaxProgress({
			url : "commercialSetController.do?saveRelatedItem",
			params : {
				"itemCodes" : itemCodes
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("RelatedItemToolbar", "RelatedItemGrid");
			}
		});
	}
    
    function showSelectDeparts(){
        var departId = $("input[name='Q_d.DEPART_ID_EQ']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&noAuth=true&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "所属部门",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='Q_d.DEPART_ID_EQ']").val(selectDepInfo.departIds);
                    $("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
                    art.dialog.removeData("selectDepInfo");
                }
                
            }
        }, false);
    }
    
    function editRelatedItemMater(){
	    var entityId = AppUtil.getEditDataGridRecord("RelatedItemGrid");
    	if(entityId){
	    	$.dialog.open("commercialSetController.do?materSetView&entityId=" + entityId, {
				title : "1+N事项材料配置",
				width: "100%",
			    height: "100%",
			    fixed: true,
				lock : true,
				resize : false
			}, false);
    	}
    }
    
    function refreshRelatedItem(){
    	var entityId = AppUtil.getEditDataGridRecord("RelatedItemGrid");
    	if(entityId){
	    	AppUtil.ajaxProgress({
				url : "commercialSetController.do?refreshRelatedItem",
				params : {
					relatedId : entityId
				},
				callback : function(resultJson) {
					if(resultJson.success){
					  	parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
						$("#RelatedItemGrid").datagrid("reload");
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
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="RelatedItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Related"
								iconcls="eicon-plus" plain="true"
								onclick="showRelatedItemGridRecord();">新增</a>
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_Related"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteRelatedItemGridRecord();">删除</a>
							<a href="#"
								class="easyui-linkbutton" reskey="EDT_Related"
								iconcls="eicon-pencil" plain="true"
								onclick="editRelatedItemMater();">材料编辑</a>
							<a href="#"
								class="easyui-linkbutton" reskey="REFRESH_Related"
								iconcls="eicon-refresh" plain="true"
								onclick="refreshRelatedItem();">更新事项信息</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="RelatedItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:128px;"
									name="Q_t.ITEM_NAME_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">所属部门</td>
							<td style="width:135px;">
								<input type="hidden" name="Q_d.DEPART_ID_EQ" id="departId">
								<input type="text" style="width:128px;float:left;" class="eve-input"
									name="DEPART_NAME" readonly="readonly" onclick="showSelectDeparts();"/>
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('RelatedItemToolbar','RelatedItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('RelatedItemForm');$('#departId').val('')" /></td>
						</tr>
					</tbody>
				</table>
			</form>				
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="RelatedItemGrid" fitcolumns="false" nowrap="false"
			toolbar="#RelatedItemToolbar" method="post" idfield="RELATED_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			url="commercialSetController.do?relatedItemData">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RELATED_ID',hidden:true">RELATED_ID</th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="20%">事项编码</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="45%" >事项名称</th>
					<!-- <th data-options="field:'SSBMBM',align:'left'" width="120" >所属部门编码</th> -->
					<th data-options="field:'DEPART_NAME',align:'left'" width="30%" >所属部门</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
