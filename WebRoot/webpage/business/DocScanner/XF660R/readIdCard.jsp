<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
    <head>
        <title>良田高拍仪-XF660R-二代证读取页</title>
        <meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8" />
        <link rel="stylesheet" type="text/css" href="<%=path%>/webpage/business/DocScanner/XF660R/css/style.css">
        <script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery2.js"></script>
        <link rel="stylesheet" href="<%=path %>/plug-in/artdialog-4.1.7/skins/default.css" type="text/css"></link>
        <script type="text/javascript" src="<%=path %>/plug-in/artdialog-4.1.7/jquery.artDialog.js?skin=default"></script>
        <script type="text/javascript" src="<%=path %>/plug-in/artdialog-4.1.7/plugins/iframeTools.source.js"></script>
        <object id="EloamGlobal_ID" classid="CLSID:52D1E686-D8D7-4DF2-9A74-8B8F4650BF73"></object>
        <script type="text/javascript">
	        var EloamGlobal;
	        
	        var idCardData = null;
			
			function Load(){
				EloamGlobal = document.getElementById("EloamGlobal_ID");
				var ret = EloamGlobal.InitDevs();
				if(!ret){
					alert('初始化高拍仪设备失败,请确认设备是否连接以及控件是否正确安装!');
				}
			}
			
			function Unload(){
				EloamGlobal.DeinitDevs();
				EloamGlobal = null;
			}
			
			function ReadIDCard(){
				try{
					//初始化二代证模块
					if(EloamGlobal.InitIdCard()){
						//读取二代证
						var ret = EloamGlobal.ReadIdCard();
						if(ret){
							//EloamGlobal.GetIdCardData(flag);
							//1 姓名 2 性别 3 籍贯 4 出生年 5 出生月 6 出生日 7 住址 
							//8 身份证号码 9 签发机关 10 有效期限年(起) 11 有效期限月(起) 
							//12 有效期限日(起) 13 有效期限年(止) 14 有效期限月(止) 15 有效期限日(止) 16 芯片序列号
							$('#name').html(EloamGlobal.GetIdCardData(1));
							$('#sex').html(EloamGlobal.GetIdCardData(2));
							$('#nation').html(EloamGlobal.GetIdCardData(3));
							$('#birthday').html(EloamGlobal.GetIdCardData(4)+'年'+EloamGlobal.GetIdCardData(5)+'月'+EloamGlobal.GetIdCardData(6)+'日');
							$('#idNum').html(EloamGlobal.GetIdCardData(8));
							$('#addr').html(EloamGlobal.GetIdCardData(7));
							$('#signDept').html(EloamGlobal.GetIdCardData(9));
							$('#validDate').html(EloamGlobal.GetIdCardData(10)+'.'+EloamGlobal.GetIdCardData(11)+'.'+EloamGlobal.GetIdCardData(12)+'-'+
									             EloamGlobal.GetIdCardData(13)+'.'+EloamGlobal.GetIdCardData(14)+'.'+EloamGlobal.GetIdCardData(15));
							idCardData = {
							    name: EloamGlobal.GetIdCardData(1),
							    sex: EloamGlobal.GetIdCardData(2),
							    nation: EloamGlobal.GetIdCardData(3),
							    birthYear: EloamGlobal.GetIdCardData(4),
							    birthMonth: EloamGlobal.GetIdCardData(5),
							    birthDay: EloamGlobal.GetIdCardData(6),
							    address: EloamGlobal.GetIdCardData(7),
							    idNum: EloamGlobal.GetIdCardData(8),
							    signDept: EloamGlobal.GetIdCardData(9),
							    validDateBY: EloamGlobal.GetIdCardData(10),
							    validDateBM: EloamGlobal.GetIdCardData(11),
							    validDateBD: EloamGlobal.GetIdCardData(12),
							    validDateEY: EloamGlobal.GetIdCardData(13),
							    validDateEM: EloamGlobal.GetIdCardData(14),
							    validDateED: EloamGlobal.GetIdCardData(15)
							};
						}else{
							alert('读取二代证失败,请拿起证件再重新放在验证机具的感应区上!');
						}
						EloamGlobal.DeinitIdCard();
					}else{
						alert('初始化二代证阅读模块失败,请尝试关闭浏览器再重新登录系统!');
					}
				}catch(e){
					alert('操作失败,设备未连接或读卡控件未安装!');
				}
			}
			
			function Confirm(){
				if(idCardData){
					art.dialog.data("idCardReadInfo", idCardData);
					Cancel();
				}else{
					alert('操作失败,未读取到身份证信息!');
				}
			}
			
			function Cancel(){
				Unload();
    			art.dialog.close();
			}
        </script>
    </head>
    <body onload="Load();" onunload="Unload();">
        <div style="padding:0 20px;">
            <table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	        	<tr>
	                <th colspan="4">身份证信息</th>
	            </tr>
	            <tr>
	                <td class="tab_width" style="width:15%;">姓名：</td>
	                <td style="width:35%;">
	                    <span id="name"></span>
	                </td>
	                <td class="tab_width" style="width:15%;">性别：</td>
	                <td style="width:35%;">
	                    <span id="sex"></span>
	                </td>
	            </tr>
	            <tr>
	                <td class="tab_width">民族：</td>
	                <td>
	                    <span id="nation"></span>
	                </td>
	                <td class="tab_width">出生：</td>
	                <td>
	                    <span id="birthday"></span>
	                </td>
	            </tr>
	            <tr>
	                <td class="tab_width">身份证号：</td>
	                <td colspan="3">
	                    <span id="idNum"></span>
	                </td>
	            </tr>
	            <tr>
	                <td class="tab_width">住址：</td>
	                <td colspan="3">
	                    <span id="addr"></span>
	                </td>
	            </tr>
	            <tr>
	                <td class="tab_width">签发机关：</td>
	                <td colspan="3">
	                    <span id="signDept"></span>
	                </td>
	            </tr>
	            <tr>
	                <td class="tab_width">有效期限：</td>
	                <td colspan="3">
	                    <span id="validDate"></span>
	                </td>
	            </tr>
	        </table>
	        <div class="gpy-sub" style="margin-top:10px;">
	            <a class="camera" href="javascript:void(0);" onclick="ReadIDCard();">
	        	    <span><i></i>读取二代证</span>
	        	</a>
	        	<a class="submit" href="javascript:void(0);" onclick="Confirm();">
	        	    <span><i></i>确 认</span>
	        	</a>
	        	<a class="cancel" href="javascript:void(0);" onclick="Cancel();">
	        	    <span><i></i>取 消</span>
	        	</a>
	        </div>
        </div>
    </body>
</html>