<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="addBox">	
	<input type="hidden" name="jldwmc" value="${CORPNAME}"/>
	<div class="syj-title1 tmargin20">		
		<a href="javascript:void(0);" onclick="javascript:addJlry('${currentTime}');" class="syj-addbtn">添加人员</a>
		<span>监理单位名称：${CORPNAME}</span>
	</div>
	<div>
		<div style="position:relative;">				
			<table cellpadding="0" cellspacing="1" class="syj-table2 tmargin20" id="${currentTime}jlryTable">
				<tr>
					<th style="width: 14%;">姓名</th>
					<th style="width: 18%;">身份证</th>
					<th style="width: 10%;">工作岗位</th>
					<th style="width: 18%;">职称</th>
					<th style="width: 16%;">专业</th>
					<th style="width: 14%;">证书编号</th>
					<th style="width: 10%;">操作</th>
				</tr>
			</table>
		</div>
	</div>
</div>