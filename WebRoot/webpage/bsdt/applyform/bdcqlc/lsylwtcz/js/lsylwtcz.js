function initBeforForm() {
    $("input[name='JBR_NAME']").removeAttr('readonly');
    $("input[name='JBR_ZJHM']").removeAttr('readonly');

    $("#djjfxx_wtcz").attr("style","display:none;");
    $("#djfzxx_wtcz").attr("style","display:none;");
    $("#djgdxx_wtcz").attr("style","display:none;");

    /*宗地房屋信息登记原因和附记事件修改*/
    $("#zddjyy").attr("onclick", "AppUtil.cyyjmbSelector('lsylwtcz_zdyy','GYTD_DJYY');");
    $("#zdfj").attr("onclick","bdcCyyjmbSelector('lsylwtcz_zdfj','GYTD_FJ','textarea');")
    $("#fwdjyy").attr("onclick", "AppUtil.cyyjmbSelector('lsylwtcz_fwyy','FW_DJYY');");
    $("#fwfj").attr("onclick", "bdcCyyjmbSelector('lsylwtcz_fwfj','FW_FJ','textarea');");

    $("#ysjeFont").hide();
    $("input[name='DJJFMX_YSJE']").attr("class", "tab_text validate[]");

    isDisabledForm("fwjbxx", true);
    isDisabledForm("zdjbxx", true);

    /*审批表在开始环节无法打印*/
    $("#SPBDF").attr("onclick","errorAction();");
    $("#SPBSF").attr("onclick","errorAction();");

}

function initAutoTable(flowSubmitObj){
    var powerpeopleinfoJson = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
    if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson && '[]' != powerpeopleinfoJson){
        var powerpeopleItems = JSON.parse(powerpeopleinfoJson);
        initPowPeople(powerpeopleItems);
    }
    var powersourceinfoJson = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
    if(null != powersourceinfoJson && '' != powersourceinfoJson && '[]' != powersourceinfoJson){
        var powersourceItems = JSON.parse(powersourceinfoJson);
        initPowSource(powersourceItems);
    }

    var djfzxx_json = flowSubmitObj.busRecord.DJFZXX_JSON;
    if(null != djfzxx_json && '' != djfzxx_json){
        var djfzxx_jsonItems = JSON.parse(djfzxx_json);
        initDjfzxx_wtcz(djfzxx_jsonItems);
    }
    var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
    if(null != djjfmx_json && '' != djjfmx_json){
        var djjfmx_jsonItems = JSON.parse(djjfmx_json);
        initDjjfxx_wtcz(djjfmx_jsonItems);
    }
}

