
//选择证件类型为身份证时添加证件号码验证
function setZjValidate(zjlx,name){
	if(zjlx=="身份证"){
		$("input[name='"+name+"']").addClass(",custom[vidcard]");
	}else{
		$("input[name='"+name+"']").removeClass(",custom[vidcard]");
	}
}
//设置用途说明
function setYtms(value){
	$("input[name='YTMS']").val(value);
}
//房地产合同备案查询
function showSelectFdchtbacx(){
	$.dialog.open("bdcYgspfygdjController.do?fdchtbaxxcxNewSelector&allowCount=1", {
		title : "房地产合同备案信息查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var fdchtbaxxInfo = art.dialog.data("fdchtbaxxInfo");
			if(fdchtbaxxInfo&&fdchtbaxxInfo.length==1){
				/*保存合同备案信息json*/
				$("input[name='HTBAXX_JSON']").val(JSON.stringify(fdchtbaxxInfo));
				/*获取房屋单元信息*/
				$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:fdchtbaxxInfo[0].BDCDYH}, function (responseText, status, xhr) {
					$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));
					$("input[name='YTMS']").val(responseText.rows[0].YTSM);
					$('[name="YTSM"]').val(responseText.rows[0].GHYT)
				})
				/*回填数据*/
				for(var i = 0;i<fdchtbaxxInfo.length;i++){
					$('input[name="ZL"]').val(fdchtbaxxInfo[i].ZL);//坐落
					$('input[name="BDCDYH"]').val(fdchtbaxxInfo[i].BDCDYH);//不动产单元号
					$("input[name='BDC_BDCDYH']").val(fdchtbaxxInfo[i].BDCDYH);//不动产单元号
					$('input[name="FWDZ"]').val(fdchtbaxxInfo[i].ZL);//房屋地址
					$('input[name="APPLICANT_UNIT"]').val(fdchtbaxxInfo[i].MSFXM);//申请人
					$('input[name="YTMS"]').val(fdchtbaxxInfo[i].YTSM);//用途说明
					$('[name="YTSM"]').val(fdchtbaxxInfo[i].YTSM);//用途说明
					FlowUtil.initFormFieldValue(fdchtbaxxInfo[i],"ygygInfo");//预购预告信息回填
					$('[name="ZRFZJLB"]').val(formatZjlx(fdchtbaxxInfo[i].ZRFZJLB));//义务人证件种类
					//回填权利人
					setQlrInfo(fdchtbaxxInfo[i].MSFXM,fdchtbaxxInfo[i].MSFZJHM);
					
					//申报对象信息回填
					/* $('input[name="SQRMC"]').val(fdchtbaxxInfo[i].MSFXM);//申请人
					$('#SQRZJLX').find("option").filter(function(index){
						return fdchtbaxxInfo[i].MSFZJLB===$(this).text();
					}).attr("selected","selected");//证件类别
					$('input[name="SQRSFZ"]').val(fdchtbaxxInfo[i].MSFZJHM);//证件号码 */
					
					//AppUtil.verifyBdcdyhExist('BDCDYH',fdchtbaxxInfo[i].BDCDYH);
					AppUtil.verifyAllBdcdyhExist(fdchtbaxxInfo[i].BDCDYH);
				}
			}	
			art.dialog.removeData("fdchtbaxxInfo");
		}	
	}, false);
}
/*格式化证件类别*/
function formatZjlx(zjlx) {
    if (zjlx) {
		if (zjlx == '统一社会编码'){
            return "统一社会信用代码";
        } else {
            return zjlx;
        }
    } 
	return zjlx;
}

