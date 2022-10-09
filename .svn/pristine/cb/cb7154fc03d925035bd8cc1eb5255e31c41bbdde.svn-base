<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <base href="<%=basePath%>">
    <meta name="renderer" content="webkit">
    <script type="text/javascript"
            src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/webpage/common/css/common.css" />
    <script type="text/javascript" src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
    <eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog,swfupload"></eve:resources>
    <script type="text/javascript">

        $(function () {
            selectVal(4);
             //初始化验证引擎的配置
		    $.validationEngine.defaults.autoHidePrompt = true;
		    $.validationEngine.defaults.promptPosition = "topRight";
		    $.validationEngine.defaults.autoHideDelay = "2000";
		    $.validationEngine.defaults.fadeDuration = "0.5";
		    $.validationEngine.defaults.autoPositionUpdate =true;
        })

        function selectVal(val) {
            if (val == 1) {
                $("tr[name='isWq']").hide();
            } else if (val == 4) {
                $("tr[name='isWq']").show();
            }
        }

        function doSelectRows() {
        	//先判断表单是否验证通过
		    var validateResult =$("#busSelectItem").validationEngine("validate");
		    if(validateResult){
		    	  var formData = FlowUtil.getFormEleData('busSelectItem');
		          art.dialog.data("busSelectData", formData);
		          AppUtil.closeLayer();
		    }
        }
        
        //是否接受税务机关根据项目申请人员填报的数据生成申报数据
        function  isAgree(val){
          var userType = '${sessionScope.curLoginMember.USER_TYPE}';//1个人 2企业
          if(val=="0"){//同意
          	$("#trOne").attr("style","");
          	$("#trTwo").attr("style","");
          	$("#trThree").attr("style","");
          	$("#trFour").attr("style","");
          	$("#trFive").attr("style","");
          	$("#trSix").attr("style","");
          	$("#trSeven").attr("style","");
          	$("#trEight").attr("style","");
          	$("#trNine").attr("style","");
          	$("#trTen").attr("style","");
          	$("#trTenOne").attr("style","");
          	$("#trTenTwo").attr("style","");
          	$("#trTenThree").attr("style","");
          	$("#trTenFour").attr("style","");
          	$("#trTenFive").attr("style","");
          	//判断是否为企业登录
          	if(userType!='2'){
               	$("#trTenSix").attr("style","display:none");
         		$("#trTenSeven").attr("style","display:none");
            }else{
            	$("#trTenSix").attr("style","");
         		$("#trTenSeven").attr("style","");
            }
          }else{//不同意
          	$("#trOne").attr("style","display:none");
          	$("#trTwo").attr("style","display:none");
          	$("#trThree").attr("style","display:none");
          	$("#trFour").attr("style","display:none");
          	$("#trFive").attr("style","display:none");
          	$("#trSix").attr("style","display:none");
          	$("#trSeven").attr("style","display:none");
          	$("#trEight").attr("style","display:none");
          	$("#trNine").attr("style","display:none");
          	$("#trTen").attr("style","display:none");
          	$("#trTenOne").attr("style","display:none");
          	$("#trTenTwo").attr("style","display:none");
          	$("#trTenThree").attr("style","display:none");
          	$("#trTenFour").attr("style","display:none");
          	$("#trTenFive").attr("style","display:none");
          	$("#trTenSix").attr("style","display:none");
          	$("#trTenSeven").attr("style","display:none");
          }
        }

    </script>
    <style>
        .flexBox{
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>

<body  style="margin:0px;background-color: #f7f7f7;">

<div class="easyui-layout" fit="true" >
    <div data-options="region:'center',split:false">
    	<form id="busSelectItem" method="post">
        <table style="width:100%;border-collapse:collapse;"
               class="dddl-contentBorder dddl_table" id="gcjsxmcxTable">
            
            <%--<tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>请选择业务类型</a></div></td>
            </tr>
             <tr>
                <td>
                    <eve:radiogroup fieldname="ZYDJ_TYPE" width="500" onclick="selectVal(this.value);" typecode="zydjlx4" defaultvalue="1" value="${busRecord.ZYDJ_TYPE}"></eve:radiogroup>
                </td>
            </tr> --%>
            <tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>请选择办理结果领取方式</a></div></td>
            </tr>
            <tr>
                <td>
                    <eve:radiogroup fieldname="FINISH_GETTYPE" width="500" defaultvalue="01" typecode="BLJGLQFS" value="${busRecord.FINISH_GETTYPE}"></eve:radiogroup>
                </td>
            </tr>
            <tr style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>所有方式</a></div></td>
            </tr>
            <tr>
                <td>
                    <eve:radiogroup fieldname="GYFS" width="500" defaultvalue="0" typecode="GYFS" value="${busRecord.GYFS}"></eve:radiogroup>
                </td>
            </tr>
            <tr name="isWq" style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>请确认是否已完成网签</a></div></td>
            </tr>
            <tr name="isWq">
                <td>
                    <eve:radiogroup fieldname="IS_WQ" width="500" defaultvalue="1" typecode="YesOrNo" value="${busRecord.IS_WQ}"></eve:radiogroup>
                </td>
            </tr>
            <tr  style="height:29px;">
                <td colspan="4" class="dddl-legend"><div class="eui-dddltit"><a>【项目申请人员】</a></div></td>
            </tr>
            <tr>
            	<td>
            	   <p style="margin-left:10px;" class="tab_color">请确认如下事项（在相应的单选框内进行选择）：</p>
            	</td>
            </tr>
            <tr>
            	<td>
            	   <p style="margin-left:10px;" ><b>1.接受税务机关根据项目申请人员所提供材料及不动产登记部门共享信息而生成的申报数据。如有提供虚假材料，愿承担由此引发的一切税收法律责任。</b></p>
            	</td>
            </tr>
            <tr>
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="IS_AGREE"  value="0" onclick="isAgree(this.value);" <c:if test="${busRecord.IS_AGREE=='0'}">checked="checked"</c:if> >同意
			       <input type="radio" class="validate[required]" name="IS_AGREE"  value="1" onclick="isAgree(this.value);" <c:if test="${busRecord.IS_AGREE=='1'}">checked="checked"</c:if> >不同意
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr>
            <tr id="trOne" style="display:none" >
            	<td>
            	   <p style="margin-left:10px;" ><b>2.涉及需缴纳税费的请于收到缴税短信通知之日起15日之内前来领证缴税，逾期未按规定期限缴纳税款的，加收滞纳金。滞纳金从纳税申报之日起，按日加收滞纳税款万分之五计算。<b></p>
            	</td>
            </tr>
            <tr id="trTwo" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="IS_KNOW"  value="0" <c:if test="${busRecord.IS_KNOW=='0'}">checked="checked"</c:if> >已知晓
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr>
            <tr id="trThree" style="display:none">
            	<td>
            	   <p style="margin-left:10px;" ><b>3. 个人购买家庭唯一住房、第二套改善性住房申报享受减征契税政策时,请提供《税务证明事项告知承诺书》（附件1）或提供由房地产主管部门出具的家庭住房情况书面查询结果。</b></p>
            	</td>
            </tr>
            <tr id="trFive" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <%-- <input type="radio" class="validate[required]" name="GF_ZM"  value="0" <c:if test="${busRecord.GF_ZM=='0'}">checked="checked"</c:if> >提供《税务证明事项告知承诺书》 --%>
			       <input type="radio" class="validate[required]" name="GF_ZM"  value="1" <c:if test="${busRecord.GF_ZM=='1'}">checked="checked"</c:if> >提供《家庭住房情况书面查询结果》
			       <input type="radio" class="validate[required]" name="GF_ZM"  value="2" <c:if test="${busRecord.GF_ZM=='2'}">checked="checked"</c:if> >无需享受，不提供
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr>
            <tr id="trSix" style="display:none">
            	<td>
            	   <p style="margin-left:10px;" ><b>4.二手房交易请填写下列《存量房评估补充信息表》以供税务评估系统进行评估，农村地区自建房、商住性质房屋、自建非住房、车位等税务评估系统不支持评估的房产请自行提供评估报告。</b></p>
            	</td>
            </tr>
            <tr id="trSeven" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="ESF_JY"  value="0" <c:if test="${busRecord.ESF_JY=='0'}">checked="checked"</c:if> >需填写
			       <input type="radio" class="validate[required]" name="ESF_JY"  value="1" <c:if test="${busRecord.ESF_JY=='1'}">checked="checked"</c:if> >无需填写
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr>
            <tr id="trEight" style="display:none">
            	<td>
            	   <p style="margin-left:10px;" ><b>5.房产交易个人所得税征收方式选择</b></p>
            	</td>
            </tr>
            <tr id="trNine" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="GRSDS_ZSFS"  value="0" <c:if test="${busRecord.GRSDS_ZSFS=='0'}">checked="checked"</c:if> >核定征收：按房屋转让收入的一定比例（普通住房=转让收入*1%；非普通住房、非住房=转让收入*1.5%；房屋拍卖=转让收入*3%）核定应纳个人所得税额。产权来源属于赠与、继承、离婚析产的，不得选择核定征收，严格按照税法规定据实征收;若提供完税证明、发票等上手原值凭证的亦不可选择核定征收。
			       <br/>
			       <span style="margin-left:10px"></span>
			       <input type="radio" class="validate[required]" name="GRSDS_ZSFS"  value="1" <c:if test="${busRecord.GRSDS_ZSFS=='1'}">checked="checked"</c:if> >据实征收：按转让收入减除房屋原值、转让房屋过程中缴纳的税金及有关合理费用的差额的20%计算个人所得税。[（转让收入—房屋原值—转让过程中缴纳的税金—有关合理费用）*20%]。选择此种方式需提供完整、准确的原购房合同、发票、完税证明等有关扣除凭证。
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr>
            <tr id="trTen" style="display:none">
            	<td>
            	   <p style="margin-left:10px;" ><b>6. 个人转让自用达5年以上，并且是福建省范围内唯一家庭生活用房取得的所得，免征收个人所得税。个人申请免征个人所得税时，应填报《家庭唯一生活用房承诺书》（附件2）。</b></p>
            	</td>
            </tr>
            <tr id="trTenOne" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="GRSDS_MZ"  value="0" <c:if test="${busRecord.GRSDS_MZ=='0'}">checked="checked"</c:if> >需填写
			       <input type="radio" class="validate[required]" name="GRSDS_MZ"  value="1" <c:if test="${busRecord.GRSDS_MZ=='1'}">checked="checked"</c:if> >无需填写
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr>
           <%--  <tr id="trTenTwo" style="display:none">
            	<td>
            	   <p style="margin-left:10px;" ><b>7.个人无偿赠与不动产请填写《个人无偿赠与不动产登记表》（附件3）</b></p>
            	</td>
            </tr>
            <tr id="trTenThree" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="WCZY_GR"  value="0" <c:if test="${busRecord.WCZY_GR=='0'}">checked="checked"</c:if> >需填写
			       <input type="radio" class="validate[required]" name="WCZY_GR"  value="1" <c:if test="${busRecord.WCZY_GR=='1'}">checked="checked"</c:if> >无需填写
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr> --%>
            <tr id="trTenFour" style="display:none">
            	<td>
            	   <p style="margin-left:10px;" ><b>7.开具税收完税证明和增值税普通发票（个人销售不动产）需先在税务系统进行实名注册采集。可关注微信公众号“福建税务”或至税务窗口进行实名注册采集。若授权他人代为开具，授权人和受托人均需进行实名注册采集。</b></p>
            	</td>
            </tr>
            <tr id="trTenFive" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="IS_SFWS"  value="0" <c:if test="${busRecord.IS_SFWS=='0'}">checked="checked"</c:if> >已知晓
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr>
           <%--  <tr id="trTenSix" style="display:none">
            	<td>
            	   <p style="margin-left:10px;" ><b>8.若买方为单位需先办理税务登记才可进行申报，且经办人需提供《实名授权委托书》（附件4）至税务窗口申请关联绑定，也可在电子税务局上自行申请授权。</b></p>
            	</td>
            </tr>
            <tr id="trTenSeven" style="display:none">
            	<td>
            	   <span style="margin-left:10px"></span>
            	   <input type="radio" class="validate[required]" name="IS_SWDJ"  value="0" <c:if test="${busRecord.IS_SWDJ=='0'}">checked="checked"</c:if> >需填写
			       <input type="radio" class="validate[required]" name="IS_SWDJ"  value="1" <c:if test="${busRecord.IS_SWDJ=='1'}">checked="checked"</c:if> >无需填写
			       <font class="tab_color">（*必选）</font>
            	</td>
            </tr> --%>
        </table>
        </form>
    </div>

    <div data-options="region:'south',split:true,border:false"  >
        <div class="eve_buttons" style="text-align: center;">
            <input value="确认所选" type="button" onclick="doSelectRows();"  name="queding"
                   class="z-dlg-button z-dialog-okbutton aui_state_highlight" style="margin-top: 10px;" />

        </div>
    </div>
</div>
</body>
</html>
