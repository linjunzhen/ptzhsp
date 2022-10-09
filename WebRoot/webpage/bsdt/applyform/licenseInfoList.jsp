<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,artdialog"></eve:resources>

<script type="text/javascript">

	function formatUrl(val,row){
        var licenseID = row.LicenseID;
        var typeCode=row.TypeCode;
        var fileId=row.FileID;
        var type=row.type;
        var IsExpired=row.IsExpired;
        if(true) {
            //licenseID=licenseID.replace("(","（");
            //licenseID=licenseID.replace(")","）");
            var href = "<span style='text-decoration:underline;color:green;'>" + val + "</span>";
            return href;
        }else{
            var href = "<a onclick=alertExpired()";
            href += "><span style='text-decoration:underline;color:red;'>" + val + "</span></a>";
            return href;
		}

	}
	var i=1;
    function formatOperator(val,row){
	    var sqfs="${sqfs}";
        var licenseID = row.LicenseID;
        //licenseID=licenseID.replace("(","（");
        //licenseID=licenseID.replace(")","）");
        var typeCode=row.TypeCode;
        i=i+1;
        var timestamp = (new Date()).getTime()+i;
        var fileId=row.FileID;
        var type=row.type;
        var name=row.Name;
        var FileSuffix=row.FileSuffix;
        var IsExpired=row.IsExpired;
        if(true) {
            var href="";
            if("1"==sqfs){
                href = "<a  onclick=showFile('" + fileId + "','" + licenseID + "','" + typeCode + "','" + type+ "','" + name+ "','" + timestamp+ "','" + FileSuffix;
                href += "')><span style='color:green;font-size: 14px;margin-left:5px;cursor: pointer;'>" + "查看" + "</span></a>";
                href+= "<a  id='"+timestamp+"' onclick=uploadFirst(";
                href += ")><span style='color:blue;margin-left:5px;font-size: 14px;cursor: pointer;'>" + "上传至审批平台" + "</span></a>";
				href+= "<a  id='"+timestamp+"' onclick=openCreditFeedBackView('2','"+fileId+"'";
				href += ")><span style='color:blue;margin-left:5px;font-size: 14px;cursor: pointer;'>" + "错误反馈" + "</span></a>";
				href+= "<a  id='"+timestamp+"' onclick=sendCreditFeedBackLoadfailureInfo('"+fileId+"'";
				href += ")><span style='color:blue;margin-left:5px;font-size: 14px;cursor: pointer;'>" + "加载失败" + "</span></a>";
                return href;
            }else if("2"==sqfs){
                href = "<a onclick=showFile('" + fileId + "','" + licenseID + "','" + typeCode + "','" + type+ "','" + name+ "','" + timestamp+ "','" + FileSuffix;
                href += "')><span style='color:red;font-size: 14px;margin-left:5px;cursor: pointer;'>" + "查看" + "</span></a>";
				href+= "<a  id='"+timestamp+"' onclick=openCreditFeedBackView('2','"+fileId+"'";
				href += ")><span style='color:red;margin-left:5px;font-size: 14px;cursor: pointer;'>" + "错误反馈" + "</span></a>";
				href+= "<a  id='"+timestamp+"' onclick=sendCreditFeedBackLoadfailureInfo('"+fileId+"'";
				href += ")><span style='color:blue;margin-left:5px;font-size: 14px;cursor: pointer;'>" + "加载失败" + "</span></a>";
                return href;
            }
        }
    }
    function uploadFirst(){
        alert("请先查阅电子证照!");
	}
	function alertExpired(){

	    alert("此电子证照已过期");
	}
    function uploadFile(fileId,licenseID,typeCode,type,name){
        var itemName="${itemName}";
        var transactor="${transactor}";
        var itemId="${itemId}";
        var queryName="${names}";
        var queryCode="${codes}";
        $.post( "creditController.do?uploadFile",{
                fileId:fileId,
                licenseID:licenseID,
                typeCode:typeCode,
                type:type,
                licenseName:name,
            itemName:itemName,
            itemId:itemId,
            transactor:transactor,
            queryName:queryName,
            queryCode:queryCode,
                attachKey:"${attachKey}",
                busRecordId:"${busRecordId}",
                busTableName:"${busTableName}"},
            function(responseText1, status, xhr) {
                var resultJson1 = $.parseJSON(responseText1);
                art.dialog.data("resultJsonInfo", resultJson1);
                AppUtil.closeLayer();
            });
	}
	function showFile(fileId,licenseID,typeCode,type,name,timestamp,FileSuffix){
        $("#"+timestamp).attr("onclick","uploadFile('" + fileId + "','" + licenseID + "','" + typeCode + "','" + type+ "','" + name+"')");
        var noprint="${noprint}";
        var queryName="${names}";
        var queryCode="${codes}";
        //新云电子证照需要额外传的新数据
        var itemName="${itemName}";
        var transactor="${transactor}";
        var itemId="${itemId}";
        var url=encodeURI("creditController.do?showFile&fileId="+fileId+"&licenseID="+licenseID+"&FileSuffix="+FileSuffix
            +"&queryName="+queryName+"&queryCode="+queryCode+"&licenseName="+name
            +"&itemName="+itemName+"&transactor="+transactor+"&itemId="+itemId
            +"&typeCode="+typeCode+"&type="+type+"&noprint="+noprint);
        $.dialog.open(url, {
            title : "证照页面",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false,
            close: function () {

            }
        }, false);

	}
