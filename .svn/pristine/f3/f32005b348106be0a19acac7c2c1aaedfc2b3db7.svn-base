<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base target="_self">
    <title>电子签章页面</title>
    <meta content="JavaScript" name="vs_defaultClientScript">
    <meta content="http://schemas.microsoft.com/intellisense/ie5" name="vs_targetSchema">
    <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
<%--    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>--%>
    <script type="text/javascript" src="<%=path%>/plug-in/jquery2/jquery.min.js" ></script>
    <script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default" ></script>
    <script type="text/javascript" src="<%=path%>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js" ></script>
    <script type="text/javascript" src="<%=path%>/plug-in/eveutil-1.0/AppUtil.js" ></script>
    <link rel="stylesheet" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css">
</head>

<script language="JavaScript" for="control" event="DealStampEnd">    //捕捉盖章成功的事件
alert("盖章成功！");
</script>

<SCRIPT LANGUAGE="javascript">


    //盖章；
    function DealStamp(){
        var bool=control.IsDealStamp();
        if(bool){
            //control.SetSealPosOnBaseMap(0);    //设置印章盖在文档下方，0为印章在下方，1为印章在上方
            //control.SetAddDate(3);         //设置在盖章后面增加日期
            //var a = control.SetTssStartupIpPort(true, '218.66.59.250', 80);   //设置盖章时启用时间戳服务，ture为启用，false为关闭
            //alert(a);
            control.DealStamp();
        }
        else{
            alert("不能进行盖章,请先导入PDF文件！");
        }
    }

    //指定位置盖章；
    function DealStamp2(){
        var bool=control.IsDealStamp();
        if(bool){
            var a = control.DealStampA("nPage=1|nX=187|nY=369|nM=0");     //在指定位置盖章，nPage为000时，表示在所有页面上的同一位置盖章
            alert(a);
        }
        else{
            alert("不能进行盖章,请先导入PDF文件！");
        }
    }

    //批量盖章；
    function DealLotStamp(){
        var bool=control.IsDealLotStamp();
        if(bool){
            control.DealLotStamp();
        }
        else{
            alert("不能进行批量盖章,请先导入PDF文件！");
        }
    }

    //签批；
    function DealHandSign(){
        var bool=control.IsDealHandSign();
        if(bool){
            control.DealHandSign();
        }
        else{
            alert("不能进行签批,请先导入PDF文件！");
        }
    }

    //撤销；
    function DealUnStamp(){
        //control.DealUnStamp();
        //control.DealUnStpFgpSign();    //用于DealSmpFgpSign该方法的撤消
        //control.PDFCommand(13);

        var bool=control.IsDealUnStamp();
        if(bool){
            control.DealUnStamp();
        }
        else{
            alert("请先进行盖章操作！");
        }

    }

    //抄送；
    function DealFileExportBlack(){
        var bool=control.IsDealFileExportBlack();
        if(bool){
            control.DealFileExportBlack("c:/test.bdc");
        }
        else{
            alert("请加载EDC文件！");
        }
    }

    //打印；
    function DealFilePrint(){
        var bool=control.IsDealFilePrint();
        if(bool){
            //control.DealFilePrint("false");    //设置默认为纸张大小打印方式
            console.log(1111)
            var result = control.DealFilePrint("");    //弹出打印窗口进行打印
        }
        else{
            alert("不能进行打印！");
        }
    }
    //以指定打印机打印
    function PrintOut(){
        var bool=control.IsDealFilePrint();
        if (bool)
        {
            var result = control.PrintOut("Foxit PDF Printer",1,1,control.GetMaxPage,"");   //以指定打印机打印
            if (result==-1){
                alert("打印失败");
            }
            else {
                alert("打印成功");
            }
        }
        else{
            alert("不能进行打印！");
        }
    }
    //以默认打印机打印
    function PrintOut1(){
        var bool=control.IsDealFilePrint();
        if (bool)
        {
            var result = control.PrintOut("",1,1,control.GetMaxPage,"");    //以默认打印机打印
            if (result==-1){
                alert("打印失败");
            }
            else {
                alert("打印成功");
            }
        }
        else{
            alert("不能进行打印！");
        }
    }
    //系统设置；
    function DealAdminOption(){
        control.DealAdminOption();
    }

    //印章管理；
    function DealAdminStamp(){
        control.DealAdminStamp();
    }

    //验证；
    function DealVerifyStamp(){
        var bool=control.IsDealVerifyStamp();
        if(bool){
            control.DealVerifyStamp();
        }
        else{
            alert("验证失败！");
        }

    }

    //加载文件；
    function LoadPDFDocument(){
        //control.SetOutMode(1);      //设置生成的文件为pdf文件
        //control.LoadDocument('http://202.109.226.172:8081/epoint-smzwfw-web-test/frame/pages/basic/attach/attachdown.html?attachGuid=be0ee530-e3dd-41c0-b692-9cdf6fc02ced');
        control.LoadPDFDocument('${fileName}');
        //control.SetToolButtonVis(-1);    //屏蔽工具条
        //control.ShowToolBar(true);     //打开福昕pdf工具条
        //control.SetEditPdf(true);    //设置是否可以编辑pdf文件
        control.SetPrintable(true);  //设置是否允许打印pdf文件
        //var page=control.GetMaxPage;     //获取pdf文档页数
        //alert(control.WordToPDFAndLoad('F:/1.doc','',3));
        //control.ImageToPDFAndLoad('d:/1.png','ORIEN=1|CENTER=0|ROTATE=1|ROOM=0|PAPERSIZE=0|NX=291|NY=114',3);
        //control.ExcelToPDFAndLoad('http://127.0.0.1:8080/fh/FileDownloadServlet.do?type=2','','ORIEN=1|GLINE=0|HEARDING=0|PAPERSIZE=9',3);
        //alert(control.LoadPDFDocument('file:///D:\\1.pdf'));
        //control.WpsToPDFAndLoad('d:/1.pdf','',3);
        //control.ExcelToPDFAndLoad('d:\\1.xlsx','','ORIEN=1|GLINE=0|HEARDING=0|PAPERSIZE=9',3);
        control.PDFCommand(13);      //以适合宽方式展示文件
        //control.SetSealToolVis(true);   //设置在工具条上显示盖章按钮
        //control.SetSealToolButtonVis(3);
        //control.WordToPDFAndLoad('ftp://soft:soft@192.168.5.50/1.doc','',3);
    }

    //加载EDC文件；
    function LoadEDCDocument(){
        control.LoadEDCDocument("d:/2.edc");
        //control.LoadEDCDocument("http://120.35.29.217/CatalogManage/GetLicenseFile.ashx?typeID=6E90A7855220215FE040007F010043A8&fileID=350105154591085&selCode=NY4R3PJA8");
        control.PDFCommand(13);    //以适合高方式展示文件
    }

    //摘录；
    function DealCento(){
        control.DealCento();
    }

    //关闭文件；
    function CloseDocument(){
        control.CloseDocument();
    }
	var fileName="";
    //打开文件；
    function OpenFileDlg(){
        //control.SetToolButtonVis(-1);    //屏蔽工具条
        //control.SetOutMode(1);      //设置生成的文件为pdf文件
        var fileurl = control.OpenFileDlgA();
			// 获取最后一个斜杠 '/'
		var index = fileurl.lastIndexOf('\\');  
		if(index==-1){
			index = fileurl.lastIndexOf('/');  
		}
		var result = fileurl.substring(index+1,fileurl.length); 
		fileName = result.substring(0, result.lastIndexOf("."));
        //control.OpenFileDlg();
        setTimeout("control.PDFCommand(13)",500);
        control.SetPrintable(true);
        //control.SetPrintable(true);    //设置是否允许打印PDF文件
        //control.SetEditPdf(false);    //设置是否可以编辑pdf文件
        //control.PDFCommand(13);      //以适合宽方式展示文件
    }

    //自动批注
    function DealHandSignA(){
        var bool=control.IsDealStamp();
        if(bool){
            control.SelfMotionPostil('TEXT=我要测试的文件&AREAPAGE=1|200|200&FACENAME=宋体&SIZE=16&COLOR=0;0;0');
        }
        else{
            alert("不能进行签批,请先导入PDF文件！");
        }
    }

    //输出EDC文件并输出B64编码；
    function DealFileExport(){
        //control.PutIsPrint(true);    //设置输出的edc文件是否能打印，true为可打印
        //control.PutIsExportBlack(true);    //设置输出的edc文件是否能抄送，true为可抄送
        //control.PutIsCento(true);    //设置输出的edc文件是否能摘录，true为可摘录
        control.SetDocSignType(1);    //edc文件输出方式,0默认,1签名,2加密
        control.DelSaveDlg(1);        //保存时不弹出保存成功的提示
        var bool=control.IsDealUnStamp();
        if(bool){
            control.DealFileExport("000");
            $.post("uploadKtStampBase64File.do",{
                base64Code:control.GetEDCB64(),
                attachKey:"${attachKey}",
                uploadUserId:"${uploadUserId}",
                uploadUserName:"${uploadUserName}",
                busTableName:"${busTableName}",
				name:fileName},
                function(responseText1, status, xhr) {
                    if (responseText1) {
                        var responseJson = $.parseJSON(responseText1)
                        art.dialog.data("resultJsonInfo", responseJson);
                        AppUtil.closeLayer();
                    }
            });
        }
        else{
            alert("请先盖章后，再保存！");
        }
    }

    function SendFile(){
        //FormPost.ClearAllFiles(true);     //清除表单数据，为true时会删除本地文件
        OAForm.submit();                    //上传文件
    }

    function GetPDF(){
        var bool = control.IsDealSavePdf();
        if(bool){
            var i = control.DealSavePdf("C:/test.pdf");    //保存PDF文件
            alert(i);
        }
        else{
            alert("不能提取PDF文件！");
        }
    }

    function SealNO(){
        var a = control.GetAllCount(0);        //获取edc文件中签章总个数（盖章+签批）
        var b = control.GetSealCount(0);       //获取edc文件中盖章总个数
        var c = control.GetHandSignCount(0);   //获取edc文件中签批总个数
        alert('签章总数为'+a+'个。'+'\n'+'电子印章总数为'+b+'个。'+'\n'+'签批总数为'+c+'个。');
    }


    function GoToPagePos(){
        control.GoToPdfPos(zbd);    //跳转至文件坐标点位置，参数为nPage,X,Y
    }

    function GetCurPDFLeftTopPos(){
        zbd = control.GetCurPDFLeftTopPos;    //获取至文档左上角坐标位置
        alert(zbd);
    }

