<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.ServiceItemService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ServiceItemService serviceItemService = (ServiceItemService)AppUtil.getBean("serviceItemService");
//2c93f48251a4c13a0151a4c13a1f0000  区党工委、管委会办公室
Map<String, Object> qdgwgwhbgs = serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13a1f0000");
request.setAttribute("qdgwgwhbgs", qdgwgwhbgs);
//2c93f48251a4c13a0151a4c13aed0001  区党群工作部
Map<String, Object> qdqgzb = serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13aed0001");
request.setAttribute("qdqgzb", qdqgzb);
//2c93f48251a4c13a0151a4c13b6b0002  区台湾工作部
Map<String, Object> qtwgzb = serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13b6b0002");
request.setAttribute("qtwgzb", qtwgzb);
//2c93f48251a4c13a0151a4c13f8f000a  区经济发展局
Map<String, Object> qjjfzj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13f8f000a");
request.setAttribute("qjjfzj",qjjfzj );
//2c93f48251a4c13a0151a4c13c420004  区环境与国土资源局
Map<String, Object> qhjygtzyj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13c420004");
request.setAttribute("qhjygtzyj",qhjygtzyj);
//2c93f48251a4c13a0151a4c13cc20005  区交通与建设局
Map<String, Object> qjtyjsj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13cc20005");
request.setAttribute("qjtyjsj",qjtyjsj);
//2c93f48251a4c13a0151a4c13d4e0006  区公安局
Map<String, Object> qgaj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13d4e0006");
request.setAttribute("qgaj",qgaj);
//2c93f48251a4c13a0151a4c13bcc0003  区财政金融局
Map<String, Object> qczjrj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13bcc0003");
request.setAttribute("qczjrj", qczjrj);
//2c93f48251a4c13a0151a4c13ded0007  区社会事业局
Map<String, Object> qshsyj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13ded0007");
request.setAttribute("qshsyj",qshsyj);
//2c93f48251a4c13a0151a4c13e750008  区市场监督管理局
Map<String, Object> qscjdlj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13e750008");
request.setAttribute("qscjdlj",qscjdlj );
//2c93f48251a4c13a0151a4c13ef80009  区综合执法局
Map<String, Object> qzhzfj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c13ef80009");
request.setAttribute("qzhzfj",qzhzfj);
//2c93f48251a4c13a0151a4c140c4000c  区行政审批局
Map<String, Object> qxzspj= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c140c4000c");
request.setAttribute("qxzspj",qxzspj);
//40288b9f530d91f8015310e880410019  区国税局
Map<String, Object> qgxj= serviceItemService.getXnjcTaotal("40288b9f530d91f8015310e880410019");
request.setAttribute("qgxj",qgxj);
//40288b9f53e626f30153eaa89fec01aa  区气象局
Map<String, Object> qqxj= serviceItemService.getXnjcTaotal("40288b9f53e626f30153eaa89fec01aa");
request.setAttribute("qqxj",qqxj);
//40288b9f53e626f30153eab076e701bd  区农村发展局
Map<String, Object> qncfzj= serviceItemService.getXnjcTaotal("40288b9f53e626f30153eab076e701bd");
request.setAttribute("qncfzj",qncfzj);
//40288b9f55f7d8c401560090cde70f0d  区教育局
Map<String, Object> qjyj= serviceItemService.getXnjcTaotal("40288b9f55f7d8c401560090cde70f0d");
request.setAttribute("qjyj",qjyj);
//40288b9f53e626f30153eab2275101c8  区卫生和计划生育局
Map<String, Object> qwshjhsyj= serviceItemService.getXnjcTaotal("40288b9f53e626f30153eab2275101c8");
request.setAttribute("qwshjhsyj",qwshjhsyj);
//40288b9f538c6d2301538dacd7ad0007  区规划局
Map<String, Object> qghj= serviceItemService.getXnjcTaotal("40288b9f538c6d2301538dacd7ad0007");
request.setAttribute("qghj",qghj);
//40288b9f53e626f30153eab0f4bd01bf  区旅游发展委员会
Map<String, Object> qlyfzj= serviceItemService.getXnjcTaotal("40288b9f53e626f30153eab0f4bd01bf");
request.setAttribute("qlyfzj",qlyfzj);
//40288b9f530d91f8015310e7b6fa0016  县烟草局
Map<String, Object> xycj= serviceItemService.getXnjcTaotal("40288b9f530d91f8015310e7b6fa0016");
request.setAttribute("xycj",xycj);
//2c90b38a57db50910157e0757aa32349  区消防支队
Map<String, Object> qxfzd= serviceItemService.getXnjcTaotal("2c90b38a57db50910157e0757aa32349");
request.setAttribute("qxfzd",qxfzd);
//2c90b38a5c48186d015c6258137449cd  区人民银行
Map<String, Object> qrmyh= serviceItemService.getXnjcTaotal("2c90b38a5c48186d015c6258137449cd");
request.setAttribute("qrmyh",qrmyh);
//区廉政办公室
Map<String, Object> qlzbgs= serviceItemService.getXnjcTaotal("2c93f48251a4c13a0151a4c14042000b");
request.setAttribute("qlzbgs",qlzbgs);
//2c90b38a5c10f1d3015c1451f35b2503  流水镇
Map<String, Object> lsz= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1451f35b2503");
request.setAttribute("lsz",lsz);
//2c90b38a5c10f1d3015c1524dd9c4809  苏澳镇
Map<String, Object> saz= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1524dd9c4809");
request.setAttribute("saz",saz);
//2c90b38a5c10f1d3015c15254bb84821  平原镇
Map<String, Object> pyz= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c15254bb84821");
request.setAttribute("pyz",pyz);
//2c90b38a5c10f1d3015c1525727a4833  潭城镇
Map<String, Object> tcz= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1525727a4833");
request.setAttribute("tcz",tcz);
//2c90b38a5c10f1d3015c1525a90b4840  澳前镇
Map<String, Object> aqz= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1525a90b4840");
request.setAttribute("aqz",aqz);
//2c90b38a5c10f1d3015c1525e9cd4842  北厝镇
Map<String, Object> bcz= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1525e9cd4842");
request.setAttribute("bcz",bcz);
//2c90b38a5c10f1d3015c1526331b4844  敖东镇
Map<String, Object> adz= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1526331b4844");
request.setAttribute("adz",adz);
//2c90b38a5c10f1d3015c15265e144872  屿头乡
Map<String, Object> ytx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c15265e144872");
request.setAttribute("ytx",ytx);
//2c90b38a5c10f1d3015c15268f934886  大练乡
Map<String, Object> dlx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c15268f934886");
request.setAttribute("dlx",dlx);
//2c90b38a5c10f1d3015c1526bc9f4888  白青乡
Map<String, Object> bqx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1526bc9f4888");
request.setAttribute("bqx",bqx);
//2c90b38a5c10f1d3015c1526f3204890  芦洋乡
Map<String, Object> lyx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1526f3204890");
request.setAttribute("lyx",lyx);
//2c90b38a5c10f1d3015c152731614894  中楼乡
Map<String, Object> zlx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c152731614894");
request.setAttribute("zlx",zlx);
//2c90b38a5c10f1d3015c152777154899  东庠乡
Map<String, Object> dxx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c152777154899");
request.setAttribute("dxx",dxx);
//2c90b38a5c10f1d3015c1527a2d8489f  岚城乡
Map<String, Object> lcx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1527a2d8489f");
request.setAttribute("lcx",lcx);
//2c90b38a5c10f1d3015c1527cb3048a1  南海乡
Map<String, Object> nhx= serviceItemService.getXnjcTaotal("2c90b38a5c10f1d3015c1527cb3048a1");
request.setAttribute("nhx",nhx);
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-网上办事大厅</title>
    <meta name="renderer" content="webkit">
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style.css">
	<script type="text/javascript" src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript" src="plug-in/base64-1.0/jquery.base64.js"></script>
	<script type="text/javascript" src="plug-in/echarts-2.2.7/build/dist/echarts.js"></script>
	<!--[if lte IE 6]> 
	<script src="plug-in/ddbelatedpng-0.8/DD_belatedPNG_0.0.8a.js" type="text/javascript"></script> 
	<script type="text/javascript"> 
	     DD_belatedPNG.fix('.logo img');  //根据所需背景的透明而定，不能直接写（*）
	</script> 
	<![endif]-->
	<eve:resources loadres="apputil,autocomplete"></eve:resources>
	
  </head>
  
  <body class="bsbody">
    <%--开始编写头部文件 --%>
    <jsp:include page="./head.jsp?currentNav=xnjc" />
    <%--结束编写头部文件 --%>
    
     <div class="container">
        <div class="current"><span>您现在所在的位置：</span><a href="webSiteController/view.do">首页</a> > <i>效能监察</i></div>
        <div class="bsMain1 clearfix">
            <div class="title4">区级行政许可和公共服务事项入驻情况：<span>数据统计</span></div>
              <table cellpadding="0" cellspacing="1" class="xntable">
                  <tr>
                      <th width="132px" rowspan="2">部门</th>
                      <th rowspan="2">事项总数</th>
                      <th rowspan="2">行政许可事项数</th>
                      <th rowspan="2">公共服务事项数</th>
                   <!--     <th colspan="5">事项服务星级</th> -->
                      <th colspan="2">网上办事开通比例（%）</th>
                  </tr>
                  <tr><!-- 
                      <th width="40px"><img src="webpage/website/common/images/star.png" /></th>
                      <th width="50px"><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /></th>
                      <th width="60px"><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /></th>
                      <th width="70px"><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /></th>
                      <th width="80px"><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /><img src="webpage/website/common/images/star.png" /></th>
                       --><th>行政许可</th>
                      <th>公共服务</th>
                  </tr>
                  <tr>
                      <td>区党工委、管委会办公室</td>
                      <td>${qdgwgwhbgs.alltotal}</td>
                      <td>${qdgwgwhbgs.xzxktotal}</td>
                      <td>${qdgwgwhbgs.ggfwtotal}</td>
                   <!--   <td>${qdgwgwhbgs.oneStar}</td>
                      <td>${qdgwgwhbgs.twoStar}</td>
                      <td>${qdgwgwhbgs.threeStar}</td>
                      <td>${qdgwgwhbgs.fourStar}</td>
                      <td>${qdgwgwhbgs.fiveStar}</td>  --> 
                      <td>${qdgwgwhbgs.xzxkpre}</td>
                      <td>${qdgwgwhbgs.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区党群工作部</td>
                      <td>${qdqgzb.alltotal}</td>
                      <td>${qdqgzb.xzxktotal}</td>
                      <td>${qdqgzb.ggfwtotal}</td>
                      <td>${qdqgzb.xzxkpre}</td>
                      <td>${qdqgzb.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区台湾工作部</td>
                      <td>${qtwgzb.alltotal}</td>
					  <td>${qtwgzb.xzxktotal}</td>
					  <td>${qtwgzb.ggfwtotal}</td>
					  <td>${qtwgzb.xzxkpre}</td>
					  <td>${qtwgzb.ggfwpre}</td>
                  </tr>
                   <tr>
                      <td>区旅游发展委员会</td>
                      <td>${qlyfzj.alltotal}</td>
                      <td>${qlyfzj.xzxktotal}</td>
                      <td>${qlyfzj.ggfwtotal}</td>
                      <td>${qlyfzj.xzxkpre}</td>
                      <td>${qlyfzj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区经济发展局</td>
                      <td>${qjjfzj.alltotal}</td>
                      <td>${qjjfzj.xzxktotal}</td>
                      <td>${qjjfzj.ggfwtotal}</td>
                      <td>${qjjfzj.xzxkpre}</td>
                      <td>${qjjfzj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区环境与国土资源局</td>
                      <td>${qhjygtzyj.alltotal}</td>
                      <td>${qhjygtzyj.xzxktotal}</td>
                      <td>${qhjygtzyj.ggfwtotal}</td>
                      <td>${qhjygtzyj.xzxkpre}</td>
                      <td>${qhjygtzyj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区交通与建设局</td>
                      <td>${qjtyjsj.alltotal}</td>
                      <td>${qjtyjsj.xzxktotal}</td>
                      <td>${qjtyjsj.ggfwtotal}</td>
                      <td>${qjtyjsj.xzxkpre}</td>
                      <td>${qjtyjsj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区公安局</td>
                      <td>${qgaj.alltotal}</td>
                      <td>${qgaj.xzxktotal}</td>
                      <td>${qgaj.ggfwtotal}</td>
                      <td>${qgaj.xzxkpre}</td>
                      <td>${qgaj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区财政金融局</td>
                      <td>${qczjrj.alltotal}</td>
                      <td>${qczjrj.xzxktotal}</td>
                      <td>${qczjrj.ggfwtotal}</td>
                      <td>${qczjrj.xzxkpre}</td>
                      <td>${qczjrj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区社会事业局</td>
                      <td>${qshsyj.alltotal}</td>
                      <td>${qshsyj.xzxktotal}</td>
                      <td>${qshsyj.ggfwtotal}</td>
                      <td>${qshsyj.xzxkpre}</td>
                      <td>${qshsyj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区市场监督管理局</td>
                      <td>${qscjdlj.alltotal}</td>
                      <td>${qscjdlj.xzxktotal}</td>
                      <td>${qscjdlj.ggfwtotal}</td>
                      <td>${qscjdlj.xzxkpre}</td>
                      <td>${qscjdlj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区综合执法局</td>
                      <td>${qzhzfj.alltotal}</td>
                      <td>${qzhzfj.xzxktotal}</td>
                      <td>${qzhzfj.ggfwtotal}</td>
                      <td>${qzhzfj.xzxkpre}</td>
                      <td>${qzhzfj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区农村发展局</td>
                      <td>${qncfzj.alltotal}</td>
                      <td>${qncfzj.xzxktotal}</td>
                      <td>${qncfzj.ggfwtotal}</td>
                      <td>${qncfzj.xzxkpre}</td>
                      <td>${qncfzj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区教育局</td>
                      <td>${qjyj.alltotal}</td>
                      <td>${qjyj.xzxktotal}</td>
                      <td>${qjyj.ggfwtotal}</td>
                      <td>${qjyj.xzxkpre}</td>
                      <td>${qjyj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区卫生和计划生育局</td>
                      <td>${qwshjhsyj.alltotal}</td>
                      <td>${qwshjhsyj.xzxktotal}</td>
                      <td>${qwshjhsyj.ggfwtotal}</td>
                      <td>${qwshjhsyj.xzxkpre}</td>
                      <td>${qwshjhsyj.ggfwpre}</td>
                  </tr>
                  <tr>
                      <td>区规划局</td>
                      <td>${qghj.alltotal}</td>
                      <td>${qghj.xzxktotal}</td>
                      <td>${qghj.ggfwtotal}</td>
                      <td>${qghj.xzxkpre}</td>
                      <td>${qghj.ggfwpre}</td>
                  </tr>
                 
<%--                  <tr>--%>
<%--                      <td>区廉政办公室</td>--%>
<%--                      <td>${qlzbgs.alltotal}</td>--%>
<%--                      <td>${qlzbgs.xzxktotal}</td>--%>
<%--                      <td>${qlzbgs.ggfwtotal}</td>--%>
<%--                      <td>${qlzbgs.xzxkpre}</td>--%>
<%--                      <td>${qlzbgs.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>区行政审批局</td>--%>
<%--                      <td>${qxzspj.alltotal}</td>--%>
<%--                      <td>${qxzspj.xzxktotal}</td>--%>
<%--                      <td>${qxzspj.ggfwtotal}</td>--%>
<%--                      <td>${qxzspj.xzxkpre}</td>--%>
<%--                      <td>${qxzspj.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>区国税局</td>--%>
<%--                      <td>${qgxj.alltotal}</td>--%>
<%--                      <td>${qgxj.xzxktotal}</td>--%>
<%--                      <td>${qgxj.ggfwtotal}</td>--%>
<%--                      <td>${qgxj.xzxkpre}</td>--%>
<%--                      <td>${qgxj.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>区气象局</td>--%>
<%--                      <td>${qqxj.alltotal}</td>--%>
<%--                      <td>${qqxj.xzxktotal}</td>--%>
<%--                      <td>${qqxj.ggfwtotal}</td>--%>
<%--                      <td>${qqxj.xzxkpre}</td>--%>
<%--                      <td>${qqxj.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>县烟草局</td>--%>
<%--                      <td>${xycj.alltotal}</td>--%>
<%--                      <td>${xycj.xzxktotal}</td>--%>
<%--                      <td>${xycj.ggfwtotal}</td>--%>
<%--                      <td>${xycj.xzxkpre}</td>--%>
<%--                      <td>${xycj.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>区消防支队</td>--%>
<%--                      <td>${qxfzd.alltotal}</td>--%>
<%--                      <td>${qxfzd.xzxktotal}</td>--%>
<%--                      <td>${qxfzd.ggfwtotal}</td>--%>
<%--                      <td>${qxfzd.xzxkpre}</td>--%>
<%--                      <td>${qxfzd.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>区人民银行</td>--%>
<%--                      <td>${qrmyh.alltotal}</td>--%>
<%--                      <td>${qrmyh.xzxktotal}</td>--%>
<%--                      <td>${qrmyh.ggfwtotal}</td>--%>
<%--                      <td>${qrmyh.xzxkpre}</td>--%>
<%--                      <td>${qrmyh.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>流水镇</td>--%>
<%--                      <td>${lsz.alltotal}</td>--%>
<%--                      <td>${lsz.xzxktotal}</td>--%>
<%--                      <td>${lsz.ggfwtotal}</td>--%>
<%--                      <td>${lsz.xzxkpre}</td>--%>
<%--                      <td>${lsz.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>苏澳镇</td>--%>
<%--                      <td>${saz.alltotal}</td>--%>
<%--                      <td>${saz.xzxktotal}</td>--%>
<%--                      <td>${saz.ggfwtotal}</td>--%>
<%--                      <td>${saz.xzxkpre}</td>--%>
<%--                      <td>${saz.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>平原镇</td>--%>
<%--                      <td>${pyz.alltotal}</td>--%>
<%--                      <td>${pyz.xzxktotal}</td>--%>
<%--                      <td>${pyz.ggfwtotal}</td>--%>
<%--                      <td>${pyz.xzxkpre}</td>--%>
<%--                      <td>${pyz.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>潭城镇</td>--%>
<%--                      <td>${tcz.alltotal}</td>--%>
<%--                      <td>${tcz.xzxktotal}</td>--%>
<%--                      <td>${tcz.ggfwtotal}</td>--%>
<%--                      <td>${tcz.xzxkpre}</td>--%>
<%--                      <td>${tcz.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>澳前村</td>--%>
<%--                      <td>${aqz.alltotal}</td>--%>
<%--                      <td>${aqz.xzxktotal}</td>--%>
<%--                      <td>${aqz.ggfwtotal}</td>--%>
<%--                      <td>${aqz.xzxkpre}</td>--%>
<%--                      <td>${aqz.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>北厝镇</td>--%>
<%--                      <td>${bcz.alltotal}</td>--%>
<%--                      <td>${bcz.xzxktotal}</td>--%>
<%--                      <td>${bcz.ggfwtotal}</td>--%>
<%--                      <td>${bcz.xzxkpre}</td>--%>
<%--                      <td>${bcz.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>敖东镇</td>--%>
<%--                      <td>${adz.alltotal}</td>--%>
<%--                      <td>${adz.xzxktotal}</td>--%>
<%--                      <td>${adz.ggfwtotal}</td>--%>
<%--                      <td>${adz.xzxkpre}</td>--%>
<%--                      <td>${adz.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>屿头乡</td>--%>
<%--                      <td>${ytx.alltotal}</td>--%>
<%--                      <td>${ytx.xzxktotal}</td>--%>
<%--                      <td>${ytx.ggfwtotal}</td>--%>
<%--                      <td>${ytx.xzxkpre}</td>--%>
<%--                      <td>${ytx.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>大练乡</td>--%>
<%--                      <td>${dlx.alltotal}</td>--%>
<%--                      <td>${dlx.xzxktotal}</td>--%>
<%--                      <td>${dlx.ggfwtotal}</td>--%>
<%--                      <td>${dlx.xzxkpre}</td>--%>
<%--                      <td>${dlx.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>白青乡</td>--%>
<%--                      <td>${bqx.alltotal}</td>--%>
<%--                      <td>${bqx.xzxktotal}</td>--%>
<%--                      <td>${bqx.ggfwtotal}</td>--%>
<%--                      <td>${bqx.xzxkpre}</td>--%>
<%--                      <td>${bqx.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>芦洋乡</td>--%>
<%--                      <td>${lyx.alltotal}</td>--%>
<%--                      <td>${lyx.xzxktotal}</td>--%>
<%--                      <td>${lyx.ggfwtotal}</td>--%>
<%--                      <td>${lyx.xzxkpre}</td>--%>
<%--                      <td>${lyx.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>中楼乡</td>--%>
<%--                      <td>${zlx.alltotal}</td>--%>
<%--                      <td>${zlx.xzxktotal}</td>--%>
<%--                      <td>${zlx.ggfwtotal}</td>--%>
<%--                      <td>${zlx.xzxkpre}</td>--%>
<%--                      <td>${zlx.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>大练乡</td>--%>
<%--                      <td>${dxx.alltotal}</td>--%>
<%--                      <td>${dxx.xzxktotal}</td>--%>
<%--                      <td>${dxx.ggfwtotal}</td>--%>
<%--                      <td>${dxx.xzxkpre}</td>--%>
<%--                      <td>${dxx.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>岚城乡</td>--%>
<%--                      <td>${lcx.alltotal}</td>--%>
<%--                      <td>${lcx.xzxktotal}</td>--%>
<%--                      <td>${lcx.ggfwtotal}</td>--%>
<%--                      <td>${lcx.xzxkpre}</td>--%>
<%--                      <td>${lcx.ggfwpre}</td>--%>
<%--                  </tr>--%>
<%--                  <tr>--%>
<%--                      <td>南海乡</td>--%>
<%--                      <td>${nhx.alltotal}</td>--%>
<%--                      <td>${nhx.xzxktotal}</td>--%>
<%--                      <td>${nhx.ggfwtotal}</td>--%>
<%--                      <td>${nhx.xzxkpre}</td>--%>
<%--                      <td>${nhx.ggfwpre}</td>--%>
<%--                  </tr>--%>
                  </table>
              <div class="title4">区级行政许可和公共服务事项入驻情况：<span>统计分析</span></div>
              <div class="datafx" style="width:940px;height: 400px;" id="main" ></div>
             <script type="text/javascript">
             // 路径配置
             require.config({
                 paths: {
                     echarts: 'plug-in/echarts-2.2.7/build/dist'
                 }
             });
          // 使用
             require(
                 [
                     'echarts',
                     'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                 ],
                 function (ec) {
                     // 基于准备好的dom，初始化echarts图表
                     var myChart = ec.init(document.getElementById('main')); 
                     myChart.showLoading();
                     var option = {
                   		 title: {
                                text: ''
                            },
                            tooltip : {
                                trigger: 'axis'
                            },
                            legend: {
                                data:['行政许可','公共服务']
                            },
                         xAxis : [
                             {
                                 type : 'category',
                                 axisLabel:{
                                     interval : 0,
                                     rotate :60,
                                    formatter:function(params){
                                        var newParamsName = "";// 最终拼接成的字符串
                                        var paramsNameNumber = params.length;// 实际标签的个数
                                        var provideNumber = 4;// 每行能显示的字的个数
                                        var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// 换行的话，需要显示几行，向上取整
                                        /**
                                         * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
                                         */
                                        // 条件等同于rowNumber>1
                                        if (paramsNameNumber > provideNumber) {
                                            /** 循环每一行,p表示行 */
                                            for (var p = 0; p < rowNumber; p++) {
                                                var tempStr = "";// 表示每一次截取的字符串
                                                var start = p * provideNumber;// 开始截取的位置
                                                var end = start + provideNumber;// 结束截取的位置
                                                // 此处特殊处理最后一行的索引值
                                                if (p == rowNumber - 1) {
                                                    // 最后一次不换行
                                                    tempStr = params.substring(start, paramsNameNumber);
                                                } else {
                                                    // 每一次拼接字符串并换行
                                                    tempStr = params.substring(start, end) + "\n";
                                                }
                                                newParamsName += tempStr;// 最终拼成的字符串
                                            }

                                        } else {
                                            // 将旧标签的值赋给新标签
                                            newParamsName = params;
                                        }
                                        //将最终的字符串返回
                                        return newParamsName
                                    }
                                },
                                data: [
                                     //2c93f48251a4c13a0151a4c13a1f0000  区党工委、管委会办公室
                                     //2c93f48251a4c13a0151a4c13aed0001  区党群工作部
                                     //2c93f48251a4c13a0151a4c13b6b0002  区台湾工作部
                                     //2c93f48251a4c13a0151a4c13f8f000a  区经济发展局
                                     //2c93f48251a4c13a0151a4c13c420004  区环境与国土资源局
                                     //2c93f48251a4c13a0151a4c13cc20005  区交通与建设局
                                     //2c93f48251a4c13a0151a4c13d4e0006  区公安局
                                     //2c93f48251a4c13a0151a4c13bcc0003  区财政金融局
                                     //2c93f48251a4c13a0151a4c13ded0007  区社会事业局
                                     //2c93f48251a4c13a0151a4c13e750008  区市场监督管理局
                                     //2c93f48251a4c13a0151a4c13ef80009  区综合执法局
                                     //2c93f48251a4c13a0151a4c140c4000c  区行政审批局
                                     //40288b9f530d91f8015310e880410019  区国税局
                                     //40288b9f53e626f30153eaa89fec01aa  区气象局
                                     //40288b9f53e626f30153eab076e701bd  区农村发展局
                                     //40288b9f55f7d8c401560090cde70f0d  区教育局
                                     //40288b9f53e626f30153eab2275101c8  区卫生和计划生育局
                                     //40288b9f538c6d2301538dacd7ad0007  区规划局
                                     //40288b9f53e626f30153eab0f4bd01bf  区旅游发展委员会
                                     //40288b9f530d91f8015310e7b6fa0016  县烟草局
                                     //2c90b38a57db50910157e0757aa32349  区消防支队
                                     //2c90b38a5c48186d015c6258137449cd  区人民银行
                                     //2c90b38a5c10f1d3015c1451f35b2503  流水镇
                                     //2c90b38a5c10f1d3015c1524dd9c4809  苏澳镇
                                     //2c90b38a5c10f1d3015c15254bb84821  平原镇
                                     //2c90b38a5c10f1d3015c1525727a4833  潭城镇
                                     //2c90b38a5c10f1d3015c1525a90b4840  澳前镇
                                     //2c90b38a5c10f1d3015c1525e9cd4842  北厝镇
                                     //2c90b38a5c10f1d3015c1526331b4844  敖东镇
                                     //2c90b38a5c10f1d3015c15265e144872  屿头乡
                                     //2c90b38a5c10f1d3015c15268f934886  大练乡
                                     //2c90b38a5c10f1d3015c1526bc9f4888  白青乡
                                     //2c90b38a5c10f1d3015c1526f3204890  芦洋乡
                                     //2c90b38a5c10f1d3015c152731614894  中楼乡
                                     //2c90b38a5c10f1d3015c152777154899  东庠乡
                                     //2c90b38a5c10f1d3015c1527a2d8489f  岚城乡
                                     //2c90b38a5c10f1d3015c1527cb3048a1  南海乡
                                       "区党工委、管委会办公室",
                                       "区党群工作部",
                                       "区台湾工作部",
                                       "区旅游发展委员会",
                                       "区经济发展局",
                                       "区环境与国土资源局",
                                       "区交通与建设局",
                                       "区公安局",
                                       "区财政金融局",
                                       "区社会事业局",
                                       "区市场监督管理局",
                                       "区综合执法局",
                                       "区农村发展局",
                                       "区教育局",
                                       "区卫生和计划生育局",
                                       "区规划局"]
                             }
                         ],
                         yAxis : [
                             {
                                 type : 'value'
                             }
                         ],
                         series: [{
                             name: '行政许可',
                             type: 'bar',
                             data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             markPoint: {  
                                 symbolOffset :['-33%',0],
                                 symbolSize:[40,40],
                                 data: [  
                                     {type: 'max', name: '最大值'},  
                                     {type: 'min', name: '最小值'}  
                                 ]  
                             },
                             markLine:{
                                 data: [{
                                            name: '平均值',
                                            type: 'average'
                                        }]
                             }
                             
                         },{
                             name: '公共服务',
                             type: 'bar',
                             data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                             markPoint: {  
                                 symbolOffset :['33%',0],
                                 symbolSize:[40,40],
                                 data: [  
                                     {type: 'max', name: '最大值'},  
                                     {type: 'min', name: '最小值'}  
                                 ]  
                             },
                             markLine:{
                                 data: [{
                                            name: '平均值',
                                            type: 'average'
                                        }]
                             }
                         }]
                     };
                     // 为echarts对象加载数据 
                     myChart.setOption(option); 
                     $.post("serviceItemController/getxnjc.do",{}, function(responseText) {
                         var resultJson = $.parseJSON(responseText);
                         myChart.hideLoading();
                         // 填入数据
                         myChart.setOption({
                             series: [{
                                 // 根据名字对应到相应的系列
                                 name: '行政许可',
                                 data: resultJson.xzxk
                             },{
                                 // 根据名字对应到相应的系列
                                 name: '公共服务',
                                 data: resultJson.ggfw
                             }]
                         });
                     });
                 }
             );
	        </script>
	        
          </div>
    </div>
	
	<%--开始编写尾部文件 --%>
	<jsp:include page="../index/foot.jsp" />
	<%--结束编写尾部文件 --%>
  </body>
</html>
