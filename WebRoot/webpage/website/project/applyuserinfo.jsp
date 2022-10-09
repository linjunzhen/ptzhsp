<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<eve:resources loadres="artdialog,swfupload,layer,json2,laydate,artdialog"></eve:resources>
<style type="text/css">
	.btn2{display:inline-block; width:128px; height:40px; line-height:40px; text-align:center; font-size:14px; color:#fff; background:url(./webpage/website/common/images/btnbg3.png) no-repeat;}
	.btn2:hover{color:#fff; position:relative; left:1px; top:1px;}
</style>
<script type="text/javascript">
	$(function(){
		var userType= '${sessionScope.curLoginMember.USER_TYPE}';
		if(userType==1){
			$("#sqjg").attr("style","display:none;");
			var idcard = $("input[name='SQRSFZ']").val();
			personCreditHandle(idcard);
		}else{
			$("#sqr").attr("style","display:none;");
			var idcard = $("input[name='SQJG_CODE']").val();
			personCreditHandle(idcard);
		}
		var sqrzjlx=$("#sqrzjlx").val();
		var jbrzjlx=$("#jbrzjlx").val();
		setValidate(sqrzjlx);
		setValidatejbr(jbrzjlx);

		//身份证号码转大写
		$("input[name='SQRSFZ']").val($("input[name='SQRSFZ']").val().toUpperCase());
		$("input[name='SQRSFZ']").on('blur', function(event) {
			var ZJLX = $("[name='SQRZJLX']").val();
			if(ZJLX=='SF'){
				$(this).val($(this).val().toUpperCase());
			}
		});
		$("input[name='JBR_ZJHM']").val($("input[name='JBR_ZJHM']").val());
		$("input[name='JBR_ZJHM']").on('blur', function(event) {
			var ZJLX = $("[name='JBR_ZJLX']").val();
			if(ZJLX=='SF'){
				$(this).val($(this).val().toUpperCase());
			}
		});

		var busTableName = $("input[name='EFLOW_BUSTABLENAME']").val();
		if (busTableName && busTableName.indexOf("T_BDCQLC_") != -1) {
			$("#jbrTip").hide();
		}


	})

	function setValidate(zjlx){
		if(zjlx=="SF"){
			$("#zjhm").addClass(",custom[vidcard]");
		}else{
			$("#zjhm").removeClass(",custom[vidcard]");
		}
	}
	function setValidatejbr(jbrzjlx){
		if(jbrzjlx=="SF"){
			$("#jbrzjhm").addClass(",custom[vidcard]");
		}else{
			$("#jbrzjhm").removeClass(",custom[vidcard]");
		}
	}

	//信用评级
	function personCreditHandle(idCard){
		AppUtil.ajaxNoProgress({
			url : "personCreditController.do?queryPersonCreditLevel",
			params : {
				ID_CARD : idCard
			},
			callback : function(resultJson) {
				var creditlevel = resultJson.jsonString;
				$("input[name='creditlevel']").val(creditlevel);
				if(creditlevel.indexOf('A')>=0){
					<c:forEach items="${applyMaters}" var="applyMater" varStatus="status" >
					var PERSON_CREDIT_USE = '${applyMater.PERSON_CREDIT_USE}';
					if(PERSON_CREDIT_USE.indexOf('A')>=0){
						var materCode = '${applyMater.MATER_CODE}';
						var options="<option value='3' selected='true'>信用免交</option>";
						$("#SQFS_"+materCode).append(options);
					}

					</c:forEach>
				}
			}
		});
	}
</script>
<div  id="userinfo_div">
	<div class="eui-card eui-declaration wysb3 ovh" id="applyuserinfoForm_B" onmouseover="validateForm('applyuserinfoForm')" >
		<form id="applyuserinfoForm" action="">
		 <input type="hidden" name="SQRMC" value="${sessionScope.curLoginMember.YHMC}"/>
           <input type="hidden" name="SQRSJH" value="${sessionScope.curLoginMember.SJHM}"/>
           <input type="hidden" name="SQRSFZ" value="${sessionScope.curLoginMember.ZJHM}"/>
			<input  name="SBMC" type="hidden" value="${sessionScope.curLoginMember.YHMC}-${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="BSYHLX" value="${sessionScope.curLoginMember.USER_TYPE}">
		<input type="hidden" name="creditlevel">

		<div class="tit" style="background-image: url(./webpage/website/project/images/wysb-i3.png);">申请人信息</div>
		<div class="eui-table-import">
			<table style="display: none">
				<colgroup>
					<col>
					<col width="200">
					<col>
					<col width="200">
				</colgroup>
				<tr>
					<th> 申报种类</th>
					<td style="border-right-style: none;"><input type="text"   class="ipt" readonly="readonly" style="border: thin;"
																 name="SXXZ" value="${serviceItem.SXXZ}" /></td>
					<th colspan="1" style="background-color: white;border-left-style: none;border-right-style: none;"></th>
					<td style="border-left-style: none;"></td>
				</tr>
			</table>
<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
			<table id="sqr"  >
				<tr>
					<th><i>*</i> 申请人</th>
					<td>
						<input type="text" maxlength="30" name="SQRMC"   class="ipt" value="${sessionScope.curLoginMember.YHMC}"/>
					</td>
					<th><i>*</i> 性别</th>
					<td>
						<eve:eveselect    clazz="ipt sel validate[required]"
										  dataParams="sex" value="${sessionScope.curLoginMember.YHXB}"
										  dataInterface="dictionaryService.findDatasForSelect"
										  defaultEmptyText="====选择性别====" name="SQRXB"></eve:eveselect>
						<%-- <input type="text" maxlength="30" name="JBR_SEX"   class="ipt" value="${sessionScope.curLoginMember.YHXB}"/> --%>
					</td>
				</tr>
				<tr>
					<th><i>*</i> 证件类型</th>
					<td>
						<eve:eveselect    clazz="ipt sel validate[required]" onchange="setValidate(this.value);"
										  dataParams="DocumentType" value="${sessionScope.curLoginMember.ZJLX}"
										  dataInterface="dictionaryService.findDatasForSelect" id="sqrzjlx"
										  defaultEmptyText="====选择证件类型====" name="SQRZJLX"></eve:eveselect>
						<%-- <input type="text" name="JBR_ZJLX"   class="ipt" value="${sessionScope.curLoginMember.ZJLX}"/> --%>
					</td>
					<th><i>*</i> 证件号码</th>
					<td>
						<input type="text" maxlength="30" name="SQRSFZ" id="zjhm" class="ipt validate[required]" value="${sessionScope.curLoginMember.ZJHM}" onchange="personCreditHandle(this.value)"/>
					</td>
				</tr>
				<th><i>*</i> 联系手机号</th>
				<td>
					<input type="text" maxlength="11" name="SQRSJH" class="ipt validate[required,custom[mobilePhoneLoose]]" value="${sessionScope.curLoginMember.SJHM}"/>
				</td>
				<th>电话号码</th>
				<td>
					<input type="text" maxlength="14" class="ipt validate[[],custom[fixPhoneWithAreaCode]]" name="SQRDHH" value="${sessionScope.curLoginMember.DHHM}"/>
				</td>
				<tr>
				</tr>
				<tr>
					<th><i>*</i> 联系地址</th>
					<td colspan="3">
						<input type="text" maxlength="60" class="ipt validate[required]" style="width: 82%;" name="SQRLXDZ" value="${sessionScope.curLoginMember.ZTZZ}" />

					</td>
				</tr>
				<tr>
					<th> 电子邮箱</th>
					<td colspan="3">
						<input type="text" maxlength="30" class="ipt validate[[],custom[email]]" name="SQRYJ" value="${sessionScope.curLoginMember.DZYX}"/>
					</td>
				</tr>
			</table>
</c:if>
			<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="bstable1" id="sqjg" >
				<tr>
					<th><i>*</i> 申请机构</th>
					<td>
						<input style="width:82%;" type="text" maxlength="60" name="SQJG_NAME"   class="ipt" value="${sessionScope.curLoginMember.YHMC}"/>
					</td>
					<th><i>*</i> 机构类型</th>
					<td>
						<eve:eveselect    clazz="ipt sel validate[required]"
										  dataParams="OrgType" value="${sessionScope.curLoginMember.JGLX}"
										  dataInterface="dictionaryService.findDatasForSelect"
										  defaultEmptyText="====选择机构类型====" name="SQJG_TYPE"></eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><i>*</i> 机构代码</th>
					<td>
						<input type="text" maxlength="18" name="SQJG_CODE" class="ipt validate[required]" value="${sessionScope.curLoginMember.ZZJGDM}"/>
					</td>
					<th> 统一社会信用代码</th>
					<td>
						<input type="text" maxlength="14" name="SQJG_CREDIT_CODE"   class="ipt" />
					</td>
				</tr>
				<tr>
					<th> 法人代表</th>
					<td>
						<input type="text" maxlength="14" name="SQJG_LEALPERSON"   class="ipt" value="${sessionScope.curLoginMember.FRDB}"/>
					</td>
					<th>联系电话</th>
					<td>
						<input type="text" maxlength="14" class="ipt validate[[],custom[fixPhoneWithAreaCode]]" name="SQJG_TEL" />
					</td>
				</tr>
				<tr>
					<th> 联系地址</th>
					<td colspan="3">
						<input type="text" maxlength="60"   class="ipt" style="width: 82%;" name="SQJG_ADDR" value="${sessionScope.curLoginMember.DWDZ}" />

					</td>
				</tr>
				<tr>
					<th><i>*</i>查询码</th>
					<td colspan="3">
						<input type="text" maxlength="8" name="BJCXMM" class="ipt  validate[required]" style="width: 500px;" class=" validate[required]" />
						<span style="color:red;"> 请自行设置数字查询码</span>
					</td>
				</tr>
			</table>

		</div>
			<div class="tit" style="background-image: url(./webpage/website/project/images/wysb-i3.png);">经办人信息</div>
			<div class="eui-table-import">

				<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
					<table>
						<colgroup>
							<col>
							<col width="200">
							<col>
							<col width="200">
						</colgroup>
						<tr>
							<th><i>*</i> 姓名</th>
							<td>
								<input type="text" maxlength="30" name="JBR_NAME" class="ipt validate[required]" value="${sessionScope.curLoginMember.YHMC}"/>
							</td>
							<th><i>*</i> 性别</th>
							<td>
								<eve:eveselect    clazz="ipt sel validate[required]"
												  dataParams="sex" value="${sessionScope.curLoginMember.YHXB}"
												  dataInterface="dictionaryService.findDatasForSelect"
												  defaultEmptyText="====选择性别====" name="JBR_SEX"></eve:eveselect>
							</td>
						</tr>
						<tr>
							<th><i>*</i> 证件类型</th>
							<td>
								<eve:eveselect    clazz="ipt sel validate[required]" onchange="setValidatejbr(this.value);"
												  dataParams="DocumentType" value="${sessionScope.curLoginMember.ZJLX}"
												  dataInterface="dictionaryService.findDatasForSelect" id="jbrzjlx"
												  defaultEmptyText="====选择证件类型====" name="JBR_ZJLX"></eve:eveselect>
							</td>
							<th><i>*</i> 证件号码</th>
							<td>
								<input type="text" maxlength="30" name="JBR_ZJHM" id="jbrzjhm" class="ipt validate[required]" value="${sessionScope.curLoginMember.ZJHM}"/>
							</td>
						</tr>
						<tr>
							<th><i>*</i> 手机号码</th>
							<td>
								<input type="text" maxlength="11" name="JBR_MOBILE" class="ipt validate[required,custom[mobilePhoneLoose]]" value="${sessionScope.curLoginMember.SJHM}"/>
							</td>
							<th> 联系电话</th>
							<td>
								<input type="text" maxlength="14" class="ipt validate[[],custom[fixPhoneWithAreaCode]]" name="JBR_LXDH" value="${sessionScope.curLoginMember.DHHM}"/>
							</td>
						</tr>
						<tr>
							<th>邮政编码</th>
							<td>
								<input type="text" maxlength="6" name="JBR_POSTCODE" class="ipt validate[[],custom[chinaZip]]" value="${sessionScope.curLoginMember.YZBM}"/>
							</td>
							<th><i>*</i> 电子邮箱</th>
							<td>
								<input type="text" maxlength="30" class="ipt validate[[],required,custom[email]]" name="JBR_EMAIL" value="${sessionScope.curLoginMember.DZYX}"/>
							</td>
						</tr>
						<tr>
							<th>联系地址</th>
							<td colspan="3">
								<input type="text" maxlength="60"   class="ipt" style="width: 82%;" name="JBR_ADDRESS" value="${sessionScope.curLoginMember.ZTZZ}" />
							</td>
						</tr>
						<tr>
							<th> 备注</th>
							<td colspan="3">
								<input type="text" maxlength="126"   class="ipt" style="width: 82%;" name="JBR_REMARK" />

							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
					<table >
						<tr>
							<th><i>*</i> 姓名</th>
							<td>
								<input type="text" maxlength="30" name="JBR_NAME"   class="ipt" value="${sessionScope.curLoginMember.JBRXM}"/>
							</td>
							<th><i>*</i> 性别</th>
							<td>
								<eve:eveselect    clazz="ipt sel validate[required]"
												  dataParams="sex"
												  dataInterface="dictionaryService.findDatasForSelect"
												  defaultEmptyText="====选择性别====" name="JBR_SEX"></eve:eveselect>
							</td>
						</tr>
						<tr>
							<th><i>*</i> 证件类型</th>
							<td>
								<eve:eveselect    clazz="ipt sel validate[required]" onchange="setValidatejbr(this.value);"
												  dataParams="DocumentType" value="SF"
												  dataInterface="dictionaryService.findDatasForSelect" id="jbrzjlx"
												  defaultEmptyText="====选择证件类型====" name="JBR_ZJLX"></eve:eveselect>
							</td>
							<th><i>*</i> 证件号码</th>
							<td>
								<input type="text" maxlength="30" name="JBR_ZJHM" id="jbrzjhm" class="ipt validate[required]" value="${sessionScope.curLoginMember.JBRSFZ}"/>
							</td>
						</tr>
						<tr>
							<th><i>*</i> 手机号码</th>
							<td>
								<input type="text" maxlength="11" name="JBR_MOBILE" class="ipt validate[required,custom[mobilePhoneLoose]]" />
							</td>
							<th> 联系电话</th>
							<td>
								<input type="text" maxlength="14" class="ipt validate[[],custom[fixPhoneWithAreaCode]]" name="JBR_LXDH" />
							</td>
						</tr>
						<tr>
							<th>邮政编码</th>
							<td>
								<input type="text" maxlength="6" name="JBR_POSTCODE" class="ipt validate[[],custom[chinaZip]]" />
							</td>
							<th>电子邮箱</th>
							<td>
								<input type="text" maxlength="30" class="ipt validate[[],custom[email]]" name="JBR_EMAIL" value="${sessionScope.curLoginMember.JBRYXDZ}"/>
							</td>
						</tr>
						<tr>
							<th>联系地址</th>
							<td colspan="3">
								<input type="text" maxlength="60"   class="ipt" style="width: 82%;" name="JBR_ADDRESS" value="${sessionScope.curLoginMember.DWDZ}" />
							</td>
						</tr>
						<tr>
							<th> 备注</th>
							<td colspan="3">
								<input type="text" maxlength="126"   class="ipt" style="width: 82%;" name="JBR_REMARK" />
							</td>
						</tr>
					</table>
				</c:if>
			</div>


<%--			<div class="bsbox clearfix"--%>
<%--				 <c:if test="${!fn:contains(serviceItem.PAPERSUB,'2')}">style="display: none;"</c:if>--%>
<%--			>--%>
<%--				<div class="tit" style="background-image: url(./webpage/website/project/images/wysb-i4.png);">--%>
<%--					<ul>--%>
<%--						<li class="on" style="background:none"><span>材料提交方式</span></li>--%>
<%--					</ul>--%>
<%--				</div>--%>
<%--				<div class="eui-table-import bsboxC">--%>
<%--					<table cellpadding="0" cellspacing="0" class="bstable1">--%>
<%--						<tr>--%>
<%--							<td colspan="2">--%>
<%--								　　<input type="radio" name="MAT_SENDTYPE" value="01" <c:if test="${busRecord.MAT_SENDTYPE=='01'||busRecord.MAT_SENDTYPE==null }">checked="checked"</c:if> />窗口提交--%>
<%--							</td>--%>
<%--							<td colspan="2">--%>
<%--								　　<input type="radio" name="MAT_SENDTYPE" value="02"  <c:if test="${busRecord.MAT_SENDTYPE=='02' }">checked="checked"</c:if> />快递送达--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="mataddressee" style="display: none;">--%>
<%--							<th><i>*</i> 寄件人姓名：</th>--%>
<%--							<td>--%>
<%--								<input type="text" maxlength="30" name="MAT_SEND_ADDRESSEE" id="mat_send_addressee"--%>
<%--									   class="ipt validate[required]" value="${busRecord.MAT_SEND_ADDRESSEE}"/>--%>
<%--							</td>--%>
<%--							<th><i>*</i> 电话号码:</th>--%>
<%--							<td>--%>
<%--								<input type="text" maxlength="30" name="MAT_SEND_MOBILE" id="mat_send_mobile"--%>
<%--									   class="ipt validate[required,custom[mobilePhoneLoose]]" value="${busRecord.MAT_SEND_MOBILE}"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="mataddrprov" style="display: none;">--%>
<%--							<th><i>*</i> 所属省市：</th>--%>
<%--							<td colspan="3">--%>
<%--								<input name="MAT_SEND_PROV" id="matprovince" class="easyui-combobox"  style="width:120px; height:26px;"--%>
<%--									   data-options="--%>
<%--			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',--%>
<%--			                method:'post',--%>
<%--			                valueField:'TYPE_NAME',--%>
<%--			                textField:'TYPE_NAME',--%>
<%--			                panelHeight:'auto',--%>
<%--			                required:true,--%>
<%--			                editable:false,--%>
<%--			                onSelect:function(rec){--%>
<%--							   $('#matcity').combobox('clear');--%>
<%--							   if(rec.TYPE_CODE){--%>
<%--							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;--%>
<%--							       $('#matcity').combobox('reload',url);--%>
<%--							   }--%>
<%--							}--%>
<%--	                " value="" />--%>
<%--								<input name="MAT_SEND_CITY" id="matcity" class="easyui-combobox" style="width:120px; height:26px;"--%>
<%--									   data-options="--%>
<%--			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',--%>
<%--			                method:'post',--%>
<%--			                valueField:'TYPE_NAME',--%>
<%--			                textField:'TYPE_NAME',--%>
<%--			                panelHeight:'auto',--%>
<%--			                required:true,--%>
<%--			                editable:false,--%>
<%--			                onSelect:function(rec){--%>
<%--							   $('#matcounty').combobox('clear');--%>
<%--							   if(rec.TYPE_CODE){--%>
<%--							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;--%>
<%--							       $('#matcounty').combobox('reload',url);--%>
<%--							   }--%>
<%--							}--%>
<%--	                " value="" />--%>
<%--								<input name="MAT_SEND_COUNTY" id="matcounty" class="easyui-combobox" style="width:120px; height:26px;"--%>
<%--									   data-options="--%>
<%--			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',--%>
<%--			                method:'post',--%>
<%--			                valueField:'TYPE_NAME',--%>
<%--			                textField:'TYPE_NAME',--%>
<%--			                panelHeight:'auto',--%>
<%--			                editable:false,--%>
<%--			                onSelect:function(rec){--%>
<%--							   $('#sllx').combobox('clear');--%>
<%--							   if(rec.TYPE_CODE){--%>
<%--				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);--%>
<%--							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';--%>
<%--							       $('#sllx').combobox('reload',url);--%>
<%--							   }--%>
<%--							}--%>
<%--	                " value="" />--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="matpostcode" style="display: none;">--%>
<%--							<th><i>*</i> 邮政编码：</th>--%>
<%--							<td>--%>
<%--								<input type="text" maxlength="30" name="MAT_SEND_POSTCODE" id="mat_send_postcode"--%>
<%--									   class="ipt validate[[],custom[chinaZip]]" value="${busRecord.MAT_SEND_POSTCODE}"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="mataddr" style="display: none;">--%>
<%--							<th><i>*</i> 详细地址：</th>--%>
<%--							<td colspan="3">--%>
<%--								<input type="text" maxlength="126" class="ipt" style="width: 82%;" name="MAT_SEND_ADDR" id="mat_send_addr"--%>
<%--									   class="validate[required]" value="${busRecord.MAT_SEND_ADDR}"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="mataddrremarks" style="display: none;">--%>
<%--							<th><i>*</i> 邮递备注：</th>--%>
<%--							<td colspan="2">--%>
<%--								<input type="text" maxlength="126" class="ipt" style="width: 82%;" name="MAT_SEND_REMARKS" id="mat_send_remarks"--%>
<%--									   class="validate[]" value="${busRecord.MAT_SEND_REMARKS}"/>--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								<a href="javascript:void(0);" onclick="selectMataddr();"  class="btn2">选择地址</a>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--					</table>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			<div class="bsbox clearfix">--%>
<%--				<div class="tit" style="background-image: url(./webpage/website/project/images/wysb-i5.png);">--%>
<%--					<ul>--%>
<%--						<li class="on" style="background:none"><span>办理结果领取方式</span></li>--%>
<%--					</ul>--%>
<%--				</div>--%>
<%--				<div class="eui-table-import bsboxC">--%>
<%--					<table cellpadding="0" cellspacing="0" class="bstable1">--%>
<%--						<tr>--%>
<%--							<td colspan="2">--%>
<%--								　　<input type="radio" name="FINISH_GETTYPE" value="01" <c:if test="${serviceItem.FINISH_GETTYPE=='01'||serviceItem.FINISH_GETTYPE==null||serviceItem.FINISH_GETTYPE==''||!fn:contains(serviceItem.FINISH_GETTYPE,'02') }">checked="checked"</c:if> />窗口领取--%>
<%--							</td>--%>
<%--							<td colspan="2">--%>
<%--								　　<input type="radio" name="FINISH_GETTYPE" value="02"--%>
<%--										 <c:if test="${!fn:contains(serviceItem.FINISH_GETTYPE,'02')}">disabled="disabled"</c:if>--%>
<%--										 <c:if test="${fn:contains(serviceItem.FINISH_GETTYPE,'02') }">checked="checked"</c:if> />快递送达--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="addressee" style="display: none;">--%>
<%--							<th><i>*</i> 收件人姓名：</th>--%>
<%--							<td>--%>
<%--								<input type="text" maxlength="30" name="RESULT_SEND_ADDRESSEE" id="result_send_addressee"--%>
<%--									   class="ipt validate[required]" value="${busRecord.RESULT_SEND_ADDRESSEE}"/>--%>
<%--							</td>--%>
<%--							<th><i>*</i> 电话号码:</th>--%>
<%--							<td>--%>
<%--								<input type="text" maxlength="30" name="RESULT_SEND_MOBILE" id="result_send_mobile"--%>
<%--									   class="ipt validate[required,custom[mobilePhoneLoose]]" value="${busRecord.RESULT_SEND_MOBILE}"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="addrprov" style="display: none;">--%>
<%--							<th><i>*</i> 所属省市：</th>--%>
<%--							<td colspan="3">--%>
<%--								<input name="RESULT_SEND_PROV" id="province" class="easyui-combobox"  style="width:120px; height:26px;"--%>
<%--									   data-options="--%>
<%--			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',--%>
<%--			                method:'post',--%>
<%--			                valueField:'TYPE_NAME',--%>
<%--			                textField:'TYPE_NAME',--%>
<%--			                panelHeight:'auto',--%>
<%--			                panelMaxHeight:'300px',--%>
<%--			                required:true,--%>
<%--			                editable:false,--%>
<%--			                onSelect:function(rec){--%>
<%--							   $('#city').combobox('clear');--%>
<%--							   if(rec.TYPE_CODE){--%>
<%--							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;--%>
<%--							       $('#city').combobox('reload',url);--%>
<%--							   }--%>
<%--							}--%>
<%--	                " value="" />--%>
<%--								<input name="RESULT_SEND_CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;"--%>
<%--									   data-options="--%>
<%--			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',--%>
<%--			                method:'post',--%>
<%--			                valueField:'TYPE_NAME',--%>
<%--			                textField:'TYPE_NAME',--%>
<%--			                panelHeight:'auto',--%>
<%--			                panelMaxHeight:'300px',--%>
<%--			                required:true,--%>
<%--			                editable:false,--%>
<%--			                onSelect:function(rec){--%>
<%--							   $('#county').combobox('clear');--%>
<%--							   if(rec.TYPE_CODE){--%>
<%--							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;--%>
<%--							       $('#county').combobox('reload',url);--%>
<%--							   }--%>
<%--							}--%>
<%--	                " value="" />--%>
<%--								<input name="RESULT_SEND_COUNTY" id="county" class="easyui-combobox" style="width:120px; height:26px;"--%>
<%--									   data-options="--%>
<%--			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',--%>
<%--			                method:'post',--%>
<%--			                valueField:'TYPE_NAME',--%>
<%--			                textField:'TYPE_NAME',--%>
<%--			                panelHeight:'auto',--%>
<%--			                panelMaxHeight:'300px',--%>
<%--			                editable:false,--%>
<%--			                onSelect:function(rec){--%>
<%--							   $('#sllx').combobox('clear');--%>
<%--							   if(rec.TYPE_CODE){--%>
<%--				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);--%>
<%--							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';--%>
<%--							       $('#sllx').combobox('reload',url);--%>
<%--							   }--%>
<%--							}--%>
<%--	                " value="" />--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="addrpostcode" style="display: none;">--%>
<%--							<th><i>*</i> 邮政编码：</th>--%>
<%--							<td>--%>
<%--								<input type="text" maxlength="30" name="RESULT_SEND_POSTCODE" id="result_send_postcode"--%>
<%--									   class="ipt validate[[],custom[chinaZip]]" value="${busRecord.RESULT_SEND_POSTCODE}"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="addr" style="display: none;">--%>
<%--							<th><i>*</i> 详细地址：</th>--%>
<%--							<td colspan="3">--%>
<%--								<input type="text" maxlength="126" class="ipt" style="width: 82%;" name="RESULT_SEND_ADDR" id="result_send_addr"--%>
<%--									   class="validate[required]" value="${busRecord.RESULT_SEND_ADDR}"/>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr id="addrremarks" style="display: none;">--%>
<%--							<th><i>*</i> 邮递备注：</th>--%>
<%--							<td colspan="2">--%>
<%--								<input type="text" maxlength="126" class="ipt" style="width: 82%;" name="RESULT_SEND_REMARKS" id="result_send_remarks"--%>
<%--									   class="validate[]" value="${busRecord.RESULT_SEND_REMARKS}"/>--%>
<%--							</td>--%>
<%--							<td>--%>
<%--								<a href="javascript:void(0);" onclick="selectResaddr();"  class="btn2">选择地址</a>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--					</table>--%>
<%--				</div>--%>
<%--			</div>--%>

</form>
		<div class="eui-flex tc eui-sx-btn">
			<a id="back2" class="eui-btn light" href="javascript:void(0)">上一步</a>
			<c:if test="${isQueryDetail!='true'}">
			<a class="eui-btn o"  onclick="tempSaveWebSiteFlowForm('T_GCXM_XMJBXX_FORM')">保存草稿</a>
            </c:if>
			<a id="goto4" class="eui-btn" href="javascript:void(0)">下一步</a>
		</div>

	</div>
</div>
