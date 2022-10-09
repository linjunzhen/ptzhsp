<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
    <title>用户中心--平潭综合实验区行政服务中心</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<script type="text/javascript" src="plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="plug-in/eveutil-1.0/AppUtil.js"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.loginL img,.main_b,.login_T ul li,.subnav ul li a img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
<style>
.usernowinfo1 a {
    color: #ffa103;
}
input {
    border: 1px solid #c9deef;
    height: 28px;
    line-height: 28px;
    padding: 0 5px;
    width: 90%;
}
.sel {
   /* width: 170px;
    height: 28px;
    line-height: 28px;*/
}
</style>
<!-- 引入验证库 -->
<eve:resources loadres="apputil,artdialog,json2,layer,validationegine,swfupload"></eve:resources>
<script type="text/javascript"> 
$(function(){
	
	$("#ZJLX").attr("disabled","disabled");
	$("#jglx").attr("disabled","disabled");
     //顶部导航切换
	$(".subnav li").click(function(){
		$(".subnav ul li").removeClass("subnavOn")
		$(this).addClass("subnavOn");
	})
	
	var username = '${sessionScope.curLoginMember.YHMC}';
	
	//${sessionScope.curLoginMember.ZJHM}//证件号码
	//${sessionScope.curLoginMember.SJHM}//手机
	//${sessionScope.curLoginMember.DHHM}
	if(${sessionScope.curLoginMember.USER_TYPE=='1'}){
		var zjhm = '${sessionScope.curLoginMember.ZJHM}';
		if(zjhm !== null && zjhm !== undefined && zjhm !== ''){
			zjhm=zjhm.split('');  //将a字符串转换成数组
			zjhm.splice(6,8,'********'); //将1这个位置的字符，替换成'xxxxx'. 用的是原生js的splice方法。
			var zjhmas = zjhm.join("");
			document.getElementById('zjhm').innerHTML=zjhmas;
		}
		var sjhm = '${sessionScope.curLoginMember.SJHM}';
		if(sjhm !== null && sjhm !== undefined && sjhm !== ''){
			sjhm=sjhm.split('');
			sjhm.splice(3,4,'****'); 
			var sjhmas = sjhm.join("");
			document.getElementById('sjhm').innerHTML=sjhmas;
		}
		var dhhm = '${sessionScope.curLoginMember.DHHM}';
		if(dhhm !== null && dhhm !== undefined && dhhm !== ''){
			dhhm=dhhm.split('');
			dhhm.splice(3,4,'****'); 
			var dhhmas = dhhm.join("");
			document.getElementById('dhhm').innerHTML=dhhmas;
		}
	}
	// <td>${sessionScope.curLoginMember.ZJHM}</td>  法人身份证
	//${sessionScope.curLoginMember.SJHM} 法人手机号码
	//${sessionScope.curLoginMember.DHHM} 法人电话号码
	//${sessionScope.curLoginMember.JBRSFZ}  经办人身份证
	if(${sessionScope.curLoginMember.USER_TYPE=='2'}){
		var frzjhm = '${sessionScope.curLoginMember.ZJHM}';
		if(frzjhm !== null && frzjhm !== undefined && frzjhm !== ''){
			frzjhm=frzjhm.split('');
			frzjhm.splice(6,8,'********');
			var frzjhmas = frzjhm.join("");
			document.getElementById('frzjhm').innerHTML=frzjhmas;
		}
		
		var frsjhm = '${sessionScope.curLoginMember.SJHM}';
		if(frsjhm !== null && frsjhm !== undefined && frsjhm !== ''){
			frsjhm=frsjhm.split('');
			frsjhm.splice(3,4,'****'); 
			var frsjhmas = frsjhm.join("");
			document.getElementById('frsjhm').innerHTML=frsjhmas;
		}
		var frdhhm = '${sessionScope.curLoginMember.DHHM}';
		if(frdhhm !== null && frdhhm !== undefined && frdhhm !== ''){
			frdhhm=frdhhm.split('');
			frdhhm.splice(3,4,'****'); 
			var frdhhmas = frdhhm.join("");
			document.getElementById('frdhhm').innerHTML=frdhhmas;
		}
		var jbrsfz = '${sessionScope.curLoginMember.JBRSFZ}';
		if(jbrsfz !== null && jbrsfz !== undefined && jbrsfz !== ''){
			jbrsfz=jbrsfz.split('');
			jbrsfz.splice(6,8,'********');
			var jbrsfzas = jbrsfz.join("");
			document.getElementById('jbrsfz').innerHTML=jbrsfzas;
		}
	}
	if(null==username||""==username){
		window.location.href = "<%=path%>/webSiteController/view.do?navTarget=website/yhzx/login";
	}
        AppUtil.initWindowForm("JBXX", function(form, valid) {
            if (valid) {
                var formData = $("#JBXX").serialize();
                var url = $("#JBXX").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                              alert( resultJson.msg);
                              window.location.reload()
                        } else {
                            alert( resultJson.msg);
                        }
                    }
                });
            }
        }, "T_BSFW_USERINFO");
        AppUtil.initWindowForm("XGMM", function(form, valid) {
            if (valid) {
                var formData = $("#XGMM").serialize();
                var url = $("#XGMM").attr("action");
                AppUtil.ajaxProgress({
                    url : url,
                    params : formData,
                    callback : function(resultJson) {
                        if (resultJson.success) {
                            alert( resultJson.msg);
                            window.location.reload()
                        } else {
                            alert( resultJson.msg);
                        }
                    }
                });
            }
        }, "T_BSFW_USERINFO");
        
        
        /* var zzjgdmFileUploadSwf = AppUtil.bindSwfUpload({
            file_types : "*.jpg;*.jpeg;*.png;*.gif;",
            file_size_limit :"2 MB",
            post_params : {
                   "uploadPath":"userInfozzjgdm"
            },
            file_upload_limit :100,
            button_placeholder_id:"uploadZzjgdmFileImg",
            successful_uploads:0,
            upload_success_handler:function(resultJson){
                if(resultJson.success==true){
                    //获取文件ID
                    var fileId = resultJson.fileId;
                    $("#FILE_ID").val(fileId);
                    //获取文件名称
                    var fileName = resultJson.fileName;
                    var divHtml = "<span style=\"width:370px;text-align:left;\" id=\""+fileId+"\"><a href=\"javascript:void(0);\" onclick=\"AppUtil.downLoadFile('";
                    divHtml+=(fileId+"');\">");
                    divHtml+=("<font color=\"blue\">查看</font></a>&nbsp;&nbsp;");
                    divHtml+="</span>";
                    $("#UploadZzjgdmFileTd").html(divHtml);
                }
            }
        }); */

		
});
/**
* 标题附件上传对话框
*/
function openZzjgdmFileUploadDialog(){
	//定义上传的人员的ID
	var uploadUserId = "${sessionScope.curLoginMember.USER_ID}";
	//定义上传的人员的NAME
	var uploadUserName = "${sessionScope.curLoginMember.YHMC}";
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=image&materType=image&busTableName=T_MSJW_SYSTEM_SYSUSER&uploadUserId='
			+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
		title: "上传图片",
		width: "620px",
		height: "240px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					$("input[name='FILE_ID']").val(uploadedFileInfo.fileId);
								
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"${_file_Server}download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType=image\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"QLRdelUploadFile('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
					$("#UploadZzjgdmFileTd").html(spanHtml);
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
/* function deleteZzjgdm(fileId){
        AppUtil.delUploadMater(fileId,'uploadZzjgdmFileImg','UploadZzjgdmFileTd');
        $("#FILE_ID").val('');
    } */
function deleteZzjgdm(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		$("input[name='FILE_ID']").val('');
	});
}
</script> 
</head>

