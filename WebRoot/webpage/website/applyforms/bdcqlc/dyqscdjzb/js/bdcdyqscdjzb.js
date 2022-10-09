$(function() {

    /*$("select[name='DYQRXX_IDTYPE']").attr("disabled", "disabled");*/
    $("select[name='DYQDJ_DYFS']").attr("disabled", "disabled");

    //点击类型触发事件
    $("input[name='TAKECARD_TYPE']").on("click", function(event) {
        setTakecardType(this.value);
    });
    //加载自动补全数据
    /* loadAutoCompleteDatas();
     $("#AutoCompleteInput").result(function(event, data, formatted) {
        $("input[name='DYQRXX_IDNO']").val(data.DIC_CODE);
        $("input[name='DYQRXX_LEGAL_NAME']").val(data.DIC_DESC);
    }); */
    $("#dymxInfo").find("input,select").attr("disabled", "disabled");
    $("input[name='DYSW']").removeAttr("disabled").removeAttr("readonly");
    $("#qslyInfo").find("input,select").attr("disabled", "disabled");
    //去除权属来源中按钮的不可编辑性
    $("#qslyInfo").find("input[type='button']").attr("disabled", false);
    //去除抵押明细中的抵押顺位、按钮的不可编辑性
  	$("#dymxInfo").find("input[name='DYSW']").attr("disabled",false);
  	$("#dymxInfo").find("input[type='button']").attr("disabled",false);

    $("input[name='QSSJ']").removeAttr("readonly").removeAttr("disabled");
    $("input[name='JSSJ']").removeAttr("readonly").removeAttr("disabled");
    $("#djjfxx").attr("style","display:none;");
    $("#djfzxx").attr("style","display:none;");
    $("#djgdxx").attr("style","display:none;");
});

function submitFlow(formId) {
    var takecardType = $("input[name='TAKECARD_TYPE']:checked").val();
    if (!takecardType) {
        parent.art.dialog({
            content: "请选择业务类型！",
            icon: "warning",
            ok: true
        });
        return;
    }
    //先判断表单是否验证通过
    var validateResult =$("#"+formId).validationEngine("validate");
    if(validateResult){
        //抵押明细
        getDymxInfoJson();
        if ($(".bdcdydacxTr").length < 1) {
            parent.art.dialog({
                content : "最少一个抵押明细信息！",
                icon : "warning",
                ok : true
            });
            return;
        }
        //判断抵押明细中不动产权利人与证件号是否与抵押权登记中的抵押人与证件号一致
        var dyrmc = $("input[name='DYQDJ_NAME']").val();
        var idno = $("input[name='DYQDJ_IDNO']").val();
        var flag = true;
        $("#dymxTable").find(".bdcdydacxTr").find("input[name='bdcdyxx']").each(function() {
            var obj = JSON.parse(this.value);
            var qlrmc = obj.QLRMC.split(',');
            var zjhm = obj.ZJHM.split(',');
            for (var i = 0; i < zjhm.length; i++) {
                if (dyrmc.indexOf(qlrmc[i]) != '-1' && idno.indexOf(zjhm[i]) != '-1') {
                    continue;
                } else {
                    flag = false;
                    break;
                }
            }
        });
        getQslyInfoJson();
        
        //判断权属来源中的义务人与抵押名字中的不动产权利人是否一致,(不一致提示"未办理产权证")
        if (takecardType == '2') {
            var ywr = $("#qslyInfo").find('table').eq(0).find("input[name='YWR']").val();
            var dymx = $("#dymxTable").find("tr").eq(1).find("[name='bdcdyxx']").val();
            var dymxJson = JSON.parse(dymx);
            var qlr = dymxJson.QLRMC;
            if(ywr.trim() != qlr.trim() ){
                parent.art.dialog({
                    content : "未办理产权证！",
                    icon : "warning",
                    ok : true
                });
                return;
            }
        }

        /*判断抵押权登记中的抵押人证件号码与抵押明细中的权利人证件号码是否一致*/
        if (takecardType == '1') {
            var DYQDJ_IDNO = $("input[name='DYQDJ_IDNO']").val();
            var ZJHM = $("input[name='ZJHM']").val();
            if (DYQDJ_IDNO != ZJHM) {
                parent.art.dialog({
                    content : "抵押权登记中的抵押人证件号码与抵押明细中的权利人证件号码不一致，请核实后再申报！",
                    icon : "warning",
                    ok : true
                });
                return;
            }
        }

        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
        if(submitMaterFileJson||submitMaterFileJson==""){
            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
            //将其转换成JSON对象
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);

            if (flowSubmitObj) {
                if(!flowSubmitObj.busRecord){
                    parent.art.dialog({
                        content: "请先暂存后提交。",
                        icon:"warning",
                        ok: true
                    });
                    return;
                }
            }

            if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
                /*判断此业务是否已被办理过*/
                var dymxJson = $("input[type='hidden'][name='DYMX_JSON']").val();
                if (dymxJson) {
                    var parse = JSON.parse(dymxJson);
                    var bdcdyh = parse[0].BDCDYH;
                    var bdcdyhFlag = AppUtil.verifyBdcdyhExistSubmit(bdcdyh);
                    if (!bdcdyhFlag) {
                        art.dialog({
                            content: "请注意：该不动产单元号已经办理过该业务！",
                            icon:"warning",
                            ok: true
                        });
                        return null;
                    }
                }
            }

            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData(formId);
            for(var index in formData){
                flowSubmitObj[eval("index")] = formData[index];
            }
            var c = $("select[name='DYQRXX_NATURE']").val();
            if(c=='1'){
                flowSubmitObj['DYQRXX_NAME'] = $("#DYQRXX_NAME2").val();
            }
            flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
            var postParam = $.param(flowSubmitObj);
            AppUtil.ajaxProgress({
                url: "webSiteController.do?submitApply",
                params: postParam,
                callback: function (resultJson) {
                    if (resultJson.OPER_SUCCESS) {
                        art.dialog({
                            content: resultJson.OPER_MSG,
                            icon: "succeed",
                            lock: true,
                            ok: function () {
                                window.top.location.href = __newUserCenter;
                            },
                            close: function () {
                                window.top.location.href = __newUserCenter;
                            }
                        });
                    } else {
                        art.dialog({
                            content : resultJson.OPER_MSG,
                            icon : "error",
                            ok : true
                        });
                    }
                }
            });
        }
    }
}

