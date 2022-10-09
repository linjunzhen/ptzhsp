
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}
function setQLRName(val){
	var datas = $('#QLR_MC').combobox('getData');
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME == val){
			$("input[name='QLR_ZJNO']").val(datas[i].DIC_CODE);
			//$("input[name='QLR_ZJNO']").val(datas[i].DIC_DESC);//法人代表
			break ;
		}
	}
	$("input[name='QLR_LABEL']").val(val); 
	/* var text = $("select[name='QLR_MC']").find("option:selected").text();
	$("input[name='QLR_ZJNO']").val(val);
	$("input[name='QLR_LABEL']").val(text); */
}

/**
 * 预购商品房抵押权预告登记登簿操作
 */
function ygspfdyqygdjDbcz(){
	var ZW_BEGIN = $('input[name="ZW_BEGIN"]').val();
	var ZW_END = $('input[name="ZW_END"]').val();
	var DBSE = $('input[name="DBSE"]').val();
	var HZ_OPINION_CONTENT = $("textarea[name='HZ_OPINION_CONTENT']").val();
	if((ZW_BEGIN==null || ZW_BEGIN=="" || ZW_BEGIN==undefined) ||
	   (ZW_END==null || ZW_END=="" || ZW_END==undefined) ||
	   (DBSE==null || DBSE=="" || DBSE==undefined) ||
	   (HZ_OPINION_CONTENT==null || HZ_OPINION_CONTENT=="" || HZ_OPINION_CONTENT==undefined)){
		art.dialog({
			content: "期限、贷款金额、核定意见不允许为空，请填写并点击相应的保存按钮！",
			icon: "error",
			time: 5,
			ok: true
		});
		return;
	}
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var flowSubmitObj = FlowUtil.getFlowObj();
	var exeId = flowSubmitObj.EFLOW_EXEID;
	if(exeId != null && typeof exeId != undefined){
		art.dialog.confirm("您确定要进行登簿吗?", function() {
			var layload = layer.load('正在提交登簿数据…');
			$.post("bdcQlcApplyController/bdcdbcz.do", {
				"eveId" : exeId
			}, function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success){
					var data = $.parseJSON(resultJson.data);
					$("input[name='BDC_DBJSON']").val(JSON.stringify(data))
					if(data.retcode == "00" && data.dbzt == "1"){
						if(data.qzinfo && data.qzinfo.length > 0){
							var qzinfo = data.qzinfo[0];
							$("input[name='BDC_DBZT']").val(qzinfo.gdzt);
							if(qzinfo.gdzt == "1"){
								$("input[name='BDC_BDCDJZMH']").val(qzinfo.qzzh);
								$("input[name='BDC_DBJG']").val("登簿成功");
								$("input[name='BDC_DBR']").val(currentUser);
								$("input[name='BDC_DJSJ']").val(currentTime);
								disabledDbBtn("qrdb");
								art.dialog({
									content :"登簿成功",
									icon : "succeed",
									ok : true
								});
							}else{
								$("input[name='BDC_DBJG']").val(qzinfo.ret_msg);
								art.dialog({
									content :"归档失败，"+qzinfo.ret_msg,
									icon : "warning",
									ok : true
								});
							}
						}
					}else{
						$("input[name='BDC_DBZT']").val(data.dbzt);
						$("input[name='BDC_DBJG']").val(data.ret_msg);
						art.dialog({
							content : data.ret_msg+"登簿失败。",
							icon : "warning",
							ok : true
						});
					}
				}else{
					art.dialog({
						content : resultJson.msg,
						icon : "warning",
						ok : true
					});
				}
			});
		});
	}
}


