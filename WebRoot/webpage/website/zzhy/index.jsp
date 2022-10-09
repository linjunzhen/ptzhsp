<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>平潭综合实验区商事主体登记申报系统 </title>
<!--新ui-->
<link href="<%=path%>/webpage/website/newzzhy/css/public.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />

<script src="<%=path%>/webpage/website/zzhy/js/jquery.min.js"></script>
<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/index/js/common.js"></script>
    <eve:resources
            loadres="apputil,artdialog"></eve:resources>
</head>

<body style="background: #f0f0f0;">
<jsp:include page="/webpage/website/newzzhy/head.jsp?currentNav=sy" />

<script type="text/javascript">
    $(document).ready(function(e) {
        $('.indRwmT').click(function(){
            var $ul = $(this).next('.indRwmC');
            $('.indRwm').find('.indRwmC').slideUp();
            if($ul.is(':visible')){
                $(this).next('.indRwmC').slideUp();
                $('.indRwmT').removeClass("indRembgOn");
            }else{
                $(this).next('.indRwmC').slideDown();
                $('.indRwmT').removeClass("indRembgOn")
                $(this).addClass("indRembgOn");
            }
        });

        ad123();
    });

    function submitOpinion(){
        var OPINION_SJHM=$("input[name='OPINION_SJHM']").val();
        var OPINION_CONTENT=$("input[name='OPINION_CONTENT']").val();
        var OPINION_USERNAME=$("input[name='OPINION_USERNAME']").val();
        if(OPINION_SJHM==""){
            window.top.location.href="${pageContext.request.contextPath}/userInfoController/mztLogin.do";
        }else if(""==OPINION_CONTENT){
            parent.art.dialog({
                content: "请输入意见内容",
                icon:"error",
                time:30,
                ok: function () {
                    AppUtil.closeLayer();
                }
            });
        }else{
            $.post("exeDataController/saveCommercialOpinion.do",{
                    OPINION_USERNAME:OPINION_USERNAME,
                    OPINION_SJHM:OPINION_SJHM,
                    OPINION_CONTENT:OPINION_CONTENT
                },
                function(responseText, status, xhr) {
                    var resultJson = $.parseJSON(responseText);
                    if(resultJson.success){//成功
                        parent.art.dialog({
                            content: "提交成功",
                            icon:"succeed",
                            time:30,
                            ok: function () {
                                $("input[name='OPINION_CONTENT']").val("");
                                AppUtil.closeLayer();
                            }
                        });
                    } else {//失败
                    }
                }
            );
        }
    }
</script>
<div showtime="10" endtime="2099-11-25 23:59:59"
     starttime="2016-03-21 23:59:59" class="ad_float eve_ad" style="z-index:999;">
    <div class="eve_ad_con" style="padding:10px;background:#0b6fa2;color:#fff;opacity:0.85"  >
        <div style="height:210px;width:410px;font-size: 12px;">
            <span style="font-size: 18px;text-align: center;margin-left:58px;">企业开办全程网上办身份实名认证公告</span><br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自2019年7月22日起，平潭综合实验区将在内资企业开办（包括但不限于<br>
            企业登记、公章刻制、社保登记等）环节推行全程无纸化网上办理。办理企业<br>
            开办业务时，需在申报材料网上预审通过后，通过下载“闽政通APP”，在<br>
            APP依次填写姓名、身份证号、实名登记手机号、密码等信息完成用户注册<br>
            ，上传身份证正反两面，采用远程身份认证方式（采用全国公安数据库<br>
            +人脸核身+活体检测+视频录制）+电子签名（章）完成本人身份实名认证。<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验证过程中如遇到问题，可咨询：0591-12345，0591-86169725。“闽政通APP<br>
            二维码下载”和“身份实名认证操作手册”可点击以下链接下载查看：<br>
            <a target="_blank" style="color: red" href="<%=path%>/DownLoadServlet?fileId=2c90b38a6c3c2978016c3cc0be3d2bc3">附件一、闽政通APP二维码</a><br>
            <a target="_blank" style="color: red"  href="<%=path%>/DownLoadServlet?fileId=2c90b38a6c3c2978016c3cc0bda12bc1">附件二、企业开办全程网上办身份实名认证操作流程</a><br>

            <br>
        </div>
    </div>
    <a target="_blank" href="javascript:void(0);" class="eve_ad_close" style="color:#313131">X关闭</a>
