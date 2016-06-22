app.controller("personal", function($scope, $http, $window) {
	$(".file-drop-zone-title").html("上传头像..");

	// 初始化加载个人信息
	$http({
		method : "post",
		url : "/Library/findUserByLoadId.do",
		params : {
			"loadId" : $window.sessionStorage.getItem("loadId")
		}
	}).success(function(data) {
		if (data.flag) {
			$scope.loadId = data.result.loadId;
			$scope.username = data.result.userName;
			$scope.realName = data.result.realName;
			$scope.phone = data.result.phone;
			$scope.idCard = data.result.idCard;
			$scope.email = data.result.email;

		}
	});

	$(".fileinput-remove").click(function() {
		$scope.$apply(function() {
			$scope.editPage = false;
		})

	});
	$scope.showPage = function(type) {
		$scope.editPage = true;
		$scope.modifyPWd = type;
	}

	$scope.update = function(modifyPWd) {
		var data = {};
		if (modifyPWd) {
			if($scope.isTrue){
				$scope.message = "原密码错误";
				$("#alert").modal("show");
			} else if ($scope.newPwd != $scope.surePwd) {
				$scope.message = "两次密码不一致";
				$("#alert").modal("show");
			} else {
				data = {
					"password" : $scope.surePwd,
					"loadId":$window.sessionStorage.getItem("loadId")
				};
			}
		} else {
			data = {
				"loadId" : $window.sessionStorage.getItem("loadId"),
				"username" : $scope.modifyName,
				"phone" : $scope.modifyPhone,
				"idCard" : $scope.modelId,
				"email" : $scope.modifyEmail
			}
		}
		$http({
			method:"post",
			url:"/Library/ModifyUserMsg.do",
			params:data
		}).success(function(data){
			if(data.flag){
				$scope.editPage = true;
				window.location.reload();
			} else {
				$scope.message="修改失败";
				$("#alert").modal("show");
			}
		}).error(function(){
			$scope.message="系统繁忙，请稍候重试";
			$("#alert").modal("show");
		});
	}
	$scope.sureModify = function(modifyPWd) {
		$scope.update(modifyPWd);
	}
	$scope.checkPwd = function() {
		// 查找原密码是否正确
		var password=$scope.modifyPwd;
		$http({
			method:"post",
			url:"/Library/checkPwd.do",
			params:{"password":password,"loadId":$window.sessionStorage.getItem("loadId")}
		}).success(function(data){
			if(!data.flag){
				$scope.isTrue=true;
			} else {
				$scope.isTrue=false;
			}
		});
	}
});
