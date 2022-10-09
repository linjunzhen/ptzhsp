// 初始化表单数据
var bdcDjfzxxTr = 1;
var bdcDjjfxxTr = 1;
function initDjxxTable(flowSubmitObj) {
	// 初始化登记缴费表格
	initDjjfxxTable(flowSubmitObj);
	// 初始化登记发证表格
	initDjfzxxTable(flowSubmitObj);
}

// 初始化登记缴费表格
function initDjjfxxTable(flowSubmitObj) {
	if (flowSubmitObj.busRecord) {
		var djjfxxJson = flowSubmitObj.busRecord.DJJFXX_JSON;
		if (djjfxxJson && djjfxxJson != '[]' && djjfxxJson != '[{}]') {
			var djjfxxInfos = eval(djjfxxJson);
			for (var i = 0; i < djjfxxInfos.length; i++) {
				// 追加发证明细行数据
				addDjjfxxItemHtml(djjfxxInfos[i]);
			}
			// 默认展示第一条发证明细数据
			loadBdcdjjfxx($("#djjfxxTable tr")[1]);
		}
	}
}

// 初始化登记发证表格
function initDjfzxxTable(flowSubmitObj) {
	if (flowSubmitObj.busRecord) {
		var djxxJson = flowSubmitObj.busRecord.DJFZXX_JSON;
		if (djxxJson && djxxJson != '[]' && djxxJson != '[{}]') {
			var djxxInfos = eval(djxxJson);
			for (var i = 0; i < djxxInfos.length; i++) {
				// 追加发证明细行数据
				addDjfzxxItemHtml(djxxInfos[i]);
			}
			// 默认展示第一条发证明细数据
			loadBdcdjfzxx($("#djfzxxTable tr")[1]);
		}
	}
}

// 缴费明细查看
function loadBdcdjjfxx(item) {
	// 清空填写表格
	clearDjxxInfoTable("djjfxxInfo_1");
	var djfjxxmx = $(item).closest("tr").find("[name='djjfxxmx']").val();
	var bdcdyxxInfos = JSON.parse(djfjxxmx);
	FlowUtil.initTableValue(bdcdyxxInfos, "djjfxxInfo_1");
	$("#djjfxxInfoTrid").val($(item).closest("tr").attr("id"));
	$("#djjfxxInfo_1 .bdcdydacxTr").removeClass("bdcdydacxTrRed");
	$(item).closest("tr").addClass("bdcdydacxTrRed");
}

// 发证明细查看
function loadBdcdjfzxx(item) {
	// 清空填写表格
	clearDjxxInfoTable("djfzxxInfos");
	var djfzxxmx = $(item).closest("tr").find("[name='djfzxxmx']").val();
	var bdcdyxxInfos = JSON.parse(djfzxxmx);
	FlowUtil.initTableValue(bdcdyxxInfos, "djfzxxInfo_1");
	$("#djfzxxInfoTrid").val($(item).closest("tr").attr("id"));
	$("#djfzxxTable .bdcdydacxTr").removeClass("bdcdydacxTrRed");
	$(item).closest("tr").addClass("bdcdydacxTrRed");
}

// 新增发证
function addDjfzxx() {
	var djfzxxInfos = {};
	// 获取发证填写表格数据
	getDjfzxxInfoTableValue(djfzxxInfos, "djfzxxInfo");
	// 追加发证明细行数据
	addDjfzxxItemHtml(djfzxxInfos);
	// 获取登记发证人JSON
	getDjfzxxJson();
	// 清空填写表格
	clearDjxxInfoTable("djfzxxInfos");
	// 清空发证签证数据
	$("#djfzxxImgId").val('');
}

// 新增缴费
function addDjjfxx() {
	// 登记缴费保存验证
	if(!validSaveDjjfxx()) {
		return;
	}
	var djjfxxInfos = {};
	// 获取缴费填写表格数据
	getDjfzxxInfoTableValue(djjfxxInfos, "djjfxxInfo");
	// 追加缴费明细行数据
	addDjjfxxItemHtml(djjfxxInfos);
	// 获取缴费JSON
	getDjjfxxJson();
	// 清空填写表格
	clearDjxxInfoTable("djjfxxInfo_1");
}

// 登记缴费保存验证
function validSaveDjjfxx() {
	if (!$("#DJJFMX_SFLX").val()) {
		alert("收费类型为必填！");
		return false;
	} else if (!$("#DJJFMX_YSJE").val()) {
		alert("应收金额为必填！");
		return false;
	} else {
		return true;
	}
}

