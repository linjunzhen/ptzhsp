<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
</style>
<form action="" id="WXY_FORM" >	
	<div  id="wxy_div">
		<e:for filterid="gcjswxy" end="100" var="efor" dsid="115">
		<div class="syj-title1 ">		
			<span>${efor.TYPE_NAME}</span>
		</div>
		<div style="position:relative;"">				
			<table cellpadding="0" cellspacing="1" class="syj-table2 ">
				<tr>
					<th style="width: 90%;">危险源名称</th>
					<th style="width: 10%;">是否有危险源</th>
				</tr>
				<e:for filterid="${efor.TYPE_CODE}" end="100" var="efor1" dsid="115">
				<tr>
					<td colspan="2"><b>${efor1.TYPE_NAME}</b></td>
				</tr>
					<e:for filterid="${efor1.TYPE_CODE}" end="100" var="efor2" dsid="40">
					<tr>
						<td>${efor2.dic_name}</td>
						<td style="text-align: center;">
							<input class="checkbox validate[]"  name="${efor1.TYPE_CODE}" value="${efor2.dic_code}" type="checkbox">
						</td>
					</tr>
					</e:for>
				</e:for>
			</table>
		</div>
		</e:for>		
	</div>	
	<table cellpadding="0" cellspacing="1" class="syj-table2 ">
		<tr>
			<td ><font class="syj-color2">*</font>起重机械设备（施工升降机）的数量</td>
			<td style="text-align: center;">
				<input type="text" style="width:90%" class="syj-input1 validate[required],custom[numberplus]" onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"
					name="SGSJJNUM"  placeholder="请输入数量" maxlength="8" value="${busRecord.SGSJJNUM}"/>
			</td>
		</tr>
		<tr>
			<td><font class="syj-color2">*</font>起重机械设备（塔式起重机）的数量</td>
			<td style="text-align: center;">
				<input type="text" style="width:90%" class="syj-input1 validate[required],custom[numberplus]" onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"
					name="TSQZJNUM"  placeholder="请输入数量" maxlength="8" value="${busRecord.TSQZJNUM}"/>
			</td>
		</tr>
	</table>
	<div style="color: red;margin-left: 5px">提示：以上起重机械填报的数量指的是本次申报内容新增的起重机械的数量。若本次申报内容未涉及建筑起重器械，请填写为0。</div>
</form>
