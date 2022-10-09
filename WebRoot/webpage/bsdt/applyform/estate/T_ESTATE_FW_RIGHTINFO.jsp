<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- 涉及房屋所有权的业务 需要添加建筑面积字段 -->
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerInfo">
	<tr>
		<th>权利信息</th>
<!-- 		<td> <input type="button" class="eflowbutton"  name="addOnePowerInfo" value="新增" onclick="addPowerInfo();"> </td> -->
	</tr>
	<tr id="powerInfo_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width">权利开始时间：</td>
					<td>
						<input type="text" id="power_effect_time" name="power_effect_time" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'power_close_time\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="60" style="width:160px;" />
					</td>
					<td class="tab_width">权利结束时间1：</td>
					<td>
						<input type="text" id="power_close_time" name="power_close_time" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'power_effect_time\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="60" style="width:160px;" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">权利结束时间2：</td>
					<td>
						<input type="text" id="power_close_time2" name="power_close_time2" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'power_effect_time\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="60" style="width:160px;" />
					</td>
					<td class="tab_width"><font class="tab_color">*</font>共有方式：</td>
					<td style="width:430px">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="GYFS"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);changePower(this.value)"
						defaultEmptyText="选择共有方式" name="power_common_way" id="power_common_way" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">土地权利性质：</td>
					<td style="width:430px">
						<input class="easyui-combobox tab_text" name="power_right_type"  
								data-options="
									url: 'bdcApplyController/fluidComboBox.do',
									method: 'get',
									valueField:'value',
									textField:'text',
									groupField:'group',
									editable:false
								">
					</td>
					<td class="tab_width">土地使用权面积：</td>
					<td><input type="text" class="tab_text "
							   name="area" /></td>
				</tr>
				<tr>
					<td class="tab_width">建筑面积：</td>
					<td><input type="text" class="tab_text "
							   name="jzarea" /></td>
					<td class="tab_width"></td>
					<td></td>
				</tr>
				<tr>
					<td class="tab_width">用途说明：</td>
					<td colspan="3"><input type="text" class="tab_text" maxlength="60"
						name="ytsm"  style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">附记：</td>
					<td colspan="3"><input type="text" class="tab_text" maxlength="60"
						name="notes"  style="width: 72%;"  />
					</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
<!-- 		<td> -->
<!-- 			<input type="button" class="eflowbutton" name="deleteCurrentPowerInfo" value="删除" onclick="deletePowerInfo('1');"> -->
<!-- 		</td> -->
	</tr>
</table>