//流程表单提交方法
function FLOW_SubmitFun(flowSubmitObj){
	 //先判断表单是否验证通过
	 var validateResult =$("#T_BDC_YGSPFYGDJ_FORM").validationEngine("validate");
	 if(validateResult){
      var isAuditPass = $('input[name="isAuditPass"]:checked').val();
	     if(isAuditPass == "-1"){
	     	 parent.art.dialog({
				content : "检查上传的审批表扫描件不合规，请先退回补件。",
				icon : "warning",
				ok : true
			 });
			 return null;
	     }else{
			 if (flowSubmitObj.EFLOW_CUREXERUNNINGNODENAMES == '开始') {
				 /*判断此业务是否已被办理过*/
				 var bdcdyh = $("input[name='BDCDYH']").val();
				 var bdcdyhFlag = AppUtil.verifyBdcdyhExistSubmit(bdcdyh);
				 if (!bdcdyhFlag) {
					 art.dialog({
						 content: "请注意：该不动产单元号已经办理过该业务！",
						 icon:"warning",
						 ok: true
					 });
					 return null;
				 }
			 }
	    	 if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == '登簿') {
					var isdbflag = $("input[name='BDC_DBZT']").val();
					if(isdbflag){
						if(isdbflag =="-1"){
							parent.art.dialog({
								content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
								icon : "warning",
								ok : true
							});
							return;
						}else if(isdbflag =="0"){
							var dbjg = $("input[name='BDC_DBJG']").val();
							parent.art.dialog({
								content : "登簿异常！"+dbjg,
								icon : "warning",
								ok : true
							});
							return;
						}
					}else{
						parent.art.dialog({
							content : "未确认登簿，请先完成登簿操作，并确认登簿成功。",
							icon : "warning",
							ok : true
						});
						return;
					}
					var  HZ_OPINION_NAME = $("input[name='HZ_OPINION_NAME']").val();
					if(null==HZ_OPINION_NAME || ''==HZ_OPINION_NAME){						
						parent.art.dialog({
							content : "未保存核定意见，请先完成核定意见保存操作，并确认保存成功。",
							icon : "warning",
							ok : true
						});
						return;
					}
			 }
			//权利人信息
			getQlrInfoJson();
	     	 setGrBsjbr();//个人不显示经办人设置经办人的值
			 var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);	
			 if(submitMaterFileJson||submitMaterFileJson==""){
				 $("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
				 //先获取表单上的所有值
				 var formData = FlowUtil.getFormEleData("T_BDC_YGSPFYGDJ_FORM");
				 for(var index in formData){
					 flowSubmitObj[eval("index")] = formData[index];
				 }
				// console.dir(flowSubmitObj);	
				return flowSubmitObj;
			 }else{
				 return null;
			 }
	     }			 
	 }else{
		 return null;
	 }
}

//流程暂存方法
function FLOW_TempSaveFun(flowSubmitObj){
	//权利人信息
	getQlrInfoJson();
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",-1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_BDC_YGSPFYGDJ_FORM");
	for(var index in formData){
		flowSubmitObj[eval("index")] = formData[index];
	}				 
	return flowSubmitObj;
		 
}

//流程退回方法
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
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


