
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
        FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_BDC_WT_FORM");
        //初始化表单字段值
        if(flowSubmitObj.busRecord){
        	
        	//初始化信息
            initAutoTable(flowSubmitObj);
            
            FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_BDC_WT_FORM");
            if(flowSubmitObj.busRecord.RUN_STATUS!=0){
                $("#T_BDC_WT_FORM input").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                $("#T_BDC_WT_FORM select").each(function(index){
                    $(this).attr("disabled","disabled");
                });
                
                if($("input[name='SBMC']").val()){
                }else{
                    $("input[name='SBMC']").val(flowSubmitObj.EFLOW_CREATORNAME+"-"+itemName);
                }
            }
        }else{
            $("input[name='SBMC']").val("-"+itemName);
            var wtStartdate= $("#WT_STARTDATE").val();
            if(wtStartdate || wtStartdate==''){
                wtStartdate=today();
                $("input[name='WT_STARTDATE']").val(wtStartdate);
            }
            
            var wtEnddate= $("#WT_ENDDATE").val();
            if(wtEnddate || wtEnddate==''){
            	wtEnddate=nextYearday();
                $("input[name='WT_ENDDATE']").val(wtEnddate);
            }
            
            
        }

        var eflowNodeName = "," + flowSubmitObj.EFLOW_CURUSEROPERNODENAME + ",";
        if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME ){
            if(flowSubmitObj.busRecord&&flowSubmitObj.EFLOW_CURUSEROPERNODENAME=="开始"){
                $("input[name='JBR_NAME']").removeAttr('readonly');
            }
        }
        
        var handTime=$("input[name='WT_JBDATE']").val();
        if(handTime==''){
            handTime=CurentTime();
            $("input[name='WT_JBDATE']").val(handTime);
        }
        
        
    }

    //初始化材料列表
  	//AppUtil.initNetUploadMaters({
  	//	busTableName:"T_BDC_WT"
  	//});
});

function FLOW_SubmitFun(flowSubmitObj){
    var flowSubmitObj = FlowUtil.getFlowObj();
    /*var runstatus1=$("input[name='runstatus1']").val();
    if(flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES="开始"&&runstatus1!="1"){
        
    }*/

    //先判断表单是否验证通过
    var validateResult =$("#T_BDC_WT_FORM").validationEngine("validate");
    if(validateResult){
    	getWtInfoJson();
    	getStInfoJson();
    	getBdInfoJson();
    	setGrBsjbr();//个人不显示经办人设置经办人的值
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
        if(submitMaterFileJson||submitMaterFileJson==""){
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
        }else{
            return null;
        }   	
        //先获取表单上的所有值
        var formData = FlowUtil.getFormEleData("T_BDC_WT_FORM");
        for(var index in formData){
            flowSubmitObj[eval("index")] = formData[index];
        }
        flowSubmitObj.EFLOW_ISTEMPSAVE = "-1";
        return flowSubmitObj;
    }else{
        return null;
    }
}

function FLOW_TempSaveFun(flowSubmitObj){
    //先判断表单是否验证通过
	getWtInfoJson();
	getStInfoJson();
	getBdInfoJson();
    var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
    $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
    //先获取表单上的所有值
    var formData = FlowUtil.getFormEleData("T_BDC_WT_FORM");
    for(var index in formData){
        flowSubmitObj[eval("index")] = formData[index];
    }
    return flowSubmitObj;

}

function FLOW_BackFun(flowSubmitObj){
    return flowSubmitObj;
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
    //$("input[name='POWERPEOPLENAME']").val("");
    //$("input[name='POWERPEOPLEIDNUM']").val("");
}

function MyGetErrMsg()//OCX读卡消息回调函数
{
// 			Status.value = GT2ICROCX.ErrMsg;
}

//委托人
function wtPowerPeopleRead(thiz){
  GT2ICROCX.PhotoPath = "c:"
  //GT2ICROCX.Start() //循环读卡
  //单次读卡(点击一次读一次)
  if (GT2ICROCX.GetState() == 0){
      GT2ICROCX.ReadCard();
      $(thiz).parent().parent().prev().find("input[name='wtr_name']").val(GT2ICROCX.Name);
      $(thiz).parent().find("input[name='wtr_zjhm']").val(GT2ICROCX.CardNo);
  }
}

