/*
 * 描述  数据库字段类型为JSON的多行表格操作
 * 功能：增,删, 改
 * @author Reuben Bao
 * @version 1.0
 * @created 2019年10月13日
 */

/*************JS字段初始化开始***************/

// JSON对应的数据库字段
var JzwqfJsonName = "JZWQF_JSON";
// 表格顶层ID
var JzwqfHeadTableId = "JzwqfInfo";
// 填写明细字段的二级表格ID
var JzwqfSecondTableId = "JzwqfInfo_1";
// 列表表格ID
var JzwqfListTableId = "JzwqfListTable";
// 列表每行tr对应的ID
var JzwqfListItemTrId = "JzwqfListTableTr";
// 列表每行对应的JSON字符串值ID
var JzwqfListItemJsonId = "JzwqfListTableTrMx";
// 列表展示的字段数组
var JzwqfListFiledArr = [ "JZWQF_BDCDYH", "JZWQF_ZH", "JZWQF_HH", "JZWQF_JZMJ", "JZWQF_ZYJZMJ" ];
// 用于更新的id名称
var JzwqfUpdateId = "JzwqfInfoTrid";
// 用于更新的id值
var JzwqfUpdateIdKey = "";
// 列表索引
var JzwqfListIndex = 1;

/*************JS字段初始化结束***************/

/*************JS需要自行修改的代码开始***************/
/* 保存验证
 * tableName：需要保存的表格ID
 */
function JzwqfValid(tableName) {
	return true;
}
/*************JS需要自行修改的代码结束***************/

/*************JS需要公共代码开始***************/
// 页面初始化JSON数据
function initJzwqfTable(flowSubmitObj) {
	// 初始化权力人明细表格
	initJzwqfListTable(flowSubmitObj);
}

// 初始化权属来源表格
function initJzwqfListTable(flowSubmitObj) {
	var JzwqfJson = flowSubmitObj.busRecord[JzwqfJsonName];
	if (JzwqfJson) {
		var JzwqfJsons = eval(JzwqfJson);
		for (var i = 0; i < JzwqfJsons.length; i++) {
			// 追加发证明细行数据
			addJzwqfTableItemHtml(JzwqfHeadTableId, JzwqfListTableId, JzwqfJsons[i], JzwqfListFiledArr, JzwqfListItemTrId, JzwqfListItemJsonId)
		}
		// 默认展示第一条发证明细数据
		loadJzwqfTableTr($("#" + JzwqfListTableId + " tr")[1], JzwqfHeadTableId);
	}
}

/* 权力人明细查看
 * item：行数据
 * tableName：需要保存的表ID
 */
function loadJzwqfTableTr(item, tableName) {
	// 清空字段填写的表格
	clearJzwqfInfoTable(JzwqfSecondTableId);
	var tableItemJsonStr = $(item).closest("tr").find("[name='" + JzwqfListItemJsonId + "']").val();
	var tableItemJson = JSON.parse(tableItemJsonStr);
	FlowUtil.initTableValue(tableItemJson, JzwqfSecondTableId);
	$("#" + JzwqfUpdateId).val($(item).closest("tr").attr("id"));
	var classTrId = "#" + JzwqfSecondTableId + " .bdcdydacxTr";
	$(classTrId).removeClass("bdcdydacxTrRed");
	$(item).closest("tr").addClass("bdcdydacxTrRed");
}

/* 新增
 * tableName：需要保存的表格ID
 */
function addJzwqfTable(tableName) {
	// 保存验证
	if (!JzwqfValid(tableName)) {
		return;
	}
	var infos = {};
	// 获取填写的表格数据
	getJzwqfTableJson(infos, tableName);
	// 追加列表表格
	addJzwqfTableItemHtml(JzwqfHeadTableId, JzwqfListTableId, infos, JzwqfListFiledArr, JzwqfListItemTrId, JzwqfListItemJsonId);
	// 获取对应的JSON
	setJzwqfJsonName(JzwqfJsonName, tableName, JzwqfListTableId, JzwqfListItemTrId, JzwqfListItemJsonId);
	// 清空填写表格
	clearJzwqfInfoTable(tableName);
	// 清除列表选中样式
	$("#" + JzwqfHeadTableId + " .bdcdydacxTr").removeClass("bdcdydacxTrRed");
	setMsg("操作成功！");
}

