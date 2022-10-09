<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<script type="text/javascript">
    
    //法律文书送达地址(同注册地址)动态选择
	function setSendLawAddr() {
		var val = $("[name='IS_REGISTER_ADDR']:checked").val();
		if(val=='1'){
			$("input[name='LAW_SEND_ADDR']").val($("input[name='REGISTER_ADDR_CHANGE']").val());
			$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
		}else{
			$("input[name='LAW_SEND_ADDR']").removeAttr("disabled");
		}
	}
	$(function () {
		setSendLawAddr();
	})
</script>
<style>
.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
</style>
<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	SysUser sysUser = AppUtil.getLoginUser();
	//获取登录用户的角色CODE
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否经营范围备注描述管理员
    boolean isJyfw = roleCodes.contains("JYFW_ALL");
	//判断是否超级管理员或经营范围备注描述管理员
	if("__ALL".equals(resKey)||isJyfw){
	    request.setAttribute("isJyfwAll",true);
	}else{
	    request.setAttribute("isJyfwAll",false);
	}
%>
<style>



.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
</style>
<%--面签审核界面 --%>
<div id="SIGNAUDIT"  style="display: none" >
	<jsp:include page="./signAudit.jsp" />
</div>
<%--结束面签审核界面 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">企业基本信息</th>
	</tr>
</table>
<!--原企业名称  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="ymcxx" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>原企业名称：</td>
		<td colspan="3">
			<input type="text" class="tab_text"  maxlength="128"  style="width:600px;"
				   name="COMPANY_NAME" value="${busRecord.COMPANY_NAME }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">原集团名称：</td>
		<td>
			<input type="text" class="tab_text"  maxlength="256"
				   name="GROUP_NAME" value="${busRecord.GROUP_NAME }"/>
		</td>
		<td class="tab_width"> 原集团简称：</td>
		<td><input type="text" class="tab_text" maxlength="128"
				   name="GROUP_ABBRE" value="${busRecord.GROUP_ABBRE}"/></td>
	</tr>
	<tr id="unicode" >
		<td class="tab_width"><font class="tab_color">*</font>统一社会信用代码：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" 
				name="SOCIAL_CREDITUNICODE" maxlength="32" value="${busRecord.SOCIAL_CREDITUNICODE}"/>
		</td>
	</tr>	
</table>	

<!--变更企业名称 -->		
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bgmcxx"  style="display:none">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业名称(变更后)：</td>
		<td colspan="3">
			<input type="text" class="tab_text"  maxlength="128"  style="width:600px;"
				   name="COMPANY_NAME_CHANGE" value="${busRecord.COMPANY_NAME_CHANGE}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">集团名称(变更后)：</td>
		<td>
			<input type="text" class="tab_text"  maxlength="256"
				   name="GROUP_NAME_CHANGE" value="${busRecord.GROUP_NAME_CHANGE}"/>
		</td>
		<td class="tab_width"> 集团简称(变更后)：</td>
		<td><input type="text" class="tab_text" maxlength="128"
				   name="GROUP_ABBRE_CHANGE" value="${busRecord.GROUP_ABBRE_CHANGE}"/></td>
	</tr>
</table>

<!--原注册地址  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="ydzxx" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>原注册地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
			name="REGISTER_ADDR" maxlength="256" value="${busRecord.REGISTER_ADDR}"/></td>
	</tr>
</table>

