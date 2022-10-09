<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<%--预购商品房预告登记 预购预告信息 --%>
<div class="bsbox clearfix" id="ygygInfo">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>预购预告</span></li>
		</ul>
		<input type="button" class="eflowbutton" id="addQlrButton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="添加权利人" onclick="addQlrDiv()">
	</div>
	<div style="padding: 25px 30px 0">	
		<div id="qlrDiv">
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="bstable1">
					<tr>
						<th><span class="bscolor1">*</span>权利人姓名：</th>
						<td><input type="text" class="tab_text validate[required]" 
							name="MSFXM" maxlength="32" value="${busRecord.MSFXM}"/></td>
						<th><span class="bscolor1">*</span>权利人证件种类：</th>
						<td>
							<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'MSFZJHM');"
							defaultEmptyText="选择证件类型" name="MSFZJLB" id="MSFZJLB"  value="${busRecord.MSFZJLB}">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<th><span class="bscolor1">*</span>权利人证件号码：</th>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="128" id="MSFZJHM" style="float: left;"
							name="MSFZJHM" value="${busRecord.MSFZJHM}" />
						</td>
						<th><span class="bscolor1">*</span>权利人手机号码：</th>
						<td>
						  <input type="text" class="tab_text validate[required,custom[equalsQlrSjhm]],custom[mobilePhoneLoose]" maxlength="11"
							name="MSFSJHM" value="${busRecord.MSFSJHM}" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table cellpadding="0" cellspacing="0" class="bstable1">	
			<tr>
				<th>代理人姓名：</th>
				<td ><input type="text" class="tab_text validate[]" 
					name="LZRXM" maxlength="32" value="${busRecord.LZRXM}"/></td>
				<th>代理人证件种类：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[]" dataParams="DYZJZL"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LZRZJHM');"
					defaultEmptyText="选择证件类型" name="LZRZJLB" id="LZRZJLB"  value="${busRecord.LZRZJLB}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th>代理人证件号码：</th>
				<td>
				  <input type="text" class="tab_text validate[]" maxlength="128" name="LZRZJHM" value="${busRecord.LZRZJHM}" />
				</td>
				<th>代理人手机号码：</th>
				<td>
				  <input type="text" class="tab_text validate[],custom[mobilePhoneLoose]" maxlength="11" name="LZRSJHM" value="${busRecord.LZRSJHM}" />
				</td>
			</tr>
		</table>
	</div>
	<div style="padding: 0 30px">
		<table cellpadding="0" cellspacing="0" class="bstable1">	
			<tr>
				<th><span class="bscolor1">*</span>义务人姓名：</th>
				<td ><input type="text" class="tab_text  validate[required]" 
					name="ZRFXM" maxlength="64" value="${busRecord.ZRFXM}"/></td>
				<th><span class="bscolor1">*</span>义务人证件种类：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
					dataInterface="dictionaryService.findDatasForSelect"   onchange="setZjValidate(this.value,'ZRFZJHM');"
					defaultEmptyText="选择证件类型" name="ZRFZJLB" id="ZRFZJLB"  value="${busRecord.ZRFZJLB}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>义务人证件号码：</th>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="128"
					name="ZRFZJHM" value="${busRecord.ZRFZJHM}" />
				</td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>代理人姓名：</th>
				<td ><input type="text" class="tab_text validate[required]" 
					name="DLRXM" maxlength="32" value="${busRecord.DLRXM}"/></td>
				<th><span class="bscolor1">*</span>代理人证件种类：</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="DYZJZL"
					dataInterface="dictionaryService.findDatasForSelect"   onchange="setZjValidate(this.value,'DLRZJHM');"
					defaultEmptyText="选择证件类型" name="DLRZJLB" id="DLRZJLB"  value="${busRecord.DLRZJLB}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>代理人证件号码：</th>
				<td>
				  <input type="text" class="tab_text validate[required]" maxlength="128" name="DLRZJHM" value="${busRecord.DLRZJHM}" />
				</td>
				<th><span class="bscolor1">*</span>代理人手机号码：</th>
				<td>
				  <input type="text" class="tab_text validate[required],custom[mobilePhoneLoose]" maxlength="11" name="DLRSJHM" value="${busRecord.DLRSJHM}" />
				</td>
			</tr>
		</table>
	</div>
	<div style="padding: 0 30px">
		<table cellpadding="0" cellspacing="0" class="bstable1">	
			<tr>
				<th>预售合同号：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="FWMMHTH" value="${busRecord.FWMMHTH}"  />
				</td>
				<th><span class="bscolor1">*</span>合同类型：</th>
				<td>	
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="htlx"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择合同类型" name="HTLX" id="HTLX"  value="${busRecord.HTLX}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th>建筑面积：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="JZMJ" value="${busRecord.JZMJ}" />平方米
				</td>
				<th><span class="bscolor1">*</span>规划用途：</th>
				<td>	
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="GHYT"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setYtms(this.value);"
					defaultEmptyText="请选择规划用途" name="YTSM" id="YTSM"  value="${busRecord.YTSM}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>房屋地址：</th>
				<td colspan='3'>
					<input type="text" class="tab_text validate[required]" maxlength="128" style="width: 850px;"
					name="FWDZ" value="${busRecord.FWDZ}"  />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>用途说明：</th>
				<td colspan='3'>
					<input type="text" class="tab_text validate[required]" maxlength="128" style="width: 850px;"
					name="YTMS" value="${busRecord.YTMS}" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>房屋性质：</th>
				<td>	
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="FWXZ"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择房屋性质" name="FWXZ" id="FWXZ"  value="${busRecord.FWXZ}">
					</eve:eveselect>
				</td>
				<th>总层数：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="ZCS" value="${busRecord.ZCS}"  />
				</td>
			</tr>
			<tr>
				<th>所在层数：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="SZC" value="${busRecord.SZC}"  />
				</td>
				<th><span class="bscolor1">*</span>取得价格：</th>
				<td>
					<input type="text" class="tab_text validate[required,custom[number]]" maxlength="32"
					name="CJJG" value="${busRecord.CJJG}" />万元
				</td>
			</tr>
			<tr>
				<th>项目名称：</th>
				<td colspan='3'>
					<input type="text" class="tab_text validate[]" maxlength="64" style="width: 850px;"
					name="XMMC" value="${busRecord.XMMC}" />
				</td>
			</tr>
			<tr>
				<th>幢号：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="ZH" value="${busRecord.ZH}" />
				</td>
				<th>户号：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="HH" value="${busRecord.HH}"  />
				</td>
			</tr>
			<tr>
				<th>土地使用权人：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="TDSYQR" value="${busRecord.TDSYQR}" />
				</td>
				<th>使用权面积：</th>
				<td>
					<input type="text" class="tab_text validate[]" maxlength="32"
					name="SYQMJ" value="${busRecord.SYQMJ}" />平方米
				</td>
			</tr>
			<tr>
				<th>登记原因：</th>
				<td colspan='3'>
					<input type="text" class="tab_text validate[]" maxlength="128" style="width: 850px;"
					name="DJYY" id="DJYY" value="预购商品房预告登记"  />
				</td>
			</tr>
			<tr>
				<th>附记：</th>
				<td colspan='3'>
					<input type="text" class="tab_text validate[]" maxlength="128" style="width: 850px;"
					name="FJ" value="${busRecord.FJ}" />
				</td>
			</tr>	
			
		</table>
	</div>
</div>