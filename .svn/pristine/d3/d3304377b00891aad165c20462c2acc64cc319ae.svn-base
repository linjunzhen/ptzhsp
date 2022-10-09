<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcQlcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    BdcQlcApplyService bdcQlcApplyService = (BdcQlcApplyService)AppUtil.getBean("bdcQlcApplyService");
    String ywId = request.getParameter("BDC_SUB_YWID");
    String hftype = request.getParameter("hftype");
    String itemcode = request.getParameter("ITEM_CODE");
    if(ywId != null && hftype != null){
        Map<String,Object> record = bdcQlcApplyService.getSubRecordInfo(itemcode,hftype,ywId);
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
		<th><span style="float:left;margin-left:10px">权属来源</span></th>
	</tr>
	<tr>
		<td style="padding: 10px;">
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
		<th><span style="float:left;margin-left:10px">基本信息</span></th>
	</tr>
	<tr>
		<td style="padding: 10px;">
			<table class="tab_tk_pro2">	
				<tr>
				    <td class="tab_width">预告登记种类：</td>
				    <td colspan="3"><input type="text" class="tab_text" style="width:65%"
                        name="BDC_DJLX" maxlength="32" value="预购商品房预告登记" readonly="readonly"/></td>
				</tr>
				<tr>
	   		   		<th colspan='4' class="tab_width"><span style="float:left;margin-left:10px">权利人</span></th>
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
				    <th colspan='4' class="tab_width"><span style="float:left;margin-left:10px">义务人</span></th>
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
                       <td class="tab_width">不动产单元号：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" 
                           name="BDC_BDCDYH" value="${recordinfo.BDC_BDCDYH}" style="width:60%" />
                       </td>                        
                   </tr>
				<tr  name="bdcqzh_tr" style="display:none;">                    
                       <td class="tab_width">不动产登记证明号：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" 
                           name="BDC_BDCDJZMH" value="${recordinfo.BDC_BDCDJZMH}" style="width:60%" placeholder="【确认登簿】后调用不动产登记系统登簿接口，返回不动产登记证明号。"/>
                           <input type="button" class="eflowbutton disabledButton" id="zsyl3" onclick="bdcHfcqdjDJZMprint3(1);" style="background:red;color:#fff;" value="证书预览" />
                           <input type="button" onclick="page3Dbcz()" id="qrdb3" class="eflowbutton disabledButton" style="background:red;color:#fff;" value="确认登簿" />
						   <input type="button" class="eflowbutton disabledButton" id="zsdy3" onclick="bdcHfcqdjDJZMprint3(2);" style="background:red;color:#fff;" value="证书打印" />
                       </td>                        
                   </tr>
                   <tr>
                       <td class="tab_width">权证标识码：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]"
                           name="BDC_QZBSM" id="BDC_QZBSM3" style="width:60%" placeholder="由内网不动产登记系统完成缮证（打证），通过接口将权证标识码推送至审批平台。"/>
                       </td>
                   </tr>
                   <tr name="bdcczr_tr" style="display:none;">                    
                       <td class="tab_width">登记时间：</td>
                       <td>
                       <input type="text" id="BDC_DBSJ" name="BDC_DBSJ" 
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'BDC_DBSJ\')}'})" 
                             class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value='${recordinfo.BDC_DBSJ}' />                       
                       </td>
                       <td class="tab_width">登簿人：</td>
                       <td>
                       <input type="text" class="tab_text validate[]" placeholder="登簿人"
                           name="BDC_DBR" maxlength="11" value="${recordinfo.BDC_DBR}"/>
                       </td>
                   </tr>
				<tr>
					<td class="tab_width">登记原因：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" maxlength="128" placeholder="预售商品房预告换发登记" 
						name="BDC_DJYY" id="BDC_DJYY" value="${recordinfo.BDC_DJYY}" style="width: 72%;" readonly="readonly" />
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

<%-- 引入登记审核、缴费信息、发证、归档信息 --%>
<!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
<!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->   
<jsp:include page="./../common/djauditinfo.jsp">
    <jsp:param value="Page3" name="domId" />
    <jsp:param value="djshxx" name="initDomShow" />    
</jsp:include>