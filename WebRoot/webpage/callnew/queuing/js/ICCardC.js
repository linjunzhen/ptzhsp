<!-- IO 主控板-->
$(document).ready(function(){
	$("#StateResult").val("欢迎使用\r\n");
	$("#IDCardCReadInfo").click(function(){
		DriverManager.ReadCardInfo(CallBackIDCardInfoFun,"3030");
	});
	$("#GetConnectStatus").click(function(){
		DriverManager.GetDeviceStatus(CallBackIDCardInfoFun,"3030",0);//设备求状态 0，表示获取二代身份证设备连接状态。
	});
	$("#OpenICCardCDevice").click(function(){
	DriverManager.OpenDevice(CallBackOpenDevice,'3030');//打开设备，完成后回调CallBackOpenDevice	
	});
	//回调方法，自定义名称
	function CallBackOpenDevice(deviceId,result,Description)
	{
		 $("#StateResult").val( $("#StateResult").val()+'CallBackOpenDevice: DeviceId='+deviceId+" result="+result+" 描述："+Description+"\r\n");
	}
	$("#clean").click(function(){
		$("#StateResult").val("");
	});
	function CallBackIDCardInfoFun(deviceId,result,Description)
	{
		alert(222);
//		 $("#StateResult").val( $("#StateResult").val()+'\r\nCallBackPrintFun: DeviceId='+deviceId+" result="+result+" "+Description);
		 if(result==3006 )//读取身份证成功时，读取照片的Base64字符串
		{
				$("input[name='lineName']").val(result.Name);
				$("input[name='lineName']").val(result.IDCarNo);
				//$("input[name='lineCardNo']").val(CVR_IDCard.CardNo); 
//			 $("#StateResult").val( $("#StateResult").val()+"\r\n Base64:\r\n"+DriverManager.GetB64String("c:\\zp.jpg"));
		}
	}
	$("#GetModuleStatus").click(function(){
	/*模块工作状态查找：是否实例化?硬件工作状态？版本查找：驱动文件名，版本号，最后升级日期
	GetModuleStatus(callback,deviceId,index);
	index: 0 查询模块是不已实例化 ；0表示 未实例化，1表已经实例化
		   1 设备端口状态，0表示未设置，1 表示端口正常  2表示端口打开失败
		   2 返回设备驱动的文件名
		   3 返回设备驱动的版本号
		   4 返回设备驱动的最后更新日期
		   5 返回设备硬件支持列表
		   返回的 result-2030+ i= index;
		   */
	DriverManager.GetModuleStatus(CallBackDeviceGetStatus,'3030',0);//查询模块是不已实例化
	DriverManager.GetModuleStatus(CallBackDeviceGetStatus,'3030',1);//设备端口状态
	DriverManager.GetModuleStatus(CallBackDeviceGetStatus,'3030',2);//返回设备驱动的文件名
	DriverManager.GetModuleStatus(CallBackDeviceGetStatus,'3030',3);//返回设备驱动的版本号
	DriverManager.GetModuleStatus(CallBackDeviceGetStatus,'3030',4);//返回设备驱动的最后更新日期
	DriverManager.GetModuleStatus(CallBackDeviceGetStatus,'3030',5);//返回设备硬件支持列表
	});
	//回调方法，自定义名称
	function CallBackDeviceGetStatus(deviceId,result,Description)
	{
		 if(result-2030==0)
		 $("#StateResult").val( $("#StateResult").val()+'\r\n查询模块是否已实例化---->');
		  if(result-2030==1)
		 $("#StateResult").val( $("#StateResult").val()+'\r\n查询模块设备端口状态---->');
		  if(result-2030==2)
		 $("#StateResult").val( $("#StateResult").val()+'\r\n查询模块设备驱动的文件名---->');
		  if(result-2030==3)
		 $("#StateResult").val( $("#StateResult").val()+'\r\n查询模块设备驱动的版本号---->');
		  if(result-2030==4)
		 $("#StateResult").val( $("#StateResult").val()+'\r\n查询模块设备驱动的最后更新日期---->');
		  if(result-2030==5)
		 $("#StateResult").val( $("#StateResult").val()+'\r\n查询模块设备硬件支持列表---->');
   		  $("#StateResult").val( $("#StateResult").val()+'应答: DeviceId='+deviceId+" result="+result+" 结果值："+Description);
	}
});
 	



	
	