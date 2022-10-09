    function showExitWin(){
    	art.dialog.confirm("您确定要退出系统吗?", function () {
    		window.top.location.href="loginController.do?logout";
    	}, function () {
    	});
    }