<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>

.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
</style>
<script type="text/javascript">

$(function(){
	
});
function setNum(obj){
	var fdcqsAmount = obj.value;
	var fdcqsAmountH = Math.ceil(obj.value/2);
	$("input[name='FDCQS_AMOUNT_HALF']").val(fdcqsAmountH);
	var fdcqsAmountHC = changeMoneyToChinese(fdcqsAmountH);
	$("input[name='FDCQS_AMOUNT_HALFCAP']").val(fdcqsAmountHC);
};
	
function changeMoneyToChinese( money ){
	var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字
	var cnIntRadice = new Array("","拾","佰","仟"); //基本单位
	var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位
	var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位
	var cnInteger = "整"; //整数金额时后面跟的字符
	var cnIntLast = "元"; //整型完以后的单位
	var maxNum = 999999999999999.9999; //最大处理的数字
	
	var IntegerNum; //金额整数部分
	var DecimalNum; //金额小数部分
	var ChineseStr=""; //输出的中文金额字符串
	var parts; //分离金额后用的数组，预定义
	
	if( money == "" ){
		return "";
	}
	
	money = parseFloat(money);
	//alert(money);
	if( money >= maxNum ){
		$.alert('超出最大处理数字');
		return "";
	}
	if( money == 0 ){
		ChineseStr = cnNums[0]+cnIntLast+cnInteger;
		//document.getElementById("show").value=ChineseStr;
		return ChineseStr;
	}
	money = money.toString(); //转换为字符串
	if( money.indexOf(".") == -1 ){
		IntegerNum = money;
		DecimalNum = '';
	}else{
		parts = money.split(".");
		IntegerNum = parts[0];
		DecimalNum = parts[1].substr(0,4);
	}
	if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换
		zeroCount = 0;
		IntLen = IntegerNum.length;
		for( i=0;i<IntLen;i++ ){
			n = IntegerNum.substr(i,1);
			p = IntLen - i - 1;
			q = p / 4;
			m = p % 4;
			if( n == "0" ){
				zeroCount++;
			}else{
				if( zeroCount > 0 ){
					ChineseStr += cnNums[0];
				}
				zeroCount = 0; //归零
				ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];
			}
			if( m==0 && zeroCount<4 ){
				ChineseStr += cnIntUnits[q];
			}
		}
		ChineseStr += cnIntLast;
		//整型部分处理完毕
	}
	if( DecimalNum!= '' ){//小数部分
		decLen = DecimalNum.length;
		for( i=0; i<decLen; i++ ){
			n = DecimalNum.substr(i,1);
			if( n != '0' ){
				ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
			}
		}
	}
	if( ChineseStr == '' ){
		ChineseStr += cnNums[0]+cnIntLast+cnInteger;
	} else if( DecimalNum == '' ){
		ChineseStr += cnInteger;
	}
	return ChineseStr;
};


function showTemplate(typeId,TemplatePath,TemplateName){
	//var flowVars = JSON2.parse($("#flowSubmitInfoJson").val());
    var flowSubmitObj = FlowUtil.getFlowObj();
    var exeid = flowSubmitObj.EFLOW_EXEID;
	var dateStr = "";
	//dateStr +="&EFLOW_EXEID="+flowVars.EFLOW_EXEID;
	dateStr +="&EFLOW_EXEID="+exeid;
	dateStr +="&typeId="+typeId;
	dateStr +="&TemplatePath="+TemplatePath;
	dateStr +="&TemplateName="+TemplateName;
    //打印模版
   $.dialog.open(encodeURI("printAttachController.do?printTemplate"+dateStr), {
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

</script>
<div id="fdcqsjb" style="display: none;">
<div class="bsbox clearfix">
    <input type="hidden" name="FDCQS_AMOUNT_HALF" />
    <input type="hidden" name="FDCQS_AMOUNT_HALFCAP" />
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>房地产契税奖补信息</span></li>			
		</ul>
	</div>
	<div class="bsboxC">
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th colspan="4"><span class="bscolor1">友情提示:申报成功后请于办件详情页内打印奖补表!</span>
				<a class="eflowbutton" id="tableOfFdcqsjb" onclick="showTemplate(3,'20170216114138.doc','平潭综合实验区奖补审批表(契税)');" style="float:right;height: 28px;display: none;">生成奖补审批表</a></th>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="bstable1" >
			<tr>
				<th><span class="bscolor1">*</span>缴纳契税金额(元):</th>
				<td>
				<input type="text" maxlength="30" name="FDCQS_AMOUNT" 
				class="tab_text validate[required,custom[numberplus]]" onblur="setNum(this)" />
				</td>
				<th><span class="bscolor1">*</span> 纳税时间:</th>
				<td ><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="tab_text validate[required] Wdate" maxlength="30"
					name="FDCQS_PAYMENT_TIME"  style="border: 1px solid #bbb;height: 24px;"/></td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>开户银行:</th>
				<td>
					<input type="text" class="tab_text validate[required]" maxlength="30" name="FDCQS_BANK" />
				</td>
				<th><span class="bscolor1">*</span>银行账号:</th>
				<td>
					<input type="text" class="tab_text validate[required,custom[number]]" maxlength="30" name="FDCQS_BANK_ACCOUNT" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>新购房具体位(含楼栋号):</th>
				<td>
					<input type="text" class="tab_text validate[required]" maxlength="30" name="FDCQS_LOCATION" />
				</td>
				<th><span class="bscolor1">*</span>开发商及项目名称:</th>
				<td>
					<input type="text" class="tab_text validate[required]" maxlength="30" name="FDCQS_DEVELOPERS" />
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>备案信息表业务宗号:</th>
				<td>
					<input type="text" class="tab_text validate[required]" maxlength="30" name="FDCQS_CONTFIL_NUM" />
				</td>
				<th><span class="bscolor1">*</span>商品房买卖合同备案时间:</th>
				<td><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="tab_text validate[required] Wdate" maxlength="32"
					name="FDCQS_CONTFIL_TIME"  style="border: 1px solid #bbb;height: 24px;"/></td>
			</tr>
		</table>	
	</div>
</div>
</div>

<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->