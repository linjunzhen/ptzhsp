<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="PERSONNEL_FORM"  >
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">	
		<tr>
			<th><font class="syj-color2">*</font>企业权利机构名称：</th>
			<td colspan="3">
				<input class="syj-input1" disabled="disabled" name="AUTHORITY_NAME" maxlength="16" type="text"  
						<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">value="董事会" </c:if>
						<c:if test="${requestParams.COMPANY_SETTYPE=='wshz'||busRecord.COMPANY_SETTYPE=='wshz'}">value="股东会" </c:if>
						<c:if test="${requestParams.COMPANY_SETTYPE=='wsdz'||busRecord.COMPANY_SETTYPE=='wsdz'}">value="投资者" </c:if>>
			</td>
		</tr>
	</table>
	
	<div id="dsxxDiv">		
		<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">
			<div class="syj-title1 tmargin20">
				<span>董事会信息</span>
			</div>
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">			
					
					<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td><input type="text" class="syj-input1 validate[required]" 
							name="DIRECTOR_NAME"  placeholder="请输入姓名" maxlength="32"/></td>
						<th><font class="syj-color2">*</font>职务：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,04"
							dataInterface="dictionaryService.findTwoDatasForSelect"
							defaultEmptyText="请选择职务" name="DIRECTOR_JOB" value="01">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>国别：</th>
						<td colspan="3">
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb"
							dataInterface="dictionaryService.findDatasForCountrySelect"
							defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>证件类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DIRECTOR_IDNO');" 
							defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>证件号码：</th>
						<td><input type="text" class="syj-input1 validate[required]"
							name="DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>产生方式：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjcsfs"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择产生方式" name="DIRECTOR_GENERATION_MODE">
							</eve:eveselect>
						</td>
						<th> 任期（年）：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
							name="DIRECTOR_OFFICETERM" value="三"  placeholder="请输入任期" style="width: 185px;"/></td>
					</tr>
					<tr>
					
						<th><font class="syj-color2">*</font>任命方：</th>
						<td colspan="3">
							<select name="DIRECTOR_APPOINTOR" class="input-dropdown validate[required]"  style="width: auto;">
								<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">
									<option value="">请选择任命方</option>
								</c:if>
								<c:if test="${requestParams.COMPANY_SETTYPE=='wshz'||busRecord.COMPANY_SETTYPE=='wshz'}">							
									<option value="股东会">股东会</option>
								</c:if>
								<c:if test="${requestParams.COMPANY_SETTYPE=='wsdz'||busRecord.COMPANY_SETTYPE=='wsdz'}">		
									<option value="投资者">投资者</option>
								</c:if>
							</select>
						</td>
					</tr>
				</table>
				<!--<a href="#" class="syj-closebtn"></a>-->
			</div>
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">			
					
					<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td><input type="text" class="syj-input1 validate[required]" 
							name="DIRECTOR_NAME"  placeholder="请输入姓名" maxlength="32"/></td>
						<th><font class="syj-color2">*</font>职务：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,04"
							dataInterface="dictionaryService.findTwoDatasForSelect"
							defaultEmptyText="请选择职务" name="DIRECTOR_JOB" value="02">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>国别：</th>
						<td colspan="3">
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb"
							dataInterface="dictionaryService.findDatasForCountrySelect"
							defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>证件类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DIRECTOR_IDNO');" 
							defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>证件号码：</th>
						<td><input type="text" class="syj-input1 validate[required]"
							name="DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>产生方式：</th>
						<td>
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjcsfs"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择产生方式" name="DIRECTOR_GENERATION_MODE" >
							</eve:eveselect>
						</td>
						<th> 任期（年）：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
							name="DIRECTOR_OFFICETERM" value="三"  placeholder="请输入任期" style="width: 185px;"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>任命方：</th>
						<td  colspan="3">							
							<select name="DIRECTOR_APPOINTOR" class="input-dropdown validate[required]"  style="width: auto;">
								<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">
									<option value="">请选择任命方</option>
								</c:if>
								<c:if test="${requestParams.COMPANY_SETTYPE=='wshz'||busRecord.COMPANY_SETTYPE=='wshz'}">							
									<option value="股东会">股东会</option>
								</c:if>
								<c:if test="${requestParams.COMPANY_SETTYPE=='wsdz'||busRecord.COMPANY_SETTYPE=='wsdz'}">		
									<option value="投资者">投资者</option>
								</c:if>
							</select>
						</td>
					</tr>
				</table>
				<!--<a href="#" class="syj-closebtn"></a>-->
			</div>
		</c:if>
		<div class="syj-title1 tmargin20">
			<a href="javascript:void(0);" onclick="javascript:addDsxxDiv();" class="syj-addbtn" id="addDsxx" style="display:none;">添 加</a><span>董事信息</span>
		</div>
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">			
				
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="DIRECTOR_NAME"  placeholder="请输入姓名" maxlength="32"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01,04"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="DIRECTOR_JOB" value="03"  >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>国别：</th>
					<td colspan="3">
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForCountrySelect"
						defaultEmptyText="请选择国别" name="DIRECTOR_COUNTRY">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DIRECTOR_IDNO');" 
						defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>产生方式：</th>
					<td>
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjcsfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择产生方式" name="DIRECTOR_GENERATION_MODE">
						</eve:eveselect>
					</td>
					<th> 任期（年）：</th>
					<td><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
						name="DIRECTOR_OFFICETERM" value="三"  placeholder="请输入任期" style="width: 185px;"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>任命方：</th>
					<td colspan="3">
						<select name="DIRECTOR_APPOINTOR" class="input-dropdown validate[required]" style="width: auto;">
							<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">
								<option value="">请选择任命方</option>
							</c:if>
							<c:if test="${requestParams.COMPANY_SETTYPE=='wshz'||busRecord.COMPANY_SETTYPE=='wshz'}">							
								<option value="股东会">股东会</option>
							</c:if>
							<c:if test="${requestParams.COMPANY_SETTYPE=='wsdz'||busRecord.COMPANY_SETTYPE=='wsdz'}">		
								<option value="投资者">投资者</option>
							</c:if>
						</select>
					</td>
				</tr>
			</table>
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
	</div>
	
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addJsxxDiv();" class="syj-addbtn" id="addJsxx" style="display:none;">添 加</a><span>监事信息</span>
	</div>
	
	<div id="jsxxDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="SUPERVISOR_NAME" placeholder="请输入姓名"  maxlength="32"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
						<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjzw:02"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="SUPERVISOR_JOB" value="10">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>国别：</th>
					<td colspan="3">
						<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForCountrySelect"
						defaultEmptyText="请选择国别" name="SUPERVISOR_COUNTRY">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="DocumentType"
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
						<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjcsfs"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择产生方式" name="SUPERVISOR_GENERATION_MODE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>任命方：</th>
					<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
						name="SUPERVISOR_APPOINTOR"  placeholder="请输入任命方"  readonly="readonly" 
						<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">value="董事会" </c:if>
						<c:if test="${requestParams.COMPANY_SETTYPE=='wshz'||busRecord.COMPANY_SETTYPE=='wshz'}">value="股东会" </c:if>
						<c:if test="${requestParams.COMPANY_SETTYPE=='wsdz'||busRecord.COMPANY_SETTYPE=='wsdz'}">value="投资者" </c:if>/></td>
				</tr>
				<tr>
					<th> 任期（年）：</th>
					<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly" 
						name="SUPERVISOR_OFFICETERM" value="三"  placeholder="请输入任期" style="width: 185px;"/></td>
				</tr>
			</table>
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
	</div>
	
	
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addJlxxDiv();" class="syj-addbtn" id="addJlxx" style="display:none;">添 加</a><span>经理信息</span>
	</div>
	<div id="jlxxDiv">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td><input type="text" class="syj-input1 validate[required]" 
							name="MANAGER_NAME"  placeholder="请输入姓名"  maxlength="32"/></td>
						<th><font class="syj-color2">*</font>职务：</th>
						<td>
							<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjzw:03"
							dataInterface="dictionaryService.findTwoDatasForSelect"
							defaultEmptyText="请选择职务" name="MANAGER_JOB" value="21">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>国别：</th>
						<td colspan="3">
							<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb"
							dataInterface="dictionaryService.findDatasForCountrySelect"
							defaultEmptyText="请选择国别" name="MANAGER_COUNTRY">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>证件类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="DocumentType"
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
							<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjcsfs"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择产生方式" name="MANAGER_GENERATION_MODE">
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>任命方：</th>
						<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly"
							name="MANAGER_APPOINTOR"  placeholder="请输入任命方" 
						<c:if test="${requestParams.COMPANY_SETTYPE=='zwhz'||busRecord.COMPANY_SETTYPE=='zwhz'}">value="董事会" </c:if>
						<c:if test="${requestParams.COMPANY_SETTYPE=='wshz'||busRecord.COMPANY_SETTYPE=='wshz'}">value="股东会" </c:if>
						<c:if test="${requestParams.COMPANY_SETTYPE=='wsdz'||busRecord.COMPANY_SETTYPE=='wsdz'}">value="投资者" </c:if>/></td>
					</tr>
					<tr>
						<th> 任期（年）：</th>
						<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
							name="MANAGER_OFFICETERM" value="三"  placeholder="请输入任期" style="width: 185px;"/></td>
					</tr>
			</table>
			<!--<a href="#" class="syj-closebtn"></a>-->
		</div>
	</div>
</form>