//委托法人代表
function wtfddbPowerPeopleRead(thiz){
  GT2ICROCX.PhotoPath = "c:"
  //GT2ICROCX.Start() //循环读卡
  //单次读卡(点击一次读一次)
  if (GT2ICROCX.GetState() == 0){
      GT2ICROCX.ReadCard();
      $(thiz).parent().parent().prev().find("input[name='fddb_name']").val(GT2ICROCX.Name);
      $(thiz).parent().find("input[name='fddb_zjhm']").val(GT2ICROCX.CardNo);
  }
}

//委托代理人
function wtagentPowerPeopleRead(thiz){
  GT2ICROCX.PhotoPath = "c:"
  //GT2ICROCX.Start() //循环读卡
  //单次读卡(点击一次读一次)
  if (GT2ICROCX.GetState() == 0){
      GT2ICROCX.ReadCard();
      $(thiz).parent().parent().prev().find("input[name='dlr_name']").val(GT2ICROCX.Name);
      $(thiz).parent().find("input[name='dlr_zjhm']").val(GT2ICROCX.CardNo);
  }
}

//受托人
function stPowerPeopleRead(thiz){
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $(thiz).parent().parent().prev().find("input[name='str_name']").val(GT2ICROCX.Name);
        $(thiz).parent().find("input[name='str_zjhm']").val(GT2ICROCX.CardNo);
    }
}

//受托法人代表
function stfddbPowerPeopleRead(thiz){
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $(thiz).parent().parent().prev().find("input[name='fddb_name']").val(GT2ICROCX.Name);
        $(thiz).parent().find("input[name='fddb_zjhm']").val(GT2ICROCX.CardNo);
    }
}

//受托代理人
function stagentPowerPeopleRead(thiz){
    GT2ICROCX.PhotoPath = "c:"
    //GT2ICROCX.Start() //循环读卡
    //单次读卡(点击一次读一次)
    if (GT2ICROCX.GetState() == 0){
        GT2ICROCX.ReadCard();
        $(thiz).parent().parent().prev().find("input[name='dlr_name']").val(GT2ICROCX.Name);
        $(thiz).parent().find("input[name='dlr_zjhm']").val(GT2ICROCX.CardNo);
    }
}

function print()//打印
{

    GT2ICROCX.PrintFaceImage(0,30,10)//0 双面，1 正面，2 反面
}
//------------------------------------身份身份证读取结束---------------------------------------------------
function initAutoTable(flowSubmitObj){
	var wt_json = flowSubmitObj.busRecord.WT_JSON;
	if(null != wt_json && '' != wt_json){
		var wtinfos = JSON.parse(wt_json);
		for(var i=0;i<wtinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(wtinfos[i],"wtInfo_1");								
			}else{
				addWtInfo();
				FlowUtil.initFormFieldValue(wtinfos[i],"wtInfo_"+(i+1));
			}			
		}
		for(var i=0;i<wtinfos.length;i++){
			if(wtinfos[i].wtr_pic_id){
				var spanHtml = "<p id=\"" + wtinfos[i].wtr_pic_id + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        		spanHtml += (" onclick=\"AppUtil.downLoadFile('" + wtinfos[i].wtr_pic_id + "');\">");
        		spanHtml += "<font color=\"blue\">下载</font></a>";
        		spanHtml += "</p>";
        		$("#wtInfo_"+(i+1)+" input[name='wtr_pic_id']").after(spanHtml);
			}
		}
		//$("input[name='deleteCurrentFramilyInfo']").remove();
		//$("input[name='addOneFamilyInfo']").remove();
	}
	
	var st_json = flowSubmitObj.busRecord.ST_JSON;
	if(null != st_json && '' != st_json){
		var stinfos = JSON.parse(st_json);
		for(var i=0;i<stinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(stinfos[i],"stInfo_1");
			}else{
				addStInfo();
				FlowUtil.initFormFieldValue(stinfos[i],"stInfo_"+(i+1));
			}			
		}
		for(var i=0;i<stinfos.length;i++){
			if(stinfos[i].str_pic_id){
				var spanHtml = "<p id=\"" + stinfos[i].str_pic_id + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
	    		spanHtml += (" onclick=\"AppUtil.downLoadFile('" + stinfos[i].str_pic_id + "');\">");
	    		spanHtml += "<font color=\"blue\">下载</font></a>";
	    		spanHtml += "</p>";
	    		$("#stInfo_"+(i+1)+" input[name='str_pic_id']").after(spanHtml);
			}
		}
		//$("input[name='deleteCurrentFramilyInfo']").remove();
		//$("input[name='addOneFamilyInfo']").remove();
	}
	
	var bd_json = flowSubmitObj.busRecord.BD_JSON;
	if(null != bd_json && '' != bd_json){
		var bdinfos = JSON.parse(bd_json);
		for(var i=0;i<bdinfos.length;i++){
			if(i==0){
				FlowUtil.initFormFieldValue(bdinfos[i],"bdInfo_1");
			}else{
				addBdInfo();
				FlowUtil.initFormFieldValue(bdinfos[i],"bdInfo_"+(i+1));
			}
		}
		//$("input[name='deleteCurrentFramilyInfo']").remove();
		//$("input[name='addOneFamilyInfo']").remove();
	}
}


