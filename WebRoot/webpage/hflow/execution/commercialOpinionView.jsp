<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<script type="text/javascript">
	function formatSubject(val,row){
		var fileId = row.FILE_ID;
        var href = "<a href='";
		if(fileId!=""){
            href += "DownLoadServlet?fileId="+fileId;
		}else{
			href += "javascript:void(0);";
		}
        href += "' target='_blank'><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	}

	$(document).ready(function() {
		var start1 = {
			elem : "#OpinionT.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#OpinionT.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);
	});

</script>
<div class="easyui-layout eui-jh-box" fit="true">

	<div region="center">

		<!-- =========================查询面板开始========================= -->
		<div id="OpinionToolbar">
			<div class="right-con">

			</div>
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<form action="#" name="OpinionForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">

							<td style="width:68px;text-align:right;">用户名</td>
							<td style="width:135px;">
								<input class="eve-input" type="text" maxlength="20" style="width:128px;" name="Q_T.OPINION_USERNAME_LIKE" />
							</td>
							<td style="width:68px;text-align:right;">申请日期从</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
															style="width:128px;float:left;" class="laydate-icon"
															id="OpinionT.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_>=" /></td>
							<td style="width:68px;text-align:right;">申请日期至</td>
							<td style="width:135px;padding-left:4px;"><input type="text"
															style="width:128px;float:left;" class="laydate-icon"
															id="OpinionT.CREATE_TIME_END" name="Q_T.CREATE_TIME_<=" /></td>

							<td colspan="2"><input type="button" value="查询"
												   class="eve-button"
												   onclick="AppUtil.gridDoSearch('OpinionToolbar','OpinionGrid')" />
								<input type="button" value="重置" class="eve-button"
									   onclick="AppUtil.gridSearchReset('OpinionForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="OpinionGrid" fitcolumns="false" toolbar="#OpinionToolbar"
			method="post" idfield="EXE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false" nowrap="false"
			url="exeDataController.do?opinionDataGrid">
			<thead>
				<tr>
					<!-- <th field="ck" checkbox="true"></th> -->
					<th data-options="field:'OPINION_USERNAME',align:'left'" width="15%">用户名</th>
					<th data-options="field:'OPINION_SJHM',align:'left'" width="15%">手机号码</th>
					<th data-options="field:'OPINION_CONTENT',align:'left'" width="40%">意见内容</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="28%" >申请时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
