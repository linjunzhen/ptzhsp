<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
$(function() {
	AppUtil.initWindowForm("TicketsForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#TicketsForm").serialize();
			var url = $("#TicketsForm").attr("action");
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if(resultJson.success){
						  parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
						parent.$("#TicketsGrid").datagrid("reload");
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
	},"T_WSBS_TICKETS",null, $("input[name='TICKETS_ID']").val());
	
	
	
});
/**
 * 查看配置文件
 */
function showTicketsTemplate() {
	//获取票单ID
    var ticketsId = $("input[name='TICKETS_ID']").val();
	if(!ticketsId){
		art.dialog({
            content: '请先保存票单',
            icon:"error",
            time:3,
            ok: true
        });
	}else{
		$.dialog.open("ticketsController.do?showTicketsTemplate&entityId="+ticketsId, {
			title : "查看票单配置",
			width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false
	    }, false);
	}
    
};

/**
 * 查看配置文件
 */
function showTickets() {
    //获取票单ID
    var ticketsId = $("input[name='TICKETS_ID']").val();
    if(!ticketsId){
        art.dialog({
            content: '请先保存票单',
            icon:"error",
            time:3,
            ok: true
        });
    }else{
        $.dialog.open("ticketsController.do?showTickets&entityId="+ticketsId, {
            title : "查看票单配置",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false
        }, false);
    }
    
};

/**
 * 编辑配置文件
 */
function editTicketsTemplate() {
    //获取票单ID
    var ticketsId = $("input[name='TICKETS_ID']").val();
    if(!ticketsId){
        art.dialog({
            content: '请先保存票单',
            icon:"error",
            time:3,
            ok: true
        });
    }else{
        $.dialog.open("ticketsController.do?editTicketsTemplate&entityId="+ticketsId, {
        	title : "票单配置",
            width: "100%",
            height: "100%",
            left: "0%",
            top: "0%",
            fixed: true,
            lock : true,
            resize : false
        }, false);
    }
    
};
/**  =======================      ==========================**/
/**
*  编号选择器调用代码
*/      
function showSelectSerialNumber(){
        var serialNumberIds = $("input[name='SERIAL_ID']").val();
        var serialNumberNames = $("input[name='SERIAL_NAME']").val();
        parent.$.dialog.open("serialNumberController.do?selector&allowCount=1&serialNumberIds="+serialNumberIds+"&serialNumberNames="+serialNumberNames, {
            title : "选择编号配置",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectRoleInfo = art.dialog.data("selectSerialNumberInfo");
                if(selectRoleInfo){
                    $("input[name='SERIAL_ID']").val(selectRoleInfo.serialNumberIds);
                    $("input[name='SERIAL_NAME']").val(selectRoleInfo.serialNumberNames);
                }
            }
        }, false);
        
    }

</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="TicketsForm" method="post" action="ticketsController.do?saveOrUpdate">
	    <div  id="TicketsFormDiv" data-options="region:'center',split:true,border:false">
		    <!--==========隐藏域部分开始 ===========-->
		    <input type="hidden" name="TICKETS_ID" value="${tickets.TICKETS_ID}"> 
		    <!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
		    <tr style="height:29px;"><td colspan="1" class="dddl-legend"  style="font-weight:bold;" >基本信息</td></tr>
<tr style="height:40px;"><td><span style="width: 100px;float:left;text-align:right;">单据名称<font class="dddl_platform_html_requiredFlag">*</font>：</span>
<input type="text" style="width:350px;float:left;" maxlength="120"  class="eve-input  validate[required] "  value="${tickets.TICKETS_NAME}" id="TICKETS_NAME"  name="TICKETS_NAME" /></td></tr>
</table>

</div>

		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="配置单票" type="button" onclick="editTicketsTemplate();" class="z-dlg-button z-dialog-okbutton " />
			    <input value="查看单票" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="showTicketsTemplate();"/>
			   <!--  <input value="加载数据单票" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="showTickets();"/> -->
			    <input value="确定" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
