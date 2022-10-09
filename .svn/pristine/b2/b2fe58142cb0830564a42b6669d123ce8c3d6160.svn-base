// 初始化表单数据
var PowerPeopleTableTr = 1;
var PowerSourceTableTr = 1;

// 权力人表格行ID标识 ,行对应的数据明细为：标识后+"mx"
var PowpeoleIndexName = "PowerPeopleTableTr";
var PowsourceIndexName = "PowerSourceTableTr";

// 权力人表格行对应的数据明细为：标识后+"mx"
var PowpeoleIndexItemName = "PowerPeopleTableTrMx";
var PowsourceIndexItemName = "PowerSourceTableTrMx";

// 初始化权力人表格字段数组
var PowerPeopleTableArr = [ "POWERPEOPLENAME", "POWERPEOPLEPRO", "POWERPEOPLENATURE", "POWERPEOPLEIDNUM", "POWERPEOPLESEX" ];

// 初始化权属来源表格字段数组
var PowerSourceTableArr = [ "POWERPEOPLENAME", "POWERPEOPLEPRO", "POWERPEOPLENATURE", "POWERPEOPLEIDNUM", "POWERPEOPLESEX" ];

function initPowerTable(flowSubmitObj) {
	// 初始化权力人明细表格
	//initPowerPeopleTable(flowSubmitObj);
	// 初始化权属来源表格
	initPowerSourceTable(flowSubmitObj);
}

// 初始化权力人明细表格
function initPowerPeopleTable(flowSubmitObj) {
	var POWERPEOPLEINFO_JSON = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
	if (null != POWERPEOPLEINFO_JSON && '' != POWERPEOPLEINFO_JSON) {
		var POWERPEOPLEINFO_JSONS = eval(POWERPEOPLEINFO_JSON);
		for (var i = 0; i < POWERPEOPLEINFO_JSONS.length; i++) {
			// 追加权力人明细行数据
			addPowerTableItemHtml("PowerPeopleTable", POWERPEOPLEINFO_JSONS[i], PowerPeopleTableArr, PowerPeopleTableTr, PowpeoleIndexName, PowpeoleIndexItemName);
		}
		// 默认展示第一条权力人明细数据
		loadPowerPeopleTableTr($("#PowerPeopleTable tr")[1], PowpeoleIndexName);
	}
}

// 初始化权属来源表格
function initPowerSourceTable(flowSubmitObj) {
	var POWERSOURCEINFO_JSON = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
	if (null != POWERSOURCEINFO_JSON &&POWERSOURCEINFO_JSON '' != djxxJson) {
		var POWERSOURCEINFO_JSONS = eval(POWERSOURCEINFO_JSON);
		for (var i = 0; i < POWERSOURCEINFO_JSONS.length; i++) {
			// 追加发证明细行数据
			addPowerTableItemHtml("PowerSourceTable", POWERSOURCEINFO_JSON[i], PowerSourceTableArr, PowerSourceTableTr, PowsourceIndexName, PowsourceIndexItemName)
		}
		// 默认展示第一条发证明细数据
		//loadBdcdjfzxx($("#PowerSourceTable tr")[1], PowsourceIndexName);
	}
}

/* 追加表格行数据公共方法
 * listTableIdName：表格ID名称
 * json：追加的json格式数据
 * tableArr：表格展示的字段数组（初始化得到）
 * indexNum：表格索引（初始化得到）
 * indexName：表格每行的Id前缀，如：表格行ID为PowerPeopleTableTr_1则indexName=PowerPeopleTableTr,indexNum=1
 * indexItemName：表格每行数据明细ID
 */
function addPowerTableItemHtml(listTableIdName, json, tableArr, indexNum, indexName, indexItemName) {
	if (json != "" && null != json && tableArr != null && tableArr.length > 0) {
		var trIdName = indexName + "_" + indexNum;
		var html = "";
		html += '<tr class="bdcdydacxTr" style="height: 38px;" id="' + trIdName + '">';
		html += '<input type="hidden" name="' + indexItemName + '"/>';
		// html += '<td style="text-align: center;">' + indexNum + '</td>';
		for (var i = 0; i < tableArr.length; i++) {
			html += '<td style="text-align: center;">' + json.tableArr[i] + '</td>';
		}
		html += '<td style="text-align: center;">';
		html += '<a href="javascript:void(0);" style="padding: 0 10px; margin-top: 2px; margin-left: 20%;" class="eflowbutton" onclick="loadTableTr(this, '+ indexItemName +');" id="' + indexName + 'Item">查 看</a>';
		html += '<a href="javascript:void(0);" onclick="del' + indexName + '(this);" style="float: right;" class="syj-closebtn"></a></td>';
		html += '</tr>';
		$("#" + listTableIdName).append(html);
		$("#" + trIdName + " input[name='" + indexItemName + "']").val(JSON.stringify(json));
		indexNum++;
	}
}

/* 权力人明细查看
 * item：行数据
 * indexName：PowerPeopleTableTr=权力人信息, PowerSourceTableTr=权属来源
 */
