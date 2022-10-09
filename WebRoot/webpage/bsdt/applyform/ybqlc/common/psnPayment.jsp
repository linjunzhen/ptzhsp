<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("#psnPaymentForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#psnPaymentGrid').datagrid({
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
     function psnPaymentSearch(){
            var validateResult =$("#psnPaymentForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#psnPaymentGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('psnPaymentToolbar','psnPaymentGrid');
            $('#psnPaymentGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#psnPaymentGrid").datagrid("getRows");
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
		function deletepsnPaymentGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"psnPaymentGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editpsnPaymentGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("psnPaymentGrid");
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
		<div id="psnPaymentToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="psnPaymentForm" id="psnPaymentForm">
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
								onclick="psnPaymentSearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('psnPaymentForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="psnPaymentGrid" fitcolumns="true" toolbar="#psnPaymentToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?psnPaymentGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aaa115',align:'left'" width="12%">应缴类型</th>
					<th data-options="field:'aab001',align:'left'" width="15%">单位ID</th>
					<th data-options="field:'aab004',align:'left'" width="11%">单位名称</th>
					<th data-options="field:'aab033',align:'left',formatter:formatZsfs" width="11%">征收方式</th>
					<th data-options="field:'aab999',align:'left'" width="15%">单位管理码</th>
					<th data-options="field:'aac001',align:'left'" width="12%">人员ID</th>
					<th data-options="field:'aac002',align:'left'" width="15%">公民身份号码</th>
					<th data-options="field:'aac003',align:'left'" width="11%">姓名</th>
					<th data-options="field:'aac040',align:'left'" width="15%">工资</th>
					<th data-options="field:'aac066',align:'left',formatter:formatCbsf" width="15%">参保身份</th>
					<th data-options="field:'aae002',align:'left'" width="11%">费款所属期</th>
					<th data-options="field:'aae003',align:'left'" width="15%">对应费款所属期</th>
					<th data-options="field:'aae017',align:'left'" width="15%">核销标志</th>
					<th data-options="field:'aae018',align:'left'" width="11%">核销年月</th>
					<th data-options="field:'aae020',align:'left'" width="15%">单位应缴金额</th>
					<th data-options="field:'aae021',align:'left'" width="15%">单位应缴划入个人账户金额</th>
					<th data-options="field:'aae022',align:'left'" width="11%">个人应缴金额</th>
					<th data-options="field:'aae023',align:'left'" width="15%">个人应缴划入个人账金额</th>
					<th data-options="field:'aae026',align:'left'" width="10%">其他来源一应缴金额</th>
					<th data-options="field:'aae027',align:'left'" width="15%" >其他来源一应缴划入个人账户金额</th>
					<th data-options="field:'aae033',align:'left'" width="10%">截至年月</th>
					<th data-options="field:'aae078',align:'left'" width="15%" >到账划拨标志</th>
					<th data-options="field:'aae079',align:'left'" width="15%" >到账日期</th>
					<th data-options="field:'aae140',align:'left',formatter:formatXzlx" width="15%">险种类型</th>
					<th data-options="field:'aae180',align:'left'" width="15%">人员缴费基数</th>
					<th data-options="field:'aae202',align:'left'" width="11%">累计缴费月数增加额</th>
					<th data-options="field:'aae300',align:'left'" width="15%">登帐人</th>
					<th data-options="field:'aaz083',align:'left'" width="10%">当事人征缴计划事件ID</th>
					<th data-options="field:'aaz223',align:'left'" width="15%" >人员征缴明细ID</th>
					<th data-options="field:'aaz289',align:'left'" width="10%">征缴规则参数ID</th>
					<th data-options="field:'bab511',align:'left'" width="15%" >单位档案号</th>
					<th data-options="field:'bac503',align:'left'" width="15%" >个人管理码</th>
					<th data-options="field:'bae534',align:'left'" width="15%">划拨批次</th>
					<th data-options="field:'bae535',align:'left'" width="11%">划拨日期</th>
					<th data-options="field:'bae536',align:'left'" width="17%">帐目冲销标记 Z:正常, +:被冲销帐目, -:冲销负帐目</th>
					<th data-options="field:'bae539',align:'left'" width="10%">建账日期</th>
					<th data-options="field:'bae541',align:'left'" width="15%" >是否所有单位</th>
					<th data-options="field:'bae679',align:'left'" width="10%">到账经办人</th>
					<th data-options="field:'bae903',align:'left'" width="15%" >备注</th>
					<th data-options="field:'bae987',align:'left'" width="15%" >预留字段4</th>
					<th data-options="field:'bae988',align:'left'" width="15%">到账来源</th>
					<th data-options="field:'orgcode',align:'left',formatter:formatSubCenter" width="11%">经办机构代码</th>
					<th data-options="field:'orgcode_gr',align:'left'" width="15%">个人所属分中心</th>
					<th data-options="field:'sum_ll',align:'left'" width="10%">划拨金额总计</th>
					<th data-options="field:'yjhj00',align:'left'" width="15%" >应缴合计</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
