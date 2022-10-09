<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>

    <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->   

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String domId = request.getParameter("domId");
    request.setAttribute("domId", domId);
    String initDomShow = request.getParameter("initDomShow");
    request.setAttribute("initDomShow",initDomShow);
%>
<style>
.eflowbutton {
    background: #3a81d0;
    border: none;
    padding: 0 10px;
    line-height: 26px;
    cursor: pointer;
    height: 26px;
    color: #fff;
    border-radius: 5px;
}

.eflowbutton-disabled {
    background: #94C4FF;
    border: none;
    padding: 0 10px;
    line-height: 26px;
    cursor: pointer;
    height: 26px;
    color: #E9E9E9;
    border-radius: 5px;
}

.selectType {
    margin-left: -100px;
}

.bdcdydacxTrRed {
    color: red;
}
</style>
<script type="text/javascript">
	function DjxxLzrLegalRead(thiz){
		GT2ICROCX.PhotoPath = "c:"
		//GT2ICROCX.Start() //循环读卡
		//单次读卡(点击一次读一次)
		if (GT2ICROCX.GetState() == 0){
			GT2ICROCX.ReadCard();	
			$("input[name='DJFZXX_LZRXM']").val(GT2ICROCX.Name);
			$("input[name='DJFZXX_LZRZJHM']").val(GT2ICROCX.CardNo);
		}
	}
    function addinfo_${param.domId}(id){
        $("#"+id).attr("style","");
        //$("#"+id+"_btn").attr("style","");
        $("#"+id+" input[type='text']").val('');
        $("#"+id+" select").val('');
    }
    
    function closeinfo_${param.domId}(id){
        //$("#"+id).attr("style","display:none;");
        $("#"+id).attr("trid","");
    }
    
    function delDjxxTr_${param.domId}(obj){
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
        });
    }
