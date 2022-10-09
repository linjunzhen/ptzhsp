<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="PERSONNEL_FORM"  >

	<div class="syj-title1 " style="margin: 5px 0; height: 40px;line-height: 40px;">
		<a href="javascript:void(0);" onclick="javascript:addDsxxDiv();" class="syj-addbtn" id="addDsxx" style="display:none;">添 加</a><span>董事信息</span>
		<c:if test="${empty directorList}">
		<select  name="setHolderInfo" style="margin-left: 82px;" class="input-dropdown " onchange="setInfoMsg(this.value,'DIRECTOR')">
			<option value="">请选择复用人员信息</option>
			<option value="SHAREHOLDER">股东信息1</option>
		</select>
		</c:if>
	</div>
	<div id="dsxxDiv">
		<c:if test="${empty directorList}">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
				
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="DIRECTOR_NAME"  placeholder="请输入姓名，须填写自然人姓名" maxlength="32"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
						<c:if test="${requestParams.COMPANY_SETNATURE=='01'||busRecord.COMPANY_SETNATURE=='01'
							||requestParams.COMPANY_SETNATURE=='03'||busRecord.COMPANY_SETNATURE=='03'}">
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:04"
							dataInterface="dictionaryService.findTwoDatasForSelect"
							defaultEmptyText="请选择职务" name="DIRECTOR_JOB" value="30" onchange="setDirectorJob(this.value,'DIRECTOR_APPOINTOR')">
							</eve:eveselect>
						</c:if>
						<c:if test="${requestParams.COMPANY_SETNATURE=='02'||busRecord.COMPANY_SETNATURE=='02'
						||requestParams.COMPANY_SETNATURE=='07'||busRecord.COMPANY_SETNATURE=='07'
							||requestParams.COMPANY_SETNATURE=='04'||busRecord.COMPANY_SETNATURE=='04'}">
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:01"
							dataInterface="dictionaryService.findTwoDatasForSelect"
							defaultEmptyText="请选择职务" name="DIRECTOR_JOB" value="01" onchange="setDirectorJob(this.value,'DIRECTOR_APPOINTOR')">
							</eve:eveselect>
						</c:if>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>国别：</th>
					<td >
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
									   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
						defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>手机号码：</th>
					<td >
						<input type="text" class="syj-input1 validate[required]"
							   name="DIRECTOR_PHONE"  placeholder="请输入手机号码"  maxlength="32"/>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DIRECTOR_IDNO');" 
						defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required],custom[equalsDirectorIdno]"
						name="DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>产生方式：</th>
					<td><c:if test="${requestParams.COMPANY_SETNATURE!='07'&&busRecord.COMPANY_SETNATURE!='07'}">
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择产生方式" name="DIRECTOR_GENERATION_MODE" value="01" >
						</eve:eveselect>
					</c:if>
						<c:if test="${requestParams.COMPANY_SETNATURE=='07'||busRecord.COMPANY_SETNATURE=='07'}">
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
										   dataInterface="dictionaryService.findDatasForSelect"
										   defaultEmptyText="请选择产生方式" name="DIRECTOR_GENERATION_MODE" value="02" >
							</eve:eveselect>
						</c:if>
					</td>
					<th><font class="syj-color2">*</font>任命方：</th>
					<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
						name="DIRECTOR_APPOINTOR"  placeholder="请输入任命方" 	readonly="readonly"
						<c:if test="${requestParams.COMPANY_SETNATURE=='01'||busRecord.COMPANY_SETNATURE=='01'}">value="股东" </c:if>
						<c:if test="${requestParams.COMPANY_SETNATURE=='02'
						||requestParams.COMPANY_SETNATURE=='04'||busRecord.COMPANY_SETNATURE=='02'||busRecord.COMPANY_SETNATURE=='04'
						||busRecord.COMPANY_SETNATURE=='07'||requestParams.COMPANY_SETNATURE=='07'}">value="董事会" </c:if>
						<c:if test="${requestParams.COMPANY_SETNATURE=='03'||busRecord.COMPANY_SETNATURE=='03'}">value="股东会" </c:if>/></td>
				</tr>
				<tr>
					<th> 任期（年）：</th>
					<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
						name="DIRECTOR_OFFICETERM" value="三"  placeholder="请输入任期"/></td>
				</tr>
			</table>
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
		</c:if>
		<jsp:include page="./initDsxxDiv.jsp"></jsp:include>
	</div>
	
	<div class="syj-title1 " style="margin: 5px 0; height: 40px;line-height: 40px;">
		<a href="javascript:void(0);" onclick="javascript:addJsxxDiv();" class="syj-addbtn" id="addJsxx" >添 加</a><span>监事信息</span>
		<select  name="setHolderInfo" style="margin-left: 82px;" class="input-dropdown " onchange="setInfoMsg(this.value,'SUPERVISOR')">
			<option value="">请选择复用人员信息</option>
			<option value="SHAREHOLDER">股东信息1</option>
		</select>
	</div>
	
	<div id="jsxxDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="SUPERVISOR_NAME" placeholder="请输入姓名，须填写自然人姓名"  maxlength="8"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:02"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="SUPERVISOR_JOB" value="10">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'SUPERVISOR_IDNO');"
						defaultEmptyText="请选择证件类型" name="SUPERVISOR_IDTYPE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required],custom[equalsDirectorOrManagerIdno]"
						name="SUPERVISOR_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>产生方式：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择产生方式" name="SUPERVISOR_GENERATION_MODE" value="01" >
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>任命方：</th>
					<td><c:if test="${requestParams.COMPANY_SETNATURE=='01'||busRecord.COMPANY_SETNATURE=='01'
					||requestParams.COMPANY_SETNATURE=='02'||busRecord.COMPANY_SETNATURE=='02'
					||requestParams.COMPANY_SETNATURE=='03'||busRecord.COMPANY_SETNATURE=='03'
					||requestParams.COMPANY_SETNATURE=='07'||busRecord.COMPANY_SETNATURE=='07'}">
					<input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
						name="SUPERVISOR_APPOINTOR"  placeholder="请输入任命方" readonly="readonly" 
						<c:if test="${requestParams.COMPANY_SETNATURE=='01'||busRecord.COMPANY_SETNATURE=='01'||
						requestParams.COMPANY_SETNATURE=='07'||busRecord.COMPANY_SETNATURE=='07'}">value="股东" </c:if>
						<c:if test="${requestParams.COMPANY_SETNATURE=='02'||requestParams.COMPANY_SETNATURE=='03'
						||busRecord.COMPANY_SETNATURE=='02'||busRecord.COMPANY_SETNATURE=='03'}">value="股东会" </c:if>/>
					</c:if>
					<c:if test="${requestParams.COMPANY_SETNATURE=='04'||busRecord.COMPANY_SETNATURE=='04'}">
						<select name="SUPERVISOR_APPOINTOR" class="input-dropdown w280 validate[required]" 
						defaultemptytext="请选择任命方" >
						<option value="">请选择任命方</option>
						<option value="职工代表大会" >职工代表大会</option>
						<option value="监事会">监事会</option>
						<option value="股东会" style=": ;">股东会</option>
						</select>
					</c:if></td>
				</tr>
				<tr>
					<th> 任期（年）：</th>
					<td ><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
						name="SUPERVISOR_OFFICETERM" value="三"  placeholder="请输入任期" /></td>
					<th><font class="syj-color2">*</font>国别（地区）：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
									   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
									   name="SUPERVISOR_COUNTRY" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>手机号码：</th>
					<td  colspan="3">
						<input type="text" class="syj-input1 validate[required]"
							   name="SUPERVISOR_PHONE"  placeholder="请输入手机号码"  maxlength="32"/>
					</td>
				</tr>
			</table>
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
	</div>
	
	
	<div class="syj-title1 " style="margin: 5px 0; height: 40px;line-height: 40px;">
		<a href="javascript:void(0);" onclick="javascript:addJlxxDiv();" class="syj-addbtn" id="addJlxx" style="display:none;">添 加</a><span>经理信息</span>
		<select  name="setHolderInfo" style="margin-left: 82px;" class="input-dropdown  w280" onchange="setInfoMsg(this.value,'MANAGER')">
			<option value="">请选择复用人员信息</option>
			<option value="SHAREHOLDER">股东信息1</option>
		</select>
	</div>
	<div id="jlxxDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td><input type="text" class="syj-input1 validate[required]" 
							name="MANAGER_NAME"  placeholder="请输入姓名，须填写自然人姓名"  maxlength="8"/></td>
						<th><font class="syj-color2">*</font>职务：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:03"
							dataInterface="dictionaryService.findTwoDatasForSelect"
							defaultEmptyText="请选择职务" name="MANAGER_JOB" value="20">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>证件类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'MANAGER_IDNO');"
							defaultEmptyText="请选择证件类型" name="MANAGER_IDTYPE">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>证件号码：</th>
						<td><input type="text" class="syj-input1 validate[required]"
							name="MANAGER_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>产生方式：</th>
						<td>
							<c:if test="${requestParams.COMPANY_SETNATURE!='07'&&busRecord.COMPANY_SETNATURE!='07'}">
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择产生方式" name="MANAGER_GENERATION_MODE"  value="01">
							</eve:eveselect>
							</c:if>
							<c:if test="${requestParams.COMPANY_SETNATURE=='07'||busRecord.COMPANY_SETNATURE=='07'}">
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjcsfs"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择产生方式" name="MANAGER_GENERATION_MODE"  value="03">
							</eve:eveselect>
							</c:if>
						</td>
						<th><font class="syj-color2">*</font>任命方：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly"
							name="MANAGER_APPOINTOR"  placeholder="请输入任命方" 
						<c:if test="${requestParams.COMPANY_SETNATURE=='01'||busRecord.COMPANY_SETNATURE=='01'}">value="股东" </c:if>
						<c:if test="${requestParams.COMPANY_SETNATURE=='02'||requestParams.COMPANY_SETNATURE=='04'||requestParams.COMPANY_SETNATURE=='07'
						||busRecord.COMPANY_SETNATURE=='02'||busRecord.COMPANY_SETNATURE=='04'||busRecord.COMPANY_SETNATURE=='07'}">value="董事会" </c:if>
						<c:if test="${requestParams.COMPANY_SETNATURE=='03'||busRecord.COMPANY_SETNATURE=='03'}">value="股东会" </c:if>/></td>
					</tr>
					<tr>
						<th> 任期（年）：</th>
						<td ><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
							name="MANAGER_OFFICETERM" value="三"  placeholder="请输入任期"/></td>

						<th><font class="syj-color2">*</font>国别（地区）：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
										   dataInterface="dictionaryService.findDatasForCountrySelect" defaultEmptyValue="中国"
										   name="MANAGER_COUNTRY" >
							</eve:eveselect>
						</td>
					</tr>
				<tr>
					<th><font class="syj-color2">*</font>手机号码：</th>
					<td  colspan="3">
						<input type="text" class="syj-input1 validate[required]"
							   name="MANAGER_PHONE"  placeholder="请输入手机号码"  maxlength="32"/>
					</td>
				</tr>
			</table>
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
	</div>
</form>
