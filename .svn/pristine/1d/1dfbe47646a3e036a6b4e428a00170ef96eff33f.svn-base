<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("#psnUnitHistoryForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#psnUnitHistoryGrid').datagrid({
				onLoadSuccess: function(data){
					if(data.total==0){
						var dc = $(this).data('datagrid').dc;
				        var header2Row = dc.header2.find('tr.datagrid-header-row');
				        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
			        }
				}
			});	
      });
    
     //查询
     function psnUnitHistorySearch(){
            var validateResult =$("#psnUnitHistoryForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#psnUnitHistoryGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('psnUnitHistoryToolbar','psnUnitHistoryGrid');
            $('#psnUnitHistoryGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#psnUnitHistoryGrid").datagrid("getRows");
                        if(rows.length==0){
                            parent.art.dialog({
                                content: "无匹配数据返回，查询记录为空！",
                                icon:"warning",
                                ok: true
                            });
                            count ++;
                        }
                    }
                }
            });
        }
        
		/**
		 * 删除列表记录
		 */
		function deletepsnUnitHistoryGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"psnUnitHistoryGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editpsnUnitHistoryGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("psnUnitHistoryGrid");
			if (entityId) {
				showExecutionWindow(entityId);
			}
		}
		/**
		 * 显示对话框
		 */
		function showExecutionWindow(entityId) {
			$.dialog.open("executionController.do?info&entityId=" + entityId, {
				title : "流程实例信息",
				width : "600px",
				height : "400px",
				lock : true,
				resize : false
			}, false);
		};

	
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="psnUnitHistoryToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="psnUnitHistoryForm" id="psnUnitHistoryForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>公民身份号码</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aac002" /></td>
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>姓名</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aac003" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="psnUnitHistorySearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('psnUnitHistoryForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="psnUnitHistoryGrid" fitcolumns="true" toolbar="#psnUnitHistoryToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?psnUnitHistoryGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aab001',align:'left'" width="12%">单位ID</th>
					<th data-options="field:'aab004',align:'left'" width="15%">单位名称</th>
					<th data-options="field:'aab019',align:'left',formatter:formatDwlx" width="11%">单位类型</th>
					<th data-options="field:'aab999',align:'left'" width="15%">单位管理码</th>
					<th data-options="field:'aac066',align:'left',formatter:formatCbsf" width="11%">参保身份</th>
					<th data-options="field:'minaae003',align:'left'" width="10%">起始时间</th>
					<th data-options="field:'maxaae003',align:'left'" width="15%" >截止时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
