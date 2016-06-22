app.controller("personalBorrow", function($scope, $http, $window, $rootScope) {
	// 初始化查看当前的借阅信息
	$http({
		method : "post",
		url : "/Library/findBorrow.do",
		params : {
			"loadId" : $window.sessionStorage.getItem("loadId"),
			"operate" : 1
		}
	}).success(function(data) {
		console.log($window.sessionStorage.getItem("loadId"));
		if (data.flag) {

			if (data.result.length == 0) {
				$scope.isNull = "您当前借阅记录为空";
			} else {
				$scope.isValue = true;
				$scope.Items = data.result;
				$scope.desc = 0;
			}

		}
	}).error(function() {
		$rootScope.message = "系统繁忙,请稍候重试";
		$("#alert").modal("show");
	});

	// 续借操作
	$scope.sureReOrder = function(item, index) {
		// 判断是否违规
		$http({
			method : "post",
			url : "/Library/checkIsIlle.do",
			params : {
				"endtime" : item.endTime
			}
		}).success(function(data) {
			if (!data.flag) {
				$rootScope.message = data.message;
				$("#alert").modal("show");
				return false;
			}
		}).error(function() {
			return false;
		});

		// 如果没有违规，此时可以续借
		$http({
			method : "post",
			url : "/Library/sureReOrder.do",
			params : {
				"bookId" : item.bookId,
				"loadId" : item.loadId,
				"id" : item.id,
				"date" : item.endTime
			}
		}).success(function(data) {
			window.location.reload();
		}).error(function() {
			$rootScope.message = "系统繁忙,请稍候重试";
			$("#alert").modal("show");
		});
	}
});

app.controller("personalHistory", function($scope, $http, $window) {
	$http({
		method : "post",
		url : "/Library/findBorrow.do",
		params : {
			"loadId" : $window.sessionStorage.getItem("loadId"),
			"operate" : 2
		}
	}).success(function(data) {
		console.log($window.sessionStorage.getItem("loadId"));
		if (data.flag) {

			if (data.result.length == 0) {
				$scope.isNull = "借阅历史为空";
			} else {
				$scope.isValue = true;
				$scope.Items = data.result;
				$scope.desc = 0;
			}

		}
	}).error(function() {
		$rootScope.message = "系统繁忙,请稍候重试";
		$("#alert").modal("show");
	});
});

app.controller("personOrder", function($scope, $http, $window) {
	$http({
		method : "post",
		url : "/Library/findBorrow.do",
		params : {
			"loadId" : $window.sessionStorage.getItem("loadId"),
			"operate" : 0
		}
	}).success(function(data) {
		console.log($window.sessionStorage.getItem("loadId"));
		if (data.flag) {

			if (data.result.length == 0) {
				$scope.isNull = "预约信息为空";
			} else {
				$scope.isValue = true;
				$scope.Items = data.result;
				$scope.desc = 0;
			}

		}
	}).error(function() {
		$rootScope.message = "系统繁忙,请稍候重试";
		$("#alert").modal("show");
	});

	/**
	 * 取消预约操作
	 */
	$scope.cancleOrder = function(item, index) {
		$http({
			method : "post",
			url : "/Library/cancleOrder.do",
			params : {
				"loadId" : item.loadId,
				"bookId" : item.bookId,
				"id" : item.id
			}
		}).success(function(data) {
			if (data.flag) {
				$scope.Items.splice(index, 1);
			}
		}).error(function() {
			$rootScope.message = "系统繁忙，请稍候重试";
			$("#alert").modal("show");
		});
	}
});

