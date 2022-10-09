<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
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
            var industryId = $("input[name='INDUSTRY_ID']:checked").val();
              art.dialog.data("mainIndustry", {
						industryId:industryId,
                    });
               AppUtil.closeLayer();
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
				<div class="syj-tyys" style="margin-left: 350px;" >
					<div class="hd syj-tabtitle" style="margin-left: 150px;">
						<ul>
							<li><a href="javasrcipt:void(0)">请选择需办理的主行业</a></li>
						</ul>
					</div>
					<div class="bd">
						<div class="wzqy">
							<div class="top-tap">
								<div style="margin-left: 50px;" class="colorOfChecked">
									<div class="qlist">
                                <ul>
										<e:for filterid="0" end="100" var="efor" dsid="235">
										 <li> <input type="radio" name="INDUSTRY_ID"
																				 value="${efor.INDUSTRY_ID}"     />
												 ${efor.INDUSTRY_NAME}
										</li>
										</e:for>
								</ul>
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