function saveFlow(formId) {
    //先判断表单是否验证通过

    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(formId,1);
    //获取流程信息对象JSON
    var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
    //将其转换成JSON对象
    var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    //抵押明细
    getDymxInfoJson();
    getQslyInfoJson();
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData(formId);
    for (var index in formData) {
        flowSubmitObj[eval("index")] = formData[index];
    }
    var c = $("select[name='DYQRXX_NATURE']").val();
    if(c=='1'){
        flowSubmitObj['DYQRXX_NAME'] = $("#DYQRXX_NAME2").val();
    }
    flowSubmitObj.EFLOW_ISTEMPSAVE = "1";
    var postParam = $.param(flowSubmitObj);
    AppUtil.ajaxProgress({
        url : "webSiteController.do?submitApply",
        params : postParam,
        callback : function(resultJson) {
            if(resultJson.OPER_SUCCESS){
                art.dialog({
                    content :"保存成功,草稿数据只保存90天，过期系统自动清理,申报号是:"+resultJson.EFLOW_EXEID,// resultJson.OPER_MSG,
                    icon : "succeed",
                    lock : true,
                    ok:function(){
                        window.top.location.href=__newUserCenter;
                    },
                    close: function(){
                        window.top.location.href=__newUserCenter;
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


function setRuleContent() {
    var selectValue = $('#_select option:selected').text(); //选中select的内容
    //alert("selectValue" + selectValue);

    var inputValue = $("#_input").val(selectValue); //input获得select的内容并显示在输入框中
//alert(inputValue);
}

/**抵押权人性质*/
function dyqrChangeFun(c,flag){
    //var c = $("select[name='DYQRXX_NATURE']").val();
    if(c == '1'){//个人
        if(flag){
            $("select[name='DYQRXX_IDTYPE'] option[value='身份证']").prop("selected", true);
        }
        $("select[name='DYQRXX_IDTYPE']").removeAttr("disabled");
        $("#dyr_gr").attr("style","");
        $("#dyr_qy").attr("style","display:none;");
        $("#DYQRXX_LEGAL_NAME_font").attr("style","display:none;");
        $("#DYQRXX_AGENT_NAME_font").attr("style","display:none;");
        $("#DYQRXX_AGENT_IDTYPE_font").attr("style","display:none;");
        $("#DYQRXX_AGENT_IDNO_font").attr("style","display:none;");
        $("#DYQRXX_AGENT_PHONE_font").attr("style","display:none;");
        $("input[name='DYQRXX_LEGAL_NAME']").removeClass("validate[required]");
        $("input[name='DYQRXX_AGENT_NAME']").removeClass("validate[required]");
        $("select[name='DYQRXX_AGENT_IDTYPE']").removeClass("validate[required]");
        $("input[name='DYQRXX_AGENT_IDNO']").removeClass("validate[required]");
        $("input[name='DYQRXX_AGENT_PHONE']").removeClass("validate[required]");
    }else{
        $("select[name='DYQRXX_IDTYPE'] option[value='营业执照']").prop("selected", true);
        $("select[name='DYQRXX_IDTYPE']").attr("disabled","disabled");
        $("#dyr_qy").attr("style","");
        $("#dyr_gr").attr("style","display:none;");
        $("#DYQRXX_LEGAL_NAME_font").attr("style","");
        $("#DYQRXX_AGENT_NAME_font").attr("style","");
        $("#DYQRXX_AGENT_IDTYPE_font").attr("style","");
        $("#DYQRXX_AGENT_IDNO_font").attr("style","");
        $("#DYQRXX_AGENT_PHONE_font").attr("style","");
        $("input[name='DYQRXX_LEGAL_NAME']").addClass("validate[required]");
        $("input[name='DYQRXX_AGENT_NAME']").addClass("validate[required]");
        $("select[name='DYQRXX_AGENT_IDTYPE']").addClass("validate[required]");
        $("input[name='DYQRXX_AGENT_IDNO']").addClass("validate[required]");
        $("input[name='DYQRXX_AGENT_PHONE']").addClass("validate[required]");
    }
}

//设置抵押权人
function setDYQRXXNameFun(val){
    var datas = $('#DYQRXX_NAME').combobox('getData');
    for(var i=0;i<datas.length;i++){
        if(datas[i].DIC_NAME == val){
            $("input[name='DYQRXX_IDNO']").val(datas[i].DIC_CODE);
            $('#DYQRXX_LEGAL_NAME').val(datas[i].BANK_LEGAL_NAME);
            $("select[name='DYQRXX_IDTYPE'] option[value='营业执照']").prop("selected", true);
            $('#DYQRXX_LEGAL_IDNO').val(datas[i].BANK_LEGAL_CARD);
            $('#DYQRXX_AGENT_NAME').val(datas[i].BANK_CONTECT_NAME);
            $("input[name='NOTIFY_NAME']").val(datas[i].BANK_CONTECT_NAME);
            $('#DYQRXX_AGENT_IDNO').val(datas[i].BANK_CONTECT_CARD);
            $("input[name='NOTIFY_TEL']").val(datas[i].BANK_CONTECT_PHONE);
            $("#DYQRXX_NAME").combobox("setValue", val);
            break ;
        }
    }
}

//社会限制人员校验
function checkLimitPerson() {
    var data = [];
    var flag = true;
    //获取抵押人
    var dyrmc = $("input[name='DYQDJ_NAME']").val();
    var idno = $("input[name='DYQDJ_IDNO']").val();
    if (dyrmc != '' && idno != '') {
        var dyrmcs = dyrmc.split('、');
        var idnos = idno.split('、');
        if (dyrmcs.length == idnos.length) {
            for (var i = 0; i < idnos.length; i++) {
                var dyqData = {};
                dyqData["xm"] = dyrmcs[i];
                dyqData["zjhm"] = idnos[i];
                data.push(dyqData);
            }
        } else {
            parent.art.dialog({
                content : "抵押权人姓名与证件号需一一对应！",
                icon : "warning",
                ok : true
            });
            flag = false;
        }
    }
    if (data.length < 1) {
        flag = false ;
    }
    var cxlist = JSON.stringify(data);
    $.ajaxSettings.async = false;
    $.post("bdcApplyController.do?checkLimitPerson", {
            cxlist : cxlist
        },
        function(responseText, status, xhr) {
            var obj = responseText.rows;
            if (responseText.total > 0) {
                var name = "";
                for (var i = 0; i < obj.length; i++) {
                    name += obj[i].XM + "(" + obj[i].ZJHM + ")、";
                }
                name = name.substring(0, name.length - 1);
                parent.art.dialog({
                    content : "存在涉会/涉黑人员【" + name + "】,不可受理此登记！",
                    icon : "warning",
                    ok : true
                });
                flag = false;
            }
        });
    return flag;
}

//保存权证标识码的操作
function saveQzbsm(){
    var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
    if(BDC_QZBSM){
        var currentUser = $("input[name='uploadUserName']").val();
        var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
        $("input[name='BDC_CZR']").val(currentUser);
        $("input[name='BDC_CZSJ']").val(currentTime);
        //并完成暂存
        BdcQzPrintUtil.BDC_jffzTempSavePageFun();
    }else{
        parent.art.dialog({
            content : "请填写权证标识码。",
            icon : "warning",
            ok : true
        });
    }
}

function isPowerPeople() {
    var powerPeopleInfoSn = $("input[name='POWERPEOPLEINFO_JSON']").val();
    var sqrmc = $("input[name='SQRMC']").val();
    if (powerPeopleInfoSn.indexOf(sqrmc) > 0) {
        return true;
    } else {
        return false;
    }
}


function setFileNumber(serialNum) {
    /* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
    var enterprise = '${execution.SQJG_CODE}';
    var idCard = '${execution.SQRSFZ}';
    // 	alert(idCard + "," + enterprise);
    var fileNum;
    if (enterprise != "") {
        fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
    } else {
        fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
    }
    $("#fileNumber").val(fileNum);
}
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx, name) {
    if (zjlx == "身份证") {
        $("input[name='" + name + "']").addClass(",custom[vidcard]");
    } else {
        $("input[name='" + name + "']").removeClass(",custom[vidcard]");
    }
}

var bdcdydacxTr = 1;
function initAutoTable(flowSubmitObj) {
    var dymxJson = flowSubmitObj.busRecord.DYMX_JSON;
    if (null != dymxJson && '' != dymxJson) {
        var dymxInfos = eval(dymxJson);
        var html = "";
        for (var i = 0; i < dymxInfos.length; i++) {
            html += '<tr class="bdcdydacxTr" id="bdcdydacxTr_' + bdcdydacxTr + '">';
            html += '<input type="hidden" name="bdcdyxx"/>';
            html += '<td style="text-align: center;">' + bdcdydacxTr + '</td>';
            html += '<td style="text-align: center;">' + dymxInfos[i].ZH + '</td>';
            html += '<td style="text-align: center;">' + dymxInfos[i].HH + '</td>';
            html += '<td style="text-align: center;">' + dymxInfos[i].BDCQZH + '</td>';
            html += '<td style="text-align: center;">' + dymxInfos[i].BDCDYH + '</td>';
            html += '<td style="text-align: center;">' + dymxInfos[i].QSZT + '</td>';
            html += '<td style="text-align: center;">';
            html += '<a href="javascript:void(0);" class="eflowbutton" style="margin-left: 5px;padding: 5px 5px;" onclick="loadBdcdaxxcx(this);" id="dymxAdd">查看</a>';
            html += '<a href="javascript:void(0);" class="eflowbutton" style="margin-left: 5px;padding: 5px 5px;" onclick="delDymxTr(this);" >删除</a></td>';
            html += '</tr>';
            $("#dymxTable").append(html);
            $("#bdcdydacxTr_" + bdcdydacxTr + " input[name='bdcdyxx']").val(JSON.stringify(dymxInfos[i]));
            bdcdydacxTr++;
            html = "";
        }
        loadBdcdaxxcxToId();
    }

    var qslyJson = flowSubmitObj.busRecord.QSLY_JSON;
    if (null != qslyJson && '' != qslyJson) {
        var qslyInfos = eval(qslyJson);
        for (var i = 0; i < qslyInfos.length; i++) {
            if (i == 0) {
                FlowUtil.initFormFieldValue(qslyInfos[i], "qslyInfo_1");
                $("#qslyInfo_1 input[name='bdcqsly']").val(JSON.stringify(qslyInfos[i]));
            } else {
                addQslyInfo();
                FlowUtil.initFormFieldValue(qslyInfos[i], "qslyInfo_" + (i + 1));
                $("#qslyInfo_" + (i + 1) + " input[name='bdcqsly']").val(JSON.stringify(qslyInfos[i]));
            }
        }
    }

    // var djfzxx_json = flowSubmitObj.busRecord.DJFZXX_JSON;
    // if(null != djfzxx_json && '' != djfzxx_json){
    //     var djfzxx_jsonItems = JSON.parse(djfzxx_json);
    //     initDjfzxx_dyqscdj(djfzxx_jsonItems);
    // }
    // var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
    // if(null != djjfmx_json && '' != djjfmx_json){
    //     var djjfmx_jsonItems = JSON.parse(djjfmx_json);
    //     initDjjfxx_dyqscdj(djjfmx_jsonItems);
    // }
}

function setTakecardType(value) {
    if (value === "1") {
        $("#qsly").hide();
        $("input[name='BDCDYH']").attr("disabled", true);
        $("input[name='BDCDYH']").removeClass(" validate[required]");
        /*领证人姓名和证件号码隐藏*/
        $("#lzrInfo").hide();
        $("#dyqrdh").hide();
        $(".dlrdh").show();
        $("#dywjzFont").hide();
        $("input[name='DYQDJ_DYWJZ']").attr("class", "tab_text");
        $(".dyxxHide").hide();
        //判断是否选择在线签章
        if(isSign=='1'){
        	$("input[name='ISQCWB']").val(2);//预抵全程网办个性化
        }
    } else {
        $("#qsly").show();
        $("input[name='BDCDYH']").attr("disabled", false);
        $("input[name='BDCDYH']").addClass(" validate[required]");
        $("#lzrInfo").show();
        $("#dyqrdh").show();
        $(".dlrdh").hide();
        $("#dywjzFont").show();
        $("input[name='DYQDJ_DYWJZ']").attr("class", "tab_text validate[required]");
        $(".dyxxHide").show();
        //判断是否选择在线签章
        if(isSign=='1'){
        	$("input[name='ISQCWB']").val(1);//智能审批
        }
    }
}

//设置是否最高额抵押
function setSfzgedy(value) {
    if (value === "1") {//是
		$("input[name='DYQDJ_ZGZQE']").attr("disabled", false).addClass("validate[required]");
		$("#zgzqeFont").show();
		$("#bdbzzqseFont").hide();
		$("input[name='DYQDJ_BDBZZQSE']").attr("disabled", true).removeClass("validate[required]");
		$("select[name='DYQDJ_DYFS']").val("2");
	} else {//否
		$("input[name='DYQDJ_ZGZQE']").attr("disabled", true).removeClass("validate[required]");
		$("#bdbzzqseFont").show();
		$("#zgzqeFont").hide();
		$("input[name='DYQDJ_BDBZZQSE']").attr("disabled", false).addClass("validate[required]");
		$("select[name='DYQDJ_DYFS']").val("1");
	}
}
function setDyqrxxName() {
    var name = $("select[name='DYQRXX_NAME_SELECT']").find("option:selected").text();
    var value = $("select[name='DYQRXX_NAME_SELECT']").val();
    var desc = $("select[name='DYQRXX_NAME_SELECT']").find("option:selected").attr("dicdesc");
    if ('' == value) {
        name = "";
    }
    $("input[name='DYQRXX_NAME']").val(name);
    $("input[name='DYQRXX_IDNO']").val(value);
    $("input[name='DYQRXX_LEGAL_NAME']").val(desc);
}
/**=================权属来源信息开始================================*/

function showSelectBdcygdacx() {
    $.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&noLike=1&allowCount=1", {
        title : "不动产预告档案查询",
        width : "100%",
        lock : true,
        resize : false,
        height : "100%",
        close : function() {
            var bdcygdacxInfo = art.dialog.data("bdcygdacxInfo");
            if (bdcygdacxInfo && bdcygdacxInfo.length >= 1) {
                var bdcdyh = $("#qslyInfo").find('table').eq(0).find("input[name='BDCDYH']").val();
            /*    var isSlFlag = isSlfh(bdcygdacxInfo[0].BDCDYH);
                if (!isSlFlag) {
                    parent.art.dialog({
                        content : "请先办理分户业务",
                        icon : "warning",
                        ok : function () {
                            window.top.location.reload();
                        },
                        close: function () {
                            window.top.location.reload();
                        }
                    });
                    return false;
                }*/
                for (var i = 0; i < bdcygdacxInfo.length; i++) {

                    if (i == 0 && qslyInfoSn == 1 && !bdcdyh) {
                        FlowUtil.initFormFieldValue(bdcygdacxInfo[i], "qslyInfo_1");
                        $("#qslyInfo_1 input[name='bdcqsly']").val(JSON.stringify(bdcygdacxInfo[i]));
                    } else {
                        addQslyInfo();
                        FlowUtil.initFormFieldValue(bdcygdacxInfo[i], "qslyInfo_" + (qslyInfoSn));
                        $("#qslyInfo_" + (qslyInfoSn) + " input[name='bdcqsly']").val(JSON.stringify(bdcygdacxInfo[i]));
                    }

                    var ygdjzl = bdcygdacxInfo[i].YGDJZL.trim();
                    if(ygdjzl.indexOf("抵押权预告登记") > -1 || ygdjzl.indexOf("预售商品房抵押权预告登记") > -1){
                        //回填抵押权人信息
                        if(bdcygdacxInfo[i].QLRZJZL.trim().indexOf("身份证") > -1){
                            $("select[name='DYQRXX_NATURE'] option[value='1']").prop("selected", true);
                            //个人
                            dyqrChangeFun("1",true);
                            $("#DYQRXX_NAME2").val(bdcygdacxInfo[i].QLR);
                            $("#DYQRXX_IDNO").val(bdcygdacxInfo[i].QLRZJH);
                        }else{
                            $("select[name='DYQRXX_NATURE'] option[value='2']").prop("selected", true);
                            //其他情况
                            dyqrChangeFun("2",true);
                            // $("#DYQRXX_NAME").combobox("setValue", bdcygdacxInfo[i].QLR.trim());
                            if(bdcygdacxInfo[i].QLR){
                                setDYQRXXNameFun(bdcygdacxInfo[i].QLR.trim());
                            }
                        }
                        //回填抵押抵押权登记信息
                        $("input[name='DYQDJ_NAME']").val(bdcygdacxInfo[i].YWR);
                        if(bdcygdacxInfo[i].YWRZJZL.trim().indexOf("营业执照") > -1){
                            $("select[name='DYQDJ_IDTYPE'] option[value='营业执照']").prop("selected", true);
                        }else{
                            $("select[name='DYQDJ_IDTYPE'] option[value='身份证']").prop("selected", true);
                        }
                        $("input[name='DYQDJ_IDNO']").val(bdcygdacxInfo[i].YWRZJH);
                        $("input[name='DYQDJ_BDBZZQSE']").val(bdcygdacxInfo[i].QDJG);
                        $("input[name='DYQDJ_ZWQSSJ']").val(bdcygdacxInfo[i].QSSJ);
                        $("input[name='DYQDJ_ZWJSSJ']").val(bdcygdacxInfo[i].JSSJ);
                        $("input[name='LOCATED']").val(bdcygdacxInfo[i].BDCZL);
                        $("input[name='DYQDJ_ZGZQQDSS']").val("/");
                        $("textarea[name='DYQDJ_FJ']").val("该抵押由房屋预告登记证明：转为抵押首次登记所得。");
                        $("input[name='DYQDJ_DJYY']").val("首次登记");
                        $("input[name='DYQDJ_DYFW']").val("详见抵押合同");


                    }

                }
                //$("#qslyInfo_1 input[name='deleteQslyInfoInput']").hide();

                $("#qslyInfo").find("input,select").attr("disabled", "disabled");
                $("#qslyInfo").find("input,select").attr("readonly", "readonly");
                //去除权属来源中按钮的不可编辑性
                $("#qslyInfo").find("input[type='button']").attr("disabled", false);
                $("#qslyInfo").find("input[type='button']").attr("readonly", false);
                /**/
                $("input[name='QSSJ']").removeAttr("readonly").removeAttr("disabled");
                $("input[name='JSSJ']").removeAttr("readonly").removeAttr("disabled");
                art.dialog.removeData("bdcygdacxInfo");

            }
        }
    }, false);
}
;
var qslyInfoSn = 1;
function addQslyInfo() {
    qslyInfoSn = qslyInfoSn + 1;
    var table = document.getElementById("qslyInfo");
    var trContent = table.getElementsByTagName("tr")[0];
    var trHtml = trContent.innerHTML;
    var findex = trHtml.indexOf("deleteQslyInfo('");
    if (qslyInfoSn > 10) {
        var replacestr = trHtml.substring(findex, findex + 20);
    } else {
        var replacestr = trHtml.substring(findex, findex + 19);
    }
    trHtml = trHtml.replace(replacestr, "deleteQslyInfo('" + qslyInfoSn + "')");
    trHtml = "<tr id=\"qslyInfo_" + qslyInfoSn + "\">" + trHtml + "</tr>";
    $("#qslyInfo").append(trHtml);
}


function deleteQslyInfo(idSn) {
    var table = document.getElementById("qslyInfo");
    if (table.rows.length == 1) {
        parent.art.dialog({
            content : "最少一个权属来源信息！",
            icon : "warning",
            ok : true
        });
        return false;
    }
    $("#qslyInfo_" + idSn).remove();
}

function getQslyInfoJson() {
    var datas = [];
    var table = document.getElementById("qslyInfo");
    for (var i = 0; i < table.rows.length; i++) {
        var bdcqsly = $("#qslyInfo").find('table').eq(i).find("input[name='bdcqsly']").val();
        var trData = {};
        if ("" != bdcqsly && null != bdcqsly && undefined != bdcqsly) {
            trData = JSON.parse(bdcqsly);
        }
        $("#qslyInfo").find('table').eq(i).find("*[name]").each(function() {
            var inputName = $(this).attr("name");
            if (inputName != "bdcqsly") {
                var inputValue = $(this).val();
                //获取元素的类型
                var fieldType = $(this).attr("type");
                if (fieldType == "radio") {
                    var isChecked = $(this).is(':checked');
                    if (isChecked) {
                        trData[inputName] = inputValue;
                    }
                } else if (fieldType == "checkbox") {
                    var inputValues = FlowUtil.getCheckBoxValues(inputName);
                    trData[inputName] = inputValues;
                } else if (fieldType != "button") {
                    trData[inputName] = inputValue;
                }
            }
        });
        datas.push(trData);

    }
    $("input[type='hidden'][name='QSLY_JSON']").val(JSON.stringify(datas));
}
/**=================权属来源信息开始================================*/


function onlyNumber(obj) {
    //得到第一个字符是否为负号
    var t = obj.value.charAt(0);
    //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d\.]/g, '');
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g, '');
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g, '.');
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace('.', '$#$').replace(/\./g, '').replace('$#$', '.');
    //只能输入小数点后两位
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
    //如果第一位是负号，则允许添加
    if (t == '-') {
        obj.value = '-' + obj.value;
    }
}

/**=================抵押明细信息开始================================*/

function showSelectBdcdydacx() {
    var negativeListCodes = $("input[name='NEGATIVELIST_CODES']").val();

    $.dialog.open("bdcDyqscdjController.do?selector&allowCount=0&negativeListCodes=" + negativeListCodes + "&isYh=0", {
        title : "不动产抵押档案查询",
        width : "100%",
        lock : true,
        resize : false,
        height : "100%",
        close : function() {
            var bdcdydacxInfo = art.dialog.data("bdcdydacxInfo");
            if (bdcdydacxInfo) {
                var html = "";
                for (var i = 0; i < bdcdydacxInfo.length; i++) {
                    html += '<tr class="bdcdydacxTr">';
                    html += '<td>' + (i + 1) + '</td>';
                    html += '<td>' + bdcdydacxInfo[i].ZH + '</td>';
                    html += '<td>' + bdcdydacxInfo[i].HH + '</td>';
                    html += '<td>' + bdcdydacxInfo[i].BDCQZH + '</td>';
                    html += '<td>' + bdcdydacxInfo[i].BDCDJZMH + '</td>';
                    html += '<td>' + bdcdydacxInfo[i].QSZT + '</td>';
                    html += '<td>';
                    html += '<a href="javascript:void(0);" onclick="delCycjDiv(this);" class="syj-closebtn"></a></td>';
                    html += '</tr>';
                }
                $(".bdcdydacxTr").remove();
                $("#dymxTable").append(html);
                art.dialog.removeData("bdcdydacxInfo");

            }
        }
    }, false);
}
;

function showSelectBdcdaxxcx() {
    $.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0&isYh=0&isRequired=1", {
        title : "不动产档案信息查询",
        width : "100%",
        lock : true,
        resize : false,
        height : "100%",
        close : function() {
            var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
            if (bdcdaxxcxInfo) {
                var html = "";
                for (var i = 0; i < bdcdaxxcxInfo.length; i++) {
                    html += '<tr class="bdcdydacxTr" id="bdcdydacxTr_' + bdcdydacxTr + '">';
                    html += '<input type="hidden" name="bdcdyxx"/>';
                    html += '<td style="text-align: center;">' + bdcdydacxTr + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].ZH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].HH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].BDCQZH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].BDCDYH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].QSZT + '</td>';
                    html += '<td style="text-align: center;">';
                    html += '<a href="javascript:void(0);" style="padding: 5px 5px;" class="eflowbutton" onclick="loadBdcdaxxcx(this);" id="dymxAdd">查看</a>';
                    html += '<a href="javascript:void(0);" style="margin-left: 5px;padding: 5px 5px;" class="eflowbutton" onclick="delDymxTr(this);">删除</a></td>';
                    html += '</tr>';
                    $("#dymxTable").append(html);
                    //抵押不动产类型回填
                    $('select[name="DYBDCLX"]').find("option").each(function() {
                        if ($(this).text() == bdcdaxxcxInfo[i].BDCLX) {
                            bdcdaxxcxInfo[i].DYBDCLX = $(this).val();
                        }
                    });
                    $("#bdcdydacxTr_" + bdcdydacxTr + " input[name='bdcdyxx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
                    bdcdydacxTr++;
                    html = "";
                }
                setTotlaJzmj();
                setTotlaZdmj();
                setTotlaDytdmj();
                setTotlaFttdmj();
                setSqlandZl(); ////回填申请人以及坐落
                art.dialog.removeData("bdcdaxxcxInfo");
                loadBdcdaxxcxToId();
            }
        }
    }, false);
}
;


