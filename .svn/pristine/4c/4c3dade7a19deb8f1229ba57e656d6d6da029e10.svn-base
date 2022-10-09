<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${directorList}" var="director" varStatus="s">
<div class="addBox">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
		
		<tr>
			<th><font class="syj-color2">*</font>姓名：</th>
			<td><input type="text" class="syj-input1 validate[required]" onblur="setDsbaxx();"
				name="${s.index}DIRECTOR_NAME"  placeholder="请输入姓名"  maxlength="32" value="${director.DIRECTOR_NAME}"/></td>
			<th>复用旧董事：</th>
			<td>
				<select name="oldDirectorInfo" class="input-dropdown " onchange="setDirectorInfo(this,'${s.index}')">
					<option value="">请选择复用人员信息</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>职务：</th>
			<td>	
				<c:if test="${requestParams.COMPANY_SETNATURE=='01'||busRecord.COMPANY_SETNATURE=='01'
					||requestParams.COMPANY_SETNATURE=='03'||busRecord.COMPANY_SETNATURE=='03'}">
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:04"
					dataInterface="dictionaryService.findTwoDatasForSelect"
					defaultEmptyText="请选择职务" name="${s.index}DIRECTOR_JOB"  onchange="setDsbaxx();setDirectorJob(this.value,'${s.index}DIRECTOR_APPOINTOR')"
					value="${director.DIRECTOR_JOB}">
					</eve:eveselect>
				</c:if>
				<c:if test="${requestParams.COMPANY_SETNATURE=='02'||busRecord.COMPANY_SETNATURE=='02'
				||requestParams.COMPANY_SETNATURE=='07'||busRecord.COMPANY_SETNATURE=='07'
					||requestParams.COMPANY_SETNATURE=='04'||busRecord.COMPANY_SETNATURE=='04'}">
					<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjzw:01"
					dataInterface="dictionaryService.findTwoDatasForSelect"
					defaultEmptyText="请选择职务" name="${s.index}DIRECTOR_JOB" onchange="setDsbaxx();setDirectorJob(this.value,'${s.index}DIRECTOR_APPOINTOR')"
					value="${director.DIRECTOR_JOB}">
					</eve:eveselect>
				</c:if>
			</td>
			<th><font class="syj-color2">*</font>手机号码：</th>
			<td >
				<input type="text" class="syj-input1 validate[required]"
				name="${s.index}DIRECTOR_PHONE"  placeholder="请输入手机号码"  maxlength="32" value="${director.DIRECTOR_PHONE}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>证件类型：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'${s.index}DIRECTOR_IDNO');" 
				defaultEmptyText="请选择证件类型" name="${s.index}DIRECTOR_IDTYPE" value="${director.DIRECTOR_IDTYPE}">
				</eve:eveselect>
				<script type="text/javascript">
					$(function() {
						setZjValidate('${director.DIRECTOR_IDTYPE}','${s.index}DIRECTOR_IDNO');				
					});
				</script>
			</td>
			<th><font class="syj-color2">*</font>证件号码：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[equalsDirectorIdno]"
				name="${s.index}DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${director.DIRECTOR_IDNO}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>产生方式：</th>
			<td>
				<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="ssdjcsfs"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产生方式" name="${s.index}DIRECTOR_GENERATION_MODE"  value="${director.DIRECTOR_GENERATION_MODE}" >
				</eve:eveselect>
			</td>
			<th><font class="syj-color2">*</font>国别：</th>
			<td >
				<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjgb"
				dataInterface="dictionaryService.findDatasForCountrySelect"
				defaultEmptyText="请选择国别" name="${s.index}DIRECTOR_COUNTRY" value="${director.DIRECTOR_COUNTRY}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th> 任期（年）：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc"  readonly="readonly"
				name="${s.index}DIRECTOR_OFFICETERM" value="${director.DIRECTOR_OFFICETERM}"  placeholder="请输入任期"/></td>
			<th><font class="syj-color2">*</font>任命方：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"
				name="${s.index}DIRECTOR_APPOINTOR"  placeholder="请输入任命方" readonly="readonly"  value="${director.DIRECTOR_APPOINTOR}"/></td>
		</tr>
	</table>
	<c:if test="${s.index>0}">
	<a  href="javascript:void(0);" onclick="javascript:delDsxxDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>