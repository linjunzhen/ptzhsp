<%@page import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="webpage/main/js/dropdown.js"></script>
<link href="webpage/system/login/css/style1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function longChange(){
		var val=$("#isAcceptMsg").get(0).checked;
		if(val){
			var mobile=$("#mobile").val();
			if(mobile!=null&&mobile!=""){
				art.dialog.confirm("您确定需要进行修改该配置?", function() {
					flag=true;
    				saveUserInfo("1");
    			},function(){//执行取消操作
    				if($("#isAcceptMsg").prop("checked") == true){
        				$("input[name='isAcceptMsg']").prop("checked", false);
    				}else{
        				$("input[name='isAcceptMsg']").prop("checked", true);
    				}
    			});
			}else{
				setMobileInfo();
			}
		}else{
			art.dialog.confirm("您确定需要进行修改该配置?", function() {
				flag=true;
    			saveUserInfo("0");
    		},function(){//执行取消操作
    			if($("#isAcceptMsg").prop("checked") == true){
        				$("input[name='isAcceptMsg']").prop("checked", false);
    				}else{
        				$("input[name='isAcceptMsg']").prop("checked", true);
    				}
    		});
		}
	}
function saveUserInfo(val){
	var userId=$("#userId").val();
	AppUtil.ajaxProgress({
    			url : "sysUserController.do?updateAcceptMsg",
    			params : {
    				"userId" : userId,
    				"isAcceptMsg" : val
    			},
    			callback : function(resultJson) {
    				if (resultJson.success) {
    					art.dialog({
    						content : resultJson.msg,
    						icon : "succeed",
    						ok : true
    					});	
    					$("#newAcceptVal").val(val);
    				} else {
    					art.dialog({
    						content : resultJson.msg,
    						icon : "error",
    						ok : true
    					});
    				}
    			}
    		});
}
function setMobileInfo(){
		var userId=$("#userId").val();
        $.dialog.open("sysUserController.do?showMobile&userId"+userId, {
            title : "手机号码设置",
            width:"360px",
            lock: true,
            resize:false,
            height:"160px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='mobile']").val(selectDepInfo.mobile);
                    var html='<img src="webpage/system/login/images/icon10.png"> 手机：'+selectDepInfo.mobile+'';
                    $("#mobileLi").html(html);
                    saveUserInfo("1");
                }
            }
        }, false);
}


$(document).ready(function(){
    dropdownOpen();//调用
});
/**
 * 鼠标划过就展开子菜单
 */
function dropdownOpen() {

    var $dropdownLi = $('.eui-nav>dt.dropdown');

    $dropdownLi.mouseover(function() {
        $(this).addClass('open');
    }).mouseout(function() {
        $(this).removeClass('open');
    });
}
</script>
 <input type="hidden"
			value="${sessionScope.curLoginUser.loginUserInfoJson}"
			id="loginUserInfoJson" />

