/*
* 校验权属来源和权利人信息字段
* id : 表单id
* reqiredStr : class中带有的必填标识 required
*
* */
function validatePowerParams(id,requireStr) {
    var flag = true
    $("#" + id + " input , #" + id + " select").each(function (index) {
        if ($(this).attr("class") && $(this).attr("class").indexOf(requireStr) != -1) {
            var val = $(this).val();
            var param = $(this).parent().prev().text();
            if (!(val && val.trim() != '')) {
                art.dialog({
                    content : "请填写" + param.replace("*","").replace(":","").replace("：",""),
                    icon : "warning",
                    ok: true
                })
                flag = false;
                return false;
            }
        }
    });
    return flag;
}

function formatDic(name,value) {
    $("select[name='"+name+"']").find("option").each(function () {
        if ($(this).text() == value) {
            $(this).attr("selected", "selected");
        }
    });
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
        time=str;
    }
    return time;
}

/*隐藏登记信息*/
function hideDjxx(name) {
    $("#djjfxx_" + name).attr("style","display:none;");
    $("#djfzxx_" + name).attr("style","display:none;");
    $("#djgdxx_" + name).attr("style","display:none;");
}

/*审批表无法打印*/
function notPrintSpb() {
    $("#spbdf").attr("onclick","notPrint();")
    $("#spbsf").attr("onclick","notPrint();")
}

/**
 * 常用意见选择器
 *  businessName 类型名称
 *  inputName  回填字典名
 *  tagName  标签  input  textarea
 */
function bdcCyyjmbSelector(businessName,inputName,tagName) {
    parent.$.dialog.open("bdcDyqscdjController.do?cyyjmbSelector&businessName=" + businessName, {
        title: "常用意见选择器",
        width: "800px",
        lock: true,
        resize: false,
        height: "500px",
        close: function () {
            var selectCyyjmbInfo = art.dialog.data("selectCyyjmbInfo");
            if (selectCyyjmbInfo && selectCyyjmbInfo.opnionContent) {
                $(tagName + "[name='" + inputName + "']").val(selectCyyjmbInfo.opnionContent);
                art.dialog.removeData("selectCyyjmbInfo");
            }
        }
    }, false);
}

/*
* 校验表单上无法在class添加的字段，主要为easyui字段,
* fieldArr  [{"fieldText":"抵押权人","fieldName","DY_DYQR"}]
* */
function verificaSpeField(fieldArr) {
    var obj = {};
    obj["flag"] = true;
    for (let i = 0; i < fieldArr.length; i++) {
        var val = $("input[name='"+fieldArr[i].fieldName+"']").val();
        if (!(val && val != "")) {
            obj["flag"] = false;
            obj["msg"] = "请填写" + fieldArr[i].fieldText
            break;
        }
    }
    return obj;
}