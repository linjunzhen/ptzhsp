<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function selectImplDepartBillOfRightUndertake(){
		var departId = $("#implDepartIdUndertake").val();	
        parent.$.dialog.open("departmentController/parentSelector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=", {
            title : "实施部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("#implDepartIdUndertake").val(selectDepInfo.departIds);
                    $("#implDepartnameUndertake").val(selectDepInfo.departNames);
                    
                    art.dialog.removeData("selectDepInfo");
                }
            }
        }, false);
	}

	function showBillOfRightUndertakeDetail(){
		var entityId = AppUtil.getEditDataGridRecord("BillOfRightUndertakeGrid");
		if (entityId) {
			$.dialog.open("billOfRightController.do?draftDetail&operType=cancelView&entityId="+ entityId, {
				title : "权责清单信息",
				width : "100%",
				height : "100%",
				left : "0%",
				top : "0%",
				fixed : true,
				lock : true,
				resize : false,
				close : function() {
					$("#BillOfRightUndertakeGrid").datagrid('reload');
				}
			}, false);
		}
	}
		
	function formateStatus(val, row) {
		if (val == "1") {
			return "<font color='blue'><b>已发布</b></font>";
		}
	}
	$(document).ready(function() {
		AppUtil.initAuthorityRes("BillOfRightUndertakeToolbar");
	});
	
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
	$("#BillOfRightUndertakeGrid").datagrid({
	    onLoadSuccess : function () {
	        $(this).datagrid("fixRownumber");
	    }
	});

</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BillOfRightUndertakeToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton"
								reskey="DETAIL_BillOfRight" iconcls="eicon-file-text-o"
								plain="true" onclick="showBillOfRightUndertakeDetail();">详细信息</a>
							<a class="easyui-linkbutton" style=" border-width: 0;height: 0;width: 0;"></a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BillOfRightUndertakeForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">实施部门</td>
							<td style="width:135px;">
								<input type="text" name="Q_T.IMPL_DEPART_ID_=" id="implDepartIdUndertake" style="display: none;"/>
								<input class="eve-input" readonly="readonly" onclick="selectImplDepartBillOfRightUndertake()"
								type="text" maxlength="20" style="width:128px;"
								id="implDepartnameUndertake" /></td>
							<td style="width:68px;text-align:right;">项目名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.RIGHT_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">子项名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.SUBITEM_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">权责类别</td>
							<td style="width:135px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_T.RIGHT_TYPE_="
								data-options="
url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=qzlb',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BillOfRightUndertakeToolbar','BillOfRightUndertakeGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BillOfRightUndertakeForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BillOfRightUndertakeGrid" fitcolumns="true"
			toolbar="#BillOfRightUndertakeToolbar" method="post" idfield="RIGHT_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false" nowrap="false"
			url="billOfRightController.do?datagrid&Q_T.STATUS_EQ=1&Q_T.RIGHT_SOURCE_IN=04,06">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RIGHT_ID',hidden:true">RIGHT_ID</th>
					<th data-options="field:'RIGHT_NAME',align:'left'" width="30%">项目名称</th>
					<th data-options="field:'SUBITEM_NAME',align:'left'" width="30%">子项名称</th>
					<th data-options="field:'RIGHT_TYPE',align:'left'" width="10%">权责类别</th>
					<th data-options="field:'IMPL_DEPART_NAME',align:'left'" width="15%">实施部门</th>
					<th data-options="field:'STATUS',align:'left'" width="10%" formatter="formateStatus">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