<!--变更注册地址  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bgdzxx"  style="display:none">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注册地址：(变更后)</td>
		<td colspan="2"><input type="text" class="tab_text validate[required]" style="width:600px;"  onchange="setSendLawAddr()"
			name="REGISTER_ADDR_CHANGE" maxlength="256" value="${busRecord.REGISTER_ADDR_CHANGE }"/></td>
		<td colspan="1"><font class="tab_color">*</font>面积：
			<input type="text" class="tab_text" maxlength="16" style="width:15%;height:20px;"
				   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
			<font style="font-size: 16px;margin-left: 10px;">㎡</font>
		</td>
	</tr>
	<tr>
		<td class="tab_width">法律文书送达地址(同注册地址)：</td>
		<td >
			<input type="radio" class="radio " name="IS_REGISTER_ADDR" value="1" onclick="setSendLawAddr('1');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
			<input type="radio" class="radio " name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
		</td>
		<td class="tab_width">法律文书送达地址: </td>
		<td >
			<input type="text" class="tab_text"
				   name="LAW_SEND_ADDR" maxlength="256"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">法律文书电子送达地址：</td>
		
		<td colspan="3">
			邮箱：<input type="text" class="tab_text "
					  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
			<span style="margin-left: 50px;">传真：</span><input type="text" class="tab_text "
															  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
			<span style="margin-left: 50px;">即时通讯账号（如微信）：</span><input type="text" class="tab_text "
																	   name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>房屋所有权人名称：</td>
		<td><input  type="text" class="tab_text  "
					name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_REGISTER_OWNER}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>房屋所有权人联系电话：</td>
		<td><input type="text" class="tab_text "
				   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_REGISTER_TEL}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>取得方式：</td>
		<td colspan="3" style="width:500px;">
			<eve:eveselect clazz="tab_text" dataParams="wayOfGet"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择取得方式" name="RESIDENCE_REGISTER_WAYOFGET"  value="${busRecord.RESIDENCE_REGISTER_WAYOFGET}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>是否属于军队房产：</td>
		<td >
			<eve:eveselect clazz="tab_text" dataParams="armyHourse"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择军队房产情况" name="ARMY_REGISTER_HOURSE"  value="${busRecord.ARMY_REGISTER_HOURSE}">
			</eve:eveselect>
		</td>
		<td class="tab_width">其他证明：</td>
		<td >
			<input type="text" class="tab_text"
				   name="ARMYHOURSE_REGISTER_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REGISTER_REMARKS}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width" >是否有生产经营地址：</td>
		<td colspan="3">
	
		<input type="radio" name="IS_BUSSINESS_ADDR" value="1" <c:if test="${busRecord.IS_BUSSINESS_ADDR==1 }">checked="checked"</c:if> >是
		<input type="radio" name="IS_BUSSINESS_ADDR" value="0" <c:if test="${busRecord.IS_BUSSINESS_ADDR==0 }">checked="checked"</c:if> >否
		</td>
	</tr>
	<c:if test="${busRecord.IS_BUSSINESS_ADDR!=0}">
	<tr>
		<td class="tab_width">生产经营地址：</td>
		<td colspan="2"><input type="text" class="tab_text " style="width:600px;"
			name="BUSSINESS_ADDR" maxlength="128" value="${busRecord.BUSSINESS_ADDR }"/></td>
		<td colspan="1">面积：
			<input type="text"  maxlength="16" style="width:15%;height:20px;"
				   name="BUSSINESS_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.BUSSINESS_SQUARE_ADDR}"/>
			<font style="font-size: 16px;margin-left: 10px;">㎡</font>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>房屋所有权人名称：</td>
		<td><input  type="text" class="tab_text  "
					name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>房屋所有权人联系电话：</td>
		<td><input type="text" class="tab_text "
				   name="PLACE_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_TEL}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>取得方式：</td>
		<td colspan="3" style="width:500px;">
			<eve:eveselect clazz="tab_text" dataParams="wayOfGet"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择取得方式" name="RESIDENCE_WAYOFGET"  value="${busRecord.RESIDENCE_WAYOFGET}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>是否属于军队房产：</td>
		<td >
			<eve:eveselect clazz="tab_text" dataParams="armyHourse"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择军队房产情况" name="ARMY_HOURSE"  value="${busRecord.ARMY_HOURSE}">
			</eve:eveselect>
		</td>
		<td class="tab_width">其他证明：</td>
		<td >
			<input type="text" class="tab_text"
				   name="ARMYHOURSE_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REMARKS}"/>
		</td>
	</tr>
	</c:if>
</table>

<!--企业电话信息  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="qydh" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业联系电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="CONTACT_PHONE" value="${busRecord.CONTACT_PHONE }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="" value="350400"/></td>
	</tr>
</table>

<!--原企业类型  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="qylx" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>原公司类型：</td>
		<td colspan="3">
			<input name="COMPANY_TYPE" id="COMPANY_TYPE" class="easyui-combobox" style="width:182px; height:26px;" disabled="disabled"
			data-options="
                url:'dicTypeController/load.do?parentTypeCode=yxzrgssl',
                method:'post',
                valueField:'TYPE_CODE',
                textField:'TYPE_NAME',
                panelHeight:'auto',
                required:true,
                editable:false
              " value="${busRecord.COMPANY_TYPE}" />
		</td>
	</tr>
</table>

