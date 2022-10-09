/*
 * 描述  权力人信息、权属来源多行处理公共JS
 * @author Reuben Bao
 * @version 1.0
 * @created 2019年10月13日
 */

/*************JS字段初始化开始***************/

// JSON对应的数据库字段
var PowJsonName = "";
// 表格顶层ID
var HeadTableId = "";
// 填写明细字段的二级表格ID
var SecondTableId = "";
// 列表表格ID
var ListTableId = "";
// 列表每行tr对应的ID
var ListItemTrId = "";
// 列表每行对应的JSON字符串值ID
var ListItemJsonId = "";
// 列表展示的字段数组
var ListFiledArr = [];
// 用于更新的id名称
var updateId = "";
// 用于更新的id值
var updateIdKey = "";
// 列表索引
var ListIndex = 1;
// 列表序号
var itemCode = 0;

// 根据表单ID修改对应的字段
// 初始化表单数据
var PowerPeopleTableTr = 1;
var PowerSourceTableTr = 1;
// 权力人表格行ID标识 ,行对应的数据明细为：标识后+"mx"
var PowpeoleIndexName = "PowerPeopleTableTr";
var PowsourceIndexName = "PowerSourceTableTr";
// 权力人表格行对应的数据明细为：标识后+"mx"
var PowpeoleIndexItemName = "PowerPeopleTableTrMx";
var PowsourceIndexItemName = "PowerSourceTableTrMx";
// 填写明细字段的二级表格ID
var PowerPeopleSecondTableId = "PowerPeopleInfo_1";
var PowerSourceSecondTableId = "PowerSourceInfo_1";
// JSON对应的数据库字段
var PowerPeopleJsonField = "POWERPEOPLEINFO_JSON";
var PowerSourceJsonField = "POWERSOURCEINFO_JSON";
// 需要保存的表格ID
var PowerPeopleTableId = "PowerPeopleInfo";
var PowerSourceTableId = "PowerSourceInfo";
// 列表表格ID
var PowerPeopleListTableId = "PowerPeopleTable";
var PowerSourceListTableId = "PowerSourceTable";
// 用于更新列表的行ID
var PowerPeopleUpdateId = "PowerPeopleInfoTrid";
var PowerSourceUpdateId = "PowerSourceInfoTrid";
// 初始化权力人表格字段数组
var PowerPeopleTableArr = [ "POWERPEOPLENAME", "POWERPEOPLEIDNUM", "POWERPEOPLESEX" ];
// 初始化权属来源表格字段数组
var PowerSourceTableArray = ["QLR", "BDC_QLRZJHM", "POWERSOURCENATURE", "POWERSOURCEIDNUM", "POWERSOURCEIDTYP"];
/*************JS字段初始化结束***************/

/*************JS需要自行修改的代码开始***************/
/* 保存验证
 * tableName：需要保存的表格ID
 */
function valid(tableName) {
	var msg = "";
	// 权力人表格
	if(PowerPeopleTableId == tableName) {
		if (!$("input[name='POWERPEOPLENAME']").val()) {
			msg = "权力人姓名不能为空！";
		} else if (!$("#POWERPEOPLEIDTYPE").val()) {
			msg = "权力人证件类型不能为空！";
		} else if (!$("#POWERPEOPLEIDNUM").val()) {
			msg = "权力人证件号码不能为空！";
		}
	} else {
		if (!$("#POWERSOURCENATURE").val()) {
			msg = "权属来源类型不能为空！";
		} else if (!$("input[name='POWERSOURCEIDNUM']").val()) {
			msg = "权属文号不能为空！";
		}
	}
	if(msg != ""){
		parent.art.dialog({
			content : msg,
			icon : "warning",
			ok : true
		});
		msg = "";
		return false;
	} else {
		return true;
	}
}
/*************JS需要自行修改的代码结束***************/