//不动产登记证明打印与预览
function bdcYgspfygdjDJZMprint(typeId){
	// typeId: 1为证书预览  2为证书打印
	var title = "证书打印";
	var templateAlias = 'DJZM';
	if(typeId==1) {
		title = "证书预览";
	}
	var dataStr = "";
	dataStr +="&QLRZJH="+$("input[name='MSFZJHM']").val();
	dataStr +="&BDCQZH="+$("input[name='BDCDYH']").val();
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

/**
 * 预购预告登簿操作
 */
function bdcYgspfygdjBooking(){
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
								setBdcWwData(sqbData,sqrzbData);
							});   
						} else{				
							setBdcWwData(sqbData,sqrzbData);
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
function setBdcWwData(sqbData,sqrzbData){
	/*保存合同备案信息json*/
	var sqbDatas = [];
	sqbDatas.push(sqbData);
	$("input[name='HTBAXX_JSON']").val(JSON.stringify(sqbDatas));
	/*获取房屋单元信息*/
	$.post("bdcApplyController.do?fwdyxxcxDatagrid",{bdcdyh:sqbData.BDCDYH}, function (responseText, status, xhr) {
		$("input[name='FWXX_JSON']").val(JSON.stringify(responseText.rows));	
		$("input[name='YTMS']").val(responseText.rows[0].YTSM);
		$('[name="YTSM"]').val(responseText.rows[0].GHYT)
		$('[name="FWXZ"]').val(responseText.rows[0].FWXZ)
	})
	$("input[name='ZL']").val(sqbData.BDCZL);
	$("input[name='FJ']").val(sqbData.FJ);
	$("input[name='BDCDYH']").val(sqbData.BDCDYH);
	$("input[name='JZMJ']").val(sqbData.FWCQMJ);
	if(sqbData.TDYT){//用途说明						
		$("input[name='YTMS']").val(sqbData.TDYT);
		$('[name="YTSM"]').val(sqbData.TDYT);
	}
	$("input[name='FWDZ']").val(sqbData.BDCZL);
	$("input[name='CJJG']").val(sqbData.QDJG);
	$("input[name='BDC_BDCDYH']").val(sqbData.BDCDYH);//不动产单元号
	$("input[name='FWMMHTH']").val(sqbData.MMHTH);
	$("input[name='CJJG']").val(sqbData.MMSFCJJ/10000);
	
	//getBdcWwCl(BDC_WWSQBH);
	
	if (sqrzbData) {
		var qlrNum = 0;
		var MSFXM = "";
		var MSFZJHM = "";
		for (let i = 0; i < sqrzbData.length; i++) {
			
			if (sqrzbData[i].RYLX == '1') {
				MSFXM += sqrzbData[i].XM + ",";
				MSFZJHM += sqrzbData[i].ZJHM + ",";
				//$("input[name='MSFXM']").val(sqrzbData[i].XM);
				//var MSFZJLB = changeDocumentType(sqrzbData[i].ZJLX);
				//$("select[name='MSFZJLB']").find("option[value='"+MSFZJLB+"']").attr("selected",true);
				//$("input[name='MSFZJHM']").val(sqrzbData[i].ZJHM);
				setQlrInfoTowwsj(qlrNum,sqrzbData[i]);
				qlrNum++ ;
			}
			if (sqrzbData[i].RYLX == '2') {
				$("input[name='ZRFXM']").val(sqrzbData[i].XM);
				var ZRFZJLB = changeDocumentType(sqrzbData[i].ZJLX);
				$("select[name='ZRFZJLB']").find("option[value='"+ZRFZJLB+"']").attr("selected",true);
				$("input[name='ZRFZJHM']").val(sqrzbData[i].ZJHM);
			}
		}				
		 if(MSFXM){
					
			$('input[name="APPLICANT_UNIT"]').val(MSFXM.substring(0, MSFXM.length - 1));//申请人
			//申报对象信息回填
			/* $('input[name="SQRMC"]').val(MSFXM.substring(0, MSFXM.length - 1));//申请人				
			$('#SQRZJLX').val("SF");
			$('input[name="SQRSFZ"]').val(MSFZJHM.substring(0, MSFZJHM.length - 1));//证件号码	 */					
		}  
	}
	//AppUtil.verifyBdcdyhExist('BDCDYH',sqbData.BDCDYH);
	AppUtil.verifyAllBdcdyhExist(sqbData.BDCDYH);
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



var qlrInfoSn = 1;
function addQlrInfo() {
	qlrInfoSn = qlrInfoSn + 1;
	var table = document.getElementById("qlrInfo");
	var trContent = table.getElementsByTagName("tr")[0];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteQlrInfo('");
	if (qlrInfoSn > 10) {
		var replacestr = trHtml.substring(findex, findex + 19);
	} else {
		var replacestr = trHtml.substring(findex, findex + 18);
	}
	trHtml = trHtml.replace(replacestr, "deleteQlrInfo('" + qlrInfoSn + "')");
	trHtml = "<tr id=\"qlrInfo_" + qlrInfoSn + "\">" + trHtml + "</tr>";
	$("#qlrInfo").append(trHtml);
	$("#qlrInfo_" + qlrInfoSn + " [name='MSFZJLB']").val("身份证");
	$("#qlrInfo_" + qlrInfoSn + " [name='MSFXM']").val("");
	$("#qlrInfo_" + qlrInfoSn + " [name='MSFZJHM']").val("");
}

function deleteQlrInfo(idSn) {
	var table = document.getElementById("qlrInfo");
	if (table.rows.length == 1) {
		parent.art.dialog({
			content : "最少一个权利人信息！",
			icon : "warning",
			ok : true
		});
		return false;
	}
	$("#qlrInfo_" + idSn).remove();
}
function setQlrInfo(MSFXM,MSFZJHM){
	var sign = [',',';','|'];
	var names;
	var cards;
	for(var i = 0; i < sign.length; i++){
		if(MSFXM.indexOf(sign[i])!=-1){
			names = MSFXM.split(sign[i]);
		}
		if(MSFZJHM.indexOf(sign[i])!=-1){
			cards = MSFZJHM.split(sign[i]);
		}
	}
	var table = document.getElementById("qlrInfo");
	if(names){	
		for (var i = 0; i < names.length; i++) {
			if(table.rows.length>=(Number(i)+Number(1))){
				$("#qlrInfo").find('table').eq(i).find("[name='MSFXM']").val(names[i]);
				$("#qlrInfo").find('table').eq(i).find("[name='MSFZJHM']").val(cards[i]);
			} else{			
				addQlrInfo();
				$("#qlrInfo").find('table').eq(i).find("[name='MSFXM']").val(names[i]);
				$("#qlrInfo").find('table').eq(i).find("[name='MSFZJHM']").val(cards[i]);
			}
		}
	} else{		
		$("#qlrInfo").find('table').eq(0).find("[name='MSFXM']").val(MSFXM);
		$("#qlrInfo").find('table').eq(0).find("[name='MSFZJHM']").val(MSFZJHM);
	}
}
function setQlrInfoTowwsj(num,data){
	
	var table = document.getElementById("qlrInfo");
	if(table.rows.length>=(Number(num)+Number(1))){
		$("#qlrInfo").find('table').eq(num).find("[name='MSFXM']").val(data.XM);
		$("#qlrInfo").find('table').eq(num).find("[name='MSFZJHM']").val(data.ZJHM);
	} else{			
		addYwrInfo();
		$("#qlrInfo").find('table').eq(num).find("[name='MSFXM']").val(data.XM);
		$("#qlrInfo").find('table').eq(num).find("[name='MSFZJHM']").val(data.ZJHM);
	}
}
/**
 * 获取权利人JSON
 */
function getQlrInfoJson() {
	var datas = [];
	var table = document.getElementById("qlrInfo");
	for (var i = 0; i < table.rows.length; i++) {
		var trData = {};
		$("#qlrInfo").find('table').eq(i).find("*[name]").each(function() {
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
	$("input[type='hidden'][name='QLR_JSON']").val(JSON.stringify(datas));
}

function initAutoTable(flowSubmitObj) {
	var qlrJson = flowSubmitObj.busRecord.QLR_JSON;
	if (null != qlrJson && '' != qlrJson) {
		var qlrInfos = eval(qlrJson);
		for (var i = 0; i < qlrInfos.length; i++) {
			if (i == 0) {
				FlowUtil.initFormFieldValue(qlrInfos[i], "qlrInfo_1");
			} else {
				addQlrInfo();
				FlowUtil.initFormFieldValue(qlrInfos[i], "qlrInfo_" + (i + 1));
			}
		}
	}
	
}



//查看访问状态
function checkFwCqzt(dyh){
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
						var cqzt =Trim(list[0].CQZT.replace("已售"," "));
						if(cqzt){							
							result = {
								"isPass":true,
								"msg":"请注意，该不动产单元号为“"+cqzt+"”状态，是否继续办理业务?",
								"type":"0"
							};	
						} else{
							result = {
								"isPass":true,
								"type":"0"
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
	var val = $("[name='BDCDYH']").val();
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
							var cqzt =Trim(list[0].CQZT.replace("已售"," "));
							if(cqzt){										
								//询问是否可办理此登记					
								art.dialog.confirm("请注意，该不动产单元号为“"+cqzt+"”状态，是否继续办理业务?", function() {	
									//$("#isAuditPass").val("1");
									AppUtil.verifyAllBdcdyhExist(val);	
								})  
							} else {
								AppUtil.verifyAllBdcdyhExist(val);	
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
	}else{
		$("#isAuditPass").val("0");
	}
	return isPass;
}

//去除字符串的空串(前后空格)
function Trim(str){ 
  return str.replace(/(^\s*)|(\s*$)/g, ""); 
}