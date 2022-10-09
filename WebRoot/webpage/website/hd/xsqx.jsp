<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String type = request.getParameter("type");
request.setAttribute("type", type);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评议调查_平潭综合实验区行政服务中心</title>
<meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/common/css/style.css">
	<script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/slimscroll-1.3.3/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/superslide-2.1.1/jquery.SuperSlide.2.1.1.js"></script>
	<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
<link type="text/css" href="<%=path%>/plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet" id="easyuiTheme">
<link type="text/css" href="<%=path%>/plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">
<script src="<%=path%>/plug-in/easyui-1.4/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default" type="text/javascript"></script>
<script src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<link type="text/css" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css" rel="stylesheet">
<link type="text/css" href="<%=path%>/plug-in/validationegine-2.6.2/css/validationEngine.jquery.css" rel="stylesheet">
<script src="<%=path%>/plug-in/validationegine-2.6.2/jquery.validationEngine.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/validationegine-2.6.2/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/json-2.0/json2.js" type="text/javascript"></script>
<script src="<%=path%>/plug-in/layer-1.8.5/layer.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/plug-in/swfupload-2.2/swfupload.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/upload/jquery.picupload.js"></script>
<link type="text/css" href="<%=path%>/plug-in/jqueryautocomplete-1.2.3/jquery.autocomplete.css" rel="stylesheet">
<script src="<%=path%>/plug-in/jqueryautocomplete-1.2.3/jquery.autocomplete.js" type="text/javascript"></script>
<!--[if lte IE 6]> 
<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
<script type="text/javascript"> 
     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
