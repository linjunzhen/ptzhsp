<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<head>
    <eve:resources loadres="jquery,easyui,apputil,validationegine,artdialog"></eve:resources>

	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js">
	</script>
	<!-- my97 end -->
	<!---引入流程JS---->
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<!---引入公共JS--->
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/website/zzhy/css/css.css" />
	<script type="text/javascript">
	$(function() {
		
		AppUtil.initWindowForm("zjinfoForm", function(form, valid) {
			if (valid) {
				var isValid = $("#zjinfoForm").form('validate');
				if(isValid){
					//将提交按钮禁用,防止重复提交
					$("input[type='submit']").attr("disabled","disabled");
					var info = {};
					$("#zjinfoForm").find("*[name]").each(function(){
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
				
			}
		}, "T_MSJW_SYSTEM_DICTYPE");
		
		$("[name='IDCARDTYPENUM']").attr("disabled",true);
		$("[name='SINGLEPROMAINTYPE']").val('${info.SINGLEPROMAINTYPE}');
		$("[name='SINGLEPROTYPE']").val('${info.SINGLEPROTYPE}');
		$("[name='SINGLEPROSUBTYPE']").val('${info.SINGLEPROSUBTYPE}');
		
		setZjgcSingleProMaintype("${info.SINGLEPROMAINTYPE}","");
		setZjgcGreenbuidinglevel("${info.ISGREENBUILDING_ZJGC}","");
		var dicDesc = 1;
		if('${info.SINGLEPROMAINTYPE}'=='01'){
		   dicDesc = 1;
		} else if('${info.SINGLEPROMAINTYPE}'=='02'){
		   dicDesc = 2;
		}
		$('#singleprotype_zjgc').combobox({
			url:'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc='+dicDesc,		
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight:'auto',
			required:true,
			editable:false
		});
		$('#singleprosubtype_zjgc').combobox({
			url:'dictionaryController/loadDataToDesc.do?typeCode=gcytxl&dicDesc=${info.SINGLEPROTYPE}',		
			method:'post',
			valueField:'VALUE',
			textField:'TEXT',
			panelHeight:'auto',
			required:true,
			editable:false
		});	
			
		
		$("[name$='STRUCTQUALTYPE_ZJGC'][type='checkbox']").each(function() {
			var name = $(this).attr("name");
			$(this).click(function() {
				if ($(this).is(':checked')) {
					$("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				} else {
					$("[name='"+name+"'][type='checkbox']").not(this).attr('checked', false)
				}
			});
		});
	}); 
	function setZjgcGreenbuidinglevel(val,index){
		if(val==1){
			$("."+index+"greenbuidinglevelTr_zjgc").show();
			$("[name='"+index+"GREENBUIDINGLEVEL']").addClass(" validate[required]");
		} else{		
			$("."+index+"greenbuidinglevelTr_zjgc").hide();
			$("[name='"+index+"GREENBUIDINGLEVEL']").removeClass(" validate[required]");
		}
	}
		
	function setZjgcSingleProMaintype(val,i){
		if(val=='01'){
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").attr("disabled",true);
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").removeAttr('checked');
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").val('');
			$("."+i+"singleProMaintype_2_zjgc").hide();
			$("."+i+"singleProMaintype_1_zjgc").show();		
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").attr("disabled",false);
			
			$("."+i+"singleProMaintype_2_jglx_zjgc").find("input,select").attr("disabled",true);
			$("."+i+"singleProMaintype_2_jglx_zjgc").find("input,select").removeAttr('checked');
			$("."+i+"singleProMaintype_2_jglx_zjgc").hide();
			$("."+i+"singleProMaintype_1_jglx_zjgc").show();		
			$("."+i+"singleProMaintype_1_jglx_zjgc").find("input,select").attr("disabled",false);
			
			
		}else{		
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").attr("disabled",true);
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").removeAttr('checked');
			$("."+i+"singleProMaintype_1_zjgc").find("input,select").val('');
			$("."+i+"singleProMaintype_1_zjgc").hide();
			$("."+i+"singleProMaintype_2_zjgc").show();
			$("."+i+"singleProMaintype_2_zjgc").find("input,select").attr("disabled",false);
			
			
			$("."+i+"singleProMaintype_1_jglx_zjgc").find("input,select").attr("disabled",true);
			$("."+i+"singleProMaintype_1_jglx_zjgc").find("input,select").removeAttr('checked');
			$("."+i+"singleProMaintype_1_jglx_zjgc").hide();
			$("."+i+"singleProMaintype_2_jglx_zjgc").show();		
			$("."+i+"singleProMaintype_2_jglx_zjgc").find("input,select").attr("disabled",false);
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
		
	/**
	* 输入数字且小数点之后只能出现3位
	**/
	function onlyNumber3(obj){       
		//得到第一个字符是否为负号  
		var t = obj.value.charAt(0);    
		//先把非数字的都替换掉，除了数字和.   
		obj.value = obj.value.replace(/[^\d\.]/g,'');     
		//必须保证第一个为数字而不是.     
		obj.value = obj.value.replace(/^\./g,'');     
		//保证只有出现一个.而没有多个.     
		obj.value = obj.value.replace(/\.{2,}/g,'.');     
		//保证.只出现一次，而不能出现两次以上     
		obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');  
		//只能输入小数点后三位
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d\d).*$/, '$1$2.$3');
		//如果第一位是负号，则允许添加  
		if(t == '-'){  
			obj.value = '-'+obj.value;  
		}   
	}  
	/**
	* 输入数字且小数点之后只能出现2位
	**/
	function onlyNumber2(obj){       
		//得到第一个字符是否为负号  
		var t = obj.value.charAt(0);    
		//先把非数字的都替换掉，除了数字和.   
		obj.value = obj.value.replace(/[^\d\.]/g,'');     
		//必须保证第一个为数字而不是.     
		obj.value = obj.value.replace(/^\./g,'');     
		//保证只有出现一个.而没有多个.     
		obj.value = obj.value.replace(/\.{2,}/g,'.');     
		//保证.只出现一次，而不能出现两次以上     
		obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');  
		//只能输入小数点
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
		//如果第一位是负号，则允许添加  
		if(t == '-'){  
			obj.value = '-'+obj.value;  
		}   
	}

	/**
	 * 输入数字且小数点之后只能出现2位
	 **/
	function onlyNumber2(obj){
		//得到第一个字符是否为负号
		var t = obj.value.charAt(0);
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d\.]/g,'');
		//必须保证第一个为数字而不是.
		obj.value = obj.value.replace(/^\./g,'');
		//保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{2,}/g,'.');
		//保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
		//只能输入小数点
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d\d\d\d\d).*$/, '$1$2.$3');
		//如果第一位是负号，则允许添加
		if(t == '-'){
			obj.value = '-'+obj.value;
		}
	}
	
