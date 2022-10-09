/*
* 校验权属来源和权利人信息字段
* id : 表单id
* reqiredStr : class中带有的必填标识 required
*
* */
function validatePowerParams(id,requireStr) {
	var flag = true
	$("#" + id + " input , #" + id + " select").each(function (index) {
		if ($(this).attr("class") && $(this).attr("class").indexOf(requireStr) != -1) {
			var val = $(this).val();
			var param = $(this).parent().prev().text();
			if (!(val && val.trim() != '')) {
				art.dialog({
					content : "请填写" + param.replace("*","").replace(":","").replace("：",""),
					icon : "warning",
					ok: true
				})
				flag = false;
				return false;
			}
		}
	});
	return flag;
}

function formatDic(name,value) {
	$("select[name='"+name+"']").find("option").each(function () {
		if ($(this).text() == value) {
			$(this).attr("selected", "selected");
		}
	});
}

function notPrint() {
	art.dialog({
		content : "当前环节不能执行该操作",
		icon : "warning",
		ok : true
	});
}

/**返回时间格式YYYY-MM-DD*/
function DateConvertFun(str){
	var time = "";
	if(str){
		time=str;
	}
	return time;
}

/*隐藏登记信息*/
function hideDjxx(name) {
	$("#djjfxx_" + name).attr("style","display:none;");
	$("#djfzxx_" + name).attr("style","display:none;");
	$("#djgdxx_" + name).attr("style","display:none;");
}

/*审批表无法打印*/
function notPrintSpb() {
	$("#spbdf").attr("onclick","notPrint();")
	$("#spbsf").attr("onclick","notPrint();")
}

/**
 * 常用意见选择器
 *  businessName 类型名称
 *  inputName  回填字典名
 *  tagName  标签  input  textarea
 */
function bdcCyyjmbSelector(businessName,inputName,tagName) {
	parent.$.dialog.open("bdcDyqscdjController.do?cyyjmbSelector&businessName=" + businessName, {
		title: "常用意见选择器",
		width: "800px",
		lock: true,
		resize: false,
		height: "500px",
		close: function () {
			var selectCyyjmbInfo = art.dialog.data("selectCyyjmbInfo");
			if (selectCyyjmbInfo && selectCyyjmbInfo.opnionContent) {
				$(tagName + "[name='" + inputName + "']").val(selectCyyjmbInfo.opnionContent);
				art.dialog.removeData("selectCyyjmbInfo");
			}
		}
	}, false);
}

/*
* 校验表单上无法在class添加的字段，主要为easyui字段,
* fieldArr  [{"fieldText":"抵押权人","fieldName","DY_DYQR"}]
* */
function verificaSpeField(fieldArr) {
	var obj = {};
	obj["flag"] = true;
	for (let i = 0; i < fieldArr.length; i++) {
		var val = $("input[name='"+fieldArr[i].fieldName+"']").val();
		if (!(val && val != "")) {
			obj["flag"] = false;
			obj["msg"] = "请填写" + fieldArr[i].fieldText
			break;
		}
	}
	return obj;
}

/*登簿后按钮无法点击*/
function disabledDbBtn(id) {
	var bdcDbzt = $("input[name='BDC_DBZT']").val();
	if (bdcDbzt == '1') {
		$("#" + id).attr("disabled", "disabled").addClass("eflowbutton-disabled").attr("style", "pointer-events: none;");
	}else{
		$("#" + id).attr("disabled", false).removeClass("eflowbutton-disabled");
	}
}

/*表单是否允许修改（设置disabled）
* formId  表单ID
* flag    true：disabled true  false：disabled false
* */
function isDisabledForm(formId,flag) {
	$("#" + formId + " input , " + "#" + formId + " select , " + "#" + formId + " textarea").each(function () {
		$(this).attr("disabled", flag);
	});
}

/*
* fieldNameArr:数组，需要被disabled的字段的name
* flag:true：disabled true  false：disabled false
* */
function isDisabledFields(fieldNameArr,flag) {
	for (let i = 0; i < fieldNameArr.length; i++) {
		$("[name='" + fieldNameArr[i] + "']").attr("disabled", flag);
	}
}

