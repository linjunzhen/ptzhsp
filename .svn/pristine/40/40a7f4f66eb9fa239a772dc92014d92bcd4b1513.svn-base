
/**=================权属来源信息开始================================*/

function showSelectBdcygdacx(){	
	$.dialog.open("bdcDyqscdjController.do?bdcygdacxSelector&allowCount=0", {
		title : "不动产预告档案查询",
		width:"100%",
		lock: true,
		resize:false,
		height:"100%",
		close: function () {
			var bdcygdacxInfo = art.dialog.data("bdcygdacxInfo");
			if(bdcygdacxInfo){
				/*var table = document.getElementById("qslyInfo");
				 if(table.rows.length>1){
					for ( var i = 1; i <= table.rows.length; i++) {
						deleteQslyInfo(i+1);
					}			
				}
				qslyInfoSn = 1;	 */	
				
				//$("#qslyInfo_1 input[name='deleteQslyInfoInput']").show();
				
				var bdcdyh = $("#qslyInfo").find('table').eq(0).find("input[name='BDCDYH']").val();
				for(var i = 0;i<bdcygdacxInfo.length;i++){
					
					if(i==0 && qslyInfoSn==1 && !bdcdyh){
						FlowUtil.initFormFieldValue(bdcygdacxInfo[i],"qslyInfo_1");
						$("#qslyInfo_1 input[name='bdcqsly']").val(JSON.stringify(bdcygdacxInfo[i]));
					}else{
						addQslyInfo();
						FlowUtil.initFormFieldValue(bdcygdacxInfo[i],"qslyInfo_"+(qslyInfoSn));
						$("#qslyInfo_"+(qslyInfoSn)+" input[name='bdcqsly']").val(JSON.stringify(bdcygdacxInfo[i]));
					}
				}	
				//$("#qslyInfo_1 input[name='deleteQslyInfoInput']").hide();
				
				$("#qslyInfo").find("input,select").attr("disabled","disabled");
				$("#qslyInfo").find("input,select").attr("readonly","readonly");
				//去除权属来源中按钮的不可编辑性
				$("#qslyInfo").find("input[type='button']").attr("disabled",false);
				$("#qslyInfo").find("input[type='button']").attr("readonly",false);
				art.dialog.removeData("bdcygdacxInfo");
				
			}
		}
	}, false);
};
var qslyInfoSn = 1;
function addQslyInfo(){
	qslyInfoSn = qslyInfoSn+1;
	var table = document.getElementById("qslyInfo");
	var trContent = table.getElementsByTagName("tr")[0];
	var trHtml = trContent.innerHTML;
	var findex = trHtml.indexOf("deleteQslyInfo('");
	if(qslyInfoSn>10){
		var replacestr = trHtml.substring(findex,findex+20);
	}else{
		var replacestr = trHtml.substring(findex,findex+19);
	}
	trHtml = trHtml.replace(replacestr,"deleteQslyInfo('"+qslyInfoSn+"')");
	trHtml = "<tr id=\"qslyInfo_"+qslyInfoSn+"\">"+trHtml+"</tr>";
	$("#qslyInfo").append(trHtml);
}


function deleteQslyInfo(idSn){
	var table = document.getElementById("qslyInfo");
	if(table.rows.length==1){
		parent.art.dialog({
			content: "最少一个权属来源信息！",
			icon:"warning",
			ok: true
		});
		return false;
	}
	$("#qslyInfo_"+idSn).remove();
}

function getQslyInfoJson(){
	var datas = [];
	var table = document.getElementById("qslyInfo");
	for ( var i = 0; i < table.rows.length; i++) {
		var bdcqsly = $("#qslyInfo").find('table').eq(i).find("input[name='bdcqsly']").val();
		var trData = {};
		if(""!=bdcqsly && null!= bdcqsly && undefined!=bdcqsly){		
			trData = JSON.parse(bdcqsly);
		}
		$("#qslyInfo").find('table').eq(i).find("*[name]").each(function(){
			  var inputName= $(this).attr("name");
			  if(inputName!="bdcqsly"){					 
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
				  }else if(fieldType!="button"){
					  trData[inputName] = inputValue;
				  }           
			  }
		});
		datas.push(trData);
		
	}
	$("input[type='hidden'][name='QSLY_JSON']").val(JSON.stringify(datas));
}
/**=================权属来源信息开始================================*/