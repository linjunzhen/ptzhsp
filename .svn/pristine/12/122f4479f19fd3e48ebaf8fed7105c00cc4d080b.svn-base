<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<eve:resources loadres="easyui"></eve:resources>
<script type="text/javascript" src="<%=path %>/webpage/bsdt/applyform/js/solely1.js"></script>
<script type="text/javascript">
	$(function(){
		document.getElementById("applyTime").value = today();
		// var projectCode = document.getElementById("projectCode").value;
		// if(projectCode == null || projectCode.length == 0 || projectCode == "null"){
		// 	projectCode = document.getElementById("project_code").value;
		// }
		// if(projectCode!=null && projectCode.length>0 && projectCode != "null"){
		// 	loadTZXMXXData();
		// }
	});
	//当前时间
	function today(){
	    var today=new Date();
	    var h=today.getFullYear();
	    var m=today.getMonth()+1;
	    var d=today.getDate();
	    m= m<10?"0"+m:m;     
	    d= d<10?"0"+d:d;
	    return h+"-"+m+"-"+d;
	}
	
	function loadTZXMXXData(){
		var code = $("input[name='PROJECTCODE']").val();
		document.getElementById("projectCode").value = code;
		if(null==code||''==code){
			art.dialog({
				content: "请填写投资项目编号",
				icon:"error",
				ok: true
			});
		}else{
			var layload = layer.load('正在提交校验中…');
			$.post( "webSiteController/loadTZXMXXData.do",{
				projectCode:code},
				function(responseText, status, xhr) {
					layer.close(layload);
					var resultJson = $.parseJSON(responseText);
					if (resultJson.result) {
						for(var key in resultJson.tzProject){
							if(key=='placeCode'){
								var typeCode2=resultJson.tzProject[key];
								$.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
								     function(responseText2,status,xhr){
								        var  resultJson2=$.parseJSON(responseText2);
								        if(null!=resultJson2){
								            $("#placeCode").combotree("setValue",resultJson2.TYPE_CODE);
								            $("#placeCode").combotree("setText",resultJson2.TYPE_NAME);
								        }
								});
							}else{							
								$("#"+key).val(resultJson.tzProject[key]);
							}
						}					
						$("#tzjbxx input").attr("readonly",false);
						$("#tzjbxx textarea").attr("readonly",false);		
						$("#tzjbxx select").attr("readonly",false);	
					} else {
						art.dialog({
							content: "校验失败",
							icon:"error",
							ok: true
						});
					}
				}
			);
		}
	};
	function choosePrjType(val){
		if($(val).is(":checked")){
			$(".childBox").attr("disabled",false);
		}else{
			$(".childBox").prop("checked", false);
			$(".childBox").attr("disabled",true);
		}
	}
	
	//当选择“新建”，下方“装修工程”不可填
	function setDisabled(val){
		if($(val).is(":checked")){
			$("#zxgcTable").find("input,radio,select").attr("disabled", "disabled");
		}else{
			$("#zxgcTable").find("input,radio,select").attr("disabled",false);
		}
	}
	
	//添加施工图审查合格证书编号
	function addSgtscNo(){
		var serachInputText = $("[name='SGTSC_NO']");
		var intValue = 0;
		$.each(serachInputText,function () {
			var input = $(this);
			intValue = input.attr("id");
		});
		if(intValue != null && intValue != "" && intValue != "undifind"){
			var arr = intValue.split("_");
			intValue = parseInt(arr[1])+1;
		}
		var idValue = "SGTSCID_"+intValue;
		var inputText = '<div id="sgdiv'+(intValue)+'"><input type="text" id="'+(idValue)+'" name="SGTSC_NO"/>';
		var asign='<a href="javascript:void(0);" onclick="delSgtsNo('+intValue+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
		$('#sgtsc').append(inputText+asign);
	}
	//删除施工图审查合格证书编号
	function delSgtsNo(intValue){
		$("#sgdiv"+intValue).remove();
	}
	//添加专业工程消防审查合格书编号
	function addZygcxf(){
		var serachInputText = $("[name='ZYGCXF_NO']");
		var intValue = 0;
		$.each(serachInputText,function () {
			var input = $(this);
			intValue = input.attr("id");
		});
		if(intValue != null && intValue != "" && intValue != "undifind"){
			var arr = intValue.split("_");
			intValue = parseInt(arr[1])+1;
		}
		var idValue = "ZYGCXFID_"+intValue;
		var inputText = '<div id="zydiv'+(intValue)+'"><input type="text" id="'+(idValue)+'" name="ZYGCXF_NO"/>';
		var asign='<a href="javascript:void(0);" onclick="delZygcxfNo('+intValue+')">&nbsp;&nbsp;<span class="bscolor1">删除</span></a></div>';
		$('#zygcxf').append(inputText+asign);
	}
	//删除专业工程消防审查合格书编号
	function delZygcxfNo(intValue){
		$("#zydiv"+intValue).remove();
	}
</script>
<style>
	.lerepCertTypeSelect{
		width: 210px !important;
	}
	.xzbhBtn{
		background: #62a1cf none repeat scroll 0 0;color: #fff;
		display: inline-block;height: 26px;left: -5px;top: 1px;
		line-height: 26px;position: relative;text-align: center;
		width: 80px;margin-left: 5px;
	}
