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
            html += '<td style="text-align: center;">' + item.QLR + '</td>';
            html += '<td style="text-align: center;">' + item.BDC_QLRZJHM + '</td>';
            if(item.POWERSOURCENATURE == "0"){
               html += '<td style="text-align: center;">土地_出让合同</td>';
            }else if(item.POWERSOURCENATURE == "1"){
               html += '<td style="text-align: center;">土地_划拨决定书</td>';
            }else if(item.POWERSOURCENATURE == "2"){
               html += '<td style="text-align: center;">土地_原土地证</td>';
            }else if(item.POWERSOURCENATURE == "3"){
               html += '<td style="text-align: center;">不动产权证书</td>';
            }else if(item.POWERSOURCENATURE == "4"){
               html += '<td style="text-align: center;">其他批准文件</td>';
            }else if(item.POWERSOURCENATURE == "5"){
               html += '<td style="text-align: center;">初始登记证明号</td>';
            }else if(item.POWERSOURCENATURE == "6"){
               html += '<td style="text-align: center;">安置协议</td>';
            }else{
               html += '<td style="text-align: center;"></td>';
            }
            html += '<td style="text-align: center;">' + item.BDC_BDCDYH + '</td>';
            html += '<td style="text-align: center;">' + item.POWERSOURCEIDNUM + '</td>';
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
            /*若item中没有法定代表人字段，则新增法定代表人，代理人字段*/
            if (!item['BDC_FDDBRXM']) {
                item['BDC_FDDBRXM'] = '';
            }
            if (!item['BDC_FDDBRDH']) {
                item['BDC_FDDBRDH'] = '';
            }
            if (!item['BDC_DLRXM']) {
                item['BDC_DLRXM'] = '';
            }
            if (!item['BDC_DLRDH']) {
                item['BDC_DLRDH'] = '';
            }
            if (!item['BDC_DLJGMC']) {
                item['BDC_DLJGMC'] = '';
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

<!-- 权属来源信息开始 -->
<div class="tab_height"></div>
<div id="powerSourceInfo" trid=""> 
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
        <tr>
	       <th><span>权属来源</span>
	       		<div id="bdcdacxButton" style="display:none; float: right;">
					<a href="javascript:void(0);" class="eflowbutton titleButton" 
							onclick="showSelectBdcdaxxcx_TBG();">不动产档案查询 </a>
				</div>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitPowSourceInfo('1');"/>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitPowSourceInfo('0');"/>
	           <input type="button" class="eflowbutton" style="display:none;float:right; margin: 2px 10px; padding: 0 20px;" value="手动填写" id="sdtxBtn" onclick="fillInQsly();"/>
	       </th>
	   </tr>
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>权属来源类型：</td>
                        <td>
                            <%-- <eve:eveselect clazz="tab_text validate[required]" dataParams="QSLYLX"
								dataInterface="dictionaryService.findDatasForSelect" onchange="setQSLYLX(this.value);"
								defaultEmptyText="选择权属来源类型" name="POWERSOURCENATURE" id="POWERSOURCENATURE">
							</eve:eveselect> --%>
							<eve:eveselect clazz="tab_text validate[required]" dataParams="QSLYLX"
								dataInterface="dictionaryService.findDatasForSelect"
								defaultEmptyText="选择权属来源类型" name="POWERSOURCENATURE" id="POWERSOURCENATURE">
							</eve:eveselect> 
                        </td>
                        <td class="tab_width"><font class="tab_color" id="zswhPow">*</font>权属文号：</td>
                        <td>
                          <input type="text" class="tab_text validate[required]" maxlength="30" id="POWERSOURCEIDNUM"
								style="float: left;" name="POWERSOURCEIDNUM" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">不动产单元号：</td>
                        <td>
                           <input type="text" class="tab_text validate[custom[estateNum]]" maxlength="128" id="BDC_BDCDYH"
								style="float: left;" name="BDC_BDCDYH" />
                        </td>
                        <td class="tab_width">权属状态：</td>
                        <td>
                            <eve:eveselect clazz="tab_text " dataParams="QSZT"
								dataInterface="dictionaryService.findDatasForSelect" 
								defaultEmptyText="选择权属状态" name="POWERSOURCEIDTYPE" id="POWERSOURCEIDTYPE">
							</eve:eveselect>
                        </td>
                    </tr>
                    <tr>
						<td class="tab_width">权力人：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" 
								style="float: left;" id="QLR" name="QLR" />
						</td>
						<td class="tab_width">宗地代码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_ZDDM"
								style="float: left;" name="BDC_ZDDM" />
						</td>
					</tr>
                    <tr>
                        <td class="tab_width">证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="BDC_QLRZJLX" id="BDC_QLRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_QLRZJHM"
								style="float: left;" name="BDC_QLRZJHM" />
						</td>
                    </tr>
                    <tr>
                        <td class="tab_width">法定代表人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_FDDBRXM"
								style="float: left;" name="BDC_FDDBRXM" />
						</td>
						<td class="tab_width">法定代表人电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="11" id="BDC_FDDBRDH"
								style="float: left;" name="BDC_FDDBRDH" />
						</td>           
                    </tr>
                    <tr>
						<td class="tab_width">代表人证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="BDC_FDDBRZJLX" id="BDC_FDDBRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">代表人证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_FDDBRZJHM"
								style="float: left;" name="BDC_FDDBRZJHM" />
						</td>
					</tr>
                    <tr>
                        <td class="tab_width">代理人姓名：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLRXM"
								style="float: left;" name="BDC_DLRXM" />
						</td>
						<td class="tab_width">代理人电话：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLRDH"
								style="float: left;" name="BDC_DLRDH" />
						</td>          
                    </tr>
                    
                    <tr>
						<td class="tab_width">代理人证件类型：</td>
						<td>
							<eve:eveselect clazz="tab_text " dataParams="DocumentType"
								dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="选择证件类型"
								name="BDC_DLRZJLX" id="BDC_DLRZJLX">
							</eve:eveselect>
						</td>
						<td class="tab_width">代理人证件号码：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="18" id="BDC_DLRZJHM"
								style="float: left;" name="BDC_DLRZJHM" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">代理机构名称：</td>
						<td>
							<input type="text" class="tab_text validate[]" maxlength="128" id="BDC_DLJGMC"
								style="float: left;" name="BDC_DLJGMC" />
						</td>
						<td class="tab_width">权力开始时间：</td>
						<td>
							<input type="text" id="BDC_QLKSSJ" name="BDC_QLKSSJ" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'BDC_QLJSSJ1\')}'})" class="tab_text_1 Wdate"/>
						</td>
					</tr>
                   
                   <tr>
						<td class="tab_width">权力结束时间1：</td>
						<td>
							<input type="text" id="BDC_QLJSSJ1" name="BDC_QLJSSJ1" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'BDC_QLKSSJ\')}'})" class="tab_text_1 Wdate"/>
						</td>
						<td class="tab_width">权力结束时间2：</td>
						<td>
							<input type="text" id="BDC_QLJSSJ2" name="BDC_QLJSSJ2" maxlength="60" style="width:184px;"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'BDC_QLKSSJ\')}'})" class="tab_text_1 Wdate"/>
						</td>
					</tr>
                    <tr>
                        <td class="tab_width">备注：</td>
						<td colspan="3">
							<input type="text" class="tab_text validate[]" maxlength="512" id="BDC_QSLYBZ"
								 style="width: 60%;" name="BDC_QSLYBZ" />
						</td>
                    </tr>
                    <tr id="sdtxly" style="display: none;">
                        <td class="tab_width"><font class="tab_color" id="sdtxFont" style="display: none;">*</font>手动填写理由：</td>
                        <td colspan="3"><input type="text" class="tab_text" maxlength="256"
                                               name="POWERSOURCE_SDTXLY" id="POWERSOURCE_SDTXLY" style="width: 72%;"  />
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
       <td colspan="7" style="text-align: center;">暂无权属来源信息，请添加！</td>
    </tr>
</table>