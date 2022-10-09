<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="slxx">
	<tr>
		<th colspan="6">受理信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
		<td colspan="2"> 
   <select name="REG_TYPE" clazz="tab_text validate[required]" style='width:186px;' defaultemptytext="首次登记" dataparams="DocumentType"><option>首次登记</option>
		<option>延续登记</option>
		<option>变更登记</option>
		<option>注销登记</option>
		<option>补发换发</option>
		</select></td>
		<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		<td colspan="2"> 
		<select name="POWER_TYPE" clazz="tab_text validate[required]" style='width:186px;' defaultemptytext="首次登记" dataparams="DocumentType">
	<option>房屋租赁登记备案_首次备案</option>
	<option>房屋租赁登记备案_延续登记</option>
	<option>房屋租赁登记备案_变更登记</option>
	<option>房屋租赁登记备案_注销登记</option>
	<option>房屋租赁登记备案_补证</option>
			</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>不动产权证号：</td>
		<td colspan="2"><input colspan="2" type="text" class="tab_text validate[required]" style="width:60%" maxlength="50"
				   name="BDCQZH" value="${busRecord.QZH}"></input></td>
		<td><a href="javascript:void(0);" class="eflowbutton" onclick="showSelectBdcdaxxcx();" id="dymxAdd">查 询</a></td>		   
			<td colspan="2"></td>	 
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
		<td colspan="2"><input type="text" class="tab_text validate[required]" name="SQR_DEPT" value="${busRecord.SQR_DEPT }"/></td>
		<td class="tab_width"><font class="tab_color ">*</font>房产证明文件：</td>
		<td colspan="2"><select name="FCZMWJ" clazz="tab_text validate[required]" style='width:186px;' defaultemptytext="不动产权证" dataparams="DocumentType">
	<option>不动产权证</option>
	<option>房屋所有权证</option>
	<option>其他</option> </select></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>出租屋地址：</td>
		<td colspan="2"><input colspan="2" type="text" class="tab_text validate[required]" style="width:60%" maxlength="50"
				   name="CZ_ADDR" value="${busRecord.CZ_ADDR }"></input></td>
	    <td class="tab_width"><font class="tab_color ">*</font>出租期限：</td>
		<td colspan="2"><input type="text" id="CZ_QX_BEGIN" name="CZ_QX_BEGIN" 
		 onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',minDate:'#F{$dp.$D(\'CZ_QX_BEGIN\')}'})" class="tab_text Wdate validate[required]" maxlength="32" style="width:160px;" value="${busRecord.CZ_QX_BEGIN}" />
		至
		<input type="text" id="CZ_QX_END" name="CZ_QX_END" 
		onclick="WdatePicker({dateFmt:'yyyy年MM月dd日',minDate:'#F{$dp.$D(\'CZ_QX_END\')}'})" class="tab_text Wdate validate[required]" maxlength="32" style="width:160px;" value="${busRecord.CZ_QX_END}" />
				 </td>
	</tr>
	
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>租金(月)：</td>
		<td colspan="2"><input type="text" class="tab_text validate[required]"
				   name="ZJ" value="${busRecord.ZJ}"/></td>
	    <td class="tab_width">备案证明编号：</td>
		<td colspan="2"><input type="text" class="tab_text validate[]"
				   name="BAZMBH" value="${busRecord.BAZMBH}" disabled="true"/></td>
		
				   
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color ">*</font>成交方式：</td>
		<td colspan="2"><select name="CJFS" id="CJFS" clazz="tab_text validate[required]" style='width:186px;' defaultemptytext="自行成交" dataparams="DocumentType"
		onclick="setJJRXXShow()"
		>
	    <option>自行成交</option>
	    <option>房地产经纪机构居间成交</option>
	    <option>房地产经纪构代理成交</option>
	    </select></td>
	    <td class="tab_width">出租部位：</td>
		<td colspan="2"><input type="text" class="tab_text validate[]"
				   name="CZBW" value="${busRecord.CZBW }"/></td>
		
				   
	</tr>
	
	<tr >
	
	    <td class="tab_width">租赁用途：</td>
		<td colspan="2"><select name="ZLYT" clazz="tab_text validate[]" style='width:186px;' defaultemptytext="居住" dataparams="DocumentType">
	          <option>居住</option>
	          <option>办公</option>
	          <option>经营</option>
	          <option>仓储</option>
	          <option>其它</option>
	    </select>

	</td>
	
		<td class="tab_width" ><span name="JJJG_NAME" style="display:none">经纪机构名称：</span></td>
		<td colspan="2"><input type="hidden"  class="tab_text validate[]"
				   name="JJJG_NAME" value="${busRecord.JJJG_NAME }"/></td> 
			   
	</tr>

	<tr style="display:none" name="tr1">

	<td class="tab_width"><span name="JJR_NAME" style="display:none">经纪人姓名：</span></td>
		<td colspan="2"><input type="hidden" class="tab_text validate[]"
				   name="JJR_NAME" value="${busRecord.JJR_NAME }"/></td>
		
		<td class="tab_width"><span name="JJRLX_PHONE" style="display:none">经纪人联系电话：</span></td>
		<td colspan="2"><input type="hidden" class="tab_text validate[]"
				   name="JJRLX_PHONE" value="${busRecord.JJRLX_PHONE }"/></td>
	
	</tr>
	
	<tr>
		<td class="tab_width">受理人员：</td>
		<td colspan="2"><input type="text" class="tab_text validate[]"
				   name="SLRY" value="${busRecord.SLRY}"/></td>
	  
		 <td colspan="1">
		<a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="printSqb"
							>打印申请表</a>
		</td>
		<td colspan="1">
		  <a href="javascript:void(0);" style="float:right; margin: 2px 10px;" class="eflowbutton" id="printBaZm"
							>打印备案证明</a>
		</td>
		
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="2"><textarea name="SL_BZ" cols="120" rows="10" class="validate[[],maxSize[500]]" maxlength="150" ></textarea>	
		</td>
	   <td colspan="2"></td> 
	</tr>
	
</table>

<div class="tab_height"></div>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="dymxTable">
</table>
