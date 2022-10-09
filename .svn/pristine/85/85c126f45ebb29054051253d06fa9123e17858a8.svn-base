<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
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

.titleButton {
	float: right;
	margin: 2px 0;
	margin-right: 10px;
	padding: 0 20px;
}

.haveButtonTitle {
	position: absolute;
	left: 47%;
}
</style>
<script type="text/javascript">
	function submitJzwqfInfo(type){

        var flag = validatePowerParams("JzwqfInfo","required");

        if (!flag) {
            return false;
        }

        if($("#JzwqfInfoList_blank_tr")){
            $("#JzwqfInfoList_blank_tr").remove();
        }
        var JzwqfInfo = FlowUtil.getFormEleData("JzwqfInfo");
        var trid = $("#JzwqfInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加建筑物区分所有权业主共有部分登记信息?", function() {
                closeinfo("JzwqfInfo");
                addinfo("JzwqfInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendJzwqfInfoRow(JzwqfInfo,i,trid);
            art.dialog({
                content : "建筑物区分所有权业主共有部分登记信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#JzwqfInfoList .JzwqfInfo_tr")){
                index = $("#JzwqfInfoList .JzwqfInfo_tr").length;
                if(index > 0){
                   var trid = $("#JzwqfInfoList .JzwqfInfo_tr").last().attr("id");
                   index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendJzwqfInfoRow(JzwqfInfo,index,null);
        } 
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加建筑物区分所有权业主共有部分登记信息?", function() {
                closeinfo("JzwqfInfo");
                addinfo("JzwqfInfo");
            });
        }       
    }
    
    function appendJzwqfInfoRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="JzwqfInfo_tr" id="JzwqfInfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + index + '</td>';
            html += '<td style="text-align: center;">' + item.JZWQF_BDCDYH + '</td>';
            html += '<td style="text-align: center;">' + item.JZWQF_ZH + '</td>';
            html += '<td style="text-align: center;">' + item.JZWQF_HH + '</td>';
            html += '<td style="text-align: center;">' + item.JZWQF_JZMJ + '</td>';
            html += '<td style="text-align: center;">' + item.JZWQF_ZYJZMJ + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadJzwqfInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadJzwqfInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
               var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
               $("#"+replaceTrid).remove();
               if(prevtrid){
                   $("#"+prevtrid).after(html)
               } else{
                   $("#JzwqfInfoList").append(html);
               }       
            }else{
               $("#JzwqfInfoList").append(html);
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

            $("#JzwqfInfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#JzwqfInfo").attr("trid","JzwqfInfotr_"+index);
        }
    }

    function loadJzwqfInfo(obj,ptype){
        $("#JzwqfInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#JzwqfInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"JzwqfInfo");
        if(ptype == "0"){
            //查看
            $("#JzwqfInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#JzwqfInfo_btn").attr("style","");
        }
    }

    function getJzwqfJson(){
       var datas = [];
       $("#JzwqfInfoList .JzwqfInfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));
       });
       $("#JZWQF_JSON").val(JSON.stringify(datas));
       return datas;
    }

    function initJzwqf(items){
        $(".JzwqfInfo_tr").each(function (index) {
            $(this).remove();
        });
        $("#JzwqfInfo").attr("trid", "");
       if(items){
           if($("#JzwqfInfoList_blank_tr")){
              $("#JzwqfInfoList_blank_tr").remove();
           }
           for(var i=0;i<items.length;i++){
              appendJzwqfInfoRow(items[i],(i+1),null);
           }
           $("#JzwqfInfo").attr("style","");
           var firstItem = $("#JzwqfInfoList .JzwqfInfo_tr")[0];
           var id = $(firstItem).attr("id");
           $("#JzwqfInfo").attr("trid",id);
           var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
           var item = JSON.parse(iteminfo);
           FlowUtil.initFormFieldValue(item,"JzwqfInfo");
           if (item.POWERSOURCE_SDTXLY && item.POWERSOURCE_SDTXLY != "") {
               $("#sdtxly").show();
           } else {
               $("#sdtxly").hide();
           }
           $("#JzwqfInfo_btn").attr("style","display:none;");
       }
    }

    function loadDataJzwqf(){
        var datastr = $("#JZWQF_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initJzwqf(items);
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
        $("#JzwqfInfo select , #JzwqfInfo input").each(function () {
            $(this).attr("disabled", false);
        });
        $("#sdtxly").show();
        $("#sdtxFont").show();
        $("input[name='POWERSOURCE_SDTXLY']").addClass(" validate[required]");
    }
	
</script>
<!-- 建筑物区分所有权业主共有部分登记开始 -->
<div id="jzwqf" name="bdc_jzwqf">
<div class="tab_height"></div>
<div id="JzwqfInfo" trid=""> 
    <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">
        <tr>
	       <th><span>建筑物区分所有权业主共有部分登记</span>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitJzwqfInfo('1');"/>
	           <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitJzwqfInfo('0');"/>
	       </th>
	   </tr>
        <tr>
            <td style="padding: 10px">
                <table class="tab_tk_pro2">
                   <tr>
						<td class="tab_width">建（构）筑物名称：</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" name="JZWQF_JZWMC"
								value="${busRecord.JZWQF_JZWMC}" id="DJSH_GGBH" />
						</td>
						<td class="tab_width">不动产单元号：</td>
						<td>
							<input type="text" class="tab_text " maxlength="500" name="JZWQF_BDCDYH"
								value="${busRecord.JZWQF_BDCDYH}" id="JZWQF_BDCDYH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">规划用途：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_GHYT"
								value="${busRecord.JZWQF_GHYT}" id="JZWQF_GHYT" />
						</td>
						<td class="tab_width">分摊土地面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_FTTDMJ"
								value="${busRecord.JZWQF_FTTDMJ}" id="JZWQF_FTTDMJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">幢号：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_ZH"
								value="${busRecord.JZWQF_ZH}" id="JZWQF_ZH" />
						</td>
						<td class="tab_width">户号：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_HH"
								value="${busRecord.JZWQF_HH}" id="JZWQF_HH" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">所在层：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_SZC"
								value="${busRecord.JZWQF_SZC}" id="JZWQF_SZC" />
						</td>
						<td class="tab_width">建（构）筑面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_JZMJ"
								value="${busRecord.JZWQF_JZMJ}" id="JZWQF_JZMJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">专用建筑面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_ZYJZMJ"
								value="${busRecord.JZWQF_ZYJZMJ}" id="JZWQF_ZYJZMJ" />
						</td>
						<td class="tab_width">分摊建筑面积：</td>
						<td>
							<input type="text" class="tab_text " maxlength="128" name="JZWQF_FTJZMJ"
								value="${busRecord.JZWQF_FTJZMJ}" id="JZWQF_FTJZMJ" />
						</td>
					</tr>
					<tr>
						<td class="tab_width">附记：</td>
						<td colspan="5">
							<input type="text" class="tab_text " maxlength="2000" name="JZWQF_FJ"
								value="${busRecord.JZWQF_FJ}" id="JZWQF_FJ" style="width: 72%;" />
						</td>
					</tr>
                </table>                
            </td>           
        </tr>
    </table>
</div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="JzwqfInfoList">
	<tr>
	    <td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">序号</td> 
		<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">不动产单元号</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">幢号</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">户号</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">建筑面积</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">专有建筑面积</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>
	<tr id="JzwqfInfoList_blank_tr" style="display:none">
       <td colspan="7" style="text-align: center;">暂无建筑物区分所有权业主共有部分登记，请添加！</td>
    </tr>
</table>
</div>
<!-- 建筑物区分所有权业主共有部分登记结束 -->
