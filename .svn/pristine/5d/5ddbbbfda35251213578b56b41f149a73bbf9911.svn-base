/**
 * 自定义可编辑表格操作工具
 * 
 * 1、表格样式示例
 * 		--------------------------------------
 * 		| colNo |  口  |  col1    |   col2    |
 * 		--------------------------------------
 * 		|   1   |  口  |  data11  |   data21  |
 * 		--------------------------------------
 * 		|   2   |  口  |  data12  |   data22  |
 * 		--------------------------------------
 * 
 * 2、表格代码示例
 *    <table id="tb">
 *        <tr>
 *            <th>colNo</th>
 *            <th><input type="checkbox"/></th>
 *            <th>col1</th>
 *            <th>col2</th>
 *        </tr>
 *        <tr>
 *            <th>1</th>
 *            <th><input chkIndex="1" type="checkbox"/></th>
 *            <td><input type="text" iptName="name11" value="data11"/></td>
 *            <td><input type="text" iptName="name21" value="data21"/></td>
 *        </tr>
 *    </table>
 * 
 * 3、实例化：$(表格dom).MyEditableTable();
 * 
 * 4、属性
 *    Properties   | Type    | Description | Default |
 *    singleSelect | boolean | 是否单选     | false   |
 *    maxRowCount  | number  | 最大行数     | 100     |
 *    
 *    @example
 *    $('#tb').MyEditableTable({singleSelect: true});
 *    
 * 5、事件：
 *    Name              |   Parameter   |             Description                     |
 *    onBeforeAppendRow |   none        |  插入一行前触发                               |
 *    onAfterAppendRow  |   rowCount    |  插入一行后触发,rowcount:执行后表格数据行数     |  
 *    onBeforeDeleteRow |   $checkedChks|  已勾选的行复选框对象集合                      |
 *    onAfterDeleteRow  |   rowCount    |  删除一行后触发,rowcount:执行后表格数据行数     |
 *    onLoadSuccess     |   rowCount    |  数据加载成功后触发,rowcount:执行后表格数据行数  |
 *    
 *    @example
 *    $('#tb').MyEditableTable({onAfterAppendRow: function(rowCount){}});
 *    
 * 6、方法：
 *    Name           |    Parameter    |             Description                         | 
 *    getData        |    none         |   返回表格数据                               |
 *    appendRow      |    none         |   在表格最后一行,插入新的一行                  |
 *    deleteRow      |    none         |   删除一行                                   | 
 *    loadData       |    array        |   加载表格数据,旧数据将被删除                  |
 *    loadDataExtend |    array        |   更新表格数据，旧数据将被覆盖，新数据行将被插入  |
 *    
 *    @example
 *    $('#tb').MyEditableTable("getData");
 *    $('#tb').MyEditableTable("loadData", [{"index":1,"col1":13,"col2":23}]);
 * 
 */