var dymxInfoSn = 1;
function addDymxInfo() {
    dymxInfoSn = dymxInfoSn + 1;
    var table = document.getElementById("dymxInfo");
    var trContent = table.getElementsByTagName("tr")[0];
    var trHtml = trContent.innerHTML;

    var findex = trHtml.indexOf("deleteDymxInfo('");
    var replacestr = "";
    if (dymxInfoSn > 10) {
        replacestr = trHtml.substring(findex, findex + 20);
    } else {
        replacestr = trHtml.substring(findex, findex + 19);
    }
    trHtml = trHtml.replace(replacestr, "deleteDymxInfo('" + dymxInfoSn + "')");
    trHtml = "<tr id=\"dymxInfo_" + dymxInfoSn + "\">" + trHtml + "</tr>";
    $("#dymxInfo").append(trHtml);
}

function deleteDymxInfo(idSn) {
    var table = document.getElementById("dymxInfo");
    if (table.rows.length == 1) {
        parent.art.dialog({
            content : "最少一个抵押明细信息！",
            icon : "warning",
            ok : true
        });
        return false;
    }
    $("#dymxInfo_" + idSn).remove();
    setTotlaJzmj();
    setTotlaZdmj();
    setTotlaDytdmj();
    setTotlaFttdmj();
}


