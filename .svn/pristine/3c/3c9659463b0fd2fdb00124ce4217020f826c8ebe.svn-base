<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">预购抵押权预告</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>权利人：</td>
		<td colspan="3">
			<input type="hidden" name="QLR_LABEL"/> 
			<input class="easyui-combobox tab_text1 validate[required]" name="QLR_MC" id="QLR_MC" 
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
						var value = $('#QLR_MC').combobox('getValue');
						setQLRName(value);
					}									
		"/><span style="width:25px;display:inline-block;text-align:right;">
				<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','QLR_MC');" style="cursor:pointer;vertical-align:middle;" title="新建权利人">
		  </span>
		  <span style="width:25px;display:inline-block;text-align:right;">
				<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('QLR_MC');" style="cursor:pointer;vertical-align:middle;" title="删除选中的权利人">
		  </span>
		</td>				
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_ZJNO');"
				defaultEmptyText="选择证件类型" name="QLR_ZJLX" id="QLR_ZJLX" value="统一社会信用代码"> 
				</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="128" id="QLR_ZJNO" style="float: left;"
			name="QLR_ZJNO"  /><!-- <a href="javascript:void(0);" onclick="QLR_Read(this);" class="eflowbutton">身份证读卡</a> -->
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>代理人（领证人）：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="DLR_MC"  maxlength="128"/></td>		
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DLR_ZJNO');"
				defaultEmptyText="选择证件类型" name="DLR_ZJLX" id="DLR_ZJLX" value="身份证">
				</eve:eveselect>
		</td>		
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="128" id="DLR_ZJNO" style="float: left;"
			name="DLR_ZJNO"  /><a href="javascript:void(0);" onclick="DLR_Read(this);" class="eflowbutton">身份证读卡</a>
		</td>
		<td><font class="tab_color">*</font>手机号码：</td>
		<td>
		  <input type="text" class="tab_text validate[required],custom[mobilePhoneLoose]" maxlength="11"
			name="DLR_SJHM" value="${busRecord.DLR_SJHM}" />
		</td>
	</tr>
