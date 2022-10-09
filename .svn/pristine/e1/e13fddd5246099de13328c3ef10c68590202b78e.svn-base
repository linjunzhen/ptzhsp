<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

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
<div name="slxx">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">个人基本信息
		<input style="float: right;" type="button" class="eflowbutton"  id="staffQueryBtn"
				onclick="cbrQuery();" value="参保人员信息查询"/>
		</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_ZJHM" value="${busRecord.JBXX_ZJHM }"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="idCardType"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.JBXX_ZJLX}"
			defaultEmptyText="选择证件类型" name="JBXX_ZJLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="JBXX_NAME" value="${busRecord.JBXX_NAME }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ybSex"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.JBXX_SEX}"
			defaultEmptyText="选择性别" name="JBXX_SEX" >
			</eve:eveselect>
		</td>
		<td class="tab_width"> 民族：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="ybNation"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.JBXX_NATION}"
			defaultEmptyText="选择民族" name="JBXX_NATION">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="JBXX_CSRQ" value="${busRecord.JBXX_CSRQ}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 解除劳动关系时间：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_JCLDGXSJ" value="${busRecord.JBXX_JCLDGXSJ}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"> 异地参保开始时间：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_YDCBKSSJ" value="${busRecord.JBXX_YDCBKSSJ}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"> 参加工作日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="JBXX_CJGZRQ" value="${busRecord.JBXX_CJGZRQ}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 户口性质：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="populateNature" value="${busRecord.JBXX_HKXZ}"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="选择户口性质" name="JBXX_HKXZ" >
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>所属行政区划：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="survivalState"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.JBXX_SSXZQH}"
			defaultEmptyText="选择所属行政区划" name="JBXX_SSXZQH">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 待遇享受级别：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="enjoyLevel" value="${busRecord.JBXX_XSDYJB}"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="选择待遇享受级别" name="JBXX_XSDYJB">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 婚姻状况：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="maritalStatus"
			dataInterface="dictionaryService.findDatasForSelect"  value="${busRecord.JBXX_HYZK}"
			defaultEmptyText="选择婚姻状况" name="JBXX_HYZK">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 手机号码：</td>
		<td>
			<input type="text" class="tab_text validate[[],custom[mobilePhoneLoose]]" name="JBXX_SJHM" value="${busRecord.JBXX_SJHM}"/>
		</td>
		<td class="tab_width"> 联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" name="JBXX_LXDH" value="${busRecord.JBXX_LXDH}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[[],custom[chinaZip]]" name="JBXX_YZBM" value="${busRecord.JBXX_YZBM}"/>
		</td>
		<td class="tab_width"> 通讯地址：</td>
		<td colspan="3">
			<input type="text" class="tab_text" style="width:440px;" name="JBXX_TXDZ"  value="${busRecord.JBXX_TXDZ}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 就业状态：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="employmentStatus" value="${busRecord.JBXX_JYZT}"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择就业状态" name="JBXX_JYZT ">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 军转干部标志：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="cadreSign" value="${busRecord.JBXX_JZGBBZ}"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择军转干部标志" name="JBXX_JZGBBZ">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 人员生存状态：</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="survivalState" value="${busRecord.JBXX_RYSCZT}"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择人员生存状态" name="JBXX_RYSCZT">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 个人保险号：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_GRBXH " value="${busRecord.JBXX_GRBXH}"/>
		</td>
		<td class="tab_width"> 职务：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text" dataParams="post" value="${busRecord.JBXX_ZW}"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择职务" name="JBXX_ZW">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 备注：</td>
		<td colspan="5">
			<input type="text" class="tab_text" style="width:440px;" name="JBXX_REMARK" value="${busRecord.JBXX_REMARK}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 连续工龄：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_LXGL"  value="${busRecord.JBXX_LXGL}"/>
		</td>		
		<td class="tab_width"> 异地参保月数：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_YDCBYS" value="${busRecord.JBXX_YDCBYS}"/>
		</td>
		<td class="tab_width"> 待遇开始日期：</td>
		<td>
			<input type="text" class="tab_text" name="JBXX_DYKSRQ" value="${busRecord.JBXX_DYKSRQ}"/>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">缴费信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 银行行号：</td>
		<td>
			<input type="text" class="tab_text validate[custom[numberplus]]" name="JFXX_YHHH" value="${busRecord.JFXX_YHHH}"/>
		</td>
		<td class="tab_width"> 开户行名称：</td>
		<td>
			<input type="text" class="tab_text validate[custom[numberplus]]" name="JFXX_KHHMC" value="${busRecord.JFXX_KHHMC}"/>
		</td>
		<td class="tab_width"> 有效标志：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="effectiveSign" value="${busRecord.JFXX_YXBZ}"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择有效标志" name="JFXX_YXBZ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 缴费账号：</td>
		<td>
			<input type="text" class="tab_text" name="JFXX_JFZH" value="${busRecord.JFXX_JFZH}" />
		</td>
		<td class="tab_width"> 缴费账号名：</td>
		<td colspan="3">
			<input type="text" style="width:440px;" class="tab_text" name="JFXX_JFZHM" value="${busRecord.JFXX_JFZHM}"/>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">参保信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>参保身份：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="insuredIdentity"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.CBXX_CBSF}"
			defaultEmptyText="选择参保身份" name="CBXX_CBSF">
			</eve:eveselect>
		</td>		
		<td class="tab_width"><font class="tab_color">*</font>参保开始日期：</td>
		<td>
			<input type="text"  class="tab_text Wdate validate[required]" name="CBXX_CBKSRQ" value="${busRecord.CBXX_CBKSRQ}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" 
		</td>
		<td class="tab_width"><font class="tab_color">*</font>申报工资（元）：</td>
		<td>
			<input type="text" class="tab_text validate[required,custom[numberplus]]" name="CBXX_SBGZ" value="${busRecord.CBXX_SBGZ}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 上次参保开始日期：</td>
		<td>
			<input type="text" class="tab_text" name="CBXX_SCCBKSRQ" value="${busRecord.CBXX_SCCBKSRQ}"/>
		</td>
		<td class="tab_width"> 是否补缴：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="YesOrNo"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.CBXX_SFBJ}"
			defaultEmptyText="" name="CBXX_SFBJ">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 上次参保开始身份：</td>
		<td>
			<input type="text" class="tab_text" name="CBXX_SCCBSF" value="${busRecord.CBXX_SCCBSF}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 是否转移续接：</td>
		<td colspan="5">
			<eve:eveselect clazz="tab_text validate[]" dataParams="YesOrNo"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.CBXX_SFZYJX}"
			defaultEmptyText="" name="CBXX_SFZYJX">
			</eve:eveselect>
		</td>
	</tr>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th>险种信息</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" style="width:100%">
	<tr>
		<td style="height:200px;">
			<div id="xzxxToolBar" style="width: 100%;">
				<div class="right-con">
					<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					</div>
				</div>
			</div>
			
			<table class="easyui-datagrid" rownumbers="false" pagination="false"
			id="xzxxGrid" fitcolumns="true" toolbar="#xzxxToolBar"
			checkonselect="false" style="width:100%;height:auto;"
			selectoncheck="false" fit="true" border="false"
			data-options="method:'post',url:''"
			>
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'XZLX',align:'left'" width="83">险种类型</th>
					<th data-options="field:'STRAT_DATE',align:'left'" width="83">开始日期</th>
					<th data-options="field:'END_DATE',align:'left'" width="83">截止日期</th>
					<th data-options="field:'CBZT',align:'left'"  width="83">参保状态</th>
					<th data-options="field:'CBQK',align:'left'" width="83">参加情况</th>
					<th data-options="field:'YDCBYS',align:'left'" width="83">异地参保月数</th>
				</tr>
			</thead>
		</td>
	</tr>

</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">变更信息</th>
	</tr>
	<tr>
		<%-- <td class="tab_width"><font class="tab_color">*</font>变更日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="BGXX_BGRQ" value="${busRecord.BGXX_BGRQ }"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" />
		</td> --%>
		<td class="tab_width"><font class="tab_color">*</font>变更日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[required]" name="BGXX_BGRQ" value="${busRecord.BGXX_BGRQ }"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" />
		</td>
		
		<td class="tab_width"> 变更原因：</td>
		<td colspan="2">
			<input type="text" class="tab_text" style="width:750px;" name="BGXX_BGYY" value="${busRecord.BGXX_BGYY }" />
		</td>
	</tr>
</table>
</div>