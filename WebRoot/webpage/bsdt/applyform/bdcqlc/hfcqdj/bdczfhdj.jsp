<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcQlcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    BdcQlcApplyService bdcQlcApplyService = (BdcQlcApplyService)AppUtil.getBean("bdcQlcApplyService");
	String ywId = request.getParameter("BDC_SUB_YWID");
	String hftype = request.getParameter("hftype");
	String itemcode = request.getParameter("ITEM_CODE");
	if(ywId != null && hftype != null){
		Map<String,Object> record = bdcQlcApplyService.getSubRecordInfo(itemcode,hftype,ywId);
		request.setAttribute("recordinfo", record);
	}
%>
<style>
.tab_text_1 {
    width: 350px;
    height: 24px;
    line-height: 24px;
    /* padding: 0 5px; */
    padding: 2px 3px 2px 1px;
    border: 1px solid #bbb;
}
</style>

<script type="text/javascript">

    function addinfo(id){
        $("#"+id).attr("style","");
        //$("#"+id+"_btn").attr("style","");
        $("#"+id+" input[type='text']").val('');
        $("#"+id+" select").val('');
    }
    
    function closeinfo(id){
        //$("#"+id).attr("style","display:none;");
        $("#"+id).attr("trid","");
    }
    
    function delDjxxTr(obj){
	    art.dialog.confirm("您确定要删除该记录吗?", function() {
	        $(obj).closest("tr").remove();
	    });
        
    }
    
    /*-------------------权利人信息相关开始--------------------------*/
    function validatePowerPeopleInfo(powerPeopleInfo){
        var flag = false;
        
        return flag;
    }
    
    function submitPowerPeopleInfo(type){
        if($("#powerPeopleInfoList_blank_tr")){
            $("#powerPeopleInfoList_blank_tr").remove();
        }
        var powerPeopleInfo = FlowUtil.getFormEleData("powerPeopleInfo");
        var trid = $("#powerPeopleInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加权利人信息?", function() {
                closeinfo("powerPeopleInfo");
                addinfo("powerPeopleInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendPowPeopleRow(powerPeopleInfo,i,trid);
            art.dialog({
                content : "权利人信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#powerPeopleInfoList .powerpeopleinfo_tr")){
                index = $("#powerPeopleInfoList .powerpeopleinfo_tr").length;
                if(index > 0){
                   var trid = $("#powerPeopleInfoList .powerpeopleinfo_tr").last().attr("id");
                   index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendPowPeopleRow(powerPeopleInfo,index,null);
        }  
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加权利人信息?", function() {
                closeinfo("powerPeopleInfo");
                addinfo("powerPeopleInfo");
            });
        }
    }
    
    function appendPowPeopleRow(item,index,replaceTrid){
        if(item != "" && null != item) {
	        var html = "";
	        html += '<tr class="powerpeopleinfo_tr" id="powerpeopleinfotr_' + index + '">';
	        html += '<input type="hidden" name="iteminfo"/>';
	        html += '<td style="text-align: center;">' + index + '</td>';
	        html += '<td style="text-align: center;">' + item.POWERPEOPLENAME + '</td>';
	        html += '<td style="text-align: center;">' + item.POWERPEOPLEIDNUM + '</td>';
	        if(item.POWERPEOPLESEX == "1"){
	           html += '<td style="text-align: center;">男</td>';
	        }else if(item.POWERPEOPLESEX == "2"){
	           html += '<td style="text-align: center;">女</td>';
	        }else{
	           html += '<td style="text-align: center;"></td>';
	        }
	        html += '<td style="text-align: center;">';
	        html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowPeopleInfo(this,0);" id="djxxItem">查看</a>';
	        html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowPeopleInfo(this,1);" id="djxxItem">编辑</a>';
	        html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
	        html += '</tr>';
	        if(replaceTrid){
	           var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
	           $("#"+replaceTrid).remove();
	           if(prevtrid){
	               $("#"+prevtrid).after(html)
	           } else{
	               $("#powerPeopleInfoList").append(html);
	           }       
	        }else{
	           $("#powerPeopleInfoList").append(html);
	        }
	        $("#powerpeopleinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
	        $("#powerPeopleInfo").attr("trid","powerpeopleinfotr_"+index);
	    }
    }
    
    function loadPowPeopleInfo(obj,ptype){
        $("#powerPeopleInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#powerPeopleInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"powerPeopleInfo");
        if(ptype == "0"){
            //查看
            $("#powerPeopleInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#powerPeopleInfo_btn").attr("style","");
        }
    }   
    
    function getPowPeopleJson_Page1(){
       var datas = [];
       $("#powerPeopleInfoList .powerpeopleinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#POWERPEOPLEINFO_JSON_Page1").val(JSON.stringify(datas));
       return datas;
    }
    
    function initPowPeople(items){
       if(items){
           if($("#powerPeopleInfoList_blank_tr")){
	          $("#powerPeopleInfoList_blank_tr").remove();
	       }
           for(var i=0;i<items.length;i++){
              appendPowPeopleRow(items[i],(i+1),null);
           }
           $("#powerPeopleInfo").attr("style","");
           var firstItem = $("#powerPeopleInfoList .powerpeopleinfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#powerPeopleInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           if(iteminfo){
                var item = JSON.parse(iteminfo);
                FlowUtil.initFormFieldValue(item,"powerPeopleInfo");
           }
           $("#powerPeopleInfo_btn").attr("style","display:none;");
       }
    }

    function initPowPeopleDb(items) {
		if (items) {
			if($("#powerPeopleInfoList_blank_tr")){
				$("#powerPeopleInfoList_blank_tr").remove();
			}
			for(var i=0;i<items.length;i++){
				$("#powerpeopleinfotr_" + (i + 1)).remove();
				appendPowPeopleRow(items[i],(i+1),null);
			}
			$("#powerPeopleInfo").attr("style","");
			var firstItem = $("#powerPeopleInfoList .powerpeopleinfo_tr")[0];
			var id = $(firstItem).attr("id");
			$("#powerPeopleInfo").attr("trid",id);
			var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
			if(iteminfo){
				var item = JSON.parse(iteminfo);
				FlowUtil.initFormFieldValue(item,"powerPeopleInfo");
			}
			$("#powerPeopleInfo_btn").attr("style","display:none;");
		}
	}

    
    function loadDataPowPeoplePage1(){
        var datastr = $("#POWERPEOPLEINFO_JSON_Page1").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowPeople(items);
        }
    }
    
    /*-------------------权利人信息相关结束--------------------------*/
    
    /*-------------------权属来源信息相关开始--------------------------*/
   function submitPowSourceInfo(){
        if($("#powerSourceInfoList_blank_tr")){
            $("#powerSourceInfoList_blank_tr").remove();
        }
        var powerPeopleInfo = FlowUtil.getFormEleData("powerSourceInfo");
        var trid = $("#powerSourceInfo").attr("trid");
        if(""!=trid){
            var i = trid.split("_")[1];
            appendPowSourceRow(powerPeopleInfo,i,trid);
        }else{
            var index = 0;
            if($("#powerSourceInfoList .powersourceinfo_tr")){
                index = $("#powerSourceInfoList .powerpeopleinfo_tr").length;
                if(index > 0){
                   var trid = $("#powerSourceInfoList .powersourceinfo_tr").last().attr("id");
                   index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendPowSourceRow(powerPeopleInfo,index,null);
        }        
        closeinfo("powerSourceInfo");
    }
    
    function appendPowSourceRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="powersourceinfo_tr" id="powersourceinfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.POWERSOURCE_QLRMC + '</td>';
            html += '<td style="text-align: center;">' + item.POWERSOURCE_ZJHM + '</td>';
            html += '<td style="text-align: center;">不动产权证书</td>';
            html += '<td style="text-align: center;">' + item.POWERSOURCE_DYH + '</td>';
            html += '<td style="text-align: center;">' + item.POWERSOURCE_QSWH + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,0);" id="djxxItem">查看</a>';
            //html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,1);" id="djxxItem">编辑</a>';
            //html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#powerSourceInfoList").append(html);
               }       
            }else{
               $("#powerSourceInfoList").append(html);
            }
            $("#powersourceinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
        }
    }
    
    function loadPowSourceInfo(obj,ptype){
        $("#powerSourceInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#powerSourceInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"powerSourceInfo");
        if(ptype == "0"){
            //查看
            $("#powerSourceInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#powerSourceInfo_btn").attr("style","");
        }
    }   
    
    function getPowSourceJson_Page1(){
       var datas = [];
       $("#powerSourceInfoList .powersourceinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#POWERSOURCEINFO_JSON_Page1").val(JSON.stringify(datas));
       return datas;
    }
    
    function initPowSource(items){
       if(items){
           if($("#powerSourceInfoList_blank_tr")){
	          $("#powerSourceInfoList_blank_tr").remove();
	       }
           for(var i=0;i<items.length;i++){
              appendPowSourceRow(items[i],(i+1),null);
           }
           $("#powerSourceInfo").attr("style","");
           var firstItem = $("#powerSourceInfoList .powersourceinfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#powerSourceInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           var item = JSON.parse(iteminfo);
           FlowUtil.initFormFieldValue(item,"powerSourceInfo");
           $("#powerSourceInfo_btn").attr("style","display:none;");
       }
    }
    
    function loadDataPowSourcePage1(){
        var datastr = $("#POWERSOURCEINFO_JSON_Page1").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowSource(items);
        }
    }
    
    /*-------------------权属来源信息相关结束--------------------------*/
    
</script>

<input type="hidden" name="POWERPEOPLEINFO_JSON" value='${recordinfo.POWERPEOPLEINFO_JSON}' id="POWERPEOPLEINFO_JSON_Page1"/>
<input type="hidden" name="POWERSOURCEINFO_JSON" value='${recordinfo.POWERSOURCEINFO_JSON}' id="POWERSOURCEINFO_JSON_Page1"/>
<%--不动产档案信息接口需回传字段 --%>
<input type="hidden" name="BDCDYH" value="${recordinfo.BDCDYH}"/>
<input type="hidden" name="ZDDM" value="${recordinfo.ZDDM}"/>
<input type="hidden" name="FWBM" value="${recordinfo.FWBM}"/>
<input type="hidden" name="YWH" value="${recordinfo.YWH}"/>
<input type="hidden" name="ZXYWH" value="${recordinfo.ZXYWH}"/>
<input type="hidden" name="GLH" value="${recordinfo.GLH}"/>

<div class="tab_height"></div>
 <div id="powerPeopleInfo" trid="">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	   <tr>
	       <th><span style="float:left;margin-left:10px">权利人信息</span>
	       <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="保存" onclick="submitPowerPeopleInfo('1');"/>
	       <input type="button" class="eflowbutton" style="float:right; margin: 2px 5px; padding: 0 20px;" value="添加" onclick="submitPowerPeopleInfo('0')"/>
	       </th>
	   </tr>    
	    <tr>
	        <td style="padding: 10px">
	            <table class="tab_tk_pro2"> 
	               <tr>
	                   <td class="tab_width"><font class="tab_color">*</font>权利人姓名：</td>
	                   <td><input type="text" class="tab_text validate[required]" 
	                       name="POWERPEOPLENAME" maxlength="62"/></td>
	                   <td class="tab_width">权利比例：</td>
	                   <td><input type="text" class="tab_text "
	                       name="POWERPEOPLEPRO" maxlength="62"/></td>
	               </tr>
	               <tr>
	                   <td class="tab_width">权利人性质：</td>
	                   <td>
	                       <eve:eveselect clazz="tab_text validate[]" dataParams="QLRXZ"
	                       dataInterface="dictionaryService.findDatasForSelect"
	                       defaultEmptyText="选择权利人性质" name="POWERPEOPLENATURE" id="POWERPEOPLENATURE" >
	                       </eve:eveselect>
	                   </td>
	                   <td class="tab_width">性别：</td>
	                   <td>
	                       <eve:eveselect clazz="tab_text"
	                                       dataParams="sex"
	                                       dataInterface="dictionaryService.findDatasForSelect"
	                                       defaultEmptyText="选择性别" name="POWERPEOPLESEX" id="POWERPEOPLESEX"></eve:eveselect>
	                   </td>
	               </tr>
	               <tr>
	                   <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
	                   <td>
	                     <eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
	                       dataInterface="dictionaryService.findDatasForSelect"
	                       defaultEmptyText="选择证件类型" name="POWERPEOPLEIDTYPE" id="POWERPEOPLEIDTYPE" >
	                       </eve:eveselect>
	                   </td>
	                   <td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
	                   <td id="test"><input type="text" class="tab_text validate[required]" maxlength="30" id="POWERPEOPLEIDNUM" style="float: left;"
	                       name="POWERPEOPLEIDNUM"  /><a href="javascript:void(0);" onclick="PowerPeopleRead(this);" class="eflowbutton">身份证读卡</a></td>
	               </tr>
	               <tr>                    
	                   <td class="tab_width">电话</td>
	                   <td>
	                   <input type="text" class="tab_text validate[]" 
	                       name="POWERPEOPLEMOBILE" maxlength="11"/>
	                   </td>
	                   <td class="tab_width">邮政编码：</td>
	                   <td><input type="text" class="tab_text validate[,custom[chinaZip]]" 
	                       name="POWERPEOPLEPOSTCODE" maxlength="6"/></td>
	               </tr>
	               <tr>                    
	                   <td class="tab_width">地址：</td>
	                   <td colspan="3"><input type="text" class="tab_text validate[]" 
	                       name="POWERPEOPLEADDR" maxlength="120" style="width:60%"/></td>
	                   
	               </tr>
	               <tr>                    
	                   <td class="tab_width">法定代表人姓名：</td>
	                   <td colspan="3"><input type="text" class="tab_text validate[]" 
	                       name="POWLEGALNAME" maxlength="62"/></td>
	               </tr>
	               <tr>
	                   <td class="tab_width">证件类型：</td>
	                   <td>
	                       <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
	                       dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
	                       defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE" >
	                       </eve:eveselect>
	                   </td>
	                   <td class="tab_width">证件号码：</td>
	                   <td>
	                     <input type="text" class="tab_text validate[]" maxlength="30" id="POWLEGALIDNUM" style="float: left;"
	                       name="POWLEGALIDNUM"  /><a href="javascript:void(0);" onclick="PowLegalRead(this);" class="eflowbutton">身份证读卡</a>
	                   </td>
	               </tr>
	               <tr>                    
	                   <td class="tab_width">代理人姓名：</td>
	                   <td colspan="3"><input type="text" class="tab_text validate[]" 
	                       name="POWAGENTNAME" maxlength="62"/></td> 
	                   
	               </tr>
	               <tr>
	                   <td class="tab_width">证件类型：</td>
	                   <td>
	                       <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
	                       dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
	                       defaultEmptyText="选择证件类型" name="POWAGENTIDTYPE" id="POWAGENTIDTYPE" >
	                       </eve:eveselect>
	                   </td>
	                   <td class="tab_width">证件号码：</td>
	                   <td>
	                     <input type="text" class="tab_text validate[]" maxlength="30" id="POWAGENTIDNUM" style="float: left;"
	                       name="POWAGENTIDNUM"  /><a href="javascript:void(0);" onclick="PowAgentRead(this);" class="eflowbutton">身份证读卡</a>
	                   </td>
	               </tr>
	               <tr name="bdcqzh_tr" style="display:none;">
                       <td class="tab_width">缮证证号：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" value="${busRecord.BDC_SZZH}"
                           name="BDC_SZZH"  style="width:60%" placeholder="【确认登簿】后调用不动产登记系统登簿接口，返回不动产登记证明号。"/>
                           <input type="button" class="eflowbutton disabledButton" id="ydb1" style="background:red;color:#fff;" value="预登簿" />
                           <input type="button" class="eflowbutton disabledButton" id="qrdb1" onclick="page1Dbcz();" style="background:red;color:#fff;" value="确认登簿" />
                           <input type="button" class="eflowbutton disabledButton" onclick="bdcHfcqdjCQZSprint1(1);" id="zsyl1" style="background:red;color:#fff;" value="证书预览" />
						   <input type="button" class="eflowbutton disabledButton" onclick="bdcHfcqdjCQZSprint1(2);" id="zsdy1" style="background:red;color:#fff;" value="证书打印" />
                       </td>                        
                   </tr>
                   <tr>                    
                       <td class="tab_width">权证标识码：</td>
                       <td colspan="3"><input type="text" class="tab_text validate[]" value="${busRecord.BDC_QZBSM}"
                           name="BDC_QZBSM" style="width:60%" placeholder="由内网不动产登记系统完成缮证（打证），通过接口将权证标识码推送至审批平台。"/>
                       </td>                        
                   </tr>
                   <tr name="bdcczr_tr" style="display:none;">                    
                       <td class="tab_width">操作人：</td>
                       <td>
                       <input type="text" class="tab_text validate[]" value="${busRecord.BDC_CZR}"
                           name="BDC_CZR" maxlength="11"/>
                       </td>
                       <td class="tab_width">操作时间：</td>
                       <td><input type="text" class="tab_text validate[]" value="${busRecord.BDC_CZSJ}"
                           name="BDC_CZSJ" maxlength="6"/></td>
                   </tr>
	               <tr>
	                   <td class="tab_width">备注：</td>
	                   <td colspan="3" class="tab_width"><input type="text" class="tab_text" 
	                       name="POWREMARK" value="${busRecord.POWREMARK}" style="width: 60%;"  />
	                   </td>
	               </tr>
	               <!-- <tr id="powerPeopleInfo_btn" style="display:none;">
			           <th colspan="4"> 
			               <input type="button" class="eflowbutton" value="确定" onclick="submitPowerPeopleInfo();"/>
			           </th>
			        </tr> -->
	            </table>
	        </td>
	        <!-- <td>
	            <input type="button" class="eflowbutton" name="deleteCurrentPowerSourceInfo" value="删除" onclick="deletePowerSourceInfo('1');">
	        </td> -->
	    </tr>	    
	</table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="powerPeopleInfoList">    
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利人名称</td>
        <td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">证件人号码</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">性别</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="powerPeopleInfoList_blank_tr">
       <td colspan="5" style="text-align: center;">暂无权利人信息，请添加！</td>
    </tr>
</table>

<div class="tab_height"></div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="powerSourceInfoList">
    <tr>
       <th colspan="7"><span style="float:left;margin-left:10px">权属来源</span><!-- <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="addinfo('powerSourceInfo');"/> --></th>
   </tr>
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利人</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">证件人号码</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">权属来源类型</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">证书（文号）</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="powerSourceInfoList_blank_tr">
       <td colspan="7" style="text-align: center;">暂无权属来源信息，请通过不动产档案查询添加！</td>
    </tr>
</table>
<div id="powerSourceInfo" trid=""> 
	<table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
		<tr>
			<td style="padding: 10px">
				<table class="tab_tk_pro2">
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>权属来源类型：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="QSLYLX" value="3"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
							defaultEmptyText="选择权属来源类型" name="POWERSOURCE_QSLYLX" id="POWERSOURCE_QSLYLX">
							</eve:eveselect>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>证书（文号）：</td>
						<td>
						  <input type="text" class="tab_text validate[required]" maxlength="30" style="float: left;" 
							name="POWERSOURCE_QSWH" id="POWERSOURCE_QSWH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">不动产单元号：</td>
						<td>
						  <input type="text" class="tab_text " maxlength="30" style="float: left;" 
							name="POWERSOURCE_DYH" id="POWERSOURCE_DYH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>权利人：</td>
						<td>
						    <input type="text" class="tab_text validate[required]" maxlength="30" style="float: left;" 
							name="POWERSOURCE_QLRMC" id="POWERSOURCE_QLRMC" />
							<%-- <eve:eveselect clazz="tab_text " dataParams="QSZT"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
							defaultEmptyText="选择权利人" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE" >
							</eve:eveselect> --%>
						</td>
						<td class="tab_width"><font class="tab_color">*</font>权属状态：</td>
						<td>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="DYQSZT"
							dataInterface="dictionaryService.findDatasForSelect" 
							defaultEmptyText="选择产权状态" name="POWERSOURCE_QSZT" id="POWERSOURCE_QSZT">
							</eve:eveselect>
						</td>
					</tr>
					<tr>
						<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
						<td>
							<!-- <input type="text" class="tab_text validate[required]" maxlength="30" id="POWERSOURCE_ZJLX" style="float: left;"
							name="POWERSOURCE_ZJLX"   readonly="readonly"/>  -->
							<eve:eveselect clazz="tab_text " dataParams="DYZJZL"
							dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
							defaultEmptyText="选择证件类型" name="POWERSOURCE_ZJLX" id="POWERSOURCE_ZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">证件号码：</td>
						<td>
						  <input type="text" class="tab_text "  maxlength="30" id="POWERSOURCE_ZJHM" style="float: left;"
							name="POWERSOURCE_ZJHM"  /><a href="javascript:void(0);" onclick="powSrcRead(this);" class="eflowbutton">身份证读卡</a>
						</td>
					</tr>
					<tr>					
						<td class="tab_width">宗地代码：</td>
						<td ><input type="text" class="tab_text validate[]" 
							name="POWERSOURCE_ZDDM"  id="POWERSOURCE_ZDDM"  maxlength="62" /></td>
						<td class="tab_width">权利开始时间：</td>
						<td >
							<input type="text" id="POWERSOURCE_EFFECT_TIME" name="POWERSOURCE_EFFECT_TIME" 
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'POWERSOURCE_CLOSE_TIME1\')}'})" 
								class="tab_text Wdate" maxlength="60" style="width:160px;" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">权利结束时间1：</td>
						<td>
							<input type="text" id="POWERSOURCE_CLOSE_TIME1" name="POWERSOURCE_CLOSE_TIME1"  
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'POWERSOURCE_EFFECT_TIME\')}'})" 
								class="tab_text Wdate" maxlength="60" style="width:160px;" />
						</td>
						<td class="tab_width">权利结束时间2：</td>
						<td>
							<input type="text" id="POWERSOURCE_CLOSE_TIME2" name="POWERSOURCE_CLOSE_TIME2"  
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'POWERSOURCE_EFFECT_TIME\')}'})" 
								class="tab_text Wdate" maxlength="60" style="width:160px;" />
						</td>					
					</tr>
					<tr>
						<td class="tab_width">备注：</td>
						<td colspan="3"><input type="text" class="tab_text" maxlength="60" 
							name="POWERSOURCE_BZ" id="POWERSOURCE_BZ" style="width: 72%;"  />
						</td>
					</tr>
					<tr id="powerSourceInfo_btn" style="display:none;">
                       <th colspan="4"> 
                           <input type="button" class="eflowbutton" value="确定" onclick="submitPowSourceInfo();"/>
                           <input type="button" class="eflowbutton" value="关闭" onclick="closeinfo('powerSourceInfo');"/>
                       </th>
                    </tr>
				</table>				
			</td>			
		</tr>
	</table>
