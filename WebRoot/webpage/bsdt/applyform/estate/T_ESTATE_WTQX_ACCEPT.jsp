<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<style type="text/css">
	.inputWidth{
		width:70%;
	}
</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="AcceptInfo_form">
	<tr>
		<th >受理信息 </th>
	</tr>
	<tr>
		<td> 
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>委托申报号：</td>
					<td colspan="3"> 
						<input type="text" class="tab_text validate[required]"  maxlength="30" id="O_EVEID" name="O_EVEID"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="eflowbutton" value="读取记录" onclick="loadDataByEveId();"/>						
					</td>
				</tr>
				<tr>
					<td class="tab_width">委托权利人：</td>
					<td>
					     <input type="text" readonly="readonly" class="tab_text validate[required]" maxlength="30" name="WT_QLR" />
					</td>
					<td class="tab_width">委托权证号：</td>
					<td>
						<input type="text" readonly="readonly" class="tab_text validate[required]" maxlength="30" name="WT_CQZH" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">委托地址：</td>
					<td colspan="3">
					     <input type="text" readonly="readonly" class="tab_text inputWidth validate[required]" maxlength="30" name="WT_ADDR" />
					</td>
				</tr>
				<tr>
					<td class="tab_width">委托人：</td>
					<td>
					     <input type="text" readonly="readonly" class="tab_text validate[required]" maxlength="30" name="WTR_NAME" />
					</td>
					<td class="tab_width">受托人：</td>
					<td>
						<input type="text" readonly="readonly" class="tab_text validate[required]" maxlength="30" name="STR_NAME" />
					</td>
				</tr>
			    <tr>
					<td class="tab_width">委托期限（开始时间）：</td>
					<td ><input type="text" class="tab_text Wdate validate[required]" id="WT_STARTDATE" readonly="true"
						name="WT_STARTDATE" /></td>
					<td class="tab_width">委托期限（结束时间）：</td>
					<td>
						<input type="text" class="tab_text Wdate validate[required]" id="WT_ENDDATE" readonly="true"
						name="WT_ENDDATE"  />
					</td>
				</tr>
			    <tr>
			    	<td class="tab_width">委托事项：</td>
			    	<td style="width:630px" colspan="3">
			    	   <input name="WT_ITEMS" type="hidden"/>
			    	   <textarea readonly="readonly" id="WT_ITEMS_TXT" name="WT_ITEMS_TXT" cols="145" rows="5" readonly="readonly"
						class="validate[[required],maxSize[1000]]"></textarea>
			    	</td>
			    </tr>				
				<tr>
					<td class="tab_width">委托原因：</td>
					<td width="630px" colspan="3"><textarea readonly="readonly" name="WT_REASON" cols="145" rows="5"
						class="validate[maxSize[1000]]"></textarea></td>
				</tr>	
				<tr>
					<td class="tab_width">委托备注：</td>
					<td width="630px" colspan="3"><textarea readonly="readonly" name="WT_BZ" cols="145" rows="5"
						class="validate[maxSize[1000]]"></textarea></td>
				</tr>
				<tr>
					<td class="tab_width">经办人：</td>
					<td>
					     <input type="text" readonly="readonly" class="tab_text validate[required]" maxlength="30" name="WT_JBR" />
					</td>
					<td class="tab_width">经办日期：</td>
					<td>
						<input type="text" readonly="readonly" class="tab_text validate[required]" maxlength="30" name="WT_JBDATE" />
					</td>
				</tr>					
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>注销原因：</td>
					<td width="630px" colspan="3"><textarea name="WTQX_REASON" cols="145" rows="5"
						class="validate[[required],maxSize[1000]]"></textarea></td>
				</tr>	
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>注销经办人：</td>
					<td>
					  <c:if test="${execution.CREATOR_NAME!=null}">
					      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${execution.CREATOR_NAME}" maxlength="30" name="WTQX_JBR" />
					  </c:if>
					  <c:if test="${execution.CREATOR_NAME==null}">
					      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${sessionScope.curLoginUser.fullname}" maxlength="30" name="WTQX_JBR" />
					  </c:if>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>注销日期：</td>
					<td>
						<input type="text"  class="tab_text validate[required]"  value="${busRecord.WTQX_JBDATE}"  readonly="readonly" maxlength="30" name="WTQX_JBDATE" />
					</td>
				</tr>				
			</table>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<script type="text/javascript">
    function CurentTime(){
	    var now = new Date();
	    var year = now.getFullYear();       //年
	    var month = now.getMonth() + 1;     //月
	    var day = now.getDate();            //日
	    var hh = now.getHours();            //时
	    var mm = now.getMinutes();          //分
	    var clock = year + "-";
	    if(month < 10)
	        clock += "0";
	    clock += month + "-";
	    if(day < 10)
	        clock += "0";
	    clock += day + " ";
	    if(hh < 10)
	        clock += "0";
	    clock += hh + ":";
	    if (mm < 10) clock += '0';
	    clock += mm;
	    return(clock);
	};
	
	function loadDataByEveId(){
	    var old_eveId = $("#O_EVEID").val();
	    if(old_eveId){
	    	AppUtil.ajaxProgress({
				url : "bdcApplyController.do?loadDataByEveId",
				params : {
					"eveId":old_eveId
				},
				callback : function(resultJson) {
					if(resultJson.success){
						var busRecord = resultJson.data;
						FlowUtil.initFormFieldValue(busRecord,"AcceptInfo_form");				
					}else{
						art.dialog({
			                content: resultJson.msg,
			                icon:"warning",
			                ok: true
			            });
					}
				}
			});
	    }
		
	}
	
	$(function(){
        var handTime="${busRecord.WTQX_JBDATE}";
        if(handTime==''){
            handTime=CurentTime();
            $("input[name='WTQX_JBDATE']").val(handTime);
        }
    });
</script>