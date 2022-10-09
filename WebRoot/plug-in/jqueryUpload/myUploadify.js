var jsonObj2={accessory:[]};
$(function() {
	$('#uploadify').uploadify({
		'uploader' : getRootPath()
				+ '/js/jquery/uploadify-v2.1.4/uploadify.swf',// 文件的相对路径，点击后淡出打开文件对话框
		'script' : getRootPath() + '/servlet/upload',// 后台处理程序的相对路径
		'cancelImg' : getRootPath() + '/style/default/css/images/cancel.png',// 用来判断上传选择的文件在服务器是否存在的后台处理程序的相对路径
		'buttonImg' : getRootPath() + '/style/default/css/images/browse.gif', 
		// 'buttonText' : '浏览',//浏览按钮的文本，默认值：BROWSE
		'folder' : '/files',// 上传文件存放的目录
		'queueID' : 'myqueue',// 文件队列的ID，该ID与存放文件队列的div的ID一致
		'queueSizeLimit' : 20,// 当允许多文件生成时，设置选择文件的个数，默认值：999
		'multi' : true,// 设置为true时可以上传多个文件
		'auto' : true,// 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传
		'fileDesc' : 'Files',// 如果配置了以下的'fileExt'属性，那么这个属性是必须的
		'fileExt' : '*.txt;*.xml;*.rar;*.zip;*.swf;*.wmv;*.avi;*.wma;*.mp3;*.mid;*.doc;*.xls;*.ppt;*.bmp;*.dib;*.gif;*.jfif;*.jpe;*.jpeg;*.jpg;*.png;*.tif;*.tiff;*.ico;*.pdf;*.ceb',// 允许的格式
		'sizeLimit' : 919871202, // 设置单个文件大小限制，单位为byte
		'simUploadLimit' : 20,// 一次同步上传的文件数目
		'removeCompleted' : false,
		'onInit' : function(){
			var text=$('#accessory').val();
			if(text!=""){
				jsonObj2 = JSON.parse(text); //字符串转化成JSON
				var accessory=jsonObj2.accessory;
				var len=accessory.length;
				if(len!=0){
					var startString='<table id="tab" style="border:1px solid #a5aeb6;">';
					startString+='<tr style="border-bottom:1px solid #a5aeb6;">';
					startString+='<td style="width:350px;text-align:center;">文件名称</td>'+
								 '<td style="width:180px;text-align:center;">上传时间</td>'+
								 '<td style="width:80px;text-align:center;">文件大小</td>'+
								 '<td style="width:80px;text-align:center;">操作</td>';
					startString+='</tr>';
					if($("#myqueue").html()==""){
						$("#myqueue").append(startString);
						for(var i=0;i<accessory.length;i++){
						var cur_accessory=eval("(" + accessory[i] + ")" );//字符串转化成JSON
						var userId=cur_accessory.userId;
						addAccessory(i,cur_accessory.pfilename,cur_accessory.bfilename,cur_accessory.size,getRootPath() + '/style/default/css/images/cancel.png',cur_accessory.category,userId);
					}
					}
				}
					
				if(len!=0){
					var endString='</table>';
					$("#myqueue").append(endString);
				}
			}
		},
		'onError' : function(event, queueId, fileObj, errorObj) {
			// event(事件对象),
			// queueId(文件的唯一标识),fileObj(name、size、creationDate、modificationDate、type
			// 5个属性，例：fileObj.name)
			// errorObj对象有type(错误的类型，有三种‘HTTP’,‘IO’, or
			// ‘Security’)和info(错误的描述)两个属性
			alert("文件:" + fileObj.name + "上传失败");
		},
		'onQueueFull': function(event,data) {  
			alert("上传数目已满. 最多上传10个文件！！");  
		}, 
		'onComplete' : function(event, queueId, fileObj, response, data) {
			// 文件上传完成后触发
			// response为后台处理程序返回的值，在上面的例子中为1或0，
			// data有两个属性fileCount和speed,fileCount：剩余没有上传完成的文件的个数。speed：文件上传的平均速率
			// kb/s
			// 注：fileObj对象和上面讲到的有些不太一样，onComplete
			// 的fileObj对象有个filePath属性可以取出上传文件的路径
			// alert(response+" data="+data.fileCount);
			//json_array[json_array.length]="pfilename:'"+fileObj.name+"',bfilename:'"+response+"',size:'"+fileObj.size+"',type:'"+fileObj.type+"'";
			//if(data.fileCount==0){
				//var json=arrayToJson(json_array);
				//$('#accessory').attr('value',json2str(json));
			//}
			var temp="{pfilename:'"+fileObj.name+"',bfilename:'"+response+"',size:'"+fileObj.size+"',type:'"+fileObj.type+"',category:'"+new Date().toLocaleString().replace("年", "-").replace("月", "-").replace("日", "")+"'";
			var userId=document.getElementById("loginUserId");
			if(userId!=null)
				temp=temp+",userId:"+userId.value;
			temp=temp+"}";
			jsonObj2.accessory.push(temp);//数组最后加一条记录
			if(data.fileCount==0){
				//转换为json文本 并赋值
				$('#accessory').attr('value',JSON.stringify(jsonObj2));
			}	
			var modulName=document.title;
			var infor_type=document.getElementById("infor_type");
			if(infor_type!=null&&infor_type!=''){
				if(infor_type.value=="1613")
					modulName="资源共享";
				if(infor_type.value=="203")
					modulName="通知公告";
				if(infor_type.value=="204")
					modulName="工作动态";
				if(infor_type.value=="205")
					modulName="工作指南";
				if(infor_type.value=="3430")
					modulName="环境标准";
			}
			$.ajax({
				type	: 'POST',
				dataType: 'json',
				url		: getRootPath()+"/addLog.action?op=1&fileName="+encodeURI(fileObj.name+"&modulName="+modulName),
				error	: function() {showInfo("mes",'系统温馨提示',"系统出错,请与管理员联系！");},
				success	: function(data) {
				}	
			});
			//alert($('#accessory').val());
			/*$('#retpanel')
					.append('<li id=\''
							+ response
							+ '\'>'
							+

							'<img src="'
							+ getRootPath()
							+ '/files/'
							+ response
							+ '" /><img src="'
							+ getRootPath()
							+ '/style/default/css/images/cancel.png" onclick="deleteFile(\''
							+ response + '\');"/></li>');*/

		},
		onCancel: function(event, ID, fileObj, data) { 
			var fileName="";
			var bfilename=jQuery("#Link" + ID).attr('bfilename');
			var accessory=jsonObj2.accessory;
			for(var i=0;i<accessory.length;i++){
				var cur_accessory=eval("(" + accessory[i] + ")" ); //获取数组对象
				if(cur_accessory.bfilename==bfilename){//判断bfilename是否出现，出现则删除
						jsonObj2.accessory.splice(i,1);//删除JSON对象
						fileName=cur_accessory.pfilename;
				}
			}
			//转换为json文本 并赋值
			$('#accessory').attr('value',JSON.stringify(jsonObj2));
			if(accessory.length==0){
				$("#tab").remove();
			}
			var modulName=document.title;
			var infor_type=document.getElementById("infor_type");
			if(infor_type!=null&&infor_type!=''){
				if(infor_type.value=="1613")
					modulName="资源共享";
				if(infor_type.value=="203")
					modulName="通知公告";
				if(infor_type.value=="204")
					modulName="工作动态";
				if(infor_type.value=="205")
					modulName="工作指南";
				if(infor_type.value=="3430")
					modulName="环境标准";
			}
			$.ajax({
				type	: 'POST',
				dataType: 'json',
				url		: getRootPath()+"/addLog.action?op=2&fileName="+encodeURI(fileName+"&modulName="+modulName),
				error	: function() {showInfo("mes",'系统温馨提示',"系统出错,请与管理员联系！");},
				success	: function(data) {
				}	
			});
		},//清除一个的时候.对应的循序清楚数组中的，后面的向前赋值。  
		onClearQueue: function(event,data) {
			jsonObj2={accessory:[]};
			$('#accessory').attr('value',JSON.stringify(jsonObj2));
		}//清楚所有的时候
	});
});

