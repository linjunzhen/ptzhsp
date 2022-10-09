<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	function downloadApply(){		
		var flowSubmitObj = FlowUtil.getFlowObj();
		var exeId = flowSubmitObj.EFLOW_EXEID;
		var itemCode = '${serviceItem.ITEM_CODE}';
		
		window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApplyFront.do?exeId="+exeId
			+"&itemCode="+itemCode;
	}
	
	 /**
      * 标题附件上传对话框
      */
     function openResultFileUploadDialog3(){
         //定义上传的人员的ID
         var uploadUserId = $("input[name='uploadUserId']").val();
         //定义上传的人员的NAME
         var uploadUserName = $("input[name='uploadUserName']").val();
         //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
         art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_COMPANY&uploadUserId='
             +uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
             title: "上传(附件)",
             width: "620px",
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
                         var fileid=$("input[name='FILE_ID3']").val();
                         if(fileid!=""&&fileid!=null){
                             $("input[name='FILE_ID3']").val(fileid+";"+uploadedFileInfo.fileId);
                         }else{
                             $("input[name='FILE_ID3']").val(uploadedFileInfo.fileId);
                         }
                         var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                         spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
                         spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                         spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile3('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
                         $("#fileListDiv3").append(spanHtml);
                     }
                 }
                 art.dialog.removeData("uploadedFileInfo");
             }
         });
      }
      /**
      * 标题附件上传对话框
      */
     function openResultFileUploadDialog4(){
         //定义上传的人员的ID
         var uploadUserId = $("input[name='uploadUserId']").val();
         //定义上传的人员的NAME
         var uploadUserName = $("input[name='uploadUserName']").val();
         //上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
         art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_COMPANY&uploadUserId='
             +uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
             title: "上传(附件)",
             width: "620px",
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
                         var fileid=$("input[name='FILE_ID4']").val();
                         if(fileid!=""&&fileid!=null){
                             $("input[name='FILE_ID4']").val(fileid+";"+uploadedFileInfo.fileId);
                         }else{
                             $("input[name='FILE_ID4']").val(uploadedFileInfo.fileId);
                         }
                         var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
                         spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+uploadedFileInfo.fileId+"');\">");
                         spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
                         spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile4('"+uploadedFileInfo.fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
                         $("#fileListDiv4").append(spanHtml);
                     }
                 }
                 art.dialog.removeData("uploadedFileInfo");
             }
         });
      }
      
      function delUploadFile3(fileId,attacheKey){
         //AppUtil.delUploadMater(fileId,attacheKey);
         art.dialog.confirm("您确定要删除该文件吗?", function() {
             //移除目标span
             $("#"+fileId).remove();
             var FILE_ID3=$("input[name='FILE_ID3']").val();
             var arrayIds=FILE_ID3.split(";");
             for(var i=0;i<arrayIds.length;i++){
                 if(arrayIds[i]==fileId){
                     arrayIds.splice(i,1);
                     break;
                 }
             }
             $("input[name='FILE_ID3']").val(arrayIds.join(";"));
         });
     }
      
      function delUploadFile4(fileId,attacheKey){
         //AppUtil.delUploadMater(fileId,attacheKey);
         art.dialog.confirm("您确定要删除该文件吗?", function() {
             //移除目标span
             $("#"+fileId).remove();
             var FILE_ID4=$("input[name='FILE_ID4']").val();
             var arrayIds=FILE_ID4.split(";");
             for(var i=0;i<arrayIds.length;i++){
                 if(arrayIds[i]==fileId){
                     arrayIds.splice(i,1);
                     break;
                 }
             }
             $("input[name='FILE_ID4']").val(arrayIds.join(";"));
         });
     }
     
     $(function(){
        var fileids="${busRecord.FILE_ID3 }";
        $.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
            if(resultJson!="websessiontimeout"){
                $("#fileListDiv3").html("");
                var newhtml = "";
                var list=resultJson.rows;
                for(var i=0; i<list.length; i++){
                    newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                    newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+=list[i].FILE_NAME+'</a>';
                    newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                    newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                    newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a></p>"
                    newhtml+='</p>';
                }
                $("#fileListDiv3").html(newhtml);
            }
        });
           var ybfileids="${busRecord.FILE_ID4 }";
           $.post("executionController.do?getResultFiles&fileIds="+ybfileids,{fileIds:ybfileids}, function(resultJson) {
               if(resultJson!="websessiontimeout"){
                   $("#fileListDiv4").html("");
                   var newhtml = "";
                   var list=resultJson.rows;
                   for(var i=0; i<list.length; i++){
                       newhtml+="<p id='"+list[i].FILE_ID+"' style='margin-left: 5px; margin-right: 5px;line-height: 20px;'>";
                       newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                       newhtml+=list[i].FILE_NAME+'</a>';
                       newhtml +='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
                       newhtml+="<span style='margin:0px 10px;'>下载</span>"+'</a>';
                       newhtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile1('"+list[i].FILE_ID+"');\" ><font color=\"red\">删除</font></a></p>"
                       newhtml+='</p>';
                   }
                   $("#fileListDiv4").html(newhtml);
               }
           });
        
        if('${busRecord.IS_TAX_BANKACCOUNT}'=='1'){
			$("#taxbankaccount").attr("style","");
        }
        if('${busRecord.IS_APPLY_INVOICE}'=='1'){
        	$("#invoiceapplyDiv").attr("style","");
			$("input[name='TOTAL_AMOUNT']").addClass("validate[required],custom[numberplus]");
			$("input[name='SERVICE_PROVIDER']").addClass("validate[required]");
			$("select[name='INVOICE_RECIVEWAY']").addClass("validate[required]");
			$("input[name='INVOICE_BUYER']").addClass("validate[required]");
			$("input[name='INVOICE_BUYER_PHONE']").addClass("validate[required],custom[mobilePhoneLoose]");
			$("select[name='INVOICE_BUYER_IDTYPE']").addClass("validate[required]");
			$("input[name='INVOICE_BUYER_NO']").addClass("validate[required]");
        }
		if('${requestParams.ISGETBILL}' == '1' || '${busRecord.ISGETBILL}' == '1'){		
			$("input[name='IS_TAX_BANKACCOUNT']").click(function(){
				var Value = $(this).val();
				if(Value=='1'){
					$("#taxbankaccount").attr("style","");
					$("#taxbankaccount").find("[name='ACCOUNT_NATURE']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNT_CERTNO']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='RELEASE_DATE']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='DIVISION']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='BANK_CATEGORY']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='DEPOSIT_BANK']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNT_NAME']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNTOPEN_DATE']").addClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNT_NO']").addClass("validate[required]");
					
					var companyName = $("#COMPANY_NAME").val();
					var accountName = $("#YhzhTable tbody:first").find("[name='ACCOUNT_NAME']").val();
					if(accountName){
					}else{
    					$("#YhzhTable tbody:first").find("[name='ACCOUNT_NAME']").val(companyName);
					}
				}else{
					$("#taxbankaccount").attr("style","display: none;");
					$("#taxbankaccount").find("[name='ACCOUNT_NATURE']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNT_CERTNO']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='RELEASE_DATE']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='DIVISION']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='BANK_CATEGORY']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='DEPOSIT_BANK']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNT_NAME']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNTOPEN_DATE']").removeClass("validate[required]");
					$("#taxbankaccount").find("[name='ACCOUNT_NO']").removeClass("validate[required]");
				}
			});
		}
		if('${requestParams.ISGETBILL}' == '1' || '${busRecord.ISGETBILL}' == '1'){		
			$("input[name='IS_APPLY_INVOICE']").click(function(){
				var Value = $(this).val();
				if(Value=='1'){
					$("#invoiceapplyDiv").attr("style","");
					$("#invoiceapplyDiv").find("[name='INVOICE_TYPE']").addClass("validate[required]");
					$("#invoiceapplyDiv").find("[name='BILLING_LIMIT']").addClass("validate[required]");
					$("#invoiceapplyDiv").find("[name='APPLY_NUM']").addClass("validate[required],custom[integerplus]");
					$("input[name='TOTAL_AMOUNT']").addClass("validate[required],custom[numberplus]");
					$("input[name='SERVICE_PROVIDER']").addClass("validate[required]");
					$("select[name='INVOICE_RECIVEWAY']").addClass("validate[required]");
					$("input[name='INVOICE_BUYER']").addClass("validate[required]");
					$("input[name='INVOICE_BUYER_PHONE']").addClass("validate[required],custom[mobilePhoneLoose]");
					$("select[name='INVOICE_BUYER_IDTYPE']").addClass("validate[required]");
					$("input[name='INVOICE_BUYER_NO']").addClass("validate[required]");
				}else{
					$("#invoiceapplyDiv").attr("style","display: none;");
					$("#invoiceapplyDiv").find("[name='INVOICE_TYPE']").removeClass("validate[required]");
					$("#invoiceapplyDiv").find("[name='BILLING_LIMIT']").removeClass("validate[required]");
					$("#invoiceapplyDiv").find("[name='APPLY_NUM']").removeClass("validate[required],custom[integerplus]");
					$("input[name='TOTAL_AMOUNT']").removeClass("validate[required],custom[numberplus]");
					$("input[name='SERVICE_PROVIDER']").removeClass("validate[required]");
					$("select[name='INVOICE_RECIVEWAY']").removeClass("validate[required]");
					$("input[name='INVOICE_BUYER']").removeClass("validate[required]");
					$("input[name='INVOICE_BUYER_PHONE']").removeClass("validate[required],custom[mobilePhoneLoose]");
					$("select[name='INVOICE_BUYER_IDTYPE']").removeClass("validate[required]");
					$("input[name='INVOICE_BUYER_NO']").removeClass("validate[required]");
				}
			});
		}
	});
		