<div class="eui-header">
            <div class="eui-logo eui-lg"></div>
            <dl class="eui-nav">
            	<c:forEach var="topChildMenu" items="${topChildMenuList}">
					<c:if test="${topChildMenu.ishide==null}">
                		<dt class="dropdown"  ><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" role="button" aria-haspopup="true" aria-expanded="false" title="${topChildMenu.RES_NAME}"><%-- <img src="webpage/system/login/images/${topChildMenu.RES_KEY}.png">  --%><span>${topChildMenu.RES_NAME}</span><!-- <label></label> --></a>
                		<ul class="eui-navli1 dropdown-menu ">
                			<c:forEach var="secondMenu" items="${topChildMenu.secondMenuList}">
								<c:if test="${secondMenu.ishide==null}">
									<li onclick="changeLeft('${secondMenu.RES_KEY}','${secondMenu.RES_NAME}','${secondMenu.SUBSYSTEM_CODE}','${secondMenu.EXTERNAL_URL}')" id="${secondMenu.RES_KEY}"><a href="javascript:void(0);"><i class="${secondMenu.ICON_PATH}"></i>${secondMenu.RES_NAME}</a></li>
								</c:if>
                			</c:forEach>
                		</ul>
                		</dt>	
					</c:if>
            	</c:forEach>
            </dl>
            <div class="eui-system">
                <ul>
                	<li><a style="background: none;">技术支持:23162985/13290986578</a></li>
                    <li class="eui-location  dropdown"><a class="dropdown-toggle" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false" href="javascript:void(0);"  >
                    <c:choose>
                    	<c:when test="${sessionScope.curLoginUser.photo!=null }">
                    		<img id="userphoto" width="36" height="36" src="${_file_Server}${sessionScope.curLoginUser.photo}">
                    	</c:when>
                    	<c:otherwise>
                    		<img id="userphoto" width="36" height="36" src="webpage/system/login/images/icon7.png">
                    	</c:otherwise>
                    </c:choose>
                     ${sessionScope.curLoginUser.fullname}</a>
                    	<div class="eui-syscenter  dropdown-menu" >
                        	
                        	<ul>
                            	<li><i class="eicon-building"></i> 单位：${sessionScope.curLoginUser.depName}</li>
                                <li id="mobileLi"><i class="eicon-mobile"></i> 手机：${sessionScope.newMobileVal}</li>
                                <!-- 
                                <li><img src="webpage/system/login/images/icon11.png"> 邮箱：${sessionScope.curLoginUser.email}</li>  -->
                            	<li class="bdLi"><i class="eicon-envelope"></i> 是否接收短信：
                            		<input type="checkbox" id="isAcceptMsg" name="isAcceptMsg" onclick="longChange();" 
							          style="width:16px;padding-left: 0px;margin-left: 0px;" />
							        <input type="hidden" value="${sessionScope.newMobileVal}" id="mobile" name="mobile"/>
							        <input type="hidden" value="${sessionScope.curLoginUser.userId}" id="userId" />
							        <input type="hidden" value="${sessionScope.newAcceptVal}" id="newAcceptVal" />  
                            	</li>
	                            <li><a href="javascript:showModifyPassWin();"><i class="eicon-edit"></i> 修改密码</a></li>
	                            <li><a href="javascript:showHeadPortrait();"><i class="eicon-photo"></i> 头像设置</a></li>
	                            <li><a href="javascript:showExitWin();"><i class="eicon-power-off"></i> 退出用户</a></li>
                            
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
    </div>
