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
	   /**
     * 删除责任主体信息
     */
    function deleteZRInfo() {
        AppUtil.deleteDataGridRecord("projectFinishManageController.do?multiDelZrzt",
                "cjgfzrztGrid");
    };
    /**
     * 编辑责任主体信息
     */
    function editZRInfo() {
        var entityId = AppUtil.getEditDataGridRecord("cjgfzrztGrid");
        if (entityId) {
            showZrztxxWindow(entityId);
        }
    }


    /**
     * 显示责任主体信息对话框
     */
    function showZrztxxWindow(entityId) {
        var prj_num = $('input[name="PRJNUM"]').val();
        var prj_code = document.getElementById("PROJECTCODE").value;
        $.dialog.open("projectFinishManageController.do?zrztInfo&entityId=" + 
                entityId+"&prj_num="+prj_num+"&prj_code="+prj_code, {
            title : "责任主体信息",
            width : "800px",
            height : "350px",
            lock : true,
            resize : false
        }, false);
    }
	;


	function setJglxInfo(index) {
		//结构类型
		var STRUCTQUALTYPES = "";
		$("#jgysbaDiv").children("div").each(function(i) {
			$(this).find("[name$='STRUCTQUALTYPE'][type='checkbox']").each(function() {
				console.log($(this).val() + " : " + $(this).is(':checked'));
				var checked = $(this).is(':checked');
				if (checked) {
					var val = $(this).val();
					if (STRUCTQUALTYPES == "" || STRUCTQUALTYPES.indexOf(val) == -1) {
						STRUCTQUALTYPES += ($(this).val() + ",");
					}
				}
			});
		});
		if (STRUCTQUALTYPES != "") {
			STRUCTQUALTYPES = STRUCTQUALTYPES.substring(0, STRUCTQUALTYPES.lastIndexOf(","));
		}
		$("[name='STRUCTQUALTYPES']").val(STRUCTQUALTYPES);
	}
</script>
<style>
    .jgysBtn{
        background: #62a1cf none repeat scroll 0 0;color: #fff;
        display: inline-block;height: 26px;left: -5px;top: 1px;
        line-height: 26px;position: relative;text-align: center;
        width: 59px;margin-left: 5px;
    }
	.bscolor1{
		font-size: 15px;
	}