/**
 * 输入数字且小数点之后只能出现6位
 **/
function onlyNumber6(obj){
    //得到第一个字符是否为负号
    var t = obj.value.charAt(0);
    //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d\.]/g,'');
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g,'');
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g,'.');
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
    //只能输入小数点
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d\d\d\d\d).*$/, '$1$2.$3');
    //如果第一位是负号，则允许添加
    if(t == '-'){
        obj.value = '-'+obj.value;
    }
}	
	
	function setZjgcInfo(index){
		var STRUCTUPFLOORAREA_DWGC = $("#zjinfoForm [name$='STRUCTUPFLOORAREA_DWGC']").val();//±0.000以上面积
		var STRUCTDWFLOORAREA_DWGC = $("#zjinfoForm [name$='STRUCTDWFLOORAREA_DWGC']").val();//±0.000以下面积
		var ARCHAREA = Number(STRUCTUPFLOORAREA_DWGC)+Number(STRUCTDWFLOORAREA_DWGC);	
		if(Number(ARCHAREA)>0){	
			$("#zjinfoForm [name$='ARCHAREA']").val(ARCHAREA.toFixed(3));
		}else{
			$("#zjinfoForm [name$='ARCHAREA']").val('');
		}
	}
	</script>
</head>
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
<body class="eui-diabody" style="margin:0px;">
	<form id="zjinfoForm" method="post" style="margin-bottom: 30px;margin-top: 30px;">
	    <div  id="ModuleFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="index" value="${index}">
	    <input type="hidden" name="currentTime" value="${currentTime}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>单位工程名称</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required]" value="${info.SINGLEPRONAME}"
					name="SINGLEPRONAME" maxlength="100" placeholder="请输入单位名称"/></td>	
			</tr>	
			<tr>
				<th><font class="syj-color2">*</font>桩基施工单位</th>
				<td colspan="3">
					<ul class="BUILDUNITS_UL">
					<c:forEach items="${sgdwCorpName}" var="sgdw" varStatus="s">
						<li>
							<input name="BUILDUNITS" class="checkbox validate[required]" value="${sgdw}" type="checkbox" 
							<c:if test="${fn:contains(info.BUILDUNITS,sgdw)}">checked="checked"</c:if>>${sgdw}
						</li>
					</c:forEach>
					</ul>
				</td>	
			</tr>	
			<tr>
				<th>桩基监理单位</th>
				<td colspan="3">
					<ul class="SUPERVISIONUNITS_UL">
					<c:forEach items="${jldwCorpName}" var="jldw" varStatus="s">
						<li>
							<input name="SUPERVISIONUNITS" class="checkbox validate[]" value="${jldw}" type="checkbox" 
							<c:if test="${fn:contains(info.SUPERVISIONUNITS,jldw)}">checked="checked"</c:if>>${jldw}
						</li>
					</c:forEach></ul>
				</td>	
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>单位工程类别</th>
				<td colspan="3">						
					<input name="SINGLEPROMAINTYPE" id="singlepromaintype_zjgc" class="easyui-combobox"
						data-options="
							url:'dictionaryController/loadData.do?typeCode=PROTYPE&orderType=asc',
							method:'post',
							valueField:'DIC_CODE',
							textField:'DIC_NAME',
							panelHeight:'auto',
							required:true,
							editable:false,
							onSelect:function(rec){								
							   var dicDesc = 1;
							   if(rec.DIC_CODE=='01'){
								   dicDesc = 1;
							   } else if(rec.DIC_CODE=='02'){
								   dicDesc = 2;
							   }
							   var url = 'dictionaryController/loadDataToDesc.do?typeCode=TBPRJFUNCTIONDIC&dicDesc='+dicDesc;
							   $('#singleprotype_zjgc').combobox('reload',url); 
							   $('#singleprotype_zjgc').combobox('setValue',''); 
							   $('#singleprosubtype_zjgc').combobox('setValue',''); 
								setZjgcSingleProMaintype(rec.DIC_CODE,'');
							}
					" value="${info.SINGLEPROMAINTYPE}"/>				
					<input name="SINGLEPROTYPE" id="singleprotype_zjgc" class="easyui-combobox" 
						data-options="
							url:'',
							method:'post',
							valueField:'VALUE',
							textField:'TEXT',
							panelHeight:'auto',
							required:true,
							editable:false,
							onSelect:function(rec){
								   setZjgcSingleprosubtype(rec,'');
							}
					"  value="${info.SINGLEPROTYPE}"/>	                					
					<input name="SINGLEPROSUBTYPE" id="singleprosubtype_zjgc" class="easyui-combobox" 
						data-options="
							url:'',
							method:'post',
							valueField:'VALUE',
							textField:'TEXT',
							panelHeight:'auto',
							required:true,
							editable:false,
							onSelect:function(rec){	
							}
					"  value="${info.SINGLEPROSUBTYPE}"/>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr class="singleProMaintype_1_zjgc">	
				<th><font class="syj-color2">*</font>建筑高度</th>
				<td><input type="text" class="syj-input1 validate[required],custom[JustNumber]" 
					name="ARCHHEIGHT" maxlength="32" placeholder="请输入建筑高度" value="${info.ARCHHEIGHT}"/>M</td>
				<th>建筑面积</th>
				<td>
					<input type="text" class="syj-input1 inputBackgroundCcc validate[]"  readonly="readonly" value="${info.ARCHAREA}"
					name="ARCHAREA"  placeholder="系统会根据用户填写自动回填"   maxlength="16" />M<sup style="vertical-align: super;font-size: smaller;">2</sup>
					<div style="color:red;"> *桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！</div>
				</td>
			
			</tr>
			<tr class="singleProMaintype_2_zjgc">	
				<th><font class="syj-color2">*</font>市政长度</th>
				<td>
					<input type="text" class="syj-input1 validate[required],custom[JustNumber]"
					name="MUNILENGTH" maxlength="16" placeholder="请输入市政长度" value="${info.MUNILENGTH}" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
				</td>
				<th>路桥面积</th>
				<td>
					<input type="text" class="syj-input1  validate[],custom[JustNumber]" 
					name="FLOORAREA" value="${info.FLOORAREA}"  placeholder="请输入路桥面积"   maxlength="16" onblur="onlyNumber2(this);" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
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
                  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTUPFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以上层数" value="${info.STRUCTUPFLOORNUM}" onblur="onlyNumber3(this);setZjgcInfo('');" onkeyup="onlyNumber3(this);"/>
                </td>
                <th><span class="syj-color2">*</span> ±0.000以下层数</th>
                <td>
                  <input type="text" class="syj-input1 validate[required],custom[numberplus]" name="STRUCTDWFLOORNUM"  maxlength="16"  placeholder="请输入±0.000以下层数" value="${info.STRUCTDWFLOORNUM}" onblur="onlyNumber3(this);setZjgcInfo('');" onkeyup="onlyNumber3(this);"/>
                </td>
            </tr>
            <tr class="singleProMaintype_1_zjgc">
                <th><span class="syj-color2">*</span> ±0.000以上面积</th>
                <td>
                  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTUPFLOORAREA_DWGC"  maxlength="16" placeholder="请输入±0.000以上面积" value="${info.STRUCTUPFLOORAREA_DWGC}" onblur="onlyNumber2(this);setZjgcInfo('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                </td>
                <th><span class="syj-color2">*</span> ±0.000以下面积</th>
                <td>
                  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTDWFLOORAREA_DWGC"  maxlength="16"  placeholder="请输入±0.000以下面积" value="${info.STRUCTDWFLOORAREA_DWGC}" onblur="onlyNumber2(this);setZjgcInfo('');" onkeyup="onlyNumber2(this);"/>M<sup style="vertical-align: super;font-size: smaller;">2</sup>
                </td>
            </tr>
			<tr>
				<th>基坑支护方法</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="FOUNDSUPWAY"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.FOUNDSUPWAY}" 
					defaultEmptyText="请选择基坑支护方法" name="FOUNDSUPWAY" >
					</eve:eveselect>
				</td>
				<th>基坑开挖深度</th>
				<td>
				  <input type="text" maxlength="16" class="syj-input1  validate[],custom[number]" name="FOUNDDEPTH"  value="${info.FOUNDDEPTH}"  placeholder="请输入基坑开挖深度"/>M
				</td>
			</tr>
			<tr>
				<th>高边坡支护方法</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="SLOPESUPWAY"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.SLOPESUPWAY}" 
					defaultEmptyText="请选择高边坡支护方法" name="SLOPESUPWAY">
					</eve:eveselect>
				</td>
				<th>边坡高度</th>
				<td>
				  <input type="text" maxlength="16" class="syj-input1 validate[],custom[number]" name="SLOPEHEIGHT"   value="${info.SLOPEHEIGHT}"  placeholder="请输入边坡高度" />M
				</td>
			</tr>
			<tr>
				<th>耐火等级</th>
				<td>
					<eve:eveselect clazz="syj-input1 w280 validate[] field_width" dataParams="REFRACTLEVEL"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.REFRACTLEVEL}"
					defaultEmptyText="请选择耐火等级" name="REFRACTLEVEL">
					</eve:eveselect>
				</td>
				<th><span class="syj-color2">*</span> 结构最大跨度</th>
				<td>
				  <input type="text" maxlength="16" class="syj-input1 validate[required],custom[JustNumber]" name="STRUCTMAXSPAN"    value="${info.STRUCTMAXSPAN}"  placeholder="请输入结构最大跨度" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);"/>M
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 工程总造价（万元）</th>
				<td colspan="3">
				  <input type="text" class="syj-input1 validate[required],custom[JustNumber]" name="ITEMINVEST"  maxlength="16"  placeholder="请输入工程总造价（万元）" value="${info.ITEMINVEST}" onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/><div style="color:red; "> 指的是本单位工程的总造价，桩基工程先行办理施工许可时也要填写整个单位工程（含上部工程）的总造价。</div>
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
					<eve:eveselect clazz="syj-input1 w280 validate[required] field_width" dataParams="INFRALEVEL"
					dataInterface="dictionaryService.findDatasForSelect" value="${info.INFRALEVEL}" 
					defaultEmptyText="请选择基础建设等级" name="INFRALEVEL">
					</eve:eveselect>
				</td>
			</tr>	
			<tr class="greenbuidinglevelTr_zjgc" style="display:none">
				<th><span class="syj-color2">*</span>绿色建筑等级</th>
				<td colspan="3">
					<eve:eveselect clazz="syj-input1 w280 field_width" dataParams="GREENBUIDINGLEVEL"
					dataInterface="dictionaryService.findDatasForSelect"  value="${info.GREENBUIDINGLEVEL}" 
					defaultEmptyText="请选择基础建设等级" name="GREENBUIDINGLEVEL">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 结构类型</th>
				<td colspan="3">
					<span class="singleProMaintype_1_jglx_zjgc">
					<eve:excheckbox
						dataInterface="dictionaryService.findTwoDatasForSelect"
						name="STRUCTQUALTYPE_ZJGC" width="749px;" clazz="checkbox validate[required]" value="${info.STRUCTQUALTYPE_ZJGC}" 
						dataParams="STRUCTQUALTYPE:2" >
					</eve:excheckbox>
					</span>
					<span class="singleProMaintype_2_jglx_zjgc">
					<eve:excheckbox
						dataInterface="dictionaryService.findTwoDatasForSelect"
						name="STRUCTQUALTYPE_ZJGC" width="749px;" clazz="checkbox validate[required]" value="${info.STRUCTQUALTYPE_ZJGC}" 
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
						name="BASICQUALTYPE_ZJGC" width="749px;" clazz="checkbox validate[required]" value="${info.BASICQUALTYPE_ZJGC}"  
						dataParams="BASICQUALTYPE" >
					</eve:excheckbox>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 结构抗震等级</th>
				<td colspan="3">
					<eve:excheckbox
						dataInterface="dictionaryService.findDatasForSelect" value="${info.REBELQUAKELEVEL_ZJGC}"  
						name="REBELQUAKELEVEL_ZJGC" width="749px;" clazz="checkbox validate[required]"
						dataParams="REBELQUAKELEVEL">
					</eve:excheckbox>
				</td>
			</tr>	
			<tr>
				<th> 备注</th>
				<td colspan="3">
					<input type="text" class="syj-input1 validate[] w838" value="${info.REMARK}"  
					name="REMARK" maxlength="100" placeholder="请输入备注"/>
				</td>
			</tr>	
		</table>
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
