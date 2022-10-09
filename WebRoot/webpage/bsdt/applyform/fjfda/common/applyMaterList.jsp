<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String applyType = request.getParameter("applyType");
	request.setAttribute("applyType", applyType);
	String isWebsite = request.getParameter("isWebsite");
	request.setAttribute("isWebsite", isWebsite);
%>
<!--电子证照开始js-->
<script type="text/javascript">
    var onlineBusTableName = "${serviceItem.FORM_KEY}";
	function LicenseList(selectid){
        var selectValue = $("#SQFS_" + selectid).val();
        var BSYHLX = $('input[name="BSYHLX"]:checked ').val();
        var applyType="${applyType}";
        if("1"==BSYHLX){
            getPersonalLicenseInfo(selectValue,selectid);
		}else if("2"==BSYHLX){
            getLicenseInfo(selectValue,selectid);
		}else if(applyType=="1"){   //前台
            var userType="${sessionScope.curLoginMember.USER_TYPE}";
            if("1"==userType){
                getPersonalLicenseInfoForFront(selectValue,selectid);
            }else if("2"==userType){
                getLicenseInfoForFront(selectValue,selectid);
            }else{
                alert("请完善信息，才能查询");
                return;
            }
        }
	}
    //后台企业证照
    function getLicenseInfo(selectValue,attachKey) {
        var credCodes=$("#SQJG_CODE").val();
        var credNames=$("#SQJG_NAME").val();
        if(credCodes==""&&credNames==""){
            alert("请填写组织机构信息");
            return ;
        }
        var type="enterprise";
        $("#showLicenseMsgSpan").html("");
        var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
			"&sqfs="+selectValue+"&attachKey="+attachKey+"&busTableName="+onlineBusTableName);
        $.dialog.open(url, {
            title : "电子证照信息列表",
            width:"880px",
            lock: true,
            resize:false,
            height:"550px",
            close: function () {
                var resultJsonInfo = art.dialog.data("resultJsonInfo");
                if (resultJsonInfo) {
                    initLicenseUploadMaters(resultJsonInfo);
                    art.dialog.removeData("resultJsonInfo");
                }
            }
        }, false);
    }
    //后台个人电子证照
    function getPersonalLicenseInfo(selectValue,attachKey) {
        var credCodes=$("#zjhm").val();
        var credNames=$("#SQRMC").val();
        if(credCodes==""||credNames==""){
            alert("请填写个人信息");
            return ;
		}
        var type="persona";
        $("#showPersonalLicenseMsgSpan").html("");
        var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
            "&sqfs="+selectValue+"&attachKey="+attachKey+"&busTableName="+onlineBusTableName);
        $.dialog.open(url, {
            title : "电子证照信息列表",
            width:"880px",
            lock: true,
            resize:false,
            height:"550px",
            close: function () {
                var resultJsonInfo = art.dialog.data("resultJsonInfo");
                if (resultJsonInfo) {
                    initLicenseUploadMaters(resultJsonInfo);
                    art.dialog.removeData("resultJsonInfo");
                }
            }
        }, false);
    }
    //前台个人电子证照
    function getPersonalLicenseInfoForFront(selectValue,attachKey) {
        var credCodes="${sessionScope.curLoginMember.YHMC}";
        var credNames="${sessionScope.curLoginMember.ZJHM}";
        if(credCodes==""||credNames==""){
            alert("请完善个人信息");
            return ;
        }
        var type="persona";
        $("#showPersonalLicenseMsgSpan").html("");
        var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
            "&sqfs="+selectValue+"&attachKey="+attachKey+"&busTableName="+onlineBusTableName+"&noprint="+1);
        $.dialog.open(url, {
            title : "电子证照信息列表",
            width:"880px",
            lock: true,
            resize:false,
            height:"550px",
            close: function () {
                var resultJsonInfo = art.dialog.data("resultJsonInfo");
                if (resultJsonInfo) {
                    initLicenseUploadMaters(resultJsonInfo);
                    art.dialog.removeData("resultJsonInfo");
                }
            }
        }, false);
    }
    //前台企业证照
    function getLicenseInfoForFront(selectValue,attachKey) {
        var credCodes="${sessionScope.curLoginMember.ZZJGDM}";
        var credNames="${sessionScope.curLoginMember.YHMC}";
        if(credCodes==""&&credNames==""){
            alert("请完善组织机构信息");
            return ;
        }
        var type="enterprise";
        $("#showLicenseMsgSpan").html("");
        var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
            "&sqfs="+selectValue+"&attachKey="+attachKey+"&busTableName="+onlineBusTableName+"&noprint="+1);
        $.dialog.open(url, {
            title : "电子证照信息列表",
            width:"880px",
            lock: true,
            resize:false,
            height:"550px",
            close: function () {
                var resultJsonInfo = art.dialog.data("resultJsonInfo");
                if (resultJsonInfo) {
                    initLicenseUploadMaters(resultJsonInfo);
                    art.dialog.removeData("resultJsonInfo");
                }
            }
        }, false);
    }