// 新增或保存缴费明细数据
function updateDjjfxx() {
	// 登记缴费保存验证
	if(!validSaveDjjfxx()) {
		return;
	}
	var djjfxxInfoTrid = $("#djjfxxInfoTrid").val();
	// djjfxxInfoTrid值为空说明为新增情况
	if (!djjfxxInfoTrid) {
		// 新增缴费数据
		addDjjfxx();
	} else {
		// 为修改情况
		var djjfxxInfos = {};
		// 获取缴费填写表格数据
		getDjfzxxInfoTableValue(djjfxxInfos, "djjfxxInfo");
		// 更新数据
		updateDjjfxxmx(djjfxxInfos, djjfxxInfoTrid);
		// 获取缴费JSON
		getDjjfxxJson();
	}
}

// 更新缴费明细数据
function updateDjjfxxmx(djjfxxInfos, trid) {
	if (djjfxxInfos != "" && null != djjfxxInfos) {
		// 获取修改后的html代码
		var html = "";
		html += '<tr class="bdcdydacxTr" style="height: 38px;" id="'+ trid + '">';
		html += '<input type="hidden" name="djjfxxmx"/>';
		// html += '<td style="text-align: center;">' + trid + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFJS + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_YSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_ZHYSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFRY + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFRQ + '</td>';
		html += '<td style="text-align: center;">';
		html += '<a href="javascript:void(0);" style="padding: 0 10px; margin-top: 2px; margin-left: 20%;" class="eflowbutton" onclick="loadBdcdjjfxx(this);" id="djjfxxItem">查 看</a>';
		html += '<a href="javascript:void(0);" onclick="delDjjfxxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
		html += '</tr>';
		if (trid) {
			// 上级ID
			var prevTrid = $("#" + trid).prev("tr").attr("id");
			$("#" + trid).remove();
			if (prevTrid) {
				$("#" + prevTrid).after(html)
			} else {
				$("#djjfxxTable").append(html);
			}
			var djjfxxmxId = "#" + trid;
			$(djjfxxmxId + " input[name='djjfxxmx']").val(JSON.stringify(djjfxxInfos));
			$("#djfzxxTable .bdcdydacxTr").removeClass("bdcdydacxTrRed");
			$(djjfxxmxId).closest("tr").addClass("bdcdydacxTrRed");
		}
	}
}

// 获取发证填写表格数据
function getDjfzxxInfoTableValue(itemInfos, infoId) {
	var id = "#" + infoId;
	$(id).find('table').eq(0).find("*[name]").each(function() {
		var inputName = $(this).attr("name");
		var inputValue = $(this).val();
		//获取元素的类型
		var fieldType = $(this).attr("type");
		if (fieldType == "radio") {
			var isChecked = $(this).is(':checked');
			if (isChecked) {
				itemInfos[inputName] = inputValue;
			}
		} else if (fieldType == "checkbox") {
			var inputValues = FlowUtil.getCheckBoxValues(inputName);
			itemInfos[inputName] = inputValues;
		} else if (fieldType != "button") {
			itemInfos[inputName] = inputValue;
		}
	});
}

// 清空填写表格
function clearDjxxInfoTable(infoId) {
	$("#" + infoId + " input[type='text']").val('');
	$("#" + infoId + " select").val('');
}

// 删除发证行数据
function delDjfzxxTr(item) {
	// 删除单前行
	$(item).closest("tr").remove();
	// 获取DJFZXX_JSON
	getDjfzxxJson();
}

// 删除缴费行数据
function delDjjfxxTr(item) {
	// 删除单前行
	$(item).closest("tr").remove();
	// 获取DJJFXX_JSON
	getDjjfxxJson();
}

