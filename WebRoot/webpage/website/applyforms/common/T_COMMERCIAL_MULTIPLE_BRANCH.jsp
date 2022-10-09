<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!---引入公共JS--->
<script src="<%=path%>/webpage/website/applyforms/common/js/common_branch.js"></script>
<script type="text/javascript">
	
</script>

<form action="" id="MULTIPLE_FORM"  >
	<div class="syj-title1 ">
		<span style="color:red;">友情提示：涉及“多证合一”事项的，由申请人自行选择。</span>
	</div>
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="BAFWGSFGS_IN" value="1" <c:if test="${busRecord.BAFWGSFGS_IN==1}"> checked="checked"</c:if>/>保安服务公司分公司备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2">			
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="BAFWGSFGS_JYFW" clazz="checkbox" width="749px"
						dataParams="BAFWGSFGSJYFW" value="${busRecord.BAFWGSFGS_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="ZCPGJG_IN" value="1" <c:if test="${busRecord.ZCPGJG_IN==1}"> checked="checked"</c:if>/>资产评估机构及其分支机构备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2">			
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="ZCPGJG_JYFW" clazz="checkbox" width="749px"
						dataParams="ZCPGJGJYFW" value="${busRecord.ZCPGJG_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="FYFWQY_IN" value="1" <c:if test="${busRecord.FYFWQY_IN==1}"> checked="checked"</c:if>/>物业服务企业及其分支机构备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2">			
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="FYFWQY_JYFW" clazz="checkbox" width="749px"
						dataParams="FYFWQYJYFW" value="${busRecord.FYFWQY_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="NZWZZSCJY_IN" value="1" <c:if test="${busRecord.NZWZZSCJY_IN==1}"> checked="checked"</c:if>/>农作物种子生产经营分支机构备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2">			
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="NZWZZSCJY_JYFW" clazz="checkbox" width="749px"
						dataParams="NZWZZSCJYJYFW" value="${busRecord.NZWZZSCJY_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="LWPQDWSLFGS_IN" value="1" <c:if test="${busRecord.LWPQDWSLFGS_IN==1}"> checked="checked"</c:if>/>劳务派遣单位设立分公司备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2">
			<tr>
				<th><font class="syj-color2">*</font>隶属公司劳务派遣经营许可证编号：</th>
				<td colspan="3">
				<input type="text" class="syj-input1" name="LWPQDWSLFGS_JYXKZH" maxlength="32" 
				style="margin-left: 5px;" value="${busRecord.LWPQDWSLFGS_JYXKZH}"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>隶属公司劳务派遣经营许可机关：</th>
				<td colspan="3">
				<input type="text" class="syj-input1" name="LWPQDWSLFGS_JYXKJG" maxlength="32" 
				style="margin-left: 5px;" value="${busRecord.LWPQDWSLFGS_JYXKJG}"/>
				</td>
			</tr>		
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="LWPQDWSLFGS_JYFW" clazz="checkbox" width="749px"
						dataParams="LWPQDWSLFGSJYFW" value="${busRecord.LWPQDWSLFGS_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
	
	
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="FDCJJJG_IN" value="1" <c:if test="${busRecord.FDCJJJG_IN==1}"> checked="checked"</c:if>/>房地产经纪机构及其分支机构备案
				
		</span>
	</div>
	<div id="fdcjjjg">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
				<tr>
					<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
					<td colspan="3">
						<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
							name="FDCJJJG_JYFW" clazz="checkbox" width="749px"
							dataParams="FDCJJJGJYFW" value="${busRecord.FDCJJJG_JYFW}">
						</eve:excheckbox>
					</td>
				</tr>		
				<tr>
					<th colspan="4" style="line-height:30px;background-color: #FFD39B;text-align:center;">
						<a href="javascript:void(0);" onclick="javascript:addFdcjjjgDiv();" class="syj-addbtn" id="addFdcjjjg" style="display:none;">添 加</a>
						房地产经纪专业人员<font style="color:red;">（管理号和登记号二选一填写即可）</font>
					</th>
				</tr>
			</table>
		</div>
		<div id="fdcjjjgDiv">
			<c:if test="${empty fdcjjgList}">	
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
					<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="FDCJJJG_NAME"  placeholder="请输入姓名"  maxlength="8"/>
						</td>
						<th><font class="syj-color2">*</font>身份证号码：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="FDCJJJG_IDCARD"  placeholder="请输入身份证号码"  maxlength="18"/>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>职业资格证书管理号：</th>
						<td>
							<input type="text" class="syj-input1 validate[groupRequired[FDCJJJG_MANAGE_NUMBER,FDCJJJG_REGISTRATION_NUMBER]]" 
							name="FDCJJJG_MANAGE_NUMBER"  placeholder="请输入管理号"  maxlength="32"/>
						</td>
						<th><font class="syj-color2">*</font>登记证书登记号：</th>
						<td>
							<input type="text" class="syj-input1 validate[groupRequired[FDCJJJG_MANAGE_NUMBER,FDCJJJG_REGISTRATION_NUMBER]]" 
							name="FDCJJJG_REGISTRATION_NUMBER"  placeholder="请输入登记号"  maxlength="32"/>
						</td>
					</tr>
				</table>
			</div>
			</c:if>
			<jsp:include page="./initFdcjjjgDiv.jsp"></jsp:include>
		</div>
	</div>
	
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="GCZJZXQYSL_IN" value="1" <c:if test="${busRecord.GCZJZXQYSL_IN==1}"> checked="checked"</c:if>/>工程造价咨询企业设立分支机构备案
				
		</span>
	</div>
	<div id="gczjzxqysl">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">			
				<tr>
					<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
					<td colspan="3">
						<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
							name="GCZJZXQYSL_JYFW" clazz="checkbox" width="749px"
							dataParams="GCZJZXQYSLJYFW" value="${busRecord.GCZJZXQYSL_JYFW}">
						</eve:excheckbox>
					</td>
				</tr>		
				<tr>
					<th colspan="4" style="line-height:30px;background-color: #FFD39B;text-align:center;">
						<a href="javascript:void(0);" onclick="javascript:addGczjzxqyslDiv();" class="syj-addbtn" id="addGczjzxqysl" style="display:none;">添 加</a>
						造价工程师<font style="color:red;">（至少填报3名）</font>
					</th>
				</tr>
			</table>
		</div>
		<div id="gczjzxqyslDiv">
			<c:if test="${empty gczjzxqyslList}">	
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
					<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="GCZJZXQYSL_NAME"  placeholder="请输入姓名"  maxlength="8"/>
						</td>
						<th><font class="syj-color2">*</font>身份证号码：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="GCZJZXQYSL_IDCARD"  placeholder="请输入身份证号码"  maxlength="18"/>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>注册证书编号：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="GCZJZXQYSL_CODE"  placeholder="请输入证书编号"  maxlength="32"/>
						</td>
						<th><font class="syj-color2">*</font>注册证书有效期：</th>
						<td>
							<input type="text" class="syj-input1" placeholder="请选择有效期限" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
							readonly="readonly" name="GCZJZXQYSL_TIME" >
						</td>
					</tr>
				</table>
			</div>
			
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
					<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="1GCZJZXQYSL_NAME"  placeholder="请输入姓名"  maxlength="8"/>
						</td>
						<th><font class="syj-color2">*</font>身份证号码：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="1GCZJZXQYSL_IDCARD"  placeholder="请输入身份证号码"  maxlength="18"/>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>注册证书编号：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="1GCZJZXQYSL_CODE"  placeholder="请输入证书编号"  maxlength="32"/>
						</td>
						<th><font class="syj-color2">*</font>注册证书有效期：</th>
						<td>
							<input type="text" class="syj-input1" placeholder="请选择有效期限" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
							readonly="readonly" name="1GCZJZXQYSL_TIME">
						</td>
					</tr>
				</table>
			</div>
			
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
					<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="2GCZJZXQYSL_NAME"  placeholder="请输入姓名"  maxlength="8"/>
						</td>
						<th><font class="syj-color2">*</font>身份证号码：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="2GCZJZXQYSL_IDCARD"  placeholder="请输入身份证号码"  maxlength="18"/>
						</td>
					</tr>
					<tr>
						<th><font class="syj-color2">*</font>注册证书编号：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="2GCZJZXQYSL_CODE"  placeholder="请输入证书编号"  maxlength="32"/>
						</td>
						<th><font class="syj-color2">*</font>注册证书有效期：</th>
						<td>
							<input type="text" class="syj-input1" placeholder="请选择有效期限" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
							readonly="readonly" name="2GCZJZXQYSL_TIME">
						</td>
					</tr>
				</table>
			</div>
			</c:if>
			<jsp:include page="./initGczjzxqyslDiv.jsp"></jsp:include>
		</div>
	</div>
	
		
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="GGFBDW_IN" value="1" <c:if test="${busRecord.GGFBDW_IN==1}"> checked="checked"</c:if>/>广告发布单位</span>
	</div>	
	<div id="ggfbdw">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>媒介名称：</th>
					<td>
						<input type="text" class="syj-input1" 
						name="GGFBDW_MJMC"  placeholder="请输入媒介名称"  maxlength="32"  value="${busRecord.GGFBDW_MJMC}"/>
					</td>
					<th><font class="syj-color2">*</font>批准文件有效期限：</th>
					<td>
						<input type="text" class="syj-input1" placeholder="请选择有效期限" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
						readonly="readonly" name="GGFBDW_TIME"  value="${busRecord.GGFBDW_TIME}">
					</td>
				</tr>			
				<tr>
					<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
					<td colspan="3">
						<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
							name="GGFBDW_JYFW" clazz="checkbox" width="749px"
							dataParams="GGFBDWJYFW" value="${busRecord.GGFBDW_JYFW}">
						</eve:excheckbox>
					</td>
				</tr>	
				<tr>
					<th colspan="4" style="line-height:30px;background-color: #FFD39B;text-align:center;">
						<a href="javascript:void(0);" onclick="javascript:addGgfbdwDiv();" class="syj-addbtn" id="addGgfbdw" >添 加</a>
						广告从业、审查人员
					</th>
				</tr>
			</table>
		</div>
		<div id="ggfbdwDiv">
			<c:if test="${empty fdcjjgList}">	
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">
					<tr>
						<th><font class="syj-color2">*</font>姓名：</th>
						<td>
							<input type="text" class="syj-input1" 
							name="GGFBDW_NAME"  placeholder="请输入姓名"  maxlength="8"/>
						</td>
						<th><font class="syj-color2">*</font>人员类型：</th>
						<td>
							<eve:eveselect clazz="input-dropdown w280" dataParams="GGFBDWTYPE"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="请选择人员类型" name="GGFBDW_TYPE"  >
							</eve:eveselect>
						</td>
					</tr>	
				</table>
			</div>
			</c:if>
			<jsp:include page="./initGgfbdwDiv.jsp"></jsp:include>

		</div>	
	</div>
	
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="LXSQYSLFWWD_IN" value="1" <c:if test="${busRecord.LXSQYSLFWWD_IN==1}"> checked="checked"</c:if>/>旅行社企业设立服务网点备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>隶属的旅行社企业经营许可证编号：</th>
				<td colspan="3">
				<input type="text" class="syj-input1 w878" name="LXSQYSLFWWD_JYXKZH" maxlength="32" 
				style="margin-left: 5px;" value="${busRecord.LXSQYSLFWWD_JYXKZH}"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>隶属的旅行社企业经营许可机关：</th>
				<td colspan="3">
				<input type="text" class="syj-input1 w878" name="LXSQYSLFWWD_JYXKJG" maxlength="32" 
				style="margin-left: 5px;" value="${busRecord.LXSQYSLFWWD_JYXKJG}"/>
				</td>
			</tr>		
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="LXSQYSLFWWD_JYFW" clazz="checkbox" width="749px"
						dataParams="LXSQYSLFWWDJYFW" value="${busRecord.LXSQYSLFWWD_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="LXSQYSLFS_IN" value="1" <c:if test="${busRecord.LXSQYSLFS_IN==1}"> checked="checked"</c:if>/>旅行社企业设立分社备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>隶属的旅行社企业经营许可证编号：</th>
				<td colspan="3">
				<input type="text" class="syj-input1 w878" name="LXSQYSLFS_JYXKZH" maxlength="32" 
				style="margin-left: 5px;" value="${busRecord.LXSQYSLFS_JYXKZH}"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>隶属的旅行社企业经营许可机关：</th>
				<td colspan="3">
				<input type="text" class="syj-input1 w878" name="LXSQYSLFS_JYXKJG" maxlength="32" 
				style="margin-left: 5px;" value="${busRecord.LXSQYSLFS_JYXKJG}"/>
				</td>
			</tr>		
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="LXSQYSLFS_JYFW" clazz="checkbox" width="749px"
						dataParams="LXSQYSLFSJYFW" value="${busRecord.LXSQYSLFS_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="syj-title1 ">
		<span><input type="checkbox"  class="checkbox" 
				name="WHYSPJYDW_IN" value="1" <c:if test="${busRecord.WHYSPJYDW_IN==1}"> checked="checked"</c:if>/>文化艺术品经营单位备案</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>经营活动类型：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="WHYSPJYDW_JYHD" clazz="checkbox" width="749px"
						dataParams="WHYSPJYDWJYHD" value="${busRecord.WHYSPJYDW_JYHD}">
					</eve:excheckbox>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>是否利用信息网络从事艺术品经营活动：</th>
				<td colspan="3">
					<eve:radiogroup typecode="YesOrNo" width="150px" defaultvalue="0"
						value="${busRecord.WHYSPJYDW_ISLYXXWL}" fieldname="WHYSPJYDW_ISLYXXWL">
					</eve:radiogroup>
				</td>
			</tr>		
			<tr>
				<th><font class="syj-color2">*</font>经营范围（可多选）：</th>
				<td colspan="3">
					<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
						name="WHYSPJYDW_JYFW" clazz="checkbox" width="749px"
						dataParams="WHYSPJYDWJYFW" value="${busRecord.WHYSPJYDW_JYFW}">
					</eve:excheckbox>
				</td>
			</tr>
		</table>
	</div>
</form>