/**
 * 获取抵押明细JSON
 */
function getDymxInfoJson() {
    var datas = [];
    $("#dymxTable tr").each(function(i) {
        var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
        if ('' != bdcdyxx && null != bdcdyxx) {
            datas.push(JSON.parse(bdcdyxx));
        }
    });
    var dymxJson = JSON.stringify(datas);
    $("input[type='hidden'][name='DYMX_JSON']").val(dymxJson);
}
function setTotlaJzmj() {
    var totla = 0;
    $("#dymxTable tr").each(function(i) {
        var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
        if ('' != bdcdyxx && null != bdcdyxx) {
            var bdcdyxxInfos = JSON.parse(bdcdyxx);
            totla += Number(bdcdyxxInfos.JZMJ);
        }
    })
    $("input[name='DYQDJ_DYJZZMJ']").val(totla.toFixed(2)); //抵押建筑总面积
    $("input[name='DYQDJ_DYZS']").val($(".bdcdydacxTr").length); //抵押宗数
}
function setTotlaDytdmj() {
    var totla = 0;
    $("#dymxTable tr").each(function(i) {
        var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
        if ('' != bdcdyxx && null != bdcdyxx) {
            var bdcdyxxInfos = JSON.parse(bdcdyxx);
            totla += Number(bdcdyxxInfos.DYTDMJ);
        }
    })
    $("input[name='DYQDJ_DYTDMJ']").val(totla.toFixed(2)); //抵押独用土地面积
}
function setTotlaZdmj() {
    var totla = 0;
    $("#dymxTable tr").each(function(i) {
        var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
        if ('' != bdcdyxx && null != bdcdyxx) {
            var bdcdyxxInfos = JSON.parse(bdcdyxx);
            totla += Number(bdcdyxxInfos.ZDMJ);
        }
    })
    $("input[name='DYQDJ_DYZSZMJ']").val(totla.toFixed(2)); //抵押宗地总面积s
}
function setTotlaFttdmj() {
    var totla = 0;
    $("#dymxTable tr").each(function(i) {
        var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
        if ('' != bdcdyxx && null != bdcdyxx) {
            var bdcdyxxInfos = JSON.parse(bdcdyxx);
            totla += Number(bdcdyxxInfos.FTTDMJ);
        }
    })
    $("input[name='DYQDJ_DYFTTDZMJ']").val(totla.toFixed(2)); //抵押分摊土地总面积
}
/**=================抵押明细信息结束================================*/
function setSqlandZl() {
    $("#dymxTable tr").each(function(i) {
        var bdcdyxx = $(this).find("[name='bdcdyxx']").val();
        if ('' != bdcdyxx && null != bdcdyxx) {
            var bdcdyxxInfos = JSON.parse(bdcdyxx);
            if ((i == 1) && ($(".bdcdydacxTr").length == 1)) {
                $("input[name='APPLICANT_UNIT']").val(bdcdyxxInfos.QLRMC);
                if (bdcdyxxInfos.BDCLX == "土地") {
                    $("input[name='LOCATED']").val(bdcdyxxInfos.TDZL); //土地坐落
                } else {
                    $("input[name='LOCATED']").val(bdcdyxxInfos.FDZL); //房屋坐落
                }
            } else {
                $("input[name='APPLICANT_UNIT']").val(bdcdyxxInfos.QLRMC + "等");
                if (bdcdyxxInfos.BDCLX == "土地") {
                    $("input[name='LOCATED']").val(bdcdyxxInfos.TDZL + "等"); //土地坐落
                } else {
                    $("input[name='LOCATED']").val(bdcdyxxInfos.FDZL + "等"); //房屋坐落
                }
            }
            return false;
        }
    });
}

