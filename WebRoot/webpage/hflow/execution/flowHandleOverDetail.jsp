<%@ page language="java" import="java.util.*,com.alibaba.fastjson.*" pageEncoding="UTF-8"%>
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
<eve:resources
	loadres="jquery,easyui,apputil,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript" src="<%=path%>/webpage/flowchart/js/jquery-1.11.1.min.js"></script>
 <script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js"></script> 
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<style>

.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
</style>
<script type="text/javascript">
	
	$(function(){
    	var flowobj='<%=request.getAttribute("resultJson")%>';
    	var test=eval(<%=request.getAttribute("resultJson")%>);
        var BdcScdjInfoList='<%=request.getAttribute("BdcScdjInfoList")%>';
        
        if(BdcScdjInfoList!==""){
          BdcScdjInfoList=JSON.parse(BdcScdjInfoList);
         for(var k in BdcScdjInfoList){
     
         $("#DYQRXX_NAME_LIST").append('<option value="' + BdcScdjInfoList[k].DYQRXX_NAME + '">' + BdcScdjInfoList[k].DYQRXX_NAME + '</option>');
         $("#DYQRXX_NATURE").append('<option value="' + BdcScdjInfoList[k].DYQRXX_NATURE + '">' + BdcScdjInfoList[k].DYQRXX_NATURE + '</option>');
         $("#DYQRXX_IDNO").append('<option value="' + BdcScdjInfoList[k].DYQRXX_IDNO + '">' + BdcScdjInfoList[k].DYQRXX_IDNO + '</option>');
    
       }
     }    
    	//var test=JSON.parse(flowobj);//$.parseJSON(flowobj);// JSON2.parse(flowobj);
    	//document.getElementById("XKCONTENT").value=test["XKCONTENT"];
    	$("textarea[name='XKCONTENT']").val(test["XKCONTENT"]);
    	$("textarea[name='SDCONTENT']").val(test["SDCONTENT"]);
    	$("textarea[name='RUN_MODE']").val(test["RUN_MODE"]);
    	
    	var fields = $("#islong_term");
    	if(test["ISLONG_TERM"]=="1"){
    		fields.attr("checked","checked");
    		$("#closespan1").css("display","none"); 
			$("#closespan2").css("display","none"); 
    	}
    	/**
		//?????????????????????
		AppUtil.initNetUploadMaters({
			busTableName:"T_BSFW_XMBAQSZHPS"
		});
		**/
		var fileids=test["RESULT_FILE_ID"];
		$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    		 if(resultJson!="websessiontimeout"){
	    		 	$("#fileListDiv").html("");
	    		 	var newhtml = "";
	    		 	var list=resultJson.rows;
	    		 	for(var i=0; i<list.length; i++){
	    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 		newhtml+=list[i].FILE_NAME+'</a>';
						if (list[i].FILE_NAME.indexOf("png") != -1 || list[i].FILE_NAME.indexOf("PNG") != -1 || list[i].FILE_NAME.indexOf("jpg") != -1 || list[i].FILE_NAME.indexOf("jpg") != -1) {
							newhtml +="<a href=\"javascript:void(0);\"  onclick=\"previewFile('"+list[i].FILE_PATH+
									"');\" ><font color=\"red\">??????</font></a>";
						}
	    		 		newhtml+='</p>';
	    		 	} 
	    		 	$("#fileListDiv").html(newhtml);
	    		 }
         });
	});
	
	function previewFile(filePath){
        //??????
        var previewPhotoStr = "<img class='testImg' style='float:left;'?? src='${_file_Server}"+filePath+"' width='214' height='160'/>";
		$("#previewPhoto").html(previewPhotoStr);
	}
	
	function validatCode(){
		var vcode=$("input[name='vcode']").val()
		if(null!=vcode && ''!=vcode){
			$.post("executionController/lookResult.do",{vcode:vcode}, function(responseText) {	
				var resultJson = $.parseJSON(responseText);			
				if(resultJson.success){
					$("#T_FLOW_RESULT_FORM").show();
					$("#validatTable").hide();
				}else{
					art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			 });			
		}else{
			alert("?????????????????????");
		}
	}
	
	
	
	function highSearch(){
	
	 var BdcScdjInfoList='<%=request.getAttribute("BdcScdjInfoList")%>';
   
  BdcScdjInfoList=JSON.parse(BdcScdjInfoList);
    //?????????????????????????????????????????????
    var itemName="${itemName}";
    var transactor="${transactor}";
    var itemId="${itemId}";
    var CORP_NAME= $("input[name='DYQRXX_NAME']").val();
    var	CORP_CODE="";
    var type="";
    if(BdcScdjInfoList.length>0){
    for(var i in BdcScdjInfoList){
    if(CORP_NAME==BdcScdjInfoList[i].DYQRXX_NAME){
     CORP_CODE =BdcScdjInfoList[i].DYQRXX_IDNO;
     type=BdcScdjInfoList[i].DYQRXX_NATURE;
    }
    }
    }
	if(type=='1'){
	type='persona';
	}
    
    var url = "creditController.do?licenseDatagrid" + "&itemName=" + itemName + "&transactor=" + "???????????????" + "&itemId=" + itemId +"&IS_HIGH_LEVEL=1"
    +"&CORP_NAME="+CORP_NAME+"&CORP_CODE="+CORP_CODE;
    
    var HIGH_CREDIT_NAME = $("input[name='HIGH_CREDIT_NAME']").val();
    var HIGH_CREDIT_CODE = $("input[name='HIGH_CREDIT_CODE']").val();
     if (HIGH_CREDIT_NAME && HIGH_CREDIT_NAME != "") {
        url += "&HIGH_CREDIT_NAME=" + HIGH_CREDIT_NAME;
  
        
    }else{
        alert("?????????????????????????????????????????????");
        return;
    }
    if (HIGH_CREDIT_CODE && HIGH_CREDIT_CODE != "") {
        url += "&HIGH_CREDIT_CODE=" + HIGH_CREDIT_CODE;
   
    }else{
      alert("??????????????????????????????");
      return;
    } 
    console.log(url);
    girdDatagrid(encodeURI(url));
}
	 function girdDatagrid(url) {
	 
        var timestamp = (new Date()).getTime();
    
		$.ajax({
        url:url,
        type:"get",
        async: true,
        success:function(response){
        //console.log(response);
          if(response.total>0){
           var fileId=response.rows[0].FileID;
           var fileSuffix=response.rows[0].FileSuffix;
           var licenseID=response.rows[0].LicenseID;
           var typeCode =response.rows[0].TypeCode;
           var type =response.rows[0].type;
           var name=response.rows[0].certificateHolderName;
           var code=response.rows[0].certificateHolderCode;
           var licenseName=response.rows[0].certificateName;
           showFile(fileId,licenseID,typeCode,type,name,timestamp,fileSuffix,code,licenseName);
          }
          } 
        
        })
   

	}
	function setDyqrxxName(){

	$("input[name='DYQRXX_NAME']").attr("value",$("#DYQRXX_NAME_LIST").val());
	
	}
	//????????????????????????
function showFile(fileId,licenseID,typeCode,type,name,timestamp,FileSuffix,code,licenseName){
        $("#"+timestamp).attr("onclick","uploadFile('" + fileId + "','" + licenseID + "','" + typeCode + "','" + type+ "','" + name+"')");
        var noprint='2';
        var queryName=licenseName;
        var queryCode=code;
        //?????????????????????????????????????????????
        var itemName="${itemName}";
        var transactor="${transactor}";
        var itemId="${itemId}";
        var url=encodeURI("creditController.do?showFile&fileId="+fileId+"&licenseID="+licenseID+"&FileSuffix="+FileSuffix
            +"&queryName="+queryName+"&queryCode="+queryCode+"&licenseName="+name
            +"&itemName="+itemName+"&transactor="+transactor+"&itemId="+itemId
            +"&typeCode="+typeCode+"&type="+type+"&noprint="+noprint);
          window.open(url, "_blank");
       

	}
</script>

</head>

<body>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="validatTable"> 
		<td>
			<span style="width: 160px;float:right;text-align:right;">????????????</span>
		</td>
		<td colspan="3">
			<input name="vcode" id="vcode" type="text" class="validate[required],maxSize[4]" style="width:105px; height: 24px;" />					
			<img name="vc" id="randpic" style="height: 26px;margin-top: -5px;width:80px;"
			title="?????????????????????" alt="?????????" src="<%=path %>/rand.jpg" align="middle"
			onclick="changeRandPic();" style="cursor:pointer"/>
			<a class="eflowbutton" onclick="validatCode();" style="height: 28px;">??????</a>
		</td>
	</table>
   <form id="T_FLOW_RESULT_FORM" method="post" style="display:none;">
   	<input type="hidden" id="resultJson" name="resultJson" value="${resultJson}" />
   	<input type="hidden" id="flowResult" name="flowResult" value="${flowResult}" />
    <%--===================????????????????????????=========== --%>
   
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" >
		<tr>
			<th colspan="4">????????????</th>
		</tr>
		<tr>
		<tr>
		
        </tr>
				<td><span style="width: 160px;float:right;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>????????????????????????
				???</span>
				</td>
				<td><input type="hidden" name="RESULT_FILE_URL" >
				<input type="hidden" name="RESULT_FILE_ID">
				<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
					<input type="text" id="xkfile_num" name="XKFILE_NUM" value="${flowResult.XKFILE_NUM}"
							class="eve-input validate[required,maxSize[60]]" maxlength="60" style="width:180px;" />
			
				</td>
				<td><span style="width: 160px;float:right;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>????????????????????????
				???</span>
				</td>
				<td>
					<input type="text" id="xkfile_name" name="XKFILE_NAME" value="${flowResult.XKFILE_NAME}"
							class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" />
							
				</td>
		</tr>
		<c:if test="${itemXz == 'XK'}">
			<tr>
				<td><span style="width: 160px;float:right;text-align:right;">??????????????????????????????<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_num" name="xkdocument_num"
						   class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" value="${flowResult.XKDOCUMENT_NUM}"/>
				</td>
				<td><span style="width: 160px;float:right;text-align:right;">?????????????????????????????????<font class="dddl_platform_html_requiredFlag">*</font>
				</span>
				</td>
				<td>
					<input type="text" id="xkdocument_name" name="xkdocument_name"
						   class="eve-input validate[required,maxSize[120]]" maxlength="120" style="width:180px;" value="${flowResult.XKDOCUMENT_NAME}" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 160px;float:right;text-align:right;">???????????????
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="XZXKLB"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="??????????????????" name="xk_type" id="xk_type" value="${flowResult.XK_TYPE}">
					</eve:eveselect>
				</td>
				<td><span style="width: 160px;float:right;text-align:right;">?????????????????????
				<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdecide_time" name="xkdecide_time"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'close_time\')}'})"
						   readonly="true" class="eve-input Wdate validate[required]" maxlength="60" style="width:180px;height:22px;" value="${flowResult.XKDECIDE_TIME}" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 160px;float:right;text-align:right;">???????????????????????????????????????
			<font class="dddl_platform_html_requiredFlag">*</font></span></td>
				<td>
					<input type="text" id="xk_usc" name="xk_usc"
						   class="eve-input validate[required]" maxlength="120" style="width:180px;" value="${flowResult.XK_USC}"/>
				</td>
				<td><span style="width: 160px;float:right;text-align:right;">????????????
			<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xk_holder" name="xk_holder"
						   class="eve-input validate[required]" maxlength="120" style="width:180px;" value="${flowResult.XK_HOLDER}" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 160px;float:right;text-align:right;">???????????????????????????</span>
				<td>
					<input type="text" id="xkdept_swdjh" name="xkdept_swdjh" value="${flowResult.XKDEPT_SWDJH}"
						   class="eve-input validate[]" maxlength="15" style="width:180px;" />
				</td>
				<td><span style="width: 160px;float:right;text-align:right;">???????????????????????????????????????</span>
				<td>
					<input type="text" id="xkdept_sydwzsh" name="xkdept_sydwzsh" value="${flowResult.XKDEPT_SYDWZSH}"
						   class="eve-input validate[]" maxlength="12" style="width:180px;" />
				</td>
			</tr>
			<tr>
				<td><span style="width: 160px;float:right;text-align:right;">?????????????????????<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdept_sjlydw" name="xkdept_sjlydw" value="${flowResult.XKDEPT_SJLYDW}"
						   class="eve-input validate[required]" maxlength="200" style="width:180px;" />
				</td>
				<td><span style="width: 160px;float:right;text-align:right;">?????????????????????????????????????????????<font class="dddl_platform_html_requiredFlag">*</font></span>
				<td>
					<input type="text" id="xkdept_sjlydw_usc" name="xkdept_sjlydw_usc" value="${flowResult.XKDEPT_SJLYDW_USC}"
						   class="eve-input validate[required]" maxlength="18" style="width:180px;" />
				</td>
			</tr>
		</c:if>
		<tr>
				<td><span style="width: 160px;float:right;text-align:right;">?????????????????????
				</span>
				<input type="hidden"  name="xkdept_id" id="xkdept_id"/>
				</td>
				<td>
					<input type="text" id="xkdept_name" name="XKDEPT_NAME" value="${flowResult.XKDEPT_NAME}"
					readonly="readonly" 
							class="eve-input" maxlength="60" style="width:180px;" />
				</td>
				<td><span style="width: 160px;float:right;text-align:right;">???????????????</span>
				</td>
				<td align="left">
					<input type="checkbox" id="islong_term" name="ISLONG_TERM" onclick="longChange();" 
							 style="width:30px;padding-left: 0px;margin-left: 0px;" />
				</td>
		</tr>
		<tr>
				<td><span style="width: 160px;float:right;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>????????????
				???</span>
				</td><td>
					<input type="text" id="effect_time" name="EFFECT_TIME" value="${flowResult.EFFECT_TIME}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'close_time\')}'})" 
					readonly="true" class="tab_text Wdate" maxlength="60" style="width:160px;" />
				</td>
				<td><span id="closespan1" style="width: 160px;float:right;text-align:right;" ><font class="dddl_platform_html_requiredFlag">*</font>????????????
				???</span>
				</td><td><span id="closespan2">
					<input type="text" id="close_time" name="CLOSE_TIME"  value="${flowResult.CLOSE_TIME}"
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'effect_time\')}'})" 
					readonly="true" class="tab_text Wdate validate[required]" style="width:160px;" /></span>
				</td>
		</tr>
		<tr height="80px"> 
			<td ><span style="width: 160px;float:right;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>??????????????????
			???
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="5" cols="6"
					class="eve-textarea validate[required,maxSize[1500]]" value="${flowResult.XKCONTENT}"
					maxlength="1600" style="width: 500px" name="XKCONTENT" id="XKCONTENT"></textarea>
			</td>
		</tr>
		<tr height="80px"> 
			<td ><span style="width: 160px;float:right;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>????????????
			???
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="5" cols="6"
					class="eve-textarea validate[required,maxSize[1500]]" value="${flowResult.SDCONTENT}"
					maxlength="1500" style="width: 500px" name="SDCONTENT" id="SDCONTENT"></textarea>
			</td>
		</tr>
		<tr height="70px"> 
			<td ><span style="width: 160px;float:right;text-align:right;">????????????
			???
			     </span>
			</td>
			<td colspan="3">
			 <textarea rows="4" cols="6"
					class="eve-textarea validate[maxSize[500]]" value="${flowResult.RUN_MODE}"
					maxlength="500" style="width: 500px" name="RUN_MODE"></textarea>
			</td>
		</tr>
		<tr>
			<td ><span style="width: 160px;float:right;text-align:right;"><font class="dddl_platform_html_requiredFlag">*</font>??????
			???
			     </span>
			</td>
			<td colspan="3">
				<div style="width:100%;" id=fileListDiv></div>
			</td>
		</tr>
		<c:if test="${!empty  flowResult.uplogList}">
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;">????????????</td>
						    </tr>
						</c:if>
						<c:forEach items="${ flowResult.uplogList}" var="logInfo" varStatus="varStatus">
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">???????????????</span>
									
								</td>
								<td colspan="3">${logInfo.FULLNAME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">???????????????</span>
									
								</td>
								<td colspan="3">${logInfo.OPERATE_TIME}</td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">???????????????</span>
								</td>
								<td colspan="3"><pre style="word-wrap: break-word; white-space: pre-wrap;">${logInfo.OPERATE_CONTENT}</pre></td>
							</tr>
							<tr style="height:29px;">
								<td colspan="4" class="dddl-legend" style="font-weight:bold;background:#FFFFF0 ;"></td>
						    </tr>
						</c:forEach>
						
						
					
	
	<c:if test="${isBdcScdydjFlag.isBdcScdydjFlag == '1'}">
	               <tr style="width:150%;">
	               <td colspan="4">
                     <span style="margin-left: 200px;">?????????????????????</span>
					<input name="DYQRXX_NAME" type="hidden" value=""/>
                    <select class="col-xs-16" id="DYQRXX_NAME_LIST" style="padding: 2px 2px; "  onchange="setDyqrxxName()">
                          <option value='' disabled selected style='display:none;'>?????????</option>
                    </select>
                    <span style="margin-left: 20px;">??????????????????????????????</span>
                    <!-- ????????????????????? -->
                    <input class="col-xs-16" name="HIGH_CREDIT_NAME" id="HIGH_CREDIT_NAME" style="padding: 2px 2px;" value="?????????????????????"/>
                    <span style="margin-left: 20px;">????????????????????????</span>
                    <input type="text" class="eve-input" name="HIGH_CREDIT_CODE"  id="HIGH_CREDIT_CODE" style="padding: 2px 2px;width: 220px;" value= ${flowResult.XKFILE_NUM} >
                    <button style="margin-left: 15px;" type="button" onclick="highSearch()">?????????????????????</button>
                    </td>
                    </tr> 
        </c:if>
      </table>  
	<center>
        <div id="previewPhoto" style="overflow-y:auto; width:1090px; height:160px;">
        </div>
	</center>
	<div class="easyui-layout" fit="true">
	
	
	
	</div>
	</form>
</body>
</html>