/*************JS需要公共代码开始***************/
// 页面初始化JSON数据
function initPowerTable(flowSubmitObj) {
	// 初始化权力人明细表格
	initPowerPeopleTable(flowSubmitObj);
	// 初始化权属来源表格
	initPowerSourceTable(flowSubmitObj);
	//alert(PowerSourceTableArray);
	
	var djfzxx_json = flowSubmitObj.busRecord.DJFZXX_JSON;
	if(null != djfzxx_json && '' != djfzxx_json){
		var djfzxx_jsonItems = JSON.parse(djfzxx_json);
		initDjfzxx_jtjsscdj(djfzxx_jsonItems);
	}
	var djjfmx_json = flowSubmitObj.busRecord.DJJFMX_JSON;
	if(null != djjfmx_json && '' != djjfmx_json){
		var djjfmx_jsonItems = JSON.parse(djjfmx_json);
		initDjjfxx_jtjsscdj(djjfmx_jsonItems);
	}
}

//初始化权力人明细表格
function initPowerPeopleTable(flowSubmitObj) {
	var POWERPEOPLEINFO_JSON = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON;
	if (null != POWERPEOPLEINFO_JSON && '' != POWERPEOPLEINFO_JSON) {
		var POWERPEOPLEINFO_JSONS = eval(POWERPEOPLEINFO_JSON);
		for (var i = 0; i < POWERPEOPLEINFO_JSONS.length; i++) {
			// 追加权力人明细行数据
			addPowerTableItemHtml(PowerPeopleTableId, PowerPeopleListTableId, POWERPEOPLEINFO_JSONS[i], PowerPeopleTableArr, PowerPeopleTableTr, PowpeoleIndexItemName);
		}
		// 默认展示第一条权力人明细数据
		loadTableTr($("#" + PowerPeopleListTableId + " tr")[1], PowerPeopleTableId);
	}
}
function initPowPeople(infos){
   if(infos){
		for (var i = 0; i < infos.length; i++) {
			// 追加权力人明细行数据
			addPowerTableItemHtml(PowerPeopleTableId, PowerPeopleListTableId, infos[i], PowerPeopleTableArr, PowerPeopleTableTr, PowpeoleIndexItemName);
		}
		// 默认展示第一条权力人明细数据
		loadTableTr($("#" + PowerPeopleListTableId + " tr")[1], PowerPeopleTableId);
   }
}
//初始化权属来源表格
function initPowerSourceTable(flowSubmitObj) {
	var POWERSOURCEINFO_JSON = flowSubmitObj.busRecord.POWERSOURCEINFO_JSON;
	if (null != POWERSOURCEINFO_JSON && '' != POWERSOURCEINFO_JSON) {
		var POWERSOURCEINFO_JSONS = eval(POWERSOURCEINFO_JSON);
		for (var i = 0; i < POWERSOURCEINFO_JSONS.length; i++) {
			// 追加发证明细行数据
			addPowerTableItemHtml(PowerSourceTableId, PowerSourceListTableId, POWERSOURCEINFO_JSONS[i], PowerSourceTableArray, PowerSourceTableTr, PowsourceIndexItemName)
		}
		// 默认展示第一条发证明细数据
		loadTableTr($("#" + PowerSourceListTableId + " tr")[1], PowerSourceTableId);
	}
}

function initFiledByTableName(tableName) {
	// 权属来源
	if (PowerSourceTableId == tableName) {
		// JSON对应的数据库字段
		PowJsonName = PowerSourceJsonField;
		// 表格顶层ID
		HeadTableId = tableName;
		// 填写明细字段的二级表格ID
		SecondTableId = PowerSourceSecondTableId;
		// 列表表格ID
		ListTableId = PowerSourceListTableId;
		// 列表每行tr对应的ID
		ListItemTrId = PowsourceIndexName;
		// 列表每行对应的JSON字符串值ID
		ListItemJsonId = PowsourceIndexItemName;
		// 列表展示的字段数组
		ListFiledArr = PowerSourceTableArray;
		// 用于更新的id
		updateId = PowerSourceUpdateId;
		// 用于更新的id值
		updateIdKey = $("#" + updateId).val();
	} else if (PowerPeopleTableId == tableName) {
		// JSON对应的数据库字段
		PowJsonName = PowerPeopleJsonField;
		// 表格顶层ID
		HeadTableId = tableName;
		// 填写明细字段的二级表格ID
		SecondTableId = PowerPeopleSecondTableId;
		// 列表表格ID
		ListTableId = PowerPeopleListTableId;
		// 列表每行tr对应的ID
		ListItemTrId = PowpeoleIndexName;
		// 列表每行对应的JSON字符串值ID
		ListItemJsonId = PowpeoleIndexItemName;
		// 列表展示的字段数组
		ListFiledArr = PowerPeopleTableArr;
		// 用于更新的id
		updateId = PowerPeopleUpdateId;
		// 用于更新的id值
		updateIdKey = $("#" + updateId).val();
	}
}

