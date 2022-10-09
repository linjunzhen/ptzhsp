<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="com.zhuozhengsoft.pageoffice.*"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%
//***************************卓正PageOffice组件的使用********************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setCustomToolbar(true); //false的话则不出现pageoffice自己的工具栏
	poCtrl1.setServerPage(path + "/poserver.zz"); //此行必须
	String filepath =null;
	Map map = (Map) request.getAttribute("tickets");
	if(map.get("TICKETS_DOC")!=null&& !map.get("TICKETS_DOC").equals("")){
	    filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
	            + "/" + "attachFiles/tickets/files/"+map.get("TICKETS_DOC").toString(); 
	}
	poCtrl1.addCustomToolButton("打开", "Open", 3);
	poCtrl1.addCustomToolButton("保存", "Save", 1);
	poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("另保存", "OtherSave", 1);
	poCtrl1.addCustomToolButton("关闭", "closeDoc", 10);
	poCtrl1.addCustomToolButton("-", "", 0);
	poCtrl1.addCustomToolButton("页面设置", "ShowDialog_1()", 0);
	poCtrl1.addCustomToolButton("打印", "ShowDialog_2()", 6);
	poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen", 4);
	if(map.get("TICKETS_DOC")!=null&& !map.get("TICKETS_DOC").equals("")){
	    poCtrl1.setSaveFilePage(request.getContextPath() + "/ticketsController/savefile.do?filename="+map.get("TICKETS_DOC")+"&type=tickets");
	}else{
	poCtrl1.setSaveFilePage(request.getContextPath() + "/ticketsController/savefile.do?type=tickets");
	}
	poCtrl1.setTitlebar(false);
	poCtrl1.setOfficeToolbars(true);
	poCtrl1.setMenubar(false);
	poCtrl1.setOfficeVendor(OfficeVendorType.AutoSelect);
    
   if(filepath!=null&&!filepath.equals("")){
    poCtrl1.webOpen(filepath, OpenModeType.docNormalEdit, "evecom");
   }else{
    poCtrl1.webCreateNew("evecom",DocumentVersion.Word2003);
   }
    poCtrl1.setJsFunction_AfterDocumentOpened("load()");
    poCtrl1.setJsFunction_AfterDocumentSaved("updateTickets()");

    poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
%>

<!-- ========================pageOffice        1.  导入java  结束 ======================== -->

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
function load() {
    document.getElementById("PageOfficeCtrl1").FullScreen = false;
}
function updateTickets(){
	var strCustomSaveResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
    AppUtil.ajaxProgress({
        url : "ticketsController.do?saveOrUpdate",
        params : {
            TICKETS_ID:$("input[name='ticketsid']").val(),
            TICKETS_DOC:strCustomSaveResult
            },
        callback : function(resultJson) {
            if(resultJson.success){
                  parent.art.dialog({
                        content: resultJson.msg,
                        icon:"succeed",
                        time:3,
                        ok: true
                    });
                AppUtil.closeLayer();
            }else{
                parent.art.dialog({
                    content: resultJson.msg,
                    icon:"error",
                    ok: true
                });
            }
        }
    });
}
//全屏/还原
function IsFullScreen() {
    document.getElementById("PageOfficeCtrl1").FullScreen = !document
            .getElementById("PageOfficeCtrl1").FullScreen;
}
//保存
function Save(){
	document.getElementById("PageOfficeCtrl1").WebSave();
}
//另存为
function OtherSave(){
	document.getElementById("PageOfficeCtrl1").ShowDialog(3);
}
//打开
function Open(){
    document.getElementById("PageOfficeCtrl1").ShowDialog(1);
}
//关闭
function closeDoc() {
    if(document.getElementById("PageOfficeCtrl1").IsDirty){
        art.dialog.confirm('当前文档未保存，是否继续关闭？', function () {
        	AppUtil.closeLayer(); 
        }, function () {
           
        });
    }else{
    	AppUtil.closeLayer();  
    }
}
//页面设置
function ShowDialog_1() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(5);
}
//页面打印
function ShowDialog_2() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(4);
}
//在光标处加入书签
function addBookMark(markname,marktext){
	var timestamp = (new Date()).valueOf(); 
    bkName = "PO_"+markname+"_"+timestamp;
    bkText = "【"+marktext+"】";
        var mac = "Function myfunc()" + " \r\n"
                + "Dim r As Range " + " \r\n"
                + "Set r = Application.Selection.Range " + " \r\n"
                + "r.Text = \"" + bkText + "\"" + " \r\n"
                + "Application.ActiveDocument.Bookmarks.Add Name:=\"" + bkName + "\", Range:=r " + " \r\n"
                + "Application.ActiveDocument.Bookmarks(\"" + bkName + "\").Select " + " \r\n"
                + "End Function " + " \r\n";
        document.getElementById("PageOfficeCtrl1").RunMacro("myfunc", mac);
	
}
//在光标处加入书签
function addBookMark2(markname,marktext){
    var timestamp = (new Date()).valueOf(); 
    document.getElementById("PageOfficeCtrl1").DataRegionList.Add("PO_"+markname+"_"+timestamp,"【"+marktext+"】");// （书签名， 显示的文本）  
}
//删除书签
function deleteBookMark(){
	var markname = "PO_NAME";
    document.getElementById("PageOfficeCtrl1").DataRegionList.Delete(markname);// （书签名， 显示的文本）    
}