function loadAutoCompleteDatas() {
    var layload = layer.load('正在提交请求中…');
    $.post("bdcDyqscdjController.do?loadDicSearch", {
        typeCode : "CYYHHMC"
    }, function(responseText, status, xhr) {
        layer.close(layload);
        var resultJson = $.parseJSON(responseText);
        $("#AutoCompleteInput").autocomplete(
            resultJson,
            {
                matchContains : true,
                formatItem : function(row, i, max) {
                    //下拉框显示
                    return "<div>" + row.DIC_NAME + "</div>";
                },
                formatMatch : function(row) {
                    //查询条件
                    return row.DIC_NAME + row.PINYINSZM + row.PINYIN;
                },
                formatResult : function(row, i, max) {
                    //显示在文本框中
                    return row.DIC_NAME;
                }
            });
    });
}


function newDicInfoWin(typeCode, combId) {
    $.dialog.open("bdcApplyController.do?wtItemInfo&typeCode=" + typeCode, {
        title : "新增",
        width : "600px",
        lock : true,
        resize : false,
        height : "180px",
        close : function() {
            $("#" + combId).combobox("reload");
        }
    }, false);
}

function removeDicInfo(combId) {
    var datas = $("#" + combId).combobox("getData");
    var val = $("#" + combId).combobox("getValue");
    var id = "";
    for (var i = 0; i < datas.length; i++) {
        if (datas[i].DIC_NAME == val) {
            id = datas[i].DIC_ID;
            break;
        }
    }
    art.dialog.confirm("您确定要删除该记录吗?", function() {
        var layload = layer.load('正在提交请求中…');
        $.post("dictionaryController.do?multiDel", {
            selectColNames : id
        }, function(responseText, status, xhr) {
            layer.close(layload);
            var resultJson = $.parseJSON(responseText);
            if (resultJson.success == true) {
                art.dialog({
                    content : resultJson.msg,
                    icon : "succeed",
                    time : 3,
                    ok : true
                });
                $("#" + combId).combobox("reload");
                $("#" + combId).combobox("setValue", "");
            } else {
                $("#" + combId).combobox("reload");
                art.dialog({
                    content : resultJson.msg,
                    icon : "error",
                    ok : true
                });
            }
        });
    });
}