</script>
<style type="text/css">
	.textbox{
		width: 280px!important;
		height: 40px!important;
		border: none!important;
	}
	.textbox .textbox-text{
		width: 280px!important;
		height: 40px!important;
		line-height: 40px!important;
		font-size: 16px!important;
		color: #000000!important;
		padding: 0 10px!important;
		box-sizing: border-box!important;
		border: 1px solid #c9deef!important;
	}
	.syj-title1{
		text-align: left!important;
		padding: 5px 0 5px 10px!important;
		color: #666666!important;
	}
</style>
<form action="" id="OTHER_FORM"  >

	<c:if test="${requestParams.ISGETBILL == '1' || busRecord.ISGETBILL == '1'}">
		<div class="syj-title1 ">
			<span>新办企业税务套餐登记信息</span>
		</div>
		<div style="position:relative;">
			<div style="height: 100px;">
				<img src="webpage/website/applyforms/zzhy/images/smdj_QRcode.jpg" height="100px" width="100px">
				<span style="color: red;line-height: 100px;font-size: 18px;">请法定代表人、财务负责人、办税人员扫描二维码办理实名注册</span>
			</div>
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th colspan="4" class="syj-title1">纳税人资格类型登记</th>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" class="syj-table2">
				<tr>
					<th style="border-top: none;"><font class="syj-color2">*</font>纳税人类别：</th>
					<td style="border-top: none;"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
						name="TAXPAYER_CATEGORY" placeholder="请输入纳税人类别" maxlength="32" value="企业,企业性单位"/></td>
					<th style="border-top: none;"><font class="syj-color2">*</font>主营业务类别：</th>
					<td style="border-top: none;">
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="MainBusinessCategory"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择主营业务类别" name="BUSINESS_CATEGORY" value="${busRecord.BUSINESS_CATEGORY}">
						</eve:eveselect>
					</td>
				</tr>				
				<tr>
					<th><font class="syj-color2">*</font>增值税纳税人资格类型：</th>
					<td colspan="3">
						<eve:radiogroup fieldname="TAXPAYER_QTYPE" width="" typecode="TAXPAYERQTYPE" defaultvalue="增值税一般纳税人" value="${busRecord.TAXPAYER_QTYPE}"></eve:radiogroup>
					</td>					
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>一般纳税人资格生效之日：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="EffectiveDate"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择生效之日" name="EFFECTIVE_DATE" value="${busRecord.EFFECTIVE_DATE}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>会计核算是否健全：</th>
					<td>
						<input type="radio" class="radio validate[required]" name="IS_SOUNDACCOUNT" value="是" <c:if test="${busRecord.IS_SOUNDACCOUNT!='否'}">checked="checked"</c:if> />是
						<input type="radio" class="radio validate[required]" name="IS_SOUNDACCOUNT" value="否" <c:if test="${busRecord.IS_SOUNDACCOUNT=='否'}">checked="checked"</c:if> />否
					</td>
				</tr>
				<tr>
					<td colspan="4" style="color: red;font-family: serif;border: none;">
						<p>1、一般纳税人资格生效之日默认为申请当月1日，如选择次月1日，将暂时无法核定、领用增值税专用发票。请您确认！</p>
						<p>2、成为一般纳税人后可自主开具增值税专用发票，在收到增值税专用发票后可按规定抵扣税款，一般纳税人按月度进行增值税及附征申报。</p>
					</td>
				</tr>
				<tr>
					<th> 是否办理银行账户信息：</th>
					<td colspan="3">
						<input type="radio" class="radio validate[required]" name="IS_TAX_BANKACCOUNT" value="1" <c:if test="${busRecord.IS_TAX_BANKACCOUNT=='1'}">checked="checked"</c:if> />是
						<input type="radio" class="radio validate[required]" name="IS_TAX_BANKACCOUNT" value="0" <c:if test="${busRecord.IS_TAX_BANKACCOUNT!='1'}">checked="checked"</c:if> />否
					</td>
				</tr>
			</table>
			<div id="taxbankaccount" style="display: none;">
				<div style="float: right; margin-right: 100px;">
					<a href="javascript:void(0);" onclick="javascript:addYhzhDiv();" class="syj-addbtn" style="position: absolute;margin-top: 10px;">添 加</a>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th colspan="4" class="syj-title1">银行账号信息及委托扣款协议</th>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="syj-table2" id="YhzhTable" style="table-layout: auto;">
					<c:if test="${empty accountAndAgreementList}">
						<tbody>
							<tr>
								<th><font class="syj-color2">*</font>账户性质：</th>
								<td>
									<eve:eveselect clazz="input-dropdown w280 " dataParams="accountnature"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择账户性质" name="ACCOUNT_NATURE" >
									</eve:eveselect>
								</td>
								<th><font class="syj-color2">*</font>银行开户登记证号：</th>
								<td><input type="text" class="syj-input1 " 
									name="ACCOUNT_CERTNO" placeholder="请输入银行开户登记证号" maxlength="32"/></td>
							</tr>
							<tr>
								<th><font class="syj-color2">*</font>发放日期：</th>
								<td>
									<input type="text" class="syj-input1 Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})"
										readonly="readonly" name="RELEASE_DATE"  placeholder="请选择发放日期" />
								</td>
								<th><font class="syj-color2">*</font>行政区划：</th>
								<td>
									
						            <input name="DIVISION" id="DIVISION" class="syj-input1 easyui-combotree"/>
						            
								</td>
							</tr>
							<tr>
								<th><font class="syj-color2">*</font>银行行别：</th>
								<td>
									<eve:eveselect clazz="input-dropdown w280" dataParams="BankCategory"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择银行行别" name="BANK_CATEGORY" >
									</eve:eveselect>
								</td>
								<th><font class="syj-color2">*</font>开户银行：</th>
								<td><input type="text" class="syj-input1 " 
									name="DEPOSIT_BANK" placeholder="请输入开户银行" maxlength="32"/></td>
							</tr>
							<tr>
								<th><font class="syj-color2">*</font>账户名称：</th>
								<td>
									<input type="text" class="syj-input1 " 
									name="ACCOUNT_NAME" placeholder="请输入账户名称" maxlength="32"/>
								</td>
								<th><font class="syj-color2">*</font>账号：</th>
								<td><input type="text" class="syj-input1 " 
									name="ACCOUNT_NO" placeholder="请输入账号" maxlength="32"/></td>
							</tr>
							<tr>
								<th> 币种：</th>
								<td>
									<input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
									name="ACCOUNT_CURRENCY" value="人民币" maxlength="32"/>
								</td>
								<th> 账户标识：</th>
								<td><eve:eveselect clazz="input-dropdown w280" dataParams="ACCOUNTIDENTIFY"
									dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="首选缴税账号"
									name="ACCOUNT_IDENTIFY" >
									</eve:eveselect>
								</td>
							</tr>				
							<tr>
								<th><font class="syj-color2">*</font>开户日期：</th>
								<td>
									<input type="text" class="syj-input1 Wdate " onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M-%d'})"
										readonly="readonly" name="ACCOUNTOPEN_DATE"  placeholder="请选择开户日期" />
								</td>
								<th> 委托扣款协议账户：</th>
								<td>								
									<eve:eveselect clazz="input-dropdown w280" dataParams="YesOrNo" value="${accountAndAgreement.IS_ENTRUST }" 
										dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="1"
										name="IS_ENTRUST" >
									</eve:eveselect>
								</td>
							</tr>
						</tbody>
					</c:if>
					<jsp:include page="./initYhzhDiv.jsp"></jsp:include>
				</table>
			</div>
			
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>					
					<th> 是否办理发票和税控设备申领：</th>
					<td colspan="3">
						<input type="radio" class="radio validate[required]" name="IS_APPLY_INVOICE" value="1" <c:if test="${busRecord.IS_APPLY_INVOICE=='1'}">checked="checked"</c:if> />是
						<input type="radio" class="radio validate[required]" name="IS_APPLY_INVOICE" value="0" <c:if test="${busRecord.IS_APPLY_INVOICE!='1'}">checked="checked"</c:if> />否
					</td>
				</tr>
			</table>
			<div id="invoiceapplyDiv" style="display: none;">
				<div style="float: right; margin-right: 100px;">
					<a href="javascript:void(0);" onclick="javascript:addInvoiceApplyDiv();" class="syj-addbtn" style="position: absolute;margin-top: 10px;">添 加</a>
				</div>
				<table cellpadding="0" cellspacing="0" class="syj-table2">
					<tr>
						<th colspan="4" class="syj-title1">发票核定及申领</th>					
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="syj-table2" id="invoiceapplyTable">
					<c:if test="${empty invoiceapplyList}">
						<tbody>
							<tr>								
								<th><font class="syj-color2">*</font>发票种类：</th>
								<td>
									<eve:eveselect clazz="input-dropdown w280" dataParams="invoiceType"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择发票种类" name="INVOICE_TYPE" >
									</eve:eveselect>
								</td>
								<th><font class="syj-color2">*</font>单份发票最高开票限额：</th>
								<td>
									<eve:eveselect clazz="input-dropdown w280" dataParams="billingLimit"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="请选择开票限额" name="BILLING_LIMIT" >
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th><font class="syj-color2">*</font>申请数量：</th>
								<td colspan="3"><input type="text" class="syj-input1 " value="25"
									name="APPLY_NUM" placeholder="请输入申请数量" maxlength="3"/></td>
							</tr>
						</tbody>
					</c:if>
					<jsp:include page="./initinvoiceapplyDiv.jsp"></jsp:include>										
				</table>				
				<table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top:10px;table-layout: auto;">					
					<th><font class="syj-color2">*</font>定额发票累计领票金额：</th>
					<td colspan="3"><input type="text" class="syj-input1 " value="${busRecord.TOTAL_AMOUNT}"
						name="TOTAL_AMOUNT" placeholder="请输入累计领票金额" maxlength="16"/></td>
					<tr>		
						<th> 税控开票设备服务厂商：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280" dataParams="serviceProvider"
							dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.SERVICE_PROVIDER}"
							defaultEmptyText="请选择服务厂商" name="SERVICE_PROVIDER" >
							</eve:eveselect>
						</td>		
						<th><font class="syj-color2">*</font>领取方式：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280" dataParams="invoiceReciveway" onchange="recivewayselect(this.value)"
							dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INVOICE_RECIVEWAY}"
							defaultEmptyText="请选择领取方式" name="INVOICE_RECIVEWAY" >
							</eve:eveselect>
							<p style="display: none;" id="postselect"><span style="font-size: 10px;color: red;">税控设备邮递地址默认同营业执照及公章邮寄地址</span></p>
						</td>	
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>购票人：</th>
						<td><input type="text" class="syj-input1 " value="${busRecord.INVOICE_BUYER==null?(sessionScope.curLoginMember.USER_TYPE=='1'?sessionScope.curLoginMember.YHMC:sessionScope.curLoginMember.JBRXM):busRecord.INVOICE_BUYER}"
							name="INVOICE_BUYER" placeholder="请输入购票人姓名" maxlength="16"/></td>
						<th><font class="syj-color2">*</font>联系电话：</th>
						<td><input type="text" class="syj-input1 " value="${busRecord.INVOICE_BUYER_PHONE==null?(sessionScope.curLoginMember.USER_TYPE=='1'?sessionScope.curLoginMember.SJHM:''):busRecord.INVOICE_BUYER_PHONE}"
							name="INVOICE_BUYER_PHONE" placeholder="请输入购票人联系电话" maxlength="16"/></td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>购票人身份证件种类：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280" dataParams="DocumentType" defaultEmptyValue="SF"
							dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.INVOICE_BUYER_IDTYPE}"
							defaultEmptyText="请选择身份证件种类" name="INVOICE_BUYER_IDTYPE" >
							</eve:eveselect>
						</td>
						<th><font class="syj-color2">*</font>购票人身份证件号码：</th>
						<td><input type="text" class="syj-input1 " value="${busRecord.INVOICE_BUYER_NO==null?(sessionScope.curLoginMember.USER_TYPE=='1'?sessionScope.curLoginMember.ZJHM:sessionScope.curLoginMember.JBRSFZ):busRecord.INVOICE_BUYER_NO}"
							name="INVOICE_BUYER_NO" placeholder="请输入购票人联系电话" maxlength="18"/></td>
					</tr>
				
					<tr>
						<td colspan="4" style="color: red;font-family: serif;border: none;">
							<p>1、新办纳税人首次申领发票，增值税专用发票最高开票限额不超过10万元，每月最高领用数量25份以内，增值税普通发票最高开票限额不超过10万元，每月最高领用数量50份以内。可通过网上办税服务厅发起申请，超过上述标准的，请在办税服务厅领取发票时根据实际经营情况重新核定。</p>
							<p>2、选择税控开票设备服务厂商后，需自行前往服务单位购买设备，然后携带设备到主管机关办税服务厅完成初始发行并领取发票。</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</c:if>
	<c:if test="${busRecord.ISSOCIALREGISTER==1||requestParams.ISSOCIALREGISTER==1}">
	<div class="syj-title1 ">
		<span>社会保险登记信息</span>
		<select   style="margin-left: 18px;" class="input-dropdown " onchange="setInfoMsg(this.value,'INSURANCE')">
			<option value="">请选择复用人员信息</option>
			<option value="OPERATOR">经办人信息</option>
		</select>

	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>社保经办人：</th>
				<td><input type="text" class="syj-input1 validate[required]" name="INSURANCE_OPERRATOR"  
					maxlength="16"  placeholder="请输入社保经办人" value="${busRecord.INSURANCE_OPERRATOR}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1 validate[required]" maxlength="32"
					name="INSURANCE_PHONE"  placeholder="请输入移动电话" value="${busRecord.INSURANCE_PHONE}"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>身份证号码：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[vidcard]"  
					name="INSURANCE_IDNO" placeholder="请输入身份证号码" maxlength="18" value="${busRecord.INSURANCE_IDNO}"/></td>				
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>参加险种：</th>
				<td colspan="3">
					<eve:excheckbox
						dataInterface="dictionaryService.findDatasForSelect"
						name="INSURANCE_TYPE" width="749px;" clazz="checkbox validate[required]"
						dataParams="insuranceType" value="${busRecord.INSURANCE_TYPE}">
					</eve:excheckbox>
				</td>
			</tr>
			
			<tr>
				<th>是否同时进行员工参保：</th>
				<td colspan="3">
					<input type="radio"  name="IS_EMPLOY_INSU" value="1" onclick="initSfCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INSU==1}"> checked="checked"</c:if> />是
					<input type="radio"  name="IS_EMPLOY_INSU" value="0" onclick="initSfCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INSU!=1}"> checked="checked"</c:if>/>否
				</td>
			</tr>
			<tr class='ygupload' style="display:none">
				<input  name="FILE_ID3"  type="hidden"
				value="${busRecord.FILE_ID3 }">
				<th>附件：</th>
				<td colspan="3">
					<div style="width:100%;display: none;" id="UPDATE_FILE_DIV3"></div>
						<a href="javascript:void(0);" onclick="openResultFileUploadDialog3()">
							<img id="UPDATE_FILE_BTN3" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
						</a>
					<div style="width:100%;" id="fileListDiv3"></div>
				</td>
			</tr>
			<tr class='ygupload'  style="display:none">
				<th>样表下载：</th>
				<td colspan="3">
					<a href='webpage/commercial/mater/qyzg.xlsx' style="color: blue;">福建省企业职工基本养老保险登记表</a>&nbsp;&nbsp;
					<a href='webpage/commercial/mater/shry.xlsx' style="color: blue;">参加社会保险人员增员申报表</a>
				</td>
			</tr>
			<tr class='ygupload'  style="display:none">
				<th>办事指南：</th>
				<td colspan="3">
					<a href="https://zwfw.fujian.gov.cn/person-todo/todo-fingerpost?infoType=1&unid=AF770E43BA61E0F67D5C88FFDB580B07&type=1&siteUnid=EC4BF9378FEEB8761D62BEDFF8EDE105&noNotice=true" target='_blank' style="color: blue;">工伤保险及企业职工基本养老保险职工参保登记</a>
					&nbsp;&nbsp;<a href="https://zwfw.fujian.gov.cn/person-todo/todo-fingerpost?infoType=1&unid=AB84E16FB662A639391555739F4A32B7&type=1&siteUnid=EC4BF9378FEEB8761D62BEDFF8EDE105&noNotice=true" target='_blank' style="color: blue;">工伤保险及企业职工基本养老保险人员增减</a>
				</td>
			</tr>
			
		</table>
		<script>
            $(function(){
                    var runStatus="${busRecord.RUN_STATUS}";
                    if(runStatus=="0"||runStatus=="1"||runStatus==""){
                    var insrranceTypes= document.getElementsByName("INSURANCE_TYPE");
                    for(i=0;i<insrranceTypes.length;i++){
                        if(insrranceTypes[i].value=='1'||insrranceTypes[i].value=='5'){
                            insrranceTypes[i].checked=true;
                            insrranceTypes[i].disabled="disabled";
                        }
                    }
                    }					

            });
		</script>
        <span style="margin-top:5px;">
        <font style="color: red;font-family: serif;font-size: 16px;line-height: 40px;">
