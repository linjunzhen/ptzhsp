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
</style>
<form action="" id="DWGC_FORM"  >
	<div style="color:red;">
	<p>注意事项：</p>
	<p>&nbsp;&nbsp;1、针对房建工程，请建设单位认真核实该项目本次申报的施工许可范围是否包括室外工程（包括“室外设施”和“附属建筑及室外环境”单位工程）；</p>
	<p>&nbsp;&nbsp;2、桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！
	</p>
	</div>
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addDwgcDiv();" class="syj-addbtn" id="addDwgc" >添 加</a>
		<span>单位工程</span>
	</div>
	<div id="dwgcDiv">
		<c:if test="${empty dwgcList}">	
		<div style="position:relative;">	
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>单位工程名称</th>
					<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]" 
						name="SINGLEPRONAME" maxlength="100" placeholder="请输入单位名称"/></td>	
				</tr>	
				<tr>
					<th><font class="syj-color2">*</font>施工单位</th>
					<td colspan="3">
						<ul class="BUILDUNITS_UL"></ul>
					</td>	
				</tr>	
				<tr>
					<th>监理单位</th>
					<td colspan="3">
						<ul class="SUPERVISIONUNITS_UL"></ul>
					</td>	
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>单位工程类别</th>
					<td colspan="3">						
						<input name="SINGLEPROMAINTYPE" id="singlepromaintype" class="easyui-combobox"
							data-options="
								url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
								method:'post',
								valueField:'DIC_CODE',
								textField:'DIC_NAME',
								panelHeight:'auto',
								required:true,
								editable:false,
								onSelect:function(rec){
								   $('#singleprotype').combobox('clear');
								   $('#singleprosubtype').combobox('clear');
								   if(rec.DIC_CODE){
										/* var PROTYPE = $('#PROTYPE').val();
									    if(rec.DIC_CODE!=PROTYPE && PROTYPE!=''){
											$('#singlepromaintype').combobox('clear');
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
									   $('#singleprotype').combobox('reload',url); 
										setSingleProMaintype(rec.DIC_CODE,'');
								   }
								}
						" />					
						<input name="SINGLEPROTYPE" id="singleprotype" class="easyui-combobox" style="width:182px; height:26px;" 
							data-options="
								url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1',
								method:'post',
								valueField:'VALUE',
								textField:'TEXT',
								panelHeight:'auto',
								required:true,
								editable:false,
								onSelect:function(rec){
								   setSingleprosubtype(rec,'');
								}
						" />	                					
						<input name="SINGLEPROSUBTYPE" id="singleprosubtype" class="easyui-combobox" style="width:182px; height:26px;" 
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
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">		
				<tr class="singleProMaintype_1">	
					<th><font class="syj-color2">*</font>建筑高度</th>
					<td><input type="text" class="syj-input1" onblur="onlyNumber3(this);setDwgcInfo('');" onkeyup="onlyNumber3(this);"
						name="ARCHHEIGHT" maxlength="32" placeholder="请输入建筑高度" />M</td>
					<th>建筑面积</th>
					<td>
						<input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly"
						name="ARCHAREA"  placeholder="系统会根据用户填写自动回填"   maxlength="16"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
						<div style="color:red;"> *桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！</div>
					</td>
				
				</tr>
				<tr class="singleProMaintype_2">	
					<th><font class="syj-color2">*</font>市政长度</th>
					<td>
						<input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);setDwgcInfo('');" onkeyup="onlyNumber3(this);" name="MUNILENGTH" maxlength="16" placeholder="请输入市政长度" style="width:90%"/>M
					</td>
					<th>路桥面积</th>
					<td>
						<input type="text" class="syj-input1  validate[],custom[JustNumber]"  onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);"
						name="FLOORAREA"  placeholder="请输入路桥面积"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
					</td>
				
				</tr>
				<tr>
					<th><span class="syj-color2">*</span> 是否超限高层建筑</th>
					<td colspan="3">
						<eve:radiogroup typecode="YesOrNo" width="130px"
						fieldname="ISSUPERHIGHTBUILDING"></eve:radiogroup>
					</td>
				</tr>	
				<tr class="singleProMaintype_1">
					<th><span class="syj-color2">*</span> ±0.000以上层数</th>
					<td>
					  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTUPFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以上层数" onblur="onlyNumber3(this);setDwgcInfo('');" onkeyup="onlyNumber3(this);" />
					</td>
					<th><span class="syj-color2">*</span> ±0.000以下层数</th>
					<td>
					  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTDWFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以下层数" onblur="onlyNumber3(this);setDwgcInfo('');" onkeyup="onlyNumber3(this);" />
					</td>
				</tr>
				<tr class="singleProMaintype_1">
					<th><span class="syj-color2">*</span> ±0.000以上面积</th>
					<td>
					  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="请输入±0.000以上面积" onblur="onlyNumber2(this);setDwgcInfo('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
					</td>
					<th><span class="syj-color2">*</span> ±0.000以下面积</th>
					<td>
					  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="请输入±0.000以下面积" onblur="onlyNumber2(this);setDwgcInfo('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
					</td>
				</tr>
				<tr>
					<th>基坑支护方法</th>
					<td>
						<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="FOUNDSUPWAY"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择基坑支护方法" name="FOUNDSUPWAY" >
						</eve:eveselect>
					</td>
					<th>基坑开挖深度</th>
					<td>
					  <input type="text" maxlength="16" class="syj-input1  validate[],custom[JustNumber]" placeholder="请输入基坑开挖深度" name="FOUNDDEPTH" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />M
					</td>
				</tr>
				<tr>
					<th>高边坡支护方法</th>
					<td>
						<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="SLOPESUPWAY"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择高边坡支护方法" name="SLOPESUPWAY" id="SLOPESUPWAY">
						</eve:eveselect>
					</td>
					<th>边坡高度</th>
					<td>
					  <input type="text" maxlength="16" class="syj-input1 validate[],custom[JustNumber]" placeholder="请输入边坡高度" name="SLOPEHEIGHT"   onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
					</td>
				</tr>
				<tr>
					<th>耐火等级</th>
					<td>
						<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="REFRACTLEVEL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择耐火等级" name="REFRACTLEVEL" id="REFRACTLEVEL">
						</eve:eveselect>
					</td>
					<th><span class="syj-color2">*</span> 结构最大跨度</th>
					<td>
					  <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]"  placeholder="请输入结构最大跨度" name="STRUCTMAXSPAN"  onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
					</td>
				</tr>
				<tr>
					<th><span class="syj-color2">*</span> 工程总造价（万元）</th>
					<td colspan="3">
					  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="ITEMINVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）" onblur="onlyNumber6(this);setDwgcInfo('');" onkeyup="onlyNumber6(this);"/><div style="color:red;width:90%;"> 指的是本单位工程的总造价，桩基工程先行办理施工许可时也要填写整个单位工程（含上部工程）的总造价。</div>
					</td>
				</tr>
				<tr>
					<th><span class="syj-color2">*</span> 是否为减隔震建筑	</th>
					<td>
						<eve:radiogroup typecode="YesOrNo" width="130px"
						fieldname="ISSHOCKISOLATIONBUILDING"></eve:radiogroup>
					</td>
					<th><span class="syj-color2">*</span> 是否为装配式建筑	</th>
					<td>
						<eve:radiogroup typecode="YesOrNo" width="130px"
						fieldname="ISEQUIPPEDARCHITECTURE"></eve:radiogroup>
					</td>
				</tr>	
				<tr>
					<th><span class="syj-color2">*</span> 是否绿色建筑	</th>
					<td>
						<eve:radiogroup typecode="YesOrNo" width="130px" onclick="setGreenbuidinglevel(this.value,'');"
						fieldname="ISGREENBUILDING_DWGC"></eve:radiogroup>
					</td>
					<th><span class="syj-color2">*</span> 基础建设等级	</th>
					<td>
						<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="INFRALEVEL"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择基础建设等级" name="INFRALEVEL">
						</eve:eveselect>
					</td>
				</tr>	
				<tr class="greenbuidinglevelTr" style="display:none">
					<th><span class="syj-color2">*</span>绿色建筑等级</th>
					<td colspan="3">
						<eve:eveselect clazz="syj-input1 w280 field_width" dataParams="GREENBUIDINGLEVEL"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择基础建设等级" name="GREENBUIDINGLEVEL">
						</eve:eveselect>
					</td>
				</tr>	
				<tr>
					<th><span class="syj-color2">*</span> 结构类型</th>
					<td colspan="3">
						<span class="singleProMaintype_1_jglx">
						<eve:excheckbox
							dataInterface="dictionaryService.findTwoDatasForSelect" onclick="setDwgcInfo('')" 
							name="STRUCTQUALTYPE" clazz="checkbox validate[required]" width="780px"
							dataParams="STRUCTQUALTYPE:2">
						</eve:excheckbox>
						</span>
						<span class="singleProMaintype_2_jglx">
						<eve:excheckbox
							dataInterface="dictionaryService.findTwoDatasForSelect" onclick="setDwgcInfo('')" 
							name="STRUCTQUALTYPE"  clazz="checkbox validate[required]" width="780px"
							dataParams="STRUCTQUALTYPE:1">
						</eve:excheckbox>
						</span>
					</td>
				</tr>	
				<tr>
					<th><span class="syj-color2">*</span> 基础类型</th>
					<td colspan="3">
						<eve:excheckbox
							dataInterface="dictionaryService.findDatasForSelect" onclick="setDwgcInfo('')" 
							name="BASICQUALTYPE" clazz="checkbox validate[required]" width="780px"
							dataParams="BASICQUALTYPE">
						</eve:excheckbox>
					</td>
				</tr>
				<tr>
					<th><span class="syj-color2">*</span> 结构抗震等级</th>
					<td colspan="3">
						<eve:excheckbox
							dataInterface="dictionaryService.findDatasForSelect"
							name="REBELQUAKELEVEL" clazz="checkbox validate[required]" width="780px"
							dataParams="REBELQUAKELEVEL">
						</eve:excheckbox>
					</td>
				</tr>	
				<tr>
					<th> 备注</th>
					<td colspan="3">
						<input type="text" class="syj-input1 validate[] w878"
						name="REMARK" maxlength="100" placeholder="请输入备注"/>
					</td>
				</tr>	
			</table>
			
			<div class="syj-title1" style="margin-top: 5px;margin-bottom: 5px;">
				<a href="javascript:void(0);" onclick="javascript:addDwgcChildrenDiv('');" class="syj-addbtn" id="addDwgc" >添加</a>
			</div>
			<table cellpadding="0" cellspacing="1" class="syj-table2 syj-table3" id="childrenDwgcTable">
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

		</div>
		</c:if>
		<c:if test="${!empty dwgcList}">
			<jsp:include page="./children/initDwgcDiv.jsp"></jsp:include>		
		</c:if>
	</div>
	
	<div id="zjgcDiv">
	   <div class="syj-title1 ">
        <a href="javascript:void(0);" onclick="javascript:addZjDiv();" class="syj-addbtn" id="addZjgc" >添 加</a>
        <span>桩基（基础）工程</span>
    </div>
    <div id="zjjcgcDiv">
        <c:if test="${empty zjgcList}"> 
        <div style="position:relative;">    
            <table cellpadding="0" cellspacing="0" class="syj-table2 ">
                <tr>
                    <th><font class="syj-color2">*</font>单位工程名称</th>
                    <td colspan="3"><input type="text" class="syj-input1  validate[required]" 
                        name="SINGLEPRONAME" maxlength="100" placeholder="请输入单位名称"/></td>   
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
                        <input name="SINGLEPROMAINTYPE" id="singlepromaintype_zjgc" class="easyui-combobox"  style="width:182px; height:26px;"
                            data-options="
                                url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
                                method:'post',
                                valueField:'DIC_CODE',
                                textField:'DIC_NAME',
                                panelHeight:'auto',
                                required:true,
                                editable:false,
                                onSelect:function(rec){
                                   $('#singleprotype_zjgc').combobox('clear');
                                   $('#singleprosubtype_zjgc').combobox('clear');
                                   if(rec.DIC_CODE){
                                        /* var PROTYPE = $('#PROTYPE').val();
                                        if(rec.DIC_CODE!=PROTYPE && PROTYPE!=''){
                                            $('#singlepromaintype_zjgc').combobox('clear');
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
                                       $('#singleprotype_zjgc').combobox('reload',url); 
                                        setZjgcSingleProMaintype(rec.DIC_CODE,'');
                                   }
                                }
                        " />                    
                        <input name="SINGLEPROTYPE" id="singleprotype_zjgc" class="easyui-combobox" style="width:182px; height:26px;" 
                            data-options="
                                url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1',
                                method:'post',
                                valueField:'VALUE',
                                textField:'TEXT',
                                panelHeight:'auto',
                                required:true,
                                editable:false,
                                onSelect:function(rec){
                                   setZjgcSingleprosubtype(rec,'');
                                }
                        " />                                        
                        <input name="SINGLEPROSUBTYPE" id="singleprosubtype_zjgc" class="easyui-combobox" style="width:182px; height:26px;" 
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
                    </td>
                </tr>
            </table>
            <table cellpadding="0" cellspacing="0" class="syj-table2 " >      
                <tr class="singleProMaintype_1_zjgc">    
                    <th><font class="syj-color2">*</font>建筑高度</th>
                    <td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"
                        name="ARCHHEIGHT" maxlength="32" placeholder="请输入建筑高度"/>M</td>
                    <th>建筑面积</th>
                    <td>
                        <input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly"
                        name="ARCHAREA"  placeholder="系统会根据用户填写自动回填"   maxlength="16"  />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                        <div style="color:red; "> *桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！</div>
                    </td>
                
                </tr>
                <tr class="singleProMaintype_2_zjgc">    
                    <th><font class="syj-color2">*</font>市政长度</th>
                    <td>
                        <input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" name="MUNILENGTH" maxlength="16" placeholder="请输入市政长度" style="width:90%"/>M
                    </td>
                    <th>路桥面积</th>
                    <td>
                        <input type="text" class="syj-input1  validate[],custom[JustNumber]"  onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);"
                        name="FLOORAREA"  placeholder="请输入路桥面积"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                    </td>
                
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 是否超限高层建筑</th>
                    <td colspan="3">
                        <eve:radiogroup typecode="YesOrNo" width="130px"
                        fieldname="ISSUPERHIGHTBUILDING_ZJGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                </tr>   
                <tr class="singleProMaintype_1_zjgc">
                    <th><span class="syj-color2">*</span> ±0.000以上层数</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTUPFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以上层数" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
                    </td>
                    <th><span class="syj-color2">*</span> ±0.000以下层数</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTDWFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以下层数" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
                    </td>
                </tr>
                <tr class="singleProMaintype_1_zjgc">
                    <th><span class="syj-color2">*</span> ±0.000以上面积</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="请输入±0.000以上面积" onblur="onlyNumber2(this);setArcharea('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                    </td>
                    <th><span class="syj-color2">*</span> ±0.000以下面积</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="请输入±0.000以下面积" onblur="onlyNumber2(this);setArcharea('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                    </td>
                </tr>
                <tr>
                    <th>基坑支护方法</th>
                    <td>
                        <eve:eveselect clazz="syj-input1 validate[] w280 field_width" dataParams="FOUNDSUPWAY"
                        dataInterface="dictionaryService.findDatasForSelect"
                        defaultEmptyText="请选择基坑支护方法" name="FOUNDSUPWAY" >
                        </eve:eveselect>
                    </td>
                    <th>基坑开挖深度</th>
                    <td>
                      <input type="text" maxlength="16" class="syj-input1  validate[],custom[JustNumber]" placeholder="请输入基坑开挖深度" name="FOUNDDEPTH" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />M
                    </td>
                </tr>
                <tr>
                    <th>高边坡支护方法</th>
                    <td>
                        <eve:eveselect clazz="syj-input1 validate[] field_width w280" dataParams="SLOPESUPWAY"
                        dataInterface="dictionaryService.findDatasForSelect"
                        defaultEmptyText="请选择高边坡支护方法" name="SLOPESUPWAY" id="SLOPESUPWAY">
                        </eve:eveselect>
                    </td>
                    <th>边坡高度</th>
                    <td>
                      <input type="text" maxlength="16" class="syj-input1" placeholder="请输入边坡高度" name="SLOPEHEIGHT"   onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
                    </td>
                </tr>
                <tr>
                    <th>耐火等级</th>
                    <td>
                        <eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="REFRACTLEVEL"
                        dataInterface="dictionaryService.findDatasForSelect" 
                        defaultEmptyText="请选择耐火等级" name="REFRACTLEVEL" id="REFRACTLEVEL">
                        </eve:eveselect>
                    </td>
                    <th><span class="syj-color2">*</span> 结构最大跨度</th>
                    <td>
                      <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]"  placeholder="请输入结构最大跨度" name="STRUCTMAXSPAN"  onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
                    </td>
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 工程总造价（万元）</th>
                    <td colspan="3">
                      <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="ITEMINVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）" onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/><div style="color:red;width:90%;"> 指的是本单位工程的总造价，桩基工程先行办理施工许可时也要填写整个单位工程（含上部工程）的总造价。</div>
                    </td>
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 是否为减隔震建筑  </th>
                    <td>
                        <eve:radiogroup typecode="YesOrNo" width="130px" 
                        fieldname="ISSHOCKISOLATIONBUILDING_ZJGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                    <th><span class="syj-color2">*</span> 是否为装配式建筑  </th>
                    <td>
                        <eve:radiogroup typecode="YesOrNo" width="130px" 
                        fieldname="ISEQUIPPEDARCHITECTURE_ZJGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                </tr>   
                <tr>
                    <th><span class="syj-color2">*</span> 是否绿色建筑    </th>
                    <td>
                        <eve:radiogroup typecode="YesOrNo" width="130px" onclick="setZjgcGreenbuidinglevel(this.value,'');"
                        fieldname="ISGREENBUILDING_ZJGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                    <th><span class="syj-color2">*</span> 基础建设等级    </th>
                    <td>
                        <eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="INFRALEVEL"
                        dataInterface="dictionaryService.findDatasForSelect" 
                        defaultEmptyText="请选择基础建设等级" name="INFRALEVEL">
                        </eve:eveselect>
                    </td>
                </tr>   
                <tr class="greenbuidinglevelTr_zjgc" style="display:none">
                    <th><span class="syj-color2">*</span>绿色建筑等级</th>
                    <td colspan="3">
                        <eve:eveselect clazz="syj-input1 w280 field_width" dataParams="GREENBUIDINGLEVEL"
                        dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="001"
                        defaultEmptyText="请选择基础建设等级" name="GREENBUIDINGLEVEL_ZJGC">
                        </eve:eveselect>
                    </td>
                </tr>   
                <tr>
                    <th><span class="syj-color2">*</span> 结构类型</th>
                    <td colspan="3">
                        <span class="singleProMaintype_1_jglx_zjgc">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findTwoDatasForSelect" 
                            name="STRUCTQUALTYPE_ZJGC"  clazz="checkbox validate[required]" width="780px"
                            dataParams="STRUCTQUALTYPE:2">
                        </eve:excheckbox>
                        </span>
                        <span class="singleProMaintype_2_jglx_zjgc">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findTwoDatasForSelect"  
                            name="STRUCTQUALTYPE_ZJGC" clazz="checkbox validate[required]" width="780px"
                            dataParams="STRUCTQUALTYPE:1">
                        </eve:excheckbox>
                        </span>
                    </td>
                </tr>   
                <tr>
                    <th><span class="syj-color2">*</span> 基础类型</th>
                    <td colspan="3">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findDatasForSelect"  
                            name="BASICQUALTYPE_ZJGC" clazz="checkbox validate[required]" width="780px"
                            dataParams="BASICQUALTYPE">
                        </eve:excheckbox>
                    </td>
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 结构抗震等级</th>
                    <td colspan="3">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findDatasForSelect"
                            name="REBELQUAKELEVEL_ZJGC" clazz="checkbox validate[required]" width="780px"
                            dataParams="REBELQUAKELEVEL">
                        </eve:excheckbox>
                    </td>
                </tr>   
                <tr>
                    <th> 备注</th>
                    <td colspan="3">
                        <input type="text" class="syj-input1 validate[] w878"
                        name="REMARK" maxlength="100" placeholder="请输入备注"/>
                    </td>
                </tr>   
            </table>
            
            <div class="syj-title1" style="margin-top: 5px;margin-bottom: 5px;">
                <a href="javascript:void(0);" onclick="javascript:addZjgcChildrenDiv('');" class="syj-addbtn" id="addZjgc" >添加</a>
            </div>
            <table cellpadding="0" cellspacing="1" class="syj-table2" id="childrenZjgcTable">
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

        </div>
        </c:if>
        <c:if test="${!empty zjgcList}">
            <jsp:include page="./children/initZjgcDiv.jsp"></jsp:include>       
        </c:if>
    </div>
    
    
	</div>
	
	
    <div id="sbgcDiv">
       <div class="syj-title1 ">
        <a href="javascript:void(0);" onclick="javascript:addSbDiv();" class="syj-addbtn" id="addSbgc" >添 加</a>
        <span>上部（地下室）工程</span>
    </div>
    <div id="sbdxsgcDiv">
        <c:if test="${empty sbgcList}"> 
        <div style="position:relative;">    
            <table cellpadding="0" cellspacing="0" class="syj-table2 ">
                <tr>
                    <th><font class="syj-color2">*</font>单位工程名称</th>
                    <td colspan="3"><input type="text" class="syj-input1  validate[required] w878" 
                        name="SINGLEPRONAME" maxlength="100" placeholder="请输入单位名称"/></td>   
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
                        <input name="SINGLEPROMAINTYPE" id="singlepromaintype_sbgc" class="easyui-combobox"  style="width:182px; height:26px;"
                            data-options="
                                url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
                                method:'post',
                                valueField:'DIC_CODE',
                                textField:'DIC_NAME',
                                panelHeight:'auto',
                                required:true,
                                editable:false,
                                onSelect:function(rec){
                                   $('#singleprotype_sbgc').combobox('clear');
                                   $('#singleprosubtype_sbgc').combobox('clear');
                                   if(rec.DIC_CODE){
                                        /* var PROTYPE = $('#PROTYPE').val();
                                        if(rec.DIC_CODE!=PROTYPE && PROTYPE!=''){
                                            $('#singlepromaintype_sbgc').combobox('clear');
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
                                       $('#singleprotype_sbgc').combobox('reload',url); 
                                        setSbgcSingleProMaintype(rec.DIC_CODE,'');
                                   }
                                }
                        " />                    
                        <input name="SINGLEPROTYPE" id="singleprotype_sbgc" class="easyui-combobox" style="width:182px; height:26px;" 
                            data-options="
                                url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1',
                                method:'post',
                                valueField:'VALUE',
                                textField:'TEXT',
                                panelHeight:'auto',
                                required:true,
                                editable:false,
                                onSelect:function(rec){
                                   setSbgcSingleprosubtype(rec,'');
                                }
                        " />                                        
                        <input name="SINGLEPROSUBTYPE" id="singleprosubtype_sbgc" class="easyui-combobox" style="width:182px; height:26px;" 
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
                    </td>
                </tr>
            </table>
            <table cellpadding="0" cellspacing="0" class="syj-table2 " style="margin-top: -1px;">      
                <tr class="singleProMaintype_1_sbgc">    
                    <th><font class="syj-color2">*</font>建筑高度</th>
                    <td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"
                        name="ARCHHEIGHT" maxlength="32" placeholder="请输入建筑高度"/>M</td>
                    <th>建筑面积</th>
                    <td>
                        <input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly"
                        name="ARCHAREA"  placeholder="系统会根据用户填写自动回填"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                        <div style="color:red; "> *桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！</div>
                    </td>
                
                </tr>
                <tr class="singleProMaintype_2_sbgc">    
                    <th><font class="syj-color2">*</font>市政长度</th>
                    <td>
                        <input type="text" class="syj-input1 validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" name="MUNILENGTH" maxlength="16" placeholder="请输入市政长度" />M
                    </td>
                    <th>路桥面积</th>
                    <td>
                        <input type="text" class="syj-input1  validate[],custom[JustNumber]"  onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);"
                        name="FLOORAREA"  placeholder="请输入路桥面积"   maxlength="16"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                    </td>
                
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 是否超限高层建筑</th>
                    <td colspan="3">
                        <eve:radiogroup typecode="YesOrNo" width="130px"
                        fieldname="ISSUPERHIGHTBUILDING_SBGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                </tr>   
                <tr class="singleProMaintype_1_sbgc">
                    <th><span class="syj-color2">*</span> ±0.000以上层数</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTUPFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以上层数" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
                    </td>
                    <th><span class="syj-color2">*</span> ±0.000以下层数</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTDWFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以下层数" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />
                    </td>
                </tr>
                <tr class="singleProMaintype_1_sbgc">
                    <th><span class="syj-color2">*</span> ±0.000以上面积</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="请输入±0.000以上面积" onblur="onlyNumber2(this);setSbArcharea('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                    </td>
                    <th><span class="syj-color2">*</span> ±0.000以下面积</th>
                    <td>
                      <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="请输入±0.000以下面积" onblur="onlyNumber2(this);setSbArcharea('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                    </td>
                </tr>
                <tr>
                    <th>基坑支护方法</th>
                    <td>
                        <eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="FOUNDSUPWAY"
                        dataInterface="dictionaryService.findDatasForSelect"
                        defaultEmptyText="请选择基坑支护方法" name="FOUNDSUPWAY" >
                        </eve:eveselect>
                    </td>
                    <th>基坑开挖深度</th>
                    <td>
                      <input type="text" maxlength="16" class="syj-input1  validate[],custom[JustNumber]" placeholder="请输入基坑开挖深度" name="FOUNDDEPTH" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" />M
                    </td>
                </tr>
                <tr>
                    <th>高边坡支护方法</th>
                    <td>
                        <eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="SLOPESUPWAY"
                        dataInterface="dictionaryService.findDatasForSelect"
                        defaultEmptyText="请选择高边坡支护方法" name="SLOPESUPWAY" id="SLOPESUPWAY">
                        </eve:eveselect>
                    </td>
                    <th>边坡高度</th>
                    <td>
                      <input type="text" maxlength="16" class="syj-input1" placeholder="请输入边坡高度" name="SLOPEHEIGHT"   onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
                    </td>
                </tr>
                <tr>
                    <th>耐火等级</th>
                    <td>
                        <eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="REFRACTLEVEL"
                        dataInterface="dictionaryService.findDatasForSelect" 
                        defaultEmptyText="请选择耐火等级" name="REFRACTLEVEL" id="REFRACTLEVEL">
                        </eve:eveselect>
                    </td>
                    <th><span class="syj-color2">*</span> 结构最大跨度</th>
                    <td>
                      <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]"  placeholder="请输入结构最大跨度" name="STRUCTMAXSPAN"  onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
                    </td>
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 工程总造价（万元）</th>
                    <td colspan="3">
                      <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="ITEMINVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）" onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/><div style="color:red;"> 指的是本单位工程的总造价，桩基工程先行办理施工许可时也要填写整个单位工程（含上部工程）的总造价。</div>
                    </td>
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 是否为减隔震建筑  </th>
                    <td>
                        <eve:radiogroup typecode="YesOrNo" width="130px" 
                        fieldname="ISSHOCKISOLATIONBUILDING_SBGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                    <th><span class="syj-color2">*</span> 是否为装配式建筑  </th>
                    <td>
                        <eve:radiogroup typecode="YesOrNo" width="130px" 
                        fieldname="ISEQUIPPEDARCHITECTURE_SBGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                </tr>   
                <tr>
                    <th><span class="syj-color2">*</span> 是否绿色建筑    </th>
                    <td>
                        <eve:radiogroup typecode="YesOrNo" width="130px" onclick="setSbgcGreenbuidinglevel(this.value,'');"
                        fieldname="ISGREENBUILDING_SBGC" defaultvalue="0"></eve:radiogroup>
                    </td>
                    <th><span class="syj-color2">*</span> 基础建设等级    </th>
                    <td>
                        <eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="INFRALEVEL"
                        dataInterface="dictionaryService.findDatasForSelect" 
                        defaultEmptyText="请选择基础建设等级" name="INFRALEVEL">
                        </eve:eveselect>
                    </td>
                </tr>   
                <tr class="greenbuidinglevelTr_sbgc" style="display:none">
                    <th><span class="syj-color2">*</span>绿色建筑等级</th>
                    <td colspan="3">
                        <eve:eveselect clazz="syj-input1 w280 field_width" dataParams="GREENBUIDINGLEVEL"
                        dataInterface="dictionaryService.findDatasForSelect" defaultEmptyValue="001"
                        defaultEmptyText="请选择基础建设等级" name="GREENBUIDINGLEVEL_SBGC">
                        </eve:eveselect>
                    </td>
                </tr>   
                <tr>
                    <th><span class="syj-color2">*</span> 结构类型</th>
                    <td colspan="3">
                        <span class="singleProMaintype_1_jglx_sbgc">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findTwoDatasForSelect" 
                            name="STRUCTQUALTYPE_SBGC" clazz="checkbox validate[required]" width="780px"
                            dataParams="STRUCTQUALTYPE:2">
                        </eve:excheckbox>
                        </span>
                        <span class="singleProMaintype_2_jglx_sbgc">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findTwoDatasForSelect"  
                            name="STRUCTQUALTYPE_SBGC" clazz="checkbox validate[required]" width="780px"
                            dataParams="STRUCTQUALTYPE:1">
                        </eve:excheckbox>
                        </span>
                    </td>
                </tr>   
                <tr>
                    <th><span class="syj-color2">*</span> 基础类型</th>
                    <td colspan="3">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findDatasForSelect"  
                            name="BASICQUALTYPE_SBGC" clazz="checkbox validate[required]" width="780px"
                            dataParams="BASICQUALTYPE">
                        </eve:excheckbox>
                    </td>
                </tr>
                <tr>
                    <th><span class="syj-color2">*</span> 结构抗震等级</th>
                    <td colspan="3">
                        <eve:excheckbox
                            dataInterface="dictionaryService.findDatasForSelect"
                            name="REBELQUAKELEVEL_SBGC" clazz="checkbox validate[required]" width="780px"
                            dataParams="REBELQUAKELEVEL">
                        </eve:excheckbox>
                    </td>
                </tr>   
                <tr>
                    <th> 备注</th>
                    <td colspan="3">
                        <input type="text" class="syj-input1 validate[] w878"
                        name="REMARK" maxlength="100" placeholder="请输入备注"/>
                    </td>
                </tr>   
            </table>
            
            <div class="syj-title1" style="margin-top: 5px;margin-bottom: 5px;">
                <a href="javascript:void(0);" onclick="javascript:addSbgcChildrenDiv('');" class="syj-addbtn" id="addSbgc" >添加</a>
            </div>
            <table cellpadding="0" cellspacing="1" class="syj-table2" id="childrenSbgcTable">
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

        </div>
        </c:if>
        <c:if test="${!empty sbgcList}">
            <jsp:include page="./children/initSbgcDiv.jsp"></jsp:include>       
        </c:if>
    </div>
    </div>
</form>