</style>
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>项目基本信息</span></li>
		</ul>
	</div>
	<div class="bsboxC">
		<table cellpadding="0" cellspacing="0" class="bstable1" style="border: 0 dotted #a0a0a0;">			
			<tr>
				<th><span class="bscolor1">*</span> 投资项目统一代码</th>
				<td colspan="3">
					<input type="hidden"  id="project_code" name="PROJECT_CODE"  value="${projectCode}"/>
					<input type="hidden"  id="prj_code" name="PRJ_CODE"  value="${prj_code}"/>
					<c:if test="${projectCode == null }">
						<input type="text" maxlength="32" class="tab_text" name="PRJ_CODE" />
						<!-- <a href="javascript:loadTZXMXXData();" class="projectBtnA">校 验</a><font color="red">（请先输入投资项目统一代码进行校验）</font> <a target="_blank" href="https://fj.tzxm.gov.cn/eap/credit.reportGuide?cantcode=350128&projecttype=A00001" style="color:red;"><省级投资项目申报登记入口></a> -->
					</c:if>
					<c:if test="${projectCode != null }">
						<input type="text" maxlength="32" class="tab_text" id="PRJ_CODE" name="PRJ_CODE" value="${projectCode}" readonly="true"/>
					</c:if>
				</td>
			</tr>
		</table><br/>
		<table cellpadding="0" cellspacing="0" class="bstable1"  id="tzjbxx">
			<tr>
				<th><span class="bscolor1">*</span> 填表日期</th>
				<td>
					<input type="text" id="applyTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
						readonly="true" class="tab_text validate[required] Wdate" name="APPLY_DATE" />
				</td>
				<th><span class="bscolor1">*</span> 项目类别</th>
				<td>
					<input type="checkbox" name="FC_PRJ_TYPE" value="100" onclick="setDisabled(this);"> 新建
			        <input type="checkbox" name="FC_PRJ_TYPE" value="200"> 扩建
			        <input type="checkbox" name="FC_PRJ_TYPE" id="prjType_editPrj" value="300" onclick="choosePrjType(this);"> 改建
		          （<input type="checkbox" class="childBox" name="FC_PRJ_TYPE" value="301"> 装饰装修
			        <input type="checkbox" class="childBox" name="FC_PRJ_TYPE" value="302"> 建筑保温
			        <input type="checkbox" class="childBox" name="FC_PRJ_TYPE" value="303"> 功能改变）
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>工程名称</th>
				<td colspan="3">
					<input id="projectName" type="text" class="tab_text w838 validate[required]" name="PRJ_NAME" 
						 maxlength="128"/>
					<input type="hidden" name="PROJECT_NAME" maxlength="128"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>工程地址</th>
				<td>
					<input id="projectAddr" type="text" class="tab_text validate[required]" name="ADDRESS" />
				</td>
				<th>所在区/县</th>
				<td>
					<eve:eveselect clazz="tab_text w280" dataParams=""
						dataInterface="dictionaryService.findDatasForSelect"
						name="ADMIN_DIVISION">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="BUILD_CORPNAME" />
				</td>
				<th><span class="bscolor1">*</span>建设单位申请人</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="JSDWSJSQR" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位联系人</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="CONTACTOR" />
				</td>
				<th><span class="bscolor1">*</span>联系人电话</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="CONTACT_INFORMATION" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>法定代表人</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="LEGAL_REPRESENT" />
				</td>
				<th><span class="bscolor1">*</span>代表人电话</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="LEGAL_INFORMATION" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>计划开工日期</th>
				<td>
					<input type="text" id="BEGIN_DETE" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
						class="tab_text validate[required] Wdate" name="BEGIN_DETE" />
				</td>
				<th><span class="bscolor1">*</span>计划竣工日期</th>
				<td>
					<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'BEGIN_DETE\')}'})" 
						class="tab_text validate[required] Wdate" name="END_DATE" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>总建筑面积(㎡)</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="ALLAREA" />
				</td>
				<th><span class="bscolor1">*</span>工程投资额(万元)</th>
				<td>
					<input type="text" maxlength="100" class="tab_text validate[required]" name="ALLINVEST" />
				</td>
			</tr>			
			<tr>
				<th><span class="bscolor1">*</span>住房城乡建设主管部门</th>
				<td colspan="3">
					<input  type="text" class="tab_text validate[required] w838" name="ZFCXJSZGBM" 
						 maxlength="128" value="平潭综合实验区行政审批局"/>
				</td>
			</tr>
			<tr>
				<th>施工图审查合格证书编号</th>
				<td>
					<div id="sgtsc"></div>
					<a href="javascript:void(0);" class="xzbhBtn" onclick="addSgtscNo()">新增编号</a>
				</td>
				<th>项目消防编码</th>
				<td>
					<input type="text" maxlength="100" class="tab_text" name="XMXFBM" />
				</td>
			</tr>
			<tr>
				<th>专业工程消防审查合格书编号</th>
				<td>
					<div id="zygcxf"></div>
					<a href="javascript:void(0);" class="xzbhBtn" onclick="addZygcxf()">新增编号</a>
				</td>
<%-- 				<th><span class="bscolor1">*</span> 公开方式</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="OPENWAY"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择公开方式" name="OPEN_WAY" id="openWay">
					</eve:eveselect>
				</td> --%>
			</tr>
		</table>
	</div>
</div>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->