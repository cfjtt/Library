app.controller("bookEasySearch",function($scope, $http,$window){
	$scope.searchBookInfo = function() {
		var condition1 = $scope.condition1;
		var data = {
			"condition1" : $scope.condition1,
			"condition2" : $scope.condition3,
			"type" : $scope.condition2,
			"startPage" : $scope.startPage,
		};
		$http({
			method : "post",
			params : data,
			url : "/Library/easySearch.do",
			dataType : "json"
		}).success(function(data) {
			$scope.desc = 0;
			$scope.items = data.result.result;
			if (data.result.totalPage == 0) {
				$scope.totalPage = 1;
			} else {
				$scope.totalPage = data.result.totalPage;
			}
			console.log(data);
			console.log($scope.startPage);
		}).error(function(data) {
			$scope.message = "数据查询失败,请稍后重试";
			$("#alert").modal('show');
		});
	}
	
	
	//分页
	$scope.changePage = function(type) {
		if (type == "up") {
			if ($scope.startPage > 1) {
				$scope.startPage -= 1;
				$scope.searchBookInfo();
			}
		} else if (type == "down") {
			if ($scope.startPage < $scope.totalPage) {
				$scope.startPage += 1;
				$scope.searchBookInfo();
			}
		} else if (type = "userDefined") {
			if ($scope.defined < 1 || $scope.defined > $scope.totalPage
					|| $scope.defined == $scope.startPage || $scope.defined==null) {
				
				return;
			} else {
				$scope.startPage = $scope.defined;
				$scope.searchBookInfo();
			}
		}
	}
	
	
});