function FLOW_SubmitFun(flowSubmitObj) {
    /*判断是否有保存意见*/
    if (!isSaveOpinion()) {
        parent.art.dialog({
            content : "填写完登记审核意见后请点击右上角保存！",
            icon : "warning",
            ok : true
        });
        return;
    }
    //先判断表单是否验证通过
    var validateResult = $("#T_BDCQLC_LSYLWTCZ_FORM").validationEngine("validate");
    if (validateResult) {

        // 获得权利人信息
        getPowPeopleJson();
        // 获得权属来源信息
        getPowSourceJson();
        // 获取发证明细数据
        getDjfzxxJson_wtcz();
        // 获取缴费信息
        getDjjfxxJson_wtcz();

        var POWERPEOPLEINFO_JSON = $("#POWERPEOPLEINFO_JSON").val();
        var FW_GYQK = $("#FW_GYQK").val();
        if (POWERPEOPLEINFO_JSON && POWERPEOPLEINFO_JSON != "" && POWERPEOPLEINFO_JSON != "[]") {
            var POWERPEOPLEINFO_OBJ = JSON.parse(POWERPEOPLEINFO_JSON);
            if (POWERPEOPLEINFO_OBJ && POWERPEOPLEINFO_OBJ.length > 1 && FW_GYQK == '0') {
                parent.art.dialog({
                    content : "多个权利人的时候不能选择单独所有。",
                    icon : "warning",
                    ok : true
                });
                return ;
            }
        }

        // /*执法局的操作一定要在资源局之后*/
        // var isZfjApprove = true;
        // if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '执法局') {
        //     $.ajax({
        //         url:"bdcLsylwtcxController.do?isAfterZyjApprove",
        //         method:"POST",
        //         data:{exeId:flowSubmitObj.EFLOW_EXEID},
        //         async:false,
        //         success:function (data) {
        //             if (data) {
        //                 var json = JSON.parse(data);
        //                 isZfjApprove = json.success
        //             }
        //         }
        //     })
        // }
        // if (!isZfjApprove) {
        //     parent.art.dialog({
        //         content : "请等待资源局审批之后再进行审批",
        //         icon : "warning",
        //         ok : true
        //     });
        //     return;
        // }

        //如果是收费环节要先进行登簿操作
        if (!(flowSubmitObj.IS_HANDUP && flowSubmitObj.IS_HANDUP == '1')) {
            if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '收费') {
                var flag = BDCQLC_Autobdcdbcz();
                if(!flag){
                    var isdbflag = $("input[name='BDC_DBZT']").val();
                    if(isdbflag){
                        if(isdbflag =="-1"){
                            parent.art.dialog({
                                content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
                                icon : "warning",
                                ok : true
                            });
                            return;
                        }else if(isdbflag =="0"){
                            var dbjg = $("input[name='BDC_DBJG']").val();
                            parent.art.dialog({
                                content : "登簿异常！"+dbjg,
                                icon : "warning",
                                ok : true
                            });
                            return;
                        }
                    }else{
                        parent.art.dialog({
                            content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
                            icon : "warning",
                            ok : true
                        });
                        return;
                    }
                }
            }
        }
        setGrBsjbr();
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
        if (submitMaterFileJson || submitMaterFileJson == "") {
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData("T_BDCQLC_LSYLWTCZ_FORM");
            for (var index in formData) {
                flowSubmitObj[eval("index")] = formData[index];
            }
            // /*合并任务*/
            // var joinPrenodenames = "资源局,交建局,片区管理局,执法局";
            // if (joinPrenodenames.indexOf(flowSubmitObj.EFLOW_CURUSEROPERNODENAME) != -1) {
            //     flowSubmitObj.EFLOW_JOINPRENODENAMES = joinPrenodenames;
            // }
            /*宗地用途特殊获取*/
            var ZD_TDYT = $("#ZD_TDYT").combobox("getValues");
            if (ZD_TDYT) {
                flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
            }
            return flowSubmitObj;
        } else {
            return null;
        }

    } else {
        return null;
    }
}

//自动登簿分方法
function BDCQLC_Autobdcdbcz(){
    var flag = false;
    var flowSubmitObj = FlowUtil.getFlowObj();
    var exeId = flowSubmitObj.EFLOW_EXEID;
    $.ajaxSettings.async = false;
    var layload = layer.load('正在提交登簿数据…');
    $.post("bdcQlcApplyController/bdcdbcz.do",
        {"eveId":exeId},
        function(responseText, status, xhr) {
            layer.close(layload);
            var resultJson = $.parseJSON(responseText);
            if(resultJson.success){
                var data = $.parseJSON(resultJson.data);
                if(data.retcode == "00" && data.dbzt == "1"){
                    if(data.qzinfo && data.qzinfo.length > 0){
                        var qzinfos = data.qzinfo;
                        flag = bdcclfsflbdbfun(qzinfos,false);
                    }
                }else{
                    $("input[name='BDC_DBZT']").val(data.dbzt);
                    $("input[name='BDC_DBJG']").val(data.ret_msg);
                }
            }else{
                $("input[name='BDC_DBZT']").val("-1");
                $("input[name='BDC_DBJG']").val("登簿失败。");
            }
        }
    );
    return flag;
}

