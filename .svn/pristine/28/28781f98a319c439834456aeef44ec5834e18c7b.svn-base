<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<script type="text/javascript">
    //窗口办理环节，面签审核
    $(function(){
        var flowSubmitObj = FlowUtil.getFlowObj();
        if(flowSubmitObj) {
            var isneedsign="${execution.isneedsign}";
            if(flowSubmitObj.EFLOW_CURUSEROPERNODENAME=='窗口办理'&&isneedsign==1){
                $("#SIGNAUDIT").attr("style","display:block");
                $("input[name='FACESIGNPASS']").attr("disabled",false);
                showSign(0);
            }
        }
    });
    function downLoadSignFile(fileId){
        window.open(__ctxPath+"/DownLoadServlet?fileId="+fileId,"_blank");
    }
    function delSign(val, num, signId) {
        var inputName="SIGN_OPINION" + num;
        var SIGN_FLAG="SIGN_FLAG"+num;
        var opinion =$("input[name='"+inputName+"']").val();
            $.ajax({
                type: "post",
                url: "exeDataController/updateSignStatus.do?signId=" + signId + "&opinion=" + opinion + "&signFlag="+val,
                dataType: "json",
                async: false,
                success: function (data) {
                    parent.art.dialog({
                        content: data.msg,
                        icon: "succeed",
                        ok: true
                    });
                }
            });
    }
    function delSignByOpinion( num, signId) {
        var inputName="SIGN_OPINION" + num;
        var SIGN_FLAG="SIGN_FLAG"+num;
        var opinion =$("input[name='"+inputName+"']").val();
        var val =$("input[name='"+SIGN_FLAG+"']:checked").val();
            $.ajax({
                type: "post",
                url: "exeDataController/updateSignStatus.do?signId=" + signId + "&opinion=" + opinion + "&signFlag="+val,
                dataType: "json",
                async: false,
                success: function (data) {

                }
            });
    }
    function showSign(val) {
        if (val == '0') {
            var exeId = '${execution.EXE_ID}';
            $.ajax({
                type: "post",
                url: "exeDataController.do?signDatagrid&exeId=" + exeId,
                dataType: "json",
                async: false,
                success: function (data) {
                    var html = "";
                    var num = 1;
                    if (data.total > 0) {
                        var rows= data.rows;
                        for (var i = 0; i < rows.length; i++) {
                            var html = "";
                            html += '<tr >';
                            html += '<td>' + (i + 1) + '</td>';
                            html += '<td>' + rows[i].SIGN_NAME + '</td>';
                            html += '<td>' + rows[i].SIGN_IDNO + '</td>';
                            if(rows[i].SIGN_FLAG=='1'){
                                html += '<td>' +"<a href='javascript:void(0);' onclick=\"downLoadSignFile(\'"+rows[i].SIGN_VIDEO+"\');\">下载</a></td>'";
                                html += '<td>' +"<a href='javascript:void(0);' onclick=\"downLoadSignFile(\'"+rows[i].SIGN_IDPHOTO_FRONT+"\');\">下载</a></td>'";
                                html += '<td>' +"<a href='javascript:void(0);' onclick=\"downLoadSignFile(\'"+rows[i].SIGN_IDPHOTO_BACK+"\');\">下载</a></td>'";
                                html += '<td>' +"<a href='javascript:void(0);' onclick=\"downLoadSignFile(\'"+rows[i].SIGN_WRITE+"\');\">下载</a></td>'";
                                html += '<td>' + "<input style='width:400px;height:30px;'"  + "onblur=\"delSignByOpinion(\'"+num+"\',\'"+rows[i].SIGN_ID+"\');\""  +  "name='SIGN_OPINION"+num+"'/>" + '</td>';
                               // html += '<td>' +"<a href=\"javascript:void(0);\"  onclick=\"delSign(this,\'"+num+"\',\'"+rows[i].SIGN_ID+"\');\"   class=\"syj-closebtn\"></a></td>";
                                html += '<td>'+
                                    "<input type=\"radio\" name=\"SIGN_FLAG"+num + "\"onclick=\"delSign(\'1\',\'"+num+"\',\'"+rows[i].SIGN_ID+"\');\" value=\"1\" checked=\"checked\" >通过"+
                                    "<input type=\"radio\" name=\"SIGN_FLAG"+num + "\"onclick=\"delSign(\'-1\',\'"+num+"\',\'"+rows[i].SIGN_ID+"\');\"  value=\"-1\" >不通过"
									+"</td>";
                            }
                            html += '</tr>';
                            $("#signShow").append(html);
                            num=num+1;
                        }
                    }
                }
            });

        }
    }

</script>

	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="1">面签审核</th>
		</tr>

		<tr>
			<input type="hidden" name="ISNEEDSIGN" value="${execution.ISNEEDSIGN}">
			<td><span
					style="width: 100px;float:left;text-align:right;">是否面签通过：</span>
				<eve:radiogroup typecode="YesOrNo" width="140px"
								defaultvalue="1"
								fieldname="FACESIGNPASS">
				</eve:radiogroup></td>
		</tr>
	</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="signShow">
	<tr>
		<td class="tab_width1" style="width: 3%;color: #000; font-weight: bold;text-align: center;">序号</td>
		<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">姓名</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">身份证号</td>
		<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">面签视频</td>
		<td class="tab_width1" style="width: 7%;color: #000; font-weight: bold;text-align: center;">身份证正面</td>
		<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">身份证反面</td>
		<td class="tab_width1" style="width: 5%;color: #000; font-weight: bold;text-align: center;">手写签名</td>
		<td class="tab_width1" style="width: 20%;color: #000; font-weight: bold;text-align: center;">审核意见（不通过才填写）</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">面签是否通过</td>
	</tr>

</table>