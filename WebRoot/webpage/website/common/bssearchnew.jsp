<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String busType = request.getParameter("busType");
request.setAttribute("busType", busType);
BusTypeService busTypeService = (BusTypeService)AppUtil.getBean("busTypeService");
if(busType!=null&&busType.equals("grbs")){
    List<Map<String,Object>>   ztlist = busTypeService.findByTypeCodeForWebSite("GRZTFL");
    List<Map<String,Object>>   rslist = busTypeService.findByTypeCodeForWebSite("GRRSSJ");
    List<Map<String,Object>>  tdlist = busTypeService.findByTypeCodeForWebSite("GRTDRQ");
    request.setAttribute("ztlist", ztlist);
    request.setAttribute("rslist", rslist);
    request.setAttribute("tdlist", tdlist);
}else if(busType!=null&&busType.equals("lsgrbs")){
    List<Map<String,Object>>   ztlist = busTypeService.findByTypeCodeForWebSite("GRZTFL");
    List<Map<String,Object>>   rslist = busTypeService.findByTypeCodeForWebSite("GRRSSJ");
    //List<Map<String,Object>>  tdlist = busTypeService.findByTypeCodeForWebSite("GRTDRQ");
    List<Map<String,Object>>  tdlist = busTypeService.findByTypeCodeForWebSite("LSTDGRTDRQ");
    request.setAttribute("ztlist", ztlist);
    request.setAttribute("rslist", rslist);
    request.setAttribute("tdlist", tdlist);
}else if(busType!=null&&busType.equals("frbs")){
    List<Map<String,Object>>   ztlist = busTypeService.findByTypeCodeForWebSite("FRZTFL");
    List<Map<String,Object>>   dxlist = busTypeService.findByTypeCodeForWebSite("QYDXFL");
    List<Map<String,Object>>  jylist = busTypeService.findByTypeCodeForWebSite("QYJYHD");
    request.setAttribute("ztlist", ztlist);
    request.setAttribute("dxlist", dxlist);
    request.setAttribute("jylist", jylist);
}else if(busType!=null&&busType.equals("bmbs")){
    List<Map<String,Object>> bmfwList = busTypeService.findByTypeCodeForWebSite("BMFW","0");
    request.setAttribute("bmfwList", bmfwList);
    List<Map<String,Object>> bmfwXzList = busTypeService.findByTypeCodeForWebSite("BMFW","1");
    request.setAttribute("bmfwXzList", bmfwXzList);
}
String sybusTypeId =  request.getParameter("sybusTypeId");
if(sybusTypeId!=null&&!sybusTypeId.equals("")){
    request.setAttribute("sybusTypeId", sybusTypeId);
}
%>
<eve:resources loadres="apputil,layer,autocomplete"></eve:resources>
<script type="text/javascript">
    function TabHeads(c,a,b){
        $(c).hover(function(){
            $(c).each(function(){
                $(this).removeClass(a)
            });
            $(b).each(function(){
                $(this).hide()
            });
            $(this).addClass(a);
            var d=$(c).index(this);
            $(b).eq(d).each(function(){
                $(this).show();
            });
        })
    }
    function setShowHref(c,a,b){
        $(c).hover(function(){
            $(c).each(function(){
                $(this).removeClass(a)
            });
            $(b).each(function(){
                $(this).hide()
            });
            //$(this).addClass(a);
            var d=$(c).index(this);
            $(b).eq(d).each(function(){
                $(this).show();
            });
        })
    }
    
    $(document).ready(function(){
        var typeCode='${typeCode}';
        addSelect(typeCode);
        TabHeads("#listContainer li","bslistOn","#listContainer h3");
      //加载自动补全数据
        loadAutoCompleteDatas();
      if("${sybusTypeId}"!=""){
    	  addSelect("${sybusTypeId}");
      }
      if("${busType}"=="lsgrbs"&&"${sybusTypeId}"==""){//绿色通道默认选中
    	 <%-- addSelect("4028818c512273e7015122dcad0c002a");//高校毕业生
    	  addSelect("4028818c512273e7015122dcd4d7002b");//人才
    	  addSelect("4028818c512273e7015122dcffec002c");//老年人
    	  addSelect("4028818c512273e7015122dd334b002d");//残疾人
    	  addSelect("4028818c512273e7015122de05960030");//妇女 
    	  addSelect("4028818c512273e7015122df1ff90033");//港澳台侨 --%>
<%--    	  addSelect("4028818c512273e7015122dd8de8002e");//特困家庭 --%>
<%--    	  addSelect("4028818c512273e7015122dde52b002f");//儿童青少年 --%>
<%--    	  addSelect("4028818c512273e7015122de3e710031");//农民 --%>
<%--    	  addSelect("4028818c512273e7015122de922c0032");//外国人 --%>
      }else if("${sybusTypeId}"!=""){
    	  
      }
    })
    function loadAutoCompleteDatas() {
    var layload = parent.layer.load('正在提交请求中…');
    $.post("webSiteController.do?loadbsSearch", {
        busType:"${busType}"
    }, function(responseText, status, xhr) {
        var resultJson = $.parseJSON(responseText);
        $("#AutoCompleteInput").autocomplete(
                resultJson,
                {
                    matchContains : true,
                    formatItem : function(row, i, max) {
                        //下拉框显示
                        return "<div>" + row.ITEM_NAME+"</div>";
                    },
                    formatMatch : function(row) {
                        //查询条件
                        return row.ITEM_NAME+row.PINYIN;
                    },
                    formatResult : function(row, i, max) {
                        //显示在文本框中
                        return row.ITEM_NAME;
                    }
                });
        });
    }
        
    function addSelect(typeid){
    	var busTypeId = $("#busTypeId").val();
<%--    	alert(busTypeId+"======="+typeid);--%>
    	if("${busType}"!="bmbs"||typeid=="ALL"||typeid=="XK"||typeid=="GF"){
	    	if(busTypeId.indexOf(typeid)==-1){
	    		$("#busTypeId").val(busTypeId+typeid+",");
	    		$("#"+typeid).addClass("listOn");
	    		$("#yxtj").html($("#yxtj").html()+"<a style=\"cursor: pointer;\" herf=\"javascript:void(0);\" class='"+typeid+"' "
	    			+"onclick=\"removeSelect('"+typeid+"')\" >"+$("#"+typeid).text()+"&nbsp;×</a>");
	    	}else{
	    		removeSelect(typeid);
	    	}
	    	reload();
    	}else{
    		if(busTypeId==""){
    			$("#busTypeId").val(busTypeId+typeid+",");
                $("#"+typeid).addClass("listOn");
                $("#yxtj").html($("#yxtj").html()+"<a style=\"cursor: pointer;\" herf=\"javascript:void(0);\" class='"+typeid+"' "
                        +"onclick=\"removeSelect('"+typeid+"')\" >"+$("#"+typeid).text()+"&nbsp;×</a>");
                reload();
    		}else if(busTypeId.indexOf(typeid)==-1){
    			//removeAllSelect();
    			$("#yxtj").html("");
    			$("#busTypeId").val("");
    			$(".listOn").each(function()
    			{
    			    $(this).removeClass("listOn");
    			});
    	
                $("#busTypeId").val(typeid+",");
                $("#"+typeid).addClass("listOn");
                $("#yxtj").html($("#yxtj").html()+"<a style=\"cursor: pointer;\" herf=\"javascript:void(0);\" class='"+typeid+"' "
                        +"onclick=\"removeSelect('"+typeid+"')\" >"+$("#"+typeid).text()+"&nbsp;×</a>");
                reload();
             }else{
                 removeSelect(typeid);
             }
    	}
    }
    function removeSelect(typeid){
    	$("#"+typeid).removeClass("listOn");
    	$("."+typeid).remove();
    	$("#busTypeId").val($("#busTypeId").val().replace(typeid+",",""));
    	reload();
    }
    function removeAllSelect(){
    	$("#yxtj").html("");
    	$("#busTypeId").val("");
    	$(".listOn").each(function()
    			{
    			    $(this).removeClass("listOn");
    			});
    	reload();
    }
    </script>
