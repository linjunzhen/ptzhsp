<%@page import="net.evecom.platform.system.service.DepartmentService"%>
<%@page import="net.evecom.platform.system.service.DictionaryService"%>
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
    DepartmentService departmentService = (DepartmentService) AppUtil.getBean("departmentService");
    DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
    List<Map<String, Object>> bmfwList = departmentService.findByParentId("7D120C9034154F0E0000280000000037");
    request.setAttribute("bmfwList", bmfwList);
    List<Map<String, Object>> sxxzList = dictionaryService.findByTypeCode("ServiceItemXz");
    request.setAttribute("sxxzList", sxxzList);
%>
<eve:resources loadres="apputil,autocomplete"></eve:resources>
<style type="text/css">
    .searchRight{
        width: 318px;
        border: #c9deef 1px solid;
        height: 26px;
        line-height: 26px;
    }
    .listinfo span input{
        margin: 6px 4px 0 11px;
    }

    .bsSearcha {
        height: 26px;
        line-height:  26px;
        padding: 0 19px;
        background: #6dabde;
        border: 1px #26bbdb solid;
        border-radius: 3px;
        color: #fff;
        display: inline-block;
        text-decoration: none;
        font-size: 14px;
        outline: none;
        width: 40px;
        margin:6px 0px 0px 6px;
    }
</style>
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
    $.post("billRightController/loadQzqdBsSearch.do", {
        busType:"${busType}"
    }, function(responseText, status, xhr) {
        var resultJson = $.parseJSON(responseText);
        $("#AutoCompleteInput").autocomplete(
                resultJson,
                {
                    matchContains : true,
                    formatItem : function(row, i, max) {
                        //下拉框显示
                        return "<div>" + row.BILL_NAME+"</div>";
                    },
                    formatMatch : function(row) {
                        //查询条件
                        return row.BILL_NAME+row.PINYIN;
                    },
                    formatResult : function(row, i, max) {
                        //显示在文本框中
                        return row.BILL_NAME;
                    }
                });
        });
    }
        
    function addSelect(typeid,id){
    	var busTypeId = $("#"+id).val();
		if(busTypeId.indexOf(typeid)==-1){
			$("#"+id).val(busTypeId+typeid+",");
			$("#"+typeid).addClass("listOn");
			$("#yxtj").html($("#yxtj").html()+"<a style=\"cursor: pointer;\" herf=\"javascript:void(0);\" class='"+typeid+"' "
					+"onclick=\"removeSelect('"+typeid+"','"+id+"')\" >"+$("#"+typeid).text()+"&nbsp;×</a>");
		}else{
			removeSelect(typeid,id);
		}
		reload();    	
    }
    function removeSelect(typeid,id){
    	$("#"+typeid).removeClass("listOn");
    	$("."+typeid).remove();
    	$("#"+id).val($("#"+id).val().replace(typeid+",",""));
    	reload();
    }
    function removeAllSelect(){
    	$("#yxtj").html("");
    	$("#busTypeId").val("");
    	$("#dicCode").val("");
    	$(".listOn").each(function()
    			{
    			    $(this).removeClass("listOn");
    			});
    	reload();
    }
    </script>
<form id="bsForm" action="javascript:void(0);">
	   <input type="hidden" id="busTypeId" name="busTypeIds" value=""/> 
	   <input type="hidden" id="dicCode" name="dicCodes" value=""/>
        <input type="hidden" id="keyWord" name="keyWord" value=""/>
    <div class="listinfo">
	<%--<span class="lfloat"><input type="checkbox" name="SFZX" value="0" onclick="reload();" /> 仅显示可以在线办理的事项</span>--%>
    <span
		class="qzqd1"><input type="text" name="KEY_WORD" id="KEY_WORD" class="searchRight" placeholder="请输入查找关键字" />
        <a href="javascript:void(0);" onclick="searchByKeyWord();" class="bsSearcha">检 索</a>
        <a href="javascript:void(0);" onclick="resetKeyWord();" class="bsSearcha">重 置</a>
    </span>
	<script type="text/javascript">
        //查找关键字
     function searchByKeyWord(){
         //部门选择removeClass
         $(".sublist1 li ").each(function(){
             $(this).removeClass("sublist1On");
         });
            var keyWord=$("input[name='KEY_WORD']").val();
            $("input[name='keyWord']").val(keyWord);
            $("input[name='busTypeIds']").val("");
            reload();
        }
        //重置
        function resetKeyWord(){
            $("input[name='KEY_WORD']").val("");
            $("input[name='keyWord']").val("");
            reload();
        }
	function jskzx(){
		var formData = $("#bsForm").serialize();
		$.post("billRightController/jskzx.do",formData, function(resultJson) {
			var itemList = jQuery.parseJSON(resultJson);
			var zgbsts = "";
			var kzxbsts = "";
			zgbsts = itemList[1].TOTAL;
			kzxbsts =  itemList[0].TOTAL;
			$("#zgbsts").text(zgbsts);
			$("#kzxbsts").text(kzxbsts);
        });
	}
	
	</script>
</div>
</form>