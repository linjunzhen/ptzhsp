
function ChangDiv(uid,css,num,div)
{  
  var tabList = document.getElementById(uid).getElementsByTagName("li");
for(i=0; i<tabList.length; i++)
{
  if (i ==num )
  { 

  tabList[i].className =css;

 document.getElementById(""+div+""+i+"").style.display="block";
    
  }else{

  tabList[i].className = "none"; 
 document.getElementById(""+div+""+i+"").style.display="none";
    }
} 

}

function ChangClass(uid,css,num)
{    
  var tabList = document.getElementById(uid).getElementsByTagName("li");

for(i=0; i<tabList.length; i++)
{
  if (i==num )
  { 

  tabList[i].className =css;
  document.getElementById("subNav"+i+"").style.display="block";

  }else{
      tabList[i].className ="none"; 
      document.getElementById("subNav"+i+"").style.display="none";

      }
} 

}
	function dis(id,num)
	{
		for (var i=1;i<=num;i++)
		{
			if(id==i)
			{
				if(document.getElementById("ul"+i).style.display=="block"){
					document.getElementById("ul"+i).style.display="none"
				}else{
					document.getElementById("ul"+i).style.display="block"
				}			
				
			}else{
				document.getElementById("ul"+i).style.display="none";
			}
		}
	}

function fontZoom(size)
{
 document.getElementById('fontzoom').style.fontSize=size+'px'
}

//验证Email
function checkEmail(str)
{
	var filter=/\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	return filter.test(str);
}

//统一的删除JS方法
function getDel(ckid,urlpath)
{
	if(getCheckBoxCheckCount(ckid)==0)
	{
		alert('请选择您要删除的记录！');
		return;
	}
	if(confirm('你确定要删除这些记录吗？'))
	{
		document.getElementById('editForm').action=urlpath;
		document.getElementById('ids').value=getCheckBoxValue(ckid);
		document.getElementById('editForm').submit();
	}
}

//执行全选/全不选
//第一个参数为基准值Checkbox的name,第二个参数为你想全选的checkbox的Name
function allCheckBox(normalCKName,ckName) {
	var checkbox = document.getElementsByName(ckName);
	for (var i = 0; i < checkbox.length; i=i+1) {
		checkbox[i].checked = document.getElementsByName(normalCKName)[0].checked;
	}
}