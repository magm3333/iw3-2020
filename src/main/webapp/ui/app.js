angular.module('iw3',
	[ 'ngRoute', 'ngSanitize', 'ngAnimate', 'ngTouch', 'ui.bootstrap',
	  'ngStorage', 'oitozero.ngSweetAlert'])	  
.constant('URL_API_BASE', '/api/v1/')
.constant('URL_BASE', '/')
.run(['$rootScope','$uibModal','coreService','$location','$log','$localStorage',
	function($rootScope, $uibModal, coreService, $location, $log,$localStorage) {

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