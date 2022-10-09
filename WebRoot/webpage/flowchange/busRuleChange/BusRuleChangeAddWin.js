function loadFactorList(processCode){
	$("#busrulechange_addprocess").val(processCode);
	$('#BusRuleChangeAddGrid').datagrid({  //初始化datagrid
	    url:'busRuleChangeController.do?datagrid',
	    idField:"RULE_ID",
	    rownumbers: true,
	    fit:true,
	    border:false,
	    checkOnSelect:false,
	    selectOnCheck:false,
	    singleSelect:true,
	    fitColumns:true,
	    pagination:false,
	 	pageSize:15,
	 	pageList:[15,20,30],
	    toolbar: "#BusRuleChangeAddbar",
	    queryParams: {
	    	"Q_A.PROCESS_CODE_EQ" : processCode
		},	
		columns:[[
	        {field:'ck',checkbox:true},
	        {field:'RULE_ID',hidden:true},
	        /*{field:'BUS_NAME',title:'业务专项',width:150,align:'left'},
	        {field:'TACHE_NAME',title:'业务环节',width:150,align:'left'},
	        {field:'PROCESS_NAME',title:'监察点',width:150,align:'left'},*/
	        {field:'SUPER_ELEMENTS',title:'监察要素',width:100,align:'left'},	        
	        {field:'IS_ARTIFICIAL',title:'监察方式',width:80,align:'left',formatter:function(value,row,index){
	    		if (row.IS_ARTIFICIAL==0){
	    			return "<b><font color=green>自动监察</font></b>";
	    		} else if(row.IS_ARTIFICIAL==1){
	    			return "<b><font color=blue>人工监察</font></b>";
	    		}else{
	    			return "<b><font color=green>自动监察</font></b>";
	    		}
	    	}},	        
	        {field:'RULE_DESC',title:'规则描述',width:200,align:'left'}
	    ]],
	    toolbar:[{ text:'新增',iconCls:'icon-add',handler:addrow},
    		{ text:'编辑',iconCls:'icon-edit',handler:editrow},
    		{ text:'删除',iconCls:'icon-edit',handler:delrow}]		
	});	
}
function addrow(){
	var entityId = $("#busrulechange_addprocess").val();
	if (entityId) {
		var url="busRuleChangeController.do?winInfo&entityId=" + entityId; 
	    var name='监察规则信息';                        
	    var iWidth=650;                         
	    var iHeight=600;                       
	    var iTop = (window.screen.availHeight-30-iHeight)/2;
	    var iLeft = (window.screen.availWidth-10-iWidth)/2;
	    var params='dialogWidth='+iWidth+',dialogHeight='+iHeight
	                +',center=yes,resizable=no,scrollbars=no,status=no';		
		window.showModalDialog(url, name,params);
	}
}

function editrow(){
	var entityId = AppUtil.getEditDataGridRecord("BusRuleChangeAddGrid");
	if (entityId) {
		var url="busRuleChangeController.do?winEditInfo&entityId=" + entityId; 
	    var name='监察规则信息';                        
	    var iWidth=650;                         
	    var iHeight=600;                       
	    var iTop = (window.screen.availHeight-30-iHeight)/2;
	    var iLeft = (window.screen.availWidth-10-iWidth)/2;
	    var params='dialogWidth='+iWidth+',dialogHeight='+iHeight
	                +',center=yes,resizable=no,scrollbars=no,status=no';	
		window.showModalDialog(url, name,params);
		/*$.dialog.open("busRuleChangeController.do?info&entityId=" + entityId, {
    		title : "监察规则信息",
            width:"650px",
            lock: true,
            resize:false,
            height:"500px",
    	}, false);*/
	}
}

function delrow(){
	AppUtil.deleteDataGridRecord("busRuleChangeController.do?muitDel","BusRuleChangeAddGrid");
}