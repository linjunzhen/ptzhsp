var AppUtilNew = new Object({	
	/**
     * 判断一个数组中是否包含某个值 
     */
    isContain:function(array,targetValue){
    	var isExist = false;
    	for(var index in array){
    		var value = array[index];
    		if(value==targetValue){
    			isExist = true;
    			break;
    		}    		
    	}
    	return isExist;
    },
	/**
	 * 获取被授权的栏目ID
	 * @param {} siteId
	 */
    getGrantedChannel:function(siteId){
    	var channelIds = [];
		$.ajax({
            type: "POST",
            url: "channelController.do?loadGranted&siteId="+siteId,
            async: false, 
            cache: false,
            success: function (responseText) {
            	var resultJson = $.parseJSON(responseText);
            	for(var index in resultJson){
            		var obj = resultJson[index];
            		channelIds.push(obj.CHANNEL_ID);
            	}
            }
        });
        return channelIds;
        /*if(channelIds.indexOf("247")!=-1){
        	console.log("存在..");
        }else{
        	console.log("Bu");
        }*/
        
    },
	/**
	 * 获取被授权的文章访问权限
	 * @param {} siteId
	 * @param {} channelId
	 */
	getGrantedArticleRights:function(siteId,channelId){
		var resultJson = null;
		$.ajax({
            type: "POST",
            url: "articleController.do?getRights&siteId="+siteId+"&channelId="+channelId,
            async: false, 
            cache: false,
            success: function (responseText) {
            	resultJson = $.parseJSON(responseText);
            }
        });
        return resultJson;
	},
	/**
	 * 显示收件箱内容
	 * @param {} recordId
	 */
	showMsgRecordWindow:function(recordId){
		$.layer({
			type : 2,
			maxmin : false,
			title : "阅读站内短信",
			area : [ "600px", "400px" ],
			iframe : {
				src : "msgRecordController.do?info&entityId=" + recordId
			},
			close: function(index){
				$("#MsgRecordGrid").datagrid("reload");
			}
		});
	},
	/**
	 * 进入系统
	 * @param {} loginFormId
	 * @param {} isNeedValidCode
	 */
	enterSystem:function(loginFormId,isNeedValidCode){
		//判断浏览器是否为ie6,7,8
	    if (!$.support.leadingWhitespace) {
	       if(event.keyCode == 13){
		    	AppUtilNew.submitLoginForm(loginFormId,isNeedValidCode);
		    }
	    }else{
	    	event=arguments.callee.caller.arguments[0] || window.event; 
		    if(event.keyCode == 13){
		    	AppUtilNew.submitLoginForm(loginFormId,isNeedValidCode);
		    }
	    }
	},
	/**
	 * 提交登录表单
	 * @param {} loginFormId
	 * @param {} isNeedValidCode
	 */
	submitLoginForm:function(loginFormId,isNeedValidCode){
		var username = $("#username").val();
		var password = $("#password").val();
		if(!(username!=null&&username!="")){
			alert("请输入用户帐号!");
			return;
		}
		if(!(password!=null&&password!="")){
			alert("请输入密码!");
			return;
		}
		if(isNeedValidCode){
			var validateCode = $("#validateCode").val();
			if(!(validateCode!=null&&validateCode!="")){
				alert("请输入验证码!");
				return;
			}
		}
		setMaxDigits(130);
		var key = new RSAKeyPair("10001","","874bb34029eb3f5114ca1f30954808c7ebb18f8ee7bc6d4e838b4f793354dc6d1110dfa8f11682712a464a6623a88715a56ecdf870432bb44a403a28723fdce7354268cd7b613bcf363393083b960f2c8bbce10e999eb875eb05a8f4c5ca922380e17b0f1f439db239a2eface41fa253f84b62fba0f33a8fc9aa59a4b3491a91");
		var encuser = encryptedString(key, encodeURIComponent(username));
		var encresult = encryptedString(key, encodeURIComponent(password));
		$("#username").val(encuser);
		$("#password").val(encresult);
		$("#"+loginFormId).submit();
	},
	/**
	 * 重置表单
	 * @param {} formName
	 */
	resetForm:function(formName){
		$("form[name='"+formName+"']")[0].reset();
	},
	/**
	 * 检查浏览器版本
	 */
	checkBrowserVersion:function(){
		var userAgent = navigator.userAgent.toLowerCase();
		browser = {
			version : (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [
					0, '0'])[1],
			safari : /webkit/.test(userAgent),
			opera : /opera/.test(userAgent),
			msie : /msie/.test(userAgent) && !/opera/.test(userAgent),
			mozilla : /mozilla/.test(userAgent)
					&& !/(compatible|webkit)/.test(userAgent)
		}
		if (browser.msie && (parseInt(browser.version) <= 7)) {
			layer.alert("您的浏览器正处于ie7或以下级别的文档模式，不适合本系统的运维操作，建议下载并安装<a href='plug-in/browser-1.0/ie8.rar'>ie8</a>或<a href='plug-in/browser-1.0/Firefox-full-latest.exe'>火狐浏览器</a>。");
		}
	},
	/**
	 * 格式化时间
	 * @param {} date
	 * @param {} format
	 */
	formatDate:function(date,format){
		var paddNum = function(num){
          num += "";
          return num.replace(/^(\d)$/,"0$1");
        }
        //指定格式字符
        var cfg = {
           yyyy : date.getFullYear() //年 : 4位
          ,yy : date.getFullYear().toString().substring(2)//年 : 2位
          ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
          ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
          ,d  : date.getDate()   //日 : 如果1位的时候不补0
          ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
          ,hh : paddNum(date.getHours())  //时
          ,mm : paddNum(date.getMinutes()) //分
          ,ss : paddNum(date.getSeconds()) //秒
        }
        format || (format = "yyyy-MM-dd hh:mm:ss");
        return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
	},
	/**
	 * 显示excel导出方式选择窗口
	 * @param {} config
	 */
	showExcelExportWin:function(config){
		var dataGridId = config.dataGridId;
		var page = $("#"+dataGridId).datagrid("options").pageNumber;
		var rows = $("#"+dataGridId).datagrid("options").pageSize;
		var tplKey= config.tplKey;
		var excelName = config.excelName;
		var queryParams = config.queryParams;
		var isportal = config.isportal;
		var src = "excelConfigController.do?selector&tplKey="+tplKey+"&excelName="+excelName
		+"&page="+page+"&rows="+rows;
		if(isportal == "true"){
			src += "&_ISPORTAL=true"
		}
		for(var queryName in queryParams){
			var queryValue = queryParams[queryName];
			if(queryValue){
				src+=("&"+queryName+"="+queryValue);
			}
		}
		$.layer({
			type : 2,
			maxmin : false,
			title : "导出EXCEL",
			area : ["500px", "150px"],
			iframe : {
				src : src
			}
		});
	},
	/**
	 * 获取表格的附件IDS
	 * @param {} gridId
	 */
	getAttachFileIds:function(gridId){
		var fileIds = [];
		var rows = $("#"+gridId).datagrid("getRows");
		if(rows.length > 0){
			for(var i = 0;i<rows.length;i++){
				fileIds.push(rows[i].FILE_ID);
			}
		}
		return fileIds;
	},
	/**
	 * 转换文件大小显示
	 * @param {} file
	 */
	convertFileSize:function(file){
		var sizeNames = [' Bytes', ' KB', ' MB', ' GB', ' TB', ' PB', ' EB', ' ZB', ' YB'];
		var i = Math.floor(Math.log(file.size)/Math.log(1024));
		var p = (i > 1) ? 2 : 0;
		var size = (file.size/Math.pow(1024, Math.floor(i))).toFixed(p) + sizeNames[i];
		return size;
	},
	/**
	 * 上传文件排队
	 * @param {} file
	 */
	fileQueued:function(file){
		try{
			var size = AppUtilNew.convertFileSize(file);
		} catch (ex) {
			this.debug(ex);
		}
	},
	/**
	 * 上传文件选择完毕
	 */
	fileDialogComplete:function(numFilesSelected, numFilesQueued){
		if(numFilesQueued>0){
			this.startUpload();
		}
	},
	/**
	 * 上传失败
	 * @param {} file
	 * @param {} errorCode
	 * @param {} message
	 */
	uploadError:function(file, errorCode, message){
		try{
			switch (errorCode) {
			case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
				alert("上传错误: " + message);
				this.debug("错误代码: HTTP错误, 文件名: " + file.name + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
				alert("上传失败");
				this.debug("错误代码: 上传失败, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.IO_ERROR:
				alert("服务器(IO)出错");
				this.debug("错误代码: IO 错误, 文件名: " + file.name + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
				alert("安全错误");
				this.debug("错误代码: 安全错误, 文件名: " + file.name + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
				alert("超出上传限制"); 
				this.debug("错误代码: 超出上传限制, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
				alert("无法验证，跳过上传 ");
				this.debug("错误代码: 文件验证失败, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
				// If there aren't any files left (they were all cancelled) disable the cancel button
	//			if (this.getStats().files_queued === 0) {
	//				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
	//			}
				//alert("取消上传");
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
				alert("停止上传");
				break;
			default:
				alert("未处理过错误");	 
				this.debug("错误代码: " + errorCode + ", 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
				break;
			}
			
		}catch(ex){
			this.debug(ex);
		}
	},
	/**
	 * 开始上传
	 * @param {} file
	 * @return {Boolean}
	 */
	uploadStart:function(file){
		return true;
	},
	uploadComplete:function(file){
		//上传完成后 继续上传下一个
		if (this.getStats().files_queued > 0) {
	        this.startUpload();
	    }
	},
	/**
	 * 删除已经上传的文件
	 * @param {} fileId
	 */
	removeUploadFile:function(fileId,singleFileDivId,singleFileFieldName,uploadTableId,uploadButtonId){
		parent.layer.confirm("您确定要删除该文件吗?", function(index) {
			parent.layer.close(index);
			$.post("fileAttachController.do?multiDel", {
						selectColNames :fileId
					}, function(responseText, status, xhr) {
						var resultJson = $.parseJSON(responseText);
						if (resultJson.success == true) {
							if(uploadTableId){
								var fileIds = [];
								var rowindex = 0;
								var rows = $("#"+uploadTableId).datagrid("getRows");
								if(rows.length > 0){
									for(var i = 0;i<rows.length;i++){
										if(rows[i].FILE_ID!=fileId){
											fileIds.push(rows[i].FILE_ID);
										}else{
											rowindex = i;
										}
									}
								}
								$("#"+uploadTableId).datagrid("deleteRow",rowindex);
								//console.log(fileIds)
								/*$("#"+uploadTableId).datagrid('load',{
									fileIds:fileIds
								});*/
								var fileCount = fileIds.length;
								$.each(SWFUpload.instances,function(n,swfObject) {   
								 	 if(swfObject.customSettings.id){
										if(swfObject.customSettings.id==uploadButtonId){
											swfObject.setStats({successful_uploads:fileCount});
										}
									}
						         }); 
							}else{
								$("#"+singleFileDivId).html("");
								$("input[name='"+singleFileFieldName+"']").val("");
							}
						} else {
							layer.msg(resultJson.msg, 3, 1);
						}
					});
		});
	},
	/**
	 * 下载附件
	 * @param {} fileId
	 */
	downLoadFile:function(fileId){
		window.location.href=__ctxPath+"/DownLoadServlet?fileId="+fileId;
	},
	/**
	 * 初始化上传控件
	 * @param {} config
	 */
	initUploadControl:function(config){
		//开始回填上传图片的内容
		if(config.uploadImageFieldName&&config.uploadImageId){
			var PHOTO_PATH = $("input[name='"+config.uploadImageFieldName+"']").val();
			if(PHOTO_PATH){
				$("#"+config.uploadImageId).attr("src",__attachFileUrl+PHOTO_PATH);
			}
		}
		//开始回填单个上传的文件内容
		if(config.singleFileId){
			$.post("fileAttachController.do?get", {
				fileId : config.singleFileId
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				var fileName = resultJson.FILE_NAME;
				var fileSize = resultJson.FILE_SIZE;
				var fileId = resultJson.FILE_ID;
				var singleFileFieldName = config.singleFileFieldName;
				if(fileName.length>30){
					fileName = fileName.substr(0,30)+"...";
					$("#"+config.singleFileDivId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+fileName+"&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"&nbsp;&nbsp;&nbsp;</div>");
					$("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtilNew.downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
				    $("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtilNew.removeUploadFile(\""+fileId+"\",\""+config.singleFileDivId+"\",\""+singleFileFieldName+"\");' >删除</a>");
				}else{
					$("#"+config.singleFileDivId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+fileName+"&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"&nbsp;&nbsp;&nbsp;</div>");
					$("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtilNew.downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
				    $("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtilNew.removeUploadFile(\""+fileId+"\",\""+config.singleFileDivId+"\",\""+singleFileFieldName+"\");' >删除</a>");
				}
			});
		}
		var uploadUserId = config.uploadUserId?config.uploadUserId:"";
		var busTableName = config.busTableName?config.busTableName:"";
		var busRecordId = config.busRecordId?config.busRecordId:"";
		var uploadPath = config.uploadPath;
		//单个文件大小限制
		var limit_size = config.limit_size?config.limit_size:"10 MB";
		//上传文件类型限制
		var file_types = config.file_types?config.file_types:"*.*;";
	    var file_upload_limit = (config.file_upload_limit!=null)?config.file_upload_limit:1;
	    var file_queue_limit = (config.file_queue_limit!=null)?config.file_queue_limit:1;
	    var button_placeholder_id = config.uploadButtonId;
	    var fileQueued = null;
	    if(config.uploadFileType&&config.uploadFileType=="image"){
	    	fileQueued = AppUtilNew.fileQueued;
	    }else if(config.singleFileDivId){
	    	fileQueued = function(file) {
				try{
					var size = AppUtilNew.convertFileSize(file);
					var singleFileDivId = config.singleFileDivId;
					$("#"+singleFileDivId).html("<div style='float:left;margin:0px auto; height:28px; line-height:28px;'>"+file.name+"&nbsp;&nbsp;&nbsp;&nbsp;"+size+"&nbsp;&nbsp;&nbsp;</div>");
					$("#"+singleFileDivId).append("<div style='width:100px; float:left; line-height:28px;'></div>");
					$("#"+singleFileDivId+" div").eq(1).progressbar({
						value:0
					});
				} catch (ex) {
					this.debug(ex);
				}
			}
	    }else if(config.uploadTableId){
	    	fileQueued = function(file) {
				try {
					var fileSize = AppUtilNew.convertFileSize(file);
					var fileType = file.type;
					fileType = fileType.substring(fileType.lastIndexOf(".")+1,fileType.length)
					$("#"+config.uploadTableId).datagrid('insertRow', {
						row : {
							FILE_ID:"0",
							FILE_NAME : "<a href='#' id='"+file.id+"_download' ><font color='blue'>"+file.name+"</font></a>",
							FILE_SIZE : fileSize,
							FILE_TYPE : fileType,
							SWF_FILEID:file.id,
							fileprogress : '<div id="' + file.id
									+ '"></div>',
							FILE_DELETE : "<a href='#' id='" + file.id
									+ "_delete'></a>"
						}
					});
					$("#" + file.id).progressbar({
								value : 0
							});

				} catch (ex) {
					this.debug(ex);
				}
			}
	    }
	    var uploadProgress = null;
	    if(config.singleFileDivId){
	    	uploadProgress =function(file,bytesLoaded, bytesTotal){
				try {
					var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
					percent = percent == 100 ? 99 : percent;
					$("#"+config.singleFileDivId+" div").eq(1).progressbar({
								value : percent
							});
				} catch (ex) {
					this.debug(ex);
				}
			}
	    }else if(config.uploadTableId){
	    	uploadProgress =function(file,bytesLoaded, bytesTotal){
				try {
					var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
					percent = percent == 100 ? 99 : percent;
					$("#" + file.id).progressbar({
								value : percent
							});
				} catch (ex) {
					this.debug(ex);
				}
			}
	    	
	    }
	    
	    var uploadSuccess = null;
	    if(config.uploadFileType&&config.uploadFileType=="image"){
	    	uploadSuccess = config.uploadSuccess;
	    }else if(config.singleFileDivId){
	    	uploadSuccess = function(file, serverData, responseReceived){
				 $("#"+config.singleFileDivId+" div").eq(1).progressbar({
					value : 100
				});
				var resultJson = $.parseJSON(serverData);
				var fileId = resultJson.fileId;
				$("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtilNew.downLoadFile(\""+fileId+"\");' >下载&nbsp;&nbsp;</a>");
			   $("#"+config.singleFileDivId).append("<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtilNew.removeUploadFile(\""+fileId+"\",\""+config.singleFileDivId+"\",\""+config.singleFileFieldName+"\");' >删除</a>");
			    if(config.uploadSuccess!=null){
			    	config.uploadSuccess.call(this,resultJson);
			    }
			    
			}
	    }else if(config.uploadTableId){
	    	uploadSuccess = function(file, serverData, responseReceived){
	    		var resultJson = $.parseJSON(serverData);
				var fileId = resultJson.fileId;
				var rowIndex = $("#"+config.uploadTableId).datagrid("getRowIndex",file.id);
				$("#"+config.uploadTableId).datagrid('updateRow',{
					index: rowIndex,
					row: {
						FILE_ID: fileId
					}
				});
				$("#"+file.id+"_download").attr("onclick","AppUtilNew.downLoadFile(\""+fileId+"\");")
				$("#"+file.id+"_delete").text('删除');
				$("#"+file.id+"_delete").attr("onclick","AppUtilNew.removeUploadFile(\""+fileId+"\",null,null,\""+config.uploadTableId+"\",\""+config.uploadButtonId+"\");")
			    $("#"+file.id).progressbar({
	                 value: 100
	            });
			}
	    }
	  
		var swfu = new SWFUpload({
			upload_url : __ctxPath+"/UploadServlet?uploadUserId="+uploadUserId+"&busTableName="+busTableName+"&uploadPath="+uploadPath+"&busRecordId="+busRecordId,
			flash_url : __ctxPath+"/plug-in/swfupload-2.2/swfupload.swf",
			flash9_url : __ctxPath+"/plug-in/swfupload-2.2/swfupload_fp9.swf",
			file_size_limit : limit_size,
			file_types : file_types,
			file_types_description : "All Files",
			file_upload_limit : file_upload_limit,
			file_queue_limit : file_queue_limit,
			//加入上传队列成功
			file_queued_handler : fileQueued,
			//加入上传队列失败
			file_queue_error_handler:function(file, errorCode, message){
				try {
					if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
						alert( (message == 0 ? "您已达到上传限制" : "您正在上传的文件过多。\n您最多能选择"+(message > 1 ? "上传"+ message+ "个文件。" : "一个文件。")));
						return false;
					}
					switch (errorCode) {
						case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT :
						    alert(file.name + "超出文件大小限制,最大只能上传"
									+ config.limit_size);
							break;
						case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE :
							alert("无法上传零字节文件:" + file.name);
							this.debug("错误代码: 零字节文件, 文件名: " + file.name + ", 文件尺寸: "
									+ file.size + ", 信息: " + message);
							break;
						case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE :
							alert("不支持的文件类型");
							this.debug("错误代码: 不支持的文件类型, 文件名: " + file.name + ", 文件尺寸: "
									+ file.size + ", 信息: " + message);
							break;
						default :
							if (file !== null) {
								alert("未处理的错误");
							}
							this.debug("错误代码: " + errorCode + ", 文件名: " + file.name
									+ ", 文件尺寸: " + file.size + ", 信息: " + message);
							break;
					}
				} catch (ex) {
					this.debug(ex);
		
				}
			},
			//加入上传队列处理完后触发 
			file_dialog_complete_handler : AppUtilNew.fileDialogComplete,
			//在文件往服务端上传之前触发
			upload_start_handler : AppUtilNew.uploadStart,
			//上传进度
			upload_progress_handler : uploadProgress,
			//上传失败，被终止或没成功触发
			upload_error_handler : AppUtilNew.uploadError,
			//上传成功触发，不管是否成功接收
			upload_success_handler : uploadSuccess,
			//上传完成时触发
			upload_complete_handler : AppUtilNew.uploadComplete,
			swfupload_loaded_handler:function(){
				if(config.uploadTableId){
					var rows = $("#"+config.uploadTableId).datagrid("getRows");
					swfu.setStats({successful_uploads:rows.length });
				}
				
			},
		    button_image_url : __ctxPath + "/plug-in/easyui-1.4/themes/icons/upload_swfupload1.png",
			button_placeholder_id :button_placeholder_id,
			button_width : 41,
			button_height : 22,
			button_text : '',
			button_text_left_padding : 0,
			button_text_top_padding : 0,
			button_action : config.uploadTableId?SWFUpload.BUTTON_ACTION.SELECT_FILES:SWFUpload.BUTTON_ACTION.SELECT_FILE,
			button_disabled : false,
			button_cursor : SWFUpload.CURSOR.HAND,
			button_window_mode:SWFUpload.WINDOW_MODE.TRANSPARENT,
			custom_settings : {
				limit_size : limit_size,
				file_upload_limit:file_upload_limit,
				id:config.uploadButtonId,
				uploadTableId:config.uploadTableId
			},
			debug : false
		});
		
	},
	/**
	 * 加入菜单资源到中央面板
	 * @param {} menuKey
	 * @param {} menuTitle
	 * @param {} url
	 */
	addMenuToCenterTab:function(menuKey,menuTitle,url){
		if($("#centerTabpanel").tabs('tabs').length>=13){
			layer.alert("抱歉,最多只能打开13个标签页!");
			return;
		}
		if ($("#centerTabpanel").tabs('exists', menuTitle)) {
			$('#centerTabpanel').tabs('select', menuTitle);
		} else {
			$('#centerTabpanel').tabs('add', {
				title:menuTitle,    
				href:url,  
	    		closable:true
			});
		}
	},
	/**
	 * 初始化弹出层表单
	 * @param {} formId:表单ID
	 * @param {} onValidationComplete:验证完毕调用函数
	 * @param {} ajaxValidTableName:需要进行ajax验证的表名称
	 */
	initWindowForm:function(formId,onValidationComplete,ajaxValidTableName,validPromptPosition){
		//设置formDiv的高度
		/*var height = $("body").height()-47;
		var style = "height:"+height+"px;overflow: auto;";
		$("#"+formId+"Div").attr("style",style);*/
	  	//绑定验证引擎
	  	if(onValidationComplete){
	  		if(ajaxValidTableName){
	  			$.validationEngineLanguage.allRules.ajaxVerifyValueExist.extraData = "tableName="+ajaxValidTableName;
	  		}
	  		$("#"+formId).validationEngine("attach",{ 
			     promptPosition:validPromptPosition?validPromptPosition:"topLeft",
			     autoPositionUpdate:true,
		         autoHidePrompt: true,            //自动隐藏提示信息
		         autoHideDelay: "3000",           //自动隐藏提示信息的延迟时间(单位：ms)   
		         fadeDuration: "0.5",             //隐藏提示信息淡出的时间
		         maxErrorsPerField: "1",          //单个元素显示错误提示的最大数量，值设为数值。默认 false 表示不限制。  
		         showOneMessage: false,           //是否只显示一个提示信息
		         onValidationComplete:onValidationComplete
		     });
	  	}
	},
	/**
	 * 关闭弹出层
	 */
	closeLayer:function(){
	   art.dialog.close();
	},
	
	closeAllLayer:function(){
		var list = art.dialog.list;
		for (var i in list) {
			list[i].close();
		};
	},
	/**
	 * 重置EasyUI的dagagrid查询条件
	 * @param {} searchToolBarId
	 */
	gridSearchReset:function(searchFormName){
		$("form[name='"+searchFormName+"']").form("reset");
	},
	/**
	 * 去除字符串的前后空格
	 * @param {} str
	 * @return {}
	 */
	trim:function(str){
		str = str.replace(/(^\s*)|(\s*$)/g, "");
		str = str.replace(/^[\s　\t]+|[\s　\t]+$/, ""); 
		return str;
	},
	/**
	 * easyUI grid做查询API
	 * @param {} searchToolBarId
	 * @param {} gridId
	 */
	gridDoSearch:function(searchToolBarId,gridId){
		var queryParams = {};
		var array = $("#"+searchToolBarId+" [name]");
		for(var i =0;i<array.length;i++){
			var value = array.eq(i).val();
			if(value!=null&&value!=""){
				value = AppUtilNew.trim(value);
				var name = array.eq(i).attr("name");
				var className = array.eq(i).attr("class");
				if(className=="laydate-icon"){
					if(value.indexOf(" ")==-1){
						if(name.indexOf(">=")!=-1){
							queryParams[name] = value+" 00:00:00";
						}else if(name.indexOf("<=")!=-1){
							queryParams[name] = value+" 23:59:59";
						}else{
							queryParams[name] = value;
						}
					}else{
						queryParams[name] = value;
					}
				}else{
					queryParams[name] = value;
				}
				
			}
		}
		$("#"+gridId).datagrid('load',queryParams);
	},
	
	/**
	 * easyUI grid做查询API
	 * @param {} searchToolBarId
	 * @param {} gridId
	 */
	gridsDoSearch:function(searchToolBarId){
		var queryParams = {};
		var array = $("#"+searchToolBarId+" [name]");
		for(var i =0;i<array.length;i++){
			var value = array.eq(i).val();
			if(value!=null&&value!=""){
				value = AppUtilNew.trim(value);
				var name = array.eq(i).attr("name");
				var className = array.eq(i).attr("class");
				if(className=="laydate-icon"){
					if(value.indexOf(" ")==-1){
						if(name.indexOf(">=")!=-1){
							queryParams[name] = value+" 00:00:00";
						}else if(name.indexOf("<=")!=-1){
							queryParams[name] = value+" 23:59:59";
						}
					}else{
						queryParams[name] = value;
					}
				}else{
					queryParams[name] = value;
				}
				
			}
		}
		
		for(var i=1;i<arguments.length;i++){
			$("#"+arguments[i]).datagrid('load',queryParams);
		}
		//$("#"+gridId).datagrid('load',queryParams);
	},
	/**
	 * 不带进度条的AJAX请求
	 * @param {} config
	 */
	ajaxPost:function(config){
		 $.post(config.url,config.params, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if(config.callback!=null){
				config.callback.call(this,resultJson);
			}
		});
	},
	/**
	 * 待进度条的AJAX请求
	 * @param {} config
	 */
	ajaxProgress:function(config){
		var layload = layer.load('正在提交请求中…');
		 $.post(config.url,config.params, function(responseText, status, xhr) {
			layer.close(layload);
			var resultJson = $.parseJSON(responseText);
			if(config.callback!=null){
				config.callback.call(this,resultJson);
			}
		});
	},
	/**
	 * 获取被编辑的表格记录ID值
	 * @param {} gridId
	 */
	getEditDataGridRecord:function(gridId){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			var colName = $dataGrid.datagrid("options").idField; 
			var idValue = eval("rowsData[0]."+colName);
			return idValue;
		}
	},
	/**
	 * 获取被编辑的表格记录ID值
	 * @param {} gridId
	 */
	getEditDataGridRecordValue:function(gridId,colName){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			//var colName = $dataGrid.datagrid("options").idField; 
			var idValue = eval("rowsData[0]."+colName);
			return idValue;
		}
	},
	/**
	 * 获取被编辑的表格记录ID值
	 * @param {} gridId
	 */
	getStatusDataGridRecord:function(gridId){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid("getChecked");
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			art.dialog({
				content: "只能选择一条被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else{
			var i_status = eval("rowsData[0].STATUS");
			if(i_status == 2){
				art.dialog({
					content: "选择的记录正在审核,不可修改!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 3){
				art.dialog({
					content: "选择的记录通过审核,不可修改!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 5){
				art.dialog({
					content: "选择的记录已关闭,不可修改!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				var colName = $dataGrid.datagrid("options").idField; 
				var idValue = eval("rowsData[0]."+colName);
				return idValue;
			}
		}
	},
	/**
	 * 删除树形节点
	 * @param {} config
	 */
	deleteTreeNode:function(config){
		if(config.noAllowDeleteIfHasChild){
			if(config.treeNode.children&&config.treeNode.children.length>0){
				art.dialog({
					content: "该节点存在孩子节点!不能进行删除!",
					icon:"warning",
				    ok: true
				});
				return;
			}
		}
		art.dialog.confirm("您确定要删除掉该记录吗?", function() {
			var layload = layer.load("正在提交请求中…");
			$.post(config.url, {
				selectColNames : config.treeNode.id
			}, function(responseText, status, xhr) {
				layer.close(layload);
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success == true) {
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					if(config.callback!=null){
						config.callback.call(this,resultJson);
					}else{
						$.fn.zTree.getZTreeObj(config.treeId)
							.reAsyncChildNodes(null, "refresh");
					}
				} else {
					art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
			});
		}, function() {
			
		});
	},
	/**
	 * 获取checkedbox选中的值
	 * @param {} name
	 * @return {}
	 */
	getCheckBoxCheckedValue:function(name){
		var checks = $("input[name='"+name+"'][type='checkbox']");
		var checkBoxValues = "";
		$.each(checks,function(index,checkboxobj) {   
			  if(checkboxobj.checked){
		            checkBoxValues += checkboxobj.value+ ","; //获取被选中的值
			  }
        }); 
        return checkBoxValues;
	},
	/**
	 * 获取当前登录用户信息
	 */
	getLoginUserInfo:function(){
		var userJsonInfo=$("#loginUserInfoJson").val();
		var __curUser=JSON2.parse(userJsonInfo);
		return __curUser;
	},
	/**
	 * 判断当前用户是否拥有资源KEY
	 * @param {} resKey
	 * @return {Boolean}
	 */
	isGranted:function(resKey){
		if(!__curUser){
			__curUser = AppUtilNew.getLoginUserInfo();
		}
		var resKeys = __curUser.resKeys;
		if(resKeys.indexOf('__ALL')!=-1){
			return true;
		}
		if(resKeys.indexOf(resKey)!=-1){
			return true;
		}
	},
	/**
	 * 初始化某个父亲节点下操作按钮的权限
	 * @param {} parentDomId
	 */
	initAuthorityRes:function(parentDomId){
		var operateButtons = $("#"+parentDomId+" a[resKey]");
		for(var i =0;i<operateButtons.length;i++){
			var operateBtn = operateButtons.eq(i);
			var resKey = operateBtn.attr("resKey");
			if(!AppUtilNew.isGranted(resKey)){
				operateBtn.attr("style","display:none;");
			}
		}
	},
	/**
	 * 多选操作表格方法
	 * @param {} url
	 * @param {} gridId
	 * @param {} callback
	 */
	multiOperateDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录",
				icon:"warning",
			    ok: true
			});
		}else{
			art.dialog.confirm("您确定执行该操作吗?", function() {
				var layload = layer.load('正在提交请求中…');
				var colName = $dataGrid.datagrid('options').idField;  
				var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				$.post(url,{
					   selectColNames:selectColNames
				    }, function(responseText, status, xhr) {
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							if(callback!=null){
								callback.call(this,resultJson);
							}else{
								$dataGrid.datagrid('reload');
							}
						}else{
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
			});
		}
	},
	/**
	 * 删除datagrid上的记录
	 * @param {} url:远程URL
	 * @param {} gridId:gridId
	 * @param {} callback:回调函数
	 */
	deleteDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			art.dialog.confirm("您确定要删除该记录吗?", function() {
				var layload = layer.load('正在提交请求中…');
				var colName = $dataGrid.datagrid('options').idField;  
				var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				$.post(url,{
					   selectColNames : selectColNames
				    }, function(responseText, status, xhr) {
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							if(callback!=null){
								callback.call(this,resultJson);
							}else{
								$dataGrid.datagrid('reload');
							}
						}else{
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
			});
		}
	},
	/**
	 * 删除datagrid上的记录
	 * @param {} url:远程URL
	 * @param {} gridId:gridId
	 * @param {} callback:回调函数
	 */
	deleteStatusDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var i_status = eval("rowsData[0].STATUS");
			if(i_status == 2){
				art.dialog({
					content: "选择的记录正在审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 3){
				art.dialog({
					content: "选择的记录通过审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 5){
				art.dialog({
					content: "选择的记录已关闭,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				art.dialog.confirm("您确定要删除该记录吗?", function() {
					var layload = layer.load('正在提交请求中…');
					var colName = $dataGrid.datagrid('options').idField;  
					var selectColNames = "";
					for ( var i = 0; i < rowsData.length; i++) {
						if(i>0){
							selectColNames+=",";
						}
						selectColNames+=eval('rowsData[i].'+colName);
					}
					$.post(url,{
						   selectColNames:selectColNames
					    }, function(responseText, status, xhr) {
					    	layer.close(layload);
					    	var resultJson = $.parseJSON(responseText);
							if(resultJson.success == true){
								art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
								    ok: true
								});
								if(callback!=null){
									callback.call(this,resultJson);
								}else{
									$dataGrid.datagrid('reload');
								}
							}else{
								art.dialog({
									content: resultJson.msg,
									icon:"error",
								    ok: true
								});
							}
					});
				});			
			}
		}
	},
	/**
	 * 删除datagrid上的记录
	 * @param {} url:远程URL
	 * @param {} gridId:gridId
	 * @param {} callback:回调函数
	 */
	deleteColumnStatusDataGridRecord:function(url,gridId,callback){
		var $dataGrid = $("#"+gridId);
		var rowsData = $dataGrid.datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			var i_status = eval("rowsData[0].STATUS");
			if(i_status == 2){
				art.dialog({
					content: "选择的记录正在审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 3){
				art.dialog({
					content: "选择的记录通过审核,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else if(i_status == 5){
				art.dialog({
					content: "选择的记录已关闭,不可删除!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				art.dialog.confirm("您确定要删除该监察点所有表字段记录吗?", function() {
					var layload = layer.load('正在提交请求中…');
					var colName = $dataGrid.datagrid('options').idField;  
					var selectColNames = "";
					for ( var i = 0; i < rowsData.length; i++) {
						if(i>0){
							selectColNames+=",";
						}
						selectColNames+=eval('rowsData[i].'+colName);
					}
					$.post(url,{
						   selectColNames:selectColNames
					    }, function(responseText, status, xhr) {
					    	layer.close(layload);
					    	var resultJson = $.parseJSON(responseText);
							if(resultJson.success == true){
								art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
								    ok: true
								});
								if(callback!=null){
									callback.call(this,resultJson);
								}else{
									$dataGrid.datagrid('reload');
								}
							}else{
								art.dialog({
									content: resultJson.msg,
									icon:"error",
								    ok: true
								});
							}
					});
				});			
			}
		}
	},
	/**
	 * 禁用表单自动提交
	 */
	disableFormAutoSubmit:function(){
		// 禁用浏览器FORM自动提交
		document.onkeydown = function(event) {  
	        var target, code, tag;  
	        if (!event) {  
	            event = window.event; //针对ie浏览器  
	            target = event.srcElement; 
	            if(target.name){
	            	var controlName = target.name;
	            	if(controlName.indexOf("Q_")!=-1){
	            		 code = event.keyCode;  
			             if (code == 13) {  
			                tag = target.tagName;  
			                if (tag == "TEXTAREA") { return true; }  
			                else { return false; }  
			             }  
	            	}
	            }
	        }  
	        else {  
	            target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	            if(target.name){
	            	var controlName = target.name;
	            	if(controlName.indexOf("Q_")!=-1){
	            		code = event.keyCode;  
			            if (code == 13) {  
			                tag = target.tagName;  
			                if (tag == "INPUT") { return false; }  
			                else { return true; }   
			            }  
	            	}
	            }
	            
	        }  
	    };  
	},
	/**
	 * 设置session过期
	 */
	setSessionTimeOut:function(){
		/**
		 * 覆盖jquery ajax默认请求设置
		 */
		$.ajaxSetup({
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			cache : false,
			type:"POST",
			complete : function(XHR, TS) {
				if(XHR.responseText&&XHR.responseText=="sessiontimeout"){
					alert("会话已过期，请重新登录!");
					window.top.location.href = __ctxPath;
				}
			}
		}); 
	},
	/**
	 * 扩展easyuidatagrid
	 */
	extendsEasyDataGrid:function(){
		/**
		 * 扩展easyui dagagrid刷新之前去除勾选行
		 */
		$.extend($.fn.datagrid.defaults, {
			onBeforeLoad:function(params){
				$(this).datagrid('clearChecked');
				return true;
			}
		});
		/**
		 * 扩展easyui dagagrid显示被截断文字内容
		 */
		$.extend($.fn.datagrid.methods, {
		    /**
		     * 开打提示功能    
		     * @param {} jq    
		     * @param {} params 提示消息框的样式    
		     * @return {}    
		     */    
		    doCellTip:function (jq, params) {      
		        function showTip(showParams, td, e, dg) {      
		            //无文本，不提示。      
		            if ($(td).text() == "") return;      
		            params = params || {};   
		            var options = dg.data('datagrid');      
		            showParams.content = '<div class="tipcontent">' + showParams.content + '</div>';     
		            $(td).tooltip({      
		                content:showParams.content,      
		                trackMouse:true,      
		                position:params.position,      
		                onHide:function () {      
		                    $(this).tooltip('destroy');      
		                },      
		                onShow:function () {   
		                    var tip = $(this).tooltip('tip');      
		                    if(showParams.tipStyler){      
		                        tip.css(showParams.tipStyler);      
		                    }      
		                    if(showParams.contentStyler){      
		                        tip.find('div.tipcontent').css(showParams.contentStyler);      
		                    }   
		                }      
		            }).tooltip('show');      
		     
		        };      
		        return jq.each(function () {      
		            var grid = $(this);      
		            var options = $(this).data('datagrid');      
		            if (!options.tooltip) {      
		                var panel = grid.datagrid('getPanel').panel('panel');      
		                panel.find('.datagrid-body').each(function () {      
		                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;      
		                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td[field]', {      
		                        'mouseover':function (e) {   
		                            //if($(this).attr('field')===undefined) return;      
		                            var that = this;   
		                            var setField = null;   
		                            if(params.specialShowFields && params.specialShowFields.sort){   
		                                for(var i=0; i<params.specialShowFields.length; i++){   
		                                    if(params.specialShowFields[i].field == $(this).attr('field')){   
		                                        setField = params.specialShowFields[i];   
		                                    }   
		                                }   
		                            }   
		                            if(setField==null){   
		                                options.factContent = $(this).find('>div').clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body');      
		                                var factContentWidth = options.factContent.width();      
		                                params.content = $(this).text();      
		                                if (params.onlyShowInterrupt) {      
		                                    if (factContentWidth > $(this).width()) {
		                                    	var str1 = params.content ;
		                                    	var str2 = "";
		                                    	var n=25;
		                                    	for(var i = 0 ; str1.length>0;i=i+n){
		                                    		s1 = str1.substring(0,n);
		                                    		str1 = str1.substring(n,str1.length);
		                                    		str2+=s1+"<br>";
		                                    	}
		                                    	params.content = str2;
		                                    	
		                                        showTip(params, this, e, grid);      
		                                    }      
		                                } else {      
		                                    showTip(params, this, e, grid);      
		                                }    
		                            }else{   
		                                panel.find('.datagrid-body').each(function(){   
		                                    var trs = $(this).find('tr[datagrid-row-index="' + $(that).parent().attr('datagrid-row-index') + '"]');   
		                                    trs.each(function(){   
		                                        var td = $(this).find('> td[field="' + setField.showField + '"]');   
		                                        if(td.length){   
		                                            params.content = td.text();   
		                                        }   
		                                    });   
		                                });   
		                                showTip(params, this, e, grid);   
		                            }   
		                        },      
		                        'mouseout':function (e) {      
		                            if (options.factContent) {      
		                                options.factContent.remove();      
		                                options.factContent = null;      
		                            }      
		                        }      
		                    });      
		                });      
		            }      
		        });      
		    },      
		    /**
		     * 关闭消息提示功能    
		     * @param {} jq    
		     * @return {}    
		     */     
		    cancelCellTip:function (jq) {      
		        return jq.each(function () {      
		            var data = $(this).data('datagrid');      
		            if (data.factContent) {      
		                data.factContent.remove();      
		                data.factContent = null;      
		            }      
		            var panel = $(this).datagrid('getPanel').panel('panel');      
		            panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')      
		        });      
		    }
		});
		$.extend($.fn.datagrid.defaults, {
		    onLoadSuccess:function() {
				$(this).datagrid('doCellTip', {
					onlyShowInterrupt : true, // 是否只有在文字被截断时才显示tip，默认值为false
					position : 'bottom' ,// tip的位置，可以为top,botom,right,left
					tipStyler:{"width":"200px"},
					contentStyler:{"width":"200px"}
					//tipStyler:tip内容的样式，注意要符合jquery css函数的要求。
					//contentStyler:整个tip的样式，注意要符合jquery css函数的要求。
				});
			}
		});
	},
	/***
	 *  判断是否支持canvas
	 * ***/
	canvasSupport:function(){
		return !!document.createElement('canvas').getContext;
	}
	
});

$(function(){
   AppUtilNew.setSessionTimeOut();
});
