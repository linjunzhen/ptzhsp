<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String ywId = request.getParameter("SB_YWID");
	request.setAttribute("ywId",ywId);
	String exeId = request.getParameter("exeId");
	request.setAttribute("exeId",exeId);
	
%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
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
</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jbxx">
	<input name="PUSH_FLAG" type="hidden"/>
	<input name="QYCB_DWBH" type="hidden"/>
	<input name="QYCB_JBJGDM" type="hidden"/>
	
	<tr>
		<th colspan="6">单位基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>单位管理码：</td>
		<td>
			<input type="text" class="tab_text validate[required]"  name="QYCB_DWGLM"/>
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="dwxxcx();"></a>
		</td>
		<td class="tab_width">单位名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="QYCB_DWMC" />
		</td>
		<td class="tab_width"> 单位状态：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBDWZT"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_DWZT">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">单位类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBDWLX"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYCB_DWLX">
			</eve:eveselect>
		</td>		
		<td class="tab_width">证照类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBZZLX"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYCB_ZZLX">
			</eve:eveselect>
		</td>
		<td class="tab_width">证照号码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="QYCB_ZZHM"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">经济类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBJJLX"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYCB_JJLX">
			</eve:eveselect>
		</td>		
		<td class="tab_width">所属行业：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBHYDM"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYCB_SSHY">
			</eve:eveselect>
		</td>
		<td class="tab_width">成立日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="QYCB_CLRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="grxx">
	<tr>
		<th colspan="6">个人信息</th>
	</tr>
	<tr>
		<td class="tab_width">个人管理码（自动生成码）：</td>
		<td>
			<input type="text" class="tab_text " disabled="disabled" name="QYCB_GRGLM" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="zjlx"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYCB_ZJLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="QYCB_ZJHM" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="personxxcx();"></a>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="QYCB_XM" />			
		</td>
		<td class="tab_width">曾用名：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="QYCB_CYM" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>社会保障号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="QYCB_SHBZHM" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="sex"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_XB">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>民族：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBMZ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_MZ">
			</eve:eveselect>
		</td>
		<td class="tab_width">临时缴费账号标志：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="lsjfzhbz"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="YCB_ZHBZ">
			</eve:eveselect>
		</td>		
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>身份证出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="QYCB_CSRQ"
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>档案出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="QYCB_DACSRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>参加工作时间：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="QYCB_GZSJ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>		
	</tr>
	<tr>
		<td class="tab_width">专业技术职务系列名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="QYCB_ZWMC" />
		</td>
		<td class="tab_width"> 专业技术职务等级：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBZYZGDJ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_ZWDJ">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 国家职业资格等级：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBZYDJ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_ZYZGDJ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 行政职务：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBXZZW"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_XZZW">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 职工岗位：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="zggw"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_ZGGW">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 婚姻状况：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="hyzk"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_HYZK">
			</eve:eveselect>
		</td>
	</tr>
	<tr>	
		<td class="tab_width"><font class="tab_color">*</font>户口性质：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBHKXZ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_HKXZ">
			</eve:eveselect>
		</td>
		<td class="tab_width">农民工标识：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBNMGBS"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_NMGBS">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>学历（文化程度）：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBXL"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_XL">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">户口所在地区县街乡：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="QYCB_HKXX" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>户口所在地址：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[required]" style="width:400px" name="QYCB_HKDZ" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">邮寄社保对账单地址：</td>
		<td colspan='5'>
			<input type="text" class="tab_text validate[]" style="width:400px" name="QYCB_SBDZDDZ" />
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="lxxx">
	<tr>
		<th colspan="6">居民地址及联系人相关信息</th>
	</tr>
	<tr>
		<td class="tab_width">居民地所属省：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="XZQHDM"
			dataInterface="dicTypeService.findProvince" onchange="changeCity(this.val)"
			defaultEmptyText="--请选择--" name="QYCB_JZDS" id="QYCB_JZDS">
			</eve:eveselect>
		</td>
		<td class="tab_width">居民地所属市：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="${busRecord.QYCB_JZDS}" onchange="changeDictinct(this)"
			dataInterface="dicTypeService.findProvince" id="QYCB_JZDSSS"
			defaultEmptyText="--请选择--" name="QYCB_JZDSSS">
			</eve:eveselect>
		</td>
		<td class="tab_width">居民地所属区（县）：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="${busRecord.QYCB_JZDSSS}"
			dataInterface="dicTypeService.findProvince" id="QYCB_JZDSSQ"
			defaultEmptyText="--请选择--" name="QYCB_JZDSSQ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">居民地所属街道（镇）：</td>
		<td>
			<eve:eveselect clazz="tab_text " dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_JZDSSJD">
			</eve:eveselect>
		</td>
		<td class="tab_width">居民地所属社区（村）：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams=""
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_JZDSSC">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>参保人联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[required]"  name="QYCB_CBRDH" /> 
		</td>	
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>居住地（联系）邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[required]"  name="QYCB_JZDYZBM" /> 
		</td>
		<td class="tab_width"><font class="tab_color">*</font>居民地（联系）地址：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]"  name="QYCB_JZDLXDZ" style="width:300px"/> 
		</td>	
	</tr>
	<tr>
		<td class="tab_width">联系人关系：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="bic621"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_LXRGX">
			</eve:eveselect>
		</td>
		<td class="tab_width">家庭联系人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="QYCB_LXRXM" /> 
		</td>
		<td class="tab_width">家庭联系人联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="QYCB_LXRLXDH" /> 
		</td>
	</tr>
	<tr>
		<td class="tab_width">家庭联系人固定电话：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="QYCB_LXRDDDH" /> 
		</td>
		<td class="tab_width">家庭联系人移动电话：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[]"  name="QYCB_LXRYDDH" /> 
		</td>
	</tr>	
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="cbxx">
	<tr>
		<th colspan="6">参保信息</th>
	</tr>
	<tr>
		<td class="tab_width">变更类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="aac050"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_BGLX">
			</eve:eveselect>				
		</td>
		<td class="tab_width"><font class="tab_color">*</font>参保日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="QYCB_CBRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>个人身份：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="aac012"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_GRSF">
			</eve:eveselect>				
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>用工性质：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="BB_AAC013_A20001"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYCB_YGXZ">
			</eve:eveselect>				
		</td>
		<td class="tab_width">视同缴费月数：</td>
		<td >
			<input type="text" class="tab_text validate[]"  name="QYCB_STJFYS" /> 
		</td>
		<td class="tab_width"><font class="tab_color">*</font>来单位时间：</td>
		<td >
			<input type="text" class="tab_text Wdate validate[required]" name="QYCB_LDWSJ"
				   onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan='5'>
			<input type="text" class="tab_text validate[]" style="width:400px" name="QYCB_BZ" /> 
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">人员参保信息</th>
	</tr>
