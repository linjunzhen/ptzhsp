<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function selectChildDepartName(){
		var departId = $("input[name='xkdept_id']").val();
        parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
                +"checkCascadeN=&noAuth=true", {
            title : "部门选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectDepInfo = art.dialog.data("selectDepInfo");
                if(selectDepInfo){
                    $("input[name='xkdept_id']").val(selectDepInfo.departIds);
                    $("input[name='xkdept_name']").val(selectDepInfo.departNames);
                }
            }
        }, false);
	}

</script>
<style>
.eve-select-width{
	width:400px !important;
}
</style>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">办结结果</th>
		</tr>
		<tr>
				<td><span style="width: 90px;float:left;text-align:right;">（许可）文件编号：</span>
				</td>
				<td>
					<input type="text" id="xkfile_num" name="xkfile_num"
										class="eve-input" maxlength="60" style="width:200px;" />
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">（许可）文件名称：</span>
				</td>
				<td>
					<input type="text" id="xkfile_name" name="xkfile_name"
										class="eve-input" maxlength="120" style="width:200px;" />
				</td>
		</tr>
		<tr>
				<td><span style="width: 90px;float:left;text-align:right;">生效时间：</span>
				</td><td>
					<input type="text" id="effect_time" name="effect_time"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'close_time\')}'})" 
					readonly="true" class="tab_text Wdate" maxlength="60" style="width:160px;" />
				</td>
				<td><span style="width: 90px;float:left;text-align:right;">截止时间：</span>
				</td><td>
					<input type="text" id="close_time" name="close_time"
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'effect_time\')}'})" 
					readonly="true" class="tab_text Wdate" maxlength="60" style="width:160px;" />
				</td>
		</tr>
		<tr>
				<td><span style="width: 90px;float:left;text-align:right;">（许可）机关：
				</span>
				<input type="hidden"  name="xkdept_id" id="xkdept_id"/>
				</td>
				<td colspan="3">
					<input type="text" id="xkdept_name" name="xkdept_name"
					readonly="readonly" onclick="selectChildDepartName();"
							class="eve-input" maxlength="60" style="width:400px;" />
				</td>
		</tr>
	    <tr height="80px"> 
			<td ><span style="width: 90px;float:left;text-align:right;">（许可）内容：
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="5" cols="6"
					class="eve-textarea"
					maxlength="1600" style="width: 500px" name="xkcontent"></textarea>
			</td>
		</tr>
		<tr height="70px"> 
			<td ><span style="width: 90px;float:left;text-align:right;">经营方式：
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="4" cols="6"
					class="eve-textarea"
					maxlength="500" style="width: 500px" name="run_mode"></textarea>
			</td>
		</tr>
	</table>