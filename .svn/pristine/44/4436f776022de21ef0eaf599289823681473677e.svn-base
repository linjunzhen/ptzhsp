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
<%--换发抵押权证明登记 --%>
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

<input type="hidden" name="POWERPEOPLEINFO_JSON" value="${recordinfo.POWERPEOPLEINFO_JSON}" id="POWERPEOPLEINFO_JSON2"/>
<%--不动产抵押信息接口需回传字段 --%>
<input type="hidden" name="YWH" value="${recordinfo.YWH}"/>
<input type="hidden" name="DYCODE" value="${recordinfo.DYCODE}"/>
<input type="hidden" name="GLH" value="${recordinfo.GLH}"/>

<%--不动产档案信息接口需回传字段 --%>
<%-- <input type="hidden" name="ZDDM" value="${recordinfo.ZDDM}"/>
<input type="hidden" name="FWBM" value="${recordinfo.FWBM}"/>
<input type="hidden" name="DA_YWH" value="${recordinfo.DA_YWH}"/>
<input type="hidden" name="ZXYWH" value="${recordinfo.ZXYWH}"/>
<input type="hidden" name="DA_GLH" value="${recordinfo.DA_GLH}"/> --%>


<div class="tab_height"></div>
<%--权利人开始 --%>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerPeopleInfo">
	<tr>
		<th >权利人</th>
		<td> <input type="button" class="eflowbutton"  name="addOnePowerPeopleInfo" value="新增" onclick="addPowerPeopleInfo();"> </td>
	</tr>
	<tr id="powerPeopleInfo_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>抵押权人：</td>
					<td colspan="3">
						<%-- <eve:eveselect clazz="tab_text validate[]" dataParams="CYYHHMC"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setDyqrName();"
						defaultEmptyText="选择抵押权人" name="QLR_DYQR" id="QLR_DYQR" value="${recordinfo.QLR_DYQR}" >
						</eve:eveselect> --%>
						<%--  <input id="QLR_DYQR" name="QLR_DYQR" class="tab_text1 validate[required]" value="${recordinfo.QLR_DYQR}" onchange="setDyqrName(this.value);"/> --%>	
						<input class="easyui-combobox tab_text_1 validate[required]" name="QLR_DYQR" id="QLR_DYQR"
						data-options="
						    prompt : '请输入或者选择抵押权人',
							url: 'dictionaryController/auto.do?typeCode=CYYHHMC',
							method: 'get',
							valueField : 'DIC_NAME',
							textField : 'DIC_NAME',
							filter : function(q, row) {
								var opts = $(this).combobox('options');
								return row[opts.textField].indexOf(q) > -1; 
							},
							onSelect:function(){
								var value = $('#QLR_DYQR').combobox('getValue');
								setDYQLName(value);
							}									
						"/><span style="width:25px;display:inline-block;text-align:right;">
								<img src="plug-in/easyui-1.4/themes/icons/edit_add.png" onclick="newDicInfoWin('CYYHHMC','QLR_DYQR');" style="cursor:pointer;vertical-align:middle;" title="新建抵押权人">
						  </span>
						  <span style="width:25px;display:inline-block;text-align:right;">
								<img src="plug-in/easyui-1.4/themes/icons/edit_remove.png" onclick="removeDicInfo('QLR_DYQR');" style="cursor:pointer;vertical-align:middle;" title="删除选中的抵押权人">
						  </span>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_DYQRZJHM');"
						defaultEmptyText="选择证件类型" name="QLR_DYQRZJLX" id="QLR_DYQRZJLX" value='${recordinfo.QLR_DYQRZJLX}'>
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="QLR_DYQRZJHM" style="float: left;"
						name="QLR_DYQRZJHM" value='${recordinfo.QLR_DYQRZJHM}' " /><a href="javascript:void(0);" onclick="QLR1_Read(this);" class="eflowbutton">身份证读卡</a>
					</td>
				</tr>
				<tr>
				    <td class="tab_width">地址：</td>
				    <td colspan='3'> <input type="text" class="tab_text validate[]" maxlength="64" id="QLR_DZ" style="float: left;width:560px"
						name="QLR_DZ"  value='${recordinfo.QLR_DZ}' " />
					</td>
				</tr>
				<tr>
					<td class="tab_width">抵押权人性质：</td>
					<td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_DYQRXZ" style="float: left;"
						name="QLR_DYQRXZ"  value='${recordinfo.QLR_DYQRXZ}' "/>
					</td>	
					<td class="tab_width">抵押权人电话：</td>
					<td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_DYQRDH" style="float: left;"
						name="QLR_DYQRDH"  value='${recordinfo.QLR_DYQRDH}' "/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">电子邮件：</td>
					<td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_DZYJ" style="float: left;"
						name="QLR_DZYJ"  value='${recordinfo.QLR_DZYJ}' "/>
					</td>	
					<td class="tab_width">邮编：</td>
					<td><input type="text" class="tab_text validate[]" maxlength="32" id="QLR_YB" style="float: left;"
						name="QLR_YB"  value='${recordinfo.QLR_YB}' "/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">法定代表人姓名：</td>
					<td colspan='3'><input type="text" class="tab_text validate[]" 
						name="QLR_LEGALNAME" maxlength="32" value="${recordinfo.QLR_LEGALNAME}" style="width:560px"/></td>
				</tr>
				<tr>
					<td class="tab_width">证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_LEGALZJHM');"
						defaultEmptyText="选择证件类型" name="QLR_LEGALZJLX" id="QLR_LEGALZJLX" value='${recordinfo.QLR_LEGALZJLX}'>
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="QLR_LEGALZJHM" style="float: left;"
						name="QLR_LEGALZJHM" value='${recordinfo.QLR_LEGALZJHM}' /><a href="javascript:void(0);" onclick="QLR2_Read(this);" class="eflowbutton">身份证读卡</a>
					</td>
				</tr>
				<tr>
					<td class="tab_width">代理人姓名：</td>
					<td colspan='3'><input type="text" class="tab_text validate[]" 
						name="QLR_DLRNAME" maxlength="32" value="${recordinfo.QLR_DLRNAME}" style="width:560px"/></td>
				</tr>
				<tr>
					<td class="tab_width">证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'QLR_DLRZJHM');"
						defaultEmptyText="选择证件类型" name="QLR_DLRZJLX" id="QLR_DLRZJLX" value='${recordinfo.QLR_DLRZJLX}'>
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="QLR_DLRZJHM" style="float: left;"
						name="QLR_DLRZJHM" value='${recordinfo.QLR_DLRZJHM}' /><a href="javascript:void(0);" onclick="QLR3_Read(this);" class="eflowbutton">身份证读卡</a>
					</td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan='3'><input type="text" class="tab_text validate[]" style="width:560px;"
						name="QLR_REMARK" maxlength="64" value="${recordinfo.QLR_REMARK}"/></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" class="eflowbutton" name="deleteCurrentPowerPeopleInfo" value="删除" onclick="deletePowerPeopleInfo('1');">
		</td>
	</tr>
