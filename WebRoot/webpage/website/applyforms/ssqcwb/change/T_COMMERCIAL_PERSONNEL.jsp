<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="PERSONNEL_FORM"  >
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addYdsxxDiv();" class="syj-addbtn" id="addOldDsxx" >原董事</a>
		<span>原董事信息</span>		
	</div>
	<div id="oldDsxxDiv">
		<jsp:include page="./initOldDsxxDiv.jsp"></jsp:include>
	</div>
	<div id="C_DIRECTOR" style="display: none;">
		<div class="syj-title1 ">
			<a href="javascript:void(0);" onclick="javascript:addDsxxDiv();" class="syj-addbtn" id="addDsxx" style="display:none;">添 加</a><span>新董事信息</span>
			<!-- <select name="setHolderInfo" style="margin-left: 50px;width:auto;padding-right:20px;" class="input-dropdown " onchange="setInfoMsg(this.value,'DIRECTOR')">
				<option value="">请选择复用人员信息</option>
			</select> -->
		</div>
		<div id="dsxxDiv">
			<jsp:include page="./initDsxxDiv.jsp"></jsp:include> 
		</div>
	</div>
	
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addYjsxxDiv();" class="syj-addbtn" id="addOldJsxx" >原监事</a>
		<span>原监事信息</span>
	</div>
	<div id="oldJsxxDiv">
		<jsp:include page="./initOldJsxxDiv.jsp"></jsp:include>
	</div>
	<div id="C_SUPERVISOR" style="display: none;">
		<div class="syj-title1 ">
			<a href="javascript:void(0);" onclick="javascript:addJsxxDiv();" class="syj-addbtn" id="addJsxx" style="display:none;">添 加</a><span>新监事信息</span>
			<select  name="setHolderInfo" style="margin-left: 50px;width:auto;" class="input-dropdown " onchange="setInfoMsg(this.value,'SUPERVISOR')">
				<option value="">请选择复用人员信息</option>
			</select>
		</div>
		
		<div id="jsxxDiv">
			
			<jsp:include page="./initJsxxDiv.jsp"></jsp:include>
		</div>
	</div>
	
	<div class="syj-title1 ">
	    <a href="javascript:void(0);" onclick="javascript:addYjlxxDiv();" class="syj-addbtn" id="addOldJlxx" >原经理</a>
		<span>原经理信息</span>		
	</div>
	<div id="oldJlxxDiv">
		<jsp:include page="./initOldJlxxDiv.jsp"></jsp:include>
	</div>
	<div id="C_MANAGER" style="display: none;">
		<div class="syj-title1 ">
			<a href="javascript:void(0);" onclick="javascript:addJlxxDiv();" class="syj-addbtn" id="addJlxx" style="display:none;">添 加</a><span>新经理信息</span>
			<select  name="setHolderInfo" style="margin-left: 50px;width:auto;" class="input-dropdown " onchange="setInfoMsg(this.value,'MANAGER')">
				<option value="">请选择复用人员信息</option>
			</select>
		</div>
		<div id="jlxxDiv">
			
			<jsp:include page="./initJlxxDiv.jsp"></jsp:include>
		</div>
	</div>
</form>