function bdcclfsflbdbfun(qzinfos,dialog){
    var flag = false;
    var currentUser = $("input[name='uploadUserName']").val();
    var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
    var qlrs = getPowPeopleJson();
    if(qzinfos.length == qlrs.length){
        var iflag = 0;
        for(var i=0;i<qzinfos.length;i++){
            if(qzinfos[i].gdzt == '1'){
                for(var j=0;j<qlrs.length;j++){
                    if(qzinfos[i].qlr_mc == qlrs[j].POWERPEOPLENAME
                        && qzinfos[i].qlr_zh == qlrs[j].POWERPEOPLEIDNUM){
                        qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
                        qlrs[j].BDC_CZR = currentUser;
                        qlrs[j].BDC_CZSJ = currentTime;
                        iflag = iflag + 1;
                    }
                }
            }
        }
        initPowPeople(qlrs);
        if(iflag != qlrs.length){
            $("input[name='BDC_DBZT']").val("0");
            $("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
            if(dialog){
                art.dialog({
                    content :"归档失败，存在与受理时的权利不一致的情况。",
                    icon : "warning",
                    ok : true
                });
            }
        }else{
            flag = true;
            $("input[name='BDC_DBZT']").val("1");
            $("input[name='BDC_DBJG']").val("登簿成功");
            /*按钮无法点击*/
            disabledDbBtn("BDC_DBBTN");
            if(dialog){
                art.dialog({
                    content :"登簿成功",
                    icon : "succeed",
                    ok : true
                });
            }

        }
    }else{
        $("input[name='BDC_DBZT']").val("0");
        $("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
        if(dialog){
            art.dialog({
                content :"归档失败，与受理时的权利人数不匹配。",
                icon : "warning",
                ok : true
            });
        }
    }
    return flag;
}

function isSaveOpinion() {
    var flag = true;
    var flowSubmitObj = FlowUtil.getFlowObj();
    if (flowSubmitObj && flowSubmitObj.EFLOW_CURUSEROPERNODENAME) {
        if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '初审') {
            var CS_OPINION_CONTENT = $("textarea[name='CS_OPINION_CONTENT']").val();
            if (CS_OPINION_CONTENT && CS_OPINION_CONTENT != '') {
                var CS_OPINION_NAME = $("input[name='CS_OPINION_NAME']").val();
                var CS_OPINION_TIME = $("input[name='CS_OPINION_TIME']").val();
                if (!(CS_OPINION_NAME && CS_OPINION_TIME && CS_OPINION_NAME != '' && CS_OPINION_TIME != '')) {
                    flag = false;
                }
            }
        }
        if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '预登簿' || flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
            var HZ_OPINION_CONTENT = $("textarea[name='HZ_OPINION_CONTENT']").val();
            if (HZ_OPINION_CONTENT && HZ_OPINION_CONTENT != '') {
                var HZ_OPINION_NAME = $("input[name='HZ_OPINION_NAME']").val();
                var HZ_OPINION_TIME = $("input[name='HZ_OPINION_TIME']").val();
                if (!(HZ_OPINION_NAME && HZ_OPINION_TIME && HZ_OPINION_NAME != '' && HZ_OPINION_TIME != '')) {
                    flag = false;
                }
            }
        }
    }
    return flag;
}

function FLOW_TempSaveFun(flowSubmitObj) {
    // 获得权利人信息
    getPowPeopleJson();
    // 获得权属来源信息
    getPowSourceJson();
    // 获取发证明细数据
    getDjfzxxJson_wtcz();
    // 获取缴费信息
    getDjjfxxJson_wtcz();
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData("T_BDCQLC_LSYLWTCZ_FORM");
    for (var index in formData) {
        flowSubmitObj[eval("index")] = formData[index];
    }
    /*宗地用途特殊获取*/
    var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
    if (ZD_TDYT) {
        flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
    }
    return flowSubmitObj;
}

function FLOW_BackFun(flowSubmitObj) {
    return flowSubmitObj;
}

function findDrawOrg(val) {
    var CHGS_ID = "";
    $.ajax({
        method: 'post',
        url: 'bdcExecutionController.do?findDrawOrgByLocated',
        async: false,
        data: {
            located: val
        },
        success: function (data) {
            if (data) {
                var json = JSON.parse(data);
                if (json.success) {
                    $("#CHGS_ID").val(json.surveyUserId);
                    CHGS_ID = json.surveyUserId;
                }
            }
        }
    });
    return CHGS_ID;
}

/*更改测绘公司*/
function changeDrawOrg(val) {
    var LOCATED = $("input[name='LOCATED']").val();
    var CHGS_ID = findDrawOrg(LOCATED);
    if (CHGS_ID && CHGS_ID != "") {
        art.dialog.confirm("该坐落已有测绘公司测绘，是否要更改？",
            function () {
                $("#CHGS_ID").val(val);
            },
            function () {
                $("#CHGS_ID").val(CHGS_ID);
            });
    }
}

/*隐藏权属来源，宗地，国有土地，房屋信息*/
function hideForm1() {
    $("#powerSourceInfo").hide();
    $("#powerSourceInfoList").hide();
    $("#zdjbxx").hide();
    $("#gyqlxx").hide();
    $("#fwjbxx").hide();
    $("#lsyljfjs").hide();
}

function setQSLYLX(value){
    if(value == "3"){
        // $("#bdcdacxButton").show();
        $("#addOrSaveButton").hide();
        $("#qslyxx input").each(function(index) {
            $(this).attr("disabled", "disabled");
        });
        $("#qslyxx select").each(function(index) {
            $(this).attr("disabled", "disabled");
        });
        //权属来源类型都是可编辑状态
        $('#POWERSOURCENATURE').attr("disabled",false);
    }else{;
        // $("#bdcdacxButton").hide();
        $("#addOrSaveButton").show();
        $("#qslyxx input").each(function(index) {
            $(this).attr("disabled", false);
        });
        $("#qslyxx select").each(function(index) {
            $(this).attr("disabled", false);
        });
    }
}