<!--变更企业类型  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="gslxtable"  style="display:none">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>公司类型：（变更后）</td>
		<td colspan="3">					
			<input name="COMPANY_SETTYPE" id="gslx" class="easyui-combobox"  style="width:182px; height:26px;"
				data-options="
	                url:'dicTypeController/load.do?parentTypeCode=nzgs',
	                method:'post',
	                valueField:'TYPE_CODE',
	                textField:'TYPE_NAME',
	                panelHeight:'auto',
	                required:true,
	                editable:false,
	                onSelect:function(rec){
					   $('#gslxzl').combobox('clear');
					   if(rec.TYPE_CODE){
					       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
					       $('#gslxzl').combobox('reload',url);  
					   }
					}
               " value="${busRecord.COMPANY_SETTYPE}" />					
			<input name="COMPANY_TYPE_CHANGE" id="gslxzl" class="easyui-combobox" style="width:182px; height:26px;" 
				data-options="
	                url:'dicTypeController/load.do?parentTypeCode=yxzrgssl',
	                method:'post',
	                valueField:'TYPE_CODE',
	                textField:'TYPE_NAME',
	                panelHeight:'auto',
	                required:true,
	                editable:false,
	                onSelect:function(rec){
					   $('#sllx').combobox('clear');
					   if(rec.TYPE_CODE){
					       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
					       $('#sllx').combobox('reload',url);  
					   }
					}
               " value="${busRecord.COMPANY_TYPE_CHANGE}" />	                					
		</td>
	</tr>
	<%-- <tr>
		<td class="tab_width"><font class="tab_color">*</font>公司类型：(变更后)</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" disabled="disabled"
				name="COMPANY_TYPE" value="${busRecord.COMPANY_TYPE}"/>		
		</td>
	</tr> --%>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="gsxz" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>公司组织架构：</td>
		<td colspan="3">					
			<input name="COMPANY_NATURE" id="sllx" class="easyui-combobox" style="width:182px; height:26px;" 
				data-options="			                
	                url:'dictionaryController/loadData.do?typeCode=0&orderType=asc',
	                method:'post',
	                valueField:'DIC_CODE',
	                textField:'DIC_NAME',
	                panelHeight:'auto',
	                required:true,
	                editable:false,
	                onSelect:function(rec){	
					   $('#jlzw').find('option').eq(1).attr('selected','selected');						       
					   if(rec.DIC_CODE=='01'){
					   		$('[id=\'appointor\']').val('股东');
					   		$('[id=\'sappointor\']').val('股东');
							$('#dszw').find('option').eq(4).attr('selected','selected');
							$('#jszw').find('option').eq(1).attr('selected','selected');
							changelegaljob(0);
					   }else if(rec.DIC_CODE=='02'){
					   		$('[id=\'appointor\']').val('董事会');
					   		$('[id=\'sappointor\']').val('股东会');
							$('#dszw').find('option').eq(1).attr('selected','selected');
							$('#jszw').find('option').eq(1).attr('selected','selected');
							changelegaljob(1);
					   }else if(rec.DIC_CODE=='03'){
					   		$('[id=\'appointor\']').val('股东会');
					   		$('[id=\'sappointor\']').val('股东会');
							$('#jszw').find('option').eq(1).attr('selected','selected');
							$('#dszw').find('option').eq(4).attr('selected','selected');
							changelegaljob(0);
					   }else if(rec.DIC_CODE=='04'){
					   		$('[id=\'appointor\']').val('董事会');
					   		$('[id=\'sappointor\']').val('职工代表大会');
							$('#jszw').find('option').eq(2).attr('selected','selected');
							$('#dszw').find('option').eq(1).attr('selected','selected');
							changelegaljob(1);
					   }
					}
               " value="${busRecord.COMPANY_NATURE}" />
		</td>
	</tr>
</table>

<!--原经营期限  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jyqx" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>原经营期限：</td>
		<td>
			<input type="radio" readonly="readonly" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
			<input type="radio" readonly="readonly" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
		</td>
		<td class="tab_width">原经营期限年数：</td>
		<td><input type="text" class="tab_text validate[custom[integerplus]]" 
			name="BUSSINESS_YEARS"  value="${busRecord.BUSSINESS_YEARS}"/></td>
	</tr> 
	<tr>
		<td class="tab_width">原经营期限起始：</td>
		<td>
			<input type="text" class="laydate-icon validate[]" id="ystart" readonly="readonly"
			name="YEAR_FROM"  value="${busRecord.YEAR_FROM}"/>
		</td>
		<td class="tab_width">原经营期限结束：</td>
		<td>
			<input type="text" class="laydate-icon validate[]" id="yend" readonly="readonly"
			name="YEAR_TO"  value="${busRecord.YEAR_TO}"/>
		</td>
	</tr>
	