//不动产预告登记查询
function showSelectBdcYgdacx(){
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=1", {
		title : "不动产预告登记查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcdydacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcdydacxInfo && bdcdydacxInfo.length == 1){
				var YGDJZL = bdcdydacxInfo[0].YGDJZL;
				var dyinfo;
				if(YGDJZL && YGDJZL=='预售商品房买卖预告登记'){		
					dyinfo = {
						"ESTATE_NUM":bdcdydacxInfo[0].BDCDYH,
						"LOCATED":bdcdydacxInfo[0].BDCZL,
					/*	"YWR_MC":bdcdydacxInfo[0].QLR,
						"YWR_ZJLX":bdcdydacxInfo[0].QLRZJZL,
						"YWR_ZJNO":bdcdydacxInfo[0].QLRZJH,
						"DBSE":bdcdydacxInfo[0].QDJG, */
						"DJXX_DYH":bdcdydacxInfo[0].BDCDYH,
						"DJXX_CQZH":bdcdydacxInfo[0].BDCDJZMH,
						"DJXX_QLR":bdcdydacxInfo[0].QLR,
						"DJXX_JZAREA":bdcdydacxInfo[0].JZMJ,
						"DJXX_ZL":bdcdydacxInfo[0].BDCZL,
						/*"ZW_BEGIN":bdcdydacxInfo[0].QSSJ,
						"ZW_END":bdcdydacxInfo[0].JSSJ,*/
						"FW_ADDR":bdcdydacxInfo[0].BDCZL,
						"HT_LX":bdcdydacxInfo[0].HTLX,
						"BDCDYH":bdcdydacxInfo[0].BDCDYH,
						"YWH":bdcdydacxInfo[0].YWH,
						"GLH":bdcdydacxInfo[0].GLH,
						"APPLICANT_UNIT":bdcdydacxInfo[0].QLR,
						"HT_NO":bdcdydacxInfo[0].HTH,
						"BDC_BDCDYH": bdcdydacxInfo[0].BDCDYH
					};	
					//回填义务人
					setYwrList(bdcdydacxInfo[0].QLR,bdcdydacxInfo[0].QLRZJH);
				} else {				
					dyinfo = {
						"ESTATE_NUM":bdcdydacxInfo[0].BDCDYH,
						"LOCATED":bdcdydacxInfo[0].BDCZL,
					/*	"YWR_MC":bdcdydacxInfo[0].QLR,
						"YWR_ZJLX":bdcdydacxInfo[0].QLRZJZL,
						"YWR_ZJNO":bdcdydacxInfo[0].QLRZJH,
						"DBSE":bdcdydacxInfo[0].QDJG, */
						"DJXX_DYH":bdcdydacxInfo[0].BDCDYH,
						"DJXX_CQZH":bdcdydacxInfo[0].BDCDJZMH,
						"DJXX_QLR":bdcdydacxInfo[0].QLR,
						"DJXX_JZAREA":bdcdydacxInfo[0].JZMJ,
						"DJXX_ZL":bdcdydacxInfo[0].BDCZL,
						/*"ZW_BEGIN":bdcdydacxInfo[0].QSSJ,
						"ZW_END":bdcdydacxInfo[0].JSSJ,*/
						"FW_ADDR":bdcdydacxInfo[0].BDCZL,
						"HT_LX":bdcdydacxInfo[0].HTLX,
						"BDCDYH":bdcdydacxInfo[0].BDCDYH,
						"YWH":bdcdydacxInfo[0].YWH,
						"GLH":bdcdydacxInfo[0].GLH,
						"APPLICANT_UNIT":bdcdydacxInfo[0].YWR,
						"HT_NO":bdcdydacxInfo[0].HTH,
						"BDC_BDCDYH": bdcdydacxInfo[0].BDCDYH
					};
					setYwrList(bdcdydacxInfo[0].YWR,bdcdydacxInfo[0].YWRZJH);
				}
				//AppUtil.verifyBdcdyhExist('BDCDYH',bdcdydacxInfo[0].BDCDYH);
				var result = checkFwCqzt(dyinfo.ESTATE_NUM);
				if(result.isPass){
					if(result.msg){						
						art.dialog.confirm(result.msg, function() {	
							/*获取房屋单元信息*/
							$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:bdcdydacxInfo[0].BDCDYH}, function (responseText, status, xhr) {
								$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
								if(responseText.rows && responseText.rows.length>0){
									$("[name='YTSM']").val(responseText.rows[0].GHYT);
									$("[name='FWXZ']").val(responseText.rows[0].FWXZ);
								}
							})
							FlowUtil.initFormFieldValue(dyinfo,"T_BDCQLC_YGSPFDYQYGDJ_FORM");
							/*将预告信息存入数据库*/
							$("input[name='YGXX_JSON']").val(JSON.stringify(bdcdydacxInfo));
							AppUtil.verifyAllBdcdyhExist(bdcdydacxInfo[0].BDCDYH);			
						});	
					} else{						
						/*获取房屋单元信息*/
						$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:bdcdydacxInfo[0].BDCDYH}, function (responseText, status, xhr) {
							$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
							if(responseText.rows && responseText.rows.length>0){
								$("[name='YTSM']").val(responseText.rows[0].GHYT);
								$("[name='FWXZ']").val(responseText.rows[0].FWXZ);
							}
						})
						FlowUtil.initFormFieldValue(dyinfo,"T_BDCQLC_YGSPFDYQYGDJ_FORM");
						/*将预告信息存入数据库*/
						$("input[name='YGXX_JSON']").val(JSON.stringify(bdcdydacxInfo));
						AppUtil.verifyAllBdcdyhExist(bdcdydacxInfo[0].BDCDYH);
					}
				}else{
					parent.art.dialog({
						lock: true,
						content: result.msg,
						icon:"warning",
						ok: true
					});
				}
				art.dialog.removeData("bdcygdacxInfo");
			}else{
				parent.art.dialog({
					content: "请根据查询选择一条预告登记信息。",
					icon:"warning",
					ok: true
				});
			}
		}
	}, false);
};

