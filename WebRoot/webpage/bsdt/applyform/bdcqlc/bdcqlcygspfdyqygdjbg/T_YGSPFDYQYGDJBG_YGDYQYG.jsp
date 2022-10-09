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
		<td class="tab_width"><font class="tab_color">*</font>义务人：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" 
			name="YWR_MC"/></td>		
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
			<td>
				<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
				dataInterface="dictionaryService.findDatasForSelect" value="身份证" onchange="setZjValidate(this.value,'YWR_ZJNO');"
				defaultEmptyText="选择证件类型" name="YWR_ZJLX" id="YWR_ZJLX" >
				</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="128" id="YWR_ZJNO" style="float: left;"
			name="YWR_ZJNO"  /><a href="javascript:void(0);" onclick="YWR_Read(this);" class="eflowbutton">身份证读卡</a>
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
			name="DBSE"  />
		</td>
		<td class="tab_width"></td>
		<td></td>				
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
		<td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
			name="FW_ADDR" style="width: 72%;"  />
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
	
		<tr class="dbxxtr" style="display:none">
			<td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
			<td>
			  <input type="text" class="tab_text" maxlength="64" id="BDC_BDCDYH" style="width: 72%;float: left;"
				name="BDC_BDCDYH" value="${busRecord.BDC_BDCDYH}" />
			</td>
			<td>
				<a href="javascript:void(0);" id="zsyl" onclick="bdcYgspfdyqygdjbgDJZMprint(1);" class="eflowbutton">证书预览</a>&nbsp;
				<a href="javascript:void(0);" id="qrdb" onclick="bdcYgspfdyqygdjbgBooking();" class="eflowbutton">确认登簿</a>
				<a href="javascript:void(0);" id="zsdy" onclick="bdcYgspfdyqygdjbgDJZMprint(2);" class="eflowbutton">证书打印</a>&nbsp;
			</td>
			<td></td>
		</tr>
		<tr class="dbxxtr" style="display:none">
			<td class="tab_width">不动产登记证明号：</td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="64"
				name="BDC_BDCDJZMH" value="${busRecord.BDC_BDCDJZMH}" style="width: 72%;" 
				placeholder="调用内网不动产登记系统登簿接口，返回不动产登记证明号" />
			</td>
			<td class="tab_width">权证标识码：</td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="32"
				name="BDC_QZBSM" value="${busRecord.BDC_QZBSM}" style="width: 72%;" 
				placeholder="由内网不动产登记系统完成缮证，通过接口将权证标识码推送至审批平台"/>
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
			name="DJ_REASON" style="width: 72%;"  value="预售商品房抵押权预告登记"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">附记：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="FJ_INFO" style="width: 72%;"  />
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
		<td class="tab_width">不动产登记证明号：</td>
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
			name="DJXX_JZAREA"  />
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