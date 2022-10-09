<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript"
		src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<style>
.layout-split-south{border-top-width:0px !important;}
.eve_buttons{position: relative !important;}
</style>
<script type="text/javascript">

    function doSelectRows(){
    		art.dialog.data("noticeInfo", {
    			smxz:"1"
			});
    		AppUtil.closeLayer();
    }

	var count=15;
	var qdtimer;
	$(function() {
		qdtimer=setInterval("setQueding()",1000);
		setTimeout("showBtn()",1000*15);
	});
	function showBtn(){//.removeAttr(attributeName)
		$("input[name='queding']").removeAttr("disabled");///.attr("disabled","");
		clearInterval(qdtimer);
		$("input[name='queding']").val("确定");
	}
	function setQueding(){
		count=count-1;
		$("input[name='queding']").val("确定("+count+")");
	}
	function dosearchItem(){
		$("#ItemGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('ItemToolbar','ItemGrid');
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" style="padding-left: 10px;font-family: FangSong;font-size: 16px;">
		<h3 style="text-align: center;padding-top: 10px;">平潭综合室实验区行政服务中心便民服务诉求须知</h3>
		<br/>
			一、受理范围<br><br>
			<p style="line-height: 36px;">
				&nbsp;&nbsp;&nbsp;&nbsp;平潭综合实验区行政服务中心（以下简称“中心”）
				便民服务诉求平台24小时均可接收诉求人对本中心工作业务的咨询和建议、
				对工作人员及其工作中违反效能建设等有关规定的行为进行投诉、举报等。
			</p><br />
			
	&nbsp;&nbsp;&nbsp;（一）具体受理事项<br>
	     <p style="line-height: 36px;">
	     	&nbsp;&nbsp;&nbsp;&nbsp;1.业务咨询类：包括对中心各入驻单位的业务范围、受理材料、办事流程等政务信息的咨询；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;2.工作建议类：包括对中心日常工作的意见和建议；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;3.投诉举报类：包括对中心工作人员违纪情况的投诉、举报；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;4.感谢类：包括办理具体业务时发现的好人、好事致以谢意等。
	     </p><br/>
	&nbsp;&nbsp;&nbsp;（二）不予受理事项<br>
		<p style="line-height: 36px;">
	     	&nbsp;&nbsp;&nbsp;&nbsp;1.违反宪法和法律法规规定的，涉及国家机密、商业机密、个人隐私的；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;2.煽动颠覆国家政权，推翻社会主义制度的；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;3.捏造或者歪曲事实，散布谣言，扰乱社会秩序的；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;4.使用不文明用语，侮辱他人，捏造事实诽谤、攻击他人或机关的;<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;5.诉求内容不清楚、无具体诉求内容的;<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;6.已经或应当通过信访的渠道反映的事项；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;7.诉求事项已办结，诉求人仍以同一事实和理由提出诉求的；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;8.所反映的问题属于紧急救助的（对于此类问题，中心将提醒诉求人及时向110、122等专门紧急救助系统求助）；<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;9.反映非本中心职权管辖范围内的问题。
	     </p>
	  二、处理流程<br/><br/>
	  	<p style="line-height: 26px;">
				&nbsp;&nbsp;&nbsp;&nbsp;诉求人发送诉求件-→中心统一受理业务范围内信件-→经调查处理后回复、发布意见-→诉求人凭查询密码和诉求件编号查询回复意见。
	     </p><br />
	 三、办理时限
		<p style="line-height: 36px;">
				&nbsp;&nbsp;&nbsp;&nbsp;中心便民服务诉求平台的诉求件类型分别为咨询、举报、投诉、建议和感谢，办理时限分别为：
	     </p>
	     <p style="line-height: 36px;">
	     &nbsp;&nbsp;&nbsp;&nbsp;1. 咨询类诉求件：自接收诉求件之日起个5工作日（含第5个工作日）内；<br/>
		 &nbsp;&nbsp;&nbsp;&nbsp;2. 投诉、举报、建议及感谢类诉求件：自接收诉求件之日起10个工作日（含第10个工作日）内。
	     </p><br />
	四、注意事项
		<p style="line-height: 36px;">
	     &nbsp;&nbsp;&nbsp;&nbsp;1.请保存好诉求件编号和密码，以便查询办理结果。<br/>
		 &nbsp;&nbsp;&nbsp;&nbsp;2.诉求人需承担因诉求件内容可能引起的法律责任。<br/>
		 &nbsp;&nbsp;&nbsp;&nbsp;3.请使用文明语言。对于含有不文明用语的诉求件一概不予受理。
		</p><br/>
	五、友情提示
		<p style="line-height: 36px;">
			&nbsp;&nbsp;&nbsp;&nbsp;除了写诉求件外，诉求人还可以通过以下两种方式得到更快捷的服务。
	     </p>
	     <p style="line-height: 36px;">
	     	&nbsp;&nbsp;&nbsp;&nbsp;1.在首页“便民服务”中点击“用户指南”，浏览中心各入驻单位的常用电话，直接拨打电话即可咨询相关业务。 <br/> 
			&nbsp;&nbsp;&nbsp;&nbsp;2.在首页 “便民服务”中点击“常见问题”，浏览各入驻单位列举的常见问题，直接查询您关注的问题及答案。
		 </p>	
	      <br><br>
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: center;">
				<input value="确定(15)" type="button" onclick="doSelectRows();" disabled="disabled" name="queding"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			</div>
		</div>
	</div>

	
</body>
</html>