</script>
<div id="djxx_${param.domId}">
    <c:if test="${fn:contains(initDomShow,'djshxx')}">
        <script>
            function printAuditTableSingle_${param.domId}(){
                alert("审批表（单方）");
            };            
            function printAuditTableDouble_${param.domId}(){
                alert("审批表（双方）");
            };
            function printGG_${param.domId}(){
                alert("打印公告");
            };
		</script>
	    <!-- 登记审核开始 -->
	    <div id="djshxx_${param.domId}">        
	        <div class="tab_height"></div>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	            <tr>
	                <th>
	                    <span style="float:left;margin-left:10px">登记审核</span>
	                    <a href="javascript:void(0);" style="float:right; margin: 2px 5px;" class="eflowbutton" id="spbdf"
	                        onclick="printAuditTableSingle_${param.domId}();">审批表（单方）</a>
	                    <a href="javascript:void(0);" style="float:right; margin: 2px 3px" class="eflowbutton" id="spbsf"
	                        onclick="printAuditTableDouble_${param.domId}();">审批表（双方）</a>
	                    <a href="javascript:void(0);" style="float:right; margin: 2px 3px" class="eflowbutton"
	                        >打印公告</a>
	                </th>
	            </tr>
	        </table>
	
	        <div class="tab_height"></div>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djshInfo_${param.domId}">
	            <tr>
	                <td style="padding: 10px;">
	                    <table class="tab_tk_pro2">
	                        <tr>
	                            <td class="tab_width">是否公告：</td>
	                            <td>
	                                <eve:radiogroup typecode="YesOrNo" width="130px"
	                                    fieldname="DJSH_SFGG" defaultvalue="0" value="${busRecord.DJSH_SFGG}"></eve:radiogroup>
	                            </td>
	                            <td class="tab_width">公告编号：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJSH_GGBH" maxlength="32"
	                                    value="${busRecord.DJSH_GGBH}" style="float: left;" />
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="tab_width">公告时长（工作日）：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJSH_GGSC" maxlength="3"
	                                    value="${busRecord.DJSH_GGSC}" />
	                            </td>
	                            <td class="tab_width"></td>
	                            <td>                                
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="tab_width">开始公告日期：</td>
	                            <td>
	                                <input type="text" class="tab_text Wdate" name="DJSH_GGQSSJ" id="DJSH_GGQSSJ" maxlength="32"
	                                    value="${busRecord.DJSH_GGQSSJ}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'DJSH_GGJSSJ\')}'})"/>
	                            </td>
	                            <td class="tab_width">结束公告日期：</td>
	                            <td>
	                                <input type="text" class="tab_text Wdate" name="DJSH_GGJSSJ" id="DJSH_GGJSSJ" maxlength="32"
	                                    value="${busRecord.DJSH_GGJSSJ}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'DJSH_GGQSSJ\')}'})" style="float: left;" />
	                            </td>
	                        </tr>
	                    </table>
	                </td>
	            </tr>
	        </table>
	    </div>
	    <!-- 登记审核结束 -->
    </c:if>

    <c:if test="${fn:contains(initDomShow,'djjfxx')}">
        <script>
	       function submitDjjfxxInfo_${param.domId}(type){

			   var flag = validatePowerParams("djjfxx_${param.domId}","required");

			   if (!flag) {
				   return false;
			   }

		        if($("#djjfmxInfo_${param.domId}_list_isblank")){
		            $("#djjfmxInfo_${param.domId}_list_isblank").remove();
		        }
		        var iteminfo = FlowUtil.getFormEleData("djjfmxInfo_${param.domId}");
		        var trid = $("#djjfmxInfo_${param.domId}").attr("trid");
		        if(type == '0' && ""!=trid && undefined != trid){
		            art.dialog.confirm("是否继续添加缴费明细信息?", function() {
		                closeinfo_${param.domId}("djjfmxInfo_${param.domId}");  
		                addinfo_${param.domId}("djjfmxInfo_${param.domId}");  
		            });
		            return ;
		        }
		        if(""!=trid && undefined !=trid ){
		            var i = trid.split("_")[1];
		            appendDjjfxxRow_${param.domId}(iteminfo,i,trid);
		            art.dialog({
		                content : "缴费明细信息更新成功。",
		                icon : "succeed",
		                ok : true
		            });
		        }else{
		            var index = 0;
		            if($("#djjfmxInfo_${param.domId}_List .djjfxxinfo_tr")){
		                index = $("#djjfmxInfo_${param.domId}_List .djjfxxinfo_tr").length;
		                if(index > 0){
		                   var trid = $("#djjfmxInfo_${param.domId}_List .djjfxxinfo_tr").last().attr("id");
		                   index =parseInt(trid.split("_")[1]);
		                }
		            }
		            index = index + 1;
		            appendDjjfxxRow_${param.domId}(iteminfo,index,null);
		        }        
		        if(type == '0'){
		            art.dialog.confirm("添加成功！是否继续添加缴费信息?", function() {
		                closeinfo_${param.domId}("djjfmxInfo_${param.domId}");  
                        addinfo_${param.domId}("djjfmxInfo_${param.domId}");  
		            });
		        }
		    };
		    
		    function appendDjjfxxRow_${param.domId}(item,index,replaceTrid){
		        if(item != "" && null != item) {
		            var html = "";
		            html += '<tr class="djjfxxinfo_tr" id="djjfxxinfotr_' + index + '_${param.domId}">';
		            html += '<input type="hidden" name="iteminfo"/>';
		            html += '<td style="text-align: center;">' + index + '</td>';
		            html += '<td style="text-align: center;">' + item.DJJFMX_SFJS + '</td>';
		            html += '<td style="text-align: center;">' + item.DJJFMX_YSJE + '</td>';
		            html += '<td style="text-align: center;">' + item.DJJFMX_ZHYSJE + '</td>';
		            html += '<td style="text-align: center;">' + item.DJJFMX_SFRY + '</td>';
		            html += '<td style="text-align: center;">' + item.DJJFMX_SFRQ + '</td>';
		            html += '<td style="text-align: center;">' + item.DJJFMX_SSJE + '</td>';
		            html += '<td style="text-align: center;">';
		            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadDjjfxxInfo_${param.domId}(this,0);" id="djxxItem">查看</a>';
		            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadDjjfxxInfo_${param.domId}(this,1);" id="djxxItem">编辑</a>';
		            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr_${param.domId}(this);"></a></td>';
		            html += '</tr>';
		            if(replaceTrid){
		               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
		               $("#"+replaceTrid).remove();
		               if(prevtrid){
		                   $("#"+prevtrid).after(html)
		               } else{
		                   $("#djjfmxInfo_${param.domId}_List").append(html);
		               }       
		            }else{
		               $("#djjfmxInfo_${param.domId}_List").append(html);
		            }
		            $("#djjfxxinfotr_" + index + "_${param.domId} input[name='iteminfo']").val(JSON.stringify(item));
		            $("#djjfmxInfo_${param.domId}").attr("trid","djjfxxinfotr_"+index+"_${param.domId}");
		        }
		    };
		    
		    function loadDjjfxxInfo_${param.domId}(obj,ptype){
		        $("#djjfmxInfo_${param.domId}").attr("style","");
		        var trid = $(obj).closest("tr").attr("id");
		        $("#djjfmxInfo_${param.domId}").attr("trid",trid);
		        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
		        var item = JSON.parse(iteminfo);
		        FlowUtil.initFormFieldValue(item,"djjfmxInfo_${param.domId}");
		        if(ptype == "0"){
		            //查看
		            //$("#djjfmxInfo_${param.domId}_btn").attr("style","display:none;");
		        }else if(ptype == "1"){
		            //修改
		            //$("#djjfmxInfo_${param.domId}_btn").attr("style","");
		        }
		    };   
		    
		    function getDjjfxxJson_${param.domId}(){
		       var datas = [];
		       $("#djjfmxInfo_${param.domId}_List .djjfxxinfo_tr").each(function(){
		            var iteminfo = $(this).find("input[name='iteminfo']").val();
		            datas.push(JSON.parse(iteminfo));          
		       });
		       $("#DJJFMX_JSON_${param.domId}").val(JSON.stringify(datas));
		       return datas;
		    };  
		    
		    //回填数据
		    function initDjjfxx_${param.domId}(items){
		       if(items){
		           if($("#djjfmxInfo_${param.domId}_list_isblank")){
		              $("#djjfmxInfo_${param.domId}_list_isblank").remove();
		           }
		           
		           if($("#djjfmxInfo_${param.domId}_List .djjfxxinfo_tr")){
		              $("#djjfmxInfo_${param.domId}_List .djjfxxinfo_tr").remove();
		           }
		           
		           for(var i=0;i<items.length;i++){
		              appendDjjfxxRow_${param.domId}(items[i],(i+1),null);
		           }
		           $("#djjfmxInfo_${param.domId}").attr("style","");
		           var firstItem = $("#djjfmxInfo_${param.domId}_List .djjfxxinfo_tr")[0];
		           var id = $(firstItem).attr("id");
		           $("#djjfmxInfo_${param.domId}").attr("trid",id);
		           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
		           if(iteminfo){
		                var item = JSON.parse(iteminfo);
		                FlowUtil.initFormFieldValue(item,"djjfmxInfo_${param.domId}");
		           }
		           //$("#djjfmxInfo_${param.domId}_btn").attr("style","display:none;");
		       }
		    };
		         
        </script>
	    <!-- 登记缴费明细开始 -->
	    <div id="djjfxx_${param.domId}">	        
	        <div class="tab_height"></div>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	            <tr>
	                <th>
	                    <span style="float:left;margin-left:10px">登记缴费明细</span>
	                    <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 10px;" value="保存" onclick="submitDjjfxxInfo_${domId}('1');"/>
	                    <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 10px;" value="添加" onclick="submitDjjfxxInfo_${domId}('0');"/>
	                    <!--  
	                    <input type="button" class="eflowbutton" style="float:right; margin: 2px 0px; padding: 0 10px;background: red;color:#fff;font-weight: bold;" value="打印缴费单子" onclick="addinfo('powerPeopleInfo');"/>
	                    -->
	                </th>
	            </tr>
	        </table>
	
	        <div class="tab_height"></div>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djjfmxInfo_${param.domId}"  trid="">
	            <tr>
	                <td style="padding: 10px;">
	                    <table class="tab_tk_pro2">
	                        <tr>
	                            <td class="tab_width">
	                                <font class="tab_color">*</font>
	                                收费类型：
	                            </td>
	                            <td>
	                                <eve:eveselect clazz="tab_text validate[required]" dataParams="SFLX"
	                                    dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择收费类型"
	                                    name="DJJFMX_SFLX" value="${busRecord.DJJFMX_SFLX}">
	                                </eve:eveselect>
	                            </td>
	                            <td class="tab_width">收费科目编码：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJJFMX_SFKMBM" maxlength="128"
	                                    value="${busRecord.DJJFMX_SFKMBM}"/>
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">计费人员：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJJFMX_JFRY" maxlength="32"
	                                    value="${busRecord.DJJFMX_JFRY}" />
	                            </td>
	                            <td class="tab_width">收费科目名称：</td>
	                            <td>
	                                <eve:eveselect clazz="tab_text validate[]" dataParams="KMMC"
	                                    dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择收费科目名称"
	                                    name="DJJFMX_SFKMMC" id="DJJFMX_SFKMMC" value="${busRecord.DJJFMX_SFKMMC}">
	                                </eve:eveselect>
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">收费基数：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJJFMX_SFJS" maxlength="128"
	                                    value="${busRecord.DJJFMX_SFJS}" />
	                            </td>
	                            <td class="tab_width">计费日期：</td>
	                            <td>
	                                <%-- <input type="text" class="tab_text" name="DJJFMX_JFRQ" maxlength="128"
	                                    value="${busRecord.DJJFMX_JFRQ}" /> --%>
	                                 <input type="text" id="DJJFMX_JFRQ" name="DJJFMX_JFRQ"  
                                          onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
                                          class="tab_text Wdate" maxlength="60" style="width:160px;" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width" id="isEwsf">是否额外收费：</td>
	                            <td>
	                                <eve:eveselect clazz="tab_text validate[]" dataParams="YesOrNo" defaultEmptyValue="0"
	                                    dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择收是否额外收费"
	                                    name="DJJFMX_SFEWSF" value="${busRecord.DJJFMX_SFEWSF}">
	                                </eve:eveselect>
	                            </td>
	
	                            <td class="tab_width">
	                                <font class="tab_color" id="ysjeFont">*</font>应收金额：
	                            </td>
	                            <td>
	                                <input type="text" class="tab_text validate[required]" name="DJJFMX_YSJE" maxlength="128"
	                                    value="${busRecord.DJJFMX_YSJE}" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width" id="zhysje">折后应收金额：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJJFMX_ZHYSJE" maxlength="128"
	                                    value="${busRecord.DJJFMX_ZHYSJE}" />
	                            </td>
	                            <td class="tab_width">实际付费人：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJJFMX_SJFFR" maxlength="32"
	                                    value="${busRecord.DJJFMX_SJFFR}" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">付费方：</td>
	                            <td colspan="3">
	                                <input type="text" class="tab_text" name="DJJFMX_FFF" maxlength="500" style="width: 72%;"
	                                    value="${busRecord.DJJFMX_FFF}" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">收费单位：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJJFMX_SFDW" maxlength="128"
	                                    value="${busRecord.DJJFMX_SFDW}" />
	                            </td>
	                            <td class="tab_width"><font class="tab_color">*</font>实收金额：</td>
	                            <td>
	                                <input type="text" class="tab_text validate[required]" name="DJJFMX_SSJE" maxlength="128"
	                                    value="${busRecord.DJJFMX_SSJE}" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">收费人员：</td>
	                            <td>
	                                <input type="text" class="tab_text" name="DJJFMX_SFRY" maxlength="32"
	                                    value="${busRecord.DJJFMX_SFRY}" />
	                            </td>
	                            <td class="tab_width">收费日期：</td>
	                            <td>
	                                <%-- <input type="text" class="tab_text" name=DJJFMX_SFRQ maxlength="32"
	                                    value="${busRecord.DJJFMX_SFRQ}" /> --%>
	                                <input type="text" id="DJJFMX_SFRQ" name="DJJFMX_SFRQ"  
                                          onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
                                          class="tab_text Wdate" maxlength="60" style="width:160px;" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">费用申报意见：</td>
	                            <td colspan="3">
	                                <input type="text" class="tab_text" name="DJJFMX_FYSBYJ" maxlength="500"
	                                    style="width: 72%; float: left;" value="${busRecord.DJJFMX_FYSBYJ}" />                                
	                            </td>
	                        </tr>
	                        <%-- <tr id="djjfmxInfo_${param.domId}_btn" style="display:none;">
		                       <th colspan="4"> 
		                           <input type="button" class="eflowbutton" value="确定收费" onclick="submitDjjfxxInfo_${param.domId}();"/>
		                           <!-- <input type="button" class="eflowbutton" value="关闭" onclick="closeinfo('powerPeopleInfo');"/> -->
		                       </th>
		                    </tr> --%>
	                    </table>
	                </td>
	            </tr>
	        </table>
	        
	        <!-- 登记缴费明细记录  -->
	        <input type="hidden" name="DJJFMX_JSON" id="DJJFMX_JSON_${param.domId}"/>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="djjfmxInfo_${param.domId}_List">
	            <tr>
	                <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
	                <td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">缴费基数</td>
	                <td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">应收金额</td>
	                <td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">折扣后应收金额</td>
	                <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">收费人员</td>
	                <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">收费日期</td>
	                <td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">实收金额</td>
	                <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	            </tr>
	            <tr id="djjfmxInfo_${param.domId}_list_isblank">
	                <td colspan="8" style="text-align: center;">暂无登记缴费记录。</td>
	            </tr>
	        </table>
	        
	    </div>
	    <!-- 登记缴费明细结束 -->
    </c:if>
    
    <c:if test="${fn:contains(initDomShow,'djfzxx')}">
        <script>
           function submitDjfzxxInfo_${param.domId}(type){
			   var flag = validatePowerParams("djfzxx_${param.domId}","required");
			   if (!flag) {
				   return false;
			   }
                if($("#djfzxxInfo_${param.domId}_list_isblank")){
                    $("#djfzxxInfo_${param.domId}_list_isblank").remove();
                }
                var iteminfo = FlowUtil.getFormEleData("djfzxxInfo_${param.domId}");
                var trid = $("#djfzxxInfo_${param.domId}").attr("trid");
                if(type == '0' && ""!=trid && undefined != trid){
                    art.dialog.confirm("是否继续添加登记发证信息?", function() {
                        closeinfo_${param.domId}("djfzxxInfo_${param.domId}");  
                        addinfo_${param.domId}("djfzxxInfo_${param.domId}");  
                    });
                    return ;
                }
                if(""!=trid && undefined !=trid){
                    var i = trid.split("_")[1];
                    appendDjfzxxRow_${param.domId}(iteminfo,i,trid);
                    art.dialog({
                        content : "登记发证信息更新成功。",
                        icon : "succeed",
                        ok : true
                    });
                }else{
                    var index = 0;
                    if($("#djfzxxInfo_${param.domId}_List .djfzxxinfo_tr")){
                        index = $("#djfzxxInfo_${param.domId}_List .djfzxxinfo_tr").length;
                        if(index > 0){
                           var trid = $("#djfzxxInfo_${param.domId}_List .djfzxxinfo_tr").last().attr("id");
                           index =parseInt(trid.split("_")[1]);
                        }
                    }
                    index = index + 1;
                    appendDjfzxxRow_${param.domId}(iteminfo,index,null);
                }  
                if(type == '0'){
                    art.dialog.confirm("添加成功！是否继续添加缴费信息?", function() {
                        closeinfo_${param.domId}("djfzxxInfo_${param.domId}");   
                        addinfo_${param.domId}("djfzxxInfo_${param.domId}");  
                    });
                }      
            };
            
            function appendDjfzxxRow_${param.domId}(item,index,replaceTrid){
                if(item != "" && null != item) {
                    var html = "";
                    html += '<tr class="djfzxxinfo_tr" id="djfzxxinfotr_' + index + '_${param.domId}">';
                    html += '<input type="hidden" name="iteminfo"/>';
                    html += '<td style="text-align: center;">' + index + '</td>';
                    html += '<td style="text-align: center;">' + item.DJFZXX_FZRY + '</td>';
                    html += '<td style="text-align: center;">' + item.DJFZXX_FZSJ + '</td>';
                    html += '<td style="text-align: center;">' + item.DJFZXX_LZRXM + '</td>';
                    html += '<td style="text-align: center;">' + item.DJFZXX_FZMC + '</td>';
                    html += '<td style="text-align: center;">';
                    html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadDjfzxxInfo_${param.domId}(this,0);" id="djxxItem">查看</a>';
                    html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadDjfzxxInfo_${param.domId}(this,1);" id="djxxItem">编辑</a>';
                    html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr_${param.domId}(this);"></a></td>';
                    html += '</tr>';
                    if(replaceTrid){
                       var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
                       $("#"+replaceTrid).remove();
                       if(prevtrid){
                           $("#"+prevtrid).after(html)
                       } else{
                           $("#djfzxxInfo_${param.domId}_List").append(html);
                       }       
                    }else{
                       $("#djfzxxInfo_${param.domId}_List").append(html);
                    }
                    $("#djfzxxinfotr_" + index + "_${param.domId} input[name='iteminfo']").val(JSON.stringify(item));
                    $("#djfzxxInfo_${param.domId}").attr("trid","djfzxxinfotr_"+index+"_${param.domId}");
                }
            };
            
            function loadDjfzxxInfo_${param.domId}(obj,ptype){
                $("#djfzxxInfo_${param.domId}").attr("style","");
                var trid = $(obj).closest("tr").attr("id");
                $("#djfzxxInfo_${param.domId}").attr("trid",trid);
                var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
                var item = JSON.parse(iteminfo);
                FlowUtil.initFormFieldValue(item,"djfzxxInfo_${param.domId}");
                if(ptype == "0"){
                    //查看
                    //$("#djfzxxInfo_${param.domId}_btn").attr("style","display:none;");
                }else if(ptype == "1"){
                    //修改
                    //$("#djfzxxInfo_${param.domId}_btn").attr("style","");
                }
            };   
            
            function getDjfzxxJson_${param.domId}(){
               var datas = [];
               $("#djfzxxInfo_${param.domId}_List .djfzxxinfo_tr").each(function(){
                    var iteminfo = $(this).find("input[name='iteminfo']").val();
                    datas.push(JSON.parse(iteminfo));          
               });
               $("#DJFZXX_JSON_${param.domId}").val(JSON.stringify(datas));
               return datas;
            }; 
            
            function initDjfzxx_${param.domId}(items){
               if(items){
                   if($("#djfzxxInfo_${param.domId}_list_isblank")){
                      $("#djfzxxInfo_${param.domId}_list_isblank").remove();
                   }
                   
                   if($("#djfzxxInfo_${param.domId}_List .djfzxxinfo_tr")){
                     $("#djfzxxInfo_${param.domId}_List .djfzxxinfo_tr").remove();
                   }
                   
                   for(var i=0;i<items.length;i++){
                      appendDjfzxxRow_${param.domId}(items[i],(i+1),null);
                   }
                   $("#djfzxxInfo_${param.domId}").attr("style","");
                   var firstItem = $("#djfzxxInfo_${param.domId}_List .djfzxxinfo_tr")[0];
                   var id = $(firstItem).attr("id");
                   $("#djfzxxInfo_${param.domId}").attr("trid",id);
                   var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
                   if(iteminfo){
                        var item = JSON.parse(iteminfo);
                        FlowUtil.initFormFieldValue(item,"djfzxxInfo_${param.domId}");
                   }
                   //$("#djfzxxInfo_${param.domId}_btn").attr("style","display:none;");
               }
            }; 
            
        </script>
	    <!-- 登记发证信息开始 -->
	    <div id="djfzxx_${param.domId}">
	        <div class="tab_height"></div>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	            <tr>
	                <th>
	                    <span style="float:left;margin-left:10px;">登记发证信息</span>
	                    <a href="javascript:void(0);" style="float:right; margin: 2px 10px; padding: 0 20px;"
	                        class="eflowbutton" onclick="submitDjfzxxInfo_${domId}('1');">保存</a>
	                    <a href="javascript:void(0);" style="float:right; margin: 2px 10px; padding: 0 20px;"
	                        class="eflowbutton" onclick="submitDjfzxxInfo_${domId}('0');">添加</a>
	                </th>
	            </tr>
	        </table>
	
	        <div class="tab_height"></div>
	        <table class="tab_tk_pro2">
	            <tr>
	                <td style="width:80%;">
	                    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djfzxxInfo_${param.domId}" trid="">
	                        <tr>
	                            <td style="padding: 0;">
	                                <table class="tab_tk_pro2">
	                                    <tr>
	                                        <td class="tab_width">发证人员：</td>
	                                        <td>
	                                            <input type="text" class="tab_text" name="DJFZXX_FZRY" maxlength="32"
	                                                value="${busRecord.DJFZXX_FZRY}" readonly="readonly"/>
	                                        </td>
	                                        <td class="tab_width">发证时间：</td>
	                                        <td>
	                                            <input type="text" class="tab_text" name="DJFZXX_FZSJ" maxlength="32" readonly="readonly" 
	                                                value="${busRecord.DJFZXX_FZSJ}" />
	                                        </td>
	                                    </tr>
	
	                                    <tr>
	                                        <td class="tab_width">发证名称：</td>
	                                        <td>
	                                            <input type="text" class="tab_text" name="DJFZXX_FZMC" maxlength="128"
	                                                value="${busRecord.DJFZXX_FZMC}" />
	                                        </td>
	                                        <td class="tab_width">发证数量：</td>
	                                        <td>
	                                            <input type="text" class="tab_text" name="DJFZXX_FZSL" maxlength="32"
	                                                value="${busRecord.DJFZXX_FZSL}" />
	                                        </td>
	                                    </tr>
	
	                                    <tr>
	                                        <td class="tab_width"><font class="tab_color">*</font>领证人姓名：</td>
	                                        <td>
	                                            <input type="text" class="tab_text validate[required]" name="DJFZXX_LZRXM" maxlength="32"
	                                                value="${busRecord.DJFZXX_LZRXM}" />
	                                        </td>
	                                        <td class="tab_width"><font class="tab_color">*</font>领证人证件类型：</td>
	                                        <td>
	                                            <eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType" defaultEmptyValue="SF"
	                                                dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择领证人证件类型"
	                                                name="DJFZXX_LZRZJLX" value="${busRecord.DJFZXX_LZRZJLX}">
	                                            </eve:eveselect>
	                                        </td>
	                                    </tr>
	
	                                    <tr>
	                                        <td class="tab_width"><font class="tab_color">*</font>领证人证件号码：</td>
	                                        <td colspan="3">
	                                            <input type="text" class="tab_text validate[required]" name="DJFZXX_LZRZJHM" maxlength="128"
	                                                style="width: 72%; float: left;" value="${busRecord.DJFZXX_LZRZJHM}" />
	                                            <div>
	                                                <a href="javascript:void(0);" style="margin: 2px 5px; padding: 0 20px;"
	                                                    class="eflowbutton" onclick="DjxxLzrLegalRead();">读卡</a>
	                                            </div>
	                                        </td>
	                                    </tr>
	
	                                    <tr>
	                                        <td class="tab_width">领证人电话：</td>
	                                        <td>
	                                            <input type="text" class="tab_text" name="DJFZXX_LZRDH" maxlength="32"
	                                                value="${busRecord.DJFZXX_LZRDH}" />
	                                        </td>
	                                        <td class="tab_width">领证人邮编：</td>
	                                        <td>
	                                            <input type="text" class="tab_text" name="DJFZXX_LZREB" maxlength="32"
	                                                value="${busRecord.DJFZXX_LZREB}" />
	                                        </td>
	                                    </tr>
	
	                                    <tr>
	                                        <td class="tab_width">领证人地址：</td>
	                                        <td colspan="3">
	                                            <input type="text" class="tab_text" name="DJFZXX_LZRDZ" maxlength="128"
	                                                style="width: 72%;" value="${busRecord.DJFZXX_LZRDZ}" />
	                                        </td>
	                                    </tr>
	
	                                    <tr>
	                                        <td class="tab_width">发证备注：</td>
	                                        <td colspan="3">
	                                            <input type="text" class="tab_text" name="DJFZXX_FZBZ" maxlength="500"
	                                                style="width: 72%;" value="${busRecord.DJFZXX_FZBZ}" />
	                                        </td>
	                                    </tr>
	                                </table>
	                                <input type="hidden" name="trid" />
	                            </td>
	                        </tr>
	                       
	                    </table>
	                </td>
	
	                <!-- <td style="width:20%; padding: 0;">
	                    <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djfzxxImg">
	                        <tr id="djfzxxImg_1">
	                            <td style="padding: 0;">
	                                <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	                                    <tr>
	                                        <th>
	                                            <a href="javascript:void(0);" style="margin: auto; margin-left: 25%; padding: 0 40px;"
	                                                class="eflowbutton" onclick="addDjfzxx();">启动签证</a>
	                                        </th>
	                                    </tr>
	                                    <tr>
	                                        <td style="height: 200px; text-align:center;">
	                                        领证件人签名图片
	                                            <img src="images/2_11.gif" />
	                                        </td>
	                                    <tr>
	                                </table>
	                            </td>
	                        </tr>
	                    </table>
	                </td> -->
	            </tr>
	            <%-- <tr id="djfzxxInfo_${param.domId}_btn" style="display:none;">
	               <td colspan="2" style="text-align:center;vertical-align:middle;">
	                   <input type="button" class="eflowbutton" value="确定" onclick="submitDjfzxxInfo_${param.domId}();"/>
	               </td> 
	            </tr> --%>
	        </table>
	
	        <input name="DJFZXX_JSON" type="hidden" id="DJFZXX_JSON_${param.domId}"/>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="djfzxxInfo_${param.domId}_List">
	            <tr>
	                <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
	                <td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">发证人员</td>
	                <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">发证时间</td>
	                <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">领证人姓名</td>
	                <td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">发证名称</td>
	                <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	            </tr>
	            <tr id="djfzxxInfo_${param.domId}_list_isblank">
	                <td colspan="6" style="text-align: center;">暂无登记发证(领证)信息。</td>
	            </tr>
	        </table>
	    </div>
	    <!-- 登记发证信息结束 -->
    </c:if>
    
    <c:if test="${fn:contains(initDomShow,'djdaxx')}">
	    <!-- 登记归档信息开始 -->
	    <div id="djgdxx_${param.domId}">
	        <div class="tab_height"></div>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	            <tr>
	                <th><span style="float:left;margin-left:10px;">登记归档信息</span></th>
	            </tr>
	        </table>
	
	        <div class="tab_height"></div>
	        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="djgdxxInfo_${param.domId}">
	            <tr id="djgdxxInfo_1">
	                <td style="padding: 10px;">
	                    <table class="tab_tk_pro2">
	                        <tr>
	                            <td class="tab_width">卷宗号：</td>
	                            <td>
	                                <input type="text" class="tab_text validate[]" name="DJGDXX_JZH" maxlength="128"
	                                    value="${busRecord.DJGDXX_JZH}" />
	                            </td>
	                            <td class="tab_width">文件数：</td>
	                            <td>
	                                <input type="text" class="tab_text validate[]" name="DJGDXX_WJS" maxlength="32"
	                                    value="${busRecord.DJGDXX_WJS}" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">总页数：</td>
	                            <td>
	                                <input type="text" class="tab_text validate[]" name="DJGDXX_ZYS" maxlength="32"
	                                    value="${busRecord.DJGDXX_ZYS}" />
	                            </td>
	                            <td class="tab_width">操作人：</td>
	                            <td>
	                                <input type="text" class="tab_text validate[]" name="DJGDXX_CZR" maxlength="32"
	                                    value="${busRecord.DJGDXX_CZR}" />
	                            </td>
	                        </tr>
	
	                        <tr>
	                            <td class="tab_width">归档时间：</td>
	                            <td>
	                                <input type="text" class="tab_text validate[]" name="DJGDXX_GDSJ" maxlength="32"
	                                    value="${busRecord.DJGDXX_GDSJ}" />
	                            </td>
	                            <td></td>
	                            <td></td>
	                        </tr>
	                        <tr>
	                            <td class="tab_width">归档备注：</td>
	                            <td colspan="3">
	                                <input type="text" class="tab_text validate[]" style="width: 73%" name="DJGDXX_GDBZ"
	                                    value="${busRecord.DJGDXX_GDBZ}" maxlength="500" />
	                            </td>
	                        </tr>
	                    </table>
	                    <input type="hidden" name="trid" />
	                </td>                
	            </tr>
	        </table>
	    </div>
	    <!-- 登记归档信息开始 -->
    </c:if>
</div>