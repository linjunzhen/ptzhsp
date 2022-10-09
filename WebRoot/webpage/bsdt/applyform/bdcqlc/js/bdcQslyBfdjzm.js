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
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].BDCDYH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].QLRMC + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].ZH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].HH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].BDCQZH + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].QSZT + '</td>';
                    html += '<td style="text-align: center;">' + bdcdaxxcxInfo[i].QLQSSJ + '</td>';
                    html += '<td style="text-align: center;">';
                    html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdcdaxxcx(this);">查 看</a>';
                    html += '</tr>';
                    $("#PowerSourceTable").append(html);
                    $("#bdcdaxxcxTr_" + bdcdaxxcxTr + " input[name='bdcdaxxcx']").val(JSON.stringify(bdcdaxxcxInfo[i]));
                    html="";
                    bdcdaxxcxTr++;
                }
                art.dialog.removeData("bdcdaxxcxInfo");
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


/**返回时间格式YYYY-MM-DD*/
function DateConvertFun(str){
    var time = "";
    if(str){
        time=str;
    }
    return time;
}
//不动产档案信息查看是回填
function loadBdcdaxxcx(o) {
    var bdcdaxxcx =$(o).closest("tr").find("[name='bdcdaxxcx']").val();
    //抵押不动产类型为土地，则坐落取土地坐落，为其他则使用房地坐落
    var bdcdaxxcxInfos = JSON.parse(bdcdaxxcx);
    //不动产单元号
    $('input[name="BDC_BDCDYH"]').val(bdcdaxxcxInfos.BDCDYH);
    $('input[name="POWERSOURCEIDNUM"]').val(bdcdaxxcxInfos.BDCQZH);
    formatDic("POWERSOURCEIDTYPE", bdcdaxxcxInfos.QSZT);
    $('input[name="QLR"]').val(bdcdaxxcxInfos.QLRMC);
    $('input[name="BDC_ZDDM"]').val(bdcdaxxcxInfos.ZDDM);
    formatDic("BDC_QLRZJLX", bdcdaxxcxInfos.ZJLX);
    $("input[name='BDC_QLRZJHM']").val(bdcdaxxcxInfos.ZJHM);
    //权利开始时间
    $('input[name="BDC_QLKSSJ"]').val(bdcdaxxcxInfos.BDC_QLKSSJ);
    var trId = $(o).closest("tr").attr("id");
    $(".bdcdaxxcxTr").removeClass("bdcdaxxcxTrRed");
    $(o).closest("tr").addClass("bdcdaxxcxTrRed");
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
/*************JS需要公共代码结束***************/