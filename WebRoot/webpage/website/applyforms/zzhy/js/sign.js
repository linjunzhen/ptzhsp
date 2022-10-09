$(function() {
    //获取流程信息对象JSON
    var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
    if(EFLOW_FLOWOBJ) {
        //将其转换成JSON对象
        var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
        //初始化表单字段值
        if (eflowObj.busRecord) {
            //邮寄公章信息
            if (eflowObj.busRecord.ISEMAIL == 1) {
                $("#emailInfo").attr("style", "");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "1");
            } else {
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY", "-1");
            }
        }else{
            //面签信息转换
            var IS_ACCOUNT_OPEN_ITEM = $("input[name='IS_ACCOUNT_OPEN_ITEM']").val();
            if('1'==IS_ACCOUNT_OPEN_ITEM){
                $("input[name='IS_ACCOUNT_OPEN']").attr("checked","checked");
                $("#bankInfo").attr("style","");
                $("input[name='BANK_TYPE']").attr("disabled",false);
                $("input[name='CONTROLLER']").attr("disabled",false);
            }
            var ISEMAIL_ITEM = $("input[name='ISEMAIL_ITEM']").val();
            if("1"==ISEMAIL_ITEM){
                $("input[name='ISEMAIL']").attr("checked","checked");
                $("#emailInfo").attr("style","");
                AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","1");
            }
        }
    }
    //邮寄公章信息
    $("input[name='ISEMAIL']").click(function(){
        var Value = $(this).val();
        if(Value=='1'){
            $("#emailInfo").attr("style","");
            AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","1");
        }else{
            $("#emailInfo").attr("style","display:none;");
            AppUtil.changeRequireStatus("EMS_RECEIVER;EMS_PHONE;EMS_ADDRESS;EMS_CITY","-1");
        }
    });
});

/**
 *回填信息
 * @param val
 * @param name
 */
function setInfoMsg(val,name){
    if(val.indexOf('SHAREHOLDER')>-1){
        v2=val.replace("SHAREHOLDER","");
        $("input[name='" + name + "_NAME" + "']").val($("input[name='" + val + "_NAME" + "']").val());
        $("input[name='" + name + "_MOBILE" + "']").val($("input[name='"+v2+"CONTACT_WAY']").val());
        $("input[name='" + name + "_PHONE" + "']").val($("input[name='"+v2+"CONTACT_WAY']").val());
        $("select[name='" + name + "_IDTYPE" + "']").val($("select[name='"+v2+"LICENCE_TYPE']").val());
        $("input[name='" + name + "_IDNO" + "']").val($("input[name='"+v2+"LICENCE_NO']").val());
        var SSSBLX = $("input[name='SSSBLX']").val();
        var idType=$("select[name='" + name + "_IDTYPE" + "']").val();
        if (SSSBLX == '1') {
            setZjValidate(idType,  name + "_IDNO");
        }
    }else if(val!="") {
        $("input[name='" + name + "_NAME" + "']").val($("input[name='" + val + "_NAME" + "']").val());
        $("input[name='" + name + "_OPERRATOR" + "']").val($("input[name='" + val + "_NAME" + "']").val());
        $("input[name='" + name + "_MOBILE" + "']").val($("input[name='" + val + "_MOBILE" + "']").val());
        $("input[name='" + name + "_PHONE" + "']").val($("input[name='" + val + "_MOBILE" + "']").val());
        $("input[name='" + name + "_FIXEDLINE" + "']").val($("input[name='" + val + "_FIXEDLINE" + "']").val());
        $("select[name='" + name + "_IDTYPE" + "']").val($("select[name='" + val + "_IDTYPE" + "']").val());
        $("input[name='" + name + "_IDNO" + "']").val($("input[name='" + val + "_IDNO" + "']").val());
        var SSSBLX = $("input[name='SSSBLX']").val();
        var idType=$("select[name='" + name + "_IDTYPE" + "']").val();
        if (SSSBLX == '1') {
            setZjValidate(idType,  name + "_IDNO");
        }
    }
}