</script>
    <input type="button" value="打开文件" onclick="OpenFileDlg()">
    <input type="button" value="加载文件" onclick="LoadPDFDocument()">
    <input type="button" value="盖章" onclick="DealStamp()">
    <input type="button" value="批量盖章" onclick="DealLotStamp()">
    <input type="button" value="撤销" onclick="DealUnStamp()">
    <input type="button" value="保存" onclick="DealFileExport()">
    <input type="button" value="打印" onclick="DealFilePrint()">
    <input type="button" value="系统设置" onclick="DealAdminOption()">
    <input type="button" value="印章管理" onclick="DealAdminStamp()">
    <OBJECT width="1000" height="100%" id="control" classid="clsid:5CAC43BD-DE74-49F9-9FD6-DDA6B5A15A9C" codebase="KTSEDocAxEx.ocx"></OBJECT>
    <OBJECT id="FormPost" classid="clsid:019A29FA-4B5D-4FE8-82D4-51C554285457" name="FormPost"></OBJECT>
    <body>
    <form id="OAForm" name="uptest" action="<%=basePath%>identifyMsgByDsjController/decodeSealStr.do" method="post" encType="multipart/form-data">
        <?IMPORT NAMESPACE = CUSTOM IMPLEMENTATION = "#FormPost" />
        <CUSTOM:KTSEDocAxExForm id="KTSEDocAxExForm"></CUSTOM:KTSEDocAxExForm>