<script type="text/javascript">
	//改变窗口大小时，触发导航文字显示与隐藏
	$(function(){
	//窗口改变大小事件
		$(window).resize(function() {
			var width = $(this).width();
			windowHeight(width);
		});
		/* if($(window).width()>1700){
			$('.eui-nav dt span').removeClass("wzspan");
			$('.eui-nav dt label').show();
			//$('.eui-logo').removeClass("eui-lg1").addClass("eui-lg");
		}else if($(window).width()>1100){
			$('.eui-nav dt span').addClass("wzspan");
			$('.eui-nav dt label').show();
			//$('.eui-logo').removeClass("eui-lg").addClass("eui-lg1");
		}else if($(window).width()<1100){
			$('.eui-nav dt span').addClass("wzspan");
			$('.eui-nav dt label').hide();
			//$('.eui-logo').removeClass("eui-lg").addClass("eui-lg1");
		} */
		if("${resKey}"!=""){
			$("#${resKey}").trigger("click");
		}else if($(".eui-navli1 li ").length>0){
			$(".eui-navli1 li:first ").trigger("click");
		}
		
		if ('${sessionScope.curLoginUser.isModifyPass}' == "-1") {
			$.dialog.open("sysUserController.do?showPass&isForce=true", {
				title : "必须修改初始登录密码",
				width : "400px",
				esc: false,//取消esc键  
				lock : true, 
				cancel : false,
				resize : false,
				height : "200px",
			}, false);
		}
		
		var fields = $("#newAcceptVal").val();
    	if(fields=="1"){
    		$("#isAcceptMsg").attr("checked","checked");
    	}
	});
	
	function windowHeight(width){
		/* if($(window).width()>1700){
			$('.eui-nav dt span').removeClass("wzspan");
			$('.eui-nav dt label').show();
			//$('.eui-logo').removeClass("eui-lg1").addClass("eui-lg");
		}else if($(window).width()>1100){
			$('.eui-nav dt span').addClass("wzspan");
			$('.eui-nav dt label').show();
			//$('.eui-logo').removeClass("eui-lg").addClass("eui-lg1");
		}else if($(window).width()<1100){
			$('.eui-nav dt span').addClass("wzspan");
			$('.eui-nav dt label').hide();
			//$('.eui-logo').removeClass("eui-lg").addClass("eui-lg1");
		} */
	} 
	
	function showModifyPassWin() {
		$.dialog.open("sysUserController.do?showPass", {
			title : "修改登录密码",
			esc: false,//取消esc键  
			width : "400px",
			lock : true,
			resize : false,
			height : "200px",
		}, false);
	}

	function showExitWin() {
		art.dialog.confirm("您确定要退出系统吗?", function() {
			window.top.location.href = "loginController.do?logout";
		}, function() {
		});
	}
	
	function changeLeft(key,systemName,subsystemCode,externalUrl){

		$(".eui-navli1 li ").each(function(index){
			$(this).removeClass("eui-navon1");
		});
		$(".eui-nav .dropdown-toggle ").removeClass('on');
		$(".eui-navli1 li#"+key).addClass("eui-navon1");
		$(".eui-navli1 li#"+key).parent('ul').siblings('a').addClass('on');
		$(".layout-panel-west .panel-header .panel-title").html(systemName);
		var layload = layer.load("正在提交请求中…");
		$.post( "loginController.do?findLeftList",{reskey:key},
			function(responseText, status, xhr) {
				layer.close(layload);
				if(responseText!="sessiontimeout"&&responseText!="loginrepeated"){
					var resultJson = $.parseJSON(responseText);
					setLeftList(resultJson);
				}
			}
		);
		
	}
	function setLeftList(itemList){
		$("#jquery-accordion-menu").html("");
		var leftHtml = "";
		leftHtml += "<div id=\"jquery-accordion-menu\" class=\"jquery-accordion-menu\"><ul>";
		for(var i=0; i<itemList.length; i++){
			
			if(itemList[i].ishide==null){
				if(itemList[i].hasChild){
					leftHtml += "<li><a href=\"#\">";
					/* if(i==0){
						leftHtml +="indnavOn\">";
					}else{
						leftHtml +="\">";
					} */
					leftHtml += "<i class=\""+itemList[i].ICON_PATH+"\"></i>"+itemList[i].RES_NAME;
					leftHtml += "</a>";
					leftHtml += "<ul class=\"jquery-accordion-submenu\" >";
					for(var j=0; j<itemList[i].secondMenuList.length; j++){
					   leftHtml += "<li><a href=\"javascript:void(0);\" ";
						    	leftHtml += "onclick=\"AppUtil.addMenuToCenterTab('"+itemList[i].secondMenuList[j].RES_KEY+
						    			"','"+itemList[i].secondMenuList[j].RES_NAME+"','"+itemList[i].secondMenuList[j].RES_URL
						    			+"','"+itemList[i].secondMenuList[j].LOAD_TYPE+"');\" >";
						    	leftHtml += itemList[i].secondMenuList[j].RES_NAME+"</a></li>";
					}
					leftHtml += "</ul></li>";
				}else{
					leftHtml += "<li><a <a href=\"javascript:void(0);\" ";
			    	leftHtml += "onclick=\"AppUtil.addMenuToCenterTab('"+itemList[i].RES_KEY+
	    			"','"+itemList[i].RES_NAME+"','"+itemList[i].RES_URL
	    			+"','"+itemList[i].LOAD_TYPE+"');\" >";
					leftHtml += "<i class=\""+itemList[i].ICON_PATH+"\"></i>"+itemList[i].RES_NAME;
					leftHtml += "</a>";
					leftHtml += "</li>";
				}
			}
			
		}
		leftHtml += "</ul></div>";
		$("div.eui-content").html(leftHtml);
		changeCss();
	}
	
	function showHeadPortrait(){
		$.dialog.open("sysUserController.do?showHeadPortrait", {
			title : "头像设置",
			esc: false,//取消esc键  
			width : "400px",
			lock : true,
			resize : false,
			height : "200px",
		}, false);
	}
</script>    
 