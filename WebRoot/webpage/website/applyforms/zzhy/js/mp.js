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


function setlegalProducemode(val) {
    var SSSBLX = $("input[name='SSSBLX']").val();
    var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司类型编码
    var companyTypeCode= $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
    if(SSSBLX=='1'){
        if(companyTypeCode=='zrrdz'&&companySetNature=='01'){
            if(val=='30'){
                $("[name='LEGAL_PRODUCEMODE']").val("01");
            }else{
                $("[name='LEGAL_PRODUCEMODE']").val("03");
            }
            $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
        }else if(companyTypeCode=='zrrdz'&&companySetNature=='07'){
            if(val=='01'){
                $("[name='LEGAL_PRODUCEMODE']").val("02");
            }else{
                $("[name='LEGAL_PRODUCEMODE']").val("03");
            }
            $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
        }else if(companyTypeCode=='zrrkg'&&companySetNature=='03'){
            if(val=='30'){
                $("[name='LEGAL_PRODUCEMODE']").val("02");
            }else{
                $("[name='LEGAL_PRODUCEMODE']").val("03");
            }
            $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
        }else if(companyTypeCode=='zrrkg'&&companySetNature=='02'){
            if(val=='01'){
                $("[name='LEGAL_PRODUCEMODE']").val("02");
            }else{
                $("[name='LEGAL_PRODUCEMODE']").val("03");
            }
            $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
        }else if(companyTypeCode=='zrrkg'&&companySetNature=='04'){
            if(val=='01'){
                $("[name='LEGAL_PRODUCEMODE']").val("02");
            }else{
                $("[name='LEGAL_PRODUCEMODE']").val("03");
            }
            $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
        }else if(companyTypeCode=='qtyxzrgs'){
            if(val=='20'){
                $("[name='LEGAL_PRODUCEMODE']").val("03");
            }else{
                $("[name='LEGAL_PRODUCEMODE']").val("02");
            }
            $("[name='LEGAL_PRODUCEMODE']").attr("disabled","disabled");
        }
    }

}
$(function () {
    var SSSBLX = $("input[name='SSSBLX']").val();
    if(SSSBLX=='1'){
        var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司类型编码
        var companyTypeCode= $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
        //监事产生方式
        if(companyTypeCode=='zrrdz'){
            $("[name$='SUPERVISOR_GENERATION_MODE']").val('01');
            $("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled","disabled");
        }else if(companyTypeCode=='zrrkg'||companyTypeCode=='qtyxzrgs'){
            $("[name$='SUPERVISOR_GENERATION_MODE']").val('02');
            $("[name$='SUPERVISOR_GENERATION_MODE']").attr("disabled","disabled");
        }
        setDefaultPersonNum();
        setDefaultPersonInfo();
        setDefaultGdxx();
        $("[name='RESIDENCE_REGISTER_WAYOFGET']").attr("disabled","disabled");
        $("[name='ARMY_REGISTER_HOURSE']").attr("disabled","disabled");
    }
    var IS_BUSSINESS_ADDR=$("input[name='IS_BUSSINESS_ADDR']:checked").val();
    if(IS_BUSSINESS_ADDR=='1'){
        $("#bussinessAddrTable").attr("style","");
    }

});
//其他有限公司股东证件类型设置
function  setGdType(val,shareholderType,licenseType,licenseNo){
    var SSSBLX = $("input[name='SSSBLX']").val();
    var companyTypeCode= $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
    if(SSSBLX=='1') {
        if ("qtyxzrgs" == companyTypeCode ) {
            if(val=='qtjg'){
                alert("股东类型不能为其他组织机构");
                $("[name='"+shareholderType+"']").val("");
            }
            if(val=='zrr'){
                $("[name='"+licenseType+"']").attr("disabled", "disabled");
                $("[name='"+licenseType+"']").val("SF");
                $("[name='"+licenseNo+"']").addClass(",custom[vidcard],custom[isEighteen]");
            }
            if(val=='qy'){
                $("[name='"+licenseType+"']").attr("disabled", "disabled");
                $("[name='"+licenseType+"']").val("YYZZ");
                $("[name='"+licenseNo+"']").removeClass(",custom[vidcard],custom[isEighteen]");
            }

        }
    }
}

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



