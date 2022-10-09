
$(function(){
    $("input[name='JBR_NAME']").removeAttr('readonly');
    $("input[name='JBR_ZJHM']").removeAttr('readonly');
    var itemName=$("input[name='ITEM_NAME']").val();
    //初始化验证引擎的配置
    $.validationEngine.defaults.autoHidePrompt = true;
    $.validationEngine.defaults.promptPosition = "topRight";
    $.validationEngine.defaults.autoHideDelay = "2000";
    $.validationEngine.defaults.fadeDuration = "0.5";
    $.validationEngine.defaults.autoPositionUpdate =true;
    var flowSubmitObj = FlowUtil.getFlowObj();
    if(flowSubmitObj){
        //获取表单字段权限控制信息
        var currentNodeFieldRights = flowSubmitObj.currentNodeFieldRights;
        var exeid = flowSubmitObj.EFLOW_EXEID;
        $("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
        //初始化表单字段权限控制
        FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_DATAQUERY_FORM");
        //切换首套房查询
        queryTypeFn();
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
            initAutoTable(flowSubmitObj);//初始化权利信息
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_DATAQUERY_FORM");
            if(flowSubmitObj.busRecord.RUN_STATUS!=0){
                $("#T_BDC_DATAQUERY_FORM input").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                $("#T_BDC_DATAQUERY_FORM select").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                //打印控件显示
                $("#printQueryCertify").show();
                $("#pringApplyForm").show();
                if($("input[name='SBMC']").val()){
                }else{
                    $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+itemName);
                }
                
                $("#bdcBtnQueryPrint").show();
            	$("#bdcBtnQuerySeach").hide();
                $("#queryButton").hide();
            }
        }else{
            $("input[name='SBMC']").val("-"+itemName);
        }

        var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
        if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME ){
            if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
                $("input[name='JBR_NAME']").removeAttr('readonly');
            }
        }
    }

    //初始化材料列表
  	//AppUtil.initNetUploadMaters({
  	//	busTableName:"T_BDC_DATAQUERY"
  	//});
});

function FLOW_SubmitFun(flowSubmitObj){
    var flowSubmitObj = FlowUtil.getFlowObj();
    var runstatus1=$("input[name='runstatus1']").val();
    var queryType = $('input[name="QUERY_TYPE"]:checked').val();
    if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES="开始"&&runstatus1!="1"&&queryType!=2){
        if(!setObId()){
            art.dialog({
                content: "请选择需要勾选的查询记录",
                icon:"warning",
                ok: true
            });
            return null;
        }
    }

    //先判断表单是否验证通过
    var validateResult =$("#T_BDC_DATAQUERY_FORM").validationEngine("validate");
    if(validateResult){
    	getFamilyInfoJson();
    	setGrBsjbr();//个人不显示经办人设置经办人的值
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
        if(submitMaterFileJson||submitMaterFileJson==""){
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData("T_BDC_DATAQUERY_FORM");
            formData.BDC_REMARK = $("textarea[name='BDC_REMARK']").val();
            for(var index in formData){
                flowSubmitObj[eval("index")] = formData[index];
            }
            flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
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
    var runstatus1=$("input[name='runstatus1']").val();
    var queryType = $('input[name="QUERY_TYPE"]:checked').val();
    if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES="开始"&&runstatus1!="1"&&queryType!=2){
        if(!setObId()){
            art.dialog({
                content: "请选择需要勾选的查询记录",
                icon:"warning",
                ok: true
            });
            return null;
        }
    }
    //先判断表单是否验证通过
	getFamilyInfoJson();
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData("T_BDC_DATAQUERY_FORM");
    formData.BDC_REMARK = $("textarea[name='BDC_REMARK']").val();
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    return flowSubmitObj;

}
//勾选查询的记录
function setObId(){
    var $dataGrid = $("#EstateGrid");
    var rowsData = $dataGrid.datagrid('getChecked');
    var total= $("#EstateGrid").datagrid("getData").total;
    if (total==1&&rowsData.length==0) {
        var name= $("#EstateGrid").datagrid("getRows")[0].NAME;
        if (name=='<div style="text-align:center;color:red">查询接口异常,请联系管理员</div>'||name=='<div style="text-align:center;color:red">查询无记录</div>'){
            return true;
        }
    }else if(total==0){
        return false;
    }else if(rowsData.length<1){
        return false;
    }else{
        var colName = $dataGrid.datagrid('options').idField;
        var selectColNames = "";
        for ( var i = 0; i < rowsData.length; i++) {
            if(i>0){
                selectColNames+=",";
            }
            selectColNames+=eval('rowsData[i].'+colName);
        }
        saveCheckedData();
        $("input[name='OB_ID']").val(selectColNames);
        return true;
    }
}
function saveCheckedData(){
    var $dataGrid = $("#EstateGrid");
    var datas = [];
    var rowsData = $dataGrid.datagrid('getChecked');
    for ( var i = 0; i < rowsData.length; i++) {
        var trData = {};
        trData=rowsData[i];
        delete trData.ZDT
        delete trData.FHT
        datas.push(trData);
    }
    var json=JSON.stringify(datas);
    if(json=='[]'){
        json="";
    }
    $("input[type='hidden'][name='POWERPEOPLEINFO_JSON']").val(json);
}

