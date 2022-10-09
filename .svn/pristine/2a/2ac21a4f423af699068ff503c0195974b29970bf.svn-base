<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<script type="text/javascript">
	var ALLXKZBH_OLD = "";
	$(function(){
		ALLXKZBH_OLD = $("input[name='XKZBH_OLD']").val();
	});
	//新开办和换证根据流水号读取数据
	function busExeIdToFillFun(){
		var exeId = $("input[name='busExeIdToFill']").val();
		exeId = FdaAppUtil.trim(exeId);
		if(null!=exeId&&''!=exeId){
			FdaAppUtil.ajaxProgress({
				url : "foodManagementController/busExeIdToFill.do",
				params : {exeId:exeId},
				callback : function(resultJson) {
					if(resultJson.success){
						successDataFill(resultJson,true);
					}else{
						art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}else{
			art.dialog({
				content: "办件流水号不能为空！",
				icon:"error",
				ok: true
			});
		}
	}
	
	//验证是否包括新证
	function yzsfbkxz(){
		var XKZBH_OLD = $("input[name='XKZBH_OLD']").val();
		XKZBH_OLD = FdaAppUtil.trim(XKZBH_OLD);
		var sfkytj = false;
		XKZBH_OLD = XKZBH_OLD.replace(/，/g,",");
		var array = XKZBH_OLD.split(",");
		for (var i=0 ; i< array.length ; i++)
		{
			if(array[i].length>2&&array[i].substring(0,2)=="JY"){
				sfkytj = true;
			}
		}
		if(sfkytj){
			art.dialog({
				content: "许可证编号有误，请勿输入JY开头的许可证编号！",
				icon:"error",
				ok: true
			});
		}
	}
	//验证是否为新许可证
	function yzsfwxxkzh(){
		var XKZBH_OLD = $("input[name='XKZBH_OLD']").val();
		XKZBH_OLD = FdaAppUtil.trim(XKZBH_OLD);
		if(null!=XKZBH_OLD&&''!=XKZBH_OLD&&XKZBH_OLD.length>2&&XKZBH_OLD.substring(0,2)=="JY"){
			
		}else if(null!=XKZBH_OLD&&''!=XKZBH_OLD){
			art.dialog({
				content: "许可证编号有误，请输入JY开头的食品经营许可证编号，餐饮服务许可证、食品流通许可证无法办理该业务！",
				icon:"error",
				ok: true
			});
		}
	}
	//验证社会信用代码是否已经办过
	function ajaxShxydmsfzhmExist(itemCode){
		var SHXYDMSFZHM = $("input[name='SHXYDMSFZHM']").val();
		SHXYDMSFZHM = FdaAppUtil.trim(SHXYDMSFZHM);
		$("input[name='SHXYDMSFZHM']").val(SHXYDMSFZHM);
		if(null!=SHXYDMSFZHM&&''!=SHXYDMSFZHM){
			var bool = SHXYDMSFZHM.indexOf("*");
			if(bool>-1){
				parent.art.dialog({
					content : "请输入完整信用代码,信用代码不允许有'*'符号! ",
					lock: true,
					cancel:false,
					icon : "error",
					ok : function(){
						$("input[name='SHXYDMSFZHM']").focus();
					}
				});
			}
			FdaAppUtil.ajaxProgress({
				url : "foodManagementController/ajaxShxydmsfzhmExist.do",
				params : {
						SHXYDMSFZHM:SHXYDMSFZHM,
						itemCode:itemCode
					},
				callback : function(resultJson) {
					if(resultJson.success){
						art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							ok : true
						});
					}else{
						
					}
				}
			});
		}
	}
	//社会信用代码回填
	function backFillCreditCodeData(){
		var SHXYDMSFZHM = $("input[name='SHXYDMSFZHM']").val();
		if(null!=SHXYDMSFZHM&&''!=SHXYDMSFZHM){
			FdaAppUtil.ajaxProgress({
				url : "foodManagementController/getCreditCodeData.do",
				params : {SHXYDMSFZHM:SHXYDMSFZHM},
				callback : function(resultJson) {
					if(resultJson.success){
						successDataFill(resultJson);
						$("input[name='SHXYDMSFZHM']").focus();
					}else{
						art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}else{
			art.dialog({
				content: "统一社会信用代码不能为空！",
				icon:"error",
				ok: true
			});
		}
	}
	
	function backPatchFillData(){
		var XKZBH_OLD = $("input[name='XKZBH_OLD']").val();
		XKZBH_OLD = FdaAppUtil.trim(XKZBH_OLD);
		if(null!=XKZBH_OLD&&''!=XKZBH_OLD){
			FdaAppUtil.ajaxProgress({
				url : "foodManagementController/getPatchSpjyxx.do",
				params : {XKZBH_OLD:XKZBH_OLD},
				callback : function(resultJson) {
					if(resultJson.success){
						art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							ok : true
						});
						//successDataFill(resultJson);
						FlowUtil.initFormFieldValue(resultJson.jbxxMap, "BASEINFO_FORM");
						FlowUtil.initFormFieldValue(resultJson.jbxxMap, "PERSONNEL_FORM");
						$("input[name='XKZBH_OLD']").val(XKZBH_OLD);
					}else{
						//获取流程变量对象
						var EFLOW_VARS = FlowUtil.getFlowObj();
						var sqfs = EFLOW_VARS.SQFS;
						if(sqfs&&sqfs=="2"){
							art.dialog({
								content: '1.验证失败（或数据读取失败），请检查原许可证编号录入是否有误，如是否使用中文括号或英文括号，SP是否使用大写，证号中是否带有空格；<br/>2.可以在【打印归档】->【历史发证查询】中输入许可证编号关键字或名称关键字进行查询系统中是否存在该许可证信息；<br/>3.系统中无该证照信息，请先手动导入该旧证信息（餐饮服务许可证或食品流通许可证），导入成功后再执行注销（或换证）操作。<br/>4.该证照已过期 ',
								icon : "error",
								ok : true
							});
						}else{
							art.dialog({
								content: '1.验证失败（或数据读取失败），请检查原许可证编号录入是否有误，如是否使用中文括号或英文括号，SP是否使用大写，证号中是否带有空格；<br/>2.若始终无法验证成功请联系当地区县局食药监部门或行政服务中心窗口。 ',
								icon : "error",
								ok : true
							});
						}
						
					}
				}
			});
		}else{
			art.dialog({
				content: "原许可证编号不能为空！",
				icon:"error",
				ok: true
			});
		}
	}
	function backFillData(){
		var XKZBH_OLD = $("input[name='XKZBH_OLD']").val();
		XKZBH_OLD = FdaAppUtil.trim(XKZBH_OLD);
		if(null!=XKZBH_OLD&&''!=XKZBH_OLD){
			FdaAppUtil.ajaxProgress({
				url : "foodManagementController/getSpjyxx.do",
				params : {XKZBH_OLD:XKZBH_OLD},
				callback : function(resultJson) {
					if(resultJson.success){
						successDataFill(resultJson);
						$("input[name='XKZBH_OLD']").val(XKZBH_OLD);
						ALLXKZBH_OLD = XKZBH_OLD;
					}else{
						art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}else{
			art.dialog({
				content: "原许可证编号不能为空！",
				icon:"error",
				ok: true
			});
		}
	}
	function successDataFill(resultJson,statue){
		if(resultJson.jbxxMap){
			FlowUtil.initFormFieldValue(resultJson.jbxxMap, "BASEINFO_FORM");
			if(resultJson.jbxxMap.JBXX_ID){
				if(!statue){
					$("select[name='JYCSQS']").attr("disabled","disabled");
				     $("select[name='JYCSXS']").attr("disabled","disabled");
				     $("input[name='ZTYTDM']").attr("disabled","disabled");
				     $("#JYCSTSAN").attr("style","display:none");
				}
				 
			}
		}
		if(resultJson.legalRepresentative){
			//初始化人员信息表单字段值
			FlowUtil.initFormFieldValue(resultJson.legalRepresentative, "PERSONNEL_FORM");
			if($("select[name='FRZJLX']").val()=="1"){
				  $("input[name='FRZJH']").attr("class","syj-input1 validate[required],custom[vidcard]");
			  }else{
				  $("input[name='FRZJH']").attr("class","syj-input1 validate[required]");
			  }
		} 
		if(resultJson.clinetMap){
			//初始化委托人信息表单字段值
			FlowUtil.initFormFieldValue(resultJson.clinetMap, "CLIENT_FORM");
			if($("select[name='WTRZJLX']").val()=="1"){
				  $("input[name='WTRZJH']").attr("class","syj-input1 validate[custom[vidcard]]");
			  }else{
				  $("input[name='WTRZJH']").attr("class","syj-input1");
			  }
		} 
		//设备信息列表
		if(resultJson.facilityEquipmentList){
			fillSetFacilityEquipmentList(resultJson.facilityEquipmentList);
		}
		//技术人员列表
		if(resultJson.technicalPersonnelList){
			var technicalPersonnelListJson = JSON.stringify(resultJson.technicalPersonnelList);
			fillSetTechnicalPersonnelList(technicalPersonnelListJson);
		}
		if(resultJson.practitionersList){
			var practitionersListJson = JSON.stringify(resultJson.practitionersList);
			fillSetPractitionersList(practitionersListJson);
		}
		fillDataReloadJs(resultJson);
	}
	//回填设备信息
	function fillSetFacilityEquipmentList(facilityEquipmentList){
		$("#FoodSafetyDiv").html("");
		for (var i=0; i<facilityEquipmentList.length; i++) {
			var wz = "";
			var  bz= "";
			if(facilityEquipmentList[i].WZ){
				wz = facilityEquipmentList[i].WZ;
			}
			if(facilityEquipmentList[i].BZ){
				bz = facilityEquipmentList[i].BZ;
			}
			var newhtml = "<div style=\"position:relative;\">";
			newhtml += "<table cellpadding=\"0\" cellspacing=\"0\" class=\"syj-table2 tmargin20\">";
			newhtml += "<tr><th><font color=\"#ff0101\">*</font>设备名称：</th><td><input type=\"text\" value=\""+facilityEquipmentList[i].SBMC+"\" name=\"SBMC\" placeholder=\"请输入设备名称\" class=\"syj-input1 validate[required]\" /></td>";
			newhtml += "<th><font color=\"#ff0101\">*</font>数量：</th><td><input type=\"text\" value=\""+facilityEquipmentList[i].SL+"\" name=\"SL\" placeholder=\"请输入数量\" class=\"syj-input1 validate[required,custom[integer],min[0]] \" /></td></tr>";
		    newhtml += "<tr><th>位置：</th><td><input type=\"text\" value=\""+wz+"\" name=\"WZ\" placeholder=\"请输入位置\" class=\"syj-input1\" /></td>";
		    newhtml += "<th>备注：</th><td><input type=\"text\" value=\""+bz+"\" name=\"BZ\" placeholder=\"请输入备注\" class=\"syj-input1\" /></td></tr>";
		    newhtml += "</table><a href=\"javascript:void(0);\" onclick=\"delFoodSafetyDiv(this);\" class=\"syj-closebtn\"></a></div>";
			$("#FoodSafetyDiv").append(newhtml);
		}
	}
	//回填技术人员
	function fillSetTechnicalPersonnelList(technicalPersonnelListJson){
			$.post("foodManagementController/refreshTechnicalPersonnelDivByList.do",{
				technicalPersonnelListJson:technicalPersonnelListJson
			}, function(responseText, status, xhr) {
				$("#TechnicalPersonnelDiv").html("");
				$("#TechnicalPersonnelDiv").append(responseText);
			});
	}
	//回填从业人员信息
	function fillSetPractitionersList(practitionersListListJson){
		$.post("foodManagementController/refreshPractitionersDivByList.do",{
			practitionersListListJson:practitionersListListJson
		}, function(responseText, status, xhr) {
			$("#PractitionersDiv").html("");
			$("#PractitionersDiv").append(responseText);
		});
	}
	function fillDataReloadJs(result){
		//基本信息界面
		//是否申请延续
		var SFSQYXVALUE = $("input[name='SFSQYX']:checked").val();
		if (SFSQYXVALUE == "0") {
			$("#XKYXQXTABLE").attr("style", "display:none;");
			$("#XKYXQXDIV").attr("style", "display:none;");
			$("input[name='XKYXQX']").removeClass(" validate[required]");
		} else if (SFSQYXVALUE == "1") {
			$("#XKYXQXDIV").attr("style", "");
			$("#XKYXQXTABLE").attr("style", "");
			$("input[name='XKYXQX']").addClass(" validate[required]");
		}
		//主体业态
		if ($("input[name='ZTYTDM']:checked").val() == "1") {
			$("#YYFSDIV").attr("style", "");
			$("input[name='YYFS']").addClass(" validate[required]");
			
			
			$("input[name='ZYCF'][value='0']").prop("checked",true); 
			$("input[name='ZYCF']").attr("disabled","disabled");
			$("input[name='JTYCPSDW'][value='0']").prop("checked",true);
			$("input[name='JTYCPSDW']").attr("disabled","disabled");
		} else if ($("input[name='ZTYTDM']:checked").val() == "2") {
			$("#CYLXDIV").attr("style", "");
			$("input[name='CYLX']").addClass(" validate[required]");
			
			
			$("input[name='ZYCF']").removeAttr("disabled","disabled");
			$("input[name='JTYCPSDW']").removeAttr("disabled","disabled");
		} else if ($("input[name='ZTYTDM']:checked").val() == "3") {
			$("#SFWXXSTDIV").attr("style", "");
			$("input[name='SFWXXST']").addClass(" validate[required]");
			
			$("input[name='ZYCF']").removeAttr("disabled","disabled");
			$("input[name='JTYCPSDW']").removeAttr("disabled","disabled");
		}
		//是否网络经营
		if ($("input[name='WLJY']:checked").val() == "0") {
			$("#WLJYDIV").attr("style", "display:none;");
			$("input[name='STMD']").removeClass(" validate[required]");
			//$("input[name='WZDZ']").removeClass(" validate[required]");
		} else if ($("input[name='WLJY']:checked").val() == "1") {
			$("#WLJYDIV").attr("style", "");
			$("input[name='STMD']").addClass(" validate[required]");
			//$("input[name='WZDZ']").addClass(" validate[required]");
			var ztFilepath = $("input[name='WZJTLJ']").val();
			if(ztFilepath&&ztFilepath.length>0){
				var fileName = result.jbxxMap.WZJTLJ_FILE_NAME;
				var fileId = result.jbxxMap.WZJTLJ_FILE_ID;
				var spanHtml = "<a href=\"javascript:void(0);\" title=\""+fileName+"\" ";
                spanHtml+=(" onclick=\"FdaAppUtil.downLoadFile('"+fileId+"');\">");
                spanHtml +="<font color=\"blue\">下载</font></a>&nbsp;"
                spanHtml +="<a href=\"javascript:void(0);\"   onclick=\"delPicFile('"+fileId+"');\" ><font color=\"red\">删除</font></a>";
                $("#WZJTLJ_DIV").html(spanHtml);
                setTimeout(function(){swfUploadPic.setStats({successful_uploads:1});}, 1000);
			}
		}
		//经营项目回填验证
		if($("#ybzspxsId").is(':checked')){
			ybzspxssj($("#ybzspxsId"));
		}
		if($("#szspxsId").is(':checked')){
			szspxssj($("#szspxsId"));
		}
		if($("#tsspxsId").is(':checked')){
			tsspxssj($("#tsspxsId"));
		}
		if($("#znjzsId").is(':checked')){
			zzypzzsj($("#znjzsId"));
		}
		if(result.jbxxMap){
			//地址回填
			FdaAppUtil.reloadSelect($("select[name='ZSXS']"),{
				  dataParams:"{TYPE_CODE:'"+result.jbxxMap.ZSQS+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name='ZSXS']").val(result.jbxxMap.ZSXS);
			  });
			FdaAppUtil.reloadSelect($("select[name='ZSXZ']"),{
				  dataParams:"{TYPE_CODE:'"+result.jbxxMap.ZSXS+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name='ZSXZ']").val(result.jbxxMap.ZSXZ);
			  });
			FdaAppUtil.reloadSelect($("select[name='JYCSXS']"),{
				  dataParams:"{TYPE_CODE:'"+result.jbxxMap.JYCSQS+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name='JYCSXS']").val(result.jbxxMap.JYCSXS);
			  });
			FdaAppUtil.reloadSelect($("select[name='JYCSXZ']"),{
				  dataParams:"{TYPE_CODE:'"+result.jbxxMap.JYCSXS+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name='JYCSXZ']").val(result.jbxxMap.JYCSXZ);
			  });
			FdaAppUtil.reloadSelect($("select[name='CCCSXS']"),{
				  dataParams:"{TYPE_CODE:'"+result.jbxxMap.CCCSQS+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name='CCCSXS']").val(result.jbxxMap.CCCSXS);
			  });
			FdaAppUtil.reloadSelect($("select[name='CCCSXZ']"),{
				  dataParams:"{TYPE_CODE:'"+result.jbxxMap.CCCSXS+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name='CCCSXZ']").val(result.jbxxMap.CCCSXZ);
			  });
		}
		//法人界面重新刷JS
		var FRGDDHVal = $("#PERSONNEL_FORM input[name='FRGDDH']").val();
		var FRYDDHVal = $("#PERSONNEL_FORM input[name='FRYDDH']").val();
		  if(FRGDDHVal!=""){
			  changeFRYDDH($("#FRGDDHID"));
		  }
		  if(FRYDDHVal!=""){
			  changeFRGDDH($("#FRYDDHID"));
		  }
	}
	
	function FrontFormTxsm(){
		art.dialog({
	 		lock : true,
	 		width:"600px",
	 		title:'填写说明',
	 		content: '1.经营者名称应当与营业执照上标注的名称一致。<br/>2.社会信用代码（身份证号码）栏参照营业执照填写社会信用代码，无社会信用代码的填写营业执照号码；无营业执照的机关、企、事业单位、社会团体以及其他组织机构，填写组织机构代码；个体经营者填写相关身份证件号码。<br/>3.本申请书内所称法定代表人（负责人）包括：①企业法人的法定代表人；②个人独资企业的投资人；③分支机构的负责人；④合伙企业的执行事务合伙人（委派代表）；⑤个体工商户业主；⑥农民专业合作社的法定代表人。<br/>4.填写住所、经营场所时要具体表述所在位置，明确到门牌号、房间号，住所应与营业执照（或组织机构证、相关身份证件）内容一致。<br/>5.申请人应选择主体业态和经营项目，并在□中打√。<br/>6.本申请书内所称食品安全管理人员是指企业内部专职或兼职的食品安全负责人。'
		});
	}
	
	function JYZMCTxsm(){
		art.dialog({
	 		lock : true,
	 		width:"600px",
	 		title:'填写说明',
	 		content: '应与营业执照标注的名称保持一致'
		});
	}
	
	function getIsTrueData(){
		var isTrue = true; 
		var ZTYTDMVALUE = $("input[name='ZTYTDM']:checked").val();
		if (ZTYTDMVALUE == "1") {
			var YYFS = $("input[name='YYFS']:checked").val();
			if(!YYFS){
				isTrue = false;
			}
		}else if (ZTYTDMVALUE == "2") {
			var CYLX = $("input[name='CYLX']:checked").val();
			if(!CYLX){
				isTrue = false;
			}
		}else if (ZTYTDMVALUE == "3") {
			var SFWXXST = $("input[name='SFWXXST']:checked").val();
			if(!SFWXXST){
				isTrue = false;
			}
		}
		if($("input[name='DLLSPZS'][value='01']").is(':checked')){
			var YBZSPXS = $("input[name='YBZSPXS']:checked").val();
			if(!YBZSPXS){
				isTrue = false;
			}
			
		}
		if($("input[name='DLLSPZS'][value='02']").is(':checked')){
			var SZSSXS = $("input[name='SZSSXS']:checked").val();
			if(!SZSSXS){
				isTrue = false;
			}
			var SZSPXS = $("input[name='SZSPXS']:checked").val();
			if(!SZSPXS){
				isTrue = false;
			}
			
		}
		if($("input[name='DLLSPZS'][value='14']").is(':checked')){
			var ZNJZS = $("input[name='ZNJZS']:checked").val();
			if(!ZNJZS){
				isTrue = false;
			}
			
		}
		if( $("select[name='ZSQS']").val()==""||$("select[name='ZSQS']").val()==undefined){
			isTrue = false;
		}
		if( $("select[name='ZSXS']").val()==""||$("select[name='ZSXS']").val()==undefined){
			isTrue = false;
		}
		if( $("select[name='JYCSQS']").val()==""||$("select[name='JYCSQS']").val()==undefined){
			isTrue = false;
		}
		if( $("select[name='JYCSXS']").val()==""||$("select[name='JYCSXS']").val()==undefined){
			isTrue = false;
		}
		return isTrue;
	}
</script>