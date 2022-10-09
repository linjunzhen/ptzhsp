<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<title>${param.departName}预约</title>
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
					},
					fail:function(args){
						mobilUtil.layerAlert("获取用户信息失败，错误信息："+args);
					}
				});
			}
		});
		function openTime(id){
			$(".bespeaktime").hide();
			$("."+id).show();
		}
		//返回预约时间点
		function afterTime(t,num){
			var time = new Date(t.replace(new RegExp("-","gm"),"/"));
		    time.setMinutes(time.getMinutes() + num);
		    return time.getHours()+":"+time.getMinutes();
		}
		function goBespeakApply(isOverTime,bespeakNumber,allowBespeakNumber,dateTime,beginTime,endTime,applyId){
			if(isOverTime==2){
				mobilUtil.layerConfirm({
					content:"请确定取消预约时间：<br/>"+dateTime+" "+beginTime+"-"+endTime+"<br/><a>温馨提示：如您不能如约请及时取消，否则视为违约处理，多次违约您将会被限制预约。</a>",
					btn:['确定', '关闭'],
					yes:function(){
					mobilUtil.doPost({
						url:"${webRoot}/bespeakOnlineController/doPortalBespeakApplyDel.do",
						params:"id="+applyId+"&userCode="+$("#userCode").val(),
						callback : function(resultJson) {
							if(resultJson.success){
								mobilUtil.layerConfirm({
									content:resultJson.msg,
									btn:['查看', '关闭'],
									yes:function(){
										window.location.href = "${webRoot}/bespeakOnlineController/goPortalBespeakList.do?userCode="+$("#userCode").val();
									},
									no:function(){
										location.reload();
									}
								});
							}else{
								mobilUtil.layerAlert(resultJson.msg);
							}
						}
					});
					}
				});

				
				
				return;
			}
			if(isOverTime == 0){
				mobilUtil.layerAlert("不可预约该时段");
				return;
			}
			if(bespeakNumber>=allowBespeakNumber){
				mobilUtil.layerAlert("该时段已预满");
				return;
			}
			var formdate = "STATUS=1&DATE_TIME="+dateTime+"&BEGIN_TIME="+beginTime+"&END_TIME="+endTime+"&DEPART_ID=${param.departId}&BUSINESS_CODE=${param.businessCode}&allowBespeakNumber="+allowBespeakNumber+"&userCode="+$("#userCode").val();
			//var stime=beginTiem.substring(0,3)+"30";
			var preTime=dateTime+" "+beginTime+":00";
			var stime=afterTime(preTime,30);
			mobilUtil.layerConfirm({
				content:"请确定预约时间：<br/>"+dateTime+" "+beginTime+"-"+endTime+"<br/><a>温馨提示：预约取号需实名，同一个人在一窗口一天只能预约一个号，预约成功后请依照短信提示在"+stime+"前至行政服务中心自助取号机取号  。</a>",
				//content:"请确定预约时间：<br/>"+dateTime+" "+beginTime+"-"+endTime+"<br/><a>温馨提示：预约取号需实名，同一个人在一窗口一天只能预约一个号，预约成功后请提前十分钟至行政服务中心一楼咨询台出示身份证进行现场号取号  。</a>",
				btn:['确认', '关闭'],
				yes:function(){
				mobilUtil.doPost({
					url:"${webRoot}/bespeakOnlineController/doPortalBespeakApply.do",
					params:formdate,
					callback : function(resultJson) {
						if(resultJson.success){
							mobilUtil.layerConfirm({
								content:resultJson.msg+"<br/><a>温馨提示：如您不能如约请及时取消，否则视为违约处理，多次违约您将会被限制预约。</a>",
								btn:['查看', '关闭'],
								yes:function(){
									window.location.href = "${webRoot}/bespeakOnlineController/goPortalBespeakList.do?userCode="+$("#userCode").val();
								},
								no:function(){
									location.reload();
								}
							});
						}else{
							mobilUtil.layerAlert(resultJson.msg);
						}
					}
				});
				}
			});
			
			
			//window.location.href = "${webRoot}/bespeakOnlineController/goPortalBespeakApply.do?dateTime="+dateTime+"&beginTime="+beginTime+"&endTime="+endTime+"&departId=${param.departId}&departName=${param.departName}&allowBespeakNumber="+allowBespeakNumber;
		}
		</script>
	</head>
	<body>
	<input type="hidden" value="${param.userCode }" id="userCode">
		 <div>
			<ul class="am-list ptwglist">
			<c:forEach var="list1" items="${list}" varStatus="i">
				<li onclick="openTime('${list1.W_ID }')">
					<a style="color: black">${list1.W_DATE }</a>
				</li>
				<c:forEach var="list2" items="${list1.bespeaklist }">
				<li onclick="goBespeakApply('${list2.isOverTime }','${list2.bespeakNumber }','${list2.ALLOW_BESPEAK_NUMBER }','${list1.W_DATE }','${list2.BEGIN_TIME }','${list2.END_TIME }','${list2.applyId }')" <c:if test="${i.index!=0 }">style="display: none;"</c:if> class="${list1.W_ID } bespeaktime" name="bespeaktime">
					<a>${list2.BEGIN_TIME }-${list2.END_TIME }<div>预约数：${list2.bespeakNumber}/${list2.ALLOW_BESPEAK_NUMBER }</div>
					<c:if test="${list2.isOverTime==1 && list2.bespeakNumber<list2.ALLOW_BESPEAK_NUMBER}">
					<span style="float: right;margin-top: -60px;">预约>></span>
					</c:if>
					<c:if test="${list2.isOverTime==0 || (list2.isOverTime!=2 && list2.bespeakNumber>=list2.ALLOW_BESPEAK_NUMBER)}">
						<span style="float: right;margin-top: -60px;color: red;">超时/约满</span>
					</c:if>
					<c:if test="${list2.isOverTime==2}">
						<span style="float: right;margin-top: -60px;color: red;">取消预约</span>
					</c:if>
					</a>
				</li>
				</c:forEach>
			</c:forEach>
			</ul>
		</div>

	</body>
</html>