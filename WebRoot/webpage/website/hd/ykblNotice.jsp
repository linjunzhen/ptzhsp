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

	var count=5;
	var qdtimer;
	$(function() {
		qdtimer=setInterval("setQueding()",1000);
		setTimeout("showBtn()",1000*5);
	});

	function showBtn(){
		$("input[name='queding']").removeAttr("disabled");
		$("input[name='queding1']").removeAttr("disabled");
		clearInterval(qdtimer);
		$("input[name='queding']").val("内资企业入口（含个体户）");
		$("input[name='queding1']").val("外资企业入口");
	}
	function setQueding(){
		count=count-1;
		$("input[name='queding']").val("内资企业入口（含个体户）("+count+")");
		$("input[name='queding1']").val("外资企业入口("+count+")")
	}


	function dosearchItem(){
		$("#ItemGrid").datagrid("clearChecked");
		AppUtil.gridDoSearch('ItemToolbar','ItemGrid');
	}

	function goWebsiteofwz() {
		window.open("http://61.154.11.191/usermana/login.do?method=index","_blank");
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

	<div class="easyui-layout" fit="true" >		
		<div data-options="region:'center',split:false" style="padding-left: 10px;font-family: FangSong;font-size: 16px;">
		<h3 style="text-align: center;padding-top: 10px;">平潭综合实验区经济发展局平潭综合实验区市场监督管理局<br />关于全面实施外商投资企业设立的商务备案与工商登记“一口办理”的公告</h3>
		<br/>
			<p style="line-height: 36px;">
				&nbsp;&nbsp;&nbsp;&nbsp;为深入贯彻落实国务院常务会议精神，持续推进商事制度改革，深化外商投资领域“放管服”改革， 构建“互联网+政务服务”管理新模式的重要举措，经我省商务和工商系统共同努力，自2018年6月30日起，在全省内施行外商投资 企业设立商务备案与工商登记“一口办理”。
			</p><br />
			<p style="line-height: 36px;">
				&nbsp;&nbsp;&nbsp;&nbsp;申请人在申请外资企业设立登记时，申请人请勿继续在平潭综合实验区综合审批服务平台进行申报，请通过福建省网上办事大厅（<a target="_blank" href="https://zwfw.fujian.gov.cn/">https://zwfw.fujian.gov.cn/
				</a>），点击“我要办  ---  部门服务----省市场监督管理局” 搜索“外商投资公司设立登记”点击“在线办理”。进入“外资企业登记”，在线一次性填报企业工商登 记和商务备案的“单一表格”，同时提交办理工商登记和商务备案所需信息及材料。申请人在申请内资转外资时，通过迁移流程完成 业务办理后，通过福建省工商红盾网“一口受理”办理独立商务备案。工商网上预审通过后，将结果返回给企业，企业登录系统，到 “我的申请”查阅结果，打印材料并签字盖章后，携带全部资料到行政服务中心商事登记窗口办理正式登记核准手续。
			</p><br />
			<p style="line-height: 36px;">
				&nbsp;&nbsp;&nbsp;&nbsp;6月30日前已办理工商登记还未进行商务备案的外商投资企业，请尽快到商务部“外商投资综合管理 应用系统”办理外资备案。外商投资企业商务备案事项发生变更或需更正的，仍按照备案办法直接通过商务部“外商投资综合管理应用系统”填报备案信息。
			</p><br />
			<p style="line-height: 36px;text-align:right;">
				咨询电话： 0591-12345，0591-86169725   服务时间：   早上： 8：15—12:00&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</p><br />
			<p style="line-height: 36px;text-align:right;">
				下午：14：00—17:30&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</p><br />
			<p style="line-height: 36px;text-align:right;">
				平潭综合实验区经济发展局&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</p><br />
			<p style="line-height: 36px;text-align:right;">
				平潭综合实验区市场监督管理局&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</p><br />
			<p style="line-height: 36px;text-align:right;">
				2018年8月16日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</p><br />
		</div>
		
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons" style="text-align: center;">
				<!--<input value="外资企业入口(5)" type="button" disabled="disabled" name="queding1" onclick="goWebsiteofwz();" target="_blank"
				   class="z-dlg-button z-dialog-okbutton aui_state_highlight" />-->
				<input value="内资企业入口（含个体户）(5)" type="button" onclick="doSelectRows();" disabled="disabled" name="queding"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			</div>
		</div>
	</div>

	
</body>
</html>