function showSelectFwdyxx() {
    $.dialog.open("bdcApplyController.do?fwdyxxcxSelector&allowCount=1", {
        title : "房屋单元信息查询",
        width : "100%",
        lock : true,
        resize : false,
        height : "100%",
        close : function() {
            var fwdyxxcxInfo = art.dialog.data("fwdyxxcxInfo");
            if (fwdyxxcxInfo) {
                var data = daxxFillFwdyxxInfoJson(fwdyxxcxInfo[0]);
                data = bdcZdxxCx(data);
                FlowUtil.initFormFieldValue(data,"T_BDCQLC_LSYLWTCZ_FORM");
                art.dialog.removeData("fwdyxxcxInfo");
            }
        }
    }, false);
}

function bdcZdxxCx(data) {
    var BDCDYH = data.ESTATE_NUM;
    $.ajax({
        url:"bdcGyjsscdjController.do?zdxxcxDatagrid",
        type:"post",
        async:false,
        data:{zddm:BDCDYH.substring(0,19)},
        success:function (result) {
            if (result) {
                var json = result;
                var zdData = json.rows[0];
                $("[name='ZD_BM']").val(zdData.ZDDM);
                formatDic('ZD_QLLX', zdData.QLLX);
                $("[name='ZD_QLSDFS']").val(zdData.QLSDFS);
                $("[name='ZD_ZL']").val(zdData.ZL);
                $("[name='ZD_MJ']").val(zdData.ZDMJ);
                if (zdData.TDYT) {
                    $("#ZD_TDYT").combobox("setValues", zdData.TDYT.split(","));
                }
                //回填ZD_QLXZ
                if(zdData.QLXZ){
                    var datas = $("#ZD_QLXZ").combobox("getData");
                    for(var i=0;i<datas.length;i++){
                        if(datas[i].text == zdData.QLXZ){
                            $("#ZD_QLXZ").combobox("setValue",datas[i].value);
                            break;
                        }
                    }
                }
                $("[name='ZD_YTSM']").val(zdData.TDYT);
                
                //回填坐落的镇、村
               /* if(zdData.TD_DZ_Z){
                	var datas = $("#ZDZL_ZHENG").combobox("getData");
                    for(var i=0;i<datas.length;i++){
                        if(datas[i].TEXT == zdData.TD_DZ_Z){
                            $("#ZDZL_ZHENG").combobox("setValue",datas[i].VALUE);
                            break;
                        }
                    }
                }
                if(zdData.TD_DZ_C){
                	var datas = $("#ZDZL_CUN").combobox("getData");
                    for(var i=0;i<datas.length;i++){
                        if(datas[i].TEXT == zdData.TD_DZ_C){
                            $("#ZDZL_CUN").combobox("setValue",datas[i].VALUE);
                            break;
                        }
                    }
                }*/
                
                $("#ZDZL_ZHENG").combobox("setValue",zdData.TD_DZ_Z);
                $("#ZDZL_CUN").combobox("setValue",zdData.TD_DZ_C);
                
                $("[name='ZD_E']").val(zdData.TD_SZ_D);
                $("[name='ZD_S']").val(zdData.TD_SZ_N);
                $("[name='ZD_W']").val(zdData.TD_SZ_X);
                $("[name='ZD_N']").val(zdData.TD_SZ_B);
                $("[name='ZD_LEVEL']").val(zdData.TDDJ);
                $("[name='GYTD_SYQMJ']").val(zdData.ZDMJ);
                $("[name='TDSYQMJ']").val(zdData.ZDMJ);
                formatDic('GYTD_GYFS', zdData.GYQK);
            }
        }
    });
    return data;
}

function daxxFillFwdyxxInfoJson(info) {
    var data = {
        "ESTATE_NUM":info.BDCDYH,
        "FW_ZL":info.ZL,
        "LOCATED":info.ZL,
        "FW_ZH":info.ZH,
        "FW_SZC":info.SZC,
        "FW_HH":info.HH,
        "FW_ZCS":info.ZCS,
        "FW_GHYT":info.GHYT,
        "FW_JGSJ":info.JGSJ,
        "FW_YTSM":info.YTSM,
        "FW_XZ":info.FWXZ,
        "FW_FWJG":info.FWJG,
        "FW_DYDYTDMJ":info.DYDYTDMJ,
        "FW_FTTDMJ":info.FTTDMJ,
        "FW_JZMJ":info.JZMJ,
        "ZDMJ2":info.JZMJ,
        "ZDMJ1":info.JZMJ,
        "FW_ZYJZMJ":info.ZYJZMJ,
        "JZMJ":info.JZMJ,
        "FW_FTJZMJ":info.FTJZMJ,
        "FW_QLLX":4,
        "BDC_BDCDYH":info.BDCDYH
    };
    return data;
}

