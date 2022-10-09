<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
</style>
<form action="" id="INFO_FORM"  >
	<input type="hidden" name="JSDW_JSON" />
	<input type="hidden" name="SGDW_JSON" />
	<input type="hidden" name="JLDW_JSON" />
	<div class="syj-title1 tmargin20">
		<span>项目基本信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><span class="syj-color2">*</span> 施工许可证号</th>
				<td colspan="3">
					<input type="hidden" id= "YW_ID" value="${busRecord.YW_ID}"/>
					<input type="text" maxlength="32" class="syj-input1" name="CONSTRNUM" style="width: 200px;" value="${busRecord.CONSTRNUM}"/>
						<a href="javascript:loadData();" class="projectBtnA">校 验</a>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 报建编号</th>
				<td colspan="3">
					<input type="text" maxlength="32" class="syj-input1 inputBackgroundCcc validate[required]" id="PRJNUM" name="PRJNUM" placeholder="请输入报建编号" 
					value="${busRecord.PRJNUM}" readonly="true"/>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
			
			<tr>
				<th><span class="syj-color2">*</span> 工程项目名称</th>
				<td>
				  <input id="projectName" type="text" class="syj-input1 inputBackgroundCcc validate[required]" name="PROJECT_NAME" maxlength="128" placeholder="请输入工程项目名称" value="${busRecord.PROJECT_NAME}" readonly="true"/>
				</td>
				<th><span class="syj-color2">*</span> 工程项目代码</th>
				<td>
				  <input id="projectNum" type="text" maxlength="32" class="syj-input1 inputBackgroundCcc validate[required]" name="PROJECTCODE"  placeholder="请输入工程项目代码" value="${busRecord.PROJECTCODE}" readonly="true"/>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><span class="syj-color2">*</span> 废止说明</th>
				<td colspan="3">
					<textarea rows="3" cols="200" name="CANCELREASON" 
					class="eve-textarea validate[required],maxSize[128]"
					style="width:100%;height:100px;"  placeholder="请输入废止说明" >${busRecord.CANCELREASON}</textarea>
				</td>				
			</tr>
		</table>
	</div>
</form>
