
//提交流程
function FLOW_SubmitFun(flowSubmitObj){
	//获取推送标志位
	var pushFlag = $("input[name='PUSH_FLAG']").val();
	//先判断表单是否验证通过
	var validateResult =$("#T_SBQLC_QYZGZJY_FORM").validationEngine("validate");
	if(validateResult){
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
		if(submitMaterFileJson||submitMaterFileJson==""){
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "受理"){
				//获取已参保险种信息
				if(!getXzxxJson(flowSubmitObj)){
					alert("至少勾选一条人员参保险种信息！");
					return null;
				}
			}
			//办结环节推送数据
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "办结" && pushFlag!='1'){
				var type = $("input[name='ZJY_TYPE']:checked").val();	
				var flag=false;
				if(type=="1"){//企业养老保险参保人员登记
					flag=pushQyZgCb(flowSubmitObj);
				}else if(type=="2"){//企业养老保险参保人员登记
					flag=pushQyZgJy(flowSubmitObj);
				}
				if(!flag){//推送数据至平潭社保系统
					return null;
				}else{
					alert("业务数据推送至平潭社保险系统成功！");
				}
			}
			//获取表单上的所有值
			var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGZJY_FORM");
			for(var index in formData){
				flowSubmitObj[eval("index")] = formData[index];
			}
			return flowSubmitObj;
		}else{
			 return null;
		}			
	}else{
		return null;
	}
}


//暂存流程
function FLOW_TempSaveFun(flowSubmitObj) {
	var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("", -1);
	$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
	getXzxxJson(flowSubmitObj);
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGZJY_FORM");
	for (var index in formData) {
		flowSubmitObj[eval("index")] = formData[index];
	}
	return flowSubmitObj;
}


//退回流程
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}



//数据格式化
function commonFormat(val,row){
	var typeCode="";
	if("性别"==this.title){
		typeCode="sex";
	}else if("单位类型"==this.title){
		typeCode="SBDWLX";
	}else if("险种类型"==this.title){
		typeCode="SBXZLX";
	}else if("个人缴费状态"==this.title){
		typeCode="SBJBZT";
	}else if("人员参保状态"==this.title){
		typeCode="SBCBZT";
	}else if("征收方式"==this.title){
		typeCode="SBZSFS";
	}else{
		typeCode=this.field;
	}
	var map = getDicNameByTypeCode(typeCode);
	return dataFormatByMap(val,map);
}
function getDicNameByTypeCode(typeCode){
	var datas;
	$.ajax({
		url: "dictionaryController.do?load",
		type: "POST",
		data:{typeCode:typeCode},
		dataType: "json",
		async: false,
		success: function(data){
			datas= data;
		},
		error:function(data){
			datas= data;
		}
	});
	return datas;
}

function dataFormatByMap(value,map){
	var rtn = "";
	$.each(map, function(idx, dic) {
		if(value==dic.DIC_CODE){
			rtn = dic.DIC_NAME;
			return false;
		}
	});
	return rtn;
}


function  changeCity(id) {
	var val =$("#QYCB_JZDS  option:selected").attr("type_code");
	$.ajax({
		url:"exeDataController/dicTypeByParentDatagrid.do",
		data:{"parentId":val},
		dataType:"json",
		success:function(datas){
			var data=datas.rows;
			$("#QYCB_JZDSSS").html("<option value=''>-- 请选择市 --</option>");
			$("#QYCB_JZDSSQ").html("<option value=''>-- 请选择区/县 --</option>");
			for(var i=0;i<data.length;i++){
				var $option = $("<option value='"+data[i].VALUE+"' type_code='"+data[i].TYPE_CODE+"'>"+data[i].TEXT+"</option>");
				$("#QYCB_JZDSSS").append($option);
			}
		}
	});
}

function changeDictinct(id) {
	var val =$("#QYCB_JZDSSS  option:selected").attr("type_code");
	$.ajax({
		url:"exeDataController/dicTypeByParentDatagrid.do",
		data:{"parentId":val},
		dataType:"json",
		success:function(datas){
			var data=datas.rows;
			$("#QYCB_JZDSSQ").html("<option value=''>-- 请选择区/县 --</option>");
			for(var i=0;i<data.length;i++){
				var $option = $("<option value='"+data[i].VALUE+"' type_code='"+data[i].TYPE_CODE+"'>"+data[i].TEXT+"</option>");
				$("#QYCB_JZDSSQ").append($option);
			}
		}
	});
}


