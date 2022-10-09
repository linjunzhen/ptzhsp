<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<style>
.tab_text_1 {
    width: 350px;
    height: 24px;
    line-height: 24px;
    /* padding: 0 5px; */
    padding: 2px 3px 2px 1px;
    border: 1px solid #bbb;
}
</style>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerInfo">
	<tr>
		<th>权利信息</th>
		<td style="width: 200px;">
		   <!--  <a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectFdchtbacx();">房地产合同备案信息查询</a> -->
		    <input type="button" class="eflowbutton"  name="searchFdchtba" value="房地产合同备案信息查询" onclick="showSelectFdchtbacx();">
			<input type="button" class="eflowbutton"  name="addOnePowerInfo" value="查询" onclick="showSelectBdcdaxxcx('1');">
			
		</td>
	</tr>
	<tr id="powerInfo_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width">权利开始时间：</td>
					<td>
						<input type="text" id="power_effect_time" name="power_effect_time" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'power_close_time\')}'})" 
							class="tab_text_1 Wdate" maxlength="60" style="width:160px;" />
					</td>
					<td class="tab_width">权利结束时间1：</td>
					<td>
						<input type="text" id="power_close_time" name="power_close_time" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'power_effect_time\')}'})" 
							class="tab_text_1 Wdate" maxlength="60" style="width:160px;" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">权利结束时间2：</td>
					<td>
						<input type="text" id="power_close_time2" name="power_close_time2" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'power_effect_time\')}'})" 
							class="tab_text_1 Wdate" maxlength="60" style="width:160px;" />
					</td>
					<td class="tab_width">权利结束时间3：</td>
					<td>
						<input type="text" id="power_close_time3" name="power_close_time3" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'power_effect_time\')}'})" 
							class="tab_text_1 Wdate" maxlength="60" style="width:160px;" />
					</td>					
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>共有方式：</td>
					<td style="width:430px">
						<eve:eveselect clazz="tab_text_1 validate[required]" dataParams="GYFS"
						dataInterface="dictionaryService.findDatasForSelect" onchange="changePower(this.value)"
						defaultEmptyText="选择共有方式" name="power_common_way" id="power_common_way" >
						</eve:eveselect>
					</td>
					<td class="tab_width">权利性质：</td>
					<td style="width:430px">
						 <input class="easyui-combobox tab_text_1" name="power_right_type" id="power_right_type"
								data-options="
								    prompt : '请选择权利性质',
									url: 'bdcApplyController/fluidComboBox.do',
									method: 'get',
									valueField:'value',
									textField:'text',
									groupField:'group',
									editable:false
								"> 
					</td>
				</tr>
				<tr>
					<td class="tab_width">使用权面积：</td>
					<td><input type="text" class="tab_text_1 "
							   name="area" /></td>
					<td class="tab_width">建筑面积：</td>
					<td><input type="text" class="tab_text_1 "
							   name="jzarea" /></td>
				</tr>				
				<tr>
					<td class="tab_width">土地用途说明：</td>
					<td style="width:430px">
						<!-- <input class="easyui-combobox tab_text_1" name="power_used_for" id="power_used_for"
								data-options="
									prompt : '请选择使用用途',
									url: 'bdcApplyController/fluidComboBox.do?typeCode=powerusedfor',
									method: 'get',
									valueField:'value',
									textField:'text',
									groupField:'group',
									editable:false
									
								"> -->
						<input type="text" class="tab_text_1 "
							   name="power_used_for" /></td>
						
					</td>
					<td class="tab_width">规划用途说明：</td>					
					<td><input type="text" class="tab_text_1 "
							   name="ghytsm" /></td>					
				</tr>
				<tr>
					<td class="tab_width">登记原因：</td>
					<td colspan="3"><input type="text" class="tab_text_1" maxlength="60"
						name="notes"  style="width: 72%;"  />
						<input type="button" class="eflowbutton" value="意见模板" onclick="AppUtil.cyyjmbSelector('gyzydjyy','notes');">
					</td>
				</tr>
			</table>
			<input type="hidden" name="BDCDYH" />
			<input type="hidden" name="ZDDM" />
			<input type="hidden" name="FWBM" />
			<input type="hidden" name="YWH" />
			<input type="hidden" name="ZXYWH" />
			<input type="hidden" name="GLH" />
			<div class="tab_height2"></div>
		</td>
		<td>
			<!-- <input type="button" class="eflowbutton" name="deleteCurrentPowerInfo" value="删除" onclick="deletePowerInfo('1');"> -->
		</td>
	</tr>
</table>