/* 权力人明细查看
 * item：行数据
 * tableName：需要保存的表ID
 */
function loadTableTr(item, tableName) {
	// 字段初始化
	initFiledByTableName(tableName);
	// 清空字段填写的表格
	clearInfoTable(SecondTableId);
	var tableItemJsonStr = $(item).closest("tr").find("[name='" + ListItemJsonId + "']").val();
	var tableItemJson = JSON.parse(tableItemJsonStr);
	FlowUtil.initTableValue(tableItemJson, SecondTableId);
	$("#" + updateId).val($(item).closest("tr").attr("id"));
	var classTrId = "#" + SecondTableId + " .bdcdydacxTr";
	$(classTrId).removeClass("bdcdydacxTrRed");
	$(item).closest("tr").addClass("bdcdydacxTrRed");
}

/* 新增
 * tableName：需要保存的表格ID
 */
function addTable(tableName) {
	// 字段初始化
	initFiledByTableName(tableName);
	// 保存验证
	if (!valid(tableName)) {
		return;
	}
	var infos = {};
	// 获取填写的表格数据
	getTableJson(infos, tableName);
	// 追加列表表格
	addPowerTableItemHtml(HeadTableId, ListTableId, infos, ListFiledArr, ListItemTrId, ListItemJsonId);
	// 获取对应的JSON
	setJsonName(PowJsonName, tableName, ListTableId, ListItemTrId, ListItemJsonId);
	// 清空填写表格
	//clearInfoTable(tableName);
	// 清除列表选中样式
	$("#" + HeadTableId + " .bdcdydacxTr").removeClass("bdcdydacxTrRed");
}

/* 删除列表行数据
 * tableName：需要保存的表格ID
 * item：行对象
 */
function delTableTr(item, tableName) {
	// 字段初始化
	initFiledByTableName(tableName);
	// 删除单前行
	$(item).closest("tr").remove();
	// 获取对应的JSON
	setJsonName(PowJsonName, tableName, ListTableId, ListItemTrId, ListItemJsonId);
	// 序号减一
	tableName == 'PowerSourceInfo' ? itemCode-- : '';
}

/* 新增或保存列表行数据
 * tableName：需要保存的表格ID
 * item：行对象
 */
function updateTable(tableName) {
	// 字段初始化
	initFiledByTableName(tableName);
	// 保存验证
	if (!valid(tableName)) {
		return;
	}
	var updateIdValue = $("#" + updateId).val();
	// updateId值为空说明为新增情况
	if (!updateIdValue) {
		// 新增缴费数据
		addTable(tableName);
	} else {
		// 为修改情况
		var infos = {};
		// 获取缴费填写表格数据
		getTableJson(infos, tableName);
		// 更新数据
		updateListItem(infos, updateIdValue, tableName);
		// 获取对应的JSON
		setJsonName(PowJsonName, tableName, ListTableId, ListItemTrId, ListItemJsonId);
	}
	setMsg("操作成功！");
}

/* 更新表格数据
 * infos：当前填写表格的json格式数据
 * updateIdValue：需要更新的ID
 * tableName：需要保存的表格ID
 */
