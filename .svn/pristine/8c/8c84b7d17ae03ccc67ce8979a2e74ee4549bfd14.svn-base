var infoForm = {
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
	    	"tableName": "JBPM6_FLOW_RESULT",
	    	"recordId": $("input[name='RESULT_ID']").val(),
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
	}
	
	
	
};