/**
 * 删除
 */
function delDymxTr(o) {
    $(o).closest("tr").remove();
    setTotlaJzmj();
    setTotlaZdmj();
    setTotlaDytdmj();
    setTotlaFttdmj();
    setSqlandZl(); ////回填申请人以及坐落
    loadBdcdaxxcxToId();
    if ($(".bdcdydacxTr").length < 1) {
        //抵押不动产类型默认为'土地和房屋'
        $("select[name='DYBDCLX']").val("2");
    }
}

function loadBdcdaxxcx(o) {
    $("#dymxInfo_1 input[type='text']").val('');
    $("#dymxInfo_1 select").val('');
    var bdcdyxx = $(o).closest("tr").find("[name='bdcdyxx']").val();
    initFormFieldValue(JSON.parse(bdcdyxx), "dymxInfo_1");
    //抵押不动产类型为土地，则坐落取土地坐落，为其他则使用房地坐落
    var bdcdyxxInfos = JSON.parse(bdcdyxx);
    if (bdcdyxxInfos.BDCLX == "土地") {
        $('input[name="FDZL"]').val(bdcdyxxInfos.TDZL);
    } else {
        $('input[name="FDZL"]').val(bdcdyxxInfos.FDZL);
    }
    //抵押不动产类型值回填
    $('select[name="DYBDCLX"]').find("option").each(function() {
        if ($(this).text() == bdcdyxxInfos.BDCLX) {
            $(this).prop("selected", true);
        }
    });
    var trId = $(o).closest("tr").attr("id");
    $("#dymxInfo_1 input[name='trid']").val(trId);
    $(".bdcdydacxTr").removeClass("bdcdydacxTrRed");
    $(o).closest("tr").addClass("bdcdydacxTrRed");
}
function loadBdcdaxxcxToId() {
    $("#dymxInfo_1 input[type='text']").val('');
    $("#dymxInfo_1 select").val('');
    if ($(".bdcdydacxTr").length >= 1) {
        var bdcdyxx = $("#dymxTable").find("tr").eq(1).find("[name='bdcdyxx']").val();
        initFormFieldValue(JSON.parse(bdcdyxx), "dymxInfo_1");
        //抵押不动产类型为土地，则坐落取土地坐落，为其他则使用房地坐落
        var bdcdyxxInfos = JSON.parse(bdcdyxx);
        if (bdcdyxxInfos.BDCLX == "土地") {
            $('input[name="FDZL"]').val(bdcdyxxInfos.TDZL);
        } else {
            $('input[name="FDZL"]').val(bdcdyxxInfos.FDZL);
        }
        //抵押不动产类型值回填
        $('select[name="DYBDCLX"]').find("option").each(function() {
            if ($(this).text() == bdcdyxxInfos.BDCLX) {
                $(this).prop("selected", true);
            }
        });
        var trId = $("#dymxTable").find("tr").eq(1).attr("id");
        $("#dymxInfo_1 input[name='trid']").val(trId);
        $(".bdcdydacxTr").removeClass("bdcdydacxTrRed");
        $("#dymxTable").find("tr").eq(1).addClass("bdcdydacxTrRed");
    }
}
function updateDymxInfo() {
    var trid = $("#dymxInfo_1 input[name='trid']").val();
    var bdcdyxx = $("#" + trid + " input[name='bdcdyxx']").val();
    if ("" != bdcdyxx && null != bdcdyxx && undefined != bdcdyxx) {
        var bdcdyxxInfos = JSON.parse(bdcdyxx);
        $("#dymxInfo").find('table').eq(0).find("*[name]").each(function() {
            var inputName = $(this).attr("name");
            var inputValue = $(this).val();
            //获取元素的类型
            var fieldType = $(this).attr("type");
            if (fieldType == "radio") {
                var isChecked = $(this).is(':checked');
                if (isChecked) {
                    bdcdyxxInfos[inputName] = inputValue;
                }
            } else if (fieldType == "checkbox") {
                var inputValues = FlowUtil.getCheckBoxValues(inputName);
                bdcdyxxInfos[inputName] = inputValues;
            } else if (fieldType != "button") {
                bdcdyxxInfos[inputName] = inputValue;
            }
        });
        $("#" + trid + " input[name='bdcdyxx']").val(JSON.stringify(bdcdyxxInfos));
        art.dialog({
            content : "保存成功",
            icon : "succeed",
            time : 3,
            ok : true
        });
        setTotlaJzmj();
        setTotlaZdmj();
        setTotlaDytdmj();
        setTotlaFttdmj();
    } else {
        art.dialog({
            content : "请先选择抵押明细信息！",
            icon : "warning",
            ok : true
        });
    }
}
/**
 * 初始化表单字段值
 * @param {} fieldValueObj
 * @param {} elementId
 */
