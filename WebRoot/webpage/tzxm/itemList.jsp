<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() 
					+ ":" + request.getServerPort() + path;
	request.setAttribute("webRoot", basePath);
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<title>平潭综合实验区工程建设项目管理平台</title>
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newproject/css/public.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.min.js" ></script>
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
		<eve:resources loadres="apputil,easyui,validationegine,artdialog,swfupload,layer,json2"></eve:resources>
		<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
		<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/tzxm/css/public.css"/>
		<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/approveItem.css">
		<script type="text/javascript">
			var userType = '${sessionScope.curLoginMember.USER_TYPE}';
			$(function(){
				if(userType=='1'){
					alert("个人账号无法办理此事项，请登录法人账号!");
					location.href = "<%=path%>/userInfoController/mztLogin.do";
				}
				//文件上传
				var flowList = $("input[name='flowList']").val();
				
				$("a[name='reda']").click(function(e){
					
					var itemCode = $(this).attr("itemCode");
					//人防事项先判断是否填写了人防项目的基本信息，如果没有需先填写，再填写具体的事项
			        if("577012559GF14001" == itemCode  //工程-结合民用建筑修建防空地下室设计审核
			                 || "577012559GF14002" == itemCode  //工程-易地修建防空地下室审批
			                 || "00007GF08002" == itemCode) {  //工程-防空地下室竣工验收备案
			        	var tipsUrl = "webSiteController.do?rftips&projectCode=${projectCode}";
			        	$.ajax({
			        	      url       : "<%=path%>/webSiteController.do?checkRF",
			        	      type      : "post",
			        	      data      : {projectCode : "${projectCode}"},
			        	      dataType  : "json", 
			        	      async     : false,  //true异步，false同步
			        	      timeout   : 5000,
			        	      success   : function(data){
			        	    	  if(!data.hasBaseInfo){  //如果未填写人防项目的基本信息
		                              $.dialog.open(tipsUrl, {
		                                    title : "提示",
		                                    width : "600px",
		                                    height : "150px",
		                                    lock : true,
		                                    resize : false
		                                }, false);
		                              e.preventDefault();
		                          }
			        	      }
			            });
			         }
				});
			});
		</script>
		<script type="text/javascript">
			function checkProjectItem(categoryId, itemId, index, isKey){
				var isCheck = $("input[type='checkbox']").get(index).checked;
				var checkValue = 0;
				if(isCheck){
					checkValue = 1;
				}
				var isKeyValue = 0;
				if(isKey == 'true'){
					isKeyValue = 1;
				}
				var projectCode = document.getElementById("projectCode").value;
				var updateUrl = "userProjectController/updateProjectItem.do?categoryId="+categoryId+"&itemId="
								+itemId+"&checkValue="+checkValue+"&isKeyValue="+isKeyValue+"&projectCode="+projectCode;
				//var sourceCategoryId = document.getElementById("sourceCategoryId").value;
				$.ajax({
					url: updateUrl,
					async: false,
					success:function(data) {
						
					}
				});
				location.reload();
			}
			function openXxcjFileUploadDialog(categoryId, categoryName){
				//定义上传的人员的ID
				var uploadUserId = $("input[name='uploadUserId']").val();
				//定义上传的人员的NAME
				var uploadUserName = $("input[name='uploadUserName']").val();
				var materType="*.jpg;*.jpeg;*.png;*.docx;*.doc;*.xlsx;*.xls;*.rar;";
				art.dialog.open('fileTypeController.do?openWebStieUploadDialog&attachType=attachToImage&materType='
						+materType+'&uploadUserId='+uploadUserId+'&uploadUserName='+encodeURI(encodeURI(uploadUserName))+'&busTableName=JBPM6_EXECUTION', {
					title: "上传(附件)",
					width: "620px",
					height: "300px",
					lock: true,
					resize: false,
					close: function(){
						var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
						if(uploadedFileInfo){
							if(uploadedFileInfo.fileId){
								var fileType = 'gif,jpg,jpeg,bmp,png';
								var attachType = 'attach';
								if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
									attachType = "image";
								}
								var projectCode = document.getElementById("projectCode").value;
					        	//获取文件ID
				            	var fileId = uploadedFileInfo.fileId;
				        		//获取文件名称
				        		var fileName = uploadedFileInfo.fileName;
				        		var updateUrl = "userProjectController/uploadTZXMFile.do?fileName="+encodeURI(encodeURI(fileName))+"&projectCode="
										+projectCode+"&categoryId="+categoryId+"&categoryName="+encodeURI(encodeURI(categoryName))+"&fileId="+fileId
										+"&attachType="+attachType+"&fileType=1";
								$.ajax({
									url: updateUrl,
									async: false
								});
							}
						}
						art.dialog.removeData("uploadedFileInfo");
					}
				});
			}
		</script>
	</head>
	<body style="background: #f0f0f0;">
		<%--开始编写头部文件 --%>
		<jsp:include page="/webpage/website/newproject/head.jsp" />
		<%--结束编写头部文件 --%>
		<div class="eui-main">
			<div class="eui-content">
				<div class="eui-crumbs">
					<ul>  
						<li style="font-size:16px"><img src="<%=path%>/webpage/website/newproject/images/new/add.png" >当前位置：</li>
						<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a> > </li>
						<li><a href="<%=userCenter%>"> 我的项目</a> > </li>
						<li><a>事项列表</a> </li>
					</ul>
				</div>
				<div class="eui-Approval">
					<div class="eui-Approval-main">
						<%--开始引入公共隐藏域部分 --%>
						<jsp:include page="/webpage/website/applyforms/commonhidden.jsp" />
						<%--结束引入公共隐藏域部分 --%>
						<input type="hidden" id="projectCode" value="${projectCode}">
						<input type="hidden" id="sourceCategoryId" value="${sourceCategoryId}">
						<input type="hidden" id="flowList" name="flowList" value="${flowList}">
						<c:forEach var="flow" items="${flowList}">
							<span>${flow.CATEGORY_NAME}</span>
							<div style="width: 140px;float: right;" >
								<a href="javascript:void(0);" onclick="openXxcjFileUploadDialog('${flow.CATEGORY_ID}','${flow.CATEGORY_NAME}')">
			                   	<img src="webpage/tzxm/images/materUpload.png"  style="width:140px;height:36px;"/></a>
						    </div>
							<div class="eui-publicity-table">
								<table>
									<tr>
										<th>预计办理</th>
										<th>序号</th>
										<th>事项名称</th>
										<th>是否必办</th>
										<th>状态</th>
										<th>备注</th>
									</tr>
									<c:forEach var="item" items="${itemList}">
										<c:if test="${flow.CATEGORY_ID == item.PARENT_ID}">
											<tr <c:if test='${item.RN %2 == 0}'> bgcolor="#c7e7ff"</c:if>>
												<td width="7%">
													<input type="checkbox" name="subhandle" <c:if test="${item.isCheckItem == true}">checked="checked"</c:if> onclick="checkProjectItem('${flow.CATEGORY_ID}','${item.ITEM_ID}','${item.index}','${item.iskeyItem}');">
												</td>
												<td width="10%" style="text-align:center;">${item.RN}</td>
												<td width="41%">${item.ITEM_NAME}</td>
												<td width="7%">
													<c:if test="${item.iskeyItem == false}">
														${item.IS_KEY_ITEM}
													</c:if>
													<c:if test="${item.iskeyItem == true}">
														<font color='green'>${item.IS_KEY_ITEM}</font>
													</c:if>
												</td>
												<td width="20%">
													<c:if test="${item.isHandle == false}">
														<c:if test="${empty item.EXE_ID || item.FLOW_STATUS!='草稿'}">
															<font color='red'>${item.FLOW_STATUS}</font>&nbsp;&nbsp;&nbsp;
															<a class="td1" name="reda"  itemCode="${item.ITEM_CODE}" href="${webRoot}/webSiteController.do?applyItem&itemCode=${item.ITEM_CODE}&projectCode=${projectCode}&projectType=2">${item.handle}</a>	
														</c:if>
														<c:if test="${!empty item.EXE_ID && item.FLOW_STATUS=='草稿'}">	
															<font color='blue'>${item.FLOW_STATUS}</font>&nbsp;&nbsp;&nbsp;
															<a class="td1" href="${webRoot}/webSiteController.do?applyItem&itemCode=${item.ITEM_CODE}&exeId=${item.EXE_ID}&projectType=2">${item.handle}</a>
														</c:if>
													</c:if>
													<c:if test="${item.isHandle == true}">
														<font color='blue'>${item.FLOW_STATUS}</font>&nbsp;&nbsp;&nbsp;
														<a class="td1" href="${webRoot}/webSiteController.do?applyItem&itemCode=${item.ITEM_CODE}&exeId=${item.EXE_ID}&isQueryDetail=true&projectType=2&isFiled=${item.ISFILED}">${item.handle}</a>
													</c:if>
												</td>
												<td width="15%">${item.ITEM_REMARK}</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<%--开始编写尾部文件 --%>
			<jsp:include page="/webpage/website/newproject/foot.jsp" />
			<%--结束编写尾部文件 --%>
		</div>
		<!--内容结束-->
	</body>	
	<script>
		$(".eui-head li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".eui-nav li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
	</script>
</html>
