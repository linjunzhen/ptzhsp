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
	<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
	<style>
		.layout-split-south{border-top-width:0px !important;}
		.eve_buttons{position: relative !important;}
        .colorOfChecked  input:checked+span{
			color: red;
		}

	</style>
	<script type="text/javascript">
        function doSelectRows(){
            var IS_ACCOUNT_OPEN =$("input[name='IS_ACCOUNT_OPEN']:checked").val();
            var IS_ENGRAVE_SEAL = $("input[name='IS_ENGRAVE_SEAL']:checked").val();
            var IS_FREE_ENGRAVE_SEAL = $("input[name='IS_FREE_ENGRAVE_SEAL']:checked").val();
            var ISNEEDSIGN ='1';
            var IS_PREAPPROVAL_PASS ='1';
            var ISSOCIALREGISTER = $("input[name='ISSOCIALREGISTER']:checked").val();
            var ISMEDICALREGISTER = $("input[name='ISMEDICALREGISTER']:checked").val();
            var ISFUNDSREGISTER = $("input[name='ISFUNDSREGISTER']:checked").val();
            var ISFIRSTAUDIT = '0';
            //var ISGETBILL ='0';
            var ISGETBILL = $("input[name='ISGETBILL']:checked").val();
            var ISEMAIL=$("input[name='ISEMAIL']:checked").val();
            var alertStr="";

            
            if(IS_FREE_ENGRAVE_SEAL=="1"){
                alertStr+="您选择免费刻制印章；</br>";
            }else{
                alertStr+="您选择不刻制印章，视为放弃免费刻制印章，需自费刻章，请确认；</br>";
            }
			if(ISGETBILL=="1"){
				alertStr+="您选择办理税务登记信息确认；</br>";
			}else{
				alertStr+="您选择不办理税务登记信息确认；</br>";
			}
            if(ISSOCIALREGISTER=="1"){
                alertStr+="您选择办理社会保险登记；</br>";
            }else{
                alertStr+="您选择不办理社会保险登记；</br>";
            }
			if(ISMEDICALREGISTER=="1"){
				alertStr+="您选择办理医疗保险登记；</br>";
			}else{
				alertStr+="您选择不办理医疗保险登记；</br>";
			}
			if(ISFUNDSREGISTER=="1"){
				alertStr+="您选择办理公积金登记；</br>";
			}else{
				alertStr+="您选择不办理公积金登记；</br>";
			}
            if(IS_ACCOUNT_OPEN=="1"){
                alertStr+="您选择进行银行预约开户；</br>";
            }else{
                alertStr+="您选择不进行银行预约开户；</br>";
            }
			if(ISEMAIL=="1"){
				alertStr+="您选择需要邮寄营业执照；</br>";
			}else{
				alertStr+="您选择不需要邮寄营业执照；</br>";
			}
            parent.art.dialog({
                content: alertStr,
                icon:"succeed",
                time:30,
                ok: function () {
                    art.dialog.data("itemInfo", {
                        IS_ACCOUNT_OPEN:IS_ACCOUNT_OPEN,
                        ISNEEDSIGN:ISNEEDSIGN,
                        IS_PREAPPROVAL_PASS:IS_PREAPPROVAL_PASS,
                        ISSOCIALREGISTER:ISSOCIALREGISTER,
						ISMEDICALREGISTER:ISMEDICALREGISTER,
						ISFUNDSREGISTER:ISFUNDSREGISTER,
                        ISFIRSTAUDIT:ISFIRSTAUDIT,
                        ISGETBILL:ISGETBILL,
                        ISEMAIL:ISEMAIL,
                        IS_ENGRAVE_SEAL:IS_ENGRAVE_SEAL,
						IS_FREE_ENGRAVE_SEAL:IS_FREE_ENGRAVE_SEAL
                    });
                    AppUtil.closeLayer();
                }
            });

        }
        function toUrl(url){
            window.open(url);
		}
		$(function() {
			//您是否选择免费刻制印章
			$("input[name='IS_FREE_ENGRAVE_SEAL']").on("click", function(event) {
				if(this.value==0){				
					art.dialog({
						content: "选择否，则视为放弃免费刻制印章，需自费刻章，请确认。",
						icon:"warning",
						ok: true
					});
				}
			});				
		});
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


									<div class="qlist" style="border: 0;">
										<div class="problem">1.您是否选择免费刻制印章？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="IS_FREE_ENGRAVE_SEAL" value="1"  checked="checked" />
												<span>是（固定材质和供应商，与营业执照一同办理）</span></label>
											<label></br>
												<input type="radio" name="IS_FREE_ENGRAVE_SEAL" value="0"  />
												<span>否（开业后自行选择刻制单位自费刻章）</span></label>
										</div>
									</div>


									<div class="qlist">
										<div class="problem">2.您是否办理税务登记信息确认？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISGETBILL" value="1"  />
												<span>是
												（1.选择是，视为同意同时办理营业执照以及税务登记信息确认，请按照税收征收管理办法规定按时报税，以免造成逾期申报，逾期申报造成的法律责任自行承担。
												2.如在该平台和电子税务局同时办理税务登记信息确认，则以电子税务局信息为准。）  </span></label></br>
											<label>
												<input type="radio" name="ISGETBILL" value="0"    checked="checked"/>
												<span>否（开业后自行办理）</span></label>
										</div>
									</div>
									<div class="qlist">
										<div class="problem">3.您是否选择办理社会保险登记？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISSOCIALREGISTER" value="1"  />
												<span>是</span></label>
											<label>
												<input type="radio" name="ISSOCIALREGISTER" value="0"  checked="checked"  />
												<span>否（开业后自行办理）</span></label>
										</div>
									</div>
									<div class="qlist">
										<div class="problem">4.您是否选择办理医疗保险登记？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISMEDICALREGISTER" value="1"  />
												<span>是</span></label>
											<label>
												<input type="radio" name="ISMEDICALREGISTER" value="0"  checked="checked"  />
												<span>否（开业后自行办理）</span></label>
										</div>
									</div>
									<div class="qlist">
										<div class="problem">5.您是否选择办理公积金登记？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISFUNDSREGISTER" value="1" />
												<span>是</span></label>
											<label>
												<input type="radio" name="ISFUNDSREGISTER" value="0" checked="checked"  />
												<span>否（开业后自行办理）</span></label>
										</div>
									</div>

									<div class="qlist" style="background: #f8f8f8">
										<div class="problem">6.您是否进行银行预约开户？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="IS_ACCOUNT_OPEN" value="1"   />
												<span>是</span></label>
											<label>
												<input type="radio" name="IS_ACCOUNT_OPEN" value="0"  checked="checked" />
												<span>否（开业后自行办理）</span></label>
										</div>
									</div>
									<div class="qlist" style="background: #f8f8f8">
										<div class="problem">7.您是否需要邮寄营业执照？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISEMAIL" value="1"  />
												<span>是</span></label>
											<label>
												<input type="radio" name="ISEMAIL" value="0"  checked="checked"  />
												<span>否（自行到窗口领取）</span></label>
										</div>
									</div>
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
