<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="SUBORDINATE_FORM"  >	
	<div class="syj-title1">
		<span>隶属企业</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2" style="table-layout: auto;">		
			<%-- <tr>
				<th><font class="syj-color2">*</font>隶属企业所在地：</th>
				<td colspan="3">
					<input type="radio" name="SUBORDINATE_LOCATION" value="1" <c:if test="${busRecord.SUBORDINATE_LOCATION!=0}"> checked="checked"</c:if>>本省
					<input type="radio" name="SUBORDINATE_LOCATION" value="0" <c:if test="${busRecord.SUBORDINATE_LOCATION==0}"> checked="checked"</c:if>>外省市
				</td>
			</tr> --%>
			<tr>
				<th ><font class="syj-color2">*</font>企业名称：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 w878 validate[required]"
						name="SUBORDINATE_NAME" maxlength="60" placeholder="请输入企业名称"  value="${busRecord.SUBORDINATE_NAME}"/>
				</td>
			</tr>
		<tr>
			<th ><font class="syj-color2">*</font>注册号/统一社会信用代码：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 w878 validate[required]"
					name="SUBORDINATE_CREDITUNICODE" maxlength="30" placeholder="请输入注册号/统一社会信用代码" value="${busRecord.SUBORDINATE_CREDITUNICODE}"/>
			</td>
			<%-- <th > 企业注册资金（万元）：</th>
			<td>
				<input type="text" class="syj-input1 validate[[],custom[JustNumber]]"
					name="SUBORDINATE_CAPITAL" maxlength="30" placeholder="请输入注册号/统一社会信用代码" value="${busRecord.SUBORDINATE_CAPITAL}"/>
			</td> --%>
		</tr>
				<tr>
					<th ><font class="syj-color2">*</font>企业类型：</th>
					<td >
						<input type="text" class="syj-input1 validate[required]"
							   name="MAIN_COMPANYTYPE" maxlength="60" placeholder="请输入企业类型"  value="${busRecord.MAIN_COMPANYTYPE}"/>
					</td>
					<th ><font class="syj-color2">*</font>登记机关：</th>
					<td >
						<input type="text" class="syj-input1 validate[required]"
							   name="MAIN_REGISAUTHOR" maxlength="60" placeholder="请输入登记机关"  value="${busRecord.MAIN_REGISAUTHOR}"/>
					</td>
				</tr>

				<tr>
					<th><font class="syj-color2">*</font>营业期限：</th>
					<td colspan="3"><input type="text" class="Wdate w878 validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
										   id="period" readonly="readonly" name="MAIN_BUSSINESSTIME"  placeholder="请选择期限" value="${busRecord.MAIN_BUSSINESSTIME}"/></td>
				</tr>
		<%-- <tr style="display: none;" id="locationInfo">
			<th ><font class="syj-color2">*</font>所属省、市、县：</th>
			<td colspan="3">
				<input name="SUBORDINATE_PROVINCE" class="easyui-combobox"  style="width:182px; height:26px;"
					data-options="
		                url:'commercialSetController/load.do?param=-1,1&defaultEmptyText=请选择所在省',
		                method:'post',
		                valueField:'VALUE',
		                textField:'TEXT',
		                panelHeight:'160',
		                required:true,
		                editable:false,
		                onSelect:function(rec){
						   $('#city').combobox('clear');
						   if(rec.VALUE){
						   	   var param = rec.VALUE+',2';
						       var url = 'commercialSetController/load.do?param='+param+'&defaultEmptyText=请选择所在市';
						       $('#city').combobox('reload',url);  
						   }
						}
                " value="${busRecord.SUBORDINATE_PROVINCE }" />					
				<input name="SUBORDINATE_CITY" id="city" class="easyui-combobox" style="width:182px; height:26px;" 
					data-options="
		                url:'commercialSetController/load.do?defaultEmptyText=请选择所在市',
		                method:'post',
		                valueField:'VALUE',
		                textField:'TEXT',
		                panelHeight:'160',
		                required:true,
		                editable:false,
		                onSelect:function(rec){
						   $('#area').combobox('clear');
						   if(rec.VALUE){
						   	   var param = rec.VALUE+',3';
						       var url = 'commercialSetController/load.do?param='+param+'&defaultEmptyText=请选择所在区（县）';
						       $('#area').combobox('reload',url);  
						   }
						}
                " value="${busRecord.SUBORDINATE_CITY }" />	                					
				<input name="SUBORDINATE_AREA" id="area" class="easyui-combobox" style="width:182px; height:26px;" 
					data-options="			                
		                url:'commercialSetController/load.do?defaultEmptyText=请选择所在区（县）',
		                method:'post',
		                valueField:'VALUE',
		                textField:'TEXT',
		                panelHeight:'160',
		                required:true,
		                editable:false
                " value="${busRecord.SUBORDINATE_AREA }" />	
			</td>
		</tr> --%>
		</table>
	</div>
</form>
