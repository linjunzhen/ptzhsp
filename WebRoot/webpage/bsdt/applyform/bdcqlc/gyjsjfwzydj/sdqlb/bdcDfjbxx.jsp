<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style>
.haveButtonTitle {
    position: absolute;
    left: 47%;
}
.titleButton {
    float: right;
    margin: 2px 0;
    margin-right: 10px;
    padding: 0 20px !important;
}
</style>

<script>
    function submitPowLimitInfo(type){
        if($("#powerLimitInfoList_blank_tr")){
            $("#powerLimitInfoList_blank_tr").remove();
        }
        var powerPeopleInfo = FlowUtil.getFormEleData("powerLimitInfo");
        var trid = $("#powerLimitInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加权利限制信息?", function() {
                closeinfo("powerLimitInfo");
                addinfo("powerLimitInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendPowLimitRow(powerPeopleInfo,i,trid);
            art.dialog({
                content : "权利限制信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#powerLimitInfoList .powerlimitinfo_tr")){
                index = $("#powerLimitInfoList .powerpeopleinfo_tr").length;
                if(index > 0){
                   var trid = $("#powerLimitInfoList .powerlimitinfo_tr").last().attr("id");
                   index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendPowLimitRow(powerPeopleInfo,index,null);
        } 
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加权利限制信息?", function() {
                closeinfo("powerLimitInfo");
                addinfo("powerLimitInfo");
            });
        }       
    }
    
    function appendPowLimitRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="powerlimitinfo_tr" id="powerlimitinfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            if(item.LIMIT_SW == "0"){
                html += '<td style="text-align: center;">否</td>';
            }else{
                html += '<td style="text-align: center;">是</td>';
            }
            if(item.LIMIT_HZ == "0"){
                html += '<td style="text-align: center;">否</td>';
            }else{
                html += '<td style="text-align: center;">是</td>';
            }
            if(item.LIMIT_SFYX == "0"){
                html += '<td style="text-align: center;">否</td>';
            }else{
                html += '<td style="text-align: center;">是</td>';
            }
            html += '<td style="text-align: center;">' + item.LIMIT_QLRQ + '</td>';
            html += '<td style="text-align: center;">' + item.LIMIT_CQZH + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowLimitInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowLimitInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#powerLimitInfoList").append(html);
               }       
            }else{
               $("#powerLimitInfoList").append(html);
            }
            $("#powerlimitinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
        }
    }
    
    function loadPowLimitInfo(obj,ptype){
        $("#powerLimitInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#powerLimitInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"powerLimitInfo");
        if(ptype == "0"){
            //查看
            $("#powerLimitInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#powerLimitInfo_btn").attr("style","");
        }
    }   
    
    function getPowLimitJson(){
       var datas = [];
       $("#powerLimitInfoList .powerlimitinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));          
       });
       $("#POWERSOURCEINFO_JSON").val(JSON.stringify(datas));
       return datas;
    }
    
    function initPowLimit(items){
       if(items){
           if($("#powerLimitInfoList_blank_tr")){
              $("#powerLimitInfoList_blank_tr").remove();
           }
           for(var i=0;i<items.length;i++){
              appendPowLimitRow(items[i],(i+1),null);
           }
           $("#powerLimitInfo").attr("style","");
           var firstItem = $("#powerLimitInfoList .powerlimitinfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#powerLimitInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           var item = JSON.parse(iteminfo);
           FlowUtil.initFormFieldValue(item,"powerLimitInfo");
           $("#powerLimitInfo_btn").attr("style","display:none;");
       }
    }
    
    function loadDataPowLimit(){
        var datastr = $("#POWERLIMITINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowLimit(items);
        }
    }
</script>

<!-- 权属来源信息开始 -->
<div class="tab_height"></div>
<div id="powerLimitInfo" trid=""> 
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
        <tr>
	       <th><span>权利限制</span>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitPowLimitInfo('1');"/>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitPowLimitInfo('0');"/>
	       </th>
	   </tr>
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width"><!-- <font class="tab_color">*</font> -->权利限制_收文：</td>
                        <td>
                            <eve:eveselect clazz="tab_text " dataParams="YesOrNo" value="0"
                            dataInterface="dictionaryService.findDatasForSelect" 
                            defaultEmptyText="选择权利限制_收文" name="LIMIT_SW" id="LIMIT_SW">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">权利限制_核准：</td>
                        <td>
                          <eve:eveselect clazz="tab_text " dataParams="YesOrNo" value="0"
                            dataInterface="dictionaryService.findDatasForSelect" 
                            defaultEmptyText="选择权利限制_核准" name="LIMIT_HZ" id="LIMIT_HZ">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><!-- <font class="tab_color">*</font> -->权利限制_是否有效：</td>
                        <td>
                            <eve:eveselect clazz="tab_text " dataParams="YesOrNo" value="0"
                            dataInterface="dictionaryService.findDatasForSelect" 
                            defaultEmptyText="选择权利限制_是否有效" name="LIMIT_SFYX" id="LIMIT_SFYX">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">权利限制种类：</td>
                        <td>
                          <eve:eveselect clazz="tab_text " dataParams="ZDQLLX" defaultEmptyValue="18" 
                            dataInterface="dictionaryService.findDatasForSelect" 
                            defaultEmptyText="选择权利限制种类" name="LIMIT_QLLX" id="LIMIT_QLLX">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">权利日期：</td>
                        <td>
                          <input type="text" id="LIMIT_QLRQ" name="LIMIT_QLRQ" 
                                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
                                class="tab_text Wdate" maxlength="60" style="width:160px;" />
                        </td>
                         <td class="tab_width"><font class="tab_color">*</font>权利人：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="30" style="float: left;" 
                            name="LIMIT_QLRMC" id="LIMIT_QLRMC" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>产权证号：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="30" style="float: left;" 
                            name="LIMIT_CQZH" id="LIMIT_CQZH" />
                        </td>
                        <td class="tab_width">义务人：</td>
                        <td>
                            <input type="text" class="tab_text" maxlength="30" style="float: left;" 
                            name="LIMIT_YWRMC" id="LIMIT_YWRMC" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">权利限制_文件号：</td>
                        <td>
                          <input type="text" class="tab_text "  maxlength="30" id="LIMIT_WJH" style="float: left;"
                            name="LIMIT_WJH"  />
                        </td>
                        <td class="tab_width">产权地址：</td>
                        <td>
                          <input type="text" class="tab_text "  maxlength="30" id="LIMIT_ADDR" style="float: left;"
                            name="LIMIT_ADDR"  />
                        </td>
                    </tr>     
                    <tr>
                        <td class="tab_width">权利限制备注：</td>
                        <td colspan="3"><input type="text" class="tab_text" maxlength="60" 
                            name="LIMIT_BZ" id="LIMIT_BZ" style="width: 72%;"  />
                        </td>
                    </tr>
                </table>                
            </td>           
        </tr>
    </table>
</div>
<table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="powerLimitInfoList">
    <tr>
        <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">权利限制_收文</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">权利限制_核准</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">权利限制_是否有效</td>
        <td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">权利日期</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">产权证号</td>
        <td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
    </tr>
    <tr id="powerLimitInfoList_blank_tr">
       <td colspan="7" style="text-align: center;">暂无权利限制信息，请添加！</td>
    </tr>
</table>