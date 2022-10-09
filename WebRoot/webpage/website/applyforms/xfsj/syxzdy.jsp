<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<eve:resources loadres="easyui"></eve:resources>
<script type="text/javascript" src="<%=path %>/webpage/bsdt/applyform/js/solely1.js"></script>
<div class="bsbox clearfix">
	<div class="bsboxC">
		<table cellpadding="0" cellspacing="0" class="xftable tab_tk_pro1" style="border: 0 dotted #a0a0a0;">
			<tr>
				<td colspan="2" style="text-align: center;">
					<label>使用性质单元</label>
				</td>
			</tr>
			<tr>
				<td style="width: 15%;text-align: center;"><span class="bscolor1">*</span>大型的人员密集场所</td>
				<td style="text-align: left;">
					1．建筑总面积大于500㎡的（应按闽建[2019]1号文要求提供施工图审查合格书）</br>
					<input type="radio" name="FC_CHARACTER" value="1101"/>歌舞厅
					<input type="radio" name="FC_CHARACTER" value="1102"/>录像厅
					<input type="radio" name="FC_CHARACTER" value="1103"/>放映厅
					<input type="radio" name="FC_CHARACTER" value="1104"/>卡拉OK厅
					<input type="radio" name="FC_CHARACTER" value="1105"/>夜总会
					<input type="radio" name="FC_CHARACTER" value="1106"/>游艺厅
					<input type="radio" name="FC_CHARACTER" value="1107"/>桑拿浴室
					<input type="radio" name="FC_CHARACTER" value="1108"/>网吧
					<input type="radio" name="FC_CHARACTER" value="1109"/>酒吧
					<input type="radio" name="FC_CHARACTER" value="1110"/>具有娱乐功能餐馆
					<input type="radio" name="FC_CHARACTER" value="1111"/>具有娱乐功能茶馆
					<input type="radio" name="FC_CHARACTER" value="1112"/>具有娱乐功能咖啡厅</br>
					2．建筑总面积大于1000㎡的</br>
					<input type="radio" name="FC_CHARACTER" value="1201"/>托儿所的儿童用房
					<input type="radio" name="FC_CHARACTER" value="1202"/>幼儿园的儿童用房
					<input type="radio" name="FC_CHARACTER" value="1203"/>儿童游乐厅
					<input type="radio" name="FC_CHARACTER" value="1204"/>其他室内儿童活动场所
					<input type="radio" name="FC_CHARACTER" value="1205"/>养老院
					<input type="radio" name="FC_CHARACTER" value="1206"/>福利院
					<input type="radio" name="FC_CHARACTER" value="1207"/>医院病房楼
					<input type="radio" name="FC_CHARACTER" value="1208"/>疗养院病房楼
					<input type="radio" name="FC_CHARACTER" value="1209"/>中小学校教学楼
					<input type="radio" name="FC_CHARACTER" value="1210"/>中小学校图书馆
					<input type="radio" name="FC_CHARACTER" value="1211"/>中小学校食堂
					<input type="radio" name="FC_CHARACTER" value="1212"/>学校集体宿舍
					<input type="radio" name="FC_CHARACTER" value="1213"/>劳动密集型企业的员工集体宿舍</br>
					3．建筑总面积大于2500㎡的</br>
					<input type="radio" name="FC_CHARACTER" value="1301"/>影剧院
					<input type="radio" name="FC_CHARACTER" value="1302"/>公共图书馆的阅览室
					<input type="radio" name="FC_CHARACTER" value="1303"/>营业性室内健身场馆
					<input type="radio" name="FC_CHARACTER" value="1304"/>营业性室内休闲场馆
					<input type="radio" name="FC_CHARACTER" value="1305"/>医院门诊楼
					<input type="radio" name="FC_CHARACTER" value="1306"/>大学教学楼
					<input type="radio" name="FC_CHARACTER" value="1307"/>大学图书馆
					<input type="radio" name="FC_CHARACTER" value="1308"/>大学食堂
					<input type="radio" name="FC_CHARACTER" value="1309"/>劳动密集型企业生产加工间
					<input type="radio" name="FC_CHARACTER" value="1310"/>寺庙
					<input type="radio" name="FC_CHARACTER" value="1311"/>教堂</br>
					4．建筑总面积大于10000㎡的</br>
					<input type="radio" name="FC_CHARACTER" value="1401"/>宾馆
					<input type="radio" name="FC_CHARACTER" value="1402"/>饭店
					<input type="radio" name="FC_CHARACTER" value="1403"/>商场
					<input type="radio" name="FC_CHARACTER" value="1404"/>市场</br>
					5．建筑总面积大于15000㎡的</br>
					<input type="radio" name="FC_CHARACTER" value="1501"/>民用机场航站楼
					<input type="radio" name="FC_CHARACTER" value="1502"/>客运车站候车室
					<input type="radio" name="FC_CHARACTER" value="1503"/>客运码头候船厅</br>
					6．建筑总面积大于20000㎡的</br>
					<input type="radio" name="FC_CHARACTER" value="1601"/>体育场馆
					<input type="radio" name="FC_CHARACTER" value="1602"/>会堂
					<input type="radio" name="FC_CHARACTER" value="1603"/>公共展览馆的展示厅
					<input type="radio" name="FC_CHARACTER" value="1604"/>博物馆的展示厅</br>
				</td>
			</tr>
			<tr>
				<td style="width: 15%;text-align: center;"><span class="bscolor1">*</span>其他特殊工程</td>
				<td style="text-align: left;">
					1．<input type="radio" name="FC_CHARACTER" value="2100"/>设有上栏所列大型的人员密集场所的建设工程</br>
					2．<input type="radio" name="FC_CHARACTER" value="2201"/>国家机关办公楼
					<input type="radio" name="FC_CHARACTER" value="2202"/>电力调度楼
					<input type="radio" name="FC_CHARACTER" value="2203"/>电信楼
					<input type="radio" name="FC_CHARACTER" value="2204"/>邮政楼
					<input type="radio" name="FC_CHARACTER" value="2205"/>防灾指挥调度楼
					<input type="radio" name="FC_CHARACTER" value="2206"/>广播电视楼
					<input type="radio" name="FC_CHARACTER" value="2207"/>档案楼</br>
					3．除本栏第1项、第2项以外的</br>
					<input type="radio" name="FC_CHARACTER" value="2301"/>单体建筑面积大于40000㎡的公共建筑
					<input type="radio" name="FC_CHARACTER" value="2302"/>建筑高度超过50m的公共建筑</br>
					4．<input type="radio" name="FC_CHARACTER" value="2400"/>国家标准规定一类高层住宅建筑</br>
					5．<input type="radio" name="FC_CHARACTER" value="2501"/>城市轨道交通工程
					<input type="radio" name="FC_CHARACTER" value="2502"/>城市隧道工程
					<input type="radio" name="FC_CHARACTER" value="2503"/>大型发电工程
					<input type="radio" name="FC_CHARACTER" value="2504"/>大型变配电工程</br>
					6．生产、储存易燃易爆危险物品的</br>
					<div id="ifSpecialBuilding">
						<input type="radio" name="FC_CHARACTER" value="2601"/>工厂
						<input type="radio" name="FC_CHARACTER" value="2602"/>仓库</br>
						装卸易燃易爆危险物品的</br>
						<input type="radio" name="FC_CHARACTER" value="2603"/>专用车站
						<input type="radio" name="FC_CHARACTER" value="2604"/>专用码头</br>
						易燃易爆气体的</br>
						<input type="radio" name="FC_CHARACTER" value="2605"/>充装站
						<input type="radio" name="FC_CHARACTER" value="2606"/>供应站
						<input type="radio" name="FC_CHARACTER" value="2607"/>调压站</br>
						易燃易爆液体的</br>
						<input type="radio" name="FC_CHARACTER" value="2608"/>充装站
						<input type="radio" name="FC_CHARACTER" value="2609"/>供应站
						<input type="radio" name="FC_CHARACTER" value="2610"/>调压站</br>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>