<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
  String registerType = request.getParameter("registerType");
  request.setAttribute("registerType", registerType);
%>
<style type="text/css">
	/*****增加CSS****/
	.btnOneP {
		background: #2da2f2 none repeat scroll 0 0;
		color: #fff;
		display: inline-block;
		font-weight: bold;
		height: 34px;
		line-height: 34px;
		margin-top: 10px;
		text-align: center;
		width: 106px;
	}
</style>
<script>
	$(function () {
		setChangeSelection();
	});
	
	function setChangeSelection(){
		$.post("enterpriseChangeController/findChangeOption.do",{
		}, function(responseText, status, xhr) {
			var obj = JSON.parse(responseText);
			var options = eval('(' + obj.jsonString + ')');
			for(var i=0;i<options.length;i++){
				var html = "";
				html+="<tr><td style='text-align: center;'><input type='checkbox' name='CHANGE_OPTIONS' value='"+options[i].DIC_CODE+"'></td>";
				html+="<td>"+options[i].DIC_NAME+"</td>";
				html+="<td id='content_"+options[i].DIC_CODE+"'></td></tr>";
				$("#changeOptions").append(html);
			}
			
		});
	}
	
	var queryDone = false;
	function queryCreditCompanyInfo(){
		queryDone = false;
		$("#content_01").html("");
		$("#content_02").html("");
		$("#content_03").html("");
		$("#content_04").html("");
		$("#content_05").html("");
		$("#content_06").html("");
		$("#content_07").html("");
		$("#content_08").html("");
		$("#content_09").html("");
		$("#content_10").html("");
		$("#content_11").html("");
		$("#content_12").html("");
		var companyName = $("input[name='COMPANY_NAME']").val();
		var socialCreditUnicode = $("input[name='SOCIAL_CREDITUNICODE']").val();
		if(companyName==''&&socialCreditUnicode==''){			
			art.dialog({
				content : "请输入查询条件！",
				icon : "warning",
				ok : true
			});
		}else{
			$.post("enterpriseChangeController/queryCreditEnterprise.do",{
				socialCreditUnicode:socialCreditUnicode,
				companyName:companyName
			}, function(responseText, status, xhr) {
				var obj = JSON.parse(responseText);
				if(obj.success){
					var data = eval('(' + obj.jsonString + ')');
					if(data.baseInfo){
						$("#enterpriseData").val(obj.jsonString);
						$("#content_01").html(data.baseInfo.ENT_NAME);
						$("#content_02").html(data.baseInfo.DOM);
						$("#content_03").html(data.baseInfo.LEREP);
						$("#content_04").html(data.baseInfo.ENT_TYPE_NAME);					
						$("#content_05").html(data.baseInfo.REG_CAP+" 万元");	
						if(data.baseInfo.OP_TO!=undefined){
							$("#content_06").html((data.baseInfo.OP_FROM).substring(0,10)+" 至 "+(data.baseInfo.OP_TO).substring(0,10));
						}else{
							$("#content_06").html((data.baseInfo.OP_FROM).substring(0,10)+" 至 ");
						}
						
						$("#content_08").html(data.baseInfo.OP_SCOPE);
						var investors = data.investors;
						var html_07="";
						var html_09="";
						for(var i=0;i,i<investors.length;i++){
							html_07+=investors[i].INV_NAME+"  证照号码："+investors[i].B_LIC_NO+"<br>";
							html_09+=investors[i].INV_NAME+"<br>";
						}
						$("#content_07").html(html_07);
						$("#content_09").html(html_09);
						var members = data.members;
						var director = "";
						var supervisor = "";
						var manager = "";
						for(var i=0;i<members.length;i++){
							if(members[i].POSITION_NAME.indexOf("董事")>=0){
								director += members[i].POSITION_NAME + "：" + members[i].PERSON_NAME;
							}else if(members[i].POSITION_NAME.indexOf("监事")>=0){
								supervisor += members[i].POSITION_NAME + "：" + members[i].PERSON_NAME;
							}else if(members[i].POSITION_NAME.indexOf("经理")>=0){
								manager += members[i].POSITION_NAME + "：" + members[i].PERSON_NAME;
							}
						}
						$("#content_10").html(director);
						$("#content_11").html(supervisor);
						$("#content_12").html(manager);
						queryDone = true;
					}else{	
						art.dialog({
							content : "未查询到匹配的企业信息，请确认是否正确输入查询条件！",
							icon : "warning",
							ok : true
						});
					}
				}else{		
					art.dialog({
						content : obj.msg,
						icon : "error",
						ok : true
					});
				}
			});
		}
	}

	function next() {
		var checked = false;
		var legalChecked = false;
		var legalRelateChecked = false;
		$.each($('input[name="CHANGE_OPTIONS"]:checked'),function(){
	        checked = true;
	        if($(this).val()=='03'){
	        	legalChecked = true;
	        }
	        if($(this).val()=='10'||$(this).val()=='12'){
	        	legalRelateChecked = true;
	        }
	    });
		if(!checked){		
			art.dialog({
				content : "请选择变更项",
				icon : "warning",
				ok : true
			});
			return false;
		}
		if(legalChecked&&!legalRelateChecked){		
			art.dialog({
				content : "法人代表不可单独变更，必定涉及到董事或经理备案",
				icon : "warning",
				ok : true
			});
			return false;
		}
		if(!queryDone){		
			art.dialog({
				content : "请先正确查询企业信息后再进行下一步",
				icon : "warning",
				ok : true
			});
			return false;
		}
        $("#container2").show();
        $("#container1").hide();
    }