function updateListItem(infos, updateIdValue, tableName) {
	// 字段初始化
	initFiledByTableName(tableName);
	if (infos != "" && null != infos) {
		// 获取修改后的html代码
		var html = getListTableHtml(HeadTableId, ListTableId, infos, ListFiledArr, ListItemTrId, ListItemJsonId, "update");
		if (updateId) {
			// 上级ID
			var prevTrid = $("#" + updateIdValue).prev("tr").attr("id");
			$("#" + updateIdValue).remove();
			if (prevTrid) {
				$("#" + prevTrid).after(html)
			} else {
				$("#" + ListTableId).append(html);
			}
			$("#" + updateIdValue + " input[name='" + ListItemJsonId + "']").val(JSON.stringify(infos));
			var tableTrClass = "#" + HeadTableId + " .bdcdydacxTr";
			$(tableTrClass).removeClass("bdcdydacxTrRed");
			$("#" + updateIdValue).closest("tr").addClass("bdcdydacxTrRed");
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

/* 设值对应的JSON数据库字段值
 * jsonName：需要保存的数据
 * tableName：需要保存的表格ID
 * listTableName：列表表格ID
 * indexName：表格每行的Id前缀，如：表格行ID为PowerPeopleTableTr_1则indexName=PowerPeopleTableTr,indexNum=1
 * indexItemName：表格每行数据明细ID
 */
function setJsonName(jsonName, tableName, listTableName, indexName, indexItemName) {
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
		getTableJson(tableJson, tableName);
		if ('' != tableJson && null != tableJson) {
			datas.push(tableJson);
		}
	}
	$("input[type='hidden'][name='" + jsonName + "']").val(JSON.stringify(datas));
}

/* 追加表格行数据公共方法
 * tableName：需要保存的表格ID
 * listTableIdName：表格ID名称
 * json：追加的json格式数据
 * tableArr：表格展示的字段数组（初始化得到）
 * indexName：表格每行的Id前缀，如：表格行ID为PowerPeopleTableTr_1则indexName=PowerPeopleTableTr,indexNum=1
 * indexItemName：表格每行数据明细ID
 */
function addPowerTableItemHtml(tableName, listTableIdName, json, tableArr, indexName, indexItemName) {
	if (json != "" && null != json && tableArr != null && tableArr.length > 0) {
		var html = getListTableHtml(tableName, listTableIdName, json, tableArr, indexName, indexItemName, "save");
		$("#" + listTableIdName).append(html);
		var v = json[indexItemName];
		if(v){			
			$("#" + indexName + "_" + ListIndex + " input[name='" + indexItemName + "']").val(json[indexItemName]);
		} else {
			$("#" + indexName + "_" + ListIndex + " input[name='" + indexItemName + "']").val(JSON.stringify(json));
		}
		ListIndex++;
	}
}

/* 列表追加Html字符串
 * tableName：需要保存的表格ID
 * listTableIdName：表格ID名称
 * json：追加的json格式数据
 * tableArr：表格展示的字段数组（初始化得到）
 * indexName：表格每行的Id前缀，如：表格行ID为PowerPeopleTableTr_1则indexName=PowerPeopleTableTr,indexNum=1
 * indexItemName：表格每行数据明细ID
 * saveOrUpdate：更新或保存
 * return：html字符串
 */
function getListTableHtml(tableName, listTableIdName, json, tableArr, indexName, indexItemName, saveOrUpdate) {
	var trIdName = saveOrUpdate == "save" ? indexName + "_" + ListIndex : updateIdKey;
	var loadMethod = "loadTableTr(this, '" + tableName + "');";
	var delMethod = "delTableTr(this, '" + tableName + "');";
	var html = "";
	html += '<tr class="bdcdydacxTr" style="height: 38px;" id="' + trIdName + '">';
	html += '<input type="hidden" name="' + indexItemName + '"/>';
	listTableIdName == 'PowerSourceTable' ?  html += '<td style="text-align: center;">' + itemCode + '</td>' : ''; 
	for (var i = 0; i < tableArr.length; i++) {
		var val = json[tableArr[i]] ;
		if(!val){
			val = "";
		} else{
			if(tableArr[i]=='POWERSOURCEIDTYPE'){
				if(val==0){
					val = '临时';
				} else if(val==1){
					val = '现势';
				} else if(val==2){
					val = '历史';
				} else if(val==3){
					val = '终止';
				}
			} else if(tableArr[i]=='POWERPEOPLESEX'){
				if(val==1){
					val = '男';
				} else if(val==2){
					val = '女';
				}
			} else if (tableArr[i] == 'POWERSOURCENATURE') {
				if (val == 0) {
					val = '土地_出让合同';
				} else if (val == 1) {
					val = '土地_划拨决定书';
				} else if (val == 2) {
					val = '土地_原土地证';
				} else if (val == 3) {
					val = '不动产权证书';
				} else if (val == 4) {
					val = '其他批准文件';
				} else if (val == 5) {
					val = '初始登记证明号';
				} else if (val == 6) {
					val = '安置协议';
				}
			}
		}
		html += '<td style="text-align: center;">' + val+ '</td>';
	}
	html += '<td style="text-align: center;">';
	html += '<a href="javascript:void(0);" style="padding: 0 10px; margin-top: 2px; margin-left: 20%;" class="eflowbutton" onclick="' + loadMethod + '" id="' + indexName + 'Item">查 看</a>';
	html += '<a href="javascript:void(0);" onclick="' + delMethod +'" style="float: right;" class="syj-closebtn"></a></td>';
	html += '</tr>';
	return html;
}
//消息提示
function setMsg(msg) {
	art.dialog({
		content : msg,
		icon : "succeed",
		ok : true
	});
}
/*************JS需要公共代码结束***************/


//不动产产权证书打印与预览
function bdcCQZSprint(typeId){
    var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
	if(BDC_QZBSM){
		// typeId: 1为证书预览  2为证书打印   
	    var title = "证书打印";
	    var templateAlias = 'BDCQZ';
	    if(typeId==1) {
	  	 title = "证书预览";
	    }
		var dataStr = "";
		var QLRZJH = getQlrMc()
		dataStr += "&QLRZJH=" + (QLRZJH == "" ? QLRZJH : QLRZJH.substring(0, QLRZJH.length - 1));
		dataStr +="&BDCQZH="+$("input[name='BDC_SZZH']").val();
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
	}else{
		art.dialog({
			content :"请先填写权证标识码。",
			icon : "warning",
			ok : true
		});
	}
}

function getQlrMc() {
	var flowSubmitObj = FlowUtil.getFlowObj();
	var POWERPEOPLEINFO_JSON = flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON
	var QLRZJH = "";
	if (POWERPEOPLEINFO_JSON) {
		var powParse = JSON.parse(POWERPEOPLEINFO_JSON);
		for (let i = 0; i < powParse.length; i++) {
			QLRZJH += powParse[i].POWERPEOPLEIDNUM + ",";
		}
	}
	return QLRZJH;
}

/*
* 不动产登簿操作
* */
function BDCQLC_bdcdbcz(){
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
							var qzinfos = data.qzinfo;
							var qlrs = JSON.parse(flowSubmitObj.busRecord.POWERPEOPLEINFO_JSON);
							if(qzinfos.length == qlrs.length){
								var iflag = 0;
								for(var i=0;i<qzinfos.length;i++){
									if(qzinfos[i].gdzt == '1'){
										for(var j=0;j<qlrs.length;j++){
											if(qzinfos[i].qlr_mc == qlrs[j].POWERPEOPLENAME
												&& qzinfos[i].qlr_zh == qlrs[j].POWERPEOPLEIDNUM){
												qlrs[j].BDC_SZZH = qzinfos[i].qzzh;
												qlrs[j].BDC_CZR = currentUser;
												qlrs[j].BDC_CZSJ = currentTime;
												iflag = iflag + 1;
											}
										}
									}
								}
								initQlrs(qlrs);
								if(iflag != qlrs.length){
									$("input[name='BDC_DBZT']").val("0");
									$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
									art.dialog({
										content :"归档失败，存在与受理时的权利不一致的情况。",
										icon : "warning",
										ok : true
									});
								}else{
									$("input[name='BDC_DBZT']").val("1");
									$("input[name='BDC_DBJG']").val("登簿成功");
									art.dialog({
										content :"登簿成功",
										icon : "succeed",
										ok : true
									});
								}
							}else{
								$("input[name='BDC_DBZT']").val("0");
								$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
								art.dialog({
									content :"归档失败，与受理时的权利人数不匹配。",
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

function initQlrs(POWERPEOPLEINFO_JSON) {
	$("#PowerPeopleTable .bdcdydacxTr").remove();
	if (null != POWERPEOPLEINFO_JSON && '' != POWERPEOPLEINFO_JSON) {
		var POWERPEOPLEINFO_JSONS = eval(POWERPEOPLEINFO_JSON);
		for (var i = 0; i < POWERPEOPLEINFO_JSONS.length; i++) {
			// 追加权力人明细行数据
			addPowerTableItemHtml(PowerPeopleTableId, PowerPeopleListTableId, POWERPEOPLEINFO_JSONS[i], PowerPeopleTableArr, PowerPeopleTableTr, PowpeoleIndexItemName);
		}
		// 默认展示第一条权力人明细数据
		loadTableTr($("#" + PowerPeopleListTableId + " tr")[1], PowerPeopleTableId);
	}
}

function initDbxx(qzinfos,dialog){	
	var flag = false;
	var currentUser = $("input[name='uploadUserName']").val();
	var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
	var datas =  [];
	$("input[name='PowerPeopleTableTrMx']").each(function() {	
		var trData = {};		
		var inputName = $(this).attr("name");		
		var inputValue = $(this).val();
		trData = JSON.parse(inputValue);
		trData[inputName] = inputValue;
		datas.push(trData);
	});
	if(qzinfos.length == datas.length){
		var iflag = 0;
		for(var i=0;i<qzinfos.length;i++){
			if(qzinfos[i].gdzt == '1'){
				for(var j=0;j<datas.length;j++){
					if(qzinfos[i].qlr_mc == datas[j].POWERPEOPLENAME 
							&& qzinfos[i].qlr_zh == datas[j].POWERPEOPLEIDNUM){
						datas[j].BDC_SZZH = qzinfos[i].qzzh;
						var PowerPeopleTableTrMx =JSON.parse(datas[j].PowerPeopleTableTrMx);
						PowerPeopleTableTrMx.BDC_SZZH = qzinfos[i].qzzh;
						datas[j].PowerPeopleTableTrMx = PowerPeopleTableTrMx;
						iflag = iflag + 1;
					}
				}
			}
		}
		initPowPeople(datas);
		if(iflag != datas.length){
			$("input[name='BDC_DBZT']").val("0");
			$("input[name='BDC_DBJG']").val("归档失败，存在与受理时的权利不一致的情况。");
			if(dialog){
				art.dialog({
					content :"归档失败，存在与受理时的权利不一致的情况。",
					icon : "warning",
					ok : true
				});
			}
		}else{
			flag = true;
			$("input[name='BDC_DBZT']").val("1");
			$("input[name='BDC_DBJG']").val("登簿成功");
			if(dialog){
				art.dialog({
					content :"登簿成功",
					icon : "succeed",
					ok : true
				});
			}
			
		}
	}else{
		$("input[name='BDC_DBZT']").val("0");
		$("input[name='BDC_DBJG']").val("归档失败，与受理时的权利人数不匹配。");
		if(dialog){
			art.dialog({
				content :"归档失败，与受理时的权利人数不匹配。",
				icon : "warning",
				ok : true
			});
		}
	}
	return flag;
}

//保存权证标识码的操作
function saveQzbsm(){
	var BDC_QZBSM = $("input[name='BDC_QZBSM']").val();
	if(BDC_QZBSM){
		var currentUser = $("input[name='uploadUserName']").val();
		var currentTime = AppUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
		$("input[name='BDC_CZR']").val(currentUser);
		$("input[name='BDC_CZSJ']").val(currentTime);
		saveQzbsmToUpdateTable('PowerPeopleInfo');
		// 获得权利人信息
		getPowerPeopleInfoJson();
		//并完成暂存
		BdcQzPrintUtil.BDC_jffzTempSavePageFun();
	}else{
		parent.art.dialog({
			content : "请填写权证标识码。",
			icon : "warning",
			ok : true
		});
	}
}

/* 新增或保存列表行数据
 * tableName：需要保存的表格ID
 * item：行对象
 */
function saveQzbsmToUpdateTable(tableName) {
	// 字段初始化
	initFiledByTableName(tableName);
	// 保存验证
	if (!valid(tableName)) {
		return;
	}
	var updateIdValue = $("#" + updateId).val();
	// updateId值为空说明为新增情况
	if (!updateIdValue) {
		// 新增缴费数据
		addTable(tableName);
	} else {
		// 为修改情况
		var infos = {};
		// 获取缴费填写表格数据
		getTableJson(infos, tableName);
		// 更新数据
		updateListItem(infos, updateIdValue, tableName);
		// 获取对应的JSON
		setJsonName(PowJsonName, tableName, ListTableId, ListItemTrId, ListItemJsonId);
	}
}

