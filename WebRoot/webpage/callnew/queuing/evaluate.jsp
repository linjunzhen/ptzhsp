<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%

	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<eve:resources loadres="jquery,apputil,layer,artdialog"></eve:resources>
<script src="<%=path%>/webpage/callnew/queuing/js/sign.js"></script>
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
	  	parent.art.dialog({
			content: "请检查评价器是连接是否正常",
			icon:"error",
			time:5,
			ok: true
		});
	}
}
function GWQevaluate(){
	<%-- var TellerName = '${sessionScope.curLoginUser.fullname}';
	var TellerNo = parent.$("input[name='winNo']").val();
	var TellerPhoto;
	if('${sessionScope.curLoginUser.photo}'!=null&&'${sessionScope.curLoginUser.photo}'!=''){
		TellerPhoto = '${_file_Server}${sessionScope.curLoginUser.photo}';
	}else{
		TellerPhoto = '<%=basePath%>/webpage/system/login/images/name.png';
	} --%>

	var cancel=GWQ.GWQ_CancelOperate();
	if(cancel==0)
	{
		//var ret=GWQ.DoGWQ_StartEvaluator(TellerName,TellerNo,TellerPhoto);
		var userJson = '{"timeout":60,"titleName":"全国一体化在线政务服务平台“好差评”","veryGood":[{"msg":"一窗受理一次办结","code":510},{"msg":"可以先受理后补材料","code":511},{"msg":"不用提交证明","code":512},{"msg":"可以全程网上办理","code":513},{"msg":"可以使用手机办理","code":514},{"msg":"可以就近办理","code":515},{"msg":"无需材料直接办理","code":516},{"msg":"服务热情效率高","code":517}],"good":[{"msg":"填写一张表单就可完成申报","code":406},{"msg":"在线提交材料窗口核验","code":407},{"msg":"一张清单告知全部申报材料","code":408},{"msg":"用告知承诺减免申报材料","code":409},{"msg":"可以在线预约办理","code":410},{"msg":"跑大厅一次办完","code":411},{"msg":"可以使用自助机办理","code":412},{"msg":"服务态度较好","code":413}],"common":[{"msg":"一次性告知需要补正的材料","code":307},{"msg":"提供申报材料样本","code":308},{"msg":"在承诺的时间内办结","code":309},{"msg":"办事指南指引准确","code":310},{"msg":"按办事指南要求的材料即可办理","code":311},{"msg":"可以快递送达","code":312},{"msg":"跑动次数与承诺的一致","code":313},{"msg":"服务态一般","code":314}],"pleased":[{"msg":"没有提供材料样本","code":207},{"msg":"没有提供材料清单","code":208},{"msg":"未在承诺时间内办结","code":209},{"msg":"同样内容的证明材料被要求多次提交","code":210},{"msg":"承诺网办但无法在线办理","code":211},{"msg":"在线预约办理后到实体大厅重复取号排队","code":212},{"msg":"窗口人员业务不熟练","code":213},{"msg":"服务态度生硬","code":214}],"veryPleased":[{"msg":"在办事指南之外增加新的审批条件","code":108},{"msg":"需提供办事指南之外的申报材料","code":109},{"msg":"无理由超过法定办理时间","code":110},{"msg":"办事指南提供样本有错","code":111},{"msg":"承诺在线收取申报材料实际无法收取","code":112},{"msg":"多头跑窗口和部门","code":113},{"msg":"跑动次数与承诺的不一致","code":114},{"msg":"服务态度差效率低","code":115}]}';
		var ret = GWQ.DoGWQ_StartGoodBadEvaluator(userJson);
		if(ret==0){

		}else{
		  	parent.art.dialog({
				content: "启动评价失败，返回错误码为"+ret,
				icon:"error",
				time:3,
				ok: true
			});
		}
	}else{
		parent.art.dialog({
			content: "关闭信息牌失败，返回错误码为"+cancel,
			icon:"error",
			time:3,
			ok: true
		});
	}

}

function SCOevaluate(){
	var a ;
	var addr = 3;
// 	addr=textfield.value;
	a=ScoreOcx1.OpenCom(addr, 0);
	if(a==0){
		var addr2 = 1;
// 		addr2=textfield2.value;
		var a1=ScoreOcx1.CallThank (addr2);
	}else {
		alert("未找到串口，请检查评价器链接是否正常")	;
	}
}
</script>
<SCRIPT   LANGUAGE="javascript"   FOR="ScoreOcx1"   EVENT="OnScore(a,b,c)">
// 		alert("地址:" + a +"评价值111"+ b);
		var eval = b;
		var recordId = '${recordId}';
		AppUtil.ajaxProgress({
			url : "newCallController.do?evaluate",
			params : {
				recordId : recordId,
				eval : eval
			},
			callback : function(resultJson) {
				ScoreOcx1.CloseCom();
			  	parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.closeLayer();
			}
		});
