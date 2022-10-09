$().ready(function() {
		var TYPE_CODE3 = "";
		var TYPE_NAME3 = "";
		$("#placeCode").combotree({
			url: 'dicTypeController/placeTree.do',
				multiple: false,
				cascadeCheck: false,
				onlyLeafCheck: true,
				onLoadSuccess: function () {
					$("input[name='PLACE_CODE']").parent().children('input').eq(0).toggleClass('validate[required]');
					$("input[name='PLACE_CODE']").parent().css("overflow","visible");
					var flowSubmitObj = FlowUtil.getFlowObj();
					if(flowSubmitObj){
						//初始化表单字段值
						if(flowSubmitObj.busRecord){
							if(TYPE_CODE3==""){
								TYPE_CODE3 = flowSubmitObj.busRecord.PLACE_CODE;
							}
							if(TYPE_NAME3==""){								
								$.post( "dicTypeController/placeInfo.do",{
									typeCode:TYPE_CODE3},
									function(responseText3, status, xhr) {
										var resultJson3 = $.parseJSON(responseText3);
										if(null!=resultJson3){	
											TYPE_CODE3 = resultJson3.TYPE_CODE;
											TYPE_NAME3 = resultJson3.TYPE_NAME;
											$("#placeCode").combotree("setValue",TYPE_CODE3);
											$("#placeCode").combotree("setText",TYPE_NAME3);
										}
								});
							}else{
								$("#placeCode").combotree("setValue",TYPE_CODE3);
								$("#placeCode").combotree("setText",TYPE_NAME3);
							}
						}
					}
				},
				onClick : function (node){
					// 返回树对象
					var tree = $(this).tree;
					// 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
					var isLeaf = tree('isLeaf', node.target);
					if (!isLeaf){
					    // 清除选中
					    $("#placeCode").combotree('clear');
					}else{
						TYPE_CODE3= $("#placeCode").combotree("getValue");
						TYPE_NAME3= $("#placeCode").combotree("getText");
					}
				},
				onExpand:function(node){
					$("input[name='PLACE_CODE']").parent().children('input').eq(0).toggleClass('validate[required]');
					$("input[name='PLACE_CODE']").parent().css("overflow","visible");
				}
		});
	});