<form id="bsForm" action="javascript:void(0);">
<div class="bsSearch">
       <input type="text" id="AutoCompleteInput"  placeholder="请输入办事事项名称、目录名称或事项编码"
                onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" 
                name="ITEM_NAME"  value="${key}" /><a href="javascript:void(0);" onclick="reload();" >检 索</a>
</div>
<div class="borderpad clearfix">
	<ul class="adsearch">
	   <input type="hidden" id="busTypeId" name="busTypeIds" value=""/> 
	   <c:if test="${busType=='grbs'}">
		<li class="clearfix"><p style="width: 118px;">按主题分类：</p>
			<div>
			<c:forEach items="${ztlist}" var="ztinfo">
				<a herf="javascript:void(0);" onclick="addSelect('${ztinfo.TYPE_ID}')"  style="cursor: pointer;"
				id="${ztinfo.TYPE_ID}">${ztinfo.TYPE_NAME}</a><span>|</span>
			</c:forEach>
			</div></li>
		<li class="clearfix"><p style="width: 118px;">按人生事件分类：</p>
			<div>
				<c:forEach items="${rslist}" var="rsinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${rsinfo.TYPE_ID}')"  style="cursor: pointer;"
                id="${rsinfo.TYPE_ID}" >${rsinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
			</div></li>
		<li class="clearfix"><p style="width: 118px;">按特定人群分类：</p>
			<div>
				<c:forEach items="${tdlist}" var="rqinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${rqinfo.TYPE_ID}')"  style="cursor: pointer;"
                id="${rqinfo.TYPE_ID}" >${rqinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
			</div></li>
		</c:if>
	    <c:if test="${busType=='lsgrbs'}">
		<li class="clearfix"><p style="width: 118px;">快速通道：</p>
			<div>
				<c:forEach items="${tdlist}" var="rqinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${rqinfo.TYPE_ID}')"  style="cursor: pointer;"
                id="${rqinfo.TYPE_ID}" >${rqinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
			</div></li>
		</c:if>
		<c:if test="${busType=='frbs'}">
        <li class="clearfix"><p style="width: 118px;">按主题分类：</p>
            <div>
            <c:forEach items="${ztlist}" var="ztinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${ztinfo.TYPE_ID}')"  style="cursor: pointer;"
                id="${ztinfo.TYPE_ID}" >${ztinfo.TYPE_NAME}</a><span>|</span>
            </c:forEach>
            </div></li>
        <li class="clearfix"><p style="width: 118px;">按对象分类：</p>
            <div>
                <c:forEach items="${dxlist}" var="dxinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${dxinfo.TYPE_ID}')"  style="cursor: pointer;"
                id="${dxinfo.TYPE_ID}" >${dxinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
            </div></li>
        <li class="clearfix"><p style="width: 118px;">按经营活动分类：</p>
            <div>
                <c:forEach items="${jylist}" var="jyinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${jyinfo.TYPE_ID}')"  style="cursor: pointer;"
                id="${jyinfo.TYPE_ID}" >${jyinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
            </div></li>
        </c:if>
        <c:if test="${busType=='bmbs'}">
        <li class="clearfix"><p style="width: 100px;">&nbsp;按部门分类：</p>
            <div>
            <c:forEach items="${bmfwList}" var="bmfwinfo">
	                <a herf="javascript:void(0);" onclick="addSelect('${bmfwinfo.TYPE_ID}')"  style="cursor: pointer;"
	                id="${bmfwinfo.TYPE_ID}"  >${bmfwinfo.TYPE_NAME}</a><span>|</span>
            </c:forEach>
            </div>
		</li>
        <li class="clearfix"><p style="width: 100px;">乡镇便民服务：</p>
            <div>
            <c:forEach items="${bmfwXzList}" var="bmfwxzinfo">
	                <a herf="javascript:void(0);" onclick="addSelect('${bmfwxzinfo.TYPE_ID}')"  style="cursor: pointer;"
	                id="${bmfwxzinfo.TYPE_ID}"  >${bmfwxzinfo.TYPE_NAME}</a><span>|</span>
            </c:forEach>
            </div>
		</li>
        <li class="clearfix"><p style="width: 100px;">&nbsp;事项性质：</p>
            <div>
<%--            <c:forEach items="${bmfwSxxzList}" var="bmfwSxxzinfo">--%>
<%--	                <a herf="javascript:void(0);" onclick="addSelect('${bmfwSxxzinfo.TYPE_ID}')"  style="cursor: pointer;"--%>
<%--	                id="${bmfwSxxzinfo.TYPE_ID}"  >${bmfwSxxzinfo.TYPE_NAME}</a><span>|</span>--%>
<%--            </c:forEach>--%>
	                <a herf="javascript:void(0);" onclick="addSelect('ALL')"  style="cursor: pointer;"
	                id="ALL"  >全部</a><span>|</span>
	                <a herf="javascript:void(0);" onclick="addSelect('XK')"  style="cursor: pointer;"
	                id="XK"  >行政许可</a><span>|</span>
	                <a herf="javascript:void(0);" onclick="addSelect('GF')"  style="cursor: pointer;"
	                id="GF"  >公共服务</a><span>|</span>
            </div>
		</li>
        </c:if>
	</ul>
</div>
<div class="borderpad clearfix">
	<ul class="dlesearch">
		<li><p>已选条件：</p>
			<div id="yxtj">
			</div>
			<a herf="javascript:void(0);" onclick="removeAllSelect()" 
			style="cursor: pointer;" class="btndelete">删除条件</a></li>
	</ul>
</div>
<div class="bsinfo">
<%--	<span>友情提示：</span>本地在线办事平台正在升级，请移步<a href="http://zwfw.fujian.gov.cn/service.action?fn=deptService" target="_blank"><span>福建省网上办事大厅</span></a>发起申请。--%>
	<span>友情提示：</span>使用在线办理功能前，仔细阅读办事指南有助于提高办事效率。
</div>
<div class="listinfo">
    <c:if test="${busType!='lsgrbs'}">
	<span class="lfloat"><input type="checkbox" name="SFZX" value="0" onclick="reload();" /> 仅显示可以在线办理的事项</span><span
		class="rfloat">共 <i id="total">0</i> 个目录，</span><span
		class="rfloat">共 <i id="zgbsts">0</i> 个服务事项，其中 <i id="kzxbsts">0</i> 个可以在线办事
	</span>
	</c:if>
	<script type="text/javascript">
	function jskzx(){
		var formData = $("#bsForm").serialize();
		$.post("serviceItemController.do?jskzx&busType=${busType}",formData, function(resultJson) {
			var itemList = jQuery.parseJSON(resultJson);
			var zgbsts = "";
			var kzxbsts = "";
			zgbsts = itemList[1].total;
			kzxbsts =  itemList[0].total;
			$("#zgbsts").text(zgbsts);
			$("#kzxbsts").text(kzxbsts);
        });
	}
	
	</script>
</div>
</form>