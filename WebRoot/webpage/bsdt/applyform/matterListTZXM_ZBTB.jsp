<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
  String applyType = request.getParameter("applyType");
  request.setAttribute("applyType", applyType);
  String formName = request.getParameter("formName");
  request.setAttribute("formName", formName);
  String path = request.getContextPath();
%>
 <eve:resources loadres="apputil,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
<script type="text/javascript">
    var  onlineBusTableName = "${serviceItem.FORM_KEY}";
	function chagediv(selectid){
		var selectValue = $("#SQFS_"+selectid).val();
		if(selectValue=="1"||selectValue=="3"){
	        $("#div1_"+selectid).attr("style","");
	        $("#div2_"+selectid).attr("style","display:none;");
	        $("#UploadedFiles_"+selectid).attr("style","");
	    }else{
	    	$("#div1_"+selectid).attr("style","display:none;");
	        $("#div2_"+selectid).attr("style","");
	        $("#UploadedFiles_"+selectid).attr("style","display:none;");
	    }
	};
	function showMater(code){
		if($("#"+code+"_a").html()=='展开'){
			$("."+code).show();		
			$("#"+code+"_a").html("收起");			
		}else{			
			$("."+code).hide();	
			$("#"+code+"_a").html("展开");
		}
	}
	var tableformjson;
	function getTableData(){
	    var data = "{";                                        //定义数据变量
	    $("#sqjg").find("input, select,textarea").each(function(){//遍历表格中的input、select等标签
	        if($(this).attr("name")){		               //如果此标签设置了name，则取出其中数据
	            data += "\"" + $(this).attr("name") + "\":\"" + $(this).val() + "\",";	//拼接name和数据
	        }
	    });
	    if(data.length != 1){                                  //如果取出了数据，删除多余的符号
	        data = data.substring(0, data.length-1);       //删除多余的符号','
	    }			
	    data += "}";                                           //添加结束符
	    data = data.replace("#","%23");
	    data = data.replace("%","%25");
	    data = data.replace(/[\n]/g,"  ");
	    data = data.replace(/[\r]/g,"  ");
	    data = eval("(" + data + ")");	                       //将数据转换成json对象
	    return data;					       //返回数据
	}
	function initAnnexFieldValue(fieldValueObj,elementId){
		for(var fieldName in fieldValueObj){
			//获取目标控件对象
			var targetField = $("#"+elementId).find("[name='"+fieldName+"']")[0];
			if(targetField){
				var type = targetField.type;
				var tagName = targetField.tagName;
				var fieldValue = fieldValueObj[fieldName];
				if(type=="radio"){
					var fields = $("#"+elementId).find("[name='"+fieldName+"']");
					fields.each(function(){
				        var radioValue = $(this).val();
				        if(radioValue==fieldValue){
				        	$(this).attr("checked","checked");
				        }
				    });
				}else if(type=="checkbox"){
					var fields = $("#"+elementId).find("[name='"+fieldName+"']");
					fields.each(function(){
				        var checkBoxValue = $(this).val();
				        var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				        if(isChecked){
				        	$(this).attr("checked","checked");
				        }
				    });
				}else if(tagName=="TEXTAREA"||tagName=="SELECT"){
					targetField.value = fieldValueObj[fieldName];
				}else{
					targetField.setAttribute("value",fieldValueObj[fieldName]);
				}
			}
		}
	}
	function hideDiv(div_id) {  
        $("#mask").remove();  
        $("#" + div_id).animate({left: 0, top: 0, opacity: "hide" }, "slow");  
    }  
	function onlineWordOld(materCode,fileId,materName){
		var leftSpanSize = 0;
		leftSpanSize = $("#UploadedFiles_"+materCode).children("p").length;
		var isfrist = 0;//是否为模版编辑
		if(leftSpanSize>0){
			fileId = $("#UploadedFiles_"+materCode).children("p").first().attr("id");
			isfrist = 1;
		}
		//定义上传的人员的ID
        var uploadUserId = $("input[name='uploadUserId']").val();
        //定义上传的人员的NAME
        var uploadUserName = $("input[name='uploadUserName']").val();
		$.dialog.open("applyMaterController.do?onlineWord&materName="+materName+"&materCode="
				+materCode+"&uploadUserId="+uploadUserId+"&uploadUserName="
				+uploadUserName+"&onlineBusTableName="+onlineBusTableName+"&isfrist="
				+isfrist+"&fileId="+fileId, {
	        title : "在线编辑",
	        width: "100%",
	        height: "100%",
	        left: "0%",
	        top: "0%",
	        fixed: true,
	        lock : true,
	        resize : false,
	        close: function () {
	            var saveMaterInfo = art.dialog.data("saveMaterInfo");
	            if(saveMaterInfo&&saveMaterInfo.templateId){
	            	var fileId = saveMaterInfo.templateId;
	            	var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                    spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
                    spanHtml +="<font color=\"blue\">"+materName+"</font></a>"
                    spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delUploadMater('"+fileId+"','"+materCode+"');\" ><font color=\"red\">删除</font></a></p>"
                    $("#UploadedFiles_"+materCode).html(spanHtml);
                    setUploadHidden(materCode);
	            }
	            art.dialog.removeData("saveMaterInfo");
	        }
	    }, false);
	}
	
	function setWordHidden(attachKey){
		$("#"+attachKey+"__ZXBJ").attr("style","float: right;margin:0 5px 0 0;display: none;");
	}
	function setUploadHidden(attachKey){
        $("#"+attachKey+"__SC").attr("style","display: none;");
    }
	function setWordShow(attachKey){
		$("#"+attachKey+"__ZXBJ").attr("style","float: right;margin:0 5px 0 0;");
		$("#"+attachKey+"__SC").attr("style","");
    }
	//附件表重新排序
	function storTd(){
		var i=0;
		$("table.tab_tk_pro1").find("tr.datatr").each(function(obj){
			if($(this).css("display")!="none"){
				i++;
				$(this).find("td.td_index").html(i);	
			}	
		});
	}
	
	
	
    <!--电子证照开始js-->
    var licenseBusTableName = "${serviceItem.FORM_KEY}";
    function LicenseList(selectid) {
        var selectValue = $("#SQFS_" + selectid).val();
        var applyType = "${applyType}";
        var BSYHLX = $('input[name="BSYHLX"]:checked ').val();
        if (applyType == "1") {
            var userType = "${sessionScope.curLoginMember.USER_TYPE}";
            if ("1" == userType) {
                getPersonalLicenseInfoForFront(selectValue, selectid);
            } else if ("2" == userType) {
                getLicenseInfoForFront(selectValue, selectid);
            } else {
                alert("请完善信息，才能查询");
                return;
            }
        } else {
            if (BSYHLX == "1") {
                getPersonalLicenseInfo(selectValue, selectid);
            } else {
                getLicenseInfo(selectValue, selectid);
            }
        }
    }

    //后台企业证照
    function getLicenseInfo(selectValue,attachKey) {
        var credCodes=$("#SQJG_CODE").val();
        var credNames=$("#SQJG_NAME").val();
        var type="enterprise";
        var itemName="${serviceItem.ITEM_NAME}";
        var itemId="${serviceItem.ITEM_CODE}";
        var transactor="${sessionScope.curLoginUser.fullname}";
        var backUserId="${sessionScope.curLoginUser.userId}";
        var creditMark = $("input[name='CREDIT_FEEDBACK_MARK']").val();
        if(credCodes==""&&credNames==""){
            alert("请填写组织机构信息");
            return ;
        }
    var url="creditController.do?licenseInfo&codes="+credCodes+"&names="+
            credNames+"&itemName="+itemName+"&itemId="+itemId+"&attachKey="+attachKey+"&busTableName="+licenseBusTableName+"&type="+type;
            console.log(url);
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
        var itemName="${serviceItem.ITEM_NAME}";
        var itemId="${serviceItem.ITEM_CODE}";
        var transactor="${sessionScope.curLoginUser.fullname}";
        var backUserId="${sessionScope.curLoginUser.userId}";
        var credCodes=$("#zjhm").val();
        var credNames=$("#SQRMC").val();
        var creditMark = $("input[name='CREDIT_FEEDBACK_MARK']").val();
        if(credCodes==""||credNames==""){
            alert("请填写个人信息");
            return ;
        }
        var type="persona";
        $("#showPersonalLicenseMsgSpan").html("");
        var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
            "&itemName="+itemName+"&transactor="+transactor+"&backUserId="+backUserId+"&itemId="+itemId+"&creditMark=" + creditMark +
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
        var itemName="${serviceItem.ITEM_NAME}";
        var itemId="${serviceItem.ITEM_CODE}";
        var credCodes="${sessionScope.curLoginMember.ZJHM}";
        var credNames="${sessionScope.curLoginMember.YHMC}";
        var aheadUserId = "${sessionScope.curLoginMember.USER_ID}";
        var creditMark = $("input[name='CREDIT_FEEDBACK_MARK']").val();
        if(credCodes==""||credNames==""){
            alert("请完善个人信息");
            return ;
        }
        var type="persona";
        var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
                "&itemName="+itemName+"&transactor="+credNames+"&aheadUserId="+aheadUserId+"&itemId="+itemId+"&creditMark=" + creditMark +
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
        var itemName="${serviceItem.ITEM_NAME}";
        var itemId="${serviceItem.ITEM_CODE}";
        var credCodes="${sessionScope.curLoginMember.ZZJGDM}";
        var credNames="${sessionScope.curLoginMember.YHMC}";
        var aheadUserId = "${sessionScope.curLoginMember.USER_ID}";
        var creditMark = $("input[name='CREDIT_FEEDBACK_MARK']").val();
        if(credCodes==""&&credNames==""){
            alert("请完善组织机构信息");
            return ;
        }
        var type="enterprise";
        //$("#showLicenseMsgSpan").html("");
        var url=encodeURI("creditController.do?licenseInfo&codes="+credCodes+"&names="+credNames+"&type="+type+
            "&itemName="+itemName+"&transactor="+credNames+"&aheadUserId="+aheadUserId+"&itemId="+itemId+"&creditMark=" + creditMark +
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
<!--电子证照js结束-->	
	
	
	
	
	
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
<input type="hidden" name="EFLOW_BUSTABLENAME" value="${serviceItem.FORM_KEY}" /> 
<input type="hidden" name="CREDIT_FEEDBACK_MARK" value="${serviceItem.CREDIT_FEEDBACK_MARK}" />
<table cellpadding="0" cellspacing="1" class="tab_tk_pro1">
	<tr>
		<th colspan="6">材料信息</th>
	</tr>
	<tr>
		<td class="tab_width1" width="50px">序号</td>
		<td class="tab_width1" width="400px">材料名称</td>
		<td class="tab_width1" width="80px">材料说明</td>
		<td class="tab_width1">附件</td>
		<c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
		<td class="tab_width1" width="100px">收取方式</td>
		<td class="tab_width1" width="200px">文件操作</td>
		</c:if>
		<c:if test="${!empty nodeConfig&&nodeConfig.UPLOAD_FILES=='-1'}">
		<td class="tab_width1" width="100px">是否收取</td>
        <td class="tab_width1" width="200px">审核状态</td>
        </c:if>
	</tr>
	<c:forEach items="${applyMaters}" var="applyMater"
		varStatus="varStatus">
		<tr class="datatr tr_${applyMater.MATER_CODE}">
			<td class='td_index'>${varStatus.index+1}</td>
			<td>
				<%--判断是否必填 --%> <c:if test="${applyMater.MATER_ISNEED=='1'}">
					<font class="tab_color">*</font>
				</c:if> ${applyMater.MATER_NAME} <%--判断是否有样本 --%> <c:if
					test="${applyMater.MATER_PATH!=null}">
					<a href="javascript:void(0);"
						onclick="AppUtil.downLoadFile('${applyMater.MATER_PATH}');"
						style="color:#F00;">[样本]</a>
				</c:if>
			</td>
			<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX=='综合件'}">
				<td colspan="4">
					<a class="materBtnA" style="color:#fff; float: right; margin-right: 15px;" href="javascript:showMater('${applyMater.MATER_CODE}')" id="${applyMater.MATER_CODE}_a">收起</a>
				</td>
			</c:if>
			<c:if test="${applyMater.MATER_CLSMLX!=null&&applyMater.MATER_CLSMLX!='综合件'}">
			<td>			 
			    ${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份
			</td>
			<td>
				<div id="UploadedFiles_${applyMater.MATER_CODE}"
					<c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
					<c:forEach var="uploadFile" items="${applyMater.uploadFiles}">
						<c:if test="${applyMater.UPLOADED_SIZE!=0}">	
							<p id="${uploadFile.FILE_ID}" >
					             <a href="javascript:void(0);"
                                onclick="AppUtil.downLoadFile('${uploadFile.FILE_ID}');"
                                 style="cursor: pointer;">
					              <font color="blue">
					                           ${uploadFile.FILE_NAME}
					              </font>
					             </a>
					              <c:if test="${EFLOW_IS_START_NODE!='false'&&isQueryDetail!='true'}">
					             <a href="javascript:void(0);"
                                onclick="AppUtil.delUploadMater('${uploadFile.FILE_ID}','${uploadFile.ATTACH_KEY}');">
                                <font color="red">删除</font></a>
                                </c:if>
					        </p>
						</c:if>
							
					</c:forEach>
					<c:if test="${applyMater.SQFS==3&&applyMater.SFSQ==1}">
						<p id="${uploadFile.FILE_ID}">
							<a href="javascript:void(0);"
								onclick="onlineWordOld('${applyMater.MATER_CODE}','${applyMater.MATER_PATH}','${applyMater.MATER_NAME}');"
								style="cursor: pointer;"><font color="blue">${applyMater.MATER_NAME}</font></a>
							
						</p>
					</c:if>
				</div>
			</td>
			<c:if test="${empty nodeConfig||nodeConfig.UPLOAD_FILES=='1'}">
			<td><select type="select" class="tab_text" style="width: 90px;"
				id="SQFS_${applyMater.MATER_CODE}" <c:if test="${applyType=='1'}">disabled="disabled"</c:if> 
				onchange="chagediv('${applyMater.MATER_CODE}')">
					<option value="1"
						<c:if test="${applyMater.SQFS==1}">selected="true"</c:if>>
						上传</option>
					<option value="2"
						<c:if test="${applyMater.SQFS==2}">selected="true"</c:if>>
						纸质收取</option>
					<c:if test="${applyMater.SUPPORT_WORD=='1'}"><option value="3"
						<c:if test="${applyMater.SQFS==3}">selected="true"</c:if>>
						在线编辑</option></c:if>
			</select></td>
			<td>
				<div id="div1_${applyMater.MATER_CODE}" 
					<c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
					<c:if test="${applyMater.SUPPORT_WORD=='1'}">
						<span id="${applyMater.MATER_CODE}__ZXBJ" style="float: right;margin:0 65px 0 0;cursor: pointer;" 
						 class="tab_btn_tj1 tab_tk_pro2btn" onclick="onlineWord('${applyMater.MATER_CODE}','${applyMater.MATER_PATH}','${applyMater.MATER_NAME}');">
	                                                            在线编辑
	                    </span>
	                </c:if>
	                <div id="${applyMater.MATER_CODE}__SC" <c:if test="${applyMater.SUPPORT_WORD=='1'}">style="display: none;"</c:if>>
					 <a href="javascript:void(0);" onclick="AppUtil.openTitleFileUploadDialog('${applyMater.MATER_TYPE}','${applyMater.MATER_CODE}')">
							<img id="${applyMater.MATER_CODE}" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
					   </a>
				    </div>
				    <!--电子证照前台用户，当材料勾选了电子证照才显示-->
                    <div id="${applyMater.MATER_CODE}__DZZZ"
                         >
                        <a href="javascript:LicenseList('${applyMater.MATER_CODE}')">
                            <img id="${applyMater.MATER_CODE}" src="webpage/bsdt/ptwgform/images/dzzz.png" style="width:73px;height:27px;"/>
                        </a>
                    </div>
				    
				    
				</div>
				<div id="div2_${applyMater.MATER_CODE}"
					<c:if test="${applyMater.SQFS!=2}">style="display: none;"</c:if>>
					<select type="select" class="tab_text" style="width: 90px;"
						id="SFSQ_${applyMater.MATER_CODE}">
						<option value="1"
							<c:if test="${applyMater.SFSQ==1}">selected="true"</c:if>>
							已收取</option>
						<option value="-1"
							<c:if test="${applyMater.SFSQ==-1}">selected="true"</c:if>>
							未收取</option>
					</select>
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
	<!-- 共性材料信息 -->
	<c:if test="${materListValue == true }">
		<tr>
			<td colspan="8">共性材料信息</th>
		</tr>
		<tr>
			<td class="tab_width1" width="50px">序号</td>
			<td class="tab_width1" colspan="4">材料名称</td>
	        <td class="tab_width1" >上传时间</td>
		</tr>
		<c:forEach items="${materList}" var="materListInfo" varStatus="materst">
			<tr>
				<td>${materst.index+1}</td>
				<td colspan="4" title="${materListInfo.FILE_NAME}">
					<a style="color: blue;" title="${materListInfo.FILE_NAME}"
						href="${fileServer }download/fileDownload?attachId=${materListInfo.FILE_ID}&attachType=${materListInfo.ATTACH_TYPE}" >
						${materListInfo.FILE_NAME}
					</a>
				</td>
				<td >${materListInfo.CREATE_TIME}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>