</div>
<div class=" eui-main" style="width:1300px">
  <div class="process_content">
    <div class="slideTxtBox">
      <div class="hd">
        <ul>          
          <li>内资企业</li>
          <li>外资企业</li>
          <li>个人独资</li>
          <li>个体商户</li>
        </ul>
      </div>
      <div class="bd">
        <div style="position:relative">
          <a style="position:absolute; left:708px; top:95px;width:137px;height:92px; display:block" target="_blank" href="<%=path%>/webpage/website/zzhy/dzhy.jsp"></a>
          <img src="<%=path%>/webpage/website/zzhy/images/process4.png" />
        </div>
        <div style="position:relative">
          <a style="position:absolute; left:648px; top:95px;width:197px;height:92px; display:block" target="_blank" href="<%=path%>/webpage/website/zzhy/dzhy.jsp"></a>
          <img src="<%=path%>/webpage/website/zzhy/images/process1.png" />
        </div> 
		<div style="position:relative">
          <a style="position:absolute; left:578px; top:95px;width:208px;height:92px; display:block" target="_blank" href="<%=path%>/webpage/website/zzhy/dzhy.jsp"></a>
          <img src="<%=path%>/webpage/website/zzhy/images/process2.png" />
        </div>
        <div style="position:relative">
          <a style="position:absolute; left:582px; top:95px;width:206px;height:92px; display:block" target="_blank" href="<%=path%>/webpage/website/zzhy/dzhy.jsp"></a>
          <img src="<%=path%>/webpage/website/zzhy/images/process3.png" />
        </div>
      </div>
      <script type="text/javascript">jQuery(".slideTxtBox").slide(); </script> 
    </div>
  </div>
</div>
<div class="content" style="margin-top:20px;">
<div style="float:left;	width:990px;margin-right:10px;overflow:hidden;">
	<a href="<%=path%>/webSiteController.do?zzhywssb" style="margin-bottom:15px; float:left;"><img src="<%=path%>/webpage/website/zzhy/images/2021031701.png" style=" width:990px; height:130px;" /></a>
    <a href="<%=path%>/webSiteController.do?wssbmp" style="margin-bottom:15px; float:left;"><img src="<%=path%>/webpage/website/zzhy/images/2021031702.png" style=" width:990px; height:130px;" /></a>
    <a href="<%=path%>/webSiteController.do?bgzx" style="margin-bottom:15px; float:left;"><img src="<%=path%>/webpage/website/zzhy/images/2021041202.png" style=" width:990px; height:130px;" /></a> 
    <div class="content_left">
    <div class="titlebg"><span>问题解答</span><a class="more" target="_blank" href="<%=path%>/contentController/list.do?moduleId=304">更多>></a></div>
    <div class="questionlist"  style="height:214px;">
      <ul>  
		<e:for filterid="304" end="2" var="efor" dsid="2">
			<li>
				<div class="bit"></div>
				<div class="answer">
					<a target="_blank" href="contentController/view.do?contentId=${efor.tid}">
					<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="30" ></e:sub>
				</a>
				</div>
			  <div class="question">
			  <e:obj filterid="${efor.itemId}" var="eobj" dsid="4">
				<c:if test="${empty eobj.content_text}">暂无解答内容。</c:if>
                <c:if test="${not empty eobj.content_text}">
                  <e:sub str="${eobj.content_text}" endindex="100" ></e:sub>
                </c:if>
			  </e:obj>
			  </div>
			</li>
		</e:for>		
      </ul>
    </div>
  </div>
 </div>
  <div class="content_right">
    <div class="titlebg1"><span>资料下载</span><a class="more"  target="_blank" href="<%=path%>/contentController/list.do?moduleId=303">更多>></a></div>
    <div class="datalist" style="height:179px;">
      <ul>
		<e:for filterid="303" end="6" var="efor" dsid="2">
			<li>
				<a target="_blank" href="contentController/view.do?contentId=${efor.tid}"><span></span>
				<e:sub objdate="${efor.itemReldate}" timeout="2"   str="${efor.itemTitle}" endindex="13" ></e:sub>
				</a>
			</li>
		</e:for>
      </ul>
    </div>
      <div class="titlebg1"><span>意见栏</span></div>
      <div class="datalist" style="height:397px;">
          <form action="exeDataController/saveCommercialOpinion.do?" id="opinion" method="post" class="find">
              <input name="OPINION_USERNAME" type="hidden" value="${sessionScope.curLoginMember.YHMC}">
              <input name="OPINION_SJHM" type="hidden" value="${sessionScope.curLoginMember.SJHM}">
              <input name="OPINION_CONTENT" type="text"   placeholder="请输入意见" class="inputtxt" />
              <input name="" type="button" class="inputbtn  validate[required]"  onclick="submitOpinion()" value="提  交" />
          </form>
      </div>
  </div>
</div>
    <jsp:include page="/webpage/website/newzzhy/foot.jsp" />
</body>
</html>
