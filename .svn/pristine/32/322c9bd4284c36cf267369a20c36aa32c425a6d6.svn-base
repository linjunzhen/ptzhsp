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
<style>
	.field_width{width:192px;}
	.lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
	.inputBackgroundCcc{
		background-color:#ccc;
	}
</style>
<script type="text/javascript">
	
	var xmdwxxDivHtml = "";
	$(function() {
		
		var lerepInfo = '${busRecord.lerep_info}';
		if(lerepInfo){
			var lerepInfoList = $.parseJSON(lerepInfo);
			for(var i=0 ; i<lerepInfoList.length; i++){
				initXmdwxx(lerepInfoList[i],i)
			}			
		}
		//setTpl(lerepInfo);
		xmdwxxDivHtml = $("#xmdwxxDiv").html();

		//清除前后空格
		$("input,textarea").on('blur', function(event) { 
			$(this).val(trim($(this).val()));
		});
	});
	//清除前后空格
	function trim(str){ 
	  return str.replace(/(^\s*)|(\s*$)/g,""); 
	}

	function onSelectClass(o){
		if(o==1){
			$("#resultcontent_tr").show();
			$("#resultcontent").attr("disabled",false); 
		}else{
			$("#resultcontent_tr").hide();
			$("#resultcontent").attr("disabled",true); 
		}
	}
	$().ready(function() {
		//var busRecord = '${busRecord.PROJECTCODE}';
		
		//if(busRecord){
		//	loadTZXMXXData();
		//}
		$("input[name='LEREP_INFO']").val('${busRecord.lerep_info}');
		$("input[name='CONTRIBUTION_INFO']").val('${busRecord.contribution_info}');
		isShowWztzxx();
	});

	function loadTZXMXXData(){
		var code = $("input[name='PROJECTCODE']").val();
		if(null==code||''==code){
			art.dialog({
				content: "请填写投资项目编号",
				icon:"error",
				ok: true
			});
		}else{
			var layload = layer.load('正在提交校验中…');
			$.post("projectApplyController.do?loadTzxmxxData",{
				projectCode:code},
				function(responseText, status, xhr) {
					layer.close(layload);
					var resultJson = $.parseJSON(responseText);
					if (resultJson.result) {
						for(var key in resultJson.datas){
							//console.log(key + " : "+ resultJson.datas[key]);
							$("[name='"+key.toUpperCase()+"']").prop("readonly", true);
							$("[name='"+key.toUpperCase()+"']").prop("disabled", "disabled");	
							if(key=='industry'){
								var typeCode = resultJson.datas[key];
								$.post( "dicTypeController/info.do",{
									typeCode:typeCode,path:"4028819d51cc6f280151cde6a3f00027"},
									function(responseText1, status, xhr) {									
										var resultJson1 = $.parseJSON(responseText1);
										if(null!=resultJson1 && null!=resultJson1.TYPE_CODE && ''!=resultJson1.TYPE_CODE){	
											$("#industry").html('<option value="'+resultJson1.TYPE_CODE+'" selected="selected">'+resultJson1.TYPE_NAME+'</option>')
										} else{											
											$("#industry").html('<option value="'+typeCode+'" selected="selected">'+typeCode+'</option>')
										}
									});
							}else if(key=='place_code'){
								var typeCode2=resultJson.datas[key];
								$.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
									 function(responseText2,status,xhr){
										var  resultJson2=$.parseJSON(responseText2);
										if(null!=resultJson2){
											$("#placeCode").html('<option value="'+resultJson2.TYPE_CODE+'" selected="selected">'+resultJson2.TYPE_NAME+'</option>')
										} else{											
											$("#placeCode").html('<option value="'+typeCode2+'" selected="selected">'+typeCode2+'</option>')
										}
								});
							}else if(key=='industry_structure'){				
								var typeCode3 = resultJson.datas[key];
								$.post( "dicTypeController/info.do",{
									typeCode:typeCode3},
									function(responseText3, status, xhr) {									
										var resultJson3 = $.parseJSON(responseText3);
										if(null!=resultJson3){	
											$("#industryStructure").html('<option value="'+resultJson3.TYPE_CODE+'" selected="selected">'+resultJson3.TYPE_NAME+'</option>')
										} else{											
											$("#industryStructure").html('<option value="'+typeCode3+'" selected="selected">'+typeCode3+'</option>')
										}
									});
							}else if(key=='lerep_info'){
								//setTpl(JSON.stringify(resultJson.datas[key]));
								var lerepInfoList = resultJson.datas[key];
								for(var j=0;j<lerepInfoList.length;j++){
									initXmdwxx(lerepInfoList[j],j);
								}
								
								$("[name='LEREP_INFO']").val(JSON.stringify(resultJson.datas[key]));
							}else if(key=='contribution_info'){
								$("[name='CONTRIBUTION_INFO']").val(JSON.stringify(resultJson.datas[key]));
							}else if(key=='get_land_mode'){
								if(null != resultJson.datas[key] && '' != resultJson.datas[key]){									
									$("[name='GET_LAND_MODE']").val(resultJson.datas[key]);
								} else{									
									$("[name='"+key.toUpperCase()+"']").prop("readonly", false);
									$("[name='"+key.toUpperCase()+"']").prop("disabled", "");	
								}
							}else{							
								$("[name='"+key.toUpperCase()+"']").val(resultJson.datas[key]);
							}
						}	
						$("[name='PROJECTCODE']").prop("disabled", "disabled");					
					} else {
						art.dialog({
							content: "校验失败",
							icon:"error",
							ok: true
						});
					}
					isShowWztzxx();
					$("[name='PROJECTCODE']").prop("disabled", "");	
				}
			);
		}
	};
	function getXmdwxxJson(){
		
		var infoArray = [];
		$("#xmdwxxDiv table").each(function(i){	
			var enterprise_name = $(this).find("[name$='enterprise_name']").val();//单位名称
			var dwlx = $(this).find("[name$='dwlx']").val();//单位类型
			var lerep_certtype = $(this).find("[name$='lerep_certtype']").val();//证照类型
			var lerep_certno = $(this).find("[name$='lerep_certno']").val();//证件号码		
			var contact_name = $(this).find("[name$='contact_name']").val();	//联系人名称
			var contact_tel = $(this).find("[name$='contact_tel']").val();//联系电话
			var contact_email = $(this).find("[name$='contact_email']").val();// 联系人邮箱				
			var enterprise_place = $(this).find("[name$='enterprise_place']").val();//注册地址
			var enterprise_nature = $(this).find("[name$='enterprise_nature']").val();//单位性质
			var china_foreign_share_ratio = $(this).find("[name$='china_foreign_share_ratio']").val();//持股比例是否与资本金相同
			var business_scope = $(this).find("[name$='business_scope']").val();//主要经营范围
			var contact_phone = $(this).find("[name$='contact_phone']").val();//联系手机
			var contact_fax = $(this).find("[name$='contact_fax']").val();//传真			
			var correspondence_address = $(this).find("[name$='correspondence_address']").val();//通讯地址
			
			var info = {};
			info.enterprise_name = enterprise_name;
			info.dwlx = dwlx;
			info.lerep_certtype = lerep_certtype;
			info.lerep_certno = lerep_certno;
			info.contact_name = contact_name;
			info.contact_tel = contact_tel;
			info.contact_email = contact_email;				
			info.enterprise_place = enterprise_place;
			info.enterprise_nature = enterprise_nature;
			info.china_foreign_share_ratio = china_foreign_share_ratio;
			info.business_scope = business_scope;
			info.contact_phone = contact_phone;
			info.contact_fax = contact_fax;				
			info.correspondence_address = correspondence_address;
			
			infoArray.push(info);	
		});
		$("[name='LEREP_INFO']").val(JSON.stringify(infoArray));
	}
	function initXmdwxx(dwxx,i){		
		if(i>0){
			$("#xmdwxxDiv").html(xmdwxxDivHtml);
		}
		initFormObjValue(dwxx,$("#xmdwxxDiv table").eq(i));
	}
	/**
	 * 初始化表单字段值
	 * @param {} fieldValueObj
	 * @param {} elementObj
	 */
	function initFormObjValue(fieldValueObj,elementObj){
		for(var fieldName in fieldValueObj){
			//获取目标控件对象
			var targetFields = elementObj.find("[name$='"+fieldName+"']");
			targetFields.each(function(){
				var targetField = $(this);
				var type = targetField.attr("type");
				var tagName = targetField.get(0).tagName;
				var fieldValue = fieldValueObj[fieldName];
				
				if(type=="radio"){
					var radioValue = targetField.val();
					if(radioValue==fieldValue){
						$(this).attr("checked","checked");
					}
				}else if(type=="checkbox"){
					var checkBoxValue = targetField.val();
					var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
					if(isChecked){
						$(this).attr("checked","checked");
					}
				}else if(tagName=="SELECT"){
					targetField.children("option[value='"+fieldValueObj[fieldName]+"']")
					.attr("selected", "selected");
				}else{
					targetField.val(fieldValueObj[fieldName]);
				}		
				if(fieldName!='dwlx'){			
					targetField.prop("readonly", true);
					targetField.prop("disabled", "disabled");
				}	
			});	
		}
	}
	function isShowWztzxx(){		
		if($("#foreignabroadFlag").val()==1){
			$("#jwtzxx").attr("style","display:none;");
			$("#wstzxx").attr("style","display:;");	
		}else if($("#foreignabroadFlag").val()==2){
			$("#wstzxx").attr("style","display:none;");
			$("#jwtzxx").attr("style","display:;");	
		}else{
			$("#wstzxx").attr("style","display:none;");
			$("#jwtzxx").attr("style","display:none;");	
		}
		if($("#totalMoney").val()==0){
			$("#totalMoneyExplain").removeClass("");
			$("#totalMoneyExplain").toggleClass('validate[required]');
		}else{ 			
			$("#totalMoneyExplain").removeClass("validate[required]");
			$("#totalMoneyExplain").toggleClass('');
		}
	}
	function getDicNames(typeCode,dicCodes){
		$.post( "dictionaryController/textname.do",
		{
			typeCode:typeCode,dicCodes:dicCodes
		},
		function(responseText3, status, xhr) {									
			return responseText3;
		});
	}
	function getLerepcertTypeNames(code){
		if(code == 'A05100'){
			return '企业营业执照（工商注册号）';
		} else if(code == 'A05201'){
			return '组织机构代码证（企业法人）';
		} else if(code == 'A05202'){
			return '组织机构代码证（国家机关法人）';
		} else if(code == 'A05203'){
			return '组织机构代码证（事业单位法人）';
		} else if(code == 'A05204'){
			return '组织机构代码证（社会团体法人）';
		} else if(code == 'A05300'){
			return '统一社会信用代码';
		} else if(code == 'A05900'){
			return '其他';
		} else{
			return '';
		}
	}
	function getEnterpriseNatureNames(code){
		if(code == 'A00001'){
			return '中外合资企业';
		} else if(code == 'A00002'){
			return '中外合作企业';
		} else if(code == 'A00003'){
			return '国有及国有控股';
		} else if(code == 'A00004'){
			return '民营及民营控股企业';
		} else if(code == 'A00004'){
			return '外商独资企业';
		} else if(code == 'A00006'){
			return '外商投资合伙企业';
		} else if(code == 'A00007'){
			return '外商投资企业境内再投资企业';
		} else if(code == 'A00008'){
			return '其他';
		} else{
			return '';
		}
	}	
	function getYesOrNoNames(code){
		if(code == '0'){
			return '与项目资本金出资结构相同';
		} else if(code == '1'){
			return '与项目资本金出资结构不同';
		} else{
			return '';
		}
	}
	function showItemSelector() {
		parent.$.dialog.open("serviceItemController/selector.do", {
			title : "事项选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectItemInfo = art.dialog.data("selectItemInfo");
				if (selectItemInfo) {	
					$("input[name='ITEM_NAME']").val(selectItemInfo.itemNames);
					$("input[name='ITEM_CODE']").val(selectItemInfo.itemCodes);												
					art.dialog.removeData("selectItemInfo");	
				}
			}
		}, false);
	}
	function showXmflSelector() {
		parent.$.dialog.open("projectApplyController/selector.do?allowCount=1", {
			title : "项目分类选择器",
			width : "1000px",
			lock : true,
			resize : false,
			height : "500px",
			close : function() {
				var selectXmflInfo = art.dialog.data("selectXmflInfo");
				if (selectXmflInfo) {	
					$("input[name='FLOW_CATE_ID']").val(selectXmflInfo.ids);
					$("input[name='FLOW_CATE_NAME']").val(selectXmflInfo.names);												
					art.dialog.removeData("selectXmflInfo");	
				}
			}
		}, false);
	}
	//文件删除
	function materDelete(fileId){
		AppUtil.delUploadProjectMater(fileId);
	}
