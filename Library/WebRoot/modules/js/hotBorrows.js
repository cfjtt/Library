app.controller("hotBorrows",function($scope,$http){
	//查询出菜单
	$http({
		method:"post",
		url:"/Library/findFirstBookClass.do"
	}).success(function(data){
		if(data.flag){
			$scope.firstClasses=data.result;
		}
	});
	$scope.selectBook=function(type){
		var data={};
		if(type!=null){
			data={"firstClassId":type}
		}
		//查询出所有书籍的借阅次数
		$http({
			method:"post",
			url:"/Library/findBookInfoOrderBorrowTimes.do",
			params:data
		}).success(function(data){
			if(data.flag){
				$scope.items=data.result;
				$scope.desc=0;
			}
		});
	}
	$scope.selectBook();
	$scope.selectClass=function(type){
		console.log(type);
		$scope.selectBook(type);
	}
});