/**
 * 初始化附件列表
 * @param ID
 *            ID
 * @param fileName
 *            文件名
 * @param response
 *            服务器上的文件名
 * @param size
 *            文件大小 
 * @param img
 * 			删除的图标路径
 * @param category
 * 			附件类别
 */
function addAccessory(ID, fileName, response, size, img,category) {
	var byteSize = Math.round(size / 1024 * 100) * .01;
	var suffix = 'KB';
	if (byteSize > 1000) {
		byteSize = Math.round(byteSize * .001 * 100) * .01;
		suffix = 'MB';
	}
	var sizeParts = byteSize.toString().split('.');
	if (sizeParts.length > 1) {
		byteSize = sizeParts[0] + '.' + sizeParts[1].substr(0, 2);
	} else {
		byteSize = sizeParts[0];
	}
	
	/*var htmlString='<div id="uploadify'+ ID+ '" class="uploadifyQueueItem">';
	if(img!=""){
		htmlString+='<div class="cancel">' +
					'<a  id="Link'+ ID+ '" bfilename="'+ response+ '" href="javascript:jQuery(\'#uploadify\').uploadifyCancel(\''+ ID+ '\',\''+ response+ '\')">' +
					'<img src="'+ img+ '" border="0" />' +
					'</a></div>';
	}
		htmlString+='<span class="fileName">' +
					'<a id="Down'+ID+'" href="'+getRootPath()+'/download.jsp?filename='+response+'">'+ fileName + '</a> ' +
					'(' + byteSize + suffix+ ')</span><span class="percentage">- 完成</span></div>';
	*/
	/*var	htmlString='<tr id="uploadify'+ ID+ '" style="background:#fff">'+
						'<td style="text-align:left;"><a id="Down'+ID+'" href="'+getRootPath()+'/download.jsp?filename='+response+'">'+ fileName + '</a></td>'+
						'<td style="text-align:center;">'+ category +'</td>'+
						'<td style="text-align:center;">' + byteSize + suffix+ '</td>';*/
	//var suffix=response.substring(response.indexOf("."),response.length);
	var	htmlString='<tr id="uploadify'+ ID+ '" style="background:#fff">';
	//if(suffix==".doc"||suffix==".xls"){
	if(suffix==".doc"){
		htmlString+='<td style="text-align:left;"><a id="Down'+ID+'" href="#" onclick="javascript:OpenText2(\''+response+'\');">'+ fileName + '</a></td>';
	}else{
		htmlString+='<td style="text-align:left;"><a id="Down'+ID+'" href="'+getRootPath()+'/download.jsp?filename='+response+'">'+ fileName + '</a></td>';
	}
	if(category!="")
	htmlString+='<td style="text-align:center;">'+ category +'</td>'+
						'<td style="text-align:center;">' + byteSize + suffix+ '</td>';		
						
	if(img!=""){
			htmlString+='<td style="text-align:center;">'+
							'<a  id="Link'+ ID+ '" bfilename="'+ response+ '" href="javascript:jQuery(\'#uploadify\').uploadifyCancel(\''+ ID+ '\',\''+ response+ '\')">'+
							'<img src="'+ img+ '" border="0" />'+
							'</a></td>';
						
	}else{
			htmlString+='<td style="width:80px;">&nbsp;</td>';
	}
	htmlString+='</tr>';
	//jQuery("#myqueue").append(htmlString);
	$("#tab").append(htmlString);
}

