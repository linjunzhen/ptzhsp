<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<eve:resources loadres="easyui"></eve:resources>
<script type="text/javascript" src="<%=path %>/webpage/bsdt/applyform/js/solely1.js"></script>
<script type="text/javascript">
var editIndex = undefined;
//结束编辑模式
function endEditing(){
	if (editIndex == undefined){return true}
	if ($("#cjgfzrztGrid").datagrid("validateRow", editIndex)){
		$("#cjgfzrztGrid").datagrid("endEdit", editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
//点击行触发操作
function onClickRow(index){
	if (editIndex != index) {
		if (endEditing()) {
			var rows = $('#cjgfzrztGrid').datagrid('getRows');
            var row = rows[index];
            var isEditValue = row.isEdit;
            if(isEditValue==true){
            	$("#cjgfzrztGrid").datagrid("selectRow", index).datagrid("beginEdit", index);
            }
			editIndex = index;
		} else {
			$("#cjgfzrztGrid").datagrid("selectRow", editIndex);
		}
	}
}
//控制行是否是编辑状态
function isEdit(value){
	var resultDate = value.rows;
	if (resultDate.length > 0) {
		for (var i = 0; i < resultDate.length; i++) {
			var isEditValue = resultDate[i].isEdit;
			if(isEditValue==false){
				$('#cjgfzrztGrid').datagrid('selectRow', i).datagrid('beginEdit', i);
				var personName = $('#cjgfzrztGrid').datagrid('getEditor', { index: i, field: 'PERSONNAME'});
				$(personName.target).attr('disabled','disabled');
				var personPhone = $('#cjgfzrztGrid').datagrid('getEditor', { index: i, field: 'PERSONPHONE'});
				$(personPhone.target).attr('disabled','disabled');
			}
		}
		$('#cjgfzrztGrid').datagrid('unselectAll');
	}
}
</script>
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>竣工验收备案信息</span></li>
		</ul>
	</div>
	<div class="bsboxC">
		<table cellpadding="0" cellspacing="0" class="bstable1" >
		    <tr>
				<th><span class="bscolor1">*</span>报建编号</th>
				<td colspan="3">
					<c:if test="${busRecord.PRJNUM==''}">
						<input type="text" maxlength="32" class="tab_text validate[required]" name="PRJNUM" value="${busRecord.PRJNUM}"/>
					</c:if>
					<c:if test="${busRecord.PRJNUM!=''}">
						<input type="text" class="tab_text" name="PRJNUM" value="${busRecord.PRJNUM}" readonly/>
					</c:if><br>
					<span class="bscolor1">注：至2020年1月10日起的项目报建编号系统会自动获取，2020年1月10日前的项目报建编号，请登录“福建省公共资源交易电子行政监督平台”地址为：ggzyjd.fj.gov.cn，获取后填入。</span>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>备案机关</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="BAJG" style="width:660px;"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="BUILDCORPNAME"/>
				</td>
				<th>所有制性质</th>
				<td>
					<eve:eveselect clazz="tab_text validate field_width" dataParams="OWNERSHIP"
						dataInterface="dictionaryService.findDatasForSelect"
						name="OWNERSHIP">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位组织机构代码</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="BUILDCORPCODE"/>
				</td>
				<th><span class="bscolor1">*</span>联系电话</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="CONPERSONTEL"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>法人代表</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="LEGALPERSON"/>
				</td>
				<th><span class="bscolor1">*</span>建设单位项目负责人</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="CONPERSON"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位地址</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="CONADDRESS" style="width:660px;"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位经办人姓名</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="JBRNAME"/>
				</td>
				<th><span class="bscolor1">*</span>建设单位经办人证件类型</th>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="TBIDCARDTYPEDIC" 
						dataInterface="dictionaryService.findDatasForSelect"
						name="JBRIDENTITYTYPENUM">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位经办人证件号码</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="JBRIDENTITY"/>
				</td>
				<th><span class="bscolor1">*</span>建设单位经办人联系电话</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="JBRTEL"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>工程项目名称</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="PROJECT_NAME" style="width:660px;"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>工程地点</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="PROADDRESS" style="width:660px;"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>合同开工日期	</th>
				<td>
					<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
						class="tab_text validate[required] Wdate" name="PLANBEGDATE"/>
				</td>
				<th><span class="bscolor1">*</span>合同竣工日期</th>
				<td>
					<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
						class="tab_text validate[required] Wdate" name="PLANENDDATE"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>合同价格（万元）</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="PROCOST"/>
				</td>
				<th><span class="bscolor1">*</span>栋数</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="DS"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建筑面积（m2）</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="PROAREA"/>
				</td>
				<th><span class="bscolor1">*</span>±0.000以上层数</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="STRUCTUPFLOORNUMS"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>市政长度（m）</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="MUNILENGTHS"/>
					<span class="bscolor1">如无此项则填0</span>
				</td>
				<th><span class="bscolor1">*</span>±0.000以下层数</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="STRUCTDWFLOORNUMS"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>实际造价（万元）</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="INVEST"/>
				</td>
				<th><span class="bscolor1">*</span>实际面积（平方米）</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="SJMJ"/>
					<span class="bscolor1">如无此项则填0</span>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>跨度（米）</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="KD"/>
					<span class="bscolor1">如无此项则填0</span>
				</td>
				<th><span class="bscolor1">*</span>公开方式</th>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="OPENWAY"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择公开方式" name="OPEN_WAY" id="openWay">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>实际开工日期	</th>
				<td>
					<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
						class="tab_text validate[required] Wdate" name="SJKGRQ"/>
				</td>
				<th><span class="bscolor1">*</span>实际竣工验收备案日期</th>
				<td>
					<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
						class="tab_text validate[required] Wdate" name="SJJGRQ"/>
				</td>
			</tr>
			<tr>
				<th>实际建设规模</th>
				<td colspan="3">
					<textarea cols=50 rows=5 name="PRJSIZE" style="width:650px"></textarea>
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					<textarea cols=50 rows=5 name="REMARK" style="width:650px"></textarea>
				</td>
			</tr>
			<tr>
				<th>工程类型</th>
				<td colspan="3">
					<div>房屋建筑类：</div>
					<eve:excheckbox
						dataInterface="dictionaryService.findTwoDatasForSelect" 
						name="PRJFUNCTIONNUM" width="749px;" clazz="checkbox validate[required]"
						dataParams="TBPRJFUNCTIONDIC:1">
					</eve:excheckbox></br></br>
					<div>市政工程类：</div>
					<eve:excheckbox
						dataInterface="dictionaryService.findTwoDatasForSelect" 
						name="PRJFUNCTIONNUM" width="749px;" clazz="checkbox validate[required]"
						dataParams="TBPRJFUNCTIONDIC:2">
					</eve:excheckbox>
					</span>
				</td>
			</tr>
			<tr>
				<th>结构类型</th>
				<td colspan="3">
					<input type="text" class="tab_text inputBackgroundCcc" name="STRUCTQUALTYPES"  maxlength="512" readonly="readonly" 
						placeholder="系统会根据用户填写的单位工程自动回填"  style="width:650px"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>施工许可证号</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]"
						name="SGXKZH" style="width:650px"/>
				</td>
			</tr>
			<tr>
				<th>相关单位工程</th>
				<td colspan="3">
					<input type="text" class="tab_text" 
						name="XGDWGC" style="width:650px"/>
				</td>
			</tr>
			<tr>
				<th>质量检测机构名称</th>
				<td colspan="3">
					<input type="text" class="tab_text" name="ZLJCJGMC" style="width:650px"/>
				</td>
			</tr>
			<tr>
				<th>质量检测机构组织机构代码</th>
				<td colspan="3">
					<input type="text" class="tab_text" name="ZLJCJGZJJGDM" style="width:650px"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>工程质量安全监督机构</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="QUALSUPERORG" 
						style="width:650px"/>
				</td>
			</tr>
			<tr>
				<th>建设单位意见</th>
				<td colspan="3">
					<textarea cols=50 rows=5 name="APPLYUNITREMARK" style="width:650px"></textarea>
				</td>
			</tr>
		</table>
	</div>



    <div class="bsboxT">
        <ul>
            <li class="on" style="background:none"><span>责任主体信息</span></li>
        </ul>
    </div>
    <div class="bsboxC">
        <table cellpadding="0" cellspacing="1" style="width:100%">
            <tr>
                <td style="height:200px;">
                    <table class="easyui-datagrid" rownumbers="false" pagination="false" striped="true"
                        id="cjgfzrztGrid" fitcolumns="true"
                        method="post" idfield="cjgfzrztName" checkonselect="false" 
                        selectoncheck="false" fit="true" border="false" 
                        nowrap="false" singleSelect="false"
                        data-options="autoSave:true,
                        method:'post',
                        onClickRow:onClickRow,
                        onLoadSuccess:isEdit,
                        url:'projectFinishManageController.do?cjgfzrztList&ywId=${busRecord.YW_ID }&projectCode=<c:if test="${PROJECT_CODE != null }">${PROJECT_CODE}</c:if><c:if test="${PROJECT_CODE == null }">${busRecord.PROJECTCODE }</c:if>'">
                        <thead>
                            <tr>
                                <th field="ck" checkbox="true"></th>
                                <th data-options="field:'cjgfzrztName',align:'left'" width="18%">参建各方责任主体类型</th>
                                <th data-options="field:'CORPNAME',align:'left'" width="35%">参建各方责任主体名称</th>
                                <th data-options="field:'LEGAL_NAME',align:'left'" width="15%">法定代表人</th>
                                <th data-options="field:'PERSONNAME',width:'15%',align:'left',
                                    editor:{
                                        type:'text'
                                    }
                                "
                                >项目负责人
                                </th>
                                <th data-options="field:'PERSONPHONE',width:'17%',align:'left',
                                    editor:{
                                        type:'text'
                                    }
                                "
                                >项目负责人联系电话
                                </th>
                            </tr>
                        </thead>
                    </table>
                </td>
            </tr>
        </table>
</div>
</div>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->