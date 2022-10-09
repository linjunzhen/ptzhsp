<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
	
    .tdth{
		height: 31px;
	    line-height: 31px;
	    background: #f3f3f3;
	    color: #053892;
	}
</style>
<script type="text/javascript">

	$(function(){
           //初始化验证引擎的配置
           $("input[name='aac002']").addClass("validate[required]");
           $("input[name='aac003']").addClass("validate[required]");
           $("input[name='aab001']").addClass("validate[required]");
           $("#psnWaveAndAccountForm").validationEngine({
               promptPosition:"bottomLeft"
           });
           //空数据，横向滚动
			$('#psnWaveGrid').datagrid({
				onLoadSuccess: function(data){
					if(data.total==0){
						var dc = $(this).data('datagrid').dc;
				        var header2Row = dc.header2.find('tr.datagrid-header-row');
				        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
			        }
				}
			});	
			//空数据，横向滚动
			$('#psnAccountGrid').datagrid({
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
     function psnWaveAndAccountSearch(){
            var validateResult =$("#psnWaveAndAccountForm").validationEngine("validate");
            if(!validateResult){
                return false;
            }
			
            var aac002 = $("#psnWaveAndAccountForm").find('input[name="aac002"]').val();//公民身份证号码
			var aac003 = $("#psnWaveAndAccountForm").find('input[name="aac003"]').val();//姓名
			var aab001 = $("#psnWaveAndAccountForm").find('input[name="aab001"]').val();//单位编号
            $.ajax({
		         type: "POST",
			     url: "ybCommonController.do?psnWaveAndAccountDatagrid",
			     data:{"aac002":aac002,"aac003":aac003,"aab001":aab001},
			     async: false, //采用同步方式进行数据判断
			     success: function (responseText) {
			    	var resultJson = responseText;
					if(resultJson.total>0){	
						var rows = resultJson.rows;
						var wage = rows[0].wage;
						var account = rows[0].account;
						if(wage!=null){
							$('#psnWaveGrid').datagrid('loadData',{"total":wage.length,"rows":wage});
						}
						if(account!=null){
							$('#psnAccountGrid').datagrid('loadData',{"total":account.length,"rows":account});
						}
					}else{
						art.dialog({
                            content: "无匹配数据返回，查询记录为空！",
                            icon:"warning",
                            ok: true
                        });
                        //清空数据
						$("#psnWaveGrid").datagrid("loadData", { total: 0, rows: [] });
						$("#psnAccountGrid").datagrid("loadData", { total: 0, rows: [] });
					}
			     }
			 });
        }
        
		/**
		 * 删除列表记录
		 */
		function deletezgInsuGridRecord() {
			AppUtil.deleteDataGridRecord("executionController.do?multiDel",
					"zgInsuGrid");
		};
		/**
		 * 编辑列表记录
		 */
		function editzgInsuGridRecord() {
			var entityId = AppUtil.getEditDataGridRecord("zgInsuGrid");
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
		<!-- =========================表格开始==========================-->
		 <table cellpadding="0" cellspacing="1" style="width:100%;height:100%" class="tab_tk_pro">
		 	<tr style="height:28px;">
		 	     <td>
		 	     <form action="#" name="psnWaveAndAccountForm" id="psnWaveAndAccountForm">
		 		 <table cellpadding="0" cellspacing="1" style="width:100%" class="tab_tk_pro" >
		 		
					<td style="width:90px;text-align:right;"><font class="tab_color">*</font>公民身份号码</td>
					<td style="width:135px;"><input class="eve-input"
						type="text" maxlength="20" style="width:128px;"
						name="aac002" /></td>
					<td style="width:90px;text-align:right;"><font class="tab_color">*</font>姓名</td>
					<td style="width:135px;"><input class="eve-input"
						type="text" maxlength="20" style="width:128px;"
						name="aac003" /></td>
					<td style="width:90px;text-align:right;"><font class="tab_color">*</font>单位编号</td>
					<td style="width:135px;"><input class="eve-input"
						type="text" maxlength="20" style="width:128px;"
						name="aab001" /></td>
					<td colspan="2"><input type="button" value="查询"
						class="eve-button"
						onclick="psnWaveAndAccountSearch();" />
						<input type="button" value="重置" class="eve-button"
						onclick="AppUtil.gridSearchReset('psnWaveAndAccountForm')" /></td>
				</table>
				</form>
				</td>
			</tr>
		 	<!-- <tr>
		 		<td style="height:100px;">
	 				<form action="#" name="psnWaveAndAccountForm" id="psnWaveAndAccountForm">
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
									<td style="width:90px;text-align:right;"><font class="tab_color">*</font>单位编号</td>
									<td style="width:135px;"><input class="eve-input"
										type="text" maxlength="20" style="width:128px;"
										name="aab001" /></td>
									<td colspan="2"><input type="button" value="查询"
										class="eve-button"
										onclick="search();" />
										<input type="button" value="重置" class="eve-button"
										onclick="AppUtil.gridSearchReset('psnWaveAndAccountForm')" /></td>
								</tr>
							</tbody>
						</table>
					</form>
		 		</td>
		 	</tr> -->
		 	<tr>
				<td colspan="8" class="tdth">工资信息</td>
			</tr>
			<tr>
				<td colspan="8" style="height:40%;">
					<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
						id="psnWaveGrid" fitcolumns="true"
						method="post" checkonselect="false" nowrap="false"
						selectoncheck="false" fit="true" border="false"
						url="ybCommonController.do?psnWaveAndAccountDatagrid">
						<thead>
							<tr>
								<!-- <th field="ck" checkbox="true"></th> -->
								<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
								<th data-options="field:'aaa027',align:'left'" width="10%">统筹区编码</th>
								<th data-options="field:'aab001',align:'left'" width="10%">单位ID</th>
								<th data-options="field:'aab004',align:'left'" width="10%">单位名称</th>
								<th data-options="field:'aac001',align:'left'" width="10%">人员ID</th>
								<th data-options="field:'aac003',align:'left'" width="10%">姓名</th>
								<th data-options="field:'aac0402',align:'left'" width="10%">工资</th>
								<th data-options="field:'aae001',align:'left'" width="10%">年度</th>
								<th data-options="field:'aae041',align:'left'" width="10%">开始年月</th>
								<th data-options="field:'aae042',align:'left'" width="10%">终止年月</th>
								<th data-options="field:'aae140',align:'left',formatter:formatXzlx" width="10%">险种类型</th>
								<th data-options="field:'aae306',align:'left'" width="10%">年度工资总额</th>
								<th data-options="field:'aae307',align:'left'" width="10%">工资月数</th>
								<th data-options="field:'aaz182',align:'left'" width="10%">人员工资ID</th>
								<th data-options="field:'aaz184',align:'left'" width="10%" >人员工资变更事件ID</th>
							</tr>
						</thead>
					</table>
					<!-- =========================表格结束==========================-->
				</td>
			</tr>
			<tr>
				<td colspan="8" class="tdth">个账信息</td>
			</tr>
			<tr>
				<td colspan="8" style="height:40%;">
					<!-- =========================表格开始==========================-->
					<table class="easyui-datagrid" rownumbers="true" pagination="false" striped="true"
						id="psnAccountGrid" fitcolumns="true" 
						method="post" checkonselect="false" nowrap="false"
						selectoncheck="false" fit="true" border="false"
						url="ybCommonController.do?psnWaveAndAccountDatagrid">
						<thead>
							<tr>
								<!-- <th field="ck" checkbox="true"></th> -->
								<!-- <th data-options="field:'TASK_ID',hidden:true">TASK_ID</th> -->
								<th data-options="field:'cardno',align:'left'" width="18%">卡号</th>
								<th data-options="field:'grzhye',align:'left'" width="15%">个人账户余额</th>
								<th data-options="field:'icztbh',align:'left',formatter:formatKzt" width="15%">卡状态</th>
								<th data-options="field:'id0000',align:'left'" width="15%">个人保险号</th>
								<th data-options="field:'tbrqi0',align:'left'" width="15%">投保开始日期</th>
								<th data-options="field:'xming0',align:'left'" width="15%">姓名</th>
							</tr>
						</thead>
					</table>
					<!-- =========================表格结束==========================-->
				</td>
			</tr>
		</table>
	</div>
</div>
