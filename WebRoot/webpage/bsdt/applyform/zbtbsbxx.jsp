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
<link rel="stylesheet" type="text/css" href="<%=path%>/plug-in/eveflowdesign-1.0/css/design.css" />
<eve:resources
		loadres="easyui"></eve:resources>
<script type="text/javascript">
var isHide=true;
var types=[["kcXM","勘察"],[ "sjXM","设计"],["jlXM","监理"],["gcXM","工程施工"],["zysbXM","重要设备"],["zyclXM","重要材料"]];
	$(function(){
		if(!${EFLOW_IS_START_NODE}){
			$("#projectCodeA").hide();	
			$("#projectCodeFont").hide();			
			$("#tzjbxx input").attr("readonly",false);
			$("#tzjbxx textarea").attr("readonly",false);		
			$("#tzjbxx select").attr("readonly",false);	
			$("#tzjbxx input[name='PROJECTCODE']").attr("readonly",true);
		}
	});
	//显示和隐藏招标项目详细信息
	function showZbxx(obj){
		var isShowFiles=false;
		$(".tr_zbtbcl0001").css('display','none');
		if($("input[name='sjXM']").is(':checked')){
			isShowFiles=true;
		}
		if(isShowFiles){
			$("tr.tr_zbtbcl0001").css('display','');
		}
		if(obj.checked){
			$("."+obj.name+"zbxxClass").css('display','');
		}else{
			$("."+obj.name+"zbxxClass").css('display','none');
			$(".tr_zbtb"+obj.name+"0002").css('display','none');
			$(".tr_zbtb"+obj.name+"0001").css('display','none');
			$("#"+obj.name+"zbfs1").attr("checked",false); 
			$("#"+obj.name+"zbfs2").attr("checked",false); 
			$("input[name='"+obj.name+"zbje']").val("");
		}
		//设置隐藏字段ZBXM_TYPE的值 
		var temp="";
		for(var i=0;i<types.length;i++){
			if($("input[name='"+types[i][0]+"']").is(':checked')){
				temp+=(i+1)+",";	
			}
		}
		temp=temp.length>1?temp.substring(0,temp.length-1):"";
		$("input[name='ZBXM_TYPE']").val(temp);
	}
	//点击项目类型标题时选重选项 
	function selectType(checkName){
		$("input[name='"+checkName+"']").click();	
		
	}
	//点击项目类型标题时选重选项 
	function selectTypeById(checkName){
		$("#"+checkName).click();	
	}
	//选择招标方式  并变换理由内容选项
	function selectZbfs(xmType,val){
		//材料显示初始化
		$(".tr_zbtbcl0001").css('display','');
		$(".tr_zbtbcl0002").css('display','none');
		$(".tr_zbtbcl0003").css('display','none');
		$(".tr_zbtbcl0004").css('display','none');
		$(".tr_zbtbcl0005").css('display','none');
		//重置理由勾选项
		for(var i=0;i<7;i++){$("input[name='"+xmType+"yqzbly"+(i+1)+"']").attr("checked",false);}
		for(var i=0;i<8;i++){$("input[name='"+xmType+"yqzbXz"+(i+1)+"']").attr("checked",false);}
		for(var i=0;i<8;i++){$("input[name='"+xmType+"bzbly"+(i+1)+"']").attr("checked",false);}
		//邀请招标 1   不招标   2
		if(val==1){
			$("#"+xmType+"yqzbDiv").attr("style","display:block;");	
			$("#"+xmType+"bzbDiv").attr("style","display:none;");	
			$(".tr_zbtb"+xmType+"0002").css('display','none');
			$(".tr_zbtb"+xmType+"0001").css('display','');
		}else{
			$("#"+xmType+"yqzbDiv").attr("style","display:none;");	
			$("#"+xmType+"bzbDiv").attr("style","display:block;");	
			$(".tr_zbtb"+xmType+"0002").css('display','');
			$(".tr_zbtb"+xmType+"0001").css('display','none');
		}
		storTd();
		var applyMatersJson = $("input[name='applyMatersJson']").val();
	}
	//选择项目性质
	function showXmxzInfo(obj,xmType){
		if(obj.checked){
			$("#"+xmType+"xmxzDiv").attr("style","display:block;");	
		}else{	
			$("#"+xmType+"xmxzDiv").attr("style","display:none;");	
		}
	}
	//判断是否有不招理由7，设置是否显示相应的几个附件
	function setFileByBZBLY(){
		var isShowFiles=false;
		$(".tr_zbtbcl0001").css('display','none');
		$(".tr_zbtbcl0002").css('display','none');
		$(".tr_zbtbcl0003").css('display','none');
		$(".tr_zbtbcl0004").css('display','none');
		$(".tr_zbtbcl0005").css('display','none');
		for(var i=0;i<types.length;i++){
			if($("input[name='"+types[i][0]+"']").is(':checked')){
				if($("input[name='"+types[i][0]+"zbfs']:checked").length>0
						&&$("input[name='"+types[i][0]+"zbfs']:checked").val()==1){
					$(".tr_zbtb"+types[i][0]+"0002").css('display','none');
					$(".tr_zbtb"+types[i][0]+"0001").css('display','');
				}else if($("input[name='"+types[i][0]+"zbfs']:checked").length>0
						&&$("input[name='"+types[i][0]+"zbfs']:checked").val()==2){
					$(".tr_zbtb"+types[i][0]+"0001").css('display','none');
					$(".tr_zbtb"+types[i][0]+"0002").css('display','');
					if($("input[name='"+types[i][0]+"bzbly7']").is(':checked')){
						isShowFiles=true;
					}
				}
			}
		}
		if(isShowFiles){
			$("tr.tr_zbtbcl0001").css('display','');
			$("tr.tr_zbtbcl0002").css('display','');
			$("tr.tr_zbtbcl0003").css('display','');
			$("tr.tr_zbtbcl0004").css('display','');
			$("tr.tr_zbtbcl0005").css('display','');
		}
	}
	function setFileByZBLY7(){
		var isShowFiles=false;
		$(".tr_zbtbcl0001").css('display','');
		for(var i=0;i<types.length;i++){
			if($("input[name='"+types[i][0]+"']").is(':checked')){
				if($("input[name='"+types[i][0]+"zbfs']:checked").length>0
						&&$("input[name='"+types[i][0]+"zbfs']:checked").val()==1){
					$(".tr_zbtb"+types[i][0]+"0002").css('display','none');
					$(".tr_zbtb"+types[i][0]+"0001").css('display','');
					if($("input[name='"+types[i][0]+"yqzbly7']").is(':checked')){
						isShowFiles=true;
					}
				}else if($("input[name='"+types[i][0]+"zbfs']:checked").length>0
						&&$("input[name='"+types[i][0]+"zbfs']:checked").val()==2){
					$(".tr_zbtb"+types[i][0]+"0001").css('display','none');
					$(".tr_zbtb"+types[i][0]+"0002").css('display','');
				}
			}
		}
		if(isShowFiles){
			$("tr.tr_zbtbcl0001").css('display','none');
		}
	}
	
