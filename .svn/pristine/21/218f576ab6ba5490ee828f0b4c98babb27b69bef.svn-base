<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
    function downMater(val,row){
        var a="";
        if(typeof(val)!='undefined'){
            a='<a class="btn1" href="javascript:void(0);"  onclick=downLoadFile("'+val+'");>下载</a>';
        }
        return a;
    }
    function downLoadFile(fileId){
        window.open(__ctxPath+"/DownLoadServlet?fileId="+fileId,"_blank");
    }
    function formatterStatus(val,row){
        if("1"==val){
            val="已面签";
        }else{
            val="未面签";
        }
        return val;
    }
</script>
<div id="zzhyAuditViewTabs" class="easyui-tabs eve-tabs" fit="true">
	<div title="面签信息">
		<div class="easyui-layout" fit="true">
			<div region="center">
				<!-- =========================表格开始==========================-->
				<table class="easyui-datagrid" rownumbers="false" pagination="false"
					   id="FlowTaskGrid" fitcolumns="true" nowrap="false"
					   method="post" idfield="TASK_ID" checkonselect="false"
					   selectoncheck="false" fit="true" border="false"
					   url="exeDataController.do?signDatagrid&exeId=${exeId}">
					<thead>
					<tr>
						<th data-options="field:'SIGN_NAME'" width="80">姓名</th>
						<th data-options="field:'SIGN_IDNO'" width="100">身份证号</th>
						<th data-options="field:'SIGN_VIDEO'" width="180" formatter="downMater" >面签视频</th>
						<th data-options="field:'SIGN_TIME'" width="180"  >面签时间</th>
						<th data-options="field:'SIGN_IDPHOTO_FRONT'"  width="180" formatter="downMater">身份证正面</th>
						<th data-options="field:'SIGN_IDPHOTO_BACK'" width="180" formatter="downMater">身份证反面</th>
						<th data-options="field:'SIGN_WRITE'" width="180" formatter="downMater">手写签名</th>
						<th data-options="field:'SIGN_FLAG'" width="80" formatter="formatterStatus">是否面签</th>
					</tr>
					</thead>
				</table>
				<!-- =========================表格结束==========================-->
			</div>
		</div>
	</div>
	<div title="公章附件信息">
		<div class="easyui-layout" fit="true"><div region="center">
			<!-- =========================表格开始==========================-->
			<table class="easyui-datagrid" rownumbers="false" pagination="false"
				   id="FlowTaskGrid1" fitcolumns="true" nowrap="false"
				   method="post" idfield="TASK_ID" checkonselect="false"
				   selectoncheck="false" fit="true" border="false"
				   url="exeDataController/companyFileDatagrid.do?exeId=${exeId}">
				<thead>
				<tr>
					<th data-options="field:'LEGAL_COMPANYNAME'" width="180"  >公司名</th>
					<th data-options="field:'LEGAL_PERSON'" width="80">法人姓名</th>
					<th data-options="field:'LEGAL_IDNO_PERSON'" width="100">法人身份证号</th>
					<th data-options="field:'LEGAL_FILEID'" width="180" formatter="downMater" >公章附件下载</th>
				</tr>
				</thead>
			</table>
			<!-- =========================表格结束==========================-->
		</div></div>
	</div>

</div>