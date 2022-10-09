<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,laydate,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
	
		var start1 = {
			elem : "#SysLogL.UPDATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.UPDATE_TIME_>=']")
						.val();
				var endTime = $("input[name='Q_T.UPDATE_TIME_<=']")
						.val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime.replace("-", "/")
							.replace("-", "/"));
					var end = new Date(endTime.replace("-", "/")
							.replace("-", "/"));
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.UPDATE_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#SysLogL.UPDATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.UPDATE_TIME_>=']")
						.val();
				var endTime = $("input[name='Q_T.UPDATE_TIME_<=']")
						.val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime.replace("-", "/")
							.replace("-", "/"));
					var end = new Date(endTime.replace("-", "/")
							.replace("-", "/"));
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.UPDATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);

	});
	
	function exportSwbExcel(){
		
		//将提交按钮禁用,防止重复提交
		$("#exportSwbButton").attr("disabled", "disabled");
    	var beginDate = $("input[name='Q_T.UPDATE_TIME_>=']").val();
    	var endDate = $("input[name='Q_T.UPDATE_TIME_<=']").val();
		var url = "statisticsController.do?exportSwbExcel&beginDate="+beginDate+"&endDate="+endDate;
		if(null!=beginDate&&''!=beginDate){
			url+="&Q_T.UPDATE_TIME_GE="+beginDate+" 00:00:00";
		}
		if(null!=endDate&&''!=endDate){
			url+="&Q_T.UPDATE_TIME_LE="+endDate+" 23:59:59";
		}
    	window.location.href = url;
    }
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="exportSwbViewWin" method="post"
		action="statisticsController.do?exportSwbExcel">
		<div id="exportSwbViewWinDiv">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">开始日期：</span>
						<input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.UPDATE_TIME_BEGIN" name="Q_T.UPDATE_TIME_>=" />
					</td>
					
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">结束日期：</span>
						<input type="text"
								style="width:128px;float:left;" class="laydate-icon"
								id="SysLogL.UPDATE_TIME_END" name="Q_T.UPDATE_TIME_<=" />
					</td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
			<input value="确定" id="exportSwbButton" type="button"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" onclick="exportSwbExcel();" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</body>

