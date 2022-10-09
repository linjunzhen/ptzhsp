function FLOW_SubmitFun(flowSubmitObj) {
    //先判断表单是否验证通过
    var validateResult = $("#T_BDCQLC_JTJS_FORM").validationEngine("validate");
    if (validateResult) {
        if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '登簿') {
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

        // 获得权利人信息
        getPowPeopleJson();
        // 获得权属来源信息
        getPowSourceJson();
        // 获取发证明细数据
        getDjfzxxJson_jtjs();
        // 获取缴费信息
        getDjjfxxJson_jtjs();

        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", 1);
        if (submitMaterFileJson || submitMaterFileJson == "") {
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData("T_BDCQLC_JTJS_FORM");
            for (var index in formData) {
                flowSubmitObj[eval("index")] = formData[index];
            }
            /*宗地用途特殊获取*/
            var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
            if (ZD_TDYT) {
                flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
            }
            return flowSubmitObj;
        } else {
            return null;
        }
    }
}

function FLOW_TempSaveFun(flowSubmitObj) {

    // 获得权利人信息
    getPowPeopleJson();
    // 获得权属来源信息
    getPowSourceJson();
    // 获取发证明细数据
    getDjfzxxJson_jtjs();
    // 获取缴费信息
    getDjjfxxJson_jtjs();

    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData("T_BDCQLC_JTJS_FORM");
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

/*初始化权利信息*/
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
        initDjfzxx_jtjs(djfzxx_jsonItems);
    }
    var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
    if(null != djjfmx_json && '' != djjfmx_json){
        var djjfmx_jsonItems = JSON.parse(djjfmx_json);
        initDjjfxx_jtjs(djjfmx_jsonItems);
    }

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

/*
* 不动产登簿操作
* */
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
                            var qzinfos = data.qzinfo;
                            var qlrs = JSON.parse(flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON);
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
                                    art.dialog({
                                        content :"归档失败，存在与受理时的权利不一致的情况。",
                                        icon : "warning",
                                        ok : true
                                    });
                                }else{
                                    $("input[name='BDC_DBZT']").val("1");
                                    $("input[name='BDC_DBJG']").val("登簿成功");
                                    art.dialog({
                                        content :"登簿成功",
                                        icon : "succeed",
                                        ok : true
                                    });
                                }
                            }else{
                                $("input[name='BDC_DBZT']").val("0");
                                $("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
                                art.dialog({
                                    content :"归档失败，与受理时的权利人数不匹配。",
                                    icon : "warning",
                                    ok : true
                                });
                            }
                        }
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

