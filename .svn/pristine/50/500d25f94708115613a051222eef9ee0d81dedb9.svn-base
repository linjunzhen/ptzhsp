<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/webpage/weixin/cominclude.jsp"%>
  <head>
    <title>排队查询</title>
    <meta charset="utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		
		<link rel="stylesheet" href="<%=path%>/webpage/weixin/css/eui.css">
<!--     <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0"> -->
    <eve:resources
    loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
    <script type="text/javascript"
    src="<%=path%>/plug-in/jquery2/jquery.min.js"></script>
    
  </head>
  
   <body style="background: #f2f2f2;">
    <div id="section_container">
      <div class="card form-square" >
          <label class="label-right nomargin noborder nopadding">
              <input type="text" class="eveinput" name="lineNo" placeholder="请输入排队号" id="lineNo"/>
          </label>
          <label class="label-left nomargin noborder"><button class="block btnbgcolor" style="border-radius: 0;" onclick="loadSearchData(event);"></button></label>
      </div>
<!-- 		<div class="eui-ywcxCon"> -->
<!-- 			<div class="eui-ywcxSearch"> -->
<!-- 				<div class="eui-inputBox"> -->
<!-- 					<input type="text" placeholder="请输入排队号"  name="lineNo" id="lineNo"/> -->
<!-- 					<i onclick='loadSearchData(event);'></i> -->
<!-- 				</div> -->
<!-- 			</div> -->
		      <section id="form_section" data-role="section" class="active">
		        <header>
		            <div>
		            </div>
		        </header>
		        <!--做这个把下面那个top改成50px-->
		        <article data-role="article" id="normal_article" data-scroll="verticle" class="active" style="top:56px;bottom:0px; background:#ebebeb;">
		          <div class="scroller">
		            <div class="margin10"> 
		             
		              <div class="form-group" >
			            <div class="eui-ywcxInfoBox" id="listItemContain">
			            </div>
		              </div>
		            <div>
		          </div>  
		        </article>
		      </section>
		</div>
    <!--- third -->
    <script src="<%=path%>/webpage/weixin/agile-lite/third/zepto/zepto.min.js"></script>
    <script src="<%=path%>/webpage/weixin/agile-lite/third/iscroll/iscroll-probe.js"></script>
    <script src="<%=path%>/webpage/weixin/agile-lite/third/arttemplate/template-native.js"></script>
    <!-- agile -->
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/agile/js/agile.js"></script>   
    <!--- bridge -->
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/exmobi.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/bridge/agile.exmobi.js"></script>
    <!-- app -->
    <script src="<%=path%>/webpage/weixin/agile-lite/component/timepicker/agile.timepicker.js"></script> 
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/component/extend.js"></script>
    <script type="text/javascript" src="<%=path%>/webpage/weixin/agile-lite/app/js/app.js"></script>
    <script type="text/javascript">
        var refresh=A.Scroll('#normal_article');
         
        //var url="serviceItemController/bsznDetail.do?itemCode="+itemList[i].ITEM_CODE;
        var selectObj='${shenqiObj}';
        function selectType(obj){
            //$("ul[class='slide downlist']").css("display","none");
            var dis=document.getElementById(obj).style.display;
            if(dis=='block'){
                document.getElementById("zhuti").style.display="none";
                document.getElementById("rensheng").style.display="none";
                document.getElementById("renqun").style.display="none";
                document.getElementById(obj).style.display="none";
            }else{
                document.getElementById("zhuti").style.display="none";
                document.getElementById("rensheng").style.display="none";
                document.getElementById("renqun").style.display="none";
                document.getElementById(obj).style.display="block";
            }
        }
        function limouseOut(obj){
            document.getElementById(obj).style.display="none";
        }
        function searchG2(itemList){
            $("#listItemContain").html("");
            var newhtml = "<ul class=\"listitem\">";
            for(var i=0; i<itemList.length; i++){
                newhtml+="<li onclick='onBind(\""+itemList[i].recordId+"\",\""+itemList[i].businessName+"\",\""+itemList[i].openId+"\")'><div class=\"text\"><div class=\"eveoverflow\"></div><small data-col=\"2\">";
                newhtml+="<span>业务：<font class=\"fontcolor1\">"+itemList[i].businessName+"</font></span><br>";
                newhtml+="<span>排队号：<font class=\"fontcolor1\">"+itemList[i].lineNo+"</font></span><br>";
                newhtml+="<span>当前位置：<font class=\"fontcolor1\">"+itemList[i].num+"</font></span>";
                if(itemList[i]['openId']!=null&&itemList[i]['openId']!="null"&&itemList[i]['openId']!=''){
                    newhtml+="<span style='float:right;'><font class=\"fontcolor1\">已绑定</font></span>";
                }else{
                    newhtml+="<span style='float:right;'><font class=\"fontcolor1\">立即绑定</font></span>";
                }
                newhtml+="</small></div></li>";
            }
             newhtml += "</ul>";
            $("#listItemContain").html(newhtml);
            refresh.refresh();
        }
        function searchG(itemList){
            $("#listItemContain").html("");
            var newhtml = "<div class=\"eui-ywcxInfo\">";
            for(var i=0; i<itemList.length; i++){
            	newhtml+="<div class=\"eui-ywcxTop\">";
            	newhtml+="<p class=\"eui-ywcxbh\">"+itemList[i].lineNo+"</p>";
            	newhtml+="<p class=\"eui-ywcxlx\">业务：<span>"+itemList[i].businessName+"</span></p>";
            	newhtml+="<p class=\"eui-ywcxdqwz\">当前位置</p>";
            	newhtml+="<p class=\"eui-dqwznum\">"+itemList[i].num+"</p>";
            	newhtml+="</div>";
            	newhtml+="<div class=\"eui-ywcxBtm\">";
                if(itemList[i]['openId']!=null&&itemList[i]['openId']!="null"&&itemList[i]['openId']!=''){
                    newhtml+="<span><font class=\"fontcolor1\">已绑定</font></span>";
                }else{
                    newhtml+="<span><font class=\"fontcolor1\"><li onclick='onBind(\""+itemList[i].recordId+"\",\""+itemList[i].businessName+"\",\""+itemList[i].openId+
                "\")'>立即绑定</font></span>";
                }
            	newhtml+="</div>";
//                 newhtml+="<li onclick='onBind(\""+itemList[i].recordId+"\",\""+itemList[i].businessName+"\",\""+itemList[i].openId+
//                 "\")'><div class=\"text\"><div class=\"eveoverflow\"></div><small data-col=\"2\">";
            	newhtml+="</div>";
            }
            newhtml+="</div>";
            $("#listItemContain").html(newhtml);
            refresh.refresh();
        }
        
        function loadSearchData(){
            var lineNo = $("#lineNo").val();
            lineNo = lineNo.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
            if(lineNo==''){
               alert('排队号不能为空!');
               return;
            }
                                
//             var reg = /^[A-z][0-9]*$/;
//             if(!reg.test(lineNo)){
//                alert('请输入正确排队号!');
//                return;
//             }
            
            $.post("callController.do?busWaitNumDatagrid", {
                    lineNo:lineNo},
                function(responseText, status, xhr) {
                   var resultJson = $.parseJSON(responseText);
                   if(resultJson!=null&&resultJson.length>0){
                       searchG(resultJson);
                   }else{
                       alert('无排号记录!');
                       $("#listItemContain").html("");
                   }
                }   
            );

        }
        function addSelect(typeid,obj){
            var busTypeId = $("#busTypeId").val();
            if(busTypeId!=''&&busTypeId!=null&&busTypeId.indexOf(typeid)==-1){
                $("#busTypeId").val(typeid);
                $("#"+typeid).addClass("select");
                $("#"+busTypeId).removeClass("select");
            }else{
                $("#busTypeId").val(typeid);
                $("#"+typeid).addClass("select");
            }
            document.getElementById(obj).style.display="none";
            loadSearchData();
       }
       
       /**
        * 排队绑定微信OPENID
        * @param recordId 排队号 ID
        * @param businessName 业务名称
        */
       function onBind(recordId,businessName,openId){
           if (openId == ""||openId=="null") {
               if (confirm("当前业务[" + businessName + "]是否要绑定微信公众号消息通知?")) {
                   var code = getQueryString("code");
                   $.post("callController.do?bindOpenId", {recordId: recordId, code: code},
                       function (responseText, status, xhr) {
                           var resultJson = $.parseJSON(responseText);
                           //alert(responseText);
                           if (resultJson['success']) {
                               alert('绑定成功,请留意微信公众号消息通知!');
                               loadSearchData();
                           } else {
                               alert('系统繁忙,请稍候尝试!');
                           }
                       }
                   );
               }
           }
       }
       
      /**
	   * 根据name获取url上的参数
	   * @param name
	   * @returns {any}
	   */
       function getQueryString(name) {
		    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		    let r = window.location.search.substr(1).match(reg);
		    if(r!=null)
		      return  decodeURI(r[2]);
		    return null;
        }
       
       function sendMessage(){
            $.post("callController.do?sendMessage", {openId:1},
                function(responseText, status, xhr) {
                   console.log(JSON.parse(responseText));
                   //var resultJson = $.parseJSON(responseText);
                   //console.log(resultJson);
                }   
            );
       }
    </script>
  </body>
</html>