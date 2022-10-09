<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String PRJ_NUM = request.getParameter("PRJ_NUM");
	request.setAttribute("PRJ_NUM",PRJ_NUM);	        
     String PRJ_CODE = request.getParameter("PRJ_CODE");
	request.setAttribute("PRJ_CODE",PRJ_CODE);           
%>
<script type="text/javascript">
	/**
	 * 删除责任主体信息
	 */
	function deleteZRInfo() {
		AppUtil.deleteDataGridRecord("xfDesignController.do?multiDelZrzt",
				"zrztxxBackGrid");
	};
	/**
	 * 编辑责任主体信息
	 */
	function editZRInfo() {
		var entityId = AppUtil.getEditDataGridRecord("zrztxxBackGrid");
		if (entityId) {
			showZrztxxWindow(entityId);
		}
	}

	/**
	 * 显示责任主体信息对话框
	 */
	function showZrztxxWindow(entityId) {
		var prj_num = document.getElementById("PRJ_NUM").value;
		var prj_code = document.getElementById("PRJ_CODE").value;
		$.dialog.open("xfDesignController.do?zrztInfo&entityId=" + entityId
				+"&prj_num="+prj_num+"&type=back"+"&prj_code="+prj_code, {
			title : "责任主体信息123",
			width : "800px",
			height : "350px",
			lock : true,
			resize : false
		}, false);
	};
	/**
	 * 格式化责任主体类型
	 */
	function formatZrztType(val,row){
		if(val=="1"){
			return "勘察企业";
		}else if(val=="2"){
			return "设计企业";
		}else if(val=="3"){
			return "施工企业";
		}else if(val=="4"){
			return "监理企业";
		}else if(val=="5"){
			return "工程总承包单位";
		}else if(val=="6"){
			return "质量检测机构";
		}else if(val=="7"){
            return "建设单位";
        }else{
			return "其他";
		}
	};
</script>
<style>
	.xfsjBtn{
		background: #62a1cf none repeat scroll 0 0;color: #fff;
		display: inline-block;height: 26px;left: -5px;top: 1px;
		line-height: 26px;position: relative;text-align: center;
		width: 59px;margin-left: 5px;
	}
</style>
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>责任主体信息</span></li>
		</ul>
	</div>
	<div class="bsboxC">
		<input type="hidden" id="PRJ_NUM" name="PRJ_NUM" value="1">
		<table cellpadding="0" cellspacing="1" style="width:94%">
<!-- 			<tr>
				<td>
					<a href="javascript:void(0);" class="xfsjBtn" onclick="showZrztxxWindow()">新增</a>
					<a href="javascript:void(0);" class="xfsjBtn" onclick="editZRInfo()">修改</a>
					<a href="javascript:void(0);" class="xfsjBtn" onclick="deleteZRInfo()">删除</a>
				</td>
			</tr> -->
			<tr>
				<td style="height:155px;">
					<table class="easyui-datagrid" rownumbers="true" pagination="false"
						id="zrztxxBackGrid" idfield="FC_CORP_INFO_ID" fitcolumns="true"
						checkonselect="true" style="width:100%;height:auto;"
						selectoncheck="true" fit="true" border="false" 
						url="xfDesignController.do?findZrztxxList&prj_code=${busRecord.PRJ_CODE}&prj_num=${busRecord.PRJ_NUM}">
						<thead>
							<tr>
								<th field="ck" checkbox="true"></th>
								<th data-options="field:'FC_CORP_INFO_ID',hidden:true">ID</th>
								<th data-options="field:'CORP_ROLE_NUM',align:'left'" width="10%" formatter="formatZrztType">责任主体类别</th>
								<th data-options="field:'CORP_NAME',align:'left'" width="24%">责任主体名称</th>
								<th data-options="field:'CORP_LEVEL',align:'left'" width="20%" >资质等级</th>
								<th data-options="field:'LEGAL_REPRESENT',align:'left'" width="20%" >法定代表人/主要负责人</th>
								<th data-options="field:'PERSON_NAME',align:'left'" width="10%" >联系人</th>
								<th data-options="field:'PERSON_PHONE',align:'left'" width="10%" >联系人电话</th>
							</tr>
						</thead>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>