</script>
<!--==========隐藏域部分开始 ===========-->
<input type="hidden" name="ID" value="${busRecord.ID}">
<input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
<input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
<input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG" value="${busRecord.FOREIGN_ABROAD_FLAG}"/>
<input type="hidden" id="theIndustry" name="THE_INDUSTRY" value="${busRecord.THE_INDUSTRY}"/>
<input type="hidden" id="GBHYDMFBND" name="GBHYDMFBND" value="2017"/>

<!--==========隐藏域部分结束 ===========-->

<div class="syj-title1" style="height:30px;">
	<span>项目基本信息(<font color="#ff0101">*</font>为必填)</span>
</div>
<table cellpadding="0" cellspacing="0" class="syj-table2" id="tzjbxx">
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>投资项目编号</th>
		<td colspan="3">
			<input type="hidden" id="PROJECT_CODE" name="PROJECT_CODE" value="${busRecord.PROJECT_CODE}"/>
			<c:if test="${empty busRecord.PROJECTCODE}">
				<input type="text" style="width:75%;height:25px;float:left;" maxlength="32" id="PROJECTCODE"
						class="eve-input validate[required]" name="PROJECTCODE" />
				<c:if test="${projectDetail != true}">
					<input type="button" value="校验" class="eve-button" onclick="loadTZXMXXData()" style="margin-top: 3px;margin-left: 5px;">
				</c:if>
			</c:if>
			<c:if test="${not empty busRecord.PROJECTCODE}">
				<font style="margin-left: 4px;">${busRecord.PROJECTCODE}</font>
				<input type="hidden" id="PROJECTCODE" name="PROJECTCODE" value="${busRecord.PROJECTCODE}"/>
				<c:if test="${projectDetail != true}">
					<input type="button" value="校验" class="eve-button" onclick="loadTZXMXXData()" style="margin-top: 3px;margin-left: 5px;">
				</c:if>
			</c:if>
		</td>
	</tr>	
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>项目名称</th>
		<td>
			<input type="text" style="width:80%;height:25px;float:left;" maxlength="64" 
			class="eve-input validate[required]" value="${busRecord.PROJECT_NAME}" name="PROJECT_NAME" />
		</td>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>项目类型</th>
		<td>
			<eve:eveselect clazz="eve-input1 whf_input validate[required] field_width" dataParams="PROJECTTYPE"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PROJECT_TYPE}"
				defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
			</eve:eveselect>
		</td>
	</tr>		
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>项目所属区划</th>
		<td>
			<input type="text" style="width:80%;height:25px;float:left;" maxlength="6" 
			class="eve-input validate[required]" value="${busRecord.DIVISION_CODE}" name="DIVISION_CODE" />
		</td>
		<th style="border-bottom-width: 1px;width:180px;">投资项目目录编码</th>
		<td>
			<input type="text" style="width:80%;height:25px;float:left;" maxlength="16" 
			class="eve-input validate[]" value="${busRecord.PERMIT_ITEM_CODE}" name="PERMIT_ITEM_CODE" />
		</td>
	</tr>			
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>建设性质</th>
		<td>
			<eve:eveselect clazz="eve-input1 whf_input validate[required] " dataParams="PROJECTNATURE"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PROJECT_NATURE}"
				defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="PROJECT_NATURE">
			</eve:eveselect>
		</td>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>国标行业</th>
		<td>
			<!--<input type="text" style="width:80%;"  id="industry"  name="INDUSTRY" class="eve-input whf_input validate[required] easyui-combotree"  panelHeight="150px"/>-->
			<select id="industry" name="INDUSTRY" class="eve-input1 whf_input validate[required] " style="width:80%;">
			<option value="">请选择国标行业</option>
			</select>
		</td>
	</tr>	
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>拟开工时间</th>
		<td>
				<input readonly="true" id="startYear" type="text"  class="eve-input  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR"  value="${busRecord.START_YEAR}"/>
		</td>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>拟建成时间</th>
		<td>
			<input readonly="true" id="endYear" type="text" class="eve-input  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR" value="${busRecord.END_YEAR}" />
		</td>
	</tr>	
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>总投资（万元）</th>
		<td>								
			<input  id="totalMoney" type="text"  style="width:80%;height:25px;float:left;" 
			class="eve-input validate[required]" maxlength="16" name="TOTAL_MONEY"  value="${busRecord.TOTAL_MONEY}" />	
		</td>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>申报时间</th>
		<td>
			<input readonly="true" id="APPLY_DATE" type="text" class="eve-input  Wdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"  name="APPLY_DATE"  value="${busRecord.APPLY_DATE}" />
		</td>
	</tr>				
	<tr>
		<th style="border-bottom-width: 1px;width:180px;">总投资额为“0”时说明</th>
		<td  colspan="3">
			<textarea id="totalMoneyExplain" class="eve-textarea " rows="3" cols="200"  
			  name="TOTAL_MONEY_EXPLAIN" style="width:81.6%;height:75px;"  >${busRecord.TOTAL_MONEY_EXPLAIN}</textarea>
		</td>
	</tr>	
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>建设地点</th>
		<td>	
				<!--<input type="text" style="width:80%;height:25px;float:left;" id="placeCode"  name="PLACE_CODE" 
				class="eve-input validate[required] easyui-combotree"  panelHeight="120px"/>-->
				<select id="placeCode" name="PLACE_CODE" class="eve-input1 whf_input validate[required] " style="width:80%;">
					<option value="">请选择建设地点</option>
				</select>
		</td>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>建设地点详情</th>
		<td>
			<input id="PLACE_CODE_DETAIL" type="text" style="width:80%;height:25px;float:left;" maxlength="16" 
			class="eve-input validate[required]"   name="PLACE_CODE_DETAIL"  value="${busRecord.PLACE_CODE_DETAIL}" />
		</td>
	</tr>					
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 建设规模及内容</th>
		<td  colspan="3">
			<textarea id="SCALE_CONTENT" class="eve-textarea " rows="3" cols="200"  
			  name="SCALE_CONTENT" style="width:81.6%;height:75px;"  >${busRecord.SCALE_CONTENT}</textarea>
		</td>
	</tr>	
	<tr>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>项目属性</th>
		<td>
			<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="PROJECTATTRIBUTES"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PROJECT_ATTRIBUTES}"
			defaultEmptyText="请选择投资项目行业分类" name="PROJECT_ATTRIBUTES" id="PROJECT_ATTRIBUTES">
			</eve:eveselect>
		</td>
		<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>产业结构调整指导目录</th>
		<td>
				<!--<input type="text" style="width:80%;"  id="industryStructure"  name="INDUSTRY_STRUCTURE" class="eve-input whf_input validate[required] easyui-combotree"  panelHeight="150px"/>-->
				<select id="industryStructure" name="INDUSTRY_STRUCTURE" class="eve-input1 whf_input validate[required] " style="width:80%;">
					<option value="">请选择产业结构调整指导目录</option>
				</select>
		</td>
	</tr>	
	<tr>	
		<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 土地获取方式</th>
		<td>
			<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="getLandMode"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.GET_LAND_MODE}"
				defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
			</eve:eveselect>
		</td>	
		<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 项目投资来源</th>
		<td>
			<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="XMTZLY"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.XMTZLY}"
				defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
			</eve:eveselect>
		</td>	
	</tr>	
	<tr>	
		<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 土地是否带设计方案</th>
		<td>
			<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="TDSFDSJFA"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.TDSFDSJFA}"
				defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
			</eve:eveselect>
		</td>	
		<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 是否完成区域评估</th>
		<td>
			<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="SFWCQYPG"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.SFWCQYPG}"
				defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
			</eve:eveselect>
		</td>	
	</tr>		
	<tr>	
		<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 工程分类</th>
		<td colspan="3">
			<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="GCFL"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.GCFL}"
				defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
			</eve:eveselect>
		</td>	
	</tr>	
