<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    BdcApplyService bdcApplyService = (BdcApplyService)AppUtil.getBean("bdcApplyService");
	String ywId = request.getParameter("BDC_SUB_YWID");
	String hftype = request.getParameter("hftype");
	if(ywId != null && hftype != null){
		Map<String,Object> record = bdcApplyService.getSubRecordInfo(hftype, ywId);
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

<script>
    function submitPowSourceInfo(type){

        var flag = validatePowerParams("powerSourceInfo","required");

        if (!flag) {
            return false;
        }

        if($("#powerSourceInfoList_blank_tr")){
            $("#powerSourceInfoList_blank_tr").remove();
        }
        var powerSourceInfo = FlowUtil.getFormEleData("powerSourceInfo");
        var trid = $("#powerSourceInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加权属来源信息?", function() {
                closeinfo("powerSourceInfo");
                addinfo("powerSourceInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendPowSourceRow(powerSourceInfo,i,trid);
            art.dialog({
                content : "权属来源信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#powerSourceInfoList .powersourceinfo_tr")){
                index = $("#powerSourceInfoList .powersourceinfo_tr").length;
                if(index > 0){
                   var trid = $("#powerSourceInfoList .powersourceinfo_tr").last().attr("id");
                   index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendPowSourceRow(powerSourceInfo,index,null);
        } 
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加权属来源信息?", function() {
                closeinfo("powerSourceInfo");
                addinfo("powerSourceInfo");
            });
        }       
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
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,0);" id="djxxItem'+index+'">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,1);" >编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
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
            /*若item中没有法定代表人字段，则新增法定代表人，代理人字段*/
            if (!item['POWERSOURCE_FDDBR']) {
                item['POWERSOURCE_FDDBR'] = '';
            }
            if (!item['POWERSOURCE_FDDBR_TEL']) {
                item['POWERSOURCE_FDDBR_TEL'] = '';
            }
            if (!item['POWERSOURCE_DLR']) {
                item['POWERSOURCE_DLR'] = '';
            }
            if (!item['POWERSOURCE_DLR_TEL']) {
                item['POWERSOURCE_DLR_TEL'] = '';
            }
            if (!item['POWERSOURCE_DLJGMC']) {
                item['POWERSOURCE_DLJGMC'] = '';
            }

            $("#powersourceinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#powerSourceInfo").attr("trid","powersourceinfotr_"+index);
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
    
    function getPowSourceJson(){
       var datas = [];
       $("#powerSourceInfoList .powersourceinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#POWERSOURCEINFO_JSON").val(JSON.stringify(datas));
       return datas;
    }
    
    function initPowSource(items){
        $(".powersourceinfo_tr").each(function (index) {
            $(this).remove();
        });
        $("#powerSourceInfo").attr("trid", "");
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
           if (item.POWERSOURCE_SDTXLY && item.POWERSOURCE_SDTXLY != "") {
               $("#sdtxly").show();
           } else {
               $("#sdtxly").hide();
           }
           $("#powerSourceInfo_btn").attr("style","display:none;");
       }
    }
    
    function loadDataPowSource(){
        var datastr = $("#POWERSOURCEINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowSource(items);
        }
    }

    /**
     * 权属来源权利人等多条编辑的公用方法
     */
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
    
    function fillInQsly() {
        $("#powerSourceInfo select , #powerSourceInfo input").each(function () {
            $(this).attr("disabled", false);
        });
        $("#sdtxly").show();
        $("#sdtxFont").show();
        $("input[name='POWERSOURCE_SDTXLY']").addClass(" validate[required]");
    }
</script>

<input type="hidden" name="POWERPEOPLEINFO_JSON" value="${recordinfo.POWERPEOPLEINFO_JSON}" id="POWERPEOPLEINFO_JSON"/>
<input type="hidden" name="POWERSOURCEINFO_JSON" value="${recordinfo.POWERSOURCEINFO_JSON}" id="POWERSOURCEINFO_JSON"/>
<%--不动产档案信息接口需回传字段 --%>
<input type="hidden" name="BDCDYH" value="${recordinfo.BDCDYH}"/>
<input type="hidden" name="ZDDM" value="${recordinfo.ZDDM}"/>
<input type="hidden" name="FWBM" value="${recordinfo.FWBM}"/>
<input type="hidden" name="YWH" value="${recordinfo.YWH}"/>
<input type="hidden" name="ZXYWH" value="${recordinfo.ZXYWH}"/>
<input type="hidden" name="GLH" value="${recordinfo.GLH}"/>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerPeopleInf1">
	<tr>
		<th >权利人信息</th>
		<td> <input type="button" class="eflowbutton"  name="addOnePowerPeopleInf1" value="新增" onclick="addPowerPeopleInf1();"> </td>
	</tr>
	<tr id="powerPeopleInf1_1">
		<td >
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权利人姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="POWERPEOPLENAME" maxlength="62"/></td>
					<td class="tab_width">权利比例：</td>
					<td ><input type="text" class="tab_text "
						name="POWERPEOPLEPRO" maxlength="62"/></td>
				</tr>
				<tr>
					<td class="tab_width">权利人性质：</td>
					<td class="tab_width">
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
					<td>证件号码：</td>
					<td id="test"><input type="text" class="tab_text validate[]" maxlength="30" id="POWERPEOPLEIDNUM" style="float: left;"
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
						name="POWERPEOPLEADDR" maxlength="120" style="width:72%"/></td>
					
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
				<tr>
					<td class="tab_width">备注：</td>
					<td colspan="3"><input type="text" class="tab_text" maxlength="60"
						name="POWREMARK" value="${busRecord.POWREMARK}" style="width: 72%;"  />
					</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" class="eflowbutton" name="deleteCurrentPowerPeopleInf1" value="删除" onclick="deletePowerPeopleInf1('1');">
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="powerSourceInfo">
	<tr>
		<th >权属来源
	        <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="查询不动产档案信息" onclick="showPageSelectorForQsly();"/>
        	<input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitPowSourceInfo('1');"/>
        	<input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitPowSourceInfo('0');"/>
		</th>
		<!--  <td> <input type="button" class="eflowbutton"  name="addOnePowerSourceInfo" value="新增" onclick="addPowerSourceInfo();"> </td> -->
	</tr>
	<tr id="powerSourceInfo_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>权属来源类型：</td>
					<td class="tab_width">
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
					<td class="tab_width">
					    <input type="text" class="tab_text validate[required]" maxlength="30" style="float: left;" 
						name="POWERSOURCE_QLRMC" id="POWERSOURCE_QLRMC" />
						<%-- <eve:eveselect clazz="tab_text " dataParams="QSZT"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择权利人" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE" >
						</eve:eveselect> --%>
					</td>
					<td class="tab_width"><font class="tab_color">*</font>权属状态：</td>
					<td class="tab_width">
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DYQSZT"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="选择产权状态" name="POWERSOURCE_QSZT" id="POWERSOURCE_QSZT">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td class="tab_width">
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
						name="POWERSOURCE_ZJHM"  />
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
			</table>
			<div class="tab_height2"></div>
		</td>
		<!-- <td>
			<input type="button" class="eflowbutton" name="deleteCurrentPowerSourceInfo" value="删除" onclick="deletePowerSourceInfo('1');">
		</td> -->
	</tr>
</table>

<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="powerSourceInfoList">
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


<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='zdjbxx'>
	<tr>
		<th colspan="4">宗地基本信息</th>
	</tr>	
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
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='gyqlxx'>
	<tr>
		<th colspan="4">国有土地使用权-权利信息</th>
	</tr>	
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
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='fwjbxx'>
	<tr>
		<th colspan="4">房屋基本信息</th>
	</tr>
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
</table>

<script type="text/javascript">
	/* $(function(){
		initAutoTable();
	}); */
</script>