// function showSelectBdcdaxxcx_TBG() {
//     $.dialog.open("bdcApplyController.do?fwdyxxcxSelector&allowCount=1", {
//         title : "不动产档案信息查询",
//         width : "100%",
//         lock : true,
//         resize : false,
//         height : "100%",
//         close : function() {
//             var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
//             if (bdcdaxxcxInfo) {
//                 var data=bdcdaxxcxInfo[0];
//                 var allInfo = daxxFillInfoJson(data);
//                 FlowUtil.initFormFieldValue(allInfo,"T_BDCQLC_LSYLWTCZ_FORM");
//                 fillInAllEasyUiForm(allInfo,data);
//                 formatDic("ZD_TZM", data.ZDTZM); //宗地特征码
//                 //回填权利类型
//                 var info = daxxFillInfoQslyJson(data)
//                 initPowSource(info);
//                 art.dialog.removeData("bdcdaxxcxInfo");
//             }
//         }
//     }, false);
// }

function daxxFillInfoQslyJson(data) {
    var items = [];
    var info = {
        "POWERSOURCEIDNUM":data.BDCQZH,
        "BDC_BDCDYH":data.BDCDYH,
        "POWERSOURCEIDTYPE":data.QSZT,
        "QLR":data.QLRMC,
        "BDC_ZDDM":data.ZDDM,
        "BDC_QLRZJLX":data.ZJLX,
        "BDC_QLRZJHM":data.ZJHM,
        "BDC_QLKSSJ":data.QLQSSJ,
        "BDC_QLJSSJ1":data.QLJSSJ,
        "BDC_QLJSSJ2":data.QLJSSJ1,
        "POWERSOURCENATURE":3
    };
    items.push(info);
    return items;
}

function fillInAllEasyUiForm(info,data) {
//回填权利类型
    $("#ZD_QLLX option").each(function(){
        var text = $(this).text();
        if(text == info.ZD_QLLX.trim()){
            $(this).attr("selected",true);
        }
    });

    //回填GYTD_GYFS
    $("#GYTD_GYFS option").each(function(){
        var text = $(this).text();
        if(text == info.GYTD_GYFS.trim()){
            $(this).attr("selected",true);
        }
    });
    //回填FW_GYQK
    $("#FW_GYQK option").each(function(){
        var text = $(this).text();
        if(text == info.FW_GYQK.trim()){
            $(this).attr("selected",true);
        }
    });
    //回填FW_QLLX
    $("#FW_QLLX option").each(function(){
        var text = $(this).text();
        if(text == info.FW_QLLX.trim()){
            $(this).attr("selected",true);
        }
    });

    //回填ZD_QLXZ
    if(info.ZD_QLXZ){
        var datas = $("#ZD_QLXZ").combobox("getData");
        for(var i=0;i<datas.length;i++){
            if(datas[i].text == info.ZD_QLXZ){
                $("#ZD_QLXZ").combobox("setValue",datas[i].value);
                break;
            }
        }
    }

    //回填宗地坐落区县
    if (data.XZQ) {
        $("#ZDZL_XIAN").combobox("setValue", data.XZQ);
    }

    //回填宗地坐落镇
    if (data.DJQ) {
        $("#ZDZL_ZHENG").combobox("setValue", data.DJQ);
    }

    //回填宗地坐落乡村
    if (data.DJZQ) {
        $("#ZDZL_CUN").combobox("setValue", data.DJZQ);
    }

    //回填土地用途
    if (data.TDYT) {
        $("#ZD_TDYT").combobox("setValues", data.TDYT.split(","));
    }
}

