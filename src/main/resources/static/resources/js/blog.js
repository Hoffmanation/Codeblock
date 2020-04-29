//Main blog angularJs module
var blogApp = angular.module("blogApp",  [ 'ui.bootstrap' ,'ngCookies'  ]);

//Main blog angularJs module-controller
blogApp.controller("blogAppController", function($timeout ,$scope, $http, $rootScope,$window, $location,$cookies) {
	
	/**
	 * Global variables
	 */
	$scope.username
	$scope.searchError = "" ; 
	$scope.KeyPressed = false;
	$scope.showLoader = false;
	$scope.Tags = '';
	$scope.loggedinUser ;
	$scope.alerts = [ { type: 'danger', msg: 'Please login first.' }];
	$scope.restUrl = $location.protocol() + '://' + $location.host() + ':'+ $location.port() + "/codeblock";
	var token = $cookies.get('XSRF-TOKEN');


	/**
	 * Handler Functions 
	 */	
	$scope.ShowTags = function () {
		return $scope.KeyPressed && $scope.Tags !== '';
	}

	$scope.clean = function() {
		$scope.formMessage == "";
		$scope.topic = "";
		$scope.codeblock = "";
		$scope.selectedLanguage = "";
	}
	
	$scope.showTheLoader = function(){
		jQuery('#cover-spin').show();
	}
	
	$scope.hideTheLoader = function(){
		jQuery('#cover-spin').hide();
	}
	

	/**
	 * HTTP Ajax Functions 
	 */	
	$scope.getUserInfo = function() {
		url = $scope.restUrl + "/getUserInfo/";
		$http.get(url).then(function(response) {
			$scope.loggedinUser = response.data.entity;
			$scope.username  = $scope.loggedinUser.username ;
			$scope.username  = $scope.username.substring(0, $scope.username.indexOf('@'));
			$scope.getAllBlogsByUserId();
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response.data.message;
			$scope.hideTheLoader();
		});
	};

	$scope.getAllBlogsByUserId = function() {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAllBlogsByUserId/";
		$http.get(url).then(function(response) {
			$scope.printAllBlogs = response.data.entity;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response;
			$scope.hideTheLoader();
		});

	};
	

	$scope.getAllLanguagesByUserId = function() {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAllLanguagesByUserId/";
		$http.get(url).then(function(response) {
			$scope.printAllUserLanguages = response.data.entity;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response;
			$scope.hideTheLoader();
		});

	};

	$scope.getAvalibaleLanguages = function() {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAvalibaleLanguages/";
		$http.get(url).then(function(response) {
			$scope.printAllAvalibaleLanguages = response.data.entity;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.errormessage = response.data.message;
			$scope.hideTheLoader();
		});

	}

	$scope.createBlog = function() {
		$scope.showTheLoader();
		$scope.blog = {
			topic : $scope.topic,
			codeblock : $scope.codeblock,
			language : $scope.selectedLanguage,
		}
		$http({
			method : 'POST',
			data : $scope.blog,
			url : $scope.restUrl + "/createBlog/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {

			jQuery('.modal').slideUp(function() {

			});
			$scope.getAllBlogsByUserId();
			$scope.getAllLanguagesByUserId();
			$scope.hideTheLoader();
		}).error(function(response, data, status, headers, config) {
			$scope.formMessage = response.message;
			$scope.hideTheLoader();
		});
	};

	
	
	$scope.updateBlog = function(id, code , topic) {
		$scope.showTheLoader();
		$scope.blog = {
			blogId : id,
			topic : topic,
			codeblock : code,
		}
		$http({
			method : 'PUT',
			data : $scope.blog,
			url : $scope.restUrl + "/updateBlog/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			jQuery('#myModal').slideUp('slow');
			$scope.getAllBlogsByUserId();
			$scope.hideTheLoader();
		}).error(function(response, data, status, headers, config) {
			$scope.formMessage = response.message;
			$scope.hideTheLoader();
		});
	};

	$scope.SearchAllBlogs = function(search) {
		$scope.showTheLoader();
		search = search.toUpperCase();
		url = $scope.restUrl + "/SearchAllBlogs/" + search;
		$http.get(url).then(function(response) {
			$scope.printAllAvalibaleLanguages = response.data.entity;
			$scope.printAllBlogs = $scope.printAllAvalibaleLanguages;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.searchError = response.data.message ;
			$scope.hideTheLoader();
			
		});
	}

	$scope.deleteBlog = function(id) {
		$scope.showTheLoader();
		$scope.blog = {
				blogId : id,
			}
		$http({
			method : 'DELETE',
			data : $scope.blog,
			url : $scope.restUrl + "/deleteBlog/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			jQuery('#myModal2').slideUp('slow');
			$scope.getAllBlogsByUserId();
		}).error(function(response, data, status, headers, config) {
			$scope.hideTheLoader();
			$scope.formMessage = response;

		});

	};

	$scope.getAllBlogsByLanguage = function(val) {
		$scope.showTheLoader();
		url = $scope.restUrl + "/getAllBlogsByLanguage/" + val;
		$http.get(url).then(function(response) {
			$scope.printAllBlogsByLanguage = response.data.entity;
			$scope.printAllBlogs = $scope.printAllBlogsByLanguage;
			$scope.hideTheLoader();
		}, function(response) {
			$scope.hideTheLoader();
		});

	}


	$scope.logout = function() {
		$scope.showTheLoader();
		$http({
			method : 'GET',
			data : $scope.blog,
			url : $scope.restUrl + "/logout/",
			headers : {
				'X-CSRF-TOKEN' : token,
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			$scope.formMessage = response
			$scope.hideTheLoader();
		}).error(function(response, data, status, headers, config) {
			$scope.hideTheLoader();
			$scope.formMessage = response.message;

		});

	};

});
