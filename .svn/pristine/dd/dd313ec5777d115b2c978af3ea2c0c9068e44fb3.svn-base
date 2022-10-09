var SWFUpload;if(SWFUpload==undefined){SWFUpload=function(a){this.initSWFUpload(a)}}SWFUpload.prototype.initSWFUpload=function(b){try{this.customSettings={};this.settings=b;this.eventQueue=[];this.movieName="SWFUpload_"+SWFUpload.movieCount++;this.movieElement=null;SWFUpload.instances[this.movieName]=this;this.initSettings();this.loadFlash();this.displayDebugInfo()}catch(a){delete SWFUpload.instances[this.movieName];throw a}};SWFUpload.instances={};SWFUpload.movieCount=0;SWFUpload.version="2.2.0 2009-03-25";SWFUpload.QUEUE_ERROR={QUEUE_LIMIT_EXCEEDED:-100,FILE_EXCEEDS_SIZE_LIMIT:-110,ZERO_BYTE_FILE:-120,INVALID_FILETYPE:-130};SWFUpload.UPLOAD_ERROR={HTTP_ERROR:-200,MISSING_UPLOAD_URL:-210,IO_ERROR:-220,SECURITY_ERROR:-230,UPLOAD_LIMIT_EXCEEDED:-240,UPLOAD_FAILED:-250,SPECIFIED_FILE_ID_NOT_FOUND:-260,FILE_VALIDATION_FAILED:-270,FILE_CANCELLED:-280,UPLOAD_STOPPED:-290};SWFUpload.FILE_STATUS={QUEUED:-1,IN_PROGRESS:-2,ERROR:-3,COMPLETE:-4,CANCELLED:-5};SWFUpload.BUTTON_ACTION={SELECT_FILE:-100,SELECT_FILES:-110,START_UPLOAD:-120};SWFUpload.CURSOR={ARROW:-1,HAND:-2};SWFUpload.WINDOW_MODE={WINDOW:"window",TRANSPARENT:"transparent",OPAQUE:"opaque"};SWFUpload.completeURL=function(a){if(typeof(a)!=="string"||a.match(/^https?:\/\//i)||a.match(/^\//)){return a}var c=window.location.protocol+"//"+window.location.hostname+(window.location.port?":"+window.location.port:"");var b=window.location.pathname.lastIndexOf("/");if(b<=0){path="/"}else{path=window.location.pathname.substr(0,b)+"/"}return path+a};SWFUpload.prototype.initSettings=function(){this.ensureDefault=function(b,a){this.settings[b]=(this.settings[b]==undefined)?a:this.settings[b]};this.ensureDefault("upload_url","");this.ensureDefault("preserve_relative_urls",false);this.ensureDefault("file_post_name","Filedata");this.ensureDefault("post_params",{});this.ensureDefault("use_query_string",false);this.ensureDefault("requeue_on_error",false);this.ensureDefault("http_success",[]);this.ensureDefault("assume_success_timeout",0);this.ensureDefault("file_types","*.*");this.ensureDefault("file_types_description","All Files");this.ensureDefault("file_size_limit",0);this.ensureDefault("file_upload_limit",0);this.ensureDefault("file_queue_limit",0);this.ensureDefault("flash_url","swfupload.swf");this.ensureDefault("prevent_swf_caching",true);this.ensureDefault("button_image_url","");this.ensureDefault("button_width",1);this.ensureDefault("button_height",1);this.ensureDefault("button_text","");this.ensureDefault("button_text_style","color: #000000; font-size: 16pt;");this.ensureDefault("button_text_top_padding",0);this.ensureDefault("button_text_left_padding",0);this.ensureDefault("button_action",SWFUpload.BUTTON_ACTION.SELECT_FILES);this.ensureDefault("button_disabled",false);this.ensureDefault("button_placeholder_id","");this.ensureDefault("button_placeholder",null);this.ensureDefault("button_cursor",SWFUpload.CURSOR.ARROW);this.ensureDefault("button_window_mode",SWFUpload.WINDOW_MODE.WINDOW);this.ensureDefault("debug",false);this.settings.debug_enabled=this.settings.debug;this.settings.return_upload_start_handler=this.returnUploadStart;this.ensureDefault("swfupload_loaded_handler",null);this.ensureDefault("file_dialog_start_handler",null);this.ensureDefault("file_queued_handler",null);this.ensureDefault("file_queue_error_handler",null);this.ensureDefault("file_dialog_complete_handler",null);this.ensureDefault("upload_start_handler",null);this.ensureDefault("upload_progress_handler",null);this.ensureDefault("upload_error_handler",null);this.ensureDefault("upload_success_handler",null);this.ensureDefault("upload_complete_handler",null);this.ensureDefault("debug_handler",this.debugMessage);this.ensureDefault("custom_settings",{});this.customSettings=this.settings.custom_settings;if(!!this.settings.prevent_swf_caching){this.settings.flash_url=this.settings.flash_url+(this.settings.flash_url.indexOf("?")<0?"?":"&")+"preventswfcaching="+new Date().getTime()}if(!this.settings.preserve_relative_urls){this.settings.upload_url=SWFUpload.completeURL(this.settings.upload_url);this.settings.button_image_url=SWFUpload.completeURL(this.settings.button_image_url)}delete this.ensureDefault};SWFUpload.prototype.loadFlash=function(){var a,b;if(document.getElementById(this.movieName)!==null){throw"ID "+this.movieName+" is already in use. The Flash Object could not be added"}a=document.getElementById(this.settings.button_placeholder_id)||this.settings.button_placeholder;if(a==undefined){throw"Could not find the placeholder element: "+this.settings.button_placeholder_id}b=document.createElement("div");b.innerHTML=this.getFlashHTML();a.parentNode.replaceChild(b.firstChild,a);if(window[this.movieName]==undefined){window[this.movieName]=this.getMovieElement()}};SWFUpload.prototype.getFlashHTML=function(){return['<object id="',this.movieName,'" type="application/x-shockwave-flash" data="',this.settings.flash_url,'" width="',this.settings.button_width,'" height="',this.settings.button_height,'" class="swfupload">','<param name="wmode" value="',this.settings.button_window_mode,'" />','<param name="movie" value="',this.settings.flash_url,'" />','<param name="quality" value="high" />','<param name="menu" value="false" />','<param name="allowScriptAccess" value="always" />','<param name="flashvars" value="'+this.getFlashVars()+'" />',"</object>"].join("")};SWFUpload.prototype.getFlashVars=function(){var b=this.buildParamString();var a=this.settings.http_success.join(",");return["movieName=",encodeURIComponent(this.movieName),"&amp;uploadURL=",encodeURIComponent(this.settings.upload_url),"&amp;useQueryString=",encodeURIComponent(this.settings.use_query_string),"&amp;requeueOnError=",encodeURIComponent(this.settings.requeue_on_error),"&amp;httpSuccess=",encodeURIComponent(a),"&amp;assumeSuccessTimeout=",encodeURIComponent(this.settings.assume_success_timeout),"&amp;params=",encodeURIComponent(b),"&amp;filePostName=",encodeURIComponent(this.settings.file_post_name),"&amp;fileTypes=",encodeURIComponent(this.settings.file_types),"&amp;fileTypesDescription=",encodeURIComponent(this.settings.file_types_description),"&amp;fileSizeLimit=",encodeURIComponent(this.settings.file_size_limit),"&amp;fileUploadLimit=",encodeURIComponent(this.settings.file_upload_limit),"&amp;fileQueueLimit=",encodeURIComponent(this.settings.file_queue_limit),"&amp;debugEnabled=",encodeURIComponent(this.settings.debug_enabled),"&amp;buttonImageURL=",encodeURIComponent(this.settings.button_image_url),"&amp;buttonWidth=",encodeURIComponent(this.settings.button_width),"&amp;buttonHeight=",encodeURIComponent(this.settings.button_height),"&amp;buttonText=",encodeURIComponent(this.settings.button_text),"&amp;buttonTextTopPadding=",encodeURIComponent(this.settings.button_text_top_padding),"&amp;buttonTextLeftPadding=",encodeURIComponent(this.settings.button_text_left_padding),"&amp;buttonTextStyle=",encodeURIComponent(this.settings.button_text_style),"&amp;buttonAction=",encodeURIComponent(this.settings.button_action),"&amp;buttonDisabled=",encodeURIComponent(this.settings.button_disabled),"&amp;buttonCursor=",encodeURIComponent(this.settings.button_cursor)].join("")};SWFUpload.prototype.getMovieElement=function(){if(this.movieElement==undefined){this.movieElement=document.getElementById(this.movieName)}if(this.movieElement===null){throw"Could not find Flash element"}return this.movieElement};SWFUpload.prototype.buildParamString=function(){var c=this.settings.post_params;var b=[];if(typeof(c)==="object"){for(var a in c){if(c.hasOwnProperty(a)){b.push(encodeURIComponent(a.toString())+"="+encodeURIComponent(c[a].toString()))}}}return b.join("&amp;")};SWFUpload.prototype.destroy=function(){try{this.cancelUpload(null,false);var a=null;a=this.getMovieElement();if(a&&typeof(a.CallFunction)==="unknown"){for(var c in a){try{if(typeof(a[c])==="function"){a[c]=null}}catch(e){}}try{a.parentNode.removeChild(a)}catch(b){}}window[this.movieName]=null;SWFUpload.instances[this.movieName]=null;delete SWFUpload.instances[this.movieName];this.movieElement=null;this.settings=null;this.customSettings=null;this.eventQueue=null;this.movieName=null;return true}catch(d){return false}};SWFUpload.prototype.displayDebugInfo=function(){this.debug(["---SWFUpload Instance Info---\n","Version: ",SWFUpload.version,"\n","Movie Name: ",this.movieName,"\n","Settings:\n","\t","upload_url:               ",this.settings.upload_url,"\n","\t","flash_url:                ",this.settings.flash_url,"\n","\t","use_query_string:         ",this.settings.use_query_string.toString(),"\n","\t","requeue_on_error:         ",this.settings.requeue_on_error.toString(),"\n","\t","http_success:             ",this.settings.http_success.join(", "),"\n","\t","assume_success_timeout:   ",this.settings.assume_success_timeout,"\n","\t","file_post_name:           ",this.settings.file_post_name,"\n","\t","post_params:              ",this.settings.post_params.toString(),"\n","\t","file_types:               ",this.settings.file_types,"\n","\t","file_types_description:   ",this.settings.file_types_description,"\n","\t","file_size_limit:          ",this.settings.file_size_limit,"\n","\t","file_upload_limit:        ",this.settings.file_upload_limit,"\n","\t","file_queue_limit:         ",this.settings.file_queue_limit,"\n","\t","debug:                    ",this.settings.debug.toString(),"\n","\t","prevent_swf_caching:      ",this.settings.prevent_swf_caching.toString(),"\n","\t","button_placeholder_id:    ",this.settings.button_placeholder_id.toString(),"\n","\t","button_placeholder:       ",(this.settings.button_placeholder?"Set":"Not Set"),"\n","\t","button_image_url:         ",this.settings.button_image_url.toString(),"\n","\t","button_width:             ",this.settings.button_width.toString(),"\n","\t","button_height:            ",this.settings.button_height.toString(),"\n","\t","button_text:              ",this.settings.button_text.toString(),"\n","\t","button_text_style:        ",this.settings.button_text_style.toString(),"\n","\t","button_text_top_padding:  ",this.settings.button_text_top_padding.toString(),"\n","\t","button_text_left_padding: ",this.settings.button_text_left_padding.toString(),"\n","\t","button_action:            ",this.settings.button_action.toString(),"\n","\t","button_disabled:          ",this.settings.button_disabled.toString(),"\n","\t","custom_settings:          ",this.settings.custom_settings.toString(),"\n","Event Handlers:\n","\t","swfupload_loaded_handler assigned:  ",(typeof this.settings.swfupload_loaded_handler==="function").toString(),"\n","\t","file_dialog_start_handler assigned: ",(typeof this.settings.file_dialog_start_handler==="function").toString(),"\n","\t","file_queued_handler assigned:       ",(typeof this.settings.file_queued_handler==="function").toString(),"\n","\t","file_queue_error_handler assigned:  ",(typeof this.settings.file_queue_error_handler==="function").toString(),"\n","\t","upload_start_handler assigned:      ",(typeof this.settings.upload_start_handler==="function").toString(),"\n","\t","upload_progress_handler assigned:   ",(typeof this.settings.upload_progress_handler==="function").toString(),"\n","\t","upload_error_handler assigned:      ",(typeof this.settings.upload_error_handler==="function").toString(),"\n","\t","upload_success_handler assigned:    ",(typeof this.settings.upload_success_handler==="function").toString(),"\n","\t","upload_complete_handler assigned:   ",(typeof this.settings.upload_complete_handler==="function").toString(),"\n","\t","debug_handler assigned:             ",(typeof this.settings.debug_handler==="function").toString(),"\n"].join(""))};SWFUpload.prototype.addSetting=function(b,c,a){if(c==undefined){return(this.settings[b]=a)}else{return(this.settings[b]=c)}};SWFUpload.prototype.getSetting=function(a){if(this.settings[a]!=undefined){return this.settings[a]}return""};SWFUpload.prototype.callFlash=function(functionName,argumentArray){argumentArray=argumentArray||[];var movieElement=this.getMovieElement();var returnValue,returnString;try{returnString=movieElement.CallFunction('<invoke name="'+functionName+'" returntype="javascript">'+__flash__argumentsToXML(argumentArray,0)+"</invoke>");returnValue=eval(returnString)}catch(ex){throw"Call to "+functionName+" failed"}if(returnValue!=undefined&&typeof returnValue.post==="object"){returnValue=this.unescapeFilePostParams(returnValue)}return returnValue};SWFUpload.prototype.selectFile=function(){this.callFlash("SelectFile")};SWFUpload.prototype.selectFiles=function(){this.callFlash("SelectFiles")};SWFUpload.prototype.startUpload=function(a){this.callFlash("StartUpload",[a])};SWFUpload.prototype.cancelUpload=function(a,b){if(b!==false){b=true}this.callFlash("CancelUpload",[a,b])};SWFUpload.prototype.stopUpload=function(){this.callFlash("StopUpload")};SWFUpload.prototype.getStats=function(){return this.callFlash("GetStats")};SWFUpload.prototype.setStats=function(a){this.callFlash("SetStats",[a])};SWFUpload.prototype.getFile=function(a){if(typeof(a)==="number"){return this.callFlash("GetFileByIndex",[a])}else{return this.callFlash("GetFile",[a])}};SWFUpload.prototype.addFileParam=function(a,b,c){return this.callFlash("AddFileParam",[a,b,c])};SWFUpload.prototype.removeFileParam=function(a,b){this.callFlash("RemoveFileParam",[a,b])};SWFUpload.prototype.setUploadURL=function(a){this.settings.upload_url=a.toString();this.callFlash("SetUploadURL",[a])};SWFUpload.prototype.setPostParams=function(a){this.settings.post_params=a;this.callFlash("SetPostParams",[a])};SWFUpload.prototype.addPostParam=function(a,b){this.settings.post_params[a]=b;this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.removePostParam=function(a){delete this.settings.post_params[a];this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.setFileTypes=function(a,b){this.settings.file_types=a;this.settings.file_types_description=b;this.callFlash("SetFileTypes",[a,b])};SWFUpload.prototype.setFileSizeLimit=function(a){this.settings.file_size_limit=a;this.callFlash("SetFileSizeLimit",[a])};SWFUpload.prototype.setFileUploadLimit=function(a){this.settings.file_upload_limit=a;this.callFlash("SetFileUploadLimit",[a])};SWFUpload.prototype.setFileQueueLimit=function(a){this.settings.file_queue_limit=a;this.callFlash("SetFileQueueLimit",[a])};SWFUpload.prototype.setFilePostName=function(a){this.settings.file_post_name=a;this.callFlash("SetFilePostName",[a])};SWFUpload.prototype.setUseQueryString=function(a){this.settings.use_query_string=a;this.callFlash("SetUseQueryString",[a])};SWFUpload.prototype.setRequeueOnError=function(a){this.settings.requeue_on_error=a;this.callFlash("SetRequeueOnError",[a])};SWFUpload.prototype.setHTTPSuccess=function(a){if(typeof a==="string"){a=a.replace(" ","").split(",")}this.settings.http_success=a;this.callFlash("SetHTTPSuccess",[a])};SWFUpload.prototype.setAssumeSuccessTimeout=function(a){this.settings.assume_success_timeout=a;this.callFlash("SetAssumeSuccessTimeout",[a])};SWFUpload.prototype.setDebugEnabled=function(a){this.settings.debug_enabled=a;this.callFlash("SetDebugEnabled",[a])};SWFUpload.prototype.setButtonImageURL=function(a){if(a==undefined){a=""}this.settings.button_image_url=a;this.callFlash("SetButtonImageURL",[a])};SWFUpload.prototype.setButtonDimensions=function(c,a){this.settings.button_width=c;this.settings.button_height=a;var b=this.getMovieElement();if(b!=undefined){b.style.width=c+"px";b.style.height=a+"px"}this.callFlash("SetButtonDimensions",[c,a])};SWFUpload.prototype.setButtonText=function(a){this.settings.button_text=a;this.callFlash("SetButtonText",[a])};SWFUpload.prototype.setButtonTextPadding=function(b,a){this.settings.button_text_top_padding=a;this.settings.button_text_left_padding=b;this.callFlash("SetButtonTextPadding",[b,a])};SWFUpload.prototype.setButtonTextStyle=function(a){this.settings.button_text_style=a;this.callFlash("SetButtonTextStyle",[a])};SWFUpload.prototype.setButtonDisabled=function(a){this.settings.button_disabled=a;this.callFlash("SetButtonDisabled",[a])};SWFUpload.prototype.setButtonAction=function(a){this.settings.button_action=a;this.callFlash("SetButtonAction",[a])};SWFUpload.prototype.setButtonCursor=function(a){this.settings.button_cursor=a;this.callFlash("SetButtonCursor",[a])};SWFUpload.prototype.queueEvent=function(b,c){if(c==undefined){c=[]}else{if(!(c instanceof Array)){c=[c]}}var a=this;if(typeof this.settings[b]==="function"){this.eventQueue.push(function(){this.settings[b].apply(this,c)});setTimeout(function(){a.executeNextEvent()},0)}else{if(this.settings[b]!==null){throw"Event handler "+b+" is unknown or is not a function"}}};SWFUpload.prototype.executeNextEvent=function(){var a=this.eventQueue?this.eventQueue.shift():null;if(typeof(a)==="function"){a.apply(this)}};SWFUpload.prototype.unescapeFilePostParams=function(c){var e=/[$]([0-9a-f]{4})/i;var f={};var d;if(c!=undefined){for(var a in c.post){if(c.post.hasOwnProperty(a)){d=a;var b;while((b=e.exec(d))!==null){d=d.replace(b[0],String.fromCharCode(parseInt("0x"+b[1],16)))}f[d]=c.post[a]}}c.post=f}return c};SWFUpload.prototype.testExternalInterface=function(){try{return this.callFlash("TestExternalInterface")}catch(a){return false}};SWFUpload.prototype.flashReady=function(){var a=this.getMovieElement();if(!a){this.debug("Flash called back ready but the flash movie can't be found.");return}this.cleanUp(a);this.queueEvent("swfupload_loaded_handler")};SWFUpload.prototype.cleanUp=function(a){try{if(this.movieElement&&typeof(a.CallFunction)==="unknown"){this.debug("Removing Flash functions hooks (this should only run in IE and should prevent memory leaks)");for(var c in a){try{if(typeof(a[c])==="function"){a[c]=null}}catch(b){}}}}catch(d){}window.__flash__removeCallback=function(e,f){try{if(e){e[f]=null}}catch(g){}}};SWFUpload.prototype.fileDialogStart=function(){this.queueEvent("file_dialog_start_handler")};SWFUpload.prototype.fileQueued=function(a){a=this.unescapeFilePostParams(a);this.queueEvent("file_queued_handler",a)};SWFUpload.prototype.fileQueueError=function(a,c,b){a=this.unescapeFilePostParams(a);this.queueEvent("file_queue_error_handler",[a,c,b])};SWFUpload.prototype.fileDialogComplete=function(b,c,a){this.queueEvent("file_dialog_complete_handler",[b,c,a])};SWFUpload.prototype.uploadStart=function(a){a=this.unescapeFilePostParams(a);this.queueEvent("return_upload_start_handler",a)};SWFUpload.prototype.returnUploadStart=function(a){var b;if(typeof this.settings.upload_start_handler==="function"){a=this.unescapeFilePostParams(a);b=this.settings.upload_start_handler.call(this,a)}else{if(this.settings.upload_start_handler!=undefined){throw"upload_start_handler must be a function"}}if(b===undefined){b=true}b=!!b;this.callFlash("ReturnUploadStart",[b])};SWFUpload.prototype.uploadProgress=function(a,c,b){a=this.unescapeFilePostParams(a);this.queueEvent("upload_progress_handler",[a,c,b])};SWFUpload.prototype.uploadError=function(a,c,b){a=this.unescapeFilePostParams(a);this.queueEvent("upload_error_handler",[a,c,b])};SWFUpload.prototype.uploadSuccess=function(b,a,c){b=this.unescapeFilePostParams(b);this.queueEvent("upload_success_handler",[b,a,c])};SWFUpload.prototype.uploadComplete=function(a){a=this.unescapeFilePostParams(a);this.queueEvent("upload_complete_handler",a)};SWFUpload.prototype.debug=function(a){this.queueEvent("debug_handler",a)};SWFUpload.prototype.debugMessage=function(c){if(this.settings.debug){var a,d=[];if(typeof c==="object"&&typeof c.name==="string"&&typeof c.message==="string"){for(var b in c){if(c.hasOwnProperty(b)){d.push(b+": "+c[b])}}a=d.join("\n")||"";d=a.split("\n");a="EXCEPTION: "+d.join("\nEXCEPTION: ");SWFUpload.Console.writeLine(a)}else{SWFUpload.Console.writeLine(c)}}};SWFUpload.Console={};SWFUpload.Console.writeLine=function(d){var b,a;try{b=document.getElementById("SWFUpload_Console");if(!b){a=document.createElement("form");document.getElementsByTagName("body")[0].appendChild(a);b=document.createElement("textarea");b.id="SWFUpload_Console";b.style.fontFamily="monospace";b.setAttribute("wrap","off");b.wrap="off";b.style.overflow="auto";b.style.width="700px";b.style.height="350px";b.style.margin="5px";a.appendChild(b)}b.value+=d+"\n";b.scrollTop=b.scrollHeight-b.clientHeight}catch(c){alert("Exception: "+c.name+" Message: "+c.message)}};

/**
 * 注意：swfupload创建后，会把绑定的dom节点替换掉（"button_placeholder_id" : settings.id）
 * $().data()不能作用域object标签
 */
(function($) {
	var methods = {
			
		"init" : function(options) {
			if (this.attr("eveappendix")) {
				var fileTypes = this.attr("eveappendix").split(",");
				$.each(fileTypes, function (idx, o) {
					fileTypes[idx] = "*." + o;
				});
				fileTypes = fileTypes.join(";")
			}
			var settings = $.extend({}, $.fn.upload.defaultSettings, options, {
				"id" : this.attr("id"),
				"evetypeid": this.attr("evetypeid"),
				"tableName": this.attr("tableName"),
				"fileSize": this.attr("evesize") + " " + this.attr("eveunit"),
				"fileTypes": fileTypes,
				"fileTypesDesc": this.attr("eveappendix")
			});
			
			return this.each(function() {
				// 创建上传界面组件
				$this = $(this);
				$hiddenInput = $("<input type='hidden'>").attr({
					"id": $this.attr("id") + "hidden"
				}).addClass("filehidden");
				var bindedDom = null;
				if (settings.evetypeid == "1") { // 单文件上传
					initUploader4File();
				} else if (settings.evetypeid == "2") { // 图片上传回显
					// 构建界面DOM
					bindedDom = _buildDom4Pic();
					// 绑定swfupload控件
					_bindSwfupload(bindedDom, this);
				} else if (settings.evetypeid == "3") { // 上传面板
					var userId = $("#flow__applyUserId");
					var tableName = $("#flow__tableName");
					if (userId.length) {
						userId = userId.val();
					} else {
						$.messager.alert("警告", "此表单不存在用户ID的隐含信息！");
					}
					if (tableName.length) {
						tableName = tableName.val();
					} else {
						$.messager.alert("警告", "此表单不存在业务表名的隐含信息！");
					}
					// 构建界面DOM
					_buildDom4Panel(tableName, userId);
					window.appendixdowload = function(fileId) {
						if (fileId) {
							window.open($.getRootPath() + "/DownLoadServlet?fileId="+fileId);
						}
					}
				}
			});
			
			/*
			 * 初始化单文件上传组件
			 */
			function initUploader4File() {
				var $queue = $("<table></table>").addClass("upload-queue");
				var $item = $("<tr></tr>").addClass("upload-queue-item");
				var $fileName = $("<td></td>").css({
					"width" : "200px"
				}).attr({
					"id" : settings.id + "_fileName"
				});
				var $fileSize = $("<td></td>").css({
					"width" : "80px",
					"textAlign" : "right"
				}).attr({
					"id" : settings.id + "_fileSize"
				});
				
				var $pb = $("<span>0%</span>").attr({
					"id" : settings.id + "_barObj"
				}).css({
					"width" : "160px",
					"textAlign" : "center",
					"display" : "none"
				});
				$pb.progressBar({
					"boxImage" : $.getRootPath() + "/plug-in/jquery/plugin/jqueryProgressbar/images/progressbar.gif",
					"barImage" : {
						"0" : $.getRootPath() + "/plug-in/jquery/plugin/jqueryProgressbar/images/progressbg_red.gif",
						"30" : $.getRootPath() + "/plug-in/jquery/plugin/jqueryProgressbar/images/progressbg_orange.gif",
						"70" : $.getRootPath() + "/plug-in/jquery/plugin/jqueryProgressbar/images/progressbg_green.gif"
					}
				});
				
				var $progressBar = $("<td></td>").css({
					"width" : "160px"
				}).attr({
					"id" : settings.id + "_progressBar"
				});
				var $button = $("<td></td>");
				$wrapbtn = $("<div style='height: 14px; line-height: 14px; width: 28px;'></div>");
				var $btnAdd = $("<a href='javascript:void(0)'><span style='font-size:14px'>添加</span></a>");
				var $btnDel = $("<a href='javascript:void(0)'><span style='font-size:14px'>删除</span></a>").css("display", "none");
				$btnDel.on("click", function() {
					if (window.attachFileIdArray && window.attachFileIdArray[0]) {
						var params = {
							"selectColNames":  window.attachFileIdArray
						};
						$.post($.getRootPath() + "/fileAttachController.do?multiDel", $.param(params, true), function(r) {
							if (r.success) {
								swfupload.cancelUpload();
								$fileName.empty();
								$fileSize.empty();
								$pb.progressBar(0);
								$btnAdd.show();
								$btnDel.hide();
								$("#" + swfupload.movieName).css("z-index", 1);
								window.attachFileIdArray = [];
							} else {
								alert("提示", "删除附件失败");
							}
						}, "json");  
					} else {
						swfupload.cancelUpload();
						$fileName.empty();
						$fileSize.empty();
						$pb.progressBar(0);
						$btnAdd.show();
						$btnDel.hide();
						$("#" + swfupload.movieName).css("z-index", 1);
						window.attachFileIdArray = [];
					}
				});
				$wrapbtn = $wrapbtn.append($btnAdd).append($btnDel).append($hiddenInput); // 重新引用，引用指向改变后的dom对象
				$this.before($queue.append($item.append($fileName).append($fileSize).append($progressBar.append($pb)).append($button.append($wrapbtn))));
				
				$this.hide();
				var setting = {
					"upload_url" : settings.uploadUrl + "&busTableName=" + settings.tableName,
					// 上传文件设置
					"file_size_limit": settings.fileSize,
					"file_types": settings.fileTypes,
					"file_types_description": settings.fileTypesDesc,
					"file_upload_limit": 0,
					"file_queue_limit": 1,
					// 按钮设置
					"button_image_url": null,
					"button_placeholder_id": settings.id + "hidden",
					"button_width": 28,
					"button_height": 14,
					"button_text": null,
					"button_text_style": null,
					"button_text_top_padding" : 0,
					"button_text_left_padding" : 0,
					"button_action": SWFUpload.BUTTON_ACTION.SELECT_FILE,
					"button_window_mode" : SWFUpload.WINDOW_MODE.TRANSPARENT,
					"button_cursor" : SWFUpload.CURSOR.HAND,
					"customSettings": {
						"scope_handler": this
					},
					"flash_url" : $.getRootPath() + "/plug-in/jquery/plugin/jqueryUpload/swfupload/swfupload.swf",
					// 事件处理函数
					"debug" : settings.debug,
					"file_dialog_complete_handler" : handlers.fileDialogCompleteHandler4File,
					"file_queued_handler" : handlers.fileQueuedHandler4File,
					"file_queue_error_handler" : handlers.fileQueueErrorHandler4File,
					"upload_start_handler" : handlers.uploadStartHandler4File,
					"upload_progress_handler" : handlers.uploadProgressHandler4File,
					"upload_success_handler" : handlers.uploadSuccessHandler4File,
					"upload_error_handler" : handlers.uploadErrorHandler4File,
					"upload_complete_handler" : handlers.uploadCompleteHandler4File
				};
				
				var swfupload = new SWFUpload(setting);
				$(swfupload).data("upload", settings);
				$(swfupload).data("$btnAdd", $btnAdd);
				$(swfupload).data("$btnDel", $btnDel);
				$("#" + swfupload.movieName).css({
					"position" : "absolute",
					"z-index" : 1
				});
				
				$wrapbtn.prepend($("#" + swfupload.movieName));
				$(swfupload).data("$bindedDom", $wrapbtn);
				$(swfupload).data("$pb", $pb);
				
				$this.data("$btnAdd", $btnAdd);
				$this.data("$btnDel", $btnDel);
				$this.data("$wrapbtn", $wrapbtn);
				
				window.attachFileIdArray = []; // 存放成功上传的文件的数据库id
			}
			
			function _bindSwfupload($bindedDom, dataDom) {
				var setting = {
					"upload_url" : settings.uploadUrl + "&busTableName=" + settings.tableName,
					// 上传文件设置
					"file_size_limit" : settings.fileSize,
					"file_types" : settings.fileTypes,
					"file_types_description" : settings.fileTypesDesc,
					"file_upload_limit" : "10",
					// 按钮设置
					"button_image_url" : null,
					"button_placeholder_id" : settings.id,
					"button_width" : 32,
					"button_height" : 20,
					"button_text" : null,
					"button_text_style" : null,
					"button_text_top_padding" : 0,
					"button_text_left_padding" : 0,
					"button_window_mode" : SWFUpload.WINDOW_MODE.TRANSPARENT,
					"button_cursor" : SWFUpload.CURSOR.HAND,
					"customSettings": {
						"scope_handler": this
					},
					// Flash Settings
					"flash_url" : $.getRootPath() + "/plug-in/jquery/plugin/jqueryUpload/swfupload/swfupload.swf",
					// 事件处理函数
					"debug" : settings.debug,
					"file_dialog_complete_handler" : handlers.fileDialogCompleteHandler4File,
					"file_queued_handler" : handlers.fileQueuedHandler4File,
					"file_queue_error_handler" : handlers.fileQueueErrorHandler4File,
					"upload_progress_handler" : handlers.uploadProgressHandler4File,
					"upload_success_handler" : handlers.uploadSuccessHandler4File,
					"upload_error_handler" : handlers.uploadErrorHandler4File,
					"upload_complete_handler" : handlers.uploadCompleteHandler4File
				};
				
				var swfupload = new SWFUpload(setting);
				$(swfupload).data("upload", settings);
				$("#" + swfupload.movieName).css({
					"position" : "absolute",
					"z-index" : 1
				});
				$bindedDom.prepend($("#" + swfupload.movieName));
				$(swfupload).data("$dataDom", dataDom);
				$(swfupload).data("$bindedDom", $bindedDom);
			}
				
			function _buildDom4Pic() {
				var $div = $("<div></div>");
				var $table = $("<table></table>");
				var $trImg = $("<tr></tr>");
				var $tdImg = $("<td></td>");
				$img = $("<img />").attr({
					"src": $.getRootPath() + "/plug-in/jquery/plugin/jqueryUpload/images/preview.jpg"
				}).css({
					"width": 120,
					"height": 150
				});
				var $trBtn = $("<tr></tr>");
				var $tdBtn = $("<td></td>");
				var $button = $("<td width='100px'></td>");
				$wrapbtn = $("<div style='height: 20px; line-height: 20px; width: 100px;'></div>");
				var $btn = $("<div></div>").addClass("upload-queue-item-button").append("<span>添加</span>").addClass("upload-queue-item-button-text");
				$wrapbtn = $wrapbtn.append($btn); // 重新引用，引用指向改变后的dom对象
				$this.before($div.append($table.append($trImg.append($tdImg.append($img))).append($trBtn.append($tdBtn.append($wrapbtn)))));
				$this.css("display", "none");
				return $wrapbtn;
			}
			
			function _buildDom4Panel(tableName, userId) {
				var $tablePanel = $("<table></table>").attr({
					"id": settings.id
				});
				//	"flow__applyUserId";
				//	"flow__applyUserName";
				//	"flow__tableName";
				//	attachFileIdArray
				settings.uploadUrl = $.getRootPath() + "/UploadServlet?uploadPath=myfile&busRecordId=1&busTableName=" + tableName + "&uploadUserId=" + userId,
				$this.replaceWith($tablePanel);
				$tablePanel.datagrid({
					"rownumbers": true,
					"striped": true,
					"width": 550,
					"height": 300,
					"url": $.getRootPath() + "/fileAttachController.do?datagrid&BUS_TABLERECORDID=1&BUS_TABLENAME=" + tableName + "&isForJquery=true",
					"idField": "fileId",
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
							return '<img title="下载" src="images/btn-download.png" style="cursor: pointer;" onclick="appendixdowload(\'' + row.FILE_ID +'\')"/>';
						}
			    	}]],
			    	"onLoadSuccess": function(data) {
			    		var arr = [];
			    		$.each(data.rows, function(idx, row) {
			    			arr.push(row.FILE_ID);
			    		});
			    		var params = {
			    			"attachFileIdArray": arr
			    		};
						$("#attachFileIdArray").val($.param(params, true));
			    	},
			    	"toolbar":  [{
			    		"text": "上传附件",
						"iconCls": "icon-add",
						"handler": function() {
							var $dialog = $("<div><table></table></div>").dialog({
							    "title": "上传附件窗口",
							    "width": 600,
							    "height": 350,
							    "modal": true,
							    "onClose" : function() {
							        $(this).dialog("destroy");
							        $tablePanel.datagrid("load");
							    },
							    "buttons" : [{
							        "text" : "确定",
							        "handler" : function() {
							            $(this).closest('.window-body').dialog("close");
							        }
							    }]
							}); 
							
							var $table = $("<table></table>");
							
							$dialog.find("table").replaceWith($table);
							
							$table.datagrid({
								"rownumbers": true,
								"striped": true,
								"fit": true,
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
								    			'<div style="height:20;width:' + (value ? value : 0) + '%;background:#cc0000;color:#fff"><font color="black">' + (value ? value : 0) + '%' + '</font></div>' +
								    			'</div>';
							    	}
						    	}]],
						    	"toolbar":  [{
						    		"text": "添加文件",
									"iconCls": "icon-fileadd",
									"handler": function() {
									}
						    	}, {
						    		"text": "删除未上传文件",
									"iconCls": "icon-filedelete",
									"handler": function() {
										var rows = $table.datagrid("getSelections");
										if (rows.length) {
											var arr = [];
											var noteArr = [];
											$.each(rows, function(idx, row) {
												if (row.fileUploadId != undefined) {
													noteArr.push(row.fileName);
												} else {
													arr.push($table.datagrid("getRowIndex", row));
												}
											}); 
											var flag = 0;
											$.each(arr, function(i, n) {
									          	$table.datagrid("deleteRow", n - flag);
									          	flag++;
									    	});
									    	if (noteArr.length) {
										    	$.messager.alert("提示", "【" + noteArr.join("】<br>【") + "】<br>已上传，不可在此删除!"); 
									    	}
										}
									}
						    	}, "-", {
						    		"text": "上传",
									"iconCls": "icon-upload",
									"handler": function() {
										swfupload.startUpload();
									}
						    	}]
							}); 
							var $td = $table.datagrid("getPanel").find("td").first();
							$td.append($("<span></span>").attr({
								"id": "swfuploadholder"
							}))
							// 创建swfupload.swf组件
							var SwfSettings = {
								"upload_url" : settings.uploadUrl,
								// 上传文件设置
								"file_size_limit" : settings.fileSize,
								"file_types" : settings.fileTypes,
								"file_types_description" : settings.fileTypesDesc,
								"file_upload_limit" : "10",
								// 按钮设置
								"button_image_url" : null,
								"button_placeholder_id" : "swfuploadholder",
								"button_width" : 72,
								"button_height" : 25,
								"button_text" : null,
								"button_text_style" : null,
								"button_text_top_padding" : 0,
								"button_text_left_padding" : 0,
								"button_window_mode" : SWFUpload.WINDOW_MODE.TRANSPARENT,
								"button_cursor" : SWFUpload.CURSOR.HAND,
								// Flash Settings
								"flash_url" : "swfupload/swfupload.swf",
								// 事件处理函数
								"debug" : settings.debug,
								"file_dialog_complete_handler" : handlers.fileDialogCompleteHandler,
								"file_queued_handler" : handlers.fileQueuedHandler,
								"file_queue_error_handler" : handlers.fileQueueErrorHandler,
								"upload_progress_handler" : handlers.uploadProgressHandler,
								"upload_success_handler" : handlers.uploadSuccessHandler,
								"upload_error_handler" : handlers.uploadErrorHandler,
								"upload_complete_handler" : handlers.uploadCompleteHandler	
							};
								
							var swfupload = new SWFUpload(SwfSettings);
							$("#" + swfupload.movieName).css({
								"position" : "absolute",
								"z-index": 1,
								"top": 2,
								"left": 2
							});
							
							$(swfupload).data("$table", $table);
						}
					},'-',{
						"text": "删除附件",
						"iconCls": "icon-cancel",
						"handler": function() {
							var rows = $tablePanel.datagrid("getChecked");
							if (rows.length) {
								var url = $.getRootPath() + "/fileAttachController.do?multiDel" 
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
										$.messager.alert("提示", "删除附件失败");
									}
								}, "json");  
							}
						}
					}]
				});
			}
		}
	};
	
	var handlers = {
		
		"fileDialogCompleteHandler4File": function(numFilesSelected, numFilesQueued, totalNumFilesQueued) {
//			alert("fileDialogCompleteHandler4File");
			handlers.fileDialogCompleteHandler.call(this, numFilesSelected, numFilesQueued, totalNumFilesQueued);
		},
		
		"fileQueuedHandler4File": function(file) {
//			alert("fileQueuedHandler4File");
			var id = $(this).data("upload").id;
			// 设置文件大小
			var fileSize = file.size / 1024;
			var suffix = 'KB';
			if (fileSize > 1000) {
				fileSize = fileSize / 1024;
				suffix = 'MB';
			}
			var fileSizeParts = fileSize.toString().split('.');
			fileSize = fileSizeParts[0];
			if (fileSizeParts.length > 1) {
				fileSize += '.' + fileSizeParts[1].substr(0,2);
			}
			fileSize += " " + suffix;
			$("#" + id + "_fileSize").append(fileSize);
			
			// 设置进度条
			$("#" + id + "_barObj").css({
				"display" : "block"
			}); 		
			// 设置文件名
			var fileName = file.name;
			if (fileName.length > 25) {
				fileName = fileName.substr(0,25) + '...';
			}
			$("#" + id + "_fileName").append(fileName); 		
		},
		
		"fileQueueErrorHandler4File": function(file, errorCode, message) {
			handlers.fileQueueErrorHandler.call(this, file, errorCode, message);
		},
		
		"uploadStartHandler4File": function(file) {
//			alert("uploadStartHandler4File");
			$(this).data("$btnAdd").hide();
			$(this).data("$btnDel").show();
			$("#" + this.movieName).css("z-index", -1);
		},
		
		"uploadProgressHandler4File": function(file, bytesCompeted, bytesTotal) {
			try {
				var percent = Math.ceil((bytesCompeted / bytesTotal) * 100);
				$(this).data("$pb").progressBar(percent);
			} catch (ex) {
				this.debug(ex);
			}
		},
		
		"uploadSuccessHandler4File": function(file, serverData, response) {
			window.attachFileIdArray.push($.parseJSON(serverData).fileId);
		},
		
		"uploadErrorHandler4File": function() {
			uploadErrorHandler();
		},
		
		"uploadCompleteHandler4File": function(file) {
			handlers.uploadCompleteHandler.call(this, file);
		},

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
					this.startUpload();
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
		 * 		 errorCode 对应SWFUpload.QUEUE_ERROR常量 
		 *       message 返回的消息
		 */
		"fileQueueErrorHandler" : function(file, errorCode, message) {
			try {
				switch (errorCode) {
				case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
					alert("只能上传一个附件");
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
		 * 参数：
		 * 		 file 上传文件
		 * 		 bytesCompeted 完成上传的字节大小
		 * 		 bytesTotal 总共字节大小 
		 */
		"uploadProgressHandler": function(file, bytesCompeted, bytesTotal) {
			var $table = $(this).data("$table");
			$table.datagrid("selectRecord", file.id);
			var row = $table.datagrid("getSelected");
			row.fileProgress = Math.ceil((bytesCompeted / bytesTotal) * 100);
			$table.datagrid("refreshRow", $table.datagrid("getRowIndex", row));
		},

		/**
		 * 功能：上传成功事件处理函数
		 * 参数：
		 * 		 file
		 * 		 serverData
		 * 		 response
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

				if (file.type == ".jpg") {
					var newImg = $(this).data("$img").get(0);
					newImg.style.margin = "5px";
					if (newImg.filters) {
						try {
							newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
						} catch (e) {
							// If it is not set initially, the browser will throw an error. This
							// will set it if it is not set yet.
							newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
						}
					} else {
						newImg.style.opacity = 0;
					}
					newImg.onload = function() {
						function fadeIn(element, opacity) {
							var reduceOpacityBy = 5;
							var rate = 30; // 15 fps
							if (opacity < 100) {
								opacity += reduceOpacityBy;
								if (opacity > 100) {
									opacity = 100;
								}
								if (element.filters) {
									try {
										element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
									} catch (e) {
										// If it is not set initially, the browser will throw an error.
										// This will set it if it is not set yet.
										element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
									}
								} else {
									element.style.opacity = opacity / 100;
								}
							}
							if (opacity < 100) {
								setTimeout(function() {
									fadeIn(element, opacity);
								}, rate);
							}
						}
						fadeIn(newImg, 0);
					};
					newImg.src = $.getRootPath() + "/attachFiles/" + $.parseJSON(serverData).filePath;
				}


				$table.datagrid("selectRecord", file.id);
				var r = $table.datagrid("getSelected");
				r.fileUploadId = $.parseJSON(serverData).fileId;
				r.fileProgress = 100;
				$table.datagrid("refreshRow", $table.datagrid("getRowIndex", r));
			} catch (ex) {
				this.debug(ex);
			}
		},

		/**
		 * 功能：上传错误事件处理函数
		 * 参数：
		 * 		 file
		 * 		 serverData
		 * 		 response
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
		 * 参数：
		 * 		 file
		 * 		 serverData
		 * 		 response
		 */
		"uploadCompleteHandler" : function(file) {
			try {
				// 上传队列中的下一个任务自动开始
				if (this.getStats().files_queued > 0) {
					this.startUpload();
				} else {
				}
			} catch (ex) {
				this.debug(ex);
			}
		}
	}
	
	$.fn.upload = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if ($.isPlainObject(method) || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error(method + '此方法未在upload中定义');
		}
	}
	
	/*
    1、写权限：上传，删除
    2、读权限：下载
    3、设置文件大小
    4、设置文件类型
    5、单行文件上传
    6、图片回显上传
    7、多文件上传面板
    */
	$.fn.upload.defaultSettings = {
		"uploadUrl" : $.getRootPath() + "/UploadServlet?uploadPath=myfile&busRecordId=&uploadUserId=-1",
		"isBrowse" : false, // 控件展现成上传形式还是查看形式，true 上传形式 false 查看形式
		"fileSize" : "1024MB", // 限制文件大小，单位KB,MB,GB，默认20MB
		"fileTypes" : "*.*", // 默认所有文件
		"fileTypesDesc" : "所有文件", // 允许文件类型描述
		"isShowImage" : true, // 是否回显图片，true 显示 false 不显示
		"isSingle" : true, // 是否单文件上传，true 单文件 false 多文件
		"debug" : false
	};
})(jQuery);