function daxxFillInfoJson(bdcdaxxcxInfo) {
    var info = {
        "ESTATE_NUM":bdcdaxxcxInfo.BDCDYH,
        "LOCATED":bdcdaxxcxInfo.FDZL,
        "POWERSOURCE_QLRMC":bdcdaxxcxInfo.QLRMC,
        "POWERSOURCE_QLRMC":bdcdaxxcxInfo.QLRMC,
        "POWERSOURCE_QSZT":bdcdaxxcxInfo.QSZT,
        "POWERSOURCE_ZJLX":bdcdaxxcxInfo.ZJLX,
        "POWERSOURCE_ZJHM":bdcdaxxcxInfo.ZJHM,
        "POWERSOURCE_ZDDM":bdcdaxxcxInfo.ZDDM,
        "POWERSOURCE_GLH":bdcdaxxcxInfo.GLH,
        "POWERSOURCE_EFFECT_TIME":bdcdaxxcxInfo.QLQSSJ,//权利开始时间
        "POWERSOURCE_CLOSE_TIME1":bdcdaxxcxInfo.QLJSSJ,//权利结束时间
        "POWERSOURCE_CLOSE_TIME2":bdcdaxxcxInfo.QLJSSJ1,
        "POWERSOURCE_CLOSE_TIME3":bdcdaxxcxInfo.QLJSSJ2,
        "ZD_BM":bdcdaxxcxInfo.ZDDM,
        "ZD_QLLX":bdcdaxxcxInfo.ZDQLLX,//宗地权利类型
        "ZD_QLSDFS":bdcdaxxcxInfo.QLSDFS,
        "ZD_ZL":bdcdaxxcxInfo.TDZL,
        "ZD_MJ":bdcdaxxcxInfo.ZDMJ,
        "ZD_YTSM":bdcdaxxcxInfo.TDYTSM,
        "ZD_QLXZ":bdcdaxxcxInfo.QLXZ,//权利性质
        "ZD_LEVEL":bdcdaxxcxInfo.DJ,
        "ZD_RJL":bdcdaxxcxInfo.RJL,
        "ZD_JZXG":bdcdaxxcxInfo.JZXG,
        "ZD_JZMD":bdcdaxxcxInfo.JZMD,
        "ZD_E":bdcdaxxcxInfo.TD_SZ_D,
        "ZD_W":bdcdaxxcxInfo.TD_SZ_X,
        "ZD_N":bdcdaxxcxInfo.TD_SZ_B,
        "ZD_S":bdcdaxxcxInfo.TD_SZ_N,
        "GYTD_BEGIN_TIME":bdcdaxxcxInfo.QLQSSJ,
        "GYTD_END_TIME1":bdcdaxxcxInfo.QLJSSJ,
        "GYTD_END_TIME2":bdcdaxxcxInfo.QLJSSJ1,
        "GYTD_END_TIME3":bdcdaxxcxInfo.QLJSSJ2,
        "GYTD_GYFS":bdcdaxxcxInfo.GYFS,
        "GYTD_QDJG":bdcdaxxcxInfo.JG,
        "GYTD_SYQMJ":bdcdaxxcxInfo.SYQMJ,
        "GYTD_QSZT":bdcdaxxcxInfo.QSZT,
        "FW_ZL":bdcdaxxcxInfo.FDZL,
        "FW_ZH":bdcdaxxcxInfo.ZH,
        "FW_SZC":bdcdaxxcxInfo.SZC,
        "FW_HH":bdcdaxxcxInfo.HH,
        "FW_ZCS":bdcdaxxcxInfo.ZCS,
        "FW_GHYT":bdcdaxxcxInfo.FW_GHYT,
        "FW_YTSM":bdcdaxxcxInfo.GHYTSM,
        "FW_XZ":bdcdaxxcxInfo.FWXZ,
        "FW_FWJG":bdcdaxxcxInfo.FWJG,//房屋结构
        "FW_DYDYTDMJ":bdcdaxxcxInfo.DYTDMJ,
        "FW_FTTDMJ":bdcdaxxcxInfo.FTTDMJ,
        "FW_JZMJ":bdcdaxxcxInfo.JZMJ,
        "YDMJ":bdcdaxxcxInfo.JZMJ,
        "FW_GYQK":bdcdaxxcxInfo.GYFS,//房屋共有情况
        "FW_ZYJZMJ":bdcdaxxcxInfo.ZYJZMJ,
        "FW_FTJZMJ":bdcdaxxcxInfo.FTJZMJ,
        "FW_QLLX":bdcdaxxcxInfo.FW_QLLX,
        "FW_JGSJ":bdcdaxxcxInfo.JGSJ,
        "POWERSOURCE_DYH":bdcdaxxcxInfo.BDCDYH,
        "POWERSOURCE_QSWH":bdcdaxxcxInfo.BDCQZH,//证书文号（权属来源）
        "POWERSOURCE_QSLYLX":"3",//证书文号（权属来源）
        "BDCDYH":bdcdaxxcxInfo.BDCDYH,//不动产单元号
        "ZDDM":bdcdaxxcxInfo.ZDDM,//宗地代码
        "FWBM":bdcdaxxcxInfo.FWBM,//房屋编码
        "YWH":bdcdaxxcxInfo.YWH,//业务号
        "ZXYWH":bdcdaxxcxInfo.ZXYWH,//注销业务号
        "GLH":bdcdaxxcxInfo.GLH//关联号
    };
    return info;
}