//查看访问状态
function checkFwCqzt(dyh){
	var failStatus = ['在建工程抵押','预售抵押预告','权属登记','权属注销',
						'抵押','查封','异议','地役','内控','限制'];
	var result = {
		"isPass":false,
		"type":"0",
		"msg":"系统异常"
	};
	$.ajax({
        type: "POST",
        url: "bdcApplyController/bdcFwztCheck.do?bdcdyh="+dyh,
        async: false, //采用同步方式进行数据判断
        cache: false,
        success: function (responseText) {
        	var resultJson = $.parseJSON(responseText);
        	if(resultJson.success){
				var list = resultJson.data;			
				if(list != null && list.length > 0){
					//CQZT
					if(list.length == 1 && list[0].CQZT != null){
						/*if(list[0].CQZT.indexOf("预售预告 已售") != -1){				
							result = {
								"isPass":true,
								"msg":""
							};
						} else{							
							result = {
								"isPass":true,
								"msg":"请注意，该不动产单元号为“"+list[0].CQZT+"”状态，是否继续办理业务?",
								"type":"0"
							};
						}	*/	
						var flag = true;
						for(var i = 0;i<failStatus.length;i++){
							if(list[0].CQZT.indexOf(failStatus[i])>=0){
								flag = false;
								result = {
									"isPass":false,
									"msg":"请注意：该不动产单元号产权状态处于“"+list[0].CQZT+"”中,不可受理此登记！",
									"type":"0"
								};
								break;
							}
						} 
						if(flag){
							result = {
								"isPass":true,
								"msg":""
							};
						}
					}
			   }else{
				   result = {
						"isPass":false,
						"msg":"暂查无该房屋状态信息,无法查验该房屋状态,不可继续办理业务。",
						"type":"0"
					};
			   }
		   }else{
			   result = {
					"isPass":false,
					"msg":resultJson.msg,
					"type":"0"
				}; 
		   }
        }
    });
	return result;
}

