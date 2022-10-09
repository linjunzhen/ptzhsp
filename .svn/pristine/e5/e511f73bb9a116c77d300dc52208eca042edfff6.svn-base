<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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

<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
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
		<th colspan="4">经营者信息</th>
	</tr>
	<tr id="unicode" <c:if test="${busRecord.SOCIAL_CREDITUNICODE==null || busRecord.SOCIAL_CREDITUNICODE==''}">style="display: none;"</c:if>>
		<td class="tab_width"><font class="tab_color">*</font>社会信用代码：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" 
				name="SOCIAL_CREDITUNICODE" maxlength="32" value="${busRecord.SOCIAL_CREDITUNICODE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="DEALER_NAME" maxlength="16" value="${busRecord.DEALER_NAME}"/></td>
		<!--<td class="tab_width" rowspan="3"><font class="tab_color">*</font>照片：</td>
		<td rowspan="3">
			<c:choose>
				<c:when test="${busRecord.DEALER_PHOTO!=null&&busRecord.DEALER_PHOTO!=''}">
					<img id="IMAGE_PATH_IMG" src="${_file_Server}${busRecord.DEALER_PHOTO}"
						height="140px" width="125px"><a id="IMAGE_PATH_BTN"></a>
				</c:when>
				<c:otherwise>
					<img id="IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
						height="140px" width="125px"><a id="IMAGE_PATH_BTN"></a>
				</c:otherwise>
			</c:choose>				
    		<input type="hidden" name="DEALER_PHOTO" value="${busRecord.DEALER_PHOTO}">
		</td>
	</tr>
	<tr>-->
		<td class="tab_width"><font class="tab_color">*</font>性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="sex"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择性别" name="DEALER_SEX" value="${busRecord.DEALER_SEX}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>身份证号码：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="DEALER_IDCARD" maxlength="30" value="${busRecord.DEALER_IDCARD}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>住所：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="DEALER_ADDR" maxlength="62" value="${busRecord.DEALER_ADDR}"/></td>
	</tr>
	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
		<td><input type="text" class="tab_text validate[required,custom[chinaZip]]"
			name="DEALER_POSTCODE" maxlength="8" value="${busRecord.DEALER_POSTCODE}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="DEALER_MOBILE" maxlength="16" value="${busRecord.DEALER_MOBILE}"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 固定电话：</td>
		<td><input type="text" class="tab_text "
			name="DEALER_FIXEDLINE" maxlength="16" value="${busRecord.DEALER_FIXEDLINE}"/></td>
		<td class="tab_width"> 电子邮箱：</td>
		<td><input type="text" class="tab_text validate[[],custom[email]]"
			name="DEALER_EMAIL" maxlength="32" value="${busRecord.DEALER_EMAIL}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>政治面貌：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="political"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择政治面貌" name="DEALER_POLITICAL" value="${busRecord.DEALER_POLITICAL}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>民族：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="nation"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择民族" name="DEALER_NATION" value="${busRecord.DEALER_NATION}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>文化程度：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="degree"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择文化程度" name="DEALER_DEGREE" value="${busRecord.DEALER_DEGREE}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>职业状况：</td>
		<td>		
			<eve:eveselect clazz="tab_text validate[required]" dataParams="zyzk"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择职业状况" name="DEALER_JOB" value="${busRecord.DEALER_JOB}">
			</eve:eveselect>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">登记信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>名称：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
			name="INDIVIDUAL_NAME" maxlength="256" value="${busRecord.INDIVIDUAL_NAME}"/>
		</td>
	</tr>
	<tr id="wordNum" style="display:none;">
		<td class="tab_width"><font class="tab_color">*</font>字号：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[]" style="width:740px;" name="WORD_NUM" id="WORD_NUM" maxlength="128" value="${busRecord.WORD_NUM}"/></span>
		</td>
	</tr>
	<tr class="oldtr">
		<td class="tab_width"> 备选字号1：</td>
		<td colspan="3"><input type="text" class="tab_text " style="width:740px;"
			name="TAG_OPTIOIN1" maxlength="32" value="${busRecord.TAG_OPTIOIN1}"/></td>
	</tr>
	<tr class="oldtr">
		<td class="tab_width"> 备选字号2：</td>
		<td colspan="3"><input type="text" class="tab_text " style="width:740px;"
			name="TAG_OPTIOIN2" maxlength="32"  value="${busRecord.TAG_OPTIOIN2}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营形式：</td>
		<td colspan="3">
			<input type="radio" name="MANAGE_FORM" value="0" <c:if test="${busRecord.MANAGE_FORM==0 }">checked="checked"</c:if> >个人经营
			<input type="radio" name="MANAGE_FORM" value="1" <c:if test="${busRecord.MANAGE_FORM==1 }">checked="checked"</c:if> >家庭经营
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="family" style="display:none;">
	<tr>
		<th colspan="5">家庭成员信息</th>
	</tr>
	<tr id="family_1">
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="FAMILY_NAME" maxlength="8"/></td>
		<td class="tab_width"><font class="tab_color">*</font>身份证号码：</td>
		<td><input type="text" class="tab_text validate[required,custom[vidcard]]"
			name="FAMILY_IDNO" maxlength="32"/>
		</td>
		<td>
			<input type="button" name="deleteFamily" value="删除行" onclick="deleteFamilys('1');">
			<input type="button" name="addOneFamily" value="增加一行" onclick="addFamily();">
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr class="hymlTr">
		<td class="tab_width"><font class="tab_color">*</font>主营范围：</td>
		<td colspan="3">
			<input type="text"  readonly="readonly" maxlength="16" id="MAIN_BUSSINESS_NAME"
				class="tab_text validate[required]" placeholder="请选择主营范围" value="${busRecord.MAIN_BUSSINESS_NAME}" name="MAIN_BUSSINESS_NAME" />
			<input type="hidden" name="MAIN_BUSSINESS_ID" value="${busRecord.MAIN_BUSSINESS_ID}">
		</td>
	</tr>							
	<tr class="hymlTr">
		<td class="tab_width"><font class="tab_color">*</font>行业门类：</td>
		<td colspan="3">
				<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="ML_NAME"
				class="tab_text validate[required]" placeholder="行业门类" value="${busRecord.ML_NAME}" name="ML_NAME" />
				
				<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="DL_NAME"
				class="tab_text validate[required]" placeholder="行业大类" value="${busRecord.DL_NAME}" name="DL_NAME" />
				
				<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="ZL_NAME"
				class="tab_text validate[required]" placeholder="行业中类" value="${busRecord.ZL_NAME}" name="ZL_NAME" />
				
				<input type="text" style="width:20%;height:25px;float:left;margin-right: 10px;" readonly="readonly" maxlength="16" id="XL_NAME"
				class="tab_text validate[required]" placeholder="行业小类" value="${busRecord.XL_NAME}" name="XL_NAME" />
		</td>
	</tr>				
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="BUSINESS_SCOPE" readonly="readonly"
				class="eve-textarea validate[validate[required],maxSize[512]]"
			 	style="width:740px;height:100px;" >${busRecord.BUSINESS_SCOPE}</textarea> 
			<input type="hidden" name="BUSINESS_SCOPE_ID" value="${busRecord.BUSINESS_SCOPE_ID}">
			<input type="button" name="scopeChose" value="选择" onclick="showSelectJyfwNew();">
			
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
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;" <c:if test="${isJyfwAll == false}">disabled="disabled"</c:if>
			name="SCOPE_REMARK" maxlength="128" value="${busRecord.SCOPE_REMARK }"/>
			<c:if test="${isJyfwAll == true}"><a class="eflowbutton" onclick="saveScopeRemark();" style="height: 28px;float: none;">保存</a></c:if></td>
	</tr>					
	<tr class="newtr">
		<td class="tab_width">辖区分局：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text validate[]" dataParams="1"
			   dataInterface="jurisdictionService.findDatasForSelect"
			   defaultEmptyText="请选择辖区分局" name="XQ_NAME"  value="${busRecord.XQ_NAME}">
			</eve:eveselect>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营场所地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
			name="BUSINESS_PLACE" maxlength="60" value="${busRecord.BUSINESS_PLACE}"/></td>
	</tr>
	<tr>
		<td class="tab_width">法律文书送达地址(经营场所地址)：</td>
		<td >
			<input type="radio" class="radio validate[]" name="IS_REGISTER_ADDR" value="1"  onclick="setSendLawAddr('1');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
			<input type="radio" class="radio validate[]" name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
		</td>
		<td class="tab_width">法律文书送达地址：</td>
		<td><input  type="text" class="tab_text validate[]"
					name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/></td>
		</td>
	</tr>
	<tr>
		<td class="tab_width" rowspan="4"> 法律文书电子送达地址：</td>
	</tr>
	<tr>	
		<td colspan="3">
			<div style="width: 150px;float:left;text-align: right;">邮箱：</div>
			<input type="text" class="tab_text  validate[]" style="width: 600px;"
			 name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
		</td>
	</tr>
	<tr>	
		<td colspan="3">
			<div style="width: 150px;float:left;text-align: right;">传真：</div>
			<input type="text" class="tab_text   validate[]" style="width: 600px;"
			name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
		</td>
	</tr>
	<tr>	
		<td colspan="3">
			<div style="width: 150px;float:left;text-align: right;">即时通讯账号（如微信）：</div>
			<input type="text" class="tab_text   validate[]" style="width: 600px;"
			 name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
		</td>
	</tr>
	<script type="text/javascript">
		function setSendLawAddr() {
			var val = $("[name='IS_REGISTER_ADDR']:checked").val();
			if(val=='1'){
				$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
			}else{
				$("input[name='LAW_SEND_ADDR']").removeAttr("disabled");
			}
		}
		$(function () {
			setSendLawAddr();
		})
	</script>		
	<tr>
		<td class="tab_width">房屋所有权人名称：</td>
		<td><input  type="text" class="tab_text validate[]"
					name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_REGISTER_OWNER}"/></td>
		<td class="tab_width">房屋所有权人联系电话：</td>
		<td><input type="text" class="tab_text validate[]"
				   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_REGISTER_TEL}"/></td>

	</tr>
	<tr>
		<td class="tab_width">取得方式：</td>
		<td style="width:500px;">
			<eve:eveselect clazz="tab_text validate[]" dataParams="wayOfGet"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择取得方式" name="RESIDENCE_REGISTER_WAYOFGET"  value="${busRecord.RESIDENCE_REGISTER_WAYOFGET}">
			</eve:eveselect>
		</td>
		<td class="tab_width">面积：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="16"
				   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
			<font style="font-size: 16px;margin-left: 10px;">㎡</font>
		</td>
	</tr>
	<tr>
		<td class="tab_width">是否属于军队房产：</td>
		<td >
			<eve:eveselect clazz="tab_text validate[]" dataParams="armyHourse"
						   dataInterface="dictionaryService.findDatasForSelect" 
						   defaultEmptyText="请选择军队房产情况" name="ARMY_REGISTER_HOURSE"  value="${busRecord.ARMY_REGISTER_HOURSE}">
			</eve:eveselect>
		</td>
		<td class="tab_width">其他证明：</td>
		<td >
			<input type="text" class="tab_text" disabled
				   name="ARMYHOURSE_REGISTER_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REGISTER_REMARKS}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
		<td><input type="text" class="tab_text validate[required,custom[chinaZip]]"
			name="PLACE_POSTCODE" maxlength="8" value="${busRecord.PLACE_POSTCODE}"/></td>
		<td class="tab_width">固定电话：</td>
		<td><input type="text" class="tab_text validate[]"
			name="PLACE_PHONE" maxlength="16" value="${busRecord.PLACE_PHONE}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>从业人数(人)：</td>
		<td><input type="text" class="tab_text validate[required,custom[integerplus]]"
			name="EMPLOYED_NUM" maxlength="8" value="${busRecord.EMPLOYED_NUM}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>资金数额(万元)：</td>
		<td><input type="text" class="tab_text validate[required,custom[JustNumber]]" 
			name="CAPITAL_AMOUNT" maxlength="8" value="${busRecord.CAPITAL_AMOUNT}"/></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="lyydiv">
	<tr>
		<th colspan="4">联络员/委托人信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td><input type="text" class="tab_text   validate[required]"
			name="LIAISON_NAME" maxlength="16"  placeholder="请输入姓名" value="${busRecord.LIAISON_NAME}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>手机号码：</td>
		<td>
			<input type="text" class="tab_text   validate[required],custom[mobilePhoneLoose]" maxlength="11"
				   name="LIAISON_MOBILE" placeholder="请输入手机号码" value="${busRecord.LIAISON_MOBILE}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>身份证号码：</td>
		<td><input  type="text" class="tab_text    validate[required],custom[vidcard]"
					name="LIAISON_IDNO"  placeholder="请输入身份证号码"  maxlength="18"  value="${busRecord.LIAISON_IDNO}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>住所：</td>
		<td><input type="text" class="tab_text   validate[required]" 
				   name="LIAISON_ADDR"  placeholder="请输入住所"  maxlength="32"  value="${busRecord.LIAISON_ADDR}"/></td>

	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>身份证正面：</td>
		<td>							
			<c:choose>
				<c:when test="${busRecord.LIAISON_SFZZM!=null&&busRecord.LIAISON_SFZZM!=''}">
					<img id="LIAISON_SFZZM_PATH_IMG" src="${_file_Server}${busRecord.LIAISON_SFZZM}"
						height="140px" width="220px">
				</c:when>
				<c:otherwise>
					<img id="LIAISON_SFZZM_PATH_IMG" src="webpage/common/images/nopic.jpg"
						height="140px" width="220px">
				</c:otherwise>
			</c:choose>			
			<input type="hidden" class="validate[required]" name="LIAISON_SFZZM" value="${busRecord.LIAISON_SFZZM}">
		</td>
		<td class="tab_width"><font class="tab_color">*</font>身份证反面：</td>
		<td>							
			<c:choose>
				<c:when test="${busRecord.LIAISON_SFZFM!=null&&busRecord.LIAISON_SFZFM!=''}">
					<img id="LIAISON_SFZFM_PATH_IMG" src="${_file_Server}${busRecord.LIAISON_SFZFM}"
						height="140px" width="220px">
				</c:when>
				<c:otherwise>
					<img id="LIAISON_SFZFM_PATH_IMG" src="webpage/common/images/nopic.jpg"
						height="140px" width="220px">
				</c:otherwise>
			</c:choose>		
			<input type="hidden" class="validate[required]" name="LIAISON_SFZFM" value="${busRecord.LIAISON_SFZFM}">
		</td>
	</tr>

</table>