function checkBoxItemNames(val){
    if ($("input[name='itemNames']").prop("checked")) {
        $("input[type='checkbox'][name='itemName']").prop("checked",true);//全选
        $("input[type='checkbox'][name='itemName']").each(function(){
            var v1=$(this).val();
        });

    } else {
        $("input[type='checkbox'][name='itemName']").prop("checked",false);  //取消全选
    }
}
var needCheckedItemCodes="";
/**
 * resetflag  :是否重置已选择的事项，当搜索时不重置,0不重置
 * @param resetflag
 */
function setItemCodesSelected(resetflag){
    var itemCodesSelected = "";
    var itemNamesSelected ="";
    if(resetflag==0){
        itemCodesSelected = $("input[name='itemCodesSelected']").val();
        itemNamesSelected = $("input[name='itemNamesSelected']").val();
    }
    $("input[type='checkbox'][name='itemName']").each(function () {
        //勾选事件
        if($(this).prop("checked")){
             if(itemCodesSelected.indexOf($(this).val())<0){
                 if (itemCodesSelected != "") {
                     itemCodesSelected += ",";
                     itemNamesSelected += ",";
                 }
                itemCodesSelected += $(this).val();
                itemNamesSelected += $(this).attr("title");
            }
        }else{
            //反勾选事件
            itemCodesSelected=itemCodesSelected.replace("," + $(this).val(),'');
            itemNamesSelected=itemNamesSelected.replace("," +  $(this).attr("title"),'');
            itemCodesSelected=itemCodesSelected.replace($(this).val(),'');
            itemNamesSelected=itemNamesSelected.replace( $(this).attr("title"),'');
        }
        if($(this).attr("data-title")=="1"&&needCheckedItemCodes.indexOf($(this).val()<0)){
            needCheckedItemCodes+=","+$(this).val();
        }
    });
    $("input[name='itemCodesSelected']").val(itemCodesSelected);
    $("input[name='S_ITEM_CODE']").val(itemCodesSelected);
    $("input[name='itemNamesSelected']").val(itemNamesSelected);
    var html = "";
    var j=0;
    if (itemCodesSelected != "") {
        var itemCodes = itemCodesSelected.split(",");
        var itemNames = itemNamesSelected.split(",");
        for (var i = 0; i < itemCodes.length; i++) {
            j=i+1;
            var itemCode = itemCodes[i];
            var itemName = itemNames[i];
            html += "    <div class=\"tr\" id=\"selectItemCodeDiv" + itemCode + "\" data-title=\"" + itemName + "\" >\n" +
                "  <div class=\"td\"><i>"+j+"</i>" + itemName + "</div>\n" +
                "  <div class=\"th min\"> " ;
            if(needCheckedItemCodes.indexOf(itemCode)<0){
                html+= " <a class=\"del\" onclick=\"removeItemCodeSelected('"+itemCode+","+itemName+"')\"  data-title=\"" + itemCode + "\"></a> " ;
            }
            html+= " </div>\n" + "     </div>";
        }

    }
    $("#toolBarNum").text(j);
    $("#selectedItemName").html(html);
    setItemMaterList();  //设置材料显示
}



function removeItemCodeSelected(itemCode,itemName){
    $("#checkBox" + itemCode).prop("checked", false);
    var itemCodesSelected = "";
    var itemNamesSelected = "";
    itemCodesSelected = $("input[name='itemCodesSelected']").val();
    itemNamesSelected = $("input[name='itemNamesSelected']").val();
    itemCodesSelected=itemCodesSelected.replace("," + itemCode,'');
    itemNamesSelected=itemNamesSelected.replace("," + itemName,'');
    itemCodesSelected=itemCodesSelected.replace(itemCode,'');
    itemNamesSelected=itemNamesSelected.replace(itemName,'');

    $("input[name='itemCodesSelected']").val(itemCodesSelected);
    $("input[name='S_ITEM_CODE']").val(itemCodesSelected);
    $("input[name='itemNamesSelected']").val(itemNamesSelected);
    var html = "";
    var j = 0;
    if (itemCodesSelected != "") {
        var itemCodes = itemCodesSelected.split(",");
        var itemNames = itemNamesSelected.split(",");
        for (var i = 0; i < itemCodes.length; i++) {
            j = i + 1;
            var itemCode = itemCodes[i];
            var itemName = itemNames[i];
            html += "    <div class=\"tr\" id=\"selectItemCodeDiv" + itemCode + "\" data-title=\"" + itemName + "\" >\n" +
                "  <div class=\"td\"><i>" + j + "</i>" + itemName + "</div>\n" +
                "  <div class=\"th min\"> ";
            if (needCheckedItemCodes.indexOf(itemCode) < 0) {
                html += " <a class=\"del\" onclick=\"removeItemCodeSelected('" + itemCode + "','" + itemName + "')\"  data-title=\"" + itemCode + "\"></a> ";
            }
            html += " </div>\n" + "     </div>";
        }

    }
    $("#toolBarNum").text(j);
    $("#selectedItemName").html(html);
    setItemMaterList();  //设置材料显示
}

