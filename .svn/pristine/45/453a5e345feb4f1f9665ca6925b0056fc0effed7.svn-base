<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>

<tr>	
	<td>
	<input type="hidden" name="${currentTime}SPLB_NAME" value="保健食品"/>
	<input type="hidden" name="${currentTime}SPSCXKXX_CP_ID" value=""/>
					<input type="hidden" name="${currentTime}CPXX_LBMC" value=""/>
		<fda:fda-exselect name="${currentTime}CPXX_SPLB"  style="width: 97%;" clazz="input-dropdown validate[required]" 
					allowblank="false" datainterface="fdaBusTypeService.findListForSelect" 
					defaultemptytext="=请选择类别="  disabled="true" value="402848df54e19b880154e1a3698b0027"
				 queryparamjson="{PARENT_ID:'402848df54e0e6560154e17bfb86007b',ORDER_TYPE:'ASC'}">
		 </fda:fda-exselect>
	</td>				
	<td>
		<fda:fda-exselect name="${currentTime}CPXX_LBBH"  style="width: 97%;" clazz="input-dropdown validate[required]" 
				allowblank="false" datainterface="fdaBusTypeService.findListForSelect" 
				defaultemptytext="=请选择类别名称=" 
			 queryparamjson="{PARENT_ID:'402848df54e19b880154e1a3698b0027',ORDER_TYPE:'ASC'}">
		</fda:fda-exselect>
	</td>		
	<td style="text-align: center;"><input type="hidden" name="CPXX_DEPTID">
					<input name="${currentTime}CPXX_ISPROVINCE" type="hidden" value="2"/>
					<input type="hidden" maxlength="128" placeholder="请选择单位"  style="width:92%"
					class="syj-input1 validate[]" name="${currentTime}CPXX_DEPTNAME" readonly="readonly"/>
					<input type="text" class="syj-input1 validate[required]" readonly="readonly" placeholder="类别编号" name="${currentTime}LBBH_CODE"  style="width:50%"/>
					<%-- <input type="button" style="width:60%" onclick="showSelectBusTypes('${currentTime}');" class="extract-button2" value="选择品种明细"> --%>
				</td>				
	<td style="text-align: center;">
		<input type="hidden" name="${currentTime}CPXX_PZMXID" value="${cpxxList.CPXX_PZMXID}">		
		<textarea name="${currentTime}CPXX_PZMX" <%-- readonly="readonly" onclick="showSelectBusTypes('${currentTime}');" --%>
		style="width: 95%" class="eve-textarea validate[required,max[256]]" cols="5" rows="5"></textarea>			
		<%-- <input style="margin-top: 5px;" type="button" value="选择品种明细" class="extract-button" onclick="showSelectBusTypes('${currentTime}');"> --%>
	</td>					
	<%-- <td><textarea type="text" maxlength="256" cols="5" rows="5"
		value="" placeholder="请输入产品执行标准文号" style="width:92%"
		class="eve-textarea validate[required]" name="${currentTime}CPXX_CPZXBZWH" ></textarea><!-- <a href="javascript:void(0);"
			onclick="delCpxxDiv(this);" class="syj-closebtn" style="position: relative;right: -102%;"></a> --></td> --%>
	<td>
		<textarea type="text" maxlength="256"
					value="" placeholder="请输入备注" cols="3" rows="3"
					class="eve-textarea " name="${currentTime}CPXX_BZ" style="width:90%"></textarea>
					<span style="line-height:14px;font-size:12px;" name="${currentTime}SLJG"></span>
	<a href="javascript:void(0);"
			onclick="delCpxxDiv(this);" class="syj-closebtn" style="position: relative;right:-30%;float: right;"></a></td>
</tr>
<script type="text/javascript">
$(function(){
	$("select[name='${currentTime}CPXX_SPLB']").change(function(){  
		resetPzmx('${currentTime}');
		var JYCSXS = $("select[name='JYCSXS']").val();
		if(JYCSXS!=null&&JYCSXS!=""){	
			var pId = $(this).children("option:selected").val();
			$("[name$=SPLB_NAME]").val($(this).children("option:selected").text());
			FdaAppUtil.reloadSelect($("select[name='${currentTime}CPXX_LBBH']"),{
				dataParams:"{PARENT_ID:'"+pId+"',ORDER_TYPE:'ASC',DEPARTID_2:'${curDepartId }'}"
			});
		}else{
			art.dialog({
	            content: "请先填写生产地址！",
	            icon:"warning",
	            ok: true
	        });
			$("select[name='CPXX_SPLB']").val('');
		}
	});
	  $("select[name='${currentTime}CPXX_LBBH']").change(function(){ 
		  var JYCSXS = $("select[name='JYCSXS']").val();
			if(!JYCSXS||JYCSXS==""){
				art.dialog({
		            content: "请先填写生产地址！",
		            icon:"warning",
		            ok: true
		        });
				$("select[name='${currentTime}CPXX_LBBH']").val('');
				return;
			}
			resetPzmx('${currentTime}');
			var pId = $(this).children("option:selected").val();
			var pName = $(this).children("option:selected").text();
			var JYCSQS = $("select[name='JYCSQS']").val();
			var JYCSXS = $("select[name='JYCSXS']").val();
			$("[name='${currentTime}CPXX_LBMC']").val(pName);
			var trIndex = $("#cpxxDiv tr").index($(this).closest("tr"));
			$.post("foodProductionController/getCpxxDept.do",{
				typeId:pId,
				JYCSQS:JYCSQS,
				JYCSXS:JYCSXS
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				//$("[name='${currentTime}CPXX_ISPROVINCE']").val(resultJson.ISPROVINCE);
				$("[name='ISPROVINCE']").val(resultJson.ISPROVINCE);
				$("[name='${currentTime}CPXX_DEPTID']").val(resultJson.DEPART_ID);
				$("[name='${currentTime}CPXX_DEPTNAME']").val(resultJson.DEPART_NAME);
				$("[name='${currentTime}LBBH_CODE']").val(resultJson.TYPE_CODE);
				//$("[name='${currentTime}SLJG']").html("受理机构："+resultJson.DEPART_NAME);
			});
	  });
});

</script>