<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
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
<form action="" id="SGXKJBXX_FORM" >
	<div class="syj-title1 ">
		<span>施工许可基本信息</span>
	</div>	
	<div style="position:relative;">	
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th>施工许可发证机关</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" name="CONSTRORG" maxlength="100" placeholder="请输入施工许可发证机关" value="平潭综合实验区行政审批局"  readonly="readonly"/></td>				
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
			<tr>
				<th><font class="syj-color2">*</font>建设用地规划许可证<br/>编号</th>
				<td><input type="text" class="syj-input1 validate[required]" name="BULDPLANNUM" maxlength="32" placeholder="请输入建设用地规划许可证编号" value="${busRecord.BULDPLANNUM}"/></td>				
				<th><font class="syj-color2">*</font>建设工程规划许可证<br/>编号</th>
				<td><input type="text" class="syj-input1 validate[required]" name="PROJECTPLANNUM" maxlength="32" placeholder="请输入建设工程规划许可证编号" value="${busRecord.PROJECTPLANNUM}"/></td>
			</tr>
			<tr>
				<th>建设单位</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="CONUNIT1" maxlength="2000" placeholder="自动回填" value="${busRecord.BUILDCORPNAME}"  readonly="readonly"/>
				</td>
				<th><font class="syj-color2">*</font>所有制性质</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="OWNERSHIP"
					dataInterface="dictionaryService.findDatasForSelect"  value="${busRecord.OWNERSHIP}"
					defaultEmptyText="请选择所有制性质" name="OWNERSHIP" id="OWNERSHIP">
					</eve:eveselect>
				</td>	
			</tr>
			<tr>	
				<th>建设单位机构代码</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="CONORGCODE" maxlength="2000" placeholder="自动回填" value="${busRecord.BUILDCORPCODE}"  readonly="readonly"/></td>	
				<th>法人代表</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="LEGALPERSON" maxlength="512" placeholder="自动回填" value="${busRecord.LEGALPERSON}"  readonly="readonly"/></td>			
			</tr>
			<tr>
				<th>建设单位项目负责人</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="CONPERSON" maxlength="512" placeholder="自动回填" value="${busRecord.CONPERSON}"  readonly="readonly"/></td>	
				<th><font class="syj-color2">*</font>负责人联系电话</th>
				<td><input type="text" class="syj-input1 validate[required]" name="CONPERSONTEL" maxlength="512" placeholder="请输入负责人联系电话" value="${busRecord.CONPERSONTEL}"/></td>	
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>建设单位地址</th>
				<td><input type="text" class="syj-input1 validate[required]" name="CONADDRESS" maxlength="512" placeholder="请输入建设单位地址" value="${busRecord.CONADDRESS}"/></td>	
				<th><font class="syj-color2">*</font>建设单位经办人姓名</th>
				<td><input type="text" class="syj-input1 validate[required]" name="JBRNAME" maxlength="32" placeholder="请输入建设单位经办人姓名" value="${busRecord.JBRNAME}"/></td>	
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>建设单位经办人证件类型</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'JBRIDENTITY');"
					defaultEmptyText="请选择证件类型" name="JBRIDENTITYTYPENUM" id="JBRIDENTITYTYPENUM"   value="${busRecord.JBRIDENTITYTYPENUM}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>建设单位经办人证件号码</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="JBRIDENTITY"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.JBRIDENTITY}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>建设单位经办人联系电话</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required]" name="JBRTEL" maxlength="11" placeholder="请输入建设单位经办人联系电话" value="${busRecord.JBRTEL}"/></td>	
			</tr>
			<tr>
				<th>工程项目名称</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="PRONAME" maxlength="100" placeholder="自动回填" value="${busRecord.TITLE}"  readonly="readonly"/>
					<div style="color:red;">*自动回填“项目基本信息”中的本次“申报项名称”
					</div>
				</td>	
				<th>工程地点</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="BELONG" maxlength="100" placeholder="自动回填" value="${busRecord.PROADDRESS}"  readonly="readonly"/></td>	
			</tr>
			<tr>
				<th>合同开工日期</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="planBegDate" maxlength="16" placeholder="自动回填" value="${busRecord.PLANBEGDATE}"  readonly="readonly"/>
				</td>	
				<th>合同竣工日期	</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="planEndDate" maxlength="16" placeholder="自动回填" value="${busRecord.PLANENDDATE}"  readonly="readonly"/></td>	
			</tr>
			<tr>
				<th>合同工期</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="workDays" maxlength="16" placeholder="自动回填" value="${busRecord.WORKDAYS}"  readonly="readonly"/>
				</td>	
				<th>合同价格（万元）</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="proCost" maxlength="16" placeholder="自动回填" value="${busRecord.PROCOST}"  readonly="readonly"/></td>	
			</tr>
			<tr>
				<th>建设规模</th>
				<td colspan="3">
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="PURIDJS" maxlength="22" placeholder="自动回填" value="${busRecord.PRJSIZE}"/>
				</td>	
			</tr>
			<tr>
				<th>房建建筑面积（m<sup style="vertical-align: super;font-size: smaller;">2</sup>）</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="proArea" maxlength="16" value="${busRecord.PROAREA}"  readonly="readonly"/>
				</td>	
				<th>±0.000以上层数</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="structUpfloorNum" maxlength="16" value="${busRecord.STRUCTUPFLOORNUMS}"  readonly="readonly"/></td>	
			</tr>
			<tr>
				<th>市政长度（m）</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="muniLength" maxlength="16" value="${busRecord.MUNILENGTHS}"  readonly="readonly"/>
				</td>	
				<th>±0.000以下层数</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[]" id="structDwfloorNum" maxlength="16" value="${busRecord.STRUCTDWFLOORNUMS}"  readonly="readonly"/></td>	
			</tr>
			<tr>
				<th>工程类型</th>
				<td colspan="3">	
					<eve:eveselect clazz="input-dropdown w280 inputBackgroundCcc validate[required]" dataParams="TBPRJFUNCTIONDIC"
					dataInterface="dictionaryService.findDatasForSelect" 
					defaultEmptyText="请选择工程类型" name="sgxkgclx"    value="${busRecord.PRJFUNCTIONNUM}">
					</eve:eveselect>
				</td>
			</tr>	
			<tr>
				<th> 结构类型</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="sgxkjglx"  maxlength="512" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.STRUCTQUALTYPES}"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>工程质量安全监督机构</th>
				<td colspan="3">
					<input type="text" class="syj-input1 inputBackgroundCcc validate[required] w878" id="qualSuperOrg" maxlength="22" placeholder="请输工程质量安全监督机构" value="平潭综合实验区城乡建设与交通运输服务中心" readonly="readonly"/>
				</td>	
			</tr>	
		</table>		
		<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3" id="sgxkjbxxTable">
			<tr>
				<th style="width: 18%;">参建各方责任主体类型</th>
				<th style="width: 35%;">参建各方责任主体名称</th>
				<th style="width: 15%;">法定代表人</th>
				<th style="width: 15%;">项目负责人</th>
				<th style="width: 17%;">项目负责人联系电话</th>
			</tr>
			<c:forEach items="${sgdwList}" var="sgdw" varStatus="s">
			<tr class="sgxkjbxxTr">
				<td style="text-align: center;">施工单位</td>
				<td style="text-align: center;">${sgdw.CORPNAME}</td>
				<td style="text-align: center;">${sgdw.LEGAL_NAME}</td>
				<td style="text-align: center;">${sgdw.PERSONNAME}</td>
				<td style="text-align: center;">${sgdw.PERSONPHONE}</td>
			</tr>
			</c:forEach>
			<c:forEach items="${sjdwList}" var="sjdw" varStatus="s">
			<tr class="sgxkjbxxTr">
				<td style="text-align: center;">设计单位</td>
				<td style="text-align: center;">${sjdw.CORPNAME}</td>
				<td style="text-align: center;">${sjdw.LEGAL_NAME}</td>
				<td style="text-align: center;">${sjdw.PERSONNAME}</td>
				<td style="text-align: center;">${sjdw.PERSONPHONE}</td>
			</tr>
			</c:forEach>
			<c:forEach items="${jldwList}" var="jldw" varStatus="s">
			<tr class="sgxkjbxxTr">
				<td style="text-align: center;">监理单位</td>
				<td style="text-align: center;">${jldw.CORPNAME}</td>
				<td style="text-align: center;">${jldw.LEGAL_NAME}</td>
				<td style="text-align: center;">${jldw.PERSONNAME}</td>
				<td style="text-align: center;">${jldw.PERSONPHONE}</td>
			</tr>
			</c:forEach>
			<c:forEach items="${kcdwList}" var="kcdw" varStatus="s">
			<tr class="sgxkjbxxTr">
				<td style="text-align: center;">勘察单位</td>
				<td style="text-align: center;">${kcdw.CORPNAME}</td>
				<td style="text-align: center;">${kcdw.LEGAL_NAME}</td>
				<td style="text-align: center;">${kcdw.PERSONNAME}</td>
				<td style="text-align: center;">${kcdw.PERSONPHONE}</td>
			</tr>
			</c:forEach>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th>申请意见</th>
				<td colspan="3">
					<textarea rows="3" cols="200" name="APPLYUNITREMARK" 
					class="eve-textarea validate[],maxSize[512]"
					style="width: 838px; height:100px;"  placeholder="请输入申请意见" >  上述表内容已审核，证明文件真实有效、齐备，具备质量安全监督手续和施工许可办理条件，现申请办理质量安全监督手续和施工许可证。
					</textarea>
				</td>				
			</tr>
		</table>
	</div>
</form>
