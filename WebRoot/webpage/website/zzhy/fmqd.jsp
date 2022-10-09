<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>平潭综合实验区商事主体登记申报系统</title>
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
<script src="<%=path%>/webpage/website/zzhy/js/jquery.min.js"></script>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
</head>

<body>
<jsp:include page="/webpage/website/zzhy/head.jsp?currentNav=wssb" />
<div  style="float:left; width:100%;">
  <div class="container">
    <div class="syj-crumb"> <span>所在位置：</span><a href="<%=path%>/webSiteController/view.do?navTarget=website/zzhy/index">首页</a> > <i>网上申报</i> </div>
  </div>
  <div class="container" style=" overflow:hidden; margin-bottom:20px;background:url(images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
  <div class="order-detail">
    <div class="syj-tyys">
      <div class="hd syj-tabtitle">
        <ul>
          <li><a href="javasrcipt:void(0)">拟投资行业负面清单筛选</a></li>
        </ul>
      </div>
    </div>
    </div>
    <div class="nagative-top"><span>按行业名称查询：</span><input name="" type="text" /><a class="onekey" href="#">查询</a></div>
    <div class="nagative-content">

    <table id="tbl1" cellpadding="8" cellspacing="0" class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th width="30px">
                                        序号
                                    </th>
                                    <th width="20%">
                                        领域
                                    </th>
                                    <th>
                                        特别管理措施
                                    </th>
                                    <th width="100px">
                                        备注
                                    </th>
                                </tr>
                            </thead>
                            
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl00$ChkSelect" type="checkbox" id="Repeater1_ctl00_ChkSelect" style="width: 20px" onclick="selectresult()" value="421" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            农、林、牧、渔业（种业）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：1.禁止投资中国稀有和特有的珍贵优良品种的研发、养殖、种植以及相关繁殖材料的生产（包括种植业、畜牧业、水产业的优良基因）。
2.禁止投资农作物、种畜禽、水产苗种转基因品种选育及其转基因种子（苗）生产。
3.农作物新品种选育和种子生产属于限制类，须由中方控股。
4.未经批准，禁止采集农作物种质资源。">
                                                1.禁止投资中国稀有和特有的珍贵优良品种的研发、养殖、种植以及相关繁殖材料的生产（包括种植业、畜牧业、水产业的优良基因）。<br/>2.禁止投资农作物、种畜禽、水产苗种转基因品种选育及其转基因种子（苗）生产。<br/>3.农作物新品种选育和种子生产属于限制类，须由中方控股。<br/>4.未经批准，禁止采集农作物种质资源。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl01$ChkSelect" type="checkbox" id="Repeater1_ctl01_ChkSelect" style="width: 20px" onclick="selectresult()" value="422" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            农、林、牧、渔业（渔业捕捞）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：5.在中国管辖水域从事渔业活动，须经中国政府批准。
6.不批准以合作、合资等方式引进渔船在管辖水域作业的船网工具指标申请。">
                                                5.在中国管辖水域从事渔业活动，须经中国政府批准。<br/>6.不批准以合作、合资等方式引进渔船在管辖水域作业的船网工具指标申请。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl02$ChkSelect" type="checkbox" id="Repeater1_ctl02_ChkSelect" style="width: 20px" onclick="selectresult()" value="423" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            负面清单以外的农、林、牧、渔业
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无特别管理措施">
                                                </a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl03$ChkSelect" type="checkbox" id="Repeater1_ctl03_ChkSelect" style="width: 20px" onclick="selectresult()" value="424" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            采矿业（专属经济区与大陆架勘探开发）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：7.对中国专属经济区和大陆架的自然资源进行勘查、开发活动或在中国大陆架上为任何目的进行钻探，须经中国政府批准。">
                                                7.对中国专属经济区和大陆架的自然资源进行勘查、开发活动或在中国大陆架上为任何目的进行钻探，须经中国政府批准。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl04$ChkSelect" type="checkbox" id="Repeater1_ctl04_ChkSelect" style="width: 20px" onclick="selectresult()" value="425" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            采矿业（石油和天然气开采）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：8.石油、天然气（含油页岩、油砂、页岩气、煤层气等非常规油气）的勘探、开发，限于合资、合作。">
                                                8.石油、天然气（含油页岩、油砂、页岩气、煤层气等非常规油气）的勘探、开发，限于合资、合作。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl05$ChkSelect" type="checkbox" id="Repeater1_ctl05_ChkSelect" style="width: 20px" onclick="selectresult()" value="426" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            采矿业（稀土和稀有矿采选）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：9.禁止投资稀土勘查、开采及选矿；未经允许，禁止进入稀土矿区或取得矿山地质资料、矿石样品及生产工艺技术。
