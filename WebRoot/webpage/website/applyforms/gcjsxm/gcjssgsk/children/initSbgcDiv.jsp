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
		
		//????????????????????????
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
			<th><font class="syj-color2">*</font>??????????????????</th>
			<td colspan="3"><input type="text" class="syj-input1 w838 validate[required]" value="${sbgc.SINGLEPRONAME}"
				name="${s.index}SINGLEPRONAME" maxlength="100" placeholder="?????????????????????"/></td>	
		</tr>	
		<tr>
			<th><font class="syj-color2">*</font>??????(?????????)????????????</th>
			<td colspan="3">
				<ul class="BUILDUNITS_UL"></ul>
			</td>	
		</tr>	
		<tr>
			<th>??????(?????????)????????????</th>
			<td colspan="3">
				<ul class="SUPERVISIONUNITS_UL"></ul>
			</td>	
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>??????????????????</th>
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
										content : '?????????'+$('#PROTYPE').find('option:selected').text(),
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
							VALUE: '??????',
							TEXT: '??????'
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
			<th><font class="syj-color2">*</font>????????????</th>
			<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"
				name="${s.index}ARCHHEIGHT" value="${sbgc.ARCHHEIGHT}" maxlength="32" placeholder="?????????????????????" />M</td>
			<th>????????????</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly"
				name="${s.index}ARCHAREA" value="${sbgc.ARCHAREA}"  placeholder="???????????????????????????????????????"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
				<div style="color:red;"> *?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????0???</div>
			</td>
		
		</tr>
		<tr class="${s.index}singleProMaintype_2_sbgc">	
			<th><font class="syj-color2">*</font>????????????</th>
			<td>
				<input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" value="${sbgc.MUNILENGTH}" 
				name="${s.index}MUNILENGTH" maxlength="16" placeholder="?????????????????????" />M
			</td>
			<th>????????????</th>
			<td>
				<input type="text" class="syj-input1  validate[],custom[JustNumber]"  onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);"
				name="${s.index}FLOORAREA"  value="${sbgc.FLOORAREA}"  placeholder="?????????????????????"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
		
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> ????????????????????????</th>
			<td colspan="3">
				<eve:radiogroup typecode="YesOrNo" width="130px" value="${sbgc.ISSUPERHIGHTBUILDING_SBGC}"
				fieldname="${s.index}ISSUPERHIGHTBUILDING_SBGC"></eve:radiogroup>
			</td>
		</tr>	
		<tr class="${s.index}singleProMaintype_1_sbgc">
			<th><span class="syj-color2">*</span> ??0.000????????????</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="${s.index}STRUCTUPFLOORNUM"  maxlength="16"  placeholder="???????????0.000????????????" value="${sbgc.STRUCTUPFLOORNUM}" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
			</td>
			<th><span class="syj-color2">*</span> ??0.000????????????</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="${s.index}STRUCTDWFLOORNUM"  maxlength="16"  placeholder="???????????0.000????????????" value="${sbgc.STRUCTDWFLOORNUM}" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
			</td>
		</tr>
		<tr class="${s.index}singleProMaintype_1_sbgc">
			<th><span class="syj-color2">*</span> ??0.000????????????</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${s.index}STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="???????????0.000????????????" value="${sbgc.STRUCTUPFLOORAREA_DWGC}" onblur="onlyNumber2(this);setSbArcharea('${s.index}');" onkeyup="onlyNumber2(this);" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
			<th><span class="syj-color2">*</span> ??0.000????????????</th>
			<td>
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${s.index}STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="???????????0.000????????????"  value="${sbgc.STRUCTDWFLOORAREA_DWGC}" onblur="onlyNumber2(this);setSbArcharea('${s.index}');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
			</td>
		</tr>
		<tr>
			<th>??????????????????</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="FOUNDSUPWAY"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.FOUNDSUPWAY}" 
				defaultEmptyText="???????????????????????????" name="${s.index}FOUNDSUPWAY" >
				</eve:eveselect>
			</td>
			<th>??????????????????</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1  validate[],custom[JustNumber]" placeholder="???????????????????????????"  name="${s.index}FOUNDDEPTH" onblur="onlyNumber3(this);" value="${sbgc.FOUNDDEPTH}"  onkeyup="onlyNumber3(this);"/>M
			</td>
		</tr>
		<tr>
			<th>?????????????????????</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="SLOPESUPWAY"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.SLOPESUPWAY}" 
				defaultEmptyText="??????????????????????????????" name="${s.index}SLOPESUPWAY">
				</eve:eveselect>
			</td>
			<th>????????????</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1 validate[],custom[JustNumber]"  placeholder="?????????????????????"  name="${s.index}SLOPEHEIGHT" onblur="onlyNumber3(this);" value="${sbgc.SLOPEHEIGHT}"  onkeyup="onlyNumber3(this);" />M
			</td>
		</tr>
		<tr>
			<th>????????????</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="REFRACTLEVEL"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.REFRACTLEVEL}" 
				defaultEmptyText="?????????????????????" name="${s.index}REFRACTLEVEL">
				</eve:eveselect>
			</td>
			<th><span class="syj-color2">*</span> ??????????????????</th>
			<td>
			  <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]"  placeholder="???????????????????????????"  name="${s.index}STRUCTMAXSPAN"  onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"  value="${sbgc.STRUCTMAXSPAN}" />M
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> ???????????????????????????</th>
			<td colspan="3">
			  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="${s.index}ITEMINVEST"  maxlength="16"  placeholder="????????????????????????????????????"  value="${sbgc.ITEMINVEST}" onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/><div style="color:red;;"> ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</div>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> ????????????????????????	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px"  value="${sbgc.ISSHOCKISOLATIONBUILDING_SBGC}" 
				fieldname="${s.index}ISSHOCKISOLATIONBUILDING_SBGC" ></eve:radiogroup>
			</td>
			<th><span class="syj-color2">*</span> ????????????????????????	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px"  value="${sbgc.ISEQUIPPEDARCHITECTURE_SBGC}" 
				fieldname="${s.index}ISEQUIPPEDARCHITECTURE_SBGC"></eve:radiogroup>
			</td>
		</tr>	
		<tr>
			<th><span class="syj-color2">*</span> ??????????????????	</th>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px" onclick="setSbgcGreenbuidinglevel(this.value,'${s.index}');"
				fieldname="${s.index}ISGREENBUILDING_SBGC"  value="${sbgc.ISGREENBUILDING_SBGC}" ></eve:radiogroup>
			</td>
			<th><span class="syj-color2">*</span> ??????????????????	</th>
			<td>
				<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="INFRALEVEL"
				dataInterface="dictionaryService.findDatasForSelect" value="${sbgc.INFRALEVEL}" 
				defaultEmptyText="???????????????????????????" name="${s.index}INFRALEVEL">
				</eve:eveselect>
			</td>
		</tr>	
		<tr class="${s.index}greenbuidinglevelTr_sbgc" style="display:none">
			<th><span class="syj-color2">*</span>??????????????????</th>
			<td colspan="3">
				<eve:eveselect clazz="syj-input1 w280 field_width" dataParams="GREENBUIDINGLEVEL"
				dataInterface="dictionaryService.findDatasForSelect"  value="${sbgc.GREENBUIDINGLEVEL}" 
				defaultEmptyText="???????????????????????????" name="${s.index}GREENBUIDINGLEVEL_SBGC">
				</eve:eveselect>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> ????????????</th>
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
			<th><span class="syj-color2">*</span> ????????????</th>
			<td colspan="3">
				<eve:excheckbox
					dataInterface="dictionaryService.findDatasForSelect"  
					name="${s.index}BASICQUALTYPE_SBGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="BASICQUALTYPE" value="${sbgc.BASICQUALTYPE}" >
				</eve:excheckbox>
			</td>
		</tr>
		<tr>
			<th><span class="syj-color2">*</span> ??????????????????</th>
			<td colspan="3">
				<eve:excheckbox
					dataInterface="dictionaryService.findDatasForSelect"
					name="${s.index}REBELQUAKELEVEL_SBGC" width="749px;" clazz="checkbox validate[required]"
					dataParams="REBELQUAKELEVEL" value="${sbgc.REBELQUAKELEVEL}" >
				</eve:excheckbox>
			</td>
		</tr>	
		<tr>
			<th> ??????</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[] w838" value="${sbgc.REMARK}"
				name="${s.index}REMARK" maxlength="100" placeholder="???????????????"/>
			</td>
		</tr>	
	</table>
	
	<div class="syj-title1" style="margin-top: 5px;margin-bottom: 5px;">
		<a href="javascript:void(0);" onclick="javascript:addSbgcChildrenDiv('${s.index}');" class="syj-addbtn" id="addSbgc" >??????</a>
	</div>
	<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3" id="${s.index}childrenSbgcTable">
		<tr>
			<th style="width: 24%;">???????????????</td>
			<th style="width: 14%;">??????????????????</td>
			<th style="width: 14%;">????????????</td>
			<th style="width: 12%;">????????????/?????????m???</td>
			<th style="width: 14%;">???????????????m2???</td>
			<th style="width: 12%;">???????????????????????????</td>
			<th style="width: 10%;">??????</td>
		</tr>	
	</table>
	<c:if test="${s.index>0}">
		<a  href="javascript:void(0);" onclick="javascript:delSbgcDiv(this);" class="syj-closebtn"></a>
	</c:if>
</div>
</c:forEach>