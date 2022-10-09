<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>申请办理行政审批事项承诺书</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/maters/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
</head>

<body>
	<p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';margin-top:15.6pt;text-align:center"
		align="center">
		<span style="font-size:14.0pt;
font-family:方正小标宋简体">申请办理行政审批事项承诺书</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif'">&nbsp;</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';margin-top:7.8pt;margin-right:0cm;margin-bottom:7.8pt;
margin-left:0cm;line-height:28.0pt;">
		<span style="font-size:12.0pt;
font-family:宋体;">平潭综合实验区行政审批局：</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:24.0pt;
line-height:29.0pt;">
		<span><span><span
				style="font-size:12.0pt;
font-family:宋体;">本单位（人）于${promiseInfo.CNSJ}向贵局申请办理<u>${promiseInfo.SXMC}</u>事项审批，并按照规定要求递交了相关申请材料。现就有关事宜承诺如下：</span></span></span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:24.0pt;
line-height:29.0pt;">
		<span style="font-size:12.0pt;font-family:宋体;">1</span><span
			style="font-size:12.0pt;font-family:宋体;">、本单位（人）已认真学习了相关法律法规规章和规范性文件，已了解该项审批的有关要求，对有关规定的内容已经知晓和全面理解，承诺自身能够满足办理该事项的条件、标准和技术要求。</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:24.0pt;
line-height:29.0pt;">
		<span style="font-size:12.0pt;font-family:宋体;">2</span><span
			style="font-size:12.0pt;font-family:宋体;">、本单位（人）承诺完全按照贵局公布的申请材料要求和标准，提交了全部申请材料。</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:24.0pt;
line-height:29.0pt;">
		<span style="font-size:12.0pt;font-family:宋体;">3</span><span
			style="font-size:12.0pt;font-family:宋体;">、本单位（人）承诺所提供的申请材料实质内容均真实、合法、有效。</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:24.0pt;
line-height:29.0pt;">
		<span style="font-size:12.0pt;font-family:宋体;">4</span><span
			style="font-size:12.0pt;font-family:宋体;">、本单位（人）承诺所提供的申请材料内容完全一致。</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:24.0pt;
line-height:29.0pt;">
		<span style="font-size:12.0pt;font-family:宋体;">5</span><span
			style="font-size:12.0pt;font-family:宋体;">、本单位（人）承诺主动接受有关监管部门的监督和管理。</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:24.0pt;
line-height:29.0pt;">
		<span style="font-size:12.0pt;font-family:宋体;">6</span><span
			style="font-size:12.0pt;font-family:宋体;">、本单位（人）承诺对违反上述承诺的行为，愿意承担相应的法律责任。若因违反有关法律法规及承诺，被撤销行政审批决定，愿意进入信用信息公示平台并自行承担因此所造成的经济和法律后果。</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:23.2pt;
line-height:29.0pt;">
		<span style="font-size:12.0pt;font-family:宋体;letter-spacing:-.2pt;">7</span><span
			style="font-size:12.0pt;font-family:宋体;
letter-spacing:-.2pt;">、本单位（人）承诺以上陈述真实、有效，是本单位（人）真实意思的表示。</span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';text-indent:23.2pt;
line-height:29.0pt;">&nbsp;</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:right;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif';line-height:28.9pt;">
		<span><span><span
				style="font-size:12.0pt;font-family:宋体;"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span></span></span></span><span><span><span
				style="font-size:12.0pt;font-family:宋体;">承诺时间：${promiseInfo.CNSJ}</span></span></span>
	</p>
	<p
		style="margin:0cm;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-size:10.5pt;font-family:'Times New Roman','serif'">&nbsp;</p>
	<br>
	</p>
</body>
</html>
