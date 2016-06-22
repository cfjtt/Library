app.controller("punish",function($scope,$http){
	//初始化的时候读取properties文件，并将初始化信息展示在 页面
	$http({
		method:"post",
		url:"/Library/punish.do"
	}).success(function(data){
		console.log(data);
		$scope.days=data.result.day;
		$scope.miss=data.result.miss;
		$scope.avoidDay=data.result.isAllow;
			
		
	});
	
	
	/**
	 * 修改操作
	 */
	$scope.sure=function(){
		$http({
			method:"post",
			url:"/Library/punish.do",
			params:{"day":$scope.days,"miss":$scope.miss,"avoidDay":$scope.avoidDay}
		}).success(function(data){
			if(data.flag){
				$scope.message="保存成功";
				$("#alert").modal("show");
			}
		}).error(function(){
			$scope.message="保存失败,请稍后重试";
			$("#alert").modal("show");
		});
	}
});