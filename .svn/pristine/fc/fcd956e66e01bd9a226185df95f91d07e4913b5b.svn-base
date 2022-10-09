<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>

<div class="scdz">
<fda:fda-exselect name="${currentTime}${name}QS" style="width:100px;"
	datainterface="fdaDicTypeService.findList" disabled="true"
	clazz="input-dropdown validate[required]"
	queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}"
	defaultemptytext="请选择区市"></fda:fda-exselect>
<fda:fda-exselect name="${currentTime}${name}XS" style="width:100px;"
	datainterface="fdaDicTypeService.findList"
	clazz="input-dropdown validate[required]"
	queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.JYCSQS}',ORDER_TYPE:'ASC'}"
	defaultemptytext="请选择县市"></fda:fda-exselect>
<fda:fda-exselect name="${currentTime}${name}XZ" style="width:150px;"
	datainterface="fdaDictionaryService.findList"
	clazz="input-dropdown validate[]"
	queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.JYCSXS}',ORDER_TYPE:'ASC'}"
	defaultemptytext="请选择乡镇/街道"></fda:fda-exselect>
<label>详细地址:</label><input type="text" style="width: 25%;" placeholder="请输入详细地址"
	maxlength="20" class="syj-input1 validate[required]" name="${currentTime}${name }XXDZ" />
	<a href="javascript:void(0);"
		 name="${currentTime }delScdz" class="syj-closebtn" style="position: relative;top:11px"></a>
</div>					
<script type="text/javascript">
	
	$(function(){
		$("[name='${currentTime }delScdz']").click(function(){
			$(this).parent().remove();
		});
		  $("select[name='${currentTime}${name}QS']").change(function(){ 
			  var typeCode = $(this).children("option:selected").val();
			  FdaAppUtil.reloadSelect($("select[name='${currentTime}${name}XS']"),{
				  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
			  });
			  FdaAppUtil.reloadSelect($("select[name='${currentTime}${name}XZ']"),{
				  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
			  });
		  });
		  $("select[name='${currentTime}${name}XS']").change(function(){ 
			  var typeCode = $(this).children("option:selected").val();
			  FdaAppUtil.reloadSelect($("select[name='${currentTime}${name}XZ']"),{
				  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  var s = $("select[name='${currentTime}${name}XZ'] option").size();
				  if(s==1){
					  $("select[name='${currentTime}${name}XZ']").removeClass(" validate[required]");
				  }else if(s>1){
					  if(!($("select[name='${currentTime}${name}XZ']").hasClass(" validate[required]"))){
						  $("select[name='${currentTime}${name}XZ']").addClass(" validate[required]");
					  }
				  }
			  });
			  
		  });
	});
</script>