</table>
<div id="wstzxx" >
	<div class="syj-title1" style="height:30px;">
		<span>外商投资信息(<font color="#ff0101">*</font>为必填)</span>
	</div>
	<table cellpadding="0" cellspacing="0" class="syj-table2">			
		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>是否涉及国家安全</th>
			<td>
				<eve:eveselect clazz="eve-input1 whf_input validate[required] " dataParams="YesOrNo"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.IS_COUNTRY_SECURITY}" onchange="showSecurityApprovalNumber(this.value)" 
					defaultEmptyText="请选择是否涉及国家安全" name="IS_COUNTRY_SECURITY" id="IS_COUNTRY_SECURITY">
				</eve:eveselect>
			</td>
			<th style="border-bottom-width: 1px;width:180px;">安全审查决定文号</th>
			<td>
				<input id="SECURITY_APPROVAL_NUMBER" type="text"  maxlength="16" style="width:80%;height:25px;float:left;" 
				class="eve-input"  name="SECURITY_APPROVAL_NUMBER"  value="${busRecord.SECURITY_APPROVAL_NUMBER}" value="${busRecord.SECURITY_APPROVAL_NUMBER}" />	
			</td>
		</tr>	

		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 投资方式</th>
			<td>
				<eve:eveselect clazz="eve-input1 whf_input  validate[required]" dataParams="investmentMode"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INVESTMENT_MODE}"
					defaultEmptyText="请选择投资方式" name="INVESTMENT_MODE" id="investmentMode" 
					onchange="showOther(this.value)">
				</eve:eveselect>
			</td>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 总投资额折合美元(万元)</th>
			<td>
				<input id="totalMoneyDollar" type="text"  maxlength="18" style="width:80%;height:25px;float:left;"
				class="eve-input  validate[required,custom[numberp6plus]]" name="TOTAL_MONEY_DOLLAR"  value="${busRecord.TOTAL_MONEY_DOLLAR}" />
			</td>
		</tr>
		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 总投资额使用的汇率<br/>（人民币/美元）</th>
			<td>
				<input id="totalMoneyDollarRate" type="text" maxlength="100" style="width:80%;height:25px;float:left;"
				class="eve-input  validate[required,custom[numberp6plus]]" name="TOTAL_MONEY_DOLLAR_RATE"  value="${busRecord.TOTAL_MONEY_DOLLAR_RATE}"  />
			</td>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 项目资本金(万元)</th>
			<td>
				<input id="projectCapitalMoney" type="text"  maxlength="18" style="width:80%;height:25px;float:left;"
				class="eve-input  validate[required,custom[numberp6plus]]" name="PROJECT_CAPITAL_MONEY"  value="${busRecord.PROJECT_CAPITAL_MONEY}" />
			</td>
		</tr>
		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 项目资本金折合美元<br/>(万元)</th>
			<td>
				<input id="projectCapitalMoneyDollar" class="eve-input  validate[required,custom[numberp6plus]]" style="width:80%;height:25px;float:left;" value="${busRecord.PROJECT_CAPITAL_MONEY_DOLLAR}"
				maxlength="100" class="eve-input  " name="PROJECT_CAPITAL_MONEY_DOLLAR"  />
			</td>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 项目资本金使用的汇率<br/>（人民币/美元）</th>
			<td>
				<input id="projectCapitalMoneyRate" type="text"  maxlength="18" style="width:80%;height:25px;float:left;"
				class="eve-input  validate[required,custom[numberp6plus]]" name="PROJECT_CAPITAL_MONEY_RATE"  value="${busRecord.PROJECT_CAPITAL_MONEY_RATE}" />
			</td>
		</tr>
		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 适用产业政策条目类型</th>
			<td>
				<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="industrialPolicyType"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INDUSTRIAL_POLICY_TYPE}"
					defaultEmptyText="请选择产业政策条目类型" name="INDUSTRIAL_POLICY_TYPE" id="industrialPolicyType">
				</eve:eveselect>
			</td>
			<th style="border-bottom-width: 1px;width:180px;">适用产业政策条目</th>
			<td>
				<eve:eveselect clazz="eve-input1 whf_input validate[]" dataParams="industrialPolicy"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INDUSTRIAL_POLICY}"
					defaultEmptyText="请选择产业政策条目" name="INDUSTRIAL_POLICY" id="industrialPolicy">
				</eve:eveselect>
			</td>
		</tr>
		<tr id="otherInvestmentApplyInfoTr" style="display: ">
			<th style="border-bottom-width: 1px;width:180px;">其他投资方式需予以<br/>申报的情况</th>
			<td  colspan="3">
				<textarea id="otherInvestmentApplyInfo" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
				name="OTHER_INVESTMENT_APPLY_INFO"  style="width:80%;height:75px;" >${busRecord.OTHER_INVESTMENT_APPLY_INFO}</textarea>
			</td>
		</tr>
		<tr id="transactionBothInfoTr" style="display: ">
			<th style="border-bottom-width: 1px;width:180px;">提供交易双方情况</th>
			<td  colspan="3">
			<textarea id="transactionBothInfo" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
			 name="TRANSACTION_BOTH_INFO"  style="width:80%;height:75px;" >${busRecord.TRANSACTION_BOTH_INFO}</textarea>
			</td>
		</tr>
		<tr id="mergerPlanTr" style="display: ">
			<th style="border-bottom-width: 1px;width:180px;">并购安排</th>
			<td  colspan="3">
			<textarea id="mergerPlan" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
			 name="MERGER_PLAN"  style="width:80%;height:75px;" >${busRecord.MERGER_PLAN}</textarea>
			</td>
		</tr>
		<tr id="mergerManagementModeScopeTr" style="display: ">
			<th style="border-bottom-width: 1px;width:180px;">并购后经营方式及<br/>经营范围</th>
			<td  colspan="3">
			<textarea id="mergerManagementModeScope" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
			 name="MERGER_MANAGEMENT_MODE_SCOPE"  style="width:80%;height:75px;" >${busRecord.MERGER_MANAGEMENT_MODE_SCOPE}</textarea>
			</td>
		</tr>
		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 总建筑面积（平方米）</th>
			<td>
				<input id="builtArea" type="text" maxlength="100" style="width:80%;height:25px;float:left;"
				class="eve-input  validate[required,custom[numberp6plus]]" name="BUILT_AREA"   value="${busRecord.BUILT_AREA}"/>
			</td>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 总用地面积(平方米)</th>
			<td>
				<input id="landArea" type="text"  maxlength="18" style="width:80%;height:25px;float:left;"
				class="eve-input  validate[required,custom[numberp6plus]]" name="LAND_AREA"  value="${busRecord.LAND_AREA}" />
			</td>
		</tr>
		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 是否新增设备</th>
			<td>
				<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="YesOrNo"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.IS_ADD_DEVICE}"
					defaultEmptyText="请选择是否新增设备" name="IS_ADD_DEVICE" id="IS_ADD_DEVICE">
				</eve:eveselect>
			</td>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 拟进口设备数量及金额</th>
			<td>
				<input id="importDeviceNumberMoney" type="text" maxlength="100" class="eve-input   validate[required]" name="IMPORT_DEVICE_NUMBER_MONEY"  style="width:80%;height:25px;float:left;" value="${busRecord.IMPORT_DEVICE_NUMBER_MONEY}"/>
			</td>
		</tr>
	</table>
