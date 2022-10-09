/**首页网上办事查询JS开始**/
function wsbs_submit() {
	$('#wsbs_form').submit();
}

function wsbs_hot_submit(event) {
	$('[name="projectname"]').val($(event.target).html());
	$('#wsbs_form').submit();
}
/**首页网上办事查询JS结束**/

/**警民互动厅长信息查询JS开始**/
function checkformold(){
	var number = document.getElementById("number").value;
	var password = document.getElementById("password").value;
	
	if(null==number||""==number||"请输入信件编号"==number){
		alert("请输入信件编号");
		document.getElementById("number").focus();
	}else if(null==password||""==password||"请输入查询密码"==password){
		alert("请输入查询密码");
		document.getElementById("password").focus();
	}else{
		//加密
		passDes("password");
		$('#form3').submit();
		document.getElementById("password").value = '';
	}
		
}
/**警民互动厅长信息查询JS结束**/

/**列表页查询条件JS开始**/
function changeClass(obj){
	$("#"+obj).parent().parent().children('li').removeClass("select-result");
	$("#"+obj).parent().toggleClass('select-result');
}

function changeValue(obj,value){
	$("#"+obj).val(value);
}
/**列表页查询条件JS结束**/

/**微博分享JS开始**/
function blogShare(num){
	var ctid = $("#ctid").val();
	var url = path+"/jhtml/share";
	$.post(url, {
		"ctid":$("#ctid").val(),
		"num":num
		}, function(responseText, status, xhr) {
			$(".shareCount").html(decodeURI(responseText));
			$(".shareCount").attr("title",
									"\u7d2f\u8ba1\u5206\u4eab" + decodeURI(responseText)
											+ "\u6b21");
	});
}
/**微博分享JS结束**/

/**聚焦各地JS开始**/
function cityShow(obj){
	$(".tc_city").hide();
	$("#"+obj).show();
}
function cityHide(obj){
	$(".cityhover").attr('class','tc_citya');
	$(".cityhover1").attr('class','tc_citya1');
	$("#"+obj).hide();
}
function cityHover(obj){
	$(".cityhover").attr('class','tc_citya');
	$(".cityhover1").attr('class','tc_citya1');
	$("#"+obj).attr("class","cityhover"+$("#"+obj).attr('class').replace("tc_citya",""));
}
/**聚焦各地JS结束**/

/**下拉框链接选择JS开始**/
function skipLink(like){
	if(like!=0){
		window.open(like,'','')
	}else{
		alert("请选择相关链接");
	}
}
/**下拉框链接选择JS开始**/
/**打印JS开始**/
function preview(oper){  
	   if (oper < 10){  
		bdhtml=window.document.body.innerHTML;//获取当前页的html代码  
		sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域  
		eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域  
		prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html  
		  
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html  
		window.document.body.innerHTML=prnhtml;  
		window.document.body.className="ind_body";
		window.print();  
		window.document.body.innerHTML=bdhtml;  
		window.document.body.className="";
		//window.location.reload();
	  } else {  
	     window.print();  
	  }  
 } 

/**查询**/
 function search(){ 
	if(jQuery.trim($("#key").val()).length == 0 || jQuery.trim($("#key").val()) == '请输入关键字' ){			$("#key").val('');
		alert("请输入关键字"); 
		$("#searchword").focus(); 
	}else{ 
		$("#searchForm").submit();
	}
}

/**
 * 替姓名为张**
 */
function replayName(name){
    if(name==null){
        return "";
    }
    name = name.replace(/(^\s*)|(\s*$)/g,"");
    var len = name.length;
    var first = name.substring(0,1);
    var sb="";
    for(var i=0;i<len-1;i++){
        sb=sb+"*";
    }
    return first+sb;
}