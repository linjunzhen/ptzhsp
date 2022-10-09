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
	var expertSn = 1;
	
	var textWidth = function(text){
        var currentObj = $('<span style="postion:absolute;width:auto;left:-9999px">'+ text + '</span>').hide().appendTo(document.body);
        $(currentObj).css('font', '21px 宋体, Arial');
        var width = currentObj.width();
        currentObj.remove();
        return width;
    };
	$(function(){
		//input宽度自适应输入内容
		$("input").unbind('keydown').bind('keydown', function(){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
	    });
		//input宽度自适应输入内容
		$("input").unbind('blur').bind('blur', function(){
	        $(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
	    });
		
		var dataObj = WordUtil.getDataObj();
		if(dataObj){
			$('#opinionDivs').attr('style','display: none;');
			$('#addOpinion').attr('style','display: none;');
			$('#removeOpinion').attr('style','display: none;');
			$('#expertDivs').attr('style','display: none;');
			$('#addExpert').attr('style','display: none;');
			$('#removeExpert').attr('style','display: none;');
    		//初始化表单字段值
    		if(dataObj.variableMap){
    			if(dataObj.variableMap['HAVEOPINION']==2){
    				$('#opinionDivs').attr('style','');
    				$('#addOpinion').attr('style','');
    				$('#removeOpinion').attr('style','');
    				var opinionsn = dataObj.variableMap['GCKXXYJBGOPTION_SN'];
    				for(var i=2;i<=opinionsn;i++){
    					addOpinionDiv();
    				}
    			}
    			if(dataObj.variableMap['HAVEEXPERT']==2){
    				$('#expertDivs').attr('style','');
    				$('#addExpert').attr('style','');
    				$('#removeExpert').attr('style','');
    				var expertsn = dataObj.variableMap['GCKXXYJBGEXPERT_SN'];
    				for(var i=2;i<=expertsn;i++){
    					addExpertDiv();
    				}
    			}
    			WordUtil.initFormFieldValue(dataObj.variableMap,"SHTZBAZHPSHZH");
    		}
    	}
    	
    	//初始化input时宽度自适应
    	$("input").each(function(){
    		if($(this).val()!=null&&$(this).val()!=""){
		    	$(this).width(textWidth($(this).val())>$(this).width()?textWidth($(this).val()):$(this).width());
    		}
		});
		
		AppUtil.initWindowForm("SHTZBAZHPSHZH", function(form, valid) {
			if (valid) {
				getOpinion();
				getExpert();
				$("input[type='submit']").attr("disabled","disabled");
				var formData = WordUtil.getFormEleData("SHTZBAZHPSHZH");
				var url = $("#SHTZBAZHPSHZH").attr("action");
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
			opinionInfo="1.部门无具体修改意见。";
			for(var i=1;i<=opinionSn;i++){
				$("#opinion"+i).val("");
				$("#reason"+i).val("");
			}
		}else{
			opinionInfo="1.部门具体修改意见：\n";
			for(var i=1;i<=opinionSn;i++){
				var opinions =$("#opinion"+i).val();
				var reasons =$("#reason"+i).val();
				if(i>1){
					opinionInfo += "\n";
				}
				opinionInfo +="（"+i+"）"+opinions+"\n主要法律依据及理由：";
				opinionInfo += reasons;
			}
		}
		$("#gcopinion").val(opinionInfo);
		$("#gcopinionsn").val(opinionSn);
	}
	function getExpert(){
		var expertInfo = "";
		if($("input[name='VB_HAVEEXPERT_ST']:checked").val()==1){
			expertInfo="2.不需要专家评审。";
			for(var i=1;i<=expertSn;i++){
				$("#expert"+i).val("");
				$("#expertreason"+i).val("");
			}
		}else{
			expertInfo="2.专家具体修改意见：\n";
			for(var i=1;i<=expertSn;i++){
				var experts =$("#expert"+i).val();
				var reasons =$("#expertreason"+i).val();
				if(i>1){
					expertInfo += "\n";
				}
				expertInfo +="（"+i+"）"+experts+"\n主要法律依据及理由：";
				expertInfo += reasons;
			}
		}
		$("#gcexpert").val(expertInfo);
		$("#gcexpertsn").val(opinionSn);
	}
	function addOpinionDiv(){
		opinionSn = opinionSn +1;
		var html = "<div style=\"display:line-block;line-height:10px;height:10px;\"></div><div id=\"opinionDiv"+opinionSn+"\" style=\"\">";
		html+="("+opinionSn+")&nbsp;<textarea id=\"opinion"+opinionSn+"\" rows=\"3\" data-validation-engine=\"validate[required]\"";
		html+="style=\"width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;\"";
		html+="onpropertychange=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="oninput=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="name=\"VB_OPINION"+opinionSn+"_ST\" ></textarea><br>";
		html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		html+="主要法律依据及理由：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		html+="<textarea id=\"reason"+opinionSn+"\"  rows=\"3\" data-validation-engine=\"validate[required]\"";
		html+="style=\"width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;\"";
		html+="onpropertychange=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="oninput=\"this.style.height=this.scrollHeight + 'px'\" name=\"VB_REASON"+opinionSn+"_ST\" ></textarea></div>";
		$("#opinionDivs").append(html);
	}
	
	function removeOpinionDiv(){
		if(opinionSn==1) return;
		var nowDiv = document.getElementById("opinionDiv"+opinionSn);
		if (nowDiv != null)
        	nowDiv.parentNode.removeChild(nowDiv);
        opinionSn=opinionSn-1;
	}
	

	function addExpertDiv(){
		expertSn = expertSn +1;
		var html = "<div style=\"display:line-block;line-height:10px;height:10px;\"></div><div id=\"expertDiv"+expertSn+"\" style=\"\">";
		html+="("+expertSn+")&nbsp;<textarea id=\"expert"+expertSn+"\" rows=\"3\" data-validation-engine=\"validate[required]\"";
		html+="style=\"width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;\"";
		html+="onpropertychange=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="oninput=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="name=\"VB_EXPERT"+expertSn+"_ST\" ></textarea><br>";
		html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		html+="主要法律依据及理由：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		html+="<textarea id=\"expertreason"+expertSn+"\"  rows=\"3\" data-validation-engine=\"validate[required]\"";
		html+="style=\"width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;\"";
		html+="onpropertychange=\"this.style.height=this.scrollHeight + 'px'\"";
		html+="oninput=\"this.style.height=this.scrollHeight + 'px'\" name=\"VB_EXPERTREASON"+expertSn+"_ST\" ></textarea></div>";
		$("#expertDivs").append(html);
	}
	
	function removeExpertDiv(){
		if(expertSn==1) return;
		var nowDiv = document.getElementById("expertDiv"+expertSn);
		if (nowDiv != null)
        	nowDiv.parentNode.removeChild(nowDiv);
		expertSn=expertSn-1;
	}
</script>
</head>

<body>
	<form id="SHTZBAZHPSHZH" method="post" action="variableController.do?saveHtmlData">
		<!-- ==============开始引入通用的隐藏界面============= -->
		<jsp:include page="commonform.jsp" />
		<!-- ==============结束引入通用的隐藏界面============= -->
		<input type="hidden" name="VB_GCOPTION_ST" id="gcopinion">
		<input type="hidden" name="VB_GCOPTION_SN_ST" id="gcopinionsn">
		<input type="hidden" name="VB_GCEXPERT_ST" id="gcexpert">
		<input type="hidden" name="VB_GCEXPERT_SN_ST" id="gcexpertsn">
		<div class="eui-word">
			<div class="eui-word-top"></div>
			<div class="eui-word-con">
				<div class="eui-word-xmbh">
					<input type="text" placeholder="请输入部门函编号" name="VB_GCHZHBH_ST" class="eui-ipt1" style="width:150px"/>
				</div>
				
				<div class="eui-word-title">
					关于<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" readonly="readonly"
						style="width:150px" name="VB_GCSUBJECT_ST" maxlength="200"
						value="${SQData.blxmmc}" />项目<br>《项目备案请示+》（送审版）综合<br>（专家）评审意见回执函
				</div>
				<div class="eui-word-name">
					<input type="text" class="eui-ipt" style="width:150px"
						data-validation-engine="validate[required]" readonly="readonly"
						value="区行政审批局：" />
				</div>
				<div class="eio-word-content">
					《平潭综合实验区投资项目〈项目备案请示+〉（送审版）综合(专家)评审联系单》（项目编号：岚综实项目〔<input type="text" class="eui-ipt"
					data-validation-engine="validate[required]" style="width:60px" name="VB_GCBHN_ST" maxlength="50"
					value="${SQData.KXXBGXMBHN}" />〕<input type="text" class="eui-ipt"
					data-validation-engine="validate[required]" style="width:35px" name="VB_GCBHH_ST" maxlength="50"
					value="${SQData.KXXBGXMBHH}" />号）收悉。<br>
					   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经对<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]" readonly="readonly"
						style="width:160px" maxlength="200" name="VB_GCSUBJECT_ST"
						value="${SQData.blxmmc}" /> 《项目备案请示+》（送审版）综合(专家)评审涉及的<input type="text" class="eui-ipt"
						data-validation-engine="validate[required]"
						style="width:160px" name="VB_GCSX_ST" maxlength="200" />审批事项进行认真审查，意见如下:<br>

					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEOPINION_ST" value="1" onclick="$('#opinionDivs').attr('style','display: none;');$('#addOpinion').attr('style','display: none;');$('#removeOpinion').attr('style','display: none;');"/>部门无具体修改意见;<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEOPINION_ST" value="2" onclick="$('#opinionDivs').attr('style','');$('#addOpinion').attr('style','');$('#removeOpinion').attr('style','');"/>部门具体修改意见:
					<input type="button" id="addOpinion" value="增加" onclick="addOpinionDiv();" style="display: none;">
					<input type="button" id="removeOpinion" value="删除" onclick="removeOpinionDiv();" style="display: none;">
					<br>
					<div id="opinionDivs" style="">
						<div id="opinionDiv" >
						(1)&nbsp;<textarea id="opinion1" rows="3" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_OPINION1_ST" ></textarea><br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							主要法律依据及理由：<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea rows="3" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_REASON1_ST" id="reason1"></textarea>
						</div>
					</div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEEXPERT_ST" value="1" onclick="$('#expertDivs').attr('style','display: none;');$('#addExpert').attr('style','display: none;');$('#removeExpert').attr('style','display: none;');"/>不需要专家评审;<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="VB_HAVEEXPERT_ST" value="2" onclick="$('#expertDivs').attr('style','');$('#addExpert').attr('style','');$('#removeExpert').attr('style','');"/>专家具体修改意见:
					<input type="button" id="addExpert" value="增加" onclick="addExpertDiv();" style="display: none;">
					<input type="button" id="removeExpert" value="删除" onclick="removeExpertDiv();" style="display: none;">
					<div id="expertDivs" style="">
						<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专家信息：<br>
						  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea id="expertinfo1" rows="3" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_EXPERTS_ST" ></textarea></div>
						<div style="display:line-block;line-height:10px;height:10px;"></div>
						<div id="expertDiv" >
						(1)&nbsp;<textarea id="expert1" rows="3" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_EXPERT1_ST" ></textarea><br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							主要法律依据及理由：<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea rows="3" data-validation-engine="validate[required]"
							style="width: 95%;font-size:21px; vertical-align:top; overflow: hidden;line-height: 36px;"
							onpropertychange="this.style.height=this.scrollHeight + 'px'"
							oninput="this.style.height=this.scrollHeight + 'px'"
							name="VB_EXPERTREASON1_ST" id="expertreason1"></textarea>
						</div>
					</div>
				</div>	
				<br>
				<div class="eui-word-bname">单位盖章</div>
				<div class="eui-word-time">
					<input type="text" class="eui-ipt1 laydate-icon"
						data-validation-engine="validate[required]"
						style="width:150px;height: 36px;" name="VB_GCHZHDQ_TIME_ST"
						maxlength="10" value="${dataMap.DQ_TIME}" readonly="readonly"
						onclick="laydate({istime: false, format: 'YYYY年MM月DD日'})" />
				</div>				
				<div class="eui-word-jjr">
					联系人：<input type="text" class="eui-ipt1" style="width:170px"  name="VB_LXR_ST" maxlength="10" />联系电话：<input
						type="text" class="eui-ipt1" style="width:170px" name="VB_LXDH_ST" maxlength="15"/>
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
