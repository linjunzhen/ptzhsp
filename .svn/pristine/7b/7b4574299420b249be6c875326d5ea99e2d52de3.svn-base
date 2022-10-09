<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<style type="text/css">
	.inputWidth{
		width:72%;
	}

</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="bdInfo">
	<tr>
		<th >标的物信息</th>
		<td> <input type="button" class="eflowbutton"  name="addOneBdInfo" value="查询添加标的物" onclick="openBdQueryPanel();"></td>
	</tr>
	<tr id="bdInfo_1">
		<td >
			<table class="tab_tk_pro2">
			    <%-- <tr>
			    	<td class="tab_width"><font class="tab_color">*</font>不动产类型：</td>
			    	<td style="width:630px" colspan="3">
			    	   <eve:eveselect clazz="tab_text inputWidth validate[required]" dataParams="BDCLX"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						defaultEmptyText="选择不动产类型" name="bdc_lx" value="1">
						</eve:eveselect>
			    	</td>
			    </tr> --%>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>不动产权利人：</td>
					<td ><input type="text" class="tab_text validate[required]" 
						name="bdc_name" maxlength="62" /></td>
					<td class="tab_width">不动产单元号：</td>
					<td>
						<input type="text" class="tab_text" 
						name="bdc_dyh" maxlength="62" />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>不动产地址：</td>
					<td>
					  <input type="text" class="tab_text validate[required]" maxlength="500" style="width: 100%;"
						name="bdc_addr"  />
					</td>
					<td class="tab_width"><font class="tab_color">*</font>不动产权证号/合同编号：</td>
					<td><input type="text" class="tab_text validate[required]"  style="width: 100%;"
						name="bdc_cqzh"  maxlength="200"/></td>
				</tr>
				<tr>
					<td class="tab_width">备注：</td>
					<td width="630px" colspan="3"><input type="text" class="tab_text validate[]" style="width: 100%;"
						name="bdc_bz" maxlength="200"/></td>
				</tr>				
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" class="eflowbutton" name="deleteOneBdInfo" value="删除" onclick="deleteBdInfo('1');">
		</td>
	</tr>
</table>

<script type="text/javascript">
var bdInfoSn = 1;

function openBdQueryPanel(){
	parent.$.dialog.open("bdcApplyController.do?bdSelector",{
         title : "标的物信息查询",
         width : "1000px",
         lock : true,
         resize : false,
         height : "500px",
         close: function () {
         	var selectBdItemsInfo = art.dialog.data("selectBdItemsInfo");
             if(selectBdItemsInfo&&selectBdItemsInfo.wtbdinfos){
             	 var bd_json = selectBdItemsInfo.wtbdinfos;
				 if(null != bd_json && '' != bd_json){
					var bdinfos = JSON.parse(bd_json);
					for(var i=0;i<bdinfos.length;i++){
						if(bdInfoSn==1){
							FlowUtil.initFormFieldValue(bdinfos[i],"bdInfo_1");
							bdInfoSn++;
						}else{
							addBdInfo();
							FlowUtil.initFormFieldValue(bdinfos[i],"bdInfo_"+(bdInfoSn));
						}
					}
				 } 
                 art.dialog.removeData("selectBdItemsInfo");                    
             }
         }
     }, false);
}

function addBdInfo(){
	bdInfoSn = bdInfoSn+1;
	var table = document.getElementById("bdInfo");
	var trContent = table.getElementsByTagName("tr")[1];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteBdInfo('");
	if(bdInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+18);
	}else{
		var replacestr = trHtml.substring(findex,findex+17);
	}
	trHtml = trHtml.replace(replacestr,"deleteBdInfo('"+bdInfoSn+"')");
	trHtml = "<tr id=\"bdInfo_"+bdInfoSn+"\">"+trHtml+"</tr>";
	$("#bdInfo").append(trHtml);
}

function getBdInfoJson(){
	var datas = [];
	var trs = $("#bdInfo tr[id*='bdInfo_']");
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
	$("input[type='hidden'][name='BD_JSON']").val(JSON.stringify(datas));
}

function deleteBdInfo(idSn){
	var table = document.getElementById("bdInfo");
	if(table.rows.length==2){
		parent.art.dialog({
			content: "不能删除,至少填写一个标的物信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#bdInfo_"+idSn).remove();
}

</script>