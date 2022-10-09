function selectChildIndustryView() {
    var insustryId =$("input[name='MAIN_INDUSTRY_ID']").val();
    if (insustryId) {
        $.dialog.open("industryController/selectChildIndustryView.do?entityId=" + insustryId, {
            title : "子行业信息",
            width : "80%",
            lock : true,
            resize : false,
            height : "80%",
            close: function () {
                var selectInfo = art.dialog.data("selectInfo");
                if(selectInfo){
                    $("input[name='CHILD_INDUSTRY_NAME']").val(selectInfo.industryName);
                    $("input[name='CHILD_INDUSTRY_ID']").val(selectInfo.industryId);
                    art.dialog.removeData("selectInfo");
                }
                setCompanyNameOfMp();
            }
        }, false);
    }else{
        selectMainIndustry();
    }
}

//选择主经营范围
function selectMainIndustry(){
    var sssblx=$("input[name='SSSBLX']").val();
    if("1"==sssblx){
        $.dialog.open("webSiteController/view.do?navTarget=website/hd/selectMainIndustry", {
            title : "请选择主行业",
            width:"80%",
            esc: false,//取消esc键
            lock: true,
            resize:false,
            height:"80%",
            close: function () {
                var selectInfo = art.dialog.data("mainIndustry");
                if(selectInfo){
                    $("input[name='MAIN_INDUSTRY_ID']").val(selectInfo.industryId);
                    art.dialog.removeData("mainIndustry");
                }
            }
        }, false);
    }
}

function selectRegisterAddr() {
        $.dialog.open("exeDataController/selectRegisterAddrView.do?", {
            title : "选择注册地址",
            width : "80%",
            lock : true,
            resize : false,
            height : "80%",
            close: function () {
                var selectInfo = art.dialog.data("selectInfo");
                if(selectInfo){
                    $("input[name='REGISTER_ADDR']").val(selectInfo.ALL_FULL_ADDR);
                    setLocalArea(selectInfo.SXZJD);
                    art.dialog.removeData("selectInfo");
                }
            }
        }, false);
}


function showSelectMainBussiness() {
    var insustryId =$("input[name='CHILD_INDUSTRY_ID']").val();
    if (insustryId) {
        $.dialog.open("industryController.do?selectMainBussinessView&entityId=" + insustryId, {
            title : "主行业信息",
            width : "80%",
            lock : true,
            resize : false,
            height : "80%",
            close: function () {
                var selectInfo = art.dialog.data("selectInfo");
                if(selectInfo){
                    $("input[name='MAIN_BUSSINESS_NAME']").val(selectInfo.BUSSCOPE_NAME);
                    $("input[name='MAIN_BUSSINESS_ID']").val(selectInfo.MAIN_BUSSINESS_ID);
                    $("input[name='DL_NAME']").val(selectInfo.DL_NAME);
                    $("input[name='ML_NAME']").val(selectInfo.ML_NAME);
                    $("input[name='ZL_NAME']").val(selectInfo.ZL_NAME);
                    $("input[name='XL_NAME']").val(selectInfo.XL_NAME);
                    art.dialog.removeData("selectInfo");
                }
            }
        }, false);
    }else{
        art.dialog({
            content: "请先选择子行业信息!",
            icon:"warning",
            ok: true
        });
    }
}



/**
 * 经营范围选择器
 **/
