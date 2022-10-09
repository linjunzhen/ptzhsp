<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">


$(function() {
	AppUtil.initWindowForm("SerialNumberForm", function(form, valid) {
		if (valid) {
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled","disabled");
			var formData = $("#SerialNumberForm").serialize();
			var url = $("#SerialNumberForm").attr("action");
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
						parent.$("#SerialNumberGrid").datagrid("reload");
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
	},"T_WSBS_SERIALNUMBER",null, $("input[name='SERIAL_ID']").val());
	
	
});
function addparam(params){
	pos = getCursortPosition(document.getElementsByName('SERIAL_RULE')[0]);		
	s = $("input[name='SERIAL_RULE']").val();		
	$("input[name='SERIAL_RULE']").val(s.substring(0, pos)+params+s.substring(pos));
}

function getCursortPosition (ctrl) {//获取光标位置函数		
	var CaretPos = 0;	// IE Support		
	if (document.selection) {	
		ctrl.focus ();			
		var Sel = document.selection.createRange ();	
		Sel.moveStart ('character', -ctrl.value.length);			
		CaretPos = Sel.text.length;		
	}		// Firefox support		
	else if (ctrl.selectionStart || ctrl.selectionStart == '0')	
		CaretPos = ctrl.selectionStart;		
	return (CaretPos);	
}
/**
*  编号选择器调用代码
*/		
function showSelectRoles(){
		var serialNumberIds = $("input[name='SERIAL_IDS']").val();
		var serialNumberNames = $("input[name='SERIAL_NAMES']").val();
		parent.$.dialog.open("serialNumberController.do?selector&allowCount=1&serialNumberIds="+serialNumberIds+"&serialNumberNames="+serialNumberNames, {
			title : "选择编号配置",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectRoleInfo = art.dialog.data("selectSerialNumberInfo");
    			if(selectRoleInfo){
    				$("input[name='SERIAL_IDS']").val(selectRoleInfo.serialNumberIds);
        			$("input[name='SERIAL_NAMES']").val(selectRoleInfo.serialNumberNames);
    			}
    		}
    	}, false);
		
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="SerialNumberForm" method="post" action="serialNumberController.do?saveOrUpdate">
	    <div  id="SerialNumberFormDiv" data-options="region:'center',split:true,border:false">
		    <!--==========隐藏域部分开始 ===========-->
		    <input type="hidden" name="SERIAL_ID" value="${serialNumber.SERIAL_ID}"> 
		    <input type="hidden" name="CREATE_TIME" value="${serialNumber.CREATE_TIME}"> 
		    <input type="hidden" name="SSBMBM" value="${serialNumber.SSBMBM}"> 
		    <input type="hidden" name="INITSEQ" value="${serialNumber.INITSEQ}">
		    <!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
<tr><td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>编号配置</a></div></td></tr><tr style="height:40px;"><td>
<span style="width: 120px;float:left;text-align:right;">编码类型<font class="dddl_platform_html_requiredFlag">*</font>：</span>
<eve:radiogroup typecode="SerialNumberType" width="450" fieldname="SERIAL_TYPE" defaultvalue="1"  value="${serialNumber.SERIAL_TYPE}" ></eve:radiogroup></td></tr>
<tr style="height:40px;"><td><span style="width: 120px;float:left;text-align:right;">编号配置名称<font class="dddl_platform_html_requiredFlag">*</font>：</span>
<input type="text" style="width:350px;float:left;" maxlength="120"  class="eve-input  validate[required] ,ajax[ajaxVerifyValueExist]"  value="${serialNumber.SERIAL_NAME}" id="SERIAL_NAME"  name="SERIAL_NAME" /></td></tr>
<tr style="height:60px;"><td>
<span style="width: 120px;float:left;text-align:right;">编号参数：</span>
	<div style="width:600px;margin-left:120px;">
	<c:forEach items="${serialParam}" var="nextTran">
			${nextTran.DIC_NAME}<img style="position: relative; top:4px;" src="plug-in/images/icons/add.png" onclick='addparam("${nextTran.DIC_NAME}")'><span style="width:30px;display:inline-block;"></span>
	</c:forEach>
</div>
</td></tr>
<tr style="height:40px;"><td><span style="width: 120px;float:left;text-align:right;">编码规则<font class="dddl_platform_html_requiredFlag">*</font>：</span>
<input type="text" style="width:500px;float:left;" maxlength="250" class="eve-input" value="${serialNumber.SERIAL_RULE}"  name="SERIAL_RULE" /></td></tr>
<tr style="height:40px;"><td><span style="width: 120px;float:left;text-align:right;">序列类型：</span>
<eve:eveselect clazz="eve-input validate[required]" dataParams="SerialSeqType"
						          value="${serialNumber.SEQUENCE_TYPE}"
						         dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择序列类型"
						         name="SEQUENCE_TYPE">
						    </eve:eveselect>
</table>

<div style="height:30px;"></div>
<div style="font-size:14pt;"> 
	<span  style="width: 100px;float:left;text-align:right;">范例: </span><span  style="width:180px;float:left;margin-left:10px;text-align:left;">
	闽发改〔2014〕1号  </span> <span  style="width:60px;float:left;margin-left:20px;text-align:left;">配置：</span><span  style="width:300px;float:left;margin-left:5px;text-align:left;color:red;">闽发改〔【年】〕【序列码】号 </span>
</div>
		  
	</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