</table>
<input type="hidden" name="YWR_JSON" />	
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" style="margin-top: -1px;">
	<tr>
		<td colspan='2' class="tab_width">义务人
		<div id="addOrSaveButton" style="float: right;">
			<a href="javascript:void(0);" class="eflowbutton titleButton" onclick="addYwrInfo();" name="addYwrxx">新增 </a>
		</div>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2"  id="ywrInfo" style="margin-left: -8px; margin-top: -3px;">
	<tr id="ywrInfo_1">
		<td>
			<table class="tab_tk_pro2" style="margin-top: -3px;">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="YWR_MC" maxlength="32" value="${busRecord.YWR_MC}"/></td>
					<td class="tab_width"><font class="tab_color">*</font>证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="YWR_ZJLX" id="YWR_ZJLX"  value="身份证">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="YWR_ZJNO" style="float: left;"
						name="YWR_ZJNO" value="${busRecord.YWR_ZJNO}" />
						<a href="javascript:void(0);" onclick="cardRead(this,'YWR_MC','YWR_ZJNO');" class="eflowbutton">身份证读卡</a>
					</td>
					<td><font class="tab_color">*</font>手机号码：</td>
					<td>
						<input type="text" class="tab_text validate[required],custom[mobilePhoneLoose]" maxlength="11"
							name="YWR_SJHM" value="${busRecord.YWR_SJHM}" />
					</td>
				</tr>
			</table>
		</td>
		<td><input type="button" class="eflowbutton" name="deleteYwrInfoInput" value="删除" onclick="deleteYwrInfo('1');">
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" style="margin-top: -1px;">
	<tr>
		<td class="tab_width">代理人：</td>
		<td><input type="text" class="tab_text" name="DLR2_MC"  maxlength="128"/></td>
		<td class="tab_width">证件类型：</td>
		<td>
				<eve:eveselect clazz="tab_text" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DLR2_ZJNO');"
				defaultEmptyText="选择证件类型" name="DLR2_ZJLX" id="DLR2_ZJLX" value="身份证">
				</eve:eveselect>
		</td>			
	</tr>
	<tr>
		<td class="tab_width">证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[]" maxlength="128" id="DLR2_ZJNO" style="float: left;"
			name="DLR2_ZJNO"  /><a href="javascript:void(0);" onclick="DLR2_Read(this);" class="eflowbutton">身份证读卡</a>
		</td>
		<td>手机号码：</td>
		<td>
		  <input type="text" class="tab_text validate[],custom[mobilePhoneLoose]" maxlength="11"
			name="DLR2_SJHM" value="${busRecord.DLR2_SJHM}" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>预售合同号：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="30" id="HT_NO" style="float: left;"
			name="HT_NO"  />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>合同类型：</td>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="htlx"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="选择合同类型" name="HT_LX" id="HT_LX" >
				</eve:eveselect>
		</td>				
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>被担保主债权数额：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="30" id="DBSE" style="float: left;"
			name="DBSE"  />万元
		</td>
		<td class="tab_width">土地使用权人：</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="32"
			name="TDSYQR" value="${busRecord.TDSYQR}" style="width: 72%;"  />
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
		<td class="tab_width"><font class="tab_color">*</font>债务起始时间：</td>
		<td>
			<input type="text" id="ZW_BEGIN" name="ZW_BEGIN" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZW_END\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width: 160px"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>债务结束时间：</td>
		<td>
			<input type="text" id="ZW_END" name="ZW_END" 
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'ZW_BEGIN\')}'})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width: 160px"/>
		</td>
	</tr>
    <tr>
		<td class="tab_width"><font class="tab_color ">*</font>担保范围：</td>
		<td ><input type="text" class="tab_text validate[required]" maxlength="128"
			name="DBFW" value="${busRecord.DBFW}" style="width: 72%;"  />
		</td>
			<td class="tab_width"><font class="tab_color ">*</font>是否存在禁止或限制转让抵押不动产业务：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="请选择" name="JZDY" id="JZDY"  value="${busRecord.JZDY}">
			</eve:eveselect>
		</td>
    </tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
			name="FW_ADDR" style="width: 72%;"  />
		</td>
	</tr>
		<!--
		<tr class="dbxxtr" style="display:none">
			<td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
			<td>
			  <input type="text" class="tab_text" maxlength="64" id="BDC_BDCDYH" style="width: 72%;float: left;"
				name="BDC_BDCDYH" value="${busRecord.BDC_BDCDYH}" />
			</td>
			<td class="tab_width">权证标识码：</td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="32"
				name="BDC_QZBSM" value="${busRecord.BDC_QZBSM}" style="width: 72%;" 
				placeholder="由内网不动产登记系统完成缮证，通过接口将权证标识码推送至审批平台"/>
			</td>
		</tr>-->
		<tr class="dbxxtr" style="display:none">
			<input type="hidden" name="BDC_DBZT" value="${busRecord.BDC_DBZT}">
			<input type="hidden" name="BDC_DBJSON" value="${busRecord.BDC_DBJSON}">
			<td class="tab_width">不动产登记证明号：</td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="64"
				name="BDC_BDCDJZMH" value="${busRecord.BDC_BDCDJZMH}" style="width: 72%;" 
				placeholder="调用内网不动产登记系统登簿接口，返回不动产登记证明号" />
			</td>
			<td colspan="2">
				<a href="javascript:void(0);" id="ylzm" class="eflowbutton" onclick="bdcYgspfdyqygdjDJZMprint(1);">预览证明</a>&nbsp;
				<a href="javascript:void(0);" id="qrdb" class="eflowbutton" onclick="ygspfdyqygdjDbcz();">确认登簿</a>
				<a href="javascript:void(0);" id="dyzm" class="eflowbutton" onclick="bdcYgspfdyqygdjDJZMprint(2)">打印证明</a>
				<a href="javascript:void(0);" id="saveBtn" style="display:none" class="eflowbutton" onclick="saveYdxx();">保存</a>
			</td>
		</tr>
		<tr class="dbxxtr" style="display:none">
			<td class="tab_width">登簿时间：</td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="32"
				name="BDC_DJSJ" value="${busRecord.BDC_DJSJ}" style="width: 72%;"  />
			</td>
			<td class="tab_width">登簿人：</td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="32"
				name="BDC_DBR" value="${busRecord.BDC_DBR}" style="width: 72%;"  />
			</td>
		</tr>
	<tr>
		<td class="tab_width">登记原因：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="DJ_REASON" style="width: 72%;"  value="预购商品房抵押预告登记"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">附记：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="FJ_INFO" style="width: 72%;"  />
		</td>
	</tr>
		
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">预售预告登记信息</th>
	</tr>			
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
			<td>
				<input type="text" class="tab_text validate[required]" maxlength="60" id="DJXX_DYH" style="float: left;"
			name="DJXX_DYH"  />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>不动产登记证明号：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="60" id="DJXX_CQZH" style="float: left;"
			name="DJXX_CQZH"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>权利人：</td>
			<td>
				<input type="text" class="tab_text validate[required]" maxlength="30" id="DJXX_QLR" style="float: left;"
			name="DJXX_QLR"  />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>建筑面积：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="30" id="DJXX_JZAREA" style="float: left;"
			name="DJXX_JZAREA"  />平方米
		</td>
	</tr>			
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>坐落：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
			name="DJXX_ZL" style="width: 72%;"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="DJXX_BZ"  style="width: 72%;"  />
		</td>
	</tr>
</table>