根据《中华人民共和国社会保险法》用人单位应当自成立之日起三十日内持单位印章，向当地社会保险经办机构申请办理养老、工伤保险登记。</font>
        </span>
	</div>
	</c:if>
	<c:if test="${requestParams.ISMEDICALREGISTER == '1' || busRecord.ISMEDICALREGISTER == '1'}">
		<div class="syj-title1 ">
			<span>医疗保险登记信息</span>
			<select   style="margin-left: 18px;" class="input-dropdown " onchange="setInfoMsg(this.value,'MEDICAL')">
				<option value="">请选择复用人员信息</option>
				<option value="OPERATOR">经办人信息</option>
			</select>
		</div>
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>医保经办人：</th>
					<td><input type="text" class="syj-input1 validate[required]" name="MEDICAL_OPERRATOR"
							   maxlength="16"  placeholder="请输入医保经办人" value="${busRecord.MEDICAL_OPERRATOR}"/></td>
					<th><font class="syj-color2">*</font>移动电话：</th>
					<td><input type="text" class="syj-input1 validate[required]" maxlength="32"
							   name="MEDICAL_PHONE"  placeholder="请输入移动电话" value="${busRecord.MEDICAL_PHONE}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>是否自动生成医疗保障企业开户信息：</th>
					<td colspan="3">
						<input type="radio" class="validate[required]" name="IS_OPEN_YBACCOUNT" value="1" <c:if test="${busRecord.IS_OPEN_YBACCOUNT==1}"> checked="checked"</c:if> />是
						<input type="radio" class="validate[required]" name="IS_OPEN_YBACCOUNT" value="0" <c:if test="${busRecord.IS_OPEN_YBACCOUNT==0}"> checked="checked"</c:if>/>否
					</td>
				</tr>
				<tr>
					<th>是否同时进行员工参保：</th>
					<td colspan="3">
						<input type="radio" name="IS_EMPLOY_INMEDICAL" value="1" onclick="initYbCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INMEDICAL==1}"> checked="checked"</c:if> />是
						<input type="radio" name="IS_EMPLOY_INMEDICAL" value="0" onclick="initYbCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INMEDICAL!=1}"> checked="checked"</c:if>/>否
					</td>
				</tr>
				<tbody id="ybcb" style="display: none;">
					<tr>
						<input  name="FILE_ID4"  type="hidden" value="${busRecord.FILE_ID4 }">
						<th>附件：</th>
						<td colspan="3">
							<div style="width:100%;display: none;" id="UPDATE_FILE_DIV4"></div>
								<a href="javascript:void(0);" onclick="openResultFileUploadDialog4()">
									<img id="UPDATE_FILE_BTN4" src="webpage/bsdt/applyform/images/tab_btn_sc.png" />
								</a>
							<div style="width:100%;" id="fileListDiv4"></div>
						</td>
					</tr>
					<tr>
						<th>样表下载：</th>
						<td colspan="3">
							<a href='webpage/commercial/mater/职工基本医疗保险参保登记表（空表）.xlsx' style="color: blue;">职工基本医疗保险参保登记表（空表）</a>&nbsp;&nbsp;
							<a href='webpage/commercial/mater/职工基本医疗保险参保登记表（样表）.xlsx' style="color: blue;">职工基本医疗保险参保登记表（样表）</a>
						</td>
					</tr>
					<tr>
						<th>办事指南：</th>
						<td colspan="3">
							<a href="https://zwfw.fujian.gov.cn/person-todo/todo-fingerpost?infoType=1&unid=77B49B51A8875C539AF4E25D8515848A&type=1&siteUnid=EC4BF9378FEEB8761D62BEDFF8EDE105" target='_blank' style="color: blue;">职工基本医疗保险参保登记</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${requestParams.ISFUNDSREGISTER == '1' || busRecord.ISFUNDSREGISTER == '1'}">
		<div class="syj-title1 ">
			<span>公积金登记信息</span>
		</div>
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>类型：</th>
					<td>
						<eve:radiogroup typecode="GJJLX" width="130px" fieldname="FUNDS_GJJLX" value="${busRecord.FUNDS_GJJLX}" defaultvalue="ZFGJJ" onclick="initGjjdjxxForm(this.value)"></eve:radiogroup>
					</td>
					<th><font class="syj-color2">*</font>单位发薪日：</th>
					<td>
						<input type="text" class="syj-input1  validate[required],custom[numberplus]"
						name="FUNDS_GJJDWFXR"  placeholder="请输入单位发薪日" value="${busRecord.FUNDS_GJJDWFXR}"  maxlength="2"/>&nbsp;日
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>启缴年月：</th>
					<td>
						<input type="text" class="Wdate validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})"
							   name="FUNDS_GJJQJNY"  placeholder="请选择启缴年月" value="${busRecord.FUNDS_GJJQJNY}"/>
					</td>
					<th><font class="syj-color2">*</font>单位电子邮箱：</th>
					<td>
						<input type="text" class="syj-input1 validate[required],custom[email]" maxlength="64"
							   name="FUNDS_GJJDWDZYX"  placeholder="请输入单位电子邮箱" value="${busRecord.FUNDS_GJJDWDZYX}"/>
					</td>
				</tr>
				<tr>
					<th colspan="1"><font class="syj-color2">*</font>受托银行：</th>
					<td colspan="3">
						<eve:radiogroup typecode="GJJSTYH" width="130px" value="${busRecord.FUNDS_GJJSTYH}" fieldname="FUNDS_GJJSTYH"></eve:radiogroup>
					</td>
				</tr>
				<tr>
					<th colspan="1"><font class="syj-color2">*</font>隶属关系：</th>
					<td colspan="3">
						<eve:radiogroup typecode="GJJLSGX" width="130px" value="${busRecord.FUNDS_GJJLSGX}" fieldname="FUNDS_GJJLSGX"></eve:radiogroup>
					</td>
				</tr>
				<tr>
					<th colspan="1"><font class="syj-color2">*</font>单位性质：</th>
					<td colspan="3">
						<eve:radiogroup typecode="GJJDWXZ" width="130px" value="${busRecord.FUNDS_GJJDWXZ}" fieldname="FUNDS_GJJDWXZ"></eve:radiogroup>
					</td>
				</tr>
				<tr>
					<th colspan="1"><font class="syj-color2">*</font>单位类型：</th>
					<td colspan="3">
						<eve:radiogroup typecode="GJJDWLX" width="130px" value="${busRecord.FUNDS_GJJDWLX}" fieldname="FUNDS_GJJDWLX"></eve:radiogroup>
					</td>
				</tr>
				<tr>
					<th colspan="1"><font class="syj-color2">*</font>经济类型：</th>
					<td colspan="3">
						<eve:radiogroup typecode="GJJJJLX" width="130px" value="${busRecord.FUNDS_GJJJJLX}" fieldname="FUNDS_GJJJJLX"></eve:radiogroup>
					</td>
				</tr>
				<tr>
					<th colspan="1"><font class="syj-color2">*</font>行业分类：</th>
					<td colspan="3">
						<eve:radiogroup typecode="GJJHYFL" width="130px" value="${busRecord.FUNDS_GJJHYFL}" fieldname="FUNDS_GJJHYFL"></eve:radiogroup>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>单位所属区划：</th>
					<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly"
						name="FUNDS_DWGSQH" placeholder="请输入单位所属区划" value="平潭综合实验区行政服务中心"  maxlength="32"/></td>
				</tr>
				<tr>
					<th>经办人姓名：</th>
					<td><input type="text" class="syj-input1 validate[]" 
						name="FUNDS_NAME"  placeholder="未填写的默认同经办人"  maxlength="8"  value="${busRecord.FUNDS_NAME}"/></td>
					<th>经办人手机号码：</th>
					<td><input type="text" class="syj-input1  validate[],custom[mobilePhone]"
						name="FUNDS_MOBILE"  placeholder="请输入手机号码" value="${busRecord.FUNDS_MOBILE}"  maxlength="16"/></td>
				</tr>
				<tr>
					<th>经办人固定电话：</th>
					<td colspan="3"><input type="text" class="syj-input1 validate[]"
						name="FUNDS_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.FUNDS_FIXEDLINE}"  maxlength="16"/></td>
				</tr>
				<tr>
					<th>经办人证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[]" dataParams="GJJZJLX"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'FUNDS_IDNO');"
						defaultEmptyText="请选择证件类型" name="FUNDS_IDTYPE" value="${busRecord.FUNDS_IDTYPE}">
						</eve:eveselect>
					</td>
					<th>经办人证件号码：</th>
					<td><input type="text" class="syj-input1 validate[]"
						name="FUNDS_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.FUNDS_IDNO}" /></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>在职员工人数：</th>
					<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[numberplusNoZero]" onblur="setFundsZzygs();"
						name="FUNDS_ZZYGRS"  placeholder="请输入在职员工人数"  maxlength="8"  value="${busRecord.FUNDS_ZZYGRS}"/></td>
				</tr>
				<tr class="zfgjj">
					<th><font class="syj-color2">*</font>单位缴存比例（5%-12%）：</th>
					<td>
						<input type="text" class="syj-input1 validate[required],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJDWJCBL"  placeholder="请输入单位缴存比例" onchange="changeGrjcbl(this.value)" value="${busRecord.FUNDS_GJJDWJCBL}"/>
					</td>
					<th><font class="syj-color2">*</font>个人缴存比例：</th>
					<td>
						<input type="text" class="syj-input1 validate[required],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJGRJCBL"  placeholder="请输入个人缴存比例" readonly="readonly" value="${busRecord.FUNDS_GJJGRJCBL}"/>
					</td>
				</tr>
				<tr class="zfgjj">
					<th><font class="syj-color2">*</font>汇缴人数：</th>
					<td>
						<input type="text" class="syj-input1 inputBackgroundCcc validate[required],custom[JustNumber]"  readonly="readonly" maxlength="8"
							   name="FUNDS_GJJJCRS"  placeholder="根据在职员工人数回填" value="${busRecord.FUNDS_GJJJCRS}"/>
					</td>
					<th>工资总额：</th>
					<td>
						<input type="text" class="syj-input1 validate[],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJGJZE"  placeholder="请输入工资总额" value="${busRecord.FUNDS_GJJGJZE}"/>
					</td>
				</tr>
				<tr class="zfgjj">
					<th>月汇缴总额：</th>
					<td>
						<input type="text" class="syj-input1 svalidate[],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJYJCZE" value="${busRecord.FUNDS_GJJYJCZE}" placeholder="根据各员工缴存金额相加" onclick="calYjcze()"/>
					</td>
				</tr>
				<tr class="zfbt" style="display: none;">
					<th><font class="syj-color2">*</font>汇缴人数：</th>
					<td>
						<input type="text" class="syj-input1 inputBackgroundCcc validate[required],custom[JustNumber]"  readonly="readonly" maxlength="8"
							   name="FUNDS_GJJBTJCRS" disabled="disabled"  placeholder="根据在职员工人数回填" value="${busRecord.FUNDS_GJJBTJCRS}"/>
					</td>
					<th>月汇缴总额：</th>
					<td>
						<input type="text" class="syj-input1 validate[],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJBTYJCZE" disabled="disabled"  placeholder="根据各员工缴存金额相加" value="${busRecord.FUNDS_GJJBTYJCZE}"/>
					</td>
				</tr>
				<tr class="zfbt" style="display: none;">
					<th><font class="syj-color2">*</font>一次性发放人数：</th>
					<td>
						<input type="text" class="syj-input1 validate[required],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJBTYCXFFRS" disabled="disabled"  placeholder="请输入一次性发放人数" value="${busRecord.FUNDS_GJJBTYCXFFRS}"/>
					</td>
					<th><font class="syj-color2">*</font>一次性发放金额：</th>
					<td>
						<input type="text" class="syj-input1 validate[required],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJBTYCXFFJE" disabled="disabled"  placeholder="请输入一次性发放金额" value="${busRecord.FUNDS_GJJBTYCXFFJE}"/>
					</td>
				</tr>
				<tr>
					<th>月缴存基础总额：</th>
					<td><input type="text" class="syj-input1 validate[],custom[JustNumber],custom[fundsYjcjcze]" 
						name="FUNDS_YJCJCZE"  placeholder="请输入月缴存基础总额"  maxlength="16"  value="${busRecord.FUNDS_YJCJCZE}"/></td>
					<th><font class="syj-color2">*</font>单位首次汇缴时间：</th>
					<td>
						<input type="radio" class="validate[required]" name="FUNDS_DWSCHJSJ" value="1" 
						<c:if test="${busRecord.FUNDS_DWSCHJSJ==1 }"> checked="checked"</c:if>>本月
						<input type="radio" class="validate[required]" name="FUNDS_DWSCHJSJ" value="2" 
						<c:if test="${busRecord.FUNDS_DWSCHJSJ==2}"> checked="checked"</c:if>>次月
					</td>
				</tr>

			</table>
		</div>
	</c:if>
	<c:if test="${requestParams.IS_ACCOUNT_OPEN == '1' || busRecord.IS_ACCOUNT_OPEN == '1'}">
	<div class="syj-title1 ">
		<span>银行开户申请信息<font style="color: red;font-family: serif;">（申请过程中使用的私章需要使用同一枚私章，私章不能使用光敏印章，身份证应使用最新、有效身份证）</font></span>
	</div>
	<div style="position:relative;;display:none;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th> 是否申请开户：</th>
				<td>
					<input type="radio" name="IS_ACCOUNT_OPEN" value="1" <c:if test="${busRecord.IS_ACCOUNT_OPEN==1}"> checked="checked"</c:if>/>是
					<input type="radio" name="IS_ACCOUNT_OPEN" value="0" <c:if test="${busRecord.IS_ACCOUNT_OPEN!=1}"> checked="checked"</c:if>/>否
				</td>
			</tr>
		</table>
	</div>
	<div id="bankInfo" style="display: none;">
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>已具备网上开户银行：</th>
				<td colspan="3">
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="ccb" <c:if test="${busRecord.BANK_TYPE=='ccb'}">checked="checked"</c:if> disabled="disabled"/>建设银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="boc" <c:if test="${busRecord.BANK_TYPE=='boc'}">checked="checked"</c:if> disabled="disabled"/>中国银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="abc" <c:if test="${busRecord.BANK_TYPE=='abc'}">checked="checked"</c:if> disabled="disabled"/>农业银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="rcb" <c:if test="${busRecord.BANK_TYPE=='rcb'}">checked="checked"</c:if> disabled="disabled"/>农商银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="pdb" <c:if test="${busRecord.BANK_TYPE=='pdb'}">checked="checked"</c:if> disabled="disabled"/>浦发银行
					<input type="radio" class="radio validate[required]" name="BANK_TYPE" value="comm" <c:if test="${busRecord.BANK_TYPE=='comm'}">checked="checked"</c:if> disabled="disabled"/>交通银行
					<c:if test="${busRecord.CONTROLLER!=''&&busRecord.CONTROLLER!=null&&busRecord.CONTROLLER!=undefind}">
						<a href="javascript:void(0);" onclick="javascript:downloadApply();" class="syj-addbtn" style="float: right;">下载申请书</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th> <font class="syj-color2">*</font>控股股东或实际控制人名称(法人代表或持股比例最大股东)：</th>
