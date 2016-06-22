var app = angular.module("myApp", ["ui.router"]);
app.controller("indexCtrl", function($scope, $http, $window) {

			if ($window.sessionStorage.getItem("name") != null) {
				$scope.show = true;
				$scope.name = $window.sessionStorage.getItem("name");
				if($window.sessionStorage.getItem("root")==0){
					$scope.admin=true;
					$scope.superAdmin=true;
				} else if($window.sessionStorage.getItem("root")==1){
					$scope.admin=true;
					$scope.superAdmin=false;
				} else {
					$scope.admin=false;
					$scope.superAdmin=false;
				}
				
			} else {
				$scope.show = false;
			}

			$scope.login = function() {
				$scope.operation = "登录";
				$scope.showregist = false;
				$scope.showlogin = true;
			}
			$scope.regist = function() {
				$scope.operation = "注册";
				$scope.showlogin = false;
				$scope.showregist = true;
			}

			/**登录注册操作*/
			$scope.sure = function() {
				var data = {};
				
				var againpassword = $scope.userinfo.againpassword;
				if ($scope.operation == "登录") {
					data = {
						"loadId" : $scope.userinfo.loadId,
						"password" : $scope.userinfo.password
					};
				} else if ($scope.operation == "注册") {
					data = {
						"loadId" : $scope.userinfo.load,
						"password" : $scope.userinfo.pwd,
						"userName" : $scope.userinfo.username,
						"realName" : $scope.userinfo.realname,
						"idCard" : $scope.userinfo.IdCard,
						"phone" : $scope.userinfo.telphone,
						"againpassword" : $scope.userinfo.againpassword
					};
				}

				$http({
						method : "post",
						url : "/Library/"
								+ (($scope.operation == "登录") ? "login.do"
										: "regist.do"),
						params : data,
						dataType : "json"
					}).success(
					function(data) {
						if (data.flag) {
							$("#myModal").modal("hide");
							$window.sessionStorage.setItem("name",
									data.result.userName);
							$window.sessionStorage.setItem("root",
									data.result.root);
							$window.sessionStorage.setItem("id",data.result.id);
							$window.sessionStorage.setItem("loadId",$scope.userinfo.loadId);
								/**
								 0  超级管理员   系统管理，管理员管理
								 1  管理员    系统管理
								 2 普通用户
								*/
								
								$scope.show = true;
								$scope.name = data.result.userName;
								if(data.result.root==0){
									$scope.admin=true;
									$scope.superAdmin=true;
								} else if(data.result.root==1){
									$scope.admin=true;
									$scope.superAdmin=false;
								} else if(data.result.root==2){
									$scope.admin=false;
									$scope.superAdmin=false;
									$scope.isLogin=true;
								}

							} else {
								$scope.loginMessage=data.message;
								$("#loginAlert").modal("show");
							}
						}).error(function() {
							$scope.loginMessage="系统繁忙,请稍后重试"
							$("#loginAlert").modal('show');
				});
			}

			/**
			 * 判断借书证号是否存在
			 */
			$scope.checkIsExit=function(){
				var loadId=$scope.userinfo.load;
				$http({
					method:"post",
					url:"/Library/checkIsExit.do",
					params:{"loadId":loadId},
					dataType:"json"
				}).success(function(data){
					if(!data.flag){
						$scope.loginMessage=data.message;
						$("#loginAlert").modal("show");
					} 
				});
			}
			/**退出操作*/
			$scope.exit = function() {
				$window.sessionStorage.clear();
				$scope.show = false;
				window.location.reload();
			}
		});