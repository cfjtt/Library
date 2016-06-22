app.controller("bookInfoCtrl", function($scope, $http) {
	// 初始化的时候，让添加书籍显示,并
	$scope.addBook = true;
	$scope.modifyBook = false;
	$scope.deleteBook = false;
	$scope.release = false;
	//初始化编辑器
	var um = UM.getEditor('myEditor');
	um.setContent('编辑图书简介(可以上传图书的图片)', false);
	  $scope.$on('$destroy', function() {
	        um.destroy();
	    });
	
	/**
	 * 初始化图书位置，和第一层级的图书类别,三级菜单显示
	 */
	$scope.config = function() {
		$http({
			method : "post",
			url : '/Library/getPlaceAndBookClass.do',
			params : {
				"action" : 0,
			},
			dataType : 'json'
		}).success(function(data) {
			var places = data.places;
			$scope.placees = places;
			var bookClasses = data.bookClasses;
			$scope.bookClasses = bookClasses;
			var bookClassInfo = data.bookClassInfo;
			$scope.bookClassInfo = bookClassInfo;
		});
	}
	$scope.config();
	/**
	 * 下拉框改变时，触发
	 */
	$scope.change = function(action) {
		var data = {};
		if (action == "first") {
			data = {
				"bookClassId" : $scope.selectBookClass.bookClassId,
				"action" : 1
			}
		} else if (action == "second") {
			data = {
				"bookClassId" : $scope.selectBookClass.bookClassId,
				"secondClassId" : $scope.selectSecondBookClass.secondClassId,
				"action" : 2
			}
		}

		$http({
			method : "post",
			url : '/Library/getPlaceAndBookClass.do',
			params : data,
			dataType : 'json',

		}).success(function(data) {
			
			if (action == "first") {
				var secondBookClasses = data.secondBookClasses;
				$scope.secondBookClasses = secondBookClasses;
			} else if (action == "second") {
				var thirdBookClasses = data.thirdBookClasses;
				$scope.thirdBookClasses = thirdBookClasses;
			}
			console.log($scope.selectSecondBookClass);
		});

	}


	// placees bookClasses selectSecondBookClass thirdBookClasses

	/**
	 * 点击确定按钮时的动作
	 */
	$scope.addBookInfo = function() {

		if ($scope.form.$valid) {
			// 获得图书简介内容(包括html)
			var descript = um.getContent();
			var bookName = $scope.bookName;
			var bookId = $scope.bookId;
			var autor = $scope.autor;
			var publishHouse = $scope.publishHouse;
			var publishTime = $scope.publishTime.getFullYear() + "/"
					+ ($scope.publishTime.getMonth() + 1);
			var place = $scope.selectPlace.id;
			var firstBookClassId = $scope.selectBookClass.bookClassId;
			var secondBookClassId = $scope.selectSecondBookClass.secondClassId;
			var thirdBookClassId = $scope.selectThirdBookClass.thirdClassId;
			var selectBookType = $scope.selectBookType.id;
			var price = $scope.price;
			var totalNum = $scope.totalNum;
			var availNum=$scope.availNum;
			var id=$scope.primaryId;
			
			var data = {
				"bookName" : bookName,
				"bookId" : bookId,
				"autor" : autor,
				"publishHouse" : publishHouse,
				"publishTime" : publishTime,
				"place" : place,
				"bookClassId" : firstBookClassId + "|" + secondBookClassId
						+ "|" + thirdBookClassId,
				"bookTypeId" : selectBookType,
				"price" : price,
				"totalNum" : totalNum,
				"availNum":availNum,
				"description" : descript,
				"id":id
			};
			console.log(data);
			$http({
				method : "post",
				url : $("#addBook").parent().attr("class")=="active" ? "/Library/addBook.do" : "/Library/modifyBookInfo.do", 
				params : data
			}).success(function(data) {
				if (data.flag) {
					// 弹出提示框
					$scope.message =($("#addBook").parent().attr("class")=="active" ? "添加成功" : "修改成功");
					$("#alert").modal('show');
				}
			}).error(function() {
				$scope.message = "添加失败，请稍后重试";
				$("#alert").modal('show');
			});

		} else {
			$scope.message = "请确保必填项都已填写";
			$("#alert").modal('show');
		}
	}

	/**
	 * 初始化查询所有符合要求的图书的总量
	 */
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
	/**
	 * 修改图书信息的查询操作
	 */
	$scope.modifySearch = function() {
		$scope.searchBookInfo();

	}

	/**
	 * 删除图书
	 */
	$scope.deleteBookInfo = function(item, index) {
		$http({
			method : "post",
			url : "/Library/deleteBookInfo.do",
			params : {
				"id" : item.id,
				"bookId" : item.bookId,
				"bookName" : item.bookName
			}
		}).success(function(data) {

			$scope.items.splice(index, 1);

		}).error(function() {
			$scope.message = "数据删除失败,请稍后重试";
			$("#alert").modal('show');
		});

	}

	/**
	 * 上一页，下一页按钮,用户自定义
	 */
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
	/**
	 * 查找菜单名字
	 */
	$scope.findBookClassName = function(firstClassId, secondClassId,
			thirdClassId) {

		$http({
			method : "post",
			url : "/Library/findBookClassAllName.do",
			params : {
				"firstClassId" : firstClassId,
				"secondClassId" : secondClassId,
				"thirdClassId" : thirdClassId
			}
		}).success(function(data) {
				// 遍历$scope.secondBookClasses(二级菜单)
				$scope.selectSecondBookClass = {
					"secondClassId" : secondClassId,
					"secondClassName" : data.result.secondClassName
				};
				$scope.secondBookClasses=[$scope.selectSecondBookClass];
				$scope.selectThirdBookClass = {
					"thirdClassId" : thirdClassId,
					"thirdClassName" : data.result.thirdClassName
				};
				$scope.thirdBookClasses=[$scope.selectThirdBookClass];
			}).error(function() {
			$scope.message = "查询失败,请稍后重试";
			$("#alert").modal('show');
		});
	}
	/**
	 * 修改图书
	 */
	$scope.modifyBookInfo = function(item, index) {
		// 初始化的时候，展示菜单
		// 获取当前图书的bookClassId,place,bookTypeId
		var bookClassId = item.bookClassId.split("|");
		um.setContent(item.description, false);
		$scope.bookName = item.bookName;
		$scope.bookId = item.bookId;
		$scope.autor = item.autor;
		$scope.availNum=item.availNum;
		$scope.publishHouse = item.publishHouse;
		var publishTime = item.publishTime.split("/");
		$scope.publishTime = new Date(publishTime[0], publishTime[1] - 1, 1);
		// 遍历$scope.placees
		for ( var key in $scope.placees) {
			if ($scope.placees[key].id == item.place) {
				$scope.selectPlace = $scope.placees[key];
			}
		}
		//遍历一级菜单
		for(var key in $scope.bookClasses){
			if($scope.bookClasses[key].bookClassId==bookClassId[0]){
				$scope.selectBookClass=$scope.bookClasses[key];
			}
		}
		$scope.findBookClassName(bookClassId[0], bookClassId[1],
						bookClassId[2]);
		// 遍历$scope.bookClassInfo
		for ( var key in $scope.bookClassInfo) {
			if ($scope.bookClassInfo[key].id == item.bookTypeId) {
				$scope.selectBookType = $scope.bookClassInfo[key];
			}
		}
		$scope.isModify=true;
		$scope.price = item.price;
		$scope.totalNum = item.totalNum;
		$scope.addBook = true;
		$scope.modifyBook = false;
		$scope.primaryId=item.id;
	}
});


