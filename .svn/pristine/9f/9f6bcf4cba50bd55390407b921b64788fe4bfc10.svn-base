<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(function() {
    var currentTime = '${currentTime}';
    setZjgcSingleProMaintype('01',currentTime);
 });
 </script>
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
<div style="position:relative;">
	<input type="hidden" name= "currentTime" value="${currentTime}"/>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>单位工程名称</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required] w838" 
				name="${currentTime}SINGLEPRONAME" maxlength="100" placeholder="请输入单位名称"/></td>	
		</tr>	
		<tr>
			<th><font class="syj-color2">*</font>桩基施工单位</th>
			<td colspan="3">
				<ul class="BUILDUNITS_UL"></ul>
			</td>	
		</tr>	
		<tr>
			<th>桩基监理单位</th>
			<td colspan="3">
				<ul class="SUPERVISIONUNITS_UL"></ul>
			</td>	
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>单位工程类别</th>
			<td colspan="3">						
				<input name="${currentTime}SINGLEPROMAINTYPE" id="${currentTime}singlepromaintype_zjgc" class="easyui-combobox" 
					data-options="
						url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
						method:'post',
						valueField:'DIC_CODE',
						textField:'DIC_NAME',
						panelHeight:'auto',
						required:true,
						editable:false,
						onSelect:function(rec){
						   $('#${currentTime}singleprotype_zjgc').combobox('clear');
						   $('#${currentTime}singleprosubtype_zjgc').combobox('clear');
						   if(rec.DIC_CODE){
								/* var PROTYPE = $('#PROTYPE').val();
								if(rec.DIC_CODE!=PROTYPE && PROTYPE!=''){
									$('#${currentTime}singlepromaintype_zjgc').combobox('clear');
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
							   $('#${currentTime}singleprotype_zjgc').combobox('reload',url); 
								setZjgcSingleProMaintype(rec.DIC_CODE,'${currentTime}');
						   }
						}
				" />	
					<script>
						$('#${currentTime}singlepromaintype_zjgc').combobox({
							url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
							method:'post',
							valueField:'DIC_CODE',
							textField:'DIC_NAME',
							panelHeight:'auto',
							required:true,
							editable:false
						});
					</script>				
				<input name="${currentTime}SINGLEPROTYPE" id="${currentTime}singleprotype_zjgc" class="easyui-combobox"
					data-options="
						url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1',
						method:'post',
						valueField:'VALUE',
						textField:'TEXT',
						panelHeight:'auto',
						required:true,
						editable:false,
						onSelect:function(rec){
						   setZjgcSingleprosubtype(rec,'${currentTime}');
						}
				" />
					<script>
						$('#${currentTime}singleprotype_zjgc').combobox({
							url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1,2',
							method:'post',
							valueField:'VALUE',
							textField:'TEXT',
							panelHeight:'auto',
							required:true,
							editable:false
						});
					</script>		                					
				<input name="${currentTime}SINGLEPROSUBTYPE" id="${currentTime}singleprosubtype_zjgc" class="easyui-combobox"
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
						$('#${currentTime}singleprosubtype_zjgc').combobox({
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
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">		
		<tr class="${currentTime}singleProMaintype_1_zjgc">	
			<th><font class="syj-color2">*</font>建筑高度</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"
				name="${currentTime}ARCHHEIGHT" maxlength="32" placeholder="请输入建筑高度" />M</td>
			<th>建筑面积</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly"
				name="${currentTime}ARCHAREA"  placeholder="系统会根据用户填写自动回填"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
				<div style="color:red;;"> *桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！</div>
			</td>
		
		</tr>
		<tr class="${currentTime}singleProMaintype_2_zjgc">	
			<th><font class="syj-color2">*</font>市政长度</th>
			<td>
				<input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"
				name="${currentTime}MUNILENGTH" maxlength="16" placeholder="请输入市政长度" />M
			</td>
			<th>路桥面积</th>
			<td>
				<input type="text" class="syj-input1  validate[],custom[JustNumber]"  onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);"
				name="${currentTime}FLOORAREA"  placeholder="请输入路桥面积"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
		
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 是否超限高层建筑</th>
			<td colspan="3">
				<eve:radiogroup typecode="YesOrNo" width="130px"
				fieldname="${currentTime}ISSUPERHIGHTBUILDING_ZJGC"></eve:radiogroup>
			</td>
		</tr>	
		<tr class="${currentTime}singleProMaintype_1_zjgc">
			<th><span class="syj-color2">*</span> ±0.000以上层数</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="${currentTime}STRUCTUPFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以上层数" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
			</td>
			<th><span class="syj-color2">*</span> ±0.000以下层数</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="${currentTime}STRUCTDWFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以下层数" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
			</td>
		</tr>
		<tr class="${currentTime}singleProMaintype_1_zjgc">
			<th><span class="syj-color2">*</span> ±0.000以上面积</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${currentTime}STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="请输入±0.000以上面积" onblur="onlyNumber2(this);setArcharea('${currentTime}');" onkeyup="onlyNumber2(this);" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
			<th><span class="syj-color2">*</span> ±0.000以下面积</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${currentTime}STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="请输入±0.000以下面积" onblur="onlyNumber2(this);setArcharea('${currentTime}');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
		</tr>
		<tr>
			<th>基坑支护方法</th>
			<td>
				<eve:eveselect clazz="syj-input1 validate[] field_width w280" dataParams="FOUNDSUPWAY"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择基坑支护方法" name="${currentTime}FOUNDSUPWAY" >
				</eve:eveselect>
			</td>
			<th>基坑开挖深度</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1  validate[],custom[JustNumber]" placeholder="请输入基坑开挖深度"  name="${currentTime}FOUNDDEPTH" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
			</td>
		</tr>
		<tr>
			<th>高边坡支护方法</th>
			<td>
				<eve:eveselect clazz="syj-input1 validate[] field_width w280" dataParams="SLOPESUPWAY"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择高边坡支护方法" name="${currentTime}SLOPESUPWAY">
				</eve:eveselect>
			</td>
			<th>边坡高度</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1 validate[],custom[JustNumber]"  placeholder="请输入边坡高度"  name="${currentTime}SLOPEHEIGHT" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />M
			</td>
		</tr>
		<tr>
			<th>耐火等级</th>
			<td>
				<eve:eveselect clazz="syj-input1 validate[] field_width w280" dataParams="REFRACTLEVEL"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择耐火等级" name="${currentTime}REFRACTLEVEL">
				</eve:eveselect>
			</td>
			<th><span class="syj-color2">*</span> 结构最大跨度</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]"  placeholder="请输入结构最大跨度"  name="${currentTime}STRUCTMAXSPAN"  onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />M
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 工程总造价（万元）</th>
			<td colspan="3">
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${currentTime}ITEMINVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）"  onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/><div style="color:red;;"> 指的是本单位工程的总造价，桩基工程先行办理施工许可时也要填写整个单位工程（含上部工程）的总造价。</div>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 是否为减隔震建筑	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px"
				fieldname="${currentTime}ISSHOCKISOLATIONBUILDING_ZJGC" ></eve:radiogroup>
			</td>
			<th><span class="syj-color2">*</span> 是否为装配式建筑	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px"
				fieldname="${currentTime}ISEQUIPPEDARCHITECTURE_ZJGC"></eve:radiogroup>
			</td>
		</tr>	
		<tr>
			<th><span class="syj-color2">*</span> 是否绿色建筑	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px" onclick="setZjgcGreenbuidinglevel(this.value,'${currentTime}');"
				fieldname="${currentTime}ISGREENBUILDING_ZJGC" ></eve:radiogroup>
			</td>
			<th><span class="syj-color2">*</span> 基础建设等级	</th>
			<td>
				<eve:eveselect clazz="syj-input1 validate[required] field_width w280" dataParams="INFRALEVEL"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择基础建设等级" name="${currentTime}INFRALEVEL">
				</eve:eveselect>
			</td>
		</tr>	
		<tr class="${currentTime}greenbuidinglevelTr_zjgc" style="display:none">
			<th><span class="syj-color2">*</span>绿色建筑等级</th>
			<td colspan="3">
				<eve:eveselect clazz="syj-input1 field_width w280" dataParams="GREENBUIDINGLEVEL"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择基础建设等级" name="${currentTime}GREENBUIDINGLEVEL_ZJGC">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 结构类型</th>
			<td colspan="3">
				<span class="${currentTime}singleProMaintype_1_jglx_zjgc">
				<eve:excheckbox
					dataInterface="dictionaryService.findTwoDatasForSelect"  
					name="${currentTime}STRUCTQUALTYPE_ZJGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="STRUCTQUALTYPE:2" >
				</eve:excheckbox>
				</span>
				<span class="${currentTime}singleProMaintype_2_jglx_zjgc">
				<eve:excheckbox
					dataInterface="dictionaryService.findTwoDatasForSelect"  
					name="${currentTime}STRUCTQUALTYPE_ZJGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="STRUCTQUALTYPE:1" >
				</eve:excheckbox>
				</span>
			</td>
		</tr>	
		<tr>
			<th><span class="syj-color2">*</span> 基础类型</th>
			<td colspan="3">
				<eve:excheckbox
					dataInterface="dictionaryService.findDatasForSelect" 
					name="${currentTime}BASICQUALTYPE_ZJGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="BASICQUALTYPE" >
				</eve:excheckbox>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> 结构抗震等级</th>
			<td colspan="3">
				<eve:excheckbox
					dataInterface="dictionaryService.findDatasForSelect"
					name="${currentTime}REBELQUAKELEVEL_ZJGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="REBELQUAKELEVEL">
				</eve:excheckbox>
			</td>
		</tr>	
		<tr>
			<th> 备注</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[] w838"
				name="${currentTime}REMARK" maxlength="100" placeholder="请输入备注"/>
			</td>
		</tr>	
	</table>
	
	<div class="syj-title1" style="margin-top: 5px;margin-bottom: 5px;">
		<a href="javascript:void(0);" onclick="javascript:addZjgcChildrenDiv('${currentTime}');" class="syj-addbtn" id="addZjgc" >添加</a>
	</div>
	<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3" id="${currentTime}childrenZjgcTable">
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
	<a  href="javascript:void(0);" onclick="javascript:delZjgcDiv(this);" class="syj-closebtn"></a>
</div>