//不动产产权证书打印与预览
function bdcCQZSprint(typeId){
    if (typeId == 2) {
        var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
        if(!BDC_QZBSM){
            art.dialog({
                content :"不动产产权证书权证标识码未填写。",
                icon : "warning",
                ok : true
            });
            return null;
        }
    }
    // typeId: 1为证书预览  2为证书打印
    var title = "证书打印";
    var templateAlias = 'BDCQZ';
    if(typeId==1) {
        title = "证书预览";
    }
    var dataStr = "";
    dataStr +="&QLRZJH="+$("input[name='POWERPEOPLEIDNUM']").val();
    dataStr +="&BDCQZH="+$("input[name='BDC_SZZH']").val();
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
}

function openDrawFileUploadDialog() {
//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
    art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDCQLC_LSYLWTCZ', {
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
                    if (fileType.indexOf(uploadedFileInfo.fileSuffix) > -1) {
                        attachType = "image";
                    }
                    var fileid = $("input[name='DRAW_FILE_ID']").val();
                    if (fileid != "" && fileid != null) {
                        $("input[name='DRAW_FILE_ID']").val(fileid + "," + uploadedFileInfo.fileId);
                    } else {
                        $("input[name='DRAW_FILE_ID']").val(uploadedFileInfo.fileId);
                    }
                    var spanHtml = "<p style='margin-left: 120px;' id=\"" + uploadedFileInfo.fileId + "\"><a href=\"" + __file_server
                        + "download/fileDownload?attachId=" + uploadedFileInfo.fileId
                        + "&attachType=" + attachType + "\" ";
                    spanHtml += (" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                    spanHtml += "<font color=\"blue\">" + uploadedFileInfo.fileName + "</font></a>";
                    spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + uploadedFileInfo.fileId + "');\" ><font color=\"red\">删除</font></a></p>";
                    $("#drawFileUpload").append(spanHtml);

                }
            }
            art.dialog.removeData("uploadedFileInfo");
        }
    });
}

/*初始化测绘材料列表*/
function initDrawFileList(fileIds,nodeName) {
    $.ajaxSettings.async = false;
    $.post("bdcGyjsjfwzydjController.do?taxRelatedFileList", {fileIds: fileIds}, function (data) {
        if (data) {
            var json = JSON.parse(data);
            var str = "";
            var len = json.length;
            for (let i = 0; i < json.length; i++) {
                var fileId = json[i].FILE_ID;
                var fileName = json[i].FILE_NAME;
                str += "<p id='" + fileId + "'><a href=\"javascript:void(0);\" style=\"color: blue;margin-right: 5px;\" onclick=\"AppUtil.downLoadFile('" + fileId + "')\">";
                str += "<font style=\"color: blue;\">" + fileName + "</font></a>";
                str += "<a href=\"javascript:void(0);\" class='delFile' style='display: none;' onclick=\"delUploadFile('" + fileId + "');\"><font color=\"red\">删除</font></a></p>";
            }
            $("#drawFileUpload").append(str);
            if (len == 0 && nodeName != '测绘') {
                $("#upload").hide();
            }
        } else {
            if (nodeName != '测绘') {
                $("#upload").hide();
            }
        }
    });
    $.ajaxSettings.async = true;
}

