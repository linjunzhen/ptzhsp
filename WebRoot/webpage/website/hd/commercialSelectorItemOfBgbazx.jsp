<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<base href="<%=basePath%>">
	<meta name="renderer" content="webkit">
	<script type="text/javascript"
			src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>/webpage/common/css/common.css" />
	<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog,swfupload"></eve:resources>
	<style>
		.layout-split-south{border-top-width:0px !important;}
		.eve_buttons{position: relative !important;}
        .colorOfChecked  input:checked+span{
			color: red;
		}

	</style>
	<script type="text/javascript">
        function doSelectRows(){				
			var validateResult = $("#bgbazxForm").validationEngine("validate");
			if(validateResult){				
				var ISNEEDSIGN = $("input[name='ISNEEDSIGN']:checked").val();
				var ISFIRSTAUDIT = $("input[name='ISFIRSTAUDIT']:checked").val();
				var ISEMAIL=$("input[name='ISEMAIL']:checked").val();
				var ISJHYYZZ=$("input[name='ISJHYYZZ']:checked").val();
				var alertStr="";
				if(ISNEEDSIGN=="1"){
					alertStr+="您选择办理无纸化办理；</br>";
				}else{
					alertStr+="您选择不办理无纸化办理；</br>";
				}
				parent.art.dialog({
					content: alertStr,
					icon:"succeed",
					cancel:false,
					lock: true,
					ok: function () {
						art.dialog.data("itemInfo", {
							ISNEEDSIGN:ISNEEDSIGN,
							ISFIRSTAUDIT:ISFIRSTAUDIT,
							ISEMAIL:ISEMAIL,
							ISJHYYZZ:ISJHYYZZ
						});
						AppUtil.closeLayer();
					},
					close: function(){
						art.dialog.data("itemInfo", {
							ISNEEDSIGN:ISNEEDSIGN,
							ISFIRSTAUDIT:ISFIRSTAUDIT,
							ISEMAIL:ISEMAIL,
							ISJHYYZZ:ISJHYYZZ
						});
						AppUtil.closeLayer();
					}
				});
			} 
        }
        function toUrl(url){
            window.open(url);
		}
	</script>
</head>

<body  style="margin:0px;background-color: #f7f7f7;">

<div class="easyui-layout" fit="true" >
	<div data-options="region:'center',split:false" style="padding-left:100px;font-family: FangSong;font-size: 20px;">
		<div class="container" style=" overflow:hidden; margin-bottom:20px;background:url(webpage/website/zzhy/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
			<div class="net-detail">
				<div class="syj-tyys">
					<div class="hd syj-tabtitle" style="margin-left: 150px;">
						<ul>
							<li><a href="javasrcipt:void(0)">请选择需办理的业务</a></li>
						</ul>
					</div>
					<div class="bd">
						<div class="wzqy">
							<div class="top-tap">
								<div style="margin-left: 50px;" class="colorOfChecked">

								<form id="bgbazxForm" method="post">
									<div class="qlist">
										<div class="problem">1.您是否办理无纸化办理？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" class="validate[required]" name="ISNEEDSIGN" value="1" />
												<span>是</span></label>
											<label>
												<input type="radio" class="validate[required]" name="ISNEEDSIGN" value="0"  />
												<span>否</span></label>
										</div>
									</div>
									<div class="qlist" style="background: #f8f8f8">
										<div class="problem">2.您的经营项目是否属于前置审批项目或类金融行业？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" class="validate[required]" name="ISFIRSTAUDIT" value="1"  />
												<span>是（上传许可证及相关批文）</span></label></br>
											<label>
												<input type="radio" class="validate[required]" name="ISFIRSTAUDIT" value="0"  />
												<span>否（开业后自行办理）</span></label>
										</div>
									</div>
									<div class="qlist" style="background: #f8f8f8">
										<div class="problem">3.您是否需要邮寄营业执照？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" class="validate[required]" name="ISEMAIL" value="1"  />
												<span>是</span></label>
											<label>
												<input type="radio" class="validate[required]" name="ISEMAIL" value="0"  />
												<span>否</span></label>
										</div>
									</div>
									<div class="qlist" style="background: #f8f8f8">
										<div class="problem">4.您是否可缴回营业执照正、副本？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" class="validate[required]" name="ISJHYYZZ" value="1"  />
												<span>是（选择无纸化的，需在审核通过三日内寄回）</span></label></br>
											<label>
												<input type="radio" class="validate[required]" name="ISJHYYZZ" value="0"  />
												<span>否（需在国家信用信息公示平台刊登营业执照正、副本丢失公告）</span></label>
										</div>
									</div>
								</form>



								</div>


							</div>


						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<div data-options="region:'south',split:true,border:false"  >
		<div class="eve_buttons" style="text-align: center;">
			<input value="确认所选事项" type="button" onclick="doSelectRows();"  name="queding"
				   class="z-dlg-button z-dialog-okbutton aui_state_highlight" />

		</div>
	</div>
</div>


</body>
</html>
