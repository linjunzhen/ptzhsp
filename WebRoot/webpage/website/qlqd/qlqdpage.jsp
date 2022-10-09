<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String pageURL = request.getParameter("pageURL");
String callpage = request.getParameter("callpage");
String limitNum = request.getParameter("limitNum");
String pageFormId = request.getParameter("pageFormId");
String morename = request.getParameter("morename");
if(morename==null){
    morename ="";
}
%>
<div class="page">
    <script type="text/javascript">
     
     function changPage<%=morename%>(pa){
    	 
         var result = {};
        if("<%=pageFormId%>"!='null'){
         var $inputs = $("#<%=pageFormId%> :input");
         $.each($inputs, function(idx, o) {
        	 if(o.type=="checkbox"){
        		 var chk_value =[];
        		 $("input[name="+o.name+"]:checked").each(function(){
        		 chk_value.push($(this).val());
        		 }); 
        		 result[o.name] = chk_value; 
        	 }else if(o.type=="radio"){
        		 result[o.name] = $("input[name="+o.name+"][type='radio']:checked").val(); 
        	 }
        	 else{
             result[o.name] = o.value;
        	 }
         });
        }
         result["page"]=pa;
         result["rows"]="<%=limitNum%>";
    	 $.post("<%=pageURL%>",result, function(resultJson) {
	    		 if(resultJson!="websessiontimeout"){
	                 <%=callpage%>(resultJson.rows);
	                 $("#allnum<%=morename%>").text("共"+resultJson.total+"条");
	                 var pagenum = parseInt(resultJson.total/<%=limitNum%>);
	                 if(resultJson.total%<%=limitNum%>!=0){
	                     pagenum = pagenum +1;
	                 }
	                 $("#endpage<%=morename%>").val(pagenum);
	                 if(pagenum==0){
	                	 $("#currnum<%=morename%>").text("当前第0/0页");
	                 }else{
	                 $("#currnum<%=morename%>").text("当前第"+pa+"/"+pagenum+"页");
	                 }
	                 $("#currentpage<%=morename%>").val(pa);
	    		 }
             });
    }
    function prepage<%=morename%>(){
        if($("#currentpage<%=morename%>").val()>1){
            changPage<%=morename%>(parseInt($("#currentpage<%=morename%>").val())-1);
        }else{
            alert("已经是第一页了");
        }
    }
    function nextpage<%=morename%>(){
        if($("#currentpage<%=morename%>").val()!=$("#endpage<%=morename%>").val()&&parseInt($("#endpage<%=morename%>").val())>0){
            changPage<%=morename%>(parseInt($("#currentpage<%=morename%>").val())+1);
        }else{
            alert("已经是最后一页了");
        }
    }
    function toendpage<%=morename%>(){
    	if(parseInt($("#endpage<%=morename%>").val())>0){
        changPage<%=morename%>(parseInt($("#endpage<%=morename%>").val()));
    	}
    }
    </script>
	<input type="hidden" id="currentpage<%=morename%>" value="1" />
	<input type="hidden" id="endpage<%=morename%>" value="0" />
	<span id="allnum<%=morename%>">共 0条</span> <span id="currnum<%=morename%>">当前第 0/0 页</span> 
	<a href="javascript:void(0);" onclick="changPage<%=morename%>(1);" >首页</a>
	<a href="javascript:void(0);" onclick="prepage<%=morename%>();">上一页</a> 
	<a href="javascript:void(0);" onclick="nextpage<%=morename%>();">下一页</a> 
	<a href="javascript:void(0);" onclick="toendpage<%=morename%>();">末页</a>
	<script type="text/javascript">
	//changPage<%=morename%>(1);
	
	function reload<%=morename%>(){
		changPage<%=morename%>(1);
	}
	</script>
</div>
