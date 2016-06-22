app.controller("classify",function($scope,$http){
	$scope.startPage=1;
	//查找出第一级菜单
	$http({
		method:"post",
		url:"/Library/findTreeView.do"
	}).success(function(data){
		//第一级菜单
		$scope.firstClasses=data.result.firstClasses;
		//查找出所有的二级菜单
		$scope.secondClasses=data.result.secondClasses;
		//查找出所有三级菜单
		$scope.thirdClasses=data.result.thirdClasses;
	});
/**
 * 初始化展示第一个记录
 */

 $scope.searchBookInfoClass=function(classId){
	 $http({
		 method:"post",
	 	 url:"/Library/SortAndNewBook.do",
	 	 params:{"classId":classId,"startPage":$scope.startPage}
	 }).success(function(data){
		 $scope.totalPage=data.result.totalPage;
		 $scope.items=data.result.result;
	 });
 }
 $scope.searchBookInfoClass("0");
  $scope.selectBySort=function(firstClassId,secondClassId,ThridClassId){
	  var classId=firstClassId;
	 if(typeof(secondClassId)!="undefined"){
		 secondClassId="|"+secondClassId;
		 classId+=secondClassId;
	 }
	 if(typeof(ThridClassId)!="undefined"){
		 ThridClassId="|"+ThridClassId;
		 classId+=ThridClassId;
	 }
	$scope.bookClassId=classId;
	console.log("classId  "+classId);
	$scope.searchBookInfoClass(classId);
	
  }
  
  /**
   * 分页
   */
  $scope.changePage=function(type){
	  if(typeof($scope.bookClassId)=="undefined"){
		  $scope.bookClassId=0;
	  }
	  if (type == "up") {
			if ($scope.startPage > 1) {
				$scope.startPage -= 1;
				$scope.searchBookInfoClass($scope.bookClassId);
			}
		} else if (type == "down") {
			if ($scope.startPage < $scope.totalPage) {
				$scope.startPage += 1;
				$scope.searchBookInfoClass($scope.bookClassId);
			}
		} else if (type = "userDefined") {
			if ($scope.defined < 1 || $scope.defined > $scope.totalPage
					|| $scope.defined == $scope.startPage || $scope.defined==null) {
				return;
			} else {
				$scope.startPage = $scope.defined;
				$scope.searchBookInfoClass($scope.bookClassId);
			}
		}
  }
});