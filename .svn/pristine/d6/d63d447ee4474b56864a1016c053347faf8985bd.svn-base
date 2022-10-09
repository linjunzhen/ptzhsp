function MyGetData()//OCX读卡成功后的回调函数
{
// 			POWERPEOPLENAME.value = GT2ICROCX.Name;//<-- 姓名--!>
// 			POWERPEOPLEIDNUM.value = GT2ICROCX.CardNo;//<-- 卡号--!>
}

function MyClearData()//OCX读卡失败后的回调函数
{

}

function MyGetErrMsg()//OCX读卡消息回调函数
{
// 			Status.value = GT2ICROCX.ErrMsg;
}

function maleRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='MALE_NAME']").val(GT2ICROCX.Name);
        $("[name='MALE_CARD']").val(GT2ICROCX.CardNo);
    }
}

function femaleRead(o)//开始读卡
{
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("[name='FEMALE_NAME']").val(GT2ICROCX.Name);
        $("[name='FEMALE_CARD']").val(GT2ICROCX.CardNo);
    }
}

function FLOW_SubmitFun(flowSubmitObj){
    //先判断表单是否验证通过
    var validateResult =$("#T_BSFW_LHDJ_FORM").validationEngine("validate");
    if(validateResult){
        setGrBsjbr();
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
        if(submitMaterFileJson||submitMaterFileJson==""){
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData("T_BSFW_LHDJ_FORM");
            for(var index in formData){
                flowSubmitObj[eval("index")] = formData[index];
            }
            //console.dir(flowSubmitObj);
            return flowSubmitObj;
        }else{
            return null;
        }
    }else{
        return null;
    }
}

function FLOW_TempSaveFun(flowSubmitObj){
    //先判断表单是否验证通过
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData("T_BSFW_LHDJ_FORM");
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    return flowSubmitObj;

}

function FLOW_BackFun(flowSubmitObj){
    return flowSubmitObj;
}

function setGrBsjbr(){
    var val=$('input:checkbox[name="SFXSJBRXX"]:checked').val();
    var lxval=$('input:radio[name="BSYHLX"]:checked').val();
    if(val==null&&lxval=="1"){
        $('input[name="JBR_NAME"]').val($('input[name="SQRMC"]').val());
        var zjlx = $('#SQRZJLX option:selected').val();
        $("#JBR_ZJLX").find("option[value='"+zjlx+"']").attr("selected",true);
        $('input[name="JBR_ZJHM"]').val($('input[name="SQRSFZ"]').val());
        $('input[name="JBR_MOBILE"]').val($('input[name="SQRSJH"]').val());
        $('input[name="JBR_LXDH"]').val($('input[name="SQRDHH"]').val());
        $('input[name="JBR_ADDRESS"]').val($('input[name="SQRLXDZ"]').val());
        $('input[name="JBR_EMAIL"]').val($('input[name="SQRYJ"]').val());
    }
}