<%--				<td colspan="3"><input type="text" class="syj-input1 validate[required]" maxlength="32"  disabled="disabled"--%>
<%--					name="CONTROLLER" value="${busRecord.CONTROLLER}"/>--%>
				<td colspan="3" id="CONTROLLERRatio">

				</td>
				
			</tr>
			<tr>
				<th> 存款人名称：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="DEPOSITOR" value="${busRecord.DEPOSITOR}" /></td>				
			</tr>
			<tr>
				<th> 地址：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="DEPOSITOR_ADDR" value="${busRecord.DEPOSITOR_ADDR}"/></td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th> 邮编：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" 
					name="DEPOSITOR_POSTCODE" value=""/></td>
				<th> 地区代码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="AREA_CODE" value=""/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>注册资金(万元)：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" 
					id="REG_CAPITAL" readonly="readonly" value="${busRecord.INVESTMENT}"/></td>
				<th> 法定代表人：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					id="legalPersonName" value="${busRecord.LEGAL_NAME}"/></td>
			</tr>
			<tr>
				<th> 证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280" dataParams="DocumentType" 
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name=""  id="legalPersonIDType" value="${busRecord.LEGAL_IDTYPE}">
					</eve:eveselect>
				</td>
				<th> 证件号码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					id="legalPersonIDNo" value="${busRecord.LEGAL_IDNO}"/></td>
			</tr>
			<tr>
				<th> 经营范围：</th>
				<td colspan="3">
					<textarea rows="3" cols="200" id="businessScope" disabled="disabled"
						class="eve-textarea w878" readonly="readonly"
						style="height:100px;" >${busRecord.BUSSINESS_SCOPE}</textarea>
				</td>
			</tr>
			<tr>
				<th>行业分类：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					id="INDUSTRY_CATEGORY_CDOE"  value="${busRecord.INDUSTRY_CATEGORY}"/>
					
				<input type="hidden" name="INDUSTRY_CATEGORY" value="${busRecord.INDUSTRY_CATEGORY}"></td>
			</tr>
			<tr>
				<th> 存款人类型：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="DEPOSITOR_TYPE" value=""/></td>
				<th> 证明文件类型：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="PROOFDOC" value=""/></td>
			</tr>
			<tr>
				<th> 账户性质：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="ACCOUNT_NATURE" value=""/></td>
				<th> 开户银行代码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="BANK_CODE" value=""/></td>
			</tr>
			<tr>
				<th> 开户银行名称：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="BANK_NAME" value=""/></td>
			</tr>
			<tr>
				<th> 账户名称：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
					name="ACCOUNT_NAME" value=""/></td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th colspan="4" class="syj-title1">到银行办理需要带的文件清单</th>
			</tr>
			<tr id="ccblist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、开立单位银行结算账户申请书；<br>
				2、营业执照正副本原件；<br>
				3、法定代表人身份证原件；<br>
				4、如委托代理人办理的，还需提供代理人身份证件以及授权委托书原件；<br>
				5、公司章程及股东身份证明文件；<br>
				6、单位公章、法定代表人个人名章；<br>
				7、预留印章（单位财务专用章或者公章、法定代表人或授权的代理人的个人名章）
				</td>
			</tr>
			<tr id="boclist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、中国银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正副本；<br>
				4、公司章程及股东身份证明文件；<br>
				5、授权开户经办人身份证及委托书；<br>
				6、单位公章、法人私章及开户预留印鉴；<br>
				7、大额有权确认人身份证原件；
				</td>
			</tr>
			<tr id="abclist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、中国农业银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正本；<br>
				4、公司章程；<br>
				5、股东身份证明文件：股东为自然人的，提供身份证复印件；股东为法人的，提供营业执照正本复印件、开户许可证复印件、信用机构代码证复印件、法人身份证复印件，并加盖股东公章；<br>
				6、单位银行结算账户相关业务授权委托书（法人签字）；<br>
				7、授权开户经办人身份证；<br>
				8、单位公章、法人私章及预留印鉴；<br>
				9、机构税收居民身份声明文件。
				</td>
			</tr>
			<tr id="pdblist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、上海浦东发展银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正副本；<br>
				4、公司章程及股东身份证明文件；<br>
				5、授权开户经办人身份证及委托书；<br>
				6、单位公章、法人私章及开户预留印鉴；
				</td>
			</tr>
			<tr id="rcblist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
				1、平潭农村商业银行开立单位银行结算账户申请书；<br>
				2、法人身份证；<br>
				3、营业执照正副本；<br>
				4、公司章程及股东身份证明文件；<br>
				5、授权开户经办人身份证及委托书；<br>
				6、单位公章、法人私章及开户预留印鉴；
				</td>
			</tr>
			<tr id="commlist" style="display: none;">
				<th> 开立基本户材料清单：</th>
				<td colspan="3">
					1、交通银行开立单位银行结算账户申请书；<br>
					2、法人身份证；<br>
					3、营业执照正本；<br>
					4、公司章程；<br>
					5、股东身份证明文件：股东为自然人的，提供身份证复印件；股东为法人的，提供营业执照正本复印件、开户许可证复印件、信用机构代码证复印件、法人身份证复印件，并加盖股东公章；<br>
					6、单位银行结算账户相关业务授权委托书（法人签字）；<br>
					7、授权开户经办人身份证；<br>
					8、单位公章、法人私章及预留印鉴；<br>
					9、机构税收居民身份声明文件。
				</td>
			</tr>
			<tr id="templateLoad" style="display: none;">
				<th> 申请书模板：</th>
				<td colspan="3">
					<a class="easyui-linkbutton"  iconcls="icon-print" plain="true" style="margin-bottom:15px;"
                				href="" >开立单位银行结算账户申请书-模板下载</a>
                </td>
			</tr>
		</table>
	</div>
	</c:if>
	<div class="syj-title1 ">
		<span>其他信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th> 登记机关：</th>
				<td colspan="3"><input type="text" class="syj-input1 w878 inputBackgroundCcc validate[required]"  readonly="readonly" 
					name="REGISTER_AUTHORITY" value="平潭综合实验区行政审批局"/></td>
			</tr>
		</table>
		<table  style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th> 批准机构：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly" 
					name="APPROVAL_AUTHORITY" value="平潭综合实验区行政审批局" /></td>
				<th> 批准机构代码：</th>
				<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly" 
					name="AUTHORITY_CODE" value="003678642"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>是否申请电子营业执照：</th>
				<td>
					<input type="radio" class="radio validate[required]" name="IS_APPLY" value="1" checked="checked" />是
				</td>
				<th><font class="syj-color2">*</font>申请营业执照副本数量（本）：</th>
				<td><input type="text" class="syj-input1 validate[required],custom[integer],min[1]"
					name="LICENSE_NUM"  placeholder="请输入数量" value="1" maxlength="2"/></td>
				<!--<th><font class="syj-color2">*</font>申请组织机构代码证电子副本数量（本）：</th>
				<td><input type="text" class="syj-input1 validate[required],custom[integer],min[1]"
					name="CERTIFICATE_NUM"  placeholder="请输入数量" value="1"  maxlength="2"/></td>-->
			</tr>
			<!--<tr>
				<th><font class="syj-color2">*</font>是否涉密单位：</th>
				<td colspan="3">
					<input type="radio" class="radio validate[required]" name="IS_SECRET" value="1" 
					<c:if test="${busRecord.IS_SECRET==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio validate[required]" name="IS_SECRET" value="0" 
					<c:if test="${busRecord.IS_SECRET==0}"> checked="checked"</c:if>/>否
				</td>
			</tr>-->
			<tr>
				<th>填表日期：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly"
					name="FILL_DATE"  placeholder="请输入填表日期"  value="${time}"/></td>
			</tr>
		</table>
	</div>
</form>