function showSelectBdcdaxxcx() {
    $.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0", {
        title : "不动产档案信息查询",
        width : "100%",
        lock : true,
        resize : false,
        height : "100%",
        close : function() {
            var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
            if (bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1) {
                var cqzt = bdcdaxxcxInfo[0].CQZT.trim();
                var result = {
                    "isPass": true
                };
                if (cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
                    result = {
                        "isPass": false,
                        "msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
                        "type": "0"
                    };
                }
                var info = {
                    "ESTATE_NUM":bdcdaxxcxInfo[0].BDCDYH,
                    "LOCATED":bdcdaxxcxInfo[0].FDZL,
                    "POWERSOURCE_QLRMC":bdcdaxxcxInfo[0].QLRMC,
                    "POWERSOURCE_QLRMC":bdcdaxxcxInfo[0].QLRMC,
                    "POWERSOURCE_QSZT":bdcdaxxcxInfo[0].QSZT,
                    "POWERSOURCE_ZJLX":bdcdaxxcxInfo[0].ZJLX,
                    "POWERSOURCE_ZJHM":bdcdaxxcxInfo[0].ZJHM,
                    "POWERSOURCE_ZDDM":bdcdaxxcxInfo[0].ZDDM,
                    "POWERSOURCE_GLH":bdcdaxxcxInfo[0].GLH,
                    "POWERSOURCE_EFFECT_TIME":bdcdaxxcxInfo[0].QLQSSJ,//权利开始时间
                    "POWERSOURCE_CLOSE_TIME1":bdcdaxxcxInfo[0].QLJSSJ,//权利结束时间
                    "POWERSOURCE_CLOSE_TIME2":bdcdaxxcxInfo[0].QLJSSJ1,
                    "POWERSOURCE_CLOSE_TIME3":bdcdaxxcxInfo[0].QLJSSJ2,
                    "ZD_BM":bdcdaxxcxInfo[0].ZDDM,
                    "ZD_QLLX":bdcdaxxcxInfo[0].ZDQLLX,//宗地权利类型
                    "ZD_QLSDFS":bdcdaxxcxInfo[0].QLSDFS,
                    "ZD_ZL":bdcdaxxcxInfo[0].TDZL,
                    "ZD_MJ":bdcdaxxcxInfo[0].ZDMJ,
                    "ZD_YTSM":bdcdaxxcxInfo[0].TDYTSM,
                    "ZD_QLXZ":bdcdaxxcxInfo[0].QLXZ,//权利性质
                    "ZD_LEVEL":bdcdaxxcxInfo[0].DJ,
                    "ZD_RJL":bdcdaxxcxInfo[0].RJL,
                    "ZD_JZXG":bdcdaxxcxInfo[0].JZXG,
                    "ZD_JZMD":bdcdaxxcxInfo[0].JZMD,
                    "ZD_E":bdcdaxxcxInfo[0].TD_SZ_D,
                    "ZD_W":bdcdaxxcxInfo[0].TD_SZ_X,
                    "ZD_N":bdcdaxxcxInfo[0].TD_SZ_B,
                    "ZD_S":bdcdaxxcxInfo[0].TD_SZ_N,
                    "GYTD_BEGIN_TIME":bdcdaxxcxInfo[0].QLQSSJ,
                    "GYTD_END_TIME1":bdcdaxxcxInfo[0].QLJSSJ,
                    "GYTD_END_TIME2":bdcdaxxcxInfo[0].QLJSSJ1,
                    "GYTD_END_TIME3":bdcdaxxcxInfo[0].QLJSSJ2,
                    "GYTD_GYFS":bdcdaxxcxInfo[0].GYFS,
                    "GYTD_QDJG":bdcdaxxcxInfo[0].JG,
                    "GYTD_SYQMJ":bdcdaxxcxInfo[0].SYQMJ,
                    "GYTD_QSZT":bdcdaxxcxInfo[0].QSZT,
                    "FW_ZL":bdcdaxxcxInfo[0].FDZL,
                    "FW_ZH":bdcdaxxcxInfo[0].ZH,
                    "FW_SZC":bdcdaxxcxInfo[0].SZC,
                    "FW_HH":bdcdaxxcxInfo[0].HH,
                    "FW_ZCS":bdcdaxxcxInfo[0].ZCS,
                    "FW_GHYT":bdcdaxxcxInfo[0].FW_GHYT,
                    "FW_YTSM":bdcdaxxcxInfo[0].GHYTSM,
                    "FW_XZ":bdcdaxxcxInfo[0].FWXZ,
                    "FW_FWJG":bdcdaxxcxInfo[0].FWJG,//房屋结构
                    "FW_JYJG":bdcdaxxcxInfo[0].JYJG,//交易价格
                    "FW_DYDYTDMJ":bdcdaxxcxInfo[0].DYTDMJ,
                    "FW_FTTDMJ":bdcdaxxcxInfo[0].FTTDMJ,
                    "FW_JZMJ":bdcdaxxcxInfo[0].JZMJ,
                    "FW_GYQK":bdcdaxxcxInfo[0].GYFS,//房屋共有情况
                    "FW_ZYJZMJ":bdcdaxxcxInfo[0].ZYJZMJ,
                    "FW_FTJZMJ":bdcdaxxcxInfo[0].FTJZMJ,
                    "FW_QLLX":bdcdaxxcxInfo[0].FW_QLLX,
                    "FW_JGSJ":bdcdaxxcxInfo[0].JGSJ,
                    "POWERSOURCE_DYH":bdcdaxxcxInfo[0].BDCDYH,
                    "POWERSOURCE_QSWH":bdcdaxxcxInfo[0].BDCQZH,//证书文号（权属来源）
                    "POWERSOURCE_QSLYLX":"3",//证书文号（权属来源）
                    "BDCDYH":bdcdaxxcxInfo[0].BDCDYH,//不动产单元号
                    "ZDDM":bdcdaxxcxInfo[0].ZDDM,//宗地代码
                    "FWBM":bdcdaxxcxInfo[0].FWBM,//房屋编码
                    "YWH":bdcdaxxcxInfo[0].YWH,//业务号
                    "ZXYWH":bdcdaxxcxInfo[0].ZXYWH,//注销业务号
                    "GLH":bdcdaxxcxInfo[0].GLH//关联号
                };
                //定义个startWithOwn
                String.prototype.startWithOwn = function(s) {
                    if (s == null || s == "" || this.length == 0 || s.length > this.length)
                        return false;
                    if (this.substr(0, s.length) == s)
                        return true;
                    else
                        return false;
                    //return true;
                }
                var powerSource = {};
                for(var name in info){
                    if(name.startWithOwn("POWERSOURCE_")){
                        powerSource[name]=info[name];
                    }
                }

                var powerSourceItems = [];
                powerSourceItems.push(powerSource);
                if (result.isPass == true) {
                    FlowUtil.initFormFieldValue(info,"T_BDCQLC_JTJS_FORM");
                    //回填权属来源信息
                    initPowSource(powerSourceItems);
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
                    formatDic("ZD_TZM", bdcdaxxcxInfo[0].ZDTZM); //宗地特征码
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
                    if (bdcdaxxcxInfo[0].XZQ) {
                        $("#ZDZL_XIAN").combobox("setValue", bdcdaxxcxInfo[0].XZQ);
                    }

                    //回填宗地坐落镇
                    if (bdcdaxxcxInfo[0].DJQ) {
                        $("#ZDZL_ZHENG").combobox("setValue", bdcdaxxcxInfo[0].DJQ);
                    }

                    //回填宗地坐落乡村
                    if (bdcdaxxcxInfo[0].DJZQ) {
                        $("#ZDZL_CUN").combobox("setValue", bdcdaxxcxInfo[0].DJZQ);
                    }

                    //回填土地用途
                    if (bdcdaxxcxInfo[0].TDYT) {
                        $("#ZD_TDYT").combobox("setValues", bdcdaxxcxInfo[0].TDYT.split(","));
                    }

                    if(result.type=="0"){
                        parent.art.dialog({
                            content: result.msg,
                            icon:"warning",
                            ok: true
                        });
                    }
                } else {
                    if (result.type == "0") {
                        parent.art.dialog({
                            content: result.msg,
                            icon: "warning",
                            ok: true
                        });
                    }
                }
                art.dialog.removeData("bdcdaxxcxInfo");
            } else {
                parent.art.dialog({
                    content: "请根据查询选择一条不动产登记信息。",
                    icon:"warning",
                    ok: true
                });
            }
        }
    }, false);
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

function yjmbSelect() {
    $("#zddjyy").attr("onclick","bdcCyyjmbSelector('jtjjzd_yy','GYTD_DJYY','input')")
    $("#zdfj").attr("onclick","bdcCyyjmbSelector('jtjjzd_fj','GYTD_FJ','textarea')")
}

function disabledForm() {
    $("#T_BDCQLC_JTJS_FORM select , #T_BDCQLC_JTJS_FORM input , #T_BDCQLC_JTJS_FORM textarea").each(function (index) {
        $(this).attr("disabled", "disabled");
    });
    $("#BDC_DBBTN").attr("disabled", false);
    $("#BDC_QZVIEW").attr("disabled", false);
    $("#BDC_QZPRINT").attr("disabled", false);
}




