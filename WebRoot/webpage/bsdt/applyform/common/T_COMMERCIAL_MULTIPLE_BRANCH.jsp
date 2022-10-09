<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!---引入公共JS--->
<script type="text/javascript">
	
</script>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="BAFWGSFGS_IN" value="1" <c:if test="${busRecord.BAFWGSFGS_IN==1}"> checked="checked"</c:if>/>保安服务公司分公司备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="BAFWGSFGS_JYFW" width="749px;" clazz="checkbox"
				dataParams="BAFWGSFGSJYFW" value="${busRecord.BAFWGSFGS_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="ZCPGJG_IN" value="1" <c:if test="${busRecord.ZCPGJG_IN==1}"> checked="checked"</c:if>/>资产评估机构及其分支机构备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="ZCPGJG_JYFW" width="749px;" clazz="checkbox"
				dataParams="ZCPGJGJYFW" value="${busRecord.ZCPGJG_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>	
	
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="FYFWQY_IN" value="1" <c:if test="${busRecord.FYFWQY_IN==1}"> checked="checked"</c:if>/>物业服务企业及其分支机构备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="FYFWQY_JYFW" width="749px;" clazz="checkbox"
				dataParams="FYFWQYJYFW" value="${busRecord.FYFWQY_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="NZWZZSCJY_IN" value="1" <c:if test="${busRecord.NZWZZSCJY_IN==1}"> checked="checked"</c:if>/>农作物种子生产经营分支机构备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="NZWZZSCJY_JYFW" width="749px;" clazz="checkbox"
				dataParams="NZWZZSCJYJYFW" value="${busRecord.NZWZZSCJY_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>	


<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="LWPQDWSLFGS_IN" value="1" <c:if test="${busRecord.LWPQDWSLFGS_IN==1}"> checked="checked"</c:if>/>劳务派遣单位设立分公司备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>隶属公司劳务派遣经营许可证编号：</td>
		<td colspan="3">
			<input type="text" class="tab_text" name="LWPQDWSLFGS_JYXKZH" maxlength="32" 
			style="margin-left: 5px;" value="${busRecord.LWPQDWSLFGS_JYXKZH}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>隶属公司劳务派遣经营许可机关：</td>
		<td colspan="3">
			<input type="text" class="tab_text" name="LWPQDWSLFGS_JYXKJG" maxlength="32" 
			style="margin-left: 5px;" value="${busRecord.LWPQDWSLFGS_JYXKJG}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="LWPQDWSLFGS_JYFW" width="749px;" clazz="checkbox"
				dataParams="LWPQDWSLFGSJYFW" value="${busRecord.LWPQDWSLFGS_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>


<input type="hidden" name="FDCJJJG_JSON" />
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="FDCJJJG_IN" value="1" <c:if test="${busRecord.FDCJJJG_IN==1}"> checked="checked"</c:if>/>房地产经纪机构及其分支机构备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="FDCJJJG_JYFW" width="749px;" clazz="checkbox"
				dataParams="FDCJJJGJYFW" value="${busRecord.FDCJJJG_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="fdcjjjg">	
	<tr>
		<th colspan="4" style="background-color: #FFD39B;">房地产经纪专业人员</th>
	</tr>
	<tr id="fdcjjjg_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[]"
						name="FDCJJJG_NAME" maxlength="8"/></td>
					<td class="tab_width"> 身份证号码：</td>
					<td><input type="text" class="tab_text validate[[],custom[vidcard]]"
						name="FDCJJJG_IDCARD" maxlength="18"/></td>
				</tr>
				<tr>					
					<td class="tab_width"><font class="tab_color">*</font>职业资格证书管理号：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="FDCJJJG_MANAGE_NUMBER" maxlength="32"/></td>
					<td class="tab_width"> 登记证书登记号：</td>
					<td><input type="text" class="tab_text" 
						name="FDCJJJG_REGISTRATION_NUMBER" maxlength="32"/></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!--<input type="button" name="deleteCurrentFdcjjjg" value="删除行" onclick="deleteFdcjjjg('1');">
			<br>
			<br>
			<input type="button" name="addOneFdcjjjg" value="增加一行" onclick="addFdcjjjg();">-->
		</td>
	</tr>
</table>



<input type="hidden" name="GCZJZXQYSL_JSON" />
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="GCZJZXQYSL_IN" value="1" <c:if test="${busRecord.GCZJZXQYSL_IN==1}"> checked="checked"</c:if>/>工程造价咨询企业设立分支机构备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="GCZJZXQYSL_JYFW" width="749px;" clazz="checkbox"
				dataParams="GCZJZXQYSLJYFW" value="${busRecord.GCZJZXQYSL_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="gczjzxqysl">	
	<tr>
		<th colspan="4" style="background-color: #FFD39B;">造价工程师<font style="color:red;">（至少填报3名）</font></th>
	</tr>
	<tr id="gczjzxqysl_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[]"
						name="GCZJZXQYSL_NAME" maxlength="8"/></td>
					<td class="tab_width"> 身份证号码：</td>
					<td><input type="text" class="tab_text validate[[],custom[vidcard]]"
						name="GCZJZXQYSL_IDCARD" maxlength="18"/></td>
				</tr>
				<tr>					
					<td class="tab_width"><font class="tab_color">*</font>注册证书编号：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="GCZJZXQYSL_CODE" maxlength="32"/></td>
					<td class="tab_width"> 注册证书有效期：</td>
					<td><input type="text" class="tab_text" 
						name="GCZJZXQYSL_TIME" maxlength="32"/></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!--<input type="button" name="deleteCurrentGczjzxqysl" value="删除行" onclick="deleteGczjzxqysl('1');">
			<br>
			<br>
			<input type="button" name="addOneGczjzxqysl" value="增加一行" onclick="addGczjzxqysl();">-->
		</td>
	</tr>