function initLicenseUploadMaters(resultJson){
    for (var i = 0; i < resultJson.length; i++) {
        //获取文件ID
        var fileId = resultJson[i].fileId;
        //获取文件名称
        var fileName = resultJson[i].fileName;
        var filePath=resultJson[i].filePath;
        //获取材料KEY
        var attachKey = resultJson[i].attachKey;
        var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
        spanHtml += "<font color=\"blue\">" + fileName + "</font></a>";
        spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"preViewEtc('" + filePath + "');\" ><font color=\"blue\">预览</font></a>";
        spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delUploadMater('" + fileId + "','" + attachKey + "');\" ><font color=\"red\">删除</font></a></p>";
        $("#UploadedFiles_" + attachKey).append(spanHtml);
    }
}
    /**
	 * 预览
     * @param filePath
     */
    function preViewEtc(filePath){
        var applyType="${applyType}";
        var url;
        if(applyType==1) {
            url = "creditController.do?showFileWithFilePath&filePath=" + filePath + "&noprint=" + 1;
        }else{
            url = "creditController.do?showFileWithFilePath&filePath=" + filePath;
        }
    $.dialog.open(url, {
        title : "证照页面",
        width: "100%",
        height: "100%",
        left: "0%",
        top: "0%",
        fixed: true,
        lock : true,
        resize : false,
        close: function () {

        }
    }, false);
}

</script>
<!--电子证照js结束-->

