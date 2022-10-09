<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<tr>
		<td>
			<div style="position:relative;">
			<span style="margin-left:100px;width:18px;overflow:hidden;">
			<eve:eveselect clazz="tab_text selectType validate[]" dataParams="jblx"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidateCyjb(this)"
			defaultEmptyText="选择奖补类型" name="${currentTime}SELECTTYPE">
			</eve:eveselect>
			</span>
			<input name="${currentTime}CYJB_TYPE"  class="validate[required]" style="position: absolute; left: 0px; width: 160px; top: 0px; height: 20px; border-width: 0px; margin-top: 1px; margin-left: 1px;">
			</div>
		</td>
		<td align="center"><input type="text" class="tab_text validate[[required],custom[numberplus]]" maxlength="16"
				name="${currentTime}CYJB_JBJE"  style="width: 60px;"  onkeyup="onlyNumber(this);sumSpjeNumber();" onblur="onlyNumber(this);sumSpjeNumber();"/></td>
		<td align="center"><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="18"
				name="${currentTime}CYJB_NSZE" style="width: 60px;" 
					 onkeyup="onlyNumber(this)" onblur="onlyNumber(this)" /></td>
		<td align="center"><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="18"
				name="${currentTime}CYJB_DFJSR" style="width: 60px;"  
					 onkeyup="onlyNumber(this)" onblur="onlyNumber(this)" />
		</td>
		<td align="center"><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
				name="${currentTime}CYJB_GSJE" style="width: 60px;" 
					 onkeyup="onlyNumber(this)" onblur="onlyNumber(this)"/></td>	
		<td><input type="text" class="tab_text" maxlength="128" name="${currentTime}CYJB_JBSD" style="width: 120px;"  /></td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="500"
				name="${currentTime}CYJB_BZ" style="width: 120px;" /></td>
		<td><a href="javascript:void(0);"
			onclick="delCycjDiv(this);" class="syj-closebtn"></a></td>
	</tr>