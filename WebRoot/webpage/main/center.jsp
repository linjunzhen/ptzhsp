<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%
	SysUser sysUser = AppUtil.getLoginUser();
	//获取登录用户的角色CODE
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    boolean isYCTB = roleCodes.contains("YCTB");
    //判断是否商事登记受理员
    boolean isSsdjsly = roleCodes.contains("ssdjsly");
    boolean jjh = roleCodes.contains("jjh");
    boolean isSsdjspry = roleCodes.contains("shdjspry");
	//判断是否商事登记受理员
	if("__ALL".equals(resKey)||isSsdjsly||isSsdjspry){
	    request.setAttribute("isSsdj",true);
	}else{
	    request.setAttribute("isSsdj",false);
	}
	if("__ALL".equals(resKey)||isYCTB){
	    request.setAttribute("isYCTB",true);
	}else{
	    request.setAttribute("isYCTB",false);
	}
	if(jjh){
	    request.setAttribute("jjh",true);
	}
	String  depCode = sysUser.getDepCode();
	request.setAttribute("depCode",depCode);
%>
<head>
<link rel="stylesheet" type="text/css"
	href="plug-in/easyui-1.4/extension/jquery-easyui-portal/portal.css" />
<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/jquery-easyui-portal/jquery.portal.js"></script>
<script type="text/javascript" src="webpage/main/js/center.js"></script>
<script type="text/javascript">
	$(function(){
		var depCode= '${depCode}';
		if(depCode && (depCode=='ZZSDAYZZZFWYXGS' || depCode=='PTZHSYQXCYZZZYXGS')){//商事供应商隐藏我的门户
			$("#homeportal").hide();
		}
// 		var isYCTB= '${isYCTB}';
// 		if(isYCTB=='true'){
		//if(true){
        	//time=setInterval("showAd()",60000);
		//}
		//叫号页面关闭事件
		$('#centerTabpanel').tabs({
			onBeforeClose: function(title,index){
				if(title.indexOf('窗口叫号')!='-1'){
					var winNo = $("input[name='winNo']").val();
					if(winNo!=""&&winNo!=undefined){
						AppUtil.ajaxProgress({
							url : "newCallController.do?clearWinSelect",
							params : {
								winNo : winNo
							},
							callback : function(resultJson) {
								
							}
						});
					}
				}
			}
		});
		$("#homeportal").portal({
			border:false,
			width:1000,
			fit:true
		});
		var jjh= '${jjh}';
		if(jjh=='true'){
			$("#needMeHandleList").attr("hidden",'true');
		}
		if(true){
			//添加标准化建设数据列表
			var bzhjs=$("#bzhjsMyPortal");
			bzhjs.panel({
				width:1000,
				height:210,
				closable: false,
				collapsible: true
			});
			$('#homeportal').portal('add', {
				panel: bzhjs,
				columnIndex: 0
			});
			loadBzhjsDatas();
		}
		if(true){
			//添加投资项目待办理列表
			var tzxm=$("#tzxmNeedMeHandle");
			tzxm.panel({
				width:1000,
				height:210,
				closable: false,
				collapsible: true
			});
			// add the panel to portal
			$('#homeportal').portal('add', {
				panel: tzxm,
				columnIndex: 0
			});
			loadTzxmDatas();
		}
// 		if(isYCTB=='true'){
		if(true){
			//添加一窗通办即办预警列表
			var zzhy=$("#yctbWarningNeedMeHandle");
			zzhy.panel({
				width:1000,
				height:210,
				closable: false,
				collapsible: true
			});
			// add the panel to portal
			$('#homeportal').portal('add', {
				panel: zzhy,
				columnIndex: 0
			});
			loadYctbProcessWarningDatas();
		}
		var isSsdj= '${isSsdj}';
		if(isSsdj=='true'){
			//添加商事待办列表
			var zzhy=$("#zzhyNeedMeHandle");
			zzhy.panel({
				//title: '<font size=\"16\">系统公告</font>',
				width:1000,
				height:210,
				closable: false,
				collapsible: true
			});
			// add the panel to portal
			$('#homeportal').portal('add', {
				panel: zzhy,
				columnIndex: 0
			});
			//添加商事待预审列表
			var zzhy=$("#zzhyDwysNeedMeHandle");
			zzhy.panel({
				//title: '<font size=\"16\">系统公告</font>',
				width:1000,
				height:210,
				closable: false,
				collapsible: true
			});
			// add the panel to portal
			$('#homeportal').portal('add', {
				panel: zzhy,
				columnIndex: 0
			});
			loadByZzhyMeHandledTaskDatas();
			loadByZzhyDwysDatas();
		}
		//添加办件预警
		var mid=$("#processWarning");
		mid.panel({
		    //title: '<font size=\"16\">系统公告</font>',
		    width:1000,
		    height:210,
		    closable: false,
		    collapsible: true
		});
		// add the panel to portal
		$('#homeportal').portal('add', {
		    panel: mid,
		    columnIndex: 0
		});
		
		var p=$("#systemContent");
		p.panel({
		    //title: '<font size=\"16\">系统公告</font>',
		    width:1000,
		    height:210,
		    closable: false,
		    collapsible: true
		});
		// add the panel to portal
		$('#homeportal').portal('add', {
		    panel: p,
		    columnIndex: 0
		});
		
		loadByMeHandledTaskDatas();
		loadProcessWarningDatas();
		loadSysNoticeDatas();
		
		$("#homeportal").portal("resize");
	});
      //2.书写显示图片的函数
      function showAd(){
		var curLoginUserAccount = $("input[name='curLoginUserAccount']").val()+"1";
		AppUtil.ajaxNoProgress({
// 		AppUtil.ajaxProgress({
			url: "flowTaskController.do?YctbWarningNum",
			params:{
				curLoginUserAccount: curLoginUserAccount
			},
			callback:function(resultJson){
				if(resultJson.msg=="操作成功"||resultJson.msg==""){
// 					loadYctbProcessWarningDatas();
				}else{
				  parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
// 					loadYctbProcessWarningDatas();
				}
			}
		});
      }
	//获取待办流程数据
	function loadByMeHandledTaskDatas(){
		var curLoginUserAccount = $("input[name='curLoginUserAccount']").val();
		$.post("flowTaskController.do?needMeHandle&start=0&limit=2",null,
		function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#HandledByMeTaskUl");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var i=0;i<rows.length;i++){
					if(i<2){
						var taskInfo = rows[i];
						var taskId = taskInfo.TASK_ID;
						var exeId = taskInfo.EXE_ID;
						var subject = taskInfo.SUBJECT;
						var createTime = taskInfo.CREATE_TIME;
						var Immediate = taskInfo.IS_IMMEDIATE;
						var workDayCount = taskInfo.LEFT_WORKDAY;
						//获取用户撤办状态
						var revokeStatus = taskInfo.REVOKE_STATUS;
						if(revokeStatus==0){
							 subject = "<font color='red'>【用户申请撤办】</font>"+subject;
						}
						var li = "<li >";
						li+=("<span>"+createTime+"</span>");
						li+="<font class=\"eui-portal-info\">";
						if(workDayCount==-1){
							li+="已超期";
						}else if(Immediate==1){
							li+="即办";
						}else if(workDayCount==0){
							if(Immediate==2){							
								li+="[A]";
							}
							li+="今天截止";
						}else if(workDayCount!=-2){
							if(Immediate==2){							
								li+="[A]";
							}
							li+=("剩余"+workDayCount+"个工作日");
						}
						li+="</font>";
						li+=("<a href='javascript:void(0)'  onclick=\"isRevoke('"+exeId+"','"+taskId+"');\"");
						li+=("' >"+subject+"</a></li>");
						newHtmlContent+=li;		
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'NeedMeHandleView\',\'待我审批\',\'executionController.do?goNeedMeHandle\');">点击查看更多待办流程>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
		
	}
	//获取标准化建设数据
	function loadBzhjsDatas(){
		$.post("flowTaskController.do?bzhjsMyPortal&start=0&limit=3",null,
			function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#bzhjsList");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var i=0;i<rows.length;i++){
					if(i<3){
						var resultInfo = rows[i];
						var contentId = resultInfo.CONTENT_ID;
						var contentTitle = resultInfo.CONTENT_TITLE;
						var releaseTime = resultInfo.RELEASE_TIME;
						var li = "<li>";
						li+=("<span>"+releaseTime+"</span>");
						li+=("<a href='javascript:void(0)'  onclick=\"showBzhjsContent('"+contentId+"');\"");
						li+=("' >"+contentTitle+"</a></li>");
						newHtmlContent+=li;
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多数据" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'bzhjsMyPortalView\',\'标准化建设\',\'moduleController.do?goBzhjsMyPortal\');">点击查看更多数据>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
		
	}
	//获取待办流程数据
	function loadTzxmDatas(){
		var curLoginUserAccount = $("input[name='curLoginUserAccount']").val();
		$.post("flowTaskController.do?tzxmNeedMeHandle&start=0&limit=2&isHaveHandup=true",null,
			function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#tzxmList");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var i=0;i<rows.length;i++){
					if(i<2){
						var taskInfo = rows[i];
						var taskId = taskInfo.TASK_ID;
						var exeId = taskInfo.EXE_ID;
						var subject = taskInfo.SUBJECT;
						var createTime = taskInfo.CREATE_TIME;
						var Immediate = taskInfo.IS_IMMEDIATE;
						var workDayCount = taskInfo.LEFT_WORKDAY;
						//获取用户撤办状态
						var revokeStatus = taskInfo.REVOKE_STATUS;
						if(revokeStatus==0){
							 subject = "<font color='red'>【用户申请撤办】</font>"+subject;
						}
						var li = "<li >";
						li+=("<span>"+createTime+"</span>");
						li+="<font class=\"eui-portal-info\">";
						if(workDayCount==-1){
							li+="已超期";
						}else if(Immediate==1){
							li+="即办";
						}else if(workDayCount==0){
							if(Immediate==2){							
								li+="[A]";
							}
							li+="今天截止";
						}else if(workDayCount!=-2){
							if(Immediate==2){							
								li+="[A]";
							}
							li+=("剩余"+workDayCount+"个工作日");
						}
						li+="</font>";
						li+=("<a href='javascript:void(0)'  onclick=\"isRevoke('"+exeId+"','"+taskId+"');\"");
						li+=("' >"+subject+"</a></li>");
						newHtmlContent+=li;
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'tzxmNeedMeHandleView\',\'工程建设项目流程\',\'executionController.do?goTzxmNeedMeHandle\');">点击查看更多待办流程>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
		if('${isSsdj}'=='true'){
			loadByZzhyMeHandledTaskDatas();
			loadByZzhyDwysDatas();
		}
		
	}
	function loadByZzhyMeHandledTaskDatas(){
		var curLoginUserAccount = $("input[name='curLoginUserAccount']").val();
		$.post("flowTaskController.do?zzhyNeedMeHandle&Q_T.TASK_NODENAME_NEQ=%e7%aa%97%e5%8f%a3%e5%8a%9e%e7%90%86&Q_E.APPLY_STATUS_NEQ=1&start=0&limit=2",null,
				function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#zzhyHandledByMeTaskUl");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var i=0;i<rows.length;i++){
					if(i<2){
						var taskInfo = rows[i];
						var taskId = taskInfo.TASK_ID;
						var exeId = taskInfo.EXE_ID;
						var subject = taskInfo.SUBJECT;
						var createTime = taskInfo.CREATE_TIME;
						var Immediate = taskInfo.IS_IMMEDIATE;
						var workDayCount = taskInfo.LEFT_WORKDAY;
						var isYs = taskInfo.isYs;
						var li = "<li >";
						li+=("<span>"+createTime+"</span>");
						li+="<font class=\"eui-portal-info\">";
						if(isYs==true){
							if(workDayCount==-1){
								li+="已超期";
							}else if(Immediate==1){
								li+="即办";
							}else if(workDayCount==0){
								if(Immediate==2){							
									li+="[A]";
								}
								li+="今天截止";
							}else if(workDayCount!=-2){
								if(Immediate==2){							
									li+="[A]";
								}
								li+=("剩余"+workDayCount+"个工作日");
							}
						}else if(workDayCount<=180){							
							var time = Number(180)-Number(workDayCount);
							li+=("剩余"+time+"分钟");
						}else if(workDayCount>180){
							li+="已超期";
						}
						li+="</font>";
						li+=("<a href='javascript:void(0)'  onclick=\"isRevoke('"+exeId+"','"+taskId+"');\"");
						li+=("' >"+subject+"</a></li>");
						newHtmlContent+=li;		
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'shdsp\',\'待审批\',\'executionController.do?goZzhyDwsp\');">点击查看更多待办流程>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
	}
	function loadByZzhyDwysDatas(){
		var curLoginUserAccount = $("input[name='curLoginUserAccount']").val();
		$.post("flowTaskController.do?zzhyNeedMeHandle&Q_E.APPLY_STATUS_EQ=1&start=0&limit=2",null,
				function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#zzhyDwys");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var i=0;i<rows.length;i++){
					if(i<2){
						var taskInfo = rows[i];
						var taskId = taskInfo.TASK_ID;
						var exeId = taskInfo.EXE_ID;
						var subject = taskInfo.SUBJECT;
						var createTime = taskInfo.CREATE_TIME;
						var Immediate = taskInfo.IS_IMMEDIATE;
						var workDayCount = taskInfo.LEFT_WORKDAY;
						var isYs = taskInfo.isYs;
						var li = "<li >";
						li+=("<span>"+createTime+"</span>");
						li+="<font class=\"eui-portal-info\">";
						if(isYs==true){
							if(workDayCount==-1){
								li+="已超期";
							}else if(Immediate==1){
								li+="即办";
							}else if(workDayCount==0){
								if(Immediate==2){							
									li+="[A]";
								}
								li+="今天截止";
							}else if(workDayCount!=-2){
								if(Immediate==2){							
									li+="[A]";
								}
								li+=("剩余"+workDayCount+"个工作日");
							}
						}else if(workDayCount<=180){							
							var time = Number(180)-Number(workDayCount);
							li+=("剩余"+time+"分钟");
						}else if(workDayCount>180){
							li+="已超期";
						}
						li+="</font>";
						li+=("<a href='javascript:void(0)'  onclick=\"isRevoke('"+exeId+"','"+taskId+"');\"");
						li+=("' >"+subject+"</a></li>");
						newHtmlContent+=li;		
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'zzhyDwys\',\'待预审\',\'executionController.do?goZzhyDwys\');">点击查看更多待办流程>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
	}
	//获取办件预警数据
	function loadProcessWarningDatas(){
		var curLoginUserAccount = $("input[name='curLoginUserAccount']").val();
		$.post("flowTaskController.do?processWarningdata&start=0&limit=2",null,
				function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#ProcessWarningUl");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var i=0;i<rows.length;i++){
					if(i<2){
						var taskInfo = rows[i];
						var taskId = taskInfo.TASK_ID;
						var exeId = taskInfo.EXE_ID;
						var subject = taskInfo.SUBJECT;
						var createTime = taskInfo.CREATE_TIME;
						var Immediate = taskInfo.IS_IMMEDIATE;
						var workDayCount = taskInfo.LEFT_WORKDAY;
						var li = "<li >";
						li+=("<span>"+createTime+"</span>");
						li+="<font class=\"eui-portal-info\">";
						if(workDayCount==-1){
							li+="已超期";
						}else if(Immediate==1){
							li+="即办";
						}else if(workDayCount==0){
							if(Immediate==2){							
								li+="[A]";
							}
							li+="今天截止";
						}else if(workDayCount!=-2){
							if(Immediate==2){							
								li+="[A]";
							}
							li+=("剩余"+workDayCount+"个工作日");
						}
						li+="</font>";
						li+= "<a href='executionController.do?goDetail&exeId="+exeId+"'  target='_blank'";
						li+=("' >"+subject+"</a></li>");
						newHtmlContent+=li;		
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'ProcessWarningView\',\'流程预警\',\'executionController.do?goProcessWarning\');">点击查看更多>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
	}
	
	//获取一窗通办即办办件预警数据
	function loadYctbProcessWarningDatas(){
		var curLoginUserAccount = $("input[name='curLoginUserAccount']").val();
		$.post("flowTaskController.do?needMeHandle&isHaveHandup=true&Q_E.ACCEPTWAY_EQ=1&Q_S.SXLX_EQ=1",null,
				function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#yctbWarning");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var i=0;i<rows.length;i++){
					if(i<2){
						var taskInfo = rows[i];
						var taskId = taskInfo.TASK_ID;
						var exeId = taskInfo.EXE_ID;
						var subject = taskInfo.SUBJECT;
						var createTime = taskInfo.CREATE_TIME;
						var leftMinute = taskInfo.LEFTMINUTE;
						var li = "<li >";
						li+=("<span>"+createTime+"</span>");
						if(leftMinute>15){
							li+="<font class=\"eui-portal-info\" style=\"color:#40bc1f;border-color:#40bc1f;\">";
							li+=("剩余"+leftMinute+"分钟");
							li+="</font>";
						}else if (leftMinute>10){
							li+="<font class=\"eui-portal-info\" style=\"color:#3e8bff;border-color:#3e8bff;\">";
							li+=("剩余"+leftMinute+"分钟");
							li+="</font>";
						}else if (leftMinute>5){
							li+="<font class=\"eui-portal-info\" style=\"color:#f3d754;border-color:#f3d754;\">";
							li+=("剩余"+leftMinute+"分钟");
							li+="</font>";
						}else if (leftMinute>3){
							li+="<font class=\"eui-portal-info\" style=\"color:#fa5800;border-color:#fa5800;\">";
							li+=("剩余"+leftMinute+"分钟");
							li+="</font>";
						}else if (leftMinute>0){
							li+="<font class=\"eui-portal-info\">";
							li+=("剩余"+leftMinute+"分钟");
							li+="</font>";
						}else if (leftMinute==0){
							li+="<font class=\"eui-portal-info\">";
							li+=("剩余不满1分钟");
							li+="</font>";
						}else{
							li+="<font class=\"eui-portal-info\">";
							li+=("超期"+Math.abs(leftMinute)+"分钟");
							li+="</font>";
						}
						li+= "<a href='executionController.do?goDetail&exeId="+exeId+"'  target='_blank'";
						li+=("' >"+subject+"</a></li>");
						newHtmlContent+=li;		
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'ProcessWarningView\',\'办件预警\',\'executionController.do?goProcessWarning\');">点击查看更多>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
	}
	function isRevoke(exeId,taskId){
		$.post("userInfoController/isRevoke.do",{
			exeId:exeId
		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success){
				sqcb(exeId,taskId)
			}else{
				toPostUrl("executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId);
			}
		});
	}
	function showBzhjsContent(contentId){
		toPostUrl("contentController/view.do?contentId="+contentId);
	}
	function toPostUrl(url){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		$("#homeportal").append(ssoForm);
		ssoForm.submit();		
	}
	function sqcb(exeId,taskId){
		$.dialog.open("executionController.do?goHandle&exeId="+exeId+"&taskId="+taskId, {
			title : "撤办审核",
			width : "600px",
			height : "240px",
			lock : true,
			resize : false,
			close: function(){	
				loadByMeHandledTaskDatas();
				loadProcessWarningDatas();
			}
		}, false);
	}
	//获取系统公告数据
	function loadSysNoticeDatas(){
		//var curLoginUserAccount = $("input[name='curLoginUserAccount']").val();
		$.post("sysNoticeController.do?indexView&start=0&limit=10",null,
				function(resultJson, status, xhr) {
			var HandledByMeTaskUl = $("#HandledByMeTaskUl2");
			HandledByMeTaskUl.html("");
			if(resultJson.total!=0){
				var rows = resultJson.rows;
				var newHtmlContent = "";
				for(var index in rows){
					if (index < 4) {
						var noticeInfo = rows[index];
						var noticeId = noticeInfo.NOTICE_ID;
						var exeId = noticeInfo.EXE_ID;
						var subject = noticeInfo.NOTICE_TITLE;
						var createTime = noticeInfo.CREATE_TIME;
						var workDayCount = noticeInfo.LEFT_WORKDAY;
						var li = "<li >";
						li+=("<span>"+createTime+"</span>");
						li+="<a href=\"javascript:void(0);\" onclick=\"showSysNoticeDetailWindow(\'"+noticeId+"\');\"";
						li+=(" >"+subject+"</a></li>");
						newHtmlContent+=li;
					}
				}
				newHtmlContent +='<li><a class="eui-portal-more" style="margin-top: 2px;" title="点击查看更多" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab(\'systemAnnouncement\',\'系统公告\',\'executionController.do?goSystemAnnouncement\');">点击查看更多>></a></li>';
				HandledByMeTaskUl.html(newHtmlContent);
			}
		});
	}

	/**
	 * 显示系统用户信息对话框
	 */
	function showSysNoticeDetailWindow(entityId) {
		//获取组织机构的ID
		var url = "sysNoticeController.do?goDetail&noticeId=" + entityId;
		$.dialog.open(url, {
    		title : "系统公告信息",
            width:"660px",
            lock: true,
            resize:false,
            height:"460px",
    	}, false);
	};
</script>
</head>
<div class="easyui-tabs" fit="true" border="false" tabHeight="37"
	id="centerTabpanel" plain="false">
	<input type="hidden" name="curLoginUserAccount" value="${sessionScope.curLoginUser.username}" />
	<div id="homeportal" fit="true" title="我的门户">	
		
		<div style="width:100%;" id="needMeHandle">
			<div class="main_con" id="needMeHandleList">
				<div class="main_title">
					<!-- <a  style="margin-top: 2px;" title="更多待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab('NeedMeHandleView','待我审批','executionController.do?goNeedMeHandle');" ><img src="webpage/main/images/ind_more.png" /></a> -->
					<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:#014781;">待办流程</span>
				</div>
				<div class="main_list1">
					<ul id="HandledByMeTaskUl">
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div style="width:100%;" id="bzhjsMyPortal">
	<div class="main_con">
		<div class="main_title">
			<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:#014781;">标准化建设</span>
		</div>
		<div class="main_list1">
			<ul id="bzhjsList">
			</ul>
		</div>
	</div>
</div>
<div style="width:100%;" id="tzxmNeedMeHandle">
	<div class="main_con">
		<div class="main_title">
			<!-- <a  style="margin-top: 2px;" title="更多工程建设项目流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab('tzxmNeedMeHandleView','工程建设项目流程','executionController.do?goTzxmNeedMeHandle');" ><img src="webpage/main/images/ind_more.png" /></a> -->
			<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:#014781;">工程建设项目流程</span>
		</div>
		<div class="main_list1">
			<ul id="tzxmList">
			</ul>
		</div>
	</div>
</div>
<div style="width:100%;" id="yctbWarningNeedMeHandle">
	<div class="main_con">
		<div class="main_title">
			<!-- <a  style="margin-top: 2px;" title="更多待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab('YctbNeedMeHandleWarningView','一窗通办即办预警','executionController.do?goYctbNeedMeHandleWarning');" ><img src="webpage/main/images/ind_more.png" /></a> -->
			<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:red;">一窗通办即办待办流程</span>
		</div>
		<div class="main_list1">
			<ul id="yctbWarning">
			</ul>
		</div>
	</div>
</div>
<div style="width:100%;" id="zzhyDwysNeedMeHandle">
	<div class="main_con">
		<div class="main_title">
			<!-- <a  style="margin-top: 2px;" title="更多商事待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab('zzhyDwys','待预审','executionController.do?goZzhyDwys');" ><img src="webpage/main/images/ind_more.png" /></a> -->
			<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:#014781;">商事待预审</span>
		</div>
		<div class="main_list1">
			<ul id="zzhyDwys">
			</ul>
		</div>
	</div>
</div>
<div style="width:100%;" id="zzhyNeedMeHandle">
	<div class="main_con">
		<div class="main_title">
			<!-- <a  style="margin-top: 2px;" title="更多商事待办流程" href="javascript:void(0);" onclick="AppUtil.addMenuToCenterTab('shdsp','待审批','executionController.do?goZzhyDwsp');" ><img src="webpage/main/images/ind_more.png" /></a> -->
			<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:#014781;">商事待审批</span>
		</div>
		<div class="main_list1">
			<ul id="zzhyHandledByMeTaskUl">
			</ul>
		</div>
	</div>
</div>
<div style="width:100%;" id="processWarning">
			<div class="main_con"> 
				<div class="main_title">
					<!-- <a  style="margin-top: 2px;" title="更多待办流程" href="javascript:void(0);" 
					onclick="AppUtil.addMenuToCenterTab('ProcessWarningView','办件预警','executionController.do?goProcessWarning');" >
					<img src="webpage/main/images/ind_more.png" /></a> -->
					<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:#014781;">办件预警</span>
				</div>  
				<div class="main_list1">
					<ul id="ProcessWarningUl">
					</ul>
				</div>
			</div>
</div>
<div style="width:100%;" id="systemContent">
			<div class="main_con"> 
				<div class="main_title">
					<!-- <a  style="margin-top: 2px;" title="更多待办流程" href="javascript:void(0);"
						onclick="AppUtil.addMenuToCenterTab('systemAnnouncement','系统公告','executionController.do?goSystemAnnouncement');" >
						<img src="webpage/main/images/ind_more.png" /></a> -->
					<span class="border_L1" style="font-size: 16px;line-height: 26px;height: 26px;padding: 0 0 0 5px;color:#014781;">系统公告</span>
				</div>  
				<div class="main_list1">
					<ul id="HandledByMeTaskUl2" class="main_ul1">
					</ul>
				</div>
			</div>
</div>
<div id="tabsMenu" class="easyui-menu" style="width:120px;">
	<div name="close">关闭标签</div>
	<div name="Other">关闭其它标签</div>
	<div name="All">关闭所有标签</div>
</div>



