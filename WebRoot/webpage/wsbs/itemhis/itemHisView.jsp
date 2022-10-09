<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

	/**
	 * 显示服务事项信息对话框
	 */
	function viewhisItemGridRecord() {
        var rows = $("#hisItemGrid").datagrid("getChecked");
        if(!(rows != null && typeof (rows) != 'undefined' && rows.length != 0)){
            art.dialog({
                content: "请选择需要需要查看的服务事项!",
                icon:"warning",
                ok: true
            });
            return;
        }else if (rows.length>1) {
            art.dialog({
                content: "只能选择一条操作记录!",
                icon:"warning",
                ok: true
            });
            return;
		}else{
			var entityId = rows[0].ITEM_ID;
			var version = rows[0].C_VERSION;
			$.dialog.open("departServiceItemController.do?hisItemInfo&entityId="
					+ entityId + "&version=" + version, {
				title : "服务事项信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false
			}, false);
		}
	}

	$(document).ready(function() {
		AppUtil.initAuthorityRes("hisItemToolbar");
	});

	function formatFwsxzt(val, row) {
		if (val == "-1") {
			return "<font color='red'><b>草稿</b></font>";
		} else if (val == "1") {
			return "<font color='blue'><b>发布</b></font>";
		} else if (val == "2" || val == "5") {
			return "<font color='green'><b>审核中</b></font>";
		} else if (val == "3") {
			return "<font color='black'><b>取消</b></font>";
		} else if (val == "4") {
			return "<font color='red'><b>退回</b></font>";
		}
	};

	function formatIsFromSwb (val, row) {
		if (val == "1") {
			return "<b>从省网办同步</b>";
		} else {
			return "<b> </b>";
		}
	};
	
	$.extend($.fn.datagrid.methods, {  
	    fixRownumber : function (jq) {  
	        return jq.each(function () {  
	            var panel = $(this).datagrid("getPanel");  
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
	            clone.css({  
	                "position" : "absolute",  
	                left : -1000  
	            }).appendTo("body");  
	            var width = clone.width("auto").width();  
	            if (width > 25) {  
	                //多加5个像素,保持一点边距  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
	                $(this).datagrid("resize");  
	                //一些清理工作  
	                clone.remove();  
	                clone = null;  
	            } else {  
	                //还原成默认状态  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
	            }  
	        });  
	    }  
	});
	$("#hisItemGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="hisItemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
								<a href="#"
                                class="easyui-linkbutton" reskey="View_hisItem"
                                iconcls="eicon-file-text-o" plain="true"
                                onclick="viewhisItemGridRecord();">详细信息</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="departServiceItemForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">唯一码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_CODE_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.ITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">事项性质</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXXZ_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemXz',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">办件类型</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.SXLX_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=ServiceItemType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('hisItemToolbar','hisItemGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('departServiceItemForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="hisItemGrid" fitcolumns="true" toolbar="#hisItemToolbar"
			method="post" idfield="ITEM_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="departServiceItemController.do?hisDataGrid&Q_T.EFFECT_STATUS_EQ=1">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ITEM_ID',hidden:true">ITEM_ID</th>
					<th data-options="field:'ITEM_CODE',align:'left'" width="13%">唯一码（事项编码）</th>
					<th data-options="field:'C_VERSION',align:'left'" width="13%" >版本号</th>
					<th data-options="field:'ITEM_NAME',align:'left'" width="27%">事项名称</th>
					<th data-options="field:'SXXZ',align:'left'" width="10%" >事项性质</th>
					<!-- <th data-options="field:'OPERATE_TIME',align:'left'" width="150" >编辑时间</th> -->
					<th data-options="field:'SXLX',align:'left'" width="8%" >办件类型</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="15%" >所属部门</th>
					<th data-options="field:'CNQXGZR',align:'left'" width="10%">承诺时限工作日</th>
					<!-- <th data-options="field:'IS_FROM_SWB',align:'left'" width="90" formatter="formatIsFromSwb">事项来源</th> -->
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