/* 删除列表行数据
 * tableName：需要保存的表格ID
 * item：行对象
 */
function delJzwqfTableTr(item, tableName) {
	// 删除单前行
	$(item).closest("tr").remove();
	// 获取对应的JSON
	setJzwqfJsonName(JzwqfJsonName, tableName, JzwqfListTableId, JzwqfListItemTrId, JzwqfListItemJsonId);
}

/* 新增或保存列表行数据
 * tableName：需要保存的表格ID
 * item：行对象
 */
function updateJzwqfTable(tableName) {
	// 保存验证
	if (!JzwqfValid(tableName)) {
		return;
	}
	var JzwqfUpdateIdValue = $("#" + JzwqfUpdateId).val();
	// JzwqfUpdateId值为空说明为新增情况
	if (!JzwqfUpdateIdValue) {
		// 新增缴费数据
		addJzwqfTable(tableName);
	} else {
		// 设值更新行ID
		JzwqfUpdateIdKey = JzwqfUpdateIdValue;
		// 为修改情况
		var infos = {};
		// 获取缴费填写表格数据
		getJzwqfTableJson(infos, tableName);
		// 更新数据
		updateJzwqListItem(infos, JzwqfUpdateIdValue, tableName);
		// 获取对应的JSON
		setJzwqfJsonName(JzwqfJsonName, tableName, JzwqfListTableId, JzwqfListItemTrId, JzwqfListItemJsonId);
	}
	setMsg("操作成功！");
}

/* 更新表格数据
 * infos：当前填写表格的json格式数据
 * JzwqfUpdateIdValue：需要更新的ID
 * tableName：需要保存的表格ID
 */
function updateJzwqListItem(infos, JzwqfUpdateIdValue, tableName) {
	if (infos != "" && null != infos) {
		// 获取修改后的html代码
		var html = getJzwqfListTableHtml(JzwqfHeadTableId, JzwqfListTableId, infos, JzwqfListFiledArr, JzwqfListItemTrId, JzwqfListItemJsonId, "update");
		if (JzwqfUpdateId) {
			// 上级ID
			var prevTrid = $("#" + JzwqfUpdateIdValue).prev("tr").attr("id");
			$("#" + JzwqfUpdateIdValue).remove();
			if (prevTrid) {
				$("#" + prevTrid).after(html)
			} else {
				$("#" + JzwqfListTableId).append(html);
			}
			$("#" + JzwqfUpdateIdValue + " input[name='" + JzwqfListItemJsonId + "']").val(JSON.stringify(infos));
			var tableTrClass = "#" + JzwqfHeadTableId + " .bdcdydacxTr";
			$(tableTrClass).removeClass("bdcdydacxTrRed");
			$("#" + JzwqfUpdateIdValue).closest("tr").addClass("bdcdydacxTrRed");
		}
	}
}

/* 获取表格填写的数据
 * itemInfos：设值对象
 * infoId：表格ID
 */
