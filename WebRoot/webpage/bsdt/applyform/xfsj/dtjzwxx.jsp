<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<script type="text/javascript">
	/**
	 * 删除单体建筑物信息
	 */
	function deleteDtjzwInfo() {
		AppUtil.deleteDataGridRecord("xfDesignController.do?multiDelDtjzw",
				"dtjzwxxBackGrid");
	};
	/**
	 * 编辑单体建筑物信息
	 */
	function editDtjzwInfo() {
		var entityId = AppUtil.getEditDataGridRecord("dtjzwxxBackGrid");
		if (entityId) {
			showDtjzwWindow(entityId);
		}
	}

	/**
	 * 显示单体建筑物信息对话框
	 */
	function showDtjzwWindow(entityId) {
		var prj_num = document.getElementById("PRJ_NUM").value;
		var prj_code = document.getElementById("PRJ_CODE").value;
		$.dialog.open("xfDesignController.do?dtjzwInfo&entityId=" + entityId
				+"&prj_num="+prj_num+"&type=back"+"&prj_code="+prj_code, {
			title : "单体建筑物信息",
			width : "800px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
	};
	/**
	 * 格式化结构类型
	 */
	function formatJGLX(val,row){
		if(val=="001"){
			return "木结构";
		}else if(val=="002"){
			return "砖木结构";
		}else if(val=="003"){
			return "砖混结构";
		}else if(val=="004"){
			return "钢筋混凝土（砼）结构";
		}else if(val=="005"){
			return "钢结构";
		}else if(val=="006"){
			return "其他结构";
		}
	};
	/**
	 * 格式化耐火等级
	 */
	function formatJZNHDJ(val,row){
		if(val=="001"){
			return "一级";
		}else if(val=="002"){
			return "二级";
		}else if(val=="003"){
			return "三级";
		}else if(val=="004"){
			return "四级";
		}else if(val=="005"){
			return "耐火极限不小于1.5h";
		}else if(val=="006"){
			return "耐火极限不小于2h";
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
			<li class="on" style="background:none"><span>单体建筑物信息</span></li>
		</ul>
	</div>
	<div class="bsboxC">
		<input type="hidden" id="PRJ_NUM" name="PRJ_NUM" value="1">
		<table cellpadding="0" cellspacing="1" style="width:94%">
<!-- 			<tr>
				<td>
					<a href="javascript:void(0);" class="xfsjBtn" onclick="showDtjzwWindow()">新增</a>
					<a href="javascript:void(0);" class="xfsjBtn" onclick="editDtjzwInfo()">修改</a>
					<a href="javascript:void(0);" class="xfsjBtn" onclick="deleteDtjzwInfo()">删除</a>
				</td>
			</tr> -->
			<tr>
				<td style="height:155px;">
					<table class="easyui-datagrid" rownumbers="true" pagination="false"
						id="dtjzwxxBackGrid" idfield="FC_UNIT_INFO_ID" fitcolumns="true"
						checkonselect="true" style="width:100%;height:auto;"
						selectoncheck="true" fit="true" border="false" 
						url="xfDesignController.do?findDtjzwList&prj_code=${busRecord.PRJ_CODE}&prj_num=${busRecord.PRJ_NUM}">
						<thead>
							<tr>
								<th field="ck" checkbox="true"></th>
								<th data-options="field:'SUB_PRJ_NAME',align:'left'" width="14%">建筑物名称</th>
								<th data-options="field:'FC_STRUCTURE_TYPE_NUM',align:'left'" width="10%" formatter="formatJGLX">结构类型</th>
								<th data-options="field:'REFRACTORY_LEVEL_NUM',align:'left'" width="10%" formatter="formatJZNHDJ">建筑耐火等级</th>
								<th data-options="field:'FLOOR_COUNT',align:'left'" width="10%" >建筑地上层数</th>
								<th data-options="field:'BOTTOM_FLOOR_COUNT',align:'left'" width="10%" >建筑地下层数</th>
								<th data-options="field:'BUILD_HEIGHT',align:'left'" width="10%" >建筑高度(m)</th>
								<th data-options="field:'BUILD_AREA',align:'left'" width="10%" >建筑占地面积(㎡)</th>
								<th data-options="field:'FLOOR_BUILD_AREA',align:'left'" width="10%" >建筑地上面积(㎡)</th>
								<th data-options="field:'BOTTOM_FLOOR_BUILD_AREA',align:'left'" width="10%" >建筑地下面积(㎡)</th>
							</tr>
						</thead>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>