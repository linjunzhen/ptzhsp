<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog"></eve:resources>

	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js">
	</script>
	<!-- my97 end -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/zzhy/css/css.css" />
	<script type="text/javascript">
	$(function() {
		
		AppUtil.initWindowForm("dwinfoForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var info = {};
				$("#dwinfoForm").find("*[name]").each(function(){
					  var inputName= $(this).attr("name");
					  var inputValue = $(this).val();
					  //获取元素的类型
					  var fieldType = $(this).attr("type");
					  if(fieldType=="radio"){
						  var isChecked = $(this).is(':checked');
						  if(isChecked){
							  info[inputName] = inputValue;
						  }
					  }else if(fieldType=="checkbox"){
						  var inputValues = FlowUtil.getCheckBoxValues(inputName);
						  info[inputName] = inputValues;
					  }else if(fieldType!="button"){
						  info[inputName] = inputValue;
					  }          
				});
				art.dialog.data("dwInfo", info);
				AppUtil.closeLayer();
				
			}
		}, "T_MSJW_SYSTEM_DICTYPE");
		
		$("[name='IDCARDTYPENUM']").attr("disabled",true);
		$("[name='SINGLEPROMAINTYPE']").val('${info.SINGLEPROMAINTYPE}');
		$("[name='SINGLEPROTYPE']").val('${info.SINGLEPROTYPE}');
		$("[name='SINGLEPROSUBTYPE']").val('${info.SINGLEPROSUBTYPE}');
		
		setZjgcSingleProMaintype("${info.SINGLEPROMAINTYPE}","");
		setZjgcGreenbuidinglevel("${info.ISGREENBUILDING_ZJGC}","");
	}); 
	function setZjgcSingleProMaintype(val,i){
		if(val=='01'){
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").attr("disabled",true);
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").removeAttr('checked');
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").val('');
			$("."+i+"singleProMaintype_2_zjgc").hide();
			$("."+i+"singleProMaintype_1_zjgc").show();		
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").attr("disabled",false);
		}else{		
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").attr("disabled",true);
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").removeAttr('checked');
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").val('');
			$("."+i+"singleProMaintype_1_zjgc").hide();
			$("."+i+"singleProMaintype_2_zjgc").show();
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").attr("disabled",false);
		}
	}
	function setZjgcSingleprosubtype(rec,index){
	   $('#'+index+'singleprosubtype_zjgc').combobox('clear');
	   if(rec.VALUE){
		   var url = 'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc='+rec.VALUE;
		   if(rec.VALUE=='100'||rec.VALUE=='300'||rec.VALUE=='900'){
			   $('#'+index+'singleprosubtype_zjgc').combobox('reload',url); 
		   }else{
			   var data, json;
				json = '[{"VALUE":"'+rec.TEXT+'","TEXT":"'+rec.TEXT+'","selected":true}]';
				data = $.parseJSON(json);
			   $('#'+index+'singleprosubtype_zjgc').combobox('loadData',data);  
		   }
	   }
	}
	function setZjgcGreenbuidinglevel(val,index){
		if(val==1){
			$("."+index+"greenbuidinglevelTr_zjgc").show();
			$("[name='"+index+"GREENBUIDINGLEVEL']").addClass(" validate[required]");
		} else{		
			$("."+index+"greenbuidinglevelTr_zjgc").hide();
			$("[name='"+index+"GREENBUIDINGLEVEL']").removeClass(" validate[required]");
		}
	}
	/**
	* 查看单位工程信息
	**/
	function getZjgcInfo(formName,id){	
		var rowJson = $("#"+id).find("[name='ROW_JSON']").val();
		var rowxx = JSON.parse(rowJson);
		delete rowxx.SGRY;
		delete rowxx.JLRY;
		var url = "projectSgxkController.do?addZjInfo&type=0&formName="+formName+"&info="+encodeURIComponent(JSON.stringify(rowxx));
		art.dialog.open(url, {  
			title : "桩基子工程信息",
			width:"1150px",
			lock: true,
			resize:false,
			height:"100%",
		}, false);
	}
	</script>
	<style>
		.gclb_width{
			width:100%!important;
		}
		.eflowbutton{
			  background: #3a81d0;
			  border: none;
			  padding: 0 10px;
			  line-height: 26px;
			  cursor: pointer;
			  height:26px;
			  color: #fff;
			  border-radius: 5px;
			  
		}
		.eflowbutton-disabled{
			  background: #94C4FF;
			  border: none;
			  padding: 0 10px;
			  line-height: 26px;
			  cursor: pointer;
			  height:26px;
			  color: #E9E9E9;
			  border-radius: 5px;
			  
		}
	</style>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="dwinfoForm" method="post">
	    <div  id="ModuleFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="index" value="${index}">
	    <input type="hidden" name="currentTime" value="${currentTime}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>桩基工程名称</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required]" value="${info.SINGLEPRONAME}"
					name="SINGLEPRONAME" maxlength="100" placeholder="请输入桩基单位名称"/></td>	
			</tr>	
			<tr>
				<th><font class="syj-color2">*</font>桩基施工单位</th>
				<td colspan="3">
					<ul class="BUILDUNITS_UL">${info.BUILDUNITS}</ul>
				</td>	
			</tr>	
			<tr>
				<th>监理单位</th>
				<td colspan="3">
					<ul class="SUPERVISIONUNITS_UL"> ${info.SUPERVISIONUNITS}</ul>
				</td>	
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>单位工程类别</th>
				<td colspan="3">						
					<input name="SINGLEPROMAINTYPE" id="singlepromaintype" class="easyui-combobox"  style="width:182px; height:26px;"
						data-options="
							url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
							method:'post',
							valueField:'DIC_CODE',
							textField:'DIC_NAME',
							panelHeight:'auto',
							required:true,
							editable:false,
							onSelect:function(rec){
							}
					" value=" ${info.SINGLEPROMAINTYPE}"/>				
					<input name="SINGLEPROTYPE" id="singleprotype" class="easyui-combobox" style="width:182px; height:26px;" 
						data-options="
							url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc=1,2',
							method:'post',
							valueField:'VALUE',
							textField:'TEXT',
							panelHeight:'auto',
							required:true,
							editable:false,
							onSelect:function(rec){
							}
					"  value=" ${info.SINGLEPROTYPE}"/>	                					
					<input name="SINGLEPROSUBTYPE" id="singleprosubtype_zjgc" class="easyui-combobox" style="width:182px; height:26px;" 
						data-options="
							url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc=100,300',
							method:'post',
							valueField:'VALUE',
							textField:'TEXT',
							panelHeight:'auto',
							required:true,
							editable:false,
							onSelect:function(rec){	
							}
					"  value=" ${info.SINGLEPROSUBTYPE}"/>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">		
			<tr class="singleProMaintype_1_zjgc">	
				<th><font class="syj-color2">*</font>建筑高度</th>
				<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" 
					name="ARCHHEIGHT" maxlength="32" placeholder="请输入建筑高度" style="width:90%"  value="${info.ARCHHEIGHT}"/>M</td>
				<th>建筑面积</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly" value="${info.ARCHAREA}"
					name="ARCHAREA"  placeholder="系统会根据用户填写自动回填"   maxlength="16" style="width:80%"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
					<div style="color:red;width:90%;"> *桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！</div>
				</td>
			
			</tr>
			<tr class="singleProMaintype_2_zjgc">	
				<th><font class="syj-color2">*</font>市政长度</th>
				<td>
					<input type="text" class="syj-input1 validate[required],custom[JustNumber]"
					name="MUNILENGTH" maxlength="16" placeholder="请输入市政长度" style="width:90%" value="${info.MUNILENGTH}"/>M
				</td>
				<th>路桥面积</th>
				<td>
					<input type="text" class="syj-input1  validate[],custom[JustNumber]" 
					name="FLOORAREA" value="${info.FLOORAREA}"  placeholder="请输入路桥面积"   maxlength="16" style="width:80%"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
				</td>
			
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 是否超限高层建筑</th>
				<td colspan="3">
					<eve:radiogroup typecode="YesOrNo" width="130px" value="${info.ISSUPERHIGHTBUILDING_ZJGC}" 
					fieldname="ISSUPERHIGHTBUILDING_ZJGC"></eve:radiogroup>
				</td>
			</tr>	
			<tr class="singleProMaintype_1_zjgc">
				<th><span class="syj-color2">*</span> ±0.000以上层数</th>
				<td>
				  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTUPFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以上层数" value="${info.STRUCTUPFLOORNUM}" />
				</td>
				<th><span class="syj-color2">*</span> ±0.000以下层数</th>
				<td>
				  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTDWFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以下层数" value="${info.STRUCTDWFLOORNUM}"  />
				</td>
			</tr>
			<tr class="singleProMaintype_1_zjgc">
				<th><span class="syj-color2">*</span> ±0.000以上面积</th>
				<td>
				  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="请输入±0.000以上面积" value="${info.STRUCTUPFLOORAREA_DWGC}"  style="width:80%"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
				</td>
				<th><span class="syj-color2">*</span> ±0.000以下面积</th>
				<td>
				  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="请输入±0.000以下面积" value="${info.STRUCTDWFLOORAREA_DWGC}" style="width:80%"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
				</td>
			</tr>
			<tr>
				<th>基坑支护方法</th>
				<td>
					<eve:eveselect clazz="syj-input1 validate[] field_width" dataParams="FOUNDSUPWAY"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.FOUNDSUPWAY}" 
					defaultEmptyText="请选择基坑支护方法" name="FOUNDSUPWAY" >
					</eve:eveselect>
				</td>
				<th>基坑开挖深度</th>
				<td>
				  <input type="text" maxlength="16" class="syj-input1  validate[],custom[number]" style="width:90%" name="FOUNDDEPTH"  value="${info.FOUNDDEPTH}" />M
				</td>
			</tr>
			<tr>
				<th>高边坡支护方法</th>
				<td>
					<eve:eveselect clazz="syj-input1 validate[] field_width" dataParams="SLOPESUPWAY"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.SLOPESUPWAY}" 
					defaultEmptyText="请选择高边坡支护方法" name="SLOPESUPWAY">
					</eve:eveselect>
				</td>
				<th>边坡高度</th>
				<td>
				  <input type="text" maxlength="16" class="syj-input1 validate[],custom[number]" style="width:90%" name="SLOPEHEIGHT"   value="${info.SLOPEHEIGHT}"  />M
				</td>
			</tr>
			<tr>
				<th>耐火等级</th>
				<td>
					<eve:eveselect clazz="syj-input1 validate[] field_width" dataParams="REFRACTLEVEL"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.REFRACTLEVEL}"
					defaultEmptyText="请选择耐火等级" name="REFRACTLEVEL">
					</eve:eveselect>
				</td>
				<th><span class="syj-color2">*</span> 结构最大跨度</th>
				<td>
				  <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]" style="width:90%" name="STRUCTMAXSPAN"    value="${info.STRUCTMAXSPAN}"/>M
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 工程总造价（万元）</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="ITEMINVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）" value="${info.ITEMINVEST}"/><div style="color:red;width:90%;"> 指的是本单位工程的总造价，桩基工程先行办理施工许可时也要填写整个单位工程（含上部工程）的总造价。</div>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 是否为减隔震建筑	</th>
				<td>
					<eve:radiogroup typecode="YesOrNo" width="130px" value="${info.ISSHOCKISOLATIONBUILDING_ZJGC}" 
					fieldname="ISSHOCKISOLATIONBUILDING_ZJGC" ></eve:radiogroup>
				</td>
				<th><span class="syj-color2">*</span> 是否为装配式建筑	</th>
				<td>
					<eve:radiogroup typecode="YesOrNo" width="130px" value="${info.ISEQUIPPEDARCHITECTURE_ZJGC}" 
					fieldname="ISEQUIPPEDARCHITECTURE_ZJGC"></eve:radiogroup>
				</td>
			</tr>	
			<tr>
				<th><span class="syj-color2">*</span> 是否绿色建筑	</th>
				<td>
					<eve:radiogroup typecode="YesOrNo" width="130px" onclick="setZjgcGreenbuidinglevel(this.value,'');"
					fieldname="ISGREENBUILDING_ZJGC"  value="${info.ISGREENBUILDING_ZJGC}" ></eve:radiogroup>
				</td>
				<th><span class="syj-color2">*</span> 基础建设等级	</th>
				<td>
					<eve:eveselect clazz="syj-input1 validate[] field_width" dataParams="INFRALEVEL"
					dataInterface="dictionaryService.findDatasForSelect" value="${info.INFRALEVEL}" 
					defaultEmptyText="请选择基础建设等级" name="INFRALEVEL">
					</eve:eveselect>
				</td>
			</tr>	
			<tr class="greenbuidinglevelTr_zjgc" style="display:none">
				<th><span class="syj-color2">*</span>绿色建筑等级</th>
				<td colspan="3">
					<eve:eveselect clazz="syj-input1 field_width" dataParams="GREENBUIDINGLEVEL"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.GREENBUIDINGLEVEL}" 
					defaultEmptyText="请选择基础建设等级" name="GREENBUIDINGLEVEL">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 结构类型</th>
				<td colspan="3">
					<span class="singleProMaintype_1_zjgc">
					<eve:excheckbox
						dataInterface="dictionaryService.findTwoDatasForSelect"
						name="STRUCTQUALTYPE" width="749px;" clazz="checkbox validate[required]" value="${info.STRUCTQUALTYPE}" 
						dataParams="STRUCTQUALTYPE:2" >
					</eve:excheckbox>
					</span>
					<span class="singleProMaintype_2_zjgc">
					<eve:excheckbox
						dataInterface="dictionaryService.findTwoDatasForSelect"
						name="STRUCTQUALTYPE" width="749px;" clazz="checkbox validate[required]" value="${info.STRUCTQUALTYPE}" 
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
						name="BASICQUALTYPE" width="749px;" clazz="checkbox validate[required]" value="${info.BASICQUALTYPE}"  
						dataParams="BASICQUALTYPE" >
					</eve:excheckbox>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 结构抗震等级</th>
				<td colspan="3">
					<eve:excheckbox
						dataInterface="dictionaryService.findDatasForSelect" value="${info.REBELQUAKELEVEL}"  
						name="REBELQUAKELEVEL" width="749px;" clazz="checkbox validate[required]"
						dataParams="REBELQUAKELEVEL">
					</eve:excheckbox>
				</td>
			</tr>	
			<tr>
				<th> 备注</th>
				<td colspan="3">
					<input type="text" class="syj-input1 validate[]" value="${info.REMARK}"  
					name="REMARK" maxlength="100" placeholder="请输入备注"/>
				</td>
			</tr>	
		</table>
		<c:if test="${!empty childrenzjgcList}">
		<table cellpadding="0" cellspacing="1" class="syj-table2" id="childrenZjgcTable" style="margin-top:5px;">
			<tr>
				<th style="width: 24%;">子单位名称</td>
				<th style="width: 14%;">单位工程类别</td>
				<th style="width: 14%;">结构类型</td>
				<th style="width: 12%;">建筑高度/长度（m）</td>
				<th style="width: 14%;">建筑面积（m2）</td>
				<th style="width: 12%;">工程总造价（万元）</td>
				<th style="width: 10%;">操作</td>
			</tr>	
			<c:forEach items="${childrenzjgcList}" var="zjzgc" varStatus="d">
				<tr class="zjgcTr" id="zjgcTr_${d.index}">
					<input type="hidden" name="ROW_JSON" value="${zjzgc.ROW_JSON}"/>
					<td style="text-align: center;">${zjzgc.SINGLEPRONAME}</td>
					<td style="text-align: center;">						
						<eve:eveselect clazz="tab_text validate[required] gclb_width" dataParams="PROTYPE"
						dataInterface="dictionaryService.findDatasForSelect" 
						defaultEmptyText="请选择工程类别" name="${d.index}SINGLEPROMAINTYPE" id="${d.index}SINGLEPROMAINTYPE" value="${zjzgc.SINGLEPROMAINTYPE}">
						</eve:eveselect>
					</td>
					<td style="text-align: center;">${zjzgc.STRUCTQUALTYPE_ZJGC}</td>
					<td style="text-align: center;">${zjzgc.ARCHHEIGHT} ${zjzgc.MUNILENGTH}</td>
					<td style="text-align: center;">${zjzgc.ARCHAREA} ${zjzgc.FLOORAREA}</td>
					<td style="text-align: center;">${zjzgc.ITEMINVEST}</td>
					<td style="text-align: center;">
					<a href="javascript:void(0);" class="eflowbutton" onclick="getZjgcInfo('zjzgc','zjgcTr_${d.index}');">查 看</a>
					</td>
				</tr>
			</c:forEach>
			
		</table>
		</c:if>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<c:if test="${type!=0}">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				</c:if>
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
