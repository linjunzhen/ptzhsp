<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
<div class="syj-title1 tmargin20">
	<span>企业信息查询</span>
</div>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" id="queryArea">
	<!-- 	<tr>
			 <th>统一社会信用代码：</th>
			<td colspan="2">
				<input type="text" class="syj-input1" style="width:250px;"
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
		</tr> -->
		
		<tr>
			<th>企业名称：</th>
			<td >
				<input type="text" class="syj-input1" style="width:540px;"
					   name="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称全称" />
			</td>
			<td style="width:15%">	
				<span class="btnOneP" onclick="queryCreditCompanyInfo()" style="cursor:pointer;width: 60px; margin-left: 20px;height: 28px;line-height:28px;">公司查询</span>
			</td>
		</tr>

	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="width:95%" id="changeOptions">							
		<tr>
			<th style="width:5%;color:red" >选择变更事项</th>
			<th style="width:10%">变更事项</th>
			<th>变更前内容</th>
			<th>变更后内容</th>
		</tr>
	</table>
</div>
	
<script type="text/javascript">
	$(function () {
		setChangeSelection();
	});
		
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
						if(data.baseInfo.REG_CAP==null || data.baseInfo.REG_CAP ==undefined || data.baseInfo.REG_CAP==""){
							$("#content_05").html("");
						}else{
							$("#content_05").html(data.baseInfo.REG_CAP+" 万元");	
						}				
						if(data.baseInfo.OP_TO!=undefined){
							$("#content_06").html((data.baseInfo.OP_FROM).substring(0,10)+" 至 "+(data.baseInfo.OP_TO).substring(0,10));
						}else{
							$("#content_06").html((data.baseInfo.OP_FROM).substring(0,10)+" 至 ");
						}
						$("#content_08").html(data.baseInfo.OP_SCOPE.replace(/\s/g,""));
						var investors = data.investors;
						var html_07="";
						var html_09="";
						for(var i=0;i,i<investors.length;i++){
						    /* if(investors[i].B_LIC_NO==undefined){
						    	html_07+=investors[i].INV_NAME+"  证照号码："+"<br>";
						    }else{
						    	html_07+=investors[i].INV_NAME+"  证照号码："+investors[i].B_LIC_NO+"<br>";
						    } */
						    html_07+=investors[i].INV_NAME+"<br>";
							
							html_09+=investors[i].INV_NAME+"<br>";
						}
						$("#content_07").html(html_07);
						$("#content_09").html(html_09);
						var members = data.members;
						var director = "";
						var supervisor = "";
						var manager = "";
						var jsnum = 0;
						for(var i=0;i<members.length;i++){
							if(members[i].POSITION_NAME.indexOf("董事")>=0){
								director += members[i].POSITION_NAME + "：" + members[i].PERSON_NAME+"<br>";
							}else if(members[i].POSITION_NAME.indexOf("监事")>=0){
								supervisor += members[i].POSITION_NAME + "：" + members[i].PERSON_NAME+"<br>";
								jsnum++;
							}else if(members[i].POSITION_NAME.indexOf("经理")>=0){
								manager += members[i].POSITION_NAME + "：" + members[i].PERSON_NAME+"<br>";
							}
						}
						$("#content_10").html(director);
						if(jsnum>1){
						    //隐藏监事备案选择项
						    $("#jsba").attr("style","display:none");
						}
						$("#content_11").html(supervisor);
						$("#content_12").html(manager);
						queryDone = true;
						
						queryBackBaseInfo(data.baseInfo);
						queryBackInvestors(data.investors);
						queryBackDirectors(data.members);
						queryBackSupervisors(data.members);
						queryBackManagers(data.members);
						queryBackContact(data.contact);
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
	
	function setChangeSelection(){
		$.post("enterpriseChangeController/findChangeOption.do",{
		}, function(responseText, status, xhr) {
			var obj = JSON.parse(responseText);
			var options = eval('(' + obj.jsonString + ')');
			for(var i=0;i<options.length;i++){
				var html = "";
				if(options[i].DIC_NAME=="监事备案"){
					html+="<tr id='jsba'><td style='text-align: center;'><input type='checkbox' name='CHANGE_OPTIONS' value='"+options[i].DIC_CODE+"'></td>";
					html+="<td>"+options[i].DIC_NAME+"</td>";
					html+="<td id='content_"+options[i].DIC_CODE+"'></td>";
					html+="<td id='change_"+options[i].DIC_CODE+"'></td></tr>";
				}else{
					html+="<tr><td style='text-align: center;'><input type='checkbox' name='CHANGE_OPTIONS' value='"+options[i].DIC_CODE+"'></td>";
					html+="<td>"+options[i].DIC_NAME+"</td>";
					html+="<td id='content_"+options[i].DIC_CODE+"'></td>";
					html+="<td id='change_"+options[i].DIC_CODE+"'></td></tr>";
				}
				
				$("#changeOptions").append(html);
					
			}
			//设置变更选项选择框点击事件
			setClickEvent();
			
			//变更选项回填
			$('input[name="CHANGE_OPTIONS"]').each(function() {
				var checked = '${busRecord.CHANGE_OPTIONS}';
				if(checked.indexOf($(this).val())>=0){
			        $(this).attr('checked', true);
			        var val = $(this).val();
			        if(val=='01'){//名称
		    			C_COMPANYNAME_FN('1');
		    		}else if(val=='02'){//住所
		    			C_REGADDRESS_FN("1");
		    		}else if(val=='03'){//法定代表人	    		
		    			C_LEGAL_FN('1');
		    		}else if(val=='04'){//企业类型	    		
		    			C_COMPANYTYPE_FN('1');	    			
		    		}else if(val=='05'||val=='07'||val=='09'){//注册资本金\投资人股权\	股东名称
		    			C_CAPTITAL_FN('1');
		    		}else if(val=='06'){//经营期限		
		    			C_BUSINESSYEAR_FN('1');  			
		    		}else if(val=='08'){//经营范围
		    			C_BUSINESSSCOPE_FN('1');
		    		}else if(val=='10'){//董事备案		
		    			C_DIRECTOR_FN('1');	    				    			
		    		}else if(val=='11'){//监事备案		
		    			C_SUPERVISOR_FN('1');   				    			
		    		}else if(val=='12'){//经理备案		
		    			C_MANAGER_FN('1');			    			
		    		}
				}
				
			});
			
			
			if('${busRecord.CHANGE_ID}'){
				//变更前内容
				$("#content_01").html('${busRecord.COMPANY_NAME}');
				$("#content_02").html('${busRecord.REGISTER_ADDR}');
				$("#content_03").html('${busRecord.LEGAL_NAME}');
				$("#content_04").html('${busRecord.COMPANY_TYPE_NAME}');					
				$("#content_05").html('${busRecord.INVESTMENT}'+" 万元");
				$("#content_06").html('${busRecord.YEAR_FROM}'+" 至 "+'${busRecord.YEAR_TO}');
				$("#content_08").html('${busRecord.BUSSINESS_SCOPE}');
				var holderJson = '${busRecord.HOLDER_JSON}';
				var holers =  eval('(' + holderJson + ')');
				var html_07="";
				var html_09="";
				for(var i=0;i<holers.length;i++){
					html_07+=holers[i].SHAREHOLDER_NAME+"  证照号码："+holers[i].LICENCE_NO+"<br>";
					html_09+=holers[i].SHAREHOLDER_NAME+"<br>";	
					//股权来源选项				
					var stockFromOption = "原股东-"+holers[i].SHAREHOLDER_NAME; 
					stockFromOptionHtml += "<option value='"+stockFromOption+"'>"+stockFromOption+"</option>";
					//复用人员选项
					$("select[name='setHolderInfo']").append("<option value='"+i+"SHAREHOLDER'>"+"原股东-"+holers[i].SHAREHOLDER_NAME+"</option>");
				}				
				$("#content_07").html(html_07);
				$("#content_09").html(html_09);
				//股权来源选项
				$("select[name='stockFrom']").html(stockFromOptionHtml);
				
				var html_10="";
				var html_11="";
				var html_12="";				
				<c:forEach items="${directorList}" var="director">
					html_10 += "${director.DIRECTOR_JOB_OLD_NAME}：${director.DIRECTOR_NAME_OLD}<br>";
				</c:forEach>
				<c:forEach items="${supervisorList}" var="supervisor">					
					html_11 += "${supervisor.SUPERVISOR_JOB_OLD_NAME}：${supervisor.SUPERVISOR_NAME_OLD}<br/>";
				</c:forEach>
				<c:forEach items="${managerList}" var="manager">					
					html_12 += "${manager.MANAGER_JOB_OLD_NAME}：${manager.MANAGER_NAME_OLD}<br/>";
				</c:forEach>			
				$("#content_10").html(html_10);
				$("#content_11").html(html_11);
				$("#content_12").html(html_12);
				
				//变更后内容			
				$("#change_01").html('${busRecord.COMPANY_NAME_CHANGE}');
				$("#change_02").html('${busRecord.REGISTER_ADDR_CHANGE}');
				$("#change_03").html('${busRecord.LEGAL_NAME_CHANGE}');
				$("#change_04").html('${busRecord.COMPANY_TYPE_NAME_CHANGE}');
				var investmentChange = Number('${busRecord.INVESTMENT_CHANGE}');
				if(investmentChange>0){
					$("#change_05").html(investmentChange.toFixed(0)+" 万元");
				}else{
					$("#change_05").html("");
				}	
				
				//是否有进行经营期限变更
				var checked = '${busRecord.CHANGE_OPTIONS}';
				if(checked.indexOf("06")>=0){
					if('${busRecord.OPRRATE_TERM_TYPE_CHANGE}'=="1"){
						$("#change_06").html('${busRecord.BUSSINESS_YEARS_CHANGE}'+"年");
					}else{
						$("#change_06").html("长期");
					}
				}else{
					$("#change_06").html("");
				} 
				
				$("#change_08").html('${busRecord.BUSSINESS_SCOPE_CHANGE}');
				var newhtml_07="";
				var newhtml_09="";				
				for(var i=0;i<holers.length;i++){
					if(holers[i].CONTRIBUTIONS_REMAIN>0){
						newhtml_07+=holers[i].SHAREHOLDER_NAME+"  证照号码："+holers[i].LICENCE_NO+"<br>";
						newhtml_09+=holers[i].SHAREHOLDER_NAME+"<br>";	
					}					
				}
				var newhtml_10="";
				var newhtml_11="";
				var newhtml_12="";
								
				<c:forEach items="${directorChangeList}" var="directorChange">		
					newhtml_10 += "${directorChange.DIRECTOR_JOB_NAME}：${directorChange.DIRECTOR_NAME}<br>";
				</c:forEach>
				<c:forEach items="${supervisorChangeList}" var="supervisorChange">					
					newhtml_11 += "${supervisorChange.SUPERVISOR_JOB_NAME}：${supervisorChange.SUPERVISOR_NAME}<br>";
				</c:forEach>
				<c:forEach items="${managerChangeList}" var="managerChange">					
					newhtml_12 += "${managerChange.MANAGER_JOB_NAME}：${managerChange.MANAGER_NAME}<br>";
				</c:forEach>
				$("#change_10").html(newhtml_10);
				$("#change_11").html(newhtml_11);
				$("#change_12").html(newhtml_12);
				
				if("${fn:length(supervisorList)}">1){
					//隐藏监事备案选择项
				    $("#jsba").attr("style","display:none");
				} 
				
				setCurrency2('${busRecord.CURRENCY_CHANGE}');
				
				queryDone = true;
				//数据初始化设置股权来源
				var i=0;
				<c:forEach items="${holderChangeList}" var="holder">
					var j=0;
			    	<c:forEach items="${holder.gqlyList}" var="gqly">
			    		var $obj = $("#newGdxxDiv div").eq(i).find(".gqlytable tbody").eq(j);
			    		$obj.find("select[name='stockFrom']").find("option[value='${gqly.stockFrom}']").attr("selected",true);
			    		
			    		if('${gqly.stockFrom}'!='新增'){
							$obj.find("tr[id='transfer1']").attr("style","");
							$obj.find("tr[id='transfer2']").attr("style","");
							$obj.find("[name='TRANSFER_PRICE']").addClass("validate[required]");
							if($obj.find("[name='TRANSFER_PRICE']").val()!="0"){
								$obj.find("[name='PAYMETHOD']").addClass("validate[required]");
							}else{
								$obj.find("[id='fkfs']").attr("style","display:none");
								$obj.find("[name='PAYMETHOD']").removeClass("validate[required]");
							}
							
						}else{
							$obj.find("tr[id='transfer1']").attr("style","display:none");
							$obj.find("tr[id='transfer2']").attr("style","display:none");
						}
			    		
			    		j++;
			    	</c:forEach>			    	
					newhtml_07 += "${holder.SHAREHOLDER_NAME}  证照号码：${holder.LICENCE_NO}<br>";
					newhtml_09 += "${holder.SHAREHOLDER_NAME}<br>";
			    	//复用人员选项
					$("select[name='setHolderInfo']").append("<option value='new"+i+"SHAREHOLDER'>"+"新股东-${holder.SHAREHOLDER_NAME}</option>");
			    	i++;
			    </c:forEach>
			    
			    var checked = '${busRecord.CHANGE_OPTIONS}';
			    if(checked.indexOf("07")>=0){
			    	$("#change_07").html(newhtml_07);
			    }else {
			    	$("#change_07").html("");
			    }
			    
				if(checked.indexOf("09")>=0){
			    	$("#change_09").html(newhtml_09);
			    }else {
			    	$("#change_09").html("");
			    }
				
				
				//数据初始化设置人事按钮
				if('${busRecord.COMPANY_NATURE}'=='02'){//设董事会、监事设立 
					$("#addGdxx").show();
					$("#addDsxx").show();
				}else if('${busRecord.COMPANY_NATURE}'=='07'){//设执行董事设立
			        $("#addGdxx").show();
			        $("#addDsxx").show();
			    }else if('${busRecord.COMPANY_NATURE}'=='03'){//设执行董事设立
					$("#addGdxx").show();
				}else if('${busRecord.COMPANY_NATURE}'=='04'){//设董事会监事会设立
					$("#addGdxx").show();
					$("#addDsxx").show();
					$("#addJsxx").show();
					
					//监事职务能选择
					$("[name$='SUPERVISOR_JOB']").attr("disabled",false);
				}else{
					$("#addGdxx").hide();
					$("#addDsxx").hide();
					$("#addJsxx").hide();
					$("#addJlxx").hide();
					$("[name$='MANAGER_APPOINTOR']").val('股东');
				}
				if('${busRecord.IS_LIAISON_CHANGE}'=='1'){					
					$("input[name='IS_LIAISON_CHANGE']").closest("div").find('[name="setHolderInfo"]').attr("disabled",false);
					$('[name="LIAISON_NAME"]').attr("disabled",false);
					$('[name="LIAISON_FIXEDLINE"]').attr("disabled",false);
					$('[name="LIAISON_MOBILE"]').attr("disabled",false);
					$('[name="LIAISON_EMAIL"]').attr("disabled",false);
					$('[name="LIAISON_IDTYPE"]').attr("disabled",false);
					$('[name="LIAISON_IDNO"]').attr("disabled",false);
				}
								
			}
			
		});
	}
	
	function setClickEvent(){
		//变更选项选择
	    $("input[name='CHANGE_OPTIONS']").on("click", function(event) {
	    	var val = $(this).val();
	    	if($(this).is(":checked")){
	    		if(val=='01'){//名称
	    			C_COMPANYNAME_FN('1');
	    		}else if(val=='02'){//住所
	    			C_REGADDRESS_FN("1");
	    		}else if(val=='03'){//法定代表人	    		
	    			C_LEGAL_FN('1');
	    		}else if(val=='04'){//企业类型	    		
	    			C_COMPANYTYPE_FN('1');	    			
	    		}else if(val=='05'||val=='07'||val=='09'){//注册资本金\投资人股权\	股东名称
	    			C_CAPTITAL_FN('1');
	    		}else if(val=='06'){//经营期限		
	    			C_BUSINESSYEAR_FN('1');  			
	    		}else if(val=='08'){//经营范围
	    			C_BUSINESSSCOPE_FN('1');
	    		}else if(val=='10'){//董事备案		
	    			C_DIRECTOR_FN('1');	    				    			
	    		}else if(val=='11'){//监事备案		
	    			C_SUPERVISOR_FN('1');   				    			
	    		}else if(val=='12'){//经理备案		
	    			C_MANAGER_FN('1');			    			
	    		}
	    	}else{	    		
	    		if(val=='01'){//名称
	    			C_COMPANYNAME_FN('-1');
	    		}else if(val=='02'){//住所
	    			C_REGADDRESS_FN('-1');
	    		}else if(val=='03'){//法定代表人	    		
	    			C_LEGAL_FN('-1');
	    		}else if(val=='04'){//企业类型
	    			C_COMPANYTYPE_FN('-1');	    			
	    		}else if(val=='05'||val=='07'||val=='09'){//注册资本金\投资人股权\	股东名称
	    			C_CAPTITAL_FN('-1');
	    		}else if(val=='06'){//经营期限
	    			C_BUSINESSYEAR_FN('-1');	    			
	    		}else if(val=='08'){//经营范围
	    			C_BUSINESSSCOPE_FN('-1');	    				    			
	    		}else if(val=='10'){//董事备案		
	    			C_DIRECTOR_FN('-1');	    				    				    			
	    		}else if(val=='11'){//监事备案		
	    			C_SUPERVISOR_FN('-1');  				    			
	    		}else if(val=='12'){//经理备案		
	    			C_MANAGER_FN('-1');	    				    			
	    		}
	    	}
	    });
	}
</script>