</table>
<%--权利人结束 --%>

<%--权属来源开始 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="qslyInfo">
	<tr>
		<th >权属来源</th>
		<td><a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectBdcdydacx();">不动产抵押档案查询</a>
		</td>
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width">不动产单元号：</td>
					<td >
						<input type="text" class="tab_text validate[]"  style="width:300px;" 
						name="BDCDYH" maxlength="64" value="${recordinfo.BDCDYH}"/>
					</td>
					<td class="tab_width">原不动产证明号：</td>
					<td >
						<input type="text" class="tab_text validate[]"  style="width: 300px;" 
						name="BDCDJZMH" maxlength="64" value="${recordinfo.BDCDJZMH}"/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">不动产权证号：</td>
					<td >
						<input type="text" class="tab_text validate[]" style="width: 300px;" 
						name="BDCQZH" maxlength="64" value="${recordinfo.BDCQZH}" />
					</td>
					<td class="tab_width">坐落：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZJJZWZL" maxlength="128" value="${recordinfo.ZJJZWZL}" style="width: 400px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">抵押权人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYQR" maxlength="64" value="${recordinfo.DYQR}" style="width: 300px;" />
					</td>				
					<td class="tab_width">权属状态：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QSZT" maxlength="32" value="${recordinfo.QSZT}" style="width: 300px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">证件类型：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZJLB" maxlength="10" value="${recordinfo.ZJLB}" style="width: 300px;" />
					</td>				
					<td class="tab_width">证件号码：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZJHM" maxlength="32" value="${recordinfo.ZJHM}" style="width: 300px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">抵押人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYR" maxlength="64" value="${recordinfo.DYR}" style="width: 300px;" />
					</td>				
					<td class="tab_width">抵押人证件号：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYRZJH" maxlength="32" value="${recordinfo.DYRZJH} " style="width: 300px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">债务人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZWR" maxlength="64" value="${recordinfo.ZWR} " style="width: 300px;" />
					</td>				
					<td class="tab_width">债务人证件号：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZWRZJH" maxlength="32" value="${recordinfo.ZWRZJH} " style="width: 300px;" />
					</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td></td>
	</tr>
</table>
<%--权属来源结束 --%>

