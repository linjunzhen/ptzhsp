<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
	<script language="javascript" src="webpage/call/takeNo/js/LodopFuncs.js"></script>
<script type="text/javascript">
	$(function() {
		$("#zjhm").addClass(",custom[vidcard]");
		AppUtil.initWindowForm("AssistForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				//$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#AssistForm").serialize();
				var url = $("#AssistForm").attr("action");
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
							//设置打印
							/* var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
							LODOP.PRINT_INIT("打印取号单");
							LODOP.SET_PRINT_PAGESIZE(1,2400,1080,''); 
							//LODOP.ADD_PRINT_RECT(0,0,320,500,0,1);//大小
							LODOP.SET_PRINT_STYLE("FontSize",11);//基本打印风格
							LODOP.ADD_PRINT_TEXT(20,150,2300,25,"平潭综合实验区行政服务中心");
							LODOP.SET_PRINT_STYLEA(1,"FontName","隶书");
							LODOP.SET_PRINT_STYLEA(1,"FontSize",30);
							LODOP.ADD_PRINT_TEXT(70,300,2300,25,"取号通知单");
							LODOP.SET_PRINT_STYLEA(2,"FontName","隶书");
							LODOP.SET_PRINT_STYLEA(2,"FontSize",30);
							LODOP.ADD_PRINT_TEXT(200,290,2300,25,"排队号："+lineNo);
							LODOP.SET_PRINT_STYLEA(3,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(3,"FontSize",24);
							LODOP.ADD_PRINT_TEXT(250,290,2300,25,"当前等候人数："+waitNum+"人");
							LODOP.SET_PRINT_STYLEA(4,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(4,"FontSize",20);
							LODOP.ADD_PRINT_TEXT(300,315,2300,25,"（请留意"+roomNo+"区"+winNo+"号窗口）");
							LODOP.ADD_PRINT_TEXT(350,50,2300,25,"温馨提示：");
							LODOP.SET_PRINT_STYLEA(6,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(6,"FontSize",20);
							LODOP.ADD_PRINT_TEXT(385,50,2300,25,"1、本单须在当天交办理窗口收执，超过时间请重新取号；");
							LODOP.SET_PRINT_STYLEA(7,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(7,"FontSize",20);
							LODOP.ADD_PRINT_TEXT(425,50,2300,25,"2、叫号三次未到窗口办理，本单作废；");
							LODOP.SET_PRINT_STYLEA(8,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(8,"FontSize",20);
							LODOP.ADD_PRINT_TEXT(465,50,2300,25,"3、请您留意窗口叫号，以免影响您的办事。");
							LODOP.SET_PRINT_STYLEA(9,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(9,"FontSize",20);
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
							LODOP.ADD_PRINT_TEXT(505,50,2300,25,"取号时间："+time);
							LODOP.SET_PRINT_STYLEA(10,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(10,"FontSize",15); */
							
							
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
							parent.$("#AssistGrid").datagrid("reload");
							document.getElementById("AssistForm").reset(); 
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
	
	function showSelectDeparts(){
    	var departId = $("input[name='departId']").val();
    	parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");
    			if(selectDepInfo){
    				$("input[name='departId']").val(selectDepInfo.departIds);
        			$("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
    			}
    		}
    	}, false);
    }
	
	function readCard(){
		 var CVR_IDCard = document.getElementById("CVR_IDCard");					
			var strReadResult = CVR_IDCard.ReadCard();
			if(strReadResult == "0"){
				$("input[name='lineName']").val(CVR_IDCard.Name);
				$("input[name='lineCardNo']").val(CVR_IDCard.CardNo); 
           	}else{
           		alert("未能有效识别身份证，请重新读卡！");
           		return false;
           	}
	}
	
	function setValidate(zjlx){
		if(zjlx=="TWTX"||zjlx=="GATX"){
			$("#isVip").val(1);
			$("#zjhm").removeClass(",custom[vidcard]");
		}else if(zjlx=="SF"){
			$("#isVip").val(0);		
			$("#zjhm").addClass(",custom[vidcard]");
		}else{
			$("#isVip").val(0);
			$("#zjhm").removeClass(",custom[vidcard]");
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="AssistForm" method="post" action="callController.do?takeNo">
	    <div  id="AssistFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="departId">
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">证件类型：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" value="SF" name="LINE_ZJLX" id="LINE_ZJLX">
						</eve:eveselect> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
					</td>
				</tr>
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">姓名：</span> <input
						type="text" style="width:150px;float:left;"
						maxlength="8"
						class="eve-input validate[required]" name="lineName" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>

				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">证件号码：</span>
						<input type="text" style="width:150px;float:left;"
						maxlength="18" id="zjhm"
						class="eve-input validate[required]"
						name="lineCardNo" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">办事部门：</span> <input
						type="text" style="width:150px;float:left;" readonly="readonly"
						onclick="showSelectDeparts();"
						class="eve-input validate[required]" name="DEPART_NAME" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">绿色通道：</span>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择" value="0" name="isVip" id="isVip">
						</eve:eveselect>
					</td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
		    <input value="读卡" type="button" onclick="readCard()" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
<OBJECT classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7"
  	id="CVR_IDCard" name="CVR_IDCard" width="0" height="0" >
</OBJECT>
<OBJECT id="LODOP_OB" CLASSID="CLSID:2105C259-1E0C-4534-8141-A753534CB4CA" WIDTH=0 HEIGHT=0> 
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</OBJECT>