$(function () {
    <!--判断是否登录开始-->
    var username = $("input[name='username']").val();
    if(null==username||""==username){
        window.location.href = "userInfoController/mztLogin.do?returnUrl=projectWebsiteController.do?index";
    }else{
        searchMyProjectInfo();
    }
    <!--判断是否登录结束-->

    <!--初始化事项选择开始-->
    var topicCode=$("input[name='TOPIC_CODE']").val();
    topicClickOfSelectServiceItem(topicCode);
    //setItemCodesSelected();//初始化事项选择；
    <!--初始化事项选择结束-->


    if($("input[name='FINISH_GETTYPE']:checked").val()=='02'){
        $("#addr").attr("style","");
        $("#addressee").attr("style","");
        $("#addrmobile").attr("style","");
        $("#addrprov").attr("style","");
        $("#addrcity").attr("style","");
        $("#addrpostcode").attr("style","");
        $("#addrremarks").attr("style","");

        $("#result_send_addressee").attr("disabled",false);
        $("#result_send_mobile").attr("disabled",false);
        $("#province").attr("disabled",false);
        $("#city").attr("disabled",false);
        $("#result_send_addr").attr("disabled",false);
    }


    $("#mat_send_addressee").attr("disabled","disabled");
    $("#mat_send_mobile").attr("disabled","disabled");
    $("#matprovince").attr("disabled","disabled");
    $("#matcity").attr("disabled","disabled");
    $("#mat_send_addr").attr("disabled","disabled");


    $("input[name='FINISH_GETTYPE']").click(function(){
        var radio = document.getElementsByName("FINISH_GETTYPE");
        for (i=0; i<radio.length; i++) {
            if (radio[i].checked) {
                var str=radio[i].value;
                if("02"==str){
                    $("#addr").attr("style","");
                    $("#addressee").attr("style","");
                    $("#addrmobile").attr("style","");
                    $("#addrprov").attr("style","");
                    $("#addrcity").attr("style","");
                    $("#addrpostcode").attr("style","");
                    $("#addrremarks").attr("style","");

                    $("#result_send_addressee").attr("disabled",false);
                    $("#result_send_mobile").attr("disabled",false);
                    $("#province").attr("disabled",false);
                    $("#city").attr("disabled",false);
                    $("#result_send_addr").attr("disabled",false);
                }else{
                    $("#addr").attr("style","display:none;");
                    $("#addressee").attr("style","display:none;");
                    $("#addrmobile").attr("style","display:none;");
                    $("#addrprov").attr("style","display:none;");
                    $("#addrcity").attr("style","display:none;");
                    $("#addrpostcode").attr("style","display:none;");
                    $("#addrremarks").attr("style","display:none;");


                    $("#result_send_addressee").attr("disabled","disabled");
                    $("#result_send_mobile").attr("disabled","disabled");
                    $("#province").attr("disabled","disabled");
                    $("#city").attr("disabled","disabled");
                    $("#result_send_addr").attr("disabled","disabled");
                }
            }
        }
    });

    $("input[name='MAT_SENDTYPE']").click(function(){
        var radio = document.getElementsByName("MAT_SENDTYPE");
        for (i=0; i<radio.length; i++) {
            if (radio[i].checked) {
                var str=radio[i].value;
                if("02"==str){
                    $("#mataddr").attr("style","");
                    $("#mataddrselect").attr("style","");
                    $("#mataddressee").attr("style","");
                    $("#mataddrmobile").attr("style","");
                    $("#mataddrprov").attr("style","");
                    $("#matpostcode").attr("style","");
                    $("#mataddrcity").attr("style","");
                    $("#mataddrpostcode").attr("style","");
                    $("#mataddrremarks").attr("style","");

                    $("#mat_send_addressee").attr("disabled",false);
                    $("#mat_send_mobile").attr("disabled",false);
                    $("#matprovince").attr("disabled",false);
                    $("#matcity").attr("disabled",false);
                    $("#mat_send_addr").attr("disabled",false);
                }else{
                    $("#mataddr").attr("style","display:none;");
                    $("#mataddrselect").attr("style","display:none;");
                    $("#mataddressee").attr("style","display:none;");
                    $("#mataddrmobile").attr("style","display:none;");
                    $("#mataddrprov").attr("style","display:none;");
                    $("#matpostcode").attr("style","display:none;");
                    $("#mataddrcity").attr("style","display:none;");
                    $("#mataddrpostcode").attr("style","display:none;");
                    $("#mataddrremarks").attr("style","display:none;");

                    $("#mat_send_addressee").attr("disabled","disabled");
                    $("#mat_send_mobile").attr("disabled","disabled");
                    $("#matprovince").attr("disabled","disabled");
                    $("#matcity").attr("disabled","disabled");
                    $("#mat_send_addr").attr("disabled","disabled");
                }
            }
        }
    });

});

