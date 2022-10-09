<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aab001']").addClass("validate[required]");
           $("#unitInsuForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#unitInsuGrid').datagrid({
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
     function psnBasicSearch(){
            var validateResult =$("#unitInsuForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#unitInsuGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('unitInsuTolbar','unitInsuGrid');
            $('#unitInsuGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#unitInsuGrid").datagrid("getRows");
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
		function deleteunitInsuGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"unitInsuGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editunitInsuGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("unitInsuGrid");
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
		<div id="unitInsuTolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="unitInsuForm" id="unitInsuForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;"><font class="tab_color">*</font>单位编号</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="aab001" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="psnBasicSearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('unitInsuForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="unitInsuGrid" fitcolumns="true" toolbar="#unitInsuTolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?unitInsuGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aaa027',align:'left'" width="12%">统筹区编码</th>
					<th data-options="field:'aab001',align:'left'" width="15%">单位ID</th>
					<th data-options="field:'aab016',align:'left'" width="11%">专管员姓名</th>
					<th data-options="field:'aab018',align:'left'" width="15%">专管员联系电话</th>
					<th data-options="field:'aab033',align:'left',formatter:formatZsfs" width="10%">征收方式</th>
					<th data-options="field:'aab050',align:'left'" width="15%" >参保日期</th>
					<th data-options="field:'aab051',align:'left',formatter:formatCbzt" width="10%">单位参保状态</th>
					<th data-options="field:'aab066',align:'left'" width="10%">单位暂停缴费标志</th>
					
					<th data-options="field:'aab340',align:'left'" width="10%">银行名称</th>
					<th data-options="field:'aae009',align:'left'" width="15%">户名</th>
					<th data-options="field:'aae010',align:'left'" width="15%">银行帐号</th>
					<th data-options="field:'aae013',align:'left'" width="11%">备注</th>
					<th data-options="field:'aae041',align:'left'" width="10%">开始年月</th>
					<th data-options="field:'aae042',align:'left'" width="15%">终止年月</th>
					
					<th data-options="field:'aae100',align:'left',formatter:formatYxbz" width="10%">有效标志</th>
					<th data-options="field:'aae140',align:'left',formatter:formatXzlx" width="15%">险种类型</th>
					<th data-options="field:'aaz002',align:'left'" width="15%">业务日志ID</th>
					<th data-options="field:'aaz003',align:'left'" width="11%">当事人银行账号ID</th>
					<th data-options="field:'aaz040',align:'left'" width="10%">单位参保情况ID</th>
					<th data-options="field:'aaz065',align:'left'" width="15%">银行ID</th>
					
					<th data-options="field:'aaz066',align:'left'" width="10%">税务机构ID</th>
					<th data-options="field:'aaz113',align:'left'" width="15%">浮动费率参数ID</th>
					<th data-options="field:'aaz289',align:'left'" width="15%">征缴规则参数ID</th>
					<th data-options="field:'bae521',align:'left',formatter:formatXzztlb" width="11%">险种主体类别</th>
					<th data-options="field:'bae522',align:'left',formatter:formatXzlx" width="10%">险种类型</th>
					<th data-options="field:'bae523',align:'left'" width="15%">保险险种</th>
					
					<th data-options="field:'bae524',align:'left'" width="10%">最大做账期号</th>
					<th data-options="field:'orgcode',align:'left',formatter:formatSubCenter" width="15%">经办机构代码</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