</SCRIPT>
<script language="javascript" event="doData(Key)" for="PJAOcx">
		var eval = Key;
		var recordId = '${recordId}';
		AppUtil.ajaxProgress({
			url : "newCallController.do?evaluate",
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
				AppUtil.closeLayer();
			}
		});
</script>
<!-- <script language="javascript" event="OnAfterGWQ_StartEvaluator(ErrorCode,EvaluatorLevel)" for="GWQ">
		var errorCode = ErrorCode;
		var evaluatorLevel = EvaluatorLevel;
		var eval;
		var recordId = '${recordId}';
		if(errorCode==0){
			if(evaluatorLevel==1)
				eval = 3;
			if(evaluatorLevel==2)
				eval = 2;
			if(evaluatorLevel==3)
				eval = 1;
			if(evaluatorLevel==4)
				eval = 0;
			AppUtil.ajaxProgress({
				url : "newCallController.do?evaluate",
				params : {
					recordId : recordId,
					eval : eval
				},
				callback : function(resultJson) {
				  	parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});

					AppUtil.closeLayer();
				}
			});
		}else if(errorCode==-2){
		  	parent.art.dialog({
				content: "未评价，已超时",
				icon:"error",
				time:3,
				ok: true
			});
		}else{
		  	parent.art.dialog({
				content: "失败，返回错误码为"+errorCode,
				icon:"error",
				time:3,
				ok: true
			});
		}
</script> -->
<script language="javascript" event="OnAfterGWQ_StartGoodBadEvaluator(ErrorCode,UserJson)" for="GWQ">
	var recordId = '${recordId}';
	if(0==ErrorCode){
		var obj = JSON.parse(UserJson);
		AppUtil.ajaxProgress({
			url : "newCallController.do?evaluate",
			params : {
				recordId : recordId,
				UserJson : UserJson
			},
			callback : function(resultJson) {
			  	parent.art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.closeLayer();
			}
		});
	}else if(ErrorCode==-2){
	  	parent.art.dialog({
			content: "未评价，已超时",
			icon:"error",
			time:3,
			ok: true
		});
	}else{
	  	parent.art.dialog({
			content: "失败，返回错误码为"+ErrorCode,
			icon:"error",
			time:3,
			ok: true
		});
	}
</script>

<script>
	function ShowURL() {
		var recordId = '${recordId}';
		var basePath = '${basePath}';
		var url = basePath +"/newCallController.do?proEvaluateView&recordId=" + recordId;
		ZCShowHtmlAB(url,1);
	}
	/**
	 * 签名版屏幕网页推送
	 * @param url 推送地址
	 * @constructor
	 */
	function ZCShowHtmlAB(url,playSound) {
		var data = JSON.stringify({ 'function': 'ZCShowHtmlAB', 'url': url,'playSound':playSound });
		connected ? sendMessage(data) : ConnectServer(sendMessage, data)
	}

	function produceMessage(jsonObj) {
		if (jsonObj) {
			if (jsonObj.functionName && jsonObj.functionName == 'ZCCloseHtmlAB') {
				parent.art.dialog({
					content: "评价成功",
					icon:"succeed",
					time:3,
					ok: true
				});
				AppUtil.closeLayer();
			}
		}
	}

</script>

</head>

<body style="margin:0px;background-color: #f7f7f7;text-align: center;">
<br>
	<p style="font-size: 20px;">请选择发起评价？</p>
	<div class="eve_buttons" style="text-align: center;">
	    <input value="按键评价器" type="submit" onclick="evaluate();" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
	    <input value="按键评价器2" type="submit" onclick="SCOevaluate();" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
	    <input value="液晶屏评价器" type="submit" onclick="GWQevaluate();" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
	    <input value="液晶屏评价器2" type="submit" onclick="ShowURL();" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
	    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
	</div>
</body>
<OBJECT id="UDS_Tablet" classid="clsid:FD90137E-44E4-439C-90C5-A9F58F9E91BC" style="display:none"></OBJECT>
<OBJECT ID="ScoreOcx1"  WIDTH="0"  HEIGHT="0"  CLASSID="CLSID:A8C33162-4D7F-45BB-AD6C-5BFA88289320">
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

<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>

