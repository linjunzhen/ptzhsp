


$(function() {
	 //限定只能企业账号办理，个人账号不允许办理
	 var userType = $("input[name='USER_TYPE']").val();
     if (userType != '2') {
         alert("个人账号无法办理此事项!")
         location.href = __ctxPath + "/contentController/list.do?moduleId=605";//跳转至全程网办入口
     }
	
	//注销明细身份证初始化
	//$("select[name='DYQRXX_JBZJLX']").val("身份证");
	//$("select[name='ZJLB']").val("身份证");
	$("select[name='ZX_QLLX']").attr("disabled","disabled");
	//动态设置注销明细选择框长度
	$("select[name='ZXYY']").attr("style","width:60%");
	$("select[name='ZXFJ']").attr("style","width:60%");
	
});


//提交流程
function submitFlow(formId) {
    //先判断表单是否验证通过
    var validateResult =$("#"+formId).validationEngine("validate");
    if(validateResult){
        //获取注销明细JSON
    	getZxmxInfoJson();
        if ($(".bdczxxxcxTr").length < 1 || $(".bdczxxxcxTr").length > 1) {
            parent.art.dialog({
                content : "请添加（有且仅有）一条注销明细！",
                icon : "warning",
                ok : true
            });
            return;
        }
        
        var loginQYZH = $("input[name='USER_NAME']").val();//登录的企业账号        
        //判断登陆的企业账号是否和注销明细中的抵押权人是否一致，只允许注销本企业所属的抵押证明。
        var flag = true;
        $("#zxmxTable").find(".bdczxxxcxTr").find("input[name='bdczxxx']").each(function() {
            var obj = JSON.parse(this.value);
            var dyqmc = obj.DYQR.trim();//抵押权人      
            if(loginQYZH.indexOf(dyqmc)==-1){
            	flag = false;
            }         
        });
        if(!flag){
        	parent.art.dialog({
                content : "只允许注销本企业所属的抵押证明！",
                icon : "warning",
                ok : true
            });
            return;
        }
        
        //判断注销明细中抵押人与抵押人电话号码个数是否一致
        var bdczxxx = $("#zxmxTable").find("tr").eq(1).find("[name='bdczxxx']").val();
        var dyrLength = 0;  
        var dyrDhLength = 0;
        if(JSON.parse(bdczxxx).DYR != null && JSON.parse(bdczxxx).DYR != "undefined"){
        	dyrLength =  JSON.parse(bdczxxx).DYR.split(",").length;
        } 
        if(JSON.parse(bdczxxx).DYRXX_DHHM != null && JSON.parse(bdczxxx).DYRXX_DHHM != "undefined"){
        	dyrDhLength = JSON.parse(bdczxxx).DYRXX_DHHM.split(",").length;
        }    
        if(dyrLength != dyrDhLength){
        	parent.art.dialog({
                content : "注销明细中抵押人个数与抵押人电话号码个数不一致，请确认！",
                icon : "warning",
                ok : true
            });
            return;
        }      
        
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
        if(submitMaterFileJson||submitMaterFileJson==""){
            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
            //将其转换成JSON对象
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);

            if (flowSubmitObj) {
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

			if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
				/*判断此业务是否已被办理过*/
				var zxmxJson = $("input[type='hidden'][name='ZXMX_JSON']").val();
				if (zxmxJson) {
					var parse = JSON.parse(zxmxJson);
					var bdcdyh = parse[0].BDCDYH;
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
			}

            //先获取表单上的所有值
            var formData = FlowUtil.getFormEleData(formId);
            for(var index in formData){
                flowSubmitObj[eval("index")] = formData[index];
            }
            flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
            var postParam = $.param(flowSubmitObj);
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


//暂存流程
function saveFlow(formId) {
    //先判断表单是否验证通过
	
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson(formId,1);
    //获取流程信息对象JSON
    var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
    //将其转换成JSON对象
    var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    //注销明细
    getZxmxInfoJson();
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData(formId);
    for (var index in formData) {
        flowSubmitObj[eval("index")] = formData[index];
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

/**
 * 获取注销明细JSON
 */
function getZxmxInfoJson(){
	var datas = [];
	 $("#zxmxTable tr").each(function(i){
		var bdczxxx = $(this).find("[name='bdczxxx']").val();
		if(''!=bdczxxx && null!=bdczxxx){
			datas.push(JSON.parse(bdczxxx));
		}
	 });
	var zxmxJson = JSON.stringify(datas);
	$("input[type='hidden'][name='ZXMX_JSON']").val(zxmxJson);	
}


var bdczxxxcxTr = 1;
//初始化业务表json（注销明细）形式数据
function initAutoTable(flowSubmitObj){
	var zxmxJson = flowSubmitObj.busRecord.ZXMX_JSON;//注销明细
		if(null != zxmxJson && '' != zxmxJson){
			var zxmxInfos = eval(zxmxJson);
			var html = "";
			for(var i=0;i<zxmxInfos.length;i++){
				html +='<tr class="bdczxxxcxTr" id="bdczxxxcxTr_'+bdczxxxcxTr+'">';
				html +='<input type="hidden" name="bdczxxx"/>';
				html +='<td style="text-align: center;">'+bdczxxxcxTr+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].ZH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].HH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].BDCDYH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].BDCDJZMH+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].DYQR+'</td>';
				html +='<td style="text-align: center;">'+zxmxInfos[i].DYR+'</td>';
				html +='<td style="text-align: center;">';
				html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdczxxxcx(this);" id="zxmxAdd">查 看</a>';
				html +='<a href="javascript:void(0);" onclick="delZxmxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
				html +='</tr>';
				$("#zxmxTable").append(html);
				$("#bdczxxxcxTr_"+bdczxxxcxTr+" input[name='bdczxxx']").val(JSON.stringify(zxmxInfos[i]));
				bdczxxxcxTr ++;
				html = "";
			}
			loadBdczxxxcxToId();
		}
}