<script type="text/javascript">
    var onlineBusTableName = "${serviceItem.FORM_KEY}";
    var uploadUserId = $("input[name='uploadUserId']").val();
    $(function() {
        //var  stat = "${serviceItem}";
        hideSC();
    });

    function hideSC() {
        var flowSubmitObj = FlowUtil.getFlowObj();
        if (flowSubmitObj.busRecord.RUN_STATUS && flowSubmitObj.busRecord.RUN_STATUS != 0) {
            $("div[id*='SC']").attr("style", "display: none;");
            $("div[id*='LICENSE']").attr("style","display:none;");
            $("div[id*='LICENSE1']").attr("style","display:none;");
        }
    }
    function chagediv(selectid) {
        var selectValue = $("#SQFS_" + selectid).val();
        if (selectValue == "1") {
            $("#div1_" + selectid).attr("style", "");
            $("#div2_" + selectid).attr("style", "display:none;");
            //$("#"+selectid+"__LICENSE1").attr("style", "display:none;");
            $("#UploadedFiles_" + selectid).attr("style", "");
        } else {
            $("#div1_" + selectid).attr("style", "display:none;");
            $("#div2_" + selectid).attr("style", "margin-left:73px;float:left");

            $("#UploadedFiles_" + selectid).attr("style", "display:none;");
        }
    }
    function showMater(code) {
        if ($("#" + code + "_a").html() == '展开') {
            $("." + code).show();
            $("#" + code + "_a").html("收起");
        } else {
            $("." + code).hide();
            $("#" + code + "_a").html("展开");
        }
    }
    function onlineWord(materCode, fileId, materName) {
        var leftSpanSize = 0;
        leftSpanSize = $("#UploadedFiles_" + materCode).children("p").length;
        var isfrist = 0; //是否为模版编辑
        if (leftSpanSize > 0) {
            fileId = $("#UploadedFiles_" + materCode).children("p").first().attr("id");
            isfrist = 1;
        }
        //定义上传的人员的ID
        var uploadUserId = $("input[name='uploadUserId']").val();
        //定义上传的人员的NAM
        var uploadUserName = $("input[name='uploadUserName']").val();
        $.dialog.open(encodeURI("applyMaterController.do?onlineWord&materName=" + materName + "&materCode="
            + materCode + "&uploadUserId=" + uploadUserId + "&uploadUserName="
            + uploadUserName + "&onlineBusTableName=" + onlineBusTableName + "&isfrist="
            + isfrist + "&fileId=" + fileId), {
                title : "在线编辑",
                width : "100%",
                height : "100%",
                left : "0%",
                top : "0%",
                fixed : true,
                lock : true,
                resize : false,
                close : function() {
                    var saveMaterInfo = art.dialog.data("saveMaterInfo");
                    if (saveMaterInfo && saveMaterInfo.templateId) {
                        var fileId = saveMaterInfo.templateId;
                        var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                        spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
                        spanHtml += "<font color=\"blue\">" + materName + "</font></a>";
                        spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delUploadMater('" + fileId + "','" + materCode + "');\" ><font color=\"red\">删除</font></a></p>";
                        $("#UploadedFiles_" + materCode).html(spanHtml);
                        setUploadHidden(materCode);
                    }
                    art.dialog.removeData("saveMaterInfo");
                }
            }, false);
    }

    function setWordHidden(attachKey) {
        $("#" + attachKey + "__ZXBJ").attr("style", "float: right;margin:0 5px 0 0;display: none;");
    }
    function setUploadHidden(attachKey) {
        $("#" + attachKey + "__SC").attr("style", "display: none;");
        $("#" + attachKey + "__SCAN").attr("style", "display: none;");
        $("#" + attachKey + "__LICENSE").attr("style", "display: none;");
    }
    function setWordShow(attachKey) {
        $("#" + attachKey + "__ZXBJ").attr("style", "float: right;margin:0 5px 0 0;");
        $("#" + attachKey + "__SC").attr("style", "float: left;");
    //$("#"+attachKey+"__SCAN").attr("style","float: left;");
    }
    function bindScanCtrl(attachKey, matreClsm) {
        //定义上传的人员的ID
        var uploadUserId = $("input[name='uploadUserId']").val();
        //定义上传的人员的NAME
        var uploadUserName = $("input[name='uploadUserName']").val();

        $.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&attachKey="
            + attachKey + "&uploadUserId=" + uploadUserId + "&uploadUserName=" + uploadUserName + "&busTableName=" + onlineBusTableName + "&matreClsm=" + matreClsm, {
                title : "高拍仪",
                width : "810px",
                lock : true,
                resize : false,
                height : "90%",
                close : function() {
                    var resultJsonInfo = art.dialog.data("resultJsonInfo");
                    if (resultJsonInfo) {
                        initScanUploadMaters(resultJsonInfo, matreClsm);
                        art.dialog.removeData("resultJsonInfo");
                    }
                }
            }, false);
    }

    function initScanUploadMaters(resultJson, matreClsm) {
        for (var i = 0; i < resultJson.length; i++) {
            //获取文件ID		
            var fileId = resultJson[i].fileId;
            //获取文件名称
            var fileName = resultJson[i].fileName;
            //获取材料KEY
            var attachKey = resultJson[i].attachKey;
            //获取文件路径
            var filePath = resultJson[i].filePath;
            //获取文件的类型
            var fileType = resultJson[i].fileType;
            //获取是否是图片类型
            var isImg = resultJson[i].isImg;
            var spanHtml = "<p id=\"" + fileId + "\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
            spanHtml += (" onclick=\"AppUtil.downLoadFile('" + fileId + "');\">");
            spanHtml += "<font color=\"blue\">" + fileName + "</font></a>";
            spanHtml += "<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delUploadMater('" + fileId + "','" + attachKey + "');\" ><font color=\"red\">删除</font></a></p>";
            $("#UploadedFiles_" + attachKey).append(spanHtml);
        }

        setWordHidden(attachKey); //隐藏在线编辑按钮


        // var num = $("#UploadedFiles_"+attachKey).children('p').length ;
        // if(num>=matreClsm){
        // alert("只能上传"+matreClsm+"个附件！");
        // return;
        // }

    }
