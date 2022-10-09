<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
<link rel="stylesheet"
	href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<script type="text/javascript">
	$(function() {
		
		
		AppUtil.initWindowForm("gtptxxglForm", function(form, valid) {
			if (valid) {
				var LIAISON_SFZZM = $("input[name='LIAISON_SFZZM']").val();
				var LIAISON_SFZFM = $("input[name='LIAISON_SFZFM']").val();				
				if(LIAISON_SFZZM==null||LIAISON_SFZZM==''){
					art.dialog({
						content : "请上传身份证正面！",
						icon : "warning",
						ok : true
					});
					return;
				}
				if(LIAISON_SFZFM==null||LIAISON_SFZFM==''){
					art.dialog({
						content : "请上传身份证反面！",
						icon : "warning",
						ok : true
					});
					return;
				}
				var oldREGISTER_ADDR_NUM = '${gtptxxgl.REGISTER_ADDR_NUM}';
				var newREGISTER_ADDR_NUM = $("input[name='REGISTER_ADDR_NUM']").val();
				if(Number(oldREGISTER_ADDR_NUM)>Number(newREGISTER_ADDR_NUM)){					
					art.dialog({
						content : "住所编码必须大于更改前！",
						icon : "warning",
						ok : true
					});
					return;
				}
				$('#gtptxxglForm').find('input,textarea').prop("disabled", false);
				$('#gtptxxglForm').find('select').prop("disabled", false);
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#gtptxxglForm").serialize();
				var url = $("#gtptxxglForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
							parent.$("#gtptxxglGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
					}
				});
			}
		}, "T_COMMERCIAL_GTPTXXGL");
		
		//清除前后空格
		$("input,textarea").on('blur', function(event) { 
			$(this).val(trim($(this).val()));
		});
		var ARMY_REGISTER_HOURSE = '${gtptxxgl.ARMY_REGISTER_HOURSE}';
		if(ARMY_REGISTER_HOURSE){			
			setRequired(ARMY_REGISTER_HOURSE,'ARMYHOURSE_REGISTER_REMARKS','03');
		}
	});
	//清除前后空格
	function trim(str){ 
	  return str.replace(/(^\s*)|(\s*$)/g,""); 
	}
	/**
	* 经营范围选择器
	**/
	function showSelectJyfwNew(name,id){
		var ids = $("input[name='"+id+"']").val();
		parent.$.dialog.open("domesticControllerController.do?selectorNew&allowCount=30&noAuth=true&ids="
			+ ids, {
				title : "选择器",
				width : "850px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectBusScopeInfo = art.dialog.data("selectBusScopeInfo");
					if (selectBusScopeInfo && selectBusScopeInfo.ids) {
						$("[name='"+id+"']").val(selectBusScopeInfo.ids);
						$("[name='"+name+"']").val(selectBusScopeInfo.busscopeNames);
						art.dialog.removeData("selectBusScopeInfo");
					}
				}
			}, false);
	};
	
	//选择行业
    function showSelectChildIndustry(id,name){
    	var ids = $("input[name='"+id+"']").val();
    	$.dialog.open(encodeURI("busScopeController.do?selector&allowCount=1&childIndustryIds="+ids), {
       		title : "行业选择器",
               width:"1000px",
               lock: true,
               resize:false,
               height:"500px",
               close: function () {
       			var selectChildIndustryInfo = art.dialog.data("selectChildIndustryInfo");
       			if(selectChildIndustryInfo){
       		    	$("input[name='"+id+"']").val(selectChildIndustryInfo.childIndustryIds);
        			$("input[name='"+name+"']").val(selectChildIndustryInfo.childIndustryNames);
					if(ids != selectChildIndustryInfo.childIndustryIds){						
						$("input[name='MAIN_BUSSINESS_NAME']").val("");
						$("input[name='MAIN_BUSSINESS_ID']").val("");
						$("input[name='DL_NAME']").val("");
						$("input[name='ML_NAME']").val("");
						$("input[name='ZL_NAME']").val("");
						$("input[name='XL_NAME']").val("");
					}
       		    	art.dialog.removeData("selectChildIndustryInfo");
       			}
       		}
       	}, false);
    }
	function showSelectMainBussiness() {
		var insustryId =$("input[name='CHILD_INDUSTRY_ID']").val();
		if (insustryId) {
			parent.$.dialog.open("industryController.do?selectMainBussinessView&entityId=" + insustryId, {
				title : "主营范围信息",
				width : "80%",
				lock : true,
				resize : false,
				height : "80%",
				close: function () {
					var selectInfo = art.dialog.data("selectInfo");
					if(selectInfo){
						$("input[name='MAIN_BUSSINESS_NAME']").val(selectInfo.BUSSCOPE_NAME);
						$("input[name='MAIN_BUSSINESS_ID']").val(selectInfo.MAIN_BUSSINESS_ID);
						$("input[name='DL_NAME']").val(selectInfo.DL_NAME);
						$("input[name='ML_NAME']").val(selectInfo.ML_NAME);
						$("input[name='ZL_NAME']").val(selectInfo.ZL_NAME);
						$("input[name='XL_NAME']").val(selectInfo.XL_NAME);
						art.dialog.removeData("selectInfo");
					}
				}
			}, false);
		}else{
			art.dialog({
				content: "请先选择行业信息!",
				icon:"warning",
				ok: true
			});
		}
	}
	/**
	 *当值为val1时，设置为必填
	 * @param val
	 * @param inputName
	 * @param otherValue
	 */
	function setRequired(val,inputName,val1){
		if (val===val1) {
			$("input[name="+inputName+"]").attr("disabled",false);
			$("input[name="+inputName+"]").addClass(" validate[required]");
		} else {
			$("input[name="+inputName+"]").attr("disabled",true);
			$("input[name="+inputName+"]").removeClass(" validate[required]");
			$("input[name="+inputName+"]").val('');
		}
	}
	
	
	function openGtptxxglImageUploadDialog(name){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open('<%=path%>/fileTypeController.do?openUploadDialog&attachType=image', {
			title: "上传(图片)",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#"+name+"_PATH_IMG").attr("src",__file_server + uploadedFileInfo.filePath);
						$("input[name='"+name+"']").val(uploadedFileInfo.filePath);
					}else{
						$("#"+name+"_PATH_IMG").attr("src",'<%=path%>/webpage/common/images/nopic.jpg');
						$("input[name='"+name+"']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
</script>
<style>
	.layout-split-south{border-top-width:0px !important;}
	.eve_buttons{position: relative !important;}
	.eflowbutton {
		background: #3a81d0;
		border: none;
		padding: 0 10px;
		line-height: 26px;
		cursor: pointer;
		height: 26px;
		color: #fff;
		border-radius: 5px;
	}
	.whf_input{width:97%!important;;height:25px;float:left;}
	.btn1 {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 27px;
		line-height: 27px;
		margin-left: 10px;
		text-align: center;
		width: 50px;
	}
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="gtptxxglForm" method="post"
		action="gtptxxglController.do?saveOrUpdate">
		<div region="center" style="min-height:460px;">
			<div class="eui-slidebox" style="width:96%">
				<!--==========隐藏域部分开始 ===========-->
				<input type="hidden" name="PT_ID" value="${gtptxxgl.PT_ID}">
				<!--==========隐藏域部分结束 ===========-->
				
				<div class="syj-title1" style="height:30px;">
					<span>基本信息(<font color="#ff0101">*</font>为必填)</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>平台名称</th>
						<td style="width:378px;">
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="16" id="PT_NAME"
								class="eve-input validate[required]" placeholder="请输入平台名称"  value="${gtptxxgl.PT_NAME}" name="PT_NAME" />
						</td>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>平台验证码</th>
						<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="16" id="PT_PWD"
								class="eve-input validate[required]" placeholder="请输入平台验证码" value="${gtptxxgl.PT_PWD}" name="PT_PWD" />
						</td>
					</tr>
					<tr>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>行业</th>
						<td style="width:378px;">
								<input type="text" style="width:80%;height:25px;float:left;" readonly="readonly" maxlength="128" id="PT_HY"
								class="eve-input validate[required]" placeholder="请选择行业" value="${gtptxxgl.PT_HY}" name="PT_HY" />
							<input type="hidden" name="CHILD_INDUSTRY_ID" value="${gtptxxgl.CHILD_INDUSTRY_ID}">
							<a href="javascript:showSelectChildIndustry('CHILD_INDUSTRY_ID','PT_HY');" style="margin-top: 3px;" class="btn1">选 择</a>
						</td>
						<th style="border-bottom-width: 1px;width:180px;"><font class="dddl_platform_html_requiredFlag">*</font>组织形式</th>
						<td>
								<input type="text" style="width:97%;height:25px;float:left;" maxlength="16" id="PT_ZZXS"
								class="eve-input validate[required]" placeholder="请输入组织形式" value="${gtptxxgl.PT_ZZXS}" name="PT_ZZXS" />
						</td>
					</tr>	
					<tr>
						<th ><font class="syj-color2">*</font>从业人数（人）：</th>
						<td><input type="text" class="eve-input validate[required,custom[onlyNumberSp]]" style="width:97%;height:25px;float:left;"
							name="EMPLOYED_NUM" maxlength="8" value="${gtptxxgl.EMPLOYED_NUM}" placeholder="请输入从业人数（人）"/></td>
						<th ><font class="syj-color2">*</font>资金数额（万元）：</th>
						<td><input type="text" class="eve-input validate[required,custom[JustNumber]]"  style="width:97%;height:25px;float:left;"
							name="CAPITAL_AMOUNT" maxlength="8" value="${gtptxxgl.CAPITAL_AMOUNT}" placeholder="请输入资金数额（万元）"/></td>
					</tr>					
					<tr>
						<th ><font class="syj-color2">*</font>主营范围：</th>
						<td colspan="3">
							<input type="text" style="width:80%;height:25px;float:left;" readonly="readonly" maxlength="16" id="MAIN_BUSSINESS_NAME"
								class="eve-input validate[required]" placeholder="请选择主营范围" value="${gtptxxgl.MAIN_BUSSINESS_NAME}" name="MAIN_BUSSINESS_NAME" />
							<input type="hidden" name="MAIN_BUSSINESS_ID" value="${gtptxxgl.MAIN_BUSSINESS_ID}">
							<a href="javascript:showSelectMainBussiness();"  style="margin-top: 3px;" class="btn1">选 择</a>
						</td>
					</tr>							
					<tr>
						<th ><font class="syj-color2">*</font>行业门类：</th>
						<td colspan="3">
								<input type="text" style="width:20%;height:25px;float:left;" readonly="readonly" maxlength="16" id="ML_NAME"
								class="eve-input validate[required]" placeholder="行业门类" value="${gtptxxgl.ML_NAME}" name="ML_NAME" />
								
								<input type="text" style="width:20%;height:25px;float:left;" readonly="readonly" maxlength="16" id="DL_NAME"
								class="eve-input validate[required]" placeholder="行业大类" value="${gtptxxgl.DL_NAME}" name="DL_NAME" />
								
								<input type="text" style="width:20%;height:25px;float:left;" readonly="readonly" maxlength="16" id="ZL_NAME"
								class="eve-input validate[required]" placeholder="行业中类" value="${gtptxxgl.ZL_NAME}" name="ZL_NAME" />
								
								<input type="text" style="width:20%;height:25px;float:left;" readonly="readonly" maxlength="16" id="XL_NAME"
								class="eve-input validate[required]" placeholder="行业小类" value="${gtptxxgl.XL_NAME}" name="XL_NAME" />
						</td>
					</tr>							
					<tr>
						<th ><font class="syj-color2">*</font>经营范围：</th>
						<td colspan="3">
							<textarea rows="3" cols="200" name="BUSINESS_SCOPE" readonly="readonly"
								class="eve-textarea validate[validate[required],maxSize[512]]"
								style="width:90%;height:100px;" placeholder="请选择经营范围" >${gtptxxgl.BUSINESS_SCOPE}</textarea> 
							<input type="hidden" name="BUSINESS_SCOPE_ID" value="${gtptxxgl.BUSINESS_SCOPE_ID}">
							<a href="javascript:showSelectJyfwNew('BUSINESS_SCOPE','BUSINESS_SCOPE_ID');" class="btn1">选 择</a>
						<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
						<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
						中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
						可以参照政策文件、行业习惯或者专业文献等提出申请</div>
						</td>
					</tr>								
					<tr>
						<th ><font class="syj-color2">*</font>辖区分局：</th>
						<td>
							<eve:eveselect clazz="eve-input1 validate[required]" dataParams="1"
							   dataInterface="jurisdictionService.findDatasForSelect"
							   defaultEmptyText="请选择辖区分局" name="XQ_NAME"  value="${gtptxxgl.XQ_NAME}">
							</eve:eveselect>
						</td>
						<th ><font class="syj-color2">*</font>是否测试阶段：</th>
						<td>							
							<input type="radio" name="IS_TEST" value="1" <c:if test="${gtptxxgl.IS_TEST=='1'}">checked="checked"</c:if>>是</input>
							<input type="radio" name="IS_TEST" value="0" <c:if test="${gtptxxgl.IS_TEST!='1'}"> checked="checked"</c:if>>否</input>
						</td>
					</tr>	
				</table>
				
				<div class="syj-title1" style="height:30px;">
					<span>住所信息(<font color="#ff0101">*</font>为必填)</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th style="border-bottom-width: 1px;width:180px;"><font class="syj-color2">*</font>注册地址：</th>
						<td style="width:378px;"><input type="text" class="eve-input validate[required]" style="width:97%;height:25px;float:left;"
							name="BUSINESS_PLACE" maxlength="117"  placeholder="请输入注册地址" value="${gtptxxgl.BUSINESS_PLACE}"/></td>
						
						<th style="border-bottom-width: 1px;width:180px;"><font class="syj-color2">*</font>住所编码：</th>
						<td style="width:378px;"><input type="text" class="eve-input validate[required,custom[onlyNumberSp]]" style="width:97%;height:25px;float:left;"
							name="REGISTER_ADDR_NUM" maxlength="10"  placeholder="请输入住所编码" value="${gtptxxgl.REGISTER_ADDR_NUM}"/></td>						
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>房屋所有权人名称：</td>
						<td><input  type="text" class="eve-input  validate[required]" style="width:97%;height:25px;float:left;"
									name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${gtptxxgl.PLACE_REGISTER_OWNER}"/></td>
						<th><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
						<td><input type="text" class="eve-input validate[required]" style="width:97%;height:25px;float:left;"
								   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${gtptxxgl.PLACE_REGISTER_TEL}"/></td>

					</tr>
					<tr>
					<th style="border-bottom-width: 1px;width:180px;"><font class="syj-color2">*</font>面积：</th>
						<td>
							<input type="text" class="eve-input validate[required],custom[JustNumber]" maxlength="16" style="width:90%;height:25px;float:left;"
								   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${gtptxxgl.REGISTER_SQUARE_ADDR}"/>
							<font style="font-size: 16px;margin-left: 10px;">㎡</font>
						</td>
						<th><font class="syj-color2">*</font>取得方式：</th>
						<td>
							<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="wayOfGet"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyText="请选择取得方式" name="RESIDENCE_REGISTER_WAYOFGET"  value="${gtptxxgl.RESIDENCE_REGISTER_WAYOFGET}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>是否属于军队房产：</th>
						<td >
							<eve:eveselect clazz="eve-input1 whf_input validate[required]" dataParams="armyHourse"
										   dataInterface="dictionaryService.findDatasForSelect" onchange="setRequired(this.value,'ARMYHOURSE_REGISTER_REMARKS','03')"
										   defaultEmptyText="请选择军队房产情况" name="ARMY_REGISTER_HOURSE"  value="${gtptxxgl.ARMY_REGISTER_HOURSE}">
							</eve:eveselect>
						</td>
						<th>其他证明：</th>
						<td >
							<input type="text" class="eve-input" style="width:97%;height:25px;float:left;" disabled
								   name="ARMYHOURSE_REGISTER_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${gtptxxgl.ARMYHOURSE_REGISTER_REMARKS}"/>
						</td>
					</tr>
					<tr>
						<th ><font class="syj-color2">*</font>邮政编码：</th>
						<td colspan="3"><input type="text" class="eve-input validate[required,custom[chinaZip]]" style="width:38%;height:25px;float:left;"
							name="PLACE_POSTCODE" maxlength="6" value="${gtptxxgl.PLACE_POSTCODE}" placeholder="请输入邮政编码"/></td>
					</tr>
				</table>
				<div class="syj-title1" style="height:30px;">
					<span>联络员/委托人信息(<font color="#ff0101">*</font>为必填)</span>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th style="border-bottom-width: 1px;width:180px;"><font class="syj-color2">*</font>姓名：</th>
						<td style="width:378px;"><input type="text" class="eve-input validate[required]" style="width:97%;height:25px;float:left;"
							name="LIAISON_NAME" maxlength="16"  placeholder="请输入姓名" value="${gtptxxgl.LIAISON_NAME}"/></td>
						<th style="border-bottom-width: 1px;width:180px;"><font class="syj-color2">*</font>手机号码：</th>
						<td>
							<input type="text" class="eve-input validate[required],custom[mobilePhoneLoose]" maxlength="11" style="width:97%;height:25px;float:left;"
								   name="LIAISON_MOBILE" placeholder="请输入手机号码" value="${gtptxxgl.LIAISON_MOBILE}"/>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>身份证号码：</td>
						<td><input  type="text" class="eve-input  validate[required],custom[vidcard]" style="width:97%;height:25px;float:left;"
									name="LIAISON_IDNO"  placeholder="请输入身份证号码"  maxlength="18"  value="${gtptxxgl.LIAISON_IDNO}"/></td>
						<th><font class="syj-color2">*</font>住所：</td>
						<td><input type="text" class="eve-input validate[required]" style="width:97%;height:25px;float:left;"
								   name="LIAISON_ADDR"  placeholder="请输入住所"  maxlength="32"  value="${gtptxxgl.LIAISON_ADDR}"/></td>

					</tr>
					<tr>
						<th><font class="syj-color2">*</font>身份证正面：</th>
						<td>							
							<c:choose>
								<c:when test="${gtptxxgl.LIAISON_SFZZM!=null&&gtptxxgl.LIAISON_SFZZM!=''}">
									<img id="LIAISON_SFZZM_PATH_IMG" src="${_file_Server}${gtptxxgl.LIAISON_SFZZM}"
										height="140px" width="220px">
									<a href="javascript:void(0);" onclick="openGtptxxglImageUploadDialog('LIAISON_SFZZM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
								</c:when>
								<c:otherwise>
									<img id="LIAISON_SFZZM_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">
									<a href="javascript:void(0);" onclick="openGtptxxglImageUploadDialog('LIAISON_SFZZM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[required]" name="LIAISON_SFZZM" value="${gtptxxgl.LIAISON_SFZZM}">
						</td>
						<th><font class="syj-color2">*</font>身份证反面：</th>
						<td>							
							<c:choose>
								<c:when test="${gtptxxgl.LIAISON_SFZFM!=null&&gtptxxgl.LIAISON_SFZFM!=''}">
									<img id="LIAISON_SFZFM_PATH_IMG" src="${_file_Server}${gtptxxgl.LIAISON_SFZFM}"
										height="140px" width="220px">
									<a href="javascript:void(0);" onclick="openGtptxxglImageUploadDialog('LIAISON_SFZFM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
								</c:when>
								<c:otherwise>
									<img id="LIAISON_SFZFM_PATH_IMG" src="webpage/common/images/nopic.jpg"
										height="140px" width="220px">
									<a href="javascript:void(0);" onclick="openGtptxxglImageUploadDialog('LIAISON_SFZFM')">
										<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
									</a>
								</c:otherwise>
							</c:choose>				
							<input type="hidden" class="validate[required]" name="LIAISON_SFZFM" value="${gtptxxgl.LIAISON_SFZFM}">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div data-options="region:'south',split:true,border:false"  >
			<div class="eve_buttons">
				<c:if test="${projectDetail != true}">
					<input value="确定" type="submit"
						class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> 
				</c:if>
				<input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

