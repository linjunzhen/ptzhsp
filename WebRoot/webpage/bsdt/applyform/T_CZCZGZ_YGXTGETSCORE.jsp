<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript">
	$(function(){
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
    		$("#EXEID").append(flowSubmitObj.EFLOW_EXEID);
    		 //初始化表单字段权限控制
    		FlowUtil.initFormFieldRightControl(currentNodeFieldRights,"T_CZCZGZ_YGXTGETSCORE_FORM");
    		//初始化表单字段值
    		if(flowSubmitObj.busRecord){
    			FlowUtil.initFormFieldValue(flowSubmitObj.busRecord,"T_CZCZGZ_YGXTGETSCORE_FORM");
				if(flowSubmitObj.busRecord.RUN_STATUS!=0){

                    $(".tab_tk_pro input").each(function(index){
                        $(this).attr("disabled","disabled");
                    });
                    $(".tab_tk_pro select").each(function(index){
                        $(this).attr("disabled","disabled");
                    });

                    $(".tab_tk_pro textarea").each(function(index){
                        $(this).attr("disabled","disabled");
                    });

					$("#taxiLisenceInfo input").each(function(index){
						$(this).attr("disabled","disabled");
					});
					$("#taxiLisenceInfo select").each(function(index){
						$(this).attr("disabled","disabled");
					});

                    $("#taxiLisenceInfo textarea").each(function(index){
                        $(this).attr("disabled","disabled");
                    });

                    if("公安审核" == flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
                        $("textarea[name='ZGZ_APPLY_AUDITOPNION']").removeAttr("disabled");
                        $("textarea[name='ZGZ_APPLY_AUDITOPNION']").removeAttr("readonly");

                        $("input[name='IS_THREE_YEAR']").removeAttr("disabled");
                        $("input[name='IS_THREE_YEAR']").removeAttr("readonly");

                        $("input[name='IS_TRAFFIC_CRIME']").removeAttr("disabled");
                        $("input[name='IS_TRAFFIC_CRIME']").removeAttr("readonly");

                        $("input[name='IS_DANGEROUS_DRIVING']").removeAttr("disabled");
                        $("input[name='IS_DANGEROUS_DRIVING']").removeAttr("readonly");

                        $("input[name='IS_DUI']").removeAttr("disabled");
                        $("input[name='IS_DUI']").removeAttr("readonly");

                        $("input[name='IS_THREEWEEK_TWELVESCORE']").removeAttr("disabled");
                        $("input[name='IS_THREEWEEK_TWELVESCORE']").removeAttr("readonly");

                        $("input[name='IS_DRUGS']").removeAttr("disabled");
                        $("input[name='IS_DRUGS']").removeAttr("readonly");

                        $("input[name='IS_VIOLENT_CRIME']").removeAttr("disabled");
                        $("input[name='IS_VIOLENT_CRIME']").removeAttr("readonly");

                    }
                    // $("#taxiLisenceInfo textarea").each(function(index){
                    //     $(this).attr("disabled","disabled");
                    // });

				}

                var glbmOpnion = "${busRecord.ZGZ_GLBM_OPNION}";
                if("不同意发证"==glbmOpnion){
                    $("#disagree_tr").attr("style","");
                    $("#agree_tr").attr("style","display:none;");
                }else{
                    $("#agree_tr").attr("style","");
                    $("#disagree_tr").attr("style","display:none;");
                }


                //上传审查附件 回显
                var fileids= "${busRecord.ZGZ_APPLY_UPLOADFILEIDS}";
                if(fileids) {
                    getScFileList(fileids);
                }
                //请求 运管成绩接口
                if("核发" == flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
                    //身份证
                    var IDCARD = "${busRecord.ZGZ_APPLY_CARDNUM}";
                    //姓名
                    var NAME = "${busRecord.ZGZ_APPLY_USERNAME}";
                    //考试时间
                    <%--var EXAMDATE = "${busRecord.ZGZ_APPLY_UPLOADFILEIDS}";--%>
                    //考试资格类别
                    <%--var SCOPECODE = "${busRecord.ZGZ_APPLY_TYPE}" == 0?"巡游出租车驾驶员":"网络预约出租车驾驶员";--%>
                    var SCOPECODE = "${busRecord.ZGZ_APPLY_TYPE}";
                    // getYGScore(IDCARD,NAME,"",SCOPECODE);
                }
    		}else{
    			$("input[name='SBMC']").val('${serviceItem.ITEM_NAME}');
    		}




    	}
		//初始化材料列表
		//AppUtil.initNetUploadMaters({
		//	busTableName:"T_BSFW_JBJINFO"
		//});
        if($("input[name='FINISH_GETTYPE']:checked").val()=='02'){
            $("#addr").attr("style","");
            $("#addressee").attr("style","");
            $("#addrmobile").attr("style","");
            $("#addrprov").attr("style","");
            $("#addrcity").attr("style","");
            $("#addrpostcode").attr("style","");
            $("#addrremarks").attr("style","");
        }
        $("input[name='ZGZ_GLBM_OPNION']").click(function(){
            var radio = document.getElementsByName("ZGZ_GLBM_OPNION");
            for (i=0; i<radio.length; i++) {
                if (radio[i].checked) {
                    var str=radio[i].value;
                    if("不同意发证"==str){
                        $("#disagree_tr").attr("style","");
                        $("#agree_tr").attr("style","display:none;");
                        $("input[name='ZGZ_DISAGRE" +
                            "E_REASON']").removeAttr("disabled");
                        $("input[name='ZGZ_DISAGREE_REASON']").removeAttr("readonly");
                        $("input[name='ZGZ_DISAGREE_REASON']").attr("class","tab_text validate[required]");

                    }else{
                        $("#agree_tr").attr("style","");
                        $("#disagree_tr").attr("style","display:none;");
                        $("input[name='ZGZ_DISAGREE_REASON']").attr("class","tab_text");
                    }
                }
            }

        });


        $("input[name='FINISH_GETTYPE']").click(function(){
            var radio = document.getElementsByName("FINISH_GETTYPE");
            for (i=0; i<radio.length; i++) {
                if (radio[i].checked) {
                    var str=radio[i].value;
                    if("02"==str){
                        $("#addr").attr("style","");
                        $("#addressee").attr("style","");
                        $("#addrmobile").attr("style","");
                        $("#addrprov").attr("style","");
                        $("#addrcity").attr("style","");
                        $("#addrpostcode").attr("style","");
                        $("#addrremarks").attr("style","");
                    }else{
                        $("#addr").attr("style","display:none;");
                        $("#addressee").attr("style","display:none;");
                        $("#addrmobile").attr("style","display:none;");
                        $("#addrprov").attr("style","display:none;");
                        $("#addrcity").attr("style","display:none;");
                        $("#addrpostcode").attr("style","display:none;");
                        $("#addrremarks").attr("style","display:none;");
                    }
                }
            }
        });
        $("#SQRMC").change(function(){
            var sqrmc = $('input[name="SQRMC"]').val();
            $('input[name="ZGZ_APPLY_USERNAME"]').val(sqrmc);
        });
        $("#SQRXB").change(function(){
            var optionValue=$("#SQRXB option:selected").val();
            $("#ZGZ_APPLY_SEX option[value='"+optionValue+"']").attr("selected","selected");
        });


        $('input[name="SQRSFZ"]').change(function(){
            //证件类型选中的是身份证
            var optionValue=$("#SQRZJLX option:selected").val();
            if("SF" == optionValue){
                var sqrsfz = $('input[name="SQRSFZ"]').val();
                $('input[name="ZGZ_APPLY_CARDNUM"]').val(sqrsfz);
            }
        });
        $('input[name="SQRSJH"]').change(function(){
            var sqrsjh = $('input[name="SQRSJH"]').val();
            $('input[name="ZGZ_APPLY_MOBILENUMBER"]').val(sqrsjh);
        });
        $('input[name="SQRLXDZ"]').change(function(){
            var sqrlxdz = $('input[name="SQRLXDZ"]').val();
            $('input[name="ZGZ_APPLY_ADDRESS"]').val(sqrlxdz);
        });







	});

    /**
     * 上传审查附件 回显
     * @param fileid
     */
    function getScFileList(fileids){
        var param = {};
        param.fileIds = fileids;
        $.ajax({
            type: "POST",
            url: "executionController.do?getResultFiles",
            data : param,
            async: false,
            success: function (result) {
                if(result=="websessiontimeout"){
                    return;
                }
                $("#scFileListDiv").html("");
                var newhtml = "";
                var list=result.rows;
                for(var i=0; i<list.length; i++){
                    newhtml+='<p id=\''+list[i].FILE_ID+'\' style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
                    newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+=list[i].FILE_NAME+'</a>';
                    newhtml+='<a href="javascript:void(0);" onclick="delSCUploadFile(\''+list[i].FILE_ID+'\',\''+list[i].ATTACH_KEY+'\');"><font color="red">删除</font></a>';
                    newhtml+='</p>';
                }
                $("#scFileListDiv").html(newhtml);
            }
        });



    }


    /***
     *
     * @param flowSubmitObj
     * @returns {null|*}
     * @constructor
     */
	function FLOW_SubmitFun(flowSubmitObj){
        var IS_HANDUP = flowSubmitObj['IS_HANDUP'];//是否挂起
        if("1" == IS_HANDUP){
            setAttr("挂起",flowSubmitObj);
        }else{
            setAttr("提交",flowSubmitObj);
        }

		 //先判断表单是否验证通过
		 var validateResult =$("#T_CZCZGZ_YGXTGETSCORE_FORM").validationEngine("validate");
		 if(validateResult){
			 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_CZCZGZ_YGXTGETSCORE_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
                 flowSubmitObj.IS_PASS="1";
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
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson();
		 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
		 //先获取表单上的所有值
		 var formData = FlowUtil.getFormEleData("T_CZCZGZ_YGXTGETSCORE_FORM");
		 for(var index in formData){
			 flowSubmitObj[eval("index")] = formData[index];
		 }

		return flowSubmitObj;

	}

	function FLOW_BackFun(flowSubmitObj){
        flowSubmitObj.IS_PASS="0";
		return flowSubmitObj;
	}

    //退件
    function FLOW_NotAccept(flowSubmitObj){
        flowSubmitObj.ZGZ_APPLY_STATUS="已退件";
        return flowSubmitObj;
    }


    function setAttr(action,flowSubmitObj){
        if("提交"==action){
            //没有成绩不能核发
            if("核发" == flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
                $("input[name='ZGZ_EXAM_USER']").removeAttr("disabled");
                $("input[name='ZGZ_EXAM_USER']").removeAttr("readonly");
                $("input[name='ZGZ_EXAM_USER']").attr("class","tab_text validate[required]");

                $("input[name='ZGZ_AREAEXAM_USER']").removeAttr("disabled");
                $("input[name='ZGZ_AREAEXAM_USER']").removeAttr("readonly");
                $("input[name='ZGZ_AREAEXAM_USER']").attr("class","tab_text validate[required]");

                $("input[name='ZGZ_EXAM_TIME']").removeAttr("disabled");
                $("input[name='ZGZ_EXAM_TIME']").removeAttr("readonly");
                $("input[name='ZGZ_EXAM_TIME']").attr("class","tab_text validate[required]");

                $("input[name='ZGZ_AREAEXAM_TIME']").removeAttr("disabled");
                $("input[name='ZGZ_AREAEXAM_TIME']").removeAttr("readonly");
                $("input[name='ZGZ_AREAEXAM_TIME']").attr("class","tab_text validate[required]");

                $("input[name='ZGZ_EXAM_SCORE']").removeAttr("disabled");
                $("input[name='ZGZ_EXAM_SCORE']").removeAttr("readonly");
                $("input[name='ZGZ_EXAM_SCORE']").attr("class","tab_text validate[required]");

                $("input[name='ZGZ_AREAEXAM_SCORE']").removeAttr("disabled");
                $("input[name='ZGZ_AREAEXAM_SCORE']").removeAttr("readonly");
                $("input[name='ZGZ_AREAEXAM_SCORE']").attr("class","tab_text validate[required]");

            }
        }else if("挂起"==action){
            //没有成绩不能核发
            if("核发" == flowSubmitObj.EFLOW_CURUSEROPERNODENAME){
                $("input[name='ZGZ_EXAM_USER']").attr("class","tab_text");
                $("input[name='ZGZ_AREAEXAM_USER']").attr("class","tab_text");
                $("input[name='ZGZ_EXAM_TIME']").attr("class","tab_text");
                $("input[name='ZGZ_AREAEXAM_TIME']").attr("class","tab_text");
                $("input[name='ZGZ_EXAM_SCORE']").attr("class","tab_text");
                $("input[name='ZGZ_AREAEXAM_SCORE']").attr("class","tab_text");
            }
        }


    }


    /**
     * 标题附件上传对话框
     */
    function openSCFileUploadDialog(){
        //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
        art.dialog.open('fileTypeController.do?openMultifileUploadDialog&attachType=attachToImage&materType=image&busTableName=T_CZCZGZ_YGXTGETSCORE', {
            title: "上传(附件)",
            width: "820px",
            height: "440px",
            lock: true,
            resize: false,
            close: function(){
                var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
                if(uploadedFileInfo){
                    for(var i=0;i<uploadedFileInfo.length;i++){
                        if(uploadedFileInfo[i].fileId){
                            var fileType = 'gif,jpg,jpeg,bmp,png';
                            var attachType = 'attach';
                            if(fileType.indexOf(uploadedFileInfo[i].fileSuffix)>-1){
                                attachType = "image";
                            }
                            var fileid=$("input[name='ZGZ_APPLY_UPLOADFILEIDS']").val();
                            var downloadUrl = "";
                            if(fileid!=""&&fileid!=null){
                                $("input[name='ZGZ_APPLY_UPLOADFILEIDS']").val(fileid+";"+uploadedFileInfo[i].fileId);
                            }else{
                                $("input[name='ZGZ_APPLY_UPLOADFILEIDS']").val(uploadedFileInfo[i].fileId);
                            }
                            var spanHtml = "<p id=\""+uploadedFileInfo[i].fileId+"\"><a href=\""+__file_server
                                + "download/fileDownload?attachId="+uploadedFileInfo[i].fileId
                                +"&attachType="+attachType+"\" ";
                            spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
                            spanHtml +="<font color=\"blue\">"+uploadedFileInfo[i].fileName+"</font></a>"
                            spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delSCUploadFile('"+uploadedFileInfo[i].fileId+"');\" ><font color=\"red\">删除</font></a></p>"
                            $("#scFileListDiv").append(spanHtml);
                        }
                    }
                }
                art.dialog.removeData("uploadedFileInfo");
            }
        });
    }
    function delSCUploadFile(fileId,attacheKey){
        art.dialog.confirm("您确定要删除该文件吗?", function() {
            //移除目标span
            $("#"+fileId).remove();
            var fileid=$("input[name='ZGZ_APPLY_UPLOADFILEIDS']").val();
            var arrayIds=fileid.split(";");
            for(var i=0;i < arrayIds.length;i++){
                if(arrayIds[i]==fileId){
                    arrayIds.splice(i,1);
                    break;
                }
            }
            $("input[name='ZGZ_APPLY_UPLOADFILEIDS']").val(arrayIds.join(";"));
        });
    }

    /**
     * 运管系统 查询-平潭出租车成绩同步接口
     * 测试地址：http://ys.hztech.cn:8888/baoming-interface/resource/SYSBL.CTOP.CRTMIS.INTERFACE.EFUZHOUCMPSN.GETSAPPLYSTATUS?
     * _app_id_=EFUZHOUCMPSN&_license_key_=D327B40E5DFE6F99E0531001A80ABC1A
     * @param _format_ ：json （此参数非必写 不传返回格式为xml）
     * @param IDCARD ：身份证号码 必填
     * @param NAME ：姓名  必填
     * @param EXAMDATE ：考试日期  选填
     * @param SCOPECODE ：考试资格类别 必填
     *
     */
    function getYGScore(IDCARD,NAME,EXAMDATE,SCOPECODE){
        debugger;
        var param = {};
        param._format_ = "json";
        param.IDCARD = IDCARD;
        param.NAME = NAME;
        param.SCOPECODE = SCOPECODE;
        if(EXAMDATE != "")
            param.EXAMDATE = EXAMDATE;

        $.ajax({
            type: "POST",
            url: "http://ys.hztech.cn:8888/baoming-interface/resource/SYSBL.CTOP.CRTMIS.INTERFACE.EFUZHOUCMPSN.GETSAPPLYSTATUS?_app_id_=EFUZHOUCMPSN&_license_key_=D327B40E5DFE6F99E0531001A80ABC1A",
            data : param,
            async: false,
            success: function (result) {
                if(result.output.head.retCode == 0){
                    var ZGZ_EXAM_USER = "11";
                    var ZGZ_AREAEXAM_USER = "22";
                    var ZGZ_EXAM_TIME = "2022-01-12";
                    var ZGZ_AREAEXAM_TIME = "2022-01-12";
                    var ZGZ_EXAM_SCORE = "33";
                    var ZGZ_AREAEXAM_SCORE = "44";
                    $("input[name='ZGZ_EXAM_USER']").val(ZGZ_EXAM_USER);
                    $("input[name='ZGZ_EXAM_USER']").removeAttr("disabled");
                    $("input[name='ZGZ_EXAM_USER']").removeAttr("readonly");
                    $("input[name='ZGZ_EXAM_USER']").attr("class","tab_text validate[required]");

                    $("input[name='ZGZ_AREAEXAM_USER']").val(ZGZ_AREAEXAM_USER);
                    $("input[name='ZGZ_AREAEXAM_USER']").removeAttr("disabled");
                    $("input[name='ZGZ_AREAEXAM_USER']").removeAttr("readonly");
                    $("input[name='ZGZ_AREAEXAM_USER']").attr("class","tab_text validate[required]");

                    $("input[name='ZGZ_EXAM_TIME']").val(ZGZ_EXAM_TIME);
                    $("input[name='ZGZ_EXAM_TIME']").removeAttr("disabled");
                    $("input[name='ZGZ_EXAM_TIME']").removeAttr("readonly");
                    $("input[name='ZGZ_EXAM_TIME']").attr("class","tab_text validate[required]");

                    $("input[name='ZGZ_AREAEXAM_TIME']").val(ZGZ_AREAEXAM_TIME);
                    $("input[name='ZGZ_AREAEXAM_TIME']").removeAttr("disabled");
                    $("input[name='ZGZ_AREAEXAM_TIME']").removeAttr("readonly");
                    $("input[name='ZGZ_AREAEXAM_TIME']").attr("class","tab_text validate[required]");

                    $("input[name='ZGZ_EXAM_SCORE']").val(ZGZ_EXAM_SCORE);
                    $("input[name='ZGZ_EXAM_SCORE']").removeAttr("disabled");
                    $("input[name='ZGZ_EXAM_SCORE']").removeAttr("readonly");
                    $("input[name='ZGZ_EXAM_SCORE']").attr("class","tab_text validate[required]");

                    $("input[name='ZGZ_AREAEXAM_SCORE']").val(ZGZ_AREAEXAM_SCORE);
                    $("input[name='ZGZ_AREAEXAM_SCORE']").removeAttr("disabled");
                    $("input[name='ZGZ_AREAEXAM_SCORE']").removeAttr("readonly");
                    $("input[name='ZGZ_AREAEXAM_SCORE']").attr("class","tab_text validate[required]");


                }else{
                    parent.art.dialog({
                        content: "获取成绩失败",
                        icon:"error",
                        ok: true
                    });
                }
            }
        });



    }

</script>
</head>

<body>
    <div class="detail_title">
        <h1>
        ${serviceItem.ITEM_NAME}
        </h1>
    </div>
   <form id="T_CZCZGZ_YGXTGETSCORE_FORM" method="post" >
    <%--===================重要的隐藏域内容=========== --%>
    <input type="hidden" name="uploadUserId" value="${sessionScope.curLoginUser.userId}"/>
    <input type="hidden" name="uploadUserName" value="${sessionScope.curLoginUser.fullname}"/>
    <input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
    <input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
    <input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
    <input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" />
    <input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" />
    <input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
    <input type="hidden" name="EFLOW_SUBMITMATERFILEJSON" />
    <input type="hidden" name="BELONG_DEPT" value="${serviceItem.SSBMBM}" />
	<input type="hidden" name="APPROVAL_ITEMS" value="${serviceItem.ITEM_NAME}" />
	<input type="hidden" name="BELONG_DEPTNAME" value="${serviceItem.SSBMMC}" />
	<input type="hidden" name="SXLX" value="${serviceItem.SXLX}" />
	<input type="hidden" name="PROMISE_DATE" value="${serviceItem.CNQXGZR}" />
    <%--===================重要的隐藏域内容=========== --%>
    <%--业务表隐藏字段--%>
    <input type="hidden" name="YW_ID" value="${busRecord.YW_ID}" />
    <input type="hidden" name="ZGZ_APPLY_UPLOADFILEIDS" value="${busRecord.ZGZ_APPLY_UPLOADFILEIDS}" />
    <%--业务表隐藏字段--%>

	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">基本信息</th>
		</tr>
		<tr>
			<td > 审批事项：</td>
			<td >${serviceItem.ITEM_NAME}</td>
            <td class="tab_width"> 审批类型：</td>
            <td >${serviceItem.SXLX}</td>
        </tr>
		<tr>
			<td> 所属部门：</td>
			<td >${serviceItem.SSBMMC}</td>
			<td class="tab_width"> 承诺时间(工作日)：</td>
			<td >${serviceItem.CNQXGZR}</td>
		</tr>
		<tr>
			<td class="tab_width"><font class="tab_color">*</font> 申报名称：</td>
			<td colspan="3"><input type="text" class="tab_text validate[required]"
				style="width: 70%" maxlength="220" value="${busRecord.SBMC}"
				name="SBMC" /><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
		</tr>
		<tr>
			<td class="tab_width"> 申报号：</td>
			<td id = "EXEID"></td>
			<td></td><td></td>
		</tr>
	</table>



	<%--开始引入公用申报对象--%>
	<jsp:include page="./applyuserinfo.jsp" />
	<%--结束引入公用申报对象 --%>

	<div class="tab_height" ></div>
    <table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="taxiLisenceInfo">
        <tr>
            <th colspan="6">出租车汽车驾驶员从业资格证申请表</th>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>姓名：</td>
            <td><input type="text" class="tab_text validate[required,custom[equalsSgsStr]]"
                       maxlength="16" style="width: 33%;" id="ZGZ_APPLY_USERNAME"
                       name="ZGZ_APPLY_USERNAME" value="${busRecord.ZGZ_APPLY_USERNAME}"  /></td>
            <td class="tab_width"><font class="tab_color">*</font>性别</td>
            <td>
                <eve:eveselect clazz="tab_text validate[required]"
                               dataParams="sex" value="${busRecord.ZGZ_APPLY_SEX}"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择性别" name="ZGZ_APPLY_SEX" id="ZGZ_APPLY_SEX"></eve:eveselect>
            </td>
            <td colspan="2">
                <a <c:if test="${exeId == null || exeId == 'null' || exeId == ''}">href="attachFiles/spmb/出租汽车驾驶员从业资格证申请表（模板）.doc"</c:if>
                   <c:if test="${exeId != null && exeId != 'null' && exeId != ''}">href="TaxiLicenseController/downloadApplyModel.do?exeId=${exeId}"</c:if>
                   style="color: blue;margin-right: 5px;" target="_blank"><font color="blue">出租汽车驾驶员从业资格证申请表（模板）.docx</font></a>
            </td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>住址：</td>
            <td colspan="5"><input type="text" class="tab_text"
                       maxlength="100" style="width: 50%;" id="ZGZ_APPLY_ADDRESS"
                       name="ZGZ_APPLY_ADDRESS" value="${busRecord.ZGZ_APPLY_ADDRESS}"  /></td>
        </tr>
        <tr>
            <td class="tab_width"><font class="tab_color">*</font>电话：</td>
            <td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11"
                       name="ZGZ_APPLY_MOBILENUMBER" value="${execution.SQRSJH}"  /></td>


            <td class="tab_width"><font class="tab_color">*</font>身份证号</td>
            <td><input type="text" class="tab_text validate[custom[vidcard]]"
                       maxlength="20" style="width: 33%;" id="ZGZ_APPLY_CARDNUM"
                       name="ZGZ_APPLY_CARDNUM" value="${busRecord.ZGZ_APPLY_CARDNUM}"  /></td>
            </td>

            <td class="tab_width"><font class="tab_color">*</font>培训单位</td>
            <td><input type="text" class="tab_text validate[required]"
                       maxlength="30" style="width: 33%;" id="ZGZ_APPLY_TRAININGUNIT"
                       name="ZGZ_APPLY_TRAININGUNIT" value="${busRecord.ZGZ_APPLY_TRAININGUNIT}"  /></td>
            </td>
        </tr>

        <tr>
            <td class="tab_width"><font class="tab_color">*</font>机动车驾驶证准驾车型：</td>
            <td><eve:eveselect clazz="tab_text validate[required]"
                               dataParams="licenseType" value="${busRecord.ZGZ_APPLY_ALLOWCARTYPE}"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择机动车驾驶证准驾车型" name="ZGZ_APPLY_ALLOWCARTYPE" id="ZGZ_APPLY_ALLOWCARTYPE"></eve:eveselect></td>

            <td class="tab_width"><font class="tab_color">*</font>初领机动车驾驶证日期</td>
            <td><input type="text" name="ZGZ_APPLY_FIRSTGETJSZTIME"
                       class="laydate-icon validate[required]"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                       value="${busRecord.ZGZ_APPLY_FIRSTGETJSZTIME}"
                       style="width:170px; height:26px;" />
            </td>

            <td class="tab_width"><font class="tab_color">*</font>申请类别</td>
            <td><eve:eveselect clazz="tab_text validate[required]"
                               dataParams="licenseApplyType" value="${busRecord.ZGZ_APPLY_TYPE}"
                               dataInterface="dictionaryService.findDatasForSelect"
                               defaultEmptyText="选择申请类别" name="ZGZ_APPLY_TYPE" id="ZGZ_APPLY_TYPE"></eve:eveselect>
            </td>
        </tr>


        <tr>
            <td class="tab_width">背景核查意见：</td>
            <td colspan="5">
                <textarea name="ZGZ_APPLY_AUDITOPNION" style="width: 50%;"  cols="80" rows="5" maxlength="500"
                          id="ZGZ_APPLY_AUDITOPNION" class="validate[maxSize[500]]" value="${busRecord.ZGZ_APPLY_AUDITOPNION}"></textarea>
            </td>
        </tr>
        <tr>
            <td class="tab_width">
                <input type="radio" class="radio" name="IS_THREE_YEAR" value="1" <c:if test="${busRecord.IS_THREE_YEAR=='1'}">checked="checked"</c:if> />已
                <input type="radio" class="radio" name="IS_THREE_YEAR" value="0" <c:if test="${busRecord.IS_THREE_YEAR=='0'}">checked="checked"</c:if> />未
                <%--<eve:excheckbox
                    dataInterface="dictionaryService.findDatasForSelect"
                    name="IS_THREE_YEAR" width="100px;" value="${busRecord.IS_THREE_YEAR}"
                    dataParams="YesOrNo" >
                  </eve:excheckbox>--%>
            </td>
            <td colspan="5">
                <p>取得相应准驾车型机动车驾驶证并具有3年以上驾驶经历</p>
            </td>
        </tr>
        <tr>
            <td class="tab_width">
                <input type="radio" class="radio" name="IS_TRAFFIC_CRIME" value="1" <c:if test="${busRecord.IS_TRAFFIC_CRIME=='1'}">checked="checked"</c:if> />有
                <input type="radio" class="radio" name="IS_TRAFFIC_CRIME" value="0" <c:if test="${busRecord.IS_TRAFFIC_CRIME=='0'}">checked="checked"</c:if> />无
                <%--<eve:excheckbox
                        dataInterface="dictionaryService.findDatasForSelect"
                        name="IS_TRAFFIC_CRIME" width="100px;" value="${busRecord.IS_TRAFFIC_CRIME}"
                        dataParams="YesOrNo" >
                </eve:excheckbox>--%>
            </td>
            <td colspan="5">
                <p>交通肇事犯罪</p>
            </td>
        </tr>
        <tr>
            <td class="tab_width">
                <input type="radio" class="radio" name="IS_DANGEROUS_DRIVING" value="1" <c:if test="${busRecord.IS_DANGEROUS_DRIVING=='1'}">checked="checked"</c:if> />有
                <input type="radio" class="radio" name="IS_DANGEROUS_DRIVING" value="0" <c:if test="${busRecord.IS_DANGEROUS_DRIVING=='0'}">checked="checked"</c:if> />无
                <%--<eve:excheckbox
                        dataInterface="dictionaryService.findDatasForSelect"
                        name="IS_DANGEROUS_DRIVING" width="100px;" value="${busRecord.IS_DANGEROUS_DRIVING}"
                        dataParams="YesOrNo" >
                </eve:excheckbox>--%>
            </td>
            <td colspan="5">
                <p>危险驾驶犯罪记录</p>
            </td>
        </tr>
        <tr>
            <td class="tab_width">
                <input type="radio" class="radio" name="IS_DUI" value="1" <c:if test="${busRecord.IS_DUI=='1'}">checked="checked"</c:if> />有
                <input type="radio" class="radio" name="IS_DUI" value="0" <c:if test="${busRecord.IS_DUI=='0'}">checked="checked"</c:if> />无
                <%--<eve:excheckbox
                        dataInterface="dictionaryService.findDatasForSelect"
                        name="IS_DUI" width="100px;" value="${busRecord.IS_DUI}"
                        dataParams="YesOrNo" >
                </eve:excheckbox>--%>
            </td>
            <td colspan="5">
                <p>饮酒后驾驶记录</p>
            </td>
        </tr>
        <tr>
            <td class="tab_width">
                <input type="radio" class="radio" name="IS_THREEWEEK_TWELVESCORE" value="1" <c:if test="${busRecord.IS_THREEWEEK_TWELVESCORE=='1'}">checked="checked"</c:if> />有
                <input type="radio" class="radio" name="IS_THREEWEEK_TWELVESCORE" value="0" <c:if test="${busRecord.IS_THREEWEEK_TWELVESCORE=='0'}">checked="checked"</c:if> />无
                <%--<eve:excheckbox
                        dataInterface="dictionaryService.findDatasForSelect"
                        name="IS_THREEWEEK_TWELVESCORE" width="100px;" value="${busRecord.IS_THREEWEEK_TWELVESCORE}"
                        dataParams="YesOrNo" >
                </eve:excheckbox>--%>
            </td>
            <td colspan="5">
                <p>最近连续3个记分周期内记满12分记录</p>
            </td>
        </tr>
        <tr>
            <td class="tab_width">
                <input type="radio" class="radio" name="IS_DRUGS" value="1" <c:if test="${busRecord.IS_DRUGS=='1'}">checked="checked"</c:if> />有
                <input type="radio" class="radio" name="IS_DRUGS" value="0" <c:if test="${busRecord.IS_DRUGS=='0'}">checked="checked"</c:if> />无
            <%--<eve:excheckbox
                        dataInterface="dictionaryService.findDatasForSelect"
                        name="IS_DRUGS" width="100px;" value="${busRecord.IS_DRUGS}"
                        dataParams="YesOrNo" >
                </eve:excheckbox>--%>
            </td>
            <td colspan="5">
                <p>吸毒记录</p>
            </td>
        </tr>
        <tr>
            <td class="tab_width">
                <input type="radio" class="radio" name="IS_VIOLENT_CRIME" value="1" <c:if test="${busRecord.IS_VIOLENT_CRIME=='1'}">checked="checked"</c:if> />有
                <input type="radio" class="radio" name="IS_VIOLENT_CRIME" value="0" <c:if test="${busRecord.IS_VIOLENT_CRIME=='0'}">checked="checked"</c:if> />无
                <%--<eve:excheckbox
                        dataInterface="dictionaryService.findDatasForSelect"
                        name="IS_VIOLENT_CRIME" width="100px;" value="${busRecord.IS_VIOLENT_CRIME}"
                        dataParams="YesOrNo" >
                </eve:excheckbox>--%>
            </td>
            <td colspan="5">
                <p>暴力犯罪记录</p>
            </td>
        </tr>


        <tr>
            <td class="tab_width">上传审查附件：</td>
            <td>
                <a href="javascript:void(0);" onclick="openSCFileUploadDialog()">
                    <img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
                </a>
                <div style="width:60%;" id=scFileListDiv></div>
            </td>

            <td class="tab_width">模板：</td>
            <td colspan="4">
                <a <c:if test="${exeId == null || exeId == 'null' || exeId == ''}">href="attachFiles/spmb/附件3：出租汽车驾驶员安全驾驶信用证明.doc"</c:if>
                   <c:if test="${exeId != null && exeId != 'null' && exeId != ''}">href="TaxiLicenseController/downloadTaxiSafeDrivingCert.do?exeId=${exeId}"</c:if>
                               style="color: blue;margin-right: 5px;" target="_blank"><font color="blue">附件3：出租汽车驾驶员安全驾驶信用证明.docx</font></a>
            </td>
        </tr>


        <tr>
            <td class="tab_width">类别</td>
            <td colspan="2" class="tab_width">考试时间</td>
            <td colspan="2" class="tab_width">成绩</td>
            <td colspan="2" class="tab_width">考核员</td>
        </tr>
        <tr>
            <td>全国公共科目考试</td>
            <td colspan="2">
                <input type="text" name="ZGZ_EXAM_TIME"
                       class="laydate-icon"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                       value="${busRecord.ZGZ_EXAM_TIME}"
                       style="width:170px; height:26px;" />
            </td>
            <td colspan="2">
                <input class="tab_text" type="text"
                       maxlength="4" style="width: 33%;" id="ZGZ_EXAM_SCORE"
                       name="ZGZ_EXAM_SCORE" value="${busRecord.ZGZ_EXAM_SCORE}"  />
            </td>
            <td colspan="2">
                <input type="text" class="tab_text"
                       maxlength="16" style="width: 33%;" id="ZGZ_EXAM_USER"
                       name="ZGZ_EXAM_USER" value="${busRecord.ZGZ_EXAM_USER}"  />
            </td>
        </tr>
        <tr>
            <td>区域科目考试</td>
            <td colspan="2">
                <input type="text" name="ZGZ_AREAEXAM_TIME"
                       class="laydate-icon"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                       value="${busRecord.ZGZ_AREAEXAM_TIME}"
                       style="width:170px; height:26px;" />
            </td>
            <td colspan="2">
                <input type="text" class="tab_text"
                       maxlength="4" style="width: 33%;" id="ZGZ_AREAEXAM_SCORE"
                       name="ZGZ_AREAEXAM_SCORE" value="${busRecord.ZGZ_AREAEXAM_SCORE}"  />
            </td>
            <td colspan="2">
                <input type="text" class="tab_text"
                       maxlength="16" style="width: 33%;" id="ZGZ_AREAEXAM_USER"
                       name="ZGZ_AREAEXAM_USER" value="${busRecord.ZGZ_AREAEXAM_USER}"  /></td>
        </tr>
        <tr>
            <td class="tab_width">管理部门意见：</td>
            <td colspan="5">
                <input type="radio" class="radio" name="ZGZ_GLBM_OPNION" value="同意发证（具备从业资格条件，且从业资格考试成绩合格）"
                       <c:if test="${busRecord.ZGZ_GLBM_OPNION=='1'}">checked="checked"</c:if> />同意发证（具备从业资格条件，且从业资格考试成绩合格）
                <input type="radio" class="radio" name="ZGZ_GLBM_OPNION" value="不同意发证"
                       <c:if test="${busRecord.ZGZ_GLBM_OPNION=='0'}">checked="checked"</c:if> />不同意发证


                <%--<eve:excheckbox
                        dataInterface="dictionaryService.findDatasForSelect"
                        name="ZGZ_GLBM_OPNION" width="749px;" value="${busRecord.ZGZ_GLBM_OPNION}"
                        dataParams="departOpnions" >
                </eve:excheckbox>--%>
            </td>
        </tr>
        <tr id="disagree_tr" style="display: none;">
            <td class="tab_width">不同意发证理由：</td>
            <td colspan="5">
                <input type="text" class="tab_text"
                       maxlength="220" style="width: 50%;" id="ZGZ_DISAGREE_REASON"
                       name="ZGZ_DISAGREE_REASON" value="${busRecord.ZGZ_DISAGREE_REASON}"  /></td>
        </tr>

        <tr  id="agree_tr" style="display: none;">
            <td class="tab_width">从业证资格证号：</td>
            <td><input type="text" class="tab_text"
                       maxlength="16" style="width: 33%;" id="ZGZ_CYZGZ_NUM"
                       name="ZGZ_CYZGZ_NUM" value="${busRecord.ZGZ_CYZGZ_NUM}"  /></td>

            <td class="tab_width">发证人</td>
            <td><input type="text" class="tab_text"
                       maxlength="16" style="width: 33%;" id="ZGZ_CYZGZ_LICENSOR"
                       name="ZGZ_CYZGZ_LICENSOR" value="${busRecord.ZGZ_CYZGZ_LICENSOR}"  /></td>
            </td>

            <td class="tab_width">领证人</td>
            <td><input type="text" class="tab_text"
                       maxlength="16" style="width: 33%;" id="ZGZ_CYZGZ_GETUSER"
                       name="ZGZ_CYZGZ_GETUSER" value="${busRecord.ZGZ_CYZGZ_GETUSER}"  /></td>
            </td>
        </tr>
    </table>
    <div class="tab_height" ></div>
        <%--开始引入公用上传材料界面 --%>
        <jsp:include page="./applyMaterList.jsp">
            <jsp:param value="2" name="isWebsite" />
        </jsp:include>
        <%--结束引入公用上传材料界面 --%>
        <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
            <tr>
                <th colspan="2">办理结果领取方式</th>
            </tr>
            <tr>
                <td><input type="radio" name="FINISH_GETTYPE" value="02"
                           <c:if test="${busRecord.FINISH_GETTYPE=='02'}">checked="checked"</c:if> />快递送达
                </td>
                <td><input type="radio" name="FINISH_GETTYPE" value="01"
                           <c:if test="${busRecord.FINISH_GETTYPE=='01'}">checked="checked"</c:if> />窗口领取
                </td>
            </tr>
            <tr id="addressee" style="display: none;">
                <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>收件人姓名：</span>
                    <input type="text" maxlength="16" class="tab_text validate[required]"
                           class="tab_text" value="${busRecord.RESULT_SEND_ADDRESSEE}"
                           name="RESULT_SEND_ADDRESSEE" />
                </td>
                <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>电话号码：</span>
                    <input type="text" maxlength="11" class="tab_text validate[required,custom[mobilePhoneLoose]]"
                           class="tab_text" value="${busRecord.RESULT_SEND_MOBILE}"
                           name="RESULT_SEND_MOBILE" />
                </td>
            </tr>
            <tr id="addrprov" style="display: none;">
                <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>所属省市：</span>
                    <input name="RESULT_SEND_PROV" id="province" class="easyui-combobox"  style="width:120px; height:26px;"
                           data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#city').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#city').combobox('reload',url);
							   }
							}
	                " value="${busRecord.RESULT_SEND_PROV}" />
                    <input name="RESULT_SEND_CITY" id="city" class="easyui-combobox" style="width:120px; height:26px;"
                           data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM11',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#county').combobox('clear');
							   if(rec.TYPE_CODE){
							       var url = 'dicTypeController/load.do?parentTypeCode='+rec.TYPE_CODE;
							       $('#county').combobox('reload',url);
							   }
							}
	                " value="${busRecord.RESULT_SEND_CITY}" />
                    <input name="RESULT_SEND_COUNTY" id="county" class="easyui-combobox" style="width:120px; height:26px;"
                           data-options="
			                url:'dicTypeController/load.do?parentTypeCode=XZQHDM1100',
			                method:'post',
			                valueField:'TYPE_NAME',
			                textField:'TYPE_NAME',
			                panelHeight:'auto',
			                panelMaxHeight:'300px',
			                editable:false,
			                onSelect:function(rec){
							   $('#sllx').combobox('clear');
							   if(rec.TYPE_CODE){
				                   $('input[name=\'COMPANY_TYPE\']').val(rec.TYPE_NAME);
							       var url = 'dictionaryController/loadData.do?typeCode='+rec.TYPE_CODE+'&orderType=asc';
							       $('#sllx').combobox('reload',url);
							   }
							}
	                " value="${busRecord.RESULT_SEND_COUNTY}" />
                </td>
                <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>邮政编码：</span>
                    <input type="text" maxlength="6" class="tab_text validate[[required],custom[chinaZip]]"
                           class="tab_text" value="${busRecord.RESULT_SEND_POSTCODE}"
                           name="RESULT_SEND_POSTCODE" />
                </td>
            </tr>
            <tr id="addr" style="display: none;">
                <td><span style="width: 90px;float:left;text-align:right;"><font class="tab_color">*</font>详细地址：</span>
                    <textarea rows="5" cols="80" name="RESULT_SEND_ADDR" value="${busRecord.RESULT_SEND_ADDR}"
                              maxlength="1998" class="validate[required]"></textarea></td>
                </td>
                <td/>
            </tr>
            <tr id="addrremarks" style="display: none;">
                <td><span style="width: 90px;float:left;text-align:right;">邮递备注：</span>
                    <textarea rows="5" cols="80" name="RESULT_SEND_REMARKS" value="${busRecord.RESULT_SEND_REMARKS}"
                              maxlength="1998" class="validate[]"></textarea></td>
                </td>
                <td/>
            </tr>
        </table>
        <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
            <tr>
                <th colspan="2">其他信息</th>
            </tr>
            <tr>
                <td class="tab_width">备注：</td>
                <td><textarea name="REMARK" cols="140" rows="10" value="${busRecord.REMARK}"
                              class="validate[[],maxSize[500]]"></textarea>
                </td>
                <td/>
            </tr>
        </table>


	</form>



</body>
</html>
