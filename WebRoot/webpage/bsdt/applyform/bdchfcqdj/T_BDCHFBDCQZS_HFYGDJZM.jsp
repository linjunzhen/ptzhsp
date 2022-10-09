<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    BdcApplyService bdcApplyService = (BdcApplyService)AppUtil.getBean("bdcApplyService");
	String ywId = request.getParameter("BDC_SUB_YWID");
	String hftype = request.getParameter("hftype");
	if(ywId != null && hftype != null){
		Map<String,Object> record = bdcApplyService.getSubRecordInfo(hftype, ywId);
		request.setAttribute("recordinfo", record);
	}
%>
<style>
.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
</style>
<%--不动产预告档案信息接口需回传字段 --%>
<input type="hidden" name="YWH" value="${recordinfo.YWH}"/>
<input type="hidden" name="GLH" value="${recordinfo.GLH}"/>

<%--不动产档案信息接口需回传字段 --%>
<%-- <input type="hidden" name="ZDDM" value="${recordinfo.ZDDM}"/>
<input type="hidden" name="FWBM" value="${recordinfo.FWBM}"/>
<input type="hidden" name="DA_YWH" value="${recordinfo.DA_YWH}"/>
<input type="hidden" name="ZXYWH" value="${recordinfo.ZXYWH}"/>
<input type="hidden" name="DA_GLH" value="${recordinfo.DA_GLH}"/> --%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="qsly3">
	<tr>
		<th>权属来源</th>
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width">不动产单元号：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="BDCDYH" value="${recordinfo.BDCDYH}" style="width: 72%;" />
					</td>
					<td class="tab_width">原不动产证明号：</td>
					<td >	
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="BDCDJZMH" value="${recordinfo.BDCDJZMH}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">权利人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QLR" maxlength="64" value="${recordinfo.QLR}" style="width: 72%;" />
					</td>				
					<td class="tab_width">权属状态：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QSZT" maxlength="32" value="${recordinfo.QSZT} " style="width: 72%;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">证件类型：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QLRZJZL" maxlength="16" value="${recordinfo.QLRZJZL}" style="width: 72%;"/>
					</td>				
					<td class="tab_width">证件号码：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QLRZJH" maxlength="32" value="${recordinfo.QLRZJH}" style="width: 72%;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">义务人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="YWR" maxlength="32" value="${recordinfo.YWR}" style="width: 72%;" />
					</td>				
					<td class="tab_width">义务人证件号：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="YWRZJH" maxlength="32" value="${recordinfo.YWRZJH} " style="width: 72%;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">抵押合同号：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QSLY_DYHTH" maxlength="32" value="${recordinfo.QSLY_DYHTH}" style="width: 72%;"/>
					</td>				
					<td class="tab_width">被担保主债权数额：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QDJG" maxlength="16" value="${recordinfo.QDJG}" style="width: 72%;" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">债务起始时间：</td>
					<td>
						<input type="text" id="QSSJ" name="QSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'JSSJ\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value='${recordinfo.QSSJ}' />
					</td>
					<td class="tab_width">债务结束时间：</td>
					<td>
						<input type="text" id="JSSJ" name="JSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'QSSJ\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value='${recordinfo.JSSJ}' />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='jbxx'>
	<tr>
		<th>基本信息</th>
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">	
				<tr>
	   		   		<td colspan='4' class="tab_width">权利人</td>
				</tr>
				<tr>
				    <td class="tab_width">姓名：</td>
					<td ><input type="text" class="tab_text" 
						name="JBXX_QLR" maxlength="32" value="${recordinfo.JBXX_QLR}" /></td>
					<td class="tab_width">证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'JBXX_ZJHM');"
						defaultEmptyText="选择证件类型" name="JBXX_ZJZL" id="JBXX_ZJZL"  value="${recordinfo.JBXX_ZJZL}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="32" id="JBXX_ZJHM" style="float: left;"
						name="JBXX_ZJHM" value="${recordinfo.JBXX_ZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="JbxxQlrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<%-- <tr>
				    <td colspan='4' class="tab_width">代理人（领证人）</td>
				</tr>
					<tr>
				    <td class="tab_width">姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="JBXX_LZR" maxlength="32" value="${recordinfo.JBXX_LZR}"/></td>
					<td class="tab_width">证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'JBXX_LZRZJHM');"
						defaultEmptyText="选择证件类型" name="JBXX_LZRZJLB" id="JBXX_LZRZJLB"  value="${recordinfo.JBXX_LZRZJLB}">
						</eve:eveselect>					
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="32" id="JBXX_LZRZJHM" style="float: left;"
						name="JBXX_LZRZJHM" value="${recordinfo.JBXX_LZRZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="JbxxLzrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr> --%>
				<tr>
				    <td colspan='4' class="tab_width">义务人</td>
				</tr>
				<tr>
				    <td class="tab_width">姓名：</td>
					<td ><input type="text" class="tab_text" 
						name="JBXX_YWR" maxlength="32" value="${recordinfo.JBXX_YWR}" /></td>
					<td class="tab_width">证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'JBXX_YWRZJHM');"
						defaultEmptyText="选择证件类型" name="JBXX_YWRZJLB" id="JBXX_YWRZJLB"  value="${recordinfo.JBXX_YWRZJLB}" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="32" id="JBXX_YWRZJHM" style="float: left;"
						name="JBXX_YWRZJHM" value="${recordinfo.JBXX_YWRZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="JbxxYwrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr>
				<%-- <tr>
				    <td colspan='4' class="tab_width">代理人</td>
				</tr>
				<tr>
				    <td class="tab_width">姓名：</td>
					<td ><input type="text" class="tab_text" 
						name="JBXX_DLR" maxlength="32" value="${recordinfo.JBXX_DLR}"/></td>
					<td class="tab_width">证件种类：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'JBXX_DLRZJHM');"
						defaultEmptyText="选择证件类型" name="JBXX_DLRZJLB" id="JBXX_DLRZJLB"  value="${recordinfo.JBXX_DLRZJLB}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="32" id="JBXX_DLRZJHM" style="float: left;"
						name="JBXX_DLRZJHM" value="${recordinfo.JBXX_DLRZJHM}" />
					</td>
					<td><a href="javascript:void(0);" onclick="JbxxDlrRead();" class="eflowbutton">身份证读卡</a></td>
					<td></td>
				</tr> --%>
				<tr>
					<td class="tab_width">预售合同号：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="JBXX_YSHTH" value="${recordinfo.JBXX_YSHTH}" style="width: 72%;" readonly="readonly" />
					</td>
					<td class="tab_width"><font class="tab_color">*</font>合同类型：</td>
					<td>	
						<eve:eveselect clazz="tab_text validate[required]" dataParams="htlx"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择合同类型" name="HTLX" id="HTLX"  value="${recordinfo.HTLX}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">建筑面积：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="JZMJ" value="${recordinfo.JZMJ}" style="width: 72%;" readonly="readonly" />
					</td>
					<td class="tab_width">规划用途：</td>
					<td>	
						<eve:eveselect clazz="tab_text validate[]" dataParams="GHYT"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择规划用途" name="FW_GHYT" id="FW_GHYT"  value="${recordinfo.FW_GHYT}" >
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[required]" maxlength="128"
						name="FDZL" value="${recordinfo.FDZL}" style="width: 72%;"  readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">用途说明：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="YTSM" value="${recordinfo.YTSM}" style="width: 72%;" readonly='readonly' />
					</td>
				</tr>
				<tr>
					<td class="tab_width">房屋性质：</td>
					<td>	
						<eve:eveselect clazz="tab_text validate[]" dataParams="FWXZ"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择房屋性质" name="FWXZ" id="FWXZ"  value="${recordinfo.FWXZ}" >
						</eve:eveselect>
					</td>
					<td class="tab_width">总层数：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="ZCS" value="${recordinfo.ZCS}" style="width: 72%;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">所在层数：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="SZC" value="${recordinfo.SZC}" style="width: 72%;" readonly="readonly" />
					</td>
					<td class="tab_width">取得价格（万元）：</td>
					<td>
						<input type="text" class="tab_text validate[custom[number]]" maxlength="32"
						name="JBXX_QDJG" value="${recordinfo.JBXX_QDJG}" style="width: 72%;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">项目名称：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="64"
						name="JBXX_XMMC" value="${recordinfo.JBXX_XMMC}" style="width: 72%;"  readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">幢号：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="ZH" value="${recordinfo.ZH}" style="width: 72%;" readonly="readonly" />
					</td>
					<td class="tab_width">户号：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="HH" value="${recordinfo.HH}" style="width: 72%;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">土地使用权人：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="JBXX_TDSYQR" value="${recordinfo.JBXX_TDSYQR}" style="width: 72%;" readonly="readonly" />
					</td>
					<td class="tab_width">使用权面积：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="32"
						name="SYQMJ" value="${recordinfo.SYQMJ}" style="width: 72%;"  readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">登记原因：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="JBXX_DJYY" id="JBXX_DJYY" value="${recordinfo.JBXX_DJYY}" style="width: 72%;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">附记：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128"
						name="FJ" value="${recordinfo.FJ}" style="width: 72%;"  readonly="readonly"/>
					</td>
				</tr>				
			</table>
		</td>
	</tr>
</table>
