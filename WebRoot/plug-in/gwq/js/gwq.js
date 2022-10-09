
//函数功能的调用
// 设值声音大小
function GWQ_SetVolume(Value) {
	var ret = GWQ.GWQ_SetVolume(Value);
	if (ret == 0) {
		alert("成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}
}

// 设值屏幕亮度
function GWQ_SetBrightness(Value) {
	var ret = GWQ.GWQ_SetBrightness(Value);
	if (ret == 0) {
		alert("成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}

}

// 显示柜员信息牌
function GWQ_TellerInfo(TellerName, TellerNo, TellerPhoto) {
	if (TellerPhoto == "")
		TellerPhoto = "C:\\Program Files (x86)\\GWQOcx\\test.jpg";
	var ret = GWQ.GWQ_TellerInfo(TellerName, TellerNo, TellerPhoto);
	if (ret == 0) {
		alert("展示成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}
}

// 显示叫号牌信息
function GWQ_CallNumber(TellerName, TellerNo, TellerPhoto, number) {
	if (TellerPhoto == "")
		TellerPhoto = "C:\\Program Files (x86)\\GWQOcx\\test.jpg";
	var ret = GWQ.GWQ_CallNumber(TellerName, TellerNo, TellerPhoto, number);
	if (ret == 0)
		alert("叫号成功");
	else
		alert("失败，返回错误码为" + ret);
}

// 显示支付牌信息
function GWQ_PaymentInfo(TellerPhoto, Info) {
	if (TellerPhoto == "")
		TellerPhoto = "C:\\Program Files (x86)\\GWQOcx\\QR_Code.jpg";
	var ret = GWQ.GWQ_PaymentInfo(TellerPhoto, Info);
	if (ret == 0)
		alert("启动成功");
	else
		alert("失败，返回错误码为" + ret);
}

// 取消所有的操作
function GWQ_CancelOperate() {
	var ret = GWQ.GWQ_CancelOperate();
	if (ret == 0) {
		//alert("关闭成功");
	} else {
		//alert("失败，返回错误码为" + ret);
	}
}

// 启动信息交互
function DoGWQ_StartInfo(VoiceStr, Info) {
	var ret = GWQ.DoGWQ_StartInfo(VoiceStr, Info);
	if (ret == 0)
		alert("启动成功");
	else
		alert("失败，返回错误码为" + ret);
}

function OnAfterGWQ_StartInfo(ErrorCode) {
	if (ErrorCode == 0) {
		alert("信息交互成功");
	} else if (ErrorCode == -9) {
		alert("信息交互取消");
	} else {
		alert("失败，返回错误码为" + ErrorCode);
	}
}

// 启动评价器
function GWQ_StartEvaluator(TellerName, TellerNo, TellerPhoto) {
	var ret = GWQ.DoGWQ_StartEvaluator(TellerName, TellerNo, TellerPhoto);
	if (ret == 0)
		alert("启动成功");
	else
		alert("失败，返回错误码为" + ret);
}

function OnAfterGWQ_StartEvaluator(ErrorCode, EvaluatorLevel) {
	if (ErrorCode == 0) {
		if (EvaluatorLevel == 1)
			alert("非常满意");
		if (EvaluatorLevel == 2)
			alert("满意");
		if (EvaluatorLevel == 3)
			alert("一般");
		if (EvaluatorLevel == 4)
			alert("非常不满意");
	} else {
		alert("失败，返回错误码为" + ErrorCode);
	}
}

// 启动电子签证
function DoGWQ_StartSign(PDFPath, XmlPath, mylocation, VoiceStr, timeout) {
	if (PDFPath == "") {
		alert("未指定PDF文档！");
		return;
	}
	var ret = GWQ.DoGWQ_StartSign(PDFPath, XmlPath, mylocation, VoiceStr, timeout);
	if (ret == 0) {
		alert("电子签证DoGWQ_StartSign启动成功");
	} else {
		alert("DoGWQ_StartSign失败，返回错误码为" + ret);
	}
}

function DoGWQ_PDFConfirm(PDFPath, timeout) {
	if (PDFPath == "")
		PDFPath = "C:\\Program Files (x86)\\GWQOcx\\test.pdf";
	var ret = GWQ.DoGWQ_PDFConfirm(PDFPath, timeout);
	if (ret == 0)
		alert("启动成功");
	else
		alert("启动失败，返回错误码为" + ret);
}

function OnAfertGWQ_PDFConfirm(ErrorCode) {
	if (ErrorCode == 0) {
		alert("确认无误");
	} else if (ErrorCode == -9) {
		alert("有误");
	} else if (ErrorCode == -2) {
		alert("超时");
	} else {
		alert("确认失败，返回错误码为" + ErrorCode);
	}
}

// 展示文档
function GWQ_ShowPDF(PDFPath, timeout) {
	if (PDFPath == "")
		PDFPath = "C:\\Program Files (x86)\\GWQOcx\\test.pdf";
	var ret = GWQ.GWQ_ShowPDF(PDFPath, timeout);
	if (ret == 0) {
		alert("成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}
}

function OnAfterGWQ_StartSign(ErrorCode, SignPdfBase64, SignNameBase64, FingerPrintBase64, MixBase64) {
	if (ErrorCode == 0) {
		alert("OnAfterGWQ_StartSign电子签名成功");
	} else if (ErrorCode == -9) {
		alert("OnAfterGWQ_StartSign电子签名取消");
	} else {
		alert("OnAfterGWQ_StartSign失败，返回错误码为" + ErrorCode);
	}
}

function GWQ_FileUpload(FileName) {
	if (FileName == "")
		FileName = "C:\\Program Files (x86)\\GWQOcx\\ad.jpg";
	var ret = GWQ.GWQ_FileUpload(FileName);
	if (ret == 0) {
		alert("上传文件成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}

}

function GWQ_FileDelete(FileName) {
	var ret = GWQ.GWQ_FileDelete(FileName);
	if (ret == 0) {
		alert("删除成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}
}

function GWQ_GetFileList() {
	var ret = GWQ.GWQ_GetFileList();
	if (ret != "") {
		alert(ret);
	} else {
		alert("获取文件列表失败");
	}
}

function GWQ_Update() {
	var ret = GWQ.GWQ_Update();
	if (ret == 0) {
		alert("成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}
}

function DoGWQ_ReadID(name, sex, nation, birth, addr, id_num) {
	document.getElementById(name).value = "";
	document.getElementById(sex).value = "";
	document.getElementById(nation).value = "";
	document.getElementById(birth).value = "";
	document.getElementById(addr).value = "";
	document.getElementById(id_num).value = "";
	//alert(rrrr);
	//name.value="1111";
	//sex.value="";
	//nation.value="";
	//birth.value="";
	//addr.value="";
	//id_num.value="";
	var ret = GWQ.DoGWQ_ReadID();
	if (ret == 0) {
		alert("启动成功");
	} else {
		alert("失败，返回错误码为" + ret);
	}
}

function OnAfterGWQ_ReadID(ErrorCode, JsonData, name, sex, nation, birth, addr, id_num) {
	if (ErrorCode == 0) {
		var obj = JSON.parse(JsonData);
		document.getElementById(name).value = obj.name;
		document.getElementById(sex).value = obj.sex;
		document.getElementById(nation).value = obj.nation;
		document.getElementById(birth).value = obj.birth;
		document.getElementById(addr).value = obj.addr;
		document.getElementById(id_num).value = obj.id_num;
	//document.getElementById(HeadImg1).src=obj.base64_ID;
	//alert(obj.base64_ID);
	//alert(obj.name);
	} else if (ErrorCode == -9) {
		alert("取消");
	} else {
		alert("失败，返回错误码为" + ErrorCode);
	}
}

function DoGWQ_StartFace(name, sex, nation, birth, addr, id_num) {
	document.getElementById(name).value = "";
	document.getElementById(sex).value = "";
	document.getElementById(nation).value = "";
	document.getElementById(birth).value = "";
	document.getElementById(addr).value = "";
	document.getElementById(id_num).value = "";

	var ret = GWQ.DoGWQ_StartFace();
	if (ret == 0)
		alert("启动成功");
	else
		alert("失败，返回错误码为" + ret);
}

function OnAfterGWQ_StartFace(ErrorCode, JsonData, name, sex, nation, birth, addr, id_num) {
	if (ErrorCode == 0) {
		//alert(JsonData);
		var obj = JSON.parse(JsonData);
		document.getElementById(name).value = obj.name;
		document.getElementById(sex).value = obj.sex;
		document.getElementById(nation).value = obj.nation;
		document.getElementById(birth).value = obj.birth;
		document.getElementById(addr).value = obj.addr;
		document.getElementById(id_num).value = obj.id_num;
		if (obj.passFlag == true)
			alert("比对通过");
		else
			alert("比对失败");
	} else {
		alert("失败，返回错误码为" + ErrorCode);
	}
}

function DoGWQ_SignName(XmlPath, SignNamePath) {
	var ret = GWQ.DoGWQ_SignName(XmlPath, SignNamePath, "预留字段");
	if (ret == 0)
		alert("启动成功");
	else
		alert("失败，返回错误码为" + ret);
}

function OnAfterGWQ_SignName(ErrorCode) {
	if (ErrorCode == 0) {
		alert("成功");
	} else if (ErrorCode == -9) {
		alert("取消");
	} else {
		alert("失败，返回错误码为" + ErrorCode);
	}
}

function GWQ_GetVer(version) {
	document.getElementById(version).value = "";
	var ret = GWQ.GWQ_GetVer();
	if (ret != "") {
		document.getElementById(version).value = ret;
	} else {
		alert("获取版本号失败");
	}

}

// 启动表单交互
function DoGWQ_StartFormInfo(formHtmlName, timeOut) {
	var ret = GWQ.DoGWQ_StartFormInfo(formHtmlName, timeOut);
	startPlatError(ret, formHtmlName, timeOut);
	if (0 == ret) {
		parent.art.dialog({
			content : "询问记录启动成功，请等待客户在签字版填写内容...",
			icon : "succeed",
			time : 7,
			ok : true
		});
		AppUtil.closeLayer();
	}
	// 设备当前处于打开状态
	if(-2 == ret) {
		if(window.confirm("签字版当前处于开启页面状态，是否关闭签字版当前页面？")) {
			var closeResult = GWQ.GWQ_CancelOperate();
			if(closeResult == 0) {
				GWQ.DoGWQ_StartFormInfo(formHtmlName, timeOut);
			}
		}
	}
}

// 开启设备异常
function startPlatError(error, formHtmlName, timeOut) {
	if(error == -3) {
		dialogMsg("签字板启动失败，请重启签字版设备，若异常依旧存在，请联系管理员！", 7);
	}
	if(error == -6) {
		dialogMsg("签字板启动失败，请联系管理员！【错误代码：-6】", 7);
	}
	if(error == -7) {
		if(window.confirm("签字版当前处于开启页面状态，是否关闭签字版当前页面？")) {
			var closeResult = GWQ.GWQ_CancelOperate();
			if(closeResult == 0) {
				GWQ.DoGWQ_StartFormInfo(formHtmlName, timeOut);
			}
		}
		//dialogMsg("签字板启动失败，请联系管理员！【错误代码：-7】", 7);
	}
	if(error == -8) {
		dialogMsg("签字板启动失败，请联系管理员！【错误代码：-8】", 7);
	}
	if(error == -10) {
		dialogMsg("签字板启动失败，请联系管理员！【错误代码：-10】", 7);
	}
	if(error == -9) {
		dialogMsg("客户在签字版点击了【取消】按钮！", 7);
	}
}

// dialog提示框
function dialogMsg(msg, timeOut) {
	parent.art.dialog({
		content : msg,
		icon : "warning",
		time : timeOut,
		ok : true
	});
	AppUtil.closeLayer();
}

