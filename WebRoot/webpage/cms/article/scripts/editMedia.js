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
	 * 编辑区域自动扩充
	 */
	$(window).resize(function(){
		var $this = $(this);
		var	eWidth = $this.innerWidth() - 280;
		var eHeight = $this.innerHeight() - 190;
		var ifr = $("#eWebEditor1").attr({
			"width": eWidth,
			"height": eHeight
		});
		if (eWidth > 700 && eHeight > 300) {
			$(".editor_table").css("height", $(".editor_top").height() + ifr.height() - 60);
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
	
	infoForm.initAttachPanelForMedia();
	

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
	
	
	
	$("#btnSave").on("click", function() {
		infoForm.submitForm(function(data) {
			window.opener.listSearch(); // 调用父窗口的方法
			window.close();
		});
	});
	
	$("#btnClose").on("click", function() {
		window.opener.listSearch(); // 调用父窗口的方法
		window.close();
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
	
	$("#isTop").on("change", function(event) {
		if (this.value === "1") {
			$("#trTop").css("display", "");
			$("input[name='content.topTime']").val($("#curdate").val());
		} else {
			$("#trTop").css("display", "none");
			$("input[name='content.topTime']").val("");
		}
	});
	
	$("#isHot").on("change", function(event) {
		if (this.value === "1") {
			$("#trHot").css("display", "");
			$("input[name='content.hotTime']").val($("#curdate").val());
		} else {
			$("#trHot").css("display", "none");
			$("input[name='content.hotTime']").val("");
		}
	});
	
	$("#isvisible").on("change", function(event) {
		if (this.value === "1") {
			$("#trVisible").css("display", "");
			$("input[name='content.hotTime']").val($("#curdate").val());
		} else {
			$("#trVisible").css("display", "none");
			$("input[name='content.visibleTime']").val("");
		}
	});
	
	/**
	 * 所属栏目--点击事件
	 */
	$("#moduleName").click(function(_e) {
		infoForm.moduleClickHandler(_e);
	});
	
	/**
	 * 引至栏目--点击事件
	 */
	$("#refNames").click(function(_e) {
		infoForm.refModuleClickHandler(_e);
	});
	
	/**
	 * 引至专题--点击事件
	 */
	$("#specialreffNames").click(function(_e) {
		infoForm.specialClickHandler(_e);
	});
	
	/**
	 * 信息来源--点击事件
	 */
	$("#source").click(function(_e) {
		infoForm.sourceClickHandler(_e);
	});
	// ****************************************** 事件绑定 end****************************************
	
		 
	
	// ***************************************** 初始数据 begin***************************************
	var contentId = $("input[name='CONTENT_ID']").val();
	if (contentId) {				
		document.getElementById("eWebEditor1").src = rootPath + "/plug-in/ewebeditor/ewebeditor.htm?id=CONTENT_TEXT&style=standard600";
		$("input[name='CONTENT_TEXT']").val($("#content_text_div").html());
		$("#content_text_div").html('');
	}		
	// ****************************************** 初始数据 end****************************************

	
	/**
	 * 图片上传
	 */
	$("#openImage").click(function() {
		infoForm.openImageUploadDialog();
	});
	/**
	 * 视频上传
	 */
	$("#openVideo").click(function() {
		infoForm.openVideoUploadDialog();
	});
});


