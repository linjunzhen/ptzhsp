<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<eve:resources loadres="jquery,apputil,layer,artdialog"></eve:resources>
<script type="text/javascript">
function evaluate(){
	var a="";
	var addr= [1,2,3,4,5,6,7,8];
	for(var i=7;i>=0;i--){
		a=PJAOcx.ComOpen(addr[i]);
		if(a==0){
			break;
		}		
	}
	if(a==0){
		a=PJAOcx.RequirePJA();
	}else {
		alert("请检查评价器是连接是否正常")	;			
	}
	//ScoreOcx.CloseCom();
}
//打开串口  
/* function evaluate(){
	PJAOcx.ComOpen(3); //串口号为1 	
	PJAOcx.RequirePJA();
} */
</script>
<script language="javascript" event="doData(Key)" for="PJAOcx">
		var eval = Key;
		var recordId = '${recordId}';
		AppUtil.ajaxProgress({
			url : "callController.do?evaluate",
			params : {
				recordId : recordId,
				eval : eval
			},
			callback : function(resultJson) {
				PJAOcx.ComClose();
			  	parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				parent.$("#QueuingDoneGrid").datagrid("reload");
				AppUtil.closeLayer();
			}
		});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;text-align: center;">
<br>
	<p style="font-size: 20px;">是否发起评价？</p>
	<div class="eve_buttons">
	    <input value="是" type="submit" onclick="evaluate();" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
	    <input value="否" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
	</div>
</body>
<!-- <OBJECT ID="ScoreOcx1"  WIDTH=""  HEIGHT=""  CLASSID="CLSID:A8C33162-4D7F-45BB-AD6C-5BFA88289320"></OBJECT> -->

<OBJECT 
classid="clsid:5663CC3D-03FE-47E3-968B-240D7BA06C1B"
width=0
height=0
align=center
hspace=0
vspace=0
id="PJAOcx"
>
</OBJECT>
</OBJECT>