10.禁止投资钨、钼、锡、锑、萤石的勘查、开采。
11.禁止投资放射性矿产的勘查、开采、选矿。">
                                                9.禁止投资稀土勘查、开采及选矿；未经允许，禁止进入稀土矿区或取得矿山地质资料、矿石样品及生产工艺技术。<br/>10.禁止投资钨、钼、锡、锑、萤石的勘查、开采。<br/>11.禁止投资放射性矿产的勘查、开采、选矿。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl06$ChkSelect" type="checkbox" id="Repeater1_ctl06_ChkSelect" style="width: 20px" onclick="selectresult()" value="427" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            采矿业（金属矿及非金属矿采选）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：12.贵金属（金、银、铂族）勘查、开采，属于限制类。
13.锂矿开采、选矿，属于限制类。
14.石墨勘查、开采，属于限制类。">
                                                12.贵金属（金、银、铂族）勘查、开采，属于限制类。<br/>13.锂矿开采、选矿，属于限制类。<br/>14.石墨勘查、开采，属于限制类。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl07$ChkSelect" type="checkbox" id="Repeater1_ctl07_ChkSelect" style="width: 20px" onclick="selectresult()" value="428" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            负面清单以外的采矿业
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无特别管理措施">
                                                </a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl08$ChkSelect" type="checkbox" id="Repeater1_ctl08_ChkSelect" style="width: 20px" onclick="selectresult()" value="429" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            制造业（航空制造）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：15.干线、支线飞机设计、制造与维修，3吨级及以上民用直升机设计与制造，地面、水面效应飞机制造及无人机、浮空器设计与制造，须由中方控股。
16.通用飞机设计、制造与维修限于合资、合作。">
                                                15.干线、支线飞机设计、制造与维修，3吨级及以上民用直升机设计与制造，地面、水面效应飞机制造及无人机、浮空器设计与制造，须由中方控股。<br/>16.通用飞机设计、制造与维修限于合资、合作。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                                
                                    <tr>
                                        <td>
                                          <input name="Repeater1$ctl09$ChkSelect" type="checkbox" id="Repeater1_ctl09_ChkSelect" style="width: 20px" onclick="selectresult()" value="430" />
                                         
                                            
                                            
                                            
                                        </td>
                                        <td>
                                            制造业（船舶制造）
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 特别管理措施：17.船用低、中速柴油机及曲轴制造，须由中方控股。
18.海洋工程装备（含模块）制造与修理，须由中方控股。
19.船舶（含分段）修理、设计与制造属于限制类，须由中方控股。">
                                                17.船用低、中速柴油机及曲轴制造，须由中方控股。<br/>18.海洋工程装备（含模块）制造与修理，须由中方控股。<br/>19.船舶（含分段）修理、设计与制造属于限制类，须由中方控股。</a>
                                        </td>
                                        <td>
                                            <a href="#" style="text-decoration: none; color: Black" title=" 无禁止措施">
                                                </a>
                                        </td>
                                    </tr>
                       
                            
                        </table>

    

    
    </div>
    <div class="container">
	<div class="syj-page">
                <a href="#" class="prev syj-pageno">上一页</a>
                <a href="#" class="syj-pageon">1</a>
                <a href="#">2</a>
                <a href="#">3</a>
                <a href="#">4</a>
                <a href="#">5</a>
                <a href="#" class="next">下一页</a>
                <!--<span>共 12 个结果</span>-->
            </div>

</div>
    <div class="order-footer">
    <div class="tap-btn"><a class="tap_back">上一步</a><a class="tap_next">下一步</a></div>
    </div>
  </div>
</div>

<jsp:include page="/webpage/website/zzhy/foot.jsp" />
</body>
</html>
