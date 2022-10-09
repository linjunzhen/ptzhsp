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
    
    <script type="text/javascript" src="<%=path%>/webpage/bsdt/applyform/projectSign/js/reader-aes.js" ></script>
    <script type="text/javascript" src="<%=path%>/webpage/bsdt/applyform/projectSign/js/crypto-js.js" ></script>
    <link rel="stylesheet" href="<%=path%>/plug-in/artdialog-4.1.7/skins/default.css">
</head>

<SCRIPT LANGUAGE="javascript">
	var fileName="";
    function uploadFile(){
    	var file = $("#file").val();
    	fileName = file;
        var index1=file.lastIndexOf(".");
        var index2=file.length;
        var suffix=file.substring(index1 + 1, index2);//后缀名
        if (file == "" || file.length == 0) {
            alert("请选择文件!");
            return false;
        }else if(suffix.toLowerCase() !='pdf' && suffix.toLowerCase() !='ofd'){
            alert("请选择PDF或OFD文件");
            return false;
        }
        var f = $("#file")[0].files[0];
    	console.log(f);
        var base64;
        var reader = new FileReader();
        reader.readAsDataURL(f);
        reader.onload = function (e) {
            base64 = this.result;
            $.ajax({
                url: "/projectSignController/uploadFile.do",
                type: 'POST',
                data: {
                	inFileType:suffix.toUpperCase(),
                	inFileDataBase64:base64
                },
                success: function (result) {
                    var dataObj = JSON.parse(result);
                    if(dataObj.code == 200 && dataObj.data.resultCode ==0 ){
                        document.getElementById("fileId").value = dataObj.data.fileId;
                        $("#sealurl").html(dataObj.data.signUrl);
                    }else {
                        alert("上传失败");
                    }
                    $("#reResult").html(JSON.stringify(dataObj));
                    show(dataObj.data.signUrl);
                },
                error: function(result) {
                    alert(JSON.stringify(result));
                }
            });
        };
    }
    
    function downloadFile(){
    	var fileId = $("#fileId").val();
        if(fileId == '' || fileId == null || fileId == 'undefiend') {
            alert("请先上传文件");
            return;
        }
        window.open("/projectSignController/downloadFile.do?fileId="+fileId);
    }
    
    function show(url) {
        if (url == '' || url == null || url == 'undefiend') {
        	alert("请先上传文件");
            return;
        }
        var inserthtml =
            '<iframe style="width: 100%;height: 47rem" /allowfullscreen="allowfullscreen" id="view" src="' + url +
            '"></iframe>';
        $("#swtcenter").html("");
        $("#swtcenter").append(inserthtml);
    };
    
    window.addEventListener("message", function (e) {
        console.log("addEventListener message" + JSON.stringify(e));
        var data = JSON.parse(e['data']);
        var date = data.date;
        console.log("data:" + JSON.stringify(data));
        if (data.action == "open") {
            if (data.check) {
                date = suwellAes.getAesNotCookieString(date, "suwellreader2020", "suwellreader2020");
            }
            post(["open", date])
        }
    });

    function post(message) {
        document.getElementById('view').contentWindow.postMessage(message, "*")
    }
    
    function DealFileExport(){
    	var fileId = $("#fileId").val();
        if(fileId == '' || fileId == null || fileId == 'undefiend') {
            alert("请先上传文件");
            return;
        }
        $.post("/projectSignController/uploadProjectSignFile.do",{
        	fileId:fileId,
            attachKey:"${attachKey}",
            uploadUserId:"${uploadUserId}",
            uploadUserName:"${uploadUserName}",
            busTableName:"${busTableName}",
			name:fileName.slice(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf("."))
		},function(responseText, status, xhr) {
            if (responseText) {
                var responseJson = $.parseJSON(responseText)
                art.dialog.data("resultJsonInfo", responseJson);
                AppUtil.closeLayer();
            }
       	});
    }

</script>
    <input type="hidden" id="fileId" value="">
    <input type="hidden" id="sealurl" value="">
    <input type="hidden" id="fileName" value="">
    <input type="hidden" id="reResult" value="">
    <input type="hidden" id="file1" value="">
    <input type="file" id="file" value="选择文件">
    <input type="button" value="上传文件" onclick="uploadFile()">
    <input type="button" value="下载文件" onclick="downloadFile()">
    <input type="button" value="保存" onclick="DealFileExport()">
    <body>
    <div>
         <div id="swtcenterCard">
             <div>
                 <div id="swtcenter"></div>
                 <input type="hidden" id="fileId" name="fileId" value="">
                 <input type="hidden" id="sealurl" name="sealurl" value="">
                 <input type="hidden" id="reResult" name="reResult" value="">
             </div>
         </div>
     	</div>
    </body>
</html>

