<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	function downloadApply(){		
		var flowSubmitObj = FlowUtil.getFlowObj();
		var exeId = flowSubmitObj.EFLOW_EXEID;
		var itemCode = '${serviceItem.ITEM_CODE}';
		
		window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApplyFront.do?exeId="+exeId
			+"&itemCode="+itemCode;
	}
</script>

<form action="" id="OTHER_FORM"  >
	<div class="syj-title1 tmargin20">
		<span>社会保险登记信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>社保经办人：</th>
				<td><input type="text" class="syj-input1 validate[required]" name="INSURANCE_OPERRATOR"  
					maxlength="16"  placeholder="请输入社保经办人" value="${busRecord.INSURANCE_OPERRATOR}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" maxlength="32"
					name="INSURANCE_PHONE"  placeholder="请输入移动电话" value="${busRecord.INSURANCE_PHONE}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>身份证号码：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[vidcard]"  
					name="INSURANCE_IDNO" placeholder="请输入身份证号码" maxlength="18" style="width: 278px;" value="${busRecord.INSURANCE_IDNO}"/></td>				
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>参加险种：</th>
				<td colspan="3">
					<eve:excheckbox
						dataInterface="dictionaryService.findDatasForSelect"
						name="INSURANCE_TYPE" width="749px;" clazz="checkbox validate[required]"
						dataParams="insuranceType" value="${busRecord.INSURANCE_TYPE}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
		<script>
            $(function(){
                var runStatus="${busRecord.RUN_STATUS}";
                if(runStatus=="0"||runStatus=="1"||runStatus==""){
                    var insrranceTypes= document.getElementsByName("INSURANCE_TYPE");
                    for(i=0;i<insrranceTypes.length;i++){
                        if(insrranceTypes[i].value=='1'||insrranceTypes[i].value=='5'){
                            insrranceTypes[i].checked=true;
                            insrranceTypes[i].disabled="disabled";
                        }
                    }
                }
            });
		</script>
        <span style="margin-top:5px;">
        <font style="color: red;font-family: serif;font-size: 16px;line-height: 40px;">
