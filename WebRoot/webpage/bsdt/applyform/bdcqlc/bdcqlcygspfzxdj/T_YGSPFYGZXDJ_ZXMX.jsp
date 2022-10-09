<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<div class="tab_height"></div>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">注销明细</th>
	</tr>			
	<tr>
		<td class="tab_width">预告登记种类：</td>
			<td colspan="3">
				<input type="text" class="tab_text validate[]" maxlength="60" id="YGDJZL" style="width: 72%;"
			name="YGDJZL"  />
		</td>
	</tr>	
	<tr>
		<td class="tab_width">不动产单元号：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="BDCDYH"  style="width: 72%;"  />
			<input type="button" class="eflowbutton" onclick="bdcYgspfygzxdjBooking();" id="ygspfygzxdjDb" value="确认登簿" style="display: none;">
		</td>
	</tr>	
	<tr>
		<td class="tab_width">不动产登记证明号：</td>
		<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
			name="BDCDJZMH" style="width: 72%;"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">债务起始时间：</td>
		<td>
			<input type="text" id="QSSJ" name="QSSJ" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'JSSJ\')}'})" 
				class="tab_text Wdate validate[]" maxlength="60"  style="width: 160px"/>
		</td>
		<td class="tab_width">债务结束时间：</td>
		<td>
			<input type="text" id="JSSJ" name="JSSJ" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'QSSJ\')}'})" 
				class="tab_text Wdate validate[]" maxlength="60"  style="width: 160px"/>
		</td>
	</tr>	
	<tr>
		<td class="tab_width">权利人：</td>
		<td colspan="3">
			<input type="hidden" name="QLR_LABEL"/> 
			<input class="easyui-combobox tab_text1 validate[]" name="QLR" id="QLR" 
				data-options="
					prompt : '请输入或者选择权利人',
					url: 'dictionaryController/auto.do?typeCode=CYYHHMC',
					method: 'get',
					valueField : 'DIC_NAME',
					textField : 'DIC_NAME',
					filter : function(q, row) {
						var opts = $(this).combobox('options');
						return row[opts.textField].indexOf(q) > -1; 
					},
					onSelect:function(){
						var value = $('#QLR').combobox('getValue');
						setQLRName(value);
					}									
		"/><span style="width:25px;display:inline-block;text-align:right;">
				<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','QLR');" style="cursor:pointer;vertical-align:middle;" title="新建权利人">
		  </span>
		  <span style="width:25px;display:inline-block;text-align:right;">
				<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('QLR');" style="cursor:pointer;vertical-align:middle;" title="删除选中的权利人">
		  </span>
		</td>				
	</tr>
	<tr>
		<td class="tab_width">证件类型：</td>
			<td>
				<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLRZJH');"
				defaultEmptyText="选择证件类型" name="QLRZJZL" id="QLRZJZL" value="统一社会信用代码"> 
				</eve:eveselect>
		</td>
		<td class="tab_width">证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[]" maxlength="128" id="QLRZJH" style="float: left;"
			name="QLRZJH"  /><!-- <a href="javascript:void(0);" onclick="QLR_Read(this);" class="eflowbutton">身份证读卡</a> -->
		</td>
	</tr>
	<tr>
		<td class="tab_width">义务人：</td>
		<td colspan="3"><input type="text" class="tab_text validate[]" 
			name="YWR"/></td>		
	</tr>
	<tr>
		<td class="tab_width">证件类型：</td>
			<td>
				<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect" value="身份证" onchange="setZjValidate(this.value,'YWRZJH');"
				defaultEmptyText="选择证件类型" name="YWRZJZL" id="YWRZJZL" >
				</eve:eveselect>
		</td>
		<td class="tab_width">证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[]" maxlength="128" id="YWRZJH" style="float: left;"
			name="YWRZJH"  /><a href="javascript:void(0);" onclick="IDCRAD_Read(this,'YWR','YWRZJH');" class="eflowbutton">身份证读卡</a>
		</td>
	</tr>
	<tr>
		<td class="tab_width">预售合同号：</td>
		<td>
		  <input type="text" class="tab_text validate[]" maxlength="30" id="HTH" style="float: left;"
			name="HTH"  />
		</td>
		<td class="tab_width">建筑面积：</td>
			<td>
		  <input type="text" class="tab_text validate[]" maxlength="30" id="JZMJ" style="float: left;"
			name="JZMJ"  />
		</td>				
	</tr>
	<tr>
		<td class="tab_width">规划用途：</td>
		<td>	
			<eve:eveselect clazz="tab_text validate[]" dataParams="GHYT"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setYtms(this.value);"
			defaultEmptyText="请选择规划用途" name="YTSM" id="YTSM"  value="${busRecord.YTSM}">
			</eve:eveselect>
		</td>
		<td class="tab_width">房屋性质：</td>
		<td>	
			<eve:eveselect clazz="tab_text validate[]" dataParams="FWXZ"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择房屋性质" name="FWXZ" id="FWXZ"  value="${busRecord.FWXZ}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">总层数：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="32"
			name="ZCS" value="${busRecord.ZCS}" style="width: 72%;"  />
		</td>
		<td class="tab_width">所在层数：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="32"
			name="SZC" value="${busRecord.SZC}" style="width: 72%;"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">取得价格/被担保主债权数额：</td>
		<td>
		  <input type="text" class="tab_text validate[]" maxlength="30" id="QDJG" style="float: left;"
			name="QDJG"  />
		</td>
		<td class="tab_width">使用权面积：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="32"
			name="SYQMJ" value="${busRecord.SYQMJ}" style="width: 72%;"  />
		</td>			
	</tr>
	<tr>
		<td class="tab_width">土地使用权人：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[]" maxlength="32"
			name="TDSYQR" value="${busRecord.TDSYQR}" style="width: 72%;"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">附记：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="FJ" style="width: 72%;"  />
		</td>
	</tr>
	
	<input type="hidden" name="YSDYQYG_FILE_URL" >
	<input type="hidden" name="YSDYQYG_FILE_ID">
	<tr>
		<td  class="tab_width">人像信息：
		</td>
		<td colspan="3">
			<div style="width:100%;display: none;" id="YSDYQYG_FILE_DIV"></div>
			<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
				<a href="javascript:YSDYQYGchooseCtrl()"><img id="${applyMater.MATER_CODE}"
				src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
			</div>
			<a href="javascript:void(0);" onclick="openSlxxFileUploadDialog()">
				<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
			</a>
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="3">
			<div style="width:100%;" id=YSDYQYG_fileListDiv></div>
		</td>
	</tr>
	
</table>