//检验是不动产单元号对应的房屋状态
function checkIsAuditPass(){
	var isPass = false;
	var reg = /^(\d{6})([a-zA-Z0-9]{6})([a-zA-Z]{2})([a-zA-Z0-9]{14})$/;
	var val = $("#ESTATE_NUM").val();
	if(val.match(reg)){
		AppUtil.ajaxProgress({
			url : "bdcApplyController/bdcFwztCheck.do",
			params : {
				"bdcdyh":val
			},
			callback : function(resultJson) {
				if(resultJson.success){
					var list = resultJson.data;			
					if(list != null && list.length > 0){
						
						if(list.length == 1 && list[0].CQZT != null){		
							if(list[0].CQZT.indexOf("预售预告 已售") != -1){				
								result = {
									"isPass":true
								};
								AppUtil.verifyAllBdcdyhExist(val);	
							} else{												
								art.dialog.confirm("请注意，该不动产单元号为“"+list[0].CQZT+"”状态，是否继续办理业务?", function() {	
									//$("#isAuditPass").val("1");
									AppUtil.verifyAllBdcdyhExist(val);	
								})
							}		
						}
				   }else{
						art.dialog({
							lock: true,
							content: "暂查无该房屋状态信息,无法查验该房屋状态，不可继续办理业务。",
							icon:"warning",
							ok: true
						});
				   }
			   }else{
					art.dialog({
						lock: true,
						content: resultJson.msg,
						icon:"warning",
						ok: true
					});
			   }
			}
		});
	}
	return isPass;
}


		
function newDicInfoWin(typeCode,combId){
	$.dialog.open("bdcApplyController.do?wtItemInfo&typeCode="+typeCode, {
		title : "新增",
        width:"600px",
        lock: true,
        resize:false,
		fixed: true,
        height:"240px",
        close: function(){
			$("#"+combId).combobox("reload");
		}
	}, false);
}

function removeDicInfo(combId){
	var datas = $("#"+combId).combobox("getData");
	var val = $("#"+combId).combobox("getValue");
	var id = "";
	for(var i=0;i<datas.length;i++){
		if(datas[i].DIC_NAME==val){
			id = datas[i].DIC_ID;
			break;
		}
	}
	art.dialog.confirm("您确定要删除该记录吗?", function() {
		var layload = layer.load('正在提交请求中…');
		$.post("dictionaryController.do?multiDel",{
			   selectColNames:id
		    }, function(responseText, status, xhr) {
		    	layer.close(layload);
		    	var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					$("#"+combId).combobox("reload");
					$("#"+combId).combobox("setValue","");
				}else{
					$("#"+combId).combobox("reload");
					art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
		});
	});
}

function notPrint() {
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}

function goPrintTemplate(TemplateName,typeId) {
	var dataStr = "";
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //4代表为权证模板
	//dataStr +="&TemplatePath="+TemplatePath;
	dataStr +="&TemplateName="+TemplateName;
	//dataStr +="&isSignButton="+isSignButton;
	var url=encodeURI("printAttachController.do?printTemplate"+dataStr);
	//打印模版
	$.dialog.open(url, {
		title : "打印模版",
		width: "100%",
		height: "100%",
		left: "0%",
		top: "0%",
		fixed: true,
		lock : true,
		resize : false
	}, false);
}


function bdcYgspfdyqygdjDJZMprint(typeId) {
	// typeId: 1为证书预览  2为证书打印
	var title = "证书打印";
	var templateAlias = 'DJZM';
	if(typeId==1) {
		title = "证书预览";
	}
	var dataStr = "";
	dataStr +="&QLRZJH="+$("input[name='QLR_ZJNO']").val();
	dataStr +="&BDCQZH="+$("input[name='ESTATE_NUM']").val();
	dataStr +="&EFLOW_EXEID="+$("#EXEID").text();
	dataStr +="&typeId="+typeId;   //1为证书预览  2为证书打印
	dataStr +="&templateAlias=" + templateAlias;
	var url=encodeURI("certificateTemplateController.do?invokeCustomMethod"+dataStr);
	$.dialog.open(url, {
		title : title,
		width: "100%",
		height: "100%",
		left: "0%",
		top: "0%",
		fixed: true,
		lock : true,
		resize : false
	}, false);
}