/*查看办件详情，项目初始化*/
$(function(){
    var flowSubmitObj = FlowUtil.getFlowObj();
    if(flowSubmitObj){
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
            var typeCode = flowSubmitObj.busRecord.INDUSTRY;
            $.post( "dicTypeController/info.do",{
                    typeCode:typeCode,path:"4028819d51cc6f280151cde6a3f00027"},
                function(responseText1, status, xhr) {
                    var resultJson1 = $.parseJSON(responseText1);
                    if(null!=resultJson1){
                        $("#industry").html('<option value="'+resultJson1.TYPE_CODE+'" selected="selected">'+resultJson1.TYPE_NAME+'</option>')
                    }else{
                        $("#industry").html('<option value="'+typeCode+'" selected="selected">'+typeCode+'</option>')
                    }
                });
            var typeCode2=flowSubmitObj.busRecord.PLACE_CODE;
            $.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
                function(responseText2,status,xhr){
                    var  resultJson2=$.parseJSON(responseText2);
                    if(null!=resultJson2){
                        $("#placeCode").html('<option value="'+resultJson2.TYPE_CODE+'" selected="selected">'+resultJson2.TYPE_NAME+'</option>')
                    }else{
                        $("#placeCode").html('<option value="'+typeCode2+'" selected="selected">'+typeCode2+'</option>')
                    }
                });
            var typeCode3 = flowSubmitObj.busRecord.INDUSTRY_STRUCTURE;
            $.post( "dicTypeController/info.do",{
                    typeCode:typeCode3},
                function(responseText3, status, xhr) {
                    var resultJson3 = $.parseJSON(responseText3);
                    if(null!=resultJson3){
                        $("#industryStructure").html('<option value="'+resultJson3.TYPE_CODE+'" selected="selected">'+resultJson3.TYPE_NAME+'</option>')
                    } else{
                        $("#industryStructure").html('<option value="'+typeCode3+'" selected="selected">'+typeCode3+'</option>')
                    }
                });
            if(flowSubmitObj.busRecord.RUN_STATUS!=0 && flowSubmitObj.EFLOW_CURUSEROPERNODENAME!='开始'){
                $("#userinfo_div input").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                $("#userinfo_div select").each(function(index){
                    $(this).attr("disabled","disabled");
                });
            } else{
                $("[name='PROJECT_CODE']").prop("readonly", false);
                $("[name='PROJECT_CODE']").prop("disabled", "");
                //loadTZXMXXData();
            }
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_GCXM_XMJBXX_FORM");
            //获取表单字段权限控制信息
            var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
            //初始化表单字段权限控制
            FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_GCXM_XMJBXX_FORM");

        }
    }

});




function  restForm(searchFormName){
    $("#"+searchFormName)[0].reset();
}
function listItem(){
    var url="";
    var typeId=$("body > div.eui-main  div.eui-card.eui-declaration.wysb1.on > div.eui-filter.i1.eui-flex.wrap > span.on").attr("data-title");
    var stageId=$("body > div.eui-main >  div.eui-card.eui-declaration.wysb1.on > div.eui-filter.i2.eui-flex.wrap > span.on").attr("data-title");
    var applyUrl="";
    if(typeof (stageId)=='undefined'){
        applyUrl="projectWebsiteController.do?applyProjectView&typeId="+typeId;
    }else{
        applyUrl="projectWebsiteController.do?applyProjectView&typeId="+typeId+"&stageId="+stageId;
    }
    window.location.href=__ctxPath+applyUrl;
}

function typeClickOfSelectServiceItem(val) {
    var applyUrl="";
    applyUrl="projectWebsiteController.do?applyProjectView&typeId="+val;
    window.location.href=__ctxPath+applyUrl;
}

function stateClickOfSelectServiceItem(val) {
    var typeId= $("input[name='TYPE_ID']").val();
    var applyUrl="";
    applyUrl="projectWebsiteController.do?applyProjectView&typeId="+typeId+"&stageId="+val;
    window.location.href=__ctxPath+applyUrl;
}

