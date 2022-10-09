<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>

	<link type="text/css"
		  href="plug-in/easyui-1.4/themes/bootstrap/easyui.css" rel="stylesheet">
	<link type="text/css"
		  href="plug-in/easyui-1.4/themes/icon.css" rel="stylesheet">
	<link type="text/css" href="plug-in/eveflowdesign-1.0/css/design.css"
		  rel="stylesheet">
	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<script type="text/javascript"
			src="plug-in/jquery2/jquery.min.js"></script>
	<script type="text/javascript"
			src="plug-in/easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript"
			src="plug-in/easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script language="JavaScript" for="SEDocAx" event="PrintEnd(lRes)">      //捕捉工具条上打印按钮的触发事件
//    if(lRes == 0){
//        alert("打印成功！");
//    }else{
//        alert("打印不成功！")
//    };
	</script>
	<script language="JavaScript" for="SEDocAx" event="HPChanged(x,y,page)">      //鼠标点击事件
    zb.value = 'X坐标：'+x+'，Y坐标：'+y+'，页面：'+page;
	</script>
	<script type="text/javascript">
        $(function () {
            var url="${filePath}";

            url=HTMLDecode(url);

            //var sealURL = "<%=basePath%>/"+url;
            var sealURL = "${_file_Server }"+url;
            SEDocAx.loadSEDocument(sealURL);
        });
        function HTMLDecode(text) {
            var temp = document.createElement("div");
            temp.innerHTML = text;
            var output = temp.innerText || temp.textContent;
            temp = null;
            return output;
        }
        function PrintOut(){
            //var printer = prompt("输入打印机名称：","PDFCreator");
            //var result =  SEDocAx.PrintOut("\\\\192.168.1.66\\\HP LaserJet P1008 (副本 1)",1,1,SEDocAx.PageCount,"");   //以指定打印机打印
            var result =  SEDocAx.PrintOut("",1,1,SEDocAx.PageCount,"");   //以系统默认打印机打印
            //var result =  SEDocAx.PrintOutA("");         //弹出打印窗口由用户自己选择打印机
            //alert(result);
            if (result==-1){
                alert("打印失败");
            }
            else {
                //alert("打印了:" + result + "页");
            }
        }
        function PrintOut1(){

            //var printer = prompt("输入打印机名称：","PDFCreator");
            //var result =  SEDocAx.PrintOut("\\\\192.168.1.66\\\HP LaserJet P1008 (副本 1)",1,1,SEDocAx.PageCount,"");   //以指定打印机打印
            var result =  SEDocAx.PrintOut("PDFCreator",1,1,SEDocAx.PageCount,"");
            //var result =  SEDocAx.PrintOut("",1,1,SEDocAx.PageCount,"");   //以系统默认打印机打印
            //var result =  SEDocAx.PrintOutA("");         //弹出打印窗口由用户自己选择打印机
            //alert(result);
            if (result==-1){
                alert("打印失败");
            }
            else {
                alert("打印了:" + result + "页");
            }
        }

        function PrintOut2(){

            //var printer = prompt("输入打印机名称：","PDFCreator");
            //var result =  SEDocAx.PrintOut("\\\\192.168.1.66\\\HP LaserJet P1008 (副本 1)",1,1,SEDocAx.PageCount,"");   //以指定打印机打印
            //var result =  SEDocAx.PrintOut("PDFCreator",1,1,SEDocAx.PageCount,"");
            //var result =  SEDocAx.PrintOut("",1,1,SEDocAx.PageCount,"");   //以系统默认打印机打印
            var result =  SEDocAx.PrintOutA("");         //弹出打印窗口由用户自己选择打印机
            //alert(result);
            if (result==-1){
                alert("打印失败");
            }
            else {
            }
        }

        function SearchStr(){

            var contentStr = prompt("输入要搜索的内容：","");
            var result =  SEDocAx.SearchStr(contentStr,0,0);
            if (result==0){
                alert("查找失败");
            }
            if (result==1){
                alert("查找成功");
            }
        }
        function GetSourceFile(){
            var result =  SEDocAx.GetSourceFile('D:\\1.pdf');
            if (result==0){
                alert("成功");
            }
        }

	</script>
</head>

<body >
<div data-options="region:'north',collapsible:false" style="height: 42px;" split="true" border="false" >
	<div class="right-con">
		<div class="e-toolbar">
			<div class="e-toolbar-ct">
				<div class="e-toolbar-overflow">
					<div style="text-align:center;">
						<!-- 遍历可控操作的按钮 -->
						<c:if test="${noprint!=1}">
							<button class="eflowbutton"
									onclick="PrintOut();">
								打印
							</button>
						</c:if>
						<%--<button class="eflowbutton"--%>
								<%--onclick="PrintOut2();">--%>
							<%--自主打印--%>
						<%--</button>--%>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<object width="100%" height="100%" id="SEDocAx" classid="clsid:4B27668B-151E-4B44-84A1-B1CB0A95072A">
</object>
</body>

