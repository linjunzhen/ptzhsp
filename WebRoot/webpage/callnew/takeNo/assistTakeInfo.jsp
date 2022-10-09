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
							var mobile = child.userMobile;						
							
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
							
							LODOP.SET_PRINT_STYLEA(5,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(5,"FontSize",12);
							//添加叫号手机号码打印输出
							if(mobile!=null && mobile !=undefined && mobile!=""){
								LODOP.ADD_PRINT_TEXT(145,20,400,20,"手机号码："+mobile);
							}else{
								LODOP.ADD_PRINT_TEXT(145,20,400,20,"未录入手机号码,暂无法接收排队短信提醒!");
							}
						
							LODOP.ADD_PRINT_TEXT(180,48,240,25,"当前等候人数："+waitNum+"人");
							LODOP.SET_PRINT_STYLEA(6,"FontName","宋体");
							LODOP.SET_PRINT_STYLEA(6,"FontSize",14);
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
							LODOP.PRINT();
							parent.$("#QueueGrid").datagrid("reload");
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
	
    
    function selectBusinessName(){
    	var businessCodes = $("input[name='businessCode']").val();
    	parent.$.dialog.open("callSetController.do?selector&allowCount=1&businessCodes="
				+ businessCodes, {
			title : "业务选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close: function () {
    			var selectBueinessInfo = art.dialog.data("selectBueinessInfo");
    			if(selectBueinessInfo&&selectBueinessInfo.businessCodes){
    				$("input[name='businessCode']").val(selectBueinessInfo.businessCodes);
    				$("input[name='WIN_BUSINESS_NAMES']").val(selectBueinessInfo.businessNames);
    				art.dialog.removeData("selectBueinessInfo");
    			}
    		}
		}, false);
    }
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="AssistForm" method="post" action="newCallController/generateNo.do">
	    <div id="AssistFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<!-- <tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr> -->
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
						type="text" style="width:235px;float:left;"
						maxlength="8"
						class="eve-input validate[required]" name="lineName" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>

				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">证件号码：</span>
						<input type="text" style="width:235px;float:left;"
						maxlength="18" id="zjhm"
						class="eve-input validate[required]"
						name="lineCardNo" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">地址：</span>
						<input type="text" style="width:235px;float:left;"
							   maxlength="128" id="address"
							   class="eve-input validate[]"
							   name="address" />
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">办理业务：</span> 						
						<input type="hidden" name="businessCode" value="">
						<input type="text"
						style="width:235px;float:left;"
						class="eve-input validate[required]"
						name="WIN_BUSINESS_NAMES" readonly="readonly"
						onclick="selectBusinessName();" />
						<!-- <input class="easyui-combobox"
								style="width:235px;" name="businessCode"
								data-options="url:'callSetController.do?loadBusiness&amp;defaultEmpty=true',editable:false,method: 'post',valueField:'BUSINESS_CODE',textField:'BUSINESS_NAME',panelWidth: 235,panelHeight: '300' " /> -->
						<font class="dddl_platform_html_requiredFlag">*</font></td>
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
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="读卡" type="button" onclick="readCard()" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
<OBJECT classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7"
  	id="CVR_IDCard" name="CVR_IDCard" width="0" height="0" >
</OBJECT>
<OBJECT id="LODOP_OB" CLASSID="CLSID:2105C259-1E0C-4534-8141-A753534CB4CA" WIDTH=0 HEIGHT=0> 
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</OBJECT>
