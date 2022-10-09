<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">

</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
		<tr>
			<td style="border-bottom: 2px solid #999"><span style="width: 100px;float:left;text-align:right;">申报号：</span></td><td style="border-bottom: 2px solid #999">${EXE_ID }</td>
			<td style="border-bottom: 2px solid #999"><span style="width: 100px;float:left;text-align:right;">邮件号：</span></td><td style="border-bottom: 2px solid #999">${mailNo }</td>
		</tr>
		<c:choose>
			<c:when test="${success=='F' }">				
				<tr>
					<td colspan="4" style="text-align: center;color: red;">${errorMsg }</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${tracelist}" var="trace" varStatus="varStatus">			
					<tr>
						<td><span style="width: 120px;float:left;text-align:right;">操作时间：</span>
						</td>
						<td colspan="3">${trace.acceptTime}</td>
					</tr>			
					<tr>
						<td><span style="width: 120px;float:left;text-align:right;">描述：</span>
						</td>
						<td colspan="3">${trace.remark}</td>
					</tr>		
					<%-- <tr>
						<td><span style="width: 120px;float:left;text-align:right;">省份：</span>
						</td>
						<td colspan="3">${trace.provName}</td>
					</tr>	
					<tr>
						<td><span style="width: 120px;float:left;text-align:right;">城市：</span>
						</td>
						<td colspan="3">${trace.cityName}</td>
					</tr> --%>
					<tr>
						<td style="border-bottom: 2px solid #999"><span style="width: 120px;float:left;text-align:right;">城市：</span>
						</td>
						<td colspan="3" style="border-bottom: 2px solid #999">${trace.acceptAddress}</td>
					</tr>
					<%-- <tr>
						<td style="border-bottom: 2px solid #999"><span style="width: 120px;float:left;text-align:right;">快递员名称：</span>
						</td>
						<td colspan="3" style="border-bottom: 2px solid #999">${trace.operator}</td>
					</tr> --%>
				</c:forEach>
			</c:otherwise>
		</c:choose>	
	</table>
</body>