/*
* 获取外网数据
* */
function getBdcWwData() {
	var BDC_WWSQBH = $("input[name='BDC_WWSQBH']").val();
	if (BDC_WWSQBH) {
		$.post("bdcGetWwDataController/getBdcWwData.do",{bdcWwsqbh:BDC_WWSQBH},function (data) {
			var json = JSON.parse(data);
			if (json.success) {
				/*申请表数据*/
				var sqbData = json.sqbData;
                var sqrzbData = json.sqrzbData;
				if (sqbData) {					
					var result = checkFwCqzt(sqbData.BDCDYH);
					if(result.isPass){
						if(result.msg){							
							art.dialog.confirm(result.msg, function() {	
								initWwData(sqbData,sqrzbData);
								//getBdcWwCl(BDC_WWSQBH);		
								//AppUtil.verifyBdcdyhExist('BDCDYH',sqbData.BDCDYH);	
								AppUtil.verifyAllBdcdyhExist(sqbData.BDCDYH);	
							})	
						} else{
							initWwData(sqbData,sqrzbData);
							AppUtil.verifyAllBdcdyhExist(sqbData.BDCDYH);	
						}	
					}else{
						parent.art.dialog({
							lock: true,
							content: result.msg,
							icon:"warning",
							ok: true
						});
					}
				}
			} else {
				art.dialog({
					content: json.msg,
					icon: "error",
					time: 3,
					ok: true
				});
			}
		})
	} else {
		art.dialog({
			content: "请输入外网申请编号",
			icon:"warning",
			time:3,
			ok: true
		});
	}
}
function initWwData(sqbData,sqrzbData){	
	/*获取房屋单元信息*/
	$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:sqbData.BDCDYH}, function (responseText, status, xhr) {
		$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
		$("[name='YTSM']").val(responseText.rows[0].GHYT);
		$("[name='FWXZ']").val(responseText.rows[0].FWXZ);
	})
	var sqbDatas = [];
	sqbDatas.push(sqbData);
	/*将预告信息存入数据库*/
	$("input[name='YGXX_JSON']").val(JSON.stringify(sqbDatas));
	if(sqbData.BDCZL){		
		$("input[name='LOCATED']").val(sqbData.BDCZL);
		$("input[name='FW_ADDR']").val(sqbData.BDCZL);
	}
	$("input[name='BDCDYH']").val(sqbData.BDCDYH);
	$("input[name='ESTATE_NUM']").val(sqbData.BDCDYH);
	$("input[name='BDC_BDCDYH']").val(sqbData.BDCDYH);
	$("input[name='DBSE']").val(sqbData.ZZQSE/10000);
	$("input[name='HT_NO']").val(sqbData.MMHTH);
	
	if(sqbData.TDYT){//用途说明	
		$("[name='YTSM']").val(sqbData.TDYT);
	}
	var ZW_BEGIN = sqbData.ZQQSSJ;
	var ZW_END = sqbData.ZQJSSJ;
	if (ZW_BEGIN) {
		ZW_BEGIN = ZW_BEGIN.substring(0, 10).replace(/\//g,"-");
		$("input[name='ZW_BEGIN']").val(ZW_BEGIN);
	}
	if (ZW_END) {
		ZW_END = ZW_END.substring(0, 10).replace(/\//g,"-");
		$("input[name='ZW_END']").val(ZW_END);
	}
	$("input[name='FJ_INFO']").val(sqbData.FJ);							
	
	if (sqrzbData) {
		var ywrNum = 0;
		var YWRXM = "";
		for (let i = 0; i < sqrzbData.length; i++) {
			if (sqrzbData[i].RYLX == '1') {
				$("#QLR_MC").combobox("setValue",sqrzbData[i].XM);
				var QLR_ZJLX = changeDocumentType(sqrzbData[i].ZJLX);
				$("select[name='QLR_ZJLX']").find("option[value='"+QLR_ZJLX+"']").attr("selected",true);
				$("input[name='QLR_ZJNO']").val(sqrzbData[i].ZJHM);
				
				
				//$("[name='SQRMC']").val(sqrzbData[i].XM);							
				//$('#SQRZJLX').val(changeDocumentTypeToCode(sqrzbData[i].ZJLX));//证件类别
				//$('input[name="SQRSFZ"]').val(sqrzbData[i].ZJHM);//证件号码
			}
			if (sqrzbData[i].RYLX == '2') {
				YWRXM += sqrzbData[i].XM + ",";
				setYwrInfo(ywrNum,sqrzbData[i]);
				ywrNum ++;
			}
					
			if(YWRXM){						 		
				$('input[name="APPLICANT_UNIT"]').val(YWRXM.substring(0, YWRXM.length - 1));//申请人		
			}  
		}
	}
}

/*
* 获取外网材料数据
* */
function getBdcWwCl(BDC_WWSQBH) {
	if (BDC_WWSQBH) {
		$.post("bdcGetWwDataController/getBdcWwCl.do", {bdcWwsqbh: BDC_WWSQBH}, function (data) {
			if (data) {
				$("#wwcl").show();
				var str = "<tr>\n" +
					"\t\t<th colspan=\"8\">外网材料信息</th>\n" +
					"\t</tr>"
				str += "<tr>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"5%\">序号</td>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"20%\">材料名称</td>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"20%\">显示名称</td>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"20%\">真实名称</td>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"20%\">备注</td>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"5%\">介质类型</td>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"5%\">份数</td>\n" +
					"\t\t<td class=\"tab_width1\" align=\"center\" style=\"font-weight: 700;\" width=\"5%\">操作</td>\n" +
					"\t</tr>";
				var json = JSON.parse(data);
				for (let i = 0; i < json.length; i++) {
					var jzlx = json[i].JZLX;
					jzlx = jzlx ? (jzlx = (jzlx == 1 ? "原件" : "复印件")) : "";
					str += "<tr><td class=\"tab_width1\" align=\"center\">" + (i + 1) + "</td>";
					str += "<td class=\"tab_width1\" align=\"center\">" + json[i].CLMC + "</td>";
					str += "<td class=\"tab_width1\" align=\"center\">" + (json[i].XSMC == null ? "" : json[i].XSMC) + "</td>";
					str += "<td class=\"tab_width1\" align=\"center\">" + (json[i].ZSMC == null ? "" : json[i].ZSMC) + "</td>";
					str += "<td class=\"tab_width1\" align=\"center\">" + (json[i].BZ == null ? "" : json[i].BZ) + "</td>";
					str += "<td class=\"tab_width1\" align=\"center\">" + jzlx + "</td>";
					str += "<td class=\"tab_width1\" align=\"center\">" + (json[i].FS == null ? "" : json[i].FS) + "</td>";
					str += "<td class=\"tab_width1\" align=\"center\"><span style='cursor: pointer;color: dodgerblue;' onclick='AppUtil.downLoadFile(\""+json[i].FILE_ID+"\")'>下载</span></td></tr>";
				}
				$("#wwcl").html(str);
			}
		});
	}
}


/*转换证件类型*/
function changeDocumentType(type) {
    var str = "";
    switch (type) {
        case '1':
            str = "身份证";
            break;
        case '2':
            str = "港澳台身份证";
            break;
        case '3':
            str = "护照";
            break;
        case '4':
            str = "户口簿";
            break;
        case '5':
            str = "军官证（士兵证）";
            break;
        case '6':
            str = "组织机构代码";
            break;
        case '7':
            str = "营业执照";
            break;
        case '8':
            str = "港澳居民来往内地通行证";
            break;
        case '9':
            str = "台胞证";
            break;
        case '10':
            str = "统一社会信用代码";
            break;
        case '99':
            str = "其它";
    }
    return str;
}

/*转换证件类型*/
function changeDocumentTypeToCode(type) {
    var str = "";
    switch (type) {
        case '1':
            str = "SF";
            break;
        case '2':
            str = "TWSF";
            break;
        case '3':
            str = "HZ";
            break;
        case '4':
            str = "SF";
            break;
        case '5':
            str = "SB";
            break;
        case '6':
            str = "JGDM";
            break;
        case '7':
            str = "YYZZ";
            break;
        case '8':
            str = "GATX";
            break;
        case '9':
            str = "TWTX";
            break;
        case '10':
            str = "YYZZ";
            break;
        case '99':
            str = "QT";
    }
    return str;
}

var ywrInfoSn = 1;
function addYwrInfo() {
	ywrInfoSn = ywrInfoSn + 1;
	var table = document.getElementById("ywrInfo");
	var trContent = table.getElementsByTagName("tr")[0];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteYwrInfo('");
	if (ywrInfoSn > 10) {
		var replacestr = trHtml.substring(findex, findex + 19);
	} else {
		var replacestr = trHtml.substring(findex, findex + 18);
	}
	trHtml = trHtml.replace(replacestr, "deleteYwrInfo('" + ywrInfoSn + "')");
	trHtml = "<tr id=\"ywrInfo_" + ywrInfoSn + "\">" + trHtml + "</tr>";
	$("#ywrInfo").append(trHtml);
	$("#ywrInfo_" + ywrInfoSn + " [name='YWR_ZJLX']").val("身份证");
	$("#ywrInfo_" + ywrInfoSn + " [name='YWR_MC']").val("");
	$("#ywrInfo_" + ywrInfoSn + " [name='YWR_ZJNO']").val("");
}

function deleteYwrInfo(idSn) {
	var table = document.getElementById("ywrInfo");
	if (table.rows.length == 1) {
		parent.art.dialog({
			content : "最少一个义务人信息！",
			icon : "warning",
			ok : true
		});
		return false;
	}
	$("#ywrInfo_" + idSn).remove();
}
function setYwrInfo(num,data){
	
	var table = document.getElementById("ywrInfo");
	if(table.rows.length>=(Number(num)+Number(1))){
		$("#ywrInfo").find('table').eq(num).find("[name='YWR_MC']").val(data.XM);
		$("#ywrInfo").find('table').eq(num).find("[name='YWR_ZJNO']").val(data.ZJHM);
         var YWR_ZJLX = changeDocumentType(data.ZJLX);
		$("#ywrInfo").find('table').eq(num).find("[name='YWR_ZJLX']").find("option[value='"+YWR_ZJLX+"']").attr("selected",true);
	} else{			
		addYwrInfo();
		$("#ywrInfo").find('table').eq(num).find("[name='YWR_MC']").val(data.XM);
		$("#ywrInfo").find('table').eq(num).find("[name='YWR_ZJNO']").val(data.ZJHM);
         var YWR_ZJLX = changeDocumentType(data.ZJLX);
		$("#ywrInfo").find('table').eq(num).find("[name='YWR_ZJLX']").find("option[value='"+YWR_ZJLX+"']").attr("selected",true);
	}
}
function setYwrToBdcygdjcxInfo(YWR_MC,YWR_ZJNO){	
	var sign = [',',';','|'];
	var names;
	var cards;
	for(var i = 0; i < sign.length; i++){
		if(YWR_MC.indexOf(sign[i])!=-1){
			names = YWR_MC.split(sign[i]);
		}
		if(YWR_ZJNO.indexOf(sign[i])!=-1){
			cards = YWR_ZJNO.split(sign[i]);
		}
	}
	var table = document.getElementById("ywrInfo");
	if(names){		
		for (var i = 0; i < names.length; i++) {
			if(table.rows.length>=(Number(i)+Number(1))){
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_MC']").val(names[i]);
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_ZJNO']").val(cards[i]);
			} else{			
				addYwrInfo();
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_MC']").val(names[i]);
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_ZJNO']").val(cards[i]);
			}
		}
	}else{		
		$("#ywrInfo").find('table').eq(0).find("[name='YWR_MC']").val(YWR_MC);
		$("#ywrInfo").find('table').eq(0).find("[name='YWR_ZJNO']").val(YWR_ZJNO);
	}
}
/**
 * 获取权利人JSON
 */
function getYwrInfoJson() {
	var datas = [];
	var table = document.getElementById("ywrInfo");
	for (var i = 0; i < table.rows.length; i++) {
		var trData = {};
		$("#ywrInfo").find('table').eq(i).find("*[name]").each(function() {
			var inputName = $(this).attr("name");
			var inputValue = $(this).val();
			//获取元素的类型
			var fieldType = $(this).attr("type");
			if (fieldType == "radio") {
				var isChecked = $(this).is(':checked');
				if (isChecked) {
					trData[inputName] = inputValue;
				}
			} else if (fieldType == "checkbox") {
				var inputValues = FlowUtil.getCheckBoxValues(inputName);
				trData[inputName] = inputValues;
			} else if (fieldType != "button") {
				trData[inputName] = inputValue;
			}
		});
		datas.push(trData);

	}
	$("input[type='hidden'][name='YWR_JSON']").val(JSON.stringify(datas));
}

function initAutoTable(flowSubmitObj) {
	var ywrJson = flowSubmitObj.busRecord.YWR_JSON;
	if (null != ywrJson && '' != ywrJson) {
		var ywrInfos = eval(ywrJson);
		for (var i = 0; i < ywrInfos.length; i++) {
			if (i == 0) {
				FlowUtil.initFormFieldValue(ywrInfos[i], "ywrInfo_1");
			} else {
				addYwrInfo();
				FlowUtil.initFormFieldValue(ywrInfos[i], "ywrInfo_" + (i + 1));
			}
		}
	}
	
}


//保存预抵押权预告信息
function saveYdxx(){
	var ZW_BEGIN = $('input[name="ZW_BEGIN"]').val();
	var ZW_END = $('input[name="ZW_END"]').val();
	var DBSE = $('input[name="DBSE"]').val();
	if((ZW_BEGIN==null || ZW_BEGIN=="" || ZW_BEGIN==undefined) ||
	   (ZW_END==null || ZW_END=="" || ZW_END==undefined) ||
	   (DBSE==null || DBSE=="" || DBSE==undefined)){
		art.dialog({
			content: "期限、贷款金额不允许为空，请填写并点击保存按钮！",
			icon: "error",
			time: 5,
			ok: true
		});
		return;
	}
	/*var ygdyqygInfo = FlowUtil.getFormEleData("ygdyqygInfo");
	var postParam = $.param(ygdyqygInfo);*/
	var params = {};
	var flowSubmitObj = FlowUtil.getFlowObj();
	params.busRecordId = flowSubmitObj.EFLOW_BUSRECORDID;
	params.busTableName = flowSubmitObj.EFLOW_BUSTABLENAME;
	params.ZW_BEGIN = ZW_BEGIN;
	params.ZW_END = ZW_END;
	params.DBSE = DBSE;
	if(flowSubmitObj){
		AppUtil.ajaxProgress({
			url: "bdcYgspfygdjController.do?saveDyqdjxx",
			params: params,
			callback: function (data) {
				if (data) {
					if (data.success) {
						art.dialog({
							content: "保存成功",
							icon: "succeed",
							time: 3,
							ok: true
						});
					} else {
						art.dialog({
							content: "保存失败",
							icon: "error",
							time: 3,
							ok: true
						});
					}
				} else {
					art.dialog({
						content: "保存失败",
						icon: "error",
						time: 3,
						ok: true
					});
				}
			}
		});
	}
}


//回填义务人信息（存在多个的情况）
function setYwrList(YWR_MC,YWR_ZJNO){
	var sign = [',',';','|'];
	var names;
	var cards;
	for(var i = 0; i < sign.length; i++){
		if(YWR_MC.indexOf(sign[i])!=-1){
			names = YWR_MC.split(sign[i]);
		}
		if(YWR_ZJNO.indexOf(sign[i])!=-1){
			cards = YWR_ZJNO.split(sign[i]);
		}
	}
	var table = document.getElementById("ywrInfo");
	if(names){	
		for (var i = 0; i < names.length; i++) {
			if(table.rows.length>=(Number(i)+Number(1))){
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_MC']").val(names[i]);
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_ZJNO']").val(cards[i]);
			} else{			
				addYwrInfo();
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_MC']").val(names[i]);
				$("#ywrInfo").find('table').eq(i).find("[name='YWR_ZJNO']").val(cards[i]);
			}
		}
	} else{		
		$("#ywrInfo").find('table').eq(0).find("[name='YWR_MC']").val(YWR_MC);
		$("#ywrInfo").find('table').eq(0).find("[name='YWR_ZJNO']").val(YWR_ZJNO);
	}
}
