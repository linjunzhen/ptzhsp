<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>


<script>
    function ssdjSelect() {
        var ssdjType = $("input[name='SSDJ_TYPE']:checked").val();
        $("div[class='ssdjxx']").each(function (index) {
            $(this).hide();
        });
        if (ssdjType == '1') {
            $("#ssdjFyxx").show();
            $("#ssdjBuyxx").show();
        } else if (ssdjType == '2') {
            $("#ssdjFwxx").show();
            $("#ssdjBuyxx").show();
            $("#ssdjSellxx").show();
        } else if (ssdjType == '3') {
            $("#ssdjCzrxx").show();
            $("#ssdjBuyxx").show();
            $("#ssdjSellxx").show();
        }
    }

    /*
    * 字段校验是否必填
    * */
    function validatePowerParamsSsdj() {
        /*判断页面字段是否未填*/
        var ssdjType = $("input[name='SSDJ_TYPE']:checked").val();
        if (ssdjType == '1') {
            var flag2 = validatePowerParams("ssdjFyxx","required");
            if (!flag2) {
                return false;
            }
            var flag3 = validatePowerParams("ssdjBuyInfo","required");
            if (!flag3) {
                return false;
            }
        } else if (ssdjType == '2') {
            var flag1 = validatePowerParams("ssdjFwxx","required");
            if (!flag1) {
                return false;
            }
            var flag3 = validatePowerParams("ssdjBuyInfo","required");
            if (!flag3) {
                return false;
            }
            var flag4 = validatePowerParams("ssdjSellInfo","required");
            if (!flag4) {
                return false;
            }
        }
        return true;
    }

    function saveTaxInfo() {
        var flag = validatePowerParamsSsdj();
        if (!flag) {
            return false;
        }
        var taxFormData = FlowUtil.getFormEleData("taxRelatedInfo");
        var flowSubmitObj = FlowUtil.getFlowObj();
        taxFormData.EXE_ID = flowSubmitObj.EFLOW_EXEID;
        taxFormData.YW_ID = flowSubmitObj.EFLOW_BUSRECORDID;
        var buyJsonInfo = getSsdjBuyJson();
        taxFormData.buyJsonInfo = JSON.stringify(buyJsonInfo);
        taxFormData.SSDJBUYINFO_JSON = JSON.stringify(buyJsonInfo);
        var sellJsonInfo = getSsdjSellJson();
        taxFormData.sellJsonInfo = JSON.stringify(sellJsonInfo);
        taxFormData.SSDJSELLINFO_JSON = JSON.stringify(sellJsonInfo);
        AppUtil.ajaxProgress({
            url:"bdcSsdjController/sendBdcSsdjInfo.do",
            params:taxFormData,
            callback:function (data) {
                if (data.success) {
                    $("#isTaxSuccess").text("（已申报）");
                    $("#getSsdjInfoBtn").show();
                    $("#IS_TAX_SUCCESS").val('1');
                    art.dialog({
                        content: data.msg,
                        icon: "succeed",
                        ok: true
                    });
                } else {
                    art.dialog({
                        content: data.msg,
                        icon: "error",
                        ok: true
                    });
                }
            }
        });
    }

    function getTaxInfo() {
        var flowSubmitObj = FlowUtil.getFlowObj();
        var exeId = flowSubmitObj.EFLOW_EXEID;
        $.dialog.open("bdcSsdjController.do?getSsdjInfoView&exeId=" + exeId, {
            title : "申报信息",
            width:"600px",
            lock: true,
            resize:false,
            height:"420px",
        }, false);
    }

    function submitBuyInfo(type) {
        var ssdjBuyInfo = FlowUtil.getFormEleData("ssdjBuyInfo");
        var trid = $("#ssdjBuyInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加买方信息?", function() {
                closeinfo("ssdjBuyInfo");
                addinfo("ssdjBuyInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendSsdjBuyRow(ssdjBuyInfo,i + 1,trid);
            art.dialog({
                content : "买方信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#ssdjBuyInfoList .ssdjbuyinfo_tr")){
                index = $("#ssdjBuyInfoList .ssdjbuyinfo_tr").length;
                if(index > 0){
                    var trid = $("#ssdjBuyInfoList .ssdjbuyinfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendSsdjBuyRow(ssdjBuyInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加买方信息?", function() {
                closeinfo("ssdjBuyInfo");
                addinfo("ssdjBuyInfo");
            });
        }
    }

    function appendSsdjBuyRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="ssdjbuyinfo_tr" id="ssdjbuyinfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + item.SSDJ_BUY_XM + '</td>';
            html += '<td style="text-align: center;">' + item.SSDJ_BUY_ZJHM + '</td>';
            html += '<td style="text-align: center;">' + item.SSDJ_BUY_LXDH + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadSsdjBuyInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadSsdjBuyInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
                var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
                $("#"+replaceTrid).remove();
                if(prevtrid){
                    $("#"+prevtrid).after(html)
                } else{
                    $("#ssdjBuyInfoList").append(html);
                }
            }else{
                $("#ssdjBuyInfoList").append(html);
            }

            $("#ssdjbuyinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#ssdjBuyInfo").attr("trid","ssdjbuyinfotr_"+index);
        }
    }

    function loadSsdjBuyInfo(obj,ptype){
        $("#ssdjBuyInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#ssdjBuyInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"ssdjBuyInfo");
        if(ptype == "0"){
            //查看
            $("#ssdjBuyInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#ssdjBuyInfo_btn").attr("style","");
        }
    }

    function getSsdjBuyJson(){
        var datas = [];
        $("#ssdjBuyInfoList .ssdjbuyinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));
        });
        $("#SSDJBUYINFO_JSON").val(JSON.stringify(datas));
        return datas;
    }

    function initSsdjBuy(items){
        if(items){
            if($("#ssdjBuyInfoList_blank_tr")){
                $("#ssdjBuyInfoList_blank_tr").remove();
            }
            for(var i=0;i<items.length;i++){
                appendSsdjBuyRow(items[i],(i+1),null);
            }
            $("#ssdjBuyInfo").attr("style","");
            var firstItem = $("#ssdjBuyInfoList .ssdjbuyinfo_tr")[0];
            var id = $(firstItem).attr("id");
            $("#ssdjBuyInfo").attr("trid",id);
            var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
            var item = JSON.parse(iteminfo);
            FlowUtil.initFormFieldValue(item,"ssdjBuyInfo");
            $("#ssdjBuyInfo_btn").attr("style","display:none;");
        }
    }

    function loadDataSsdjBuy(){
        var datastr = $("#SSDJBUYINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initSsdjBuy(items);
        }
    }

</script>

<script>
    function submitSellInfo(type) {
        var ssdjSellInfo = FlowUtil.getFormEleData("ssdjSellInfo");
        var trid = $("#ssdjSellInfo").attr("trid");
        if(type == '0' && ""!=trid && undefined != trid){
            art.dialog.confirm("是否继续添加卖方信息?", function() {
                closeinfo("ssdjSellInfo");
                addinfo("ssdjSellInfo");
            });
            return ;
        }
        if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendSsdjSellRow(ssdjSellInfo,i + 1,trid);
            art.dialog({
                content : "卖方信息更新成功。",
                icon : "succeed",
                ok : true
            });
        }else{
            var index = 0;
            if($("#ssdjSellInfoList .ssdjsellinfo_tr")){
                index = $("#ssdjSellInfoList .ssdjsellinfo_tr").length;
                if(index > 0){
                    var trid = $("#ssdjSellInfoList .ssdjsellinfo_tr").last().attr("id");
                    index =parseInt(trid.split("_")[1]);
                }
            }
            index = index + 1;
            appendSsdjSellRow(ssdjSellInfo,index,null);
        }
        if(type == '0'){
            art.dialog.confirm("添加成功！是否继续添加卖方信息?", function() {
                closeinfo("ssdjSellInfo");
                addinfo("ssdjSellInfo");
            });
        }
    }

    function appendSsdjSellRow(item,index,replaceTrid){
        if(item != "" && null != item) {
            var html = "";
            html += '<tr class="ssdjsellinfo_tr" id="ssdjsellinfotr_' + index + '">';
            html += '<input type="hidden" name="iteminfo"/>';
            html += '<td style="text-align: center;">' + item.SSDJ_SELL_XM + '</td>';
            html += '<td style="text-align: center;">' + item.SSDJ_SELL_ZJHM + '</td>';
            html += '<td style="text-align: center;">' + item.SSDJ_SELL_LXDH + '</td>';
            html += '<td style="text-align: center;">';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadSsdjSellInfo(this,0);" id="djxxItem">查看</a>';
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="loadSsdjSellInfo(this,1);" id="djxxItem">编辑</a>';
            html += ' <a href="javascript:void(0);" style="float: right;" class="syj-closebtn" onclick="delDjxxTr(this);"></a></td>';
            html += '</tr>';
            if(replaceTrid){
                var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
                $("#"+replaceTrid).remove();
                if(prevtrid){
                    $("#"+prevtrid).after(html)
                } else{
                    $("#ssdjSellInfoList").append(html);
                }
            }else{
                $("#ssdjSellInfoList").append(html);
            }

            $("#ssdjsellinfotr_" + index + " input[name='iteminfo']").val(JSON.stringify(item));
            $("#ssdjSellInfo").attr("trid","ssdjsellinfotr_"+index);
        }
    }

    function loadSsdjSellInfo(obj,ptype){
        $("#ssdjSellInfo").attr("style","");
        var trid = $(obj).closest("tr").attr("id");
        $("#ssdjSellInfo").attr("trid",trid);
        var iteminfo = $(obj).closest("tr").find("input[name='iteminfo']").val();
        var item = JSON.parse(iteminfo);
        FlowUtil.initFormFieldValue(item,"ssdjSellInfo");
        if(ptype == "0"){
            //查看
            $("#ssdjSellInfo_btn").attr("style","display:none;");
        }else if(ptype == "1"){
            //修改
            $("#ssdjSellInfo_btn").attr("style","");
        }
    }

    function getSsdjSellJson(){
        var datas = [];
        $("#ssdjSellInfoList .ssdjsellinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));
        });
        $("#SSDJSELLINFO_JSON").val(JSON.stringify(datas));
        return datas;
    }

    function initSsdjSell(items){
        if(items){
            if($("#ssdjSellInfoList_blank_tr")){
                $("#ssdjSellInfoList_blank_tr").remove();
            }
            for(var i=0;i<items.length;i++){
                appendSsdjSellRow(items[i],(i+1),null);
            }
            $("#ssdjSellInfo").attr("style","");
            var firstItem = $("#ssdjSellInfoList .ssdjsellinfo_tr")[0];
            var id = $(firstItem).attr("id");
            $("#ssdjSellInfo").attr("trid",id);
            var iteminfo = $(firstItem).find("input[name='iteminfo']").val();
            var item = JSON.parse(iteminfo);
            FlowUtil.initFormFieldValue(item,"ssdjSellInfo");
            $("#ssdjSellInfo_btn").attr("style","display:none;");
        }
    }

    function loadDataSsdjSell(){
        var datastr = $("#SSDJSELLINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initSsdjSell(items);
        }
    }