function test(){
    showFile('12350128054323059A-(闽平）船登（销）（2018）HY-000139号-35012819480401541X',
		'（闽平）船登（销）（2018）HY-000139号','02102301','persona',
		'渔业船舶注销/中止登记证明书');

}

function openCreditFeedBackView(val,fileId){
	var title = "";
	if (val == 1) {
		title = "缺失证照反馈";
	} else if (val == 2) {
		title = "错误证照反馈";
	}
	var itemId="${itemId}";
	var aheadUserId="${aheadUserId}";
	var backUserId="${backUserId}";
	var names="${names}";
	var codes="${codes}";
	var creditMark="${creditMark}";
	var url = "creditController.do?creditFeedBackView&creditFeedBackType=" + val +
			"&itemId=" + itemId + "&aheadUserId=" + aheadUserId + "&backUserId=" + backUserId
			+ "&codes=" + codes + "&names=" + names + "&creditMark=" + creditMark;
	if (val == 2) {
		url += "&fileId=" + fileId;
	}
	$.dialog.open(encodeURI(url), {
		title: title,
		width: "800px",
		height: "400px",
		lock: true,
		resize: false
	}, false);
}

function sendCreditFeedBackLoadfailureInfo(fileId){
	var itemId="${itemId}";
	var aheadUserId="${aheadUserId}";
	var backUserId="${backUserId}";
	var creditMark="${creditMark}";
	$.ajax({
		url: "creditController.do?saveOrUpdateCreditFeedBack",
		async: false,
		method: "post",
		data: {
			"CREDIT_TYPE" : "3",
			"ITEM_ID" : itemId,
			"AHEAD_USER_ID" : aheadUserId,
			"BACK_USER_ID" : backUserId,
			"LICENSE_FILE_ID" : fileId,
			"CREDIT_FEEDBACK_MARK" : creditMark
		},
		success: function (data) {
			if (data) {
				var json = JSON.parse(data);
				if (json.success) {
					parent.art.dialog({
						content: json.msg,
						icon: "succeed",
						time: 3,
						ok: true
					});
				} else {
					parent.art.dialog({
						content: json.msg,
						icon: "error",
						ok: true
					});
				}
			}
		}
	});
}