</script> 
<![endif]-->
<script type="text/javascript">
	var __ctxPath="<%=request.getContextPath() %>";
	$(function() {
		readXZ();
		AppUtil.initWindowForm("letter_form", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#letter_form").serialize();
				var url = $("#letter_form").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								ok: true
							});
							resetForm();
						}else{
							art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		}, "T_HD_LETTER");
		
		var type = '${type}';
		if(null!=type&&''!=type){
			$("#"+type).attr("checked","checked");
		}
		changeRandPic();
		
		
		
		//加载自动补全数据
		loadAutoCompleteDatas();
		//诉求方式点击
		$("input[name='DEPT_OR_ITEM_TYPE']").on("click", function(event) {
			if (this.value === "0") {					
				$("#deptTr").hide();
				$("#itemTr").show();
				$("input[name='LETTER_DEPT']").attr("disabled",true);
				$("input[name='LETTER_ITEMS']").attr("disabled",false);
				$("input[name='LETTER_DEPTID']").val("");	
				$("input[name='LETTER_DEPT']").val("");					
			} else {
				$("#deptTr").show();
				$("#itemTr").hide();	
				$("input[name='LETTER_DEPT']").attr("disabled",false);
				$("input[name='LETTER_ITEMS']").attr("disabled",true);
				$("input[name='LETTER_ITEMID']").val("");		
				$("input[name='LETTER_ITEMS']").val("");	
			}
		});
		/**
		$("input[name='smxz']").on("click", function(event) {
			if ($("input[name='smxz']").is(':checked')) {	
				$("#smxzBtn").attr("class","btn2");
				$("#smxzBtn").attr("onclick","$('#letter_form').submit();");	
			} else {
				$("#smxzBtn").attr("class","btn3");
				$("#smxzBtn").attr("onclick","");	
			}
		});  **/
	});
	/**
	* 附件上传对话框
	*/
	function openXsqxFileUploadDialog(){
		//定义上传的人员的ID
		var uploadUserId = "123456";
		//定义上传的人员的NAME
		var uploadUserName = "匿名";
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attach&busTableName=T_HD_LETTER&uploadUserId='
			+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
			title: "上传(附件)",
			width: "620px",
			height: "240px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						var fileType = 'gif,jpg,jpeg,bmp,png';
						var attachType = 'attach';
						if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
							attachType = "image";
						}											
						$("input[name='LETTER_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='LETTER_FILE_ID']").val(uploadedFileInfo.fileId);
						var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server
						+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
						+"&attachType="+attachType+"\" ";
						spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
						spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
						spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delNewUploadMater('"+uploadedFileInfo.fileId+"');\" ><font color=\"red\">删除</font></a></p>"
						$("#LETTER_FILE_DIV").html(spanHtml); 
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	/**
	 * 删除已上传的材料文件
	 * @param {} fileId
	 */
	function delNewUploadMater(fileId){
		art.dialog.confirm("您确定要删除该文件吗?", function() {
			//移除目标span
			$("#"+fileId).remove();
			$("input[name='LETTER_FILE_URL']").val("");
			$("input[name='LETTER_FILE_ID']").val("");
		});
	}
	function changeRandPic(){
    	$("#randpic").attr({
	          "src": "<%=path %>/rand.jpg?"+Math.random()
	     });
    }
	function checkFormContent(){
		 var str = $("textarea[name='LETTER_CONTENT']").val();;
		if (str.length<=2000) {
			document.getElementById("contentSpan").innerHTML = "(还剩"+(2000-str.length)+"字可输入，如果内容超过两千字，建议Word附件方式提交)";
		} else {
			document.getElementById("contentSpan").innerHTML = "<font color='red'>(您输入的内容超过两千字，建议Word附件方式提交)</font>";
		}
	}
	
	function resetForm(){
		$('#letter_form')[0].reset();
		$("input[name='LETTER_FILE_URL']").val('');
		$("input[name='LETTER_FILE_ID']").val('');
		$("#LETTER_FILE_DIV").html('');
		checkFormContent();
		changeRandPic();
	}
	
		function pressKey(event){				
			
		}
	
		function showSelectDeparts(){
			var departId = $("input[name='LETTER_DEPTID']").val();
			var url = "departmentController/selector.do?noAuth=true&departIds="+departId+"&allowCount=1&checkCascadeY=&"
			+"checkCascadeN=";
			$.dialog.open(url, {
				title : "部门选择器",
				width:"600px",
				lock: true,
				resize:false,
				height:"500px",
				close: function () {
					var selectDepInfo = art.dialog.data("selectDepInfo");
					if(selectDepInfo){
						$("input[name='LETTER_DEPTID']").val(selectDepInfo.departIds);
						$("input[name='LETTER_DEPT']").val(selectDepInfo.departNames);
					}
				}
			}, false);
		};
		//自动补全
		function loadAutoCompleteDatas() {
			$.post("consultController/load.do", {

			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				$("#AutoCompleteInput").autocomplete(
						resultJson,
					{
						matchContains : true,
						mustMatch : true,
						formatItem : function(row, i, max) {
							//下拉框显示
							return "<div>" + row.ITEM_NAME+"</div>";
						},
						formatMatch : function(row) {
							//查询条件
							return row.ITEM_NAME+row.PINYIN;
						},
						formatResult : function(row, i, max) {
							//显示在文本框中
							return row.ITEM_NAME;
						}
					}).result(
					function(event, data, formatted) {
						if(null!=data){							
							$("input[name='LETTER_ITEMID']").val(data.ITEM_ID);
						}
					}

					);
				});
			}
	function bindItem(){
		$.dialog.open("consultController/selector.do", {
			title : "事项选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectItemInfo = art.dialog.data("selectItemInfo");
				if(selectItemInfo){
					$("input[name='LETTER_ITEMID']").val(selectItemInfo.itemIds);
					$("input[name='LETTER_ITEMS']").val(selectItemInfo.itemNames);
					art.dialog.removeData("selectItemInfo");
				}
			}
		}, false);
	}
	function readXZ(){
		$.dialog.open("webSiteController/view.do?navTarget=website/hd/xsqxNotice", {
			title : "市民填写诉求件须知",
			width:"800px",
			esc: false,//取消esc键  
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectInfo = art.dialog.data("noticeInfo");
				if(selectInfo){
					$("input[name='smxz']").val(selectInfo.smxz);
					art.dialog.removeData("noticeInfo");
				}
			}
		}, false);
		 $(".aui_close").hide(); 
		 $("#smxzBtn").attr("class","btn2");
		 $("#smxzBtn").attr("onclick","$('#letter_form').submit();");	
	}
</script>
</head>

<body>
	<%--开始编写头部文件 --%>
    <jsp:include page="../index/head.jsp?currentNav=zxhd" /> 
    <%--结束编写头部文件 --%>
	<div class="container lbpadding">
