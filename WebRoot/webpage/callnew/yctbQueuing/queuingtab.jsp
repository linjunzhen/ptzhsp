<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<%
	SysUser sysUser = AppUtil.getLoginUser();
	//获取登录用户的角色CODE
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否商事登记受理员
    boolean isSsdjsly = roleCodes.contains("ssdjsly");
    boolean isBdcdjsly = roleCodes.contains("BDCDJZX_SLY");
    boolean isXmtzcsly = roleCodes.contains("QXZSPJ_TZXMSLY");
    boolean isCyjbcsly = roleCodes.contains("CYCJ_SLY");
    boolean isYctb = roleCodes.contains("YCTB");
	//判断是否商事登记受理员
	if("__ALL".equals(resKey)||isSsdjsly){
	    request.setAttribute("isSsdjsly",true);
	}else{
	    request.setAttribute("isSsdjsly",false);
	}
	if("__ALL".equals(resKey)||isBdcdjsly){
	    request.setAttribute("isBdcdjsly",true);
	}else{
	    request.setAttribute("isBdcdjsly",false);
	}
	if("__ALL".equals(resKey)||isXmtzcsly){
	    request.setAttribute("isXmtzcsly",true);
	}else{
	    request.setAttribute("isXmtzcsly",false);
	}
	if("__ALL".equals(resKey)||isCyjbcsly){
	    request.setAttribute("isCyjbcsly",true);
	}else{
	    request.setAttribute("isCyjbcsly",false);
	}
	if("__ALL".equals(resKey)||isYctb){
	    request.setAttribute("isYctb",true);
	}else{
	    request.setAttribute("isYctb",false);
	}
%>
<!-- <style>
<style>
.switch{
    width: 82px;
    margin: 100px 0px 0 100px;
}
.btn_fath{
  /* margin-top: 10px; */
  position: relative;
  border-radius: 20px;
}
 
.btn1{
  float: left;
}
 
.btn2{
  float: right;
}
.btnSwitch{
  height: 20px;
  width: 25px;
  border:none;
  color: #fff;
  line-height: 20px;
  font-size: 10px;
  text-align: center;
  z-index: 1;
}
 
.move{
  z-index: 100;
  width: 20px;
  border-radius: 10px;
  height: 20px;
  position: absolute;
  cursor: pointer;
  border: 1px solid #828282;
  background-color: #f1eff0;
  box-shadow: 1px 2px 2px 1px #fff inset,0 0 5px 1px #999;
}
.on .move{
  left: 60px;
}
.on.btn_fath{
  background-color: #5281cd;
}
.off.btn_fath{
  background-color: #828282;
}

.callbutton{
  background-color:#D6D6D6;
  width: 70px;
  height:70px;
  margin-top: 5px;
  margin-bottom: 5px;
  border-radius: 50%;
  border:none;
  cursor: pointer;
}
.callbutton_in{
  background-color:#CDC9C9;
  width: 70px;
  height:70px;
  margin-top: 5px;
  margin-bottom: 5px;
  border-radius: 50%;
  border:none;
  cursor: pointer;
}
.notclick{
	pointer-events: none;
}
</style> -->

