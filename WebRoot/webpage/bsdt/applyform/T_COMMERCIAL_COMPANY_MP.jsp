<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
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
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">企业基本信息</th>
	</tr>



	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" style="width:140px;"
				   name="AREA_NAME"  maxlength="64" placeholder="请输入行政区划" value="平潭综合实验区" readonly/>
			<input type="text" class="tab_text  validate[required,maxSize[8],minSize[2]]" style="width:140px;" onclick="setCompanyNameOfMp()"
				   name="WORD_NUM"  maxlength="8" minlength="2"  placeholder="请输入字号" value="${busRecord.WORD_NUM}"/>
			<input type="text" class="tab_text validate[required]" style="width:140px;" onclick="selectChildIndustryView()"
				   name="CHILD_INDUSTRY_NAME" maxlength="64" placeholder="请选择子行业" value="${busRecord.CHILD_INDUSTRY_NAME}"/>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="OrgTypeOfMp"
						   dataInterface="dictionaryService.findDatasForSelect" onchange="setCompanyNameOfMp()"
						   defaultEmptyText="请选择组织形式" name="ORG_TYPE"  value="${busRecord.ORG_TYPE}">
			</eve:eveselect>
		</td>
	</tr>


	<tr>
		<td class="tab_width">集团名称：</td>
		<td>
			<input type="text" class="tab_text"  maxlength="256"
				   name="GROUP_NAME" value="${busRecord.GROUP_NAME }"/>
		</td>
		<td class="tab_width"> 集团简称：</td>
		<td><input type="text" class="tab_text" maxlength="128"
				   name="GROUP_ABBRE" value="${busRecord.GROUP_ABBRE }"/></td>
	</tr>
	<%--<tr style="display: none;">--%>
		<%--<td class="tab_width"><font class="tab_color">*</font>是否已通过名称自主选用：</td>--%>
		<%--<td>--%>
			<%--<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1 }">checked="checked"</c:if> >是--%>
			<%--<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==0 }">checked="checked"</c:if> >否--%>
		<%--</td>--%>
		<%--<td class="tab_width"> 名称自主选用文号：</td>--%>
		<%--<td><input type="text" class="tab_text" disabled="disabled" maxlength="32"--%>
			<%--name="PREAPPROVAL_NUM" value="${busRecord.PREAPPROVAL_NUM }"/></td>--%>
	<%--</tr>--%>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注册地址：</td>
		<td colspan="2"><input type="text" class="tab_text validate[required]" style="width:600px;"
			name="REGISTER_ADDR" maxlength="126" value="${busRecord.REGISTER_ADDR }"/></td>
		<td colspan="1"><font class="tab_color">*</font>面积：
			<input type="text" class="tab_text" maxlength="16" style="width:15%;height:20px;"
				   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
			<font style="font-size: 16px;margin-left: 10px;">㎡</font>
		</td>
	</tr>




	<script type="text/javascript">
		function setSendLawAddr(val) {
			if(val=='1'){
				$("input[name='LAW_SEND_ADDR']").val($("input[name='REGISTER_ADDR']").val());
				$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
			}else{
				$("input[name='LAW_SEND_ADDR']").attr("disabled","");
			}
		}
	</script>
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
				   name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/>
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
		<td>是否有生产经营地址：</td>
	<td class="tab_width" colspan="3">

	<input type="radio" name="IS_BUSSINESS_ADDR" value="1" <c:if test="${busRecord.IS_BUSSINESS_ADDR==1 }">checked="checked"</c:if> >是
	<input type="radio" name="IS_BUSSINESS_ADDR" value="0" <c:if test="${busRecord.IS_BUSSINESS_ADDR==0 }">checked="checked"</c:if> >否
	</td>
	</tr>


	<tr style="display: none">
		<td class="tab_width" rowspan="4"><font class="tab_color">*</font>是否仅作为联络地址：</td>
		<td rowspan="4">
			<input type="radio" name="IS_ONLY_CONTACT" value="1" <c:if test="${busRecord.IS_ONLY_CONTACT==1 }">checked="checked"</c:if> >是
			<input type="radio" name="IS_ONLY_CONTACT" value="0" <c:if test="${busRecord.IS_ONLY_CONTACT==0 }">checked="checked"</c:if> >否
		</td>
	</tr>
	<tr style="display: none">
		<td class="tab_width">出租（借）方：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="LESSOR" maxlength="32" value="${busRecord.LESSOR }"/></td>
	</tr>
	<tr style="display: none">
		<td class="tab_width">出租（借）时间：</td>
		<td><input type="text" class="laydate-icon" id="start" disabled="disabled" readonly="readonly"
			name="LEASE_START_DATE" value="${busRecord.LEASE_START_DATE }"/>至
			<input type="text" class="laydate-icon" id="end" disabled="disabled" readonly="readonly"
			name="LEASE_END_DATE" value="${busRecord.LEASE_END_DATE }"/></td>
	</tr>
	<tr style="display: none">
		<td class="tab_width">租赁合同签订时间：</td>
		<td><input type="text" class="laydate-icon" id="sign" disabled="disabled" readonly="readonly"
			name="SINGING_TIME" value="${busRecord.SINGING_TIME }"/></td>
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
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业联系电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="CONTACT_PHONE" value="${busRecord.CONTACT_PHONE }"/></td>
			
		<td class="tab_width"><font class="tab_color">*</font>企业规模：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjqyfl"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择企业声明" name="ENTERPRISE_STATEMENT" value="${busRecord.ENTERPRISE_STATEMENT }">
			</eve:eveselect>
			<img src="webpage/bsdt/applyform/images/question.png" style="cursor: pointer;" onclick="window.open('<%=path%>/contentController/view.do?contentId=996');">
		</td>
	</tr>

	<tr>
		<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="POST_CODE" value="350400" /></td>
		<td class="tab_width"><font class="tab_color">*</font>公司类型：</td>
		<td>
			<input type="text" class="tab_text validate[required]" disabled="disabled"
				name="COMPANY_TYPE" value="${busRecord.COMPANY_TYPE }"/>		
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营期限：</td>
		<td>
			<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE==1 }">checked="checked"</c:if> >年
			<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0 }">checked="checked"</c:if> >长期
		</td>
		<td class="tab_width"> 经营期限年数：</td>
		<td><input type="text" class="tab_text validate[[],custom[integerplus]]" 
			name="BUSSINESS_YEARS" value="${busRecord.BUSSINESS_YEARS }"/>年</td>
	</tr>

	<tr>
		<td class="tab_width"><font class="tab_color">*</font>主营范围：</td>
		<td colspan="3"><input type="text" class="tab_text" readonly="readonly"
				   name="MAIN_BUSSINESS_NAME" value="${busRecord.MAIN_BUSSINESS_NAME}" /></td>
	</tr>


	<tr>
		<td class="tab_width">行业信息：</td>
		<td colspan="3">
			<input type="text" class="tab_text" style="width:140px;"
				   name="ML_NAME"  placeholder="门类名称"  maxlength="128"  value="${busRecord.ML_NAME}" readonly/>
			<input type="text" class="tab_text " style="width:140px;"
				   name="DL_NAME"  placeholder="大类名称" value="${busRecord.DL_NAME}" readonly/>
			<input type="text" class="tab_text" style="width:140px;"
				   name="ZL_NAME"  placeholder="中类名称"  value="${busRecord.ZL_NAME}" readonly/>
			<input type="text" class="tab_text " style="width:140px;"
				   name="XL_NAME"  placeholder="小类名称"  value="${busRecord.XL_NAME}" readonly/>
		</td>
	</tr>




	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" readonly="readonly"
				class="eve-textarea validate[validate[required],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.BUSSINESS_SCOPE }</textarea> 
			<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID }">
			<input type="button" name="scopeChose" value="选择" onclick="showSelectJyfwNew();">
			<!-- <input type="button" name="scopeChose" value="选择" onclick="choseScope()"> -->
		</td>
	</tr>
	<tr>
		<td class="tab_width">常用限定用语</td>
		<td colspan="3">
		<div>
			<eve:eveselect clazz="tab_text validate[]" dataParams="yjfwcyxdyy"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setJyfw(this.value)"
			defaultEmptyText="选择常用限定用语" name="yjfwcyxdyy" value="${busRecord.ENTERPRISE_STATEMENT }">
			</eve:eveselect>
		</div>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 经营范围备注描述：</td>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"  <c:if test="${isJyfwAll == false}">disabled="disabled"</c:if>
			name="SCOPE_REMARK" maxlength="128" value="${busRecord.SCOPE_REMARK }"/>
			<c:if test="${isJyfwAll == true}"><a class="eflowbutton" onclick="saveScopeRemark();" style="height: 28px;float: none;">保存</a></c:if></td>
	</tr>
	<tr>
		<td class="tab_width"> 投资行业：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
				class="eve-textarea validate[validate[],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.INVEST_INDUSTRY }</textarea> 
			<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID }">
			<!-- <input type="button" name="industryChose" value="选择" onclick="showSelectTzhy()"> -->
			<!-- <input type="button" name="industryChose" value="选择" onclick="choseIndustry()"> -->
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 行业编码：</td>
		<td colspan="3"><input type="text" class="tab_text " style="width:740px;" readonly="readonly"
			name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>职工人数/从业人数：</td>
		<td><input type="text" class="tab_text validate[required,custom[integerplus]]"
			name="STAFF_NUM"  value="${busRecord.STAFF_NUM }"/>人</td>
		<td class="tab_width"> 所在地行政区：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="LOCAL_REGION" value="福州市平潭县" /></td>
	</tr>
</table>
