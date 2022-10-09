<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
	<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script>
<script type="text/javascript">
	$(function() {
		$("#zjhm").addClass(",custom[vidcard]");
		AppUtil.initWindowForm("AppointmentTakeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#AppointmentTakeForm").serialize();
				var url = $("#AppointmentTakeForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							var child = eval("("+resultJson.jsonString+")");
							var lineNo = child.lineNo;
							var waitNum = child.waitNum;
							var winNo = child.winNo;
							var roomNo = child.roomNo;	
							
							LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
							LODOP.PRINT_INIT("打印取号单");
							LODOP.SET_PRINT_PAGESIZE(1,800,830,''); 
							LODOP.SET_PRINT_STYLE("FontSize",11);//基本打印风格
							LODOP.ADD_PRINT_TEXT(20,25,240,25,"平潭综合实验区");
							LODOP.SET_PRINT_STYLEA(1,"FontName","隶书");
							LODOP.SET_PRINT_STYLEA(1,"FontSize",24);
							LODOP.ADD_PRINT_TEXT(50,35,240,25,"行政服务中心");
							LODOP.SET_PRINT_STYLEA(2,"FontName","隶书");
							LODOP.SET_PRINT_STYLEA(2,"FontSize",18);
							LODOP.ADD_PRINT_TEXT(90,15,240,25,"（平潭综合实验区微信公众号预约取号）");
							LODOP.SET_PRINT_STYLEA(3,"FontName","隶书");
							LODOP.SET_PRINT_STYLEA(3,"FontSize",14);
							LODOP.ADD_PRINT_TEXT(120,35,240,25,"排队号："+lineNo);
							LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(4,"FontSize",20);
							LODOP.ADD_PRINT_TEXT(180,48,240,25,"当前等候人数："+waitNum+"人");
							LODOP.SET_PRINT_STYLEA(5,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(5,"FontSize",14);
							LODOP.ADD_PRINT_TEXT(210,48,240,25,"（请留意"+roomNo+"区"+winNo+"号窗口）");
					        var now=new Date();            //创建Date对象
					        var year=now.getFullYear();    //获取年份
					        var month=now.getMonth();    //获取月份
					        var date=now.getDate();        //获取日期
					        var hour=now.getHours();    //获取小时
					        var minu=now.getMinutes();    //获取分钟
					        var sec=now.getSeconds();    //获取秒钟
					        hour=checkTime(hour);
					        minu=checkTime(minu);
					        sec=checkTime(sec);
					        month=month+1;
					        month=checkTime(month);
					        date=checkTime(date);
					        var time = year+"-"+month+"-"+date+" "+hour+":"+minu+":"+sec;
							LODOP.ADD_PRINT_TEXT(280,7,280,25,"-----"+time+"-----");
							//LODOP.PREVIEW();
							//LODOP.SET_PRINT_MODE("CATCH_PRINT_STATUS",true);
							LODOP.PRINT();
							parent.$("#AppointGrid").datagrid("reload");							
							AppUtil.closeLayer(); 
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
							$("input[type='submit']").attr("disabled","false");
						}
					}
				});
			}
		},null);
	});
	
	function checkTime(i){
	    if (i<10){
	    	i="0" + i;
	    }
	    return i;
    }
	
</script>
</head>

<body style="margin:0px;background-color: #333399;">
	<form id="AppointmentTakeForm" method="post" action="callController.do?takeNo">
	    <div  id="AppointmentTakeFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="departId" value="${appointment.DEPART_ID }">
		    <input type="hidden" name="LINE_ZJLX" value="SF">
		    <input type="hidden" name="isAppoint" value="1">
		    <input type="hidden" name="appointmentId" value="${appointment.ID }">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">姓名：</span> <input
						type="text" style="width:150px;float:left;"
						maxlength="8" readonly="readonly"
						class="eve-input validate[required]" name="lineName" value="${appointment.NAME }"/> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>

				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">身份证号：</span>
						<input type="text" style="width:150px;float:left;"
						maxlength="18" id="zjhm" readonly="readonly"
						class="eve-input validate[required]"
						name="lineCardNo"  value="${appointment.CARD }"/> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">办事部门：</span> <input
						type="text" style="width:150px;float:left;" readonly="readonly"
						class="eve-input validate[required]" name="DEPART_NAME" value="${appointment.DEPART_NAME }"/> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
<OBJECT id="LODOP_OB" CLASSID="CLSID:2105C259-1E0C-4534-8141-A753534CB4CA" WIDTH=0 HEIGHT=0> 
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</OBJECT>