function FLOW_BackFun(flowSubmitObj){
    return flowSubmitObj;
}

function CurentTime()
{
    var now = new Date();
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var clock = year + "-";
    if(month < 10)
        clock += "0";
    clock += month + "-";
    if(day < 10)
        clock += "0";
    clock += day + " ";
    if(hh < 10)
        clock += "0";
    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm;
    return(clock);
}

function setFileNumber(serialNum){
	/* var fileNum = '${serviceItem.SSBMBM}'+"-"+serialNum+"-"+'${execution.SQJG_CODE}'; */
    var enterprise = '${execution.SQJG_CODE}';
    var idCard = '${execution.SQRSFZ}';
// 	alert(idCard + "," + enterprise);
    var fileNum;
    if (enterprise != ""){
        fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + enterprise;
    } else {
        fileNum = '${serviceItem.SSBMCODE}' + "-" + serialNum + "-" + idCard;
    }
    $("#fileNumber").val(fileNum);
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

function applyOfReadCard(){
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("input[name='SQRMC']").val(GT2ICROCX.Name);
        $("input[name='SQRSFZ']").val(GT2ICROCX.CardNo);
        $("input[name='Q_T.OBLIGEE_NAME_LIKE']").val(GT2ICROCX.Name);
        $("input[name='Q_T.OBLIGEE_IDNUM_LIKE']").val(GT2ICROCX.CardNo);
    }
}
function jbrOfReadCard(){
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $("input[name='JBR_NAME']").val(GT2ICROCX.Name)
        $("input[name='JBR_ZJHM']").val(GT2ICROCX.CardNo)
    }
}


function print()//打印
{

    GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
}
//------------------------------------身份身份证读取结束---------------------------------------------------

/**=================查档证明(家庭成员信息)开始================================*/
var familyInfoSn = 1;
function addFamilyInfo(){
	familyInfoSn = familyInfoSn+1;
	var table = document.getElementById("familyInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteFamilyInfo('");
	if(familyInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+27);
	}else{
		var replacestr = trHtml.substring(findex,findex+26);
	}
	trHtml = trHtml.replace(replacestr,"deleteFamilyInfo('"+familyInfoSn+"')");
	trHtml = "<tr id=\"familyInfo_"+familyInfoSn+"\">"+trHtml+"</tr>";
	$("#familyInfo").append(trHtml);
}

function deleteFamilyInfo(idSn){
	var table = document.getElementById("familyInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "不能删除,至少填写一个家庭成员信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#familyInfo_"+idSn).remove();
}

function getFamilyInfoJson(){
	var datas = [];
	var table = document.getElementById("familyInfo");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#familyInfo_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='FAMILY_JSON']").val(JSON.stringify(datas));
}
/**=================查档证明(家庭成员信息)结束================================*/
//家庭首套查询方法
function searchBdcData(){
	 var validateResult =$("#familyInfo_form").validationEngine("validate");
	 if(validateResult){
	 	var datas = [];
		var table = document.getElementById("familyInfo");
		for ( var i = 1; i <= table.rows.length-1; i++) {
			var trData = {};
			$("#familyInfo_"+i).find("*[name]").each(function(){
		          var inputName= $(this).attr("name");
		          var inputValue = $(this).val();
		          //获取元素的类型
				  var fieldType = $(this).attr("type");
				  if(fieldType=="radio"){
				  	  var isChecked = $(this).is(':checked');
				  	  if(isChecked){
				  		  trData[inputName] = inputValue;
				  	  }
				  }else if(fieldType=="checkbox"){
				  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  	  trData[inputName] = inputValues;
				  }else{
					  trData[inputName] = inputValue;
				  }          
		    });
			datas.push(trData);
		}
		AppUtil.ajaxProgress({
			url : "bdcApplyController/searchBdcQuery.do",
			params : {
				"datas":JSON.stringify(datas)
			},
			callback : function(resultJson) {
				if(resultJson.success){
					var list = resultJson.data;
					if(list != null && list.length > 0){
						//查询的结果
                        var listArr = [];
                        for (let i = 0; i < list.length; i++) {
                            delete list[i].FHT;
                            delete list[i].ZDT;
                            listArr.push(list[i])
                        }
						$("input[type='hidden'][name='RESULT_JSON']").val(JSON.stringify(listArr));
						$('#EstaeFamilyGrid').datagrid('loadData', list);
						/*var sn = 1,html="";
						for(var i=0;i<list.length;i++){
							html += setpowerPeopleHtml(list[i],sn);
							sn = sn +1;
						}
						$("#result_tb").html(html);*/
					}else{
						var jsonstr = '{"total":0,"rows":[]}';  
						var data = $.parseJSON(jsonstr);    
						$('#EstaeFamilyGrid').datagrid('loadData', data);
					}					
				}else{
					art.dialog({
		                content: resultJson.msg,
		                icon:"warning",
		                ok: true
		            });
				}
			}
		});
	 }
}