// 追加发证明细行数据
function addDjfzxxItemHtml(djfzxxInfos) {
	if (djfzxxInfos != "" && null != djfzxxInfos) {
		var html = "";
		html += '<tr class="bdcdydacxTr" id="bdcDjfzxxTr_' + bdcDjfzxxTr + '">';
		html += '<input type="hidden" name="djfzxxmx"/>';
		// html += '<td style="text-align: center;">' + bdcDjfzxxTr + '</td>';
		html += '<td style="text-align: center;">' + djfzxxInfos.DJFZXX_FZMC + '</td>';
		html += '<td style="text-align: center;">' + djfzxxInfos.DJFZXX_FZRY + '</td>';
		html += '<td style="text-align: center;">' + djfzxxInfos.DJFZXX_FZSL + '</td>';
		html += '<td style="text-align: center;">' + djfzxxInfos.DJFZXX_FZSJ + '</td>';
		html += '<td style="text-align: center;">' + djfzxxInfos.DJFZXX_LZRXM + '</td>';
		html += '<td style="text-align: center;">';
		html += '<a href="javascript:void(0);" style="padding: 4px 10px; margin-top: 2px;" class="eflowbutton" onclick="loadBdcdjfzxx(this);" id="djfzxxItem">查 看</a>';
		html += '<a href="javascript:void(0);" onclick="delDjfzxxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
		html += '</tr>';
		$("#djfzxxTable").append(html);
		$("#bdcDjfzxxTr_" + bdcDjfzxxTr + " input[name='djfzxxmx']").val(JSON.stringify(djfzxxInfos));
		bdcDjfzxxTr++;
	}
}

// 追加缴费明细行数据
function addDjjfxxItemHtml(djjfxxInfos) {
	if (djjfxxInfos != "" && null != djjfxxInfos) {
		var html = "";
		html += '<tr class="bdcdydacxTr" style="height: 38px;" id="bdcDjjfxxTr_' + bdcDjjfxxTr + '">';
		html += '<input type="hidden" name="djjfxxmx"/>';
		// html += '<td style="text-align: center;">' + bdcDjjfxxTr + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFJS + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_YSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_ZHYSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFRY + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFRQ + '</td>';
		html += '<td style="text-align: center;">';
		html += '<a href="javascript:void(0);" style="padding: 0 10px; margin-top: 2px; margin-left: 20%;" class="eflowbutton" onclick="loadBdcdjjfxx(this);" id="djjfxxItem">查 看</a>';
		html += '<a href="javascript:void(0);" onclick="delDjjfxxTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
		html += '</tr>';
		$("#djjfxxTable").append(html);
		$("#bdcDjjfxxTr_" + bdcDjjfxxTr + " input[name='djjfxxmx']").val(JSON.stringify(djjfxxInfos));
		bdcDjjfxxTr++;
	}
}

// 新增发证填写表格
var djxxInfoSn = 1;
function addDjxxInfoHtml() {
	djxxInfoSn = djxxInfoSn + 1;
	var table = document.getElementById("djfzxxInfo");
	var trContent = table.getElementsByTagName("tr")[0];
	var trHtml = trContent.innerHTML;
	trHtml = "<tr id=\"djfzxxInfo_" + djxxInfoSn + "\">" + trHtml + "</tr>";
	$("#djfzxxInfo").append(trHtml);
}

// 获取DJFZXX_JSON
function getDjfzxxJson() {
	var datas = [];
	$("#djfzxxTable tr").each(function(i) {
		var djfzxxmx = $(this).find("[name='djfzxxmx']").val();
		if ('' != djfzxxmx && null != djfzxxmx) {
			datas.push(JSON.parse(djfzxxmx));
		}
	});
	// 明细表没有数据
	if ($("#djfzxxTable tr").length < 2) {
		// 用户没有点击新建做数据保存直接点击流程提交或暂存
		var djfzxxTableValue = {};
		getDjfzxxInfoTableValue(djfzxxTableValue, "djfzxxInfo");
		if ('' != djfzxxTableValue && null != djfzxxTableValue) {
			datas.push(djfzxxTableValue);
		}
	}
	$("input[type='hidden'][name='DJFZXX_JSON']").val(JSON.stringify(datas));
}

// 获取DJJFXX_JSON
function getDjjfxxJson() {
	var datas = [];
	$("#djjfxxTable tr").each(function(i) {
		var djjfxxmx = $(this).find("[name='djjfxxmx']").val();
		if ('' != djjfxxmx && null != djjfxxmx) {
			datas.push(JSON.parse(djjfxxmx));
		}
	});
	// 明细表没有数据
	if ($("#djjfxxTable tr").length < 2) {
		// 用户没有点击新建做数据保存直接点击流程提交或暂存
		var djjfxxTableValue = {};
		getDjfzxxInfoTableValue(djjfxxTableValue, "djjfxxInfo");
		if ('' != djjfxxTableValue && null != djjfxxTableValue) {
			datas.push(djjfxxTableValue);
		}
	}
	$("input[type='hidden'][name='DJJFXX_JSON']").val(JSON.stringify(datas));
}

