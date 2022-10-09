<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String uploadPath = request.getParameter("uploadPath");
			String attachKey = request.getParameter("attachKey");
			String uploadUserId = request.getParameter("uploadUserId");
			String uploadUserName = request.getParameter("uploadUserName");
			String matreClsm = request.getParameter("matreClsm");
			String winNo = request.getParameter("winNo");
			String takeNoWay = request.getParameter("takeNoWay");
			request.getParameter("lineName");

			request.setAttribute("matreClsm", matreClsm);
			request.setAttribute("uploadPath", uploadPath);
			request.setAttribute("attachKey", attachKey);
			request.setAttribute("uploadUserId", uploadUserId);
			request.setAttribute("uploadUserName", uploadUserName);
			request.setAttribute("winNo", winNo);
			request.setAttribute("takeNoWay", takeNoWay);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>
<title>VideoInputCtl</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/bsdt/ptwgform/scan/css/style.css">
    <script type="text/javascript">
        //var szDefaultDevice = "NewImage DocCam"; // 540
        var szDefaultDevice = "[1a3c:010d]"; // 525
        var previewPhotoStr = "";
    
        function release() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
    
            if (VideoInputCtl.IsDeviceOpened(nDeviceIndex)) {
                VideoInputCtl.CloseDevice(nDeviceIndex);
            }
        }
    
        function changedev1() {
            var obj = document.getElementById("sxtsx").value;
            if (obj == 0) {
                document.getElementById("sxtsx").value = 1;
            } else {
                document.getElementById("sxtsx").value = 0
            }
            opendevice();
        }
    
        function opendevice() {
            var nDeviceIndex = document.getElementById("sxtsx").value;
    
            if (!VideoInputCtl.IsDeviceOpened(nDeviceIndex))
                VideoInputCtl.OpenDevice(nDeviceIndex);
    
            var nFormatCount = VideoInputCtl.GetDeviceFormatCount(nDeviceIndex);
            //分辨率
            ResolutionList.options.length = 0;
            for (var i = 0; i < nFormatCount; i++) {
                var szFormatName = VideoInputCtl.GetDeviceFormatName(nDeviceIndex, i);
                ResolutionList.options.add(new Option(szFormatName, i));
            }
            //旋转角度
            selRotateList.options.length = 0;
            for (var i = 0; i < 4; i++) {
                var selRotate = 0 + 90 * i;
                selRotateList.options.add(new Option(selRotate, selRotate));
            }
    
            VideoInputCtl.StartPlayDevice(nDeviceIndex);
    
            ExposureList.options.length = 0;
            for (var i = -5; i <= -1; i++) {
                ExposureList.options.add(new Option(i, i));
            }
        }
    
        function changeresolution() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            var nFormatIndex = ResolutionList.selectedIndex;
            VideoInputCtl.SetDeviceFormatIndex(nDeviceIndex, nFormatIndex);
        }
    
        function grabimage() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            var path = "C:\\TEMP\\" + get_name() + ".jpg";
            VideoInputCtl.GrabToFile(path);
            var szBase64 = VideoInputCtl.GrabToBase64(".jpg");
            //预览
            previewPhotoStr = previewPhotoStr
                + "<input type='checkbox' style='float:left;' pvalue='" + path + "' name='selPhoto' value='" + szBase64 + "'/>"
                + "<img class='testImg' style='float:left;'  src='data:image/jpeg;base64,"
                + szBase64 + "' width='160' height='120'/>";
            $("#previewPhoto").html(previewPhotoStr);
    
            if (OCREnabled.checked) {
                var nCount = VideoInputCtl.GetQRcodeCount();
                var szType,
                    szText;
                for (var i = 0; i < nCount; i++) {
                    szType = VideoInputCtl.GetQRcodeTypeName(i);
                    szText = VideoInputCtl.GetQRcodeContent(i);
                    QRcodeText.value += szType + ": " + szText + "\r\n";
                }
            }
            OCRText.value = "";
            if (VideoInputCtl.GetDeviceOCR(nDeviceIndex)) {
                OCRText.value = VideoInputCtl.GetOCRResult();
            }
        }
        function get_name() {
            var date = new Date();
            var yy = date.getFullYear().toString();
            var mm = (date.getMonth() + 1).toString();
            var dd = date.getDate().toString();
            var hh = date.getHours().toString();
            var nn = date.getMinutes().toString();
            var ss = date.getSeconds().toString();
            var mi = date.getMilliseconds().toString();
            var picName = yy + mm + dd + hh + nn + ss + mi;
            return picName;
        }
        function UploadFileToHttp() {
            var imgs = document.getElementById("previewPhoto").getElementsByTagName("img");
            //var num=EThumbnails.GetDisplayCount();
            var num = imgs.length;
            UploadFile();
            if (num > 0) {
                UploadFile();
            } else {
                alert("请选择要上传的图片！");
                return;
            }
        }
        function UploadFile() {
            var matreClsm = '${matreClsm}';
            var departId = document.getElementById("departId").value;
            var lineName = document.getElementById("lineName").value;
            var lineCardNo = document.getElementById("lineCardNo").value;
            var LINE_ZJLX = document.getElementById("LINE_ZJLX").value;
            var isVip = 0;
            if (departId == "" || departId == null || departId == undefined
                || lineName == "" || lineName == null || lineName == undefined
                || lineCardNo == "" || lineCardNo == null || lineCardNo == undefined
                || LINE_ZJLX == "" || LINE_ZJLX == null || LINE_ZJLX == undefined) {
                alert("请补充取号信息！");
                return;
            }
            var tp = thumbnailPath();
            if (tp.length <= 1) {
                alert("请选择要上传的图片,图片至少包括取号人照片及身份证人像面照片！");
                return;
            }
            var imgStr;
            var imgStrAll = "";
            for (var i = 0; i < tp.length; i++) {
                imgStr = tp[i];
                if (imgStr == '' || imgStr == null || imgStr == undefined) {
                    continue;
                }
                imgStrAll = imgStrAll + ";" + imgStr;
            }
            $.post("callController.do?videoInputTakeNoContinue", {
                base64Code : imgStrAll,
                attachKey : "${attachKey}",
                uploadUserId : "${uploadUserId}",
                uploadUserName : "${uploadUserName}",
                winNo : "${winNo}",
                takeNoWay : "${takeNoWay}",
                busTableName : "T_CKBS_NUMRECORD",
                departId : departId,
                lineName : lineName,
                lineCardNo : lineCardNo,
                LINE_ZJLX : LINE_ZJLX,
                isVip : isVip
            },
                function(responseText1, status, xhr) {
                    //alert(responseText1);
                    //var obj = responseText1.parseJSON();
                    //alert(obj.msg);
                    var resultJson1 = $.parseJSON(responseText1);
                    //art.dialog.data("resultJsonInfo", resultJson1);
                    parent.$("#callingGrid").datagrid("reload");
                    AppUtil.closeLayer();
                });
        }
        function getTiff() {
            var imgs = document.getElementById("previewPhoto").getElementsByTagName("img");
            //var num=EThumbnails.GetDisplayCount();
            var num = imgs.length;
            if (num > 0) {
                var paths = getCheckedPath();
                tiff(paths);
            } else {
                alert("请选择要上传的图片！");
                return;
            }
        }
    
        function thumbnailPath() {
            var gfp = new Array();
            $("input[name='selPhoto']:checked").each(function() {
                gfp.push($(this).val());
            });
            return gfp;
        }
        function getCheckedPath() {
            var gfp = new Array();
            $("input[name='selPhoto']:checked").each(function() {
                gfp.push($(this).prop("pvalue"));
            });
            return gfp;
        }
    
        function changebarcode() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceBarcode(nDeviceIndex, BarcodeEnabled.checked);
        }
    
        function changeqrcode() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceQRcode(nDeviceIndex, QRcodeEnabled.checked);
        }
    
        function changeocr() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceOCR(nDeviceIndex, OCREnabled.checked);
            VideoInputCtl.SetOCRLanguage(OCRLanguage.selectedIndex);
        }
    
        function rotateangle() {
            var angle = document.getElementById("selRotateList").value;
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceRotate(nDeviceIndex, angle);
        }
    
        function flipxy() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceFlipX(nDeviceIndex, FlipX.checked);
            VideoInputCtl.SetDeviceFlipY(nDeviceIndex, FlipY.checked);
        }
    
        function changecolormode() {
            VideoInputCtl.SetColorPreview(true);
            VideoInputCtl.SetColorMode(ColorMode.selectedIndex);
        }
    
        function changebinarizemethod() {
            VideoInputCtl.SetBinarizeMode(BinarizeMethod.selectedIndex);
        }
    
        function changedeskew() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceDeskew(nDeviceIndex, DeskewEnabled.checked);
            VideoInputCtl.SetDeskewPreview(true);
        }
    
        function showproperty() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.ShowDevicePages(nDeviceIndex);
        }
    
        function changeexposure() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceExposure(nDeviceIndex, ExposureList.value);
        }
    
        function autoexposure() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceAutoExposure(nDeviceIndex, AutoExposure.checked);
        }
    
        function startrecord() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
    
            VideoInputCtl.SetDeviceRecordCodec(nDeviceIndex, RecordCodec.value);
            VideoInputCtl.SetDeviceRecordFileName(nDeviceIndex, RecordFile.value);
            VideoInputCtl.SetDeviceRecord(nDeviceIndex, true);
    
            changeresolution();
        }
    
        function stoprecord() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
    
            VideoInputCtl.SetDeviceRecord(nDeviceIndex, false);
    
            changeresolution();
        }
    
        function grabbase64() {
            var szBase64 = VideoInputCtl.GrabToBase64(".jpg");
            Base64Text.value = szBase64;
            Base64Image.src = "data:image/tiff;base64," + szBase64;
        }
    
        function grabtopdf() {
            var path = "C:\\TEMP\\" + get_name() + ".pdf";
            VideoInputCtl.GrabToPdf(path, "", "", "", "", "");
        }
    
        function jpegtopdf() {
            VideoInputCtl.JpegToPdf(JpegFiles.value, PdfFile.value, "", "", "", "", "");
        }
    
        function autograb() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
    
            if (!AutoGrab.checked) {
                VideoInputCtl.SetDeviceAutoGrab(nDeviceIndex, false);
            }
    
            VideoInputCtl.SetDeviceAutoGrabSetting(nDeviceIndex,
                StableLow.value, StableHigh.value, StableTime.value,
                GrabPrefix.value, GrabExt.value);
    
            if (AutoGrab.checked) {
                VideoInputCtl.SetDeviceAutoGrab(nDeviceIndex, true);
            }
        }
    
        function autograbreset() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceAutoGrabSn(nDeviceIndex, 0);
        }
    
        function facehighlight() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceFaceHighlight(nDeviceIndex,
                FaceHighlight.checked,
                FaceHiKernel.selectedIndex * 2 + 1,
                (FaceHiStep.selectedIndex + 1) * 2,
                FaceHiXml.value);
        }
    
        function undistort() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceUndistortCoeffs(nDeviceIndex, UCoeffs.value);
            VideoInputCtl.SetDeviceUndistort(nDeviceIndex, Undistort.checked);
        }
    
        function mouseenable() {
            VideoInputCtl.SetMouseEnabled(MouseLeft.checked, MouseRight.checked, MouseWheel.checked);
        }
    
        function combine() {
            var nCols = CombineCols.selectedIndex + 1;
            var files = CombineFiles.value;
            files = files.replace(/\r\n/g, '\n');
            alert(files);
            VideoInputCtl.CombineImages(files, CombineOut.value, nCols, 0xffffff);
        }
    
        function deleteaftercombine() {
            VideoInputCtl.SetDeleteAfterCombine(DeleteAfterCombine.checked);
        }
    
        function tiff(paths) {
            var files = paths;
            if (files.length <= 0) {
                alert("请选择要上传的图片！");
                return;
            }
            var imgStr;
            var imgStrAll = "";
            for (var i = 0; i < files.length; i++) {
                imgStr = files[i];
                if (imgStr == '' || imgStr == null || imgStr == undefined) {
                    continue;
                }
                imgStrAll = imgStrAll + imgStr + "|";
            }
            var TiffOut = "C:\\TEMP\\" + get_name() + ".tif";
            VideoInputCtl.JpegToTiff(imgStrAll, TiffOut);
            var tifBase64 = VideoInputCtl.Base64Image(TiffOut);
            //预览
            previewPhotoStr = previewPhotoStr
                + "<input type='checkbox' style='float:left;' pvalue='" + TiffOut + "' name='selPhoto' value='" + tifBase64 + "'/>"
                + "<img class='testImg' style='float:left;'  src='data:image/tiff;base64,"
                + tifBase64 + "' width='160' height='120'/>";
            $("#previewPhoto").html(previewPhotoStr);
        }
    
        function mugshot() {
            var nDeviceIndex = VideoInputCtl.GetDeviceIndex();
            VideoInputCtl.SetDeviceMugshot(nDeviceIndex, Mugshot.checked);
            VideoInputCtl.SetDeviceMugshotThreshold(nDeviceIndex, MugshotThreshold.value);
        }
        function copyUrl2() {
            var Url2 = document.getElementById("OCRText");
            Url2.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            alert("已复制好，可贴粘。");
        }
    
        function showSelectDeparts() {
            var departId = $("input[name='departId']").val();
            parent.$.dialog.open("departmentController/selector.do?departIds=" + departId + "&allowCount=1&checkCascadeY=&"
                + "checkCascadeN=", {
                    title : "组织机构选择器",
                    width : "600px",
                    lock : true,
                    resize : false,
                    height : "680px",
                    close : function() {
                        var selectDepInfo = art.dialog.data("selectDepInfo");
                        if (selectDepInfo) {
                            $("input[name='departId']").val(selectDepInfo.departIds);
                            $("input[name='DEPART_NAME']").val(selectDepInfo.departNames);
                        }
                    }
                }, false);
        }
    
        //------------------------------------身份身份证读取开始---------------------------------------------------
        function MyGetData() //OCX读卡成功后的回调函数
        {
            lineName.value = GT2ICROCX.Name; //<-- 姓名--!>		
            lineCardNo.value = GT2ICROCX.CardNo; //<-- 卡号--!>      
            //namel.value = GT2ICROCX.NameL;//<-- 姓名--!>		      
            //sex.value = GT2ICROCX.Sex;//<-- 性别--!>		
            //sexl.value = GT2ICROCX.SexL;//<-- 性别--!>	
            //nation.value = GT2ICROCX.Nation;//<-- 民族--!>		
            //nationl.value = GT2ICROCX.NationL;//<-- 民族--!>	
            //born.value = GT2ICROCX.Born;//<-- 出生--!>	
            //bornl.value = GT2ICROCX.BornL;//<-- 出生--!>
            //address.value = GT2ICROCX.Address;//<-- 地址--!>
            //police.value = GT2ICROCX.Police;//<-- 发证机关--!>
            //activity.value = GT2ICROCX.Activity;//<-- 有效期--!>
            //activitylfrom.value = GT2ICROCX.ActivityLFrom;//<-- 有效期--!>
            //activitylto.value = GT2ICROCX.ActivityLTo;//<-- 有效期--!>
            //photo.value = GT2ICROCX.GetPhotoBuffer();
    
        //jpgPhotoFace1.value = GT2ICROCX.GetFaceJpgBase64(0);//双面
        //jpgPhotoFace1.value = GT2ICROCX.GetFaceJpgBase64(1);//正面
        //jpgPhotoFace2.value = GT2ICROCX.GetFaceJpgBase64(2);//反面	
        }
    
        function MyClearData() //OCX读卡失败后的回调函数
        {
            lineName.value = "";
            lineCardNo.value = "";
        //namel.value = ""     
        //sex.value = "";	
        //sexl.value = "";
        //nation.value = "";	
        //nationl.value = "";
        //born.value = "";	
        //bornl.value = "";
        //address.value = "";
        //police.value = "";
        //activity.value = "";
        //activitylfrom.value = "";
        //activitylto.value = "";
        //photo.value = "";
        //jpgPhotoFace1.value =  "";
        //jpgPhotoFace2.value =  "";	
        }
    
        function MyGetErrMsg() //OCX读卡消息回调函数
        {
            Status.value = GT2ICROCX.ErrMsg;
        }
    
        function print() //打印
        {
            GT2ICROCX.PrintFaceImage(0, 30, 10) //0 双面，1 正面，2 反面
        }
        //------------------------------------身份身份证读取结束---------------------------------------------------
    
        function setValidate(zjlx) {
            if (zjlx == "TWTX" || zjlx == "GATX") {
                $("#isVip").val(1);
                $("#zjhm").removeClass(",custom[vidcard]");
            } else if (zjlx == "SF") {
                $("#isVip").val(0);
                $("#zjhm").addClass(",custom[vidcard]");
            } else {
                $("#isVip").val(0);
                $("#zjhm").removeClass(",custom[vidcard]");
            }
        }
    </script>
    <script type="text/javascript" for="VideoInputCtl" event="OnGrabTrigger(nDeviceIndex)">
        alert(nDeviceIndex);
    </script>
    <script type="text/javascript" for="VideoInputCtl" event="OnGrabNow(szFileName)">
        alert(szFileName);
    </script>
    <script type="text/javascript" for="VideoInputCtl" event="OnMaskChanged(nDeviceIndex,nStatus)">
        var text;
    
        if (nStatus == 0) {
            text = "New mask";
        } else if (nStatus == 1) {
            text = "Modify mask";
        } else if (nStatus == 2) {
            text = "Clear mask";
        }
    
        alert("Device:" + nDeviceIndex + ", " + text);
    </script>
    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>    //设置回调函数
        MyGetData()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>    //设置回调函数
        MyGetErrMsg()
    </SCRIPT>

    <SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>    //设置回调函数
        MyClearData()
    </SCRIPT>
    <style>
