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

<script type="text/javascript">
    var  onlineBusTableName = "${serviceItem.FORM_KEY}";
    var uploadUserId = $("input[name='uploadUserId']").val();
	$(function(){
	    //var  stat = "${serviceItem}";
    	hideSC();
	});

	function hideSC(){
    	var flowSubmitObj = FlowUtil.getFlowObj();
    	if(flowSubmitObj.busRecord){
	    	if((flowSubmitObj.busRecord.RUN_STATUS!=0&&flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES!='开始')||flowSubmitObj.busRecord.RUN_STATUS==2){
	    		 $("div[id*='SC']").attr("style","display: none;");
	    	}
    	}
    }
	function chagediv(selectid){
		var selectValue = $("#SQFS_"+selectid).val();
		if(selectValue=="1"){
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
	function onlineForm(formName){
		var recordId = store[formName];
		if(recordId==undefined){
			recordId="";
		}
		var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
		var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
		var exeId = eflowObj.EFLOW_EXEID;
		if(exeId==null||exeId==undefined){
			exeId = "";
		}
		var operType;
		if(eflowObj.busRecord==undefined||(eflowObj.EFLOW_CUREXERUNNINGNODENAMES=='开始'&&(eflowObj.busRecord.RUN_STATUS==1||eflowObj.busRecord.RUN_STATUS==0))){
			operType = "write";
		}else{
			operType = "read";
		}
		$.dialog.open("domesticControllerController/onLineForm.do?formName="+formName+"&recordId="+recordId+"&exeId="+exeId+"&operType="+operType, {
	        title : "在线编辑",
	        width: "80%",
	        height: "100%",
	        fixed: true,
	        lock : true,
	        resize : false,
	        close: function () {	            
                var backFormInfo = art.dialog.data("backFormInfo");
                if(backFormInfo){
	                var backName = backFormInfo.formName;
	                var recordId = backFormInfo.recordId;
	                store[backName] = recordId;
	                art.dialog.removeData("backFormInfo");
	            }
	        }
	    }, false);
	}
	
	function setWordHidden(attachKey){
		$("#"+attachKey+"__ZXBJ").attr("style","float: right;margin:0 5px 0 0;display: none;");
	}
	function setUploadHidden(attachKey){
        $("#"+attachKey+"__SC").attr("style","display: none;");
        $("#"+attachKey+"__SCAN").attr("style","display: none;");
    }
	function setWordShow(attachKey){
		$("#"+attachKey+"__ZXBJ").attr("style","float: right;margin:0 5px 0 0;");
		$("#"+attachKey+"__SC").attr("style","float: left;");
		//$("#"+attachKey+"__SCAN").attr("style","float: left;");
    }
	function bindScanCtrl(attachKey,matreClsm){
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		
		$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&attachKey="
		+attachKey+"&uploadUserId="+uploadUserId+"&uploadUserName="+uploadUserName+"&busTableName="+onlineBusTableName+"&matreClsm="+matreClsm, {
			title : "高拍仪",
			width:"810px",
			lock: true,
			resize:false,
			height:"90%",
			close: function () {
				var resultJsonInfo = art.dialog.data("resultJsonInfo");
				if(resultJsonInfo){
					initScanUploadMaters(resultJsonInfo,matreClsm);
					art.dialog.removeData("resultJsonInfo");
				}
			}
		}, false);
	}
	function initScanUploadMaters(resultJson,matreClsm){
		for(var i=0;i<resultJson.length;i++){	
			//获取文件ID		
			var fileId = resultJson[i].fileId;
			//获取文件名称
			var fileName = resultJson[i].fileName;
			//获取材料KEY
			var attachKey =resultJson[i].attachKey;
			//获取文件路径
			var filePath = resultJson[i].filePath;
			//获取文件的类型
			var fileType = resultJson[i].fileType;
			//获取是否是图片类型
			var isImg = resultJson[i].isImg;
			var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
			spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
			spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
			spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"AppUtil.delUploadMater('"+fileId+"','"+attachKey+"');\" ><font color=\"red\">删除</font></a></p>"
			$("#UploadedFiles_"+attachKey).append(spanHtml);
		}
		
		setWordHidden(attachKey);//隐藏在线编辑按钮
						
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
<input type="hidden" name="applyItemMatersJson" value="${applyMatersJson}" /> 
<input type="hidden" name="haveOnline" value="${haveOnline}" /> 
<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3">
	<tr>
		<td class="tab_width1" width="40px">序号</td>
		<td class="tab_width1" width="350px">材料名称</td>
		<td class="tab_width1" width="70px">材料说明</td>
		<td class="tab_width1">附件</td>
		<td class="tab_width1" width="100px">收取方式</td>
		<td class="tab_width1" width="72px">文件操作</td>
	</tr>
	<c:forEach items="${applyMaters}" var="applyMater"
		varStatus="varStatus">
		<tr <c:if test="${applyMater.MATER_PARENTCODE!=null&&applyMater.MATER_PARENTCODE!=''}"> class="${applyMater.MATER_PARENTCODE}"</c:if> >
			<td>${varStatus.index+1}</td>
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
			<td>			 
			    ${applyMater.MATER_CLSMLX}${applyMater.MATER_CLSM}份
			</td>
			<td>
				<div id="UploadedFiles_${applyMater.MATER_CODE}"
					<c:if test="${applyMater.SQFS==2||applyMater.SQFS==3}">style="display: none;"</c:if>>
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
				</div>
			</td>
			<td><select type="select" class="tab_text" style="width: 90px;"
				id="SQFS_${applyMater.MATER_CODE}" <c:if test="${applyType=='1'}">disabled="disabled"</c:if> 
				onchange="chagediv('${applyMater.MATER_CODE}')">
					<option value="1"
						<c:if test="${applyMater.SQFS==1}">selected="true"</c:if>>
						上传</option>
					<option value="2"
						<c:if test="${applyMater.SQFS==2}">selected="true"</c:if>>
						纸质收取</option>
					<option value="3"
						<c:if test="${applyMater.IS_FORM==1}">selected="true"</c:if>>
						在线编辑</option>
			</select></td>
			<td>
				<div id="div1_${applyMater.MATER_CODE}" 
					<c:if test="${applyMater.SQFS==2}">style="display: none;"</c:if>>
					<c:if test="${applyMater.IS_FORM=='1'}">
						<!-- <script type="text/javascript">
							haveOnLine.push('${applyMater.FORM_NAME}');
						</script> -->
						<span id="${applyMater.MATER_CODE}__ZXBJ" style="cursor: pointer;;margin:0 5px 0 0;" 
						 class="tab_btn_tj1 tab_tk_pro2btn" onclick="onlineForm('${applyMater.FORM_NAME}');">
	                    		在线编辑
	                    </span>
	                </c:if>
	                <c:if test="${applyMater.IS_FORM!='1'}">
	                <div id="${applyMater.MATER_CODE}__SC" style="width: 80px;float: left;" >
					 <img id="${applyMater.MATER_CODE}"
						src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
				    </div>
				    </c:if>
					<div id="${applyMater.MATER_CODE}__SCAN" 
						<c:if test="${isWebsite=='2'}">style="float: left;"</c:if>
						<c:if test="${isWebsite=='1'}">style="display: none;"</c:if>>
						<a href="javascript:bindScanCtrl('${applyMater.MATER_CODE}','${applyMater.MATER_CLSM}')"><img id="${applyMater.MATER_CODE}"
						src="webpage/bsdt/ptwgform/images/scan.png" style="width:73px;height:27px;"/></a>
					</div>
				</div>
			</td>
		</tr>
	</c:forEach>
</table>