</style>
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>竣工验收备案信息</span></li>
		</ul>
	</div>
	<div id="jgysbaDiv">
	<div class="bsboxC" >
		<table cellpadding="0" cellspacing="0" class="bstable1" style="table-layout: auto;">
			<tr>
				<th ><span class="bscolor1">*</span>报建编号</th>
				<td colspan="3">
					<c:if test="${busRecord.PRJNUM==''}">
						<input type="text" maxlength="32" class="tab_text validate[required] w838" name="PRJNUM" value="${busRecord.PRJNUM}"/>
					</c:if>
					<c:if test="${busRecord.PRJNUM!=''}">
						<input type="text" class="tab_text w838" name="PRJNUM" value="${busRecord.PRJNUM}"  readonly/>
					</c:if><br>
					<span class="bscolor1">注：至2020年1月10日起的项目报建编号系统会自动获取，2020年1月10日前的项目报建编号，请登录“福建省公共资源交易电子行政监督平台”地址为：ggzyjd.fj.gov.cn，获取后填入。</span>
				</td>
			</tr>
			<tr>
				<th ><span class="bscolor1">*</span>建设单位</th>
				<td >
					<input type="text" class="tab_text validate[required]" name="BUILDCORPNAME" />
				</td>
				<th ><span class="bscolor1">*</span>所有制性质</th>
				<td >
					<eve:eveselect clazz="tab_text w280" dataParams="OWNERSHIP"
						dataInterface="dictionaryService.findDatasForSelect"
						name="OWNERSHIP">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>备案机关</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="BAJG" style="width:848px;"/>
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
					<input type="text" class="tab_text validate[required]" name="CONADDRESS" style="width:848px;"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>建设单位经办人姓名</th>
				<td>
					<input type="text" class="tab_text validate[required]" name="JBRNAME"/>
				</td>
				<th><span class="bscolor1">*</span>建设单位经办人证件类型</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="TBIDCARDTYPEDIC" 
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
					<input type="text" class="tab_text validate[required]" name="PROJECT_NAME" style="width:848px;"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>工程地点</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="PROADDRESS" style="width:848px;"/>
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
					<input type="text" class="tab_text validate[required]" name="PROCOST" />
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
				<th><span class="bscolor1">*</span> 公开方式</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="OPENWAY"
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
				<th><span class="bscolor1">*</span>实际竣工验收日期</th>
				<td>
					<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
						class="tab_text validate[required] Wdate" name="SJJGRQ" />
				</td>
			</tr>
			<tr>
				<th>实际建设规模</th>
				<td colspan="3">
					<textarea cols=50 rows=5 name="PRJSIZE" style="width:848px;border: 1px solid #c9deef!important;"></textarea>
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					<textarea cols=50 rows=5 name="REMARK" style="width:848px;border: 1px solid #c9deef!important;"></textarea>
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
						dataParams="TBPRJFUNCTIONDIC:2" >
					</eve:excheckbox>
					</span>
				</td>
			</tr>
			<tr>
				<th>结构类型</th>
				<td colspan="3" id="jglxTd">
					<input type="text" class="tab_text inputBackgroundCcc" name="STRUCTQUALTYPES"  maxlength="512" readonly="readonly" 
						placeholder="系统会根据用户填写的单位工程自动回填"  style="width:848px" />
				</td>
				 <td colspan="3" id="jglxTd2" style="display:none;">
				         <eve:excheckbox
                            dataInterface="dictionaryService.findTwoDatasForSelect" onclick="setJglxInfo('')" 
                            name="STRUCTQUALTYPE" width="749px;" clazz="checkbox validate[required]"
                            dataParams="STRUCTQUALTYPE:1">
                        </eve:excheckbox>
                        <eve:excheckbox
                            dataInterface="dictionaryService.findTwoDatasForSelect" onclick="setJglxInfo('')" 
                            name="STRUCTQUALTYPE" width="749px;" clazz="checkbox validate[required]"
                            dataParams="STRUCTQUALTYPE:2">
                        </eve:excheckbox>
                    </td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>施工许可证号</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]"
						name="SGXKZH" style="width:848px"/>
				</td>
			</tr>
			<tr>
				<th>相关单位工程</th>
				<td colspan="3">
					<input type="text" class="tab_text" 
						name="XGDWGC" style="width:848px"/>
				</td>
			</tr>
			<tr>
				<th>质量检测机构名称</th>
				<td colspan="3">
					<input type="text" class="tab_text" name="ZLJCJGMC" style="width:848px" />
				</td>
			</tr>
			<tr>
				<th>质量检测机构组织机构代码</th>
				<td colspan="3">
					<input type="text" class="tab_text" name="ZLJCJGZJJGDM" style="width:848px" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>工程质量安全监督机构</th>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" name="QUALSUPERORG" 
						style="width:848px;"/>
				</td>
			</tr>
			<tr>
				<th>建设单位意见</th>
				<td colspan="3">
					<textarea class="tab_text" cols=50 rows=5 name="APPLYUNITREMARK" style="width:848px;height: 100px!important;" ></textarea>
				</td>
			</tr>
		</table>
	
</div>
	</div>
</div>
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>责任主体单位信息</span></li>
		</ul>
	</div>
	<div class="bsboxC">			
		<table cellpadding="0" cellspacing="1" style="width:100%">
		          <tr id="zljgysBtn" style="display:none;" >
                <td>
                    <a href="javascript:void(0);" class="jgysBtn" onclick="showZrztxxWindow()">新增</a>
                    <a href="javascript:void(0);" class="jgysBtn" onclick="editZRInfo()">修改</a>
                    <a href="javascript:void(0);" class="jgysBtn" onclick="deleteZRInfo()">删除</a>
                </td>
            </tr>
			<tr>
				<td style="height:200px;">
					<table class="easyui-datagrid" rownumbers="false" pagination="false" striped="true"
						id="cjgfzrztGrid" fitcolumns="true"
						method="post" idfield="ID" checkonselect="true" 
						selectoncheck="true" fit="true" border="false" 
						nowrap="false" singleSelect="false"
						url="projectFinishManageController.do?cjgfzrztList&projectCode=
						<c:if test="${projectCode != null }">${projectCode}</c:if>
						<c:if test="${projectCode == null }">${busRecord.PROJECTCODE }</c:if>">
						<thead>
							<tr>
								<!-- <th field="ck" checkbox="true"></th> -->
								<th data-options="field:'ID',hidden:true">ID</th>
								<th data-options="field:'cjgfzrztName',align:'left'" width="18%">参建各方责任主体类型</th>
								<th data-options="field:'CORPNAME',align:'left'" width="35%">参建各方责任主体名称</th>
								<th data-options="field:'LEGAL_NAME',align:'left'" width="15%">法定代表人</th>				
								<th data-options="field:'PERSONNAME',align:'left'" width="15%">项目负责人</th>
								<th data-options="field:'PERSONPHONE',align:'left'" width="17%">项目负责人联系电话</th>
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