function getJzwqfTableJson(itemInfos, infoId) {
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
function clearJzwqfInfoTable(infoId) {
	$("#" + infoId + " input[type='text']").val('');
	$("#" + infoId + " select").val('');
}

/* 设值对应的JSON数据库字段值
 * jsonName：需要保存的数据
 * tableName：需要保存的表格ID
 * listTableName：列表表格ID
 * indexName：表格每行的Id前缀，如：表格行ID为PowerPeopleTableTr_1则indexName=PowerPeopleTableTr,indexNum=1
 * indexItemName：表格每行数据明细ID
 */
function setJzwqfJsonName(jsonName, tableName, listTableName, indexName, indexItemName) {
	var datas = [];
	var listTableTrId = "#" + listTableName + " tr";
	$(listTableTrId).each(function(i) {
		var jsonStr = $(this).find("[name='" + indexItemName + "']").val();
		if ('' != jsonStr && null != jsonStr) {
			datas.push(JSON.parse(jsonStr));
		}
	});
	// 明细表没有数据
	if ($(listTableTrId).length < 2) {
		// 用户没有点击新建做数据保存直接点击流程提交或暂存
		var tableJson = {};
		getJzwqfTableJson(tableJson, tableName);
		if ('' != tableJson && null != tableJson) {
			datas.push(tableJson);
		}
	}
	$("input[type='hidden'][name='" + jsonName + "']").val(JSON.stringify(datas));
}

/* 追加表格行数据公共方法
 * tableName：需要保存的表格ID
 * ListTableIdName：表格ID名称
 * json：追加的json格式数据
 * tableArr：表格展示的字段数组（初始化得到）
 * indexName：表格每行的Id前缀，如：表格行ID为PowerPeopleTableTr_1则indexName=PowerPeopleTableTr,indexNum=1
 * indexItemName：表格每行数据明细ID
 */
function addJzwqfTableItemHtml(tableName, ListTableIdName, json, tableArr, indexName, indexItemName) {
	if (json != "" && null != json && tableArr != null && tableArr.length > 0) {
		var html = getJzwqfListTableHtml(tableName, ListTableIdName, json, tableArr, indexName, indexItemName, "save");
		$("#" + ListTableIdName).append(html);
		$("#" + indexName + "_" + JzwqfListIndex + " input[name='" + indexItemName + "']").val(JSON.stringify(json));
		JzwqfListIndex++;
	}
}

/* 列表追加Html字符串
 * tableName：需要保存的表格ID
 * ListTableIdName：表格ID名称
 * json：追加的json格式数据
 * tableArr：表格展示的字段数组（初始化得到）
 * indexName：表格每行的Id前缀，如：表格行ID为PowerPeopleTableTr_1则indexName=PowerPeopleTableTr,indexNum=1
 * indexItemName：表格每行数据明细ID
 * saveOrUpdate：更新或保存
 * return：html字符串
 */
function getJzwqfListTableHtml(tableName, ListTableIdName, json, tableArr, indexName, indexItemName, saveOrUpdate) {
	var trIdName = saveOrUpdate == "save" ? indexName + "_" + JzwqfListIndex : JzwqfUpdateIdKey;
	var loadMethod = "loadJzwqfTableTr(this, '" + tableName + "');";
	var delMethod = "delJzwqfTableTr(this, '" + tableName + "');";
	var html = "";
	html += '<tr class="bdcdydacxTr" style="height: 38px;" id="' + trIdName + '">';
	html += '<input type="hidden" name="' + indexItemName + '"/>';
	// html += '<td style="text-align: center;">' + indexNum + '</td>';
	for (var i = 0; i < tableArr.length; i++) {
		html += '<td style="text-align: center;">' + json[tableArr[i]] + '</td>';
	}
	html += '<td style="text-align: center;">';
	html += '<a href="javascript:void(0);" style="padding: 0 10px; margin-top: 2px; margin-left: 20%;" class="eflowbutton" onclick="' + loadMethod + '" id="' + indexName + 'Item">查 看</a>';
	html += '<a href="javascript:void(0);" onclick="delJzwqfTableTr(this);" style="float: right;" class="syj-closebtn"></a></td>';
	html += '</tr>';
	return html;
}

// 消息提示
function setMsg(msg) {
	art.dialog({
		content : msg,
		icon : "succeed",
		ok : true
	});
}
/*************JS需要公共代码结束***************/