<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<style>
.eflowbutton {
	background: #3a81d0;
	border: none;
	padding: 0 10px;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #fff;
	border-radius: 5px;
}

.eflowbutton-disabled {
	background: #94C4FF;
	border: none;
	padding: 0 10px;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #E9E9E9;
	border-radius: 5px;
}

.selectType {
	margin-left: -100px;
}

.bdcdydacxTrRed {
	color: red;
}
</style>
<script type="text/javascript">
	$(function() {});
</script>
<div class="tab_height"></div>
<!-- 宗地基本信息开始 -->
<div id="zdjbxx" name="zdqlxx_zdjbxx">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>宗地基本信息 </span>
			</th>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshxxInfo">
		<tr>
			<td class="tab_width">宗地代码：</td>
			<td>
				<input type="text" class="tab_text" maxlength="19" id="ZD_BM" style="float: left;" name="ZD_BM"
				 value="${busRecord.ZD_BM}" />
			</td>
			<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="ZDQLLX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择权利类型" name="ZD_QLLX" id="ZD_QLLX" value="${busRecord.ZD_QLLX}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<td class="tab_width">宗地特征码：</td>
			<td>
				<eve:eveselect clazz="tab_text " dataParams="zdtzm"
							   dataInterface="dictionaryService.findDatasForSelect"
							   defaultEmptyText="选择宗地特征码" name="ZD_TZM" id="ZD_TZM" value="${busRecord.ZD_TZM}">
				</eve:eveselect>
			</td>
			<td class="tab_width"><font class="tab_color">*</font>权力设定方式：</td>
			<td>
				<eve:eveselect clazz="tab_text  validate[required]" dataParams="ZDQLSDFS"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择权利设定方式" name="ZD_QLSDFS" id="ZD_QLSDFS" value="${busRecord.ZD_QLSDFS}">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font>坐落：</td>
			<td colspan="3">
				<input class="easyui-combobox" name="ZDZL_XIAN" id="ZDZL_XIAN" data-options="prompt:'请选择区县'">
				<input class="easyui-combobox" name="ZDZL_ZHENG" id="ZDZL_ZHENG" data-options="prompt:'请选择镇'">
				<input class="easyui-combobox" name="ZDZL_CUN" id="ZDZL_CUN" data-options="prompt:'请选择乡村'" style="width: 150px;">
				<br>
				<input type="text" class="tab_text" maxlength="60" name="ZD_ZL" value="${busRecord.ZD_ZL}"
					   style="width: 72%;" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">宗地面积：</td>
			<td>
				<input type="text" class="tab_text " maxlength="30" id="ZD_MJ" style="float: left;" name="ZD_MJ"
					value="${busRecord.ZD_MJ}" />
			</td>
			<td class="tab_width"><font class="tab_color">*</font>土地用途：</td>
			<td>
				<input id="ZD_TDYT" class="easyui-combobox" name="ZD_TDYT" style="width: 354px;height: 20px;"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">用途说明：</td>
			<td>
				<input type="text" class="tab_text " maxlength="30" id="ZD_YTSM" style="float: left;"
					name="ZD_YTSM" value="${busRecord.ZD_YTSM}" />
			</td>
			<td class="tab_width"><font class="tab_color">*</font>权力性质：</td>
			<td>
				<%-- <eve:eveselect clazz="tab_text " dataParams="DocumentType"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择权利性质" name="ZD_QLXZ" id="ZD_QLXZ" value="${busRecord.ZD_QLXZ}"> 
			                </eve:eveselect> --%>
				<input class="easyui-combobox tab_text" name="ZD_QLXZ" id="ZD_QLXZ"
					value="${busRecord.ZD_QLXZ}"
					data-options="
			                                    prompt : '请选择选择权利性质',
			                                    url: 'bdcApplyController/fluidComboBox.do',
			                                    method: 'get',
			                                    valueField:'value',
			                                    textField:'text',
			                                    groupField:'group',
												required:true,
			                                    editable:false
			                                ">
			</td>
		</tr>
		<tr>
			<td class="tab_width">等级：</td>
			<td>
				<eve:eveselect clazz="tab_text" dataParams="ZDLEVEL"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择等级" name="ZD_LEVEL" id="ZD_LEVEL" value="${busRecord.ZD_LEVEL}">
				</eve:eveselect>
			</td>
			<td class="tab_width"><font class="tab_color">*</font>容积率：</td>
			<td>
				<input type="text" class="tab_text  validate[required]" maxlength="30" id="ZD_RJL" style="float: left;"
					name="ZD_RJL" value="${busRecord.ZD_RJL}"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">建筑限高（米）：</td>
			<td>
				<input type="text" class="tab_text validate[custom[number]]" maxlength="30" id="ZD_JZXG" style="float: left;"
					name="ZD_JZXG" value="${busRecord.ZD_JZXG}"/>
			</td>
			<td class="tab_width">建筑密度：</td>
			<td>
				<input type="text" class="tab_text " maxlength="30" id="ZD_JZMD" style="float: left;"
					name="ZD_JZMD" value="${busRecord.ZD_JZMD}" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">宗地四至_东：</td>
			<td>
				<input type="text" class="tab_text " maxlength="30" id="ZD_E" style="float: left;" name="ZD_E"
					value="${busRecord.ZD_E}" />
			</td>
			<td class="tab_width">宗地四至_南：</td>
			<td>
				<input type="text" class="tab_text " maxlength="30" id="ZD_S" style="float: left;" name="ZD_S"
					value="${busRecord.ZD_S}" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">宗地四至_西：</td>
			<td>
				<input type="text" class="tab_text " maxlength="30" id="ZD_W" style="float: left;" name="ZD_W"
					value="${busRecord.ZD_W}" />
			</td>
			<td class="tab_width">宗地四至_北：</td>
			<td>
				<input type="text" class="tab_text " maxlength="30" id="ZD_N" style="float: left;" name="ZD_N"
					value="${busRecord.ZD_N}" />
			</td>
		</tr>
	</table>
