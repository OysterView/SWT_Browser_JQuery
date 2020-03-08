function loadData(){
	//在js文件可以注释没有问题，可能HTML在加载的时候会处理吧，但不要在HTML里面写js的时候写注释，会有问题
	var datas = getDataFromJava("666");
	//从java拿到的是一个json string，需要把它转化为json object
	var dataJson = $.parseJSON( datas );
	$('#tt').datagrid('loadData', dataJson);
	$("#demo").text("总行数："+$('#tt').datagrid('getData')['total']);	
	$('#tt').datagrid('scrollTo',$('#tt').datagrid('getData')['total']-1);
}


function jsToJava(){
	alert('触发按钮');
	try {
		var res = jsEntity.toTest('我是js,我调用java了!');
		alert(res);
	} catch (e) {
		// TODO: handle exception
		alert(res);
	}
}
