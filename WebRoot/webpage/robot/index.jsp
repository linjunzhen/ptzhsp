<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <!-- <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> -->
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>办事指南查询</title>
<!--     <link rel="stylesheet" href="css/mui.min.css"/> -->
    <link rel="stylesheet" href="css/index.css"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
     <script type="text/javascript" src="js/mui.min.js"></script>
    <script type="text/javascript" src="js/mobileUtil.js"></script>
</head>
<body>
    <div class="eui-ken-body-bszn">
        <div class="eui-ken-body-bszncx-logo">
            <img src="images/bszncx.png"/>
        </div>
        <!--办事指南查询-->
        <div class="eui-ken-bszncx eui-ken-bszncx2">
            <div class="eui-ken-bszncx-input">
                <input type="text" name="ITEM_NAME" id="ITEM_NAME" value="" maxlength="50" style="color:#ffffff;"/>
                <input type="hidden" name="ITEM_CODE" id="ITEM_CODE" value=""/>
                <!-- <a>查 询</a> -->
            </div>
            <div class="eui-ken-bszncx-main" id="divUL" style="display:none;">
                <ul id="UL_ITEM_NAME">
                </ul>
            </div>
        </div>
        <!--办事指南查询-->
    </div>
    <script>

	   var oItemName = document.getElementById('ITEM_NAME');
       var cpLock = false;
       oItemName.addEventListener('keydown', function(e){
           if(e.keyCode == 13){
               onSearch(oItemName.value);
           }
       }, false);
       
       oItemName.addEventListener('compositionstart', function (e) {
           cpLock = true;
       }, false);
       
       oItemName.addEventListener('compositionend', function (e) {
           cpLock = false;
           onSearch(oItemName.value);
       }, false); 
       
       oItemName.addEventListener('input', function (e) {
           if (!cpLock) {
               onSearch(oItemName.value);
           }
       }, false);
       
       /**
        * 根据关键字搜索
        * @param keyWord 关键字
        **/
       function onSearch(keyWord){
           var oUL_ITEM_NAME = document.getElementById("UL_ITEM_NAME");
           while(oUL_ITEM_NAME.hasChildNodes()){
               oUL_ITEM_NAME.removeChild(oUL_ITEM_NAME.firstChild);
           }
          
           keyWord = keyWord.replace(/(^\s*)|(\s*$)/g, "");//去左右空格
           if(keyWord!=''){
               mobileUtil.muiAjaxForData({
	                mask : true,
	                //url : '${ctx}/declare/findItemForRobot',
                    url : '<%=path%>/declare.do?findItemForRobot',
	                data : {
	                    ITEM_NAME:keyWord
	                },
	                timeout:30000,
	                callback : function(result) {
	                    result = result||{};
	                    var rows = result['rows']||[];
	                    if(rows!=null&&rows.length>0){
	                       $('#divUL').show();
	                       var j = 0;
	                       for(var i = 0;i<rows.length;i++){
	                           j++;
	                              $('#UL_ITEM_NAME').append('<li>'+rows[i]['ITEM_NAME'].replace(keyWord,'<span>'+keyWord+'</span>')+'</li>');
	                       }
	                       
	                       if(j>0){
	                              $("#UL_ITEM_NAME li").click(function() {
	                                  $('#divUL').hide();
	                                  $('#ITEM_NAME').val(rows[$(this).index()]['ITEM_NAME']);
	                                  //window.location.href='${ctx}/app/robot/detail.jsp?ITEM_CODE='+rows[$(this).index()]['ITEM_CODE']+'&ITEM_NAME='+rows[$(this).index()]['ITEM_NAME'];
                                      window.location.href='<%=path%>/webpage/robot/detail.jsp?ITEM_CODE='+rows[$(this).index()]['ITEM_CODE'];
                                  });
	                          }
	                    }
	                }
	            });
           }
       }

    </script>
</body>
</html>