</script>
<style>
.materBtnA {
	background: #62a1cf none repeat scroll 0 0;
	color: #fff;
	display: inline-block;
	height: 26px;
	left: -1px;
	line-height: 26px;
	margin-left: 5px;
	position: relative;
	text-align: center;
	top: 1px;
	width: 59px;
}
</style>
<div class="tab_height"></div>
<div class="syj-title1"><span>材料信息</span></div>
<table cellpadding="0" cellspacing="1" class="syj-table2">
    <tr>
		<th colspan="7"><font color="red">（去现场办理的人员必须为经办人本人或申请人本人，并提供本人有效身份证件给窗口。）</font></th>
	</tr>
    <tr>
        <th width="20px" style="width: 20px;">序号</th>
        <!-- <th class="tab_width" width="40px">事项子项</th> -->
        <th class="tab_width" width="400px">材料名称</th>
        <th class="tab_width" width="80px">材料说明</th>
        <th class="tab_width">附件</th>
        <c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
            <th class="tab_width" width="60px">收取方式</th>
            <th class="tab_width" width="350px">文件操作</th>
        </c:if>
        <c:if test="${!empty nodeConfig&&nodeConfig.UPLOAD_FILES=='-1'}">
            <th class="tab_width" width="100px">是否收取</th>
            <th class="tab_width" width="200px">审核状态</th>
        </c:if>
    </tr>
    <c:forEach items="${applyMaters}" var="applyMater" varStatus="varStatus">
        <tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if>>
            <td width="20px" style="text-align: center;width: 20px;">${varStatus.index+1}</td>
            <%-- <td>${applyMater.BUSCLASS_NAME}				<c:if test="${applyMater.BUSCLASS_NAME=='无'}"></c:if> 				<c:if test="${applyMater.BUSCLASS_NAME!='无'}">${applyMater.BUSCLASS_NAME}</c:if>
            </td> --%>
            <td>
                <%--判断是否必填 --%> <c:if test="${applyMater.MATER_ISNEED=='1'}">
                    <font class="tab_color">*</font>
                </c:if> ${applyMater.MATER_NAME} <%--判断是否有样本 --%> <c:if test="${applyMater.MATER_PATH!=null}">
                    <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');" style="color:#F00;">[样本]</a>
                </c:if>
            </td>
            <c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX=='综合件'}">
                <td colspan="4"><a class="materBtnA" style="color:#fff; float: right; margin-right: 15px;" href="javascript:showMater('${applyMater.MATER_CODE}')" id="${applyMater.MATER_CODE}_a">收起</a>
                </td>
            </c:if>
            <c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX!='综合件'}">
                <td>
                    <%--			    ${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份--%> <c:if test="${applyMater.PAGECOPY_NUM==null&&applyMater.PAGE_NUM==null}">
                        <c:if test="${applyMater.MATER_CLSMLX==null||applyMater.MATER_CLSMLX==''}">无</c:if>
                        <c:if test="${applyMater.MATER_CLSMLX=='复印件'}">原件1份&nbsp;</c:if>
                        <c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX!=''}">${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份</c:if>
                    </c:if> <c:if test="${applyMater.PAGECOPY_NUM!=null||applyMater.PAGE_NUM!=null}">
                        <c:if test="${applyMater.PAGECOPY_NUM!=null}">复印件${applyMater.PAGECOPY_NUM}份</c:if>
                        <c:if test="${applyMater.PAGE_NUM!=null}">原件${applyMater.PAGE_NUM}份（${applyMater.GATHERORVER}）</c:if>
                        <c:if test="${fn:indexOf(applyMater.MATER_CLSMLX, '电子文档') != -1}">电子文档</c:if>
                    </c:if>
                </td>
                <td>
                    <div id="UploadedFiles_${applyMater.MATER_CODE}" <c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
                        <c:forEach var="uploadFile" items="${applyMater.uploadFiles}">
                            <c:if test="${applyMater.UPLOADED_SIZE!=0}">
                                <p id="${uploadFile.FILE_ID}">
                                    <a href="javascript:void(0);" onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');" style="cursor: pointer;"> <font color="blue">
                                            ${uploadFile.FILE_NAME} </font>
                                    </a>
									<c:if test="${uploadFile.FILE_TYPE=='etc'}">
										<a href="javascript:void(0);"
										   onclick="preViewEtc('${uploadFile.FILE_PATH}');">
											<font color="red">预览</font></a>
									</c:if>
                                     <c:if test="${EFLOW_IS_START_NODE!='false'&&isQueryDetail!='true'}">
                                        <a href="javascript:void(0);" onclick="AppUtil.delUploadMater('${uploadFile.FILE_ID}','${uploadFile.ATTACH_KEY}');"> <font color="red">删除</font></a>
                                    </c:if>
                                </p>
                            </c:if>
                        </c:forEach>
                    </div>
                </td>
                <c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
                    <td><select type="select" class="tab_text" style="width: 90px;" id="SQFS_${applyMater.MATER_CODE}" <c:if test="${applyType=='1'}">disabled="disabled"</c:if>
                        onchange="chagediv('${applyMater.MATER_CODE}')">
                            <option value="1"<c:if test="${applyMater.SQFS==1}">selected="true"</c:if>>上传</option>
					<option value="2"<c:if test="${applyMater.SQFS==2}">selected="true"</c:if>>纸质收取</option>
			     </select>
            </td>
			<td>
				<div id="div1_${applyMater.MATER_CODE}" <c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
					<c:if test="${applyMater.SUPPORT_WORD=='1'}">
						<span id="${applyMater.MATER_CODE}__ZXBJ" style="float: right;margin:0 5px 0 0;<c:if test="${applyMater.UPLOADED_SIZE>0}">display: none;</c:if>" 
						 class="tab_btn_tj1 tab_tk_pro2btn" onclick="onlineWord('${applyMater.MATER_CODE}','${applyMater.MATER_PATH}','${applyMater.MATER_NAME}');">
	                                                            在线编辑
	                    </span>
	                </c:if>
	                <div id="${applyMater.MATER_CODE}__SC" style="width: 80px;float: left;" >
	                   <img id="${applyMater.MATER_CODE}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				    </div>
					<div id="${applyMater.MATER_CODE}__SCAN"
						<c:if test="${isWebsite=='2'}">style="float: left;"</c:if>
						<c:if test="${isWebsite=='1'}">style="display: none;"</c:if>>
						<a href="javascript:bindScanCtrl('${applyMater.MATER_CODE}','${applyMater.MATER_CLSM}')">
                            <img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/>
                        </a>
					</div>
					<!--电子证照-->
					<div id="${applyMater.MATER_CODE}__LICENSE"
						 >
						<a href="javascript:LicenseList('${applyMater.MATER_CODE}')">
							<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/dzzz.png" style="width:73px;height:27px;"/>
						</a>
					</div>


				</div>
				<div id="div2_${applyMater.MATER_CODE}"<c:if test="${applyMater.SQFS!=2}">style="display: none;"</c:if>>
					<select type="select" class="tab_text" style="width: 90px;float: left;" id="SFSQ_${applyMater.MATER_CODE}">
						<option value="1"<c:if test="${applyMater.SFSQ==1}">selected="true"</c:if>>已收取</option>
						<option value="-1"<c:if test="${applyMater.SFSQ==-1}">selected="true"</c:if>>未收取</option>
						<option value="2"<c:if test="${applyMater.SFSQ==2}">selected="true"</c:if>>未收取</option>
					</select>
					<!--电子证照-->
					<div id="${applyMater.MATER_CODE}__LICENSE1">
						<a href="javascript:LicenseList('${applyMater.MATER_CODE}')">
							<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/dzzz.png" style="width:73px;height:27px;"/>
						</a>
					</div>
				</div>

			</td>
			</c:if>
			<c:if test="${!empty nodeConfig&&nodeConfig.UPLOAD_FILES=='-1'}">
			     <td>
                            <c:if test="${applyMater.SFSQ==1}">已收取</c:if>
                            <c:if test="${applyMater.SFSQ!=1}">未收取</c:if>
                </td>
                <td  width="100px">
                    <c:if test="${applyMater.FILE_SHZT==1}">审核通过</c:if>
                    <c:if test="${applyMater.FILE_SHZT==-1}">审核未通过</c:if>
                    <c:if test="${applyMater.FILE_SHZT!=1&&applyMater.FILE_SHZT!=-1}">未审核</c:if>
                </td>
            </c:if>
			</c:if>
		</tr>
	</c:forEach>
	
	<tr>
		<th colspan="7" style="color: red;">办件申报时已收取材料（备查用）</th>
	</tr>
	<tr>
		<th width="20px" style="width: 20px;">序号</th>
		<!-- <th class="tab_width" width="40px">事项子项</th> -->
		<th class="tab_width" width="400px">材料名称</th>
		<th class="tab_width" width="80px">材料说明</th>
		<th class="tab_width">附件</th>
		<c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
		<th class="tab_width" width="100px">收取方式</th>
		</c:if>
		<c:if test="${!empty nodeConfig&&nodeConfig.UPLOAD_FILES=='-1'}">
        <th class="tab_width" width="100px">审核状态</th>
        </c:if>
        <th class="tab_width" width="100px">收取时间</th>
	</tr>
	
	<c:forEach items="${ysqMaters}" var="ysqMater"
		varStatus="varStatus">
		<tr <c:if test="${ysqMater.MATER_PARENTCODE!=null&&ysqMater.MATER_PARENTCODE!=''}"> class="${ysqMater.MATER_PARENTCODE}"</c:if> >
			<td width="20px" style="text-align: center; width: 20px; ">${varStatus.index+1}</td>
			<%-- <td>${applyMater.BUSCLASS_NAME}
				<c:if test="${applyMater.BUSCLASS_NAME=='无'}"></c:if>
				<c:if test="${applyMater.BUSCLASS_NAME!='无'}">${applyMater.BUSCLASS_NAME}</c:if>
			</td> --%>
			<td>
				<%--判断是否必填 --%> <c:if test="${ysqMater.MATER_ISNEED=='1'}">
					<font class="tab_color">*</font>
				</c:if> ${ysqMater.MATER_NAME} <%--判断是否有样本 --%> <c:if
					test="${ysqMater.MATER_PATH!=null}">
					<a href="javascript:void(0);"
						onclick="AppUtil.downLoadFile('${ysqMater.MATER_PATH}');"
						style="color:#F00;">[样本]</a>
				</c:if>
			</td>
			<c:if test="${ysqMater.MATER_CLSMLX!=null&&ysqMater.MATER_CLSMLX=='综合件'}">
				<td colspan="4">
					<a class="materBtnA" style="color:#fff; float: right; margin-right: 15px;" href="javascript:showMater('${ysqMater.MATER_CODE}')" id="${ysqMater.MATER_CODE}_a">收起</a>
				</td>
			</c:if>
			<c:if test="${ysqMater.MATER_CLSMLX!=null&&ysqMater.MATER_CLSMLX!='综合件'}">
			<td>			 
			    ${ysqMater.MATER_CLSMLX}${ysqMater.MATER_CLSM}份
			</td>
			<td>
				<div id="UploadedFiles_${ysqMater.MATER_CODE}"
					<c:if test="${ysqMater.SQFS==2}">style="display: none;"</c:if>>
					<c:forEach var="uploadFile" items="${ysqMater.uploadFiles}">
						<c:if test="${ysqMater.UPLOADED_SIZE!=0}">	
							<p id="${uploadFile.FILE_ID}" >
					             <a href="javascript:void(0);"
                                onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');"
                                 style="cursor: pointer;">
					              <font color="blue">
					                           ${uploadFile.FILE_NAME}
					              </font>
					             </a>
									<c:if test="${uploadFile.FILE_TYPE=='etc'}">
									<a href="javascript:void(0);"
									   onclick="preViewEtc('${uploadFile.FILE_PATH}');">
										<font color="red">预览</font></a>
									</c:if>
					              <c:if test="${EFLOW_IS_START_NODE!='false'&&isQueryDetail!='true'}">
					             <a href="javascript:void(0);"
                                onclick="AppUtil.delUploadMater('${uploadFile.FILE_ID}','${uploadFile.ATTACH_KEY}');">
                                <font color="red">删除</font></a>
                                </c:if>
					        </p>
						</c:if>
					</c:forEach>
				</div>
			</td>
			<c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
			<td><select type="select" class="tab_text" style="width: 90px;"
				id="SQFS_${ysqMater.MATER_CODE}" <c:if test="${applyType=='1'}">disabled="disabled"</c:if> 
				onchange="chagediv('${ysqMater.MATER_CODE}')">
					<option value="1"
						<c:if test="${ysqMater.SQFS==1}">selected="true"</c:if>>
						上传</option>
                            <option value="2"
                                <c:if test="${ysqMater.SQFS==2}">selected="true"</c:if>>
						纸质收取</option>
			</select></td>
			</c:if>
			<c:if test="${!empty nodeConfig&&nodeConfig.UPLOAD_FILES=='-1'}">
                <td  width="100px">
                    <c:if test="${ysqMater.FILE_SHZT==1}">审核通过</c:if>
                    <c:if test="${ysqMater.FILE_SHZT==-1}">审核未通过</c:if>
                    <c:if test="${ysqMater.FILE_SHZT!=1&&ysqMater.FILE_SHZT!=-1}">未审核</c:if>
                </td>
            </c:if>
			</c:if>
			<td>			 
			    ${ysqMater.CREATE_TIME}
			</td>
		</tr>
	</c:forEach>
	<!-- 共性材料信息 -->
	<c:if test="${materListValue == true }">
		<tr>
			<td colspan="6">共性材料信息</th>
		</tr>
		<tr>
			<td class="tab_width1" width="50px">序号</td>
			<td class="tab_width1" colspan="3">材料名称</td>
	        <td class="tab_width1" colspan="2">上传时间</td>
		</tr>
		<c:forEach items="${materList}" var="materListInfo" varStatus="materst">
			<tr>
				<td>${materst.index+1}</td>
				<td colspan="3" title="${materListInfo.FILE_NAME}">
					<a style="color: blue;" title="${materListInfo.FILE_NAME}"
						href="${fileServer }download/fileDownload?attachId=${materListInfo.FILE_ID}&attachType=${materListInfo.ATTACH_TYPE}" >
						${materListInfo.FILE_NAME}
					</a>
				</td>
				<td colspan="2">${materListInfo.CREATE_TIME}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>
