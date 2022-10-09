$(function() {

	rootPath = $("#rootPath").val();
	
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
	/**
	 * 图片上传
	 */
	infoForm.initImgUpload();
	
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
		art.dialog.confirm("您确定要关闭吗?", function() {  
			window.opener.listSearch(); // 调用父窗口的方法  		
			window.close();
		});
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
	// ****************************************** 事件绑定 end****************************************
	// ***************************************** 初始数据 begin***************************************
	var contentId = $("input[name='CONTENT_ID']").val();
	if (contentId) {				
		document.getElementById("eWebEditor1").src = rootPath + "/plug-in/ewebeditor/ewebeditor.htm?id=CONTENT_TEXT&style=standard600";
		$("input[name='CONTENT_TEXT']").val($("#content_text_div").html());
		$("#content_text_div").html('');
	}		
	// ****************************************** 初始数据 end****************************************
});


