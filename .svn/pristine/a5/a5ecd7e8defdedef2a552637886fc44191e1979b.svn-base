<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<style type="text/css">
	.inputWidth{
		width:72%;
	}

</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th >不动产委托书
		<a style="padding:8px 20px;line-height: 30px;height:30px;font-weight: bold;" class="eflowbutton"  id="bdcBtnWtPrint" onclick="printBdcWt();">打印委托书</a>
		<font class="tab_color">打印前先暂存操作。</font>
        </th>
	</tr>
	<tr>
		<td> 
			<table class="tab_tk_pro2">
			    <tr>
					<td class="tab_width"><font class="tab_color">*</font>委托期限（开始时间）：</td>
					<td ><input type="text" class="tab_text Wdate validate[required]" id="WT_STARTDATE" 
						name="WT_STARTDATE" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,startDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'WT_ENDDATE\')}'})" /></td>
					<td class="tab_width"><font class="tab_color">*</font>委托期限（结束时间）：</td>
					<td>
						<input type="text" class="tab_text Wdate validate[required]" id="WT_ENDDATE" 
						name="WT_ENDDATE" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'WT_STARTDATE\')}'})" />
					</td>
				</tr>
			    <tr>
			    	<td class="tab_width"><font class="tab_color">*</font>委托事项：</td>
			    	<td style="width:630px" colspan="3">
			    	   <input name="WT_ITEMS" type="hidden"/>
			    	   <textarea id="WT_ITEMS_TXT" name="WT_ITEMS_TXT" cols="145" rows="5" readonly="readonly"
						class="validate[[required],maxSize[1000]]"></textarea><input type="button" class="eflowbutton" value="选择事项" onclick="selectWtItems();">
			    	</td>
			    </tr>				
				<tr>
					<td class="tab_width">委托原因：</td>
					<td width="630px" colspan="3"><textarea name="WT_REASON" cols="145" rows="5"
						class="validate[maxSize[1000]]"></textarea></td>
				</tr>				
				<tr>
					<td class="tab_width">委托备注：</td>
					<td width="630px" colspan="3"><textarea name="WT_BZ" cols="145" rows="5"
						class="validate[maxSize[1000]]"></textarea></td>
				</tr>	
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>经办人：</td>
					<td>
					  <c:if test="${execution.CREATOR_NAME!=null}">
					      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${execution.CREATOR_NAME}" maxlength="30" name="WT_JBR" />
					  </c:if>
					  <c:if test="${execution.CREATOR_NAME==null}">
					      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${sessionScope.curLoginUser.fullname}" maxlength="30" name="WT_JBR" />
					  </c:if>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>经办日期：</td>
					<td>
						<input type="text"  class="tab_text validate[required]"  value="${busRecord.WT_JBDATE}"  readonly="readonly" maxlength="30" name="WT_JBDATE" />
					</td>
				</tr>			
			</table>
		</td>
	</tr>
</table>
<div class="tab_height"></div>
<script type="text/javascript">
    function selectWtItems(){
        var wtitemCodes = $("input[name='WT_ITEMS']").val();
        parent.$.dialog.open("bdcApplyController.do?selector&wtitemCodes="
                + wtitemCodes, {
            title : "委托事项选择器",
            width : "800px",
            lock : true,
            resize : false,
            height : "500px",
            close: function () {
            	var selectWtItemsInfo = art.dialog.data("selectWtItemsInfo");
                if(selectWtItemsInfo&&selectWtItemsInfo.wtitemcodes){
                    $("input[name='WT_ITEMS']").val(selectWtItemsInfo.wtitemcodes);
                    $("#WT_ITEMS_TXT").val(selectWtItemsInfo.wtitemNames);
                    art.dialog.removeData("selectWtItemsInfo");                    
                }
            }
        }, false);
        
    };
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
	
	function today(){
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
	    clock += day;
	    return(clock);
	};
	
	function nextYearday(){
	    var now = new Date();
	    var year = now.getFullYear()+1;       //年
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
	    clock += day;
	    return(clock);
	};
	
    /* $(function(){
        var wtStartdate= $("#WT_STARTDATE").val();
        if(wtStartdate || wtStartdate==''){
            wtStartdate=today();
            $("input[name='WT_STARTDATE']").val(wtStartdate);
        }
    }); */
    
    function printBdcWt(){
	    var exeId="${execution.EXE_ID}";
		var dateStr = "";
		dateStr +="&eveId="+exeId;
		dateStr +="&TemplatePath=bdcwt.doc";
		dateStr +="&TemplateName=不动产委托书查询";
		//打印模版
       $.dialog.open(encodeURI("bdcApplyController.do?printBdcWtTemplate"+dateStr), {
                title : "打印模版",
                width: "100%",
                height: "100%",
                left: "0%",
                top: "0%",
                fixed: true,
                lock : true,
                resize : false
        }, false);
	}
    
</script>