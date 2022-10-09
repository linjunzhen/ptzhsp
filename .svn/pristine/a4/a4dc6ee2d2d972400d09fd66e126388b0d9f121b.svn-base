


//提交流程
function submitFlow(formId) {
    //先判断表单是否验证通过
    var validateResult =$("#"+formId).validationEngine("validate");
    if(validateResult){
        //获取权利人信息JSON
    	getPowPeopleJson();
        if ($(".powerpeopleinfo_tr").length < 1 ) {
            parent.art.dialog({
                content : "请填写并保存权利人信息！",
                icon : "error",
                ok : true
            });
            return;
        }
        
        //var loginQYZH = $("input[name='USER_NAME']").val();//登录的闽政通账号        
        var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
        if(submitMaterFileJson||submitMaterFileJson==""){
            //获取流程信息对象JSON
            var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
            //将其转换成JSON对象
            var flowSubmitObj = JSON2.parse(EFLOW_FLOWOBJ);

          /*  if (flowSubmitObj) {
                if(!flowSubmitObj.busRecord){
                    parent.art.dialog({
                        content: "请先暂存后提交。",
                        icon:"warning",
                        ok: true
                    });
                    return;
                }else{
                	parent.art.dialog({
                        content: "系统调整中，暂停受理, 请稍后再申请",
                        icon:"warning",
                        ok: true
                    });
                    return;
                }
            }*/
            $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);

            if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
                /*判断此业务是否已被办理过*/
                var bdcdyh = $("input[name='ESTATE_NUM']").val();
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
  //权利人信息JON
  getPowPeopleJson();
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

//初始化受理信息
function initSlxx() {
    var SQRMC = $("input[name='SQRMC']").val();
    $("input[name='APPLICANT_UNIT']").val(SQRMC);
}


//初始化权利人信息
function initAutoTable(flowSubmitObj){
    var powerpeopleinfoJson = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
    if(null != powerpeopleinfoJson && '' != powerpeopleinfoJson && '[]' != powerpeopleinfoJson){
        var powerpeopleItems = JSON.parse(powerpeopleinfoJson);
        initPowPeople(powerpeopleItems);
    } 
}

//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx, name) {
    if ( zjlx == "SF" || zjlx == "身份证") {
        $("input[name='" + name + "']").addClass(",custom[vidcard]");
    } else {
        $("input[name='" + name + "']").removeClass(",custom[vidcard]");
    }
}


//权利人性质动态更新（个人与法人）
function qlrChangeFun(c,flag){
    if(c == '1'){//个人
        if(flag){
            $("select[name='POWERPEOPLEIDTYPE'] option[value='SF']").prop("selected", true);
        }
        $("select[name='POWERPEOPLEIDTYPE']").removeAttr("disabled");
        $("#POWERPEOPLEMOBILE_font").attr("style","");        
        $("input[name='POWERPEOPLEMOBILE']").addClass("validate[required]");
        $("#qlr_gr").attr("style","");
        $("#qlr_qy").attr("style","display:none;");
        //法定代表人&代理人信息非必填
        $("#POWLEGALNAME_font").attr("style","display:none;");
        $("#POWLEGALTEL_font").attr("style","display:none;");
        $("#POWLEGALIDTYPE_font").attr("style","display:none;");
        $("#POWLEGALIDNUM_font").attr("style","display:none;");        
        $("input[name='POWLEGALNAME']").removeClass("validate[required]");
        $("input[name='POWLEGALTEL']").removeClass("validate[required,custom[mobilePhoneLoose]]");
        $("input[name='POWLEGALTEL']").addClass("validate[,custom[mobilePhoneLoose]]");
        $("select[name='POWLEGALIDTYPE']").removeClass("validate[required]");
        $("input[name='POWLEGALIDNUM']").removeClass("validate[required]");
        
        $("#POWAGENTNAME_font").attr("style","display:none;");
        $("#POWAGENTIDTYPE_font").attr("style","display:none;");
        $("#POWAGENTIDNUM_font").attr("style","display:none;");
        $("#POWAGENTTEL_font").attr("style","display:none;");        
        $("input[name='POWAGENTNAME']").removeClass("validate[required]");
        $("select[name='POWAGENTIDTYPE']").removeClass("validate[required]");
        $("input[name='POWAGENTIDNUM']").removeClass("validate[required]");
        $("input[name='POWAGENTTEL']").removeClass("validate[required,custom[mobilePhoneLoose]]");
        $("input[name='POWAGENTTEL']").addClass("validate[,custom[mobilePhoneLoose]]");
    }else{//法人
        $("select[name='POWERPEOPLEIDTYPE'] option[value='YYZZ']").prop("selected", true);//营业执照
        //$("select[name='POWERPEOPLEIDTYPE']").attr("disabled","disabled");        
        $("#POWERPEOPLEMOBILE_font").attr("style","display:none;");        
        $("input[name='POWERPEOPLEMOBILE']").removeClass("validate[required]");
        $("#qlr_qy").attr("style","");
        $("#qlr_gr").attr("style","display:none;");
        //法定代表人&代理人信息必填
        $("#POWLEGALNAME_font").attr("style","");
        $("#POWLEGALTEL_font").attr("style","");
        $("#POWLEGALIDTYPE_font").attr("style","");
        $("#POWLEGALIDNUM_font").attr("style","");        
        $("input[name='POWLEGALNAME']").addClass("validate[required]");    
        $("input[name='POWLEGALTEL']").removeClass("validate[,custom[mobilePhoneLoose]]");
        $("input[name='POWLEGALTEL']").addClass("validate[required,custom[mobilePhoneLoose]]");
        $("select[name='POWLEGALIDTYPE']").addClass("validate[required]");
        $("input[name='POWLEGALIDNUM']").addClass("validate[required]");
        
        $("#POWAGENTNAME_font").attr("style","");
        $("#POWAGENTIDTYPE_font").attr("style","");
        $("#POWAGENTIDNUM_font").attr("style","");
        $("#POWAGENTTEL_font").attr("style","");        
        $("input[name='POWAGENTNAME']").addClass("validate[required]");
        $("select[name='POWAGENTIDTYPE']").addClass("validate[required]");
        $("input[name='POWAGENTIDNUM']").addClass("validate[required]");
        $("input[name='POWAGENTTEL']").removeClass("validate[,custom[mobilePhoneLoose]]");
        $("input[name='POWAGENTTEL']").addClass("validate[required,custom[mobilePhoneLoose]]");
    }
}


/**
 * 标题附件上传对话框(申请人信息)
 */
function openXxcjFileUploadDialog(){

    //定义上传的人员的ID
    var uploadUserId = $("input[name='uploadUserId']").val();
    //定义上传的人员的NAME
    var uploadUserName = $("input[name='uploadUserName']").val();
    var EFLOW_BUSTABLENAME = $("input[name='EFLOW_BUSTABLENAME']").val();
    var materType = '*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.rar;';
    //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
    art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType='
        +materType+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName)+'&busTableName='+EFLOW_BUSTABLENAME, {
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
                    var fileurl=$("input[name='RESULT_FILE_URL']").val();
                    var fileid=$("input[name='RESULT_FILE_ID']").val();
                    if(fileurl!=""&&fileurl!=null){
                        $("input[name='RESULT_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
                        $("input[name='RESULT_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
                    }else{
                        $("input[name='RESULT_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
                        $("input[name='RESULT_FILE_ID']").val(uploadedFileInfo.fileId);
                    }
                    var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server
                        + "download/fileDownload?attachId="+uploadedFileInfo.fileId
                        +"&attachType="+attachType+"\" ";
                    spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                    spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                    spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"');\" ><font color=\"red\">删除</font></a></p>"
                    $("#fileListDiv").append(spanHtml);
                }
            }
            art.dialog.removeData("uploadedFileInfo");
        }
    });
}


//删除附件
function delUploadFile(fileId,attacheKey){
    //AppUtil.delUploadMater(fileId,attacheKey);
    art.dialog.confirm("您确定要删除该文件吗?", function() {
        //移除目标span
        $("#"+fileId).remove();
        var fileurl=$("input[name='RESULT_FILE_URL']").val();
        var fileid=$("input[name='RESULT_FILE_ID']").val();
        var arrayIds=fileid.split(";");
        var arrayurls=fileurl.split(";");
        for(var i=0;i<arrayIds.length;i++){
            if(arrayIds[i]==fileId){
                arrayIds.splice(i,1);
                arrayurls.splice(i,1);
                break;
            }
        }
        $("input[name='RESULT_FILE_URL']").val(arrayurls.join(";"));
        $("input[name='RESULT_FILE_ID']").val(arrayIds.join(";"));
    });
}