</table>	
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:200px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="cbxxConfGrid" fitcolumns="true" checkonselect="true" 
			selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',url:'sbQyzjyController.do?personXzxxJson&ywId=${ywId}&exeId=${exeId}'"
			>
			<thead>
				<tr>
					<th data-options="field:'ck',align:'left',checkbox:true"></th>
					<th data-options="field:'aae140',width:'10%',align:'center',formatter:commonFormat">险种类型</th>
					<th data-options="field:'ajc050',width:'10%',align:'center'">单位登记参保日期</th>
				   	<th data-options="field:'baeai6',width:'10%',align:'center'">账户建立年月</th>
					<th data-options="field:'baeaf2',width:'10%',align:'center'">参保日期</th>
					<th data-options="field:'baeac7',width:'20%',align:'center'">首次参保地个人实行缴费时间</th>
					<th data-options="field:'baeac8',width:'8%',align:'center'">本人首次缴费时间</th>
				   	<th data-options="field:'aac066',width:'10%',align:'center',formatter:commonFormat">参保身份</th>
					<th data-options="field:'aac012',width:'10%',align:'center',formatter:commonFormat">个人身份</th>
					<th data-options="field:'bac118',width:'10%',align:'center'">建账前累计缴费月数</th>
					<th data-options="field:'baeaj1',width:'10%',align:'center'">建账前视同累计缴费月数</th>
				   	<th data-options="field:'bac170',width:'10%',align:'center'">85-建账钱月数</th>
					<th data-options="field:'aab033',width:'10%',align:'center',formatter:commonFormat">征收方式</th>
					<th data-options="field:'YWLSH',width:'10%',align:'center',hidden:'true'">业务流水号</th>
					<th data-options="field:'PUSH_FLAG',width:'10%',align:'center',hidden:'true'">是否推送了</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table>	

