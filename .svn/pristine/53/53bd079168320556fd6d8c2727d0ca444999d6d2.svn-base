/*************JS需要公共代码开始***************/
//不动产档案信息查询
bdcdaxxcxTr = 1;
function showSelectBdcdaxxcx() {
    $.dialog.open("bdcDyqscdjController.do?bdcdaxxcxSelector&allowCount=0", {
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
                    var powerSourceTableTrMx = getPowerSourceTableTrMx(bdcdaxxcxInfo[i]);
                    html += '<tr class="bdcdydacxTr" id="bdcdaxxcxTr_' + bdcdaxxcxTr + '">';
                    html += '<input type="hidden" name="bdcdaxxcx"/>';
                    html += "<input type='hidden' name='PowerSourceTableTrMx' value='"+powerSourceTableTrMx+"'>";
                    html += '<td style="text-align: center;">' + bdcdaxxcxTr + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].QLRMC + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].ZJHM + '</td>';
                    html += '<td style="text-align: center;">不动产权证书</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].BDCQZH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].CQZT + '</td>';
                    html +='<td style="text-align: center;"><a href="javascript:void(0);" class="eflowbutton" onclick="loadBdcdaxxcx(this);">查 看</a></td>td>';
                    html += '</tr>';
                    $("#PowerSourceTable").append(html);
                    $("#bdcdaxxcxTr_" + bdcdaxxcxTr + " input[name='bdcdaxxcx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
                    html="";
                    bdcdaxxcxTr++;
                }
                var data=bdcdaxxcxInfo[0];
                data.ESTATE_NUM=data.BDCDYH,
                    data.LOCATED=data.FDZL,
                    data.POWERSOURCE_QLRMC=data.QLRMC,
                    data.POWERSOURCE_QLRMC=data.QLRMC,
                    data.QLR = data.QLRMC,
                    data.POWERSOURCE_QSZT=data.QSZT,
                    data.POWERSOURCE_ZJLX=data.ZJLX,
                    data.POWERSOURCE_ZJHM=data.ZJHM,
                    data.BDC_QLRZJHM = data.ZJHM,
                    data.POWERSOURCE_ZDDM=data.ZDDM,
                    data.POWERSOURCE_EFFECT_TIME=data.QLQSSJ,//权利开始时间
                    data.POWERSOURCE_CLOSE_TIME1=data.QLJSSJ,//权利结束时间
                    data.POWERSOURCE_CLOSE_TIME2=data.QLJSSJ,//权利结束时间
                    data.ZD_BM=data.ZDDM,
                    data.ZD_QLLX=data.ZDQLLX,//宗地权利类型
                    data.ZD_QLSDFS=data.QLSDFS,
                    data.ZD_ZL=data.TDZL,
                    data.ZD_MJ=data.ZDMJ,
                    data.ZD_TDYT=data.TDYT,
                    data.ZD_YTSM=data.TDYTSM,
                    data.ZD_QLXZ=data.QLXZ,//权利性质
                    data.ZD_LEVEL=data.DJ,
                    data.ZD_RJL=data.RJL,
                    data.ZD_JZXG=data.JZXG,
                    data.ZD_JZMD=data.JZMD,
                    data.ZD_E=data.TD_SZ_D,
                    data.ZD_W=data.TD_SZ_X,
                    data.ZD_N=data.TD_SZ_B,
                    data.ZD_S=data.TD_SZ_N,

                    data.BDC_QLKSSJ=DateConvertFun(data.QLQSSJ),
                    data.BDC_QLJSSJ1=DateConvertFun(data.QLJSSJ1),
                    data.BDC_QLJSSJ2=DateConvertFun(data.QLJSSJ2),


                    data.GYTD_BEGIN_TIME=DateConvertFun(data.QLQSSJ),
                    data.GYTD_END_TIME1=DateConvertFun(data.QLJSSJ1),
                    data.GYTD_END_TIME2=DateConvertFun(data.QLJSSJ2),

                    data.GYTD_GYFS=data.GYFS,
                    data.GYTD_QDJG=data.JG,
                    data.GYTD_SYQMJ=data.SYQMJ,
                    data.GYTD_QSZT=data.QSZT,
                    data.FW_ZL=data.FDZL,
                    data.FW_ZH=data.ZH,
                    data.FW_SZC=data.SZC,
                    data.FW_HH=data.HH,
                    data.FW_ZCS=data.ZCS,
                    data.FW_GHYT=data.FW_GHYT,
                    data.FW_YTSM=data.GHYTSM,
                    data.FW_XZ=data.FWXZ,
                    data.FW_FWJG=data.FWJG,//房屋结构
                    data.FW_JYJG=data.JYJG,//交易价格
                    data.FW_DYDYTDMJ=data.DYTDMJ,
                    data.FW_FTTDMJ=data.FTTDMJ,
                    data.FW_JZMJ=data.JZMJ,
                    data.FW_GYQK=data.GYFS,//房屋共有情况
                    data.FW_ZYJZMJ=data.ZYJZMJ,
                    data.FW_FTJZMJ=data.ZYJZMJ,
                    data.FW_QLLX=data.FW_QLLX,
                    data.POWERSOURCE_DYH=data.BDCDYH,
                    data.BDC_BDCDYH=data.BDCDYH,
                    data.POWERSOURCE_QSWH=data.BDCQZH,//证书文号（权属来源）
                    data.POWERSOURCEIDNUM=data.BDCQZH,
                    data.POWERSOURCE_QSLYLX="3",//证书文号（权属来源）
                    data.BDCDYH=data.BDCDYH,//不动产单元号
                    data.ZDDM=data.ZDDM,//宗地代码
                    data.BDC_ZDDM = data.ZDDM,
                    data.FWBM=data.FWBM,//房屋编码
                    data.YWH=data.YWH,//业务号
                    data.ZXYWH=data.ZXYWH,//注销业务号
                    data.GLH=data.GLH,//关联号
                    data.ZD_RJL=data.RJL
                FlowUtil.initFormFieldValue(data,"T_BDCQLC_ZJSYSCDJ_FORM");
                formatDic('POWERSOURCEIDTYPE', data.QSZT); //权属状态
                formatDic('BDC_QLRZJLX', data.ZJLX); //证件类型
                formatDic('ZD_QLLX', data.FW_QLLX); //权利类型
                formatDic('FW_QLLX', data.FW_QLLX); //权利类型
                formatDic('ZD_LEVEL', data.DJ); //等级
                formatDic("ZD_TZM", data.ZDTZM); //宗地特征码
                $("#ZD_QLXZ").combobox('select', data.QLXZ); //权利性质
                $('#ZD_QLXZ').combobox('setValue', data.QLXZ);
                formatDic('GYTD_GYFS', data.GYFS);
                formatDic('POWERSOURCENATURE', '不动产权证书');
                art.dialog.removeData("bdcdaxxcxInfo","PowerSourceInfoTrid");
            }
        }
    }, false);
}