function showSelectJyfwOfMp(){
    var MAIN_BUSSINESS_ID=$("input[name='MAIN_BUSSINESS_ID']").val();
    if(MAIN_BUSSINESS_ID==''){
        art.dialog({
            content: "请先选择主经营范围!",
            icon:"warning",
            ok: true
        });
    }else{
        var ids = $("input[name='BUSSINESS_SCOPE_ID']").val();
        if(ids.indexOf(MAIN_BUSSINESS_ID)<=-1){
            ids=MAIN_BUSSINESS_ID+","+ids;
        }
        var defId=$("input[name='FLOW_DEFID']").val();
        parent.$.dialog.open("domesticControllerController/selectorNewOfMp.do?allowCount=30&noAuth=true&ids="
            + ids+"&mainBussinessId="+MAIN_BUSSINESS_ID, {
            title : "经营范围选择器",
            width : "850px",
            lock : true,
            resize : false,
            height : "500px",
            close : function() {
                var selectBusScopeInfo = art.dialog.data("selectBusScopeInfo");
                if (selectBusScopeInfo && selectBusScopeInfo.ids) {
                    $("[name='BUSSINESS_SCOPE_ID']").val(selectBusScopeInfo.ids);
                    $("[name='INVEST_INDUSTRY_ID']").val(selectBusScopeInfo.ids);
                    $("[name='BUSSINESS_SCOPE']").val(selectBusScopeInfo.busscopeNames);
                    $("[name='INVEST_INDUSTRY']").val(selectBusScopeInfo.induNames);
                    $("[name='INDUSTRY_CODE']").val(selectBusScopeInfo.induCodes);

                    var chk = $("input[name='IS_ACCOUNT_OPEN']:checked").val();
                    if(chk=='1'){
                        $("#businessScope").val(selectBusScopeInfo.induNames);
                        $("#INDUSTRY_CATEGORY_CDOE").val(selectBusScopeInfo.induCodes);
                    }
                    $("[name='INDUSTRY_CATEGORY']").val(selectBusScopeInfo.induCodes);

                    art.dialog.removeData("selectBusScopeInfo");
                }
            }
        }, false);
    }
};


/**
 * 添加股东信息
 */
function addGdxxDivMp(deleKey){
    var companyTypeCode=$("input[name='COMPANY_TYPECODE']").val();
    $.post("jointVentureController/refreshGdxxDivMp.do?companyTypeCode="+companyTypeCode+"&deleKey="+deleKey,{
    }, function(responseText, status, xhr) {
        $("#gdxxDiv").append(responseText);
        var CURRENCY = $("[name='CURRENCY']").val();
        setCurrency(CURRENCY);
        setcdzfs();
        setDefaultGdxx();
    });
}

$(function () {
    var SSSBLX = $("input[name='SSSBLX']").val();
    if(SSSBLX=='1'){
        setOrgType();
        setDefaultGdxx();
        setDefaultPerson();
        $("[name='RESIDENCE_REGISTER_WAYOFGET']").attr("disabled","disabled");
        $("[name='ARMY_REGISTER_HOURSE']").attr("disabled","disabled");
        var IS_BUSSINESS_ADDR=$("input[name='IS_OTHER_PLACE']:checked").val();
        if(IS_BUSSINESS_ADDR=='1'){
            $("#bussinessAddrTable").attr("style","");
        }
    }
});
function  setOrgType(){
    var companyTypeCode= $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
    if("yxhhqy"==companyTypeCode){
       $("[name='ORG_TYPE']").val("合伙企业（有限合伙）");
        $("[name='ORG_TYPE']").attr("disabled","disabled");
    }
    if("pthhqy"==companyTypeCode){
        $("[name='ORG_TYPE']").val("合伙企业（普通合伙）");
        $("[name='ORG_TYPE']").attr("disabled","disabled");
    }

}


function setDefaultPerson(){

    //人员信息证件类型默认为身份证
    $("[name='LEGAL_IDTYPE']").attr("disabled", "disabled");
    $("[name='LEGAL_IDTYPE']").val("SF");
    $("[name='OPERATOR_IDTYPE']").attr("disabled", "disabled");
    $("[name='OPERATOR_IDTYPE']").val("SF");
    $("[name='FINANCE_IDTYPE']").attr("disabled", "disabled");
    $("[name='FINANCE_IDTYPE']").val("SF");
    $("[name='TAXMAN_IDTYPE']").attr("disabled", "disabled");
    $("[name='TAXMAN_IDTYPE']").val("SF");
    $("[name='LIAISON_IDTYPE']").attr("disabled", "disabled");
    $("[name='LIAISON_IDTYPE']").val("SF");
    $("input[name='LEGAL_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");
    $("input[name='FINANCE_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");
    $("input[name='TAXMAN_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");
    $("input[name='LIAISON_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");
    $("input[name='OPERATOR_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");
}
/**
 *当值为val1时，设置为必填
 * @param val
 * @param inputName
 * @param otherValue
 */