function topicClickOfSelectServiceItem(val) {
    var stageId= $("input[name='STAGE_ID']").val();
    $("input[name='TOPIC_CODE']").val(val);
    var itemName=$("input[name='ITEM_NAME_SELECTSERVICEITEM']").val();
    var  url="projectWebsiteController/findServiceItemByStageIdAndTopicCode.do?stageId="+stageId+"&topicCode="+val+"&itemName="+itemName;
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        cache: false,
        success: function (responseText) {
            var datas = $.parseJSON(responseText);
            $("#listItemtbody").html("");
            var html="";
            for (var index in datas) {
                var isKeyItem = datas[index].IS_KEY_ITEM;
                html+="<tr class=\"" + datas[index].ITEM_CODE + "tr\">";
                html+=" <th><input class=\"eui-checkbox \" name=\"itemName\"  onclick=\"setItemCodesSelected()\" id=\"checkBox" + datas[index].ITEM_CODE + "\"";
                if (isKeyItem == '1') {
                    html+=" checked=\"checked\"  data-title=\"1\"   disabled=\"disabled\" ";
                }
                html+=" value=\""+datas[index].ITEM_CODE +"\" type=\"checkbox\" title=\""+datas[index].ITEM_NAME+"\" /></th>  ";
                html+="  <td>" + datas[index].ITEM_NAME ;
                if (isKeyItem == '1') {
                    html+="<span>必经事项</span>";
                }
                html+="</td> <th><a href=\"serviceItemController/bsznDetail.do?itemCode=" + datas[index].ITEM_CODE + "&projectType=1\">办事指南</a></th></tr>";
            }
            $("#listItemtbody").html(html);
            setItemCodesSelected();
        }
    });
}



function searchMyProjectInfo(){
    var myProjectCode=$("input[name='myProjectCode']").val();
    var myProjectName=$("input[name='myProjectName']").val();
    var url="projectWebsiteController/findMyProjectInfo.do?myProjectCode="+myProjectCode+"&myProjectName="+myProjectName;
    url=encodeURI(url);
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        cache: false,
        success: function (responseText) {
            var resultJson = $.parseJSON(responseText);
            $("#myProjectInfo").html("");
            if(resultJson.success){
                var jsonDatas=$.parseJSON(resultJson.jsonString);
                var datas=jsonDatas.data;
                for(var index in datas){
                    $("#myProjectInfo").append("  <tr>\n" +
                        "                                    <th><input class=\"eui-checkbox\" type=\"checkbox\"  value='"+datas[index].PROJECT_CODE+"' name='myProjectCodeOk'/></th>\n" +
                        "                                    <td>"+datas[index].PROJECT_CODE+"</td>\n" +
                        "                                    <td>"+datas[index].PROJECT_NAME+"</td>\n" +
                        "                                </tr>");
                }
            }
        }
    });
}