app.controller("releaseCtr",function($scope,$http,$rootScope){
	$scope.search=function(isAll){
		var data;
		if(isAll){
			data={"isAll":0,"startPage":$scope.startPage};
		} else {
			var data={
				"condition1":$scope.condition1,
				"condition2":$scope.condition2,
				"condition3":$scope.condition3,
				"startPage":$scope.startPage,
				"isAll":1
			}
		}
		
		$http({
			method:"post",
			url:"/Library/searchBookPlace.do",
			params:data
		}).success(function(data){
			console.log(data);
			$scope.releaseItems=data.result.result;
			$scope.totalPage=data.result.totalPage;
			$scope.desc=0;
		}).error(function(){
			$rootScope.message="系统繁忙,请稍候重试";
			$("#alert").modal("show");
		});
	}
	$scope.releaseSearch=function(){
		$scope.search(false);
	}
	
	$scope.changePage=function(type){
		var isAll;
		if($("#releaseNav").attr("class")=="active"){
			isAll=false;
		} else {
			isAll=true;
		}
		if (type == "up") {
			if ($scope.startPage > 1) {
				$scope.startPage -= 1;
				$scope.search(isAll);
			}
		} else if (type == "down") {
			if ($scope.startPage < $scope.totalPage) {
				$scope.startPage += 1;
				$scope.search(isAll);
			}
		} else if (type = "userDefined") {
			if ($scope.defined < 1 || $scope.defined > $scope.totalPage
					|| $scope.defined == $scope.startPage) {
				return;
			} else {
				$scope.startPage = $scope.defined;
				$scope.search(isAll);
			}
		}
	}
});
app.directive("click", function() {
	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			// 给tabs绑定点击事件
			element.bind("click", function() {
				$(element).parent().addClass("active");
				$(element).parent().siblings().removeClass("active");

				// 当新的操作不是由AngularJS的库创建的，这时我们需要使用scope.$apply()包住方法
				scope.$apply(function() {
					if (attrs.id == "addBook") {
						scope.addBook = true;
						scope.modifyBook = false;
						scope.deleteBook = false;
						scope.release = false;
					} else if (attrs.id == "modifyBook") {
						scope.addBook = false;
						scope.modifyBook = true;
						scope.deleteBook = false;
						scope.release = false;
					} else if (attrs.id == "deleteBook") {
						scope.addBook = false;
						scope.modifyBook = false;
						scope.deleteBook = true;
						scope.release = false;
					} else if (attrs.id == "release") {
						scope.addBook = false;
						scope.modifyBook = false;
						scope.deleteBook = false;
						scope.release = true;
						scope.bookPlace=true;
					} else if(attrs.id=="addUser"){
						scope.addUser=true;
						scope.isModifyUserShow=false;
						scope.isAvailShow=false;
						scope.isClick=false;
						scope.isModify=false;
					} else if(attrs.id=="modifyUser"){
						scope.addUser=false;
						scope.isModifyUserShow=true;
						scope.isModify=false;
					} else if(attrs.id=="borrowBook"){
						scope.borrowBook=true;
						scope.returnBook=false;
						scope.payBook=false;
						scope.showIsMiss=false;
						
					} else if(attrs.id=="returnBook"){
						scope.borrowBook=false;
						scope.returnBook=true;
						scope.payBook=false;
						scope.showIsMiss=false;
					} else if(attrs.id=="payBook"){
						scope.borrowBook=false;
						scope.returnBook=false;
						scope.payBook=true;
						scope.showIsMiss=false;
					} else if(attrs.id=="all"){
						scope.bookPlace=true;
						scope.searchAll=false;
						scope.releaseItems=null;
						scope.startPage=1;
						scope.totalPage=1;
					} else if(attrs.id=="one"){
						scope.releaseItems=null;
						scope.bookPlace=false;
						scope.searchAll=true;
						scope.search(true);
						scope.startPage=1;
						scope.totalPage=1;
					} else if(attrs.id=="sureIsMiss"){
						scope.borrowBook=false;
						scope.returnBook=false;
						scope.payBook=false;
						scope.showIsMiss=true;
					} else if(attrs.id=="bookInfoMessage"){
						scope.showBookInfo=true;
						scope.showOrder=false;
						scope.showCommit=false;
					} else if(attrs.id=="orderMessage"){
						scope.showBookInfo=false;
						scope.showOrder=true;
						scope.showCommit=false;
					} else if(attrs.id=="commit"){
						scope.showBookInfo=false;
						scope.showOrder=false;
						scope.showCommit=true;
					} else if(attrs.id=="chartUser"){
						scope.chartUser=true;
						scope.chartScale=false;
						scope.chartReturn=false;
						scope.chartMiss=false;
						scope.chartPay=false;
					} else if(attrs.id=="chartScale"){
						scope.chartUser=false;
						scope.chartScale=true;
						scope.chartReturn=false;
						scope.chartMiss=false;
						scope.chartPay=false;
					}  else if(attrs.id=="chartReturn"){
						scope.chartUser=false;
						scope.chartScale=false;
						scope.chartReturn=true;
						scope.chartMiss=false;
						scope.chartPay=false;
					}  else if(attrs.id=="chartMiss"){
						scope.chartUser=false;
						scope.chartScale=false;
						scope.chartReturn=false;
						scope.chartMiss=true;
						scope.chartPay=false;
					}  else if(attrs.id=="chartPay"){
						scope.chartUser=false;
						scope.chartScale=false;
						scope.chartReturn=false;
						scope.chartMiss=false;
						scope.chartPay=true;
					}
				});
			});
		}
	}
});
