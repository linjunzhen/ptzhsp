<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>

.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
</style>
<form action="" id="JLRY_FORM" >
	<c:forEach items="${jldwList}" var="jldw" varStatus="s">	
	<div style="position:relative;">	
		<input type="hidden" name="jldwmc" value="${jldw.CORPNAME}"/>
		<div class="syj-title1 ">		
			<a href="javascript:void(0);" onclick="javascript:addJlry('${s.index}');" class="syj-addbtn">添加人员</a>
			<span>监理单位名称：${jldw.CORPNAME}</span>
		</div>
		<div>
			<div style="position:relative;">				
				<table cellpadding="0" cellspacing="1" class="syj-table2 " id="${s.index}jlryTable">
					<tr>
						<th style="width: 14%;">姓名</th>
						<th style="width: 18%;">身份证</th>
						<th style="width: 10%;">工作岗位</th>
						<th style="width: 18%;">职称</th>
						<th style="width: 16%;">专业</th>
						<th style="width: 14%;">证书编号</th>
						<th style="width: 10%;">操作</th>
					</tr>
					<c:forEach items="${jldw.JLRYLIST}" var="jlry" varStatus="s1">
						<tr class="jlryxxTr" id="jlryTr_${s.index}_${s1.index}">
							<input type="hidden" name="jlryxx" value="${jlry.JLRY}"/>
							<td style="text-align: center;">${jlry.STATION}</td>
							<td style="text-align: center;">${jlry.PERSONIDENTITY}</td>
							<td style="text-align: center;">${jlry.PERSONNAME}</td>
							<td style="text-align: center;">${jlry.PERSONTITLE}</td>
							<td style="text-align: center;">${jlry.PERSONPROF}</td>
							<td style="text-align: center;">${jlry.PERSONCERTID}</td>
							<td style="text-align: center;">
							<a href="javascript:void(0);" class="eflowbutton" onclick="getJlry('jlryTr_${s.index}_${s1.index}');">编 辑</a>
							<a href="javascript:void(0);" onclick="delClosestTr(this);" style="float: right;top: auto;margin-top:-2px;right:-41px;" class="syj-closebtn"></a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	</c:forEach>
</form>
