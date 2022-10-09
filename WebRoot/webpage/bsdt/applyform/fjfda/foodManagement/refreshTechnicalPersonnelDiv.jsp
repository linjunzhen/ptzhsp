<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font color="#ff0101">*</font>姓 名</th>
			<td><input type="text" maxlength="100"
				value="" placeholder="请输入姓名"
				class="syj-input1 validate[required]" name="RYXM" /></td>
			<th><font color="#ff0101">*</font>性 别</th>
			<td><fda:fda-exradiogroup name="${currentTime}RYXB"
					width="100" value=""
					datainterface="fdaDictionaryService.findList"
					queryparamjson="{TYPE_CODE:'sex',ORDER_TYPE:'ASC'}"
					allowblank="false"></fda:fda-exradiogroup></td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>民 族</th>
			<td><fda:fda-exselect name="RYMZ" value="01"
					datainterface="fdaDictionaryService.findList"
					queryparamjson="{TYPE_CODE:'mzdm',ORDER_TYPE:'ASC'}"
					defaultemptytext="请选择民族" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
			</td>
			<th><font color="#ff0101">*</font>职 务</th>
			<td><fda:fda-exselect name="RYZW" value="499B"
					datainterface="fdaDictionaryService.findList"
					queryparamjson="{TYPE_CODE:'zwdm',ORDER_TYPE:'ASC'}"
					defaultemptytext="请选择职务" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>户籍登记住址</th>
			<td colspan="3"><input type="text" maxlength="50"
				value="" placeholder="请输入户籍登记地址"
				class="syj-input1 validate[required]" name="RYHJDJZZ" /></td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>证件类型</th>
			<td><fda:fda-exselect name="RYZJLX"
					value="1"
					datainterface="fdaDictionaryService.findList"
					queryparamjson="{TYPE_CODE:'zjlx',ORDER_TYPE:'ASC'}"
					defaultemptytext="请选择证件类型" allowblank="false"
					clazz="input-dropdown"></fda:fda-exselect></td>
			<th><font color="#ff0101">*</font>证件号</th>
			<td><input type="text" maxlength="100"
				value="" placeholder="请输入证件号"
				class="syj-input1 validate[required],custom[vidcard]" name="RYZJH" /></td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>联系电话</th>
			<td><input type="text" maxlength="40"
				value=""
				class="syj-input1 validate[required]"
				placeholder="请输入联系电话" name="RYLXDH" /></td>
			<th><font color="#ff0101">*</font>任免单位</th>
			<td><input type="text" maxlength="200"
				value="${jyzmc}" placeholder="请输入任免单位"
				class="syj-input1 validate[required]" name="RYRMDW" /></td>
		</tr>
		<tr>
			<td colspan="4">
				<input type="button" onclick="copyFRDBXX(this);" style="float: right;" class="extract-button" value="同法定代表人（负责人）">
			</td>
		</tr>
		<%-- <tr>
			<th><font color="#ff0101">*</font>人员类型</th>
			<td colspan="3"><fda:fda-exradiogroup
					name="${currentTime}RYLX" width="400"
					datainterface="fdaDictionaryService.findList"
					value=""
					queryparamjson="{TYPE_CODE:'RYLX',ORDER_TYPE:'ASC'}"
					allowblank="false"></fda:fda-exradiogroup></td>
		</tr> --%>
	</table>
	<a href="javascript:void(0);"
			onclick="delTechnicalPersonnelDiv(this);" class="syj-closebtn"></a>
</div>
<script type="text/javascript">
$("select[name='RYZJLX']").change(function(){ 
		  var typeCode = $(this).val();
		  if(typeCode=="1"){
			  if(!$(this).closest("tr").find("input[name='RYZJH']").hasClass(",custom[vidcard]")){
				  $(this).closest("tr").find("input[name='RYZJH']").addClass(",custom[vidcard]");  
			  }
		  }else{
			  //$(this).closest("tr").find("input[name='RYZJH']").removeClass(",custom[vidcard]");
			  $(this).closest("tr").find("input[name='RYZJH']").attr("class","syj-input1 validate[required]");
		  }
}); 
</script>