function myProjectInfoOk(){
    var chk_value="";
    var i=0;
    $('input[name="myProjectCodeOk"]:checked').each(function(){
        i=i+1;
        chk_value=$(this).val();
    });

    if(i>1){
        alert("只能选择一个项目");
    }else{
        var code =chk_value;
        if(null==code||''==code){
            art.dialog({
                content: "请选择一项投资项目",
                icon:"error",
                ok: true
            });
        }else {
            $.post("projectWebsiteController/loadLocalXMJBXXB.do", {
                    projectCode: code
                },
                function (responseText, status, xhr) {
                    var resultJson = $.parseJSON(responseText);
                    for (var key in resultJson) {
                        if (key == 'INDUSTRY') {
                            var typeCode = resultJson[key];
                            $.post("dicTypeController/info.do", {
                                    typeCode: typeCode, path: "4028819d51cc6f280151cde6a3f00027"
                                },
                                function (responseText1, status, xhr) {
                                    var resultJson1 = $.parseJSON(responseText1);
                                    if (null != resultJson1 && null != resultJson1.TYPE_CODE && '' != resultJson1.TYPE_CODE) {
                                        $("[name='INDUSTRY']").html('<option value="' + resultJson1.TYPE_CODE + '" selected="selected">' + resultJson1.TYPE_NAME + '</option>')
                                        //$("[name='INDUSTRY']").selectMatch();
                                    } else {
                                        $("[name='INDUSTRY']").html('<option value="' + typeCode + '" selected="selected">' + typeCode + '</option>')
                                        //$("[name='INDUSTRY']").selectMatch();
                                    }
                                });

                        } else if (key == 'PLACE_CODE') {
                            var typeCode2 =resultJson[key];
                            $.post("dicTypeController/placeInfo.do", {typeCode: typeCode2},
                                function (responseText2, status, xhr) {
                                    var resultJson2 = $.parseJSON(responseText2);
                                    if (null != resultJson2) {
                                        $("[name='PLACE_CODE']").html('<option value="' + resultJson2.TYPE_CODE + '" selected="selected">' + resultJson2.TYPE_NAME + '</option>')
                                        //$("[name='PLACE_CODE']").selectMatch();
                                    } else {
                                        $("[name='PLACE_CODE']").html('<option value="' + typeCode2 + '" selected="selected">' + typeCode2 + '</option>')
                                        //$("[name='PLACE_CODE']").selectMatch();
                                    }
                                });
                        } else if (key == 'PROJECT_CODE') {
                            $("[name='PROJECTCODE']").val(resultJson[key]);
                            $("[name='PROJECT_CODE']").val(resultJson[key]);
                        } else if (key == 'PROJECT_NAME') {
                            $("[name='PROJECT_NAME']").val(resultJson[key]);
                        } else if (key == 'INDUSTRY_STRUCTURE') {
                            var typeCode3 =resultJson[key];
                            $.post("dicTypeController/info.do", {
                                    typeCode: typeCode3
                                },
                                function (responseText3, status, xhr) {
                                    var resultJson3 = $.parseJSON(responseText3);
                                    if (null != resultJson3) {
                                        $("[name='INDUSTRY_STRUCTURE']").html('<option value="' + resultJson3.TYPE_CODE + '" selected="selected">' + resultJson3.TYPE_NAME + '</option>')
                                        //$("[name='INDUSTRY_STRUCTURE']").selectMatch();
                                    } else {
                                        $("[name='INDUSTRY_STRUCTURE']").html('<option value="' + typeCode3 + '" selected="selected">' + typeCode3 + '</option>')
                                       // $("[name='INDUSTRY_STRUCTURE']").selectMatch();
                                    }
                                });
                        } else if (key == 'LEREP_INFO') {
                            //setTpl(JSON.stringify(resultJson[key]));
                            var lerepInfoList =$.parseJSON(resultJson[key]);
                            for (var j = 0; j < lerepInfoList.length; j++) {
                                initXmdwxx(lerepInfoList[j], j);
                            }
                            $("[name='LEREP_INFO']").val(resultJson[key]);
                        } else if (key == 'contribution_info') {
                            $("[name='CONTRIBUTION_INFO']").val(JSON.stringify(resultJson[key]));
                        } else if (key == 'get_land_mode') {
                            if (null !=resultJson[key] && '' !=resultJson[key]) {
                                $("[name='GET_LAND_MODE']").val(resultJson[key]);
                            } else {
                                $("[name='" + key.toUpperCase() + "']").prop("readonly", false);
                                $("[name='" + key.toUpperCase() + "']").prop("disabled", "");
                            }
                        }else{
                            $("#tzjbxx select").attr("readonly", true);
                            $("[name='" + key.toUpperCase() + "']").val(resultJson[key]);
                        }
                        $("[name='" + key.toUpperCase() + "']").attr("readonly", true);
                        $("select[name='" + key.toUpperCase() + "']").prop("disabled", "disabled");
                        //$("select[name='" + key.toUpperCase() + "']").selectMatch();
                    }
                    $("#tzjbxx input").attr("readonly", true);
                    $("#tzjbxx textarea").attr("readonly", true);
                    $("#tzjbxx select").attr("readonly", true);
                    if ($("#foreignabroadFlag").val() == 1) {
                        $("#jwtzxx").attr("style", "display:none;");
                        $("#wstzxx").attr("style", "display:;");
                    } else if ($("#foreignabroadFlag").val() == 2) {
                        $("#wstzxx").attr("style", "display:none;");
                        $("#jwtzxx").attr("style", "display:;");
                    } else {
                        $("#wstzxx").attr("style", "display:none;");
                        $("#jwtzxx").attr("style", "display:none;");
                    }
                    if ($("#totalMoney").val() == 0) {
                        $("#totalMoneyExplain").removeClass("");
                        $("#totalMoneyExplain").toggleClass('validate[required]');
                    } else {
                        $("#totalMoneyExplain").removeClass("validate[required]");
                        $("#totalMoneyExplain").toggleClass('');
                    }
                    // lerepContributionData();
                    $(".eui-porject-select").fadeOut(300);
                    $("#tooltipsProjectCode").css("display", "none");
                    $('#project_code').removeClass("error");
                }
            );
        }
    }
}
function initXmdwxx(dwxx,i){

    if(i>0){
        $("#xmdwxxDiv").html(xmdwxxDivHtml);
    }
    initFormObjValue(dwxx,$("#xmdwxxDiv table").eq(i));
}
/**
 * 初始化表单字段值
 * @param {} fieldValueObj
 * @param {} elementObj
 */