function searchCredit(){
	//新云电子证照需要额外传的新数据
	var itemName="${itemName}";
	var transactor="${transactor}";
	var itemId="${itemId}";
	var url = "creditController.do?licenseDatagrid" + "&itemName=" + itemName + "&transactor=" + transactor + "&itemId=" + itemId;
	var creditCode = $("input[name='CREDIT_CODE']").val();
	var creditName = $("input[name='CREDIT_NAME']").val();
	if (creditCode && creditCode != "") {
		url += "&creditCode=" + creditCode;
	}
	if (creditName && creditName != "") {
		url += "&creditName=" + creditName;
	}
	girdDatagrid(encodeURI(url));
}


function highSearch(){
    //新云电子证照需要额外传的新数据
    var itemName="${itemName}";
    var transactor="${transactor}";
    var itemId="${itemId}";
    var url = "creditController.do?licenseDatagrid" + "&itemName=" + itemName + "&transactor=" + transactor + "&itemId=" + itemId +"&IS_HIGH_LEVEL=1";
    var HIGH_CREDIT_NAME = $("input[name='HIGH_CREDIT_NAME']").val();
    var HIGH_CREDIT_CODE = $("input[name='HIGH_CREDIT_CODE']").val();
    if (HIGH_CREDIT_NAME && HIGH_CREDIT_NAME != "") {
        url += "&HIGH_CREDIT_NAME=" + HIGH_CREDIT_NAME;
    }else{
        alert("请选择正确的高级别证照类型名称");
        return;
    }
    if (HIGH_CREDIT_CODE && HIGH_CREDIT_CODE != "") {
        url += "&HIGH_CREDIT_CODE=" + HIGH_CREDIT_CODE;
    }else{
      alert("请填写高级别证照编号");
      return;
    }
    girdDatagrid(encodeURI(url));
}



