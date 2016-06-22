app.controller("borrowInfo", function($scope, $http, $sce, $rootScope) {
	/**
	 * 初始化的时候，让读者借阅模块显示
	 */
	$scope.borrowBook = true;

	/**
	 * 根据借书证查找用户相关信息,并展示
	 */
	$scope.searchUser = function() {
		$http({
			method : "post",
			url : "/Library/findUserByLoadId.do",
			params : {
				"loadId" : $scope.loadId
			},
			dataType : "json"
		}).success(
				function(data) {
					if (data.flag) {
						$scope.showUserMessage = true;
						var message = "个人信息<br/>借书证:" + data.result.loadId
								+ "<br/>姓名:" + data.result.realName
								+ "<br/>电话:" + data.result.phone
						$scope.searchMessage = $sce.trustAsHtml(message);
					}
				}).error(function() {
			$rootScope.message = "系统繁忙，请稍候重试";
			$("#alert").modal("show");
		});
	}

	/**
	 * 根据图书的索引号查找图书的是否可以借阅
	 */
	$scope.searchBook = function() {
		$http({
			method : "post",
			url : "/Library/findBookUserInfo.do",
			params : {
				"bookId" : $scope.bookId,
				"operate" : 0,
				"loadId" : $scope.loadId
			}
		}).success(
				function(data) {
					if (data.flag) {
						console.log(data);
						$scope.showBookMessage = true;
						// 查找出的书籍的id号
						$scope.id = data.result.result.id;
						var message = "图书信息<br/>图书名:"
								+ data.result.result.bookName + "<br/>索引号:"
								+ data.result.result.bookId + "<br/>可用数量:"
								+ data.result.result.availNum
								+ "<br/>当前书籍预约人数:" + data.result.order
								+ "<br/>当前用户是否预约:";
						if (data.result.isOrder) {
							message += "未预约";
						} else {
							message += "已预约";
						}
						$scope.showMessage = $sce.trustAsHtml(message);
					} else {
						$rootScope.message = data.message;
						$("#alert").modal("show");
					}
				}).error(function() {
			$rootScope.message = "系统繁忙,请稍候重试";
			$("#alert").modal("show");
		});
	}

	/**
	 * 确定借阅
	 */
	$scope.sureBorrow = function() {
		var loadId = $scope.loadId;
		var bookId = $scope.bookId;
		var days = $scope.days;
		var id = $scope.id;
		// 添加borrowrecord表,更新bookinfo表中的borrowtimes字段
		$http({
			method : "post",
			url : "/Library/modifyBorrowAndBookInfo.do",
			params : {
				"loadId" : loadId,
				"bookId" : bookId,
				"days" : days,
				"id" : id
			}
		}).success(function(data) {
			if (data.flag) {
				$rootScope.message = "借阅成功";
				$("#alert").modal("show");
			} else {
				$rootScope.message = data.message;
				$("#alert").modal("show");
			}
		}).error(function() {
			$rootScope.message = "系统繁忙,请稍后重试";
			$("#alert").modal("show");
		});
	}
});

/**
 * 读者还书模块
 */
app.controller("return", function($scope, $http, $sce, $rootScope) {
	$scope.sureReturn = function() {
		$http({
			method : "post",
			url : "/Library/findUserIsIlleg.do",
			params : {
				"loadId" : $scope.loadId,
				"bookId" : $scope.bookId,
				"operate" : 1
			}
		}).success(function(data) {
			$scope.showMessage = true;
			$scope.flag = data.flag;
			$scope.flagmessage = data.message;
			$scope.showReturnMessage = $sce.trustAsHtml(data.message);
		}).error(function() {
			$rootScope.message = "系统繁忙,请稍后重试";
			$("#alert").modal("show");
		});
	}
});

/**
 * 赔偿处理
 */
app.controller("pay", function($scope, $http, $rootScope) {
	$scope.paySure = function() {
		$http({
			method : "post",
			url : "/Library/findAllIlleg.do",
			params : {
				"bookId" : ($scope.bookId == undefined) ? null : $scope.bookId,
				"loadId" : $scope.loadId
			}
		}).success(function(data) {
			console.log(data);
			if (data.flag) {
				$scope.items = data.result;
				$scope.showTable = true;
				$scope.desc = 0;
			}
		}).error(function() {
			$rootScope.message = "系统繁忙,请稍候重试";
			$("#alert").modal("show");
		});
	}

	$scope.surePay = function(item, index) {
		// 确认赔偿,修改borrowRecord中的operate字段为2
		$http({
			method : "post",
			url : "/Library/modifyOperate.do",
			params : {
				"bookId" : item.bookId,
				"loadId" : item.loadId,
				"operate" : item.operate,
				"id" : item.id,
				"changeOperate" : 2
			}
		}).success(function(data) {
			if (data.flag) {
				$rootScope.message = "修改成功";
				$("#alert").modal("show");
				$scope.items.splice(index, 1);
			} else {
				$rootScope.message = data.message;
				$("#alert").modal("show");
			}
		}).error(function() {
			$rootScope.message = "系统繁忙,请稍候重试";
			$("#alert").modal("show");
		});
	}
});

/**
 * 书籍挂失
 */
app.controller("sureMiss", function($scope, $http, $rootScope, $sce) {
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
							$scope.showMissMsg=$sce.trustAsHtml(message);
						} else {
							$scope.showMissMsg=$sce.trustAsHtml("您未借阅此书");
						}
					} else {
						if(data.flag){
							$rootScope.message="挂失成功";
							$("#alert").modal("show");
							window.location.reload();
						} else {
							$rootScope.message="挂失失败,请稍候重试";
							$("#alert").modal("show");
						}
					}
				}).error(function() {
					if(type==1){
						$scope.showMissMsg=$sce.trustAsHtml("系统繁忙，暂未查到此书的借阅信息");
					} else {
						$rootScope.message="系统繁忙，请稍候重试";
						$("#alert").modal("show");
					}
				});
	}

});