<body class="userbody">
	<%--开始编写头部文件 --%>
    <jsp:include page="../yhzx/head.jsp?currentNav=zhsz" />
    <%--结束编写头部文件 --%>
    <div class="container lbpadding20">
    	<div class="main_t"></div>
    	<div class="nmain clearfix">
        	<%--开始引入用户中心的左侧菜单 --%>
        	<jsp:include page="./yhmenu.jsp" >
        	    <jsp:param value="zhsz" name="menuKey"/>
        	</jsp:include>
        	<%--结束引入用户中心的左侧菜单 --%>
        	
            <div class="nmainR">
                <div class="nmainRtitle1">
                    <ul>
                        <li><a href="javascript:void(0)">基本信息</a></li>
                        <li style="background:none;"><a href="javascript:void(0)">密码修改</a></li>
                    </ul>
                </div>
                <div class="nuserC">
                    <form id="JBXX" action="userInfoController.do?updateUserInfo">
                    <input type="hidden" name="USER_ID" value="${sessionScope.curLoginMember.USER_ID}"/>
                    <input type="hidden" name="FILE_ID" id="FILE_ID" value="${sessionScope.curLoginMember.FILE_ID}" />
                    <div>
                        <table cellpadding="0" cellspacing="0" class="bstable3 tmargin12">
                            <c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
                            <tr>
                                <th>登录名</th>
                                <td>${sessionScope.curLoginMember.YHZH}</td>
                                <th>姓名</th>
                                <td>${sessionScope.curLoginMember.YHMC}</td>
                            </tr>
                            <tr>
                                <th>证件类型</th>
                                <td ><eve:eveselect clazz="eve-input sel validate[required]"
                                    dataParams="DocumentType" id="ZJLX"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="=====选择证件类型====" name="ZJLX" value="${sessionScope.curLoginMember.ZJLX}"></eve:eveselect></td>
                                <th>证件号码</th>
                                <td>
                                	<div id='zjhm'> </div>
                                </td>
                            </tr>
                            <tr>
                                <th>手机</th>
                                <td>
                                	<div id='sjhm'> </div>
                                </td>
                                <th>邮箱地址</th>