function goPrintTemplate(TemplateName,typeId) {
    var dataStr = "";
    dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
    dataStr +="&typeId="+typeId;   //4代表为权证模板
    //dataStr +="&TemplatePath="+TemplatePath;
    dataStr +="&TemplateName="+TemplateName;
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

//不动产产权证书打印与预览
function bdcCQZSprint(typeId){
    if (typeId == 2) {
        var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
        if(!BDC_QZBSM){
            art.dialog({
                content :"不动产产权证书权证标识码未填写。",
                icon : "warning",
                ok : true
            });
            return null;
        }
    }
    // typeId: 1为证书预览  2为证书打印
    var title = "证书打印";
    var templateAlias = 'BDCQZ';
    if(typeId==1) {
        title = "证书预览";
    }
    var dataStr = "";
    dataStr +="&QLRZJH="+$("input[name='POWERPEOPLEIDNUM']").val();
    dataStr +="&BDCQZH="+$("input[name='BDC_SZZH']").val();
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
}

function initBdcLsyljfjs(flowSubmitObj) {
    var EXE_SUBBUSCLASS = flowSubmitObj.busRecord.EXE_SUBBUSCLASS;
    /*默认单位集资房*/
    if (!(EXE_SUBBUSCLASS && EXE_SUBBUSCLASS != "")) {
        $("input[name='EXE_SUBBUSCLASS'][value='单位集资房']").attr("checked", true);
        $("#dwjzf").show();
        $("#zjfwfgxs").hide();
        $("#jcgzxm").hide();
    }

    chooseBdcLsyljfjsType(EXE_SUBBUSCLASS);
}

function chooseBdcLsyljfjsType(EXE_SUBBUSCLASS) {
    var flowSubmitObj = FlowUtil.getFlowObj();
    $("#lsyljfjs").show();
    if (EXE_SUBBUSCLASS == '单位集资房') {
        $("#dwjzf").show();
        $("#zjfwfgxs").hide();
        $("#jcgzxm").hide();
        $("#jgText").text("用地出让金（元）：");
    } else if (EXE_SUBBUSCLASS == '国有土地上自建房屋分割销售') {
        $("#dwjzf").hide();
        $("#zjfwfgxs").show();
        $("#jcgzxm").hide();
        $("#jgText").text("补缴标准（元）：");
        var TDZRQX1 = $("select[name='TDZRQX1']").val();
        if (TDZRQX1 == 1) {
            $("input[name='YDMJ']").removeClass("required");
        } else {
            $("input[name='YDMJ']").addClass("required");
        }
    } else if (EXE_SUBBUSCLASS == '旧城改造') {
        $("#dwjzf").hide();
        $("#zjfwfgxs").hide();
        $("#jcgzxm").show();
        $("#jgText").text("补缴标准（元）：");
    } else {
        $("#lsyljfjs").hide();
    }

    if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '开始') {
        $("#lsyljfjs").hide();
    }
}

function changeTdzrqx(val) {
    if (val == 1) {
        $("input[name='YDMJ']").removeClass("required");
    } else {
        $("input[name='YDMJ']").addClass("required");
    }
}

//------------------------------------身份身份证读取开始---------------------------------------------------
function MyGetData()//OCX读卡成功后的回调函数
{
// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>
// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>
}

function MyClearData()//OCX读卡失败后的回调函数
{
    alert("未能有效识别身份证，请重新读卡！");
    $("input[name='POWERPEOPLENAME']").val("");
    $("input[name='POWERPEOPLEIDNUM']").val("");
}

function MyGetErrMsg()//OCX读卡消息回调函数
{
// 			Status.value = GT2ICROCX.ErrMsg;
}

function PowerPeopleRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='POWERPEOPLENAME']").val(GT2ICROCX.Name);
        $("[name='POWERPEOPLEIDNUM']").val(GT2ICROCX.CardNo);
        checkLimitPerson();
    }
}
function PowLegalRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='POWLEGALNAME']").val(GT2ICROCX.Name);
        $("[name='POWLEGALIDNUM']").val(GT2ICROCX.CardNo);
        checkLimitPerson();
    }
}
function PowAgentRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='POWAGENTNAME']").val(GT2ICROCX.Name);
        $("[name='POWAGENTIDNUM']").val(GT2ICROCX.CardNo);
        checkLimitPerson();
    }
}

function PowSrcRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='POWERSOURCE_QLRMC']").val(GT2ICROCX.Name);
        $("[name='POWERSOURCE_ZJHM']").val(GT2ICROCX.CardNo);
        $("#POWERSOURCE_ZJLX").val("身份证");
        checkLimitPerson();
    }
}

function PowFRDHRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='POWERSOURCE_FRDB']").val(GT2ICROCX.Name);
        $("[name='POWERSOURCE_FRZJHM']").val(GT2ICROCX.CardNo);
        checkLimitPerson();
    }
}

function PowDLRRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='POWERSOURCE_DLRXM']").val(GT2ICROCX.Name);
        $("[name='POWERSOURCE_DLRZJHM']").val(GT2ICROCX.CardNo);
        checkLimitPerson();
    }
}

function print()//打印
{

    GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
}
function LZRRead(){
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0) {
        GT2ICROCX.ReadCard();
        $("input[name='QZR_NAME']").val(GT2ICROCX.Name);
        $("input[name='QZR_ZJH']").val(GT2ICROCX.CardNo);
    }
}
//------------------------------------身份身份证读取结束---------------------------------------------------