</div>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='zdjbxx'>
	<tr>
		<th><span style="float:left;margin-left:10px">宗地基本信息</span></th>
	</tr>	
	<tr>
	   <td style="padding: 10px">
	       <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
			    <tr>
			        <td class="tab_width">宗地代码：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="19" id="ZD_BM" style="float: left;"
			            name="ZD_BM" readonly="readonly" value="${recordinfo.ZD_BM}" />
			        </td>   
			        <td class="tab_width">权利类型：</td>
			        <td>
			                <eve:eveselect clazz="tab_text " dataParams="ZDQLLX"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择权利类型" name="ZD_QLLX" id="ZD_QLLX" value="${recordinfo.ZD_QLLX}"> 
			                </eve:eveselect>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">宗地特征码：</td>
			        <td>
						<eve:eveselect clazz="tab_text " dataParams="zdtzm"
									   dataInterface="dictionaryService.findDatasForSelect"
									   defaultEmptyText="选择宗地特征码" name="ZD_TZM" id="ZD_TZM" value="${busRecord.ZD_TZM}">
						</eve:eveselect>
			        </td>   
			        <td class="tab_width">权利设定方式：</td>
			        <td>
			                <eve:eveselect clazz="tab_text " dataParams="ZDQLSDFS"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择权利设定方式" name="ZD_QLSDFS" id="ZD_QLSDFS" value="${recordinfo.ZD_QLSDFS}" > 
			                </eve:eveselect>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">坐落：</td>
			        <td colspan="3"><input type="text" class="tab_text" maxlength="60"
			            name="ZD_ZL" value="${busRecord.ZD_ZL}" style="width: 72%;" readonly="readonly" />
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">宗地面积：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="ZD_MJ" style="float: left;"
			            name="ZD_MJ" value="${recordinfo.ZD_MJ}" readonly="readonly"/>
			        </td>   
			        <td class="tab_width">土地用途：</td>
			        <td>
			                <eve:eveselect clazz="tab_text " dataParams="GHYT"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择土地用途" name="ZD_TDYT" id="ZD_TDYT" value="${recordinfo.ZD_TDYT}"> 
			                </eve:eveselect>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">用途说明：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="ZD_YTSM" style="float: left;"
			            name="ZD_YTSM" value="${recordinfo.ZD_YTSM}" readonly="readonly"/>
			        </td>   
			        <td class="tab_width">权利性质：</td>
			        <td>
			                <%-- <eve:eveselect clazz="tab_text " dataParams="DocumentType"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择权利性质" name="ZD_QLXZ" id="ZD_QLXZ" value="${recordinfo.ZD_QLXZ}"> 
			                </eve:eveselect> --%>
			                <input class="easyui-combobox tab_text" name="ZD_QLXZ" id="ZD_QLXZ" value="${recordinfo.ZD_QLXZ}" readonly="readonly"
			                                data-options="
			                                    prompt : '请选择选择权利性质',
			                                    url: 'bdcApplyController/fluidComboBox.do',
			                                    method: 'get',
			                                    valueField:'value',
			                                    textField:'text',
			                                    groupField:'group',
			                                    editable:false
			                                ">
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">等级：</td>
			            <td>
			                <eve:eveselect clazz="tab_text " dataParams="ZDLEVEL"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择等级" name="ZD_LEVEL" id="ZD_LEVEL" value="${recordinfo.ZD_LEVEL}"> 
			                </eve:eveselect>
			        </td>
			        <td class="tab_width">容积率：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="ZD_RJL" style="float: left;"
			            name="ZD_RJL" value="${recordinfo.ZD_RJL}" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">建筑限高（米）：</td>
			            <td>
			                <input type="text" class="tab_text validate[custom[number]]" maxlength="30" id="ZD_JZXG" style="float: left;"
			            name="ZD_JZXG" value="${recordinfo.ZD_JZXG}" readonly="readonly" />
			        </td>
			        <td class="tab_width">建筑密度：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="ZD_JZMD" style="float: left;"
			            name="ZD_JZMD" value="${recordinfo.ZD_JZMD}" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">宗地四至_东：</td>
			            <td>
			                <input type="text" class="tab_text " maxlength="30" id="ZD_E" style="float: left;"
			            name="ZD_E" value="${recordinfo.ZD_E}" readonly="readonly"/>
			        </td>
			        <td class="tab_width">宗地四至_南：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="ZD_S" style="float: left;"
			            name="ZD_S" value="${recordinfo.ZD_S}" readonly="readonly" />
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">宗地四至_西：</td>
			            <td>
			                <input type="text" class="tab_text " maxlength="30" id="ZD_W" style="float: left;"
			            name="ZD_W" value="${recordinfo.ZD_W}" readonly="readonly"/>
			        </td>
			        <td class="tab_width">宗地四至_北：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="ZD_N" style="float: left;"
			            name="ZD_N" value="${recordinfo.ZD_N}" readonly="readonly"/>
			        </td>
			    </tr>
			</table>
	   </td>
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='gyqlxx'>
	<tr>
		<th><span style="float:left;margin-left:10px">国有土地使用权-权利信息</span></th>
	</tr>	
	<tr>
	   <td style="padding: 10px;">
	       <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
			    <tr>
			        <td class="tab_width">权利开始时间：</td>
			        <td>
			            <input type="text" id="GYTD_BEGIN_TIME" name="GYTD_BEGIN_TIME" value="${recordinfo.GYTD_BEGIN_TIME}"
			                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'GYTD_END_TIME1\')}'})" 
			                 class="tab_text Wdate" maxlength="60" style="width:160px;" readonly="readonly" />
			        </td>
			        <td class="tab_width">权利结束时间1：</td>
			        <td>
			            <input type="text" id="GYTD_END_TIME1" name="GYTD_END_TIME1" value="${recordinfo.GYTD_END_TIME1}"
			                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})" 
			               class="tab_text Wdate" maxlength="60" style="width:160px;" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">权利结束时间2：</td>
			        <td>
			            <input type="text" id="GYTD_END_TIME2" name="GYTD_END_TIME2" value="${recordinfo.GYTD_END_TIME2}"
			                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})" 
			             class="tab_text Wdate" maxlength="60" style="width:160px;" />
			        </td>
			        <td class="tab_width">权利结束时间3：</td>
			        <td>
			            <input type="text" id="GYTD_END_TIME3" name="GYTD_END_TIME3" value="${recordinfo.GYTD_END_TIME3}"
			                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'GYTD_BEGIN_TIME\')}'})" 
			                class="tab_text Wdate" maxlength="60" style="width:160px;" />
			        </td>                   
			    </tr>
			    <tr>
			        <td class="tab_width">共有方式：</td>
			        <td style="width:430px">
			            <eve:eveselect clazz="tab_text " dataParams="GYFS" value="${recordinfo.GYTD_GYFS}"
			            dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			            defaultEmptyText="选择共有方式" name="GYTD_GYFS" id="GYTD_GYFS">
			            </eve:eveselect>
			        </td>
			        <td class="tab_width">取得价格（万元）：</td>
			        <td style="width:430px">
			            <input type="text" class="tab_text validate[custom[number]]" value="${recordinfo.GYTD_QDJG}"
			                   name="GYTD_QDJG" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">使用权面积：</td>
			        <td><input type="text" class="tab_text " value="${recordinfo.GYTD_SYQMJ}"
			                   name="GYTD_SYQMJ" readonly="readonly"/></td>
			        <td class="tab_width">权属状态：</td>
			        <td><eve:eveselect clazz="tab_text" dataParams="DYQSZT"
			            dataInterface="dictionaryService.findDatasForSelect" value="${recordinfo.GYTD_QSZT}" 
			            defaultEmptyText="选择权属状态" name="GYTD_QSZT" id="GYTD_QSZT">
			            </eve:eveselect>
			        </td>
			    </tr>  
			    <tr>
                    <td class="tab_width">登簿人：</td>
                    <td><input type="text" class="tab_text " value="${recordinfo.GYTD_DBRMC}"
                               name="GYTD_DBRMC" readonly="readonly"/></td>
                    <td class="tab_width">登记时间：</td>
                    <td><input type="text" class="tab_text " value="${recordinfo.GYTD_DJRQ}"
                               name="GYTD_DJRQ" readonly="readonly"/>
                    </td>
                </tr> 
                <tr>
                    <td class="tab_width">登记原因：</td>
                    <td colspan="3"><input type="text" class="tab_text" maxlength="60" 
                        name="GYTD_DJYY" style="width: 72%;"  />
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">附记：</td>
                    <td colspan="3"><input type="text" class="tab_text" maxlength="60" 
                        name="GYTD_FJ" style="width: 72%;"  />
                    </td>
                </tr>            
			</table>
	   </td>
	</tr>				
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='fwjbxx'>
	<tr>
		<th><span style="float:left;margin-left:10px">房屋基本信息</span></th>
	</tr>
	<tr>
	   <td style="padding: 10px">
	       <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
			    <tr>
			        <td class="tab_width">房地坐落：</td>
			            <td>
			                <input type="text" class="tab_text " maxlength="30" id="FW_ZL" style="float: left;"
			            name="FW_ZL" value="${recordinfo.FW_ZL}" readonly="readonly"/>
			        </td>
			        <td class="tab_width">幢号：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="FW_ZH" style="float: left;"
			            name="FW_ZH" value="${recordinfo.FW_ZH}" readonly="readonly"/>
			        </td>
			    </tr>   
			    <tr>
			        <td class="tab_width">所在层：</td>
			            <td>
			                <input type="text" class="tab_text " maxlength="30" id="FW_SZC" style="float: left;"
			            name="FW_SZC" value="${recordinfo.FW_SZC}" readonly="readonly"/>
			        </td>
			        <td class="tab_width">户号：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="FW_HH" style="float: left;"
			            name="FW_HH" value="${recordinfo.FW_HH}" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">总层数：</td>
			        <td>
			          <input type="text" class="tab_text" maxlength="30" id="FW_ZCS" style="float: left;"
			            name="FW_ZCS" value="${recordinfo.FW_ZCS}" readonly="readonly"/>
			        </td>   
			        <td class="tab_width">规划用途：</td>
			        <td>
			                <eve:eveselect clazz="tab_text" dataParams="GHYT"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择规划用途" name="FW_GHYT" id="FW_GHYT" value="${recordinfo.FW_GHYT}"> 
			                </eve:eveselect>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">竣工时间：</td>
			            <td>
			                <input type="text" id="FW_JGSJ" name="FW_JGSJ" 
			                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
			                class="tab_text_1 Wdate" maxlength="60" style="width:160px;" />
			        </td>
			        <td class="tab_width">用途说明：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="FW_YTSM" style="float: left;"
			            name="FW_YTSM" value="${recordinfo.FW_YTSM}" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">房屋性质：</td>
			        <td>
			            <eve:eveselect clazz="tab_text" dataParams="FWXZ"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择房屋性质" name="FW_XZ" id="FW_XZ" value="${recordinfo.FW_XZ}"> 
			                </eve:eveselect>
			        </td>   
			        <td class="tab_width">房屋结构：</td>
			        <td>
			                <eve:eveselect clazz="tab_text" dataParams="FWJG"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择房屋结构" name="FW_FWJG" id="FW_FWJG" value="${recordinfo.FW_FWJG}" > 
			                </eve:eveselect>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">交易价格：</td>
			            <td>
			                <input type="text" class="tab_text " maxlength="30" id="FW_JYJG" style="float: left;"
			            name="FW_JYJG" value="${recordinfo.FW_JYJG}" readonly="readonly"/>
			        </td>
			        <td class="tab_width">独用土地面积：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="FW_DYDYTDMJ" style="float: left;"
			            name="FW_DYDYTDMJ" value="${recordinfo.FW_DYDYTDMJ}" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">分摊土地面积：</td>
			            <td>
			                <input type="text" class="tab_text " maxlength="30" id="FW_FTTDMJ" style="float: left;"
			            name="FW_FTTDMJ" value="${recordinfo.FW_FTTDMJ}" readonly="readonly"/>
			        </td>
			        <td class="tab_width">建筑面积：</td>
			        <td>
			          <input type="text" class="tab_text " maxlength="30" id="FW_JZMJ" style="float: left;"
			            name="FW_JZMJ" value="${recordinfo.FW_JZMJ}" readonly="readonly"/>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">房屋共有情况：</td>
			        <td>
			                <eve:eveselect clazz="tab_text" dataParams="GYFS"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择房屋共有情况" name="FW_GYQK" id="FW_GYQK" value="${recordinfo.FW_GYQK}"> 
			                </eve:eveselect>
			        </td>
			        <td class="tab_width">专有建筑面积：</td>
			        <td>
			          <input type="text" class="tab_text" maxlength="30" id="FW_ZYJZMJ" style="float: left;"
			            name="FW_ZYJZMJ" value="${recordinfo.FW_ZYJZMJ}" readonly="readonly"/>
			        </td>   
			    </tr>
			    <tr>
			        <td class="tab_width">分摊建筑面积：</td>
			        <td>
			          <input type="text" class="tab_text" maxlength="30" id="FW_FTJZMJ" style="float: left;"
			            name="FW_FTJZMJ" value="${recordinfo.FW_FTJZMJ}" readonly="readonly"/>
			        </td>   
			        <td class="tab_width">权利类型：</td>
			        <td>
			                <eve:eveselect clazz="tab_text" dataParams="ZDQLLX"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="选择权利类型" name="FW_QLLX" id="FW_QLLX" value="${recordinfo.FW_QLLX}"> 
			                </eve:eveselect>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">土地使用权人：</td>
			        <td>
			          <input type="text" class="tab_text" maxlength="30" id="FW_TDSYQR" style="float: left;"
			            name="FW_TDSYQR" value="${recordinfo.FW_TDSYQR}" />
			        </td>   
			        <td class="tab_width">是否单登记房产：</td>
			        <td>
			                <eve:eveselect clazz="tab_text" dataParams="YesOrNo"
			                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			                defaultEmptyText="--请选择--" name="FW_SFDJ" id="FW_SFDJ" value="${recordinfo.FW_SFDJ}"> 
			                </eve:eveselect>
			        </td>
			    </tr>
			    <tr>
			        <td class="tab_width">登记时间：</td>
                    <td><input type="text" class="tab_text " value="${recordinfo.FW_DJRQ}"
                               name="FW_DJRQ" />
                    </td>
                    <td class="tab_width">登簿人：</td>
                    <td><input type="text" class="tab_text " value="${recordinfo.FW_DBRMC}"
                               name="FW_DBRMC" /></td>                    
                </tr> 
                <tr>
                    <td class="tab_width">登记原因：</td>
                    <td colspan="3"><input type="text" class="tab_text" maxlength="80" 
                        name="FW_DJYY"  style="width: 72%;"  value="${recordinfo.FW_DJYY}"/>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">附记：</td>
                    <td colspan="3"><input type="text" class="tab_text" maxlength="80" 
                        name="FW_FJ" style="width: 72%;" value="${recordinfo.FW_FJ}" />
                    </td>
                </tr>
			</table>
	   </td>
	</tr>
</table>

<%--    &lt;%&ndash; 引入登记审核、缴费信息、发证、归档信息 &ndash;%&gt;--%>

    <!-- djshxx:登记审核信息,djjfxx:登记缴费信息,djfzxx:登记发证信息,djdaxx:登记归档信息 -->
    <!-- optype:默认0标识可编辑；1：表示查看不可编辑暂定 -->
    <jsp:include page="./../common/djauditinfo.jsp">
        <jsp:param value="Page1" name="domId" />
        <jsp:param value="djshxx,djjfxx,djfzxx,djdaxx" name="initDomShow" />
    </jsp:include>