</table>

<!--变更经营期限  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bgjyqx" style="display:none">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营期限：(变更后)</td>
		<td>
			<input type="radio" name="OPRRATE_TERM_TYPE_CHANGE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE_CHANGE==1 }">checked="checked"</c:if> >年
			<input type="radio" name="OPRRATE_TERM_TYPE_CHANGE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE_CHANGE==0 }">checked="checked"</c:if> >长期
		</td>
		<td class="tab_width">经营期限年数：(变更后)</td>
		<td><input type="text" class="tab_text validate[[],custom[integerplus]]" 
			name="BUSSINESS_YEARS_CHANGE" value="${busRecord.BUSSINESS_YEARS_CHANGE }"/>年</td>
	</tr>
	<%-- <tr>
		<td class="tab_width"><font class="tab_color">*</font>经营期限起始：(变更后)</td>
		<td>
			<input type="text" class="laydate-icon validate[required]" id="cstart" readonly="readonly"
			name="YEAR_FROM_CHANGE"  value="${busRecord.YEAR_FROM_CHANGE}"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>经营期限结束：(变更后)</td>
		<td>
			<input type="text" class="laydate-icon validate[required]" id="cend" readonly="readonly"
			name="YEAR_TO_CHANGE"  value="${busRecord.YEAR_TO_CHANGE}"/>
		</td>
	</tr> --%>
</table>

<!--原经营范围  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jjfw" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>原经营范围：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" readonly="readonly"
				class="eve-textarea validate[validate[required],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.BUSSINESS_SCOPE }</textarea> 
			<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID }">
		</td>
	</tr>
</table>

<!-- 变更经营范围  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bgjjfw" style="display:none">
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围：（变更后）</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="BUSSINESS_SCOPE_CHANGE" readonly="readonly"
				class="eve-textarea validate[validate[required],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.BUSSINESS_SCOPE_CHANGE}</textarea> 
			<input type="hidden" name="BUSSINESS_SCOPE_ID_CHANGE" value="${busRecord.BUSSINESS_SCOPE_ID_CHANGE }">
			<input type="button" class="eflowbutton" name="scopeChose" value="选择" onclick="showSelectJyfwNew();">
		</td>
	</tr>
</table>

<!--原投资行业 -->
<%-- <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="tzhy" >
	<tr>
		<td class="tab_width"> 原投资行业：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
				class="eve-textarea validate[validate[],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.INVEST_INDUSTRY }</textarea> 
			<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID }">
		</td>
	</tr>
</table>
 --%>
<!--变更投资行业 -->
<%-- <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bgtzhy" >
	<tr>
		<td class="tab_width"> 投资行业：（变更后）</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
				class="eve-textarea validate[validate[],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.INVEST_INDUSTRY }</textarea> 
			<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID }">
		</td>
	</tr>
</table> --%>

<!-- 原行业编码 -->
<%-- <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="hybm" >
	<tr>
		<td class="tab_width"> 原行业编码：</td>
		<td colspan="3"><input type="text" class="tab_text " style="width:740px;" readonly="readonly"
			name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE }"/></td>
	</tr>
</table> --%>

<!-- 变更行业编码 -->
<%-- <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bghybm" >
	<tr>
		<td class="tab_width"> 行业编码：(变更后)</td>
		<td colspan="3"><input type="text" class="tab_text " style="width:740px;" readonly="readonly"
			name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE }"/></td>
	</tr>
</table> --%>

<!-- 从业人数&行政地区-->
<%-- <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="cyrs" >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>职工人数/从业人数：</td>
		<td><input type="text" class="tab_text validate[required,custom[integerplus]]"
			name="STAFF_NUM"  value="${busRecord.STAFF_NUM }"/>人</td>
		<td class="tab_width"> 所在地行政区：</td>
		<td colspan="3"><input type="text" class="tab_text" disabled="disabled"
			name="LOCAL_REGION" value="福州市平潭县" /></td>
	</tr>
</table> --%>

