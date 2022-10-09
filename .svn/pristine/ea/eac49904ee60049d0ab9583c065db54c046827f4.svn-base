function initEasyUiForm() {
    $("#ZD_TDYT").combobox({
        url:'bdcGyjsjfwzydjController/loadTdytData.do',
        method:'post',
        valueField:'VALUE',
        textField:'TEXT',
        prompt:'请选择土地用途',
        panelHeight:'200',
        multiple:true,
        formatter: function (row) {
            var opts = $(this).combobox('options');
            return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
        },
        onLoadSuccess: function (row) {  //下拉框数据加载成功调用
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');//获取选中的值的values
            $.map(values, function (value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            })
        },
        onSelect: function (row) { //选中一个选项时调用
            var opts = $(this).combobox('options');
            //设置选中值所对应的复选框为选中状态
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', true);
            //获取选中的值的values
            // $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
            $("#ZD_YTSM").val($(this).combobox('getText'))
        },
        onUnselect: function (row) {
            //不选中一个选项时调用
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', false);
            //获取选中的值的values
            // $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
            $("#ZD_YTSM").val($(this).combobox('getText'))
        }
    });

    $("#ZDZL_XIAN").combobox({
        url:'bdcGyjsjfwzydjController/loadZdzlqxData.do',
        method:'post',
        valueField:'VALUE',
        textField:'TEXT',
        panelHeight: '200',
        editable: false,
        required:true,
        formatter: function (row) {
            var opts = $(this).combobox('options');
            return row[opts.textField]
        },
        onLoadSuccess:function(row) {
            $('#ZDZL_XIAN').combobox('setValue','351001');
        },
        onSelect:function (row) {
            $('#ZDZL_ZHENG').combobox('clear');
            $('#ZDZL_CUN').combobox('clear');
            if (row.VALUE) {
                var url = 'bdcGyjsjfwzydjController/loadZdzlzData.do?value='+row.VALUE;
                $('#ZDZL_ZHENG').combobox('reload',url);
            }
        }
    });

    $("#ZDZL_ZHENG").combobox({
        url:'bdcGyjsjfwzydjController/loadZdzlzData.do',
        method:'post',
        valueField:'VALUE',
        textField:'TEXT',
        panelHeight: '200',
        editable: false,
        required:true,
        formatter: function (row) {
            var opts = $(this).combobox('options');
            return row[opts.textField]
        },
        onSelect:function (row) {
            $('#ZDZL_CUN').combobox('clear');
            if (row.VALUE) {
                var url = 'bdcGyjsjfwzydjController/loadZdzlxcData.do?value='+row.VALUE;
                $('#ZDZL_CUN').combobox('reload',url);
            }
        }
    });

    $("#ZDZL_CUN").combobox({
        url:'bdcGyjsjfwzydjController/loadZdzlxcData.do',
        method:'post',
        valueField:'VALUE',
        textField:'TEXT',
        panelHeight: '200',
        editable: false,
        required:true,
        formatter: function (row) {
            var opts = $(this).combobox('options');
            return row[opts.textField]
        },
        onSelect:function (row) {

        }
    });

    /*规划用途自动改变*/
    $("#FW_GHYT").change(function () {
        $("#FW_YTSM").val($("#FW_GHYT").val());
    })
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
}

/*权属来源部分字段不可填写*/
function disabledQslyForm() {
    $("#POWERSOURCE_QSLYLX").attr("disabled", "disabled");
    $("#POWERSOURCE_QSWH").attr("disabled", "disabled");
    $("#POWERSOURCE_DYH").attr("disabled", "disabled");
    $("#POWERSOURCE_QLRMC").attr("disabled", "disabled");
    $("#POWERSOURCE_QSZT").attr("disabled", "disabled");
    $("#POWERSOURCE_ZJLX").attr("disabled", "disabled");
    $("#POWERSOURCE_ZJHM").attr("disabled", "disabled");
    $("#POWERSOURCE_ZDDM").attr("disabled", "disabled");
    $("#POWERSOURCE_GLH").attr("disabled", "disabled");
    $("#POWERSOURCE_FDDBR").attr("disabled", "disabled");
    $("#POWERSOURCE_FDDBR_TEL").attr("disabled", "disabled");
}