<%--    	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <a href="webSiteController/view.do?navTarget=website/hd/zxhd">咨询互动</a> > <i>写诉求信</i></div>--%>
	<div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>咨询互动</i> > <i>业务咨询</i></div>
	<div class="listMain clearfix">
        	<div class="listL">
            	<div class="listTitle">咨询互动</div>
                <div class="sublist">
                	<ul>
<%--                    	<li class="subOn"><a href="webSiteController/view.do?navTarget=website/hd/xsqx">写诉求信</a></li>--%>
                        <li><a href="webSiteController/view.do?navTarget=website/hd/wyjc">我要纠错</a></li>
                    </ul>
                </div>
            </div>
            <div class="listR">
				<form class="eui-form" id="letter_form" method="post" action="<%=path%>/letterController/saveLetter.do">
				<input type="hidden" name="LETTER_FILE_URL" >
				<input type="hidden" name="LETTER_FILE_ID">
				<input type="hidden" name="LETTER_DEPTID">
				<input type="hidden" name="LETTER_ITEMID">
            	<div class="eui-xsqx">
                	<b>注：</b>请认真填写表格(带 <font color="#FF0000">*</font> 项不能为空)。为了使您获得最快的E-mail答复，请您务必填写正确的E-mail地址！ 我们会为您的个人资料(电话和邮箱地址)保密！请您在发表诉求时务必使用文明用语。对于含有不文明用语的诉求件，系统不予受理。
                    <div class="eui-xsyqts">若您在填写诉求件时所用时间过长，将会造成验证码失效，需刷新后再次提交。</div>
                </div>
                <div class="title3" style="margin-bottom:0px;">来信人</div>
            	<table cellpadding="0" cellpadding="0" class="eui-table">
                	<tr>
                    	<th><font color="#FF0000">* </font>姓名：</th>
                        <td><input type="text" class="validate[required],maxSize[30]" style="width:180px;" name="LETTER_NAME"/><span>（可匿名填写）</span></td>
                        <th>性别：</th>
                        <td>
							<input type="radio" name="LETTER_SEX" value="男" checked="checked"/> 男  
							<input type="radio" name="LETTER_SEX" value="女"/> 女
						</td>
                    </tr>
                    <tr>
                    	<th><font color="#FF0000">* </font>从事职业：</th>
                        <td>
						<eve:eveselect clazz="validate[required]" dataParams="HDJOB" 
						dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="---------请选择从事职业--------" name="LETTER_CAREER">
						</eve:eveselect>
						</td>
                        <th><font color="#FF0000">* </font>电子邮箱：</th>
                        <td><input type="text" style="width:180px;" class="validate[required],custom[email],maxSize[30]" name="LETTER_EMAIL"/></td>
                    </tr>
                    <tr>
                    	<th>联系电话：</th>
                        <td><input type="text" style="width:180px;" class="validate[],custom[fixPhoneWithAreaCode],maxSize[16]" name="LETTER_PHONE"/></td>
                        <th><font color="#FF0000">* </font>手机：</th>
                        <td><input type="text" style="width:180px;" class="validate[required],custom[mobilePhone],maxSize[16]" name="LETTER_MOBILE"/></td>
                    </tr>
                    <tr>
                    	<th>联系地址：</th>
                        <td colspan="3"><input type="text" style="width:500px;" class="validate[],maxSize[250]" name="LETTER_ADDRESS"/></td>
                    </tr>
                    <tr>
                    	<th>工作单位：</th>
                        <td colspan="3"><input type="text" style="width:500px;" class="validate[],maxSize[50]" name="LETTER_WORKUNIT"/></td>
                    </tr>
                </table>
                <div class="title3" style="margin-bottom:0px;">信件问题</div>
                <table cellpadding="0" cellpadding="0" class="eui-table">
                	<tr>
                    	<th><font color="#FF0000">* </font>信件类型：</th>
                        <td colspan="3">
							<input type="radio" id="zx" name="LETTER_TYPE" value="咨询"/> 咨询 
							<input type="radio" id="jb" name="LETTER_TYPE" value="举报"/> 举报 
							<input type="radio" id="ts" name="LETTER_TYPE" value="投诉"/> 投诉 
							<input type="radio" id="jy" name="LETTER_TYPE" checked="checked" value="建议"/> 建议 
							<input type="radio" id="gx" name="LETTER_TYPE" value="感谢"/> 感谢 
						</td>
                    </tr>
					<tr>
						<th><font color="#FF0000">* </font>诉求方式：</th>
						<td><input type="radio" name="DEPT_OR_ITEM_TYPE" value="0" checked="checked" /> 办事事项</td>
						<td colspan="2"><input type="radio"  name="DEPT_OR_ITEM_TYPE" value="1" /> 部门</td>
					</tr>
					<tr id="itemTr">
						<th valign="top"><font color="#FF0000">* </font>办事事项：</th>
                        <td colspan="3">
							<input  value="${LETTER_ITEMS}" class="validate[required]" type="text"
							id="AutoCompleteInput" placeholder="请选择需要的事项" style="width:430px"
							name="LETTER_ITEMS" onkeyup="pressKey(event)" 
							onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"/>
							<a href="javascript:bindItem();" class="btn1">选 择</a>
						</td>
					</tr>						
					<tr id="deptTr" style="display:none;">
						<th valign="top"><font color="#FF0000">* </font>部&nbsp;&nbsp;门：</th>
						<td colspan="3"><input onclick="showSelectDeparts();"
						value="${deptName}" class="validate[required]" type="text" name="LETTER_DEPT"
						placeholder="请选择需要的部门" style="width:430px"  readonly="readonly" 
						disabled="disabled"/><a href="javascript:showSelectDeparts();" class="btn1">选 择</a>
						</td>
					</tr>	
                    <tr>
                    	<th><font color="#FF0000">* </font>事件地点：</th>
                        <td><input type="text" style="width:180px;" name="LETTER_PLACE" class="validate[required],maxSize[100]"/></td>
                        <th><font color="#FF0000">* </font>事件时间：</th>
                        <td>
							<input type="text" style="height: 26px;width:180px;" readonly="ture" class="validate[required] Wdate" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="LETTER_TIME">	
						</td>
                    </tr>
                    <tr>
                    	<th><font color="#FF0000">* </font>信件标题：</th>
                        <td colspan="3"><input type="text" style="width:500px;" name="LETTER_TITLE" class="validate[required],maxSize[100]"/></td>
                    </tr>
                    <tr>
                    	<th valign="top"><font color="#FF0000">* </font>信件内容：</th>
                        <td colspan="3"><textarea onblur="checkFormContent()" onkeyup="checkFormContent()" name="LETTER_CONTENT" class="validate[required],maxSize[2000]"></textarea><br />
						<span id="contentSpan">（还剩2000字可输入，如果内容超过两千字，请使用word附件形式提交）</span></td>
                    </tr>
                    <tr>
                    	<th>上传附件：</th>
                        <td colspan="3">
						 <a href="javascript:void(0);" onclick="openXsqxFileUploadDialog()">
							<img src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
						</a>
						<span style="width:100%;" id="LETTER_FILE_DIV">
						</span>
						</td>
                    </tr>
                    <tr>
                    	<th><font color="#FF0000">* </font>是否公开：</th>
                        <td colspan="3">
							<input type="radio" name="LETTER_ISPUBLIC" checked="checked" value="1"/> 可以在网上公开  
							<input type="radio" name="LETTER_ISPUBLIC" value="0"/> 不在网上公开
						</td>
                    </tr>
                    <tr>
                    	<th><font color="#FF0000">* </font>验证码：</th>
                        <td colspan="3">
							<input name="vcode" id="vcode" type="text" class="validate[required],maxSize[4]" style="width:105px; height: 24px;" />					
							<img name="vc" id="randpic" style="height: 26px;margin-top: -5px;width:80px;"
							title="点击切换验证码" alt="验证码" src="<%=path %>/rand.jpg" align="middle"
							onclick="changeRandPic();" style="cursor:pointer"/>
						</td>
                    </tr>
                </table>
                <input type="hidden" name="smxz">
				<!-- 
                <div class="eui-checkinfo"><input type="checkbox" name="smxz"  id="smxz" value="1"/>  已查看过
				<a target="_blank" href="webSiteController/view.do?navTarget=website/hd/smxz">
					<font color="red">《市民填写诉求件须知》</font>
					</a>
				</div>
					 -->
                <div class="sbtbtn">
				<a href="javascript:void(0);" id="smxzBtn" class="btn3" disabled="disabled">提  交</a>
				<a href="javascript:resetForm();" class="btn2">重  置</a></div>
				</form>
            </div>
			
        </div>
    </div>
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
</body>
</html>