<%--        <input type="button" value="加载文件" onclick="LoadPDFDocument()">--%>
<%--        <input type="button" value="加载EDC文件" onclick="LoadEDCDocument()">--%>
<%--        <input type="button" value="关闭文件" onclick="CloseDocument()">--%>
<%--        <input type="button" value="打印" onclick="DealFilePrint()">--%>
<%--        <input type="button" value="指定打印机" onclick="PrintOut()">--%>
<%--        <input type="button" value="默认打印机" onclick="PrintOut1()">--%>
<%--        <input type="button" value="抄送" onclick="DealFileExportBlack()">--%>
<%--        <input type="button" value="摘录" onclick="DealCento()">--%>
<%--        <input type="button" value="盖章" onclick="DealStamp()">--%>
<%--        <input type="button" value="指定位置盖章" onclick="DealStamp2()">--%>
<%--        <input type="button" value="多坐标点盖章" onclick="DealStamp3()">--%>
<%--        <input type="button" value="批量盖章" onclick="DealLotStamp()">--%>
<%--        <input type="button" value="签批" onclick="DealHandSign()">--%>
<%--        <input type="button" value="自动签批" onclick="DealHandSignA()">--%>
<%--        <input type="button" value="撤销" onclick="DealUnStamp()">--%>
<%--        <input type="button" value="验证" onclick="DealVerifyStamp()">--%>
<%--        <input type="button" value="系统设置" onclick="DealAdminOption()">--%>
<%--        <input type="button" value="印章管理" onclick="DealAdminStamp()">--%>
<%--        <input type="button" value="上传" onclick="SendFile()">--%>
<%--        <input type="button" value="获取印章个数" onclick="SealNO()">--%>
<%--        <input type="button" value="提取PDF文件" onclick="GetPDF()">--%>
<%--        <input type="button" value="获取左上角坐标点位置" onclick="GetCurPDFLeftTopPos()">--%>
<%--        <input type="button" value="跳转至坐标点位置" onclick="GoToPagePos()">--%>
    </form>
    </body>
</html>

