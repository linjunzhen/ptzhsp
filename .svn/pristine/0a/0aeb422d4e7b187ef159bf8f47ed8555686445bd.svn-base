<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/wordOnline-1.0/js/WordUtil.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/InternalForm/css/word.css" />
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
<script type="text/javascript">
	var opinionSn = 1;
	
	var textWidth = function(text){
        var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ text + '</span>').hide().appendTo(document.body);
        $(currentObj).css('font', '21px 宋体, Arial');
        var width = currentObj.width();
        currentObj.remove();
        return width+10;
    };
	$(function(){
		//input宽度自适应输入内容
		$("input").unbind('keydown').bind('keydown', function(){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
	    });
	    
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			if(dataObj.variableMap['HAVEOPINION']==2){
    				$('#opinionDivs').attr('style','');
    				$('#addOpinion').attr('style','');
    				$('#removeOpinion').attr('style','');
    				var opinionsn = dataObj.variableMap['GCOPINION_SN'];
    				for(var i=2;i<=opinionsn;i++){
    					addOpinionDiv();
    				}
    			}
    			WordUtil.initFormFieldValue(dataObj.variableMap,"GHXZYDYJHZH");
    		}
    	}
    	
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	//$(this).width(textWidth($(this).val()));
		    	$(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("GHXZYDYJHZH", function(form, valid) {
			if (valid) {
				getOpinion();
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("GHXZYDYJHZH");
				var url = $("#GHXZYDYJHZH").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							  parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
							  art.dialog.data("saveTemplateInfo", {
									templatePath:resultJson.templatePath,
									xnb_id:resultJson.xnb_id
							    });
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
				
		},null,"bottomLeft");
	});
	
	function getOpinion(){
		var opinionInfo = "";
		if($("input[name='VB_HAVEOPINION_ST']:checked").val()==1){
			opinionInfo="无具体修改意见。";
		}else{
			for(var i=1;i<=opinionSn;i++){
				var opinions =$("#opinion"+i).val();
				var reasons =$("#reason"+i).val();
				if(i>1){
					opinionInfo += "\n";
				}
				opinionInfo +="（"+i+"）"+opinions+"，主要法律依据及理由：";
				opinionInfo += reasons;
			}
		}
		$("#gcopinion").val(opinionInfo);
		$("#gcopinionsn").val(opinionSn);
	}
	
	function addOpinionDiv(){
		opinionSn = opinionSn +1;
		var html = "<div id=\"opinionDiv"+opinionSn+"\" style=\"\">";
		html+="("+opinionSn+")&nbsp;<textarea id=\"opinion"+opinionSn+"\" rows=\"6\" data-validation-engine=\"validate[required]\"";
		html+="style=\"width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;\"";
		html+="onpropertychange=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="oninput=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="name=\"VB_OPINION"+opinionSn+"_ST\" ></textarea><br>";
		html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		html+="主要法律依据及理由：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		html+="<textarea id=\"reason"+opinionSn+"\"  rows=\"6\" data-validation-engine=\"validate[required]\"";
		html+="style=\"width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;\"";
		html+="onpropertychange=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="oninput=\"this.style.height=this.scrollHeight + 'px'\" name=\"VB_REASON"+opinionSn+"_ST\" ></textarea></div><br>";
		$("#opinionDivs").append(html);
	}
	
	function removeOpinionDiv(){
		if(opinionSn==1) return;
		var nowDiv = document.getElementById("opinionDiv"+opinionSn);
		if (nowDiv != null)
        	nowDiv.parentNode.removeChild(nowDiv);
        opinionSn=opinionSn-1;
	}
</script>
</head>

<body>
	<form id="GHXZYDYJHZH" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<input type="hidden" name="VB_GCOPINION_ST" id="gcopinion">
		<input type="hidden" name="VB_GCOPINION_SN_ST" id="gcopinionsn">
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-xmbh">
					<input type="text" placeholder="请输入部门函编号" name="VB_GCHZHBH_ST" class="eui-ipt1" style="width:150px"/>
				</div>
				<br>
				<div class="eui-word-title">
					关于<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" readonly="readonly"
						style="width:273px" name="VB_GCSUBJECT_ST" maxlength="200"
						value="${SQData.blxmmc}" />项目<br>规划选址初步意见回执函
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" style="width:150px"
						data-validation-engine="validate[required]" readonly="readonly"
						value="区行政审批局：" />
				</div>
				<div class="eio-word-content">
					你局《平潭综合实验区投资项目规划选址及用地的征求联系单》（项目编号：岚综实项目〔<input type="text" class="eui-ipt"
					data-validation-engine="validate[required]" 
					style="width:60px" name="VB_XMNH_ST" maxlength="4"/>〕 <input type="text" class="eui-ipt"
					data-validation-engine="validate[required]" 
					style="width:30px" name="VB_XMBH_ST" maxlength="4"/> 号）收悉。<br>
					     经对<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" readonly="readonly"
						style="width:273px" maxlength="200"
						value="${SQData.blxmmc}" /> 项目规划选址及用地申请涉及的<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]"
						style="width:273px" name="VB_GCAPPROVALITEM_ST" maxlength="200" />审批事项进行认真审查，意见如下:<br>

					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEOPINION_ST" value="1" onclick="$('#opinionDivs').attr('style','display: none;');$('#addOpinion').attr('style','display: none;');$('#removeOpinion').attr('style','display: none;');"/>无具体修改意见<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEOPINION_ST" value="2" onclick="$('#opinionDivs').attr('style','');$('#addOpinion').attr('style','');$('#removeOpinion').attr('style','');"/>具体意见:
					
					<input type="button" id="addOpinion" value="增加" onclick="addOpinionDiv();" style="display: none;">
					<input type="button" id="removeOpinion" value="删除" onclick="removeOpinionDiv();" style="display: none;">
					<br>
					<div id="opinionDivs" style="display: none;">
						<div id="opinionDiv" >
						(1)&nbsp;<textarea id="opinion1" rows="6" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_OPINION1_ST" ></textarea><br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							主要法律依据及理由：<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea rows="6" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_REASON1_ST" id="reason1"></textarea>
						</div><br>
					</div>
				</div>	
				<br>
				<div class="eui-word-bname">单位盖章</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_GCDQ_TIME_ST"
						maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
				</div>				
				<div class="eui-word-jjr">
					联系人：<input type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:170px"  name="VB_LXR_ST" maxlength="10" />联系电话：<input
						type="text" class="eui-ipt1" data-validation-engine="validate[required]" style="width:170px" name="VB_LXDH_ST" maxlength="15"/>
				</div>
			</div>
			<div class="eui-word-bottom"></div>
		</div>
		<div class="eve_buttons">
		    <input value="保存" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