function initFormObjValue(fieldValueObj,elementObj){
    for(var fieldName in fieldValueObj){
        //获取目标控件对象
        var targetFields = elementObj.find("[name$='"+fieldName+"']");
        targetFields.each(function(){
            var targetField = $(this);
            var type = targetField.attr("type");
            var tagName = targetField.get(0).tagName;
            var fieldValue = fieldValueObj[fieldName];

            if(type=="radio"){
                var radioValue = targetField.val();
                if(radioValue==fieldValue){
                    $(this).attr("checked","checked");
                }
            }else if(type=="checkbox"){
                var checkBoxValue = targetField.val();
                var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
                if(isChecked){
                    $(this).attr("checked","checked");
                }
            }else if(tagName=="SELECT"){
                targetField.children("option[value='"+fieldValueObj[fieldName]+"']")
                    .attr("selected", "selected");
                targetField.prop("disabled", "disabled");
                //targetField.selectMatch();
            }else{
                targetField.val(fieldValueObj[fieldName]);
            }
            if(fieldName!='dwlx'){
                targetField.prop("readonly", true);
                targetField.attr("readonly", true);
            }
        });
    }
}
function isShowWztzxx(){
    if($("#foreignabroadFlag").val()==1){
        $("#jwtzxx").attr("style","display:none;");
        $("#wstzxx").attr("style","display:;");
    }else if($("#foreignabroadFlag").val()==2){
        $("#wstzxx").attr("style","display:none;");
        $("#jwtzxx").attr("style","display:;");
    }else{
        $("#wstzxx").attr("style","display:none;");
        $("#jwtzxx").attr("style","display:none;");
    }
    if($("#totalMoney").val()==0){
        $("#totalMoneyExplain").removeClass("");
        $("#totalMoneyExplain").toggleClass('validate[required]');
    }else{
        $("#totalMoneyExplain").removeClass("validate[required]");
        $("#totalMoneyExplain").toggleClass('');
    }
}
function getDicNames(typeCode,dicCodes){
    $.post( "dictionaryController/textname.do",
        {
            typeCode:typeCode,dicCodes:dicCodes
        },
        function(responseText3, status, xhr) {
            return responseText3;
        });
}

function selectResaddr(){
    //alert($("input[name='USER_ID']").val());
    //var userId = $("input[name='USER_ID']").val();
    parent.$.dialog.open("userInfoController/addrSelector.do", {
        title : "材料寄送地址选择器",
        width:"800px",
        lock: true,
        resize:false,
        height:"500px",
        close: function () {
            var selectAddrInfo = art.dialog.data("selectAddrInfo");
            if(selectAddrInfo){
                $("input[name='RESULT_SEND_ADDRESSEE']").val(selectAddrInfo.recName);
                $("input[name='RESULT_SEND_MOBILE']").val(selectAddrInfo.mobile);
                $("input[name='RESULT_SEND_PROV']").val(selectAddrInfo.province);
                $("input[name='RESULT_SEND_CITY']").val(selectAddrInfo.city);
                $("input[name='RESULT_SEND_COUNTY']").val(selectAddrInfo.county);
                $("input[name='RESULT_SEND_POSTCODE']").val(selectAddrInfo.postcode);
                $("input[name='RESULT_SEND_ADDR']").val(selectAddrInfo.detailAdd);
                $('#province').combobox('setValue', selectAddrInfo.province);
                $('#city').combobox('setValue', selectAddrInfo.city);
                $('#county').combobox('setValue', selectAddrInfo.county);

                art.dialog.removeData("selectAddrInfo");
            }
        }
    }, false);
}
function  goto2() {
    var selectedCode=$("input[name='itemCodesSelected']").val();
    if(selectedCode==""){
        alert("请选择需要申报的事项");
    }else{
        $(".wysb1").removeClass("on");
        $(".wysb2").addClass("on");
        $(".eui-step .i2").addClass("on");
        $('html, body').animate({scrollTop: 0},300);
        return false;
    }
}

function  validateGoto2() {
    var selectedCode=$("input[name='itemCodesSelected']").val();
    if(selectedCode==""){
        alert("请选择需要申报的事项");
    }else{
        $(".wysb1").removeClass("on");
        $(".wysb4").removeClass("on");
        $(".wysb2").addClass("on");
        $(".eui-step .i2").addClass("on");
        $('html, body').animate({scrollTop: 0},300);
        return false;
    }
}

function  validateGoto3() {
    $(".wysb2").removeClass("on");
    $(".wysb4").removeClass("on");
    $(".wysb3").addClass("on");
    $(".eui-step .i3").addClass("on");
    $('html, body').animate({scrollTop: 0},300);
    //validateSingleForm('projectInfoForm');
    return false;
}
function validateGoto4() {
    $(".wysb3").removeClass("on");
    $(".eui-step .i4").addClass("on");
    $('html, body').animate({scrollTop: 0},300);
    return false;

}
/**
 * 设置事项材料
 */
