<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
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
<script type="text/javascript">

$(function(){
	
});

</script>
<div id="qsly">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<th>权属来源&nbsp;
			</th>
			<td style="width: 90px;">				
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectBdcygdacx();" id="qslyAdd">新 增</a>
			</td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="qslyInfo">
		
		<tr id="qslyInfo_1">
			<td>
				<table class="tab_tk_pro2">				
					<tr>					
						<td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
						<td colspan="3">
						
							<input type="hidden" name="bdcqsly" />
							<input type="text" class="tab_text  validate[required]"  style="width: 500px;"
							name="BDCDYH" maxlength="100" value="${busRecord.BDCDYH}"/>
						</td>
					
					</tr>
					<tr>					
						<td class="tab_width">原不动产证明号：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="BDCDJZMH" maxlength="40"/>
						</td>
						<td class="tab_width">权属状态：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="DYQSZT"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="选择权属状态" name="QSZT" >
							</eve:eveselect>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">权利人：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="QLR" maxlength="32" />
						</td>
						<td class="tab_width">权利人证件种类：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="选择证件种类" name="QLRZJZL" >
							</eve:eveselect>
							
						</td>
					</tr>
					<tr>					
						<td class="tab_width">权利人证件号码：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" 
							name="QLRZJH" maxlength="32" />
						</td>
					</tr>
					<tr>					
						<td class="tab_width">义务人：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="YWR" maxlength="32"/>
						</td>
						<td class="tab_width">义务人证件种类：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[]" dataParams="DYZJZL"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="选择证件种类" name="YWRZJZL" >
							</eve:eveselect>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">义务人证件号码：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" 
							name="YWRZJH" maxlength="32" />
						</td>
					</tr>
					<tr>					
						<td class="tab_width">抵押合同号：</td>
						<td >
							<input type="text" class="tab_text validate[]" 
							name="HTH" maxlength="32"/>
						</td>
						<td class="tab_width">被担保主债权数额：</td>
						<td>
							<input type="text" class="tab_text validate[]" 
							name="QDJG" maxlength="32" />
						</td>
					</tr>
				
					<tr>
						<td class="tab_width">债务起始时间：</td>
						<td>
							<input type="text" name="QSSJ" 
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
								readonly="true" class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" />
						</td>
						<td class="tab_width">债务结束时间：</td>
						<td>
							<input type="text" name="JSSJ" 
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
								readonly="true" class="tab_text Wdate validate[]" maxlength="32" style="width:160px;" />
						</td>
					</tr>
				</table>
				<div class="tab_height2"></div>
			</td>
			<td>
				<input type="button" class="eflowbutton" name="deleteQslyInfoInput" value="删除" onclick="deleteQslyInfo('1');">
			</td>
		</tr>
	</table>
</div>