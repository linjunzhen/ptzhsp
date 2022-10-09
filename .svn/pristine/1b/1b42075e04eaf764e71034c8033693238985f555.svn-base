<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">

<body>
	<div class="eui-main">
		<jsp:include page="head.jsp"  flush="true"></jsp:include>
		<!-- banner -->
		<jsp:include page="banner.jsp"  flush="true"></jsp:include>
		<!-- banner end -->
		
		<!-- 主体 -->
		<div class="eui-con">

			<div class="slideTxtBox eui-tab">
				<div class="hd">
					<ul class="eui-flex">
						<li>批前公示</li>
						<li>批后公示</li>
					</ul>
				</div>
				<div class="bd">
					<!-- 批前公示 -->
					<div class="">
						<div class="eui-table-info bjgs">
							<form id="projectListFormId" >
							<table>
								<tr>
									<th>项目编码</th>
									<td><input class="ipt" type="text" name="PROJECT_CODE" placeholder="请输入项目编码"/></td>
									<th>项目名称</th>
									<td><input class="ipt" type="text" name="PROJECT_NAME" placeholder="请输入项目名称"/></td>
									<th>事项名称</th>
									<td><input class="ipt" type="text" name="ITEM_NAME" placeholder="请输入事项名称"/></td>
								</tr>
								<tr>
									<th>项目类型</th>
									<td>
										<eve:eveselect clazz="eve-input1 whf_input field_width" dataParams="PROJECTTYPE"
													   dataInterface="dictionaryService.findDatasForSelect"
													   defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType" >
										</eve:eveselect>
									</td>
									<th>办理结果</th>
									<td>
										<select name="IS_OVER">
											<option value="">请选择办理结果</option>
											<option value="0">未办结</option>
											<option value="1">已办结</option>
										</select>
									</td>
									<th colspan="3">
										<a class="eui-btn round" onclick="currentPage(1);resetTotal()">查询</a>
										<a class="eui-btn round lightblue"  onclick="restForm('projectListFormId');">重置</a>
									</th>
								</tr>
							</table>
							</form>
						</div>

						<div class="eui-index-bjgs in">
							<table id="wraper">

							</table>

							<div class="eui-page round pager clearfix" style="margin: 50px 190px -20px;" id="pager">

							</div>
						</div>
					</div>
					<!-- 批后公示 -->
					<div class="">

					</div>
				</div>
			</div>

		</div>
		<!-- 主体 end -->
		
		<!-- 底部 -->
		<div class="eui-footer">
			<iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
		</div>
		<!-- 底部 end -->
	</div>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/Select.css">
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/Select.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
	<script type="text/javascript">
		$(function() {
			// banner切换
			jQuery(".slideBox").slide({titCell:".hd ul",mainCell:".bd ul",effect:"fold",autoPlay:true,autoPage:true,interTime:4000});
			// 选项卡切换
			jQuery(".slideTxtBox").slide({targetCell:".more a",delayTime:0,triggerTime: 0,trigger:"click"});
			//下拉框
			//$('select').selectMatch();
		});
	</script>


	<!--分页开始-->
	<input name="totalData"  hidden="hidden" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/page.css">
	<script src="<%=basePath%>/webpage/website/project/js/jquery.z-pager.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$("#pager").zPager({
			pageData:5,
			htmlBox: $('#wraper'),
			btnShow: true,
			ajaxSetData: false
		});

		function currentPage(currentPage){
			var projectCode=$("input[name='PROJECT_CODE']").val();
			var itemName=$("input[name='ITEM_NAME']").val();
			var projectName=$("input[name='PROJECT_NAME']").val();
			var projectType=$("select[name='PROJECT_TYPE']").find("option:selected").val();
			var isOver=$("select[name='IS_OVER']").find("option:selected").val();
			if(typeof (projectType)=='undefined'||projectType=='undefined'){
				projectType="";
			}
			if(typeof (isOver)=='undefined'||isOver=='undefined'){
				isOver="";
			}
			var start=(currentPage-1)*5;
			var url= '${webRoot}/projectWebsiteController.do?findContentForPage&start='+start+
					'&paras='+projectCode+':'+projectName+':'+itemName+':'+projectType+':'+isOver+':1&limit=5&dsid=238';
			url=encodeURI(url);
			$.ajax({
				url: url,
				type: 'post',
				async: false,//此处必须是同步
				dataType: 'json',
				success: function (data) {
					var result=JSON.parse(data.jsonString);
					var total=result.total;
					$("input[name='totalData']").val(total)
					var rows=result.data;
					var html="\t<tr>\n" +
							"\t\t\t\t\t\t\t\t\t<th width=\"25%\">项目代码</th>\n" +
							"\t\t\t\t\t\t\t\t\t<th align=\"left\">项目名称</th>\n" +
							"\t\t\t\t\t\t\t\t\t<th width=\"16%\">项目类型</th>\n" +
							"\t\t\t\t\t\t\t\t\t<th width=\"12%\">办理结果</th>\n" +
							"\t\t\t\t\t\t\t\t</tr>";
					for(var i=0; i<rows.length; i++){
						var PROJECT_CODE=rows[i].PROJECT_CODE;
						var PROJECT_NAME=rows[i].PROJECT_NAME;
						var PROJECT_TYPE=rows[i].DIC_NAME;
						var id=rows[i].ID;
						var XMSFWQBJ=rows[i].XMSFWQBJ;
						var runStatus=rows[i].RUN_STATUS;
						if(runStatus=='2'){
							runStatus="办结";
						}else{
							runStatus="办理中";
						}
						var projectInfoDetailUrl='${webRoot}/projectWebsiteController.do?projectInfoDetail&id='+id;
						html=html+"<tr onclick=\"toUrl('"+projectInfoDetailUrl +"')\">"+
								"<td align=\"center\">"+PROJECT_CODE+"</td>\n" +
								"\t\t\t\t\t\t\t\t\t<td>"+PROJECT_NAME+"</td>\n" +
								"\t\t\t\t\t\t\t\t\t<td align=\"center\">"+PROJECT_TYPE+"</td>\n" +
								"\t\t\t\t\t\t\t\t\t<td align=\"center\">"+runStatus+"</td>\n" +
								"</tr>";
					}
					$("#wraper").html(html);
				}
			});
			/*
                触发页码数位置： Page/js/jquery.z-pager.js 64行
            */
			console.log("当前页码数：" + currentPage);
		}
		function settotal(){
			var projectCode=$("input[name='PROJECT_CODE']").val();
			var itemName=$("input[name='ITEM_NAME']").val();
			var projectName=$("input[name='PROJECT_NAME']").val();
			var projectType=$("select[name='PROJECT_TYPE']").find("option:selected").val();
			var isOver=$("select[name='IS_OVER']").find("option:selected").val();
			if(typeof (projectType)=='undefined'||projectType=='undefined'){
				projectType="";
			}
			if(typeof (isOver)=='undefined'||isOver=='undefined'){
				isOver="";
			}
			var url= '${webRoot}/projectWebsiteController.do?findContentForPage&start=0'+
					'&paras='+projectCode+':'+projectName+':'+itemName+':'+projectType+':'+isOver+':1&limit=5&dsid=238';
			url=encodeURI(url);
			$.ajax({
				url: url,
				type: 'post',
				async: false,//此处必须是同步
				dataType: 'json',
				success: function (data) {
					var result=JSON.parse(data.jsonString);
					var total=result.total;
					$("input[name='totalData']").val(total)
				}
			});
		}
		function resetTotal() {
			$("#pager").zPager({

			});
		}
	</script>
	<!--分页结束-->

<script type="text/javascript" >
	function  restForm(searchFormName){
		$("#"+searchFormName)[0].reset();
	}
</script>
</body>
</html>