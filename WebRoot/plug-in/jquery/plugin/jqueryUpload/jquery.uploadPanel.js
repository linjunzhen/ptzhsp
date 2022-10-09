var SWFUpload;if(SWFUpload==undefined){SWFUpload=function(a){this.initSWFUpload(a)}}SWFUpload.prototype.initSWFUpload=function(b){try{this.customSettings={};this.settings=b;this.eventQueue=[];this.movieName="SWFUpload_"+SWFUpload.movieCount++;this.movieElement=null;SWFUpload.instances[this.movieName]=this;this.initSettings();this.loadFlash();this.displayDebugInfo()}catch(a){delete SWFUpload.instances[this.movieName];throw a}};SWFUpload.instances={};SWFUpload.movieCount=0;SWFUpload.version="2.2.0 2009-03-25";SWFUpload.QUEUE_ERROR={QUEUE_LIMIT_EXCEEDED:-100,FILE_EXCEEDS_SIZE_LIMIT:-110,ZERO_BYTE_FILE:-120,INVALID_FILETYPE:-130};SWFUpload.UPLOAD_ERROR={HTTP_ERROR:-200,MISSING_UPLOAD_URL:-210,IO_ERROR:-220,SECURITY_ERROR:-230,UPLOAD_LIMIT_EXCEEDED:-240,UPLOAD_FAILED:-250,SPECIFIED_FILE_ID_NOT_FOUND:-260,FILE_VALIDATION_FAILED:-270,FILE_CANCELLED:-280,UPLOAD_STOPPED:-290};SWFUpload.FILE_STATUS={QUEUED:-1,IN_PROGRESS:-2,ERROR:-3,COMPLETE:-4,CANCELLED:-5};SWFUpload.BUTTON_ACTION={SELECT_FILE:-100,SELECT_FILES:-110,START_UPLOAD:-120};SWFUpload.CURSOR={ARROW:-1,HAND:-2};SWFUpload.WINDOW_MODE={WINDOW:"window",TRANSPARENT:"transparent",OPAQUE:"opaque"};SWFUpload.completeURL=function(a){if(typeof(a)!=="string"||a.match(/^https?:\/\//i)||a.match(/^\//)){return a}var c=window.location.protocol+"//"+window.location.hostname+(window.location.port?":"+window.location.port:"");var b=window.location.pathname.lastIndexOf("/");if(b<=0){path="/"}else{path=window.location.pathname.substr(0,b)+"/"}return path+a};SWFUpload.prototype.initSettings=function(){this.ensureDefault=function(b,a){this.settings[b]=(this.settings[b]==undefined)?a:this.settings[b]};this.ensureDefault("upload_url","");this.ensureDefault("preserve_relative_urls",false);this.ensureDefault("file_post_name","Filedata");this.ensureDefault("post_params",{});this.ensureDefault("use_query_string",false);this.ensureDefault("requeue_on_error",false);this.ensureDefault("http_success",[]);this.ensureDefault("assume_success_timeout",0);this.ensureDefault("file_types","*.*");this.ensureDefault("file_types_description","All Files");this.ensureDefault("file_size_limit",0);this.ensureDefault("file_upload_limit",0);this.ensureDefault("file_queue_limit",0);this.ensureDefault("flash_url","swfupload.swf");this.ensureDefault("prevent_swf_caching",true);this.ensureDefault("button_image_url","");this.ensureDefault("button_width",1);this.ensureDefault("button_height",1);this.ensureDefault("button_text","");this.ensureDefault("button_text_style","color: #000000; font-size: 16pt;");this.ensureDefault("button_text_top_padding",0);this.ensureDefault("button_text_left_padding",0);this.ensureDefault("button_action",SWFUpload.BUTTON_ACTION.SELECT_FILES);this.ensureDefault("button_disabled",false);this.ensureDefault("button_placeholder_id","");this.ensureDefault("button_placeholder",null);this.ensureDefault("button_cursor",SWFUpload.CURSOR.ARROW);this.ensureDefault("button_window_mode",SWFUpload.WINDOW_MODE.WINDOW);this.ensureDefault("debug",false);this.settings.debug_enabled=this.settings.debug;this.settings.return_upload_start_handler=this.returnUploadStart;this.ensureDefault("swfupload_loaded_handler",null);this.ensureDefault("file_dialog_start_handler",null);this.ensureDefault("file_queued_handler",null);this.ensureDefault("file_queue_error_handler",null);this.ensureDefault("file_dialog_complete_handler",null);this.ensureDefault("upload_start_handler",null);this.ensureDefault("upload_progress_handler",null);this.ensureDefault("upload_error_handler",null);this.ensureDefault("upload_success_handler",null);this.ensureDefault("upload_complete_handler",null);this.ensureDefault("debug_handler",this.debugMessage);this.ensureDefault("custom_settings",{});this.customSettings=this.settings.custom_settings;if(!!this.settings.prevent_swf_caching){this.settings.flash_url=this.settings.flash_url+(this.settings.flash_url.indexOf("?")<0?"?":"&")+"preventswfcaching="+new Date().getTime()}if(!this.settings.preserve_relative_urls){this.settings.upload_url=SWFUpload.completeURL(this.settings.upload_url);this.settings.button_image_url=SWFUpload.completeURL(this.settings.button_image_url)}delete this.ensureDefault};SWFUpload.prototype.loadFlash=function(){var a,b;if(document.getElementById(this.movieName)!==null){throw"ID "+this.movieName+" is already in use. The Flash Object could not be added"}a=document.getElementById(this.settings.button_placeholder_id)||this.settings.button_placeholder;if(a==undefined){throw"Could not find the placeholder element: "+this.settings.button_placeholder_id}b=document.createElement("div");b.innerHTML=this.getFlashHTML();a.parentNode.replaceChild(b.firstChild,a);if(window[this.movieName]==undefined){window[this.movieName]=this.getMovieElement()}};SWFUpload.prototype.getFlashHTML=function(){return['<object id="',this.movieName,'" type="application/x-shockwave-flash" data="',this.settings.flash_url,'" width="',this.settings.button_width,'" height="',this.settings.button_height,'" class="swfupload">','<param name="wmode" value="',this.settings.button_window_mode,'" />','<param name="movie" value="',this.settings.flash_url,'" />','<param name="quality" value="high" />','<param name="menu" value="false" />','<param name="allowScriptAccess" value="always" />','<param name="flashvars" value="'+this.getFlashVars()+'" />',"</object>"].join("")};SWFUpload.prototype.getFlashVars=function(){var b=this.buildParamString();var a=this.settings.http_success.join(",");return["movieName=",encodeURIComponent(this.movieName),"&amp;uploadURL=",encodeURIComponent(this.settings.upload_url),"&amp;useQueryString=",encodeURIComponent(this.settings.use_query_string),"&amp;requeueOnError=",encodeURIComponent(this.settings.requeue_on_error),"&amp;httpSuccess=",encodeURIComponent(a),"&amp;assumeSuccessTimeout=",encodeURIComponent(this.settings.assume_success_timeout),"&amp;params=",encodeURIComponent(b),"&amp;filePostName=",encodeURIComponent(this.settings.file_post_name),"&amp;fileTypes=",encodeURIComponent(this.settings.file_types),"&amp;fileTypesDescription=",encodeURIComponent(this.settings.file_types_description),"&amp;fileSizeLimit=",encodeURIComponent(this.settings.file_size_limit),"&amp;fileUploadLimit=",encodeURIComponent(this.settings.file_upload_limit),"&amp;fileQueueLimit=",encodeURIComponent(this.settings.file_queue_limit),"&amp;debugEnabled=",encodeURIComponent(this.settings.debug_enabled),"&amp;buttonImageURL=",encodeURIComponent(this.settings.button_image_url),"&amp;buttonWidth=",encodeURIComponent(this.settings.button_width),"&amp;buttonHeight=",encodeURIComponent(this.settings.button_height),"&amp;buttonText=",encodeURIComponent(this.settings.button_text),"&amp;buttonTextTopPadding=",encodeURIComponent(this.settings.button_text_top_padding),"&amp;buttonTextLeftPadding=",encodeURIComponent(this.settings.button_text_left_padding),"&amp;buttonTextStyle=",encodeURIComponent(this.settings.button_text_style),"&amp;buttonAction=",encodeURIComponent(this.settings.button_action),"&amp;buttonDisabled=",encodeURIComponent(this.settings.button_disabled),"&amp;buttonCursor=",encodeURIComponent(this.settings.button_cursor)].join("")};SWFUpload.prototype.getMovieElement=function(){if(this.movieElement==undefined){this.movieElement=document.getElementById(this.movieName)}if(this.movieElement===null){throw"Could not find Flash element"}return this.movieElement};SWFUpload.prototype.buildParamString=function(){var c=this.settings.post_params;var b=[];if(typeof(c)==="object"){for(var a in c){if(c.hasOwnProperty(a)){b.push(encodeURIComponent(a.toString())+"="+encodeURIComponent(c[a].toString()))}}}return b.join("&amp;")};SWFUpload.prototype.destroy=function(){try{this.cancelUpload(null,false);var a=null;a=this.getMovieElement();if(a&&typeof(a.CallFunction)==="unknown"){for(var c in a){try{if(typeof(a[c])==="function"){a[c]=null}}catch(e){}}try{a.parentNode.removeChild(a)}catch(b){}}window[this.movieName]=null;SWFUpload.instances[this.movieName]=null;delete SWFUpload.instances[this.movieName];this.movieElement=null;this.settings=null;this.customSettings=null;this.eventQueue=null;this.movieName=null;return true}catch(d){return false}};SWFUpload.prototype.displayDebugInfo=function(){this.debug(["---SWFUpload Instance Info---\n","Version: ",SWFUpload.version,"\n","Movie Name: ",this.movieName,"\n","Settings:\n","\t","upload_url:               ",this.settings.upload_url,"\n","\t","flash_url:                ",this.settings.flash_url,"\n","\t","use_query_string:         ",this.settings.use_query_string.toString(),"\n","\t","requeue_on_error:         ",this.settings.requeue_on_error.toString(),"\n","\t","http_success:             ",this.settings.http_success.join(", "),"\n","\t","assume_success_timeout:   ",this.settings.assume_success_timeout,"\n","\t","file_post_name:           ",this.settings.file_post_name,"\n","\t","post_params:              ",this.settings.post_params.toString(),"\n","\t","file_types:               ",this.settings.file_types,"\n","\t","file_types_description:   ",this.settings.file_types_description,"\n","\t","file_size_limit:          ",this.settings.file_size_limit,"\n","\t","file_upload_limit:        ",this.settings.file_upload_limit,"\n","\t","file_queue_limit:         ",this.settings.file_queue_limit,"\n","\t","debug:                    ",this.settings.debug.toString(),"\n","\t","prevent_swf_caching:      ",this.settings.prevent_swf_caching.toString(),"\n","\t","button_placeholder_id:    ",this.settings.button_placeholder_id.toString(),"\n","\t","button_placeholder:       ",(this.settings.button_placeholder?"Set":"Not Set"),"\n","\t","button_image_url:         ",this.settings.button_image_url.toString(),"\n","\t","button_width:             ",this.settings.button_width.toString(),"\n","\t","button_height:            ",this.settings.button_height.toString(),"\n","\t","button_text:              ",this.settings.button_text.toString(),"\n","\t","button_text_style:        ",this.settings.button_text_style.toString(),"\n","\t","button_text_top_padding:  ",this.settings.button_text_top_padding.toString(),"\n","\t","button_text_left_padding: ",this.settings.button_text_left_padding.toString(),"\n","\t","button_action:            ",this.settings.button_action.toString(),"\n","\t","button_disabled:          ",this.settings.button_disabled.toString(),"\n","\t","custom_settings:          ",this.settings.custom_settings.toString(),"\n","Event Handlers:\n","\t","swfupload_loaded_handler assigned:  ",(typeof this.settings.swfupload_loaded_handler==="function").toString(),"\n","\t","file_dialog_start_handler assigned: ",(typeof this.settings.file_dialog_start_handler==="function").toString(),"\n","\t","file_queued_handler assigned:       ",(typeof this.settings.file_queued_handler==="function").toString(),"\n","\t","file_queue_error_handler assigned:  ",(typeof this.settings.file_queue_error_handler==="function").toString(),"\n","\t","upload_start_handler assigned:      ",(typeof this.settings.upload_start_handler==="function").toString(),"\n","\t","upload_progress_handler assigned:   ",(typeof this.settings.upload_progress_handler==="function").toString(),"\n","\t","upload_error_handler assigned:      ",(typeof this.settings.upload_error_handler==="function").toString(),"\n","\t","upload_success_handler assigned:    ",(typeof this.settings.upload_success_handler==="function").toString(),"\n","\t","upload_complete_handler assigned:   ",(typeof this.settings.upload_complete_handler==="function").toString(),"\n","\t","debug_handler assigned:             ",(typeof this.settings.debug_handler==="function").toString(),"\n"].join(""))};SWFUpload.prototype.addSetting=function(b,c,a){if(c==undefined){return(this.settings[b]=a)}else{return(this.settings[b]=c)}};SWFUpload.prototype.getSetting=function(a){if(this.settings[a]!=undefined){return this.settings[a]}return""};SWFUpload.prototype.callFlash=function(functionName,argumentArray){argumentArray=argumentArray||[];var movieElement=this.getMovieElement();var returnValue,returnString;try{returnString=movieElement.CallFunction('<invoke name="'+functionName+'" returntype="javascript">'+__flash__argumentsToXML(argumentArray,0)+"</invoke>");returnValue=eval(returnString)}catch(ex){throw"Call to "+functionName+" failed"}if(returnValue!=undefined&&typeof returnValue.post==="object"){returnValue=this.unescapeFilePostParams(returnValue)}return returnValue};SWFUpload.prototype.selectFile=function(){this.callFlash("SelectFile")};SWFUpload.prototype.selectFiles=function(){this.callFlash("SelectFiles")};SWFUpload.prototype.startUpload=function(a){this.callFlash("StartUpload",[a])};SWFUpload.prototype.cancelUpload=function(a,b){if(b!==false){b=true}this.callFlash("CancelUpload",[a,b])};SWFUpload.prototype.stopUpload=function(){this.callFlash("StopUpload")};SWFUpload.prototype.getStats=function(){return this.callFlash("GetStats")};SWFUpload.prototype.setStats=function(a){this.callFlash("SetStats",[a])};SWFUpload.prototype.getFile=function(a){if(typeof(a)==="number"){return this.callFlash("GetFileByIndex",[a])}else{return this.callFlash("GetFile",[a])}};SWFUpload.prototype.addFileParam=function(a,b,c){return this.callFlash("AddFileParam",[a,b,c])};SWFUpload.prototype.removeFileParam=function(a,b){this.callFlash("RemoveFileParam",[a,b])};SWFUpload.prototype.setUploadURL=function(a){this.settings.upload_url=a.toString();this.callFlash("SetUploadURL",[a])};SWFUpload.prototype.setPostParams=function(a){this.settings.post_params=a;this.callFlash("SetPostParams",[a])};SWFUpload.prototype.addPostParam=function(a,b){this.settings.post_params[a]=b;this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.removePostParam=function(a){delete this.settings.post_params[a];this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.setFileTypes=function(a,b){this.settings.file_types=a;this.settings.file_types_description=b;this.callFlash("SetFileTypes",[a,b])};SWFUpload.prototype.setFileSizeLimit=function(a){this.settings.file_size_limit=a;this.callFlash("SetFileSizeLimit",[a])};SWFUpload.prototype.setFileUploadLimit=function(a){this.settings.file_upload_limit=a;this.callFlash("SetFileUploadLimit",[a])};SWFUpload.prototype.setFileQueueLimit=function(a){this.settings.file_queue_limit=a;this.callFlash("SetFileQueueLimit",[a])};SWFUpload.prototype.setFilePostName=function(a){this.settings.file_post_name=a;this.callFlash("SetFilePostName",[a])};SWFUpload.prototype.setUseQueryString=function(a){this.settings.use_query_string=a;this.callFlash("SetUseQueryString",[a])};SWFUpload.prototype.setRequeueOnError=function(a){this.settings.requeue_on_error=a;this.callFlash("SetRequeueOnError",[a])};SWFUpload.prototype.setHTTPSuccess=function(a){if(typeof a==="string"){a=a.replace(" ","").split(",")}this.settings.http_success=a;this.callFlash("SetHTTPSuccess",[a])};SWFUpload.prototype.setAssumeSuccessTimeout=function(a){this.settings.assume_success_timeout=a;this.callFlash("SetAssumeSuccessTimeout",[a])};SWFUpload.prototype.setDebugEnabled=function(a){this.settings.debug_enabled=a;this.callFlash("SetDebugEnabled",[a])};SWFUpload.prototype.setButtonImageURL=function(a){if(a==undefined){a=""}this.settings.button_image_url=a;this.callFlash("SetButtonImageURL",[a])};SWFUpload.prototype.setButtonDimensions=function(c,a){this.settings.button_width=c;this.settings.button_height=a;var b=this.getMovieElement();if(b!=undefined){b.style.width=c+"px";b.style.height=a+"px"}this.callFlash("SetButtonDimensions",[c,a])};SWFUpload.prototype.setButtonText=function(a){this.settings.button_text=a;this.callFlash("SetButtonText",[a])};SWFUpload.prototype.setButtonTextPadding=function(b,a){this.settings.button_text_top_padding=a;this.settings.button_text_left_padding=b;this.callFlash("SetButtonTextPadding",[b,a])};SWFUpload.prototype.setButtonTextStyle=function(a){this.settings.button_text_style=a;this.callFlash("SetButtonTextStyle",[a])};SWFUpload.prototype.setButtonDisabled=function(a){this.settings.button_disabled=a;this.callFlash("SetButtonDisabled",[a])};SWFUpload.prototype.setButtonAction=function(a){this.settings.button_action=a;this.callFlash("SetButtonAction",[a])};SWFUpload.prototype.setButtonCursor=function(a){this.settings.button_cursor=a;this.callFlash("SetButtonCursor",[a])};SWFUpload.prototype.queueEvent=function(b,c){if(c==undefined){c=[]}else{if(!(c instanceof Array)){c=[c]}}var a=this;if(typeof this.settings[b]==="function"){this.eventQueue.push(function(){this.settings[b].apply(this,c)});setTimeout(function(){a.executeNextEvent()},0)}else{if(this.settings[b]!==null){throw"Event handler "+b+" is unknown or is not a function"}}};SWFUpload.prototype.executeNextEvent=function(){var a=this.eventQueue?this.eventQueue.shift():null;if(typeof(a)==="function"){a.apply(this)}};SWFUpload.prototype.unescapeFilePostParams=function(c){var e=/[$]([0-9a-f]{4})/i;var f={};var d;if(c!=undefined){for(var a in c.post){if(c.post.hasOwnProperty(a)){d=a;var b;while((b=e.exec(d))!==null){d=d.replace(b[0],String.fromCharCode(parseInt("0x"+b[1],16)))}f[d]=c.post[a]}}c.post=f}return c};SWFUpload.prototype.testExternalInterface=function(){try{return this.callFlash("TestExternalInterface")}catch(a){return false}};SWFUpload.prototype.flashReady=function(){var a=this.getMovieElement();if(!a){this.debug("Flash called back ready but the flash movie can't be found.");return}this.cleanUp(a);this.queueEvent("swfupload_loaded_handler")};SWFUpload.prototype.cleanUp=function(a){try{if(this.movieElement&&typeof(a.CallFunction)==="unknown"){this.debug("Removing Flash functions hooks (this should only run in IE and should prevent memory leaks)");for(var c in a){try{if(typeof(a[c])==="function"){a[c]=null}}catch(b){}}}}catch(d){}window.__flash__removeCallback=function(e,f){try{if(e){e[f]=null}}catch(g){}}};SWFUpload.prototype.fileDialogStart=function(){this.queueEvent("file_dialog_start_handler")};SWFUpload.prototype.fileQueued=function(a){a=this.unescapeFilePostParams(a);this.queueEvent("file_queued_handler",a)};SWFUpload.prototype.fileQueueError=function(a,c,b){a=this.unescapeFilePostParams(a);this.queueEvent("file_queue_error_handler",[a,c,b])};SWFUpload.prototype.fileDialogComplete=function(b,c,a){this.queueEvent("file_dialog_complete_handler",[b,c,a])};SWFUpload.prototype.uploadStart=function(a){a=this.unescapeFilePostParams(a);this.queueEvent("return_upload_start_handler",a)};SWFUpload.prototype.returnUploadStart=function(a){var b;if(typeof this.settings.upload_start_handler==="function"){a=this.unescapeFilePostParams(a);b=this.settings.upload_start_handler.call(this,a)}else{if(this.settings.upload_start_handler!=undefined){throw"upload_start_handler must be a function"}}if(b===undefined){b=true}b=!!b;this.callFlash("ReturnUploadStart",[b])};SWFUpload.prototype.uploadProgress=function(a,c,b){a=this.unescapeFilePostParams(a);this.queueEvent("upload_progress_handler",[a,c,b])};SWFUpload.prototype.uploadError=function(a,c,b){a=this.unescapeFilePostParams(a);this.queueEvent("upload_error_handler",[a,c,b])};SWFUpload.prototype.uploadSuccess=function(b,a,c){b=this.unescapeFilePostParams(b);this.queueEvent("upload_success_handler",[b,a,c])};SWFUpload.prototype.uploadComplete=function(a){a=this.unescapeFilePostParams(a);this.queueEvent("upload_complete_handler",a)};SWFUpload.prototype.debug=function(a){this.queueEvent("debug_handler",a)};SWFUpload.prototype.debugMessage=function(c){if(this.settings.debug){var a,d=[];if(typeof c==="object"&&typeof c.name==="string"&&typeof c.message==="string"){for(var b in c){if(c.hasOwnProperty(b)){d.push(b+": "+c[b])}}a=d.join("\n")||"";d=a.split("\n");a="EXCEPTION: "+d.join("\nEXCEPTION: ");SWFUpload.Console.writeLine(a)}else{SWFUpload.Console.writeLine(c)}}};SWFUpload.Console={};SWFUpload.Console.writeLine=function(d){var b,a;try{b=document.getElementById("SWFUpload_Console");if(!b){a=document.createElement("form");document.getElementsByTagName("body")[0].appendChild(a);b=document.createElement("textarea");b.id="SWFUpload_Console";b.style.fontFamily="monospace";b.setAttribute("wrap","off");b.wrap="off";b.style.overflow="auto";b.style.width="700px";b.style.height="350px";b.style.margin="5px";a.appendChild(b)}b.value+=d+"\n";b.scrollTop=b.scrollHeight-b.clientHeight}catch(c){alert("Exception: "+c.name+" Message: "+c.message)}};

/**
 * 注意：
 * 1、swfupload创建后，会把绑定的dom节点替换掉（"button_placeholder_id" : settings.id）
 * 2、$().data()不能作用域object标签
 * 3、解决swfupload改变display属性后flash重新加载的问题(chome,safari内核的所有浏览器)
 * 4、解决上传成功回调方法还没调用，就关闭上传窗口bug
 */
(function($) {
	
	// 附件表记录id
	var attachFileIds = [];
	// 业务表记录id
	var busTableRecordId;
	var rootPath;
	
	var methods = {
		"init" : function(options) {
			var settings = $.extend({
				"btnAddName": "上传附件",
				"btnDelName": "删除附件",
				"url": "/UploadServlet?uploadPath=myfile&busRecordId=&uploadUserId=-1",
				"swf": "swfupload/swfupload.swf",
				"rootPath": "/ptzhsp",
				"fileSize": "5MB", // 限制文件大小，单位KB,MB,GB
				"fileTypes": "*.*", // 默认所有文件
				"fileTypesDesc": "所有文件", // 允许文件类型描述
				"debug": false
			}, options || {});
			
			rootPath = settings.rootPath;
			return this.each(function() {
				// 创建上传界面组件
				var $this = $(this).hide();
				// 构建界面DOM
				_buildDom4Panel($this);
				//swfu.selectFile();
				window.appendixdowload = function(fileId) {
					if (fileId) {
						window.open(settings.rootPath + "/DownLoadServlet?fileId="+fileId);
					}
				}
			});
			
			function _buildDom4Panel($thiz) {
				if (settings.attachFileIds) {
					attachFileIds = settings.attachFileIds.split(",");
				}
				busTableRecordId = settings.recordId || -1;

				var $tablePanel = $("<table></table>").attr({
					"id": settings.id
				});
				
				$thiz.after($tablePanel);
				$tablePanel.datagrid({
					"rownumbers": true,
					"striped": true,
					"fit": settings.fit,
					"width": settings.width,
					"height": settings.height,
					"idField": "FILE_ID",
				    "columns": [[{
				    	"field": "ck" ,
				    	"checkbox": true
				    }, {
				    	"field": "FILE_NAME",
				    	"title": '文件名称',
				    	"width": 140
			    	}, {
				    	"field": "FILE_TYPE",
				    	"title": '文件类型',
				    	"width": 80
			    	}, {
				    	"field": "FILE_SIZE",
				    	"title": '文件大小',
				    	"width": 80
			    	}, {
				    	"field": "CREATE_TIME",
				    	"title": '上传时间',
				    	"width": 110
			    	}, {
			    		"field": "op", 
			    		"title": "下载",
			    		"width": 60,
						"formatter": function(value, row, index) {
							return '<a target="_blank" href="'+settings.rootPath + '/DownLoadServlet?fileId='+row.FILE_ID+'"><img title="下载" src="' + settings.rootPath + '/plug-in/jquery/plugin/jqueryUpload/images/btn-download.png" style="cursor: pointer;"/></a>';
						}
			    	}]],
			    	"onBeforeLoad": function(param) {
			    		if (attachFileIds.length > 0) { // 直接通过附件id查找附件，如{id,id,id,...} 已存在关联附件的情况
			    			$(this).datagrid("options").url = settings.rootPath + "/fileAttachController.do?datagrid&attachFileIds=" + attachFileIds + "&isForJquery=true";
			    		} else { // 业务表单id和业务表名查找附件 开始不存在
			    			$(this).datagrid("options").url = settings.rootPath + "/fileAttachController.do?datagrid&BUS_TABLERECORDID=" + busTableRecordId + "&BUS_TABLENAME=" + settings.tableName + "&isForJquery=true";
			    		}
			    	},
			    	"onLoadSuccess": function(data) {
			    		var arr = [];
			    		var paths = [];
			    		$.each(data.rows, function(idx, row) {
			    			arr.push(row.FILE_ID);
			    			paths.push(row.FILE_PATH);
			    		});
			    		var params = {
			    			"attachFileIdArray": arr
			    		};
			    		attachFileIds = arr;
						$("#attachFileIdArray").val($.param(params, true));
						if ($("input[name='attachFileIds']").length > 0) {
							$("input[name='attachFileIds']").val(arr.join(","));
						} else {
							alert("表单上缺失attachFileIds字段!");
						}
						if (typeof settings.onListLoaded === "function") {
							settings.onListLoaded(paths.join(","));
						}
			    	},
			    	"toolbar":  [{
			    		"text": settings.btnAddName,
						"iconCls": "icon-add",
						"handler": function() {
							if (!settings.isMultiple && $tablePanel.datagrid("getRows").length > 0) {
								$.messager.alert("提示", "只能上传一个附件!");
								return;
							}
							if ($thiz.data("$dialog")) {
								$thiz.data("$dialog").closest(".window").css("z-index", "20001").removeClass("uploadPanel-hide");
								$thiz.data("$dialogMask").css("z-index", "20000").show();
								return;
							}
							var $dialog = $("<div><table></table></div>").dialog({
							    "title": "上传附件窗口",
							    "width": 600,
							    "height": 350,
							    "modal": true,
							    "shadow": false,
							    "closable": false,
							    "buttons" : [{
							        "text" : "关闭",
							        "handler" : function() {
							        	$tablePanel.datagrid("load");
							        	$table.datagrid("loadData", { total: 0, rows: [] });
							        	$dialog.closest(".window").addClass("uploadPanel-hide"); // 为兼容ie9以上的写法
							        	$thiz.data("$dialog", $dialog);
							        	//为遮罩配置id，保证选择唯一
							        	var id = $thiz.attr("id");
							        	$(".window-mask:visible:last").attr("id", id + "_mask").hide(); 
							        	$thiz.data("$dialogMask", $("#" + id + "_mask"));
							        	if ($thiz.data("$dialogZindex")) { // 记录第一次z-index，稍后还原
							        		$dialog.closest(".window").css("z-index", $thiz.data("$dialogZindex"));
							        		$thiz.data("$dialogMask").css("z-index", $thiz.data("$dialogMaskZindex"));
							        	} else {
							        		$thiz.data("$dialogZindex", $dialog.closest(".window").css("z-index"));
							        		$thiz.data("$dialogMaskZindex", $thiz.data("$dialogMask").css("z-index"));
							        	}
							        }
							    }]
							}); 
							
							var $table = $("<table></table>");
							$dialog.find("table").replaceWith($table);
							$table.datagrid({
								"rownumbers": true,
								"striped": true,
								"fit": true,
								"border": false,
								"idField": "fileId",
							    "columns": [[{
							    	"field": "ck" ,
							    	"checkbox": true
							    }, {
							    	"field": "fileName",
							    	"title": '文件名称',
							    	"width": 220
						    	}, {
							    	"field": "fileType",
							    	"title": '文件类型',
							    	"width": 80
						    	}, {
							    	"field": "fileSize",
							    	"title": '文件大小',
							    	"width": 80,
							    	"formatter": function (value) {
										var fileSize = Math.round(value * 100 / 1024) / 100;
										var suffix   = 'KB';
										if (fileSize >= 1024) {
											fileSize = Math.round(fileSize * 100 / 1024) / 100;
											suffix   = 'MB';
										}
										var fileSizeParts = fileSize.toString().split('.');
										fileSize = fileSizeParts[0];
										if (fileSizeParts.length > 1) {
											fileSize += '.' + fileSizeParts[1].substr(0,2);
										}
										return fileSize += suffix;
							    	}
						    	}, {
							    	"field": "fileProgress",
							    	"title": '进度',
							    	"width": 120,
							    	"formatter": function (value) {
									    return '<div style="text-align:center; width:100%;border:1px solid #ccc">' +
								    			'<div style="height:20;width:' + (value ? value : 0) + '%;background:#228B22;color:#fff"><font color="black">' + (value ? value : 0) + '%' + '</font></div>' +
								    			'</div>';
							    	}
						    	}]],
						    	"toolbar":  [{
						    		"text": "添加文件",
									"iconCls": "icon-add",
									"handler": function() {
									}
						    	}]
							}); 
							var $td = $table.datagrid("getPanel").find("td").first();
							$td.append($("<span></span>").attr({
								"id": "swfuploadholder"
							}));
							// 创建swfupload.swf组件
							var swfu = new SWFUpload({
								"upload_url" : settings.rootPath + "/UploadServlet?uploadPath=myfile&busTableName=" + settings.tableName + "&uploadUserId=" + settings.userId,
								// 上传文件设置
								"file_size_limit" : settings.fileSize,
								"file_types" : settings.fileTypes,
								"file_types_description" : settings.fileTypesDesc,
								"file_upload_limit" : 0,
								"file_queue_limit": settings.isMultiple ? 0 : 1, 
								// 按钮设置
								"button_image_url" : null,
								"button_placeholder_id" : "swfuploadholder",
								"button_width" : 72,
								"button_height" : 25,
								"button_text" : null,
								"button_text_style" : null,
								"button_text_top_padding" : 0,
								"button_text_left_padding" : 0,
								"button_action": settings.isMultiple ? SWFUpload.BUTTON_ACTION.SELECT_FILES : SWFUpload.BUTTON_ACTION.SELECT_FILE,
								"button_window_mode" : SWFUpload.WINDOW_MODE.TRANSPARENT,
								"button_cursor" : SWFUpload.CURSOR.HAND,
								// Flash Settings
								"flash_url" : settings.swf,
								// 事件处理函数
								"file_dialog_start_handler" : handlers.fileDialogStartHandler, 
								"file_dialog_complete_handler" : handlers.fileDialogCompleteHandler,
								"file_queued_handler" : handlers.fileQueuedHandler,
								"file_queue_error_handler" : handlers.fileQueueErrorHandler,
								"upload_progress_handler" : handlers.uploadProgressHandler,
								"upload_success_handler" : handlers.uploadSuccessHandler,
								"upload_error_handler" : handlers.uploadErrorHandler,
								"upload_complete_handler" : handlers.uploadCompleteHandler
							});
							
							$("#" + swfu.movieName).css({
								"position" : "absolute",
								"z-index": 1,
								"top": 2,
								"left": 2
							});
							$(swfu).data("$table", $table);
							$(swfu).data("settings", settings);
							$thiz.data("swfu", swfu);
							$thiz.data("listTable", $tablePanel);
						}
					},'-',{
						"text": settings.btnDelName,
						"iconCls": "icon-cancel",
						"handler": function() {
							var rows = $tablePanel.datagrid("getChecked");
							if (rows.length) {
								$.messager.confirm('确认','您确定删除这些文件吗?',function(r) {  
								    if (r) {  
										var url = settings.rootPath + "/fileAttachController.do?cmsMultiDel" 
										var ids = [];
										$.each(rows, function(idx, row) {
											ids.push(row.FILE_ID);
										});
										var params = {
											"selectColNames":  ids
										};
										$.post(url, $.param(params, true), function(r) {
											if (r.success) {
												$tablePanel.datagrid("load");
												$tablePanel.datagrid("clearSelections"); // 清除之前选择的记录，easyui的bug：过去选中记录取消选择前继续保留
											} else {
												$.messager.alert("提示", "删除附件失败!");
											}
										}, "json");  
										if (!settings.isMultiple) {
											$thiz.data("swfu").setFileUploadLimit(0);
										}
								    }  
								});  
							} else {
								$.messager.alert("提示", "请勾选要删除的文件!");
							}
						}
					}, "-", {
						"text": "<span style='color:red'>" + (settings.tip || "") + "</span>"
					}] // toolbar
				});// datagrid
			}
		},
		"deleteAll": function() {
			return this.each(function() {
				var _$tablePanel = $(this).data("listTable");
				if(_$tablePanel){
					_$tablePanel.datagrid("checkAll");
				
					var swfu = $(this).data("swfu");
					if (!$(swfu).data("settings").isMultiple) {
						swfu.setFileUploadLimit(0);
					}
					var rows = _$tablePanel.datagrid("getChecked");
					if (rows.length) {
						var url = rootPath + "/fileAttachController.do?cmsMultiDel" 
						var ids = [];
						$.each(rows, function(idx, row) {
							ids.push(row.FILE_ID);
						});
						var params = {
							"selectColNames":  ids
						};
						$.post(url, $.param(params, true), function(r) {
							if (r.success) {
								_$tablePanel.datagrid("load");
								_$tablePanel.datagrid("clearSelections"); // 清除之前选择的记录，easyui的bug：过去选中记录取消选择前继续保留
							} else {
								$.messager.alert("提示", "删除附件失败");
							}
						}, "json");  
					}
				}
			});
		},
		"setTypes": function(_jsonParam) {
			return this.each(function() {
				var sf = $(this).data("swfu");
				sf.setFileTypes(_jsonParam.fileType, _jsonParam.fileTypeDesc);
			});
		},
		"setUploadLimit": function(_num) {
			return this.each(function() {
				var sf = $(this).data("swfu");
				sf.setFileQueueLimit(_num);
				sf.setButtonAction(_num == 0 ? SWFUpload.BUTTON_ACTION.SELECT_FILES : SWFUpload.BUTTON_ACTION.SELECT_FILE);
			});
		}
	};
	
	var handlers = {
		/**
		 * 功能：文件选择框关闭后事件处理函数
		 * 参数：numFilesSelected 选择的文件数量
		 * 		numFilesQueued 队列中文件数量
		 * 		totalNumFilesQueued 队列中的全部文件数量
		 * 备注：若要立即上传文件，可以在此调用this.startUpload()
		 */
		"fileDialogCompleteHandler": function(numFilesSelected, numFilesQueued, totalNumFilesQueued) {
			try {
				if (numFilesQueued > 0) {
//					this.startUpload();
				}
			} catch (ex) {
				this.debug(ex);
			}
		},

		/**
		 * 功能：文件成功加入队列事件处理函数（N个文件，触发N次）
		 * 参数：
		 * file = {
		 *     id : string, // SWFUpload控制的文件的id,通过指定该id可启动此文件的上传、退出上传等
		 *     index : number, // 文件在选定文件队列（包括出错、退出、排队的文件）中的索引，getFile可使用此索引 
		 *     name : string, // 文件名，不包括文件的路径。
		 *     size : number, // 文件字节数 
		 *     type : string, // 客户端操作系统设置的文件类型 
		 *     creationdate : Date, // 文件的创建时间 
		 *     modificationdate :Date, // 文件的最后修改时间 
		 *     filestatus : number //文件的当前状态，对应的状态代码可查看SWFUpload.FILE_STATUS }
		 * } 
		 */
		"fileQueuedHandler" : function(file) {
			if (file.name.length > 60) {
				alert("文件名太长!");
				return;
			}
			this.startUpload();
			var row = {
				"fileId": file.id,
				"fileName": file.name,
				"fileType": file.type.substring(1),
				"fileSize": file.size,
				"fileProgress": 0
			};
			var $table = $(this).data("$table");
			$table.datagrid("appendRow", row);
		},

		/**
		 * 功能：文件加入队列错误时间处理函数（N个文件，触发N次）
		 * 参数：file 上传文件对象
		 * 		errorCode 对应SWFUpload.QUEUE_ERROR常量 
		 *      message 返回的消息
		 */
		"fileQueueErrorHandler" : function(file, errorCode, message) {
			try {
				switch (errorCode) {
				case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
					alert("只能上传一个附件！");
					break;
				case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
					imageName = "<font color='red'>文件大小为0</font>";
					break;
				case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					alert("文件大小超过限制！");
					imageName = "<font color='red'>文件大小超过限制</font>";
					break;
				case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					alert("文件类型不符合要求！");
					break;
				default:
					alert("文件上传队列错误，请联系站点管理员！");
					break;
				}
			} catch (ex) {
				this.debug(ex);
			}
		},
		
		/**
		 * 功能：上传进度事件处理函数，flash控件会定时触发，linux下只会等文件上传完毕后触发（bug）
		 * 参数：file 上传文件
		 * 		bytesCompeted 完成上传的字节大小
		 * 		bytesTotal 总共字节大小 
		 */
		"uploadProgressHandler": function(file, bytesCompeted, bytesTotal) {
			var $table = $(this).data("$table");
			$table.datagrid("selectRecord", file.id);
			var row = $table.datagrid("getSelected");
			var progress = Math.round((bytesCompeted / bytesTotal) * 100);
			row.fileProgress = progress >= 1 ? progress - 1 : progress; // 故意进度减去1，在uploadSuccessHandler处理完成进度，保证等待回调函数调到。
			$table.datagrid("refreshRow", $table.datagrid("getRowIndex", row));
		},

		/**
		 * 功能：上传成功事件处理函数
		 * 参数：file
		 * 		serverData
		 * 		response
		 */
		"uploadSuccessHandler" : function(file, serverData, response) {
			try {
				var $table = $(this).data("$table");
				var _resp = $.parseJSON(serverData);

				/**********************************************/
				//上传的文件类型/文件路径错误处理
				if(_resp && !_resp.success){
					alert(_resp.message);
					//获取当前有行
					var row = $table.datagrid("getSelected");
					var rowIndex = $table.datagrid('getRowIndex', row);
					//删除错误的列
					$table.datagrid('deleteRow', rowIndex);
					//删除后重新加载下
					$table.datagrid('reload');
				}
				/**********************************************/

				$table.datagrid("selectRecord", file.id);
				var r = $table.datagrid("getSelected");
				$table.datagrid("clearSelections");
				r.fileUploadId = _resp.fileId;
				r.fileProgress = 100;
				attachFileIds.push(r.fileUploadId);
				$table.datagrid("refreshRow", $table.datagrid("getRowIndex", r));
				var settings = $(this).data("settings");
				if (!settings.isMultiple) {
					this.setFileUploadLimit(1);
				}
			} catch (ex) {
				this.debug(ex);
			}
		},

		/**
		 * 功能：上传错误事件处理函数
		 * 参数：file
		 * 		serverData
		 * 		response
		 */
		"uploadErrorHandler" : function(file, errorCode, message) {
			var imageName = "<font color='red'>文件上传出错!</font>";
			var progress;
			try {
				switch (errorCode) {
				case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
					try {
					} catch (ex1) {
						this.debug(ex1);
					}
					break;
				case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
					try {
					} catch (ex2) {
						this.debug(ex2);
					}
				case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
					imageName = "<font color='red'>文件大小超过限制!</font>";
					break;
				default:
					break;
				}
				$("#" + $(this).data("upload").id + "_fileName").html(imageName)
			} catch (ex3) {
				this.debug(ex3);
			}
		},
		
		/**
		 * 功能：上传完成事件处理函数
		 * 参数：file
		 */
		"uploadCompleteHandler" : function(file) {
			try {
				// 上传队列中的下一个任务自动开始
				if (this.getStats().files_queued > 0) {
					this.startUpload();
				}
			} catch (ex) {
				this.debug(ex);
			}
		}
	}
	
	$.fn.uploadPanel = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if ($.isPlainObject(method) || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error("无此方法");
		}
	}
})(jQuery);