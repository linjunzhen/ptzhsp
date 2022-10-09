<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>
.field_width{width:192px;}
</style>

<style>
	.lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
	td{
		word-break:break-all; 
	}
</style>
<input type="hidden" id= "YW_ID" value="${busRecord.YW_ID}"/>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="4">项目基本交互单元</th>
	</tr>
	<tr>
		<td class="tab_width"> 投资项目编号：</td>
		<td colspan="3">
		  <input type="text" maxlength="32" class="tab_text" name="PROJECTCODE" />
		  <a id="projectCodeA" onclick="loadTZXMXXData();" class="eflowbutton" style="padding: 3px 10px;">校 验</a>
		  <font id="projectCodeFont" color="red">（请先输入投资项目编号进行校验）</font>
		  <a target="_blank" href="https://fj.tzxm.gov.cn/eap/credit.reportGuide?cantcode=350128&projecttype=A00001" style="color:red;"><省级投资项目申报登记入口></a>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 报建编号：</td>
		<td colspan="3">
			<input type="text" maxlength="32" class="tab_text" name="PRJNUM" value="${busRecord.PRJNUM}"/>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro"   style="margin-top: -1px;">
	<tr>
		<td class="tab_width"> <font class="tab_color">*</font>工程项目名称：</td>
		<td width="35%">
		  <input id="projectName" type="text" maxlength="128" class="tab_text validate[required]"  name="PROJECT_NAME" value="${busRecord.PROJECT_NAME}"/>
		</td>
		<td class="tab_width"> <font class="tab_color">*</font>工程项目代码：</td>
		<td>
		  <input id="projectNum" type="text" class="tab_text validate[required]" name="PROJECT_NUM"   maxlength="32" value="${busRecord.PROJECT_NUM}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 建设单位：</td>
		<td width="35%">
		  <input readonly="true" type="text" style="width:80%" maxlength="2000" class="tab_text validate[]"  name="BUILDCORPNAME" value="${busRecord.BUILDCORPNAME}"  placeholder="系统会根据用户填写自动回填" />
		</td>
		<td class="tab_width"> 建设单位机构代码：</td>
		<td>
		  <input readonly="true" type="text" style="width:80%" class="tab_text validate[]" name="BUILDCORPCODE"   maxlength="2000" value="${busRecord.BUILDCORPCODE}"  placeholder="系统会根据用户填写自动回填" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 施工单位：</td>
		<td width="35%">
		  <input readonly="true" type="text" style="width:80%" maxlength="2000" class="tab_text validate[]"  name="SGUNITS" value="${busRecord.SGUNITS}"  placeholder="系统会根据用户填写自动回填" />
		</td>
		<td class="tab_width"> 监理单位：</td>
		<td>
		  <input readonly="true" type="text" style="width:80%" class="tab_text validate[]" name="JLUNITS"   maxlength="2000" value="${busRecord.JLUNITS}"  placeholder="系统会根据用户填写自动回填" />
		</td>
	</tr>
	               <tr>
        <td class="tab_width">
            <font class="tab_color ">*</font>
            申报项目类型：
        </td>
        <td colspan="3">
            <input type="radio" name="SGXK_SBXMLX" class="validate[required]"  value="1"
                <c:if test="${busRecord.SGXK_SBXMLX=='1'}">checked="checked"</c:if>/>
            施工许可证
            <input type="radio" name="SGXK_SBXMLX" class="validate[required]"  value="2"
                <c:if test="${busRecord.SGXK_SBXMLX=='2'}">checked="checked"</c:if>/>
            桩基（基础）工程
             <input type="radio" name="SGXK_SBXMLX" class="validate[required]"  value="3"
                <c:if test="${busRecord.SGXK_SBXMLX=='3'}">checked="checked"</c:if> disabled/>
           上部（地下室）工程
        </td>
    </tr>
	<!--<tr>
		<td class="tab_width"><font class="tab_color">*</font> 公开方式：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="OPENWAY"
						   dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.OPEN_WAY}"
						   defaultEmptyText="请选择公开方式" name="OPEN_WAY" id="openWay">
			</eve:eveselect>
		</td>
	</tr>-->
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="4">项目基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 工程类别：</td>
		<td width="35%">
				<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="PROTYPE"
				dataInterface="dictionaryService.findDatasForSelect" onchange="changeProType(this.value)"
				defaultEmptyText="请选择工程类别" name="PROTYPE" id="PROTYPE" value="${busRecord.PROTYPE}">
				</eve:eveselect>
		</td>
		<td class="tab_width"><span class="tab_color">*</span> 是否园林绿化工程	</td>
		<td>
			<eve:radiogroup typecode="YesOrNo" width="130px" fieldname="ISGARDEN" value="${busRecord.ISGARDEN}"></eve:radiogroup>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 是否应按《福建省绿色建筑设计标准》（DBJ13-197-2017）进行施工</td>
		<td colspan="3">
			<eve:radiogroup typecode="YesOrNo" width="130px"
			fieldname="ISGREENBUILDING" value="${busRecord.ISGREENBUILDING}"></eve:radiogroup>
			<div style="color:red;width:90%;">填写说明：是否执行绿色建筑标准请查阅施工图审查合格证书或施工图设计文件。</div>
		</td>
	</tr>			
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 建设性质</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="TBPRJPROPERTYDIC"
			dataInterface="dictionaryService.findDatasForSelect"  value="${busRecord.PRJPROPERTYNUM}"
			defaultEmptyText="请选择建设性质" name="PRJPROPERTYNUM">
			</eve:eveselect><div style="color:red;width:90%;">注:工程加固项目选“恢复”</div>
		</td>
		<td class="tab_width"><span class="tab_color">*</span> 建设规模</td>
		<td>
			<input type="text" maxlength="22" class="tab_text validate[required]" name="PRJSIZE" placeholder="请输入建设规模"  value="${busRecord.PRJSIZE}"  onblur="setSgxkjbxx();"/>
			<div style="color:red;width:90%;">填写说明：因施工许可证上的“建设规模”栏目空间有限，字数不能超过22字。建议描述面积或市政长度等，如：1234.12平方米。
			</div>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 立项文号</td>
		<td>
		  <input type="text" class="tab_text validate[required]" name="PRJAPPROVALNUM"  maxlength="100" placeholder="请输入立项文号" value="${busRecord.PRJAPPROVALNUM}"/>
		</td>
		<td class="tab_width"><span class="tab_color">*</span> 立项级别</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="TBLXJBDIC"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PRJAPPROVALLEVELNUM}"
			defaultEmptyText="请选择建设性质" name="PRJAPPROVALLEVELNUM" id="PRJAPPROVALLEVELNUM">
			</eve:eveselect>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 工程所在地址</td>
		<td colspan="3">
		  <input type="text" class="tab_text validate[required]" name="PROADDRESS"  maxlength="100" placeholder="请输入工程所在地址" value="${busRecord.PROADDRESS}"  onblur="setSgxkjbxx();"  style="width:80%" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 质量监督机构</td>
		<td colspan="3">
		  <input type="text" readonly="readonly" class="tab_text inputBackgroundCcc validate[required]" name="QUALSUPERORG"  maxlength="100"  value="平潭综合实验区城乡建设与交通运输服务中心" value="${busRecord.QUALSUPERORG}"  style="width:80%" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 安全监督机构</td>
		<td colspan="3">
		  <input type="text" readonly="readonly" class="tab_text inputBackgroundCcc validate[required]" name="SECSUPERORG"  maxlength="100"  value="平潭综合实验区城乡建设与交通运输服务中心"  style="width:80%" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 施工许可发证机关</td>
		<td colspan="3">
		  <input type="text" class="tab_text validate[required]" name="BUILDSUPERORG"  maxlength="100"  value="平潭综合实验区行政审批局" onblur="setSgxkjbxx();"  style="width:80%" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 是否需要施工图审查</td>
		<td>
			<eve:radiogroup typecode="YesOrNo" width="130px" fieldname="NOTSUNIT" value="${busRecord.NOTSUNIT}"></eve:radiogroup>
		</td>
		<td class="tab_width"><span class="tab_color">*</span> 单位工程个数（个）</td>
		<td>
		  <input type="text" maxlength="6"  class="tab_text inputBackgroundCcc validate[],custom[numberplus]" name="SINGLEPRONUM" 
		   readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.SINGLEPRONUM}"  onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 是否重点工程</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="ISKEYPRO"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.ISKEYPRO}"
			defaultEmptyText="请选择是否重点工程" name="ISKEYPRO" id="ISKEYPRO">
			</eve:eveselect>
		</td>
		<td class="tab_width">首次报监提交时间</td>
		<td>
		  <input type="text" maxlength="16" readonly="readonly" class="tab_text inputBackgroundCcc validate[]" name="SCBJSJ" 
		   value="${time}" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 抗震设防烈度</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="ASEISINTENSITY"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.ASEISINTENSITY}"
			defaultEmptyText="请选择抗震设防烈度" name="ASEISINTENSITY" id="ASEISINTENSITY">
			</eve:eveselect>
		</td>
		<td class="tab_width"><span class="tab_color">*</span> 工程质量目标</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="QUALTARGET"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.QUALTARGET}"
			defaultEmptyText="请选择工程质量目标" name="QUALTARGET" id="QUALTARGET">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 投资来源</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="BSOURCE"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择投资来源" name="BSOURCE"  value="${busRecord.BSOURCE}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><span class="tab_color">*</span> 工程用途</td>
		<td>
			<div id="gcyh1">
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="TBPRJFUNCTIONDIC:1"
			dataInterface="dictionaryService.findTwoDatasForSelect" onchange="setSgxkjbxx();"
			defaultEmptyText="请选择工程用途" name="PRJFUNCTIONNUM"  value="${busRecord.PRJFUNCTIONNUM}">
			</eve:eveselect>
			</div>
			<div id="gcyh2" style="display:none;">
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="TBPRJFUNCTIONDIC:2"
			dataInterface="dictionaryService.findTwoDatasForSelect" onchange="setSgxkjbxx();"
			defaultEmptyText="请选择工程用途" name="PRJFUNCTIONNUM" value="${busRecord.PRJFUNCTIONNUM}" >
			</eve:eveselect>
			</div>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 工程总造价（万元）</td>
		<td>
		  <input type="text" class="tab_text validate[required],custom[JustNumber]" name="INVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）" value="${busRecord.INVEST}" onblur="onlyNumber6(this);setSgxkjbxx();" onkeyup="onlyNumber6(this);"/>
		</td>
		<td class="tab_width">
			<span class="pro_area_1">工程总面积（M<sup style="vertical-align: super;font-size: smaller;">2</sup>）</span>
			<span class="pro_area_2" style="display:none;">市政长度(M)</span>
		</td>
		<td>
			<span class="pro_area_1">
				<input type="text" maxlength="16" class="tab_text inputBackgroundCcc validate[],custom[JustNumber]" name="PROAREA" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.PROAREA}"/>
			</span>
			<span class="pro_area_2" style="display:none;">
				<input type="text" maxlength="16" class="tab_text inputBackgroundCcc validate[],custom[JustNumber]" name="MUNILENGTHS" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.MUNILENGTHS}"/>
			</span>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> ±0.000以上层数</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[],custom[numberplus]" name="STRUCTUPFLOORNUMS"  maxlength="16"  readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTUPFLOORNUMS}"/>
		</td>
		<td class="tab_width"> ±0.000以下层数</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[],custom[numberplus]" name="STRUCTDWFLOORNUMS"  maxlength="16" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTDWFLOORNUMS}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> ±0.000以上面积</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[],custom[JustNumber]" name="STRUCTUPFLOORAREAS"  maxlength="16" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTUPFLOORAREAS}"/>
		</td>
		<td class="tab_width"> ±0.000以下面积</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[],custom[JustNumber]" name="STRUCTDWFLOORAREAS"  maxlength="16" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${busRecord.STRUCTDWFLOORAREAS}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 结构类型</td>
		<td colspan="3">
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" name="STRUCTQUALTYPES"  maxlength="512" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.STRUCTQUALTYPES}"  style="width:80%" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 基础类型</td>
		<td colspan="3">
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" name="BASICQUALTYPES"  maxlength="512" readonly="readonly" placeholder="系统会根据用户填写的单位工程自动回填" value="${busRecord.BASICQUALTYPES}"  style="width:80%" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 施工合同号</td>
		<td colspan="3">
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" name="CONTRACTNUMBER2"  maxlength="512"  readonly="readonly" placeholder="系统会根据用户填写自动回填" value="${busRecord.CONTRACTNUMBER2}"  style="width:80%" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 监理合同号</td>
		<td colspan="3">
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" name="CONTRACTNUMBER1"  maxlength="512"  readonly="readonly" placeholder="系统会根据用户填写自动回填" value="${busRecord.CONTRACTNUMBER1}"  style="width:80%" />
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro"   style="margin-top: -1px;">
	<tr>
		<th colspan="4">施工图审查合格证书编号</th>
	</tr>
	<c:forEach items="${chartreviewnumList}" var="chartreviewnum" varStatus="s">	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证书编号</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" value="${chartreviewnum.CHARTREVIEWNUM}" 
			name="${s.index}CHARTREVIEWNUM" maxlength="32" placeholder="请输入施工图审查合格证书编号" /></td>
	</tr>
	</c:forEach>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="4">申报项信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>申报项名称</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" style="width:80%;"  onblur="setSgxkjbxx();" 
				name="TITLE" id="TITLE" maxlength="100" placeholder="请输入本次申报项名称" value="${busRecord.TITLE}"/>
			<span style="color:red;">*本次申报工程项目名称</span>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>本次申报单位工程个数</td>
		<td><input type="text" class="tab_text validate[required],custom[numberplus]" onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"
			name="NUM"  placeholder="请输入本次申报单位工程个数" maxlength="8" value="${busRecord.NUM}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>报监提交时间：</td>
		<td><input type="text" class="tab_text inputBackgroundCcc validate[required]"  readonly="readonly"
			name="SUBDATE"  placeholder="请输入报监提交时间" value="${time}"  maxlength="16"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>合同开工日期</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" id="PLANBEGDATE" 
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'PLANENDDATE\',{d:-1})}',onpicked:function(dp){getWorkDays();setSgxkjbxx();}})" 
			readonly="readonly" name="PLANBEGDATE"  placeholder="请选择开始时间" value="${busRecord.PLANBEGDATE}"  maxlength="16"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>合同竣工日期</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" id="PLANENDDATE" readonly="readonly"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'PLANBEGDATE\',{d:1})}'
			,onpicked:function(dp){getWorkDays();setSgxkjbxx();}})"
			name="PLANENDDATE"  placeholder="请选择结束时间" value="${busRecord.PLANENDDATE}"  maxlength="16"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>合同工期</td>
		<td colspan="3">
			<input type="text" class="tab_text inputBackgroundCcc validate[required]" readonly="readonly"
				name="WORKDAYS" id="WORKDAYS" maxlength="100" placeholder="根据用户填写的合同日期自动计算" value="${busRecord.WORKDAYS}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><span class="tab_color">*</span> 本次申报工程总造价（万元）</td>
		<td colspan="3">
		  <input type="text" class="tab_text validate[required],custom[JustNumber]" name="PROCOST"  maxlength="16"  placeholder="请输入本次申报工程总造价（万元）" onblur="onlyNumber6(this);setSgxkjbxx();" onkeyup="onlyNumber6(this);" value="${busRecord.PROCOST}"/>
		</td>
	</tr>
</table>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->