/*easyui表单初始化*/
function initEasyUiForm() {
	$("#ZD_TDYT").combobox({
		url:'bdcGyjsjfwzydjController/loadTdytData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		prompt:'请选择土地用途',
		panelHeight:'200',
		multiple:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
		},
		onLoadSuccess: function (row) {  //下拉框数据加载成功调用
			var opts = $(this).combobox('options');
			var target = this;
			var values = $(target).combobox('getValues');//获取选中的值的values
			$.map(values, function (value) {
				var el = opts.finder.getEl(target, value);
				el.find('input.combobox-checkbox')._propAttr('checked', true);
			})
		},
		onSelect: function (row) { //选中一个选项时调用
			var opts = $(this).combobox('options');
			//设置选中值所对应的复选框为选中状态
			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', true);
			//获取选中的值的values
			// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
			$("#ZD_YTSM").val($(this).combobox('getText'))
		},
		onUnselect: function (row) {
			//不选中一个选项时调用
			var opts = $(this).combobox('options');
			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', false);
			//获取选中的值的values
			// $("#ZD_YTSM").val($(this).combobox('getValues').join(","))
			$("#ZD_YTSM").val($(this).combobox('getText'))
		}
	});

	$("#ZDZL_XIAN").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlqxData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onLoadSuccess:function(row) {
			$('#ZDZL_XIAN').combobox('setValue','351001');
		},
		onSelect:function (row) {
			$('#ZDZL_ZHENG').combobox('clear');
			$('#ZDZL_CUN').combobox('clear');
			if (row.VALUE) {
				var url = 'bdcGyjsjfwzydjController/loadZdzlzData.do?value='+row.VALUE;
				$('#ZDZL_ZHENG').combobox('reload',url);
			}
		}
	});

	$("#ZDZL_ZHENG").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlzData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onSelect:function (row) {
			$('#ZDZL_CUN').combobox('clear');
			if (row.VALUE) {
				var url = 'bdcGyjsjfwzydjController/loadZdzlxcData.do?value='+row.VALUE;
				$('#ZDZL_CUN').combobox('reload',url);
			}
		}
	});

	$("#ZDZL_CUN").combobox({
		url:'bdcGyjsjfwzydjController/loadZdzlxcData.do',
		method:'post',
		valueField:'VALUE',
		textField:'TEXT',
		panelHeight: '200',
		editable: false,
		required:true,
		formatter: function (row) {
			var opts = $(this).combobox('options');
			return row[opts.textField]
		},
		onSelect:function (row) {

		}
	});

	$("#SSDJ_FWXX_PQMC_S").combobox({
		valueField: 'DIC_CODE',
		textField: 'DIC_NAME',
		editable:true,
		hasDownArrow:true,
		url: "dictionaryController.do?load&typeCode=SSDJFWSZPQ",
		required:false,
		panelHeight: '200',
		filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		onSelect:function (row) {
			$("#SSDJ_FWXX_PQDM").val(row.DIC_CODE);
			$("#SSDJ_FWXX_PQMC").val(row.DIC_NAME);
		}
	});
}

/*删除材料子项信息*/
function delChildItem() {
	$("#xhTitle").hide();
	$("#sxzxTitle").hide();
	$(".materIndex").each(function () {
		$(this).hide();
	});
	$("td[name='busClass']").each(function () {
		$(this).hide();
	});
	$("#xhTitleCheck").hide();
	$("#sxzxTitleCheck").hide();
	$(".materCheckIndex").each(function () {
		$(this).hide();
	});
	$("td[name='busClassCheck']").each(function () {
		$(this).hide();
	});
}

/*回填easyui通用字段*/
function fillInEasyUiForm(flowSubmitObj) {
	if (flowSubmitObj.busRecord.ZD_TDYT) {
		$("#ZD_TDYT").combobox("setValues", flowSubmitObj.busRecord.ZD_TDYT.split(","));
	}
	if (flowSubmitObj.busRecord.ZDZL_XIAN) {
		$("#ZDZL_XIAN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_XIAN);
	}
	if (flowSubmitObj.busRecord.ZDZL_ZHENG) {
		$("#ZDZL_ZHENG").combobox("setValue", flowSubmitObj.busRecord.ZDZL_ZHENG);
	}
	if (flowSubmitObj.busRecord.ZDZL_CUN) {
		$("#ZDZL_CUN").combobox("setValue", flowSubmitObj.busRecord.ZDZL_CUN);
	}
}

/*
* 设置证件类型选择器校验规则
* zjlx:证件类型
* inputName：name
* */
function bdcSetValidate(zjlx,inputName) {
	if(zjlx=="SF"){
		$("input[name='" + inputName + "']").addClass(",custom[vidcard]");
	}else{
		$("input[name='" + inputName + "']").removeClass(",custom[vidcard]");
	}
}