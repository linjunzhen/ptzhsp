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

    function submitPowerPeopleInfo(type){

        var flag = validatePowerParams("powerPeopleInfo","required");

        if (!flag) {
            return false;
        }

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
         //取权利人信息第一个，回填申报对象信息
         /* var powerpeopleinfoJson =  $("#powerPeopleInfoList tr").eq(1).find("input[name='iteminfo']").val();
	     if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson && '[]' != powerpeopleinfoJson){
	        var powerpeopleItem = JSON.parse(powerpeopleinfoJson);
        	$("input[name='SQRMC']").val(powerpeopleItem.POWERPEOPLENAME);
            $("select[name='SQRZJLX']").val(powerpeopleItem.POWERPEOPLEIDTYPE);
            $("input[name='SQRSFZ']").val(powerpeopleItem.POWERPEOPLEIDNUM);
            $("select[name='SQRXB']").val(powerpeopleItem.POWERPEOPLESEX);
            $("input[name='SQRSJH']").val(powerpeopleItem.POWERPEOPLEMOBILE);
            $("input[name='SQRLXDZ']").val(powerpeopleItem.POWERPEOPLEADDR);
            
	     } */
    }

    function submitPowerPeopleInfoS(type){

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
            html += ' <a href="javascript:void(0);" style="padding: 4px 10px;" class="eflowbutton" onclick="delDjxxTrQsly(this);">删除</a></td>';
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

            fillInFwGyqk();
        }
    }

    function delDjxxTrQsly(obj) {
        art.dialog.confirm("您确定要删除该记录吗?", function() {
            $(obj).closest("tr").remove();
            fillInFwGyqk();
            //取权利人信息第一个，回填申报对象信息
            /* if($("#powerPeopleInfoList .powerpeopleinfo_tr").length>0){
            	var powerpeopleinfoJson =  $("#powerPeopleInfoList tr").eq(1).find("input[name='iteminfo']").val();
			    if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson && '[]' != powerpeopleinfoJson){
			        var powerpeopleItem = JSON.parse(powerpeopleinfoJson);
			       	$("input[name='SQRMC']").val(powerpeopleItem.POWERPEOPLENAME);
		            $("select[name='SQRZJLX']").val(powerpeopleItem.POWERPEOPLEIDTYPE);
		            $("input[name='SQRSFZ']").val(powerpeopleItem.POWERPEOPLEIDNUM);
		            $("select[name='SQRXB']").val(powerpeopleItem.POWERPEOPLESEX);
		            $("input[name='SQRSJH']").val(powerpeopleItem.POWERPEOPLEMOBILE);
		            $("input[name='SQRLXDZ']").val(powerpeopleItem.POWERPEOPLEADDR);
			    }
            }else{
            	    $("input[name='SQRMC']").val("");
		            $("select[name='SQRZJLX']").val("");
		            $("input[name='SQRSFZ']").val("");
		            $("select[name='SQRXB']").val("");
		            $("input[name='SQRSJH']").val("");
		            $("input[name='SQRLXDZ']").val("");
            } */
        });
        
    }

    /*查询出有多少条权利人信息，根据权利人信息条数回填房屋公用情况*/
    function fillInFwGyqk() {
        var flowSubmitObj = FlowUtil.getFlowObj();
        if (!(flowSubmitObj && flowSubmitObj.busRecord)) {
            var len = $(".powerpeopleinfo_tr").length;
            if (len == 0) {
                $("[name='FW_GYQK']").val('');
            } else if (len == 1) {
                $("[name='FW_GYQK']").val(0);
            } else {
                $("[name='FW_GYQK']").val(1);
            }
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

    function getPowPeopleJson(){
        var datas = [];
        $("#powerPeopleInfoList .powerpeopleinfo_tr").each(function(){
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            datas.push(JSON.parse(iteminfo));
        });
        $("#POWERPEOPLEINFO_JSON").val(JSON.stringify(datas));
        return datas;
    }

    function initPowPeople(items){
        if(items){
            if($("#powerPeopleInfoList_blank_tr")){
                $("#powerPeopleInfoList_blank_tr").remove();
            }
            if($("#powerPeopleInfoList .powerpeopleinfo_tr")){
                $("#powerPeopleInfoList .powerpeopleinfo_tr").remove();
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

    function loadDataPowPeople(){
        var datastr = $("#POWERPEOPLEINFO_JSON").val();
        if(datastr){
            var itemsinfo = JSON.parse(datastr);
            var items = JSON.parse(itemsinfo.items);
            initPowPeople(items);
        }
    }
</script>

<div class="bsbox clearfix">
    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none">
                <span>权利人信息</span>
            </li>
        </ul>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="保存" onclick="submitPowerPeopleInfo('1');"/>
        <input type="button" class="eflowbutton" style="float:right; margin: 10px 5px; padding: 0 20px;" value="添加" onclick="submitPowerPeopleInfo('0')"/>
    </div>
    <div id="powerPeopleInfo" trid="">
        <table cellpadding="0" cellspacing="0" class="bstable1">
            <tr>
                <td style="padding: 10px">
                    <table class="bstable1">
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>权利人姓名：</th>
                            <td><input type="text" class="tab_text validate[required]"
                                       name="POWERPEOPLENAME" maxlength="62"/></td>
                            <th class="tab_width"><font class="tab_color" id="qlblFont" >*</font>权利比例：</th>
                            <td><input type="text" class="tab_text validate[required]"
                                       name="POWERPEOPLEPRO" maxlength="32"/><br/><font class="tab_color">输入比例如：100</font></td>
                        </tr>
                        <tr>
                            <th class="tab_width">权利人性质：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[]" dataParams="QLRXZ"
                                               dataInterface="dictionaryService.findDatasForSelect"
                                               defaultEmptyText="选择权利人性质" name="POWERPEOPLENATURE" id="POWERPEOPLENATURE" >
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">性别：</th>
                            <td>
                                <eve:eveselect clazz="tab_text"
                                               dataParams="sex"
                                               dataInterface="dictionaryService.findDatasForSelect"
                                               defaultEmptyText="选择性别" name="POWERPEOPLESEX" id="POWERPEOPLESEX"></eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>证件类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType" 
                                               dataInterface="dictionaryService.findDatasForSelect" 
                                               defaultEmptyValue="SF" name="POWERPEOPLEIDTYPE" id="POWERPEOPLEIDTYPE" >
                                </eve:eveselect>
                            </td>
                            <th class="tab_width"><font class="tab_color">*</font>证件号码：</th>
                            <td id="test"><input type="text" class="tab_text validate[required]" maxlength="30" id="POWERPEOPLEIDNUM" name="POWERPEOPLEIDNUM" style="float: left;"></td>
                        </tr>
                        <tr>
                            <th class="tab_width"><font class="tab_color">*</font>电话</th>
                            <td>
                                <input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]"
                                       name="POWERPEOPLEMOBILE" maxlength="11"/><font class="tab_color">（用于接收短信提醒）</font>
                            </td>
                            <th class="tab_width">邮政编码：</th>
                            <td><input type="text" class="tab_text validate[,custom[chinaZip]]"
                                       name="POWERPEOPLEPOSTCODE" maxlength="6"/></td>
                        </tr>
                        <tr>
                            <th class="tab_width">地址：</th>
                            <td><input type="text" class="tab_text validate[]"
                                                   name="POWERPEOPLEADDR" maxlength="120"/></td>
                            <th name="isWcnr" class="tab_width"><font class="tab_color">*</font>是否未成年人：</th>
                            <td name="isWcnr">
                                <eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"
                                               dataInterface="dictionaryService.findDatasForSelect"
                                               defaultEmptyValue="0" name="POWERPEOPLEISWCNR" id="POWERPEOPLEISWCNR">
                                </eve:eveselect>
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">负责人姓名：</th>
                            <td><input type="text" class="tab_text validate[]"
                                       name="POWLEGALNAME" maxlength="62"/></td>
                            <th class="tab_width">负责人电话：</th>
                            <td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]"
                                       name="POWLEGALTEL" maxlength="13"/></td>
                        </tr>
                        <tr>
                            <th class="tab_width">证件类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                                               dataInterface="dictionaryService.findDatasForSelect" 
                                               defaultEmptyText="选择证件类型" name="POWLEGALIDTYPE" id="POWLEGALIDTYPE" >
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">证件号码：</th>
                            <td>
                                <input type="text" class="tab_text validate[]" maxlength="30" id="POWLEGALIDNUM" name="POWLEGALIDNUM" style="float: left;">
                            </td>
                        </tr>
                        <tr>
                            <th class="tab_width">代理人姓名：</th>
                            <td><input type="text" class="tab_text validate[]"
                                       name="POWAGENTNAME" maxlength="62"/></td>
                            <th class="tab_width">代理人电话：</th>
                            <td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]"
                                       name="POWAGENTTEL" maxlength="13"/></td>
                        </tr>
                        <tr>
                            <th class="tab_width">证件类型：</th>
                            <td>
                                <eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
                                               dataInterface="dictionaryService.findDatasForSelect" 
                                               defaultEmptyText="选择证件类型" name="POWAGENTIDTYPE" id="POWAGENTIDTYPE" >
                                </eve:eveselect>
                            </td>
                            <th class="tab_width">证件号码：</th>
                            <td>
                                <input type="text" class="tab_text validate[]" maxlength="30" id="POWAGENTIDNUM" name="POWAGENTIDNUM" style="float: left;">
                            </td>
                        </tr>
                    </table>
                </td>
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
</div>