//遍历当前Word中已存在的书签名称
function checkBkNames() {
    var drlist = document.getElementById("PageOfficeCtrl1").DataRegionList;
    drlist.Refresh();
    var bkName = "";
    var bkNames = ",";
    for (var i = 0; i < drlist.Count; i++) {
        bkName = drlist.Item(i).Name;
        bkNames += bkName.substr(3) + ",";
    }
    //return bkNames.substr(0, bkNames.length - 1);
    return bkNames;
}

//遍历当前Word中已存在的书签文本
function checkBkConts() {
    var drlist = document.getElementById("PageOfficeCtrl1").DataRegionList;
    drlist.Refresh();
    var bkCont = "";
    var bkConts = ",";
    for (var i = 0; i < drlist.Count; i++) {
        bkCont = drlist.Item(i).Value;
        bkConts += bkCont + ",";
    }
   // return bkConts.substr(0, bkConts.length - 1);
    return bkConts;
}
/**
 * 点击树形节点触发的事件
 */
function onTicketsTreeClick(event, treeId, treeNode, clickFlag) {
    if(event.target.tagName=="SPAN"&&treeNode.id!="0"){
    	addBookMark(treeNode.id,treeNode.name);
        }
}

/**$(document).ready(function() {
    var ticketsTreeSetting = {
        edit : {
            enable : false,
            showRemoveBtn : false,
            showRenameBtn : false
        },
        view : {
            selectedMulti : false
        },
        callback : {
            onClick : onTicketsTreeClick
        },
        async : {
        	enable : true,
            url : "baseController.do?tree",
            otherParam : {
                "tableName" : "T_MSJW_SYSTEM_DICTYPE",
                "idAndNameCol" : "TYPE_CODE,TYPE_NAME",
                "targetCols" : "PATH,TYPE_CODE,PARENT_ID",
                "rootParentId" : "402881945050e09b015050f2a4d90008",
                "isShowRoot" : "false",
                "rootName":"书签树"
            }
        }
    };
    $.fn.zTree.init($("#ticketsTree"), ticketsTreeSetting);
});*/
$(document).ready(function() {
	//loadDatas();
	var ticketsTreeSetting = {
	        edit : {
	            enable : false,
	            showRemoveBtn : false,
	            showRenameBtn : false
	        },
	        view : {
	            selectedMulti : false
	        },
	        callback : {
	            onClick : onTicketsTreeClick
	        },
	        async : {
	            enable : true,
	            url : "ticketsController/tree.do",
	            otherParam : {
	                "parentId" : "402881945050e09b015050f2a4d90008"
	            }
	        }
	    };
	    $.fn.zTree.init($("#ticketsTree"), ticketsTreeSetting);
});
/**function loadDatas() {
    $.post("dictionaryController/auto.do", {typeCode:"djsqpz"}, function(responseText,
            status, xhr) {
        var resultJson = $.parseJSON(responseText);
        for ( var index in resultJson) {
            var record = resultJson[index];
            $("#ticketsTree").append("<li><span onclick=\"addBookMark('"+record.DIC_CODE+"','"+record.DIC_NAME+"')\">"+record.DIC_NAME+"</span></li>");

        }
    });
}*/
/**function loadDatas() {
    $.post("ticketsController/tree.do", {parentId:"402881945050e09b015050f2a4d90008"}, function(responseText,
            status, xhr) {
        var resultJson = $.parseJSON(responseText);
        for ( var index in resultJson.data) {
            var record = resultJson.data[index];
            $("#ticketsTree").append("<li><span >"+record.TYPE_NAME+"</span></li>");
            for ( var ind in record.Item){
            	var item = record.Item[ind];
            	$("#ticketsTree").append("<li><span >111"+item.DIC_NAME+"</span></li>");
            }

        }
    });
}**/
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
       
      
  <div class="easyui-layout" fit="true">
    <div data-options="region:'west',split:false"
        style="width:150px;">
        <div class="right-con">
            <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                <div class="e-toolbar-ct">
                    <div class="e-toolbar-overflow">
                        <div style="color:#005588;">
                            <img src="plug-in/easyui-1.4/themes/icons/script.png"
                                style="position: relative; top:7px; float:left;" />&nbsp;书签节点
                        </div>
                    </div>
                </div>
            </div>
        </div>
      <ul id="ticketsTree" class="ztree"></ul>
       <!--<ul id="ticketsTree"></ul>-->
    </div>
    <div data-options="region:'center',split:false">
        <input type="hidden" name="ticketsid" value="${tickets.TICKETS_ID}">
        <div style="height: 4%;width: 100%;text-align: right;" ><span style="font-size: 14px;color: #EE1818;">注意事项</span><span style="font-size: 14px;padding-right: 10px;">:(1)多个书签用空格隔开并且注意空格需要在书签外</span></div>
        <div style="height: 96%;width: 100%;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1" ></po:PageOfficeCtrl>
        </div>
    </div>
</div>
       
</body>
