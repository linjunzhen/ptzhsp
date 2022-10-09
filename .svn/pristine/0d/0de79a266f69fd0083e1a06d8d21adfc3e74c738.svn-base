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
    function submitPowSourceInfo(type){
        if($("#powerSourceInfoList_blank_tr")){
            $("#powerSourceInfoList_blank_tr").remove();
        }
        var powerPeopleInfo = FlowUtil.getFormEleData("powerSourceInfo");
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
            appendPowSourceRow(powerPeopleInfo,i,trid);
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
            appendPowSourceRow(powerPeopleInfo,index,null);
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
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadPowSourceInfo(this,1);" id="djxxItem">编辑</a>';
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
       if(items){
           if($("#powerSourceInfoList_blank_tr")){
              $("#powerSourceInfoList_blank_tr").remove();
           }
           if($("#powerSourceInfoList .powersourceinfo_tr")){
           	  $("#powerSourceInfoList .powersourceinfo_tr").remove();
           }
           for(var i=0;i<items.length;i++){
              appendPowSourceRow(items[i],(i+1),null);
           }
           $("#powerSourceInfo").attr("style","");
           var firstItem = $("#powerSourceInfoList .powersourceinfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#powerSourceInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           if(iteminfo){
                 var item = JSON.parse(iteminfo);
          		 FlowUtil.initFormFieldValue(item,"powerSourceInfo");
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
</script>

<!-- 权属来源信息开始 -->
<div class="tab_height"></div>
<div id="powerSourceInfo" trid=""> 
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
        <input type="hidden" name="POWERSOURCE_GLH" />
        <tr>
	       <th><span>权属来源</span>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitPowSourceInfo('1');"/>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitPowSourceInfo('0');"/>
	       </th>
	   </tr>
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
                          <input type="text" class="tab_text validate[required]" style="float: left;" 
                            name="POWERSOURCE_QSWH" id="POWERSOURCE_QSWH" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">不动产单元号：</td>
                        <td>
                          <input type="text" class="tab_text"  style="float: left;" 
                            name="POWERSOURCE_DYH" id="POWERSOURCE_DYH" />
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
                        <td class="tab_width">宗地代码：</td>
                        <td>
                          <input type="text" class="tab_text"  style="float: left;" 
                            name="POWERSOURCE_ZDDM" id="POWERSOURCE_ZDDM" />
                        </td>
                        <td class="tab_width">产权状态：</td>
                        <td>
                            <eve:eveselect clazz="tab_text validate[]" dataParams="CQZT"
                            dataInterface="dictionaryService.findDatasForSelect" 
                            defaultEmptyText="选择产权状态" name="POWERSOURCE_CQZT" id="POWERSOURCE_CQZT">
                            </eve:eveselect>
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
                        <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
                        <td>
                            <!-- <input type="text" class="tab_text validate[required]" maxlength="30" id="POWERSOURCE_ZJLX" style="float: left;"
                            name="POWERSOURCE_ZJLX"   readonly="readonly"/>  -->
                            <eve:eveselect clazz="tab_text " dataParams="DYZJZL"
                            dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
                            defaultEmptyText="选择证件类型" name="POWERSOURCE_ZJLX" id="POWERSOURCE_ZJLX">
                            </eve:eveselect>
                        </td>                                               
                    </tr>
                    <tr>
                        <td class="tab_width">证件号码：</td>
                        <td colspan="3">
                          <input type="text" class="tab_text "  maxlength="30" id="POWERSOURCE_ZJHM" style="float: left;"
                            name="POWERSOURCE_ZJHM"  /><a href="javascript:void(0);" onclick="PowSrcRead(this);" class="eflowbutton">身份证读卡</a>
                        </td>                        
                    </tr>
                    <tr>
						<td class="tab_width">法定代表人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="POWERSOURCE_FDDBRXM"
								style="float: left;" name="POWERSOURCE_FDDBRXM" />
						</td>
						<td class="tab_width">法定代表人电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="POWERSOURCE_FDDBRDH"
								style="float: left;" name="POWERSOURCE_FDDBRDH" />
						</td>
					</tr>
					
					<tr>
						<td class="tab_width">代表人证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="POWERSOURCE_FDDBRZJLX" id="POWERSOURCE_FDDBRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">代表人证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="POWERSOURCE_FDDBRZJHM"
								style="float: left;" name="POWERSOURCE_FDDBRZJHM" /><a href="javascript:void(0);" onclick="PowFRDHRead(this);" class="eflowbutton">身份证读卡</a>
						</td>
					</tr>
					<tr>
						<td class="tab_width">代理人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="POWERSOURCE_DLRXM"
								style="float: left;" name="POWERSOURCE_DLRXM" />
						</td>
						<td class="tab_width">代理人电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="POWERSOURCE_DLRDH"
								style="float: left;" name="POWERSOURCE_DLRDH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">代理人证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="POWERSOURCE_DLRZJLX" id="POWERSOURCE_DLRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">代理人证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="POWERSOURCE_DLRZJHM"
								style="float: left;" name="POWERSOURCE_DLRZJHM" /><a href="javascript:void(0);" onclick="PowDLRRead(this);" class="eflowbutton">身份证读卡</a>
						</td>
					</tr>
					
					
					<tr>
						<td class="tab_width">代理机构名称：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="POWERSOURCE_DLJGMC"
								style="float: left;" name="POWERSOURCE_DLJGMC" />
						</td>
						<td class="tab_width">权力开始时间：</td>
						<td>
							<input type="text" id="POWERSOURCE_EFFECT_TIME" name="POWERSOURCE_EFFECT_TIME" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" class="tab_text_1 Wdate"/>
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
            </td>           
        </tr>
    </table>
</div>
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