function initFormFieldValue(fieldValueObj, elementId) {
    for (var fieldName in fieldValueObj) {
        //获取目标控件对象
        var targetFields = $("#" + elementId).find("[name='" + fieldName + "']");
        targetFields.each(function() {
            var targetField = $(this);
            var type = targetField.attr("type");
            var tagName = targetField.get(0).tagName;
            var fieldValue = fieldValueObj[fieldName];
            if (type == "radio") {
                var radioValue = targetField.val();
                if (radioValue == fieldValue) {
                    $(this).attr("checked", "checked");
                }
            } else if (type == "checkbox") {
                var checkBoxValue = targetField.val();
                var isChecked = AppUtil.isContain(fieldValue.split(","), checkBoxValue);
                if (isChecked) {
                    $(this).attr("checked", "checked");
                }
            } else {
                targetField.val(fieldValueObj[fieldName]);
            }
        });
    }
}


//不动产产权证书打印与预览
function bdcCQZSprint(typeId){
    var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
    if(BDC_QZBSM){
        // typeId: 1为证书预览  2为证书打印
        var title = "证书打印";
        var templateAlias = 'BDCQZ';
        if(typeId==1) {
            title = "证书预览";
        }
        var dataStr = "";
        dataStr +="&QLRZJH="+$("input[name='DYQRXX_IDNO']").val();
        dataStr +="&BDCQZH="+$("input[name='BDC_BDCDJZMH']").val();
        dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
        dataStr +="&typeId="+typeId;   //1为证书预览  2为证书打印
        dataStr +="&templateAlias=" + templateAlias;
        var url=encodeURI("certificateTemplateController.do?invokeCustomMethod"+dataStr);
        $.dialog.open(url, {
            title : title,
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false
        }, false);
    }else{
        art.dialog({
            content :"请先填写权证标识码。",
            icon : "warning",
            ok : true
        });
    }
}

/**
 * 不动产登簿操作
 */
function BDCQLC_bdcdbcz(){
    var currentUser = $("input[name='uploadUserName']").val();
    var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
    var flowSubmitObj = FlowUtil.getFlowObj();
    var exeId = flowSubmitObj.EFLOW_EXEID;
    if(exeId != null && typeof exeId != undefined){
        art.dialog.confirm("您确定要进行登簿吗?", function() {
            var layload = layer.load('正在提交登簿数据…');
            $.post("bdcQlcApplyController/bdcdbcz.do", {
                "eveId" : exeId
            }, function(responseText, status, xhr) {
                layer.close(layload);
                var resultJson = $.parseJSON(responseText);
                if(resultJson.success){
                    var data = $.parseJSON(resultJson.data);
                    if(data.retcode == "00" && data.dbzt == "1"){
                        if(data.qzinfo && data.qzinfo.length > 0){
                            var qzinfo = data.qzinfo[0];
                            $("input[name='BDC_DBZT']").val(qzinfo.gdzt);
                            if(qzinfo.gdzt == "1"){
                                $("input[name='BDC_BDCDJZMH']").val(qzinfo.qzzh);
                                $("input[name='BDC_DBR']").val(currentUser);
                                $("input[name='BDC_DBSJ']").val(currentTime);
                                $("input[name='BDC_DBJG']").val("登簿成功");
                                art.dialog({
                                    content :"登簿成功",
                                    icon : "succeed",
                                    ok : true
                                });
                            }else{
                                $("input[name='BDC_DBJG']").val(qzinfo.ret_msg);
                                art.dialog({
                                    content :"归档失败，"+qzinfo.ret_msg,
                                    icon : "warning",
                                    ok : true
                                });
                            }
                        }
                    }else if(data.retcode == "99" ){
                        $("input[name='BDC_DBZT']").val(0);
                        $("input[name='BDC_DBJG']").val(data.msg);
                        art.dialog({
                            content : data.msg+"登簿失败。",
                            icon : "warning",
                            ok : true
                        });
                    }else{
                        $("input[name='BDC_DBZT']").val(data.dbzt);
                        $("input[name='BDC_DBJG']").val(data.ret_msg);
                        art.dialog({
                            content : data.ret_msg+"登簿失败。",
                            icon : "warning",
                            ok : true
                        });
                    }
                }else{
                    art.dialog({
                        content : resultJson.msg,
                        icon : "warning",
                        ok : true
                    });
                }
            });
        });
    }
}