</script>
<div class="container" id="container1" style=" display:none;overflow:hidden; margin-bottom:20px;background:url(webpage/website/applyforms/change/images/netbg.png) right bottom no-repeat #fff;min-height:500px;">
	<div class="net-detail">
		<div class="syj-tyys">
			<div class="hd syj-tabtitle">
				<ul>
					<li><a href="javasrcipt:void(0)">变更事项选择</a></li>
				</ul>
			</div>
			<div class="bd">
				<div class="top-container">
					<input type="hidden" name="enterpriseData" value="">
					<table cellpadding="0" cellspacing="0" class="syj-table2" style="margin-top: 10px;width:95%;">
						<tr>
							<th>统一社会信用代码：</th>
							<td colspan="2">
								<input type="text" class="syj-input1"
									   name="SOCIAL_CREDITUNICODE" maxlength="18" placeholder="请输入统一社会信用代码" />
							</td>
							<td rowspan="2" style="width:15%">	
								<span class="btnOneP" onclick="queryCreditCompanyInfo()" style="cursor:pointer;width: 60px; margin-left: 20px;height: 28px;line-height:28px;">公司查询</span>
							
							</td>
						</tr>
						<tr>
							<th>企业名称：</th>
							<td colspan="2">
								<input type="text" class="syj-input1" style="width:540px;"
									   name="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称全称" />
							</td>
						</tr>
					</table>
					<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="width:95%" id="changeOptions">							
						<tr>
							<th style="width:3%">是否变更</th>
							<th style="width:10%">变更事项</th>
							<th>变更前内容</th>
						</tr>
					</table>
					<div class="tap-btn" style="padding-left: 0px;margin-bottom: 5px;">

						<a class="tap_next" onclick="next()">下一步</a>
					</div>
				</div>
					
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
    jQuery(".eui-menu").slide({
        type:"menu", //效果类型
        titCell:".syj-location", // 鼠标触发对象
        targetCell:".syj-city", // 效果对象，必须被titCell包含
        delayTime:0, // 效果时间
        defaultPlay:false,  //默认不执行
        returnDefault:true // 返回默认
    });
    jQuery(".syj-tyys").slide({trigger:"click"});
</script>