function setItemMaterList(){
    $("#itemMaterDiv").html("");
    var itemCodesSelected=$("input[name='itemCodesSelected']").val();
    var itemCoes=itemCodesSelected.split(",");
    var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
    var exeId = "";
    if(EFLOW_FLOWOBJ){
        var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
        if(eflowObj.busRecord){
            exeId = eflowObj.busRecord.EXE_ID;
        }
    }
    if (exeId == '') {
        $.ajaxSettings.async = false;
        for (var i = 0; i < itemCoes.length; i++) {
            var itemCode = itemCoes[i];
            $.post("projectWebsiteController/applyItemMaterList.do", {
                itemCode: itemCode,
                exeId: exeId,
                STAGE_ID: $("[name='STAGE_ID']").val(),
                PROJECT_CODE: $("[name='PROJECT_CODE']").val(),
                isWebsite: '${isWebsite}',
                materModelView: 'applyMaterList2'
            }, function (responseText, status, xhr) {
                $("#itemMaterDiv").append(responseText);
                $("#itemMaterDiv").find('select').prop("disabled", "disabled");
            });
        }
        $.ajaxSettings.async = true;
    }
}


function validateForm(formId) {
    $("#"+formId).validationEngine("validate");
}

function  validateAllForm() {
    $("#projectInfoForm").validationEngine("validate");
    $("#applyuserinfoForm").validationEngine("validate");
    $("#applyMaterListForm").validationEngine("validate");
}
function submitProjectApply(formId) {
    //var valiateTabForm = validateWebsiteTabForm();
    var validateResult =$("#projectInfoForm").validationEngine("validate");
    if(!validateResult){
        validateGoto2();
        return;
    }
    validateResult =$("#applyuserinfoForm").validationEngine("validate");
    if(!validateResult){
        validateGoto3();
        return;
    }
    validateResult =$("#applyMaterListForm").validationEngine("validate");
    if(!validateResult){
        validateGoto4();
        return;
    }
    var validateResult = $("#"+formId).validationEngine("validate");
    var valifileResult = AppUtil.getSubmitMaterFileJson(formId, 1);
    if (validateResult) {
        if (valifileResult) {
            art.dialog.confirm("请确认必要材料是否提供。预审时间为1个工作日，您确定要提交该流程吗?", function () {
                submitWebSiteFlowForm(formId);
            });
        }
    }
}

function submitWebSiteFlowForm(formId){
    //先判断表单是否验证通过
    var validateResult =$("#"+formId).validationEngine("validate");
    if(validateResult){
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(formId,1);
        if(submitMaterFileJson||submitMaterFileJson==""){
            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
            //将其转换成JSON对象
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            //先获取表单上的所有值
            var forms = $("form[id]");
            forms.each(function() {
                var formId = $(this).attr("id");
                var formData = FlowUtil.getFormEleData(formId);
                for ( var index in formData) {
                    flowSubmitObj[eval("index")] = formData[index];
                }
            });


            flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
            var postParam = $.param(flowSubmitObj);
            AppUtil.ajaxProgress({
                url : "webSiteController.do?submitApply",
                params : postParam,
                callback : function(resultJson) {
                    if(resultJson.OPER_SUCCESS){
                        $(".wysb4").removeClass("on");
                        $(".wysb5").addClass("on");
                        $(".eui-step .i5").addClass("on");
                        $('html, body').animate({scrollTop: 0},300);
                        $("#applyCompletedExeId").text(resultJson.EFLOW_EXEID);

                        $("#applyCompletedProjectCode").text(resultJson[key]);
                        $("#applyCompletedProjectName").text(resultJson[key]);
                    }else{
                        art.dialog({
                            content : resultJson.OPER_MSG,
                            icon : "error",
                            ok : true
                        });
                    }
                }
            });
        }else{
            return null;
        }
    }
}

function tempSaveWebSiteFlowForm(formId){
    var selectedCode=$("input[name='itemCodesSelected']").val();
    if(selectedCode==""){
        art.dialog({
            content :"请选择需要申报的事项和投资项目编号，才能进行保存",// resultJson.OPER_MSG,
            icon : "succeed",
            lock : true,
            ok:function() {

            }
        });
        return;
    }
    //先判断表单是否验证通过
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(formId,-1);
    //获取流程信息对象JSON
    var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
    //将其转换成JSON对象
    var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData(formId);
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
    var postParam = $.param(flowSubmitObj);
    AppUtil.ajaxProgress({
        url : "webSiteController.do?submitApply",
        params : postParam,
        callback : function(resultJson) {
            if(resultJson.OPER_SUCCESS){
                art.dialog({
                    content :"保存成功,草稿数据只保存90天，过期系统自动清理,申报号是:"+resultJson.MM_EXE_ID,// resultJson.OPER_MSG,
                    icon : "succeed",
                    lock : true,
                    ok:function(){
                        window.top.location.href=__ctxPath+"/projectWebsiteController.do?myProjectView";
                    },
                    close: function(){
                        window.top.location.href=__ctxPath+"/projectWebsiteController.do?myProjectView";
                    }
                });
            }else{
                art.dialog({
                    content : resultJson.OPER_MSG,
                    icon : "error",
                    ok : true
                });
            }
        }
    });
}