/**
 * 初始化附件列表
 * @param ID
 *            ID
 * @param fileName
 *            文件名
 * @param response
 *            服务器上的文件名
 * @param size
 *            文件大小 
 * @param img
 * 			删除的图标路径
 * @param category
 * 			附件类别
 */
function addAccessory(ID, fileName, response, size, img,category,userId) {
	var byteSize = Math.round(size / 1024 * 100) * .01;
	var suffix = 'KB';
	if (byteSize > 1000) {
		byteSize = Math.round(byteSize * .001 * 100) * .01;
		suffix = 'MB';
	}
	var sizeParts = byteSize.toString().split('.');
	if (sizeParts.length > 1) {
		byteSize = sizeParts[0] + '.' + sizeParts[1].substr(0, 2);
	} else {
		byteSize = sizeParts[0];
	}
	
	/*var htmlString='<div id="uploadify'+ ID+ '" class="uploadifyQueueItem">';
	if(img!=""){
		htmlString+='<div class="cancel">' +
					'<a  id="Link'+ ID+ '" bfilename="'+ response+ '" href="javascript:jQuery(\'#uploadify\').uploadifyCancel(\''+ ID+ '\',\''+ response+ '\')">' +
					'<img src="'+ img+ '" border="0" />' +
					'</a></div>';
	}
		htmlString+='<span class="fileName">' +
					'<a id="Down'+ID+'" href="'+getRootPath()+'/download.jsp?filename='+response+'">'+ fileName + '</a> ' +
					'(' + byteSize + suffix+ ')</span><span class="percentage">- 完成</span></div>';
	*/
	/*var	htmlString='<tr id="uploadify'+ ID+ '" style="background:#fff">'+
						'<td style="text-align:left;"><a id="Down'+ID+'" href="'+getRootPath()+'/download.jsp?filename='+response+'">'+ fileName + '</a></td>'+
						'<td style="text-align:center;">'+ category +'</td>'+
						'<td style="text-align:center;">' + byteSize + suffix+ '</td>';*/
	//var suffix=response.substring(response.indexOf("."),response.length);
	var	htmlString='<tr id="uploadify'+ ID+ '" style="background:#fff">';
	//if(suffix==".doc"||suffix==".xls"){
	if(suffix==".doc"){
		htmlString+='<td style="text-align:left;"><a id="Down'+ID+'" href="#" onclick="javascript:OpenText2(\''+response+'\');">'+ fileName + '</a></td>';
	}else{
		htmlString+='<td style="text-align:left;"><a id="Down'+ID+'" href="'+getRootPath()+'/download.jsp?filename='+response+'">'+ fileName + '</a></td>';
	}
	if(category!="")
	htmlString+='<td style="text-align:center;">'+ category +'</td>'+
						'<td style="text-align:center;">' + byteSize + suffix+ '</td>';		
						
	if(img!=""){
			htmlString+='<td style="text-align:center;">'+
							'<a  id="Link'+ ID+ '" bfilename="'+ response+ '" href="javascript:jQuery(\'#uploadify\').uploadifyCancel(\''+ ID+ '\',\''+ response+ '\')">';
			var loginUserId=document.getElementById("loginUserId");
			if(loginUserId!=null)
				loginUserId=loginUserId.value;
			if(userId==null||loginUserId==null||userId==loginUserId||userId=="392"||userId=="420"||userId=="483")
				htmlString+='<img src="'+ img+ '" border="0" />';
			htmlString+='</a></td>';
						
	}else{
			htmlString+='<td style="width:80px;">&nbsp;</td>';
	}
	htmlString+='</tr>';
	//jQuery("#myqueue").append(htmlString);
	$("#tab").append(htmlString);
}