/**=================注销明细信息开始================================*/

//不动产抵押档案查询
function showSelectBdcdydacx(){	
	$.dialog.open("bdcDyqzxdjController.do?selector&allowCount=1", {
		title : "不动产抵押档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
		    var bdcdydacxInfo = art.dialog.data("bdcdydacxInfo");
			if(bdcdydacxInfo!=undefined&&bdcdydacxInfo!=null&&bdcdydacxInfo!=""){
			    var html = "";
				for(var i = 0;i<bdcdydacxInfo.length;i++){
					html +='<tr class="bdczxxxcxTr" id="bdczxxxcxTr_'+bdczxxxcxTr+'">';
					html +='<input type="hidden" name="bdczxxx"/>';	 
					html +='<td style="text-align: center;">'+bdczxxxcxTr+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].ZH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].HH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].BDCDYH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].BDCDJZMH+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].DYQR+'</td>';
					html +='<td style="text-align: center;">'+bdcdydacxInfo[i].DYR+'</td>';
					html +='<td style="text-align: center;">';
					html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdczxxxcx(this);" id="zxmxAdd">查 看</a>';
					html +='<a href="javascript:void(0);" onclick="delZxmxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
					html +='</tr>';
					$("#zxmxTable").append(html);
					$("#bdczxxxcxTr_"+bdczxxxcxTr+" input[name='bdczxxx']").val(JSON.stringify(bdcdydacxInfo[i]));
					bdczxxxcxTr ++;
					html = "";
				}	
			    art.dialog.removeData("bdcdydacxInfo");		
			    loadBdczxxxcxToId();
			}
			if($("#zxmxTable").find("tr").length>1){				
				$("input[name='BDC_ZXZS']").val($("#zxmxTable").find("tr").length-1);
			} else{				
				$("input[name='BDC_ZXZS']").val(1);
			}
		}
	
	}, false);
};

/**
 * 查看
 */
function loadBdczxxxcx(o){
	$("#zxmxInfo_1 input[type='text']").val('');
	$("#zxmxInfo_1 select").val('');
	$("#DYQR").combobox("setValue", "");
	var bdczxxx = $(o).closest("tr").find("[name='bdczxxx']").val();
	FlowUtil.initFormFieldValue(JSON.parse(bdczxxx),"zxmxInfo_1");
	//设置抵押权人
	$("#DYQR").combobox("setValue", JSON.parse(bdczxxx).DYQR);//设置抵押权人
	var trId = $(o).closest("tr").attr("id");
	$("#zxmxInfo_1 input[name='trid']").val(trId);
	$(".bdczxxxcxTr").removeClass("bdczxxxcxTrRed");
	$(o).closest("tr").addClass("bdczxxxcxTrRed");
}
/**
 * 删除注销明细
 */
function delZxmxTr(o){
	$(o).closest("tr").remove();
	loadBdczxxxcxToId();
} 

function loadBdczxxxcxToId(){
	$("#zxmxInfo_1 input[type='text']").val('');
	$("#zxmxInfo_1 select").val('');
	if($(".bdczxxxcxTr").length>=1){		
		var bdczxxx = $("#zxmxTable").find("tr").eq(1).find("[name='bdczxxx']").val();
		FlowUtil.initFormFieldValue(JSON.parse(bdczxxx),"zxmxInfo_1");
		setDyqrInfo(JSON.parse(bdczxxx));//设置抵押权人
		var trId = $("#zxmxTable").find("tr").eq(1).attr("id");
		$("#zxmxInfo_1 input[name='trid']").val(trId);
		$(".bdczxxxcxTr").removeClass("bdczxxxcxTrRed");
		$("#zxmxTable").find("tr").eq(1).addClass("bdczxxxcxTrRed");
		//更新受理信息-申请人单位（默认抵押权人名称） 坐落默认（抵押物坐落）
		$("input[name='APPLICANT_UNIT']").val(JSON.parse(bdczxxx).DYQR);
		$("input[name='LOCATED']").val(JSON.parse(bdczxxx).ZJJZWZL);
	}else{
		bdczxxxcxTr =1;//初始值为1
	}
}

