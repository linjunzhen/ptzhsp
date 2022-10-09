$(function() {
	rootPath = $("#rootPath").val();
	
	juicer.set({
	    'tag::operationOpen': '{@',
	    'tag::operationClose': '}',
	    'tag::interpolateOpen': '@{',
	    'tag::interpolateClose': '}',
	    'tag::noneencodeOpen': '@@{',
	    'tag::noneencodeClose': '}',
	    'tag::commentOpen': '{#',
	    'tag::commentClose': '}'
	});

	// ****************************************** 组件渲染 begin****************************************
	/**
	 * 编辑区域自适应
	 */
	$(window).resize(function() {
		var $this = $(this);
		var	eWidth = $this.innerWidth() - 280;
		var eHeight = $this.innerHeight() - 190;
		
		var ifr = $("#eWebEditor1").attr({
			"width": eWidth,
			"height": eHeight
		});
		
		if (eWidth > 700 && eHeight > 300) {
			$("#ewebmain").css({
				"width": eWidth,
				"height": eHeight
			});
			$(".editor_table").css("height", $(".editor_top").height() + $("#ewebmain").height() - 60);
		}
	}).resize();

	
	/**
	 * 初始化百叶窗
	 */
	$(".editor_table").next().next().hide();
	
	
	/**
	 * 图片上传
	 */
	infoForm.initImgUpload();
	
	/**
	 * 阅读者
	 */
	$("#readers").combotree({
		url: rootPath + '/system/panelTreeSysGroup.do',
		multiple: true,
		panelHeight: 'auto'
	});
	// ****************************************** 组件渲染 end****************************************
	
	// ***************************************** 事件绑定 begin***************************************
	/**
	 * 百叶窗事件
	 */
	$(".editor_titleR").click(function(_e) {
		$(".editor_table").hide();
		$(_e.target).next().show();
	});
	
	/**
	 * 自动摘要-点击事件
	 */
	$("#btnAutoSummury").click(function() {
		$.post(rootPath + "/contentController.do?autosummary", {
			"contentText": document.getElementById("eWebEditor1").contentWindow.getHTML()
		}, function(r) {
			$("[name='CONTENT_SUMMARY']").val(r.msg);
		}, "json");
	});

	/**
	 * 信息类型--变化事件
	 */
	$("#selContentType").change(function(_e) {
		infoForm.contentTypeChangeHanlder(_e);
	});
	
	/**
	 * 选取正文图片--点击事件
	 */
	$("#btnimgselector").click(function(_e) {
		infoForm.imgCaptureClickHandler(_e)
	});
	
	/**
	 * 图片裁剪--点击事件
	 */
	$("#btnimgCut").click(function() {
		var src = $("#titleImg_tag").attr("src");
		if (src) {
			infoForm.showImgCutDialog(src);
		}
	});
	
	/**
	 * 图片删除--点击事件
	 */
	$("#btnimgdel").click(function(_e) {
		$("#artTitleImgUrl").val("");
		$("#titleImg_tag").attr("src", "").hide();
	});
	
	
	/**
	 * 弹出附件列表--点击事件
	 */
	$("#btnAttach").click(function(_e) {
		//infoForm.fileClickHandler(_e);
		$.dialog.open("contentController.do?fileList&attachFileIds="+$("input[name='attachFileIds']").val()
		+"&contentId="+$("input[name='CONTENT_ID']").val(), {
			title : "附件列表",
			width : "800px",
			height : "420px",
			lock : true,
			resize : false,
			close: function(){
				var uploadedFileIds = art.dialog.data("uploadedFileIds");
				if(uploadedFileIds){
					if(uploadedFileIds.attachFileIds){						
						$("input[name='attachFileIds']").val(uploadedFileIds.attachFileIds);
					}
				}
				art.dialog.removeData("uploadedFileIds");
			}
		}, false);
	});
	/**
	 * 引至栏目--点击事件
	 */
	$("#refNames").click(function(_e) {
		infoForm.refModuleClickHandler(_e);
	});
	/**
	 * 删除引至栏目--点击事件
	 */
	$("#btnQuote").click(function(_e) {
		$("input[name='TARGET_MODULE_ID']").val("");
		$("input[name='TARGET_MODULE_NAME']").val("");
	});
	
	$("#btnWord").on("click", function() {
		document.getElementById("eWebEditor1").contentWindow.exec("importword");
	});
	
	$("#btnOnekey").on("click", function() {
		document.getElementById("eWebEditor1").contentWindow.exec("QuickFormat");
	});
	
	$("#btnExcel").on("click", function() {
		document.getElementById("eWebEditor1").contentWindow.exec("importexcel");
	});
	
	$("#btnSave").on("click", function() {
		infoForm.submitForm(function(data) {
		//	eval("opener." + $("#callback").val() + "()"); // 调用父窗口的方法
			window.close();
		});
	});
	
	$("#btnClose").on("click", function() {
	//	eval("opener." + $("#callback").val() + "()"); // 调用父窗口的方法
		art.dialog.confirm("您确定要关闭吗?", function() {    		
			window.close();
		});
	});
	
	$("#ckSubTitle").on("click", function(event) {
		if (event.target.checked) {
			$("#subTitle").show();
			$("#ipSubTitle").validatebox({  
			    required: true
			});  
		} else {
			$("#subTitle").hide();
			$("#ipSubTitle").validatebox({  
			    required: false 
			}); 
		}
	});
	
	$("input[name='isNote']").on("click", function(event) {
		if (this.value === "1") {
			$("#trNote").css("display", "");
		} else {
			$("#trNote").css("display", "none");
			$("#note").val("");
		}
	});
	
	
	
	/**
	 * 图片上传
	 */
	$("#openImage").click(function() {
		infoForm.openImageUploadDialog();
	});
	/**
	 * 文件上传
	 */
	$("#openAttach").click(function() {
		infoForm.openAttachUploadDialog();
	});	
	/**
	 * 视频上传
	 */
	$("#openVideo").click(function() {
		infoForm.openVideoUploadDialog();
	});
	
	
	// ****************************************** 事件绑定 end****************************************
	
});