<%--                                <td>${sessionScope.curLoginMember.DZYX}</td>--%>
                                <td><input type="text" 
                                    class="eve-input validate[maxSize[30]],custom[email]"
                                    name="DZYX" value="${sessionScope.curLoginMember.DZYX}"/></td>
                            </tr>
                            <tr>
                                <th>电话号码</th>
                                <td>
                                	<div id='dhhm'> </div>
                                </td>
                                <th>性别</th>
                                <td><eve:eveselect clazz="eve-input sel validate[required]"
                                    dataParams="sex"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="======选择性别=====" 
                                    name="YHXB" value="${sessionScope.curLoginMember.YHXB}" >
                                    </eve:eveselect></td>
                            </tr>
                         </c:if>
                         <c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
                            <tr>
                                <th>登录名</th>
                                <td>${sessionScope.curLoginMember.YHZH}</td>
                                <th>机构名称</th>
                                <td>${sessionScope.curLoginMember.YHMC}</td>
                            </tr>
                            <tr>
                                <th>机构类型</th>
                                <td><eve:eveselect clazz="eve-input sel validate[required]" id="jglx"
                                    dataParams="OrgType"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="=====选择机构类型====" name="JGLX" value="${sessionScope.curLoginMember.JGLX}"></eve:eveselect></td>
                                <th>单位机构代码</th>
                                <td>${sessionScope.curLoginMember.ZZJGDM}</td>
                            </tr>
                            <tr>
                                <th>机构代码证图片</th>
                                <td colspan="2" id="UploadZzjgdmFileTd">
                                <c:if test="${!empty  sessionScope.curLoginMember.FILE_ID}">
                                <span style="width:370px;text-align:left;" id="${sessionScope.curLoginMember.FILE_ID}">
                                <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${sessionScope.curLoginMember.FILE_ID}')"><font color="blue">查看</font></a>
                                </span>
                                </c:if>
                                </td>
                                <td>			
								<a href="javascript:void(0);" onclick="openZzjgdmFileUploadDialog()">
									<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
								</a>
                                </td>
                            </tr>
                            <tr>
                                <th>法人代表姓名</th>
                                <td>${sessionScope.curLoginMember.FRDB}</td>
                                 <th>法人代表性别</th>
                                <td><eve:eveselect clazz="eve-input sel validate[required]"
                                    dataParams="sex"
                                    dataInterface="dictionaryService.findDatasForSelect"
                                    defaultEmptyText="======选择性别=====" 
                                    name="YHXB" value="${sessionScope.curLoginMember.YHXB}" >
                                    </eve:eveselect></td>
                            </tr>
                            <tr>
                                <th>法人代表身份证</th>
