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
         art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_NZ_JOINTVENTURE&uploadUserId='
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
         art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType=image&busTableName=T_COMMERCIAL_NZ_JOINTVENTURE&uploadUserId='
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
	<div class="syj-title1 ">
		<span>社会保险登记信息</span>
		<select style="margin-left: 18px;" class="input-dropdown " onchange="setInfoMsg(this.value,'INSURANCE')">
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
				<td><input type="text" class="syj-input1 validate[required,custom[mobilePhone]]" maxlength="32"
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
						name="INSURANCE_TYPE" clazz="checkbox validate[required]" width="749px"
						dataParams="insuranceType" value="${busRecord.INSURANCE_TYPE}">
					</eve:excheckbox>
				</td>
			</tr>
			<tr>
				<th>是否同时进行员工参保：</th>
				<td colspan="3">
					<input type="radio" name="IS_EMPLOY_INSU" value="1" onclick="initSfCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INSU==1}"> checked="checked"</c:if> />是
					<input type="radio" name="IS_EMPLOY_INSU" value="0" onclick="initSfCb(this.value)" <c:if test="${busRecord.IS_EMPLOY_INSU!=1}"> checked="checked"</c:if>/>否
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
					<td><input type="text" class="syj-input1  validate[required,custom[mobilePhone]]" maxlength="32"
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
						<input type="text" class="syj-input1 validate[required],custom[numberplus]"
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
						<eve:eveselect clazz="input-dropdown w280 validate[]" dataParams="DocumentType" 
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
						<input type="text" class="syj-input1 inputBackgroundCcc validate[required],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJJCRS"  placeholder="请输入缴存人数" value="${busRecord.FUNDS_GJJJCRS}"/>
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
						<input type="text" class="syj-input1 validate[],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJYJCZE" value="${busRecord.FUNDS_GJJYJCZE}"  placeholder="根据各员工缴存金额相加" onclick="calYjcze()"/>
					</td>
				</tr>
				<tr class="zfbt" style="display: none;">
					<th><font class="syj-color2">*</font>汇缴人数：</th>
					<td>
						<input type="text" class="syj-input1 inputBackgroundCcc validate[required],custom[JustNumber]" maxlength="8"
							   name="FUNDS_GJJBTJCRS" disabled="disabled"  placeholder="请输入缴存人数" value="${busRecord.FUNDS_GJJBTJCRS}"/>
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







	
	<div style="position:relative;display: none;" >
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
	
	<div class="syj-title1 ">
		<span>其他信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th> 登记机关：</th>
				<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc validate[required]"  readonly="readonly" 
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
