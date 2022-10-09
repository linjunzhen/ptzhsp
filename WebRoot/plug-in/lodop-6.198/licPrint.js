$(function(){
	$("#LODOP_OB").attr("width", "100%");
});
//监听窗口
$(window).resize(function () {
    $("#LODOP_OB").attr("width", $(window).width());
});

var PrintPage = {}; //声明打印
PrintPage.Items = new Array(); //声明打印对象项目


//初始化页面
PrintPage.InitPage = function () {
    if (PrintPage.Name == "") {
        alert("请声明打印页面名称！");
    }
    var strPrintInita = $.cookie(PrintPage.PrintInitaName);
	LODOP.PRINT_INITA(PrintPage.PrintInita.Top,PrintPage.PrintInita.Left,PrintPage.PrintInita.Width,PrintPage.PrintInita.Height,PrintPage.PrintInita.strPrintName);

   // console.log(strPrintInita);
    if(strPrintInita!=null){
    	if(strPrintInita && strPrintInita.length > 0){
    		  var arrCookiesPrintInita = strPrintInita.split(','); //将读取的位置信息进行分割
    		  PrintPage.PrintInita.Top = arrCookiesPrintInita[0];
    		  PrintPage.PrintInita.Left = arrCookiesPrintInita[1];
    		  PrintPage.PrintInita.Width = arrCookiesPrintInita[2];
    		  PrintPage.PrintInita.Height = arrCookiesPrintInita[3];
    		  PrintPage.PrintInita.strPrintName = arrCookiesPrintInita[4];
    		//console.log("arrCookiesPrintInita:"+arrCookiesPrintInita);
    	}
    }
    
    if (PrintPage.Items.length > 0) {
        var strCookies = PrintPage.GetCookie(); //读取Cookie中的位置信息
        try {
            if (strCookies != "null") {
                if (strCookies && strCookies.length > 0) {
                    var arrCookies = strCookies.split('|');
                    for (var j = 0; j < arrCookies.length; j++) {
                        var arrCookiesParams = arrCookies[j].split(','); //将读取的位置信息进行分割
                        if (j < PrintPage.Items.length) {
                            PrintPage.Items[j].Top = arrCookiesParams[0]; //上边距
                            PrintPage.Items[j].Left = arrCookiesParams[1]; //左边距
                            PrintPage.Items[j].Height = arrCookiesParams[2]; //宽度
                            PrintPage.Items[j].Width = arrCookiesParams[3]; //高度
                            PrintPage.Items[j].FontName = arrCookiesParams[4]; //字体
                            PrintPage.Items[j].FontSize = arrCookiesParams[5]; //字体大小
                            PrintPage.Items[j].FontColor = arrCookiesParams[6]; //字体颜色
                            PrintPage.Items[j].ItemName = arrCookiesParams[7]; //标签类型
                        }
                    }
                }
            };
        } catch (e) {

        }
        PrintPage.CreateBookmarks(); //创建书签
    };
};
//创建书签
PrintPage.CreateBookmarks = function () {
    if (PrintPage.Items.length > 0) {
        $.each(PrintPage.Items, function (i) {
            if (PrintPage.Items[i].ItemName == "QRCode") {
                LODOP.ADD_PRINT_BARCODE(PrintPage.Items[i].Top, PrintPage.Items[i].Left, PrintPage.Items[i].Width, PrintPage.Items[i].Height, "QRCode", PrintPage.Items[i].Text);
                LODOP.SET_PRINT_STYLEA(0, "ItemName", PrintPage.Items[i].ItemName);
                
            }else if(PrintPage.Items[i].ItemClassName == "'Image'"){
            	LODOP.ADD_PRINT_IMAGE(PrintPage.Items[i].Top, PrintPage.Items[i].Left, PrintPage.Items[i].Width, PrintPage.Items[i].Height,"<img border='0' src='"+PrintPage.Items[i].Text+"'/>");
            	LODOP.SET_PRINT_STYLEA(0,"Stretch",2);//(可变形)扩展缩放模式
            	LODOP.SET_PRINT_STYLEA(0,"FontSize",0);
            }else {
                LODOP.ADD_PRINT_TEXT(PrintPage.Items[i].Top, PrintPage.Items[i].Left, PrintPage.Items[i].Width, PrintPage.Items[i].Height, PrintPage.Items[i].Text.replace(/<br>/g, "\r\n"));
//                LODOP.SET_PRINT_STYLEA(0,"ContentVName","MyData");
                LODOP.SET_PRINT_STYLEA(0, "FontName", PrintPage.Items[i].FontName);
                LODOP.SET_PRINT_STYLEA(0, "FontSize", PrintPage.Items[i].FontSize);
                LODOP.SET_PRINT_STYLEA(0, "FontColor",PrintPage.Items[i].FontColor);
            }
        });
    }
};
//保存打印参数到cookie
PrintPage.SetCookie = function () {
//	console.log(LODOP.GET_VALUE("PrintInitTop", 0));
//	console.log(LODOP.GET_VALUE("PrintInitLeft", 0));
	var strInitDataCookie = "";
	strInitDataCookie += LODOP.GET_VALUE("PrintInitTop", 0)+",";
	strInitDataCookie += LODOP.GET_VALUE("PrintInitLeft", 0)+",";
	strInitDataCookie += LODOP.GET_VALUE("PrintInitWidth", 0)+",";
	strInitDataCookie += LODOP.GET_VALUE("PrintInitHeight", 0)+","; 
	strInitDataCookie += LODOP.GET_VALUE("PrintTaskName", 0);
		
    if (PrintPage.Items.length > 0) {
        var strCookies = "";
        for (var i = 0; i < PrintPage.Items.length; i++) {
            strCookies += LODOP.GET_VALUE("ItemTop", i + 1) + ",";
            strCookies += LODOP.GET_VALUE("ItemLeft", i + 1) + ",";
            strCookies += LODOP.GET_VALUE("ItemHeight", i + 1) + ",";
            strCookies += LODOP.GET_VALUE("ItemWidth", i + 1) + ",";
            strCookies += LODOP.GET_VALUE("ItemFontName", i + 1) + ",";
            strCookies += LODOP.GET_VALUE("ItemFontSize", i + 1) + ",";
            strCookies += LODOP.GET_VALUE("ItemColor", i + 1) + ",";
            strCookies += LODOP.GET_VALUE("ItemClassName", i + 1) + ",";
            if(LODOP.GET_VALUE("ItemFontSize", i + 1)==0){
                strCookies += "IMAGE,";
            }
            if (i == PrintPage.Items.length - 1) {
                strCookies += LODOP.GET_VALUE("ItemName", i + 1);
            } else {
                strCookies += LODOP.GET_VALUE("ItemName", i + 1) + "|";
            }
        }
        $.cookie(PrintPage.Name, strCookies, { expires: 365 }); //写入Cookie
        $.cookie(PrintPage.PrintInitaName, strInitDataCookie, { expires: 365 }); //写入Cookie
        if (PrintPage.GetCookie() != "") {
            alert("标签位置保存成功！");
        }
    }
};
//清空本地Cookie
PrintPage.ClearCookie = function() {
    $.cookie(PrintPage.Name, null);
    $.cookie(PrintPage.PrintInitaName, null);
};
//从cookie中获取打印参数
PrintPage.GetCookie = function () {
    return $.cookie(PrintPage.Name);
};
