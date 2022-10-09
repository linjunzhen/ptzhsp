<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<title>我的预约</title>
		<%@include file="common.jsp"%>
		<link rel="stylesheet"
			href="${webRoot }/plug-in/assets/css/amazeui.min.css">
		<link rel="stylesheet" href="${webRoot }/plug-in/assets/css/app.css">
		<link rel="stylesheet" href="${webRoot }/plug-in/evecom/css/evecom.css">
		<script src="${webRoot}/plug-in/assets/js/jquery.min.js"></script>
		<script src="${webRoot}/plug-in/assets/js/handlebars.min.js"></script>
		<script src="${webRoot}/plug-in/assets/js/amazeui.min.js"></script>
		<script src="${webRoot}/plug-in/layer_mobile/layer.js"></script>
		<script src="${webRoot}/plug-in/evecom/mobilUtil.js"></script>
		<script src="http://101.com/JsBridge.js"></script>
		<script>
		$(function(){
			var userCode = $("#userCode").val();
			if(!userCode){
				var uc= Bridge.require("sdp.uc");
				uc.getCurrentUser({"force":true},{
					success:function(args){
						userCode = args.org_exinfo.org_user_code;
						$("#userCode").val(userCode);
						getMoreList();
					},
					fail:function(args){
						mobilUtil.layerAlert("获取用户信息失败，错误信息："+args);
					}
				});
			}else{
				getMoreList();
			}
		});
		function reLoad(){
			$("#start").val(1);
			$("#splist").empty();
			getMoreList();
		}
		function getMoreList(){
			var start = $("#start").val();
			var limit = $("#limit").val();
			mobilUtil.doPost({
				url:"${webRoot}/bespeakOnlineController/getPortalBespeakList.do",
				params:{userCode:$("#userCode").val(),start:start,limit:limit},
				callback : function(resultJson) {
					if(resultJson.success){
						$("#start").val(parseInt(start) + parseInt(limit));
						var obj = $.parseJSON(resultJson.jsonString);
						var html = "";
						var morehtml = "";
						for(var i = 0; i<obj.length;i++){
							var row = obj[i];
							var zt = "";
							if(row.IS_TAKE==0 && row.STATUS==1){
								zt = "<a class=\"ptwgsplistright\">取消预约>></a>";
							}else if(row.IS_TAKE==0 && row.STATUS==0){
								zt = "<a class=\"ptwgsplistright\" style=\"color: #6C6C6C\">已取消预约</a>";
							}else if(row.IS_TAKE==1 && row.STATUS==1){
								zt = "<a class=\"ptwgsplistright\" style=\"color: #6C6C6C\">已预约取票</a>";
							}else if(row.IS_TAKE==0 && row.STATUS==2){
								zt = "<a class=\"ptwgsplistright\">已作废</a>";
							}
							html += "<div onclick=\"dodel('"+row.RECORD_ID+"','"+row.IS_TAKE+"','"+row.STATUS+"','"+row.DATE_TIME+"','"+row.BEGIN_TIME+"','"+row.END_TIME+"')\" class=\"am-form-group clearfix\" ><a class=\"ptwgsplistleft\">"
								+row.BUSINESS_NAME+"</a>"+zt+"<a class=\"ptwgsplistsecond\">预约时间："+row.DATE_TIME+" "+row.BEGIN_TIME+"-"+row.END_TIME
								+"<span>"+(row.IS_TAKE==0?"未取票":"已取票") +"</span></a></div>";
						}
						$("#splist").append(html);
						if(limit>obj.length){
							morehtml+="<div class=\"am-form-group clearfix\"><a class=\"ptwgsplistmore\">无更多数据</a></div>";
						}else{
							morehtml+="<div class=\"am-form-group clearfix\"><a class=\"ptwgsplistmore\" onclick=\"getMoreList()\">查看更多</a></div>";
						}
						$("#splistmore").html(morehtml);
					}else{
						mobilUtil.layerAlert(resultJson.msg);
					}
					
				}
			});
		}
		function dodel(id,istake,status,dateTime,beginTime,endTime){
			if(istake==0 && status==1){
				mobilUtil.layerConfirm({
					content:"请确定取消预约时间：<br/>"+dateTime+" "+beginTime+"-"+endTime+"<br/><a>温馨提示：如您不能如约请及时取消，否则视为违约处理，多次违约您将会被限制预约。</a>",
					btn:['确定', '关闭'],
					yes:function(){
						mobilUtil.doPost({
							url:"${webRoot}/bespeakOnlineController/doPortalBespeakApplyDel.do",
							params:"id="+id+"&userCode="+$("#userCode").val(),
							callback : function(resultJson) {
								if(resultJson.success){
									mobilUtil.layerAlert(resultJson.msg);
									reLoad();
								}
							}
						});
					}
				});
			}
		}
		</script>
	</head>
	<body>
	<input type="hidden" id="start" name="start" value="1" />
	<input type="hidden" id="limit" name="limit" value="10" />
	<input type="hidden" value="${param.userCode }" id="userCode">

		<div class="govmain clearfix govtm10 am-form govForm clearfix" id="splist">
		</div>
		<div class="govmain clearfix govtm10 am-form govForm clearfix" id="splistmore">
		</div>
	</body>
</html>