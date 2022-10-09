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
}else if(busType!=null&&busType.equals("frbs")){
    List<Map<String,Object>>   ztlist = busTypeService.findByTypeCodeForWebSite("FRZTFL");
    List<Map<String,Object>>   dxlist = busTypeService.findByTypeCodeForWebSite("QYDXFL");
    List<Map<String,Object>>  jylist = busTypeService.findByTypeCodeForWebSite("QYJYHD");
    request.setAttribute("ztlist", ztlist);
    request.setAttribute("dxlist", dxlist);
    request.setAttribute("jylist", jylist);
}else if(busType!=null&&busType.equals("bmbs")){
    List<Map<String,Object>> bmfwList = busTypeService.findByTypeCodeForWebSite("BMFW");
    request.setAttribute("bmfwList", bmfwList);
}
String sybusTypeId =  request.getParameter("sybusTypeId");
if(sybusTypeId!=null&&!sybusTypeId.equals("")){
    request.setAttribute("sybusTypeId", sybusTypeId);
}
%>
<eve:resources loadres="apputil,autocomplete"></eve:resources>
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
    $(document).ready(function(){
        TabHeads("#listContainer li","bslistOn","#listContainer h3");
      //加载自动补全数据
        loadAutoCompleteDatas();
      if("${sybusTypeId}"!=""){
    	  addSelect("${sybusTypeId}");
      }
    })
    function loadAutoCompleteDatas() {
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
    	if("${busType}"!="bmbs"){
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
    			removeAllSelect();
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
       <input type="text" id="AutoCompleteInput" 
                onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}" 
                name="ITEM_NAME"  /><a href="javascript:void(0);" onclick="reload();" >检 索</a>
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
        <li class="clearfix"><p>按部门分类：</p>
            <div>
            <c:forEach items="${bmfwList}" var="bmfwinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${bmfwinfo.TYPE_ID}')"  style="cursor: pointer;"
                id="${bmfwinfo.TYPE_ID}"  >${bmfwinfo.TYPE_NAME}</a><span>|</span>
            </c:forEach>
            </div></li>
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
<%--	<span>友情提示：</span>本地在线办事平台正在升级，请移步<a href="http://zwfw.fujian.gov.cn/service.action?fn=deptService" target="_blank">福建省网上办事大厅</a>发起申请。--%>
	<span>友情提示：</span>使用在线办理功能前，仔细阅读办事指南有助于提高办事效率。
</div>
<div class="listinfo">
	<span class="lfloat"><input type="checkbox" name="SFZX" value="0" onclick="reload();" /> 仅显示可以在线办理的事项</span><span
		class="rfloat">共 <i id="zgbsts">0</i> 个服务事项，其中 <i id="kzxbsts">0</i> 个可以在线办事
	</span>
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