var infoForm = {
		
	moduleDialog: null,
	
	moduleTree: null,
	
	moduleClickHandler: function(_e) {
		this.showModuleDialog(_e.target);
	},
	
	showModuleDialog: function(_bindDom) {
		if (!this.moduleTree) {
			this.initModuleTree(_bindDom);
		}
		if (this.moduleDialog) {
			this.moduleDialog.dialog("open");
		} else {
			this.initModuleDialog(_bindDom, this.moduleTree);
		}
	},
	
	initModuleDialog: function(_bindDom, _tree) {
		var thiz = $("#artModuleDialog").dialog({
			title: "所属栏目",
			width: 400,
			height: 340,
			modal: true,
			shadow: false,
			buttons: [ { 
				text: "确定", 
				handler: function() { 
					var node = _tree.getSelectedNodes()[0];
					if (node && !node.isParent) {
						$(_bindDom).val(node.text);
						$("#" + _bindDom.id + "Hidden").val(node.id);
						$("#hasSummaryHidden").val(node.hasSummary);
						thiz.dialog("close"); 
					} else {
						alert("请选择具体栏目！");
					}
				} 
			}, { 
				text: "取消", 
				handler: function() { 
					thiz.dialog("close"); 
				} 
			} ]
		});
		this.moduleDialog = thiz;
	},
	
	initModuleTree: function(_bindDom) {
		var thiz = $.fn.zTree.init($("#artModuleTree"), {
	        async: {
	            enable: true,
	            autoParam: ["id", "website"],
	            dataType: "json",
	            url: rootPath + "/article/rolePanelTreeForNewModule.do"
	        },
	        callback: {
	        	onAsyncSuccess: function(_event, _treeId, _treeNode, _msg) {
	        		var _id = $("#" + _bindDom.id + "Hidden").val();
	        		$.each(_msg, function(idx, node) {
	        			if (node.id === _id) {
	        				thiz.selectNode(thiz.getNodeByParam("id", _id));
	        			}
	        		}); 
	        	}
	        },
			data: {
				key: {
					name: "text"
				},
				simpleData: {
					enable: true,
					rootPid: "0"
				}
			},
			view: {
				selectedMulti: false
			}
		});
		this.moduleTree = thiz;
	},

	
	refModuleClickHandler: function(_e) {
		var TARGET_MODULE_ID = $("input[name='TARGET_MODULE_ID']").val();
		var MODULE_ID = $("input[name='MODULE_ID']").val();
		parent.$.dialog.open("moduleController/selector.do?moduleIds="+TARGET_MODULE_ID+"&allowCount=1&checkCascadeY=&"
				+"checkCascadeN=", {
			title : "栏目选择器",
			width:"600px",
			lock: true,
			resize:false,
			height:"500px",
			close: function () {
				var selectModuleInfo = art.dialog.data("selectModuleInfo");
				if(selectModuleInfo){
					if(MODULE_ID==selectModuleInfo.moduleIds){
						art.dialog({
							content: "不能选择自己!",
							icon:"warning",
							ok: true
						});
					}else{
						$("input[name='TARGET_MODULE_ID']").val(selectModuleInfo.moduleIds);
						$("input[name='TARGET_MODULE_NAME']").val(selectModuleInfo.moduleNames);
					}
					art.dialog.removeData("selectModuleInfo");
				}
			}
		}, false);
	},


    shareModuleClickHandler: function(_e) {
        var TARGET_MODULE_ID = $("input[name='SHARE_MODULEID']").val();
        var MODULE_ID = $("input[name='MODULE_ID']").val();
        parent.$.dialog.open("moduleController/selector.do?moduleIds="+TARGET_MODULE_ID+"&allowCount=50&checkCascadeY=&"
            +"checkCascadeN=", {
            title : "分享栏目选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
                var selectModuleInfo = art.dialog.data("selectModuleInfo");
                if(selectModuleInfo){
                    if(MODULE_ID==selectModuleInfo.moduleIds){
                        art.dialog({
                            content: "不能选择自己!",
                            icon:"warning",
                            ok: true
                        });
                    }else{
                        $("input[name='SHARE_MODULEID']").val(selectModuleInfo.moduleIds);
                        $("input[name='SHARE_MODULENAME']").val(selectModuleInfo.moduleNames);
                    }
                    art.dialog.removeData("selectModuleInfo");
                }
            }
        }, false);
    },
	initRefModuleTree: function(_bindDom) {
		var thiz = $.fn.zTree.init($("#artRefModuleTree"), {
			async: {
				enable: true,
				autoParam: ["id", "website"],
				dataType: "json",
				url: rootPath + "/article/rolePanelTreeForNewModule.do"
			},
	        callback: {
	        	onAsyncSuccess: function(_event, _treeId, _treeNode, _msg) {
	        		var _ids = $("#" + _bindDom.id + "Hidden").val().split(",");
	        		$.each(_msg, function(idx, node) {
	        			for (var i = 0; i < _ids.length; i++) {
	        				if (node.id === _ids[i]) {
	        					thiz.checkNode(thiz.getNodeByParam("id", _ids[i]));
	        				}
	        			}
	        		}); 
	        	}
	        },
			check: {
				enable: true
			},
			data: {
				key: {
					name: "text"
				},
				simpleData: {
					enable: true,
					rootPid: "0"
				}
			}
		});	
		this.refModuleTree = thiz;
	},
	
	specialDialog: null,
	
	specialTree: null,
	
	specialClickHandler: function(_e) {
		this.showSpecialDialog(_e.target);
	},
	
	showSpecialDialog: function(_bindDom) {
		if (!this.specialTree) {
			this.initSpecialTree(_bindDom);
		}
		if (this.specialDialog) {
			this.specialDialog.dialog("open");
		} else {
			this.initSpecialDialog(_bindDom, this.specialTree);
		}
	},
	
	initSpecialDialog: function(_bindDom, _tree) {
		var thiz = $("#artSpecialDialog").dialog({
			title: "引至专题",
			width: 400,
			height: 340,
			modal: true,
			shadow: false,
			buttons: [ { 
				text: "确定", 
				handler: function() { 
					var nodes = _tree.getCheckedNodes(true);
					var arrId = [];
					var arrText = [];
					$.each(nodes, function(idx, o) {
						if (o.leaf) {
							arrId.push(o.id);
							arrText.push(o.text);
						}
					});
					$(_bindDom).val(arrText.join("、"));
					$("#" + _bindDom.id + "Hidden").val(arrId.join(","));
					thiz.dialog("close"); 
				} 
			}, { 
				text: "取消", 
				handler: function() { 
					thiz.dialog("close"); 
				} 
			} ]
		});
		this.specialDialog = thiz;
	},
	
	initSpecialTree: function(_bindDom) {
		var thiz = $.fn.zTree.init($("#artSpecialTree"), {
			async: {
				enable: true,
				autoParam: ["id", "website"],
				dataType: "json",
				url: rootPath + "/article/treeSpecialModule.do?websiteId=" + $("#moduleNameHidden").val()
			},
	        callback: {
	        	onExpand: function(_event, _treeId, _treeNode) {
	        		var _ids = $("#" + _bindDom.id + "Hidden").val().split(",");
	        		$.each(_treeNode.children, function(idx, node) {
	        			for (var i = 0; i < _ids.length; i++) {
	        				if (node.id === _ids[i]) {
	        					thiz.checkNode(thiz.getNodeByParam("id", _ids[i]), true);
	        				}
	        			}
	        		}); 
	        	},
	        	onAsyncSuccess: function(_event, _treeId, _treeNode, _msg) {
	        		var _ids = $("#" + _bindDom.id + "Hidden").val().split(",");
	        		$.each(_msg[0].children[0].children, function(idx, node) {
	        			for (var i = 0; i < _ids.length; i++) {
	        				if (node.id === _ids[i]) {
	        					thiz.checkNode(thiz.getNodeByParam("id", _ids[i]));
	        				}
	        			}
	        		}); 
	        	}
	        },			
			check: {
				enable: true
			},
			data: {
				key: {
					name: "text"
				},
				simpleData: {
					enable: true,
					rootPid: "0"
				}
			}
		});
		this.specialTree = thiz;
	},
	
	sourceDialog: null,
	
	sourceTree: null,
	
	sourceClickHandler: function(_e) {
		if($('#articleSourceKey').val() == 'FZINFODEPTS'){
			this.showSourceDialog(_e.target);
		}
	},
	
	showSourceDialog: function(_bindDom) {
		if (!this.sourceTree) {
			this.initSourceTree(_bindDom);
		}
		if (this.sourceDialog) {
			this.sourceDialog.dialog("open");
		} else {
			this.initSourceDialog(_bindDom, this.sourceTree);
		}
	},
	
	initSourceDialog: function(_bindDom, _tree) {
		var thiz = $("#infoSourceDialog").dialog({
			title: "信息来源",
			width: 300,
			height: 450,
			modal: true,
			shadow: false,
			buttons: [ { 
				text: "确定", 
				handler: function() { 
					var nodes = _tree.getCheckedNodes(true);
					var arrId = [];
					var arrText = [];
					$.each(nodes, function(idx, o) {
						if (o.leaf) {
							arrId.push(o.id);
							arrText.push(o.text);
						}
					});
					$(_bindDom).val(arrText.join("、"));
					$(_bindDom).validatebox("validate");
					thiz.dialog("close"); 
				} 
			}, { 
				text: "取消", 
				handler: function() { 
					thiz.dialog("close"); 
				} 
			} ]
		});
	},
	
	initSourceTree: function(_bindDom) {
		var thiz = $.fn.zTree.init($("#infoSourceTree"), {
	        async: {
	            enable: true,
	            dataType: "json",
	            url: rootPath + "/system/treeDictionary.do?typeKey=InfoDepts"
	        },
	        callback: {
	        	onAsyncSuccess: function(_event, _treeId, _treeNode, _msg) {
	        		thiz.setChkDisabled(thiz.getNodeByParam("id", "-1"), true);
	        		if($("#" + _bindDom.id).val() != ""){
	        			var sources = new Array();
		        		$.each(_msg[0].children, function(idx, node) {
		        			if(node.id != '-1'){
		        				sources.push(node.text);
		        			}
		        		});
		        		var _texts = $("#" + _bindDom.id).val().split("、");
		        		for (var i = 0; i < _texts.length; i++) {
		        			if(sources.join("、").indexOf(_texts[i]) != -1){
		        				thiz.checkNode(thiz.getNodeByParam("text", _texts[i]), true, true);
		        			}else{
		        				var otherNode = thiz.getNodeByParam("id", "-1");
		        				otherNode.text = _texts[i];
		        				thiz.updateNode(otherNode);
		        				thiz.setChkDisabled(otherNode, false);
		        				thiz.checkNode(otherNode, true, true);
		        			}
		        		}
	        		}
	        	},
	        	onDblClick: function(_event, _treeId, _treeNode) {
	        		if(_treeNode != null && _treeNode.id === '-1'){
	        			if(_treeNode.text == "其他（请双击编辑）"){
	        				_treeNode.text = "";
	        				thiz.updateNode(_treeNode);
	        			}
	        			thiz.editName(_treeNode);
	        		}
	        	},
	        	onRename: function(_event, _treeId, _treeNode, _isCancel){
	        		if(!_isCancel){
	        			if(_treeNode != null && _treeNode.id === '-1'){
	        				if(_treeNode.text != ''){
	        					thiz.setChkDisabled(_treeNode, false);
	        				}else{
	        					thiz.setChkDisabled(_treeNode, true);
	        				}
	        			}
	        		}
	        	}
	        },
			check: {
				enable: true,
				chkboxType: {
					"Y": "ps", 
					"N": "ps" 
				}
			},
			data: {
				key: {
					name: "text"
				},
				simpleData: {
					enable: true,
					rootPid: "0"
				}
			}
		});
		this.sourceTree = thiz;
	},
	
	contentTypeChangeHanlder: function(_e) {
		if (_e.target.value === "0") { // 文本
			$("input[name='LINKURL']").val("");
			$("#trLinkUrl").hide();
            $("#eWebEditContent").css('visibility', '');
			$("input[name='FILEURL']").val("");
			$("#trFileUrl").hide();
			$("input[name='VIDEOURL']").val("");
			$("#trVideoUrl").hide();
			$("input[name='LINKURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
			$("input[name='FILEURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
			$("input[name='VIDEOURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
		} else if (_e.target.value === "1") { // 文件
			$("input[name='LINKURL']").val("");
			$("#trLinkUrl").hide();
            $("#eWebEditContent").css('visibility', 'hidden');
			$("input[name='FILEURL']").val("");
			$("#trFileUrl").show();
			$("input[name='VIDEOURL']").val("");
			$("#trVideoUrl").hide();
			$("input[name='LINKURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
			$("input[name='FILEURL']").validatebox({
				required: true,
				validType: 'maxLength[2000]'
			});  
			$("input[name='VIDEOURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			}); 
		} else if (_e.target.value === "2") { // 网址
			$("input[name='LINKURL']").val("");
			$("#trLinkUrl").show();
			$("input[name='FILEURL']").val("");
			$("#trFileUrl").hide();
			$("input[name='VIDEOURL']").val("");
			$("#trVideoUrl").hide();
			$("input[name='LINKURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
			$("input[name='FILEURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
			$("input[name='VIDEOURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
		} else if (_e.target.value === "3") { // 视频
			$("input[name='LINKURL']").val("");
			$("#trLinkUrl").hide();
			$("input[name='FILEURL']").val("");
			$("#trFileUrl").hide();
			$("input[name='VIDEOURL']").val("");
			$("#trVideoUrl").show();
			$("input[name='LINKURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
			$("input[name='FILEURL']").validatebox({
				required: false,
				validType: 'maxLength[2000]'
			});  
			$("input[name='VIDEOURL']").validatebox({
				required: true,
				validType: 'maxLength[2000]'
			});  
		}
	},
	
	accordionSwtich: function(_idx) {
		$(".editor_table").hide().eq(_idx).show();
	},
	
	fileClickHandler: function(_e) {
		this.showAttachDialog();
	},
	
	attachDialog: null,
	
	attachForAll: null,
	
	showAttachDialog: function() {
		if (this.attachDialog) {
			this.attachDialog.dialog("open");
		} else {
			this.initAttachDialog();
		}
		if (!this.attachForAll) {
			this.initAttachPanel();
		}
	},
	
	initAttachDialog: function() {
		var thiz = $("#attachDialog").dialog({
			title: "附件列表",
			width: 570,
			height: 400,
			modal: true,
			shadow: false,
			buttons: [ { 
				text: "关闭", 
				handler: function() { 
					thiz.dialog("close");
				} 
			}]
		});
		this.attachDialog = thiz;
	},
	
	initAttachPanel: function() {
		var thiz = $("#attachPanel").uploadPanel({
			"swf": rootPath + "/plug-in/jquery/plugin/jqueryUpload/swfupload/swfupload.swf",
	    	"rootPath": rootPath,
	    	"tableName": "t_article_content",
	    	"recordId": $("input[name='CONTENT_ID']").val(),
	    	"userId": $("input[name='CREATEUSERID']").val(),
	    	"isMultiple": true,
	    	"fit": true,
	    	"fileSize": "100MB",
	    	"tip": "单文件大小限制100MB",
	    	"onListLoaded": function(paths) {
	    		//$("input[name='LINKURL']").val(paths);
	    	}
	    });
		this.attachForAll = thiz;
	},
	
	attachPanelForMedia: null,
	
	initAttachPanelForMedia: function() {
		var thiz = $("#mediaList").uploadPanel({
			"swf": rootPath + "/plug-in/jquery/plugin/jqueryUpload/swfupload/swfupload.swf",
	    	"rootPath": rootPath,
	    	"tableName": "t_article_content",
	    	"recordId": $("input[name='CONTENT_ID']").val(),
	    	"userId": $("input[name='CREATEUSERID']").val(),
	    	"isMultiple": false,
	    	"fileTypes": "*.flv",
	    	"fileTypesDesc": "flv视频文件",
	    	"fileSize": "2GB",
	    	"fit": true,
	    	"btnAddName": "上传视频",
	    	"btnDelName": "删除视频",
	    	"tip": "只能上传一个最大2GB的flv格式的视频文件",
	    	"onListLoaded": function(paths) {
	    		$("input[name='LINKURL']").val(paths);
	    	}
	    });
		this.attachPanelForMedia = thiz;
	},
	
	attachPanelForFile: null,
	
	initAttachPanelForFile: function() {
		var thiz = $("#fileList").uploadPanel({
			"swf": rootPath + "/plug-in/jquery/plugin/jqueryUpload/swfupload/swfupload.swf",
	    	"rootPath": rootPath,
	    	"tableName": "t_article_content",
	    	"recordId": $("input[name='CONTENT_ID']").val(),
	    	"userId": $("input[name='CREATEUSERID']").val(),
	    	"isMultiple": false,
	    	"fileSize": "1GB",
	    	"fit": true,
	    	"btnAddName": "上传文件",
	    	"btnDelName": "删除文件",
	    	"tip": "只能上传一个最大1GB的文件",
	    	"onListLoaded": function(paths) {
	    		$("input[name='LINKURL']").val(paths);
	    	}
	    });
		this.attachPanelForFile = thiz;
	},
	
	imgCaptureClickHandler: function(_e) {
		this.showImgCaptureDialog();
	},
	
	showImgCaptureDialog: function() {
		this.initImgCaptureDialog();
	},

	imgCaptureDialog: null,
	
	initImgCaptureDialog: function() {
		var thiz = $("<div/>").dialog({
			title: "文章图片管理",
			width: 550,
			height: 420,
			modal: true,
			shadow: false,
			cache: false,
			closable: false,
			onOpen: function() {  
	            $(this).dialog('refresh', rootPath + "/webpage/cms/article/imgTabLayout.jsp");  
	        },  
			buttons: [ {
				text: "确定",
				handler: function() {
					if ($("input[name='selImg']:checked").length > 0) {
						$.post(rootPath + "/article/titleimgSettingContent.do", {
							"srcPath": $("input[name='selImg']:checked").val()
						}, function(r) {
							if (r.success) {    					
								$("#titleImg_tag").attr("src", rootPath + "/" +r.path).css("display", "");
								$("#artTitleImgUrl").val(r.path);
								infoForm.imgCaptureDialog.dialog("destroy");
							}else{
								$.messager.alert("错误",r.msg);
							}
						}, "json");
					} else {
						$.messager.alert("提示", "请选择图片");
					}
				}
			}, { 
				text: "关闭", 
				handler: function() { 
					thiz.dialog("destroy"); 
				} 
			} ]
		});
		this.imgCaptureDialog = thiz;
	},
	
	initImgUpload: function() {
		var thiz = $("#imgUpload").uploadImg({
			"rootPath": rootPath,
			"url": rootPath + "/UploadServlet?uploadPath=myfile&busRecordId=&uploadUserId=-1",
			"swf": rootPath + "/plug-in/jquery/plugin/jqueryUpload/swfupload/swfupload.swf",
			"width": 240,
			"height": 190,
			"fileTypes": "*.png;*.jpg;*.jpeg",
			"fileTypesDesc": "图片文件",
			"onUploaded": function(r) {
				$("#artTitleImgUrl").val(r.filePath);
				$("#titleImg_tag").attr("src", rootPath +"/"+r.filePath).css("display", "");
			}
		});
	},
	
	imgCutDialog: null,
	
	showImgCutDialog: function(_src) {
		var thiz = $("<div></div>").dialog({
			title: "图片剪裁",
    		cache:false,
    		shadow: false,
			modal: true,
			closable: false,
			maximizable: true,
			width: 980,
			height: 700,
			href: rootPath + "/webpage/cms/article/imgCut.jsp?srcPath=" + _src,
			buttons: [ { 
				text: "关闭", 
				handler: function() { 
					thiz.dialog("destroy"); 
				} 
			} ]
		}); 
		this.imgCutDialog = thiz;
	},
	
	submitForm: function (_fn) {
		if ($("#articleFrom").form("validate")) {			
			var win = $.messager.progress({
				title: '系统提示',
				msg: '正在提交请求中...'
			});
			var $inputs = $(":input");
			var result = {};
			$.each($inputs, function(idx, o) {
				result[o.name] = o.value;
			});
			if (document.getElementById("eWebEditor1")) {
				result["CONTENT_TEXT"] = document.getElementById("eWebEditor1").contentWindow.getHTML();
			}
			$.post(rootPath + "/contentController.do?saveOrUpdate", result, function(responseText) {
				if(responseText&&responseText=="sessionIllegal"){
					alert("非法访问!");
				    window.top.location.href = __ctxPath+"/403.jsp";
				}
				var r = $.parseJSON(responseText);
				if (r) {
					//$.messager.alert("提示", r.msg);
					//parent.$("#contentGrid").datagrid("reload");	
					if(window.opener.$("#publishContentGrid").length>0){
						//window.opener.$("#publishContentGrid").datagrid('reload');
						window.opener.infopublishListSearch(r);
					}					
					window.opener.infoListSearch(r); // 调用父窗口的方法
					window.close();
				} else {
					$.messager.alert("提示", r.msg);
				}
			});
		} else {
			$.messager.alert("提示", "请检查信息是否正确填写!");
		}
	},
	
	snifferClickHandler: function(_fn){
		var o_Editor = document.getElementById("eWebEditor1").contentWindow;
		var win = $.messager.progress({
	        title: '系统提示',
	        msg: '正在提交请求中...'
	    });
		$.post(rootPath + "/article/snifferContent.do",{text:o_Editor.getHTML(), website:$('#contentSiteId').val()}, function(responseText, status, xhr) {
			$.messager.progress('close');
			var resultJson = $.parseJSON(responseText);
        	o_Editor.setHTML(resultJson.infoText);
        	if(typeof _fn == "function"){
        		if(resultJson.keyWordShow != null){	
	        		$.messager.confirm("系统提示", "<font color='#d77a01'><B>您所编辑的信息存在【"+resultJson.keyWordShow+"】等敏感词，可能影响信息采用。请确认是否继续操作？【仅供参考】</B></font>", function (data) {
	                    if (data) {
	                    	_fn();
	                    }
	                });
        		}else{
        			_fn();
        		}
        	}else{
        		if(resultJson.keyWordShow != null){		
    				$.messager.alert("系统提示", "<font color='#d77a01'><B>您所编辑的信息存在【"+resultJson.keyWordShow+"】等敏感词，可能影响信息采用。【仅供参考】</B></font>");
    			}
        	}
		});
	},
	openImageUploadDialog:function(){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open(rootPath+'/fileTypeController.do?openUploadDialog&attachType=image', {
			title: "上传(图片)",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("#titleImg_tag").attr("src", __file_server + uploadedFileInfo.filePath).css("display", "");
						$("input[name='TITLEIMG']").val(uploadedFileInfo.filePath);
					}else{
						$("#moduleLogo").attr("src",'').css("display", "none");
						$("input[name='TITLEIMG']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	},
	openAttachUploadDialog:function (){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open(rootPath+'/fileTypeController.do?openUploadDialog&attachType=attach', {
			title: "上传(文件)",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("input[name='FILEURL']").val(uploadedFileInfo.filePath);
						$("input[name='FILENAME']").val(uploadedFileInfo.fileName);
                        $("input[name='LINKURL']").val( __file_server+uploadedFileInfo.filePath);
					}else{
						$("input[name='FILEURL']").val('');
						$("input[name='FILENAME']").val('');
                        $("input[name='LINKURL']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	},
	openVideoUploadDialog:function (){
		/**
		 * 上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
		 */
		art.dialog.open(rootPath+'/fileTypeController.do?openUploadDialog&attachType=video', {
			title: "上传(视频)",
			width: "500px",
			height: "300px",
			lock: true,
			resize: false,
			close: function(){
				var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
				if(uploadedFileInfo){
					if(uploadedFileInfo.fileId){
						$("input[name='VIDEOURL']").val(uploadedFileInfo.filePath);
						$("input[name='VIDEONAME']").val(uploadedFileInfo.fileName);
					}else{
						$("input[name='VIDEOURL']").val('');
						$("input[name='VIDEONAME']").val('');
					}
				}
				art.dialog.removeData("uploadedFileInfo");
			}
		});
	}
	
	
};
