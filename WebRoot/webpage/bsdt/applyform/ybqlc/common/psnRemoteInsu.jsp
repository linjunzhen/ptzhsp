<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("#psnRemoteInsuForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#psnRemoteInsuGrid').datagrid({
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
     function psnRemoteInsuSearch(){
            var validateResult =$("#psnRemoteInsuForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#psnRemoteInsuGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('psnRemoteInsuToolbar','psnRemoteInsuGrid');
            $('#psnRemoteInsuGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#psnRemoteInsuGrid").datagrid("getRows");
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
		function deletepsnRemoteInsuGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"psnRemoteInsuGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editpsnRemoteInsuGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("psnRemoteInsuGrid");
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
		<div id="psnRemoteInsuToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="psnRemoteInsuForm" id="psnRemoteInsuForm">
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
								onclick="psnRemoteInsuSearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('psnRemoteInsuForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="psnRemoteInsuGrid" fitcolumns="true" toolbar="#psnRemoteInsuToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?psnRemoteInsuGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aac001',align:'left'" width="11%">人员ID</th>
					<th data-options="field:'aac002',align:'left'" width="15%">公民身份号码</th>
					<th data-options="field:'aac003',align:'left'" width="10%">姓名</th>
					<th data-options="field:'aae100',align:'left',formatter:formatYxbz" width="11%">有效标志</th>
					<th data-options="field:'aae140',align:'left',formatter:formatXzlx" width="11%">险种类型</th>
					<th data-options="field:'aab300',align:'left'" width="12%">最后医保参保地</th>
					<th data-options="field:'aab301',align:'left'" width="15%">所属行政区划代码</th>
					<th data-options="field:'aae011',align:'left'" width="15%" >经办人</th>
					<th data-options="field:'aae013',align:'left'" width="12%">备注</th>
					<th data-options="field:'aae014',align:'left'" width="15%">审核人</th>
					<th data-options="field:'aae015',align:'left'" width="11%">复核时间</th>
					<th data-options="field:'aae036',align:'left'" width="15%">经办时间</th>
					<th data-options="field:'aae041',align:'left'" width="10%">开始年月</th>
					<th data-options="field:'aae042',align:'left'" width="15%" >终止年月</th>
					<th data-options="field:'aae218',align:'left'" width="12%">办理完成日期</th>
					<th data-options="field:'aae219',align:'left'" width="15%">修改日期</th>
					<th data-options="field:'aaz002',align:'left'" width="11%">业务日志ID</th>
					<th data-options="field:'bab504',align:'left'" width="15%">居民单位登记事件表id</th>
					<th data-options="field:'bab505',align:'left'" width="10%">居民单位管理码</th>
					<th data-options="field:'bac511',align:'left'" width="15%" >异地参保月数</th>
					<th data-options="field:'bae572',align:'left'" width="12%">已转个人账户余额</th>
					<th data-options="field:'bae573',align:'left'" width="15%">未转个人账户余额</th>
					<th data-options="field:'bae693',align:'left'" width="11%">转入来源</th>
					<th data-options="field:'bae765',align:'left'" width="15%">是否中断</th>
					<th data-options="field:'baz524',align:'left'" width="10%">事件表id</th>
					<th data-options="field:'baz525',align:'left'" width="15%" >系统跟踪流水号</th>
					<th data-options="field:'orgcode',align:'left'" width="15%" >经办机构代码</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
