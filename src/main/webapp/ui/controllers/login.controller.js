angular.module('iw3')
.controller('LoginController', 
		function (
				$rootScope, $scope, $localStorage,
				$uibModalInstance, SweetAlert,
				coreService,$log) {
			$scope.title="Ingreso";
			
			$scope.user={name:"",password:""};
			
			
			$scope.login = function () {
				coreService.login($scope.user).then(
					function(resp){ 
						if(resp.status===200) {
							$localStorage.userdata=resp.data;
							$localStorage.logged=true;
							$rootScope.loginOpen = false;
							$uibModalInstance.dismiss(true);
						}else{
							delete $localStorage.userdata;
							$localStorage.logged=false;
							SweetAlert.swal(resp.data, "Problemas autententicando", "error");
						}
					},
					function(respErr){
						$log.log(respErr);
						SweetAlert.swal(respErr.data, "Problemas autententicando", "error");
					}
				);
			  };  
		}); //End LoginFormController