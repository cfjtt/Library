app.controller("OrderRelease",function($scope,$http,$window){
	$scope.startPage=1;
	$scope.loadMsg=function() {
		var root=$window.sessionStorage.getItem("root");
		var loadId=$window.sessionStorage.getItem("loadId");
		var data={};
		if(root==2){
			data={"loadId":loadId,"startPage":$scope.startPage};
		} else {
			data={"startPage":$scope.startPage};
		}
		$http({
			method:"post",
			url:"/Library/findOrderIsAvail.do",
			params:data
		}).success(function(data){
			$scope.totalPage=(data.result.totalPage==0 ? 1 : data.result.totalPage);
			$scope.items=data.result.result;
			$scope.desc=0;
		});
	}
	$scope.loadMsg();
	
	
	//分页
	$scope.changePage = function(type) {
		if (type == "up") {
			if ($scope.startPage > 1) {
				$scope.startPage -= 1;
				$scope.loadMsg();
			}
		} else if (type == "down") {
			if ($scope.startPage < $scope.totalPage) {
				$scope.startPage += 1;
				$scope.loadMsg();
			}
		} else if (type = "userDefined") {
			if ($scope.defined < 1 || $scope.defined > $scope.totalPage
					|| $scope.defined == $scope.startPage) {
				return;
			} else {
				$scope.startPage = $scope.defined;
				$scope.loadMsg();
			}
		}
	}
});