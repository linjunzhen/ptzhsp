<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<style type="text/css">
	.inputWidth{
		width:82%;
	}

</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="wtInfo">
	<tr>
		<th >委托人信息</th>
		<td> <input type="button" class="eflowbutton"  name="addOneWtInfo" value="新增" onclick="addWtInfo();"> </td>
	</tr>
	<tr id="wtInfo_1">
		<td >
			<table class="tab_tk_pro2">
			    <tr>
			    	<td class="tab_width"><font class="tab_color">*</font>申请人类型：</td>
			    	<td style="width:630px" colspan="3">
			    	   <eve:eveselect clazz="tab_text inputWidth validate[required]" dataParams="WTSQRLX"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择申请人类型" name="wtr_sqlx" value="1">
						</eve:eveselect>
			    	</td>
			    </tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="wtr_name" maxlength="62"/></td>
					<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择证件类型" name="wtr_zjlx" id="wtr_zjlx" value="SF">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" id="wtr_zjhm" style="float: left;"
						name="wtr_zjhm"  /><a href="javascript:void(0);" onclick="wtPowerPeopleRead(this);" class="eflowbutton">读 卡</a>
					</td>
					<td class="tab_width">联系电话：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="wtr_lxdh" maxlength="11"/></td>
				</tr>
				<tr>					
					<td class="tab_width">性别</td>
					<td>
					<eve:eveselect clazz="tab_text"
										dataParams="sex"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="选择性别" name="wtr_sex" id="wtr_sex"></eve:eveselect>
					</td>
					<td class="tab_width">地址：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="wtr_addr" maxlength="62"/></td>
				</tr>
				<tr>					
					<td class="tab_width">邮政编码：</td>
					<td><input type="text" class="tab_text validate[,custom[chinaZip]]" 
						name="wtr_zipcode" maxlength="6"/></td>
					<td class="tab_width">人脸照片：</td>
					<td><input name="wtr_pic_id" type="hidden"/><a href="javascript:void(0);" onclick="chooseScanCtrl('wtr_pic_id',this);" class="eflowbutton">高拍仪</a>
						<a href="javascript:void(0);" onclick="openUploadCtrl('wtr_pic_id',this);" class="eflowbutton">上传</a>
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
						defaultEmptyText="选择证件类型" name="fddb_zjlx" id="fddb_zjlx" value="SF">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" style="float: left;"
						name="fddb_zjhm"  /><a href="javascript:void(0);" onclick="wtfddbPowerPeopleRead(this);" class="eflowbutton">读 卡</a>
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
						defaultEmptyText="选择证件类型" name="dlr_zjlx" id="dlr_zjlx" value="SF">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<td class="tab_width">证件号码：</td>
					<td>
					  <input type="text" class="tab_text validate[]" maxlength="30" style="float: left;"
						name="dlr_zjhm"  /><a href="javascript:void(0);" onclick="wtagentPowerPeopleRead(this);" class="eflowbutton">读 卡</a>
					</td>
					<td class="tab_width">联系电话：</td>
					<td><input type="text" class="tab_text validate[]" 
						name="dlr_lxdh" maxlength="11"/></td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" class="eflowbutton" name="deleteOneWtInfo" value="删除" onclick="deleteWtInfo('1');">
		</td>
	</tr>
</table>

<script type="text/javascript">
var wtInfoSn = 1;
function addWtInfo(){
	wtInfoSn = wtInfoSn+1;
	var table = document.getElementById("wtInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteWtInfo('");
	if(wtInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+18);
	}else{
		var replacestr = trHtml.substring(findex,findex+17);
	}
	trHtml = trHtml.replace(replacestr,"deleteWtInfo('"+wtInfoSn+"')");
	trHtml = "<tr id=\"wtInfo_"+wtInfoSn+"\">"+trHtml+"</tr>";
	$("#wtInfo").append(trHtml);
}

function getWtInfoJson(){
	var datas = [];
	var trs = $("#wtInfo tr[id*='wtInfo_']");
	for(var i=0;i<trs.length;i++){
		var id = $(trs[i]).attr("id");
		var trData = {};
		$("#"+id).find("*[name]").each(function(){
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
	$("input[type='hidden'][name='WT_JSON']").val(JSON.stringify(datas));
}

function deleteWtInfo(idSn){
	var table = document.getElementById("wtInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "不能删除,至少填写一个委托人信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#wtInfo_"+idSn).remove();
}
</script>