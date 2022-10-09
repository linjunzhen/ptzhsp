<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script>
        if (window.self === window.top) {
            window.location.href = __newUserCenter
        }
    </script>


	<base href="<%=basePath%>">
    <title>平潭综合实验区行政服务中心-用户中心</title>
<!--     <meta name="renderer" content="webkit"> -->
	<link rel="stylesheet" type="text/css" href="webpage/website/common/css/style4.css">
	<script type="text/javascript" src="plug-in/jquery/jquery2.js"></script>
	<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<eve:resources loadres="jquery,easyui,apputil,artdialog,json2,layer"></eve:resources>
	<script type="text/javascript">
	function bjlist(itemList){
		$("#wdbslist").html("");
	    var newhtml = "";
	    var artFlag="0";
	    for(var i=0; i<itemList.length; i++){
            newhtml +="<tr>";
            newhtml += "<td width=\"50px\" valign=\"top\">"+(i+1)+"</td>";
            newhtml += "<td width=\"140px\" valign=\"top\">"+itemList[i].EXE_ID+"</td>";
            newhtml += "<td width=\"260px\"  valign=\"top\" style=\"text-align:center;\">"+itemList[i].ITEM_NAME+"</td>";
            newhtml += "<td width=\"100px\" valign=\"top\">"+itemList[i].CREATE_TIME.substr(0,10)+"</td>";
            var isneedSign1 = itemList[i].ISNEEDSIGN;
            var zt =itemList[i].runStatusStr;
            if(isneedSign1=="1"&&zt.indexOf("窗口办理")>0){
                zt=zt.replace("窗口办理","执照审核");
            }
            // getZt(itemList[i]);//getZt(itemList[i].RUN_STATUS);
            newhtml += "<td width=\"100px\" valign=\"top\" style=\" text-align: center;\">"+zt;
            newhtml +="</td>";
            newhtml += "<td width=\"160px\" valign=\"top\" id=\"list"+i+"\">";
            if(itemList[i].RUN_STATUS==1){//正在办理
                if(itemList[i].TASK_STATUS=='1'){//正常流程流转待办任务===OK
                    newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="
                    +itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID+"&taskId="+itemList[i].TASK_ID+"\"  class=\"userbtn3\" target=\"_blank\">办理</a></td>";
                }else if(itemList[i].TASK_STATUS=='-1'){//退回补件-待办任务==OK
                    newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="
                    +itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID+"&taskId="+itemList[i].TASK_ID+"\"  class=\"userbtn3\" target=\"_blank\">办理</a>";
                    newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;\" onclick=\"thsm('"+itemList[i].BJXX_ID
                    +"','"+encodeURIComponent(itemList[i].BACKOPINION)+"','"+itemList[i].APPLY_STATUS+"')\"  class=\"userbtn3\">退回说明</a>";
                    newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;\" onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  class=\"userbtn3\">查看回执</a></td>";
                }else if(itemList[i].preAuditState=='1'){//预审待审核，详情、撤办；===OK
                    newhtml += "<a href=\"javascript:void(0);\" onclick=\"revokeFlow('"
                        +itemList[i].EXE_ID+"');\" class=\"userbtn3\">撤回</a>";
                    newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"&isQueryDetail=true\" class=\"userbtn3\" target=\"_blank\">详情</a>";
                }else if(itemList[i].APPLY_STATUS=='7'){//退件待办===OK
                    newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="
                    +itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID+"&taskId="+itemList[i].TASK_ID+"\"  class=\"userbtn3\" target=\"_blank\">办理</a>";
                    newhtml += "<a href=\"javascript:void(0);\" onclick=\"ckbjyj('"
                        +encodeURIComponent(itemList[i].BACKOPINION)+"');\" class=\"userbtn3\">退件意见</a>";
                    newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"&isQueryDetail=true\" class=\"userbtn3\" target=\"_blank\">详情</a>";
                    newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;\" onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  class=\"userbtn3\">查看回执</a></td>";
                }else{//正在办理===OK
                     newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                         +"&isQueryDetail=true\" class=\"userbtn3\" target=\"_blank\" style=\"margin-top: 3px;float: left;\">详情</a> ";
                     newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;float: left;\" onclick=\"ckhz('"
                        +itemList[i].EXE_ID+"')\"  class=\"userbtn3\">查看回执</a>";
                    var CUR_STEPNAMES = itemList[i].CUR_STEPNAMES;
                    if(CUR_STEPNAMES == '窗口办理'){
                         newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;float: left;\" onclick=\"clxz('"+itemList[i].EXE_ID+"','"+
                        itemList[i].ITEM_CODE+"');\"  class=\"userbtn3\">材料下载</a>";
                    }
                     var ITEM_CODE = itemList[i].ITEM_CODE;
                     if(ITEM_CODE.indexOf("201605170002XK001")>-1){
    //					 newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;float: left;\" onclick=\"sqcb('"+itemList[i].EXE_ID+"','"+
    //					itemList[i].ITEM_CODE+"');\"  class=\"userbtn3\">申请撤办</a>";
                         newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;float: left;\" onclick=\"showOpinion('"
                             +itemList[i].EXE_ID+"')\"  class=\"userbtn3\">审核意见</a>";
                     }
                    var ISNEEDSIGN = itemList[i].ISNEEDSIGN;
                     //面签信息展示
                     if(CUR_STEPNAMES == '身份认证'&&ISNEEDSIGN=='1'){
                         newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;float: left;\" onclick=\"signInfo('"+itemList[i].EXE_ID+"','"+
                             itemList[i].ITEM_CODE+"');\"  class=\"userbtn3\">面签信息</a>";
                         //身份认证提醒
                         if(artFlag=="0"){
                             artFlag="1";
                             art.dialog({
                                 content: "您有待身份认证办件，请点击待身份认证办件面签信息查看详情。",
                                 icon: "succeed",
                                 time: 300,
                                 ok: function () {
                                     //AppUtil.closeLayer();
                                 }
                             })
                         }
                     }
                    var ISFACESIGN = itemList[i].ISFACESIGN;
                    if(CUR_STEPNAMES == '身份认证'&&ISNEEDSIGN=='1'&&ISFACESIGN!='1'){
                        newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;float: left;\" onclick=\"uploadCompanyFile('"+itemList[i].EXE_ID+"','"+
                            itemList[i].ITEM_CODE+"');\"  class=\"userbtn3\">上传公章</a>";
                    }
                     newhtml += "</td>";
                }
            }else if(itemList[i].RUN_STATUS==0){//草稿===PRE--OK
                newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"\" class=\"userbtn3\" target=\"_blank\">编辑</a>";
                newhtml += "<a href=\"javascript:void(0);\" onclick=\"deleteFlow('"
                        +itemList[i].EXE_ID+"');\" class=\"userbtn3\">删除</a></td>";
            }else if(itemList[i].RUN_STATUS==7){//已办结(预审不通过)===OK
                newhtml += "<a href=\"javascript:void(0);\" onclick=\"ckysyj('"
                        +itemList[i].EXE_ID+"');\" class=\"userbtn3\">预审意见</a>";
                /*if(itemList[i].isPj!='1'){
                    newhtml += "<a href=\"javascript:void(0);\"  class=\"userbtn3\" onclick=\"bjpj('"+itemList[i].EXE_ID+"','"+
                    itemList[i].ITEM_CODE+"');\">评价</a>";
                }else{
                    newhtml += "<a href=\"javascript:void(0);\"  class=\"userbtn3\" >已评价</a>";
                }*/
                newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"&isQueryDetail=true\" class=\"userbtn3\" target=\"_blank\">详情</a>";
                newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;\" onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  class=\"userbtn3\">查看回执</a></td>";
            }else{//已办结===OK
                newhtml += "<a href=\"javascript:void(0);\" onclick=\"ckbjyj('"
                        +encodeURIComponent(itemList[i].FINAL_HANDLEOPINION)+"');\" class=\"userbtn3\">办结意见</a>";
                var ISGETBILL = itemList[i].ISGETBILL;
                if(itemList[i].ISGETBILL=='1'){
                    newhtml += "<a href='https://etax.fujian.chinatax.gov.cn/xxmh/html/index_login.html'  class=\"userbtn3\" target='_blank'>申领发票</a>";
                }
                /*if(itemList[i].isPj!='1'){
                    newhtml += "<a href=\"javascript:void(0);\"  class=\"userbtn3\" onclick=\"bjpj('"+itemList[i].EXE_ID+"','"+
                    itemList[i].ITEM_CODE+"');\">评价</a>";
                }else{
                    newhtml += "<a href=\"javascript:void(0);\"  class=\"userbtn3\" >已评价</a>";
                }*/
                newhtml += "<a href=\"webSiteController.do?applyItem&itemCode="+itemList[i].ITEM_CODE+"&exeId="+itemList[i].EXE_ID
                    +"&isQueryDetail=true\" class=\"userbtn3\" target=\"_blank\">详情</a>";
                newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;\" onclick=\"ckhz('"+itemList[i].EXE_ID+"')\"  class=\"userbtn3\">查看回执</a>";

                var IS_OPEN = itemList[i].IS_OPEN;
                if(IS_OPEN==1){
                newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;\" onclick=\"ckjg('"+itemList[i].EXE_ID+"')\"  class=\"userbtn3\">查看结果</a>";
                }
                var ITEM_CODE = itemList[i].ITEM_CODE;
                if(ITEM_CODE.indexOf("201605170002XK001")>=0){
                     newhtml += "<a href=\"javascript:void(0);\" style=\"margin-top: 3px;float: left;\" onclick=\"clxz('"+itemList[i].EXE_ID+"','"+
                    itemList[i].ITEM_CODE+"');\"  class=\"userbtn3\">材料下载</a>";
                 }
                newhtml += "</td>";
            }
            newhtml +="</tr>";
	    } 
	    $("#wdbslist").html(newhtml);

	    /*添加评价按钮*/
        for(var i=0; i<itemList.length; i++){
            if (itemList[i].RUN_STATUS != 0) {
                $.ajax({
                    url:"bspjController/getSwpjMessage.do",
                    method:"post",
                    data:{EXE_ID: itemList[i].EXE_ID},
                    async: false,
                    success:function (data) {
                        if (data) {
                            var obj = JSON.parse(data);
                            if (obj.url) {
                                console.log(obj);
                                if (obj.status == 'ypj') {
                                    $("#list"+i) .append("<a href='"+obj.url+"' class='userbtn3' target='_blank'>查看评价</a>");
                                } else if (obj.status == 'wpj'&&(itemList[i].RUN_STATUS=='2'||itemList[i].RUN_STATUS=='4')) {
                                    console.log($("#list"+i))
                                    $("#list"+i).append("<a href='"+obj.url+"' class='userbtn3' target='_blank'>评价</a>");
                                }
                            }
                        }
                    }
                })
            }
        }
	}
	
	function revokeFlow(exeId){
		if (exeId) {
			art.dialog.confirm("您确定要撤回该流程吗?", function() {
				var layload = layer.load("正在提交请求中…");
				$.post("executionController.do?getBackMyApply",{
					exeId:exeId
				}, function(responseText, status, xhr) {
				    layer.close(layload);
					var resultJson = $.parseJSON(responseText);
					if(resultJson.success){
						art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							lock : true,
							ok:function(){
								reload();
							},
							close: function(){
								reload();
							}
						});
					}else{
						art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				});
			});
		}
	}
	/**
	*  流程回退操作
	**/
	function FLOW_Recover(exeId){	
		if(exeId){
			$.post("executionController.do?getBackMyApply",{
				exeId:exeId
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if(resultJson.success){
					art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						lock : true,
						ok:function(){
							reload();
						},
						close: function(){
							reload();
						}
					});
				}else{
					art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			});
		}
	}

	function deleteFlow(exeId){
		art.dialog.confirm("您确定要删除该记录吗?", function() {
				var layload = layer.load('正在提交请求中…');  
				var selectColNames =exeId;
				$.post("executionController.do?multiDelDraft",{
					   selectColNames:selectColNames
				    }, function(responseText, status, xhr) {
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							reload();
						}else{
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
			});
	}
	function getZt(itemList){
		var status=itemList.RUN_STATUS;
		var taskStatus=itemList.TASK_STATUS;
		var applyStatus=itemList.APPLY_STATUS;
		var preAuditState=itemList.preAuditState;
		var s = "";
		if(status=='0'){
			s="草稿";
		}else if(status=='1'){
			if(taskStatus=='1'||taskStatus=='-1'){
				s="<b style=\"color: #008000;\">待办</b>";
			}else if(applyStatus=='1'){
				s="<b style=\"color: #008000;\">预审中</b>";
			}else if(applyStatus=='2'&&itemList.preAuditState=='1'){
				s="<b style=\"color: #008000;\">预审通过</b>";
			}else{
				s="<b style=\"color: #008000;\">正在办理</b>";
			}
		}else if(status=='2'){
            s="<b style=\"color:blue\">已办结</b>";
        }else if(status=='3'){
            s="<b style=\"color: #008000;\">审核通过</b>";
        }else if(status=='4'){
            s="<b style=\"color: #008000;\">审核不通过</b>";
        }else if(status=='5'){
            s="<b style=\"color: #008000;\">退件办结</b>";
        }else if(status=='6'){
            s="<b style=\"color:blue\">已办结</b>";
        }else if(status=='7'){
            s="<b style=\"color:blue\">预审不通过</b>";
        }
		
		return s;
	}
    function showOpinion(exeId) {
            $.dialog.open("webSiteController/showOpinion.do?exeId="+exeId, {
                title: "审核信息",
                width: "900px",
                height: "400px",
                lock: true,
                resize: false
            }, false);
    }
	function thsm(bjid,backinfo,applyStatus){
        backinfo = decodeURIComponent(backinfo);
		if(bjid!=''&&backinfo!=''&&(applyStatus=='4'||applyStatus=='7')){
			$.dialog.open("webSiteController/thbjxx.do?&BJID="+bjid, {
                title : "退回补件信息",
                width : "900px",
                height : "400px",
                lock : true,
                resize : false
            }, false);
		}else if(bjid==''&&backinfo!=''&&applyStatus=='1'){
			art.dialog({
	            title: '退回意见',
	            content: backinfo,
	            lock : true,
	            width : "400px",
	            ok: true
	        });
		}else if(bjid!=''&&backinfo!=''&&applyStatus=='1'){
            art.dialog({
                title: '退回意见',
                content: backinfo,
                lock : true,
                width : "400px",
                ok: true
            });
        }else if(backinfo!=''){
            art.dialog({
                title: '退回意见',
                content: backinfo,
                lock : true,
                width : "400px",
                ok: true
            });
        }
	}
	
	function ckbjyj(con){
	    con = decodeURIComponent(con)
		if(con=="undefined"){
			con="无";
		}
		art.dialog({
			title: '办结意见',
			content: con,
			lock : true,
			width : "400px",
			ok: true
		});
	}
	function bjpj(exeId,itemCode){
		$.dialog.open("bspjController/pjxx.do?exeId="+exeId+"&itemCode="+itemCode, {
            title : "评价信息",
            width : "600px",
            height : "200px",
            lock : true,
            resize : false,
            close: function () {
                reload();
            }
        }, false);
	}
	   function ckhz(exeId){
	        $.dialog.open("webSiteController/hzxx.do?&exeId="+exeId, {
	            title : "查看回执信息",
	            width : "800px",
	            height : "400px",
	            lock : true,
	            resize : false
	        }, false);
	    }
	   function ckjg(exeId){
	        $.dialog.open("webSiteController/jgxx.do?&exeId="+exeId, {
	            title : "查看结果信息",
	            width : "800px",
	            height : "500px",
	            lock : true,
	            resize : false
	        }, false);
	    }
	function clxz(exeId,itemCode){
		$.dialog.open("webSiteController/clxz.do?itemCode="+itemCode+"&exeId="+exeId, {
			title : "材料下载",
			width : "1000px",
			height : "400px",
			lock : true,
			resize : false
		}, false);
	}
    function uploadCompanyFile(exeId,itemCode){
        $.dialog.open("exeDataController/uploadCompanyFileView.do?itemCode="+itemCode+"&exeId="+exeId, {
            title : "上传公司公章",
            width : "1000px",
            height : "500px",
            lock : true,
            ok:function(){
                window.top.location.href=__newUserCenter;
            },
            close: function(){
                window.top.location.href=__newUserCenter;
            },
            resize : false
        }, false);
    }
	function sqcb(exeId,itemCode){
		$.dialog.open("userInfoController.do?sqcb&exeId="+exeId, {
			title : "申请撤办",
			width : "600px",
			height : "240px",
			lock : true,
			resize : false,
			close: function(){
				reload();
			}
		}, false);
	}
    function signInfo(exeId,itemCode){
        $.dialog.open("exeDataController/fontSignInfo.do?exeId="+exeId, {
            title : "面签信息",
            width : "80%",
            height : "800px",
            lock : true,
            resize : false,
            close: function(){
                reload();
            }
        }, false);
    }


	function ckysyj(exeId){	
		$.dialog.open("webSiteController/ystjxx.do?&exeId="+exeId, {
	              title : "预审退回信息",
	              width : "900px",
	              height : "400px",
	              lock : true,
	              resize : false
	          }, false);
	}
	</script>
</head>

<body >
    <div>
    	<div class="nmain clearfix">
        	<%--结束引入用户中心的左侧菜单 --%>
            <div class="nmainZ">
            	<div class="nmainRtitle" style="margin-top:0px;"><h3>我的办件</h3></div>
                <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10">
                    <tr>
                        <th width="50px">序号</th>
                        <th width="140px">申报号</th>
                        <th width="260px">事项名称</th>
                        <th width="100px">提交时间</th>
                        <th width="100px">当前状态</th>
                        <th width="160px" style="background-image:none;">操作</th>
                    </tr>
                 </table>
                 <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10" id="wdbslist">
                 </table>
                <table cellpadding="0" cellspacing="0" class="zxtable2 tmargin10">
                    <tr>
                        <th colspan="6">备注：当前状态为窗口办理时请按照要求下载材料，并将签署好的材料递交到平潭综合实验行政服务中心商事登记窗口；<br/>
                        				为预审未通过时请按照审核人员的修改意见修改后，重新提交</th>
                    </tr>
                 </table>
                <%--开始引入分页JSP --%>
	            <jsp:include page="../common/page.jsp" >
	            <jsp:param value="userInfoController.do?wdbjlist" name="pageURL"/>
	            <jsp:param value="bjlist" name="callpage"/>
	            <jsp:param value="10" name="limitNum"/>
	            </jsp:include>
	            <%--结束引入分页JSP--%>
            </div>
        </div>
    </div>
</body>

