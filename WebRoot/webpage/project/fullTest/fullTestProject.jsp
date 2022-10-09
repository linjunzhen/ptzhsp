<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="pragma" content="no-cache">
	<meta name="renderer" content="webkit">
	<title></title>
</head>
<body>
<script type="text/javascript" src="<%=path%>/webpage/project/fullTest/js/jquery.min.js"></script>
<eve:resources loadres="easyui,artdialog"></eve:resources>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/common/dynamic.jsp"></script>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	  href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	  href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	  href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/project/fullTest/css/eui.css">
<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css">

<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<jsp:include page="head.jsp"></jsp:include>
	<script type="text/javascript">
		var userType = '${sessionScope.curLoginMember.USER_TYPE}';
		$(function() {
			AppUtil.initWindowForm("xmdjDetailForm", function(form, valid) {
				if (valid) {
					$('#xmdjDetailForm').find('input,textarea').prop("disabled", false);
					$('#xmdjDetailForm').find('select').prop("disabled", false);
					
					var chbglxValue = '';
					$("[name='CHBGLX']:checked").each(function(i){
						chbglxValue += $(this).val() + ",";
					});
					chbglxValue = chbglxValue.substr(0, chbglxValue.length-1);
					$("[name='chbglxValue']").val(chbglxValue);
					var formData = $("#xmdjDetailForm").serialize();
					var url = $("#xmdjDetailForm").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							console.log(resultJson);
							if (resultJson.success) {
								art.dialog({
									content : resultJson.msg,
									icon : "succeed",
									lock : true,
									ok:function(){
										
									}
								});
							} else {
								parent.art.dialog({
									content: resultJson.msg,
									icon:"error",
									ok: true
								});
							}
						}
					});
				}
			}, "T_PROJECT_FULLTEST");
		});
		$().ready(function() {
			var projectCode = '${project_code}';
			if(projectCode!=null){
				loadTzxmxxData(projectCode);
			}
			$("input[name='CONTRIBUTION_INFO']").val('${projectApply.contribution_info}');
			//清除前后空格
			$("input,textarea").on('blur', function(event) {
				$(this).val(trim($(this).val()));
			});
		});

		//清除前后空格
		function trim(str){
			return str.replace(/(^\s*)|(\s*$)/g,"");
		}
		function loadTzxmxxData(code){
			var layload = layer.load('正在提交校验中…');
			$.post("projectApplyController.do?loadTzxmxxData",{
						projectCode:code},
					function(responseText, status, xhr) {
						layer.close(layload);
						var resultJson = $.parseJSON(responseText);
						if (resultJson.result) {
							for(var key in resultJson.datas){
								$("[name='"+key.toUpperCase()+"']").prop("readonly", true);
								$("[name='"+key.toUpperCase()+"']").prop("disabled", "disabled");
								if(key=='industry'){
									var typeCode = resultJson.datas[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode,path:"4028819d51cc6f280151cde6a3f00027"},
											function(responseText1, status, xhr) {
												var resultJson1 = $.parseJSON(responseText1);
												if(null!=resultJson1 && null!=resultJson1.TYPE_CODE && ''!=resultJson1.TYPE_CODE){
													$("#industry").html('<option value="'+resultJson1.TYPE_CODE+'" selected="selected">'+resultJson1.TYPE_NAME+'</option>')
												} else{
													$("#industry").html('<option value="'+typeCode+'" selected="selected">'+typeCode+'</option>')
												}
											});
								}else if(key=='place_code'){
									var typeCode2=resultJson.datas[key];
									$.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
											function(responseText2,status,xhr){
												var  resultJson2=$.parseJSON(responseText2);
												if(null!=resultJson2){
													$("#placeCode").html('<option value="'+resultJson2.TYPE_CODE+'" selected="selected">'+resultJson2.TYPE_NAME+'</option>')
												} else{
													$("#placeCode").html('<option value="'+typeCode2+'" selected="selected">'+typeCode2+'</option>')
												}
											});
								}else if(key=='industry_structure'){
									var typeCode3 = resultJson.datas[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode3},
											function(responseText3, status, xhr) {
												var resultJson3 = $.parseJSON(responseText3);
												if(null!=resultJson3){
													$("#industryStructure").html('<option value="'+resultJson3.TYPE_CODE+'" selected="selected">'+resultJson3.TYPE_NAME+'</option>')
												}else{
													$("#industryStructure").html('<option value="'+typeCode3+'" selected="selected">'+typeCode3+'</option>')
												}
											});
								}else if(key=='lerep_info'){
									var lerepInfoList = resultJson.datas[key];
									for(var j=0;j<lerepInfoList.length;j++){
										initXmdwxx(lerepInfoList[j],j);
									}
									$("[name='LEREP_INFO']").val(JSON.stringify(resultJson.datas[key]));
								}else if(key=='contribution_info'){
									$("[name='CONTRIBUTION_INFO']").val(JSON.stringify(resultJson.datas[key]));
								}else if(key=='get_land_mode'){
									if(null != resultJson.datas[key] && '' != resultJson.datas[key]){
										$("[name='GET_LAND_MODE']").val(resultJson.datas[key]);
									} else{
										$("[name='"+key.toUpperCase()+"']").prop("readonly", false);
										$("[name='"+key.toUpperCase()+"']").prop("disabled", "");
									}
								}else{
									$("[name='"+key.toUpperCase()+"']").val(resultJson.datas[key]);
								}
							}
						} else {
							art.dialog({
								content: "校验失败",
								icon:"error",
								ok: true
							});
						}
						$("[name='PROJECT_CODE']").prop("disabled", "");
						$("[name='PROJECT_NAME']").prop("disabled", "");
					}
			);
		};
		function initXmdwxx(dwxx,i){
			var enterpriseName = dwxx.enterprise_name;
			$("#enterprise_name").val(enterpriseName);
		}
	</script>
	<form id="xmdjDetailForm" method="post"
		  action="projectFullTestController.do?saveFullTestProject">
		<!-- 主体 -->
			<div class="eui-con">
				<div class="slideTxtBox eui-tab">
					<div class="hd" style="margin-bottom: 12px;">
						<ul class="eui-flex">
							<li>项目基本信息</li>
						</ul>
					</div>
					<div class="bd">
						<!-- 项目基本信息 -->
						<div id="projectInfo">
							<div class="eui-table-info">
								<input type="hidden" name="ID" value="${entityId}">
								<input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
								<input type="hidden" id="chbglxValue" name="chbglxValue"/>
								<input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
								<input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG" value="${projectApply.FOREIGN_ABROAD_FLAG}"/>
								<input type="hidden" id="theIndustry" name="THE_INDUSTRY" value="${projectApply.THE_INDUSTRY}"/>
								<input type="hidden" id="FLOW_CATE_ID" name="FLOW_CATE_ID" value="${projectApply.FLOW_CATE_ID}"/>
								<table>
									<colgroup>
										<col>
										<col width="200">
										<col>
										<col width="200">
									</colgroup>
									<tr>
										<th><i>*</i> 项目代码</th>
										<td colspan="3">
											<input class="eui-bh-check ipt validate[required]" type="text" name="PROJECT_CODE" id="PROJECT_CODE" readonly/>
										</td>
									</tr>
									<tr>
										<th><i>*</i> 项目名称</th>
										<td>
											<input type="text" class="ipt validate[required]" id="PROJECT_NAME"  maxlength="64"
													   name="PROJECT_NAME"  readonly/>
										</td>
										<th>项目类型</th>
										<td>
											<eve:eveselect clazz=" validate[] "  dataParams="PROJECTTYPE"
												dataInterface="dictionaryService.findDatasForSelect" 
												defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
											</eve:eveselect>
										</td>
									</tr>
									<tr>
										<th>项目所属区划</th>
										<td>
											<input type="text" maxlength="6"  class="ipt validate[]" name="DIVISION_CODE" />
										</td>
										<th> 投资项目目录编码</th>
										<td>
											<input type="text" maxlength="16" class="ipt validate[]" name="PERMIT_ITEM_CODE" />
										</td>
									</tr>
									<tr>
										<th>建设性质</th>
										<td>
											<eve:eveselect clazz="1 validate[]" dataParams="PROJECTNATURE"
														   dataInterface="dictionaryService.findDatasForSelect" 
														   defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="PROJECT_NATURE">
											</eve:eveselect>
										</td>
										<th>建设地点详情</th>
										<td>
											<input type="text" maxlength="500" class="ipt validate[]"
												   id="PLACE_CODE_DETAIL"  name="PLACE_CODE_DETAIL" />
										</td>
									</tr>
									<tr>
										<th>拟开工时间</th>
										<td>
											<input readonly="true" id="startYear" type="text" maxlength="4" class="ipt" name="START_YEAR"/>
										</td>
										<th>拟建成时间</th>
										<td>
											<input readonly="true" id="endYear" type="text" maxlength="4" class="ipt" name="END_YEAR" />
										</td>
									</tr>
									<tr>
										<th>总投资（万元）</th>
										<td>
											<input  id="totalMoney" type="text" maxlength="16" class="ipt validate[]"
													name="TOTAL_MONEY" />
										</td>
										<th>申报时间</th>
										<td>
											<input readonly="true" id="APPLY_DATE" type="text" maxlength="4" class="ipt" name="APPLY_DATE" />
										</td>
									</tr>
									<tr>
										<th>总投资额为0时说明</th>
										<td colspan="3">
											<textarea id="totalMoneyExplain" class="eui-input-w" rows="3" cols="200"
												name="TOTAL_MONEY_EXPLAIN" ></textarea>
										</td>
									</tr>
									<tr>
										<th>建设地点</th>
										<td>
											<div>
												<select id="placeCode" name="PLACE_CODE" class="ipt1 validate[]" style="width:97%;">
													<option value="">请选择建设地点</option>
												</select>
											</div>
										</td>
										<th>国标行业</th>
										<td>
											<div>
												<select id="industry" name="INDUSTRY" class="ipt1 validate[]" style="width:97%;">
													<option value="">请选择国标行业</option>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<th>建设规模及内容</th>
										<td colspan="3">
											<textarea id="SCALE_CONTENT" class="eui-input-w validate[]"
												rows="3" cols="200" name="SCALE_CONTENT" ></textarea>
										</td>
									</tr>
									<tr>
										<th>项目属性</th>
										<td>
											<eve:eveselect clazz="1  validate[]" dataParams="PROJECTATTRIBUTES"
													dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择投资项目属性" 
													name="PROJECT_ATTRIBUTES" id="PROJECT_ATTRIBUTES">
											</eve:eveselect>
										</td>
										</td>
										<th>产业结构指导目录</th>
										<td>
											<div>
												<select id="industryStructure" name="INDUSTRY_STRUCTURE" class="ipt1 validate[]" style="width:97%;">
													<option value="">请选择产业结构调整指导目录</option>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<th>土地获取方式</th>
										<td>
											<eve:eveselect clazz="1  validate[]" dataParams="getLandMode"
												dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择土地获取方式" 
												name="GET_LAND_MODE" id="getLandMode">
											</eve:eveselect>
										</td>
										<th>项目投资来源</th>
										<td>
											<eve:eveselect clazz="1  validate[]" dataParams="XMTZLY"
												dataInterface="dictionaryService.findDatasForSelect"
												defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
											</eve:eveselect>
										</td>
									</tr>
									<tr>
										<th>工程分类</th>
										<td>
											<eve:eveselect clazz="1  validate[]" dataParams="GCFL"
												dataInterface="dictionaryService.findDatasForSelect" 
												defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
											</eve:eveselect>
										</td>
										<th>是否完成区域评估</th>
										<td>
											<eve:eveselect clazz="1  validate[]" dataParams="SFWCQYPG"
												dataInterface="dictionaryService.findDatasForSelect"
												defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
											</eve:eveselect>
										</td>
									</tr>
									<tr>
										<th>土地是否带设计方案</th>
										<td colspan="3">
											<eve:eveselect clazz="1  validate[]" dataParams="TDSFDSJFA"
												dataInterface="dictionaryService.findDatasForSelect" 
												defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
											</eve:eveselect>
										</td>
									</tr>
									<tr>
										<th><i>*</i>测绘报告类型</th>
										<td colspan="3">
											<eve:excheckbox
													dataInterface="dictionaryService.findDatasForSelect"
													name="CHBGLX" width="650px;"
													clazz="checkbox validate[required]" dataParams="CHBGLX"
													value="">
											</eve:excheckbox>
										</td>
									</tr>
								</table>
							</div>
							<div class="eui-flex tc eui-sx-btn">
								<input value="提交" type="submit" class="eui-btn" />
							</div>
						</div>
					</div>
				</div>
			</div>
		<!-- 主体 end -->
	</form>
	<!-- 底部 -->
	<%-- <div class="eui-footer">
		<iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
	</div> --%>
	<!-- 底部 end -->

<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
<%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/Select.js"></script>--%>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
<script type="text/javascript">
	$(function() {
		// banner切换
		jQuery(".slideBox").slide({titCell:".hd ul",mainCell:".bd ul",effect:"fold",autoPlay:true,autoPage:true,interTime:4000});
		// 选项卡切换
		jQuery(".slideTxtBox").slide({targetCell:".more a",delayTime:0,triggerTime: 0,trigger:"click"});
	});
</script>

</body>
</html>