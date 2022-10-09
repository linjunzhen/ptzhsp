<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>


<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="stInfo">
	<tr>
		<th >受托人信息</th>
		<!-- <td> <input type="button" class="eflowbutton"  name="addOneStInfo" value="新增" onclick="addStInfo();"> </td> -->
	</tr>
	<tr id="stInfo_1">
		<td >
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="str_name" maxlength="62"/></td>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="str_zjlx" id="str_zjlx" value="SF">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" style="float: left;"
						name="str_zjhm"  /><a href="javascript:void(0);" onclick="stPowerPeopleRead(this);" class="eflowbutton">读 卡</a>
					</td>
					<td class="tab_width">联系电话：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="str_lxdh" maxlength="11"/></td>
				</tr>
				<tr>					
					<td class="tab_width">性别</td>
					<td>
					<eve:eveselect clazz="tab_text"
										dataParams="sex"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="选择性别" name="str_sex" id="str_sex"></eve:eveselect>
					</td>
					<td class="tab_width">地址：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="str_addr" maxlength="62"/></td>
				</tr>
				<tr>					
					<td class="tab_width">邮政编码：</td>
					<td><input type="text" class="tab_text validate[,custom[chinaZip]]" 
						name="str_zipcode" maxlength="6"/></td>
					<td class="tab_width">人脸照片：</td>
					<td><input name="str_pic_id" type="hidden"/><a href="javascript:void(0);" onclick="chooseScanCtrl('str_pic_id',this);" class="eflowbutton">高拍仪</a>
						<a href="javascript:void(0);" onclick="openUploadCtrl('str_pic_id',this);" class="eflowbutton">上传</a>
					</td>
				</tr>
				<tr>					
					<td class="tab_width">法定代表人姓名：</td>
					<td ><input type="text" class="tab_text validate[]" 
						name="fddb_name" maxlength="62"/></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="fddb_zjlx" value="SF">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" style="float: left;"
						name="fddb_zjhm"  /><a href="javascript:void(0);" onclick="stfddbPowerPeopleRead(this);" class="eflowbutton">读 卡</a>
					</td>
					<td class="tab_width">联系电话：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="fddb_lxdh" maxlength="11"/></td>
				</tr>
				<tr>					
					<td class="tab_width">代理人姓名：</td>
					<td ><input type="text" class="tab_text validate[]" 
						name="dlr_name" maxlength="62"/></td>
					<td class="tab_width">证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="dlr_zjlx" value="SF">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" style="float: left;"
						name="dlr_zjhm"  /><a href="javascript:void(0);" onclick="stagentPowerPeopleRead(this);" class="eflowbutton">读 卡</a>
					</td>
					<td class="tab_width">联系电话：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="dlr_lxdh" maxlength="11"/></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<!-- <td>
			<input type="button" class="eflowbutton" name="deleteOneStInfo" value="删除" onclick="deleteStInfo('1');">
		</td> -->
	</tr>
</table>

<script type="text/javascript">
var stInfoSn = 1;
function addStInfo(){
	stInfoSn = stInfoSn+1;
	var table = document.getElementById("stInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteStInfo('");
	if(stInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+18);
	}else{
		var replacestr = trHtml.substring(findex,findex+17);
	}
	trHtml = trHtml.replace(replacestr,"deleteStInfo('"+stInfoSn+"')");
	trHtml = "<tr id=\"stInfo_"+stInfoSn+"\">"+trHtml+"</tr>";
	$("#stInfo").append(trHtml);
}

function getStInfoJson(){
	var datas = [];
	var table = document.getElementById("stInfo");
	for ( var i = 1; i <= table.rows.length-1; i++) {
		var trData = {};
		$("#stInfo_"+i).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  		  trData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  trData[inputName] = inputValues;
			  }else{
				  trData[inputName] = inputValue;
			  }          
	    });
		datas.push(trData);
	}
	$("input[type='hidden'][name='ST_JSON']").val(JSON.stringify(datas));
}

function deleteStInfo(idSn){
	var table = document.getElementById("stInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "不能删除,至少填写一个受托人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#stInfo_"+idSn).remove();
}
</script>