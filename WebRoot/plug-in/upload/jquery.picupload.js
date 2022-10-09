(function($) {
    $.fn.picupload=function(options){
		$.fn.picupload.defaults= {
	        url: '',
	        fileName: 'filedata',
	        dataType: 'json',
	        params: {},
	        allowType:["JPG","GIF","PNG","BMP"],
	        allowSize:5*1024*1024,//单位字节，默认5MB
	        width:'73px',
	        height:'27px',
	        onSubmit:function() {return true},
	        success: function() {return true}
	    };
		return this.each(function() {
			var opts = $.extend({},$.fn.picupload.defaults,options);
			var uploader = $(this);
			
			if (opts.url == '') {
				return;
			};
			uploader.css({"width":opts.width, "height":opts.height, "overflow":"hidden"});
		    //创建一个IFRAME
	        var iframe;
	        if($('#upload_iframe').length==0){
	        	iframe = $('<iframe style="width: 0px; height: 0px; border: 0px;position:absolute;top:-9999px" name="upload_iframe" id="upload_iframe"></iframe>');
	        	iframe.appendTo("body");
	        };

	        var form = $('<form method="post" enctype="multipart/form-data" name="form_'+opts.fileName+'" id="form_'+opts.fileName+'"></form>');
	        form.attr("target", "upload_iframe");
	         
	        // form中增加数据域
	        var formHtml = '<input type="file" name="' + opts.fileName + '" accept="image/png,image/gif,image/jpg,image/jpeg">';
	        for (key in opts.params) {
	           formHtml += '<input type="hidden" name="' + key + '" value="' + opts.params[key] + '">';
	        }
	        formHtml += '<input type="submit" class="upload_submit">';
	        form.append(formHtml);
	        form.appendTo(uploader);
	        // 文件框
	        var fileInput = $('input[type=file][name=' + opts.fileName + ']', form);
	        fileInput.css({"width":opts.width, "height":opts.height, "border":"none", "opacity":"0", "filter":"Alpha(opacity=0)"});
	        //获取文件类型
	        var getFileType = function(file){
	        	var filePath = $(file).val();
	        	var extStart  = filePath.lastIndexOf(".")+1;
	        	var fileExt = filePath.substring(extStart,filePath.length).toUpperCase();
	        	return fileExt;
	        };
	        //获取文件大小
	        var getFileSize = function(file){
	        	var file_size = 0;
                if (navigator.userAgent.indexOf("MSIE")>0) {
                    var image=new Image();  
                    image.src=$(file).val(); 
                    while (true) {
                        if (image.fileSize != 0) {
                        	file_size=image.fileSize;
                        	break;
                        }
                    }
                } else {
                    file_size = file.files[0].size;
                }
                return file_size;
	        };
	        
	        fileInput.change(function() {
	        	//验证文件类型
	        	if ($.inArray(getFileType(this),opts.allowType) == -1){
	        	    alert("上传的文件类型必须为："+opts.allowType);
	        	    return false;
	        	}
	        	//验证文件大小
	        	if(getFileSize(this)>opts.allowSize){
	        		alert("文件大小不能超过："+opts.allowSize+"KB ("+opts.allowSize/1024+"MB)");
	        		return false;
	        	};
	        	//var loading = $("<div class=\"panel-loading\" id=\"loading\">上传中...</div>");
				//uploader.after(loading);
				var form1 = $(this).parent();
	        	// iframe 在提交完成之后
	        	var iframeId = form1.attr("target");
	        	var ifm = $('#'+iframeId);
	    		ifm.bind('load',function(){
	        		var data = $(this).contents().find('body').text();
	        		if ('json' == opts.dataType) {
	        			//data = $.parseJSON(data);
	        			data = window.eval('(' + data + ')');
	        		};
	        		//loading.remove();
	        		opts.success(data);
	        		$(this).unbind('load');//不可少，不可改
	        	});
	        	try{
	        		if(opts.onSubmit()){
	        			form1.attr('action', opts.url)
	        			$(form1).find('.upload_submit').click();
	        		};
	        	}catch(e){
	        		alert(e);
	        	}
	        });
		});	
	};//picupload End	
})(jQuery);

