<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog"></eve:resources>

	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<!-- my97 end -->
	<script type="text/javascript">
	$(function() {
		
		AppUtil.initWindowForm("dwinfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var info = {};
				$("#dwinfoForm").find("*[name]").each(function(){
					  var inputName= $(this).attr("name");
					  var inputValue = $(this).val();
					  //获取元素的类型
					  var fieldType = $(this).attr("type");
					  if(fieldType=="radio"){
						  var isChecked = $(this).is(':checked');
						  if(isChecked){
							  info[inputName] = inputValue;
						  }
					  }else if(fieldType=="checkbox"){
						  var inputValues = FlowUtil.getCheckBoxValues(inputName);
						  info[inputName] = inputValues;
					  }else if(fieldType!="button"){
						  info[inputName] = inputValue;
					  }          
				});
				art.dialog.data("dwInfo", info);
				AppUtil.closeLayer();
				
			}
		}, "T_MSJW_SYSTEM_DICTYPE");
		
		$("[name='IDCARDTYPENUM']").attr("disabled",true);
	});
		
	/**
	* 标题附件上传对话框
	*/
	function openPthotoFileUploadDialog(id,name){
		//定义上传的人员的ID
		var uploadUserId = $("input[name='uploadUserId']").val();
		//定义上传的人员的NAME
		var uploadUserName = $("input[name='uploadUserName']").val();
		//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=image&materType=image&busTableName=T_COMMERCIAL_INDIVIDUAL'
		+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(uploadUserName), {
			title: "上传(附件)",
			width: "620px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#"+id).attr("src",__file_server + uploadedFileInfo.filePath);
						$("input[name='"+name+"']").val(uploadedFileInfo.filePath);
					}else{
						$("#"+id).attr("src",'<%=path%>/webpage/common/images/nopic.jpg');
						$("input[name='"+name+"']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}


	function showSelectEnterprise(id,name,code){	
		$.dialog.open("projectSgxkController.do?enterpriseSelector&allowCount=1", {
			title : "企业查询",
			width:"800px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var enterpriseInfo = art.dialog.data("enterpriseInfo");
				if(enterpriseInfo){
					$("#"+id).find("[name='"+name+"']").val(enterpriseInfo[0].name);
					$("#"+id).find("[name='"+code+"']").val(enterpriseInfo[0].socialcreditcode);
				}
			}
		}, false);
	};
	</script>
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
</style>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="dwinfoForm" method="post">
	    <div  id="ModuleFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">单位名称：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="CORPNAME" maxlength="100" placeholder="请输入单位名称" value="${info.CORPNAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
					<c:if test="${type!=0}">
						<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectEnterprise('dwinfoForm','CORPNAME','CORPCREDITCODE');">查询</a>
					</c:if>
				</td>				
				<td>
					<span style="width: 130px;float:left;text-align:right;">统一社会信用代码：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码" value="${info.CORPCREDITCODE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">监理单位资质等级：</span>
					
						<eve:eveselect clazz="eve-input validate[required]" dataParams="QUALLEVEL"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择监理单位资质等级" name="QUALLEVEL" id="QUALLEVEL"   value="${info.QUALLEVEL}">
						</eve:eveselect>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td>
						<span style="width: 130px;float:left;text-align:right;">监理单位&nbsp;&nbsp;&nbsp;&nbsp;<br/>资质证书号：</span>
						<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
						name="QUALCERTNO"  placeholder="请输入监理单位资质证书号"  maxlength="32"   value="${info.QUALCERTNO}"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">组织机构代码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码" value="${info.ORGCODE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">法定代表人姓名：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${info.LEGAL_NAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:right;">证件类型：</span>
				
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"   value="${info.LEGAL_IDTYPE}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${info.LEGAL_IDNO}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">总监理工程师：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;" 
					name="PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16" value="${info.PERSONNAME}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">电话号码：</span>
					<input type="text" class="eve-input  validate[required]"  style="width:186px;float:left;" 
					name="PERSONPHONE"  placeholder="请输入负责人电话号码"   maxlength="16" value="${info.PERSONPHONE}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:right;">证件类型：</span>
				
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBIDCARDTYPEDIC"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="IDCARDTYPENUM" id="IDCARDTYPENUM"   value="${info.IDCARDTYPENUM}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input  validate[required]" style="width:186px;float:left;"
					name="PERSONIDCARD"  placeholder="请输入证件号码"  maxlength="32"   value="${info.PERSONIDCARD}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			
			<tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">执业资格等级：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="JLZYZGDJ"
					dataInterface="dictionaryService.findDatasForSelect" 
					defaultEmptyText="请选择执业资格等级" name="${s.index}PERSONQUALLEVEL"   value="${info.PERSONQUALLEVEL}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">执业资格证书号：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;"
					name="${s.index}PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"   value="${info.PERSONCERT}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">安全生产考核&nbsp;&nbsp;&nbsp;&nbsp;<br/>合格证书编号：</span>
					<input type="text" class="eve-input" style="width:186px;float:left;"
					name="${s.index}CERTNUM"  placeholder="请输入安全生产考核合格证书编号"  maxlength="32"   value="${info.CERTNUM}"/>
				</td>	
				<td rowspan="3">
					<span style="width: 130px;float:left;text-align:right;">电子照片：</span>
					<c:choose>
						<c:when test="${info.PERSONPHOTO!=null&&info.PERSONPHOTO!=''}">
							<img id="IMAGE_PATH_IMG" src="${_file_Server}${info.PERSONPHOTO}"
								height="140px" width="125px">
							<font class="dddl_platform_html_requiredFlag">*</font>
							<c:if test="${type!=0}">
							<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('IMAGE_PATH_IMG','PERSONPHOTO')">
								<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
							</a>
							</c:if>
						</c:when>
						<c:otherwise>
							<img id="IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
								height="140px" width="125px">
							<font class="dddl_platform_html_requiredFlag">*</font>
							<c:if test="${type!=0}">
							<a href="javascript:void(0);" onclick="openPthotoFileUploadDialog('IMAGE_PATH_IMG','PERSONPHOTO')">
								<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
							</a>
							</c:if>
						</c:otherwise>
					</c:choose>				
					<input type="hidden" class="validate[required]" name="PERSONPHOTO" value="${info.PERSONPHOTO}">
					<div style="color:red;width:105%;">注意：此处照片应为总监理工程师个人电子证件照。</div>
				</td>	
			</tr>
			<tr><td></td><td></td></tr>
			<tr><td></td><td></td></tr>
			<tr>	
				<td>
					<span style="width: 130px;float:left;text-align:right;">标段名称：</span>
					<input type="text" class="eve-input validate[]" name="SECTION" style="width:186px;float:left;" 
					maxlength="16" placeholder="请输入标段名称" value="${info.SECTION}"/>
				</td>
				<td>
					<span style="width: 130px;float:left;text-align:right;">合同编号：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;" 
					name="CONTRACTNUMBER" maxlength="32" placeholder="请输入合同编号" value="${info.CONTRACTNUMBER}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>		
			</tr>	
			<tr>
				<td>
					<span style="width: 130px;float:left;text-align:right;">监理合同价：</span>
					<input type="text" class="eve-input validate[required],custom[JustNumber]"  style="width:186px;float:left;"   value="${info.CONTRACTCOST}"
					name="CONTRACTCOST" maxlength="16" placeholder="请输入监理合同价" /></td>				
				<td>
					<span style="width: 130px;float:left;text-align:right;">中标通知书时间：</span>
					<input type="text" class="eve-input Wdate validate[required]"  readonly="readonly" style="width:186px;float:left;"  
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${info.BIDDINGDATE}"
					name="BIDDINGDATE"  placeholder="请选择中标通知书时间"  maxlength="16"/>
				</td>
			</tr>
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<c:if test="${type!=0}">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				</c:if>
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
