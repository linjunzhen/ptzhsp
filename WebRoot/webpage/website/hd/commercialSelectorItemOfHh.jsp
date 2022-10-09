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
            var IS_ACCOUNT_OPEN ='0';
            var ISNEEDSIGN ='0';
            var IS_PREAPPROVAL_PASS ='1';
            var ISSOCIALREGISTER = $("input[name='ISSOCIALREGISTER']:checked").val();
            var ISMEDICALREGISTER = $("input[name='ISMEDICALREGISTER']:checked").val();
            var ISFUNDSREGISTER = $("input[name='ISFUNDSREGISTER']:checked").val();
            var ISFIRSTAUDIT = '0';
            var ISGETBILL ='0';
            var ISEMAIL=$("input[name='ISEMAIL']:checked").val();
            var alertStr="";

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
                        ISEMAIL:ISEMAIL
                    });
                    AppUtil.closeLayer();
                }
            });

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

									<div class="qlist">
										<div class="problem">1.您是否选择办理社会保险登记？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISSOCIALREGISTER" value="1"   checked="checked"  />
												<span>是</span></label>
											<label>
												<input type="radio" name="ISSOCIALREGISTER" value="0"  />
												<span>否（开业后自行办理）</span></label>
										</div>
									</div>
									<div class="qlist">
										<div class="problem">2.您是否选择办理医疗保险登记？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISMEDICALREGISTER" value="1"   checked="checked"  />
												<span>是（必须同时办理税务登记）</span></label>
											<label>
												<input type="radio" name="ISMEDICALREGISTER" value="0"  />
												<span>否</span></label>
										</div>
									</div>
									<div class="qlist">
										<div class="problem">3.您是否选择办理公积金登记？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISFUNDSREGISTER" value="1"   checked="checked"  />
												<span>是</span></label>
											<label>
												<input type="radio" name="ISFUNDSREGISTER" value="0"  />
												<span>否</span></label>
										</div>
									</div>

									<div class="qlist" style="background: #f8f8f8">
										<div class="problem">4.您是否需要邮寄营业执照？</div>
										<div class="key">
											<label style="margin-right: 60px;">
												<input type="radio" name="ISEMAIL" value="1"  />
												<span>是</span></label>
											<label>
												<input type="radio" name="ISEMAIL" value="0"  checked="checked"  />
												<span>否（开业后自行办理）</span></label>
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