function queryTypeFn(){
    var val = $('input[name="ZYDJ_TYPE"]:checked').val();
    if(val == '5'){
        $("#powerDY_div").show();
        $("#yhxwjlxx").show();
        $("#qslyInfo").show();
        $("#zswhPow").hide();
        $("input[name='POWERSOURCE_QSWH']").attr("class","tab_text")
        $("#powTr").hide();
		$("#gasTr").hide();
		$("#waterTr").hide();
        $("[name='isWcnr']").hide();
        $("#ywrXwjlInfo").hide();
        $("#ISQCWB").val('1');
        $("#FW_XZ").val("");
        $("#FW_YTSM").val("");
    } else if (val == '1') {
        $("#powerDY_div").hide();
        $("#yhxwjlxx").hide();
        /*全书来源隐藏*/
        $("#qslyInfo").hide();
        $("#zswhPow").hide();
        $("input[name='POWERSOURCE_QSWH']").attr("class","tab_text")
        $("#powTr").hide();
		$("#gasTr").hide();
		$("#waterTr").hide();
        $("[name='isWcnr']").hide();
        $("#ywrXwjlInfo").hide();
        $("#ISQCWB").val('1');
        $("#FW_XZ").val("动迁房");
        $("#FW_YTSM").val("成套住宅");
    } else if (val == '4') {
        $("#powerDY_div").hide();
        $("#yhxwjlxx").hide();
        $("#qslyInfo").show();
        $("#zswhPow").show();
        $("input[name='POWERSOURCE_QSWH']").attr("class", "tab_text validate[required]");
        $("#powTr").show();
		$("#gasTr").show();
		$("#waterTr").show();
        $("[name='isWcnr']").show();
        $("#ywrXwjlInfo").show();
        $("#ISQCWB").val('0');
        $("#FW_XZ").val("");
        $("#FW_YTSM").val("");
    }
}
function openSelector() {
    $.dialog.open("bdcGyjsjfwzydjController.do?busSelectorItemView", {
        title : "业务选择",
        width : "600px",
        height : "330px",
        lock : true,
        resize : false,
        close: function () {
            var busSelectData = art.dialog.data("busSelectData");
            if (busSelectData) {
                var ZYDJ_TYPE = busSelectData.ZYDJ_TYPE;
                var FINISH_GETTYPE = busSelectData.FINISH_GETTYPE;
                var IS_WQ = busSelectData.IS_WQ;
                var GYFS = busSelectData.GYFS;
                $("input[name='ZYDJ_TYPE']").removeAttr('checked');
                $("input[name='ZYDJ_TYPE'][value='" + ZYDJ_TYPE + "']").prop("checked", true);
                $("input[name='ZYDJ_TYPE']").attr("disabled", true);
                queryTypeFn();
                $("input[name='FINISH_GETTYPE']").removeAttr('checked');
                $("input[name='FINISH_GETTYPE'][value='" + FINISH_GETTYPE + "']").prop("checked", true);
                selectResultFetch();
                if (IS_WQ == '0') {
                    window.location.href = 'http://27.151.80.22:81/ptclfwq/#/login';
                }
                if (GYFS == '2') {
                    $("#qlblFont").show();
                    $("input[name='POWERPEOPLEPRO']").attr("class", "tab_text validate[required]");
                } else {
                    $("#qlblFont").hide();
                    $("input[name='POWERPEOPLEPRO']").attr("class", "tab_text");
                }
            } else {
                window.location.href = __ctxPath + '/contentController/list.do?moduleId=605';
            }
        }
    }, false);
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
    if(flowSubmitObj.busRecord.DY_SFZGEDY){
        setSfzgedy(flowSubmitObj.busRecord.DY_SFZGEDY);
    }

    var ssdjbuyinfojson = flowSubmitObj.busRecord.SSDJBUYINFO_JSON;
    if(null != ssdjbuyinfojson && '' != ssdjbuyinfojson && '[]' != ssdjbuyinfojson){
        var ssdjbuyItems = JSON.parse(ssdjbuyinfojson);
        initSsdjBuy(ssdjbuyItems);
    }
    var ssdjsellinfojson = flowSubmitObj.busRecord.SSDJSELLINFO_JSON;
    if(null != ssdjsellinfojson && '' != ssdjsellinfojson && '[]' != ssdjsellinfojson){
        var ssdjsellItems = JSON.parse(ssdjsellinfojson);
        initSsdjSell(ssdjsellItems);
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

/*创建权属来源信息*/
function daxxFillPowerSource(info) {
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
    return powerSourceItems;
}

/*回填所有档案信息*/
function daxxFillAllInfo(info, bdcdaxxcxInfo, powerSourceItems) {
    FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSJFWZYDJ_FORM");

    //回填权属来源信息

    fillQslyForm(powerSourceItems[0]);

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

    formatDic("ZD_TZM", bdcdaxxcxInfo.ZDTZM); //宗地特征码
    $("#DZWQLLX").attr("disabled", true);
    if (bdcdaxxcxInfo.FDZL && bdcdaxxcxInfo.FDZL != "") {
        $("input[name='LOCATED']").attr("disabled", true);
    }
    var zydjType = $("input[name='ZYDJ_TYPE']:checked").val();
    if (zydjType == '1') {
        $("#FW_XZ").val("动迁房");
        $("#FW_YTSM").val("成套住宅");
    }

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
    if (bdcdaxxcxInfo.XZQ) {
        $("#ZDZL_XIAN").combobox("setValue", bdcdaxxcxInfo.XZQ);
    }

    //回填宗地坐落镇
    if (bdcdaxxcxInfo.DJQ) {
        $("#ZDZL_ZHENG").combobox("setValue", bdcdaxxcxInfo.DJQ);
    }

    //回填宗地坐落乡村
    if (bdcdaxxcxInfo.DJZQ) {
        $("#ZDZL_CUN").combobox("setValue", bdcdaxxcxInfo.DJZQ);
    }

    //回填土地用途
    if (bdcdaxxcxInfo.TDYT) {
        $("#ZD_TDYT").combobox("setValues", bdcdaxxcxInfo.TDYT.split(","));
    }
}

/**
 * 国有建设用地及房屋所有权转移登记查询不动产档案信息
 */
function showSelectBdcfwzdcx(){
    $.dialog.open("bdcApplyController.do?bdcDocInfoSelectorWw&allowCount=1&isAllClo=1", {
        title : "不动产档案信息查询",
        width:"100%",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {
            var bdcdaxxcxInfo = art.dialog.data("bdcdaxxcxInfo");
            if(bdcdaxxcxInfo && bdcdaxxcxInfo.length == 1){
                var cqzt = bdcdaxxcxInfo[0].CQZT.trim();
                var result = {
                    "isPass":true
                }
                /*抵押、限制、查封状态中的房子可以在二手房交易中读取，并无提示弹窗*/
                /*需要对房子状态进行提示，一手房(分户)提示，二手房（存量）不允许办理，数据不会填*/
                var zydjType = $('input[name="ZYDJ_TYPE"]:checked').val();
                if (zydjType == '5' || zydjType == '1') {
                    if (cqzt.indexOf('抵押') != -1 || cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
                        result = {
                            "isPass": true,
                            "msg": "请注意，该不动产单元号为" + cqzt + "状态",
                            "type": "0"
                        };
                    }
                } else if (zydjType == '4') {
                    if (zydjType == '4') {
                        if (cqzt.indexOf('抵押') != -1 || cqzt.indexOf('限制') != -1 || cqzt.indexOf('查封') != -1) {
                            result = {
                                "isPass": false,
                                "msg": "请注意，该不动产单元号为" + cqzt + "状态，不可受理此登记。",
                                "type": "0"
                            };
                        }
                    }
                }

                var info = daxxFillInfoJson(bdcdaxxcxInfo[0]);

                /*创建权属来源信息*/
                var powerSourceItems = daxxFillPowerSource(info);

                if(result.isPass == true){

                    daxxFillAllInfo(info, bdcdaxxcxInfo[0], powerSourceItems);

                    /*回填涉税登记信息*/
                    fillSsdjFormInfo(bdcdaxxcxInfo[0]);

                    if(result.type=="0"){
                        parent.art.dialog({
                            content: result.msg,
                            icon:"warning",
                            ok: true
                        });
                    }
                }else{
                    if(result.type=="0"){
                        parent.art.dialog({
                            content: result.msg,
                            icon:"warning",
                            ok: true
                        });
                    }
                }
                art.dialog.removeData("bdcdaxxcxInfo");
            }else{
                parent.art.dialog({
                    content: "请根据查询选择一条不动产登记信息。",
                    icon:"warning",
                    ok: true
                });
            }
        }
    }, false);
};

//房地产合同备案查询
function showSelectFdchtbacx(){
    $.dialog.open("bdcApplyController.do?fdchtbaxxcxSelectorWw&allowCount=1", {
        title : "房地产合同备案信息查询",
        width:"100%",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {
            var fdchtbaxxInfo = art.dialog.data("fdchtbaxxInfo");
            if (fdchtbaxxInfo && fdchtbaxxInfo.length == 1) {
                /*自动查询档案信息*/
                fillBdcDaxxForm(fdchtbaxxInfo[0]);
                $("#FW_JYJG").val(fdchtbaxxInfo[0].CJJG);
                $("#FW_BAHTH").val(fdchtbaxxInfo[0].FWMMHTH);
                $("input[name='HTBAH']").val(fdchtbaxxInfo[0].FWMMHTH);
                $("input[name='ESTATE_NUM']").val(fdchtbaxxInfo[0].BDCDYH);

                /*涉税登记信息回填*/
                $("#SSDJ_FYXX_JYHTBH").val(fdchtbaxxInfo[0].FWMMHTH);
                $("#SSDJ_FWXX_JYHTBH").val(fdchtbaxxInfo[0].FWMMHTH);
                if (fdchtbaxxInfo[0].CJJG) {
                    var cjjg = (fdchtbaxxInfo[0].CJJG * 10000).toFixed(2);
                    $("#SSDJ_FYXX_JYJG").val(cjjg);
                    $("#SSDJ_FYXX_HTJE").val(cjjg);
                    $("#SSDJ_FYXX_DQYSKJE").val(cjjg);
                    $("#SSDJ_FWXX_JYJG").val(cjjg);
                    $("#SSDJ_FWXX_HTJE").val(cjjg);
                    $("#SSDJ_FWXX_BCJYJE").val(cjjg);
                }
                $("#SSDJ_FWXX_HTQDSJ").val(fdchtbaxxInfo[0].HTQDSJ);
                $("#SSDJ_FWXX_JFSJ").val(fdchtbaxxInfo[0].HTQDSJ);
                $("#SSDJ_FYXX_HTQDSJ").val(fdchtbaxxInfo[0].HTQDSJ);
                $("#SSDJ_FWXX_FJH").val(fdchtbaxxInfo[0].HH);
                $("#SSDJ_FWXX_PQMC").val("步行街1号楼");
                $("#SSDJ_FWXX_PQDM").val("350128100000002001");
                $("#SSDJ_FWXX_PQMC_S").val("350128100000002001");

                /*买方信息*/
                fillSsdjBuyxxForm(fdchtbaxxInfo[0]);
                /*卖方信息*/
                fillSsdjSellxxForm(fdchtbaxxInfo[0]);
                /*回填权利人信息*/
                fillQlrForm(fdchtbaxxInfo[0]);

            } else {
                parent.art.dialog({
                    content: "请根据查询选择一条备案信息。",
                    icon:"warning",
                    ok: true
                });
            }
        }
    }, false);
}

/*
* 不动产预告登记查询
* */
function showSelectBdcygdjcx() {
    $.dialog.open("bdcDyqscdjController.do?bdcygdacxSelectorWw&allowCount=1", {
        title : "不动产预告登记查询",
        width:"100%",
        lock: true,
        resize:false,
        height:"100%",
        close: function () {
            var bdcygdacxInfo = art.dialog.data("bdcygdacxInfo");
            if(bdcygdacxInfo && bdcygdacxInfo.length == 1){
                var data = bdcygdacxInfo[0];
                var info = {
                    "DY_DJJG":"平潭综合实验区不动产登记中心",
                    "DY_DYQRZJHM":data.QLRZJH,
                    "DY_ZWR":data.ZWR,
                    "DY_ZWRZJHM":data.ZWRZJHM,
                    "DY_DYR":data.YWR,
                    "DY_DBSE":data.QDJG,
                    "DY_QLQSSJ":data.QSSJ,
                    "DY_QLJSSJ":data.JSSJ,
                    "DY_BDCDYH":data.BDCDYH,
                    "DY_YWH":data.YWH,
                    "DY_GLH":data.GLH,
                    "DY_DYRZJHM1":data.YWRZJH
                };
                FlowUtil.initFormFieldValue(info,"T_BDCQLC_GYJSJFWZYDJ_FORM");
                $("#DY_DYQR").combobox("setValue", data.QLR);
                formatDic("DY_ZWRZJLX", data.ZWRZJZL);
                formatDic("DY_DYRZJLX1", data.YWRZJZL);
                art.dialog.removeData("bdcygdacxInfo");
            }else{
                parent.art.dialog({
                    content: "请根据查询选择一条预告登记信息。",
                    icon:"warning",
                    ok: true
                });
            }
        }
    }, false);
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

    // 获得权利人信息
    getPowPeopleJson();
    // 获得权属来源信息
    getPowSourceJson();
    // 获取涉税登记买方信息
    getSsdjBuyJson();
    //获取涉税登记卖方信息
    getSsdjSellJson();

    var formData = FlowUtil.getFormEleData(formId);
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    /*宗地用途特殊获取*/
    var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
    if (ZD_TDYT) {
        flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
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

/*提交事件*/
function submitFlow(formId){
    //先判断表单是否验证通过
    var validateResult =$("#"+formId).validationEngine("validate");
    if(validateResult){
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
        if(submitMaterFileJson||submitMaterFileJson==""){
            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
            //将其转换成JSON对象
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);

            var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]:checked').val();
            if (ZYDJ_TYPE == '1') {
                if(!flowSubmitObj.busRecord){
                    parent.art.dialog({
                        content: "请先暂存后提交。",
                        icon:"warning",
                        ok: true
                    });
                    return;
                }
            }
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);

            if (ZYDJ_TYPE == '5') {
                var vaObj = {"fieldName": "DY_DYQR", "fieldText": "抵押权人"};
                var vaFlag = verificaSpeField([vaObj]);
                if (!vaFlag.flag) {
                    parent.art.dialog({
                        content : vaFlag.msg,
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
            // 获取涉税登记买方信息
            getSsdjBuyJson();
            //获取涉税登记卖方信息
            getSsdjSellJson();

            var POWERPEOPLEINFO_JSON = $("input[name='POWERPEOPLEINFO_JSON']").val();
            if (!(POWERPEOPLEINFO_JSON && POWERPEOPLEINFO_JSON != "" && POWERPEOPLEINFO_JSON!="[]")) {
                art.dialog({
                    content : "请填写并保存权利人信息",
                    icon : "error",
                    ok : true
                });
                return;
            }

            var POWERSOURCEINFO_JSON = $("input[name='POWERSOURCEINFO_JSON']").val();
            if (!(POWERSOURCEINFO_JSON && POWERSOURCEINFO_JSON != "" && POWERSOURCEINFO_JSON != "[]")) {
                art.dialog({
                    content : "请填写并保存权属来源信息",
                    icon : "error",
                    ok : true
                });
                return;
            }

            // var FW_GYQK = $("#FW_GYQK").val();
            // if (POWERPEOPLEINFO_JSON && POWERPEOPLEINFO_JSON != "" && POWERPEOPLEINFO_JSON != "[]") {
            //     var POWERPEOPLEINFO_OBJ = JSON.parse(POWERPEOPLEINFO_JSON);
            //     if (POWERPEOPLEINFO_OBJ && POWERPEOPLEINFO_OBJ.length > 1 && FW_GYQK == '0') {
            //         parent.art.dialog({
            //             content : "多个权利人的时候不能选择单独所有。",
            //             icon : "warning",
            //             ok : true
            //         });
            //         return ;
            //     }
            // }

            /*询问笔录校验*/
            var xwblCheckObj = xwblCheck();
            if (!xwblCheckObj.flag) {
                art.dialog({
                    content:xwblCheckObj.errMsg,
                    icon:"warning",
                    ok:true
                });
                return;
            }

            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData(formId);
            for(var index in formData){
                flowSubmitObj[eval("index")] = formData[index];
            }

            /*宗地用途特殊获取*/
            var ZD_TDYT = $("#ZD_TDYT").combobox("getValues")
            if (ZD_TDYT) {
                flowSubmitObj['ZD_TDYT'] = ZD_TDYT.join(",");
            }

            flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
            var postParam = $.param(flowSubmitObj);

            if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
                /*判断此业务是否已被办理过*/
                var bdcdyh = $("input[name='ESTATE_NUM']").val();
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

function xwblCheck() {
    var obj = {};
    obj.flag = true;
    var BDC_XWJL_QLR1 = $("input[name='BDC_XWJL_QLR1']:checked").val();
    var BDC_XWJL_QLR2 = $("input[name='BDC_XWJL_QLR2']:checked").val();
    var BDC_XWJL_QLR3 = $("input[name='BDC_XWJL_QLR3']:checked").val();
    var BDC_XWJL_QLR4 = $("input[name='BDC_XWJL_QLR4']:checked").val();
    var BDC_XWJL_QLR5 = $("input[name='BDC_XWJL_QLR5']:checked").val();
    if (BDC_XWJL_QLR1 == '0') {
        obj.flag = false;
        obj.errMsg = "权利人申请登记事项必须为申请人的真实意思表达";
        return obj;
    }
    if (BDC_XWJL_QLR2 == '1') {
        obj.flag = false;
        obj.errMsg = "权利人申请登记的不动产不能有争议、纠纷或是被查封、冻结、抵押";
        return obj;
    }
    var POWERPEOPLEINFO_JSON = $("input[name='POWERPEOPLEINFO_JSON']").val();
    if (POWERPEOPLEINFO_JSON && POWERPEOPLEINFO_JSON != "" && POWERPEOPLEINFO_JSON != "[]") {
        var POWERPEOPLEINFO_OBJ = JSON.parse(POWERPEOPLEINFO_JSON);
        var POWERPEOPLEINFO_LENGTH = POWERPEOPLEINFO_OBJ.length;
        if (BDC_XWJL_QLR3 == '1') {
            if (POWERPEOPLEINFO_LENGTH != '1') {
                obj.flag = false;
                obj.errMsg = "权利人仅有一个时请选择权利人申请登记的不动产为单独所有";
                return obj;
            }
            if (BDC_XWJL_QLR4 == '1' || BDC_XWJL_QLR5 == '1') {
                obj.flag = false;
                obj.errMsg = "权利人询问笔录中选择单独所有后不可再选择共同所有或者按份共有";
                return obj;
            }
        }
        if (BDC_XWJL_QLR4 == '1') {
            if (POWERPEOPLEINFO_LENGTH != '2') {
                obj.flag = false;
                obj.errMsg = "权利人为两个时请选择权利人申请登记的不动产为共同所有或者按份共有";
                return obj;
            }
            if (BDC_XWJL_QLR5 == '1') {
                obj.flag = false;
                obj.errMsg = "权利人询问笔录中共同共有和按份共有仅能选择一个";
                return obj;
            }
        }
        if (BDC_XWJL_QLR5 == '1') {
            if (POWERPEOPLEINFO_LENGTH == '1') {
                obj.flag = false;
                obj.errMsg = "权利人仅有一个，不可选择权利人询问笔录中按份共有选项";
                return obj;
            }
        }
        if (BDC_XWJL_QLR4 == '0' && BDC_XWJL_QLR3 == '0' && BDC_XWJL_QLR5 == '0') {
            obj.flag = false;
            obj.errMsg = "权利人询问笔录中单独所有，共同共有，按份共有至少需要选择一个";
            return obj;
        }
    }

    return obj;
}

/*当为分户和分户抵押联办时自动查询档案信息*/
function fillBdcDaxxForm(bdcHtbaxxInfo) {
    var val = $('input[name="ZYDJ_TYPE"]:checked').val();
    if (val == '1' || val == '5' || val == '4') {
        if (bdcHtbaxxInfo.BDCDYH) {
            var layload = layer.load('正在查询数据…');
            $.ajaxSettings.async = false;
            $.post("bdcDyqscdjController.do?bdcdaxxcxDatagrid", {"bdcdyh": bdcHtbaxxInfo.BDCDYH}, function (data) {
                layer.close(layload);
                if (data && data.rows && data.rows.length > 0) {
                    for (let i = 0; i < data.rows.length; i++) {
                        var QSZT = data.rows[i].QSZT;
                        if (QSZT == '现势') {
                            var info = daxxFillInfoJson(data.rows[i]);
                            var powerSourceItems = daxxFillPowerSource(info);
                            daxxFillAllInfo(info, data.rows[i], powerSourceItems);
                            /*回填涉税登记信息*/
                            fillSsdjFormInfo(data.rows[i]);
                        }
                    }
                }
            });
            $.ajaxSettings.async = true;
        }
    }
}

function setSfzgedy(value) {
    if (value === "1") {
        $("input[name='DY_ZGZQSE']").attr("disabled", false).addClass("validate[required]");
        $("#zgzqeFont").show();
        $("#bdbzzqseFont").hide();
        $("input[name='DY_DBSE']").attr("disabled", true).removeClass("validate[required]");
        $("select[name='DY_DYFS']").val("2");
    } else {
        $("input[name='DY_ZGZQSE']").attr("disabled", true).removeClass("validate[required]");
        $("#bdbzzqseFont").show();
        $("#zgzqeFont").hide();
        $("input[name='DY_DBSE']").attr("disabled", false).addClass("validate[required]");
        $("select[name='DY_DYFS']").val("1");
    }

}

function fillSsdjFormInfo(info) {
    $("#SSDJ_FYXX_BDCDYH").val(info.BDCDYH);
    $("#SSDJ_FWXX_BDCDYH").val(info.BDCDYH);
    $("#SSDJ_FYXX_XZQH").val('350128');
    $("#SSDJ_FWXX_XZQH").val('350128');
    $("#SSDJ_FYXX_FWDZ").val(info.FDZL);
    $("#SSDJ_FWXX_FWDZ").val(info.FDZL);
    $("#SSDJ_FYXX_FWZLC").val(info.ZCS);
    $("#SSDJ_FYXX_JZMJ").val(info.JZMJ);
    $("#SSDJ_FWXX_JZMJ").val(info.JZMJ);
    //单价
    // if (info.JZMJ && info.JYJG) {
    // 	var dj = (Math.round((info.JYJG * 10000) / info.JZMJ *100) / 100).toFixed(2);
    // 	$("#SSDJ_FYXX_DJ").val(dj);
    // 	$("#SSDJ_FWXX_DJ").val(dj);
    // }
    if (info.JYJG) {
        var jyjg = (info.JYJG * 10000).toFixed(2);
        $("#SSDJ_FYXX_JYJG").val(jyjg);
        $("#SSDJ_FYXX_HTJE").val(jyjg);
        $("#SSDJ_FYXX_DQYSKJE").val(jyjg);
        $("#SSDJ_FWXX_JYJG").val(jyjg);
        // $("#SSDJ_FWXX_BCJYDJ").val(jyjg);
        $("#SSDJ_FWXX_HTJE").val(jyjg);
        $("#SSDJ_FWXX_BCJYJE").val(jyjg);
    }
    $("#SSDJ_FWXX_JCNF").val(info.JGSJ);
    /*回填开发企业名称*/
    var val = $('input[name="ZYDJ_TYPE"]:checked').val();
    if (val == '1' || val == '5') {
        $("#SSDJ_FYXX_KFQYMC").val(info.QLRMC);
        $("#SSDJ_FYXX_NSRSBH").val(info.ZJHM);
    }
}

/*回填涉税登记买方信息*/
function fillSsdjBuyxxForm(info) {
    $(".ssdjbuyinfo_tr").each(function (index) {
        $(this).remove();
    })
    $("#ssdjBuyInfo").attr("trid", "");

    var MSFXM = info.MSFXM;
    var MSFXMARR = splitInfo(MSFXM);
    var MSFZJHM = info.MSFZJHM;
    var MSFZJHMARR = splitInfo(MSFZJHM);

    var MSFLXDH = info.MSFLXDH;
    var MSFLXDHARR = splitInfo(MSFLXDH);

    var MSFLXDZ = info.MSFLXDZ;
    var MSFLXDZARR = splitInfo(MSFLXDZ);

    for (let i = 0; i < MSFXMARR.length; i++) {
        $("#SSDJ_BUY_XM").val(MSFXMARR[i]);//买方姓名
        fillSsdjZjlx(info.MSFZJLB,"SSDJ_BUY_ZJLX");
        $("#SSDJ_BUY_ZJHM").val(MSFZJHMARR[i]);
        $("#SSDJ_BUY_LXDH").val(MSFLXDHARR[i]);
        $("#SSDJ_BUY_LXDZ").val(MSFLXDZARR[i]);
        submitBuyInfo('1');
        if (i < (MSFXMARR.length - 1)) {
            $("#ssdjBuyInfo").attr("trid", "");
        }
    }
}

/*回填涉税登记卖方信息*/
function fillSsdjSellxxForm(info) {
    $(".ssdjsellinfo_tr").each(function (index) {
        $(this).remove();
    })
    $("#ssdjSellInfo").attr("trid", "");

    var ZRFXM = info.ZRFXM;
    var ZRFXMARR = splitInfo(ZRFXM);

    var ZRFZJHM = info.ZRFZJHM;
    var ZRFZJHMARR = splitInfo(ZRFZJHM);

    var ZRFLXDH = info.ZRFLXDH;
    var ZRFLXDHARR = splitInfo(ZRFLXDH);

    var ZRFLXDZ = info.ZRFLXDZ;
    var ZRFLXDZARR = splitInfo(ZRFLXDZ);

    // var ZRFLXR = info.ZRFLXR;
    // var ZRFLXRARR = splitInfo(ZRFLXR);

    var sourceJson = getPowSourceJson();

    for (let i = 0; i < ZRFXMARR.length; i++) {
        $("#SSDJ_SELL_XM").val(ZRFXMARR[i]);
        fillSsdjZjlx(info.ZRFZJLB,"SSDJ_SELL_ZJLX");
        $("#SSDJ_SELL_ZJHM").val(ZRFZJHMARR[i]);
        $("#SSDJ_SELL_LXDH").val(ZRFLXDHARR[i]);
        $("#SSDJ_SELL_LXDZ").val(ZRFLXDZARR[i]);
        $("#SSDJ_SELL_SCQDFWCB").val(0);
        for (let j = 0; j < sourceJson.length; j++) {
            if (sourceJson[j].POWERSOURCE_QLRMC == ZRFXMARR[i]) { //权属来源权利人姓名和卖方姓名相同
                var qlrZjlx = sourceJson[j].POWERSOURCE_ZJLX;
                if (qlrZjlx == '营业执照' || qlrZjlx == '组织机构代码' || qlrZjlx == '统一社会信用代码') { //企业

                } else {
                    sourceJson[j].POWERSOURCE_QLR_TEL = ZRFLXDHARR[i];
                }
            }
        }
        submitSellInfo('1');
        if (i < (ZRFXMARR.length - 1)) {
            $("#ssdjSellInfo").attr("trid", "");
        }
    }

    initPowSource(sourceJson);
}

function splitInfo(str) {
    if (str.indexOf("|") != -1) {
        return str.split("|");
    } else if (str.indexOf(",") != -1) {
        return str.split(",");
    } else {
        return str.split(",");
    }
}

/*格式化证件类别*/
function fillSsdjZjlx(type,id) {
    var parse = "";
    if (type) {
        if (type == "身份证") {
            parse = '201';
        } else if (type == '护照') {
            parse = '227';
        } else if (type == '组织机构代码') {
            parse = '101';
        } else if (type == '营业执照') {
            parse = '102';
        } else if (type == '其它') {
            parse = '299';
        } else if (type == '港澳居民来往内地通行证') {
            parse = '210';
        } else if (type == '统一社会编码'){
            parse = '101';
        } else {
            parse = '201';
        }
    } else {
        parse = "201";
    }
    $("#" + id).val(parse);
}

//设置抵押权人
function setDYQLName(val){
    var datas = $('#DY_DYQR').combobox('getData');
    for(var i=0;i<datas.length;i++){
        if(datas[i].DIC_NAME == val){
            $("input[name='DY_DYQRZJHM']").val(datas[i].DIC_CODE);
            $('#DY_DYQR').combobox('setValue',val);
            $("select[name='DY_DYQRZJLX'] option[value='营业执照']").prop("selected", true);
            break ;
        }
    }
}

//初始化表单
function initNormalForm() {
    hideForm();
    $("#fwjbxx select , #fwjbxx input , #fwjbxx textarea").each(function () {
        $(this).attr("disabled", true);
    });
    $("#FW_SFDJ").attr("disabled", false);
    $("input[name='ZYDJ_TYPE']").attr("disabled", true);
    if ($("input[name='APPLICANT_UNIT']").val() && $("input[name='APPLICANT_UNIT']").val() != "") {
        $("input[name='APPLICANT_UNIT']").attr("disabled", true);
    }
    if ($("input[name='LOCATED']").val() && $("input[name='LOCATED']").val() != "") {
        $("input[name='LOCATED']").attr("disabled", true);
    }
    if ($("#DZWQLLX").val() && $("#DZWQLLX").val() != "") {
        $("#DZWQLLX").attr("disabled", true);
    }


}

//隐藏表单
function hideForm() {
    $("#zdjbxx").hide();
    $("#gyqlxx").hide();
}

/*合同备案号回填权利人信息*/
function fillQlrForm(info) {
    $(".powerpeopleinfo_tr").each(function (index) {
        $(this).remove();
    });
    $("#powerPeopleInfo").attr("trid", "");
    var MSFXM = info.MSFXM;
    var MSFXMARR = splitInfo(MSFXM);
    var MSFZJHM = info.MSFZJHM;
    var MSFZJHMARR = splitInfo(MSFZJHM);

    var MSFLXDH = info.MSFLXDH;
    var MSFLXDHARR = splitInfo(MSFLXDH);

    var MSFLXDZ = info.MSFLXDZ;
    var MSFLXDZARR = splitInfo(MSFLXDZ);

    var MSFZJLB = info.MSFZJLB;

    for (let i = 0; i < MSFXMARR.length; i++) {
        $("input[name='POWERPEOPLENAME']").val(MSFXMARR[i]);//买方姓名
        $("input[name='POWERPEOPLEIDNUM']").val(MSFZJHMARR[i]);
        if (MSFZJHMARR[i] && MSFZJHMARR[i] != "") {
            var sexNum = MSFZJHMARR[i].substr(16, 1);
            if (sexNum % 2 == 1) {
                $("#POWERPEOPLESEX").val('1');
            } else {
                $("#POWERPEOPLESEX").val('2');
            }
        }
        $("input[name='POWERPEOPLEMOBILE").val(MSFLXDHARR[i]);
        $("input[name='POWERPEOPLEADDR").val(MSFLXDZARR[i]);
        $("input[name='APPLICANT_UNIT']").val(MSFXMARR.join("、"));
        $("input[name='NOTIFY_NAME']").val(MSFXMARR.join("、"));
        if (MSFZJLB == '身份证') {
            $("#POWERPEOPLENATURE").val(1);
        } else if (MSFZJLB == '组织机构代码') {
            $("#POWERPEOPLENATURE").val(2);
        } else if (MSFZJLB == '营业执照') {
            $("#POWERPEOPLENATURE").val(2);
        }
        submitPowerPeopleInfoS('1');
        if (i < (MSFXMARR.length - 1)) {
            $("#powerPeopleInfo").attr("trid", "");
        }
    }
    if ($("input[name='APPLICANT_UNIT']") != "") {
        $("input[name='APPLICANT_UNIT']").attr("disabled", true);
    }
}

/*回填权属来源信息*/
function fillQslyForm(info) {
    $(".powersourceinfo_tr").each(function (index) {
        $(this).remove();
    });
    $("#powerSourceInfo").attr("trid", "");
    var POWERSOURCE_QLRMC = info.POWERSOURCE_QLRMC;
    var POWERSOURCE_QLRMCARR = splitInfo(POWERSOURCE_QLRMC);
    var POWERSOURCE_ZJHM = info.POWERSOURCE_ZJHM;
    var POWERSOURCE_ZJHMARR = splitInfo(POWERSOURCE_ZJHM);
    for (let i = 0; i < POWERSOURCE_QLRMCARR.length; i++) {
        $("#POWERSOURCE_QSLYLX").val('3');
        $("#POWERSOURCE_QSWH").val(info.POWERSOURCE_QSWH);
        $("#POWERSOURCE_DYH").val(info.POWERSOURCE_DYH);
        $("#POWERSOURCE_QLRMC").val(POWERSOURCE_QLRMCARR[i]);
        $("#POWERSOURCE_QSZT").val(info.POWERSOURCE_QSZT);
        $("#POWERSOURCE_ZJLX").val(info.POWERSOURCE_ZJLX);
        $("#POWERSOURCE_ZJHM").val(POWERSOURCE_ZJHMARR[i]);
        $("#POWERSOURCE_ZDDM").val(info.POWERSOURCE_ZDDM);
        $("#POWERSOURCE_GLH").val(info.POWERSOURCE_GLH);
        $("#POWERSOURCE_FDDBR").val(info.POWERSOURCE_FDDBR);
        $("#POWERSOURCE_FDDBR_TEL").val(info.POWERSOURCE_FDDBR_TEL);
        $("#POWERSOURCE_DLR").val(info.POWERSOURCE_DLR);
        $("#POWERSOURCE_DLR_TEL").val(info.POWERSOURCE_DLR_TEL);
        $("#POWERSOURCE_DLJGMC").val(info.POWERSOURCE_DLJGMC);
        $("#POWERSOURCE_EFFECT_TIME").val(info.POWERSOURCE_EFFECT_TIME);
        $("#POWERSOURCE_CLOSE_TIME1").val(info.POWERSOURCE_CLOSE_TIME1);
        $("#POWERSOURCE_CLOSE_TIME2").val(info.POWERSOURCE_CLOSE_TIME2);
        $("#POWERSOURCE_CLOSE_TIME3").val(info.POWERSOURCE_CLOSE_TIME3);
        submitPowSourceInfoS("1");
        if (i < (POWERSOURCE_QLRMCARR.length - 1)) {
            $("#powerSourceInfo").attr("trid", "");
        }
    }
}

function delChildItem() {
    $("#xhTitle").hide();
    $("#sxzxTitle").hide();
    $(".materIndex").each(function () {
        $(this).hide();
    });
    $("td[name='busClass']").each(function () {
        $(this).hide();
    });
}

function checkIsPowTransfer(val) {
    var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]:checked').val();
    if (ZYDJ_TYPE == '4') {
        if (val) {
            if (val == '1') {
                $("#dlhhName").show();
                $("#dlhhInput").show();
            } else {
                $("#dlhhName").hide();
                $("#dlhhInput").hide();
            }
        }
    }
}
function checkIsWaterTransfer(val) {
	var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]:checked').val();
	if (ZYDJ_TYPE == '4') {
		if (val) {
			if (val == '1') {
				$("#slhhName").show();
				$("#slhhInput").show();
			} else {
				$("#slhhName").hide();
				$("#slhhInput").hide();
			}
		}
	}
}
function checkIsGasTransfer(val) {
	var ZYDJ_TYPE = $('input[name="ZYDJ_TYPE"]:checked').val();
	if (ZYDJ_TYPE == '4') {
		if (val) {
			if (val == '1') {
				$("#rqhhName").show();
				$("#rqhhInput").show();
			} else {
				$("#rqhhName").hide();
				$("#rqhhInput").hide();
			}
		}
	}
}

function setTzrxmValue(val) {
    $("input[name='NOTIFY_NAME']").val(val)
}

function afgyEvent(val) {
    if (val == '1') {
        var str = "";
        var i = 0;
        $("#powerPeopleInfoList .powerpeopleinfo_tr").each(function () {
            var iteminfo = $(this).find("input[name='iteminfo']").val();
            var json = JSON.parse(iteminfo);
            if (json) {
                var POWERPEOPLEPRO = json.POWERPEOPLEPRO;
                var POWERPEOPLENAME = json.POWERPEOPLENAME;
                str += POWERPEOPLENAME + POWERPEOPLEPRO + "%"
                if (i++ < ($("#powerPeopleInfoList .powerpeopleinfo_tr").length - 1)) {
                    str += " ";
                }
            }
        });
        $("input[name='BDC_XWJL_QLRSZFE']").val(str);
    } else {
        $("input[name='BDC_XWJL_QLRSZFE']").val('');
    }
}