(function($){
	    	
	/**
	 * 初始化
	 */
	function init(target){
		var options = $.data(target, 'MyEditableTable').options;
		var $table = $(target);
		var $titleRow = $table.find('tr:first');
		var $checkAllChk = $titleRow.find('input[type=checkbox]:first');
		var $rows = $table.find('tr');
		//标题行复选框绑定全选/全不选事件
		$checkAllChk.off('change');
		$checkAllChk.prop('checked', false);
		$checkAllChk.on('change', function(){
			if(options.singleSelect){
				$(this).prop('checked', false);
				return;
			}
			var chkValue = $(this).prop('checked');
			$rows.each(function(index, row){
    			if(index > 0){
    				var $chk = $(row).find('input[type=checkbox]:first');
    				$chk.prop('checked', chkValue);
    			}
			});
		});
		//数据行复选框绑定事件
		$rows.each(function(index, row){
			if(index > 0){
				var $curRowChk = $(row).find('input[type=checkbox]:first');
				$curRowChk.off('change');
				$curRowChk.prop('checked', false);
				$curRowChk.on('change', function(){
					if(options.singleSelect){
						$rows.each(function(i, r){
		        			if(i > 0 && i != index){
		        				var $chk = $(r).find('input[type=checkbox]:first');
		        				$chk.prop('checked', false);
		        			}
						});
						if(!$(this).prop('checked')){
							$(this).prop('checked', true);
						}
					}else{
						if($(this).prop('checked')){
							var isAllChecked = true;
							$rows.each(function(i, r){
    		        			if(i > 0 && i != index){
    		        				var $chk = $(r).find('input[type=checkbox]:first');
    		        				if(!$chk.prop('checked')){
    		        					isAllChecked = false;
    		        					return false;
    		        				}
    		        			}
							});
							if(isAllChecked){
								$checkAllChk.prop('checked', true);
							}
						}else{
							if($checkAllChk.prop('checked')){
								$checkAllChk.prop('checked', false);
							}
						}
					}
				});
			}
		});
	}
	
	/**
	 * 返回加载的数据
	 */
	function getData(target){
		var $table = $(target);
		var $rows = $table.find('tr');
		var objArray = [];
		$rows.each(function(index, dom){
			if(index > 0){
				var obj = {};
				$(dom).find('input[iptName][type=radio]:checked').each(function(){
					obj[$(this).attr('iptName')] = $(this).val();
				});
				$(dom).find('input[iptName][type=text]').each(function(){
					obj[$(this).attr('iptName')] = $(this).val();
				});
				$(dom).find('select[iptName]').each(function(){
					obj[$(this).attr('iptName')] = $(this).val();
				});
				$(dom).find('input[iptName][type=hidden]').each(function(){
					obj[$(this).attr('iptName')] = $(this).val();
				});
				//记录行索引
				obj['index'] = index;
				objArray.push(obj);
			}
		});
		return objArray;
	}
	
	/**
	 * 在表格最后一行后，追加一行
	 */
	function appendRow(target){
		var options = $.data(target, 'MyEditableTable').options;
		if(options.onBeforeAppendRow.call(target) == false){
			return;
		}
		var $table = $(target);
		var $rows = $table.find('tr');
		if($rows.length - 1 >= options.maxRowCount){
			alert('操作失败,最多增加'+options.maxRowCount+'条记录!');
			return;
		}
		var $lastRow = $table.find('tr:last');
		var lastRowIndex = parseInt($lastRow.find('th:first').html(), 10);
		var $newRow = $lastRow.clone();
		$newRow.find('th:first').html($rows.length);
		$newRow.find('input[type=checkbox][chkIndex]').attr('chkIndex', $rows.length);
		$newRow.find('input[iptName][type=radio]').each(function(index, node){
			$(this).attr('name', $(this).attr('iptName')+(lastRowIndex+1));
			$(this).prop('checked', false);
		});
		$newRow.find('select[iptName]').val('');
		$newRow.find('input[iptName][type=text]').val('');
		$lastRow.after($newRow);
		init(target);
		options.onAfterAppendRow.call(target, $rows.length);
	}
	
	/**
	 * 删除行
	 */
	function deleteRow(target){
		var $table = $(target);
		var $checkedChks = $table.find('input[type=checkbox][chkIndex]:checked');
		if($checkedChks.length === 0){
			alert('操作失败,请勾选待删除的记录!');
			return;
		}
		var options = $.data(target, 'MyEditableTable').options;
		if(options.onBeforeDeleteRow.call(target, $checkedChks) == false){
			return;
		}
		if(confirm('您确定要删除已勾选的记录吗?')){
			var $rows = $table.find('tr');
			var $baseRow = null;
			if($rows.length - 1 === $checkedChks.length){
				var $lastRow = $table.find('tr:last');
				$baseRow = $lastRow.clone();
    			$baseRow.find('th:first').html('1');
    			$baseRow.find('input[type=checkbox][chkIndex]').attr('chkIndex', '1');
    			$baseRow.find('input[iptName][type=radio]').each(function(index, node){
	    			$(this).attr('name', $(this).attr('iptName')+'1');
	    			$(this).prop('checked', false);
	    		});
    			$baseRow.find('select[iptName]').val('');
    			$baseRow.find('input[iptName][type=text]').val('');
			}
			$checkedChks.each(function(index, node){
				var $checkedRow = $(this).parents('tr:first');
				$checkedRow.remove();
			});
			var leftRowCount = 0;
			if($rows.length - 1 === $checkedChks.length){
				$table.append($baseRow);
				leftRowCount = 1;
			}else{
				var $curRows = $table.find('tr');
				$curRows.each(function(index, row){
					if(index > 0){
						$(this).find('th:first').html(index);
		    			$(this).find('input[type=checkbox][chkIndex]').attr('chkIndex', index);
		    			$(this).find('input[iptName][type=radio]').each(function(i, n){
			    			$(this).attr('name', $(this).attr('iptName') + index);
			    		});
					}
				});
				leftRowCount = $curRows.length;
			}
			init(target);
			var options = $.data(target, 'MyEditableTable').options;
			options.onAfterDeleteRow.call(target, leftRowCount);
		}
	}
	
	/**
	 * 加载表格数据，旧数据将被移除
	 */
	function loadData(target, data){
		if(!$.data(target, 'MyEditableTable')){
			$.data(target, 'MyEditableTable', {
				options: $.extend({}, $.fn.MyEditableTable.defaults)
			});
		}
		if(typeof data == 'object' && data.length != 0){
			var $table = $(target);
			//记录数据行
			var $lastRow = $table.find('tr:last');
			var $baseRow = $lastRow.clone();
			$baseRow.find('th:first').html('');
			$baseRow.find('input[type=checkbox][chkIndex]').attr('chkIndex', '');
			$baseRow.find('input[iptName][type=radio]').each(function(index, node){
    			$(this).attr('name', $(this).attr('iptName'));
    			$(this).prop('checked', false);
    		});
			$baseRow.find('select[iptName]').val('');
			$baseRow.find('input[iptName][type=text]').val('');
			//遍历删除数据行
			var $rows = $table.find('tr');
			$rows.each(function(index, row){
				if(index > 0){
					$(this).remove();
				}
			});
			//计算出data中index的最大值，即数据行数
			var maxIndex = 0;
			$.each(data, function(index, node){
				var curIndex = data[index].index;
				if(curIndex > maxIndex){
					maxIndex = curIndex;
				}
			});
			//插入新数据行并赋值
			for(var rowIndex = 1; rowIndex <= maxIndex; rowIndex++){
				var $newRow = $baseRow.clone();
				$newRow.find('th:first').html(rowIndex);
				$newRow.find('input[type=checkbox][chkIndex]').attr('chkIndex', rowIndex);
	    		$newRow.find('input[iptName][type=radio]').each(function(i, n){
	    			$(this).attr('name', $(this).attr('iptName') + rowIndex);
	    		});
	    		$.each(data, function(i, n){
	    			var rowData = data[i];
	    			if(rowData.index === rowIndex){
	    				for(var name in rowData){
	    	    			if(rowData[name]){
			    					$newRow.find("input[type=radio][iptName="+name+"][value='"+rowData[name]+"']").prop('checked', true);
		    		    			$newRow.find('select[iptName='+name+']').val(rowData[name]);
		        		    		$newRow.find('input[type=text][iptName='+name+']').val(rowData[name]);
		        		    		$newRow.find('input[type=hidden][iptName='+name+']').val(rowData[name]);
	    	    			}
	    	    		}
	    			}
				});
	    		$table.append($newRow);
			}
			var options = $.data(target, 'MyEditableTable').options;
			options.onAfterAppendRow.call(target, maxIndex);
		}
		init(target);
	}
	
	/**
	 * 更新表格数据，旧数据将被覆盖，新数据行将被插入
	 */
	function loadDataExtend(target, data){
		if(!$.data(target, 'MyEditableTable')){
			$.data(target, 'MyEditableTable', {
				options: $.extend({}, $.fn.MyEditableTable.defaults)
			});
		}
		if(typeof data == 'object' && data.length != 0){
			var $table = $(target);
			var $rows = $table.find('tr');
			//记录数据行
			var $lastRow = $table.find('tr:last');
			var $baseRow = $lastRow.clone();
			$baseRow.find('th:first').html('');
			$baseRow.find('input[type=checkbox][chkIndex]').attr('chkIndex', '');
			$baseRow.find('input[iptName][type=radio]').each(function(index, node){
    			$(this).attr('name', $(this).attr('iptName'));
    			$(this).prop('checked', false);
    		});
			$baseRow.find('select[iptName]').val('');
			$baseRow.find('input[iptName][type=text]').val('');
			//计算出data中index的最大值，即数据行数
			var maxIndex = 0;
			$.each(data, function(index, node){
				var curIndex = data[index].index;
				if(curIndex > maxIndex){
					maxIndex = curIndex;
				}
			});
			//遍历现有行，更新值
			$rows.each(function(index, row){
				if(index > 0){
					var rowIndex = parseInt($(this).find('th:first').html(), 10);
					var $curRow = $(this);
					$.each(data, function(i, n){
						var rowData = data[i];
						if(rowData.index === rowIndex){
							for(var name in rowData){
				    			if(rowData[name] ){
				    					$curRow.find("input[type=radio][iptName="+name+"][value='"+rowData[name]+"']").prop('checked', true);
				    					$curRow.find('select[iptName='+name+']').val(rowData[name]);
					    				$curRow.find('input[type=text][iptName='+name+']').val(rowData[name]);
					    				$curRow.find('input[type=hidden][iptName='+name+']').val(rowData[name]);
				    			}
				    		}
						}
					});
				}
			});
			//循环，插入新行，并赋值
			for(var rowIndex = $rows.length; rowIndex <= maxIndex; rowIndex++){
				var $newRow = $baseRow.clone();
				$newRow.find('th:first').html(rowIndex);
				$newRow.find('input[type=checkbox][chkIndex]').attr('chkIndex', rowIndex);
	    		$newRow.find('input[iptName][type=radio]').each(function(i, n){
	    			$(this).attr('name', $(this).attr('iptName') + rowIndex);
	    		});
	    		$.each(data, function(i, n){
	    			var rowData = data[i];
	    			if(rowData.index === rowIndex){
	    				for(var name in rowData){
	    	    			if(rowData[name]){
	    	    					$newRow.find("input[type=radio][iptName="+name+"][value='"+rowData[name]+"']").prop('checked', true);
			    					$newRow.find('select[iptName='+name+']').val(rowData[name]);
		        		    		$newRow.find('input[type=text][iptName='+name+']').val(rowData[name]);
		        		    		$newRow.find('input[type=hidden][iptName='+name+']').val(rowData[name]);
	    	    				
	    	    			}
	    	    		}
	    			}
				});
	    		$table.append($newRow);
			}
			var options = $.data(target, 'MyEditableTable').options;
			options.onAfterAppendRow.call(target, $rows.length);
		}
		init(target);
	}
	
	/**
	 * 注册jquery插件
	 */
	$.fn.MyEditableTable = function(options, param){
		if(typeof options == 'string'){
			return $.fn.MyEditableTable.methods[options](this, param);
		}
		options = options || {};
		return this.each(function(){
			if(!$.data(this, 'MyEditableTable')){
				$.data(this, 'MyEditableTable', {
					options: $.extend({}, $.fn.MyEditableTable.defaults, options)
				});
			}
			init(this);
		});
	};
	
	/**
	 * 定义methods
	 */
	$.fn.MyEditableTable.methods = {
		getData: function(jq){
			return getData(jq[0]);
		},
		appendRow: function(jq){
			return jq.each(function(){
				appendRow(this);
			});
		},
		deleteRow: function(jq){
			return jq.each(function(){
				deleteRow(this);
	    	});
		},
	    loadData: function(jq, data){
	    	return jq.each(function(){
	    		loadData(this, data);
	    	});
	    },
	    loadDataExtend: function(jq, data){
	    	return jq.each(function(){
	    		loadDataExtend(this, data);
	    	});
	    }
	};
	
	/**
	 * 默认属性配置
	 */
	$.fn.MyEditableTable.defaults = {
		//是否单选一行
		'singleSelect': false,
		//最大行数限制
        'maxRowCount': 100,
        //追加一行前置事件
        onBeforeAppendRow: function(){},
        //追加一行后置事件
        onAfterAppendRow: function(rowCount){},
        //删除一行前置事件
        onBeforeDeleteRow: function($checkedChks){},
        //删除一行后置事件
        onAfterDeleteRow: function(rowCount){},
        //加载数据成功事件
        onLoadSuccess: function(rowCount){}
	};
})(jQuery);