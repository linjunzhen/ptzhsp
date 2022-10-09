<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("#psnSpecialHistoryForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#psnSpecialHistoryGrid').datagrid({
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
     function psnSpecialHistorySearch(){
            var validateResult =$("#psnSpecialHistoryForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#psnSpecialHistoryGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('psnSpecialHistoryToolbar','psnSpecialHistoryGrid');
            $('#psnSpecialHistoryGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#psnSpecialHistoryGrid").datagrid("getRows");
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
		function deletepsnSpecialHistoryGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"psnSpecialHistoryGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editpsnSpecialHistoryGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("psnSpecialHistoryGrid");
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
		<div id="psnSpecialHistoryToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="psnSpecialHistoryForm" id="psnSpecialHistoryForm">
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
								onclick="psnSpecialHistorySearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('psnSpecialHistoryForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="psnSpecialHistoryGrid" fitcolumns="true" toolbar="#psnSpecialHistoryToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?psnSpecialHistoryGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aab001',align:'left'" width="12%">单位ID</th>
					<th data-options="field:'aab301',align:'left'" width="15%">所属行政区划代码</th>
					<th data-options="field:'aac001',align:'left'" width="11%">人员ID</th>
					<th data-options="field:'aac002',align:'left'" width="15%">公民身份号码</th>
					<th data-options="field:'aac003',align:'left'" width="12%">姓名</th>
					<th data-options="field:'aac040',align:'left'" width="15%">工资</th>
					<th data-options="field:'aae001',align:'left'" width="11%">年度</th>
					<th data-options="field:'aae011',align:'left'" width="15%">经办人</th>
					<th data-options="field:'aae013',align:'left'" width="15%">备注</th>
					<th data-options="field:'aae014',align:'left'" width="11%">审核人</th>
					<th data-options="field:'aae041',align:'left'" width="15%">开始年月</th>
					<th data-options="field:'aae042',align:'left'" width="15%">终止年月</th>
					<th data-options="field:'aae100',align:'left',formatter:formatYxbz" width="11%">有效标志</th>
					<th data-options="field:'aae217',align:'left'" width="15%">确认日期</th>
					<th data-options="field:'aae218',align:'left'" width="15%">办理完成日期</th>
					<th data-options="field:'aaz002',align:'left'" width="11%">业务日志ID</th>
					<th data-options="field:'bac508',align:'left'" width="15%">特殊人员类型</th>
					<th data-options="field:'bae501',align:'left'" width="10%">一级审核人</th>
					<th data-options="field:'bae502',align:'left'" width="15%" >一级审核时间</th>
					<th data-options="field:'bae513',align:'left'" width="11%">是否免缴 1是  0否</th>
					<th data-options="field:'bae570',align:'left'" width="10%">所属系统</th>
					<th data-options="field:'baz518',align:'left'" width="15%" >特殊人员登记ID</th>
					<th data-options="field:'orgcode',align:'left'" width="15%" >经办机构代码</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
