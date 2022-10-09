<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<%--预购商品房预告登记 预购预告信息 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='ygygInfo'>
	<tr>
		<th>预购预告</th>
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">
				<tr>
	    		<td colspan='4' class="tab_width">权利人</td>
				</tr>
				<tr>
				    <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="MSFXM" maxlength="32" value="${busRecord.MSFXM}"/></td>
					<td class="tab_width"><font class="tab_color">*</font>证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="MSFZJLB" id="MSFZJLB"  value="${busRecord.MSFZJLB}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="MSFZJHM" style="float: left;"
						name="MSFZJHM" value="${busRecord.MSFZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="YgygQlrRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>
				    <td colspan='4' class="tab_width">代理人（领证人）</td>
				</tr>
				<tr>
				    <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="LZRXM" maxlength="32" value="${busRecord.LZRXM}"/></td>
					<td class="tab_width"><font class="tab_color">*</font>证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="LZRZJLB" id="LZRZJLB"  value="${busRecord.LZRZJLB}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="LZRZJHM" style="float: left;"
						name="LZRZJHM" value="${busRecord.LZRZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="YgygLzrRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>
				    <td colspan='4' class="tab_width">义务人</td>
				</tr>
				<tr>
				    <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td ><input type="text" class="tab_text  validate[required]" 
						name="ZRFXM" maxlength="32" value="${busRecord.ZRFXM}"/></td>
					<td class="tab_width"><font class="tab_color">*</font>证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="ZRFZJLB" id="ZRFZJLB"  value="${busRecord.ZRFZJLB}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="ZRFZJHM" style="float: left;"
						name="ZRFZJHM" value="${busRecord.ZRFZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="YgygYwrRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>
				    <td colspan='4' class="tab_width">代理人</td>
				</tr>
				<tr>
				    <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="DLRXM" maxlength="32" value="${busRecord.DLRXM}"/></td>
					<td class="tab_width"><font class="tab_color">*</font>证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择证件类型" name="DLRZJLB" id="DLRZJLB"  value="${busRecord.DLRZJLB}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]]" maxlength="128" id="DLRZJHM" style="float: left;"
						name="DLRZJHM" value="${busRecord.DLRZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="YgygDlrRead();" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>
					<td class="tab_width">预售合同号：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="FWMMHTH" value="${busRecord.FWMMHTH}" style="width: 72%;"  />
					</td>
					<td class="tab_width"><font class="tab_color">*</font>合同类型：</td>
					<td>	
						<eve:eveselect clazz="tab_text validate[required]" dataParams="htlx"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择合同类型" name="HTLX" id="HTLX"  value="${busRecord.HTLX}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">建筑面积：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="JZMJ" value="${busRecord.JZMJ}" style="width: 72%;"  />
					</td>
					<td class="tab_width"><font class="tab_color">*</font>规划用途：</td>
					<td>	
						<eve:eveselect clazz="tab_text validate[required]" dataParams="GHYT"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setYtms(this.value);"
						defaultEmptyText="请选择规划用途" name="YTSM" id="YTSM"  value="${busRecord.YTSM}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[required]" maxlength="128"
						name="FWDZ" value="${busRecord.FWDZ}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>用途说明：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[required]" maxlength="128"
						name="YTMS" value="${busRecord.YTMS}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>房屋性质：</td>
					<td>	
						<eve:eveselect clazz="tab_text validate[required]" dataParams="FWXZ"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择房屋性质" name="FWXZ" id="FWXZ"  value="${busRecord.FWXZ}">
						</eve:eveselect>
					</td>
					<td class="tab_width">总层数：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="ZCS" value="${busRecord.ZCS}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">所在层数：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="SZC" value="${busRecord.SZC}" style="width: 72%;"  />
					</td>
					<td class="tab_width"><font class="tab_color">*</font>取得价格（万元）：</td>
					<td>
						<input type="text" class="tab_text validate[required,custom[number]]" maxlength="32"
						name="CJJG" value="${busRecord.CJJG}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">项目名称：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="64"
						name="XMMC" value="${busRecord.XMMC}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">幢号：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="ZH" value="${busRecord.ZH}" style="width: 72%;"  />
					</td>
					<td class="tab_width">户号：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="HH" value="${busRecord.HH}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">土地使用权人：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="TDSYQR" value="${busRecord.TDSYQR}" style="width: 72%;"  />
					</td>
					<td class="tab_width">使用权面积：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="SYQMJ" value="${busRecord.SYQMJ}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">登记原因：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="DJYY" id="DJYY" value="${busRecord.DJYY}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">附记：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="FJ" value="${busRecord.FJ}" style="width: 72%;"  />
					</td>
				</tr>	
				
				<input type="hidden" name="YGYG_FILE_URL" >
				<input type="hidden" name="YGYG_FILE_ID">
				<tr>
					<td  class="tab_width">人像信息：
<!-- 					<font class="dddl_platform_html_requiredFlag">*</font> -->
					</td>
					<td colspan="3">
						<div style="width:100%;display: none;" id="YGYG_FILE_DIV"></div>
						<div id="${applyMater.MATER_CODE}__SCAN" style="float: left;">
							<a href="javascript:YGYGchooseCtrl()"><img id="${applyMater.MATER_CODE}"
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
						<div style="width:100%;" id=YGYG_fileListDiv></div>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
</table>