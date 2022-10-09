<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="pragma" content="no-cache">
	<meta name="renderer" content="webkit">
	<title></title>
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/projectflow/project/css/eui.css">
</head>
<body>
	
	<div class="eui-xmgl">
		<div class="eui-xmgl-tab slideTxtBox">
			<!-- <a class="back" href="#">返回项目管理</a> -->
			<div class="hd">
				<ul>
					<li>项目基本信息</li>
					<li>项目材料</li>
					<li>审批结果信息</li>
				</ul>
			</div>
			<div class="bd">
				<!-- 项目基本信息 -->
				<div class="item">
					<div class="tit">项目基本信息</div>
					<table class="eui-xmgl-table">
						<colgroup>
							<col width="136">
							<col>
							<col width="136">
							<col>
						</colgroup>
						<tr>
							<th>项目编码</th>
							<td colspan="3">${info.PROJECT_CODE}</td>
						</tr>
						<tr>
							<th>项目名称</th>
							<td>${info.PROJECT_NAME}</td>
							<th>项目类型</th>
							<td>
								${info.PROJECT_TYPE}
							</td>
						</tr>
						<tr>
							<th>项目所属区划</th>
							<td>${info.DIVISION_CODE}</td>
							<th>投资项目目录编码</th>
							<td>${info.PERMIT_ITEM_CODE}</td>
						</tr>
						<tr>
							<th>建设性质</th>
							<td>
								${info.PROJECT_NATURE}
							</td>
							<th>国标行业</th>
							<td>${info.INDUSTRY}</td>
						</tr>
						<tr>
							<th>拟开工时间</th>
							<td>
								${info.START_YEAR}
							</td>
							<th>拟建成时间</th>
							<td>${info.END_YEAR}</td>
						</tr>
						<tr>
							<th>总投资（万元）</th>
							<td>${info.TOTAL_MONEY}</td>
							<th>申报时间</th>
							<td>${info.APPLY_DATE}</td>
						</tr>
						<tr>
							<th style="height: 80px;">总投资额为“0”时说明</th>
							<td colspan="3">${info.TOTAL_MONEY_EXPLAIN}</td>
						</tr>
						<tr>
							<th>建设地点</th>
							<td>平潭综合实验区</td>
							<th>建设地点详情</th>
							<td>${info.PLACE_CODE_DETAIL}</td>
						</tr>
						<tr>
							<th style="height: 80px;">建设规模及内容</th>
							<td colspan="3">${info.SCALE_CONTENT}</td>
						</tr>
						<tr>
							<th>项目属性</th>
							<td>${info.PROJECT_ATTRIBUTES}</td>
							<th>产业结构调整指导目录</th>
							<td>${info.INDUSTRY_STRUCTURE}</td>
						</tr>
						<tr>
							<th>土地获取方式</th>
							<td>${info.GET_LAND_MODE}</td>
							<th>项目投资来源</th>
							<td>${info.XMTZLY}</td>
						</tr>
						<tr>
							<th>土地是否带设计方案</th>
							<td>${info.TDSFDSJFA}</td>
							<th>是否完成区域评估</th>
							<td>${info.SFWCQYPG}</td>
						</tr>
						<tr>
							<th>工程分类</th>
							<td colspan="3">${info.GCFL}</td>
						</tr>
					</table>
					<!-- 外商投资信息 -->
					<c:if test="${info.FOREIGN_ABROAD_FLAG == 1}">
						<div class="tit">外商投资信息</div>
						<table class="eui-xmgl-table">
							<colgroup>
								<col width="136">
								<col>
								<col width="136">
								<col>
							</colgroup>
							<tr>
								<th>是否涉及国家安全</th>
								<td>${info.IS_COUNTRY_SECURITY}</td>
								<th>安全审查决定文号</th>
								<td>${info.SECURITY_APPROVAL_NUMBER}</td>
							</tr>
							<tr>
								<th>投资方式</th>
								<td>${info.INVESTMENT_MODE}</td>
								<th>总投资额折合美元(万元)</th>
								<td>${info.TOTAL_MONEY_DOLLAR}</td>
							</tr>
							<tr>
								<th>总投资额使用的汇率(人民币/美元)</th>
								<td>${info.TOTAL_MONEY_DOLLAR_RATE}</td>
								<th>项目资本金(万元)</th>
								<td>${info.PROJECT_CAPITAL_MONEY}</td>
							</tr>
							<tr>
								<th>项目资本金折合美元(万元)</th>
								<td>${info.PROJECT_CAPITAL_MONEY_DOLLAR}</td>
								<th>项目资本金使用的汇率(人民币/美元)</th>
								<td>${info.PROJECT_CAPITAL_MONEY_RATE}</td>
							</tr>
							<tr>
								<th>适用产业政策条目类型</th>
								<td>${info.INDUSTRIAL_POLICY_TYPE}</td>
								<th>适用产业政策条目</th>
								<td>${info.INDUSTRIAL_POLICY}</td>
							</tr>
							<tr>
								<th style="height: 80px;">其他投资方式需予以申报的情况</th>
								<td colspan="3">${info.OTHER_INVESTMENT_APPLY_INFO}</td>
							</tr>
							<tr>
								<th style="height: 80px;">提供交易双方情况</th>
								<td colspan="3">${info.TRANSACTION_BOTH_INFO}</td>
							</tr>
							<tr>
								<th style="height: 80px;">并购安排</th>
								<td colspan="3">${info.MERGER_PLAN}</td>
							</tr>
							<tr>
								<th style="height: 80px;">并购后经营方式及经营范围</th>
								<td colspan="3">${info.MERGER_MANAGEMENT_MODE_SCOPE}</td>
							</tr>
							<tr>
								<th>总建筑面积(平方米)</th>
								<td>${info.BUILT_AREA}</td>
								<th>总用地面积(平方米)</th>
								<td>${info.LAND_AREA}</td>
							</tr>
							<tr>
								<th>是否新增设备</th>
								<td>${info.IS_ADD_DEVICE}</td>
								<th>拟进口设备数量及金额</th>
								<td>${info.IMPORT_DEVICE_NUMBER_MONEY}</td>
							</tr>
						</table>
					</c:if>
					<!-- 境外投资信息 -->
					<c:if test="${info.FOREIGN_ABROAD_FLAG == 2}">
						<div class="tit">境外投资信息</div>
						<table class="eui-xmgl-table">
							<colgroup>
								<col width="136">
								<col>
								<col width="136">
								<col>
							</colgroup>
							<tr>
								<th>项目所在地</th>
								<td>${info.PROJECT_SITE}</td>
								<th>中方投资额(万元)</th>
								<td>${info.CHINA_TOTAL_MONEY}</td>
							</tr>
						</table>
					</c:if>
					<!-- 项目（法人）单位信息 -->
					<div class="tit">项目（法人）单位信息</div>
					<table class="eui-xmgl-table">
						<colgroup>
							<col width="136">
							<col>
							<col width="136">
							<col>
						</colgroup>
						<tr>
							<th>单位名称</th>
							<td>${lerepInfo.enterprise_name}</td>
							<th>单位类型</th>
							<td>${lerepInfo.dwlx}</td>
						</tr>
						<tr>
							<th>证照类型</th>
							<td>${lerepInfo.lerep_certtype}</td>
							<th>证照号码</th>
							<td>${lerepInfo.lerep_certno}</td>
						</tr>
						<tr>
							<th>联系人名称</th>
							<td>${lerepInfo.contact_name}</td>
							<th>联系电话</th>
							<td>${lerepInfo.contact_tel}</td>
						</tr>
						<tr>
							<th>联系人邮箱</th>
							<td>${lerepInfo.contact_email}</td>
							<th>注册地址</th>
							<td>${lerepInfo.enterprise_place}</td>
						</tr>
						<tr>
							<th>单位性质</th>
							<td>${lerepInfo.enterprise_nature}</td>
							<th>持股比例是否与资本金相同</th>
							<td>${lerepInfo.china_foreign_share_ratio}</td>
						</tr>
						<tr>
							<th>主要经营范围</th>
							<td>${lerepInfo.business_scope}</td>
							<th>联系手机</th>
							<td>${lerepInfo.contact_phone}</td>
						</tr>
						<tr>
							<th>传真</th>
							<td>${lerepInfo.contact_fax}</td>
							<th>通讯地址</th>
							<td>${lerepInfo.correspondence_address}</td>
						</tr>
					</table>
				</div>
				<!-- 项目材料 -->
				<div class="item">
					<div class="eui-xmgl-tab2">
						<div class="hd2">
							<ul>
								<li>共性材料</li>
								<li>申报材料</li>
								<li>流转公文</li>
								<li>许可证材料</li>
								<li>电子证照</li>
							</ul>
						</div>
						<div class="bd2">
							<!-- 共性材料 -->
								<div>
									<table class="eui-xmgl-table-zbr">
										<colgroup>
											<col width="80">
										</colgroup>
										<tr>
											<th>序号</th>
											<th>项目阶段</th>
											<th>材料名称</th>
											<th>提交时间</th>
										</tr>
										<c:forEach items="${generalityMaterList}" var="genMater" varStatus="varStatus">
											<tr>
												<td>${varStatus.index + 1}</td>
												<td>${genMater.CATEGORY_NAME}</td>
												<td>${genMater.FILE_NAME}</td>
												<td>${genMater.CREATE_TIME}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							<!-- 申报材料 -->
							<div>
								<table class="eui-xmgl-table-zbr">
									<colgroup>
										<col width="80">
									</colgroup>
									<tr>
										<th>序号</th>
										<th>项目阶段</th>
										<th>材料名称</th>
										<th>提交时间</th>
									</tr>
									<c:if test="${applyMaterList != null}">
										<c:forEach items="${applyMaterList}" var="applyMater" varStatus="varStatus">
											<tr>
												<td>${varStatus.index + 1}</td>
												<td>${stageMap.NAME}</td>
												<td>${applyMater.MATER_NAME}</td>
												<td>${applyMater.CREATE_TIME}</td>
											</tr>
										</c:forEach>
									</c:if>
									
								</table>
							</div>
							<!-- 流转公文 -->
							<div>
								<table class="eui-xmgl-table-zbr">
									<colgroup>
										<col width="80">
									</colgroup>
									<tr>
										<th>序号</th>
										<th>项目阶段</th>
										<th>材料名称</th>
										<th>提交时间</th>
									</tr>
									<c:if test="${flowMaterList != null}">
										<c:forEach items="${flowMaterList}" var="flowMater" varStatus="varStatus">
											<tr>
												<td>${varStatus.index + 1}</td>
												<td>${stageMap.NAME}</td>
												<td>${flowMater.FILE_NAME}</td>
												<td>${flowMater.CREATE_TIME}</td>
											</tr>
										</c:forEach>
									</c:if>
								</table>
		        			</div>
							<!-- 许可证材料 -->
							<div>
								<table class="eui-xmgl-table-zbr">
									<colgroup>
										<col width="80">
									</colgroup>
									<tr>
										<th>序号</th>
										<th>施工许可证号</th>
										<th>施工许可电子证照号</th>
										<th>提交时间</th>
									</tr>
									<c:if test="${licenceMaterList != null}">
										<c:forEach items="${licenceMaterList}" var="licenceMater" varStatus="varStatus">
											<tr>
												<td>${varStatus.index + 1}</td>
												<td>${licenceMater.CONSTRNUM}</td>
												<td>${licenceMater.CERT_NUM}</td>
												<td>${licenceMater.CREATE_TIME}</td>
											</tr>
										</c:forEach>
									</c:if>
								</table>
							</div>
							<!-- 电子证照 -->
							<div>
								<table class="eui-xmgl-table-zbr">
									<colgroup>
										<col width="80">
									</colgroup>
									<tr>
										<th>序号</th>
										<th>项目阶段</th>
										<th>材料名称</th>
										<th>提交时间</th>
									</tr>
									<c:forEach items="${electronicMaterList}" var="eleMater" varStatus="varStatus">
										<tr>
											<td>${varStatus.index + 1}</td>
											<td>工程建设施工许可</td>
											<td>${eleMater.CERTIFICATEIDENTIFIERFILENAME}</td>
											<td>${eleMater.CREATE_TIME}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!-- 审批结果信息 -->
				<div class="item">
					<div class="eui-xmgl-tab3">
						<div class="hd3">
							<ul>
								<e:for filterid="${info.TYPE_ID}" end="100" var="efor" dsid="1004">
									<li>${efor.NAME}</li>
								</e:for>
							</ul>
						</div>
						<div class="bd3">
							<!-- 立项用地规划许可阶段 -->
							<e:for filterid="${info.TYPE_ID}" end="100" var="efor" dsid="1004">
								<div>
									<table class="eui-xmgl-table-zbr scroll">
										<thead>
											<tr>
												<th width="10%">事项编号</th>
												<th width="10%">审批事项</th>
												<th width="60%">事项编码</th>
												<th width="10%">受理时间</th>
												<th width="10%">办理结果</th>
											</tr>
										</thead>
										<tbody>
											<e:for filterid="${efor.STAGE_ID}" end="100" var="efor1" dsid="1005">
												<tr>	
													<td width="10%">${efor1.EXE_ID}</td>
													<td width="10%">${efor1.S_ITEM_CODE}</td>
													<td width="60%"><a style="text-decoration:underline;color:green;" href="<%=path%>/executionController.do?goDetail&exeId=${efor1.EXE_ID}" target="_blank">${efor1.ITEM_NAME}</a></td>				
													<td width="10%">${efor1.SLSJ}</td>
													<td width="10%">
														<c:if test="${efor1.RUN_STATUS=='0'}"><font color='#ff4b4b'>草稿</font></c:if>
														<c:if test="${efor1.RUN_STATUS=='1'}"><font color='#0368ff'>正在办理</font></c:if>
														<c:if test="${efor1.RUN_STATUS=='2'}"><font >已办结(正常结束)</font></c:if>
														<c:if test="${efor1.RUN_STATUS=='3'}"><font >已办结(审核通过)</font></c:if>
														<c:if test="${efor1.RUN_STATUS=='4'}"><font >已办结(审核不通过)</font></c:if>
														<c:if test="${efor1.RUN_STATUS=='5'}"><font color='#ff4b4b'>已办结(退回)</font></c:if>
														<c:if test="${efor1.RUN_STATUS=='6'}"><font color='black'>强制结束</font></c:if>
														<c:if test="${efor1.RUN_STATUS=='7'}"><font color='#ff4b4b'>预审不通过</font></c:if>
													</td>
												 </tr>
											</e:for>
										</tbody>
									</table>
								</div>
							</e:for>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/projectflow/project/js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/projectflow/project/js/jquery.SuperSlide.2.1.3.js"></script>
	<script type="text/javascript">
		//选项卡切换
		jQuery(".slideTxtBox").slide({trigger:"click"});
		jQuery(".eui-xmgl-tab2").slide({titCell:".hd2 li",mainCell:".bd2",trigger:"click"});
		jQuery(".eui-xmgl-tab3").slide({titCell:".hd3 li",mainCell:".bd3",trigger:"click"});
	</script>
	
</body>
</html>