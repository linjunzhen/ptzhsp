<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String ywId = request.getParameter("SB_YWID");
	request.setAttribute("ywId",ywId);
%>
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
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">	
	<tr>
		<th colspan="6">人员一般信息</th>
	</tr>
	<tr>
		<td class="tab_width">社会保障码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="SHBZHM" />
		</td>
		<td class="tab_width">证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="zjlx"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="ZJLX">
			</eve:eveselect>
		</td>
		<td class="tab_width">证件号码：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="ZJHM" />

		</td>
	</tr>
	<tr>
		<td class="tab_width">姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="XM" />
		</td>
		<td class="tab_width"> 曾用名：</td>
		<td>
			<input type="text" class="tab_text" name="CYM" />
		</td>
		<td class="tab_width">临时缴费账号标志：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="lsjfzhbz"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="ZHBZ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="sex"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="XB">
			</eve:eveselect>
		</td>
		<td class="tab_width">民族：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBMZ"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="MZ">
			</eve:eveselect>
		</td>
		<td class="tab_width">人员状态：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBRYZT"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="RYZT">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">身份证出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="CSRQ"
				   onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width">档案出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[]" name="DACSRQ"
				   onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width">参加工作时间：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[]" name="GZSJ"
				   onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">专业技术职务系列名称：</td>
		<td>
			<input type="text" class="tab_text validate[]" name="ZWMC" />
		</td>
		<td class="tab_width"> 专业技术职务等级：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBZYDJ"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="ZWDJ">
			</eve:eveselect>
		</td>
		<td class="tab_width">国家职业资格等级：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBZYZGDJ"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="ZYZGDJ">
			</eve:eveselect>
		</td>
	</tr>
	<tr>

		<td class="tab_width"> 行政职务：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBXZZW"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="XZZW">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 职工岗位：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="zggw"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="ZGGW">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 婚姻状况：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="hyzk"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="HYZK">
			</eve:eveselect>
		</td>

	</tr>

	<tr>

		<td class="tab_width">户口性质：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBHKXZ"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="HKXZ">
			</eve:eveselect>
		</td>
		<td class="tab_width">农民工标识：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBNMGBS"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="NMGBS">
			</eve:eveselect>
		</td>
		<td class="tab_width">学历（文化程度）：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBXL"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="XL">
			</eve:eveselect>
		</td>

	</tr>
	<tr>
		<td class="tab_width">国家/地区代码：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBGJ"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="--请选择--" name="GJDQDM">
			</eve:eveselect>
		</td>
		<td class="tab_width">户口所在地址：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[]" name="HKSZDZ" style="width:500px"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">参保人联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="CBRDH" />
		</td>
		<td class="tab_width">联系人姓名：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="LXRXM" />
		</td>
		<td class="tab_width">联系人联系电话：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="LXRLXDH" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">居住地（联系）邮政编码：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="JZDYZBM" />
		</td>
		<td class="tab_width">居民地（联系）地址：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[]"  name="JZDLXDZ" style="width:300px"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">死亡日期：</td>
		<td>
			<input type="text" class="tab_text Wdate validate[]" name="SWRQ"
				   onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width">备注：</td>
		<td colspan='3'>
			<input type="text" class="tab_text validate[]" style="width:400px" name="BZ" />
		</td>
	</tr>
</table>
