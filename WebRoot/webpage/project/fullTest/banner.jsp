<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<!-- banner -->
<div class="eui-banner slideBox">
    <div class="hd"><ul class="eui-flex tc"></ul></div>
    <div class="bd">
        <ul>
            <e:for filterid="624" end="7" var="efor" dsid="2">
                <li><img src="${_file_Server}${efor.TITLEIMG}" ></li>
            </e:for>
        </ul>
    </div>
</div>
<!-- banner end -->