</table>

<input type="hidden" name="GGFBDW_JSON" />
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="GGFBDW_IN" value="1" <c:if test="${busRecord.GGFBDW_IN==1}"> checked="checked"</c:if>/>广告发布单位</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>媒介名称：</td>
		<td><input type="text" class="tab_text validate[]"
			name="GGFBDW_MJMC" maxlength="8" value="${busRecord.GGFBDW_MJMC}"/></td>
		<td class="tab_width">批准文件有效期限：</td>
		<td><input type="text" class="tab_text validate[]"
			name="GGFBDW_TIME" maxlength="18" value="${busRecord.GGFBDW_TIME}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="GGFBDW_JYFW" width="749px;" clazz="checkbox"
				dataParams="GGFBDWJYFW" value="${busRecord.GGFBDW_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="ggfbdw">	
	<tr>
		<th colspan="4" style="background-color: #FFD39B;">广告从业、审查人员</th>
	</tr>
	<tr id="ggfbdw_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td><input type="text" class="tab_text validate[]"
						name="GGFBDW_NAME" maxlength="8"/></td>
					<td class="tab_width"> 人员类型：</td>
					<td>
						<eve:eveselect clazz="input-dropdown" dataParams="GGFBDWTYPE"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择人员类型" name="GGFBDW_TYPE"  >
						</eve:eveselect>
					</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<!--<input type="button" name="deleteCurrentGgfbdw" value="删除行" onclick="deleteGgfbdw('1');">
			<br>
			<br>
			<input type="button" name="addOneGgfbdw" value="增加一行" onclick="addGgfbdw();">-->
		</td>
	</tr>
</table>



<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="LXSQYSLFWWD_IN" value="1" <c:if test="${busRecord.LXSQYSLFWWD_IN==1}"> checked="checked"</c:if>/>旅行社企业设立服务网点备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>隶属的旅行社企业经营许可证编号：</td>
		<td colspan="3">
			<input type="text" class="tab_text" name="LXSQYSLFWWD_JYXKZH" maxlength="32" 
			style="margin-left: 5px;" value="${busRecord.LXSQYSLFWWD_JYXKZH}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>隶属的旅行社企业经营许可机关：</td>
		<td colspan="3">
			<input type="text" class="tab_text" name="LXSQYSLFWWD_JYXKJG" maxlength="32" 
			style="margin-left: 5px;" value="${busRecord.LXSQYSLFWWD_JYXKJG}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="LXSQYSLFWWD_JYFW" width="749px;" clazz="checkbox"
				dataParams="LXSQYSLFWWDJYFW" value="${busRecord.LXSQYSLFWWD_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>
	

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="LXSQYSLFS_IN" value="1" <c:if test="${busRecord.LXSQYSLFS_IN==1}"> checked="checked"</c:if>/>旅行社企业设立分社备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>隶属的旅行社企业经营许可证编号：</td>
		<td colspan="3">
			<input type="text" class="tab_text" name="LXSQYSLFS_JYXKZH" maxlength="32" 
			style="margin-left: 5px;" value="${busRecord.LXSQYSLFS_JYXKZH}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>隶属的旅行社企业经营许可机关：</td>
		<td colspan="3">
			<input type="text" class="tab_text" name="LXSQYSLFS_JYXKJG" maxlength="32" 
			style="margin-left: 5px;" value="${busRecord.LXSQYSLFS_JYXKJG}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="LXSQYSLFS_JYFW" width="749px;" clazz="checkbox"
				dataParams="LXSQYSLFSJYFW" value="${busRecord.LXSQYSLFS_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>
	
	
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" ><input type="checkbox"  class="checkbox" 
				name="WHYSPJYDW_IN" value="1" <c:if test="${busRecord.WHYSPJYDW_IN==1}"> checked="checked"</c:if>/>文化艺术品经营单位备案</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营活动类型：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="WHYSPJYDW_JYHD" width="749px;" clazz="checkbox"
				dataParams="WHYSPJYDWJYHD" value="${busRecord.WHYSPJYDW_JYHD}">
			</eve:excheckbox>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>是否利用信息网络从事艺术品经营活动：</td>
		<td colspan="3">
			<eve:radiogroup typecode="YesOrNo" width="150px" 
				value="${busRecord.WHYSPJYDW_ISLYXXWL}" fieldname="WHYSPJYDW_ISLYXXWL">
			</eve:radiogroup>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围（可多选）：</td>
		<td colspan="3">
			<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
				name="WHYSPJYDW_JYFW" width="749px;" clazz="checkbox"
				dataParams="WHYSPJYDWJYFW" value="${busRecord.WHYSPJYDW_JYFW}">
			</eve:excheckbox>
		</td>
	</tr>
</table>
	