</script>

<%--涉税登记买方信息--%>
<input type="hidden" name="SSDJBUYINFO_JSON" id="SSDJBUYINFO_JSON" value="${busRecord.SSDJBUYINFO_JSON}"/>
<%--涉税登记卖方信息--%>
<input type="hidden" name="SSDJSELLINFO_JSON" id="SSDJSELLINFO_JSON" value="${busRecord.SSDJSELLINFO_JSON}"/>
<%--涉税登记申报状态--%>
<input type="hidden" name="IS_TAX_SUCCESS" id="IS_TAX_SUCCESS" value="${busRecord.IS_TAX_SUCCESS}"/>
<div class="tab_height"></div>
<%--涉税登记信息开始--%>
<div id="taxRelatedInfo" style="display:none;">
    <%--涉税登记类型选择开始--%>
    <div id="ssdjTypeSelect">
        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
            <tr>
                <th>
                    <span>涉税登记<span id="isTaxSuccess" style="color: red;"></span>
                        <input type="button" style="float:right;margin-top: 2px;margin-right: 10px;" id="sendSsdjInfoBtn" class="eflowbutton" value="申报" onclick="saveTaxInfo();">
                        <input type="button" style="float:right;margin-right: 10px;margin-top: 2px;display: none;" id="getSsdjInfoBtn"  class="eflowbutton" value="查询申报信息" onclick="getTaxInfo();">
                    </span>

                </th>
            </tr>
            <tr>
                <td style="color:black;">
                    <eve:radiogroup fieldname="SSDJ_TYPE" width="500" defaultvalue="1" typecode="SSDJTYPE" onclick="ssdjSelect();" value="${busRecord.SSDJ_TYPE}"></eve:radiogroup>
                </td>
            </tr>
        </table>
    </div>
    <%--涉税登记类型选择开始--%>

    <%--房源信息开始--%>
    <div id="ssdjFyxx" class="ssdjxx" style="display: none;">
        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
            <tr>
                <th>
                    <span>房源信息</span>
                </th>
            </tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="36" id="SSDJ_FYXX_BDCDYH" name="SSDJ_FYXX_BDCDYH" value="${busRecord.SSDJ_FYXX_BDCDYH}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>房屋属地纳税机关：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJSWJG"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="13593012100" name="SSDJ_FYXX_FWSDNSJG" id="SSDJ_FYXX_FWSDNSJG" value="${busRecord.SSDJ_FYXX_FWSDNSJG}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>权属转移对象：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJZLFQSZYDX"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="20103" name="SSDJ_FYXX_QSZYDX" id="SSDJ_FYXX_QSZYDX" value="${busRecord.SSDJ_FYXX_QSZYDX}">
                        </eve:eveselect>
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>权属转移用途：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJQSZYYT"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="201" name="SSDJ_FYXX_QSZYYT" id="SSDJ_FYXX_QSZYYT" value="${busRecord.SSDJ_FYXX_QSZYYT}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>房屋所在地行政区划：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="6" id="SSDJ_FYXX_XZQH" name="SSDJ_FYXX_XZQH" value="${busRecord.SSDJ_FYXX_XZQH}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>房屋所在地乡镇街道：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJJDXZDM"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="350128100" name="SSDJ_FYXX_FWSZDXZJD" id="SSDJ_FYXX_FWSZDXZJD" value="${busRecord.SSDJ_FYXX_FWSZDXZJD}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>开发企业纳税人识别号：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="36" id="SSDJ_FYXX_NSRSBH" name="SSDJ_FYXX_NSRSBH" value="${busRecord.SSDJ_FYXX_NSRSBH}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>开发企业名称：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="32" id="SSDJ_FYXX_KFQYMC" name="SSDJ_FYXX_KFQYMC" value="${busRecord.SSDJ_FYXX_KFQYMC}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="64" id="SSDJ_FYXX_FWDZ" name="SSDJ_FYXX_FWDZ" value="${busRecord.SSDJ_FYXX_FWDZ}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>房屋总楼层：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FYXX_FWZLC" name="SSDJ_FYXX_FWZLC" value="${busRecord.SSDJ_FYXX_FWZLC}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>建筑面积：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FYXX_JZMJ" name="SSDJ_FYXX_JZMJ" value="${busRecord.SSDJ_FYXX_JZMJ}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>朝向代码：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJZLFCX"
                                       dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="01"
                                       name="SSDJ_FYXX_CXDM" id="SSDJ_FYXX_CXDM" value="${busRecord.SSDJ_FYXX_CXDM}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">交易合同编号：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="32" id="SSDJ_FYXX_JYHTBH" name="SSDJ_FYXX_JYHTBH" value="${busRecord.SSDJ_FYXX_JYHTBH}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>交易价格：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FYXX_JYJG" name="SSDJ_FYXX_JYJG" value="${busRecord.SSDJ_FYXX_JYJG}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>单价：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FYXX_DJ" name="SSDJ_FYXX_DJ" value="${busRecord.SSDJ_FYXX_DJ}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>合同金额：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FYXX_HTJE" name="SSDJ_FYXX_HTJE" value="${busRecord.SSDJ_FYXX_HTJE}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>权属转移方式：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJQSZYFS"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="21" name="SSDJ_FYXX_QSZYFS" id="SSDJ_FYXX_QSZYFS" value="${busRecord.SSDJ_FYXX_QSZYFS}">
                        </eve:eveselect>
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>当期应收款金额：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FYXX_DQYSKJE" name="SSDJ_FYXX_DQYSKJE" value="${busRecord.SSDJ_FYXX_DQYSKJE}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>当期应收款所属月份：</td>
                    <td>
                        <input type="text" class="tab_text Wdate validate[required]" maxlength="32" id="SSDJ_FYXX_DQYSKSSYF" name="SSDJ_FYXX_DQYSKSSYF"
                               onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true})" value="${busRecord.SSDJ_FYXX_DQYSKSSYF}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>建筑结构类型：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJJZJGLX"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="1" name="SSDJ_FYXX_JZJGLX" id="SSDJ_FYXX_JZJGLX" value="${busRecord.SSDJ_FYXX_JZJGLX}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>合同签订时间：</td>
                    <td>
                        <input type="text" class="tab_text Wdate validate[required]" maxlength="32" id="SSDJ_FYXX_HTQDSJ" name="SSDJ_FYXX_HTQDSJ"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" value="${busRecord.SSDJ_FYXX_HTQDSJ}">
                    </td>
                    <td class="tab_width">备注：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="128" id="SSDJ_FYXX_FYXXBZ" name="SSDJ_FYXX_FYXXBZ" value="${busRecord.SSDJ_FYXX_FYXXBZ}">
                    </td>
                </tr>
            </table>
        </table>
    </div>
    <%--房源信息结束--%>

    <%--房屋信息开始--%>
    <div id="ssdjFwxx" class="ssdjxx" style="display: none;">
        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
            <tr>
                <th>
                    <span>房屋信息</span>
                </th>
            </tr>
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>不动产单元号：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="32" id="SSDJ_FWXX_BDCDYH" name="SSDJ_FWXX_BDCDYH" value="${busRecord.SSDJ_FWXX_BDCDYH}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>房屋属地纳税机关：</td>
                    <td>
                        <eve:eveselect clazz="tab_text seletWidth w280 validate[required]" dataParams="SSDJSWJG"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="13593017000" name="SSDJ_FWXX_FWSDNSJG" id="SSDJ_FWXX_FWSDNSJG" value="${busRecord.SSDJ_FWXX_FWSDNSJG}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>房屋地址：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="64" id="SSDJ_FWXX_FWDZ" name="SSDJ_FWXX_FWDZ" value="${busRecord.SSDJ_FWXX_FWDZ}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>房屋所在行政区划：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="6" id="SSDJ_FWXX_XZQH" name="SSDJ_FWXX_XZQH" value="${busRecord.SSDJ_FWXX_XZQH}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>房屋所在乡镇街道：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJJDXZDM"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="350128100" name="SSDJ_FWXX_FWSZDXZJD" id="SSDJ_FWXX_FWSZDXZJD" value="${busRecord.SSDJ_FWXX_FWSZDXZJD}">
                        </eve:eveselect>
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>房产性质：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJFCXZ"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="1" name="SSDJ_FWXX_FCXZ" id="SSDJ_FWXX_FCXZ" value="${busRecord.SSDJ_FWXX_FCXZ}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>房间号：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FWXX_FJH" name="SSDJ_FWXX_FJH" value="${busRecord.SSDJ_FWXX_FJH}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>建筑面积：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FWXX_JZMJ" name="SSDJ_FWXX_JZMJ" value="${busRecord.SSDJ_FWXX_JZMJ}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>朝向：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJCLFPGCX"
                                       dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="0101"
                                       defaultEmptyText="选择朝向" name="SSDJ_FWXX_CX" id="SSDJ_FWXX_CX" value="${busRecord.SSDJ_FWXX_CX}">
                        </eve:eveselect>
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>建成年份：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="32" id="SSDJ_FWXX_JCNF" name="SSDJ_FWXX_JCNF" value="${busRecord.SSDJ_FWXX_JCNF}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">交易合同编号：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="32" id="SSDJ_FWXX_JYHTBH" name="SSDJ_FWXX_JYHTBH" value="${busRecord.SSDJ_FWXX_JYHTBH}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>合同签订时间：</td>
                    <td>
                        <input type="text" class="tab_text Wdate validate[required]" maxlength="32" id="SSDJ_FWXX_HTQDSJ" name="SSDJ_FWXX_HTQDSJ"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" value="${busRecord.SSDJ_FWXX_HTQDSJ}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">单价：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="16" id="SSDJ_FWXX_DJ" name="SSDJ_FWXX_DJ" value="${busRecord.SSDJ_FWXX_DJ}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>交易价格：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FWXX_JYJG" name="SSDJ_FWXX_JYJG" value="${busRecord.SSDJ_FWXX_JYJG}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">本次交易单价：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="16" id="SSDJ_FWXX_BCJYDJ" name="SSDJ_FWXX_BCJYDJ" value="${busRecord.SSDJ_FWXX_BCJYDJ}">
                    </td>
                    <td class="tab_width">合同金额：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="16" id="SSDJ_FWXX_HTJE" name="SSDJ_FWXX_HTJE" value="${busRecord.SSDJ_FWXX_HTJE}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">合同约定房屋交付时间：</td>
                    <td>
                        <input type="text" class="tab_text Wdate" maxlength="32" id="SSDJ_FWXX_JFSJ" name="SSDJ_FWXX_JFSJ"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" value="${busRecord.SSDJ_FWXX_JFSJ}">
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>本次交易金额：</td>
                    <td>
                        <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_FWXX_BCJYJE" name="SSDJ_FWXX_BCJYJE" value="${busRecord.SSDJ_FWXX_BCJYJE}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>房产类型：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJFCLX"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyText="选择房产类型" name="SSDJ_FWXX_FCLX" id="SSDJ_FWXX_FCLX" value="${busRecord.SSDJ_FWXX_FCLX}">
                        </eve:eveselect>
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>权属转移对象：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJCLFQSZYDX"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="20203" name="SSDJ_FWXX_QSZYDX" id="SSDJ_FWXX_QSZYDX" value="${busRecord.SSDJ_FWXX_QSZYDX}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>权属转移用途：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJQSZYYT"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="201" name="SSDJ_FWXX_QSZYYT" id="SSDJ_FWXX_QSZYYT" value="${busRecord.SSDJ_FWXX_QSZYYT}">
                        </eve:eveselect>
                    </td>
                    <td class="tab_width"><font class="tab_color">*</font>权属转移方式：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJQSZYFS"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyValue="21" name="SSDJ_FWXX_QSZYFS" id="SSDJ_FWXX_QSZYFS" value="${busRecord.SSDJ_FWXX_QSZYFS}">
                        </eve:eveselect>
                    </td>
                </tr>
                <tr>
                    <td class="tab_width">房屋所在片区名称：</td>
                    <td>
                        <%--                        <input type="text" class="tab_text" maxlength="32" id="SSDJ_FWXX_PQMC" name="SSDJ_FWXX_PQMC" value="${busRecord.SSDJ_FWXX_PQMC}">--%>
                        <input class="easyui-combobox" name="SSDJ_FWXX_PQMC_S" id="SSDJ_FWXX_PQMC_S" value="${busRecord.SSDJ_FWXX_PQMC_S}" style="width: 354px;height: 20px;"/>
                        <input type="hidden" name="SSDJ_FWXX_PQMC" id="SSDJ_FWXX_PQMC" value="${busRecord.SSDJ_FWXX_PQMC}" style="width: 354px;height: 20px;"/>
                    </td>
                    <td class="tab_width">房屋所在片区代码：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="32" id="SSDJ_FWXX_PQDM" name="SSDJ_FWXX_PQDM" value="${busRecord.SSDJ_FWXX_PQDM}">
                    </td>
                </tr>
                <tr>
                    <td class="tab_width"><font class="tab_color">*</font>有无电梯：</td>
                    <td>
                        <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="YORN"
                                       dataInterface="dictionaryService.findDatasForSelect"
                                       defaultEmptyText="选择有无电梯" name="SSDJ_FWXX_YWDT" id="SSDJ_FWXX_YWDT" value="${busRecord.SSDJ_FWXX_YWDT}">
                        </eve:eveselect>
                    </td>
                    <td class="tab_width">备注：</td>
                    <td>
                        <input type="text" class="tab_text" maxlength="64" id="SSDJ_FWXX_BZ" name="SSDJ_FWXX_BZ" value="${busRecord.SSDJ_FWXX_BZ}">
                    </td>
                </tr>
            </table>
        </table>
    </div>
    <%--房屋信息结束--%>

    <%--    &lt;%&ndash;出转让信息开始&ndash;%&gt;--%>
    <%--    <div id="ssdjCzrxx" class="ssdjxx">--%>
    <%--        <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">--%>
    <%--            <tr>--%>
    <%--                <th>--%>
    <%--                    <span>出转让信息</span>--%>
    <%--                </th>--%>
    <%--            </tr>--%>
    <%--            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">--%>
    <%--                <tr>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>土地座落位置：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text validate[required]" maxlength="48" id="SSDJ_CZRXX_TDZLWZ" name="SSDJ_CZRXX_TDZLWZ" value="${busRecord.SSDJ_CZRXX_TDZLWZ}">--%>
    <%--                    </td>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>土地属地税务机关：</td>--%>
    <%--                    <td>--%>

    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>房屋所在地行政区划：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text validate[required]" maxlength="48" id="SSDJ_CZRXX_XZQH" name="SSDJ_CZRXX_XZQH" value="${busRecord.SSDJ_CZRXX_XZQH}">--%>
    <%--                    </td>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>房屋所在地乡镇街道：</td>--%>
    <%--                    <td>--%>

    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>土地面积：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text validate[required]" maxlength="48" id="SSDJ_CZRXX_TDMJ" name="SSDJ_CZRXX_TDMJ" value="${busRecord.SSDJ_CZRXX_TDMJ}">--%>
    <%--                    </td>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>合同编号：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text validate[required]" maxlength="48" id="SSDJ_CZRXX_HTBH" name="SSDJ_CZRXX_HTBH" value="${busRecord.SSDJ_CZRXX_HTBH}">--%>
    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>合同签订时间：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text Wdate validate[required]" maxlength="48" id="SSDJ_CZRXX_HTQDSJ" name="SSDJ_CZRXX_HTQDSJ"--%>
    <%--                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" value="${busRecord.SSDJ_CZRXX_HTQDSJ}">--%>
    <%--                    </td>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>出转让时间：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text Wdate validate[required]" maxlength="48" id="SSDJ_CZRXX_CZRSJ" name="SSDJ_CZRXX_CZRSJ"--%>
    <%--                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" value="${busRecord.SSDJ_CZRXX_CZRSJ}">--%>
    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>土地用途：</td>--%>
    <%--                    <td>--%>

    <%--                    </td>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>土地等级：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text validate[required]" maxlength="48" id="SSDJ_CZRXX_TDDJ" name="SSDJ_CZRXX_TDDJ" value="${busRecord.SSDJ_CZRXX_TDDJ}">--%>
    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>交易价格：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text validate[required]" maxlength="48" id="SSDJ_CZRXX_JYJG" name="SSDJ_CZRXX_JYJG" value="${busRecord.SSDJ_CZRXX_JYJG}">--%>
    <%--                    </td>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>外部门交易价格：</td>--%>
    <%--                    <td>--%>
    <%--                        <input type="text" class="tab_text validate[required]" maxlength="48" id="SSDJ_CZRXX_WBMJYJG" name="SSDJ_CZRXX_WBMJYJG" value="${busRecord.SSDJ_CZRXX_WBMJYJG}">--%>
    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--                <tr>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>成交价格是否含税：</td>--%>
    <%--                    <td>--%>

    <%--                    </td>--%>
    <%--                    <td class="tab_width"><font class="tab_color">*</font>出转让方式代码：</td>--%>
    <%--                    <td>--%>

    <%--                    </td>--%>
    <%--                </tr>--%>
    <%--            </table>--%>
    <%--        </table>--%>
    <%--    </div>--%>
    <%--    &lt;%&ndash;出转让信息结束&ndash;%&gt;--%>

    <%--买方信息开始--%>
    <div id="ssdjBuyxx" class="ssdjxx" style="display: none;">
        <div id="ssdjBuyInfo" trid="">
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                <tr>
                    <th>
                        <span>买方信息</span>
                        <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitBuyInfo('1');"/>
                        <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitBuyInfo('0');"/>
                    </th>
                </tr>
                <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="4816" id="SSDJ_BUY_XM" name="SSDJ_BUY_XM" value="${busRecord.SSDJ_BUY_XM}">
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>国籍：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJGJ"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyValue="156" name="SSDJ_BUY_GJ" id="SSDJ_BUY_GJ" value="${busRecord.SSDJ_BUY_GJ}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJZZLX"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择证件类型" name="SSDJ_BUY_ZJLX" id="SSDJ_BUY_ZJLX" value="${busRecord.SSDJ_BUY_ZJLX}">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="32" id="SSDJ_BUY_ZJHM" name="SSDJ_BUY_ZJHM" value="${busRecord.SSDJ_BUY_ZJHM}">
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_BUY_LXDH" name="SSDJ_BUY_LXDH" value="${busRecord.SSDJ_BUY_LXDH}">
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>联系地址：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="64" id="SSDJ_BUY_LXDZ" name="SSDJ_BUY_LXDZ" value="${busRecord.SSDJ_BUY_LXDZ}">
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>所占份额(%)：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_BUY_SZFE" name="SSDJ_BUY_SZFE" value="${busRecord.SSDJ_BUY_SZFE}">
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>交易份额(%)：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_BUY_JYFE" name="SSDJ_BUY_JYFE" value="${busRecord.SSDJ_BUY_JYFE}">
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>房屋套次：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJBUYFWTC"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyValue="11" name="SSDJ_BUY_FWTC" id="SSDJ_BUY_FWTC" value="${busRecord.SSDJ_BUY_FWTC}">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>是否已完税：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="YORN"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择是否已完税" name="SSDJ_BUY_SFYWS" id="SSDJ_BUY_SFYWS" value="${busRecord.SSDJ_BUY_SFYWS}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width">涉税信息联系单号：</td>
                        <td>
                            <input type="text" class="tab_text" maxlength="32" id="SSDJ_BUY_SSXXLXDH" name="SSDJ_BUY_SSXXLXDH" value="${busRecord.SSDJ_BUY_SSXXLXDH}">
                        </td>
                    </tr>
                </table>
            </table>
        </div>
        <table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="ssdjBuyInfoList">
            <tr>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">姓名</td>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">操作</td>
            </tr>
        </table>
    </div>
    <%--买方信息结束--%>
    <%--卖方信息开始--%>
    <div id="ssdjSellxx" class="ssdjxx" style="display: none;">
        <div id="ssdjSellInfo">
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                <tr>
                    <th>
                        <span>卖方信息</span>
                        <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="保存" onclick="submitSellInfo('1');"/>
                        <input type="button" class="eflowbutton" style="float:right; margin: 2px 10px; padding: 0 20px;" value="添加" onclick="submitSellInfo('0');"/>
                    </th>
                </tr>
                <table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_SELL_XM" name="SSDJ_SELL_XM" value="${busRecord.SSDJ_SELL_XM}">
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>国籍：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJGJ"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyValue="156" name="SSDJ_SELL_GJ" id="SSDJ_SELL_GJ" value="${busRecord.SSDJ_SELL_GJ}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJZZLX"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择证件类型" name="SSDJ_SELL_ZJLX" id="SSDJ_SELL_ZJLX" value="${busRecord.SSDJ_SELL_ZJLX}">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="32" id="SSDJ_SELL_ZJHM" name="SSDJ_SELL_ZJHM" value="${busRecord.SSDJ_SELL_ZJHM}">
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_SELL_LXDH" name="SSDJ_SELL_LXDH" value="${busRecord.SSDJ_SELL_LXDH}">
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>联系地址：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="64" id="SSDJ_SELL_LXDZ" name="SSDJ_SELL_LXDZ" value="${busRecord.SSDJ_SELL_LXDZ}">
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>所占份额(%)：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_SELL_SZFE" name="SSDJ_SELL_SZFE" value="${busRecord.SSDJ_SELL_SZFE}">
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>交易份额(%)：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="16" id="SSDJ_SELL_JYFE" name="SSDJ_SELL_JYFE" value="${busRecord.SSDJ_SELL_JYFE}">
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>房屋套次：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJSELLFWTC"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择房屋套次" name="SSDJ_SELL_FWTC" id="SSDJ_SELL_FWTC" value="${busRecord.SSDJ_SELL_FWTC}">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>是否已完税：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="YORN"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择是否已完税" name="SSDJ_SELL_SFYWS" id="SSDJ_SELL_SFYWS" value="${busRecord.SSDJ_SELL_SFYWS}">
                            </eve:eveselect>
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>上次取得房屋时间：</td>
                        <td>
                            <input type="text" class="tab_text Wdate validate[required]" maxlength="32" id="SSDJ_SELL_SCQDFWSJ" name="SSDJ_SELL_SCQDFWSJ"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" value="${busRecord.SSDJ_SELL_SCQDFWSJ}">
                        </td>
                        <td class="tab_width"><font class="tab_color">*</font>上次取得房屋成本：</td>
                        <td>
                            <input type="text" class="tab_text validate[required]" maxlength="32" id="SSDJ_SELL_SCQDFWCB" name="SSDJ_SELL_SCQDFWCB" value="${busRecord.SSDJ_SELL_SCQDFWCB}">
                        </td>
                    </tr>
                    <tr>
                        <td class="tab_width"><font class="tab_color">*</font>上次取得房屋方式：</td>
                        <td>
                            <eve:eveselect clazz="tab_text w280 validate[required]" dataParams="SSDJSCQDFWFS"
                                           dataInterface="dictionaryService.findDatasForSelect"
                                           defaultEmptyText="选择上次取得房屋方式" name="SSDJ_SELL_SCQDFWFS" id="SSDJ_SELL_SCQDFWFS" value="${busRecord.SSDJ_SELL_SCQDFWFS}">
                            </eve:eveselect>
                        </td>
                        <td class="tab_width">涉税信息联系单号：</td>
                        <td>
                            <input type="text" class="tab_text" maxlength="32" id="SSDJ_SELL_SSXXLXDH" name="SSDJ_SELL_SSXXLXDH" value="${busRecord.SSDJ_SELL_SSXXLXDH}">
                        </td>
                    </tr>
                </table>
            </table>
        </div>
        <%--卖方信息结束--%>
        <table cellpadding="1" cellspacing="1" class="tab_tk_pro" id="ssdjSellInfoList">
            <tr>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">姓名</td>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">证件号码</td>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
                <td class="tab_width1" style="width: 25%;color: #000; font-weight: bold;text-align: center;">操作</td>
            </tr>
        </table>
    </div>
</div>