</div>
<div id="jwtzxx">
	<div class="syj-title1" style="height:30px;">
		<span>境外投资信息(<font color="#ff0101">*</font>为必填)</span>
	</div>
	<table cellpadding="0" cellspacing="0" class="syj-table2">	
		<tr>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 项目所在地</th>
			<td>
				<input id="PROJECT_SITE" type="text" maxlength="100" style="width:80%;height:25px;float:left;"
				class="eve-input  validate[required]" name="PROJECT_SITE"  value="${busRecord.PROJECT_SITE}" />
			</td>
			<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 中方投资额(万元)</th>
			<td>
				<input id="chinaTotalMoney" type="text"  maxlength="18" style="width:80%;height:25px;float:left;"
				class="eve-input validate[required,custom[numberp6plus]]" name="CHINA_TOTAL_MONEY" value="${busRecord.CHINA_TOTAL_MONEY}" />
			</td>
		</tr>
	</table>
</div>
<div class="syj-title1">
	<span>项目（法人）单位信息</span>
</div>	
<div id="xmdwxxDiv">	
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top: 5px;">
			<tr>
				<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 单位名称</th>
				<td >
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="100" 
					class="eve-input validate[required]" name="enterprise_name" />
				</td>
				<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 单位类型</th>
				<td>
					<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="DWLX"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择单位类型" name="dwlx" id="dwlx">
					</eve:eveselect>
				</td>
			</tr>	
			<tr>
				<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 证照类型</th>
				<td>
					<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="LEREPCERTTYPE"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证照类型" name="lerep_certtype" id="lerep_certtype">
					</eve:eveselect>
				</td>
				<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 证照号码</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="32" 
					class="eve-input validate[required]" name="lerep_certno" />
				</td>
			</tr>	
			<tr>
				<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 联系人名称</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="16" 
					class="eve-input validate[required]" name="contact_name" />
				</td>
				<th style="border-bottom-width: 1px;width:180px;"><font class="tab_color">*</font> 联系电话</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="16" 
					class="eve-input validate[required]" name="contact_tel" />
				</td>
			</tr>	
			<tr>
				<th style="border-bottom-width: 1px;width:180px;">联系人邮箱</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="32" 
					class="eve-input validate[]" name="contact_email" />
				</td>
				<th style="border-bottom-width: 1px;width:180px;">注册地址</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="128" 
					class="eve-input validate[]" name="enterprise_place" />
				</td>
			</tr>	
			<tr>
				<th style="border-bottom-width: 1px;width:180px;">单位性质</th>
				<td>
					<eve:eveselect clazz="eve-input1 whf_input validate[]" dataParams="enterpriseNature"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择单位性质" name="enterprise_nature" id="enterprise_nature">
					</eve:eveselect>
				</td>
				<th style="border-bottom-width: 1px;width:180px;">持股比例是否与资本金相同</th>
				<td>
					<eve:eveselect clazz="eve-input1 whf_input validate[]" dataParams="chinaForeignShareRatio"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择持股比例是否与资本金相同" name="china_foreign_share_ratio" id="china_foreign_share_ratio">
					</eve:eveselect>								
				</td>
			</tr>
			<tr>
				<th style="border-bottom-width: 1px;width:180px;">主要经营范围</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="512" 
					class="eve-input validate[]" name="business_scope" />
				</td>
				<th style="border-bottom-width: 1px;width:180px;">联系手机</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="16" 
					class="eve-input validate[]" name="contact_phone" />
				</td>
			</tr>
			<tr>
				<th style="border-bottom-width: 1px;width:180px;">传真</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="16" 
					class="eve-input validate[]" name="contact_fax" />
				</td>
				<th style="border-bottom-width: 1px;width:180px;">通讯地址</th>
				<td>
					<input type="text" style="width:80%;height:25px;float:left;" maxlength="64" 
					class="eve-input validate[]" name="correspondence_address" />
				</td>
			</tr>
		</table>
	</div>	
</div>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->