function setRequired(val,inputName,val1){
    if (val===val1) {
        $("input[name="+inputName+"]").attr("disabled",false);
        $("input[name="+inputName+"]").addClass(" validate[required]");
        $("."+inputName+"_CLASS").attr("style","display:''");
    } else {
        $("input[name="+inputName+"]").attr("disabled",true);
        $("input[name="+inputName+"]").removeClass(" validate[required]");
        $("input[name="+inputName+"]").val('');
        $("."+inputName+"_CLASS").attr("style","display:none");
    }
}

//点击是否有生产经营地址
$("input[name='IS_BUSSINESS_ADDR']").on("click", function(event) {
    if("1"==this.value){
        $("#bussinessAddrTable").attr("style","");
        AppUtil.changeRequireStatus("BUSSINESS_SQUARE_ADDR;BUSINESS_PLACE;PLACE_OWNER;PLACE_TEL;RESIDENCE_WAYOFGET;ARMY_HOURSE;ARMYHOURSE_REMARKS","1");
    }else{
        $("#bussinessAddrTable").attr("style","display:none;");
        AppUtil.changeRequireStatus("BUSSINESS_SQUARE_ADDR;BUSINESS_PLACE;PLACE_OWNER;PLACE_TEL;RESIDENCE_WAYOFGET;ARMY_HOURSE;ARMYHOURSE_REMARKS","-1");
    }
});



$(function () {
    setInterval(function () {
        checkCompanyNameResult();
    }, 1000 * 60);
});
function checkCompanyName() {
        var AREA_NAME = $("input[name='AREA_NAME']").val();
        var WORD_NUM = $("input[name='WORD_NUM']").val();
        var CHILD_INDUSTRY_NAME = $("input[name='CHILD_INDUSTRY_NAME']").val();
        var ORG_TYPE = $("[name='ORG_TYPE']").val();
        if (WORD_NUM == '') {
            alert("字号不能为空");
            return;
        }
    if (CHILD_INDUSTRY_NAME == '') {
        alert("子行业不能为空");
        return;
    }
    if (ORG_TYPE == '') {
        alert("组织形式不能为空");
        return;
    }
       changeCheckCompanyNameText();
        $.post("exeDataController/pushCompanyName.do?", {
            AREA_NAME: AREA_NAME,
            WORD_NUM: WORD_NUM,
            CHILD_INDUSTRY_NAME: CHILD_INDUSTRY_NAME,
            ORG_TYPE: ORG_TYPE
        }, function (responseText, status, xhr) {
            var resultJson = $.parseJSON(responseText);
            if (resultJson.success) {
                art.dialog({
                    content: "名称正在查重中，请等待",
                    icon: "warning",
                    ok: true
                });
                $("input[name='IS_CHECKCOMPANYNAME']").val(1);
            }
        });
}

function changeCheckCompanyNameText() {
    $("input[name='COMPANYNAME_PASS']").val(0);
    $("input[name='WORD_NUM']").attr("readonly","readonly").addClass("inputBackgroundCcc");
    $("#checkCompanyNameId").removeAttr("onclick");
    var count = 5*60*1;
    var countdown = setInterval(CountDown, 1000);
    function CountDown() {
        if (count == 0) {
            $("#checkCompanyNameId").text("修改");
            $("#checkCompanyNameId").attr("onclick","changeTojiaoyan()");
            clearInterval(countdown);
        }else{
            $("#checkCompanyNameId").text("可"+count+"秒后修改");
        }
        count--;
    }
}

function  changeTojiaoyan() {
    $("#checkCompanyNameId").text("校验");
    $("#checkCompanyNameId").attr("onclick","checkCompanyName()");
    $("input[name='WORD_NUM']").removeAttr("readonly").removeClass("inputBackgroundCcc");
    $("input[name='COMPANYNAME_PASS']").val("0");
}


