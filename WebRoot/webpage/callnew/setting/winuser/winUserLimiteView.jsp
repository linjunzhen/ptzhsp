<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function limitAB(statu){
		AppUtil.ajaxProgress({
			url : "callSetController.do?limitAB",
			params : {
				"statu" : statu
			},
			callback : function(resultJson) {
			    parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.gridDoSearch("WinUserLimiteToolbar", "WinUserLimiteGrid");
			}
		});
	}

	function numformater(val,row){
		if(val<"40"){
			return "<font color='green'><b>当前等待人数"+val+"人</font>";
		}else if(val>="40"&&val<"50"){
			return "<font color='orange'><b>当前等待人数"+val+"人</font>";
		}else {
			return "<font color='red'><b>当前等待人数"+val+"人</font>";
		}
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="WinUserLimiteToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-check" plain="true"
								onclick="limitAB('1');">开启2.4.6.8.10.12AB类业务</a>
								<a href="#"
								class="easyui-linkbutton" reskey="DEL_Win"
								iconcls="eicon-ban" plain="true"
								onclick="limitAB('0');">限制2.4.6.8.10.12AB类业务</a>
						</div>
					</div>
				</div>
			</div>	
			<!-- <form action="#" name="WinUserLimiteForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
						</tr>
					</tbody>
				</table>
			</form>	 -->		
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="WinUserLimiteGrid" fitcolumns="true" nowrap="false"
			toolbar="#WinUserLimiteToolbar" method="post" idfield="BUSINESS_CODE"
			checkonselect="true" selectoncheck="false" fit="true" border="false"
			url="callSetController.do?waitNum">
			<thead>
				<tr>
					<th data-options="field:'BUSINESS_CODE',align:'left'" width="20%">业务</th>
					<th data-options="field:'NUM',align:'left',formatter:numformater" width="78%">当前等待人数</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