</script>
<style>
	.lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
</style>
<input type="hidden" name="IS_IMPORTANT_PROJECT" value=""/>
<input type="hidden" name="ZBXM_TYPE" value=""/>
<input type="hidden" name="kcXM_Record" value=""/>
<input type="hidden" name="sjXM_Record" value=""/>
<input type="hidden" name="jlXM_Record" value=""/>
<input type="hidden" name="gcXM_Record" value=""/>
<input type="hidden" name="zysbXM_Record" value=""/>
<input type="hidden" name="zyclXM_Record" value=""/>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro"  id="tzjbxx">
	<tr>
		<th colspan="4">招标事项核准申报信息</th>
	</tr>
	<tr>
		<td class="tab_width" style="width:180px;"><font class="tab_color">*</font>是否是国家、省重点建设项目：</td>
		<td colspan="3">
			<span  style="width:20px;display:inline-block;"></span><input type="radio" name="isImport" value="1"/>是
			<span  style="width:20px;display:inline-block;"></span><input type="radio" name="isImport" value="2"/>否
		</td>
	</tr>
	<tr>
		<td class="tab_width" style="width:180px;"><font class="tab_color">*</font>招标项目类型：</td>
		<td colspan="3">
			<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="kcXM" value="1" onclick="showZbxx(this);"/> <a href="javascript:void(0);" onclick="selectType('kcXM')">勘察</a>
			<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="sjXM" value="2" onclick="showZbxx(this);"/> <a href="javascript:void(0);" onclick="selectType('sjXM')"> 设计</a>
		    <span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="jlXM" value="3" onclick="showZbxx(this);"/>  <a href="javascript:void(0);" onclick="selectType('jlXM')">监理</a>
			<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="gcXM" value="4" onclick="showZbxx(this);"/>  <a href="javascript:void(0);" onclick="selectType('gcXM')">工程施工</a>
		    <span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="zysbXM" value="5" onclick="showZbxx(this);"/>  <a href="javascript:void(0);" onclick="selectType('zysbXM')">重要设备</a>
			<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="zyclXM" value="6" onclick="showZbxx(this);"/>  <a href="javascript:void(0);" onclick="selectType('zyclXM')">重要材料</a>
		</td>
	</tr>
	<script>
	 function makeHtml(xmType,titles,backColorStr){
			document.write('<tr class="'+xmType+'zbxxClass" style="display:none;">');
			document.write('<td class="tab_width" rowSpan="2" style="width:80px;text-align:center;background-color:#'+backColorStr+';" >'+titles+'</td>');
		    document.write('<td colSpan="3">');
		    document.write('    <span  style="width:20px;display:inline-block;"></span><font class="tab_color">*</font><span  style="width:60px;display:inline-block;">招标形式：</span>');
			document.write('	<span  style="width:5px;display:inline-block;"></span><input type="radio" name="'+xmType+'zbfs" value="1" id="'+xmType+'zbfs1" onclick="selectZbfs(\''+xmType+'\',1);"><a href="javascript:void(0);" onclick="selectTypeById(\''+xmType+'zbfs1\')"/>邀请招标</a>');
			document.write('	<span  style="width:20px;display:inline-block;"></span><input type="radio" name="'+xmType+'zbfs" value="2" id="'+xmType+'zbfs2" onclick="selectZbfs(\''+xmType+'\',2);"><a href="javascript:void(0);" onclick="selectTypeById(\''+xmType+'zbfs2\')"/>不招标</a> <span style="width:60px;display:inline-block;"></span>   项目估算金额：<input type="text" name="'+xmType+'zbje" style="width:100px;" maxlength="15" class="tab_text validate[required,custom[numberp6plus]]"/> 万元 ');
			document.write(	'</td>');
			document.write('</tr>');
			document.write('<tr class="'+xmType+'zbxxClass" style="display:none;">');
			document.write(	'<td colSpan="3">');
			document.write(	 '<div id="'+xmType+'yqzbDiv" style="display:none;">');
			document.write(		  '  <span  style="width:150px;display:inline-block;">邀请招标申请理由：</span></br>');
			document.write(		  '  <span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbly1" value="1"/>  ');
			document.write(		  '  	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbly1\')">（一）涉及国家安全、国家秘密或者抢险救灾，不适宜公开招标的；</a> ');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbly2" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbly2\')">（二）技术复杂或者有特殊专业要求，仅有少数潜在投标人可供选择的；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbly3" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbly3\')">（三）采用公开招标方式的费用占项目合同金额的比例过大，不符合经济合理性要求的；</a> ');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbly4" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbly4\')">（四）受自然资源或者环境条件限制，不适宜公开招标的；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbly5" value="1" onclick="showXmxzInfo(this,\''+xmType+'\');"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbly5\')">（五）城市重要地段、重要景观地区的建设工程、景观绿化工程，以及建筑功能有特殊要求的省重大型工程和公共建筑等项目的工程设计；</a>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbly7" onclick="setFileByZBLY7();" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbly7\')">（六）连续两次招标失败。</a></br> ');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbly6" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbly6\')">（七）法律、法规、规章规定可以邀请招标的其他情形。</a> ');
			document.write(			'<div id="'+xmType+'xmxzDiv" style="display:none;">');
			document.write(		    '<span  style="width:50px;display:inline-block;"></span><span  style="width:150px;display:inline-block;color:red;">请选择项目性质：</span></br>');
			document.write(			'<span  style="width:50px;display:inline-block;"></span>1)“城市重要地段、重要景观地区”是指：</br>');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz1" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz1\')">1.城市主干道两侧；</a></br>');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz2" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz2\')">2.城市主要水系滨水地带；</a></br>');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz3" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz3\')">3.城市中心区、中央商务区、公建集中区；</a></br>			');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz4" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz4\')">4.主要广场、公园、历史文化街区等市展活动节点周边；</a></br>			');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz5" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz5\')">5.城市交通主要出入口、交通干道交叉口、长途汽车站、地铁出入口、火车站周办区域。</a></br>');
			document.write(			'<span  style="width:50px;display:inline-block;"></span>2)“城市重要地段、重要景观地区”是指：</br>');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz6" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz6\')">机场候机楼、火车站、汽车站、图书馆、博物馆、大剧院、美术馆、档案馆、展览馆、文献馆、科普馆、纪念馆、体育场馆、公文中心、医院、学校等具有特定功能要求的公共建筑。</a></br>');
			document.write(			'<span  style="width:50px;display:inline-block;"></span>3)“省重要大型工程”是指：</br>');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz7" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz7\')">1.列入省人民政府确定的当年度省重点项目；</a></br>			');
			document.write(			'<span  style="width:70px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'yqzbXz8" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'yqzbXz8\')">2.市政大型桥梁、隧道。</a></br>		');
			document.write(			'</div>');
			document.write(		'</div>');
			document.write(		'<div id="'+xmType+'bzbDiv" style="display:none;">');
			document.write(		  '  <span  style="width:150px;display:inline-block;">不招标申请理由：</span></br>');
			document.write(		  '  <span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly1" value="1"/>  ');
			document.write(		  '  	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly1\')">（一）涉及国家安全、国家秘密或者抢险救灾，不适宜招标的；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly2" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly2\')">（二）利用扶贫资金实行以工代赈、需要使用农民工，不适宜招标的；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly3" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly3\')">（三）需要采用不可替代的专利或者专有技术；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly4" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly4\')">（四）采购人依法能够自行建设、生产或者提供；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly5" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly5\')">（五）已通过招标选定的特许经营项目投资人能够自行建设、生产或者提供；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly6" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly6\')">（六）在建工程追加的附属小型工程或者主体加层工程，原中标人仍具备承包能力，并且其他人承担将影响施工或者功能配套要求；已建成项目需要改、扩建或者技术改造，由其他单位进行设计影响项目功能配套性；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly7"  onclick="setFileByBZBLY();" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly7\')">（七）工程设计选择中国科学院、中国工程院院士（建筑专业）或者获得国际建筑成就奖或者梁思成建筑奖的建筑师作为主创设计师的设计单位（具备相应资质）进行建筑工程项目设计的；</a></br>');
			document.write(			'<span  style="width:20px;display:inline-block;"></span><input type="checkbox" name="'+xmType+'bzbly8" value="1"/>  ');
			document.write(			'	<a href="javascript:void(0);" onclick="selectType(\''+xmType+'bzbly8\')">（八）法律、法规、规章规定不适宜招标的其他情形。</a>');
			document.write(		'</div>');
			document.write(	'</td>');
			document.write('</tr>');
			document.write('<tr class="'+xmType+'zbxxClass"  style="display:none;"><td colSpan=4 style="background-color:#EDEDED;height:2px;"></td>');
			document.write('</tr>');
	 } 
	 makeHtml('kcXM','勘察','EED');
	 makeHtml('sjXM','设计','FED');
	 makeHtml('jlXM','监理','DEE');
	 makeHtml('gcXM','工程施工','FDE');
	 makeHtml('zysbXM','重要设备','DDD');
	 makeHtml('zyclXM','重要材料','DCF');
	</script>
</table>
