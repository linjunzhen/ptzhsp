<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
function onTjtbTreeClick(event, treeId, treeNode, clickFlag){
	if(event.target.tagName=="SPAN"){
        if(treeNode.id=="0"){

        }else{
           $("#tjtbiframe").attr("src",treeNode.desc);
        }
	}
}

function onTjtbTreeAsyncSuccess(event, treeId, treeNode, clickFlag){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
    //获取节点
    var nodes = treeObj.getNodes()[0].children;
    if(nodes.length>0){
    	treeObj.selectNode(nodes[0]);
    	$("#tjtbiframe").attr("src",nodes[0].desc);
    }
    
}
var tjtbTreeSetting;
$(document).ready(function(){
	var tjtbTreeSetting = {
		edit : {
            enable : false,
            showRemoveBtn : false,
            showRenameBtn : false
        },
        view : {
            selectedMulti : false
        },
        callback : {
            onClick : onTjtbTreeClick,
            onAsyncSuccess: onTjtbTreeAsyncSuccess
        },
        async : {
            enable : true,
            url : "dictionaryController/tjtbTree.do",
            otherParam : {
                "TYPE_CODE" : "xntb"
            }
        }
    };
    $.fn.zTree.init($("#tjtbTree"), tjtbTreeSetting);

});
</script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',split:false" 
        style="width:250px;" >
        <div class="right-con">
                <div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
                    <div class="e-toolbar-ct">
                        <div class="e-toolbar-overflow">
                          <div style="color:#005588;"><img src="plug-in/easyui-1.4/themes/icons/script.png" style="position: relative; top:7px; float:left;" />&nbsp;效率图表</div>
                        </div>
                    </div>
                </div>
        </div>
        <ul id="tjtbTree" class="ztree"></ul>
    </div>
    <div data-options="region:'center',split:false" >
        <iframe scrolling="auto" width="100%" height="120%" frameborder="0" src="" id="tjtbiframe">
        </iframe>
    </div>
</div>