</div>
<!-- 宗地基本信息结束 -->
<div class="tab_height"></div>

<!-- 国有土地使用权-权利信息开始 -->
<div id="gyqlxx" name="zdqlxx_gyqlxx">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>
				<span>集体建设用地使用权-权利信息 </span>
			</th>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshxxInfo">
		<tr>
			<td class="tab_width">权利开始时间：</td>
			<td>
				<input type="text" id="GYTD_BEGIN_TIME" name="GYTD_BEGIN_TIME"
					value="${busRecord.GYTD_BEGIN_TIME}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'GYTD_END_TIME1\')}'})"
					class="tab_text Wdate" maxlength="60" style="width:160px;" />
			</td>
			<td class="tab_width">权利结束时间1：</td>
			<td>
				<input type="text" id="GYTD_END_TIME1" name="GYTD_END_TIME1"
					value="${busRecord.GYTD_END_TIME1}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})"
					class="tab_text Wdate" maxlength="60" style="width:160px;" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">权利结束时间2：</td>
			<td>
				<input type="text" id="GYTD_END_TIME2" name="GYTD_END_TIME2"
					value="${busRecord.GYTD_END_TIME2}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})"
					class="tab_text Wdate" maxlength="60" style="width:160px;" />
			</td>
			<td class="tab_width">权利结束时间3：</td>
			<td>
				<input type="text" id="GYTD_END_TIME3" name="GYTD_END_TIME3"
					value="${busRecord.GYTD_END_TIME3}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})"
					class="tab_text Wdate" maxlength="60" style="width:160px;" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">共有方式：</td>
			<td>
				<eve:eveselect clazz="tab_text " dataParams="GYFS" value="${busRecord.GYTD_GYFS}"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择共有方式" name="GYTD_GYFS" id="GYTD_GYFS">
				</eve:eveselect>
			</td>
			<td class="tab_width">取得价格（万元）：</td>
			<td>
				<input type="text" class="tab_text validate[custom[number]]" value="${busRecord.GYTD_QDJG}" name="GYTD_QDJG"/>
			</td>
		</tr>
		<tr>
			<td class="tab_width">使用权面积：</td>
			<td>
				<input type="text" class="tab_text " value="${busRecord.GYTD_SYQMJ}" name="GYTD_SYQMJ"/>
			</td>
			<td class="tab_width">权属状态：</td>
			<td>
				<eve:eveselect clazz="tab_text" dataParams="DYQSZT"
					dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.GYTD_QSZT}"
					defaultEmptyText="选择权属状态" name="GYTD_QSZT" id="GYTD_QSZT">
				</eve:eveselect>
			</td>
		</tr>
		<tr  class="bdcdbc_tr" style="display:none;">
			<td class="tab_width">登簿人：</td>
			<td>
				<input type="text" class="tab_text " value="${busRecord.BDC_DBR}" name="BDC_DBR"/>
			</td>
			<td class="tab_width">登记时间：</td>
			<td>
				<input type="text" class="tab_text " value="${busRecord.GYTD_DJSJ}" name="GYTD_DJSJ"/>
			</td>
		</tr>
		<tr  class="bdcdbc_tr" style="display:none;">
			<td class="tab_width">登记原因：</td>
			<td colspan="3">
				<input type="text" class="tab_text" maxlength="60" name="GYTD_DJYY" style="width: 72%;" />
			</td>
		</tr>
		<tr>
			<td class="tab_width">附记：</td>
			<td colspan="3">
				<input type="text" class="tab_text" maxlength="60" name="GYTD_FJ" style="width: 72%;" />
			</td>
		</tr>
	</table>
</div>
<!-- 国有土地使用权-权利信息结束 -->