<%--基本信息 --%>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="jbxxInfo">
	<tr>
		<th >基本信息</th>
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width">抵押人：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]"  style="width:490px;" 
						name="JBXX_DYR" maxlength="64" value="${recordinfo.JBXX_DYR}"/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">抵押人证件类型：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DYQRZJHM');"
						defaultEmptyText="选择证件类型" name="DYRZJLX" id="DYRZJLX" value='${recordinfo.DYZJZL}'>
						</eve:eveselect>
					</td>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="32" id="DYQRZJHM" style="float: left;"
						name="DYRZJHM" value='${recordinfo.DYRZJHM}' />
					</td>
				</tr>
				<tr>					
					<td class="tab_width">抵押物价值：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="32" id="DYWJZ" style="float: left;width: 300px;"
						name="DYWJZ" value='${recordinfo.DYWJZ}' />
					</td>
					<td class="tab_width">是否最高额抵押：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setSfzgedy(this.value);"
						defaultEmptyText="选择是否最高额抵押" name="SFZGEDY" id="SFZGEDY"  value="${recordinfo.SFZGEDY}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">抵押方式：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYFS" maxlength="32" value=" ${recordinfo.DYFS}" style="width: 300px;" readonly='readonly'/>
					</td>				
					<td class="tab_width">被担保主债权数额：：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[numberp6plus]" 
						name="BDBZZQSE" maxlength="16" value="${recordinfo.BDBZZQSE} " style="width: 300px;" />
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">最高债权额：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[numberp6plus]" 
						name="ZGZQSE" maxlength="16" value=" ${recordinfo.ZGZQSE}" style="width: 300px;" />
					</td>				
					<td class="tab_width">抵押宗数：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[integerplus]" maxlength="16"
						name="DYZS" value="${recordinfo.DYZS}" style="width:300px;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">债务起始时间：</td>
					<td>
						<input type="text" id="QLQSSJ" name="QLQSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'QLJSSJ\')}'})" 
							 class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value='${recordinfo.QLQSSJ}' readonly='readonly'/>
					</td>
					<td class="tab_width">债务结束时间：</td>
					<td>
						<input type="text" id="QLJSSJ" name="QLJSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'QLQSSJ\')}'})" 
						    class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" value="${recordinfo.QLJSSJ}" readonly='readonly'/>
					</td>
				</tr>
				<tr>
					<td class="tab_width">登记原因：</td>
					<td colspan='3'><input type="text" class="tab_text" maxlength="128"
						name="DJYY" value="${recordinfo.DJYY}" style="width:490px;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">最高债权确定事实：</td>
					<td colspan='3'><input type="text" class="tab_text" maxlength="128"
						name="ZGZQQDSS" value="${recordinfo.ZGZQQDSS}" style="width:490px;"  />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%--基本信息 --%>

<%-- 档案信息--%>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="daInfo">
	<tr>
		<th >档案信息</th>
	<!-- 	<td><a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectBdcdaxxcx();">不动产档案信息查询</a>
		</td> -->
	</tr>
	<tr>
		<td>
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width">不动产单元号：</td>
					<td >
						<input type="text" class="tab_text validate[]"  style="width:300px;" 
						name="DAXX_BDCDYH" maxlength="32" value="${recordinfo.DAXX_BDCDYH}" readonly='readonly'/>
					</td>
					<td class="tab_width">产权状态：</td>
					<td >
						<input type="text" class="tab_text validate[]"  style="width: 300px;" 
						name="CQZT" maxlength="16" value="${recordinfo.CQZT}" readonly='readonly'/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">不动产权证号：</td>
					<td >
						<input type="text" class="tab_text validate[]" style="width: 300px;" 
						name="DAXX_BDCQZH" maxlength="64" value="${recordinfo.DAXX_BDCQZH}" readonly='readonly' />
					</td>
					<td class="tab_width">抵押不动产类型：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DYBDCLX" maxlength="16" value="${recordinfo.DYBDCLX} " style="width: 300px;" readonly='readonly'/>
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">不动产权利人：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="QLRMC" maxlength="64" value="${recordinfo.QLRMC} " style="width: 300px;"/>
					</td>				
					<td class="tab_width">权属状态：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DAXX_QSZT" maxlength="16" value="${recordinfo.DAXX_QSZT} " style="width: 300px;" readonly='readonly'/>
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">证件类型：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="ZJLX" maxlength="16" value=" ${recordinfo.ZJLX}" style="width: 300px;" />
					</td>				
					<td class="tab_width">证件号码：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="DAXX_ZJHM" maxlength="32" value="${recordinfo.DAXX_ZJHM} " style="width: 300px;"/>
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">坐落：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="FDZL" maxlength="100" value=" ${recordinfo.FDZL}" style="width: 400px;" readonly='readonly'/>
					</td>				
					<td class="tab_width">面积：</td>
					<td>
						<input type="text" class="tab_text validate[]" 
						name="JZMJ" maxlength="16" value="${recordinfo.JZMJ} " style="width: 300px;" readonly='readonly'/>
					</td>
				</tr>
				<tr>	
				    <td class="tab_width">备注：</td>
					<td colspan='3'>
						<input type="text" class="tab_text validate[]" 
						name="BZ" maxlength="100" value=" ${recordinfo.BZ}" style="width: 300px;"/>
					</td>				
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
	</tr>
</table>
<%--档案信息 --%>