.sel {
    border: solid 1px red;
}
</style>
</head>
<body onload="opendevice()" onload="release()">
    <object classid="clsid:6EDE7AEC-5C00-4D60-B4FC-774F57A2A480" style="display:none" id=TrCtrl> </object>
    <input type="hidden" id="sxtsx" name="sxtsx" value="0" />
    <input id="AutoExposure" type="hidden" onclick="autoexposure()" />
    <input id="MouseLeft" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseRight" type="hidden" onclick="mouseenable()" checked="checked" />
    <input id="MouseWheel" type="hidden" onclick="mouseenable()" checked="checked" />
    <div class="gpy">
        <div class="clearfix">
            <div class="lfloat">
                <div class="gpy-camera">
                    <object id="VideoInputCtl" classid="CLSID:30516390-004F-40B9-9FC6-C9096B59262E" codebase="*.cab#version=1,0,0,1" width="560" height="300"></object>
                </div>
            </div>
            <div class="rfloat">
                <div class="gpy-btn">
                    <a href="javascript:void(0);" onclick="changedev1()">切换摄像头</a>
                </div>
                <div class="gpy-btn">
                    <a href="javascript:void(0);" id="ShowProperty" value="ShowProperty" onclick="showproperty()">设备属性</a>
                </div>
                <div class="gpy-btn gjgn">
                    高级功能<i class="af"></i>
                </div>
                <div class="gpy-af">
                    <div class="gpy-af-con">
                        分辨率 <select class="select1" id="ResolutionList" name="ResolutionList" onchange="changeresolution()">
                        </select>
                    </div>
                    <div class="gpy-af-con">
                        旋转角度 <select class="select1" id="selRotateList" name="selRotateList" onchange="rotateangle()">
                        </select>
                    </div>
                    <div class="gpy-af-con">
                        颜色设置 <select class="select1" id="ColorMode" name="ColorMode" onchange="changecolormode()">
                            <option selected="Color">彩色</option>
                            <option selected="Grayscale">灰色</option>
                            <option selected="Blackwhite">黑白</option>
                        </select>
                    </div>
                    <div class="gpy-af-con">
                        曝光 <select class="select1" id="ExposureList" name="ExposureList" onchange="changeexposure()">
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <span style="color: red;">友情提示：请在仪器正常对焦后进行拍摄。（对焦时间约为10秒）<strong>请拍取取号人现场照片及身份证人像面照片以便核查。</strong></span>
        <%--        <div id="previewPhoto" class="gpy-camera-info" >--%>
        <div id="previewPhoto" class="gpy-camera-info" style="overflow-y:auto; width:710px; height:120px;"></div>
        <div id="AssistFormDiv">
            <%--==========隐藏域部分开始 ===========--%>
            <input type="hidden" name="departId" id="departId"> <%--==========隐藏域部分结束 ===========--%>
                <table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
                    <tr>
                        <td><span style="width: 100px;float:left;text-align:right;">证件类型：</span> <eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
                                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);" defaultEmptyText="选择证件类型" value="SF" name="LINE_ZJLX" id="LINE_ZJLX">
                            </eve:eveselect> <font class="dddl_platform_html_requiredFlag">*</font></td>
                        </td>
                        <td colspan="2"><span style="width: 100px;float:left;text-align:right;">姓名：</span> <input type="text" style="width:150px;float:left;" maxlength="8"
                            class="eve-input validate[required]" name="lineName" id="lineName" readonly="readonly" /> <font class="dddl_platform_html_requiredFlag">*</font></td>
                    </tr>
                    <tr>
                        <td><span style="width: 100px;float:left;text-align:right;">证件号码：</span> <input type="text" style="width:150px;float:left;" maxlength="18" id="lineCardNo"
                            readonly="readonly" class="eve-input validate[required]" name="lineCardNo" /> <font class="dddl_platform_html_requiredFlag">*</font></td>
                        <td><span style="width: 100px;float:left;text-align:right;">办事部门：</span> <input type="text" style="width:150px;float:left;" readonly="readonly"
                            onclick="showSelectDeparts();" class="eve-input validate[required]" name="DEPART_NAME" id="DEPART_NAME" /> <font class="dddl_platform_html_requiredFlag">*</font></td>
                    </tr>
                    <tr>
                        <td><span style="width: 100px;float:left;text-align:right;">手机号码：</span> <input type="text" style="width:150px;float:left;" maxlength="18" id="phoneNo"
                            class="eve-input validate[]" name="phoneNo" /></td>
                        <td></td>
                        <%--					<td><span--%>
                        <%--						style="width: 100px;float:left;text-align:right;">绿色通道：</span>--%>
                        <%--						<eve:eveselect clazz="tab_text validate[required]" dataParams="YesOrNo"--%>
                        <%--						dataInterface="dictionaryService.findDatasForSelect"--%>
                        <%--						defaultEmptyText="请选择" value="0" name="isVip" id="isVip">--%>
                        <%--						</eve:eveselect>--%>
                        <%--					</td>--%>
                    </tr>
                </table>
        </div>
        <br />
        <div class="gpy-sub">
            <a class="camera" onclick="grabimage()"><span><i></i>拍 照</span></a>
            <%--        	<a class="camera" onclick="grabtopdf()"><span><i></i>生成PDF</span></a>--%>
            <%--        	<a class="submit" onclick="getTiff()"><span><i></i>生成TIFF文件</span></a>--%>
            <a class="submit" onclick="readCard()"><span><i></i>读 卡</span></a> <a class="submit" onclick="UploadFileToHttp()"><span><i></i>确 认</span></a> <a class="cancel"
                onclick="AppUtil.closeLayer();"><span><i></i>取 消</span></a>
        </div>
    </div>


    <!-- JS -->
    <script type="text/javascript">
        /**
         * 读卡
         */
        function readCard() {
            var port = TrCtrl.TR_SdtGetSAMStatus_ocx();
    
            if (port == -1) {
                alert('设备连接失败');
                return;
            }
    
            var cardInfoStr = TrCtrl.TR_GetIdInfoExport_ocx();
            var cardInfos = cardInfoStr.split('||');
            if (cardInfos == '') {
                alert('未能有效识别身份证，请重新读卡！');
                return;
            }
    
            $("input[name='lineName']").val(cardInfos[1]);
            $("input[name='lineCardNo']").val(cardInfos[5]);
        }
    
        $(document).ready(function() {
    
            $(".gjgn").click(function() {
                $(".gpy-af").toggle();
            });
    
            $(".testImg").click(function() {
                $(this).addClass("sel").siblings("testImg").removeClass("sel");
            });
        });
    
        //$(".select1").select();
    </script>
</body>
</html>
