<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<script type="text/javascript">
    function goStartFlow(defKey){
    	window.open("executionController.do?goStart&defKey="+defKey, "_blank");
    }
</script>
  <c:forEach items="${types}" var="item">
<div class="defselect_content">
   	<div class="defselect_title">> ${item.TYPE_NAME}类</div>
      <div class="defselect_con clearfix">
      	<ul>
      	    <c:forEach items="${item.defs}" var="def">
          	<li onclick="goStartFlow('${def.DEF_KEY}')"><a href="#" ><img src="plug-in/images/icons/ie.png" />${def.DEF_NAME}</a></li>
          	</c:forEach>
        </ul>
      </div>
  </div>
  </c:forEach>
    



