angular.module('iw3',
	[ 'ngRoute', 'ngSanitize', 'ngAnimate', 'ngTouch', 'ui.bootstrap',
	  'ngStorage', 'oitozero.ngSweetAlert', 'productos'])	  
.constant('URL_API_BASE', 'http://localhost:8080/api/v1/')
.constant('URL_BASE', 'http://localhost:8080/')
.run(['$rootScope','$uibModal','coreService','$location','$log','$localStorage',
	function($rootScope, $uibModal, coreService, $location, $log,$localStorage) {


	$rootScope.relocate = function(loc) {
		$rootScope.oldLoc=$location.$$path;
		$location.path(loc);
	};

	$rootScope.userData=function() {
		return $localStorage.userdata;
	};
	
	$rootScope.logout=function() {
		coreService.logout();
	};
	
	
	$rootScope.openLoginForm = function(size) {
		if (!$rootScope.loginOpen) {
			//$rootScope.cleanLoginData();
			$rootScope.loginOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'ui/views/login.html',
				controller : 'LoginController',
				size : size,
				resolve : {
					user : function() {
						return $rootScope.user;
					}
				}
			});
		}
	};

	coreService.authInfo();
	
} 
]);

