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
		setTenderClassNum('${info.TENDERCLASSNUM}','');
		setTenderTypeNum('${info.TENDERTYPENUM}','');
	});
	function setTenderClassNum(val,index){
		if(val=='001'||val=='002'||val=='003'||val=='004'){
			$("."+index+"_zbdwxx").show();
			$("."+index+"_zbdwxx").find("input").addClass(" validate[required]");
		} else{		
			$("."+index+"_zbdwxx").hide();
			$("."+index+"_zbdwxx").find("input").removeClass(" validate[required]");
			$("."+index+"_zbdwxx").find("input").val('');
		}
	}
	function setTenderTypeNum(val,index){	
		if(val=='003'||val=='099'){
			$("."+index+"_zbdldwxx").hide();
			$("."+index+"_zbdldwxx").find("input,select").attr("disabled",true);
			$("."+index+"_zbdldwxx").find("input,select").val('');
		} else{		
			$("."+index+"_zbdldwxx").show();
			$("."+index+"_zbdldwxx").find("input,select").attr("disabled",false);
			$("#zbdwDiv [name$='IDCARDTYPENUM']").val(1);
			$("#zbdwDiv [name$='IDCARDTYPENUM']").attr("disabled",true);
		}
	}

	</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="dwinfoForm" method="post">
	    <div  id="ModuleFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="index" value="${index}">
	    <input type="hidden" name="currentTime" value="${currentTime}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 140px;float:left;text-align:right;">招标类型：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TENDERCLASSNUM" value="${info.TENDERCLASSNUM}"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setTenderClassNum(this.value,'');"
					defaultEmptyText="请选择招标类型" name="TENDERCLASSNUM">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td><span style="width: 140px;float:left;text-align:right;">招标方式：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TENDERTYPENUM" value="${info.TENDERTYPENUM}"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setTenderTypeNum(this.value,'');"
					defaultEmptyText="请选择招标方式" name="TENDERTYPENUM">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr class="_zbdwxx" style="display:none;">
				<td>
					<span style="width: 140px;float:left;text-align:right;">中标单位：</span>
					<input type="text" class="eve-input"  style="width:186px;float:left;" value="${info.BIDDINGUNITNAME}"
					name="BIDDINGUNITNAME" maxlength="100" placeholder="请输入中标单位"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>				
				<td>
					<span style="width: 140px;float:left;text-align:right;">监理中标通知单编号：</span>
					<input type="text" class="eve-input"   style="width:186px;float:left;" value="${info.BIDDINGNOTICENUMPROVINCE}"
					name="BIDDINGNOTICENUMPROVINCE" maxlength="32" placeholder="请输入监理中标通知单编号"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<span style="width: 140px;float:left;text-align:right;">中标日期：</span>				
					<input type="text" class="eve-input Wdate validate[required]"  readonly="readonly"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:186px;float:left;" value="${info.TENDERRESULTDATE}"
					name="TENDERRESULTDATE"  placeholder="请选择中标日期"  maxlength="16"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>				
				<td>
					<span style="width: 140px;float:left;text-align:right;">中标金额（万元）：</span>
					<input type="text" class="eve-input validate[required],custom[JustNumber]" 
					name="TENDERMONEY" maxlength="16" placeholder="请输中标金额（万元）"  style="width:186px;float:left;" value="${info.TENDERMONEY}"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr class="_zbdldwxx">
				<td>
					<span style="width: 140px;float:left;text-align:right;">招标代理单位：</span>
					<input type="text" class="eve-input validate[required]"  style="width:186px;float:left;" value="${info.CORPNAME}"
					name="CORPNAME" maxlength="100" placeholder="请输入单位名称"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>				
				<td>
					<span style="width: 140px;float:left;text-align:right;">统一社会信用代码：</span>
					<input type="text" class="eve-input validate[required]"   style="width:186px;float:left;" value="${info.CORPCREDITCODE}"
					name="CORPCREDITCODE" maxlength="32" placeholder="请输入统一社会信用代码"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr class="_zbdldwxx">
				<td>
					<span style="width: 140px;float:left;text-align:right;">招标单位资质等级：</span>
					<input type="text" class="eve-input " style="width:186px;float:left;" value="${info.QUALLEVEL}"
					name="QUALLEVEL"  placeholder="请输入招标单位资质等级"  maxlength="32" />
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">招标单位资质证书号：</span>
					<input type="text" class="eve-input" style="width:186px;float:left;" value="${info.QUALCERTNO}"
					name="QUALCERTNO"  placeholder="请输入招标单位资质证书号"  maxlength="32" />
				</td>
			</tr>
			<tr class="_zbdldwxx">	
				<td>
					<span style="width: 140px;float:left;text-align:right;">组织机构代码：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;" value="${info.ORGCODE}"
					name="ORGCODE" maxlength="32" placeholder="请输入组织机构代码"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">法定代表人姓名：</span>
					<input type="text" class="eve-input validate[required]"  style="width:186px;float:left;" value="${info.LEGAL_NAME}"
					name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr class="_zbdldwxx">
				<td>
					<span style="width: 140px;float:left;text-align:right;">证件类型：</span>				
					<eve:eveselect clazz="eve-input validate[required]" dataParams="TBIDCARDTYPEDIC" value="${info.LEGAL_IDTYPE}"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'LEGAL_IDNO');"
					defaultEmptyText="请选择法定代表人证件类型" name="LEGAL_IDTYPE">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;" value="${info.LEGAL_IDNO}"
					name="LEGAL_IDNO"  placeholder="请输入法定代表人证件号码"  maxlength="32" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr class="_zbdldwxx">
				<td>
					<span style="width: 140px;float:left;text-align:right;">项目负责人：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;" value="${info.PERSONNAME}"
					name="PERSONNAME"  placeholder="请输入项目负责人"   maxlength="16"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">电话号码：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;" value="${info.PERSONPHONE}"
					name="PERSONPHONE"  placeholder="请输入项目负责人电话号码"   maxlength="16"/>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			
			</tr>
			<tr class="_zbdldwxx">
				<td>
					<span style="width: 140px;float:left;text-align:right;">证件类型：</span>				
					<eve:eveselect clazz="eve-input inputBackgroundCcc validate[required]" dataParams="TBIDCARDTYPEDIC" 
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'PERSONIDCARD');"
					defaultEmptyText="请选择项目负责人证件类型" name="IDCARDTYPENUM"  value="${info.IDCARDTYPENUM}">
					</eve:eveselect>
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">证件号码：</span>
					<input type="text" class="eve-input validate[required]" style="width:186px;float:left;" value="${info.PERSONIDCARD}"
					name="PERSONIDCARD"  placeholder="请输入项目负责人证件号码"  maxlength="32" />
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr class="_zbdldwxx">
				<td>
					<span style="width: 140px;float:left;text-align:right;">项目负责人资格等级：</span>
					<input type="text" class="eve-input validate[]" style="width:186px;float:left;" value="${info.PERSONQUALLEVEL}"
					name="PERSONQUALLEVEL"  placeholder="请输入项目负责人资格等级"  maxlength="32"  />
				</td>
				<td>
					<span style="width: 140px;float:left;text-align:right;">执业资格证书号：</span>
					<input type="text" class="eve-input validate[]" style="width:186px;float:left;" value="${info.PERSONCERT}"
					name="PERSONCERT"  placeholder="请输入执业资格证书号"  maxlength="32"  />
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
