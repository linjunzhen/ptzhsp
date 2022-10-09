

//表单初始化
function initForm(){
	//空数据，横向滚动
	$('#payDetailGrid').datagrid({	
		onLoadSuccess: function(data){			
			if(data.total==0){			
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});	
}

//提交流程
function FLOW_SubmitFun(flowSubmitObj){
	//先判断表单是否验证通过
	var validateResult =$("#T_SBQLC_QYZGCX_FORM").validationEngine("validate");
	if(validateResult){
		var submitMaterFileJson = AppUtil.getSubmitMaterFileJson("",1);
		if(submitMaterFileJson||submitMaterFileJson==""){
			$("input[name='EFLOW_SUBMITMATERFILEJSON']").val(submitMaterFileJson);
			if (flowSubmitObj.EFLOW_CURUSEROPERNODENAME == "开始"){
				getJsonInfo(flowSubmitObj);
			}
			//获取表单上的所有值
			var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGCX_FORM");
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
	getJsonInfo(flowSubmitObj);//获取已参保险种信息
	//先获取表单上的所有值
	var formData = FlowUtil.getFormEleData("T_SBQLC_QYZGCX_FORM");
	for (var index in formData) {
		flowSubmitObj[eval("index")] = formData[index];
	}
	return flowSubmitObj;
}


//退回流程
function FLOW_BackFun(flowSubmitObj){
	return flowSubmitObj;
}


//人员信息查询总入口
function  totalSearch() {
	//业务表单数据先清空，再回填
	$("#ryxx").find("input[type='text'],.Wdate").val("");
	$("#ryxx").find("select").find("option:selected").attr("selected", false);					
	$("#xzConfGrid").datagrid("loadData", { total: 0, rows: [] });
	$("#payDetailGrid").datagrid('loadData',{ total: 0,rows: []});	
	personxxcx();//人员基本&参保信息&缴费明细
	//insuredDetail();//人员参保信息
}


//人员基本&参保信息查询（不弹框）
function personxxcx(){	
	var layload = layer.load('正在查询，请稍等…');
	var aac001=$('input[name="GRBH"]').val();//个人编号
	var aac002=$('input[name="SHBZHM"]').val();//社会保障号码
	var queryParam="aac001="+aac001+"&aac002="+aac002;;
	if((aac001!="" && aac001!="undefined" && aac001!=null) || 
			(aac002!="" && aac002!="undefined" && aac002!=null)){
		$.ajax({
			type: "POST",
			url: "sbQyzgcxController.do?getpersonInfo&"+queryParam,
			async: false, //采用同步方式进行数据判断
			success: function (responseText) {
				var data=$.parseJSON(responseText);
				if(data.success){
					layer.close(layload);					
					var data = JSON.parse(data.jsonString);
					data.DWGLM=data.aab999//单位管理码
					data.DWMC=data.aab004;//单位名称
					data.DWLX=data.aab019;//单位类型
					data.ZJLX=data.aac058;//证件类型
					data.ZJHM=data.aac002;//证件号码
					data.XM=data.aac003;//姓名
					data.SHBZHM=data.aac002;//社会保障号码
					data.XB=data.aac004;//性别
					data.MZ=data.aac005;//民族
					data.CSRQ=data.aac006;//身份证出生日期
					data.DACSRQ=data.aac006;//档案出生日期
					data.GZSJ=data.aac007;//参加工作时间
					//data.QYCB_ZWMC=data.aac014;//专业技术职务系列名称
					data.ZWDJ=data.aac014;//专业技术职务等级
					data.ZYZGDJ=data.aac015;//国家职业资格等级
					data.XZZW=data.aac020;//行政职务
					data.ZGGW=data.bac180;//职工岗位
					data.HYZK=data.aac017;//婚姻状况
					data.HKXZ=data.aac009;//户口性质
					data.NMGBS=data.aac028;//农民工标识
					data.XL=data.aac011;//学历
					data.HKDZ=data.aac010;//户口所在地址
					data.SBDZDDZ=data.aae006;//邮寄社保对账单地址
					data.CBRDH=data.aae005;//参保人联系电话
					data.JZDYZBM=data.aae007;//居住地（联系）邮政编码
					data.JZDLXDZ=data.aae006;//居住地（联系）地址
					data.CBRQ=data.aac049;//参保日期
					data.GRSF=data.aac012;//个人身份
					data.STJFYS=data.aae200//视同缴费月数					
					data.JBZT=data.baeaf1;//缴费状态
					data.ZYZGDJ=data.aac015;//国家职业资格
					data.JBJGDM=data.aab034;//经办机构代码
					FlowUtil.initFormFieldValue(data,"T_SBQLC_QYZGCX_FORM");
					//加载已参加险种信息
					$("#xzConfGrid").datagrid('loadData',data.infos);					
					//根据单位编号是否为空，判断缴费明细接口的入参
					if((data.aab001=="" || data.aab001=="undefined" || data.aab001==null)
						&&(aac001 == "" || aac001 == "undefined" || aac001 == null)
						&&(aac002 == "" || aac002 == "undefined" || aac002 == null)) {							
							art.dialog({
								content : "该人员所在单位编号不存在，需查缴费明细请先输入完整的个人编号和社会保障号码！",
								icon : "warning",
								ok : true
							});							
					}else{						
						payDetail(data.aab001);//缴费明细接口
					}					
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
			content : "请先输入完整的个人编号或者社会保障号码！",
			icon : "warning",
			ok : true
		});	
	}
	layer.close(layload);
}

//缴费明细查询（不弹框）(从接口获取数据）
function payDetail(dwbh){
	var aab001 = dwbh;//单位编号
	var aac001=$('input[name="GRBH"]').val();//个人编号
	var aac002=$('input[name="SHBZHM"]').val();//社会保障号码
	var aae140=[];
	$('input[name="XZLX"]:checked').each(function(){
		aae140.push($(this).val());
	});
	if(aae140.length <=0 || aae140.length>1)
		aae140="";//险种类型
	var aae003Begin=$('input[name="KFSJ"]').val();//开始时间
	var aae003End=$('input[name="JSSJ"]').val();//结束时间
	var aae002Begin=$('input[name="JZQJFYS"]').val();//建账前缴费月数
	var aae002End=$('input[name="JZHJFYS"]').val();//建账后缴费月数
	var url="sbQyzgcxController.do?findPayDetail";
	url+="&aab001="+aab001;
	url+="&aac001="+aac001;
	url+="&aac002="+aac002;
	url+="&aae140="+aae140;
	url+="&aae003Begin="+aae003Begin;
	url+="&aae003End="+aae003End;
	url+="&aae002Begin="+aae002Begin;
	url+="&aae002End="+aae002End;
	url+="&unitflag=3";	
	$.ajax({
		type: "POST",
		url: url,
		async: false, //采用同步方式进行数据判断
		success: function (responseText) {
			var data=$.parseJSON(responseText);
			if(data.success){				
				//数据回填
				var data = JSON.parse(data.jsonString);
				//加载已参加险种信息
				$("#payDetailGrid").datagrid('loadData',data);
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
}

//打印基本养老个人历年缴费明细表*工伤保险个人历年缴费明细表（模板路径、模板名称、打印险种）
function printPaiedDetail(TemplatePath,TemplateName){
	var flowSubmitObj = FlowUtil.getFlowObj();
	var exeid = flowSubmitObj.EFLOW_EXEID;	
	var rows = $("#payDetailGrid").datagrid("getChecked");
	var flag = true;
	var GRBH=$('input[name="GRBH"]').val();//个人编号
	var SHBZHM=$('input[name="SHBZHM"]').val();//社会保障号码
	var XM=$('input[name="XM"]').val();//姓名
	var dateStr = "";
	dateStr +="&EFLOW_EXEID="+exeid;
	dateStr +="&TemplatePath="+TemplatePath;
	dateStr +="&TemplateName="+TemplateName;
	dateStr +="&GRBH="+GRBH;
	dateStr +="&SHBZHM="+SHBZHM;
	dateStr +="&XM="+XM;
	if(rows.length<=0){
		art.dialog({
			content:"请勾选相应的缴费信息之后才能进行打印！",
			icon:"warning",
			time:5,
			ok: true
		});
		flag = false;
		return ;
	}else if(TemplateName=="基本养老个人历年缴费明细表"){		
		for(var i=0;i<rows.length;i++){
			if(rows[i].aae140!="110"){//养老保险
				art.dialog({
					content:"所勾选缴费信息存在非养老保险险种，请确认！",
					icon:"warning",
					time:5,
					ok: true
				});
				flag = false;
				break;
			}
		}
	}else if(TemplateName=="工伤保险个人历年缴费明细表"){
		for(var i=0;i<rows.length;i++){
			if(rows[i].aae140!="410"){//养老保险
				art.dialog({
					content:"所勾选缴费信息存在非工伤保险险种，请确认！",
					icon:"warning",
					time:5,
					ok: true
				});
				flag = false;
				break;
			}
		}
	}
	if(flag){
		dateStr+="&data="+JSON.stringify(rows);
		//alert("打印数据："+JSON.stringify(rows));
		//打印模版
		$.dialog.open(encodeURI("sbQyzgcxController.do?printPaiedDetail"+dateStr), {
			title : "打印模版",
			width: "100%",
			height: "100%",
			left: "0%",
			top: "0%",
			fixed: true,
			lock : true,
			resize : false
		}, false);
	}
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
	}else if("当前缴费状态"==this.title){
		typeCode="SBJBZT";
	}else if("参保状态"==this.title){
		typeCode="SBCBZT";
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

//获取json信息
function getJsonInfo(flowSubmitObj) {
	var rows = $("#payDetailGrid").datagrid("getChecked");
	if(rows.length>0){
		$("input[type='hidden'][name='PAYDETAILJSON']").val(JSON.stringify(rows));
	}
}


function formatterYear(val,row){
	return "-"+val;
}

//人员参保信息（不弹框）(从接口获取数据）
function insuredDetail(type,inputName){
	var aac001=$('input[name="QYJY_GRBH"]').val();
	var aac002=$('input[name="QYJY_SHBZHM"]').val();
	var url="sbQyzgcxController.do?insuredDetail";
	url+="&aac001="+aac001;
	url+="&aac002="+aac002;
	var layload = layer.load('正在查询，请稍等…');
		$.ajax({
			type: "POST",
			url: url,
			async: false, //采用同步方式进行数据判断
			success: function (responseText) {
				var data=$.parseJSON(responseText);
				console.log(data);
				if(data.success){
					layer.close(layload);
					//数据回填
					var data = JSON.parse(data.jsonString);
					//加载已参加险种信息
					$("#payDetailGrid").datagrid('loadData',data);
				}else if(!data.success){
					layer.close(layload);
					art.dialog({
						content: data.msg,
						icon:"warning",
						time:5,
						ok: true
					});

				}
			}
		});
}


/*
//联级下拉框改变城市
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

//联级下拉框改变区县
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
}*/