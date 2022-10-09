<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.core.util.DateTimeUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<tbody>
	<tr style="background: #f2f2f2">
		<td style="text-align: center;"><font class="syj-color2">*</font>股权来源：</td>
		<td colspan="3">
			<select name="stockFrom" class="input-dropdown w280" onchange="setStockFrom(this);setNewInvestment(this);">
				
			</select>
			<a href="javascript:void(0);" onclick="javascript:delGqlyDiv(this);" class="deletebtn" id="delGqly"></a>
		</td>
	</tr>
	<tr>
		<th> 出资方式</th>
		<td colspan="3" style="text-align: center;">折合人民币(万元)</td>
	</tr>
	<tr>
		<th> 现金</th>
		<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
			name="INVESTMENT_CASH"  placeholder="请输入现金(万元)"  maxlength="14" 
			onblur="onlyNumber(this);setNewInvestment(this);isEqualToCash(this.value);" onkeyup="onlyNumber(this);" style="width: 638px;"/>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
				dataInterface="extraDictionaryService.findDatasForSelect"
				defaultEmptyText="请选择币种" name="CURRENCY_2" value="002" onchange="setCurrency2(this.value)">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<th> 实物</th>
		<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
			name="INVESTMENT_MATERIAL"  placeholder="请输入实物(万元)" maxlength="14"
			onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
				dataInterface="extraDictionaryService.findDatasForSelect"
				defaultEmptyText="请选择币种" name="CURRENCY_2" value="人民币" onchange="setCurrency2(this.value)">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<th> 技术出资</th>
		<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
			name="INVESTMENT_TECHNOLOGY"  placeholder="请输入技术出资(万元)" maxlength="14"
			onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
				dataInterface="extraDictionaryService.findDatasForSelect"
				defaultEmptyText="请选择币种" name="CURRENCY_2" value="人民币" onchange="setCurrency2(this.value)">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<th> 土地使用权</th>
		<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
			name="INVESTMENT_LAND" placeholder="请输入土地使用权(万元)" maxlength="14"
			onblur="onlyNumber(this);setNewInvestment(this);isEqualToNoCash(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
				dataInterface="extraDictionaryService.findDatasForSelect"
				defaultEmptyText="请选择币种" name="CURRENCY_2" value="人民币" onchange="setCurrency2(this.value)">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<th> 股权</th>
		<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
			name="INVESTMENT_STOCK"  placeholder="请输入股权(万元)" maxlength="14"
			onblur="onlyNumber(this);setNewInvestment(this);isEqualToStock(this.value)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
				dataInterface="extraDictionaryService.findDatasForSelect"
				defaultEmptyText="请选择币种" name="CURRENCY_2" value="人民币" onchange="setCurrency2(this.value)">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<th> 其他</th>
		<td colspan="3" ><input type="text" class="syj-input1 validate[],custom[JustNumber]"
			name="INVESTMENT_OTHER"  placeholder="请输入其他(万元)"  maxlength="14"
			onblur="onlyNumber(this);setNewInvestment(this)" onkeyup="onlyNumber(this);" style="width: 638px;"/>
			<eve:eveselect clazz="input-dropdown validate[required]" dataParams="currency"
				dataInterface="extraDictionaryService.findDatasForSelect"
				defaultEmptyText="请选择币种" name="CURRENCY_2" value="人民币" onchange="setCurrency2(this.value)">
			</eve:eveselect>
		</td>
	</tr>
	<tr id="transfer1" style="display:none;">
		<th>总转让认缴出资额（万元）：</th>
		<td><input type="text" class="syj-input1 inputBackgroundCcc"
			name="TRANSFER_CONTRIBUTIONS"  placeholder="系统会根据用户填写的信息自动计算" readonly="readonly" /></td>
		<th>占原注册资本比例：</th>
		<td><input type="text" class="syj-input1 inputBackgroundCcc"
			name="OLD_PROPORTION"  placeholder="系统会根据用户填写的信息自动计算" readonly="readonly" /></td>
	</tr>
	<tr id="transfer2" style="display:none;">
		<th><font class="syj-color2">*</font>转让价格：</th>
		<td><input type="text" class="syj-input1 "
			name="TRANSFER_PRICE"  placeholder="请输入转让价格（万元）" onblur="setPayWay(this)" />
			<fda:fda-eveselect clazz="selectwidth w280 input-dropdown " dataparams="currency"
				datainterface="extraDictionaryService.findDatasForSelect"
				defaultemptytext="请选择币种" name="CURRENCY_2" value="人民币" onchange="setCurrency2(this.value)">
			</fda:fda-eveselect>
		</td>
		<th><font class="syj-color2" id="fkfs" style="" >*</font>付款方式：</th>
		<td>
			<select name="PAYMETHOD" class="input-dropdown  w280">
				<option value="">请选择</option>
				<option value="现金">现金</option>
				<option value="转账">转账</option>
			</select>
		</td>
	</tr>
</tbody>

