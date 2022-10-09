<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("#psnBasicForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#psnBasicGrid').datagrid({
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
            var validateResult =$("#psnBasicForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
            var count = 0;
            //$("#psnBasicGrid").datagrid('clearChecked');
            AppUtil.gridDoSearch('psnBasicToolbar','psnBasicGrid');
            $('#psnBasicGrid').datagrid({
                onLoadSuccess:function(){
                    //确保初始化后只执行一次
                    if(count == 0){
                        var rows = $("#psnBasicGrid").datagrid("getRows");
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
		function deletepsnBasicGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"psnBasicGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editpsnBasicGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("psnBasicGrid");
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
		<div id="psnBasicToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			
			<form action="#" name="psnBasicForm" id="psnBasicForm">
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
								onclick="psnBasicSearch();" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('psnBasicForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
			id="psnBasicGrid" fitcolumns="true" toolbar="#psnBasicToolbar"
			method="post" checkonselect="false" nowrap="false"
			selectoncheck="false" fit="true" border="false"
			url="ybCommonController.do?psnBasicGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
					<th data-options="field:'aab001',align:'left'" width="12%">居民单位ID</th>
					<th data-options="field:'aac001',align:'left'" width="15%">个人编号</th>
					<th data-options="field:'aaz501',align:'left'" width="11%">社保卡号</th>
					<th data-options="field:'aac003',align:'left'" width="15%">姓名</th>
					<th data-options="field:'aac002',align:'left'" width="10%">公民身份证号码</th>
					<th data-options="field:'bae955',align:'left'" width="15%" >公务员类型</th>
					<th data-options="field:'orgcode',align:'left',formatter:formatSubCenter" width="10%">分中心</th>
					<th data-options="field:'aaz067',align:'left'" width="10%">家庭编号</th>
					
					<th data-options="field:'aab999',align:'left'" width="10%">单位保险号</th>
					<th data-options="field:'aab004',align:'left'" width="15%">单位名称</th>
					<th data-options="field:'aac066',align:'left',formatter:formatCbsf" width="11%">参保身份</th>
					<th data-options="field:'aac031',align:'left',formatter:formatCbzt" width="10%">参保状态</th>
					<th data-options="field:'aae140',align:'left',formatter:formatXzlx" width="15%">险种</th>
					<th data-options="field:'bae522',align:'left',formatter:formatXzlb" width="10%">险种类型</th>
					
					
					<th data-options="field:'bae957',align:'left'" width="15%">个人医疗证号</th>
					<th data-options="field:'bac503',align:'left'" width="11%">个人保险号</th>
					<th data-options="field:'bae957',align:'left'" width="10%">医疗证号</th>
					<th data-options="field:'aab001',align:'left'" width="15%">单位编号</th>
					
					
					<th data-options="field:'aac040',align:'left'" width="15%">工资</th>
					<th data-options="field:'aac004',align:'left',formatter:formatSex" width="11%">性别</th>
					<th data-options="field:'aac005',align:'left',formatter:formatMz" width="10%">民族</th>
					<th data-options="field:'aae010',align:'left'" width="15%">银行账号</th>
					
					<th data-options="field:'aac006',align:'left'" width="10%">出生日期</th>
					<th data-options="field:'aac007',align:'left'" width="15%">参加工作时间</th>
					<th data-options="field:'aab301',align:'left'" width="11%">所属地区</th>
					<th data-options="field:'aae006',align:'left'" width="10%">通讯地址</th>
					<th data-options="field:'aae005',align:'left'" width="15%">联系电话</th>
					<th data-options="field:'bae528',align:'left'" width="15%">手机号码</th>
					
					<th data-options="field:'aac009',align:'left',formatter:formatHkxz" width="10%">户口性质</th>
					<th data-options="field:'bab511',align:'left'" width="15%">单位档案号</th>
					<th data-options="field:'aab019',align:'left',formatter:formatDwlb" width="11%">单位类别</th>
					<th data-options="field:'aab021',align:'left',formatter:formatLsgx" width="10%">隶属关系</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
