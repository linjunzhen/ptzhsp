<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript">
	</script>
</head>
<body>
	<form action="" id="00007XK00115">
	<div class="container" style="width:980px;" >
		<div class="syj-sbmain2">
			<div class="syj-tyys" style="z-index: 99;">
				<div>
					<form>
						<div class="syj-title1">
							<span>申请材料核对表 &nbsp;&nbsp;&nbsp;<font style="color:red">(注：只有下面材料齐全有效后，您的申请才能受理)</font></span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<td>&nbsp;&nbsp;1、《道路旅客运输站经营申请表》（本表） <input type="checkbox" style="float:right;margin: 4px 25px 0 0;"></td>  
								</tr>
								<tr>
									<td>&nbsp;&nbsp;2、客运站竣工验收证明和站级验收证明          <input type="checkbox" style="float:right;margin: 4px 25px 0 0;">  </td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;3、拟招聘的专业人员、管理人员的身份证明和专业证书及其复印件 <input type="checkbox" style="float:right;margin: 4px 25px 0 0;">  </td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;4、负责人身份证明及其复印件，经办人的身份证明及其复印件和委托书 <input type="checkbox" style="float:right;margin: 4px 25px 0 0;">  </td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;5、业务操作规程和安全管理制度文本 <input type="checkbox" style="float:right;margin: 4px 25px 0 0;">  </td>
								</tr>
							</table>
						</div>
						
						
						<div class="syj-title1 tmargin20">
							<span>声明</font></span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<td>&nbsp;&nbsp;我声明本表及其他相关材料中提供的信息均真实可靠</td>  
								</tr>
								<tr>
									<td>&nbsp;&nbsp;我知悉如此表中有故意填写的虚假信息，我取得的客运站经营许可将被撤销 </td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;我承诺将遵守《中华人民共和国道路运输条例》及其他有关道路运输法规、规章的规定</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	</form>
</body>