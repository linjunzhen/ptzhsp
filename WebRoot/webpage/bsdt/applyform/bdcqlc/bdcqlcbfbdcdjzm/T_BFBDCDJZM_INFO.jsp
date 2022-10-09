<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<%--预购商品房预告登记 预购预告信息 --%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerInfo">
	<tr>
		<th>基本信息</th>
	</tr>
	<tr id="powerInfo_1">
		<td>
			<table class="tab_tk_pro2">				
				<tr>					
					<td class="tab_width"><font class="tab_color ">*</font>抵押人：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="DYR" maxlength="128" value="${busRecord.DYR}" onblur="checkLimitPerson();"/>
						<font class="tab_color">(多个抵押人以顿号区别)</font>
						</td>
					<td class="tab_width"><font class="tab_color ">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYZJZL"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="选择证件类型" name="DYRZJZL" id="DYRZJZL"  value="${busRecord.DYRZJZL}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="128" id="DYRZJHM" style="float: left;"
						name="DYRZJHM"  value="${busRecord.DYRZJHM}" onblur="checkLimitPerson();"/>
					</td>
					<td><a href="javascript:void(0);" onclick="IDCRAD_Read('DYR','DYRZJZL');" class="eflowbutton">读 卡</a></td>
					<td></td>
				</tr>
				<tr>					
					<td class="tab_width">抵押物价值：</td>
					<td>
						<input type="text" class="tab_text" maxlength="128"
						name="DYWJZ" value="${busRecord.DYWJZ}" style="float: left;width: 72%;"/>
					</td>				
					<td class="tab_width"><font class="tab_color ">*</font>是否最高额抵押：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setSfzgedy(this.value);"
						defaultEmptyText="选择是否最高额抵押" name="SFZGEDY" id="SFZGEDY"  value="${busRecord.SFZGEDY}">
						</eve:eveselect>
					</td>
				</tr>
				
				<tr>	
					<td class="tab_width"><font class="tab_color ">*</font>抵押方式：</td>
					<td >						
						<eve:eveselect clazz="tab_text validate[required]" dataParams="BDCDYFS"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="选择抵押方式" name="DYFS" id="DYFS"  value="${busRecord.DYFS}">
						</eve:eveselect>
					</td>				
					<td class="tab_width"><font class="tab_color ">*</font>被担保主债权数额：</td>
					<td>
						<input type="text" class="tab_text validate[required],custom[JustNumber]" maxlength="16"
						name="BDBZZQSE" value="${busRecord.BDBZZQSE}" style="width: 72%;"  />
					</td>
				</tr>
				
				<tr>	
					<td class="tab_width">最高债权额：</td>
					<td >	
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="ZGZQE" value="${busRecord.ZGZQE}" style="width: 72%;"  />
					</td>				
					<td class="tab_width">抵押宗数：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[integerplus]" maxlength="16"
						name="DYZS" value="${busRecord.DYZS}" style="width: 72%;"  />
					</td>
				</tr>
				
				<!--<tr>	
					<td class="tab_width">抵押宗地总面积：</td>
					<td >	
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="DYZSZMJ" value="${busRecord.DYZSZMJ}" style="width: 72%;"  disabled="disabled"/>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">抵押建筑总面积：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="DYJZZMJ" value="${busRecord.DYJZZMJ}" style="width: 72%;"  disabled="disabled"/>
					</td>
					<td class="tab_width">抵押分摊土地总面积：</td>
					<td >	
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="DYQDJ_DYFTTDZMJ" value="${busRecord.DYQDJ_DYFTTDZMJ}" style="width: 72%;" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">抵押独用土地总面积：</td>
					<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="16"
						name="DYFTTDZMJ" value="${busRecord.DYFTTDZMJ}" style="width: 72%;" disabled="disabled" />
					</td>
				</tr>-->
				
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>债务起始时间：</td>
					<td>
						<input type="text" id="ZWQSSJ" name="ZWQSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'ZWJSSJ\')}'})" 
							 class="tab_text Wdate validate[required]" maxlength="32" style="width:160px;" />
					</td>
					<td class="tab_width"><font class="tab_color ">*</font>债务结束时间：</td>
					<td>
						<input type="text" id="ZWJSSJ" name="ZWJSSJ" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'ZWQSSJ\')}'})" 
							 class="tab_text Wdate validate[required]" maxlength="32" style="width:160px;" />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>登记原因：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
						name="DJYY" value="${busRecord.DJYY}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>最高债权确定事实：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
						name="ZGZQQDSS" value="${busRecord.ZGZQQDSS}" style="width: 72%;"  />
					</td>
				</tr>
				
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>抵押范围：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="500"
						name="DYFW" value="${busRecord.DYFW}" style="width: 72%;"  />
					</td>
				</tr>
				
				<tr>
					<td class="tab_width"><font class="tab_color ">*</font>附记：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
						name="FJ" value="${busRecord.FJ}" style="width: 72%;" />
						<input type="button" class="eflowbutton" value="意见模板" onclick="AppUtil.cyyjmbSelector('bfbdcdjzm','FJ');">
					</td>
				</tr>
				
				<tr>
					<td class="tab_width">登簿人：</td>
					<td>
						<input type="text" class="tab_text" maxlength="128" id="BDC_DBR"
							style="float: left;" name="BDC_DBR" value="${busRecord.BDC_DBR}" />
					</td>

					<td class="tab_width">登记时间：</td>
					<td>
						<input type="text" class="tab_text" maxlength="32" id="BDC_DBSJ"
							style="float: left;" name="BDC_DBSJ" value="${busRecord.BDC_DBSJ}" />
					</td>
				</tr>
								
			</table>
			<div class="tab_height2"></div>
		</td>
	</tr>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerInfo">
	<tr>
		<th>档案信息</th>
	</tr>
	<tr id="powerInfo_1">
		<td>
			<table class="tab_tk_pro2">		
				<tr>	
					<td class="tab_width">不动产单元号：</td>
					<td >	
						<input type="text" class="tab_text validate[]" maxlength="16"
						name="DAXX_BDCDYH" value="${busRecord.DAXX_BDCDYH}" style="width: 72%;"  />
					</td>				
					<td class="tab_width">产权状态：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="16"
						name="DAXX_CQZT" value="${busRecord.DAXX_CQZT}" style="width: 72%;"  />
					</td>
				</tr>		
				<tr>	
					<td class="tab_width">不动产权证号：</td>
					<td >	
						<input type="text" class="tab_text validate[]" maxlength="250"
						name="DAXX_BDCQZH" value="${busRecord.DAXX_BDCQZH}" style="width: 72%;"  />
					</td>				
					<td class="tab_width">抵押不动产类型：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="50"
						name="DAXX_DJLX" value="${busRecord.DAXX_DJLX}" style="width: 72%;"  />
					</td>
				</tr>		
				<tr>	
					<td class="tab_width">不动产权利人：</td>
					<td >	
						<input type="text" class="tab_text validate[]" maxlength="250"
						name="DAXX_QLR" value="${busRecord.DAXX_QLR}" style="width: 72%;"  />
					</td>				
					<td class="tab_width">权属状态：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="5"
						name="DAXX_QSZT" value="${busRecord.DAXX_QSZT}" style="width: 72%;"  />
					</td>
				</tr>		
				<tr>	
					<td class="tab_width">证件类型：</td>
					<td >	
						<input type="text" class="tab_text validate[]" maxlength="25"
						name="DAXX_QLRZJZL" value="${busRecord.DAXX_QLRZJZL}" style="width: 72%;"  />
					</td>				
					<td class="tab_width">证件号码：</td>
					<td>
						<input type="text" class="tab_text validate[]" maxlength="250"
						name="DAXX_QLRZJH" value="${busRecord.DAXX_QLRZJH}" style="width: 72%;"  />
					</td>
				</tr>		
				<tr>	
					<td class="tab_width">坐落：</td>
					<td >	
						<input type="text" class="tab_text validate[]" maxlength="100"
						name="DAXX_BDCZL" value="${busRecord.DAXX_BDCZL}" style="width: 72%;"  />
					</td>				
					<td class="tab_width">面积：</td>
					<td>
						<input type="text" class="tab_text validate[],custom[JustNumber]" maxlength="16"
						name="DAXX_JZMJ" value="${busRecord.DAXX_JZMJ}" style="width: 72%;"  />
					</td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="500"
						name="DAXX_REMARK" value="${busRecord.DAXX_REMARK}" style="width: 72%;"  />
					</td>
				</tr>
								
			</table>
			<div class="tab_height2"></div>
		</td>
	</tr>
</table>