function searchReset() {
	$("input[name='CREDIT_CODE']").val('');
	$("input[name='CREDIT_NAME']").val('');
}



	$(function() {
        //新云电子证照需要额外传的新数据
        var itemName="${itemName}";
        var transactor="${transactor}";
        var itemId="${itemId}";
        var url=encodeURI("creditController.do?licenseDatagrid"+"&itemName="+itemName+"&transactor="+transactor+"&itemId="+itemId);
		girdDatagrid(url);
		
		$("#HIGH_CREDIT_NAME").combobox({
                valueField: 'DIC_CODE',
                textField: 'DIC_NAME',
                editable:true,
                hasDownArrow:true,
                url: "creditController.do?getCertificateType",
                required:false,
                panelHeight: '200',
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) != -1;
                },
                onSelect:function (row) {
                    if($("input[name='HIGH_CREDIT_NAME']").val()=="中华人民共和国不动产权证书"){
                        $("input[name='HIGH_CREDIT_CODE']").val("闽(XXXX)平潭不动产权第XXXXXXX号");
                    }
                    if($("input[name='HIGH_CREDIT_NAME']").val()=="不动产登记证明"){
                        $("input[name='HIGH_CREDIT_CODE']").val("闽(XXXX)平潭不动产证明第XXXXXXX号");
                    }
                }
        });
		
	});

    function girdDatagrid(url) {
		$('#LicenseInfoGrid').datagrid({  //初始化datagrid
			url:url,
			idField:"FileID",
			rownumbers: false,
			fit:true,
			border:false,
			checkOnSelect:true,
			selectOnCheck:true,
			singleSelect:true,
			fitColumns:true,
			pagination:true,
			pageSize:15,
			pageList:[15,20,30],
			queryParams: {
				"CORP_NAME" : "${names}",
				"CORP_CODE" : "${codes}",
				"TYPE" : "${type}",
				"attachKey":"${attachKey}",
				"searchType":"${searchType}"
			},
			onLoadSuccess: function (data) {
				if (data.total == 0) {
					//添加一个新数据行，第一列的值为你需要的提示信息，然后将其他列合并到第一列来，注意修改colspan参数为你columns配置的总列数
					$(this).datagrid('appendRow', { LicenseID: '<div style="text-align:center;color:red;font-size: 18px;">没有相关记录！<span style="color: #0c62ba;cursor: pointer;font-weight: 700;text-decoration: underline;" onclick="openCreditFeedBackView(1);">缺失反馈</span></div>' }).datagrid('mergeCells', { index: 0, field: 'LicenseID', colspan: 5 })
					//隐藏分页导航条，这个需要熟悉datagrid的html结构，直接用jquery操作DOM对象，easyui datagrid没有提供相关方法隐藏导航条
					$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
				}else{
					var len = data.total;
					$(this).datagrid('appendRow', { LicenseID: '<div style="text-align:center;color:red;font-size: 18px;"><span style="color: #0c62ba;cursor: pointer;font-weight: 700;text-decoration: underline;" onclick="openCreditFeedBackView(1);">缺失反馈</span></div>' }).datagrid('mergeCells', { index: len, field: 'LicenseID', colspan: 5 })
					$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').show();
				}
				//如果通过调用reload方法重新加载数据有数据时显示出分页导航容器

			},
			onDblClickRow: function(index, row){
				var regNum="";
				if(row.CORP_CODE!=null){
					regNum=row.CORP_CODE;
				}else if(row.CREDIT_CODE!=null){
					regNum=row.CREDIT_CODE;
				}else if(row.REG_NUM!=null){
					regNum=row.REG_NUM;
				}
				art.dialog.data("creditInfo",{
					regNum:regNum,
					corpName:row.CORP_NAME
				});
				AppUtil.closeLayer();
			},
			columns:[[
// 		        {field:'ck',checkbox:true},
				{field:'LicenseID',title:'证照编号',width:120,align:'left'},
				{field:'Name',title:'证照名称',width:120,align:'left',formatter: function(value,row,index){
						return formatUrl(value,row);
					}},
				{field:'TypeCode',title:'证照类别编码',width:80,align:'left'},
				{field:'IsExpired',title:'是否过期',width:40,align:'left'},
				{field:'FileID',title:'操作',width:160,align:'left',formatter: function(value,row,index){
						return formatOperator(value,row);
					}}
			]]
		});
	}

	function closePage(){
		art.dialog.data("creditInfo",null);
		AppUtil.closeLayer();
	}
</script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">
<div class="easyui-layout" fit="true">
	<div region="center">

		<div id="FormFieldToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<span>证照编号：</span><input type="text" class="eve-input" name="CREDIT_CODE">
							<span>证照名称：</span><input type="text" class="eve-input" name="CREDIT_NAME">
							<a href="#" class="easyui-linkbutton"
							   plain="true"
							   onclick="searchCredit();">搜索</a>
							<a href="#" class="easyui-linkbutton"
							   plain="true"
							   onclick="searchReset();">重置</a>
						</div>
					</div>
				</div>
			</div>
		</div>


        <div>
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                         <span style="color: #0c62ba;font-size: 14px;cursor: pointer;font-weight: 700;">获取高级别证照补充信息</span>
                         <span style="color: #0c62ba;font-size: 12px;cursor: pointer;"><font color="red">（根据省要求高级别证照需精确调用）</font></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="HighToolbar">
            <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                            <span>高级别证照类型名称：</span><input class="easyui-combobox" name="HIGH_CREDIT_NAME" id="HIGH_CREDIT_NAME" style="width: 250px;height: 30px;"/>
                            <span>高级别证照编号：</span><input type="text" class="eve-input" name="HIGH_CREDIT_CODE"  id="HIGH_CREDIT_CODE" style="width: 250px;" placeholder="参考格式：闽(XXXX)平潭不动产权第XXXXXXX号" />
                            <a href="#" class="easyui-linkbutton" plain="true" onclick="highSearch();">获取高级别证照</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

		
		<table id="LicenseInfoGrid"></table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
</body>