根据《中华人民共和国社会保险法》用人单位应当自成立之日起三十日内持单位印章，向当地社会保险经办机构申请办理养老、工伤保险登记。</font>
        </span>
	</div>
	<div class="syj-title1 tmargin20">
		<span>银行开户申请信息<font style="color: red;font-family: serif;">（申请过程中使用的私章需要使用同一枚私章，私章不能使用光敏印章，身份证应使用最新、有效身份证）</font></span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th> 是否申请开户：</th>
				<td>
					<input type="radio" name="IS_ACCOUNT_OPEN" value="1" <c:if test="${busRecord.IS_ACCOUNT_OPEN==1}"> checked="checked"</c:if>/>是
					<input type="radio" name="IS_ACCOUNT_OPEN" value="0" <c:if test="${busRecord.IS_ACCOUNT_OPEN!=1}"> checked="checked"</c:if>/>否
				</td>
			</tr>
		</table>
	</div>
	<div id="bankInfo" style="display: none;">
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>已具备网上开户银行：</th>
				<td colspan="3">
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="ccb" <c:if test="${busRecord.BANK_TYPE=='ccb'}">checked="checked"</c:if> disabled="disabled"/>建设银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="boc" <c:if test="${busRecord.BANK_TYPE=='boc'}">checked="checked"</c:if> disabled="disabled"/>中国银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="abc" <c:if test="${busRecord.BANK_TYPE=='abc'}">checked="checked"</c:if> disabled="disabled"/>农业银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="rcb" <c:if test="${busRecord.BANK_TYPE=='rcb'}">checked="checked"</c:if> disabled="disabled"/>农商银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="pdb" <c:if test="${busRecord.BANK_TYPE=='pdb'}">checked="checked"</c:if> disabled="disabled"/>浦发银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="comm" <c:if test="${busRecord.BANK_TYPE=='comm'}">checked="checked"</c:if> disabled="disabled"/>交通银行

					<c:if test="${busRecord.CONTROLLER!=''&&busRecord.CONTROLLER!=null&&busRecord.CONTROLLER!=undefind}">
						<a href="javascript:void(0);" onclick="javascript:downloadApply();" class="syj-addbtn" style="float: right;">下载申请书</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th> <font class="syj-color2">*</font>控股股东或实际控制人名称(法人代表或持股比例最大股东)：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required]" maxlength="32"  disabled="disabled"
					name="CONTROLLER" value="${busRecord.CONTROLLER}"/>
			</tr>
			<tr>
				<th> 存款人名称：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="DEPOSITOR" value="${busRecord.DEPOSITOR}" /></td>				
			</tr>
			<tr>
				<th> 地址：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="DEPOSITOR_ADDR" value="${busRecord.DEPOSITOR_ADDR}"/></td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th> 邮编：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" 
					name="DEPOSITOR_POSTCODE" value=""/></td>
				<th> 地区代码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="AREA_CODE" value=""/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>注册资金(万元)：</th>
				<td><input type="text" class="syj-input1"  onkeyup="onlyNumber(this);" onblur="onlyNumber(this);" 
					id="INVESTMENT" name="INVESTMENT"  maxlength="11" value="${busRecord.INVESTMENT}"/></td>
				<th> 法定代表人：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc"
					id="legalPersonName" readonly="readonly" value="${busRecord.LEGAL_NAME}"/></td>
			</tr>
			<tr>
				<th> 证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown" dataParams="DocumentType" 
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name=""  id="legalPersonIDType" value="${busRecord.LEGAL_IDTYPE}">
					</eve:eveselect>
				</td>
				<th> 证件号码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					id="legalPersonIDNo" value="${busRecord.LEGAL_IDNO}"/></td>
			</tr>
			<tr>
				<th> 经营范围：</th>
				<td colspan="3">
					<textarea rows="3" cols="200" id="businessScope" disabled="disabled"
						class="eve-textarea" readonly="readonly"
						style="width:90%;height:100px;" >${busRecord.BUSSINESS_SCOPE}</textarea>
				</td>
			</tr>
			<tr>
				<th>行业分类：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					id="INDUSTRY_CATEGORY_CDOE"  value="${busRecord.INDUSTRY_CATEGORY}"/>
					
				<input type="hidden" name="INDUSTRY_CATEGORY" value="${busRecord.INDUSTRY_CATEGORY}"></td>
			</tr>
			<tr>
				<th> 存款人类型：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="DEPOSITOR_TYPE" value=""/></td>
				<th> 证明文件类型：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="PROOFDOC" value=""/></td>
			</tr>
			<tr>
				<th> 账户性质：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="ACCOUNT_NATURE" value=""/></td>
				<th> 开户银行代码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="BANK_CODE" value=""/></td>
			</tr>
			<tr>
				<th> 开户银行名称：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="BANK_NAME" value=""/></td>
			</tr>
			<tr>
				<th> 账户名称：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="ACCOUNT_NAME" value=""/></td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th colspan="4"> 到银行办理需要带的文件清单</th>
			</tr>
			<tr id="ccblist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、开立单位银行结算账户申请书；<br>
				2、营业执照正副本原件；<br>
				3、法定代表人身份证原件；<br>
				4、如委托代理人办理的，还需提供代理人身份证件以及授权委托书原件；<br>
				5、公司章程及股东身份证明文件；<br>
				6、单位公章、法定代表人个人名章；<br>
				7、预留印章（单位财务专用章或者公章、法定代表人或授权的代理人的个人名章）
				</td>
			</tr>
			<tr id="boclist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、中国银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正副本；<br>
				4、公司章程及股东身份证明文件；<br>
				5、授权开户经办人身份证及委托书；<br>
				6、单位公章、法人私章及开户预留印鉴；<br>
				7、大额有权确认人身份证原件；
				</td>
			</tr>
			<tr id="abclist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、中国农业银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正本；<br>
				4、公司章程；<br>
				5、股东身份证明文件：股东为自然人的，提供身份证复印件；股东为法人的，提供营业执照正本复印件、开户许可证复印件、信用机构代码证复印件、法人身份证复印件，并加盖股东公章；<br>
				6、单位银行结算账户相关业务授权委托书（法人签字）；<br>
				7、授权开户经办人身份证；<br>
				8、单位公章、法人私章及预留印鉴；<br>
				9、机构税收居民身份声明文件。
				</td>
			</tr>
			<tr id="pdblist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、上海浦东发展银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正副本；<br>
				4、公司章程及股东身份证明文件；<br>
				5、授权开户经办人身份证及委托书；<br>
				6、单位公章、法人私章及开户预留印鉴；
				</td>
			</tr>
			<tr id="rcblist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、平潭农村商业银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正副本；<br>
				4、公司章程及股东身份证明文件；<br>
				5、授权开户经办人身份证及委托书；<br>
				6、单位公章、法人私章及开户预留印鉴；
				</td>
			</tr>
			<tr id="commlist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
					1、交通银行开立单位银行结算账户申请书；<br>
					2、法人身份证；<br>
					3、营业执照正本；<br>
					4、公司章程；<br>
					5、股东身份证明文件：股东为自然人的，提供身份证复印件；股东为法人的，提供营业执照正本复印件、开户许可证复印件、信用机构代码证复印件、法人身份证复印件，并加盖股东公章；<br>
					6、单位银行结算账户相关业务授权委托书（法人签字）；<br>
					7、授权开户经办人身份证；<br>
					8、单位公章、法人私章及预留印鉴；<br>
					9、机构税收居民身份声明文件。
				</td>
			</tr>
			<tr id="templateLoad" style="display: none;">
				<th> 申请书模板：</th>
				<td colspan="3">
					<a class="easyui-linkbutton"  iconcls="icon-print" plain="true" style="margin-bottom:15px;"
                				href="" >开立单位银行结算账户申请书-模板下载</a>
                </td>
			</tr>
		</table>
	</div>
	<div class="syj-title1 tmargin20">
		<span>其他信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th> 登记机关：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly" 
					name="REGISTER_AUTHORITY" value="平潭综合实验区行政审批局"/></td>
			</tr>
		</table>
		<table  style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th> 批准机构：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly" 
					name="APPROVAL_AUTHORITY" value="平潭综合实验区行政审批局" /></td>
				<th> 批准机构代码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly" 
					name="AUTHORITY_CODE" value="003678642"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>是否申请电子营业执照：</th>
				<td>
					<input type="radio" class="radio validate[required]" name="LICENSE_NUM" value="1" checked="checked" />是
				</td>
				<th><font class="syj-color2">*</font>申请营业执照副本数量（本）：</th>
				<td><input type="text" class="syj-input1 validate[required],custom[integer],min[1]"
					name="LICENSE_COPY_NUM"  placeholder="请输入数量" value="1" maxlength="2"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>是否涉密单位：</th>
				<td colspan="3">
					<input type="radio" class="radio validate[required]" name="IS_SECRET" value="1" 
					<c:if test="${busRecord.IS_SECRET==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio validate[required]" name="IS_SECRET" value="0" 
					<c:if test="${busRecord.IS_SECRET!=1}"> checked="checked"</c:if>/>否
				</td>
			</tr>
			<tr>
				<th>填表日期：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly"
					name="FILL_DATE"  placeholder="请输入填表日期"  value="${time}"/></td>
			</tr>
		</table>
	</div>
</form>