function loadTableTr(item, indexName) {
	// 判断权力人信息或权属来源
	var selectArr = [];
	var tableInfoIdName = "";
	var tableTrid = "";
	if("PowerPeopleTableTr" == indexName) {
		selectArr = ["POWERPEOPLENATURE", "POWERPEOPLESEX", "POWAGENTIDTYPE", "POWERPEOPLEIDTYPE", "POWAGENTIDTYPE"];
		tableInfoIdName = "PowerPeopleInfo_1";
		tableTrid = "PowerPeopleInfoTrid";
	}
	if("PowerSourceTableTr" == indexName) {
		selectArr = ["POWERSOURCENATURE"];
		tableInfoIdName = "PowerSourceInfo_1";
		tableTrid = "PowerSourceInfoTrid";
	}
	// 清空填写表格
	clearInfoTable(tableInfoIdName);
	var tableItemJsonStr = $(item).closest("tr").find("[name='" + PowpeoleIndexItemName + "']").val();
	FlowUtil.initFormFieldValue(JSON.parse(tableItemJsonStr), "powerPeopleInfo_1");
	var tableItemJson = JSON.parse(tableItemJsonStr);
	// 回填下拉框数据
	for(var i=0; i<selectArr.length; i++) {
		$('select[name="'+ selectArr[i] +'"]').find("option").each(function() {
			if ($(this).text() == powerPeopleTablemxs.selectArr[i]) {
				$(this).prop("selected", true);
			}
		});
	}
	$("#" + tableTrid).val($(item).closest("tr").attr("id"));
	var classTrId = "#" + tableInfoIdName + " .bdcdydacxTr";
	$(classTrId).removeClass("bdcdydacxTrRed");
	$(item).closest("tr").addClass("bdcdydacxTrRed");
}

function addTable2() {
	alert(1);
}

/* 新增
 * tableName：需要保存的表格ID
 * jsonName：表格对应的数据库JSON字段
 */
function addTable(tableName) {
	var infos = {};
	// 获取发证填写表格数据
	getTableJson(infos, tableName);
	// 获取登记发证人JSON
	if("PowerPeopleInfo" == tableName) {
		// 追加发证明细行数据
		addPowerTableItemHtml("PowerPeopleTable", infos, PowerPeopleTableArr, PowerPeopleTableTr, PowpeoleIndexName, PowpeoleIndexItemName);
		setJsonName("POWERPEOPLEINFO_JSON", tableName, "PowerPeopleTable", PowpeoleIndexName, PowpeoleIndexItemName);
	} else if("PowerSourceInfo" == tableName) {
		addPowerTableItemHtml("PowerSourceTable", infos, PowerSourceTableArr, PowerSourceTableTr, PowsourceIndexName, PowsourceIndexItemName);
		setJsonName("POWERSOURCEINFO_JSON", tableName, "PowerSourceTable", PowsourceIndexName, PowsourceIndexItemName);
	}
	// 清空填写表格
	clearInfoTable(tableName);
}

// 登记缴费保存验证
function validSaveDjjfxx() {
	if ($("#DJJFMX_SFLX").val() == '' || $("#DJJFMX_SFLX").val() == undefined) {
		alert("收费类型为必填！");
		return false;
	} else if ($("#DJJFMX_YSJE").val() == '' || $("#DJJFMX_YSJE").val() == undefined) {
		alert("应收金额为必填！");
		return false;
	} else {
		return true;
	}
}

// 新增或保存缴费明细数据
function updateDjjfxx() {
	// 登记缴费保存验证
	if (!validSaveDjjfxx()) {
		return;
	}
	var djjfxxInfoTrid = $("#djjfxxInfoTrid").val();
	// djjfxxInfoTrid值为空说明为新增情况
	if (djjfxxInfoTrid == '' || djjfxxInfoTrid == undefined) {
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
		html += '<tr class="bdcdydacxTr" style="height: 38px;" id="' + trid + '">';
		html += '<input type="hidden" name="djjfxxmx"/>';
		// html += '<td style="text-align: center;">' + trid + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFJS + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_YSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_ZHYSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SSJE + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFRY + '</td>';
		html += '<td style="text-align: center;">' + djjfxxInfos.DJJFMX_SFRQ + '</td>';
		html += '<td style="text-align: center;">';
		html += '<a href="javascript:void(0);" style="padding: 0 10px; margin-top: 2px; margin-left: 20%;" class="eflowbutton" onclick="loadPowerPeopleTableTr(this);" id="djjfxxItem">查 看</a>';
		html += '<a href="javascript:void(0);" onclick="delPowerPeopleTableTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
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

/* 获取表格填写的数据
 * itemInfos：设值对象
 * infoId：表格ID
 */
function getTableJson(itemInfos, infoId) {
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
function clearInfoTable(infoId) {
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

// 删除权力人信息行数据
function delPowerPeopleTableTr(item) {
	// 删除单前行
	$(item).closest("tr").remove();
	// 获取DJJFXX_JSON
	getDjjfxxJson();
}

// 获取DJFZXX_JSON
function setJsonName(jsonName, tableName, listTableName, indexName, indexItemName) {
	var datas = [];
	var listTableTrId = "#" + listTableName + " tr";
	$(listTableTrId).each(function(i) {
		var jsonStr = $(this).find("[name='"+ indexItemName +"']").val();
		if ('' != jsonStr && null != jsonStr) {
			datas.push(JSON.parse(jsonStr));
		}
	});
	// 明细表没有数据
	if ($(listTableTrId).length < 2) {
		// 用户没有点击新建做数据保存直接点击流程提交或暂存
		var tableJson = {};
		getTableJson(tableJson, tableName);
		if ('' != tableJson && null != tableJson) {
			datas.push(tableJson);
		}
	}
	$("input[type='hidden'][name='"+ jsonName +"']").val(JSON.stringify(datas));
}
