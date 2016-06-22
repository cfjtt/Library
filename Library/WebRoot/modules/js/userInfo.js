app.controller("userCtrl",function($scope,$http, $window,$rootScope){
	//初始化让添加用户显示
	$scope.addUser=true;
	$scope.isModifyUserShow=false;
	if($window.sessionStorage.getItem("root")==0){
		$scope.isSuperRoot=true;
	}
	/**
	 * 判断借书证号是否存在
	 */
	$scope.checkIsExit=function(){
		var loadId=$scope.loadId;
		$http({
			method:"post",
			url:"/Library/checkIsExit.do",
			params:{"loadId":loadId},
			dataType:"json"
		}).success(function(data){
			if(!data.flag){
				$scope.message=data.message;
				$("#alert").modal("show");
			} 
		});
	}
	/**
	 * 添加用户操作
	 */
	$scope.addUserInfo=function(){
		console.log();
		
		if($scope.form.$valid){
			var loadId=$scope.loadId;
			var realName=$scope.realName;
			var phone=$scope.phone;
			var root=2;
			//如果是超级管理员的，这时用户权限去下拉框的值，否则用户权限为普通用户
			if($window.sessionStorage.getItem("root")==0){
				 root=$scope.selectRoot;
				 console.log($scope.selectRoot);
				 console.log("root1"+root);
			} else {
				 root=2;
				 console.log("root2"+root);
			}
			if($("#addUserLi").attr("class")=="active"){
				var data={"loadId":loadId,"realName":realName,"phone":phone,"root":root};
			} else {
				console.log("root3"+root);
				var data={"loadId":loadId,"realName":realName,"phone":phone,"root":root,"isAvail":$scope.isAvail,"id":$scope.id};
			}
			
			$http({
				method:"post",
				url:($("#addUserLi").attr("class")=="active") ? "/Library/addUser.do" : "/Library/modifyUserInfo.do",
				params:data,
				dataType:"json" 
			}).success(function(data){
				if(data.flag){
					$scope.message=($("#addUserLi").attr("class")=="active") ?"添加成功" : "修改成功";
					$("#alert").modal('show');
				} else {
					$scope.message=data.message;
					$("#alert").modal('show');
				}
			}).error(function(){
				$scope.message="添加失败，请稍候重试";
				$("#alert").modal('show');
			});
			
		} else {
			$scope.message="确定填写是否正确";
			$("#alert").modal('show');
		}
	}
	
	
	/**
	 * 查找全部用户
	 */
	$scope.searchAllUserLimit=function(type){
		var data={};
		
		if(type=='All'){
			data={"startPage":$scope.startPage,"condition":"","root":'AllUser'};
		} else if(type=="condition"){
			
			data={"startPage":$scope.startPage,"condition":$scope.condition,"value":$scope.searchContent,"root":$scope.superRoot}
		}
		$http({
			method:"post",
			url:"/Library/findAllUser.do",
			params:data,
			dataType:"json"
		}).success(function(data){
			if(data.flag){
				$scope.items=data.result.result;
				$scope.desc=0;
				$scope.totalPage=data.result.totalPage;
			}
		}).error(function(){
			$scope.message="系统繁忙,请稍候重试";
			$("#alert").modal("show");
		});
	}
	$scope.searchAllUser=function(){
		$scope.isClick=true;
		$scope.searchByCondition=false;
		$scope.searchType="All";
		/**
		 * 查找全部用户
		 */
		$scope.searchAllUserLimit("All");
	}
	
	
	/**
	 * 删除用户
	 */
	$scope.deleteUserInfo=function(item,index){
		
		$http({
			method : "post",
			url : "/Library/deleteUserInfo.do",
			params : {
				"id" : item.id,
			}
		}).success(function(data) {
			
			$scope.items.splice(index, 1);

		}).error(function() {
			$scope.message = "数据删除失败,请稍后重试";
			$("#alert").modal('show');
		});
		
	}
	$scope.changePage = function(type,searchType) {
		if (type == "up") {
			if ($scope.startPage > 1) {
				$scope.startPage -= 1;
				$scope.searchAllUserLimit(searchType);
			}
		} else if (type == "down") {
			if ($scope.startPage < $scope.totalPage) {
				$scope.startPage += 1;
				$scope.searchAllUserLimit(searchType);
			}
		} else if (type = "userDefined") {
			if ($scope.defined < 1 || $scope.defined > $scope.totalPage
					|| $scope.defined == $scope.startPage || $scope.defined==null) {
				return;
			} else {
				$scope.startPage = $scope.defined;
				$scope.searchAllUserLimit(searchType);
			}
		}
	}
	
	/**
	 * 查找指定用户(主要功能是让查询条件的组件显示)
	 */
	$scope.searchSomeUser=function(){
		$scope.searchByCondition=true;
		$scope.isClick=false;
		$scope.searchType="condition";
	}
	
	
	/**
	 * 查找指定用户
	 */
	$scope.modifyUser=function(){
		$scope.startPage=1;
		$scope.totalPage=1;
		$scope.searchAllUserLimit("condition");
		$scope.isClick=true;
	}
	
	/**
	 * 点击表格上方的修改
	 */
	$scope.modifyUserInfo=function(item,index){
		
		$scope.addUser=true;
		$scope.isClick=false;
		$scope.searchByCondition=false;
		$scope.isModifyUserShow=false;
		$scope.isAvailShow=true;
		$scope.id=item.id;
		$scope.loadId=item.loadId;
		$scope.realName=item.realName;
		$scope.phone=item.phone;
		if($scope.isSuperRoot){
			$scope.selectRoot=item.root+"";
			
		}
		$scope.isAvail=item.isAvail+"";
		
	}
});