/*获取json*/
function getPowerSourceTableTrMx(info) {
    $("input[name='POWERSOURCEIDNUM']").val(info.BDCQZH);
    $("input[name='BDC_BDCDYH']").val(info.BDCDYH);
    formatDic("POWERSOURCEIDTYPE",info.QSZT);
    $("input[name='QLR']").val(info.QLRMC);
    $("input[name='BDC_ZDDM']").val(info.ZDDM);
    formatDic('BDC_QLRZJLX', info.ZJLX);
    $("input[name='BDC_QLRZJHM']").val(info.ZJHM);
    var powerSourceTableTrMx = {};
    getTableJson(powerSourceTableTrMx, 'PowerSourceInfo');
    return JSON.stringify(powerSourceTableTrMx)
}

// function saveQslyXx() {
//     var POWERSOURCEINFO_JSON = $("input[name='POWERSOURCEINFO_JSON']").val();
//     var json = [];
//     if (POWERSOURCEINFO_JSON) {
//         json = JSON.parse(POWERSOURCEINFO_JSON);
//     }
//     var variables = {};
//     $("#PowerSourceInfo input").each(function () {
//         var name = $(this).attr('name')
//         var value = $(this).val();
//         if (name != 'PowerSourceInfoTrid' && name != 'bdcdaxxcx') {
//             variables[name] = value;
//         }
//     });
//
//     $("#PowerSourceInfo select").each(function () {
//         var name = $(this).attr('name')
//         var value = $(this).val();
//         variables[name] = value;
//     });
//     json.push(variables);
//     $("input[name='POWERSOURCEINFO_JSON']").val(JSON.stringify(json));
// }


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

function notPrint() {
    art.dialog({
        content : "当前环节不能执行该操作",
        icon : "warning",
        ok : true
    });
}

/**返回时间格式YYYY-MM-DD*/
function DateConvertFun(str){
    var time = "";
    if(str){
        /*var result=str.match(/\d+/g);
        var year = result[0];

        var day = result[2];
        time = year +"-";
        if(result[1]){
            var month = result[1];
            if(parseInt(month) > 9){
                time = time + month +"-";
            }else {
                time = time + "0"+ month + "-";
            }
        }
        if(result[2]){
            var day = result[2];
            if(parseInt(day) > 9){
                time = time + day;
            }else {
                time = time + "0"+ day;
            }
        }*/
        time=str;
    }
    return time;
}
//不动产档案信息查看是回填
function loadBdcdaxxcx(o) {
    var bdcdaxxcx =$(o).closest("tr").find("[name='bdcdaxxcx']").val();
    var bdcdaxxJson = JSON.parse(bdcdaxxcx);
    var data = {};
    data.POWERSOURCENATURE = 3; // 权属来源类型：
    data.POWERSOURCEIDNUM = bdcdaxxJson.BDCQZH; //权属文号
    data.BDC_BDCDYH = bdcdaxxJson.BDCDYH; //不动产单元号
    data.QLR = bdcdaxxJson.QLRMC; //权利人名称
    data.BDC_ZDDM = bdcdaxxJson.ZDDM; //宗地代码
    formatDic('POWERSOURCEIDTYPE', bdcdaxxJson.QSZT); //权属状态
    formatDic('BDC_QLRZJLX', bdcdaxxJson.ZJLX); //证件类型
    data.BDC_QLRZJHM = bdcdaxxJson.ZJHM; //证件号码
    initFormFieldValue(data, "PowerSourceInfo_1");
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
function setQSLYLX(value){
    if(value == "3"){
        $("#bdcdacxButton").show();
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
        $("#bdcdacxButton").hide();
        $("#addOrSaveButton").show();
        $("#qslyxx input").each(function(index) {
            $(this).attr("disabled", false);
        });
        $("#qslyxx select").each(function(index) {
            $(this).attr("disabled", false);
        });
    }
}

function formatDic(name,value) {
    $("select[name='"+name+"']").find("option").each(function () {
        if ($(this).text() == value) {
            $(this).attr("selected", "selected");
        }
    });
}
/*************JS需要公共代码结束***************/