function checkCompanyNameResult() {
    var ischeck= $("input[name='IS_CHECKCOMPANYNAME']").val();
    if(ischeck=='1') {
        var AREA_NAME = $("input[name='AREA_NAME']").val();
        var WORD_NUM = $("input[name='WORD_NUM']").val();
        var CHILD_INDUSTRY_NAME = $("input[name='CHILD_INDUSTRY_NAME']").val();
        var ORG_TYPE = $("[name='ORG_TYPE']").val();
        if (WORD_NUM !== '') {
            $.post("exeDataController/getCompanyNameResult.do?", {
                AREA_NAME: AREA_NAME,
                WORD_NUM: WORD_NUM,
                CHILD_INDUSTRY_NAME: CHILD_INDUSTRY_NAME,
                ORG_TYPE: ORG_TYPE
            }, function (responseText, status, xhr) {
                var resultJson = $.parseJSON(responseText);
                if (resultJson.code == '-1') {
                    art.dialog({
                        content: resultJson.msg,
                        icon: "warning",
                        ok: true
                    });
                    $("input[name='IS_CHECKCOMPANYNAME']").val(0);
                }
                if (resultJson.code == '1') {
                    art.dialog({
                        content:  resultJson.msg,
                        icon: "succeed",
                        ok: true
                    });
                    $("input[name='IS_CHECKCOMPANYNAME']").val(0);
                    $("input[name='COMPANYNAME_PASS']").val(1);
                }
            });
        }
    }
}





function isGtCurDate(val){
    var date = new Date();
    var valDate=new Date(val);
    if(valDate<=date){
        art.dialog({
            content:"请确认该时间前是否能全部到资",
            icon:"warning",
            ok: true
        });
    }
}


