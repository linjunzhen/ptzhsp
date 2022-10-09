<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("#psnInfoHistoryForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#psnInfoHistoryGrid').datagrid({
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
     function psnInfoHistorySearch(){
            var validateResult =$("#psnInfoHistoryForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#psnInfoHistoryGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('psnInfoHistoryToolbar','psnInfoHistoryGrid');
            $('#psnInfoHistoryGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#psnInfoHistoryGrid").datagrid("getRows");
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
		function deletepsnInfoHistoryGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"psnInfoHistoryGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editpsnInfoHistoryGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("psnInfoHistoryGrid");
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
		<div id="psnInfoHistoryToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="psnInfoHistoryForm" id="psnInfoHistoryForm">
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
								onclick="psnInfoHistorySearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('psnInfoHistoryForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="psnInfoHistoryGrid" fitcolumns="true" toolbar="#psnInfoHistoryToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?psnInfoHistoryGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aaa121',align:'left'" width="12%">业务类型</th>
					<th data-options="field:'aac001',align:'left'" width="15%">人员ID</th>
					<th data-options="field:'aae011',align:'left'" width="11%">经办人</th>
					<th data-options="field:'aae013',align:'left'" width="15%">备注</th>
					<th data-options="field:'aae016',align:'left'" width="11%">审核标记</th>
					<th data-options="field:'aae035',align:'left'" width="10%">变更时间</th>
					<th data-options="field:'aae122',align:'left'" width="15%" >变更项目</th>
					<th data-options="field:'aae123',align:'left'" width="15%" >变更前信息</th>
					<th data-options="field:'aae124',align:'left'" width="15%" >变更后信息</th>
					<th data-options="field:'aae140',align:'left',formatter:formatXzlx" width="15%" >险种类型</th>
					<th data-options="field:'aae155',align:'left'" width="15%" >变更项中文含义</th>
					<th data-options="field:'aae157',align:'left'" width="15%" >变更项目所在表</th>
					<th data-options="field:'aaz002',align:'left'" width="15%" >业务日志ID</th>
					<th data-options="field:'pk0000',align:'left'" width="15%" >主键</th>
					
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