app.controller("bookmiss", function($scope, $http, $sce, $window, $rootScope) {

	$scope.operateMiss = function() {
		$scope.showHistory = true;
		$http({
			method : "post",
			url : "/Library/findBorrow.do",
			params : {
				"loadId" : $window.sessionStorage.getItem("loadId"),
				"operate" : 3
			}
		}).success(function(data) {
			console.log(data);
			if (data.flag) {

				if (data.result.length == 0) {
					$scope.isNull = "书籍挂失为空";
				} else {
					$scope.isValue = true;
					$scope.Items = data.result;
					$scope.desc = 0;
				}

			}
		}).error(function() {
			$rootScope.message = "系统繁忙,请稍候重试";
			$("#alert").modal("show");
		});

	}
	// 书籍挂失
	// 修改borrowRecord
	$scope.sureAndMiss = function(type) {
		$http({
			method : "post",
			url : "/Library/sureMiss.do",
			params : {
				"loadId" : $scope.loadId,
				"bookId" : $scope.bookId,
				"type" : type
			}
		}).success(
				function(data) {
					if (type == 1) {
						if (data.flag) {
							var message = "图书信息<br/>书名: "
									+ data.result.bookName + "<br/>索引号: "
									+ data.result.bookId + "<br/>作者: "
									+ data.result.autor + "<br/>单价:"
									+ data.result.price;
							$scope.showMissMsg = $sce.trustAsHtml(message);
						} else {
							$scope.showMissMsg = $sce
									.trustAsHtml("您未借阅此书，无法挂失");
						}
					} else {
						if (data.flag) {
							$rootScope.message = "挂失成功";
							$("#alert").modal("show");
							window.location.reload();
						} else {
							$rootScope.message = "挂失失败,请稍候重试";
							$("#alert").modal("show");
						}
					}
				}).error(function() {
			if (type == 1) {
				$scope.showMissMsg = $sce.trustAsHtml("系统繁忙，暂未查到此书的借阅信息");
			} else {
				$rootScope.message = "系统繁忙，请稍候重试";
				$("#alert").modal("show");
			}
		});
	}

	// 初始化显示挂失历史
	$scope.operateMiss();
});

/**
 * 读者挂失
 */
app.controller("readermiss", function($scope, $http, $window) {
	$scope.sureReaderMiss = function() {
		$http({
			method : "post",
			url : "/Library/updateIsAvail.do",
			params : {
				"loadId" : $scope.loadId,
				"isAvail" : 1
			}
		}).success(function(data) {
			if (data.flag) {
				$scope.readerMessage = "挂失成功";
			} else {
				$scope.readerMessage = "没有此用户,确保借书证输入正确";
			}
		}).error(function() {
			$rootScope.message = "系统繁忙,请稍候重试";
		});
	}

});

/**
 * 违章记录
 */
	app.controller("illegal",function($scope,$http,$window,$rootScope){
		$scope.startPage=1;
		$scope.loadMsg=function(){
			$http({
				method:"post",
				url:"/Library/findAllIllegal.do",
				params:{"loadId":$window.sessionStorage.getItem("loadId"),"startPage":$scope.startPage,"type":1,"role":1}
			}).success(function(data){
				console.log(data);
				if(data.flag){
					if(data.result.result.length==0){
						
						$scope.isNull="违规记录为空";
						$scope.isValue=false;
					} else {
						$scope.isValue=true;
						$scope.Items=data.result.result;
						$scope.desc=0;
					}
				}
			}).error(function(){
				$scope.isNull="系统繁忙,请稍候重试";
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
	
	/**
	 * 收藏记录
	 */
	app.controller("saveBook",function($scope,$http,$window,$rootScope){
		/**
		 * 初始化查找当前用户所有的收藏信息
		 */
		$http({
			method:"post",
			url:"/Library/OperateSaveBook.do",
			params:{"type":0,"loadId":$window.sessionStorage.getItem("loadId")}
		}).success(function(data){
			if(data.flag){
				if(data.result.length==0){
					$scope.isNull="收藏记录为空";
				} else {
					$scope.isValue=true;
					$scope.Items=data.result;
					$scope.desc=0;
				}
				
			}
		}).error(function(){});
		
		/**
		 * 取消收藏记录
		 */
		$scope.cancleSave=function(item,index){
			$http({
				method:"post",
				url:"/Library/OperateSaveBook.do",
				params:{"bookId":item.bookId,"loadId":$window.sessionStorage.getItem("loadId"),"type":1}
			}).success(function(data){
				if(data.flag){
					$scope.Items.splice(index, 1);
					if($scope.Items.length==0){
						$scope.isValue=false;
					}
				} else {
					$rootScope.message="取消收藏失败，请稍候重试";
					$("#alert").modal("show");
				}
			}).error(function(){
				$rootScope.message="取消收藏失败，请稍候重试";
				$("#alert").modal("show");
			});
		}
	});
