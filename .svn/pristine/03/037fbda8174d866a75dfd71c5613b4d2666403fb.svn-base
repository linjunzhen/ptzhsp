<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
$(function(){
	/**委托人身份证**/
	  $("select[name='WTRZJLX']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  if(typeCode=="1"){
			  $("input[name='WTRZJH']").addClass(",validate[custom[vidcard]]");
		  }else{
			  $("input[name='WTRZJH']").removeClass(",validate[custom[vidcard]]");
		  }
	  });
	  var start1 = {
				elem : "#WTKSSJ",
				format : "YYYY年MM月DD日",
				istime : false,
				choose : function(datas) {
					var beginTime = $("#WTKSSJ").val();
					var endTime = $("#WTJSSJ").val();
					if (beginTime != "" && endTime != "") {
						beginTime = beginTime.replace("年","").replace("月","").replace("日","");
						endTime = endTime.replace("年","").replace("月","").replace("日","");
						var start = parseInt(beginTime);
						var end = parseInt(endTime);
						if (start > end) {
							alert("委托开始日期必须小于等于委托结束日期,请重新输入!");
							$("#WTKSSJ").val("");
						}
					}
				}
			};
			var end1 = {
				elem : "#WTJSSJ",
				format : "YYYY年MM月DD日",
				istime : false,
				choose : function(datas) {
					var beginTime = $("#WTKSSJ").val();
					var endTime = $("#WTJSSJ").val();
					if (beginTime != "" && endTime != "") {
						beginTime = beginTime.replace("年","").replace("月","").replace("日","");
						endTime = endTime.replace("年","").replace("月","").replace("日","");
						var start = parseInt(beginTime);
						var end = parseInt(endTime);
						if (start > end) {
							alert("委托结束日期必须大于等于委托开始日期,请重新输入!");
							$("#WTJSSJ").val("");
						}
					}
				}
			};
			laydate(start1);
			laydate(end1);
});
</script>
<form action="" id="CLIENT_FORM"  >
	<div class="syj-title1"><span>委托人信息</span></div>
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		 <tr>
			<th>委托代理人：</th>
			<td>
			<input type="text" placeholder="请输入委托代理人"
			maxlength="100" class="syj-input1" name="WTDLR" />
			</td>
			<th>委托人：</td>
			<td>
				<input type="text" placeholder="请输入委托人"
				maxlength="100" class="syj-input1" name="WTR" />
			</td>
		</tr>
		<tr>
			<th>证件类型：</th>
			<td>
			<fda:fda-exselect name="WTRZJLX" datainterface="fdaDictionaryService.findList" 
				queryparamjson="{TYPE_CODE:'zjlx',ORDER_TYPE:'ASC'}"
				defaultemptytext="请选择证件类型"
				allowblank="true" clazz="input-dropdown"></fda:fda-exselect>
			</td>
			<th>证件号：</th>
			<td>
			<input type="text" placeholder="请输入证件号"
			maxlength="100" class="syj-input1 " name="WTRZJH" />
			</td>
		</tr>
		<tr>
			<th>委托开始日期：</th>
			<td>
			<input type="text"  id="WTKSSJ"
				placeholder="请选择委托开始日期"
			maxlength="16" class="syj-input1 laydate-icon" name="WTKSSJ" />
			</td>
			<th>委托结束日期：</th>
			<td>
			<input type="text" id="WTJSSJ"
				placeholder="请选择委托结束日期"
			maxlength="16" class="syj-input1 laydate-icon" name="WTJSSJ" />
			</td>
		</tr>
		<tr>
			<th>委托权限</th>
			<td colspan="3">
				<p>1、<fda:fda-exradiogroup name="WTXGCLQX" width="120" 
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'AgreeOrDisagree',ORDER_TYPE:'ASC'}"
						allowblank="true" puremode="true"></fda:fda-exradiogroup>
				修改自备材料中的修改材料 </p>
				<p>2、<fda:fda-exradiogroup name="WTXGBGQX" width="120" 
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'AgreeOrDisagree',ORDER_TYPE:'ASC'}"
						allowblank="true" puremode="true"></fda:fda-exradiogroup>
				修改有关表格的填写错误 </p>
				<p>3、<fda:fda-exradiogroup name="WTLQZSQX" width="120" 
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'AgreeOrDisagree',ORDER_TYPE:'ASC'}"
						allowblank="true" puremode="true"></fda:fda-exradiogroup>
				领取许可证有关证书 </p>
				<p>4、<fda:fda-exradiogroup name="WTHDQX" width="120" 
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'AgreeOrDisagree',ORDER_TYPE:'ASC'}"
						allowblank="true" puremode="true"></fda:fda-exradiogroup>
				核对申请材料中的复印件并签署核对意见</p>
				<p>5、其他委托事项及权限（请详细注明）<input type="text"  style="width: 400px;"
					placeholder="请输入其他委托事项及权限" maxlength="200" class="syj-input1 " name="WTQTCZQX" /></p>
			</td>
		</tr>
		<tr>
			<th>固定电话：</th>
			<td>
			<input type="text" placeholder="请输入固定电话"
			maxlength="200" class="syj-input1 validate[custom[fixPhoneWithAreaCode]]" name="WTRGDDH" />
			</td>
			<th>移动电话：</th>
			<td>
			<input type="text" placeholder="请输入移动电话"
			maxlength="200" class="syj-input1 validate[custom[mobilePhoneLoose]]" name="WTRYDDH" />
			</td>
		</tr> 
	</table>
</form>