/*类型切换操作*/
function zjyTypeClick(){
	var zjyvalue = $("input[name='ZJY_TYPE']:checked").val();
	if(zjyvalue == 1){
		$("#page_1").attr("style","");	   
		$("#page_2").attr("style","display:none;");
		//空数据横向滚动条
		$('#cbxxConfGrid').datagrid({
			onLoadSuccess: function(data){  
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});
	}else if(zjyvalue == 2){
		$("#page_1").attr("style","display:none;");
		$("#page_2").attr("style","");
		//空数据横向滚动条
		$('#xzConfGrid').datagrid({
			onLoadSuccess: function(data){  
				if(data.total==0){
					var dc = $(this).data('datagrid').dc;
			        var header2Row = dc.header2.find('tr.datagrid-header-row');
			        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
		        }
			}
		});
	}
}



//参保业务数据推送出去
function pushQyZgCb(flowSubmitObj){
	var result = false;
	var ywId = flowSubmitObj.busRecord.YW_ID;
	//判断单位是否正常在参
	var dwzt = $('select[name="QYCB_DWZT"]').val();//单位状态
	if(dwzt=="1"){
		alert("业务数据一经推送社保系统，不可回退！");
		var layload = layer.load("正在提交请求中…");
		$.ajax({
			type: "POST",
			url: "sbQyzjyController.do?pushZgCb&ywId="+ywId,
			async: false, //采用同步方式进行数据判断
			success: function (responseText) {
				layer.close(layload);
				var data=$.parseJSON(responseText);
				if(data.success){
					$('input[name="PUSH_FLAG"]').val("1");//设置推送标志位为1(成功)
					result = true;
				}else if(!data.success){
					//var msg = data.msg.replace(/\s\r\n/g,"<br>");
					art.dialog({
						content: data.msg,
						icon:"warning",
						time:5,
						ok: true
					});
					result = false;
				}
			}
		});
	}else{
		alert("该单位为非正常在参状态，不可受理！");
		result = false;
	}
	return result;
}

//减员业务数据推送出去
function pushQyZgJy(flowSubmitObj){
	var result = false;
	var ywId = flowSubmitObj.busRecord.YW_ID;
	alert("业务数据一经推送社保系统，不可回退！");
	var layload = layer.load("正在提交请求中…");
	$.ajax({
		type: "POST",
		url: "sbQyzjyController.do?pushZgJy&ywId="+ywId,
		async: false, //采用同步方式进行数据判断
		success: function (responseText) {
			layer.close(layload);
			var data=$.parseJSON(responseText);
			if(data.success){
				$('input[name="PUSH_FLAG"]').val("1");//设置推送标志位为1(成功)
				result = true;
			}else if(!data.success){
				//var msg = data.msg.replace(/\s\r\n/g,"<br>");
				art.dialog({
					content: data.msg,
					icon:"warning",
					time:5,
					ok: true
				});
				result = false;
			}
		}
	});
	return result;
}

//获取已参保险种信息
function getXzxxJson(flowSubmitObj) {
	var flag=false;
	var rows="";
	var type = $("input[name='ZJY_TYPE']:checked").val();	
	if(type=='1') {//参保登记
		rows = $("#cbxxConfGrid").datagrid("getChecked");
		if (rows.length > 0) {
			$("input[type='hidden'][name='QYCB_RYCBXXJSON']").val(JSON.stringify(rows));
		}
	}
	if(type=='2') {//减员登记
		rows = $("#xzConfGrid").datagrid("getChecked");
		if (rows.length > 0) {
			$("input[type='hidden'][name='QYJY_XZXXJSON']").val(JSON.stringify(rows));
		}
	}	
	if (rows.length > 0) {
		flag = true;
	}
	return flag;
}


//单位信息查询（不弹框）
function dwxxcx(){
	var dwglm = $("input[name='QYCB_DWGLM']").val();//单位管理码
	if( dwglm!="" && dwglm!="undefined" && dwglm!=null){
		var layload = layer.load("正在提交请求中…");
		$.ajax({
			type: "POST",
			url: "sbQyzjyController.do?getDwxx&dwglm="+dwglm,
			async: false, //采用同步方式进行数据判断
			success: function (responseText) {
				layer.close(layload);
				//清空单位基本信息业务表单数据
				$("#jbxx").find("input[type='text'],.Wdate").val("");
				$("#jbxx").find("select").find("option:selected").prop("selected", false);
				$("#cbxxConfGrid").datagrid("loadData", { total: 0, rows: [] });
				var data=$.parseJSON(responseText);
				if(data.success){
					//数据回填
					var data = JSON.parse(data.jsonString);
					FlowUtil.initFormFieldValue(data,"T_SBQLC_QYZGZJY_FORM");
					//加载已参加险种信息
					$("#cbxxConfGrid").datagrid('loadData',data.cbxx);
				}else if(!data.success){					
					art.dialog({
						content: data.msg,
						icon:"warning",
						time:5,
						ok: true
					});					
				}
			}
		});
	}else{
		art.dialog({
			content : "请先输入完整的单位管理码！",
			icon : "warning",
			ok : true
		});
	}
}

//增员-个人基本&参保信息查询（不弹框）
function personxxcx(){
	var id = $("input[name='QYCB_ZJHM']").val();//个人身份证号码;	
	if( id!="" && id!="undefined" && id!=null){
		var layload = layer.load('正在查询，请稍等…');
		$.ajax({
			type: "POST",
			url: "sbQyzjyController.do?getpersonInfo&aac002="+id,
			async: false, //采用同步方式进行数据判断
			success: function (responseText) {
				layer.close(layload);
				//原有业务数据清空
				$("#grxx").find("input[type='text'],.Wdate").val("");
				$("#grxx").find("select").find("option:selected").prop("selected", false);	
				$("#lxxx").find("input[type='text'],.Wdate").val("");
				$("#lxxx").find("select").find("option:selected").prop("selected", false);
				$("#cbxx").find("input[type='text'],.Wdate").val("");
				$("#cbxx").find("select").find("option:selected").prop("selected", false);					
				$("#cbxxConfGrid").datagrid("loadData", { total: 0, rows: [] });
				var data=$.parseJSON(responseText);				
				if(data.success){
					//有数据无需清空，直接覆盖				
					var data = JSON.parse(data.jsonString);
					/*data.QYCB_DWGLM=data.aab999//单位管理码
					data.QYCB_DWMC=data.aab004;//单位名称
					data.QYCB_DWLX=data.aab019;//单位类型
					data.QYCB_DWBH=data.aab001;//单位编号*/	
				    data.QYCB_ZJLX=data.aac058;//证件类型
					data.QYCB_ZJHM=data.aac002;//证件号码
					data.QYCB_XM=data.aac003;//姓名
					data.QYCB_SHBZHM=data.aac002;//社会保障号码
					data.QYCB_XB=data.aac004;//性别
					data.QYCB_MZ=data.aac005;//民族
					data.QYCB_CSRQ=data.aac006;//身份证出生日期
					data.QYCB_DACSRQ=data.aac006;//档案出生日期
					data.QYCB_GZSJ=data.aac007;//参加工作时间
					//data.QYCB_ZWMC=data.aac014;//专业技术职务系列名称
					data.QYCB_ZWDJ=data.aac014;//专业技术职务等级
					data.QYCB_ZYZGDJ=data.aac015;//国家职业资格等级
					data.QYCB_XZZW=data.aac020;//行政职务
					data.QYCB_ZGGW=data.bac180;//职工岗位
					data.QYCB_HYZK=data.aac017;//婚姻状况
					data.QYCB_HKXZ=data.aac009;//户口性质
					data.QYCB_NMGBS=data.aac028;//农民工标识
					data.QYCB_XL=data.aac011;//学历
					data.QYCB_HKDZ=data.aac010;//户口所在地址
					data.QYCB_SBDZDDZ=data.aae006;//邮寄社保对账单地址
					data.QYCB_CBRDH=data.aae005;//参保人联系电话
					data.QYCB_JZDYZBM=data.aae007;//居住地（联系）邮政编码
					data.QYCB_JZDLXDZ=data.aae006;//居住地（联系）地址
					data.QYCB_CBRQ=data.aac049;//参保日期
					data.QYCB_GRSF=data.aac012;//个人身份
					data.QYCB_STJFYS=data.aae200//视同缴费月数					
					data.QYCB_JBZT=data.baeaf1;//缴费状态
					data.QYCB_ZYZGDJ=data.aac015;//国家职业资格
					data.QYCB_JBJGDM=data.aab034;//经办机构代码
					FlowUtil.initFormFieldValue(data,"T_SBQLC_QYZGZJY_FORM");
					//加载已参加险种信息
					$("#cbxxConfGrid").datagrid('loadData',data.infos);
				}else if(!data.success){					
					art.dialog({
						content: data.msg,
						icon:"warning",
						time:5,
						ok: true
					});										
				}
			}
		});
	}else{
		art.dialog({
			content : "请先输入完整的证件号码！",
			icon : "warning",
			ok : true
		});
	}
}



//减员-个人基本&参保信息查询（不弹框）
function personInsureSelector(){
	var aac001 = $("input[name='QYJY_GRBH']").val();//个人编号
	var aac002 = $("input[name='QYJY_SHBZHM']").val();//身份证号
	var param ="aac001="+aac001;
	param +="&aac002="+aac002;
	if((aac001!=null && aac001!=undefined && aac001!="")||
		(aac002!=null && aac002!=undefined && aac002!="")){
		var layload = layer.load('正在查询，请稍等…');
		$.ajax({
			type: "POST",
			url: "sbQyzjyController.do?getpersonInfo&"+param,
			async: false, //采用同步方式进行数据判断
			success: function (responseText) {
				layer.close(layload);
				//原有业务数据清空
				$("#page_2").find("input[type='text'],.Wdate").val("");
				$("#page_2").find("select").find("option:selected").prop("selected", false);					
				$("#xzConfGrid").datagrid("loadData", { total: 0, rows: [] });
				var data=$.parseJSON(responseText);				
				if(data.success){					
					var data = JSON.parse(data.jsonString);				
					data.QYJY_GRBH=data.aac001;//个人编号
					data.QYJY_SHBZHM=data.aac002;//社会保障号码
					data.QYJY_XM=data.aac003;//姓名
					data.QYJY_XB=data.aac004;//性别
					data.QYJY_CSRQ=data.aac006;//出生日期
					data.QYJY_GZRQ=data.aac007;//参加工作日期
					data.QYJY_DWGLM=data.aab999;//单位管理码
					data.QYJY_DWMC=data.aab004;//单位名称
					data.QYJY_DWLX=data.aab019;//单位类型
					data.QYJY_XZLX=data.aae140;//险种类型
					data.QYJY_DWBH=data.aab001;//单位编号
					data.QYJY_SCCBLX=data.aac049;//首次参保日期
					data.QYJY_JFZT=data.baeaf1;//缴费状态
					data.QYCB_JBJGDM=data.aab034;//社保机构代码
					FlowUtil.initFormFieldValue(data,"T_SBQLC_QYZGZJY_FORM");
					//加载已参加险种信息
					$("#xzConfGrid").datagrid('loadData',data.infos);									
				}else if(!data.success){					
					art.dialog({
						content: data.msg,
						icon:"warning",
						time:5,
						ok: true
					});										
				}
			}
		});
	}else{
		art.dialog({
			content : "请先输入完整的个人编号或者社会保障码！",
			icon : "warning",
			ok : true
		});
	}
	
}






//减员-个人基本&参保信息查询（弹框）
/*function personInsureSelector(type,inputName){
	var inputValue=$("input[name='"+inputName+"']").val();
	var queryParam=type+"="+inputValue;
	$.dialog.open("sbQyzjyController.do?personInsureSelector&"+queryParam, {
		title : "个人基本和参保信息查询",
		width:"80%",
		height:"80%",
		lock : true,
		resize : false,
		close: function () {
			var dwxx = art.dialog.data("dwjbxxInfo");
			var dwjbxxInfos = art.dialog.data("dwjbxxInfos");
			if(dwxx){				
				FlowUtil.initFormFieldValue(dwxx[0],"T_SBQLC_QYZGZJY_FORM");
				//加载已参加险种信息
			}
			if(dwjbxxInfos){
				//$("#xzConfGrid").datagrid("loadData", { total: 0, rows: [] });
				$("#xzConfGrid").datagrid('loadData',dwjbxxInfos);
			}
			art.dialog.removeData("dwxx");
			art.dialog.removeData("dwjbxxInfos");
		}
	}, false);
}*/