<%--                                <td>${sessionScope.curLoginMember.ZJHM}</td>--%>
                                <td>
                                	<div id='frzjhm'> </div>
                                </td>
                                <th>法人代表手机号码</th>
<%--                                <td><input type="text" maxlength="11"--%>
<%--                                    class="eve-input validate[required,maxSize[11]],custom[mobilePhone]"--%>
<%--                                    name="SJHM" value="${sessionScope.curLoginMember.SJHM}"/></td>--%>
                                <td>
                                	<div id='frsjhm'> </div>
                                </td>
                            </tr>
                            <tr>
                                 <th>电话号码</th>
<%--                                 <td><input type="text" --%>
<%--                                    class="eve-input validate[maxSize[12]],custom[fixPhoneWithAreaCode]"--%>
<%--                                    name="DHHM" value="${sessionScope.curLoginMember.DHHM}"/></td>--%>
                                <td>
                                	<div id='frdhhm'> </div>
                                </td>
                                <th>邮政编码</th>
                                <td><input type="text" maxlength="6"
                                    class="eve-input validate[maxSize[6],custom[chinaZip]]" 
                                    name="YZBM" value="${sessionScope.curLoginMember.YZBM}"/></td>
                            </tr>
                            <tr>
                                <th>单位地址</th>
                                <td colspan="3"><textarea rows="5" cols="5" class="eve-textarea validate[maxSize[50]]"
                                    style="width: 390px;border: 1px solid #c9deef;" name="DWDZ">${sessionScope.curLoginMember.DWDZ}</textarea></td>
                            </tr>
                            <tr>
                                <th>经办人邮箱地址</th>
                                <td><input type="text" 
                                    class="eve-input validate[maxSize[30]],custom[email]"
                                    name="JBRYXDZ" value="${sessionScope.curLoginMember.JBRYXDZ}"/></td>
                                <th>经办人姓名</th>
                                <td><input type="text" maxlength="10"
                                    class=" validate[required,maxSize[10]],custom[onlychineseLetter]" name="JBRXM" value="${sessionScope.curLoginMember.JBRXM}" /></td>
                            </tr>
                            <tr>
                                <th>经办人身份证</th>
<%--                                <td><input type="text" maxlength="20" --%>
<%--                                    class=" validate[required,maxSize[20]],custom[vidcard]" name="JBRSFZ" value="${sessionScope.curLoginMember.JBRSFZ}"/></td>--%>
                                <td>
                                	<div id='jbrsfz'> </div>
                                </td>
                            </tr>
                            </c:if>
                        </table>
                        <div class="Ctext clearfix lbpadding32">
                            <a href="javascript:void(0);" onclick="$('#JBXX').submit();"
                            class="btn2">完  成</a>
                        </div>
                    </div>
                    </form>
                    <form id="XGMM" action="userInfoController.do?xgmm">
                    <div>
                        <table cellpadding="0" cellspacing="0" class="zxtable tmargin12">
                            <tr>
                                <th><span class="bscolor1">*</span> 当前使用的登录密码：</th>
                                <td><input type="password" 
                                     style="width:310px;"
                                    class="eve-input validate[required,maxSize[20]]"
                                    name="OLDMM" /></td>
                            </tr>
                            <tr>
                                <th><span class="bscolor1">*</span> 输入新密码：</th>
                                <td><input type="password" id="dlmm"
                                     style="width:310px;"
                                    class="eve-input validate[required,maxSize[20]],minSize[6],custom[Enpassword]"
                                    name="DLMM" /></td>
                            </tr>
                            <tr>
                                <th><span class="bscolor1">*</span> 重复新密码：</th>
                                <td><input type="password" maxlength="20" style="width:310px;"
                                    class="eve-input validate[equals[dlmm]]" name="QRDLMM" /></td>
                            </tr>
                        </table>
                        <div class="Ctext clearfix lbpadding32">
                            <a href="javascript:void(0);" onclick="$('#XGMM').submit();" class="btn2">完  成</a>
                        </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<script type="text/javascript">jQuery(".nmainR").slide({titCell:".nmainRtitle1 li",mainCell:".nuserC"})</script>
	<%--结束编写尾部文件 --%>
</body>
</html>
