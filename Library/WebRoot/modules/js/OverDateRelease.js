/**
 *超期欠款 
 */
app.controller("OverDateRelease",function($scope,$http,$window){
	$scope.startPage=1;
	$scope.loadMsg=function(){
		var root=$window.sessionStorage.getItem("root");
		var loadId=$window.sessionStorage.getItem("loadId");
		var data={};
		//如果是普通用户，之查看自己的记录
		if(root==2){
			data={"loadId":loadId,"type":1,"startPage":$scope.startPage}
		} else {
			data={"type":1,"startPage":$scope.startPage}
		}
		$http({
			method:"post",
			url:"/Library/findAllIllegal.do",
			params:data
		}).success(function(data){
			    
				$scope.totalPage=((data.result.totalPage==0 || typeof(data.result.totalPage)=='undefined')? 1 : data.result.totalPage);
			
			
			$scope.items=data.result.result;
			$scope.desc=0;
		});
	}
	$scope.loadMsg();
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
					|| $scope.defined == $scope.startPage || $scope.defined==null) {
				return;
			} else {
				$scope.startPage = $scope.defined;
				$scope.loadMsg();
			}
		}
	}
});
