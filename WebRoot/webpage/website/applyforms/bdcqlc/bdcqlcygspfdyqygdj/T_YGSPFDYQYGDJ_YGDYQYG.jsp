<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<div class="bsbox clearfix" id="ygdyqygInfo">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>预购抵押权预告</span></li>
		</ul>
	</div>
	<div style="padding: 25px 30px">
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th ><span class="bscolor1">*</span>权利人：</th>
				<td>
					<input type="hidden" name="QLR_LABEL"/> 
					<input class="easyui-combobox w280 tab_text validate[required]" name="QLR_MC" id="QLR_MC" style="height:28px;line-height:28px;" 
						data-options="
							prompt : '请输入或者选择权利人',
							url: 'dictionaryController/auto.do?typeCode=CYYHHMC',
							method: 'get',
							valueField : 'DIC_NAME',
							textField : 'DIC_NAME',
							required:true,
							filter : function(q, row) {
								var opts = $(this).combobox('options');
								return row[opts.textField].indexOf(q) > -1; 
							},
							onSelect:function(){
								var value = $('#QLR_MC').combobox('getValue');
								setQLRName(value);
							}									
				"/>
				</td>	
				<th ><span class="bscolor1">*</span>权利人证件类型：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_ZJNO');"
					defaultEmptyText="选择证件类型" name="QLR_ZJLX" id="QLR_ZJLX" value="统一社会信用代码"> 
					</eve:eveselect>
				</td>
				
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>权利人证件号码：</th>
				<td colspan='3'>
					<input type="text" class="tab_text validate[required]" maxlength="64" id="QLR_ZJNO"
						name="QLR_ZJNO" style="background-color: #f2f2f2;" readonly="readonly"/>
					<span style="color:red;">&nbsp;&nbsp;&nbsp;注：选择权利人后自动回填</span>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>代理人姓名：</th>
				<td><input type="text" class="tab_text validate[required]" name="DLR_MC"  maxlength="128"/></td>
				<th><span class="bscolor1">*</span>代理人证件类型：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DLR_ZJNO');"
					defaultEmptyText="选择证件类型" name="DLR_ZJLX" id="DLR_ZJLX" value="身份证">
					</eve:eveselect>
				</td>				
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>代理人证件号码：</th>
				<td>
					<input type="text" class="tab_text validate[required]" maxlength="64" id="DLR_ZJNO" name="DLR_ZJNO"  />
				</td>
				<th><span class="bscolor1">*</span>代理人手机号码：</th>
				<td>
					<input type="text" class="tab_text validate[required],custom[mobilePhoneLoose]" maxlength="11"
						name="DLR_SJHM" value="${busRecord.DLR_SJHM}" />
				</td>
			</tr>
		</table>
		<div id="ywrDiv">
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="bstable1" >
					<tr>
						<th ><span class="bscolor1">*</span>义务人姓名：</th>
						<td>
							<input type="text" class="tab_text validate[required]" 
								name="YWR_MC" maxlength="32" value="${busRecord.YWR_MC}"/>
						</td>
						<th ><span class="bscolor1">*</span>义务人证件种类：</th>
						<td>
							<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
							dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'YWR_ZJNO');"
							defaultEmptyText="选择证件类型" name="YWR_ZJLX" id="YWR_ZJLX"  value="身份证">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><span class="bscolor1">*</span>义务人证件号码：</th>
						<td>
							<input type="text" class="tab_text validate[required]" maxlength="64" id="YWR_ZJNO" name="YWR_ZJNO" value="${busRecord.YWR_ZJNO}" />
						</td>
						<th><span class="bscolor1">*</span>义务人手机号码：</th>
						<td>
							<input type="text" class="tab_text validate[required,custom[equalsYwrSjhm]],custom[mobilePhoneLoose]" maxlength="11"
								name="YWR_SJHM" value="${busRecord.YWR_SJHM}" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table cellpadding="0" cellspacing="0" class="bstable1" style=" margin-top: -1px;">
			<tr>
				<th >代理人姓名：</th>
				<td><input type="text" class="tab_text" name="DLR2_MC"  maxlength="128"/></td>
				<th >代理人证件类型：</th>
				<td>
					<eve:eveselect clazz="tab_text w280" dataParams="DYZJZL"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DLR2_ZJNO');"
					defaultEmptyText="选择证件类型" name="DLR2_ZJLX" id="DLR2_ZJLX" value="身份证">
					</eve:eveselect>
				</td>			
			</tr>
			<tr>
				<th>代理人证件号码：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="64" id="DLR2_ZJNO" name="DLR2_ZJNO"  />
				</td>
				<th>代理人手机号码：</th>
				<td>
					<input type="text" class="tab_text validate[],custom[mobilePhoneLoose]" maxlength="11"
						name="DLR2_SJHM" value="${busRecord.DLR2_SJHM}" />
				</td>
			</tr>
		</table>		
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th ><span class="bscolor1">*</span>预售合同号：</th>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="30" id="HT_NO" name="HT_NO"  />
				</td>
				<th ><span class="bscolor1">*</span>合同类型：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="htlx"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="选择合同类型" name="HT_LX" id="HT_LX" >
					</eve:eveselect>
				</td>				
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>被担保主债权数额：</th>
				<td>
					<input type="text" class="tab_text validate[required],custom[numberp6plus]" maxlength="30" id="DBSE"  name="DBSE" /><span>万元</span>
				</td>
				<th>土地使用权人：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="TDSYQR" value="${busRecord.TDSYQR}"  />
				</td>			
			</tr>
			<tr>
				<th>规划用途：</th>
				<td>	
					<eve:eveselect clazz="tab_text w280 validate[]" dataParams="GHYT"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setYtms(this.value);"
					defaultEmptyText="请选择规划用途" name="YTSM" id="YTSM"  value="${busRecord.YTSM}">
					</eve:eveselect>
				</td>
				<th>房屋性质：</th>
				<td>	
					<eve:eveselect clazz="tab_text w280 validate[]" dataParams="FWXZ"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择房屋性质" name="FWXZ" id="FWXZ"  value="${busRecord.FWXZ}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>债务起始时间：</th>
				<td>
					<input type="text" id="ZW_BEGIN" name="ZW_BEGIN" readonly 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'ZW_END\')}'})" 
						class="tab_text Wdate validate[required]" maxlength="60"/>
				</td>
				<th><span class="bscolor1">*</span>债务结束时间：</th>
				<td>
					<input type="text" id="ZW_END" name="ZW_END" readonly 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'ZW_BEGIN\')}'})" 
						class="tab_text Wdate validate[required]" maxlength="60" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>担保范围：</th>
				<td ><input type="text" class="tab_text validate[required]" maxlength="128"
					name="DBFW" value="${busRecord.DBFW}"/>
				</td>
					<th><span class="bscolor1">*</span>是否存在禁止或限制转让抵押不动产业务：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="YesOrNo"
					dataInterface="dictionaryService.findDatasForSelect" 
					defaultEmptyText="请选择" name="JZDY" id="JZDY"  value="${busRecord.JZDY}">
					</eve:eveselect>
				</td>
		    </tr>
			<tr>
				<th><span class="bscolor1">*</span>房屋地址：</th>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
					name="FW_ADDR" style="width:850px;"  />
				</td>
			</tr>
			<tr>
				<th>登记原因：</th>
				<td colspan="3"><input type="text" class="tab_text" maxlength="60"
					name="DJ_REASON" style="width: 850px;"  value="预购商品房抵押预告登记"/>
				</td>
			</tr>
			<tr>
				<th>附记：</th>
				<td colspan="3"><input type="text" class="tab_text" maxlength="60"
					name="FJ_INFO" style="width: 850px;"  />
				</td>
			</tr>
				
		</table>
	</div>
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>预售预告登记信息</span></li>
		</ul>
	</div>
	<div style="padding: 25px 30px">
		<table cellpadding="0" cellspacing="1" class="bstable1">
			<tr>
				<th ><span class="bscolor1">*</span>不动产单元号：</th>
				<td>
					<input type="text" class="tab_text validate[required]" maxlength="60" id="DJXX_DYH" name="DJXX_DYH"  />
				</td>
				<th ><span class="bscolor1">*</span>不动产登记证明号：</th>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="60" id="DJXX_CQZH" name="DJXX_CQZH"  />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>权利人：</th>
				<td>
					<input type="text" class="tab_text validate[required]" maxlength="30" id="DJXX_QLR"  name="DJXX_QLR"  />
				</td>
				<th><span class="bscolor1">*</span>建筑面积：</th>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="30" id="DJXX_JZAREA" name="DJXX_JZAREA"  />平方米
				</td>
			</tr>			
			<tr>
				<th><span class="bscolor1">*</span>坐落：</th>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60" name="DJXX_ZL" style="width: 850px;"  />
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3"><input type="text" class="tab_text" maxlength="60"
					name="DJXX_BZ"  style="width: 850px;"  />
				</td>
			</tr>
		</table>
	</div>
</div>