function setDefaultPersonNum() {
    var companySetNature = $("input[name='COMPANY_SETNATURE']").val();//公司类型编码
    //监事信息
    var jsNum = 0;
    var EFLOW_FLOWOBJ = $("#EFLOW_FLOWOBJ").val();
    if (EFLOW_FLOWOBJ) {
        //将其转换成JSON对象
        var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
        //初始化表单字段值
        if (!eflowObj.busRecord) {
            if (companySetNature == '04') {
                $("[name$='SUPERVISOR_COUNTRY']").each(function (i) {
                    jsNum++;
                });
                if (jsNum < 2) {
                    addJsxxDiv("0");
                    addJsxxDiv("0");
                }
            }

        }
    }

    if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07') {
        var dxNum = 0;
        $("[name$='DIRECTOR_IDTYPE']").each(function (i) {
            dxNum++;
        });
        if (dxNum < 2) {
            addDsxxDivMp("0");
            addDsxxDivMp("0");
        }
    }
}

//秒批股东初始化参数
function setDefaultGdxx() {
    var SSSBLX = $("input[name='SSSBLX']").val();
    var companyTypeCode= $("input[name='COMPANY_TYPECODE']").val();//公司类型编码
    if(SSSBLX=='1') {
        if("zrrdz"==companyTypeCode||"zrrkg"==companyTypeCode){
            $("#gdxxDiv>div").each(function(j){
                    $(this).find("[name$='SHAREHOLDER_TYPE']").attr("disabled", "disabled");
                    $(this).find("[name$='SHAREHOLDER_TYPE']").val("zrr");
                    $(this).find("[name$='LICENCE_TYPE']").attr("disabled", "disabled");
                    $(this).find("[name$='LICENCE_TYPE']").val("SF");
                    $(this).find("[name$='LICENCE_NO']").addClass(",custom[vidcard],custom[isEighteen]");
                    var LEGAL_PERSON=$(this).find("[name$='LEGAL_PERSON']").attr("name");
                    var LEGAL_IDNO_PERSON=$(this).find("[name$='LEGAL_IDNO_PERSON']").attr("name");
                    setGdlxValidate("zrr",LEGAL_PERSON);
                    setGdlxValidate("zrr",LEGAL_IDNO_PERSON);
            });
        }else if("frdz"==companyTypeCode){
            $("#gdxxDiv>div").each(function(j){
                $(this).find("[name$='SHAREHOLDER_TYPE']").attr("disabled", "disabled");
                $(this).find("[name$='SHAREHOLDER_TYPE']").val("qy");
                $(this).find("[name$='LICENCE_TYPE']").attr("disabled", "disabled");
                $(this).find("[name$='LICENCE_TYPE']").val("YYZZ");
                $(this).find("[name$='LICENCE_NO']").removeClass(",custom[vidcard],custom[isEighteen]");
                var LEGAL_PERSON=$(this).find("[name$='LEGAL_PERSON']").attr("name");
                var LEGAL_IDNO_PERSON=$(this).find("[name$='LEGAL_IDNO_PERSON']").attr("name");
                setGdlxValidate("qy",LEGAL_PERSON);
                setGdlxValidate("qy",LEGAL_IDNO_PERSON);
            });
        }else if("qtyxzrgs"==companyTypeCode){
            $("#gdxxDiv>div").each(function(j){
                if(j==0){
                $(this).find("[name$='SHAREHOLDER_TYPE']").attr("disabled", "disabled");
                $(this).find("[name$='SHAREHOLDER_TYPE']").val("qy");
                $(this).find("[name$='LICENCE_TYPE']").attr("disabled", "disabled");
                $(this).find("[name$='LICENCE_TYPE']").val("YYZZ");
                $(this).find("[name$='LICENCE_NO']").removeClass(",custom[vidcard],custom[isEighteen]");
                var LEGAL_PERSON=$(this).find("[name$='LEGAL_PERSON']").attr("name");
                var LEGAL_IDNO_PERSON=$(this).find("[name$='LEGAL_IDNO_PERSON']").attr("name");
                setGdlxValidate("qy",LEGAL_PERSON);
                setGdlxValidate("qy",LEGAL_IDNO_PERSON);
                }
            });
        }

        $("[name$='SHAREHOLDER_COMPANYCOUNTRY']").attr("disabled", "disabled");
        $("[name$='SHAREHOLDER_COMPANYCOUNTRY']").val("中国");


        $("[name$='PAIDCAPITAL']").attr("disabled", "disabled");
        $("[name$='PAIDCAPITAL']").val("0");

         //以下类型默认两个股东
        if("qtyxzrgs"==companyTypeCode||"zrrkg"==companyTypeCode){
            var num = 0;
            $("[name$='SHAREHOLDER_NAME']").each(function(i){
                num++;
            });
            if(num<2){
                addGdxxDivMp('0');
            }
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
function setDefaultPersonInfo(){
    var SSSBLX = $("input[name='SSSBLX']").val();
    if(SSSBLX=='1') {
        //人事国别默认为中国，证件类型默认为身份证
        $("[name$='DIRECTOR_COUNTRY']").attr("disabled", "disabled");
        $("[name$='SUPERVISOR_COUNTRY']").attr("disabled", "disabled");
        $("[name$='MANAGER_COUNTRY']").attr("disabled", "disabled");
        $("[name$='LEGAL_COMPANYCOUNTRY']").attr("disabled", "disabled");

        $("[name$='DIRECTOR_IDTYPE']").attr("disabled", "disabled");
        $("[name$='SUPERVISOR_IDTYPE']").attr("disabled", "disabled");
        $("[name$='MANAGER_IDTYPE']").attr("disabled", "disabled");

        $("[name$='DIRECTOR_IDTYPE']").val("SF");
        $("[name$='SUPERVISOR_IDTYPE']").val("SF");
        $("[name$='MANAGER_IDTYPE']").val("SF");

        $("[name$='DIRECTOR_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");
        $("[name$='SUPERVISOR_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");
        $("[name$='MANAGER_IDNO']").addClass(",custom[vidcard],custom[isEighteen]");


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


        //董事会固定三个董事，不能减少，只能增加。第一个是董事长，
        var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司设立性质编码
        if(companySetNature=='02'||companySetNature=='04'||companySetNature=='07'){
            $("#dsxxDiv>div").each(function(j){
                if(j==0){
                    $(this).find("[name$='DIRECTOR_JOB']").attr("disabled", "disabled");
                    $(this).find("[name$='DIRECTOR_JOB']").val("01");
                    $(this).find("[name$='DIRECTOR_GENERATION_MODE']").val("02");
                    $(this).find("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled", "disabled");
                    var DIRECTOR_APPOINTOR=$(this).find("[name$='DIRECTOR_APPOINTOR']").attr("name");
                    //setGenerationModeByDirector("03",DIRECTOR_GENERATION_MODE);
                    setDirectorJob("01",DIRECTOR_APPOINTOR);
                }else if(j>1){
                    $(this).find("[name$='DIRECTOR_JOB']").attr("disabled", "disabled");
                    $(this).find("[name$='DIRECTOR_JOB']").val("03");
                    $(this).find("[name$='DIRECTOR_GENERATION_MODE']").val("02");
                    $(this).find("[name$='DIRECTOR_GENERATION_MODE']").attr("disabled", "disabled");
                    var DIRECTOR_APPOINTOR=$(this).find("[name$='DIRECTOR_APPOINTOR']").attr("name");
                    //setGenerationModeByDirector("03",DIRECTOR_GENERATION_MODE);
                    setDirectorJob("03",DIRECTOR_APPOINTOR);
                }
            });
        }
         //监事会
        if(companySetNature=='04'){
            $("#jsxxDiv>div").each(function(j){
                if(j==0){
                    $(this).find("[name$='SUPERVISOR_JOB']").attr("disabled", "disabled");
                    $(this).find("[name$='SUPERVISOR_JOB']").val("11");
                    $(this).find("[name$='SUPERVISOR_APPOINTOR']").attr("disabled", "disabled");
                    $(this).find("[name$='SUPERVISOR_APPOINTOR']").val("监事会");
                }else if(j==1){
                    $(this).find("[name$='SUPERVISOR_JOB']").attr("disabled", "disabled");
                    $(this).find("[name$='SUPERVISOR_JOB']").val("10");
                    $(this).find("[name$='SUPERVISOR_APPOINTOR']").attr("disabled", "disabled");
                    $(this).find("[name$='SUPERVISOR_APPOINTOR']").val("职工代表大会");
                }else if(j==2){
                    $(this).find("[name$='SUPERVISOR_JOB']").attr("disabled", "disabled");
                    $(this).find("[name$='SUPERVISOR_JOB']").val("10");
                    $(this).find("[name$='SUPERVISOR_APPOINTOR']").attr("disabled", "disabled");
                    $(this).find("[name$='SUPERVISOR_APPOINTOR']").val("股东会");
                }
            });

        }
    }
}
function checkIndustryName(){
    var mainIndustryId=$("input[name='MAIN_INDUSTRY_ID']").val();
    var childIndustryName=$("input[name='CHILD_INDUSTRY_NAME']").val();
    var flag=true;
    if(mainIndustryId!=''){
        $.ajax({
            type: "POST",
            url: encodeURI("industryController/childIndustryForWebsite.do?Q_T.PARENT_ID_EQ="+mainIndustryId+"&Q_T.INDUSTRY_NAME_EQ="+childIndustryName),
            async: false,
            success: function (responseText) {
                if(responseText.total<1){
                    flag=false;
                }
            }
        });
    }
    return flag;
}

/**
 * 秒批js提交事件
 */
function validateSubmitFunOfMp() {
    var flag=true;
    var SSSBLX = $("input[name='SSSBLX']").val();
    var COMPANY_TYPECODE=$("input[name='COMPANY_TYPECODE']").val();
    if (SSSBLX == '1') {
        if(!checkIndustryName()){
            art.dialog({
                content:"子行业不规范，请重新选择子行业名称！",
                icon:"warning",
                ok: true
            });
            return false;
        }
        var companynamePass=$("input[name='COMPANYNAME_PASS']").val();
        if(companynamePass=='0'){
            art.dialog({
                content:"名称未校验或校验未通过，请检验公司名称！",
                icon:"warning",
                ok: true
            });
            return false;
        }
        var branchNum=0;
        $("#gdxxDiv div").each(function(i){
            var SHAREHOLDER_NAME = $(this).find("[name$='SHAREHOLDER_NAME']").val();//股东姓名/名称
            var SHAREHOLDER_TYPE = $(this).find("[name$='SHAREHOLDER_TYPE']").val();//股东类型
            if(SHAREHOLDER_TYPE=='qy'&&COMPANY_TYPECODE=='qtyxzrgs'){
                if (SHAREHOLDER_NAME.indexOf("分公司") > -1 || SHAREHOLDER_NAME.indexOf("分店") > -1 || SHAREHOLDER_NAME.indexOf("分行") > -1 || SHAREHOLDER_NAME.indexOf("支行") > -1  ) {
                    branchNum=branchNum+1;
                    var content= "请确认第"+branchNum+"个股东是否具备对外投资资格!";
                    art.dialog({
                        content:content,
                        icon:"warning",
                        ok: true
                    });
                    flag= false;
                    return false;
                }
            }
        });
        if(!flag) return flag;
        //股东数量验证
        var num = 0;
        $("select[name$='SHAREHOLDER_TYPE']").each(function(i){
            num++;
        });
        //自然人投资或控股  其他有限责任公司  2≤股东数量≤ 50
       if(COMPANY_TYPECODE=='zrrkg'||COMPANY_TYPECODE=='qtyxzrgs'){
           if(num>50){
               art.dialog({
                   content: "股东数量不能大于50!",
                   icon:"warning",
                   ok: true
               });
                return false;
           }else if(num<2){
               art.dialog({
                   content: "股东数量不能小于2!",
                   icon:"warning",
                   ok: true
               });
              return false;
           }

       }
       //其他有限责任公司,股东必须至少有一个不是自然人
        if(COMPANY_TYPECODE=='qtyxzrgs'){
            var licenceTypeNum=0;
            $("[name$='SHAREHOLDER_TYPE']").each(function(i){
                var SHAREHOLDER_TYPE = $(this).val();
                if(SHAREHOLDER_TYPE!='zrr'){
                    licenceTypeNum++;
                }
            });
            if(licenceTypeNum<1){{
                art.dialog({
                    content: "股东必须至少有一个不是自然人!",
                    icon:"warning",
                    ok: true
                });
                 return false;
            }}
        }
        //监事数量控制
        var jsnum = 0;
        var zgnum=0;
        var companySetNature= $("input[name='COMPANY_SETNATURE']").val();//公司设立性质编码
        $("select[name$='SUPERVISOR_JOB']").each(function(i){
            jsnum++;
        });
        $("select[name$='SUPERVISOR_APPOINTOR']").each(function(i){
            var appointor=this.value;
            if(appointor=='职工代表大会'){
                zgnum++;
            }
        });
        if(companySetNature=='04'){
            if(jsnum!=3){
                art.dialog({
                    content: "监事数量必须为3",
                    icon:"warning",
                    ok: true
                });
               return false;
            }
            if(zgnum<1||zgnum>2){
                art.dialog({
                    content: "职工监事数量大于等于1且小于等于2",
                    icon:"warning",
                    ok: true
                });
                return false;
            }
        }else{
            if(jsnum!=1){
                art.dialog({
                    content: "监事数量必须为1!",
                    icon:"warning",
                    ok: true
                });
                return false;
            }
        }

    }
    return flag;
}


function  setCompanyNameOfMp() {
     var areaName=$("input[name='AREA_NAME']").val();
    var wordNum=$("input[name='WORD_NUM']").val();
    var chileIndustryName= $("input[name='CHILD_INDUSTRY_NAME']").val();
    var orgType=$("[name='ORG_TYPE']").val();
    var companyName=areaName+wordNum+chileIndustryName+orgType;
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