//设置抵押权人信息
function setDyqrInfo(bdczxxx){
	var value = bdczxxx.DYQR;
	$("#DYQR").combobox("setValue", "");	
	var datas = $('#DYQR').combobox('getData');
	$("#DYQR").combobox("setValue", value);//设置抵押权人
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == value){			
			$('#DYQRXX_IDNO').val(datas[i].DIC_CODE);                                       
            $('#DYQRXX_JB').val(datas[i].BANK_CONTECT_NAME);
            $('#DYQRXX_JBZJHM').val(datas[i].BANK_CONTECT_CARD);
            $('#DYQRXX_JBDH').val(datas[i].BANK_CONTECT_PHONE);
			$("select[name='DYQRXX_ZJZL'] option[value='营业执照']").prop("selected", true);
			break ;
		}
	}
}

/**
 * 保存注销明细
 */
function updateZxmxInfo(){
	var trid = $("#zxmxInfo_1 input[name='trid']").val();	
	var bdczxxx = $("#"+trid+" input[name='bdczxxx']").val();
	if(""!=bdczxxx && null!= bdczxxx && undefined!=bdczxxx){		
		var bdczxxxInfos = JSON.parse(bdczxxx);
		$("#zxmxInfo").find('table').eq(0).find("*[name]").each(function(){
			  var inputName= $(this).attr("name");
			  var inputValue = $(this).val();
			  //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
				  var isChecked = $(this).is(':checked');
				  if(isChecked){
					  bdczxxxInfos[inputName] = inputValue;
				  }
			  }else if(fieldType=="checkbox"){
				  var inputValues = FlowUtil.getCheckBoxValues(inputName);
				  bdczxxxInfos[inputName] = inputValues;
			  }else if(fieldType!="button"){
				  bdczxxxInfos[inputName] = inputValue;
			  }          
		});
		$("#"+trid+" input[name='bdczxxx']").val(JSON.stringify(bdczxxxInfos));
		//同时更新底下的注销明细列表信息
		if(""!=trid && undefined !=trid){
            var i = trid.split("_")[1];
            appendZxmxRow(bdczxxxInfos,i,trid); 
            art.dialog({
    			content: "保存成功",
    			icon:"succeed",
    			time:3,
    			ok: true
    		});
	    }		
	} else {
		art.dialog({
			content: "请先选择注销明细信息！",
			icon:"warning",
			ok: true
		});
	}
}


function appendZxmxRow(item,index,replaceTrid){
    if(item != "" && null != item) {
        var html = "";
        html +='<tr class="bdczxxxcxTr" id="bdczxxxcxTr_'+index+'">';
		html +='<input type="hidden" name="bdczxxx"/>';	 
		html +='<td style="text-align: center;">'+index+'</td>';
		html +='<td style="text-align: center;">'+item.ZH+'</td>';
		html +='<td style="text-align: center;">'+item.HH+'</td>';
		html +='<td style="text-align: center;">'+item.BDCDYH+'</td>';
		html +='<td style="text-align: center;">'+item.BDCDJZMH+'</td>';
		html +='<td style="text-align: center;">'+item.DYQR+'</td>';
		html +='<td style="text-align: center;">'+item.DYR+'</td>';
		html +='<td style="text-align: center;">';
		html +='<a href="javascript:void(0);" class="eflowbutton" onclick="loadBdczxxxcx(this);" id="zxmxAdd">查 看</a>';
		html +='<a href="javascript:void(0);" onclick="delZxmxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
		html +='</tr>';
        if(replaceTrid){
           var prevtrid = $("#"+replaceTrid).prev("tr").attr("id");
           $("#"+replaceTrid).remove();
           if(prevtrid){
                $("#"+prevtrid).after(html);
           } else{
               $("#zxmxTable").find("tr").eq(0).after(html);
           }       
        }else{
           $("#zxmxTable").append(html);
        }
        $("#bdczxxxcxTr_"+index+" input[name='bdczxxx']").val(JSON.stringify(item));
        $("#zxmxInfo_1").attr("trid","bdczxxxcxTr_"+index);
    }
}
/**=================注销明细信息结束================================*/


//证件类型动态改变
function setValidate(zjlx,name){
	if(zjlx=="SF" || zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");			
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");	
	}
}

//是否在线签章
function openSelector() {
    $.dialog.open("bdcDyqzxdjController.do?busSelectorItemView", {
        title : "业务选择",
        width : "600px",
        height : "200px",
        lock : true,
        resize : false,
        close: function () {
            var busSelectData = art.dialog.data("busSelectData");
            if (busSelectData) {
                var IS_SIGN = busSelectData.IS_SIGN;
                if (IS_SIGN == '1') {
                	$("input[name='ISQCWB']").val("1");
                }else{
                	$("input[name='ISQCWB']").val("0");
                }
                //console.log($("input[name='ISQCWB']").val());
            } else {
                window.location.href = __ctxPath + '/contentController/list.do?moduleId=605';
            }
        }
    }, false);
}


