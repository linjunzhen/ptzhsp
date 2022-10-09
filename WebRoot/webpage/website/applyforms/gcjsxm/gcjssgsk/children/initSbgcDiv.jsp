<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style type="text/css">
.textbox{
	width: 280px!important;
	height: 40px!important;
	border: none!important;
}
.textbox .textbox-text{
	width: 280px!important;
	height: 40px!important;
	line-height: 40px!important;
	font-size: 16px!important;
	color: #000000!important;
	padding: 0 10px!important;
	box-sizing: border-box!important;
	border: 1px solid #c9deef!important;
}	
.syj-table2 tr th,.syj-table2 tr td{
	background: none;
	border: none;
}
</style>
<c:forEach items="${sbgcList}" var="sbgc" varStatus="s">
<div class="addBox">
	<script>
	$(function() {	
		setBuildUnits();
		setSupervisionUnits();
		//setInitBuildUnits('${dwgc.BUILDUNITS}');	
		//setInitSupervisionUnits('${dwgc.SUPERVISIONUNITS}');
		setSbgcGreenbuidinglevel('${sbgc.ISGREENBUILDING_SBGC}','${s.index}');
		setSbgcSingleProMaintype('${sbgc.SINGLEPROMAINTYPE}','${s.index}');
		
		var dicDesc = 1;
		if('${sbgc.SINGLEPROMAINTYPE}'=='01'){
		   dicDesc = 1;
		} else if('${sbgc.SINGLEPROMAINTYPE}'=='02'){
		   dicDesc = 2;
		}
		$('#${s.index}singleprotype_sbgc').combobox({
			url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc='+dicDesc,		
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight:'auto',
			required:true,
			editable:false
		});
		$('#${s.index}singleprosubtype_sbgc').combobox({
			url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc=${sbgc.SINGLEPROTYPE}',		
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight:'auto',
			required:true,
			editable:false
		});	
		var CHILDRENSBGC_JSON = '${sbgc.CHILDRENSBGC_JSON}';
		if(CHILDRENSBGC_JSON){					
			var childrenSbgcInfo = eval(CHILDRENSBGC_JSON);
			if(childrenSbgcInfo && childrenSbgcInfo.length>0){						
				initChildrenSbgc(childrenSbgcInfo,'${s.index}','${s.index}')
			}
		}
		
		//获取目标控件对象
		$("[name='${s.index}BUILDUNITS']").each(function(){
			var targetField = $(this);
			var type = targetField.attr("type");
			var tagName = targetField.get(0).tagName;
			var fieldValue = '${sbgc.BUILDUNITS}';
			if(type=="checkbox"){
				var checkBoxValue = targetField.val();
				var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				if(isChecked){
					$(this).attr("checked","checked");
				}
			}
		});
		$("[name='${s.index}SUPERVISIONUNITS']").each(function(){
			var targetField = $(this);
			var type = targetField.attr("type");
			var tagName = targetField.get(0).tagName;
			var fieldValue = '${sbgc.SUPERVISIONUNITS}';
			if(type=="checkbox"){
				var checkBoxValue = targetField.val();
				var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				if(isChecked){
					$(this).attr("checked","checked");
				}
			}
		});
	});
	</script>
	<input type="hidden" name= "s.index" value="${s.index}"/>
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		<tr>
			<th><font class="syj-color2">*</font>单位工程名称</th>
			<td colspan="3"><input type="text" class="syj-input1 w838 validate[required]" value="${sbgc.SINGLEPRONAME}"
				name="${s.index}SINGLEPRONAME" maxlength="100" placeholder="请输入单位名称"/></td>	
		</tr>	
		<tr>
			<th><font class="syj-color2">*</font>上部(地下室)施工单位</th>
			<td colspan="3">
				<ul class="BUILDUNITS_UL"></ul>
			</td>	
		</tr>	
		<tr>
			<th>上部(地下室)监理单位</th>
			<td colspan="3">
				<ul class="SUPERVISIONUNITS_UL"></ul>
			</td>	
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>单位工程类别</th>
			<td colspan="3">						
				<input name="${s.index}SINGLEPROMAINTYPE" id="${s.index}singlepromaintype_sbgc" value="${sbgc.SINGLEPROMAINTYPE}" class="easyui-combobox" 
					data-options="
						url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
						method:'post',
						valueField:'DIC_CODE',
						textField:'DIC_NAME',
						panelHeight:'auto',
						required:true,
						editable:false,
						onSelect:function(rec){
						   $('#${s.index}singleprotype_sbgc').combobox('clear');
						   $('#${s.index}singleprosubtype_sbgc').combobox('clear');
						   if(rec.DIC_CODE){
								/* var PROTYPE = $('#PROTYPE').val();
								if(rec.DIC_CODE!=PROTYPE && PROTYPE!=''){
									$('#${s.index}singlepromaintype').combobox('clear');
									art.dialog({
										content : '请选择'+$('#PROTYPE').find('option:selected').text(),
										icon : 'error',
										ok : true
									});
								   return;
								} */
							   var dicDesc = 1;
							   if(rec.DIC_CODE=='01'){
								   dicDesc = 1;
							   } else if(rec.DIC_CODE=='02'){
								   dicDesc = 2;
							   }
							   var url = 'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc='+dicDesc;
							   $('#${s.index}singleprotype_sbgc').combobox('reload',url); 
								setSbgcSingleProMaintype(rec.DIC_CODE,'${s.index}');
						   }
						}
				" />	
					<script>
						$('#${s.index}singlepromaintype_sbgc').combobox({
							url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
							method:'post',
							valueField:'DIC_CODE',
							textField:'DIC_NAME',
							panelHeight:'auto',
							required:true,
							editable:false
						});
					</script>				
				<input name="${s.index}SINGLEPROTYPE" id="${s.index}singleprotype_sbgc"  value="${sbgc.SINGLEPROTYPE}" class="easyui-combobox" 
					data-options="
						url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1',
						method:'post',
						valueField:'VALUE',
						textField:'TEXT',
						panelHeight:'auto',
						required:true,
						editable:false,
						onSelect:function(rec){
						   setSbgcSingleprosubtype(rec,'${s.index}');
						}
				" />
					<script>
						$('#${s.index}singleprotype_sbgc').combobox({
							url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1,2',
							method:'post',
							valueField:'VALUE',
							textField:'TEXT',
							panelHeight:'auto',
							required:true,
							editable:false
						});
					</script>		                					
				<input name="${s.index}SINGLEPROSUBTYPE" id="${s.index}singleprosubtype_sbgc"  value="${sbgc.SINGLEPROSUBTYPE}" class="easyui-combobox"  
					data-options="			                
						method:'post',
						valueField:'VALUE',
						textField:'TEXT',
						data: [{
							VALUE: '其他',
							TEXT: '其他'
						}],
						panelHeight:'auto',
						required:true,
						editable:false,
						onSelect:function(rec){	
						}
				" />
					<script>
						$('#${s.index}singleprosubtype_sbgc').combobox({
							url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc=100,300',
							method:'post',
							valueField:'VALUE',
							textField:'TEXT',
							panelHeight:'auto',
							required:true,
							editable:false
						});
						
					</script>	
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2" >		
		<tr class="${s.index}singleProMaintype_1_sbgc">	
			<th><font class="syj-color2">*</font>建筑高度</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"
				name="${s.index}ARCHHEIGHT" value="${sbgc.ARCHHEIGHT}" maxlength="32" placeholder="请输入建筑高度" />M</td>
			<th>建筑面积</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly"
				name="${s.index}ARCHAREA" value="${sbgc.ARCHAREA}"  placeholder="系统会根据用户填写自动回填"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
				<div style="color:red;"> *桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！</div>
			</td>
		
		</tr>
		<tr class="${s.index}singleProMaintype_2_sbgc">	
			<th><font class="syj-color2">*</font>市政长度</th>
			<td>
				<input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${sbgc.MUNILENGTH}" 
				name="${s.index}MUNILENGTH" maxlength="16" placeholder="请输入市政长度" />M
			</td>
			<th>路桥面积</th>
			<td>
				<input type="text" class="syj-input1  validate[],custom[JustNumber]"  onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);"
				name="${s.index}FLOORAREA"  value="${sbgc.FLOORAREA}"  placeholder="请输入路桥面积"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
		
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 是否超限高层建筑</th>
			<td colspan="3">
				<eve:radiogroup typecode="YesOrNo" width="130px" value="${sbgc.ISSUPERHIGHTBUILDING_SBGC}"
				fieldname="${s.index}ISSUPERHIGHTBUILDING_SBGC"></eve:radiogroup>
			</td>
		</tr>	
		<tr class="${s.index}singleProMaintype_1_sbgc">
			<th><span class="syj-color2">*</span> ±0.000以上层数</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="${s.index}STRUCTUPFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以上层数" value="${sbgc.STRUCTUPFLOORNUM}" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
			</td>
			<th><span class="syj-color2">*</span> ±0.000以下层数</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="${s.index}STRUCTDWFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以下层数" value="${sbgc.STRUCTDWFLOORNUM}" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
			</td>
		</tr>
		<tr class="${s.index}singleProMaintype_1_sbgc">
			<th><span class="syj-color2">*</span> ±0.000以上面积</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${s.index}STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="请输入±0.000以上面积" value="${sbgc.STRUCTUPFLOORAREA_DWGC}" onblur="onlyNumber2(this);setSbArcharea('${s.index}');" onkeyup="onlyNumber2(this);" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
			<th><span class="syj-color2">*</span> ±0.000以下面积</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${s.index}STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="请输入±0.000以下面积"  value="${sbgc.STRUCTDWFLOORAREA_DWGC}" onblur="onlyNumber2(this);setSbArcharea('${s.index}');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
		</tr>
		<tr>
			<th>基坑支护方法</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="FOUNDSUPWAY"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.FOUNDSUPWAY}" 
				defaultEmptyText="请选择基坑支护方法" name="${s.index}FOUNDSUPWAY" >
				</eve:eveselect>
			</td>
			<th>基坑开挖深度</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1  validate[],custom[JustNumber]" placeholder="请输入基坑开挖深度"  name="${s.index}FOUNDDEPTH" onblur="onlyNumber3(this);" value="${sbgc.FOUNDDEPTH}"  onkeyup="onlyNumber3(this);"/>M
			</td>
		</tr>
		<tr>
			<th>高边坡支护方法</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="SLOPESUPWAY"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.SLOPESUPWAY}" 
				defaultEmptyText="请选择高边坡支护方法" name="${s.index}SLOPESUPWAY">
				</eve:eveselect>
			</td>
			<th>边坡高度</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1 validate[],custom[JustNumber]"  placeholder="请输入边坡高度"  name="${s.index}SLOPEHEIGHT" onblur="onlyNumber3(this);" value="${sbgc.SLOPEHEIGHT}"  onkeyup="onlyNumber3(this);" />M
			</td>
		</tr>
		<tr>
			<th>耐火等级</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="REFRACTLEVEL"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.REFRACTLEVEL}" 
				defaultEmptyText="请选择耐火等级" name="${s.index}REFRACTLEVEL">
				</eve:eveselect>
			</td>
			<th><span class="syj-color2">*</span> 结构最大跨度</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]"  placeholder="请输入结构最大跨度"  name="${s.index}STRUCTMAXSPAN"  onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"  value="${sbgc.STRUCTMAXSPAN}" />M
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 工程总造价（万元）</th>
			<td colspan="3">
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${s.index}ITEMINVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）"  value="${sbgc.ITEMINVEST}" onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/><div style="color:red;;"> 指的是本单位工程的总造价，桩基工程先行办理施工许可时也要填写整个单位工程（含上部工程）的总造价。</div>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 是否为减隔震建筑	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px"  value="${sbgc.ISSHOCKISOLATIONBUILDING_SBGC}" 
				fieldname="${s.index}ISSHOCKISOLATIONBUILDING_SBGC" ></eve:radiogroup>
			</td>
			<th><span class="syj-color2">*</span> 是否为装配式建筑	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px"  value="${sbgc.ISEQUIPPEDARCHITECTURE_SBGC}" 
				fieldname="${s.index}ISEQUIPPEDARCHITECTURE_SBGC"></eve:radiogroup>
			</td>
		</tr>	
		<tr>
			<th><span class="syj-color2">*</span> 是否绿色建筑	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px" onclick="setSbgcGreenbuidinglevel(this.value,'${s.index}');"
				fieldname="${s.index}ISGREENBUILDING_SBGC"  value="${sbgc.ISGREENBUILDING_SBGC}" ></eve:radiogroup>
			</td>
			<th><span class="syj-color2">*</span> 基础建设等级	</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="INFRALEVEL"
				dataInterface="dictionaryService.findDatasForSelect" value="${sbgc.INFRALEVEL}" 
				defaultEmptyText="请选择基础建设等级" name="${s.index}INFRALEVEL">
				</eve:eveselect>
			</td>
		</tr>	
		<tr class="${s.index}greenbuidinglevelTr_sbgc" style="display:none">
			<th><span class="syj-color2">*</span>绿色建筑等级</th>
			<td colspan="3">
				<eve:eveselect clazz="syj-input1 w280 field_width" dataParams="GREENBUIDINGLEVEL"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.GREENBUIDINGLEVEL}" 
				defaultEmptyText="请选择基础建设等级" name="${s.index}GREENBUIDINGLEVEL_SBGC">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 结构类型</th>
			<td colspan="3">
				<span class="${s.index}singleProMaintype_1_jglx_sbgc">
				<eve:excheckbox
					dataInterface="dictionaryService.findTwoDatasForSelect"  
					name="${s.index}STRUCTQUALTYPE_SBGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="STRUCTQUALTYPE:2"  value="${sbgc.STRUCTQUALTYPE}" >
				</eve:excheckbox>
				</span>
				<span class="${s.index}singleProMaintype_2_jglx_sbgc">
				<eve:excheckbox
					dataInterface="dictionaryService.findTwoDatasForSelect"  
					name="${s.index}STRUCTQUALTYPE_SBGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="STRUCTQUALTYPE:1" value="${sbgc.STRUCTQUALTYPE}">
				</eve:excheckbox>
				</span>
			</td>
		</tr>	
		<tr>
			<th><span class="syj-color2">*</span> 基础类型</th>
			<td colspan="3">
				<eve:excheckbox
					dataInterface="dictionaryService.findDatasForSelect"  
					name="${s.index}BASICQUALTYPE_SBGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="BASICQUALTYPE" value="${sbgc.BASICQUALTYPE}" >
				</eve:excheckbox>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 结构抗震等级</th>
			<td colspan="3">
				<eve:excheckbox
					dataInterface="dictionaryService.findDatasForSelect"
					name="${s.index}REBELQUAKELEVEL_SBGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="REBELQUAKELEVEL" value="${sbgc.REBELQUAKELEVEL}" >
				</eve:excheckbox>
			</td>
		</tr>	
		<tr>
			<th> 备注</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[] w838" value="${sbgc.REMARK}"
				name="${s.index}REMARK" maxlength="100" placeholder="请输入备注"/>
			</td>
		</tr>	
	</table>
	
	<div class="syj-title1" style="margin-top: 5px;margin-bottom: 5px;">
		<a href="javascript:void(0);" onclick="javascript:addSbgcChildrenDiv('${s.index}');" class="syj-addbtn" id="addSbgc" >添加</a>
	</div>
	<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3" id="${s.index}childrenSbgcTable">
		<tr>
			<th style="width: 24%;">子单位名称</td>
			<th style="width: 14%;">单位工程类别</td>
			<th style="width: 14%;">结构类型</td>
			<th style="width: 12%;">建筑高度/长度（m）</td>
			<th style="width: 14%;">建筑面积（m2）</td>
			<th style="width: 12%;">工程总造价（万元）</td>
			<th style="width: 10%;">操作</td>
		</tr>	
	</table>
	<c:if test="${s.index>0}">
		<a  href="javascript:void(0);" onclick="javascript:delSbgcDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>