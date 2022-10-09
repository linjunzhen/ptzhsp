<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%--抵押权注销登记 注销明细 --%>
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
.bdczxxxcxTrRed{
	color:red;
}
</style>
<div id='zxmx'>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr> 
			<th>注销明细&nbsp;
			</th>
			<td style="width: 200px;">				
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectBdcdydacx();" name="zxmxAdd" id="zxmxAdd">新 增</a>
				<a href="javascript:void(0);" class="eflowbutton"  style="display:none" onclick="BDCQLC_bdcdbcz();" name="doBooking" id="doBooking">确认登簿</a>
			</td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="zxmxTable">
		<tr>
			<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">幢号</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">户号</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
			<td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">不动产登记证明号</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">抵押权人</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">抵押人</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		</tr>	
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='zxmxInfo'>
		<tr id="zxmxInfo_1">
			<td>
				<table class="tab_tk_pro2">	
					<tr>					
						<td class="tab_width">不动产权证号：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]"  style="width: 350px;"
							name="BDCQZH" disabled="disabled" value="${busRecord.BDCQZH}"/>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">不动产单元号：</td>
						<td ><input type="text" class="tab_text validate[]" disabled="disabled" style="width: 350px;"
							name="BDCDYH" value="${busRecord.BDCDYH}"/></td>
						<td class="tab_width">不动产登记证明号：</td>
						<td><input type="text" class="tab_text " disabled="disabled" style="width: 350px;"
						    name="BDCDJZMH" value="${busRecord.BDC_BDCDJZMH}"/> </td>
					</tr>
					<tr>
						<td class="tab_width">权属状态：</td>
						<td><input type="text" class="tab_text validate[]" disabled="disabled"  style="width: 350px;"
							 name="QSZT"  value="${busRecord.QSZT}"/></td>
						<td class="tab_width">抵押顺位：</td>	
						<td><input type="text" class="tab_text validate[]" disabled="disabled"  style="width: 350px;"
							name="DYSW"  value="${busRecord.DYSW}"/></td>
					</tr>	
					<tr>
						<td class="tab_width">债务人：</td>
						<td><input type="text" class="tab_text " disabled="disabled"  style="width: 350px;"
						    name="ZWR" value="${busRecord.ZWR}"/></td>
					    <td class="tab_width">抵押权人：</td>
					    <td><input type="text" class="tab_text validate[]" disabled="disabled"  style="width: 350px;"
						     name="DYQR" value="${busRecord.DYQR}"/>
						</td>
					</tr>
					
					<tr>
                        <td class="tab_width">证照类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
                                           dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'DYQRXX_IDNO');"
                                           defaultEmptyText="选择证照类型" name="DYQRXX_ZJZL" value="营业执照" >
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">证照号码：</td>
                        <td>
                            <input type="text" class="tab_text validate[]" maxlength="128" id="DYQRXX_IDNO" style="float: left;"
                                   name="DYQRXX_IDNO"  value="${busRecord.DYQRXX_IDNO}"/>
                        </td>
	                </tr>
					<tr>
						<td class="tab_width">经办人：</td>
						<td><input type="text" class="tab_text validate[]"  
						    name="DYQRXX_JB" id="DYQRXX_JB" value="${busRecord.DYQRXX_JB}"/></td>							
					    <td class="tab_width">经办人电话：</td>
					    <td><input type="text" class="tab_text validate[]"  
						     name="DYQRXX_JBDH" id="DYQRXX_JBDH" value="${busRecord.DYQRXX_JBDH}"/>
						</td>						
					</tr>
					<tr>
						<td>经办人证件类型</td>
						<td>				
						<eve:eveselect clazz="tab_text  validate[]" onchange="setZjValidate(this.value,'DYQRXX_JBZJHM');"
											dataParams="DYZJZL" value="${busRecord.DYQRXX_JBZJLX}"
											dataInterface="dictionaryService.findDatasForSelect" id="DYQRXX_JBZJLX"
											defaultEmptyText="====选择证件类型====" name="DYQRXX_JBZJLX"></eve:eveselect>
						</td>
						<td class="tab_width">经办人证件号码：</td>
						<td><input type="text" class="tab_text validate[]" 
						    name="DYQRXX_JBZJHM" id="DYQRXX_JBZJHM" value="${busRecord.DYQRXX_JBZJHM}"/></td>
					</tr>					
					<tr>
					    <td class="tab_width">抵押人：</td>
						<td><input type="text" class="tab_text validate[]" disabled="disabled"  style="width: 350px;"
							name="DYR"  value="${busRecord.DYR}"/></td>
						<td class="tab_width">抵押人手机号码：</td>
						<td><input type="text" class="tab_text validate[]"  disabled="disabled"
								name="DYRXX_DHHM"  value="${busRecord.DYRXX_DHHM}"/><font color="red">(多个人之间用英文逗号","隔开)</font></td>				
					</tr>					
					<tr>	
						<td class="tab_width">抵押人证件类型：</td>
                        <td><eve:eveselect clazz="tab_text  " onchange="setZjValidate(this.value,'ZJHM');"
											dataParams="DYZJZL" value="${busRecord.ZJLB}"
											dataInterface="dictionaryService.findDatasForSelect" id="ZJLB"
											defaultEmptyText="====选择证件类型====" name="ZJLB"></eve:eveselect></td>					    
						<td class="tab_width">抵押人证件号码：</td>
                        <td><input type="text" class="tab_text validate[]"  disabled="disabled" 
                                   name="ZJHM" id="ZJHM" value="${busRecord.ZJHM}"/></td>        
					</tr>
					<tr>
						<td class="tab_width">幢号：</td>
						<td><input type="text" class="tab_text validate[]" disabled="disabled"  style="width: 350px;"
							name="ZH"  value="${busRecord.ZH}"/></td>
						<td class="tab_width">户号：</td>
						<td ><input type="text" class="tab_text validate[]" disabled="disabled"  style="width: 350px;"
							 name="HH"  value="${busRecord.HH}"/></td>	
					</tr>
					<tr>
						<td class="tab_width">抵押不动产类型：</td>
						<td><input type="text" class="tab_text validate[]" disabled="disabled"  style="width: 350px;"
							 name="DYBDCLX"  value="${busRecord.DYBDCLX}"/>
						</td>
						 <td class="tab_width">抵押范围：</td>
						 <td><input type="text" class="tab_text " disabled="disabled"  style="width: 350px;"
						   name="ZJJZWDYFW"  value="${busRecord.ZJJZWDYFW}" /></td>
					</tr>
					<tr>
						<td class="tab_width">债务起始时间：</td>
						<td><input type="text" class="tab_text " disabled="disabled"  style="width: 350px;"
								   name="QLQSSJ" value="${busRecord.QLQSSJ}"/></td>
						<td class="tab_width">债务结束时间：</td>
						<td><input type="text" class="tab_text " disabled="disabled"  style="width: 350px;"
								   name="QLJSSJ" value="${busRecord.QLJSSJ}"/></td>
					</tr>		
					<tr>
						<td class="tab_width">被担保主债权数额：</td>
						<td><input type="text" class="tab_text " disabled="disabled"   style="width: 350px;"
							 name="BDBZZQSE" value="${busRecord.BDBZZQSE}"/></td>
						<td class="tab_width">最高债权数额：</td>
						<td><input type="text" class="tab_text " disabled="disabled"  style="width: 350px;"
						    name="ZGZQSE" value="${busRecord.ZGZQSE}"/></td>
					</tr>
					<tr>
					    <td class="tab_width">坐落：</td>
						<td ><input type="text" class="tab_text " disabled="disabled"
								   name="ZJJZWZL" style="width:350px;" value="${busRecord.ZJJZWZL}"/></td>
					    <td class="tab_width">担保范围：</td>
						<td ><input type="text" class="tab_text "  maxlength="1000" style="width: 350px;"
						   name="DBFW"  value="${busRecord.DBFW}"/></td>
					</tr>
					<tr>
					    <td class="tab_width">最高债权确定事实:</td>
						<td><input type="text" class="tab_text validate[]"  disabled="disabled" style="width: 350px;"
							name="ZGZQQDSS" value="${busRecord.ZGZQQDSS}"/></td>
							
					    <td class="tab_width">产权状态:</td>
						<td><input type="text" class="tab_text validate[]"  disabled="disabled" style="width: 350px;"
							name="CQZT" value="${busRecord.CQZT}"/></td>
					</tr>
                    <tr>
                        <td class="tab_width">抵押方式：</td>
                        <td><input type="text" class="tab_text " disabled="disabled"  style="width: 350px;"
                                   name="DYFS" value="${busRecord.DYFS}"/></td>
                        <td></td>
                        <td></td>
                    </tr>
					<tr>
						<td class="tab_width">附记：</td>
						<td colspan="3"><input type="text" class="tab_text validate[]" style="width: 1000px;"
							name="FJ" maxlength="500" value="${busRecord.FJ}"/></td>
					</tr>	
				</table>
				<div class="tab_height2"></div>
			</td>
	 		<td>
	 		   <input type="hidden" name="trid" />
			   <input type="button" class="eflowbutton" name="updateZxmxInfoInput" value="保存" onclick="updateZxmxInfo('1');">
			</td> 
		</tr>
	</table>
</div>