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
    $(document).ready(function(){
      //加载自动补全数据
        //loadAutoCompleteDatas();
    	if("${sybusTypeId}"!=""){
            addSelect("${sybusTypeId}",'grbs');
        }
    })
        
       function addSelect(typeid,busType){
        var busTypeId = $("#busTypeId").val();
        if(busType!="bmbs"){
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
<div class="borderpad clearfix">
	<ul class="adsearch">
	   
	   <c:if test="${busType=='grbs'}">
		<li class="clearfix"><p style="width: 118px;">按主题分类：</p>
			<div>
			<c:forEach items="${ztlist}" var="ztinfo">
				<a herf="javascript:void(0);" onclick="addSelect('${ztinfo.TYPE_ID}','${busType}')"  style="cursor: pointer;"
				id="${ztinfo.TYPE_ID}">${ztinfo.TYPE_NAME}</a><span>|</span>
			</c:forEach>
			</div></li>
		<li class="clearfix"><p style="width: 118px;">按人生事件分类：</p>
			<div>
				<c:forEach items="${rslist}" var="rsinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${rsinfo.TYPE_ID}','${busType}')"  style="cursor: pointer;"
                id="${rsinfo.TYPE_ID}" >${rsinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
			</div></li>
		<li class="clearfix"><p style="width: 118px;">按特定人群分类：</p>
			<div>
				<c:forEach items="${tdlist}" var="rqinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${rqinfo.TYPE_ID}','${busType}')"  style="cursor: pointer;"
                id="${rqinfo.TYPE_ID}" >${rqinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
			</div></li>
		</c:if>
		<c:if test="${busType=='frbs'}">
        <li class="clearfix"><p style="width: 118px;">按主题分类：</p>
            <div>
            <c:forEach items="${ztlist}" var="ztinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${ztinfo.TYPE_ID}','${busType}')"  style="cursor: pointer;"
                id="${ztinfo.TYPE_ID}" >${ztinfo.TYPE_NAME}</a><span>|</span>
            </c:forEach>
            </div></li>
        <li class="clearfix"><p style="width: 118px;">按对象分类：</p>
            <div>
                <c:forEach items="${dxlist}" var="dxinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${dxinfo.TYPE_ID}','${busType}')"  style="cursor: pointer;"
                id="${dxinfo.TYPE_ID}" >${dxinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
            </div></li>
        <li class="clearfix"><p style="width: 118px;">按经营活动分类：</p>
            <div>
                <c:forEach items="${jylist}" var="jyinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${jyinfo.TYPE_ID}','${busType}')"  style="cursor: pointer;"
                id="${jyinfo.TYPE_ID}" >${jyinfo.TYPE_NAME}</a><span>|</span>
                </c:forEach>
            </div></li>
        </c:if>
        <c:if test="${busType=='bmbs'}">
        <li class="clearfix"><p>按部门分类：</p>
            <div>
            <c:forEach items="${bmfwList}" var="bmfwinfo">
                <a herf="javascript:void(0);" onclick="addSelect('${bmfwinfo.TYPE_ID}','${busType}')"  style="cursor: pointer;"
                id="${bmfwinfo.TYPE_ID}"  >${bmfwinfo.TYPE_NAME}</a><span>|</span>
            </c:forEach>
            </div></li>
        </c:if>
	</ul>
</div>