<script type="text/javascript">
	$(function(){
		$.dialog.open("newCallController.do?winSelect", {
			title : "窗口选择",
			width : "700px",
			height : "350px",
			lock : true,
			resize : false,
		    close : function() {
    			var selectWinInfo = art.dialog.data("selectWinInfo");
    			if(selectWinInfo){
    				var winNo = selectWinInfo.winNo;
    				var departName = selectWinInfo.departName;
        			var business = selectWinInfo.business;
        			var isContinue = selectWinInfo.isContinue;
        			departName = departName.replace(/\d+/g,''); 
        			var winInfo = departName+"—"+winNo+"号窗口";
        			$("#winInfo").html(winInfo);
        			/* $("#winStatu").html("窗口状态：工作中"); */
        			$("input[name='business']").val(business);
        			$("input[name='winNo']").val(winNo);
        			$("input[name='doneWinNo']").val(winNo);
        			$("input[name='isContinue']").val(isContinue);
					AppUtil.gridDoSearch('callingToolbarYctb','callingNewGrid');
					AppUtil.gridDoSearch('QueuingDayToolbarYctb','QueuingDayGrid');
					setTimeout("setWaitNum();",1000);
					if(isContinue==0){
						$("#againButton").attr("disabled","disabled");
					}
					
					art.dialog.removeData("selectWinInfo");
					
					tellerInfo();
    			}
		    }
		});
		
		//定时刷新待办列表
		setInterval(function(){
			$("#callingNewGrid").datagrid("reload");
            afterDeal();
			setTimeout("setWaitNum();",1000);
		},60000);
		
		$(window).unload(function(){
			var winNo = $("input[name='winNo']").val();
			if(winNo!=""&&winNo!=undefined){
				AppUtil.ajaxProgress({
					url : "newCallController.do?clearWinSelect",
					params : {
						winNo : winNo
					}
				});
			}
		});		
		
		$("#switch").click(function(){
			if(this.checked==true){
				servicing();
			}else{
				waiting();
			}
		});
	});
    function afterDeal(){
        var row = $('#callingNewGrid').datagrid('getData').rows[0];
        if (row&&row.CALL_STATUS=='6') {
            var lineId=row.RECORD_ID;
            AppUtil.ajaxNoProgress({
                url : "newCallController.do?checkLineItemDeal",
                params : {
                    lineId : lineId
                },
                callback : function(resultJson) {
                    if(resultJson.success){
                        AppUtil.closeLayer();
                    }else{
                        dosearchItem();
                    }
                }
            });
        }
    }
	function tellerInfo(){
		var TellerName = '${sessionScope.curLoginUser.fullname}';
		var TellerNo = $("input[name='winNo']").val();
		var TellerPhoto;
		if('${sessionScope.curLoginUser.photo}'!=null&&'${sessionScope.curLoginUser.photo}'!=''){
			TellerPhoto = '<%=basePath%>/${sessionScope.curLoginUser.photo}';
		}else{
			TellerPhoto = '<%=basePath%>/webpage/system/login/images/name.png';
		}
		var cancel=GWQ.GWQ_CancelOperate();
		var ret=GWQ.GWQ_TellerInfo(TellerName,TellerNo,TellerPhoto);
		if(ret==0){

		}else{
		  	/* parent.art.dialog({
				content: "展示信息牌失败，返回错误码为"+ret,
				icon:"error",
				time:3,
				ok: true
			}); */
		}
	}
	
	function setWaitNum(){
		var data=$('#callingNewGrid').datagrid('getData');
     	$("#waitNum").html(data.total);
	}
	
	function zjformater(value,row,index){
		if(row.LINE_ZJLX=="SF"){
			return value;
		}else{
			return value+"<br>("+row.ZJLX+")";
		}
	}
	
	//格式化操作按钮
	function rowformater(value,row,index){
		var isBdcdjsly = '${isBdcdjsly}';
		var html = "";
		if(row.CALL_STATUS==6){
			if(isBdcdjsly=='true'){			
 				 html += "<a href='#' onclick='bdcWaitAccept("+index+")'><font color='blue'>不动产待受理</font></a>&nbsp;&nbsp;|&nbsp;&nbsp;";
 			} 
			html += "<a href='#' onclick='accept("+index+")'><font color='blue'>办理</font></a>&nbsp;&nbsp;|&nbsp;&nbsp;"+
			"<a href='#' onclick='forward("+index+")'>转发</a>";
		}
		return html;
	}
	
	function evaluateformater(value,row,index){
		if(value){
			return "已评价";
		}else{
			if(row.CALL_STATUS=='2'&&index<=2){
				return "<button onclick=\"yctbPassAccept("+index+");\" style=\"cursor:pointer\"> 过号受理</button>";
			}else{
				return "未评价";
			}
		}
	}
	
	//格式化状态
	function statusformater(value,row,index){
		if(value=='6'){
			return '正在办理';
		}else if(value=='0'){
			return '等待中';
		}else if(value=='1'){
			return '已受理';
		}else if(value=='2'){
			return '过号';
		}else if(value=='3'){
			return '处理完毕（领照）';
		}else if(value=='4'){
			return '处理完毕（咨询）';
		}else if(value=='5'){
			return '处理完毕（其他）';
		}else if(value=='7'){
			return '取错事项';
		}
	}
	//叫号按钮时间控制
	var secs = 10;
	var wait = secs * 1000;
	//叫号
	function callCurrent(){	

	    
		//$("#callingNewGrid").datagrid("reload");
		//显示第一页数据  
		$("#callingNewGrid").datagrid("options").pageNumber = 1;  
		//分页栏上跳转到第一页  
		$("#callingNewGrid").datagrid('getPager').pagination({pageNumber: 1});  
		$("#callingNewGrid").datagrid("reload");
		$("#switch").prop("checked",true);
		
		
		var row = $('#QueuingDayGrid').datagrid('getData').rows[0];
		if(row){
		if(row.EVALUATE==""||row.EVALUATE==undefined){ 
	
		if(row.CALL_STATUS=="2"){
	
		
		setTimeout("calling()",2000);
					setBtnDisable();
					}else{
					
		parent.art.dialog({
					content : "上个号未评价，是否继续叫号",
					icon : "warning",
					ok:function(){
					setTimeout("calling()",2000);
					setBtnDisable();
					},
					cancel:function(){
					return
					}				
					})
				
		
		}
		}else{
	
		setTimeout("calling()",2000);
					setBtnDisable();
		
		}
		}else{
		
		setTimeout("calling()",2000);
					setBtnDisable();
		
		
		}
	
		}
	
	
	
	
	function setBtnDisable() {
		var disabSeconds = 10;//设置变灰延时时间，单位：秒
		document.getElementById("calltext").innerText = "叫    号(" + disabSeconds + ")";
		document.getElementById("callButton").disabled = true;
		document.getElementById("passButton").disabled = true;
		document.getElementById("callNumServicing").disabled = true;
		document.getElementById("againButton").disabled = true;
		document.getElementById("eveluateButton").disabled = true;
		$("#switch").prop("disabled",true);
		/* $("#switchbutton").addClass("notclick"); */
		for (i = 1; i <= disabSeconds; i++) {
			window.setTimeout("update(" + i + ")", i * 1000);
		}
		window.setTimeout("timer()", disabSeconds * 1000);
	}
	function update(num, value) {
		if (num == (wait / 1000)) {
			document.getElementById("calltext").innerText = "叫    号";
		} else {
			printnr = (wait / 1000) - num;
			document.getElementById("calltext").innerText = "叫    号(" + printnr + ")";
		}
	}
	function timer() {
		document.getElementById("callButton").disabled = false;
		document.getElementById("calltext").innerText = "叫    号";
		document.getElementById("passButton").disabled = false;
		document.getElementById("callNumServicing").disabled = false;		
		var isContinue = $("input[name='isContinue']").val();
		if(isContinue==1){
			document.getElementById("againButton").disabled = false;
		}
		document.getElementById("eveluateButton").disabled = false;
		$("#switch").prop("disabled",false);
		/* $("#switchbutton").removeClass("notclick"); */
	}	
	
	function calling(){
 		var winNo = $("input[name='winNo']").val();
		AppUtil.ajaxNoProgress({
			url : "newCallController.do?isExpressItemDealing",
			params : {
				curWin : winNo
			},
			callback : function(resultJson) {
				if(resultJson.success){
					var businessCode = resultJson.jsonString;					
					limitCall(businessCode);
				}else{
					unLimitCall();
				}
			}
		});		
		setWaitNum();
	}
	
	var push={};
	function limitCall(businessCode){
 		var winNo = $("input[name='winNo']").val();
		var rows = $('#callingNewGrid').datagrid('getRows');
		if(rows){
			for(var i=0;i<rows.length;i++){
			    alert(rows[i].BUSINESS_CODE);
				if(businessCode.indexOf(rows[i].BUSINESS_CODE)!='-1'){
					continue;
				}else{
					if(rows[i].APPOINTCALL==0){
						parent.art.dialog({
							content : "当前等待记录未到可叫号时段",
							icon : "warning",
							time : 3,
							ok : true
						});
						return null;
					}
					AppUtil.ajaxProgress({
						url : "newCallController.do?callQueuing",
						params : {
							recordId : rows[i].RECORD_ID,
							lineNo : rows[i].LINE_NO,
							winNo : winNo,
							type : "callNum"
						},
						callback : function(resultJson) {
							 //当前叫号排第4位时，增加短信下发提醒(防止重复推送)
					         if(rows.length>=(i+4)){
					         	var callUser = $('#callingNewGrid').datagrid('getData').rows[i+3];
					         	if(!push[callUser.RECORD_ID]){
					         		$.post("<%=path%>/newCallController.do?sendMsgToUser", {
			                                user : JSON.stringify(callUser),
			                                lineNo:rows[i].LINE_NO,
			                                winNo:winNo},
			                            function(responseText, status, xhr) {
			                                var resultJson = $.parseJSON(responseText);
			                            }
			                        );	
			                       push[callUser.RECORD_ID]=true; 
					         	}
					        } 
							
							$("#callingNewGrid").datagrid("reload");
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
						}
					});
		
		            $.post("callController.do?notifyWxHasOpenId", {
		                    recordId : rows[i].RECORD_ID},
		                function(responseText, status, xhr) {
		                    var resultJson = $.parseJSON(responseText);
		                }
		            );
		            break;
				}
			}
		}else{			
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	
	function unLimitCall(){
 		var winNo = $("input[name='winNo']").val();
		var row = $('#callingNewGrid').datagrid('getData').rows[0];
		var rows = $('#callingNewGrid').datagrid('getData');
		//alert(row);
 		if (row) {
 			if(row.APPOINTCALL==0){
				parent.art.dialog({
					content : "当前等待记录未到可叫号时段",
					icon : "warning",
					time : 3,
					ok : true
				});
				return null;
			}
			AppUtil.ajaxProgress({
				url : "newCallController.do?callQueuing",
				params : {
					recordId : row.RECORD_ID,
					lineNo : row.LINE_NO,
					winNo : winNo,
					type : "callNum"
				},
				callback : function(resultJson) {
					//当前叫号排第4位时，增加短信下发提醒(防止重复推送)
			         if(rows.total>=4){
			         	var callUser = $('#callingNewGrid').datagrid('getData').rows[3];
			         	if(!push[callUser.RECORD_ID]){
			         		$.post("<%=path%>/newCallController.do?sendMsgToUser", {
	                                user : JSON.stringify(callUser),
	                                lineNo:row.LINE_NO,
	                                winNo:winNo},
	                            function(responseText, status, xhr) {
	                                var resultJson = $.parseJSON(responseText);
	                            }
	                        );	
	                       push[callUser.RECORD_ID]=true; 
			         	}
			        } 
			        
					$("#callingNewGrid").datagrid("reload");
					parent.art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						time : 3,
						ok : true
					});
				}
			});

            $.post("callController.do?notifyWxHasOpenId", {
                    recordId : row.RECORD_ID},
                function(responseText, status, xhr) {
                    var resultJson = $.parseJSON(responseText);
                }
            );

        }else{
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	
	//不动产待受理
	function bdcWaitAccept(index) {
		var row = $('#callingNewGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("newCallController.do?bdcWaitAccept&recordId="
					+ row.RECORD_ID, {
				title : "不动产待受理",
				width : "90%",
				height : "90%",
				lock : true,
				resize : false
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	
    //普通受理
    function commonAccept(index) {
        var row = $('#callingNewGrid').datagrid('getData').rows[index];
        var yctbsl = 1;
        if (row) {
            $.dialog.open("serviceItemController/selector.do", {
                title : "事项选择器",
                width : "1100px",
                lock : true,
                resize : false,
                height : "550px",
                close : function() {
                    var selectItemInfo = art.dialog.data("selectItemInfo");
                    if (selectItemInfo) {
                        var defKey = selectItemInfo.defKeys;
                        var itemCode = selectItemInfo.itemCodes;
                        art.dialog.removeData("selectItemInfo");
                        var url=encodeURI("executionController.do?goStart&defKey="
                            + defKey + "&itemCode=" + itemCode
                            + "&acceptway=" + yctbsl
								+ "&takenoway=" + row.TAKENOWAY
								+ "&lineId=" + row.RECORD_ID);
                        toUrl(url);
                    }
                }
            }, false);
        }else{
            parent.art.dialog({
                content : "当前无可办理记录",
                icon : "warning",
                time : 3,
                ok : true
            });
        }
    }
	//受理
	function accept(index) {
		var yctbsl = 1;
		var row = $('#callingNewGrid').datagrid('getData').rows[index];
		if (row) {
				if(row.IS_ITEM_CHOOSE=='0'){
								$.dialog.open("serviceItemController/yctbSelector.do", {
									title : "事项选择器",
									width : "1100px",
									lock : true,
									resize : false,
									height : "550px",
									close : function() {
										var selectItemInfo = art.dialog.data("selectItemInfo");
										if (selectItemInfo) {
											var defKey = selectItemInfo.defKeys;
											var itemCode = selectItemInfo.itemCodes;													
											art.dialog.removeData("selectItemInfo");
											var url=encodeURI("executionController.do?goStart&defKey="
				                                + defKey + "&itemCode=" + itemCode
				                                + "&acceptway=" + yctbsl
				                                + "&takenoway=" + row.TAKENOWAY
				                                + "&lineId=" + row.RECORD_ID);
											toUrl(url);
										}
									}
								}, false);
							}else{
								var url = "newCallController.do?selectedItemSelector&lineId=" + row.RECORD_ID;
								var uncodeUrl = encodeURI(url);
								$.dialog.open(uncodeUrl, {
									title : "待办理事项列表",
									width : "1100px",
									lock : true,
									resize : false,
									height : "550px",
									close : function() {
										afterRefresh();
									}
								}, false);
							}
			
			
		}else{
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	
	
	function toUrl(url,lineName){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var lineNameInput="<input name='lineName' type='hidden' value='"+lineName+"' />";
		$("#callingToolbarYctb").append(ssoForm);
		ssoForm.append(lineNameInput);
		ssoForm.submit();		
	}
	
	//过号
	function passCurrent() {
		pass(0);
	}
	function pass(index) {
		var row = $('#callingNewGrid').datagrid('getData').rows[index];
		if (row&&row.CALL_STATUS=='6') {						
			$.dialog.open("newCallController.do?passSeason&recordId="
					+ row.RECORD_ID, {
				title : "过号原因",
				width : "470px",
				height : "200px",
				lock : true,
				resize : false,
				close : function (){
					setTimeout("setWaitNum();",1000);
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无已叫号正在办理的记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	//咨询
	function dealzx(index) { 
		var row = $('#callingNewGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("newCallController.do?goQueuingDealzx&recordId="
					+ row.RECORD_ID, {
				title : "办件处理",
				width : "500px",
				height : "300px",
				lock : true,
				resize : false,
				close : function (){
					setTimeout("setWaitNum();",1000);
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	//领照
	function deallz(index) {  
		var row = $('#callingNewGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("newCallController.do?goQueuingDeallz&recordId="
					+ row.RECORD_ID, {
				title : "办件处理",
				width : "500px",
				height : "300px",
				lock : true,
				resize : false,
				close : function (){
					setTimeout("setWaitNum();",1000);
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无正在叫号的记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	//转发
	function forward(index) {
		var row = $('#callingNewGrid').datagrid('getData').rows[index];
		var url = "newCallController.do?goYctbQueuingForward&recordId="+row.RECORD_ID;
		if (row) {
			$.dialog.open(url, {
				title : "办件转发",
				width : "500px",
				height : "250px",
				lock : true,
				resize : false,
				close : function (){
					setTimeout("setWaitNum();",1000);
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无可办理记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	//信用报告查询
	function creditInquiry(){
		$.dialog.open("callController.do?showCreditInquiry", {
			title : "信用报告查询 详询:15980100998",
			width : "450px",
			height : "200px",
			lock: true,
			resize:false,
		}, false);
	}
	
	//窗口取号
	function bindScanCtrl(){
		$.dialog.open("webpage/callnew/queuing/VideoInputTakeNoCtl.jsp?", {
			title : "窗口取号",
			width:"810px",
			lock: true,
			resize:false,
			height:"100%",
			close: function () {
			}
		}, false);
	}
	//评价
	function evaluateInfo(){
		var row = $('#QueuingDayGrid').datagrid('getData').rows[0];
		if(row){
			if(row.EVALUATE==""||row.EVALUATE==undefined){
				if(row.CALL_STATUS==2){
					parent.art.dialog({
						content : "过号记录不能评价",
						icon : "error",
						time : 3,
						ok : true
					});
				}else{
					var recordId = row.RECORD_ID;
					$.dialog.open("newCallController.do?evaluateInfo&recordId=" + recordId, {
						title : "？评价",
						width : "380px",
						height : "130px",
						lock : true,
						resize : false,
						close: function(){
							tellerInfo();
							$("#QueuingDayGrid").datagrid("reload");
						}
					}, false);
				}
			}else{
				parent.art.dialog({
					content : "本次办理记录已评价，请勿重复评价",
					icon : "warning",
					time : 3,
					ok : true
				});
			}
		}else{
			parent.art.dialog({
				content : "当前无可评价记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	//是否评价
	function iSEvaluate(){
		var row = $('#QueuingDayGrid').datagrid('getData').rows[0];
		console.log(row)
	
			if(row.EVALUATE==""){
				
					/* parent.art.dialog({
						content : "过号记录不能评价",
						icon : "error",
						time : 3,
						ok : true */
	}				
	}
	//请稍等
	function waiting() {
 		var winNo = $("input[name='winNo']").val();
		AppUtil.ajaxProgress({
			url : "newCallController.do?callQueuing",
			params : {
				winNo : winNo,
				type : "CallingWait"
			},
			callback : function(resultJson) {
				/* var ele = $("#fath").children(".move");
				ele.animate({
					left : "0"
				}, 300, function() {
					ele.attr("data-state", "off");
				});
				$("#fath").removeClass("on").addClass("off");
				$("#winStatu").html("窗口状态：暂停服务"); */				
				$("#switch").prop("checked",false);
				parent.art.dialog({
					content : resultJson.msg,
					icon : "succeed",
					time : 3,
					ok : true
				});
			}
		});
		setServicingBtnDisable();
	}
	var waitsecs = 50*1000;
	function setServicingBtnDisable(){
		var disabSeconds = 50;//设置变灰延时时间，单位：秒
		document.getElementById("servicetext").innerText = "暂停服务(" + disabSeconds + ")";
		document.getElementById("callButton").disabled = true;
		document.getElementById("passButton").disabled = true;
		document.getElementById("callNumServicing").disabled = true;
		document.getElementById("againButton").disabled = true;
		document.getElementById("eveluateButton").disabled = true;
		$("#switch").prop("disabled",true);
		/* $("#switchbutton").addClass("notclick"); */
		for (i = 1; i <= disabSeconds; i++) {
			window.setTimeout("updateServicing(" + i + ")", i * 1000);
		}
		window.setTimeout("timerServicing()", disabSeconds * 1000);
	}
	function updateServicing(num, value) {
		if (num == (waitsecs / 1000)) {
			document.getElementById("servicetext").innerText = "暂停服务";
		} else {
			printnr = (waitsecs / 1000) - num;
			document.getElementById("servicetext").innerText = "暂停服务(" + printnr + ")";
		}
	}
	function timerServicing() {
		/* $("#switchbutton").removeClass("notclick"); */
		$("#switch").prop("disabled",false);
		document.getElementById("servicetext").innerText = "暂停服务";
		document.getElementById("callButton").disabled = false;	
		document.getElementById("passButton").disabled = false;
		//document.getElementById("callNumServicing").disabled = false;	
		var isContinue = $("input[name='isContinue']").val();
		if(isContinue==1){
			document.getElementById("againButton").disabled = false;
		}
		document.getElementById("eveluateButton").disabled = false;
	}
	//服务
	function servicing() {
 		var winNo = $("input[name='winNo']").val();
		AppUtil.ajaxProgress({
			url : "newCallController.do?callQueuing",
			params : {
				winNo : winNo,
				type : "CallingService"
			},
			callback : function(resultJson) {
				/* var ele = $("#fath").children(".move");
				ele.animate({
					left : '60px'
				}, 300, function() {
					$("#fath").attr("data-state", "on");
				});
				$("#fath").removeClass("off").addClass("on");
				$("#winStatu").html("窗口状态：工作中"); */
								
				document.getElementById("callButton").disabled = false;
				document.getElementById("passButton").disabled = false;
				document.getElementById("callNumServicing").disabled = false;	
				var isContinue = $("input[name='isContinue']").val();
				if(isContinue==1){
					document.getElementById("againButton").disabled = false;
				}
				document.getElementById("eveluateButton").disabled = false;
				parent.art.dialog({
					content : resultJson.msg,
					icon : "succeed",
					time : 3,
					ok : true
				});
			}
		});
	}
	
	//受理回调函数
	function afterRefresh(){
		AppUtil.gridDoSearch('callingToolbarYctb','callingNewGrid');
		AppUtil.gridDoSearch('QueuingDayToolbarYctb','QueuingDayGrid');
		setTimeout("setWaitNum();",1000);
	}
	
	function toogle(th) {
		var ele = $(th).children(".move");
		if (ele.attr("data-state") == "on") {
			waiting();
		} else if (ele.attr("data-state") == "off") {
			servicing();
		}
	}
	function acceptAgain(){
		var yctbsl = 1;
		var row = $('#QueuingDayGrid').datagrid('getData').rows[0];
		if(row&&(row.CALL_STATUS==1||row.CALL_STATUS==7)){
			$.dialog.open("serviceItemController/yctbSelector.do", {
				title : "事项选择器",
				width : "1100px",
				lock : true,
				resize : false,
				height : "550px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						var defKey = selectItemInfo.defKeys;
						var itemCode = selectItemInfo.itemCodes;													
						art.dialog.removeData("selectItemInfo");
						var url=encodeURI("executionController.do?goStart&defKey="
                            + defKey + "&itemCode=" + itemCode
                            + "&acceptway=" + yctbsl
								+ "&takenoway=" + row.TAKENOWAY
								+ "&lineId=" + row.RECORD_ID);
						toUrl(url);
					}
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无可继续受理的记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
	
	function yctbPassAccept(index){		
		var yctbsl = 1;
		var row = $('#QueuingDayGrid').datagrid('getData').rows[index];
		if(row.IS_ITEM_CHOOSE=='0'){
			$.dialog.open("serviceItemController/yctbSelector.do", {
				title : "事项选择器",
				width : "1100px",
				lock : true,
				resize : false,
				height : "550px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						var defKey = selectItemInfo.defKeys;
						var itemCode = selectItemInfo.itemCodes;													
						art.dialog.removeData("selectItemInfo");
						var url=encodeURI("executionController.do?goStart&defKey="
                               + defKey + "&itemCode=" + itemCode
                               + "&acceptway=" + yctbsl
                               + "&takenoway=" + row.TAKENOWAY
                               + "&lineId=" + row.RECORD_ID);
						toUrl(url);
					}
				}
			}, false);
		}else{
			var url = "newCallController.do?selectedItemSelector&lineId=" + row.RECORD_ID;
			var uncodeUrl = encodeURI(url);
			$.dialog.open(uncodeUrl, {
				title : "待办理事项列表",
				width : "1100px",
				lock : true,
				resize : false,
				height : "550px",
				close : function() {
					afterRefresh();
				}
			}, false);
		}
							
	}
</script>

<div class="easyui-layout eui-in-box" fit="true">	
	<div data-options="region:'west'" style="width:85%;">
		
		<div id="callingToolbarYctb">
			<div class="right-con">
				<div style="background:#fff;height:40px;line-height:40px;padding:0 10px;" class="clearfix">
					<span style="font-size: 18px;color: #005588;float: left;" id="winInfo"></span>
					<span style="font-size: 16px;color: #333;float: right;"><img src="webpage/callnew/queuing/image/waiticon.png"
						style="padding:0 10px;"/>当前办理人：${sessionScope.curLoginUser.fullname}
					 | 有<font id="waitNum" style="color:#3e8bff;">0</font>位正在排队等待办理</span>
				</div>
			</div>	
			<form action="#" name="callingForm">
				<input name="business" type="hidden" value=""/>
				<input name="winNo" type="hidden" value=""/>
				<input name="isContinue" type="hidden" value=""/>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"  
			id="callingNewGrid" nowrap="false" style="height: 50%;width: 100%;"
			toolbar="#callingToolbarYctb" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="false" border="false"
			url="newCallController.do?yctbQueuingGrid">
			<thead>
				<tr>
					<th data-options="field:'RECORD_ID',hidden:true" >RECORD_ID</th>
					<th data-options="field:'TAKENOWAY',hidden:true" >TAKENOWAY</th>
					<th data-options="field:'APPOINTCALL',hidden:true" >APPOINTCALL</th>
					<th data-options="field:'LINE_NO',align:'center'" width="10%">排队号</th>
					<th data-options="field:'LINE_NAME',align:'center'" width="10%">姓名</th>
					<th data-options="field:'LINE_ZJLX',hidden:true" >LINE_ZJLX</th>
					<th data-options="field:'LINE_CARDNO',align:'center',formatter:zjformater" width="20%" >证件号码</th>
					<th data-options="field:'CALL_STATUS',align:'center',formatter:statusformater" width="10%" >状态</th>
					<th data-options="field:'CREATE_TIME',align:'center'" width="16%" >取号时间</th>
					<th data-options="field:'id',formatter:rowformater" width="32%">操作</th>
				</tr>
			</thead>
		</table>
		<!-- =========================查询面板开始========================= -->
		<div id="QueuingDayToolbarYctb">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						    <div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;窗口已办理记录（当天）
							</div>
						</div>
					</div>
				</div>
			</div>			
			<form action="#" name="QueuingDayForm">
				<input name="doneWinNo" type="hidden" value=""/>
			</form>	
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="QueuingDayGrid"  nowrap="false" style="height: 50%;"
			toolbar="#QueuingDayToolbarYctb" method="post" idfield="RECORD_ID"
			checkonselect="false" selectoncheck="false" fit="false" border="false"
			url="newCallController.do?queuingDoneDayGrid">
			<thead>
				<tr>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'TAKENOWAY',hidden:true">TAKENOWAY</th>
					<th data-options="field:'APPOINTCALL',hidden:true">APPOINTCALL</th>
					<th data-options="field:'LINE_NO',align:'center'" width="9%">排队号</th>
					<th data-options="field:'LINE_NAME',align:'center'" width="10%">姓名</th>
					<th data-options="field:'LINE_ZJLX',hidden:true" >证件类型</th>
					<th data-options="field:'LINE_CARDNO',align:'center',formatter:zjformater" width="16%" >证件号码</th>
					<th data-options="field:'CREATE_TIME',align:'center'" width="15%" >取号时间</th>
					<th data-options="field:'OPERATOR_NAME',align:'center'" width="10%" >办理人</th>
					<th data-options="field:'OPER_TIME',align:'center'" width="15%" >办理时间</th>
					<th data-options="field:'CALL_STATUS',align:'center',formatter:statusformater" width="11%" >状态</th>
					<th data-options="field:'EVALUATE',align:'center',formatter:evaluateformater" width="13%" >评价/过号受理</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
	<div data-options="region:'east'" style="width:15%;position:relative;">
	<!-- <div>
		<div class="call_buttons" style="text-align: center;height:40px;">
			<div class="switch" id="switchbutton" style="width:82px !important;margin-left:25px;">
			  	<div class="btn_fath clearfix on" id="fath" onclick="toogle(this)">
			    	<div class="move" data-state="on"></div>
			    	<div class="btnSwitch btn1">ON</div>
			    	<div class="btnSwitch btn2 ">OFF</div>
			  	</div>
			</div>
			<span style="font-size: 12px;color: #2ba4d9;" id="winStatu"></span>
		</div>
		
		<div style="position:absolute;text-align: center;">
			<input type="button" value="叫  号" id="callButton" onclick="callCurrent();" class="callbutton" onmousemove="this.className='callbutton_in'" onmouseout="this.className='callbutton'">
			<input type="button" value="过  号" id="passButton" onclick="passCurrent();" class="callbutton" onmousemove="this.className='callbutton_in'" onmouseout="this.className='callbutton'">
			<input type="button" value="暂停服务" id="callNumServicing" onclick="waiting();" class="callbutton" onmousemove="this.className='callbutton_in'" onmouseout="this.className='callbutton'">
			<input type="button" value="继续受理" id="againButton" onclick="acceptAgain();" class="callbutton" onmousemove="this.className='callbutton_in'" onmouseout="this.className='callbutton'">
			<input type="button" value="评  价" id="eveluateButton" onclick="evaluateInfo();" class="callbutton" onmousemove="this.className='callbutton_in'" onmouseout="this.className='callbutton'">
		</div>
		
		<div style="position:absolute;bottom:0;text-align: center;border-top-style: solid;border-top-color: #d9d9d9;border-top-width: 1px;">
			<a href="javascript:;" class="easyui-linkbutton" reskey="Video_takeNo" onclick="bindScanCtrl();" style="background-color:#2ba4d9;width: 135px;height:36px;margin-top: 5px;margin-bottom: 5px;">取    号</a>
			<a href="javascript:;" class="easyui-linkbutton" reskey="CREDITINQUIRY" onclick="creditInquiry();" style="background-color:#2ba4d9;width: 135px;height:36px;margin-top: 5px;margin-bottom: 5px;">信用报告查询</a>
		 	
		</div>
	</div> -->
		<div>
			<div class="eui-switchck">

				<input id="switch" class="eui-switchckbox" type="checkbox" checked="checked" name="interest" value="1">
				<label for="switch"></label><span>开启叫号服务</span>
			</div>
			<div class="eui-rightbtn">
				<button id="callButton" onclick="callCurrent();"><i class="eui-jhicon-1"></i> <font id="calltext">叫&nbsp;&nbsp;&nbsp;&nbsp;号<font></button>
				<button id="passButton" onclick="passCurrent();"><i class="eui-jhicon-2"></i> 过&nbsp;&nbsp;号</button>
				<button id="callNumServicing" onclick="waiting();"><i class="eui-jhicon-3"></i> <font id="servicetext">暂停服务<font></button>
				<button id="againButton" onclick="acceptAgain();"><i class="eui-jhicon-5"></i> 继续受理</button>
				<button id="eveluateButton" onclick="evaluateInfo();"><i class="eui-jhicon-6"></i> 评&nbsp;&nbsp;价</button>
			</div>

			<ul class="eui-rbottombtn">
				<a href="javascript:;" onclick="bindScanCtrl();" ><li>取&nbsp;&nbsp;号</li></a>
				<a href="javascript:;" onclick="creditInquiry();" ><li>信用报告查询</li></a>
			</ul>
		</div>
	</div>
</div>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>