function chooseScanCtrl(inputName,thiz) {
    var gpytype = $('input[name="GPYTYPE"]:checked').val();
    if(gpytype==""||gpytype==undefined){
        parent.art.dialog({
            content : "请选择高拍仪类型",
            icon : "error",
            time : 3,
            ok : true
        });
    }else if(gpytype=="1"){
        openScan("VideoInputCtl",inputName,thiz);
    }else if(gpytype=="2"){
        openScan("VideoInputCtl",inputName,thiz);
    }else if(gpytype=="3"){
        openScan("VideoInputCtlZT",inputName,thiz);
    }else if(gpytype=="4"){
        openScan("VideoInputCtl",inputName,thiz);
    }
}

function openScan(scanView,inputName,thiz) {
    var uploadUserId = $("input[name='uploadUserId']").val();
    //定义上传的人员的NAME
    var uploadUserName = $("input[name='uploadUserName']").val();
    $.dialog.open("webpage/bsdt/applyform/videoinput/"+scanView+".jsp?uploadPath=applyform&attachKey=bdcfaces" +
        "&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName) + "&busTableName=T_BDC_WT&matreClsm=bdcwtfacescan", {
        title : "高拍仪",
        width : "810px",
        lock : true,
        resize : false,
        height : "90%",
        close : function() {
            var resultJsonInfo = art.dialog.data("resultJsonInfo");
            if (resultJsonInfo) {
                for (var i = 0; i < resultJsonInfo.length; i++) {

                    var num = $(thiz).parent().children('p');
                    if(num && num.length > 0){
                        num.remove();
                    }
                    $(thiz).parent().find("input[name='" + inputName + "']").val(resultJsonInfo[i].data.fileId);
                    //获取文件ID
                    var fileId = resultJsonInfo[i].data.fileId;
                    //获取文件名称
                    var fileName = resultJsonInfo[i].data.fileName;

                    var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                    spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
                    spanHtml += "<font color=\"blue\">" + fileName + "</font></a>";
                    spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + fileId + "','"+inputName+"',this);\" ><font color=\"red\">删除</font></a></p>";
                    $(thiz).parent().find("input[name='"+inputName+"']").before(spanHtml);
                }
            }
            art.dialog.removeData("resultJsonInfo");
        }
    }, false);
}

function delUploadFile(fileId,inputName,thiz){
    art.dialog.confirm("您确定要删除该文件吗?", function() {
        //移除目标span
        $("#"+fileId).remove();
        var fileid = $("input[name='" + inputName + "']").val();
        var arrayIds=fileid.split(";");
        for(var i=0;i<arrayIds.length;i++){
            if(arrayIds[i]==fileId){
                arrayIds.splice(i,1);
                break;
            }
        }
        $(thiz).parent().find("input[name='" + inputName + "']").val(arrayIds.join(";"));
    });
}

function openUploadCtrl(inputName,thiz) {
    //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
    art.dialog.open('fileTypeController.do?openUploadDialog&attachType=attachToImage&materType=image&busTableName=T_BDC_WT', {
        title: "上传(附件)",
        width: "650px",
        height: "300px",
        lock: true,
        resize: false,
        close: function(){
            var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
            if(uploadedFileInfo){
                if(uploadedFileInfo.fileId){
                    var fileType = 'gif,jpg,jpeg,bmp,png';
                    var attachType = 'attach';
                    if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
                        attachType = "image";
                    }
                    var num = $(thiz).parent().children('p');
                    if(num && num.length > 0){
                        num.remove();
                    }
                    $(thiz).parent().find("input[name='" + inputName + "']").val(uploadedFileInfo.fileId);
                    var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server
                        + "download/fileDownload?attachId="+uploadedFileInfo.fileId
                        +"&attachType="+attachType+"\" ";
                    spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                    spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                    spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('" + uploadedFileInfo.fileId + "','"+inputName+"',this);\" ><font color=\"red\">删除</font></a></p>"
                    $(thiz).parent().find("input[name='"+inputName+"']").before(spanHtml);
                }
            }
            art.dialog.removeData("uploadedFileInfo");
        }
    });
}