//秒批股东初始化参数
function setDefaultGdxx() {
    var SSSBLX = $("input[name='SSSBLX']").val();
    var companyTypeCode= $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
    if(SSSBLX=='1') {

        $("[name$='SHAREHOLDER_COMPANYCOUNTRY']").attr("disabled", "disabled");
        $("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val("中国");
        $("[name$='EVALUATE_WAY']").attr("disabled", "disabled");
        $("[name$='EVALUATE_WAY']").val("无");

        $("[name$='PAIDIN_QUOTA']").attr("disabled", "disabled");
        $("[name$='PAIDIN_QUOTA']").val("0");
        $("[name$='PAIDIN_DATE']").attr("disabled", "disabled");

        if("yxhhqy"==companyTypeCode){
            $("#gdxxDiv>div").each(function(j){
                if(j==0){
                    $(this).find("[name$='DUTY_MODE']").attr("disabled", "disabled");
                    $(this).find("[name$='DUTY_MODE']").val("无限责任");
                }
                if(j==1){
                    $(this).find("[name$='DUTY_MODE']").attr("disabled", "disabled");
                    $(this).find("[name$='DUTY_MODE']").val("有限责任");
                    //只能是普通合伙人担任执行事务合伙人
                    var COMPANY_SETNATURE = $("#COMPANY_FORM input[name='COMPANY_SETNATURE']").val();
                    if(COMPANY_SETNATURE=="yxhhqy") {
                        $(this).find("select[name$='IS_PARTNERSHIP']").attr("disabled", "disabled").css("background-color", "#EEEEEE;");
                        $(this).find("select[name$='IS_PARTNERSHIP']").val("0");
                    }
                }
            });
        }

        // $("[name$='SHAREHOLDER_COMPANYCOUNTRY']").attr("disabled", "disabled");
        // $("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val("中国");
        // $("[name$='PAIDCAPITAL']").attr("disabled", "disabled");
        // $("[name$='PAIDCAPITAL']").val("0");

        //以下类型默认两个股东
        var num = 0;
        $("[name$='SHAREHOLDER_NAME']").each(function(i){
            num++;
        });
        if(num<2){
            addGdxxDivMp('0');
        }
    }
}

function  setGdType(val,shareholderType,licenseType,licenseNo,shareHolderName){
    var SSSBLX = $("input[name='SSSBLX']").val();
    if(SSSBLX=='1') {
            if(val=='zrr'){
                $("[name='"+licenseType+"']").attr("disabled", "disabled");
                $("[name='"+licenseType+"']").val("SF");
                $("[name='"+licenseNo+"']").addClass(",custom[vidcard],custom[isEighteen]");
                $("[name='"+shareHolderName+"']").removeClass(" ,custom[checkHhShareHolderName]");
            }else{
                $("[name='"+licenseType+"']").attr("disabled", "disabled");
                $("[name='"+licenseType+"']").val("YYZZ");
                $("[name='"+licenseNo+"']").removeClass(",custom[vidcard],custom[isEighteen]");
                $("[name='"+shareHolderName+"']").addClass(" ,custom[checkHhShareHolderName]");
            }
    }
}

/**
 * 秒批js提交事件
 */
function validateSubmitFunOfMp() {
    var flag = true;
    var SSSBLX = $("input[name='SSSBLX']").val();
    var COMPANY_TYPECODE = $("input[name='COMPANY_TYPECODE']").val();
    if (SSSBLX == '1') {
        var companynamePass = $("input[name='COMPANYNAME_PASS']").val();
        if (companynamePass == '0') {
            art.dialog({
                content: "名称未校验或校验未通过，请检验公司名称！",
                icon: "warning",
                ok: true
            });
            return false;
        }


        //股东数量验证
        var num = 0;
        $("select[name$='SHAREHOLDER_TYPE']").each(function (i) {
            num++;
        });
        //自然人投资或控股  其他有限责任公司  2≤股东数量≤ 50
        if (COMPANY_TYPECODE == 'yxhhqy' ) {
            if (num > 50) {
                art.dialog({
                    content: "股东数量不能大于50!",
                    icon: "warning",
                    ok: true
                });
                return false;
            } else if (num < 2) {
                art.dialog({
                    content: "股东数量不能小于2!",
                    icon: "warning",
                    ok: true
                });
                return false;
            }

        }
        //执行事务合伙人
        var ispartnership = 0;
        $("select[name$='IS_PARTNERSHIP']").each(function(i){
            var is = $(this).val();
            if(is=="1"){
                ispartnership++;
            }
        });
        if(ispartnership!=1 ){
            art.dialog({
                content : "执行事务合伙人有且仅有一个！",
                icon : "warning",
                ok : true
            });
            return false;
        }

        var BUSSINESS_YEARS= $("input[name='BUSSINESS_YEARS']").val();//经营期限
        var PAYMENT_PERIOD= $("input[name='PAYMENT_PERIOD']").val();//出资全部缴付到位期限
        if(BUSSINESS_YEARS != null && BUSSINESS_YEARS != ''
            && PAYMENT_PERIOD != null && PAYMENT_PERIOD != ''){
            var myDate = new Date();
            var fullYear = myDate.getFullYear();
            var year = PAYMENT_PERIOD.substring(0,4);
            if((Number(fullYear)+Number(BUSSINESS_YEARS))<Number(year)){
                art.dialog({
                    content : "出资全部缴付到位期限必须小于等于当前时间加上经营期限",
                    icon : "warning",
                    ok : true
                });
            }
        }
    }
    return flag;
}

function setPartnershipByDutyMode(value,name,ispartship){
    if(ispartship!=''){
        if (value === "有限责任") {
            $("select[name='"+name+"']").attr("disabled","disabled").css("background-color","#EEEEEE;");
            $("select[name='"+name+"']").val("0");
        } else {
            $("select[name='"+name+"']").removeAttr("disabled","true").css("background-color","#fff;");
            $("select[name='"+name+"']").val(ispartship);
        }
    }
}
    function setCompanyNameOfMp() {
        var areaName = $("input[name='AREA_NAME']").val();
        var wordNum = $("input[name='WORD_NUM']").val();
        var chileIndustryName = $("input[name='CHILD_INDUSTRY_NAME']").val();
        var orgType = $("[name='ORG_TYPE']").val();
        var companyName = areaName + wordNum + chileIndustryName + orgType;
        $("input[name='COMPANY_NAME']").val(companyName);
    }

    function alertInvesment(val) {
        var SSSBLX = $("input[name='SSSBLX']").val();
        if (SSSBLX == '1') {
            if (val > 10000) {
                art.dialog({
                    content: "请确认注册资本,注册资本单位为万元",
                    icon: "warning",
                    ok: true
                });
            }
        }
    }