function searchItemOfSelectServiceItem(){
    var itemName=$("input[name='ITEM_NAME_SELECTSERVICEITEM']").val();
    var stageId= $("input[name='STAGE_ID']").val();
    var topicCode=$("input[name='TOPIC_CODE']").val();
    var  url=encodeURI("projectWebsiteController/findServiceItemByStageIdAndTopicCode.do?stageId="+stageId+"&topicCode="+topicCode+"&itemName="+itemName);
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        cache: false,
        success: function (responseText) {
            var datas = $.parseJSON(responseText);
            $("#listItemtbody").html("");
            var html="";
            var j=0;
            for (var index in datas) {
                j=j+1;
                var isKeyItem = datas[index].IS_KEY_ITEM;
                html+="<tr class=\"" + datas[index].ITEM_CODE + "tr\"> <th>";
                if (isKeyItem != '1') {
                html+="<input class=\"eui-checkbox \" name=\"itemName\"  onclick=\"setItemCodesSelected('0')\" id=\"checkBox" + datas[index].ITEM_CODE + "\"";
                  //  html+=" checked=\"checked\"  data-title=\"1\"   disabled=\"disabled\" ";
                html+=" value=\""+datas[index].ITEM_CODE +"\" type=\"checkbox\" title=\""+datas[index].ITEM_NAME+"\" />";
                }
                html+=" </th> <td>" + datas[index].ITEM_NAME ;
                if (isKeyItem == '1') {
                    html+="<span>必经事项</span>";
                }
                html+="</td> <th><a href=\"serviceItemController/bsznDetail.do?itemCode=" + datas[index].ITEM_CODE + "&projectType=1\">办事指南</a></th></tr>";
            }


            // var itemCodesSelected=$("input[name='itemCodesSelected']").val();
            // var itemNamesSelected=$("input[name='itemNamesSelected']").val();
            // if (itemCodesSelected != "") {
            //     var itemCodes = itemCodesSelected.split(",");
            //     var itemNames = itemNamesSelected.split(",");
            //     for (var i = 0; i < itemCodes.length; i++) {
            //         j = i + 1;
            //         j=i+1;
            //         var itemCode = itemCodes[i];
            //         var itemName = itemNames[i];
            //         html += "    <div class=\"tr\" id=\"selectItemCodeDiv" + itemCode + "\" data-title=\"" + itemName + "\" >\n" +
            //             "  <div class=\"td\"><i>"+j+"</i>" + itemName + "</div>\n" +
            //             "  <div class=\"th min\"> " ;
            //         if(needCheckedItemCodes.indexOf(itemCode)<0){
            //             html+= " <a class=\"del\" onclick=\"removeItemCodeSelected('"+itemCode+","+itemName+"')\"  data-title=\"" + itemCode + "\"></a> " ;
            //         }
            //         html+= " </div>\n" + "     </div>";
            //     }
            // }

            $("#listItemtbody").html(html);
        }
    });

}

/**
 * 验证单个表单
 * @param {} formId
 */
function validateSingleForm(formId){
    //$("#"+formId).validationEngine("validate");
    //$("#"+formId).validationEngine('updatePromptsPosition');
    validateWebsiteTabForm();
    $("#"+formId).validationEngine("hideAll");
}
/**
 * 验证多标签FORM
 * 返回true,代表验证通过
 */
function validateWebsiteTabForm(isTemplateSave){
    var forms = $("form[id]");
    var validate = true;
    if(isTemplateSave){
        forms.each(function(){
            var formId = $(this).attr("id");
            var targetFields = $("#"+formId).find("[name][class*=validate]");
            targetFields.each(function(){
                var classValue = $(this).attr("class");
                var newClass = classValue.replace("required","");
                $(this).attr("class",newClass);
            });
        });
    }
    forms.each(function(){
        var formId = $(this).attr("id");
        var validateResult = $("#"+formId).validationEngine("validate");
        var text = $("#" + formId + "_A").text();
        if (!validateResult) {
            validate = false;
            $("#" + formId + "_A").addClass("on");
            $("#" + formId + "_A").addClass("on");
            $("#" + formId + "_B").html("<img src=\"webpage/website/zzhy/images/icon2.png\">" + text);
            return false;
        } else {
            $("#" + formId + "_A").html("<img src=\"webpage/website/zzhy/images/icon1.png\">" + text);
            $("#" + formId + "_A").removeClass("on");
            $("#" + formId + "_B").removeClass("on");
        }

    });
    /*if(!validate){
        parent.art.dialog({
            content : "提交失败,表单信息输入格式有误!",
            icon : "error",
            ok : true
        });
    }*/
    return validate;
}