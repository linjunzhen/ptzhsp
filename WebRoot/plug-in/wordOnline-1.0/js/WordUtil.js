var WordUtil = new Object({	
	/**
	 * 获取对象
	 */
	getDataObj:function(){
		var DataOBJ = $("#dataMapJson").val();
		if(DataOBJ){
			//将其转换成JSON对象
			var eObj = JSON2.parse(DataOBJ);
			return eObj;
		}else{
			return null;
		}
	},
	/**
	 * 初始化表单字段值
	 * @param {} fieldValueObj
	 * @param {} elementId
	 */
	initFormFieldValue:function(fieldValueObj,elementId){
		for(var fieldName in fieldValueObj){
			//获取目标控件对象
			var targetField = $("#"+elementId).find("[name^='VB_"+fieldName+"_']")[0];
			if(targetField){
				var type = targetField.type;
				var tagName = targetField.tagName;
				var fieldValue = fieldValueObj[fieldName];
				if(type=="radio"){
					var fields = $("#"+elementId).find("[name^='VB_"+fieldName+"_']");
					fields.each(function(){
				        var radioValue = $(this).val();
				        if(radioValue==fieldValue){
				        	$(this).attr("checked","checked");
				        }
				    });
				}else if(type=="checkbox"){
					var fields = $("#"+elementId).find("[name^='VB_"+fieldName+"_']");
					fields.each(function(){
				        var checkBoxValue = $(this).val();
				        var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
				        if(isChecked){
				        	$(this).attr("checked","checked");
				        }
				    });
				}else if(tagName=="TEXTAREA"||tagName=="SELECT"){
					targetField.value = fieldValueObj[fieldName];
				}else{
					targetField.setAttribute("value",fieldValueObj[fieldName]);
				}
			}
		}
	},
	/**
	 * 根据传入的元素id获取子孙节点元素值
	 * @param {} elementId
	 */
	getFormEleData:function(elementId){
		var formData = {};
	    $("#"+elementId).find("*[name]").each(function(){
	          var inputName= $(this).attr("name");
	          var inputValue = $(this).val();
	          //获取元素的类型
			  var fieldType = $(this).attr("type");
			  if(fieldType=="radio"){
			  	  var isChecked = $(this).is(':checked');
			  	  if(isChecked){
			  	  	 formData[inputName] = inputValue;
			  	  }
			  }else if(fieldType=="checkbox"){
			  	  var inputValues = FlowUtil.getCheckBoxValues(inputName);
			  	  formData[inputName] = inputValues;
			  }else{
			  	  formData[inputName] = inputValue;
			  }
	          
	    });
	    return formData;
	}
});