function isButtonAvailable(){
    var result = {};
    result["ITEM_CODE"] = $("input[name='ITEM_CODE']").val();
    result["EXE_ID"] = $("#EXEID").text();
    result["CUR_STEPNAMES"] = $("#CUR_STEPNAMES").val();
    $.post("readTemplateController.do?selectedPrint",result, function(resultJson) {
        var itemList = resultJson.rows;
        var newhtml = "";
        for(var i=0; i<itemList.length; i++){
            if(itemList[i].READ_NAME=='不动产登记审批表一审'){
                $("#dfspb").attr("onclick","goPrintTemplate('BDCDJSPBYS',3)");
                $("#sfspb").attr("onclick","goPrintTemplate('BDCDJSPBYS',3)");
            }
        }
    });
}

function goPrintTemplate(TemplateName,typeId) {
    var dataStr = "";
    dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
    dataStr +="&typeId="+typeId;   //4代表为权证模板 3为阅办
    //dataStr +="&TemplatePath="+TemplatePath;
    dataStr +="&TemplateName="+TemplateName;	//TemplateName为模板名称或别名
    //dataStr +="&isSignButton="+isSignButton;
    var url=encodeURI("printAttachController.do?printTemplate"+dataStr);
    //打印模版
    $.dialog.open(url, {
        title : "打印模版",
        width: "100%",
        height: "100%",
        left: "0%",
        top: "0%",
        fixed: true,
        lock : true,
        resize : false
    }, false);
}

function errorAction(){
    art.dialog({
        content : "当前环节不能执行该操作",
        icon : "warning",
        ok : true
    });
}

/*初始化用户表单*/
function initUserForm(userType) {
    if(userType==1){
        $("#sqjg").attr("style","display:none;");
        var idcard = $("input[name='SQRSFZ']").val();
        personCreditHandle(idcard);
    }else{
        $("#sqr").attr("style","display:none;");
        var idcard = $("input[name='SQJG_CODE']").val();
        personCreditHandle(idcard);
    }
    var sqrzjlx=$("#sqrzjlx").val();
    var jbrzjlx=$("#jbrzjlx").val();
    setValidate(sqrzjlx);
    setValidatejbr(jbrzjlx);

    initSlxx();

}

function initSlxx() {
    var SQRMC = $("input[name='SQRMC']").val();
    $("input[name='APPLICANT_UNIT']").val(SQRMC);
}

function isSlfh(bdcdyh) {
    var flag = false;
    $.ajaxSettings.async = false;
    $.post("bdcDyqscdjController.do?checkBdcfh", {bdcdyh: bdcdyh}, function (data) {
        if (data) {
            var json = JSON.parse(data);
            if (json.success) {
                flag = true;
            }
        }
    });
    $.ajaxSettings.async = true;
    return flag;
}

/**
 * 标题附件上传对话框
 */
function openXxcjFileUploadDialog(){

    //定义上传的人员的ID
    var uploadUserId = $("input[name='uploadUserId']").val();
    //定义上传的人员的NAME
    var uploadUserName = $("input[name='uploadUserName']").val();
    var EFLOW_BUSTABLENAME = $("input[name='EFLOW_BUSTABLENAME']").val();
    var materType = '*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.rar;';
    //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
    art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType='
        +materType+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName)+'&busTableName='+EFLOW_BUSTABLENAME, {
        title: "上传(附件)",
        width: "650px",
        height: "300px",
        lock: true,
        resize: false,
        close: function(){
            var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
            if(uploadedFileInfo){
                if(uploadedFileInfo.fileId){
                    var fileType = 'gif,jpg,jpeg,bmp,png';
                    var attachType = 'attach';
                    if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
                        attachType = "image";
                    }
                    var fileurl=$("input[name='RESULT_FILE_URL']").val();
                    var fileid=$("input[name='RESULT_FILE_ID']").val();
                    if(fileurl!=""&&fileurl!=null){
                        $("input[name='RESULT_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
                        $("input[name='RESULT_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
                    }else{
                        $("input[name='RESULT_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
                        $("input[name='RESULT_FILE_ID']").val(uploadedFileInfo.fileId);
                    }
                    var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server
                        + "download/fileDownload?attachId="+uploadedFileInfo.fileId
                        +"&attachType="+attachType+"\" ";
                    spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                    spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                    spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"');\" ><font color=\"red\">删除</font></a></p>"
                    $("#fileListDiv").append(spanHtml);
                }
            }
            art.dialog.removeData("uploadedFileInfo");
        }
    });
}

function delUploadFile(fileId,attacheKey){
    //AppUtil.delUploadMater(fileId,attacheKey);
    art.dialog.confirm("您确定要删除该文件吗?", function() {
        //移除目标span
        $("#"+fileId).remove();
        var fileurl=$("input[name='RESULT_FILE_URL']").val();
        var fileid=$("input[name='RESULT_FILE_ID']").val();
        var arrayIds=fileid.split(";");
        var arrayurls=fileurl.split(";");
        for(var i=0;i<arrayIds.length;i++){
            if(arrayIds[i]==fileId){
                arrayIds.splice(i,1);
                arrayurls.splice(i,1);
                break;
            }
        }
        $("input[name='RESULT_FILE_URL']").val(arrayurls.join(";"));
        $("input[name='RESULT_FILE_ID']").val(arrayIds.join(";"));
    });
}

//是否在线签章
var isSign;
function openSelector() {
$.dialog.open("bdcDyqzxdjController.do?busSelectorItemView", {
    title : "业务选择",
    width : "600px",
    height : "200px",
    lock : true,
    resize : false,
    close: function () {
        var busSelectData = art.dialog.data("busSelectData");
        if (busSelectData) {
            var IS_SIGN = busSelectData.IS_SIGN;
            if (IS_SIGN == '1') {
            	isSign = "1";//是
            }else{
            	isSign = "0";//否
            }
            
        } else {
            window.location.href = __ctxPath + '/contentController/list.do?moduleId=605';
        }
    }
}, false);
}

