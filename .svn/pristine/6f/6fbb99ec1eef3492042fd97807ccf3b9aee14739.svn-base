<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
td{
	word-break:break-all; 
}
</style>
<form action="" id="INFO_FORM"  >
	<div class="syj-title1 ">
		<span>项目基本交互单元</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><span class="syj-color2">*</span> 投资项目编号</th>
				<td colspan="3">
					<input type="hidden" id= "YW_ID" value="${busRecord.YW_ID}"/>
					<input type="hidden"  id="project_code" name="PROJECT_CODE"  value="${projectCode}"/>
					<c:if test="${projectCode == null }">
						<input type="text" maxlength="32" class="syj-input1 validate[required]" name="PROJECTCODE" style="width: 200px;" readonly="readonly"/>
						<a href="javascript:loadTZXMXXData();" class="projectBtnA">校 验</a><font color="red">（请先输入投资项目编号进行校验）</font> <a target="_blank" href="https://fj.tzxm.gov.cn/eap/credit.reportGuide?cantcode=350128&projecttype=A00001" style="color:red;"><省级投资项目申报登记入口></a>
					</c:if>
					<c:if test="${projectCode != null }">
						<input type="text" maxlength="32" class="syj-input1" id="projectCode" name="PROJECTCODE" value="${projectCode}" readonly="true"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 报建编号</th>
				<td colspan="3">
					<input type="text" maxlength="32" class="syj-input1 validate[required]" id="PRJNUM" name="PRJNUM" placeholder="请输入报建编号" 
					value="${busRecord.PRJNUM}" readonly="true"/>
					<div style="color:red">						注：至2020年1月10日起的项目报建编号系统会自动获取，2020年1月10日前的项目报建编号，请登录“福建省公共资源交易电子行政监督平台”地址为：ggzyjd.fj.gov.cn，获取后填入。
					</div>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
			
			<tr>
				<th><span class="syj-color2">*</span> 工程项目名称</th>
				<td>
				  <input id="projectName" type="text" class="syj-input1 validate[required]" name="PROJECT_NAME" maxlength="128" placeholder="请输入工程项目名称" value="${busRecord.PROJECT_NAME}"  readonly="true"/>
				</td>
				<th><span class="syj-color2">*</span> 工程项目代码</th>
				<td>
				  <input id="projectNum" type="text" maxlength="32" class="syj-input1 validate[required]" name="PROJECT_NUM"  placeholder="请输入工程项目代码" value="${busRecord.PROJECT_NUM}"  readonly="true"/>
				</td>
			</tr>
			
			<tr>
				<th> 建设单位</th>
				<td>
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="BUILDCORPNAME"  maxlength="2000" readonly="readonly" placeholder="系统会根据用户填写的建设单位自动回填"  value="${busRecord.BUILDCORPNAME}"/>
				</td>
				<th> 建设单位机构代码</th>
				<td>
				  <input type="text" maxlength="2000" class="syj-input1 inputBackgroundCcc validate[]" name="BUILDCORPCODE" readonly="readonly" placeholder="系统会根据用户填写的建设单位自动回填"  value="${busRecord.BUILDCORPCODE}" />
				</td>
			</tr>	
			<tr>
				<th> 施工单位</th>
				<td>
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="SGUNITS"  maxlength="2000" readonly="readonly" placeholder="系统会根据用户填写的施工单位自动回填" value="${busRecord.SGUNITS}"/>
				</td>
				<th> 监理单位</th>
				<td>
				  <input type="text" maxlength="2000" class="syj-input1 inputBackgroundCcc validate[]" name="JLUNITS"  readonly="readonly" placeholder="系统会根据用户填写的监理单位自动回填" value="${busRecord.JLUNITS}"/>
				</td>
			</tr>
			
            <tr>
                <th><span class="syj-color2">*</span> 申报项目类型</th>
                <td colspan="3">
                    <input type="radio" name="SGXK_SBXMLX"  value="1" <c:if test="${busRecord.SGXK_SBXMLX =='1'}">checked="checked"</c:if>  onclick="showOrHideZjdwgcTab(1);" />施工许可证
                    <input type="radio"  class="site-demo-active"   name="SGXK_SBXMLX" value="2"  <c:if test="${busRecord.SGXK_SBXMLX =='2'}">checked="checked"</c:if>  onclick="showOrHideZjdwgcTab(2);" />桩基（基础）工程
                    <input type="radio" id="SGXK_SBXMLX3" name="SGXK_SBXMLX" value="3" <c:if test="${busRecord.SGXK_SBXMLX =='3'}">checked="checked"</c:if>  disabled/>上部（地下室）工程               
                </td>
            </tr>			
			
			
			<!--<tr>
				<th><span class="syj-color2">*</span> 公开方式</th>
				<td>
					<eve:eveselect clazz="syj-input1 validate[required] field_width" dataParams="OPENWAY"
								   dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.OPEN_WAY}"
								   defaultEmptyText="请选择公开方式" name="OPEN_WAY" id="openWay">
					</eve:eveselect>
				</td>
				<th></th>
				<td></td>
			</tr>-->

		</table>
	</div>
	
	<div class="syj-title1 ">
		<span>项目基本信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><span class="syj-color2">*</span> 工程类别</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="PROTYPE"
					dataInterface="dictionaryService.findDatasForSelect" onchange="changeProType(this.value);clearDwgcType();"
					defaultEmptyText="请选择工程类别" name="PROTYPE" id="PROTYPE" value="${busRecord.PROTYPE}">
					</eve:eveselect>
				</td>
				<th><span class="syj-color2">*</span> 是否园林绿化工程	</th>
				<td>
					<eve:radiogroup typecode="YesOrNo" width="130px" fieldname="ISGARDEN" value="${busRecord.ISGARDEN}"></eve:radiogroup>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 是否应按《福建省绿色建筑设计标准》（DBJ13-197-2017）进行施工</th>
				<td colspan="3">
					<eve:radiogroup typecode="YesOrNo" width="130px"
					fieldname="ISGREENBUILDING" value="${busRecord.ISGREENBUILDING}"></eve:radiogroup>
					<div style="color:red; ">填写说明：是否执行绿色建筑标准请查阅施工图审查合格证书或施工图设计文件。</div>
				</td>
			</tr>			
			<tr>
				<th><span class="syj-color2">*</span> 建设性质</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="TBPRJPROPERTYDIC"
					dataInterface="dictionaryService.findDatasForSelect"  value="${busRecord.PRJPROPERTYNUM}"
					defaultEmptyText="请选择建设性质" name="PRJPROPERTYNUM">
					</eve:eveselect><div style="color:red;">注:工程加固项目选“恢复”</div>
				</td>
				<th><span class="syj-color2">*</span> 建设规模</th>
				<td>
					<input type="text" maxlength="22" class="syj-input1 validate[required]" name="PRJSIZE" placeholder="请输入建设规模"  value="${busRecord.PRJSIZE}"  onblur="setSgxkjbxx();"/>
					<div style="color:red; ">填写说明：因施工许可证上的“建设规模”栏目空间有限，字数不能超过22字。建议描述面积或市政长度等，如：1234.12平方米。
					</div>
				</td>
			</tr>	
			<tr>
				<th><span class="syj-color2">*</span> 立项文号</th>
				<td>
				  <input type="text" class="syj-input1 validate[required]" name="PRJAPPROVALNUM"  maxlength="100" placeholder="请输入立项文号" value="${busRecord.PRJAPPROVALNUM}"/>
				</td>
				<th><span class="syj-color2">*</span> 立项级别</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="TBLXJBDIC"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PRJAPPROVALLEVELNUM}"
					defaultEmptyText="请选择立项级别" name="PRJAPPROVALLEVELNUM" id="PRJAPPROVALLEVELNUM">
					</eve:eveselect>
				</td>
			</tr>	
			<tr>
				<th><span class="syj-color2">*</span> 工程所在地址</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 validate[required] w878" name="PROADDRESS"  maxlength="100" placeholder="请输入工程所在地址" value="${busRecord.PROADDRESS}"  onblur="setSgxkjbxx();"/>
				</td>
			</tr>	
			<tr>
				<th><span class="syj-color2">*</span> 质量监督机构</th>
				<td colspan="3">
				  <input type="text" readonly="readonly" class="syj-input1 inputBackgroundCcc validate[required] w878" name="QUALSUPERORG"  maxlength="100"  value="平潭综合实验区城乡建设与交通运输服务中心" value="${busRecord.QUALSUPERORG}"/>
				</td>
			</tr>	
			<tr>
				<th><span class="syj-color2">*</span> 安全监督机构</th>
				<td colspan="3">
				  <input type="text" readonly="readonly" class="syj-input1 inputBackgroundCcc validate[required] w878" name="SECSUPERORG"  maxlength="100"  value="平潭综合实验区城乡建设与交通运输服务中心"/>
				</td>
			</tr>	
			<tr>
				<th><span class="syj-color2">*</span> 施工许可发证机关</th>
				<td colspan="3">
				  <input type="text" readonly="readonly" class="syj-input1 inputBackgroundCcc validate[required] w878" name="BUILDSUPERORG"  maxlength="100"  value="平潭综合实验区行政审批局" onblur="setSgxkjbxx();"/>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 是否需要施工图审查</th>
				<td>
					<eve:radiogroup typecode="YesOrNo" width="130px" fieldname="NOTSUNIT" value="${busRecord.NOTSUNIT}"></eve:radiogroup>
				</td>
				<th><span class="syj-color2">*</span> 单位工程个数（个）</th>
				<td>
				  <input type="text" maxlength="6"  class="syj-input1 inputBackgroundCcc validate[],custom[numberplus]" name="SINGLEPRONUM" 
				   readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.SINGLEPRONUM}"  onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"/>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 是否重点工程</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="ISKEYPRO"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.ISKEYPRO}"
					defaultEmptyText="请选择是否重点工程" name="ISKEYPRO" id="ISKEYPRO">
					</eve:eveselect>
				</td>
				<th>首次报监提交时间</th>
				<td>
				  <input type="text" maxlength="16" readonly="readonly" class="syj-input1 inputBackgroundCcc validate[]" name="SCBJSJ" 
				   value="${time}" />
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 抗震设防烈度</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="ASEISINTENSITY"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.ASEISINTENSITY}"
					defaultEmptyText="请选择抗震设防烈度" name="ASEISINTENSITY" id="ASEISINTENSITY">
					</eve:eveselect>
				</td>
				<th><span class="syj-color2">*</span> 工程质量目标</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="QUALTARGET"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.QUALTARGET}"
					defaultEmptyText="请选择工程质量目标" name="QUALTARGET" id="QUALTARGET">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 投资来源</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="BSOURCE"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择投资来源" name="BSOURCE"  value="${busRecord.BSOURCE}">
					</eve:eveselect>
				</td>
				<th><span class="syj-color2">*</span> 工程用途</th>
				<td>
					<div id="gcyh1">
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="TBPRJFUNCTIONDIC:1"
					dataInterface="dictionaryService.findTwoDatasForSelect" onchange="setSgxkjbxx();"
					defaultEmptyText="请选择工程用途" name="PRJFUNCTIONNUM"  value="${busRecord.PRJFUNCTIONNUM}">
					</eve:eveselect>
					</div>
					<div id="gcyh2" style="display:none;">
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="TBPRJFUNCTIONDIC:2"
					dataInterface="dictionaryService.findTwoDatasForSelect" onchange="setSgxkjbxx();"
					defaultEmptyText="请选择工程用途" name="PRJFUNCTIONNUM" value="${busRecord.PRJFUNCTIONNUM}" >
					</eve:eveselect>
					</div>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 工程总造价（万元）</th>
				<td>
				  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="INVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）" value="${busRecord.INVEST}" onblur="onlyNumber6(this);setSgxkjbxx();" onkeyup="onlyNumber6(this);"/>
				</td>
				<th>
					<span class="pro_area_1">工程总面积（M<sup style="vertical-align: super;font-size: smaller;">2</sup>）</span>
					<span class="pro_area_2" style="display:none;">建筑高度(M)</span>
				</th>
				<td>
					<span class="pro_area_1">
						<input type="text" maxlength="16" class="syj-input1 inputBackgroundCcc validate[],custom[JustNumber]" name="PROAREA" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);" value="${busRecord.PROAREA}"/>
					</span>
					<span class="pro_area_2" style="display:none;">
						<input type="text" maxlength="16" class="syj-input1 inputBackgroundCcc validate[],custom[JustNumber]" name="MUNILENGTHS" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.MUNILENGTHS}"/>
					</span>
				</td>
			</tr>
			<tr>
				<th> ±0.000以上层数</th>
				<td>
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[],custom[numberplus]" name="STRUCTUPFLOORNUMS"  maxlength="16"  readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTUPFLOORNUMS}"/>
				</td>
				<th> ±0.000以下层数</th>
				<td>
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[],custom[numberplus]" name="STRUCTDWFLOORNUMS"  maxlength="16" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTDWFLOORNUMS}"/>
				</td>
			</tr>
			<tr>
				<th> ±0.000以上面积</th>
				<td>
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[],custom[JustNumber]" name="STRUCTUPFLOORAREAS"  maxlength="16" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTUPFLOORAREAS}"/>
				</td>
				<th> ±0.000以下面积</th>
				<td>
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[],custom[JustNumber]" name="STRUCTDWFLOORAREAS"  maxlength="16" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTDWFLOORAREAS}"/>
				</td>
			</tr>
			<tr>
				<th> 结构类型</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="STRUCTQUALTYPES"  maxlength="512" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.STRUCTQUALTYPES}"/>
				</td>
			</tr>
			<tr>
				<th> 基础类型</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="BASICQUALTYPES"  maxlength="512" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.BASICQUALTYPES}"/>
				</td>
			</tr>
			<tr>
				<th> 施工合同号</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="CONTRACTNUMBER2"  maxlength="512"  readonly="readonly" placeholder="系统会根据用户填写自动回填" value="${busRecord.CONTRACTNUMBER2}"/>
				</td>
			</tr>
			<tr>
				<th> 监理合同号</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 inputBackgroundCcc validate[]" name="CONTRACTNUMBER1"  maxlength="512"  readonly="readonly" placeholder="系统会根据用户填写自动回填" value="${busRecord.CONTRACTNUMBER1}"/>
				</td>
			</tr>
		</table>
		<div  id="chartreviewnum">
			<div class="syj-title1 ">
				<a href="javascript:void(0);" onclick="javascript:addChartreviewnumDiv();" class="syj-addbtn" id="addChartreviewnum" >添 加</a><span>施工图审查合格证书编号</span>
			</div>
			<div id="chartreviewnumDiv">
				<c:if test="${empty chartreviewnumList}">
				<div style="position:relative;">	
					<table cellpadding="0" cellspacing="0" class="syj-table2 ">
						<tr>
							<th><font class="syj-color2">*</font>证书编号</th>
							<td colspan="3"><input type="text" class="syj-input1 validate[required]" 
								name="CHARTREVIEWNUM" maxlength="32" placeholder="请输入施工图审查合格证书编号"/></td>
						</tr>
					</table>
				</div>
				</c:if>
				<c:if test="${!empty chartreviewnumList}">
				<jsp:include page="./children/initChartreviewnumDiv.jsp"></jsp:include>		
				</c:if>
			</div>
		</div>
	</div>
	<div class="syj-title1 ">
		<span>申报项信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>申报项名称</th>
				<td colspan="3">
					<input type="text" class="syj-input1 validate[required]" onblur="setSgxkjbxx();" 
						name="TITLE" id="TITLE" maxlength="100" placeholder="请输入本次申报项名称" value="${busRecord.TITLE}"/>
						<span style="color:red;">*本次申报工程项目名称</span>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " >
			<tr>
				<th><font class="syj-color2">*</font>本次申报单位工程个数</th>
				<td><input type="text" class="syj-input1 validate[required],custom[numberplus]" onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"
					name="NUM"  placeholder="请输入本次申报单位工程个数" maxlength="8" value="${busRecord.NUM}"/></td>
				<th><font class="syj-color2">*</font>报监提交时间：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly"
					name="SUBDATE"  placeholder="请输入报监提交时间" value="${time}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>合同开工日期</th>
				<td>
					<input type="text" class="Wdate validate[required]" id="PLANBEGDATE" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'PLANENDDATE\',{d:-1})}',onpicked:function(dp){getWorkDays();setSgxkjbxx();}})" 
					readonly="readonly" name="PLANBEGDATE"  placeholder="请选择开始时间" value="${busRecord.PLANBEGDATE}"  maxlength="16"/>
				</td>
				<th><font class="syj-color2">*</font>合同竣工日期</th>
				<td>
					<input type="text" class="Wdate validate[required]" id="PLANENDDATE" readonly="readonly"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'PLANBEGDATE\',{d:1})}'
					,onpicked:function(dp){getWorkDays();setSgxkjbxx();}})"  
					name="PLANENDDATE"  placeholder="请选择结束时间" value="${busRecord.PLANENDDATE}"  maxlength="16"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>合同工期</th>
				<td colspan="3">
					<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
						name="WORKDAYS" id="WORKDAYS" maxlength="100" placeholder="根据用户填写的合同日期自动计算" value="${busRecord.WORKDAYS}"/>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 本次申报工程总造价（万元）</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="PROCOST"  maxlength="16"  placeholder="请输入本次申报工程总造价（万元）" onblur="onlyNumber6(this);setSgxkjbxx();" onkeyup="onlyNumber6(this);" value="${busRecord.PROCOST}"/>
				</td>
			</tr>
		</table>
	</div>
</form>
